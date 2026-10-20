package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!ak")
public final class Class10 {

	@OriginalMember(owner = "client!ak", name = "b", descriptor = "Ljava/lang/String;")
	public String aString3;

	@OriginalMember(owner = "client!ak", name = "l", descriptor = "I")
	public int anInt169;

@OriginalMember(owner = "client!ak", name = "d", descriptor = "[[[B")
	public static byte[][][] aByteArrayArrayArray1;
@OriginalMember(owner = "client!ak", name = "c", descriptor = "I")
	public static int anInt163 = 0;
@OriginalMember(owner = "client!ak", name = "h", descriptor = "Z")
	public static boolean aBoolean18 = false;
@OriginalMember(owner = "client!ak", name = "j", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_17 = new Class22(14, 3);
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIIIIB)V")
	public static void method124(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		@Pc(7) int local7 = 0;
		@Pc(9) int local9 = arg0;
		@Pc(13) int local13 = arg3 * arg3;
		@Pc(17) int local17 = arg0 * arg0;
		@Pc(21) int local21 = local17 << 1;
		@Pc(29) int local29 = local13 << 1;
		@Pc(33) int local33 = arg0 << 1;
		@Pc(41) int local41 = local21 + local13 * (1 - local33);
		@Pc(49) int local49 = local17 - (local33 - 1) * local29;
		@Pc(53) int local53 = local13 << 2;
		@Pc(57) int local57 = local17 << 2;
		@Pc(65) int local65 = local21 * 3;
		@Pc(73) int local73 = local29 * ((arg0 << 1) - 3);
		@Pc(79) int local79 = local57;
		@Pc(85) int local85 = local53 * (arg0 - 1);
		@Pc(103) int local103;
		@Pc(111) int local111;
		if (Model.anInt6287 <= arg4 && arg4 <= GlCubeTexture.anInt3256) {
			local103 = Js5GroupLoader.method4519(Class36.anInt2656, arg3 + arg1, ItemType.anInt6186);
			local111 = Js5GroupLoader.method4519(Class36.anInt2656, arg1 - arg3, ItemType.anInt6186);
			BasType.method3640(local111, arg2, local103, Class4_Sub6_Sub7.anIntArrayArray21[arg4]);
		}
		while (local9 > 0) {
			if (local41 < 0) {
				while (local41 < 0) {
					local41 += local65;
					local49 += local79;
					local79 += local57;
					local65 += local57;
					local7++;
				}
			}
			if (local49 < 0) {
				local41 += local65;
				local49 += local79;
				local79 += local57;
				local65 += local57;
				local7++;
			}
			local49 += -local73;
			local41 += -local85;
			local9--;
			local85 -= local53;
			local73 -= local53;
			local103 = arg4 - local9;
			local111 = local9 + arg4;
			if (Model.anInt6287 <= local111 && GlCubeTexture.anInt3256 >= local103) {
				@Pc(219) int local219 = Js5GroupLoader.method4519(Class36.anInt2656, arg1 + local7, ItemType.anInt6186);
				@Pc(228) int local228 = Js5GroupLoader.method4519(Class36.anInt2656, arg1 - local7, ItemType.anInt6186);
				if (Model.anInt6287 <= local103) {
					BasType.method3640(local228, arg2, local219, Class4_Sub6_Sub7.anIntArrayArray21[local103]);
				}
				if (local111 <= GlCubeTexture.anInt3256) {
					BasType.method3640(local228, arg2, local219, Class4_Sub6_Sub7.anIntArrayArray21[local111]);
				}
			}
		}
	}
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(Lclient!uu;B)Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component method125(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		if (arg0.anInt6850 != -1) {
			return ParticleBinaryTree.method5121(arg0.anInt6850);
		}
		@Pc(20) int local20 = arg0.anInt6809 >>> 16;
		@Pc(25) Class25 local25 = new Class25(MusicPatch.aClass102_29);
		for (@Pc(30) Class4_Sub43 local30 = (Class4_Sub43) local25.method641(); local30 != null; local30 = (Class4_Sub43) local25.method640()) {
			if (local30.anInt6979 == local20) {
				return ParticleBinaryTree.method5121((int) local30.aLong224);
			}
		}
		return null;
	}
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(ILclient!uu;)Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component method126(@OriginalArg(1) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		@Pc(6) com.jagex.game.runetek5.config.rsinterface.Component local6 = client.method906(arg0);
		if (local6 == null) {
			local6 = arg0.depthChildOverride;
		}
		return local6;
	}
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(B)V")
	public static void method127() {
		Static57.aClass19_7.B(((float) TextureOpBrightness.aClass50_Sub1_1.anInt3436 * 0.1F + 0.7F) * Class208.aFloat187);
		Static57.aClass19_7.scaleHeight(Static398.anInt6606, TextureOpDiagonalWaves.aFloat131, Country.aFloat228, (float) (RectangularTextureGL.anInt2361 << 0), (float) (StaticElements.anInt5676 << 0), (float) (LRUCache.anInt2036 << 0));
		Static57.aClass19_7.setSunCubeTexture(MemoryPool.aClass99_4);
	}
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(II)Lclient!ho;")
	public static QuickChatValueType method128(@OriginalArg(0) int arg0) {
		@Pc(8) QuickChatValueType[] local8 = JavaThreadResource.method995();
		for (@Pc(10) int local10 = 0; local10 < local8.length; local10++) {
			if (local8[local10].anInt3060 == arg0) {
				return local8[local10];
			}
		}
		return null;
	}
@OriginalMember(owner = "client!ak", name = "a", descriptor = "(IIIIIZII)V")
	public static void method130(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		@Pc(10) int local10 = arg5 - arg6;
		@Pc(19) int local19 = arg0 + arg6;
		for (@Pc(21) int local21 = arg0; local21 < local19; local21++) {
			BasType.method3640(arg3, arg4, arg1, Class4_Sub6_Sub7.anIntArrayArray21[local21]);
		}
		@Pc(44) int local44 = arg1 - arg6;
		for (@Pc(46) int local46 = arg5; local46 > local10; local46--) {
			BasType.method3640(arg3, arg4, arg1, Class4_Sub6_Sub7.anIntArrayArray21[local46]);
		}
		@Pc(64) int local64 = arg3 + arg6;
		for (@Pc(66) int local66 = local19; local66 <= local10; local66++) {
			@Pc(72) int[] local72 = Class4_Sub6_Sub7.anIntArrayArray21[local66];
			BasType.method3640(arg3, arg4, local64, local72);
			BasType.method3640(local64, arg2, local44, local72);
			BasType.method3640(local44, arg4, arg1, local72);
		}
	}
}
