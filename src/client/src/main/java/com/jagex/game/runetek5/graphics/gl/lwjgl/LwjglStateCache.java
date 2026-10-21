package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Minimum-call GL state cache for the LWJGL renderer. Every mutator reports
 * whether the state actually changed, so render code only issues GL calls on
 * transitions; {@link #reset()} invalidates everything to force re-emission
 * (context loss, canvas switch). All decisions are pure and headless-safe;
 * the cache never touches GL itself.
 */
public final class LwjglStateCache {

    /** Texture units tracked, mirroring fixed-function limits. */
    public static final int MAX_TEXTURE_UNITS = 8;

    /** Capability slots cached in the enable bitmask. */
    public static final int CAP_DEPTH_TEST = 0;
    public static final int CAP_BLEND = 1;
    public static final int CAP_CULL_FACE = 2;
    public static final int CAP_SCISSOR_TEST = 3;
    public static final int CAP_ALPHA_TEST = 4;
    public static final int CAP_FOG = 5;

    private int activeUnit = -1;
    private final int[] boundTexture = new int[MAX_TEXTURE_UNITS];
    private int enabledCaps;
    private boolean capsKnown;
    private int blendSrc = -1;
    private int blendDst = -1;
    private int arrayBuffer;
    private int elementBuffer;
    private boolean buffersKnown;

    public LwjglStateCache() {
        this.reset();
    }

    /** Invalidates every entry so the next mutator reports a change. */
    public void reset() {
        this.activeUnit = -1;
        for (int i = 0; i < MAX_TEXTURE_UNITS; i++) {
            this.boundTexture[i] = 0;
        }
        this.enabledCaps = 0;
        this.capsKnown = false;
        this.blendSrc = -1;
        this.blendDst = -1;
        this.arrayBuffer = 0;
        this.elementBuffer = 0;
        this.buffersKnown = false;
    }

    /**
     * @return true when the active texture unit changed.
     * @throws IllegalArgumentException for units outside 0..7.
     */
    public boolean useUnit(int unit) {
        if (unit < 0 || unit >= MAX_TEXTURE_UNITS) {
            throw new IllegalArgumentException();
        }
        if (this.activeUnit == unit) {
            return false;
        }
        this.activeUnit = unit;
        return true;
    }

    /**
     * @return true when a different texture must be bound on the unit.
     * @throws IllegalArgumentException for units outside 0..7.
     */
    public boolean bindTexture(int unit, int id) {
        if (unit < 0 || unit >= MAX_TEXTURE_UNITS) {
            throw new IllegalArgumentException();
        }
        if (this.boundTexture[unit] == id) {
            return false;
        }
        this.boundTexture[unit] = id;
        return true;
    }

    /**
     * @return true when the capability must be (re-)emitted. Unknown
     * capabilities always report a change and are never cached.
     */
    public boolean setCapability(int slot, boolean enabled) {
        if (slot < CAP_DEPTH_TEST || slot > CAP_FOG) {
            return true;
        }
        int bit = 1 << slot;
        boolean knownEnabled = this.capsKnown && (this.enabledCaps & bit) != 0;
        if (this.capsKnown && knownEnabled == enabled) {
            return false;
        }
        if (enabled) {
            this.enabledCaps |= bit;
        } else {
            this.enabledCaps &= ~bit;
        }
        this.capsKnown = true;
        return true;
    }

    /** @return true when the blend factors changed. */
    public boolean setBlendFunc(int src, int dst) {
        if (this.blendSrc == src && this.blendDst == dst) {
            return false;
        }
        this.blendSrc = src;
        this.blendDst = dst;
        return true;
    }

    /** @return true when a different array buffer must be bound. */
    public boolean bindArrayBuffer(int id) {
        if (this.buffersKnown && this.arrayBuffer == id) {
            return false;
        }
        this.arrayBuffer = id;
        this.buffersKnown = true;
        return true;
    }

    /** @return true when a different element buffer must be bound. */
    public boolean bindElementBuffer(int id) {
        if (this.buffersKnown && this.elementBuffer == id) {
            return false;
        }
        this.elementBuffer = id;
        this.buffersKnown = true;
        return true;
    }
}
