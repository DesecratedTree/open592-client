package com.jagex.game.runetek5.core.compress;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.core.compress.*;
@OriginalClass("client!cu")
public final class BzipDecompressor {

	@OriginalMember(owner = "client!cu", name = "a", descriptor = "I")
	public int k0;

	@OriginalMember(owner = "client!cu", name = "d", descriptor = "I")
	public int strmTotalInLo32;

	@OriginalMember(owner = "client!cu", name = "e", descriptor = "I")
	public int tPos;

	@OriginalMember(owner = "client!cu", name = "j", descriptor = "I")
	public int nblockused;

	@OriginalMember(owner = "client!cu", name = "k", descriptor = "I")
	public int stateOutLen;

	@OriginalMember(owner = "client!cu", name = "p", descriptor = "I")
	public int origPtr;

	@OriginalMember(owner = "client!cu", name = "r", descriptor = "B")
	public byte stateOutCh;

	@OriginalMember(owner = "client!cu", name = "u", descriptor = "I")
	public int strmTotalOutLo32;

	@OriginalMember(owner = "client!cu", name = "v", descriptor = "I")
	public int bsBuff;

	@OriginalMember(owner = "client!cu", name = "x", descriptor = "[B")
	public byte[] output;

	@OriginalMember(owner = "client!cu", name = "y", descriptor = "I")
	public int blockSize100k;

	@OriginalMember(owner = "client!cu", name = "B", descriptor = "I")
	public int bsLive;

	@OriginalMember(owner = "client!cu", name = "D", descriptor = "I")
	public int nInUse;

	@OriginalMember(owner = "client!cu", name = "E", descriptor = "I")
	public int availableOut;

	@OriginalMember(owner = "client!cu", name = "G", descriptor = "[B")
	public byte[] input;

	@OriginalMember(owner = "client!cu", name = "J", descriptor = "I")
	public int saveNblock;

	@OriginalMember(owner = "client!cu", name = "c", descriptor = "[Z")
	public final boolean[] inUse = new boolean[256];

	@OriginalMember(owner = "client!cu", name = "h", descriptor = "[B")
	public final byte[] selector = new byte[18002];

	@OriginalMember(owner = "client!cu", name = "g", descriptor = "[Z")
	public final boolean[] inUse16 = new boolean[16];

	@OriginalMember(owner = "client!cu", name = "l", descriptor = "I")
	public int strmNextInPtr = 0;

	@OriginalMember(owner = "client!cu", name = "t", descriptor = "I")
	public int strmNextOutPtr = 0;

	@OriginalMember(owner = "client!cu", name = "s", descriptor = "[I")
	public final int[] minLens = new int[6];

	@OriginalMember(owner = "client!cu", name = "m", descriptor = "[[I")
	public final int[][] base = new int[6][258];

	@OriginalMember(owner = "client!cu", name = "z", descriptor = "[[I")
	public final int[][] perm = new int[6][258];

	@OriginalMember(owner = "client!cu", name = "w", descriptor = "[I")
	public final int[] cftab = new int[257];

	@OriginalMember(owner = "client!cu", name = "C", descriptor = "[[I")
	public final int[][] limit = new int[6][258];

	@OriginalMember(owner = "client!cu", name = "b", descriptor = "[B")
	public final byte[] selectorMtf = new byte[18002];

	@OriginalMember(owner = "client!cu", name = "o", descriptor = "[[B")
	public final byte[][] len = new byte[6][258];

	@OriginalMember(owner = "client!cu", name = "F", descriptor = "[B")
	public final byte[] seqToUnseq = new byte[256];

	@OriginalMember(owner = "client!cu", name = "I", descriptor = "[I")
	public final int[] unzftab = new int[256];

	@OriginalMember(owner = "client!cu", name = "f", descriptor = "[B")
	public final byte[] mtfa = new byte[4096];

	@OriginalMember(owner = "client!cu", name = "H", descriptor = "[I")
	public final int[] mtfbase = new int[16];

@OriginalMember(owner = "client!cu", name = "i", descriptor = "S")
	public static short aShort13 = 320;
@OriginalMember(owner = "client!cu", name = "n", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_30 = new Class83("Loaded client variable data", "Client-Variablen geladen", "Variables du client chargées", "As variáveis do sistema foram carregadas");
@OriginalMember(owner = "client!cu", name = "A", descriptor = "[I")
	public static final int[] anIntArray65 = new int[5];
@OriginalMember(owner = "client!cu", name = "a", descriptor = "(ILclient!iv;)Lclient!gu;")
	public static Class27_Sub2 method994(@OriginalArg(1) Packet arg0) {
		return new Class27_Sub2(arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2512(), arg0.method2493(), arg0.method2490());
	}
}
