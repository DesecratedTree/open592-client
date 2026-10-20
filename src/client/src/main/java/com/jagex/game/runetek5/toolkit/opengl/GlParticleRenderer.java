package com.jagex.game.runetek5.toolkit.opengl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;

import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.toolkit.opengl.*;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!cm")
public final class GlParticleRenderer {

	@OriginalMember(owner = "client!cm", name = "m", descriptor = "Lclient!pc;")
	private Class177 colourPointer;

	@OriginalMember(owner = "client!cm", name = "o", descriptor = "Lclient!pc;")
	private Class177 aClass177_2;

	@OriginalMember(owner = "client!cm", name = "p", descriptor = "Lclient!pc;")
	private Class177 aClass177_3;

	@OriginalMember(owner = "client!cm", name = "q", descriptor = "Lclient!vd;")
	private ElementsBufferGL anInterface11_1;

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "[F")
	private final float[] modelViewMatrix = new float[16];

	@OriginalMember(owner = "client!cm", name = "h", descriptor = "Lclient!hk;")
	private final GlBitBuffer vertices = new GlBitBuffer(786336);

	@OriginalMember(owner = "client!cm", name = "n", descriptor = "I")
	private final int bucketCountBits = Class196.method4426(1600);

	@OriginalMember(owner = "client!cm", name = "s", descriptor = "[I")
	private final int[] overflowBucketSizes = new int[64];

	@OriginalMember(owner = "client!cm", name = "r", descriptor = "[[Lclient!wv;")
	private final PointEntity[][] buckets = new PointEntity[1600][64];

	@OriginalMember(owner = "client!cm", name = "v", descriptor = "[[Lclient!wv;")
	private final PointEntity[][] overflowBuckets = new PointEntity[64][768];

	@OriginalMember(owner = "client!cm", name = "w", descriptor = "[I")
	private final int[] depths = new int[8191];

	@OriginalMember(owner = "client!cm", name = "t", descriptor = "[I")
	private final int[] bucketSizes = new int[1600];

	@OriginalMember(owner = "client!cm", name = "u", descriptor = "I")
	private int overflowBucketCount = 0;

	static {
		new Class83("Invalid teleport!", "Unzulässiger Teleport!", "Téléportation non valide !", "Teleporte inválido!");
	}

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "(Lclient!fd;II)V")
	private void method915(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1) {
		OpenGL.glGetFloatv(2982, this.modelViewMatrix, 0);
		@Pc(15) float local15 = this.modelViewMatrix[0];
		@Pc(20) float local20 = this.modelViewMatrix[4];
		@Pc(25) float local25 = this.modelViewMatrix[8];
		@Pc(30) float local30 = this.modelViewMatrix[1];
		@Pc(35) float local35 = this.modelViewMatrix[5];
		@Pc(40) float local40 = this.modelViewMatrix[9];
		@Pc(44) float local44 = local30 + local15;
		@Pc(48) float local48 = local20 + local35;
		@Pc(52) float local52 = local40 + local25;
		@Pc(57) float local57 = local15 - local30;
		@Pc(70) float local70 = local20 - local35;
		@Pc(75) float local75 = local25 - local40;
		@Pc(80) float local80 = local30 - local15;
		@Pc(85) float local85 = local35 - local20;
		this.vertices.currentPosition = 0;
		@Pc(94) float local94 = local40 - local25;
		@Pc(101) int local101;
		@Pc(116) int local116;
		@Pc(125) int local125;
		@Pc(134) PointEntity local134;
		@Pc(137) int local137;
		@Pc(142) byte local142;
		@Pc(147) byte local147;
		@Pc(150) byte local150;
		@Pc(155) byte local155;
		@Pc(161) float local161;
		@Pc(167) float local167;
		@Pc(173) float local173;
		@Pc(178) int local178;
		@Pc(497) float local497;
		@Pc(502) int local502;
		@Pc(442) int local442;
		@Pc(458) PointEntity local458;
		@Pc(461) int local461;
		@Pc(474) byte local474;
		@Pc(479) byte local479;
		@Pc(491) float local491;
		if (arg0.bigEndianess) {
			for (local101 = arg1 - 1; local101 >= 0; local101--) {
				local116 = this.bucketSizes[local101] <= 64 ? this.bucketSizes[local101] : 64;
				if (local116 > 0) {
					for (local125 = local116 - 1; local125 >= 0; local125--) {
						local134 = this.buckets[local101][local125];
						local137 = local134.colorArgb;
						local142 = (byte) (local137 >> 16);
						local147 = (byte) (local137 >> 8);
						local150 = (byte) local137;
						local155 = (byte) (local137 >>> 24);
						local161 = local134.x >> Class111.anInt3366;
						local167 = local134.y >> Class111.anInt3366;
						local173 = local134.z >> Class111.anInt3366;
						local178 = local134.size >> Class111.anInt3366;
						this.vertices.method2556(0.0F);
						this.vertices.method2556(0.0F);
						this.vertices.method2556(local161 + (float) -local178 * local44);
						this.vertices.method2556((float) -local178 * local48 + local167);
						this.vertices.method2556(local173 + local52 * (float) -local178);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2556(1.0F);
						this.vertices.method2556(0.0F);
						this.vertices.method2556(local161 + local57 * (float) local178);
						this.vertices.method2556(local167 + (float) local178 * local70);
						this.vertices.method2556((float) local178 * local75 + local173);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2556(1.0F);
						this.vertices.method2556(1.0F);
						this.vertices.method2556((float) local178 * local44 + local161);
						this.vertices.method2556(local167 + (float) local178 * local48);
						this.vertices.method2556((float) local178 * local52 + local173);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2556(0.0F);
						this.vertices.method2556(1.0F);
						this.vertices.method2556(local161 + (float) local178 * local80);
						this.vertices.method2556(local167 + (float) local178 * local85);
						this.vertices.method2556(local173 + (float) local178 * local94);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
					}
					if (this.bucketSizes[local101] > 64) {
						local442 = this.bucketSizes[local101] - 64 - 1;
						for (local137 = this.overflowBucketSizes[local442] - 1; local137 >= 0; local137--) {
							local458 = this.overflowBuckets[local442][local137];
							local461 = local458.colorArgb;
							local150 = (byte) (local461 >> 16);
							local155 = (byte) (local461 >> 8);
							local474 = (byte) local461;
							local479 = (byte) (local461 >>> 24);
							local173 = local458.x >> Class111.anInt3366;
							local491 = local458.y >> Class111.anInt3366;
							local497 = local458.z >> Class111.anInt3366;
							local502 = local458.size >> Class111.anInt3366;
							this.vertices.method2556(0.0F);
							this.vertices.method2556(0.0F);
							this.vertices.method2556(local44 * (float) -local502 + local173);
							this.vertices.method2556(local491 + local48 * (float) -local502);
							this.vertices.method2556((float) -local502 * local52 + local497);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2556(1.0F);
							this.vertices.method2556(0.0F);
							this.vertices.method2556((float) local502 * local57 + local173);
							this.vertices.method2556((float) local502 * local70 + local491);
							this.vertices.method2556(local497 + (float) local502 * local75);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2556(1.0F);
							this.vertices.method2556(1.0F);
							this.vertices.method2556(local44 * (float) local502 + local173);
							this.vertices.method2556((float) local502 * local48 + local491);
							this.vertices.method2556(local52 * (float) local502 + local497);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2556(0.0F);
							this.vertices.method2556(1.0F);
							this.vertices.method2556(local173 + (float) local502 * local80);
							this.vertices.method2556(local491 + (float) local502 * local85);
							this.vertices.method2556(local497 + (float) local502 * local94);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
						}
					}
				}
			}
		} else {
			for (local101 = arg1 - 1; local101 >= 0; local101--) {
				local116 = this.bucketSizes[local101] > 64 ? 64 : this.bucketSizes[local101];
				if (local116 > 0) {
					for (local125 = local116 - 1; local125 >= 0; local125--) {
						local134 = this.buckets[local101][local125];
						local137 = local134.colorArgb;
						local142 = (byte) (local137 >> 16);
						local147 = (byte) (local137 >> 8);
						local150 = (byte) local137;
						local155 = (byte) (local137 >>> 24);
						local161 = local134.x >> Class111.anInt3366;
						local167 = local134.y >> Class111.anInt3366;
						local173 = local134.z >> Class111.anInt3366;
						local178 = local134.size >> Class111.anInt3366;
						this.vertices.method2552(0.0F);
						this.vertices.method2552(0.0F);
						this.vertices.method2552(local161 + (float) -local178 * local44);
						this.vertices.method2552(local48 * (float) -local178 + local167);
						this.vertices.method2552(local173 + local52 * (float) -local178);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2552(1.0F);
						this.vertices.method2552(0.0F);
						this.vertices.method2552(local161 + (float) local178 * local57);
						this.vertices.method2552(local70 * (float) local178 + local167);
						this.vertices.method2552((float) local178 * local75 + local173);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2552(1.0F);
						this.vertices.method2552(1.0F);
						this.vertices.method2552(local161 + (float) local178 * local44);
						this.vertices.method2552((float) local178 * local48 + local167);
						this.vertices.method2552(local173 + (float) local178 * local52);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
						this.vertices.method2552(0.0F);
						this.vertices.method2552(1.0F);
						this.vertices.method2552(local161 + (float) local178 * local80);
						this.vertices.method2552(local85 * (float) local178 + local167);
						this.vertices.method2552((float) local178 * local94 + local173);
						this.vertices.method2551(local142);
						this.vertices.method2551(local147);
						this.vertices.method2551(local150);
						this.vertices.method2551(local155);
					}
					if (this.bucketSizes[local101] > 64) {
						local442 = this.bucketSizes[local101] - 65;
						for (local137 = this.overflowBucketSizes[local442] - 1; local137 >= 0; local137--) {
							local458 = this.overflowBuckets[local442][local137];
							local461 = local458.colorArgb;
							local150 = (byte) (local461 >> 16);
							local155 = (byte) (local461 >> 8);
							local474 = (byte) local461;
							local479 = (byte) (local461 >>> 24);
							local173 = local458.x >> Class111.anInt3366;
							local491 = local458.y >> Class111.anInt3366;
							local497 = local458.z >> Class111.anInt3366;
							local502 = local458.size >> Class111.anInt3366;
							this.vertices.method2552(0.0F);
							this.vertices.method2552(0.0F);
							this.vertices.method2552(local173 + (float) -local502 * local44);
							this.vertices.method2552(local491 + local48 * (float) -local502);
							this.vertices.method2552((float) -local502 * local52 + local497);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2552(1.0F);
							this.vertices.method2552(0.0F);
							this.vertices.method2552(local173 + local57 * (float) local502);
							this.vertices.method2552(local491 + local70 * (float) local502);
							this.vertices.method2552((float) local502 * local75 + local497);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2552(1.0F);
							this.vertices.method2552(1.0F);
							this.vertices.method2552(local44 * (float) local502 + local173);
							this.vertices.method2552(local491 + local48 * (float) local502);
							this.vertices.method2552(local497 + local52 * (float) local502);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
							this.vertices.method2552(0.0F);
							this.vertices.method2552(1.0F);
							this.vertices.method2552((float) local502 * local80 + local173);
							this.vertices.method2552((float) local502 * local85 + local491);
							this.vertices.method2552(local497 + local94 * (float) local502);
							this.vertices.method2551(local150);
							this.vertices.method2551(local155);
							this.vertices.method2551(local474);
							this.vertices.method2551(local479);
						}
					}
				}
			}
		}
		if (this.vertices.currentPosition != 0) {
			this.anInterface11_1.method5184(this.vertices.currentPosition, 24, this.vertices.buffer);
			arg0.method1934(this.colourPointer, null, this.aClass177_3, this.aClass177_2);
			arg0.method1925(this.vertices.currentPosition / 24);
		}
	}

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "(BLclient!mc;Lclient!fd;)V")
	public void method916(@OriginalArg(1) ParticleList arg0, @OriginalArg(2) GlToolkit arg1) {
		if (arg1.aClass31_Sub1_4 == null) {
			return;
		}
		this.method920(arg1);
		@Pc(17) float local17 = arg1.aClass31_Sub1_4.aFloat206;
		@Pc(21) float local21 = arg1.aClass31_Sub1_4.aFloat203;
		@Pc(25) float local25 = arg1.aClass31_Sub1_4.aFloat199;
		@Pc(37) float local37 = arg1.aClass31_Sub1_4.aFloat198;
		try {
			@Pc(47) int local47;
			@Pc(57) int local57;
			@Pc(82) ParticleNode local82;
			@Pc(95) int local95;
			@Pc(245) PointEntity[] local245;
			@Pc(188) int local188;
			@Pc(255) int local255;
			@Pc(257) int local257;
			if (arg0.aBoolean417) {
				local47 = arg0.anInt4515 - arg0.anInt4520;
				if (local47 + 2 <= 1600) {
					local47 += 2;
					local57 = 0;
				} else {
					local57 = Class196.method4426(local47) + 1 - this.bucketCountBits;
					local47 = (local47 >> local57) + 2;
				}
				@Pc(79) ParticleNode local79 = arg0.particles.aClass3_Sub1_7;
				local82 = local79.next2;
				@Pc(84) int local84 = -2;
				@Pc(86) boolean local86 = true;
				@Pc(88) boolean local88 = true;
				while (local79 != local82) {
					this.overflowBucketCount = 0;
					for (local95 = 0; local95 < local47; local95++) {
						this.bucketSizes[local95] = 0;
					}
					for (@Pc(108) int local108 = 0; local108 < 64; local108++) {
						this.overflowBucketSizes[local108] = 0;
					}
					while (local79 != local82) {
						@Pc(124) PointEntity local124 = (PointEntity) local82;
						if (local88) {
							local84 = local124.materialId;
							local86 = local124.lightIntensityAdjustment;
							local88 = false;
						} else if (local124.materialId != local84 || local86 != local124.lightIntensityAdjustment) {
							local88 = true;
							break;
						}
						local188 = (int) ((float) (local124.x >> Class111.anInt3366) * local17 + (float) (local124.y >> Class111.anInt3366) * local21 + (float) (local124.z >> Class111.anInt3366) * local25 + local37) - arg0.anInt4520 >> local57;
						if (local188 < 1600) {
							if (this.bucketSizes[local188] >= 64) {
								label198: {
									if (this.bucketSizes[local188] == 64) {
										if (this.overflowBucketCount == 64) {
											break label198;
										}
										this.bucketSizes[local188] += this.overflowBucketCount++ + 1;
									}
									local245 = this.overflowBuckets[this.bucketSizes[local188] - 64 - 1];
									local255 = this.bucketSizes[local188] - 1 - 64;
									local257 = this.overflowBucketSizes[this.bucketSizes[local188] - 1 - 64];
									this.overflowBucketSizes[local255] = this.overflowBucketSizes[this.bucketSizes[local188] - 1 - 64] + 1;
									local245[local257] = local124;
								}
							} else {
								this.buckets[local188][this.bucketSizes[local188]++] = local124;
							}
						}
						local82 = local82.next2;
					}
					if (local84 >= 0) {
						arg1.setActiveTexture(arg1.textureCache.method1549(local84));
						arg1.method1900(arg1.materialProvider.getMetrics(local84).anInt6934);
					} else {
						arg1.setActiveTexture(null);
					}
					if (local86 && arg1.sunBrightness != MapRegion.aFloat224) {
						arg1.B(MapRegion.aFloat224);
					} else if (arg1.sunBrightness != 1.0F) {
						arg1.B(1.0F);
					}
					this.method915(arg1, local47);
				}
			} else {
				local47 = 0;
				local57 = Integer.MAX_VALUE;
				@Pc(350) int local350 = 0;
				local82 = arg0.particles.aClass3_Sub1_7;
				@Pc(357) ParticleNode local357;
				@Pc(389) int local389;
				for (local357 = local82.next2; local357 != local82; local357 = local357.next2) {
					@Pc(362) PointEntity local362 = (PointEntity) local357;
					local389 = (int) (local37 + (float) (local362.x >> Class111.anInt3366) * local17 + local21 * (float) (local362.y >> Class111.anInt3366) + (float) (local362.z >> Class111.anInt3366) * local25);
					this.depths[local47++] = local389;
					if (local57 > local389) {
						local57 = local389;
					}
					if (local350 < local389) {
						local350 = local389;
					}
				}
				@Pc(415) int local415 = local350 - local57;
				if (local415 + 2 <= 1600) {
					local415 += 2;
					local389 = 0;
				} else {
					local389 = Class196.method4426(local415) + 1 - this.bucketCountBits;
					local415 = (local415 >> local389) + 2;
				}
				local47 = 0;
				local357 = local82.next2;
				local95 = -2;
				@Pc(451) boolean local451 = true;
				@Pc(453) boolean local453 = true;
				while (local357 != local82) {
					this.overflowBucketCount = 0;
					for (local188 = 0; local188 < local415; local188++) {
						this.bucketSizes[local188] = 0;
					}
					for (@Pc(473) int local473 = 0; local473 < 64; local473++) {
						this.overflowBucketSizes[local473] = 0;
					}
					while (local357 != local82) {
						@Pc(489) PointEntity local489 = (PointEntity) local357;
						if (local453) {
							local451 = local489.lightIntensityAdjustment;
							local453 = false;
							local95 = local489.materialId;
						}
						if (local47 > 0 && (local95 != local489.materialId || local451 != local489.lightIntensityAdjustment)) {
							local453 = true;
							break;
						}
						@Pc(532) int local532 = this.depths[local47++] - local57 >> local389;
						if (local532 < 1600) {
							if (this.bucketSizes[local532] < 64) {
								this.buckets[local532][this.bucketSizes[local532]++] = local489;
							} else {
								label148: {
									if (this.bucketSizes[local532] == 64) {
										if (this.overflowBucketCount == 64) {
											break label148;
										}
										this.bucketSizes[local532] += this.overflowBucketCount++ + 1;
									}
									local245 = this.overflowBuckets[this.bucketSizes[local532] - 65];
									local255 = this.bucketSizes[local532] - 64 - 1;
									local257 = this.overflowBucketSizes[this.bucketSizes[local532] - 64 - 1];
									this.overflowBucketSizes[local255] = this.overflowBucketSizes[this.bucketSizes[local532] - 64 - 1] + 1;
									local245[local257] = local489;
								}
							}
						}
						local357 = local357.next2;
					}
					if (local95 < 0) {
						arg1.setActiveTexture(null);
					} else {
						arg1.setActiveTexture(arg1.textureCache.method1549(local95));
						arg1.method1900(arg1.materialProvider.getMetrics(local95).anInt6934);
					}
					if (local451 && MapRegion.aFloat224 != arg1.sunBrightness) {
						arg1.B(MapRegion.aFloat224);
					} else if (arg1.sunBrightness != 1.0F) {
						arg1.B(1.0F);
					}
					this.method915(arg1, local415);
				}
			}
		} catch (@Pc(681) Exception local681) {
		}
		this.method919(arg1);
	}

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "(BLclient!fd;)V")
	public void method917(@OriginalArg(1) GlToolkit arg0) {
		this.anInterface11_1 = arg0.method1960(196584, null, true, 24);
		this.aClass177_3 = new Class177(this.anInterface11_1, 5126, 2, 0);
		this.aClass177_2 = new Class177(this.anInterface11_1, 5126, 3, 8);
		this.colourPointer = new Class177(this.anInterface11_1, 5121, 4, 20);
	}

	@OriginalMember(owner = "client!cm", name = "a", descriptor = "(ILclient!fd;)V")
	private void method919(@OriginalArg(1) GlToolkit arg0) {
		arg0.method1911(true);
		OpenGL.glEnable(16384);
		OpenGL.glEnable(16385);
		if (arg0.sunBrightness != MapRegion.aFloat224) {
			arg0.B(MapRegion.aFloat224);
		}
	}

	@OriginalMember(owner = "client!cm", name = "b", descriptor = "(ILclient!fd;)V")
	private void method920(@OriginalArg(1) GlToolkit arg0) {
		MapRegion.aFloat224 = arg0.sunBrightness;
		arg0.method1904();
		OpenGL.glDisable(16384);
		OpenGL.glDisable(16385);
		arg0.method1911(false);
		arg0.method1957(-2);
		OpenGL.glNormal3f(0.0F, -1.0F, 0.0F);
	}

@OriginalMember(owner = "client!cm", name = "k", descriptor = "F")
	public static float aFloat31;
@OriginalMember(owner = "client!cm", name = "e", descriptor = "Z")
	public static boolean aBoolean93 = false;
@OriginalMember(owner = "client!cm", name = "f", descriptor = "[I")
	public static final int[] anIntArray56 = new int[14];
@OriginalMember(owner = "client!cm", name = "a", descriptor = "(I)V")
	public static void method913() {
		for (@Pc(12) int local12 = 0; local12 < 5; local12++) {
			TextureOpBoxBlur.aBooleanArray17[local12] = false;
		}
		Class133.anInt4125 = 0;
		Js5TextureSource.anInt2836 = -1;
		ParamTypeList.anInt6417 = 0;
		Class3_Sub3.anInt2386 = 1;
		Class27_Sub2.anInt2797 = -1;
	}
@OriginalMember(owner = "client!cm", name = "a", descriptor = "(IIIII)V")
	public static void method914(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(8) Class4_Sub1_Sub11 local8 = NPCTypeList.method5222(arg1, 4);
		local8.method2604();
		local8.anInt3083 = arg0;
		local8.anInt3079 = arg3;
		local8.anInt3082 = arg2;
	}
@OriginalMember(owner = "client!cm", name = "a", descriptor = "(III)Lclient!ii;")
	public static Class16_Sub2 method918(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(7) Class164 local7 = Class70.aClass164ArrayArrayArray5[arg0][arg1][arg2];
		return local7 == null ? null : local7.aClass16_Sub2_2;
	}
}
