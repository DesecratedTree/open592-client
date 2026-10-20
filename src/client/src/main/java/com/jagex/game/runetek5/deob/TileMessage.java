package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.EntityNode;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.Class56;
@OriginalClass("client!wu")
public final class TileMessage extends EntityNode {

	@OriginalMember(owner = "client!wu", name = "k", descriptor = "Ljava/lang/String;")
	public String aString71;

	@OriginalMember(owner = "client!wu", name = "m", descriptor = "I")
	public int anInt7366;

	@OriginalMember(owner = "client!wu", name = "n", descriptor = "I")
	public int anInt7367;

	@OriginalMember(owner = "client!wu", name = "t", descriptor = "I")
	public int anInt7372;

	@OriginalMember(owner = "client!wu", name = "v", descriptor = "I")
	public int anInt7374;

	@OriginalMember(owner = "client!wu", name = "w", descriptor = "I")
	public int anInt7375;

	@OriginalMember(owner = "client!wu", name = "x", descriptor = "I")
	public int anInt7376;

@OriginalMember(owner = "client!wu", name = "l", descriptor = "[Z")
	public static boolean[] aBooleanArray25;
@OriginalMember(owner = "client!wu", name = "r", descriptor = "I")
	public static int anInt7371;
@OriginalMember(owner = "client!wu", name = "u", descriptor = "I")
	public static int anInt7373;
@OriginalMember(owner = "client!wu", name = "s", descriptor = "J")
	public static long aLong223 = 0L;
@OriginalMember(owner = "client!wu", name = "a", descriptor = "(IIIZ)I")
	public static int method5678(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) boolean arg2) {
		@Pc(8) Class4_Sub25 local8 = PlayerVarDomain.method2834(arg2, arg1);
		if (local8 == null) {
			return 0;
		} else if (arg0 >= 0 && local8.anIntArray229.length > arg0) {
			return local8.anIntArray229[arg0];
		} else {
			return 0;
		}
	}
@OriginalMember(owner = "client!wu", name = "a", descriptor = "(II)V")
	public static void method5679(@OriginalArg(1) int arg0) {
		TextureCombinerOp.anInt5156 = arg0;
		Class9.anInt6920 = -1;
		Class9.anInt6920 = -1;
		JavaMatrix.method5160();
	}
@OriginalMember(owner = "client!wu", name = "a", descriptor = "([BZ)[B")
	public static byte[] method5680(@OriginalArg(0) byte[] arg0) {
		@Pc(8) Packet local8 = new Packet(arg0);
		@Pc(12) int local12 = local8.method2490();
		@Pc(21) int local21 = local8.method2529();
		if (local21 < 0 || GpuFaceNormal.anInt6952 != 0 && GpuFaceNormal.anInt6952 < local21) {
			throw new RuntimeException();
		} else if (local12 == 0) {
			@Pc(88) byte[] local88 = new byte[local21];
			local8.method2508(local21, local88);
			return local88;
		} else {
			@Pc(42) int local42 = local8.method2529();
			if (local42 < 0 || GpuFaceNormal.anInt6952 != 0 && GpuFaceNormal.anInt6952 < local42) {
				throw new RuntimeException();
			}
			@Pc(66) byte[] local66 = new byte[local42];
			if (local12 == 1) {
				Static139.bunzip(local66, local42, arg0, local21);
			} else {
				Class56.aClass187_1.method4171(local66, local8);
			}
			return local66;
		}
	}
}
