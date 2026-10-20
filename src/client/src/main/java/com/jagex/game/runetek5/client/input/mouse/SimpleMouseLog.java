package com.jagex.game.runetek5.client.input.mouse;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class30;

import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.client.input.mouse.*;
@OriginalClass("client!bw")
public final class SimpleMouseLog extends Class30 {

	@OriginalMember(owner = "client!bw", name = "n", descriptor = "J")
	public long aLong29;

	@OriginalMember(owner = "client!bw", name = "p", descriptor = "Lclient!bw;")
	public SimpleMouseLog aClass30_Sub1_1;

	@OriginalMember(owner = "client!bw", name = "r", descriptor = "I")
	public int anInt781;

	@OriginalMember(owner = "client!bw", name = "s", descriptor = "Z")
	public boolean aBoolean77;

	@OriginalMember(owner = "client!bw", name = "t", descriptor = "C")
	public char aChar1;

	@OriginalMember(owner = "client!bw", name = "u", descriptor = "I")
	public int anInt782;

	@OriginalMember(owner = "client!bw", name = "c", descriptor = "(I)C")
	@Override
	public char method749() {
		return this.aChar1;
	}

	@OriginalMember(owner = "client!bw", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean method741() {
		return this.aBoolean77;
	}

	@OriginalMember(owner = "client!bw", name = "c", descriptor = "(B)I")
	@Override
	public int method746() {
		return this.anInt781;
	}

	@OriginalMember(owner = "client!bw", name = "a", descriptor = "(I)J")
	@Override
	public long method742() {
		return this.aLong29;
	}

	@OriginalMember(owner = "client!bw", name = "b", descriptor = "(I)I")
	@Override
	public int method745() {
		return this.anInt782;
	}

@OriginalMember(owner = "client!bw", name = "k", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_22 = new Class83("Loaded textures", "Texturen geladen.", "Textures chargées", "Texturas carregadas");
@OriginalMember(owner = "client!bw", name = "l", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_8 = new Class215(64, 8);
@OriginalMember(owner = "client!bw", name = "v", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_46 = new Class22(26, -1);
@OriginalMember(owner = "client!bw", name = "w", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_23 = new Class83("purple:", "lila:", "violet:", "roxo:");
}
