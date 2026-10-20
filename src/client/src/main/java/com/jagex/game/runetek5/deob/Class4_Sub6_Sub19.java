package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!kp")
public final class Class4_Sub6_Sub19 extends TextureOperation {

	@OriginalMember(owner = "client!kp", name = "C", descriptor = "I")
	private int anInt3950 = 4096;

	@OriginalMember(owner = "client!kp", name = "E", descriptor = "I")
	private int anInt3952 = 4096;

	@OriginalMember(owner = "client!kp", name = "K", descriptor = "I")
	private int anInt3957 = 4096;

	@OriginalMember(owner = "client!kp", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub19() {
		super(1, false);
	}

	@OriginalMember(owner = "client!kp", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt3950 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt3957 = arg1.method2536();
		} else if (arg0 == 2) {
			this.anInt3952 = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!kp", name = "a", descriptor = "(BI)[[I")
	@Override
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[][] local19 = super.colorImageCache.get(arg0);
		if (super.colorImageCache.dirty) {
			@Pc(29) int[][] local29 = this.getChildColorOutput(0, arg0);
			@Pc(33) int[] local33 = local29[0];
			@Pc(37) int[] local37 = local29[1];
			@Pc(41) int[] local41 = local29[2];
			@Pc(45) int[] local45 = local19[0];
			@Pc(49) int[] local49 = local19[1];
			@Pc(53) int[] local53 = local19[2];
			for (@Pc(55) int local55 = 0; local55 < MidiPcmStream.anInt1616; local55++) {
				@Pc(61) int local61 = local33[local55];
				@Pc(65) int local65 = local41[local55];
				@Pc(69) int local69 = local37[local55];
				if (local65 == local61 && local69 == local65) {
					local45[local55] = local61 * this.anInt3950 >> 12;
					local49[local55] = this.anInt3957 * local65 >> 12;
					local53[local55] = local69 * this.anInt3952 >> 12;
				} else {
					local45[local55] = this.anInt3950;
					local49[local55] = this.anInt3957;
					local53[local55] = this.anInt3952;
				}
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!kp", name = "H", descriptor = "I")
	public static int anInt3954;
@OriginalMember(owner = "client!kp", name = "J", descriptor = "I")
	public static int anInt3956 = 0;
@OriginalMember(owner = "client!kp", name = "c", descriptor = "(B)V")
	public static void method3223() {
		if (Class48_Sub1_Sub1.anInt4882 < 102) {
			Class48_Sub1_Sub1.anInt4882 += 6;
		}
		if (PointLight.anInt3862 != 0) {
			Static167.anInt3231 -= PointLight.anInt3862 * 5;
			if (OpenGlArbElementArrayBuffer.anInt1881 <= Static167.anInt3231) {
				Static167.anInt3231 = OpenGlArbElementArrayBuffer.anInt1881 - 1;
			}
			PointLight.anInt3862 = 0;
			if (Static167.anInt3231 < 0) {
				Static167.anInt3231 = 0;
			}
		}
		for (@Pc(44) int local44 = 0; local44 < Class4_Sub6_Sub15.anInt3602; local44++) {
			@Pc(52) int local52 = Class16_Sub2.aClass30Array4[local44].method746();
			@Pc(58) char local58 = Class16_Sub2.aClass30Array4[local44].method749();
			if (local52 == 84) {
				JavaThreadResource.method997();
			} else if (Static384.aClass244_1.method5489(82) && local52 == 66) {
				if (Class4_Sub6_Sub31.aClipboard1 != null) {
					@Pc(73) String local73 = "";
					for (@Pc(78) int local78 = Static102.aStringArray8.length - 1; local78 >= 0; local78--) {
						if (Static102.aStringArray8[local78] != null && Static102.aStringArray8[local78].length() > 0) {
							local73 = local73 + Static102.aStringArray8[local78] + '\n';
						}
					}
					Class4_Sub6_Sub31.aClipboard1.setContents(new StringSelection(local73), null);
				}
			} else if (Static384.aClass244_1.method5489(82) && local52 == 67) {
				if (Class4_Sub6_Sub31.aClipboard1 != null) {
					@Pc(343) Transferable local343 = Class4_Sub6_Sub31.aClipboard1.getContents(null);
					if (local343 != null) {
						try {
							@Pc(350) String local350 = (String) local343.getTransferData(DataFlavor.stringFlavor);
							if (local350 != null) {
								@Pc(357) String[] local357 = CursorType.method2058(local350, '\n');
								if (local357.length <= 1) {
									Class4_Sub19.aString29 = Class4_Sub19.aString29 + local350;
								} else {
									for (@Pc(374) int local374 = 0; local374 < local357.length; local374++) {
										Class4_Sub19.aString29 = local357[local374];
										JavaThreadResource.method997();
									}
								}
							}
						} catch (@Pc(393) Exception local393) {
						}
					}
				}
			} else if (local52 == 85 && CacheableNodeByteArray.anInt4511 > 0) {
				Class4_Sub19.aString29 = Class4_Sub19.aString29.substring(0, CacheableNodeByteArray.anInt4511 - 1) + Class4_Sub19.aString29.substring(CacheableNodeByteArray.anInt4511);
				CacheableNodeByteArray.anInt4511--;
			} else if (local52 == 101 && CacheableNodeByteArray.anInt4511 < Class4_Sub19.aString29.length()) {
				Class4_Sub19.aString29 = Class4_Sub19.aString29.substring(0, CacheableNodeByteArray.anInt4511) + Class4_Sub19.aString29.substring(CacheableNodeByteArray.anInt4511 + 1);
			} else if (local52 == 96 && CacheableNodeByteArray.anInt4511 > 0) {
				CacheableNodeByteArray.anInt4511--;
			} else if (local52 == 97 && CacheableNodeByteArray.anInt4511 < Class4_Sub19.aString29.length()) {
				CacheableNodeByteArray.anInt4511++;
			} else if (local52 == 102) {
				CacheableNodeByteArray.anInt4511 = 0;
			} else if (local52 == 103) {
				CacheableNodeByteArray.anInt4511 = Class4_Sub19.aString29.length();
			} else if (local52 == 104 && Class16.anInt6887 < Static102.aStringArray8.length) {
				Class16.anInt6887++;
				Toolkit.method4270();
				CacheableNodeByteArray.anInt4511 = Class4_Sub19.aString29.length();
			} else if (local52 == 105 && Class16.anInt6887 > 0) {
				Class16.anInt6887--;
				Toolkit.method4270();
				CacheableNodeByteArray.anInt4511 = Class4_Sub19.aString29.length();
			} else if (TextureType.method3280(local58) || local58 == ':' || local58 == ',' || local58 == ' ' || local58 == '_' || local58 == '-' || local58 == '+' || local58 == '[' || local58 == ']') {
				Class4_Sub19.aString29 = Class4_Sub19.aString29.substring(0, CacheableNodeByteArray.anInt4511) + Class16_Sub2.aClass30Array4[local44].method749() + Class4_Sub19.aString29.substring(CacheableNodeByteArray.anInt4511);
				CacheableNodeByteArray.anInt4511++;
			}
		}
		Class4_Sub6_Sub15.anInt3602 = 0;
		ModeWhere.method4940();
	}
}
