package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Static166;
import com.jagex.game.runetek5.deob.Static384;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.graphics.model.particle.Particle;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.entity.*;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.entity.ParticleSystem;
@OriginalClass("client!ao")
public final class ParticleSystem extends EntityNode {

	@OriginalMember(owner = "client!ao", name = "u", descriptor = "I")
	public int anInt214;

	@OriginalMember(owner = "client!ao", name = "w", descriptor = "I")
	public int level;

	@OriginalMember(owner = "client!ao", name = "y", descriptor = "I")
	public int anInt217;

	@OriginalMember(owner = "client!ao", name = "z", descriptor = "I")
	public int anInt218;

	@OriginalMember(owner = "client!ao", name = "A", descriptor = "I")
	public int anInt219;

	@OriginalMember(owner = "client!ao", name = "l", descriptor = "Z")
	private boolean stopped = false;

	@OriginalMember(owner = "client!ao", name = "m", descriptor = "I")
	public int nextParticleSlot = 0;

	@OriginalMember(owner = "client!ao", name = "q", descriptor = "Z")
	public boolean removed = false;

	@OriginalMember(owner = "client!ao", name = "j", descriptor = "Lclient!gn;")
	private EntityNodeList emitterCache = new EntityNodeList();

	@OriginalMember(owner = "client!ao", name = "t", descriptor = "I")
	private int emitterCount = 0;

	@OriginalMember(owner = "client!ao", name = "s", descriptor = "Lclient!pk;")
	public Class183 effectorCache = new Class183();

	@OriginalMember(owner = "client!ao", name = "v", descriptor = "Z")
	private boolean awaitingStartup = false;

	@OriginalMember(owner = "client!ao", name = "x", descriptor = "I")
	private int effectorCount = 0;

	@OriginalMember(owner = "client!ao", name = "B", descriptor = "Lclient!mc;")
	public final ParticleList list = new ParticleList();

	@OriginalMember(owner = "client!ao", name = "o", descriptor = "[Lclient!lb;")
	public Particle[] aClass3_Sub1_Sub1_Sub1Array1 = new Particle[8192];

	@OriginalMember(owner = "client!ao", name = "k", descriptor = "J")
	private long clock;

	@OriginalMember(owner = "client!ao", name = "r", descriptor = "J")
	private long lastTick;

	@OriginalMember(owner = "client!ao", name = "<init>", descriptor = "(I)V")
	public ParticleSystem(@OriginalArg(0) int cycle) {
		Static384.aClass84_8.offer(this);
		this.clock = cycle;
		this.lastTick = cycle;
		this.awaitingStartup = true;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "()V")
	public void restart() {
		this.awaitingStartup = true;
	}

	@OriginalMember(owner = "client!ao", name = "b", descriptor = "()V")
	public void remove() {
		this.removed = true;
		for (@Pc(8) ParticleEffector local8 = (ParticleEffector) this.effectorCache.method4140(); local8 != null; local8 = (ParticleEffector) this.effectorCache.method4144()) {
			if (local8.type.particleHandlingType == 1) {
				local8.method4662();
			}
		}
		this.aClass3_Sub1_Sub1_Sub1Array1 = new Particle[8192];
		this.nextParticleSlot = 0;
		this.emitterCache = new EntityNodeList();
		this.emitterCount = 0;
		this.effectorCache = new Class183();
		this.effectorCount = 0;
		this.method5676();
	}

	@OriginalMember(owner = "client!ao", name = "c", descriptor = "()V")
	public void stopped() {
		this.stopped = true;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "(J)V")
	public void setClock(@OriginalArg(0) long clock) {
		this.clock = clock;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "(Lclient!ya;J[Lclient!bu;[Lclient!pf;Z)V")
	public void update(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) long clock, @OriginalArg(2) EmissiveTriangle[] emitters, @OriginalArg(3) EffectiveVertex[] effectors) {
		if (!this.removed) {
			this.updateEmitters(toolkit, emitters);
			this.updateEffectors(effectors);
			this.clock = clock;
		}
	}

	@OriginalMember(owner = "client!ao", name = "e", descriptor = "()V")
	public void method170() {
		this.list.particles.method5122();
		for (@Pc(10) ParticleEmitter local10 = (ParticleEmitter) this.emitterCache.method2312(); local10 != null; local10 = (ParticleEmitter) this.emitterCache.method2309()) {
			local10.method3635(this.lastTick);
		}
	}

	@OriginalMember(owner = "client!ao", name = "f", descriptor = "()Lclient!mc;")
	public ParticleList getList() {
		return this.list;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "(Lclient!ya;J)Z")
	public boolean performUpdate(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) long lastTick) {
		if (this.clock == this.lastTick) {
			this.running();
		} else {
			this.stopped();
		}
		if (lastTick - this.clock > 750L) {
			this.remove();
			return false;
		}
		@Pc(27) int local27 = (int) (lastTick - this.lastTick);
		@Pc(36) ParticleEmitter local36;
		if (this.awaitingStartup) {
			for (local36 = (ParticleEmitter) this.emitterCache.method2312(); local36 != null; local36 = (ParticleEmitter) this.emitterCache.method2309()) {
				for (@Pc(39) int local39 = 0; local39 < local36.type.updatesPerCycle; local39++) {
					local36.method3632(1, lastTick, !this.stopped, arg0);
				}
			}
			this.awaitingStartup = false;
		}
		for (local36 = (ParticleEmitter) this.emitterCache.method2312(); local36 != null; local36 = (ParticleEmitter) this.emitterCache.method2309()) {
			local36.method3632(local27, lastTick, !this.stopped, arg0);
		}
		this.lastTick = lastTick;
		return true;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "([Lclient!pf;Z)V")
	private void updateEffectors(@OriginalArg(0) EffectiveVertex[] effectors) {
		for (@Pc(1) int local1 = 0; local1 < 8; local1++) {
			ParticleSystem.EFFECT_SLOT_OCCUPIED[local1] = false;
		}
		@Pc(21) int local21;
		label68: for (@Pc(16) ParticleEffector local16 = (ParticleEffector) this.effectorCache.method4140(); local16 != null; local16 = (ParticleEffector) this.effectorCache.method4144()) {
			if (effectors != null) {
				for (local21 = 0; local21 < effectors.length; local21++) {
					if (local16.model == effectors[local21] || local16.model == effectors[local21].aClass179_2) {
						ParticleSystem.EFFECT_SLOT_OCCUPIED[local21] = true;
						local16.update();
						continue label68;
					}
				}
			}
			local16.method5684();
			this.effectorCount--;
			if (local16.method4666()) {
				local16.method4662();
				Class4_Sub29.anInt4588--;
			}
		}
		if (effectors == null) {
			return;
		}
		for (local21 = 0; local21 < effectors.length && this.effectorCount != 8; local21++) {
			if (!ParticleSystem.EFFECT_SLOT_OCCUPIED[local21]) {
				@Pc(93) ParticleEffector local93 = null;
				if (effectors[local21].method4106().particleHandlingType == 1 && Class4_Sub29.anInt4588 < 32) {
					local93 = new ParticleEffector(effectors[local21], this);
					Static166.aClass100_1.method2674(local93, (long) effectors[local21].effectId);
					Class4_Sub29.anInt4588++;
				}
				if (local93 == null) {
					local93 = new ParticleEffector(effectors[local21], this);
				}
				this.effectorCache.method4137(local93);
				this.effectorCount++;
				ParticleSystem.EFFECT_SLOT_OCCUPIED[local21] = true;
			}
		}
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "(IIIII)V")
	public void updateBounds(@OriginalArg(0) int level, @OriginalArg(1) int startX, @OriginalArg(2) int endX, @OriginalArg(3) int startZ, @OriginalArg(4) int endZ) {
		this.anInt217 = level;
		this.level = startX;
		this.anInt218 = endX;
		this.anInt219 = startZ;
		this.anInt214 = endZ;
	}

	@OriginalMember(owner = "client!ao", name = "a", descriptor = "(Lclient!ya;[Lclient!bu;Z)V")
	private void updateEmitters(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) EmissiveTriangle[] emitters) {
		for (@Pc(1) int local1 = 0; local1 < 8; local1++) {
			ParticleSystem.ACTIVE_PRODUCERS[local1] = false;
		}
		@Pc(21) int local21;
		label59: for (@Pc(16) ParticleEmitter local16 = (ParticleEmitter) this.emitterCache.method2312(); local16 != null; local16 = (ParticleEmitter) this.emitterCache.method2309()) {
			if (emitters != null) {
				for (local21 = 0; local21 < emitters.length; local21++) {
					if (local16.model == emitters[local21] || local16.model == emitters[local21].aClass29_1) {
						ParticleSystem.ACTIVE_PRODUCERS[local21] = true;
						local16.method3630();
						local16.inactive = false;
						continue label59;
					}
				}
			}
			if (local16.anInt4650 == 0) {
				local16.method5676();
				this.emitterCount--;
			} else {
				local16.inactive = true;
			}
		}
		if (emitters == null) {
			return;
		}
		for (local21 = 0; local21 < emitters.length && this.emitterCount != 8; local21++) {
			if (!ParticleSystem.ACTIVE_PRODUCERS[local21]) {
				@Pc(101) ParticleEmitter local101 = new ParticleEmitter(toolkit, emitters[local21], this, this.clock);
				this.emitterCache.offer(local101);
				this.emitterCount++;
				ParticleSystem.ACTIVE_PRODUCERS[local21] = true;
			}
		}
	}

	@OriginalMember(owner = "client!ao", name = "g", descriptor = "()V")
	private void running() {
		this.stopped = false;
	}

@OriginalMember(owner = "client!ao", name = "n", descriptor = "[Z")
	public static final boolean[] EFFECT_SLOT_OCCUPIED = new boolean[8];
@OriginalMember(owner = "client!ao", name = "p", descriptor = "[Z")
	public static final boolean[] ACTIVE_PRODUCERS = new boolean[8];
}
