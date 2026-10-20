package com.jagex.game.runetek5.graphics.gl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.GlRenderTarget;
import com.jagex.game.runetek5.cache.node.lru.OpenGlRenderBuffer;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.toolkit.companion.Binding;

import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static14;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.graphics.gl.*;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.deob.Class135;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!ml")
public final class GlFramebuffer implements GlRenderTarget {

	@OriginalMember(owner = "client!ml", name = "b", descriptor = "I")
	private int anInt4591;

	@OriginalMember(owner = "client!ml", name = "v", descriptor = "I")
	private int anInt4610;

	@OriginalMember(owner = "client!ml", name = "x", descriptor = "I")
	private int anInt4612;

	@OriginalMember(owner = "client!ml", name = "j", descriptor = "I")
	private int anInt4598 = 0;

	@OriginalMember(owner = "client!ml", name = "u", descriptor = "I")
	private int anInt4609 = -1;

	@OriginalMember(owner = "client!ml", name = "H", descriptor = "[Lclient!pa;")
	private final Binding[] attachments = new Binding[9];

	@OriginalMember(owner = "client!ml", name = "B", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!ml", name = "o", descriptor = "I")
	private final int anInt4603;

	@OriginalMember(owner = "client!ml", name = "<init>", descriptor = "(Lclient!fd;)V")
	public GlFramebuffer(@OriginalArg(0) GlToolkit glToolkit) {
		if (!glToolkit.extFramebufferObject) {
			throw new IllegalStateException("");
		}
		this.glToolkit = glToolkit;
		OpenGL.glGenFramebuffersEXT(1, KeyRecordEntry.anIntArray29, 0);
		this.anInt4603 = KeyRecordEntry.anIntArray29[0];
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(II)V")
	public void method3610(@OriginalArg(1) int arg0) {
		if (this.anInt4609 == -1) {
			throw new RuntimeException();
		}
		OpenGL.glDrawBuffer(PcmPlayerThread.anIntArray496[arg0]);
	}

	@OriginalMember(owner = "client!ml", name = "b", descriptor = "(II)V")
	public void method3611(@OriginalArg(0) int arg0) {
		if (this.attachments[arg0] != null) {
			this.attachments[arg0].detach();
		}
		this.anInt4612 &= ~(0x1 << arg0);
		this.attachments[arg0] = null;
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(IILclient!af;I)V")
	private void method3612(@OriginalArg(0) int arg0, @OriginalArg(2) OpenGl2DTexture arg1) {
		if (this.anInt4609 == -1) {
			throw new RuntimeException();
		}
		@Pc(15) int local15 = 0x1 << arg0;
		if ((this.anInt4612 & ~local15) == 0) {
			this.anInt4610 = arg1.anInt5458;
			this.anInt4591 = arg1.anInt5466;
		} else if (this.anInt4591 != arg1.anInt5466 || this.anInt4610 != arg1.anInt5458) {
			throw new RuntimeException();
		}
		arg1.method4195(PcmPlayerThread.anIntArray496[arg0], this.anInt4609);
		this.attachments[arg0] = arg1;
		this.anInt4612 |= local15;
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(IIILclient!im;I)V")
	private void method3613(@OriginalArg(3) GlCubeTexture arg0, @OriginalArg(4) int arg1) {
		if (this.anInt4609 == -1) {
			throw new RuntimeException();
		}
		if ((this.anInt4612 & 0xFFFFFFFE) == 0) {
			this.anInt4591 = arg0.size;
			this.anInt4610 = arg0.size;
		} else if (this.anInt4591 != arg0.size || this.anInt4610 != arg0.size) {
			throw new RuntimeException();
		}
		arg0.method2754(this.anInt4609, arg1, PcmPlayerThread.anIntArray496[0]);
		this.attachments[0] = arg0;
		this.anInt4612 |= 0x1;
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(I)V")
	@Override
	public void method3603() {
		OpenGL.glBindFramebufferEXT(36160, 0);
		this.anInt4598 &= 0xFFFFFFFB;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(Lclient!io;IB)V")
	public void method3614(@OriginalArg(0) OpenGlRenderBuffer arg0, @OriginalArg(1) int arg1) {
		if (this.anInt4609 == -1) {
			throw new RuntimeException();
		}
		@Pc(23) int local23 = 0x1 << arg1;
		if ((this.anInt4612 & ~local23) == 0) {
			this.anInt4591 = arg0.width;
			this.anInt4610 = arg0.height;
		} else if (this.anInt4591 != arg0.width || this.anInt4610 != arg0.height) {
			throw new RuntimeException();
		}
		arg0.attach(PcmPlayerThread.anIntArray496[arg1], this.anInt4609);
		this.attachments[arg1] = arg0;
		this.anInt4612 |= local23;
	}

	@OriginalMember(owner = "client!ml", name = "d", descriptor = "(B)I")
	public int method3615() {
		return this.anInt4610;
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(B)V")
	@Override
	public void method3602() {
		OpenGL.glBindFramebufferEXT(36160, this.anInt4603);
		this.anInt4598 |= 0x4;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() throws Throwable {
		this.glToolkit.method1941(this.anInt4603);
		super.finalize();
	}

	@OriginalMember(owner = "client!ml", name = "f", descriptor = "(I)Z")
	public boolean method3616() {
		@Pc(7) int local7 = OpenGL.glCheckFramebufferStatusEXT(this.anInt4609);
		return local7 == 36053;
	}

	@OriginalMember(owner = "client!ml", name = "b", descriptor = "(B)V")
	@Override
	public void method3607() {
		OpenGL.glBindFramebufferEXT(36008, this.anInt4603);
		this.anInt4598 |= 0x1;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "d", descriptor = "(I)V")
	@Override
	public void method3606() {
		OpenGL.glBindFramebufferEXT(36008, 0);
		this.anInt4598 &= 0xFFFFFFFE;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(IB)V")
	public void method3618() {
		if (this.anInt4609 == -1) {
			throw new RuntimeException();
		}
		OpenGL.glReadBuffer(PcmPlayerThread.anIntArray496[0]);
	}

	@OriginalMember(owner = "client!ml", name = "c", descriptor = "(I)V")
	@Override
	public void method3605() {
		OpenGL.glBindFramebufferEXT(36009, this.anInt4603);
		this.anInt4598 |= 0x2;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(IILclient!im;I)V")
	public void method3619(@OriginalArg(2) GlCubeTexture arg0, @OriginalArg(3) int arg1) {
		this.method3613(arg0, arg1);
	}

	@OriginalMember(owner = "client!ml", name = "g", descriptor = "(I)I")
	private int method3620() {
		if ((this.anInt4598 & 0x4) != 0) {
			return 36160;
		} else if ((this.anInt4598 & 0x2) == 0) {
			return (this.anInt4598 & 0x1) == 0 ? -1 : 36008;
		} else {
			return 36009;
		}
	}

	@OriginalMember(owner = "client!ml", name = "b", descriptor = "(I)V")
	@Override
	public void method3604() {
		OpenGL.glBindFramebufferEXT(36009, 0);
		this.anInt4598 &= 0xFFFFFFFD;
		this.anInt4609 = this.method3620();
	}

	@OriginalMember(owner = "client!ml", name = "a", descriptor = "(BLclient!af;I)V")
	public void method3621(@OriginalArg(1) OpenGl2DTexture arg0, @OriginalArg(2) int arg1) {
		this.method3612(arg1, arg0);
	}

@OriginalMember(owner = "client!ml", name = "i", descriptor = "[[B")
	public static byte[][] aByteArrayArray16;
@OriginalMember(owner = "client!ml", name = "I", descriptor = "Lclient!c;")
	public static Ground aClass31_2;
@OriginalMember(owner = "client!ml", name = "a", descriptor = "I")
	public static int anInt4590 = 10;
@OriginalMember(owner = "client!ml", name = "y", descriptor = "Z")
	public static boolean aBoolean424 = false;
@OriginalMember(owner = "client!ml", name = "C", descriptor = "I")
	public static int totalTransitionDuration = -1;
@OriginalMember(owner = "client!ml", name = "D", descriptor = "Lclient!sl;")
	private static final Class215 aClass215_51 = new Class215(26, 7);
@OriginalMember(owner = "client!ml", name = "G", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_52 = new Class215(68, 3);
@OriginalMember(owner = "client!ml", name = "e", descriptor = "(I)V")
	public static void method3609() {
		SceneLocEntity.aClass68_19.method1777();
	}
@OriginalMember(owner = "client!ml", name = "a", descriptor = "(ILclient!qm;IZ)V")
	public static void method3617(@OriginalArg(0) int arg0, @OriginalArg(1) OggOtherStream arg1, @OriginalArg(2) int arg2) {
		if (arg1 == null || arg1 == Class126.aClass183_28.aClass4_207) {
			return;
		}
		@Pc(15) int local15 = arg1.anInt5696;
		@Pc(18) int local18 = arg1.anInt5697;
		@Pc(21) int local21 = arg1.anInt5699;
		@Pc(25) int local25 = (int) arg1.aLong192;
		if (local21 >= 2000) {
			local21 -= 2000;
		}
		@Pc(32) long local32 = arg1.aLong192;
		if (local21 == 1004 || local21 == 1012 || local21 == 1008 || local21 == 1001 || local21 == 1006) {
			Class4_Sub6_Sub33.method5043(local21, local15, local25);
		}
		if (local21 == 45) {
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpColorGradient.anInt1764 = 2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(Class186.aClass215_68);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(Integer.MAX_VALUE & (int) (local32 >>> 32));
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		@Pc(120) PlayerEntity local120;
		if (local21 == 10) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(TextureOpDiagonalWaves.aClass215_43);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		@Pc(180) NPCEntity local180;
		if (local21 == 15) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				Static14.anInt191 = arg2;
				PingWorker.method5476(Class78_Sub1.aClass215_40);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (local21 == 46) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(JavaFallbackSurface.aClass215_80);
				ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(ChatFilter.anInt3179);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(StackFrame.anInt350);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 6) {
			ShadowTileGL.anInt3911 = arg0;
			TextureOpColorGradient.anInt1764 = 2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			PingWorker.method5476(OggOtherStream.aClass215_77);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(ChatFilter.anInt3179);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(StackFrame.anInt350);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 59) {
			if (GlToolkit.activeChildCount > 0 && Static384.aClass244_1.method5489(82) && Static384.aClass244_1.method5489(81)) {
				Class4_Sub21_Sub2.method3168(Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, OpenGLTextureCache.anInt1771 + local18, local15 + Class50_Sub1.anInt3453);
			} else {
				ShadowTileGL.anInt3911 = arg0;
				TextureOpColorGradient.anInt1764 = 1;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(Static173.aClass215_38);
				ParticleNode.aClass4_Sub12_Sub1_5.method2505(local15 + Class50_Sub1.anInt3453);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local18 + OpenGLTextureCache.anInt1771);
			}
		}
		if (local21 == 58) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				ShadowTileGL.anInt3911 = arg0;
				Static14.anInt191 = arg2;
				TextureOpColorGradient.anInt1764 = 2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(Class3_Sub3.aClass215_28);
				ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 8) {
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			TextureOpColorGradient.anInt1764 = 2;
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			PingWorker.method5476(JavaFallbackSurface.aClass215_80);
			ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6037);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(ChatFilter.anInt3179);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(StackFrame.anInt350);
			ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
		}
		if (local21 == 21) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				ShadowTileGL.anInt3911 = arg0;
				TextureOpColorGradient.anInt1764 = 2;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(AnimBase.aClass215_61);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2494(local25);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (local21 == 23) {
			TextureOpColorGradient.anInt1764 = 2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			PingWorker.method5476(Toolkit.aClass215_73);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(Class50_Sub1.anInt3453 + local15);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(Integer.MAX_VALUE & (int) (local32 >>> 32));
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		if (local21 == 25) {
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			ShadowTileGL.anInt3911 = arg0;
			Static14.anInt191 = arg2;
			TextureOpColorGradient.anInt1764 = 2;
			PingWorker.method5476(Static331.aClass215_78);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local18 + OpenGLTextureCache.anInt1771);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505((int) (local32 >>> 32) & Integer.MAX_VALUE);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(Class50_Sub1.anInt3453 + local15);
			ParticleNode.aClass4_Sub12_Sub1_5.method2533(Static384.aClass244_1.method5489(82) ? 1 : 0);
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		if (local21 == 49) {
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			TextureOpColorGradient.anInt1764 = 2;
			Static14.anInt191 = arg2;
			PingWorker.method5476(Toolkit.aClass215_72);
			ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(Class50_Sub1.anInt3453 + local15);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 1007) {
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			TextureOpColorGradient.anInt1764 = 2;
			PingWorker.method5476(GLBillowTextureData.aClass215_75);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(local18 + OpenGLTextureCache.anInt1771);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(Integer.MAX_VALUE & (int) (local32 >>> 32));
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local15 + Class50_Sub1.anInt3453);
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		if (local21 == 16) {
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			TextureOpColorGradient.anInt1764 = 2;
			PingWorker.method5476(Class64_Sub1.aClass215_21);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2533(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(Class50_Sub1.anInt3453 + local15);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local18 + OpenGLTextureCache.anInt1771);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 60 && Class180.aClass247_16 == null) {
			DequeIterator.method1617(local18, local15);
			Class180.aClass247_16 = EnvironmentSampler.method3230(local18, local15);
			Class4_Sub12_Sub1.method1142(Class180.aClass247_16);
		}
		@Pc(895) com.jagex.game.runetek5.config.rsinterface.Component local895;
		if (local21 == 9) {
			local895 = EnvironmentSampler.method3230(local18, local15);
			if (local895 != null) {
				Class16_Sub5.method5139();
				@Pc(902) GlRectangleTexture local902 = client.method900(local895);
				SoundBankCache.method3975(local902.anInt5110, local902.method3911(), local895);
				Class230.aString62 = Class196.method4431(local895);
				if (Class230.aString62 == null) {
					Class230.aString62 = "Null";
				}
				Class4_Sub10.aString28 = local895.aString66 + "<col=ffffff>";
			}
			return;
		}
		if (local21 == 12) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				ShadowTileGL.anInt3911 = arg0;
				TextureOpColorGradient.anInt1764 = 2;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(GlShadow.aClass215_34);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 57) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				ShadowTileGL.anInt3911 = arg0;
				TextureOpColorGradient.anInt1764 = 2;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(MonoStereoData.aClass215_32);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 30) {
			TextureOpColorGradient.anInt1764 = 2;
			ShadowTileGL.anInt3911 = arg0;
			Static14.anInt191 = arg2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(DummyInputStream.aClass215_53);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(StackFrame.anInt350);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(ChatFilter.anInt3179);
			ParticleNode.aClass4_Sub12_Sub1_5.method2533(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(Integer.MAX_VALUE & (int) (local32 >>> 32));
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		if (local21 == 19) {
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpColorGradient.anInt1764 = 1;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(PcmPlayer.aClass215_29);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2513(Class243.anInt6726);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(ChatFilter.anInt3179);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(StackFrame.anInt350);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(OpenGLTextureCache.anInt1771 + local18);
			Static166.method2734(0, local18, local15, 1, true, 1, 0, -4);
		}
		if (local21 == 47) {
			local895 = EnvironmentSampler.method3230(local18, local15);
			if (local895 != null) {
				Class6.method4182(local895);
			}
		}
		if (local21 == 50) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				Static14.anInt191 = arg2;
				ShadowTileGL.anInt3911 = arg0;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				TextureOpColorGradient.anInt1764 = 2;
				PingWorker.method5476(RequestManager.aClass215_60);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (local21 == 5) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				Static14.anInt191 = arg2;
				PingWorker.method5476(Class56.aClass215_18);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 22) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				Static14.anInt191 = arg2;
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				PingWorker.method5476(Class4_Sub6_Sub24.aClass215_58);
				ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(ChatFilter.anInt3179);
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class243.anInt6726);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(StackFrame.anInt350);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (local21 == 48 || local21 == 1002) {
			Component.method5309(local25, arg1.aString56, local18, local15);
		}
		if (local21 == 2) {
			Static14.anInt191 = arg2;
			TextureOpColorGradient.anInt1764 = 2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(aClass215_51);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local18 + OpenGLTextureCache.anInt1771);
			ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 18) {
			ShadowTileGL.anInt3911 = arg0;
			TextureOpColorGradient.anInt1764 = 2;
			Static14.anInt191 = arg2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(TextureOpIrregularBricks.aClass215_22);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524((int) (local32 >>> 32) & Integer.MAX_VALUE);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local18 + OpenGLTextureCache.anInt1771);
			MapSpriteTypeList.method5589(local32, local15, local18);
		}
		if (local21 == 3) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				Static14.anInt191 = arg2;
				ShadowTileGL.anInt3911 = arg0;
				TextureOpColorGradient.anInt1764 = 2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(MusicPatchInfo.aClass215_93);
				ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (local21 == 17) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				Static14.anInt191 = arg2;
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				PingWorker.method5476(aClass215_52);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 1003) {
			Static14.anInt191 = arg2;
			TextureOpColorGradient.anInt1764 = 2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				@Pc(1653) NPCType local1653 = local180.npcType;
				if (local1653.anIntArray544 != null) {
					local1653 = local1653.method5574(Class4_Sub6_Sub24.aClass114_1);
				}
				if (local1653 != null) {
					PingWorker.method5476(TextureOpBrightness.aClass215_74);
					ParticleNode.aClass4_Sub12_Sub1_5.method2524(local1653.id);
				}
			}
		}
		if (local21 == 13) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				TextureOpColorGradient.anInt1764 = 2;
				ShadowTileGL.anInt3911 = arg0;
				PingWorker.method5476(Class27_Sub4.aClass215_63);
				ParticleNode.aClass4_Sub12_Sub1_5.method2505(local25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 44) {
			if (GlToolkit.activeChildCount > 0 && Static384.aClass244_1.method5489(82) && Static384.aClass244_1.method5489(81)) {
				Class4_Sub21_Sub2.method3168(Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local18 + OpenGLTextureCache.anInt1771, local15 + Class50_Sub1.anInt3453);
			} else {
				StillWaterEffect.method5383(local15, local25, local18);
				if (local25 == 1) {
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(-1);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(-1);
					ParticleNode.aClass4_Sub12_Sub1_5.method2524((int) Static368.aFloat192);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(57);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(PlayerVarDomain.anInt3399);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub17.anInt2374);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(89);
					ParticleNode.aClass4_Sub12_Sub1_5.method2524(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893);
					ParticleNode.aClass4_Sub12_Sub1_5.method2524(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(63);
				} else {
					TextureOpColorGradient.anInt1764 = 1;
					TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
					ShadowTileGL.anInt3911 = arg0;
					Static14.anInt191 = arg2;
				}
				Static166.method2734(0, local18, local15, 1, true, 1, 0, -4);
			}
		}
		if (local21 == 51) {
			TextureOpColorGradient.anInt1764 = 2;
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			PingWorker.method5476(Class75.aClass215_31);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local18 + OpenGLTextureCache.anInt1771);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 1010) {
			TextureOpColorGradient.anInt1764 = 2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			PingWorker.method5476(Class4_Sub17.aClass215_27);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
		}
		if (local21 == 1009) {
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			ShadowTileGL.anInt3911 = arg0;
			TextureOpColorGradient.anInt1764 = 2;
			Static14.anInt191 = arg2;
			PingWorker.method5476(Class135.aClass215_45);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
		}
		if (local21 == 4) {
			TextureOpColorGradient.anInt1764 = 2;
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
			Static14.anInt191 = arg2;
			ShadowTileGL.anInt3911 = arg0;
			PingWorker.method5476(LocType.aClass215_79);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(local15 + Class50_Sub1.anInt3453);
			ParticleNode.aClass4_Sub12_Sub1_5.method2494(OpenGLTextureCache.anInt1771 + local18);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496(local25);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static384.aClass244_1.method5489(82) ? 1 : 0);
			ProjectileAnimationNode.method1664(local15, local18);
		}
		if (local21 == 20) {
			local120 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local25];
			if (local120 != null) {
				TextureOpColorGradient.anInt1764 = 2;
				Static14.anInt191 = arg2;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				ShadowTileGL.anInt3911 = arg0;
				PingWorker.method5476(SpotAnimation.aClass215_12);
				ParticleNode.aClass4_Sub12_Sub1_5.method2495(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2494(local25);
				Static166.method2734(0, local120.movementBaseY[0], local120.movementBaseX[0], local120.getSize(), true, local120.getSize(), 0, -2);
			}
		}
		if (local21 == 11) {
			local180 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local25];
			if (local180 != null) {
				ShadowTileGL.anInt3911 = arg0;
				TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS = 0;
				Static14.anInt191 = arg2;
				TextureOpColorGradient.anInt1764 = 2;
				PingWorker.method5476(ItemType.aClass215_83);
				ParticleNode.aClass4_Sub12_Sub1_5.method2521(Static384.aClass244_1.method5489(82) ? 1 : 0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(local25);
				Static166.method2734(0, local180.movementBaseY[0], local180.movementBaseX[0], local180.getSize(), true, local180.getSize(), 0, -2);
			}
		}
		if (AbstractArbBufferGL.aBoolean599) {
			Class16_Sub5.method5139();
		}
		if (EnvironmentSampler.aClass247_8 != null && TextureOpSprite.anInt1087 == 0) {
			Class4_Sub12_Sub1.method1142(EnvironmentSampler.aClass247_8);
		}
	}
}
