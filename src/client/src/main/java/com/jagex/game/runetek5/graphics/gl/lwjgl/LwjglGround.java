package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class65;

/**
 * Minimal HD ground owned by the LWJGL renderer, replacing the
 * {@code GlGround} role for the static tile-upload step only.
 *
 * <p>Construction mirrors the {@code GlGround} argument order
 * (ground flags, feature flags, width, length, tile heights, normal
 * heights, scaling) and keeps the real tile height arrays. {@link #l} and
 * {@link #a} answer clamped height lookups addressed as (x, z), matching
 * the 2D tile addressing the {@code i} shadow-level path uses.
 * {@link #buildTiles} packs a unit-spaced interleaved vertex grid from the
 * real heights (float-3 position, white ubyte-4 colour, planar float-2
 * texcoord: 24 bytes per vertex, the no-shadow layout) and uploads it
 * through {@link LwjglGroundBuffers#build}. World spacing, normals and
 * layer indices stay with the future scene-matrix and lighting steps.
 *
 * <p>Dynamic tile editing, shadows and lights fast-fail or no-op: those
 * paths are not ported yet. The empty {@code method4028} and the
 * max-assigning {@code i} mirror the legacy bodies exactly.
 */
public final class LwjglGround extends Class65 {

    private final int groundFlags;
    private final int featureFlags;
    private final int[][] tileHeights;
    private final int[][] normalHeights;
    private final byte[][] shadowLevels;
    private final LwjglToolkit toolkit;
    private final LwjglGroundBuffers buffers;
    private boolean built;
    private int tileVertexCount;

    /**
     * @param toolkit owning toolkit; uploads route through its buffer seam.
     * @param groundFlags ground flags, held for the layer step.
     * @param featureFlags feature flags, held for the lighting step.
     * @param width tiles across.
     * @param length tiles deep.
     * @param tileHeights per-tile heights, oversized by one like the
     *     legacy arrays.
     * @param normalHeights per-tile normal heights, held for the
     *     lighting step.
     * @param scaling tile scaling, held for the scene-matrix step.
     */
    public LwjglGround(LwjglToolkit toolkit, int groundFlags, int featureFlags, int width, int length,
        int[][] tileHeights, int[][] normalHeights, int scaling) {
        super(width, length, scaling);
        if (toolkit == null) {
            throw new RuntimeException();
        }
        this.groundFlags = groundFlags;
        this.featureFlags = featureFlags;
        this.tileHeights = tileHeights;
        this.normalHeights = normalHeights;
        this.shadowLevels = new byte[width + 1][length + 1];
        this.toolkit = toolkit;
        this.buffers = toolkit.createGroundBuffers();
    }

    /**
     * Packs the height grid and uploads it. Rebuilding replaces the
     * previous upload without leaking.
     *
     * @return the live tile buffer.
     */
    public LwjglElementArrayBuffer buildTiles() {
        int pointsX = Math.min(this.anInt5262 + 1, this.tileHeights == null ? 0 : this.tileHeights.length);
        int pointsZ = 0;
        if (this.tileHeights != null) {
            for (int x = 0; x < pointsX; x++) {
                if (this.tileHeights[x] != null && this.tileHeights[x].length > pointsZ) {
                    pointsZ = this.tileHeights[x].length;
                }
            }
            pointsZ = Math.min(pointsZ, this.anInt5258 + 1);
        }
        byte[] data = new byte[pointsX * pointsZ * 24];
        int at = 0;
        for (int x = 0; x < pointsX; x++) {
            for (int z = 0; z < pointsZ; z++) {
                putFloat(data, at, x);
                putFloat(data, at + 4, heightAt(x, z));
                putFloat(data, at + 8, z);
                data[at + 12] = (byte) 0xFF;
                data[at + 13] = (byte) 0xFF;
                data[at + 14] = (byte) 0xFF;
                data[at + 15] = (byte) 0xFF;
                putFloat(data, at + 16, x);
                putFloat(data, at + 20, z);
                at += 24;
            }
        }
        LwjglElementArrayBuffer uploaded = this.buffers.build(data, data.length, 5123, false, false);
        this.tileVertexCount = pointsX * pointsZ;
        this.built = true;
        return uploaded;
    }

    private static void putFloat(byte[] out, int at, int value) {
        int bits = Float.floatToIntBits((float) value);
        out[at] = (byte) bits;
        out[at + 1] = (byte) (bits >> 8);
        out[at + 2] = (byte) (bits >> 16);
        out[at + 3] = (byte) (bits >> 24);
    }

    /** @return true after the first {@link #buildTiles}. */
    public boolean isBuilt() {
        return this.built;
    }

    /** @return uploaded grid vertex count, 0 before the first {@link #buildTiles}. */
    public int tileVertexCount() {
        return this.tileVertexCount;
    }

    /** @return tile buffer, null before the first {@link #buildTiles}. */
    public LwjglElementArrayBuffer tileBuffer() {
        return this.buffers.tileBuffer();
    }

    /** @return ground flags held for the layer step. */
    public int groundFlags() {
        return this.groundFlags;
    }

    /** @return feature flags held for the lighting step. */
    public int featureFlags() {
        return this.featureFlags;
    }

    private int heightAt(int x, int z) {
        if (this.tileHeights == null || this.tileHeights.length == 0) {
            return 0;
        }
        int cx = x < 0 ? 0 : (x >= this.tileHeights.length ? this.tileHeights.length - 1 : x);
        int[] column = this.tileHeights[cx];
        if (column == null || column.length == 0) {
            return 0;
        }
        int cz = z < 0 ? 0 : (z >= column.length ? column.length - 1 : z);
        return column[cz];
    }

    @Override
    public int l(int x, int z) {
        return this.heightAt(x, z);
    }

    @Override
    public int a(int x, int z) {
        return this.heightAt(x, z);
    }

    @Override
    public void ca() {
    }

    @Override
    public void method4028(int arg0, int arg1) {
    }

    @Override
    public void i(int x, int z, int level) {
        if (x < 0 || z < 0 || x >= this.shadowLevels.length || z >= this.shadowLevels[0].length) {
            return;
        }
        if (level > (this.shadowLevels[x][z] & 0xFF)) {
            this.shadowLevels[x][z] = (byte) level;
        }
    }

    @Override
    public Shadow k(int arg0, int arg1, Shadow arg2) {
        return null;
    }

    @Override
    public boolean method4022(Shadow arg0, int arg1, int arg2, int arg3) {
        return false;
    }

    @Override
    public void method4024(PointLight arg0, int[] arg1) {
    }

    @Override
    public void b(Shadow arg0, int arg1, int arg2, int arg3, int arg4, boolean arg5) {
    }

    @Override
    public void p(Shadow arg0, int arg1, int arg2, int arg3, int arg4, boolean arg5) {
    }

    @Override
    public void method4023(int arg0, int arg1, int[] arg2, int[] arg3, int[] arg4, int[] arg5, int[] arg6,
        int[] arg7, int[] arg8, int[] arg9, int[] arg10, int[] arg11, int[] arg12, int arg13, int arg14, int arg15) {
        throw new RuntimeException();
    }

    /**
     * Renders the tile grid, mirroring the {@code GlGround.method4025}
     * head state: lighting uploads (enabled only when
     * {@code (featureFlags & 0x7) != 0}, like the legacy
     * {@code method1906} gate), blending off ({@code method1958(0)}),
     * and the idle material path ({@code method1957(-2)} plus null
     * texture plus combine preset 0, i.e. {@code bindMaterial(-1)}).
     * The grid draws non-indexed; per-layer index expansion lands with
     * the layer step.
     */
    @Override
    public void method4025(int arg0, int arg1, int arg2, int arg3, boolean[][] arg4) {
        if (!this.built) {
            this.buildTiles();
        }
        LwjglElementArrayBuffer tiles = this.buffers.tileBuffer();
        if (tiles == null) {
            return;
        }
        this.toolkit.lighting().upload();
        if ((this.featureFlags & 0x7) == 0) {
            this.toolkit.frameOps().disableCap(LwjglLighting.CAP_LIGHTING);
        } else {
            this.toolkit.frameOps().enableCap(LwjglLighting.CAP_LIGHTING);
        }
        this.toolkit.setBlendMode(LwjglToolkit.BLEND_NONE);
        this.toolkit.bindMaterial(-1, false);
        tiles.bind();
        this.toolkit.drawOps().drawArrays(LwjglModel.MODE_TRIANGLES, 0, this.tileVertexCount);
    }

    @Override
    public void B(int arg0, int arg1, int[] arg2, int[] arg3, int[] arg4, int[] arg5, int[] arg6,
        int[] arg7, int[] arg8, int[] arg9, int arg10, int arg11, int arg12, boolean arg13) {
        throw new RuntimeException();
    }

    @Override
    public void method4029(int arg0, int arg1, int arg2, boolean[][] arg3, boolean arg4) {
        throw new RuntimeException();
    }
}
