package com.jagex.game.runetek5.graphics.model;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.config.sequence.frameset.AnimFrame;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.scene.ground.Ground;

import java.awt.Component;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.preference.Preference;

import com.jagex.game.runetek5.graphics.model.*;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
@OriginalClass("client!t")
public abstract class Model {

	static {
		new Class83("You will be un-muted within 24 hours.", "Du wirst innerhalb der nächsten 24 Stunden wieder sprechen können.", "Vous aurez à nouveau accès à la messagerie instantanée dans 24 heures.", "O veto será retirado dentro de 24 horas.");
	}

	@OriginalMember(owner = "client!t", name = "<init>", descriptor = "()V")
	protected Model() {
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(Lclient!c;Lclient!mi;II)V")
	public abstract void renderOrtho(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon boundingBox, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!t", name = "Z", descriptor = "(I)V")
	public abstract void Z(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "()[Lclient!bu;")
	public abstract EmissiveTriangle[] getEmissiveTriangles();

	@OriginalMember(owner = "client!t", name = "MA", descriptor = "()I")
	public abstract int setOffsets();

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IIZIILclient!pt;Lclient!pt;II)V")
	public final void method4883(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) boolean arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) Class4_Sub1_Sub17 arg5, @OriginalArg(6) Class4_Sub1_Sub17 arg6, @OriginalArg(7) int arg7) {
		if (arg3 == -1 || !this.w()) {
			return;
		}
		@Pc(16) AnimFrame local16 = arg5.aClass88Array1[arg3];
		@Pc(19) AnimBase local19 = local16.base;
		@Pc(21) AnimFrame local21 = null;
		if (arg6 != null) {
			local21 = arg6.aClass88Array1[arg4];
			if (local21.base != local19) {
				local21 = null;
			}
		}
		this.method4896(local21, null, local19, arg7, arg2, arg0, local16, false, arg1, 65535, null);
		this.scaleWidth();
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IIIIILclient!ta;III)V")
	protected final void method4884(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) Class65 arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7) {
		@Pc(21) int local21 = -arg5 / 2;
		@Pc(26) int local26 = -arg2 / 2;
		@Pc(35) int local35 = arg4.a(local21 + arg0, local26 + arg1);
		@Pc(39) int local39 = arg5 / 2;
		@Pc(44) int local44 = -arg2 / 2;
		@Pc(53) int local53 = arg4.a(local39 + arg0, local44 + arg1);
		@Pc(58) int local58 = -arg5 / 2;
		@Pc(62) int local62 = arg2 / 2;
		@Pc(71) int local71 = arg4.a(local58 + arg0, arg1 + local62);
		@Pc(75) int local75 = arg5 / 2;
		@Pc(79) int local79 = arg2 / 2;
		@Pc(89) int local89 = arg4.a(arg0 + local75, local79 + arg1);
		@Pc(100) int local100 = local53 > local35 ? local35 : local53;
		@Pc(107) int local107 = local71 >= local89 ? local89 : local71;
		@Pc(114) int local114 = local53 >= local89 ? local89 : local53;
		@Pc(121) int local121 = local71 > local35 ? local35 : local71;
		@Pc(148) int local148;
		if (arg2 != 0) {
			@Pc(137) int local137 = (int) (Math.atan2((double) (local100 - local107), (double) arg2) * 2607.5945876176133D) & 0x3FFF;
			if (local137 != 0) {
				if (arg3 != 0) {
					if (local137 > 8192) {
						local148 = 16384 - arg3;
						if (local148 > local137) {
							local137 = local148;
						}
					} else if (local137 > arg3) {
						local137 = arg3;
					}
				}
				this.Z(local137);
			}
		}
		@Pc(171) int local171 = local35 + local89;
		if (arg5 != 0) {
			@Pc(190) int local190 = (int) (Math.atan2((double) (local121 - local114), (double) arg5) * 2607.5945876176133D) & 0x3FFF;
			if (local190 != 0) {
				if (arg6 != 0) {
					if (local190 > 8192) {
						local148 = 16384 - arg6;
						if (local190 < local148) {
							local190 = local148;
						}
					} else if (local190 > arg6) {
						local190 = arg6;
					}
				}
				this.R(local190);
			}
		}
		if (local171 > local53 + local71) {
			local171 = local53 + local71;
		}
		local171 = (local171 >> 1) - arg7;
		if (local171 != 0) {
			this.drawScaledColored(0, local171, 0);
		}
	}

	@OriginalMember(owner = "client!t", name = "PA", descriptor = "()I")
	public abstract int PA();

	@OriginalMember(owner = "client!t", name = "JA", descriptor = "(III)V")
	public abstract void drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!t", name = "E", descriptor = "(I)V")
	public abstract void E(@OriginalArg(0) int ambient);

	@OriginalMember(owner = "client!t", name = "b", descriptor = "()[Lclient!pf;")
	public abstract EffectiveVertex[] getEffectiveVertices();

	@OriginalMember(owner = "client!t", name = "c", descriptor = "()V")
	public abstract void uploadToGpu();

	@OriginalMember(owner = "client!t", name = "IA", descriptor = "()V")
	public abstract void IA();

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(BIZ)Lclient!t;")
	public abstract Model copy(@OriginalArg(0) byte copyType, @OriginalArg(1) int functionMask, @OriginalArg(2) boolean ensureLit);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(I[IIIIZI[III)V")
	private void method4888(@OriginalArg(0) int arg0, @OriginalArg(1) int[] arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5, @OriginalArg(7) int[] arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8) {
		@Pc(71) int local71;
		if (arg4 == 1) {
			if (arg0 == 0 || arg0 == 1) {
				local71 = -arg8;
				arg8 = arg2;
				arg2 = local71;
			} else if (arg0 == 3) {
				local71 = arg8;
				arg8 = arg2;
				arg2 = local71;
			} else if (arg0 == 2) {
				local71 = arg8;
				arg8 = -arg2 & 0x3FFF;
				arg2 = local71 & 0x3FFF;
			}
		} else if (arg4 == 2) {
			if (arg0 == 0 || arg0 == 1) {
				arg2 = -arg2;
				arg8 = -arg8;
			} else if (arg0 == 2) {
				arg2 = -arg2 & 0x3FFF;
				arg8 = -arg8 & 0x3FFF;
			}
		} else if (arg4 == 3) {
			if (arg0 == 0 || arg0 == 1) {
				local71 = arg8;
				arg8 = -arg2;
				arg2 = local71;
			} else if (arg0 == 3) {
				local71 = arg8;
				arg8 = arg2;
				arg2 = local71;
			} else if (arg0 == 2) {
				local71 = arg8;
				arg8 = arg2 & 0x3FFF;
				arg2 = -local71 & 0x3FFF;
			}
		}
		if (arg3 == 65535) {
			this.applyTransformUnmasked(arg0, arg6, arg8, arg7, arg2, arg4, arg5);
		} else {
			this.M(arg0, arg6, arg8, arg7, arg2, arg5, arg3, arg1);
		}
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(Lclient!c;Lclient!mi;I)V")
	public abstract void render(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon boundingBox, @OriginalArg(2) int arg2);

	@OriginalMember(owner = "client!t", name = "XA", descriptor = "(I[IIIIIZ)V")
	protected abstract void applyTransformUnmasked(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) int rotation, @OriginalArg(6) boolean lights);

	@OriginalMember(owner = "client!t", name = "l", descriptor = "()I")
	public abstract int l();

	@OriginalMember(owner = "client!t", name = "M", descriptor = "(I[IIIIZI[I)V")
	protected abstract void M(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) boolean lights, @OriginalArg(6) int submesh, @OriginalArg(7) int[] matrix);

	@OriginalMember(owner = "client!t", name = "fa", descriptor = "(Lclient!ba;)Lclient!ba;")
	public abstract Shadow fa(@OriginalArg(0) Shadow shadow);

	@OriginalMember(owner = "client!t", name = "P", descriptor = "()I")
	public abstract int P();

	@OriginalMember(owner = "client!t", name = "d", descriptor = "()Z")
	public abstract boolean loadedTextures();

	@OriginalMember(owner = "client!t", name = "HA", descriptor = "()I")
	public abstract int HA();

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(Lclient!t;IIIZ)V")
	public abstract void shareLight(@OriginalArg(0) Model model, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) boolean renderTypeAdjustment);

	@OriginalMember(owner = "client!t", name = "NA", descriptor = "()I")
	public abstract int NA();

	@OriginalMember(owner = "client!t", name = "m", descriptor = "()I")
	public abstract int addLight();

	@OriginalMember(owner = "client!t", name = "BA", descriptor = "(SS)V")
	public abstract void copyAlpha(@OriginalArg(0) short arg0, @OriginalArg(1) short arg1);

	@OriginalMember(owner = "client!t", name = "R", descriptor = "(I)V")
	public abstract void R(@OriginalArg(0) int arg0);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IILclient!pt;)V")
	public final void method4892(@OriginalArg(1) int arg0, @OriginalArg(2) Class4_Sub1_Sub17 arg1) {
		if (arg0 == -1 || !this.w()) {
			return;
		}
		@Pc(18) AnimFrame local18 = arg1.aClass88Array1[arg0];
		@Pc(21) AnimBase local21 = local18.base;
		for (@Pc(23) int local23 = 0; local23 < local18.numTransformations; local23++) {
			@Pc(30) short local30 = local18.baseIndices[local23];
			if (local21.shadowed[local30]) {
				if (local18.labels[local23] != -1) {
					this.N(0, 0, 0, 0);
				}
				this.N(local21.transformationType[local30], local18.transformationX[local23], local18.transformationY[local23], local18.transformationZ[local23]);
			}
		}
		this.scaleWidth();
	}

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IIII)V")
	public abstract void adjustColours(@OriginalArg(0) int hue, @OriginalArg(1) int saturation, @OriginalArg(2) int lightness, @OriginalArg(3) int opacity);

	@OriginalMember(owner = "client!t", name = "c", descriptor = "(I)V")
	public abstract void c(@OriginalArg(0) int angle);

	@OriginalMember(owner = "client!t", name = "h", descriptor = "(IILclient!ta;Lclient!ta;III)V")
	public abstract void h(@OriginalArg(0) int contourType, @OriginalArg(1) int contourModifier, @OriginalArg(2) Class65 arg2, @OriginalArg(3) Class65 arg3, @OriginalArg(4) int x, @OriginalArg(5) int y, @OriginalArg(6) int z);

	@OriginalMember(owner = "client!t", name = "aa", descriptor = "(I)V")
	public abstract void aa(@OriginalArg(0) int contrast);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IILclient!c;Z)Z")
	public abstract boolean picked(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) Ground arg2, @OriginalArg(3) boolean quick);

	@OriginalMember(owner = "client!t", name = "UA", descriptor = "(I)V")
	public abstract void UA(@OriginalArg(0) int functionMask);

	@OriginalMember(owner = "client!t", name = "H", descriptor = "(III)V")
	public abstract void H(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ);

	@OriginalMember(owner = "client!t", name = "Q", descriptor = "(I)V")
	public abstract void Q(@OriginalArg(0) int angle);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(Lclient!gr;[ZLclient!nk;IZIILclient!gr;ZII[I)V")
	private void method4896(@OriginalArg(0) AnimFrame arg0, @OriginalArg(1) boolean[] arg1, @OriginalArg(2) AnimBase arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean arg4, @OriginalArg(5) int arg5, @OriginalArg(7) AnimFrame arg6, @OriginalArg(8) boolean arg7, @OriginalArg(9) int arg8, @OriginalArg(10) int arg9, @OriginalArg(11) int[] arg10) {
		@Pc(11) int local11;
		if (arg0 == null || arg3 == 0) {
			for (local11 = 0; local11 < arg6.numTransformations; local11++) {
				@Pc(18) short local18 = arg6.baseIndices[local11];
				if (arg1 == null || arg1[local18] == arg7 || arg2.transformationType[local18] == 0) {
					@Pc(43) short local43 = arg6.labels[local11];
					if (local43 != -1) {
						this.method4888(0, arg10, 0, arg2.submeshes[local43] & arg9, arg5, arg4, arg2.labels[local43], 0, 0);
					}
					this.method4888(arg2.transformationType[local18], arg10, arg6.transformationZ[local11], arg9 & arg2.submeshes[local18], arg5, arg4, arg2.labels[local18], arg6.transformationY[local11], arg6.transformationX[local11]);
				}
			}
			return;
		}
		local11 = 0;
		@Pc(115) int local115 = 0;
		for (@Pc(117) int local117 = 0; local117 < arg2.numTransformation; local117++) {
			@Pc(121) boolean local121 = false;
			if (local11 < arg6.numTransformations && local117 == arg6.baseIndices[local11]) {
				local121 = true;
			}
			@Pc(139) boolean local139 = false;
			if (arg0.numTransformations > local115 && arg0.baseIndices[local115] == local117) {
				local139 = true;
			}
			if (local121 || local139) {
				if (arg1 == null || arg1[local117] == arg7 || arg2.transformationType[local117] == 0) {
					@Pc(181) short local181 = 0;
					@Pc(186) int local186 = arg2.transformationType[local117];
					if (local186 == 3 || local186 == 10) {
						local181 = 128;
					}
					@Pc(223) short local223;
					@Pc(203) short local203;
					@Pc(213) short local213;
					@Pc(218) short local218;
					@Pc(208) byte local208;
					if (local121) {
						local203 = arg6.transformationY[local11];
						local208 = arg6.tweeningProperties[local11];
						local213 = arg6.transformationZ[local11];
						local218 = arg6.labels[local11];
						local223 = arg6.transformationX[local11];
						local11++;
					} else {
						local208 = 0;
						local223 = local181;
						local213 = local181;
						local218 = -1;
						local203 = local181;
					}
					@Pc(246) short local246;
					@Pc(242) short local242;
					@Pc(248) short local248;
					@Pc(244) short local244;
					@Pc(240) byte local240;
					if (local139) {
						local244 = arg0.labels[local115];
						local248 = arg0.transformationZ[local115];
						local242 = arg0.transformationY[local115];
						local246 = arg0.transformationX[local115];
						local240 = arg0.tweeningProperties[local115];
						local115++;
					} else {
						local240 = 0;
						local242 = local181;
						local244 = -1;
						local246 = local181;
						local248 = local181;
					}
					if (local218 != -1) {
						this.method4888(0, arg10, 0, arg2.submeshes[local218] & arg9, arg5, arg4, arg2.labels[local218], 0, 0);
					} else if (local244 != -1) {
						this.method4888(0, arg10, 0, arg9 & arg2.submeshes[local244], arg5, arg4, arg2.labels[local244], 0, 0);
					}
					@Pc(386) int local386;
					@Pc(402) int local402;
					@Pc(412) int local412;
					if ((local208 & 0x2) == 0 && (local240 & 0x1) == 0) {
						@Pc(352) int local352;
						if (local186 == 2) {
							local352 = local246 - local223 & 0x3FFF;
							@Pc(359) int local359 = local242 - local203 & 0x3FFF;
							if (local352 >= 8192) {
								local352 -= 16384;
							}
							@Pc(372) int local372 = local248 - local213 & 0x3FFF;
							if (local359 >= 8192) {
								local359 -= 16384;
							}
							local386 = local223 + local352 * arg3 / arg8 & 0x3FFF;
							if (local372 >= 8192) {
								local372 -= 16384;
							}
							local402 = local203 + arg3 * local359 / arg8 & 0x3FFF;
							local412 = local213 + local372 * arg3 / arg8 & 0x3FFF;
						} else if (local186 == 9) {
							local352 = local246 - local223 & 0x3FFF;
							if (local352 >= 8192) {
								local352 -= 16384;
							}
							local412 = 0;
							local402 = 0;
							local386 = local352 * arg3 / arg8 + local223 & 0x3FFF;
						} else if (local186 == 7) {
							local352 = local246 - local223 & 0x3F;
							if (local352 >= 32) {
								local352 -= 64;
							}
							local402 = local203 + arg3 * (local242 - local203) / arg8;
							local412 = arg3 * (local248 - local213) / arg8 + local213;
							local386 = local352 * arg3 / arg8 + local223 & 0x3F;
						} else {
							local412 = local213 + arg3 * (local248 - local213) / arg8;
							local402 = local203 + (local242 - local203) * arg3 / arg8;
							local386 = local223 + (local246 - local223) * arg3 / arg8;
						}
					} else {
						local402 = local203;
						local386 = local223;
						local412 = local213;
					}
					this.method4888(local186, arg10, local412, arg2.submeshes[local117] & arg9, arg5, arg4, arg2.labels[local117], local402, local386);
				} else {
					if (local139) {
						local115++;
					}
					if (local121) {
						local11++;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!t", name = "I", descriptor = "(SS)V")
	public abstract void I(@OriginalArg(0) short src, @OriginalArg(1) short dst);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(ILclient!pt;[IZIIIILclient!pt;II)V")
	public final void method4897(@OriginalArg(0) int arg0, @OriginalArg(1) Class4_Sub1_Sub17 arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) boolean arg3, @OriginalArg(5) int arg4, @OriginalArg(7) int arg5, @OriginalArg(8) Class4_Sub1_Sub17 arg6, @OriginalArg(9) int arg7, @OriginalArg(10) int arg8) {
		if (arg4 == -1 || !this.w()) {
			return;
		}
		@Pc(17) AnimFrame local17 = arg6.aClass88Array1[arg4];
		@Pc(20) AnimBase local20 = local17.base;
		@Pc(30) AnimFrame local30 = null;
		if (arg1 != null) {
			local30 = arg1.aClass88Array1[arg0];
			if (local30.base != local20) {
				local30 = null;
			}
		}
		this.method4896(local30, null, local20, arg7, arg3, 0, local17, false, arg5, arg8, arg2);
		this.scaleWidth();
	}

	@OriginalMember(owner = "client!t", name = "WA", descriptor = "()I")
	public abstract int WA();

	@OriginalMember(owner = "client!t", name = "D", descriptor = "()I")
	public abstract int D();

	@OriginalMember(owner = "client!t", name = "w", descriptor = "()Z")
	protected abstract boolean w();

	@OriginalMember(owner = "client!t", name = "j", descriptor = "()V")
	protected abstract void scaleWidth();

	@OriginalMember(owner = "client!t", name = "U", descriptor = "()I")
	public abstract int U();

	@OriginalMember(owner = "client!t", name = "N", descriptor = "(IIII)V")
	protected abstract void N(@OriginalArg(0) int type, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z);

	@OriginalMember(owner = "client!t", name = "a", descriptor = "(IILclient!pt;ZIIILclient!pt;Lclient!pt;I[ZLclient!pt;III)V")
	public final void method4899(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class4_Sub1_Sub17 arg2, @OriginalArg(3) boolean arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) Class4_Sub1_Sub17 arg7, @OriginalArg(8) Class4_Sub1_Sub17 arg8, @OriginalArg(9) int arg9, @OriginalArg(10) boolean[] arg10, @OriginalArg(11) Class4_Sub1_Sub17 arg11, @OriginalArg(12) int arg12, @OriginalArg(13) int arg13) {
		if (arg0 == -1) {
			return;
		}
		if (arg10 == null || arg6 == -1) {
			this.method4883(0, arg1, arg3, arg0, arg13, arg8, arg11, arg12);
		} else if (this.w()) {
			@Pc(38) AnimFrame local38 = arg8.aClass88Array1[arg0];
			@Pc(41) AnimBase local41 = local38.base;
			@Pc(43) AnimFrame local43 = null;
			if (arg11 != null) {
				local43 = arg11.aClass88Array1[arg13];
				if (local43.base != local41) {
					local43 = null;
				}
			}
			this.method4896(local43, arg10, local41, arg12, arg3, 0, local38, false, arg1, 65535, null);
			@Pc(75) AnimFrame local75 = arg7.aClass88Array1[arg6];
			@Pc(77) AnimFrame local77 = null;
			if (arg2 != null) {
				local77 = arg2.aClass88Array1[arg9];
				if (local41 != local77.base) {
					local77 = null;
				}
			}
			this.applyTransformUnmasked(0, new int[0], 0, 0, 0, 0, arg3);
			this.method4896(local77, arg10, local75.base, arg5, arg3, 0, local75, true, arg4, 65535, null);
			this.scaleWidth();
		}
	}

@OriginalMember(owner = "client!t", name = "l", descriptor = "I")
	public static int anInt6286;
@OriginalMember(owner = "client!t", name = "n", descriptor = "[[I")
	public static int[][] anIntArrayArray50;
@OriginalMember(owner = "client!t", name = "b", descriptor = "I")
	public static int anInt6277 = 0;
@OriginalMember(owner = "client!t", name = "d", descriptor = "Z")
	public static boolean aBoolean573 = false;
@OriginalMember(owner = "client!t", name = "m", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_286 = new Class22(9, 6);
@OriginalMember(owner = "client!t", name = "o", descriptor = "I")
	public static int anInt6287 = 0;
@OriginalMember(owner = "client!t", name = "a", descriptor = "(IZLjava/awt/Component;)Lclient!jn;")
	public static Preference method4882(@OriginalArg(2) Component arg0) {
		return new MouseManager(arg0, true);
	}
@OriginalMember(owner = "client!t", name = "a", descriptor = "(IIIIIIIIII)V")
	public static void method4895(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		if (arg6 >= ItemType.anInt6186 && arg6 <= Class36.anInt2656 && ItemType.anInt6186 <= arg4 && Class36.anInt2656 >= arg4 && ItemType.anInt6186 <= arg1 && Class36.anInt2656 >= arg1 && ItemType.anInt6186 <= arg0 && Class36.anInt2656 >= arg0 && anInt6287 <= arg8 && arg8 <= GlCubeTexture.anInt3256 && arg2 >= anInt6287 && GlCubeTexture.anInt3256 >= arg2 && anInt6287 <= arg5 && arg5 <= GlCubeTexture.anInt3256 && arg3 >= anInt6287 && GlCubeTexture.anInt3256 >= arg3) {
			Class56.method1468(arg8, arg7, arg3, arg2, arg5, arg0, arg4, arg6, arg1);
		} else {
			Class16_Sub2.method3579(arg0, arg7, arg4, arg5, arg6, arg2, arg3, arg1, arg8);
		}
	}
}
