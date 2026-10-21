package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;

@DisplayName("LWJGL 2D paths: sprites, fonts, masks, effects")
class LwjglSpriteFontTest {

    /** Recording LwjglTextureOps fake that also keeps uploaded pixels. */
    private static final class RecordingTextureOps implements LwjglTextureOps {
        final List<String> calls = new ArrayList<>();
        final List<int[]> intUploads = new ArrayList<>();
        int nextTextureId = 41;
        int nextArbId = 7;

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
            int[] copy = new int[width * height];
            System.arraycopy(pixels, offset, copy, 0, copy.length);
            intUploads.add(copy);
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
            calls.add("tFbo");
        }

        @Override
        public void flush() {
            calls.add("flush");
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
            return nextArbId++;
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

    /** Recording LwjglFrameOps fake for 2D draws. */
    private static final class RecordingFrameOps implements LwjglFrameOps {
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
        public void blitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
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
            calls.add("loadIdentity");
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

    /** Recording LwjglListOps fake. */
    private static final class RecordingListOps implements LwjglListOps {
        final List<String> calls = new ArrayList<>();
        int nextBase = 100;

        @Override
        public int genLists(int range) {
            calls.add("genLists(" + range + ")");
            int base = nextBase;
            nextBase += range;
            return base;
        }

        @Override
        public void callList(int id) {
            calls.add("call(" + id + ")");
        }

        @Override
        public void newList(int id, int mode) {
            calls.add("new(" + id + "," + mode + ")");
        }

        @Override
        public void endList() {
            calls.add("end");
        }
    }

    private static LwjglToolkit toolkit(RecordingTextureOps textureOps, RecordingFrameOps frameOps, RecordingListOps listOps) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);
        toolkit.setTextureOpsForTest(textureOps);
        toolkit.setFrameOpsForTest(frameOps);
        toolkit.setListOpsForTest(listOps);
        return toolkit;
    }

    private static SpriteType glyph(int width, int height) {
        SpriteType glyph = new SpriteType();
        glyph.width = width;
        glyph.height = height;
        glyph.minX = 3;
        glyph.minY = 5;
        glyph.pixels = new byte[width * height];
        glyph.pixels[0] = 1;
        return glyph;
    }

    @Test
    @DisplayName("sprite upload packs rows and draws one textured quad")
    void sprite_should_upload_and_draw() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps, new RecordingListOps());
        int[] pixels = { 0xFFFF0000, 0xFF00FF00, 0xFF0000FF, 0xFFFFFFFF, 0xFF000000, 0xFFFFFFFF, 0xFFFFFFFF, 0xFFFFFFFF };

        LwjglSprite sprite = (LwjglSprite) toolkit.method4265(pixels, 4, 4, 2);
        sprite.setOffsets(1, 2, 1, 0);
        sprite.render(10, 20, 1, 0, 1);

        assertAll(
            () -> assertEquals(
                "[genT(41), enableT(3553), bindT(3553,41), image2Di(4,2), param(3553,10241,9729), param(3553,10240,9729), unit(0), envi(34161,7681), envi(34162,7681)]",
                textureOps.calls.toString()),
            () -> assertArrayEquals(pixels, textureOps.intUploads.get(0)),
            () -> assertEquals(
                "[color(0.0,0.0,0.0,0.0), enable(3042), enable(3008), blend(770,771), beginQuads, tc(0.0,0.0), v(11,22), "
                    + "tc(0.0,1.0), v(11,24), tc(1.0,1.0), v(14,24), tc(1.0,0.0), v(14,22), end]",
                frameOps.calls.toString()),
            () -> assertEquals(3, sprite.drawnWidth()),
            () -> assertEquals(2, sprite.drawnHeight())
        );
    }

    @Test
    @DisplayName("sprite stride packing compacts padded rows")
    void sprite_should_packStride() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());
        int[] padded = { 1, 2, 9, 9, 3, 4, 9, 9 };

        LwjglSprite sprite = (LwjglSprite) toolkit.method4265(padded, 4, 2, 2);

        assertAll(
            () -> assertArrayEquals(new int[] { 1, 2, 3, 4 }, textureOps.intUploads.get(0)),
            () -> assertEquals(2, sprite.getWidth()),
            () -> assertEquals(2, sprite.getHeight())
        );
    }

    @Test
    @DisplayName("sprites reject bad input and fail after delete")
    void sprite_should_rejectBadInput() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> new LwjglSprite(null, 2, 2, new int[4], 2)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglSprite(toolkit, 0, 2, new int[4], 2)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglSprite(toolkit, 2, 2, null, 2)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglSprite(toolkit, 4, 2, new int[4], 2)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglSprite(toolkit, 4, 2, new int[7], 4))
        );

        LwjglSprite sprite = new LwjglSprite(toolkit, 2, 2, new int[4]);
        sprite.delete();
        sprite.delete();
        assertAll(
            () -> assertTrue(sprite.isDeleted()),
            () -> assertThrows(RuntimeException.class, () -> sprite.render(0, 0, 1, 0, 1)),
            () -> assertThrows(RuntimeException.class, () -> sprite.copyAlpha(0, 0, 0)),
            () -> assertThrows(RuntimeException.class, () -> sprite.captureScreenRegion(0, 0, 1, 1, 0, 0))
        );
    }

    @Test
    @DisplayName("generateSprite decodes the palette exactly like the legacy path")
    void generateSprite_should_decodePalette() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());
        SpriteType indexed = new SpriteType();
        indexed.width = 2;
        indexed.height = 1;
        indexed.pallete = new int[] { 0, 0xFF0000 };
        indexed.pixels = new byte[] { 0, 1 };
        indexed.alpha = null;
        indexed.minX = 7;
        indexed.minY = 8;
        indexed.borderX = 0;
        indexed.borderY = 0;

        NativeSprite sprite = toolkit.generateSprite(indexed);

        assertAll(
            () -> assertInstanceOf(LwjglSprite.class, sprite),
            () -> assertArrayEquals(new int[] { 0, 0xFFFF0000 }, textureOps.intUploads.get(0)),
            () -> assertEquals(2, sprite.scaleWidth()),
            () -> assertEquals(1, sprite.scaleHeight())
        );
    }

    @Test
    @DisplayName("font packs the atlas and compiles one list per glyph")
    void font_should_packAtlas_and_compileLists() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        RecordingListOps listOps = new RecordingListOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps, listOps);
        SpriteType[] images = new SpriteType[256];
        for (int i = 0; i < 256; i++) {
            images[i] = glyph(4, 6);
        }

        LwjglFont font = new LwjglFont(toolkit, null, images);

        int listStart = listOps.calls.indexOf("new(100,4864)");
        assertAll(
            () -> assertEquals(6, font.cell()),
            () -> assertEquals(96, font.atlasSize()),
            () -> assertTrue(textureOps.calls.contains("image2Db(96,96)")),
            () -> assertEquals("genLists(256)", listOps.calls.get(0)),
            () -> assertTrue(listStart >= 0),
            () -> assertEquals("[new(100,4864), end]", listOps.calls.subList(listStart, listStart + 2).toString()),
            () -> assertEquals(
                "[beginQuads, tc(0.0,1.0), v(3,5), tc(0.0,0.9375), v(3,11), "
                    + "tc(0.041666668,0.9375), v(7,11), tc(0.041666668,1.0), v(7,5), end]",
                frameOps.calls.subList(0, 10).toString())
        );
    }

    @Test
    @DisplayName("font draw installs colour, translates and calls the glyph")
    void font_should_drawGlyph() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        RecordingFrameOps frameOps = new RecordingFrameOps();
        RecordingListOps listOps = new RecordingListOps();
        LwjglToolkit toolkit = toolkit(textureOps, frameOps, listOps);
        SpriteType[] images = new SpriteType[256];
        for (int i = 0; i < 256; i++) {
            images[i] = glyph(4, 6);
        }
        LwjglFont font = new LwjglFont(toolkit, null, images);
        frameOps.calls.clear();

        font.OA('A', 10, 20, 0xFFFF0000, false);

        assertAll(
            () -> assertEquals(
                "[color(1.0,0.0,0.0,1.0), translate(10.0,20.0,0.0), loadIdentity]",
                frameOps.calls.toString()),
            () -> assertTrue(listOps.calls.contains("call(165)"))
        );
    }

    @Test
    @DisplayName("fonts reject bad glyph sets")
    void font_should_rejectBadInput() {
        LwjglToolkit toolkit = toolkit(new RecordingTextureOps(), new RecordingFrameOps(), new RecordingListOps());
        SpriteType[] shortSet = new SpriteType[10];

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> new LwjglFont(null, null, new SpriteType[256])),
            () -> assertThrows(RuntimeException.class, () -> new LwjglFont(toolkit, null, null)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglFont(toolkit, null, shortSet))
        );
    }

    @Test
    @DisplayName("mask packs spans exactly like the legacy fill")
    void mask_should_packSpans() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());

        LwjglMask mask = (LwjglMask) toolkit.createMask(4, 2, new int[] { 0, 1 }, new int[] { 4, 1 });

        assertAll(
            () -> assertEquals(4, mask.width()),
            () -> assertEquals(2, mask.height()),
            () -> assertArrayEquals(new byte[] { -1, -1, -1, -1, 0, -1, 0, 0 }, mask.bits()),
            () -> assertTrue(textureOps.calls.contains("image2Db(4,2)"))
        );
    }

    @Test
    @DisplayName("effect manager mirrors the legacy selection machine")
    void effects_should_mirrorSelection() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());
        LwjglEffectManager effects = toolkit.effects();

        assertFalse(toolkit.method4261());
        effects.select(false, 8);
        int degraded = effects.currentId();
        effects.select(true, 3);
        int combined = effects.currentId();
        effects.select(false, 0);

        assertAll(
            () -> assertNotNull(effects.bloom()),
            () -> assertEquals(2, degraded),
            () -> assertEquals(3 | Integer.MIN_VALUE, combined),
            () -> assertEquals(0, effects.currentId()),
            () -> assertFalse(effects.bindForEffect(null, 0)),
            () -> assertThrows(RuntimeException.class, () -> {
                effects.select(true, 3);
                effects.bindForEffect(null, 0);
            })
        );
    }

    @Test
    @DisplayName("still water loads through the seam and reports support")
    void effects_should_loadWater() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());

        LwjglStillWaterEffect water = toolkit.effects().waterEffect();

        assertAll(
            () -> assertNotNull(water),
            () -> assertTrue(toolkit.effects().isWaterSupported()),
            () -> assertTrue(toolkit.method4261())
        );
    }

    @Test
    @DisplayName("native sprite creation returns blank and toolkit sprites")
    void toolkit_should_create2D() {
        RecordingTextureOps textureOps = new RecordingTextureOps();
        LwjglToolkit toolkit = toolkit(textureOps, new RecordingFrameOps(), new RecordingListOps());

        assertAll(
            () -> assertInstanceOf(LwjglSprite.class, toolkit.createNativeSprite(0, 0, 8, 8, true)),
            () -> assertTrue(textureOps.calls.contains("alloc2D(3553,6408,8,8)"))
        );
    }
}
