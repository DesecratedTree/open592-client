package com.jagex.game.runetek5.graphics.gl.lwjgl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Panel;
import java.awt.event.FocusAdapter;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelListener;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("LWJGL canvas installation: GL canvas takes the host's place")
class LwjglCanvasInstallTest {

    private static final class HostHarness {
        final Panel parent = new Panel(null);
        final Canvas host = new Canvas();

        HostHarness() {
            this.host.setBounds(10, 20, 300, 200);
            this.parent.add(this.host);
        }

        LwjglCanvas installedGl() {
            for (Component child : this.parent.getComponents()) {
                if (child instanceof LwjglCanvas) {
                    return (LwjglCanvas) child;
                }
            }
            return null;
        }
    }

    @Test
    @DisplayName("construction installs the GL canvas over the host and forwards input")
    void install_should_placeGlOverHostAndForwardInput() {
        HostHarness harness = new HostHarness();
        harness.host.addMouseListener(new MouseAdapter() {
        });
        harness.host.addMouseMotionListener(new MouseAdapter() {
        });
        MouseWheelListener wheel = event -> {
        };
        harness.host.addMouseWheelListener(wheel);
        harness.host.addKeyListener(new KeyAdapter() {
        });
        harness.host.addFocusListener(new FocusAdapter() {
        });

        LwjglToolkit.create(0, harness.host, null, 0, null);
        LwjglCanvas gl = harness.installedGl();

        assertAll(
            () -> assertTrue(harness.parent.isAncestorOf(harness.host),
                "host stays parented so shell remove/size bookkeeping keeps working"),
            () -> assertFalse(harness.host.isVisible(), "host hides behind the GL canvas"),
            () -> assertTrue(gl != null, "GL canvas joins the host parent"),
            () -> assertEquals(harness.host.getBounds(), gl.getBounds(), "GL canvas takes the host bounds"),
            () -> assertEquals(0, harness.parent.getComponentZOrder(gl), "GL canvas paints above the host"),
            () -> assertEquals(1, gl.getMouseListeners().length, "mouse listeners forwarded"),
            () -> assertEquals(1, gl.getMouseMotionListeners().length, "motion listeners forwarded"),
            () -> assertEquals(1, gl.getMouseWheelListeners().length, "wheel listeners forwarded"),
            () -> assertEquals(1, gl.getKeyListeners().length, "key listeners forwarded"),
            () -> assertEquals(1, gl.getFocusListeners().length, "focus listeners forwarded"));
    }

    @Test
    @DisplayName("host resizes keep the GL canvas in step and the host hidden")
    void install_should_trackHostResizes() {
        HostHarness harness = new HostHarness();

        LwjglToolkit toolkit = LwjglToolkit.create(0, harness.host, null, 0, null);
        harness.host.setSize(640, 480);
        // Peerless components generate no resize events on their own, so
        // deliver the shell's resize exactly as AWT would with a live peer.
        harness.host.dispatchEvent(
            new java.awt.event.ComponentEvent(harness.host, java.awt.event.ComponentEvent.COMPONENT_RESIZED));
        LwjglCanvas gl = harness.installedGl();

        assertAll(
            () -> assertEquals(harness.host.getBounds(), gl.getBounds(), "GL canvas follows host resizes"),
            () -> assertFalse(harness.host.isVisible(), "host stays hidden when the shell re-shows it"),
            () -> assertDoesNotThrow(() -> toolkit.setCanvas(null), "clearing the canvas never throws"));
    }

    @Test
    @DisplayName("setCanvas installs the fresh host after a shell canvas re-creation")
    void setCanvas_should_reinstallAfterRecreation() {
        HostHarness harness = new HostHarness();
        LwjglToolkit toolkit = LwjglToolkit.create(0, harness.host, null, 0, null);
        Canvas fresh = new Canvas();
        fresh.setBounds(0, 0, 765, 503);
        harness.parent.add(fresh);

        toolkit.setCanvas(fresh);

        assertAll(
            () -> assertFalse(fresh.isVisible(), "fresh host hides"),
            () -> assertEquals(fresh.getBounds(), harness.installedGl().getBounds(),
                "mapped GL canvas takes the fresh host bounds"));
    }
}
