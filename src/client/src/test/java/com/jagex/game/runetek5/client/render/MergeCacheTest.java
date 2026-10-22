package com.jagex.game.runetek5.client.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Covers the merge cache's decision table: identity of the inputs, the caller's
 * stamp, and what a collision costs.
 */
class MergeCacheTest {

	private static final Object TOOLKIT = new Object();

	private static final Object OTHER_TOOLKIT = new Object();

	/** Counts builds so a hit is observable. */
	private static final class Counter implements MergeCache.Builder<String> {

		private int builds;

		private final String value;

		private Counter(String value) {
			this.value = value;
		}

		@Override
		public String build() {
			this.builds++;
			return this.value;
		}
	}

	@Test
	void secondLookupWithTheSameInputsReusesTheValue() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object[] parts = { new Object(), new Object() };
		Counter builder = new Counter("merged");

		assertSame("merged", cache.resolve(TOOLKIT, parts, 1, builder));
		assertSame("merged", cache.resolve(TOOLKIT, parts, 1, builder));
		assertSame("merged", cache.resolve(TOOLKIT, parts, 1, builder));

		assertEquals(1, builder.builds);
		assertEquals(1, cache.size());
	}

	@Test
	void aChangedStampRebuilds() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object[] parts = { new Object(), new Object() };
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, parts, 1, builder);
		cache.resolve(TOOLKIT, parts, 2, builder);

		assertEquals(2, builder.builds);
		assertEquals(1, cache.size(), "the rebuilt value replaces the stale one in the same slot");
	}

	@Test
	void aReplacedPartRebuilds() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object[] parts = { new Object(), new Object() };
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, parts, 1, builder);
		parts[1] = new Object();
		cache.resolve(TOOLKIT, parts, 1, builder);

		assertEquals(2, builder.builds);
	}

	@Test
	void equalButDistinctPartsAreDifferentInputs() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object part = new Object();
		Object twin = new Object();
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, new Object[] { part }, 1, builder);
		cache.resolve(TOOLKIT, new Object[] { twin }, 1, builder);

		assertEquals(2, builder.builds, "identity decides, not equals");
	}

	@Test
	void nullPartsAreMatchedByIdentityToo() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object part = new Object();
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, new Object[] { part, null }, 1, builder);
		cache.resolve(TOOLKIT, new Object[] { part, null }, 1, builder);
		assertEquals(1, builder.builds);

		cache.resolve(TOOLKIT, new Object[] { part, new Object() }, 1, builder);
		assertEquals(2, builder.builds, "a part appearing where there was none rebuilds");
	}

	@Test
	void aDifferentToolkitRebuilds() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object[] parts = { new Object(), new Object() };
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, parts, 1, builder);
		cache.resolve(OTHER_TOOLKIT, parts, 1, builder);

		assertEquals(2, builder.builds);
	}

	@Test
	void aLongerOrShorterPartListRebuilds() {
		MergeCache<String> cache = new MergeCache<String>(8);
		Object part = new Object();
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, new Object[] { part, part }, 1, builder);
		cache.resolve(TOOLKIT, new Object[] { part, part, part }, 1, builder);

		assertEquals(2, builder.builds);
	}

	@Test
	void aCollisionReplacesRatherThanGrows() {
		MergeCache<String> cache = new MergeCache<String>(1);
		Object[] first = { new Object(), new Object() };
		Object[] second = { new Object(), new Object() };
		Counter firstBuilder = new Counter("first");
		Counter secondBuilder = new Counter("second");

		assertSame("first", cache.resolve(TOOLKIT, first, 1, firstBuilder));
		assertSame("second", cache.resolve(TOOLKIT, second, 1, secondBuilder));
		assertEquals(1, cache.size());
		assertEquals(1, cache.capacity());

		assertSame("first", cache.resolve(TOOLKIT, first, 1, firstBuilder));
		assertEquals(2, firstBuilder.builds, "the evicted value had to be rebuilt");
	}

	@Test
	void capacityRoundsUpToAPowerOfTwo() {
		assertEquals(1, new MergeCache<String>(0).capacity());
		assertEquals(1, new MergeCache<String>(1).capacity());
		assertEquals(8, new MergeCache<String>(5).capacity());
		assertEquals(64, new MergeCache<String>(64).capacity());
	}

	@Test
	void clearForgetsEveryEntry() {
		MergeCache<String> cache = new MergeCache<String>(4);
		Object[] parts = { new Object(), new Object() };
		Counter builder = new Counter("merged");

		cache.resolve(TOOLKIT, parts, 1, builder);
		cache.clear();

		assertEquals(0, cache.size());
		cache.resolve(TOOLKIT, parts, 1, builder);
		assertEquals(2, builder.builds);
	}

	@Test
	void samePartsComparesByIdentityAndLength() {
		Object part = new Object();

		assertTrue(MergeCache.sameParts(new Object[] { part }, new Object[] { part }));
		assertTrue(MergeCache.sameParts(null, null));
		assertFalse(MergeCache.sameParts(new Object[] { part }, new Object[] { new Object() }));
		assertFalse(MergeCache.sameParts(new Object[] { part }, new Object[] { part, part }));
		assertFalse(MergeCache.sameParts(new Object[] { part }, null));
	}

	@Test
	void hashSeparatesDifferentInputs() {
		Object first = new Object();
		Object second = new Object();

		assertEquals(MergeCache.hash(TOOLKIT, new Object[] { first }), MergeCache.hash(TOOLKIT, new Object[] { first }));
		assertFalse(MergeCache.hash(TOOLKIT, new Object[] { first }) == MergeCache.hash(TOOLKIT, new Object[] { second }));
	}
}
