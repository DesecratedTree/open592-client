package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.scene.ground.Ground;

import java.awt.Color;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
@OriginalClass("client!dd")
public final class SpotAnimation extends Class16_Sub1 {

	@OriginalMember(owner = "client!dd", name = "X", descriptor = "Lclient!ao;")
	private ParticleSystem aClass3_Sub2_2;

	@OriginalMember(owner = "client!dd", name = "C", descriptor = "I")
	private final int anInt1348 = -1;

	@OriginalMember(owner = "client!dd", name = "A", descriptor = "I")
	private int anInt1347 = 0;

	@OriginalMember(owner = "client!dd", name = "P", descriptor = "I")
	private int anInt1358 = 0;

	@OriginalMember(owner = "client!dd", name = "R", descriptor = "Z")
	public boolean aBoolean113 = false;

	@OriginalMember(owner = "client!dd", name = "F", descriptor = "I")
	private int anInt1350 = 0;

	@OriginalMember(owner = "client!dd", name = "O", descriptor = "I")
	private int anInt1357 = -32768;

	@OriginalMember(owner = "client!dd", name = "y", descriptor = "I")
	public final int startCycle;

	@OriginalMember(owner = "client!dd", name = "ab", descriptor = "I")
	private final int id;

	@OriginalMember(owner = "client!dd", name = "E", descriptor = "I")
	private final int anInt1349;

	@OriginalMember(owner = "client!dd", name = "D", descriptor = "Lclient!lp;")
	private SeqType aClass138_2;

	@OriginalMember(owner = "client!dd", name = "<init>", descriptor = "(IIIIIIIIIIIII)V")
	public SpotAnimation(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12) {
		super(arg3, arg5, arg6, arg7, arg8, arg9, arg10, arg11, false, (byte) 0);
		this.startCycle = arg2 + arg1;
		this.id = arg0;
		this.anInt1347 = arg12;
		this.anInt1349 = arg4;
		@Pc(49) SpotAnimationType local49 = Js5MasterIndex.aClass194_2.method4421(this.id);
		@Pc(52) int local52 = local49.sequenceId;
		if (local52 == -1) {
			this.aBoolean113 = true;
		} else {
			this.aClass138_2 = SceneLocEntity.aClass55_1.method1397(local52);
			this.aBoolean113 = false;
		}
		if (this.startCycle == arg2) {
			Class4_Sub1_Sub2.method156(super.anInt6893, this.anInt1358, super.aByte82, super.anInt6892, this.aClass138_2, false);
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IILclient!ya;B)Lclient!t;")
	private Model method1240(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg2) {
		@Pc(13) SpotAnimationType local13 = Js5MasterIndex.aClass194_2.method4421(arg0);
		@Pc(18) Class65 local18 = Class4_Sub6_Sub4.aClass65Array1[super.aByte82];
		@Pc(31) Class65 local31 = this.anInt1349 >= 3 ? null : Class4_Sub6_Sub4.aClass65Array1[this.anInt1349 + 1];
		return this.aBoolean113 ? local13.method3833(local31, super.anInt6889, super.anInt6893, arg2, super.anInt6892, -1, local18, -1, SceneLocEntity.aClass55_1, arg1, 0) : local13.method3833(local31, super.anInt6889, super.anInt6893, arg2, super.anInt6892, this.anInt1358, local18, this.anInt1348, SceneLocEntity.aClass55_1, arg1, this.anInt1350);
	}

	@OriginalMember(owner = "client!dd", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		if (this.aClass3_Sub2_2 != null) {
			this.aClass3_Sub2_2.stopped();
		}
	}

	@OriginalMember(owner = "client!dd", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(16) Model local16 = this.method1240(this.id, 0, arg0);
		if (local16 != null) {
			this.method1245(local16, arg0);
		}
	}

	@OriginalMember(owner = "client!dd", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!dd", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(26) Model local26 = this.method1240(this.id, (this.anInt1347 == 0 ? 0 : 5) | 0x800, arg0);
		if (local26 == null) {
			return null;
		}
		if (this.anInt1347 != 0) {
			local26.Q(this.anInt1347 * 2048);
		}
		@Pc(42) Ground local42 = arg0.method4289();
		local42.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
		if (this.aClass3_Sub2_2 == null) {
			local26.render(local42, null, 0);
		} else {
			@Pc(57) ParticleList local57 = this.aClass3_Sub2_2.getList();
			arg0.method4262(local26, local57, local42, null);
		}
		this.anInt1357 = local26.setOffsets();
		this.method1245(local26, arg0);
		return null;
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IZ)V")
	public void method1243(@OriginalArg(0) int arg0) {
		if (this.aBoolean113) {
			return;
		}
		this.anInt1350 += arg0;
		while (this.anInt1350 > this.aClass138_2.frames[this.anInt1358]) {
			this.anInt1350 -= this.aClass138_2.frames[this.anInt1358];
			this.anInt1358++;
			if (this.anInt1358 >= this.aClass138_2.durations.length) {
				this.aBoolean113 = true;
				break;
			}
		}
		if (!this.aBoolean113) {
			Class4_Sub1_Sub2.method156(super.anInt6893, this.anInt1358, super.aByte82, super.anInt6892, this.aClass138_2, false);
		}
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(Z)V")
	public void method1244() {
		if (this.aClass3_Sub2_2 != null) {
			this.aClass3_Sub2_2.stopped();
		}
	}

	@OriginalMember(owner = "client!dd", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		return false;
	}

	@OriginalMember(owner = "client!dd", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return this.anInt1357;
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!dd", name = "a", descriptor = "(Lclient!t;Lclient!ya;I)V")
	private void method1245(@OriginalArg(0) Model arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		@Pc(6) EmissiveTriangle[] local6 = arg0.getEmissiveTriangles();
		@Pc(14) EffectiveVertex[] local14 = arg0.getEffectiveVertices();
		if ((this.aClass3_Sub2_2 == null || this.aClass3_Sub2_2.removed) && (local6 != null || local14 != null)) {
			this.aClass3_Sub2_2 = new ParticleSystem(Shadow.anInt5323);
		}
		if (this.aClass3_Sub2_2 != null) {
			this.aClass3_Sub2_2.update(arg1, (long) Shadow.anInt5323, local6, local14);
			this.aClass3_Sub2_2.updateBounds(super.aByte82, super.aShort94, super.aShort97, super.aShort96, super.aShort95);
		}
	}

@OriginalMember(owner = "client!dd", name = "M", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_17;
@OriginalMember(owner = "client!dd", name = "Q", descriptor = "[I")
	public static int[] anIntArray106;
@OriginalMember(owner = "client!dd", name = "z", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_12 = new Class215(60, 3);
@OriginalMember(owner = "client!dd", name = "J", descriptor = "[I")
	public static final int[] anIntArray105 = new int[] { 1, 0, -1, 0 };
@OriginalMember(owner = "client!dd", name = "U", descriptor = "I")
	public static int anInt1361 = 0;
@OriginalMember(owner = "client!dd", name = "bb", descriptor = "I")
	public static int anInt1366 = 0;
@OriginalMember(owner = "client!dd", name = "cb", descriptor = "[Ljava/awt/Color;")
	public static final Color[] aColorArray3 = new Color[] { new Color(9179409), new Color(3289650) };
@OriginalMember(owner = "client!dd", name = "db", descriptor = "I")
	public static int anInt1367 = 0;
@OriginalMember(owner = "client!dd", name = "a", descriptor = "(I)V")
	public static void method1241() {
		if (Class4_Sub43.aClass49_2 != null) {
			Class4_Sub43.aClass49_2.method2074();
		}
		if (TextureOpMandelbrot.aClass49_1 != null) {
			TextureOpMandelbrot.aClass49_1.method2074();
		}
	}
}
