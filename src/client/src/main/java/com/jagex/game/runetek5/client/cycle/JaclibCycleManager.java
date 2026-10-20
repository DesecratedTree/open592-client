package com.jagex.game.runetek5.client.cycle;

import jagex3.jagmisc.jagmisc;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class36;

import com.jagex.game.runetek5.deob.Class259;
@OriginalClass("client!gg")
public final class JaclibCycleManager extends Class36 {

	@OriginalMember(owner = "client!gg", name = "f", descriptor = "J")
	private long aLong89 = 0L;

	@OriginalMember(owner = "client!gg", name = "g", descriptor = "J")
	private long aLong90 = 0L;

	@OriginalMember(owner = "client!gg", name = "i", descriptor = "I")
	private int cachePos = 0;

	@OriginalMember(owner = "client!gg", name = "j", descriptor = "I")
	private int entryCount = 1;

	@OriginalMember(owner = "client!gg", name = "h", descriptor = "[J")
	private final long[] aLongArray5 = new long[10];

	@OriginalMember(owner = "client!gg", name = "k", descriptor = "J")
	private long aLong91 = 0L;

	@OriginalMember(owner = "client!gg", name = "<init>", descriptor = "()V")
	public JaclibCycleManager() {
		this.aLong90 = this.aLong89 = jagmisc.nanoTime();
		if (this.aLong89 == 0L) {
			throw new RuntimeException();
		}
	}

	@OriginalMember(owner = "client!gg", name = "c", descriptor = "(B)J")
	@Override
	public long method2252() {
		return this.aLong89;
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(I)J")
	private long averageExecutionTime() {
		@Pc(1) long local1 = jagmisc.nanoTime();
		@Pc(7) long local7 = local1 - this.aLong91;
		this.aLong91 = local1;
		if (local7 > -5000000000L && local7 < 5000000000L) {
			this.aLongArray5[this.cachePos] = local7;
			this.cachePos = (this.cachePos + 1) % 10;
			if (this.entryCount < 1) {
				this.entryCount++;
			}
		}
		@Pc(48) long local48 = 0L;
		for (@Pc(55) int local55 = 1; local55 <= this.entryCount; local55++) {
			local48 += this.aLongArray5[(this.cachePos + 10 - local55) % 10];
		}
		return local48 / (long) this.entryCount;
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(ZI)I")
	@Override
	public int method2253(@OriginalArg(1) int arg0) {
		@Pc(9) long local9 = (long) arg0 * 1000000L;
		this.aLong89 += this.averageExecutionTime();
		if (this.aLong90 > this.aLong89) {
			Class259.method5503((this.aLong90 - this.aLong89) / 1000000L);
			this.aLong91 += this.aLong90 - this.aLong89;
			this.aLong89 += this.aLong90 - this.aLong89;
			this.aLong90 += local9;
			return 1;
		}
		@Pc(29) int local29 = 0;
		do {
			local29++;
			this.aLong90 += local9;
		} while (local29 < 10 && this.aLong89 > this.aLong90);
		if (this.aLong89 > this.aLong90) {
			this.aLong90 = this.aLong89;
		}
		return local29;
	}

	@OriginalMember(owner = "client!gg", name = "a", descriptor = "(B)V")
	@Override
	public void method2248() {
		this.aLong91 = 0L;
		if (this.aLong89 < this.aLong90) {
			this.aLong89 += this.aLong90 - this.aLong89;
		}
	}
}
