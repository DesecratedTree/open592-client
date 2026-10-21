package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;

@DisplayName("LWJGL scene matrix: ea, projection, frustum, model placement")
class LwjglSceneMatrixTest {

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

    /** Recording frame fake with matrix calls tracked. */
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
            calls.add("viewport(" + width + "," + height + ")");
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
            calls.add("pushMatrix");
        }

        @Override
        public void popMatrix() {
            calls.add("popMatrix");
        }

        @Override
        public void loadIdentity() {
            calls.add("loadIdentity");
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
        }

        @Override
        public void translatef(float x, float y, float z) {
        }

        @Override
        public void blendFunc(int src, int dst) {
        }

        @Override
        public void multMatrixf(float[] matrix) {
            calls.add("multMatrix(" + matrix.length + ")");
        }
    }

    /** Recording LwjglLightOps fake. */
    private static final class RecordingLights implements LwjglLightOps {
        final List<String> calls = new ArrayList<>();

        @Override
        public void lightfv(int light, int pname, float[] params) {
            calls.add("light(" + light + "," + pname + ")");
        }

        @Override
        public void lightf(int light, int pname, float value) {
            calls.add("lightf(" + light + "," + pname + "," + value + ")");
        }

        @Override
        public void lightModelfv(int pname, float[] params) {
            calls.add("model(" + pname + ")");
        }
    }

    private static LwjglToolkit toolkit(RecordingGlOps glOps, RecordingDraws draws, RecordingFrames frames,
        RecordingLights lights) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setGlOpsForTest(glOps);
        toolkit.setDrawOpsForTest(draws);
        toolkit.setFrameOpsForTest(frames);
        toolkit.setLightOpsForTest(lights);
        return toolkit;
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
    @DisplayName("ea installs the view matrix and refreshes the frame")
    void ea_should_installViewMatrix() {
        RecordingFrames frames = new RecordingFrames();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), frames, new RecordingLights());
        JavaMatrix view = new JavaMatrix();
        view.o(100, 0, 0);

        toolkit.ea(view);

        assertAll(
            () -> assertEquals(100.0F, toolkit.viewMatrix()[12]),
            () -> assertTrue(frames.calls.contains("loadIdentity")),
            () -> assertTrue(frames.calls.contains("multMatrix(16)"))
        );
    }

    @Test
    @DisplayName("H rebuilds the perspective projection from centers and scales")
    void projection_should_followCenterAndScale() {
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(), new RecordingLights());
        toolkit.setViewport(0, 0, 512, 512);

        toolkit.H(256, 256, 512, 512);
        float[] projection = toolkit.projectionMatrix();

        assertAll(
            () -> assertEquals(2.0F, projection[0]),
            () -> assertEquals(2.0F, projection[5]),
            () -> assertEquals(0.0F, projection[8]),
            () -> assertEquals(-1.0F, projection[11])
        );
    }

    @Test
    @DisplayName("OA plus H narrows the culling frustum from the NDC default")
    void frustum_should_deriveFromClipAndScale() {
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), draws, new RecordingFrames(), new RecordingLights());
        Model lateral = toolkit.createModel(placedMesh(300, 500), 0, 0, 64, 768);

        lateral.render(null, null, 0);
        toolkit.setViewport(0, 0, 512, 512);
        toolkit.OA(0, 0, 512, 512);
        toolkit.H(256, 256, 512, 512);
        lateral.render(null, null, 0);

        assertAll(
            () -> assertEquals(1, draws.calls.size()),
            () -> assertTrue(toolkit.visible(0.0F, 0.0F, 500.0F, 1.0F)),
            () -> assertTrue(!toolkit.visible(300.0F, 0.0F, 500.0F, 1.0F))
        );
    }

    @Test
    @DisplayName("model matrices copy the real JavaMatrix instead of identity")
    void modelMatrix_should_useRealMatrix() {
        JavaMatrix placed = new JavaMatrix();
        placed.o(30, 0, 0);
        float[] real = LwjglSceneMatrix.modelMatrix(placed);
        float[] fallback = LwjglSceneMatrix.modelMatrix(null);

        assertAll(
            () -> assertEquals(30.0F, real[12]),
            () -> assertEquals(1.0F, fallback[0]),
            () -> assertEquals(0.0F, fallback[12])
        );
    }

    @Test
    @DisplayName("model draws push the placement, draw, then pop")
    void render_should_bracketDrawsWithPushPop() {
        RecordingFrames frames = new RecordingFrames();
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), draws, frames, new RecordingLights());
        Model model = toolkit.createModel(placedMesh(100, 500), 0, 0, 64, 768);

        model.render(null, null, 0);

        int push = frames.calls.indexOf("pushMatrix");
        int mult = frames.calls.indexOf("multMatrix(16)");
        int pop = frames.calls.indexOf("popMatrix");
        assertAll(
            () -> assertEquals("[draw(4,3,5123,0)]", draws.calls.toString()),
            () -> assertTrue(push >= 0),
            () -> assertTrue(mult > push),
            () -> assertTrue(pop > mult)
        );
    }

    @Test
    @DisplayName("beyond-far spheres stay visible for the projection to clip")
    void visible_should_ignoreFarPlane() {
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), new RecordingDraws(), new RecordingFrames(), new RecordingLights());

        assertTrue(toolkit.visible(0.0F, 0.0F, 4000.0F, 1.0F));
    }

    @Test
    @DisplayName("ea view translation moves models behind the near plane")
    void render_should_followEaView() {
        RecordingDraws draws = new RecordingDraws();
        LwjglToolkit toolkit = toolkit(new RecordingGlOps(), draws, new RecordingFrames(), new RecordingLights());
        Model model = toolkit.createModel(placedMesh(100, 500), 0, 0, 64, 768);
        JavaMatrix shifted = new JavaMatrix();
        shifted.o(0, 0, -600);

        model.render(null, null, 0);
        toolkit.ea(shifted);
        model.render(null, null, 0);

        assertAll(
            () -> assertEquals(1, draws.calls.size()),
            () -> assertTrue(((LwjglModel) model).isUploaded())
        );
    }
}
