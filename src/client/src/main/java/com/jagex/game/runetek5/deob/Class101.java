package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!hv")
public final class Class101 {

	@OriginalMember(owner = "client!hv", name = "b", descriptor = "[[I")
	private int[][] anIntArrayArray27;

	@OriginalMember(owner = "client!hv", name = "c", descriptor = "[I")
	private int[] anIntArray199;

	@OriginalMember(owner = "client!hv", name = "d", descriptor = "[I")
	private int[] anIntArray200;

	@OriginalMember(owner = "client!hv", name = "f", descriptor = "[I")
	private int[] anIntArray201;

	@OriginalMember(owner = "client!hv", name = "i", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_15;

	@OriginalMember(owner = "client!hv", name = "j", descriptor = "[Ljava/lang/String;")
	private String[] aStringArray12;

	@OriginalMember(owner = "client!hv", name = "k", descriptor = "[I")
	private int[] anIntArray202;

	@OriginalMember(owner = "client!hv", name = "l", descriptor = "Ljava/lang/String;")
	private String aString33;

	@OriginalMember(owner = "client!hv", name = "m", descriptor = "[I")
	private int[] anIntArray203;

	@OriginalMember(owner = "client!hv", name = "n", descriptor = "[I")
	private int[] anIntArray204;

	@OriginalMember(owner = "client!hv", name = "p", descriptor = "Ljava/lang/String;")
	private String aString34;

	@OriginalMember(owner = "client!hv", name = "r", descriptor = "[I")
	private int[] anIntArray205;

	@OriginalMember(owner = "client!hv", name = "s", descriptor = "[[I")
	private int[][] anIntArrayArray28;

	@OriginalMember(owner = "client!hv", name = "t", descriptor = "[I")
	private int[] anIntArray206;

	@OriginalMember(owner = "client!hv", name = "u", descriptor = "[[I")
	private int[][] anIntArrayArray29;

	@OriginalMember(owner = "client!hv", name = "w", descriptor = "[Ljava/lang/String;")
	private String[] aStringArray13;

	@OriginalMember(owner = "client!hv", name = "q", descriptor = "I")
	public int anInt3153 = -1;

	static {
		new Class83("Invalid name", "Unzulässiger Name!", "Nom incorrect", "Nome inválido");
	}

	@OriginalMember(owner = "client!hv", name = "a", descriptor = "(I)V")
	public void method2676() {
		if (this.aString34 == null) {
			this.aString34 = this.aString33;
		}
	}

	@OriginalMember(owner = "client!hv", name = "a", descriptor = "(BLclient!iv;)V")
	public void method2679(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method2681(arg0, local5);
		}
	}

	@OriginalMember(owner = "client!hv", name = "a", descriptor = "(Lclient!iv;II)V")
	private void method2681(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1) {
		if (arg1 == 1) {
			this.aString33 = arg0.method2497();
		} else if (arg1 == 2) {
			this.aString34 = arg0.method2497();
		} else {
			@Pc(82) int local82;
			@Pc(88) int local88;
			if (arg1 == 3) {
				local82 = arg0.method2490();
				this.anIntArrayArray27 = new int[local82][3];
				for (local88 = 0; local88 < local82; local88++) {
					this.anIntArrayArray27[local88][0] = arg0.method2536();
					this.anIntArrayArray27[local88][1] = arg0.method2529();
					this.anIntArrayArray27[local88][2] = arg0.method2529();
				}
			} else if (arg1 == 4) {
				local82 = arg0.method2490();
				this.anIntArrayArray28 = new int[local82][3];
				for (local88 = 0; local88 < local82; local88++) {
					this.anIntArrayArray28[local88][0] = arg0.method2536();
					this.anIntArrayArray28[local88][1] = arg0.method2529();
					this.anIntArrayArray28[local88][2] = arg0.method2529();
				}
			} else if (arg1 == 5) {
				arg0.method2536();
			} else if (arg1 == 6) {
				arg0.method2490();
			} else if (arg1 == 7) {
				arg0.method2490();
				return;
			} else if (arg1 != 8) {
				if (arg1 == 9) {
					arg0.method2490();
					return;
				}
				if (arg1 == 10) {
					local82 = arg0.method2490();
					this.anIntArray206 = new int[local82];
					for (local88 = 0; local88 < local82; local88++) {
						this.anIntArray206[local88] = arg0.method2529();
					}
					return;
				}
				if (arg1 == 12) {
					arg0.method2529();
					return;
				}
				if (arg1 != 13) {
					if (arg1 == 14) {
						local82 = arg0.method2490();
						this.anIntArrayArray29 = new int[local82][2];
						for (local88 = 0; local88 < local82; local88++) {
							this.anIntArrayArray29[local88][0] = arg0.method2490();
							this.anIntArrayArray29[local88][1] = arg0.method2490();
						}
						return;
					}
					if (arg1 == 15) {
						arg0.method2536();
						return;
					}
					if (arg1 == 17) {
						this.anInt3153 = arg0.method2536();
						return;
					}
					if (arg1 != 18) {
						if (arg1 == 19) {
							local82 = arg0.method2490();
							this.anIntArray202 = new int[local82];
							this.aStringArray13 = new String[local82];
							this.anIntArray203 = new int[local82];
							this.anIntArray199 = new int[local82];
							for (local88 = 0; local88 < local82; local88++) {
								this.anIntArray203[local88] = arg0.method2529();
								this.anIntArray202[local88] = arg0.method2529();
								this.anIntArray199[local88] = arg0.method2529();
								this.aStringArray13[local88] = arg0.method2537();
							}
						} else if (arg1 == 249) {
							local82 = arg0.method2490();
							if (this.aClass102_15 == null) {
								local88 = SpriteDrawQueue.method4949(local82);
								this.aClass102_15 = new CubeMapSpecularEffect(local88);
							}
							for (local88 = 0; local88 < local82; local88++) {
								@Pc(222) boolean local222 = arg0.method2490() == 1;
								@Pc(226) int local226 = arg0.method2493();
								@Pc(235) Country local235;
								if (local222) {
									local235 = new Class4_Sub7(arg0.method2537());
								} else {
									local235 = new Class4_Sub37(arg0.method2529());
								}
								this.aClass102_15.method2703((long) local226, local235);
							}
							return;
						}
						return;
					}
					local82 = arg0.method2490();
					this.anIntArray201 = new int[local82];
					this.aStringArray12 = new String[local82];
					this.anIntArray200 = new int[local82];
					this.anIntArray204 = new int[local82];
					for (local88 = 0; local88 < local82; local88++) {
						this.anIntArray204[local88] = arg0.method2529();
						this.anIntArray201[local88] = arg0.method2529();
						this.anIntArray200[local88] = arg0.method2529();
						this.aStringArray12[local88] = arg0.method2537();
					}
					return;
				}
				local82 = arg0.method2490();
				this.anIntArray205 = new int[local82];
				for (local88 = 0; local88 < local82; local88++) {
					this.anIntArray205[local88] = arg0.method2536();
				}
				return;
			}
		}
	}

@OriginalMember(owner = "client!hv", name = "g", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_16 = new LRUCache(4);
@OriginalMember(owner = "client!hv", name = "b", descriptor = "(B)Z")
	public static boolean method2677() {
		@Pc(8) OggOtherStream local8 = (OggOtherStream) Class126.aClass183_28.aClass4_207.aClass4_261;
		if (local8 == null || local8 == Class126.aClass183_28.aClass4_207) {
			return false;
		} else {
			if (local8.anInt5699 >= 2000) {
				local8.anInt5699 -= 2000;
			}
			return local8.anInt5699 == 1002;
		}
	}
@OriginalMember(owner = "client!hv", name = "a", descriptor = "(III)I")
	public static int method2678(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(7) int local7 = 1;
		while (arg1 > 1) {
			if ((arg1 & 0x1) != 0) {
				local7 *= arg0;
			}
			arg0 *= arg0;
			arg1 >>= 0x1;
		}
		if (arg1 == 1) {
			return arg0 * local7;
		} else {
			return local7;
		}
	}
@OriginalMember(owner = "client!hv", name = "a", descriptor = "(Lclient!iv;B)Lclient!oc;")
	public static TextureOperation method2680(@OriginalArg(0) Packet arg0) {
		arg0.method2490();
		@Pc(21) int local21 = arg0.method2490();
		@Pc(25) TextureOperation local25 = ReflectiveRipplingWaterEffect.method122(local21);
		local25.imageCacheCapacity = arg0.method2490();
		@Pc(34) int local34 = arg0.method2490();
		for (@Pc(36) int local36 = 0; local36 < local34; local36++) {
			@Pc(42) int local42 = arg0.method2490();
			local25.method5518(local42, arg0);
		}
		local25.postDecode();
		return local25;
	}
}
