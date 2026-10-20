package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Static366;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static47;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.config.material.texture.*;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.deob.Class27_Sub3;
@OriginalClass("client!ms")
public final class ColorImageCache {

	@OriginalMember(owner = "client!ms", name = "a", descriptor = "I")
	private int cachedId = 0;

	@OriginalMember(owner = "client!ms", name = "k", descriptor = "I")
	private int count = -1;

	@OriginalMember(owner = "client!ms", name = "b", descriptor = "Lclient!pk;")
	private Class183 history = new Class183();

	@OriginalMember(owner = "client!ms", name = "o", descriptor = "Z")
	public boolean dirty = false;

	@OriginalMember(owner = "client!ms", name = "g", descriptor = "I")
	private final int totalRows;

	@OriginalMember(owner = "client!ms", name = "m", descriptor = "I")
	private final int maxCachedRows;

	@OriginalMember(owner = "client!ms", name = "f", descriptor = "[Lclient!gd;")
	private MonoStereoData[] aClass4_Sub20Array1;

	@OriginalMember(owner = "client!ms", name = "d", descriptor = "[[[I")
	private int[][][] rgbData;

	@OriginalMember(owner = "client!ms", name = "<init>", descriptor = "(III)V")
	public ColorImageCache(@OriginalArg(0) int capacity, @OriginalArg(1) int height, @OriginalArg(2) int width) {
		this.totalRows = capacity;
		this.maxCachedRows = height;
		this.aClass4_Sub20Array1 = new MonoStereoData[this.maxCachedRows];
		this.rgbData = new int[this.totalRows][3][width];
	}

	@OriginalMember(owner = "client!ms", name = "a", descriptor = "(BI)[[I")
	public int[][] get(@OriginalArg(1) int arg0) {
		if (this.totalRows == this.maxCachedRows) {
			this.dirty = this.aClass4_Sub20Array1[arg0] == null;
			this.aClass4_Sub20Array1[arg0] = Static366.aClass4_Sub20_1;
			return this.rgbData[arg0];
		} else if (this.totalRows == 1) {
			this.dirty = this.count != arg0;
			this.count = arg0;
			return this.rgbData[0];
		} else {
			@Pc(72) MonoStereoData local72 = this.aClass4_Sub20Array1[arg0];
			if (local72 == null) {
				this.dirty = true;
				if (this.cachedId >= this.totalRows) {
					@Pc(97) MonoStereoData local97 = (MonoStereoData) this.history.method4139();
					local72 = new MonoStereoData(arg0, local97.anInt2616);
					this.aClass4_Sub20Array1[local97.anInt2614] = null;
					local97.method5684();
				} else {
					local72 = new MonoStereoData(arg0, this.cachedId);
					this.cachedId++;
				}
				this.aClass4_Sub20Array1[arg0] = local72;
			} else {
				this.dirty = false;
			}
			this.history.method4133(local72);
			return this.rgbData[local72.anInt2616];
		}
	}

	@OriginalMember(owner = "client!ms", name = "a", descriptor = "(B)[[[I")
	public int[][][] getAllPalettes() {
		if (this.totalRows != this.maxCachedRows) {
			throw new RuntimeException("Can only retrieve a full image cache");
		}
		for (@Pc(21) int local21 = 0; local21 < this.totalRows; local21++) {
			this.aClass4_Sub20Array1[local21] = Static366.aClass4_Sub20_1;
		}
		return this.rgbData;
	}

	@OriginalMember(owner = "client!ms", name = "c", descriptor = "(B)V")
	public void reset() {
		for (@Pc(7) int local7 = 0; local7 < this.totalRows; local7++) {
			this.rgbData[local7][0] = null;
			this.rgbData[local7][1] = null;
			this.rgbData[local7][2] = null;
			this.rgbData[local7] = null;
		}
		this.rgbData = null;
		this.aClass4_Sub20Array1 = null;
		this.history.method4138();
		this.history = null;
	}

@OriginalMember(owner = "client!ms", name = "n", descriptor = "I")
	public static int anInt4755;
@OriginalMember(owner = "client!ms", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_209 = new Class22(102, 5);
@OriginalMember(owner = "client!ms", name = "l", descriptor = "[I")
	public static final int[] anIntArray311 = new int[14];
@OriginalMember(owner = "client!ms", name = "a", descriptor = "(ILclient!ya;II)V")
	public static void method3682(@OriginalArg(0) int arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(2) int arg2) {
		Static1.aClass155ArrayArray1 = new TileLighting[arg2][arg0];
		Static57.aClass19_7 = arg1;
		if (Class4_Sub6_Sub33.anIntArray491 != null) {
			RequestManager.aClass99_5 = Class27_Sub3.method3186(Class4_Sub6_Sub33.anIntArray491[4], Class4_Sub6_Sub33.anIntArray491[2], Class4_Sub6_Sub33.anIntArray491[5], Class4_Sub6_Sub33.anIntArray491[0], Class4_Sub6_Sub33.anIntArray491[3], Class4_Sub6_Sub33.anIntArray491[1]);
		}
		Static47.aClass155_1 = new TileLighting();
		RequestManager.method3816();
	}
}
