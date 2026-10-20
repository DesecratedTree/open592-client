package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.client.input.keyboard.CanvasKeyRecorder;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.rsinterface.Component;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!wd")
public final class Resampler {

	@OriginalMember(owner = "client!wd", name = "a", descriptor = "I")
	private int anInt7171;

	@OriginalMember(owner = "client!wd", name = "m", descriptor = "[[I")
	private int[][] anIntArrayArray60;

	@OriginalMember(owner = "client!wd", name = "f", descriptor = "I")
	private int anInt7176;

	@OriginalMember(owner = "client!wd", name = "<init>", descriptor = "(II)V")
	public Resampler(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (arg0 != arg1) {
			@Pc(12) int local12 = CanvasKeyRecorder.method5493(arg0, arg1);
			@Pc(16) int local16 = arg0 / local12;
			@Pc(20) int local20 = arg1 / local12;
			this.anInt7171 = local20;
			this.anIntArrayArray60 = new int[local16][14];
			this.anInt7176 = local16;
			for (@Pc(33) int local33 = 0; local33 < local16; local33++) {
				@Pc(40) int[] local40 = this.anIntArrayArray60[local33];
				@Pc(48) double local48 = (double) local33 / (double) local16 + 6.0D;
				@Pc(56) int local56 = (int) Math.floor(local48 + 1.0D - 7.0D);
				if (local56 < 0) {
					local56 = 0;
				}
				@Pc(66) int local66 = (int) Math.ceil(local48 + 7.0D);
				if (local66 > 14) {
					local66 = 14;
				}
				@Pc(77) double local77 = (double) local20 / (double) local16;
				while (local66 > local56) {
					@Pc(86) double local86 = ((double) local56 - local48) * 3.141592653589793D;
					@Pc(88) double local88 = local77;
					if (local86 < -1.0E-4D || local86 > 1.0E-4D) {
						local88 = local77 * (Math.sin(local86) / local86);
					}
					local88 *= Math.cos(((double) local56 - local48) * 0.2243994752564138D) * 0.46D + 0.54D;
					local40[local56] = (int) Math.floor(local88 * 65536.0D + 0.5D);
					local56++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!wd", name = "a", descriptor = "(IB)I")
	public int method5550(@OriginalArg(0) int arg0) {
		if (this.anIntArrayArray60 != null) {
			arg0 = (int) ((long) arg0 * (long) this.anInt7171 / (long) this.anInt7176) + 6;
		}
		return arg0;
	}

	@OriginalMember(owner = "client!wd", name = "a", descriptor = "([BI)[B")
	public byte[] method5551(@OriginalArg(0) byte[] arg0) {
		if (this.anIntArrayArray60 != null) {
			@Pc(29) int local29 = (int) ((long) this.anInt7171 * (long) arg0.length / (long) this.anInt7176) + 14;
			@Pc(32) int[] local32 = new int[local29];
			@Pc(34) int local34 = 0;
			@Pc(36) int local36 = 0;
			for (@Pc(38) int local38 = 0; local38 < arg0.length; local38++) {
				@Pc(44) byte local44 = arg0[local38];
				@Pc(49) int[] local49 = this.anIntArrayArray60[local36];
				for (@Pc(51) int local51 = 0; local51 < 14; local51++) {
					local32[local34 + local51] += local49[local51] * local44;
				}
				local36 += this.anInt7171;
				@Pc(80) int local80 = local36 / this.anInt7176;
				local34 += local80;
				local36 -= local80 * this.anInt7176;
			}
			arg0 = new byte[local29];
			for (@Pc(101) int local101 = 0; local101 < local29; local101++) {
				@Pc(111) int local111 = local32[local101] + 32768 >> 16;
				if (local111 < -128) {
					arg0[local101] = -128;
				} else if (local111 > 127) {
					arg0[local101] = 127;
				} else {
					arg0[local101] = (byte) local111;
				}
			}
		}
		return arg0;
	}

	@OriginalMember(owner = "client!wd", name = "a", descriptor = "(II)I")
	public int method5552(@OriginalArg(1) int arg0) {
		if (this.anIntArrayArray60 != null) {
			arg0 = (int) ((long) this.anInt7171 * (long) arg0 / (long) this.anInt7176);
		}
		return arg0;
	}

@OriginalMember(owner = "client!wd", name = "i", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_148 = new Class83("Loading - please wait.", "Ladevorgang - bitte warte.", "Chargement en cours. Veuillez patienter.", "Carregando. Aguarde.");
@OriginalMember(owner = "client!wd", name = "l", descriptor = "Z")
	public static boolean aBoolean676 = true;
@OriginalMember(owner = "client!wd", name = "a", descriptor = "(IIII)V")
	public static void method5547(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		ParticleNode.aClass4_Sub12_Sub1_5.method2531(arg0);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(arg1);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(arg2);
	}
@OriginalMember(owner = "client!wd", name = "a", descriptor = "(IIIIIIB)V")
	public static void method5548(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		@Pc(11) int local11 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg0, Model.anInt6287);
		@Pc(17) int local17 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg5, Model.anInt6287);
		@Pc(23) int local23 = Js5GroupLoader.method4519(Class36.anInt2656, arg2, ItemType.anInt6186);
		@Pc(29) int local29 = Js5GroupLoader.method4519(Class36.anInt2656, arg3, ItemType.anInt6186);
		@Pc(38) int local38 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg0 + arg4, Model.anInt6287);
		@Pc(47) int local47 = Js5GroupLoader.method4519(GlCubeTexture.anInt3256, arg5 - arg4, Model.anInt6287);
		for (@Pc(49) int local49 = local11; local49 < local38; local49++) {
			BasType.method3640(local23, arg1, local29, Class4_Sub6_Sub7.anIntArrayArray21[local49]);
		}
		for (@Pc(69) int local69 = local17; local69 > local47; local69--) {
			BasType.method3640(local23, arg1, local29, Class4_Sub6_Sub7.anIntArrayArray21[local69]);
		}
		@Pc(92) int local92 = Js5GroupLoader.method4519(Class36.anInt2656, arg2 + arg4, ItemType.anInt6186);
		@Pc(101) int local101 = Js5GroupLoader.method4519(Class36.anInt2656, arg3 - arg4, ItemType.anInt6186);
		for (@Pc(111) int local111 = local38; local111 <= local47; local111++) {
			@Pc(117) int[] local117 = Class4_Sub6_Sub7.anIntArrayArray21[local111];
			BasType.method3640(local23, arg1, local92, local117);
			BasType.method3640(local101, arg1, local29, local117);
		}
	}
@OriginalMember(owner = "client!wd", name = "a", descriptor = "(Ljava/lang/String;I)V")
	public static void method5549(@OriginalArg(0) String arg0) {
		if (arg0 == null) {
			return;
		}
		if (arg0.startsWith("*")) {
			arg0 = arg0.substring(1);
		}
		@Pc(20) String local20 = Class79.method4868(arg0);
		if (local20 == null) {
			return;
		}
		for (@Pc(25) int local25 = 0; local25 < Component.anInt6823; local25++) {
			@Pc(31) String local31 = GLBillowTextureData.aStringArray27[local25];
			if (local31.startsWith("*")) {
				local31 = local31.substring(1);
			}
			local31 = Class79.method4868(local31);
			if (local31 != null && local31.equals(local20)) {
				Component.anInt6823--;
				for (@Pc(55) int local55 = local25; local55 < Component.anInt6823; local55++) {
					GLBillowTextureData.aStringArray27[local55] = GLBillowTextureData.aStringArray27[local55 + 1];
					Static102.aStringArray7[local55] = Static102.aStringArray7[local55 + 1];
					Class4_Sub1_Sub17.aStringArray26[local55] = Class4_Sub1_Sub17.aStringArray26[local55 + 1];
					GpuPointLight.aStringArray11[local55] = GpuPointLight.aStringArray11[local55 + 1];
					EffectManagerGL.aBooleanArray20[local55] = EffectManagerGL.aBooleanArray20[local55 + 1];
				}
				Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
				PingWorker.method5476(TextureOpColorGradient.aClass215_19);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(arg0));
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
				break;
			}
		}
	}
@OriginalMember(owner = "client!wd", name = "a", descriptor = "(BLjava/lang/String;)V")
	public static void method5553(@OriginalArg(1) String arg0) {
		ReflectionCheck.aString13 = arg0;
		if (Class126.aClass66_4.anApplet1 == null) {
			return;
		}
		try {
			@Pc(18) String local18 = Class126.aClass66_4.anApplet1.getParameter("cookieprefix");
			@Pc(23) String local23 = Class126.aClass66_4.anApplet1.getParameter("cookiehost");
			@Pc(38) String local38 = local18 + "settings=" + arg0 + "; version=1; path=/; domain=" + local23;
			if (arg0.length() == 0) {
				local38 = local38 + "; Expires=Thu, 01-Jan-1970 00:00:00 GMT; Max-Age=0";
			} else {
				local38 = local38 + "; Expires=" + GlModel.method2805(Class168.method3962() + 94608000000L) + "; Max-Age=" + 94608000L;
			}
			Static458.method2592("document.cookie=\"" + local38 + "\"", Class126.aClass66_4.anApplet1);
		} catch (@Pc(87) Throwable local87) {
		}
	}
@OriginalMember(owner = "client!wd", name = "a", descriptor = "(Lclient!fs;Lclient!dv;Lclient!fs;Lclient!fs;B)Z")
	public static boolean method5554(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(1) MidiPcmStream arg1, @OriginalArg(2) Js5ResourceProvider arg2, @OriginalArg(3) Js5ResourceProvider arg3) {
		Class4_Sub6_Sub16.aClass76_43 = arg3;
		StructType.aClass76_75 = arg2;
		FluType.aClass76_85 = arg0;
		MapSpriteType.aClass4_Sub15_Sub1_1 = arg1;
		return true;
	}
}
