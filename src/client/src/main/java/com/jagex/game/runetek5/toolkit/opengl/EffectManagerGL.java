package com.jagex.game.runetek5.toolkit.opengl;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class9_Sub4;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.toolkit.opengl.*;
@OriginalClass("client!uv")
public final class EffectManagerGL {

	@OriginalMember(owner = "client!uv", name = "h", descriptor = "I")
	private int anInt6869 = 0;

	/**
	 * True when a material effect (water and friends) currently owns the GL
	 * program. The 592HD lighting shader must not bind over it. Read-only seam;
	 * see {@code GlToolkit.method1938} for where an effect is selected.
	 */
	public boolean isEffectActive() {
		return this.anInt6869 != 0;
	}

	@OriginalMember(owner = "client!uv", name = "e", descriptor = "I")
	private int anInt6867 = 0;

	@OriginalMember(owner = "client!uv", name = "d", descriptor = "Lclient!kj;")
	private Class6 aClass6_1 = null;

	@OriginalMember(owner = "client!uv", name = "m", descriptor = "I")
	private int anInt6871 = 0;

	@OriginalMember(owner = "client!uv", name = "k", descriptor = "Lclient!fd;")
	private final GlToolkit openglToolkit;

	@OriginalMember(owner = "client!uv", name = "p", descriptor = "Lclient!m;")
	private final NativeWaterTexture aClass144_6;

	@OriginalMember(owner = "client!uv", name = "f", descriptor = "[Lclient!dl;")
	private final Class9[] aClass9Array1;

	@OriginalMember(owner = "client!uv", name = "l", descriptor = "Lclient!ve;")
	public final StillWaterEffect waterEffect;

	@OriginalMember(owner = "client!uv", name = "<init>", descriptor = "(Lclient!fd;)V")
	public EffectManagerGL(@OriginalArg(0) GlToolkit glToolkit) {
		this.openglToolkit = glToolkit;
		this.aClass144_6 = new NativeWaterTexture(glToolkit);
		this.aClass9Array1 = new Class9[10];
		this.aClass9Array1[1] = new FixedFunctionSpecularPass(glToolkit);
		this.aClass9Array1[2] = new Class9_Sub4(glToolkit, this.aClass144_6);
		this.aClass9Array1[4] = new Class9_Sub3(glToolkit, this.aClass144_6);
		this.aClass9Array1[5] = new WaterfallEffect(glToolkit, this.aClass144_6);
		this.aClass9Array1[6] = new LegacyMouseManager(glToolkit);
		this.aClass9Array1[7] = new ReflectingTextureEffect(glToolkit);
		this.aClass9Array1[3] = this.waterEffect = new StillWaterEffect(glToolkit);
		this.aClass9Array1[8] = new ReflectiveRipplingWaterEffect(glToolkit, this.aClass144_6);
		this.aClass9Array1[9] = new BreakingWaterEffect(glToolkit, this.aClass144_6);
		if (!this.aClass9Array1[8].isSupported()) {
			this.aClass9Array1[8] = this.aClass9Array1[4];
		}
		if (!this.aClass9Array1[9].isSupported()) {
			this.aClass9Array1[9] = this.aClass9Array1[8];
		}
	}

	@OriginalMember(owner = "client!uv", name = "a", descriptor = "(BLclient!kj;I)Z")
	public boolean method5316(@OriginalArg(1) Class6 arg0, @OriginalArg(2) int arg1) {
		if (this.anInt6869 == 0) {
			return false;
		}
		if (arg0 != this.aClass6_1) {
			this.aClass9Array1[this.anInt6869 & Integer.MAX_VALUE].method5376(arg1, arg0);
			this.aClass6_1 = arg0;
		}
		return true;
	}

	@OriginalMember(owner = "client!uv", name = "a", descriptor = "(IB)Z")
	public boolean method5317() {
		return this.aClass9Array1[3].isSupported();
	}

	@OriginalMember(owner = "client!uv", name = "a", descriptor = "(IZZI)V")
	public void method5318(@OriginalArg(1) boolean arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) int arg2) {
		@Pc(9) boolean local9 = arg0 & this.openglToolkit.method4261();
		if (!local9 && (arg2 == 4 || arg2 == 8)) {
			arg2 = 2;
		}
		if (arg2 != 0 && arg1) {
			arg2 |= Integer.MIN_VALUE;
		}
		if (arg2 != this.anInt6869) {
			if (this.anInt6869 != 0) {
				this.aClass9Array1[Integer.MAX_VALUE & this.anInt6869].disable();
			}
			if (arg2 != 0) {
				this.aClass9Array1[arg2 & Integer.MAX_VALUE].enable(arg1);
				this.aClass9Array1[Integer.MAX_VALUE & arg2].applyTextureCombine(arg1);
			}
			this.anInt6869 = arg2;
			this.anInt6867 = Integer.MIN_VALUE;
			this.aClass6_1 = null;
			this.anInt6871 = Integer.MIN_VALUE;
		} else if (this.anInt6869 != 0) {
			this.aClass9Array1[Integer.MAX_VALUE & this.anInt6869].applyTextureCombine(arg1);
			return;
		}
	}

	@OriginalMember(owner = "client!uv", name = "a", descriptor = "(IIZ)V")
	public void method5319(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if ((this.anInt6867 != arg0 | arg1 != this.anInt6871) & this.anInt6869 != 0) {
			this.aClass9Array1[Integer.MAX_VALUE & this.anInt6869].setEffectParams(arg1, arg0);
			this.anInt6867 = arg0;
			this.anInt6871 = arg1;
		}
	}

@OriginalMember(owner = "client!uv", name = "a", descriptor = "[Lclient!lk;")
	public static Class136[] aClass136Array3;
@OriginalMember(owner = "client!uv", name = "b", descriptor = "I")
	public static int anInt6866;
@OriginalMember(owner = "client!uv", name = "c", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_96;
@OriginalMember(owner = "client!uv", name = "j", descriptor = "[I")
	public static final int[] anIntArray525 = new int[500];
@OriginalMember(owner = "client!uv", name = "n", descriptor = "[Z")
	public static final boolean[] aBooleanArray20 = new boolean[100];
}
