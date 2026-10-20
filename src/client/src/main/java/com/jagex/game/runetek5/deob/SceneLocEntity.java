package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class28;
@OriginalClass("client!jd")
public final class SceneLocEntity extends Class16_Sub1 implements GameObject {

	@OriginalMember(owner = "client!jd", name = "E", descriptor = "Lclient!bi;")
	public final LocEntity aClass23_2;

	@OriginalMember(owner = "client!jd", name = "N", descriptor = "Z")
	private final boolean aBoolean312;

	static {
		new Class83(" was kicked from the channel.", " wurde aus dem Chatraum rausgeworfen.", " a été expulsé du canal.", " foi expulso do canal.");
	}

	@OriginalMember(owner = "client!jd", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIZIIIIIII)V")
	public SceneLocEntity(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12, @OriginalArg(13) int arg13, @OriginalArg(14) int arg14) {
		super(arg2, arg4, arg5, arg6, arg8, arg9, arg10, arg11, arg1.anInt5818 == 1, Static312.method4172(arg12, arg13));
		this.aClass23_2 = new LocEntity(arg0, arg1, arg12, arg13, super.aByte82, arg3, arg4, arg6, arg7, arg14);
		this.aBoolean312 = arg1.anInt5813 != 0 && !arg7;
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aClass23_2.method584();
	}

	@OriginalMember(owner = "client!jd", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return this.aClass23_2.method580();
	}

	@OriginalMember(owner = "client!jd", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.aClass23_2.method578(0, false, arg0, 0, arg1, false);
	}

	@OriginalMember(owner = "client!jd", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(20) Model local20 = this.aClass23_2.method578(super.anInt6892, true, arg0, super.anInt6893, 262144, true);
		if (local20 != null) {
			this.aClass23_2.method586(false, super.aShort96, local20, super.aShort97, arg0, super.aShort95, super.aShort94);
		}
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!jd", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aClass23_2.anInt536;
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_2.method582(arg0);
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_2.method588(arg0);
	}

	@OriginalMember(owner = "client!jd", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(16) Model local16 = this.aClass23_2.method578(super.anInt6892, true, arg0, super.anInt6893, 2048, false);
		if (local16 == null) {
			return null;
		}
		@Pc(23) Ground local23 = arg0.method4289();
		local23.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(33) Class3_Sub3 local33 = null;
		if (this.aBoolean312) {
			local33 = Class28.method672(1);
		}
		if (this.aClass23_2.aClass3_Sub2_1 == null) {
			local16.render(local23, local33 == null ? null : local33.aClass3_Sub5Array1[0], 0);
		} else {
			@Pc(54) ParticleList local54 = this.aClass23_2.aClass3_Sub2_1.getList();
			arg0.method4262(local16, local54, local23, local33 == null ? null : local33.aClass3_Sub5Array1[0]);
		}
		this.aClass23_2.method586(true, super.aShort96, local16, super.aShort97, arg0, super.aShort95, super.aShort94);
		return local33;
	}

	@OriginalMember(owner = "client!jd", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!jd", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(16) Model local16 = this.aClass23_2.method578(super.anInt6892, false, arg1, super.anInt6893, 131072, false);
		if (local16 == null) {
			return false;
		} else {
			@Pc(27) Ground local27 = arg1.method4289();
			local27.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
			return local16.picked(arg2, arg0, local27, false);
		}
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aClass23_2.anInt551;
	}

	@OriginalMember(owner = "client!jd", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aClass23_2.anInt548;
	}

@OriginalMember(owner = "client!jd", name = "K", descriptor = "Lclient!du;")
	public static SeqTypeList aClass55_1;
@OriginalMember(owner = "client!jd", name = "M", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_19 = new LRUCache(4);
@OriginalMember(owner = "client!jd", name = "S", descriptor = "I")
	public static final int anInt3494 = 4;
@OriginalMember(owner = "client!jd", name = "T", descriptor = "I")
	public static int anInt3495 = 0;
@OriginalMember(owner = "client!jd", name = "U", descriptor = "I")
	public static final int anInt3496 = -1;
@OriginalMember(owner = "client!jd", name = "e", descriptor = "(B)Lclient!wa;")
	public static Class174 method2891() {
		try {
			return (Class174) Class.forName("com.jagex.game.runetek5.graphics.texture.water.KeyedSoftReferenceFactory").getDeclaredConstructor().newInstance();
		} catch (@Pc(14) Throwable local14) {
			return null;
		}
	}
}
