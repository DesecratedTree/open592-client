package com.jagex.game.runetek5;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Static271;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Class111;
@OriginalClass("client!cf")
public final class GlVolumeTexture extends Class6 {

	@OriginalMember(owner = "client!cf", name = "C", descriptor = "I")
	private int framebufferTarget = -1;

	@OriginalMember(owner = "client!cf", name = "D", descriptor = "I")
	private int framebufferAttachment = -1;

	@OriginalMember(owner = "client!cf", name = "H", descriptor = "I")
	public final int anInt841;

	@OriginalMember(owner = "client!cf", name = "A", descriptor = "I")
	public final int anInt835;

	@OriginalMember(owner = "client!cf", name = "y", descriptor = "I")
	public final int anInt833;

	@OriginalMember(owner = "client!cf", name = "<init>", descriptor = "(Lclient!fd;IIII[BI)V")
	public GlVolumeTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) byte[] arg5, @OriginalArg(6) int arg6) {
		super(arg0, 32879, arg1, arg2 * arg3 * arg4, false);
		this.anInt841 = arg3;
		this.anInt835 = arg4;
		this.anInt833 = arg2;
		super.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glPixelStorei(3317, 1);
		OpenGL.glTexImage3Dub(super.anInt5441, 0, super.anInt5443, this.anInt833, this.anInt841, this.anInt835, 0, arg6, 5121, arg5, 0);
		OpenGL.glPixelStorei(3317, 4);
		this.method4181(true);
	}

	@OriginalMember(owner = "client!cf", name = "<init>", descriptor = "(Lclient!fd;IIII)V")
	public GlVolumeTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		super(arg0, 32879, arg1, arg4 * arg2 * arg3, false);
		this.anInt835 = arg4;
		this.anInt833 = arg2;
		this.anInt841 = arg3;
		super.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glTexImage3Dub(super.anInt5441, 0, super.anInt5443, this.anInt833, this.anInt841, this.anInt835, 0, Class180.method4122(super.anInt5443), 5121, null, 0);
		this.method4181(true);
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(I)V")
	@Override
	public void detach() {
		OpenGL.glFramebufferTexture3DEXT(this.framebufferTarget, this.framebufferAttachment, super.anInt5441, 0, 0, 0);
		this.framebufferAttachment = -1;
		this.framebufferTarget = -1;
	}

	@OriginalMember(owner = "client!cf", name = "a", descriptor = "(IIIIIBII)V")
	public void method793(@OriginalArg(3) int arg0, @OriginalArg(4) int arg1, @OriginalArg(6) int arg2) {
		super.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glCopyTexSubImage3D(super.anInt5441, 0, 0, 0, arg1, 0, 0, arg0, arg2);
		OpenGL.glFlush();
	}

@OriginalMember(owner = "client!cf", name = "E", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_53 = new Class22(30, 3);
@OriginalMember(owner = "client!cf", name = "I", descriptor = "Lclient!lt;")
	public static final CacheableNodeQueue aClass142_1 = new CacheableNodeQueue(128);
@OriginalMember(owner = "client!cf", name = "f", descriptor = "(I)V")
	public static void method789() {
		Class111.aClass68_18.method1777();
	}
@OriginalMember(owner = "client!cf", name = "a", descriptor = "(II[Lclient!uu;)V")
	public static void method790(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component[] arg1) {
		for (@Pc(8) int local8 = 0; local8 < arg1.length; local8++) {
			@Pc(14) com.jagex.game.runetek5.config.rsinterface.Component local14 = arg1[local8];
			if (local14 != null) {
				if (local14.anInt6840 == 0) {
					if (local14.slotChildren != null) {
						method790(arg0, local14.slotChildren);
					}
					@Pc(38) Class4_Sub43 local38 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local14.anInt6809);
					if (local38 != null) {
						Class4_Sub37.method4165(local38.anInt6979, arg0);
					}
				}
				@Pc(54) HookRequest local54;
				if (arg0 == 0 && local14.anObjectArray29 != null) {
					local54 = new HookRequest();
					local54.params = local14.anObjectArray29;
					local54.source = local14;
					Static271.executeHookInner(local54);
				}
				if (arg0 == 1 && local14.anObjectArray10 != null) {
					if (local14.anInt6865 >= 0) {
						@Pc(79) com.jagex.game.runetek5.config.rsinterface.Component local79 = ParticleBinaryTree.method5121(local14.anInt6809);
						if (local79 == null || local79.slotChildren == null || local14.anInt6865 >= local79.slotChildren.length || local79.slotChildren[local14.anInt6865] != local14) {
							continue;
						}
					}
					local54 = new HookRequest();
					local54.source = local14;
					local54.params = local14.anObjectArray10;
					Static271.executeHookInner(local54);
				}
			}
		}
	}
@OriginalMember(owner = "client!cf", name = "a", descriptor = "(III)I")
	public static int method791(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		arg1 = arg1 * (arg0 & 0x7F) >> 7;
		if (arg1 < 2) {
			arg1 = 2;
		} else if (arg1 > 126) {
			arg1 = 126;
		}
		return arg1 + (arg0 & 0xFF80);
	}
}
