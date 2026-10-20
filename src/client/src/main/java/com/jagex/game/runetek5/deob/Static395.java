package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.config.bas.BasType;

import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.config.material.texture.TextureType;
public final class Static395 {

	@OriginalMember(owner = "client!tv", name = "a", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_92;

	@OriginalMember(owner = "client!tv", name = "b", descriptor = "Ljava/lang/String;")
	public static String aString63 = null;

	@OriginalMember(owner = "client!tv", name = "g", descriptor = "[I")
	public static final int[] anIntArray502 = new int[25];

	@OriginalMember(owner = "client!tv", name = "a", descriptor = "(III)I")
	public static int method5162(@OriginalArg(0) int arg0) {
		if (arg0 == -1) {
			return 12345678;
		}
		@Pc(17) int local17 = (arg0 & 0x7F) * 96 >> 7;
		if (local17 < 2) {
			local17 = 2;
		} else if (local17 > 126) {
			local17 = 126;
		}
		return local17 + (arg0 & 0xFF80);
	}

	@OriginalMember(owner = "client!tv", name = "a", descriptor = "(IZLclient!r;)V")
	public static void method5163(@OriginalArg(1) boolean arg0, @OriginalArg(2) PathingEntity arg1) {
		@Pc(9) BasType local9 = arg1.method4757();
		if (arg1.pathPointer == 0) {
			RuntimeException_Sub1.anInt4620 = -1;
			Class56.anInt1655 = 0;
			arg1.anInt6085 = 0;
			return;
		}
		if (arg1.anInt6021 != -1 && arg1.anInt6046 == 0) {
			@Pc(40) SeqType local40 = SceneLocEntity.aClass55_1.method1397(arg1.anInt6021);
			if (arg1.anInt6087 > 0 && local40.interruptType == 0) {
				arg1.anInt6085++;
				Class56.anInt1655 = 0;
				RuntimeException_Sub1.anInt4620 = -1;
				return;
			}
			if (arg1.anInt6087 <= 0 && local40.moveType == 0) {
				arg1.anInt6085++;
				RuntimeException_Sub1.anInt4620 = -1;
				Class56.anInt1655 = 0;
				return;
			}
		}
		@Pc(112) SeqType local112;
		@Pc(98) SpotAnimationType local98;
		if (arg1.anInt6029 != -1 && arg1.anInt6057 <= Shadow.anInt5323) {
			local98 = Js5MasterIndex.aClass194_2.method4421(arg1.anInt6029);
			if (local98.replay && local98.sequenceId != -1) {
				local112 = SceneLocEntity.aClass55_1.method1397(local98.sequenceId);
				if (arg1.anInt6087 > 0 && local112.interruptType == 0) {
					Class56.anInt1655 = 0;
					RuntimeException_Sub1.anInt4620 = -1;
					arg1.anInt6085++;
					return;
				}
				if (arg1.anInt6087 <= 0 && local112.moveType == 0) {
					Class56.anInt1655 = 0;
					RuntimeException_Sub1.anInt4620 = -1;
					arg1.anInt6085++;
					return;
				}
			}
		}
		if (arg1.anInt6029 != -1 && arg1.anInt6057 <= Shadow.anInt5323) {
			local98 = Js5MasterIndex.aClass194_2.method4421(arg1.anInt6029);
			if (local98.replay && local98.sequenceId != -1) {
				local112 = SceneLocEntity.aClass55_1.method1397(local98.sequenceId);
				if (arg1.anInt6087 > 0 && local112.interruptType == 0) {
					RuntimeException_Sub1.anInt4620 = -1;
					arg1.anInt6085++;
					Class56.anInt1655 = 0;
					return;
				}
				if (arg1.anInt6087 <= 0 && local112.moveType == 0) {
					arg1.anInt6085++;
					RuntimeException_Sub1.anInt4620 = -1;
					Class56.anInt1655 = 0;
					return;
				}
			}
		}
		@Pc(233) int local233 = arg1.anInt6893;
		@Pc(236) int local236 = arg1.anInt6892;
		@Pc(252) int local252 = arg1.movementBaseX[arg1.pathPointer - 1] * 128 + arg1.getSize() * 64;
		@Pc(269) int local269 = arg1.movementBaseY[arg1.pathPointer - 1] * 128 + arg1.getSize() * 64;
		if (local252 > local233) {
			if (local269 > local236) {
				arg1.method4743(10240);
			} else if (local236 <= local269) {
				arg1.method4743(12288);
			} else {
				arg1.method4743(14336);
			}
		} else if (local252 >= local233) {
			if (local236 < local269) {
				arg1.method4743(8192);
			} else if (local236 > local269) {
				arg1.method4743(0);
			}
		} else if (local269 > local236) {
			arg1.method4743(6144);
		} else if (local236 <= local269) {
			arg1.method4743(4096);
		} else {
			arg1.method4743(2048);
		}
		@Pc(355) byte local355 = arg1.movementQueueSpeed[arg1.pathPointer - 1];
		if (!arg0 && (local252 - local233 > 256 || local252 - local233 < -256 || local269 - local236 > 256 || local269 - local236 < -256)) {
			arg1.anInt6893 = local252;
			arg1.anInt6892 = local269;
			arg1.method4745(arg1.lastYawRotation);
			if (arg1.anInt6087 > 0) {
				arg1.anInt6087--;
			}
			RuntimeException_Sub1.anInt4620 = -1;
			Class56.anInt1655 = 0;
			arg1.pathPointer--;
			return;
		}
		@Pc(421) int local421 = 4;
		@Pc(423) boolean local423 = true;
		if (arg1 instanceof NPCEntity) {
			local423 = ((NPCEntity) arg1).npcType.aBoolean683;
		}
		@Pc(441) int local441;
		if (local423) {
			local441 = arg1.lastYawRotation - arg1.yaw.rotation;
			if (local441 != 0 && arg1.anInt6034 == -1 && arg1.anInt6080 != 0) {
				local421 = 2;
			}
			if (!arg0 && arg1.pathPointer > 2) {
				local421 = 6;
			}
			if (!arg0 && arg1.pathPointer > 3) {
				local421 = 8;
			}
		} else {
			if (!arg0 && arg1.pathPointer > 1) {
				local421 = 6;
			}
			if (!arg0 && arg1.pathPointer > 2) {
				local421 = 8;
			}
		}
		if (arg1.anInt6085 > 0 && arg1.pathPointer > 1) {
			local421 = 8;
			arg1.anInt6085--;
		}
		if (local355 == 2) {
			local421 <<= 0x1;
		} else if (local355 == 0) {
			local421 >>= 0x1;
		}
		if (local9.anInt4664 != -1) {
			local421 <<= 0x7;
			if (arg1.pathPointer == 1) {
				local441 = arg1.anInt6088 * arg1.anInt6088;
				@Pc(576) int local576 = (local252 >= arg1.anInt6893 ? local252 - arg1.anInt6893 : arg1.anInt6893 - local252) << 7;
				@Pc(598) int local598 = (arg1.anInt6892 > local269 ? arg1.anInt6892 - local269 : local269 + -arg1.anInt6892) << 7;
				@Pc(609) int local609 = local598 < local576 ? local576 : local598;
				@Pc(616) int local616 = local609 * local9.anInt4664 * 2;
				if (local616 < local441) {
					arg1.anInt6088 /= 2;
				} else if (local441 / 2 > local609) {
					arg1.anInt6088 -= local9.anInt4664;
					if (arg1.anInt6088 < 0) {
						arg1.anInt6088 = 0;
					}
				} else if (arg1.anInt6088 < local421) {
					arg1.anInt6088 += local9.anInt4664;
					if (local421 < arg1.anInt6088) {
						arg1.anInt6088 = local421;
					}
				}
			} else if (arg1.anInt6088 < local421) {
				arg1.anInt6088 += local9.anInt4664;
				if (local421 < arg1.anInt6088) {
					arg1.anInt6088 = local421;
				}
			} else if (arg1.anInt6088 > 0) {
				arg1.anInt6088 -= local9.anInt4664;
				if (arg1.anInt6088 < 0) {
					arg1.anInt6088 = 0;
				}
			}
			local421 = arg1.anInt6088 >> 7;
			if (local421 < 1) {
				local421 = 1;
			}
		}
		Class56.anInt1655 = 0;
		if (local233 < local252) {
			arg1.anInt6893 += local421;
			Class56.anInt1655 |= 0x4;
			if (arg1.anInt6893 > local252) {
				arg1.anInt6893 = local252;
			}
		} else if (local252 < local233) {
			arg1.anInt6893 -= local421;
			Class56.anInt1655 |= 0x8;
			if (local252 > arg1.anInt6893) {
				arg1.anInt6893 = local252;
			}
		}
		if (local269 > local236) {
			arg1.anInt6892 += local421;
			Class56.anInt1655 |= 0x1;
			if (local269 < arg1.anInt6892) {
				arg1.anInt6892 = local269;
			}
		} else if (local269 < local236) {
			arg1.anInt6892 -= local421;
			Class56.anInt1655 |= 0x2;
			if (arg1.anInt6892 < local269) {
				arg1.anInt6892 = local269;
			}
		}
		if (arg1.anInt6893 == local252 && local269 == arg1.anInt6892) {
			arg1.pathPointer--;
			if (arg1.anInt6087 > 0) {
				arg1.anInt6087--;
			}
		}
		if (local421 >= 8) {
			RuntimeException_Sub1.anInt4620 = 2;
		} else {
			RuntimeException_Sub1.anInt4620 = local355;
		}
	}

	@OriginalMember(owner = "client!tv", name = "a", descriptor = "(IZIII)V")
	public static void method5164(@OriginalArg(1) boolean arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (Class4_Sub18.method2034(arg2)) {
			TextureType.method3278(arg3, arg1, Class4_Sub36.aClass247ArrayArray2[arg2], -1, arg0);
		}
	}

	@OriginalMember(owner = "client!tv", name = "a", descriptor = "(I)I")
	public static int method5165() {
		return 2;
	}
}
