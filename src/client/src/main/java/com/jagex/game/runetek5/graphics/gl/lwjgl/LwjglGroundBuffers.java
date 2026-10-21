package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Ground tile index upload owned by the LWJGL renderer, replacing the tile
 * path in {@code GlGround}.
 *
 * <p>{@link #build} mirrors the {@code method1902} VBO branch (static usage,
 * always) and records the {@code Class177} declaration layout the ground
 * path installs afterwards: float-3 positions at offset 0, unsigned-byte-4
 * colours at offset 12, float texcoords at offset 16 (2 components, 3 when
 * {@code shadowData} widens the stride, exactly like
 * {@code anIntArrayArrayArray9 != null} switching the declaration), and
 * float-3 normals (only when {@code normals}, the legacy
 * {@code (featureFlags & 0x7) != 0} gate) at offset 24, or 28 with shadow
 * data.
 *
 * <p>Rebuilding deletes the previous tile buffer first, so unlike the
 * legacy field overwrite no GPU storage leaks. A deleted holder must be
 * discarded.
 */
public final class LwjglGroundBuffers {

    /** Position attribute: float x3 at offset 0. */
    public static final int POSITION_OFFSET = 0;

    /** Colour attribute: unsigned-byte x4 at offset 12. */
    public static final int COLOUR_OFFSET = 12;

    /** Texcoord attribute offset. */
    public static final int TEXCOORD_OFFSET = 16;

    /** Normal attribute offset without shadow data. */
    public static final int NORMAL_OFFSET = 24;

    /** Normal attribute offset with shadow data. */
    public static final int NORMAL_OFFSET_SHADOW = 28;

    private final LwjglToolkit toolkit;
    private final LwjglGlOps ops;
    private LwjglElementArrayBuffer tileBuffer;
    private int texCoordComponents;
    private boolean hasNormal;
    private int normalOffset = -1;

    /**
     * @param toolkit owning toolkit, adjusted for every size change.
     * @param ops GL seam; {@link LwjglGl#get()} in game code.
     */
    public LwjglGroundBuffers(LwjglToolkit toolkit, LwjglGlOps ops) {
        if (toolkit == null || ops == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.ops = ops;
    }

    /**
     * Uploads the tile index data and records the vertex layout.
     *
     * @param data tile bytes, at least {@code size} bytes.
     * @param size storage size in bytes.
     * @param indexType index element type.
     * @param shadowData true when per-vertex shadow data widens the stride.
     * @param normals true when the normal declaration is installed.
     * @return the live tile buffer.
     */
    public LwjglElementArrayBuffer build(byte[] data, int size, int indexType, boolean shadowData, boolean normals) {
        if (data == null) {
            throw new RuntimeException();
        }
        if (this.tileBuffer != null) {
            this.tileBuffer.delete();
        }
        this.tileBuffer = new LwjglElementArrayBuffer(this.toolkit, indexType, data, size, false, this.ops);
        this.texCoordComponents = shadowData ? 3 : 2;
        this.hasNormal = normals;
        this.normalOffset = normals ? (shadowData ? NORMAL_OFFSET_SHADOW : NORMAL_OFFSET) : -1;
        return this.tileBuffer;
    }

    /** @return the tile buffer, null before the first build. */
    public LwjglElementArrayBuffer tileBuffer() {
        return this.tileBuffer;
    }

    /** @return texcoord component count, 2 or 3. */
    public int texCoordComponents() {
        return this.texCoordComponents;
    }

    /** @return true when the normal declaration is installed. */
    public boolean hasNormal() {
        return this.hasNormal;
    }

    /** @return normal attribute offset, -1 when absent. */
    public int normalOffset() {
        return this.normalOffset;
    }

    /** Deletes the tile buffer. Idempotent. */
    public void delete() {
        if (this.tileBuffer != null) {
            this.tileBuffer.delete();
        }
    }
}
