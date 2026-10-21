package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Post and water effect selection owned by the LWJGL renderer, replacing
 * the {@code EffectManagerGL} role for the effects ported so far.
 *
 * <p>Selection bookkeeping mirrors {@code method5318} exactly: effect 4
 * and 8 degrade to 2 while the chain is inactive, the low bit carries the
 * texture-combine flag, switching away from a live id disables it, and
 * reselecting the live id re-applies its texture combine. Only the
 * still-water id (3) owns a live effect here; every other nonzero id
 * idles after disabling the previous effect. Still water loads lazily
 * through {@link #waterEffect()} so construction issues no GL, and
 * {@link #isWaterSupported()} mirrors {@code method5317} once it is
 * loaded. The bloom pass is held eagerly (its constructor issues no GL)
 * and takes the toolkit bloom parameters.
 *
 * <p>Selecting a specular-style id (1 or 7) installs its unit-0 combine
 * (8448, 7681, like {@code FixedFunctionSpecularPass} and
 * {@code ReflectingTextureEffect}); selecting the still-water style id
 * (4, like {@code Class9_Sub3}) installs (260, 8448); every other id
 * leaves unit 0 alone until its effect lands. Binding mirrors
 * {@code method5316}: false while idle, otherwise unit 0 takes the
 * texture with the preset. Effects whose legacy bind leaves the diffuse
 * texture to the effect (still water and the other water ids) fall back
 * to the diffuse bind here, keeping geometry visible until their unit 1/2
 * effect textures land; the branch shape stays per-id so those binds
 * slot in without changing callers.
 */
public final class LwjglEffectManager {

    /** Still-water effect id (legacy slot 3). */
    public static final int EFFECT_STILL_WATER = 3;

    /** Combine flag ORed into the selected id (legacy MIN_VALUE bit). */
    public static final int FLAG_COMBINE = Integer.MIN_VALUE;

    private final LwjglToolkit toolkit;
    private final LwjglBloomEffect bloom;
    private LwjglStillWaterEffect stillWater;
    private int current;
    private int effectParamA = Integer.MIN_VALUE;
    private int effectParamB = Integer.MIN_VALUE;

    /**
     * @param toolkit owning toolkit.
     */
    public LwjglEffectManager(LwjglToolkit toolkit) {
        if (toolkit == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
        this.bloom = new LwjglBloomEffect(toolkit, toolkit.textureOps(), toolkit.frameOps());
    }

    /** @return bloom pass, configured through {@link #setBloomParams}. */
    public LwjglBloomEffect bloom() {
        return this.bloom;
    }

    /**
     * Loads the still-water effect on first call, which requires the
     * calling thread's GL context to be current. Idempotent.
     *
     * @return the effect, or null when its programs fail to load.
     */
    public LwjglStillWaterEffect waterEffect() {
        if (this.stillWater == null) {
            this.stillWater = LwjglStillWaterEffect.load(this.toolkit.textureOps(), this.toolkit.frameOps());
        }
        return this.stillWater;
    }

    /** @return true once still water has loaded. */
    public boolean isWaterSupported() {
        return this.stillWater != null;
    }

    /** @return selected id including the combine flag, 0 when idle. */
    public int currentId() {
        return this.current;
    }

    /** Routes the toolkit bloom parameters to the bloom pass. */
    public void setBloomParams(float whitePoint, float bloomStrength, float brightThreshold) {
        this.bloom.setBloomParams(brightThreshold, bloomStrength, whitePoint);
    }

    /**
     * Selects an effect id, mirroring the legacy switch: unported nonzero
     * ids idle after disabling the previous effect, and the known
     * specular/water combines install on unit 0.
     */
    public void select(boolean lit, int id) {
        boolean active = lit && this.toolkit.method4261();
        if (!active && (id == 4 || id == 8)) {
            id = 2;
        }
        if (id != 0 && lit) {
            id |= FLAG_COMBINE;
        }
        if (id != this.current) {
            if (this.current != 0) {
                this.disable(this.current & Integer.MAX_VALUE);
            }
            if (id != 0) {
                this.applyCombine(id & Integer.MAX_VALUE, lit);
            }
            this.current = id;
            this.effectParamA = Integer.MIN_VALUE;
            this.effectParamB = Integer.MIN_VALUE;
        } else if (this.current != 0) {
            this.applyCombine(this.current & Integer.MAX_VALUE, lit);
        }
    }

    /**
     * Caches effect params, mirroring {@code method5319}: only acts while
     * an effect is selected and only on change. Per-effect delivery
     * arrives with the water programs.
     */
    public void setEffectParams(int paramA, int paramB) {
        if ((this.effectParamA != paramA || paramB != this.effectParamB) && this.current != 0) {
            this.effectParamA = paramA;
            this.effectParamB = paramB;
        }
    }

    private void applyCombine(int id, boolean lit) {
        if (id == 1 || id == 7) {
            this.toolkit.setCombineRgbAlpha(8448, 7681);
        } else if (id == 4) {
            this.toolkit.setCombineRgbAlpha(260, 8448);
        }
    }

    private void disable(int id) {
        if (id == EFFECT_STILL_WATER && this.stillWater != null) {
            this.stillWater.disable();
        }
        if (id == 1 || id == 4 || id == 7) {
            this.toolkit.setCombineRgbAlpha(8448, 8448);
        }
    }

    /**
     * Binds a texture for the current effect, mirroring
     * {@code method5316}: false while idle, otherwise unit 0 takes the
     * texture with the preset combine. Ids whose legacy bind leaves the
     * diffuse texture to the effect (2, 5 and 7 bind it directly; the
     * water ids drive effect textures instead) share this fallback until
     * their effect textures land, so the selected branch stays visible
     * for that step.
     */
    public boolean bindForEffect(LwjglTexture2D texture, int combinePreset) {
        if (this.current == 0) {
            return false;
        }
        if (texture == null) {
            throw new RuntimeException();
        }
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(texture);
        this.toolkit.setCombine(combinePreset);
        return true;
    }
}
