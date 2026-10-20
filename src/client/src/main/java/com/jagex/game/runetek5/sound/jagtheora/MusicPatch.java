package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import jaggl.OpenGL;
import java.util.Random;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static380;
import com.jagex.game.runetek5.deob.Static449;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
@OriginalClass("client!qh")
public final class MusicPatch extends Country {

	@OriginalMember(owner = "client!qh", name = "m", descriptor = "[B")
	public byte[] volumeOffset;

	@OriginalMember(owner = "client!qh", name = "o", descriptor = "[I")
	private int[] sampleOffsets;

	@OriginalMember(owner = "client!qh", name = "q", descriptor = "[B")
	public byte[] panOffset;

	@OriginalMember(owner = "client!qh", name = "r", descriptor = "[B")
	public byte[] loopMode;

	@OriginalMember(owner = "client!qh", name = "t", descriptor = "[Lclient!js;")
	public VariableRateSoundPacket[] audioFiles;

	@OriginalMember(owner = "client!qh", name = "w", descriptor = "I")
	public int baseVelocity;

	@OriginalMember(owner = "client!qh", name = "x", descriptor = "[S")
	public short[] pitchOffset;

	@OriginalMember(owner = "client!qh", name = "y", descriptor = "[Lclient!vg;")
	public MusicPatchInfo[] aClass252Array1;

	@OriginalMember(owner = "client!qh", name = "<init>", descriptor = "()V")
	private MusicPatch() {
	}

	@OriginalMember(owner = "client!qh", name = "<init>", descriptor = "([B)V")
	public MusicPatch(@OriginalArg(0) byte[] arg0) {
		this.pitchOffset = new short[128];
		this.panOffset = new byte[128];
		this.audioFiles = new VariableRateSoundPacket[128];
		this.volumeOffset = new byte[128];
		this.aClass252Array1 = new MusicPatchInfo[128];
		this.sampleOffsets = new int[128];
		this.loopMode = new byte[128];
		@Pc(36) Packet local36 = new Packet(arg0);
		@Pc(38) int local38;
		for (local38 = 0; local36.buffer[local36.currentPosition + local38] != 0; local38++) {
		}
		@Pc(56) byte[] local56 = new byte[local38];
		for (@Pc(58) int local58 = 0; local58 < local38; local58++) {
			local56[local58] = local36.method2542();
		}
		local38++;
		local36.currentPosition++;
		@Pc(80) int local80 = local36.currentPosition;
		local36.currentPosition += local38;
		@Pc(88) int local88;
		for (local88 = 0; local36.buffer[local88 + local36.currentPosition] != 0; local88++) {
		}
		@Pc(105) byte[] local105 = new byte[local88];
		for (@Pc(107) int local107 = 0; local107 < local88; local107++) {
			local105[local107] = local36.method2542();
		}
		local36.currentPosition++;
		local88++;
		@Pc(133) int local133 = local36.currentPosition;
		local36.currentPosition += local88;
		@Pc(141) int local141;
		for (local141 = 0; local36.buffer[local36.currentPosition + local141] != 0; local141++) {
		}
		@Pc(159) byte[] local159 = new byte[local141];
		for (@Pc(161) int local161 = 0; local161 < local141; local161++) {
			local159[local161] = local36.method2542();
		}
		local36.currentPosition++;
		local141++;
		@Pc(187) byte[] local187 = new byte[local141];
		@Pc(200) int local200;
		@Pc(202) int local202;
		@Pc(208) int local208;
		if (local141 > 1) {
			local187[1] = 1;
			@Pc(198) int local198 = 1;
			local200 = 2;
			for (local202 = 2; local202 < local141; local202++) {
				local208 = local36.method2490();
				if (local208 == 0) {
					local198 = local200++;
				} else {
					if (local208 <= local198) {
						local208--;
					}
					local198 = local208;
				}
				local187[local202] = (byte) local198;
			}
		} else {
			local200 = local141;
		}
		@Pc(241) MusicPatchInfo[] local241 = new MusicPatchInfo[local200];
		for (local202 = 0; local202 < local241.length; local202++) {
			@Pc(253) MusicPatchInfo local253 = local241[local202] = new MusicPatchInfo();
			@Pc(257) int local257 = local36.method2490();
			if (local257 > 0) {
				local253.volumeEnvelopeData = new byte[local257 * 2];
			}
			local257 = local36.method2490();
			if (local257 > 0) {
				local253.releaseEnvelopeData = new byte[local257 * 2 + 2];
				local253.releaseEnvelopeData[1] = 64;
			}
		}
		local208 = local36.method2490();
		@Pc(308) byte[] local308 = local208 <= 0 ? null : new byte[local208 * 2];
		local208 = local36.method2490();
		@Pc(321) byte[] local321 = local208 <= 0 ? null : new byte[local208 * 2];
		@Pc(323) int local323;
		for (local323 = 0; local36.buffer[local323 + local36.currentPosition] != 0; local323++) {
		}
		@Pc(340) byte[] local340 = new byte[local323];
		for (@Pc(342) int local342 = 0; local342 < local323; local342++) {
			local340[local342] = local36.method2542();
		}
		local323++;
		local36.currentPosition++;
		@Pc(367) int local367 = 0;
		for (@Pc(369) int local369 = 0; local369 < 128; local369++) {
			local367 += local36.method2490();
			this.pitchOffset[local369] = (short) local367;
		}
		local367 = 0;
		for (@Pc(391) int local391 = 0; local391 < 128; local391++) {
			local367 += local36.method2490();
			this.pitchOffset[local391] = (short) (this.pitchOffset[local391] + (local367 << 8));
		}
		@Pc(418) int local418 = 0;
		@Pc(420) int local420 = 0;
		@Pc(422) int local422 = 0;
		for (@Pc(424) int local424 = 0; local424 < 128; local424++) {
			if (local418 == 0) {
				if (local420 >= local340.length) {
					local418 = -1;
				} else {
					local418 = local340[local420++];
				}
				local422 = local36.method2528();
			}
			this.pitchOffset[local424] = (short) (this.pitchOffset[local424] + ((local422 - 1 & 0x2) << 14));
			this.sampleOffsets[local424] = local422;
			local418--;
		}
		local420 = 0;
		local418 = 0;
		@Pc(482) int local482 = 0;
		for (@Pc(484) int local484 = 0; local484 < 128; local484++) {
			if (this.sampleOffsets[local484] != 0) {
				if (local418 == 0) {
					local482 = local36.buffer[local80++] - 1;
					if (local420 >= local56.length) {
						local418 = -1;
					} else {
						local418 = local56[local420++];
					}
				}
				this.loopMode[local484] = (byte) local482;
				local418--;
			}
		}
		local420 = 0;
		local418 = 0;
		@Pc(536) int local536 = 0;
		for (@Pc(538) int local538 = 0; local538 < 128; local538++) {
			if (this.sampleOffsets[local538] != 0) {
				if (local418 == 0) {
					local536 = local36.buffer[local133++] + 16 << 2;
					if (local420 >= local105.length) {
						local418 = -1;
					} else {
						local418 = local105[local420++];
					}
				}
				local418--;
				this.panOffset[local538] = (byte) local536;
			}
		}
		local418 = 0;
		local420 = 0;
		@Pc(597) MusicPatchInfo local597 = null;
		for (@Pc(599) int local599 = 0; local599 < 128; local599++) {
			if (this.sampleOffsets[local599] != 0) {
				if (local418 == 0) {
					local597 = local241[local187[local420]];
					if (local159.length <= local420) {
						local418 = -1;
					} else {
						local418 = local159[local420++];
					}
				}
				local418--;
				this.aClass252Array1[local599] = local597;
			}
		}
		local418 = 0;
		local420 = 0;
		@Pc(645) int local645 = 0;
		for (@Pc(647) int local647 = 0; local647 < 128; local647++) {
			if (local418 == 0) {
				if (local420 >= local340.length) {
					local418 = -1;
				} else {
					local418 = local340[local420++];
				}
				if (this.sampleOffsets[local647] > 0) {
					local645 = local36.method2490() + 1;
				}
			}
			this.volumeOffset[local647] = (byte) local645;
			local418--;
		}
		this.baseVelocity = local36.method2490() + 1;
		@Pc(713) int local713;
		for (@Pc(702) int local702 = 0; local702 < local200; local702++) {
			@Pc(708) MusicPatchInfo local708 = local241[local702];
			if (local708.volumeEnvelopeData != null) {
				for (local713 = 1; local713 < local708.volumeEnvelopeData.length; local713 += 2) {
					local708.volumeEnvelopeData[local713] = local36.method2542();
				}
			}
			if (local708.releaseEnvelopeData != null) {
				for (local713 = 3; local713 < local708.releaseEnvelopeData.length - 2; local713 += 2) {
					local708.releaseEnvelopeData[local713] = local36.method2542();
				}
			}
		}
		@Pc(758) int local758;
		if (local308 != null) {
			for (local758 = 1; local758 < local308.length; local758 += 2) {
				local308[local758] = local36.method2542();
			}
		}
		if (local321 != null) {
			for (local758 = 1; local758 < local321.length; local758 += 2) {
				local321[local758] = local36.method2542();
			}
		}
		@Pc(807) int local807;
		for (local758 = 0; local758 < local200; local758++) {
			@Pc(800) MusicPatchInfo local800 = local241[local758];
			if (local800.releaseEnvelopeData != null) {
				local367 = 0;
				for (local807 = 2; local807 < local800.releaseEnvelopeData.length; local807 += 2) {
					local367 = local367 + local36.method2490() + 1;
					local800.releaseEnvelopeData[local807] = (byte) local367;
				}
			}
		}
		@Pc(852) int local852;
		for (local713 = 0; local713 < local200; local713++) {
			@Pc(845) MusicPatchInfo local845 = local241[local713];
			if (local845.volumeEnvelopeData != null) {
				local367 = 0;
				for (local852 = 2; local852 < local845.volumeEnvelopeData.length; local852 += 2) {
					local367 += local36.method2490() + 1;
					local845.volumeEnvelopeData[local852] = (byte) local367;
				}
			}
		}
		@Pc(929) int local929;
		@Pc(956) int local956;
		@Pc(962) byte local962;
		@Pc(980) int local980;
		@Pc(982) int local982;
		@Pc(992) int local992;
		@Pc(923) byte local923;
		@Pc(1035) int local1035;
		if (local308 != null) {
			local367 = local36.method2490();
			local308[0] = (byte) local367;
			for (local807 = 2; local807 < local308.length; local807 += 2) {
				local367 = local367 + local36.method2490() + 1;
				local308[local807] = (byte) local367;
			}
			local923 = local308[0];
			@Pc(927) byte local927 = local308[1];
			for (local929 = 0; local929 < local923; local929++) {
				this.volumeOffset[local929] = (byte) (local927 * this.volumeOffset[local929] + 32 >> 6);
			}
			local956 = 2;
			while (local308.length > local956) {
				local962 = local308[local956];
				@Pc(968) byte local968 = local308[local956 + 1];
				local980 = (local962 - local923) * local927 + (local962 - local923) / 2;
				for (local982 = local923; local982 < local962; local982++) {
					local992 = Class4_Sub6_Sub15.method2976(local980, local962 - local923);
					this.volumeOffset[local982] = (byte) (this.volumeOffset[local982] * local992 + 32 >> 6);
					local980 += local968 - local927;
				}
				local956 += 2;
				local927 = local968;
				local923 = local962;
			}
			for (local1035 = local923; local1035 < 128; local1035++) {
				this.volumeOffset[local1035] = (byte) (this.volumeOffset[local1035] * local927 + 32 >> 6);
			}
		}
		@Pc(1105) int local1105;
		if (local321 != null) {
			local367 = local36.method2490();
			local321[0] = (byte) local367;
			for (local807 = 2; local807 < local321.length; local807 += 2) {
				local367 = local367 + local36.method2490() + 1;
				local321[local807] = (byte) local367;
			}
			local923 = local321[0];
			local1105 = local321[1] << 1;
			for (local929 = 0; local929 < local923; local929++) {
				local956 = local1105 + (this.panOffset[local929] & 0xFF);
				if (local956 < 0) {
					local956 = 0;
				}
				if (local956 > 128) {
					local956 = 128;
				}
				this.panOffset[local929] = (byte) local956;
			}
			local956 = 2;
			@Pc(1153) int local1153;
			while (local321.length > local956) {
				local962 = local321[local956];
				local1153 = local321[local956 + 1] << 1;
				local980 = (local962 - local923) / 2 + (local962 - local923) * local1105;
				for (local982 = local923; local982 < local962; local982++) {
					local992 = Class4_Sub6_Sub15.method2976(local980, local962 - local923);
					@Pc(1187) int local1187 = local992 + (this.panOffset[local982] & 0xFF);
					if (local1187 < 0) {
						local1187 = 0;
					}
					if (local1187 > 128) {
						local1187 = 128;
					}
					local980 += local1153 - local1105;
					this.panOffset[local982] = (byte) local1187;
				}
				local923 = local962;
				local956 += 2;
				local1105 = local1153;
			}
			for (local1035 = local923; local1035 < 128; local1035++) {
				local1153 = (this.panOffset[local1035] & 0xFF) + local1105;
				if (local1153 < 0) {
					local1153 = 0;
				}
				if (local1153 > 128) {
					local1153 = 128;
				}
				this.panOffset[local1035] = (byte) local1153;
			}
		}
		for (local807 = 0; local807 < local200; local807++) {
			local241[local807].volumeEnvelopeDecay = local36.method2490();
		}
		for (local852 = 0; local852 < local200; local852++) {
			@Pc(1294) MusicPatchInfo local1294 = local241[local852];
			if (local1294.volumeEnvelopeData != null) {
				local1294.anInt6943 = local36.method2490();
			}
			if (local1294.releaseEnvelopeData != null) {
				local1294.releaseEnvelopeKeyScaling = local36.method2490();
			}
			if (local1294.volumeEnvelopeDecay > 0) {
				local1294.anInt6938 = local36.method2490();
			}
		}
		for (local1105 = 0; local1105 < local200; local1105++) {
			local241[local1105].volumeEnvelopeRelease = local36.method2490();
		}
		for (local929 = 0; local929 < local200; local929++) {
			@Pc(1345) MusicPatchInfo local1345 = local241[local929];
			if (local1345.volumeEnvelopeRelease > 0) {
				local1345.vibratoLfoPitch = local36.method2490();
			}
		}
		for (local956 = 0; local956 < local200; local956++) {
			@Pc(1365) MusicPatchInfo local1365 = local241[local956];
			if (local1365.vibratoLfoPitch > 0) {
				local1365.anInt6944 = local36.method2490();
			}
		}
	}

	@OriginalMember(owner = "client!qh", name = "c", descriptor = "(I)V")
	public void method4393() {
		this.sampleOffsets = null;
	}

	@OriginalMember(owner = "client!qh", name = "a", descriptor = "(Lclient!oj;I[B[I)Z")
	public boolean method4398(@OriginalArg(0) SoundBankCache arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) int[] arg2) {
		@Pc(16) boolean local16 = true;
		@Pc(18) int local18 = 0;
		@Pc(20) VariableRateSoundPacket local20 = null;
		for (@Pc(22) int local22 = 0; local22 < 128; local22++) {
			if (arg1 == null || arg1[local22] != 0) {
				@Pc(37) int local37 = this.sampleOffsets[local22];
				if (local37 != 0) {
					if (local37 != local18) {
						local18 = local37--;
						if ((local37 & 0x1) == 0) {
							local20 = arg0.method3976(arg2, local37 >> 2);
						} else {
							local20 = arg0.method3973(arg2, local37 >> 2);
						}
						if (local20 == null) {
							local16 = false;
						}
					}
					if (local20 != null) {
						this.audioFiles[local22] = local20;
						this.sampleOffsets[local22] = 0;
					}
				}
			}
		}
		return local16;
	}

@OriginalMember(owner = "client!qh", name = "v", descriptor = "I")
	public static int anInt5636;
@OriginalMember(owner = "client!qh", name = "A", descriptor = "Z")
	public static boolean aBoolean506 = false;
@OriginalMember(owner = "client!qh", name = "B", descriptor = "Lclient!gn;")
	public static final EntityNodeList aClass84_7 = new EntityNodeList();
@OriginalMember(owner = "client!qh", name = "D", descriptor = "Lclient!ib;")
	public static CubeMapSpecularEffect aClass102_29 = new CubeMapSpecularEffect(8);
@OriginalMember(owner = "client!qh", name = "E", descriptor = "Ljava/util/Random;")
	public static final Random aRandom1 = new Random();
@OriginalMember(owner = "client!qh", name = "F", descriptor = "Z")
	public static boolean aBoolean507 = false;
@OriginalMember(owner = "client!qh", name = "G", descriptor = "I")
	public static int anInt5640 = 1;
@OriginalMember(owner = "client!qh", name = "a", descriptor = "(BLclient!rs;)I")
	public static int method4392(@OriginalArg(1) NPCEntity arg0) {
		@Pc(8) NPCType local8 = arg0.npcType;
		if (local8.anIntArray544 != null) {
			local8 = local8.method5574(Class4_Sub6_Sub24.aClass114_1);
			if (local8 == null) {
				return -1;
			}
		}
		@Pc(28) int local28 = local8.anInt7202;
		@Pc(34) BasType local34 = arg0.method4757();
		if (arg0.aBoolean562) {
			local28 = local8.anInt7210;
		} else if (arg0.anInt6076 == local34.anInt4669 || local34.anInt4656 == arg0.anInt6076 || local34.anInt4665 == arg0.anInt6076 || arg0.anInt6076 == local34.anInt4686) {
			local28 = local8.anInt7212;
		} else if (arg0.anInt6076 == local34.anInt4667 || local34.anInt4681 == arg0.anInt6076 || arg0.anInt6076 == local34.anInt4688 || local34.anInt4675 == arg0.anInt6076) {
			local28 = local8.anInt7207;
		}
		return local28;
	}
@OriginalMember(owner = "client!qh", name = "a", descriptor = "(III)I")
	public static int method4394(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return arg1 == 4 || arg1 == 5 ? HookRequest.anIntArray343[arg0 & 0x3] : 256;
	}
@OriginalMember(owner = "client!qh", name = "a", descriptor = "(BIIIIII)V")
	public static void method4395(@OriginalArg(2) int arg0, @OriginalArg(3) int arg1, @OriginalArg(4) int arg2, @OriginalArg(6) int arg3) {
		@Pc(7) Class38[] local7 = Class183.aClass38Array1;
		for (@Pc(9) int local9 = 0; local9 < local7.length; local9++) {
			@Pc(15) Class38 local15 = local7[local9];
			if (local15 != null && local15.anInt891 == 2) {
				QuickChatCategoryType.method3871(local15.anInt892, local15.anInt894, local15.anInt887, local15.anInt895 * 2, arg2 >> 1, arg1 >> 1);
				if (Static449.anIntArray557[0] > -1 && Shadow.anInt5323 % 20 < 10) {
					TextureOpTiledSprite.aClass57Array3[local15.anInt886].render(arg3 + Static449.anIntArray557[0] - 12, arg0 + (Static449.anIntArray557[1] - 28));
				}
			}
		}
	}
@OriginalMember(owner = "client!qh", name = "a", descriptor = "(ZLclient!rs;Z)V")
	public static void method4396(@OriginalArg(0) boolean arg0, @OriginalArg(1) NPCEntity arg1) {
		if (Skybox.anInt6710 >= 400) {
			return;
		}
		@Pc(16) NPCType local16 = arg1.npcType;
		if (local16.anIntArray544 != null) {
			local16 = local16.method5574(Class4_Sub6_Sub24.aClass114_1);
			if (local16 == null) {
				return;
			}
		}
		if (!local16.aBoolean682) {
			return;
		}
		@Pc(34) String local34 = local16.aString70;
		if (local16.lb != 0) {
			@Pc(50) String local50 = DynamicWall.aClass121_3 == FixedFunctionSpecularPass.WRITE_PING ? CacheableNode.aClass83_121.method2267(NativeBillboardFace.anInt3423) : BillowNoiseGenerator.aClass83_118.method2267(NativeBillboardFace.anInt3423);
			local34 = local34 + GlRectangleTexture.getCombatLevelColour(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345, local16.lb) + " (" + local50 + local16.lb + ")";
		}
		if (!AbstractArbBufferGL.aBoolean599) {
			if (!arg0) {
				@Pc(84) String[] local84 = local16.actions;
				if (MaterialType.aBoolean646) {
					local84 = MouseManager.method2247(local84);
				}
				@Pc(94) int local94;
				if (local84 != null) {
						for (local94 = 4; local94 >= 0; local94--) {
							// Pets replace the first option with Pick-up, which is added below.
							if (arg1.isPet && local94 == 0) {
								continue;
							}
							if (local84[local94] != null && (DynamicWall.aClass121_3 != TextureOpTexture.aClass121_1 || !local84[local94].equalsIgnoreCase(GlGroundLayer.aClass83_33.method2267(NativeBillboardFace.anInt3423)))) {
							@Pc(114) byte local114 = 0;
							@Pc(116) int local116 = Class6.anInt5453;
							if (local94 == 0) {
								local114 = 11;
							}
							if (local94 == 1) {
								local114 = 15;
							}
							if (local94 == 2) {
								local114 = 21;
							}
							if (local94 == 3) {
								local114 = 3;
							}
							if (local94 == 4) {
								local114 = 50;
							}
							if (local16.anInt7206 == local94) {
								local116 = local16.anInt7237;
							}
							if (local94 == local16.anInt7219) {
								local116 = local16.anInt7229;
							}
							Static380.method5017(local114, false, (long) arg1.anInt6037, 0, "<col=ffff00>" + local34, true, local84[local94], 0, -1, local116);
						}
					}
				}
				if (DynamicWall.aClass121_3 == TextureOpTexture.aClass121_1 && local84 != null) {
					for (local94 = 4; local94 >= 0; local94--) {
						if (local84[local94] != null && local84[local94].equalsIgnoreCase(GlGroundLayer.aClass83_33.method2267(NativeBillboardFace.anInt3423))) {
							@Pc(214) short local214 = 0;
							if (local16.lb > Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345) {
								local214 = 2000;
							}
							@Pc(227) short local227 = 0;
							if (local94 == 0) {
								local227 = 11;
							}
							if (local94 == 1) {
								local227 = 15;
							}
							if (local94 == 2) {
								local227 = 21;
							}
							if (local94 == 3) {
								local227 = 3;
							}
							if (local94 == 4) {
								local227 = 50;
							}
							if (local227 != 0) {
								local227 += local214;
							}
							Static380.method5017(local227, false, (long) arg1.anInt6037, 0, "<col=ffff00>" + local34, true, local84[local94], 0, -1, local16.anInt7214);
						}
					}
				}
				if (arg1.isPet) {
					Static380.method5017(11, false, (long) arg1.anInt6037, 0, "<col=ffff00>" + local34, true, "Dismiss-pet", 0, -1, Class6.anInt5453);
				}
			}
			Static380.method5017(1003, arg0, (long) arg1.anInt6037, 0, "<col=ffff00>" + local34, true, QuickchatMessageType.aClass83_12.method2267(NativeBillboardFace.anInt3423), 0, -1, Class70_Sub1.anInt2066);
		} else if (!arg0) {
			@Pc(339) Class133 local339 = OpenGl2DTexture.anInt5460 == -1 ? null : Class4_Sub1_Sub11.aClass180_1.method4121(OpenGl2DTexture.anInt5460);
			if ((Country.anInt7378 & 0x2) != 0) {
				if (local339 == null || local16.method5582(local339.anInt4123, OpenGl2DTexture.anInt5460) != local339.anInt4123) {
					Static380.method5017(22, false, (long) arg1.anInt6037, 0, Class4_Sub10.aString28 + " -> <col=ffff00>" + local34, true, Class230.aString62, 0, -1, Class164.anInt5120);
				}
				return;
			}
		}
	}
@OriginalMember(owner = "client!qh", name = "a", descriptor = "(BLclient!fd;ILjava/lang/String;)Lclient!wl;")
	public static Class267 method4397(@OriginalArg(1) GlToolkit arg0, @OriginalArg(2) int arg1, @OriginalArg(3) String arg2) {
		@Pc(6) long local6 = OpenGL.glCreateShaderObjectARB(arg1);
		OpenGL.glShaderSourceARB(local6, arg2);
		OpenGL.glCompileShaderARB(local6);
		OpenGL.glGetObjectParameterivARB(local6, 35713, Class210.anIntArray434, 0);
		if (Class210.anIntArray434[0] == 0) {
			if (Class210.anIntArray434[0] == 0) {
				System.out.println("Shader compile failed:");
			}
			OpenGL.glGetObjectParameterivARB(local6, 35716, Class210.anIntArray434, 1);
			if (Class210.anIntArray434[1] > 1) {
				@Pc(53) byte[] local53 = new byte[Class210.anIntArray434[1]];
				OpenGL.glGetInfoLogARB(local6, Class210.anIntArray434[1], Class210.anIntArray434, 0, local53, 0);
				System.out.println(new String(local53));
			}
			if (Class210.anIntArray434[0] == 0) {
				OpenGL.glDeleteObjectARB(local6);
				return null;
			}
		}
		return new Class267(arg0, local6, arg1);
	}
}
