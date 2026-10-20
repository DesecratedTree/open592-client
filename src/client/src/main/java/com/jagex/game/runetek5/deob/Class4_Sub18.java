package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.OpenGLArbTextureCubeMapGenerator;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!fl")
public final class Class4_Sub18 extends Country {

	@OriginalMember(owner = "client!fl", name = "p", descriptor = "Lclient!pk;")
	public final Class183 aClass183_14 = new Class183();

@OriginalMember(owner = "client!fl", name = "l", descriptor = "I")
	public static int anInt2392;
@OriginalMember(owner = "client!fl", name = "a", descriptor = "(IBI)Z")
	public static boolean method2029(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (Class9_Sub3.method1353(arg0, arg1) | (arg1 & 0x10000) != 0 || FileOnDisk.method3468(arg0, arg1)) {
			return true;
		} else {
			return (arg0 & 0x37) == 0 && BufferedSocket.method137(arg0, arg1);
		}
	}
@OriginalMember(owner = "client!fl", name = "a", descriptor = "(III)Z")
	public static boolean method2030(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) int local7 = Class105.anIntArrayArrayArray13[arg0][arg1][arg2];
		if (local7 == -Class93.anInt2891) {
			return false;
		} else if (local7 == Class93.anInt2891) {
			return true;
		} else {
			@Pc(22) int local22 = arg1 << Static231.anInt4434;
			@Pc(26) int local26 = arg2 << Static231.anInt4434;
			if (Class177.method4088(local22 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2), local26 + 1) && Class177.method4088(local22 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg2), local26 + 1) && Class177.method4088(local22 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg2 + 1), local26 + CacheableNode.anInt5935 - 1) && Class177.method4088(local22 + 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2 + 1), local26 + CacheableNode.anInt5935 - 1) && Class177.method4088(local22 + ReflectingTextureEffect.anInt5747, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2), local26 + 1) && Class177.method4088(local22 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1 + 1, arg2), local26 + ReflectingTextureEffect.anInt5747) && Class177.method4088(local22 + ReflectingTextureEffect.anInt5747, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2 + 1), local26 + CacheableNode.anInt5935 - 1) && Class177.method4088(local22 + CacheableNode.anInt5935 - 1, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2), local26 + ReflectingTextureEffect.anInt5747) && Class177.method4088(local22 + ReflectingTextureEffect.anInt5747, Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2), local26 + ReflectingTextureEffect.anInt5747)) {
				Class105.anIntArrayArrayArray13[arg0][arg1][arg2] = Class93.anInt2891;
				return true;
			} else {
				Class105.anIntArrayArrayArray13[arg0][arg1][arg2] = -Class93.anInt2891;
				return false;
			}
		}
	}
@OriginalMember(owner = "client!fl", name = "a", descriptor = "([SI)[S")
	public static short[] method2031(@OriginalArg(0) short[] arg0) {
		if (arg0 == null) {
			return null;
		} else {
			@Pc(18) short[] local18 = new short[arg0.length];
			Static459.copy(arg0, 0, local18, 0, arg0.length);
			return local18;
		}
	}
@OriginalMember(owner = "client!fl", name = "a", descriptor = "(Lclient!r;Z)V")
	public static void method2032(@OriginalArg(0) PathingEntity arg0) {
		if (arg0 instanceof NPCEntity) {
			@Pc(24) NPCEntity local24 = (NPCEntity) arg0;
			if (local24.npcType != null) {
				MusicPatch.method4396(local24.aByte82 != Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82, local24);
			}
		} else if (arg0 instanceof PlayerEntity) {
			@Pc(8) PlayerEntity local8 = (PlayerEntity) arg0;
			Class65.method4026(Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 != local8.aByte82, local8);
		}
	}
@OriginalMember(owner = "client!fl", name = "b", descriptor = "(III)V")
	public static void method2033(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(12) Class4_Sub1_Sub11 local12 = NPCTypeList.method5222(arg1, 5);
		local12.method2604();
		local12.anInt3083 = arg0;
	}
@OriginalMember(owner = "client!fl", name = "a", descriptor = "(II)Z")
	public static boolean method2034(@OriginalArg(1) int arg0) {
		if (TileMessage.aBooleanArray25[arg0]) {
			return true;
		} else if (LegacyMouseManager.aClass76_81.method2101(arg0)) {
			@Pc(30) int local30 = LegacyMouseManager.aClass76_81.method2108(arg0);
			if (local30 == 0) {
				TileMessage.aBooleanArray25[arg0] = true;
				return true;
			}
			if (Class4_Sub36.aClass247ArrayArray2[arg0] == null) {
				Class4_Sub36.aClass247ArrayArray2[arg0] = new com.jagex.game.runetek5.config.rsinterface.Component[local30];
			}
			for (@Pc(52) int local52 = 0; local52 < local30; local52++) {
				if (Class4_Sub36.aClass247ArrayArray2[arg0][local52] == null) {
					@Pc(66) byte[] local66 = LegacyMouseManager.aClass76_81.method2104(local52, arg0);
					if (local66 != null) {
						@Pc(78) com.jagex.game.runetek5.config.rsinterface.Component local78 = Class4_Sub36.aClass247ArrayArray2[arg0][local52] = new com.jagex.game.runetek5.config.rsinterface.Component();
						local78.anInt6809 = local52 + (arg0 << 16);
						if (local66[0] != -1) {
							throw new IllegalStateException("if1");
						}
						local78.method5304(new Packet(local66));
						if (arg0 == 742 || arg0 == 744 || arg0 == 882) {
							System.out.println("[if" + arg0 + "] id=" + local52 + " type=" + local78.anInt6840 + " code=" + local78.anInt6772 + " hidden=" + local78.hidden + " name=" + local78.name + " str66=" + local78.aString66 + " str67=" + local78.aString67 + " str64=" + local78.aString64 + " opts=" + (local78.options == null ? "null" : java.util.Arrays.toString(local78.options)));
						}
					}
				}
			}
			TileMessage.aBooleanArray25[arg0] = true;
			return true;
		} else {
			return false;
		}
	}
@OriginalMember(owner = "client!fl", name = "a", descriptor = "(I)I")
	public static int method2035() {
		try {
			if (EasedAngle.anInt862 == 0) {
				if (Class105.aLong210 > Class168.method3962() - 5000L) {
					return 0;
				}
				HookRequest.aClass199_8 = Class126.aClass66_4.method1728(BufferedSocket.aString4, Class189.anInt5435);
				Class4_Sub3.aLong14 = Class168.method3962();
				EasedAngle.anInt862 = 1;
			}
			if (Class168.method3962() > Class4_Sub3.aLong14 + 30000L) {
				return Class22.method526(1000);
			}
			@Pc(78) int local78;
			@Pc(110) int local110;
			if (EasedAngle.anInt862 == 1) {
				if (HookRequest.aClass199_8.anInt5760 == 2) {
					return Class22.method526(1001);
				}
				if (HookRequest.aClass199_8.anInt5760 != 1) {
					return -1;
				}
				QuickchatMessageTypeList.aClass11_5 = new BufferedSocket((Socket) HookRequest.aClass199_8.anObject29, Class126.aClass66_4);
				HookRequest.aClass199_8 = null;
				local78 = 0;
				if (Class64_Sub1.aBoolean176) {
					local78 = BreakingWaterEffect.anInt1504;
				}
				ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_9.anInt1812);
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(local78);
				QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				Class196.method4427();
				local110 = QuickchatMessageTypeList.aClass11_5.method139();
				Class196.method4427();
				if (local110 != 0) {
					return Class22.method526(local110);
				}
				EasedAngle.anInt862 = 2;
			}
			if (EasedAngle.anInt862 == 2) {
				if (QuickchatMessageTypeList.aClass11_5.method133() < 2) {
					return -1;
				}
				GpuFaceNormal.anInt6950 = QuickchatMessageTypeList.aClass11_5.method139();
				GpuFaceNormal.anInt6950 <<= 0x8;
				GpuFaceNormal.anInt6950 += QuickchatMessageTypeList.aClass11_5.method139();
				EasedAngle.anInt862 = 3;
				Class164.aByteArray64 = new byte[GpuFaceNormal.anInt6950];
				OpenGLArbTextureCubeMapGenerator.anInt3980 = 0;
			}
			if (EasedAngle.anInt862 != 3) {
				return -1;
			}
			local78 = QuickchatMessageTypeList.aClass11_5.method133();
			if (local78 < 1) {
				return -1;
			}
			if (local78 > GpuFaceNormal.anInt6950 - OpenGLArbTextureCubeMapGenerator.anInt3980) {
				local78 = GpuFaceNormal.anInt6950 - OpenGLArbTextureCubeMapGenerator.anInt3980;
			}
			QuickchatMessageTypeList.aClass11_5.method131(OpenGLArbTextureCubeMapGenerator.anInt3980, local78, Class164.aByteArray64);
			OpenGLArbTextureCubeMapGenerator.anInt3980 += local78;
			if (GpuFaceNormal.anInt6950 > OpenGLArbTextureCubeMapGenerator.anInt3980) {
				return -1;
			} else if (NPCType.method5583(Class164.aByteArray64)) {
				ParticleBinaryTree.aClass78_Sub1Array1 = new Class78_Sub1[Class4_Sub7.anInt602];
				local110 = 0;
				for (@Pc(211) int local211 = MemoryPool.anInt4816; local211 <= JavaFallbackSurface.anInt5874; local211++) {
					@Pc(217) Class78_Sub1 local217 = Class126.method3192(local211);
					if (local217 != null) {
						ParticleBinaryTree.aClass78_Sub1Array1[local110++] = local217;
					}
				}
				GlCubeTexture.moveToVariableSpeed = 0;
				OpenGl2DTexture.aClass4_Sub42_3 = null;
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
				Class164.aByteArray64 = null;
				WaterfallEffect.anInt5132 = 0;
				EasedAngle.anInt862 = 0;
				Class105.aLong210 = Class168.method3962();
				return 0;
			} else {
				return Class22.method526(1002);
			}
		} catch (@Pc(249) IOException local249) {
			return Class22.method526(1003);
		}
	}
@OriginalMember(owner = "client!fl", name = "c", descriptor = "(I)V")
	public static void method2036() {
		try {
			@Pc(16) Method local16 = Runtime.class.getMethod("availableProcessors");
			if (local16 != null) {
				try {
					@Pc(20) Runtime local20 = Runtime.getRuntime();
					@Pc(26) Integer local26 = (Integer) local16.invoke(local20, (Object[]) null);
					RuntimeException_Sub1.anInt4622 = local26;
				} catch (@Pc(31) Throwable local31) {
				}
			}
		} catch (@Pc(33) Exception local33) {
		}
	}
}
