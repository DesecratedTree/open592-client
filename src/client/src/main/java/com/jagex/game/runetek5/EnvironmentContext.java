package com.jagex.game.runetek5;

import java.awt.Canvas;
import java.awt.Graphics;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageObserver;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.ia;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.core.io.DummyInputStream;
@OriginalClass("client!kd")
public abstract class EnvironmentContext extends Country {

	@OriginalMember(owner = "client!kd", name = "l", descriptor = "[I")
	public int[] anIntArray409;

	@OriginalMember(owner = "client!kd", name = "m", descriptor = "I")
	public int anInt5856;

	@OriginalMember(owner = "client!kd", name = "n", descriptor = "I")
	public int anInt5857;

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(IIILjava/awt/Graphics;BI)V")
	public abstract void method4601(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Graphics arg3, @OriginalArg(5) int arg4);

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(BLjava/awt/Canvas;)V")
	public abstract void method4606(@OriginalArg(1) Canvas arg0);

	@OriginalMember(owner = "client!kd", name = "a", descriptor = "(IIILjava/awt/Graphics;)V")
	public abstract void method4608(@OriginalArg(3) Graphics arg0);

@OriginalMember(owner = "client!kd", name = "p", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_273 = new Class22(42, -1);
@OriginalMember(owner = "client!kd", name = "t", descriptor = "[I")
	public static final int[] anIntArray410 = new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };
@OriginalMember(owner = "client!kd", name = "a", descriptor = "(II)V")
	public static void method4602(@OriginalArg(1) int arg0) {
		if (-1 != arg0 && TileMessage.aBooleanArray25[arg0]) {
			LegacyMouseManager.aClass76_81.method2115(arg0);
			Class4_Sub36.aClass247ArrayArray2[arg0] = null;
			LocEntity.aClass247ArrayArray1[arg0] = null;
			TileMessage.aBooleanArray25[arg0] = false;
		}
	}
@OriginalMember(owner = "client!kd", name = "a", descriptor = "(Ljava/awt/Color;ILjava/awt/Color;Ljava/awt/Color;IZLjava/lang/String;)V")
	public static void method4604(@OriginalArg(0) Color arg0, @OriginalArg(2) Color arg1, @OriginalArg(3) Color arg2, @OriginalArg(4) int arg3, @OriginalArg(5) boolean arg4, @OriginalArg(6) String arg5) {
		try {
			@Pc(6) Graphics local6 = Class164.aCanvas5.getGraphics();
			if (ParamTypeList.aFont1 == null) {
				ParamTypeList.aFont1 = new Font("Helvetica", 1, 13);
			}
			if (arg4) {
				local6.setColor(Color.black);
				local6.fillRect(0, 0, GpuPointLight.anInt2881, EffectiveVertex.anInt5346);
			}
			if (arg2 == null) {
				arg2 = new Color(140, 17, 17);
			}
			if (arg1 == null) {
				arg1 = new Color(140, 17, 17);
			}
			if (arg0 == null) {
				arg0 = new Color(255, 255, 255);
			}
			try {
				if (Class4_Sub6_Sub23.anImage2 == null) {
					Class4_Sub6_Sub23.anImage2 = Class164.aCanvas5.createImage(304, 34);
				}
				@Pc(63) Graphics local63 = Class4_Sub6_Sub23.anImage2.getGraphics();
				local63.setColor(arg1);
				local63.drawRect(0, 0, 303, 33);
				local63.setColor(arg2);
				local63.fillRect(2, 2, arg3 * 3, 30);
				local63.setColor(Color.black);
				local63.drawRect(1, 1, 301, 31);
				local63.fillRect(arg3 * 3 + 2, 2, 300 - arg3 * 3, 30);
				local63.setFont(ParamTypeList.aFont1);
				local63.setColor(arg0);
				local63.drawString(arg5, (304 - arg5.length() * 6) / 2, 22);
				local6.drawImage(Class4_Sub6_Sub23.anImage2, GpuPointLight.anInt2881 / 2 - 152, EffectiveVertex.anInt5346 / 2 + -18, null);
			} catch (@Pc(149) Exception local149) {
				@Pc(155) int local155 = GpuPointLight.anInt2881 / 2 - 152;
				@Pc(161) int local161 = EffectiveVertex.anInt5346 / 2 - 18;
				local6.setColor(arg1);
				local6.drawRect(0, 0, 303, 33);
				local6.setColor(arg2);
				local6.fillRect(local155 + 2, local161 + 2, arg3 * 3, 30);
				local6.setColor(Color.black);
				local6.drawRect(local155 + 1, local161 + 1, 301, 31);
				local6.fillRect(arg3 * 3 + local155 + 2, local161 - -2, 300 - arg3 * 3, 30);
				local6.setFont(ParamTypeList.aFont1);
				local6.setColor(arg0);
				local6.drawString(arg5, (304 - arg5.length() * 6) / 2 + local155, local161 - -22);
			}
			if (TextureOpPerlinNoise.aString30 != null) {
				local6.setFont(ParamTypeList.aFont1);
				local6.setColor(arg0);
				local6.drawString(TextureOpPerlinNoise.aString30, GpuPointLight.anInt2881 / 2 - TextureOpPerlinNoise.aString30.length() * 6 / 2, EffectiveVertex.anInt5346 / 2 + -26);
			}
		} catch (@Pc(268) Exception local268) {
			Class164.aCanvas5.repaint();
		}
	}
@OriginalMember(owner = "client!kd", name = "a", descriptor = "(Z)V")
	public static void method4605() {
		if (Class92.aClass199_6 == null) {
			return;
		}
		if (Class92.aClass199_6.anInt5760 == 1) {
			Class92.aClass199_6 = null;
			return;
		}
		if (Class92.aClass199_6.anInt5760 == 2) {
			TextureOpBlend.method3110(DummyInputStream.aClass66_6, 2, ReflectingTextureEffect.aString57);
			Class92.aClass199_6 = null;
			return;
		}
	}
@OriginalMember(owner = "client!kd", name = "a", descriptor = "(I)V")
	public static void method4607() {
		@Pc(9) int local9 = (int) ((double) GLBillowTextureData.anInt5666 * 34.46D);
		local9 <<= 0x0;
		if (MonoStereoData.aClass19_16.method4286()) {
			local9 += 128;
		}
		MonoStereoData.aClass19_16.ia(50, local9);
	}
}
