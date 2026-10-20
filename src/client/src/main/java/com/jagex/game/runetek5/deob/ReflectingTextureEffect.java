package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorFill;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Class10;
@OriginalClass("client!qr")
public final class ReflectingTextureEffect extends Class9 {

	@OriginalMember(owner = "client!qr", name = "n", descriptor = "Z")
	private boolean aBoolean517 = false;

	@OriginalMember(owner = "client!qr", name = "r", descriptor = "Lclient!ct;")
	private GlRenderList aClass43_6;

	@OriginalMember(owner = "client!qr", name = "<init>", descriptor = "(Lclient!fd;)V")
	public ReflectingTextureEffect(@OriginalArg(0) GlToolkit arg0) {
		super(arg0);
		if (arg0.arbTextureCubeMap) {
			this.aClass43_6 = new GlRenderList(arg0, 2);
			this.aClass43_6.method990(0);
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.method1912(34165, 7681);
			super.aClass19_Sub2_41.method1945(2, 770, 34168);
			super.aClass19_Sub2_41.method1954(34167, 0);
			OpenGL.glTexGeni(8192, 9472, 34066);
			OpenGL.glTexGeni(8193, 9472, 34066);
			OpenGL.glTexGeni(8194, 9472, 34066);
			OpenGL.glEnable(3168);
			OpenGL.glEnable(3169);
			OpenGL.glEnable(3170);
			super.aClass19_Sub2_41.method1936(0);
			this.aClass43_6.method991();
			this.aClass43_6.method990(1);
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.method1912(8448, 8448);
			super.aClass19_Sub2_41.method1945(2, 770, 34166);
			super.aClass19_Sub2_41.method1954(5890, 0);
			OpenGL.glDisable(3168);
			OpenGL.glDisable(3169);
			OpenGL.glDisable(3170);
			OpenGL.glMatrixMode(5890);
			OpenGL.glLoadIdentity();
			OpenGL.glMatrixMode(5888);
			super.aClass19_Sub2_41.method1936(0);
			this.aClass43_6.method991();
		}
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		@Pc(12) GlCubeTexture local12 = super.aClass19_Sub2_41.method1946();
		if (this.aClass43_6 == null || local12 == null || !arg0) {
			super.aClass19_Sub2_41.method1954(34168, 0);
			return;
		}
		this.aClass43_6.method989('\u0000');
		super.aClass19_Sub2_41.method1936(1);
		super.aClass19_Sub2_41.setActiveTexture(local12);
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadMatrixf(super.aClass19_Sub2_41.aClass31_Sub1_3.method5155(), 0);
		OpenGL.glRotatef(-180.0F, 1.0F, 0.0F, 0.0F);
		OpenGL.glMatrixMode(5888);
		super.aClass19_Sub2_41.method1936(0);
		this.aBoolean517 = true;
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
		super.aClass19_Sub2_41.method1912(8448, 7681);
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (this.aBoolean517) {
			this.aClass43_6.method989('\u0001');
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(null);
			super.aClass19_Sub2_41.method1936(0);
		} else {
			super.aClass19_Sub2_41.method1954(5890, 0);
		}
		super.aClass19_Sub2_41.method1912(8448, 8448);
		this.aBoolean517 = false;
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		super.aClass19_Sub2_41.setActiveTexture(arg1);
		super.aClass19_Sub2_41.method1900(arg0);
	}

	@OriginalMember(owner = "client!qr", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

@OriginalMember(owner = "client!qr", name = "o", descriptor = "I")
	public static int anInt5747;
@OriginalMember(owner = "client!qr", name = "p", descriptor = "Ljava/lang/String;")
	public static String aString57;
@OriginalMember(owner = "client!qr", name = "t", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array12;
@OriginalMember(owner = "client!qr", name = "k", descriptor = "I")
	public static int anInt5744 = -1;
@OriginalMember(owner = "client!qr", name = "b", descriptor = "(B)V")
	public static void method4529() {
		Class14.aClass4_Sub12_Sub1_3.method1136();
		@Pc(13) int local13 = Class14.aClass4_Sub12_Sub1_3.method1143(8);
		@Pc(18) int local18;
		if (local13 < Class10.anInt163) {
			for (local18 = local13; local18 < Class10.anInt163; local18++) {
				TextureOpColorFill.anIntArray35[Class60.anInt1816++] = TextureOpBoxBlur.anIntArray412[local18];
			}
		}
		if (local13 > Class10.anInt163) {
			throw new RuntimeException("gnpov1");
		}
		Class10.anInt163 = 0;
		for (local18 = 0; local18 < local13; local18++) {
			@Pc(56) int local56 = TextureOpBoxBlur.anIntArray412[local18];
			@Pc(60) NPCEntity local60 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local56];
			@Pc(65) int local65 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
			if (local65 == 0) {
				TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local56;
				local60.anInt6015 = Shadow.anInt5323;
			} else {
				@Pc(90) int local90 = Class14.aClass4_Sub12_Sub1_3.method1143(2);
				if (local90 == 0) {
					TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local56;
					local60.anInt6015 = Shadow.anInt5323;
					LocTypeList.mapElementLocIds[ObjStackEntity.anInt759++] = local56;
				} else {
					@Pc(134) int local134;
					@Pc(144) int local144;
					if (local90 == 1) {
						TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local56;
						local60.anInt6015 = Shadow.anInt5323;
						local134 = Class14.aClass4_Sub12_Sub1_3.method1143(3);
						local60.move(local134, 1);
						local144 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
						if (local144 == 1) {
							LocTypeList.mapElementLocIds[ObjStackEntity.anInt759++] = local56;
						}
					} else if (local90 == 2) {
						TextureOpBoxBlur.anIntArray412[Class10.anInt163++] = local56;
						local60.anInt6015 = Shadow.anInt5323;
						if (Class14.aClass4_Sub12_Sub1_3.method1143(1) == 1) {
							local134 = Class14.aClass4_Sub12_Sub1_3.method1143(3);
							local60.move(local134, 2);
							local144 = Class14.aClass4_Sub12_Sub1_3.method1143(3);
							local60.move(local144, 2);
						} else {
							local134 = Class14.aClass4_Sub12_Sub1_3.method1143(3);
							local60.move(local134, 0);
						}
						local134 = Class14.aClass4_Sub12_Sub1_3.method1143(1);
						if (local134 == 1) {
							LocTypeList.mapElementLocIds[ObjStackEntity.anInt759++] = local56;
						}
					} else if (local90 == 3) {
						TextureOpColorFill.anIntArray35[Class60.anInt1816++] = local56;
					}
				}
			}
		}
	}
}
