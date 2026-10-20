package com.jagex.game.runetek5.graphics.gl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.da;

import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;

import com.jagex.game.runetek5.graphics.gl.*;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!ht")
public final class OpenGlCubeTextureGenerator extends Class99_Sub1 {

	@OriginalMember(owner = "client!ht", name = "u", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!ht", name = "x", descriptor = "Lclient!im;")
	private final GlCubeTexture cubeTexture;

	static {
		new Class83("Swap this note at any bank for the equivalent item.", "Dieses Zertifikat kann in einer Bank entsprechend eingetauscht werden.", "Échangez ce reçu contre l'objet correspondant dans la banque de votre choix.", "Vá a qualquer banco para trocar esta nota pelo objeto equivalente.");
	}

	@OriginalMember(owner = "client!ht", name = "<init>", descriptor = "(Lclient!fd;I)V")
	public OpenGlCubeTextureGenerator(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int size) {
		this.glToolkit = glToolkit;
		this.cubeTexture = new GlCubeTexture(glToolkit, 6408, size);
	}

	@OriginalMember(owner = "client!ht", name = "a", descriptor = "(I)Lclient!im;")
	@Override
	public GlCubeTexture method3232() {
		return this.cubeTexture;
	}

	@OriginalMember(owner = "client!ht", name = "a", descriptor = "(Lclient!im;FLclient!im;I)Z")
	public boolean method2666(@OriginalArg(0) GlCubeTexture arg0, @OriginalArg(1) float arg1, @OriginalArg(2) GlCubeTexture arg2) {
		@Pc(7) boolean local7 = true;
		@Pc(11) GlFramebuffer local11 = this.glToolkit.aClass150_2;
		@Pc(15) GlFramebuffer local15 = this.glToolkit.aClass150_1;
		if (arg2.size != this.glToolkit.aClass6_Sub1_2.anInt5466 || this.glToolkit.aClass6_Sub1_2.anInt5458 != arg2.size) {
			this.glToolkit.aClass6_Sub1_2 = new OpenGl2DTexture(this.glToolkit, 3553, 6408, arg2.size, arg2.size);
		}
		if (this.glToolkit.aClass6_Sub1_1.anInt5466 != arg0.size || this.glToolkit.aClass6_Sub1_1.anInt5458 != arg0.size) {
			this.glToolkit.aClass6_Sub1_1 = new OpenGl2DTexture(this.glToolkit, 3553, 6408, arg0.size, arg0.size);
		}
		this.glToolkit.da(AnimBase.anIntArray319);
		this.glToolkit.e();
		this.glToolkit.method1898();
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadIdentity();
		OpenGL.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
		OpenGL.glMatrixMode(5888);
		OpenGL.glLoadIdentity();
		OpenGL.glPushAttrib(2048);
		OpenGL.glViewport(0, 0, this.cubeTexture.size, this.cubeTexture.size);
		this.glToolkit.method1918(false);
		this.glToolkit.method1906(false);
		this.glToolkit.method1951(false);
		this.glToolkit.method1911(false);
		this.glToolkit.method1957(-2);
		this.glToolkit.method1936(1);
		this.glToolkit.method1913(0.0F, arg1, 0.0F, 0.0F);
		this.glToolkit.method1912(34165, 34165);
		this.glToolkit.setActiveTexture(null);
		this.glToolkit.method1936(0);
		this.glToolkit.method1900(1);
		this.glToolkit.setActiveTexture(null);
		@Pc(176) int local176 = 0;
		while (local176 < 6) {
			this.glToolkit.method1940(local11);
			@Pc(187) int local187 = local176 + 34069;
			local11.method3619(arg2, local187);
			this.glToolkit.method1956(local15);
			local15.method3621(this.glToolkit.aClass6_Sub1_2, 0);
			if (local11.method3616() && local15.method3616()) {
				OpenGL.glBlitFramebufferEXT(0, 0, arg2.size, arg2.size, 0, 0, arg2.size, arg2.size, 16384, 9728);
				local11.method3619(arg0, local187);
				local15.method3621(this.glToolkit.aClass6_Sub1_1, 0);
				if (local11.method3616() && local15.method3616()) {
					OpenGL.glBlitFramebufferEXT(0, 0, arg2.size, arg2.size, 0, 0, arg2.size, arg2.size, 16384, 9728);
					local11.method3611(0);
					this.glToolkit.method1935(local11);
					local15.method3611(0);
					this.glToolkit.method1949(local15);
					this.glToolkit.method1922(local11);
					local11.method3619(this.cubeTexture, local187);
					if (!local11.method3616()) {
						local11.method3611(0);
						local7 = false;
						this.glToolkit.method1971(local11);
						break;
					}
					this.glToolkit.method1936(1);
					this.glToolkit.setActiveTexture(this.glToolkit.aClass6_Sub1_1);
					this.glToolkit.method1936(0);
					this.glToolkit.setActiveTexture(this.glToolkit.aClass6_Sub1_2);
					OpenGL.glBegin(7);
					OpenGL.glTexCoord2f(0.0F, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 0.0F);
					OpenGL.glVertex2f(0.0F, 0.0F);
					OpenGL.glTexCoord2f(1.0F, 0.0F);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 0.0F);
					OpenGL.glVertex2f(1.0F, 0.0F);
					OpenGL.glTexCoord2f(1.0F, 1.0F);
					OpenGL.glMultiTexCoord2f(33985, 1.0F, 1.0F);
					OpenGL.glVertex2f(1.0F, 1.0F);
					OpenGL.glTexCoord2f(0.0F, 1.0F);
					OpenGL.glMultiTexCoord2f(33985, 0.0F, 1.0F);
					OpenGL.glVertex2f(0.0F, 1.0F);
					OpenGL.glEnd();
					this.glToolkit.method1936(1);
					this.glToolkit.setActiveTexture(null);
					this.glToolkit.method1936(0);
					this.glToolkit.setActiveTexture(null);
					local11.method3611(0);
					this.glToolkit.method1971(local11);
					local176++;
					continue;
				}
				local11.method3611(0);
				this.glToolkit.method1935(local11);
				local15.method3611(0);
				local7 = false;
				this.glToolkit.method1949(local15);
				break;
			}
			local11.method3611(0);
			this.glToolkit.method1935(local11);
			local15.method3611(0);
			this.glToolkit.method1949(local15);
			local7 = false;
			break;
		}
		this.glToolkit.method1936(1);
		this.glToolkit.method1912(8448, 8448);
		this.glToolkit.method1936(0);
		OpenGL.glPopAttrib();
		this.glToolkit.OA(AnimBase.anIntArray319[0], AnimBase.anIntArray319[1], AnimBase.anIntArray319[2], AnimBase.anIntArray319[3]);
		if (local7) {
			this.cubeTexture.method4189();
		}
		return local7;
	}

	@OriginalMember(owner = "client!ht", name = "c", descriptor = "(Z)I")
	public int method2668() {
		return this.cubeTexture.size;
	}

@OriginalMember(owner = "client!ht", name = "y", descriptor = "[I")
	public static final int[] anIntArray198 = new int[2];
@OriginalMember(owner = "client!ht", name = "a", descriptor = "(II)V")
	public static void method2667(@OriginalArg(1) int arg0) {
		@Pc(13) Class4_Sub1_Sub11 local13 = NPCTypeList.method5222(arg0, 10);
		local13.method2608();
	}
}
