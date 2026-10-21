package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Material owned by the LWJGL renderer, replacing the per-group material
 * step in the {@code GlModel}/{@code method1926} path.
 *
 * <p>Two shapes exist. The id shape ({@link #LwjglMaterial(int, boolean)})
 * mirrors {@code method1938}/{@code method1926} exactly: applying
 * delegates to {@link LwjglToolkit#bindMaterial(int, boolean)}, which
 * resolves the cached texture, scrolls the texture matrix, selects the
 * effect and either lets the effect consume the binding or binds unit 0
 * with the material's combine preset. The direct shape
 * ({@link #LwjglMaterial(LwjglTexture2D, int)}) keeps the early single-
 * texture behaviour for tests and untextured-lit geometry: unit 0 takes
 * the texture with opaque white and standard alpha blending through
 * {@link LwjglToolkit#applyCombine}.
 */
public final class LwjglMaterial {

    private final LwjglTexture2D texture;
    private final int combineMode;
    private final int materialId;
    private final boolean lit;

    /**
     * @param texture diffuse texture, bound on unit 0 when applied.
     * @param combineMode combine preset, 0-4 like the legacy path.
     */
    public LwjglMaterial(LwjglTexture2D texture, int combineMode) {
        if (texture == null || combineMode < 0 || combineMode > 4) {
            throw new RuntimeException();
        }
        this.texture = texture;
        this.combineMode = combineMode;
        this.materialId = -1;
        this.lit = true;
    }

    /**
     * @param materialId material id resolved through the toolkit cache.
     * @param lit true when the geometry carries normals.
     */
    public LwjglMaterial(int materialId, boolean lit) {
        this.texture = null;
        this.combineMode = 0;
        this.materialId = materialId;
        this.lit = lit;
    }

    /** @return diffuse texture, null for the id shape. */
    public LwjglTexture2D texture() {
        return this.texture;
    }

    /** @return combine preset (direct shape only). */
    public int combineMode() {
        return this.combineMode;
    }

    /** @return material id, -1 for the direct shape. */
    public int materialId() {
        return this.materialId;
    }

    /** @return lit flag (id shape only). */
    public boolean lit() {
        return this.lit;
    }

    /** Binds the material: id shape through the toolkit graph, direct shape on unit 0. */
    public void apply(LwjglToolkit toolkit) {
        if (toolkit == null) {
            throw new RuntimeException();
        }
        if (this.materialId >= 0) {
            toolkit.bindMaterial(this.materialId, this.lit);
            return;
        }
        toolkit.selectTextureUnit(0);
        toolkit.bindTexture(this.texture);
        toolkit.applyCombine(this.combineMode, 0xFFFFFFFF, LwjglToolkit.BLEND_ALPHA);
    }
}
