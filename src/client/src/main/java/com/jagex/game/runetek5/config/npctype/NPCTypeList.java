package com.jagex.game.runetek5.config.npctype;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.k;
import com.jagex.game.runetek5.config.npctype.*;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.graphics.model.GlModel;
@OriginalClass("client!uk")
public final class NPCTypeList {

	@OriginalMember(owner = "client!uk", name = "s", descriptor = "I")
	public int featureMask;

	@OriginalMember(owner = "client!uk", name = "h", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_48 = new LRUCache(64);

	@OriginalMember(owner = "client!uk", name = "p", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_49 = new LRUCache(50);

	@OriginalMember(owner = "client!uk", name = "r", descriptor = "Lclient!fa;")
	public final LRUCache aClass68_50 = new LRUCache(5);

	@OriginalMember(owner = "client!uk", name = "k", descriptor = "Z")
	public boolean allowMembers;

	@OriginalMember(owner = "client!uk", name = "j", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider configClient;

	@OriginalMember(owner = "client!uk", name = "n", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider meshes;

	static {
		new Class83("Login to a members' server to use this object.", "Du musst auf einer Mitglieder-Welt sein, um diesen Gegenstand zu benutzen.", "Connectez-vous à un serveur d'abonnés pour utiliser cet objet.", "Acesse um servidor para membros para usar este objeto.");
	}

	@OriginalMember(owner = "client!uk", name = "<init>", descriptor = "(Lclient!jk;IZLclient!fs;Lclient!fs;)V")
	public NPCTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) boolean allowMembers, @OriginalArg(3) Js5ResourceProvider configClient, @OriginalArg(4) Js5ResourceProvider meshes) {
		this.allowMembers = allowMembers;
		this.configClient = configClient;
		this.meshes = meshes;
		if (this.configClient != null) {
			@Pc(38) int local38 = this.configClient.method2100() - 1;
			this.configClient.method2108(local38);
		}
	}

	@OriginalMember(owner = "client!uk", name = "a", descriptor = "(B)V")
	public void method5221() {
		@Pc(2) LRUCache local2 = this.aClass68_48;
		synchronized (this.aClass68_48) {
			this.aClass68_48.method1791();
		}
		local2 = this.aClass68_49;
		synchronized (this.aClass68_49) {
			this.aClass68_49.method1791();
		}
		local2 = this.aClass68_50;
		synchronized (this.aClass68_50) {
			this.aClass68_50.method1791();
		}
	}

	@OriginalMember(owner = "client!uk", name = "a", descriptor = "(I)V")
	public void method5223() {
		@Pc(6) LRUCache local6 = this.aClass68_49;
		synchronized (this.aClass68_49) {
			this.aClass68_49.method1777();
		}
		local6 = this.aClass68_50;
		synchronized (this.aClass68_50) {
			this.aClass68_50.method1777();
		}
	}

	@OriginalMember(owner = "client!uk", name = "b", descriptor = "(II)V")
	public void method5225(@OriginalArg(0) int arg0) {
		this.featureMask = arg0;
		@Pc(9) LRUCache local9 = this.aClass68_49;
		synchronized (this.aClass68_49) {
			this.aClass68_49.method1777();
		}
		local9 = this.aClass68_50;
		synchronized (this.aClass68_50) {
			this.aClass68_50.method1777();
		}
	}

	@OriginalMember(owner = "client!uk", name = "a", descriptor = "(Z)V")
	public void method5226() {
		@Pc(6) LRUCache local6 = this.aClass68_48;
		synchronized (this.aClass68_48) {
			this.aClass68_48.method1777();
		}
		local6 = this.aClass68_49;
		synchronized (this.aClass68_49) {
			this.aClass68_49.method1777();
		}
		local6 = this.aClass68_50;
		synchronized (this.aClass68_50) {
			this.aClass68_50.method1777();
		}
	}

	@OriginalMember(owner = "client!uk", name = "d", descriptor = "(II)Lclient!wh;")
	public NPCType method5229(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_48;
		@Pc(16) NPCType local16;
		synchronized (this.aClass68_48) {
			local16 = (NPCType) this.aClass68_48.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(37) byte[] local37 = this.configClient.method2104(FloType.method2820(arg0), ItemType.method4785(arg0));
		local16 = new NPCType();
		local16.id = arg0;
		local16.npcTypeList = this;
		if (local37 != null) {
			local16.method5581(new Packet(local37));
		}
		local16.method5576();
		@Pc(62) LRUCache local62 = this.aClass68_48;
		synchronized (this.aClass68_48) {
			this.aClass68_48.method1779((long) arg0, local16);
			return local16;
		}
	}

	@OriginalMember(owner = "client!uk", name = "a", descriptor = "(ZI)V")
	public void method5230(@OriginalArg(0) boolean arg0) {
		if (arg0 != this.allowMembers) {
			this.allowMembers = arg0;
			this.method5226();
		}
	}

	@OriginalMember(owner = "client!uk", name = "a", descriptor = "(BI)V")
	public void method5232() {
		@Pc(6) LRUCache local6 = this.aClass68_48;
		synchronized (this.aClass68_48) {
			this.aClass68_48.method1788(5);
		}
		local6 = this.aClass68_49;
		synchronized (this.aClass68_49) {
			this.aClass68_49.method1788(5);
		}
		local6 = this.aClass68_50;
		synchronized (this.aClass68_50) {
			this.aClass68_50.method1788(5);
		}
	}

@OriginalMember(owner = "client!uk", name = "l", descriptor = "Lclient!rl;")
	public static Class204 aClass204_1;
@OriginalMember(owner = "client!uk", name = "o", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_14 = new Class21(9, 2);
@OriginalMember(owner = "client!uk", name = "q", descriptor = "I")
	public static int anInt6682 = 0;
@OriginalMember(owner = "client!uk", name = "a", descriptor = "(IBI)Lclient!hp;")
	public static Class4_Sub1_Sub11 method5222(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(20) Class4_Sub1_Sub11 local20 = (Class4_Sub1_Sub11) ReflectionCheck.aClass102_4.method2700((long) arg1 << 32 | (long) arg0);
		if (local20 == null) {
			local20 = new Class4_Sub1_Sub11(arg1, arg0);
			ReflectionCheck.aClass102_4.method2703(local20.aLong224, local20);
		}
		return local20;
	}
@OriginalMember(owner = "client!uk", name = "a", descriptor = "(II)V")
	public static void method5224(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[0][arg0][arg1];
		for (@Pc(9) int local9 = 0; local9 < 3; local9++) {
			@Pc(28) Class164 local28 = Class70.aClass164ArrayArrayArray5[local9][arg0][arg1] = Class70.aClass164ArrayArrayArray5[local9 + 1][arg0][arg1];
			if (local28 != null) {
				local28.aByte54--;
				for (@Pc(40) Class7 local40 = local28.aClass7_3; local40 != null; local40 = local40.aClass7_1) {
					@Pc(44) Class16_Sub1 local44 = local40.aClass16_Sub1_1;
					if (local44.aShort94 == arg0 && local44.aShort96 == arg1) {
						local44.aByte82--;
					}
				}
			}
		}
		if (Class70.aClass164ArrayArrayArray5[0][arg0][arg1] == null) {
			Class70.aClass164ArrayArrayArray5[0][arg0][arg1] = new Class164(0, arg0, arg1);
			Class70.aClass164ArrayArrayArray5[0][arg0][arg1].aByte53 = 1;
		}
		Class70.aClass164ArrayArrayArray5[0][arg0][arg1].aClass164_1 = local7;
		Class70.aClass164ArrayArrayArray5[3][arg0][arg1] = null;
	}
@OriginalMember(owner = "client!uk", name = "c", descriptor = "(II)V")
	public static void method5228(@OriginalArg(1) int arg0) {
		@Pc(1) Shadow local1 = null;
		for (@Pc(3) int local3 = 1; local3 < arg0; local3++) {
			@Pc(8) Class65 local8 = Class4_Sub6_Sub4.aClass65Array1[local3];
			if (local8 != null) {
				for (@Pc(12) int local12 = 0; local12 < GlModel.anInt3361; local12++) {
					for (@Pc(15) int local15 = 0; local15 < GpuFaceNormal.menuOpenWidth; local15++) {
						local1 = local8.k(local15, local12, local1);
						if (local1 != null) {
							@Pc(28) int local28 = local15 << Static231.anInt4434;
							@Pc(32) int local32 = local12 << Static231.anInt4434;
							for (@Pc(36) int local36 = local3 - 1; local36 >= 0; local36--) {
								@Pc(41) Class65 local41 = Class4_Sub6_Sub4.aClass65Array1[local36];
								if (local41 != null) {
									@Pc(53) int local53 = local8.l(local15, local12) - local41.l(local15, local12);
									@Pc(67) int local67 = local8.l(local15 + 1, local12) - local41.l(local15 + 1, local12);
									@Pc(85) int local85 = local8.l(local15 + 1, local12 + 1) - local41.l(local15 + 1, local12 + 1);
									@Pc(99) int local99 = local8.l(local15, local12 + 1) - local41.l(local15, local12 + 1);
									local41.b(local1, local28, (local53 + local67 + local85 + local99) / 4, local32, 0, false);
								}
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!uk", name = "b", descriptor = "(ZI)V")
	public static void method5231(@OriginalArg(1) int arg0) {
		Js5TextureSource.anInt2835 = 3;
		TextureOpColorGradient.anInt1763 = arg0;
		Class4_Sub6_Sub35.anInt6662 = -1;
		WaterfallEffect.anInt5136 = 100;
	}
}
