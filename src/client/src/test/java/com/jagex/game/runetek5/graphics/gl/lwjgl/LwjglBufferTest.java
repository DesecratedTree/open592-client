package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LWJGL buffer allocation and lifecycle")
class LwjglBufferTest {

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

    private static LwjglToolkit toolkit() {
        return LwjglToolkit.create(0, null, null, 0, null);
    }

    @Test
    @DisplayName("direct buffer round-trips bytes and frees exactly once")
    void byteBuffer_should_roundTrip_and_free() {
        byte[] payload = { 1, 2, 3, 4, 5, 6, 7, 8 };
        LwjglByteBuffer buffer = LwjglByteBuffer.allocate(8);

        buffer.put(payload, 0, 0, 8);
        byte[] back = new byte[8];
        buffer.get(back, 0, 0, 8);

        assertArrayEquals(payload, back);
        assertTrue(buffer.address() != 0L);
        buffer.free();
        buffer.free();
        assertTrue(buffer.isFreed());
        assertThrows(RuntimeException.class, () -> buffer.put(payload, 0, 0, 1));
    }

    @Test
    @DisplayName("direct buffer rejects out-of-range access")
    void byteBuffer_should_reject_outOfRange() {
        LwjglByteBuffer buffer = LwjglByteBuffer.allocate(4);
        try {
            assertAll(
                () -> assertThrows(RuntimeException.class, () -> buffer.put(new byte[8], 0, 0, 8)),
                () -> assertThrows(RuntimeException.class, () -> buffer.put(new byte[4], 0, 1, 4)),
                () -> assertThrows(RuntimeException.class, () -> buffer.get(new byte[1], 0, 4, 1)),
                () -> assertThrows(RuntimeException.class, () -> LwjglByteBuffer.allocate(-1))
            );
        } finally {
            buffer.free();
        }
    }

    @Test
    @DisplayName("memory pool allocates buffers and refuses work after release")
    void memoryPool_should_allocate_untilReleased() {
        LwjglMemoryPool pool = new LwjglMemoryPool(1024);
        assertEquals(1024, pool.capacity());
        assertFalse(pool.isReleased());

        LwjglByteBuffer buffer = pool.allocate(16);
        try {
            assertEquals(16, buffer.capacity());
        } finally {
            buffer.free();
        }
        pool.deallocate();
        assertTrue(pool.isReleased());
        assertThrows(RuntimeException.class, () -> pool.allocate(16));
    }

    @Test
    @DisplayName("VBO create grows, sub-uploads, and deletes with counter math")
    void glBuffer_should_orphanGrow_subUploadShrink_and_delete() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();
        byte[] data = new byte[16];

        LwjglArrayBuffer buffer = new LwjglArrayBuffer(toolkit, 12, data, 16, false, ops);
        assertEquals(41, buffer.getId());
        assertEquals(16, toolkit.IA());

        buffer.upload(new byte[8], 8);
        assertEquals(16, toolkit.IA());

        buffer.upload(new byte[32], 32);
        assertEquals(32, toolkit.IA());

        buffer.delete();
        buffer.delete();
        assertTrue(buffer.isDeleted());
        assertEquals(0, toolkit.IA());

        assertEquals(
            "[gen, bind(34962,41), upload(34962,16,35044), "
                + "bind(34962,41), subUpload(34962,0,8), "
                + "bind(34962,41), upload(34962,32,35044), delete(41)]",
            ops.calls.toString()
        );
    }

    @Test
    @DisplayName("array and element buffers expose their interface contracts")
    void bufferRoles_should_match_legacyContracts() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();
        byte[] data = new byte[8];

        LwjglArrayBuffer array = new LwjglArrayBuffer(toolkit, 12, data, 8, true, ops);
        LwjglElementArrayBuffer elements = new LwjglElementArrayBuffer(toolkit, 5123, data, 8, false, ops);

        array.method3657(new byte[8], 8);
        elements.method5184(8, 5121, new byte[8]);

        assertAll(
            () -> assertEquals(0L, array.getAddress()),
            () -> assertEquals(5123, array.getStride()),
            () -> assertEquals(0, elements.method5181()),
            () -> assertEquals(5121, elements.method5182()),
            () -> assertEquals(0L, elements.getAddress())
        );

        array.delete();
        elements.delete();
        assertEquals(0, toolkit.IA());
    }

    @Test
    @DisplayName("buffer rejects null inputs and double lifecycle errors")
    void glBuffer_should_reject_badInput() {
        RecordingOps ops = new RecordingOps();
        LwjglToolkit toolkit = toolkit();

        assertThrows(RuntimeException.class, () -> new LwjglArrayBuffer(null, 1, new byte[4], 4, false, ops));
        assertThrows(RuntimeException.class, () -> new LwjglArrayBuffer(toolkit, 1, null, 4, false, ops));

        LwjglArrayBuffer buffer = new LwjglArrayBuffer(toolkit, 1, new byte[4], 4, false, ops);
        buffer.delete();
        assertThrows(RuntimeException.class, () -> buffer.upload(new byte[4], 4));
    }
}
