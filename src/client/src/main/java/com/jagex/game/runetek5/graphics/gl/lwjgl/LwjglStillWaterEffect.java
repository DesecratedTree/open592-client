package com.jagex.game.runetek5.graphics.gl.lwjgl;

/**
 * Still-water vertex programs owned by the LWJGL renderer, replacing the
 * {@code StillWaterEffect} program role: four ARB vertex programs (lit and
 * unlit, each with a projected variant), bound around water geometry with
 * an eye-plane texgen and a fog local parameter.
 *
 * <p>The four assembly sources are verbatim copies of the legacy ones;
 * only the entry-point names change (loading goes through
 * {@link LwjglArbProgram}). Toolkit fields the legacy enable path read
 * internally (light selection, fog distances) are explicit parameters
 * here, since the LWJGL toolkit does not carry that scene state yet.
 */
public class LwjglStillWaterEffect {

    /** Fragment program capability the legacy disable path toggles. */
    public static final int CAP_FRAGMENT_PROGRAM = 0x8804;

    private static final String PROGRAM_BASE_UNLIT = "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour       = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   tMatrix[4]   = { state.matrix.texture[0] };\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nTEMP    viewPos, fogFactor;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nMAD   fogFactor.y, iTexCoord.z, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, iTexCoord.z;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nMOV   oColour, iColour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP3   oTexCoord0.x, tMatrix[0], iTexCoord;\nDP3   oTexCoord0.y, tMatrix[1], iTexCoord;\nMOV   oTexCoord0.zw, iTexCoord;\nEND\n";

    private static final String PROGRAM_BASE_LIT = "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iNormal      = vertex.normal;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour       = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   tMatrix[4]   = { state.matrix.texture[0] };\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nTEMP    viewPos, viewNormal, fogFactor, colour, ndotl;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nMAD   fogFactor.y, iTexCoord.z, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, iTexCoord.z;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nDP3   viewNormal.x, mvMatrix[0], iNormal;\nDP3   viewNormal.y, mvMatrix[1], iNormal;\nDP3   viewNormal.z, mvMatrix[2], iNormal;\nDP3   ndotl.x, viewNormal, state.light[0].position;\nDP3   ndotl.y, viewNormal, state.light[1].position;\nMAX   ndotl, ndotl, 0;\nMOV   colour, state.lightmodel.ambient;\nMAD   colour, state.light[0].diffuse, ndotl.xxxx, colour;\nMAD   colour, state.light[1].diffuse, ndotl.yyyy, colour;\nMUL   oColour, iColour, colour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP3   oTexCoord0.x, tMatrix[0], iTexCoord;\nDP3   oTexCoord0.y, tMatrix[1], iTexCoord;\nMOV   oTexCoord0.zw, iTexCoord;\nEND\n";

    private static final String PROGRAM_PROJECTED_UNLIT = "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour       = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   texMatrix[4] = { state.matrix.texture[0] };\nTEMP    viewPos, fogFactor, depth;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nDP4   depth, waterPlane, viewPos;\nMAD   fogFactor.y, -depth, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, -depth;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nMOV   oColour, iColour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP4   oTexCoord0.x, texMatrix[0], iTexCoord;\nDP4   oTexCoord0.y, texMatrix[1], iTexCoord;\nDP4   oTexCoord0.z, texMatrix[2], iTexCoord;\nMOV   oTexCoord0.w, 1;\nEND\n";

    private static final String PROGRAM_PROJECTED_LIT = "!!ARBvp1.0\nATTRIB  iPos         = vertex.position;\nATTRIB  iNormal      = vertex.normal;\nATTRIB  iColour      = vertex.color;\nATTRIB  iTexCoord    = vertex.texcoord[0];\nOUTPUT  oPos         = result.position;\nOUTPUT  oColour       = result.color;\nOUTPUT  oTexCoord0   = result.texcoord[0];\nOUTPUT  oFogCoord    = result.fogcoord;\nPARAM   fogParams    = program.local[0];\nPARAM   waterPlane   = state.texgen.eye.s;\nPARAM   pMatrix[4]   = { state.matrix.projection };\nPARAM   mvMatrix[4]  = { state.matrix.modelview };\nPARAM   texMatrix[4] = { state.matrix.texture[0] };\nTEMP    viewPos, viewNormal, fogFactor, depth, colour, ndotl;\nDP4   viewPos.x, mvMatrix[0], iPos;\nDP4   viewPos.y, mvMatrix[1], iPos;\nDP4   viewPos.z, mvMatrix[2], iPos;\nDP4   viewPos.w, mvMatrix[3], iPos;\nSUB   fogFactor.x, -viewPos.z, fogParams.x;\nMUL   fogFactor.x, fogFactor.x, 0.001953125;\nDP4   depth, waterPlane, viewPos;\nMAD   fogFactor.y, -depth, fogParams.z, fogParams.w;\nSUB   fogFactor.z, -viewPos.z, fogParams.y;\nMUL   fogFactor.z, fogFactor.z, 0.00390625;\nMIN   fogFactor, fogFactor, 1;\nMAX   fogFactor, fogFactor, 0;\nMUL   fogFactor.z, fogFactor.z, -depth;\nMAD   viewPos.xyz, waterPlane.xyzw, fogFactor.zzzz, viewPos.xyzw;\nMAX   oFogCoord.x, fogFactor.x, fogFactor.y;\nDP3   viewNormal.x, mvMatrix[0], iNormal;\nDP3   viewNormal.y, mvMatrix[1], iNormal;\nDP3   viewNormal.z, mvMatrix[2], iNormal;\nDP3   ndotl.x, viewNormal, state.light[0].position;\nDP3   ndotl.y, viewNormal, state.light[1].position;\nMAX   ndotl, ndotl, 0;\nMOV   colour, state.lightmodel.ambient;\nMAD   colour, state.light[0].diffuse, ndotl.xxxx, colour;\nMAD   colour, state.light[1].diffuse, ndotl.yyyy, colour;\nMUL   oColour, iColour, colour;\nDP4   oPos.x, pMatrix[0], viewPos;\nDP4   oPos.y, pMatrix[1], viewPos;\nDP4   oPos.z, pMatrix[2], viewPos;\nDP4   oPos.w, pMatrix[3], viewPos;\nDP4   oTexCoord0.x, texMatrix[0], iTexCoord;\nDP4   oTexCoord0.y, texMatrix[1], iTexCoord;\nDP4   oTexCoord0.z, texMatrix[2], iTexCoord;\nMOV   oTexCoord0.w, 1;\nEND\n";

    private final LwjglTextureOps textureOps;
    private final LwjglFrameOps frameOps;
    private final LwjglArbProgram baseUnlit;
    private final LwjglArbProgram baseLit;
    private final LwjglArbProgram projectedUnlit;
    private final LwjglArbProgram projectedLit;

    private boolean enabled;

    private LwjglStillWaterEffect(LwjglTextureOps textureOps, LwjglFrameOps frameOps, LwjglArbProgram baseUnlit, LwjglArbProgram baseLit, LwjglArbProgram projectedUnlit, LwjglArbProgram projectedLit) {
        this.textureOps = textureOps;
        this.frameOps = frameOps;
        this.baseUnlit = baseUnlit;
        this.baseLit = baseLit;
        this.projectedUnlit = projectedUnlit;
        this.projectedLit = projectedLit;
    }

    /**
     * Loads the four programs, returning null when any fails to load.
     */
    public static LwjglStillWaterEffect load(LwjglTextureOps textureOps, LwjglFrameOps frameOps) {
        if (textureOps == null || frameOps == null) {
            throw new RuntimeException();
        }
        LwjglArbProgram baseUnlit = LwjglArbProgram.create(textureOps, LwjglArbProgram.TARGET_VERTEX_PROGRAM, PROGRAM_BASE_UNLIT);
        LwjglArbProgram baseLit = LwjglArbProgram.create(textureOps, LwjglArbProgram.TARGET_VERTEX_PROGRAM, PROGRAM_BASE_LIT);
        LwjglArbProgram projectedUnlit = LwjglArbProgram.create(textureOps, LwjglArbProgram.TARGET_VERTEX_PROGRAM, PROGRAM_PROJECTED_UNLIT);
        LwjglArbProgram projectedLit = LwjglArbProgram.create(textureOps, LwjglArbProgram.TARGET_VERTEX_PROGRAM, PROGRAM_PROJECTED_LIT);
        if (baseUnlit == null || baseLit == null || projectedUnlit == null || projectedLit == null) {
            return null;
        }
        return new LwjglStillWaterEffect(textureOps, frameOps, baseUnlit, baseLit, projectedUnlit, projectedLit);
    }

    /** @return true when every program loaded. */
    public boolean isSupported() {
        return true;
    }

    /**
     * Binds the lit or unlit program (projected or base variant) and
     * installs the eye-plane texgen, mirroring {@code method5380}.
     */
    public void enable(boolean lit, boolean projected, float[] eyePlane) {
        if (eyePlane == null) {
            throw new RuntimeException();
        }
        this.frameOps.texGen(LwjglFrameGl.COORDINATE_S, LwjglFrameGl.PLANE_EYE, eyePlane);
        if (lit) {
            (projected ? this.projectedLit : this.baseLit).bind();
        } else {
            (projected ? this.projectedUnlit : this.baseUnlit).bind();
        }
        this.frameOps.enableCap(LwjglArbProgram.TARGET_VERTEX_PROGRAM);
        this.enabled = true;
    }

    /**
     * Refreshes program local parameter 0, mirroring
     * {@code method5379} with its toolkit fields passed explicitly.
     */
    public void updateLocalParams(float a, float b, float c, float d) {
        if (!this.enabled) {
            return;
        }
        this.textureOps.programLocalParameter4f(LwjglArbProgram.TARGET_VERTEX_PROGRAM, 0, a, b, c, d);
    }

    /**
     * Unbinds the program, mirroring {@code disable} including the
     * legacy fragment-program disable.
     */
    public void disable() {
        if (!this.enabled) {
            return;
        }
        this.textureOps.bindArbProgram(LwjglArbProgram.TARGET_VERTEX_PROGRAM, 0);
        this.frameOps.disableCap(CAP_FRAGMENT_PROGRAM);
        this.frameOps.disableCap(LwjglArbProgram.TARGET_VERTEX_PROGRAM);
        this.enabled = false;
    }

    /** @return true while the program is bound. */
    public boolean isEnabled() {
        return this.enabled;
    }
}
