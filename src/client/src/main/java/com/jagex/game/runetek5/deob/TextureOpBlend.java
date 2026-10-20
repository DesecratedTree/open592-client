package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.net.URL;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.graphics.model.GlModel;
@OriginalClass("client!ju")
public final class TextureOpBlend extends TextureOperation {

	@OriginalMember(owner = "client!ju", name = "J", descriptor = "I")
	private int anInt3770 = 6;

	@OriginalMember(owner = "client!ju", name = "<init>", descriptor = "()V")
	public TextureOpBlend() {
		super(2, false);
	}

	@OriginalMember(owner = "client!ju", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(16) int[][] local16 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(26) int[][] local26 = this.getChildColorOutput(0, arg0);
			@Pc(32) int[][] local32 = this.getChildColorOutput(1, arg0);
			@Pc(36) int[] local36 = local16[0];
			@Pc(40) int[] local40 = local16[1];
			@Pc(44) int[] local44 = local16[2];
			@Pc(48) int[] local48 = local26[0];
			@Pc(52) int[] local52 = local26[1];
			@Pc(56) int[] local56 = local26[2];
			@Pc(60) int[] local60 = local32[0];
			@Pc(64) int[] local64 = local32[1];
			@Pc(68) int[] local68 = local32[2];
			@Pc(71) int local71 = this.anInt3770;
			if (local71 == 1) {
				for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
					local36[local71] = local48[local71] + local60[local71];
					local40[local71] = local64[local71] + local52[local71];
					local44[local71] = local56[local71] + local68[local71];
				}
			} else if (local71 == 2) {
				for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
					local36[local71] = local48[local71] - local60[local71];
					local40[local71] = local52[local71] - local64[local71];
					local44[local71] = local56[local71] - local68[local71];
				}
			} else if (local71 == 3) {
				for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
					local36[local71] = local48[local71] * local60[local71] >> 12;
					local40[local71] = local64[local71] * local52[local71] >> 12;
					local44[local71] = local56[local71] * local68[local71] >> 12;
				}
			} else {
				@Pc(261) int local261;
				@Pc(265) int local265;
				@Pc(269) int local269;
				if (local71 == 4) {
					for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
						local261 = local60[local71];
						local265 = local64[local71];
						local269 = local68[local71];
						local36[local71] = local261 == 0 ? 4096 : (local48[local71] << 12) / local261;
						local40[local71] = local265 == 0 ? 4096 : (local52[local71] << 12) / local265;
						local44[local71] = local269 == 0 ? 4096 : (local56[local71] << 12) / local269;
					}
				} else if (local71 == 5) {
					for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
						local36[local71] = 4096 - ((4096 - local48[local71]) * (-local60[local71] + 4096) >> 12);
						local40[local71] = 4096 - ((4096 - local52[local71]) * (4096 - local64[local71]) >> 12);
						local44[local71] = 4096 - ((4096 - local68[local71]) * (-local56[local71] + 4096) >> 12);
					}
				} else if (local71 == 6) {
					for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
						local265 = local64[local71];
						local261 = local60[local71];
						local269 = local68[local71];
						local36[local71] = local261 < 2048 ? local48[local71] * local261 >> 11 : 4096 - ((4096 - local48[local71]) * (-local261 + 4096) >> 11);
						local40[local71] = local265 >= 2048 ? 4096 - ((4096 - local52[local71]) * (-local265 + 4096) >> 11) : local52[local71] * local265 >> 11;
						local44[local71] = local269 < 2048 ? local56[local71] * local269 >> 11 : 4096 - ((4096 - local56[local71]) * (-local269 + 4096) >> 11);
					}
				} else {
					@Pc(517) int local517;
					@Pc(513) int local513;
					@Pc(521) int local521;
					if (local71 == 7) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local513 = local52[local71];
							local517 = local48[local71];
							local521 = local56[local71];
							local36[local71] = local517 == 4096 ? 4096 : (local60[local71] << 12) / (4096 - local517);
							local40[local71] = local513 == 4096 ? 4096 : (local64[local71] << 12) / (4096 - local513);
							local44[local71] = local521 == 4096 ? 4096 : (local68[local71] << 12) / (4096 - local521);
						}
					} else if (local71 == 8) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local517 = local48[local71];
							local513 = local52[local71];
							local521 = local56[local71];
							local36[local71] = local517 == 0 ? 0 : 4096 - (4096 - local60[local71] << 12) / local517;
							local40[local71] = local513 == 0 ? 0 : 4096 - (4096 - local64[local71] << 12) / local513;
							local44[local71] = local521 == 0 ? 0 : 4096 - (4096 - local68[local71] << 12) / local521;
						}
					} else if (local71 == 9) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local265 = local64[local71];
							local521 = local56[local71];
							local517 = local48[local71];
							local261 = local60[local71];
							local269 = local68[local71];
							local513 = local52[local71];
							local36[local71] = local517 >= local261 ? local261 : local517;
							local40[local71] = local513 < local265 ? local513 : local265;
							local44[local71] = local521 < local269 ? local521 : local269;
						}
					} else if (local71 == 10) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local265 = local64[local71];
							local261 = local60[local71];
							local521 = local56[local71];
							local269 = local68[local71];
							local517 = local48[local71];
							local513 = local52[local71];
							local36[local71] = local261 < local517 ? local517 : local261;
							local40[local71] = local513 > local265 ? local513 : local265;
							local44[local71] = local521 <= local269 ? local269 : local521;
						}
					} else if (local71 == 11) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local269 = local68[local71];
							local517 = local48[local71];
							local261 = local60[local71];
							local265 = local64[local71];
							local521 = local56[local71];
							local513 = local52[local71];
							local36[local71] = local261 < local517 ? local517 - local261 : -local517 + local261;
							local40[local71] = local265 < local513 ? local513 - local265 : local265 - local513;
							local44[local71] = local269 >= local521 ? local269 - local521 : local521 - local269;
						}
					} else if (local71 == 12) {
						for (local71 = 0; local71 < MidiPcmStream.anInt1616; local71++) {
							local265 = local64[local71];
							local513 = local52[local71];
							local517 = local48[local71];
							local261 = local60[local71];
							local521 = local56[local71];
							local269 = local68[local71];
							local36[local71] = local517 + local261 - (local517 * local261 >> 11);
							local40[local71] = local513 + local265 - (local513 * local265 >> 11);
							local44[local71] = local269 + local521 - (local521 * local269 >> 11);
						}
					}
				}
			}
		}
		return local16;
	}

	@OriginalMember(owner = "client!ju", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt3770 = arg1.method2490();
		} else if (arg0 == 1) {
			super.monochrome = arg1.method2490() == 1;
		}
	}

	@OriginalMember(owner = "client!ju", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			@Pc(35) int[] local35 = this.getChildMonochromeOutput(arg0, 1);
			@Pc(38) int local38 = this.anInt3770;
			if (local38 == 1) {
				for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
					local19[local38] = local35[local38] + local29[local38];
				}
			} else if (local38 == 2) {
				for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
					local19[local38] = local29[local38] - local35[local38];
				}
			} else if (local38 == 3) {
				for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
					local19[local38] = local35[local38] * local29[local38] >> 12;
				}
			} else {
				@Pc(157) int local157;
				if (local38 == 4) {
					for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
						local157 = local35[local38];
						local19[local38] = local157 == 0 ? 4096 : (local29[local38] << 12) / local157;
					}
				} else if (local38 == 5) {
					for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
						local19[local38] = 4096 - ((4096 - local29[local38]) * (-local35[local38] + 4096) >> 12);
					}
				} else if (local38 == 6) {
					for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
						local157 = local35[local38];
						local19[local38] = local157 >= 2048 ? 4096 - ((4096 - local157) * (-local29[local38] + 4096) >> 11) : local157 * local29[local38] >> 11;
					}
				} else {
					@Pc(264) int local264;
					if (local38 == 7) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local264 = local29[local38];
							local19[local38] = local264 == 4096 ? 4096 : (local35[local38] << 12) / (4096 - local264);
						}
					} else if (local38 == 8) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local264 = local29[local38];
							local19[local38] = local264 == 0 ? 0 : 4096 - (4096 - local35[local38] << 12) / local264;
						}
					} else if (local38 == 9) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local157 = local35[local38];
							local264 = local29[local38];
							local19[local38] = local157 <= local264 ? local157 : local264;
						}
					} else if (local38 == 10) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local264 = local29[local38];
							local157 = local35[local38];
							local19[local38] = local264 <= local157 ? local157 : local264;
						}
					} else if (local38 == 11) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local264 = local29[local38];
							local157 = local35[local38];
							local19[local38] = local157 < local264 ? local264 - local157 : -local264 + local157;
						}
					} else if (local38 == 12) {
						for (local38 = 0; local38 < MidiPcmStream.anInt1616; local38++) {
							local264 = local29[local38];
							local157 = local35[local38];
							local19[local38] = local264 + local157 - (local264 * local157 >> 11);
						}
					}
				}
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!ju", name = "H", descriptor = "Lclient!cn;")
	public static WorldMapElement aClass4_Sub1_Sub5_2;
@OriginalMember(owner = "client!ju", name = "K", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_45;
@OriginalMember(owner = "client!ju", name = "I", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_39 = new Class215(32, -1);
@OriginalMember(owner = "client!ju", name = "a", descriptor = "(Lclient!et;ILjava/lang/String;I)Lclient!qt;")
	public static SignedResource method3110(@OriginalArg(0) SignLink arg0, @OriginalArg(1) int arg1, @OriginalArg(2) String arg2) {
		if (arg1 == 0) {
			return arg0.method1744(arg2);
		}
		@Pc(44) SignedResource local44;
		if (arg1 == 1) {
			try {
				Static458.method2591(arg0.anApplet1, "openjs", new Object[] { (new URL(arg0.anApplet1.getCodeBase(), arg2)).toString() });
				local44 = new SignedResource();
				local44.anInt5760 = 1;
				return local44;
			} catch (@Pc(50) Throwable local50) {
				local44 = new SignedResource();
				local44.anInt5760 = 2;
				return local44;
			}
		} else if (arg1 == 2) {
			try {
				arg0.anApplet1.getAppletContext().showDocument(new URL(arg0.anApplet1.getCodeBase(), arg2), "_blank");
				local44 = new SignedResource();
				local44.anInt5760 = 1;
				return local44;
			} catch (@Pc(86) Exception local86) {
				local44 = new SignedResource();
				local44.anInt5760 = 2;
				return local44;
			}
		} else if (arg1 == 3) {
			try {
				Static458.method2593(arg0.anApplet1, "loggedout");
			} catch (@Pc(106) Throwable local106) {
			}
			try {
				arg0.anApplet1.getAppletContext().showDocument(new URL(arg0.anApplet1.getCodeBase(), arg2), "_top");
				local44 = new SignedResource();
				local44.anInt5760 = 1;
				return local44;
			} catch (@Pc(128) Exception local128) {
				local44 = new SignedResource();
				local44.anInt5760 = 2;
				return local44;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
@OriginalMember(owner = "client!ju", name = "a", descriptor = "(Lclient!qc;)V")
	public static void method3111(@OriginalArg(0) Class191 arg0) {
		if (Ground.anInt7322 >= 65535) {
			return;
		}
		@Pc(6) PointLight local6 = arg0.aClass4_Sub21_2;
		StructTypeList.aClass191Array1[Ground.anInt7322] = arg0;
		MouseManager.aBooleanArray12[Ground.anInt7322] = false;
		Ground.anInt7322++;
		@Pc(21) int local21 = arg0.anInt5570;
		if (arg0.aBoolean500) {
			local21 = 0;
		}
		@Pc(29) int local29 = arg0.anInt5570;
		if (arg0.aBoolean499) {
			local29 = RemoteFileWorker.anInt4487 - 1;
		}
		for (@Pc(38) int local38 = local21; local38 <= local29; local38++) {
			@Pc(41) int local41 = 0;
			@Pc(53) int local53 = local6.method3164() + ReflectingTextureEffect.anInt5747 - local6.method3165() >> Static231.anInt4434;
			if (local53 < 0) {
				local41 = -local53;
				local53 = 0;
			}
			@Pc(73) int local73 = local6.method3164() + local6.method3165() - ReflectingTextureEffect.anInt5747 >> Static231.anInt4434;
			if (local73 >= GlModel.anInt3361) {
				local73 = GlModel.anInt3361 - 1;
			}
			for (@Pc(82) int local82 = local53; local82 <= local73; local82++) {
				@Pc(89) short local89 = arg0.aShortArray189[local41++];
				@Pc(105) int local105 = (local6.method3160() + ReflectingTextureEffect.anInt5747 - local6.method3165() >> Static231.anInt4434) + (local89 >>> 8);
				@Pc(113) int local113 = local105 + (local89 & 0xFF) - 1;
				if (local105 < 0) {
					local105 = 0;
				}
				if (local113 >= GpuFaceNormal.menuOpenWidth) {
					local113 = GpuFaceNormal.menuOpenWidth - 1;
				}
				for (@Pc(126) int local126 = local105; local126 <= local113; local126++) {
					@Pc(135) long local135 = Terrain.aLongArrayArrayArray2[local38][local126][local82];
					if ((local135 & 0xFFFFL) == 0L) {
						Terrain.aLongArrayArrayArray2[local38][local126][local82] = local135 | (long) Ground.anInt7322;
					} else if ((local135 & 0xFFFF0000L) == 0L) {
						Terrain.aLongArrayArrayArray2[local38][local126][local82] = local135 | (long) Ground.anInt7322 << 16;
					} else if ((local135 & 0xFFFF00000000L) == 0L) {
						Terrain.aLongArrayArrayArray2[local38][local126][local82] = local135 | (long) Ground.anInt7322 << 32;
					} else if ((local135 & 0xFFFF000000000000L) == 0L) {
						Terrain.aLongArrayArrayArray2[local38][local126][local82] = local135 | (long) Ground.anInt7322 << 48;
					}
				}
			}
		}
	}
}
