package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class135;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
@OriginalClass("client!pk")
public final class Class183 {

	@OriginalMember(owner = "client!pk", name = "r", descriptor = "Lclient!vu;")
	private Country aClass4_208;

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "Lclient!vu;")
	public final Country aClass4_207 = new Country();

	@OriginalMember(owner = "client!pk", name = "<init>", descriptor = "()V")
	public Class183() {
		this.aClass4_207.aClass4_261 = this.aClass4_207;
		this.aClass4_207.aClass4_262 = this.aClass4_207;
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(ILclient!vu;)V")
	public void method4133(@OriginalArg(1) Country arg0) {
		if (arg0.aClass4_261 != null) {
			arg0.method5684();
		}
		arg0.aClass4_262 = this.aClass4_207.aClass4_262;
		arg0.aClass4_261 = this.aClass4_207;
		arg0.aClass4_261.aClass4_262 = arg0;
		arg0.aClass4_262.aClass4_261 = arg0;
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(I)Lclient!vu;")
	public Country method4136() {
		@Pc(7) Country local7 = this.aClass4_207.aClass4_262;
		if (this.aClass4_207 == local7) {
			return null;
		} else {
			local7.method5684();
			return local7;
		}
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(ZLclient!vu;)V")
	public void method4137(@OriginalArg(1) Country arg0) {
		if (arg0.aClass4_261 != null) {
			arg0.method5684();
		}
		arg0.aClass4_262 = this.aClass4_207;
		arg0.aClass4_261 = this.aClass4_207.aClass4_261;
		arg0.aClass4_261.aClass4_262 = arg0;
		arg0.aClass4_262.aClass4_261 = arg0;
	}

	@OriginalMember(owner = "client!pk", name = "b", descriptor = "(I)V")
	public void method4138() {
		while (true) {
			@Pc(5) Country local5 = this.aClass4_207.aClass4_262;
			if (this.aClass4_207 == local5) {
				this.aClass4_208 = null;
				return;
			}
			local5.method5684();
		}
	}

	@OriginalMember(owner = "client!pk", name = "c", descriptor = "(I)Lclient!vu;")
	public Country method4139() {
		@Pc(12) Country local12 = this.aClass4_207.aClass4_261;
		if (this.aClass4_207 == local12) {
			this.aClass4_208 = null;
			return null;
		} else {
			this.aClass4_208 = local12.aClass4_261;
			return local12;
		}
	}

	@OriginalMember(owner = "client!pk", name = "d", descriptor = "(I)Lclient!vu;")
	public Country method4140() {
		@Pc(12) Country local12 = this.aClass4_207.aClass4_262;
		if (this.aClass4_207 == local12) {
			this.aClass4_208 = null;
			return null;
		} else {
			this.aClass4_208 = local12.aClass4_262;
			return local12;
		}
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(Lclient!vu;ZLclient!pk;)V")
	private void method4141(@OriginalArg(0) Country arg0, @OriginalArg(2) Class183 arg1) {
		@Pc(7) Country local7 = this.aClass4_207.aClass4_261;
		this.aClass4_207.aClass4_261 = arg0.aClass4_261;
		arg0.aClass4_261.aClass4_262 = this.aClass4_207;
		if (arg0 != this.aClass4_207) {
			arg0.aClass4_261 = arg1.aClass4_207.aClass4_261;
			arg0.aClass4_261.aClass4_262 = arg0;
			arg1.aClass4_207.aClass4_261 = local7;
			local7.aClass4_262 = arg1.aClass4_207;
		}
	}

	@OriginalMember(owner = "client!pk", name = "b", descriptor = "(B)Lclient!vu;")
	public Country method4144() {
		@Pc(6) Country local6 = this.aClass4_208;
		if (this.aClass4_207 == local6) {
			this.aClass4_208 = null;
			return null;
		} else {
			this.aClass4_208 = local6.aClass4_262;
			return local6;
		}
	}

	@OriginalMember(owner = "client!pk", name = "c", descriptor = "(B)Lclient!vu;")
	public Country method4145() {
		@Pc(6) Country local6 = this.aClass4_208;
		if (this.aClass4_207 == local6) {
			this.aClass4_208 = null;
			return null;
		} else {
			this.aClass4_208 = local6.aClass4_261;
			return local6;
		}
	}

	@OriginalMember(owner = "client!pk", name = "d", descriptor = "(B)Z")
	public boolean method4147() {
		return this.aClass4_207.aClass4_262 == this.aClass4_207;
	}

	@OriginalMember(owner = "client!pk", name = "e", descriptor = "(I)I")
	public int method4148() {
		@Pc(7) int local7 = 0;
		for (@Pc(11) Country local11 = this.aClass4_207.aClass4_262; local11 != this.aClass4_207; local11 = local11.aClass4_262) {
			local7++;
		}
		return local7;
	}

	@OriginalMember(owner = "client!pk", name = "a", descriptor = "(Lclient!pk;B)V")
	public void method4149(@OriginalArg(0) Class183 arg0) {
		this.method4141(this.aClass4_207.aClass4_262, arg0);
	}

@OriginalMember(owner = "client!pk", name = "b", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_65;
@OriginalMember(owner = "client!pk", name = "c", descriptor = "[I")
	public static final int[] anIntArray359 = new int[] { 1, 2, 4, 8 };
@OriginalMember(owner = "client!pk", name = "e", descriptor = "[Lclient!ck;")
	public static final Class38[] aClass38Array1 = new Class38[8];
@OriginalMember(owner = "client!pk", name = "l", descriptor = "[I")
	public static int[] anIntArray360 = new int[2];
@OriginalMember(owner = "client!pk", name = "o", descriptor = "Z")
	public static boolean aBoolean484 = false;
@OriginalMember(owner = "client!pk", name = "w", descriptor = "[Lclient!vh;")
	public static final GpuFaceNormal[] aClass253Array1 = new GpuFaceNormal[2048];
@OriginalMember(owner = "client!pk", name = "a", descriptor = "(ZIIIIII)V")
	public static void method4134(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		Js5MasterIndex.method4768(arg1);
		@Pc(14) int local14 = 0;
		@Pc(18) int local18 = arg1 - arg3;
		if (local18 < 0) {
			local18 = 0;
		}
		@Pc(27) int local27 = arg1;
		@Pc(30) int local30 = -arg1;
		@Pc(32) int local32 = local18;
		@Pc(35) int local35 = -local18;
		@Pc(37) int local37 = -1;
		@Pc(39) int local39 = -1;
		@Pc(43) int[] local43 = Class4_Sub6_Sub7.anIntArrayArray21[arg5];
		@Pc(48) int local48 = arg2 - local18;
		BasType.method3640(arg2 - arg1, arg0, local48, local43);
		@Pc(65) int local65 = arg2 + local18;
		BasType.method3640(local48, arg4, local65, local43);
		BasType.method3640(local65, arg0, arg1 + arg2, local43);
		while (local27 > local14) {
			local37 += 2;
			local39 += 2;
			local30 += local37;
			local35 += local39;
			if (local35 >= 0 && local32 >= 1) {
				Class135.anIntArray288[local32] = local14;
				local32--;
				local35 -= local32 << 1;
			}
			local14++;
			@Pc(134) int[] local134;
			@Pc(141) int[] local141;
			@Pc(145) int local145;
			@Pc(149) int local149;
			@Pc(154) int local154;
			@Pc(158) int local158;
			@Pc(163) int local163;
			if (local30 >= 0) {
				local27--;
				local30 -= local27 << 1;
				if (local27 < local18) {
					local134 = Class4_Sub6_Sub7.anIntArrayArray21[local27 + arg5];
					local141 = Class4_Sub6_Sub7.anIntArrayArray21[arg5 - local27];
					local145 = Class135.anIntArray288[local27];
					local149 = local14 + arg2;
					local154 = arg2 - local14;
					local158 = local145 + arg2;
					local163 = arg2 - local145;
					BasType.method3640(local154, arg0, local163, local134);
					BasType.method3640(local163, arg4, local158, local134);
					BasType.method3640(local158, arg0, local149, local134);
					BasType.method3640(local154, arg0, local163, local141);
					BasType.method3640(local163, arg4, local158, local141);
					BasType.method3640(local158, arg0, local149, local141);
				} else {
					local134 = Class4_Sub6_Sub7.anIntArrayArray21[local27 + arg5];
					local141 = Class4_Sub6_Sub7.anIntArrayArray21[arg5 - local27];
					local145 = arg2 + local14;
					local149 = arg2 - local14;
					BasType.method3640(local149, arg0, local145, local134);
					BasType.method3640(local149, arg0, local145, local141);
				}
			}
			local134 = Class4_Sub6_Sub7.anIntArrayArray21[arg5 + local14];
			local141 = Class4_Sub6_Sub7.anIntArrayArray21[arg5 - local14];
			local145 = local27 + arg2;
			local149 = arg2 - local27;
			if (local18 <= local14) {
				BasType.method3640(local149, arg0, local145, local134);
				BasType.method3640(local149, arg0, local145, local141);
			} else {
				local154 = local32 >= local14 ? local32 : Class135.anIntArray288[local14];
				local158 = arg2 + local154;
				local163 = arg2 - local154;
				BasType.method3640(local149, arg0, local163, local134);
				BasType.method3640(local163, arg4, local158, local134);
				BasType.method3640(local158, arg0, local145, local134);
				BasType.method3640(local149, arg0, local163, local141);
				BasType.method3640(local163, arg4, local158, local141);
				BasType.method3640(local158, arg0, local145, local141);
			}
		}
	}
@OriginalMember(owner = "client!pk", name = "a", descriptor = "(IZIII[[[Lclient!nt;)Z")
	public static boolean method4135(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) Class164[][][] arg4) {
		@Pc(14) byte local14 = arg1 ? 1 : (byte) (Class79_Sub1.anInt6264 & 0xFF);
		if (local14 == Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][arg0][arg2]) {
			return false;
		} else if ((SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][arg0][arg2] & 0x4) == 0) {
			return false;
		} else {
			@Pc(39) byte local39 = 0;
			@Pc(41) int local41 = 0;
			Class79.anIntArray442[0] = arg0;
			@Pc(53) int local53 = local39 + 1;
			Skybox.anIntArray513[0] = arg2;
			Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][arg0][arg2] = local14;
			while (local53 != local41) {
				@Pc(71) int local71 = Class79.anIntArray442[local41] & 0xFFFF;
				@Pc(79) int local79 = Class79.anIntArray442[local41] >> 16 & 0xFF;
				@Pc(87) int local87 = Class79.anIntArray442[local41] >> 24 & 0xFF;
				@Pc(93) int local93 = Skybox.anIntArray513[local41] & 0xFFFF;
				@Pc(101) int local101 = Skybox.anIntArray513[local41] >> 16 & 0xFF;
				local41 = local41 + 1 & 0xFFF;
				@Pc(109) boolean local109 = false;
				if ((SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71][local93] & 0x4) == 0) {
					local109 = true;
				}
				@Pc(123) boolean local123 = false;
				@Pc(165) int local165;
				@Pc(210) int local210;
				label231: for (@Pc(127) int local127 = Class4_Sub6_Sub25.anInt4963 + 1; local127 <= 3; local127++) {
					if ((SeqTypeList.aByteArrayArrayArray2[local127][local71][local93] & 0x8) == 0) {
						@Pc(312) Class16_Sub1 local312;
						@Pc(322) int local322;
						@Pc(301) Class164 local301;
						@Pc(307) Class7 local307;
						if (local109 && arg4[local127][local71][local93] != null) {
							if (arg4[local127][local71][local93].aClass16_Sub5_3 != null) {
								local165 = RequestManager.method3818(local79);
								if (local165 == arg4[local127][local71][local93].aClass16_Sub5_3.anInt6551 || arg4[local127][local71][local93].aClass16_Sub5_2 != null && arg4[local127][local71][local93].aClass16_Sub5_2.anInt6551 == local165) {
									continue;
								}
								if (local87 != 0) {
									local210 = RequestManager.method3818(local87);
									if (arg4[local127][local71][local93].aClass16_Sub5_3.anInt6551 == local210 || arg4[local127][local71][local93].aClass16_Sub5_2 != null && arg4[local127][local71][local93].aClass16_Sub5_2.anInt6551 == local210) {
										continue;
									}
								}
								if (local101 != 0) {
									local210 = RequestManager.method3818(local101);
									if (local210 == arg4[local127][local71][local93].aClass16_Sub5_3.anInt6551 || arg4[local127][local71][local93].aClass16_Sub5_2 != null && local210 == arg4[local127][local71][local93].aClass16_Sub5_2.anInt6551) {
										continue;
									}
								}
							}
							local301 = arg4[local127][local71][local93];
							if (local301.aClass7_3 != null) {
								for (local307 = local301.aClass7_3; local307 != null; local307 = local307.aClass7_1) {
									local312 = local307.aClass16_Sub1_1;
									if (local312 instanceof GameObject) {
										@Pc(318) GameObject local318 = (GameObject) local312;
										local322 = local318.method5354();
										@Pc(326) int local326 = local318.method5347();
										if (local322 == 21) {
											local322 = 19;
										}
										@Pc(337) int local337 = local322 | local326 << 6;
										if (local337 == local79 || local87 != 0 && local87 == local337 || local101 != 0 && local337 == local101) {
											continue label231;
										}
									}
								}
							}
						}
						local301 = arg4[local127][local71][local93];
						if (local301 != null && local301.aClass7_3 != null) {
							for (local307 = local301.aClass7_3; local307 != null; local307 = local307.aClass7_1) {
								local312 = local307.aClass16_Sub1_1;
								if (local312.aShort94 != local312.aShort97 || local312.aShort96 != local312.aShort95) {
									for (@Pc(399) int local399 = local312.aShort94; local399 <= local312.aShort97; local399++) {
										for (local322 = local312.aShort96; local322 <= local312.aShort95; local322++) {
											Class10.aByteArrayArrayArray1[local127][local399][local322] = local14;
										}
									}
								}
							}
						}
						local123 = true;
						Class10.aByteArrayArrayArray1[local127][local71][local93] = local14;
					}
				}
				if (local123) {
					local165 = Class16_Sub1_Sub6.aClass65Array4[Class4_Sub6_Sub25.anInt4963 + 1].l(local71, local93);
					if (anIntArray360[arg3] < local165) {
						anIntArray360[arg3] = local165;
					}
					local210 = local71 << 7;
					if (QuickchatMessageType.DYNAMIC_CORNER_WALL_SIDE_MASKS[arg3] > local210) {
						QuickchatMessageType.DYNAMIC_CORNER_WALL_SIDE_MASKS[arg3] = local210;
					} else if (MapSpriteTypeList.anIntArray549[arg3] < local210) {
						MapSpriteTypeList.anIntArray549[arg3] = local210;
					}
					@Pc(505) int local505 = local93 << 7;
					if (Class38.anIntArray53[arg3] > local505) {
						Class38.anIntArray53[arg3] = local505;
					} else if (Class190.anIntArray376[arg3] < local505) {
						Class190.anIntArray376[arg3] = local505;
					}
				}
				if (!local109) {
					if (local71 >= 1 && Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] != local14) {
						Class79.anIntArray442[local53] = local71 - 1 | 0x120000 | 0xD3000000;
						Skybox.anIntArray513[local53] = local93 | 0x130000;
						local53 = local53 + 1 & 0xFFF;
						Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] = local14;
					}
					local93++;
					if (local93 < Static283.anInt5187) {
						if (local71 - 1 >= 0 && local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71][local93] & 0x4) == 0 && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93 - 1] & 0x4) == 0) {
							Class79.anIntArray442[local53] = local71 - 1 | 0x120000 | 0x52000000;
							Skybox.anIntArray513[local53] = local93 | 0x130000;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] = local14;
							local53 = local53 + 1 & 0xFFF;
						}
						if (local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71][local93]) {
							Class79.anIntArray442[local53] = 0x13000000 | 0x520000 | local71;
							Skybox.anIntArray513[local53] = local93 | 0x530000;
							local53 = local53 + 1 & 0xFFF;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71][local93] = local14;
						}
						if (GLBillowTextureData.anInt5666 > local71 + 1 && local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93] && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71][local93] & 0x4) == 0 && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93 - 1] & 0x4) == 0) {
							Class79.anIntArray442[local53] = local71 + 1 | 0x520000 | 0x92000000;
							Skybox.anIntArray513[local53] = local93 | 0x530000;
							local53 = local53 + 1 & 0xFFF;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93] = local14;
						}
					}
					local93--;
					if (local71 + 1 < GLBillowTextureData.anInt5666 && local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93]) {
						Class79.anIntArray442[local53] = local71 + 1 | 0x53000000 | 0x920000;
						Skybox.anIntArray513[local53] = local93 | 0x930000;
						Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93] = local14;
						local53 = local53 + 1 & 0xFFF;
					}
					local93--;
					if (local93 >= 0) {
						if (local71 - 1 >= 0 && local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71][local93] & 0x4) == 0 && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93 + 1] & 0x4) == 0) {
							Class79.anIntArray442[local53] = 0x12000000 | 0xD20000 | local71 - 1;
							Skybox.anIntArray513[local53] = local93 | 0xD30000;
							local53 = local53 + 1 & 0xFFF;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 - 1][local93] = local14;
						}
						if (Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71][local93] != local14) {
							Class79.anIntArray442[local53] = local71 | 0xD20000 | 0x93000000;
							Skybox.anIntArray513[local53] = local93 | 0xD30000;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71][local93] = local14;
							local53 = local53 + 1 & 0xFFF;
						}
						if (local71 + 1 < GLBillowTextureData.anInt5666 && local14 != Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93] && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71][local93] & 0x4) == 0 && (SeqTypeList.aByteArrayArrayArray2[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93 + 1] & 0x4) == 0) {
							Class79.anIntArray442[local53] = 0xD2000000 | 0x920000 | local71 + 1;
							Skybox.anIntArray513[local53] = local93 | 0x930000;
							local53 = local53 + 1 & 0xFFF;
							Class10.aByteArrayArrayArray1[Class4_Sub6_Sub25.anInt4963][local71 + 1][local93] = local14;
						}
					}
				}
			}
			if (anIntArray360[arg3] != -1000000) {
				anIntArray360[arg3] += 10;
				QuickchatMessageType.DYNAMIC_CORNER_WALL_SIDE_MASKS[arg3] -= 50;
				MapSpriteTypeList.anIntArray549[arg3] += 50;
				Class190.anIntArray376[arg3] += 50;
				Class38.anIntArray53[arg3] -= 50;
			}
			return true;
		}
	}
@OriginalMember(owner = "client!pk", name = "a", descriptor = "(IZ)I")
	public static int method4142(@OriginalArg(0) int arg0) {
		return arg0 >>> 8;
	}
@OriginalMember(owner = "client!pk", name = "a", descriptor = "(Z)V")
	public static void method4146() {
		if (CacheableNodeByteArray.anInt4506 == 5) {
			CacheableNodeByteArray.anInt4506 = 6;
		}
	}
}
