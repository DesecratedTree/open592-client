package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.LegacyMouseManager;
@OriginalClass("client!pe")
public abstract class Class16_Sub1 extends Class16 {

	@OriginalMember(owner = "client!pe", name = "m", descriptor = "I")
	public int anInt6890;

	@OriginalMember(owner = "client!pe", name = "u", descriptor = "S")
	public short aShort96;

	@OriginalMember(owner = "client!pe", name = "l", descriptor = "B")
	public final byte aByte83;

	@OriginalMember(owner = "client!pe", name = "n", descriptor = "S")
	public short aShort94;

	@OriginalMember(owner = "client!pe", name = "q", descriptor = "I")
	public int anInt6893;

	@OriginalMember(owner = "client!pe", name = "s", descriptor = "S")
	public short aShort95;

	@OriginalMember(owner = "client!pe", name = "i", descriptor = "I")
	public int anInt6889;

	@OriginalMember(owner = "client!pe", name = "v", descriptor = "S")
	public short aShort97;

	@OriginalMember(owner = "client!pe", name = "p", descriptor = "I")
	public int anInt6892;

	@OriginalMember(owner = "client!pe", name = "k", descriptor = "Z")
	public final boolean aBoolean629;

	@OriginalMember(owner = "client!pe", name = "j", descriptor = "B")
	public byte aByte82;

	@OriginalMember(owner = "client!pe", name = "<init>", descriptor = "(IIIIIIIIZB)V")
	protected Class16_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) boolean arg8, @OriginalArg(9) byte arg9) {
		this.aShort96 = (short) arg6;
		this.aByte83 = arg9;
		this.aShort94 = (short) arg4;
		this.anInt6893 = arg1;
		this.aShort95 = (short) arg7;
		this.anInt6889 = arg2;
		this.aShort97 = (short) arg5;
		this.anInt6892 = arg3;
		this.aBoolean629 = arg8;
		this.aByte82 = (byte) arg0;
	}

	@OriginalMember(owner = "client!pe", name = "e", descriptor = "(I)I")
	public abstract int method5342();

	@OriginalMember(owner = "client!pe", name = "f", descriptor = "(I)V")
	public void method5346() {
	}

@OriginalMember(owner = "client!pe", name = "a", descriptor = "(III)Z")
	public static boolean method5343(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return (arg0 & 0x400) != 0;
	}
@OriginalMember(owner = "client!pe", name = "a", descriptor = "(I[[[BIBII)V")
	public static void method5344(@OriginalArg(0) int arg0, @OriginalArg(1) byte[][][] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) byte arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		Class93.anInt2891++;
		OpenGl2DImageTexture.anInt5476 = 0;
		@Pc(14) int local14;
		for (@Pc(7) int local7 = MusicPatchPcmStream.anInt6251; local7 < RemoteFileWorker.anInt4487; local7++) {
			@Pc(12) Class164[][] local12 = Class70.aClass164ArrayArrayArray5[local7];
			for (local14 = Static105.anInt2325; local14 < Class30.anInt775; local14++) {
				for (@Pc(17) int local17 = Class149.anInt4530; local17 < Class4_Sub1_Sub11.anInt3087; local17++) {
					@Pc(24) Class164 local24 = local12[local14][local17];
					if (local24 != null) {
						if (GlVboArrayBuffer.aBooleanArrayArray4[local14 + FileOnDisk.anInt4431 - QuickChatPhrase.anInt7073][local17 + FileOnDisk.anInt4431 - OpenGlArbElementArrayBuffer.anInt1887] && (arg1 == null || local7 < arg2 || arg1[local7][local14][local17] != arg3)) {
							local24.aBoolean464 = true;
							local24.aBoolean466 = true;
							if (local24.aClass7_3 == null) {
								local24.aBoolean465 = false;
							} else {
								local24.aBoolean465 = true;
							}
							OpenGl2DImageTexture.anInt5476++;
						} else {
							local24.aBoolean464 = false;
							local24.aBoolean466 = false;
							local24.aByte49 = 0;
							if (local14 >= QuickChatPhrase.anInt7073 - 16 && local14 <= QuickChatPhrase.anInt7073 + 16 && local17 >= OpenGlArbElementArrayBuffer.anInt1887 - 16 && local17 <= OpenGlArbElementArrayBuffer.anInt1887 + 16 && (local24.aClass16_Sub5_3 != null || local24.aClass16_Sub5_2 != null || local24.aClass16_Sub2_2 != null || local24.aClass16_Sub2_3 != null || local24.aClass16_Sub4_2 != null || local24.aClass7_3 != null)) {
								Static433.aClass70_1.method5076(local24);
							}
						}
					}
				}
			}
		}
		@Pc(145) boolean local145 = FileWorker.aClass65Array3 == Class16_Sub1_Sub6.aClass65Array4;
		for (local14 = MusicPatchPcmStream.anInt6251; local14 < RemoteFileWorker.anInt4487; local14++) {
			@Pc(161) float local161 = local145 ? 251.5F : 201.5F - (float) local14 * 50.0F - 0.5F;
			if (Class95_Sub3.aClass19_37.method4243() && local14 >= arg2 && arg1 != null) {
				@Pc(172) int local172 = GlVboArrayBuffer.aBooleanArrayArray4.length;
				if (Static105.anInt2325 + GlVboArrayBuffer.aBooleanArrayArray4.length > GpuFaceNormal.menuOpenWidth) {
					local172 -= Static105.anInt2325 + GlVboArrayBuffer.aBooleanArrayArray4.length - GpuFaceNormal.menuOpenWidth;
				}
				@Pc(192) int local192 = GlVboArrayBuffer.aBooleanArrayArray4[0].length;
				if (Class149.anInt4530 + GlVboArrayBuffer.aBooleanArrayArray4[0].length > GlModel.anInt3361) {
					local192 -= Class149.anInt4530 + GlVboArrayBuffer.aBooleanArrayArray4[0].length - GlModel.anInt3361;
				}
				@Pc(213) int local213 = TileMessage.anInt7371;
				while (true) {
					if (local213 >= local172) {
						Static433.aClass70_1.method5079(local14, true, Class16_Sub1_Sub6.aClass65Array4[local14], local161);
						break;
					}
					@Pc(220) int local220 = local213 + Static105.anInt2325 - TileMessage.anInt7371;
					for (@Pc(222) int local222 = TextureOpTiledSprite.anInt1088; local222 < local192; local222++) {
						LocTypeList.aBooleanArrayArray6[local213][local222] = false;
						if (GlVboArrayBuffer.aBooleanArrayArray4[local213][local222]) {
							@Pc(241) int local241 = local222 + Class149.anInt4530 - TextureOpTiledSprite.anInt1088;
							for (@Pc(243) int local243 = local14; local243 >= 0; local243--) {
								if (Class70.aClass164ArrayArrayArray5[local243][local220][local241] != null && Class70.aClass164ArrayArrayArray5[local243][local220][local241].aByte53 == local14) {
									LocTypeList.aBooleanArrayArray6[local213][local222] = Class70.aClass164ArrayArrayArray5[local243][local220][local241].aBoolean464;
									break;
								}
							}
						}
					}
					local213++;
				}
			} else {
				Static433.aClass70_1.method5079(local14, false, Class16_Sub1_Sub6.aClass65Array4[local14], local161);
			}
			Static433.aClass70_1.method5074();
		}
		if (!LegacyMouseManager.method4833(true)) {
			LegacyMouseManager.method4833(false);
		}
	}
@OriginalMember(owner = "client!pe", name = "a", descriptor = "(IB[I[[B[BI[I[[B)I")
	public static int method5345(@OriginalArg(0) int arg0, @OriginalArg(2) int[] arg1, @OriginalArg(3) byte[][] arg2, @OriginalArg(4) byte[] arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int[] arg5, @OriginalArg(7) byte[][] arg6) {
		@Pc(9) int local9 = arg1[arg4];
		@Pc(15) int local15 = arg5[arg4] + local9;
		@Pc(19) int local19 = arg1[arg0];
		@Pc(26) int local26 = local19 + arg5[arg0];
		@Pc(28) int local28 = local9;
		if (local9 < local19) {
			local28 = local19;
		}
		@Pc(39) int local39 = local15;
		if (local15 > local26) {
			local39 = local26;
		}
		@Pc(54) int local54 = arg3[arg4] & 0xFF;
		if ((arg3[arg0] & 0xFF) < local54) {
			local54 = arg3[arg0] & 0xFF;
		}
		@Pc(75) byte[] local75 = arg2[arg4];
		@Pc(79) byte[] local79 = arg6[arg0];
		@Pc(84) int local84 = local28 - local9;
		@Pc(88) int local88 = local28 - local19;
		for (@Pc(98) int local98 = local28; local98 < local39; local98++) {
			@Pc(110) int local110 = local79[local88++] + local75[local84++];
			if (local110 < local54) {
				local54 = local110;
			}
		}
		return -local54;
	}
}
