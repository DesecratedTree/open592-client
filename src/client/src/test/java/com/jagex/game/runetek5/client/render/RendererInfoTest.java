package com.jagex.game.runetek5.client.render;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RendererInfo driver line")
class RendererInfoTest {

	@BeforeEach
	@AfterEach
	void clear() {
		RendererInfo.reset();
	}

	@Test
	@DisplayName("nothing is shown before a context has reported")
	void describe_shouldBeEmpty_beforeAnythingIsRecorded() {
		assertEquals("", RendererInfo.describe());
	}

	@Test
	@DisplayName("the line carries the GL version, the vendor and the renderer")
	void describe_shouldJoinTheDriverStrings() {
		RendererInfo.record("4.6.0 NVIDIA 617.42", "NVIDIA Corporation", "NVIDIA GeForce RTX 4070/PCIe/SSE2");

		assertEquals(
			"GL:4.6.0 NVIDIA 617.42 | NVIDIA Corporation | NVIDIA GeForce RTX 4070/PCIe/SSE2",
			RendererInfo.describe());
		assertEquals("4.6.0 NVIDIA 617.42", RendererInfo.getVersion());
		assertEquals("NVIDIA Corporation", RendererInfo.getVendor());
		assertEquals("NVIDIA GeForce RTX 4070/PCIe/SSE2", RendererInfo.getRenderer());
	}

	@Test
	@DisplayName("parts the driver leaves out do not leave separators behind")
	void describe_shouldDropMissingParts() {
		RendererInfo.record(null, "  ", "Mesa Intel(R) UHD Graphics 630");

		assertEquals("GL:Mesa Intel(R) UHD Graphics 630", RendererInfo.describe());
	}

	@Test
	@DisplayName("a driver string too long for the overlay is capped")
	void describe_shouldTruncate_aVeryLongDriverString() {
		StringBuilder renderer = new StringBuilder();
		for (int i = 0; i < 200; i++) {
			renderer.append('x');
		}

		RendererInfo.record("4.6.0", "Vendor", renderer.toString());
		String line = RendererInfo.describe();

		assertEquals(RendererInfo.MAX_LENGTH, line.length());
		assertTrue(line.startsWith("GL:4.6.0 | Vendor | xxx"), line);
		assertTrue(line.endsWith("..."), line);
	}
}
