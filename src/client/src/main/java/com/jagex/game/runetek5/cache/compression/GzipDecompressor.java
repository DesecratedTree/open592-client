package com.jagex.game.runetek5.cache.compression;

import java.util.zip.Inflater;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Static145;
import com.jagex.game.runetek5.deob.Static148;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static277;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.deob.Static380;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.cache.compression.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!pp")
public final class GzipDecompressor {

	@OriginalMember(owner = "client!pp", name = "a", descriptor = "Ljava/util/zip/Inflater;")
	private Inflater inflater;

	@OriginalMember(owner = "client!pp", name = "<init>", descriptor = "(III)V")
	private GzipDecompressor(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
	}

	@OriginalMember(owner = "client!pp", name = "<init>", descriptor = "()V")
	public GzipDecompressor() {
		this(-1, 1000000, 1000000);
	}

	@OriginalMember(owner = "client!pp", name = "a", descriptor = "(B[BLclient!iv;)V")
	public void method4171(@OriginalArg(1) byte[] arg0, @OriginalArg(2) Packet arg1) {
		if (arg1.buffer[arg1.currentPosition] != 31 || arg1.buffer[arg1.currentPosition + 1] != -117) {
			throw new RuntimeException("Invalid GZIP header!");
		}
		if (this.inflater == null) {
			this.inflater = new Inflater(true);
		}
		try {
			this.inflater.setInput(arg1.buffer, arg1.currentPosition + 10, -arg1.currentPosition + -10 + -8 + arg1.buffer.length);
			this.inflater.inflate(arg0);
		} catch (@Pc(67) Exception local67) {
			this.inflater.reset();
			throw new RuntimeException("Invalid GZIP compressed data!");
		}
		this.inflater.reset();
	}

@OriginalMember(owner = "client!pp", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_252 = new Class22(70, 4);
@OriginalMember(owner = "client!pp", name = "f", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_69 = new Class215(44, -1);
@OriginalMember(owner = "client!pp", name = "a", descriptor = "(ZBIIZ)V")
	public static void method4167(@OriginalArg(0) boolean arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean arg3) {
		if (!arg3 && IDKTypeList.anInt3265 == arg2 && arg1 == TileMessage.anInt7373 && (FixedFunctionSpecularPass.anInt5523 == Class4_Sub6_Sub25.anInt4963 || TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973))) {
			return;
		}
		IDKTypeList.anInt3265 = arg2;
		TileMessage.anInt7373 = arg1;
		FixedFunctionSpecularPass.anInt5523 = Class4_Sub6_Sub25.anInt4963;
		if (TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973)) {
			FixedFunctionSpecularPass.anInt5523 = 0;
		}
		if (arg0) {
			Huffman.method2932(28);
		} else {
			Huffman.method2932(25);
		}
		TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423), Class6.aClass46_9);
		@Pc(59) int local59 = Class50_Sub1.anInt3453;
		Class50_Sub1.anInt3453 = (IDKTypeList.anInt3265 - (GLBillowTextureData.anInt5666 >> 4)) * 8;
		@Pc(70) int local70 = OpenGLTextureCache.anInt1771;
		OpenGLTextureCache.anInt1771 = (TileMessage.anInt7373 - (Static283.anInt5187 >> 4)) * 8;
		TextureOpBlend.aClass4_Sub1_Sub5_2 = Static148.method4494(IDKTypeList.anInt3265 * 8, TileMessage.anInt7373 * 8);
		HookRequest.aClass195_2 = null;
		@Pc(92) int local92 = Class50_Sub1.anInt3453 - local59;
		@Pc(97) int local97 = OpenGLTextureCache.anInt1771 - local70;
		@Pc(107) int local107;
		@Pc(115) int local115;
		if (arg0) {
			Class10.anInt163 = 0;
			local107 = (GLBillowTextureData.anInt5666 - 1) * 128;
			@Pc(113) int local113 = Static283.anInt5187 * 128 - 128;
			for (local115 = 0; local115 < 32768; local115++) {
				@Pc(121) NPCEntity local121 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local115];
				if (local121 != null) {
					local121.anInt6892 -= local97 * 128;
					local121.anInt6893 -= local92 * 128;
					if (local121.anInt6893 >= 0 && local107 >= local121.anInt6893 && local121.anInt6892 >= 0 && local113 >= local121.anInt6892) {
						@Pc(162) boolean local162 = true;
						for (@Pc(164) int local164 = 0; local164 < 10; local164++) {
							local121.movementBaseX[local164] -= local92;
							local121.movementBaseY[local164] -= local97;
							if (local121.movementBaseX[local164] < 0 || GLBillowTextureData.anInt5666 <= local121.movementBaseX[local164] || local121.movementBaseY[local164] < 0 || Static283.anInt5187 <= local121.movementBaseY[local164]) {
								local162 = false;
							}
						}
						if (local162) {
							TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local115;
						} else {
							Class93.aClass16_Sub1_Sub5_Sub2Array1[local115].method4766(null);
							Class93.aClass16_Sub1_Sub5_Sub2Array1[local115] = null;
						}
					} else {
						Class93.aClass16_Sub1_Sub5_Sub2Array1[local115].method4766(null);
						Class93.aClass16_Sub1_Sub5_Sub2Array1[local115] = null;
					}
				}
			}
		} else {
			for (local107 = 0; local107 < 32768; local107++) {
				@Pc(270) NPCEntity local270 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local107];
				if (local270 != null) {
					for (local115 = 0; local115 < 10; local115++) {
						local270.movementBaseX[local115] -= local92;
						local270.movementBaseY[local115] -= local97;
					}
					local270.anInt6892 -= local97 * 128;
					local270.anInt6893 -= local92 * 128;
				}
			}
		}
		for (local107 = 0; local107 < 2048; local107++) {
			@Pc(326) PlayerEntity local326 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local107];
			if (local326 != null) {
				for (local115 = 0; local115 < 10; local115++) {
					local326.movementBaseX[local115] -= local92;
					local326.movementBaseY[local115] -= local97;
				}
				local326.anInt6892 -= local97 * 128;
				local326.anInt6893 -= local92 * 128;
			}
		}
		@Pc(378) Class38[] local378 = Class183.aClass38Array1;
		for (local115 = 0; local115 < local378.length; local115++) {
			@Pc(386) Class38 local386 = local378[local115];
			if (local386 != null) {
				local386.anInt894 -= local97 * 128;
				local386.anInt892 -= local92 * 128;
			}
		}
		Class78_Sub1.method3177(local92, local97);
		for (@Pc(418) Class4_Sub9 local418 = (Class4_Sub9) Class75.aClass183_15.method4140(); local418 != null; local418 = (Class4_Sub9) Class75.aClass183_15.method4144()) {
			local418.anInt790 -= local97;
			local418.anInt793 -= local92;
			if (MapRegion.anInt7307 != 3 && (local418.anInt793 < 0 || local418.anInt790 < 0 || local418.anInt793 >= GLBillowTextureData.anInt5666 || local418.anInt790 >= Static283.anInt5187)) {
				local418.method5684();
			}
		}
		if (Class30.anInt771 != 0) {
			Class30.anInt771 -= local92;
			GlRenderList.anInt1094 -= local97;
		}
		PositionedGfx.method2462();
		if (arg0) {
			Class120.anInt5355 -= local97;
			Class4_Sub23.anInt3197 -= local92 * 128;
			Class3_Sub3.anInt2379 -= local92;
			StillWaterEffect.anInt6924 -= local97;
			Static145.anInt2906 -= local97 * 128;
			Class14.anInt266 -= local92;
			if (Math.abs(local92) > GLBillowTextureData.anInt5666 || Math.abs(local97) > Static283.anInt5187) {
				Class133.method3355();
			}
		} else if (Class3_Sub3.anInt2386 == 4) {
			MusicPatch.anInt5636 -= local97 * 128;
			Js5WorkerThread.anInt7059 -= local92 * 128;
			Class4_Sub45.anInt7360 -= local97 * 128;
			JavaMatrix.anInt6578 -= local92 * 128;
		} else {
			Class3_Sub3.anInt2386 = 1;
		}
		Class70_Sub1.method1816();
		Static277.method3932();
		HookRequest.aClass183_36.method4138();
		Class21.aClass183_2.method4138();
		GameShell.aClass84_4.method2310();
		ParticleType.method90();
	}
@OriginalMember(owner = "client!pp", name = "a", descriptor = "(BI)V")
	public static void method4169(@OriginalArg(1) int arg0) {
		if (arg0 == 37) {
			Static148.requestedMapZoom = 3.0F;
		} else if (arg0 == 50) {
			Static148.requestedMapZoom = 4.0F;
		} else if (arg0 == 75) {
			Static148.requestedMapZoom = 6.0F;
		} else if (arg0 == 100) {
			Static148.requestedMapZoom = 8.0F;
		} else if (arg0 == 200) {
			Static148.requestedMapZoom = 16.0F;
		}
		Class9.anInt6920 = -1;
		Class9.anInt6920 = -1;
	}
@OriginalMember(owner = "client!pp", name = "a", descriptor = "(B)V")
	public static void method4170() {
		if (Static308.aBoolean487) {
			return;
		}
		Static308.aBoolean487 = true;
		BasType.aBoolean429 = true;
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean303) {
			Static368.aFloat192 = (int) Static368.aFloat192 - 65 & 0xFFFFFF80;
		} else {
			Static380.aFloat195 += (-24.0F - Static380.aFloat195) / 2.0F;
		}
	}
}
