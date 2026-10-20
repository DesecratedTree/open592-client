package com.jagex.game.runetek5.config.material.texture.operation;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.config.material.texture.operation.*;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
@OriginalClass("client!el")
public class TextureOpSprite extends TextureOperation {

	@OriginalMember(owner = "client!el", name = "D", descriptor = "I")
	protected int spriteHeight;

	@OriginalMember(owner = "client!el", name = "M", descriptor = "I")
	protected int spriteWidth;

	@OriginalMember(owner = "client!el", name = "O", descriptor = "[I")
	protected int[] spritePixels;

	@OriginalMember(owner = "client!el", name = "L", descriptor = "I")
	private int spriteId = -1;

	@OriginalMember(owner = "client!el", name = "<init>", descriptor = "()V")
	public TextureOpSprite() {
		super(0, false);
	}

	@OriginalMember(owner = "client!el", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[][] local11 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty && this.loadSprite()) {
			@Pc(28) int[] local28 = local11[0];
			@Pc(32) int[] local32 = local11[1];
			@Pc(36) int[] local36 = local11[2];
			@Pc(52) int local52 = (EnumTypeList.anInt4939 == this.spriteWidth ? arg0 : this.spriteWidth * arg0 / EnumTypeList.anInt4939) * this.spriteHeight;
			@Pc(62) int local62;
			@Pc(71) int local71;
			if (MidiPcmStream.anInt1616 == this.spriteHeight) {
				for (local62 = 0; local62 < MidiPcmStream.anInt1616; local62++) {
					local71 = this.spritePixels[local52++];
					local36[local62] = (local71 & 0xFF) << 4;
					local32[local62] = local71 >> 4 & 0xFF0;
					local28[local62] = local71 >> 12 & 0xFF0;
				}
			} else {
				for (local62 = 0; local62 < MidiPcmStream.anInt1616; local62++) {
					local71 = local62 * this.spriteHeight / MidiPcmStream.anInt1616;
					@Pc(79) int local79 = this.spritePixels[local52 + local71];
					local36[local62] = (local79 & 0xFF) << 4;
					local32[local62] = local79 >> 4 & 0xFF0;
					local28[local62] = local79 >> 12 & 0xFF0;
				}
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!el", name = "a", descriptor = "(I)I")
	@Override
	public final int getSpriteId() {
		return this.spriteId;
	}

	@OriginalMember(owner = "client!el", name = "e", descriptor = "(I)V")
	@Override
	public final void cacheReset() {
		super.cacheReset();
		this.spritePixels = null;
	}

	@OriginalMember(owner = "client!el", name = "g", descriptor = "(I)Z")
	protected final boolean loadSprite() {
		if (this.spritePixels != null) {
			return true;
		} else if (this.spriteId >= 0) {
			@Pc(32) SpriteType local32 = SceneLocEntity.anInt3496 >= 0 ? SpriteType.listSingle(EmissiveTriangle.aClass76_9, SceneLocEntity.anInt3496, this.spriteId) : SpriteType.method328(EmissiveTriangle.aClass76_9, this.spriteId);
			local32.expandToFullSize();
			this.spritePixels = local32.getPixels();
			this.spriteWidth = local32.height;
			this.spriteHeight = local32.width;
			return true;
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!el", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public final void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.spriteId = arg1.method2536();
		}
	}

@OriginalMember(owner = "client!el", name = "C", descriptor = "[Lclient!lk;")
	public static Class136[] aClass136Array2;
@OriginalMember(owner = "client!el", name = "N", descriptor = "I")
	public static int anInt1087 = 0;
@OriginalMember(owner = "client!el", name = "b", descriptor = "(Z)V")
	public static void method985() {
		@Pc(6) LRUCache local6 = Class4_Sub1_Sub6_Sub2.aClass68_24;
		synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
			Class4_Sub1_Sub6_Sub2.aClass68_24.method1777();
		}
	}
}
