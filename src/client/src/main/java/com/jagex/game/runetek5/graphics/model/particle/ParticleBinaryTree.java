package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;

import jaggl.OpenGL;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class225;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static47;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.graphics.model.particle.*;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub36;
@OriginalClass("client!ts")
public final class ParticleBinaryTree {

	@OriginalMember(owner = "client!ts", name = "l", descriptor = "Lclient!ab;")
	private ParticleNode aClass3_Sub1_8;

	@OriginalMember(owner = "client!ts", name = "k", descriptor = "Lclient!ab;")
	public final ParticleNode aClass3_Sub1_7 = new ParticleNode();

	static {
		new Class83("You can't report yourself!", "Du kannst dich nicht selbst melden!", "Vous ne pouvez pas vous signaler vous-même !", "Você não pode denunciar a si próprio!");
	}

	@OriginalMember(owner = "client!ts", name = "<init>", descriptor = "()V")
	public ParticleBinaryTree() {
		this.aClass3_Sub1_7.prev2 = this.aClass3_Sub1_7;
		this.aClass3_Sub1_7.next2 = this.aClass3_Sub1_7;
	}

	@OriginalMember(owner = "client!ts", name = "a", descriptor = "(I)V")
	public void method5122() {
		while (true) {
			@Pc(5) ParticleNode local5 = this.aClass3_Sub1_7.next2;
			if (local5 == this.aClass3_Sub1_7) {
				this.aClass3_Sub1_8 = null;
				return;
			}
			local5.unlink2();
		}
	}

	@OriginalMember(owner = "client!ts", name = "b", descriptor = "(Z)Lclient!ab;")
	public ParticleNode method5124() {
		@Pc(11) ParticleNode local11 = this.aClass3_Sub1_8;
		if (local11 == this.aClass3_Sub1_7) {
			this.aClass3_Sub1_8 = null;
			return null;
		} else {
			this.aClass3_Sub1_8 = local11.next2;
			return local11;
		}
	}

	@OriginalMember(owner = "client!ts", name = "a", descriptor = "(ZLclient!ab;)V")
	public void method5126(@OriginalArg(1) ParticleNode arg0) {
		if (arg0.prev2 != null) {
			arg0.unlink2();
		}
		arg0.next2 = this.aClass3_Sub1_7;
		arg0.prev2 = this.aClass3_Sub1_7.prev2;
		arg0.prev2.next2 = arg0;
		arg0.next2.prev2 = arg0;
	}

	@OriginalMember(owner = "client!ts", name = "b", descriptor = "(I)Lclient!ab;")
	public ParticleNode method5127() {
		@Pc(14) ParticleNode local14 = this.aClass3_Sub1_7.next2;
		if (this.aClass3_Sub1_7 == local14) {
			this.aClass3_Sub1_8 = null;
			return null;
		} else {
			this.aClass3_Sub1_8 = local14.next2;
			return local14;
		}
	}

	@OriginalMember(owner = "client!ts", name = "c", descriptor = "(I)I")
	public int method5128() {
		@Pc(7) int local7 = 0;
		@Pc(11) ParticleNode local11 = this.aClass3_Sub1_7.next2;
		while (local11 != this.aClass3_Sub1_7) {
			local11 = local11.next2;
			local7++;
		}
		return local7;
	}

@OriginalMember(owner = "client!ts", name = "j", descriptor = "[[B")
	public static byte[][] aByteArrayArray28;
@OriginalMember(owner = "client!ts", name = "c", descriptor = "Lclient!lt;")
	public static final CacheableNodeQueue aClass142_3 = new CacheableNodeQueue(16);
@OriginalMember(owner = "client!ts", name = "h", descriptor = "[I")
	public static final int[] anIntArray498 = new int[50];
@OriginalMember(owner = "client!ts", name = "m", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_86 = new Class215(61, 2);
@OriginalMember(owner = "client!ts", name = "n", descriptor = "I")
	public static int anInt6542 = 0;
@OriginalMember(owner = "client!ts", name = "o", descriptor = "[Lclient!kf;")
	public static Class78_Sub1[] aClass78_Sub1Array1 = new Class78_Sub1[0];
@OriginalMember(owner = "client!ts", name = "p", descriptor = "[Lclient!ah;")
	public static final ParticleType[] aClass8Array1 = new ParticleType[16];
@OriginalMember(owner = "client!ts", name = "q", descriptor = "I")
	public static int anInt6543 = 0;
@OriginalMember(owner = "client!ts", name = "r", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_134 = new Class83("Loading additional fonts - ", "Lade Zusatzschriftarten - ", "Chargement de polices secondaires - ", "Carregando fontes principais - ");
@OriginalMember(owner = "client!ts", name = "a", descriptor = "(II)Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component method5121(@OriginalArg(1) int arg0) {
		@Pc(15) int local15 = arg0 >> 16;
		@Pc(19) int local19 = arg0 & 0xFFFF;
		if (Class4_Sub36.aClass247ArrayArray2[local15] == null || Class4_Sub36.aClass247ArrayArray2[local15][local19] == null) {
			@Pc(35) boolean local35 = Class4_Sub18.method2034(local15);
			if (!local35) {
				return null;
			}
		}
		return Class4_Sub36.aClass247ArrayArray2[local15][local19];
	}
@OriginalMember(owner = "client!ts", name = "a", descriptor = "(IBLclient!fd;Ljava/lang/String;)Lclient!td;")
	public static Class225 method5125(@OriginalArg(2) GlToolkit arg0, @OriginalArg(3) String arg1) {
		@Pc(5) int local5 = OpenGL.glGenProgramARB();
		OpenGL.glBindProgramARB(34336, local5);
		OpenGL.glProgramStringARB(34336, 34933, arg1);
		OpenGL.glGetIntegerv(34379, Static47.anIntArray47, 0);
		if (Static47.anIntArray47[0] == -1) {
			OpenGL.glBindProgramARB(34336, 0);
			return new Class225(arg0, 34336, local5);
		} else {
			OpenGL.glBindProgramARB(34336, 0);
			return null;
		}
	}
}
