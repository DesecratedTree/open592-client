package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.unpack_Sub1;

/**
 * Pins the full material graph: combine/blend tables, the id-based
 * material bind (scroll, effect select, consume-or-bind), effect manager
 * select/bind wiring, model/ground render wiring, and the sprite masked,
 * channel, copy and tiled paths.
 */
class LwjglMaterialEffectTest {

    private static final class RecordingTextures implements LwjglTextureOps {
        final List<String> calls = new ArrayList<>();
        int nextId = 41;

        @Override
        public int genTexture() {
            int id = nextId++;
            calls.add("genT(" + id + ")");
            return id;
        }

        @Override
        public void deleteTexture(int id) {
            calls.add("deleteT(" + id + ")");
        }

        @Override
        public void bindTexture(int target, int id) {
            calls.add("bindT(" + target + "," + id + ")");
        }

        @Override
        public void enableTarget(int target) {
            calls.add("enableT(" + target + ")");
        }

        @Override
        public void disableTarget(int target) {
            calls.add("disableT(" + target + ")");
        }

        @Override
        public void activeTexture(int unit) {
            calls.add("unit(" + unit + ")");
        }

        @Override
        public void texParameteri(int target, int pname, int param) {
            calls.add("param(" + target + "," + pname + "," + param + ")");
        }

        @Override
        public void texEnvi(int pname, int param) {
            calls.add("envi(" + pname + "," + param + ")");
        }

        @Override
        public void texEnvColor(float r, float g, float b, float a) {
            calls.add("envColor(" + r + "," + g + "," + b + "," + a + ")");
        }

        @Override
        public void pixelStorei(int pname, int param) {
        }

        @Override
        public void pixelTransferf(int pname, float param) {
            calls.add("transfer(" + pname + "," + param + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type, byte[] pixels, int offset) {
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type, int[] pixels, int offset) {
            calls.add("image2Di(" + width + "," + height + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type, float[] pixels, int offset) {
        }

        @Override
        public void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type) {
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format,
            int type, byte[] pixels, int offset) {
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format,
            int type, int[] pixels, int offset) {
        }

        @Override
        public void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height) {
            calls.add("copy(" + target + "," + level + "," + internalFormat + "," + x + "," + y + "," + width + ","
                + height + ")");
        }

        @Override
        public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width,
            int height) {
            calls.add("copySub(" + target + "," + level + "," + xOffset + "," + yOffset + "," + x + "," + y + ","
                + width + "," + height + ")");
        }

        @Override
        public void generateMipmap(int target) {
        }

        @Override
        public void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        }

        @Override
        public void flush() {
            calls.add("flush");
        }

        @Override
        public long createShader(int type) {
            return 0L;
        }

        @Override
        public void shaderSource(long shader, String source) {
        }

        @Override
        public void compileShader(long shader) {
        }

        @Override
        public int getObjectParam(long object, int pname) {
            return 0;
        }

        @Override
        public String getObjectInfoLog(long object) {
            return "";
        }

        @Override
        public void deleteObject(long object) {
        }

        @Override
        public long createProgram() {
            return 0L;
        }

        @Override
        public void attachShader(long program, long shader) {
        }

        @Override
        public void detachShader(long program, long shader) {
        }

        @Override
        public void linkProgram(long program) {
        }

        @Override
        public void useProgram(long program) {
        }

        @Override
        public int getUniformLocation(long program, String name) {
            return 0;
        }

        @Override
        public void uniform1i(int location, int value) {
        }

        @Override
        public void uniform1f(int location, float value) {
        }

        @Override
        public void uniform2f(int location, float x, float y) {
        }

        @Override
        public void uniform3f(int location, float x, float y, float z) {
        }

        @Override
        public void uniform4f(int location, float x, float y, float z, float w) {
        }

        @Override
        public void programLocalParameter4f(int target, int index, float x, float y, float z, float w) {
        }

        @Override
        public int genArbProgram() {
            return 0;
        }

        @Override
        public void bindArbProgram(int target, int id) {
        }

        @Override
        public void programString(int target, int format, String source) {
        }

        @Override
        public int programErrorPosition() {
            return -1;
        }
    }

    private static final class RecordingFrames implements LwjglFrameOps {
        final List<String> calls = new ArrayList<>();

        @Override
        public boolean supportsFramebufferObjects() {
            return true;
        }

        @Override
        public int genFramebuffer() {
            return 0;
        }

        @Override
        public void deleteFramebuffer(int id) {
        }

        @Override
        public void bindFramebuffer(int target, int id) {
        }

        @Override
        public int checkFramebufferStatus(int target) {
            return 0;
        }

        @Override
        public void drawBuffer(int attachment) {
        }

        @Override
        public void readBuffer(int attachment) {
        }

        @Override
        public void blitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1,
            int dstY1, int mask, int filter) {
        }

        @Override
        public int genRenderbuffer() {
            return 0;
        }

        @Override
        public void deleteRenderbuffer(int id) {
        }

        @Override
        public void bindRenderbuffer(int id) {
        }

        @Override
        public void renderbufferStorage(int format, int width, int height) {
        }

        @Override
        public void renderbufferStorageMultisample(int samples, int format, int width, int height) {
        }

        @Override
        public void framebufferRenderbuffer(int target, int attachment, int renderbuffer) {
        }

        @Override
        public void viewport(int x, int y, int width, int height) {
        }

        @Override
        public void pushAttrib(int mask) {
        }

        @Override
        public void popAttrib() {
        }

        @Override
        public void matrixMode(int mode) {
            calls.add("matrix(" + mode + ")");
        }

        @Override
        public void pushMatrix() {
            calls.add("push");
        }

        @Override
        public void popMatrix() {
            calls.add("pop");
        }

        @Override
        public void loadIdentity() {
            calls.add("identity");
        }

        @Override
        public void ortho(double left, double right, double bottom, double top, double near, double far) {
        }

        @Override
        public void beginQuads() {
            calls.add("beginQuads");
        }

        @Override
        public void end() {
            calls.add("end");
        }

        @Override
        public void texCoord2f(float s, float t) {
            calls.add("tc(" + s + "," + t + ")");
        }

        @Override
        public void multiTexCoord2f(int unit, float s, float t) {
            calls.add("mtc(" + unit + "," + s + "," + t + ")");
        }

        @Override
        public void vertex2i(int x, int y) {
            calls.add("v(" + x + "," + y + ")");
        }

        @Override
        public void vertex2f(float x, float y) {
            calls.add("vf(" + x + "," + y + ")");
        }

        @Override
        public void enableCap(int cap) {
            calls.add("enable(" + cap + ")");
        }

        @Override
        public void disableCap(int cap) {
            calls.add("disable(" + cap + ")");
        }

        @Override
        public void texGen(int coordinate, int pname, float[] params) {
        }

        @Override
        public void color4f(float r, float g, float b, float a) {
            calls.add("color(" + r + "," + g + "," + b + "," + a + ")");
        }

        @Override
        public void blendFunc(int src, int dst) {
            calls.add("blend(" + src + "," + dst + ")");
        }

        @Override
        public void multMatrixf(float[] matrix) {
            calls.add("multMatrix(" + matrix.length + ")");
        }

        @Override
        public void translatef(float x, float y, float z) {
            calls.add("translate(" + x + "," + y + "," + z + ")");
        }
    }

    private static final class RecordingGlOps implements LwjglGlOps {
        @Override
        public int genBuffer() {
            return 7;
        }

        @Override
        public void bindBuffer(int target, int id) {
        }

        @Override
        public void upload(int target, byte[] data, int size, int usage) {
        }

        @Override
        public void subUpload(int target, long offset, byte[] data, int size) {
        }

        @Override
        public void deleteBuffer(int id) {
        }
    }

    private static final class RecordingDraws implements LwjglDrawOps {
        final List<String> calls = new ArrayList<>();

        @Override
        public void drawElements(int mode, int count, int type, long offset) {
            calls.add("draw(" + mode + "," + count + "," + type + "," + offset + ")");
        }

        @Override
        public void drawArrays(int mode, int first, int count) {
            calls.add("drawArrays(" + mode + "," + first + "," + count + ")");
        }
    }

    private static final class RecordingLights implements LwjglLightOps {
        @Override
        public void lightfv(int light, int pname, float[] params) {
        }

        @Override
        public void lightf(int light, int pname, float value) {
        }

        @Override
        public void lightModelfv(int pname, float[] params) {
        }
    }

    private static final class StubProvider implements unpack_Sub1 {
        final Map<Integer, MaterialType> metrics = new HashMap<>();

        @Override
        public boolean textureAvailable(int id) {
            return metrics.containsKey(Integer.valueOf(id));
        }

        @Override
        public int[] getPixels(boolean readReversed, int id, float arg2, int width, int height) {
            return new int[width * height];
        }

        @Override
        public float[] method2394(float arg0, int id, int width, int height) {
            return new float[width * height];
        }

        @Override
        public int[] getPixelsArgb(int id, float arg1, int width, int height) {
            return new int[width * height];
        }

        @Override
        public MaterialType getMetrics(int id) {
            return metrics.get(Integer.valueOf(id));
        }
    }

    private static MaterialType metrics(int effectId, int combinePreset) {
        MaterialType metrics = new MaterialType();
        metrics.aByte89 = (byte) effectId;
        metrics.anInt6934 = combinePreset;
        metrics.anInt6933 = 0;
        metrics.aByte88 = 0;
        return metrics;
    }

    private static LwjglToolkit toolkit(StubProvider provider, RecordingGlOps glOps, RecordingDraws draws,
        RecordingFrames frames, RecordingLights lights, RecordingTextures textures) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, provider, 0, null);
        toolkit.setGlOpsForTest(glOps);
        toolkit.setDrawOpsForTest(draws);
        toolkit.setFrameOpsForTest(frames);
        toolkit.setLightOpsForTest(lights);
        toolkit.setTextureOpsForTest(textures);
        return toolkit;
    }

    @Test
    @DisplayName("combine presets install the exact legacy RGB/alpha pairs")
    void combine_should_installExactPairs() {
        int[][] pairs = { { 8448, 8448 }, { 7681, 7681 }, { 34165, 7681 }, { 260, 8448 }, { 34023, 34023 } };
        for (int mode = 1; mode <= 4; mode++) {
            RecordingTextures textures = new RecordingTextures();
            LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
                new RecordingLights(), textures);
            toolkit.setCombine(mode);
            StringBuilder expected = new StringBuilder("[");
            if (pairs[mode][0] != 8448) {
                expected.append("envi(34161,").append(pairs[mode][0]).append(")");
            }
            if (pairs[mode][1] != 8448) {
                if (expected.length() > 1) {
                    expected.append(", ");
                }
                expected.append("envi(34162,").append(pairs[mode][1]).append(")");
            }
            expected.append("]");
            int check = mode;
            assertEquals(expected.toString(), textures.calls.toString(), () -> "mode " + check);
        }
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
            new RecordingLights(), textures);
        toolkit.setCombine(1);
        textures.calls.clear();
        toolkit.setCombine(0);
        assertAll(
            () -> assertEquals("[envi(34161,8448), envi(34162,8448)]", textures.calls.toString()),
            () -> assertThrows(RuntimeException.class, () -> toolkit.setCombine(5)));
    }

    @Test
    @DisplayName("blend modes install the exact legacy factors and alpha-test rule")
    void blend_should_installExactState() {
        int[][] cases = { { 0, 3042, 3008, 0, 0 }, { 1, 3042, 3008, 770, 771 }, { 2, 3042, 3008, 1, 1 },
            { 128, 3042, 3008, 774, 1 } };
        for (int[] mode : cases) {
            RecordingFrames frames = new RecordingFrames();
            LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), frames,
                new RecordingLights(), new RecordingTextures());
            toolkit.setBlendMode(mode[0]);
            StringBuilder expected = new StringBuilder("[");
            expected.append(mode[0] == 0 ? "disable(" : "enable(").append(mode[1]).append("), ");
            expected.append((mode[0] == 1 || mode[0] == 128) ? "enable(" : "disable(").append(mode[2]).append(")");
            if (mode[0] != 0) {
                expected.append(", blend(").append(mode[3]).append(",").append(mode[4]).append(")");
            }
            expected.append("]");
            assertEquals(expected.toString(), frames.calls.toString(), "mode " + mode[0]);
        }
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
            new RecordingLights(), new RecordingTextures());
        assertThrows(RuntimeException.class, () -> toolkit.setBlendMode(3));
    }

    @Test
    @DisplayName("texenv colour shuffles exactly like the legacy upload")
    void texEnv_should_shuffleLikeLegacy() {
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
            new RecordingLights(), textures);
        toolkit.setTexEnvColor(0x80FF0000);
        toolkit.setTexCoordGen(34167, 1);
        toolkit.setTexCombine(2, 768, 34168);
        assertEquals(
            "[envColor(1.0,0.0,0.0,0.5019608), envi(34185,34167), envi(34201,770), envi(34178,34168), envi(34194,768)]",
            textures.calls.toString());
    }

    @Test
    @DisplayName("untextured bind resets state and idles the effects")
    void bindMaterial_should_handleNegativeId() {
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        toolkit.bindMaterial(-1, false);
        assertAll(
            () -> assertEquals("[unit(0)]", textures.calls.toString()),
            () -> assertTrue(frames.calls.isEmpty()),
            () -> assertEquals(0, toolkit.effects().currentId()));
    }

    @Test
    @DisplayName("textured bind resolves cache, scrolls and falls back to unit 0")
    void bindMaterial_should_bindTextured() {
        StubProvider provider = new StubProvider();
        provider.metrics.put(Integer.valueOf(7), metrics(0, 2));
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(provider, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        toolkit.bindMaterial(7, true);
        assertAll(
            () -> assertTrue(textures.calls.contains("image2Di(128,128)")),
            () -> assertTrue(textures.calls.contains("unit(0)")),
            () -> assertTrue(textures.calls.contains("envi(34161,34165)")),
            () -> assertTrue(textures.calls.contains("envi(34162,7681)")),
            () -> assertEquals(0, toolkit.effects().currentId()));
    }

    @Test
    @DisplayName("scrolled materials translate the texture matrix with the tick time")
    void bindMaterial_should_scrollWithTick() {
        StubProvider provider = new StubProvider();
        MaterialType scrolled = metrics(0, 0);
        scrolled.isSmall = true;
        scrolled.aByte91 = 1;
        scrolled.aByte93 = 2;
        provider.metrics.put(Integer.valueOf(9), scrolled);
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(provider, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), new RecordingTextures());
        toolkit.tick(100);
        toolkit.bindMaterial(9, true);
        assertTrue(frames.calls.containsAll(
            java.util.Arrays.asList("matrix(5890)", "identity", "translate(0.03125,0.0625,0.0)", "matrix(5888)")));
        frames.calls.clear();
        toolkit.bindMaterial(-1, false);
        assertTrue(frames.calls.containsAll(java.util.Arrays.asList("matrix(5890)", "identity", "matrix(5888)")));
    }

    @Test
    @DisplayName("effect select degrades, flags, combines and binds through unit 0")
    void effects_should_wireSelectAndBind() {
        StubProvider provider = new StubProvider();
        provider.metrics.put(Integer.valueOf(3), metrics(1, 2));
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(provider, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        LwjglEffectManager effects = toolkit.effects();
        effects.select(false, 8);
        assertEquals(2, effects.currentId());
        effects.select(false, 0);
        effects.select(true, 1);
        assertEquals(1 | Integer.MIN_VALUE, effects.currentId());
        assertTrue(textures.calls.contains("envi(34162,7681)"));
        effects.setEffectParams(5, 6);
        LwjglTexture2D texture = new LwjglTexture2D(toolkit, LwjglTexture.TARGET_2D, LwjglTexture2D.FORMAT_RGBA,
            2, 2, false, new int[4], false, textures);
        textures.calls.clear();
        frames.calls.clear();
        assertTrue(effects.bindForEffect(texture, 2));
        assertEquals("[unit(0), envi(34161,34165)]", textures.calls.toString());
        effects.select(false, 0);
        assertAll(
            () -> assertEquals(0, effects.currentId()),
            () -> assertTrue(textures.calls.contains("envi(34161,8448)")),
            () -> assertTrue(textures.calls.contains("envi(34162,8448)")));
    }

    @Test
    @DisplayName("id materials drive the model render bind")
    void model_should_bindIdMaterial() {
        StubProvider provider = new StubProvider();
        provider.metrics.put(Integer.valueOf(7), metrics(0, 1));
        RecordingTextures textures = new RecordingTextures();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(provider, new RecordingGlOps(), draws, new RecordingFrames(),
            new RecordingLights(), textures);
        Mesh mesh = new Mesh(3, 1, 0);
        mesh.anIntArray242 = new int[] { 60, 140, 100 };
        mesh.anIntArray244 = new int[] { 0, 0, 0 };
        mesh.anIntArray251 = new int[] { 400, 400, 600 };
        mesh.aShortArray117 = new short[] { 0 };
        mesh.aShortArray119 = new short[] { 1 };
        mesh.aShortArray120 = new short[] { 2 };
        mesh.anInt3668 = 3;
        mesh.anInt3686 = 1;
        Model model = toolkit.createModel(mesh, 0, 0, 64, 768);
        ((LwjglModel) model).setMaterial(new LwjglMaterial(7, true));
        model.render(null, null, 0);
        assertAll(
            () -> assertTrue(textures.calls.contains("image2Di(128,128)"),
                () -> "uploads missing: " + textures.calls),
            () -> assertTrue(textures.calls.contains("envi(34161,7681)"),
                () -> "rgb combine missing: " + textures.calls),
            () -> assertTrue(textures.calls.contains("envi(34162,7681)"),
                () -> "alpha combine missing: " + textures.calls),
            () -> assertEquals("[draw(4,3,5123,0)]", draws.calls.toString()));
    }

    @Test
    @DisplayName("ground render binds the idle material and draws the grid")
    void ground_should_renderIdleGrid() {
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), draws, frames, new RecordingLights(), textures);
        int[][] heights = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        Class65 ground = toolkit.method4297(2, 2, heights, new int[3][3], 0, 0);
        assertTrue(ground instanceof LwjglGround);
        ground.method4025(0, 0, 0, 0, null);
        assertAll(
            () -> assertTrue(((LwjglGround) ground).isBuilt()),
            () -> assertEquals(9, ((LwjglGround) ground).tileVertexCount()),
            () -> assertTrue(frames.calls.contains("disable(2896)")),
            () -> assertTrue(textures.calls.contains("unit(0)")),
            () -> assertEquals("[drawArrays(4,0,9)]", draws.calls.toString()));
    }

    @Test
    @DisplayName("masked sprite draw runs the two-unit pass with teardown")
    void sprite_should_drawMaskedTwoUnit() {
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        LwjglSprite sprite = new LwjglSprite(toolkit, 4, 2,
            new int[] { 1, 2, 3, 4, 5, 6, 7, 8 });
        LwjglMask mask = new LwjglMask(toolkit, 4, 2, new int[] { 0, 0 }, new int[] { 4, 4 });
        textures.calls.clear();
        frames.calls.clear();
        sprite.drawWithSurface(10, 20, mask, 2, 1);
        String texCalls = textures.calls.toString();
        String frameCalls = frames.calls.toString();
        assertAll(
            () -> assertTrue(texCalls.contains("envi(34161,7681), envi(34162,8448)"),
                () -> "unit-1 combine missing: " + texCalls),
            () -> assertTrue(texCalls.contains("envi(34176,34168), envi(34192,768)"),
                () -> "unit-1 operand missing: " + texCalls),
            () -> assertTrue(frameCalls.contains("mtc(33984,0.0,0.0), mtc(33985,2.0,9.5), v(10,20)"),
                () -> "first masked vertex wrong: " + frameCalls),
            () -> assertTrue(frameCalls.contains("mtc(33984,1.0,0.0), mtc(33985,3.0,9.5), v(14,20)"),
                () -> "last masked vertex wrong: " + frameCalls),
            () -> assertTrue(texCalls.contains("envi(34176,5890)"), () -> "teardown missing: " + texCalls),
            () -> assertTrue(texCalls.contains("disableT(3553)"), () -> "mask unbind missing: " + texCalls));
    }

    @Test
    @DisplayName("channel copy installs the mask and routes the next render through it")
    void sprite_should_copyChannelAndRenderMasked() {
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        toolkit.setViewport(0, 0, 800, 600);
        LwjglSprite sprite = new LwjglSprite(toolkit, 4, 2, new int[8]);
        textures.calls.clear();
        frames.calls.clear();
        sprite.copyAlpha(6406, 3, 2);
        assertAll(
            () -> assertNotNull(sprite.channelMaskTexture()),
            () -> assertEquals(2, sprite.channelMaskIndex()),
            () -> assertTrue(textures.calls.contains("transfer(3348,0.5)")),
            () -> assertTrue(textures.calls.contains("transfer(3349,0.499)")),
            () -> assertTrue(textures.calls.contains("copySub(3553,0,0,0,0,598,3,2)"),
                () -> "copy missing: " + textures.calls),
            () -> assertTrue(textures.calls.contains("transfer(3348,1.0)")));
        textures.calls.clear();
        frames.calls.clear();
        sprite.render(0, 0, 1, 0, 1);
        String texCalls = textures.calls.toString();
        String frameCalls = frames.calls.toString();
        assertAll(
            () -> assertTrue(texCalls.contains("envi(34161,7681), envi(34162,7681)"),
                () -> "unit-1 combine missing: " + texCalls),
            () -> assertTrue(texCalls.contains("envi(34161,34479), envi(34162,7681)"),
                () -> "dot3 combine missing: " + texCalls),
            () -> assertTrue(texCalls.contains("envColor(0.5,0.5,1.0,0.0)"),
                () -> "channel colour missing: " + texCalls),
            () -> assertTrue(frameCalls.contains("mtc(33985,0.0,0.0), tc(0.0,0.0), v(0,0)"),
                () -> "channel quad wrong: " + frameCalls),
            () -> assertTrue(texCalls.contains("envi(34184,5890)"), () -> "texgen reset missing: " + texCalls));
    }

    @Test
    @DisplayName("capture copies the framebuffer rect into the sprite")
    void sprite_should_captureScreenRegion() {
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
            new RecordingLights(), textures);
        toolkit.setViewport(0, 0, 800, 600);
        LwjglSprite sprite = new LwjglSprite(toolkit, 4, 2, new int[8]);
        textures.calls.clear();
        sprite.captureScreenRegion(1, 0, 2, 1, 9, 9);
        assertTrue(textures.calls.contains("copySub(3553,0,1,1,0,599,2,1)"),
            () -> "capture missing: " + textures.calls);
    }

    @Test
    @DisplayName("scaled draws tile unstretched and grid stretched sprites")
    void sprite_should_drawScaledGrids() {
        RecordingTextures textures = new RecordingTextures();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), frames,
            new RecordingLights(), textures);
        LwjglSprite sprite = new LwjglSprite(toolkit, 4, 2, new int[8]);
        textures.calls.clear();
        frames.calls.clear();
        sprite.drawScaledColored(10, 20, 8, 4, 1, 0xFF000000, 1);
        assertTrue(frames.calls.containsAll(java.util.Arrays.asList("tc(0.0,2.0)", "v(10,20)", "tc(2.0,0.0)",
            "v(18,24)")) && !frames.calls.contains("push"),
            () -> "tiled quad wrong: " + frames.calls);
        sprite.setOffsets(1, 0, 1, 0);
        frames.calls.clear();
        textures.calls.clear();
        sprite.drawScaledColored(0, 0, 6, 2, 1, 0, 1);
        String frameCalls = frames.calls.toString();
        assertAll(
            () -> assertTrue(frameCalls.contains("push"), () -> "grid missing push: " + frameCalls),
            () -> assertTrue(frameCalls.contains("translate(1.0,0.0,0.0)"),
                () -> "grid missing translate: " + frameCalls),
            () -> assertTrue(frameCalls.contains("v(4,2)"), () -> "grid missing tile: " + frameCalls),
            () -> assertTrue(frameCalls.contains("pop"), () -> "grid missing pop: " + frameCalls));
    }

    @Test
    @DisplayName("drawWithSurface rejects non-mask surfaces and dead sprites fail")
    void sprite_should_guardMaskedDraws() {
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(null, new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(),
            new RecordingLights(), textures);
        LwjglSprite sprite = new LwjglSprite(toolkit, 2, 2, new int[4]);
        assertThrows(RuntimeException.class, () -> sprite.drawWithSurface(0, 0, null, 0, 0));
        assertThrows(RuntimeException.class, () -> sprite.copyAlpha(6406, 0, 0));
        assertThrows(RuntimeException.class, () -> sprite.copyAlpha(6406, 2, 4));
        sprite.delete();
        assertThrows(RuntimeException.class, () -> sprite.drawScaledColored(0, 0, 2, 2, 1, 0, 1));
        assertFalse(sprite.channelMaskTexture() != null && !sprite.isDeleted());
    }
}
