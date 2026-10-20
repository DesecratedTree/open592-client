package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.MonochromeImageCacheSlot;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static281;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.config.material.texture.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!gi")
public final class MonochromeImageCache {

	@OriginalMember(owner = "client!gi", name = "l", descriptor = "I")
	private int anInt2670 = 0;

	@OriginalMember(owner = "client!gi", name = "n", descriptor = "I")
	private int anInt2672 = -1;

	@OriginalMember(owner = "client!gi", name = "h", descriptor = "Lclient!pk;")
	private Class183 history = new Class183();

	@OriginalMember(owner = "client!gi", name = "p", descriptor = "Z")
	public boolean dirty = false;

	@OriginalMember(owner = "client!gi", name = "o", descriptor = "I")
	private final int anInt2673;

	@OriginalMember(owner = "client!gi", name = "e", descriptor = "I")
	private final int anInt2667;

	@OriginalMember(owner = "client!gi", name = "i", descriptor = "[Lclient!cr;")
	private MonochromeImageCacheSlot[] nodes;

	@OriginalMember(owner = "client!gi", name = "b", descriptor = "[[I")
	private int[][] paletteData;

	@OriginalMember(owner = "client!gi", name = "<init>", descriptor = "(III)V")
	public MonochromeImageCache(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int width) {
		this.anInt2673 = arg1;
		this.anInt2667 = arg0;
		this.nodes = new MonochromeImageCacheSlot[this.anInt2673];
		this.paletteData = new int[this.anInt2667][width];
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(I)V")
	public void method2259() {
		for (@Pc(11) int local11 = 0; local11 < this.anInt2667; local11++) {
			this.paletteData[local11] = null;
		}
		this.paletteData = null;
		this.nodes = null;
		this.history.method4138();
		this.history = null;
	}

	@OriginalMember(owner = "client!gi", name = "c", descriptor = "(I)[[I")
	public int[][] method2263() {
		if (this.anInt2667 != this.anInt2673) {
			throw new RuntimeException("Can only retrieve a full image cache");
		}
		for (@Pc(22) int local22 = 0; local22 < this.anInt2667; local22++) {
			this.nodes[local22] = Class9.aClass4_Sub11_10;
		}
		return this.paletteData;
	}

	@OriginalMember(owner = "client!gi", name = "a", descriptor = "(BI)[I")
	public int[] method2265(@OriginalArg(1) int arg0) {
		if (this.anInt2673 == this.anInt2667) {
			this.dirty = this.nodes[arg0] == null;
			this.nodes[arg0] = Class9.aClass4_Sub11_10;
			return this.paletteData[arg0];
		} else if (this.anInt2667 == 1) {
			this.dirty = this.anInt2672 != arg0;
			this.anInt2672 = arg0;
			return this.paletteData[0];
		} else {
			@Pc(24) MonochromeImageCacheSlot local24 = this.nodes[arg0];
			if (local24 == null) {
				this.dirty = true;
				if (this.anInt2667 > this.anInt2670) {
					local24 = new MonochromeImageCacheSlot(arg0, this.anInt2670);
					this.anInt2670++;
				} else {
					@Pc(59) MonochromeImageCacheSlot local59 = (MonochromeImageCacheSlot) this.history.method4139();
					local24 = new MonochromeImageCacheSlot(arg0, local59.anInt1037);
					this.nodes[local59.anInt1036] = null;
					local59.method5684();
				}
				this.nodes[arg0] = local24;
			} else {
				this.dirty = false;
			}
			this.history.method4133(local24);
			return this.paletteData[local24.anInt1037];
		}
	}

@OriginalMember(owner = "client!gi", name = "k", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array7;
@OriginalMember(owner = "client!gi", name = "g", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_49 = new Class83("skill: ", "Fertigkeit: ", "compétence ", "habilidade: ");
@OriginalMember(owner = "client!gi", name = "a", descriptor = "(BII)Z")
	public static boolean method2260(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(13) LocType local13 = Static267.aClass262_2.method5560(arg0);
		if (arg1 == 11) {
			arg1 = 10;
		}
		if (arg1 >= 5 && arg1 <= 8) {
			arg1 = 4;
		}
		return local13.method4590(arg1);
	}
@OriginalMember(owner = "client!gi", name = "b", descriptor = "(I)V")
	public static void method2262() {
		if (Class4_Sub6_Sub16.anInt3719 != -1) {
			Static231.method3474(-1, -1, Class4_Sub6_Sub16.anInt3719, false);
			Class4_Sub6_Sub16.anInt3719 = -1;
		}
	}
@OriginalMember(owner = "client!gi", name = "a", descriptor = "(IILclient!rs;Lclient!lh;IIILclient!rd;)V")
	public static void method2264(@OriginalArg(0) int arg0, @OriginalArg(2) NPCEntity arg1, @OriginalArg(3) PlayerEntity arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) LocType arg6) {
		@Pc(7) ReflectionCheck local7 = new ReflectionCheck();
		local7.anInt614 = arg0 << 7;
		local7.anInt619 = arg5;
		local7.anInt613 = arg3 << 7;
		if (arg6 != null) {
			local7.aClass202_1 = arg6;
			@Pc(145) int local145 = arg6.anInt5816;
			@Pc(148) int local148 = arg6.anInt5801;
			if (arg4 == 1 || arg4 == 3) {
				local145 = arg6.anInt5801;
				local148 = arg6.anInt5816;
			}
			local7.anInt620 = arg6.anInt5806;
			local7.anInt622 = arg6.anInt5843;
			local7.anInt612 = arg3 + local148 << 7;
			local7.anIntArray37 = arg6.soundGroupIds;
			local7.anInt615 = local145 + arg0 << 7;
			local7.anInt616 = arg6.anInt5821;
			local7.anInt611 = arg6.anInt5849 << 7;
			local7.anInt623 = arg6.anInt5844;
			if (arg6.alternativeIds != null) {
				local7.aBoolean66 = true;
				local7.method638();
			}
			if (local7.anIntArray37 != null) {
				local7.anInt618 = local7.anInt623 + (int) ((double) (local7.anInt622 - local7.anInt623) * Math.random());
			}
			Static281.aClass183_34.method4137(local7);
			return;
		}
		if (arg1 != null) {
			local7.aClass16_Sub1_Sub5_Sub2_1 = arg1;
			@Pc(42) NPCType local42 = arg1.npcType;
			if (local42.anIntArray544 != null) {
				local7.aBoolean66 = true;
				local42 = local42.method5574(Class4_Sub6_Sub24.aClass114_1);
			}
			if (local42 != null) {
				local7.anInt615 = arg0 + local42.anInt7201 << 7;
				local7.anInt612 = arg3 + local42.anInt7201 << 7;
				local7.anInt616 = MusicPatch.method4392(arg1);
				local7.anInt611 = local42.anInt7238 << 7;
				local7.anInt620 = local42.anInt7235;
			}
			Class93.aClass183_20.method4137(local7);
			return;
		}
		if (arg2 == null) {
			return;
		}
		local7.aClass16_Sub1_Sub5_Sub1_2 = arg2;
		local7.anInt615 = arg2.getSize() + arg0 << 7;
		local7.anInt612 = arg2.getSize() + arg3 << 7;
		local7.anInt616 = Class4_Sub7.method631(arg2);
		local7.anInt620 = arg2.anInt4348;
		local7.anInt611 = arg2.isNpc << 7;
		AbstractArbBufferGL.aClass102_38.method2703((long) arg2.anInt6037, local7);
		return;
	}
}
