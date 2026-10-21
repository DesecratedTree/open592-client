package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("LWJGL framebuffers and post pipeline")
class LwjglFrameTest {

    /** Recording LwjglFrameOps fake: no GL context needed. */
    private static final class RecordingFrameOps implements LwjglFrameOps {
        final List<String> calls = new ArrayList<>();
        boolean supported = true;
        int nextFboId = 61;
        int nextRbId = 71;
        int status = 36053;

        @Override
        public boolean supportsFramebufferObjects() {
            return supported;
        }

        @Override
        public int genFramebuffer() {
            int id = nextFboId++;
            calls.add("genFBO()=" + id);
            return id;
        }

        @Override
        public void deleteFramebuffer(int id) {
            calls.add("deleteFBO(" + id + ")");
        }

        @Override
        public void bindFramebuffer(int target, int id) {
            calls.add("bindFB(" + target + "," + id + ")");
        }

        @Override
        public int checkFramebufferStatus(int target) {
            calls.add("checkFB(" + target + ")=" + status);
            return status;
        }

        @Override
        public void drawBuffer(int attachment) {
            calls.add("drawBuf(" + attachment + ")");
        }

        @Override
        public void readBuffer(int attachment) {
            calls.add("readBuf(" + attachment + ")");
        }

        @Override
        public void blitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
            calls.add("blit(" + srcX0 + "," + srcY0 + "," + srcX1 + "," + srcY1 + "," + dstX0 + "," + dstY0 + "," + dstX1 + "," + dstY1 + "," + mask + "," + filter + ")");
        }

        @Override
        public int genRenderbuffer() {
            int id = nextRbId++;
            calls.add("genRB()=" + id);
            return id;
        }

        @Override
        public void deleteRenderbuffer(int id) {
            calls.add("deleteRB(" + id + ")");
        }

        @Override
        public void bindRenderbuffer(int id) {
            calls.add("bindRB(" + id + ")");
        }

        @Override
        public void renderbufferStorage(int format, int width, int height) {
            calls.add("storage(" + format + "," + width + "," + height + ")");
        }

        @Override
        public void renderbufferStorageMultisample(int samples, int format, int width, int height) {
            calls.add("storageMS(" + samples + "," + format + "," + width + "," + height + ")");
        }

        @Override
        public void framebufferRenderbuffer(int target, int attachment, int renderbuffer) {
            calls.add("fboRB(" + target + "," + attachment + "," + renderbuffer + ")");
        }

        @Override
        public void viewport(int x, int y, int width, int height) {
            calls.add("viewport(" + x + "," + y + "," + width + "," + height + ")");
        }

        @Override
        public void pushAttrib(int mask) {
            calls.add("pushAttrib(" + mask + ")");
        }

        @Override
        public void popAttrib() {
            calls.add("popAttrib");
        }

        @Override
        public void matrixMode(int mode) {
            calls.add("matrixMode(" + mode + ")");
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
            calls.add("ortho");
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
            calls.add("enableCap(" + cap + ")");
        }

        @Override
        public void disableCap(int cap) {
            calls.add("disableCap(" + cap + ")");
        }

        @Override
        public void texGen(int coordinate, int pname, float[] params) {
            calls.add("texGen(" + coordinate + "," + pname + "," + params.length + ")");
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

    /** Compact recording LwjglTextureOps fake for frame tests. */
    private static final class RecordingTextureOps implements LwjglTextureOps {
        final List<String> calls = new ArrayList<>();
        int nextTextureId = 41;
        long nextShaderHandle = 101L;
        long nextProgramHandle = 201L;
        int nextArbId = 7;
        int objectStatus = 1;
        int errorPosition = -1;

        @Override
        public int genTexture() {
            int id = nextTextureId++;
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
            calls.add("store(" + pname + "," + param + ")");
        }

        @Override
        public void pixelTransferf(int pname, float param) {
            calls.add("transfer(" + pname + "," + param + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, byte[] pixels, int offset) {
            calls.add("image2Db(" + width + "," + height + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, int[] pixels, int offset) {
            calls.add("image2Di(" + width + "," + height + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, float[] pixels, int offset) {
            calls.add("image2Df(" + width + "," + height + ")");
        }

        @Override
        public void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format, int type) {
            calls.add("alloc2D(" + target + "," + internalFormat + "," + width + "," + height + ")");
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, byte[] pixels, int offset) {
            calls.add("sub2D");
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, int[] pixels, int offset) {
            calls.add("sub2D");
        }

        @Override
        public void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height) {
            calls.add("copy2D");
        }

        @Override
        public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
            calls.add("copySub2D");
        }

        @Override
        public void generateMipmap(int target) {
            calls.add("mipmap(" + target + ")");
        }

        @Override
        public void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
            calls.add("tFbo(" + target + "," + attachment + "," + textarget + "," + texture + "," + level + ")");
        }

        @Override
        public void flush() {
            calls.add("flush");
        }

        @Override
        public long createShader(int type) {
            long handle = nextShaderHandle++;
            calls.add("createShader(" + type + ")=" + handle);
            return handle;
        }

        @Override
        public void shaderSource(long shader, String source) {
            calls.add("source(" + shader + ")");
        }

        @Override
        public void compileShader(long shader) {
            calls.add("compile(" + shader + ")");
        }

        @Override
        public int getObjectParam(long object, int pname) {
            calls.add("objParam(" + object + "," + pname + ")=" + objectStatus);
            return objectStatus;
        }

        @Override
        public String getObjectInfoLog(long object) {
            return "";
        }

        @Override
        public void deleteObject(long object) {
            calls.add("deleteObj(" + object + ")");
        }

        @Override
        public long createProgram() {
            long handle = nextProgramHandle++;
            calls.add("createProgram()=" + handle);
            return handle;
        }

        @Override
        public void attachShader(long program, long shader) {
            calls.add("attach(" + program + "," + shader + ")");
        }

        @Override
        public void detachShader(long program, long shader) {
            calls.add("detach(" + program + "," + shader + ")");
        }

        @Override
        public void linkProgram(long program) {
            calls.add("link(" + program + ")");
        }

        @Override
        public void useProgram(long program) {
            calls.add("use(" + program + ")");
        }

        @Override
        public int getUniformLocation(long program, String name) {
            calls.add("loc(" + program + "," + name + ")");
            return 7;
        }

        @Override
        public void uniform1i(int location, int value) {
            calls.add("u1i(" + location + "," + value + ")");
        }

        @Override
        public void uniform1f(int location, float value) {
            calls.add("u1f(" + location + "," + value + ")");
        }

        @Override
        public void uniform2f(int location, float x, float y) {
            calls.add("u2f(" + location + "," + x + "," + y + ")");
        }

        @Override
        public void uniform3f(int location, float x, float y, float z) {
            calls.add("u3f(" + location + "," + x + "," + y + "," + z + ")");
        }

        @Override
        public void uniform4f(int location, float x, float y, float z, float w) {
            calls.add("u4f(" + location + "," + x + "," + y + "," + z + "," + w + ")");
        }

        @Override
        public void programLocalParameter4f(int target, int index, float x, float y, float z, float w) {
            calls.add("localParam(" + target + "," + index + "," + x + "," + y + "," + z + "," + w + ")");
        }

        @Override
        public int genArbProgram() {
            calls.add("genArb()=" + nextArbId);
            return nextArbId++;
        }

        @Override
        public void bindArbProgram(int target, int id) {
            calls.add("bindArb(" + target + "," + id + ")");
        }

        @Override
        public void programString(int target, int format, String source) {
            calls.add("progStr(" + target + "," + format + ")");
        }

        @Override
        public int programErrorPosition() {
            calls.add("errPos()=" + errorPosition);
            return errorPosition;
        }
    }

    /** Stub post effect recording present/finish calls. */
    private static final class StubEffect implements LwjglPostEffect {
        int presents;
        int finishes;
        LwjglTexture2D lastScene;
        boolean active = true;
        boolean setupResult = true;

        @Override
        public boolean isAvailable() {
            return false;
        }

        @Override
        public boolean setup() {
            return setupResult;
        }

        @Override
        public void present(int pass, LwjglTexture2D scene, LwjglTexture2D depth) {
            presents++;
            lastScene = scene;
        }

        @Override
        public void finish(int pass) {
            finishes++;
        }

        @Override
        public void resize(int width, int height) {
        }

        @Override
        public void release() {
        }

        @Override
        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        public boolean isActive() {
            return active;
        }
    }

    private static LwjglToolkit toolkit(RecordingTextureOps textureOps, RecordingFrameOps frameOps) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setTextureOpsForTest(textureOps);
        toolkit.setFrameOpsForTest(frameOps);
        return toolkit;
    }

    private static LwjglTexture2D rectTexture(LwjglToolkit toolkit, RecordingTextureOps ops, int size) {
        return new LwjglTexture2D(toolkit, LwjglTexture.TARGET_2D, 6408, size, size, ops);
    }

    @ParameterizedTest(name = "attachmentForSlot({0}) == {1}")
    @CsvSource({
        "0, 36064",
        "1, 36065",
        "7, 36071",
        "8, 36096"
    })
    @DisplayName("attachment slots mirror the legacy table")
    void attachmentForSlot_should_mapLegacyTable(int slot, int expected) {
        assertEquals(expected, LwjglFramebuffer.attachmentForSlot(slot));
    }

    @Test
    @DisplayName("attachment slots reject out-of-range slots")
    void attachmentForSlot_should_reject_badSlots() {
        assertAll(
            () -> assertThrows(RuntimeException.class, () -> LwjglFramebuffer.attachmentForSlot(-1)),
            () -> assertThrows(RuntimeException.class, () -> LwjglFramebuffer.attachmentForSlot(9))
        );
    }

    @Test
    @DisplayName("framebuffer creates, binds, attaches, checks and deletes")
    void framebuffer_should_bindAttachCheckAndDelete() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglTexture2D texture = rectTexture(toolkit, textureOps, 4);
        textureOps.calls.clear();
        frameOps.calls.clear();

        LwjglFramebuffer fbo = new LwjglFramebuffer(toolkit, frameOps);
        fbo.bind();
        fbo.attachTexture(texture, 0, textureOps);
        fbo.selectDrawBuffer(0);
        boolean complete = fbo.checkComplete();
        fbo.detachSlot(0, textureOps, frameOps);
        fbo.unbind();
        fbo.delete();
        fbo.delete();

        assertAll(
            () -> assertTrue(complete),
            () -> assertTrue(fbo.isDeleted()),
            () -> assertEquals(
                "[genFBO()=61, bindFB(36160,61), drawBuf(36064), checkFB(36160)=36053, "
                    + "bindFB(36160,0), deleteFBO(61)]",
                frameOps.calls.toString()),
            () -> assertEquals(
                "[tFbo(36160,36064,3553,41,0), tFbo(36160,36064,3553,0,0)]",
                textureOps.calls.toString())
        );
    }

    @Test
    @DisplayName("framebuffer guards unbound use and size mismatches")
    void framebuffer_should_guardUnboundUse() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglFramebuffer fbo = new LwjglFramebuffer(toolkit, frameOps);
        LwjglTexture2D small = rectTexture(toolkit, textureOps, 4);
        LwjglTexture2D big = rectTexture(toolkit, textureOps, 8);

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> fbo.attachTexture(small, 0, textureOps)),
            () -> assertThrows(RuntimeException.class, () -> fbo.selectDrawBuffer(0)),
            () -> assertThrows(RuntimeException.class, () -> fbo.checkComplete()),
            () -> {
                fbo.bind();
                fbo.attachTexture(small, 0, textureOps);
                assertThrows(RuntimeException.class, () -> fbo.attachTexture(big, 1, textureOps));
            }
        );
    }

    @Test
    @DisplayName("renderbuffer allocates, attaches and disposes with byte math")
    void renderbuffer_should_allocateAndDispose() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);

        LwjglRenderbuffer plain = new LwjglRenderbuffer(toolkit, 6402, 4, 4, frameOps);
        int bytes = plain.byteSize();
        plain.dispose();
        plain.dispose();
        LwjglRenderbuffer multi = new LwjglRenderbuffer(toolkit, 6408, 8, 8, 2, frameOps);
        multi.dispose();

        assertAll(
            () -> assertEquals(48, bytes),
            () -> assertEquals(0, toolkit.IA()),
            () -> assertTrue(frameOps.calls.toString().contains("storage(6402,4,4)")),
            () -> assertTrue(frameOps.calls.toString().contains("storageMS(2,6408,8,8)")),
            () -> assertTrue(frameOps.calls.toString().contains("deleteRB(71)")),
            () -> assertTrue(frameOps.calls.toString().contains("deleteRB(72)"))
        );
    }

    @Test
    @DisplayName("toolkit framebuffer stack binds with push/pop discipline")
    void toolkitStack_should_pushPopWithDiscipline() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglFramebuffer first = new LwjglFramebuffer(toolkit, frameOps);
        LwjglFramebuffer second = new LwjglFramebuffer(toolkit, frameOps);
        frameOps.calls.clear();

        toolkit.pushFramebuffer(first);
        toolkit.pushFramebuffer(second);
        toolkit.popFramebuffer(second);
        toolkit.popFramebuffer(first);

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> toolkit.popFramebuffer(first)),
            () -> assertThrows(RuntimeException.class, () -> toolkit.pushFramebuffer(null)),
            () -> assertEquals(
                "[bindFB(36160,61), bindFB(36160,0), bindFB(36160,62), "
                    + "bindFB(36160,0), bindFB(36160,61), bindFB(36160,0)]",
                frameOps.calls.toString())
        );
    }

    @Test
    @DisplayName("toolkit framebuffer stack rejects overflow")
    void toolkitStack_should_rejectOverflow() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglFramebuffer[] fbos = new LwjglFramebuffer[4];
        for (int i = 0; i < 4; i++) {
            fbos[i] = new LwjglFramebuffer(toolkit, frameOps);
            toolkit.pushFramebuffer(fbos[i]);
        }

        assertThrows(RuntimeException.class, () -> toolkit.pushFramebuffer(new LwjglFramebuffer(toolkit, frameOps)));
    }

    @Test
    @DisplayName("compositor registers, prepares and presents via fake")
    void compositor_should_presentViaFake() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglFramebufferCompositor compositor = new LwjglFramebufferCompositor(toolkit, textureOps, frameOps);
        StubEffect effect = new StubEffect();

        assertTrue(compositor.isDisplayAvailable());
        assertTrue(compositor.register(effect));
        assertTrue(effect.isActive());
        assertTrue(compositor.prepareForRendering(64, 64, 0, 0));
        frameOps.calls.clear();
        compositor.composite();

        assertAll(
            () -> assertEquals(1, effect.presents),
            () -> assertEquals(1, effect.finishes),
            () -> assertNotNull(effect.lastScene),
            () -> assertTrue(frameOps.calls.toString().contains("beginQuads")),
            () -> assertTrue(frameOps.calls.toString().contains("viewport(0,0,64,64)"))
        );
    }

    @Test
    @DisplayName("compositor stays inert without framebuffer support")
    void compositor_should_stayInert_when_unsupported() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        frameOps.supported = false;
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglFramebufferCompositor compositor = new LwjglFramebufferCompositor(toolkit, textureOps, frameOps);
        StubEffect effect = new StubEffect();

        assertAll(
            () -> assertTrue(!compositor.isDisplayAvailable()),
            () -> assertTrue(!compositor.register(effect)),
            () -> assertTrue(!compositor.prepareForRendering(64, 64, 0, 0))
        );
    }

    @Test
    @DisplayName("bloom sets up programs and presents the small-scene path")
    void bloom_should_setupAndPresent() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglBloomEffect bloom = new LwjglBloomEffect(toolkit, textureOps, frameOps);

        assertTrue(bloom.setup());
        assertTrue(bloom.isAvailable());
        bloom.resize(64, 64);
        LwjglTexture2D scene = rectTexture(toolkit, textureOps, 64);
        textureOps.calls.clear();
        frameOps.calls.clear();
        bloom.present(0, scene, null);

        assertAll(
            () -> assertTrue(textureOps.calls.toString().contains("use(201)")),
            () -> assertTrue(textureOps.calls.toString().contains("use(204)")),
            () -> assertTrue(textureOps.calls.toString().contains("use(203)")),
            () -> assertTrue(frameOps.calls.toString().contains("viewport(0,0,256,256)")),
            () -> assertTrue(frameOps.calls.toString().contains("beginQuads"))
        );
    }

    @Test
    @DisplayName("bloom builds the downsample chain for large scenes")
    void bloom_should_downsampleLargeScenes() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglBloomEffect bloom = new LwjglBloomEffect(toolkit, textureOps, frameOps);
        assertTrue(bloom.setup());

        bloom.resize(512, 512);
        LwjglTexture2D scene = rectTexture(toolkit, textureOps, 512);
        bloom.present(0, scene, null);

        assertAll(
            () -> assertTrue(textureOps.calls.toString().contains("alloc2D(3553,34842,512,512)")),
            () -> assertTrue(textureOps.calls.toString().contains("use(202)"))
        );
    }

    @Test
    @DisplayName("bloom setup fails cleanly without framebuffer support")
    void bloom_should_failSetup_when_unsupported() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        frameOps.supported = false;
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);

        assertTrue(!new LwjglBloomEffect(toolkit, textureOps, frameOps).setup());
    }

    @Test
    @DisplayName("still water loads, enables, updates params and disables")
    void stillWater_should_enableAndDisable() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();

        LwjglStillWaterEffect water = LwjglStillWaterEffect.load(textureOps, frameOps);
        assertNotNull(water);
        textureOps.calls.clear();
        frameOps.calls.clear();
        water.enable(true, false, new float[] { 0.0F, 1.0F, 0.0F, 0.0F });
        water.updateLocalParams(1.0F, 2.0F, 3.0F, 4.0F);
        water.disable();
        water.disable();

        assertAll(
            () -> assertTrue(water.isSupported()),
            () -> assertTrue(!water.isEnabled()),
            () -> assertEquals(
                "[texGen(8192,9474,4), enableCap(34336), disableCap(34820), disableCap(34336)]",
                frameOps.calls.toString()),
            () -> assertEquals(
                "[bindArb(34336,8), localParam(34336,0,1.0,2.0,3.0,4.0), bindArb(34336,0)]",
                textureOps.calls.toString())
        );
    }

    @Test
    @DisplayName("still water load fails cleanly on program errors")
    void stillWater_should_failLoadCleanly() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        textureOps.errorPosition = 5;

        assertNull(LwjglStillWaterEffect.load(textureOps, frameOps));
    }

    @Test
    @DisplayName("rippling water enables, animates and disables")
    void ripplingWater_should_enableAnimateDisable() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglTexture2D normal = rectTexture(toolkit, textureOps, 4);
        LwjglCubeTexture env = new LwjglCubeTexture(toolkit, 6408, 4, false, new int[6][16], textureOps);
        LwjglRipplingWaterEffect water = LwjglRipplingWaterEffect.load(toolkit, textureOps, normal, env, false);
        assertNotNull(water);
        textureOps.calls.clear();

        water.enable(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 96.0F);
        water.setEffectParams(40000, 0, (2 << 16) | 5);
        water.disable();

        assertAll(
            () -> assertTrue(!water.isBreaking()),
            () -> assertTrue(!water.isEnabled()),
            () -> assertEquals(
                "[unit(1), enableT(34067), bindT(34067,42), unit(0), disableT(34067), enableT(3553), bindT(3553,41), "
                    + "use(201), loc(201,normalSampler), u1i(7,0), loc(201,envMapSampler), u1i(7,1), "
                    + "loc(201,sunDir), u3f(7,-0.0,1.0,-0.0), loc(201,sunColour), u4f(7,1.0,1.0,1.0,1.0), "
                    + "loc(201,sunExponent), u1f(7,96.0), "
                    + "loc(201,time), u1f(7,0.0), loc(201,scale), u1f(7,0.03125), "
                    + "loc(201,breakWaterDepth), u1f(7,5.0), loc(201,breakWaterOffset), u1f(7,0.25), "
                    + "unit(1), disableT(34067), unit(0), disableT(3553), use(0)]",
                textureOps.calls.toString())
        );
    }

    @Test
    @DisplayName("breaking water uploads wave uniforms")
    void breakingWater_should_uploadWaveUniforms() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglTexture2D normal = rectTexture(toolkit, textureOps, 4);
        LwjglCubeTexture env = new LwjglCubeTexture(toolkit, 6408, 4, false, new int[6][16], textureOps);
        LwjglRipplingWaterEffect water = LwjglRipplingWaterEffect.load(toolkit, textureOps, normal, env, true);
        assertNotNull(water);
        assertTrue(water.isBreaking());
        textureOps.calls.clear();

        water.enable(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 96.0F);
        assertTrue(water.isEnabled());
        water.setEffectParams(0, 0, (3 << 27) | (7 << 23) | (9 << 19) | (2 << 16) | 5);

        assertTrue(textureOps.calls.toString().contains("loc(201,waveIntensity), u2f(7,0.4375,0.5625), loc(201,waveExponent), u1f(7,3.0)"));
    }

    @Test
    @DisplayName("rippling water load fails cleanly on shader errors")
    void ripplingWater_should_failLoadCleanly() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps);
        LwjglTexture2D normal = rectTexture(toolkit, textureOps, 4);
        LwjglCubeTexture env = new LwjglCubeTexture(toolkit, 6408, 4, false, new int[6][16], textureOps);
        textureOps.objectStatus = 0;

        assertNull(LwjglRipplingWaterEffect.load(toolkit, textureOps, normal, env, false));
    }
}
