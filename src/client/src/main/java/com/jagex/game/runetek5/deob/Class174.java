package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;

import java.io.IOException;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.deob.Class135;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpCurve;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!wa")
public abstract class Class174 {

	@OriginalMember(owner = "client!wa", name = "a", descriptor = "(ILclient!fq;)Lclient!fq;")
	public abstract SpriteRenderNode createSoftReference(@OriginalArg(1) SpriteRenderNode arg0);

@OriginalMember(owner = "client!wa", name = "b", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_66 = new Class215(55, 16);
@OriginalMember(owner = "client!wa", name = "e", descriptor = "Z")
	public static boolean aBoolean477 = false;
@OriginalMember(owner = "client!wa", name = "f", descriptor = "Lclient!o;")
	public static GameShell anApplet_Sub1_2 = null;
@OriginalMember(owner = "client!wa", name = "g", descriptor = "[C")
	public static final char[] aCharArray6 = new char[] { '[', ']', '#' };
@OriginalMember(owner = "client!wa", name = "a", descriptor = "(ZIZLjava/lang/String;)I")
	public static int method4041(@OriginalArg(1) int arg0, @OriginalArg(3) String arg1) {
		@Pc(26) boolean local26 = false;
		@Pc(28) boolean local28 = false;
		@Pc(30) int local30 = 0;
		@Pc(33) int local33 = arg1.length();
		for (@Pc(35) int local35 = 0; local35 < local33; local35++) {
			@Pc(41) char local41 = arg1.charAt(local35);
			if (local35 == 0) {
				if (local41 == '-') {
					local26 = true;
					continue;
				}
				if (local41 == '+') {
					continue;
				}
			}
			@Pc(67) int local67;
			if (local41 >= '0' && local41 <= '9') {
				local67 = local41 - '0';
			} else if (local41 >= 'A' && local41 <= 'Z') {
				local67 = local41 - '7';
			} else if (local41 >= 'a' && local41 <= 'z') {
				local67 = local41 - 'W';
			} else {
				throw new NumberFormatException();
			}
			if (local67 >= arg0) {
				throw new NumberFormatException();
			}
			if (local26) {
				local67 = -local67;
			}
			@Pc(111) int local111 = local67 + local30 * arg0;
			if (local111 / arg0 != local30) {
				throw new NumberFormatException();
			}
			local30 = local111;
			local28 = true;
		}
		if (!local28) {
			throw new NumberFormatException();
		}
		return local30;
	}
@OriginalMember(owner = "client!wa", name = "a", descriptor = "(I)V")
	public static void method4042() {
		if (Class120.anInt5353 > 1) {
			Static308.anInt5413 = MusicPatch.anInt5640;
			Class120.anInt5353--;
		}
		if (ProjectileAnimationNode.anInt1910 > 0) {
			ProjectileAnimationNode.anInt1910--;
		}
		if (Class16_Sub1_Sub1.aBoolean38) {
			Class16_Sub1_Sub1.aBoolean38 = false;
			ParticleEffector.method2731();
			return;
		}
		if (!Class4_Sub6_Sub22.aBoolean411) {
			OpenGl2DImageTexture.method4198();
		}
		for (@Pc(38) int local38 = 0; local38 < 100 && Class6.method4188(); local38++) {
		}
		if (VertexDeclarationComponent.anInt6667 != 30) {
			return;
		}
		Class191.method4336(BasType.aClass215_54.method4926(), ParticleNode.aClass4_Sub12_Sub1_5);
		if (NativeBillboardFace.aClass4_Sub42_2 == null) {
			if (StillWaterEffect.aLong212 <= Class168.method3962()) {
				NativeBillboardFace.aClass4_Sub42_2 = Class4_Sub6_Sub35.aClass256_1.requestPing(Class191.aString54);
			}
		} else if (NativeBillboardFace.aClass4_Sub42_2.ping != -1) {
			PingWorker.method5476(Class21.aClass215_7);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(NativeBillboardFace.aClass4_Sub42_2.ping);
			NativeBillboardFace.aClass4_Sub42_2 = null;
			StillWaterEffect.aLong212 = Class168.method3962() + 30000L;
		}
		@Pc(99) Class4_Sub5 local99 = (Class4_Sub5) Component.aClass183_47.method4140();
		@Pc(119) int local119;
		@Pc(140) int local140;
		@Pc(159) int local159;
		@Pc(175) boolean local175;
		@Pc(227) int local227;
		@Pc(234) int local234;
		@Pc(246) int local246;
		if (local99 != null || Class168.method3962() - 2000L > HookRequest.aLong177) {
			@Pc(116) boolean local116 = false;
			local119 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
			for (@Pc(124) Class4_Sub5 local124 = (Class4_Sub5) Class4_Sub6_Sub31.aClass183_42.method4140(); local124 != null && ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local119 < 240; local124 = (Class4_Sub5) Class4_Sub6_Sub31.aClass183_42.method4144()) {
				local124.method5684();
				local140 = local124.method519();
				if (local140 < 0) {
					local140 = 0;
				} else if (local140 > 65534) {
					local140 = 65534;
				}
				local159 = local124.method518();
				if (local159 < 0) {
					local159 = 0;
				} else if (local159 > 65534) {
					local159 = 65534;
				}
				local175 = false;
				if (local124.method519() == -1 && local124.method518() == -1) {
					local140 = -1;
					local175 = true;
					local159 = -1;
				}
				if (local159 != Class4_Sub45.anInt7358 || local140 != Static410.anInt6734) {
					if (!local116) {
						PingWorker.method5476(AnimBase.aClass215_62);
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
						local116 = true;
						local119 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
					}
					local227 = local159 - Class4_Sub45.anInt7358;
					Class4_Sub45.anInt7358 = local159;
					local234 = local140 - Static410.anInt6734;
					Static410.anInt6734 = local140;
					local246 = (int) ((local124.method516() - HookRequest.aLong177) / 20L);
					if (local246 < 8 && local227 >= -32 && local227 <= 31 && local234 >= -32 && local234 <= 31) {
						local234 += 32;
						local227 += 32;
						ParticleNode.aClass4_Sub12_Sub1_5.method2524(local234 + (local246 << 12) + (local227 << 6));
					} else if (local246 < 32 && local227 >= -128 && local227 <= 127 && local234 >= -128 && local234 <= 127) {
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(local246 + 128);
						local227 += 128;
						local234 += 128;
						ParticleNode.aClass4_Sub12_Sub1_5.method2524(local234 + (local227 << 8));
					} else if (local246 >= 32) {
						ParticleNode.aClass4_Sub12_Sub1_5.method2524(local246 + 57344);
						if (local175) {
							ParticleNode.aClass4_Sub12_Sub1_5.method2531(Integer.MIN_VALUE);
						} else {
							ParticleNode.aClass4_Sub12_Sub1_5.method2531(local140 << 16 | local159);
						}
					} else {
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(local246 + 192);
						if (local175) {
							ParticleNode.aClass4_Sub12_Sub1_5.method2531(Integer.MIN_VALUE);
						} else {
							ParticleNode.aClass4_Sub12_Sub1_5.method2531(local140 << 16 | local159);
						}
					}
					HookRequest.aLong177 = local124.method516();
				}
			}
			if (local116) {
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local119);
			}
		}
		@Pc(416) int local416;
		@Pc(463) int local463;
		if (local99 != null) {
			@Pc(402) long local402 = (local99.method516() - Class48_Sub1_Sub1.aLong160) / 50L;
			if (local402 > 32767L) {
				local402 = 32767L;
			}
			Class48_Sub1_Sub1.aLong160 = local99.method516();
			local416 = local99.method519();
			if (local416 < 0) {
				local416 = 0;
			} else if (local416 > 65535) {
				local416 = 65535;
			}
			local140 = local99.method518();
			if (local140 < 0) {
				local140 = 0;
			} else if (local140 > 65535) {
				local140 = 65535;
			}
			@Pc(447) byte local447 = 0;
			if (local99.method515() == 2) {
				local447 = 1;
			}
			local463 = (int) local402;
			PingWorker.method5476(ModelNormalsGL.aClass215_47);
			ParticleNode.aClass4_Sub12_Sub1_5.method2531(local416 << 16 | local140);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505(local463 | local447 << 15);
		}
		@Pc(489) int local489;
		if (Class4_Sub6_Sub15.anInt3602 > 0) {
			local489 = 0;
			for (local119 = 0; local119 < Class4_Sub6_Sub15.anInt3602; local119++) {
				if (Class16_Sub2.aClass30Array4[local119].method741()) {
					local489++;
				}
			}
			if (local489 > 0) {
				PingWorker.method5476(Class135.aClass215_44);
				if (local489 > 75) {
					local489 = 75;
				}
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(local489 * 3);
				for (local416 = 0; local416 < Class4_Sub6_Sub15.anInt3602; local416++) {
					@Pc(537) Class30 local537 = Class16_Sub2.aClass30Array4[local416];
					if (local537.method741()) {
						@Pc(550) long local550 = (local537.method742() - TileLighting.aLong156) / 50L;
						TileLighting.aLong156 = local537.method742();
						if (local550 > 65535L) {
							local550 = 65535L;
						}
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(local537.method746());
						ParticleNode.aClass4_Sub12_Sub1_5.method2524((int) local550);
					}
				}
			}
		}
		if (Class193.anInt5626 > 0) {
			Class193.anInt5626--;
		}
		if (BasType.aBoolean429 && Class193.anInt5626 <= 0) {
			BasType.aBoolean429 = false;
			Class193.anInt5626 = 20;
			PingWorker.method5476(Class4_Sub6_Sub23.aClass215_57);
			ParticleNode.aClass4_Sub12_Sub1_5.method2505((int) Static368.aFloat192 >> 3);
			ParticleNode.aClass4_Sub12_Sub1_5.method2496((int) Class16_Sub2.aFloat142 >> 3);
		}
		if (RequestManager.aBoolean457 && !ObjStackEntity.aBoolean74) {
			ObjStackEntity.aBoolean74 = true;
			PingWorker.method5476(Class4_Sub9.aClass215_9);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(1);
		}
		if (!RequestManager.aBoolean457 && ObjStackEntity.aBoolean74) {
			ObjStackEntity.aBoolean74 = false;
			PingWorker.method5476(Class4_Sub9.aClass215_9);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
		}
		if (!RuntimeException_Sub1.aBoolean425) {
			PingWorker.method5476(TextureOpBlend.aClass215_39);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
			local489 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
			@Pc(692) Packet local692 = TextureOpBrightness.aClass50_Sub1_1.method2863();
			ParticleNode.aClass4_Sub12_Sub1_5.method2519(local692.buffer, local692.currentPosition);
			ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local489);
			RuntimeException_Sub1.aBoolean425 = true;
		}
		if (Class70.aClass164ArrayArrayArray5 != null) {
			if (Class3_Sub3.anInt2386 == 2) {
				Class196.method4430();
			} else if (Class3_Sub3.anInt2386 == 3) {
				Class4_Sub5.method514();
			}
		}
		if (Static308.aBoolean487) {
			Static308.aBoolean487 = false;
		} else {
			Static380.aFloat195 /= 2.0F;
		}
		if (Static32.aBoolean63) {
			Static32.aBoolean63 = false;
		} else {
			EasedAngle.aFloat28 /= 2.0F;
		}
		Class154.method3687();
		if (VertexDeclarationComponent.anInt6667 != 30) {
			return;
		}
		DummyInputStream.method3623();
		MidiPcmStream.method1433();
		SpriteGL.method5294();
		Packet.method2527();
		Static410.anInt6735++;
		if (Static410.anInt6735 > 750) {
			ParticleEffector.method2731();
			return;
		}
		SeqType.method3458();
		GameShell.method884();
		RectangularTextureGL.method2009();
		for (local489 = Class4_Sub6_Sub24.aClass114_1.removeNext(true); local489 != -1; local489 = Class4_Sub6_Sub24.aClass114_1.removeNext(false)) {
			Static277.method3934(local489);
			Class7.anIntArray7[HookRequest.anInt5255++ & 0x1F] = local489;
		}
		@Pc(840) com.jagex.game.runetek5.config.rsinterface.Component local840;
		for (@Pc(815) Class4_Sub1_Sub11 local815 = DequeIterator.method1614(); local815 != null; local815 = DequeIterator.method1614()) {
			local416 = local815.method2606();
			local140 = local815.method2609();
			if (local416 == 1) {
				ParticleEffector.WALL_ROTATION_FLAGS[local140] = local815.anInt3083;
				TextureOpKaleidoscope.aBoolean256 |= Class16_Sub1_Sub1.aBooleanArray3[local140];
				Class93.anIntArray184[OpenGl2DImageTexture.anInt5473++ & 0x1F] = local140;
			} else if (local416 == 2) {
				RequestManager.aStringArray20[local140] = local815.aString32;
				Static277.anIntArray337[TextureType.anInt4042++ & 0x1F] = local140;
			} else if (local416 == 3) {
				local840 = ParticleBinaryTree.method5121(local140);
				if (!local815.aString32.equals(local840.aString67)) {
					local840.aString67 = local815.aString32;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 4) {
				local840 = ParticleBinaryTree.method5121(local140);
				local463 = local815.anInt3083;
				local227 = local815.anInt3082;
				local234 = local815.anInt3079;
				if (local463 != local840.anInt6796 || local840.anInt6781 != local227 || local234 != local840.anInt6848) {
					local840.anInt6781 = local227;
					local840.anInt6848 = local234;
					local840.anInt6796 = local463;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 5) {
				local840 = ParticleBinaryTree.method5121(local140);
				if (local815.anInt3083 != local840.anInt6788 || local815.anInt3083 == -1) {
					local840.anInt6815 = 0;
					local840.anInt6829 = 0;
					local840.anInt6783 = 1;
					local840.anInt6788 = local815.anInt3083;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 6) {
				local159 = local815.anInt3083;
				local463 = local159 >> 10 & 0x1F;
				local227 = local159 >> 5 & 0x1F;
				local234 = local159 & 0x1F;
				local246 = (local234 << 3) + (local227 << 11) + (local463 << 19);
				@Pc(937) com.jagex.game.runetek5.config.rsinterface.Component local937 = ParticleBinaryTree.method5121(local140);
				if (local937.anInt6772 != local246) {
					local937.anInt6772 = local246;
					Class4_Sub12_Sub1.method1142(local937);
				}
			} else if (local416 == 7) {
				local840 = ParticleBinaryTree.method5121(local140);
				local175 = local815.anInt3083 == 1;
				if (local175 != local840.hidden) {
					local840.hidden = local175;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 8) {
				local840 = ParticleBinaryTree.method5121(local140);
				if (local840.anInt6844 != local815.anInt3083 || local840.anInt6858 != local815.anInt3082 || local815.anInt3079 != local840.spriteScale) {
					local840.spriteScale = local815.anInt3079;
					local840.anInt6858 = local815.anInt3082;
					local840.anInt6844 = local815.anInt3083;
					if (local840.anInt6779 != -1) {
						if (local840.anInt6822 > 0) {
							local840.spriteScale = local840.spriteScale * 32 / local840.anInt6822;
						} else if (local840.anInt6816 > 0) {
							local840.spriteScale = local840.spriteScale * 32 / local840.anInt6816;
						}
					}
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 9) {
				local840 = ParticleBinaryTree.method5121(local140);
				if (local840.anInt6779 != local815.anInt3083 || local840.anInt6832 != local815.anInt3082) {
					local840.anInt6832 = local815.anInt3082;
					local840.anInt6779 = local815.anInt3083;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 10) {
				local840 = ParticleBinaryTree.method5121(local140);
				if (local840.anInt6814 != local815.anInt3083 || local840.anInt6826 != local815.anInt3082 || local840.anInt6843 != local815.anInt3079) {
					local840.anInt6814 = local815.anInt3083;
					local840.anInt6826 = local815.anInt3082;
					local840.anInt6843 = local815.anInt3079;
					Class4_Sub12_Sub1.method1142(local840);
				}
			} else if (local416 == 11) {
				local840 = ParticleBinaryTree.method5121(local140);
				local840.aByte79 = 0;
				local840.anInt6836 = local840.anInt6804 = local815.anInt3083;
				local840.aByte81 = 0;
				local840.anInt6859 = local840.anInt6808 = local815.anInt3082;
				Class4_Sub12_Sub1.method1142(local840);
			} else if (local416 == 12) {
				local840 = ParticleBinaryTree.method5121(local140);
				local463 = local815.anInt3083;
				if (local840 != null && local840.anInt6840 == 0) {
					if (local463 > local840.anInt6834 - local840.anInt6805) {
						local463 = local840.anInt6834 - local840.anInt6805;
					}
					if (local463 < 0) {
						local463 = 0;
					}
					if (local840.anInt6849 != local463) {
						local840.anInt6849 = local463;
						Class4_Sub12_Sub1.method1142(local840);
					}
				}
			} else if (local416 == 14) {
				local840 = ParticleBinaryTree.method5121(local140);
				local840.lb = local815.anInt3083;
			} else if (local416 == 15) {
				GlRenderList.anInt1094 = local815.anInt3082;
				Class208.aBoolean556 = true;
				Class30.anInt771 = local815.anInt3083;
			} else if (local416 == 16) {
				local840 = ParticleBinaryTree.method5121(local140);
				local840.anInt6791 = local815.anInt3083;
			}
		}
		LightGL.npcCount++;
		if (TextureOpColorGradient.anInt1764 != 0) {
			TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS += 20;
			if (TextureOpDiagonalWaves.LOGIN_PACKET_CURSOR_POS >= 400) {
				TextureOpColorGradient.anInt1764 = 0;
			}
		}
		if (EnvironmentSampler.aClass247_8 != null) {
			TextureOpSprite.anInt1087++;
			if (TextureOpSprite.anInt1087 >= 15) {
				Class4_Sub12_Sub1.method1142(EnvironmentSampler.aClass247_8);
				EnvironmentSampler.aClass247_8 = null;
			}
		}
		GlGroundLayer.aClass247_2 = null;
		Static232.aClass247_9 = null;
		Class79_Sub1.aBoolean570 = false;
		Class95_Sub3.aBoolean584 = false;
		Class79_Sub1.method4870(-1, null, -1);
		EmissiveTriangle.method697(null, -1, -1);
		if (!AbstractArbBufferGL.aBoolean599) {
			HashTable.anInt3147 = -1;
		}
		Class16_Sub2.method3582();
		MusicPatch.anInt5640++;
		if (AbstractFontRenderer.aBoolean588) {
			PingWorker.method5476(TextureOpTile.aClass215_84);
			ParticleNode.aClass4_Sub12_Sub1_5.method2532(Class145_Sub1.anInt7274 | Class4_Sub6_Sub25.anInt4958 << 14 | Class16_Sub4.anInt3644 << 28);
			AbstractFontRenderer.aBoolean588 = false;
		}
		while (true) {
			@Pc(1462) HookRequest local1462;
			@Pc(1467) com.jagex.game.runetek5.config.rsinterface.Component local1467;
			do {
				local1462 = (HookRequest) ComponentSurface.aClass183_52.method4136();
				if (local1462 == null) {
					while (true) {
						do {
							local1462 = (HookRequest) TextureOpCurve.aClass183_3.method4136();
							if (local1462 == null) {
								while (true) {
									do {
										local1462 = (HookRequest) TextureOpMandelbrot.aClass183_35.method4136();
										if (local1462 == null) {
											if (GlGroundLayer.aClass247_2 == null) {
												SpotAnimation.anInt1367 = 0;
											}
											if (Static281.aClass247_12 != null) {
												Static1.method3();
											}
											if (GlToolkit.activeChildCount > 0 && Static384.aClass244_1.method5489(82) && Static384.aClass244_1.method5489(81) && PointLight.anInt3862 != 0) {
												local416 = Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 - PointLight.anInt3862;
												if (local416 < 0) {
													local416 = 0;
												} else if (local416 > 3) {
													local416 = 3;
												}
												Class4_Sub21_Sub2.method3168(local416, OpenGLTextureCache.anInt1771 + Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0], Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0] + Class50_Sub1.anInt3453);
											}
											ObjStackEntity.method730();
											for (local416 = 0; local416 < 5; local416++) {
												@Pc(1675) int local1675 = Class95_Sub3.anIntArray486[local416]++;
											}
											if (TextureOpKaleidoscope.aBoolean256 && FloType.aLong119 < Class168.method3962() - 60000L) {
												Class190.method4216();
											}
											Class4_Sub21_Sub2.anInt3873++;
											if (Class4_Sub21_Sub2.anInt3873 > 500) {
												Class4_Sub21_Sub2.anInt3873 = 0;
												local140 = (int) (Math.random() * 8.0D);
												if ((local140 & 0x1) == 1) {
													Class3_Sub4.anInt4024 += Static105.anInt2326;
												}
												if ((local140 & 0x4) == 4) {
													Class164.anInt5119 += SpotAnimationType.anInt5006;
												}
												if ((local140 & 0x2) == 2) {
													TextureOpBrightness.anInt5620 += Class4_Sub6_Sub16.anInt3714;
												}
											}
											if (Class3_Sub4.anInt4024 < -50) {
												Static105.anInt2326 = 2;
											}
											if (TextureOpBrightness.anInt5620 < -55) {
												Class4_Sub6_Sub16.anInt3714 = 2;
											}
											if (Class3_Sub4.anInt4024 > 50) {
												Static105.anInt2326 = -2;
											}
											if (TextureOpBrightness.anInt5620 > 55) {
												Class4_Sub6_Sub16.anInt3714 = -2;
											}
											if (Class164.anInt5119 < -40) {
												SpotAnimationType.anInt5006 = 1;
											}
											Isaac.deferredDragOffsetY++;
											if (Class164.anInt5119 > 40) {
												SpotAnimationType.anInt5006 = -1;
											}
											if (Isaac.deferredDragOffsetY > 500) {
												Isaac.deferredDragOffsetY = 0;
												local140 = (int) (Math.random() * 8.0D);
												if ((local140 & 0x2) == 2) {
													Class4_Sub17.anInt2374 += Class4_Sub17.anInt2369;
												}
												if ((local140 & 0x1) == 1) {
													PlayerVarDomain.anInt3399 += GlVboElementsArrayBuffer.anInt6628;
												}
											}
											if (PlayerVarDomain.anInt3399 < -60) {
												GlVboElementsArrayBuffer.anInt6628 = 2;
											}
											if (PlayerVarDomain.anInt3399 > 60) {
												GlVboElementsArrayBuffer.anInt6628 = -2;
											}
											if (Class4_Sub17.anInt2374 < -20) {
												Class4_Sub17.anInt2369 = 1;
											}
											if (Class4_Sub17.anInt2374 > 10) {
												Class4_Sub17.anInt2369 = -1;
											}
											Class105.anInt6744++;
											if (Class105.anInt6744 > 50) {
												PingWorker.method5476(Static14.aClass215_2);
											}
											if (Class58.aBoolean159) {
												Class95_Sub3_Sub1.method4996();
												Class58.aBoolean159 = false;
											}
											try {
												if (QuickchatMessageTypeList.aClass11_5 != null && ParticleNode.aClass4_Sub12_Sub1_5.currentPosition > 0) {
													GlRenderList.anInt1097 += ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
													QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
													ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
													Class105.anInt6744 = 0;
													return;
												}
												return;
											} catch (@Pc(1884) IOException local1884) {
												ParticleEffector.method2731();
												return;
											}
										}
										local1467 = local1462.source;
										if (local1467.anInt6865 < 0) {
											break;
										}
										local840 = ParticleBinaryTree.method5121(local1467.anInt6850);
									} while (local840 == null || local840.slotChildren == null || local1467.anInt6865 >= local840.slotChildren.length || local1467 != local840.slotChildren[local1467.anInt6865]);
									Static271.executeHookInner(local1462);
								}
							}
							local1467 = local1462.source;
							if (local1467.anInt6865 < 0) {
								break;
							}
							local840 = ParticleBinaryTree.method5121(local1467.anInt6850);
						} while (local840 == null || local840.slotChildren == null || local1467.anInt6865 >= local840.slotChildren.length || local840.slotChildren[local1467.anInt6865] != local1467);
						Static271.executeHookInner(local1462);
					}
				}
				local1467 = local1462.source;
				if (local1467.anInt6865 < 0) {
					break;
				}
				local840 = ParticleBinaryTree.method5121(local1467.anInt6850);
			} while (local840 == null || local840.slotChildren == null || local840.slotChildren.length <= local1467.anInt6865 || local1467 != local840.slotChildren[local1467.anInt6865]);
			Static271.executeHookInner(local1462);
		}
	}
@OriginalMember(owner = "client!wa", name = "a", descriptor = "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;")
	public static String method4044(@OriginalArg(0) String arg0, @OriginalArg(2) String arg1, @OriginalArg(3) String arg2) {
		for (@Pc(14) int local14 = arg2.indexOf(arg1); local14 != -1; local14 = arg2.indexOf(arg1, arg0.length() + local14)) {
			arg2 = arg2.substring(0, local14) + arg0 + arg2.substring(local14 + arg1.length());
		}
		return arg2;
	}
}
