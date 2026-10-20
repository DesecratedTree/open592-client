package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!bo")
public final class Class25 {

	@OriginalMember(owner = "client!bo", name = "g", descriptor = "Lclient!vu;")
	private Country aClass4_33;

	@OriginalMember(owner = "client!bo", name = "h", descriptor = "I")
	private int anInt626 = 0;

	@OriginalMember(owner = "client!bo", name = "d", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_5;

	@OriginalMember(owner = "client!bo", name = "<init>", descriptor = "(Lclient!ib;)V")
	public Class25(@OriginalArg(0) CubeMapSpecularEffect arg0) {
		this.aClass102_5 = arg0;
	}

	@OriginalMember(owner = "client!bo", name = "a", descriptor = "(Z)Lclient!vu;")
	public Country method640() {
		@Pc(30) Country local30;
		if (this.anInt626 > 0 && this.aClass4_33 != this.aClass102_5.aClass4Array1[this.anInt626 - 1]) {
			local30 = this.aClass4_33;
			this.aClass4_33 = local30.aClass4_262;
			return local30;
		}
		while (this.anInt626 < this.aClass102_5.anInt3159) {
			local30 = this.aClass102_5.aClass4Array1[this.anInt626++].aClass4_262;
			if (this.aClass102_5.aClass4Array1[this.anInt626 - 1] != local30) {
				this.aClass4_33 = local30.aClass4_262;
				return local30;
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!bo", name = "a", descriptor = "(I)Lclient!vu;")
	public Country method641() {
		this.anInt626 = 0;
		return this.method640();
	}

@OriginalMember(owner = "client!bo", name = "b", descriptor = "Lclient!bg;")
	public static Class22 aClass22_42;
@OriginalMember(owner = "client!bo", name = "e", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_5 = new Class21(6, 4);
@OriginalMember(owner = "client!bo", name = "f", descriptor = "[Lclient!pt;")
	public static final Class4_Sub1_Sub17[] aClass4_Sub1_Sub17Array3 = new Class4_Sub1_Sub17[14];
}
