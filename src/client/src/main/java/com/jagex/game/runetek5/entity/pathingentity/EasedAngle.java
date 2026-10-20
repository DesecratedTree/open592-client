package com.jagex.game.runetek5.entity.pathingentity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static277;
import com.jagex.game.runetek5.deob.Static298;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;

import com.jagex.game.runetek5.entity.pathingentity.*;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.LegacyMouseManager;
@OriginalClass("client!ch")
public final class EasedAngle {

	@OriginalMember(owner = "client!ch", name = "e", descriptor = "I")
	private int velocity;

	@OriginalMember(owner = "client!ch", name = "i", descriptor = "I")
	public int rotation;

	static {
		new Class83("Your clan channel has now been disabled!", "Dein Chatraum ist jetzt ausgeschaltet.", "Votre canal de clan est désactivé.", "Seu canal de clã foi desativado!");
	}

	@OriginalMember(owner = "client!ch", name = "b", descriptor = "(I)I")
	public int method811() {
		return this.rotation & 0x3FFF;
	}

	@OriginalMember(owner = "client!ch", name = "a", descriptor = "(II)V")
	public void method812(@OriginalArg(0) int arg0) {
		this.rotation = arg0;
		this.velocity = 0;
	}

	@OriginalMember(owner = "client!ch", name = "a", descriptor = "(Z)V")
	public void method815() {
		this.rotation &= 0x3FFF;
	}

	@OriginalMember(owner = "client!ch", name = "a", descriptor = "(IIII)Z")
	public boolean method816(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(8) int local8 = this.velocity;
		if (this.rotation == arg2 && this.velocity == 0) {
			return false;
		}
		@Pc(42) boolean local42;
		@Pc(54) int local54;
		if (this.velocity == 0) {
			if (this.rotation < arg2 && this.rotation + arg0 >= arg2 || this.rotation > arg2 && this.rotation - arg0 <= arg2) {
				this.rotation = arg2;
				return false;
			}
			local42 = true;
		} else {
			@Pc(60) int local60;
			if (this.velocity > 0 && arg2 > this.rotation) {
				local54 = this.velocity * this.velocity / (arg0 * 2);
				local60 = this.rotation + local54;
				if (local60 < arg2 && this.rotation <= local60) {
					local42 = true;
				} else {
					local42 = false;
				}
			} else if (this.velocity < 0 && this.rotation > arg2) {
				local54 = this.velocity * this.velocity / (arg0 * 2);
				local60 = this.rotation - local54;
				if (arg2 < local60 && local60 <= this.rotation) {
					local42 = true;
				} else {
					local42 = false;
				}
			} else {
				local42 = false;
			}
		}
		if (local42) {
			if (arg2 <= this.rotation) {
				this.velocity -= arg0;
				if (arg1 != 0 && this.velocity < -arg1) {
					this.velocity = -arg1;
				}
			} else {
				this.velocity += arg0;
				if (arg1 != 0 && arg1 < this.velocity) {
					this.velocity = arg1;
				}
			}
			if (this.velocity != local8) {
				local54 = this.velocity * this.velocity / (arg0 * 2);
				if (this.rotation < arg2) {
					if (this.rotation + local54 > arg2) {
						this.velocity = local8;
					}
				} else if (arg2 < this.rotation && arg2 > this.rotation - local54) {
					this.velocity = local8;
				}
			}
		} else if (this.velocity > 0) {
			this.velocity -= arg0;
			if (this.velocity < 0) {
				this.velocity = 0;
			}
		} else {
			this.velocity += arg0;
			if (this.velocity > 0) {
				this.velocity = 0;
			}
		}
		this.rotation += local8 + this.velocity >> 1;
		return local42;
	}

@OriginalMember(owner = "client!ch", name = "m", descriptor = "[[I")
	public static int[][] anIntArrayArray8;
@OriginalMember(owner = "client!ch", name = "b", descriptor = "I")
	public static int anInt855 = 0;
@OriginalMember(owner = "client!ch", name = "d", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_11 = new Class215(69, 0);
@OriginalMember(owner = "client!ch", name = "f", descriptor = "F")
	public static float aFloat28 = 0.0F;
@OriginalMember(owner = "client!ch", name = "g", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_27 = new Class83("Loading defaults - ", "Lade Standardeinstellungen - ", "Chargement des paramètres par défaut - ", "Carregando padrões - ");
@OriginalMember(owner = "client!ch", name = "h", descriptor = "[I")
	public static final int[] anIntArray50 = new int[] { 1, 2, 4, 8 };
@OriginalMember(owner = "client!ch", name = "n", descriptor = "I")
	public static int anInt862 = 0;
@OriginalMember(owner = "client!ch", name = "c", descriptor = "(I)V")
	public static void method813() {
		if (MonoStereoData.aClass19_16 == null) {
			return;
		}
		Class4_Sub3.aClass84_2.method2310();
		TextureOpColorGradient.method1541();
		Static277.method3932();
		Class4_Sub29.method3601();
		Class190.method4217();
		NPCEntity.method4759();
		if (GlMemoryPool.aClass242_1 != null) {
			GlMemoryPool.aClass242_1.method5253();
		}
		Class4_Sub9.method759();
		Class244.method5485();
		Static298.method4086();
		Class4_Sub7.method633(false);
		LegacyMouseManager.method4834();
		for (@Pc(45) int local45 = 0; local45 < 2048; local45++) {
			@Pc(51) PlayerEntity local51 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local45];
			if (local51 != null) {
				local51.aClass4_Sub23_3 = null;
				for (@Pc(58) int local58 = 0; local58 < local51.models.length; local58++) {
					local51.models[local58] = null;
				}
			}
		}
		for (@Pc(83) int local83 = 0; local83 < Class93.aClass16_Sub1_Sub5_Sub2Array1.length; local83++) {
			@Pc(89) NPCEntity local89 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local83];
			if (local89 != null) {
				for (@Pc(93) int local93 = 0; local93 < local89.models.length; local93++) {
					local89.models[local93] = null;
				}
			}
		}
		MonoStereoData.aClass19_16.method4267();
		MonoStereoData.aClass19_16 = null;
	}
@OriginalMember(owner = "client!ch", name = "a", descriptor = "(IIIIIII)V")
	public static void method814(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		@Pc(3) Class136 local3 = new Class136();
		local3.anInt4365 = arg1 >> Static231.anInt4434;
		local3.anInt4363 = arg2 >> Static231.anInt4434;
		local3.anInt4354 = arg3 >> Static231.anInt4434;
		local3.anInt4357 = arg4 >> Static231.anInt4434;
		local3.anInt4360 = arg0;
		local3.anInt4355 = arg1;
		local3.anInt4370 = arg2;
		local3.anInt4359 = arg3;
		local3.anInt4356 = arg4;
		local3.anInt4368 = arg5;
		local3.anInt4361 = arg6;
		TextureOpSprite.aClass136Array2[OpenGlArbElementArrayBuffer.anInt1878++] = local3;
	}
}
