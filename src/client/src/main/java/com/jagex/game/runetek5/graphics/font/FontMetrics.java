package com.jagex.game.runetek5.graphics.font;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Static312;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.graphics.font.*;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
@OriginalClass("client!gt")
public final class FontMetrics {

	@OriginalMember(owner = "client!gt", name = "n", descriptor = "[B")
	private final byte[] glyphWidths;

	@OriginalMember(owner = "client!gt", name = "i", descriptor = "[[B")
	private byte[][] kerning;

	@OriginalMember(owner = "client!gt", name = "k", descriptor = "I")
	public final int anInt2786;

	@OriginalMember(owner = "client!gt", name = "g", descriptor = "I")
	public final int anInt2783;

	@OriginalMember(owner = "client!gt", name = "b", descriptor = "I")
	public final int anInt2778;

	@OriginalMember(owner = "client!gt", name = "<init>", descriptor = "([B)V")
	public FontMetrics(@OriginalArg(0) byte[] bytes) {
		@Pc(8) Packet local8 = new Packet(bytes);
		@Pc(12) int local12 = local8.method2490();
		if (local12 != 0) {
			throw new RuntimeException("");
		}
		@Pc(28) boolean local28 = local8.method2490() == 1;
		this.glyphWidths = new byte[256];
		local8.method2508(256, this.glyphWidths);
		if (local28) {
			@Pc(44) int[] local44 = new int[256];
			for (@Pc(46) int local46 = 0; local46 < 256; local46++) {
				local44[local46] = local8.method2490();
			}
			@Pc(63) int[] local63 = new int[256];
			for (@Pc(65) int local65 = 0; local65 < 256; local65++) {
				local63[local65] = local8.method2490();
			}
			@Pc(80) byte[][] local80 = new byte[256][];
			@Pc(95) int local95;
			for (@Pc(82) int local82 = 0; local82 < 256; local82++) {
				local80[local82] = new byte[local44[local82]];
				@Pc(93) byte local93 = 0;
				for (local95 = 0; local95 < local80[local82].length; local95++) {
					local93 += local8.method2542();
					local80[local82][local95] = local93;
				}
			}
			@Pc(128) byte[][] local128 = new byte[256][];
			@Pc(143) int local143;
			for (local95 = 0; local95 < 256; local95++) {
				local128[local95] = new byte[local44[local95]];
				@Pc(141) byte local141 = 0;
				for (local143 = 0; local143 < local128[local95].length; local143++) {
					local141 += local8.method2542();
					local128[local95][local143] = local141;
				}
			}
			this.kerning = new byte[256][256];
			for (@Pc(178) int local178 = 0; local178 < 256; local178++) {
				if (local178 != 32 && local178 != 160) {
					for (local143 = 0; local143 < 256; local143++) {
						if (local143 != 32 && local143 != 160) {
							this.kerning[local178][local143] = (byte) Class16_Sub1.method5345(local143, local63, local128, this.glyphWidths, local178, local44, local80);
						}
					}
				}
			}
			this.anInt2786 = local44[32] + local63[32];
		} else {
			this.anInt2786 = local8.method2490();
		}
		local8.method2490();
		local8.method2490();
		this.anInt2783 = local8.method2490();
		this.anInt2778 = local8.method2490();
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "([Lclient!l;[IB[Ljava/lang/String;Ljava/lang/String;)I")
	public int method2339(@OriginalArg(0) NativeSprite[] arg0, @OriginalArg(1) int[] arg1, @OriginalArg(3) String[] arg2, @OriginalArg(4) String arg3) {
		if (arg3 == null) {
			return 0;
		}
		@Pc(11) int local11 = 0;
		@Pc(13) int local13 = 0;
		@Pc(23) int local23 = -1;
		@Pc(25) int local25 = 0;
		@Pc(27) byte local27 = 0;
		@Pc(29) int local29 = -1;
		@Pc(31) int local31 = -1;
		@Pc(33) int local33 = 0;
		@Pc(36) int local36 = arg3.length();
		for (@Pc(38) int local38 = 0; local38 < local36; local38++) {
			@Pc(48) int local48 = Class4_Sub1_Sub11.method2605(arg3.charAt(local38)) & 0xFF;
			@Pc(50) int local50 = 0;
			if (local48 == 60) {
				local29 = local38;
			} else {
				@Pc(72) int local72;
				if (local29 == -1) {
					local50 = this.glyphWidth(local48);
					local72 = local38;
					if (this.kerning != null && local31 != -1) {
						local50 += this.kerning[local31][local48];
					}
					local31 = local48;
				} else {
					if (local48 != 62) {
						continue;
					}
					local72 = local29;
					@Pc(103) String local103 = arg3.substring(local29 + 1, local38);
					local29 = -1;
					if (local103.equals("br")) {
						arg2[local33] = arg3.substring(local13, local38 + 1);
						local33++;
						if (local33 >= arg2.length) {
							return 0;
						}
						local31 = -1;
						local23 = -1;
						local11 = 0;
						local13 = local38 + 1;
						continue;
					}
					if (local103.equals("lt")) {
						local50 = this.glyphWidth(60);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][60];
						}
						local31 = 60;
					} else if (local103.equals("gt")) {
						local50 = this.glyphWidth(62);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][62];
						}
						local31 = 62;
					} else if (local103.equals("nbsp")) {
						local50 = this.glyphWidth(160);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][160];
						}
						local31 = 160;
					} else if (local103.equals("shy")) {
						local50 = this.glyphWidth(173);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][173];
						}
						local31 = 173;
					} else if (local103.equals("times")) {
						local50 = this.glyphWidth(215);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][215];
						}
						local31 = 215;
					} else if (local103.equals("euro")) {
						local50 = this.glyphWidth(8364);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][8364];
						}
						local31 = 8364;
					} else if (local103.equals("copy")) {
						local50 = this.glyphWidth(169);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][169];
						}
						local31 = 169;
					} else if (local103.equals("reg")) {
						local50 = this.glyphWidth(174);
						if (this.kerning != null && local31 != -1) {
							local50 += this.kerning[local31][174];
						}
						local31 = 174;
					} else if (local103.startsWith("img=") && arg0 != null) {
						try {
							@Pc(284) int local284 = ParticleProducerType.method3113(local103.substring(4));
							local31 = -1;
							local50 = arg0[local284].scaleWidth();
						} catch (@Pc(295) Exception local295) {
						}
					}
					local48 = -1;
				}
				if (local50 > 0) {
					local11 += local50;
					if (arg1 != null) {
						if (local48 == 32) {
							local23 = local38;
							local25 = local11;
							local27 = 1;
						}
						if (arg1[local33 < arg1.length ? local33 : arg1.length - 1] < local11) {
							if (local23 >= 0) {
								arg2[local33] = arg3.substring(local13, local23 + 1 - local27);
								local33++;
								if (arg2.length <= local33) {
									return 0;
								}
								local13 = local23 + 1;
								local31 = -1;
								local23 = -1;
								local11 -= local25;
							} else {
								arg2[local33] = arg3.substring(local13, local72);
								local33++;
								if (local33 >= arg2.length) {
									return 0;
								}
								local23 = -1;
								local11 = local50;
								local31 = -1;
								local13 = local72;
							}
						}
						if (local48 == 45) {
							local23 = local38;
							local25 = local11;
							local27 = 0;
						}
					}
				}
			}
		}
		if (local13 < arg3.length()) {
			arg2[local33] = arg3.substring(local13, arg3.length());
			local33++;
		}
		return local33;
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(ILjava/lang/String;)I")
	public int getWidthNoSprites(@OriginalArg(1) String arg0) {
		return this.method2342(arg0, null);
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(BIC)I")
	public int getKerning(@OriginalArg(1) int arg0, @OriginalArg(2) char arg1) {
		return this.kerning == null ? 0 : this.kerning[arg0][arg1];
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(Ljava/lang/String;I[Lclient!l;)I")
	public int method2342(@OriginalArg(0) String arg0, @OriginalArg(2) NativeSprite[] arg1) {
		if (arg0 == null) {
			return 0;
		}
		@Pc(11) int local11 = -1;
		@Pc(13) int local13 = -1;
		@Pc(15) int local15 = 0;
		@Pc(18) int local18 = arg0.length();
		for (@Pc(25) int local25 = 0; local25 < local18; local25++) {
			@Pc(31) char local31 = arg0.charAt(local25);
			if (local31 == '<') {
				local11 = local25;
			} else {
				if (local31 == '>' && local11 != -1) {
					@Pc(54) String local54 = arg0.substring(local11 + 1, local25);
					local11 = -1;
					if (local54.equals("lt")) {
						local31 = '<';
					} else if (local54.equals("gt")) {
						local31 = '>';
					} else if (local54.equals("nbsp")) {
						local31 = ' ';
					} else if (local54.equals("shy")) {
						local31 = '\u00ad';
					} else if (local54.equals("times")) {
						local31 = '×';
					} else if (local54.equals("euro")) {
						local31 = '€';
					} else if (local54.equals("copy")) {
						local31 = '©';
					} else {
						if (!local54.equals("reg")) {
							if (local54.startsWith("img=") && arg1 != null) {
								try {
									@Pc(116) int local116 = ParticleProducerType.method3113(local54.substring(4));
									local13 = -1;
									local15 += arg1[local116].scaleWidth();
								} catch (@Pc(127) Exception local127) {
								}
							}
							continue;
						}
						local31 = '®';
					}
				}
				if (local11 == -1) {
					local15 += this.glyphWidths[Class4_Sub1_Sub11.method2605(local31) & 0xFF] & 0xFF;
					if (this.kerning != null && local13 != -1) {
						local15 += this.kerning[local13][local31];
					}
					local13 = local31;
				}
			}
		}
		return local15;
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(II)I")
	public int glyphWidth(@OriginalArg(1) int arg0) {
		return this.glyphWidths[arg0] & 0xFF;
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(Ljava/lang/String;II[Lclient!l;I)I")
	public int method2345(@OriginalArg(0) String arg0, @OriginalArg(3) NativeSprite[] arg1) {
		@Pc(11) int local11 = this.anInt2786;
		@Pc(24) int local24 = this.method2339(arg1, new int[] { 100 }, Static312.aStringArray25, arg0);
		@Pc(30) int local30 = local11 * (local24 - 1);
		return this.anInt2783 + local30 + this.anInt2778;
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(I[Lclient!l;Ljava/lang/String;I)Ljava/lang/String;")
	public String method2346(@OriginalArg(1) NativeSprite[] arg0, @OriginalArg(2) String arg1, @OriginalArg(3) int arg2) {
		if (arg2 >= this.method2342(arg1, arg0)) {
			return arg1;
		}
		@Pc(26) int local26 = arg2 - this.method2342("...", null);
		@Pc(28) int local28 = -1;
		@Pc(30) int local30 = -1;
		@Pc(32) int local32 = 0;
		@Pc(35) int local35 = arg1.length();
		@Pc(37) String local37 = "";
		for (@Pc(39) int local39 = 0; local39 < local35; local39++) {
			@Pc(45) char local45 = arg1.charAt(local39);
			if (local45 == '<') {
				local28 = local39;
			} else {
				if (local45 == '>' && local28 != -1) {
					@Pc(68) String local68 = arg1.substring(local28 + 1, local39);
					local28 = -1;
					if (local68.equals("lt")) {
						local45 = '<';
					} else if (local68.equals("gt")) {
						local45 = '>';
					} else if (local68.equals("nbsp")) {
						local45 = ' ';
					} else if (local68.equals("shy")) {
						local45 = '\u00ad';
					} else if (local68.equals("times")) {
						local45 = '×';
					} else if (local68.equals("euro")) {
						local45 = '€';
					} else if (local68.equals("copy")) {
						local45 = '©';
					} else {
						if (!local68.equals("reg")) {
							if (local68.startsWith("img=") && arg0 != null) {
								try {
									@Pc(138) int local138 = ParticleProducerType.method3113(local68.substring(4));
									local32 += arg0[local138].scaleWidth();
									local30 = -1;
									if (local26 < local32) {
										return local37 + "...";
									}
									local37 = arg1.substring(0, local39 + 1);
								} catch (@Pc(172) Exception local172) {
								}
							}
							continue;
						}
						local45 = '®';
					}
				}
				if (local28 == -1) {
					local32 += this.glyphWidths[Class4_Sub1_Sub11.method2605(local45) & 0xFF] & 0xFF;
					if (this.kerning != null && local30 != -1) {
						local32 += this.kerning[local30][local45];
					}
					local30 = local45;
					@Pc(219) int local219 = local32;
					if (this.kerning != null) {
						local219 = local32 + this.kerning[local45][46];
					}
					if (local26 < local219) {
						return local37 + "...";
					}
					local37 = arg1.substring(0, local39 + 1);
				}
			}
		}
		return arg1;
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(I[Lclient!l;ILjava/lang/String;)I")
	public int method2347(@OriginalArg(1) NativeSprite[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) String arg2) {
		return this.method2339(arg0, new int[] { arg1 }, Static312.aStringArray25, arg2);
	}

	@OriginalMember(owner = "client!gt", name = "a", descriptor = "(BLjava/lang/String;I[Lclient!l;)I")
	public int method2349(@OriginalArg(1) String arg0, @OriginalArg(2) int arg1, @OriginalArg(3) NativeSprite[] arg2) {
		@Pc(18) int local18 = this.method2339(arg2, new int[] { arg1 }, Static312.aStringArray25, arg0);
		@Pc(20) int local20 = 0;
		for (@Pc(22) int local22 = 0; local22 < local18; local22++) {
			@Pc(40) int local40 = this.method2342(Static312.aStringArray25[local22], arg2);
			if (local20 < local40) {
				local20 = local40;
			}
		}
		return local20;
	}

@OriginalMember(owner = "client!gt", name = "q", descriptor = "I")
	public static int anInt2790;
@OriginalMember(owner = "client!gt", name = "r", descriptor = "[Lclient!ak;")
	public static Class10[] aClass10Array1;
@OriginalMember(owner = "client!gt", name = "o", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_53 = new Class83("Cancel", "Abbrechen", "Annuler", "Cancelar");
@OriginalMember(owner = "client!gt", name = "a", descriptor = "(III)Lclient!rb;")
	public static Class16_Sub5 method2344(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		return local7 == null ? null : local7.aClass16_Sub5_2;
	}
}
