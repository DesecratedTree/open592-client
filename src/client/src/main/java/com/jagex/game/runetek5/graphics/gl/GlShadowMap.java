package com.jagex.game.runetek5.graphics.gl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.scene.ground.GlGround;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class62;

import com.jagex.game.runetek5.graphics.gl.*;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.EnvironmentSampler;
@OriginalClass("client!uf")
public final class GlShadowMap {

	@OriginalMember(owner = "client!uf", name = "g", descriptor = "[[Lclient!kh;")
	private ShadowTileGL[][] aClass125ArrayArray1;

	@OriginalMember(owner = "client!uf", name = "c", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!uf", name = "o", descriptor = "Lclient!oq;")
	private final GlGround glGround;

	@OriginalMember(owner = "client!uf", name = "j", descriptor = "I")
	public final int width;

	@OriginalMember(owner = "client!uf", name = "b", descriptor = "I")
	private final int length;

	@OriginalMember(owner = "client!uf", name = "l", descriptor = "I")
	private final int sizeShift;

	@OriginalMember(owner = "client!uf", name = "d", descriptor = "[B")
	public final byte[] shadowMapperData;

	@OriginalMember(owner = "client!uf", name = "m", descriptor = "I")
	private final int sizeX;

	@OriginalMember(owner = "client!uf", name = "i", descriptor = "I")
	private final int sizeY;

	@OriginalMember(owner = "client!uf", name = "<init>", descriptor = "(Lclient!fd;Lclient!oq;)V")
	public GlShadowMap(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) GlGround glGround) {
		this.glToolkit = glToolkit;
		this.glGround = glGround;
		this.width = (this.glGround.anInt5262 * this.glGround.anInt5261 >> this.glToolkit.shadowScaleShift) + 2;
		this.length = (this.glGround.anInt5261 * this.glGround.anInt5258 >> this.glToolkit.shadowScaleShift) + 2;
		this.sizeShift = this.glToolkit.shadowScaleShift + 7 - this.glGround.anInt5256;
		this.shadowMapperData = new byte[this.width * this.length];
		this.sizeX = this.glGround.anInt5262 >> this.sizeShift;
		this.sizeY = this.glGround.anInt5258 >> this.sizeShift;
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(BLclient!ba;II)Z")
	public boolean method5190(@OriginalArg(1) Shadow arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(6) GlShadow local6 = (GlShadow) arg0;
		arg2 += local6.anInt2727 + 1;
		arg1 += local6.anInt2725 + 1;
		@Pc(32) int local32 = this.width * arg1 + arg2;
		@Pc(35) int local35 = local6.anInt2722;
		@Pc(38) int local38 = local6.sizeX;
		@Pc(48) int local48;
		if (arg1 <= 0) {
			local48 = 1 - arg1;
			arg1 = 1;
			local35 -= local48;
			local32 += local48 * this.width;
		}
		@Pc(67) int local67 = this.width - local38;
		if (local35 + arg1 >= this.length) {
			local48 = local35 + arg1 + 1 - this.length;
			local35 -= local48;
		}
		if (arg2 <= 0) {
			local48 = 1 - arg2;
			local67 += local48;
			local32 += local48;
			local38 -= local48;
			arg2 = 1;
		}
		if (arg2 + local38 >= this.width) {
			local48 = local38 + arg2 + 1 - this.width;
			local67 += local48;
			local38 -= local48;
		}
		if (local38 > 0 && local35 > 0) {
			local67 += this.width * 7;
			return Class4_Sub3.method184(local32, local38, this.shadowMapperData, local67, local35);
		} else {
			return false;
		}
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(ZII[[ZIZ)V")
	public void renderShadowOverlay(@OriginalArg(0) boolean belowLowestPlane, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean[][] arg3, @OriginalArg(4) int arg4) {
		this.glToolkit.method1906(false);
		this.glToolkit.method1911(false);
		this.glToolkit.method1957(-2);
		this.glToolkit.method1900(1);
		this.glToolkit.method1958(1);
		@Pc(39) float local39 = 1.0F / (float) (this.glToolkit.shadowScale * 128);
		@Pc(43) int local43;
		@Pc(50) int local50;
		@Pc(57) int local57;
		@Pc(59) int local59;
		@Pc(66) int local66;
		@Pc(73) int local73;
		@Pc(75) int local75;
		if (belowLowestPlane) {
			for (local43 = 0; local43 < this.sizeY; local43++) {
				local50 = local43 << this.sizeShift;
				local57 = local43 + 1 << this.sizeShift;
				label128: for (local59 = 0; local59 < this.sizeX; local59++) {
					local66 = local59 << this.sizeShift;
					local73 = local59 + 1 << this.sizeShift;
					for (local75 = local66; local75 < local73; local75++) {
						if (local75 - arg2 >= -arg4 && arg4 >= local75 - arg2) {
							for (@Pc(99) int local99 = local50; local99 < local57; local99++) {
								if (-arg4 <= local99 - arg1 && arg4 >= local99 - arg1 && arg3[arg4 + local75 - arg2][arg4 + local99 - arg1]) {
									OpenGL.glMatrixMode(5890);
									OpenGL.glLoadIdentity();
									OpenGL.glScalef(local39, local39, 1.0F);
									OpenGL.glTranslatef((float) -local59 / local39, (float) -local43 / local39, 1.0F);
									OpenGL.glMatrixMode(5888);
									this.aClass125ArrayArray1[local59][local43].drawShadow();
									continue label128;
								}
							}
						}
					}
				}
			}
		} else {
			for (local43 = 0; local43 < this.sizeY; local43++) {
				local50 = local43 << this.sizeShift;
				local57 = local43 + 1 << this.sizeShift;
				for (local59 = 0; local59 < this.sizeX; local59++) {
					local66 = 0;
					local73 = local59 << this.sizeShift;
					local75 = local59 + 1 << this.sizeShift;
					@Pc(219) Packet local219 = PcmPlayerThread.aClass4_Sub12_7;
					local219.currentPosition = 0;
					for (@Pc(224) int local224 = local50; local224 < local57; local224++) {
						if (local224 - arg1 >= -arg4 && arg4 >= local224 - arg1) {
							@Pc(251) int local251 = local73 + this.glGround.anInt5262 * local224;
							for (@Pc(253) int local253 = local73; local253 < local75; local253++) {
								if (-arg4 <= local253 - arg2 && arg4 >= local253 - arg2 && arg3[arg4 + local253 - arg2][arg4 + local224 - arg1]) {
									@Pc(297) short[] local297 = this.glGround.tileVertexIndices[local251];
									if (local297 != null) {
										@Pc(305) int local305;
										if (this.glToolkit.bigEndianess) {
											for (local305 = 0; local305 < local297.length; local305++) {
												local66++;
												local219.method2524(local297[local305] & 0xFFFF);
											}
										} else {
											for (local305 = 0; local305 < local297.length; local305++) {
												local66++;
												local219.method2541(local297[local305] & 0xFFFF);
											}
										}
									}
								}
								local251++;
							}
						}
					}
					if (local66 > 0) {
						OpenGL.glMatrixMode(5890);
						OpenGL.glLoadIdentity();
						OpenGL.glScalef(local39, local39, 1.0F);
						OpenGL.glTranslatef((float) -local59 / local39, (float) -local43 / local39, 1.0F);
						OpenGL.glMatrixMode(5888);
						this.aClass125ArrayArray1[local59][local43].method3190(local219.buffer, local66);
					}
				}
			}
		}
		OpenGL.glMatrixMode(5890);
		OpenGL.glLoadIdentity();
		OpenGL.glMatrixMode(5888);
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(IIIII)V")
	private void invalidateShadowTiles(@OriginalArg(0) int regionHeight, @OriginalArg(1) int regionWidth, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		if (this.aClass125ArrayArray1 == null) {
			return;
		}
		@Pc(15) int local15 = regionWidth - 1 >> 7;
		@Pc(25) int local25 = regionHeight + regionWidth - 1 - 1 >> 7;
		@Pc(31) int local31 = arg2 - 1 >> 7;
		@Pc(41) int local41 = arg2 + arg3 - 1 - 1 >> 7;
		for (@Pc(43) int local43 = local15; local43 <= local25; local43++) {
			@Pc(50) ShadowTileGL[] local50 = this.aClass125ArrayArray1[local43];
			for (@Pc(52) int local52 = local31; local52 <= local41; local52++) {
				local50[local52].needsTextureRegeneration = true;
			}
		}
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(I)V")
	public void initializeShadowTiles() {
		this.aClass125ArrayArray1 = new ShadowTileGL[this.sizeX][this.sizeY];
		for (@Pc(24) int local24 = 0; local24 < this.sizeY; local24++) {
			for (@Pc(28) int local28 = 0; local28 < this.sizeX; local28++) {
				this.aClass125ArrayArray1[local28][local24] = new ShadowTileGL(this.glToolkit, this, this.glGround, local28, local24, this.sizeShift, local28 * 128 + 1, local24 * 128 + 1);
			}
		}
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(IILclient!ba;B)V")
	public void method5194(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Shadow arg2) {
		@Pc(6) GlShadow local6 = (GlShadow) arg2;
		arg0 += local6.anInt2725 + 1;
		arg1 += local6.anInt2727 + 1;
		@Pc(27) int local27 = arg0 * this.width + arg1;
		@Pc(29) int local29 = 0;
		@Pc(32) int local32 = local6.anInt2722;
		@Pc(35) int local35 = local6.sizeX;
		@Pc(41) int local41 = this.width - local35;
		@Pc(48) int local48;
		if (arg0 <= 0) {
			local48 = 1 - arg0;
			local29 = local48 * local35;
			local27 += local48 * this.width;
			arg0 = 1;
			local32 -= local48;
		}
		@Pc(69) int local69 = 0;
		if (arg0 + local32 >= this.length) {
			local48 = local32 + arg0 + 1 - this.length;
			local32 -= local48;
		}
		if (arg1 <= 0) {
			local48 = 1 - arg1;
			local69 = local48;
			local35 -= local48;
			local27 += local48;
			local41 += local48;
			arg1 = 1;
			local29 += local48;
		}
		if (arg1 + local35 >= this.width) {
			local48 = arg1 + local35 + 1 - this.width;
			local69 += local48;
			local41 += local48;
			local35 -= local48;
		}
		if (local35 > 0 && local32 > 0) {
			EnvironmentSampler.method3231(local27, local6.shade, local41, this.shadowMapperData, local29, local35, local69, local32);
			this.invalidateShadowTiles(local35, arg1, arg0, local32);
		}
	}

	@OriginalMember(owner = "client!uf", name = "a", descriptor = "(IIILclient!ba;)V")
	public void method5195(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) Shadow arg2) {
		@Pc(6) GlShadow local6 = (GlShadow) arg2;
		arg1 += local6.anInt2727 + 1;
		arg0 += local6.anInt2725 + 1;
		@Pc(28) int local28 = arg1 + this.width * arg0;
		@Pc(30) int local30 = 0;
		@Pc(33) int local33 = local6.anInt2722;
		@Pc(36) int local36 = local6.sizeX;
		@Pc(42) int local42 = this.width - local36;
		@Pc(63) int local63;
		if (arg0 <= 0) {
			local63 = 1 - arg0;
			local33 -= local63;
			arg0 = 1;
			local30 = local36 * local63;
			local28 += local63 * this.width;
		}
		@Pc(84) int local84 = 0;
		if (this.length <= arg0 + local33) {
			local63 = arg0 + local33 + 1 - this.length;
			local33 -= local63;
		}
		if (arg1 <= 0) {
			local63 = 1 - arg1;
			local36 -= local63;
			arg1 = 1;
			local42 += local63;
			local84 = local63;
			local30 += local63;
			local28 += local63;
		}
		if (this.width <= arg1 + local36) {
			local63 = arg1 + local36 + 1 - this.width;
			local36 -= local63;
			local42 += local63;
			local84 += local63;
		}
		if (local36 > 0 && local33 > 0) {
			Class4_Sub6_Sub35.method5213(local33, local28, this.shadowMapperData, local36, local30, local84, local42, local6.shade);
			this.invalidateShadowTiles(local36, arg1, arg0, local33);
		}
	}

@OriginalMember(owner = "client!uf", name = "t", descriptor = "Lclient!ek;")
	public static Class62 aClass62_1;
@OriginalMember(owner = "client!uf", name = "e", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_89 = new Class215(20, 8);
@OriginalMember(owner = "client!uf", name = "p", descriptor = "I")
	public static int anInt6639 = 0;
@OriginalMember(owner = "client!uf", name = "r", descriptor = "Lclient!bg;")
	public static final Class22 ALIGN_CENTER = new Class22(88, -1);
@OriginalMember(owner = "client!uf", name = "s", descriptor = "Z")
	public static boolean aBoolean600 = false;
}
