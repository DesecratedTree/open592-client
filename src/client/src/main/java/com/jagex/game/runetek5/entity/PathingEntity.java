package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static459;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.*;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
@OriginalClass("client!r")
public abstract class PathingEntity extends Class16_Sub1 {

	@OriginalMember(owner = "client!r", name = "A", descriptor = "I")
	public int anInt6014;

	@OriginalMember(owner = "client!r", name = "E", descriptor = "I")
	public int anInt6017;

	@OriginalMember(owner = "client!r", name = "F", descriptor = "I")
	public int anInt6018;

	@OriginalMember(owner = "client!r", name = "L", descriptor = "I")
	public int anInt6023;

	@OriginalMember(owner = "client!r", name = "P", descriptor = "I")
	public int anInt6026;

	@OriginalMember(owner = "client!r", name = "T", descriptor = "B")
	public byte aByte69;

	@OriginalMember(owner = "client!r", name = "db", descriptor = "I")
	public int anInt6037;

	@OriginalMember(owner = "client!r", name = "ib", descriptor = "I")
	protected int modelTranslateY;

	@OriginalMember(owner = "client!r", name = "jb", descriptor = "I")
	public int anInt6041;

	@OriginalMember(owner = "client!r", name = "kb", descriptor = "I")
	protected int modelRotateZ;

	@OriginalMember(owner = "client!r", name = "lb", descriptor = "I")
	public int lb;

	@OriginalMember(owner = "client!r", name = "mb", descriptor = "B")
	public byte aByte70;

	@OriginalMember(owner = "client!r", name = "nb", descriptor = "I")
	public int anInt6043;

	@OriginalMember(owner = "client!r", name = "ob", descriptor = "I")
	public int anInt6044;

	@OriginalMember(owner = "client!r", name = "vb", descriptor = "I")
	public int anInt6051;

	@OriginalMember(owner = "client!r", name = "wb", descriptor = "I")
	public int anInt6052;

	@OriginalMember(owner = "client!r", name = "xb", descriptor = "I")
	public int anInt6053;

	@OriginalMember(owner = "client!r", name = "yb", descriptor = "I")
	public int anInt6054;

	@OriginalMember(owner = "client!r", name = "Ab", descriptor = "I")
	public int anInt6056;

	@OriginalMember(owner = "client!r", name = "Cb", descriptor = "I")
	public int anInt6057;

	@OriginalMember(owner = "client!r", name = "Hb", descriptor = "I")
	public int drawCycle;

	@OriginalMember(owner = "client!r", name = "Jb", descriptor = "I")
	public int lastYawRotation;

	@OriginalMember(owner = "client!r", name = "Mb", descriptor = "I")
	public int anInt6066;

	@OriginalMember(owner = "client!r", name = "Qb", descriptor = "I")
	protected int modelRotateX;

	@OriginalMember(owner = "client!r", name = "Ub", descriptor = "I")
	public int anInt6074;

	@OriginalMember(owner = "client!r", name = "Wb", descriptor = "B")
	public byte aByte71;

	@OriginalMember(owner = "client!r", name = "lc", descriptor = "Lclient!ao;")
	protected ParticleSystem aClass3_Sub2_7;

	@OriginalMember(owner = "client!r", name = "pc", descriptor = "Lclient!ig;")
	public Class4_Sub23 aClass4_Sub23_3;

	@OriginalMember(owner = "client!r", name = "y", descriptor = "I")
	public int anInt6012 = 0;

	@OriginalMember(owner = "client!r", name = "B", descriptor = "I")
	public int anInt6015 = 0;

	@OriginalMember(owner = "client!r", name = "J", descriptor = "I")
	public int anInt6021 = -1;

	@OriginalMember(owner = "client!r", name = "x", descriptor = "Z")
	public boolean aBoolean559 = false;

	@OriginalMember(owner = "client!r", name = "W", descriptor = "I")
	public int anInt6031 = 0;

	@OriginalMember(owner = "client!r", name = "M", descriptor = "Ljava/lang/String;")
	public String aString60 = null;

	@OriginalMember(owner = "client!r", name = "H", descriptor = "I")
	public int anInt6020 = -1;

	@OriginalMember(owner = "client!r", name = "O", descriptor = "I")
	public int worldPriority = 0;

	@OriginalMember(owner = "client!r", name = "bb", descriptor = "I")
	public int anInt6035 = -1000;

	@OriginalMember(owner = "client!r", name = "sb", descriptor = "I")
	public int anInt6048 = -1;

	@OriginalMember(owner = "client!r", name = "ab", descriptor = "I")
	public int anInt6034 = -1;

	@OriginalMember(owner = "client!r", name = "V", descriptor = "I")
	protected int sphereRadius = -32768;

	@OriginalMember(owner = "client!r", name = "cb", descriptor = "I")
	public int anInt6036 = -1;

	@OriginalMember(owner = "client!r", name = "U", descriptor = "I")
	public int anInt6029 = -1;

	@OriginalMember(owner = "client!r", name = "Bb", descriptor = "[I")
	public final int[] anIntArray424 = new int[4];

	@OriginalMember(owner = "client!r", name = "pb", descriptor = "I")
	public int anInt6045 = -1;

	@OriginalMember(owner = "client!r", name = "Z", descriptor = "I")
	public int anInt6033 = 0;

	@OriginalMember(owner = "client!r", name = "K", descriptor = "I")
	public int anInt6022 = 0;

	@OriginalMember(owner = "client!r", name = "Q", descriptor = "I")
	public int anInt6027 = 0;

	@OriginalMember(owner = "client!r", name = "C", descriptor = "Z")
	public boolean transparent = false;

	@OriginalMember(owner = "client!r", name = "Gb", descriptor = "I")
	public int anInt6060 = 0;

	@OriginalMember(owner = "client!r", name = "qb", descriptor = "I")
	public int anInt6046 = 0;

	@OriginalMember(owner = "client!r", name = "D", descriptor = "I")
	public int anInt6016 = 0;

	@OriginalMember(owner = "client!r", name = "Y", descriptor = "Z")
	public boolean isHidden = true;

	@OriginalMember(owner = "client!r", name = "I", descriptor = "B")
	public byte aByte68 = 0;

	@OriginalMember(owner = "client!r", name = "Db", descriptor = "I")
	public int anInt6058 = -1;

	@OriginalMember(owner = "client!r", name = "Lb", descriptor = "I")
	public int anInt6065 = 0;

	@OriginalMember(owner = "client!r", name = "Pb", descriptor = "I")
	public int anInt6069 = -1;

	@OriginalMember(owner = "client!r", name = "S", descriptor = "I")
	public int turningTicks = 0;

	@OriginalMember(owner = "client!r", name = "Eb", descriptor = "Z")
	public boolean aBoolean562 = false;

	@OriginalMember(owner = "client!r", name = "Kb", descriptor = "I")
	public int anInt6064 = 0;

	@OriginalMember(owner = "client!r", name = "Zb", descriptor = "I")
	public int anInt6077 = 100;

	@OriginalMember(owner = "client!r", name = "Yb", descriptor = "I")
	public int anInt6076 = -1;

	@OriginalMember(owner = "client!r", name = "Ib", descriptor = "I")
	public int anInt6062 = 0;

	@OriginalMember(owner = "client!r", name = "ac", descriptor = "I")
	private int size = 1;

	@OriginalMember(owner = "client!r", name = "Rb", descriptor = "I")
	public int overheadStackCount = 0;

	@OriginalMember(owner = "client!r", name = "Sb", descriptor = "I")
	public int anInt6072 = -1000;

	@OriginalMember(owner = "client!r", name = "cc", descriptor = "I")
	public int anInt6080 = 256;

	@OriginalMember(owner = "client!r", name = "Tb", descriptor = "I")
	public int anInt6073 = 0;

	@OriginalMember(owner = "client!r", name = "fc", descriptor = "I")
	public int anInt6083 = -1;

	@OriginalMember(owner = "client!r", name = "Vb", descriptor = "[Lclient!nv;")
	public final PathingEntitySequence[] sequences = new PathingEntitySequence[12];

	@OriginalMember(owner = "client!r", name = "ub", descriptor = "I")
	public int anInt6050 = 0;

	@OriginalMember(owner = "client!r", name = "gc", descriptor = "[I")
	public final int[] anIntArray425 = new int[4];

	@OriginalMember(owner = "client!r", name = "Ob", descriptor = "I")
	public int anInt6068 = -1;

	@OriginalMember(owner = "client!r", name = "hc", descriptor = "I")
	public int anInt6084 = 0;

	@OriginalMember(owner = "client!r", name = "R", descriptor = "[I")
	public final int[] anIntArray422 = new int[4];

	@OriginalMember(owner = "client!r", name = "fb", descriptor = "Lclient!ch;")
	public final EasedAngle yaw = new EasedAngle();

	@OriginalMember(owner = "client!r", name = "ic", descriptor = "Lclient!ch;")
	private final EasedAngle roll = new EasedAngle();

	@OriginalMember(owner = "client!r", name = "jc", descriptor = "Lclient!ch;")
	private final EasedAngle pitch = new EasedAngle();

	@OriginalMember(owner = "client!r", name = "mc", descriptor = "[I")
	public final int[] movementBaseX = new int[10];

	@OriginalMember(owner = "client!r", name = "nc", descriptor = "[I")
	public final int[] movementBaseY = new int[10];

	@OriginalMember(owner = "client!r", name = "kc", descriptor = "I")
	public int anInt6085 = 0;

	@OriginalMember(owner = "client!r", name = "rc", descriptor = "[B")
	public final byte[] movementQueueSpeed = new byte[10];

	@OriginalMember(owner = "client!r", name = "oc", descriptor = "I")
	public int pathPointer = 0;

	@OriginalMember(owner = "client!r", name = "sc", descriptor = "I")
	public int anInt6087 = 0;

	@OriginalMember(owner = "client!r", name = "tc", descriptor = "I")
	public int anInt6088 = 0;

	@OriginalMember(owner = "client!r", name = "qc", descriptor = "[Lclient!t;")
	public final Model[] models = new Model[4];

	@OriginalMember(owner = "client!r", name = "uc", descriptor = "Z")
	protected boolean particlesInitialized = false;

	@OriginalMember(owner = "client!r", name = "<init>", descriptor = "()V")
	public PathingEntity() {
		super(0, 0, 0, 0, 0, 0, 0, 0, false, (byte) 0);
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(Z[Lclient!t;ILclient!ya;)V")
	protected final void method4741(@OriginalArg(0) boolean arg0, @OriginalArg(1) Model[] arg1, @OriginalArg(3) com.jagex.game.runetek5.graphics.Toolkit arg2) {
		if (!arg0) {
			@Pc(22) Model local22 = arg1[0];
			@Pc(26) Model local26 = arg1[1];
			@Pc(30) Model local30 = arg1[3];
			if (local22 != null) {
				@Pc(34) int local34 = 0;
				@Pc(36) int local36 = 0;
				@Pc(38) int local38 = 0;
				@Pc(40) int local40 = 0;
				@Pc(43) EmissiveTriangle[] local43 = local22.getEmissiveTriangles();
				@Pc(46) EffectiveVertex[] local46 = local22.getEffectiveVertices();
				if (local43 != null) {
					local36++;
					local34 = local43.length;
				}
				if (local46 != null) {
					local38 = local46.length;
					local40++;
				}
				@Pc(64) EmissiveTriangle[] local64 = null;
				@Pc(66) EffectiveVertex[] local66 = null;
				if (local26 != null) {
					local64 = local26.getEmissiveTriangles();
					local66 = local26.getEffectiveVertices();
				}
				if (local66 != null) {
					local38 += local66.length;
					local40++;
				}
				if (local64 != null) {
					local34 += local64.length;
					local36++;
				}
				@Pc(92) EmissiveTriangle[] local92 = null;
				@Pc(94) EffectiveVertex[] local94 = null;
				if (local30 != null) {
					local92 = local30.getEmissiveTriangles();
					local94 = local30.getEffectiveVertices();
				}
				if (local94 != null) {
					local38 += local94.length;
					local40++;
				}
				if (local92 != null) {
					local34 += local92.length;
					local36++;
				}
				if ((this.aClass3_Sub2_7 == null || this.aClass3_Sub2_7.removed) && (local36 > 0 || local40 > 0)) {
					this.aClass3_Sub2_7 = new ParticleSystem(Shadow.anInt5323);
				}
				if (this.aClass3_Sub2_7 != null) {
					@Pc(149) EmissiveTriangle[] local149;
					if (local36 != 1) {
						local149 = new EmissiveTriangle[local34];
						@Pc(161) int local161 = 0;
						if (local43 != null) {
							Static459.copy(local43, 0, local149, 0, local43.length);
							local161 = local43.length;
						}
						if (local64 != null) {
							Static459.copy(local64, 0, local149, local161, local64.length);
							local161 += local64.length;
						}
						if (local92 != null) {
							Static459.copy(local92, 0, local149, local161, local92.length);
						}
					} else if (local64 != null) {
						local149 = local64;
					} else if (local92 == null) {
						local149 = local43;
					} else {
						local149 = local92;
					}
					@Pc(211) EffectiveVertex[] local211;
					if (local40 != 1) {
						local211 = new EffectiveVertex[local38];
						@Pc(223) int local223 = 0;
						if (local46 != null) {
							Static459.copy(local46, 0, local211, 0, local46.length);
							local223 = local46.length;
						}
						if (local66 != null) {
							Static459.copy(local66, 0, local211, local223, local66.length);
							local223 += local66.length;
						}
						if (local94 != null) {
							Static459.copy(local94, 0, local211, local223, local94.length);
						}
					} else if (local66 != null) {
						local211 = local66;
					} else if (local94 == null) {
						local211 = local46;
					} else {
						local211 = local94;
					}
					this.aClass3_Sub2_7.update(arg2, (long) Shadow.anInt5323, local149, local211);
				}
				this.particlesInitialized = true;
			}
		} else if (this.aClass3_Sub2_7 != null) {
			this.aClass3_Sub2_7.setClock((long) Shadow.anInt5323);
		}
		if (this.aClass3_Sub2_7 != null) {
			this.aClass3_Sub2_7.updateBounds(super.aByte82, super.aShort94, super.aShort97, super.aShort96, super.aShort95);
		}
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(I)V")
	public final void stopMoving() {
		this.pathPointer = 0;
		this.anInt6087 = 0;
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(IZ)V")
	public final void method4743(@OriginalArg(0) int arg0) {
		this.yaw.method815();
		@Pc(16) int local16 = arg0 - this.yaw.rotation & 0x3FFF;
		if (local16 > 8192) {
			this.lastYawRotation = local16 + this.yaw.rotation - 16384;
		} else {
			this.lastYawRotation = local16 + this.yaw.rotation;
		}
	}

	@OriginalMember(owner = "client!r", name = "finalize", descriptor = "()V")
	@Override
	public final void finalize() {
		if (this.aClass3_Sub2_7 != null) {
			this.aClass3_Sub2_7.stopped();
		}
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(II)V")
	public final void method4745(@OriginalArg(1) int arg0) {
		this.lastYawRotation = arg0 & 0x3FFF;
		this.yaw.method812(this.lastYawRotation);
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(IIII)V")
	public final void method4746(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		for (@Pc(14) int local14 = 0; local14 < 4; local14++) {
			if (this.anIntArray424[local14] <= arg1) {
				this.anIntArray422[local14] = arg0;
				this.anIntArray425[local14] = arg2;
				this.anIntArray424[local14] = arg1 + 70;
				return;
			}
		}
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(BLclient!t;)V")
	protected final void method4747(@OriginalArg(1) Model arg0) {
		@Pc(17) int local17 = this.roll.rotation;
		@Pc(21) int local21 = this.pitch.rotation;
		if (local17 == 0 && local21 == 0) {
			return;
		}
		@Pc(30) int local30 = arg0.setOffsets() / 2;
		arg0.drawScaledColored(0, -local30, 0);
		arg0.R(local17 & 0x3FFF);
		arg0.Z(local21 & 0x3FFF);
		arg0.drawScaledColored(0, local30, 0);
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(IIIZI)V")
	public final void method4748(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) int arg3) {
		@Pc(14) int local14 = arg2 ? this.anInt6045 : this.anInt6029;
		@Pc(93) SeqType local93;
		if (arg3 != -1 && local14 != -1) {
			@Pc(32) SpotAnimationType local32;
			if (arg3 == local14) {
				local32 = Js5MasterIndex.aClass194_2.method4421(arg3);
				if (local32.replay && local32.sequenceId != -1) {
					@Pc(45) SeqType local45 = SceneLocEntity.aClass55_1.method1397(local32.sequenceId);
					@Pc(48) int local48 = local45.loopType;
					if (local48 == 0) {
						return;
					}
					if (local48 == 2) {
						if (arg2) {
							this.anInt6031 = 0;
							return;
						}
						this.anInt6050 = 0;
						return;
					}
				}
			} else {
				local32 = Js5MasterIndex.aClass194_2.method4421(arg3);
				@Pc(78) SpotAnimationType local78 = Js5MasterIndex.aClass194_2.method4421(local14);
				if (local32.sequenceId != -1 && local78.sequenceId != -1) {
					local93 = SceneLocEntity.aClass55_1.method1397(local32.sequenceId);
					@Pc(99) SeqType local99 = SceneLocEntity.aClass55_1.method1397(local78.sequenceId);
					if (local93.priority < local99.priority) {
						return;
					}
				}
			}
		}
		@Pc(107) int local107 = Shadow.anInt5323;
		@Pc(164) int local164;
		if (arg2) {
			this.anInt6062 = 0;
			this.anInt6022 = 0;
			this.anInt6056 = arg0;
			this.anInt6045 = arg3;
			this.anInt6053 = arg1 >> 16;
			this.anInt6069 = 1;
			this.anInt6066 = local107 + (arg1 & 0xFFFF);
			if (local107 < this.anInt6066) {
				this.anInt6022 = -1;
			}
			if (this.anInt6045 != -1 && local107 == this.anInt6066) {
				local164 = Js5MasterIndex.aClass194_2.method4421(this.anInt6045).sequenceId;
				if (local164 != -1) {
					local93 = SceneLocEntity.aClass55_1.method1397(local164);
					if (local93 != null && local93.durations != null) {
						Class4_Sub1_Sub2.method156(super.anInt6893, 0, super.aByte82, super.anInt6892, local93, Static1.aClass16_Sub1_Sub5_Sub1_1 == this);
						return;
					}
				}
			}
			return;
		}
		this.anInt6033 = 0;
		this.anInt6083 = 1;
		this.anInt6029 = arg3;
		this.anInt6017 = arg0;
		this.anInt6014 = arg1 >> 16;
		this.anInt6027 = 0;
		this.anInt6057 = (arg1 & 0xFFFF) + local107;
		if (this.anInt6057 > local107) {
			this.anInt6027 = -1;
		}
		if (this.anInt6029 != -1 && this.anInt6057 == local107) {
			local164 = Js5MasterIndex.aClass194_2.method4421(this.anInt6029).sequenceId;
			if (local164 != -1) {
				local93 = SceneLocEntity.aClass55_1.method1397(local164);
				if (local93 != null && local93.durations != null) {
					Class4_Sub1_Sub2.method156(super.anInt6893, 0, super.aByte82, super.anInt6892, local93, Static1.aClass16_Sub1_Sub5_Sub1_1 == this);
					return;
				}
			}
		}
		return;
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(B)I")
	protected abstract int getBasId();

	@OriginalMember(owner = "client!r", name = "f", descriptor = "(I)V")
	@Override
	public final void method5346() {
		@Pc(16) int local16 = (this.size - 1 << 6) + 60;
		super.aShort95 = (short) (local16 + super.anInt6892 >> 7);
		super.aShort97 = (short) (super.anInt6893 + local16 >> 7);
		super.aShort96 = (short) (super.anInt6892 - local16 >> 7);
		super.aShort94 = (short) (super.anInt6893 - local16 >> 7);
		if (this.transparent) {
			@Pc(62) int local62 = this.yaw.method811();
			if (local62 > 1024 && local62 < 7168) {
				super.aShort94--;
			}
			if (local62 > 13312 || local62 < 3072) {
				super.aShort96--;
			}
			if (local62 > 9216 && local62 < 15360) {
				super.aShort97++;
			}
			if (local62 > 5120 && local62 < 11264) {
				super.aShort95++;
			}
		}
		if (this.aClass4_Sub23_3 == null) {
			return;
		}
		if (super.aShort94 > this.aClass4_Sub23_3.anInt3200) {
			super.aShort94 = (short) this.aClass4_Sub23_3.anInt3200;
		}
		if (super.aShort95 < this.aClass4_Sub23_3.anInt3199) {
			super.aShort95 = (short) this.aClass4_Sub23_3.anInt3199;
		}
		if (super.aShort96 > this.aClass4_Sub23_3.anInt3192) {
			super.aShort96 = (short) this.aClass4_Sub23_3.anInt3192;
		}
		if (super.aShort97 < this.aClass4_Sub23_3.anInt3204) {
			super.aShort97 = (short) this.aClass4_Sub23_3.anInt3204;
			return;
		}
	}

	@OriginalMember(owner = "client!r", name = "b", descriptor = "(Z)I")
	public final int rotationTick() {
		@Pc(14) BasType local14 = this.method4757();
		@Pc(18) int local18 = this.yaw.rotation;
		@Pc(35) boolean local35;
		if (local14.anInt4676 == 0) {
			local35 = this.yaw.method816(this.anInt6080, this.anInt6080, this.lastYawRotation);
		} else {
			local35 = this.yaw.method816(local14.anInt4676, local14.anInt4696, this.lastYawRotation);
		}
		@Pc(54) int local54 = this.yaw.rotation - local18;
		if (local54 == 0) {
			this.turningTicks = 0;
			this.yaw.method812(this.lastYawRotation);
		} else {
			this.turningTicks++;
		}
		if (local35) {
			if (local14.anInt4700 != 0) {
				if (local54 > 0) {
					this.roll.method816(local14.anInt4700, local14.anInt4685, local14.anInt4674);
				} else {
					this.roll.method816(local14.anInt4700, local14.anInt4685, -local14.anInt4674);
				}
			}
			if (local14.anInt4683 != 0) {
				this.pitch.method816(local14.anInt4683, local14.anInt4690, local14.anInt4660);
			}
		} else {
			if (local14.anInt4700 == 0) {
				this.roll.method812(0);
			} else {
				this.roll.method816(local14.anInt4700, local14.anInt4685, 0);
			}
			if (local14.anInt4683 == 0) {
				this.pitch.method812(0);
			} else {
				this.pitch.method816(local14.anInt4683, local14.anInt4690, 0);
			}
		}
		return local54;
	}

	@OriginalMember(owner = "client!r", name = "e", descriptor = "(B)I")
	public int getSize() {
		return this.size;
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(IIIIII)V")
	protected final void hillChange(@OriginalArg(0) int rotation, @OriginalArg(1) int sizeX, @OriginalArg(2) int maxRotateZ, @OriginalArg(3) int sizeZ, @OriginalArg(4) int arg4) {
		@Pc(14) int local14 = super.aShort97 + super.aShort94 >> 1;
		@Pc(23) int local23 = super.aShort95 + super.aShort96 >> 1;
		@Pc(27) int local27 = Class50_Sub1.anIntArray225[sizeZ];
		@Pc(31) int local31 = Class50_Sub1.anIntArray224[sizeZ];
		@Pc(36) int local36 = -rotation / 2;
		@Pc(41) int local41 = -arg4 / 2;
		@Pc(51) int local51 = local36 * local31 + local41 * local27 >> 15;
		@Pc(61) int local61 = local31 * local41 - local36 * local27 >> 15;
		@Pc(76) int local76 = PointEntity.method3300(local14, local51 + super.anInt6893, local23, super.anInt6892 + local61, super.aByte82);
		@Pc(80) int local80 = rotation / 2;
		@Pc(85) int local85 = -arg4 / 2;
		@Pc(95) int local95 = local85 * local27 + local80 * local31 >> 15;
		@Pc(105) int local105 = local31 * local85 - local80 * local27 >> 15;
		@Pc(122) int local122 = PointEntity.method3300(local14, super.anInt6893 + local95, local23, super.anInt6892 + local105, super.aByte82);
		@Pc(134) int local134 = -rotation / 2;
		@Pc(138) int local138 = arg4 / 2;
		@Pc(148) int local148 = local27 * local138 + local31 * local134 >> 15;
		@Pc(159) int local159 = local31 * local138 - local27 * local134 >> 15;
		@Pc(176) int local176 = PointEntity.method3300(local14, super.anInt6893 + local148, local23, super.anInt6892 + local159, super.aByte82);
		@Pc(180) int local180 = rotation / 2;
		@Pc(184) int local184 = arg4 / 2;
		@Pc(194) int local194 = local31 * local180 + local184 * local27 >> 15;
		@Pc(205) int local205 = local31 * local184 - local180 * local27 >> 15;
		@Pc(221) int local221 = PointEntity.method3300(local14, super.anInt6893 + local194, local23, local205 + super.anInt6892, super.aByte82);
		@Pc(232) int local232 = local76 >= local122 ? local122 : local76;
		@Pc(239) int local239 = local221 <= local176 ? local221 : local176;
		@Pc(250) int local250 = local122 < local221 ? local122 : local221;
		@Pc(257) int local257 = local76 >= local176 ? local176 : local76;
		this.modelRotateX = (int) (Math.atan2((double) (local232 - local239), (double) arg4) * 2607.5945876176133D) & 0x3FFF;
		this.modelRotateZ = (int) (Math.atan2((double) (local257 - local250), (double) rotation) * 2607.5945876176133D) & 0x3FFF;
		@Pc(299) int local299;
		if (this.modelRotateX != 0 && sizeX != 0) {
			local299 = 16384 - sizeX;
			if (this.modelRotateX <= 8192) {
				if (this.modelRotateX > sizeX) {
					this.modelRotateX = sizeX;
				}
			} else if (this.modelRotateX < local299) {
				this.modelRotateX = local299;
			}
		}
		this.modelTranslateY = local221 + local76;
		if (this.modelRotateZ != 0 && maxRotateZ != 0) {
			local299 = 16384 - maxRotateZ;
			if (this.modelRotateZ <= 8192) {
				if (maxRotateZ < this.modelRotateZ) {
					this.modelRotateZ = maxRotateZ;
				}
			} else if (this.modelRotateZ < local299) {
				this.modelRotateZ = local299;
			}
		}
		if (local122 + local176 < this.modelTranslateY) {
			this.modelTranslateY = local176 + local122;
		}
		this.modelTranslateY = (this.modelTranslateY >> 1) - super.anInt6889;
	}

	@OriginalMember(owner = "client!r", name = "a", descriptor = "(IB)V")
	public final void setSize(@OriginalArg(0) int arg0) {
		this.size = arg0;
	}

	@OriginalMember(owner = "client!r", name = "b", descriptor = "(I)I")
	public int getOverheadHeight() {
		@Pc(9) BasType local9 = this.method4757();
		if (local9.anInt4684 == -1) {
			return this.sphereRadius == -32768 ? 200 : -this.sphereRadius;
		} else {
			return local9.anInt4684;
		}
	}

	@OriginalMember(owner = "client!r", name = "f", descriptor = "(B)I")
	public abstract int getOverheadSprite();

	@OriginalMember(owner = "client!r", name = "c", descriptor = "(I)Lclient!mp;")
	public final BasType method4757() {
		@Pc(7) int local7 = this.getBasId();
		return local7 == -1 ? Js5WorkerThread.aClass151_1 : Class133.aClass249_1.method5325(local7);
	}

@OriginalMember(owner = "client!r", name = "eb", descriptor = "[I")
	public static int[] anIntArray423;
@OriginalMember(owner = "client!r", name = "a", descriptor = "(Z)V")
	public static void method4744() {
		if (VertexDeclarationComponent.anInt6667 == 10) {
			Huffman.method2932(28);
		}
		if (VertexDeclarationComponent.anInt6667 == 30) {
			Huffman.method2932(25);
		}
	}
@OriginalMember(owner = "client!r", name = "b", descriptor = "(II)I")
	public static int method4753(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return Class145_Sub2.anIntArrayArray64 == null ? 0 : Class145_Sub2.anIntArrayArray64[arg0][arg1] & 0xFFFFFF;
	}
}
