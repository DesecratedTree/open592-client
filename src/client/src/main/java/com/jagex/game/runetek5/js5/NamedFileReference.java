package com.jagex.game.runetek5.js5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.js5.*;
@OriginalClass("client!ud")
public final class NamedFileReference {

	@OriginalMember(owner = "client!ud", name = "c", descriptor = "[I")
	private final int[] nameHashes;

	@OriginalMember(owner = "client!ud", name = "<init>", descriptor = "([I)V")
	public NamedFileReference(@OriginalArg(0) int[] hashes) {
		@Pc(5) int local5;
		for (local5 = 1; local5 <= (hashes.length >> 1) + hashes.length; local5 <<= 0x1) {
		}
		this.nameHashes = new int[local5 + local5];
		for (@Pc(28) int local28 = 0; local28 < local5 + local5; local28++) {
			this.nameHashes[local28] = -1;
		}
		@Pc(47) int local47 = 0;
		while (local47 < hashes.length) {
			@Pc(57) int local57;
			for (local57 = hashes[local47] & local5 - 1; this.nameHashes[local57 + local57 + 1] != -1; local57 = local5 - 1 & local57 + 1) {
			}
			this.nameHashes[local57 + local57] = hashes[local47];
			this.nameHashes[local57 + local57 + 1] = local47++;
		}
	}

	@OriginalMember(owner = "client!ud", name = "a", descriptor = "(II)I")
	public int method5174(@OriginalArg(1) int arg0) {
		@Pc(19) int local19 = (this.nameHashes.length >> 1) - 1;
		@Pc(23) int local23 = local19 & arg0;
		while (true) {
			@Pc(32) int local32 = this.nameHashes[local23 + local23 + 1];
			if (local32 == -1) {
				return -1;
			}
			if (this.nameHashes[local23 + local23] == arg0) {
				return local32;
			}
			local23 = local19 & local23 + 1;
		}
	}

@OriginalMember(owner = "client!ud", name = "e", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_300 = new Class22(109, 0);
@OriginalMember(owner = "client!ud", name = "f", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_138 = new Class83("Loaded interface scripts", "Interface-Skripte geladen", "Interfaces chargées", "Interfaces carregadas");
@OriginalMember(owner = "client!ud", name = "g", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_301 = new Class22(60, -1);
@OriginalMember(owner = "client!ud", name = "a", descriptor = "(IIB)Z")
	public static boolean method5172(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return (arg1 & 0x800) != 0;
	}
}
