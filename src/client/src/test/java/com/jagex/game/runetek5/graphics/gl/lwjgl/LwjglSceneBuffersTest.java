package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

@DisplayName("LWJGL scene geometry uploads and render lists")
class LwjglSceneBuffersTest {

    /** Recording LwjglGlOps fake: no GL context needed. */
    private static final class RecordingOps implements LwjglGlOps {
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

    /** Recording LwjglListOps fake: no GL context needed. */
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

    private static LwjglToolkit toolkit() {
        return LwjglToolkit.create(0, null, null, 0, null);
    }

    @Test
    @DisplayName("model vertex upload creates once, then re-uploads in place")
    void modelVertex_should_createOnce_then_reupload() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();
        LwjglModelBuffers buffers = new LwjglModelBuffers(toolkit, ops);

        assertNull(buffers.vertexBuffer());

        LwjglArrayBuffer created = buffers.uploadVertex(new byte[16], 16, true);
        LwjglArrayBuffer same = buffers.uploadVertex(new byte[8], 8, true);
        buffers.uploadVertex(new byte[32], 32, true);

        assertAll(
            () -> assertSame(created, same),
            () -> assertSame(created, buffers.vertexBuffer()),
            () -> assertEquals(5123, buffers.vertexBuffer().getStride()),
            () -> assertEquals(32, toolkit.IA()),
            () -> assertEquals(
                "[gen, bind(34962,41), upload(34962,16,35040), "
                    + "bind(34962,41), subUpload(34962,0,8), "
                    + "bind(34962,41), upload(34962,32,35040)]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("model index upload preserves the caller's index type")
    void modelIndex_should_preserveIndexType() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();
        LwjglModelBuffers buffers = new LwjglModelBuffers(toolkit, ops);

        buffers.uploadIndex(new byte[8], 8, 5123, false);
        LwjglElementArrayBuffer refreshed = buffers.uploadIndex(new byte[8], 8, 5121, false);

        assertAll(
            () -> assertEquals(5121, refreshed.method5182()),
            () -> assertEquals(8, toolkit.IA()),
            () -> assertEquals(1, ops.calls.stream().filter(c -> c.equals("gen")).count()),
            () -> assertTrue(ops.calls.contains("upload(34963,8,35044)"))
        );
    }

    @Test
    @DisplayName("model buffers reject bad input and deleted re-upload")
    void modelBuffers_should_rejectBadInput() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> new LwjglModelBuffers(null, ops)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglModelBuffers(toolkit, null)),
            () -> assertThrows(RuntimeException.class,
                () -> new LwjglModelBuffers(toolkit, ops).uploadVertex(null, 4, false)),
            () -> assertThrows(RuntimeException.class,
                () -> new LwjglModelBuffers(toolkit, ops).uploadIndex(null, 4, 5121, false)),
            () -> assertThrows(RuntimeException.class, () -> new LwjglRenderList(null, 4))
        );

        LwjglModelBuffers buffers = new LwjglModelBuffers(toolkit, ops);
        buffers.uploadVertex(new byte[4], 4, false);
        buffers.delete();
        buffers.delete();
        assertThrows(RuntimeException.class, () -> buffers.uploadVertex(new byte[4], 4, false));
    }

    @ParameterizedTest(name = "shadowData={0}, normals={1} -> texComps={2}, normalOffset={3}")
    @CsvSource({
        "true,  true,  3, 28",
        "false, true,  2, 24",
        "true,  false, 3, -1",
        "false, false, 2, -1",
    })
    @DisplayName("ground tile build matches the legacy declaration layout")
    void groundTile_should_matchLegacyLayout(boolean shadowData, boolean normals, int texComps, int normalOffset) {
        RecordingOps ops = new RecordingOps();
        LwjglGroundBuffers ground = new LwjglGroundBuffers(toolkit(), ops);

        assertNull(ground.tileBuffer());
        ground.build(new byte[16], 16, 5123, shadowData, normals);

        assertAll(
            () -> assertEquals(texComps, ground.texCoordComponents()),
            () -> assertEquals(normals, ground.hasNormal()),
            () -> assertEquals(normalOffset, ground.normalOffset()),
            () -> assertEquals(5123, ground.tileBuffer().method5182()),
            () -> assertEquals(LwjglGroundBuffers.POSITION_OFFSET, 0),
            () -> assertEquals(LwjglGroundBuffers.COLOUR_OFFSET, 12),
            () -> assertEquals(LwjglGroundBuffers.TEXCOORD_OFFSET, 16)
        );
    }

    @Test
    @DisplayName("ground tile rebuild frees the previous buffer without leaking")
    void groundTile_should_replaceWithoutLeak() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();
        LwjglGroundBuffers ground = new LwjglGroundBuffers(toolkit, ops);

        ground.build(new byte[16], 16, 5123, false, true);
        LwjglElementArrayBuffer rebuilt = ground.build(new byte[16], 16, 5123, false, true);

        assertAll(
            () -> assertTrue(rebuilt != null && !rebuilt.isDeleted()),
            () -> assertEquals(16, toolkit.IA()),
            () -> assertEquals(
                "[gen, bind(34963,41), upload(34963,16,35044), "
                    + "delete(41), gen, bind(34963,42), upload(34963,16,35044)]",
                ops.calls.toString())
        );

        ground.delete();
        assertEquals(0, toolkit.IA());
        assertFalse(ops.calls.contains("upload(34963,16,35040)"));
    }

    @Test
    @DisplayName("render list offsets every call from the reserved base")
    void renderList_should_offsetFromBase() {
        RecordingListOps ops = new RecordingListOps();

        LwjglRenderList list = new LwjglRenderList(ops, 8);
        list.callList((char) 5);
        list.beginList(2);
        list.endList();

        assertAll(
            () -> assertEquals(100, list.base()),
            () -> assertEquals(
                "[genLists(8), call(105), new(102,4864), end]",
                ops.calls.toString())
        );
    }

    @Test
    @DisplayName("toolkit factories route through the injected seams")
    void toolkitFactories_should_useInjectedSeams() {
        RecordingOps glOps = new RecordingOps();
        RecordingListOps listOps = new RecordingListOps();
        LwjglToolkit toolkit = toolkit();
        toolkit.setGlOpsForTest(glOps);
        toolkit.setListOpsForTest(listOps);

        toolkit.createModelBuffers().uploadVertex(new byte[8], 8, false);
        toolkit.createGroundBuffers().build(new byte[8], 8, 5121, false, false);
        toolkit.createRenderList(4).callList((char) 1);

        assertAll(
            () -> assertSame(glOps, toolkit.glOps()),
            () -> assertSame(listOps, toolkit.listOps()),
            () -> assertTrue(glOps.calls.contains("upload(34962,8,35044)")),
            () -> assertEquals("[genLists(4), call(101)]", listOps.calls.toString())
        );
    }
}
