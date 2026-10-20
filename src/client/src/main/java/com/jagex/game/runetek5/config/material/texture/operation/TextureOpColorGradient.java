package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.math.BigInteger;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static2;
import com.jagex.game.runetek5.deob.Static214;
import com.jagex.game.runetek5.deob.Static433;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
@OriginalClass("client!ed")
public final class TextureOpColorGradient extends TextureOperation {

	@OriginalMember(owner = "client!ed", name = "J", descriptor = "[[I")
	private int[][] colorStops;

	@OriginalMember(owner = "client!ed", name = "E", descriptor = "[I")
	private final int[] gradientLookup = new int[257];

	@OriginalMember(owner = "client!ed", name = "<init>", descriptor = "()V")
	public TextureOpColorGradient() {
		super(1, false);
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(Z)V")
	@Override
	public void postDecode() {
		if (this.colorStops == null) {
			this.setPreset(1);
		}
		this.buildRamp();
	}

	@OriginalMember(owner = "client!ed", name = "f", descriptor = "(I)V")
	private void buildRamp() {
		@Pc(13) int local13 = this.colorStops.length;
		if (local13 <= 0) {
			return;
		}
		for (@Pc(20) int local20 = 0; local20 < 257; local20++) {
			@Pc(24) int local24 = 0;
			@Pc(28) int local28 = local20 << 4;
			for (@Pc(30) int local30 = 0; local13 > local30 && this.colorStops[local30][0] <= local28; local30++) {
				local24++;
			}
			@Pc(75) int local75;
			@Pc(67) int local67;
			@Pc(71) int local71;
			@Pc(63) int[] local63;
			if (local13 <= local24) {
				local63 = this.colorStops[local13 - 1];
				local67 = local63[2];
				local71 = local63[3];
				local75 = local63[1];
			} else {
				local63 = this.colorStops[local24];
				if (local24 <= 0) {
					local67 = local63[2];
					local71 = local63[3];
					local75 = local63[1];
				} else {
					@Pc(105) int[] local105 = this.colorStops[local24 - 1];
					@Pc(123) int local123 = (local28 - local105[0] << 12) / (local63[0] - local105[0]);
					@Pc(127) int local127 = 4096 - local123;
					local75 = local123 * local63[1] + local127 * local105[1] >> 12;
					local67 = local123 * local63[2] + local105[2] * local127 >> 12;
					local71 = local127 * local105[3] + local123 * local63[3] >> 12;
				}
			}
			local71 >>= 0x4;
			local75 >>= 0x4;
			local67 >>= 0x4;
			if (local71 < 0) {
				local71 = 0;
			} else if (local71 > 255) {
				local71 = 255;
			}
			if (local75 < 0) {
				local75 = 0;
			} else if (local75 > 255) {
				local75 = 255;
			}
			if (local67 < 0) {
				local67 = 0;
			} else if (local67 > 255) {
				local67 = 255;
			}
			this.gradientLookup[local20] = local71 | local67 << 8 | local75 << 16;
		}
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 != 0) {
			return;
		}
		@Pc(9) int local9 = arg1.method2490();
		if (local9 != 0) {
			this.setPreset(local9);
			return;
		}
		this.colorStops = new int[arg1.method2490()][4];
		for (@Pc(23) int local23 = 0; local23 < this.colorStops.length; local23++) {
			this.colorStops[local23][0] = arg1.method2536();
			this.colorStops[local23][1] = arg1.method2490() << 4;
			this.colorStops[local23][2] = arg1.method2490() << 4;
			this.colorStops[local23][3] = arg1.method2490() << 4;
		}
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(IB)V")
	private void setPreset(@OriginalArg(0) int preset) {
		if (preset == 0) {
			return;
		}
		if (preset == 1) {
			this.colorStops = new int[2][4];
			this.colorStops[0][3] = 0;
			this.colorStops[0][1] = 0;
			this.colorStops[0][0] = 0;
			this.colorStops[0][2] = 0;
			this.colorStops[1][1] = 4096;
			this.colorStops[1][3] = 4096;
			this.colorStops[1][2] = 4096;
			this.colorStops[1][0] = 4096;
		} else if (preset == 2) {
			this.colorStops = new int[8][4];
			this.colorStops[0][3] = 2361;
			this.colorStops[0][0] = 0;
			this.colorStops[0][1] = 2650;
			this.colorStops[0][2] = 2602;
			this.colorStops[1][3] = 1558;
			this.colorStops[1][1] = 2313;
			this.colorStops[1][2] = 1799;
			this.colorStops[1][0] = 2867;
			this.colorStops[2][3] = 1413;
			this.colorStops[2][1] = 2618;
			this.colorStops[2][0] = 3072;
			this.colorStops[2][2] = 1734;
			this.colorStops[3][0] = 3276;
			this.colorStops[3][3] = 947;
			this.colorStops[3][1] = 2296;
			this.colorStops[3][2] = 1220;
			this.colorStops[4][3] = 722;
			this.colorStops[4][2] = 963;
			this.colorStops[4][0] = 3481;
			this.colorStops[4][1] = 2072;
			this.colorStops[5][3] = 1766;
			this.colorStops[5][0] = 3686;
			this.colorStops[5][1] = 2730;
			this.colorStops[5][2] = 2152;
			this.colorStops[6][1] = 2232;
			this.colorStops[6][0] = 3891;
			this.colorStops[6][3] = 915;
			this.colorStops[6][2] = 1060;
			this.colorStops[7][0] = 4096;
			this.colorStops[7][1] = 1686;
			this.colorStops[7][2] = 1413;
			this.colorStops[7][3] = 1140;
		} else if (preset == 3) {
			this.colorStops = new int[7][4];
			this.colorStops[0][0] = 0;
			this.colorStops[0][1] = 0;
			this.colorStops[0][3] = 4096;
			this.colorStops[0][2] = 0;
			this.colorStops[1][1] = 0;
			this.colorStops[1][2] = 4096;
			this.colorStops[1][0] = 663;
			this.colorStops[1][3] = 4096;
			this.colorStops[2][2] = 4096;
			this.colorStops[2][1] = 0;
			this.colorStops[2][3] = 0;
			this.colorStops[2][0] = 1363;
			this.colorStops[3][2] = 4096;
			this.colorStops[3][0] = 2048;
			this.colorStops[3][3] = 0;
			this.colorStops[3][1] = 4096;
			this.colorStops[4][2] = 0;
			this.colorStops[4][1] = 4096;
			this.colorStops[4][0] = 2727;
			this.colorStops[4][3] = 0;
			this.colorStops[5][3] = 4096;
			this.colorStops[5][1] = 4096;
			this.colorStops[5][2] = 0;
			this.colorStops[5][0] = 3411;
			this.colorStops[6][0] = 4096;
			this.colorStops[6][3] = 4096;
			this.colorStops[6][1] = 0;
			this.colorStops[6][2] = 0;
		} else if (preset == 4) {
			this.colorStops = new int[6][4];
			this.colorStops[0][0] = 0;
			this.colorStops[0][1] = 0;
			this.colorStops[0][3] = 0;
			this.colorStops[0][2] = 0;
			this.colorStops[1][0] = 1843;
			this.colorStops[1][2] = 0;
			this.colorStops[1][1] = 0;
			this.colorStops[1][3] = 1493;
			this.colorStops[2][1] = 0;
			this.colorStops[2][2] = 0;
			this.colorStops[2][0] = 2457;
			this.colorStops[2][3] = 2939;
			this.colorStops[3][1] = 0;
			this.colorStops[3][3] = 3565;
			this.colorStops[3][0] = 2781;
			this.colorStops[3][2] = 1124;
			this.colorStops[4][0] = 3481;
			this.colorStops[4][3] = 4031;
			this.colorStops[4][2] = 3084;
			this.colorStops[4][1] = 546;
			this.colorStops[5][0] = 4096;
			this.colorStops[5][3] = 4096;
			this.colorStops[5][1] = 4096;
			this.colorStops[5][2] = 4096;
		} else if (preset == 5) {
			this.colorStops = new int[16][4];
			this.colorStops[0][0] = 0;
			this.colorStops[0][2] = 192;
			this.colorStops[0][1] = 80;
			this.colorStops[0][3] = 321;
			this.colorStops[1][2] = 449;
			this.colorStops[1][3] = 562;
			this.colorStops[1][1] = 321;
			this.colorStops[1][0] = 155;
			this.colorStops[2][3] = 803;
			this.colorStops[2][1] = 578;
			this.colorStops[2][2] = 690;
			this.colorStops[2][0] = 389;
			this.colorStops[3][2] = 995;
			this.colorStops[3][1] = 947;
			this.colorStops[3][3] = 1140;
			this.colorStops[3][0] = 671;
			this.colorStops[4][2] = 1397;
			this.colorStops[4][1] = 1285;
			this.colorStops[4][3] = 1509;
			this.colorStops[4][0] = 897;
			this.colorStops[5][0] = 1175;
			this.colorStops[5][3] = 1413;
			this.colorStops[5][1] = 1525;
			this.colorStops[5][2] = 1429;
			this.colorStops[6][1] = 1734;
			this.colorStops[6][0] = 1368;
			this.colorStops[6][2] = 1461;
			this.colorStops[6][3] = 1333;
			this.colorStops[7][2] = 1525;
			this.colorStops[7][3] = 1702;
			this.colorStops[7][1] = 1413;
			this.colorStops[7][0] = 1507;
			this.colorStops[8][3] = 2056;
			this.colorStops[8][1] = 1108;
			this.colorStops[8][2] = 1590;
			this.colorStops[8][0] = 1736;
			this.colorStops[9][3] = 2666;
			this.colorStops[9][2] = 2056;
			this.colorStops[9][1] = 1766;
			this.colorStops[9][0] = 2088;
			this.colorStops[10][2] = 2586;
			this.colorStops[10][1] = 2409;
			this.colorStops[10][0] = 2355;
			this.colorStops[10][3] = 3276;
			this.colorStops[11][2] = 3148;
			this.colorStops[11][0] = 2691;
			this.colorStops[11][1] = 3116;
			this.colorStops[11][3] = 3228;
			this.colorStops[12][0] = 3031;
			this.colorStops[12][3] = 3196;
			this.colorStops[12][1] = 3806;
			this.colorStops[12][2] = 3710;
			this.colorStops[13][2] = 3421;
			this.colorStops[13][3] = 3019;
			this.colorStops[13][0] = 3522;
			this.colorStops[13][1] = 3437;
			this.colorStops[14][2] = 3148;
			this.colorStops[14][0] = 3727;
			this.colorStops[14][3] = 3228;
			this.colorStops[14][1] = 3116;
			this.colorStops[15][2] = 2505;
			this.colorStops[15][0] = 4096;
			this.colorStops[15][3] = 2746;
			this.colorStops[15][1] = 2377;
		} else if (preset == 6) {
			this.colorStops = new int[4][4];
			this.colorStops[0][3] = 0;
			this.colorStops[0][1] = 0;
			this.colorStops[0][2] = 4096;
			this.colorStops[0][0] = 2048;
			this.colorStops[1][0] = 2867;
			this.colorStops[1][1] = 4096;
			this.colorStops[1][2] = 4096;
			this.colorStops[1][3] = 0;
			this.colorStops[2][0] = 3276;
			this.colorStops[2][1] = 4096;
			this.colorStops[2][2] = 4096;
			this.colorStops[2][3] = 0;
			this.colorStops[3][2] = 0;
			this.colorStops[3][3] = 0;
			this.colorStops[3][0] = 4096;
			this.colorStops[3][1] = 4096;
		} else {
			throw new RuntimeException("Invalid gradient preset");
		}
	}

	@OriginalMember(owner = "client!ed", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(28) int[] local28 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(32) int[] local32 = local11[0];
			@Pc(36) int[] local36 = local11[1];
			@Pc(40) int[] local40 = local11[2];
			for (@Pc(42) int local42 = 0; local42 < MidiPcmStream.anInt1616; local42++) {
				@Pc(50) int local50 = local28[local42] >> 4;
				if (local50 < 0) {
					local50 = 0;
				}
				if (local50 > 256) {
					local50 = 256;
				}
				local50 = this.gradientLookup[local50];
				local32[local42] = local50 >> 12 & 0xFF0;
				local36[local42] = local50 >> 4 & 0xFF0;
				local40[local42] = (local50 & 0xFF) << 4;
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!ed", name = "P", descriptor = "[I")
	public static int[] anIntArray143;
@OriginalMember(owner = "client!ed", name = "G", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_19 = new Class215(0, -1);
@OriginalMember(owner = "client!ed", name = "L", descriptor = "Ljava/math/BigInteger;")
	public static final BigInteger aBigInteger1 = new BigInteger("65537");
@OriginalMember(owner = "client!ed", name = "M", descriptor = "I")
	public static int anInt1763 = -1;
@OriginalMember(owner = "client!ed", name = "N", descriptor = "I")
	public static int anInt1764 = 0;
@OriginalMember(owner = "client!ed", name = "O", descriptor = "I")
	public static int anInt1765 = 0;
@OriginalMember(owner = "client!ed", name = "a", descriptor = "()V")
	public static void method1541() {
		@Pc(3) int local3;
		@Pc(6) int local6;
		@Pc(9) int local9;
		if (Static214.aClass164ArrayArrayArray4 != null) {
			for (local3 = 0; local3 < Static214.aClass164ArrayArrayArray4.length; local3++) {
				for (local6 = 0; local6 < GpuFaceNormal.menuOpenWidth; local6++) {
					for (local9 = 0; local9 < GlModel.anInt3361; local9++) {
						if (Static214.aClass164ArrayArrayArray4[local3][local6][local9] != null) {
							Static214.aClass164ArrayArrayArray4[local3][local6][local9].method3915();
						}
						Static214.aClass164ArrayArrayArray4[local3][local6][local9] = null;
					}
				}
			}
		}
		Static214.aClass164ArrayArrayArray4 = null;
		Class4_Sub6_Sub4.aClass65Array1 = null;
		if (Static2.aClass164ArrayArrayArray1 != null) {
			for (local3 = 0; local3 < Static2.aClass164ArrayArrayArray1.length; local3++) {
				for (local6 = 0; local6 < GpuFaceNormal.menuOpenWidth; local6++) {
					for (local9 = 0; local9 < GlModel.anInt3361; local9++) {
						if (Static2.aClass164ArrayArrayArray1[local3][local6][local9] != null) {
							Static2.aClass164ArrayArrayArray1[local3][local6][local9].method3915();
						}
						Static2.aClass164ArrayArrayArray1[local3][local6][local9] = null;
					}
				}
			}
		}
		Static2.aClass164ArrayArrayArray1 = null;
		FileWorker.aClass65Array3 = null;
		Class70.aClass164ArrayArrayArray5 = null;
		Class16_Sub1_Sub6.aClass65Array4 = null;
		OpenGlArbElementArrayBuffer.anInt1878 = 0;
		if (TextureOpSprite.aClass136Array2 != null) {
			for (local3 = 0; local3 < OpenGlArbElementArrayBuffer.anInt1878; local3++) {
				TextureOpSprite.aClass136Array2[local3] = null;
			}
		}
		if (Class4_Sub6_Sub38.aClass16_Sub1Array3 != null) {
			for (local3 = 0; local3 < Static1.anInt6; local3++) {
				Class4_Sub6_Sub38.aClass16_Sub1Array3[local3] = null;
			}
			Static1.anInt6 = 0;
		}
		if (StructTypeList.aClass191Array1 != null) {
			for (local3 = 0; local3 < Ground.anInt7322; local3++) {
				StructTypeList.aClass191Array1[local3] = null;
			}
			for (local6 = 0; local6 < RemoteFileWorker.anInt4487; local6++) {
				for (local9 = 0; local9 < GpuFaceNormal.menuOpenWidth; local9++) {
					for (@Pc(160) int local160 = 0; local160 < GlModel.anInt3361; local160++) {
						Terrain.aLongArrayArrayArray2[local6][local9][local160] = 0L;
					}
				}
			}
			Ground.anInt7322 = 0;
		}
		Class105.anIntArrayArrayArray13 = null;
		Class190.method4217();
		Class4_Sub3.aClass84_2.method2310();
		AbstractArbBufferGL.aByteArrayArray29 = null;
		Class145_Sub2.anIntArrayArray64 = null;
		Class204.aByteArrayArray26 = null;
		Static433.aClass70_1 = null;
		MECType.aClass31_3 = null;
		Class95_Sub3.aClass19_37 = null;
	}
}
