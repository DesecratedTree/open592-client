package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;

@DisplayName("LWJGL lit scene: lights, culling, materials, combine")
class LwjglLitSceneTest {

    /** Recording LwjglGlOps fake. */
    private static final class RecordingGlOps implements LwjglGlOps {
        final List<String> calls = new ArrayList<>();
        int nextId = 41;

        @Override
        public int genBuffer() {
            calls.add("gen");
            return nextId++;
        }

        @Override
        public void bindBuffer(int target, int id) {
            calls.add("bind(" + target + "," + id + ")");
        }

        @Override
        public void upload(int target, byte[] data, int size, int usage) {
            calls.add("upload(" + target + "," + size + "," + usage + ")");
        }

        @Override
        public void subUpload(int target, long offset, byte[] data, int size) {
            calls.add("subUpload(" + target + "," + offset + "," + size + ")");
        }

        @Override
        public void deleteBuffer(int id) {
            calls.add("delete(" + id + ")");
        }
    }

    /** Recording LwjglDrawOps fake. */
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

    /** Recording LwjglFrameOps fake for caps, matrices and colour. */
    private static final class RecordingFrames implements LwjglFrameOps {
        final List<String> calls = new ArrayList<>();

        @Override
        public boolean supportsFramebufferObjects() {
            return true;
        }

        @Override
        public int genFramebuffer() {
            return 61;
        }

        @Override
        public void deleteFramebuffer(int id) {
        }

        @Override
        public void bindFramebuffer(int target, int id) {
        }

        @Override
        public int checkFramebufferStatus(int target) {
            return 36053;
        }

        @Override
        public void drawBuffer(int attachment) {
        }

        @Override
        public void readBuffer(int attachment) {
        }

        @Override
        public void blitFramebuffer(int sX0, int sY0, int sX1, int sY1, int dX0, int dY0, int dX1, int dY1, int mask, int filter) {
        }

        @Override
        public int genRenderbuffer() {
            return 71;
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
        }

        @Override
        public void pushMatrix() {
        }

        @Override
        public void popMatrix() {
        }

        @Override
        public void loadIdentity() {
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
        }

        @Override
        public void vertex2i(int x, int y) {
            calls.add("v(" + x + "," + y + ")");
        }

        @Override
        public void vertex2f(float x, float y) {
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
        public void translatef(float x, float y, float z) {
        }

        @Override
        public void blendFunc(int src, int dst) {
            calls.add("blend(" + src + "," + dst + ")");
        }

        @Override
        public void multMatrixf(float[] matrix) {
            calls.add("multMatrix(" + matrix.length + ")");
        }
    }

    /** Recording LwjglLightOps fake keeping parameter vectors. */
    private static final class RecordingLights implements LwjglLightOps {
        final List<String> calls = new ArrayList<>();
        final List<float[]> vectors = new ArrayList<>();

        @Override
        public void lightfv(int light, int pname, float[] params) {
            calls.add("light(" + light + "," + pname + "," + Arrays.toString(params) + ")");
            vectors.add(params.clone());
        }

        @Override
        public void lightf(int light, int pname, float value) {
            calls.add("lightf(" + light + "," + pname + "," + value + ")");
        }

        @Override
        public void lightModelfv(int pname, float[] params) {
            calls.add("model(" + pname + "," + Arrays.toString(params) + ")");
            vectors.add(params.clone());
        }
    }

    /** Compact recording LwjglTextureOps fake. */
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
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, byte[] pixels, int offset) {
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, int[] pixels, int offset) {
            calls.add("image2Di(" + width + "," + height + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, float[] pixels, int offset) {
        }

        @Override
        public void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format, int type) {
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, byte[] pixels, int offset) {
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, int[] pixels, int offset) {
        }

        @Override
        public void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height) {
        }

        @Override
        public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
        }

        @Override
        public void generateMipmap(int target) {
        }

        @Override
        public void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        }

        @Override
        public void flush() {
        }

        @Override
        public long createShader(int type) {
            return 101L;
        }

        @Override
        public void shaderSource(long shader, String source) {
        }

        @Override
        public void compileShader(long shader) {
        }

        @Override
        public int getObjectParam(long object, int pname) {
            return 1;
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
            return 201L;
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
            return 7;
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
            return 7;
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

    private static LwjglToolkit toolkit(RecordingGlOps glOps, RecordingDraws draws, RecordingFrames frames,
        RecordingLights lights, RecordingTextures textures) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setGlOpsForTest(glOps);
        toolkit.setDrawOpsForTest(draws);
        toolkit.setFrameOpsForTest(frames);
        toolkit.setLightOpsForTest(lights);
        toolkit.setTextureOpsForTest(textures);
        return toolkit;
    }

    private static LwjglToolkit toolkit(RecordingGlOps glOps, RecordingDraws draws, RecordingFrames frames, RecordingLights lights) {
        return toolkit(glOps, draws, frames, lights, new RecordingTextures());
    }

    /** Triangle centered on (100, 0, 500): in front of the near plane. */
    private static Mesh visibleMesh() {
        Mesh mesh = new Mesh(3, 1, 0);
        mesh.anIntArray242 = new int[] { 60, 140, 100 };
        mesh.anIntArray244 = new int[] { 0, 0, 0 };
        mesh.anIntArray251 = new int[] { 400, 400, 600 };
        mesh.aShortArray117 = new short[] { 0 };
        mesh.aShortArray119 = new short[] { 1 };
        mesh.aShortArray120 = new short[] { 2 };
        mesh.anInt3668 = 3;
        mesh.anInt3686 = 1;
        return mesh;
    }

    /** Triangle centered on (x, 0, z). */
    private static Mesh placedMesh(int x, int z) {
        Mesh mesh = new Mesh(3, 1, 0);
        mesh.anIntArray242 = new int[] { x - 1, x + 1, x };
        mesh.anIntArray244 = new int[] { 0, 0, 0 };
        mesh.anIntArray251 = new int[] { z, z, z + 1 };
        mesh.aShortArray117 = new short[] { 0 };
        mesh.aShortArray119 = new short[] { 1 };
        mesh.aShortArray120 = new short[] { 2 };
        mesh.anInt3668 = 3;
        mesh.anInt3686 = 1;
        return mesh;
    }

    @Test
    @DisplayName("sun setup normalizes and splits exactly like the legacy path")
    void lighting_should_setupSun() {
        RecordingLights lights = new RecordingLights();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(), lights);
        toolkit.lighting().setSunPosition(3.0F, 0.0F, 4.0F);
        toolkit.lighting().setSunColor(0xFF8040);
        toolkit.lighting().setBrightness(2.0F);
        toolkit.lighting().upload();

        assertAll(
            () -> assertArrayEquals(new float[] { 0.6F, 0.0F, 0.8F, 0.0F }, toolkit.lighting().sunDirection()),
            () -> assertEquals("light(16384,4611,[0.6, 0.0, 0.8, 0.0])", lights.calls.get(0)),
            () -> assertEquals("light(16385,4611,[-0.6, -0.0, -0.8, 0.0])", lights.calls.get(1)),
            () -> assertEquals(2.0F, lights.vectors.get(2)[0]),
            () -> assertEquals("model(2899," + Arrays.toString(lights.vectors.get(2)) + ")", lights.calls.get(2))
        );
    }

    @Test
    @DisplayName("zero sun vector keeps the previous direction")
    void lighting_should_keepDirection_onZeroSun() {
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(), new RecordingLights());
        toolkit.lighting().setSunPosition(0.0F, 1.0F, 0.0F);
        toolkit.lighting().setSunPosition(0.0F, 0.0F, 0.0F);

        assertArrayEquals(new float[] { 0.0F, 1.0F, 0.0F, 0.0F }, toolkit.lighting().sunDirection());
    }

    @Test
    @DisplayName("point lights upload position, diffuse and falloff, capped at four")
    void lighting_should_uploadPointLights() {
        RecordingLights lights = new RecordingLights();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), frames, lights);
        PointLight[] pointLights = new PointLight[] {
            new GpuPointLight(10, 20, 30, 5, 0x808080, 255.0F),
            new GpuPointLight(0, 0, 0, 1, 0, 0.0F),
            new GpuPointLight(0, 0, 0, 1, 0, 0.0F),
            new GpuPointLight(0, 0, 0, 1, 0, 0.0F),
            new GpuPointLight(0, 0, 0, 1, 0, 0.0F),
        };
        toolkit.copyLights(5, pointLights);

        assertAll(
            () -> assertEquals(4, toolkit.lighting().pointLightCount()),
            () -> {
                toolkit.lighting().upload();
                assertAll(
                    () -> assertEquals("light(16386,4611,[10.0, 20.0, 30.0, 1.0])", lights.calls.get(3)),
                    () -> assertEquals("light(16386,4609,[128.0, 128.0, 128.0, 1.0])", lights.calls.get(4)),
                    () -> assertEquals("lightf(16386,4617,0.04)", lights.calls.get(5)),
                    () -> assertTrue(frames.calls.contains("enable(16386)")),
                    () -> assertFalse(frames.calls.contains("enable(16390)"))
                );
            }
        );
    }

    @Test
    @DisplayName("empty slots disable their lights")
    void lighting_should_disableEmptySlots() {
        RecordingLights lights = new RecordingLights();
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), frames, lights);
        toolkit.copyLights(0, new PointLight[0]);
        toolkit.lighting().upload();

        assertAll(
            () -> assertEquals(0, toolkit.lighting().pointLightCount()),
            () -> assertEquals(
                "[disable(16386), disable(16387), disable(16388), disable(16389)]",
                frames.calls.toString())
        );
    }

    @Test
    @DisplayName("toolkit creates GPU point lights")
    void toolkit_should_createPointLight() {
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(), new RecordingLights());

        assertTrue(toolkit.createPointLight(1, 2, 3, 4, 0xFFFFFF, 1.0F) instanceof GpuPointLight);
    }

    @ParameterizedTest(name = "mesh at ({0},{1}) draws={2}")
    @CsvSource({
        "100,  500,  true",
        "0,    5,    false",
        "0,    4000, true",
        "2000, 500,  false",
        "-2000, 500, false",
        "30,   60,   true",
    })
    @DisplayName("off-screen models skip their draws")
    void render_should_cullOffScreen(int x, int z, boolean draws) {
        RecordingGlOps glOps = new RecordingGlOps();
        RecordingDraws drawOps = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(glOps, drawOps, new RecordingFrames(), new RecordingLights());
        Model model = toolkit.createModel(placedMesh(x, z), 0, 0, 64, 768);

        model.render(null, null, 0);
        model.render(null, null, 0);

        if (draws) {
            assertEquals("[draw(4,3,5123,0), draw(4,3,5123,0)]", drawOps.calls.toString());
        } else {
            assertAll(
                () -> assertTrue(drawOps.calls.isEmpty()),
                () -> assertTrue(((LwjglModel) model).isUploaded())
            );
        }
    }

    @Test
    @DisplayName("view translation moves models in and out of view")
    void render_should_cullThroughViewMatrix() {
        RecordingDraws drawOps = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), drawOps, new RecordingFrames(), new RecordingLights());
        Model model = toolkit.createModel(visibleMesh(), 0, 0, 64, 768);

        model.render(null, null, 0);
        toolkit.setViewMatrix(new float[] {
            1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
            0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, -600.0F, 1.0F });
        model.render(null, null, 0);

        assertAll(
            () -> assertEquals(1, drawOps.calls.size()),
            () -> assertThrows(RuntimeException.class, () -> toolkit.setViewMatrix(new float[15])),
            () -> assertThrows(RuntimeException.class, () -> toolkit.setViewMatrix(null)),
            () -> assertEquals(16, toolkit.viewMatrix().length)
        );
    }

    @Test
    @DisplayName("widening the frustum reveals lateral models")
    void render_should_followFrustum() {
        RecordingDraws drawOps = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), drawOps, new RecordingFrames(), new RecordingLights());
        Model model = toolkit.createModel(placedMesh(2000, 500), 0, 0, 64, 768);

        model.render(null, null, 0);
        toolkit.setFrustum(-5.0F, 5.0F, -5.0F, 5.0F);
        model.render(null, null, 0);

        assertEquals(1, drawOps.calls.size());
    }

    @Test
    @DisplayName("visible models upload lights, matrix and material before drawing")
    void render_should_lightAndBindMaterial() {
        RecordingGlOps glOps = new RecordingGlOps();
        RecordingDraws drawOps = new RecordingDraws();
        RecordingFrames frames = new RecordingFrames();
        RecordingLights lights = new RecordingLights();
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(glOps, drawOps, frames, lights, textures);
        LwjglModel model = (LwjglModel) toolkit.createModel(visibleMesh(), 0, 0, 64, 768);
        int[] pixels = new int[16];
        LwjglMaterial material = new LwjglMaterial(((LwjglSprite) toolkit.method4265(pixels, 4, 4, 4)).texture(), 1);
        model.setMaterial(material);

        model.render(null, null, 0);

        assertAll(
            () -> assertTrue(lights.calls.get(0).startsWith("light(16384,4611,")),
            () -> assertTrue(frames.calls.contains("enable(2896)")),
            () -> assertTrue(frames.calls.contains("multMatrix(16)")),
            () -> assertTrue(textures.calls.contains("bindT(3553,41)")),
            () -> assertTrue(frames.calls.contains("color(1.0,1.0,1.0,1.0)")),
            () -> assertEquals("[draw(4,3,5123,0)]", drawOps.calls.toString()),
            () -> assertThrows(RuntimeException.class, () -> new LwjglMaterial(null, 1)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglMaterial(material.texture(), 5))
        );
    }

    @Test
    @DisplayName("sprites install tint and blend from the combine call")
    void sprite_should_tintAndBlend() {
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), frames, new RecordingLights(),
            new RecordingTextures());
        LwjglSprite sprite = (LwjglSprite) toolkit.method4265(new int[4], 2, 2, 2);

        sprite.render(0, 0, 1, 0x80FF0000, 1);

        assertTrue(frames.calls.containsAll(Arrays.asList("color(1.0,0.0,0.0,0.5019608)", "enable(3042)", "blend(770,771)")));
    }

    @Test
    @DisplayName("sprites switch blending off and reject bad modes")
    void sprite_should_gateBlendModes() {
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), frames, new RecordingLights(),
            new RecordingTextures());
        LwjglSprite sprite = (LwjglSprite) toolkit.method4265(new int[4], 2, 2, 2);

        sprite.render(0, 0, 1, 0xFFFFFFFF, 0);

        assertAll(
            () -> assertTrue(frames.calls.contains("disable(3042)")),
            () -> assertFalse(frames.calls.contains("blend(770,771)")),
            () -> assertThrows(RuntimeException.class, () -> sprite.render(0, 0, 5, 0, 1)),
            () -> assertThrows(RuntimeException.class, () -> sprite.render(0, 0, 1, 0, 3)),
            () -> assertThrows(RuntimeException.class, () -> toolkit.applyCombine(-1, 0, 1)),
            () -> assertThrows(RuntimeException.class, () -> toolkit.loadMatrix(new float[3]))
        );

        frames.calls.clear();
        sprite.render(0, 0, 1, 0xFFFFFFFF, 2);
        assertTrue(frames.calls.contains("blend(1,1)"));
    }
}
