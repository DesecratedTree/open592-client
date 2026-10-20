package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class223;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!bv")
public final class ObjStackEntity extends Class16_Sub3 {

	@OriginalMember(owner = "client!bv", name = "z", descriptor = "I")
	public int anInt749;

	@OriginalMember(owner = "client!bv", name = "D", descriptor = "I")
	public int anInt752;

	@OriginalMember(owner = "client!bv", name = "H", descriptor = "I")
	public int anInt755;

	@OriginalMember(owner = "client!bv", name = "I", descriptor = "I")
	public int anInt756;

	@OriginalMember(owner = "client!bv", name = "w", descriptor = "I")
	public int anInt746 = -1;

	@OriginalMember(owner = "client!bv", name = "K", descriptor = "I")
	public int anInt758 = -1;

	@OriginalMember(owner = "client!bv", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
	}

	@OriginalMember(owner = "client!bv", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(6) Ground local6 = arg1.method4289();
		local6.scaleWidth(super.anInt744, super.anInt739, super.anInt737);
		@Pc(31) Model local31 = Terrain.aClass206_3.method4703(this.anInt755).method4773(0, this.anInt756, null, 131072, -1, null, 0, arg1);
		if (local31 != null && local31.picked(arg2, arg0, local6, true)) {
			return true;
		}
		if (this.anInt758 != -1) {
			local31 = Terrain.aClass206_3.method4703(this.anInt758).method4773(0, this.anInt752, null, 131072, -1, null, 0, arg1);
			if (local31 != null && local31.picked(arg2, arg0, local6, true)) {
				return true;
			}
		}
		if (this.anInt746 != -1) {
			local31 = Terrain.aClass206_3.method4703(this.anInt746).method4773(0, this.anInt749, null, 131072, -1, null, 0, arg1);
			if (local31 != null && local31.picked(arg2, arg0, local6, true)) {
				return true;
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!bv", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(6) Ground local6 = arg0.method4289();
		local6.scaleWidth(super.anInt744, super.anInt739, super.anInt737);
		@Pc(18) Class3_Sub3 local18 = Class28.method672(3);
		@Pc(46) Model local46;
		if (this.anInt746 != -1) {
			local46 = Terrain.aClass206_3.method4703(this.anInt746).method4773(0, this.anInt749, null, 2048, -1, null, 0, arg0);
			if (local46 != null) {
				local46.render(local6, local18.aClass3_Sub5Array1[2], 0);
			}
		}
		if (this.anInt758 != -1) {
			local46 = Terrain.aClass206_3.method4703(this.anInt758).method4773(0, this.anInt752, null, 2048, -1, null, 0, arg0);
			if (local46 != null) {
				local46.render(local6, local18.aClass3_Sub5Array1[1], 0);
			}
		}
		local46 = Terrain.aClass206_3.method4703(this.anInt755).method4773(0, this.anInt756, null, 2048, -1, null, 0, arg0);
		if (local46 != null) {
			local46.render(local6, local18.aClass3_Sub5Array1[0], 0);
		}
		return local18;
	}

@OriginalMember(owner = "client!bv", name = "B", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_3 = new QuickChatValueType(11, 0, 1, 2);
@OriginalMember(owner = "client!bv", name = "L", descriptor = "Z")
	public static boolean aBoolean74 = true;
@OriginalMember(owner = "client!bv", name = "M", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_45 = new Class22(69, -1);
@OriginalMember(owner = "client!bv", name = "N", descriptor = "I")
	public static int anInt759 = 0;
@OriginalMember(owner = "client!bv", name = "O", descriptor = "[I")
	public static final int[] anIntArray46 = new int[5];
@OriginalMember(owner = "client!bv", name = "P", descriptor = "Z")
	public static boolean aBoolean75 = false;
@OriginalMember(owner = "client!bv", name = "Q", descriptor = "Z")
	public static boolean aBoolean76 = true;
@OriginalMember(owner = "client!bv", name = "a", descriptor = "(Lclient!fk;I)V")
	public static void method727(@OriginalArg(0) Class3_Sub3 arg0) {
		arg0.aClass16_1 = null;
		@Pc(12) int local12 = arg0.aClass3_Sub5Array1.length;
		for (@Pc(18) int local18 = 0; local18 < local12; local18++) {
			arg0.aClass3_Sub5Array1[local18].positionCalculated = false;
		}
		@Pc(36) EntityNodeList[] local36 = MeshBillboard.aClass84Array1;
		synchronized (MeshBillboard.aClass84Array1) {
			if (local12 < MeshBillboard.aClass84Array1.length && ProjectileAnimationNode.anIntArray154[local12] < 200) {
				MeshBillboard.aClass84Array1[local12].offer(arg0);
				@Pc(65) int local65 = ProjectileAnimationNode.anIntArray154[local12]++;
			}
		}
	}
@OriginalMember(owner = "client!bv", name = "a", descriptor = "(ILclient!cw;IILclient!lh;)V")
	public static void method729(@OriginalArg(0) int arg0, @OriginalArg(1) Class4_Sub12_Sub1 arg1, @OriginalArg(2) int arg2, @OriginalArg(4) PlayerEntity arg3) {
		@Pc(7) byte local7 = -1;
		if ((arg0 & 0x40) != 0) {
			arg3.anInt4323 = arg1.method2536();
			if (arg3.pathPointer == 0) {
				arg3.method4743(arg3.anInt4323);
				arg3.anInt4323 = -1;
			}
		}
		if ((arg0 & 0x400) != 0) {
			arg3.aString60 = arg1.method2537();
			if (arg3.aString60.charAt(0) == '~') {
				arg3.aString60 = arg3.aString60.substring(1);
				Class83.method2268(0, 2, arg3.aString60, arg3.method3428(), arg3.method3432());
			} else if (Static1.aClass16_Sub1_Sub5_Sub1_1 == arg3) {
				Class83.method2268(0, 2, arg3.aString60, arg3.method3428(), arg3.method3432());
			}
			arg3.anInt6064 = 0;
			arg3.anInt6077 = 150;
			arg3.anInt6084 = 0;
		}
		if ((arg0 & 0x8000) != 0) {
			arg3.anInt6043 = arg1.method2534();
			arg3.anInt6052 = arg1.method2542();
			arg3.anInt6044 = arg1.method2534();
			arg3.anInt6074 = arg1.method2542();
			arg3.anInt6018 = arg1.method2536() + Shadow.anInt5323;
			arg3.anInt6023 = arg1.method2548() + Shadow.anInt5323;
			arg3.lb = arg1.method2520();
			arg3.anInt6087 = 0;
			if (arg3.hasPendingPositionUpdate) {
				arg3.pathPointer = 0;
				arg3.anInt6043 += arg3.anInt4335;
				arg3.anInt6052 += arg3.anInt4317;
				arg3.anInt6074 += arg3.anInt4317;
				arg3.anInt6044 += arg3.anInt4335;
			} else {
				arg3.anInt6044 += arg3.movementBaseX[0];
				arg3.anInt6043 += arg3.movementBaseX[0];
				arg3.anInt6074 += arg3.movementBaseY[0];
				arg3.pathPointer = 1;
				arg3.anInt6052 += arg3.movementBaseY[0];
			}
		}
		@Pc(243) int local243;
		if ((arg0 & 0x800) != 0) {
			local243 = arg1.method2548();
			arg3.anInt6051 = arg1.method2490();
			arg3.anInt6054 = arg1.method2520();
			arg3.aBoolean559 = (local243 & 0x8000) != 0;
			arg3.anInt6041 = local243 & 0x7FFF;
			arg3.anInt6035 = Shadow.anInt5323 + arg3.anInt6041 + arg3.anInt6051;
		}
		if ((arg0 & 0x10000) != 0) {
			local243 = arg1.method2490();
			@Pc(297) int[] local297 = new int[local243];
			@Pc(300) int[] local300 = new int[local243];
			@Pc(303) int[] local303 = new int[local243];
			for (@Pc(305) int local305 = 0; local305 < local243; local305++) {
				@Pc(311) int local311 = arg1.method2536();
				if (local311 == 65535) {
					local311 = -1;
				}
				local297[local305] = local311;
				local300[local305] = arg1.method2489();
				local303[local305] = arg1.method2538();
			}
			Class223.method5000(local303, local300, arg3, local297);
		}
		@Pc(363) int local363;
		@Pc(367) int local367;
		if ((arg0 & 0x40000) != 0) {
			local243 = arg1.method2538();
			if (local243 == 65535) {
				local243 = -1;
			}
			local363 = arg1.method2510();
			local367 = arg1.method2523();
			arg3.method4748(local367, local363, true, local243);
		}
		if ((arg0 & 0x1) != 0) {
			Static411.aByteArray95[arg2] = arg1.method2507();
		}
		if ((arg0 & 0x2) != 0) {
			local243 = arg1.method2515();
			if (local243 == 65535) {
				local243 = -1;
			}
			arg3.anInt6034 = local243;
		}
		if ((arg0 & 0x80) != 0) {
			local243 = arg1.method2548();
			local363 = arg1.method2520();
			local367 = arg1.method2520();
			@Pc(427) int local427 = arg1.currentPosition;
			@Pc(435) boolean local435 = (local243 & 0x8000) != 0;
			if (arg3.aString44 != null && arg3.playerModel != null) {
				@Pc(443) boolean local443 = false;
				if (local363 <= 1) {
					if (!local435 && (Class16.aBoolean628 && !MemoryPool.aBoolean443 || Static308.aBoolean486)) {
						local443 = true;
					} else if (ParticleList.method3549(arg3.aString44)) {
						local443 = true;
					}
				}
				if (!local443 && Texture3DShaderGL.anInt1373 == 0) {
					ByteArrayWrapper.aClass4_Sub12_2.currentPosition = 0;
					arg1.method2508(local367, ByteArrayWrapper.aClass4_Sub12_2.buffer);
					ByteArrayWrapper.aClass4_Sub12_2.currentPosition = 0;
					@Pc(489) int local489 = -1;
					@Pc(508) String local508;
					if (local435) {
						local243 &= 0x7FFF;
						@Pc(499) QuickChatPhrase local499 = StillWaterEffect.method5378(ByteArrayWrapper.aClass4_Sub12_2);
						local489 = local499.id;
						local508 = local499.type.method234(ByteArrayWrapper.aClass4_Sub12_2);
					} else {
						local508 = Class16_Sub1_Sub1.method297(MECType.method3776(ByteArrayWrapper.aClass4_Sub12_2));
					}
					arg3.aString60 = local508.trim();
					arg3.anInt6077 = 150;
					arg3.anInt6064 = local243 & 0xFF;
					arg3.anInt6084 = local243 >> 8;
					@Pc(549) int local549;
					if (local363 == 1 || local363 == 2) {
						local549 = local435 ? 17 : 1;
					} else {
						local549 = local435 ? 17 : 2;
					}
					if (local363 == 2) {
						LocalRequester.method5438(local489, "<img=1>" + arg3.method3432(), local508, null, 0, "<img=1>" + arg3.method3428(), local549);
					} else if (local363 == 1) {
						LocalRequester.method5438(local489, "<img=0>" + arg3.method3432(), local508, null, 0, "<img=0>" + arg3.method3428(), local549);
					} else {
						LocalRequester.method5438(local489, arg3.method3432(), local508, null, 0, arg3.method3428(), local549);
					}
				}
			}
			arg1.currentPosition = local367 + local427;
		}
		if ((arg0 & 0x1000) != 0) {
			local243 = arg1.method2538();
			if (local243 == 65535) {
				local243 = -1;
			}
			local363 = arg1.method2529();
			local367 = arg1.method2489();
			arg3.method4748(local367, local363, false, local243);
		}
		if ((arg0 & 0x8) != 0) {
			local243 = arg1.method2536();
			if (local243 == 65535) {
				local243 = -1;
			}
			local363 = arg1.method2490();
			Class4_Sub3.method185(local363, arg3, local243);
		}
		if ((arg0 & 0x20000) != 0) {
			arg3.aByte71 = arg1.method2507();
			arg3.aByte69 = arg1.method2534();
			arg3.aByte70 = arg1.method2542();
			arg3.aByte68 = (byte) arg1.method2489();
			arg3.anInt6020 = Shadow.anInt5323 + arg1.method2548();
			arg3.anInt6068 = Shadow.anInt5323 + arg1.method2538();
		}
		if ((arg0 & 0x200) != 0) {
			local7 = arg1.method2507();
		}
		if ((arg0 & 0x10) != 0) {
			local243 = arg1.method2506();
			local363 = arg1.method2520();
			arg3.method4746(local243, Shadow.anInt5323, local363);
			arg3.anInt6072 = Shadow.anInt5323 + 300;
			arg3.anInt6026 = arg1.method2520();
		}
		if ((arg0 & 0x4000) != 0) {
			arg3.pMapdot = arg1.method2520() == 1;
		}
		if ((arg0 & 0x20) != 0) {
			local243 = arg1.method2490();
			@Pc(818) byte[] local818 = new byte[local243];
			@Pc(823) Packet local823 = new Packet(local818);
			arg1.method2508(local243, local818);
			ItemType.aClass4_Sub12Array1[arg2] = local823;
			arg3.method3422(local823);
		}
		if ((arg0 & 0x100) != 0) {
			local243 = Shadow.anInt5323;
			local363 = arg1.method2506();
			local367 = arg1.method2489();
			arg3.method4746(local363, local243, local367);
		}
		if (!arg3.hasPendingPositionUpdate) {
			return;
		}
		if (local7 == 127) {
			arg3.move(arg3.anInt4317, arg3.anInt4335);
			return;
		}
		@Pc(884) byte local884;
		if (local7 == -1) {
			local884 = Static411.aByteArray95[arg2];
		} else {
			local884 = local7;
		}
		arg3.method3423(local884, arg3.anInt4317, arg3.anInt4335);
	}
@OriginalMember(owner = "client!bv", name = "a", descriptor = "(I)V")
	public static void method730() {
		Class180.aClass183_39.method4138();
		for (@Pc(15) OggOtherStream local15 = (OggOtherStream) Class126.aClass183_28.method4140(); local15 != null; local15 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			if (local15.anInt5699 < 1000) {
				local15.method5684();
				Class180.aClass183_39.method4137(local15);
			}
		}
		Class180.aClass183_39.method4149(Class126.aClass183_28);
		@Pc(43) int local43 = -1;
		@Pc(48) Class4_Sub5 local48 = (Class4_Sub5) Component.aClass183_47.method4140();
		if (local48 != null) {
			local43 = local48.method515();
		}
		if (!Class4_Sub6_Sub22.aBoolean411) {
			if (local43 == 0 && (SpotAnimation.anInt1361 == 1 && Skybox.anInt6710 > 2 || Class101.method2677())) {
				local43 = 2;
			}
			if (local43 == 2 && Skybox.anInt6710 > 0 && local48 != null) {
				if (Static281.aClass247_12 == null && SpotAnimation.anInt1367 == 0) {
					Class16_Sub5_Sub2.method4658(local48.method519(), local48.method518());
				} else {
					Mesh.anInt3685 = 2;
				}
			}
			if (local43 == 0 && Skybox.anInt6710 > 0) {
				Static231.method3475();
			}
			if (Static281.aClass247_12 == null && SpotAnimation.anInt1367 == 0) {
				Mesh.anInt3685 = 0;
				Component.aClass4_Sub39_2 = null;
				return;
			}
			return;
		}
		@Pc(133) int local133;
		@Pc(137) int local137;
		if (local43 == -1) {
			local133 = MaterialType.aClass80_1.method2234();
			local137 = MaterialType.aClass80_1.getDefaultValue();
			if (Class4_Sub37.anInt5424 - 10 > local133 || local133 > Static173.anInt3370 + Class4_Sub37.anInt5424 + 10 || Class111.anInt3367 - 10 > local137 || local137 > Class4_Sub18.anInt2392 + Class111.anInt3367 + 10) {
				Class4_Sub6_Sub22.aBoolean411 = false;
				EffectiveVertex.method4103(Class4_Sub37.anInt5424, Class4_Sub18.anInt2392, Static173.anInt3370, Class111.anInt3367);
			}
		}
		if (local43 != 0) {
			return;
		}
		local133 = Class4_Sub37.anInt5424;
		local137 = Class111.anInt3367;
		@Pc(188) int local188 = Static173.anInt3370;
		@Pc(194) int local194 = local48.method518();
		@Pc(198) int local198 = local48.method519();
		@Pc(200) int local200 = -1;
		@Pc(219) int local219;
		for (@Pc(202) int local202 = 0; local202 < Skybox.anInt6710; local202++) {
			if (Class177.aBoolean481) {
				local219 = local137 + (-local202 + -1 + Skybox.anInt6710) * 16 + 33;
				if (local194 > local133 && local194 < local133 + local188 && local198 > local219 - 13 && local198 < local219 + 4) {
					local200 = local202;
				}
			} else {
				local219 = local137 + (Skybox.anInt6710 - local202 - 1) * 16 + 31;
				if (local133 < local194 && local188 + local133 > local194 && local219 - 13 < local198 && local219 + 3 > local198) {
					local200 = local202;
				}
			}
		}
		if (local200 != -1) {
			local219 = 0;
			@Pc(301) DequeIterator local301 = new DequeIterator(Class126.aClass183_28);
			for (@Pc(306) OggOtherStream local306 = (OggOtherStream) local301.method1611(); local306 != null; local306 = (OggOtherStream) local301.method1612()) {
				if (local219 == local200) {
					GlFramebuffer.method3617(local198, local306, local194);
				}
				local219++;
			}
		}
		Class4_Sub6_Sub22.aBoolean411 = false;
		EffectiveVertex.method4103(Class4_Sub37.anInt5424, Class4_Sub18.anInt2392, Static173.anInt3370, Class111.anInt3367);
	}
}
