package com.jagex.game.runetek5.client.animation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.jagex.game.runetek5.deob.OutputStream_Sub1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tweening default and renderer flag")
class TweeningTest {

	@BeforeEach
	@AfterEach
	void restoreDefault() {
		Tweening.reset();
	}

	@Test
	@DisplayName("tweening is on before anyone touches the console")
	void should_beEnabled_by_default() {
		assertTrue(Tweening.DEFAULT_ENABLED);
		assertTrue(Tweening.isEnabled());
	}

	@Test
	@DisplayName("the startup push turns the renderers' flag on")
	void reset_should_enableRendererFlag() {
		Tweening.setForTest(false);
		assertFalse(OutputStream_Sub1.aBoolean244);

		Tweening.reset();

		assertTrue(Tweening.isEnabled());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}

	@Test
	@DisplayName("toggle drives the renderer flag and reports the new state")
	void toggle_should_driveRendererFlag() {
		assertFalse(Tweening.toggle());
		assertFalse(OutputStream_Sub1.aBoolean244);

		assertTrue(Tweening.toggle());
		assertTrue(OutputStream_Sub1.aBoolean244);
	}
}
