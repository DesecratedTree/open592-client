package com.jagex.game.runetek5.config.item;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.ia;
import com.jagex.game.runetek5.deob.ka;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.config.item.*;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
@OriginalClass("client!rv")
public final class ItemType {

	@OriginalMember(owner = "client!rv", name = "d", descriptor = "[B")
	private byte[] recolorDPalette;

	@OriginalMember(owner = "client!rv", name = "g", descriptor = "[S")
	private short[] aShortArray211;

	@OriginalMember(owner = "client!rv", name = "o", descriptor = "[I")
	public int[] quests;

	@OriginalMember(owner = "client!rv", name = "v", descriptor = "[Ljava/lang/String;")
	public String[] aStringArray33;

	@OriginalMember(owner = "client!rv", name = "y", descriptor = "[I")
	public int[] stackIds;

	@OriginalMember(owner = "client!rv", name = "A", descriptor = "[S")
	private short[] aShortArray212;

	@OriginalMember(owner = "client!rv", name = "M", descriptor = "[Ljava/lang/String;")
	public String[] aStringArray34;

	@OriginalMember(owner = "client!rv", name = "N", descriptor = "[S")
	private short[] aShortArray213;

	@OriginalMember(owner = "client!rv", name = "gb", descriptor = "[I")
	public int[] stackTriggerAmount;

	@OriginalMember(owner = "client!rv", name = "ib", descriptor = "[S")
	private short[] aShortArray214;

	@OriginalMember(owner = "client!rv", name = "ob", descriptor = "Lclient!ro;")
	public ObjTypeList typeList;

	@OriginalMember(owner = "client!rv", name = "pb", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!rv", name = "wb", descriptor = "I")
	private int anInt6178;

	@OriginalMember(owner = "client!rv", name = "xb", descriptor = "Lclient!ib;")
	public CubeMapSpecularEffect aClass102_36;

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "I")
	public int anInt6122 = 0;

	@OriginalMember(owner = "client!rv", name = "f", descriptor = "I")
	private int anInt6125 = -1;

	@OriginalMember(owner = "client!rv", name = "t", descriptor = "I")
	private int anInt6134 = 0;

	@OriginalMember(owner = "client!rv", name = "x", descriptor = "I")
	private int anInt6136 = 0;

	@OriginalMember(owner = "client!rv", name = "h", descriptor = "I")
	public int anInt6126 = -1;

	@OriginalMember(owner = "client!rv", name = "z", descriptor = "I")
	public int anInt6137 = 2000;

	@OriginalMember(owner = "client!rv", name = "G", descriptor = "I")
	public int anInt6143 = -1;

	@OriginalMember(owner = "client!rv", name = "n", descriptor = "I")
	private int anInt6130 = 0;

	@OriginalMember(owner = "client!rv", name = "w", descriptor = "I")
	public int anInt6135 = 0;

	@OriginalMember(owner = "client!rv", name = "q", descriptor = "Z")
	public boolean stockmarket = false;

	@OriginalMember(owner = "client!rv", name = "I", descriptor = "I")
	private int anInt6145 = 0;

	@OriginalMember(owner = "client!rv", name = "K", descriptor = "Z")
	public boolean membersOnly = false;

	@OriginalMember(owner = "client!rv", name = "s", descriptor = "I")
	public int anInt6133 = 0;

	@OriginalMember(owner = "client!rv", name = "B", descriptor = "I")
	public int anInt6138 = -1;

	@OriginalMember(owner = "client!rv", name = "X", descriptor = "I")
	public int anInt6157 = -1;

	@OriginalMember(owner = "client!rv", name = "T", descriptor = "I")
	public int anInt6153 = -1;

	@OriginalMember(owner = "client!rv", name = "ab", descriptor = "I")
	public int anInt6160 = -1;

	@OriginalMember(owner = "client!rv", name = "Q", descriptor = "I")
	private int anInt6150 = -1;

	@OriginalMember(owner = "client!rv", name = "R", descriptor = "I")
	public int anInt6151 = 0;

	@OriginalMember(owner = "client!rv", name = "fb", descriptor = "I")
	public int anInt6165 = 1;

	@OriginalMember(owner = "client!rv", name = "lb", descriptor = "I")
	public int lb = 0;

	@OriginalMember(owner = "client!rv", name = "W", descriptor = "I")
	public int anInt6156 = -1;

	@OriginalMember(owner = "client!rv", name = "H", descriptor = "I")
	private int anInt6144 = 128;

	@OriginalMember(owner = "client!rv", name = "bb", descriptor = "I")
	private int anInt6161 = -1;

	@OriginalMember(owner = "client!rv", name = "nb", descriptor = "I")
	private int anInt6170 = -1;

	@OriginalMember(owner = "client!rv", name = "e", descriptor = "I")
	public int anInt6124 = -1;

	@OriginalMember(owner = "client!rv", name = "J", descriptor = "I")
	private int anInt6146 = 0;

	@OriginalMember(owner = "client!rv", name = "u", descriptor = "Ljava/lang/String;")
	public String name = "null";

	@OriginalMember(owner = "client!rv", name = "D", descriptor = "I")
	private int anInt6140 = 0;

	@OriginalMember(owner = "client!rv", name = "V", descriptor = "I")
	public int anInt6155 = -1;

	@OriginalMember(owner = "client!rv", name = "kb", descriptor = "I")
	private int anInt6168 = -1;

	@OriginalMember(owner = "client!rv", name = "tb", descriptor = "I")
	public int anInt6175 = -1;

	@OriginalMember(owner = "client!rv", name = "eb", descriptor = "I")
	public int anInt6164 = 0;

	@OriginalMember(owner = "client!rv", name = "ub", descriptor = "I")
	private int anInt6176 = -1;

	@OriginalMember(owner = "client!rv", name = "Z", descriptor = "I")
	private int anInt6159 = -1;

	@OriginalMember(owner = "client!rv", name = "O", descriptor = "I")
	private int anInt6148 = 128;

	@OriginalMember(owner = "client!rv", name = "S", descriptor = "I")
	public int anInt6152 = 0;

	@OriginalMember(owner = "client!rv", name = "i", descriptor = "I")
	public int anInt6127 = -1;

	@OriginalMember(owner = "client!rv", name = "sb", descriptor = "I")
	private int anInt6174 = 0;

	@OriginalMember(owner = "client!rv", name = "L", descriptor = "I")
	private int anInt6147 = -1;

	@OriginalMember(owner = "client!rv", name = "cb", descriptor = "I")
	public int anInt6162 = -1;

	@OriginalMember(owner = "client!rv", name = "Db", descriptor = "I")
	public int anInt6184 = -1;

	@OriginalMember(owner = "client!rv", name = "Ab", descriptor = "I")
	public int anInt6181 = 0;

	@OriginalMember(owner = "client!rv", name = "r", descriptor = "I")
	private int anInt6132 = 0;

	@OriginalMember(owner = "client!rv", name = "Eb", descriptor = "I")
	public int anInt6185 = -1;

	@OriginalMember(owner = "client!rv", name = "zb", descriptor = "I")
	private int anInt6180 = 128;

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(IILclient!lp;IILclient!cj;IILclient!ya;)Lclient!t;")
	public Model method4773(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) SeqType arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) PlayerAppearance arg5, @OriginalArg(6) int arg6, @OriginalArg(8) com.jagex.game.runetek5.graphics.Toolkit arg7) {
		@Pc(18) int local18;
		if (this.stackIds != null && arg1 > 1) {
			local18 = -1;
			for (@Pc(20) int local20 = 0; local20 < 10; local20++) {
				if (arg1 >= this.stackTriggerAmount[local20] && this.stackTriggerAmount[local20] != 0) {
					local18 = this.stackIds[local20];
				}
			}
			if (local18 != -1) {
				return this.typeList.method4703(local18).method4773(arg0, 1, arg2, arg3, arg4, arg5, arg6, arg7);
			}
		}
		local18 = arg3;
		if (arg2 != null) {
			local18 = arg3 | arg2.getFunctionMask(arg6, arg4, true);
		}
		@Pc(87) LRUCache local87 = this.typeList.modelCache;
		@Pc(104) Model local104;
		synchronized (this.typeList.modelCache) {
			local104 = (Model) this.typeList.modelCache.method1787((long) (this.id | arg7.index << 29));
		}
		if (local104 == null || arg7.compareFunctionMasks(local104.P(), local18) != 0) {
			if (local104 != null) {
				local18 = arg7.combineFunctionMasks(local18, local104.P());
			}
			@Pc(128) int local128 = local18;
			if (this.aShortArray213 != null) {
				local128 = local18 | 0x8000;
			}
			if (this.aShortArray214 != null || arg5 != null) {
				local128 |= 0x4000;
			}
			if (this.anInt6180 != 128) {
				local128 |= 0x1;
			}
			if (this.anInt6180 != 128) {
				local128 |= 0x2;
			}
			if (this.anInt6180 != 128) {
				local128 |= 0x4;
			}
			@Pc(177) Mesh local177 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, this.anInt6178);
			if (local177 == null) {
				return null;
			}
			if (local177.version < 13) {
				local177.method3039();
			}
			local104 = arg7.createModel(local177, local128, this.typeList.featureMask, this.anInt6130 + 64, 850 - -this.anInt6146);
			if (this.anInt6180 != 128 || this.anInt6144 != 128 || this.anInt6148 != 128) {
				local104.H(this.anInt6180, this.anInt6144, this.anInt6148);
			}
			@Pc(235) int local235;
			if (this.aShortArray214 != null) {
				for (local235 = 0; local235 < this.aShortArray214.length; local235++) {
					if (this.recolorDPalette == null || local235 >= this.recolorDPalette.length) {
						local104.copyAlpha(this.aShortArray214[local235], this.aShortArray211[local235]);
					} else {
						local104.copyAlpha(this.aShortArray214[local235], Static166.aShortArray97[this.recolorDPalette[local235] & 0xFF]);
					}
				}
			}
			if (this.aShortArray213 != null) {
				for (local235 = 0; local235 < this.aShortArray213.length; local235++) {
					local104.I(this.aShortArray213[local235], this.aShortArray212[local235]);
				}
			}
			if (arg5 != null) {
				for (local235 = 0; local235 < 5; local235++) {
					if (arg5.anIntArray52[local235] < KeyRecordEntry.aShortArrayArray2[local235].length) {
						local104.copyAlpha(Class177.aShortArray179[local235], KeyRecordEntry.aShortArrayArray2[local235][arg5.anIntArray52[local235]]);
					}
					if (arg5.anIntArray52[local235] < Class4_Sub1_Sub2.aShortArrayArray1[local235].length) {
						local104.copyAlpha(Country.aShortArray252[local235], Class4_Sub1_Sub2.aShortArrayArray1[local235][arg5.anIntArray52[local235]]);
					}
				}
			}
			local104.UA(local18);
			@Pc(365) LRUCache local365 = this.typeList.modelCache;
			synchronized (this.typeList.modelCache) {
				this.typeList.modelCache.method1779((long) (arg7.index << 29 | this.id), local104);
			}
		}
		if (arg2 != null) {
			local104 = arg2.method3456(arg0, local18, arg4, arg6, local104);
		}
		local104.UA(arg3);
		return local104;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(Lclient!cj;ZLclient!la;IIIILclient!ya;ILclient!ya;)[I")
	public int[] method4774(@OriginalArg(0) PlayerAppearance arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) AbstractFontRenderer arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) com.jagex.game.runetek5.graphics.Toolkit arg6, @OriginalArg(8) int arg7, @OriginalArg(9) com.jagex.game.runetek5.graphics.Toolkit arg8) {
		@Pc(14) Mesh local14 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, this.anInt6178);
		if (local14 == null) {
			return null;
		}
		if (local14.version < 13) {
			local14.method3039();
		}
		@Pc(31) int local31;
		if (this.aShortArray214 != null) {
			for (local31 = 0; local31 < this.aShortArray214.length; local31++) {
				if (this.recolorDPalette == null || this.recolorDPalette.length <= local31) {
					local14.method3055(this.aShortArray214[local31], this.aShortArray211[local31]);
				} else {
					local14.method3055(this.aShortArray214[local31], Static166.aShortArray97[this.recolorDPalette[local31] & 0xFF]);
				}
			}
		}
		if (this.aShortArray213 != null) {
			for (local31 = 0; local31 < this.aShortArray213.length; local31++) {
				local14.method3038(this.aShortArray212[local31], this.aShortArray213[local31]);
			}
		}
		if (arg0 != null) {
			for (local31 = 0; local31 < 5; local31++) {
				if (arg0.anIntArray52[local31] < KeyRecordEntry.aShortArrayArray2[local31].length) {
					local14.method3055(Class177.aShortArray179[local31], KeyRecordEntry.aShortArrayArray2[local31][arg0.anIntArray52[local31]]);
				}
				if (arg0.anIntArray52[local31] < Class4_Sub1_Sub2.aShortArrayArray1[local31].length) {
					local14.method3055(Country.aShortArray252[local31], Class4_Sub1_Sub2.aShortArrayArray1[local31][arg0.anIntArray52[local31]]);
				}
			}
		}
		@Pc(173) short local173 = 2048;
		@Pc(175) boolean local175 = false;
		if (this.anInt6180 != 128 || this.anInt6144 != 128 || this.anInt6148 != 128) {
			local175 = true;
			local173 = 2055;
		}
		@Pc(211) Model local211 = arg8.createModel(local14, local173, 0, this.anInt6130 + 64, this.anInt6146 + 768);
		if (!local211.loadedTextures()) {
			return null;
		}
		if (local175) {
			local211.H(this.anInt6180, this.anInt6144, this.anInt6148);
		}
		@Pc(228) NativeSprite local228 = null;
		if (this.anInt6175 != -1) {
			local228 = this.typeList.method4699(true, arg0, arg2, this.anInt6162, 0, arg8, true, 10, 0, 1, arg6);
			if (local228 == null) {
				return null;
			}
		} else if (this.anInt6185 != -1) {
			local228 = this.typeList.method4699(false, arg0, arg2, this.anInt6138, 0, arg8, true, arg3, arg7, arg4, arg6);
			if (local228 == null) {
				return null;
			}
		}
		@Pc(290) int local290;
		if (arg1) {
			local290 = (int) ((double) this.anInt6137 * 1.5D) << 0;
		} else if (arg4 == 2) {
			local290 = (int) ((double) this.anInt6137 * 1.04D) << 0;
		} else {
			local290 = this.anInt6137 << 0;
		}
		arg8.H(16, 16, 512, 512);
		@Pc(321) Ground local321 = arg8.method4274();
		local321.xa();
		arg8.ea(local321);
		arg8.B(1.0F);
		arg8.scaleHeight(16777215, 1.0F, 1.0F, -50.0F, -10.0F, -50.0F);
		@Pc(340) Ground local340 = arg8.method4289();
		local340.O(-this.anInt6135 << 3);
		local340.ja(this.anInt6122 << 3);
		local340.o(this.anInt6152, (Class50_Sub1.anIntArray225[this.anInt6133 << 3] * local290 >> 15) + this.lb - local211.setOffsets() / 2, (local290 * Class50_Sub1.anIntArray224[this.anInt6133 << 3] >> 15) + this.lb);
		local340.ka(this.anInt6133 << 3);
		@Pc(398) int local398 = arg8.a();
		@Pc(401) int local401 = arg8.YA();
		arg8.ia(50, Integer.MAX_VALUE);
		arg8.method4266();
		arg8.e();
		arg8.O(0, 0, 36, 32, 0, 0);
		local211.render(local340, null, 1);
		arg8.ia(local398, local401);
		@Pc(433) int[] local433 = arg8.F(0, 0, 36, 32);
		if (arg4 >= 1) {
			local433 = this.method4775(local433, -16777214);
			if (arg4 >= 2) {
				local433 = this.method4775(local433, -1);
			}
		}
		if (arg7 != 0) {
			this.method4782(arg7, local433);
		}
		arg8.method4265(local433, 36, 36, 32).render(0, 0);
		if (this.anInt6175 != -1) {
			local228.render(0, 0);
		} else if (this.anInt6185 != -1) {
			local228.render(0, 0);
		}
		if (arg5 == 1 || arg5 == 2 && (this.anInt6164 == 1 || arg3 != 1) && arg3 != -1) {
			arg2.method5058(-16777215, -256, this.formatAmount(arg3), 9, 0);
		}
		local433 = arg8.F(0, 0, 36, 32);
		for (@Pc(537) int local537 = 0; local537 < local433.length; local537++) {
			if ((local433[local537] & 0xFFFFFF) == 0) {
				local433[local537] = 0;
			} else {
				local433[local537] |= 0xFF000000;
			}
		}
		return local433;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "([III)[I")
	private int[] method4775(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1) {
		@Pc(8) int[] local8 = new int[1152];
		@Pc(16) int local16 = 0;
		for (@Pc(18) int local18 = 0; local18 < 32; local18++) {
			for (@Pc(22) int local22 = 0; local22 < 36; local22++) {
				@Pc(28) int local28 = arg0[local16];
				if (local28 == 0) {
					if (local22 > 0 && arg0[local16 - 1] != 0) {
						local28 = arg1;
					} else if (local18 > 0 && arg0[local16 - 36] != 0) {
						local28 = arg1;
					} else if (local22 < 35 && arg0[local16 + 1] != 0) {
						local28 = arg1;
					} else if (local18 < 31 && arg0[local16 + 36] != 0) {
						local28 = arg1;
					}
				}
				local8[local16++] = local28;
			}
		}
		return local8;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(IZ)Z")
	public boolean method4776(@OriginalArg(1) boolean arg0) {
		@Pc(11) int local11 = this.anInt6168;
		@Pc(14) int local14 = this.anInt6125;
		if (arg0) {
			local11 = this.anInt6147;
			local14 = this.anInt6161;
		}
		if (local11 == -1) {
			return true;
		}
		@Pc(29) boolean local29 = true;
		if (!this.typeList.aClass76_77.method2098(0, local11)) {
			local29 = false;
		}
		if (local14 != -1 && !this.typeList.aClass76_77.method2098(0, local14)) {
			local29 = false;
		}
		return local29;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(II)Ljava/lang/String;")
	private String formatAmount(@OriginalArg(0) int amount) {
		if (amount < 100000) {
			return "<col=ffff00>" + amount + "</col>";
		} else if (amount < 10000000) {
			return "<col=ffffff>" + amount / 1000 + Huffman.aClass83_74.method2267(this.typeList.languageId) + "</col>";
		} else {
			return "<col=00ff80>" + amount / 1000000 + NativeBillboardFace.aClass83_72.method2267(this.typeList.languageId) + "</col>";
		}
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(Lclient!rv;Lclient!rv;Z)V")
	public void method4779(@OriginalArg(0) ItemType arg0, @OriginalArg(1) ItemType arg1) {
		this.anInt6161 = arg0.anInt6161;
		this.aShortArray213 = arg0.aShortArray213;
		this.anInt6150 = arg0.anInt6150;
		this.anInt6132 = arg0.anInt6132;
		this.anInt6145 = arg0.anInt6145;
		this.anInt6170 = arg0.anInt6170;
		this.anInt6165 = 0;
		this.aStringArray34 = arg0.aStringArray34;
		this.anInt6125 = arg0.anInt6125;
		this.name = arg0.name;
		this.anInt6137 = arg1.anInt6137;
		this.anInt6133 = arg1.anInt6133;
		this.anInt6147 = arg0.anInt6147;
		this.aShortArray214 = arg0.aShortArray214;
		this.anInt6135 = arg1.anInt6135;
		this.anInt6176 = arg0.anInt6176;
		this.anInt6136 = arg0.anInt6136;
		this.anInt6174 = arg0.anInt6174;
		this.aShortArray211 = arg0.aShortArray211;
		this.anInt6152 = arg1.anInt6152;
		this.anInt6168 = arg0.anInt6168;
		this.anInt6156 = arg0.anInt6156;
		this.lb = arg1.lb;
		this.recolorDPalette = arg0.recolorDPalette;
		this.anInt6181 = arg0.anInt6181;
		this.aShortArray212 = arg0.aShortArray212;
		this.aClass102_36 = arg0.aClass102_36;
		this.membersOnly = arg0.membersOnly;
		this.anInt6122 = arg1.anInt6122;
		this.anInt6134 = arg0.anInt6134;
		this.anInt6140 = arg0.anInt6140;
		this.anInt6157 = arg0.anInt6157;
		this.anInt6178 = arg1.anInt6178;
		this.anInt6159 = arg0.anInt6159;
		this.aStringArray33 = new String[5];
		if (arg0.aStringArray33 != null) {
			for (@Pc(149) int local149 = 0; local149 < 4; local149++) {
				this.aStringArray33[local149] = arg0.aStringArray33[local149];
			}
		}
		this.aStringArray33[4] = Class28.aClass83_19.method2267(this.typeList.languageId);
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(ILclient!iv;I)V")
	private void method4780(@OriginalArg(0) int arg0, @OriginalArg(1) Packet arg1) {
		if (arg0 == 1) {
			this.anInt6178 = arg1.method2536();
		} else if (arg0 == 2) {
			this.name = arg1.method2537();
		} else if (arg0 == 4) {
			this.anInt6137 = arg1.method2536();
		} else if (arg0 == 5) {
			this.anInt6133 = arg1.method2536();
		} else if (arg0 == 6) {
			this.anInt6122 = arg1.method2536();
		} else if (arg0 == 7) {
			this.anInt6152 = arg1.method2536();
			if (this.anInt6152 > 32767) {
				this.anInt6152 -= 65536;
			}
			this.anInt6152 <<= 0x0;
		} else if (arg0 == 8) {
			this.lb = arg1.method2536();
			if (this.lb > 32767) {
				this.lb -= 65536;
			}
			this.lb <<= 0x0;
		} else if (arg0 == 11) {
			this.anInt6164 = 1;
		} else if (arg0 == 12) {
			this.anInt6165 = arg1.method2529();
		} else if (arg0 == 16) {
			this.membersOnly = true;
		} else if (arg0 == 23) {
			this.anInt6157 = arg1.method2536();
		} else if (arg0 == 24) {
			this.anInt6176 = arg1.method2536();
		} else if (arg0 == 25) {
			this.anInt6156 = arg1.method2536();
		} else if (arg0 == 26) {
			this.anInt6150 = arg1.method2536();
		} else if (arg0 >= 30 && arg0 < 35) {
			this.aStringArray34[arg0 - 30] = arg1.method2537();
		} else if (arg0 >= 35 && arg0 < 40) {
			this.aStringArray33[arg0 - 35] = arg1.method2537();
		} else {
			@Pc(147) int local147;
			@Pc(157) int local157;
			if (arg0 == 40) {
				local147 = arg1.method2490();
				this.aShortArray211 = new short[local147];
				this.aShortArray214 = new short[local147];
				for (local157 = 0; local157 < local147; local157++) {
					this.aShortArray214[local157] = (short) arg1.method2536();
					this.aShortArray211[local157] = (short) arg1.method2536();
				}
			} else if (arg0 == 41) {
				local147 = arg1.method2490();
				this.aShortArray212 = new short[local147];
				this.aShortArray213 = new short[local147];
				for (local157 = 0; local157 < local147; local157++) {
					this.aShortArray213[local157] = (short) arg1.method2536();
					this.aShortArray212[local157] = (short) arg1.method2536();
				}
			} else if (arg0 == 42) {
				local147 = arg1.method2490();
				this.recolorDPalette = new byte[local147];
				for (local157 = 0; local157 < local147; local157++) {
					this.recolorDPalette[local157] = arg1.method2542();
				}
			} else if (arg0 == 65) {
				this.stockmarket = true;
			} else if (arg0 == 78) {
				this.anInt6170 = arg1.method2536();
			} else if (arg0 == 79) {
				this.anInt6159 = arg1.method2536();
			} else if (arg0 == 90) {
				this.anInt6168 = arg1.method2536();
			} else if (arg0 == 91) {
				this.anInt6147 = arg1.method2536();
			} else if (arg0 == 92) {
				this.anInt6125 = arg1.method2536();
			} else if (arg0 == 93) {
				this.anInt6161 = arg1.method2536();
			} else if (arg0 == 95) {
				this.anInt6135 = arg1.method2536();
			} else if (arg0 == 96) {
				this.anInt6151 = arg1.method2490();
			} else if (arg0 == 97) {
				this.anInt6162 = arg1.method2536();
			} else if (arg0 == 98) {
				this.anInt6175 = arg1.method2536();
			} else if (arg0 >= 100 && arg0 < 110) {
				if (this.stackIds == null) {
					this.stackIds = new int[10];
					this.stackTriggerAmount = new int[10];
				}
				this.stackIds[arg0 - 100] = arg1.method2536();
				this.stackTriggerAmount[arg0 - 100] = arg1.method2536();
			} else if (arg0 == 110) {
				this.anInt6180 = arg1.method2536();
			} else if (arg0 == 111) {
				this.anInt6144 = arg1.method2536();
			} else if (arg0 == 112) {
				this.anInt6148 = arg1.method2536();
			} else if (arg0 == 113) {
				this.anInt6130 = arg1.method2542();
			} else if (arg0 == 114) {
				this.anInt6146 = arg1.method2542() * 5;
			} else if (arg0 == 115) {
				this.anInt6181 = arg1.method2490();
			} else if (arg0 == 121) {
				this.anInt6138 = arg1.method2536();
			} else if (arg0 == 122) {
				this.anInt6185 = arg1.method2536();
			} else if (arg0 == 125) {
				this.anInt6174 = arg1.method2542() << 0;
				this.anInt6140 = arg1.method2542() << 0;
				this.anInt6136 = arg1.method2542() << 0;
			} else if (arg0 == 126) {
				this.anInt6145 = arg1.method2542() << 0;
				this.anInt6132 = arg1.method2542() << 0;
				this.anInt6134 = arg1.method2542() << 0;
			} else if (arg0 == 127) {
				this.anInt6124 = arg1.method2490();
				this.anInt6155 = arg1.method2536();
			} else if (arg0 == 128) {
				this.anInt6153 = arg1.method2490();
				this.anInt6127 = arg1.method2536();
			} else if (arg0 == 129) {
				this.anInt6143 = arg1.method2490();
				this.anInt6160 = arg1.method2536();
			} else if (arg0 == 130) {
				this.anInt6184 = arg1.method2490();
				this.anInt6126 = arg1.method2536();
			} else if (arg0 == 132) {
				local147 = arg1.method2490();
				this.quests = new int[local147];
				for (local157 = 0; local157 < local147; local157++) {
					this.quests[local157] = arg1.method2536();
				}
			} else if (arg0 == 249) {
				local147 = arg1.method2490();
				if (this.aClass102_36 == null) {
					local157 = SpriteDrawQueue.method4949(local147);
					this.aClass102_36 = new CubeMapSpecularEffect(local157);
				}
				for (local157 = 0; local157 < local147; local157++) {
					@Pc(517) boolean local517 = arg1.method2490() == 1;
					@Pc(521) int local521 = arg1.method2493();
					@Pc(530) Country local530;
					if (local517) {
						local530 = new Class4_Sub7(arg1.method2537());
					} else {
						local530 = new Class4_Sub37(arg1.method2529());
					}
					this.aClass102_36.method2703((long) local521, local530);
				}
				return;
			}
		}
	}

	@OriginalMember(owner = "client!rv", name = "b", descriptor = "(III)I")
	public int method4781(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_36 == null) {
			return arg1;
		} else {
			@Pc(16) Class4_Sub37 local16 = (Class4_Sub37) this.aClass102_36.method2700((long) arg0);
			return local16 == null ? arg1 : local16.anInt5426;
		}
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(II[I)V")
	private void method4782(@OriginalArg(1) int arg0, @OriginalArg(2) int[] arg1) {
		for (@Pc(3) int local3 = 31; local3 > 0; local3--) {
			@Pc(9) int local9 = local3 * 36;
			for (@Pc(11) int local11 = 35; local11 > 0; local11--) {
				if (arg1[local9 + local11] == 0 && arg1[local9 + local11 - 37] != 0) {
					arg1[local11 + local9] = arg0;
				}
			}
		}
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(ZI)Lclient!jq;")
	public Mesh method4783(@OriginalArg(0) boolean arg0) {
		@Pc(8) int local8 = this.anInt6157;
		@Pc(11) int local11 = this.anInt6176;
		@Pc(14) int local14 = this.anInt6170;
		if (arg0) {
			local14 = this.anInt6159;
			local8 = this.anInt6156;
			local11 = this.anInt6150;
		}
		if (local8 == -1) {
			return null;
		}
		@Pc(40) Mesh local40 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, local8);
		if (local40.version < 13) {
			local40.method3039();
		}
		if (local11 != -1) {
			@Pc(62) Mesh local62 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, local11);
			if (local62.version < 13) {
				local62.method3039();
			}
			if (local14 == -1) {
				@Pc(124) Mesh[] local124 = new Mesh[] { local40, local62 };
				local40 = new Mesh(local124, 2);
			} else {
				@Pc(82) Mesh local82 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, local14);
				if (local82.version < 13) {
					local82.method3039();
				}
				@Pc(105) Mesh[] local105 = new Mesh[] { local40, local62, local82 };
				local40 = new Mesh(local105, 3);
			}
		}
		if (local40 == null) {
			return null;
		}
		if (!arg0 && (this.anInt6174 != 0 || this.anInt6140 != 0 || this.anInt6136 != 0)) {
			local40.method3041(this.anInt6140, this.anInt6174, this.anInt6136);
		}
		if (arg0 && (this.anInt6145 != 0 || this.anInt6132 != 0 || this.anInt6134 != 0)) {
			local40.method3041(this.anInt6132, this.anInt6145, this.anInt6134);
		}
		@Pc(185) int local185;
		if (this.aShortArray214 != null) {
			for (local185 = 0; local185 < this.aShortArray214.length; local185++) {
				local40.method3055(this.aShortArray214[local185], this.aShortArray211[local185]);
			}
		}
		if (this.aShortArray213 != null) {
			for (local185 = 0; local185 < this.aShortArray213.length; local185++) {
				local40.method3038(this.aShortArray212[local185], this.aShortArray213[local185]);
			}
		}
		return local40;
	}

	@OriginalMember(owner = "client!rv", name = "b", descriptor = "(IZ)Z")
	public boolean method4784(@OriginalArg(1) boolean arg0) {
		@Pc(6) int local6 = this.anInt6157;
		@Pc(9) int local9 = this.anInt6176;
		@Pc(12) int local12 = this.anInt6170;
		if (arg0) {
			local12 = this.anInt6159;
			local9 = this.anInt6150;
			local6 = this.anInt6156;
		}
		if (local6 == -1) {
			return true;
		}
		@Pc(30) boolean local30 = true;
		if (!this.typeList.aClass76_77.method2098(0, local6)) {
			local30 = false;
		}
		if (local9 != -1 && !this.typeList.aClass76_77.method2098(0, local9)) {
			local30 = false;
		}
		if (local12 != -1 && !this.typeList.aClass76_77.method2098(0, local12)) {
			local30 = false;
		}
		return local30;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(ILclient!rv;Lclient!rv;)V")
	public void generateNote(@OriginalArg(1) ItemType arg0, @OriginalArg(2) ItemType objType) {
		this.anInt6135 = objType.anInt6135;
		this.aShortArray211 = objType.aShortArray211;
		this.aShortArray214 = objType.aShortArray214;
		this.name = arg0.name;
		this.lb = objType.lb;
		this.anInt6137 = objType.anInt6137;
		this.anInt6133 = objType.anInt6133;
		this.aShortArray213 = objType.aShortArray213;
		this.anInt6122 = objType.anInt6122;
		this.membersOnly = arg0.membersOnly;
		this.recolorDPalette = objType.recolorDPalette;
		this.aShortArray212 = objType.aShortArray212;
		this.anInt6164 = 1;
		this.anInt6178 = objType.anInt6178;
		this.anInt6165 = arg0.anInt6165;
		this.anInt6152 = objType.anInt6152;
	}

	@OriginalMember(owner = "client!rv", name = "b", descriptor = "(II)Lclient!rv;")
	public ItemType method4788(@OriginalArg(0) int arg0) {
		if (this.stackIds != null && arg0 > 1) {
			@Pc(21) int local21 = -1;
			for (@Pc(23) int local23 = 0; local23 < 10; local23++) {
				if (this.stackTriggerAmount[local23] <= arg0 && this.stackTriggerAmount[local23] != 0) {
					local21 = this.stackIds[local23];
				}
			}
			if (local21 != -1) {
				return this.typeList.method4703(local21);
			}
		}
		return this;
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(Ljava/lang/String;II)Ljava/lang/String;")
	public String method4789(@OriginalArg(0) String arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_36 == null) {
			return arg0;
		} else {
			@Pc(16) Class4_Sub7 local16 = (Class4_Sub7) this.aClass102_36.method2700((long) arg1);
			return local16 == null ? arg0 : local16.aString12;
		}
	}

	@OriginalMember(owner = "client!rv", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method4790(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(15) int local15 = arg0.method2490();
			if (local15 == 0) {
				return;
			}
			this.method4780(local15, arg0);
		}
	}

	@OriginalMember(owner = "client!rv", name = "b", descriptor = "(I)V")
	public void method4791() {
	}

	@OriginalMember(owner = "client!rv", name = "c", descriptor = "(IZ)Lclient!jq;")
	public Mesh method4792(@OriginalArg(1) boolean arg0) {
		@Pc(8) int local8 = this.anInt6168;
		@Pc(11) int local11 = this.anInt6125;
		if (arg0) {
			local8 = this.anInt6147;
			local11 = this.anInt6161;
		}
		if (local8 == -1) {
			return null;
		}
		@Pc(33) Mesh local33 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, local8);
		if (local33.version < 13) {
			local33.method3039();
		}
		if (local11 != -1) {
			@Pc(54) Mesh local54 = SpriteDrawQueue.method4948(this.typeList.aClass76_77, local11);
			if (local54.version < 13) {
				local54.method3039();
			}
			@Pc(73) Mesh[] local73 = new Mesh[] { local33, local54 };
			local33 = new Mesh(local73, 2);
		}
		@Pc(84) int local84;
		if (this.aShortArray214 != null) {
			for (local84 = 0; local84 < this.aShortArray214.length; local84++) {
				local33.method3055(this.aShortArray214[local84], this.aShortArray211[local84]);
			}
		}
		if (this.aShortArray213 != null) {
			for (local84 = 0; local84 < this.aShortArray213.length; local84++) {
				local33.method3038(this.aShortArray212[local84], this.aShortArray213[local84]);
			}
		}
		return local33;
	}

@OriginalMember(owner = "client!rv", name = "b", descriptor = "[S")
	public static final short[] aShortArray210 = new short[] { 6798, 8741, 25238, 4626, 4550 };
@OriginalMember(owner = "client!rv", name = "l", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_83 = new Class215(13, 3);
@OriginalMember(owner = "client!rv", name = "m", descriptor = "[Lclient!iv;")
	public static final Packet[] aClass4_Sub12Array1 = new Packet[2048];
@OriginalMember(owner = "client!rv", name = "Cb", descriptor = "I")
	public static int anInt6183 = 0;
@OriginalMember(owner = "client!rv", name = "Fb", descriptor = "I")
	public static int anInt6186 = 0;
@OriginalMember(owner = "client!rv", name = "a", descriptor = "(III)V")
	public static void method4777(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		if (local7 == null) {
			return;
		}
		if (local7.aClass16_Sub5_3 != null) {
			local7.aClass16_Sub5_3 = null;
		}
		if (local7.aClass16_Sub5_2 != null) {
			local7.aClass16_Sub5_2 = null;
		}
	}
@OriginalMember(owner = "client!rv", name = "a", descriptor = "(IB)I")
	public static int method4785(@OriginalArg(0) int arg0) {
		return arg0 >>> 7;
	}
}
