package com.jagex.game.runetek5.graphics.gl.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * GPU texture owned by the LWJGL renderer, replacing the {@code Class6}
 * role. Creation generates a name and binds it once; deletion is
 * idempotent and releases the tracked bytes.
 *
 * <p>Filter state mirrors the legacy pair: smoothing (linear vs nearest)
 * plus the mipmap flag combine into the min/mag parameters exactly like
 * {@code Class6.method4180}, and only transitions emit GL calls. Byte
 * accounting mirrors {@code Class6.method4184}: width-by-height pixels at
 * the internal format's byte depth, scaled by 4/3 once mipmaps exist.
 *
 * <p>Construction and mutation require a current GL context on the calling
 * thread; pass a fake {@link LwjglTextureOps} to exercise the lifecycle
 * without one.
 */
public class LwjglTexture {

    /** 2D texture target. */
    public static final int TARGET_2D = GL11.GL_TEXTURE_2D;

    /** Rectangle (non-power-of-two, unnormalised) texture target. */
    public static final int TARGET_RECTANGLE = 0x84F5;

    /** Cube-map texture target. */
    public static final int TARGET_CUBE_MAP = 0x8513;

    /** First cube-map face target; faces are contiguous. */
    public static final int TARGET_CUBE_MAP_POSITIVE_X = 0x8515;

    protected final LwjglToolkit toolkit;
    protected final LwjglTextureOps ops;

    private final int target;
    private final int internalFormat;
    private final int id;
    private final int pixelCount;

    private int byteSize;
    private boolean smoothing;
    private boolean mipmapped;
    private boolean deleted;

    /**
     * @param toolkit owning toolkit, adjusted for every size change.
     * @param target GL texture target this texture binds to.
     * @param internalFormat sized internal format, driving the byte count.
     * @param pixelCount texel count (width by height, times faces for cubes).
     * @param mipmapped true when the full pyramid is (or will be) resident.
     * @param ops GL seam; {@link LwjglTextureGl#get()} in game code.
     */
    public LwjglTexture(LwjglToolkit toolkit, int target, int internalFormat, int pixelCount, boolean mipmapped, LwjglTextureOps ops) {
        if (toolkit == null || ops == null || pixelCount < 0) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.ops = ops;
        this.target = target;
        this.internalFormat = internalFormat;
        this.pixelCount = pixelCount;
        this.mipmapped = mipmapped;
        this.id = ops.genTexture();
        this.bind();
        this.byteSize = trackedBytes();
        toolkit.trackTexture(this.byteSize);
    }

    /** Binds this texture on the toolkit's active unit. */
    public final void bind() {
        this.toolkit.bindTexture(this);
    }

    /** @return GL texture target. */
    public final int target() {
        return this.target;
    }

    /** @return sized internal format. */
    public final int internalFormat() {
        return this.internalFormat;
    }

    /** @return GL object name, 0 after {@link #delete()}. */
    public final int id() {
        return this.deleted ? 0 : this.id;
    }

    /** @return tracked GPU bytes for this texture. */
    public final int byteSize() {
        return this.byteSize;
    }

    /** @return true after {@link #delete()}. */
    public final boolean isDeleted() {
        return this.deleted;
    }

    /**
     * Enables or disables linear filtering, emitting the min/mag pair only
     * on change (mirrors {@code Class6.method4181}).
     */
    public final void setSmoothing(boolean smoothing) {
        if (this.deleted) {
            throw new RuntimeException();
        }
        if (this.smoothing != smoothing) {
            this.smoothing = smoothing;
            this.applyFilter();
        }
    }

    /**
     * Flags the mipmap pyramid resident, emitting the min/mag pair and
     * growing the tracked bytes by a third. Only upgrades from false to
     * true, mirroring {@code Class6.method4185}.
     */
    public final void setMipmapped() {
        if (this.deleted) {
            throw new RuntimeException();
        }
        if (!this.mipmapped) {
            int before = this.byteSize;
            this.mipmapped = true;
            this.applyFilter();
            this.byteSize = trackedBytes();
            this.toolkit.trackTexture(this.byteSize - before);
        }
    }

    /** Deletes the GL object and releases the tracked bytes. Idempotent. */
    public void delete() {
        if (!this.deleted) {
            this.deleted = true;
            this.ops.deleteTexture(this.id);
            this.toolkit.trackTexture(-this.byteSize);
            this.byteSize = 0;
        }
    }

    private void applyFilter() {
        this.bind();
        int min;
        int mag;
        if (this.smoothing) {
            min = this.mipmapped ? GL11.GL_LINEAR_MIPMAP_LINEAR : GL11.GL_LINEAR;
            mag = GL11.GL_LINEAR;
        } else {
            min = this.mipmapped ? GL11.GL_NEAREST_MIPMAP_NEAREST : GL11.GL_NEAREST;
            mag = GL11.GL_NEAREST;
        }
        this.ops.texParameteri(this.target, GL11.GL_TEXTURE_MIN_FILTER, min);
        this.ops.texParameteri(this.target, GL11.GL_TEXTURE_MAG_FILTER, mag);
    }

    private int trackedBytes() {
        int bytes = LwjglTextureFormat.bytesPerPixel(this.internalFormat) * this.pixelCount;
        return this.mipmapped ? bytes * 4 / 3 : bytes;
    }
}
