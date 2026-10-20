package com.jagex.game.runetek5.config.loctype;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import jagex3.jagmisc.jagmisc;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Static209;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.config.loctype.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!wf")
public final class LocTypeList {

	@OriginalMember(owner = "client!wf", name = "x", descriptor = "I")
	public int featureMask;

	@OriginalMember(owner = "client!wf", name = "l", descriptor = "Z")
	public boolean aBoolean678 = false;

	@OriginalMember(owner = "client!wf", name = "n", descriptor = "Lclient!fa;")
	private LRUCache aClass68_54 = new LRUCache(64);

	@OriginalMember(owner = "client!wf", name = "s", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_55 = new LRUCache(500);

	@OriginalMember(owner = "client!wf", name = "v", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_56 = new LRUCache(30);

	@OriginalMember(owner = "client!wf", name = "w", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_57 = new LRUCache(50);

	@OriginalMember(owner = "client!wf", name = "r", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider configClient;

	@OriginalMember(owner = "client!wf", name = "d", descriptor = "Z")
	public boolean aBoolean677;

	@OriginalMember(owner = "client!wf", name = "o", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider meshResource;

	@OriginalMember(owner = "client!wf", name = "<init>", descriptor = "(Lclient!jk;IZLclient!fs;Lclient!fs;)V")
	public LocTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) boolean allowMembers, @OriginalArg(3) Js5ResourceProvider configClient, @OriginalArg(4) Js5ResourceProvider meshResource) {
		this.configClient = configClient;
		this.aBoolean677 = allowMembers;
		this.meshResource = meshResource;
		if (this.configClient != null) {
			@Pc(47) int local47 = this.configClient.method2100() - 1;
			this.configClient.method2108(local47);
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(B)V")
	public void method5555() {
		@Pc(6) LRUCache local6 = this.aClass68_54;
		synchronized (this.aClass68_54) {
			this.aClass68_54.method1777();
		}
		local6 = this.aClass68_55;
		synchronized (this.aClass68_55) {
			this.aClass68_55.method1777();
		}
		local6 = this.aClass68_56;
		synchronized (this.aClass68_56) {
			this.aClass68_56.method1777();
		}
		local6 = this.aClass68_57;
		synchronized (this.aClass68_57) {
			this.aClass68_57.method1777();
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(II)V")
	public void method5556() {
		@Pc(2) LRUCache local2 = this.aClass68_54;
		synchronized (this.aClass68_54) {
			this.aClass68_54.method1788(5);
		}
		local2 = this.aClass68_55;
		synchronized (this.aClass68_55) {
			this.aClass68_55.method1788(5);
		}
		local2 = this.aClass68_56;
		synchronized (this.aClass68_56) {
			this.aClass68_56.method1788(5);
		}
		local2 = this.aClass68_57;
		synchronized (this.aClass68_57) {
			this.aClass68_57.method1788(5);
		}
	}

	@OriginalMember(owner = "client!wf", name = "b", descriptor = "(B)V")
	public void method5557() {
		@Pc(6) LRUCache local6 = this.aClass68_54;
		synchronized (this.aClass68_54) {
			this.aClass68_54.method1791();
		}
		local6 = this.aClass68_55;
		synchronized (this.aClass68_55) {
			this.aClass68_55.method1791();
		}
		local6 = this.aClass68_56;
		synchronized (this.aClass68_56) {
			this.aClass68_56.method1791();
		}
		local6 = this.aClass68_57;
		synchronized (this.aClass68_57) {
			this.aClass68_57.method1791();
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(IZ)V")
	public void method5558(@OriginalArg(0) int arg0) {
		this.featureMask = arg0;
		@Pc(9) LRUCache local9 = this.aClass68_55;
		synchronized (this.aClass68_55) {
			this.aClass68_55.method1777();
		}
		local9 = this.aClass68_56;
		synchronized (this.aClass68_56) {
			this.aClass68_56.method1777();
		}
		local9 = this.aClass68_57;
		synchronized (this.aClass68_57) {
			this.aClass68_57.method1777();
		}
	}

	@OriginalMember(owner = "client!wf", name = "b", descriptor = "(II)Lclient!rd;")
	public LocType method5560(@OriginalArg(0) int arg0) {
		@Pc(10) LRUCache local10 = this.aClass68_54;
		@Pc(20) LocType local20;
		synchronized (this.aClass68_54) {
			local20 = (LocType) this.aClass68_54.method1787((long) arg0);
		}
		if (local20 != null) {
			return local20;
		}
		@Pc(41) byte[] local41 = this.configClient.method2104(Class21.method523(arg0), Class183.method4142(arg0));
		local20 = new LocType();
		local20.locTypeList = this;
		local20.id = arg0;
		if (local41 != null) {
			local20.method4596(new Packet(local41));
		}
		local20.method4581();
		if (!this.aBoolean677 && local20.aBoolean532) {
			local20.actions = null;
			local20.quests = null;
		}
		if (local20.aBoolean525) {
			local20.anInt5851 = 0;
			local20.aBoolean534 = false;
		}
		@Pc(87) LRUCache local87 = this.aClass68_54;
		synchronized (this.aClass68_54) {
			this.aClass68_54.method1779((long) arg0, local20);
			return local20;
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(BZ)V")
	public void method5562(@OriginalArg(1) boolean arg0) {
		if (arg0 != this.aBoolean678) {
			this.aBoolean678 = arg0;
			this.method5555();
		}
	}

	@OriginalMember(owner = "client!wf", name = "a", descriptor = "(ZB)V")
	public void method5564(@OriginalArg(0) boolean arg0) {
		if (arg0 != this.aBoolean677) {
			this.aBoolean677 = arg0;
			this.method5555();
		}
	}

	@OriginalMember(owner = "client!wf", name = "c", descriptor = "(II)V")
	public void method5565(@OriginalArg(1) int arg0) {
		this.aClass68_54 = new LRUCache(arg0);
	}

@OriginalMember(owner = "client!wf", name = "k", descriptor = "[Ljava/lang/String;")
	public static String[] aStringArray40;
@OriginalMember(owner = "client!wf", name = "u", descriptor = "[[Z")
	public static boolean[][] aBooleanArrayArray6;
@OriginalMember(owner = "client!wf", name = "p", descriptor = "Lclient!ib;")
	public static CubeMapSpecularEffect aClass102_40 = new CubeMapSpecularEffect(64);
@OriginalMember(owner = "client!wf", name = "q", descriptor = "[I")
	public static final int[] mapElementLocIds = new int[250];
@OriginalMember(owner = "client!wf", name = "t", descriptor = "[[I")
	public static final int[][] anIntArrayArray61 = new int[][] { { 0, 2, 4, 6 }, { 6, 0, 2, 4 }, { 6, 0, 2 }, { 2, 6, 0 }, { 0, 2, 6 }, { 6, 0, 2 }, { 5, 6, 0, 1, 2, 4 }, { 7, 2, 4, 4 }, { 2, 4, 4, 7 }, { 6, 6, 4, 0, 2, 2 }, { 0, 2, 2, 6, 6, 4 }, { 0, 2, 2, 4, 6, 6 }, { 0, 2, 4, 6 } };
@OriginalMember(owner = "client!wf", name = "a", descriptor = "(IZI)V")
	public static void method5561(@OriginalArg(1) boolean arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1 && !Toolkit.openGlNativesAvailable()) {
			// No libjaclib/libjaggl for this platform (macOS in particular):
			// use the pure-Java renderer instead of throwing UnsatisfiedLinkError.
			System.out.println("OpenGL natives (jaclib/jaggl) are not available for this platform; using the Java software renderer.");
			arg1 = 0;
		}
		if (arg1 == 0) {
			MonoStereoData.aClass19_16 = Toolkit.method4241(MidiPcmStream.anInterface7_3, TextureOpBrightness.aClass50_Sub1_1.anInt3440 * 2, Class164.aCanvas5, arg1, Class126.aClass66_4);
		} else {
			if (arg0) {
				MonoStereoData.aClass19_16 = Toolkit.method4241(MidiPcmStream.anInterface7_3, 0, Class164.aCanvas5, 0, Class126.aClass66_4);
				MonoStereoData.aClass19_16.p(0);
				@Pc(22) com.jagex.game.runetek5.graphics.font.FontMetrics local22 = ParticleList.method3546(JavaThreadResource.anInt1122, Static209.aClass76_48);
				@Pc(31) AbstractFontRenderer local31 = MonoStereoData.aClass19_16.createFontRenderer(local22, SpriteType.decodeGroup(GlGround.aClass76_60, JavaThreadResource.anInt1122));
				TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423), local31);
				MonoStereoData.aClass19_16.method4247();
				Class4_Sub6_Sub31.method4697();
				MonoStereoData.aClass19_16.method4267();
			}
			try {
				MonoStereoData.aClass19_16 = Toolkit.method4241(MidiPcmStream.anInterface7_3, TextureOpBrightness.aClass50_Sub1_1.anInt3440 * 2, Class164.aCanvas5, arg1, Class126.aClass66_4);
				if (MonoStereoData.aClass19_16.method4300()) {
					@Pc(62) boolean local62 = true;
					try {
						local62 = jagmisc.getTotalPhysicalMemory() > 268435456L;
					} catch (@Pc(72) Throwable local72) {
					}
					@Pc(78) MemoryPool local78;
					if (local62) {
						local78 = MonoStereoData.aClass19_16.createHeap(146800640);
					} else {
						local78 = MonoStereoData.aClass19_16.createHeap(104857600);
					}
					MonoStereoData.aClass19_16.setHeap(local78);
				}
			} catch (@Pc(89) Throwable local89) {
				MonoStereoData.aClass19_16 = Toolkit.method4241(MidiPcmStream.anInterface7_3, 0, Class164.aCanvas5, 0, Class126.aClass66_4);
				arg1 = 0;
			}
		}
		Packet.anInt2973 = arg1;
		Class70_Sub1.method1813();
		if (!MonoStereoData.aClass19_16.method4264()) {
			TextureType.anInt4043 = 1;
		}
		MonoStereoData.aClass19_16.allocateThreads(TextureType.anInt4043);
		MonoStereoData.aClass19_16.linkThreads(0);
		MonoStereoData.aClass19_16.setOffsets(8);
		Class3_Sub3.aClass31_1 = MonoStereoData.aClass19_16.method4274();
		GlFramebuffer.aClass31_2 = MonoStereoData.aClass19_16.method4274();
		EnvironmentContext.method4607();
		MonoStereoData.aClass19_16.setShrinkTextures(!TextureOpBrightness.aClass50_Sub1_1.aBoolean296);
		if (MonoStereoData.aClass19_16.method4252()) {
			Class210.method4772(TextureOpBrightness.aClass50_Sub1_1.aBoolean300);
		}
		ColorImageCache.method3682(Static283.anInt5187 >> 3, MonoStereoData.aClass19_16, GLBillowTextureData.anInt5666 >> 3);
		Class133.method3355();
		RuntimeException_Sub1.aBoolean425 = false;
	}
@OriginalMember(owner = "client!wf", name = "a", descriptor = "(IILclient!ya;IIIIIIIII[[[B)V")
	public static void method5563(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int arg10, @OriginalArg(12) byte[][][] arg11) {
		if (arg9 == 0 || arg1 == 0) {
			return;
		}
		if (arg9 == 9) {
			arg0 = arg0 + 1 & 0x3;
			arg9 = 1;
		}
		if (arg9 == 10) {
			arg0 = arg0 + 3 & 0x3;
			arg9 = 1;
		}
		if (arg9 == 11) {
			arg9 = 8;
			arg0 = arg0 + 3 & 0x3;
		}
		arg2.DA(arg7, arg8, arg4, arg5, arg3, arg10, arg11[arg9 - 1][arg0], arg1, arg6);
	}
}
