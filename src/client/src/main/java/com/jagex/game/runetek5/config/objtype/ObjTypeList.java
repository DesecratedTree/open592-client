package com.jagex.game.runetek5.config.objtype;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Static32;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.config.objtype.*;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.deob.Class225;
@OriginalClass("client!ro")
public final class ObjTypeList {

	@OriginalMember(owner = "client!ro", name = "C", descriptor = "I")
	public int featureMask;

	@OriginalMember(owner = "client!ro", name = "c", descriptor = "Lclient!fa;")
	private final LRUCache typeCache = new LRUCache(64);

	@OriginalMember(owner = "client!ro", name = "w", descriptor = "Lclient!fa;")
	public final LRUCache modelCache = new LRUCache(50);

	@OriginalMember(owner = "client!ro", name = "x", descriptor = "Lclient!sq;")
	public final SpriteDrawQueue spriteCache = new SpriteDrawQueue(250);

	@OriginalMember(owner = "client!ro", name = "y", descriptor = "Lclient!mt;")
	private final Class154 aClass154_1 = new Class154();

	@OriginalMember(owner = "client!ro", name = "A", descriptor = "Lclient!ph;")
	private final Class180 aClass180_2;

	@OriginalMember(owner = "client!ro", name = "n", descriptor = "Lclient!fs;")
	public final Js5ResourceProvider aClass76_77;

	@OriginalMember(owner = "client!ro", name = "r", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider configClient;

	@OriginalMember(owner = "client!ro", name = "u", descriptor = "Z")
	private boolean allowMembers;

	@OriginalMember(owner = "client!ro", name = "o", descriptor = "I")
	public final int languageId;

	@OriginalMember(owner = "client!ro", name = "m", descriptor = "I")
	public final int num;

	@OriginalMember(owner = "client!ro", name = "D", descriptor = "[Ljava/lang/String;")
	private final String[] defaultOps;

	@OriginalMember(owner = "client!ro", name = "z", descriptor = "[Ljava/lang/String;")
	private final String[] aStringArray30;

	@OriginalMember(owner = "client!ro", name = "<init>", descriptor = "(Lclient!jk;IZLclient!ph;Lclient!fs;Lclient!fs;)V")
	public ObjTypeList(@OriginalArg(0) Game modeGame, @OriginalArg(1) int languageId, @OriginalArg(2) boolean allowMembers, @OriginalArg(3) Class180 arg3, @OriginalArg(4) Js5ResourceProvider configClient, @OriginalArg(5) Js5ResourceProvider arg5) {
		this.aClass180_2 = arg3;
		this.aClass76_77 = arg5;
		this.configClient = configClient;
		this.allowMembers = allowMembers;
		this.languageId = languageId;
		if (this.configClient == null) {
			this.num = 0;
		} else {
			@Pc(49) int local49 = this.configClient.method2100() - 1;
			this.num = this.configClient.method2108(local49) + local49 * 256;
		}
		this.defaultOps = new String[] { null, null, PositionedGfx.aClass83_57.method2267(this.languageId), null, null };
		this.aStringArray30 = new String[] { null, null, null, null, Class95_Sub3_Sub1.aClass83_128.method2267(this.languageId) };
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(ZLclient!cj;Lclient!la;IILclient!ya;ZIIIZLclient!ya;)Lclient!l;")
	public NativeSprite method4699(@OriginalArg(0) boolean arg0, @OriginalArg(1) PlayerAppearance arg1, @OriginalArg(2) AbstractFontRenderer arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg5, @OriginalArg(6) boolean arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9, @OriginalArg(11) com.jagex.game.runetek5.graphics.Toolkit arg10) {
		@Pc(16) NativeSprite local16 = this.method4700(arg10, arg3, arg1, arg4, arg7, arg9, arg8);
		if (local16 != null) {
			return local16;
		}
		@Pc(30) ItemType local30 = this.method4703(arg3);
		if (arg7 > 1 && local30.stackIds != null) {
			@Pc(38) int local38 = -1;
			for (@Pc(40) int local40 = 0; local40 < 10; local40++) {
				if (local30.stackTriggerAmount[local40] <= arg7 && local30.stackTriggerAmount[local40] != 0) {
					local38 = local30.stackIds[local40];
				}
			}
			if (local38 != -1) {
				local30 = this.method4703(local38);
			}
		}
		@Pc(92) int[] local92 = local30.method4774(arg1, arg0, arg2, arg7, arg9, arg4, arg10, arg8, arg5);
		if (local92 == null) {
			return null;
		}
		@Pc(106) NativeSprite local106;
		if (arg6) {
			local106 = arg5.method4265(local92, 36, 36, 32);
		} else {
			local106 = arg10.method4265(local92, 36, 36, 32);
		}
		if (!arg6) {
			@Pc(122) Class154 local122 = new Class154();
			local122.aBoolean438 = arg1 != null;
			local122.anInt4761 = arg9;
			local122.anInt4759 = arg3;
			local122.anInt4769 = arg4;
			local122.anInt4758 = arg7;
			local122.anInt4762 = arg10.index;
			local122.anInt4765 = arg8;
			this.spriteCache.put(local106, local122);
		}
		return local106;
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(Lclient!ya;IILclient!cj;IIII)Lclient!l;")
	public NativeSprite method4700(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(3) PlayerAppearance arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		this.aClass154_1.anInt4765 = arg6;
		this.aClass154_1.anInt4762 = arg0.index;
		this.aClass154_1.anInt4769 = arg3;
		this.aClass154_1.anInt4761 = arg5;
		this.aClass154_1.aBoolean438 = arg2 != null;
		this.aClass154_1.anInt4759 = arg1;
		this.aClass154_1.anInt4758 = arg4;
		return (NativeSprite) this.spriteCache.method4946(this.aClass154_1);
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(B)V")
	public void method4701() {
		@Pc(6) SpriteDrawQueue local6 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method4951();
		}
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(I)V")
	public void method4702() {
		@Pc(12) LRUCache local12 = this.typeCache;
		synchronized (this.typeCache) {
			this.typeCache.method1791();
		}
		local12 = this.modelCache;
		synchronized (this.modelCache) {
			this.modelCache.method1791();
		}
		@Pc(44) SpriteDrawQueue local44 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method4952();
		}
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(II)Lclient!rv;")
	public ItemType method4703(@OriginalArg(1) int arg0) {
		@Pc(8) LRUCache local8 = this.typeCache;
		@Pc(18) ItemType local18;
		synchronized (this.typeCache) {
			local18 = (ItemType) this.typeCache.method1787((long) arg0);
		}
		if (local18 != null) {
			return local18;
		}
		@Pc(41) byte[] local41 = this.configClient.method2104(Shadow.method4082(arg0), Class225.method5009(arg0));
		local18 = new ItemType();
		local18.id = arg0;
		local18.typeList = this;
		local18.aStringArray34 = new String[] { null, null, PositionedGfx.aClass83_57.method2267(this.languageId), null, null };
		local18.aStringArray33 = new String[] { null, null, null, null, Class95_Sub3_Sub1.aClass83_128.method2267(this.languageId) };
		if (local41 != null) {
			local18.method4790(new Packet(local41));
		}
		local18.method4791();
		if (local18.anInt6175 != -1) {
			local18.generateNote(this.method4703(local18.anInt6162), this.method4703(local18.anInt6175));
		}
		if (local18.anInt6185 != -1) {
			local18.method4779(this.method4703(local18.anInt6138), this.method4703(local18.anInt6185));
		}
		if (!this.allowMembers && local18.membersOnly) {
			local18.name = Class16_Sub3.aClass83_21.method2267(this.languageId);
			local18.anInt6181 = 0;
			local18.aStringArray34 = this.defaultOps;
			local18.quests = null;
			local18.stockmarket = false;
			local18.aStringArray33 = this.aStringArray30;
			if (local18.aClass102_36 != null) {
				@Pc(200) boolean local200 = false;
				for (@Pc(205) Country local205 = local18.aClass102_36.method2705(); local205 != null; local205 = local18.aClass102_36.method2704()) {
					@Pc(215) Class133 local215 = this.aClass180_2.method4121((int) local205.aLong224);
					if (local215.aBoolean385) {
						local205.method5684();
					} else {
						local200 = true;
					}
				}
				if (!local200) {
					local18.aClass102_36 = null;
				}
			}
		}
		@Pc(240) LRUCache local240 = this.typeCache;
		synchronized (this.typeCache) {
			this.typeCache.method1779((long) arg0, local18);
			return local18;
		}
	}

	@OriginalMember(owner = "client!ro", name = "a", descriptor = "(ZZ)V")
	public void method4704(@OriginalArg(0) boolean arg0) {
		if (arg0 != this.allowMembers) {
			this.allowMembers = arg0;
			this.method4707();
		}
	}

	@OriginalMember(owner = "client!ro", name = "b", descriptor = "(II)V")
	public void method4706() {
		@Pc(2) LRUCache local2 = this.typeCache;
		synchronized (this.typeCache) {
			this.typeCache.method1788(5);
		}
		local2 = this.modelCache;
		synchronized (this.modelCache) {
			this.modelCache.method1788(5);
		}
		@Pc(38) SpriteDrawQueue local38 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method4950();
		}
	}

	@OriginalMember(owner = "client!ro", name = "b", descriptor = "(B)V")
	public void method4707() {
		@Pc(2) LRUCache local2 = this.typeCache;
		synchronized (this.typeCache) {
			this.typeCache.method1777();
		}
		local2 = this.modelCache;
		synchronized (this.modelCache) {
			this.modelCache.method1777();
		}
		@Pc(42) SpriteDrawQueue local42 = this.spriteCache;
		synchronized (this.spriteCache) {
			this.spriteCache.method4951();
		}
	}

	@OriginalMember(owner = "client!ro", name = "c", descriptor = "(B)V")
	public void method4710() {
		@Pc(2) LRUCache local2 = this.modelCache;
		synchronized (this.modelCache) {
			this.modelCache.method1777();
		}
	}

	@OriginalMember(owner = "client!ro", name = "c", descriptor = "(II)V")
	public void method4712(@OriginalArg(0) int arg0) {
		this.featureMask = arg0;
		@Pc(13) LRUCache local13 = this.modelCache;
		synchronized (this.modelCache) {
			this.modelCache.method1777();
		}
	}

@OriginalMember(owner = "client!ro", name = "l", descriptor = "I")
	public static int anInt5986;
@OriginalMember(owner = "client!ro", name = "q", descriptor = "[Lclient!l;")
	public static NativeSprite[] aClass57Array14;
@OriginalMember(owner = "client!ro", name = "e", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray29 = new String[8];
@OriginalMember(owner = "client!ro", name = "a", descriptor = "(Lclient!lh;B)V")
	public static void method4705(@OriginalArg(0) PlayerEntity arg0) {
		@Pc(11) ReflectionCheck local11 = (ReflectionCheck) AbstractArbBufferGL.aClass102_38.method2700((long) arg0.anInt6037);
		if (local11 == null) {
			return;
		}
		if (local11.aClass4_Sub15_Sub3_2 != null) {
			GameCanvas.aClass4_Sub15_Sub2_2.method2955(local11.aClass4_Sub15_Sub3_2);
			local11.aClass4_Sub15_Sub3_2 = null;
		}
		local11.method5684();
	}
@OriginalMember(owner = "client!ro", name = "a", descriptor = "(IIIIIB)V")
	public static void method4709(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		if (ItemType.anInt6186 <= arg1 && Class36.anInt2656 >= arg3 && Model.anInt6287 <= arg4 && GlCubeTexture.anInt3256 >= arg0) {
			Class4_Sub41.method5242(arg1, arg0, arg2, arg3, arg4);
		} else {
			Static32.method626(arg4, arg2, arg3, arg1, arg0);
		}
	}
@OriginalMember(owner = "client!ro", name = "c", descriptor = "(I)V")
	public static void method4711() {
		if (MonoStereoData.aClass19_16.method4291()) {
			MonoStereoData.aClass19_16.method4263(Class164.aCanvas5);
			Class92.method2426();
			MonoStereoData.aClass19_16.method4249(Class164.aCanvas5);
			MonoStereoData.aClass19_16.setCanvas(Class164.aCanvas5);
		} else {
			ParticleList.method3551(Packet.anInt2973);
		}
		ModeWhere.method4940();
	}
}
