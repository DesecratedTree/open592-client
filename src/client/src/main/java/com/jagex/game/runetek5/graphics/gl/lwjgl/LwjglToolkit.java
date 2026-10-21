package com.jagex.game.runetek5.graphics.gl.lwjgl;

import java.awt.Canvas;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Hashtable;

import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.unpack_Sub1;

/**
 * Always-on high-detail toolkit backed by LWJGL.
 *
 * <p>This is the seam every later renderer port step fills in: it exposes
 * the full {@link Toolkit} surface with the same signatures the game calls
 * on {@code GlToolkit}, so call sites stay untouched while the internals
 * move from the legacy {@code jaclib/jaggl} natives to LWJGL. Capability
 * queries mirror the legacy HD values; models and grounds upload through
 * the buffer seams and issue draws, while particle and effect sub-passes
 * stay no-ops until their port step lands.
 *
 * <p>Threading mirrors the legacy native path: the game thread owns GL.
 * {@link LwjglCanvas#attachGameThread()} makes the context current on the
 * game thread at install time and leaves it there, so uploads, draws and
 * the per-frame present may issue GL from any toolkit method on that
 * thread. AWT paint events never issue GL.
 *
 * <p>Construction throws {@link RuntimeException} when the LWJGL runtime
 * is unavailable, so the existing {@code try/catch} in the toolkit
 * selection path falls back to the Java software renderer.
 */
public final class LwjglToolkit extends Toolkit {

    /** Test hook forcing {@link #isAvailable()} to false. Never set in game code. */
    private static boolean unavailableForTest;

    private final Canvas hostCanvas;
    private final int multisamplePreference;
    private final LwjglCanvas glCanvas;
    private final LwjglStateCache stateCache = new LwjglStateCache();
    private final Hashtable canvasMap = new Hashtable();

    private LwjglCanvas currentCanvas;
    private Canvas currentHost;
    /** GL canvas currently placed in the host hierarchy; evicted on reinstall. */
    private LwjglCanvas installedCanvas;
    private boolean attached;
    private Thread boundThread;
    private MemoryPool heap;
    private int vboBuffersSize;
    private int textureBuffersSize;
    private int renderBuffersSize;
    private int textureSize = 128;
    private LwjglTextureOps textureOps = LwjglTextureGl.get();
    private LwjglFrameOps frameOps = LwjglFrameGl.get();
    private LwjglGlOps glOps = LwjglGl.get();
    private LwjglListOps listOps = LwjglListGl.get();
    private LwjglDrawOps drawOps = LwjglDrawGl.get();
    private LwjglLightOps lightOps = LwjglLightGl.get();
    private final LwjglLighting lighting;
    private Ground sharedGround;
    private Ground sceneGround;
    private float frustumMinX = -1.0F;
    private float frustumMaxX = 1.0F;
    private float frustumMinY = -1.0F;
    private float frustumMaxY = 1.0F;
    private final float[] viewMatrix = new float[] {
        1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
        0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
    private final float[] projectionMatrix = LwjglSceneMatrix.identity();
    private final LwjglFramebuffer[] frameStack = new LwjglFramebuffer[4];
    private int frameDepth = -1;
    private final LwjglTexture[] unitTextures = new LwjglTexture[LwjglStateCache.MAX_TEXTURE_UNITS];
    private int activeUnit;
    private int combineRgb0 = LwjglCombine.COMBINE_MODULATE;
    private int combineAlpha0 = LwjglCombine.COMBINE_MODULATE;
    private boolean textureMatrixDirty;
    private int blendMode;
    private int tickTime;
    private LwjglTextureCache textureCache;
    private final LwjglEffectManager effects;

    private int zNear = 50;
    private int zFar = 3584;
    private int effectiveFar = 3584;
    private int projectionCenterX;
    private int projectionCenterY;
    private int projectionScaleX = 512;
    private int projectionScaleY = 512;
    private int viewportWidth;
    private int viewportHeight;
    private int clipLeft;
    private int clipTop;
    private int clipRight;
    private int clipBottom;
    private boolean clipSet;
    private float zoomFactor = 1.0F;
    private int zaCounter;
    private int wCounter;

    /**
     * @param index toolkit slot, as for the legacy toolkits.
     * @param canvas host canvas from the shell (may be null in tests).
     * @param provider material provider, may be null until wired.
     * @param multisamples multisample preference forwarded to the GL context.
     * @param sign signlink, unused by this renderer.
     */
    public LwjglToolkit(int index, Canvas canvas, unpack_Sub1 provider, int multisamples, SignLink sign) {
        super(index, provider);
        if (!isAvailable()) {
            throw new RuntimeException("LWJGL runtime unavailable");
        }
        this.hostCanvas = canvas;
        this.multisamplePreference = LwjglConfig.samplesForPreference(multisamples);
        try {
            this.glCanvas = new LwjglCanvas(LwjglConfig.newGLData(this.multisamplePreference));
        } catch (Throwable failure) {
            throw new RuntimeException("LWJGL canvas unavailable", failure);
        }
        this.currentCanvas = this.glCanvas;
        this.currentHost = canvas;
        if (canvas != null) {
            this.canvasMap.put(canvas, this.glCanvas);
            this.installHostCanvas(canvas, this.glCanvas);
        }
        this.lighting = new LwjglLighting(this);
        this.effects = new LwjglEffectManager(this);
    }

    /**
     * Factory mirroring the legacy {@code Class27_Sub4.method3941} shape so
     * the HD slot in {@code Toolkit.method4241} keeps a single call site.
     */
    public static LwjglToolkit create(int index, Canvas canvas, unpack_Sub1 provider, int multisamples, SignLink sign) {
        return new LwjglToolkit(index, canvas, provider, multisamples, sign);
    }

    /**
     * @return true when the LWJGL core, OpenGL bindings and AWT bridge load.
     * Creates no windows, peers or contexts, so it is headless-safe.
     */
    public static boolean isAvailable() {
        return !unavailableForTest && LwjglConfig.isRuntimePresent();
    }

    /** Package-private test hook forcing construction to fail (fallback path). */
    static void setUnavailableForTest(boolean unavailable) {
        unavailableForTest = unavailable;
    }

    /** @return the GL state change cache owned by this toolkit. */
    public LwjglStateCache stateCache() {
        return this.stateCache;
    }

    /**
     * Binds the calling thread for GL work, mirroring the legacy attach
     * contract (the context is made current per render, not held).
     *
     * @return always true; throws only when the runtime is unavailable.
     */
    public boolean attach() {
        this.attached = true;
        this.boundThread = Thread.currentThread();
        return true;
    }

    /** Releases the thread binding. Idempotent. @return always true. */
    public boolean detach() {
        this.attached = false;
        this.boundThread = null;
        return true;
    }

    /** @return true after {@link #attach()} until {@link #detach()}. */
    public boolean isAttached() {
        return this.attached;
    }

    /**
     * Adjusts the tracked GPU buffer bytes, mirroring the legacy
     * {@code vboBuffersSize} accounting behind {@link #IA()}.
     */
    void trackVbo(int delta) {
        this.vboBuffersSize += delta;
    }

    /**
     * Adjusts the tracked texture bytes, mirroring the legacy
     * {@code textureBuffersSize} accounting behind {@link #IA()}.
     */
    void trackTexture(int delta) {
        this.textureBuffersSize += delta;
    }

    /**
     * Adjusts the tracked renderbuffer bytes, mirroring the legacy
     * {@code renderBuffersSize} accounting behind {@link #IA()}.
     */
    void trackRenderbuffer(int delta) {
        this.renderBuffersSize += delta;
    }

    /** Package-private seam override so tests can record GL calls. */
    void setFrameOpsForTest(LwjglFrameOps ops) {
        this.frameOps = ops;
    }

    /** Package-private seam override so tests can record buffer calls. */
    void setGlOpsForTest(LwjglGlOps ops) {
        this.glOps = ops;
    }

    /** Package-private seam override so tests can record list calls. */
    void setListOpsForTest(LwjglListOps ops) {
        this.listOps = ops;
    }

    /** Package-private seam override so tests can record draw calls. */
    void setDrawOpsForTest(LwjglDrawOps ops) {
        this.drawOps = ops;
    }

    /** @return the draw seam backing scene rendering. */
    public LwjglDrawOps drawOps() {
        return this.drawOps;
    }

    /** Package-private seam override so tests can record light calls. */
    void setLightOpsForTest(LwjglLightOps ops) {
        this.lightOps = ops;
    }

    /** @return the light seam backing fixed-function lighting. */
    public LwjglLightOps lightOps() {
        return this.lightOps;
    }

    /** @return the fixed-function light state. */
    public LwjglLighting lighting() {
        return this.lighting;
    }

    /** @return the post and water effect selection. */
    public LwjglEffectManager effects() {
        return this.effects;
    }

    /** @return the buffer seam backing vertex and index uploads. */
    public LwjglGlOps glOps() {
        return this.glOps;
    }

    /** @return the display-list seam backing render lists. */
    public LwjglListOps listOps() {
        return this.listOps;
    }

    /**
     * Creates lazy model vertex/index uploads backed by the buffer seam,
     * replacing the {@code GlModel} upload sites for this renderer.
     */
    public LwjglModelBuffers createModelBuffers() {
        return new LwjglModelBuffers(this, this.glOps);
    }

    /**
     * Creates a ground tile index upload backed by the buffer seam,
     * replacing the {@code GlGround} tile path for this renderer.
     */
    public LwjglGroundBuffers createGroundBuffers() {
        return new LwjglGroundBuffers(this, this.glOps);
    }

    /**
     * Reserves a display-list range backed by the list seam, replacing
     * {@code GlRenderList} for this renderer.
     */
    public LwjglRenderList createRenderList(int range) {
        return new LwjglRenderList(this.listOps, range);
    }

    /** @return the frame seam backing framebuffer objects. */
    public LwjglFrameOps frameOps() {
        return this.frameOps;
    }

    /** @return the texture seam backing textures and programs. */
    public LwjglTextureOps textureOps() {
        return this.textureOps;
    }

    /** @return multisample count for renderbuffer storage, 1 when off. */
    public int multisampleSamples() {
        return this.multisamplePreference;
    }

    /**
     * Pushes a framebuffer target, binding it and unbinding the previous
     * top. Mirrors the {@code GlToolkit.method1922} stack discipline:
     * depth is capped at four and popping a non-top target throws.
     */
    public void pushFramebuffer(LwjglFramebuffer target) {
        if (target == null) {
            throw new RuntimeException();
        }
        if (this.frameDepth >= 3) {
            throw new RuntimeException();
        }
        if (this.frameDepth >= 0) {
            this.frameStack[this.frameDepth].unbind();
        }
        this.frameStack[++this.frameDepth] = target;
        target.bind();
    }

    /**
     * Pops a framebuffer target, unbinding it and rebinding the new top
     * when the stack is non-empty. The popped target must be on top.
     */
    public void popFramebuffer(LwjglFramebuffer target) {
        if (this.frameDepth < 0 || this.frameStack[this.frameDepth] != target) {
            throw new RuntimeException();
        }
        this.frameStack[this.frameDepth--] = null;
        target.unbind();
        if (this.frameDepth >= 0) {
            this.frameStack[this.frameDepth].bind();
        }
    }

    /**
     * Sets the viewport rectangle, mirroring the
     * {@code GlToolkit.method1931}/{@code method1950} pair with explicit
     * dimensions instead of cached canvas state.
     */
    public void setViewport(int x, int y, int width, int height) {
        this.viewportWidth = width;
        this.viewportHeight = height;
        this.frameOps.viewport(x, y, width, height);
        this.updateProjection();
        this.updateFrustum();
    }

    /** @return the material texture edge length, mirroring the legacy size. */
    public int textureSize() {
        return this.textureSize;
    }

    /** Package-private seam override so tests can record GL calls. */
    void setTextureOpsForTest(LwjglTextureOps ops) {
        this.textureOps = ops;
    }

    /**
     * Selects the active texture unit, mirroring
     * {@code GlToolkit.method1936}. Out-of-range units throw, matching the
     * state cache contract.
     */
    public void selectTextureUnit(int unit) {
        if (this.stateCache.useUnit(unit)) {
            this.textureOps.activeTexture(unit);
        }
        this.activeUnit = unit;
    }

    /** Blend off: blending is disabled (legacy {@code method1958} mode 0). */
    public static final int BLEND_NONE = LwjglCombine.BLEND_NONE;

    /** Standard alpha blending (legacy {@code method1958} mode 1). */
    public static final int BLEND_ALPHA = LwjglCombine.BLEND_ALPHA;

    /** Additive blending (legacy {@code method1958} mode 2). */
    public static final int BLEND_ADD = LwjglCombine.BLEND_ADD;

    /** Modulate blending (legacy {@code method1958} mode 128). */
    public static final int BLEND_MULTIPLY = LwjglCombine.BLEND_MULTIPLY;

    /** Blend capability (legacy literal 3042). */
    public static final int CAP_BLEND = 0x0BE2;

    /** Alpha-test capability (legacy literal 3008). */
    public static final int CAP_ALPHA_TEST = LwjglCombine.CAP_ALPHA_TEST;

    /** Source-alpha blend factor (legacy literal 770). */
    public static final int BLEND_SRC_ALPHA = 0x0302;

    /** One-minus-source-alpha blend factor (legacy literal 771). */
    public static final int BLEND_ONE_MINUS_SRC_ALPHA = 0x0303;

    /** Texture unit 0 combiner (legacy {@code GL_TEXTURE0}, 33984). */
    public static final int UNIT_TEXCOORD_0 = 0x84C0;

    /** Texture unit 1 combiner (legacy {@code GL_TEXTURE1}, 33985). */
    public static final int UNIT_TEXCOORD_1 = 0x84C1;

    /**
     * Sets the blend factors, emitting only on change like the texture
     * unit cache.
     */
    public void setBlend(int src, int dst) {
        if (this.stateCache.setBlendFunc(src, dst)) {
            this.frameOps.blendFunc(src, dst);
        }
    }

    /**
     * Installs the full legacy blend state for a {@code method1958}
     * mode (0, 1, 2 or 128): alpha-blend factors plus the alpha-test
     * rule (enabled for modes 1 and 128). Unknown modes throw.
     */
    public void setBlendMode(int blendMode) {
        int[] factors = LwjglCombine.blendFactors(blendMode);
        if (LwjglCombine.alphaTestEnabled(blendMode)) {
            this.frameOps.enableCap(CAP_BLEND);
            this.frameOps.enableCap(CAP_ALPHA_TEST);
            this.setBlend(factors[0], factors[1]);
        } else if (blendMode == BLEND_NONE) {
            this.frameOps.disableCap(CAP_BLEND);
            this.frameOps.disableCap(CAP_ALPHA_TEST);
        } else {
            this.frameOps.enableCap(CAP_BLEND);
            this.frameOps.disableCap(CAP_ALPHA_TEST);
            this.setBlend(factors[0], factors[1]);
        }
        this.blendMode = blendMode;
    }

    /** @return the last blend mode passed to {@link #setBlendMode}. */
    public int blendMode() {
        return this.blendMode;
    }

    /**
     * Installs an RGB/alpha combiner pair, mirroring
     * {@code GlToolkit.method1912}: unit 0 caches each half and emits
     * only on change, other units emit the pair directly.
     */
    public void setCombineRgbAlpha(int rgbOp, int alphaOp) {
        if (this.activeUnit != 0) {
            this.textureOps.texEnvi(LwjglCombine.COMBINE_RGB, rgbOp);
            this.textureOps.texEnvi(LwjglCombine.COMBINE_ALPHA, alphaOp);
            return;
        }
        if (rgbOp != this.combineRgb0) {
            this.textureOps.texEnvi(LwjglCombine.COMBINE_RGB, rgbOp);
            this.combineRgb0 = rgbOp;
        }
        if (alphaOp != this.combineAlpha0) {
            this.textureOps.texEnvi(LwjglCombine.COMBINE_ALPHA, alphaOp);
            this.combineAlpha0 = alphaOp;
        }
    }

    /**
     * Installs a 2D combine preset, mirroring the {@code method1900}
     * table exactly: 0 to (8448, 8448), 1 to (7681, 7681), 2 to
     * (34165, 7681), 3 to (260, 8448), 4 to (34023, 34023). Unknown
     * presets throw.
     */
    public void setCombine(int combineMode) {
        this.setCombineRgbAlpha(LwjglCombine.rgbOp(combineMode), LwjglCombine.alphaOp(combineMode));
    }

    /**
     * Installs the texture-environment colour from packed ARGB,
     * mirroring {@code GlToolkit.method1901}.
     */
    public void setTexEnvColor(int colorArgb) {
        this.textureOps.texEnvColor((float) (colorArgb >> 16 & 0xFF) / 255.0F,
            (float) (colorArgb >> 8 & 0xFF) / 255.0F, (float) (colorArgb & 0xFF) / 255.0F,
            (float) (colorArgb >>> 24) / 255.0F);
    }

    /**
     * Installs the texture-environment colour from components,
     * mirroring {@code GlToolkit.method1913} including its shuffled
     * upload order.
     */
    public void setTexEnvColor4f(float a, float b, float c, float d) {
        this.textureOps.texEnvColor(d, a, c, b);
    }

    /**
     * Installs a per-unit combiner/source pair, mirroring
     * {@code GlToolkit.method1945}: {@code texEnvi(unit + 34176,
     * combiner)} plus {@code texEnvi(unit + 34192, operand)}.
     */
    public void setTexCombine(int unit, int operand, int combiner) {
        this.textureOps.texEnvi(LwjglCombine.COMBINE_UNIT_BASE + unit, combiner);
        this.textureOps.texEnvi(LwjglCombine.SOURCE_UNIT_BASE + unit, operand);
    }

    /**
     * Installs a texture-coordinate generation pair, mirroring
     * {@code GlToolkit.method1954}: {@code texEnvi(unit + 34184, mode)}
     * plus {@code texEnvi(unit + 34200, 770)}.
     */
    public void setTexCoordGen(int mode, int unit) {
        this.textureOps.texEnvi(LwjglCombine.TEXGEN_UNIT_BASE + unit, mode);
        this.textureOps.texEnvi(LwjglCombine.TEXSRC_UNIT_BASE + unit, 770);
    }

    /**
     * Installs a 2D combine preset with an ARGB tint and a legacy blend
     * mode, mirroring the {@code SpriteGL} draw path: the colour tints
     * through the colour seam, the preset installs the exact
     * {@code method1900} combiner pair, and the mode installs the full
     * {@code method1958} blend state.
     */
    public void applyCombine(int combineMode, int colorArgb, int blendMode) {
        if (combineMode < 0 || combineMode > 4) {
            throw new RuntimeException();
        }
        this.frameOps.color4f((float) (colorArgb >> 16 & 0xFF) / 255.0F,
            (float) (colorArgb >> 8 & 0xFF) / 255.0F, (float) (colorArgb & 0xFF) / 255.0F,
            (float) (colorArgb >>> 24) / 255.0F);
        this.setCombine(combineMode);
        this.setBlendMode(blendMode);
    }

    /**
     * Multiplies the current matrix with a model placement, mirroring
     * {@code method1948}: pushes first, multiplies second. Pair every call
     * with {@link #popModelMatrix}.
     */
    public void pushModelMatrix(float[] matrix) {
        if (matrix == null || matrix.length != 16) {
            throw new RuntimeException();
        }
        this.frameOps.pushMatrix();
        this.frameOps.multMatrixf(matrix);
    }

    /** Pops the model placement pushed by {@link #pushModelMatrix}. */
    public void popModelMatrix() {
        this.frameOps.popMatrix();
    }

    /**
     * Records the ortho zoom factor, mirroring {@code method1937}. The
     * orthographic projection it feeds stays with the present step.
     */
    public void setZoom(float zoom) {
        this.zoomFactor = zoom;
    }

    /** @return the last zoom factor passed to {@link #setZoom}. */
    public float zoomFactor() {
        return this.zoomFactor;
    }

    /**
     * Multiplies the current matrix, mirroring the bare
     * {@code glMultMatrixf} part of {@code method1948} for callers that
     * manage the stack themselves.
     */
    public void loadMatrix(float[] matrix) {
        if (matrix == null || matrix.length != 16) {
            throw new RuntimeException();
        }
        this.frameOps.multMatrixf(matrix);
    }

    /** @return copy of the column-major projection matrix. */
    public float[] projectionMatrix() {
        float[] copy = new float[16];
        System.arraycopy(this.projectionMatrix, 0, copy, 0, 16);
        return copy;
    }

    /** Rebuilds the projection from the H centers/scales and planes. */
    private void updateProjection() {
        LwjglSceneMatrix.projection(this.projectionMatrix, this.projectionCenterX, this.projectionCenterY,
            this.projectionScaleX, this.projectionScaleY, this.viewportWidth, this.viewportHeight,
            this.zNear, this.effectiveFar);
    }

    /**
     * Recomputes the culling frustum from the OA clip rectangle and the H
     * scales. Runs only once a clip rectangle exists, so the NDC default
     * (and any explicit {@link #setFrustum} box) survives until the game
     * installs its real clip rect.
     */
    private void updateFrustum() {
        if (!this.clipSet || this.projectionScaleX <= 0 || this.projectionScaleY <= 0) {
            return;
        }
        float[] clip = LwjglSceneMatrix.clipBounds(this.clipLeft, this.clipTop, this.clipRight,
            this.clipBottom, this.projectionCenterX, this.projectionCenterY);
        float[] frustum = LwjglSceneMatrix.frustumFromClip(clip, this.projectionScaleX,
            this.projectionScaleY);
        this.frustumMinX = frustum[0];
        this.frustumMaxX = frustum[1];
        this.frustumMinY = frustum[2];
        this.frustumMaxY = frustum[3];
    }

    /**
     * Sets the visible frustum box in depth-normalized coordinates,
     * mirroring the legacy clip comparisons structurally. Defaults to
     * the NDC box; the projection step recomputes it from the real
     * matrix later.
     */
    public void setFrustum(float minX, float maxX, float minY, float maxY) {
        this.frustumMinX = minX;
        this.frustumMaxX = maxX;
        this.frustumMinY = minY;
        this.frustumMaxY = maxY;
    }

    /**
     * Installs the view matrix, copying the column-major 16 floats.
     */
    public void setViewMatrix(float[] matrix) {
        if (matrix == null || matrix.length != 16) {
            throw new RuntimeException();
        }
        System.arraycopy(matrix, 0, this.viewMatrix, 0, 16);
    }

    /** @return copy of the column-major view matrix. */
    public float[] viewMatrix() {
        float[] copy = new float[16];
        System.arraycopy(this.viewMatrix, 0, copy, 0, 16);
        return copy;
    }

    /**
     * Tests a camera-space bounding sphere, mirroring the legacy
     * near-plane and clip comparisons: spheres fully behind the near
     * plane cull, and the depth-normalized center must fall inside the
     * frustum box widened by the normalized radius. There is deliberately
     * no far-plane cull: the far plane is a fog-derived limit (see
     * {@code FogDistance}), so beyond-far geometry stays in the
     * projection and is clipped by it. Spheres at or behind the eye cull.
     */
    public boolean visible(float x, float y, float z, float radius) {
        if (z <= 0.0F || z + radius <= (float) this.zNear) {
            return false;
        }
        float inv = 1.0F / z;
        float spread = radius * inv;
        float nx = x * inv;
        float ny = y * inv;
        return nx + spread >= this.frustumMinX && nx - spread <= this.frustumMaxX
            && ny + spread >= this.frustumMinY && ny - spread <= this.frustumMaxY;
    }

    /**
     * Binds a texture on the active unit, mirroring
     * {@code GlToolkit.setActiveTexture}: target transitions emit
     * enable/disable pairs, repeat binds are elided, and null unbinds the
     * unit's current target.
     */
    public void bindTexture(LwjglTexture texture) {
        LwjglTexture current = this.unitTextures[this.activeUnit];
        if (current == texture) {
            return;
        }
        if (texture == null) {
            if (current != null) {
                this.textureOps.disableTarget(current.target());
            }
        } else {
            if (current == null) {
                this.textureOps.enableTarget(texture.target());
            } else if (current.target() != texture.target()) {
                this.textureOps.disableTarget(current.target());
                this.textureOps.enableTarget(texture.target());
            }
            if (this.stateCache.bindTexture(this.activeUnit, texture.id())) {
                this.textureOps.bindTexture(texture.target(), texture.id());
            }
        }
        this.unitTextures[this.activeUnit] = texture;
    }

    /**
     * @return the material texture cache, created on first use. Mirrors
     * the legacy toolkit-owned {@code OpenGLTextureCache}.
     */
    public synchronized LwjglTextureCache textureCache() {
        if (this.textureCache == null) {
            this.textureCache = new LwjglTextureCache(this, this.materialProvider, this.textureOps);
        }
        return this.textureCache;
    }

    @Override
    public void method4236() {
    }

    @Override
    public void setShrinkTextures(boolean shrink) {
    }

    @Override
    public void copyLights(int count, PointLight[] lights) {
        this.lighting.copyLights(count, lights);
    }

    @Override
    protected void stop() {
    }

    @Override
    public void B(float intensity) {
    }

    @Override
    public void AA(int offsetX, int arg1, int arg2, int arg3) {
    }

    @Override
    public boolean hardShadow() {
        return true;
    }

    @Override
    public boolean method4243() {
        return true;
    }

    @Override
    public void setHeap(MemoryPool heap) {
        this.heap = heap;
    }

    @Override
    public boolean method4245() {
        return true;
    }

    /**
     * Scrolls the texture matrix, mirroring {@code GlToolkit.method1963}
     * including its swapped translate arguments: the texture matrix is
     * selected (5890), reset when clean, translated by (v, u, 0), and
     * the mode returns to modelview (5888).
     */
    public void scrollTexture(float u, float v) {
        this.frameOps.matrixMode(LwjglCombine.MATRIX_TEXTURE);
        if (!this.textureMatrixDirty) {
            this.frameOps.loadIdentity();
        }
        this.frameOps.translatef(v, u, 0.0F);
        this.frameOps.matrixMode(LwjglCombine.MATRIX_MODELVIEW);
        this.textureMatrixDirty = true;
    }

    /**
     * Resets the texture matrix, mirroring
     * {@code GlToolkit.method1917}: only emits when dirty.
     */
    public void resetTextureMatrix() {
        if (this.textureMatrixDirty) {
            this.frameOps.matrixMode(LwjglCombine.MATRIX_TEXTURE);
            this.frameOps.loadIdentity();
            this.frameOps.matrixMode(LwjglCombine.MATRIX_MODELVIEW);
            this.textureMatrixDirty = false;
        }
    }

    /**
     * @return canvas height for framebuffer-copy Y flips: the live
     *     viewport height, falling back to the host canvas size.
     */
    public int canvasHeight() {
        if (this.viewportHeight > 0) {
            return this.viewportHeight;
        }
        if (this.hostCanvas != null) {
            return Math.max(0, this.hostCanvas.getHeight());
        }
        return 0;
    }

    /**
     * Binds a full material graph, mirroring
     * {@code GlToolkit.method1926} (untextured branch and textured
     * branch, non-fog path): a negative id resets the texture matrix,
     * unbinds unit 0, installs combine preset 0 and idles the effects;
     * otherwise the cached texture binds with the material's scroll
     * offsets ({@code method1963} with the tick time), the effect
     * manager selects by the material's effect id and params
     * ({@code method5318}/{@code method5319}), and the effect either
     * consumes the binding ({@code method5316}) or unit 0 takes the
     * texture with the material's combine preset. The fog-redirect
     * ({@code aBoolean192}) stays with a later step and always takes the
     * game path here.
     *
     * @param materialId material id, negative for untextured.
     * @param lit true when the geometry carries normals.
     */
    public void bindMaterial(int materialId, boolean lit) {
        if (materialId < 0) {
            this.resetTextureMatrix();
            this.selectTextureUnit(0);
            this.bindTexture(null);
            this.setCombine(0);
            this.effects.select(lit, 0);
            return;
        }
        LwjglTexture2D texture = this.textureCache().texture(materialId);
        MaterialType metrics = this.materialProvider == null ? null : this.materialProvider.getMetrics(materialId);
        if (texture == null || metrics == null) {
            throw new RuntimeException();
        }
        if (metrics.aByte91 != 0 || metrics.aByte93 != 0) {
            int size = metrics.isSmall ? 64 : this.textureSize;
            int period = size * 50;
            this.scrollTexture((float) (metrics.aByte93 * (this.tickTime % period)) / (float) period,
                (float) (metrics.aByte91 * (this.tickTime % period)) / (float) period);
        } else {
            this.resetTextureMatrix();
        }
        this.effects.select(lit, metrics.aByte89 & 0xFF);
        this.effects.setEffectParams(metrics.anInt6933, metrics.aByte88);
        if (!this.effects.bindForEffect(texture, metrics.anInt6934)) {
            this.selectTextureUnit(0);
            this.bindTexture(texture);
            this.setCombine(metrics.anInt6934);
        }
    }

    /** @return last tick time in millis, driving effect binds. */
    public int tickTime() {
        return this.tickTime;
    }

    @Override
    public void tick(int time) {
        this.tickTime = time;
        if (this.textureCache != null) {
            this.textureCache.trim();
        }
    }

    @Override
    public void method4247() {
        if (this.currentCanvas != null) {
            try {
                // Re-attach heals a context lost to canvas re-homing; the
                // present itself only swaps, since the scene was drawn by
                // the toolkit before this step.
                this.currentCanvas.attachGameThread();
                this.currentCanvas.presentFrame();
            } catch (RuntimeException ignored) {
            }
        }
    }

    @Override
    public boolean method4248() {
        return false;
    }

    @Override
    public void method4249(Canvas arg0) {
        if (arg0 == null || arg0 == this.hostCanvas || this.canvasMap.containsKey(arg0)) {
            return;
        }
        this.canvasMap.put(arg0, new LwjglCanvas(LwjglConfig.newGLData(this.multisamplePreference)));
    }

    @Override
    public void scaleHeight(int arg0, float arg1, float arg2, float arg3, float arg4, float arg5) {
    }

    @Override
    public void fa(int left, int top, int right, int bottom) {
    }

    @Override
    public void setCanvas(Canvas canvas) {
        if (canvas == null) {
            this.currentCanvas = this.glCanvas;
            this.currentHost = null;
            return;
        }
        Object mapped = this.canvasMap.get(canvas);
        if (mapped == null) {
            mapped = new LwjglCanvas(LwjglConfig.newGLData(this.multisamplePreference));
            this.canvasMap.put(canvas, mapped);
        }
        this.currentCanvas = (LwjglCanvas) mapped;
        this.currentHost = canvas;
        this.installHostCanvas(canvas, this.currentCanvas);
    }

    /**
     * Shows the GL canvas where the host canvas sits, mirroring the legacy
     * attach where the native context drew into the shell's canvas peer.
     * The host stays in the hierarchy (hidden) so the shell's remove/size
     * bookkeeping keeps working; the GL canvas takes its bounds and z
     * position, receives copies of its input listeners, tracks its moves
     * and resizes, and takes focus. The context is attached when the canvas
     * is displayable, so later game-thread GL (fonts, textures, scene) has
     * a current context; undisplayed hosts (unit tests) only rewire the
     * hierarchy. Runs once per host canvas: the shell re-creates the host
     * on every display change and setCanvas reinstalls after each one.
     */
    private void installHostCanvas(Canvas host, LwjglCanvas gl) {
        Container parent = host.getParent();
        if (parent == null) {
            return;
        }
        if (this.installedCanvas != null && this.installedCanvas != gl) {
            Container prior = this.installedCanvas.getParent();
            if (prior != null) {
                prior.remove(this.installedCanvas);
            }
        }
        this.installedCanvas = gl;
        if (gl.getParent() != parent) {
            Container old = gl.getParent();
            if (old != null) {
                old.remove(gl);
            }
            parent.add(gl);
        }
        // Host is parented, so its index always resolves.
        parent.setComponentZOrder(gl, parent.getComponentZOrder(host));
        gl.setBounds(host.getBounds());
        host.setVisible(false);
        // The shell attaches input to the host on every canvas re-creation
        // and setCanvas reinstalls after each one, so one copy per install
        // stays in step without touching the deob attach sites.
        for (java.awt.event.MouseListener listener : host.getMouseListeners()) {
            gl.addMouseListener(listener);
        }
        for (java.awt.event.MouseMotionListener listener : host.getMouseMotionListeners()) {
            gl.addMouseMotionListener(listener);
        }
        for (java.awt.event.MouseWheelListener listener : host.getMouseWheelListeners()) {
            gl.addMouseWheelListener(listener);
        }
        for (java.awt.event.KeyListener listener : host.getKeyListeners()) {
            gl.addKeyListener(listener);
        }
        for (java.awt.event.FocusListener listener : host.getFocusListeners()) {
            gl.addFocusListener(listener);
        }
        host.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                host.setVisible(false);
                gl.setBounds(host.getBounds());
            }

            @Override
            public void componentMoved(ComponentEvent event) {
                gl.setBounds(host.getBounds());
            }
        });
        gl.requestFocus();
        if (gl.isDisplayable()) {
            gl.attachGameThread();
        }
    }

    /**
     * Installs the scene view, mirroring {@code GlToolkit.ea}: the view
     * matrix is read live from the ground (identity for non-matrix
     * grounds), pushed through the frame seam, and the sun/point lights
     * refresh against it. The legacy 180-degree X flip stays with the
     * frame present step.
     */
    @Override
    public void ea(Ground arg0) {
        this.sceneGround = arg0;
        float[] view = LwjglSceneMatrix.viewMatrix(arg0);
        System.arraycopy(view, 0, this.viewMatrix, 0, 16);
        this.frameOps.loadIdentity();
        this.frameOps.multMatrixf(view);
        this.lighting().upload();
    }

    @Override
    public int YA() {
        return this.zFar;
    }

    @Override
    public void TA(int x, int y, int height, int colour, int mode) {
    }

    @Override
    public boolean method4251() {
        return true;
    }

    @Override
    public boolean method4252() {
        return false;
    }

    @Override
    public void strongLine(int x1, int y1, int x2, int y2, int colour, int width) {
    }

    @Override
    public Model createModel(Mesh mesh, int functionMask, int featureMask, int ambient, int contrast) {
        return new LwjglModel(this, mesh, functionMask, ambient, contrast, featureMask);
    }

    @Override
    public void method4255(boolean enabled) {
    }

    @Override
    public void setBloomParams(float whitePoint, float bloom, float brightpass) {
        this.effects.setBloomParams(whitePoint, bloom, brightpass);
    }

    @Override
    public boolean method4258() {
        return true;
    }

    @Override
    public void g(float arg0, float arg1) {
    }

    @Override
    public int IA() {
        return this.renderBuffersSize + this.textureBuffersSize + this.vboBuffersSize;
    }

    @Override
    public boolean supportsAntiAliasing() {
        return this.multisamplePreference > 1;
    }

    @Override
    public int getMaxLights() {
        return 4;
    }

    @Override
    public boolean method4261() {
        return this.effects.isWaterSupported();
    }

    @Override
    public void method4262(Model arg0, ParticleList arg1, Ground arg2, BoundingBoxPolygon arg3) {
        arg0.render(arg2, arg3, 0);
        this.render(arg1);
    }

    @Override
    public void method4263(Canvas arg0) {
        this.canvasMap.remove(arg0);
        if (arg0 != null && arg0 == this.currentHost) {
            this.currentCanvas = this.glCanvas;
            this.currentHost = null;
        }
    }

    @Override
    public boolean method4264() {
        return false;
    }

    @Override
    public int w() {
        int value = this.wCounter;
        this.wCounter = 0;
        return value;
    }

    @Override
    public void l() {
    }

    @Override
    protected void NA(int x, int y, int radius, int colour, int mode) {
        throw new IllegalStateException("Circle fill is not supported by the gl toolkit");
    }

    @Override
    public NativeSprite method4265(int[] arg0, int arg1, int arg2, int arg3) {
        return new LwjglSprite(this, arg2, arg3, arg0, arg1);
    }

    @Override
    public void method4266() {
    }

    @Override
    public void da(int[] clip) {
        clip[0] = 0;
        clip[1] = 0;
        clip[2] = 0;
        clip[3] = 0;
    }

    @Override
    public EnvironmentSampler createCubeTexture(int materialPositiveX, int materialNegativeX, int materialPositiveY, int materialNegativeY, int materialPositiveZ, int materialNegativeZ) {
        return null;
    }

    @Override
    public int compareFunctionMasks(int maskA, int maskB) {
        return 0;
    }

    @Override
    public void releaseSurface(Canvas canvas) {
        if (canvas == null || (!this.canvasMap.containsKey(canvas) && canvas != this.hostCanvas)) {
            throw new RuntimeException();
        }
        if (canvas == this.currentHost) {
            this.currentCanvas = this.glCanvas;
            this.currentHost = null;
        }
    }

    @Override
    public void e() {
    }

    @Override
    public void linkThreads(int threadId) {
        this.attach();
    }

    @Override
    public Ground method4274() {
        return new JavaMatrix();
    }

    @Override
    public int ZA() {
        int value = this.zaCounter;
        this.zaCounter = 0;
        return value;
    }

    @Override
    public AbstractFontRenderer createFontRenderer(FontMetrics metrics, SpriteType[] images) {
        return new LwjglFont(this, metrics, images);
    }

    @Override
    public void method4276() {
    }

    @Override
    public void render(ParticleList particleList) {
    }

    /**
     * Installs the depth planes, mirroring {@code GlToolkit.ia}: the far
     * plane is fog-scaled for the projection while the raw value is kept
     * for {@link #YA} so callers can round-trip it back into a later
     * {@code ia} call.
     */
    @Override
    public void ia(int zNear, int zFar) {
        this.zNear = zNear;
        this.zFar = zFar;
        this.effectiveFar = FogDistance.scaleDistance(zFar);
        this.updateProjection();
    }

    @Override
    public void method4278() {
    }

    @Override
    public boolean hasTextures() {
        return true;
    }

    @Override
    public NativeSprite createNativeSprite(int x, int y, int width, int height, boolean hasAlpha) {
        return new LwjglSprite(this, width, height);
    }

    @Override
    public void method4281() {
    }

    @Override
    public int combineFunctionMasks(int maskA, int maskB) {
        return maskA | maskB;
    }

    @Override
    public void GA(int arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void allocateThreads(int threadCount) {
        if (threadCount != 1) {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public MemoryPool createHeap(int size) {
        return new LwjglMemoryPool(size);
    }

    @Override
    public ComponentSurface createMask(int width, int height, int[] offsets, int[] widths) {
        return new LwjglMask(this, width, height, offsets, widths);
    }

    @Override
    public boolean method4286() {
        return true;
    }

    @Override
    public void drawSurfaceLine(int startX, int startY, int endX, int endY, int colour, ComponentSurface arg5, int clippingMask, int maskX) {
    }

    @Override
    public void ca(int colour, ComponentSurface mask, int x, int y) {
    }

    @Override
    public void setSunCubeTexture(EnvironmentSampler cubeTexture) {
    }

    @Override
    public void setOffsets(int arg0) {
    }

    @Override
    public float xa() {
        return 0.0F;
    }

    @Override
    public Ground method4289() {
        if (this.sharedGround == null) {
            this.sharedGround = new JavaMatrix();
        }
        return this.sharedGround;
    }

    @Override
    public void method4290(Rectangle[] arg0, int arg1) {
    }

    @Override
    public boolean method4291() {
        return false;
    }

    @Override
    public boolean drawScaledColored(int arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
        return false;
    }

    @Override
    public void UA(int arg0, int arg1, int arg2) {
    }

    @Override
    public void method4294(int x, int y, int width, int height) {
    }

    @Override
    public void X(int arg0, int arg1, int arg2, int[] arg3) {
        arg3[0] = -1;
        arg3[1] = -1;
        arg3[2] = -1;
    }

    @Override
    public void DA(int x, int y, int width, int height, int backgroundColour, int foregroundColour, byte[] mask, int maskWidth, int mode) {
    }

    @Override
    public Class65 method4297(int arg0, int arg1, int[][] arg2, int[][] arg3, int arg4, int arg5) {
        return new LwjglGround(this, arg4, arg5, arg0, arg1, arg2, arg3, 128);
    }

    @Override
    public EnvironmentSampler drawQuad(EnvironmentSampler sourceA, EnvironmentSampler sourceB, float alpha, EnvironmentSampler existing) {
        return existing;
    }

    @Override
    public void method4299(Model[] arg0, ParticleList arg1, Ground arg2, BoundingBoxPolygon[] arg3, int arg4) {
        this.method4307(arg0, arg2, arg3, arg4);
        this.render(arg1);
    }

    @Override
    public boolean method4300() {
        return true;
    }

    @Override
    public boolean method4301() {
        return false;
    }

    @Override
    public void line(int x1, int y1, int x2, int y2, int colour, int mode) {
    }

    /**
     * Installs the pixel clip rectangle, mirroring the clamping half of
     * {@code GlToolkit.OA} (scissor presentation stays with the frame
     * step), and recomputes the culling frustum from it.
     */
    @Override
    public void OA(int left, int top, int right, int bottom) {
        if (top < 0) {
            top = 0;
        }
        if (left < 0) {
            left = 0;
        }
        if (this.viewportHeight > 0 && bottom > this.viewportHeight) {
            bottom = this.viewportHeight;
        }
        if (this.viewportWidth > 0 && right > this.viewportWidth) {
            right = this.viewportWidth;
        }
        this.clipLeft = left;
        this.clipTop = top;
        this.clipRight = right;
        this.clipBottom = bottom;
        this.clipSet = true;
        this.updateFrustum();
    }

    @Override
    public void p(int colour) {
    }

    @Override
    public void method4303(int threadId) {
    }

    @Override
    public int a() {
        return this.zNear;
    }

    @Override
    public void O(int x, int y, int width, int height, int colour, int mode) {
    }

    @Override
    public void setTextureSize(int size) {
        this.textureSize = size;
    }

    @Override
    public void method4307(Model[] arg0, Ground arg1, BoundingBoxPolygon[] arg2, int arg3) {
        for (int i = 0; i < arg0.length; i++) {
            if (arg0[i] != null) {
                arg0[i].render(arg1, arg2 == null ? null : arg2[i], arg3);
            }
        }
    }

    @Override
    public NativeSprite generateSprite(SpriteType spriteType) {
        int[] pixels = new int[spriteType.height * spriteType.width];
        int pos = 0;
        int out = 0;
        if (spriteType.alpha == null) {
            for (int y = 0; y < spriteType.height; y++) {
                for (int x = 0; x < spriteType.width; x++) {
                    int colour = spriteType.pallete[spriteType.pixels[pos++] & 0xFF];
                    pixels[out++] = colour == 0 ? 0 : colour | 0xFF000000;
                }
            }
        } else {
            for (int y = 0; y < spriteType.height; y++) {
                for (int x = 0; x < spriteType.width; x++) {
                    pixels[out++] = spriteType.alpha[pos] << 24 | spriteType.pallete[spriteType.pixels[pos] & 0xFF];
                    pos++;
                }
            }
        }
        NativeSprite sprite = this.method4265(pixels, spriteType.width, spriteType.width, spriteType.height);
        sprite.setOffsets(spriteType.minX, spriteType.minY, spriteType.borderX, spriteType.borderY);
        return sprite;
    }

    @Override
    public void method4309(boolean enabled) {
    }

    @Override
    public void b(int x, int y, int width, int colour, int mode) {
    }

    @Override
    public PointLight createPointLight(int x, int y, int z, int radius, int colour, float intensity) {
        return new GpuPointLight(x, y, z, radius, colour, intensity);
    }

    @Override
    public int[] F(int x, int y, int width, int height) {
        return new int[width * height];
    }

    /**
     * Installs the projection centers and scales, mirroring
     * {@code GlToolkit.H}: rebuilds the projection matrix and recomputes
     * the culling frustum from the clip rectangle when one exists.
     */
    @Override
    public void H(int projectionCenterX, int projectionCenterY, int projectionScaleX, int projectionScaleY) {
        this.projectionCenterX = projectionCenterX;
        this.projectionCenterY = projectionCenterY;
        this.projectionScaleX = projectionScaleX;
        this.projectionScaleY = projectionScaleY;
        this.updateProjection();
        this.updateFrustum();
    }

    @Override
    public float W() {
        return 3000.0F;
    }

    @Override
    public void cacheReset() {
    }

    @Override
    public void outlineRect(int x, int y, int width, int height, int colour, int mode) {
    }
}
