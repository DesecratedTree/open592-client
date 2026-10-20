package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.config.material.texture.*;
@OriginalClass("client!oc")
public abstract class TextureOperation extends Country {

	@OriginalMember(owner = "client!oc", name = "m", descriptor = "I")
	public int imageCacheCapacity;

	@OriginalMember(owner = "client!oc", name = "n", descriptor = "Lclient!ms;")
	protected ColorImageCache colorImageCache;

	@OriginalMember(owner = "client!oc", name = "s", descriptor = "Lclient!gi;")
	protected MonochromeImageCache monochromeImageCache;

	@OriginalMember(owner = "client!oc", name = "B", descriptor = "Z")
	public boolean monochrome;

	@OriginalMember(owner = "client!oc", name = "p", descriptor = "[Lclient!oc;")
	public final TextureOperation[] operations;

	@OriginalMember(owner = "client!oc", name = "<init>", descriptor = "(IZ)V")
	protected TextureOperation(@OriginalArg(0) int size, @OriginalArg(1) boolean monochrome) {
		this.monochrome = monochrome;
		this.operations = new TextureOperation[size];
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(III)[I")
	protected final int[] getChildMonochromeOutput(@OriginalArg(0) int row, @OriginalArg(1) int arg1) {
		return this.operations[arg1].monochrome ? this.operations[arg1].monochromeOutput(row) : this.operations[arg1].getColorOutput(row)[0];
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(Z)V")
	public void postDecode() {
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(II)[I")
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		throw new IllegalStateException("This operation does not have a monochrome output");
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(I)I")
	public int getSpriteId() {
		return -1;
	}

	@OriginalMember(owner = "client!oc", name = "c", descriptor = "(I)I")
	public int getTextureId() {
		return -1;
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(IBI)V")
	public void initCache(@OriginalArg(0) int height, @OriginalArg(2) int arg1) {
		@Pc(20) int local20 = this.imageCacheCapacity == 255 ? height : this.imageCacheCapacity;
		if (this.monochrome) {
			this.monochromeImageCache = new MonochromeImageCache(local20, height, arg1);
		} else {
			this.colorImageCache = new ColorImageCache(local20, height, arg1);
		}
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(BI)[[I")
	public int[][] getColorOutput(@OriginalArg(1) int arg0) {
		throw new IllegalStateException("This operation does not have a colour output");
	}

	@OriginalMember(owner = "client!oc", name = "b", descriptor = "(III)[[I")
	protected final int[][] getChildColorOutput(@OriginalArg(1) int arg0, @OriginalArg(2) int row) {
		if (this.operations[arg0].monochrome) {
			@Pc(30) int[] local30 = this.operations[arg0].monochromeOutput(row);
			return new int[][] { local30, local30, local30 };
		} else {
			return this.operations[arg0].getColorOutput(row);
		}
	}

	@OriginalMember(owner = "client!oc", name = "e", descriptor = "(I)V")
	public void cacheReset() {
		if (this.monochrome) {
			this.monochromeImageCache.method2259();
			this.monochromeImageCache = null;
		} else {
			this.colorImageCache.reset();
			this.colorImageCache = null;
		}
	}

	@OriginalMember(owner = "client!oc", name = "a", descriptor = "(IILclient!iv;)V")
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
	}

@OriginalMember(owner = "client!oc", name = "o", descriptor = "Z")
	public static boolean aBoolean664 = false;
@OriginalMember(owner = "client!oc", name = "u", descriptor = "Lclient!bg;")
	public static final Class22 CONTAINER_MAP = new Class22(12, 5);
}
