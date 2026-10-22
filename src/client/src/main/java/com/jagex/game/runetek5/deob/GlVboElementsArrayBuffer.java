package com.jagex.game.runetek5.deob;

import jaclib.memory.NativeBuffer;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.client.animation.Tweening;
import com.jagex.game.runetek5.client.cursor.CursorSettings;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;

import jagex3.jagmisc.jagmisc;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.cycle.FrameRateLimit;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class48;
import com.jagex.game.runetek5.entity.PathingEntity;
@OriginalClass("client!ue")
public final class GlVboElementsArrayBuffer extends AbstractArbBufferGL implements ElementsBufferGL {

	@OriginalMember(owner = "client!ue", name = "z", descriptor = "[I")
	public static final int[] anIntArray505 = new int[256];

	@OriginalMember(owner = "client!ue", name = "x", descriptor = "I")
	private int anInt6626;

	static {
		for (@Pc(4) int local4 = 0; local4 < 256; local4++) {
			@Pc(7) int local7 = local4;
			for (@Pc(9) int local9 = 0; local9 < 8; local9++) {
				if ((local7 & 0x1) == 1) {
					local7 = local7 >>> 1 ^ 0xEDB88320;
				} else {
					local7 >>>= 0x1;
				}
			}
			anIntArray505[local4] = local7;
		}
	}

	@OriginalMember(owner = "client!ue", name = "<init>", descriptor = "(Lclient!fd;I[BIZ)V")
	public GlVboElementsArrayBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		super(arg0, 34962, arg2, arg3, arg4);
		this.anInt6626 = arg1;
	}

	@OriginalMember(owner = "client!ue", name = "<init>", descriptor = "(Lclient!fd;ILclient!jaggl/memory/NativeBuffer;IZ)V")
	public GlVboElementsArrayBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) NativeBuffer arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		super(arg0, 34962, arg2, arg3, arg4);
		this.anInt6626 = arg1;
	}

	@OriginalMember(owner = "client!ue", name = "a", descriptor = "(BII[B)V")
	@Override
	public void method5184(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) byte[] arg2) {
		this.method5177(arg0, arg2);
		this.anInt6626 = arg1;
	}

	@OriginalMember(owner = "client!ue", name = "d", descriptor = "(B)V")
	@Override
	protected void setAsActiveBuffer() {
		super.toolkit.method1944(this);
	}

	@OriginalMember(owner = "client!ue", name = "b", descriptor = "(I)J")
	@Override
	public long getAddress() {
		return 0L;
	}

	@OriginalMember(owner = "client!ue", name = "a", descriptor = "(Z)I")
	@Override
	public int method5182() {
		return this.anInt6626;
	}

	@OriginalMember(owner = "client!ue", name = "a", descriptor = "(I)I")
	@Override
	public int method5181() {
		return super.id;
	}

@OriginalMember(owner = "client!ue", name = "E", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_23;
@OriginalMember(owner = "client!ue", name = "B", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_88 = new Class215(23, 3);
@OriginalMember(owner = "client!ue", name = "C", descriptor = "I")
	public static int anInt6628 = 2;
@OriginalMember(owner = "client!ue", name = "a", descriptor = "(Ljava/lang/String;ZI)V")
	public static void method5186(@OriginalArg(0) String arg0, @OriginalArg(1) boolean arg1) {
		if (GlToolkit.aClass219_2 == Class222.aClass219_6 && GlToolkit.activeChildCount < 2) {
			return;
		}
		if (arg0.equalsIgnoreCase("errortest")) {
			throw new RuntimeException();
		}
		if (arg0.equals("nativememerror")) {
			throw new OutOfMemoryError("native(MPR");
		}
		try {
			if (arg0.equalsIgnoreCase("fpson")) {
				MusicPatch.aBoolean507 = true;
				Static441.method5568("fps debug enabled");
				return;
			}
			if (arg0.equalsIgnoreCase("fpsoff")) {
				MusicPatch.aBoolean507 = false;
				Static441.method5568("fps debug disabled");
				return;
			}
			if (arg0.equalsIgnoreCase("displayfps")) {
				MusicPatch.aBoolean507 = !MusicPatch.aBoolean507;
				Static441.method5568(MusicPatch.aBoolean507 ? "fps debug enabled" : "fps debug disabled");
				return;
			}
			if (arg0.equalsIgnoreCase("cursor")) {
				boolean cursorsEnabled = CursorSettings.toggle();
				CursorSettings.save();
				Static441.method5568(cursorsEnabled ? "custom cursors enabled" : "custom cursors disabled");
				return;
			}
			if (arg0.equalsIgnoreCase("cursoron")) {
				CursorSettings.setEnabled(true);
				CursorSettings.save();
				Static441.method5568("custom cursors enabled");
				return;
			}
			if (arg0.equalsIgnoreCase("cursoroff")) {
				CursorSettings.setEnabled(false);
				CursorSettings.save();
				Static441.method5568("custom cursors disabled");
				return;
			}
			if (arg0.equals("systemmem")) {
				try {
					Static441.method5568("System memory: " + jagmisc.getAvailablePhysicalMemory() / 1048576L + "/" + jagmisc.getTotalPhysicalMemory() / 1048576L + "Mb");
					return;
				} catch (@Pc(75) Throwable local75) {
					return;
				}
			}
			if (arg0.equalsIgnoreCase("cls")) {
				OpenGlArbElementArrayBuffer.anInt1881 = 0;
				Static167.chatScrollOffset = 0;
				return;
			}
			if (arg0.equalsIgnoreCase("cleartext")) {
				GameShell.aClass84_4.method2310();
				Static441.method5568("Text coords cleared");
				return;
			}
			@Pc(106) int local106;
			@Pc(125) int local125;
			@Pc(115) Runtime local115;
			if (arg0.equalsIgnoreCase("gc")) {
				Class70_Sub2.method5082();
				for (local106 = 0; local106 < 10; local106++) {
					System.gc();
				}
				local115 = Runtime.getRuntime();
				local125 = (int) ((local115.totalMemory() - local115.freeMemory()) / 1024L);
				Static441.method5568("mem=" + local125 + "k");
				return;
			}
			if (arg0.equalsIgnoreCase("compact")) {
				Class70_Sub2.method5082();
				for (local106 = 0; local106 < 10; local106++) {
					System.gc();
				}
				local115 = Runtime.getRuntime();
				local125 = (int) ((local115.totalMemory() - local115.freeMemory()) / 1024L);
				Static441.method5568("Memory before cleanup=" + local125 + "k");
				Class30.method743();
				Class70_Sub2.method5082();
				for (@Pc(185) int local185 = 0; local185 < 10; local185++) {
					System.gc();
				}
				local125 = (int) ((local115.totalMemory() - local115.freeMemory()) / 1024L);
				Static441.method5568("Memory after cleanup=" + local125 + "k");
				return;
			}
			if (arg0.equalsIgnoreCase("pcachesize")) {
				Static441.method5568("Number of player models in cache:" + FixedFunctionSpecularPass.method4232());
				return;
			}
			if (arg0.equalsIgnoreCase("clientdrop")) {
				Static441.method5568("Dropped client connection");
				if (VertexDeclarationComponent.anInt6667 == 30) {
					ParticleEffector.method2731();
					return;
				}
				if (VertexDeclarationComponent.anInt6667 == 25) {
					Class16_Sub1_Sub1.aBoolean38 = true;
				}
				return;
			}
			if (arg0.equalsIgnoreCase("clientjs5drop")) {
				MECType.aClass255_2.method5474();
				Static441.method5568("Dropped client js5 net queue");
				return;
			}
			if (arg0.equalsIgnoreCase("serverjs5drop")) {
				MECType.aClass255_2.writeTerminate();
				Static441.method5568("Dropped server js5 net queue");
				return;
			}
			if (arg0.equalsIgnoreCase("breakcon")) {
				Class126.aClass66_4.method1753();
				QuickchatMessageTypeList.aClass11_5.method132();
				MECType.aClass255_2.method5466();
				Static441.method5568("Breaking new connections for 5 seconds");
				return;
			}
			if (arg0.equalsIgnoreCase("rebuild")) {
				PathingEntity.method4744();
				Static277.method3932();
				Static441.method5568("Rebuilding map");
				return;
			}
			if (arg0.equalsIgnoreCase("wm1")) {
				Class120.method4107(1, -1, -1, false);
				if (Class269.method5664() != 1) {
					Static441.method5568("wm1 failed");
					return;
				}
				Static441.method5568("wm1 succeeded");
				return;
			}
			if (arg0.equalsIgnoreCase("wm2")) {
				Class120.method4107(2, -1, -1, false);
				if (Class269.method5664() != 2) {
					Static441.method5568("wm2 failed");
					return;
				}
				Static441.method5568("wm2 succeeded");
				return;
			}
			if (arg0.equalsIgnoreCase("wm3")) {
				Class120.method4107(3, 768, 1024, false);
				if (Class269.method5664() != 3) {
					Static441.method5568("wm3 failed");
					return;
				}
				Static441.method5568("wm3 succeeded");
				return;
			}
			if (arg0.equalsIgnoreCase("tk0")) {
				ParticleList.method3551(0);
				if (Packet.anInt2973 == 0) {
					Static441.method5568("Entered tk0");
					TextureOpBrightness.aClass50_Sub1_1.anInt3445 = 0;
					TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
					RuntimeException_Sub1.aBoolean425 = false;
					return;
				}
				Static441.method5568("Failed to enter tk0");
				return;
			}
			if (arg0.equalsIgnoreCase("tk1")) {
				ParticleList.method3551(1);
				if (Packet.anInt2973 != 1) {
					Static441.method5568("Failed to enter tk1");
					return;
				}
				Static441.method5568("Entered tk1");
				TextureOpBrightness.aClass50_Sub1_1.anInt3445 = 1;
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
				RuntimeException_Sub1.aBoolean425 = false;
				return;
			}
			if (arg0.equalsIgnoreCase("tk2")) {
				ParticleList.method3551(2);
				if (Packet.anInt2973 == 2) {
					Static441.method5568("Entered tk2");
					TextureOpBrightness.aClass50_Sub1_1.anInt3445 = 2;
					TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
					RuntimeException_Sub1.aBoolean425 = false;
					return;
				}
				Static441.method5568("Failed to enter tk2");
				return;
			}
			if (arg0.equalsIgnoreCase("tk3")) {
				ParticleList.method3551(3);
				if (Packet.anInt2973 == 3) {
					Static441.method5568("Entered tk3");
					return;
				}
				Static441.method5568("Failed to enter tk3");
				return;
			}
			if (arg0.equalsIgnoreCase("ot")) {
				TextureOpBrightness.aClass50_Sub1_1.aBoolean293 = !TextureOpBrightness.aClass50_Sub1_1.aBoolean293;
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
				RuntimeException_Sub1.aBoolean425 = false;
				PathingEntity.method4744();
				Static441.method5568("ot=" + TextureOpBrightness.aClass50_Sub1_1.aBoolean293);
				return;
			}
			if (arg0.equalsIgnoreCase("gb")) {
				TextureOpBrightness.aClass50_Sub1_1.aBoolean289 = !TextureOpBrightness.aClass50_Sub1_1.aBoolean289;
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
				RuntimeException_Sub1.aBoolean425 = false;
				PathingEntity.method4744();
				Static441.method5568("gb=" + TextureOpBrightness.aClass50_Sub1_1.aBoolean289);
				return;
			}
			@Pc(568) int local568;
			if (arg0.startsWith("shadows")) {
				if (arg0.length() < 8) {
					Static441.method5568("Invalid shadows value");
					return;
				}
				@Pc(558) String local558 = arg0.substring(8);
				local568 = Class60.method1590(local558) ? ParticleProducerType.method3113(local558) : -1;
				if (local568 >= 0 && local568 <= 2) {
					TextureOpBrightness.aClass50_Sub1_1.method2852(Packet.anInt2973, local568);
					TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
					RuntimeException_Sub1.aBoolean425 = false;
					PathingEntity.method4744();
					Static441.method5568("shadows=" + local568);
					return;
				}
				Static441.method5568("Invalid shadows value");
				return;
			}
			if (arg0.startsWith("textures")) {
				TextureOpBrightness.aClass50_Sub1_1.aBoolean307 = !TextureOpBrightness.aClass50_Sub1_1.aBoolean307;
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
				RuntimeException_Sub1.aBoolean425 = false;
				Class16_Sub4_Sub2.method2149();
				PathingEntity.method4744();
				Static441.method5568("textures=" + TextureOpBrightness.aClass50_Sub1_1.aBoolean307);
				return;
			}
			if (arg0.startsWith("setba")) {
				if (arg0.length() < 6) {
					Static441.method5568("Invalid buildarea value");
					return;
				}
				local106 = ParticleProducerType.method3113(arg0.substring(6));
				if (local106 >= 0 && local106 <= client.method898(BreakingWaterEffect.anInt1503)) {
					TextureOpBrightness.aClass50_Sub1_1.anInt3434 = local106;
					TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
					RuntimeException_Sub1.aBoolean425 = false;
					Static441.method5568("maxbuildarea=" + TextureOpBrightness.aClass50_Sub1_1.anInt3434);
					return;
				}
				Static441.method5568("Invalid buildarea value");
				return;
			}
			if (arg0.startsWith("setparticles")) {
				if (arg0.length() < 13) {
					Static441.method5568("Invalid particles value");
					return;
				}
				HashTable.method2673(ParticleProducerType.method3113(arg0.substring(13)));
				TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
				RuntimeException_Sub1.aBoolean425 = false;
				Static441.method5568("particles=" + TextureType.method3279());
				return;
			}
			if (arg0.startsWith("rect_debug")) {
				if (arg0.length() < 10) {
					Static441.method5568("Invalid rect_debug value");
					return;
				}
				GlGround.lastSentMouseX = ParticleProducerType.method3113(arg0.substring(10).trim());
				Static441.method5568("rect_debug=" + GlGround.lastSentMouseX);
				return;
			}
			if (arg0.equalsIgnoreCase("qa_op_test")) {
				Class249.aBoolean626 = true;
				Static441.method5568("qa_op_test=" + Class249.aBoolean626);
				return;
			}
			if (arg0.equalsIgnoreCase("clipcomponents")) {
				Static449.aBoolean688 = !Static449.aBoolean688;
				Static441.method5568("clipcomponents=" + Static449.aBoolean688);
				return;
			}
			if (arg0.startsWith("bloom")) {
				@Pc(812) boolean local812 = MonoStereoData.aClass19_16.method4301();
				if (Class210.method4772(!local812)) {
					if (!local812) {
						Static441.method5568("Bloom enabled");
						return;
					}
					Static441.method5568("Bloom disabled");
					return;
				}
				Static441.method5568("Failed to enable bloom");
				return;
			}
			if (arg0.equalsIgnoreCase("tween")) {
				boolean tweening = Tweening.toggle();
				Tweening.save();
				Static441.method5568(tweening ? "Forced tweening ENABLED!" : "Forced tweening disabled.");
				return;
			}
			if (arg0.equalsIgnoreCase("shiftclick")) {
				if (!Country.aBoolean692) {
					Static441.method5568("Shift-click ENABLED!");
					Country.aBoolean692 = true;
					return;
				}
				Static441.method5568("Shift-click disabled.");
				Country.aBoolean692 = false;
				return;
			}
			if (arg0.equalsIgnoreCase("getcgcoord")) {
				Static441.method5568("x:" + (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7) + " z:" + (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7));
				return;
			}
			if (arg0.equalsIgnoreCase("getheight")) {
				Static441.method5568("Height: " + Class16_Sub1_Sub6.aClass65Array4[Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82].l(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7, Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7));
				return;
			}
			if (arg0.equalsIgnoreCase("resetminimap")) {
				GlGround.aClass76_60.method2102();
				GlGround.aClass76_60.method2118();
				Class4_Sub14.aClass265_2.method5587();
				Class4_Sub6_Sub31.aClass182_4.method4127();
				Static277.method3932();
				Static441.method5568("Minimap reset");
				return;
			}
			if (arg0.startsWith("mc")) {
				if (!MonoStereoData.aClass19_16.method4264()) {
					Static441.method5568("Current toolkit doesn't support multiple cores");
					return;
				}
				local106 = Integer.parseInt(arg0.substring(3));
				if (local106 < 1) {
					local106 = 1;
				} else if (local106 > 4) {
					local106 = 4;
				}
				TextureType.anInt4043 = local106;
				MonoStereoData.aClass19_16.allocateThreads(TextureType.anInt4043);
				MonoStereoData.aClass19_16.linkThreads(0);
				Static441.method5568("Render cores now: " + TextureType.anInt4043);
				return;
			}
			if (arg0.startsWith("cachespace")) {
				Static441.method5568("I(s): " + Class48_Sub1_Sub1.aClass68_26.method1781() + "/" + Class48_Sub1_Sub1.aClass68_26.method1785());
				Static441.method5568("I(m): " + Static232.aClass68_25.method1781() + "/" + Static232.aClass68_25.method1785());
				Static441.method5568("O(s): " + Terrain.aClass206_3.spriteCache.method4944() + "/" + Terrain.aClass206_3.spriteCache.method4954());
				return;
			}
			if (arg0.equalsIgnoreCase("getcamerapos")) {
				Static441.method5568("Pos: " + Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 + "," + (Class50_Sub1.anInt3453 + (Class4_Sub23.anInt3197 >> 7) >> 6) + "," + (OpenGLTextureCache.anInt1771 + (Static145.anInt2906 >> 7) >> 6) + "," + (Class50_Sub1.anInt3453 + (Class4_Sub23.anInt3197 >> 7) & 0x3F) + "," + ((Static145.anInt2906 >> 7) + OpenGLTextureCache.anInt1771 & 0x3F) + " Height: " + (BufferedSocket.method135(Class4_Sub23.anInt3197, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, Static145.anInt2906) - TextureOpIrregularBricks.anInt2024));
				Static441.method5568("Look: " + Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 + "," + (Class50_Sub1.anInt3453 + Class3_Sub3.anInt2379 >> 6) + "," + (StillWaterEffect.anInt6924 + OpenGLTextureCache.anInt1771 >> 6) + "," + (Class3_Sub3.anInt2379 + Class50_Sub1.anInt3453 & 0x3F) + "," + (OpenGLTextureCache.anInt1771 + StillWaterEffect.anInt6924 & 0x3F) + " Height: " + (BufferedSocket.method135(Class3_Sub3.anInt2379, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, StillWaterEffect.anInt6924) - CacheableNodeByteArray.anInt4509));
				return;
			}
			if (arg0.equals("showocc")) {
				MaterialType.aBoolean647 = !MaterialType.aBoolean647;
				PathingEntity.method4744();
				Static441.method5568("showocc=" + MaterialType.aBoolean647);
				return;
			}
			if (arg0.equals("wallocc")) {
				Class111.aBoolean282 = !Class111.aBoolean282;
				PathingEntity.method4744();
				Static441.method5568("forcewallocc=" + Class111.aBoolean282);
				return;
			}
			if (arg0.equals("renderprofile") || arg0.equals("rp")) {
				Class174.aBoolean477 = !Class174.aBoolean477;
				MonoStereoData.aClass19_16.method4255(Class174.aBoolean477);
				Static47.method768();
				Static441.method5568("showprofiling=" + Class174.aBoolean477);
				return;
			}
			if (arg0.equals("performancetest")) {
				Static441.method5568("Java toolkit: " + Class4_Sub43.method5410(Class126.aClass66_4));
				Static441.method5568("GL toolkit:   " + Class4_Sub43.method5410(Class126.aClass66_4));
				Static441.method5568("SSE toolkit:  " + Class4_Sub43.method5410(Class126.aClass66_4));
				return;
			}
			if (arg0.equals("nonpcs")) {
				Class48.aBoolean508 = !Class48.aBoolean508;
				Static441.method5568("nonpcs=" + Class48.aBoolean508);
				return;
			}
			if (arg0.equals("autoworld")) {
				TextureOpPerlinNoise.method2288();
				Static441.method5568("auto world selected");
				return;
			}
			if (arg0.startsWith("pc")) {
				PingWorker.method5476(GzipDecompressor.aClass215_69);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				local106 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				local568 = arg0.indexOf(" ", 4);
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0.substring(3, local568));
				Class16_Sub1_Sub6.method5359(arg0.substring(local568), ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local106);
				return;
			}
			if (arg0.equals("heap")) {
				Static441.method5568("Heap: " + BreakingWaterEffect.anInt1503 + "MB");
				return;
			}
			if (arg0.equals("savevarcs")) {
				Class190.method4216();
				Static441.method5568("perm varcs saved");
				return;
			}
			if (arg0.equals("scramblevarcs")) {
				for (local106 = 0; local106 < ParticleEffector.WALL_ROTATION_FLAGS.length; local106++) {
					if (Class16_Sub1_Sub1.aBooleanArray3[local106]) {
						ParticleEffector.WALL_ROTATION_FLAGS[local106] = (int) (Math.random() * 99999.0D);
						if (Math.random() > 0.5D) {
							ParticleEffector.WALL_ROTATION_FLAGS[local106] *= -1;
						}
					}
				}
				Class190.method4216();
				Static441.method5568("perm varcs scrambled");
				return;
			}
			if (arg0.equals("showcolmap")) {
				GlFramebuffer.aBoolean424 = true;
				Static277.method3932();
				Static441.method5568("colmap is shown");
				return;
			}
			if (arg0.equals("hidecolmap")) {
				GlFramebuffer.aBoolean424 = false;
				Static277.method3932();
				Static441.method5568("colmap is hidden");
				return;
			}
			if (arg0.equals("resetcache")) {
				Class244.method5485();
				Static441.method5568("Caches reset");
				return;
			}
			if (arg0.equals("profilecpu")) {
				Static441.method5568(FluType.method4958() + "ms");
				return;
			}
			if (arg0.startsWith("cpuusage")) {
				local106 = Integer.parseInt(arg0.substring(9));
				if (local106 >= 0 && local106 <= 4) {
					TextureOpBrightness.aClass50_Sub1_1.anInt3437 = local106;
				}
				Static441.method5568("cpuusage=" + TextureOpBrightness.aClass50_Sub1_1.anInt3437);
				return;
			}
			if (arg0.startsWith("maxfps")) {
				if (arg0.length() > 6) {
					FrameRateLimit.setConfiguredFps(ParticleProducerType.method3113(arg0.substring(6).trim()));
				}
				Static441.method5568("maxfps=" + FrameRateLimit.getConfiguredFps() + " (0 = uncapped)");
				return;
			}
			if (arg0.startsWith("getclientvarpbit")) {
				local106 = Integer.parseInt(arg0.substring(17));
				Static441.method5568("varpbit=" + Class4_Sub6_Sub24.aClass114_1.getVarBitValue(local106));
				return;
			}
			if (arg0.startsWith("getclientvarp")) {
				local106 = Integer.parseInt(arg0.substring(14));
				Static441.method5568("varp=" + Class4_Sub6_Sub24.aClass114_1.getVarValueInt(local106));
				return;
			}
			if (arg0.startsWith("csprofileclear")) {
				Static271.profileClear();
				return;
			}
			if (arg0.startsWith("csprofileoutputc")) {
				Static271.profileOutput();
				return;
			}
			if (arg0.startsWith("csprofileoutputt")) {
				Static271.profileOutput();
				return;
			}
			if (arg0.startsWith("texsize")) {
				local106 = Integer.parseInt(arg0.substring(8));
				MonoStereoData.aClass19_16.setTextureSize(local106);
				return;
			}
			if (arg0.equals("soundstreamcount")) {
				Static441.method5568("Active streams: " + GameCanvas.aClass4_Sub15_Sub2_2.count());
				return;
			}
			if (VertexDeclarationComponent.anInt6667 == 30) {
				PingWorker.method5476(StructTypeList.aClass215_26);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(arg0.length() + 2);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(arg1 ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
			}
			if (arg0.startsWith("fps ") && Class222.aClass219_6 != GlToolkit.aClass219_2) {
				BufferedSocket.method136(ParticleProducerType.method3113(arg0.substring(4)));
				return;
			}
			if (VertexDeclarationComponent.anInt6667 != 30) {
				Static441.method5568("Unrecogonised commmand when not logged in: " + arg0);
			}
		} catch (@Pc(1681) Exception local1681) {
			Static441.method5568("Whoops, something went wrong.");
		}
	}
@OriginalMember(owner = "client!ue", name = "d", descriptor = "(I)Z")
	public static boolean method5187() {
		if (Class70_Sub2.aBoolean591) {
			try {
				Static458.method2593(Class126.aClass66_4.anApplet1, "showVideoAd");
				return true;
			} catch (@Pc(19) Throwable local19) {
			}
		}
		return false;
	}
@OriginalMember(owner = "client!ue", name = "a", descriptor = "(IIII)I")
	public static int method5188(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(7) int local7 = arg0 & 0x3;
		if (local7 == 0) {
			return arg2;
		} else if (local7 == 1) {
			return arg1;
		} else if (local7 == 2) {
			return 1023 - arg2;
		} else {
			return 1023 - arg1;
		}
	}
@OriginalMember(owner = "client!ue", name = "b", descriptor = "(IIII)V")
	public static void method5189(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg0, 11);
		local13.method2604();
		local13.anInt3082 = arg2;
		local13.anInt3083 = arg1;
	}
}
