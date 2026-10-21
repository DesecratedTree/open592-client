package com.jagex.game.runetek5.graphics.gl.lwjgl;

import com.jagex.game.runetek5.cache.node.PointLight;

/**
 * Fixed-function light state owned by the LWJGL renderer, replacing the
 * {@code GlToolkit} sun and point-light paths.
 *
 * <p>Sun handling mirrors the legacy setup exactly: positions normalize
 * into the sun and anti-sun directions, and the sun colour splits into
 * the ambient triple that is scaled by brightness on upload. A zero sun
 * vector keeps the previous direction instead of poisoning the state
 * with infinities. Point lights mirror {@code method1903}: slots start
 * at light 1 (light 0 stays reserved), each uploads its position,
 * intensity-scaled diffuse colour and quadratic falloff, and unused
 * slots disable. Only the first {@code MAX_LIGHTS} entries upload,
 * matching {@code getMaxLights}.
 */
public final class LwjglLighting {

    /** Fixed-function lighting capability (legacy literal 2896). */
    public static final int CAP_LIGHTING = 0x0B50;

    /** First light id (legacy literal 16384). */
    public static final int LIGHT0 = 0x4000;

    /** Position parameter (legacy literal 4611). */
    public static final int POSITION = 0x1203;

    /** Diffuse parameter (legacy literal 4609). */
    public static final int DIFFUSE = 0x1201;

    /** Quadratic attenuation parameter (legacy literal 4617). */
    public static final int QUADRATIC_ATTENUATION = 0x1209;

    /** Lighting-model ambient parameter (legacy literal 2899). */
    public static final int MODEL_AMBIENT = 0x0B53;

    /** Uploaded point-light slots. */
    public static final int MAX_LIGHTS = 4;

    private final LwjglToolkit toolkit;
    private final float[] sunDirection = new float[4];
    private final float[] lightingDirection = new float[4];
    private float ambientR = 1.0F;
    private float ambientG = 1.0F;
    private float ambientB = 1.0F;
    private float sunBrightness = 1.0F;
    private final PointLight[] pointLights = new PointLight[MAX_LIGHTS];
    private int pointLightCount;

    /**
     * @param toolkit owning toolkit; seams are read live so tests can
     *     swap them after construction.
     */
    public LwjglLighting(LwjglToolkit toolkit) {
        if (toolkit == null) {
            throw new RuntimeException();
        }
        this.toolkit = toolkit;
    }

    /**
     * Points the sun along (x, y, z), normalizing exactly like the legacy
     * path. A zero vector keeps the previous direction.
     */
    public void setSunPosition(float x, float y, float z) {
        float length = (float) Math.sqrt((double) (x * x + y * y + z * z));
        if (!(length > 0.0F)) {
            return;
        }
        this.sunDirection[0] = x / length;
        this.sunDirection[1] = y / length;
        this.sunDirection[2] = z / length;
        this.lightingDirection[0] = -this.sunDirection[0];
        this.lightingDirection[1] = -this.sunDirection[1];
        this.lightingDirection[2] = -this.sunDirection[2];
    }

    /** Splits the packed sun colour into the ambient triple. */
    public void setSunColor(int rgb) {
        this.ambientR = (float) (rgb & 0xFF0000) / 1.671168E7F;
        this.ambientG = (float) (rgb & 0xFF00) / 65280.0F;
        this.ambientB = (float) (rgb & 0xFF) / 255.0F;
    }

    /** @param brightness ambient scale factor. */
    public void setBrightness(float brightness) {
        this.sunBrightness = brightness;
    }

    /**
     * Stores scene point lights, mirroring {@code copyLights}; only the
     * first {@code MAX_LIGHTS} entries upload.
     */
    public void copyLights(int count, PointLight[] lights) {
        if (lights == null || count < 0) {
            throw new RuntimeException();
        }
        int stored = Math.min(count, Math.min(lights.length, MAX_LIGHTS));
        for (int i = 0; i < stored; i++) {
            this.pointLights[i] = lights[i];
        }
        for (int i = stored; i < MAX_LIGHTS; i++) {
            this.pointLights[i] = null;
        }
        this.pointLightCount = stored;
    }

    /** @return stored point-light count. */
    public int pointLightCount() {
        return this.pointLightCount;
    }

    /** @return copy of the sun direction. */
    public float[] sunDirection() {
        return new float[] { this.sunDirection[0], this.sunDirection[1], this.sunDirection[2], this.sunDirection[3] };
    }

    /** Uploads the sun, the model ambient and every stored point light. */
    public void upload() {
        LwjglLightOps lights = this.toolkit.lightOps();
        LwjglFrameOps frame = this.toolkit.frameOps();
        lights.lightfv(LIGHT0, POSITION, this.sunDirection);
        lights.lightfv(LIGHT0 + 1, POSITION, this.lightingDirection);
        lights.lightModelfv(MODEL_AMBIENT, new float[] {
            this.ambientR * this.sunBrightness, this.ambientG * this.sunBrightness,
            this.ambientB * this.sunBrightness, 1.0F });
        for (int i = 0; i < MAX_LIGHTS; i++) {
            int id = LIGHT0 + 2 + i;
            PointLight light = this.pointLights[i];
            if (light == null) {
                frame.disableCap(id);
                continue;
            }
            float[] position = new float[] {
                (float) light.method3160(), (float) light.method3161(),
                (float) light.method3164(), 1.0F };
            lights.lightfv(id, POSITION, position);
            float scale = light.getIntensity() / 255.0F;
            int colour = light.method3162();
            lights.lightfv(id, DIFFUSE, new float[] {
                scale * (float) (colour >> 16 & 0xFF),
                scale * (float) (colour >> 8 & 0xFF),
                scale * (float) (colour & 0xFF), 1.0F });
            float radius = (float) light.method3165();
            lights.lightf(id, QUADRATIC_ATTENUATION, radius > 0.0F ? 1.0F / (radius * radius) : 0.0F);
            frame.enableCap(id);
        }
    }
}
