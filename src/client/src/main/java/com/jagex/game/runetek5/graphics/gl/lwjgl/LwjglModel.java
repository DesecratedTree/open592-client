package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.scene.ground.Ground;

/**
 * Minimal HD model owned by the LWJGL renderer, replacing the
 * {@code GlModel} role for the buffer-upload and draw steps only.
 *
 * <p>The mesh is encoded on first render: vertex positions pack from the
 * {@code Mesh} register order (242, 244, 251, matching the order the
 * {@code GlModel} decoder reads them in) as little-endian float triples
 * exactly like the stock scratch-buffer path, and face corners pack in
 * stored (117, 119, 120) order. Short indices are used while every corner
 * fits in 16 bits, int indices beyond that. Placement composes the real
 * model matrix ({@link LwjglSceneMatrix#modelMatrix}) with the toolkit
 * view and culls through {@code LwjglToolkit.visible}; winding follows
 * the stored order until the face-cull step lands.
 *
 * <p>Picking, particles and billboards fast-fail: those paths are not
 * ported yet. Queries answer safe defaults
 * ({@code loadedTextures} is true, bounds are zero) and {@code copy}
 * returns this model, which is harmless while every mutating path still
 * fast-fails.
 */
public final class LwjglModel extends Model {

    /** Triangle draw mode (legacy literal 4). */
    public static final int MODE_TRIANGLES = 0x0004;

    /** Unsigned-short index type (legacy literal 5123). */
    public static final int INDEX_USHORT = 0x1403;

    /** Unsigned-int index type (legacy literal 5125). */
    public static final int INDEX_UINT = 0x1405;

    private final LwjglToolkit toolkit;
    private final Mesh mesh;
    private final int functionMask;
    private final int ambient;
    private final int contrast;
    private final int featureMask;
    private final LwjglModelBuffers buffers;
    private final LwjglDrawOps draws;
    private LwjglMaterial material;
    private boolean uploaded;
    private int indexCount;
    private int indexType;
    private float centerX;
    private float centerY;
    private float centerZ;
    private float radius;

    /**
     * @param toolkit owning toolkit; buffers route through its buffer seam
     *     and draws through its draw seam.
     * @param mesh decoded mesh, encoded on first render.
     */
    public LwjglModel(LwjglToolkit toolkit, Mesh mesh, int functionMask, int ambient, int contrast, int featureMask) {
        if (toolkit == null || mesh == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.mesh = mesh;
        this.functionMask = functionMask;
        this.ambient = ambient;
        this.contrast = contrast;
        this.featureMask = featureMask;
        this.buffers = toolkit.createModelBuffers();
        this.draws = toolkit.drawOps();
    }

    /** Attaches a material, applied on every render. Null detaches. */
    public void setMaterial(LwjglMaterial material) {
        this.material = material;
    }

    /** @return attached material, null when untextured. */
    public LwjglMaterial material() {
        return this.material;
    }

    /**
     * Packs a mesh into render bytes: float-3 positions in {@code Mesh}
     * register order and face corners in stored order.
     */
    public static LwjglMeshData encode(Mesh mesh) {
        if (mesh == null) {
            throw new RuntimeException();
        }
        int verts = mesh.anInt3668;
        int faces = mesh.anInt3686;
        if (verts <= 0 || faces <= 0) {
            throw new RuntimeException();
        }
        if (mesh.anIntArray242 == null || mesh.anIntArray244 == null || mesh.anIntArray251 == null
            || mesh.aShortArray117 == null || mesh.aShortArray119 == null || mesh.aShortArray120 == null
            || mesh.anIntArray242.length < verts || mesh.anIntArray244.length < verts
            || mesh.anIntArray251.length < verts || mesh.aShortArray117.length < faces
            || mesh.aShortArray119.length < faces || mesh.aShortArray120.length < faces) {
            throw new RuntimeException();
        }
        byte[] vertices = new byte[verts * 12];
        int minX = mesh.anIntArray242[0];
        int maxX = minX;
        int minY = mesh.anIntArray244[0];
        int maxY = minY;
        int minZ = mesh.anIntArray251[0];
        int maxZ = minZ;
        for (int i = 0; i < verts; i++) {
            int x = mesh.anIntArray242[i];
            int y = mesh.anIntArray244[i];
            int z = mesh.anIntArray251[i];
            putFloat(vertices, i * 12, x);
            putFloat(vertices, i * 12 + 4, y);
            putFloat(vertices, i * 12 + 8, z);
            if (x < minX) {
                minX = x;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (y > maxY) {
                maxY = y;
            }
            if (z < minZ) {
                minZ = z;
            }
            if (z > maxZ) {
                maxZ = z;
            }
        }
        float centerX = (float) (minX + maxX) / 2.0F;
        float centerY = (float) (minY + maxY) / 2.0F;
        float centerZ = (float) (minZ + maxZ) / 2.0F;
        float radius = 0.0F;
        for (int i = 0; i < verts; i++) {
            float dx = (float) mesh.anIntArray242[i] - centerX;
            float dy = (float) mesh.anIntArray244[i] - centerY;
            float dz = (float) mesh.anIntArray251[i] - centerZ;
            float dist = (float) Math.sqrt((double) (dx * dx + dy * dy + dz * dz));
            if (dist > radius) {
                radius = dist;
            }
        }
        int type = verts > 65535 ? INDEX_UINT : INDEX_USHORT;
        int corners = faces * 3;
        byte[] indices;
        if (type == INDEX_USHORT) {
            indices = new byte[corners * 2];
            for (int f = 0; f < faces; f++) {
                putShort(indices, f * 6, mesh.aShortArray117[f]);
                putShort(indices, f * 6 + 2, mesh.aShortArray119[f]);
                putShort(indices, f * 6 + 4, mesh.aShortArray120[f]);
            }
        } else {
            indices = new byte[corners * 4];
            for (int f = 0; f < faces; f++) {
                putInt(indices, f * 12, mesh.aShortArray117[f] & 0xFFFF);
                putInt(indices, f * 12 + 4, mesh.aShortArray119[f] & 0xFFFF);
                putInt(indices, f * 12 + 8, mesh.aShortArray120[f] & 0xFFFF);
            }
        }
        return new LwjglMeshData(vertices, indices, type, corners, centerX, centerY, centerZ, radius);
    }

    private static void putFloat(byte[] out, int at, int value) {
        putInt(out, at, Float.floatToIntBits((float) value));
    }

    private static void putShort(byte[] out, int at, short value) {
        out[at] = (byte) value;
        out[at + 1] = (byte) (value >> 8);
    }

    private static void putInt(byte[] out, int at, int value) {
        out[at] = (byte) value;
        out[at + 1] = (byte) (value >> 8);
        out[at + 2] = (byte) (value >> 16);
        out[at + 3] = (byte) (value >> 24);
    }

    /** Uploads the encoded mesh once, on first render. */
    public void ensureUploaded() {
        if (this.uploaded) {
            return;
        }
        LwjglMeshData data = encode(this.mesh);
        this.buffers.uploadVertex(data.vertexBytes(), data.vertexSize(), true);
        this.buffers.uploadIndex(data.indexBytes(), data.indexSize(), data.indexType(), true);
        this.indexCount = data.indexCount();
        this.indexType = data.indexType();
        this.centerX = data.centerX();
        this.centerY = data.centerY();
        this.centerZ = data.centerZ();
        this.radius = data.radius();
        this.uploaded = true;
    }

    /** @return true after the first upload. */
    public boolean isUploaded() {
        return this.uploaded;
    }

    /** @return decoded mesh backing this model. */
    public Mesh mesh() {
        return this.mesh;
    }

    @Override
    public void render(Ground ground, BoundingBoxPolygon boundingBox, int flags) {
        this.ensureUploaded();
        if (this.indexCount <= 0) {
            return;
        }
        float[] model = LwjglSceneMatrix.modelMatrix(ground);
        float wx = model[0] * this.centerX + model[4] * this.centerY + model[8] * this.centerZ + model[12];
        float wy = model[1] * this.centerX + model[5] * this.centerY + model[9] * this.centerZ + model[13];
        float wz = model[2] * this.centerX + model[6] * this.centerY + model[10] * this.centerZ + model[14];
        float[] view = this.toolkit.viewMatrix();
        float ex = view[0] * wx + view[4] * wy + view[8] * wz + view[12];
        float ey = view[1] * wx + view[5] * wy + view[9] * wz + view[13];
        float ez = view[2] * wx + view[6] * wy + view[10] * wz + view[14];
        if (!this.toolkit.visible(ex, ey, ez, this.radius)) {
            return;
        }
        this.toolkit.lighting().upload();
        this.toolkit.frameOps().enableCap(LwjglLighting.CAP_LIGHTING);
        if (this.material != null) {
            this.material.apply(this.toolkit);
        }
        this.toolkit.pushModelMatrix(model);
        this.buffers.vertexBuffer().bind();
        this.buffers.indexBuffer().bind();
        this.draws.drawElements(MODE_TRIANGLES, this.indexCount, this.indexType, 0L);
        this.toolkit.popModelMatrix();
    }

    @Override
    public void renderOrtho(Ground ground, BoundingBoxPolygon boundingBox, int flags) {
        this.render(ground, boundingBox, flags);
    }

    @Override
    public void uploadToGpu() {
        this.ensureUploaded();
    }

    @Override
    public Model copy(byte copyType, int functionMask, boolean ensureLit) {
        return this;
    }

    @Override
    public Shadow fa(Shadow shadow) {
        return shadow;
    }

    @Override
    public boolean loadedTextures() {
        return true;
    }

    @Override
    public boolean picked(int x, int y, Ground ground, boolean quick) {
        return false;
    }

    @Override
    public int setOffsets() {
        return 0;
    }

    @Override
    public int PA() {
        return 0;
    }

    @Override
    public int P() {
        return 0;
    }

    @Override
    public int HA() {
        return 0;
    }

    @Override
    public int NA() {
        return 0;
    }

    @Override
    public int WA() {
        return 0;
    }

    @Override
    public int D() {
        return 0;
    }

    @Override
    public int U() {
        return 0;
    }

    @Override
    public int l() {
        return 0;
    }

    @Override
    public int addLight() {
        return 0;
    }

    @Override
    public EmissiveTriangle[] getEmissiveTriangles() {
        return null;
    }

    @Override
    public EffectiveVertex[] getEffectiveVertices() {
        return null;
    }

    @Override
    public void Z(int arg0) {
        throw new RuntimeException();
    }

    @Override
    public void drawScaledColored(int arg0, int arg1, int arg2) {
        throw new RuntimeException();
    }

    @Override
    public void E(int ambient) {
        throw new RuntimeException();
    }

    @Override
    public void IA() {
    }

    @Override
    public void shareLight(Model model, int x, int y, int z, boolean renderTypeAdjustment) {
        throw new RuntimeException();
    }

    @Override
    public void copyAlpha(short arg0, short arg1) {
        throw new RuntimeException();
    }

    @Override
    public void R(int arg0) {
        throw new RuntimeException();
    }

    @Override
    public void adjustColours(int hue, int saturation, int lightness, int opacity) {
        throw new RuntimeException();
    }

    @Override
    public void c(int angle) {
        throw new RuntimeException();
    }

    @Override
    public void h(int contourType, int contourModifier, Class65 arg2, Class65 arg3, int x, int y, int z) {
        throw new RuntimeException();
    }

    @Override
    public void aa(int contrast) {
        throw new RuntimeException();
    }

    @Override
    public void UA(int functionMask) {
        throw new RuntimeException();
    }

    @Override
    public void H(int scaleX, int scaleY, int scaleZ) {
        throw new RuntimeException();
    }

    @Override
    public void Q(int angle) {
        throw new RuntimeException();
    }

    @Override
    public void I(short src, short dst) {
        throw new RuntimeException();
    }

    @Override
    protected void applyTransformUnmasked(int type, int[] labels, int x, int y, int z, int rotation, boolean lights) {
        throw new RuntimeException();
    }

    @Override
    protected void M(int type, int[] labels, int x, int y, int z, boolean lights, int submesh, int[] matrix) {
        throw new RuntimeException();
    }

    @Override
    protected boolean w() {
        return false;
    }

    @Override
    protected void scaleWidth() {
        throw new RuntimeException();
    }

    @Override
    protected void N(int type, int x, int y, int z) {
        throw new RuntimeException();
    }
}
