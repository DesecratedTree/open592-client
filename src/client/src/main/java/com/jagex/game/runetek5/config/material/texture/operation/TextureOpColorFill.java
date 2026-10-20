package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!bj")
public final class TextureOpColorFill extends TextureOperation {

	@OriginalMember(owner = "client!bj", name = "C", descriptor = "I")
	private int redComponent;

	@OriginalMember(owner = "client!bj", name = "E", descriptor = "I")
	private int anInt586;

	@OriginalMember(owner = "client!bj", name = "G", descriptor = "I")
	private int anInt588;

	static {
		new Class83("Please wait until you are logged out of your previous channel.", "Bitte warte, bis du den vorherigen Chatraum verlassen hast.", "Veuillez attendre d'être déconnecté(e) de votre canal précédent.", "Aguarde até se desconectar do canal anterior.");
	}

	@OriginalMember(owner = "client!bj", name = "<init>", descriptor = "(I)V")
	private TextureOpColorFill(@OriginalArg(0) int rgb) {
		super(0, false);
		this.method620(rgb);
	}

	@OriginalMember(owner = "client!bj", name = "<init>", descriptor = "()V")
	public TextureOpColorFill() {
		this(0);
	}

	@OriginalMember(owner = "client!bj", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.method620(arg1.method2493());
		}
	}

	@OriginalMember(owner = "client!bj", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(24) int[] local24 = local11[0];
			@Pc(28) int[] local28 = local11[1];
			@Pc(32) int[] local32 = local11[2];
			for (@Pc(34) int local34 = 0; local34 < MidiPcmStream.anInt1616; local34++) {
				local24[local34] = this.redComponent;
				local28[local34] = this.anInt588;
				local32[local34] = this.anInt586;
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!bj", name = "a", descriptor = "(IB)V")
	private void method620(@OriginalArg(0) int arg0) {
		this.anInt588 = arg0 >> 4 & 0xFF0;
		this.redComponent = arg0 >> 12 & 0xFF0;
		this.anInt586 = (arg0 & 0xFF) << 4;
	}

@OriginalMember(owner = "client!bj", name = "H", descriptor = "[I")
	public static final int[] anIntArray35 = new int[1000];
@OriginalMember(owner = "client!bj", name = "a", descriptor = "(Lclient!cw;I)V")
	public static void method617(@OriginalArg(0) Class4_Sub12_Sub1 arg0) {
		@Pc(7) int local7 = 0;
		arg0.method1136();
		@Pc(20) int local20;
		@Pc(48) int local48;
		for (@Pc(14) int local14 = 0; local14 < Class4_Sub7.anInt606; local14++) {
			local20 = Static99.anIntArray157[local14];
			if ((SpriteRenderNode.aByteArray63[local20] & 0x1) == 0) {
				if (local7 > 0) {
					local7--;
					SpriteRenderNode.aByteArray63[local20] = (byte) (SpriteRenderNode.aByteArray63[local20] | 0x2);
				} else {
					local48 = arg0.method1143(1);
					if (local48 == 0) {
						local7 = Component.method5308(arg0);
						SpriteRenderNode.aByteArray63[local20] = (byte) (SpriteRenderNode.aByteArray63[local20] | 0x2);
					} else {
						CubeMapSpecularEffect.method2702(arg0, local20);
					}
				}
			}
		}
		arg0.method1135();
		if (local7 != 0) {
			throw new RuntimeException("nsn0");
		}
		arg0.method1136();
		@Pc(131) int local131;
		for (local20 = 0; local20 < Class4_Sub7.anInt606; local20++) {
			local48 = Static99.anIntArray157[local20];
			if ((SpriteRenderNode.aByteArray63[local48] & 0x1) != 0) {
				if (local7 > 0) {
					SpriteRenderNode.aByteArray63[local48] = (byte) (SpriteRenderNode.aByteArray63[local48] | 0x2);
					local7--;
				} else {
					local131 = arg0.method1143(1);
					if (local131 == 0) {
						local7 = Component.method5308(arg0);
						SpriteRenderNode.aByteArray63[local48] = (byte) (SpriteRenderNode.aByteArray63[local48] | 0x2);
					} else {
						CubeMapSpecularEffect.method2702(arg0, local48);
					}
				}
			}
		}
		arg0.method1135();
		if (local7 != 0) {
			throw new RuntimeException("nsn1");
		}
		arg0.method1136();
		@Pc(213) int local213;
		for (local48 = 0; local48 < EnumType.anInt5761; local48++) {
			local131 = Class189.anIntArray374[local48];
			if ((SpriteRenderNode.aByteArray63[local131] & 0x1) != 0) {
				if (local7 > 0) {
					local7--;
					SpriteRenderNode.aByteArray63[local131] = (byte) (SpriteRenderNode.aByteArray63[local131] | 0x2);
				} else {
					local213 = arg0.method1143(1);
					if (local213 == 0) {
						local7 = Component.method5308(arg0);
						SpriteRenderNode.aByteArray63[local131] = (byte) (SpriteRenderNode.aByteArray63[local131] | 0x2);
					} else if (Class244.method5490(arg0, local131)) {
						SpriteRenderNode.aByteArray63[local131] = (byte) (SpriteRenderNode.aByteArray63[local131] | 0x2);
					}
				}
			}
		}
		arg0.method1135();
		if (local7 != 0) {
			throw new RuntimeException("nsn2");
		}
		arg0.method1136();
		for (local131 = 0; local131 < EnumType.anInt5761; local131++) {
			local213 = Class189.anIntArray374[local131];
			if ((SpriteRenderNode.aByteArray63[local213] & 0x1) == 0) {
				if (local7 > 0) {
					SpriteRenderNode.aByteArray63[local213] = (byte) (SpriteRenderNode.aByteArray63[local213] | 0x2);
					local7--;
				} else {
					@Pc(299) int local299 = arg0.method1143(1);
					if (local299 == 0) {
						local7 = Component.method5308(arg0);
						SpriteRenderNode.aByteArray63[local213] = (byte) (SpriteRenderNode.aByteArray63[local213] | 0x2);
					} else if (Class244.method5490(arg0, local213)) {
						SpriteRenderNode.aByteArray63[local213] = (byte) (SpriteRenderNode.aByteArray63[local213] | 0x2);
					}
				}
			}
		}
		arg0.method1135();
		if (local7 != 0) {
			throw new RuntimeException("nsn3");
		}
		EnumType.anInt5761 = 0;
		Class4_Sub7.anInt606 = 0;
		for (local213 = 1; local213 < 2048; local213++) {
			SpriteRenderNode.aByteArray63[local213] = (byte) (SpriteRenderNode.aByteArray63[local213] >> 1);
			@Pc(369) PlayerEntity local369 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local213];
			if (local369 == null) {
				Class189.anIntArray374[EnumType.anInt5761++] = local213;
			} else {
				Static99.anIntArray157[Class4_Sub7.anInt606++] = local213;
			}
		}
	}
@OriginalMember(owner = "client!bj", name = "a", descriptor = "(IFIIZI[FIII)V")
	public static void method619(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) float[] arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8) {
		@Pc(5) int local5 = arg0 - arg8;
		@Pc(13) int local13 = arg4 - arg7;
		@Pc(21) int local21 = arg2 - arg3;
		@Pc(42) float local42 = arg5[2] * (float) local5 + (float) local13 * arg5[1] + arg5[0] * (float) local21;
		@Pc(63) float local63 = (float) local5 * arg5[5] + arg5[4] * (float) local13 + (float) local21 * arg5[3];
		@Pc(84) float local84 = arg5[8] * (float) local5 + arg5[7] * (float) local13 + (float) local21 * arg5[6];
		@Pc(99) float local99 = (float) Math.sqrt((double) (local84 * local84 + local63 * local63 + local42 * local42));
		@Pc(110) float local110 = (float) Math.atan2((double) local42, (double) local84) / 6.2831855F + 0.5F;
		@Pc(123) float local123 = (float) Math.asin((double) (local63 / local99)) / 3.1415927F + arg1 + 0.5F;
		@Pc(130) float local130;
		if (arg6 == 1) {
			local130 = local110;
			local110 = -local123;
			local123 = local130;
		} else if (arg6 == 2) {
			local123 = -local123;
			local110 = -local110;
		} else if (arg6 == 3) {
			local130 = local110;
			local110 = local123;
			local123 = -local130;
		}
		Class16_Sub2.aFloat143 = local123;
		Class62.aFloat49 = local110;
	}
}
