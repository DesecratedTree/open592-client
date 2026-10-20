package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class27_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class95;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!er")
public final class Class4_Sub6_Sub8 extends TextureOperation {

	@OriginalMember(owner = "client!er", name = "G", descriptor = "[Lclient!cc;")
	private TextureCombinerOp[] aClass27Array1;

	@OriginalMember(owner = "client!er", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub8() {
		super(0, true);
	}

	@OriginalMember(owner = "client!er", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(23) int local23 = MidiPcmStream.anInt1616;
			@Pc(25) int local25 = EnumTypeList.anInt4939;
			@Pc(29) int[][] local29 = new int[local25][local23];
			@Pc(34) int[][][] local34 = super.colorImageCache.getAllPalettes();
			this.method1699(local29);
			for (@Pc(40) int local40 = 0; local40 < EnumTypeList.anInt4939; local40++) {
				@Pc(46) int[] local46 = local29[local40];
				@Pc(50) int[][] local50 = local34[local40];
				@Pc(54) int[] local54 = local50[0];
				@Pc(58) int[] local58 = local50[1];
				@Pc(62) int[] local62 = local50[2];
				for (@Pc(64) int local64 = 0; local64 < MidiPcmStream.anInt1616; local64++) {
					@Pc(70) int local70 = local46[local64];
					local62[local64] = (local70 & 0xFF) << 4;
					local58[local64] = local70 >> 4 & 0xFF0;
					local54[local64] = local70 >> 12 & 0xFF0;
				}
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!er", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(9) int[] local9 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			this.method1699(super.monochromeImageCache.method2263());
		}
		return local9;
	}

	@OriginalMember(owner = "client!er", name = "a", descriptor = "([[IB)V")
	private void method1699(@OriginalArg(0) int[][] arg0) {
		@Pc(11) int local11 = MidiPcmStream.anInt1616;
		@Pc(13) int local13 = EnumTypeList.anInt4939;
		Class30.method747(arg0);
		Class95.method5012(Class78_Sub1.anInt3883, Static211.anInt3929);
		if (this.aClass27Array1 == null) {
			return;
		}
		for (@Pc(27) int local27 = 0; local27 < this.aClass27Array1.length; local27++) {
			@Pc(34) TextureCombinerOp local34 = this.aClass27Array1[local27];
			@Pc(37) int local37 = local34.anInt5155;
			@Pc(40) int local40 = local34.anInt5152;
			if (local37 < 0) {
				if (local40 >= 0) {
					local34.method3935(local13, local11);
				}
			} else if (local40 >= 0) {
				local34.method3936(local13, local11);
			} else {
				local34.method3939(local13, local11);
			}
		}
	}

	@OriginalMember(owner = "client!er", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.aClass27Array1 = new TextureCombinerOp[arg1.method2490()];
			for (@Pc(41) int local41 = 0; local41 < this.aClass27Array1.length; local41++) {
				@Pc(47) int local47 = arg1.method2490();
				if (local47 == 0) {
					this.aClass27Array1[local41] = BzipDecompressor.method994(arg1);
				} else if (local47 == 1) {
					this.aClass27Array1[local41] = DynamicWall.method5149(arg1);
				} else if (local47 == 2) {
					this.aClass27Array1[local41] = Static78.method1389(arg1);
				} else if (local47 == 3) {
					this.aClass27Array1[local41] = Class99_Sub1.method3235(arg1);
				}
			}
		} else if (arg0 == 1) {
			super.monochrome = arg1.method2490() == 1;
			return;
		}
	}

@OriginalMember(owner = "client!er", name = "C", descriptor = "I")
	public static int anInt1949;
@OriginalMember(owner = "client!er", name = "a", descriptor = "(Lclient!fs;ZLclient!ya;)V")
	public static void method1700(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		@Pc(10) SpriteType[] local10 = SpriteType.decodeGroup(arg0, Class16_Sub5.anInt6553);
		ObjTypeList.aClass57Array14 = new NativeSprite[local10.length];
		for (@Pc(20) int local20 = 0; local20 < local10.length; local20++) {
			ObjTypeList.aClass57Array14[local20] = arg1.generateSprite(local10[local20]);
		}
		local10 = SpriteType.decodeGroup(arg0, JavaMatrix.anInt6593);
		TextureOpInterpolate.aClass57Array8 = new NativeSprite[local10.length];
		for (@Pc(51) int local51 = 0; local51 < local10.length; local51++) {
			TextureOpInterpolate.aClass57Array8[local51] = arg1.generateSprite(local10[local51]);
		}
		local10 = SpriteType.decodeGroup(arg0, Class53.anInt1532);
		Static410.aClass57Array17 = new NativeSprite[local10.length];
		for (@Pc(78) int local78 = 0; local78 < local10.length; local78++) {
			Static410.aClass57Array17[local78] = arg1.generateSprite(local10[local78]);
		}
		local10 = SpriteType.decodeGroup(arg0, PointEntity.anInt4074);
		BillboardType.aClass57Array9 = new NativeSprite[local10.length];
		for (@Pc(105) int local105 = 0; local105 < local10.length; local105++) {
			BillboardType.aClass57Array9[local105] = arg1.generateSprite(local10[local105]);
		}
		local10 = SpriteType.decodeGroup(arg0, Static145.anInt2904);
		Static107.aClass57Array6 = new NativeSprite[local10.length];
		for (@Pc(136) int local136 = 0; local136 < local10.length; local136++) {
			Static107.aClass57Array6[local136] = arg1.generateSprite(local10[local136]);
		}
		local10 = SpriteType.decodeGroup(arg0, Class4_Sub1_Sub8_Sub1.anInt4897);
		TextureOpTiledSprite.aClass57Array3 = new NativeSprite[local10.length];
		for (@Pc(163) int local163 = 0; local163 < local10.length; local163++) {
			TextureOpTiledSprite.aClass57Array3[local163] = arg1.generateSprite(local10[local163]);
		}
		local10 = SpriteType.decodeGroup(arg0, GlElementsArrayBuffer.anInt5213);
		Toolkit.aClass57Array11 = new NativeSprite[local10.length];
		for (@Pc(194) int local194 = 0; local194 < local10.length; local194++) {
			Toolkit.aClass57Array11[local194] = arg1.generateSprite(local10[local194]);
		}
		local10 = SpriteType.decodeGroup(arg0, Class215.anInt6310);
		ReflectingTextureEffect.aClass57Array12 = new NativeSprite[local10.length];
		for (@Pc(221) int local221 = 0; local221 < local10.length; local221++) {
			ReflectingTextureEffect.aClass57Array12[local221] = arg1.generateSprite(local10[local221]);
		}
		local10 = SpriteType.decodeGroup(arg0, PlayerEntity.anInt4342);
		TextureOpBoxBlur.aClass57Array13 = new NativeSprite[local10.length];
		for (@Pc(248) int local248 = 0; local248 < local10.length; local248++) {
			TextureOpBoxBlur.aClass57Array13[local248] = arg1.generateSprite(local10[local248]);
		}
		local10 = SpriteType.decodeGroup(arg0, Class27_Sub1.anInt649);
		MonochromeImageCache.aClass57Array7 = new NativeSprite[local10.length];
		for (@Pc(275) int local275 = 0; local275 < local10.length; local275++) {
			MonochromeImageCache.aClass57Array7[local275] = arg1.generateSprite(local10[local275]);
		}
		local10 = SpriteType.decodeGroup(arg0, Model.anInt6286);
		Class4_Sub6_Sub5.aClass57Array4 = new NativeSprite[local10.length];
		for (@Pc(302) int local302 = 0; local302 < local10.length; local302++) {
			Class4_Sub6_Sub5.aClass57Array4[local302] = arg1.generateSprite(local10[local302]);
		}
		local10 = SpriteType.decodeGroup(arg0, ShadowTileGL.anInt3910);
		PingWorker.aClass57Array18 = new NativeSprite[local10.length];
		for (@Pc(333) int local333 = 0; local333 < local10.length; local333++) {
			PingWorker.aClass57Array18[local333] = arg1.generateSprite(local10[local333]);
		}
		Class4_Sub23.aClass57_16 = arg1.generateSprite(SpriteType.listSingle(arg0, JavaFallbackSurface.anInt5870, 0));
		TextureOpVoronoiNoise.aClass57_22 = arg1.generateSprite(SpriteType.listSingle(arg0, Static410.anInt6733, 0));
		local10 = SpriteType.decodeGroup(arg0, PlayerVarDomain.anInt3393);
		Class186.aClass57Array10 = new NativeSprite[local10.length];
		for (@Pc(376) int local376 = 0; local376 < local10.length; local376++) {
			Class186.aClass57Array10[local376] = arg1.generateSprite(local10[local376]);
		}
	}
@OriginalMember(owner = "client!er", name = "a", descriptor = "(Lclient!fs;I)I")
	public static int method1701(@OriginalArg(0) Js5ResourceProvider arg0) {
		@Pc(5) int local5 = 0;
		if (arg0.method2125(LocType.anInt5846)) {
			local5++;
		}
		if (arg0.method2125(Class217.anInt6320)) {
			local5++;
		}
		return local5;
	}
}
