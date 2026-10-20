package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


public final class Static459 {

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([III)V")
	public static void clear(@OriginalArg(0) int[] src, @OriginalArg(1) int off, @OriginalArg(2) int initialLen) {
		@Pc(5) int local5 = initialLen - 7;
		while (off < local5) {
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
			src[off++] = 0;
		}
		local5 += 7;
		while (off < local5) {
			src[off++] = 0;
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([IIII)V")
	public static void setVariables(@OriginalArg(0) int[] array, @OriginalArg(1) int position, @OriginalArg(2) int startPosition, @OriginalArg(3) int variableToSet) {
		@Pc(5) int local5 = position + startPosition - 7;
		while (position < local5) {
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
			array[position++] = variableToSet;
		}
		local5 += 7;
		while (position < local5) {
			array[position++] = variableToSet;
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([SI[SII)V")
	public static void copy(@OriginalArg(0) short[] src, @OriginalArg(1) int srcPos, @OriginalArg(2) short[] dest, @OriginalArg(3) int destPos, @OriginalArg(4) int length) {
		if (src == dest) {
			return;
		}
		@Pc(114) int local114 = length;
		@Pc(115) int local115 = local114 - 7;
		while (srcPos < local115) {
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
		}
		local114 = local115 + 7;
		while (srcPos < local114) {
			dest[destPos++] = src[srcPos++];
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([FI[FII)V")
	public static void copy(@OriginalArg(0) float[] src, @OriginalArg(1) int srcPos, @OriginalArg(2) float[] dest, @OriginalArg(3) int destPos, @OriginalArg(4) int length) {
		if (src == dest) {
			return;
		}
		@Pc(114) int local114 = length;
		@Pc(115) int local115 = local114 - 7;
		while (srcPos < local115) {
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
		}
		local114 = local115 + 7;
		while (srcPos < local114) {
			dest[destPos++] = src[srcPos++];
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([BI[BII)V")
	public static void copy(@OriginalArg(0) byte[] src, @OriginalArg(1) int srcPos, @OriginalArg(2) byte[] dest, @OriginalArg(3) int destPos, @OriginalArg(4) int length) {
		@Pc(15) int local15;
		if (src == dest) {
			if (srcPos == destPos) {
				return;
			}
			if (destPos > srcPos && destPos < srcPos + length) {
				local15 = length - 1;
				@Pc(19) int local19 = srcPos + local15;
				@Pc(23) int local23 = destPos + local15;
				local15 = local19 - local15;
				local15 += 7;
				while (local19 >= local15) {
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
					dest[local23--] = src[local19--];
				}
				local15 -= 7;
				while (local19 >= local15) {
					dest[local23--] = src[local19--];
				}
				return;
			}
		}
		local15 = length + srcPos;
		@Pc(115) int local115 = local15 - 7;
		while (srcPos < local115) {
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
			dest[destPos++] = src[srcPos++];
		}
		local15 = local115 + 7;
		while (srcPos < local15) {
			dest[destPos++] = src[srcPos++];
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([II[III)V")
	public static void copy(@OriginalArg(0) int[] pallete, @OriginalArg(1) int srcPos, @OriginalArg(2) int[] dest, @OriginalArg(3) int destPos, @OriginalArg(4) int length) {
		if (pallete == dest) {
			return;
		}
		@Pc(114) int local114 = length;
		@Pc(115) int local115 = local114 - 7;
		while (srcPos < local115) {
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
			dest[destPos++] = pallete[srcPos++];
		}
		local114 = local115 + 7;
		while (srcPos < local114) {
			dest[destPos++] = pallete[srcPos++];
		}
	}

	@OriginalMember(owner = "client!le", name = "a", descriptor = "([Ljava/lang/Object;I[Ljava/lang/Object;II)V")
	public static void copy(@OriginalArg(0) Object[] objects, @OriginalArg(1) int srcPos, @OriginalArg(2) Object[] objects2, @OriginalArg(3) int destPos, @OriginalArg(4) int length) {
		@Pc(15) int local15;
		if (objects == objects2) {
			if (srcPos == destPos) {
				return;
			}
			if (destPos > srcPos && destPos < srcPos + length) {
				local15 = length - 1;
				@Pc(19) int local19 = srcPos + local15;
				@Pc(23) int local23 = destPos + local15;
				local15 = local19 - local15;
				local15 += 7;
				while (local19 >= local15) {
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
					objects2[local23--] = objects[local19--];
				}
				local15 -= 7;
				while (local19 >= local15) {
					objects2[local23--] = objects[local19--];
				}
				return;
			}
		}
		local15 = length + srcPos;
		@Pc(115) int local115 = local15 - 7;
		while (srcPos < local115) {
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
			objects2[destPos++] = objects[srcPos++];
		}
		local15 = local115 + 7;
		while (srcPos < local15) {
			objects2[destPos++] = objects[srcPos++];
		}
	}
}
