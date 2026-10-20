package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
public final class Static391 {

	@OriginalMember(owner = "client!tr", name = "f", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_91;

	@OriginalMember(owner = "client!tr", name = "c", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_132 = new Class83("Loaded sprites", "Sprites geladen.", "Sprites chargés", "Sprites carregados");

	@OriginalMember(owner = "client!tr", name = "d", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_133 = new Class83("Choose Option", "Wähl eine Option", "Choisir une option", "Selecionar opção");

	@OriginalMember(owner = "client!tr", name = "a", descriptor = "(ILclient!cw;)V")
	public static void method5117(@OriginalArg(1) Class4_Sub12_Sub1 arg0) {
		arg0.method1136();
		@Pc(10) int local10 = Class6.anInt5452;
		@Pc(20) PlayerEntity local20 = Static1.aClass16_Sub1_Sub5_Sub1_1 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local10] = new PlayerEntity();
		local20.anInt6037 = local10;
		@Pc(28) int local28 = arg0.method1143(30);
		@Pc(33) byte local33 = (byte) (local28 >> 28);
		@Pc(39) int local39 = local28 >> 14 & 0x3FFF;
		local20.movementBaseX[0] = local39 - Class50_Sub1.anInt3453;
		@Pc(51) int local51 = local28 & 0x3FFF;
		local20.anInt6893 = (local20.movementBaseX[0] << 7) + (local20.getSize() << 6);
		local20.movementBaseY[0] = local51 - OpenGLTextureCache.anInt1771;
		local20.anInt6892 = (local20.movementBaseY[0] << 7) + (local20.getSize() << 6);
		Class4_Sub6_Sub25.anInt4963 = local20.aByte82 = local33;
		if (ItemType.aClass4_Sub12Array1[local10] != null) {
			local20.method3422(ItemType.aClass4_Sub12Array1[local10]);
		}
		Class4_Sub7.anInt606 = 0;
		Static99.anIntArray157[Class4_Sub7.anInt606++] = local10;
		SpriteRenderNode.aByteArray63[local10] = 0;
		EnumType.anInt5761 = 0;
		for (@Pc(125) int local125 = 1; local125 < 2048; local125++) {
			if (local125 != local10) {
				@Pc(135) int local135 = arg0.method1143(18);
				@Pc(139) int local139 = local135 >> 16;
				@Pc(145) int local145 = local135 >> 8 & 0xFF;
				@Pc(149) int local149 = local135 & 0xFF;
				@Pc(157) GpuFaceNormal local157 = Class183.aClass253Array1[local125] = new GpuFaceNormal();
				local157.anInt6949 = (local139 << 28) + (local145 << 14) + local149;
				local157.anInt6947 = 0;
				local157.anInt6946 = -1;
				local157.aBoolean648 = false;
				Class189.anIntArray374[EnumType.anInt5761++] = local125;
				SpriteRenderNode.aByteArray63[local125] = 0;
			}
		}
		arg0.method1135();
	}

	@OriginalMember(owner = "client!tr", name = "a", descriptor = "(I)V")
	public static void method5118() {
		Texture3DShaderGL.anInt1373 = 0;
		@Pc(12) int local12 = (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7) + Class50_Sub1.anInt3453;
		@Pc(19) int local19 = OpenGLTextureCache.anInt1771 + (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7);
		if (local12 >= 3053 && local12 <= 3156 && local19 >= 3056 && local19 <= 3136) {
			Texture3DShaderGL.anInt1373 = 1;
		}
		if (local12 >= 3072 && local12 <= 3118 && local19 >= 9492 && local19 <= 9535) {
			Texture3DShaderGL.anInt1373 = 1;
		}
		if (Texture3DShaderGL.anInt1373 == 1 && local12 >= 3139 && local12 <= 3199 && local19 >= 3008 && local19 <= 3062) {
			Texture3DShaderGL.anInt1373 = 0;
		}
	}

	@OriginalMember(owner = "client!tr", name = "a", descriptor = "(ZII)Z")
	public static boolean method5119(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return Static283.method3965(arg1, arg0) || StackFrame.method303(arg1, arg0);
	}
}
