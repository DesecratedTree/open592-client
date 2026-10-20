package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static433;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchPcmStream;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
@OriginalClass("client!sc")
public final class LegacyMouseManager extends Class9 {

	@OriginalMember(owner = "client!sc", name = "<init>", descriptor = "(Lclient!fd;)V")
	public LegacyMouseManager(@OriginalArg(0) GlToolkit arg0) {
		super(arg0);
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		super.aClass19_Sub2_41.setActiveTexture(arg1);
		super.aClass19_Sub2_41.method1900(arg0);
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		super.aClass19_Sub2_41.method1966(false);
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean arg0) {
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

	@OriginalMember(owner = "client!sc", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		super.aClass19_Sub2_41.method1966(true);
	}

@OriginalMember(owner = "client!sc", name = "l", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_81;
@OriginalMember(owner = "client!sc", name = "q", descriptor = "I")
	public static int anInt6220;
@OriginalMember(owner = "client!sc", name = "n", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_43 = new LRUCache(64);
@OriginalMember(owner = "client!sc", name = "a", descriptor = "(ILclient!iv;I)Ljava/lang/String;")
	public static String method4831(@OriginalArg(1) Packet arg0) {
		try {
			@Pc(7) int local7 = arg0.method2506();
			if (local7 > 32767) {
				local7 = 32767;
			}
			@Pc(19) byte[] local19 = new byte[local7];
			arg0.currentPosition += JavaFallbackSurface.aClass119_1.method2933(arg0.buffer, 0, local19, local7, arg0.currentPosition);
			return SpriteGL.method5293(local19, local7, 0);
		} catch (@Pc(45) Exception local45) {
			return "Cabbage";
		}
	}
@OriginalMember(owner = "client!sc", name = "b", descriptor = "(Z)Z")
	public static boolean method4833(@OriginalArg(0) boolean arg0) {
		for (@Pc(1) int local1 = MusicPatchPcmStream.anInt6251; local1 < RemoteFileWorker.anInt4487; local1++) {
			@Pc(6) Class164[][] local6 = Class70.aClass164ArrayArrayArray5[local1];
			for (@Pc(9) int local9 = -FileOnDisk.anInt4431; local9 <= 0; local9++) {
				@Pc(14) int local14 = QuickChatPhrase.anInt7073 + local9;
				@Pc(18) int local18 = QuickChatPhrase.anInt7073 - local9;
				if (local14 >= Static105.anInt2325 || local18 < Class30.anInt775) {
					for (@Pc(27) int local27 = -FileOnDisk.anInt4431; local27 <= 0; local27++) {
						@Pc(32) int local32 = OpenGlArbElementArrayBuffer.anInt1887 + local27;
						@Pc(36) int local36 = OpenGlArbElementArrayBuffer.anInt1887 - local27;
						@Pc(48) Class164 local48;
						if (local14 >= Static105.anInt2325) {
							if (local32 >= Class149.anInt4530) {
								local48 = local6[local14][local32];
								if (local48 != null && local48.aBoolean464) {
									QuickChatValueType.aBoolean269 = arg0;
									Static433.aClass70_1.method5080(local48);
									Static433.aClass70_1.method5074();
								}
							}
							if (local36 < Class4_Sub1_Sub11.anInt3087) {
								local48 = local6[local14][local36];
								if (local48 != null && local48.aBoolean464) {
									QuickChatValueType.aBoolean269 = arg0;
									Static433.aClass70_1.method5080(local48);
									Static433.aClass70_1.method5074();
								}
							}
						}
						if (local18 < Class30.anInt775) {
							if (local32 >= Class149.anInt4530) {
								local48 = local6[local18][local32];
								if (local48 != null && local48.aBoolean464) {
									QuickChatValueType.aBoolean269 = arg0;
									Static433.aClass70_1.method5080(local48);
									Static433.aClass70_1.method5074();
								}
							}
							if (local36 < Class4_Sub1_Sub11.anInt3087) {
								local48 = local6[local18][local36];
								if (local48 != null && local48.aBoolean464) {
									QuickChatValueType.aBoolean269 = arg0;
									Static433.aClass70_1.method5080(local48);
									Static433.aClass70_1.method5074();
								}
							}
						}
						if (OpenGl2DImageTexture.anInt5476 == 0) {
							if (Class4_Sub6_Sub26.aBoolean470) {
								Static433.aClass70_1.method5077(24);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}
@OriginalMember(owner = "client!sc", name = "c", descriptor = "(I)V")
	public static void method4834() {
		if (GLBillowTextureData.aBoolean509) {
			GlVboElementsArrayBuffer.aClass57_23 = null;
			Class58.aClass57_10 = null;
			GLBillowTextureData.aBoolean509 = false;
		}
	}
}
