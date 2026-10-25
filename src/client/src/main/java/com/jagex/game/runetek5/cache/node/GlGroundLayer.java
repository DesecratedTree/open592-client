package com.jagex.game.runetek5.cache.node;

import jaggl.OpenGL;
import jaclib.memory.NativeBuffer;
import jaclib.memory.Stream;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;

import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.cache.node.*;
import com.jagex.game.runetek5.PcmPlayerThread;
@OriginalClass("client!dw")
public final class GlGroundLayer extends Country {

	@OriginalMember(owner = "client!dw", name = "l", descriptor = "Lclient!jaggl/memory/NativeBuffer;")
	private NativeBuffer aNativeBuffer1;

	@OriginalMember(owner = "client!dw", name = "s", descriptor = "Lclient!jaggl/memory/NativeStream;")
	private Stream aNativeStream1;

	@OriginalMember(owner = "client!dw", name = "u", descriptor = "Lclient!pc;")
	private Class177 colourDrawBuffer;

	@OriginalMember(owner = "client!dw", name = "o", descriptor = "Lclient!oq;")
	private final GlGround glGround;

	@OriginalMember(owner = "client!dw", name = "p", descriptor = "I")
	public final int anInt1640;

	@OriginalMember(owner = "client!dw", name = "D", descriptor = "I")
	public final int anInt1647;

	@OriginalMember(owner = "client!dw", name = "C", descriptor = "F")
	public final float textureScale;

	@OriginalMember(owner = "client!dw", name = "y", descriptor = "Lclient!fd;")
	private final GlToolkit aClass19_Sub2_11;

	@OriginalMember(owner = "client!dw", name = "q", descriptor = "I")
	public final int anInt1641;

	@OriginalMember(owner = "client!dw", name = "x", descriptor = "I")
	public final int waterDepth;

	@OriginalMember(owner = "client!dw", name = "A", descriptor = "[I")
	private final int[] tileTriangleMasks;

	@OriginalMember(owner = "client!dw", name = "I", descriptor = "Lclient!eo;")
	private final Class64_Sub1 aClass64_Sub1_1;

	@OriginalMember(owner = "client!dw", name = "<init>", descriptor = "(Lclient!oq;IIIII)V")
	public GlGroundLayer(@OriginalArg(0) GlGround glGround, @OriginalArg(1) int arg1, @OriginalArg(2) int textureScale, @OriginalArg(3) int arg3, @OriginalArg(4) int waterDepth, @OriginalArg(5) int waterIntensity) {
		this.glGround = glGround;
		this.anInt1640 = waterDepth;
		this.anInt1647 = arg1;
		this.textureScale = textureScale;
		this.aClass19_Sub2_11 = this.glGround.glToolkit;
		this.anInt1641 = arg3;
		this.waterDepth = waterIntensity;
		this.tileTriangleMasks = new int[this.glGround.anInt5262 * this.glGround.anInt5258];
		this.aClass64_Sub1_1 = new Class64_Sub1(this.aClass19_Sub2_11, 5123, null, 1);
	}

	@OriginalMember(owner = "client!dw", name = "a", descriptor = "(II)V")
	public void finishColours(@OriginalArg(0) int arg0) {
		this.aNativeStream1.b();
		@Pc(18) ElementsBufferGL local18 = this.aClass19_Sub2_11.method1902(this.aNativeBuffer1, arg0 * 4, 4);
		this.colourDrawBuffer = new Class177(local18, 5121, 4, 0);
		this.aNativeStream1 = null;
		this.aNativeBuffer1 = null;
	}

	@OriginalMember(owner = "client!dw", name = "a", descriptor = "([III)V")
	public void method1462(@OriginalArg(0) int[] arg0, @OriginalArg(1) int arg1) {
		@Pc(7) int local7 = 0;
		@Pc(9) Packet local9 = PcmPlayerThread.aClass4_Sub12_7;
		local9.currentPosition = 0;
		@Pc(36) short[] local36;
		@Pc(30) int local30;
		@Pc(41) int local41;
		@Pc(24) int local24;
		@Pc(50) int local50;
		@Pc(52) int local52;
		if (this.aClass19_Sub2_11.bigEndianess) {
			for (local24 = 0; local24 < arg1; local24++) {
				local30 = arg0[local24];
				local36 = this.glGround.tileVertexIndices[local30];
				local41 = this.tileTriangleMasks[local30];
				if (local41 != 0 && local36 != null) {
					local50 = 0;
					local52 = 0;
					while (local52 < local36.length) {
						if ((local41 & 0x1 << local50++) == 0) {
							local52 += 3;
						} else {
							local7++;
							local9.method2524(local36[local52++] & 0xFFFF);
							local7++;
							local9.method2524(local36[local52++] & 0xFFFF);
							local9.method2524(local36[local52++] & 0xFFFF);
							local7++;
						}
					}
				}
			}
		} else {
			for (local24 = 0; local24 < arg1; local24++) {
				local30 = arg0[local24];
				local41 = this.tileTriangleMasks[local30];
				local36 = this.glGround.tileVertexIndices[local30];
				if (local41 != 0 && local36 != null) {
					local50 = 0;
					local52 = 0;
					while (local36.length > local52) {
						if ((0x1 << local50++ & local41) == 0) {
							local52 += 3;
						} else {
							local9.method2541(local36[local52++] & 0xFFFF);
							local7++;
							local9.method2541(local36[local52++] & 0xFFFF);
							local7++;
							local7++;
							local9.method2541(local36[local52++] & 0xFFFF);
						}
					}
				}
			}
		}
		if (local7 <= 0) {
			return;
		}
		this.aClass64_Sub1_1.method3657(local9.buffer, local9.currentPosition);
		this.aClass19_Sub2_11.method1934(this.colourDrawBuffer, this.glGround.aClass177_16, this.glGround.aClass177_14, this.glGround.aClass177_15);
		this.aClass19_Sub2_11.method1926((this.glGround.featureFlags & 0x7) != 0, this.anInt1647, (this.glGround.featureFlags & 0x8) != 0);
		if (this.aClass19_Sub2_11.aBoolean192) {
			this.aClass19_Sub2_11.GA(Integer.MAX_VALUE, this.anInt1641, this.anInt1640, this.waterDepth);
		}
		OpenGL.glMatrixMode(5890);
		OpenGL.glPushMatrix();
		OpenGL.glScalef(1.0F / this.textureScale, 1.0F / this.textureScale, 1.0F);
		OpenGL.glMatrixMode(5888);
		this.aClass19_Sub2_11.method1934(this.colourDrawBuffer, this.glGround.aClass177_16, this.glGround.aClass177_14, this.glGround.aClass177_15);
		// 592HD: the terrain is a receiver on its live draw too. This method is
		// the pass the scene submits every frame (method4029 -> method4034 ->
		// here, one call per layer); the top-down map bake is GlGround.method4025
		// and draws its own indices, so it never reaches this method.
		//
		// The buffer holds scene units - GlToolkit.createGround builds the pass
		// with 128 vertices per tile, so x and z are already the coordinates a
		// model at that spot reports - and the pass draws it under the camera's
		// own matrices, method1952's rotX(180) * V. Both of the seam's inputs
		// therefore come from the drawn vertex and the live camera: tile-to-scene
		// is the identity (the window starts at tile 0 because this pass draws
		// the whole scene), and scene-to-eye is read from aClass31_Sub1_4.
		// The caustics row's depth is this layer's own stock value - the byte
		// Terrain reads per tile for an underwater region and GA passed to the
		// water effect as depth / 255 - so the pattern is scaled by exactly the
		// water the client already knows stands here, and a dry tile is DRY.
		boolean hdLighting = this.aClass19_Sub2_11.hdBeginGroundLighting(this.anInt1647 >= 0,
			this.glGround.anInt5261, 0, 0, HdGroundWater.of(this.waterDepth), this.anInt1647,
			this.textureScale, this.glGround.hdTileMaterialsTexture());
		try {
			this.aClass19_Sub2_11.method1967(0, local7, this.aClass64_Sub1_1);
		} finally {
			if (hdLighting) {
				this.aClass19_Sub2_11.hdEndModelLighting();
			}
		}
		OpenGL.glMatrixMode(5890);
		OpenGL.glPopMatrix();
		OpenGL.glMatrixMode(5888);
	}

	@OriginalMember(owner = "client!dw", name = "a", descriptor = "(IIIZ)V")
	public void addTriangle(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int tileZ) {
		this.tileTriangleMasks[this.glGround.anInt5262 * arg0 + tileZ] |= 0x1 << arg1;
	}

	@OriginalMember(owner = "client!dw", name = "a", descriptor = "(IIBIF)V")
	public void setVertexColour(@OriginalArg(0) int arg0, @OriginalArg(1) int vertex, @OriginalArg(3) int arg2, @OriginalArg(4) float colour) {
		@Pc(23) int local23;
		@Pc(45) int local45;
		if (this.anInt1647 != -1) {
			@Pc(18) MaterialType local18 = this.aClass19_Sub2_11.materialProvider.getMetrics(this.anInt1647);
			local23 = local18.aByte90 & 0xFF;
			@Pc(67) int local67;
			if (local23 != 0 && local18.aByte89 != 4) {
				if (arg0 < 0) {
					local45 = 0;
				} else if (arg0 <= 127) {
					local45 = arg0 * 131586;
				} else {
					local45 = 16777215;
				}
				if (local23 == 256) {
					arg2 = local45;
				} else {
					local67 = 256 - local23;
					arg2 = (local67 * (arg2 & 0xFF00FF) + (local45 & 0xFF00FF) * local23 & 0xFF00FF00) + (local67 * (arg2 & 0xFF00) + local23 * (local45 & 0xFF00) & 0xFF0000) >> 8;
				}
			}
			local45 = local18.aByte87 & 0xFF;
			if (local45 != 0) {
				local45 += 256;
				@Pc(114) int local114 = (arg2 >> 16 & 0xFF) * local45;
				if (local114 > 65535) {
					local114 = 65535;
				}
				local67 = local45 * (arg2 >> 8 & 0xFF);
				if (local67 > 65535) {
					local67 = 65535;
				}
				@Pc(140) int local140 = local45 * (arg2 & 0xFF);
				if (local140 > 65535) {
					local140 = 65535;
				}
				arg2 = (local140 >> 8) + (local67 & 0xFF00) + ((local114 & 0xFF00) << 8);
			}
		}
		this.aNativeStream1.a(vertex * 4);
		if (colour != 1.0F) {
			@Pc(182) int local182 = arg2 >> 16 & 0xFF;
			local23 = arg2 >> 8 & 0xFF;
			local182 = (int) ((float) local182 * colour);
			local45 = arg2 & 0xFF;
			if (local182 < 0) {
				local182 = 0;
			} else if (local182 > 255) {
				local182 = 255;
			}
			local23 = (int) ((float) local23 * colour);
			if (local23 < 0) {
				local23 = 0;
			} else if (local23 > 255) {
				local23 = 255;
			}
			local45 = (int) ((float) local45 * colour);
			if (local45 < 0) {
				local45 = 0;
			} else if (local45 > 255) {
				local45 = 255;
			}
			arg2 = local45 | local182 << 16 | local23 << 8;
		}
		this.aNativeStream1.b((byte) (arg2 >> 16));
		this.aNativeStream1.b((byte) (arg2 >> 8));
		this.aNativeStream1.b((byte) arg2);
	}

	@OriginalMember(owner = "client!dw", name = "b", descriptor = "(II)V")
	public void initColours(@OriginalArg(1) int arg0) {
		this.aNativeBuffer1 = this.aClass19_Sub2_11.aNativeHeap2.a(arg0 * 4);
		this.aNativeStream1 = new Stream(this.aNativeBuffer1);
	}

	@OriginalMember(owner = "client!dw", name = "a", descriptor = "(IZ)V")
	public void setVertexOpaque(@OriginalArg(0) int arg0) {
		this.aNativeStream1.a(arg0 * 4 + 3);
		this.aNativeStream1.b(-1);
	}

@OriginalMember(owner = "client!dw", name = "z", descriptor = "Lclient!sb;")
	public static HelveticaFont aClass212_2;
@OriginalMember(owner = "client!dw", name = "G", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_2;
@OriginalMember(owner = "client!dw", name = "r", descriptor = "I")
	public static int anInt1642 = -1;
@OriginalMember(owner = "client!dw", name = "t", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_17 = new Class215(9, -1);
@OriginalMember(owner = "client!dw", name = "H", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_33 = new Class83("Attack", "Angreifen", "Attaquer", "Atacar");
@OriginalMember(owner = "client!dw", name = "a", descriptor = "(IBI)I")
	public static int method1460(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(16) int local16 = arg0 - 1 & arg1 >> 31;
		return local16 + (arg1 + (arg1 >>> 31)) % arg0;
	}
}
