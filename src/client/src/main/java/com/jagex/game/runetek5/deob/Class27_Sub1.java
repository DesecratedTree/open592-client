package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import jaggl.OpenGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.deob.Class4_Sub43;
@OriginalClass("client!br")
public final class Class27_Sub1 extends TextureCombinerOp {

	@OriginalMember(owner = "client!br", name = "k", descriptor = "I")
	private final int anInt646;

	@OriginalMember(owner = "client!br", name = "v", descriptor = "I")
	private final int anInt657;

	@OriginalMember(owner = "client!br", name = "p", descriptor = "I")
	private final int anInt651;

	@OriginalMember(owner = "client!br", name = "h", descriptor = "I")
	private final int anInt643;

	@OriginalMember(owner = "client!br", name = "o", descriptor = "I")
	private final int anInt650;

	@OriginalMember(owner = "client!br", name = "i", descriptor = "I")
	private final int anInt644;

	@OriginalMember(owner = "client!br", name = "j", descriptor = "I")
	private final int anInt645;

	@OriginalMember(owner = "client!br", name = "r", descriptor = "I")
	private final int anInt653;

	static {
		new Class83("To interact with this please login to a members' server.", "Logg dich auf einer Mitglieder-Welt ein, um damit zu interagieren.", "Veuillez vous connecter à un serveur d'abonnés pour cette interaction.", "Para interagir, acesse um servidor para membros.");
	}

	@OriginalMember(owner = "client!br", name = "<init>", descriptor = "(IIIIIIIIII)V")
	public Class27_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9) {
		super(-1, arg8, arg9);
		this.anInt646 = arg7;
		this.anInt657 = arg6;
		this.anInt651 = arg5;
		this.anInt643 = arg3;
		this.anInt650 = arg4;
		this.anInt644 = arg1;
		this.anInt645 = arg2;
		this.anInt653 = arg0;
	}

	@OriginalMember(owner = "client!br", name = "b", descriptor = "(III)V")
	@Override
	public void method3936(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
	}

	@OriginalMember(owner = "client!br", name = "a", descriptor = "(III)V")
	@Override
	public void method3935(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(16) int local16 = this.anInt653 * arg1 >> 12;
		@Pc(23) int local23 = arg0 * this.anInt644 >> 12;
		@Pc(30) int local30 = arg1 * this.anInt645 >> 12;
		@Pc(37) int local37 = this.anInt643 * arg0 >> 12;
		@Pc(44) int local44 = this.anInt650 * arg1 >> 12;
		@Pc(51) int local51 = this.anInt651 * arg0 >> 12;
		@Pc(58) int local58 = this.anInt657 * arg1 >> 12;
		@Pc(65) int local65 = this.anInt646 * arg0 >> 12;
		Model.method4895(local58, local44, local37, local65, local30, local51, local16, super.anInt5152, local23);
	}

	@OriginalMember(owner = "client!br", name = "c", descriptor = "(III)V")
	@Override
	public void method3939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
	}

@OriginalMember(owner = "client!br", name = "n", descriptor = "I")
	public static int anInt649;
@OriginalMember(owner = "client!br", name = "w", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray5 = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };
@OriginalMember(owner = "client!br", name = "a", descriptor = "(Lclient!fs;I)V")
	public static void method664(@OriginalArg(0) Js5ResourceProvider arg0) {
		BillboardType.aClass76_42 = arg0;
	}
@OriginalMember(owner = "client!br", name = "a", descriptor = "(I[IIIIIII)V")
	public static void method665(@OriginalArg(1) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		if (arg3 > 0 && !Class4_Sub43.method5413(arg3)) {
			throw new IllegalArgumentException("");
		} else if (arg2 <= 0 || Class4_Sub43.method5413(arg2)) {
			@Pc(45) int local45 = 0;
			@Pc(56) int local56 = arg2 <= arg3 ? arg2 : arg3;
			@Pc(60) int local60 = arg3 >> 1;
			@Pc(64) int local64 = arg2 >> 1;
			@Pc(66) int[] local66 = arg0;
			@Pc(71) int[] local71 = new int[local60 * local64];
			while (true) {
				OpenGL.glTexImage2Di(arg5, local45, arg1, arg3, arg2, 0, 32993, arg4, local66, 0);
				if (local56 <= 1) {
					return;
				}
				@Pc(89) int local89 = 0;
				@Pc(91) int local91 = 0;
				@Pc(95) int local95 = arg3;
				@Pc(97) int[] local97 = local71;
				for (@Pc(99) int local99 = 0; local99 < local64; local99++) {
					for (@Pc(103) int local103 = 0; local103 < local60; local103++) {
						@Pc(110) int local110 = local66[local91++];
						@Pc(115) int local115 = local66[local95++];
						@Pc(120) int local120 = local66[local91++];
						@Pc(126) int local126 = local110 >> 16 & 0xFF;
						@Pc(130) int local130 = local110 & 0xFF;
						@Pc(136) int local136 = local110 >> 24 & 0xFF;
						@Pc(141) int local141 = local66[local95++];
						@Pc(147) int local147 = local110 >> 8 & 0xFF;
						@Pc(155) int local155 = local147 + (local120 >> 8 & 0xFF);
						@Pc(163) int local163 = local126 + (local120 >> 16 & 0xFF);
						@Pc(171) int local171 = local136 + (local120 >> 24 & 0xFF);
						@Pc(177) int local177 = local130 + (local120 & 0xFF);
						@Pc(183) int local183 = local177 + (local115 & 0xFF);
						@Pc(191) int local191 = local163 + (local115 >> 16 & 0xFF);
						@Pc(199) int local199 = local155 + (local115 >> 8 & 0xFF);
						@Pc(207) int local207 = local171 + (local115 >> 24 & 0xFF);
						@Pc(215) int local215 = local191 + (local141 >> 16 & 0xFF);
						@Pc(221) int local221 = local183 + (local141 & 0xFF);
						@Pc(229) int local229 = local207 + (local141 >> 24 & 0xFF);
						@Pc(237) int local237 = local199 + (local141 >> 8 & 0xFF);
						local71[local89++] = local221 >> 2 & 0xFF | (local237 & 0x3FC) << 6 | (local215 & 0x3FC) << 14 | (local229 & 0x3FC) << 22;
					}
					local91 += arg3;
					local95 += arg3;
				}
				local71 = local66;
				local66 = local97;
				arg3 = local60;
				arg2 = local64;
				local60 >>= 0x1;
				local56 >>= 0x1;
				local45++;
				local64 >>= 0x1;
			}
		} else {
			throw new IllegalArgumentException("");
		}
	}
}
