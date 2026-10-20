package com.jagex.game.runetek5.toolkit.opengl.effect;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static411;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.*;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
@OriginalClass("client!ib")
public final class CubeMapSpecularEffect {

	@OriginalMember(owner = "client!ib", name = "m", descriptor = "Lclient!vu;")
	private Country aClass4_121;

	@OriginalMember(owner = "client!ib", name = "n", descriptor = "J")
	private long aLong111;

	@OriginalMember(owner = "client!ib", name = "s", descriptor = "Lclient!vu;")
	private Country aClass4_122;

	@OriginalMember(owner = "client!ib", name = "r", descriptor = "I")
	private int anInt3171 = 0;

	@OriginalMember(owner = "client!ib", name = "d", descriptor = "[Lclient!vu;")
	public final Country[] aClass4Array1;

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "I")
	public final int anInt3159;

	@OriginalMember(owner = "client!ib", name = "<init>", descriptor = "(I)V")
	public CubeMapSpecularEffect(@OriginalArg(0) int arg0) {
		this.aClass4Array1 = new Country[arg0];
		this.anInt3159 = arg0;
		for (@Pc(13) int local13 = 0; local13 < arg0; local13++) {
			@Pc(23) Country local23 = this.aClass4Array1[local13] = new Country();
			local23.aClass4_262 = local23;
			local23.aClass4_261 = local23;
		}
	}

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(I)Lclient!vu;")
	public Country method2697() {
		if (this.aClass4_121 == null) {
			return null;
		}
		@Pc(23) Country local23 = this.aClass4Array1[(int) ((long) (this.anInt3159 - 1) & this.aLong111)];
		while (local23 != this.aClass4_121) {
			if (this.aLong111 == this.aClass4_121.aLong224) {
				@Pc(35) Country local35 = this.aClass4_121;
				this.aClass4_121 = this.aClass4_121.aClass4_262;
				return local35;
			}
			this.aClass4_121 = this.aClass4_121.aClass4_262;
		}
		this.aClass4_121 = null;
		return null;
	}

	@OriginalMember(owner = "client!ib", name = "b", descriptor = "(I)I")
	public int method2698() {
		@Pc(7) int local7 = 0;
		for (@Pc(9) int local9 = 0; local9 < this.anInt3159; local9++) {
			@Pc(16) Country local16 = this.aClass4Array1[local9];
			@Pc(19) Country local19 = local16.aClass4_262;
			while (local16 != local19) {
				local19 = local19.aClass4_262;
				local7++;
			}
		}
		return local7;
	}

	@OriginalMember(owner = "client!ib", name = "c", descriptor = "(I)V")
	public void method2699() {
		for (@Pc(3) int local3 = 0; local3 < this.anInt3159; local3++) {
			@Pc(10) Country local10 = this.aClass4Array1[local3];
			while (true) {
				@Pc(13) Country local13 = local10.aClass4_262;
				if (local13 == local10) {
					break;
				}
				local13.method5684();
			}
		}
		this.aClass4_121 = null;
		this.aClass4_122 = null;
	}

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(JI)Lclient!vu;")
	public Country method2700(@OriginalArg(0) long arg0) {
		this.aLong111 = arg0;
		@Pc(20) Country local20 = this.aClass4Array1[(int) (arg0 & (long) (this.anInt3159 - 1))];
		for (this.aClass4_121 = local20.aClass4_262; this.aClass4_121 != local20; this.aClass4_121 = this.aClass4_121.aClass4_262) {
			if (arg0 == this.aClass4_121.aLong224) {
				@Pc(35) Country local35 = this.aClass4_121;
				this.aClass4_121 = this.aClass4_121.aClass4_262;
				return local35;
			}
		}
		this.aClass4_121 = null;
		return null;
	}

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(JILclient!vu;)V")
	public void method2703(@OriginalArg(0) long arg0, @OriginalArg(2) Country arg1) {
		if (arg1.aClass4_261 != null) {
			arg1.method5684();
		}
		@Pc(26) Country local26 = this.aClass4Array1[(int) (arg0 & (long) (this.anInt3159 - 1))];
		arg1.aClass4_262 = local26;
		arg1.aClass4_261 = local26.aClass4_261;
		arg1.aClass4_261.aClass4_262 = arg1;
		arg1.aLong224 = arg0;
		arg1.aClass4_262.aClass4_261 = arg1;
	}

	@OriginalMember(owner = "client!ib", name = "b", descriptor = "(B)Lclient!vu;")
	public Country method2704() {
		@Pc(24) Country local24;
		if (this.anInt3171 > 0 && this.aClass4_122 != this.aClass4Array1[this.anInt3171 - 1]) {
			local24 = this.aClass4_122;
			this.aClass4_122 = local24.aClass4_262;
			return local24;
		}
		while (this.anInt3171 < this.anInt3159) {
			local24 = this.aClass4Array1[this.anInt3171++].aClass4_262;
			if (this.aClass4Array1[this.anInt3171 - 1] != local24) {
				this.aClass4_122 = local24.aClass4_262;
				return local24;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!ib", name = "c", descriptor = "(B)Lclient!vu;")
	public Country method2705() {
		this.anInt3171 = 0;
		return this.method2704();
	}

	@OriginalMember(owner = "client!ib", name = "d", descriptor = "(I)I")
	public int method2706() {
		return this.anInt3159;
	}

	@OriginalMember(owner = "client!ib", name = "a", descriptor = "(I[Lclient!vu;)I")
	public int method2708(@OriginalArg(1) Country[] arg0) {
		@Pc(15) int local15 = 0;
		for (@Pc(17) int local17 = 0; local17 < this.anInt3159; local17++) {
			@Pc(24) Country local24 = this.aClass4Array1[local17];
			for (@Pc(27) Country local27 = local24.aClass4_262; local27 != local24; local27 = local27.aClass4_262) {
				arg0[local15++] = local27;
			}
		}
		return local15;
	}

@OriginalMember(owner = "client!ib", name = "e", descriptor = "[[I")
	public static final int[][] anIntArrayArray30 = new int[128][128];
@OriginalMember(owner = "client!ib", name = "i", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_67 = new Class83("Loading core fonts - ", "Lade Schriftarten - ", "Chargement des polices - ", "Carregando fontes principais - ");
@OriginalMember(owner = "client!ib", name = "u", descriptor = "F")
	public static float aFloat108 = 1.0F;
@OriginalMember(owner = "client!ib", name = "a", descriptor = "(IZ)I")
	public static int method2696(@OriginalArg(0) int arg0) {
		@Pc(12) int local12 = arg0 >>> 1;
		@Pc(18) int local18 = local12 | local12 >>> 1;
		@Pc(24) int local24 = local18 | local18 >>> 2;
		@Pc(30) int local30 = local24 | local24 >>> 4;
		@Pc(36) int local36 = local30 | local30 >>> 8;
		@Pc(42) int local42 = local36 | local36 >>> 16;
		return ~local42 & arg0;
	}
@OriginalMember(owner = "client!ib", name = "a", descriptor = "(B)V")
	public static void method2701() {
		if (Class4_Sub43.aClass49_2 != null) {
			Class4_Sub43.aClass49_2.method2069();
		}
		if (TextureOpMandelbrot.aClass49_1 != null) {
			TextureOpMandelbrot.aClass49_1.method2069();
		}
	}
@OriginalMember(owner = "client!ib", name = "a", descriptor = "(Lclient!cw;IB)V")
	public static void method2702(@OriginalArg(0) Class4_Sub12_Sub1 arg0, @OriginalArg(1) int arg1) {
		@Pc(17) boolean local17 = arg0.method1143(1) == 1;
		if (local17) {
			Class230.anIntArray497[Static441.anInt7199++] = arg1;
		}
		@Pc(32) int local32 = arg0.method1143(2);
		@Pc(36) PlayerEntity local36 = Static267.aClass16_Sub1_Sub5_Sub1Array1[arg1];
		if (local32 != 0) {
			@Pc(155) int local155;
			@Pc(160) int local160;
			@Pc(165) int local165;
			if (local32 == 1) {
				local155 = arg0.method1143(3);
				local160 = local36.movementBaseX[0];
				local165 = local36.movementBaseY[0];
				if (local155 == 0) {
					local160--;
					local165--;
				} else if (local155 == 1) {
					local165--;
				} else if (local155 == 2) {
					local165--;
					local160++;
				} else if (local155 == 3) {
					local160--;
				} else if (local155 == 4) {
					local160++;
				} else if (local155 == 5) {
					local160--;
					local165++;
				} else if (local155 == 6) {
					local165++;
				} else if (local155 == 7) {
					local160++;
					local165++;
				}
				if (local17) {
					local36.hasPendingPositionUpdate = true;
					local36.anInt4335 = local160;
					local36.anInt4317 = local165;
				} else {
					local36.method3423(Static411.aByteArray95[arg1], local165, local160);
				}
			} else if (local32 == 2) {
				local155 = arg0.method1143(4);
				local160 = local36.movementBaseX[0];
				local165 = local36.movementBaseY[0];
				if (local155 == 0) {
					local165 -= 2;
					local160 -= 2;
				} else if (local155 == 1) {
					local165 -= 2;
					local160--;
				} else if (local155 == 2) {
					local165 -= 2;
				} else if (local155 == 3) {
					local160++;
					local165 -= 2;
				} else if (local155 == 4) {
					local160 += 2;
					local165 -= 2;
				} else if (local155 == 5) {
					local165--;
					local160 -= 2;
				} else if (local155 == 6) {
					local160 += 2;
					local165--;
				} else if (local155 == 7) {
					local160 -= 2;
				} else if (local155 == 8) {
					local160 += 2;
				} else if (local155 == 9) {
					local165++;
					local160 -= 2;
				} else if (local155 == 10) {
					local160 += 2;
					local165++;
				} else if (local155 == 11) {
					local160 -= 2;
					local165 += 2;
				} else if (local155 == 12) {
					local160--;
					local165 += 2;
				} else if (local155 == 13) {
					local165 += 2;
				} else if (local155 == 14) {
					local165 += 2;
					local160++;
				} else if (local155 == 15) {
					local165 += 2;
					local160 += 2;
				}
				if (local17) {
					local36.anInt4335 = local160;
					local36.hasPendingPositionUpdate = true;
					local36.anInt4317 = local165;
				} else {
					local36.method3423(Static411.aByteArray95[arg1], local165, local160);
				}
			} else {
				local155 = arg0.method1143(1);
				@Pc(427) int local427;
				@Pc(435) int local435;
				@Pc(449) int local449;
				@Pc(457) int local457;
				if (local155 == 0) {
					local160 = arg0.method1143(12);
					local165 = local160 >> 10;
					local427 = local160 >> 5 & 0x1F;
					if (local427 > 15) {
						local427 -= 32;
					}
					local435 = local160 & 0x1F;
					if (local435 > 15) {
						local435 -= 32;
					}
					local449 = local36.movementBaseX[0] + local427;
					local457 = local36.movementBaseY[0] + local435;
					if (local17) {
						local36.anInt4317 = local457;
						local36.hasPendingPositionUpdate = true;
						local36.anInt4335 = local449;
					} else {
						local36.method3423(Static411.aByteArray95[arg1], local457, local449);
					}
					local36.aByte82 = (byte) (local36.aByte82 + local165 & 0x3);
					if (Class6.anInt5452 == arg1) {
						Class4_Sub6_Sub25.anInt4963 = local36.aByte82;
					}
				} else {
					local160 = arg0.method1143(30);
					local165 = local160 >> 28;
					local427 = local160 >> 14 & 0x3FFF;
					local435 = local160 & 0x3FFF;
					local449 = (local427 + Class50_Sub1.anInt3453 + local36.movementBaseX[0] & 0x3FFF) - Class50_Sub1.anInt3453;
					local457 = (OpenGLTextureCache.anInt1771 + local36.movementBaseY[0] + local435 & 0x3FFF) - OpenGLTextureCache.anInt1771;
					if (local17) {
						local36.anInt4335 = local449;
						local36.hasPendingPositionUpdate = true;
						local36.anInt4317 = local457;
					} else {
						local36.method3423(Static411.aByteArray95[arg1], local457, local449);
					}
					local36.aByte82 = (byte) (local36.aByte82 + local165 & 0x3);
					if (arg1 == Class6.anInt5452) {
						Class4_Sub6_Sub25.anInt4963 = local36.aByte82;
					}
				}
			}
		} else if (local17) {
			local36.hasPendingPositionUpdate = false;
		} else if (Class6.anInt5452 == arg1) {
			throw new RuntimeException("s:lr");
		} else {
			@Pc(64) GpuFaceNormal local64 = Class183.aClass253Array1[arg1] = new GpuFaceNormal();
			local64.anInt6949 = (local36.movementBaseY[0] + OpenGLTextureCache.anInt1771 >> 6) + (local36.aByte82 << 28) + (Class50_Sub1.anInt3453 + local36.movementBaseX[0] >> 6 << 14);
			if (local36.anInt4323 == -1) {
				local64.anInt6947 = local36.yaw.method811();
			} else {
				local64.anInt6947 = local36.anInt4323;
			}
			local64.anInt6946 = local36.anInt6034;
			local64.aBoolean648 = local36.pMapdot;
			if (local36.isNpc > 0) {
				ObjTypeList.method4705(local36);
			}
			Static267.aClass16_Sub1_Sub5_Sub1Array1[arg1] = null;
			if (arg0.method1143(1) != 0) {
				Class244.method5490(arg0, arg1);
			}
		}
	}
}
