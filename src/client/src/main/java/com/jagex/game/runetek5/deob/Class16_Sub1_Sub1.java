package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.js5.request.Js5WorkerRequestMessage;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!av")
public final class Class16_Sub1_Sub1 extends Class16_Sub1 implements GameObject {

	@OriginalMember(owner = "client!av", name = "H", descriptor = "Lclient!pe;")
	public Class16_Sub1 aClass16_Sub1_2;

	@OriginalMember(owner = "client!av", name = "<init>", descriptor = "(IIIIIIIIIILclient!pe;)V")
	public Class16_Sub1_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) Class16_Sub1 arg10) {
		super(arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, false, Js5WorkerRequestMessage.method3341(arg1, arg0));
		this.aClass16_Sub1_2 = arg10;
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return false;
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		return false;
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return 0;
	}

	@OriginalMember(owner = "client!av", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return 0;
	}

	@OriginalMember(owner = "client!av", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return 0;
	}

	@OriginalMember(owner = "client!av", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		return null;
	}

	@OriginalMember(owner = "client!av", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!av", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return null;
	}

	@OriginalMember(owner = "client!av", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
	}

	@OriginalMember(owner = "client!av", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!av", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return 0;
	}

@OriginalMember(owner = "client!av", name = "O", descriptor = "[Z")
	public static boolean[] aBooleanArray3;
@OriginalMember(owner = "client!av", name = "W", descriptor = "Lclient!la;")
	public static AbstractFontRenderer aClass46_4;
@OriginalMember(owner = "client!av", name = "D", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_13 = new Class83("Loading config - ", "Lade Konfiguration - ", "Chargement des fichiers config - ", "Carregando config - ");
@OriginalMember(owner = "client!av", name = "S", descriptor = "J")
	public static long aLong17 = 0L;
@OriginalMember(owner = "client!av", name = "V", descriptor = "Z")
	public static boolean aBoolean38 = false;
@OriginalMember(owner = "client!av", name = "e", descriptor = "(B)V")
	public static void method293() {
		if (Class9_Sub3.anIntArray115 != null) {
			return;
		}
		Class9_Sub3.anIntArray115 = new int[65536];
		@Pc(19) double local19 = Math.random() * 0.03D + 0.7D - 0.015D;
		for (@Pc(29) int local29 = 0; local29 < 65536; local29++) {
			@Pc(42) double local42 = (double) (local29 >> 10 & 0x3F) / 64.0D + 0.0078125D;
			@Pc(53) double local53 = (double) (local29 >> 7 & 0x7) / 8.0D + 0.0625D;
			@Pc(60) double local60 = (double) (local29 & 0x7F) / 128.0D;
			@Pc(62) double local62 = local60;
			@Pc(64) double local64 = local60;
			@Pc(66) double local66 = local60;
			if (local53 != 0.0D) {
				@Pc(80) double local80;
				if (local60 < 0.5D) {
					local80 = local60 * (local53 + 1.0D);
				} else {
					local80 = local53 + local60 - local60 * local53;
				}
				@Pc(97) double local97 = local60 * 2.0D - local80;
				@Pc(101) double local101 = local42 + 0.3333333333333333D;
				if (local101 > 1.0D) {
					local101--;
				}
				@Pc(115) double local115 = local42 - 0.3333333333333333D;
				if (local115 < 0.0D) {
					local115++;
				}
				if (local42 * 6.0D < 1.0D) {
					local64 = local42 * 6.0D * (local80 - local97) + local97;
				} else if (local42 * 2.0D < 1.0D) {
					local64 = local80;
				} else if (local42 * 3.0D < 2.0D) {
					local64 = local97 + (local80 - local97) * 6.0D * (0.6666666666666666D - local42);
				} else {
					local64 = local97;
				}
				if (local101 * 6.0D < 1.0D) {
					local62 = local97 + local101 * (local80 - local97) * 6.0D;
				} else if (local101 * 2.0D < 1.0D) {
					local62 = local80;
				} else if (local101 * 3.0D < 2.0D) {
					local62 = local97 + (local80 - local97) * (-local101 + 0.6666666666666666D) * 6.0D;
				} else {
					local62 = local97;
				}
				if (local115 * 6.0D < 1.0D) {
					local66 = (local80 - local97) * 6.0D * local115 + local97;
				} else if (local115 * 2.0D < 1.0D) {
					local66 = local80;
				} else if (local115 * 3.0D < 2.0D) {
					local66 = local97 + (0.6666666666666666D - local115) * 6.0D * (local80 - local97);
				} else {
					local66 = local97;
				}
			}
			local62 = Math.pow(local62, local19);
			local64 = Math.pow(local64, local19);
			local66 = Math.pow(local66, local19);
			@Pc(296) int local296 = (int) (local62 * 256.0D);
			@Pc(301) int local301 = (int) (local64 * 256.0D);
			@Pc(306) int local306 = (int) (local66 * 256.0D);
			@Pc(317) int local317 = local306 + (local296 << 16) + (local301 << 8);
			Class9_Sub3.anIntArray115[local29] = local317;
		}
	}
@OriginalMember(owner = "client!av", name = "a", descriptor = "(IILclient!ya;ILclient!uu;ILclient!ma;II)V")
	public static void method294(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2, @OriginalArg(4) com.jagex.game.runetek5.config.rsinterface.Component arg3, @OriginalArg(5) int arg4, @OriginalArg(6) ComponentSurface arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7) {
		@Pc(10) MECType local10 = Class4_Sub6_Sub31.aClass182_4.method4124(arg2);
		if (local10 == null || !local10.aBoolean450 || !local10.method3778(Class4_Sub6_Sub24.aClass114_1)) {
			return;
		}
		@Pc(34) int local34;
		if (local10.offsets != null) {
			@Pc(32) int[] local32 = new int[local10.offsets.length];
			@Pc(44) int local44;
			for (local34 = 0; local34 < local32.length / 2; local34++) {
				if (Class3_Sub3.anInt2386 == 4) {
					local44 = (int) Static368.aFloat192 & 0x3FFF;
				} else {
					local44 = (int) Static368.aFloat192 + PlayerVarDomain.anInt3399 & 0x3FFF;
				}
				@Pc(58) int local58 = Class50_Sub1.anIntArray225[local44];
				@Pc(62) int local62 = Class50_Sub1.anIntArray224[local44];
				if (Class3_Sub3.anInt2386 != 4) {
					local62 = local62 * 256 / (Class4_Sub17.anInt2374 + 256);
					local58 = local58 * 256 / (Class4_Sub17.anInt2374 + 256);
				}
				local32[local34 * 2] = arg3.anInt6833 / 2 + arg6 + ((arg0 + local10.offsets[local34 * 2] * 4) * local62 + local58 * (local10.offsets[local34 * 2 + 1] * 4 + arg4) >> 15);
				local32[local34 * 2 + 1] = arg7 + arg3.anInt6805 / 2 - ((arg4 + local10.offsets[local34 * 2 + 1] * 4) * local62 - (arg0 + local10.offsets[local34 * 2] * 4) * local58 >> 15);
			}
			Static144.fillPolygonMasked(arg1, local32, local10.anInt4927, arg3.anIntArray524, arg3.anIntArray519);
			for (local44 = 0; local44 < local32.length / 2 - 1; local44++) {
				arg1.drawSurfaceLine(local32[local44 * 2], local32[local44 * 2 + 1], local32[local44 * 2 + 2], local32[(local44 + 1) * 2 + 1], local10.anInt4908, arg5, arg6, arg7);
			}
			arg1.drawSurfaceLine(local32[local32.length - 2], local32[local32.length - 1], local32[0], local32[1], local10.anInt4908, arg5, arg6, arg7);
		}
		@Pc(262) NativeSprite local262 = null;
		if (local10.anInt4930 != -1) {
			local262 = local10.method3785(arg1, false);
			if (local262 != null) {
				NPCType.method5585(arg6, local262, arg3, arg5, arg4, arg0, arg7);
			}
		}
		if (local10.aString47 == null) {
			return;
		}
		local34 = 0;
		if (local262 != null) {
			local34 = local262.getHeight();
		}
		@Pc(296) AbstractFontRenderer local296 = Ground.aClass46_12;
		@Pc(298) com.jagex.game.runetek5.graphics.font.FontMetrics local298 = Class229.aClass89_10;
		if (local10.anInt4904 == 1) {
			local296 = Class6.aClass46_9;
			local298 = ReflectionCheck.aClass89_1;
		}
		if (local10.anInt4904 == 2) {
			local296 = Static331.aClass46_10;
			local298 = Js5ResourceProvider.aClass89_5;
		}
		PathingEntitySequence.method3930(arg6, local10.aString47, arg0, local34, local296, arg7, arg3, arg4, local10.anInt4926, local298, arg5);
		return;
	}
@OriginalMember(owner = "client!av", name = "a", descriptor = "(II)V")
	public static void method296(@OriginalArg(1) int arg0) {
		ObjTypeList.anInt5986 = arg0;
		Class111.aClass68_18.method1777();
	}
@OriginalMember(owner = "client!av", name = "a", descriptor = "(Ljava/lang/String;B)Ljava/lang/String;")
	public static String method297(@OriginalArg(0) String arg0) {
		@Pc(8) int local8 = arg0.length();
		@Pc(10) int local10 = 0;
		for (@Pc(17) int local17 = 0; local17 < local8; local17++) {
			@Pc(23) char local23 = arg0.charAt(local17);
			if (local23 == '<' || local23 == '>') {
				local10 += 3;
			}
		}
		@Pc(45) StringBuffer local45 = new StringBuffer(local10 + local8);
		for (@Pc(47) int local47 = 0; local47 < local8; local47++) {
			@Pc(53) char local53 = arg0.charAt(local47);
			if (local53 == '<') {
				local45.append("<lt>");
			} else if (local53 == '>') {
				local45.append("<gt>");
			} else {
				local45.append(local53);
			}
		}
		return local45.toString();
	}
@OriginalMember(owner = "client!av", name = "h", descriptor = "(I)Ljava/lang/String;")
	public static String method298() {
		@Pc(12) String local12 = "www";
		if (client.aClass219_1 == GlToolkit.aClass219_2) {
			local12 = "www-wtrc";
		} else if (Class3_Sub4.aClass219_3 == GlToolkit.aClass219_2) {
			local12 = "www-wtqa";
		} else if (GlToolkit.aClass219_2 == MaterialType.aClass219_7) {
			local12 = "www-wtwip";
		}
		@Pc(33) String local33 = "";
		if (ReflectionCheck.aString13 != null) {
			local33 = "/p=" + ReflectionCheck.aString13;
		}
		return "http://" + local12 + "." + DynamicWall.aClass121_3.aString36 + ".com/l=" + NativeBillboardFace.anInt3423 + "/a=" + GlCubeTexture.anInt3259 + local33 + "/";
	}
}
