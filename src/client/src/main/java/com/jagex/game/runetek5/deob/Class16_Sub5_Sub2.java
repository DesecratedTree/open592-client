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

import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!rj")
public final class Class16_Sub5_Sub2 extends Class16_Sub5 implements GameObject {

	@OriginalMember(owner = "client!rj", name = "L", descriptor = "S")
	private final short aShort74;

	@OriginalMember(owner = "client!rj", name = "U", descriptor = "Z")
	private final boolean aBoolean550;

	@OriginalMember(owner = "client!rj", name = "W", descriptor = "B")
	private final byte aByte65;

	@OriginalMember(owner = "client!rj", name = "q", descriptor = "Z")
	private boolean aBoolean546;

	@OriginalMember(owner = "client!rj", name = "r", descriptor = "B")
	private final byte aByte63;

	@OriginalMember(owner = "client!rj", name = "v", descriptor = "Z")
	private final boolean aBoolean547;

	@OriginalMember(owner = "client!rj", name = "u", descriptor = "B")
	private final byte aByte64;

	@OriginalMember(owner = "client!rj", name = "H", descriptor = "Z")
	private final boolean aBoolean548;

	@OriginalMember(owner = "client!rj", name = "Q", descriptor = "Lclient!ba;")
	private Shadow aClass4_Sub1_Sub4_5;

	@OriginalMember(owner = "client!rj", name = "D", descriptor = "Lclient!t;")
	public Model aClass110_6;

	@OriginalMember(owner = "client!rj", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIZIIZ)V")
	public Class16_Sub5_Sub2(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) boolean arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) boolean arg9) {
		super(arg3, arg4, arg5, Class4_Sub1_Sub9.method2570(arg7, arg8));
		this.aShort74 = (short) arg1.id;
		super.anInt6550 = arg3;
		this.aBoolean550 = arg1.anInt5813 != 0 && !arg6;
		this.aByte65 = (byte) arg2;
		this.aBoolean546 = arg9;
		this.aByte63 = (byte) arg8;
		super.anInt6552 = arg5;
		this.aBoolean547 = arg6;
		this.aByte64 = (byte) arg7;
		this.aBoolean548 = arg0.hardShadow() && arg1.aBoolean526 && !this.aBoolean547 && TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) != 0;
		@Pc(68) int local68 = 2048;
		if (this.aBoolean546) {
			local68 |= 0x10000;
		}
		@Pc(83) Class149 local83 = this.method4653(local68, arg0, this.aBoolean548);
		if (local83 != null) {
			this.aClass4_Sub1_Sub4_5 = local83.aClass4_Sub1_Sub4_4;
			this.aClass110_6 = local83.aClass110_5;
			if (this.aBoolean546) {
				this.aClass110_6 = this.aClass110_6.copy((byte) 0, local68, false);
				return;
			}
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(21) Shadow local21;
		if (this.aClass4_Sub1_Sub4_5 == null && this.aBoolean548) {
			@Pc(34) Class149 local34 = this.method4653(262144, arg0, true);
			local21 = local34 == null ? null : local34.aClass4_Sub1_Sub4_4;
		} else {
			local21 = this.aClass4_Sub1_Sub4_5;
			this.aClass4_Sub1_Sub4_5 = null;
		}
		if (local21 != null) {
			Class38.method844(local21, this.aByte65, super.anInt6550, super.anInt6552, null);
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(IILclient!ya;Z)Lclient!mg;")
	private Class149 method4653(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) boolean arg2) {
		@Pc(13) LocType local13 = Static267.aClass262_2.method5560(this.aShort74 & 0xFFFF);
		@Pc(44) Class65 local44;
		@Pc(30) Class65 local30;
		if (this.aBoolean547) {
			local30 = Class4_Sub6_Sub4.aClass65Array1[0];
			local44 = FileWorker.aClass65Array3[this.aByte65];
		} else {
			if (this.aByte65 >= 3) {
				local30 = null;
			} else {
				local30 = Class4_Sub6_Sub4.aClass65Array1[this.aByte65 + 1];
			}
			local44 = Class4_Sub6_Sub4.aClass65Array1[this.aByte65];
		}
		return local13.method4589(arg1, super.anInt6550, arg0, super.anInt6554, arg2, this.aByte63, local30, super.anInt6552, this.aByte64, local44);
	}

	@OriginalMember(owner = "client!rj", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return this.aBoolean546;
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aByte63;
	}

	@OriginalMember(owner = "client!rj", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.method4657(arg1, arg0);
	}

	@OriginalMember(owner = "client!rj", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		this.aBoolean546 = false;
		if (this.aClass110_6 != null) {
			this.aClass110_6.UA(this.aClass110_6.P() & 0xFFFEFFFF);
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		if (arg2 instanceof Class16_Sub5_Sub2) {
			@Pc(5) Class16_Sub5_Sub2 local5 = (Class16_Sub5_Sub2) arg2;
			if (this.aClass110_6 != null && local5.aClass110_6 != null) {
				this.aClass110_6.shareLight(local5.aClass110_6, arg1, arg5, arg0, arg3);
			}
		} else if (arg2 instanceof Class16_Sub1_Sub6) {
			@Pc(28) Class16_Sub1_Sub6 local28 = (Class16_Sub1_Sub6) arg2;
			if (this.aClass110_6 != null && local28.aClass110_7 != null) {
				this.aClass110_6.shareLight(local28.aClass110_7, arg1, arg5, arg0, arg3);
			}
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aShort74 & 0xFFFF;
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(BILclient!ya;)Lclient!t;")
	private Model method4657(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		if (this.aClass110_6 != null && arg1.compareFunctionMasks(this.aClass110_6.P(), arg0) == 0) {
			return this.aClass110_6;
		} else {
			@Pc(33) Class149 local33 = this.method4653(arg0, arg1, false);
			return local33 == null ? null : local33.aClass110_5;
		}
	}

	@OriginalMember(owner = "client!rj", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.aClass110_6 == null) {
			return null;
		}
		@Pc(11) Ground local11 = arg0.method4289();
		local11.scaleWidth(super.anInt6550, super.anInt6554, super.anInt6552);
		@Pc(26) Class3_Sub3 local26 = null;
		if (this.aBoolean550) {
			local26 = Class28.method672(1);
		}
		this.aClass110_6.render(local11, local26 == null ? null : local26.aClass3_Sub5Array1[0], 0);
		return local26;
	}

	@OriginalMember(owner = "client!rj", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aByte64;
	}

	@OriginalMember(owner = "client!rj", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
		if (this.aClass110_6 != null) {
			this.aClass110_6.uploadToGpu();
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(13) Model local13 = this.method4657(131072, arg1);
		if (local13 == null) {
			return false;
		} else {
			@Pc(18) Ground local18 = arg1.method4289();
			local18.scaleWidth(super.anInt6550, super.anInt6554, super.anInt6552);
			return local13.picked(arg2, arg0, local18, false);
		}
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aBoolean548;
	}

	@OriginalMember(owner = "client!rj", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(14) Shadow local14;
		if (this.aClass4_Sub1_Sub4_5 == null && this.aBoolean548) {
			@Pc(25) Class149 local25 = this.method4653(262144, arg0, true);
			local14 = local25 == null ? null : local25.aClass4_Sub1_Sub4_4;
		} else {
			local14 = this.aClass4_Sub1_Sub4_5;
			this.aClass4_Sub1_Sub4_5 = null;
		}
		if (local14 != null) {
			Class4_Sub17.method2015(local14, this.aByte65, super.anInt6550, super.anInt6552, null);
		}
	}

@OriginalMember(owner = "client!rj", name = "O", descriptor = "F")
	public static float aFloat184;
@OriginalMember(owner = "client!rj", name = "z", descriptor = "I")
	public static int anInt5913 = 0;
@OriginalMember(owner = "client!rj", name = "B", descriptor = "[I")
	public static final int[] anIntArray414 = new int[3];
@OriginalMember(owner = "client!rj", name = "N", descriptor = "Z")
	public static boolean aBoolean549 = false;
@OriginalMember(owner = "client!rj", name = "a", descriptor = "(BLclient!wn;[[B)V")
	public static void method4655(@OriginalArg(1) MapRegion arg0, @OriginalArg(2) byte[][] arg1) {
		@Pc(15) int local15 = Class99_Sub1.aByteArrayArray15.length;
		for (@Pc(17) int local17 = 0; local17 < local15; local17++) {
			@Pc(23) byte[] local23 = arg1[local17];
			if (local23 != null) {
				@Pc(35) int local35 = (Static308.anIntArray372[local17] >> 8) * 64 - Class50_Sub1.anInt3453;
				@Pc(46) int local46 = (Static308.anIntArray372[local17] & 0xFF) * 64 - OpenGLTextureCache.anInt1771;
				CubeMapSpecularEffect.method2701();
				arg0.method5646(PlayerVarDomain.aClass213Array1, local46, local23, local35, MonoStereoData.aClass19_16);
			}
		}
	}
@OriginalMember(owner = "client!rj", name = "a", descriptor = "(III)Z")
	public static boolean method4656(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x70000) != 0 | Class58.method1510(arg1, arg0) || Class164.method3914(arg0, arg1);
	}
@OriginalMember(owner = "client!rj", name = "b", descriptor = "(III)V")
	public static void method4658(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(13) int local13 = Js5ResourceProvider.aClass89_5.getWidthNoSprites(Static391.aClass83_133.method2267(NativeBillboardFace.anInt3423));
		@Pc(24) int local24;
		for (@Pc(18) OggOtherStream local18 = (OggOtherStream) Class126.aClass183_28.method4140(); local18 != null; local18 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			local24 = JavaBillboardFace.method3197(local18);
			if (local13 < local24) {
				local13 = local24;
			}
		}
		local13 += 8;
		local24 = Skybox.anInt6710 * 16 + 21;
		@Pc(54) int local54 = arg1 - local13 / 2;
		if (GpuPointLight.anInt2881 < local13 + local54) {
			local54 = GpuPointLight.anInt2881 - local13;
		}
		if (local54 < 0) {
			local54 = 0;
		}
		@Pc(73) int local73 = arg0;
		if (EffectiveVertex.anInt5346 < arg0 + local24) {
			local73 = EffectiveVertex.anInt5346 - local24;
		}
		Class4_Sub37.anInt5424 = local54;
		if (local73 < 0) {
			local73 = 0;
		}
		Class4_Sub6_Sub22.aBoolean411 = true;
		Class111.anInt3367 = local73;
		Static173.anInt3370 = local13;
		Class4_Sub18.anInt2392 = (Class177.aBoolean481 ? 26 : 22) + Skybox.anInt6710 * 16;
	}
}
