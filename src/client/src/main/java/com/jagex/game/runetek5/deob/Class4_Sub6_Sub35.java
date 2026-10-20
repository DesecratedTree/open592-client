package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
@OriginalClass("client!ug")
public final class Class4_Sub6_Sub35 extends TextureOperation {

	@OriginalMember(owner = "client!ug", name = "E", descriptor = "I")
	private int anInt6657 = 409;

	@OriginalMember(owner = "client!ug", name = "J", descriptor = "[I")
	private final int[] anIntArray506 = new int[3];

	@OriginalMember(owner = "client!ug", name = "I", descriptor = "I")
	private int anInt6661 = 4096;

	@OriginalMember(owner = "client!ug", name = "M", descriptor = "I")
	private int anInt6663 = 4096;

	@OriginalMember(owner = "client!ug", name = "N", descriptor = "I")
	private int anInt6664 = 4096;

	@OriginalMember(owner = "client!ug", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub35() {
		super(1, false);
	}

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(26) int[][] local26 = this.getChildColorOutput(0, arg0);
			@Pc(30) int[] local30 = local26[0];
			@Pc(34) int[] local34 = local26[1];
			@Pc(38) int[] local38 = local26[2];
			@Pc(42) int[] local42 = local11[0];
			@Pc(46) int[] local46 = local11[1];
			@Pc(50) int[] local50 = local11[2];
			for (@Pc(52) int local52 = 0; local52 < MidiPcmStream.anInt1616; local52++) {
				@Pc(58) int local58 = local30[local52];
				@Pc(65) int local65 = local58 - this.anIntArray506[0];
				if (local65 < 0) {
					local65 = -local65;
				}
				if (this.anInt6657 < local65) {
					local42[local52] = local58;
					local46[local52] = local34[local52];
					local50[local52] = local38[local52];
				} else {
					@Pc(99) int local99 = local34[local52];
					local65 = local99 - this.anIntArray506[1];
					if (local65 < 0) {
						local65 = -local65;
					}
					if (this.anInt6657 < local65) {
						local42[local52] = local58;
						local46[local52] = local99;
						local50[local52] = local38[local52];
					} else {
						@Pc(139) int local139 = local38[local52];
						local65 = local139 - this.anIntArray506[2];
						if (local65 < 0) {
							local65 = -local65;
						}
						if (this.anInt6657 < local65) {
							local42[local52] = local58;
							local46[local52] = local99;
							local50[local52] = local139;
						} else {
							local42[local52] = this.anInt6661 * local58 >> 12;
							local46[local52] = local99 * this.anInt6663 >> 12;
							local50[local52] = this.anInt6664 * local139 >> 12;
						}
					}
				}
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!ug", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt6657 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt6664 = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt6663 = arg1.method2536();
		} else if (arg0 == 3) {
			this.anInt6661 = arg1.method2536();
		} else if (arg0 == 4) {
			@Pc(55) int local55 = arg1.method2493();
			this.anIntArray506[1] = local55 >> 4 & 0xFF0;
			this.anIntArray506[0] = (local55 & 0xFF0000) << 4;
			this.anIntArray506[2] = local55 >> 12 & 0x0;
		}
	}

@OriginalMember(owner = "client!ug", name = "K", descriptor = "Lclient!ga;")
	public static unpack_Sub1 anInterface7_8;
@OriginalMember(owner = "client!ug", name = "C", descriptor = "Lclient!vo;")
	public static PingWorker aClass256_1 = new PingWorker();
@OriginalMember(owner = "client!ug", name = "L", descriptor = "I")
	public static int anInt6662 = -1;
@OriginalMember(owner = "client!ug", name = "a", descriptor = "(ZIZ)I")
	public static int method5212(@OriginalArg(1) int arg0) {
		@Pc(14) Class4_Sub25 local14 = PlayerVarDomain.method2834(false, arg0);
		if (local14 == null) {
			return LRUCache.aClass75_1.method2079(arg0).anInt207;
		}
		@Pc(24) int local24 = 0;
		for (@Pc(26) int local26 = 0; local26 < local14.anIntArray230.length; local26++) {
			if (local14.anIntArray230[local26] == -1) {
				local24++;
			}
		}
		return local24 + LRUCache.aClass75_1.method2079(arg0).anInt207 - local14.anIntArray230.length;
	}
@OriginalMember(owner = "client!ug", name = "a", descriptor = "(II[BIIIII[B)V")
	public static void method5213(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) byte[] arg7) {
		@Pc(10) int local10 = -(arg3 >> 2);
		@Pc(15) int local15 = -(arg3 & 0x3);
		for (@Pc(23) int local23 = -arg0; local23 < 0; local23++) {
			@Pc(31) int local31;
			for (@Pc(27) int local27 = local10; local27 < 0; local27++) {
				local31 = arg1++;
				arg2[local31] += arg7[arg4++];
				@Pc(43) int local43 = arg1++;
				arg2[local43] += arg7[arg4++];
				@Pc(55) int local55 = arg1++;
				arg2[local55] += arg7[arg4++];
				@Pc(67) int local67 = arg1++;
				arg2[local67] += arg7[arg4++];
			}
			for (@Pc(82) int local82 = local15; local82 < 0; local82++) {
				local31 = arg1++;
				arg2[local31] += arg7[arg4++];
			}
			arg1 += arg6;
			arg4 += arg5;
		}
	}
}
