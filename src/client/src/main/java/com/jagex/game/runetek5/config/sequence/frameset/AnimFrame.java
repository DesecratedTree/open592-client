package com.jagex.game.runetek5.config.sequence.frameset;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.sequence.frameset.*;
import com.jagex.game.runetek5.config.sequence.frameset.AnimFrame;
@OriginalClass("client!gr")
public final class AnimFrame {

	@OriginalMember(owner = "client!gr", name = "k", descriptor = "Z")
	public boolean aBoolean250 = false;

	@OriginalMember(owner = "client!gr", name = "e", descriptor = "Lclient!nk;")
	public AnimBase base = null;

	@OriginalMember(owner = "client!gr", name = "o", descriptor = "Z")
	public boolean modifiesAlpha = false;

	@OriginalMember(owner = "client!gr", name = "q", descriptor = "Z")
	public boolean aBoolean252 = false;

	@OriginalMember(owner = "client!gr", name = "m", descriptor = "I")
	public int numTransformations = 0;

	@OriginalMember(owner = "client!gr", name = "i", descriptor = "[S")
	public short[] baseIndices;

	@OriginalMember(owner = "client!gr", name = "c", descriptor = "[S")
	public short[] transformationX;

	@OriginalMember(owner = "client!gr", name = "g", descriptor = "[S")
	public short[] transformationY;

	@OriginalMember(owner = "client!gr", name = "p", descriptor = "[S")
	public short[] transformationZ;

	@OriginalMember(owner = "client!gr", name = "h", descriptor = "[S")
	public short[] labels;

	@OriginalMember(owner = "client!gr", name = "n", descriptor = "[B")
	public byte[] tweeningProperties;

	@OriginalMember(owner = "client!gr", name = "<init>", descriptor = "([BLclient!nk;)V")
	public AnimFrame(@OriginalArg(0) byte[] data, @OriginalArg(1) AnimBase animBase) {
		this.base = animBase;
		try {
			@Pc(24) Packet local24 = new Packet(data);
			@Pc(29) Packet local29 = new Packet(data);
			local24.currentPosition = 2;
			@Pc(36) int local36 = local24.method2490();
			@Pc(38) int local38 = 0;
			@Pc(40) int local40 = -1;
			@Pc(42) int local42 = -1;
			local29.currentPosition = local24.currentPosition + local36;
			@Pc(57) int local57;
			for (@Pc(50) int local50 = 0; local50 < local36; local50++) {
				local57 = this.base.transformationType[local50];
				if (local57 == 0) {
					local40 = local50;
				}
				@Pc(65) int local65 = local24.method2490();
				if (local65 > 0) {
					if (local57 == 0) {
						local42 = local50;
					}
					AnimFrame.INDICES[local38] = (short) local50;
					@Pc(78) short local78 = 0;
					if (local57 == 3 || local57 == 10) {
						local78 = 128;
					}
					if ((local65 & 0x1) == 0) {
						AnimFrame.TRANSFORM_X[local38] = local78;
					} else {
						AnimFrame.TRANSFORM_X[local38] = (short) local29.method2502();
					}
					if ((local65 & 0x2) == 0) {
						AnimFrame.TRANSFORM_Y[local38] = local78;
					} else {
						AnimFrame.TRANSFORM_Y[local38] = (short) local29.method2502();
					}
					if ((local65 & 0x4) == 0) {
						AnimFrame.TRANSFORM_Z[local38] = local78;
					} else {
						AnimFrame.TRANSFORM_Z[local38] = (short) local29.method2502();
					}
					AnimFrame.FLAGS[local38] = (byte) (local65 >>> 3 & 0x3);
					if (local57 == 2 || local57 == 9) {
						AnimFrame.TRANSFORM_X[local38] = (short) (((AnimFrame.TRANSFORM_X[local38] & 0xFF) << 3) + (AnimFrame.TRANSFORM_X[local38] >> 8 & 0x7));
						AnimFrame.TRANSFORM_Y[local38] = (short) (((AnimFrame.TRANSFORM_Y[local38] & 0xFF) << 3) + (AnimFrame.TRANSFORM_Y[local38] >> 8 & 0x7));
						AnimFrame.TRANSFORM_Z[local38] = (short) (((AnimFrame.TRANSFORM_Z[local38] & 0xFF) << 3) + (AnimFrame.TRANSFORM_Z[local38] >> 8 & 0x7));
						AnimFrame.TRANSFORM_X[local38] = (short) (AnimFrame.TRANSFORM_X[local38] << 3);
						AnimFrame.TRANSFORM_Y[local38] = (short) (AnimFrame.TRANSFORM_Y[local38] << 3);
						AnimFrame.TRANSFORM_Z[local38] = (short) (AnimFrame.TRANSFORM_Z[local38] << 3);
					}
					AnimFrame.SKIPPED[local38] = -1;
					if (local57 == 1 || local57 == 2 || local57 == 3) {
						if (local40 > local42) {
							AnimFrame.SKIPPED[local38] = (short) local40;
							local42 = local40;
						}
					} else if (local57 == 5) {
						this.modifiesAlpha = true;
					} else if (local57 == 7) {
						this.aBoolean252 = true;
					} else if (local57 == 9 || local57 == 10 || local57 == 8) {
						this.aBoolean250 = true;
					}
					local38++;
				}
			}
			if (local29.currentPosition != data.length) {
				throw new RuntimeException();
			}
			this.numTransformations = local38;
			this.baseIndices = new short[local38];
			this.transformationX = new short[local38];
			this.transformationY = new short[local38];
			this.transformationZ = new short[local38];
			this.labels = new short[local38];
			this.tweeningProperties = new byte[local38];
			for (local57 = 0; local57 < local38; local57++) {
				this.baseIndices[local57] = AnimFrame.INDICES[local57];
				this.transformationX[local57] = AnimFrame.TRANSFORM_X[local57];
				this.transformationY[local57] = AnimFrame.TRANSFORM_Y[local57];
				this.transformationZ[local57] = AnimFrame.TRANSFORM_Z[local57];
				this.labels[local57] = AnimFrame.SKIPPED[local57];
				this.tweeningProperties[local57] = AnimFrame.FLAGS[local57];
			}
		} catch (@Pc(372) Exception local372) {
			this.numTransformations = 0;
			this.modifiesAlpha = false;
			this.aBoolean252 = false;
		}
	}

@OriginalMember(owner = "client!gr", name = "a", descriptor = "[B")
	public static final byte[] FLAGS = new byte[500];
@OriginalMember(owner = "client!gr", name = "b", descriptor = "[S")
	public static final short[] TRANSFORM_X = new short[500];
@OriginalMember(owner = "client!gr", name = "d", descriptor = "[S")
	public static final short[] SKIPPED = new short[500];
@OriginalMember(owner = "client!gr", name = "f", descriptor = "[S")
	public static final short[] TRANSFORM_Y = new short[500];
@OriginalMember(owner = "client!gr", name = "j", descriptor = "[S")
	public static final short[] INDICES = new short[500];
@OriginalMember(owner = "client!gr", name = "l", descriptor = "[S")
	public static final short[] TRANSFORM_Z = new short[500];
}
