package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Rippling-water GLSL program owned by the LWJGL renderer, replacing the
 * {@code ReflectiveRipplingWaterEffect} and {@code BreakingWaterEffect}
 * program roles: a vertex shader shared by both, plus the reflective or
 * the breaking fragment shader, bound with the normal-map volume texture
 * on unit 0 and the environment cube map on unit 1.
 *
 * <p>Both GLSL sources are verbatim copies of the legacy ones; only the
 * entry-point names change (compilation goes through
 * {@link LwjglShader}/{@link LwjglProgram}). Scene state the legacy
 * paths read from the toolkit (sun direction and colour, tick counter)
 * is passed explicitly, since the LWJGL toolkit does not carry it yet.
 * The breaking variant adds the wave-intensity and wave-exponent
 * uniforms, mirroring {@code setEffectParams} exactly.
 */
public class LwjglRipplingWaterEffect {

    private static final String VERTEX_SOURCE = "uniform float time;\nuniform float scale;\nvarying vec3 wvVertex;\nvarying float waterDepth;\nvoid main() {\nwaterDepth = gl_MultiTexCoord0.z;\nvec4 ecVertex = gl_ModelViewMatrix*gl_Vertex;\nwvVertex.x = dot(gl_NormalMatrix[0], ecVertex.xyz);\nwvVertex.y = dot(gl_NormalMatrix[1], ecVertex.xyz);\nwvVertex.z = dot(gl_NormalMatrix[2], ecVertex.xyz);\ngl_TexCoord[0].x = dot(gl_TextureMatrix[0][0], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].y = dot(gl_TextureMatrix[0][1], gl_MultiTexCoord0)*scale;\ngl_TexCoord[0].z = time;\ngl_TexCoord[0].w = 1.0;\ngl_FogFragCoord = 1.0-clamp((gl_Fog.end+ecVertex.z)*gl_Fog.scale, 0.0, 1.0);\ngl_Position = ftransform();\n}\n";

    private static final String REFLECTIVE_FRAGMENT_SOURCE = "varying vec3 wvVertex;\nvarying float waterDepth;\nuniform vec3 sunDir;\nuniform vec4 sunColour;\nuniform float sunExponent;\nuniform float breakWaterDepth;\nuniform float breakWaterOffset;\nuniform sampler3D normalSampler;\nuniform samplerCube envMapSampler;\nvoid main() {\nvec4 wnNormal = texture3D(normalSampler, gl_TexCoord[0].xyz).rbga;\nwnNormal.xyz = 2.0*wnNormal.xyz-1.0;\nvec3 wnVector = normalize(wvVertex);\nvec3 wnReflection = reflect(wnVector, wnNormal.xyz);\nvec3 envColour = textureCube(envMapSampler, wnReflection).rgb;\nvec4 specularColour = sunColour*pow(clamp(-dot(sunDir, wnReflection), 0.0, 1.0), sunExponent);\nfloat shoreFactor = clamp(waterDepth/breakWaterDepth-breakWaterOffset*wnNormal.w, 0.0, 1.0);\nfloat ndote = dot(wnVector, wnNormal.xyz);\nfloat fresnel = pow(1.0-abs(ndote), 1.0);\nvec4 surfaceColour = vec4(envColour, fresnel*shoreFactor)+specularColour*shoreFactor;\ngl_FragColor = mix(surfaceColour, vec4(gl_Fog.color.rgb, 1.0), gl_FogFragCoord);\n}\n";

    private static final String BREAKING_FRAGMENT_SOURCE = "varying vec3 wvVertex;\nvarying float waterDepth;\nuniform vec3 sunDir;\nuniform vec4 sunColour;\nuniform float sunExponent;\nuniform vec2 waveIntensity;\nuniform float waveExponent;\nuniform float breakWaterDepth;\nuniform float breakWaterOffset;\nuniform sampler3D normalSampler;\nuniform samplerCube envMapSampler;\nvoid main() {\nvec4 wnNormal = texture3D(normalSampler, gl_TexCoord[0].xyz).rbga;\nwnNormal.xyz = 2.0*wnNormal.xyz-1.0;\nvec3 wnVector = normalize(wvVertex);\nvec3 wnReflection = reflect(wnVector, wnNormal.xyz);\nvec3 envColour = textureCube(envMapSampler, wnReflection).rgb;\nvec4 specularColour = sunColour*pow(clamp(-dot(sunDir, wnReflection), 0.0, 1.0), sunExponent);\nfloat shoreFactor = clamp(waterDepth/breakWaterDepth-breakWaterOffset*wnNormal.w, 0.0, 1.0);\nfloat waveFactor = pow(1.0-shoreFactor, waveExponent)-0.5;\nwaveFactor = -4.0*waveFactor*waveFactor+1.0;\nfloat ndote = dot(wnVector, wnNormal.xyz);\nfloat fresnel = pow(1.0-abs(ndote), 1.0);\nvec4 surfaceColour = mix(vec4(envColour, fresnel*shoreFactor), (waveIntensity.x*wnNormal.wwww)+waveIntensity.y, waveFactor)+specularColour*shoreFactor;\ngl_FragColor = mix(surfaceColour, vec4(gl_Fog.color.rgb, 1.0), gl_FogFragCoord);\n}\n";

    /** Vertex shader type, mirroring the legacy 35633. */
    public static final int TYPE_VERTEX_SHADER = 0x8B31;

    private final LwjglToolkit toolkit;
    private final LwjglTextureOps textureOps;
    private final LwjglProgram program;
    private final LwjglTexture normalMap;
    private final LwjglCubeTexture envMap;
    private final boolean breaking;

    private boolean enabled;

    private LwjglRipplingWaterEffect(LwjglToolkit toolkit, LwjglTextureOps textureOps, LwjglProgram program, LwjglTexture normalMap, LwjglCubeTexture envMap, boolean breaking) {
        this.toolkit = toolkit;
        this.textureOps = textureOps;
        this.program = program;
        this.normalMap = normalMap;
        this.envMap = envMap;
        this.breaking = breaking;
    }

    /**
     * Compiles and links the program, returning null on failure.
     *
     * @param breaking true for the breaking-water fragment shader.
     */
    public static LwjglRipplingWaterEffect load(LwjglToolkit toolkit, LwjglTextureOps textureOps, LwjglTexture normalMap, LwjglCubeTexture envMap, boolean breaking) {
        if (toolkit == null || textureOps == null || normalMap == null || envMap == null) {
            throw new RuntimeException();
        }
        LwjglShader vertex = LwjglShader.compile(textureOps, TYPE_VERTEX_SHADER, VERTEX_SOURCE);
        LwjglShader fragment = LwjglShader.compile(textureOps, LwjglShader.TYPE_FRAGMENT_SHADER, breaking ? BREAKING_FRAGMENT_SOURCE : REFLECTIVE_FRAGMENT_SOURCE);
        if (vertex == null || fragment == null) {
            return null;
        }
        LwjglProgram program = LwjglProgram.link(textureOps, new LwjglShader[] { vertex, fragment });
        if (program == null) {
            return null;
        }
        return new LwjglRipplingWaterEffect(toolkit, textureOps, program, normalMap, envMap, breaking);
    }

    /** @return true for the breaking-water variant. */
    public boolean isBreaking() {
        return this.breaking;
    }

    /** @return true while the program is bound. */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Binds the environment map on unit 1 and the normal map on unit 0,
     * installs the program and uploads the sun uniforms, mirroring the
     * legacy {@code enable} paths.
     */
    public void enable(float sunDirX, float sunDirY, float sunDirZ, float sunR, float sunG, float sunB, float sunExponent) {
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(this.envMap);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(this.normalMap);
        this.program.use();
        this.program.uniform1i("normalSampler", 0);
        this.program.uniform1i("envMapSampler", 1);
        this.program.uniform3f("sunDir", -sunDirX, -sunDirY, -sunDirZ);
        this.program.uniform4f("sunColour", sunR, sunG, sunB, 1.0F);
        this.program.uniform1f("sunExponent", sunExponent);
        this.enabled = true;
    }

    /**
     * Uploads the animated uniforms, mirroring {@code setEffectParams}:
     * the time counter, scale, break depth and offset come from the
     * packed variant exactly like the legacy bit unpacking, with the
     * breaking variant adding wave intensity and exponent.
     */
    public void setEffectParams(int tick, int variant, int packed) {
        if (!this.enabled) {
            return;
        }
        int step = 0x1 << (variant & 0x3);
        float scale = (float) (0x1 << (variant >> 3 & 0x7)) / 32.0F;
        this.program.uniform1f("time", (float) (tick * step % 40000) / 40000.0F);
        this.program.uniform1f("scale", scale);
        this.program.uniform1f("breakWaterDepth", packed & 0xFFFF);
        this.program.uniform1f("breakWaterOffset", (float) (packed >> 16 & 0x3) / 8.0F);
        if (this.breaking) {
            float intensityY = (float) (packed >> 19 & 0xF) / 16.0F;
            float intensityX = (float) (packed >> 23 & 0xF) / 16.0F;
            this.program.uniform2f("waveIntensity", intensityX, intensityY);
            this.program.uniform1f("waveExponent", packed >> 27 & 0xF);
        }
    }

    /**
     * Unbinds the units and the program, mirroring {@code disable}.
     */
    public void disable() {
        if (!this.enabled) {
            return;
        }
        this.toolkit.selectTextureUnit(1);
        this.toolkit.bindTexture(null);
        this.toolkit.selectTextureUnit(0);
        this.toolkit.bindTexture(null);
        LwjglProgram.unbind(this.textureOps);
        this.enabled = false;
    }
}
