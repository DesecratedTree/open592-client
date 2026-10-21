package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Canvas;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.jagex.game.runetek5.JavaToolkit;
import com.jagex.game.runetek5.graphics.Toolkit;

@DisplayName("LwjglToolkit HD slot wiring")
class LwjglToolkitTest {

    private static Canvas sizedCanvas() {
        Canvas canvas = new Canvas();
        canvas.setSize(765, 503);
        return canvas;
    }

    @Test
    @DisplayName("HD slot constructs the LWJGL toolkit")
    void hdSlot_should_returnLwjglToolkit() {
        Toolkit toolkit = Toolkit.method4241(null, 0, sizedCanvas(), 1, null);

        assertInstanceOf(LwjglToolkit.class, toolkit);
    }

    @Test
    @DisplayName("HD slot falls back to the Java toolkit when LWJGL is unavailable")
    void hdSlot_should_fallBackToJavaToolkit_when_lwjglUnavailable() {
        LwjglToolkit.setUnavailableForTest(true);
        try {
            assertThrows(RuntimeException.class, () -> LwjglToolkit.create(0, null, null, 0, null));
        } finally {
            LwjglToolkit.setUnavailableForTest(false);
        }

        Toolkit toolkit = Toolkit.method4241(null, 0, sizedCanvas(), 0, null);

        assertInstanceOf(JavaToolkit.class, toolkit);
    }

    @ParameterizedTest(name = "samplesForPreference({0}) == {1}")
    @CsvSource({
        "0, 0",
        "1, 1",
        "2, 2",
        "4, 4",
        "-1, 0"
    })
    @DisplayName("samplesForPreference clamps the multisample preference at zero")
    void samplesForPreference_should_clampAtZero(int input, int expected) {
        assertEquals(expected, LwjglConfig.samplesForPreference(input));
    }

    @ParameterizedTest(name = "pref {0} -> antiAliasing {1}")
    @CsvSource({
        "0, false",
        "1, false",
        "2, true",
        "4, true"
    })
    @DisplayName("supportsAntiAliasing mirrors the multisample preference")
    void supportsAntiAliasing_should_mirrorPreference(int preference, boolean expected) {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, preference, null);

        assertEquals(expected, toolkit.supportsAntiAliasing());
    }

    @Test
    @DisplayName("capability queries mirror the legacy HD values")
    void capabilities_should_mirrorLegacyHd() {
        LwjglToolkit toolkit = LwjglToolkit.create(0, null, null, 0, null);

        assertEquals(4, toolkit.getMaxLights());
        assertTrue(toolkit.hasTextures());
        assertTrue(toolkit.method4300());
        assertTrue(toolkit.method4243());
    }
}
