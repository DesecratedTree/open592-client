package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Render byte snapshot of a {@code Mesh}: packed vertex positions plus the
 * face index stream, ready for {@link LwjglModelBuffers}. Immutable.
 */
public final class LwjglMeshData {

    private final byte[] vertexBytes;
    private final byte[] indexBytes;
    private final int indexType;
    private final int indexCount;
    private final float centerX;
    private final float centerY;
    private final float centerZ;
    private final float radius;

    /**
     * @param vertexBytes packed float-3 positions, 12 bytes per vertex.
     * @param indexBytes packed face corners in stored order.
     * @param indexType 5123 (unsigned short) or 5125 (unsigned int).
     * @param indexCount face corners, three per face.
     * @param centerX bounding-sphere center x.
     * @param centerY bounding-sphere center y.
     * @param centerZ bounding-sphere center z.
     * @param radius bounding-sphere radius, never negative.
     */
    public LwjglMeshData(byte[] vertexBytes, byte[] indexBytes, int indexType, int indexCount,
        float centerX, float centerY, float centerZ, float radius) {
        if (vertexBytes == null || indexBytes == null || indexCount <= 0
            || (indexType != 5123 && indexType != 5125) || radius < 0.0F) {
            throw new RuntimeException();
        }
        this.vertexBytes = vertexBytes;
        this.indexBytes = indexBytes;
        this.indexType = indexType;
        this.indexCount = indexCount;
        this.centerX = centerX;
        this.centerY = centerY;
        this.centerZ = centerZ;
        this.radius = radius;
    }

    /** @return packed float-3 positions, 12 bytes per vertex. */
    public byte[] vertexBytes() {
        return this.vertexBytes;
    }

    /** @return packed face corners in stored order. */
    public byte[] indexBytes() {
        return this.indexBytes;
    }

    /** @return 5123 (unsigned short) or 5125 (unsigned int). */
    public int indexType() {
        return this.indexType;
    }

    /** @return face corners, three per face. */
    public int indexCount() {
        return this.indexCount;
    }

    /** @return vertex storage size in bytes. */
    public int vertexSize() {
        return this.vertexBytes.length;
    }

    /** @return index storage size in bytes. */
    public int indexSize() {
        return this.indexBytes.length;
    }

    /** @return bounding-sphere center x. */
    public float centerX() {
        return this.centerX;
    }

    /** @return bounding-sphere center y. */
    public float centerY() {
        return this.centerY;
    }

    /** @return bounding-sphere center z. */
    public float centerZ() {
        return this.centerZ;
    }

    /** @return bounding-sphere radius, never negative. */
    public float radius() {
        return this.radius;
    }
}
