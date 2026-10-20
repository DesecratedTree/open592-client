package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.graphics.model.particle.Particle;
import jaggl.OpenGL;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.entity.*;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!mo")
public final class ParticleEmitter extends EntityNode {

	@OriginalMember(owner = "client!mo", name = "j", descriptor = "I")
	private int anInt4628;

	@OriginalMember(owner = "client!mo", name = "l", descriptor = "I")
	private int anInt4630;

	@OriginalMember(owner = "client!mo", name = "m", descriptor = "I")
	public int anInt4631;

	@OriginalMember(owner = "client!mo", name = "n", descriptor = "I")
	private int anInt4632;

	@OriginalMember(owner = "client!mo", name = "o", descriptor = "I")
	private int anInt4633;

	@OriginalMember(owner = "client!mo", name = "p", descriptor = "I")
	private int anInt4634;

	@OriginalMember(owner = "client!mo", name = "q", descriptor = "I")
	private int anInt4635;

	@OriginalMember(owner = "client!mo", name = "t", descriptor = "I")
	private int anInt4637;

	@OriginalMember(owner = "client!mo", name = "u", descriptor = "I")
	private int anInt4638;

	@OriginalMember(owner = "client!mo", name = "x", descriptor = "I")
	private int anInt4641;

	@OriginalMember(owner = "client!mo", name = "B", descriptor = "I")
	private int anInt4643;

	@OriginalMember(owner = "client!mo", name = "C", descriptor = "I")
	private int anInt4644;

	@OriginalMember(owner = "client!mo", name = "D", descriptor = "I")
	private int anInt4645;

	@OriginalMember(owner = "client!mo", name = "F", descriptor = "I")
	private int anInt4647;

	@OriginalMember(owner = "client!mo", name = "H", descriptor = "I")
	private int anInt4648;

	@OriginalMember(owner = "client!mo", name = "J", descriptor = "I")
	public int anInt4650;

	@OriginalMember(owner = "client!mo", name = "L", descriptor = "I")
	private int anInt4651;

	@OriginalMember(owner = "client!mo", name = "M", descriptor = "I")
	public int anInt4652;

	@OriginalMember(owner = "client!mo", name = "O", descriptor = "I")
	public int anInt4653;

	@OriginalMember(owner = "client!mo", name = "S", descriptor = "I")
	private int anInt4655;

	@OriginalMember(owner = "client!mo", name = "A", descriptor = "I")
	private int anInt4642 = 0;

	@OriginalMember(owner = "client!mo", name = "z", descriptor = "Z")
	private boolean finished = false;

	@OriginalMember(owner = "client!mo", name = "Q", descriptor = "Z")
	public boolean inactive = false;

	@OriginalMember(owner = "client!mo", name = "N", descriptor = "Lclient!ao;")
	public final ParticleSystem system;

	@OriginalMember(owner = "client!mo", name = "P", descriptor = "J")
	private final long startTime;

	@OriginalMember(owner = "client!mo", name = "G", descriptor = "Lclient!bu;")
	public final EmissiveTriangle model;

	@OriginalMember(owner = "client!mo", name = "K", descriptor = "Lclient!jv;")
	public ParticleProducerType type;

	@OriginalMember(owner = "client!mo", name = "s", descriptor = "Lclient!gn;")
	public final EntityNodeList aClass84_6;

	@OriginalMember(owner = "client!mo", name = "<init>", descriptor = "(Lclient!ya;Lclient!bu;Lclient!ao;J)V")
	public ParticleEmitter(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) EmissiveTriangle modelParticleEmitter, @OriginalArg(2) ParticleSystem particleSystem, @OriginalArg(3) long startTime) {
		this.system = particleSystem;
		this.startTime = startTime;
		this.model = modelParticleEmitter;
		this.type = this.model.method695();
		if (!toolkit.hasTextures() && this.type.nonTexturedProducerId != -1) {
			this.type = WaterfallEffect.method3927(this.type.nonTexturedProducerId);
		}
		this.aClass84_6 = new EntityNodeList();
		this.anInt4642 = (int) ((double) this.anInt4642 + Math.random() * 64.0D);
		this.method3630();
	}

	@OriginalMember(owner = "client!mo", name = "c", descriptor = "(I)V")
	public void method3630() {
		this.anInt4635 = this.model.anInt711;
		this.anInt4633 = this.model.anInt700;
		this.anInt4651 = this.model.anInt707;
		this.anInt4630 = this.model.anInt708;
		this.anInt4634 = this.model.anInt701;
		this.anInt4638 = this.model.anInt699;
		this.anInt4637 = this.model.anInt702;
		this.anInt4645 = this.model.anInt709;
		this.anInt4644 = this.model.anInt703;
		if (this.anInt4638 == this.anInt4634 && this.anInt4634 == this.anInt4630 && this.anInt4637 == this.anInt4633 && this.anInt4637 == this.anInt4644 && this.anInt4651 == this.anInt4635 && this.anInt4645 == this.anInt4651) {
			this.finished = true;
			return;
		}
		this.finished = false;
		@Pc(122) int local122 = (this.anInt4630 + this.anInt4634 + this.anInt4638) / 3;
		@Pc(134) int local134 = (this.anInt4644 + this.anInt4633 + this.anInt4637) / 3;
		@Pc(146) int local146 = (this.anInt4645 + this.anInt4635 + this.anInt4651) / 3;
		if (this.anInt4631 == local122 && this.anInt4652 == local134 && local146 == this.anInt4653) {
			return;
		}
		this.anInt4652 = local134;
		this.anInt4653 = local146;
		this.anInt4631 = local122;
		@Pc(182) int local182 = this.anInt4634 - this.anInt4638;
		@Pc(189) int local189 = this.anInt4637 - this.anInt4633;
		@Pc(196) int local196 = this.anInt4651 - this.anInt4635;
		@Pc(202) int local202 = this.anInt4630 - this.anInt4638;
		@Pc(208) int local208 = this.anInt4644 - this.anInt4633;
		@Pc(215) int local215 = this.anInt4645 - this.anInt4635;
		this.anInt4655 = local208 * local182 - local189 * local202;
		this.anInt4648 = local215 * local189 - local196 * local208;
		this.anInt4628 = local202 * local196 - local182 * local215;
		while (this.anInt4648 > 32767 || this.anInt4628 > 32767 || this.anInt4655 > 32767 || this.anInt4648 < -32767 || this.anInt4628 < -32767 || this.anInt4655 < -32767) {
			this.anInt4648 >>= 0x1;
			this.anInt4628 >>= 0x1;
			this.anInt4655 >>= 0x1;
		}
		@Pc(317) int local317 = (int) Math.sqrt((double) (this.anInt4655 * this.anInt4655 + this.anInt4648 * this.anInt4648 + this.anInt4628 * this.anInt4628));
		if (local317 <= 0) {
			local317 = 1;
		}
		this.anInt4628 = this.anInt4628 * 32767 / local317;
		this.anInt4655 = this.anInt4655 * 32767 / local317;
		this.anInt4648 = this.anInt4648 * 32767 / local317;
		if (this.type.aShort41 <= 0 && this.type.aShort44 <= 0) {
			return;
		}
		@Pc(370) int local370 = (int) (Math.atan2((double) this.anInt4655, (double) this.anInt4648) * 2607.5945876176133D);
		@Pc(392) int local392 = (int) (Math.atan2((double) this.anInt4628, Math.sqrt((double) (this.anInt4648 * this.anInt4648 + this.anInt4655 * this.anInt4655))) * 2607.5945876176133D);
		this.anInt4643 = this.type.aShort41 - this.type.aShort42;
		this.anInt4641 = this.type.aShort44 - this.type.aShort43;
		this.anInt4632 = local370 + this.type.aShort42 - (this.anInt4643 >> 1);
		this.anInt4647 = local392 + this.type.aShort43 - (this.anInt4641 >> 1);
	}

	@OriginalMember(owner = "client!mo", name = "a", descriptor = "(IIJZLclient!ya;)V")
	public void method3632(@OriginalArg(0) int arg0, @OriginalArg(2) long arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) com.jagex.game.runetek5.graphics.Toolkit arg3) {
		@Pc(40) int local40;
		if (this.inactive) {
			arg2 = false;
		} else if (this.type.anInt3798 > TextureOpDiagonalWaves.anInt4008) {
			arg2 = false;
		} else if (GlRectangleTexture.anInt5116 > BreakingWaterEffect.anIntArray113[TextureOpDiagonalWaves.anInt4008]) {
			arg2 = false;
		} else if (this.finished) {
			arg2 = false;
		} else if (this.type.anInt3810 != -1) {
			local40 = (int) (arg1 - this.startTime);
			if (this.type.aBoolean353 || local40 <= this.type.anInt3810) {
				local40 %= this.type.anInt3810;
			} else {
				arg2 = false;
			}
			if (!this.type.aBoolean355 && this.type.lb > local40) {
				arg2 = false;
			}
			if (this.type.aBoolean355 && this.type.lb <= local40) {
				arg2 = false;
			}
		}
		if (arg2) {
			this.anInt4642 += (int) (((double) this.type.anInt3818 + Math.random() * (double) (this.type.anInt3773 - this.type.anInt3818)) * (double) arg0);
			if (this.anInt4642 > 63) {
				local40 = this.anInt4642 >> 6;
				this.anInt4642 &= 0x3F;
				for (@Pc(151) int local151 = 0; local151 < local40; local151++) {
					@Pc(173) int local173;
					@Pc(170) int local170;
					@Pc(176) int local176;
					@Pc(188) int local188;
					@Pc(196) int local196;
					@Pc(200) int local200;
					@Pc(210) int local210;
					@Pc(218) int local218;
					@Pc(222) int local222;
					if (this.type.aShort41 <= 0 && this.type.aShort44 <= 0) {
						local170 = this.anInt4628;
						local173 = this.anInt4648;
						local176 = this.anInt4655;
					} else {
						local188 = (int) (Math.random() * (double) this.anInt4643) + this.anInt4632;
						@Pc(192) int local192 = local188 & 0x3FFF;
						local196 = Class50_Sub1.anIntArray225[local192];
						local200 = Class50_Sub1.anIntArray224[local192];
						local210 = this.anInt4647 + (int) (Math.random() * (double) this.anInt4641);
						@Pc(214) int local214 = local210 & 0x1FFF;
						local218 = Class50_Sub1.anIntArray225[local214];
						local222 = Class50_Sub1.anIntArray224[local214];
						local170 = (local222 << 0) * -1;
						local173 = local200 * local218 >> 15;
						local176 = local218 * local196 >> 15;
					}
					local188 = (int) (Math.random() * 65535.0D);
					local196 = (int) (Math.random() * 65535.0D);
					if (local188 + local196 > 65535) {
						local196 = 65535 - local196;
						local188 = 65535 - local188;
					}
					local200 = 65535 - local188 - local196;
					local210 = this.anInt4634 * local196 + this.anInt4638 * local188 + local200 * this.anInt4630 >> 16;
					local218 = local200 * this.anInt4644 + this.anInt4637 * local196 + local188 * this.anInt4633 >> 16;
					local222 = this.anInt4651 * local196 + this.anInt4635 * local188 + this.anInt4645 * local200 >> 16;
					@Pc(344) int local344 = (int) ((double) (this.type.anInt3800 - this.type.anInt3804) * Math.random()) + this.type.anInt3804;
					@Pc(362) int local362 = this.type.anInt3775 + (int) ((double) (this.type.anInt3780 - this.type.anInt3775) * Math.random());
					@Pc(379) int local379 = (int) (Math.random() * (double) (this.type.anInt3816 - this.type.anInt3788)) + this.type.anInt3788;
					@Pc(441) int local441;
					if (this.type.aBoolean360) {
						@Pc(445) double local445 = Math.random();
						local441 = (int) (Math.random() * (double) this.type.alphaVariance + (double) this.type.minimumStartA) << 24 | (int) ((double) this.type.anInt3783 + local445 * (double) this.type.anInt3799) << 16 | (int) (local445 * (double) this.type.anInt3774 + (double) this.type.minimumStartG) << 8 | (int) (local445 * (double) this.type.anInt3785 + (double) this.type.maximumStartB);
					} else {
						local441 = (int) ((double) this.type.minimumStartA + Math.random() * (double) this.type.alphaVariance) << 24 | (int) (Math.random() * (double) this.type.anInt3799 + (double) this.type.anInt3783) << 16 | (int) ((double) this.type.minimumStartG + Math.random() * (double) this.type.anInt3774) << 8 | (int) (Math.random() * (double) this.type.anInt3785 + (double) this.type.maximumStartB);
					}
					@Pc(507) int local507 = this.type.minimumSetting;
					if (!arg3.hasTextures() && !this.type.aBoolean358) {
						local507 = -1;
					}
					if (MECTypeList.anInt5385 == CollisionMap.anInt6240) {
						new Particle(this, local210, local218, local222, local173, local170, local176, local344, local362, local441, local379, local507, this.type.aBoolean357, this.type.aBoolean354);
					} else {
						@Pc(547) Particle local547 = Class4_Sub45.aClass3_Sub1_Sub1_Sub1Array2[CollisionMap.anInt6240];
						CollisionMap.anInt6240 = CollisionMap.anInt6240 + 1 & 0x3FF;
						local547.reset(this, local210, local218, local222, local173, local170, local176, local344, local362, local441, local379, local507, this.type.aBoolean357, this.type.aBoolean354);
					}
				}
			}
		}
		this.anInt4650 = 0;
		for (@Pc(586) Particle local586 = (Particle) this.aClass84_6.method2312(); local586 != null; local586 = (Particle) this.aClass84_6.method2309()) {
			local586.tick(arg1, arg0);
			this.anInt4650++;
		}
		Class259.anInt7099 += this.anInt4650;
	}

	@OriginalMember(owner = "client!mo", name = "a", descriptor = "(ZJ)V")
	public void method3635(@OriginalArg(1) long arg0) {
		for (@Pc(21) Particle local21 = (Particle) this.aClass84_6.method2312(); local21 != null; local21 = (Particle) this.aClass84_6.method2309()) {
			local21.method3304(arg0);
		}
	}

@OriginalMember(owner = "client!mo", name = "v", descriptor = "I")
	public static int anInt4639 = -2;
@OriginalMember(owner = "client!mo", name = "y", descriptor = "Lclient!tn;")
	public static final Class229 OPNPC6 = new Class229();
@OriginalMember(owner = "client!mo", name = "a", descriptor = "(II)V")
	public static void method3629(@OriginalArg(1) int arg0) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg0, 2);
		local8.method2608();
	}
@OriginalMember(owner = "client!mo", name = "b", descriptor = "(II)Z")
	public static boolean method3631(@OriginalArg(1) int arg0) {
		return arg0 >= 12 && arg0 <= 17;
	}
@OriginalMember(owner = "client!mo", name = "a", descriptor = "(BII[FIII)V")
	public static void method3634(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) float[] arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		if (arg4 > 0 && !Class4_Sub43.method5413(arg4)) {
			throw new IllegalArgumentException("");
		} else if (arg5 <= 0 || Class4_Sub43.method5413(arg5)) {
			@Pc(34) int local34 = ParticleType.method92(6408);
			@Pc(36) int local36 = 0;
			@Pc(47) int local47 = arg4 < arg5 ? arg4 : arg5;
			@Pc(51) int local51 = arg4 >> 1;
			@Pc(55) int local55 = arg5 >> 1;
			@Pc(63) float[] local63 = arg2;
			@Pc(70) float[] local70 = new float[local55 * local51 * local34];
			while (true) {
				OpenGL.glTexImage2Df(3553, local36, 34842, arg4, arg5, 0, 6408, 5126, local63, 0);
				if (local47 <= 1) {
					return;
				}
				@Pc(90) int local90 = local34 * arg4;
				for (@Pc(92) int local92 = 0; local92 < local34; local92++) {
					@Pc(96) int local96 = local92;
					@Pc(98) int local98 = local92;
					@Pc(102) int local102 = local90 + local92;
					for (@Pc(104) int local104 = 0; local104 < local55; local104++) {
						for (@Pc(108) int local108 = 0; local108 < local51; local108++) {
							@Pc(114) float local114 = local63[local98];
							local98 += local34;
							@Pc(124) float local124 = local114 + local63[local98];
							@Pc(130) float local130 = local124 + local63[local102];
							local98 += local34;
							local102 += local34;
							@Pc(144) float local144 = local130 + local63[local102];
							local102 += local34;
							local70[local96] = local144 * 0.25F;
							local96 += local34;
						}
						local98 += local90;
						local102 += local90;
					}
				}
				@Pc(184) float[] local184 = local70;
				local70 = local63;
				arg4 = local51;
				local63 = local184;
				arg5 = local55;
				local36++;
				local51 >>= 0x1;
				local55 >>= 0x1;
				local47 >>= 0x1;
			}
		} else {
			throw new IllegalArgumentException("");
		}
	}
}
