package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static211;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!lt")
public final class CacheableNodeQueue {

	@OriginalMember(owner = "client!lt", name = "f", descriptor = "Lclient!ac;")
	private CacheableNode head = new CacheableNode();

	@OriginalMember(owner = "client!lt", name = "j", descriptor = "Lclient!tn;")
	private final Class229 queue = new Class229();

	@OriginalMember(owner = "client!lt", name = "l", descriptor = "I")
	private final int initialCapacity;

	@OriginalMember(owner = "client!lt", name = "k", descriptor = "I")
	private int capacityLeft;

	@OriginalMember(owner = "client!lt", name = "m", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_19;

	@OriginalMember(owner = "client!lt", name = "<init>", descriptor = "(I)V")
	public CacheableNodeQueue(@OriginalArg(0) int size) {
		this.initialCapacity = size;
		this.capacityLeft = size;
		@Pc(19) int local19;
		for (local19 = 1; size > local19 + local19; local19 += local19) {
		}
		this.aClass102_19 = new CubeMapSpecularEffect(local19);
	}

	@OriginalMember(owner = "client!lt", name = "a", descriptor = "(JLclient!ac;I)V")
	public void method3480(@OriginalArg(0) long arg0, @OriginalArg(1) CacheableNode arg1) {
		if (this.capacityLeft == 0) {
			@Pc(11) CacheableNode local11 = this.queue.method5091();
			local11.method5684();
			local11.method4662();
			if (local11 == this.head) {
				local11 = this.queue.method5091();
				local11.method5684();
				local11.method4662();
			}
		} else {
			this.capacityLeft--;
		}
		this.aClass102_19.method2703(arg0, arg1);
		this.queue.method5088(arg1);
	}

	@OriginalMember(owner = "client!lt", name = "a", descriptor = "(JB)Lclient!ac;")
	public CacheableNode method3483(@OriginalArg(0) long arg0) {
		@Pc(10) CacheableNode local10 = (CacheableNode) this.aClass102_19.method2700(arg0);
		if (local10 != null) {
			this.queue.method5088(local10);
		}
		return local10;
	}

	@OriginalMember(owner = "client!lt", name = "a", descriptor = "(B)V")
	public void method3484() {
		this.queue.method5087();
		this.aClass102_19.method2699();
		this.head = new CacheableNode();
		this.capacityLeft = this.initialCapacity;
	}

@OriginalMember(owner = "client!lt", name = "h", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_195 = new Class22(2, -2);
@OriginalMember(owner = "client!lt", name = "i", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_196 = new Class22(23, 8);
@OriginalMember(owner = "client!lt", name = "a", descriptor = "(Ljava/lang/String;Z)V")
	public static void method3478(@OriginalArg(0) String arg0) {
		if (arg0 == null) {
			return;
		}
		if (Class4_Sub10.anInt2346 >= 100 && !Class4_Sub29.aBoolean423 || Class4_Sub10.anInt2346 >= 200) {
			Class249.method5323(Class99_Sub1.aClass83_80.method2267(NativeBillboardFace.anInt3423));
			return;
		}
		@Pc(27) String local27 = Class79.method4868(arg0);
		if (local27 == null) {
			return;
		}
		@Pc(75) String local75;
		for (@Pc(37) int local37 = 0; local37 < Class4_Sub10.anInt2346; local37++) {
			@Pc(45) String local45 = Class79.method4868(Mesh.aStringArray14[local37]);
			if (local45 != null && local45.equals(local27)) {
				Class249.method5323(arg0 + Class4_Sub5.aClass83_17.method2267(NativeBillboardFace.anInt3423));
				return;
			}
			if (Static57.aStringArray6[local37] != null) {
				local75 = Class79.method4868(Static57.aStringArray6[local37]);
				if (local75 != null && local75.equals(local27)) {
					Class249.method5323(arg0 + Class4_Sub5.aClass83_17.method2267(NativeBillboardFace.anInt3423));
					return;
				}
			}
		}
		for (@Pc(105) int local105 = 0; local105 < Component.anInt6823; local105++) {
			local75 = Class79.method4868(GLBillowTextureData.aStringArray27[local105]);
			if (local75 != null && local75.equals(local27)) {
				Class249.method5323(Static368.aClass83_124.method2267(NativeBillboardFace.anInt3423) + arg0 + GpuFaceNormal.aClass83_144.method2267(NativeBillboardFace.anInt3423));
				return;
			}
			if (Class4_Sub1_Sub17.aStringArray26[local105] != null) {
				@Pc(152) String local152 = Class79.method4868(Class4_Sub1_Sub17.aStringArray26[local105]);
				if (local152 != null && local152.equals(local27)) {
					Class249.method5323(Static368.aClass83_124.method2267(NativeBillboardFace.anInt3423) + arg0 + GpuFaceNormal.aClass83_144.method2267(NativeBillboardFace.anInt3423));
					return;
				}
			}
		}
		if (Class79.method4868(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44).equals(local27)) {
			Class249.method5323(Class4_Sub3.aClass83_8.method2267(NativeBillboardFace.anInt3423));
		} else {
			PingWorker.method5476(Static211.aClass215_41);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(arg0));
			ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
		}
	}
@OriginalMember(owner = "client!lt", name = "a", descriptor = "(IBII)I")
	public static int method3479(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if ((SeqTypeList.aByteArrayArrayArray2[arg1][arg2][arg0] & 0x8) == 0) {
			return arg1 <= 0 || (SeqTypeList.aByteArrayArrayArray2[1][arg2][arg0] & 0x2) == 0 ? arg1 : arg1 - 1;
		} else {
			return 0;
		}
	}
@OriginalMember(owner = "client!lt", name = "a", descriptor = "(CI)Z")
	public static boolean method3481(@OriginalArg(0) char arg0) {
		return arg0 == ' ' || arg0 == ' ' || arg0 == '_' || arg0 == '-';
	}
}
