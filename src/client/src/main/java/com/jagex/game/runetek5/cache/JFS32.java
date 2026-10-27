package com.jagex.game.runetek5.cache;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


@OriginalClass("client!rp")
public final class JFS32 {

	@OriginalMember(owner = "client!rp", name = "a", descriptor = "Ljava/io/File;")
	private File aFile1;

	@OriginalMember(owner = "client!rp", name = "b", descriptor = "Ljava/io/RandomAccessFile;")
	private RandomAccessFile rwFile;

	@OriginalMember(owner = "client!rp", name = "c", descriptor = "J")
	private long aLong200;

	@OriginalMember(owner = "client!rp", name = "d", descriptor = "J")
	private long aLong201;

	@OriginalMember(owner = "client!rp", name = "<init>", descriptor = "()V")
	private JFS32() {
	}

	/**
	 * Opens a file for the reads and writes the game's own preferences make
	 * ({@code Class50_Sub1} for graphics options, {@code Class190} and
	 * {@code PcmPlayer} for sound). Stock opened this through SignLink's
	 * worker; without the constructor those callers had no way to get a
	 * {@code JFS32} and every load and save failed silently.
	 *
	 * <p>{@code maxLength} bounds a write, exactly like the loader's copy of
	 * this class: the file is discarded when it is already longer than that,
	 * so a corrupt or oversized preferences file starts clean instead of
	 * hitting the bound on the first save.
	 */
	@OriginalMember(owner = "client!rp", name = "<init>", descriptor = "(Ljava/io/File;Ljava/lang/String;J)V")
	public JFS32(@OriginalArg(0) File file, @OriginalArg(1) String mode, @OriginalArg(2) long maxLength) throws IOException {
		if (maxLength == -1L) {
			maxLength = Long.MAX_VALUE;
		}
		if (maxLength < file.length()) {
			file.delete();
		}
		this.aFile1 = file;
		this.rwFile = new RandomAccessFile(file, mode);
		this.aLong201 = maxLength;
		this.aLong200 = 0L;
		@Pc(37) int firstByte = this.rwFile.read();
		if (firstByte != -1 && !mode.equals("r")) {
			this.rwFile.seek(0L);
			this.rwFile.write(firstByte);
		}
		this.rwFile.seek(0L);
	}

	@OriginalMember(owner = "client!rp", name = "a", descriptor = "(I)V")
	public void method4713() throws IOException {
		if (this.rwFile != null) {
			this.rwFile.close();
			this.rwFile = null;
		}
	}

	@OriginalMember(owner = "client!rp", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() throws Throwable {
		if (this.rwFile != null) {
			System.out.println("Warning! fileondisk " + this.aFile1 + " not closed correctly using close(). Auto-closing instead. ");
			this.method4713();
		}
	}

	@OriginalMember(owner = "client!rp", name = "a", descriptor = "(II[BI)V")
	public void method4714(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2) throws IOException {
		if (this.aLong201 < this.aLong200 + (long) arg1) {
			this.rwFile.seek(this.aLong201);
			this.rwFile.write(1);
			throw new EOFException();
		} else {
			this.rwFile.write(arg2, arg0, arg1);
			this.aLong200 += arg1;
		}
	}

	@OriginalMember(owner = "client!rp", name = "a", descriptor = "(II[BB)I")
	public int method4715(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) byte[] arg2) throws IOException {
		@Pc(6) int local6 = this.rwFile.read(arg2, arg0, arg1);
		if (local6 > 0) {
			this.aLong200 += local6;
		}
		return local6;
	}

	@OriginalMember(owner = "client!rp", name = "b", descriptor = "(I)Ljava/io/File;")
	public File method4716() {
		return this.aFile1;
	}

	@OriginalMember(owner = "client!rp", name = "c", descriptor = "(I)J")
	public long method4717() throws IOException {
		return this.rwFile.length();
	}

	@OriginalMember(owner = "client!rp", name = "a", descriptor = "(JI)V")
	public void method4718(@OriginalArg(0) long arg0) throws IOException {
		this.rwFile.seek(arg0);
		this.aLong200 = arg0;
	}
}
