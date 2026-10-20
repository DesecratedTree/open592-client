package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class225;
@OriginalClass("client!nd")
public final class Class48_Sub1_Sub1 extends Class48_Sub1 {

	@OriginalMember(owner = "client!nd", name = "J", descriptor = "[B")
	private byte[] aByteArray61;

	static {
		new Class83("Unable to add name - unknown player.", "Name konnte nicht hinzugefügt werden - Spieler unbekannt.", "Impossible d'ajouter le nom - joueur inconnu.", "Não foi possível adicionar um nome - jogador desconhecido.");
	}

	@OriginalMember(owner = "client!nd", name = "<init>", descriptor = "()V")
	public Class48_Sub1_Sub1() {
		super(8, 5, 8, 8, 2, 0.1F, 0.55F, 3.0F);
	}

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "(BII)V")
	@Override
	protected void method3752(@OriginalArg(0) byte arg0, @OriginalArg(1) int arg1) {
		@Pc(7) int local7 = arg1 * 2;
		@Pc(11) int local11 = arg0 & 0xFF;
		@Pc(14) int local14 = local7;
		@Pc(15) int local15 = local7 + 1;
		this.aByteArray61[local14] = (byte) (local11 * 3 >> 5);
		this.aByteArray61[local15] = (byte) (local11 * 3 >> 5);
	}

	@OriginalMember(owner = "client!nd", name = "a", descriptor = "(IIII)[B")
	public byte[] method3759() {
		this.aByteArray61 = new byte[524288];
		this.method4400();
		return this.aByteArray61;
	}

@OriginalMember(owner = "client!nd", name = "N", descriptor = "I")
	public static int anInt4879;
@OriginalMember(owner = "client!nd", name = "P", descriptor = "I")
	public static int anInt4881;
@OriginalMember(owner = "client!nd", name = "K", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_26 = new LRUCache(200);
@OriginalMember(owner = "client!nd", name = "L", descriptor = "I")
	public static int anInt4877 = -1;
@OriginalMember(owner = "client!nd", name = "Q", descriptor = "[I")
	public static final int[] anIntArray314 = new int[6];
@OriginalMember(owner = "client!nd", name = "R", descriptor = "I")
	public static int anInt4882 = 0;
@OriginalMember(owner = "client!nd", name = "S", descriptor = "J")
	public static long aLong160 = -1L;
@OriginalMember(owner = "client!nd", name = "a", descriptor = "(ILclient!uu;Lclient!ya;)V")
	public static void method3755(@OriginalArg(1) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		@Pc(33) boolean local33 = Terrain.aClass206_3.method4700(arg1, arg0.anInt6779, arg0.aBoolean613 ? Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel : null, arg0.anInt6828, arg0.anInt6832, arg0.anInt6831, arg0.anInt6778 | 0xFF000000) == null;
		if (local33) {
			Class225.aClass183_45.method4137(new Class4_Sub17(arg0.anInt6779, arg0.anInt6832, arg0.anInt6831, arg0.anInt6778 | 0xFF000000, arg0.anInt6828, arg0.aBoolean613));
			Class4_Sub12_Sub1.method1142(arg0);
		}
	}
@OriginalMember(owner = "client!nd", name = "a", descriptor = "(Lclient!wn;I[[B)V")
	public static void method3757(@OriginalArg(0) MapRegion arg0, @OriginalArg(2) byte[][] arg1) {
		@Pc(16) int[] local16 = new int[] { -1, 0, 0, 0, 0 };
		for (@Pc(18) int local18 = 0; local18 < arg0.anInt7302; local18++) {
			CubeMapSpecularEffect.method2701();
			for (@Pc(24) int local24 = 0; local24 < GLBillowTextureData.anInt5666 >> 3; local24++) {
				for (@Pc(28) int local28 = 0; local28 < Static283.anInt5187 >> 3; local28++) {
					@Pc(32) boolean local32 = false;
					@Pc(40) int local40 = Class189.anIntArrayArrayArray12[local18][local24][local28];
					if (local40 != -1) {
						@Pc(49) int local49 = local40 >> 24 & 0x3;
						if (!arg0.underwater || local49 == 0) {
							@Pc(63) int local63 = local40 >> 1 & 0x3;
							@Pc(69) int local69 = local40 >> 14 & 0x3FF;
							@Pc(75) int local75 = local40 >> 3 & 0x7FF;
							@Pc(85) int local85 = (local69 / 8 << 8) + local75 / 8;
							for (@Pc(87) int local87 = 0; local87 < Static308.anIntArray372.length; local87++) {
								if (Static308.anIntArray372[local87] == local85 && arg1[local87] != null) {
									@Pc(109) Packet local109 = new Packet(arg1[local87]);
									arg0.method5640(local63, local109, local49, PlayerVarDomain.aClass213Array1, local75, local18, local28 * 8, local24 * 8, local69);
									arg0.method5647(local18, local28 * 8, local109, local16[0] == -1 ? local16 : null, MonoStereoData.aClass19_16, local75, local69, local63, local49, local24 * 8);
									local32 = true;
									break;
								}
							}
						}
					}
					if (!local32) {
						arg0.setTileHeights(8, local18, 8, local28 * 8, local24 * 8);
					}
				}
			}
		}
		if (local16[0] != -1) {
			GlMemoryPool.aClass242_1 = StringNode.aClass217_1.method4934(local16[1], local16[0], local16[2], local16[3], CacheableNodeByteArray.aClass226_1);
			TextureOpIrregularBricks.anInt2023 = local16[4];
		}
	}
@OriginalMember(owner = "client!nd", name = "a", descriptor = "([ILclient!rs;[IZ[I)V")
	public static void method3758(@OriginalArg(0) int[] arg0, @OriginalArg(1) NPCEntity arg1, @OriginalArg(2) int[] arg2, @OriginalArg(4) int[] arg3) {
		for (@Pc(3) int local3 = 0; local3 < arg2.length; local3++) {
			@Pc(9) int local9 = arg2[local3];
			@Pc(13) int local13 = arg3[local3];
			@Pc(17) int local17 = arg0[local3];
			for (@Pc(19) int local19 = 0; local13 != 0 && arg1.sequences.length > local19; local19++) {
				if ((local13 & 0x1) != 0) {
					if (local9 == -1) {
						arg1.sequences[local19] = null;
					} else {
						@Pc(40) SeqType local40 = SceneLocEntity.aClass55_1.method1397(local9);
						@Pc(43) int local43 = local40.loopType;
						@Pc(48) PathingEntitySequence local48 = arg1.sequences[local19];
						if (local48 != null) {
							if (local9 == local48.anInt5139) {
								if (local43 == 0) {
									local48 = arg1.sequences[local19] = null;
								} else if (local43 == 1) {
									local48.anInt5140 = 1;
									local48.anInt5138 = 0;
									local48.anInt5142 = 0;
									local48.anInt5143 = 0;
									local48.anInt5141 = local17;
									Class4_Sub1_Sub2.method156(arg1.anInt6893, 0, arg1.aByte82, arg1.anInt6892, local40, false);
								} else if (local43 == 2) {
									local48.anInt5143 = 0;
								}
							} else if (local40.priority >= SceneLocEntity.aClass55_1.method1397(local48.anInt5139).priority) {
								local48 = arg1.sequences[local19] = null;
							}
						}
						if (local48 == null) {
							local48 = arg1.sequences[local19] = new PathingEntitySequence();
							local48.anInt5140 = 1;
							local48.anInt5141 = local17;
							local48.anInt5139 = local9;
							local48.anInt5142 = 0;
							local48.anInt5138 = 0;
							local48.anInt5143 = 0;
							Class4_Sub1_Sub2.method156(arg1.anInt6893, 0, arg1.aByte82, arg1.anInt6892, local40, false);
						}
					}
				}
				local13 >>>= 0x1;
			}
		}
	}
}
