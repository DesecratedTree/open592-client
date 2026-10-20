package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import java.awt.Canvas;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.buffer.Packet;
@OriginalClass("client!sr")
public final class FluType {

	@OriginalMember(owner = "client!sr", name = "a", descriptor = "I")
	public int anInt6341;

	@OriginalMember(owner = "client!sr", name = "b", descriptor = "I")
	public int anInt6342;

	@OriginalMember(owner = "client!sr", name = "c", descriptor = "I")
	public int anInt6343;

	@OriginalMember(owner = "client!sr", name = "i", descriptor = "I")
	public int anInt6348;

	@OriginalMember(owner = "client!sr", name = "e", descriptor = "Z")
	public boolean aBoolean579 = true;

	@OriginalMember(owner = "client!sr", name = "f", descriptor = "I")
	public int anInt6345 = -1;

	@OriginalMember(owner = "client!sr", name = "h", descriptor = "I")
	private int rgb = 0;

	@OriginalMember(owner = "client!sr", name = "p", descriptor = "I")
	public int anInt6353 = 128;

	@OriginalMember(owner = "client!sr", name = "a", descriptor = "(BI)V")
	private void method4956(@OriginalArg(1) int arg0) {
		@Pc(14) double local14 = (double) (arg0 >> 16 & 0xFF) / 256.0D;
		@Pc(23) double local23 = (double) (arg0 >> 8 & 0xFF) / 256.0D;
		@Pc(30) double local30 = (double) (arg0 & 0xFF) / 256.0D;
		@Pc(32) double local32 = local14;
		if (local14 > local23) {
			local32 = local23;
		}
		if (local30 < local32) {
			local32 = local30;
		}
		@Pc(46) double local46 = local14;
		if (local14 < local23) {
			local46 = local23;
		}
		if (local30 > local46) {
			local46 = local30;
		}
		@Pc(64) double local64 = 0.0D;
		@Pc(66) double local66 = 0.0D;
		@Pc(72) double local72 = (local32 + local46) / 2.0D;
		if (local32 != local46) {
			if (local72 < 0.5D) {
				local66 = (local46 - local32) / (local32 + local46);
			}
			if (local14 == local46) {
				local64 = (local23 - local30) / (-local32 + local46);
			} else if (local46 == local23) {
				local64 = (local30 - local14) / (local46 - local32) + 2.0D;
			} else if (local30 == local46) {
				local64 = (local14 - local23) / (-local32 + local46) + 4.0D;
			}
			if (local72 >= 0.5D) {
				local66 = (local46 - local32) / (2.0D - local46 - local32);
			}
		}
		this.anInt6341 = (int) (local66 * 256.0D);
		this.anInt6343 = (int) (local72 * 256.0D);
		local64 /= 6.0D;
		if (this.anInt6343 < 0) {
			this.anInt6343 = 0;
		} else if (this.anInt6343 > 255) {
			this.anInt6343 = 255;
		}
		if (local72 > 0.5D) {
			this.anInt6342 = (int) (local66 * 512.0D * (1.0D - local72));
		} else {
			this.anInt6342 = (int) (local72 * local66 * 512.0D);
		}
		if (this.anInt6341 < 0) {
			this.anInt6341 = 0;
		} else if (this.anInt6341 > 255) {
			this.anInt6341 = 255;
		}
		if (this.anInt6342 < 1) {
			this.anInt6342 = 1;
		}
		this.anInt6348 = (int) (local64 * (double) this.anInt6342);
	}

	@OriginalMember(owner = "client!sr", name = "a", descriptor = "(ILclient!iv;)V")
	public void method4960(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(17) int local17 = arg0.method2490();
			if (local17 == 0) {
				return;
			}
			this.method4961(arg0, local17);
		}
	}

	@OriginalMember(owner = "client!sr", name = "a", descriptor = "(Lclient!iv;II)V")
	private void method4961(@OriginalArg(0) Packet arg0, @OriginalArg(1) int arg1) {
		if (arg1 == 1) {
			this.rgb = arg0.method2493();
			this.method4956(this.rgb);
		} else if (arg1 == 2) {
			this.anInt6345 = arg0.method2536();
			if (this.anInt6345 == 65535) {
				this.anInt6345 = -1;
			}
		} else if (arg1 == 3) {
			this.anInt6353 = arg0.method2536() << 0;
		} else if (arg1 == 4) {
			this.aBoolean579 = false;
		}
	}

@OriginalMember(owner = "client!sr", name = "o", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_85;
@OriginalMember(owner = "client!sr", name = "l", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_125 = new Class83(" has logged out.", " loggt sich aus.", " s'est déconnecté.", " saiu do jogo.");
@OriginalMember(owner = "client!sr", name = "b", descriptor = "(I)I")
	public static int method4958() {
		@Pc(7) com.jagex.game.runetek5.graphics.Toolkit local7 = MonoStereoData.aClass19_16;
		@Pc(9) boolean local9 = false;
		if (Packet.anInt2973 != 0) {
			@Pc(15) Canvas local15 = new Canvas();
			local15.setSize(100, 100);
			local7 = Toolkit.method4241(null, 0, local15, 0, null);
			local9 = true;
		}
		@Pc(32) long local32 = Class168.method3962();
		for (@Pc(34) int local34 = 0; local34 < 10000; local34++) {
			local7.method4276();
		}
		@Pc(69) int local69 = (int) (Class168.method3962() - local32);
		local7.method4293(0, 100, 0, -16777216, 100);
		if (local9) {
			local7.method4267();
		}
		return local69;
	}
@OriginalMember(owner = "client!sr", name = "a", descriptor = "(Lclient!r;I)V")
	public static void method4959(@OriginalArg(0) PathingEntity arg0) {
		arg0.transparent = false;
		@Pc(19) SeqType local19;
		if (arg0.anInt6076 != -1) {
			local19 = SceneLocEntity.aClass55_1.method1397(arg0.anInt6076);
			if (local19 == null || local19.durations == null) {
				arg0.anInt6076 = -1;
				arg0.aBoolean562 = false;
			} else {
				label394: {
					arg0.anInt6065++;
					if (arg0.anInt6016 < local19.durations.length && local19.frames[arg0.anInt6016] < arg0.anInt6065) {
						arg0.anInt6065 = 1;
						arg0.anInt6036++;
						arg0.anInt6016++;
						Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6016, arg0.aByte82, arg0.anInt6892, local19, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
					}
					if (arg0.anInt6016 >= local19.durations.length) {
						arg0.anInt6016 = 0;
						arg0.anInt6065 = 0;
						if (arg0.aBoolean562) {
							arg0.anInt6076 = arg0.method4757().method3637();
							if (arg0.anInt6076 == -1) {
								arg0.aBoolean562 = false;
								break label394;
							}
							local19 = SceneLocEntity.aClass55_1.method1397(arg0.anInt6076);
						}
						Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6016, arg0.aByte82, arg0.anInt6892, local19, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
					}
					arg0.anInt6036 = arg0.anInt6016 + 1;
					if (local19.durations.length <= arg0.anInt6036) {
						arg0.anInt6036 = 0;
					}
				}
			}
		}
		@Pc(183) int local183;
		@Pc(196) SeqType local196;
		@Pc(180) SpotAnimationType local180;
		if (arg0.anInt6029 != -1 && arg0.anInt6057 <= Shadow.anInt5323) {
			local180 = Js5MasterIndex.aClass194_2.method4421(arg0.anInt6029);
			local183 = local180.sequenceId;
			if (local183 == -1) {
				arg0.anInt6029 = -1;
			} else {
				label396: {
					local196 = SceneLocEntity.aClass55_1.method1397(local183);
					if (local180.replay) {
						if (local196.interruptType == 3) {
							if (arg0.anInt6087 > 0 && Shadow.anInt5323 >= arg0.anInt6018 && Shadow.anInt5323 > arg0.anInt6023) {
								arg0.anInt6029 = -1;
								break label396;
							}
						} else if (local196.interruptType == 1 && arg0.anInt6087 > 0 && Shadow.anInt5323 >= arg0.anInt6018 && Shadow.anInt5323 > arg0.anInt6023) {
							arg0.anInt6057 = Shadow.anInt5323 + 1;
							break label396;
						}
					}
					if (local196 == null || local196.durations == null) {
						arg0.anInt6029 = -1;
					} else {
						if (arg0.anInt6027 < 0) {
							arg0.anInt6027 = 0;
							Class4_Sub1_Sub2.method156(arg0.anInt6893, 0, arg0.aByte82, arg0.anInt6892, local196, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
						}
						arg0.anInt6033++;
						if (local196.durations.length > arg0.anInt6027 && arg0.anInt6033 > local196.frames[arg0.anInt6027]) {
							arg0.anInt6027++;
							arg0.anInt6033 = 1;
							Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6027, arg0.aByte82, arg0.anInt6892, local196, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
						}
						if (arg0.anInt6027 >= local196.durations.length) {
							if (local180.replay) {
								arg0.anInt6027 -= local196.anInt4405;
								arg0.anInt6050++;
								if (local196.anInt4412 <= arg0.anInt6050) {
									arg0.anInt6029 = -1;
								} else if (arg0.anInt6027 >= 0 && arg0.anInt6027 < local196.durations.length) {
									Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6027, arg0.aByte82, arg0.anInt6892, local196, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
								} else {
									arg0.anInt6029 = -1;
								}
							} else {
								arg0.anInt6029 = -1;
							}
						}
						arg0.anInt6083 = arg0.anInt6027 + 1;
						if (arg0.anInt6083 >= local196.durations.length) {
							if (local180.replay) {
								arg0.anInt6083 -= local196.anInt4405;
								if (local196.anInt4412 <= arg0.anInt6050 + 1) {
									arg0.anInt6083 = -1;
								} else if (arg0.anInt6083 < 0 || local196.durations.length <= arg0.anInt6083) {
									arg0.anInt6083 = -1;
								}
							} else {
								arg0.anInt6083 = -1;
							}
						}
					}
				}
			}
		}
		if (arg0.anInt6045 != -1 && arg0.anInt6066 <= Shadow.anInt5323) {
			local180 = Js5MasterIndex.aClass194_2.method4421(arg0.anInt6045);
			local183 = local180.sequenceId;
			if (local183 == -1) {
				arg0.anInt6045 = -1;
			} else {
				label399: {
					local196 = SceneLocEntity.aClass55_1.method1397(local183);
					if (local180.replay) {
						if (local196.interruptType == 3) {
							if (arg0.anInt6087 > 0 && Shadow.anInt5323 >= arg0.anInt6018 && Shadow.anInt5323 > arg0.anInt6023) {
								arg0.anInt6045 = -1;
								break label399;
							}
						} else if (local196.interruptType == 1 && arg0.anInt6087 > 0 && Shadow.anInt5323 >= arg0.anInt6018 && arg0.anInt6023 < Shadow.anInt5323) {
							arg0.anInt6066 = Shadow.anInt5323 + 1;
							break label399;
						}
					}
					if (local196 == null || local196.durations == null) {
						arg0.anInt6045 = -1;
					} else {
						if (arg0.anInt6022 < 0) {
							arg0.anInt6022 = 0;
							Class4_Sub1_Sub2.method156(arg0.anInt6893, 0, arg0.aByte82, arg0.anInt6892, local196, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
						}
						arg0.anInt6062++;
						if (arg0.anInt6022 < local196.durations.length && arg0.anInt6062 > local196.frames[arg0.anInt6022]) {
							arg0.anInt6022++;
							arg0.anInt6062 = 1;
							Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6022, arg0.aByte82, arg0.anInt6892, local196, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
						}
						if (arg0.anInt6022 >= local196.durations.length) {
							if (local180.replay) {
								arg0.anInt6022 -= local196.anInt4405;
								arg0.anInt6031++;
								if (arg0.anInt6031 >= local196.anInt4412) {
									arg0.anInt6045 = -1;
								} else if (arg0.anInt6022 >= 0 && local196.durations.length > arg0.anInt6022) {
									Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6022, arg0.aByte82, arg0.anInt6892, local196, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
								} else {
									arg0.anInt6045 = -1;
								}
							} else {
								arg0.anInt6045 = -1;
							}
						}
						arg0.anInt6069 = arg0.anInt6022 + 1;
						if (local196.durations.length <= arg0.anInt6069) {
							if (local180.replay) {
								arg0.anInt6069 -= local196.anInt4405;
								if (arg0.anInt6031 + 1 >= local196.anInt4412) {
									arg0.anInt6069 = -1;
								} else if (arg0.anInt6069 < 0 || local196.durations.length <= arg0.anInt6069) {
									arg0.anInt6069 = -1;
								}
							} else {
								arg0.anInt6069 = -1;
							}
						}
					}
				}
			}
		}
		if (arg0.anInt6021 != -1 && arg0.anInt6046 <= 1) {
			local19 = SceneLocEntity.aClass55_1.method1397(arg0.anInt6021);
			if (local19.interruptType == 3) {
				if (arg0.anInt6087 > 0 && arg0.anInt6018 <= Shadow.anInt5323 && Shadow.anInt5323 > arg0.anInt6023) {
					arg0.anInt6021 = -1;
				}
			} else if (local19.interruptType == 1 && arg0.anInt6087 > 0 && arg0.anInt6018 <= Shadow.anInt5323 && Shadow.anInt5323 > arg0.anInt6023) {
				arg0.anInt6046 = 2;
			}
		}
		if (arg0.anInt6021 != -1 && arg0.anInt6046 == 0) {
			local19 = SceneLocEntity.aClass55_1.method1397(arg0.anInt6021);
			if (local19 == null || local19.durations == null) {
				arg0.anInt6021 = -1;
			} else {
				arg0.anInt6073++;
				if (arg0.anInt6060 < local19.durations.length && arg0.anInt6073 > local19.frames[arg0.anInt6060]) {
					arg0.anInt6073 = 1;
					arg0.anInt6060++;
					Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6060, arg0.aByte82, arg0.anInt6892, local19, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
				}
				if (arg0.anInt6060 >= local19.durations.length) {
					arg0.anInt6060 -= local19.anInt4405;
					arg0.anInt6012++;
					if (local19.anInt4412 <= arg0.anInt6012) {
						arg0.anInt6021 = -1;
					} else if (arg0.anInt6060 >= 0 && arg0.anInt6060 < local19.durations.length) {
						Class4_Sub1_Sub2.method156(arg0.anInt6893, arg0.anInt6060, arg0.aByte82, arg0.anInt6892, local19, arg0 == Static1.aClass16_Sub1_Sub5_Sub1_1);
					} else {
						arg0.anInt6021 = -1;
					}
				}
				arg0.anInt6058 = arg0.anInt6060 + 1;
				if (arg0.anInt6058 >= local19.durations.length) {
					arg0.anInt6058 -= local19.anInt4405;
					if (arg0.anInt6012 + 1 >= local19.anInt4412) {
						arg0.anInt6058 = -1;
					} else if (arg0.anInt6058 < 0 || arg0.anInt6058 >= local19.durations.length) {
						arg0.anInt6058 = -1;
					}
				}
				arg0.transparent = local19.aBoolean407;
			}
		}
		if (arg0.anInt6046 > 0) {
			arg0.anInt6046--;
		}
		for (@Pc(1066) int local1066 = 0; local1066 < arg0.sequences.length; local1066++) {
			@Pc(1073) PathingEntitySequence local1073 = arg0.sequences[local1066];
			if (local1073 != null) {
				if (local1073.anInt5141 > 0) {
					local1073.anInt5141--;
				} else {
					local196 = SceneLocEntity.aClass55_1.method1397(local1073.anInt5139);
					if (local196 == null || local196.durations == null) {
						arg0.sequences[local1066] = null;
					} else {
						local1073.anInt5142++;
						if (local196.durations.length > local1073.anInt5138 && local196.frames[local1073.anInt5138] < local1073.anInt5142) {
							local1073.anInt5138++;
							local1073.anInt5142 = 1;
							Class4_Sub1_Sub2.method156(arg0.anInt6893, local1073.anInt5138, arg0.aByte82, arg0.anInt6892, local196, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
						}
						if (local1073.anInt5138 >= local196.durations.length) {
							local1073.anInt5138 -= local196.anInt4405;
							local1073.anInt5143++;
							if (local1073.anInt5143 >= local196.anInt4412) {
								arg0.sequences[local1066] = null;
							} else if (local1073.anInt5138 >= 0 && local196.durations.length > local1073.anInt5138) {
								Class4_Sub1_Sub2.method156(arg0.anInt6893, local1073.anInt5138, arg0.aByte82, arg0.anInt6892, local196, Static1.aClass16_Sub1_Sub5_Sub1_1 == arg0);
							} else {
								arg0.sequences[local1066] = null;
							}
						}
						local1073.anInt5140 = local1073.anInt5138 + 1;
						if (local196.durations.length <= local1073.anInt5140) {
							local1073.anInt5140 -= local196.anInt4405;
							if (local1073.anInt5143 + 1 >= local196.anInt4412) {
								local1073.anInt5140 = -1;
							} else if (local1073.anInt5140 < 0 || local196.durations.length <= local1073.anInt5140) {
								local1073.anInt5140 = -1;
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!sr", name = "a", descriptor = "(IILjava/lang/String;)I")
	public static int method4962(@OriginalArg(2) String arg0) {
		return Class174.method4041(16, arg0);
	}
}
