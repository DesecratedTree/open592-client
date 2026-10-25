package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.client.config.LaunchConfig;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class170;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub8;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.Class48;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.deob.Class130;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
@OriginalClass("client!vk")
public final class Class4_Sub6_Sub37 extends TextureOperation {

	@OriginalMember(owner = "client!vk", name = "J", descriptor = "I")
	private int anInt7001 = 4096;

	@OriginalMember(owner = "client!vk", name = "K", descriptor = "Z")
	private boolean aBoolean658 = true;

	@OriginalMember(owner = "client!vk", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub37() {
		super(1, false);
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt7001 = arg1.method2536();
		} else if (arg0 == 1) {
			this.aBoolean658 = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!vk", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(30) int[] local30 = this.getChildMonochromeOutput(Static211.anInt3929 & arg0 - 1, 0);
			@Pc(36) int[] local36 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(46) int[] local46 = this.getChildMonochromeOutput(Static211.anInt3929 & arg0 + 1, 0);
			@Pc(50) int[] local50 = local11[0];
			@Pc(54) int[] local54 = local11[1];
			@Pc(58) int[] local58 = local11[2];
			for (@Pc(60) int local60 = 0; local60 < MidiPcmStream.anInt1616; local60++) {
				@Pc(73) int local73 = (local46[local60] - local30[local60]) * this.anInt7001;
				@Pc(92) int local92 = (local36[local60 + 1 & Class78_Sub1.anInt3883] - local36[Class78_Sub1.anInt3883 & local60 - 1]) * this.anInt7001;
				@Pc(96) int local96 = local92 >> 12;
				@Pc(100) int local100 = local73 >> 12;
				@Pc(106) int local106 = local96 * local96 >> 12;
				@Pc(112) int local112 = local100 * local100 >> 12;
				@Pc(127) int local127 = (int) (Math.sqrt((double) ((float) (local106 + local112 + 4096) / 4096.0F)) * 4096.0D);
				@Pc(133) int local133;
				@Pc(131) int local131;
				@Pc(135) int local135;
				if (local127 == 0) {
					local131 = 0;
					local133 = 0;
					local135 = 0;
				} else {
					local131 = local73 / local127;
					local133 = local92 / local127;
					local135 = 16777216 / local127;
				}
				if (this.aBoolean658) {
					local135 = (local135 >> 1) + 2048;
					local131 = (local131 >> 1) + 2048;
					local133 = (local133 >> 1) + 2048;
				}
				local50[local60] = local133;
				local54[local60] = local131;
				local58[local60] = local135;
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!vk", name = "F", descriptor = "Lclient!lq;")
	public static FileOnDisk aClass139_5;
@OriginalMember(owner = "client!vk", name = "I", descriptor = "I")
	public static int anInt7000;
@OriginalMember(owner = "client!vk", name = "a", descriptor = "(Lclient!rs;I)V")
	public static void method5429(@OriginalArg(0) NPCEntity arg0) {
		for (@Pc(10) ReflectionCheck local10 = (ReflectionCheck) Class93.aClass183_20.method4140(); local10 != null; local10 = (ReflectionCheck) Class93.aClass183_20.method4144()) {
			if (arg0 == local10.aClass16_Sub1_Sub5_Sub2_1) {
				if (local10.aClass4_Sub15_Sub3_2 != null) {
					GameCanvas.aClass4_Sub15_Sub2_2.method2955(local10.aClass4_Sub15_Sub3_2);
					local10.aClass4_Sub15_Sub3_2 = null;
				}
				local10.method5684();
				return;
			}
		}
	}
@OriginalMember(owner = "client!vk", name = "f", descriptor = "(I)V")
	public static void method5430() {
		if (CacheableNodeByteArray.anInt4506 == 0 || CacheableNodeByteArray.anInt4506 == 5) {
			return;
		}
		try {
			if (++SpotAnimation.anInt1366 > 2000) {
				if (QuickchatMessageTypeList.aClass11_5 != null) {
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
				}
				if (Class130.anInt3932 >= 1) {
					CacheableNodeByteArray.anInt4506 = 0;
					StringNode.anInt5302 = -5;
					return;
				}
				CacheableNodeByteArray.anInt4506 = 1;
				Class130.anInt3932++;
				if (Class189.anInt5436 == Skybox.anInt6723) {
					Skybox.anInt6723 = Class4_Sub6_Sub8.anInt1949;
				} else {
					Skybox.anInt6723 = Class189.anInt5436;
				}
				SpotAnimation.anInt1366 = 0;
			}
			if (CacheableNodeByteArray.anInt4506 == 1) {
				Class26.aClass199_3 = Class126.aClass66_4.method1728(Class191.aString54, Skybox.anInt6723);
				CacheableNodeByteArray.anInt4506 = 2;
			}
			@Pc(112) int local112;
			if (CacheableNodeByteArray.anInt4506 == 2) {
				if (Class26.aClass199_3.anInt5760 == 2) {
					throw new IOException();
				}
				if (Class26.aClass199_3.anInt5760 != 1) {
					return;
				}
				QuickchatMessageTypeList.aClass11_5 = new BufferedSocket((Socket) Class26.aClass199_3.anObject29, Class126.aClass66_4);
				Class26.aClass199_3 = null;
				@Pc(102) long local102 = SoundBankCache.aLong174 = Class4_Sub6_Sub7.method1684(Static2.aString1);
				ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
				local112 = (int) (local102 >> 16 & 0x1FL);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_1.anInt1812);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(local112);
				QuickchatMessageTypeList.aClass11_5.method142(2, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				Class196.method4427();
				@Pc(134) int local134 = QuickchatMessageTypeList.aClass11_5.method139();
				Class196.method4427();
				if (local134 != 0) {
					CacheableNodeByteArray.anInt4506 = 0;
					StringNode.anInt5302 = local134;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					return;
				}
				CacheableNodeByteArray.anInt4506 = 3;
			}
			if (CacheableNodeByteArray.anInt4506 == 3) {
				if (QuickchatMessageTypeList.aClass11_5.method133() < 8) {
					return;
				}
				QuickchatMessageTypeList.aClass11_5.method131(0, 8, Class14.aClass4_Sub12_Sub1_3.buffer);
				Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
				Class186.aLong183 = Class14.aClass4_Sub12_Sub1_3.method2501();
				@Pc(185) Packet local185 = new Packet(132); // 70: 1 length byte + up to 129 RSA bytes for the 1024-bit key
				@Pc(188) int[] local188 = new int[] { (int) (Math.random() * 9.9999999E7D), (int) (Math.random() * 9.9999999E7D), (int) (Class186.aLong183 >> 32), (int) Class186.aLong183 };
				local185.method2551(10);
				local185.method2531(local188[0]);
				local185.method2531(local188[1]);
				local185.method2531(local188[2]);
				local185.method2531(local188[3]);
				local185.method2504(Class4_Sub6_Sub7.method1684(Static2.aString1));
				local185.method2518(Class4_Sub36.aString52);
				local185.method2522(LaunchConfig.rsaExponent(), LaunchConfig.rsaModulus());
				ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
				if (VertexDeclarationComponent.anInt6667 == 40) {
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_5.anInt1812);
				} else {
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_3.anInt1812);
				}
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(0);
				local112 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(592);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(ChatFilter.anInt3177);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class269.method5664());
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(GpuPointLight.anInt2881);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(EffectiveVertex.anInt5346);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(TextureOpBrightness.aClass50_Sub1_1.anInt3440);
				Class56.method1471(ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(ReflectionCheck.aString13);
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(GlCubeTexture.anInt3259);
				@Pc(323) Packet local323 = TextureOpBrightness.aClass50_Sub1_1.method2863();
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(local323.currentPosition);
				ParticleNode.aClass4_Sub12_Sub1_5.method2519(local323.buffer, local323.currentPosition);
				RuntimeException_Sub1.aBoolean425 = true;
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(Js5ResourceRequest.mediumPos);
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static395.aClass76_92.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class193.aClass76_69.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Isaac.aClass76_20.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(TileLighting.aClass76_50.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class4_Sub6_Sub4.aClass76_19.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class4_Sub10.aClass76_27.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(TextureCombinerOp.aClass76_54.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(CollisionMap.aClass76_82.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(GlGround.aClass76_60.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(TextureOpTexture.aClass76_44.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class60.aClass76_23.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class170.aClass76_55.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(TextureOpBlend.aClass76_45.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static209.aClass76_48.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static312.aClass76_66.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class208.aClass76_79.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class4_Sub43.aClass76_99.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class36.aClass76_29.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(ParamTypeList.aClass76_87.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static391.aClass76_91.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class230.aClass76_90.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(WorldMapElement.aClass76_16.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(SpotAnimation.aClass76_17.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class4_Sub23.aClass76_39.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class48.aClass76_70.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(ByteArrayWrapper.aClass76_11.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Shadow.aClass76_61.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class16_Sub1_Sub6.aClass76_98.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class6_Sub2.aClass76_2.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static366.aClass76_83.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2531(Static366.aClass76_100.method2111());
				ParticleNode.aClass4_Sub12_Sub1_5.method2519(local185.buffer, local185.currentPosition);
				ParticleNode.aClass4_Sub12_Sub1_5.method2547(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local112);
				QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				ParticleNode.aClass4_Sub12_Sub1_5.method1139(local188);
				for (@Pc(551) int local551 = 0; local551 < 4; local551++) {
					local188[local551] += 50;
				}
				Class14.aClass4_Sub12_Sub1_3.method1139(local188);
				CacheableNodeByteArray.anInt4506 = 4;
			}
			if (CacheableNodeByteArray.anInt4506 == 4) {
				if (QuickchatMessageTypeList.aClass11_5.method133() < 1) {
					return;
				}
				@Pc(585) int local585 = QuickchatMessageTypeList.aClass11_5.method139();
				if (local585 == 21) {
					CacheableNodeByteArray.anInt4506 = 7;
				} else if (local585 == 29) {
					CacheableNodeByteArray.anInt4506 = 11;
				} else if (local585 == 1) {
					CacheableNodeByteArray.anInt4506 = 5;
					StringNode.anInt5302 = local585;
					return;
				} else if (local585 == 2) {
					CacheableNodeByteArray.anInt4506 = 8;
				} else if (local585 == 15) {
					PointEntity.anInt4075 = -2;
					CacheableNodeByteArray.anInt4506 = 12;
				} else if (local585 == 23 && Class130.anInt3932 < 1) {
					SpotAnimation.anInt1366 = 0;
					CacheableNodeByteArray.anInt4506 = 1;
					Class130.anInt3932++;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					return;
				} else {
					CacheableNodeByteArray.anInt4506 = 0;
					StringNode.anInt5302 = local585;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					return;
				}
			}
			if (CacheableNodeByteArray.anInt4506 == 6) {
				ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
				ParticleNode.aClass4_Sub12_Sub1_5.method1133(Class4_Sub1_Sub11.aClass60_4.anInt1812);
				QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				CacheableNodeByteArray.anInt4506 = 4;
			} else if (CacheableNodeByteArray.anInt4506 == 7) {
				if (QuickchatMessageTypeList.aClass11_5.method133() >= 1) {
					PlayerVarDomain.anInt3398 = (QuickchatMessageTypeList.aClass11_5.method139() + 3) * 60;
					StringNode.anInt5302 = 21;
					CacheableNodeByteArray.anInt4506 = 0;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
				}
			} else if (CacheableNodeByteArray.anInt4506 != 11) {
				if (CacheableNodeByteArray.anInt4506 == 8) {
					if (QuickchatMessageTypeList.aClass11_5.method133() < 13) {
						return;
					}
					QuickchatMessageTypeList.aClass11_5.method131(0, 13, Class14.aClass4_Sub12_Sub1_3.buffer);
					Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
					GlToolkit.activeChildCount = Class14.aClass4_Sub12_Sub1_3.method2490();
					Class79.anInt6262 = Class14.aClass4_Sub12_Sub1_3.method2490();
					Class16.aBoolean628 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					MemoryPool.aBoolean443 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					TextureOpSquareWaveform.aBoolean668 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					Static308.aBoolean486 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					Class6.anInt5452 = Class14.aClass4_Sub12_Sub1_3.method2536();
					Class4_Sub29.aBoolean423 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					MusicPatch.aBoolean506 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
					Static267.aClass262_2.method5564(MusicPatch.aBoolean506);
					Terrain.aClass206_3.method4704(MusicPatch.aBoolean506);
					Class196.aClass240_1.method5230(MusicPatch.aBoolean506);
					if (Class16.aBoolean628 && !TextureOpSquareWaveform.aBoolean668 || Class4_Sub29.aBoolean423) {
						try {
							Static458.method2593(Class126.aClass66_4.anApplet1, "zap");
						} catch (@Pc(880) Throwable local880) {
							if (Class183.aBoolean484) {
								try {
									Class126.aClass66_4.anApplet1.getAppletContext().showDocument(new URL(Class126.aClass66_4.anApplet1.getCodeBase(), "blank.ws"), "tbi");
								} catch (@Pc(896) Exception local896) {
								}
							}
						}
					} else {
						try {
							Static458.method2593(Class126.aClass66_4.anApplet1, "unzap");
						} catch (@Pc(870) Throwable local870) {
						}
					}
					if (Class222.aClass219_6 == GlToolkit.aClass219_2) {
						try {
							Static458.method2593(Class126.aClass66_4.anApplet1, "loggedin");
						} catch (@Pc(908) Throwable local908) {
						}
					}
					CacheableNodeByteArray.anInt4506 = 10;
				}
			if (CacheableNodeByteArray.anInt4506 == 10) {
				if (Class14.aClass4_Sub12_Sub1_3.method1141()) {
						if (QuickchatMessageTypeList.aClass11_5.method133() < 1) {
							return;
						}
						QuickchatMessageTypeList.aClass11_5.method131(Class14.aClass4_Sub12_Sub1_3.currentPosition + 2, 1, Class14.aClass4_Sub12_Sub1_3.buffer);
					}
					IdkType.aClass22_246 = GlBitBuffer.method2553()[Class14.aClass4_Sub12_Sub1_3.method1140()];
					PointEntity.anInt4075 = Class14.aClass4_Sub12_Sub1_3.method2536();
					CacheableNodeByteArray.anInt4506 = 9;
				}
				@Pc(970) int local970;
				if (CacheableNodeByteArray.anInt4506 == 9) {
					if (QuickchatMessageTypeList.aClass11_5.method133() >= PointEntity.anInt4075) {
						QuickchatMessageTypeList.aClass11_5.method131(0, PointEntity.anInt4075, Class14.aClass4_Sub12_Sub1_3.buffer);
						Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
						local970 = PointEntity.anInt4075;
						CacheableNodeByteArray.anInt4506 = 0;
						StringNode.anInt5302 = 2;
						Static167.method2736();
						Static391.method5117(Class14.aClass4_Sub12_Sub1_3);
						IDKTypeList.anInt3265 = -1;
						FileOnDisk.method3470();
						if (local970 != Class14.aClass4_Sub12_Sub1_3.currentPosition) {
							throw new RuntimeException("lswp pos:" + Class14.aClass4_Sub12_Sub1_3.currentPosition + " psize:" + local970);
						}
						IdkType.aClass22_246 = null;
					}
				} else if (CacheableNodeByteArray.anInt4506 == 12) {
					if (PointEntity.anInt4075 == -2) {
						if (QuickchatMessageTypeList.aClass11_5.method133() < 2) {
							return;
						}
						QuickchatMessageTypeList.aClass11_5.method131(0, 2, Class14.aClass4_Sub12_Sub1_3.buffer);
						Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
						PointEntity.anInt4075 = Class14.aClass4_Sub12_Sub1_3.method2536();
					}
					if (QuickchatMessageTypeList.aClass11_5.method133() >= PointEntity.anInt4075) {
						QuickchatMessageTypeList.aClass11_5.method131(0, PointEntity.anInt4075, Class14.aClass4_Sub12_Sub1_3.buffer);
						Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
						local970 = PointEntity.anInt4075;
						CacheableNodeByteArray.anInt4506 = 0;
						StringNode.anInt5302 = 15;
						Class4_Sub36.method4069();
						Static391.method5117(Class14.aClass4_Sub12_Sub1_3);
						if (local970 != Class14.aClass4_Sub12_Sub1_3.currentPosition) {
							throw new RuntimeException("lswpr pos:" + Class14.aClass4_Sub12_Sub1_3.currentPosition + " psize:" + local970);
						}
						IdkType.aClass22_246 = null;
					}
				}
			} else if (QuickchatMessageTypeList.aClass11_5.method133() >= 1) {
				Class177.anInt5328 = QuickchatMessageTypeList.aClass11_5.method139();
				CacheableNodeByteArray.anInt4506 = 0;
				StringNode.anInt5302 = 29;
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
			}
		} catch (@Pc(1095) IOException local1095) {
			if (QuickchatMessageTypeList.aClass11_5 != null) {
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
			}
			if (Class130.anInt3932 >= 1) {
				StringNode.anInt5302 = -4;
				CacheableNodeByteArray.anInt4506 = 0;
			} else {
				if (Class189.anInt5436 == Skybox.anInt6723) {
					Skybox.anInt6723 = Class4_Sub6_Sub8.anInt1949;
				} else {
					Skybox.anInt6723 = Class189.anInt5436;
				}
				CacheableNodeByteArray.anInt4506 = 1;
				Class130.anInt3932++;
				SpotAnimation.anInt1366 = 0;
			}
		}
	}
}
