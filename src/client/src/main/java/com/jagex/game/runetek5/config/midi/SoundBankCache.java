package com.jagex.game.runetek5.config.midi;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.VorbisSound;
import com.jagex.game.runetek5.cache.node.impl.VariableRateSoundPacket;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static211;
import com.jagex.game.runetek5.deob.Static271;
import com.jagex.game.runetek5.config.midi.*;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.config.midi.SoundEffect;
@OriginalClass("client!oj")
public final class SoundBankCache {

	@OriginalMember(owner = "client!oj", name = "j", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_25 = new CubeMapSpecularEffect(256);

	@OriginalMember(owner = "client!oj", name = "o", descriptor = "Lclient!ib;")
	private final CubeMapSpecularEffect aClass102_26 = new CubeMapSpecularEffect(256);

	@OriginalMember(owner = "client!oj", name = "b", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_56;

	@OriginalMember(owner = "client!oj", name = "i", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_57;

	@OriginalMember(owner = "client!oj", name = "<init>", descriptor = "(Lclient!fs;Lclient!fs;)V")
	public SoundBankCache(@OriginalArg(0) Js5ResourceProvider synthArchive, @OriginalArg(1) Js5ResourceProvider vorbisArchive) {
		this.aClass76_56 = vorbisArchive;
		this.aClass76_57 = synthArchive;
	}

	@OriginalMember(owner = "client!oj", name = "a", descriptor = "(III[I)Lclient!js;")
	private VariableRateSoundPacket method3970(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int[] arg2) {
		@Pc(15) int local15 = (arg0 << 4 & 0xFFF4 | arg0 >>> 12) ^ arg1;
		@Pc(21) int local21 = local15 | arg0 << 16;
		@Pc(24) long local24 = (long) local21;
		@Pc(31) VariableRateSoundPacket local31 = (VariableRateSoundPacket) this.aClass102_26.method2700(local24);
		if (local31 != null) {
			return local31;
		} else if (arg2 == null || arg2[0] > 0) {
			@Pc(54) SoundEffect local54 = SoundEffect.getSoundEffect(this.aClass76_57, arg0, arg1);
			if (local54 == null) {
				return null;
			}
			local31 = local54.getMixedAudio();
			this.aClass102_26.method2703(local24, local31);
			if (arg2 != null) {
				arg2[0] -= local31.data.length;
			}
			return local31;
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!oj", name = "a", descriptor = "(I[IBI)Lclient!js;")
	private VariableRateSoundPacket method3972(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(3) int arg2) {
		@Pc(15) int local15 = arg2 ^ (arg0 << 4 & 0xFFF2 | arg0 >>> 12);
		@Pc(26) int local26 = local15 | arg0 << 16;
		@Pc(31) long local31 = (long) local26 ^ 0x100000000L;
		@Pc(38) VariableRateSoundPacket local38 = (VariableRateSoundPacket) this.aClass102_26.method2700(local31);
		if (local38 != null) {
			return local38;
		} else if (arg1 == null || arg1[0] > 0) {
			@Pc(57) VorbisSound local57 = (VorbisSound) this.aClass102_25.method2700(local31);
			if (local57 == null) {
				local57 = VorbisSound.create(this.aClass76_56, arg0, arg2);
				if (local57 == null) {
					return null;
				}
				this.aClass102_25.method2703(local31, local57);
			}
			local38 = local57.toPcmSound(arg1);
			if (local38 == null) {
				return null;
			} else {
				local57.method5684();
				this.aClass102_26.method2703(local31, local38);
				return local38;
			}
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!oj", name = "a", descriptor = "([III)Lclient!js;")
	public VariableRateSoundPacket method3973(@OriginalArg(0) int[] arg0, @OriginalArg(1) int arg1) {
		if (this.aClass76_56.method2100() == 1) {
			return this.method3972(0, arg0, arg1);
		} else if (this.aClass76_56.method2108(arg1) == 1) {
			return this.method3972(arg1, arg0, 0);
		} else {
			throw new RuntimeException();
		}
	}

	@OriginalMember(owner = "client!oj", name = "b", descriptor = "([III)Lclient!js;")
	public VariableRateSoundPacket method3976(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1) {
		if (this.aClass76_57.method2100() == 1) {
			return this.method3970(0, arg1, arg0);
		} else if (this.aClass76_57.method2108(arg1) == 1) {
			return this.method3970(arg1, 0, arg0);
		} else {
			throw new RuntimeException();
		}
	}

@OriginalMember(owner = "client!oj", name = "f", descriptor = "J")
	public static long aLong174;
@OriginalMember(owner = "client!oj", name = "k", descriptor = "I")
	public static int hslToRgbUsers;
@OriginalMember(owner = "client!oj", name = "l", descriptor = "I")
	public static int anInt5199;
@OriginalMember(owner = "client!oj", name = "a", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_64 = new Class215(8, 8);
@OriginalMember(owner = "client!oj", name = "m", descriptor = "Lclient!sp;")
	public static final ModeWhere MODE_WHERE_WTQA = new ModeWhere("LOCAL", 4);
@OriginalMember(owner = "client!oj", name = "a", descriptor = "(III)V")
	public static void method3974(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(9) int local9;
		if (arg1 != MidiPcmStream.anInt1616) {
			AbstractArbBufferGL.anIntArray504 = new int[arg1];
			for (local9 = 0; local9 < arg1; local9++) {
				AbstractArbBufferGL.anIntArray504[local9] = (local9 << 12) / arg1;
			}
			Class78_Sub1.anInt3883 = arg1 - 1;
			MidiPcmStream.anInt1616 = arg1;
			TileLighting.anInt4776 = arg1 * 32;
		}
		if (EnumTypeList.anInt4939 == arg0) {
			return;
		}
		if (MidiPcmStream.anInt1616 == arg0) {
			ParticleEffector.anIntArray209 = AbstractArbBufferGL.anIntArray504;
		} else {
			ParticleEffector.anIntArray209 = new int[arg0];
			for (local9 = 0; local9 < arg0; local9++) {
				ParticleEffector.anIntArray209[local9] = (local9 << 12) / arg0;
			}
		}
		EnumTypeList.anInt4939 = arg0;
		Static211.anInt3929 = arg0 - 1;
	}
@OriginalMember(owner = "client!oj", name = "a", descriptor = "(IILclient!uu;B)V")
	public static void method3975(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component arg2) {
		if (arg2 == null) {
			return;
		}
		if (arg2.anObjectArray21 != null) {
			@Pc(13) HookRequest local13 = new HookRequest();
			local13.source = arg2;
			local13.params = arg2.anObjectArray21;
			Static271.executeHookInner(local13);
		}
		Class243.anInt6726 = arg2.anInt6809;
		OpenGl2DTexture.anInt5460 = arg0;
		AbstractArbBufferGL.aBoolean599 = true;
		HashTable.anInt3147 = arg2.anInt6773;
		ChatFilter.anInt3179 = arg2.anInt6779;
		Country.anInt7378 = arg1;
		StackFrame.anInt350 = arg2.anInt6865;
		Class164.anInt5120 = arg2.anInt6790;
		Class4_Sub12_Sub1.method1142(arg2);
	}
}
