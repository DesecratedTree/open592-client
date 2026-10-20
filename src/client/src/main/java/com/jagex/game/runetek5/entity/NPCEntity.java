package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.ra;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static57;
import com.jagex.game.runetek5.entity.*;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.cache.node.MemoryPool;
@OriginalClass("client!rs")
public final class NPCEntity extends PathingEntity {

	@OriginalMember(owner = "client!rs", name = "Pc", descriptor = "Lclient!wh;")
	public NPCType npcType;

	/** Set by the server when this NPC is a player's spawned pet (right-click Pick-up). */
	public boolean isPet;

	@OriginalMember(owner = "client!rs", name = "vc", descriptor = "I")
	public int anInt6089 = -1;

	@OriginalMember(owner = "client!rs", name = "Cc", descriptor = "I")
	public int anInt6095 = -1;

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(B)I")
	@Override
	protected int getBasId() {
		if (this.npcType.anIntArray544 != null) {
			@Pc(20) NPCType local20 = this.npcType.method5574(Class4_Sub6_Sub24.aClass114_1);
			if (local20 != null && local20.anInt7241 != -1) {
				return local20.anInt7241;
			}
		}
		return this.npcType.anInt7241;
	}

	@OriginalMember(owner = "client!rs", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(IIIIZI)V")
	public void clearPath(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int z, @OriginalArg(4) boolean arg3, @OriginalArg(5) int arg4) {
		super.aByte82 = (byte) arg0;
		if (super.anInt6021 != -1 && SceneLocEntity.aClass55_1.method1397(super.anInt6021).moveType == 1) {
			super.anInt6021 = -1;
		}
		@Pc(37) SpotAnimationType local37;
		if (super.anInt6029 != -1) {
			local37 = Js5MasterIndex.aClass194_2.method4421(super.anInt6029);
			if (local37.replay && local37.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local37.sequenceId).moveType == 1) {
				super.anInt6029 = -1;
			}
		}
		if (super.anInt6045 != -1) {
			local37 = Js5MasterIndex.aClass194_2.method4421(super.anInt6045);
			if (local37.replay && local37.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local37.sequenceId).moveType == 1) {
				super.anInt6045 = -1;
			}
		}
		if (!arg3) {
			@Pc(96) int local96 = arg1 - super.movementBaseX[0];
			@Pc(104) int local104 = arg4 - super.movementBaseY[0];
			if (local96 >= -8 && local96 <= 8 && local104 >= -8 && local104 <= 8) {
				if (super.pathPointer < 9) {
					super.pathPointer++;
				}
				for (@Pc(139) int local139 = super.pathPointer; local139 > 0; local139--) {
					super.movementBaseX[local139] = super.movementBaseX[local139 - 1];
					super.movementBaseY[local139] = super.movementBaseY[local139 - 1];
					super.movementQueueSpeed[local139] = super.movementQueueSpeed[local139 - 1];
				}
				super.movementBaseX[0] = arg1;
				super.movementBaseY[0] = arg4;
				super.movementQueueSpeed[0] = 1;
				return;
			}
		}
		super.movementBaseX[0] = arg1;
		super.pathPointer = 0;
		super.anInt6085 = 0;
		super.anInt6087 = 0;
		super.movementBaseY[0] = arg4;
		super.anInt6892 = (super.movementBaseY[0] << 7) + (z << 6);
		super.anInt6893 = (z << 6) + (super.movementBaseX[0] << 7);
		if (super.aClass3_Sub2_7 != null) {
			super.aClass3_Sub2_7.restart();
		}
	}

	@OriginalMember(owner = "client!rs", name = "b", descriptor = "(I)I")
	@Override
	public int getOverheadHeight() {
		if (this.npcType.anIntArray544 != null) {
			@Pc(17) NPCType local17 = this.npcType.method5574(Class4_Sub6_Sub24.aClass114_1);
			if (local17 != null && local17.iconHeight != -1) {
				return local17.iconHeight;
			}
		}
		return this.npcType.iconHeight == -1 ? super.getOverheadHeight() : this.npcType.iconHeight;
	}

	@OriginalMember(owner = "client!rs", name = "e", descriptor = "(I)I")
	@Override
	public int method5342() {
		return super.sphereRadius;
	}

	@OriginalMember(owner = "client!rs", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!rs", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.npcType == null || !this.method4764(arg0, 2048)) {
			return null;
		}
		@Pc(22) Ground local22 = arg0.method4289();
		@Pc(27) int local27 = super.yaw.method811();
		local22.ra(local27);
		local22.o(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(42) BasType local42 = this.method4757();
		@Pc(55) NPCType local55 = this.npcType.anIntArray544 == null ? this.npcType : this.npcType.method5574(Class4_Sub6_Sub24.aClass114_1);
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean302 && local55.shadowed && local42.aBoolean428) {
			@Pc(80) SeqType local80 = super.anInt6021 != -1 && super.anInt6046 == 0 ? SceneLocEntity.aClass55_1.method1397(super.anInt6021) : null;
			@Pc(97) SeqType local97 = super.anInt6076 == -1 || super.aBoolean562 && local80 != null ? null : SceneLocEntity.aClass55_1.method1397(super.anInt6076);
			@Pc(149) Model local149 = ClientScript.method48(super.modelRotateZ, super.modelRotateX, super.models[0], this.npcType.aShort102 & 0xFFFF, super.modelTranslateY, this.npcType.aShort101 & 0xFFFF, arg0, this.npcType.aByte98 & 0xFF, this.npcType.anInt7201, local27, local97 == null ? super.anInt6060 : super.anInt6016, super.transparent, local97 == null ? local80 : local97, this.npcType.aByte100 & 0xFF);
			if (local149 != null) {
				@Pc(154) float local154 = arg0.W();
				@Pc(157) float local157 = arg0.xa();
				arg0.method4309(false);
				arg0.g(local154, local157 - 150.0F);
				local149.render(local22, null, 0);
				arg0.g(local154, local157);
				arg0.method4309(true);
			}
		}
		@Pc(180) Class3_Sub3 local180 = null;
		if (this.isVisible()) {
			local180 = Class28.method672(super.models.length);
		}
		if (super.aClass3_Sub2_7 == null) {
			arg0.method4307(super.models, local22, local180 == null ? null : local180.aClass3_Sub5Array1, 0);
		} else {
			@Pc(211) ParticleList local211 = super.aClass3_Sub2_7.getList();
			arg0.method4299(super.models, local211, local22, local180 == null ? null : local180.aClass3_Sub5Array1, 0);
		}
		this.method4741(false, super.models, arg0);
		if (super.models[2] != null) {
			if (local27 != 0) {
				super.models[2].Q(local27);
			}
			super.models[2].drawScaledColored(super.anInt6893 - super.aClass4_Sub23_3.anInt3198, -super.aClass4_Sub23_3.anInt3188 + super.anInt6889, super.anInt6892 - super.aClass4_Sub23_3.anInt3203);
		}
		super.drawCycle = Class79_Sub1.anInt6264;
		super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		return local180;
	}

	@OriginalMember(owner = "client!rs", name = "j", descriptor = "(I)Z")
	public boolean hasType() {
		return this.npcType != null;
	}

	@OriginalMember(owner = "client!rs", name = "f", descriptor = "(B)I")
	@Override
	public int getOverheadSprite() {
		if (this.npcType.anIntArray544 != null) {
			@Pc(18) NPCType local18 = this.npcType.method5574(Class4_Sub6_Sub24.aClass114_1);
			if (local18 != null && local18.overheadSprite != -1) {
				return local18.overheadSprite;
			}
		}
		return this.npcType.overheadSprite;
	}

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		if (this.npcType == null || !this.method4764(arg1, 131072)) {
			return false;
		}
		@Pc(19) Ground local19 = arg1.method4289();
		@Pc(24) int local24 = super.yaw.method811();
		local19.ra(local24);
		local19.o(super.anInt6893, super.anInt6889, super.anInt6892);
		@Pc(37) boolean local37 = false;
		for (@Pc(39) int local39 = 0; local39 < super.models.length; local39++) {
			if (super.models[local39] != null && super.models[local39].picked(arg2, arg0, local19, this.npcType.anInt7201 == 1)) {
				local37 = true;
				break;
			}
		}
		super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		return local37;
	}

	@OriginalMember(owner = "client!rs", name = "b", descriptor = "(III)V")
	public void move(@OriginalArg(1) int arg0, @OriginalArg(2) int movementType) {
		@Pc(18) int local18 = super.movementBaseX[0];
		@Pc(23) int local23 = super.movementBaseY[0];
		if (arg0 == 0) {
			local23++;
		}
		if (arg0 == 1) {
			local18++;
			local23++;
		}
		if (arg0 == 2) {
			local18++;
		}
		if (arg0 == 3) {
			local23--;
			local18++;
		}
		if (arg0 == 4) {
			local23--;
		}
		if (arg0 == 5) {
			local23--;
			local18--;
		}
		if (arg0 == 6) {
			local18--;
		}
		if (super.anInt6021 != -1 && SceneLocEntity.aClass55_1.method1397(super.anInt6021).moveType == 1) {
			super.anInt6021 = -1;
		}
		if (arg0 == 7) {
			local18--;
			local23++;
		}
		@Pc(91) SpotAnimationType local91;
		if (super.anInt6029 != -1) {
			local91 = Js5MasterIndex.aClass194_2.method4421(super.anInt6029);
			if (local91.replay && local91.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local91.sequenceId).moveType == 1) {
				super.anInt6029 = -1;
			}
		}
		if (super.anInt6045 != -1) {
			local91 = Js5MasterIndex.aClass194_2.method4421(super.anInt6045);
			if (local91.replay && local91.sequenceId != -1 && SceneLocEntity.aClass55_1.method1397(local91.sequenceId).moveType == 1) {
				super.anInt6045 = -1;
			}
		}
		if (super.pathPointer < 9) {
			super.pathPointer++;
		}
		for (@Pc(155) int local155 = super.pathPointer; local155 > 0; local155--) {
			super.movementBaseX[local155] = super.movementBaseX[local155 - 1];
			super.movementBaseY[local155] = super.movementBaseY[local155 - 1];
			super.movementQueueSpeed[local155] = super.movementQueueSpeed[local155 - 1];
		}
		super.movementBaseX[0] = local18;
		super.movementBaseY[0] = local23;
		super.movementQueueSpeed[0] = (byte) movementType;
	}

	@OriginalMember(owner = "client!rs", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.npcType != null && (super.particlesInitialized || this.method4764(arg0, 0))) {
			this.method4741(super.particlesInitialized, super.models, arg0);
			super.models[0] = super.models[1] = super.models[2] = super.models[3] = null;
		}
	}

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(ILclient!ya;I)Z")
	private boolean method4764(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(2) int arg1) {
		@Pc(7) int local7 = arg1;
		@Pc(11) BasType local11 = this.method4757();
		@Pc(26) SeqType local26 = super.anInt6021 != -1 && super.anInt6046 == 0 ? SceneLocEntity.aClass55_1.method1397(super.anInt6021) : null;
		@Pc(43) SeqType local43 = super.anInt6076 == -1 || super.aBoolean562 && local26 != null ? null : SceneLocEntity.aClass55_1.method1397(super.anInt6076);
		@Pc(46) int local46 = local11.anInt4662;
		@Pc(49) int local49 = local11.anInt4670;
		if (local46 != 0 || local49 != 0 || local11.anInt4674 != 0 || local11.anInt4660 != 0) {
			arg1 |= 0x7;
		}
		@Pc(98) boolean local98 = super.aByte68 != 0 && Shadow.anInt5323 >= super.anInt6020 && Shadow.anInt5323 < super.anInt6068;
		if (local98) {
			arg1 |= 0x80000;
		}
		@Pc(135) Model local135 = super.models[0] = this.npcType.method5575(super.anInt6065, local26, Class4_Sub6_Sub24.aClass114_1, super.anInt6058, arg1, super.anInt6073, super.anInt6016, arg0, super.anInt6060, super.anInt6036, super.sequences, local43, SceneLocEntity.aClass55_1, Class133.aClass249_1);
		if (local135 == null) {
			return false;
		}
		super.sphereRadius = local135.setOffsets();
		this.method4747(local135);
		@Pc(152) int local152 = super.yaw.method811();
		if (local46 == 0 && local49 == 0) {
			this.hillChange(this.getSize() << 7, 0, 0, local152, this.getSize() << 7);
		} else {
			this.hillChange(local46, local11.anInt4671, local11.anInt4693, local152, local49);
			if (super.modelRotateX != 0) {
				super.models[0].Z(super.modelRotateX);
			}
			if (super.modelRotateZ != 0) {
				super.models[0].R(super.modelRotateZ);
			}
			if (super.modelTranslateY != 0) {
				super.models[0].drawScaledColored(0, super.modelTranslateY, 0);
			}
		}
		if (local98) {
			local135.adjustColours(super.aByte71, super.aByte69, super.aByte70, super.aByte68 & 0xFF);
		}
		@Pc(267) SpotAnimationType local267;
		@Pc(282) boolean local282;
		@Pc(284) int local284;
		@Pc(324) Model local324;
		if (super.anInt6029 == -1 || super.anInt6027 == -1) {
			super.models[1] = null;
		} else {
			local267 = Js5MasterIndex.aClass194_2.method4421(super.anInt6029);
			local282 = local267.contourType == 3 && (local46 != 0 || local49 != 0);
			local284 = local7;
			if (local282) {
				local284 = local7 | 0x7;
			} else {
				if (super.anInt6017 != 0) {
					local284 = local7 | 0x5;
				}
				if (super.anInt6014 != 0) {
					local284 |= 0x2;
				}
			}
			local324 = super.models[1] = local267.method3830(super.anInt6027, super.anInt6033, SceneLocEntity.aClass55_1, super.anInt6083, local284, arg0);
			if (local324 != null) {
				if (super.anInt6014 != 0) {
					local324.drawScaledColored(0, -super.anInt6014 << 0, 0);
				}
				if (super.anInt6017 != 0) {
					local324.Q(super.anInt6017 * 2048);
				}
				if (local282) {
					if (super.modelRotateX != 0) {
						local324.Z(super.modelRotateX);
					}
					if (super.modelRotateZ != 0) {
						local324.R(super.modelRotateZ);
					}
					if (super.modelTranslateY != 0) {
						local324.drawScaledColored(0, super.modelTranslateY, 0);
					}
				}
			}
		}
		if (super.anInt6045 == -1 || super.anInt6022 == -1) {
			super.models[3] = null;
		} else {
			local267 = Js5MasterIndex.aClass194_2.method4421(super.anInt6045);
			local282 = local267.contourType == 3 && (local46 != 0 || local49 != 0);
			local284 = local7;
			if (local282) {
				local284 = local7 | 0x7;
			} else {
				if (super.anInt6056 != 0) {
					local284 = local7 | 0x5;
				}
				if (super.anInt6053 != 0) {
					local284 |= 0x2;
				}
			}
			local324 = super.models[3] = local267.method3837(arg0, super.anInt6062, SceneLocEntity.aClass55_1, super.anInt6022, local284, super.anInt6069);
			if (local324 != null) {
				if (super.anInt6053 != 0) {
					local324.drawScaledColored(0, -super.anInt6053 << 0, 0);
				}
				if (super.anInt6056 != 0) {
					local324.Q(super.anInt6056 * 2048);
				}
				if (local282) {
					if (super.modelRotateX != 0) {
						local324.Z(super.modelRotateX);
					}
					if (super.modelRotateZ != 0) {
						local324.R(super.modelRotateZ);
					}
					if (super.modelTranslateY != 0) {
						local324.drawScaledColored(0, super.modelTranslateY, 0);
					}
				}
			}
		}
		super.models[2] = null;
		if (super.aClass4_Sub23_3 != null) {
			if (super.aClass4_Sub23_3.anInt3202 <= Shadow.anInt5323) {
				super.aClass4_Sub23_3 = null;
			} else if (super.aClass4_Sub23_3.anInt3191 <= Shadow.anInt5323) {
				@Pc(564) Model local564 = super.aClass4_Sub23_3.method2719(arg0, local7 | 0x7);
				if (local564 != null) {
					local564.drawScaledColored(-super.anInt6893 + super.aClass4_Sub23_3.anInt3198, super.aClass4_Sub23_3.anInt3188 + -super.anInt6889, super.aClass4_Sub23_3.anInt3203 - super.anInt6892);
					if (local152 != 0) {
						local564.Q(local152);
					}
					super.models[2] = local564;
				}
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!rs", name = "k", descriptor = "(I)Z")
	private boolean isVisible() {
		return this.npcType.aBoolean682;
	}

	@OriginalMember(owner = "client!rs", name = "a", descriptor = "(BLclient!wh;)V")
	public void method4766(@OriginalArg(1) NPCType arg0) {
		this.npcType = arg0;
		if (super.aClass3_Sub2_7 != null) {
			super.aClass3_Sub2_7.restart();
		}
	}

@OriginalMember(owner = "client!rs", name = "zc", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_81 = new Class215(58, 6);
@OriginalMember(owner = "client!rs", name = "h", descriptor = "(I)V")
	public static void method4759() {
		Class14.method210();
		GlGround.aClass99_6 = null;
		Static1.aClass155ArrayArray1 = null;
		RequestManager.aClass99_5 = null;
		Static57.aClass19_7 = null;
		MemoryPool.aClass99_4 = null;
	}
}
