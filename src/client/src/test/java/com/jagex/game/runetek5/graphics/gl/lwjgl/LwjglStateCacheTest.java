package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("LwjglStateCache transitions")
class LwjglStateCacheTest {

    private LwjglStateCache cache;

    @BeforeEach
    void resetCache() {
        cache = new LwjglStateCache();
    }

    @Test
    @DisplayName("first use of every entry reports a change")
    void freshCache_should_reportChange_on_firstUse() {
        assertAll(
            () -> assertTrue(cache.useUnit(0)),
            () -> assertTrue(cache.bindTexture(0, 7)),
            () -> assertTrue(cache.setCapability(LwjglStateCache.CAP_BLEND, true)),
            () -> assertTrue(cache.setBlendFunc(1, 2)),
            () -> assertTrue(cache.bindArrayBuffer(9)),
            () -> assertTrue(cache.bindElementBuffer(10))
        );
    }

    @Test
    @DisplayName("repeating the same state reports no change")
    void repeatedState_should_reportNoChange() {
        cache.useUnit(3);
        cache.bindTexture(3, 7);
        cache.setCapability(LwjglStateCache.CAP_DEPTH_TEST, true);
        cache.setBlendFunc(1, 0);
        cache.bindArrayBuffer(5);
        cache.bindElementBuffer(6);

        assertAll(
            () -> assertFalse(cache.useUnit(3)),
            () -> assertFalse(cache.bindTexture(3, 7)),
            () -> assertFalse(cache.setCapability(LwjglStateCache.CAP_DEPTH_TEST, true)),
            () -> assertFalse(cache.setBlendFunc(1, 0)),
            () -> assertFalse(cache.bindArrayBuffer(5)),
            () -> assertFalse(cache.bindElementBuffer(6))
        );
    }

    @Test
    @DisplayName("reset forces every entry to report a change again")
    void reset_should_forceChange_on_everything() {
        cache.useUnit(1);
        cache.bindTexture(1, 7);
        cache.setCapability(LwjglStateCache.CAP_BLEND, true);
        cache.setBlendFunc(1, 2);
        cache.bindArrayBuffer(5);
        cache.bindElementBuffer(6);
        cache.reset();

        assertAll(
            () -> assertTrue(cache.useUnit(1)),
            () -> assertTrue(cache.bindTexture(1, 7)),
            () -> assertTrue(cache.setCapability(LwjglStateCache.CAP_BLEND, true)),
            () -> assertTrue(cache.setBlendFunc(1, 2)),
            () -> assertTrue(cache.bindArrayBuffer(5)),
            () -> assertTrue(cache.bindElementBuffer(6))
        );
    }

    @ParameterizedTest(name = "disabling unset cap {0} still emits once")
    @ValueSource(ints = {
        LwjglStateCache.CAP_DEPTH_TEST,
        LwjglStateCache.CAP_BLEND,
        LwjglStateCache.CAP_FOG
    })
    @DisplayName("first setCapability call always emits, then tracks")
    void setCapability_should_emitOnce_then_track(int cap) {
        assertTrue(cache.setCapability(cap, false));
        assertFalse(cache.setCapability(cap, false));
        assertTrue(cache.setCapability(cap, true));
        assertFalse(cache.setCapability(cap, true));
    }

    @Test
    @DisplayName("unknown capability slots always report a change")
    void unknownCapability_should_always_reportChange() {
        assertTrue(cache.setCapability(-1, true));
        assertTrue(cache.setCapability(99, false));
        assertTrue(cache.setCapability(99, false));
    }

    @ParameterizedTest(name = "texture bound on unit {0} is independent")
    @ValueSource(ints = { 0, 1, 7 })
    @DisplayName("per-unit texture bindings are independent")
    void bindTexture_should_trackPerUnit(int unit) {
        assertTrue(cache.bindTexture(unit, 3));
        assertFalse(cache.bindTexture(unit, 3));
        assertTrue(cache.bindTexture(unit, 4));
    }

    @ParameterizedTest(name = "unit {0} is rejected")
    @ValueSource(ints = { -1, 8, 99 })
    @DisplayName("texture unit selection rejects out-of-range units")
    void useUnit_should_reject_outOfRange(int unit) {
        assertThrows(IllegalArgumentException.class, () -> cache.useUnit(unit));
        assertThrows(IllegalArgumentException.class, () -> cache.bindTexture(unit, 1));
    }

    @ParameterizedTest(name = "blend({0}, {1}) emits once")
    @CsvSource({
        "770, 771",
        "1, 1",
        "0, 0"
    })
    @DisplayName("blend factors emit on change only")
    void setBlendFunc_should_emitOnChange(int src, int dst) {
        assertTrue(cache.setBlendFunc(src, dst));
        assertFalse(cache.setBlendFunc(src, dst));
    }
}
