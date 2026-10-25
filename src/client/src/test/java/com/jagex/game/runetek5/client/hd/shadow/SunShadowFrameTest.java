package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pins the per-frame contract of the sun shadow driver: what runs, in which
 * order, and - just as important - what does not run when the row or 592HD is
 * off. The surface is a fake, so the trace is the evidence: a pass is one
 * prepare, one begin, the casters, one end, in that order, per frame.
 */
@DisplayName("sun shadow frame driver")
class SunShadowFrameTest {

	private static final int ROW_1024 = 1;

	private static final int ROW_2048 = 2;

	/** The trace entry the default {@link #render} call's begin produces. */
	private static final String BEGIN = "begin:0.0,0.0,1.0,0.0,0.0,0.0,32.0";

	private FakeSurface surface;

	private SunShadowFrame frame;

	private List<String> trace;

	@BeforeEach
	void setUp() {
		this.trace = new ArrayList<String>();
		this.surface = new FakeSurface(this.trace);
		this.frame = new SunShadowFrame();
	}

	private SunShadowFrame.Casters casters() {
		return new SunShadowFrame.Casters() {
			@Override
			public void drawCasters() {
				SunShadowFrameTest.this.trace.add("casters");
			}
		};
	}

	private boolean render(boolean hd, boolean perPixel, int row) {
		return this.frame.render(this.surface, casters(), hd, perPixel, row,
			0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 32.0F);
	}

	@Test
	@DisplayName("one frame is prepare, begin, casters, end - in that order")
	void frame_shouldRunThePassOnce() {
		assertTrue(render(true, true, ROW_2048));

		assertAll(
			() -> assertEquals(Arrays.asList("prepare:2048", BEGIN, "casters", "end"), this.trace),
			() -> assertEquals(1L, this.frame.frames()),
			() -> assertEquals(1L, this.frame.passes()),
			() -> assertEquals(0L, this.frame.skipped()),
			() -> assertEquals(SunShadowFrame.Skip.NONE, this.frame.lastSkip()),
			() -> assertFalse(this.surface.inPass, "the pass must be closed")
		);
	}

	@Test
	@DisplayName("the sun direction and volume reach the pass unchanged")
	void frame_shouldForwardTheSunAndVolume() {
		this.frame.render(this.surface, casters(), true, true, ROW_1024,
			0.5F, 0.0F, 0.866F, 100.0F, 200.0F, 300.0F, 16.0F);

		assertAll(
			() -> assertEquals("begin:0.5,0.0,0.866,100.0,200.0,300.0,16.0", this.trace.get(1)),
			() -> assertEquals("prepare:1024", this.trace.get(0))
		);
	}

	@Test
	@DisplayName("592HD off, per-pixel lighting off and the row off all skip without touching the target")
	void settingsOff_shouldSkipEverything() {
		assertAll(
			() -> assertFalse(render(false, true, ROW_2048)),
			() -> assertTrue(this.trace.isEmpty(), this.trace.toString()),
			() -> assertEquals(SunShadowFrame.Skip.SETTINGS, this.frame.lastSkip()),
			() -> assertFalse(render(true, false, ROW_2048)),
			() -> assertFalse(render(true, true, 0)),
			() -> assertTrue(this.trace.isEmpty(), this.trace.toString()),
			() -> assertEquals(3L, this.frame.skipped()),
			() -> assertEquals(0L, this.frame.passes())
		);
	}

	@Test
	@DisplayName("a refused target skips the frame and is never retried inside it")
	void targetRefused_shouldSkip() {
		this.surface.refusePrepare = true;

		assertFalse(render(true, true, ROW_2048));

		assertAll(
			() -> assertEquals(Arrays.asList("prepare:2048"), this.trace),
			() -> assertEquals(SunShadowFrame.Skip.TARGET, this.frame.lastSkip()),
			() -> assertEquals(1L, this.frame.skipped()),
			() -> assertEquals(0L, this.frame.passes())
		);
	}

	@Test
	@DisplayName("a pass that refuses to start draws no casters and ends nothing")
	void passRefused_shouldNotDrawCasters() {
		this.surface.refuseBegin = true;

		assertFalse(render(true, true, ROW_2048));

		assertAll(
			() -> assertEquals(Arrays.asList("prepare:2048", BEGIN), this.trace),
			() -> assertEquals(SunShadowFrame.Skip.PASS, this.frame.lastSkip()),
			() -> assertEquals(0L, this.frame.passes())
		);
	}

	@Test
	@DisplayName("ten frames are ten passes, and the frame id advances once each")
	void frames_shouldBeOnePassEach() {
		for (int i = 0; i < 10; i++) {
			assertTrue(render(true, true, ROW_1024));
		}

		assertAll(
			() -> assertEquals(10L, this.frame.passes()),
			() -> assertEquals(10L, this.frame.frames()),
			() -> assertEquals(10, this.surface.frameId),
			() -> assertEquals(40, this.trace.size(), "4 steps per frame")
		);
	}

	@Test
	@DisplayName("a resize between frames re-prepares once and only then")
	void resize_shouldPrepareTheNewSize() {
		render(true, true, ROW_1024);
		render(true, true, ROW_1024);
		assertEquals(2, count("prepare:1024"));

		render(true, true, ROW_2048);
		assertAll(
			() -> assertEquals(2, count("prepare:1024")),
			() -> assertEquals(1, count("prepare:2048"), "one re-prepare, not one per frame")
		);
	}

	@Test
	@DisplayName("the stats line names the reason the last frame was skipped")
	void stats_shouldExplainSkips() {
		render(true, true, ROW_2048);
		render(true, true, 0);

		String stats = this.frame.stats();
		assertAll(
			() -> assertTrue(stats.contains("frames=2"), stats),
			() -> assertTrue(stats.contains("passes=1"), stats),
			() -> assertTrue(stats.contains("skipped=1"), stats),
			() -> assertTrue(stats.contains("settings off"), stats)
		);
	}

	@Test
	@DisplayName("reset clears the counters but leaves the surface alone")
	void reset_shouldClearCounters() {
		render(true, true, ROW_2048);
		this.frame.reset();

		assertAll(
			() -> assertEquals(0L, this.frame.frames()),
			() -> assertEquals(0L, this.frame.passes()),
			() -> assertEquals(SunShadowFrame.Skip.NONE, this.frame.lastSkip()),
			() -> assertEquals(1, this.surface.frameId, "the map keeps its own count")
		);
	}

	private int count(String step) {
		int matches = 0;
		for (String entry : this.trace) {
			if (entry.equals(step)) {
				matches++;
			}
		}
		return matches;
	}

	/** Records the pass protocol and can refuse either step. */
	private static final class FakeSurface implements SunShadowFrame.Surface {

		private final List<String> trace;

		private int frameId;

		private boolean prepared;

		private int preparedSize;

		boolean refusePrepare;

		boolean refuseBegin;

		boolean inPass;

		FakeSurface(List<String> trace) {
			this.trace = trace;
		}

		@Override
		public boolean prepare(int size) {
			this.trace.add("prepare:" + size);
			if (this.refusePrepare) {
				return false;
			}
			if (!this.prepared || this.preparedSize != size) {
				this.prepared = true;
				this.preparedSize = size;
			}
			return true;
		}

		@Override
		public boolean beginPass(float sunX, float sunY, float sunZ, float centreX, float centreY,
				float centreZ, float radius) {
			this.trace.add("begin:" + sunX + "," + sunY + "," + sunZ + "," + centreX + "," + centreY
				+ "," + centreZ + "," + radius);
			if (this.refuseBegin || this.inPass) {
				return false;
			}
			this.inPass = true;
			return true;
		}

		@Override
		public void endPass() {
			this.trace.add("end");
			this.inPass = false;
			this.frameId++;
		}

		@Override
		public int frameId() {
			return this.frameId;
		}
	}
}
