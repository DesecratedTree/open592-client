package com.jagex.game.runetek5.deob;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.sound.player.SignLinkAudioChannel;

import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
public final class Static380 {

	@OriginalMember(owner = "client!te", name = "m", descriptor = "Lclient!bg;")
	public static Class22 aClass22_294;

	@OriginalMember(owner = "client!te", name = "l", descriptor = "F")
	public static float aFloat195 = 0.0F;

	@OriginalMember(owner = "client!te", name = "a", descriptor = "(IILclient!et;ILjava/awt/Component;)Lclient!ln;")
	public static PcmPlayer method5016(@OriginalArg(0) int arg0, @OriginalArg(2) SignLink arg1, @OriginalArg(3) int arg2, @OriginalArg(4) Component arg3) {
		if (MECType.anInt4928 == 0) {
			throw new IllegalStateException();
		}
		try {
			@Pc(49) PcmPlayer local49 = (PcmPlayer) Class.forName("com.jagex.game.runetek5.sound.player.MonoSterioPlayer").getDeclaredConstructor().newInstance();
			local49.sampleRate2 = arg2;
			local49.bitpackedSoundData = new int[(GpuFaceNormal.aBoolean649 ? 2 : 1) * 256];
			local49.init(arg3);
			local49.bufferCapacity = (arg2 & 0xFFFFFC00) + 1024;
			if (local49.bufferCapacity > 16384) {
				local49.bufferCapacity = 16384;
			}
			local49.play(local49.bufferCapacity);
			if (FluTypeList.anInt5579 > 0 && JavaThreadResource.aClass227_1 == null) {
				JavaThreadResource.aClass227_1 = new PcmPlayerThread();
				JavaThreadResource.aClass227_1.aClass66_7 = arg1;
				arg1.method1741(FluTypeList.anInt5579, JavaThreadResource.aClass227_1);
			}
			if (JavaThreadResource.aClass227_1 != null) {
				if (JavaThreadResource.aClass227_1.players[arg0] != null) {
					throw new IllegalArgumentException();
				}
				JavaThreadResource.aClass227_1.players[arg0] = local49;
			}
			return local49;
		} catch (@Pc(122) Throwable local122) {
			try {
				@Pc(128) SignLinkAudioChannel local128 = new SignLinkAudioChannel(arg1, arg0);
				local128.bitpackedSoundData = new int[(GpuFaceNormal.aBoolean649 ? 2 : 1) * 256];
				local128.sampleRate2 = arg2;
				local128.init(arg3);
				local128.bufferCapacity = 16384;
				local128.play(local128.bufferCapacity);
				if (FluTypeList.anInt5579 > 0 && JavaThreadResource.aClass227_1 == null) {
					JavaThreadResource.aClass227_1 = new PcmPlayerThread();
					JavaThreadResource.aClass227_1.aClass66_7 = arg1;
					arg1.method1741(FluTypeList.anInt5579, JavaThreadResource.aClass227_1);
				}
				if (JavaThreadResource.aClass227_1 != null) {
					if (JavaThreadResource.aClass227_1.players[arg0] != null) {
						throw new IllegalArgumentException();
					}
					JavaThreadResource.aClass227_1.players[arg0] = local128;
				}
				return local128;
			} catch (@Pc(192) Throwable local192) {
				return new PcmPlayer();
			}
		}
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "(IBZJILjava/lang/String;ZLjava/lang/String;III)V")
	public static void method5017(@OriginalArg(0) int arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) long arg2, @OriginalArg(4) int arg3, @OriginalArg(5) String arg4, @OriginalArg(6) boolean arg5, @OriginalArg(7) String arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9) {
		if (!Class4_Sub6_Sub22.aBoolean411 && Skybox.anInt6710 < 500) {
			@Pc(16) int local16 = arg9 == -1 ? HashTable.anInt3147 : arg9;
			@Pc(30) OggOtherStream local30 = new OggOtherStream(arg6, arg4, local16, arg0, arg8, arg2, arg3, arg7, arg5, arg1);
			Class126.aClass183_28.method4137(local30);
			Skybox.anInt6710++;
		}
	}

	@OriginalMember(owner = "client!te", name = "a", descriptor = "(Lclient!fs;ILjava/lang/String;Z)Lclient!qk;")
	public static StaticElements method5018(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(2) String arg1, @OriginalArg(3) boolean arg2) {
		@Pc(18) int local18 = arg0.method2099(arg1);
		if (local18 == -1) {
			return new StaticElements(0);
		}
		@Pc(31) int[] local31 = arg0.method2124(local18);
		@Pc(37) StaticElements local37 = new StaticElements(local31.length);
		@Pc(39) int local39 = 0;
		@Pc(41) int local41 = 0;
		while (true) {
			while (local37.elementCount > local39) {
				@Pc(55) Packet local55 = new Packet(arg0.method2104(local31[local41++], local18));
				@Pc(59) int local59 = local55.method2529();
				@Pc(63) int local63 = local55.method2536();
				@Pc(67) int local67 = local55.method2490();
				if (!arg2 && local67 == 1) {
					local37.elementCount--;
				} else {
					local37.anIntArray397[local39] = local59;
					local37.anIntArray396[local39] = local63;
					local39++;
				}
			}
			return local37;
		}
	}
}
