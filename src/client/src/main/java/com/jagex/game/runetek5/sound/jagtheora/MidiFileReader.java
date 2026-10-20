package com.jagex.game.runetek5.sound.jagtheora;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.sound.jagtheora.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiFileReader;
@OriginalClass("client!ot")
public final class MidiFileReader {

	@OriginalMember(owner = "client!ot", name = "c", descriptor = "[I")
	private int[] startPositionTrackBuffer;

	@OriginalMember(owner = "client!ot", name = "d", descriptor = "J")
	private long tempoTimeOffset;

	@OriginalMember(owner = "client!ot", name = "e", descriptor = "I")
	private int microsecondsPerBeat;

	@OriginalMember(owner = "client!ot", name = "f", descriptor = "[I")
	private int[] trackStarts;

	@OriginalMember(owner = "client!ot", name = "g", descriptor = "[I")
	public int[] trackLengths;

	@OriginalMember(owner = "client!ot", name = "h", descriptor = "I")
	public int division;

	@OriginalMember(owner = "client!ot", name = "i", descriptor = "[I")
	private int[] runningStatus;

	@OriginalMember(owner = "client!ot", name = "b", descriptor = "Lclient!iv;")
	private final Packet buffer = new Packet(null);

	@OriginalMember(owner = "client!ot", name = "<init>", descriptor = "()V")
	public MidiFileReader() {
	}

	@OriginalMember(owner = "client!ot", name = "<init>", descriptor = "([B)V")
	public MidiFileReader(@OriginalArg(0) byte[] arg0) {
		this.decode(arg0);
	}

	@OriginalMember(owner = "client!ot", name = "a", descriptor = "()V")
	public void setTrackDone() {
		this.buffer.currentPosition = -1;
	}

	@OriginalMember(owner = "client!ot", name = "a", descriptor = "(I)I")
	private int getNextEventInternal(@OriginalArg(0) int track) {
		@Pc(7) byte local7 = this.buffer.buffer[this.buffer.currentPosition];
		@Pc(13) int local13;
		if (local7 < 0) {
			local13 = local7 & 0xFF;
			this.runningStatus[track] = local13;
			this.buffer.currentPosition++;
		} else {
			local13 = this.runningStatus[track];
		}
		if (local13 != 240 && local13 != 247) {
			return this.nextEventData(track, local13);
		}
		@Pc(42) int local42 = this.buffer.method2528();
		if (local13 == 247 && local42 > 0) {
			@Pc(57) int local57 = this.buffer.buffer[this.buffer.currentPosition] & 0xFF;
			if (local57 >= 241 && local57 <= 243 || local57 == 246 || local57 == 248 || local57 >= 250 && local57 <= 252 || local57 == 254) {
				this.buffer.currentPosition++;
				this.runningStatus[track] = local57;
				return this.nextEventData(track, local57);
			}
		}
		this.buffer.currentPosition += local42;
		return 0;
	}

	@OriginalMember(owner = "client!ot", name = "b", descriptor = "(I)V")
	public void markTracePosition(@OriginalArg(0) int track) {
		this.startPositionTrackBuffer[track] = this.buffer.currentPosition;
	}

	@OriginalMember(owner = "client!ot", name = "c", descriptor = "(I)J")
	public long getTickTimestamp(@OriginalArg(0) int tickLength) {
		return this.tempoTimeOffset + (long) tickLength * (long) this.microsecondsPerBeat;
	}

	@OriginalMember(owner = "client!ot", name = "b", descriptor = "()V")
	public void clear() {
		this.buffer.buffer = null;
		this.trackStarts = null;
		this.startPositionTrackBuffer = null;
		this.trackLengths = null;
		this.runningStatus = null;
	}

	@OriginalMember(owner = "client!ot", name = "d", descriptor = "(I)V")
	public void getTrackLength(@OriginalArg(0) int track) {
		@Pc(4) int local4 = this.buffer.method2528();
		this.trackLengths[track] += local4;
	}

	@OriginalMember(owner = "client!ot", name = "a", descriptor = "(II)I")
	private int nextEventData(@OriginalArg(0) int track, @OriginalArg(1) int event) {
		@Pc(12) int local12;
		if (event != 255) {
			@Pc(78) byte local78 = MidiFileReader.messageDataByteCount[event - 128];
			local12 = event;
			if (local78 >= 1) {
				local12 = event | this.buffer.method2490() << 8;
			}
			if (local78 >= 2) {
				local12 |= this.buffer.method2490() << 16;
			}
			return local12;
		}
		@Pc(7) int local7 = this.buffer.method2490();
		local12 = this.buffer.method2528();
		if (local7 == 47) {
			this.buffer.currentPosition += local12;
			return 1;
		} else if (local7 == 81) {
			@Pc(32) int local32 = this.buffer.method2493();
			local12 -= 3;
			@Pc(38) int local38 = this.trackLengths[track];
			this.tempoTimeOffset += (long) local38 * (long) (this.microsecondsPerBeat - local32);
			this.microsecondsPerBeat = local32;
			this.buffer.currentPosition += local12;
			return 2;
		} else {
			this.buffer.currentPosition += local12;
			return 3;
		}
	}

	@OriginalMember(owner = "client!ot", name = "c", descriptor = "()Z")
	public boolean isDone() {
		@Pc(3) int local3 = this.startPositionTrackBuffer.length;
		for (@Pc(5) int local5 = 0; local5 < local3; local5++) {
			if (this.startPositionTrackBuffer[local5] >= 0) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!ot", name = "a", descriptor = "(J)V")
	public void resetTracksFromTimestamp(@OriginalArg(0) long timestamp) {
		this.tempoTimeOffset = timestamp;
		@Pc(6) int local6 = this.startPositionTrackBuffer.length;
		for (@Pc(8) int local8 = 0; local8 < local6; local8++) {
			this.trackLengths[local8] = 0;
			this.runningStatus[local8] = 0;
			this.buffer.currentPosition = this.trackStarts[local8];
			this.getTrackLength(local8);
			this.startPositionTrackBuffer[local8] = this.buffer.currentPosition;
		}
	}

	@OriginalMember(owner = "client!ot", name = "a", descriptor = "([B)V")
	public void decode(@OriginalArg(0) byte[] data) {
		this.buffer.buffer = data;
		this.buffer.currentPosition = 10;
		@Pc(12) int local12 = this.buffer.method2536();
		this.division = this.buffer.method2536();
		this.microsecondsPerBeat = 500000;
		this.trackStarts = new int[local12];
		@Pc(27) int local27 = 0;
		@Pc(33) int local33;
		while (local27 < local12) {
			local33 = this.buffer.method2529();
			@Pc(38) int local38 = this.buffer.method2529();
			if (local33 == 1297379947) {
				this.trackStarts[local27] = this.buffer.currentPosition;
				local27++;
			}
			this.buffer.currentPosition += local38;
		}
		this.tempoTimeOffset = 0L;
		this.startPositionTrackBuffer = new int[local12];
		for (local33 = 0; local33 < local12; local33++) {
			this.startPositionTrackBuffer[local33] = this.trackStarts[local33];
		}
		this.trackLengths = new int[local12];
		this.runningStatus = new int[local12];
	}

	@OriginalMember(owner = "client!ot", name = "d", descriptor = "()I")
	public int getPrioritizedTrack() {
		@Pc(3) int local3 = this.startPositionTrackBuffer.length;
		@Pc(5) int local5 = -1;
		@Pc(7) int local7 = Integer.MAX_VALUE;
		for (@Pc(9) int local9 = 0; local9 < local3; local9++) {
			if (this.startPositionTrackBuffer[local9] >= 0 && this.trackLengths[local9] < local7) {
				local5 = local9;
				local7 = this.trackLengths[local9];
			}
		}
		return local5;
	}

	@OriginalMember(owner = "client!ot", name = "e", descriptor = "(I)V")
	public void getGotoTrack(@OriginalArg(0) int track) {
		this.buffer.currentPosition = this.startPositionTrackBuffer[track];
	}

	@OriginalMember(owner = "client!ot", name = "e", descriptor = "()Z")
	public boolean dataLoaded() {
		return this.buffer.buffer != null;
	}

	@OriginalMember(owner = "client!ot", name = "f", descriptor = "(I)I")
	public int readMessage(@OriginalArg(0) int track) {
		return this.getNextEventInternal(track);
	}

	@OriginalMember(owner = "client!ot", name = "g", descriptor = "()I")
	public int getTrackCount() {
		return this.startPositionTrackBuffer.length;
	}

@OriginalMember(owner = "client!ot", name = "a", descriptor = "[B")
	public static final byte[] messageDataByteCount = new byte[] { 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
}
