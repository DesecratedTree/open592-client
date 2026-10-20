package com.jagex.game.runetek5.toolkit.opengl.buffer;

import jaggl.OpenGL;
import jaclib.memory.NativeBuffer;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.toolkit.opengl.buffer.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.deob.Class70;
@OriginalClass("client!rg")
public abstract class AbstractArbBufferGL {

	@OriginalMember(owner = "client!rg", name = "f", descriptor = "Z")
	private final boolean streamDraw;

	@OriginalMember(owner = "client!rg", name = "c", descriptor = "I")
	private final int bufferTarget;

	@OriginalMember(owner = "client!rg", name = "i", descriptor = "I")
	private int bufferSize;

	@OriginalMember(owner = "client!rg", name = "d", descriptor = "Lclient!fd;")
	protected final GlToolkit toolkit;

	@OriginalMember(owner = "client!rg", name = "g", descriptor = "I")
	protected final int id;

	static {
		new Class83("To talk, start each line of chat with the / symbol.", "Leite eine Zeile mit / ein, um hier zu chatten.", "Pour parler, insérez le symbole / au début de chaque ligne.", "Para falar, comece cada linha de conversa com o símbolo /.");
	}

	@OriginalMember(owner = "client!rg", name = "<init>", descriptor = "(Lclient!fd;I[BIZ)V")
	public AbstractArbBufferGL(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int bufferTarget, @OriginalArg(2) byte[] arg2, @OriginalArg(3) int bufferSize, @OriginalArg(4) boolean streamDraw) {
		this.streamDraw = streamDraw;
		this.bufferTarget = bufferTarget;
		this.bufferSize = bufferSize;
		this.toolkit = glToolkit;
		OpenGL.glGenBuffersARB(1, RemoteFileWorker.anIntArray299, 0);
		this.id = RemoteFileWorker.anIntArray299[0];
		this.setAsActiveBuffer();
		OpenGL.glBufferDataARBub(bufferTarget, this.bufferSize, arg2, 0, this.streamDraw ? 35040 : 35044);
		this.toolkit.vboBuffersSize += this.bufferSize;
	}

	@OriginalMember(owner = "client!rg", name = "<init>", descriptor = "(Lclient!fd;ILclient!jaggl/memory/NativeBuffer;IZ)V")
	public AbstractArbBufferGL(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) NativeBuffer arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4) {
		this.bufferTarget = arg1;
		this.bufferSize = arg3;
		this.toolkit = arg0;
		this.streamDraw = arg4;
		OpenGL.glGenBuffersARB(1, RemoteFileWorker.anIntArray299, 0);
		this.id = RemoteFileWorker.anIntArray299[0];
		this.setAsActiveBuffer();
		OpenGL.glBufferDataARBa(arg1, this.bufferSize, arg2.c(), this.streamDraw ? 35040 : 35044);
		this.toolkit.vboBuffersSize += this.bufferSize;
	}

	@OriginalMember(owner = "client!rg", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() throws Throwable {
		this.toolkit.method1939(this.id, this.bufferSize);
		super.finalize();
	}

	@OriginalMember(owner = "client!rg", name = "a", descriptor = "(II[B)V")
	protected final void method5177(@OriginalArg(1) int arg0, @OriginalArg(2) byte[] arg1) {
		this.setAsActiveBuffer();
		if (this.bufferSize < arg0) {
			OpenGL.glBufferDataARBub(this.bufferTarget, arg0, arg1, 0, this.streamDraw ? 35040 : 35044);
			this.toolkit.vboBuffersSize += arg0 - this.bufferSize;
			this.bufferSize = arg0;
		} else {
			OpenGL.glBufferSubDataARBCompat(this.bufferTarget, 0, arg0, arg1, 0);
		}
	}

	@OriginalMember(owner = "client!rg", name = "d", descriptor = "(B)V")
	protected abstract void setAsActiveBuffer();

@OriginalMember(owner = "client!rg", name = "j", descriptor = "[[B")
	public static byte[][] aByteArrayArray29;
@OriginalMember(owner = "client!rg", name = "o", descriptor = "[I")
	public static int[] anIntArray504;
@OriginalMember(owner = "client!rg", name = "h", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_87 = new Class215(56, -1);
@OriginalMember(owner = "client!rg", name = "l", descriptor = "Lclient!ib;")
	public static final CubeMapSpecularEffect aClass102_38 = new CubeMapSpecularEffect(16);
@OriginalMember(owner = "client!rg", name = "m", descriptor = "I")
	public static int anInt6617 = 999999;
@OriginalMember(owner = "client!rg", name = "n", descriptor = "Z")
	public static boolean aBoolean599 = false;
@OriginalMember(owner = "client!rg", name = "a", descriptor = "(III)Lclient!nt;")
	public static Class164 method5175(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		if (Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2] == null) {
			@Pc(28) boolean local28 = Class70.aClass164ArrayArrayArray5[0][arg1][arg2] != null && Class70.aClass164ArrayArrayArray5[0][arg1][arg2].aClass164_1 != null;
			if (local28 && arg0 >= RemoteFileWorker.anInt4487 - 1) {
				return null;
			}
			Class111.method2809(arg0, arg1, arg2);
		}
		return Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
	}
@OriginalMember(owner = "client!rg", name = "c", descriptor = "(B)V")
	public static void method5178() {
		@Pc(5) LRUCache local5 = Class4_Sub1_Sub6_Sub2.aClass68_24;
		synchronized (Class4_Sub1_Sub6_Sub2.aClass68_24) {
			Class4_Sub1_Sub6_Sub2.aClass68_24.method1791();
		}
	}
@OriginalMember(owner = "client!rg", name = "a", descriptor = "(IIIII)I")
	public static int method5180(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(7) int local7 = arg3 & 0xF;
		@Pc(16) int local16 = local7 < 8 ? arg1 : arg0;
		@Pc(35) int local35 = local7 >= 4 ? (local7 == 12 || local7 == 14 ? arg1 : arg2) : arg0;
		return ((local7 & 0x1) == 0 ? local16 : -local16) + ((local7 & 0x2) == 0 ? local35 : -local35);
	}
}
