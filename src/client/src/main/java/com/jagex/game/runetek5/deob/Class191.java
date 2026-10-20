package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.PointLight;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.entity.PathingEntity;
@OriginalClass("client!qc")
public final class Class191 {

	@OriginalMember(owner = "client!qc", name = "a", descriptor = "[S")
	public short[] aShortArray189;

	@OriginalMember(owner = "client!qc", name = "b", descriptor = "I")
	private int anInt5561;

	@OriginalMember(owner = "client!qc", name = "d", descriptor = "I")
	public int anInt5562;

	@OriginalMember(owner = "client!qc", name = "f", descriptor = "Lclient!vp;")
	public PointLight aClass4_Sub21_2;

	@OriginalMember(owner = "client!qc", name = "i", descriptor = "I")
	private int anInt5565;

	@OriginalMember(owner = "client!qc", name = "j", descriptor = "I")
	private int anInt5566;

	@OriginalMember(owner = "client!qc", name = "k", descriptor = "I")
	private int anInt5567;

	@OriginalMember(owner = "client!qc", name = "l", descriptor = "I")
	private int anInt5568;

	@OriginalMember(owner = "client!qc", name = "o", descriptor = "I")
	public int anInt5570;

	@OriginalMember(owner = "client!qc", name = "t", descriptor = "Z")
	public boolean aBoolean499;

	@OriginalMember(owner = "client!qc", name = "u", descriptor = "Z")
	public boolean aBoolean500;

	@OriginalMember(owner = "client!qc", name = "<init>", descriptor = "()V")
	private Class191() {
		if (Class16_Sub4_Sub2.anIntArray169 == null) {
			Class4_Sub1_Sub9.method2568();
		}
		this.method4333();
	}

	@OriginalMember(owner = "client!qc", name = "<init>", descriptor = "(Lclient!ya;Lclient!iv;I)V")
	public Class191(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) Packet arg1, @OriginalArg(2) int arg2) {
		if (Class16_Sub4_Sub2.anIntArray169 == null) {
			Class4_Sub1_Sub9.method2568();
		}
		this.anInt5570 = arg1.method2490();
		this.aBoolean499 = (this.anInt5570 & 0x8) != 0;
		this.aBoolean500 = (this.anInt5570 & 0x10) != 0;
		this.anInt5570 &= 0x7;
		@Pc(50) int local50 = arg1.method2536() << arg2;
		@Pc(56) int local56 = arg1.method2536() << arg2;
		@Pc(62) int local62 = arg1.method2536() << arg2;
		@Pc(66) int local66 = arg1.method2490();
		@Pc(72) int local72 = local66 * 2 + 1;
		this.aShortArray189 = new short[local72];
		@Pc(89) int local89;
		for (@Pc(78) int local78 = 0; local78 < this.aShortArray189.length; local78++) {
			@Pc(85) short local85 = (short) arg1.method2536();
			local89 = local85 >>> 8;
			@Pc(93) int local93 = local85 & 0xFF;
			if (local72 <= local89) {
				local89 = local72 - 1;
			}
			if (local93 > local72 - local89) {
				local93 = local72 - local89;
			}
			this.aShortArray189[local78] = (short) (local93 | local89 << 8);
		}
		local66 = (local66 << Static231.anInt4434) + ReflectingTextureEffect.anInt5747;
		@Pc(160) int local160 = Class9_Sub3.anIntArray115 == null ? PathingEntity.anIntArray423[Class56.method1470(arg1.method2536()) & 0xFFFF] : Class9_Sub3.anIntArray115[arg1.method2536()];
		local89 = arg1.method2490();
		this.anInt5562 = local89 & 0x1F;
		this.anInt5566 = (local89 & 0xE0) << 3;
		if (this.anInt5562 != 31) {
			this.method4333();
		}
		this.method4338(arg0, local50, local56, local62, local160, local66);
	}

	@OriginalMember(owner = "client!qc", name = "a", descriptor = "(IZI)V")
	public void method4332(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1) {
		@Pc(14) int local14;
		if (arg1) {
			local14 = 2048;
		} else {
			@Pc(28) int local28 = arg0 * this.anInt5568 / 50 + this.anInt5566 & 0x7FF;
			@Pc(31) int local31 = this.anInt5567;
			if (local31 == 1) {
				local14 = (Class50_Sub1.anIntArray225[local28 << 3] >> 5) + 1024;
			} else if (local31 == 3) {
				local14 = Class16_Sub4_Sub2.anIntArray169[local28] >> 1;
			} else if (local31 == 4) {
				local14 = local28 >> 10 << 11;
			} else if (local31 == 2) {
				local14 = local28;
			} else if (local31 == 5) {
				local14 = (local28 < 1024 ? local28 : 2048 - local28) << 1;
			} else {
				local14 = 2048;
			}
		}
		this.aClass4_Sub21_2.setIntensity((float) (this.anInt5565 + (this.anInt5561 * local14 >> 11)) / 2048.0F);
	}

	@OriginalMember(owner = "client!qc", name = "a", descriptor = "(B)V")
	private void method4333() {
		@Pc(12) int local12 = this.anInt5562;
		if (local12 == 2) {
			this.anInt5565 = 0;
			this.anInt5567 = 1;
			this.anInt5561 = 2048;
			this.anInt5568 = 2048;
		} else if (local12 == 3) {
			this.anInt5565 = 0;
			this.anInt5561 = 2048;
			this.anInt5568 = 4096;
			this.anInt5567 = 1;
		} else if (local12 == 4) {
			this.anInt5567 = 4;
			this.anInt5568 = 2048;
			this.anInt5565 = 0;
			this.anInt5561 = 2048;
		} else if (local12 == 5) {
			this.anInt5568 = 8192;
			this.anInt5561 = 2048;
			this.anInt5567 = 4;
			this.anInt5565 = 0;
		} else if (local12 == 12) {
			this.anInt5565 = 0;
			this.anInt5568 = 2048;
			this.anInt5561 = 2048;
			this.anInt5567 = 2;
		} else if (local12 == 13) {
			this.anInt5561 = 2048;
			this.anInt5565 = 0;
			this.anInt5567 = 2;
			this.anInt5568 = 8192;
		} else if (local12 == 10) {
			this.anInt5565 = 1536;
			this.anInt5561 = 512;
			this.anInt5568 = 2048;
			this.anInt5567 = 3;
		} else if (local12 == 11) {
			this.anInt5565 = 1536;
			this.anInt5568 = 4096;
			this.anInt5561 = 512;
			this.anInt5567 = 3;
		} else if (local12 == 6) {
			this.anInt5561 = 768;
			this.anInt5565 = 1280;
			this.anInt5568 = 2048;
			this.anInt5567 = 3;
		} else if (local12 == 7) {
			this.anInt5561 = 768;
			this.anInt5565 = 1280;
			this.anInt5568 = 4096;
			this.anInt5567 = 3;
		} else if (local12 == 8) {
			this.anInt5568 = 2048;
			this.anInt5561 = 1024;
			this.anInt5567 = 3;
			this.anInt5565 = 1024;
		} else if (local12 == 9) {
			this.anInt5561 = 1024;
			this.anInt5568 = 4096;
			this.anInt5567 = 3;
			this.anInt5565 = 1024;
		} else if (local12 == 14) {
			this.anInt5567 = 1;
			this.anInt5561 = 768;
			this.anInt5568 = 2048;
			this.anInt5565 = 1280;
		} else if (local12 == 15) {
			this.anInt5567 = 1;
			this.anInt5565 = 1536;
			this.anInt5568 = 4096;
			this.anInt5561 = 512;
		} else if (local12 == 16) {
			this.anInt5561 = 256;
			this.anInt5567 = 1;
			this.anInt5565 = 1792;
			this.anInt5568 = 8192;
		} else {
			this.anInt5568 = 2048;
			this.anInt5561 = 2048;
			this.anInt5565 = 0;
			this.anInt5567 = 0;
		}
	}

	@OriginalMember(owner = "client!qc", name = "a", descriptor = "(IIIIB)V")
	public void method4334(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt5565 = arg3;
		this.anInt5567 = arg0;
		this.anInt5568 = arg1;
		this.anInt5561 = arg2;
	}

	@OriginalMember(owner = "client!qc", name = "a", descriptor = "(Lclient!ya;IIIIII)V")
	private void method4338(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		this.aClass4_Sub21_2 = arg0.createPointLight(arg1, arg3, arg2, arg5, arg4, 1.0F);
	}

@OriginalMember(owner = "client!qc", name = "c", descriptor = "Ljava/lang/String;")
	public static String aString54;
@OriginalMember(owner = "client!qc", name = "h", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_117 = new Class83("Connected to update server", "Verbindung zum Update-Server hergestellt.", "Connecté au serveur de mise à jour", "Conectado ao servidor de atualização");
@OriginalMember(owner = "client!qc", name = "n", descriptor = "[I")
	public static int[] anIntArray387 = new int[1];
@OriginalMember(owner = "client!qc", name = "p", descriptor = "Z")
	public static boolean aBoolean498 = false;
@OriginalMember(owner = "client!qc", name = "a", descriptor = "([Ljava/lang/Object;[JB)V")
	public static void method4335(@OriginalArg(0) Object[] arg0, @OriginalArg(1) long[] arg1) {
		Class4_Sub6_Sub16.method3075(0, arg1, arg0, arg1.length - 1);
	}
@OriginalMember(owner = "client!qc", name = "a", descriptor = "(ILclient!cw;I)V")
	public static void method4336(@OriginalArg(0) int arg0, @OriginalArg(1) Class4_Sub12_Sub1 arg1) {
		while (true) {
			@Pc(15) Class4_Sub36 local15 = (Class4_Sub36) MusicPatchInfo.aClass183_48.method4140();
			if (local15 == null) {
				return;
			}
			@Pc(20) boolean local20 = false;
			for (@Pc(22) int local22 = 0; local22 < local15.anInt5309; local22++) {
				if (local15.aClass199Array1[local22] != null) {
					if (local15.aClass199Array1[local22].anInt5760 == 2) {
						local15.anIntArray351[local22] = -5;
					}
					if (local15.aClass199Array1[local22].anInt5760 == 0) {
						local20 = true;
					}
				}
				if (local15.aClass199Array2[local22] != null) {
					if (local15.aClass199Array2[local22].anInt5760 == 2) {
						local15.anIntArray351[local22] = -6;
					}
					if (local15.aClass199Array2[local22].anInt5760 == 0) {
						local20 = true;
					}
				}
			}
			if (local20) {
				return;
			}
			arg1.method1133(arg0);
			arg1.method2551(0);
			@Pc(99) int local99 = arg1.currentPosition;
			arg1.method2531(local15.anInt5310);
			for (@Pc(106) int local106 = 0; local106 < local15.anInt5309; local106++) {
				if (local15.anIntArray351[local106] == 0) {
					try {
						@Pc(127) int local127 = local15.anIntArray349[local106];
						@Pc(139) Field local139;
						@Pc(143) int local143;
						if (local127 == 0) {
							local139 = (Field) local15.aClass199Array1[local106].anObject29;
							local143 = local139.getInt(null);
							arg1.method2551(0);
							arg1.method2531(local143);
						} else if (local127 == 1) {
							local139 = (Field) local15.aClass199Array1[local106].anObject29;
							local139.setInt(null, local15.anIntArray350[local106]);
							arg1.method2551(0);
						} else if (local127 == 2) {
							local139 = (Field) local15.aClass199Array1[local106].anObject29;
							local143 = local139.getModifiers();
							arg1.method2551(0);
							arg1.method2531(local143);
						}
						@Pc(207) Method local207;
						if (local127 == 3) {
							local207 = (Method) local15.aClass199Array2[local106].anObject29;
							@Pc(212) byte[][] local212 = local15.aByteArrayArrayArray4[local106];
							@Pc(216) Object[] local216 = new Object[local212.length];
							for (@Pc(218) int local218 = 0; local218 < local212.length; local218++) {
								@Pc(230) ObjectInputStream local230 = new ObjectInputStream(new ByteArrayInputStream(local212[local218]));
								local216[local218] = local230.readObject();
							}
							@Pc(245) Object local245 = local207.invoke(null, local216);
							if (local245 == null) {
								arg1.method2551(0);
							} else if (local245 instanceof Number) {
								arg1.method2551(1);
								arg1.method2504(((Number) local245).longValue());
							} else if (local245 instanceof String) {
								arg1.method2551(2);
								arg1.method2518((String) local245);
							} else {
								arg1.method2551(4);
							}
						} else if (local127 == 4) {
							local207 = (Method) local15.aClass199Array2[local106].anObject29;
							local143 = local207.getModifiers();
							arg1.method2551(0);
							arg1.method2531(local143);
						}
					} catch (@Pc(313) ClassNotFoundException local313) {
						arg1.method2551(-10);
					} catch (@Pc(319) InvalidClassException local319) {
						arg1.method2551(-11);
					} catch (@Pc(325) StreamCorruptedException local325) {
						arg1.method2551(-12);
					} catch (@Pc(331) OptionalDataException local331) {
						arg1.method2551(-13);
					} catch (@Pc(337) IllegalAccessException local337) {
						arg1.method2551(-14);
					} catch (@Pc(343) IllegalArgumentException local343) {
						arg1.method2551(-15);
					} catch (@Pc(349) InvocationTargetException local349) {
						arg1.method2551(-16);
					} catch (@Pc(355) SecurityException local355) {
						arg1.method2551(-17);
					} catch (@Pc(361) IOException local361) {
						arg1.method2551(-18);
					} catch (@Pc(367) NullPointerException local367) {
						arg1.method2551(-19);
					} catch (@Pc(373) Exception local373) {
						arg1.method2551(-20);
					} catch (@Pc(379) Throwable local379) {
						arg1.method2551(-21);
					}
				} else {
					arg1.method2551(local15.anIntArray351[local106]);
				}
			}
			arg1.method2525(local99);
			arg1.method2545(arg1.currentPosition - local99);
			local15.method5684();
		}
	}
}
