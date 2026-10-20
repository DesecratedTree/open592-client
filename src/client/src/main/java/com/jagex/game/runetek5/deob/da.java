package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.loginscreen.DrawableItem;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.toolkit.opengl.effect.UnlitEffect;

import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.qa;
@OriginalClass("client!da")
public final class da extends Model implements DrawableItem {

	@OriginalMember(owner = "client!da", name = "nativeid", descriptor = "J")
	private long nativeid;

	@OriginalMember(owner = "client!da", name = "q", descriptor = "Lclient!qa;")
	private final qa aQa1;

	@OriginalMember(owner = "client!da", name = "p", descriptor = "Lclient!na;")
	private final na aNa1;

	@OriginalMember(owner = "client!da", name = "s", descriptor = "[Lclient!bu;")
	public EmissiveTriangle[] aClass29Array1;

	@OriginalMember(owner = "client!da", name = "r", descriptor = "[Lclient!pf;")
	public EffectiveVertex[] aClass179Array1;

	@OriginalMember(owner = "client!da", name = "<init>", descriptor = "(Lclient!qa;Lclient!na;Lclient!jq;IIII)V")
	public da(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) Mesh mesh, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		this.aQa1 = arg0;
		this.aNa1 = arg1;
		this.aClass29Array1 = mesh.particleEmitters;
		this.aClass179Array1 = mesh.aClass179Array3;
		@Pc(24) int local24 = mesh.particleEmitters == null ? 0 : mesh.particleEmitters.length;
		@Pc(33) int local33 = mesh.aClass179Array3 == null ? 0 : mesh.aClass179Array3.length;
		@Pc(35) int local35 = 0;
		@Pc(42) int[] local42 = new int[local24 * 3 + local33];
		for (@Pc(44) int local44 = 0; local44 < local24; local44++) {
			local42[local35++] = this.aClass29Array1[local44].anInt706;
			local42[local35++] = this.aClass29Array1[local44].anInt713;
			local42[local35++] = this.aClass29Array1[local44].anInt698;
		}
		for (@Pc(78) int local78 = 0; local78 < local33; local78++) {
			local42[local35++] = this.aClass179Array1[local78].appliesOnVertex;
		}
		@Pc(101) int local101 = mesh.billboards == null ? 0 : mesh.billboards.length;
		@Pc(106) int[] local106 = new int[local101 * 8];
		@Pc(108) int local108 = 0;
		for (@Pc(110) int local110 = 0; local110 < local101; local110++) {
			@Pc(116) MeshBillboard local116 = mesh.billboards[local110];
			@Pc(121) BillboardType local121 = Class259.method5501(local116.anInt4526);
			local106[local108++] = local116.anInt4521;
			local106[local108++] = local121.anInt3538;
			local106[local108++] = local121.anInt3543;
			local106[local108++] = local121.anInt3536;
			local106[local108++] = local121.anInt3535;
			local106[local108++] = local121.anInt3542;
			local106[local108++] = local121.hasUid ? -1 : 0;
		}
		for (@Pc(173) int local173 = 0; local173 < local101; local173++) {
			@Pc(179) MeshBillboard local179 = mesh.billboards[local173];
			local106[local108++] = local179.anInt4527;
		}
		this.q(this.aQa1, this.aNa1, mesh.anInt3668, mesh.maxDepth, mesh.anIntArray244, mesh.anIntArray242, mesh.anIntArray251, mesh.anIntArray246, mesh.aShortArray122, mesh.anInt3686, mesh.aShortArray120, mesh.aShortArray119, mesh.aShortArray117, mesh.aByteArray43, mesh.aByteArray42, mesh.aByteArray44, mesh.aByteArray46, mesh.aShortArray123, mesh.aShortArray125, mesh.anIntArray245, mesh.priority, mesh.aShortArray121, mesh.anInt3689, mesh.aByteArray47, mesh.aShortArray118, mesh.aShortArray116, mesh.aShortArray124, mesh.anIntArray249, mesh.anIntArray248, mesh.anIntArray250, mesh.aByteArray41, mesh.aByteArray45, mesh.anIntArray252, mesh.anIntArray243, mesh.anIntArray247, local42, local24, local33, arg3, arg4, arg5, arg6, local106);
	}

	@OriginalMember(owner = "client!da", name = "<init>", descriptor = "(Lclient!qa;)V")
	public da(@OriginalArg(0) qa arg0) {
		this.aQa1 = arg0;
		this.aNa1 = null;
		this.o(arg0);
	}

	@OriginalMember(owner = "client!da", name = "D", descriptor = "()I")
	public native int D();

	@OriginalMember(owner = "client!da", name = "m", descriptor = "()I")
	public native int addLight();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(IIII)V")
	@Override
	public void adjustColours(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!da", name = "M", descriptor = "(I[IIIIZI[I)V")
	protected native void M(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int[] arg7);

	@OriginalMember(owner = "client!da", name = "w", descriptor = "()Z")
	protected native boolean w();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(Lclient!t;IIIZ)V")
	@Override
	public void shareLight(@OriginalArg(0) Model model, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		this.aQa1.method4317().U(this, model, arg1, arg2, arg3, arg4);
	}

	@OriginalMember(owner = "client!da", name = "c", descriptor = "(I)V")
	public native void c(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "N", descriptor = "(IIII)V")
	protected native void N(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3);

	@OriginalMember(owner = "client!da", name = "j", descriptor = "()V")
	protected native void scaleWidth();

	@OriginalMember(owner = "client!da", name = "c", descriptor = "()V")
	@Override
	public void uploadToGpu() {
	}

	@OriginalMember(owner = "client!da", name = "o", descriptor = "(Lclient!qa;)V")
	private native void o(@OriginalArg(0) qa arg0);

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(IILclient!c;Z)Z")
	@Override
	public boolean picked(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) boolean arg3) {
		return this.aQa1.method4317().B(this, arg0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!da", name = "b", descriptor = "()[Lclient!pf;")
	@Override
	public EffectiveVertex[] getEffectiveVertices() {
		return this.aClass179Array1;
	}

	@OriginalMember(owner = "client!da", name = "I", descriptor = "(SS)V")
	public native void I(@OriginalArg(0) short arg0, @OriginalArg(1) short arg1);

	@OriginalMember(owner = "client!da", name = "JA", descriptor = "(III)V")
	public native void drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!da", name = "Z", descriptor = "(I)V")
	public native void Z(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "WA", descriptor = "()I")
	public native int WA();

	@OriginalMember(owner = "client!da", name = "fa", descriptor = "(Lclient!ba;)Lclient!ba;")
	public native Shadow fa(@OriginalArg(0) Shadow arg0);

	@OriginalMember(owner = "client!da", name = "NA", descriptor = "()I")
	public native int NA();

	@OriginalMember(owner = "client!da", name = "Q", descriptor = "(I)V")
	public native void Q(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "aa", descriptor = "(I)V")
	public native void aa(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "PA", descriptor = "()I")
	public native int PA();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "()[Lclient!bu;")
	@Override
	public EmissiveTriangle[] getEmissiveTriangles() {
		return this.aClass29Array1;
	}

	@OriginalMember(owner = "client!da", name = "R", descriptor = "(I)V")
	public native void R(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "HA", descriptor = "()I")
	public native int HA();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(Lclient!c;Lclient!mi;I)V")
	@Override
	public void render(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon pickingCylinder, @OriginalArg(2) int arg2) {
		if (pickingCylinder == null) {
			this.aQa1.method4317().ea(this, arg0, null, arg2);
			return;
		}
		qa.pickingResult[5] = 0;
		this.aQa1.method4317().ea(this, arg0, qa.pickingResult, arg2);
		pickingCylinder.anInt4542 = qa.pickingResult[0];
		pickingCylinder.anInt4540 = qa.pickingResult[1];
		pickingCylinder.anInt4543 = qa.pickingResult[2];
		pickingCylinder.anInt4541 = qa.pickingResult[3];
		pickingCylinder.anInt4544 = qa.pickingResult[4];
		pickingCylinder.positionCalculated = qa.pickingResult[5] != 0;
	}

	@OriginalMember(owner = "client!da", name = "EA", descriptor = "()V")
	public native void EA();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(BIZ)Lclient!t;")
	@Override
	public Model copy(@OriginalArg(0) byte arg0, @OriginalArg(1) int arg1, @OriginalArg(2) boolean arg2) {
		return this.aQa1.method4317().method3715(this, arg0, arg1, arg2);
	}

	@OriginalMember(owner = "client!da", name = "P", descriptor = "()I")
	public native int P();

	@OriginalMember(owner = "client!da", name = "l", descriptor = "()I")
	public native int l();

	@OriginalMember(owner = "client!da", name = "d", descriptor = "()Z")
	@Override
	public boolean loadedTextures() {
		return true;
	}

	@OriginalMember(owner = "client!da", name = "H", descriptor = "(III)V")
	public native void H(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!da", name = "MA", descriptor = "()I")
	public native int setOffsets();

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(Lclient!c;Lclient!mi;II)V")
	@Override
	public void renderOrtho(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon pickingCylinder, @OriginalArg(2) int arg2) {
		if (pickingCylinder == null) {
			this.aQa1.method4317().ta(this, arg0, null, arg2, 1);
			return;
		}
		qa.pickingResult[5] = 0;
		this.aQa1.method4317().ta(this, arg0, qa.pickingResult, arg2, 1);
		pickingCylinder.anInt4542 = qa.pickingResult[0];
		pickingCylinder.anInt4540 = qa.pickingResult[1];
		pickingCylinder.anInt4543 = qa.pickingResult[2];
		pickingCylinder.anInt4541 = qa.pickingResult[3];
		pickingCylinder.anInt4544 = qa.pickingResult[4];
		pickingCylinder.positionCalculated = qa.pickingResult[5] != 0;
	}

	@OriginalMember(owner = "client!da", name = "UA", descriptor = "(I)V")
	public native void UA(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		Static404.method5220(this);
	}

	@OriginalMember(owner = "client!da", name = "ya", descriptor = "(Lclient!da;Lclient!da;IZZ)V")
	public native void ya(@OriginalArg(0) da arg0, @OriginalArg(1) da arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean arg3, @OriginalArg(4) boolean arg4);

	@OriginalMember(owner = "client!da", name = "BA", descriptor = "(SS)V")
	public native void copyAlpha(@OriginalArg(0) short arg0, @OriginalArg(1) short arg1);

	@OriginalMember(owner = "client!da", name = "E", descriptor = "(I)V")
	public native void E(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!da", name = "IA", descriptor = "()V")
	public native void IA();

	@OriginalMember(owner = "client!da", name = "U", descriptor = "()I")
	public native int U();

	@OriginalMember(owner = "client!da", name = "XA", descriptor = "(I[IIIIIZ)V")
	protected native void applyTransformUnmasked(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) boolean arg6);

	@OriginalMember(owner = "client!da", name = "q", descriptor = "(Lclient!qa;Lclient!na;II[I[I[I[I[SI[S[S[S[B[B[B[B[S[S[IB[SI[B[S[S[S[I[I[I[B[B[I[I[I[IIIIIII[I)V")
	private native void q(@OriginalArg(0) qa arg0, @OriginalArg(1) na arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7, @OriginalArg(8) short[] arg8, @OriginalArg(9) int arg9, @OriginalArg(10) short[] arg10, @OriginalArg(11) short[] arg11, @OriginalArg(12) short[] arg12, @OriginalArg(13) byte[] arg13, @OriginalArg(14) byte[] arg14, @OriginalArg(15) byte[] arg15, @OriginalArg(16) byte[] arg16, @OriginalArg(17) short[] arg17, @OriginalArg(18) short[] arg18, @OriginalArg(19) int[] arg19, @OriginalArg(20) byte arg20, @OriginalArg(21) short[] arg21, @OriginalArg(22) int arg22, @OriginalArg(23) byte[] arg23, @OriginalArg(24) short[] arg24, @OriginalArg(25) short[] arg25, @OriginalArg(26) short[] arg26, @OriginalArg(27) int[] arg27, @OriginalArg(28) int[] arg28, @OriginalArg(29) int[] arg29, @OriginalArg(30) byte[] arg30, @OriginalArg(31) byte[] arg31, @OriginalArg(32) int[] arg32, @OriginalArg(33) int[] arg33, @OriginalArg(34) int[] arg34, @OriginalArg(35) int[] arg35, @OriginalArg(36) int arg36, @OriginalArg(37) int arg37, @OriginalArg(38) int arg38, @OriginalArg(39) int arg39, @OriginalArg(40) int arg40, @OriginalArg(41) int arg41, @OriginalArg(42) int[] arg42);

	@OriginalMember(owner = "client!da", name = "h", descriptor = "(IILclient!ta;Lclient!ta;III)V")
	public native void h(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class65 arg2, @OriginalArg(3) Class65 arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6);

	@OriginalMember(owner = "client!da", name = "a", descriptor = "(ILclient!c;)V")
	public void method1184(@OriginalArg(0) int arg0, @OriginalArg(1) Ground arg1) {
		@Pc(1) int local1 = arg0;
		@Pc(6) int local6;
		if (this.aClass29Array1 != null) {
			for (local6 = 0; local6 < this.aClass29Array1.length; local6++) {
				@Pc(12) EmissiveTriangle local12 = this.aClass29Array1[local6];
				local12.anInt699 = qa.effectVertices[local1++];
				local12.anInt700 = qa.effectVertices[local1++];
				local12.anInt711 = qa.effectVertices[local1++];
				local12.anInt701 = qa.effectVertices[local1++];
				local12.anInt702 = qa.effectVertices[local1++];
				local12.anInt707 = qa.effectVertices[local1++];
				local12.anInt708 = qa.effectVertices[local1++];
				local12.anInt703 = qa.effectVertices[local1++];
				local12.anInt709 = qa.effectVertices[local1++];
			}
		}
		if (this.aClass179Array1 == null) {
			return;
		}
		for (local6 = 0; local6 < this.aClass179Array1.length; local6++) {
			@Pc(83) EffectiveVertex local83 = this.aClass179Array1[local6];
			@Pc(85) EffectiveVertex local85 = local83;
			if (local83.aClass179_2 != null) {
				local85 = local83.aClass179_2;
			}
			if (local83.aClass31_4 == null) {
				local83.aClass31_4 = arg1.method5657();
			} else {
				local83.aClass31_4.UnlitEffect(arg1);
			}
			local85.anInt5342 = qa.effectVertices[local1++];
			local85.anInt5340 = qa.effectVertices[local1++];
			local85.anInt5347 = qa.effectVertices[local1++];
		}
	}
}
