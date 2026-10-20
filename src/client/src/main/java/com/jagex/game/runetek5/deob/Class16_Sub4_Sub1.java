package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
@OriginalClass("client!eg")
public final class Class16_Sub4_Sub1 extends Class16_Sub4 implements GameObject {

	@OriginalMember(owner = "client!eg", name = "s", descriptor = "Lclient!hr;")
	public Class16_Sub4 aClass16_Sub4_1;

	@OriginalMember(owner = "client!eg", name = "<init>", descriptor = "(IIILclient!hr;)V")
	public Class16_Sub4_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) Class16_Sub4 arg3) {
		super(arg0, arg1, arg2, false, false);
		this.aClass16_Sub4_1 = arg3;
	}

	@OriginalMember(owner = "client!eg", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!eg", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return 0;
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return 0;
	}

	@OriginalMember(owner = "client!eg", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return false;
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return 0;
	}

	@OriginalMember(owner = "client!eg", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!eg", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
	}

	@OriginalMember(owner = "client!eg", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		return null;
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return null;
	}

	@OriginalMember(owner = "client!eg", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		return false;
	}

@OriginalMember(owner = "client!eg", name = "B", descriptor = "[I")
	public static final int[] anIntArray146 = new int[1000];
@OriginalMember(owner = "client!eg", name = "a", descriptor = "(II)Lclient!ah;")
	public static ParticleType method1587(@OriginalArg(1) int arg0) {
		@Pc(16) ParticleType local16 = (ParticleType) OggOtherStream.aClass68_38.method1787((long) arg0);
		if (local16 != null) {
			return local16;
		}
		@Pc(26) byte[] local26 = BillboardType.aClass76_42.method2104(arg0, 1);
		local16 = new ParticleType();
		local16.partileFileId = arg0;
		if (local26 != null) {
			local16.method89(new Packet(local26));
		}
		local16.method91();
		if (local16.particleHandlingType == 2 && Class4_Sub3.aClass102_2.method2700((long) arg0) == null) {
			Class4_Sub3.aClass102_2.method2703((long) arg0, new Class4_Sub37(Mesh.anInt3688));
			ParticleBinaryTree.aClass8Array1[Mesh.anInt3688++] = local16;
		}
		OggOtherStream.aClass68_38.method1779((long) arg0, local16);
		return local16;
	}
}
