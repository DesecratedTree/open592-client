package com.jagex.game.runetek5.js5.request;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.Js5MasterIndex;

import java.io.IOException;
import java.net.Socket;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.Static133;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.js5.request.*;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.deob.Class105;
@OriginalClass("client!oi")
public abstract class FileWorker {

	@OriginalMember(owner = "client!oi", name = "<init>", descriptor = "()V")
	protected FileWorker() {
	}

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(II)V")
	public abstract void requestGroup(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(B)Lclient!rt;")
	public abstract Js5MasterIndex index();

	@OriginalMember(owner = "client!oi", name = "a", descriptor = "(IB)I")
	public abstract int completePercentage(@OriginalArg(0) int groupId);

	@OriginalMember(owner = "client!oi", name = "b", descriptor = "(II)[B")
	public abstract byte[] fetchgroup(@OriginalArg(1) int arg0);

@OriginalMember(owner = "client!oi", name = "c", descriptor = "[Lclient!ta;")
	public static Class65[] aClass65Array3;
@OriginalMember(owner = "client!oi", name = "a", descriptor = "(I)V")
	public static void method3510() {
		if (NPCTypeList.anInt6682 == 0) {
			return;
		}
		try {
			if (++GameShell.anInt970 > 1500) {
				if (QuickchatMessageTypeList.aClass11_5 != null) {
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
				}
				if (ParticleList.anInt4518 >= 1) {
					StringNode.anInt5302 = -5;
					NPCTypeList.anInt6682 = 0;
					return;
				}
				if (Class189.anInt5435 == Static133.anInt2755) {
					Class189.anInt5435 = ReflectiveRipplingWaterEffect.anInt154;
				} else {
					Class189.anInt5435 = Static133.anInt2755;
				}
				ParticleList.anInt4518++;
				GameShell.anInt970 = 0;
				NPCTypeList.anInt6682 = 1;
			}
			if (NPCTypeList.anInt6682 == 1) {
				Class26.aClass199_3 = Class126.aClass66_4.method1728(BufferedSocket.aString4, Class189.anInt5435);
				NPCTypeList.anInt6682 = 2;
			}
			@Pc(118) int local118;
			if (NPCTypeList.anInt6682 == 2) {
				if (Class26.aClass199_3.anInt5760 == 2) {
					throw new IOException();
				}
				if (Class26.aClass199_3.anInt5760 != 1) {
					return;
				}
				QuickchatMessageTypeList.aClass11_5 = new BufferedSocket((Socket) Class26.aClass199_3.anObject29, Class126.aClass66_4);
				Class26.aClass199_3 = null;
				QuickchatMessageTypeList.aClass11_5.method142(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition, ParticleNode.aClass4_Sub12_Sub1_5.buffer);
				Class196.method4427();
				local118 = QuickchatMessageTypeList.aClass11_5.method139();
				Class196.method4427();
				if (local118 != 101) {
					StringNode.anInt5302 = local118;
					NPCTypeList.anInt6682 = 0;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					return;
				}
				NPCTypeList.anInt6682 = 3;
			}
			if (NPCTypeList.anInt6682 == 3 && QuickchatMessageTypeList.aClass11_5.method133() >= 2) {
				local118 = QuickchatMessageTypeList.aClass11_5.method139() << 8 | QuickchatMessageTypeList.aClass11_5.method139();
				Class105.method5279(local118);
				if (MapSpriteType.anInt1028 == -1) {
					NPCTypeList.anInt6682 = 0;
					StringNode.anInt5302 = 6;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
				} else {
					NPCTypeList.anInt6682 = 0;
					QuickchatMessageTypeList.aClass11_5.method138();
					QuickchatMessageTypeList.aClass11_5 = null;
					Class145_Sub1.method5617();
				}
			}
		} catch (@Pc(188) IOException local188) {
			if (QuickchatMessageTypeList.aClass11_5 != null) {
				QuickchatMessageTypeList.aClass11_5.method138();
				QuickchatMessageTypeList.aClass11_5 = null;
			}
			if (ParticleList.anInt4518 >= 1) {
				NPCTypeList.anInt6682 = 0;
				StringNode.anInt5302 = -4;
			} else {
				NPCTypeList.anInt6682 = 1;
				GameShell.anInt970 = 0;
				ParticleList.anInt4518++;
				if (Static133.anInt2755 == Class189.anInt5435) {
					Class189.anInt5435 = ReflectiveRipplingWaterEffect.anInt154;
				} else {
					Class189.anInt5435 = Static133.anInt2755;
				}
			}
		}
	}
@OriginalMember(owner = "client!oi", name = "a", descriptor = "(IIBI)Lclient!r;")
	public static PathingEntity method3513(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(11) Class164 local11 = Class70.aClass164ArrayArrayArray5[arg2][arg1][arg0];
		if (local11 == null) {
			return null;
		}
		@Pc(17) PathingEntity local17 = null;
		@Pc(24) int local24 = -1;
		for (@Pc(27) Class7 local27 = local11.aClass7_3; local27 != null; local27 = local27.aClass7_1) {
			@Pc(31) Class16_Sub1 local31 = local27.aClass16_Sub1_1;
			if (local31 instanceof PathingEntity) {
				@Pc(37) PathingEntity local37 = (PathingEntity) local31;
				@Pc(47) int local47 = (local37.getSize() - 1) * 64 + 60;
				@Pc(54) int local54 = local37.anInt6893 - local47 >> 7;
				@Pc(62) int local62 = local37.anInt6892 - local47 >> 7;
				@Pc(69) int local69 = local47 + local37.anInt6893 >> 7;
				@Pc(76) int local76 = local37.anInt6892 + local47 >> 7;
				if (local54 <= arg1 && local62 <= arg0 && arg1 <= local69 && arg0 <= local76) {
					@Pc(110) int local110 = (local76 + 1 - arg0) * (-arg1 + 1 + local69);
					if (local110 > local24) {
						local17 = local37;
						local24 = local110;
					}
				}
			}
		}
		return local17;
	}
}
