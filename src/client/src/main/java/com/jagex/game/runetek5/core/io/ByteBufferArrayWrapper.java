package com.jagex.game.runetek5.core.io;

import java.nio.ByteBuffer;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ByteArrayWrapper;

@OriginalClass("client!ce")
public final class ByteBufferArrayWrapper extends ByteArrayWrapper {

	@OriginalMember(owner = "client!ce", name = "h", descriptor = "Ljava/nio/ByteBuffer;")
	private ByteBuffer buffer;

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(III)[B")
	@Override
	public byte[] readBlock(@OriginalArg(0) int arg0) {
		this.buffer.position(arg0);
		@Pc(14) byte[] local14 = new byte[32768];
		this.buffer.get(local14, 0, 32768);
		return local14;
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(B)[B")
	@Override
	public byte[] get() {
		@Pc(12) byte[] local12 = new byte[this.buffer.capacity()];
		this.buffer.position(0);
		this.buffer.get(local12);
		return local12;
	}

	@OriginalMember(owner = "client!ce", name = "a", descriptor = "(I[B)V")
	@Override
	public void set(@OriginalArg(1) byte[] arg0) {
		this.buffer = ByteBuffer.allocateDirect(arg0.length);
		this.buffer.position(0);
		this.buffer.put(arg0);
	}
}
