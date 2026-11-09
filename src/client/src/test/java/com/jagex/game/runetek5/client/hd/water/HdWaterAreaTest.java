package com.jagex.game.runetek5.client.hd.water;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HD water area")
class HdWaterAreaTest {

	@AfterEach
	void forgetArea() {
		HdWaterArea.resetForTest();
	}

	@Test
	@DisplayName("no area is the identity: every scale 1, the type's colour, settled")
	void defaults_shouldBeTheIdentity() {
		HdWaterArea area = HdWaterArea.DEFAULT;
		assertAll(
			() -> assertEquals(1.0F, area.waves()),
			() -> assertEquals(1.0F, area.glint()),
			() -> assertEquals(1.0F, area.speed()),
			() -> assertEquals(1.0F, area.size()),
			() -> assertEquals(1.0F, area.shownSize()),
			() -> assertEquals(1.0F, area.blend()),
			() -> assertEquals(HdWaterArea.DEFAULT_TINT, area.tint()),
			() -> assertEquals(HdWaterArea.NO_TINT, area.tintStrength(), 0.0F),
			// Outside the schema's [0, 1]: no area stated a tint.
			() -> assertTrue(HdWaterArea.NO_TINT < 0.0F)
		);
	}

	@Test
	@DisplayName("the schema's ranges are the clamps")
	void of_shouldClampToTheSchema() {
		HdWaterArea area = HdWaterArea.of(0xFFFFFF, 5.0F, -1.0F, 9.0F, -2.0F, 0.01F);
		assertAll(
			() -> assertEquals(1.0F, area.tintStrength()),
			() -> assertEquals(0.0F, area.waves()),
			() -> assertEquals(4.0F, area.glint()),
			() -> assertEquals(0.0F, area.speed()),
			() -> assertEquals(0.25F, area.size())
		);
	}

	@Test
	@DisplayName("the tint divides into channels exactly as the shader reads it")
	void tint_shouldDivideIntoChannels() {
		HdWaterArea area = HdWaterArea.of(0x204060, 0.5F, 1.0F, 1.0F, 1.0F, 1.0F);
		assertAll(
			() -> assertEquals(0x20 / 255.0F, area.tintRed(), 1.0E-6F),
			() -> assertEquals(0x40 / 255.0F, area.tintGreen(), 1.0E-6F),
			() -> assertEquals(0x60 / 255.0F, area.tintBlue(), 1.0E-6F)
		);
	}

	@Test
	@DisplayName("the tiling crossfades over three seconds from what was on show")
	void smoothStep_shouldCrossfadeTheSize() {
		HdWaterArea area = HdWaterArea.of(0x69809C, 0.45F, 1.0F, 1.0F, 1.0F, 4.0F);
		HdWaterArea.publishForTest(HdWaterArea.midTransition(area, 2.0F, 0.0F));

		assertEquals(2.0F, HdWaterArea.current().displayedSize(), 1.0E-6F);

		for (int i = 0; i < 15; i++) {
			HdWaterArea.smoothStep(100L);
		}
		assertAll(
			() -> assertEquals(0.5F, HdWaterArea.current().blend(), 1.0E-5F),
			() -> assertEquals(3.0F, HdWaterArea.current().displayedSize(), 1.0E-5F)
		);

		for (int i = 0; i < 15; i++) {
			HdWaterArea.smoothStep(100L);
		}
		assertAll(
			() -> assertEquals(1.0F, HdWaterArea.current().blend(), 0.0F),
			() -> assertEquals(4.0F, HdWaterArea.current().displayedSize(), 0.0F)
		);
	}

	@Test
	@DisplayName("a stall cannot finish the crossfade, and a settled area never moves")
	void smoothStep_shouldClampAndSettle() {
		HdWaterArea area = HdWaterArea.of(0x69809C, 0.45F, 1.0F, 1.0F, 1.0F, 4.0F);
		HdWaterArea.publishForTest(HdWaterArea.midTransition(area, 1.0F, 0.0F));

		HdWaterArea.smoothStep(5000L);
		assertTrue(HdWaterArea.current().blend() <= 0.034F,
			"a five second stall must advance at most the clamped step: " + HdWaterArea.current().blend());

		HdWaterArea.publishForTest(area);
		HdWaterArea.smoothStep(1500L);
		assertEquals(1.0F, HdWaterArea.current().blend(), 0.0F);
	}

	@Test
	@DisplayName("a new area crossfades from the tiling that was displayed")
	void retarget_shouldStartFromWhatWasShown() {
		HdWaterArea three = HdWaterArea.of(0x69809C, 0.45F, 1.0F, 1.0F, 1.0F, 3.0F);
		HdWaterArea fading = HdWaterArea.midTransition(three, 1.0F, 0.5F);

		assertEquals(2.0F, fading.displayedSize(), 1.0E-6F);

		HdWaterArea next = HdWaterArea.retarget(fading, null);

		assertAll(
			() -> assertEquals(2.0F, next.shownSize(), 1.0E-6F),
			() -> assertEquals(0.0F, next.blend(), 0.0F),
			() -> assertEquals(1.0F, next.size(), 0.0F),
			() -> assertSame(HdWaterArea.DEFAULT, HdWaterArea.retarget(HdWaterArea.DEFAULT, null))
		);
	}

	@Test
	@DisplayName("two areas are the same only when every value they state matches")
	void sameAs_shouldCompareEveryValue() {
		HdWaterArea area = HdWaterArea.of(0x204060, 0.5F, 2.0F, 3.0F, 4.0F, 1.5F);
		HdWaterArea same = HdWaterArea.of(0x204060, 0.5F, 2.0F, 3.0F, 4.0F, 1.5F);
		HdWaterArea other = HdWaterArea.of(0x204060, 0.5F, 2.0F, 3.0F, 4.0F, 2.5F);

		assertAll(
			() -> assertTrue(area.sameAs(same)),
			() -> assertTrue(!area.sameAs(other)),
			() -> assertTrue(!area.sameAs(null))
		);
	}
}
