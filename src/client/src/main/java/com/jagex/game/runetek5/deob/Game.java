package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorFill;
@OriginalClass("client!jk")
public final class Game {

	@OriginalMember(owner = "client!jk", name = "h", descriptor = "Ljava/lang/String;")
	public final String aString36;

	@OriginalMember(owner = "client!jk", name = "c", descriptor = "I")
	public final int anInt3570;

	static {
		new Class83("Attempting to join channel...", "Chatraum wird betreten...", "Tentative de connexion au canal...", "Tentando acessar canal...");
	}

	@OriginalMember(owner = "client!jk", name = "<init>", descriptor = "(Ljava/lang/String;I)V")
	public Game(@OriginalArg(0) String arg0, @OriginalArg(1) int arg1) {
		this.aString36 = arg0;
		this.anInt3570 = arg1;
	}

	@OriginalMember(owner = "client!jk", name = "toString", descriptor = "()Ljava/lang/String;")
	@Override
	public String toString() {
		throw new IllegalStateException();
	}

@OriginalMember(owner = "client!jk", name = "b", descriptor = "I")
	public static int anInt3569;
@OriginalMember(owner = "client!jk", name = "j", descriptor = "[I")
	public static int[] anIntArray236;
@OriginalMember(owner = "client!jk", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_160 = new Class22(79, 6);
@OriginalMember(owner = "client!jk", name = "f", descriptor = "I")
	public static int anInt3572 = 0;
@OriginalMember(owner = "client!jk", name = "a", descriptor = "(Lclient!cw;II)V")
	public static void method2942(@OriginalArg(0) Class4_Sub12_Sub1 arg0, @OriginalArg(2) int arg1) {
		Static449.aBoolean689 = false;
		Static441.anInt7199 = 0;
		TextureOpColorFill.method617(arg0);
		Static173.method2814(arg0);
		if (Static449.aBoolean689) {
			System.out.println("---endgpp---");
		}
		if (arg1 != arg0.currentPosition) {
			throw new RuntimeException("gpi1 pos:" + arg0.currentPosition + " psize:" + arg1);
		}
	}
}
