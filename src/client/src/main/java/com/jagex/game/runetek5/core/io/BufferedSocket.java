package com.jagex.game.runetek5.core.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sign.SignedResource;

import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.core.io.*;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
@OriginalClass("client!al")
public final class BufferedSocket implements Runnable {

	@OriginalMember(owner = "client!al", name = "b", descriptor = "[B")
	private byte[] writeBuffer;

	@OriginalMember(owner = "client!al", name = "n", descriptor = "Lclient!qt;")
	private SignedResource threadReference;

	@OriginalMember(owner = "client!al", name = "s", descriptor = "Z")
	private boolean aBoolean19 = false;

	@OriginalMember(owner = "client!al", name = "x", descriptor = "Z")
	private boolean aBoolean20 = false;

	@OriginalMember(owner = "client!al", name = "c", descriptor = "I")
	private int writePos = 0;

	@OriginalMember(owner = "client!al", name = "t", descriptor = "I")
	private int anInt183 = 0;

	@OriginalMember(owner = "client!al", name = "w", descriptor = "Ljava/net/Socket;")
	private final Socket socket;

	@OriginalMember(owner = "client!al", name = "f", descriptor = "Lclient!et;")
	private final SignLink signlink;

	@OriginalMember(owner = "client!al", name = "u", descriptor = "Ljava/io/InputStream;")
	private InputStream inputStream;

	@OriginalMember(owner = "client!al", name = "a", descriptor = "Ljava/io/OutputStream;")
	private OutputStream outputStream;

	@OriginalMember(owner = "client!al", name = "<init>", descriptor = "(Ljava/net/Socket;Lclient!et;)V")
	public BufferedSocket(@OriginalArg(0) Socket socket2, @OriginalArg(1) SignLink signLink) throws IOException {
		this.socket = socket2;
		this.signlink = signLink;
		this.socket.setSoTimeout(30000);
		this.socket.setTcpNoDelay(true);
		this.inputStream = this.socket.getInputStream();
		this.outputStream = this.socket.getOutputStream();
	}

	@OriginalMember(owner = "client!al", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		this.method138();
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(IBI[B)V")
	public void method131(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) byte[] arg2) throws IOException {
		if (this.aBoolean19) {
			return;
		}
		while (arg1 > 0) {
			@Pc(23) int local23 = this.inputStream.read(arg2, arg0, arg1);
			if (local23 <= 0) {
				throw new EOFException();
			}
			arg1 -= local23;
			arg0 += local23;
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(I)V")
	public void method132() {
		if (!this.aBoolean19) {
			this.inputStream = new DummyInputStream();
			this.outputStream = new OutputStream_Sub1();
		}
	}

	@OriginalMember(owner = "client!al", name = "b", descriptor = "(I)I")
	public int method133() throws IOException {
		return this.aBoolean19 ? 0 : this.inputStream.available();
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(Z)V")
	public void method138() {
		if (this.aBoolean19) {
			return;
		}
		synchronized (this) {
			this.aBoolean19 = true;
			this.notifyAll();
		}
		if (this.threadReference != null) {
			while (this.threadReference.anInt5760 == 0) {
				Class259.method5503(1L);
			}
			if (this.threadReference.anInt5760 == 1) {
				try {
					((Thread) this.threadReference.anObject29).join();
				} catch (@Pc(56) InterruptedException local56) {
				}
			}
		}
		this.threadReference = null;
	}

	@OriginalMember(owner = "client!al", name = "d", descriptor = "(I)I")
	public int method139() throws IOException {
		return this.aBoolean19 ? 0 : this.inputStream.read();
	}

	@OriginalMember(owner = "client!al", name = "run", descriptor = "()V")
	@Override
	public void run() {
		try {
			while (true) {
				label80: {
					@Pc(40) int local40;
					@Pc(28) int local28;
					synchronized (this) {
						if (this.anInt183 == this.writePos) {
							if (this.aBoolean19) {
								break label80;
							}
							try {
								this.wait();
							} catch (@Pc(24) InterruptedException local24) {
							}
						}
						local28 = this.writePos;
						if (this.writePos <= this.anInt183) {
							local40 = this.anInt183 - this.writePos;
						} else {
							local40 = 5000 - this.writePos;
						}
					}
					if (local40 <= 0) {
						continue;
					}
					try {
						this.outputStream.write(this.writeBuffer, local28, local40);
					} catch (@Pc(65) IOException local65) {
						this.aBoolean20 = true;
					}
					this.writePos = (local40 + this.writePos) % 5000;
					try {
						if (this.anInt183 == this.writePos) {
							this.outputStream.flush();
						}
					} catch (@Pc(86) IOException local86) {
						this.aBoolean20 = true;
					}
					continue;
				}
				try {
					if (this.inputStream != null) {
						this.inputStream.close();
					}
					if (this.outputStream != null) {
						this.outputStream.close();
					}
					if (this.socket != null) {
						this.socket.close();
					}
				} catch (@Pc(110) IOException local110) {
				}
				this.writeBuffer = null;
				break;
			}
		} catch (@Pc(115) Exception local115) {
			Class64_Sub1.method1654(local115, null);
		}
	}

	@OriginalMember(owner = "client!al", name = "e", descriptor = "(I)V")
	public void method141() throws IOException {
		if (!this.aBoolean19 && this.aBoolean20) {
			this.aBoolean20 = false;
			throw new IOException();
		}
	}

	@OriginalMember(owner = "client!al", name = "a", descriptor = "(II[BI)V")
	public void method142(@OriginalArg(0) int arg0, @OriginalArg(2) byte[] arg1) throws IOException {
		if (this.aBoolean19) {
			return;
		}
		if (this.aBoolean20) {
			this.aBoolean20 = false;
			throw new IOException();
		}
		if (this.writeBuffer == null) {
			this.writeBuffer = new byte[5000];
		}
		synchronized (this) {
			for (@Pc(38) int local38 = 0; local38 < arg0; local38++) {
				this.writeBuffer[this.anInt183] = arg1[local38];
				this.anInt183 = (this.anInt183 + 1) % 5000;
				if (this.anInt183 == (this.writePos + 4900) % 5000) {
					throw new IOException();
				}
			}
			if (this.threadReference == null) {
				this.threadReference = this.signlink.method1741(3, this);
			}
			this.notifyAll();
		}
	}

@OriginalMember(owner = "client!al", name = "d", descriptor = "Ljava/lang/String;")
	public static String aString4;
@OriginalMember(owner = "client!al", name = "m", descriptor = "I")
	public static int anInt178;
@OriginalMember(owner = "client!al", name = "a", descriptor = "(IIII)I")
	public static int method135(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (Class16_Sub1_Sub6.aClass65Array4 == null) {
			return 0;
		}
		@Pc(11) int local11 = arg0 >> 7;
		@Pc(15) int local15 = arg2 >> 7;
		if (local11 < 0 || local15 < 0 || GLBillowTextureData.anInt5666 - 1 < local11 || Static283.anInt5187 - 1 < local15) {
			return 0;
		}
		@Pc(43) int local43 = arg1;
		if (arg1 < 3 && (SeqTypeList.aByteArrayArrayArray2[1][local11][local15] & 0x2) != 0) {
			local43 = arg1 + 1;
		}
		return Class16_Sub1_Sub6.aClass65Array4[local43].a(arg0, arg2);
	}
@OriginalMember(owner = "client!al", name = "a", descriptor = "(IB)V")
	public static void method136(@OriginalArg(0) int arg0) {
		Class149.anInt4535 = 1000 / arg0;
	}
@OriginalMember(owner = "client!al", name = "a", descriptor = "(III)Z")
	public static boolean method137(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		if (Class4_Sub9.method760(arg0, arg1)) {
			return (arg1 & 0xB000) != 0 | OpenGlArbElementArrayBuffer.method1643(arg0, arg1) | WorldMapElement.method934(arg1, arg0) ? true : (Class9_Sub3.method1353(arg0, arg1) | Js5ResourceRequest.method2904(arg0, arg1)) & (arg0 & 0x37) == 0;
		} else {
			return false;
		}
	}
@OriginalMember(owner = "client!al", name = "a", descriptor = "(ZLjava/lang/String;)V")
	public static void method140(@OriginalArg(1) String arg0) {
		System.out.println("Error: " + Class174.method4044("\n", "%0a", arg0));
	}
}
