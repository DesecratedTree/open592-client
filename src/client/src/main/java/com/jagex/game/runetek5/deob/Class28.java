package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!bs")
public final class Class28 {

	@OriginalMember(owner = "client!bs", name = "a", descriptor = "I")
	public int anInt658;

	@OriginalMember(owner = "client!bs", name = "c", descriptor = "I")
	public int anInt660;

	@OriginalMember(owner = "client!bs", name = "f", descriptor = "I")
	public int anInt663;

	@OriginalMember(owner = "client!bs", name = "g", descriptor = "I")
	public int anInt664;

	@OriginalMember(owner = "client!bs", name = "m", descriptor = "B")
	private byte aByte11;

	@OriginalMember(owner = "client!bs", name = "n", descriptor = "I")
	public int anInt669;

	@OriginalMember(owner = "client!bs", name = "<init>", descriptor = "()V")
	public Class28() {
	}

	@OriginalMember(owner = "client!bs", name = "<init>", descriptor = "(Lclient!iv;)V")
	public Class28(@OriginalArg(0) Packet arg0) {
		this.aByte11 = arg0.method2542();
		this.anInt660 = arg0.method2536();
		this.anInt663 = arg0.method2529();
		this.anInt664 = arg0.method2529();
		this.anInt658 = arg0.method2529();
		this.anInt669 = arg0.method2529();
	}

	@OriginalMember(owner = "client!bs", name = "a", descriptor = "(B)I")
	public int method667() {
		return this.aByte11 & 0x7;
	}

	@OriginalMember(owner = "client!bs", name = "b", descriptor = "(B)I")
	public int method668() {
		return (this.aByte11 & 0x8) == 8 ? 1 : 0;
	}

@OriginalMember(owner = "client!bs", name = "h", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_6 = new Class21(11, 18);
@OriginalMember(owner = "client!bs", name = "p", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_19 = new Class83("Discard", "Ablegen", "Jeter", "Descartar");
@OriginalMember(owner = "client!bs", name = "q", descriptor = "I")
	public static final int anInt671 = 1403;
@OriginalMember(owner = "client!bs", name = "a", descriptor = "(IZIZB)V")
	public static void method669(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean arg3) {
		Skybox.method5249(0, arg2, arg3, ParticleBinaryTree.aClass78_Sub1Array1.length - 1, arg1, arg0);
		OpenGl2DTexture.aClass4_Sub42_3 = null;
		GlCubeTexture.moveToVariableSpeed = 0;
	}
@OriginalMember(owner = "client!bs", name = "a", descriptor = "(II)V")
	public static void method670(@OriginalArg(0) int arg0) {
		@Pc(16) Class4_Sub1_Sub11 local16 = NPCTypeList.method5222(arg0, 1);
		local16.method2608();
	}
@OriginalMember(owner = "client!bs", name = "a", descriptor = "(ZB[BLclient!fd;IIII)Lclient!ps;")
	public static OpenGl2DImageTexture method671(@OriginalArg(2) byte[] arg0, @OriginalArg(3) GlToolkit arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3) {
		if (arg1.framebufferObjectSupported || Class4_Sub43.method5413(arg2) && Class4_Sub43.method5413(arg3)) {
			return new OpenGl2DImageTexture(arg1, 3553, 6406, arg2, arg3, false, arg0, 6406);
		} else if (arg1.arbRectangleTexturesSupported) {
			return new OpenGl2DImageTexture(arg1, 34037, 6406, arg2, arg3, false, arg0, 6406);
		} else {
			return new OpenGl2DImageTexture(arg1, 6406, arg2, arg3, SpriteDrawQueue.method4949(arg2), SpriteDrawQueue.method4949(arg3), arg0, 6406);
		}
	}
@OriginalMember(owner = "client!bs", name = "a", descriptor = "(IB)Lclient!fk;")
	public static Class3_Sub3 method672(@OriginalArg(0) int arg0) {
		@Pc(7) EntityNodeList[] local7 = MeshBillboard.aClass84Array1;
		synchronized (MeshBillboard.aClass84Array1) {
			@Pc(36) Class3_Sub3 local36;
			if (MeshBillboard.aClass84Array1.length <= arg0 || MeshBillboard.aClass84Array1[arg0].method2313()) {
				local36 = new Class3_Sub3();
				local36.aClass3_Sub5Array1 = new BoundingBoxPolygon[arg0];
				for (@Pc(42) int local42 = 0; local42 < arg0; local42++) {
					local36.aClass3_Sub5Array1[local42] = new BoundingBoxPolygon();
				}
			} else {
				local36 = (Class3_Sub3) MeshBillboard.aClass84Array1[arg0].method2308();
				local36.method5676();
				@Pc(75) int local75 = ProjectileAnimationNode.anIntArray154[arg0]--;
			}
			return local36;
		}
	}
@OriginalMember(owner = "client!bs", name = "b", descriptor = "(I)V")
	public static void method673() {
		@Pc(9) int[] local9 = new int[Terrain.aClass206_3.num];
		@Pc(11) int local11 = 0;
		for (@Pc(17) int local17 = 0; local17 < Terrain.aClass206_3.num; local17++) {
			@Pc(24) ItemType local24 = Terrain.aClass206_3.method4703(local17);
			if (local24.anInt6157 >= 0 || local24.anInt6156 >= 0) {
				local9[local11++] = local17;
			}
		}
		Class16_Sub4_Sub2.anIntArray170 = new int[local11];
		for (@Pc(48) int local48 = 0; local48 < local11; local48++) {
			Class16_Sub4_Sub2.anIntArray170[local48] = local9[local48];
		}
	}
}
