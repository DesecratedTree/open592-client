package com.jagex.game.runetek5.deob;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.companion.Binding;

import java.io.IOException;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.deob.Class14;
@OriginalClass("client!kj")
public abstract class Class6 implements Binding {

	@OriginalMember(owner = "client!kj", name = "o", descriptor = "Z")
	private boolean aBoolean489 = false;

	@OriginalMember(owner = "client!kj", name = "g", descriptor = "I")
	public final int anInt5441;

	@OriginalMember(owner = "client!kj", name = "r", descriptor = "I")
	private final int anInt5450;

	@OriginalMember(owner = "client!kj", name = "j", descriptor = "I")
	protected final int anInt5443;

	@OriginalMember(owner = "client!kj", name = "t", descriptor = "Z")
	private boolean aBoolean490;

	@OriginalMember(owner = "client!kj", name = "f", descriptor = "Lclient!fd;")
	protected final GlToolkit aClass19_Sub2_31;

	@OriginalMember(owner = "client!kj", name = "q", descriptor = "I")
	protected int anInt5449;

	static {
		new Class83("Loaded additional fonts", "Zusatzschriftarten geladen", "Polices secondaires chargées", "Fontes principais carregadas");
	}

	@OriginalMember(owner = "client!kj", name = "<init>", descriptor = "(Lclient!fd;IIIZ)V")
	protected Class6(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		this.anInt5441 = arg1;
		this.anInt5450 = arg3;
		this.anInt5443 = arg2;
		this.aBoolean490 = arg4;
		this.aClass19_Sub2_31 = arg0;
		OpenGL.glGenTextures(1, EmissiveTriangle.anIntArray41, 0);
		this.anInt5449 = EmissiveTriangle.anIntArray41[0];
		this.method4179(0);
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(BI)V")
	private void method4179(@OriginalArg(1) int arg0) {
		this.aClass19_Sub2_31.textureBuffersSize -= arg0;
		this.aClass19_Sub2_31.textureBuffersSize += this.method4184();
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(B)V")
	private void method4180() {
		this.aClass19_Sub2_31.setActiveTexture(this);
		if (this.aBoolean489) {
			OpenGL.glTexParameteri(this.anInt5441, 10241, this.aBoolean490 ? 9987 : 9729);
			OpenGL.glTexParameteri(this.anInt5441, 10240, 9729);
		} else {
			OpenGL.glTexParameteri(this.anInt5441, 10241, this.aBoolean490 ? 9984 : 9728);
			OpenGL.glTexParameteri(this.anInt5441, 10240, 9728);
		}
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(IZ)V")
	public final void method4181(@OriginalArg(1) boolean arg0) {
		if (this.aBoolean489 != arg0) {
			this.aBoolean489 = arg0;
			this.method4180();
		}
	}

	@OriginalMember(owner = "client!kj", name = "b", descriptor = "(I)V")
	public final void method4183() {
		if (this.anInt5449 > 0) {
			this.aClass19_Sub2_31.method1978(this.anInt5449, this.method4184());
			this.anInt5449 = 0;
		}
	}

	@OriginalMember(owner = "client!kj", name = "c", descriptor = "(I)I")
	private int method4184() {
		@Pc(13) int local13 = this.aClass19_Sub2_31.method1920(this.anInt5443) * this.anInt5450;
		return this.aBoolean490 ? local13 * 4 / 3 : local13;
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(ZZ)V")
	protected final void method4185(@OriginalArg(1) boolean arg0) {
		if (this.aBoolean490 != arg0) {
			@Pc(17) int local17 = this.method4184();
			this.aBoolean490 = true;
			this.method4180();
			this.method4179(local17);
		}
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(Z)I")
	public final int method4186() {
		return this.anInt5449;
	}

	@OriginalMember(owner = "client!kj", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() throws Throwable {
		this.method4183();
		super.finalize();
	}

	@OriginalMember(owner = "client!kj", name = "e", descriptor = "(I)Z")
	public final boolean method4189() {
		if (!this.aClass19_Sub2_31.extFramebufferObject) {
			return false;
		}
		@Pc(16) int local16 = this.method4184();
		this.aClass19_Sub2_31.setActiveTexture(this);
		OpenGL.glGenerateMipmapEXT(this.anInt5441);
		this.aBoolean490 = true;
		this.method4180();
		this.method4179(local16);
		return true;
	}

	@OriginalMember(owner = "client!kj", name = "a", descriptor = "(I)V")
	public abstract void detach();

@OriginalMember(owner = "client!kj", name = "h", descriptor = "Lclient!la;")
	public static AbstractFontRenderer aClass46_9;
@OriginalMember(owner = "client!kj", name = "w", descriptor = "I")
	public static int anInt5454;
@OriginalMember(owner = "client!kj", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_253 = new Class22(83, 3);
@OriginalMember(owner = "client!kj", name = "u", descriptor = "I")
	public static int anInt5452 = -1;
@OriginalMember(owner = "client!kj", name = "v", descriptor = "I")
	public static int anInt5453 = -1;
@OriginalMember(owner = "client!kj", name = "a", descriptor = "(Lclient!uu;I)V")
	public static void method4182(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		if (!AbstractArbBufferGL.aBoolean599) {
			return;
		}
		if (arg0.anObjectArray17 != null) {
			@Pc(14) com.jagex.game.runetek5.config.rsinterface.Component local14 = EnvironmentSampler.method3230(Class243.anInt6726, StackFrame.anInt350);
			if (local14 != null) {
				@Pc(20) HookRequest local20 = new HookRequest();
				local20.targetComponent = local14;
				local20.source = arg0;
				local20.params = arg0.anObjectArray17;
				Static271.executeHookInner(local20);
			}
		}
		PingWorker.method5476(Class174.aClass215_66);
		ParticleNode.aClass4_Sub12_Sub1_5.method2531(arg0.anInt6809);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(StackFrame.anInt350);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(arg0.anInt6865);
		ParticleNode.aClass4_Sub12_Sub1_5.method2531(Class243.anInt6726);
		ParticleNode.aClass4_Sub12_Sub1_5.method2494(arg0.anInt6779);
		ParticleNode.aClass4_Sub12_Sub1_5.method2524(ChatFilter.anInt3179);
	}
@OriginalMember(owner = "client!kj", name = "b", descriptor = "(Z)Z")
	public static boolean method4188() {
		try {
			return FloType.method2816();
		} catch (@Pc(14) IOException local14) {
			ParticleEffector.method2731();
			return true;
		} catch (@Pc(19) Exception local19) {
			@Pc(77) String local77 = "T2 - " + (IdkType.aClass22_246 == null ? -1 : IdkType.aClass22_246.method527()) + "," + (Static380.aClass22_294 == null ? -1 : Static380.aClass22_294.method527()) + "," + (Class190.aClass22_257 == null ? -1 : Class190.aClass22_257.method527()) + " - " + PointEntity.anInt4075 + "," + (Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0] + Class50_Sub1.anInt3453) + "," + (OpenGLTextureCache.anInt1771 + Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0]) + " - ";
			for (@Pc(79) int local79 = 0; local79 < PointEntity.anInt4075 && local79 < 50; local79++) {
				local77 = local77 + Class14.aClass4_Sub12_Sub1_3.buffer[local79] + ",";
			}
			Class64_Sub1.method1654(local19, local77);
			BasType.method3639();
			return true;
		}
	}
}
