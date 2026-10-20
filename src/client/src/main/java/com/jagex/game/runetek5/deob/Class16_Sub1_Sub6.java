package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class170;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
@OriginalClass("client!vb")
public final class Class16_Sub1_Sub6 extends Class16_Sub1 implements GameObject {

	@OriginalMember(owner = "client!vb", name = "E", descriptor = "Z")
	private final boolean aBoolean631;

	@OriginalMember(owner = "client!vb", name = "z", descriptor = "B")
	private final byte aByte84;

	@OriginalMember(owner = "client!vb", name = "K", descriptor = "B")
	private final byte aByte85;

	@OriginalMember(owner = "client!vb", name = "X", descriptor = "S")
	private final short aShort98;

	@OriginalMember(owner = "client!vb", name = "N", descriptor = "Z")
	private boolean aBoolean632;

	@OriginalMember(owner = "client!vb", name = "B", descriptor = "Z")
	private final boolean aBoolean630;

	@OriginalMember(owner = "client!vb", name = "R", descriptor = "B")
	private final byte aByte86;

	@OriginalMember(owner = "client!vb", name = "O", descriptor = "Z")
	private final boolean aBoolean633;

	@OriginalMember(owner = "client!vb", name = "y", descriptor = "Lclient!t;")
	public Model aClass110_7;

	@OriginalMember(owner = "client!vb", name = "T", descriptor = "Lclient!ba;")
	private Shadow aClass4_Sub1_Sub4_6;

	@OriginalMember(owner = "client!vb", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIZIIIIIIZ)V")
	public Class16_Sub1_Sub6(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12, @OriginalArg(13) int arg13, @OriginalArg(14) boolean arg14) {
		super(arg2, arg4, arg5, arg6, arg8, arg9, arg10, arg11, arg1.anInt5818 == 1, QuickchatMessageTypeList.method2257(arg13, arg12));
		this.aBoolean631 = arg1.anInt5813 != 0 && !arg7;
		this.aByte84 = (byte) arg13;
		this.aByte85 = (byte) arg3;
		this.aShort98 = (short) arg1.id;
		this.aBoolean632 = arg14;
		this.aBoolean630 = arg7;
		this.aByte86 = (byte) arg12;
		this.aBoolean633 = arg0.hardShadow() && arg1.aBoolean526 && !this.aBoolean630 && TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) != 0;
		@Pc(80) int local80 = 2048;
		if (this.aBoolean632) {
			local80 |= 0x10000;
		}
		@Pc(95) Class149 local95 = this.method5361(local80, arg0, this.aBoolean633);
		if (local95 != null) {
			this.aClass110_7 = local95.aClass110_5;
			this.aClass4_Sub1_Sub4_6 = local95.aClass4_Sub1_Sub4_4;
			if (this.aBoolean632) {
				this.aClass110_7 = this.aClass110_7.copy((byte) 0, local80, false);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.method5357(arg0, arg1);
	}

	@OriginalMember(owner = "client!vb", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		this.aBoolean632 = false;
		if (this.aClass110_7 != null) {
			this.aClass110_7.UA(this.aClass110_7.P() & 0xFFFEFFFF);
		}
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aBoolean633;
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		if (arg2 instanceof Class16_Sub5_Sub2) {
			@Pc(13) Class16_Sub5_Sub2 local13 = (Class16_Sub5_Sub2) arg2;
			if (this.aClass110_7 != null && local13.aClass110_6 != null) {
				this.aClass110_7.shareLight(local13.aClass110_6, arg1, arg5, arg0, arg3);
			}
		} else if (arg2 instanceof Class16_Sub1_Sub6) {
			@Pc(35) Class16_Sub1_Sub6 local35 = (Class16_Sub1_Sub6) arg2;
			if (this.aClass110_7 != null && local35.aClass110_7 != null) {
				this.aClass110_7.shareLight(local35.aClass110_7, arg1, arg5, arg0, arg3);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!vb", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return this.aClass110_7 == null ? 0 : this.aClass110_7.setOffsets();
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(9) Model local9 = this.method5357(arg1, 131072);
		if (local9 == null) {
			return false;
		} else {
			@Pc(19) Ground local19 = arg1.method4289();
			local19.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
			return local9.picked(arg2, arg0, local19, false);
		}
	}

	@OriginalMember(owner = "client!vb", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
		if (this.aClass110_7 != null) {
			this.aClass110_7.uploadToGpu();
		}
	}

	@OriginalMember(owner = "client!vb", name = "b", descriptor = "(Lclient!ya;II)Lclient!t;")
	private Model method5357(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1) {
		if (this.aClass110_7 != null && arg0.compareFunctionMasks(this.aClass110_7.P(), arg1) == 0) {
			return this.aClass110_7;
		} else {
			@Pc(26) Class149 local26 = this.method5361(arg1, arg0, false);
			return local26 == null ? null : local26.aClass110_5;
		}
	}

	@OriginalMember(owner = "client!vb", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.aClass110_7 == null) {
			return null;
		}
		@Pc(11) Ground local11 = arg0.method4289();
		local11.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(21) Class3_Sub3 local21 = null;
		if (this.aBoolean631) {
			local21 = Class28.method672(1);
		}
		this.aClass110_7.render(local11, local21 == null ? null : local21.aClass3_Sub5Array1[0], 0);
		return local21;
	}

	@OriginalMember(owner = "client!vb", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!vb", name = "g", descriptor = "(I)I")
	public int method5358() {
		return this.aClass110_7 == null ? 15 : this.aClass110_7.U() / 4;
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aByte84;
	}

	@OriginalMember(owner = "client!vb", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aByte86;
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(14) Shadow local14;
		if (this.aClass4_Sub1_Sub4_6 == null && this.aBoolean633) {
			@Pc(25) Class149 local25 = this.method5361(262144, arg0, true);
			local14 = local25 == null ? null : local25.aClass4_Sub1_Sub4_4;
		} else {
			local14 = this.aClass4_Sub1_Sub4_6;
			this.aClass4_Sub1_Sub4_6 = null;
		}
		if (local14 != null) {
			Class38.method844(local14, this.aByte85, super.anInt6893, super.anInt6892, null);
		}
	}

	@OriginalMember(owner = "client!vb", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return this.aBoolean632;
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(14) Shadow local14;
		if (this.aClass4_Sub1_Sub4_6 == null && this.aBoolean633) {
			@Pc(25) Class149 local25 = this.method5361(262144, arg0, true);
			local14 = local25 == null ? null : local25.aClass4_Sub1_Sub4_4;
		} else {
			local14 = this.aClass4_Sub1_Sub4_6;
			this.aClass4_Sub1_Sub4_6 = null;
		}
		if (local14 != null) {
			Class4_Sub17.method2015(local14, this.aByte85, super.anInt6893, super.anInt6892, null);
		}
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(BILclient!ya;Z)Lclient!mg;")
	private Class149 method5361(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) boolean arg2) {
		@Pc(18) LocType local18 = Static267.aClass262_2.method5560(this.aShort98 & 0xFFFF);
		@Pc(43) Class65 local43;
		@Pc(29) Class65 local29;
		if (this.aBoolean630) {
			local29 = Class4_Sub6_Sub4.aClass65Array1[0];
			local43 = FileWorker.aClass65Array3[this.aByte85];
		} else {
			if (this.aByte85 >= 3) {
				local29 = null;
			} else {
				local29 = Class4_Sub6_Sub4.aClass65Array1[this.aByte85 + 1];
			}
			local43 = Class4_Sub6_Sub4.aClass65Array1[this.aByte85];
		}
		return local18.method4589(arg1, super.anInt6893, arg0, super.anInt6889, arg2, this.aByte86 == 11 ? this.aByte84 + 4 : this.aByte84, local29, super.anInt6892, this.aByte86 == 11 ? 10 : this.aByte86, local43);
	}

	@OriginalMember(owner = "client!vb", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aShort98 & 0xFFFF;
	}

@OriginalMember(owner = "client!vb", name = "S", descriptor = "[Lclient!ta;")
	public static Class65[] aClass65Array4;
@OriginalMember(owner = "client!vb", name = "ab", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_98;
@OriginalMember(owner = "client!vb", name = "a", descriptor = "(ILclient!fs;BIZII)V")
	public static void method5355(@OriginalArg(0) int arg0, @OriginalArg(1) Js5ResourceProvider arg1, @OriginalArg(3) int arg2, @OriginalArg(6) int arg3) {
		Class126.anInt3920 = arg0;
		Class21.anInt461 = 1;
		Class16_Sub3.anInt742 = 0;
		Static14.anInt190 = arg3;
		NativeWaterTexture.aClass76_49 = arg1;
		Class6_Sub2.aBoolean30 = false;
		Static47.anInt811 = MapSpriteType.aClass4_Sub15_Sub1_1.method1437() / arg2;
		if (Static47.anInt811 < 1) {
			Static47.anInt811 = 1;
		}
	}
@OriginalMember(owner = "client!vb", name = "a", descriptor = "(IZIIIFII)[I")
	public static int[] method5356(@OriginalArg(5) float arg0) {
		@Pc(11) int[] local11 = new int[2048];
		@Pc(15) TextureOpPerlinNoise local15 = new TextureOpPerlinNoise();
		local15.anInt2706 = 8;
		local15.randomSeed = 35;
		local15.anInt2698 = 4;
		local15.useFogEffect = true;
		local15.anInt2699 = 8;
		local15.anInt2702 = (int) (arg0 * 4096.0F);
		local15.postDecode();
		SoundBankCache.method3974(1, 2048);
		local15.method2283(local11, 0);
		return local11;
	}
@OriginalMember(owner = "client!vb", name = "a", descriptor = "(Ljava/lang/String;ILclient!iv;)I")
	public static int method5359(@OriginalArg(0) String arg0, @OriginalArg(2) Packet arg1) {
		@Pc(6) int local6 = arg1.currentPosition;
		@Pc(12) byte[] local12 = Class170.method3967(arg0);
		arg1.method2543(local12.length);
		arg1.currentPosition += JavaFallbackSurface.aClass119_1.method2935(local12, arg1.currentPosition, 0, arg1.buffer, local12.length);
		return arg1.currentPosition - local6;
	}
}
