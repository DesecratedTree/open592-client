package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!kt")
public final class TextureOpDiagonalWaves extends TextureOperation {

	@OriginalMember(owner = "client!kt", name = "I", descriptor = "I")
	private int pattern1OffsetX = 2048;

	@OriginalMember(owner = "client!kt", name = "E", descriptor = "I")
	private int anInt4001 = 4096;

	@OriginalMember(owner = "client!kt", name = "H", descriptor = "I")
	private int pattern1OffsetY = 0;

	@OriginalMember(owner = "client!kt", name = "J", descriptor = "I")
	private int anInt4006 = 2048;

	@OriginalMember(owner = "client!kt", name = "M", descriptor = "I")
	private int anInt4009 = 12288;

	@OriginalMember(owner = "client!kt", name = "K", descriptor = "I")
	private int anInt4007 = 0;

	@OriginalMember(owner = "client!kt", name = "N", descriptor = "I")
	private int anInt4010 = 8192;

	@OriginalMember(owner = "client!kt", name = "<init>", descriptor = "()V")
	public TextureOpDiagonalWaves() {
		super(0, true);
	}

	@OriginalMember(owner = "client!kt", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int local29 = ParticleEffector.anIntArray209[arg0] - 2048;
			for (@Pc(31) int local31 = 0; local31 < MidiPcmStream.anInt1616; local31++) {
				@Pc(39) int local39 = AbstractArbBufferGL.anIntArray504[local31] - 2048;
				@Pc(44) int local44 = local39 + this.pattern1OffsetX;
				@Pc(55) int local55 = local44 < -2048 ? local44 + 4096 : local44;
				@Pc(66) int local66 = local55 > 2048 ? local55 - 4096 : local55;
				@Pc(71) int local71 = this.pattern1OffsetY + local29;
				@Pc(82) int local82 = local71 < -2048 ? local71 + 4096 : local71;
				@Pc(91) int local91 = local82 <= 2048 ? local82 : local82 - 4096;
				@Pc(97) int local97 = local39 + this.anInt4007;
				@Pc(106) int local106 = local97 < -2048 ? local97 + 4096 : local97;
				@Pc(117) int local117 = local106 <= 2048 ? local106 : local106 - 4096;
				@Pc(122) int local122 = local29 + this.anInt4006;
				@Pc(133) int local133 = local122 < -2048 ? local122 + 4096 : local122;
				@Pc(142) int local142 = local133 <= 2048 ? local133 : local133 - 4096;
				local11[local31] = this.method3259(local66, local91) || this.method3260(local142, local117) ? 4096 : 0;
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!kt", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.pattern1OffsetX = arg1.method2536();
		} else if (arg0 == 1) {
			this.pattern1OffsetY = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt4007 = arg1.method2536();
		} else if (arg0 == 3) {
			this.anInt4006 = arg1.method2536();
		} else if (arg0 == 4) {
			this.anInt4009 = arg1.method2536();
		} else if (arg0 == 5) {
			this.anInt4001 = arg1.method2536();
		} else if (arg0 == 6) {
			this.anInt4010 = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!kt", name = "c", descriptor = "(III)Z")
	private boolean method3259(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(13) int local13 = (arg1 - arg0) * this.anInt4009 >> 12;
		@Pc(23) int local23 = StructType.anIntArray416[local13 * 255 >> 12 & 0xFF];
		@Pc(30) int local30 = (local23 << 12) / this.anInt4009;
		@Pc(37) int local37 = (local30 << 12) / this.anInt4010;
		@Pc(44) int local44 = local37 * this.anInt4001 >> 12;
		return arg1 + arg0 < local44 && -local44 < arg0 + arg1;
	}

	@OriginalMember(owner = "client!kt", name = "a", descriptor = "(IIB)Z")
	private boolean method3260(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(12) int local12 = this.anInt4009 * (arg1 + arg0) >> 12;
		@Pc(22) int local22 = StructType.anIntArray416[local12 * 255 >> 12 & 0xFF];
		@Pc(29) int local29 = (local22 << 12) / this.anInt4009;
		@Pc(36) int local36 = (local29 << 12) / this.anInt4010;
		@Pc(43) int local43 = this.anInt4001 * local36 >> 12;
		return local43 > arg0 - arg1 && arg0 - arg1 > -local43;
	}

	@OriginalMember(owner = "client!kt", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		LRUCache.method1784();
	}

@OriginalMember(owner = "client!kt", name = "T", descriptor = "Lclient!qt;")
	public static SignedResource aClass199_7;
@OriginalMember(owner = "client!kt", name = "U", descriptor = "F")
	public static float aFloat131;
@OriginalMember(owner = "client!kt", name = "D", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_43 = new Class215(45, 3);
@OriginalMember(owner = "client!kt", name = "L", descriptor = "I")
	public static int anInt4008 = 2;
@OriginalMember(owner = "client!kt", name = "S", descriptor = "I")
	public static int LOGIN_PACKET_CURSOR_POS = 0;
@OriginalMember(owner = "client!kt", name = "a", descriptor = "(BIII)Z")
	public static boolean method3257(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(18) GameObject local18 = (GameObject) MusicPatchNode.method3449(arg1, arg2, arg0);
		@Pc(20) boolean local20 = true;
		if (local18 != null) {
			local20 = Class4_Sub1_Sub17.method4210(local18) & true;
		}
		local18 = (GameObject) Class26.method648(arg1, arg2, arg0, GameObject.class);
		if (local18 != null) {
			local20 &= Class4_Sub1_Sub17.method4210(local18);
		}
		local18 = (GameObject) Class4_Sub19.method2096(arg1, arg2, arg0);
		if (local18 != null) {
			local20 &= Class4_Sub1_Sub17.method4210(local18);
		}
		return local20;
	}
}
