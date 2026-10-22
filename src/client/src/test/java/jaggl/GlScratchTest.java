package jaggl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.lwjgl.system.MemoryUtil;

/**
 * The jaggl natives took a Java array plus an offset and read it in place. The port copies
 * those elements into native memory instead, so the copy has to start at the offset, stop at the
 * end of the array, and come back where the GL call wrote its result.
 */
class GlScratchTest {

	@Test
	void bytesCopyFromTheOffsetOnly() {
		byte[] data = { 10, 11, 12, 13, 14 };
		long address = GlScratch.bytes(data, 2, 3, 0, 0);
		assertEquals(12, MemoryUtil.memGetByte(address));
		assertEquals(13, MemoryUtil.memGetByte(address + 1));
		assertEquals(14, MemoryUtil.memGetByte(address + 2));
	}

	@Test
	void bytesStopAtTheEndOfTheArray() {
		byte[] data = { 10, 11, 12 };
		long address = GlScratch.bytes(data, 2, 100, 0, 0);
		assertEquals(12, MemoryUtil.memGetByte(address));
	}

	@Test
	void theReserveIsZeroFilledForCallsThatReadAllOfIt() {
		// glTexImage2D with a sub-image of a texture: GL may read the whole reserved level.
		byte[] data = { 1, 2 };
		long address = GlScratch.bytes(data, 0, 2, 0, 6);
		assertEquals(1, MemoryUtil.memGetByte(address));
		assertEquals(2, MemoryUtil.memGetByte(address + 1));
		for (int i = 2; i < 6; i++) {
			assertEquals(0, MemoryUtil.memGetByte(address + i), "byte " + i + " of the reserve");
		}
	}

	@Test
	void aNullArrayIsTheNullPointer() {
		assertEquals(0L, GlScratch.bytes(null, 0));
		assertEquals(0L, GlScratch.ints(null, 0));
		assertEquals(0L, GlScratch.floats(null, 0));
	}

	@Test
	void intsAndFloatsKeepTheirValues() {
		int[] integers = { 0x11223344, -1, 7 };
		long intAddress = GlScratch.ints(integers, 1, 2);
		assertEquals(-1, MemoryUtil.memGetInt(intAddress));
		assertEquals(7, MemoryUtil.memGetInt(intAddress + 4));

		float[] floats = { 0.5F, -2.25F };
		long floatAddress = GlScratch.floats(floats, 0, 2);
		assertEquals(0.5F, MemoryUtil.memGetFloat(floatAddress));
		assertEquals(-2.25F, MemoryUtil.memGetFloat(floatAddress + 4));
	}

	@Test
	void theBufferGrowsPastItsInitialCapacity() {
		byte[] large = new byte[300000];
		for (int i = 0; i < large.length; i++) {
			large[i] = (byte) i;
		}
		long address = GlScratch.bytes(large, 0, large.length, 0, 0);
		assertEquals(0, MemoryUtil.memGetByte(address));
		assertEquals((byte) 299999, MemoryUtil.memGetByte(address + 299999));
	}

	@Test
	void readingBackNeverWritesPastTheArray() {
		int[] source = { 101, 202 };
		GlScratch.ints(source, 0, 2);
		int[] destination = { 0, 0 };
		// The query wrote two ints, but only one element is left after the offset.
		GlScratch.readInts(destination, 1, 2);
		assertEquals(0, destination[0]);
		assertEquals(101, destination[1]);
	}

	@Test
	void spaceIsZeroedBetweenQueries() {
		GlScratch.ints(new int[] { -1, -1 }, 0, 2);
		long address = GlScratch.space(8);
		assertEquals(0, MemoryUtil.memGetInt(address));
		assertEquals(0, MemoryUtil.memGetInt(address + 4));
	}

	@Test
	void eachThreadHasItsOwnBuffer() throws Exception {
		long main = GlScratch.ints(new int[] { 1 }, 0, 1);
		final long[] other = new long[1];
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				other[0] = GlScratch.ints(new int[] { 2 }, 0, 1);
			}
		});
		thread.start();
		thread.join();
		assertNotEquals(main, other[0]);
		assertEquals(1, MemoryUtil.memGetInt(main));
		assertEquals(2, MemoryUtil.memGetInt(other[0]));
	}
}
