package com.jagex.game.runetek5.config.bas;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static242;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static458;

import com.jagex.game.runetek5.config.bas.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Class16_Sub3;
@OriginalClass("client!mp")
public final class BasType {

	@OriginalMember(owner = "client!mp", name = "z", descriptor = "[[I")
	public int[][] anIntArrayArray38;

	@OriginalMember(owner = "client!mp", name = "N", descriptor = "[I")
	public int[] anIntArray306;

	@OriginalMember(owner = "client!mp", name = "e", descriptor = "I")
	public int anInt4660 = 0;

	@OriginalMember(owner = "client!mp", name = "h", descriptor = "I")
	public int anInt4663 = -1;

	@OriginalMember(owner = "client!mp", name = "o", descriptor = "I")
	public int anInt4669 = -1;

	@OriginalMember(owner = "client!mp", name = "j", descriptor = "I")
	public int anInt4664 = -1;

	@OriginalMember(owner = "client!mp", name = "p", descriptor = "I")
	public int anInt4670 = 0;

	@OriginalMember(owner = "client!mp", name = "r", descriptor = "I")
	public int anInt4672 = -1;

	@OriginalMember(owner = "client!mp", name = "A", descriptor = "Z")
	public boolean aBoolean428 = true;

	@OriginalMember(owner = "client!mp", name = "v", descriptor = "I")
	public int anInt4676 = 0;

	@OriginalMember(owner = "client!mp", name = "k", descriptor = "I")
	public int anInt4665 = -1;

	@OriginalMember(owner = "client!mp", name = "t", descriptor = "I")
	public int anInt4674 = 0;

	@OriginalMember(owner = "client!mp", name = "n", descriptor = "I")
	public int anInt4668 = -1;

	@OriginalMember(owner = "client!mp", name = "u", descriptor = "I")
	public int anInt4675 = -1;

	@OriginalMember(owner = "client!mp", name = "f", descriptor = "I")
	public int anInt4661 = -1;

	@OriginalMember(owner = "client!mp", name = "c", descriptor = "I")
	public int anInt4658 = -1;

	@OriginalMember(owner = "client!mp", name = "F", descriptor = "I")
	public int anInt4683 = 0;

	@OriginalMember(owner = "client!mp", name = "B", descriptor = "I")
	public int anInt4680 = -1;

	@OriginalMember(owner = "client!mp", name = "b", descriptor = "I")
	public int anInt4657 = -1;

	@OriginalMember(owner = "client!mp", name = "m", descriptor = "I")
	public int anInt4667 = -1;

	@OriginalMember(owner = "client!mp", name = "G", descriptor = "I")
	public int anInt4684 = -1;

	@OriginalMember(owner = "client!mp", name = "g", descriptor = "I")
	public int anInt4662 = 0;

	@OriginalMember(owner = "client!mp", name = "J", descriptor = "I")
	public int anInt4687 = -1;

	@OriginalMember(owner = "client!mp", name = "q", descriptor = "I")
	public int anInt4671 = 0;

	@OriginalMember(owner = "client!mp", name = "E", descriptor = "[I")
	public int[] anIntArray304 = null;

	@OriginalMember(owner = "client!mp", name = "I", descriptor = "I")
	public int anInt4686 = -1;

	@OriginalMember(owner = "client!mp", name = "s", descriptor = "I")
	public int anInt4673 = -1;

	@OriginalMember(owner = "client!mp", name = "O", descriptor = "I")
	public int anInt4690 = 0;

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "I")
	public int anInt4656 = -1;

	@OriginalMember(owner = "client!mp", name = "R", descriptor = "I")
	public int anInt4693 = 0;

	@OriginalMember(owner = "client!mp", name = "x", descriptor = "I")
	public int anInt4678 = -1;

	@OriginalMember(owner = "client!mp", name = "C", descriptor = "I")
	public int anInt4681 = -1;

	@OriginalMember(owner = "client!mp", name = "D", descriptor = "I")
	public int anInt4682 = -1;

	@OriginalMember(owner = "client!mp", name = "U", descriptor = "I")
	public int anInt4696 = 0;

	@OriginalMember(owner = "client!mp", name = "M", descriptor = "I")
	public int anInt4689 = -1;

	@OriginalMember(owner = "client!mp", name = "V", descriptor = "I")
	public int anInt4697 = -1;

	@OriginalMember(owner = "client!mp", name = "L", descriptor = "I")
	public int anInt4688 = -1;

	@OriginalMember(owner = "client!mp", name = "Q", descriptor = "I")
	public int anInt4692 = -1;

	@OriginalMember(owner = "client!mp", name = "P", descriptor = "I")
	private int anInt4691 = 0;

	@OriginalMember(owner = "client!mp", name = "K", descriptor = "[I")
	public int[] anIntArray305 = null;

	@OriginalMember(owner = "client!mp", name = "H", descriptor = "I")
	public int anInt4685 = 0;

	@OriginalMember(owner = "client!mp", name = "Z", descriptor = "I")
	public int anInt4700 = 0;

	@OriginalMember(owner = "client!mp", name = "bb", descriptor = "I")
	public int anInt4701 = -1;

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(Z)I")
	public int method3637() {
		if (this.anInt4661 != -1) {
			return this.anInt4661;
		} else if (this.anIntArray304 == null) {
			return -1;
		} else {
			@Pc(28) int local28 = (int) (Math.random() * (double) this.anInt4691);
			@Pc(30) int local30;
			for (local30 = 0; this.anIntArray305[local30] <= local28; local30++) {
				local28 -= this.anIntArray305[local30];
			}
			return this.anIntArray304[local30];
		}
	}

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(ILclient!iv;)V")
	public void method3638(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(18) int local18 = arg0.method2490();
			if (local18 == 0) {
				return;
			}
			this.method3643(local18, arg0);
		}
	}

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(BI)Z")
	public boolean method3642(@OriginalArg(1) int arg0) {
		if (arg0 == -1) {
			return false;
		} else if (this.anInt4661 == arg0) {
			return true;
		} else {
			if (this.anIntArray304 != null) {
				for (@Pc(32) int local32 = 0; local32 < this.anIntArray304.length; local32++) {
					if (this.anIntArray304[local32] == arg0) {
						return true;
					}
				}
			}
			return false;
		}
	}

	@OriginalMember(owner = "client!mp", name = "a", descriptor = "(IILclient!iv;)V")
	private void method3643(@OriginalArg(0) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 1) {
			this.anInt4661 = arg1.method2536();
			this.anInt4697 = arg1.method2536();
			if (this.anInt4697 == 65535) {
				this.anInt4697 = -1;
			}
			if (this.anInt4661 == 65535) {
				this.anInt4661 = -1;
				return;
			}
		} else if (arg0 == 2) {
			this.anInt4667 = arg1.method2536();
			return;
		} else if (arg0 == 3) {
			this.anInt4681 = arg1.method2536();
			return;
		} else if (arg0 == 4) {
			this.anInt4675 = arg1.method2536();
		} else if (arg0 == 5) {
			this.anInt4688 = arg1.method2536();
			return;
		} else if (arg0 == 6) {
			this.anInt4669 = arg1.method2536();
			return;
		} else if (arg0 == 7) {
			this.anInt4656 = arg1.method2536();
			return;
		} else if (arg0 == 8) {
			this.anInt4686 = arg1.method2536();
			return;
		} else if (arg0 == 9) {
			this.anInt4665 = arg1.method2536();
			return;
		} else if (arg0 == 26) {
			this.anInt4662 = (short) (arg1.method2490() * 4);
			this.anInt4670 = (short) (arg1.method2490() * 4);
			return;
		} else {
			@Pc(103) int local103;
			@Pc(294) int local294;
			if (arg0 != 27) {
				if (arg0 == 28) {
					this.anIntArray306 = new int[12];
					for (local103 = 0; local103 < 12; local103++) {
						this.anIntArray306[local103] = arg1.method2490();
						if (this.anIntArray306[local103] == 255) {
							this.anIntArray306[local103] = -1;
						}
					}
					return;
				}
				if (arg0 == 29) {
					this.anInt4676 = arg1.method2490();
					return;
				}
				if (arg0 == 30) {
					this.anInt4696 = arg1.method2536();
					return;
				}
				if (arg0 == 31) {
					this.anInt4700 = arg1.method2490();
					return;
				}
				if (arg0 == 32) {
					this.anInt4685 = arg1.method2536();
					return;
				}
				if (arg0 == 33) {
					this.anInt4674 = arg1.method2512();
					return;
				}
				if (arg0 == 34) {
					this.anInt4683 = arg1.method2490();
					return;
				}
				if (arg0 == 35) {
					this.anInt4690 = arg1.method2536();
					return;
				}
				if (arg0 == 36) {
					this.anInt4660 = arg1.method2512();
					return;
				}
				if (arg0 == 37) {
					this.anInt4664 = arg1.method2490();
					return;
				}
				if (arg0 == 38) {
					this.anInt4663 = arg1.method2536();
					return;
				}
				if (arg0 == 39) {
					this.anInt4689 = arg1.method2536();
					return;
				}
				if (arg0 == 40) {
					this.anInt4701 = arg1.method2536();
					return;
				}
				if (arg0 == 41) {
					this.anInt4680 = arg1.method2536();
					return;
				}
				if (arg0 == 42) {
					this.anInt4658 = arg1.method2536();
					return;
				}
				if (arg0 == 43) {
					this.anInt4692 = arg1.method2536();
					return;
				}
				if (arg0 == 44) {
					this.anInt4673 = arg1.method2536();
					return;
				}
				if (arg0 == 45) {
					this.anInt4684 = arg1.method2536();
					return;
				}
				if (arg0 == 46) {
					this.anInt4657 = arg1.method2536();
					return;
				}
				if (arg0 == 47) {
					this.anInt4672 = arg1.method2536();
					return;
				}
				if (arg0 == 48) {
					this.anInt4687 = arg1.method2536();
					return;
				}
				if (arg0 == 49) {
					this.anInt4678 = arg1.method2536();
					return;
				}
				if (arg0 == 50) {
					this.anInt4668 = arg1.method2536();
					return;
				}
				if (arg0 == 51) {
					this.anInt4682 = arg1.method2536();
					return;
				}
				if (arg0 != 52) {
					if (arg0 == 53) {
						this.aBoolean428 = false;
					} else if (arg0 == 54) {
						this.anInt4671 = arg1.method2490() << 6;
						this.anInt4693 = arg1.method2490() << 6;
						return;
					}
					return;
				}
				local103 = arg1.method2490();
				this.anIntArray305 = new int[local103];
				this.anIntArray304 = new int[local103];
				for (local294 = 0; local294 < local103; local294++) {
					this.anIntArray304[local294] = arg1.method2536();
					@Pc(307) int local307 = arg1.method2490();
					this.anIntArray305[local294] = local307;
					this.anInt4691 += local307;
				}
				return;
			}
			if (this.anIntArrayArray38 == null) {
				this.anIntArrayArray38 = new int[12][];
			}
			local103 = arg1.method2490();
			this.anIntArrayArray38[local103] = new int[6];
			for (local294 = 0; local294 < 6; local294++) {
				this.anIntArrayArray38[local103][local294] = arg1.method2512();
			}
			return;
		}
	}

@OriginalMember(owner = "client!mp", name = "i", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_54 = new Class215(4, -1);
@OriginalMember(owner = "client!mp", name = "X", descriptor = "Z")
	public static boolean aBoolean429 = true;
@OriginalMember(owner = "client!mp", name = "ab", descriptor = "[I")
	public static final int[] anIntArray307 = new int[] { 4, 4, 1, 2, 6, 4, 2, 46, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
@OriginalMember(owner = "client!mp", name = "cb", descriptor = "[[B")
	public static final byte[][] aByteArrayArray17 = new byte[50][];
@OriginalMember(owner = "client!mp", name = "a", descriptor = "(IBII)I")
	public static int method3636(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(7) int local7 = arg0 & 0x3;
		if (local7 == 0) {
			return arg2;
		} else if (local7 == 1) {
			return 7 - arg1;
		} else if (local7 == 2) {
			return 7 - arg2;
		} else {
			return arg1;
		}
	}
@OriginalMember(owner = "client!mp", name = "a", descriptor = "(I)V")
	public static void method3639() {
		if (QuickchatMessageTypeList.aClass11_5 != null) {
			QuickchatMessageTypeList.aClass11_5.method138();
			QuickchatMessageTypeList.aClass11_5 = null;
		}
		Class244.method5485();
		TextureOpColorGradient.method1541();
		for (@Pc(17) int local17 = 0; local17 < 4; local17++) {
			PlayerVarDomain.aClass213Array1[local17].method4851();
		}
		Class4_Sub7.method633(false);
		System.gc();
		Class16_Sub3.method724();
		TextureOpSquareWaveform.aBoolean666 = false;
		JavaThreadResource.mapBuildLevel = -1;
		Class230.method5094(true);
		MapRegion.anInt7307 = 0;
		TileMessage.anInt7373 = 0;
		IDKTypeList.anInt3265 = 0;
		Class50_Sub1.anInt3453 = 0;
		OpenGLTextureCache.anInt1771 = 0;
		for (@Pc(64) int local64 = 0; local64 < Class183.aClass38Array1.length; local64++) {
			Class183.aClass38Array1[local64] = null;
		}
		StructTypeList.method1983();
		for (@Pc(83) int local83 = 0; local83 < 2048; local83++) {
			Static267.aClass16_Sub1_Sub5_Sub1Array1[local83] = null;
		}
		Class10.anInt163 = 0;
		for (@Pc(97) int local97 = 0; local97 < 32768; local97++) {
			Class93.aClass16_Sub1_Sub5_Sub2Array1[local97] = null;
		}
		LocTypeList.aClass102_40.method2699();
		GlParticleRenderer.method913();
		Js5ResourceRequest.mediumPos = 0;
		Class4_Sub6_Sub24.aClass114_1.reset();
		Class4_Sub6_Sub4.method1277();
		Static242.method3557();
		Class95_Sub3_Sub1.method4994(true);
		try {
			Static458.method2593(Class126.aClass66_4.anApplet1, "loggedout");
		} catch (@Pc(136) Throwable local136) {
		}
		NativeBillboardFace.aClass4_Sub42_2 = null;
		StillWaterEffect.aLong212 = 0L;
	}
@OriginalMember(owner = "client!mp", name = "a", descriptor = "(IIII[I)V")
	public static void method3640(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int[] arg3) {
		arg0--;
		@Pc(7) int local7 = arg2 - 1;
		@Pc(11) int local11 = local7 - 7;
		while (arg0 < local11) {
			@Pc(15) int local15 = arg0 + 1;
			arg3[local15] = arg1;
			@Pc(20) int local20 = local15 + 1;
			arg3[local20] = arg1;
			@Pc(25) int local25 = local20 + 1;
			arg3[local25] = arg1;
			@Pc(30) int local30 = local25 + 1;
			arg3[local30] = arg1;
			@Pc(35) int local35 = local30 + 1;
			arg3[local35] = arg1;
			@Pc(40) int local40 = local35 + 1;
			arg3[local40] = arg1;
			@Pc(45) int local45 = local40 + 1;
			arg3[local45] = arg1;
			arg0 = local45 + 1;
			arg3[arg0] = arg1;
		}
		while (local7 > arg0) {
			arg0++;
			arg3[arg0] = arg1;
		}
	}
}
