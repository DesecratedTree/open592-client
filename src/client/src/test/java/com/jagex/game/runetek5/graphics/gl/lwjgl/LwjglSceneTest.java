package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;

@DisplayName("LWJGL HD scene wiring: models and grounds")
class LwjglSceneTest {

    /** Recording LwjglGlOps fake that also keeps uploaded bytes. */
    private static final class RecordingOps implements LwjglGlOps {
        final List<String> calls = new ArrayList<>();
        final List<byte[]> uploads = new ArrayList<>();
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
            byte[] copy = new byte[size];
            System.arraycopy(data, 0, copy, 0, size);
            uploads.add(copy);
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

    /** Recording LwjglDrawOps fake: no GL context needed. */
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

    /** No-op frame fake: model renders only need caps and matrices. */
    private static final class RecordingFrames implements LwjglFrameOps {
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
        }

        @Override
        public void end() {
        }

        @Override
        public void texCoord2f(float s, float t) {
        }

        @Override
        public void multiTexCoord2f(int unit, float s, float t) {
        }

        @Override
        public void vertex2i(int x, int y) {
        }

        @Override
        public void vertex2f(float x, float y) {
        }

        @Override
        public void enableCap(int cap) {
        }

        @Override
        public void disableCap(int cap) {
        }

        @Override
        public void texGen(int coordinate, int pname, float[] params) {
        }

        @Override
        public void color4f(float r, float g, float b, float a) {
        }

        @Override
        public void translatef(float x, float y, float z) {
        }

        @Override
        public void blendFunc(int src, int dst) {
        }

        @Override
        public void multMatrixf(float[] matrix) {
        }
    }

    /** No-op light fake: model renders only need silent uploads. */
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

    /** Recording texture fake: only unit selection is observed. */
    private static final class RecordingTextures implements LwjglTextureOps {
        final List<String> calls = new ArrayList<>();
        int nextId = 41;

        @Override
        public int genTexture() {
            return nextId++;
        }

        @Override
        public void deleteTexture(int id) {
        }

        @Override
        public void bindTexture(int target, int id) {
            calls.add("bindT(" + target + "," + id + ")");
        }

        @Override
        public void enableTarget(int target) {
        }

        @Override
        public void disableTarget(int target) {
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
        }

        @Override
        public void pixelStorei(int pname, int param) {
        }

        @Override
        public void pixelTransferf(int pname, float param) {
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type, byte[] pixels, int offset) {
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format,
            int type, int[] pixels, int offset) {
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
        }

        @Override
        public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width,
            int height) {
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

    private static LwjglToolkit toolkit(RecordingOps glOps, RecordingDraws draws) {
        return toolkit(glOps, draws, new RecordingTextures());
    }

    private static LwjglToolkit toolkit(RecordingOps glOps, RecordingDraws draws, RecordingTextures textures) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setGlOpsForTest(glOps);
        toolkit.setDrawOpsForTest(draws);
        toolkit.setFrameOpsForTest(new RecordingFrames());
        toolkit.setLightOpsForTest(new RecordingLights());
        toolkit.setTextureOpsForTest(textures);
        return toolkit;
    }

    /** Single triangle placed past the near plane so it stays visible. */
    private static Mesh triangleMesh() {
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

    @Test
    @DisplayName("createModel returns an un-uploaded LWJGL model")
    void createModel_should_returnRenderable() {
        RecordingOps glOps = new RecordingOps();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(glOps, draws);
        Mesh mesh = triangleMesh();

        Model model = toolkit.createModel(mesh, 0, 0, 64, 768);

        assertAll(
            () -> assertInstanceOf(LwjglModel.class, model),
            () -> assertSame(mesh, ((LwjglModel) model).mesh()),
            () -> assertFalse(((LwjglModel) model).isUploaded()),
            () -> assertTrue(glOps.calls.isEmpty())
        );
    }

    @Test
    @DisplayName("first render uploads once, later renders only draw")
    void render_should_uploadOnce_then_draw() {
        RecordingOps glOps = new RecordingOps();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(glOps, draws);
        LwjglModel model = (LwjglModel) toolkit.createModel(triangleMesh(), 0, 0, 64, 768);

        model.render(null, null, 0);
        model.render(null, null, 0);

        assertAll(
            () -> assertTrue(model.isUploaded()),
            () -> assertEquals(42, toolkit.IA()),
            () -> assertEquals(2, glOps.calls.stream().filter(c -> c.equals("gen")).count()),
            () -> assertEquals(
                "[draw(4,3,5123,0), draw(4,3,5123,0)]",
                draws.calls.toString()),
            () -> assertArrayEquals(
                new byte[] { 0, 0, 0x70, 0x42 },
                new byte[] { glOps.uploads.get(0)[0], glOps.uploads.get(0)[1], glOps.uploads.get(0)[2], glOps.uploads.get(0)[3] })
        );
    }

    @Test
    @DisplayName("large meshes encode with int indices")
    void encode_should_useIntIndices_forLargeMeshes() {
        Mesh mesh = new Mesh(65536, 1, 0);
        mesh.anIntArray242 = new int[65536];
        mesh.anIntArray244 = new int[65536];
        mesh.anIntArray251 = new int[65536];
        mesh.aShortArray117 = new short[] { 0 };
        mesh.aShortArray119 = new short[] { 1 };
        mesh.aShortArray120 = new short[] { 2 };
        mesh.anInt3668 = 65536;
        mesh.anInt3686 = 1;

        LwjglMeshData data = LwjglModel.encode(mesh);

        assertAll(
            () -> assertEquals(5125, data.indexType()),
            () -> assertEquals(3, data.indexCount()),
            () -> assertEquals(12, data.indexSize()),
            () -> assertEquals(65536 * 12, data.vertexSize())
        );
    }

    @Test
    @DisplayName("mesh encoder rejects bad input")
    void encode_should_rejectBadMeshes() {
        Mesh empty = new Mesh(1, 1, 0);

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> LwjglModel.encode(null)),
            () -> assertThrows(RuntimeException.class, () -> LwjglModel.encode(empty)),
            () -> assertThrows(RuntimeException.class,
                () -> new LwjglModel(toolkit(new RecordingOps(), new RecordingDraws()), null, 0, 0, 0, 0)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglMeshData(null, new byte[2], 5123, 1, 0.0F, 0.0F, 0.0F, 0.0F))
        );
    }

    @Test
    @DisplayName("ground builds its tile grid from the real height arrays")
    void ground_should_buildFromRealHeights() {
        RecordingOps glOps = new RecordingOps();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(glOps, draws);
        int[][] heights = new int[][] {
            { 0, 1, 2 },
            { 3, 50, 5 },
            { 6, 7, 8 },
        };

        LwjglGround ground = (LwjglGround) toolkit.method4297(2, 2, heights, new int[3][3], 0, 0);

        assertAll(
            () -> assertEquals(50, ground.l(1, 1)),
            () -> assertEquals(50, ground.a(1, 1)),
            () -> assertEquals(2, ground.l(-5, 99)),
            () -> assertFalse(ground.isBuilt()),
            () -> assertNull(ground.tileBuffer())
        );

        ground.buildTiles();

        assertAll(
            () -> assertTrue(ground.isBuilt()),
            () -> assertEquals(216, toolkit.IA()),
            () -> assertEquals(5123, ground.tileBuffer().method5182()),
            () -> assertEquals(
                "[gen, bind(34963,41), upload(34963,216,35044)]",
                glOps.calls.toString()),
            () -> assertArrayEquals(
                new byte[] { 0, 0, 0x48, 0x42 },
                new byte[] {
                    glOps.uploads.get(0)[100], glOps.uploads.get(0)[101],
                    glOps.uploads.get(0)[102], glOps.uploads.get(0)[103] })
        );
    }

    @Test
    @DisplayName("ground keeps flags, renders the idle grid and fast-fails dynamic editing")
    void ground_should_keepFlags_and_rejectDynamicEdits() {
        RecordingDraws draws = new RecordingDraws();
        RecordingTextures textures = new RecordingTextures();
        LwjglToolkit toolkit = toolkit(new RecordingOps(), draws, textures);
        LwjglGround ground = (LwjglGround) toolkit.method4297(2, 2, new int[3][3], new int[3][3], 7, 3);

        ground.method4025(0, 0, 0, 0, null);

        assertAll(
            () -> assertEquals(7, ground.groundFlags()),
            () -> assertEquals(3, ground.featureFlags()),
            () -> assertNull(ground.k(0, 0, null)),
            () -> assertFalse(ground.method4022(null, 0, 0, 0)),
            () -> assertTrue(ground.isBuilt()),
            () -> assertEquals(9, ground.tileVertexCount()),
            () -> assertEquals("[drawArrays(4,0,9)]", draws.calls.toString()),
            () -> assertEquals("[unit(0)]", textures.calls.toString()),
            () -> assertThrows(RuntimeException.class,
                () -> ground.method4023(0, 0, null, null, null, null, null, null, null, null, null, null, null, 0, 0, 0)),
            () -> assertThrows(RuntimeException.class,
                () -> ground.B(0, 0, null, null, null, null, null, null, null, null, 0, 0, 0, false)),
            () -> assertThrows(RuntimeException.class, () -> ground.method4029(0, 0, 0, null, false))
        );
    }

    @Test
    @DisplayName("toolkit scene paths render every model through the draw seam")
    void toolkitScene_should_renderModelLists() {
        RecordingOps glOps = new RecordingOps();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(glOps, draws);
        Model first = toolkit.createModel(triangleMesh(), 0, 0, 64, 768);
        Model second = toolkit.createModel(triangleMesh(), 0, 0, 64, 768);

        toolkit.method4307(new Model[] { first, null, second }, null, null, 0);
        toolkit.method4299(new Model[] { first }, null, null, null, 0);
        toolkit.method4262(second, null, null, null);

        assertAll(
            () -> assertEquals(4, draws.calls.size()),
            () -> assertTrue(draws.calls.stream().allMatch(c -> c.equals("draw(4,3,5123,0)"))),
            () -> assertSame(toolkit.method4289(), toolkit.method4289()),
            () -> assertTrue(toolkit.method4274() != null)
        );
    }
}
