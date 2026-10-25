package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.client.hd.sway.HdVegetation;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.entity.ParticleSystem;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;

import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sign.SignedResource;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;

import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.OutputStream_Sub1;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.buffer.Packet;
@OriginalClass("client!bi")
public final class LocEntity {

	@OriginalMember(owner = "client!bi", name = "f", descriptor = "Lclient!lp;")
	private SeqType sequence;

	@OriginalMember(owner = "client!bi", name = "g", descriptor = "I")
	private int anInt533;

	@OriginalMember(owner = "client!bi", name = "i", descriptor = "Lclient!ba;")
	private Shadow shadow;

	@OriginalMember(owner = "client!bi", name = "m", descriptor = "[Z")
	private boolean[] castShadowAtPlane;

	@OriginalMember(owner = "client!bi", name = "p", descriptor = "Lclient!t;")
	private Model model;

	@OriginalMember(owner = "client!bi", name = "q", descriptor = "I")
	private int anInt538;

	@OriginalMember(owner = "client!bi", name = "x", descriptor = "Lclient!ao;")
	public ParticleSystem aClass3_Sub2_1;

	@OriginalMember(owner = "client!bi", name = "H", descriptor = "I")
	private int anInt549;

	@OriginalMember(owner = "client!bi", name = "L", descriptor = "I")
	private int anInt552;

	@OriginalMember(owner = "client!bi", name = "l", descriptor = "Z")
	private boolean aBoolean54 = false;

	@OriginalMember(owner = "client!bi", name = "r", descriptor = "I")
	private int anInt539 = -1;

	@OriginalMember(owner = "client!bi", name = "F", descriptor = "Z")
	private boolean aBoolean56 = false;

	@OriginalMember(owner = "client!bi", name = "j", descriptor = "I")
	private int anInt535 = -32768;

	@OriginalMember(owner = "client!bi", name = "o", descriptor = "I")
	private int anInt537 = -1;

	@OriginalMember(owner = "client!bi", name = "K", descriptor = "I")
	public final int anInt551;

	@OriginalMember(owner = "client!bi", name = "G", descriptor = "I")
	public final int anInt548;

	@OriginalMember(owner = "client!bi", name = "d", descriptor = "B")
	private final byte aByte9;

	@OriginalMember(owner = "client!bi", name = "y", descriptor = "I")
	private int anInt543;

	@OriginalMember(owner = "client!bi", name = "z", descriptor = "I")
	private int anInt544;

	@OriginalMember(owner = "client!bi", name = "n", descriptor = "I")
	public final int anInt536;

	@OriginalMember(owner = "client!bi", name = "v", descriptor = "B")
	private final byte aByte10;

	@OriginalMember(owner = "client!bi", name = "u", descriptor = "Z")
	private final boolean aBoolean55;

	@OriginalMember(owner = "client!bi", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIIZI)V")
	public LocEntity(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) LocType locType, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int level, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) boolean arg8, @OriginalArg(9) int arg9) {
		this.anInt551 = arg3;
		this.anInt548 = locType.id;
		this.aBoolean56 = arg8;
		this.aByte9 = (byte) level;
		this.anInt543 = arg6;
		this.anInt544 = arg7;
		this.anInt536 = arg2;
		this.aByte10 = (byte) arg5;
		this.aBoolean55 = toolkit.hardShadow() && locType.aBoolean526 && !this.aBoolean56;
		if (arg9 != -1) {
			this.aBoolean54 = true;
		}
		this.method579(arg9);
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IZLclient!ya;IIBZ)Lclient!t;")
	public Model method578(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(6) boolean arg5) {
		@Pc(11) LocType local11 = Static267.aClass262_2.method5560(this.anInt548);
		if (local11.alternativeIds != null) {
			local11 = local11.method4584(Class4_Sub6_Sub24.aClass114_1);
		}
		if (local11 == null) {
			this.method588(arg2);
			this.anInt539 = -1;
			this.anInt537 = this.anInt538;
			return null;
		}
		if (!this.aBoolean54 && local11.id != this.anInt539) {
			this.model = null;
			this.method579(-1);
		}
		this.method585(arg0, arg3);
		@Pc(72) boolean local72 = arg1 & this.aBoolean55 & TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) != 0;
		@Pc(110) boolean local110 = local72 & (this.anInt539 != local11.id || (this.anInt538 != this.anInt537 || this.sequence != null && (this.sequence.vorbis || OutputStream_Sub1.aBoolean244) && this.anInt538 != this.anInt552) && TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) >= 2);
		if (arg5 && !local110) {
			this.anInt537 = this.anInt538;
			this.anInt539 = local11.id;
			return null;
		}
		if (local110) {
			Class4_Sub17.method2015(this.shadow, this.aByte10, this.anInt543, this.anInt544, this.castShadowAtPlane);
		}
		@Pc(142) Class65 local142 = Class16_Sub1_Sub6.aClass65Array4[this.aByte10];
		@Pc(160) Class65 local160;
		if (this.aBoolean56) {
			local160 = Class4_Sub6_Sub4.aClass65Array1[0];
		} else {
			local160 = this.aByte10 < 3 ? Class16_Sub1_Sub6.aClass65Array4[this.aByte10 + 1] : null;
		}
		@Pc(168) Model local168 = null;
		if (this.sequence != null) {
			if (local110) {
				arg4 |= 0x40000;
			}
			local168 = local11.method4594(this.anInt536 == 11 ? 10 : this.anInt536, local142.a(this.anInt543, this.anInt544), this.anInt533, this.anInt538, arg2, this.sequence, this.anInt552, this.anInt543, this.anInt536 == 11 ? this.anInt551 + 4 : this.anInt551, this.anInt544, arg4, local160, local142);
			if (local168 == null) {
				this.shadow = null;
				this.castShadowAtPlane = null;
				this.anInt535 = 0;
			} else {
				if (local110) {
					if (this.castShadowAtPlane == null) {
						this.castShadowAtPlane = new boolean[4];
					}
					this.shadow = local168.fa(this.shadow);
					Class38.method844(this.shadow, this.aByte10, arg3, arg0, this.castShadowAtPlane);
				}
				this.anInt535 = local168.setOffsets();
			}
			this.model = null;
		} else if (this.model != null && arg4 == (this.model.P() & arg4) && this.anInt539 == local11.id) {
			local168 = this.model;
		} else {
			if (this.model != null) {
				arg4 |= this.model.P();
			}
			@Pc(334) Class149 local334 = local11.method4589(arg2, this.anInt543, arg4, local142.a(this.anInt543, this.anInt544), local110, this.anInt536 == 11 ? this.anInt551 + 4 : this.anInt551, local160, this.anInt544, this.anInt536 == 11 ? 10 : this.anInt536, local142);
			if (local334 == null) {
				this.anInt535 = 0;
				this.castShadowAtPlane = null;
				this.model = null;
				this.shadow = null;
			} else {
				local168 = local334.aClass110_5;
				this.model = local334.aClass110_5;
				if (local110) {
					this.castShadowAtPlane = null;
					this.shadow = local334.aClass4_Sub1_Sub4_4;
					Class38.method844(this.shadow, this.aByte10, arg3, arg0, null);
				}
				this.anInt535 = local168.setOffsets();
			}
		}
		this.anInt539 = local11.id;
		this.anInt543 = arg3;
		this.anInt537 = this.anInt538;
		this.anInt544 = arg0;
		// 592HD: the one place the scene still knows a plant from a wall. The two
		// vegetation rows displace only models marked here; the kind rides the
		// model into the draw seam instead of being guessed from geometry there.
		// The model is the loc type's own, keyed by the same shape that built it,
		// so the mark is stable for every draw of it.
		if (local168 instanceof GlModel) {
			((GlModel) local168).hdVegetation = HdVegetation.ofLocShape(this.anInt536);
		}
		return local168;
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(IB)V")
	private void method579(@OriginalArg(0) int arg0) {
		@Pc(7) int local7 = arg0;
		@Pc(9) boolean local9 = false;
		if (arg0 == -1) {
			@Pc(19) LocType local19 = Static267.aClass262_2.method5560(this.anInt548);
			@Pc(21) LocType local21 = local19;
			if (local19.alternativeIds != null) {
				local19 = local19.method4584(Class4_Sub6_Sub24.aClass114_1);
			}
			if (local19 == null) {
				return;
			}
			if (local21 == local19) {
				local21 = null;
			}
			if (local19.anIntArray405 != null) {
				if (this.sequence != null && local19.method4583(this.sequence.id)) {
					return;
				}
				local7 = local19.method4588();
				if (this.anInt539 != local19.id) {
					local9 = local19.replaySequence;
				}
			} else if (local19.anInt5840 == -1) {
				if (local21 != null && local21.anIntArray405 != null) {
					if (this.sequence != null && local21.method4583(this.sequence.id)) {
						return;
					}
					local7 = local21.method4588();
					if (this.anInt539 != local21.id) {
						local9 = local21.replaySequence;
					}
				} else if (local21 != null && local21.anInt5840 != -1 && local21.id != this.anInt539) {
					local7 = local21.anInt5840;
					local9 = local21.replaySequence;
				}
			} else if (local19.id != this.anInt539) {
				local7 = local19.anInt5840;
				local9 = local19.replaySequence;
			}
		}
		if (local7 == -1) {
			this.sequence = null;
			return;
		}
		this.model = null;
		if (this.sequence == null || this.sequence.id != local7) {
			this.sequence = SceneLocEntity.aClass55_1.method1397(local7);
		} else if (this.sequence.loopType == 0) {
			return;
		}
		if (this.sequence.durations == null) {
			this.sequence = null;
			return;
		}
		if (local9) {
			this.anInt538 = (int) ((double) this.sequence.durations.length * Math.random());
			this.anInt533 = (int) (Math.random() * (double) this.sequence.frames[this.anInt538]) + 1;
		} else {
			this.anInt533 = 1;
			this.anInt538 = 0;
		}
		this.anInt552 = this.anInt538 + 1;
		if (this.anInt552 < 0 || this.sequence.durations.length <= this.anInt552) {
			this.anInt552 = -1;
		}
		this.anInt549 = Shadow.anInt5323 - this.anInt533;
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(B)I")
	public int method580() {
		return this.anInt535;
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ILclient!ya;)V")
	public void method582(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.method578(this.anInt544, true, arg0, this.anInt543, 262144, true);
	}

	@OriginalMember(owner = "client!bi", name = "b", descriptor = "(I)Z")
	public boolean method584() {
		return this.aBoolean55;
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(III)V")
	private void method585(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		label80: while (true) {
			if (this.sequence == null) {
				if (this.aBoolean54) {
					return;
				}
				this.method579(-1);
				if (this.sequence == null) {
					return;
				}
			}
			@Pc(33) int local33 = Shadow.anInt5323 - this.anInt549;
			if (local33 > 100 && this.sequence.anInt4405 > 0) {
				@Pc(49) int local49 = this.sequence.durations.length - this.sequence.anInt4405;
				while (local49 > this.anInt538 && local33 > this.sequence.frames[this.anInt538]) {
					local33 -= this.sequence.frames[this.anInt538];
					this.anInt538++;
				}
				if (this.anInt538 >= local49) {
					@Pc(92) int local92 = 0;
					for (@Pc(94) int local94 = local49; local94 < this.sequence.durations.length; local94++) {
						local92 += this.sequence.frames[local94];
					}
					local33 %= local92;
				}
				this.anInt552 = this.anInt538 + 1;
				if (this.sequence.durations.length <= this.anInt552) {
					this.anInt552 -= this.sequence.anInt4405;
					if (this.anInt552 < 0 || this.sequence.durations.length <= this.anInt552) {
						this.anInt552 = -1;
					}
				}
			}
			while (local33 > this.sequence.frames[this.anInt538]) {
				Class4_Sub1_Sub2.method156(arg1, this.anInt538, this.aByte9, arg0, this.sequence, false);
				local33 -= this.sequence.frames[this.anInt538];
				this.anInt538++;
				if (this.anInt538 >= this.sequence.durations.length) {
					this.anInt538 -= this.sequence.anInt4405;
					if (this.anInt538 < 0 || this.anInt538 >= this.sequence.durations.length) {
						this.sequence = null;
						continue label80;
					}
				}
				this.anInt552 = this.anInt538 + 1;
				if (this.sequence.durations.length <= this.anInt552) {
					this.anInt552 -= this.sequence.anInt4405;
					if (this.anInt552 < 0 || this.sequence.durations.length <= this.anInt552) {
						this.anInt552 = -1;
					}
				}
			}
			this.anInt533 = local33;
			this.anInt549 = Shadow.anInt5323 - local33;
			return;
		}
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ZIILclient!t;ILclient!ya;II)V")
	public void method586(@OriginalArg(0) boolean arg0, @OriginalArg(1) int arg1, @OriginalArg(3) Model arg2, @OriginalArg(4) int arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		@Pc(6) EmissiveTriangle[] local6 = arg2.getEmissiveTriangles();
		@Pc(16) EffectiveVertex[] local16 = arg2.getEffectiveVertices();
		if ((this.aClass3_Sub2_1 == null || this.aClass3_Sub2_1.removed) && (local6 != null || local16 != null)) {
			@Pc(33) LocType local33 = Static267.aClass262_2.method5560(this.anInt548);
			if (local33.alternativeIds != null) {
				local33 = local33.method4584(Class4_Sub6_Sub24.aClass114_1);
			}
			if (local33 != null) {
				this.aClass3_Sub2_1 = new ParticleSystem(Shadow.anInt5323);
			}
		}
		if (this.aClass3_Sub2_1 == null) {
			return;
		}
		if (arg0) {
			this.aClass3_Sub2_1.update(arg4, (long) Shadow.anInt5323, local6, local16);
		} else {
			this.aClass3_Sub2_1.setClock((long) Shadow.anInt5323);
		}
		this.aClass3_Sub2_1.updateBounds(this.aByte9, arg6, arg3, arg1, arg5);
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(II)V")
	public void method587(@OriginalArg(0) int arg0) {
		this.aBoolean54 = true;
		this.method579(arg0);
	}

	@OriginalMember(owner = "client!bi", name = "finalize", descriptor = "()V")
	@Override
	public void finalize() {
		if (this.aClass3_Sub2_1 != null) {
			this.aClass3_Sub2_1.stopped();
		}
	}

	@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ZLclient!ya;)V")
	public void method588(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (this.shadow != null) {
			Class4_Sub17.method2015(this.shadow, this.aByte10, this.anInt543, this.anInt544, this.castShadowAtPlane);
			this.shadow = null;
			this.castShadowAtPlane = null;
		}
	}

@OriginalMember(owner = "client!bi", name = "B", descriptor = "Lclient!qt;")
	public static SignedResource aClass199_2;
@OriginalMember(owner = "client!bi", name = "J", descriptor = "[[Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component[][] aClass247ArrayArray1;
@OriginalMember(owner = "client!bi", name = "k", descriptor = "Z")
	public static final boolean aBoolean53 = false;
@OriginalMember(owner = "client!bi", name = "a", descriptor = "(ILclient!fs;I)Lclient!qh;")
	public static MusicPatch method577(@OriginalArg(0) int arg0, @OriginalArg(1) Js5ResourceProvider arg1) {
		@Pc(8) byte[] local8 = arg1.method2122(arg0);
		return local8 == null ? null : new MusicPatch(local8);
	}
@OriginalMember(owner = "client!bi", name = "a", descriptor = "(BF)F")
	public static float method581(@OriginalArg(1) float arg0) {
		return (arg0 * (arg0 * 6.0F - 15.0F) + 10.0F) * arg0 * arg0 * arg0;
	}
@OriginalMember(owner = "client!bi", name = "a", descriptor = "(Lclient!r;I)V")
	public static void method583(@OriginalArg(0) PathingEntity arg0) {
		@Pc(9) int local9 = arg0.anInt6018 - Shadow.anInt5323;
		@Pc(20) int local20 = arg0.anInt6043 * 128 + arg0.getSize() * 64;
		@Pc(32) int local32 = arg0.anInt6052 * 128 + arg0.getSize() * 64;
		arg0.anInt6892 += (local32 - arg0.anInt6892) / local9;
		arg0.anInt6085 = 0;
		arg0.anInt6893 += (local20 - arg0.anInt6893) / local9;
		if (arg0.lb == 0) {
			arg0.method4743(8192);
		}
		if (arg0.lb == 1) {
			arg0.method4743(12288);
		}
		if (arg0.lb == 2) {
			arg0.method4743(0);
		}
		if (arg0.lb == 3) {
			arg0.method4743(4096);
		}
	}
}
