package com.jagex.game.runetek5.config.sequence;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.graphics.model.Model;

import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.config.sequence.*;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.deob.Class4_Sub9;
@OriginalClass("client!lp")
public final class SeqType {

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "[I")
	public int[] durations;

	@OriginalMember(owner = "client!lp", name = "k", descriptor = "[[I")
	public int[][] frameSounds;

	@OriginalMember(owner = "client!lp", name = "m", descriptor = "Lclient!du;")
	public SeqTypeList seqtypelist;

	@OriginalMember(owner = "client!lp", name = "s", descriptor = "I")
	public int id;

	@OriginalMember(owner = "client!lp", name = "v", descriptor = "[I")
	public int[] frames;

	@OriginalMember(owner = "client!lp", name = "A", descriptor = "[I")
	private int[] interfaceFrames;

	@OriginalMember(owner = "client!lp", name = "B", descriptor = "[Z")
	public boolean[] interLeaveOrder;

	@OriginalMember(owner = "client!lp", name = "d", descriptor = "I")
	public int interruptType = -1;

	@OriginalMember(owner = "client!lp", name = "t", descriptor = "Z")
	public boolean lights = false;

	@OriginalMember(owner = "client!lp", name = "p", descriptor = "I")
	public int replayInterval = -1;

	@OriginalMember(owner = "client!lp", name = "w", descriptor = "I")
	public int anInt4412 = 99;

	@OriginalMember(owner = "client!lp", name = "i", descriptor = "Z")
	public boolean tweened = false;

	@OriginalMember(owner = "client!lp", name = "x", descriptor = "I")
	public int priority = 5;

	@OriginalMember(owner = "client!lp", name = "u", descriptor = "Z")
	public boolean aBoolean407 = false;

	@OriginalMember(owner = "client!lp", name = "h", descriptor = "I")
	public int anInt4405 = -1;

	@OriginalMember(owner = "client!lp", name = "b", descriptor = "Z")
	public boolean vorbis = false;

	@OriginalMember(owner = "client!lp", name = "n", descriptor = "I")
	public int loopType = 2;

	@OriginalMember(owner = "client!lp", name = "z", descriptor = "I")
	public int replacementWeapon = -1;

	@OriginalMember(owner = "client!lp", name = "r", descriptor = "I")
	public int moveType = -1;

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "(Lclient!iv;II)V")
	private void decode(@OriginalArg(0) Packet arg0, @OriginalArg(1) int opcode) {
		@Pc(31) int local31;
		@Pc(33) int local33;
		@Pc(149) int local149;
		@Pc(168) int local168;
		if (opcode == 1) {
			local31 = arg0.method2536();
			this.frames = new int[local31];
			for (local33 = 0; local33 < local31; local33++) {
				this.frames[local33] = arg0.method2536();
			}
			this.durations = new int[local31];
			for (local149 = 0; local149 < local31; local149++) {
				this.durations[local149] = arg0.method2536();
			}
			for (local168 = 0; local168 < local31; local168++) {
				this.durations[local168] += arg0.method2536() << 16;
			}
		} else if (opcode == 2) {
			this.anInt4405 = arg0.method2536();
		} else if (opcode == 3) {
			this.interLeaveOrder = new boolean[256];
			local31 = arg0.method2490();
			for (local33 = 0; local33 < local31; local33++) {
				this.interLeaveOrder[arg0.method2490()] = true;
			}
		} else if (opcode == 4) {
			this.aBoolean407 = true;
		} else if (opcode == 5) {
			this.priority = arg0.method2490();
		} else if (opcode == 6) {
			this.replayInterval = arg0.method2536();
		} else if (opcode == 7) {
			this.replacementWeapon = arg0.method2536();
		} else if (opcode == 8) {
			this.anInt4412 = arg0.method2490();
		} else if (opcode == 9) {
			this.interruptType = arg0.method2490();
		} else if (opcode == 10) {
			this.moveType = arg0.method2490();
		} else if (opcode == 11) {
			this.loopType = arg0.method2490();
		} else if (opcode == 12) {
			local31 = arg0.method2490();
			this.interfaceFrames = new int[local31];
			for (local33 = 0; local33 < local31; local33++) {
				this.interfaceFrames[local33] = arg0.method2536();
			}
			for (local149 = 0; local149 < local31; local149++) {
				this.interfaceFrames[local149] = (arg0.method2536() << 16) + this.interfaceFrames[local149];
			}
		} else if (opcode == 13) {
			local31 = arg0.method2536();
			this.frameSounds = new int[local31][];
			for (local33 = 0; local33 < local31; local33++) {
				local149 = arg0.method2490();
				if (local149 > 0) {
					this.frameSounds[local33] = new int[local149];
					this.frameSounds[local33][0] = arg0.method2493();
					for (local168 = 1; local168 < local149; local168++) {
						this.frameSounds[local33][local168] = arg0.method2536();
					}
				}
			}
			return;
		} else if (opcode == 14) {
			this.lights = true;
			return;
		} else if (opcode == 15) {
			this.tweened = true;
			return;
		} else if (opcode == 16) {
			this.vorbis = true;
			return;
		}
	}

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method3455(@OriginalArg(0) Packet arg0) {
		while (true) {
			@Pc(13) int local13 = arg0.method2490();
			if (local13 == 0) {
				return;
			}
			this.decode(arg0, local13);
		}
	}

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "(IIIILclient!t;I)Lclient!t;")
	public Model method3456(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) Model arg4) {
		@Pc(14) int local14 = this.frames[arg3];
		@Pc(19) int local19 = this.durations[arg3];
		@Pc(27) Class4_Sub1_Sub17 local27 = this.seqtypelist.method1391(local19 >> 16);
		@Pc(31) int local31 = local19 & 0xFFFF;
		if (local27 == null) {
			return arg4.copy((byte) 1, arg1, true);
		}
		@Pc(41) Class4_Sub1_Sub17 local41 = null;
		if ((this.tweened || OutputStream_Sub1.aBoolean244) && arg2 != -1 && this.durations.length > arg2) {
			@Pc(60) int local60 = this.durations[arg2];
			local41 = this.seqtypelist.method1391(local60 >> 16);
			arg2 = local60 & 0xFFFF;
		}
		@Pc(74) Class4_Sub1_Sub17 local74 = null;
		@Pc(76) Class4_Sub1_Sub17 local76 = null;
		@Pc(78) int local78 = 0;
		@Pc(80) int local80 = 0;
		if (this.interfaceFrames != null) {
			if (arg3 < this.interfaceFrames.length) {
				local78 = this.interfaceFrames[arg3];
				if (local78 != 65535) {
					local74 = this.seqtypelist.method1391(local78 >> 16);
					local78 &= 0xFFFF;
				}
			}
			if ((this.tweened || OutputStream_Sub1.aBoolean244) && arg2 != -1 && arg2 < this.interfaceFrames.length) {
				local80 = this.interfaceFrames[arg2];
				if (local80 != 65535) {
					local76 = this.seqtypelist.method1391(local80 >> 16);
					local80 &= 0xFFFF;
				}
			}
		}
		if (this.lights) {
			arg1 |= 0x200;
		}
		if (local27.method4207(local31)) {
			arg1 |= 0x80;
		}
		if (local27.method4214(local31)) {
			arg1 |= 0x100;
		}
		if (local27.method4215(local31)) {
			arg1 |= 0x400;
		}
		if (local74 != null) {
			if (local74.method4207(local78)) {
				arg1 |= 0x80;
			}
			if (local74.method4214(local78)) {
				arg1 |= 0x100;
			}
			if (local74.method4215(local78)) {
				arg1 |= 0x400;
			}
		}
		if (local41 != null) {
			if (local41.method4207(arg2)) {
				arg1 |= 0x80;
			}
			if (local41.method4214(arg2)) {
				arg1 |= 0x100;
			}
			if (local41.method4215(arg2)) {
				arg1 |= 0x400;
			}
		}
		if (local76 != null) {
			if (local76.method4207(local80)) {
				arg1 |= 0x80;
			}
			if (local76.method4214(local80)) {
				arg1 |= 0x100;
			}
			if (local76.method4215(local80)) {
				arg1 |= 0x400;
			}
		}
		arg1 |= 0x20;
		@Pc(281) Model local281 = arg4.copy((byte) 1, arg1, true);
		local281.method4883(0, local14, this.lights, local31, arg2, local27, local41, arg0 - 1);
		if (local74 != null) {
			local281.method4883(0, local14, this.lights, local78, local80, local74, local76, arg0 - 1);
		}
		return local281;
	}

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "(IIIZ)I")
	public int getFunctionMask(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) boolean arg2) {
		@Pc(5) int local5 = 0;
		@Pc(7) int local7 = 0;
		@Pc(20) int local20 = this.durations[arg0];
		@Pc(22) Class4_Sub1_Sub17 local22 = null;
		@Pc(30) Class4_Sub1_Sub17 local30 = this.seqtypelist.method1391(local20 >> 16);
		@Pc(34) int local34 = local20 & 0xFFFF;
		if (local30 == null) {
			return 0;
		}
		if ((this.tweened || OutputStream_Sub1.aBoolean244) && arg1 != -1 && arg1 < this.durations.length) {
			local7 = this.durations[arg1];
			local22 = this.seqtypelist.method1391(local7 >> 16);
			local7 &= 0xFFFF;
		}
		if (this.lights) {
			local5 = 512;
		}
		if (local30.method4207(local34)) {
			local5 |= 0x80;
		}
		if (local30.method4214(local34)) {
			local5 |= 0x100;
		}
		if (local30.method4215(local34)) {
			local5 |= 0x400;
		}
		if (local22 != null) {
			if (local22.method4207(local7)) {
				local5 |= 0x80;
			}
			if (local22.method4214(local7)) {
				local5 |= 0x100;
			}
			if (local22.method4215(local7)) {
				local5 |= 0x400;
			}
		}
		if (this.interfaceFrames != null && arg2) {
			@Pc(151) int local151;
			@Pc(164) Class4_Sub1_Sub17 local164;
			if (this.interfaceFrames.length > arg0) {
				local151 = this.interfaceFrames[arg0];
				if (local151 != 65535) {
					local164 = this.seqtypelist.method1391(local151 >> 16);
					local151 &= 0xFFFF;
					if (local164 != null) {
						if (local164.method4207(local151)) {
							local5 |= 0x80;
						}
						if (local164.method4214(local151)) {
							local5 |= 0x100;
						}
						if (local164.method4215(local151)) {
							local5 |= 0x400;
						}
					}
				}
			}
			if ((this.tweened || OutputStream_Sub1.aBoolean244) && arg1 != -1 && this.interfaceFrames.length > arg1) {
				local151 = this.interfaceFrames[arg1];
				if (local151 != 65535) {
					local164 = this.seqtypelist.method1391(local151 >> 16);
					local151 &= 0xFFFF;
					if (local164 != null) {
						if (local164.method4207(local151)) {
							local5 |= 0x80;
						}
						if (local164.method4214(local151)) {
							local5 |= 0x100;
						}
						if (local164.method4215(local151)) {
							local5 |= 0x400;
						}
					}
				}
			}
		}
		return local5 | 0x20;
	}

	@OriginalMember(owner = "client!lp", name = "a", descriptor = "(IBLclient!t;IIIII)Lclient!t;")
	public Model method3459(@OriginalArg(0) int arg0, @OriginalArg(1) byte arg1, @OriginalArg(2) Model arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6) {
		@Pc(8) int local8 = this.frames[arg0];
		@Pc(13) int local13 = this.durations[arg0];
		@Pc(23) Class4_Sub1_Sub17 local23 = this.seqtypelist.method1391(local13 >> 16);
		@Pc(27) int local27 = local13 & 0xFFFF;
		if (local23 == null) {
			return arg2.copy(arg1, arg6, true);
		}
		@Pc(37) Class4_Sub1_Sub17 local37 = null;
		if ((this.tweened || OutputStream_Sub1.aBoolean244) && arg4 != -1 && arg4 < this.durations.length) {
			@Pc(55) int local55 = this.durations[arg4];
			local37 = this.seqtypelist.method1391(local55 >> 16);
			arg4 = local55 & 0xFFFF;
		}
		if (this.lights) {
			arg6 |= 0x200;
		}
		if (local23.method4207(local27)) {
			arg6 |= 0x80;
		}
		if (local23.method4214(local27)) {
			arg6 |= 0x100;
		}
		if (local23.method4215(local27)) {
			arg6 |= 0x400;
		}
		if (local37 != null) {
			if (local37.method4207(arg4)) {
				arg6 |= 0x80;
			}
			if (local37.method4214(arg4)) {
				arg6 |= 0x100;
			}
			if (local37.method4215(arg4)) {
				arg6 |= 0x400;
			}
		}
		arg6 |= 0x20;
		@Pc(150) Model local150 = arg2.copy(arg1, arg6, true);
		local150.method4883(arg5, local8, this.lights, local27, arg4, local23, local37, arg3 - 1);
		return local150;
	}

	@OriginalMember(owner = "client!lp", name = "b", descriptor = "(I)V")
	public void postDecode() {
		if (this.interruptType == -1) {
			if (this.interLeaveOrder == null) {
				this.interruptType = 0;
			} else {
				this.interruptType = 2;
			}
		}
		if (this.moveType != -1) {
			return;
		}
		if (this.interLeaveOrder == null) {
			this.moveType = 0;
		} else {
			this.moveType = 2;
		}
	}

@OriginalMember(owner = "client!lp", name = "j", descriptor = "Lclient!l;")
	public static NativeSprite aClass57_18;
@OriginalMember(owner = "client!lp", name = "l", descriptor = "Lclient!sp;")
	public static final ModeWhere aClass219_4 = new ModeWhere("WTI", 5);
@OriginalMember(owner = "client!lp", name = "o", descriptor = "I")
	public static int p12FullIndex = 0;
@OriginalMember(owner = "client!lp", name = "a", descriptor = "(I)V")
	public static void method3458() {
		@Pc(12) int local12 = Class4_Sub7.anInt606;
		@Pc(14) int[] local14 = Static99.anIntArray157;
		for (@Pc(16) int local16 = 0; local16 < local12; local16++) {
			@Pc(24) PlayerEntity local24 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local14[local16]];
			if (local24 != null) {
				Class4_Sub9.method761(local24.getSize(), local24);
			}
		}
	}
}
