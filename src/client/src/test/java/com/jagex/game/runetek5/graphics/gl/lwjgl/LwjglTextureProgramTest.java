package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.unpack_Sub1;

@DisplayName("LWJGL texture and program lifecycle")
class LwjglTextureProgramTest {

    /** Recording LwjglTextureOps fake: no GL context needed. */
    private static final class RecordingTextureOps implements LwjglTextureOps {
        final List<String> calls = new ArrayList<>();
        int nextTextureId = 41;
        long nextShaderHandle = 101L;
        long nextProgramHandle = 201L;
        int nextArbId = 7;
        int objectStatus = 1;
        String infoLog = "";
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
            calls.add("enable(" + target + ")");
        }

        @Override
        public void disableTarget(int target) {
            calls.add("disable(" + target + ")");
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
            calls.add("image2Db(" + target + "," + level + "," + internalFormat + "," + width + "," + height + "," + format + "," + type + "," + pixels.length + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, int[] pixels, int offset) {
            calls.add("image2Di(" + target + "," + level + "," + internalFormat + "," + width + "," + height + "," + format + "," + type + "," + pixels.length + ")");
        }

        @Override
        public void texImage2D(int target, int level, int internalFormat, int width, int height, int format, int type, float[] pixels, int offset) {
            calls.add("image2Df(" + target + "," + level + "," + internalFormat + "," + width + "," + height + "," + format + "," + type + "," + pixels.length + ")");
        }

        @Override
        public void allocateImage2D(int target, int level, int internalFormat, int width, int height, int format, int type) {
            calls.add("alloc2D(" + target + "," + level + "," + internalFormat + "," + width + "," + height + ")");
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, byte[] pixels, int offset) {
            calls.add("sub2Db(" + target + "," + level + "," + xOffset + "," + yOffset + "," + width + "," + height + "," + format + "," + type + "," + pixels.length + ")");
        }

        @Override
        public void texSubImage2D(int target, int level, int xOffset, int yOffset, int width, int height, int format, int type, int[] pixels, int offset) {
            calls.add("sub2Di(" + target + "," + level + "," + xOffset + "," + yOffset + "," + width + "," + height + "," + format + "," + type + "," + pixels.length + ")");
        }

        @Override
        public void copyImage2D(int target, int level, int internalFormat, int x, int y, int width, int height) {
            calls.add("copy2D(" + target + "," + level + "," + internalFormat + "," + x + "," + y + "," + width + "," + height + ")");
        }

        @Override
        public void copySubImage2D(int target, int level, int xOffset, int yOffset, int x, int y, int width, int height) {
            calls.add("copySub2D(" + target + "," + level + "," + xOffset + "," + yOffset + "," + x + "," + y + "," + width + "," + height + ")");
        }

        @Override
        public void generateMipmap(int target) {
            calls.add("mipmap(" + target + ")");
        }

        @Override
        public void framebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
            calls.add("fbo(" + target + "," + attachment + "," + textarget + "," + texture + "," + level + ")");
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
            calls.add("source(" + shader + "," + source.length() + ")");
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
            calls.add("log(" + object + ")");
            return infoLog;
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
            calls.add("progStr(" + target + "," + format + "," + source.length() + ")");
        }

        @Override
        public int programErrorPosition() {
            calls.add("errPos()=" + errorPosition);
            return errorPosition;
        }
    }

    /** Stub material provider with one small RGBA material per available id. */
    private static final class StubProvider implements unpack_Sub1 {
        boolean floatTextures;

        @Override
        public boolean textureAvailable(int id) {
            return id >= 0 && id < 1000;
        }

        @Override
        public MaterialType getMetrics(int id) {
            MaterialType metrics = new MaterialType();
            metrics.isSmall = true;
            metrics.aBoolean638 = floatTextures;
            metrics.aBoolean640 = true;
            metrics.aByte89 = 0;
            metrics.aByte92 = 0;
            metrics.aBoolean643 = true;
            metrics.aBoolean644 = false;
            return metrics;
        }

        @Override
        public int[] getPixels(boolean readReversed, int id, float brightness, int width, int height) {
            return new int[width * height];
        }

        @Override
        public int[] getPixelsArgb(int id, float brightness, int width, int height) {
            return new int[width * height];
        }

        @Override
        public float[] method2394(float brightness, int id, int width, int height) {
            return new float[width * height * 4];
        }
    }

    private static LwjglToolkit toolkit(RecordingTextureOps ops) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setTextureOpsForTest(ops);
        return toolkit;
    }

    @ParameterizedTest(name = "bytesPerPixel({0}) == {1}")
    @CsvSource({
        "6406, 1",
        "6409, 1",
        "6410, 2",
        "34846, 2",
        "34844, 2",
        "6407, 3",
        "6408, 4",
        "34847, 4",
        "34843, 6",
        "34842, 8",
        "6402, 3",
        "6401, 1"
    })
    @DisplayName("bytesPerPixel matches the legacy format table")
    void formatTables_should_matchLegacyBytesPerPixel(int format, int expected) {
        assertEquals(expected, LwjglTextureFormat.bytesPerPixel(format));
    }

    @ParameterizedTest(name = "formatForInternalFormat({0}) == {1}")
    @CsvSource({
        "6407, 6407",
        "34843, 6407",
        "34837, 6407",
        "6408, 6408",
        "34842, 6408",
        "34836, 6408",
        "6406, 6406",
        "34844, 6406",
        "6409, 6409",
        "34846, 6409",
        "6410, 6410",
        "34847, 6410",
        "6402, 6402"
    })
    @DisplayName("formatForInternalFormat matches the legacy mapping")
    void formatTables_should_matchLegacyFormatMapping(int internalFormat, int expected) {
        assertEquals(expected, LwjglTextureFormat.formatForInternalFormat(internalFormat));
    }

    @Test
    @DisplayName("format tables reject unknown formats and gate RGB uploads")
    void formatTables_should_reject_unknownFormats() {
        assertAll(
            () -> assertThrows(RuntimeException.class, () -> LwjglTextureFormat.bytesPerPixel(9999)),
            () -> assertThrows(RuntimeException.class, () -> LwjglTextureFormat.formatForInternalFormat(9999)),
            () -> assertTrue(LwjglTextureFormat.isDirectRgbUpload(0)),
            () -> assertTrue(!LwjglTextureFormat.isDirectRgbUpload(1)),
            () -> assertTrue(!LwjglTextureFormat.isDirectRgbUpload(7))
        );
    }

    @Test
    @DisplayName("2D byte texture emits the legacy create/bind/filter sequence")
    void texture2D_should_emitLegacyCreateSequence() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);

        LwjglTexture2D texture = new LwjglTexture2D(toolkit, 3553, 6408, 4, 4, false, new byte[16], 6408, false, ops);

        assertAll(
            () -> assertEquals(41, texture.id()),
            () -> assertEquals(64, texture.byteSize()),
            () -> assertEquals(64, toolkit.IA()),
            () -> assertEquals(
                "[genT(41), enable(3553), bindT(3553,41), "
                    + "store(3317,1), image2Db(3553,0,6408,4,4,6408,5121,16), store(3317,4), "
                    + "param(3553,10241,9729), param(3553,10240,9729)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("2D texture wraps, sub-uploads, copies and deletes like the legacy paths")
    void texture2D_should_wrapSubUploadCopyAndDelete() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        LwjglTexture2D texture = new LwjglTexture2D(toolkit, 3553, 6408, 4, 4, false, new byte[16], 6408, false, ops);
        ops.calls.clear();

        texture.setWrap(true, false, ops);
        texture.uploadSubRegion(4, 4, new byte[16], 6406, true, ops);
        texture.copySubRegion(0, 0, 10, 20, 4, 4, ops);
        texture.attachToFramebuffer(36160, 0, ops);
        texture.detachFromFramebuffer(ops);
        texture.delete();
        texture.delete();

        assertAll(
            () -> assertEquals(0, toolkit.IA()),
            () -> assertTrue(texture.isDeleted()),
            () -> assertEquals(
                "[param(3553,10242,10497), param(3553,10243,33071), "
                    + "store(3317,1), sub2Db(3553,0,0,0,4,4,6406,5121,16), store(3317,4), "
                    + "copySub2D(3553,0,0,0,10,20,4,4), flush, "
                    + "fbo(36160,0,3553,41,0), fbo(36160,0,3553,0,0), deleteT(41)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("mipmapped int texture generates the pyramid and scales bytes by 4/3")
    void texture2D_should_generateMipmap_when_mipmapped() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);

        LwjglTexture2D texture = new LwjglTexture2D(toolkit, 3553, 6408, 8, 8, true, new int[64], false, ops);

        assertAll(
            () -> assertEquals(341, texture.byteSize()),
            () -> assertEquals(341, toolkit.IA()),
            () -> assertEquals(
                "[genT(41), enable(3553), bindT(3553,41), "
                    + "image2Di(3553,0,6408,8,8,32993,5121,64), "
                    + "param(3553,10241,9984), param(3553,10240,9728), mipmap(3553), "
                    + "param(3553,10241,9987), param(3553,10240,9729)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("cube texture uploads all six faces and deletes once")
    void cubeTexture_should_uploadSixFaces() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        byte[][] faces = new byte[6][16];

        LwjglCubeTexture texture = new LwjglCubeTexture(toolkit, 6408, 4, false, faces, 6408, ops);
        int bytes = texture.byteSize();
        texture.delete();

        assertAll(
            () -> assertEquals(384, bytes),
            () -> assertEquals(0, toolkit.IA()),
            () -> assertEquals(
                "[genT(41), enable(34067), bindT(34067,41), "
                    + "image2Db(34069,0,6408,4,4,6408,5121,16), "
                    + "image2Db(34070,0,6408,4,4,6408,5121,16), "
                    + "image2Db(34071,0,6408,4,4,6408,5121,16), "
                    + "image2Db(34072,0,6408,4,4,6408,5121,16), "
                    + "image2Db(34073,0,6408,4,4,6408,5121,16), "
                    + "image2Db(34074,0,6408,4,4,6408,5121,16), "
                    + "param(34067,10241,9729), param(34067,10240,9729), deleteT(41)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("toolkit binding elides repeat binds across units")
    void toolkitBinding_should_elideRepeatBinds() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        LwjglTexture2D first = new LwjglTexture2D(toolkit, 3553, 6408, 4, 4, false, new byte[16], 6408, false, ops);
        LwjglCubeTexture second = new LwjglCubeTexture(toolkit, 6408, 4, false, new byte[6][16], 6408, ops);
        ops.calls.clear();

        toolkit.selectTextureUnit(1);
        toolkit.selectTextureUnit(1);
        toolkit.bindTexture(first);
        toolkit.bindTexture(first);
        toolkit.selectTextureUnit(0);
        toolkit.bindTexture(first);
        toolkit.bindTexture(null);

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> toolkit.selectTextureUnit(9)),
            () -> assertEquals(
                "[unit(1), enable(3553), bindT(3553,41), "
                    + "unit(0), disable(34067), enable(3553), bindT(3553,41), disable(3553)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("shader compiles, reports failure, and deletes")
    void shader_should_compileAndDelete() {
        RecordingTextureOps ops = new RecordingTextureOps();

        LwjglShader shader = LwjglShader.compile(ops, 35632, "void main() {}");
        assertNotNull(shader);
        long id = shader.id();
        shader.delete();
        shader.delete();

        ops.objectStatus = 0;
        ops.infoLog = "boom";
        LwjglShader failed = LwjglShader.compile(ops, 35632, "void main() {}");

        assertAll(
            () -> assertNull(failed),
            () -> assertTrue(shader.isDeleted()),
            () -> assertEquals(
                "[createShader(35632)=" + id + ", source(" + id + ",14), compile(" + id + "), "
                    + "objParam(" + id + ",35713)=1, deleteObj(" + id + "), "
                    + "createShader(35632)=" + (id + 1) + ", source(" + (id + 1) + ",14), compile(" + (id + 1) + "), "
                    + "objParam(" + (id + 1) + ",35713)=0, log(" + (id + 1) + "), deleteObj(" + (id + 1) + ")]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("program links, sets uniforms, unbinds and deletes")
    void program_should_linkUseAndDelete() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglShader shader = LwjglShader.compile(ops, 35632, "void main() {}");
        assertNotNull(shader);
        ops.calls.clear();

        LwjglProgram program = LwjglProgram.link(ops, new LwjglShader[] { shader });
        assertNotNull(program);
        program.use();
        program.uniform1i("sceneTex", 0);
        program.uniform3f("params", 1.0F, 0.0F, 0.0F);
        LwjglProgram.unbind(ops);
        program.delete();

        assertAll(
            () -> assertEquals(
                "[createProgram()=201, attach(201,101), link(201), objParam(201,35714)=1, "
                    + "use(201), loc(201,sceneTex), u1i(7,0), loc(201,params), u3f(7,1.0,0.0,0.0), "
                    + "use(0), deleteObj(201)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("program link fails cleanly on bad shaders")
    void program_should_failLinkCleanly() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglShader shader = LwjglShader.compile(ops, 35632, "void main() {}");
        assertNotNull(shader);
        ops.calls.clear();
        ops.objectStatus = 0;
        ops.infoLog = "bad";

        assertAll(
            () -> assertNull(LwjglProgram.link(ops, new LwjglShader[] { shader })),
            () -> assertNull(LwjglProgram.link(ops, new LwjglShader[] { null })),
            () -> assertNull(LwjglProgram.link(ops, new LwjglShader[] { shader, null })),
            () -> assertEquals(
                "[createProgram()=201, attach(201,101), link(201), objParam(201,35714)=0, "
                    + "log(201), detach(201,101), deleteObj(201)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("ARB program loads on a clean error position")
    void arbProgram_should_loadAndBind() {
        RecordingTextureOps ops = new RecordingTextureOps();

        LwjglArbProgram program = LwjglArbProgram.create(ops, 34336, "prog");
        assertNotNull(program);
        program.bind();
        program.unbind();
        program.delete();
        ops.errorPosition = 5;
        LwjglArbProgram failed = LwjglArbProgram.create(ops, 34336, "prog");

        assertAll(
            () -> assertNull(failed),
            () -> assertTrue(program.isDeleted()),
            () -> assertEquals(
                "[genArb()=7, bindArb(34336,7), progStr(34336,34933,4), errPos()=-1, bindArb(34336,0), "
                    + "bindArb(34336,7), bindArb(34336,0), deleteObj(7), "
                    + "genArb()=8, bindArb(34336,8), progStr(34336,34933,4), errPos()=5, bindArb(34336,0)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("texture cache hits, misses and clears")
    void textureCache_should_hitMissAndClear() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        StubProvider provider = new StubProvider();
        LwjglTextureCache cache = new LwjglTextureCache(toolkit, provider, ops, false);

        LwjglTexture2D first = cache.texture(5);
        assertNotNull(first);
        assertSame(first, cache.texture(5));
        assertNull(cache.texture(1000));
        assertEquals(1, cache.size());
        assertEquals(42, ops.nextTextureId);
        cache.clear();

        assertAll(
            () -> assertEquals(0, cache.size()),
            () -> assertTrue(first.isDeleted()),
            () -> assertEquals(0, toolkit.IA())
        );
    }

    @Test
    @DisplayName("texture cache builds the float path when enabled")
    void textureCache_should_buildFloatPath() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        StubProvider provider = new StubProvider();
        provider.floatTextures = true;
        LwjglTextureCache cache = new LwjglTextureCache(toolkit, provider, ops, true);

        LwjglTexture2D texture = cache.texture(5);

        assertAll(
            () -> assertNotNull(texture),
            () -> assertEquals(64 * 64 * 8, toolkit.IA()),
            () -> assertTrue(ops.calls.toString().contains("image2Df(3553,0,34842,64,64,6408,5126,16384)"))
        );
    }

    @Test
    @DisplayName("texture cache evicts the eldest entry past capacity")
    void textureCache_should_evictEldest() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        LwjglTextureCache cache = new LwjglTextureCache(toolkit, new StubProvider(), ops, false);

        LwjglTexture2D first = cache.texture(0);
        for (int id = 1; id <= 256; id++) {
            cache.texture(id);
        }

        assertAll(
            () -> assertTrue(first.isDeleted()),
            () -> assertEquals(256, cache.size()),
            () -> assertEquals(256 * 64 * 64 * 3, toolkit.IA())
        );
    }

    @Test
    @DisplayName("texture cache trim ages entries out after five ticks")
    void textureCache_should_trimAgedEntries() {
        RecordingTextureOps ops = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(ops);
        LwjglTextureCache cache = new LwjglTextureCache(toolkit, new StubProvider(), ops, false);
        LwjglTexture2D texture = cache.texture(3);

        for (int i = 0; i < 5; i++) {
            cache.trim();
        }
        boolean kept = cache.size() == 1 && !texture.isDeleted();
        cache.trim();

        assertAll(
            () -> assertTrue(kept),
            () -> assertEquals(0, cache.size()),
            () -> assertTrue(texture.isDeleted()),
            () -> assertEquals(0, toolkit.IA())
        );
    }

    @Test
    @DisplayName("toolkit tick trims its cache")
    void toolkitTick_should_trimCache() {
        RecordingTextureOps ops = new RecordingTextureOps();
        StubProvider provider = new StubProvider();
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, provider, 0, null);
        toolkit.setTextureOpsForTest(ops);
        LwjglTexture2D texture = toolkit.textureCache().texture(3);
        assertNotNull(texture);

        for (int i = 0; i < 6; i++) {
            toolkit.tick(i);
        }

        assertAll(
            () -> assertTrue(texture.isDeleted()),
            () -> assertEquals(0, toolkit.IA())
        );
    }
}
