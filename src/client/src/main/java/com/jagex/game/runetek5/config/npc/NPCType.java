package com.jagex.game.runetek5.config.npc;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Static398;
import com.jagex.game.runetek5.deob.Static78;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.vardomain.VarDomain;

import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Static340;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.deob.Static410;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.config.npc.*;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.deob.Class25;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.deob.Class208;
@OriginalClass("client!wh")
public final class NPCType {

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "[B")
	private byte[] recolorDPalette;

	@OriginalMember(owner = "client!wh", name = "c", descriptor = "[S")
	private short[] aShortArray245;

	@OriginalMember(owner = "client!wh", name = "f", descriptor = "[S")
	private short[] aShortArray246;

	@OriginalMember(owner = "client!wh", name = "l", descriptor = "[S")
	private short[] aShortArray247;

	@OriginalMember(owner = "client!wh", name = "t", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!wh", name = "u", descriptor = "[[I")
	private int[][] modelOffsets;

	@OriginalMember(owner = "client!wh", name = "w", descriptor = "[[I")
	private int[][] anIntArrayArray63;

	@OriginalMember(owner = "client!wh", name = "A", descriptor = "[I")
	public int[] anIntArray544;

	@OriginalMember(owner = "client!wh", name = "E", descriptor = "B")
	private byte aByte96;

	@OriginalMember(owner = "client!wh", name = "F", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_41;

	@OriginalMember(owner = "client!wh", name = "H", descriptor = "B")
	private byte aByte97;

	@OriginalMember(owner = "client!wh", name = "L", descriptor = "[I")
	private int[] modelIds;

	@OriginalMember(owner = "client!wh", name = "hb", descriptor = "[S")
	private short[] aShortArray248;

	@OriginalMember(owner = "client!wh", name = "kb", descriptor = "[I")
	private int[] headModels;

	@OriginalMember(owner = "client!wh", name = "ob", descriptor = "[I")
	public int[] anIntArray547;

	@OriginalMember(owner = "client!wh", name = "rb", descriptor = "B")
	private byte aByte102;

	@OriginalMember(owner = "client!wh", name = "xb", descriptor = "Lclient!uk;")
	public NPCTypeList npcTypeList;

	@OriginalMember(owner = "client!wh", name = "o", descriptor = "B")
	private byte aByte95 = 0;

	@OriginalMember(owner = "client!wh", name = "k", descriptor = "I")
	public int anInt7205 = -1;

	@OriginalMember(owner = "client!wh", name = "j", descriptor = "I")
	public int anInt7204 = -1;

	@OriginalMember(owner = "client!wh", name = "I", descriptor = "I")
	public int anInt7218 = -1;

	@OriginalMember(owner = "client!wh", name = "b", descriptor = "Z")
	public boolean aBoolean679 = true;

	@OriginalMember(owner = "client!wh", name = "x", descriptor = "I")
	private int anInt7213 = -1;

	@OriginalMember(owner = "client!wh", name = "N", descriptor = "I")
	private int anInt7220 = 128;

	@OriginalMember(owner = "client!wh", name = "d", descriptor = "Z")
	public boolean aBoolean680 = false;

	@OriginalMember(owner = "client!wh", name = "O", descriptor = "I")
	public int anInt7221 = 32;

	@OriginalMember(owner = "client!wh", name = "h", descriptor = "I")
	public int anInt7202 = -1;

	@OriginalMember(owner = "client!wh", name = "P", descriptor = "B")
	public byte aByte99 = 0;

	@OriginalMember(owner = "client!wh", name = "W", descriptor = "B")
	public byte aByte101 = 4;

	@OriginalMember(owner = "client!wh", name = "y", descriptor = "I")
	public int anInt7214 = -1;

	@OriginalMember(owner = "client!wh", name = "n", descriptor = "Z")
	public boolean highPriority = false;

	@OriginalMember(owner = "client!wh", name = "Y", descriptor = "Z")
	public boolean aBoolean683 = true;

	@OriginalMember(owner = "client!wh", name = "eb", descriptor = "I")
	private int anInt7231 = -1;

	@OriginalMember(owner = "client!wh", name = "bb", descriptor = "I")
	public int anInt7229 = -1;

	@OriginalMember(owner = "client!wh", name = "v", descriptor = "I")
	public int anInt7212 = -1;

	@OriginalMember(owner = "client!wh", name = "Q", descriptor = "B")
	public byte aByte100 = -16;

	@OriginalMember(owner = "client!wh", name = "K", descriptor = "[Ljava/lang/String;")
	public final String[] actions = new String[5];

	@OriginalMember(owner = "client!wh", name = "C", descriptor = "Z")
	public boolean aBoolean682 = true;

	@OriginalMember(owner = "client!wh", name = "db", descriptor = "Ljava/lang/String;")
	public String aString70 = "null";

	@OriginalMember(owner = "client!wh", name = "V", descriptor = "S")
	public short aShort101 = 0;

	@OriginalMember(owner = "client!wh", name = "s", descriptor = "I")
	public int anInt7210 = -1;

	@OriginalMember(owner = "client!wh", name = "m", descriptor = "I")
	public int anInt7206 = -1;

	@OriginalMember(owner = "client!wh", name = "sb", descriptor = "I")
	public int anInt7238 = 0;

	@OriginalMember(owner = "client!wh", name = "lb", descriptor = "I")
	public int lb = -1;

	@OriginalMember(owner = "client!wh", name = "fb", descriptor = "I")
	private int anInt7232 = 0;

	@OriginalMember(owner = "client!wh", name = "g", descriptor = "I")
	public int anInt7201 = 1;

	@OriginalMember(owner = "client!wh", name = "p", descriptor = "I")
	public int anInt7207 = -1;

	@OriginalMember(owner = "client!wh", name = "pb", descriptor = "I")
	public int anInt7237 = -1;

	@OriginalMember(owner = "client!wh", name = "tb", descriptor = "S")
	public short aShort102 = 0;

	@OriginalMember(owner = "client!wh", name = "M", descriptor = "I")
	public int anInt7219 = -1;

	@OriginalMember(owner = "client!wh", name = "qb", descriptor = "Z")
	public boolean shadowed = true;

	@OriginalMember(owner = "client!wh", name = "J", descriptor = "B")
	public byte aByte98 = -96;

	@OriginalMember(owner = "client!wh", name = "T", descriptor = "I")
	private int anInt7224 = 128;

	@OriginalMember(owner = "client!wh", name = "jb", descriptor = "I")
	public int anInt7235 = 255;

	@OriginalMember(owner = "client!wh", name = "ib", descriptor = "I")
	public int iconHeight = -1;

	@OriginalMember(owner = "client!wh", name = "ub", descriptor = "I")
	public int anInt7239 = -1;

	@OriginalMember(owner = "client!wh", name = "yb", descriptor = "I")
	public int anInt7241 = -1;

	@OriginalMember(owner = "client!wh", name = "vb", descriptor = "Z")
	public boolean aBoolean685 = false;

	@OriginalMember(owner = "client!wh", name = "r", descriptor = "I")
	public int overheadSprite = -1;

	@OriginalMember(owner = "client!wh", name = "ab", descriptor = "I")
	private int anInt7228 = 0;

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(Lclient!vs;I)Z")
	public boolean resolvesToNpc(@OriginalArg(0) VarDomain varDomain) {
		if (this.anIntArray544 == null) {
			return true;
		}
		@Pc(10) int local10 = -1;
		if (this.anInt7213 != -1) {
			local10 = varDomain.getVarBitValue(this.anInt7213);
		} else if (this.anInt7231 != -1) {
			local10 = varDomain.getVarValueInt(this.anInt7231);
		}
		if (local10 < 0 || this.anIntArray544.length - 1 <= local10 || this.anIntArray544[local10] == -1) {
			@Pc(74) int local74 = this.anIntArray544[this.anIntArray544.length - 1];
			return local74 != -1;
		} else {
			return true;
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(ZLclient!vs;)Lclient!wh;")
	public NPCType method5574(@OriginalArg(1) VarDomain arg0) {
		@Pc(20) int local20 = -1;
		if (this.anInt7213 != -1) {
			local20 = arg0.getVarBitValue(this.anInt7213);
		} else if (this.anInt7231 != -1) {
			local20 = arg0.getVarValueInt(this.anInt7231);
		}
		if (local20 < 0 || local20 >= this.anIntArray544.length - 1 || this.anIntArray544[local20] == -1) {
			@Pc(71) int local71 = this.anIntArray544[this.anIntArray544.length - 1];
			return local71 == -1 ? null : this.npcTypeList.method5229(local71);
		} else {
			return this.npcTypeList.method5229(this.anIntArray544[local20]);
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(IILclient!lp;Lclient!vs;IIIILclient!ya;II[Lclient!nv;Lclient!lp;Lclient!du;Lclient!v;)Lclient!t;")
	public Model method5575(@OriginalArg(0) int arg0, @OriginalArg(2) SeqType arg1, @OriginalArg(3) VarDomain arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) com.jagex.game.runetek5.graphics.Toolkit arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) PathingEntitySequence[] arg10, @OriginalArg(12) SeqType arg11, @OriginalArg(13) SeqTypeList arg12, @OriginalArg(14) Class249 arg13) {
		if (this.anIntArray544 != null) {
			@Pc(13) NPCType local13 = this.method5574(arg2);
			return local13 == null ? null : local13.method5575(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12, arg13);
		}
		@Pc(37) int local37 = arg4;
		if (this.anInt7224 != 128) {
			local37 = arg4 | 0x2;
		}
		if (this.anInt7220 != 128) {
			local37 |= 0x5;
		}
		@Pc(63) boolean local63 = arg1 != null || arg11 != null;
		@Pc(65) boolean local65 = false;
		@Pc(67) boolean local67 = false;
		@Pc(69) boolean local69 = false;
		@Pc(71) boolean local71 = false;
		@Pc(78) int local78 = arg10 == null ? 0 : arg10.length;
		@Pc(112) int local112;
		@Pc(117) int local117;
		for (@Pc(80) int local80 = 0; local80 < local78; local80++) {
			Class25.aClass4_Sub1_Sub17Array3[local80] = null;
			if (arg10[local80] != null) {
				@Pc(98) SeqType local98 = arg12.method1397(arg10[local80].anInt5139);
				if (local98.durations != null) {
					local63 = true;
					Static398.aClass138Array3[local80] = local98;
					local112 = arg10[local80].anInt5138;
					local117 = arg10[local80].anInt5140;
					@Pc(122) int local122 = local98.durations[local112];
					Class25.aClass4_Sub1_Sub17Array3[local80] = arg12.method1391(local122 >>> 16);
					@Pc(135) int local135 = local122 & 0xFFFF;
					FixedFunctionSpecularPass.anIntArray378[local80] = local135;
					if (Class25.aClass4_Sub1_Sub17Array3[local80] != null) {
						local67 |= Class25.aClass4_Sub1_Sub17Array3[local80].method4207(local135);
						local65 |= Class25.aClass4_Sub1_Sub17Array3[local80].method4214(local135);
						local71 |= Class25.aClass4_Sub1_Sub17Array3[local80].method4215(local135);
						local69 |= local98.lights;
					}
					if ((local98.tweened || OutputStream_Sub1.aBoolean244) && local117 != -1 && local98.durations.length > local117) {
						Class208.anIntArray419[local80] = local98.frames[local112];
						Class56.anIntArray137[local80] = arg10[local80].anInt5142;
						@Pc(212) int local212 = local98.durations[local117];
						Class9.aClass4_Sub1_Sub17Array14[local80] = arg12.method1391(local212 >>> 16);
						@Pc(225) int local225 = local212 & 0xFFFF;
						Huffman.anIntArray233[local80] = local225;
						if (Class9.aClass4_Sub1_Sub17Array14[local80] != null) {
							local67 |= Class9.aClass4_Sub1_Sub17Array14[local80].method4207(local225);
							local65 |= Class9.aClass4_Sub1_Sub17Array14[local80].method4214(local225);
							local71 |= Class9.aClass4_Sub1_Sub17Array14[local80].method4215(local225);
						}
					} else {
						Class208.anIntArray419[local80] = 0;
						Class56.anIntArray137[local80] = 0;
						Class9.aClass4_Sub1_Sub17Array14[local80] = null;
						Huffman.anIntArray233[local80] = -1;
					}
				}
			}
		}
		@Pc(288) int local288 = -1;
		local112 = -1;
		local117 = 0;
		@Pc(294) Class4_Sub1_Sub17 local294 = null;
		@Pc(296) Class4_Sub1_Sub17 local296 = null;
		@Pc(298) int local298 = -1;
		@Pc(300) int local300 = -1;
		@Pc(302) int local302 = 0;
		@Pc(304) Class4_Sub1_Sub17 local304 = null;
		@Pc(306) Class4_Sub1_Sub17 local306 = null;
		@Pc(387) int local387;
		if (local63) {
			@Pc(319) int local319;
			if (arg1 != null) {
				local288 = arg1.durations[arg8];
				local319 = local288 >>> 16;
				local288 &= 0xFFFF;
				local294 = arg12.method1391(local319);
				if (local294 != null) {
					local67 |= local294.method4207(local288);
					local65 |= local294.method4214(local288);
					local71 |= local294.method4215(local288);
					local69 |= arg1.lights;
				}
				if ((arg1.tweened || OutputStream_Sub1.aBoolean244) && arg3 != -1 && arg3 < arg1.durations.length) {
					local112 = arg1.durations[arg3];
					local117 = arg1.frames[arg8];
					local387 = local112 >>> 16;
					if (local387 == local319) {
						local296 = local294;
					} else {
						local296 = arg12.method1391(local387);
					}
					local112 &= 0xFFFF;
					if (local296 != null) {
						local67 |= local296.method4207(local112);
						local65 |= local296.method4214(local112);
						local71 |= local296.method4215(local112);
					}
				}
			}
			local37 |= 0x20;
			if (arg11 != null) {
				local298 = arg11.durations[arg6];
				local319 = local298 >>> 16;
				local298 &= 0xFFFF;
				local304 = arg12.method1391(local319);
				if (local304 != null) {
					local67 |= local304.method4207(local298);
					local65 |= local304.method4214(local298);
					local71 |= local304.method4215(local298);
					local69 |= arg11.lights;
				}
				if ((arg11.tweened || OutputStream_Sub1.aBoolean244) && arg9 != -1 && arg9 < arg11.durations.length) {
					local300 = arg11.durations[arg9];
					local302 = arg11.frames[arg6];
					local387 = local300 >>> 16;
					local300 &= 0xFFFF;
					if (local387 == local319) {
						local306 = local304;
					} else {
						local306 = arg12.method1391(local387);
					}
					if (local306 != null) {
						local67 |= local306.method4207(local300);
						local65 |= local306.method4214(local300);
						local71 |= local306.method4215(local300);
					}
				}
			}
			if (local67) {
				local37 |= 0x80;
			}
			if (local65) {
				local37 |= 0x100;
			}
			if (local69) {
				local37 |= 0x200;
			}
			if (local71) {
				local37 |= 0x400;
			}
		}
		@Pc(583) LRUCache local583 = this.npcTypeList.aClass68_49;
		@Pc(600) Model local600;
		synchronized (this.npcTypeList.aClass68_49) {
			local600 = (Model) this.npcTypeList.aClass68_49.method1787((long) (arg7.index << 16 | this.id));
		}
		@Pc(633) int local633;
		if (local600 == null || local37 != (local600.P() & local37)) {
			if (local600 != null) {
				local37 |= local600.P();
			}
			local387 = local37;
			@Pc(625) boolean local625 = false;
			@Pc(629) Js5ResourceProvider local629 = this.npcTypeList.meshes;
			synchronized (this.npcTypeList.meshes) {
				local633 = 0;
				while (true) {
					if (local633 >= this.modelIds.length) {
						break;
					}
					if (this.modelIds[local633] != -1 && !this.npcTypeList.meshes.method2098(0, this.modelIds[local633])) {
						local625 = true;
					}
					local633++;
				}
			}
			if (local625) {
				return null;
			}
			@Pc(676) Mesh[] local676 = new Mesh[this.modelIds.length];
			for (local633 = 0; local633 < this.modelIds.length; local633++) {
				if (this.modelIds[local633] != -1) {
					@Pc(690) Js5ResourceProvider local690 = this.npcTypeList.meshes;
					synchronized (this.npcTypeList.meshes) {
						local676[local633] = SpriteDrawQueue.method4948(this.npcTypeList.meshes, this.modelIds[local633]);
					}
					if (local676[local633] != null) {
						if (local676[local633].version < 13) {
							local676[local633].method3039();
						}
						if (this.modelOffsets != null && this.modelOffsets[local633] != null) {
							local676[local633].method3041(this.modelOffsets[local633][1], this.modelOffsets[local633][0], this.modelOffsets[local633][2]);
						}
					}
				}
			}
			@Pc(770) BasType local770 = null;
			if (this.anInt7241 != -1) {
				local770 = arg13.method5325(this.anInt7241);
			}
			@Pc(788) int local788;
			if (local770 != null && local770.anIntArrayArray38 != null) {
				for (local788 = 0; local788 < local770.anIntArrayArray38.length; local788++) {
					if (local770.anIntArrayArray38[local788] != null && local676.length > local788 && local676[local788] != null) {
						@Pc(812) int local812 = local770.anIntArrayArray38[local788][0];
						@Pc(819) int local819 = local770.anIntArrayArray38[local788][1];
						@Pc(826) int local826 = local770.anIntArrayArray38[local788][2];
						@Pc(835) int local835 = local770.anIntArrayArray38[local788][3] << 3;
						@Pc(844) int local844 = local770.anIntArrayArray38[local788][4] << 3;
						@Pc(853) int local853 = local770.anIntArrayArray38[local788][5] << 3;
						if (this.anIntArrayArray63 == null) {
							this.anIntArrayArray63 = new int[local770.anIntArrayArray38.length][];
						}
						if (this.anIntArrayArray63[local788] == null) {
							@Pc(875) int[] local875 = this.anIntArrayArray63[local788] = new int[15];
							if (local835 == 0 && local844 == 0 && local853 == 0) {
								local875[14] = -local826;
								local875[0] = local875[4] = local875[8] = 32768;
								local875[13] = -local819;
								local875[12] = -local812;
							} else {
								@Pc(917) int local917 = Class50_Sub1.anIntArray224[local835];
								@Pc(921) int local921 = Class50_Sub1.anIntArray225[local835];
								@Pc(925) int local925 = Class50_Sub1.anIntArray224[local844];
								@Pc(929) int local929 = Class50_Sub1.anIntArray225[local844];
								@Pc(933) int local933 = Class50_Sub1.anIntArray224[local853];
								@Pc(937) int local937 = Class50_Sub1.anIntArray225[local853];
								@Pc(945) int local945 = local933 * local921 + 16384 >> 15;
								@Pc(953) int local953 = local937 * local921 + 16384 >> 15;
								local875[3] = local917 * local937 + 16384 >> 15;
								local875[5] = -local921;
								local875[1] = local925 * -local937 + local929 * local945 + 16384 >> 15;
								local875[8] = local925 * local917 + 16384 >> 15;
								local875[7] = local945 * local925 + -local929 * -local937 + 16384 >> 15;
								local875[0] = local933 * local925 + local929 * local953 + 16384 >> 15;
								local875[2] = local929 * local917 + 16384 >> 15;
								local875[4] = local917 * local933 + 16384 >> 15;
								local875[6] = local953 * local925 + -local929 * local933 + 16384 >> 15;
								local875[13] = local875[4] * -local819 + local875[1] * -local812 + -local826 * local875[7] + 16384 >> 15;
								local875[14] = local875[8] * -local826 + -local812 * local875[2] + local875[5] * -local819 + 16384 >> 15;
								local875[12] = -local812 * local875[0] + -local819 * local875[3] + local875[6] * -local826 + 16384 >> 15;
							}
							local875[11] = local826;
							local875[10] = local819;
							local875[9] = local812;
						}
						if (local835 != 0 || local844 != 0 || local853 != 0) {
							local676[local788].method3053(local835, local844, local853);
						}
						if (local812 != 0 || local819 != 0 || local826 != 0) {
							local676[local788].method3041(local819, local812, local826);
						}
					}
				}
			}
			@Pc(1208) Mesh local1208;
			if (local676.length == 1) {
				local1208 = local676[0];
			} else {
				local1208 = new Mesh(local676, local676.length);
			}
			if (this.aShortArray248 != null) {
				local387 = local37 | 0x4000;
			}
			if (this.aShortArray247 != null) {
				local387 |= 0x8000;
			}
			if (this.aByte95 != 0) {
				local387 |= 0x80000;
			}
			local600 = arg7.createModel(local1208, local387, this.npcTypeList.featureMask, this.anInt7228 + 64, this.anInt7232 + 850);
			if (this.aShortArray248 != null) {
				for (local788 = 0; local788 < this.aShortArray248.length; local788++) {
					if (this.recolorDPalette == null || this.recolorDPalette.length <= local788) {
						local600.copyAlpha(this.aShortArray248[local788], this.aShortArray246[local788]);
					} else {
						local600.copyAlpha(this.aShortArray248[local788], Static78.aShortArray46[this.recolorDPalette[local788] & 0xFF]);
					}
				}
			}
			if (this.aShortArray247 != null) {
				for (local788 = 0; local788 < this.aShortArray247.length; local788++) {
					local600.I(this.aShortArray247[local788], this.aShortArray245[local788]);
				}
			}
			if (this.aByte95 != 0) {
				local600.adjustColours(this.aByte96, this.aByte97, this.aByte102, this.aByte95 & 0xFF);
			}
			local600.UA(local37);
			@Pc(1360) LRUCache local1360 = this.npcTypeList.aClass68_49;
			synchronized (this.npcTypeList.aClass68_49) {
				this.npcTypeList.aClass68_49.method1779((long) (arg7.index << 16 | this.id), local600);
			}
		}
		@Pc(1388) Model local1388 = local600.copy((byte) 4, local37, true);
		if (!local63) {
			return local1388;
		}
		@Pc(1402) int local1402 = 0;
		@Pc(1404) int local1404 = 1;
		while (local78 > local1402) {
			if (Class25.aClass4_Sub1_Sub17Array3[local1402] != null) {
				local1388.method4897(Huffman.anIntArray233[local1402], Class9.aClass4_Sub1_Sub17Array14[local1402], this.anIntArrayArray63[local1402], Static398.aClass138Array3[local1402].lights, FixedFunctionSpecularPass.anIntArray378[local1402], Class208.anIntArray419[local1402], Class25.aClass4_Sub1_Sub17Array3[local1402], Class56.anIntArray137[local1402] - 1, local1404);
			}
			local1402++;
			local1404 <<= 0x1;
		}
		if (local294 != null && local304 != null) {
			local1388.method4899(local288, local117, local306, arg11.lights | arg1.lights, local302, arg0 - 1, local298, local304, local294, local300, arg1.interLeaveOrder, local296, arg5 - 1, local112);
		} else if (local294 != null) {
			local1388.method4883(0, local117, arg1.lights, local288, local112, local294, local296, arg5 - 1);
		} else if (local304 != null) {
			local1388.method4883(0, local302, arg11.lights, local298, local300, local304, local306, arg0 - 1);
		}
		for (local633 = 0; local633 < local78; local633++) {
			Class25.aClass4_Sub1_Sub17Array3[local633] = null;
			Class9.aClass4_Sub1_Sub17Array14[local633] = null;
			Static398.aClass138Array3[local633] = null;
		}
		if (this.anInt7220 != 128 || this.anInt7224 != 128) {
			local1388.H(this.anInt7220, this.anInt7224, this.anInt7220);
		}
		local1388.UA(arg4);
		return local1388;
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(Z)V")
	public void method5576() {
		if (this.modelIds == null) {
			this.modelIds = new int[0];
		}
	}

	@OriginalMember(owner = "client!wh", name = "b", descriptor = "(I)Z")
	public boolean method5578() {
		if (this.anIntArray544 == null) {
			return this.anInt7210 != -1 || this.anInt7202 != -1 || this.anInt7212 != -1;
		}
		for (@Pc(34) int local34 = 0; local34 < this.anIntArray544.length; local34++) {
			if (this.anIntArray544[local34] != -1) {
				@Pc(52) NPCType local52 = this.npcTypeList.method5229(this.anIntArray544[local34]);
				if (local52.anInt7210 != -1 || local52.anInt7202 != -1 || local52.anInt7212 != -1) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(BLjava/lang/String;I)Ljava/lang/String;")
	public String method5579(@OriginalArg(1) String arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_41 == null) {
			return arg0;
		} else {
			@Pc(25) Class4_Sub7 local25 = (Class4_Sub7) this.aClass102_41.method2700((long) arg1);
			return local25 == null ? arg0 : local25.aString12;
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(Lclient!iv;II)V")
	private void decode(@OriginalArg(0) Packet packet, @OriginalArg(1) int opcode) {
		@Pc(31) int local31;
		@Pc(41) int local41;
		if (opcode == 1) {
			local31 = packet.method2490();
			this.modelIds = new int[local31];
			for (local41 = 0; local41 < local31; local41++) {
				this.modelIds[local41] = packet.method2536();
				if (this.modelIds[local41] == 65535) {
					this.modelIds[local41] = -1;
				}
			}
		} else if (opcode == 2) {
			this.aString70 = packet.method2537();
		} else if (opcode == 12) {
			this.anInt7201 = packet.method2490();
		} else if (opcode >= 30 && opcode < 35) {
			this.actions[opcode - 30] = packet.method2537();
		} else if (opcode == 40) {
			local31 = packet.method2490();
			this.aShortArray248 = new short[local31];
			this.aShortArray246 = new short[local31];
			for (local41 = 0; local41 < local31; local41++) {
				this.aShortArray248[local41] = (short) packet.method2536();
				this.aShortArray246[local41] = (short) packet.method2536();
			}
		} else if (opcode == 41) {
			local31 = packet.method2490();
			this.aShortArray245 = new short[local31];
			this.aShortArray247 = new short[local31];
			for (local41 = 0; local41 < local31; local41++) {
				this.aShortArray247[local41] = (short) packet.method2536();
				this.aShortArray245[local41] = (short) packet.method2536();
			}
		} else if (opcode == 42) {
			local31 = packet.method2490();
			this.recolorDPalette = new byte[local31];
			for (local41 = 0; local41 < local31; local41++) {
				this.recolorDPalette[local41] = packet.method2542();
			}
		} else if (opcode == 60) {
			local31 = packet.method2490();
			this.headModels = new int[local31];
			for (local41 = 0; local41 < local31; local41++) {
				this.headModels[local41] = packet.method2536();
			}
		} else if (opcode == 93) {
			this.aBoolean679 = false;
		} else if (opcode == 95) {
			this.lb = packet.method2536();
		} else if (opcode == 97) {
			this.anInt7220 = packet.method2536();
		} else if (opcode == 98) {
			this.anInt7224 = packet.method2536();
		} else if (opcode == 99) {
			this.highPriority = true;
		} else if (opcode == 100) {
			this.anInt7228 = packet.method2542();
		} else if (opcode == 101) {
			this.anInt7232 = packet.method2542() * 5;
		} else if (opcode == 102) {
			this.anInt7205 = packet.method2536();
		} else if (opcode == 103) {
			this.anInt7221 = packet.method2536();
		} else {
			@Pc(284) int local284;
			if (opcode == 106 || opcode == 118) {
				this.anInt7213 = packet.method2536();
				if (this.anInt7213 == 65535) {
					this.anInt7213 = -1;
				}
				this.anInt7231 = packet.method2536();
				if (this.anInt7231 == 65535) {
					this.anInt7231 = -1;
				}
				local31 = -1;
				if (opcode == 118) {
					local31 = packet.method2536();
					if (local31 == 65535) {
						local31 = -1;
					}
				}
				local41 = packet.method2490();
				this.anIntArray544 = new int[local41 + 2];
				for (local284 = 0; local284 <= local41; local284++) {
					this.anIntArray544[local284] = packet.method2536();
					if (this.anIntArray544[local284] == 65535) {
						this.anIntArray544[local284] = -1;
					}
				}
				this.anIntArray544[local41 + 1] = local31;
			} else if (opcode == 107) {
				this.aBoolean682 = false;
			} else if (opcode == 109) {
				this.aBoolean683 = false;
			} else if (opcode == 111) {
				this.shadowed = false;
			} else if (opcode == 113) {
				this.aShort102 = (short) packet.method2536();
				this.aShort101 = (short) packet.method2536();
			} else if (opcode == 114) {
				this.aByte98 = packet.method2542();
				this.aByte100 = packet.method2542();
			} else if (opcode == 115) {
				packet.method2490();
				packet.method2490();
			} else if (opcode == 119) {
				this.aByte99 = packet.method2542();
			} else if (opcode == 121) {
				this.modelOffsets = new int[this.modelIds.length][];
				local31 = packet.method2490();
				for (local41 = 0; local41 < local31; local41++) {
					local284 = packet.method2490();
					@Pc(292) int[] local292 = this.modelOffsets[local284] = new int[3];
					local292[0] = packet.method2542();
					local292[1] = packet.method2542();
					local292[2] = packet.method2542();
				}
			} else if (opcode == 122) {
				this.anInt7204 = packet.method2536();
			} else if (opcode == 123) {
				this.iconHeight = packet.method2536();
			} else if (opcode == 125) {
				this.aByte101 = packet.method2542();
			} else if (opcode == 127) {
				this.anInt7241 = packet.method2536();
			} else if (opcode == 128) {
				packet.method2490();
			} else if (opcode == 134) {
				this.anInt7210 = packet.method2536();
				if (this.anInt7210 == 65535) {
					this.anInt7210 = -1;
				}
				this.anInt7207 = packet.method2536();
				if (this.anInt7207 == 65535) {
					this.anInt7207 = -1;
				}
				this.anInt7202 = packet.method2536();
				if (this.anInt7202 == 65535) {
					this.anInt7202 = -1;
				}
				this.anInt7212 = packet.method2536();
				if (this.anInt7212 == 65535) {
					this.anInt7212 = -1;
				}
				this.anInt7238 = packet.method2490();
			} else if (opcode == 135) {
				this.anInt7206 = packet.method2490();
				this.anInt7237 = packet.method2536();
			} else if (opcode == 136) {
				this.anInt7219 = packet.method2490();
				this.anInt7229 = packet.method2536();
			} else if (opcode == 137) {
				this.anInt7214 = packet.method2536();
			} else if (opcode == 138) {
				this.overheadSprite = packet.method2536();
			} else if (opcode == 139) {
				this.anInt7218 = packet.method2536();
			} else if (opcode == 140) {
				this.anInt7235 = packet.method2490();
			} else if (opcode == 141) {
				this.aBoolean685 = true;
			} else if (opcode == 142) {
				this.anInt7239 = packet.method2536();
			} else if (opcode == 143) {
				this.aBoolean680 = true;
			} else if (opcode >= 150 && opcode < 155) {
				this.actions[opcode - 150] = packet.method2537();
				if (!this.npcTypeList.allowMembers) {
					this.actions[opcode - 150] = null;
				}
			} else if (opcode == 155) {
				this.aByte96 = packet.method2542();
				this.aByte97 = packet.method2542();
				this.aByte102 = packet.method2542();
				this.aByte95 = packet.method2542();
			} else if (opcode == 160) {
				local31 = packet.method2490();
				this.anIntArray547 = new int[local31];
				for (local41 = 0; local41 < local31; local41++) {
					this.anIntArray547[local41] = packet.method2536();
				}
			} else if (opcode == 249) {
				local31 = packet.method2490();
				if (this.aClass102_41 == null) {
					local41 = SpriteDrawQueue.method4949(local31);
					this.aClass102_41 = new CubeMapSpecularEffect(local41);
				}
				for (local41 = 0; local41 < local31; local41++) {
					@Pc(485) boolean local485 = packet.method2490() == 1;
					@Pc(489) int local489 = packet.method2493();
					@Pc(498) Country local498;
					if (local485) {
						local498 = new Class4_Sub7(packet.method2537());
					} else {
						local498 = new Class4_Sub37(packet.method2529());
					}
					this.aClass102_41.method2703((long) local489, local498);
				}
			}
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(ILclient!iv;)V")
	public void method5581(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(16) int local16 = arg0.method2490();
			if (local16 == 0) {
				return;
			}
			this.decode(arg0, local16);
		}
	}

	@OriginalMember(owner = "client!wh", name = "b", descriptor = "(III)I")
	public int method5582(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (this.aClass102_41 == null) {
			return arg0;
		} else {
			@Pc(22) Class4_Sub37 local22 = (Class4_Sub37) this.aClass102_41.method2700((long) arg1);
			return local22 == null ? arg0 : local22.anInt5426;
		}
	}

	@OriginalMember(owner = "client!wh", name = "a", descriptor = "(IIIILclient!lp;ILclient!vs;Lclient!ya;Lclient!du;)Lclient!t;")
	public Model method5584(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(4) SeqType arg2, @OriginalArg(5) int arg3, @OriginalArg(6) VarDomain arg4, @OriginalArg(7) com.jagex.game.runetek5.graphics.Toolkit arg5, @OriginalArg(8) SeqTypeList arg6) {
		if (this.anIntArray544 != null) {
			@Pc(19) NPCType local19 = this.method5574(arg4);
			return local19 == null ? null : local19.method5584(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		} else if (this.headModels == null) {
			return null;
		} else {
			@Pc(42) int local42 = 2048;
			if (arg2 != null && arg3 != -1) {
				local42 = arg2.getFunctionMask(arg3, arg0, true) | 0x800;
			}
			@Pc(61) LRUCache local61 = this.npcTypeList.aClass68_50;
			@Pc(78) Model local78;
			synchronized (this.npcTypeList.aClass68_50) {
				local78 = (Model) this.npcTypeList.aClass68_50.method1787((long) (this.id | arg5.index << 16));
			}
			if (local78 == null || (local42 & local78.P()) != local42) {
				if (local78 != null) {
					local42 |= local78.P();
				}
				@Pc(105) int local105 = local42;
				@Pc(107) boolean local107 = false;
				@Pc(111) Js5ResourceProvider local111 = this.npcTypeList.meshes;
				synchronized (this.npcTypeList.meshes) {
					@Pc(115) int local115 = 0;
					while (true) {
						if (local115 >= this.headModels.length) {
							break;
						}
						if (!this.npcTypeList.meshes.method2098(0, this.headModels[local115])) {
							local107 = true;
						}
						local115++;
					}
				}
				if (local107) {
					return null;
				}
				@Pc(151) Mesh[] local151 = new Mesh[this.headModels.length];
				@Pc(155) Js5ResourceProvider local155 = this.npcTypeList.meshes;
				@Pc(159) int local159;
				synchronized (this.npcTypeList.meshes) {
					for (local159 = 0; local159 < this.headModels.length; local159++) {
						local151[local159] = SpriteDrawQueue.method4948(this.npcTypeList.meshes, this.headModels[local159]);
					}
				}
				for (@Pc(192) int local192 = 0; local192 < this.headModels.length; local192++) {
					if (local151[local192] != null && local151[local192].version < 13) {
						local151[local192].method3039();
					}
				}
				@Pc(231) Mesh local231;
				if (local151.length == 1) {
					local231 = local151[0];
				} else {
					local231 = new Mesh(local151, local151.length);
				}
				if (this.aShortArray248 != null) {
					local105 = local42 | 0x4000;
				}
				if (this.aShortArray247 != null) {
					local105 |= 0x8000;
				}
				if (this.aByte95 != 0) {
					local105 |= 0x80000;
				}
				local78 = arg5.createModel(local231, local105, this.npcTypeList.featureMask, 64, 768);
				if (this.aShortArray248 != null) {
					for (local159 = 0; local159 < this.aShortArray248.length; local159++) {
						if (this.recolorDPalette == null || local159 >= this.recolorDPalette.length) {
							local78.copyAlpha(this.aShortArray248[local159], this.aShortArray246[local159]);
						} else {
							local78.copyAlpha(this.aShortArray248[local159], Static78.aShortArray46[this.recolorDPalette[local159] & 0xFF]);
						}
					}
				}
				if (this.aShortArray247 != null) {
					for (local159 = 0; local159 < this.aShortArray247.length; local159++) {
						local78.I(this.aShortArray247[local159], this.aShortArray245[local159]);
					}
				}
				if (this.aByte95 != 0) {
					local78.adjustColours(this.aByte96, this.aByte97, this.aByte102, this.aByte95 & 0xFF);
				}
				local78.UA(local42);
				@Pc(377) LRUCache local377 = this.npcTypeList.aClass68_50;
				synchronized (this.npcTypeList.aClass68_50) {
					this.npcTypeList.aClass68_50.method1779((long) (this.id | arg5.index << 16), local78);
				}
			}
			if (arg2 != null && arg3 != -1) {
				local78 = arg2.method3456(arg1, local42, arg0, arg3, local78);
			}
			local78.UA(2048);
			return local78;
		}
	}

@OriginalMember(owner = "client!wh", name = "G", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_58 = new LRUCache(260);
@OriginalMember(owner = "client!wh", name = "nb", descriptor = "J")
	public static long aLong219 = 0L;
@OriginalMember(owner = "client!wh", name = "wb", descriptor = "I")
	public static int chatMessageHashIndex = 0;
@OriginalMember(owner = "client!wh", name = "a", descriptor = "(III)V")
	public static void method5573(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(0, 15);
		local8.method2604();
		local8.anInt3082 = arg1;
		local8.anInt3083 = arg0;
	}
@OriginalMember(owner = "client!wh", name = "a", descriptor = "(Z[B)Z")
	public static boolean method5583(@OriginalArg(1) byte[] arg0) {
		@Pc(20) Packet local20 = new Packet(arg0);
		@Pc(24) int local24 = local20.method2490();
		if (local24 != 1) {
			return false;
		}
		@Pc(38) boolean local38 = local20.method2490() == 1;
		if (local38) {
			Class92.method2425(local20);
		}
		Static340.method4615(local20);
		return true;
	}
@OriginalMember(owner = "client!wh", name = "a", descriptor = "(IZLclient!l;Lclient!uu;Lclient!ma;III)V")
	public static void method5585(@OriginalArg(0) int arg0, @OriginalArg(2) NativeSprite arg1, @OriginalArg(3) com.jagex.game.runetek5.config.rsinterface.Component arg2, @OriginalArg(4) ComponentSurface arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		if (arg1 == null) {
			return;
		}
		@Pc(21) int local21;
		if (Class3_Sub3.anInt2386 == 4) {
			local21 = (int) Static368.aFloat192 & 0x3FFF;
		} else {
			local21 = PlayerVarDomain.anInt3399 + (int) Static368.aFloat192 & 0x3FFF;
		}
		@Pc(40) int local40 = Math.max(arg2.anInt6833 / 2, arg2.anInt6805 / 2) + 10;
		@Pc(48) int local48 = arg5 * arg5 + arg4 * arg4;
		if (local48 > local40 * local40) {
			return;
		}
		@Pc(62) int local62 = Class50_Sub1.anIntArray225[local21];
		@Pc(66) int local66 = Class50_Sub1.anIntArray224[local21];
		if (Class3_Sub3.anInt2386 != 4) {
			local62 = local62 * 256 / (Class4_Sub17.anInt2374 + 256);
			local66 = local66 * 256 / (Class4_Sub17.anInt2374 + 256);
		}
		@Pc(97) int local97 = arg4 * local62 + arg5 * local66 >> 15;
		@Pc(108) int local108 = local66 * arg4 - local62 * arg5 >> 15;
		arg1.drawWithSurface(local97 + arg2.anInt6833 / 2 + arg0 - arg1.scaleWidth() / 2, -local108 + arg2.anInt6805 / 2 + arg6 + -(arg1.scaleHeight() / 2), arg3, arg0, arg6);
	}
@OriginalMember(owner = "client!wh", name = "a", descriptor = "(ILclient!fs;Lclient!fs;Lclient!fs;Lclient!fs;)V")
	public static void method5586(@OriginalArg(1) Js5ResourceProvider arg0, @OriginalArg(2) Js5ResourceProvider arg1, @OriginalArg(3) Js5ResourceProvider arg2, @OriginalArg(4) Js5ResourceProvider arg3) {
		LegacyMouseManager.aClass76_81 = arg0;
		Static410.aClass76_95 = arg2;
		Class38.aClass76_15 = arg1;
		ParamTypeList.aClass76_89 = arg3;
		Class4_Sub36.aClass247ArrayArray2 = new com.jagex.game.runetek5.config.rsinterface.Component[LegacyMouseManager.aClass76_81.method2100()][];
		TileMessage.aBooleanArray25 = new boolean[LegacyMouseManager.aClass76_81.method2100()];
	}
}
