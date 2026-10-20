package com.jagex.game.runetek5.config.mapsprite;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;

import com.jagex.game.runetek5.config.mapsprite.*;
@OriginalClass("client!co")
public final class MapSpriteType {

	@OriginalMember(owner = "client!co", name = "b", descriptor = "I")
	public int fileId;

	@OriginalMember(owner = "client!co", name = "e", descriptor = "I")
	public int backgroundColor;

	@OriginalMember(owner = "client!co", name = "k", descriptor = "Lclient!wi;")
	public MapSpriteTypeList mapspriteTypeList;

	@OriginalMember(owner = "client!co", name = "h", descriptor = "Z")
	public boolean requiresUpscaling = false;

	@OriginalMember(owner = "client!co", name = "a", descriptor = "(ZLclient!ya;II)Lclient!l;")
	public NativeSprite method937(@OriginalArg(0) boolean arg0, @OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(2) int arg2) {
		@Pc(24) long local24 = (long) (arg1.index << 19 | (arg0 ? 262144 : 0) | this.fileId | arg2 << 16);
		@Pc(32) NativeSprite local32 = (NativeSprite) this.mapspriteTypeList.mapspriteCache.method1787(local24);
		if (local32 != null) {
			return local32;
		} else if (this.mapspriteTypeList.aClass76_103.method2125(this.fileId)) {
			@Pc(61) SpriteType local61 = SpriteType.listSingle(this.mapspriteTypeList.aClass76_103, this.fileId, 0);
			if (local61 != null) {
				local61.borderY = local61.minX = local61.borderX = local61.minY = 0;
				if (arg0) {
					local61.flipVertical();
				}
				for (@Pc(81) int local81 = 0; local81 < arg2; local81++) {
					local61.rotate90DegLeft();
				}
			}
			local32 = arg1.generateSprite(local61);
			if (local32 != null) {
				this.mapspriteTypeList.mapspriteCache.method1779(local24, local32);
			}
			return local32;
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!co", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method938(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(17) int local17 = arg0.method2490();
			if (local17 == 0) {
				return;
			}
			this.method939(local17, arg0);
		}
	}

	@OriginalMember(owner = "client!co", name = "a", descriptor = "(ILclient!iv;I)V")
	private void method939(@OriginalArg(0) int arg0, @OriginalArg(1) Packet arg1) {
		if (arg0 == 1) {
			this.fileId = arg1.method2536();
		} else if (arg0 == 2) {
			this.backgroundColor = arg1.method2493();
			return;
		} else if (arg0 == 3) {
			this.requiresUpscaling = true;
			return;
		} else if (arg0 == 4) {
			this.fileId = -1;
			return;
		}
	}

	@OriginalMember(owner = "client!co", name = "a", descriptor = "(B)Z")
	public boolean method941() {
		return this.mapspriteTypeList.aClass76_103.method2125(this.fileId);
	}

@OriginalMember(owner = "client!co", name = "a", descriptor = "Lclient!dv;")
	public static MidiPcmStream aClass4_Sub15_Sub1_1;
@OriginalMember(owner = "client!co", name = "f", descriptor = "I")
	public static int anInt1028 = -1;
}
