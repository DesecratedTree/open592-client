package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub1;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.mec.MECTypeList;
@OriginalClass("client!lb")
public final class Particle extends PointEntity {

	@OriginalMember(owner = "client!lb", name = "F", descriptor = "I")
	private int colourFraction;

	@OriginalMember(owner = "client!lb", name = "K", descriptor = "S")
	private short producerParentSize;

	@OriginalMember(owner = "client!lb", name = "M", descriptor = "Lclient!mo;")
	private ParticleEmitter producer;

	@OriginalMember(owner = "client!lb", name = "N", descriptor = "S")
	private short remainingLifetime;

	@OriginalMember(owner = "client!lb", name = "J", descriptor = "S")
	private short lifetime;

	@OriginalMember(owner = "client!lb", name = "L", descriptor = "S")
	private short directionX;

	@OriginalMember(owner = "client!lb", name = "H", descriptor = "S")
	private short directionY;

	@OriginalMember(owner = "client!lb", name = "I", descriptor = "S")
	private short directionZ;

	@OriginalMember(owner = "client!lb", name = "G", descriptor = "I")
	private int speed;

	@OriginalMember(owner = "client!lb", name = "<init>", descriptor = "(Lclient!mo;IIIIIIIIIIIZZ)V")
	public Particle(@OriginalArg(0) ParticleEmitter particleEmitter, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) int directionX, @OriginalArg(5) int directionY, @OriginalArg(6) int directionZ, @OriginalArg(7) int speed, @OriginalArg(8) int lifetime, @OriginalArg(9) int colorArgb, @OriginalArg(10) int size, @OriginalArg(11) int materialId, @OriginalArg(12) boolean disableHdLighting, @OriginalArg(13) boolean lightIntensityAdjustment) {
		this.producer = particleEmitter;
		super.x = x << Class111.anInt3366;
		super.y = y << Class111.anInt3366;
		super.z = z << Class111.anInt3366;
		super.colorArgb = colorArgb;
		this.lifetime = this.remainingLifetime = (short) lifetime;
		super.size = size;
		super.materialId = materialId;
		super.lightIntensityAdjustment = lightIntensityAdjustment;
		this.directionX = (short) directionX;
		this.directionY = (short) directionY;
		this.directionZ = (short) directionZ;
		this.speed = speed;
		super.priority = this.producer.model.priority;
		this.link();
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "()V")
	private void remove() {
		this.producer.system.aClass3_Sub1_Sub1_Sub1Array1[this.producerParentSize] = null;
		Class4_Sub45.aClass3_Sub1_Sub1_Sub1Array2[MECTypeList.anInt5385] = this;
		MECTypeList.anInt5385 = MECTypeList.anInt5385 + 1 & 0x3FF;
		this.method5676();
		this.unlink2();
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "(J)V")
	public void method3304(@OriginalArg(0) long arg0) {
		@Pc(6) int local6 = super.x >> Static231.anInt4434 + 12;
		@Pc(13) int local13 = super.z >> Static231.anInt4434 + 12;
		@Pc(18) int local18 = super.y >> Class111.anInt3366;
		if (local18 > 0 || local18 < -262144 || local6 < 0 || local6 >= GpuFaceNormal.menuOpenWidth || local13 < 0 || local13 >= GlModel.anInt3361) {
			this.remove();
			return;
		}
		@Pc(40) ParticleSystem local40 = this.producer.system;
		@Pc(44) ParticleProducerType local44 = this.producer.type;
		@Pc(46) Class65[] local46 = Class16_Sub1_Sub6.aClass65Array4;
		@Pc(54) int local54 = local46[local40.anInt217].l(local6, local13);
		@Pc(70) int local70;
		if (local40.anInt217 < RemoteFileWorker.anInt4487 - 1) {
			local70 = local46[local40.anInt217 + 1].l(local6, local13);
		} else {
			local70 = local54 - (0x8 << Static231.anInt4434);
		}
		if (local44.adjustsToScene) {
			if (local44.anInt3790 == -1 && local18 > local54) {
				this.remove();
				return;
			}
			if (local44.anInt3790 >= 0 && local18 > local46[local44.anInt3790].l(local6, local13)) {
				this.remove();
				return;
			}
			if (local44.anInt3808 == -1 && local18 < local70) {
				this.remove();
				return;
			}
			if (local44.anInt3808 >= 0 && local18 < local46[local44.anInt3808 + 1].l(local6, local13)) {
				this.remove();
				return;
			}
		}
		if (local6 >= local40.level && local6 <= local40.anInt218 && local13 >= local40.anInt219 && local13 <= local40.anInt214 && local18 <= local54 && local18 >= local70) {
			local40.list.particles.method5126(this);
			return;
		}
		@Pc(158) int local158;
		for (local158 = RemoteFileWorker.anInt4487 - 1; local158 > 0 && local18 > local46[local158].l(local6, local13); local158--) {
		}
		if (local158 == 0 && local18 > local46[0].l(local6, local13)) {
			this.remove();
		} else if (local158 == RemoteFileWorker.anInt4487 - 1 && local46[local158].l(local6, local13) - local18 > 0x8 << Static231.anInt4434) {
			this.remove();
		} else {
			@Pc(211) Class164 local211 = Class70.aClass164ArrayArrayArray5[local158][local6][local13];
			if (local211 == null) {
				if (local158 == 0) {
					local211 = Class70.aClass164ArrayArrayArray5[0][local6][local13] = new Class164(0, local6, local13);
				}
				@Pc(243) boolean local243 = Class70.aClass164ArrayArrayArray5[0][local6][local13].aClass164_1 != null;
				if (local158 == 3 && local243) {
					this.remove();
					return;
				}
				for (@Pc(253) int local253 = 1; local253 <= local158; local253++) {
					if (Class70.aClass164ArrayArrayArray5[local253][local6][local13] == null) {
						local211 = Class70.aClass164ArrayArrayArray5[local253][local6][local13] = new Class164(local253, local6, local13);
						if (local243) {
							local211.aByte53++;
						}
					}
				}
			}
			if (local211.aClass146_2 == null) {
				local211.aClass146_2 = new ParticleList();
				local211.aByte48 = (byte) (arg0 & 0xFFL);
			} else if (local211.aByte48 != (byte) (arg0 & 0xFFL)) {
				local211.aClass146_2.particles.method5122();
				local211.aClass146_2.aBoolean417 = false;
				local211.aByte48 = (byte) (arg0 & 0xFFL);
			}
			local211.aClass146_2.particles.method5126(this);
		}
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "(Lclient!mo;IIIIIIIIIIIZZ)V")
	public void reset(@OriginalArg(0) ParticleEmitter particleEmitter, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) int directionX, @OriginalArg(5) int directionY, @OriginalArg(6) int directionZ, @OriginalArg(7) int speed, @OriginalArg(8) int lifetime, @OriginalArg(9) int colour, @OriginalArg(10) int size, @OriginalArg(11) int texture, @OriginalArg(12) boolean disableHdLighting, @OriginalArg(13) boolean preserveAmbient) {
		this.producer = particleEmitter;
		super.x = x << Class111.anInt3366;
		super.y = y << Class111.anInt3366;
		super.z = z << Class111.anInt3366;
		super.colorArgb = colour;
		this.lifetime = this.remainingLifetime = (short) lifetime;
		super.size = size;
		super.materialId = texture;
		super.lightIntensityAdjustment = preserveAmbient;
		this.directionX = (short) directionX;
		this.directionY = (short) directionY;
		this.directionZ = (short) directionZ;
		this.speed = speed;
		super.priority = this.producer.model.priority;
		this.link();
	}

	@OriginalMember(owner = "client!lb", name = "b", descriptor = "()V")
	private void link() {
		@Pc(4) int local4 = this.producer.system.nextParticleSlot;
		if (this.producer.system.aClass3_Sub1_Sub1_Sub1Array1[local4] != null) {
			this.producer.system.aClass3_Sub1_Sub1_Sub1Array1[local4].remove();
		}
		this.producer.system.aClass3_Sub1_Sub1_Sub1Array1[local4] = this;
		this.producerParentSize = (short) this.producer.system.nextParticleSlot;
		this.producer.system.nextParticleSlot = local4 + 1 & 0x1FFF;
		this.producer.aClass84_6.offer(this);
	}

	@OriginalMember(owner = "client!lb", name = "a", descriptor = "(JI)V")
	public void tick(@OriginalArg(0) long time, @OriginalArg(1) int elapsedTime) {
		this.remainingLifetime = (short) (this.remainingLifetime - elapsedTime);
		if (this.remainingLifetime <= 0) {
			this.remove();
			return;
		}
		@Pc(17) int local17 = super.x >> Class111.anInt3366;
		@Pc(22) int local22 = super.y >> Class111.anInt3366;
		@Pc(27) int local27 = super.z >> Class111.anInt3366;
		@Pc(31) ParticleSystem local31 = this.producer.system;
		@Pc(35) ParticleProducerType local35 = this.producer.type;
		@Pc(65) int local65;
		@Pc(82) int local82;
		@Pc(99) int local99;
		if (local35.faceColorRgb != 0) {
			if (this.lifetime - this.remainingLifetime <= local35.alphaFadeStart) {
				local65 = (super.colorArgb >> 8 & 0xFF00) + (this.colourFraction >> 16 & 0xFF) + local35.greenFadeStep * elapsedTime;
				local82 = (super.colorArgb & 0xFF00) + (this.colourFraction >> 8 & 0xFF) + local35.alphaFadeStep * elapsedTime;
				local99 = ((super.colorArgb & 0xFF) << 8) + (this.colourFraction & 0xFF) + local35.anInt3778 * elapsedTime;
				if (local65 < 0) {
					local65 = 0;
				} else if (local65 > 65535) {
					local65 = 65535;
				}
				if (local82 < 0) {
					local82 = 0;
				} else if (local82 > 65535) {
					local82 = 65535;
				}
				if (local99 < 0) {
					local99 = 0;
				} else if (local99 > 65535) {
					local99 = 65535;
				}
				super.colorArgb &= 0xFF000000;
				super.colorArgb |= ((local65 & 0xFF00) << 8) + (local82 & 0xFF00) + (local99 >> 8 & 0xFF);
				this.colourFraction &= 0xFF000000;
				this.colourFraction |= ((local65 & 0xFF) << 16) + ((local82 & 0xFF) << 8) + (local99 & 0xFF);
			}
			if (this.lifetime - this.remainingLifetime <= local35.anInt3779) {
				local65 = (super.colorArgb >> 16 & 0xFF00) + (this.colourFraction >> 24 & 0xFF) + local35.anInt3819 * elapsedTime;
				if (local65 < 0) {
					local65 = 0;
				} else if (local65 > 65535) {
					local65 = 65535;
				}
				super.colorArgb &= 0xFFFFFF;
				super.colorArgb |= (local65 & 0xFF00) << 16;
				this.colourFraction &= 0xFFFFFF;
				this.colourFraction |= (local65 & 0xFF) << 24;
			}
		}
		if (local35.endSpeed != -1 && this.lifetime - this.remainingLifetime <= local35.startSizeChange) {
			this.speed += local35.speedStep * elapsedTime;
		}
		if (local35.endSize != -1 && this.lifetime - this.remainingLifetime <= local35.colorFadeStart) {
			super.size += local35.sizeChangeStep * elapsedTime;
		}
		local65 = this.directionX;
		local82 = this.directionY;
		local99 = this.directionZ;
		@Pc(303) boolean local303 = false;
		@Pc(313) int local313;
		@Pc(319) int local319;
		@Pc(325) int local325;
		@Pc(340) int local340;
		@Pc(348) long local348;
		if (local35.speedUpdateType == 1) {
			local313 = local17 - this.producer.anInt4631;
			local319 = local22 - this.producer.anInt4652;
			local325 = local27 - this.producer.anInt4653;
			local340 = (int) Math.sqrt((double) (local313 * local313 + local319 * local319 + local325 * local325));
			local348 = local35.speedFallOffStep * local340 * elapsedTime;
			this.speed = (int) ((long) this.speed - ((long) this.speed * local348 >> 18));
		} else if (local35.speedUpdateType == 2) {
			local313 = local17 - this.producer.anInt4631;
			local319 = local22 - this.producer.anInt4652;
			local325 = local27 - this.producer.anInt4653;
			local340 = local313 * local313 + local319 * local319 + local325 * local325;
			local348 = local35.speedFallOffStep * local340 * elapsedTime;
			this.speed = (int) ((long) this.speed - ((long) this.speed * local348 >> 28));
		}
		@Pc(444) int local444;
		@Pc(478) int local478;
		@Pc(491) long local491;
		@Pc(501) int local501;
		@Pc(537) int local537;
		if (local35.anIntArray256 != null) {
			@Pc(426) Country local426 = local31.effectorCache.aClass4_207;
			for (@Pc(429) Country local429 = local426.aClass4_262; local429 != local426; local429 = local429.aClass4_262) {
				@Pc(433) ParticleEffector local433 = (ParticleEffector) local429;
				@Pc(436) ParticleType local436 = local433.type;
				if (local436.particleHandlingType != 1) {
					@Pc(442) boolean local442 = false;
					for (local444 = 0; local444 < local35.anIntArray256.length; local444++) {
						if (local35.anIntArray256[local444] == local436.partileFileId) {
							local442 = true;
							break;
						}
					}
					if (local442) {
						@Pc(468) int local468 = local17 - local433.anInt3220;
						@Pc(473) int local473 = local22 - local433.verticeX;
						local478 = local27 - local433.anInt3225;
						local491 = local468 * local468 + local473 * local473 + local478 * local478;
						if (local491 <= local436.uid) {
							local501 = (int) Math.sqrt((double) local491);
							if (local501 == 0) {
								local501 = 1;
							}
							@Pc(529) long local529 = (long) (local468 * local433.anInt3218 + local473 * local436.anInt125 + local478 * local433.anInt3221) * 65535L / (long) (local436.anInt119 * local501);
							if (local529 >= (long) local436.anInt124) {
								local537 = 0;
								if (local436.anInt133 == 1) {
									local537 = (local501 >> 4) * local436.anInt131;
								} else if (local436.anInt133 == 2) {
									local537 = (local501 >> 4) * (local501 >> 4) * local436.anInt131;
								}
								if (local436.verticeCalculationType != 0) {
									@Pc(643) int local643 = (local468 << 15) / local501 * local436.anInt119;
									@Pc(652) int local652 = (local473 << 15) / local501 * local436.anInt119;
									@Pc(661) int local661 = (local478 << 15) / local501 * local436.anInt119;
									if (local436.currentOffset == 0) {
										local65 += local643 * elapsedTime >> 15;
										local82 += local652 * elapsedTime >> 15;
										local99 += local661 * elapsedTime >> 15;
										local303 = true;
									} else {
										super.x += local643 * elapsedTime >> 15;
										super.y += local652 * elapsedTime >> 15;
										super.z += local661 * elapsedTime >> 15;
									}
								} else if (local436.currentOffset == 0) {
									local65 += (local433.anInt3218 - local537) * elapsedTime;
									local82 += (local436.anInt125 - local537) * elapsedTime;
									local99 += (local433.anInt3221 - local537) * elapsedTime;
									local303 = true;
								} else {
									super.x += (local433.anInt3218 - local537) * elapsedTime;
									super.y += (local436.anInt125 - local537) * elapsedTime;
									super.z += (local433.anInt3221 - local537) * elapsedTime;
								}
							}
						}
					}
				}
			}
		}
		if (local35.effectiveVertexUids != null) {
			for (local313 = 0; local313 < local35.effectiveVertexUids.length; local313++) {
				@Pc(743) ParticleEffector local743 = (ParticleEffector) Static166.aClass100_1.method2671((long) local35.effectiveVertexUids[local313]);
				while (local743 != null) {
					@Pc(747) ParticleType local747 = local743.type;
					local340 = local17 - local743.anInt3220;
					@Pc(757) int local757 = local22 - local743.verticeX;
					local444 = local27 - local743.anInt3225;
					@Pc(775) long local775 = (long) (local340 * local340 + local757 * local757 + local444 * local444);
					if (local775 > local747.uid) {
						local743 = (ParticleEffector) Static166.aClass100_1.method2670();
					} else {
						local478 = (int) Math.sqrt((double) local775);
						if (local478 == 0) {
							local478 = 1;
						}
						local491 = (long) (local340 * local743.anInt3218 + local757 * local747.anInt125 + local444 * local743.anInt3221) * 65535L / (long) (local747.anInt119 * local478);
						if (local491 < (long) local747.anInt124) {
							local743 = (ParticleEffector) Static166.aClass100_1.method2670();
						} else {
							local501 = 0;
							if (local747.anInt133 == 1) {
								local501 = (local478 >> 4) * local747.anInt131;
							} else if (local747.anInt133 == 2) {
								local501 = (local478 >> 4) * (local478 >> 4) * local747.anInt131;
							}
							if (local747.verticeCalculationType != 0) {
								@Pc(939) int local939 = (local340 << 15) / local478 * local747.anInt119;
								@Pc(948) int local948 = (local757 << 15) / local478 * local747.anInt119;
								local537 = (local444 << 15) / local478 * local747.anInt119;
								if (local747.currentOffset == 0) {
									local65 += local939 * elapsedTime >> 15;
									local82 += local948 * elapsedTime >> 15;
									local99 += local537 * elapsedTime >> 15;
									local303 = true;
								} else {
									super.x += local939 * elapsedTime >> 15;
									super.y += local948 * elapsedTime >> 15;
									super.z += local537 * elapsedTime >> 15;
								}
							} else if (local747.currentOffset == 0) {
								local65 += (local743.anInt3218 - local501) * elapsedTime;
								local82 += (local747.anInt125 - local501) * elapsedTime;
								local99 += (local743.anInt3221 - local501) * elapsedTime;
								local303 = true;
							} else {
								super.x += (local743.anInt3218 - local501) * elapsedTime;
								super.y += (local747.anInt125 - local501) * elapsedTime;
								super.z += (local743.anInt3221 - local501) * elapsedTime;
							}
							local743 = (ParticleEffector) Static166.aClass100_1.method2670();
						}
					}
				}
			}
		}
		if (local35.anIntArray257 != null) {
			if (local35.copiedParticleFileIds == null) {
				local35.copiedParticleFileIds = new int[local35.anIntArray257.length];
				for (local313 = 0; local313 < local35.anIntArray257.length; local313++) {
					Class16_Sub4_Sub1.method1587(local35.anIntArray257[local313]);
					local35.copiedParticleFileIds[local313] = ((Class4_Sub37) Class4_Sub3.aClass102_2.method2700((long) local35.anIntArray257[local313])).anInt5426;
				}
			}
			for (local313 = 0; local313 < local35.copiedParticleFileIds.length; local313++) {
				@Pc(1082) ParticleType local1082 = ParticleBinaryTree.aClass8Array1[local35.copiedParticleFileIds[local313]];
				if (local1082.currentOffset == 0) {
					local65 += local1082.anInt128 * elapsedTime;
					local82 += local1082.anInt125 * elapsedTime;
					local99 += local1082.anInt132 * elapsedTime;
					local303 = true;
				} else {
					super.x += local1082.anInt128 * elapsedTime;
					super.y += local1082.anInt125 * elapsedTime;
					super.z += local1082.anInt132 * elapsedTime;
				}
			}
		}
		if (local303) {
			while (true) {
				if (local65 <= 32767 && local82 <= 32767 && local99 <= 32767 && local65 >= -32767 && local82 >= -32767 && local99 >= -32767) {
					this.directionX = (short) local65;
					this.directionY = (short) local82;
					this.directionZ = (short) local99;
					break;
				}
				local65 >>= 0x1;
				local82 >>= 0x1;
				local99 >>= 0x1;
				this.speed <<= 0x1;
			}
		}
		super.x = (int) ((long) super.x + ((long) this.directionX * (long) this.speed >> 23) * (long) elapsedTime);
		super.y = (int) ((long) super.y + ((long) this.directionY * (long) this.speed >> 23) * (long) elapsedTime);
		super.z = (int) ((long) super.z + ((long) this.directionZ * (long) this.speed >> 23) * (long) elapsedTime);
	}
}
