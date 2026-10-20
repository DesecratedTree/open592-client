package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!q")
public class Class48_Sub1 extends Class48 {

	@OriginalMember(owner = "client!q", name = "u", descriptor = "I")
	private int anInt4864;

	@OriginalMember(owner = "client!q", name = "w", descriptor = "I")
	private int anInt4866;

	@OriginalMember(owner = "client!q", name = "A", descriptor = "I")
	private int anInt4870;

	@OriginalMember(owner = "client!q", name = "D", descriptor = "[B")
	private byte[] aByteArray60;

	@OriginalMember(owner = "client!q", name = "E", descriptor = "I")
	private int anInt4873;

	@OriginalMember(owner = "client!q", name = "t", descriptor = "I")
	private final int anInt4863;

	@OriginalMember(owner = "client!q", name = "s", descriptor = "I")
	private final int anInt4862;

	@OriginalMember(owner = "client!q", name = "x", descriptor = "I")
	private final int anInt4867;

	@OriginalMember(owner = "client!q", name = "z", descriptor = "I")
	private int anInt4869;

	@OriginalMember(owner = "client!q", name = "<init>", descriptor = "(IIIIIFFF)V")
	protected Class48_Sub1(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) float arg5, @OriginalArg(6) float arg6, @OriginalArg(7) float arg7) {
		super(arg0, arg1, arg2, arg3, arg4);
		this.anInt4863 = (int) (arg6 * 4096.0F);
		this.anInt4862 = (int) (arg7 * 4096.0F);
		this.anInt4869 = this.anInt4867 = (int) (Math.pow(0.5D, (double) -arg5) * 4096.0D);
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(IIZ)V")
	@Override
	protected final void accumulateOctave(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (arg0 == 0) {
			this.anInt4873 = this.anInt4863 - (arg1 >= 0 ? arg1 : -arg1);
			this.anInt4866 = 4096;
			this.anInt4873 = this.anInt4873 * this.anInt4873 >> 12;
			this.anInt4864 = this.anInt4873;
			return;
		}
		this.anInt4866 = this.anInt4873 * this.anInt4862 >> 12;
		this.anInt4873 = this.anInt4863 - (arg1 < 0 ? -arg1 : arg1);
		if (this.anInt4866 < 0) {
			this.anInt4866 = 0;
		} else if (this.anInt4866 > 4096) {
			this.anInt4866 = 4096;
		}
		this.anInt4873 = this.anInt4873 * this.anInt4873 >> 12;
		this.anInt4873 = this.anInt4866 * this.anInt4873 >> 12;
		this.anInt4864 += this.anInt4873 * this.anInt4869 >> 12;
		this.anInt4869 = this.anInt4869 * this.anInt4867 >> 12;
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(BII)V")
	protected void method3752(@OriginalArg(0) byte arg0, @OriginalArg(1) int arg1) {
		this.aByteArray60[arg1] = arg0;
	}

	@OriginalMember(owner = "client!q", name = "d", descriptor = "(I)V")
	@Override
	protected final void reset() {
		this.anInt4864 = 0;
		this.anInt4870 = 0;
	}

	@OriginalMember(owner = "client!q", name = "a", descriptor = "(B)V")
	@Override
	protected final void emitTexel() {
		this.anInt4864 >>= 0x4;
		this.anInt4869 = this.anInt4867;
		if (this.anInt4864 < 0) {
			this.anInt4864 = 0;
		} else if (this.anInt4864 > 255) {
			this.anInt4864 = 255;
		}
		this.method3752((byte) this.anInt4864, this.anInt4870++);
		this.anInt4864 = 0;
	}

@OriginalMember(owner = "client!q", name = "r", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_101 = new Class83("yellow:", "gelb:", "jaune:", "amarelo:");
@OriginalMember(owner = "client!q", name = "a", descriptor = "(FIFF)F")
	public static float method3751(@OriginalArg(0) float arg0, @OriginalArg(2) float arg1, @OriginalArg(3) float arg2) {
		return (arg2 - arg1) * arg0 + arg1;
	}
@OriginalMember(owner = "client!q", name = "a", descriptor = "(ICI)C")
	public static char method3753(@OriginalArg(1) char arg0, @OriginalArg(2) int arg1) {
		if (arg0 >= 'À' && arg0 <= 'ÿ') {
			if (arg0 >= 'À' && arg0 <= 'Æ') {
				return 'A';
			}
			if (arg0 == 'Ç') {
				return 'C';
			}
			if (arg0 >= 'È' && arg0 <= 'Ë') {
				return 'E';
			}
			if (arg0 >= 'Ì' && arg0 <= 'Ï') {
				return 'I';
			}
			if (arg0 == 'Ñ' && arg1 != 0) {
				return 'N';
			}
			if (arg0 >= 'Ò' && arg0 <= 'Ö') {
				return 'O';
			}
			if (arg0 >= 'Ù' && arg0 <= 'Ü') {
				return 'U';
			}
			if (arg0 == 'Ý') {
				return 'Y';
			}
			if (arg0 == 'ß') {
				return 's';
			}
			if (arg0 >= 'à' && arg0 <= 'æ') {
				return 'a';
			}
			if (arg0 == 'ç') {
				return 'c';
			}
			if (arg0 >= 'è' && arg0 <= 'ë') {
				return 'e';
			}
			if (arg0 >= 'ì' && arg0 <= 'ï') {
				return 'i';
			}
			if (arg0 == 'ñ' && arg1 != 0) {
				return 'n';
			}
			if (arg0 >= 'ò' && arg0 <= 'ö') {
				return 'o';
			}
			if (arg0 >= 'ù' && arg0 <= 'ü') {
				return 'u';
			}
			if (arg0 == 'ý' || arg0 == 'ÿ') {
				return 'y';
			}
		}
		if (arg0 == 'Œ') {
			return 'O';
		} else if (arg0 == 'œ') {
			return 'o';
		} else if (arg0 == 'Ÿ') {
			return 'Y';
		} else {
			return arg0;
		}
	}
}
