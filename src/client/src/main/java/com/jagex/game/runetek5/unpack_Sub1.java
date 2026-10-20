package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.material.MaterialType;

@OriginalClass("client!ga")
public interface unpack_Sub1 {

	@OriginalMember(owner = "client!ga", name = "a", descriptor = "(II)Z")
	boolean textureAvailable(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!ga", name = "a", descriptor = "(ZIIFII)[I")
	int[] getPixels(@OriginalArg(0) boolean readReversed, @OriginalArg(2) int arg1, @OriginalArg(3) float arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int height);

	@OriginalMember(owner = "client!ga", name = "a", descriptor = "(FIIZII)[F")
	float[] method2394(@OriginalArg(0) float arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3);

	@OriginalMember(owner = "client!ga", name = "a", descriptor = "(ZIZFII)[I")
	int[] getPixelsArgb(@OriginalArg(1) int arg0, @OriginalArg(3) float arg1, @OriginalArg(4) int arg2, @OriginalArg(5) int arg3);

	@OriginalMember(owner = "client!ga", name = "b", descriptor = "(II)Lclient!vf;")
	MaterialType getMetrics(@OriginalArg(1) int arg0);
}
