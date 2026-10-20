package com.jagex.game.runetek5.cache.node.lru;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.companion.Binding;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Static281;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.cache.node.lru.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
@OriginalClass("client!io")
public final class OpenGlRenderBuffer extends CacheableNode implements Binding {

	@OriginalMember(owner = "client!io", name = "I", descriptor = "I")
	private int target = -1;

	@OriginalMember(owner = "client!io", name = "M", descriptor = "I")
	private int attachment = -1;

	@OriginalMember(owner = "client!io", name = "H", descriptor = "I")
	private final int format;

	@OriginalMember(owner = "client!io", name = "F", descriptor = "I")
	public final int width;

	@OriginalMember(owner = "client!io", name = "E", descriptor = "I")
	public final int height;

	@OriginalMember(owner = "client!io", name = "D", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!io", name = "y", descriptor = "I")
	private int handle;

	@OriginalMember(owner = "client!io", name = "z", descriptor = "I")
	private final int bufferSizeBytes;

	@OriginalMember(owner = "client!io", name = "<init>", descriptor = "(Lclient!fd;III)V")
	public OpenGlRenderBuffer(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int format, @OriginalArg(2) int width, @OriginalArg(3) int height) {
		this.format = format;
		this.width = width;
		this.height = height;
		this.glToolkit = glToolkit;
		OpenGL.glGenRenderbuffersEXT(1, Static267.anIntArray321, 0);
		this.handle = Static267.anIntArray321[0];
		OpenGL.glBindRenderbufferEXT(36161, this.handle);
		OpenGL.glRenderbufferStorageEXT(36161, this.format, this.width, this.height);
		this.bufferSizeBytes = this.height * this.width * this.glToolkit.method1920(this.format);
	}

	@OriginalMember(owner = "client!io", name = "<init>", descriptor = "(Lclient!fd;IIII)V")
	public OpenGlRenderBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		this.format = arg1;
		this.glToolkit = arg0;
		this.height = arg3;
		this.width = arg2;
		OpenGL.glGenRenderbuffersEXT(1, Static267.anIntArray321, 0);
		this.handle = Static267.anIntArray321[0];
		OpenGL.glBindRenderbufferEXT(36161, this.handle);
		OpenGL.glRenderbufferStorageMultisampleEXT(36161, arg4, this.format, this.width, this.height);
		this.bufferSizeBytes = this.height * this.width * this.glToolkit.method1920(this.format);
	}

	@OriginalMember(owner = "client!io", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() throws Throwable {
		this.dispose();
		super.finalize();
	}

	@OriginalMember(owner = "client!io", name = "a", descriptor = "(ZII)V")
	public void attach(@OriginalArg(1) int arg0, @OriginalArg(2) int attachment) {
		OpenGL.glFramebufferRenderbufferEXT(attachment, arg0, 36161, this.handle);
		this.target = attachment;
		this.attachment = arg0;
	}

	@OriginalMember(owner = "client!io", name = "a", descriptor = "(I)V")
	@Override
	public void detach() {
		OpenGL.glFramebufferRenderbufferEXT(this.target, this.attachment, 36161, 0);
		this.attachment = -1;
		this.target = -1;
	}

	@OriginalMember(owner = "client!io", name = "d", descriptor = "(I)V")
	public void dispose() {
		if (this.handle > 0) {
			this.glToolkit.method1910(this.bufferSizeBytes, this.handle);
			this.handle = 0;
		}
	}

@OriginalMember(owner = "client!io", name = "a", descriptor = "(III)Z")
	public static boolean method2771(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (!Class4_Sub6_Sub22.aBoolean411) {
			return false;
		}
		@Pc(13) int local13 = arg0 >> 16;
		@Pc(17) int local17 = arg0 & 0xFFFF;
		if (Class4_Sub36.aClass247ArrayArray2[local13] == null || Class4_Sub36.aClass247ArrayArray2[local13][local17] == null) {
			return false;
		}
		@Pc(35) com.jagex.game.runetek5.config.rsinterface.Component local35 = Class4_Sub36.aClass247ArrayArray2[local13][local17];
		@Pc(46) OggOtherStream local46;
		if (arg1 == -1 && local35.anInt6840 == 0) {
			for (local46 = (OggOtherStream) Class126.aClass183_28.method4140(); local46 != null; local46 = (OggOtherStream) Class126.aClass183_28.method4144()) {
				if (local46.anInt5699 == 47 || local46.anInt5699 == 1002 || local46.anInt5699 == 9 || local46.anInt5699 == 48 || local46.anInt5699 == 60) {
					for (@Pc(136) com.jagex.game.runetek5.config.rsinterface.Component local136 = ParticleBinaryTree.method5121(local46.anInt5697); local136 != null; local136 = Class10.method125(local136)) {
						if (local136.anInt6809 == local35.anInt6809) {
							return true;
						}
					}
				}
			}
		} else {
			for (local46 = (OggOtherStream) Class126.aClass183_28.method4140(); local46 != null; local46 = (OggOtherStream) Class126.aClass183_28.method4144()) {
				if (arg1 == local46.anInt5696 && local46.anInt5697 == local35.anInt6809 && (local46.anInt5699 == 47 || local46.anInt5699 == 1002 || local46.anInt5699 == 9 || local46.anInt5699 == 48 || local46.anInt5699 == 60)) {
					return true;
				}
			}
		}
		return false;
	}
@OriginalMember(owner = "client!io", name = "a", descriptor = "(IIIILclient!rd;)V")
	public static void method2773(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) LocType arg3) {
		for (@Pc(14) ReflectionCheck local14 = (ReflectionCheck) Static281.aClass183_34.method4140(); local14 != null; local14 = (ReflectionCheck) Static281.aClass183_34.method4144()) {
			if (local14.anInt619 == arg2 && local14.anInt614 == arg1 << 7 && local14.anInt613 == arg0 << 7 && local14.aClass202_1.id == arg3.id) {
				if (local14.aClass4_Sub15_Sub3_2 != null) {
					GameCanvas.aClass4_Sub15_Sub2_2.method2955(local14.aClass4_Sub15_Sub3_2);
					local14.aClass4_Sub15_Sub3_2 = null;
				}
				if (local14.aClass4_Sub15_Sub3_1 != null) {
					GameCanvas.aClass4_Sub15_Sub2_2.method2955(local14.aClass4_Sub15_Sub3_1);
					local14.aClass4_Sub15_Sub3_1 = null;
				}
				local14.method5684();
				return;
			}
		}
	}
}
