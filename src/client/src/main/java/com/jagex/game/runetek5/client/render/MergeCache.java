package com.jagex.game.runetek5.client.render;

/**
 * A small direct-mapped table that remembers a value built from a set of
 * objects, so an expensive rebuild can be skipped while the inputs are
 * unchanged.
 *
 * <p>Membership is decided by identity, never by {@code equals}: the callers
 * pass live game objects (models, a toolkit) whose identity is the only thing
 * that reliably says "same input". A slot holds one entry, so a collision
 * simply replaces what was there — the cost of a wrong guess is a rebuild, not
 * a wrong value.
 *
 * <p>The table is not synchronised. Callers either keep one per thread or
 * accept that sharing it means sharing the rebuild.
 */
final class MergeCache<T> {

	/** Builds the value on a miss. */
	interface Builder<T> {
		T build();
	}

	private static final class Entry<T> {

		private Object toolkit;

		private Object[] parts;

		private int stamp;

		private T value;
	}

	private final Entry<T>[] entries;

	private int size;

	@SuppressWarnings("unchecked")
	MergeCache(int capacity) {
		int length = 1;
		while (length < capacity) {
			length <<= 1;
		}
		this.entries = new Entry[length];
	}

	/** Slots in the table, after rounding the requested capacity up. */
	int capacity() {
		return this.entries.length;
	}

	/** Slots currently holding an entry, for tests and diagnostics. */
	int size() {
		return this.size;
	}

	/**
	 * Returns the value remembered for these inputs, building and storing one
	 * when nothing matching is present.
	 *
	 * @param stamp an extra scalar the caller wants to invalidate on, such as a
	 *              light revision; any change is a miss
	 */
	T resolve(Object toolkit, Object[] parts, int stamp, Builder<T> builder) {
		int index = hash(toolkit, parts) & this.entries.length - 1;
		Entry<T> entry = this.entries[index];
		if (entry != null && matches(entry, toolkit, parts, stamp)) {
			return entry.value;
		}
		T value = builder.build();
		if (entry == null) {
			entry = new Entry<T>();
			this.entries[index] = entry;
			this.size++;
		}
		entry.toolkit = toolkit;
		entry.parts = copyOf(parts);
		entry.stamp = stamp;
		entry.value = value;
		return value;
	}

	/** Forgets everything, as a toolkit or scene change would. */
	void clear() {
		for (int i = 0; i < this.entries.length; i++) {
			this.entries[i] = null;
		}
		this.size = 0;
	}

	private static <T> boolean matches(Entry<T> entry, Object toolkit, Object[] parts, int stamp) {
		return entry.toolkit == toolkit && entry.stamp == stamp && sameParts(entry.parts, parts);
	}

	/** Identity comparison of two part lists, element by element. */
	static boolean sameParts(Object[] left, Object[] right) {
		if (left == right) {
			return true;
		}
		if (left == null || right == null || left.length != right.length) {
			return false;
		}
		for (int i = 0; i < left.length; i++) {
			if (left[i] != right[i]) {
				return false;
			}
		}
		return true;
	}

	static int hash(Object toolkit, Object[] parts) {
		int hash = System.identityHashCode(toolkit);
		for (int i = 0; i < parts.length; i++) {
			hash = hash * 31 + System.identityHashCode(parts[i]);
		}
		return hash ^ hash >>> 16;
	}

	private static Object[] copyOf(Object[] parts) {
		Object[] copy = new Object[parts.length];
		System.arraycopy(parts, 0, copy, 0, parts.length);
		return copy;
	}
}
