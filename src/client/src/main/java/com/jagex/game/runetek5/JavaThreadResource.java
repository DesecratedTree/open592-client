package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class31_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Static340;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.JavaModel;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class223;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
@OriginalClass("client!cv")
public final class JavaThreadResource {

	@OriginalMember(owner = "client!cv", name = "f", descriptor = "Ljava/lang/Runnable;")
	public Runnable thread;

	@OriginalMember(owner = "client!cv", name = "A", descriptor = "I")
	public int anInt1118;

	@OriginalMember(owner = "client!cv", name = "F", descriptor = "I")
	public int anInt1119;

	@OriginalMember(owner = "client!cv", name = "O", descriptor = "I")
	public int anInt1120;

	@OriginalMember(owner = "client!cv", name = "c", descriptor = "Lclient!wp;")
	public final Class31_Sub2 aClass31_Sub2_2 = new Class31_Sub2();

	@OriginalMember(owner = "client!cv", name = "q", descriptor = "[I")
	public final int[] anIntArray72 = new int[8191];

	@OriginalMember(owner = "client!cv", name = "o", descriptor = "[I")
	public final int[] anIntArray70 = new int[2200];

	@OriginalMember(owner = "client!cv", name = "g", descriptor = "[Z")
	public final boolean[] aBooleanArray7 = new boolean[JavaModel.anInt6301];

	@OriginalMember(owner = "client!cv", name = "y", descriptor = "[I")
	public final int[] anIntArray74 = new int[12];

	@OriginalMember(owner = "client!cv", name = "B", descriptor = "[I")
	public final int[] anIntArray76 = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "l", descriptor = "[I")
	public final int[] anIntArray68 = new int[8191];

	@OriginalMember(owner = "client!cv", name = "D", descriptor = "[I")
	public final int[] anIntArray77 = new int[8191];

	@OriginalMember(owner = "client!cv", name = "H", descriptor = "[I")
	public final int[] anIntArray79 = new int[10];

	@OriginalMember(owner = "client!cv", name = "z", descriptor = "[I")
	public final int[] anIntArray75 = new int[64];

	@OriginalMember(owner = "client!cv", name = "s", descriptor = "[[I")
	public final int[][] anIntArrayArray14 = new int[32][512];

	@OriginalMember(owner = "client!cv", name = "p", descriptor = "[I")
	public final int[] anIntArray71 = new int[64];

	@OriginalMember(owner = "client!cv", name = "N", descriptor = "[I")
	public final int[] anIntArray84 = new int[JavaModel.anInt6301 + 8191];

	@OriginalMember(owner = "client!cv", name = "M", descriptor = "[I")
	public final int[] otherMergeStamps = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "K", descriptor = "[I")
	public final int[] worldY = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "m", descriptor = "[I")
	public final int[] anIntArray69 = new int[8191];

	@OriginalMember(owner = "client!cv", name = "T", descriptor = "[I")
	public final int[] anIntArray89 = new int[8];

	@OriginalMember(owner = "client!cv", name = "P", descriptor = "[I")
	public final int[] cameraZ = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "r", descriptor = "[I")
	public final int[] anIntArray73 = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "I", descriptor = "[I")
	public final int[] anIntArray80 = new int[10000];

	@OriginalMember(owner = "client!cv", name = "Q", descriptor = "[I")
	public final int[] anIntArray86 = new int[10000];

	@OriginalMember(owner = "client!cv", name = "V", descriptor = "[I")
	public final int[] anIntArray91 = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "G", descriptor = "[I")
	public final int[] anIntArray78 = new int[32];

	@OriginalMember(owner = "client!cv", name = "S", descriptor = "[I")
	public final int[] anIntArray88 = new int[8];

	@OriginalMember(owner = "client!cv", name = "Y", descriptor = "[[I")
	public final int[][] anIntArrayArray15 = new int[12][JavaModel.anInt6301 + 8191];

	@OriginalMember(owner = "client!cv", name = "R", descriptor = "[I")
	public final int[] anIntArray87 = new int[10];

	@OriginalMember(owner = "client!cv", name = "x", descriptor = "[Lclient!wv;")
	public final PointEntity[] aClass3_Sub1_Sub1Array1 = new PointEntity[8191];

	@OriginalMember(owner = "client!cv", name = "bb", descriptor = "[I")
	public final int[] anIntArray94 = new int[JavaModel.anInt6301 + 8191];

	@OriginalMember(owner = "client!cv", name = "h", descriptor = "[[I")
	public final int[][] anIntArrayArray13 = new int[2200][64];

	@OriginalMember(owner = "client!cv", name = "W", descriptor = "[I")
	public final int[] cameraX = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "U", descriptor = "[I")
	public final int[] anIntArray90 = new int[12];

	@OriginalMember(owner = "client!cv", name = "Z", descriptor = "[I")
	public final int[] anIntArray93 = new int[64];

	@OriginalMember(owner = "client!cv", name = "gb", descriptor = "[I")
	public final int[] anIntArray97 = new int[JavaModel.anInt6294];

	@OriginalMember(owner = "client!cv", name = "cb", descriptor = "[I")
	public final int[] anIntArray95 = new int[8];

	@OriginalMember(owner = "client!cv", name = "fb", descriptor = "[I")
	public final int[] anIntArray96 = new int[10];

	@OriginalMember(owner = "client!cv", name = "hb", descriptor = "[Z")
	public final boolean[] aBooleanArray8 = new boolean[JavaModel.anInt6301];

	@OriginalMember(owner = "client!cv", name = "e", descriptor = "Lclient!bc;")
	private final JavaToolkit toolkit;

	@OriginalMember(owner = "client!cv", name = "d", descriptor = "Lclient!qv;")
	public Rasterizer rasterizer;

	@OriginalMember(owner = "client!cv", name = "db", descriptor = "Lclient!sk;")
	public final JavaModel copyDstModel1;

	@OriginalMember(owner = "client!cv", name = "w", descriptor = "Lclient!sk;")
	public final JavaModel copyDstModel2;

	@OriginalMember(owner = "client!cv", name = "j", descriptor = "Lclient!sk;")
	public final JavaModel copyDstModel3;

	@OriginalMember(owner = "client!cv", name = "C", descriptor = "Lclient!sk;")
	public final JavaModel copyDstModel4;

	@OriginalMember(owner = "client!cv", name = "i", descriptor = "Lclient!sk;")
	public final JavaModel copyDstModel5;

	@OriginalMember(owner = "client!cv", name = "eb", descriptor = "Lclient!sk;")
	public final JavaModel copySrcModel1;

	@OriginalMember(owner = "client!cv", name = "u", descriptor = "Lclient!sk;")
	public final JavaModel copySrcModel2;

	@OriginalMember(owner = "client!cv", name = "ab", descriptor = "Lclient!sk;")
	public final JavaModel copySrcModel3;

	@OriginalMember(owner = "client!cv", name = "E", descriptor = "Lclient!sk;")
	public final JavaModel copySrcModel4;

	@OriginalMember(owner = "client!cv", name = "k", descriptor = "Lclient!sk;")
	public final JavaModel copySrcModel5;

	@OriginalMember(owner = "client!cv", name = "J", descriptor = "[I")
	public final int[] faceBillboard;

	@OriginalMember(owner = "client!cv", name = "<init>", descriptor = "(Lclient!bc;)V")
	public JavaThreadResource(@OriginalArg(0) JavaToolkit javaToolkit) {
		this.toolkit = javaToolkit;
		this.rasterizer = new Rasterizer(this.toolkit);
		this.copyDstModel1 = new JavaModel(this.toolkit);
		this.copyDstModel2 = new JavaModel(this.toolkit);
		this.copyDstModel3 = new JavaModel(this.toolkit);
		this.copyDstModel4 = new JavaModel(this.toolkit);
		this.copyDstModel5 = new JavaModel(this.toolkit);
		this.copySrcModel1 = new JavaModel(this.toolkit);
		this.copySrcModel2 = new JavaModel(this.toolkit);
		this.copySrcModel3 = new JavaModel(this.toolkit);
		this.copySrcModel4 = new JavaModel(this.toolkit);
		this.copySrcModel5 = new JavaModel(this.toolkit);
		this.faceBillboard = new int[JavaModel.anInt6301];
		for (@Pc(243) int local243 = 0; local243 < JavaModel.anInt6301; local243++) {
			this.faceBillboard[local243] = -1;
		}
	}

	@OriginalMember(owner = "client!cv", name = "a", descriptor = "(ILjava/lang/Runnable;)V")
	public void bindThread(@OriginalArg(1) Runnable arg0) {
		this.thread = arg0;
	}

	@OriginalMember(owner = "client!cv", name = "a", descriptor = "(B)V")
	public void method998() {
		this.rasterizer = new Rasterizer(this.toolkit);
	}

@OriginalMember(owner = "client!cv", name = "b", descriptor = "Lclient!tj;")
	public static PcmPlayerThread aClass227_1;
@OriginalMember(owner = "client!cv", name = "ib", descriptor = "I")
	public static int anInt1122;
@OriginalMember(owner = "client!cv", name = "v", descriptor = "I")
	public static int mapBuildLevel = -1;
@OriginalMember(owner = "client!cv", name = "a", descriptor = "(I)[Lclient!ho;")
	public static QuickChatValueType[] method995() {
		return new QuickChatValueType[] { GlElementsArrayBuffer.aClass98_13, Class267.aClass98_18, BreakingWaterEffect.aClass98_4, PlayerVarDomain.aClass98_7, TextureCombinerOp.aClass98_11, SpriteDrawQueue.OPPLAYER8, Isaac.aClass98_5, Class4_Sub6_Sub15.aClass98_8, Static340.aClass98_15, ObjStackEntity.aClass98_3, Class223.aClass98_17, Class4_Sub6_Sub26.aClass98_12, Js5GroupLoader.aClass98_14, TextureOpConstant.aClass98_10 };
	}
@OriginalMember(owner = "client!cv", name = "b", descriptor = "(I)V")
	public static void method997() {
		if (Class4_Sub19.aString29.length() == 0) {
			return;
		}
		Static441.method5568("--> " + Class4_Sub19.aString29);
		GlVboElementsArrayBuffer.method5186(Class4_Sub19.aString29, false);
		Class16.anInt6887 = 0;
		Class4_Sub19.aString29 = "";
		CacheableNodeByteArray.anInt4511 = 0;
	}
}
