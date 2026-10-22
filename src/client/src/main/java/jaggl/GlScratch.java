package jaggl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.lwjgl.system.MemoryUtil;

/**
 * Not part of the original client: native memory for the jaggl calls that take a Java array and an
 * offset.
 *
 * <p>libjaggl pinned the array and passed its address; LWJGL takes a raw address, so the elements
 * from the offset on are copied into one of two growable direct buffers per thread (the toolkit may
 * move the current context between threads) and copied back for the calls that write into the
 * array. The buffers are the JDK's direct buffers, so a thread's are freed with it.</p>
 */
final class GlScratch {

	/** At least this many bytes, so a GL query writing more than the array holds stays in bounds. */
	private static final int MIN_CAPACITY = 64 * 1024;

	private static final ThreadLocal<GlScratch> LOCAL = new ThreadLocal<GlScratch>() {
		@Override
		protected GlScratch initialValue() {
			return new GlScratch();
		}
	};

	private final ByteBuffer[] slots = new ByteBuffer[2];

	private GlScratch() {
	}

	private ByteBuffer buffer(int slot, int bytes) {
		ByteBuffer buffer = this.slots[slot];
		if (buffer == null || buffer.capacity() < bytes) {
			int capacity = buffer == null ? MIN_CAPACITY : buffer.capacity();
			while (capacity < bytes) {
				capacity *= 2;
			}
			buffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
			this.slots[slot] = buffer;
		}
		buffer.clear();
		return buffer;
	}

	/** The elements of {@code array} from {@code offset} on, or at most {@code count} of them. */
	private static int count(int size, int offset, int count) {
		return Math.max(0, Math.min(size - offset, count));
	}

	/**
	 * @param reserve how many bytes GL reads from the address. Where the array holds fewer, the
	 * rest is zeroes, rather than whatever lies past the copy.
	 */
	static long bytes(byte[] array, int offset, int count, int slot, int reserve) {
		if (array == null) {
			return 0L;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().buffer(slot, Math.max(n, reserve));
		buffer.put(array, offset, n);
		long address = MemoryUtil.memAddress0(buffer);
		if (reserve > n) {
			MemoryUtil.memSet(address + n, 0, reserve - n);
		}
		return address;
	}

	static long bytes(byte[] array, int offset) {
		return bytes(array, offset, Integer.MAX_VALUE, 0, 0);
	}

	static long bytes(byte[] array, int offset, int count) {
		return bytes(array, offset, count, 0, 0);
	}

	static long ints(int[] array, int offset, int count) {
		if (array == null) {
			return 0L;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().buffer(0, n * 4);
		buffer.asIntBuffer().put(array, offset, n);
		return MemoryUtil.memAddress0(buffer);
	}

	static long ints(int[] array, int offset) {
		return ints(array, offset, Integer.MAX_VALUE);
	}

	static long floats(float[] array, int offset, int count) {
		if (array == null) {
			return 0L;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().buffer(0, n * 4);
		buffer.asFloatBuffer().put(array, offset, n);
		return MemoryUtil.memAddress0(buffer);
	}

	static long floats(float[] array, int offset) {
		return floats(array, offset, Integer.MAX_VALUE);
	}

	/**
	 * Space for a call that writes into it, such as a GL query, zeroed so that a copy back of more
	 * elements than the query wrote cannot leak a previous call's values.
	 */
	static long space(int bytes) {
		ByteBuffer buffer = LOCAL.get().buffer(0, bytes);
		long address = MemoryUtil.memAddress0(buffer);
		MemoryUtil.memSet(address, 0, bytes);
		return address;
	}

	static void readBytes(byte[] array, int offset, int count) {
		if (array == null) {
			return;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().slots[0];
		buffer.clear();
		buffer.get(array, offset, n);
	}

	static void readInts(int[] array, int offset, int count) {
		if (array == null) {
			return;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().slots[0];
		buffer.clear();
		buffer.asIntBuffer().get(array, offset, n);
	}

	static void readFloats(float[] array, int offset, int count) {
		if (array == null) {
			return;
		}
		int n = count(array.length, offset, count);
		ByteBuffer buffer = LOCAL.get().slots[0];
		buffer.clear();
		buffer.asFloatBuffer().get(array, offset, n);
	}
}
