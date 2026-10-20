package com.jagex.game.runetek5.config.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static271;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;

import com.jagex.game.runetek5.config.particle.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!jv")
public final class ParticleProducerType {

	@OriginalMember(owner = "client!jv", name = "a", descriptor = "I")
	private int maximumStartColorRgb;

	@OriginalMember(owner = "client!jv", name = "c", descriptor = "[I")
	public int[] effectiveVertexUids;

	@OriginalMember(owner = "client!jv", name = "d", descriptor = "I")
	public int anInt3773;

	@OriginalMember(owner = "client!jv", name = "e", descriptor = "I")
	public int anInt3774;

	@OriginalMember(owner = "client!jv", name = "f", descriptor = "I")
	public int anInt3775;

	@OriginalMember(owner = "client!jv", name = "g", descriptor = "S")
	public short aShort41;

	@OriginalMember(owner = "client!jv", name = "i", descriptor = "I")
	private int minimumStartColorRgb;

	@OriginalMember(owner = "client!jv", name = "k", descriptor = "[I")
	public int[] anIntArray256;

	@OriginalMember(owner = "client!jv", name = "m", descriptor = "I")
	public int anInt3778;

	@OriginalMember(owner = "client!jv", name = "n", descriptor = "I")
	public int anInt3779;

	@OriginalMember(owner = "client!jv", name = "o", descriptor = "I")
	public int anInt3780;

	@OriginalMember(owner = "client!jv", name = "q", descriptor = "I")
	public int faceColorRgb;

	@OriginalMember(owner = "client!jv", name = "r", descriptor = "I")
	public int anInt3783;

	@OriginalMember(owner = "client!jv", name = "u", descriptor = "I")
	public int anInt3785;

	@OriginalMember(owner = "client!jv", name = "w", descriptor = "I")
	public int minimumStartG;

	@OriginalMember(owner = "client!jv", name = "x", descriptor = "[I")
	public int[] anIntArray257;

	@OriginalMember(owner = "client!jv", name = "y", descriptor = "I")
	private int anInt3787;

	@OriginalMember(owner = "client!jv", name = "z", descriptor = "I")
	public int anInt3788;

	@OriginalMember(owner = "client!jv", name = "D", descriptor = "I")
	public int sizeChangeStep;

	@OriginalMember(owner = "client!jv", name = "G", descriptor = "I")
	private int anInt3793;

	@OriginalMember(owner = "client!jv", name = "H", descriptor = "I")
	public int speedStep;

	@OriginalMember(owner = "client!jv", name = "K", descriptor = "I")
	public int alphaFadeStart;

	@OriginalMember(owner = "client!jv", name = "M", descriptor = "I")
	public int anInt3799;

	@OriginalMember(owner = "client!jv", name = "N", descriptor = "I")
	public int anInt3800;

	@OriginalMember(owner = "client!jv", name = "O", descriptor = "S")
	public short aShort42;

	@OriginalMember(owner = "client!jv", name = "P", descriptor = "I")
	public int minimumStartA;

	@OriginalMember(owner = "client!jv", name = "U", descriptor = "I")
	public int greenFadeStep;

	@OriginalMember(owner = "client!jv", name = "V", descriptor = "I")
	public int anInt3804;

	@OriginalMember(owner = "client!jv", name = "W", descriptor = "[I")
	public int[] copiedParticleFileIds;

	@OriginalMember(owner = "client!jv", name = "X", descriptor = "I")
	public int colorFadeStart;

	@OriginalMember(owner = "client!jv", name = "bb", descriptor = "S")
	public short aShort43;

	@OriginalMember(owner = "client!jv", name = "db", descriptor = "I")
	public int speedFallOffStep;

	@OriginalMember(owner = "client!jv", name = "fb", descriptor = "I")
	private int maximumStartA;

	@OriginalMember(owner = "client!jv", name = "gb", descriptor = "I")
	public int alphaFadeStep;

	@OriginalMember(owner = "client!jv", name = "mb", descriptor = "S")
	public short aShort44;

	@OriginalMember(owner = "client!jv", name = "nb", descriptor = "I")
	public int anInt3816;

	@OriginalMember(owner = "client!jv", name = "ob", descriptor = "I")
	public int startSizeChange;

	@OriginalMember(owner = "client!jv", name = "pb", descriptor = "I")
	public int anInt3818;

	@OriginalMember(owner = "client!jv", name = "qb", descriptor = "I")
	public int anInt3819;

	@OriginalMember(owner = "client!jv", name = "rb", descriptor = "I")
	public int alphaVariance;

	@OriginalMember(owner = "client!jv", name = "sb", descriptor = "I")
	public int maximumStartB;

	@OriginalMember(owner = "client!jv", name = "ub", descriptor = "I")
	private int maximumStartG;

	@OriginalMember(owner = "client!jv", name = "l", descriptor = "I")
	public int minimumSetting = -1;

	@OriginalMember(owner = "client!jv", name = "A", descriptor = "I")
	public int updatesPerCycle = 0;

	@OriginalMember(owner = "client!jv", name = "J", descriptor = "I")
	public int endSpeed = -1;

	@OriginalMember(owner = "client!jv", name = "B", descriptor = "I")
	public int anInt3790 = -2;

	@OriginalMember(owner = "client!jv", name = "L", descriptor = "I")
	public int anInt3798 = 0;

	@OriginalMember(owner = "client!jv", name = "C", descriptor = "Z")
	public boolean adjustsToScene = false;

	@OriginalMember(owner = "client!jv", name = "ab", descriptor = "I")
	public int nonTexturedProducerId = -1;

	@OriginalMember(owner = "client!jv", name = "h", descriptor = "Z")
	public boolean aBoolean353 = true;

	@OriginalMember(owner = "client!jv", name = "F", descriptor = "I")
	public int speedUpdateType = 0;

	@OriginalMember(owner = "client!jv", name = "R", descriptor = "Z")
	public boolean aBoolean357 = true;

	@OriginalMember(owner = "client!jv", name = "kb", descriptor = "Z")
	public boolean aBoolean360 = true;

	@OriginalMember(owner = "client!jv", name = "jb", descriptor = "I")
	private int speedChange = 100;

	@OriginalMember(owner = "client!jv", name = "b", descriptor = "I")
	private int anInt3772 = 100;

	@OriginalMember(owner = "client!jv", name = "T", descriptor = "Z")
	public boolean aBoolean358 = false;

	@OriginalMember(owner = "client!jv", name = "lb", descriptor = "I")
	public int lb = -1;

	@OriginalMember(owner = "client!jv", name = "eb", descriptor = "I")
	public int anInt3810 = -1;

	@OriginalMember(owner = "client!jv", name = "j", descriptor = "Z")
	public boolean aBoolean354 = true;

	@OriginalMember(owner = "client!jv", name = "p", descriptor = "I")
	private int alphaFading = 100;

	@OriginalMember(owner = "client!jv", name = "t", descriptor = "Z")
	public boolean aBoolean355 = true;

	@OriginalMember(owner = "client!jv", name = "Z", descriptor = "I")
	public int endSize = -1;

	@OriginalMember(owner = "client!jv", name = "cb", descriptor = "I")
	public int anInt3808 = -2;

	@OriginalMember(owner = "client!jv", name = "wb", descriptor = "I")
	private int anInt3824 = 100;

	@OriginalMember(owner = "client!jv", name = "a", descriptor = "(ILclient!iv;I)V")
	private void decode(@OriginalArg(0) int arg0, @OriginalArg(1) Packet packet) {
		if (arg0 == 1) {
			this.aShort42 = (short) packet.method2536();
			this.aShort41 = (short) packet.method2536();
			this.aShort43 = (short) packet.method2536();
			this.aShort44 = (short) packet.method2536();
			this.aShort42 = (short) (this.aShort42 << 3);
			this.aShort44 = (short) (this.aShort44 << 3);
			this.aShort43 = (short) (this.aShort43 << 3);
			this.aShort41 = (short) (this.aShort41 << 3);
		} else if (arg0 == 2) {
			packet.method2490();
		} else if (arg0 == 3) {
			this.anInt3804 = packet.method2529();
			this.anInt3800 = packet.method2529();
		} else if (arg0 == 4) {
			this.speedUpdateType = packet.method2490();
			this.speedFallOffStep = packet.method2542();
		} else if (arg0 == 5) {
			this.anInt3788 = this.anInt3816 = packet.method2536() << 12 << 0;
		} else if (arg0 == 6) {
			this.minimumStartColorRgb = packet.method2529();
			this.maximumStartColorRgb = packet.method2529();
		} else if (arg0 == 7) {
			this.anInt3775 = packet.method2536();
			this.anInt3780 = packet.method2536();
		} else if (arg0 == 8) {
			this.anInt3818 = packet.method2536();
			this.anInt3773 = packet.method2536();
		} else {
			@Pc(290) int local290;
			@Pc(296) int local296;
			if (arg0 == 9) {
				local290 = packet.method2490();
				this.anIntArray256 = new int[local290];
				for (local296 = 0; local296 < local290; local296++) {
					this.anIntArray256[local296] = packet.method2536();
				}
			} else if (arg0 == 10) {
				local290 = packet.method2490();
				this.anIntArray257 = new int[local290];
				for (local296 = 0; local296 < local290; local296++) {
					this.anIntArray257[local296] = packet.method2536();
				}
			} else if (arg0 == 12) {
				this.anInt3790 = packet.method2542();
			} else if (arg0 == 13) {
				this.anInt3808 = packet.method2542();
			} else if (arg0 == 14) {
				this.updatesPerCycle = packet.method2536();
			} else if (arg0 == 15) {
				this.minimumSetting = packet.method2536();
			} else if (arg0 == 16) {
				this.aBoolean355 = packet.method2490() == 1;
				this.lb = packet.method2536();
				this.anInt3810 = packet.method2536();
				this.aBoolean353 = packet.method2490() == 1;
			} else if (arg0 == 17) {
				this.nonTexturedProducerId = packet.method2536();
			} else if (arg0 == 18) {
				this.faceColorRgb = packet.method2529();
			} else if (arg0 == 19) {
				this.anInt3798 = packet.method2490();
			} else if (arg0 == 20) {
				this.anInt3824 = packet.method2490();
			} else if (arg0 == 21) {
				this.alphaFading = packet.method2490();
			} else if (arg0 == 22) {
				this.endSpeed = packet.method2529();
			} else if (arg0 == 23) {
				this.anInt3772 = packet.method2490();
			} else if (arg0 == 24) {
				this.aBoolean360 = false;
			} else if (arg0 == 25) {
				local290 = packet.method2490();
				this.effectiveVertexUids = new int[local290];
				for (local296 = 0; local296 < local290; local296++) {
					this.effectiveVertexUids[local296] = packet.method2536();
				}
			} else if (arg0 == 26) {
				this.aBoolean357 = false;
			} else if (arg0 == 27) {
				this.endSize = packet.method2536() << 12 << 0;
			} else if (arg0 == 28) {
				this.speedChange = packet.method2490();
			} else if (arg0 == 29) {
				packet.method2512();
			} else if (arg0 == 30) {
				this.aBoolean358 = true;
			} else if (arg0 == 31) {
				this.anInt3788 = packet.method2536() << 12 << 0;
				this.anInt3816 = packet.method2536() << 12 << 0;
			} else if (arg0 == 32) {
				this.aBoolean354 = false;
				return;
			}
		}
	}

	@OriginalMember(owner = "client!jv", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method3114(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(9) int local9 = arg0.method2490();
			if (local9 == 0) {
				return;
			}
			this.decode(local9, arg0);
		}
	}

	@OriginalMember(owner = "client!jv", name = "a", descriptor = "(I)V")
	public void postDecode() {
		if (this.anInt3790 > -2 || this.anInt3808 > -2) {
			this.adjustsToScene = true;
		}
		this.anInt3783 = this.minimumStartColorRgb >> 16 & 0xFF;
		this.anInt3787 = this.maximumStartColorRgb >> 16 & 0xFF;
		this.anInt3793 = this.maximumStartColorRgb >> 8 & 0xFF;
		this.anInt3799 = this.anInt3787 - this.anInt3783;
		this.minimumStartG = this.minimumStartColorRgb >> 8 & 0xFF;
		this.maximumStartG = this.maximumStartColorRgb & 0xFF;
		this.maximumStartB = this.minimumStartColorRgb & 0xFF;
		this.anInt3774 = this.anInt3793 - this.minimumStartG;
		this.anInt3785 = this.maximumStartG - this.maximumStartB;
		this.minimumStartA = this.minimumStartColorRgb >> 24 & 0xFF;
		this.maximumStartA = this.maximumStartColorRgb >> 24 & 0xFF;
		this.alphaVariance = this.maximumStartA - this.minimumStartA;
		if (this.endSpeed != -1) {
			this.startSizeChange = this.anInt3772 * this.anInt3780 / 100;
			if (this.startSizeChange == 0) {
				this.startSizeChange = 1;
			}
			this.speedStep = (this.endSpeed - (this.anInt3800 - this.anInt3804) / 2 - this.anInt3804) / this.startSizeChange;
		}
		if (this.endSize != -1) {
			this.colorFadeStart = this.speedChange * this.anInt3780 / 100;
			if (this.colorFadeStart == 0) {
				this.colorFadeStart = 1;
			}
			this.sizeChangeStep = (this.endSize - this.anInt3788 - (this.anInt3816 - this.anInt3788) / 2) / this.colorFadeStart;
		}
		if (this.faceColorRgb == 0) {
			return;
		}
		this.anInt3779 = this.anInt3780 * this.alphaFading / 100;
		this.alphaFadeStart = this.anInt3824 * this.anInt3780 / 100;
		if (this.alphaFadeStart == 0) {
			this.alphaFadeStart = 1;
		}
		this.greenFadeStep = ((this.faceColorRgb >> 16 & 0xFF) - this.anInt3799 / 2 - this.anInt3783 << 8) / this.alphaFadeStart;
		this.alphaFadeStep = ((this.faceColorRgb >> 8 & 0xFF) - this.minimumStartG - this.anInt3774 / 2 << 8) / this.alphaFadeStart;
		this.anInt3778 = ((this.faceColorRgb & 0xFF) - this.maximumStartB - this.anInt3785 / 2 << 8) / this.alphaFadeStart;
		if (this.anInt3779 == 0) {
			this.anInt3779 = 1;
		}
		this.greenFadeStep += this.greenFadeStep > 0 ? -4 : 4;
		this.alphaFadeStep += this.alphaFadeStep > 0 ? -4 : 4;
		this.anInt3819 = ((this.faceColorRgb >> 24 & 0xFF) - this.minimumStartA - this.alphaVariance / 2 << 8) / this.anInt3779;
		this.anInt3778 += this.anInt3778 <= 0 ? 4 : -4;
		this.anInt3819 += this.anInt3819 > 0 ? -4 : 4;
	}

@OriginalMember(owner = "client!jv", name = "v", descriptor = "Lclient!qm;")
	public static OggOtherStream aClass4_Sub39_1;
@OriginalMember(owner = "client!jv", name = "S", descriptor = "[I")
	public static final int[] anIntArray258 = new int[25];
@OriginalMember(owner = "client!jv", name = "Y", descriptor = "Z")
	public static volatile boolean aBoolean359 = true;
@OriginalMember(owner = "client!jv", name = "vb", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_7 = null;
@OriginalMember(owner = "client!jv", name = "a", descriptor = "(Ljava/lang/String;I)I")
	public static int method3113(@OriginalArg(0) String arg0) {
		return Class174.method4041(10, arg0);
	}
@OriginalMember(owner = "client!jv", name = "c", descriptor = "(I)V")
	public static void method3117() {
		ByteArrayWrapper.aClass124_2.method3152();
		Class4_Sub6_Sub22.aClass192_2.method4339();
		MaterialType.aClass109_2.method2759();
		Static267.aClass262_2.method5556();
		Class196.aClass240_1.method5232();
		Terrain.aClass206_3.method4706();
		SceneLocEntity.aClass55_1.method1394();
		Js5MasterIndex.aClass194_2.method4418();
		KeyRecordEntry.aClass26_1.method645();
		Js5ResourceRequest.aClass223_1.method5005();
		Class133.aClass249_1.method5322();
		Class4_Sub6_Sub31.aClass182_4.method4128();
		Class4_Sub14.aClass265_2.method5594();
		Class4_Sub1_Sub11.aClass180_1.method4120();
		LocalRequester.aClass208_1.method4723();
		StringNode.aClass217_1.method4933();
		CacheableNodeByteArray.aClass226_1.method5024();
		NPCTypeList.aClass204_1.method4676();
		Js5ResourceProvider.aClass172_2.method3981();
		Class4_Sub6_Sub26.aClass72_1.method1985();
		Class180.method4119();
		Class133.method3351();
		Static1.method2();
		PingWorker.method5478();
		MECType.aClass68_27.method1788(5);
		Class4_Sub21_Sub2.aClass68_22.method1788(5);
		Class99_Sub1.aClass68_23.method1788(5);
		Static105.aClass68_9.method1788(5);
		Static271.aClass68_29.method1788(5);
	}
@OriginalMember(owner = "client!jv", name = "a", descriptor = "()V")
	public static void method3118() {
		for (@Pc(1) int local1 = 0; local1 < Ground.anInt7322; local1++) {
			if (!MouseManager.aBooleanArray12[local1]) {
				@Pc(10) Class191 local10 = StructTypeList.aClass191Array1[local1];
				@Pc(13) PointLight local13 = local10.aClass4_Sub21_2;
				@Pc(16) int local16 = local10.anInt5570;
				@Pc(22) int local22 = local13.method3165() - ReflectingTextureEffect.anInt5747;
				@Pc(30) int local30 = (local22 * 2 >> Static231.anInt4434) + 1;
				@Pc(32) int local32 = 0;
				@Pc(37) int[] local37 = new int[local30 * local30];
				@Pc(45) int local45 = local13.method3160() - local22 >> Static231.anInt4434;
				@Pc(53) int local53 = local13.method3164() - local22 >> Static231.anInt4434;
				@Pc(61) int local61 = local13.method3164() + local22 >> Static231.anInt4434;
				if (local53 < 0) {
					local32 = -local53;
					local53 = 0;
				}
				if (local61 >= GlModel.anInt3361) {
					local61 = GlModel.anInt3361 - 1;
				}
				for (@Pc(78) int local78 = local53; local78 <= local61; local78++) {
					@Pc(84) short local84 = local10.aShortArray189[local32];
					@Pc(88) int local88 = local84 >>> 8;
					@Pc(94) int local94 = local32 * local30 + local88;
					@Pc(100) int local100 = local45 + (local84 >>> 8);
					@Pc(108) int local108 = local100 + (local84 & 0xFF) - 1;
					if (local100 < 0) {
						local94 -= local100;
						local100 = 0;
					}
					if (local108 >= GpuFaceNormal.menuOpenWidth) {
						local108 = GpuFaceNormal.menuOpenWidth - 1;
					}
					for (@Pc(125) int local125 = local100; local125 <= local108; local125++) {
						@Pc(128) byte local128 = 1;
						@Pc(141) Class16_Sub1 local141 = Class26.method648(local16, local125, local78, Class16_Sub1.class);
						if (local141 != null && local141.aByte83 != 0) {
							@Pc(159) boolean local159;
							@Pc(168) boolean local168;
							@Pc(182) short local182;
							@Pc(188) int local188;
							@Pc(194) int local194;
							if (local141.aByte83 == 1) {
								local159 = local125 - 1 >= local100;
								local168 = local125 + 1 <= local108;
								if (!local159 && local78 + 1 <= local61) {
									local182 = local10.aShortArray189[local32 + 1];
									local188 = local45 + (local182 >>> 8);
									local194 = local188 + (local182 & 0xFF);
									local159 = local125 > local188 && local125 < local194;
								}
								if (!local168 && local78 - 1 >= local53) {
									local182 = local10.aShortArray189[local32 - 1];
									local188 = local45 + (local182 >>> 8);
									local194 = local188 + (local182 & 0xFF);
									local168 = local125 > local188 && local125 < local194;
								}
								if (local159 && !local168) {
									local128 = 4;
								} else if (local168 && !local159) {
									local128 = 2;
								}
							} else {
								local159 = local125 - 1 >= local100;
								local168 = local125 + 1 <= local108;
								if (!local159 && local78 - 1 >= local53) {
									local182 = local10.aShortArray189[local32 - 1];
									local188 = local45 + (local182 >>> 8);
									local194 = local188 + (local182 & 0xFF);
									local159 = local125 > local188 && local125 < local194;
								}
								if (!local168 && local78 + 1 <= local61) {
									local182 = local10.aShortArray189[local32 + 1];
									local188 = local45 + (local182 >>> 8);
									local194 = local188 + (local182 & 0xFF);
									local168 = local125 > local188 && local125 < local194;
								}
								if (local159 && !local168) {
									local128 = 3;
								} else if (local168 && !local159) {
									local128 = 5;
								}
							}
						}
						local37[local94++] = local128;
					}
					local32++;
				}
				MouseManager.aBooleanArray12[local1] = true;
				Class16_Sub1_Sub6.aClass65Array4[local16].method4024(local13, local37);
			}
		}
	}
}
