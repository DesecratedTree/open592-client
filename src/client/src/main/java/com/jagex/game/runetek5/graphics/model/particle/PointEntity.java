package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.graphics.model.particle.*;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
@OriginalClass("client!wv")
public class PointEntity extends ParticleNode {

	@OriginalMember(owner = "client!wv", name = "r", descriptor = "I")
	public int x;

	@OriginalMember(owner = "client!wv", name = "s", descriptor = "I")
	public int size;

	@OriginalMember(owner = "client!wv", name = "u", descriptor = "Z")
	public boolean lightIntensityAdjustment;

	@OriginalMember(owner = "client!wv", name = "x", descriptor = "I")
	public int colorArgb;

	@OriginalMember(owner = "client!wv", name = "y", descriptor = "I")
	public int z;

	@OriginalMember(owner = "client!wv", name = "C", descriptor = "I")
	public int y;

	@OriginalMember(owner = "client!wv", name = "D", descriptor = "I")
	public int materialId;

	@OriginalMember(owner = "client!wv", name = "v", descriptor = "B")
	public byte priority = 5;

	@OriginalMember(owner = "client!wv", name = "<init>", descriptor = "()V")
	protected PointEntity() {
	}

@OriginalMember(owner = "client!wv", name = "z", descriptor = "I")
	public static int anInt4074;
@OriginalMember(owner = "client!wv", name = "A", descriptor = "I")
	public static int anInt4075 = 0;
@OriginalMember(owner = "client!wv", name = "B", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_89 = new Class83("flash1:", "blinken1:", "clignotant1:", "flash1:");
@OriginalMember(owner = "client!wv", name = "a", descriptor = "(IIIBII)I")
	public static int method3300(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (Class16_Sub1_Sub6.aClass65Array4 == null) {
			return 0;
		} else if (arg4 < 3) {
			@Pc(21) int local21 = arg1 >> 7;
			@Pc(25) int local25 = arg3 >> 7;
			if (arg0 < 0 || arg2 < 0 || GLBillowTextureData.anInt5666 - 1 < arg0 || Static283.anInt5187 - 1 < arg2) {
				return 0;
			} else if (local21 >= 1 && local25 >= 1 && local21 <= GLBillowTextureData.anInt5666 - 1 && Static283.anInt5187 - 1 >= local25) {
				@Pc(104) boolean local104 = (SeqTypeList.aByteArrayArrayArray2[1][arg1 >> 7][arg3 >> 7] & 0x2) != 0;
				@Pc(129) boolean local129;
				@Pc(148) boolean local148;
				if ((arg1 & 0x7F) == 0) {
					local129 = (SeqTypeList.aByteArrayArrayArray2[1][local21 - 1][arg3 >> 7] & 0x2) != 0;
					local148 = (SeqTypeList.aByteArrayArrayArray2[1][local21][arg3 >> 7] & 0x2) != 0;
					if (local148 != local129) {
						local104 = (SeqTypeList.aByteArrayArrayArray2[1][arg0][arg2] & 0x2) != 0;
					}
				}
				if ((arg3 & 0x7F) == 0) {
					local129 = (SeqTypeList.aByteArrayArrayArray2[1][arg1 >> 7][local25 - 1] & 0x2) != 0;
					local148 = (SeqTypeList.aByteArrayArrayArray2[1][arg1 >> 7][local25] & 0x2) != 0;
					if (local129 != local148) {
						local104 = (SeqTypeList.aByteArrayArrayArray2[1][arg0][arg2] & 0x2) != 0;
					}
				}
				if (local104) {
					arg4++;
				}
				return Class16_Sub1_Sub6.aClass65Array4[arg4].a(arg1, arg3);
			} else {
				return 0;
			}
		} else {
			return Class16_Sub1_Sub6.aClass65Array4[arg4].a(arg1, arg3);
		}
	}
@OriginalMember(owner = "client!wv", name = "e", descriptor = "(I)I")
	public static int method3302() {
		return 6;
	}
}
