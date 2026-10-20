package com.jagex.game.runetek5.config.mec;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.vardomain.VarDomain;

import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static281;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.scene.ground.Ground;

import com.jagex.game.runetek5.config.mec.*;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.deob.Class210;
@OriginalClass("client!nf")
public final class MECType {

	@OriginalMember(owner = "client!nf", name = "d", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_24;

	@OriginalMember(owner = "client!nf", name = "e", descriptor = "I")
	private int anInt4903;

	@OriginalMember(owner = "client!nf", name = "i", descriptor = "I")
	public int anInt4906;

	@OriginalMember(owner = "client!nf", name = "j", descriptor = "Ljava/lang/String;")
	public String aString47;

	@OriginalMember(owner = "client!nf", name = "l", descriptor = "I")
	public int anInt4908;

	@OriginalMember(owner = "client!nf", name = "r", descriptor = "I")
	public int anInt4914;

	@OriginalMember(owner = "client!nf", name = "u", descriptor = "Lclient!pj;")
	public MECTypeList initialType;

	@OriginalMember(owner = "client!nf", name = "w", descriptor = "I")
	private int anInt4916;

	@OriginalMember(owner = "client!nf", name = "y", descriptor = "I")
	public int anInt4918;

	@OriginalMember(owner = "client!nf", name = "B", descriptor = "Ljava/lang/String;")
	public String aString48;

	@OriginalMember(owner = "client!nf", name = "F", descriptor = "I")
	private int anInt4924;

	@OriginalMember(owner = "client!nf", name = "H", descriptor = "I")
	public int anInt4926;

	@OriginalMember(owner = "client!nf", name = "I", descriptor = "I")
	public int anInt4927;

	@OriginalMember(owner = "client!nf", name = "K", descriptor = "I")
	private int anInt4929;

	@OriginalMember(owner = "client!nf", name = "M", descriptor = "[I")
	public int[] offsets;

	@OriginalMember(owner = "client!nf", name = "c", descriptor = "Z")
	public boolean aBoolean448 = true;

	@OriginalMember(owner = "client!nf", name = "f", descriptor = "I")
	public int anInt4904 = 0;

	@OriginalMember(owner = "client!nf", name = "q", descriptor = "I")
	public int anInt4913 = -1;

	@OriginalMember(owner = "client!nf", name = "n", descriptor = "I")
	private int anInt4910 = -1;

	@OriginalMember(owner = "client!nf", name = "k", descriptor = "I")
	public int anInt4907 = -1;

	@OriginalMember(owner = "client!nf", name = "t", descriptor = "I")
	public int anInt4915 = Integer.MAX_VALUE;

	@OriginalMember(owner = "client!nf", name = "x", descriptor = "I")
	private int anInt4917 = -1;

	@OriginalMember(owner = "client!nf", name = "z", descriptor = "I")
	public int anInt4919 = -1;

	@OriginalMember(owner = "client!nf", name = "m", descriptor = "I")
	private int anInt4909 = -1;

	@OriginalMember(owner = "client!nf", name = "h", descriptor = "Z")
	public boolean aBoolean449 = true;

	@OriginalMember(owner = "client!nf", name = "G", descriptor = "I")
	public int anInt4925 = Integer.MIN_VALUE;

	@OriginalMember(owner = "client!nf", name = "p", descriptor = "I")
	private int anInt4912 = -1;

	@OriginalMember(owner = "client!nf", name = "N", descriptor = "Z")
	public boolean aBoolean450 = false;

	@OriginalMember(owner = "client!nf", name = "U", descriptor = "I")
	private int anInt4935 = -1;

	@OriginalMember(owner = "client!nf", name = "L", descriptor = "I")
	public int anInt4930 = -1;

	@OriginalMember(owner = "client!nf", name = "R", descriptor = "I")
	public int anInt4933 = Integer.MIN_VALUE;

	@OriginalMember(owner = "client!nf", name = "O", descriptor = "[Ljava/lang/String;")
	public final String[] minimenuActions = new String[5];

	@OriginalMember(owner = "client!nf", name = "W", descriptor = "Z")
	public boolean aBoolean451 = true;

	@OriginalMember(owner = "client!nf", name = "X", descriptor = "I")
	public int anInt4936 = Integer.MAX_VALUE;

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(ILjava/lang/String;I)Ljava/lang/String;")
	public String method3777(@OriginalArg(1) String arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_24 == null) {
			return arg0;
		} else {
			@Pc(23) Class4_Sub7 local23 = (Class4_Sub7) this.aClass102_24.method2700((long) arg1);
			return local23 == null ? arg0 : local23.aString12;
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(ILclient!vs;)Z")
	public boolean method3778(@OriginalArg(1) VarDomain arg0) {
		@Pc(15) int local15;
		if (this.anInt4917 == -1) {
			if (this.anInt4935 == -1) {
				return true;
			}
			local15 = arg0.getVarBitValue(this.anInt4935);
		} else {
			local15 = arg0.getVarValueInt(this.anInt4917);
		}
		if (local15 < this.anInt4924 || this.anInt4903 < local15) {
			return false;
		}
		@Pc(68) int local68;
		if (this.anInt4910 == -1) {
			if (this.anInt4909 == -1) {
				return true;
			}
			local68 = arg0.getVarBitValue(this.anInt4909);
		} else {
			local68 = arg0.getVarValueInt(this.anInt4910);
		}
		return this.anInt4929 <= local68 && this.anInt4916 >= local68;
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(III)I")
	public int method3779(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (this.aClass102_24 == null) {
			return arg0;
		} else {
			@Pc(16) Class4_Sub37 local16 = (Class4_Sub37) this.aClass102_24.method2700((long) arg1);
			return local16 == null ? arg0 : local16.anInt5426;
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(ILclient!ya;)Lclient!l;")
	public NativeSprite method3780(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(20) NativeSprite local20 = (NativeSprite) this.initialType.spriteCache.method1787((long) (this.anInt4912 | 0x20000 | arg0.index << 29));
		if (local20 != null) {
			return local20;
		}
		this.initialType.aClass76_63.method2125(this.anInt4912);
		@Pc(45) SpriteType local45 = SpriteType.listSingle(this.initialType.aClass76_63, this.anInt4912, 0);
		if (local45 != null) {
			local20 = arg0.generateSprite(local45);
			this.initialType.spriteCache.method1779((long) (arg0.index << 29 | this.anInt4912 | 0x20000), local20);
		}
		return local20;
	}

	@OriginalMember(owner = "client!nf", name = "b", descriptor = "(B)V")
	public void method3781() {
		if (this.offsets == null) {
			return;
		}
		for (@Pc(6) int local6 = 0; local6 < this.offsets.length; local6 += 2) {
			if (this.anInt4936 > this.offsets[local6]) {
				this.anInt4936 = this.offsets[local6];
			} else if (this.anInt4933 < this.offsets[local6]) {
				this.anInt4933 = this.offsets[local6];
			}
			if (this.anInt4915 > this.offsets[local6 + 1]) {
				this.anInt4915 = this.offsets[local6 + 1];
			} else if (this.offsets[local6 + 1] > this.anInt4925) {
				this.anInt4925 = this.offsets[local6 + 1];
			}
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(Lclient!iv;BI)V")
	private void method3783(@OriginalArg(0) Packet arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1) {
			this.anInt4930 = arg0.method2536();
		} else if (arg1 == 2) {
			this.anInt4913 = arg0.method2536();
		} else if (arg1 == 3) {
			this.aString47 = arg0.method2537();
		} else if (arg1 == 4) {
			this.anInt4926 = arg0.method2493();
		} else if (arg1 == 5) {
			this.anInt4907 = arg0.method2493();
		} else if (arg1 == 6) {
			this.anInt4904 = arg0.method2490();
		} else {
			@Pc(189) int local189;
			if (arg1 != 7) {
				if (arg1 == 8) {
					this.aBoolean451 = arg0.method2490() == 1;
					return;
				}
				if (arg1 == 9) {
					this.anInt4935 = arg0.method2536();
					if (this.anInt4935 == 65535) {
						this.anInt4935 = -1;
					}
					this.anInt4917 = arg0.method2536();
					if (this.anInt4917 == 65535) {
						this.anInt4917 = -1;
					}
					this.anInt4924 = arg0.method2529();
					this.anInt4903 = arg0.method2529();
					return;
				}
				if (arg1 >= 10 && arg1 <= 14) {
					this.minimenuActions[arg1 - 10] = arg0.method2537();
					return;
				}
				@Pc(196) int local196;
				if (arg1 != 15) {
					if (arg1 != 16) {
						if (arg1 != 17) {
							if (arg1 != 18) {
								if (arg1 == 19) {
									this.anInt4919 = arg0.method2536();
								} else if (arg1 == 20) {
									this.anInt4909 = arg0.method2536();
									if (this.anInt4909 == 65535) {
										this.anInt4909 = -1;
									}
									this.anInt4910 = arg0.method2536();
									if (this.anInt4910 == 65535) {
										this.anInt4910 = -1;
									}
									this.anInt4929 = arg0.method2529();
									this.anInt4916 = arg0.method2529();
									return;
								} else if (arg1 == 21) {
									this.anInt4906 = arg0.method2529();
									return;
								} else if (arg1 == 22) {
									this.anInt4918 = arg0.method2529();
									return;
								} else if (arg1 == 249) {
									local189 = arg0.method2490();
									if (this.aClass102_24 == null) {
										local196 = SpriteDrawQueue.method4949(local189);
										this.aClass102_24 = new CubeMapSpecularEffect(local196);
									}
									for (local196 = 0; local196 < local189; local196++) {
										@Pc(215) boolean local215 = arg0.method2490() == 1;
										@Pc(219) int local219 = arg0.method2493();
										@Pc(228) Country local228;
										if (local215) {
											local228 = new Class4_Sub7(arg0.method2537());
										} else {
											local228 = new Class4_Sub37(arg0.method2529());
										}
										this.aClass102_24.method2703((long) local219, local228);
									}
									return;
								}
								return;
							}
							this.anInt4912 = arg0.method2536();
							return;
						}
						this.aString48 = arg0.method2537();
						return;
					}
					this.aBoolean448 = false;
					return;
				}
				local189 = arg0.method2490();
				this.offsets = new int[local189 * 2];
				for (local196 = 0; local196 < local189 * 2; local196++) {
					this.offsets[local196] = arg0.method2512();
				}
				this.anInt4908 = arg0.method2529();
				this.anInt4927 = arg0.method2529();
				return;
			}
			local189 = arg0.method2490();
			if ((local189 & 0x1) == 0) {
				this.aBoolean449 = false;
			}
			if ((local189 & 0x2) == 2) {
				this.aBoolean450 = true;
				return;
			}
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method3784(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(9) int local9 = arg0.method2490();
			if (local9 == 0) {
				return;
			}
			this.method3783(arg0, local9);
		}
	}

	@OriginalMember(owner = "client!nf", name = "a", descriptor = "(Lclient!ya;ZB)Lclient!l;")
	public NativeSprite method3785(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) boolean arg1) {
		@Pc(11) int local11 = arg1 ? this.anInt4913 : this.anInt4930;
		@Pc(18) int local18 = local11 | arg0.index << 29;
		@Pc(32) NativeSprite local32 = (NativeSprite) this.initialType.spriteCache.method1787((long) local18);
		if (local32 != null) {
			return local32;
		} else if (this.initialType.aClass76_63.method2125(local11)) {
			@Pc(52) SpriteType local52 = SpriteType.listSingle(this.initialType.aClass76_63, local11, 0);
			if (local52 != null) {
				local32 = arg0.generateSprite(local52);
				this.initialType.spriteCache.method1779((long) local18, local32);
			}
			return local32;
		} else {
			return null;
		}
	}

@OriginalMember(owner = "client!nf", name = "s", descriptor = "Lclient!c;")
	public static Ground aClass31_3;
@OriginalMember(owner = "client!nf", name = "J", descriptor = "I")
	public static int anInt4928;
@OriginalMember(owner = "client!nf", name = "V", descriptor = "Lclient!vn;")
	public static Js5WorkerThread aClass255_2;
@OriginalMember(owner = "client!nf", name = "v", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_103 = new Class83("Loaded world map", "Weltkarte geladen", "Mappemonde chargée", "Mapa-múndi carregado");
@OriginalMember(owner = "client!nf", name = "T", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_27 = new LRUCache(4);
@OriginalMember(owner = "client!nf", name = "Z", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_218 = new Class22(84, 2);
@OriginalMember(owner = "client!nf", name = "ab", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_59 = new Class215(75, 16);
@OriginalMember(owner = "client!nf", name = "a", descriptor = "(B)V")
	public static void method3775() {
		for (@Pc(10) ReflectionCheck local10 = (ReflectionCheck) Static281.aClass183_34.method4140(); local10 != null; local10 = (ReflectionCheck) Static281.aClass183_34.method4144()) {
			if (local10.aBoolean66) {
				local10.method638();
			}
		}
		for (@Pc(30) ReflectionCheck local30 = (ReflectionCheck) Class93.aClass183_20.method4140(); local30 != null; local30 = (ReflectionCheck) Class93.aClass183_20.method4144()) {
			if (local30.aBoolean66) {
				local30.method638();
			}
		}
	}
@OriginalMember(owner = "client!nf", name = "a", descriptor = "(ZLclient!iv;)Ljava/lang/String;")
	public static String method3776(@OriginalArg(1) Packet arg0) {
		return LegacyMouseManager.method4831(arg0);
	}
@OriginalMember(owner = "client!nf", name = "a", descriptor = "([BFFIIIFFIIIFLclient!ge;)V")
	public static void method3782(@OriginalArg(0) byte[] arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) int arg3, @OriginalArg(6) float arg4, @OriginalArg(7) float arg5, @OriginalArg(11) float arg6, @OriginalArg(12) Class79 arg7) {
		for (@Pc(10) int local10 = 0; local10 < 16; local10++) {
			Class210.method4771(arg2, arg7, arg1, arg5, arg0, local10, arg6, arg3, arg4);
			arg3 += 16384;
		}
	}
@OriginalMember(owner = "client!nf", name = "a", descriptor = "(Lclient!ya;Lclient!fi;III)V")
	public static void method3787(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) Class16 arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		@Pc(12) Class164 local12;
		if (arg3 < GpuFaceNormal.menuOpenWidth) {
			local12 = Class70.aClass164ArrayArrayArray5[arg2][arg3 + 1][arg4];
			if (local12 != null && local12.aClass16_Sub4_2 != null && local12.aClass16_Sub4_2.hasTileShadow()) {
				arg1.method5337(0, CacheableNode.anInt5935, local12.aClass16_Sub4_2, true, arg0, 0);
			}
		}
		if (arg4 < GpuFaceNormal.menuOpenWidth) {
			local12 = Class70.aClass164ArrayArrayArray5[arg2][arg3][arg4 + 1];
			if (local12 != null && local12.aClass16_Sub4_2 != null && local12.aClass16_Sub4_2.hasTileShadow()) {
				arg1.method5337(CacheableNode.anInt5935, 0, local12.aClass16_Sub4_2, true, arg0, 0);
			}
		}
		if (arg3 < GpuFaceNormal.menuOpenWidth && arg4 < GlModel.anInt3361) {
			local12 = Class70.aClass164ArrayArrayArray5[arg2][arg3 + 1][arg4 + 1];
			if (local12 != null && local12.aClass16_Sub4_2 != null && local12.aClass16_Sub4_2.hasTileShadow()) {
				arg1.method5337(CacheableNode.anInt5935, CacheableNode.anInt5935, local12.aClass16_Sub4_2, true, arg0, 0);
			}
		}
		if (arg3 < GpuFaceNormal.menuOpenWidth && arg4 > 0) {
			local12 = Class70.aClass164ArrayArrayArray5[arg2][arg3 + 1][arg4 - 1];
			if (local12 != null && local12.aClass16_Sub4_2 != null && local12.aClass16_Sub4_2.hasTileShadow()) {
				arg1.method5337(-CacheableNode.anInt5935, CacheableNode.anInt5935, local12.aClass16_Sub4_2, true, arg0, 0);
			}
		}
	}
@OriginalMember(owner = "client!nf", name = "d", descriptor = "(B)V")
	public static void method3788() {
		Static308.aBoolean485 = true;
	}
}
