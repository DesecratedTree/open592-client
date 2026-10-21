package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL30;

/**
 * Framebuffer object owned by the LWJGL renderer, replacing the
 * {@code GlFramebuffer} role. Attachments mirror the legacy slots: colour
 * slots 0-7 map to {@code COLOR_ATTACHMENT0+i} and slot 8 maps to
 * {@code DEPTH_ATTACHMENT}, exactly like the legacy
 * {@code anIntArray496} table.
 *
 * <p>Binding state mirrors the legacy flag word: the framebuffer,
 * read and draw bindings are tracked separately and attachment or
 * draw-buffer selection requires the object to be bound, throwing
 * otherwise just like {@code method3610}/{@code method3612}. Detaching a
 * slot needs no binding, also like the legacy {@code method3611}.
 * Attached images must share dimensions; the first attachment sets the
 * size, like the legacy size-consistency checks. Deletion is immediate
 * and idempotent (the legacy tick queued it; the LWJGL renderer has no
 * cross-thread delete queue, so deletes run on the GL thread instead).
 */
public class LwjglFramebuffer {

    /** Combined read/draw binding target. */
    public static final int TARGET_FRAMEBUFFER = GL30.GL_FRAMEBUFFER;

    /** Read binding target (multisample resolve source). */
    public static final int TARGET_READ = GL30.GL_READ_FRAMEBUFFER;

    /** Draw binding target (multisample resolve destination). */
    public static final int TARGET_DRAW = GL30.GL_DRAW_FRAMEBUFFER;

    /** Complete status, mirroring the {@code method3616} check. */
    public static final int STATUS_COMPLETE = GL30.GL_FRAMEBUFFER_COMPLETE;

    /** Colour slots plus the depth slot. */
    public static final int SLOT_COUNT = 9;

    /** Depth attachment slot. */
    public static final int SLOT_DEPTH = 8;

    private static final int FLAG_READ = 0x1;
    private static final int FLAG_DRAW = 0x2;
    private static final int FLAG_FRAMEBUFFER = 0x4;

    private final LwjglFrameOps ops;
    private final int id;
    private final Object[] attachments = new Object[SLOT_COUNT];

    private int flags;
    private int mask;
    private int width;
    private int height;
    private boolean deleted;

    /**
     * Maps an attachment slot to its GL enum, mirroring the legacy table:
     * slots 0-7 are colour attachments, slot 8 is the depth attachment.
     */
    public static int attachmentForSlot(int slot) {
        if (slot >= 0 && slot < SLOT_DEPTH) {
            return GL30.GL_COLOR_ATTACHMENT0 + slot;
        }
        if (slot == SLOT_DEPTH) {
            return GL30.GL_DEPTH_ATTACHMENT;
        }
        throw new RuntimeException();
    }

    /**
     * @param toolkit owning toolkit (unused for accounting: framebuffer
     * objects carry no byte count, like the legacy class).
     * @param ops frame seam; {@link LwjglFrameGl#get()} in game code.
     */
    public LwjglFramebuffer(LwjglToolkit toolkit, LwjglFrameOps ops) {
        if (toolkit == null || ops == null) {
            throw new RuntimeException();
        }
        if (!ops.supportsFramebufferObjects()) {
            throw new IllegalStateException();
        }
        this.ops = ops;
        this.id = ops.genFramebuffer();
    }

    /** @return GL object name. */
    public int id() {
        return this.id;
    }

    /** @return attached width, -1 when empty. */
    public int width() {
        return this.width;
    }

    /** @return attached height, -1 when empty. */
    public int height() {
        return this.height;
    }

    /** @return true after {@link #delete()}. */
    public boolean isDeleted() {
        return this.deleted;
    }

    /** Binds for combined read/draw. */
    public void bind() {
        this.checkLive();
        this.ops.bindFramebuffer(TARGET_FRAMEBUFFER, this.id);
        this.flags |= FLAG_FRAMEBUFFER;
    }

    /** Unbinds the combined binding, restoring the default framebuffer. */
    public void unbind() {
        this.ops.bindFramebuffer(TARGET_FRAMEBUFFER, 0);
        this.flags &= ~FLAG_FRAMEBUFFER;
    }

    /** Binds as the blit source. */
    public void bindRead() {
        this.checkLive();
        this.ops.bindFramebuffer(TARGET_READ, this.id);
        this.flags |= FLAG_READ;
    }

    /** Unbinds the read binding. */
    public void unbindRead() {
        this.ops.bindFramebuffer(TARGET_READ, 0);
        this.flags &= ~FLAG_READ;
    }

    /** Binds as the blit destination. */
    public void bindDraw() {
        this.checkLive();
        this.ops.bindFramebuffer(TARGET_DRAW, this.id);
        this.flags |= FLAG_DRAW;
    }

    /** Unbinds the draw binding. */
    public void unbindDraw() {
        this.ops.bindFramebuffer(TARGET_DRAW, 0);
        this.flags &= ~FLAG_DRAW;
    }

    /**
     * Attaches a 2D texture image to a slot. Requires a binding.
     */
    public void attachTexture(LwjglTexture2D texture, int slot, LwjglTextureOps textureOps) {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        this.checkSlot(slot);
        this.checkSize(slot, texture.width(), texture.height());
        texture.attachToFramebuffer(this.currentTarget(), attachmentForSlot(slot), textureOps);
        this.attachments[slot] = texture;
        this.mask |= 0x1 << slot;
    }

    /**
     * Attaches a cube-map face to colour slot 0, mirroring
     * {@code method3613}.
     */
    public void attachCubeFace(LwjglCubeTexture texture, int face, LwjglTextureOps textureOps) {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        this.checkSize(0, texture.size(), texture.size());
        texture.attachToFramebuffer(this.currentTarget(), face, attachmentForSlot(0), textureOps);
        this.attachments[0] = texture;
        this.mask |= 0x1;
    }

    /**
     * Attaches a renderbuffer to a slot. Requires a binding.
     */
    public void attachRenderbuffer(LwjglRenderbuffer buffer, int slot, LwjglFrameOps frameOps) {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        this.checkSlot(slot);
        this.checkSize(slot, buffer.width(), buffer.height());
        buffer.attachToFramebuffer(this.currentTarget(), attachmentForSlot(slot), frameOps);
        this.attachments[slot] = buffer;
        this.mask |= 0x1 << slot;
    }

    /**
     * Detaches whatever slot holds, forgetting the attachment. Needs no
     * binding, like the legacy detach.
     */
    public void detachSlot(int slot, LwjglTextureOps textureOps, LwjglFrameOps frameOps) {
        this.checkSlot(slot);
        Object attached = this.attachments[slot];
        if (attached instanceof LwjglTexture2D) {
            ((LwjglTexture2D) attached).detachFromFramebuffer(textureOps);
        } else if (attached instanceof LwjglCubeTexture) {
            ((LwjglCubeTexture) attached).detachFromFramebuffer(textureOps);
        } else if (attached instanceof LwjglRenderbuffer) {
            ((LwjglRenderbuffer) attached).detachFromFramebuffer(frameOps);
        }
        this.attachments[slot] = null;
        this.mask &= ~(0x1 << slot);
    }

    /**
     * Selects the draw buffer by slot. Requires a binding, mirroring
     * {@code method3610}.
     */
    public void selectDrawBuffer(int slot) {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        this.checkSlot(slot);
        this.ops.drawBuffer(attachmentForSlot(slot));
    }

    /**
     * Selects colour slot 0 as the read buffer, mirroring
     * {@code method3618}. Requires a binding.
     */
    public void selectReadBuffer() {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        this.ops.readBuffer(attachmentForSlot(0));
    }

    /** @return true when the bound framebuffer is complete. */
    public boolean checkComplete() {
        if (this.currentTarget() == -1) {
            throw new RuntimeException();
        }
        return this.ops.checkFramebufferStatus(this.currentTarget()) == STATUS_COMPLETE;
    }

    /** Deletes the GL object. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteFramebuffer(this.id);
        }
    }

    private int currentTarget() {
        if ((this.flags & FLAG_FRAMEBUFFER) != 0) {
            return TARGET_FRAMEBUFFER;
        }
        if ((this.flags & FLAG_DRAW) == 0) {
            return (this.flags & FLAG_READ) == 0 ? -1 : TARGET_READ;
        }
        return TARGET_DRAW;
    }

    private void checkSlot(int slot) {
        if (slot < 0 || slot >= SLOT_COUNT) {
            throw new RuntimeException();
        }
    }

    private void checkSize(int slot, int width, int height) {
        if ((this.mask & ~(0x1 << slot)) == 0) {
            this.width = width;
            this.height = height;
        } else if (this.width != width || this.height != height) {
            throw new RuntimeException();
        }
    }

    private void checkLive() {
        if (this.deleted) {
            throw new RuntimeException();
        }
    }
}
