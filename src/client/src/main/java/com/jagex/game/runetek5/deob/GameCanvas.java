package com.jagex.game.runetek5.deob;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.sound.jagtheora.PcmStreamMixer;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.scene.ground.Ground;
@OriginalClass("client!sg")
public final class GameCanvas extends Canvas {

	@OriginalMember(owner = "client!sg", name = "a", descriptor = "Ljava/awt/Component;")
	private final Component aComponent2;

	@OriginalMember(owner = "client!sg", name = "<init>", descriptor = "(Ljava/awt/Component;)V")
	public GameCanvas(@OriginalArg(0) Component arg0) {
		this.aComponent2 = arg0;
	}

	@OriginalMember(owner = "client!sg", name = "paint", descriptor = "(Ljava/awt/Graphics;)V")
	@Override
	public void paint(@OriginalArg(0) Graphics arg0) {
		this.aComponent2.paint(arg0);
	}

	@OriginalMember(owner = "client!sg", name = "update", descriptor = "(Ljava/awt/Graphics;)V")
	@Override
	public void update(@OriginalArg(0) Graphics arg0) {
		this.aComponent2.update(arg0);
	}

@OriginalMember(owner = "client!sg", name = "b", descriptor = "Lclient!jl;")
	public static PcmStreamMixer aClass4_Sub15_Sub2_2;
@OriginalMember(owner = "client!sg", name = "g", descriptor = "F")
	public static float aFloat189;
@OriginalMember(owner = "client!sg", name = "h", descriptor = "F")
	public static float aFloat190;
@OriginalMember(owner = "client!sg", name = "c", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_284 = new Class22(106, 10);
@OriginalMember(owner = "client!sg", name = "a", descriptor = "(ILclient!iv;)V")
	public static void method4864(@OriginalArg(1) Packet arg0) {
		if (arg0.buffer.length - arg0.currentPosition < 1) {
			return;
		}
		@Pc(22) int local22 = arg0.method2490();
		if (local22 < 0 || local22 > 1 || arg0.buffer.length - arg0.currentPosition < 2) {
			return;
		}
		@Pc(47) int local47 = arg0.method2536();
		if (arg0.buffer.length - arg0.currentPosition != local47 * 6) {
			return;
		}
		while (true) {
			@Pc(67) int local67;
			@Pc(71) int local71;
			do {
				do {
					do {
						if (arg0.buffer.length <= arg0.currentPosition) {
							return;
						}
						local67 = arg0.method2536();
						local71 = arg0.method2529();
					} while (local67 >= ParticleEffector.WALL_ROTATION_FLAGS.length);
				} while (!Class16_Sub1_Sub1.aBooleanArray3[local67]);
			} while (Ground.aClass93_4.method2430(local67).aChar5 == '1' && (local71 < -1 || local71 > 1));
			ParticleEffector.WALL_ROTATION_FLAGS[local67] = local71;
		}
	}
}
