package com.jagex.game.runetek5.scene.ground;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.ka;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.deob.ra;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.toolkit.opengl.effect.UnlitEffect;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;

import com.jagex.game.runetek5.scene.ground.*;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class249;
@OriginalClass("client!c")
public abstract class Ground {

	@OriginalMember(owner = "client!c", name = "p", descriptor = "(III[I)V")
	public abstract void p(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3);

	@OriginalMember(owner = "client!c", name = "N", descriptor = "(I)V")
	public abstract void N(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!c", name = "xa", descriptor = "()V")
	public abstract void xa();

	@OriginalMember(owner = "client!c", name = "m", descriptor = "([I)V")
	public abstract void addLight(@OriginalArg(0) int[] arg0);

	@OriginalMember(owner = "client!c", name = "ra", descriptor = "(I)V")
	public abstract void ra(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!c", name = "j", descriptor = "(III)V")
	public abstract void scaleWidth(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!c", name = "a", descriptor = "()Lclient!c;")
	public abstract Ground method5657();

	@OriginalMember(owner = "client!c", name = "ja", descriptor = "(I)V")
	public abstract void ja(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!c", name = "za", descriptor = "(Lclient!c;)V")
	public abstract void UnlitEffect(@OriginalArg(0) Ground arg0);

	@OriginalMember(owner = "client!c", name = "ka", descriptor = "(I)V")
	public abstract void ka(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!c", name = "O", descriptor = "(I)V")
	public abstract void O(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!c", name = "a", descriptor = "(III[I)V")
	public abstract void method5660(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3);

	@OriginalMember(owner = "client!c", name = "s", descriptor = "(IIIIII)V")
	public abstract void s(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5);

	@OriginalMember(owner = "client!c", name = "o", descriptor = "(III)V")
	public abstract void o(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

@OriginalMember(owner = "client!c", name = "c", descriptor = "I")
	public static int anInt7321;
@OriginalMember(owner = "client!c", name = "e", descriptor = "Lclient!he;")
	public static Class93 aClass93_4;
@OriginalMember(owner = "client!c", name = "f", descriptor = "I")
	public static int anInt7322;
@OriginalMember(owner = "client!c", name = "g", descriptor = "Lclient!la;")
	public static AbstractFontRenderer aClass46_12;
@OriginalMember(owner = "client!c", name = "a", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_151 = new Class83("Loaded core fonts", "Schriftarten geladen", "Polices chargées", "Fontes principais carregadas");
@OriginalMember(owner = "client!c", name = "d", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_332 = new Class22(59, -2);
@OriginalMember(owner = "client!c", name = "a", descriptor = "(ZILclient!uu;II)V")
	public static void method5658(@OriginalArg(0) boolean arg0, @OriginalArg(1) int arg1, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component arg2, @OriginalArg(4) int arg3) {
		@Pc(8) int local8 = arg2.anInt6833;
		if (arg2.aByte80 == 0) {
			arg2.anInt6833 = arg2.anInt6816;
		} else if (arg2.aByte80 == 1) {
			arg2.anInt6833 = arg1 - arg2.anInt6816;
		} else if (arg2.aByte80 == 2) {
			arg2.anInt6833 = arg2.anInt6816 * arg1 >> 14;
		}
		@Pc(52) int local52 = arg2.anInt6805;
		if (arg2.aByte78 == 0) {
			arg2.anInt6805 = arg2.anInt6802;
		} else if (arg2.aByte78 == 1) {
			arg2.anInt6805 = arg3 - arg2.anInt6802;
		} else if (arg2.aByte78 == 2) {
			arg2.anInt6805 = arg2.anInt6802 * arg3 >> 14;
		}
		if (arg2.aByte80 == 4) {
			arg2.anInt6833 = arg2.anInt6774 * arg2.anInt6805 / arg2.anInt6856;
		}
		if (arg2.aByte78 == 4) {
			arg2.anInt6805 = arg2.anInt6856 * arg2.anInt6833 / arg2.anInt6774;
		}
		if (Class249.aBoolean626 && (client.method900(arg2).anInt5105 != 0 || arg2.anInt6840 == 0)) {
			if (arg2.anInt6805 < 5 && arg2.anInt6833 < 5) {
				arg2.anInt6805 = 5;
				arg2.anInt6833 = 5;
			} else {
				if (arg2.anInt6805 <= 0) {
					arg2.anInt6805 = 5;
				}
				if (arg2.anInt6833 <= 0) {
					arg2.anInt6833 = 5;
				}
			}
		}
		if (arg2.anInt6842 == QuickchatMessageType.anInt295) {
			ParticleProducerType.aClass247_7 = arg2;
		}
		if (arg0 && arg2.anObjectArray7 != null && (local8 != arg2.anInt6833 || local52 != arg2.anInt6805)) {
			@Pc(203) HookRequest local203 = new HookRequest();
			local203.source = arg2;
			local203.params = arg2.anObjectArray7;
			TextureOpMandelbrot.aClass183_35.method4137(local203);
		}
	}
}
