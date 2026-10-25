package com.jagex.game.runetek5.client.hd.shadow;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The scene's caster submission without a GL context: one frame's collection,
 * the volume it names, and the counters that explain a frame with no shadow.
 *
 * <p>{@code submit} itself is proved by {@code HdSunShadowGlTest}; here the rules
 * a subtle bug would break are pinned: the set is cleared every frame (a caster
 * cannot linger), casters can only be added between {@code beginFrame} and
 * {@code submit} (a submission half-filled after the pass is a bug worth
 * failing), and the volume is the camera's clamped one.
 */
@DisplayName("the scene's sun shadow submission")
class SunShadowSubmissionTest {

	private static final ShadowCaster.Draw NOTHING = new ShadowCaster.Draw() {
		@Override
		public void draw() {
		}
	};

	@Test
	@DisplayName("a new frame starts with no casters, whatever the last one had")
	void beginFrame_shouldClearLastFramesCasters() {
		SunShadowSubmission submission = new SunShadowSubmission();
		submission.beginFrame(0.0F, 0.0F, 0.0F);
		submission.addAt(1.0F, 0.0F, 2.0F, NOTHING);
		submission.addAt(3.0F, 0.0F, 4.0F, NOTHING);
		assertEquals(2, submission.casters().size(), "two casters collected");

		submission.beginFrame(0.0F, 0.0F, 0.0F);
		assertAll(
			() -> assertTrue(submission.casters().isEmpty(), "a caster from last frame must not cast again"),
			() -> assertEquals(0, submission.casters().size())
		);
	}

	@Test
	@DisplayName("each caster keeps its own model-to-scene translation")
	void addAt_shouldKeepPerObjectTransforms() {
		SunShadowSubmission submission = new SunShadowSubmission();
		submission.beginFrame(0.0F, 0.0F, 0.0F);
		submission.addAt(-8.0F, 4.0F, 16.0F, NOTHING);
		submission.addAt(0.0F, 0.0F, 0.0F, NOTHING);

		float[] first = submission.casters().get(0).modelToScene();
		float[] second = submission.casters().get(1).modelToScene();
		assertAll(
			() -> assertEquals(-8.0F, first[12], "x translation"),
			() -> assertEquals(4.0F, first[13], "y translation"),
			() -> assertEquals(16.0F, first[14], "z translation"),
			() -> assertEquals(1.0F, first[0], "no rotation"),
			() -> assertEquals(0.0F, second[12], "the second caster is not the first")
		);
	}

	@Test
	@DisplayName("casters can only be added between beginFrame and submit")
	void add_shouldRefuseOutsideTheCollectionWindow() {
		SunShadowSubmission submission = new SunShadowSubmission();
		assertThrows(IllegalStateException.class, () -> submission.addAt(0.0F, 0.0F, 0.0F, NOTHING),
			"before beginFrame");

		submission.beginFrame(0.0F, 0.0F, 0.0F);
		submission.reset();
		assertThrows(IllegalStateException.class, () -> submission.addAt(0.0F, 0.0F, 0.0F, NOTHING),
			"after reset");
	}

	@Test
	@DisplayName("the volume is the camera's, clamped by ShadowVolume")
	void beginFrame_shouldUseTheClampedCameraVolume() {
		SunShadowSubmission submission = new SunShadowSubmission();
		submission.beginFrame(1024.0F, -64.0F, 2048.0F);

		assertAll(
			() -> assertEquals(ShadowVolume.DEFAULT_RADIUS, submission.radius(), "default radius"),
			() -> assertTrue(submission.radius() >= ShadowVolume.MIN_RADIUS),
			() -> assertTrue(submission.radius() <= ShadowVolume.MAX_RADIUS)
		);
	}

	@Test
	@DisplayName("nested world draws share the frame and only the outermost closes it")
	void scene_shouldCountNestedWorldDraws() {
		SunShadowSubmission submission = new SunShadowSubmission();
		assertFalse(submission.sceneOpen(), "no draw open yet");

		submission.enterScene();
		submission.enterScene();
		assertTrue(submission.sceneOpen(), "a reflection's re-entrant draw shares the frame");

		submission.exitScene();
		assertTrue(submission.sceneOpen(), "the nested draw ending must not close the outer one");

		submission.exitScene();
		assertFalse(submission.sceneOpen(), "the outer draw closes the frame");

		submission.exitScene();
		assertFalse(submission.sceneOpen(), "an unmatched exit cannot go below zero");
	}

	@Test
	@DisplayName("submit needs the toolkit that draws the frame")
	void submit_shouldRefuseANullToolkit() {
		SunShadowSubmission submission = new SunShadowSubmission();
		submission.beginFrame(0.0F, 0.0F, 0.0F);
		assertThrows(IllegalArgumentException.class, () -> submission.submit(null));
	}

	@Test
	@DisplayName("reset forgets the frame and leaves the counters explainable")
	void reset_shouldZeroEverything() {
		SunShadowSubmission submission = new SunShadowSubmission();
		submission.beginFrame(0.0F, 0.0F, 0.0F);
		submission.addAt(0.0F, 0.0F, 0.0F, NOTHING);
		submission.reset();

		assertAll(
			() -> assertTrue(submission.casters().isEmpty()),
			() -> assertFalse(submission.sceneOpen(), "a reset frame has no open draw"),
			() -> assertEquals(0L, submission.frames()),
			() -> assertEquals(0L, submission.passes()),
			() -> assertEquals(0L, submission.skipped()),
			() -> assertEquals(0L, submission.castersSubmitted()),
			() -> assertTrue(
				submission.stats().contains("frames=0 passes=0 skipped=0 recorded=0 submitted=0"),
				submission.stats()),
			() -> assertFalse(submission.stats().isEmpty())
		);
	}
}
