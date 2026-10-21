package com.jagex.game.runetek5.cache.node;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.Country;

import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.cache.node.*;
@OriginalClass("client!vp")
public abstract class PointLight extends Country {

	@OriginalMember(owner = "client!vp", name = "n", descriptor = "F")
	protected float intensity;

	@OriginalMember(owner = "client!vp", name = "y", descriptor = "I")
	protected int anInt3866;

	@OriginalMember(owner = "client!vp", name = "l", descriptor = "I")
	private final int anInt3855;

	@OriginalMember(owner = "client!vp", name = "o", descriptor = "I")
	private final int anInt3857;

	@OriginalMember(owner = "client!vp", name = "r", descriptor = "I")
	protected int anInt3860;

	@OriginalMember(owner = "client!vp", name = "m", descriptor = "I")
	protected int anInt3856;

	@OriginalMember(owner = "client!vp", name = "<init>", descriptor = "(IIIIIF)V")
	public PointLight(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int radius, @OriginalArg(4) int colour, @OriginalArg(5) float intensity) {
		this.intensity = intensity;
		this.anInt3866 = y;
		this.anInt3855 = radius;
		this.anInt3857 = colour;
		this.anInt3860 = x;
		this.anInt3856 = z;
	}

	@OriginalMember(owner = "client!vp", name = "a", descriptor = "(IF)V")
	public abstract void setIntensity(@OriginalArg(1) float arg0);

	@OriginalMember(owner = "client!vp", name = "b", descriptor = "(Z)F")
	public final float getIntensity() {
		return this.intensity;
	}

	@OriginalMember(owner = "client!vp", name = "c", descriptor = "(Z)I")
	public final int method3160() {
		return this.anInt3860;
	}

	@OriginalMember(owner = "client!vp", name = "c", descriptor = "(B)I")
	public final int method3161() {
		return this.anInt3866;
	}

	@OriginalMember(owner = "client!vp", name = "d", descriptor = "(B)I")
	public final int method3162() {
		return this.anInt3857;
	}

	@OriginalMember(owner = "client!vp", name = "a", descriptor = "(IIIB)V")
	public abstract void setPosition(@OriginalArg(0) int x, @OriginalArg(1) int arg1, @OriginalArg(2) int z);

	@OriginalMember(owner = "client!vp", name = "e", descriptor = "(B)I")
	public final int method3164() {
		return this.anInt3856;
	}

	@OriginalMember(owner = "client!vp", name = "a", descriptor = "(I)I")
	public final int method3165() {
		return this.anInt3855;
	}

@OriginalMember(owner = "client!vp", name = "x", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_17;
@OriginalMember(owner = "client!vp", name = "t", descriptor = "I")
	public static int pendingWheelDelta = 0;
}
