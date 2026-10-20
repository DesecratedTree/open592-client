package com.jagex.game.runetek5.config.mec;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.deob.Static391;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.config.mec.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class85;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
@OriginalClass("client!pj")
public final class MECTypeList {

	@OriginalMember(owner = "client!pj", name = "f", descriptor = "Lclient!fa;")
	private LRUCache definitionCache = new LRUCache(128);

	@OriginalMember(owner = "client!pj", name = "l", descriptor = "Lclient!fa;")
	public LRUCache spriteCache = new LRUCache(64);

	@OriginalMember(owner = "client!pj", name = "d", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider definitionResource;

	@OriginalMember(owner = "client!pj", name = "b", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider aClass76_63;

	@OriginalMember(owner = "client!pj", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;Lclient!fs;)V")
	public MECTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) Js5ResourceProvider definitionResource, @OriginalArg(3) Js5ResourceProvider spriteResource) {
		this.definitionResource = definitionResource;
		this.aClass76_63 = spriteResource;
		this.definitionResource.method2108(36);
	}

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "(IB)Lclient!nf;")
	public MECType method4124(@OriginalArg(0) int arg0) {
		@Pc(11) LRUCache local11 = this.definitionCache;
		@Pc(21) MECType local21;
		synchronized (this.definitionCache) {
			local21 = (MECType) this.definitionCache.method1787((long) arg0);
		}
		if (local21 != null) {
			return local21;
		}
		@Pc(40) byte[] local40 = this.definitionResource.method2104(arg0, 36);
		local21 = new MECType();
		local21.anInt4914 = arg0;
		local21.initialType = this;
		if (local40 != null) {
			local21.method3784(new Packet(local40));
		}
		local21.method3781();
		@Pc(67) LRUCache local67 = this.definitionCache;
		synchronized (this.definitionCache) {
			this.definitionCache.method1779((long) arg0, local21);
			return local21;
		}
	}

	@OriginalMember(owner = "client!pj", name = "b", descriptor = "(I)V")
	public void method4127() {
		@Pc(2) LRUCache local2 = this.definitionCache;
		synchronized (this.definitionCache) {
			this.definitionCache.method1777();
		}
		local2 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method1777();
		}
	}

	@OriginalMember(owner = "client!pj", name = "b", descriptor = "(IB)V")
	public void method4128() {
		@Pc(6) LRUCache local6 = this.definitionCache;
		synchronized (this.definitionCache) {
			this.definitionCache.method1788(5);
		}
		local6 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method1788(5);
		}
	}

	@OriginalMember(owner = "client!pj", name = "a", descriptor = "(IZI)V")
	public void method4131(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		this.definitionCache = new LRUCache(arg1);
		this.spriteCache = new LRUCache(arg0);
	}

	@OriginalMember(owner = "client!pj", name = "c", descriptor = "(I)V")
	public void method4132() {
		@Pc(2) LRUCache local2 = this.definitionCache;
		synchronized (this.definitionCache) {
			this.definitionCache.method1791();
		}
		local2 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method1791();
		}
	}

@OriginalMember(owner = "client!pj", name = "n", descriptor = "F")
	public static float aFloat169;
@OriginalMember(owner = "client!pj", name = "j", descriptor = "I")
	public static int anInt5385 = 0;
@OriginalMember(owner = "client!pj", name = "m", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_249 = new Class22(105, -2);
@OriginalMember(owner = "client!pj", name = "o", descriptor = "I")
	public static int anInt5387 = -1;
@OriginalMember(owner = "client!pj", name = "a", descriptor = "(I)V")
	public static void method4123() {
		Static57.aClass19_7.B(((float) TextureOpBrightness.aClass50_Sub1_1.anInt3436 * 0.1F + 0.7F) * 1.1523438F);
		Static57.aClass19_7.scaleHeight(Texture3DShaderGL.anInt1369, 0.69921875F, 1.2F, -50.0F, -60.0F, -50.0F);
		Static57.aClass19_7.UA(FixedFunctionSpecularPass.anInt5521, -1, 256);
		Static57.aClass19_7.setSunCubeTexture(RequestManager.aClass99_5);
	}
@OriginalMember(owner = "client!pj", name = "a", descriptor = "(I[BZIII)V")
	public static void method4125(@OriginalArg(1) byte[] arg0, @OriginalArg(3) int arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3) {
		if (arg2 >= arg1) {
			return;
		}
		arg3 += arg2;
		@Pc(23) int local23 = arg1 - arg2 >> 2;
		while (true) {
			local23--;
			if (local23 < 0) {
				local23 = arg1 - arg2 & 0x3;
				while (true) {
					local23--;
					if (local23 < 0) {
						return;
					}
					arg0[arg3++] = 1;
				}
			}
			@Pc(28) int local28 = arg3 + 1;
			arg0[arg3] = 1;
			@Pc(33) int local33 = local28 + 1;
			arg0[local28] = 1;
			@Pc(38) int local38 = local33 + 1;
			arg0[local33] = 1;
			arg3 = local38 + 1;
			arg0[local38] = 1;
		}
	}
@OriginalMember(owner = "client!pj", name = "a", descriptor = "(Lclient!ya;I)V")
	public static void method4129(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(7) int local7 = Class4_Sub37.anInt5424;
		@Pc(9) int local9 = Class111.anInt3367;
		@Pc(11) int local11 = Static173.anInt3370;
		@Pc(15) int local15 = Class4_Sub18.anInt2392 - 3;
		if (QuickchatMessageType.aClass57_4 == null || PointLight.aClass57_17 == null) {
			if (GlGround.aClass76_60.method2125(FloTypeList.anInt3850) && GlGround.aClass76_60.method2125(StillWaterEffect.anInt6931)) {
				QuickchatMessageType.aClass57_4 = arg0.generateSprite(SpriteType.listSingle(GlGround.aClass76_60, FloTypeList.anInt3850, 0));
				@Pc(63) SpriteType local63 = SpriteType.listSingle(GlGround.aClass76_60, StillWaterEffect.anInt6931, 0);
				PointLight.aClass57_17 = arg0.generateSprite(local63);
				local63.flipHorizontal();
				Class177.aClass57_20 = arg0.generateSprite(local63);
			} else {
				arg0.O(local7, local9, local11, 20, LocalRequester.anInt7011 | 255 - TextureOpTile.anInt6373 << 24, 1);
			}
		}
		@Pc(92) int local92;
		@Pc(90) int local90;
		if (QuickchatMessageType.aClass57_4 != null && PointLight.aClass57_17 != null) {
			local90 = (local11 - PointLight.aClass57_17.getWidth() * 2) / QuickchatMessageType.aClass57_4.getWidth();
			for (local92 = 0; local92 < local90; local92++) {
				QuickchatMessageType.aClass57_4.render(local7 + PointLight.aClass57_17.getWidth() + QuickchatMessageType.aClass57_4.getWidth() * local92, local9);
			}
			PointLight.aClass57_17.render(local7, local9);
			Class177.aClass57_20.render(local7 + local11 - Class177.aClass57_20.getWidth(), local9);
		}
		Static331.aClass46_10.method5058(-1, Class3_Sub3.anInt2387 | -16777216, Static391.aClass83_133.method2267(NativeBillboardFace.anInt3423), local9 + 14, local7 + 3);
		arg0.O(local7, local9 + 20, local11, local15 - 20, LocalRequester.anInt7011 | 255 - TextureOpTile.anInt6373 << 24, 1);
		local90 = MaterialType.aClass80_1.method2234();
		local92 = MaterialType.aClass80_1.getDefaultValue();
		@Pc(172) int local172 = 0;
		@Pc(196) int local196;
		for (@Pc(179) OggOtherStream local179 = (OggOtherStream) Class126.aClass183_28.method4140(); local179 != null; local179 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			local196 = (Skybox.anInt6710 - local172 - 1) * 16 + local9 + 13 + 20;
			local172++;
			if (local7 < local90 && local7 + local11 > local90 && local196 - 13 < local92 && local92 < local196 + 4 && local179.aBoolean512) {
				arg0.O(local7, local196 - 12, local11, 16, 255 - EntityNodeList.anInt2738 << 24 | QuickChatValueType.anInt3062, 1);
			}
		}
		if ((Class4_Sub6_Sub24.aClass57_19 == null || ModeWhere.aClass57_21 == null || Class93.aClass57_15 == null) && GlGround.aClass76_60.method2125(QuickChatCategoryType.anInt5073) && GlGround.aClass76_60.method2125(Class4_Sub6_Sub15.anInt3601) && GlGround.aClass76_60.method2125(Terrain.anInt7297)) {
			@Pc(279) SpriteType local279 = SpriteType.listSingle(GlGround.aClass76_60, Class4_Sub6_Sub15.anInt3601, 0);
			ModeWhere.aClass57_21 = arg0.generateSprite(local279);
			local279.flipHorizontal();
			MidiPcmStream.aClass57_9 = arg0.generateSprite(local279);
			Class4_Sub6_Sub24.aClass57_19 = arg0.generateSprite(SpriteType.listSingle(GlGround.aClass76_60, QuickChatCategoryType.anInt5073, 0));
			@Pc(304) SpriteType local304 = SpriteType.listSingle(GlGround.aClass76_60, Terrain.anInt7297, 0);
			Class93.aClass57_15 = arg0.generateSprite(local304);
			local304.flipHorizontal();
			Class85.aClass57_12 = arg0.generateSprite(local304);
		}
		@Pc(372) int local372;
		@Pc(335) int local335;
		if (Class4_Sub6_Sub24.aClass57_19 != null && ModeWhere.aClass57_21 != null && Class93.aClass57_15 != null) {
			local196 = (local11 - Class93.aClass57_15.getWidth() * 2) / Class4_Sub6_Sub24.aClass57_19.getWidth();
			for (local335 = 0; local335 < local196; local335++) {
				Class4_Sub6_Sub24.aClass57_19.render(Class93.aClass57_15.getWidth() + local7 + local335 * Class4_Sub6_Sub24.aClass57_19.getWidth(), local15 + local9 + -Class4_Sub6_Sub24.aClass57_19.getHeight());
			}
			local372 = (local15 - Class93.aClass57_15.getHeight() - 20) / ModeWhere.aClass57_21.getHeight();
			for (@Pc(374) int local374 = 0; local374 < local372; local374++) {
				ModeWhere.aClass57_21.render(local7, local9 + ModeWhere.aClass57_21.getHeight() * local374 + 20);
				MidiPcmStream.aClass57_9.render(local11 + local7 - MidiPcmStream.aClass57_9.getWidth(), local9 - (-(ModeWhere.aClass57_21.getHeight() * local374) + -20));
			}
			Class93.aClass57_15.render(local7, local15 + local9 - Class93.aClass57_15.getHeight());
			Class85.aClass57_12.render(local11 + local7 - Class93.aClass57_15.getWidth(), local9 + (local15 - Class93.aClass57_15.getHeight()));
		}
		local172 = 0;
		for (@Pc(444) OggOtherStream local444 = (OggOtherStream) Class126.aClass183_28.method4140(); local444 != null; local444 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			local335 = local9 + (Skybox.anInt6710 + -1 + -local172) * 16 + 20 + 13;
			local372 = Class3_Sub3.anInt2387 | 0xFF000000;
			if (local7 < local90 && local11 + local7 > local90 && local92 > local335 - 13 && local335 + 4 > local92 && local444.aBoolean512) {
				local372 = SoundBankCache.anInt5199 | 0xFF000000;
			}
			@Pc(500) int[] local500 = null;
			if (Static441.method5569(local444.anInt5699)) {
				local500 = Terrain.aClass206_3.method4703((int) local444.aLong192).quests;
			} else if (local444.anInt5695 != -1) {
				local500 = Terrain.aClass206_3.method4703(local444.anInt5695).quests;
			} else if (TextureOpBrightness.method4382(local444.anInt5699)) {
				@Pc(578) NPCEntity local578 = Class93.aClass16_Sub1_Sub5_Sub2Array1[(int) local444.aLong192];
				if (local578 != null) {
					@Pc(583) NPCType local583 = local578.npcType;
					if (local583.anIntArray544 != null) {
						local583 = local583.method5574(Class4_Sub6_Sub24.aClass114_1);
					}
					if (local583 != null) {
						local500 = local583.anIntArray547;
					}
				}
			} else if (StructTypeList.method1982(local444.anInt5699)) {
				@Pc(550) LocType local550;
				if (local444.anInt5699 == 1010) {
					local550 = Static267.aClass262_2.method5560((int) local444.aLong192);
				} else {
					local550 = Static267.aClass262_2.method5560((int) (local444.aLong192 >>> 32 & 0x7FFFFFFFL));
				}
				if (local550.alternativeIds != null) {
					local550 = local550.method4584(Class4_Sub6_Sub24.aClass114_1);
				}
				if (local550 != null) {
					local500 = local550.quests;
				}
			}
			@Pc(608) String local608 = Class4_Sub21_Sub2.method3166(local444);
			if (local500 != null) {
				local608 = local608 + Class259.method5504(local500);
			}
			Static331.aClass46_10.method5057(Class27_Sub4.anIntArray338, Class70_Sub2.aClass57Array16, local372, local608, local7 + 3, local335);
			local172++;
			if (local444.aBoolean511) {
				TextureOpVoronoiNoise.aClass57_22.render(Js5ResourceProvider.aClass89_5.getWidthNoSprites(local608) + local7 + 5, local335 + -12);
			}
		}
		VertexDeclarationComponent.method5216(Static173.anInt3370, Class4_Sub18.anInt2392, Class111.anInt3367, Class4_Sub37.anInt5424);
	}
@OriginalMember(owner = "client!pj", name = "a", descriptor = "(Ljava/lang/String;I)V")
	public static void method4130(@OriginalArg(0) String arg0) {
		if (!AbstractArbBufferGL.aBoolean599) {
			return;
		}
		@Pc(10) boolean local10 = false;
		@Pc(17) int local17 = Class4_Sub7.anInt606;
		@Pc(19) int[] local19 = Static99.anIntArray157;
		for (@Pc(21) int local21 = 0; local21 < local17; local21++) {
			@Pc(29) PlayerEntity local29 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local19[local21]];
			if (local29 != null && Static1.aClass16_Sub1_Sub5_Sub1_1 != local29 && local29.aString44 != null && local29.aString44.equalsIgnoreCase(arg0)) {
				PingWorker.method5476(JavaFallbackSurface.aClass215_80);
				ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local19[local21]);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(ChatFilter.anInt3179);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(StackFrame.anInt350);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(0);
				Static166.method2734(0, local29.movementBaseY[0], local29.movementBaseX[0], local29.getSize(), true, local29.getSize(), 0, -2);
				local10 = true;
				break;
			}
		}
		if (!local10) {
			Class249.method5323(Class4_Sub14.aClass83_32.method2267(NativeBillboardFace.anInt3423) + arg0);
		}
		if (AbstractArbBufferGL.aBoolean599) {
			Class16_Sub5.method5139();
		}
	}
}
