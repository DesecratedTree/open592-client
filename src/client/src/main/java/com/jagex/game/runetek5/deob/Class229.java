package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.CacheableNode;

import java.io.IOException;
import java.net.Socket;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub8;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
@OriginalClass("client!tn")
public final class Class229 {

	@OriginalMember(owner = "client!tn", name = "k", descriptor = "Lclient!ac;")
	private CacheableNode aClass4_Sub1_58;

	@OriginalMember(owner = "client!tn", name = "d", descriptor = "Lclient!ac;")
	private final CacheableNode aClass4_Sub1_57 = new CacheableNode();

	@OriginalMember(owner = "client!tn", name = "<init>", descriptor = "()V")
	public Class229() {
		this.aClass4_Sub1_57.aClass4_Sub1_56 = this.aClass4_Sub1_57;
		this.aClass4_Sub1_57.aClass4_Sub1_55 = this.aClass4_Sub1_57;
	}

	@OriginalMember(owner = "client!tn", name = "a", descriptor = "(B)V")
	public void method5087() {
		while (true) {
			@Pc(5) CacheableNode local5 = this.aClass4_Sub1_57.aClass4_Sub1_55;
			if (this.aClass4_Sub1_57 == local5) {
				this.aClass4_Sub1_58 = null;
				return;
			}
			local5.method4662();
		}
	}

	@OriginalMember(owner = "client!tn", name = "a", descriptor = "(ILclient!ac;)V")
	public void method5088(@OriginalArg(1) CacheableNode arg0) {
		if (arg0.aClass4_Sub1_56 != null) {
			arg0.method4662();
		}
		arg0.aClass4_Sub1_55 = this.aClass4_Sub1_57;
		arg0.aClass4_Sub1_56 = this.aClass4_Sub1_57.aClass4_Sub1_56;
		arg0.aClass4_Sub1_56.aClass4_Sub1_55 = arg0;
		arg0.aClass4_Sub1_55.aClass4_Sub1_56 = arg0;
	}

	@OriginalMember(owner = "client!tn", name = "b", descriptor = "(B)Lclient!ac;")
	public CacheableNode method5089() {
		@Pc(12) CacheableNode local12 = this.aClass4_Sub1_57.aClass4_Sub1_55;
		if (local12 == this.aClass4_Sub1_57) {
			this.aClass4_Sub1_58 = null;
			return null;
		} else {
			this.aClass4_Sub1_58 = local12.aClass4_Sub1_55;
			return local12;
		}
	}

	@OriginalMember(owner = "client!tn", name = "c", descriptor = "(B)Lclient!ac;")
	public CacheableNode method5091() {
		@Pc(7) CacheableNode local7 = this.aClass4_Sub1_57.aClass4_Sub1_55;
		if (this.aClass4_Sub1_57 == local7) {
			return null;
		} else {
			local7.method4662();
			return local7;
		}
	}

	@OriginalMember(owner = "client!tn", name = "b", descriptor = "(Z)Lclient!ac;")
	public CacheableNode method5092() {
		@Pc(11) CacheableNode local11 = this.aClass4_Sub1_58;
		if (this.aClass4_Sub1_57 == local11) {
			this.aClass4_Sub1_58 = null;
			return null;
		} else {
			this.aClass4_Sub1_58 = local11.aClass4_Sub1_55;
			return local11;
		}
	}

	@OriginalMember(owner = "client!tn", name = "c", descriptor = "(Z)I")
	public int method5093() {
		@Pc(13) int local13 = 0;
		@Pc(17) CacheableNode local17 = this.aClass4_Sub1_57.aClass4_Sub1_55;
		while (local17 != this.aClass4_Sub1_57) {
			local17 = local17.aClass4_Sub1_55;
			local13++;
		}
		return local13;
	}

@OriginalMember(owner = "client!tn", name = "l", descriptor = "Lclient!gt;")
	public static com.jagex.game.runetek5.graphics.font.FontMetrics aClass89_10;
@OriginalMember(owner = "client!tn", name = "h", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_85 = new Class215(38, 8);
@OriginalMember(owner = "client!tn", name = "a", descriptor = "(IZ)V")
	public static void method5086(@OriginalArg(0) int arg0) {
		@Pc(12) Class4_Sub1_Sub11 local12 = NPCTypeList.method5222(arg0, 4);
		local12.method2608();
	}
@OriginalMember(owner = "client!tn", name = "a", descriptor = "(Z)V")
	public static void method5090() {
		if (ItemType.anInt6183 == 0) {
			return;
		}
		try {
			if (++Class4_Sub6_Sub31.anInt5976 > 2000) {
				if (QuickchatMessageTypeList.aClass11_5 != null) {
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
				}
				if (Class16_Sub4_Sub2.anInt2524 >= 1) {
					ItemType.anInt6183 = 0;
					RuntimeException_Sub1.anInt4623 = -5;
					return;
				}
				ItemType.anInt6183 = 1;
				Class4_Sub6_Sub31.anInt5976 = 0;
				if (Class189.anInt5436 == Skybox.anInt6723) {
					Skybox.anInt6723 = Class4_Sub6_Sub8.anInt1949;
				} else {
					Skybox.anInt6723 = Class189.anInt5436;
				}
				Class16_Sub4_Sub2.anInt2524++;
			}
			if (ItemType.anInt6183 == 1) {
				Class26.aClass199_3 = Class126.aClass66_4.method1728(Class191.aString54, Skybox.anInt6723);
				ItemType.anInt6183 = 2;
			}
			@Pc(120) int local120;
			if (ItemType.anInt6183 == 2) {
				if (Class26.aClass199_3.anInt5760 == 2) {
					throw new IOException();
				}
				if (Class26.aClass199_3.anInt5760 != 1) {
					return;
				}
				QuickchatMessageTypeList.aClass11_5 = new BufferedSocket((Socket) Class26.aClass199_3.anObject29, Class126.aClass66_4);
				Class26.aClass199_3 = null;
				QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				Class196.method4427();
				local120 = QuickchatMessageTypeList.aClass11_5.method139();
				Class196.method4427();
				if (local120 != 21) {
					RuntimeException_Sub1.anInt4623 = local120;
					ItemType.anInt6183 = 0;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					return;
				}
				ItemType.anInt6183 = 3;
			}
			if (ItemType.anInt6183 == 3) {
				if (QuickchatMessageTypeList.aClass11_5.method133() < 1) {
					return;
				}
				Class126.aStringArray16 = new String[QuickchatMessageTypeList.aClass11_5.method139()];
				ItemType.anInt6183 = 4;
			}
			if (ItemType.anInt6183 == 4 && QuickchatMessageTypeList.aClass11_5.method133() >= Class126.aStringArray16.length * 8) {
				Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
				QuickchatMessageTypeList.aClass11_5.method131(0, Class126.aStringArray16.length * 8, Class14.aClass4_Sub12_Sub1_3.buffer);
				for (local120 = 0; local120 < Class126.aStringArray16.length; local120++) {
					Class126.aStringArray16[local120] = Class4_Sub9.method763(Class14.aClass4_Sub12_Sub1_3.method2501());
				}
				RuntimeException_Sub1.anInt4623 = 21;
				ItemType.anInt6183 = 0;
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
			}
		} catch (@Pc(214) IOException local214) {
			if (QuickchatMessageTypeList.aClass11_5 != null) {
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
			}
			if (Class16_Sub4_Sub2.anInt2524 >= 1) {
				ItemType.anInt6183 = 0;
				RuntimeException_Sub1.anInt4623 = -4;
			} else {
				ItemType.anInt6183 = 1;
				if (Skybox.anInt6723 == Class189.anInt5436) {
					Skybox.anInt6723 = Class4_Sub6_Sub8.anInt1949;
				} else {
					Skybox.anInt6723 = Class189.anInt5436;
				}
				Class16_Sub4_Sub2.anInt2524++;
				Class4_Sub6_Sub31.anInt5976 = 0;
			}
		}
	}
}
