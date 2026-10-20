package com.jagex.game.runetek5;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;

import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.config.npc.NPCType;
@OriginalClass("client!pv")
public final class FixedFunctionSpecularPass extends Class9 {

	@OriginalMember(owner = "client!pv", name = "n", descriptor = "Lclient!ct;")
	private GlRenderList aClass43_5;

	@OriginalMember(owner = "client!pv", name = "o", descriptor = "Z")
	private boolean active = false;

	@OriginalMember(owner = "client!pv", name = "j", descriptor = "Z")
	private boolean singleStage;

	@OriginalMember(owner = "client!pv", name = "x", descriptor = "[Lclient!im;")
	private GlCubeTexture[] aClass6_Sub4Array1;

	@OriginalMember(owner = "client!pv", name = "<init>", descriptor = "(Lclient!fd;)V")
	public FixedFunctionSpecularPass(@OriginalArg(0) GlToolkit arg0) {
		super(arg0);
		if (arg0.arbTextureCubeMap) {
			this.singleStage = arg0.anInt2306 < 3;
			@Pc(26) int local26 = this.singleStage ? 48 : 127;
			@Pc(30) byte[][] local30 = new byte[6][4096];
			@Pc(34) byte[][] local34 = new byte[6][4096];
			@Pc(38) byte[][] local38 = new byte[6][4096];
			@Pc(40) int local40 = 0;
			for (@Pc(42) int local42 = 0; local42 < 64; local42++) {
				for (@Pc(46) int local46 = 0; local46 < 64; local46++) {
					@Pc(57) float local57 = (float) local46 * 2.0F / 64.0F - 1.0F;
					@Pc(66) float local66 = (float) local42 * 2.0F / 64.0F - 1.0F;
					@Pc(81) float local81 = (float) (1.0D / Math.sqrt((double) (local66 * local66 + local57 * local57 + 1.0F)));
					@Pc(85) float local85 = local57 * local81;
					@Pc(89) float local89 = local66 * local81;
					for (@Pc(91) int local91 = 0; local91 < 6; local91++) {
						@Pc(114) float local114;
						if (local91 == 0) {
							local114 = -local85;
						} else if (local91 == 1) {
							local114 = local85;
						} else if (local91 == 2) {
							local114 = local89;
						} else if (local91 == 3) {
							local114 = -local89;
						} else if (local91 == 4) {
							local114 = local81;
						} else {
							local114 = -local81;
						}
						@Pc(147) int local147;
						@Pc(146) int local146;
						@Pc(144) int local144;
						if (local114 > 0.0F) {
							local147 = (int) (Math.pow((double) local114, 96.0D) * (double) local26);
							local146 = (int) (Math.pow((double) local114, 36.0D) * (double) local26);
							local144 = (int) (Math.pow((double) local114, 12.0D) * (double) local26);
						} else {
							local144 = 0;
							local146 = 0;
							local147 = 0;
						}
						local34[local91][local40] = (byte) local147;
						local38[local91][local40] = (byte) local146;
						local30[local91][local40] = (byte) local144;
					}
					local40++;
				}
			}
			this.aClass6_Sub4Array1 = new GlCubeTexture[3];
			this.aClass6_Sub4Array1[0] = new GlCubeTexture(super.aClass19_Sub2_41, 6406, 64, false, local34, 6406);
			this.aClass6_Sub4Array1[1] = new GlCubeTexture(super.aClass19_Sub2_41, 6406, 64, false, local38, 6406);
			this.aClass6_Sub4Array1[2] = new GlCubeTexture(super.aClass19_Sub2_41, 6406, 64, false, local30, 6406);
			this.method4233();
		}
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(ZB)V")
	@Override
	public void applyTextureCombine(@OriginalArg(0) boolean lit) {
		super.aClass19_Sub2_41.method1912(8448, 7681);
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(I)V")
	@Override
	public void disable() {
		if (this.active) {
			if (!this.singleStage) {
				super.aClass19_Sub2_41.method1936(2);
				super.aClass19_Sub2_41.setActiveTexture(null);
			}
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(null);
			super.aClass19_Sub2_41.method1936(0);
			this.aClass43_5.method989('\u0001');
			this.active = false;
		} else {
			super.aClass19_Sub2_41.method1954(5890, 0);
		}
		super.aClass19_Sub2_41.method1912(8448, 8448);
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(ILclient!kj;Z)V")
	@Override
	public void method5376(@OriginalArg(0) int arg0, @OriginalArg(1) Class6 arg1) {
		super.aClass19_Sub2_41.setActiveTexture(arg1);
		super.aClass19_Sub2_41.method1900(arg0);
	}

	@OriginalMember(owner = "client!pv", name = "b", descriptor = "(Z)V")
	private void method4233() {
		this.aClass43_5 = new GlRenderList(super.aClass19_Sub2_41, 2);
		this.aClass43_5.method990(0);
		super.aClass19_Sub2_41.method1936(1);
		OpenGL.glTexGeni(8192, 9472, 34065);
		OpenGL.glTexGeni(8193, 9472, 34065);
		OpenGL.glTexGeni(8194, 9472, 34065);
		OpenGL.glEnable(3168);
		OpenGL.glEnable(3169);
		OpenGL.glEnable(3170);
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadIdentity();
		OpenGL.glRotatef(22.5F, 1.0F, 0.0F, 0.0F);
		OpenGL.glMatrixMode(5888);
		if (this.singleStage) {
			super.aClass19_Sub2_41.method1912(260, 7681);
			super.aClass19_Sub2_41.method1945(0, 770, 5890);
			super.aClass19_Sub2_41.method1954(34167, 0);
		} else {
			super.aClass19_Sub2_41.method1912(7681, 8448);
			super.aClass19_Sub2_41.method1945(0, 768, 34168);
			super.aClass19_Sub2_41.method1936(2);
			super.aClass19_Sub2_41.method1912(260, 7681);
			super.aClass19_Sub2_41.method1945(0, 768, 34168);
			super.aClass19_Sub2_41.method1945(1, 770, 34168);
			super.aClass19_Sub2_41.method1954(34167, 0);
		}
		super.aClass19_Sub2_41.method1936(0);
		this.aClass43_5.method991();
		this.aClass43_5.method990(1);
		super.aClass19_Sub2_41.method1936(1);
		OpenGL.glDisable(3168);
		OpenGL.glDisable(3169);
		OpenGL.glDisable(3170);
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadIdentity();
		OpenGL.glMatrixMode(5888);
		if (this.singleStage) {
			super.aClass19_Sub2_41.method1912(8448, 8448);
			super.aClass19_Sub2_41.method1945(0, 768, 5890);
			super.aClass19_Sub2_41.method1954(5890, 0);
		} else {
			super.aClass19_Sub2_41.method1912(8448, 8448);
			super.aClass19_Sub2_41.method1945(0, 768, 5890);
			super.aClass19_Sub2_41.method1936(2);
			super.aClass19_Sub2_41.method1912(8448, 8448);
			super.aClass19_Sub2_41.method1945(0, 768, 5890);
			super.aClass19_Sub2_41.method1945(1, 768, 34168);
			super.aClass19_Sub2_41.method1954(5890, 0);
		}
		super.aClass19_Sub2_41.method1936(0);
		this.aClass43_5.method991();
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(IZ)V")
	@Override
	public void enable(@OriginalArg(1) boolean arg0) {
		if (this.aClass43_5 == null || !arg0) {
			super.aClass19_Sub2_41.method1954(34168, 0);
			return;
		}
		if (!this.singleStage) {
			super.aClass19_Sub2_41.method1936(2);
			super.aClass19_Sub2_41.setActiveTexture(super.aClass19_Sub2_41.texture2d);
			super.aClass19_Sub2_41.method1936(0);
		}
		this.aClass43_5.method989('\u0000');
		this.active = true;
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(Z)Z")
	@Override
	public boolean isSupported() {
		return true;
	}

	@OriginalMember(owner = "client!pv", name = "a", descriptor = "(III)V")
	@Override
	public void setEffectParams(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		if (this.active) {
			super.aClass19_Sub2_41.method1936(1);
			super.aClass19_Sub2_41.setActiveTexture(this.aClass6_Sub4Array1[arg0 - 1]);
			super.aClass19_Sub2_41.method1936(0);
		}
	}

@OriginalMember(owner = "client!pv", name = "p", descriptor = "Lclient!jk;")
	public static final Game WRITE_PING = new Game("stellardawn", 1);
@OriginalMember(owner = "client!pv", name = "w", descriptor = "I")
	public static int anInt5521 = 13156520;
@OriginalMember(owner = "client!pv", name = "z", descriptor = "[I")
	public static final int[] anIntArray378 = new int[14];
@OriginalMember(owner = "client!pv", name = "A", descriptor = "I")
	public static int anInt5523 = 0;
@OriginalMember(owner = "client!pv", name = "b", descriptor = "(B)V")
	public static void method4231() {
		Static57.aClass19_7.UA(Js5MasterIndex.anInt6110, TextureOpBrightness.aClass50_Sub1_1.aBoolean298 ? Class7.anInt116 + 256 << 0 : -1, 256);
	}
@OriginalMember(owner = "client!pv", name = "c", descriptor = "(B)I")
	public static int method4232() {
		@Pc(10) LRUCache local10 = NPCType.aClass68_58;
		synchronized (NPCType.aClass68_58) {
			return NPCType.aClass68_58.method1786();
		}
	}
@OriginalMember(owner = "client!pv", name = "a", descriptor = "(Lclient!fs;ILclient!fs;)V")
	public static void method4234(@OriginalArg(0) Js5ResourceProvider arg0, @OriginalArg(2) Js5ResourceProvider arg1) {
		GlMemoryPool.aClass76_1 = arg0;
		EffectManagerGL.aClass76_96 = arg1;
	}
}
