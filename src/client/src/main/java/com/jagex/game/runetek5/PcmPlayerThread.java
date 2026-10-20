package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.sound.player.PcmPlayer;

import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Static433;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.deob.Class85;
@OriginalClass("client!tj")
public final class PcmPlayerThread implements Runnable {

	@OriginalMember(owner = "client!tj", name = "b", descriptor = "Lclient!et;")
	public SignLink aClass66_7;

	@OriginalMember(owner = "client!tj", name = "e", descriptor = "[Lclient!ln;")
	public final PcmPlayer[] players = new PcmPlayer[2];

	@OriginalMember(owner = "client!tj", name = "c", descriptor = "Z")
	public volatile boolean stopping = false;

	@OriginalMember(owner = "client!tj", name = "f", descriptor = "Z")
	public volatile boolean running = false;

	@OriginalMember(owner = "client!tj", name = "run", descriptor = "()V")
	@Override
	public void run() {
		this.running = true;
		try {
			while (!this.stopping) {
				for (@Pc(12) int local12 = 0; local12 < 2; local12++) {
					@Pc(19) PcmPlayer local19 = this.players[local12];
					if (local19 != null) {
						local19.method2069();
					}
				}
				Class259.method5503(10L);
				Static441.method5566(this.aClass66_7, null);
			}
		} catch (@Pc(43) Exception local43) {
			Class64_Sub1.method1654(local43, null);
		} finally {
			@Pc(53) Object local53 = null;
			this.running = false;
		}
	}

@OriginalMember(owner = "client!tj", name = "d", descriptor = "Lclient!iv;")
	public static Packet aClass4_Sub12_7 = new Packet(1);
@OriginalMember(owner = "client!tj", name = "g", descriptor = "[I")
	public static final int[] anIntArray496 = new int[] { 36064, 36065, 36066, 36067, 36068, 36069, 36070, 36071, 36096 };
@OriginalMember(owner = "client!tj", name = "a", descriptor = "(Ljava/lang/String;B)V")
	public static void method5070(@OriginalArg(0) String arg0) {
		if (arg0 == null) {
			return;
		}
		if (arg0.startsWith("*")) {
			arg0 = arg0.substring(1);
		}
		@Pc(24) String local24 = Class79.method4868(arg0);
		if (local24 == null) {
			return;
		}
		for (@Pc(29) int local29 = 0; local29 < Class4_Sub10.anInt2346; local29++) {
			@Pc(35) String local35 = Mesh.aStringArray14[local29];
			if (local35.startsWith("*")) {
				local35 = local35.substring(1);
			}
			local35 = Class79.method4868(local35);
			if (local35 != null && local35.equals(local24)) {
				Class4_Sub10.anInt2346--;
				for (@Pc(59) int local59 = local29; local59 < Class4_Sub10.anInt2346; local59++) {
					Mesh.aStringArray14[local59] = Mesh.aStringArray14[local59 + 1];
					Static57.aStringArray6[local59] = Static57.aStringArray6[local59 + 1];
					Class9.anIntArray536[local59] = Class9.anIntArray536[local59 + 1];
					Static433.aStringArray38[local59] = Static433.aStringArray38[local59 + 1];
					Class4_Sub6_Sub26.anIntArray339[local59] = Class4_Sub6_Sub26.anIntArray339[local59 + 1];
					Class4_Sub43.aBooleanArray23[local59] = Class4_Sub43.aBooleanArray23[local59 + 1];
				}
				Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
				PingWorker.method5476(Class85.aClass215_36);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(arg0));
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
				return;
			}
		}
	}
}
