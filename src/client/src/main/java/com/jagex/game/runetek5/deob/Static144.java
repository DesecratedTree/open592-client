package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


public final class Static144 {

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "I")
	private static int nextEdgeIndex;

	@OriginalMember(owner = "client!hf", name = "b", descriptor = "I")
	private static int scanlineStartX;

	@OriginalMember(owner = "client!hf", name = "c", descriptor = "I")
	private static int scanEdgeIndex;

	@OriginalMember(owner = "client!hf", name = "d", descriptor = "I")
	private static int edgeBufferSize;

	@OriginalMember(owner = "client!hf", name = "e", descriptor = "[I")
	private static int[] edgeBuffer;

	@OriginalMember(owner = "client!hf", name = "f", descriptor = "I")
	private static int activeEdgeStart;

	@OriginalMember(owner = "client!hf", name = "g", descriptor = "I")
	private static int scanlineY;

	@OriginalMember(owner = "client!hf", name = "h", descriptor = "I")
	private static int scanlineEndX;

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(Lclient!ya;[II)V")
	public static void method2432(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2) {
		fillPolygon(arg0, arg1, arg1.length, arg2, null, null);
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(II)V")
	private static void sortEdgesByY(@OriginalArg(0) int from, @OriginalArg(1) int arg1) {
		if (arg1 <= from + 4) {
			return;
		}
		@Pc(7) int local7 = from;
		@Pc(11) int local11 = edgeBuffer[from];
		@Pc(17) int local17 = edgeBuffer[from + 1];
		@Pc(23) int local23 = edgeBuffer[from + 2];
		@Pc(29) int local29 = edgeBuffer[from + 3];
		for (@Pc(33) int local33 = from + 4; local33 < arg1; local33 += 4) {
			@Pc(40) int local40 = edgeBuffer[local33 + 1];
			if (local40 < local17) {
				edgeBuffer[local7] = edgeBuffer[local33];
				edgeBuffer[local7 + 1] = local40;
				edgeBuffer[local7 + 2] = edgeBuffer[local33 + 2];
				edgeBuffer[local7 + 3] = edgeBuffer[local33 + 3];
				local7 += 4;
				edgeBuffer[local33] = edgeBuffer[local7];
				edgeBuffer[local33 + 1] = edgeBuffer[local7 + 1];
				edgeBuffer[local33 + 2] = edgeBuffer[local7 + 2];
				edgeBuffer[local33 + 3] = edgeBuffer[local7 + 3];
			}
		}
		edgeBuffer[local7] = local11;
		edgeBuffer[local7 + 1] = local17;
		edgeBuffer[local7 + 2] = local23;
		edgeBuffer[local7 + 3] = local29;
		sortEdgesByY(from, local7);
		sortEdgesByY(local7 + 4, arg1);
	}

	@OriginalMember(owner = "client!hf", name = "b", descriptor = "(II)V")
	private static void sortActiveEdgesByX(@OriginalArg(0) int from, @OriginalArg(1) int arg1) {
		while (true) {
			if (arg1 >= from + 8) {
				@Pc(2) boolean local2 = true;
				for (@Pc(6) int local6 = from + 4; local6 < arg1; local6 += 4) {
					@Pc(13) int local13 = edgeBuffer[local6 - 4];
					@Pc(17) int local17 = edgeBuffer[local6];
					if (local13 > local17) {
						local2 = false;
						edgeBuffer[local6 - 4] = local17;
						edgeBuffer[local6] = local13;
						local13 = edgeBuffer[local6 - 2];
						edgeBuffer[local6 - 2] = edgeBuffer[local6 + 2];
						edgeBuffer[local6 + 2] = local13;
						local13 = edgeBuffer[local6 - 1];
						edgeBuffer[local6 - 1] = edgeBuffer[local6 + 3];
						edgeBuffer[local6 + 3] = local13;
					}
				}
				if (!local2) {
					arg1 -= 4;
					continue;
				}
			}
			return;
		}
	}

	@OriginalMember(owner = "client!hf", name = "b", descriptor = "()V")
	private static void resetEdgeBuffer() {
		edgeBufferSize = 0;
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(I)Z")
	private static boolean advanceScanline(@OriginalArg(0) int maxY) {
		@Pc(1) int local1 = nextEdgeIndex;
		@Pc(3) int local3 = scanEdgeIndex;
		@Pc(5) int local5 = scanlineY;
		while (local3 >= local1) {
			local5++;
			scanlineY = local5;
			if (local5 >= maxY) {
				return false;
			}
			@Pc(16) int local16 = activeEdgeStart;
			@Pc(23) int local23;
			@Pc(30) int local30;
			while (local1 < edgeBufferSize) {
				local23 = edgeBuffer[local1 + 1];
				if (local5 < local23) {
					break;
				}
				local30 = edgeBuffer[local1];
				@Pc(36) int local36 = edgeBuffer[local1 + 2];
				@Pc(42) int local42 = edgeBuffer[local1 + 3];
				@Pc(52) int local52 = (local36 - local30 << 16) / (local42 - local23);
				@Pc(58) int local58 = (local30 << 16) + 32768;
				edgeBuffer[local1] = local58;
				edgeBuffer[local1 + 2] = local52;
				local1 += 4;
			}
			for (local23 = local16; local23 < local1; local23 += 4) {
				local30 = edgeBuffer[local23 + 3];
				if (local5 >= local30) {
					edgeBuffer[local23] = edgeBuffer[local16];
					edgeBuffer[local23 + 1] = edgeBuffer[local16 + 1];
					edgeBuffer[local23 + 2] = edgeBuffer[local16 + 2];
					edgeBuffer[local23 + 3] = edgeBuffer[local16 + 3];
					local16 += 4;
				}
			}
			if (local16 == edgeBufferSize) {
				edgeBufferSize = 0;
				return false;
			}
			sortActiveEdgesByX(local16, local1);
			activeEdgeStart = local16;
			nextEdgeIndex = local1;
			local3 = local16;
		}
		scanlineStartX = edgeBuffer[local3] >> 16;
		scanlineEndX = edgeBuffer[local3 + 4] >> 16;
		edgeBuffer[local3] += edgeBuffer[local3 + 2];
		edgeBuffer[local3 + 4] += edgeBuffer[local3 + 6];
		local3 += 8;
		scanEdgeIndex = local3;
		return true;
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(Lclient!ya;[IIII[I[I)V")
	private static void fillPolygon(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int[] vertices, @OriginalArg(3) int arg2, @OriginalArg(4) int count, @OriginalArg(5) int[] colour, @OriginalArg(6) int[] lineOffsets) {
		@Pc(2) int[] local2 = new int[4];
		toolkit.da(local2);
		if (colour != null && local2[3] - local2[1] != colour.length) {
			throw new IllegalStateException();
		}
		resetEdgeBuffer();
		addEdges(vertices, arg2);
		initializeScanline(local2[1]);
		while (true) {
			@Pc(33) int local33;
			@Pc(35) int local35;
			@Pc(37) int local37;
			do {
				if (!advanceScanline(local2[3])) {
					return;
				}
				local33 = scanlineStartX;
				local35 = scanlineEndX;
				local37 = scanlineY;
				if (colour == null) {
					break;
				}
				@Pc(45) int local45 = local37 - local2[1];
				if (local33 < colour[local45] + local2[0]) {
					local33 = colour[local45] + local2[0];
				}
				if (local35 > colour[local45] + lineOffsets[local45] + local2[0]) {
					local35 = colour[local45] + lineOffsets[local45] + local2[0];
				}
			} while (local35 - local33 <= 0);
			toolkit.b(local33, local37, local35 - local33, count, 1);
		}
	}

	@OriginalMember(owner = "client!hf", name = "b", descriptor = "(I)V")
	private static void initializeScanline(@OriginalArg(0) int topY) {
		if (edgeBufferSize < 0) {
			scanEdgeIndex = 0;
			nextEdgeIndex = 0;
			activeEdgeStart = 0;
			scanlineY = 2147483646;
			return;
		}
		sortEdgesByY(0, edgeBufferSize);
		@Pc(17) int local17 = edgeBuffer[1];
		if (local17 < topY) {
			local17 = topY;
		}
		@Pc(26) int local26;
		for (local26 = 0; local26 < edgeBufferSize; local26 += 4) {
			@Pc(33) int local33 = edgeBuffer[local26 + 1];
			if (local17 < local33) {
				break;
			}
			@Pc(40) int local40 = edgeBuffer[local26];
			@Pc(46) int local46 = edgeBuffer[local26 + 2];
			@Pc(52) int local52 = edgeBuffer[local26 + 3];
			@Pc(62) int local62 = (local46 - local40 << 16) / (local52 - local33);
			@Pc(68) int local68 = (local40 << 16) + 32768;
			edgeBuffer[local26] = local68 + (local17 - local33) * local62;
			edgeBuffer[local26 + 2] = local62;
		}
		activeEdgeStart = 0;
		nextEdgeIndex = local26;
		scanEdgeIndex = local26;
		scanlineY = local17 - 1;
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "(Lclient!ya;[II[I[I)V")
	public static void fillPolygonMasked(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int[] vertices, @OriginalArg(2) int colour, @OriginalArg(3) int[] lineOffsets, @OriginalArg(4) int[] lineWidths) {
		fillPolygon(toolkit, vertices, vertices.length, colour, lineOffsets, lineWidths);
	}

	@OriginalMember(owner = "client!hf", name = "a", descriptor = "([III)V")
	private static void addEdges(@OriginalArg(0) int[] vertices, @OriginalArg(2) int arg1) {
		@Pc(5) int local5 = edgeBufferSize + (arg1 << 1);
		@Pc(16) int local16;
		if (edgeBuffer == null || edgeBuffer.length < local5) {
			@Pc(14) int[] local14 = new int[local5];
			for (local16 = 0; local16 < edgeBufferSize; local16++) {
				local14[local16] = edgeBuffer[local16];
			}
			edgeBuffer = local14;
		}
		@Pc(33) int local33 = arg1;
		@Pc(37) int local37 = local33 - 2;
		for (local16 = 0; local16 < local33; local16 += 2) {
			@Pc(46) int local46 = vertices[local37 + 1];
			@Pc(52) int local52 = vertices[local16 + 1];
			if (local46 < local52) {
				edgeBuffer[edgeBufferSize++] = vertices[local37];
				edgeBuffer[edgeBufferSize++] = local46;
				edgeBuffer[edgeBufferSize++] = vertices[local16];
				edgeBuffer[edgeBufferSize++] = local52;
			} else if (local52 < local46) {
				edgeBuffer[edgeBufferSize++] = vertices[local16];
				edgeBuffer[edgeBufferSize++] = local52;
				edgeBuffer[edgeBufferSize++] = vertices[local37];
				edgeBuffer[edgeBufferSize++] = local46;
			}
			local37 = local16;
		}
	}
}
