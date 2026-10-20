package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!sv")
public final class Class223 {

	@OriginalMember(owner = "client!sv", name = "l", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_45 = new LRUCache(64);

	@OriginalMember(owner = "client!sv", name = "f", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_86;

	@OriginalMember(owner = "client!sv", name = "h", descriptor = "I")
	public final int anInt6400;

	@OriginalMember(owner = "client!sv", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class223(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_86 = arg2;
		if (this.aClass76_86 == null) {
			this.anInt6400 = 0;
		} else {
			this.anInt6400 = this.aClass76_86.method2108(16);
		}
	}

	@OriginalMember(owner = "client!sv", name = "a", descriptor = "(B)V")
	public void method5002() {
		@Pc(6) LRUCache local6 = this.aClass68_45;
		synchronized (this.aClass68_45) {
			this.aClass68_45.method1777();
		}
	}

	@OriginalMember(owner = "client!sv", name = "b", descriptor = "(I)V")
	public void method5003() {
		@Pc(6) LRUCache local6 = this.aClass68_45;
		synchronized (this.aClass68_45) {
			this.aClass68_45.method1791();
		}
	}

	@OriginalMember(owner = "client!sv", name = "a", descriptor = "(II)Lclient!iq;")
	public Class111 method5004(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_45;
		@Pc(16) Class111 local16;
		synchronized (this.aClass68_45) {
			local16 = (Class111) this.aClass68_45.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.aClass76_86.method2104(arg0, 16);
		local16 = new Class111();
		if (local33 != null) {
			local16.method2810(new Packet(local33));
		}
		@Pc(49) LRUCache local49 = this.aClass68_45;
		synchronized (this.aClass68_45) {
			this.aClass68_45.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!sv", name = "b", descriptor = "(II)V")
	public void method5005() {
		@Pc(2) LRUCache local2 = this.aClass68_45;
		synchronized (this.aClass68_45) {
			this.aClass68_45.method1788(5);
		}
	}

@OriginalMember(owner = "client!sv", name = "b", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_17 = new QuickChatValueType(12, 0, 1, 0);
@OriginalMember(owner = "client!sv", name = "a", descriptor = "(I)V")
	public static void method4999() {
		if (!Class10.aBoolean18) {
			return;
		}
		while (true) {
			while (GlCubeTexture.moveToVariableSpeed < ParticleBinaryTree.aClass78_Sub1Array1.length) {
				@Pc(30) Class78_Sub1 local30 = ParticleBinaryTree.aClass78_Sub1Array1[GlCubeTexture.moveToVariableSpeed];
				if (local30 != null && local30.anInt3887 == -1) {
					if (OpenGl2DTexture.aClass4_Sub42_3 == null) {
						OpenGl2DTexture.aClass4_Sub42_3 = Class4_Sub6_Sub35.aClass256_1.requestPing(local30.aString38);
					}
					@Pc(53) int local53 = OpenGl2DTexture.aClass4_Sub42_3.ping;
					if (local53 == -1) {
						return;
					}
					GlCubeTexture.moveToVariableSpeed++;
					OpenGl2DTexture.aClass4_Sub42_3 = null;
					local30.anInt3887 = local53;
				} else {
					GlCubeTexture.moveToVariableSpeed++;
				}
			}
			return;
		}
	}
@OriginalMember(owner = "client!sv", name = "a", descriptor = "([I[ILclient!lh;I[I)V")
	public static void method5000(@OriginalArg(0) int[] arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) PlayerEntity arg2, @OriginalArg(4) int[] arg3) {
		for (@Pc(8) int local8 = 0; local8 < arg3.length; local8++) {
			@Pc(14) int local14 = arg3[local8];
			@Pc(18) int local18 = arg0[local8];
			@Pc(22) int local22 = arg1[local8];
			@Pc(24) int local24 = 0;
			while (local18 != 0 && local24 < arg2.sequences.length) {
				if ((local18 & 0x1) != 0) {
					if (local14 == -1) {
						arg2.sequences[local24] = null;
					} else {
						@Pc(48) SeqType local48 = SceneLocEntity.aClass55_1.method1397(local14);
						@Pc(51) int local51 = local48.loopType;
						@Pc(56) PathingEntitySequence local56 = arg2.sequences[local24];
						if (local56 != null) {
							if (local56.anInt5139 == local14) {
								if (local51 == 0) {
									local56 = arg2.sequences[local24] = null;
								} else if (local51 == 1) {
									local56.anInt5138 = 0;
									local56.anInt5142 = 0;
									local56.anInt5140 = 1;
									local56.anInt5143 = 0;
									local56.anInt5141 = local22;
									Class4_Sub1_Sub2.method156(arg2.anInt6893, 0, arg2.aByte82, arg2.anInt6892, local48, arg2 == Static1.aClass16_Sub1_Sub5_Sub1_1);
								} else if (local51 == 2) {
									local56.anInt5143 = 0;
								}
							} else if (local48.priority >= SceneLocEntity.aClass55_1.method1397(local56.anInt5139).priority) {
								local56 = arg2.sequences[local24] = null;
							}
						}
						if (local56 == null) {
							local56 = arg2.sequences[local24] = new PathingEntitySequence();
							local56.anInt5138 = 0;
							local56.anInt5143 = 0;
							local56.anInt5139 = local14;
							local56.anInt5142 = 0;
							local56.anInt5140 = 1;
							local56.anInt5141 = local22;
							Class4_Sub1_Sub2.method156(arg2.anInt6893, 0, arg2.aByte82, arg2.anInt6892, local48, arg2 == Static1.aClass16_Sub1_Sub5_Sub1_1);
						}
					}
				}
				local24++;
				local18 >>>= 0x1;
			}
		}
	}
@OriginalMember(owner = "client!sv", name = "a", descriptor = "(IIIIII)V")
	public static void method5001(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (arg3 > GlCubeTexture.anInt3256 || arg1 < Model.anInt6287) {
			return;
		}
		@Pc(16) boolean local16;
		if (ItemType.anInt6186 > arg4) {
			local16 = false;
			arg4 = ItemType.anInt6186;
		} else if (arg4 > Class36.anInt2656) {
			arg4 = Class36.anInt2656;
			local16 = false;
		} else {
			local16 = true;
		}
		@Pc(38) boolean local38;
		if (arg0 < ItemType.anInt6186) {
			arg0 = ItemType.anInt6186;
			local38 = false;
		} else if (Class36.anInt2656 < arg0) {
			arg0 = Class36.anInt2656;
			local38 = false;
		} else {
			local38 = true;
		}
		if (Model.anInt6287 > arg3) {
			arg3 = Model.anInt6287;
		} else {
			BasType.method3640(arg4, arg2, arg0, Class4_Sub6_Sub7.anIntArrayArray21[arg3++]);
		}
		if (arg1 > GlCubeTexture.anInt3256) {
			arg1 = GlCubeTexture.anInt3256;
		} else {
			BasType.method3640(arg4, arg2, arg0, Class4_Sub6_Sub7.anIntArrayArray21[arg1--]);
		}
		@Pc(97) int local97;
		if (local16 && local38) {
			for (local97 = arg3; local97 <= arg1; local97++) {
				@Pc(103) int[] local103 = Class4_Sub6_Sub7.anIntArrayArray21[local97];
				local103[arg4] = local103[arg0] = arg2;
			}
		} else if (local16) {
			for (local97 = arg3; local97 <= arg1; local97++) {
				Class4_Sub6_Sub7.anIntArrayArray21[local97][arg4] = arg2;
			}
		} else if (local38) {
			for (local97 = arg3; local97 <= arg1; local97++) {
				Class4_Sub6_Sub7.anIntArrayArray21[local97][arg0] = arg2;
			}
		}
	}
}
