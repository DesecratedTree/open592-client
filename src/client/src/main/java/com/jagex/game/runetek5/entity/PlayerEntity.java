package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.ra;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static398;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.entity.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.graphics.FramebufferCompositor;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9_Sub2;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!lh")
public final class PlayerEntity extends PathingEntity {

	@OriginalMember(owner = "client!lh", name = "Fc", descriptor = "I")
	public int anInt4317;

	@OriginalMember(owner = "client!lh", name = "Gc", descriptor = "Ljava/lang/String;")
	public String aString43;

	@OriginalMember(owner = "client!lh", name = "Oc", descriptor = "Lclient!cj;")
	public PlayerAppearance playerModel;

	@OriginalMember(owner = "client!lh", name = "Rc", descriptor = "I")
	private int basId;

	@OriginalMember(owner = "client!lh", name = "Xc", descriptor = "Ljava/lang/String;")
	public String aString44;

	@OriginalMember(owner = "client!lh", name = "ed", descriptor = "I")
	public int anInt4335;

	@OriginalMember(owner = "client!lh", name = "zc", descriptor = "B")
	private byte titleId = 0;

	@OriginalMember(owner = "client!lh", name = "Bc", descriptor = "I")
	public int walkingSoundEffectId = -1;

	@OriginalMember(owner = "client!lh", name = "vc", descriptor = "B")
	private byte titleType = 0;

	@OriginalMember(owner = "client!lh", name = "Qc", descriptor = "I")
	public int anInt4323 = -1;

	@OriginalMember(owner = "client!lh", name = "Kc", descriptor = "I")
	public int anInt4319 = -1;

	@OriginalMember(owner = "client!lh", name = "Yc", descriptor = "I")
	public int anInt4330 = -1;

	@OriginalMember(owner = "client!lh", name = "cd", descriptor = "I")
	public int anInt4333 = -1;

	@OriginalMember(owner = "client!lh", name = "fd", descriptor = "I")
	public int anInt4336 = 0;

	@OriginalMember(owner = "client!lh", name = "Vc", descriptor = "I")
	public int anInt4328 = 0;

	@OriginalMember(owner = "client!lh", name = "Zc", descriptor = "Z")
	public boolean hasPendingPositionUpdate = false;

	@OriginalMember(owner = "client!lh", name = "ld", descriptor = "Z")
	public boolean shouldSuppressMovingAnimation = false;

	@OriginalMember(owner = "client!lh", name = "Mc", descriptor = "Z")
	public boolean pMapdot = false;

	@OriginalMember(owner = "client!lh", name = "bd", descriptor = "I")
	public int anInt4332 = 0;

	@OriginalMember(owner = "client!lh", name = "Hc", descriptor = "B")
	private byte gender = 0;

	@OriginalMember(owner = "client!lh", name = "jd", descriptor = "I")
	public int anInt4339 = -1;

	@OriginalMember(owner = "client!lh", name = "td", descriptor = "I")
	public int anInt4346 = -1;

	@OriginalMember(owner = "client!lh", name = "Pc", descriptor = "I")
	public int anInt4322 = -1;

	@OriginalMember(owner = "client!lh", name = "dd", descriptor = "I")
	public int isNpc = 0;

	@OriginalMember(owner = "client!lh", name = "qd", descriptor = "I")
	public int anInt4345 = 0;

	@OriginalMember(owner = "client!lh", name = "wd", descriptor = "I")
	public int anInt4348 = 255;

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(Lclient!iv;I)V")
	public void method3422(@OriginalArg(0) Packet arg0) {
		arg0.currentPosition = 0;
		@Pc(14) int local14 = arg0.method2490();
		this.gender = (byte) (local14 & 0x1);
		@Pc(31) boolean local31 = (local14 & 0x2) != 0;
		@Pc(42) boolean local42 = (local14 & 0x4) != 0;
		@Pc(46) int local46 = super.getSize();
		this.setSize((local14 >> 3 & 0x7) + 1);
		this.titleType = (byte) (local14 >> 6 & 0x3);
		super.anInt6893 += this.getSize() - local46 << 6;
		super.anInt6892 += this.getSize() - local46 << 6;
		this.titleId = arg0.method2542();
		this.anInt4339 = arg0.method2542();
		this.anInt4346 = arg0.method2542();
		@Pc(106) int local106 = -1;
		this.anInt4328 = 0;
		@Pc(112) int[] local112 = new int[12];
		@Pc(134) int local134;
		@Pc(141) int local141;
		@Pc(180) int local180;
		for (@Pc(114) int local114 = 0; local114 < 12; local114++) {
			@Pc(122) int local122 = arg0.method2490();
			if (local122 == 0) {
				local112[local114] = 0;
			} else {
				local134 = arg0.method2490();
				local141 = (local122 << 8) + local134;
				if (local114 == 0 && local141 == 65535) {
					local106 = arg0.method2536();
					this.anInt4328 = arg0.method2490();
					break;
				}
				if (local141 >= 32768) {
					local141 = Class16_Sub4_Sub2.anIntArray170[local141 - 32768];
					local112[local114] = local141 | 0x40000000;
					local180 = Terrain.aClass206_3.method4703(local141).anInt6181;
					if (local180 != 0) {
						this.anInt4328 = local180;
					}
				} else {
					local112[local114] = local141 - 256 | Integer.MIN_VALUE;
				}
			}
		}
		@Pc(205) int[] local205 = new int[5];
		for (local134 = 0; local134 < 5; local134++) {
			local141 = arg0.method2490();
			if (local141 < 0 || local141 >= KeyRecordEntry.aShortArrayArray2[local134].length) {
				local141 = 0;
			}
			local205[local134] = local141;
		}
		this.basId = arg0.method2536();
		this.aString43 = arg0.method2537();
		if (local31) {
			this.aString44 = arg0.method2537();
		} else {
			this.aString44 = this.aString43;
		}
		this.anInt4345 = arg0.method2490();
		if (local42) {
			this.anInt4336 = arg0.method2536();
			this.anInt4332 = this.anInt4345;
			this.anInt4330 = -1;
		} else {
			this.anInt4336 = 0;
			this.anInt4332 = arg0.method2490();
			this.anInt4330 = arg0.method2490();
			if (this.anInt4330 == 255) {
				this.anInt4330 = -1;
			}
		}
		local141 = this.isNpc;
		this.isNpc = arg0.method2490();
		@Pc(336) int local336;
		if (this.isNpc == 0) {
			ObjTypeList.method4705(this);
		} else {
			local180 = this.walkingSoundEffectId;
			@Pc(333) int local333 = this.anInt4319;
			local336 = this.anInt4333;
			@Pc(339) int local339 = this.anInt4322;
			@Pc(342) int local342 = this.anInt4348;
			this.walkingSoundEffectId = arg0.method2536();
			this.anInt4319 = arg0.method2536();
			this.anInt4333 = arg0.method2536();
			this.anInt4322 = arg0.method2536();
			this.anInt4348 = arg0.method2490();
			if (local141 != this.isNpc || this.walkingSoundEffectId != local180 || this.anInt4319 != local333 || this.anInt4333 != local336 || local339 != this.anInt4322 || this.anInt4348 != local342) {
				GlBitBuffer.method2555(this);
			}
		}
		if (this.playerModel == null) {
			this.playerModel = new PlayerAppearance();
		}
		local180 = this.playerModel.morphism;
		@Pc(422) int[] local422 = this.playerModel.anIntArray52;
		this.playerModel.method835(this.gender == 1, local106, this.getBasId(), local112, local205);
		if (local106 != local180) {
			super.anInt6893 = (super.movementBaseX[0] << 7) + (this.getSize() << 6);
			super.anInt6892 = (super.movementBaseY[0] << 7) + (this.getSize() << 6);
		}
		if (super.anInt6037 == Class6.anInt5452 && local422 != null) {
			for (local336 = 0; local336 < local205.length; local336++) {
				if (local422[local336] != local205[local336]) {
					Terrain.aClass206_3.method4701();
					break;
				}
			}
		}
		if (super.aClass3_Sub2_7 != null) {
			super.aClass3_Sub2_7.restart();
		}
		if (super.anInt6076 == -1 || !super.aBoolean562) {
			return;
		}
		@Pc(521) BasType local521 = this.method4757();
		if (!local521.method3642(super.anInt6076)) {
			super.anInt6076 = -1;
			super.aBoolean562 = false;
			return;
		}
	}

	@OriginalMember(owner = "client!lh", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.playerModel != null && (super.particlesInitialized || this.method3430(arg0, 0))) {
			this.method4741(super.particlesInitialized, super.models, arg0);
			super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		}
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(BIII)V")
	public void method3423(@OriginalArg(0) byte arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		if (super.anInt6021 != -1 && SceneLocEntity.aClass55_1.method1397(super.anInt6021).moveType == 1) {
			super.anInt6021 = -1;
		}
		@Pc(36) SpotAnimationType local36;
		if (super.anInt6029 != -1) {
			local36 = Js5MasterIndex.aClass194_2.method4421(super.anInt6029);
			if (local36.replay && local36.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local36.sequenceId).moveType == 1) {
				super.anInt6029 = -1;
			}
		}
		if (super.anInt6045 != -1) {
			local36 = Js5MasterIndex.aClass194_2.method4421(super.anInt6045);
			if (local36.replay && local36.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local36.sequenceId).moveType == 1) {
				super.anInt6045 = -1;
			}
		}
		this.anInt4323 = -1;
		if (arg2 < 0 || arg2 >= GLBillowTextureData.anInt5666 || arg1 < 0 || Static283.anInt5187 <= arg1) {
			this.move(arg1, arg2);
		} else if (super.movementBaseX[0] >= 0 && super.movementBaseX[0] < GLBillowTextureData.anInt5666 && super.movementBaseY[0] >= 0 && super.movementBaseY[0] < Static283.anInt5187) {
			if (arg0 == 2) {
				Class3_Sub3.method2022(this, arg2, arg1);
			}
			this.method3427(arg1, arg2, arg0);
		} else {
			this.move(arg1, arg2);
		}
	}

	@OriginalMember(owner = "client!lh", name = "f", descriptor = "(B)I")
	@Override
	public int getOverheadSprite() {
		return -1;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		if (this.playerModel == null || !this.method3430(arg1, 131072)) {
			return false;
		}
		@Pc(19) Ground local19 = arg1.method4289();
		@Pc(26) int local26 = super.yaw.method811();
		local19.ra(local26);
		local19.o(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(39) boolean local39 = false;
		for (@Pc(41) int local41 = 0; local41 < super.models.length; local41++) {
			if (super.models[local41] != null && super.models[local41].picked(arg2, arg0, local19, true)) {
				local39 = true;
				break;
			}
		}
		super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		return local39;
	}

	@OriginalMember(owner = "client!lh", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.playerModel == null || !this.method3430(arg0, 2048)) {
			return null;
		}
		@Pc(19) Ground local19 = arg0.method4289();
		@Pc(24) int local24 = super.yaw.method811();
		local19.ra(local24);
		local19.o(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(38) float local38 = arg0.W();
		@Pc(47) float local47 = arg0.xa();
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean302) {
			@Pc(54) BasType local54 = this.method4757();
			if (local54.aBoolean428 && (this.playerModel.morphism == -1 || Class196.aClass240_1.method5229(this.playerModel.morphism).shadowed)) {
				@Pc(85) SeqType local85 = super.anInt6021 != -1 && super.anInt6046 == 0 ? SceneLocEntity.aClass55_1.method1397(super.anInt6021) : null;
				@Pc(105) SeqType local105 = super.anInt6076 == -1 || this.shouldSuppressMovingAnimation || super.aBoolean562 && local85 != null ? null : SceneLocEntity.aClass55_1.method1397(super.anInt6076);
				@Pc(139) Model local139 = ClientScript.method48(super.modelRotateZ, super.modelRotateX, super.models[0], 0, super.modelTranslateY, 0, arg0, 160, 1, local24, local105 == null ? super.anInt6060 : super.anInt6016, super.transparent, local105 == null ? local85 : local105, 240);
				if (local139 != null) {
					arg0.g(local38, local47 - 128.0F);
					arg0.method4309(false);
					local139.render(local19, null, 0);
					arg0.method4309(true);
				}
			}
		}
		if (Static1.aClass16_Sub1_Sub5_Sub1_1 == this) {
			arg0.g(local38, local47 - 144.0F);
			local19.scaleWidth(super.anInt6893, super.anInt6889, super.anInt6892);
			for (@Pc(180) int local180 = Class183.aClass38Array1.length - 1; local180 >= 0; local180--) {
				@Pc(186) Class38 local186 = Class183.aClass38Array1[local180];
				if (local186 != null && local186.anInt888 != -1) {
					@Pc(228) int local228;
					@Pc(221) int local221;
					if (local186.anInt891 == 1 && local186.anInt885 >= 0 && local186.anInt885 < Class93.aClass16_Sub1_Sub5_Sub2Array1.length) {
						@Pc(212) NPCEntity local212 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local186.anInt885];
						if (local212 != null) {
							local221 = local212.anInt6893 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893;
							local228 = local212.anInt6892 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892;
							this.method3429(local228, super.models[0], local221, arg0, 5760000, local186.anInt888, local19);
						}
					}
					if (local186.anInt891 == 2) {
						@Pc(255) int local255 = local186.anInt892 + 64 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893;
						local221 = local186.anInt894 + 64 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892;
						local228 = local186.anInt889 << 7;
						local228 *= local228;
						this.method3429(local221, super.models[0], local255, arg0, local228, local186.anInt888, local19);
					}
					if (local186.anInt891 == 10 && local186.anInt885 >= 0 && Static267.aClass16_Sub1_Sub5_Sub1Array1.length > local186.anInt885) {
						@Pc(303) PlayerEntity local303 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local186.anInt885];
						if (local303 != null) {
							local221 = local303.anInt6893 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893;
							local228 = local303.anInt6892 - Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892;
							this.method3429(local228, super.models[0], local221, arg0, 5760000, local186.anInt888, local19);
						}
					}
				}
			}
			local19.ra(local24);
			local19.o(super.anInt6893, super.anInt6889, super.anInt6892);
		}
		arg0.g(local38, local47);
		@Pc(360) Class3_Sub3 local360 = Class28.method672(super.models.length);
		if (super.aClass3_Sub2_7 == null) {
			arg0.method4307(super.models, local19, local360.aClass3_Sub5Array1, Static1.aClass16_Sub1_Sub5_Sub1_1 == this ? 1 : 0);
		} else {
			@Pc(367) ParticleList local367 = super.aClass3_Sub2_7.getList();
			arg0.method4299(super.models, local367, local19, local360.aClass3_Sub5Array1, Static1.aClass16_Sub1_Sub5_Sub1_1 == this ? 1 : 0);
		}
		this.method4741(false, super.models, arg0);
		if (super.models[2] != null) {
			if (local24 != 0) {
				super.models[2].Q(local24);
			}
			super.models[2].drawScaledColored(-super.aClass4_Sub23_3.anInt3198 + super.anInt6893, -super.aClass4_Sub23_3.anInt3188 + super.anInt6889, -super.aClass4_Sub23_3.anInt3203 + super.anInt6892);
		}
		super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		super.drawCycle = Class79_Sub1.anInt6264;
		return local360;
	}

	@OriginalMember(owner = "client!lh", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return super.sphereRadius;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(B)I")
	@Override
	protected int getBasId() {
		return this.basId;
	}

	@OriginalMember(owner = "client!lh", name = "h", descriptor = "(I)Z")
	public boolean hasAppearance() {
		return this.playerModel != null;
	}

	@OriginalMember(owner = "client!lh", name = "b", descriptor = "(III)V")
	public void move(@OriginalArg(0) int baseY, @OriginalArg(2) int arg1) {
		super.pathPointer = 0;
		super.anInt6085 = 0;
		super.anInt6087 = 0;
		super.movementBaseX[0] = arg1;
		super.movementBaseY[0] = baseY;
		@Pc(26) int local26 = this.getSize();
		super.anInt6893 = local26 * 64 + super.movementBaseX[0] * 128;
		super.anInt6892 = super.movementBaseY[0] * 128 + local26 * 64;
		if (Static1.aClass16_Sub1_Sub5_Sub1_1 == this) {
			Class133.method3355();
		}
		if (super.aClass3_Sub2_7 != null) {
			super.aClass3_Sub2_7.restart();
		}
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(IIBB)V")
	public void method3427(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) byte arg2) {
		if (super.pathPointer < 9) {
			super.pathPointer++;
		}
		for (@Pc(20) int local20 = super.pathPointer; local20 > 0; local20--) {
			super.movementBaseX[local20] = super.movementBaseX[local20 - 1];
			super.movementBaseY[local20] = super.movementBaseY[local20 - 1];
			super.movementQueueSpeed[local20] = super.movementQueueSpeed[local20 - 1];
		}
		super.movementBaseX[0] = arg1;
		super.movementQueueSpeed[0] = arg2;
		super.movementBaseY[0] = arg0;
	}

	@OriginalMember(owner = "client!lh", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!lh", name = "b", descriptor = "(IZ)Ljava/lang/String;")
	public String method3428() {
		@Pc(15) String local15 = "";
		if (LocTypeList.aStringArray40 != null) {
			local15 = local15 + LocTypeList.aStringArray40[this.titleType];
		}
		@Pc(37) int[] local37;
		if (this.gender == 1 && GlModel.anIntArray217 != null) {
			local37 = GlModel.anIntArray217;
		} else {
			local37 = Class95_Sub2.anIntArray490;
		}
		if (local37 != null && local37[this.titleType] != -1) {
			@Pc(58) EnumType local58 = PingRequest.aClass158_1.method3790(local37[this.titleType]);
			if (local58.valueType == 's') {
				local15 = local15 + local58.getString(this.titleId & 0xFF);
			} else {
				Class64_Sub1.method1654(new Throwable(), "gdn1");
				local37[this.titleType] = -1;
			}
		}
		local15 = local15 + this.aString43;
		if (FramebufferCompositor.aStringArray39 != null) {
			local15 = local15 + FramebufferCompositor.aStringArray39[this.titleType];
		}
		return local15;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(ILclient!t;ILclient!ya;IIILclient!c;)V")
	private void method3429(@OriginalArg(0) int arg0, @OriginalArg(1) Model arg1, @OriginalArg(2) int arg2, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) Ground arg6) {
		@Pc(11) int local11 = arg2 * arg2 + arg0 * arg0;
		if (local11 < 16384 || arg4 < local11) {
			return;
		}
		@Pc(35) int local35 = (int) (Math.atan2((double) arg2, (double) arg0) * 2607.5945876176133D) & 0x3FFF;
		@Pc(55) Model local55 = CacheableNodeByteArray.method3545(arg5, super.modelRotateX, arg3, super.modelRotateZ, local35, super.modelTranslateY);
		if (local55 != null) {
			arg3.method4309(false);
			local55.render(arg6, null, 0);
			arg3.method4309(true);
		}
	}

	@OriginalMember(owner = "client!lh", name = "e", descriptor = "(B)I")
	@Override
	public int getSize() {
		return this.playerModel == null || this.playerModel.morphism == -1 ? super.getSize() : Class196.aClass240_1.method5229(this.playerModel.morphism).anInt7201;
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(ZLclient!ya;I)Z")
	private boolean method3430(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1) {
		@Pc(7) int local7 = arg1;
		@Pc(11) BasType local11 = this.method4757();
		@Pc(30) SeqType local30 = super.anInt6021 != -1 && super.anInt6046 == 0 ? SceneLocEntity.aClass55_1.method1397(super.anInt6021) : null;
		@Pc(50) SeqType local50 = super.anInt6076 == -1 || this.shouldSuppressMovingAnimation || super.aBoolean562 && local30 != null ? null : SceneLocEntity.aClass55_1.method1397(super.anInt6076);
		@Pc(59) int local59 = local11.anInt4662;
		@Pc(62) int local62 = local11.anInt4670;
		if (local59 != 0 || local62 != 0 || local11.anInt4674 != 0 || local11.anInt4660 != 0) {
			arg1 |= 0x7;
		}
		@Pc(104) boolean local104 = super.aByte68 != 0 && super.anInt6020 <= Shadow.anInt5323 && Shadow.anInt5323 < super.anInt6068;
		if (local104) {
			arg1 |= 0x80000;
		}
		@Pc(145) Model local145 = super.models[0] = this.playerModel.method833(Class133.aClass249_1, MaterialType.aClass109_2, local50, arg1, super.sequences, Class196.aClass240_1, super.anInt6058, super.anInt6060, local30, SceneLocEntity.aClass55_1, super.anInt6036, super.anInt6016, arg0, Terrain.aClass206_3, super.anInt6065, Class4_Sub6_Sub24.aClass114_1, super.anInt6073);
		@Pc(148) int local148 = FixedFunctionSpecularPass.method4232();
		if (BreakingWaterEffect.anInt1503 < 96 && local148 > 50) {
			Class30.method743();
		}
		@Pc(177) int local177;
		if (Class222.aClass219_6 != GlToolkit.aClass219_2 && local148 < 50) {
			local177 = 50 - local148;
			while (Static102.anInt2043 < local177) {
				ParticleBinaryTree.aByteArrayArray28[Static102.anInt2043] = new byte[102400];
				Static102.anInt2043++;
			}
			while (local177 < Static102.anInt2043) {
				Static102.anInt2043--;
				ParticleBinaryTree.aByteArrayArray28[Static102.anInt2043] = null;
			}
		} else if (Class222.aClass219_6 != GlToolkit.aClass219_2) {
			ParticleBinaryTree.aByteArrayArray28 = new byte[50][];
			Static102.anInt2043 = 0;
		}
		if (local145 == null) {
			return false;
		}
		super.sphereRadius = local145.setOffsets();
		this.method4747(local145);
		local177 = super.yaw.method811();
		if (local59 == 0 && local62 == 0) {
			this.hillChange(this.getSize() << 7, 0, 0, local177, this.getSize() << 7);
		} else {
			this.hillChange(local59, local11.anInt4671, local11.anInt4693, local177, local62);
			if (super.modelRotateX != 0) {
				local145.Z(super.modelRotateX);
			}
			if (super.modelRotateZ != 0) {
				local145.R(super.modelRotateZ);
			}
			if (super.modelTranslateY != 0) {
				local145.drawScaledColored(0, super.modelTranslateY, 0);
			}
		}
		if (local104) {
			local145.adjustColours(super.aByte71, super.aByte69, super.aByte70, super.aByte68 & 0xFF);
		}
		@Pc(318) SpotAnimationType local318;
		@Pc(330) boolean local330;
		@Pc(332) int local332;
		@Pc(375) Model local375;
		if (this.shouldSuppressMovingAnimation || super.anInt6029 == -1 || super.anInt6027 == -1) {
			super.models[1] = null;
		} else {
			local318 = Js5MasterIndex.aClass194_2.method4421(super.anInt6029);
			local330 = local318.contourType == 3 && (local59 != 0 || local62 != 0);
			local332 = local7;
			if (local330) {
				local332 = local7 | 0x7;
			} else {
				if (super.anInt6017 != 0) {
					local332 = local7 | 0x5;
				}
				if (super.anInt6014 != 0) {
					local332 |= 0x2;
				}
			}
			local375 = super.models[1] = local318.method3830(super.anInt6027, super.anInt6033, SceneLocEntity.aClass55_1, super.anInt6083, local332, arg0);
			if (local375 != null) {
				if (super.anInt6014 != 0) {
					local375.drawScaledColored(0, -super.anInt6014 << 0, 0);
				}
				if (super.anInt6017 != 0) {
					local375.Q(super.anInt6017 * 2048);
				}
				if (local330) {
					if (super.modelRotateX != 0) {
						local375.Z(super.modelRotateX);
					}
					if (super.modelRotateZ != 0) {
						local375.R(super.modelRotateZ);
					}
					if (super.modelTranslateY != 0) {
						local375.drawScaledColored(0, super.modelTranslateY, 0);
					}
				}
			}
		}
		if (this.shouldSuppressMovingAnimation || super.anInt6045 == -1 || super.anInt6022 == -1) {
			super.models[3] = null;
		} else {
			local318 = Js5MasterIndex.aClass194_2.method4421(super.anInt6045);
			local330 = local318.contourType == 3 && (local59 != 0 || local62 != 0);
			local332 = local7;
			if (local330) {
				local332 = local7 | 0x7;
			} else {
				if (super.anInt6056 != 0) {
					local332 = local7 | 0x5;
				}
				if (super.anInt6053 != 0) {
					local332 |= 0x2;
				}
			}
			local375 = super.models[3] = local318.method3837(arg0, super.anInt6062, SceneLocEntity.aClass55_1, super.anInt6022, local332, super.anInt6069);
			if (local375 != null) {
				if (super.anInt6053 != 0) {
					local375.drawScaledColored(0, -super.anInt6053 << 0, 0);
				}
				if (super.anInt6056 != 0) {
					local375.Q(super.anInt6056 * 2048);
				}
				if (local330) {
					if (super.modelRotateX != 0) {
						local375.Z(super.modelRotateX);
					}
					if (super.modelRotateZ != 0) {
						local375.R(super.modelRotateZ);
					}
					if (super.modelTranslateY != 0) {
						local375.drawScaledColored(0, super.modelTranslateY, 0);
					}
				}
			}
		}
		super.models[2] = null;
		if (!this.shouldSuppressMovingAnimation && super.aClass4_Sub23_3 != null) {
			if (Shadow.anInt5323 >= super.aClass4_Sub23_3.anInt3202) {
				super.aClass4_Sub23_3 = null;
			} else if (super.aClass4_Sub23_3.anInt3191 <= Shadow.anInt5323) {
				@Pc(618) Model local618 = super.aClass4_Sub23_3.method2719(arg0, local7 | 0x7);
				if (local618 != null) {
					local618.drawScaledColored(super.aClass4_Sub23_3.anInt3198 - super.anInt6893, super.aClass4_Sub23_3.anInt3188 - super.anInt6889, -super.anInt6892 + super.aClass4_Sub23_3.anInt3203);
					if (local177 != 0) {
						local618.Q(-local177 & 0x3FFF);
					}
					super.models[2] = local618;
				}
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!lh", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!lh", name = "a", descriptor = "(ZI)Ljava/lang/String;")
	public String method3432() {
		return this.aString44;
	}

@OriginalMember(owner = "client!lh", name = "nd", descriptor = "I")
	public static int anInt4342;
@OriginalMember(owner = "client!lh", name = "od", descriptor = "I")
	public static int anInt4343;
@OriginalMember(owner = "client!lh", name = "rd", descriptor = "Lclient!hh;")
	public static Class14 aClass14_1;
@OriginalMember(owner = "client!lh", name = "vd", descriptor = "[I")
	public static int[] anIntArray284;
@OriginalMember(owner = "client!lh", name = "Ic", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_91 = new Class83("Self", "Mich", "Moi", "Eu");
@OriginalMember(owner = "client!lh", name = "id", descriptor = "[I")
	public static final int[] visibleBelowDrawheight = new int[] { 16776960, 16711680, 65280, 65535, 16711935, 16777215 };
@OriginalMember(owner = "client!lh", name = "sd", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_191 = new Class22(27, -2);
@OriginalMember(owner = "client!lh", name = "a", descriptor = "(Lclient!mu;II)V")
	public static void method3431(@OriginalArg(0) TileLighting arg0, @OriginalArg(1) int arg1) {
		if (Class16_Sub5.aBoolean596) {
			Class16_Sub5.aBoolean596 = false;
			arg1 = 0;
		}
		if (StillWaterEffect.aClass155_2 != null && StillWaterEffect.aClass155_2.method3695(arg0)) {
			return;
		}
		StillWaterEffect.aClass155_2 = arg0;
		NPCType.aLong219 = Class168.method3962();
		GlCubeTexture.anInt3253 = arg1;
		ParticleBinaryTree.anInt6542 = arg1;
		if (ParticleBinaryTree.anInt6542 != 0) {
			Class50_Sub1.aFloat114 = Class95_Sub3_Sub1.aFloat194;
			anInt4343 = Class7.anInt116;
			GameCanvas.aFloat190 = Static231.aFloat138;
			MECTypeList.aFloat169 = GlParticleRenderer.aFloat31;
			EmissiveTriangle.anInt715 = Js5MasterIndex.anInt6110;
			ModelNormalsGL.aFloat136 = Country.aFloat228;
			Static105.aFloat78 = TextureOpDiagonalWaves.aFloat131;
			Class4_Sub1_Sub9_Sub2.aFloat103 = Class208.aFloat187;
			OutputStream_Sub1.anInt2675 = Static398.anInt6606;
			GlGround.aClass99_6 = MemoryPool.aClass99_4;
			return;
		}
		RemoteFileWorker.method3519();
	}
}
