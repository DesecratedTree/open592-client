package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * Post-processing compositor owned by the LWJGL renderer, replacing the
 * {@code FramebufferCompositor} role: it renders the scene into a
 * multisample or resolve framebuffer, resolves multisampling with a blit,
 * runs registered {@link LwjglPostEffect}s ping-ponging two rectangle
 * textures, and presents the final result with a fullscreen quad.
 *
 * <p>Effect registration, the format/attachment dirty-bit rebuild and the
 * present loop mirror the legacy class step for step; only the entry
 * points change. Multisampling follows the toolkit's anti-aliasing
 * preference (the legacy {@code aBoolean204}/{@code aBoolean209} GL caps
 * have no LWJGL counterpart yet: GL30 core always provides them).
 * Depth comes from a texture when an effect needs it, otherwise from a
 * renderbuffer, exactly like the legacy paths.
 *
 * <p>Colour buffers use rectangle textures and float formats driven by
 * the effects' format need (0 is byte RGBA, 1 is 32-bit float, 2 is
 * 16-bit float), mirroring {@code recalculateFormat}.
 */
public class LwjglFramebufferCompositor {

    private static final int MAX_FACE_TEXTURES = 2;

    private static final int MASK_COLOUR = GL11.GL_COLOR_BUFFER_BIT;
    private static final int MASK_DEPTH = GL11.GL_DEPTH_BUFFER_BIT;
    private static final int BLIT_FILTER = GL11.GL_NEAREST;

    private final LwjglToolkit toolkit;
    private final LwjglTextureOps textureOps;
    private final LwjglFrameOps frameOps;
    private final ArrayList<LwjglPostEffect> effects = new ArrayList<LwjglPostEffect>();

    private LwjglFramebuffer resolveDisplay;
    private LwjglFramebuffer activeDisplay;
    private LwjglFramebuffer multisampleDisplay;
    private final LwjglTexture2D[] sceneTextures = new LwjglTexture2D[MAX_FACE_TEXTURES];
    private LwjglRenderbuffer renderBuffer;
    private LwjglRenderbuffer hdrRenderBuffer;
    private LwjglTexture2D depthTexture;

    private int bufferWidth = 1;
    private int bufferHeight = 1;
    private int destX;
    private int destY;
    private int colourFormat = 6408;
    private int faceTextureCount;
    private boolean loadDepthTexture;
    private boolean depthDirty = true;
    private boolean framebufferDirty = true;
    private boolean attachmentsDirty = true;
    private boolean formatDirty = true;
    private boolean compositionPending;

    public LwjglFramebufferCompositor(LwjglToolkit toolkit, LwjglTextureOps textureOps, LwjglFrameOps frameOps) {
        if (toolkit == null || textureOps == null || frameOps == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.textureOps = textureOps;
        this.frameOps = frameOps;
        if (frameOps.supportsFramebufferObjects()) {
            this.resolveDisplay = new LwjglFramebuffer(toolkit, frameOps);
            this.activeDisplay = this.resolveDisplay;
            if (toolkit.supportsAntiAliasing()) {
                this.activeDisplay = new LwjglFramebuffer(toolkit, frameOps);
                this.multisampleDisplay = this.activeDisplay;
            }
        }
    }

    /** @return true when a display framebuffer exists. */
    public boolean isDisplayAvailable() {
        return this.activeDisplay != null;
    }

    /**
     * Registers an effect, rebuilding the framebuffers, mirroring
     * {@code method5525}.
     */
    public boolean register(LwjglPostEffect effect) {
        if (this.activeDisplay != null) {
            if (effect.isAvailable() || effect.setup()) {
                this.effects.add(effect);
                effect.setActive(true);
                this.recalculateFormat();
                if (this.rebuildFramebuffers()) {
                    if (this.bufferWidth != -1 && this.bufferHeight != -1) {
                        effect.resize(this.bufferWidth, this.bufferHeight);
                    }
                    return true;
                }
            }
            this.unregister(effect);
        }
        return false;
    }

    /**
     * Releases and unregisters an effect, mirroring {@code method5529}.
     */
    public void unregister(LwjglPostEffect effect) {
        effect.setActive(false);
        effect.release();
        this.effects.remove(effect);
        this.recalculateFormat();
    }

    /** Releases every effect and deletes every GL object. */
    public void shutdown() {
        for (int i = 0; i < this.effects.size(); i++) {
            LwjglPostEffect effect = this.effects.get(i);
            effect.setActive(false);
            effect.release();
        }
        this.effects.clear();
        this.deleteDisplays();
        this.bufferWidth = this.bufferHeight = 1;
    }

    /**
     * Prepares for scene rendering into the active display, mirroring
     * {@code prepareForRendering}. The buffer size is the capture size;
     * the destination is the on-screen offset of the final quad.
     */
    public boolean prepareForRendering(int width, int height, int destX, int destY) {
        if (this.activeDisplay == null || this.effects.isEmpty()) {
            return false;
        }
        if (this.bufferWidth != width || this.bufferHeight != height) {
            this.bufferWidth = width;
            this.bufferHeight = height;
            for (int i = 0; i < this.effects.size(); i++) {
                this.effects.get(i).resize(width, height);
            }
            this.formatDirty = true;
            this.attachmentsDirty = true;
            this.depthDirty = true;
        }
        if (!this.rebuildFramebuffers()) {
            return false;
        }
        this.destX = destX;
        this.destY = destY;
        this.compositionPending = true;
        this.toolkit.pushFramebuffer(this.activeDisplay);
        this.activeDisplay.selectDrawBuffer(0);
        this.toolkit.setViewport(-destY, height - this.activeDisplay.height() - destX, width, height);
        return true;
    }

    /**
     * Runs every effect pass and presents the final quad, mirroring
     * {@code compositeEffects}.
     */
    public void composite() {
        if (!this.compositionPending) {
            return;
        }
        if (this.multisampleDisplay != null) {
            this.multisampleDisplay.bindRead();
            this.resolveDisplay.bindDraw();
            this.multisampleDisplay.selectReadBuffer();
            this.resolveDisplay.selectDrawBuffer(0);
            this.frameOps.blitFramebuffer(0, 0, this.bufferWidth, this.bufferHeight, 0, 0, this.bufferWidth, this.bufferHeight, this.loadDepthTexture ? MASK_COLOUR | MASK_DEPTH : MASK_COLOUR, BLIT_FILTER);
            this.multisampleDisplay.unbindRead();
            this.resolveDisplay.unbindDraw();
            this.resolveDisplay.bind();
        }
        this.toolkit.stateCache().reset();
        int read = 0;
        int write = 1;
        for (int i = 0; i < this.effects.size(); i++) {
            LwjglPostEffect effect = this.effects.get(i);
            boolean lastEffect = i == this.effects.size() - 1;
            for (int pass = 0; pass < effect.passes(); pass++) {
                boolean lastPass = lastEffect && pass == effect.passes() - 1;
                effect.present(pass, this.sceneTextures[read], this.depthTexture);
                if (lastPass) {
                    this.toolkit.popFramebuffer(this.activeDisplay);
                    this.toolkit.setViewport(0, 0, this.bufferWidth, this.bufferHeight);
                    this.finalQuad();
                } else {
                    this.resolveDisplay.selectDrawBuffer(write);
                    this.chainQuad();
                }
                read = read + 1 & 0x1;
                write = write + 1 & 0x1;
                effect.finish(pass);
            }
        }
        this.compositionPending = false;
    }

    private void recalculateFormat() {
        boolean needDepthTexture = false;
        int maxPasses = 0;
        int totalPasses = 0;
        for (int i = 0; i < this.effects.size(); i++) {
            LwjglPostEffect effect = this.effects.get(i);
            int need = effect.formatNeed();
            totalPasses += effect.passes();
            if (need > maxPasses) {
                maxPasses = need;
            }
            needDepthTexture |= effect.needsDepthTexture();
        }
        int format;
        if (maxPasses == 2) {
            format = 34836;
        } else if (maxPasses == 1) {
            format = 34842;
        } else {
            format = 6408;
        }
        if (format != this.colourFormat) {
            this.formatDirty = true;
            this.colourFormat = format;
        }
        int oldFaces = this.faceTextureCount > 2 ? 2 : this.faceTextureCount;
        int newFaces = totalPasses > 2 ? 2 : totalPasses;
        if (needDepthTexture != this.loadDepthTexture) {
            this.loadDepthTexture = needDepthTexture;
            this.depthDirty = true;
        }
        if (oldFaces != newFaces) {
            this.attachmentsDirty = true;
            this.formatDirty = true;
        }
        this.faceTextureCount = totalPasses;
    }

    private boolean rebuildFramebuffers() {
        if (this.depthDirty) {
            this.deleteDepth();
            if (this.multisampleDisplay != null) {
                this.renderBuffer = new LwjglRenderbuffer(this.toolkit, 6402, this.bufferWidth, this.bufferHeight, this.toolkit.multisampleSamples(), this.frameOps);
            }
            if (this.loadDepthTexture) {
                this.depthTexture = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_RECTANGLE, 6402, this.bufferWidth, this.bufferHeight, this.textureOps);
            } else if (this.renderBuffer == null) {
                this.renderBuffer = new LwjglRenderbuffer(this.toolkit, 6402, this.bufferWidth, this.bufferHeight, this.frameOps);
            }
            this.attachmentsDirty = true;
            this.depthDirty = false;
            this.framebufferDirty = true;
        }
        if (this.formatDirty) {
            this.deleteColour();
            if (this.multisampleDisplay != null) {
                this.hdrRenderBuffer = new LwjglRenderbuffer(this.toolkit, this.colourFormat, this.bufferWidth, this.bufferHeight, this.toolkit.multisampleSamples(), this.frameOps);
            }
            this.sceneTextures[0] = new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_RECTANGLE, this.colourFormat, this.bufferWidth, this.bufferHeight, this.textureOps);
            this.sceneTextures[1] = this.faceTextureCount <= 1 ? null : new LwjglTexture2D(this.toolkit, LwjglTexture.TARGET_RECTANGLE, this.colourFormat, this.bufferWidth, this.bufferHeight, this.textureOps);
            this.formatDirty = false;
            this.framebufferDirty = true;
            this.attachmentsDirty = true;
        }
        if (this.attachmentsDirty) {
            if (this.multisampleDisplay == null) {
                this.toolkit.pushFramebuffer(this.resolveDisplay);
                this.resolveDisplay.detachSlot(0, this.textureOps, this.frameOps);
                this.resolveDisplay.detachSlot(1, this.textureOps, this.frameOps);
                this.resolveDisplay.detachSlot(LwjglFramebuffer.SLOT_DEPTH, this.textureOps, this.frameOps);
                this.resolveDisplay.attachTexture(this.sceneTextures[0], 0, this.textureOps);
                if (this.faceTextureCount > 1) {
                    this.resolveDisplay.attachTexture(this.sceneTextures[1], 1, this.textureOps);
                }
                if (this.loadDepthTexture) {
                    this.resolveDisplay.attachTexture(this.depthTexture, LwjglFramebuffer.SLOT_DEPTH, this.textureOps);
                } else {
                    this.resolveDisplay.attachRenderbuffer(this.renderBuffer, LwjglFramebuffer.SLOT_DEPTH, this.frameOps);
                }
                this.toolkit.popFramebuffer(this.resolveDisplay);
            } else {
                this.toolkit.pushFramebuffer(this.resolveDisplay);
                this.resolveDisplay.detachSlot(0, this.textureOps, this.frameOps);
                this.resolveDisplay.detachSlot(1, this.textureOps, this.frameOps);
                this.resolveDisplay.detachSlot(LwjglFramebuffer.SLOT_DEPTH, this.textureOps, this.frameOps);
                this.resolveDisplay.attachTexture(this.sceneTextures[0], 0, this.textureOps);
                if (this.faceTextureCount > 1) {
                    this.resolveDisplay.attachTexture(this.sceneTextures[1], 1, this.textureOps);
                }
                if (this.loadDepthTexture) {
                    this.resolveDisplay.attachTexture(this.depthTexture, LwjglFramebuffer.SLOT_DEPTH, this.textureOps);
                }
                this.toolkit.popFramebuffer(this.resolveDisplay);
                this.toolkit.pushFramebuffer(this.multisampleDisplay);
                this.multisampleDisplay.detachSlot(0, this.textureOps, this.frameOps);
                this.multisampleDisplay.detachSlot(LwjglFramebuffer.SLOT_DEPTH, this.textureOps, this.frameOps);
                this.multisampleDisplay.attachRenderbuffer(this.hdrRenderBuffer, 0, this.frameOps);
                this.multisampleDisplay.attachRenderbuffer(this.renderBuffer, LwjglFramebuffer.SLOT_DEPTH, this.frameOps);
                this.toolkit.popFramebuffer(this.multisampleDisplay);
            }
            this.framebufferDirty = true;
            this.attachmentsDirty = false;
        }
        if (this.framebufferDirty) {
            this.toolkit.pushFramebuffer(this.activeDisplay);
            this.framebufferDirty = !this.activeDisplay.checkComplete();
            this.toolkit.popFramebuffer(this.activeDisplay);
        }
        return !this.framebufferDirty;
    }

    private void deleteDepth() {
        if (this.renderBuffer != null) {
            this.renderBuffer.dispose();
            this.renderBuffer = null;
        }
        if (this.depthTexture != null) {
            this.depthTexture.delete();
            this.depthTexture = null;
        }
    }

    private void deleteColour() {
        if (this.hdrRenderBuffer != null) {
            this.hdrRenderBuffer.dispose();
            this.hdrRenderBuffer = null;
        }
        for (int i = 0; i < this.sceneTextures.length; i++) {
            if (this.sceneTextures[i] != null) {
                this.sceneTextures[i].delete();
                this.sceneTextures[i] = null;
            }
        }
    }

    private void deleteDisplays() {
        this.deleteDepth();
        this.deleteColour();
        if (this.resolveDisplay != null) {
            this.resolveDisplay.delete();
            this.resolveDisplay = null;
        }
        if (this.multisampleDisplay != null) {
            this.multisampleDisplay.delete();
            this.multisampleDisplay = null;
        }
        this.activeDisplay = null;
    }

    private void chainQuad() {
        this.frameOps.beginQuads();
        this.frameOps.texCoord2f(0.0F, 0.0F);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 0.0F, 0.0F);
        this.frameOps.vertex2i(0, 0);
        this.frameOps.texCoord2f(0.0F, this.bufferHeight);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 0.0F, 1.0F);
        this.frameOps.vertex2i(0, this.bufferHeight);
        this.frameOps.texCoord2f(this.bufferWidth, this.bufferHeight);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 1.0F, 1.0F);
        this.frameOps.vertex2i(this.bufferWidth, this.bufferHeight);
        this.frameOps.texCoord2f(this.bufferWidth, 0.0F);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 1.0F, 0.0F);
        this.frameOps.vertex2i(this.bufferWidth, 0);
        this.frameOps.end();
    }

    private void finalQuad() {
        this.frameOps.beginQuads();
        this.frameOps.texCoord2f(0.0F, this.bufferHeight);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 0.0F, 1.0F);
        this.frameOps.vertex2i(this.destY, this.destX);
        this.frameOps.texCoord2f(0.0F, 0.0F);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 0.0F, 0.0F);
        this.frameOps.vertex2i(this.destY, this.bufferHeight + this.destX);
        this.frameOps.texCoord2f(this.bufferWidth, 0.0F);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 1.0F, 0.0F);
        this.frameOps.vertex2i(this.destY + this.bufferWidth, this.bufferHeight + this.destX);
        this.frameOps.texCoord2f(this.bufferWidth, this.bufferHeight);
        this.frameOps.multiTexCoord2f(GL13.GL_TEXTURE1, 1.0F, 1.0F);
        this.frameOps.vertex2i(this.bufferWidth + this.destY, this.destX);
        this.frameOps.end();
    }
}
