package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Class4_Sub18;
@OriginalClass("client!cs")
public final class TextureOpTiledSprite extends TextureOpSprite {

	@OriginalMember(owner = "client!cs", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty && this.loadSprite()) {
			@Pc(23) int[] local23 = local11[0];
			@Pc(27) int[] local27 = local11[1];
			@Pc(31) int[] local31 = local11[2];
			@Pc(39) int local39 = super.spriteWidth * (arg0 % super.spriteWidth);
			for (@Pc(41) int local41 = 0; local41 < MidiPcmStream.anInt1616; local41++) {
				@Pc(53) int local53 = super.spritePixels[local39 + local41 % super.spriteHeight];
				local31[local41] = (local53 & 0xFF) << 4;
				local27[local41] = local53 >> 4 & 0xFF0;
				local23[local41] = local53 >> 12 & 0xFF0;
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!cs", name = "E", descriptor = "I")
	public static int anInt1088;
@OriginalMember(owner = "client!cs", name = "Q", descriptor = "I")
	public static int anInt1090;
@OriginalMember(owner = "client!cs", name = "R", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array3;
@OriginalMember(owner = "client!cs", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void method988(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8) {
		if (!Class4_Sub18.method2034(arg3)) {
			return;
		}
		if (LocEntity.aClass247ArrayArray1[arg3] == null) {
			client.method901(Class4_Sub36.aClass247ArrayArray2[arg3], -1, arg0, arg4, arg2, arg6, arg5, arg1, arg8, arg7);
		} else {
			client.method901(LocEntity.aClass247ArrayArray1[arg3], -1, arg0, arg4, arg2, arg6, arg5, arg1, arg8, arg7);
		}
	}
}
