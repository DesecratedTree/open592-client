package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class130;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!wk")
public final class Class145_Sub1 extends ComponentSurface {

	@OriginalMember(owner = "client!wk", name = "j", descriptor = "Lclient!ps;")
	public final OpenGl2DImageTexture aClass6_Sub1_Sub1_5;

	@OriginalMember(owner = "client!wk", name = "<init>", descriptor = "(Lclient!fd;II[B)V")
	public Class145_Sub1(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte[] arg3) {
		this.aClass6_Sub1_Sub1_5 = Class28.method671(arg3, arg0, arg1, arg2);
		this.aClass6_Sub1_Sub1_5.method4196(false, false);
	}

@OriginalMember(owner = "client!wk", name = "e", descriptor = "Lclient!gh;")
	public static QuickchatMessageTypeList aClass81_2;
@OriginalMember(owner = "client!wk", name = "g", descriptor = "I")
	public static int anInt7274;
@OriginalMember(owner = "client!wk", name = "h", descriptor = "F")
	public static float aFloat222;
@OriginalMember(owner = "client!wk", name = "b", descriptor = "(I)V")
	public static void method5615() {
		Static166.aClass247Array1 = null;
		ReflectiveRipplingWaterEffect.method123(0, -1, 0, GpuPointLight.anInt2881, EnumType.anInt5766, EffectiveVertex.anInt5346, 0, 0);
		if (Static166.aClass247Array1 != null) {
			PositionedGfx.method2460(0, GpuPointLight.anInt2881, Static258.anInt4823, Static57.aClass247_1.anInt6852, Static166.aClass247Array1, 0, -1412584499, EffectiveVertex.anInt5346, TextureOpInterpolate.anInt3054);
			Static166.aClass247Array1 = null;
		}
	}
@OriginalMember(owner = "client!wk", name = "d", descriptor = "(I)V")
	public static void method5617() {
		Class130.anInt3932 = 0;
		Class16_Sub1_Sub1.aBoolean38 = false;
		Class177.anInt5328 = -1;
		PlayerVarDomain.anInt3398 = 0;
		SpotAnimation.anInt1366 = 0;
		CacheableNodeByteArray.anInt4506 = 1;
		StringNode.anInt5302 = -3;
	}
@OriginalMember(owner = "client!wk", name = "a", descriptor = "(ILjava/lang/String;ILjava/lang/String;)V")
	public static void method5618(@OriginalArg(1) String arg0, @OriginalArg(2) int arg1, @OriginalArg(3) String arg2) {
		Static2.aString1 = arg0;
		Class4_Sub36.aString52 = arg2;
		ChatFilter.anInt3177 = arg1;
		if (Static2.aString1.equals("") || Class4_Sub36.aString52.equals("")) {
			StringNode.anInt5302 = 3;
		} else if (MapSpriteType.anInt1028 == -1) {
			StringNode.anInt5302 = -3;
			GameShell.anInt970 = 0;
			NPCTypeList.anInt6682 = 1;
			ParticleList.anInt4518 = 0;
			@Pc(40) Packet local40 = new Packet(128);
			local40.method2551(10);
			local40.method2531((int) (Math.random() * 9.9999999E7D));
			local40.method2504(Class4_Sub6_Sub7.method1684(Static2.aString1));
			local40.method2531((int) (Math.random() * 9.9999999E7D));
			local40.method2518(Class4_Sub36.aString52);
			local40.method2531((int) (Math.random() * 9.9999999E7D));
			local40.method2522(TextureOpColorGradient.aBigInteger1, Class186.aBigInteger2);
			ParticleNode.aClass4_Sub12_Sub1_5.currentPosition = 0;
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub1_Sub11.aClass60_10.anInt1812);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(local40.currentPosition + 2);
			ParticleNode.aClass4_Sub12_Sub1_5.method2524(592);
			ParticleNode.aClass4_Sub12_Sub1_5.method2519(local40.buffer, local40.currentPosition);
		} else {
			method5617();
		}
	}
}
