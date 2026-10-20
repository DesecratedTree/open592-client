package com.jagex.game.runetek5.toolkit.opengl.buffer;

import jaclib.memory.NativeBuffer;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

@OriginalClass("client!rr")
public class GlBuffer {

	@OriginalMember(owner = "client!rr", name = "a", descriptor = "Lclient!fd;")
	private final GlToolkit toolkit;

	@OriginalMember(owner = "client!rr", name = "b", descriptor = "Lclient!jaggl/memory/NativeBuffer;")
	protected NativeBuffer aNativeBuffer4;

	@OriginalMember(owner = "client!rr", name = "<init>", descriptor = "(Lclient!fd;[BI)V")
	protected GlBuffer(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) byte[] data, @OriginalArg(2) int initialCapacity) {
		this.toolkit = glToolkit;
		this.aNativeBuffer4 = this.toolkit.aNativeHeap2.a(initialCapacity);
		if (data != null) {
			this.aNativeBuffer4.a(data, 0, initialCapacity);
		}
	}

	@OriginalMember(owner = "client!rr", name = "<init>", descriptor = "(Lclient!fd;Lclient!jaggl/memory/NativeBuffer;)V")
	protected GlBuffer(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) NativeBuffer arg1) {
		this.toolkit = arg0;
		this.aNativeBuffer4 = arg1;
	}

	@OriginalMember(owner = "client!rr", name = "a", descriptor = "([BI)V")
	protected final void put(@OriginalArg(0) byte[] data, @OriginalArg(1) int requiredSize) {
		if (this.aNativeBuffer4 == null || this.aNativeBuffer4.d < requiredSize) {
			this.aNativeBuffer4 = this.toolkit.aNativeHeap2.a(requiredSize);
		}
		this.aNativeBuffer4.a(data, 0, requiredSize);
	}
}
