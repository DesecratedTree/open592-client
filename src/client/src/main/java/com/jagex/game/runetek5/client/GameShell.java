package com.jagex.game.runetek5.client;

import jagex3.jagmisc.jagmisc;
import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Method;
import java.net.URL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.client.camera.CameraPreferences;
import com.jagex.game.runetek5.client.animation.Tweening;
import com.jagex.game.runetek5.client.cursor.CursorSettings;
import com.jagex.game.runetek5.client.cycle.ClientFrameClock;
import com.jagex.game.runetek5.client.cycle.FramePacer;
import com.jagex.game.runetek5.client.cycle.FrameRateStats;
import com.jagex.game.runetek5.client.cycle.TickBatch;
import com.jagex.game.runetek5.deob.Static17;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.deob.Static458;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static179;
import com.jagex.game.runetek5.deob.Static298;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.client.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.graphics.FramebufferCompositor;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.entity.ProjectileAnimation;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchPcmStream;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub29;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.js5.request.Js5WorkerRequestMessage;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!o")
public abstract class GameShell extends Applet implements Runnable, FocusListener, WindowListener {

	@OriginalMember(owner = "client!o", name = "a", descriptor = "Z")
	private boolean errorScreenRaised = false;

	@OriginalMember(owner = "client!o", name = "f", descriptor = "Z")
	private boolean jagmiscLoaded = false;

	/**
	 * Owns tick scheduling and the frame-rate ceiling for {@link #run()}:
	 * ticks keep their fixed interval while frames run as fast as the
	 * renderer and the ceiling allow.
	 */
	private final FramePacer pacer = new FramePacer();

	/** Wall-clock stamp of the last canvas resync, in milliseconds. */
	private long lastCanvasSyncMillis;

	/** Ticks this run-loop iteration actually executed; see {@link #method892()}. */
	private int executedTicks;


	@OriginalMember(owner = "client!o", name = "a", descriptor = "(I)Z")
	protected final boolean isHostnameValid() {
		@Pc(16) String local16 = this.getDocumentBase().getHost().toLowerCase();
		if (local16.equals("jagex.com") || local16.endsWith(".jagex.com")) {
			return true;
		} else if (local16.equals("runescape.com") || local16.endsWith(".runescape.com")) {
			return true;
		} else if (local16.equals("stellardawn.com") || local16.endsWith(".stellardawn.com")) {
			return true;
		} else if (local16.endsWith("127.0.0.1")) {
			return true;
		} else {
			while (local16.length() > 0 && local16.charAt(local16.length() - 1) >= '0' && local16.charAt(local16.length() - 1) <= '9') {
				local16 = local16.substring(0, local16.length() - 1);
			}
			if (local16.endsWith("192.168.1.")) {
				return true;
			} else {
				this.raiseErrorScreen("invalidhost");
				return false;
			}
		}
	}

	@OriginalMember(owner = "client!o", name = "b", descriptor = "(I)V")
	private void method870() {
		@Pc(13) long local13 = Class168.method3962();
		@Pc(17) long local17 = Class92.aLongArray6[QuickchatMessageType.anInt290];
		Class92.aLongArray6[QuickchatMessageType.anInt290] = local13;
		@Pc(36) boolean local36;
		if (local17 == 0L || local17 >= local13) {
			local36 = false;
		} else {
			local36 = true;
		}
		QuickchatMessageType.anInt290 = QuickchatMessageType.anInt290 + 1 & 0x1F;
		synchronized (this) {
			RequestManager.aBoolean457 = ParticleProducerType.aBoolean359;
		}
		this.cleanup();
	}

	@OriginalMember(owner = "client!o", name = "focusGained", descriptor = "(Ljava/awt/event/FocusEvent;)V")
	@Override
	public final void focusGained(@OriginalArg(0) FocusEvent event) {
		ParticleProducerType.aBoolean359 = true;
		TextureOpIrregularBricks.aBoolean189 = true;
		// AWT callback: ask the loop to reset rather than touching its clock
		// state from this thread.
		this.pacer.requestReset();
	}

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(ILjava/lang/String;)V")
	protected final void raiseErrorScreen(@OriginalArg(1) String arg0) {
		if (this.errorScreenRaised) {
			return;
		}
		this.errorScreenRaised = true;
		System.out.println("error_game_" + arg0);
		try {
			Static458.method2593(Class126.aClass66_4.anApplet1, "loggedout");
		} catch (@Pc(31) Throwable local31) {
		}
		try {
			this.getAppletContext().showDocument(new URL(this.getCodeBase(), "error_game_" + arg0 + ".ws"), "_top");
		} catch (@Pc(51) Exception local51) {
		}
	}

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(B)V")
	protected abstract void cleanup();

	@OriginalMember(owner = "client!o", name = "start", descriptor = "()V")
	@Override
	public final void start() {
		if (Class174.anApplet_Sub1_2 == this && !FramebufferCompositor.aBoolean675) {
			Static173.aLong118 = 0L;
			// Applet lifecycle callback, off the loop thread: defer both resets.
			this.pacer.requestReset();
			FrameRateStats.requestReset();
		}
	}

	@OriginalMember(owner = "client!o", name = "getAppletContext", descriptor = "()Ljava/applet/AppletContext;")
	@Override
	public final AppletContext getAppletContext() {
		if (ModelNormalsGL.aFrame1 == null) {
			return Class126.aClass66_4 == null || Class126.aClass66_4.anApplet1 == this ? super.getAppletContext() : Class126.aClass66_4.anApplet1.getAppletContext();
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!o", name = "init", descriptor = "()V")
	public abstract void init();

	@OriginalMember(owner = "client!o", name = "b", descriptor = "(B)V")
	public final synchronized void method874() {
		if (Class164.aCanvas5 != null) {
			Class164.aCanvas5.removeFocusListener(this);
			Class164.aCanvas5.getParent().remove(Class164.aCanvas5);
		}
		@Pc(18) Container local18;
		if (BillowNoiseGenerator.aFrame3 != null) {
			local18 = BillowNoiseGenerator.aFrame3;
		} else if (ModelNormalsGL.aFrame1 == null) {
			local18 = Class126.aClass66_4.anApplet1;
		} else {
			local18 = ModelNormalsGL.aFrame1;
		}
		local18.setLayout(null);
		Class164.aCanvas5 = new GameCanvas(this);
		local18.add(Class164.aCanvas5);
		Class164.aCanvas5.setSize(GpuPointLight.anInt2881, EffectiveVertex.anInt5346);
		Class164.aCanvas5.setVisible(true);
		if (local18 == ModelNormalsGL.aFrame1) {
			@Pc(60) Insets local60 = ModelNormalsGL.aFrame1.getInsets();
			Class164.aCanvas5.setLocation(FileOnDisk.anInt4424 + local60.left, local60.top + Class120_Sub1.anInt5363);
		} else {
			Class164.aCanvas5.setLocation(FileOnDisk.anInt4424, Class120_Sub1.anInt5363);
		}
		Class164.aCanvas5.addFocusListener(this);
		Class164.aCanvas5.requestFocus();
		ParticleProducerType.aBoolean359 = true;
		RequestManager.aBoolean457 = true;
		TextureOpIrregularBricks.aBoolean189 = true;
		Js5WorkerRequestMessage.aBoolean383 = false;
		Class58.aLong59 = Class168.method3962();
	}

	@OriginalMember(owner = "client!o", name = "paint", descriptor = "(Ljava/awt/Graphics;)V")
	@Override
	public final synchronized void paint(@OriginalArg(0) Graphics graphics) {
		if (Class174.anApplet_Sub1_2 != this || FramebufferCompositor.aBoolean675) {
			return;
		}
		TextureOpIrregularBricks.aBoolean189 = true;
		if (Class4_Sub6_Sub25.aBoolean455 && Class168.method3962() - Class58.aLong59 > 1000L) {
			@Pc(24) Rectangle local24 = graphics.getClipBounds();
			if (local24 == null || Class4_Sub6_Sub37.anInt7000 <= local24.width && Static17.anInt222 <= local24.height) {
				Js5WorkerRequestMessage.aBoolean383 = true;
			}
		}
	}

	@OriginalMember(owner = "client!o", name = "getParameter", descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
	@Override
	public final String getParameter(@OriginalArg(0) String name) {
		if (ModelNormalsGL.aFrame1 == null) {
			return Class126.aClass66_4 == null || Class126.aClass66_4.anApplet1 == this ? super.getParameter(name) : Class126.aClass66_4.anApplet1.getParameter(name);
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!o", name = "stop", descriptor = "()V")
	@Override
	public final void stop() {
		System.out.println("[stop] called");
		new Exception("stop").printStackTrace(System.out);
		if (Class174.anApplet_Sub1_2 == this && !FramebufferCompositor.aBoolean675) {
			Static173.aLong118 = Class168.method3962() + 4000L;
		}
	}

	@OriginalMember(owner = "client!o", name = "update", descriptor = "(Ljava/awt/Graphics;)V")
	@Override
	public final void update(@OriginalArg(0) Graphics graphics) {
		this.paint(graphics);
	}

	@OriginalMember(owner = "client!o", name = "windowActivated", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowActivated(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "windowDeactivated", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowDeactivated(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(ZZ)V")
	private void method876(@OriginalArg(1) boolean arg0) {
		System.out.println("[shutdown] clean=" + arg0);
		new Exception("shutdown-path").printStackTrace(System.out);
		// Camera zoom and fog distance are stored beside the client rather than in
		// its own settings file, so save them here too: the wheel already saves on
		// every change, and this covers any level that reached the state without
		// going through the wheel.
		CameraPreferences.save();
		CursorSettings.save();
		Tweening.save();
		synchronized (this) {
			if (FramebufferCompositor.aBoolean675) {
				return;
			}
			FramebufferCompositor.aBoolean675 = true;
		}
		if (Class126.aClass66_4.anApplet1 != null) {
			Class126.aClass66_4.anApplet1.destroy();
		}
		try {
			this.method886();
		} catch (@Pc(33) Exception local33) {
		}
		if (this.jagmiscLoaded) {
			try {
				jagmisc.quit();
			} catch (@Pc(39) Throwable local39) {
			}
			this.jagmiscLoaded = false;
		}
		@Pc(48) SignedResource local48 = Class126.aClass66_4.method1746(Class174.anApplet_Sub1_2.getClass());
		while (local48.anInt5760 == 0) {
			Class259.method5503(100L);
		}
		if (Class164.aCanvas5 != null) {
			try {
				Class164.aCanvas5.removeFocusListener(this);
				Class164.aCanvas5.getParent().remove(Class164.aCanvas5);
			} catch (@Pc(75) Exception local75) {
			}
		}
		if (Class126.aClass66_4 != null) {
			try {
				Class126.aClass66_4.method1732();
			} catch (@Pc(83) Exception local83) {
			}
		}
		this.method880();
		if (ModelNormalsGL.aFrame1 != null) {
			try {
				System.exit(0);
			} catch (@Pc(93) Throwable local93) {
			}
		}
		System.out.println("Shutdown complete - clean:" + arg0);
	}

	@OriginalMember(owner = "client!o", name = "c", descriptor = "(B)V")
	public final void method877() {
		if (this.jagmiscLoaded) {
			return;
		}
		try {
			@Pc(22) SignedResource local22 = Class126.aClass66_4.method1736(Class174.anApplet_Sub1_2.getClass());
			while (local22.anInt5760 == 0) {
				Class259.method5503(100L);
			}
			if (local22.anObject29 != null) {
				throw (Throwable) local22.anObject29;
			}
			jagmisc.init();
			this.jagmiscLoaded = true;
			ClientFrameClock.installDuringStartup(Class4_Sub6_Sub29.method4580());
		} catch (@Pc(50) Throwable local50) {
		}
	}

	@OriginalMember(owner = "client!o", name = "destroy", descriptor = "()V")
	@Override
	public final void destroy() {
		if (Class174.anApplet_Sub1_2 == this && !FramebufferCompositor.aBoolean675) {
			Static173.aLong118 = Class168.method3962();
			Class259.method5503(5000L);
			Class99_Sub1.aClass66_5 = null;
			this.method876(false);
		}
	}

	@OriginalMember(owner = "client!o", name = "focusLost", descriptor = "(Ljava/awt/event/FocusEvent;)V")
	@Override
	public final void focusLost(@OriginalArg(0) FocusEvent event) {
		ParticleProducerType.aBoolean359 = false;
	}

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(Z)V")
	protected abstract void method880();

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(IIIIB)V")
	protected final void method881(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		try {
			if (Class174.anApplet_Sub1_2 == null) {
				Class120_Sub1.anInt5363 = 0;
				FileOnDisk.anInt4424 = 0;
				Class174.anApplet_Sub1_2 = this;
				EffectiveVertex.anInt5346 = arg1;
				Static17.anInt222 = arg1;
				BufferedSocket.anInt178 = 592;
				GpuPointLight.anInt2881 = arg2;
				Class4_Sub6_Sub37.anInt7000 = arg2;
				if (Class126.aClass66_4 == null) {
					Class99_Sub1.aClass66_5 = Class126.aClass66_4 = new SignLink(this, arg0, null, 0);
				}
				@Pc(67) SignedResource local67 = Class126.aClass66_4.method1741(1, this);
				while (local67.anInt5760 == 0) {
					Class259.method5503(10L);
				}
			} else {
				Class4_Sub41.anInt6704++;
				if (Class4_Sub41.anInt6704 >= 3) {
					this.raiseErrorScreen("alreadyloaded");
				} else {
					this.getAppletContext().showDocument(this.getDocumentBase(), "_self");
				}
			}
		} catch (@Pc(80) Throwable local80) {
			Class64_Sub1.method1654(local80, null);
			this.raiseErrorScreen("crash");
		}
	}

	@OriginalMember(owner = "client!o", name = "windowClosed", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowClosed(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "d", descriptor = "(I)V")
	protected abstract void maininit();

	@OriginalMember(owner = "client!o", name = "run", descriptor = "()V")
	@Override
	public final void run() {
		try {
			label107: {
				if (SignLink.aString23 != null) {
					@Pc(10) String local10 = SignLink.aString23.toLowerCase();
					if (local10.indexOf("sun") != -1 || local10.indexOf("apple") != -1) {
						@Pc(40) String local40 = SignLink.aString24;
						if (local40.equals("1.1") || local40.startsWith("1.1.") || local40.equals("1.2") || local40.startsWith("1.2.")) {
							this.raiseErrorScreen("wrongjava");
							break label107;
						}
					} else if (local10.indexOf("ibm") != -1 && (SignLink.aString24 == null || SignLink.aString24.equals("1.4.2"))) {
						this.raiseErrorScreen("wrongjava");
						break label107;
					}
				}
				@Pc(70) int local70;
				if (SignLink.aString24 != null && SignLink.aString24.startsWith("1.")) {
					local70 = 2;
					@Pc(72) int local72 = 0;
					while (SignLink.aString24.length() > local70) {
						@Pc(78) char local78 = SignLink.aString24.charAt(local70);
						if (local78 < '0' || local78 > '9') {
							break;
						}
						local72 = local72 * 10 + local78 - 48;
						local70++;
					}
					if (local72 >= 5) {
						Class4_Sub6_Sub25.aBoolean455 = true;
					}
				}
				if (Class126.aClass66_4.anApplet1 != null) {
					@Pc(114) Method local114 = SignLink.aMethod2;
					if (local114 != null) {
						try {
							local114.invoke(Class126.aClass66_4.anApplet1, Boolean.TRUE);
						} catch (@Pc(129) Throwable local129) {
						}
					}
				}
				Class193.method4384();
				Class4_Sub18.method2036();
				this.method874();
				this.maininit();
				System.out.println("[run] maininit done");
				this.method877();
				// The pacer becomes the published frame clock; nothing else may be.
				ClientFrameClock.install(this.pacer);
				while (Static173.aLong118 == 0L || Class168.method3962() < Static173.aLong118) {
					@Pc(20) int dueTicks = this.pacer.ticksDue(Class149.anInt4535);
					// Never bound this on Static411.anInt6741: the tick path clears
					// it mid-tick, which used to truncate a multi-tick frame to one.
					this.executedTicks = TickBatch.run(dueTicks, this::method870);
					this.method892();
					if (this.executedTicks > 0) {
						Static441.method5566(Class126.aClass66_4, Class164.aCanvas5);
					}
					this.pacer.sleepToDeadline(Class149.anInt4535, ParticleProducerType.aBoolean359);
				}
				System.out.println("[run] loop ended aLong118=" + Static173.aLong118);
			}
		} catch (@Pc(190) Throwable local190) {
			System.out.println("[run] uncaught:");
			local190.printStackTrace(System.out);
			Class64_Sub1.method1654(local190, this.method887());
			this.raiseErrorScreen("crash");
		}
		this.method876(true);
	}

	@OriginalMember(owner = "client!o", name = "a", descriptor = "(IZIIILjava/lang/String;II)V")
	protected final void method885(@OriginalArg(5) String arg0, @OriginalArg(7) int arg1) {
		try {
			GpuPointLight.anInt2881 = 1024;
			Class4_Sub6_Sub37.anInt7000 = 1024;
			Class120_Sub1.anInt5363 = 0;
			FileOnDisk.anInt4424 = 0;
			BufferedSocket.anInt178 = 592;
			EffectiveVertex.anInt5346 = 768;
			Static17.anInt222 = 768;
			Class174.anApplet_Sub1_2 = this;
			ModelNormalsGL.aFrame1 = new Frame();
			ModelNormalsGL.aFrame1.setTitle("Jagex");
			ModelNormalsGL.aFrame1.setResizable(true);
			ModelNormalsGL.aFrame1.addWindowListener(this);
			ModelNormalsGL.aFrame1.setVisible(true);
			ModelNormalsGL.aFrame1.toFront();
			@Pc(38) Insets local38 = ModelNormalsGL.aFrame1.getInsets();
			ModelNormalsGL.aFrame1.setSize(local38.right + Class4_Sub6_Sub37.anInt7000 + local38.left, local38.bottom + Static17.anInt222 + local38.top);
			Class99_Sub1.aClass66_5 = Class126.aClass66_4 = new SignLink(null, arg1, arg0, 30);
			@Pc(70) SignedResource local70 = Class126.aClass66_4.method1741(1, this);
			while (local70.anInt5760 == 0) {
				Class259.method5503(10L);
			}
		} catch (@Pc(83) Exception local83) {
			Class64_Sub1.method1654(local83, null);
		}
	}

	@OriginalMember(owner = "client!o", name = "e", descriptor = "(I)V")
	protected abstract void method886();

	@OriginalMember(owner = "client!o", name = "windowIconified", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowIconified(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "e", descriptor = "(B)Ljava/lang/String;")
	protected String method887() {
		return null;
	}

	@OriginalMember(owner = "client!o", name = "getDocumentBase", descriptor = "()Ljava/net/URL;")
	@Override
	public final URL getDocumentBase() {
		if (ModelNormalsGL.aFrame1 == null) {
			return Class126.aClass66_4 == null || Class126.aClass66_4.anApplet1 == this ? super.getDocumentBase() : Class126.aClass66_4.anApplet1.getDocumentBase();
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!o", name = "windowOpened", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowOpened(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "windowClosing", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowClosing(@OriginalArg(0) WindowEvent event) {
		this.destroy();
	}

	@OriginalMember(owner = "client!o", name = "getCodeBase", descriptor = "()Ljava/net/URL;")
	@Override
	public final URL getCodeBase() {
		if (ModelNormalsGL.aFrame1 == null) {
			return Class126.aClass66_4 == null || Class126.aClass66_4.anApplet1 == this ? super.getCodeBase() : Class126.aClass66_4.anApplet1.getCodeBase();
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!o", name = "g", descriptor = "(I)V")
	protected abstract void method891();

	@OriginalMember(owner = "client!o", name = "windowDeiconified", descriptor = "(Ljava/awt/event/WindowEvent;)V")
	@Override
	public final void windowDeiconified(@OriginalArg(0) WindowEvent event) {
	}

	@OriginalMember(owner = "client!o", name = "h", descriptor = "(I)V")
	private void method892() {
		@Pc(10) long local10 = Class168.method3962();
		// Publish what the debug overlay prints: frames and ticks counted over
		// the last second, so decoupled pacing can be read in game. The old
		// reciprocal of one frame delta was scaled ~32x and knew nothing about
		// ticks.
		FrameRateStats.record(this.executedTicks, local10);
		// Resync the canvas on a wall-clock interval: frames no longer run at
		// tick rate, so counting frames here would resize and reposition the
		// AWT canvas many times per second at high frame rates.
		if (local10 - this.lastCanvasSyncMillis >= 1000L) {
			this.lastCanvasSyncMillis = local10;
			TextureOpIrregularBricks.aBoolean189 = true;
			Class164.aCanvas5.setSize(GpuPointLight.anInt2881, EffectiveVertex.anInt5346);
			Class164.aCanvas5.setVisible(true);
			if (ModelNormalsGL.aFrame1 != null && BillowNoiseGenerator.aFrame3 == null) {
				@Pc(86) Insets local86 = ModelNormalsGL.aFrame1.getInsets();
				Class164.aCanvas5.setLocation(FileOnDisk.anInt4424 + local86.left, local86.top - -Class120_Sub1.anInt5363);
			} else {
				Class164.aCanvas5.setLocation(FileOnDisk.anInt4424, Class120_Sub1.anInt5363);
			}
		}
		this.method891();
	}

@OriginalMember(owner = "client!o", name = "O", descriptor = "[Lclient!at;")
	public static Class15[] aClass15Array2;
@OriginalMember(owner = "client!o", name = "Q", descriptor = "F")
	public static float aFloat30;
@OriginalMember(owner = "client!o", name = "S", descriptor = "I")
	private static int anInt972;
@OriginalMember(owner = "client!o", name = "T", descriptor = "I")
	private static int anInt973;
@OriginalMember(owner = "client!o", name = "U", descriptor = "Z")
	private static boolean aBoolean90;
@OriginalMember(owner = "client!o", name = "V", descriptor = "Z")
	private static boolean aBoolean91;
@OriginalMember(owner = "client!o", name = "x", descriptor = "Lclient!gn;")
	public static final EntityNodeList aClass84_4 = new EntityNodeList();
@OriginalMember(owner = "client!o", name = "N", descriptor = "Lclient!pn;")
	public static final Class186 aClass186_3 = new Class186("", 14);
@OriginalMember(owner = "client!o", name = "P", descriptor = "I")
	public static int anInt970 = 0;
@OriginalMember(owner = "client!o", name = "a", descriptor = "()V")
	public static void method873() {
		for (@Pc(1) int local1 = 0; local1 < Static1.anInt6; local1++) {
			@Pc(6) Class16_Sub1 local6 = Class4_Sub6_Sub38.aClass16_Sub1Array3[local1];
			Class4_Sub6_Sub15.method2978(local6);
			Class4_Sub6_Sub38.aClass16_Sub1Array3[local1] = null;
		}
		Static1.anInt6 = 0;
	}
@OriginalMember(owner = "client!o", name = "a", descriptor = "(IFIII[FIIFII)V")
	public static void method875(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) float[] arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) float arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9) {
		@Pc(5) int local5 = arg3 - arg2;
		@Pc(13) int local13 = arg5 - arg6;
		@Pc(17) int local17 = arg0 - arg9;
		@Pc(38) float local38 = (float) local17 * arg4[2] + (float) local5 * arg4[1] + arg4[0] * (float) local13;
		@Pc(59) float local59 = arg4[3] * (float) local13 + (float) local5 * arg4[4] + arg4[5] * (float) local17;
		@Pc(80) float local80 = arg4[7] * (float) local5 + (float) local13 * arg4[6] + arg4[8] * (float) local17;
		@Pc(91) float local91 = (float) Math.atan2((double) local38, (double) local80) / 6.2831855F + 0.5F;
		if (arg7 != 1.0F) {
			local91 *= arg7;
		}
		@Pc(105) float local105 = arg1 + local59 + 0.5F;
		@Pc(112) float local112;
		if (arg8 == 1) {
			local112 = local91;
			local91 = -local105;
			local105 = local112;
		} else if (arg8 == 2) {
			local91 = -local91;
			local105 = -local105;
		} else if (arg8 == 3) {
			local112 = local91;
			local91 = local105;
			local105 = -local112;
		}
		Class4_Sub1_Sub9.aFloat102 = local105;
		VertexDeclarationComponent.aFloat211 = local91;
	}
@OriginalMember(owner = "client!o", name = "a", descriptor = "(IIBIZI)V")
	public static void method878(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) int arg4) {
		if (arg1 < 1) {
			arg1 = 1;
		}
		if (arg4 < 1) {
			arg4 = 1;
		}
		@Pc(21) int local21 = arg4 - 334;
		if (local21 < 0) {
			local21 = 0;
		} else if (local21 > 100) {
			local21 = 100;
		}
		@Pc(43) int local43 = (Class83.aShort24 - Static298.aShort67) * local21 / 100 + Static298.aShort67;
		if (TextureOpMandelbrot.aShort66 > local43) {
			local43 = TextureOpMandelbrot.aShort66;
		} else if (local43 > Class4_Sub17.aShort18) {
			local43 = Class4_Sub17.aShort18;
		}
		@Pc(69) int local69 = arg4 * 512 * local43 / (arg1 * 334);
		@Pc(106) int local106;
		@Pc(112) int local112;
		@Pc(81) short local81;
		if (CursorType.aShort19 > local69) {
			local81 = CursorType.aShort19;
			local43 = arg1 * local81 * 334 / (arg4 * 512);
			if (local43 > Class4_Sub17.aShort18) {
				local43 = Class4_Sub17.aShort18;
				local106 = arg4 * local43 * 512 / (local81 * 334);
				local112 = (arg1 - local106) / 2;
				if (arg3) {
					MonoStereoData.aClass19_16.e();
					MonoStereoData.aClass19_16.method4293(arg2, local112, arg0, -16777216, arg4);
					MonoStereoData.aClass19_16.method4293(arg2 + arg1 - local112, local112, arg0, -16777216, arg4);
				}
				arg2 += local112;
				arg1 -= local112 * 2;
			}
		} else if (Class244.aShort100 < local69) {
			local81 = Class244.aShort100;
			local43 = local81 * 334 * arg1 / (arg4 * 512);
			if (local43 < TextureOpMandelbrot.aShort66) {
				local43 = TextureOpMandelbrot.aShort66;
				local106 = local81 * 334 * arg1 / (local43 * 512);
				local112 = (arg4 - local106) / 2;
				if (arg3) {
					MonoStereoData.aClass19_16.e();
					MonoStereoData.aClass19_16.method4293(arg2, arg1, arg0, -16777216, local112);
					MonoStereoData.aClass19_16.method4293(arg2, arg1, arg4 + arg0 - local112, -16777216, local112);
				}
				arg4 -= local112 * 2;
				arg0 += local112;
			}
		}
		AbstractFontRenderer.anInt6458 = local43 * arg4 / 334;
		ProjectileAnimation.anInt2574 = arg0;
		Class16_Sub4_Sub2.anInt2527 = (short) arg4;
		Class196.anInt5685 = (short) arg1;
		ProjectileAnimation.anInt2600 = arg2;
	}
@OriginalMember(owner = "client!o", name = "a", descriptor = "(BI[B)I")
	public static int method879(@OriginalArg(1) int arg0, @OriginalArg(2) byte[] arg1) {
		return Static179.method2846(0, arg1, arg0);
	}
@OriginalMember(owner = "client!o", name = "c", descriptor = "(I)V")
	public static void method882(@OriginalArg(0) int arg0) {
		MusicPatchPcmStream.anInt6251 = arg0;
		for (@Pc(3) int local3 = 0; local3 < GpuFaceNormal.menuOpenWidth; local3++) {
			for (@Pc(6) int local6 = 0; local6 < GlModel.anInt3361; local6++) {
				if (Class70.aClass164ArrayArrayArray5[arg0][local3][local6] == null) {
					Class70.aClass164ArrayArrayArray5[arg0][local3][local6] = new Class164(arg0, local3, local6);
				}
			}
		}
	}
@OriginalMember(owner = "client!o", name = "d", descriptor = "(B)V")
	public static void method884() {
		for (@Pc(11) int local11 = 0; local11 < Class10.anInt163; local11++) {
			@Pc(17) int local17 = TextureOpBoxBlur.anIntArray412[local11];
			@Pc(21) NPCEntity local21 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local17];
			if (local21 != null) {
				Class4_Sub9.method761(local21.npcType.anInt7201, local21);
			}
		}
	}
@OriginalMember(owner = "client!o", name = "providesignlink", descriptor = "(Lclient!et;)V")
	private static void method888(@OriginalArg(0) SignLink arg0) {
		Class126.aClass66_4 = arg0;
		Class99_Sub1.aClass66_5 = arg0;
	}
@OriginalMember(owner = "client!o", name = "a", descriptor = "(IB)V")
	public static void method889(@OriginalArg(0) int arg0) {
		OpenGl2DImageTexture.anInt5475 = arg0;
		SceneLocEntity.aClass68_19.method1777();
	}
}
