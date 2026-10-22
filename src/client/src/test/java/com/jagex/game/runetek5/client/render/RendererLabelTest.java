package com.jagex.game.runetek5.client.render;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RendererLabelTest {

	@Test
	void modeOneIsTheOpenGlToolkit() {
		assertEquals(RendererLabel.OPENGL, RendererLabel.of(1));
		assertEquals("OpenGL", RendererLabel.of(1));
	}

	@Test
	void everyOtherModeIsTheJavaRenderer() {
		// 0 is low detail, 2 is standard detail (which this port builds with the
		// Java toolkit) and -1 is before the toolkit has been chosen.
		assertEquals(RendererLabel.SOFTWARE, RendererLabel.of(0));
		assertEquals(RendererLabel.SOFTWARE, RendererLabel.of(2));
		assertEquals(RendererLabel.SOFTWARE, RendererLabel.of(-1));
		assertEquals("Software", RendererLabel.SOFTWARE);
	}
}
