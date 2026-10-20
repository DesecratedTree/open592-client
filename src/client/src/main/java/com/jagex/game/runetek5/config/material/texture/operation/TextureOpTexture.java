package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.deob.Static331;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
@OriginalClass("client!jt")
public final class TextureOpTexture extends TextureOperation {

	@OriginalMember(owner = "client!jt", name = "J", descriptor = "I")
	private int textureWidth;

	@OriginalMember(owner = "client!jt", name = "K", descriptor = "I")
	private int textureHeight;

	@OriginalMember(owner = "client!jt", name = "O", descriptor = "[I")
	private int[] texturePixels;

	@OriginalMember(owner = "client!jt", name = "N", descriptor = "I")
	private int materialId = -1;

	@OriginalMember(owner = "client!jt", name = "<init>", descriptor = "()V")
	public TextureOpTexture() {
		super(0, false);
	}

	@OriginalMember(owner = "client!jt", name = "e", descriptor = "(I)V")
	@Override
	public void cacheReset() {
		super.cacheReset();
		this.texturePixels = null;
	}

	@OriginalMember(owner = "client!jt", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.materialId = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!jt", name = "c", descriptor = "(I)I")
	@Override
	public int getTextureId() {
		return this.materialId;
	}

	@OriginalMember(owner = "client!jt", name = "a", descriptor = "(IBI)V")
	@Override
	public void initCache(@OriginalArg(0) int height, @OriginalArg(2) int arg1) {
		super.initCache(height, arg1);
		if (this.materialId >= 0 && Static331.anInterface7_7 != null) {
			@Pc(27) int local27 = Static331.anInterface7_7.getMetrics(this.materialId).isSmall ? 64 : 128;
			this.texturePixels = Static331.anInterface7_7.getPixels(false, this.materialId, 1.0F, local27, local27);
			this.textureHeight = local27;
			this.textureWidth = local27;
		}
	}

	@OriginalMember(owner = "client!jt", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(41) int local41 = (EnumTypeList.anInt4939 == this.textureHeight ? arg0 : arg0 * this.textureHeight / EnumTypeList.anInt4939) * this.textureWidth;
			@Pc(45) int[] local45 = local11[0];
			@Pc(49) int[] local49 = local11[1];
			@Pc(53) int[] local53 = local11[2];
			@Pc(63) int local63;
			@Pc(71) int local71;
			if (MidiPcmStream.anInt1616 == this.textureWidth) {
				for (local63 = 0; local63 < MidiPcmStream.anInt1616; local63++) {
					local71 = this.texturePixels[local41++];
					local53[local63] = (local71 & 0xFF) << 4;
					local49[local63] = local71 >> 4 & 0xFF0;
					local45[local63] = local71 >> 12 & 0xFF0;
				}
			} else {
				for (local63 = 0; local63 < MidiPcmStream.anInt1616; local63++) {
					local71 = local63 * this.textureWidth / MidiPcmStream.anInt1616;
					@Pc(119) int local119 = this.texturePixels[local71 + local41];
					local53[local63] = (local119 & 0xFF) << 4;
					local49[local63] = local119 >> 4 & 0xFF0;
					local45[local63] = local119 >> 12 & 0xFF0;
				}
			}
		}
		return local11;
	}

@OriginalMember(owner = "client!jt", name = "L", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_44;
@OriginalMember(owner = "client!jt", name = "D", descriptor = "Lclient!jk;")
	public static final Game aClass121_1 = new Game("runescape", 0);
}
