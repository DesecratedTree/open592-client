package com.jagex.game.runetek5;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.deob.Class31_Sub2;
import com.jagex.game.runetek5.deob.Class57_Sub1_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Static347;
import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.deob.ia;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.graphics.texture.SafeModeTexture2D;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;

import com.jagex.game.runetek5.JavaModel;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class16_Sub2;
@OriginalClass("client!bc")
public final class JavaToolkit extends com.jagex.game.runetek5.graphics.Toolkit {

	@OriginalMember(owner = "client!bc", name = "t", descriptor = "Ljava/awt/Canvas;")
	private Canvas canvas;

	@OriginalMember(owner = "client!bc", name = "w", descriptor = "I")
	private int lastTickTime;

	@OriginalMember(owner = "client!bc", name = "x", descriptor = "Lclient!kd;")
	private EnvironmentContext aClass4_Sub22_1;

	@OriginalMember(owner = "client!bc", name = "y", descriptor = "I")
	public int sunX;

	@OriginalMember(owner = "client!bc", name = "B", descriptor = "[I")
	public int[] surfaceRaster;

	@OriginalMember(owner = "client!bc", name = "C", descriptor = "[Lclient!cv;")
	private JavaThreadResource[] resources;

	@OriginalMember(owner = "client!bc", name = "J", descriptor = "I")
	public int viewY2;

	@OriginalMember(owner = "client!bc", name = "L", descriptor = "F")
	private float aFloat12;

	@OriginalMember(owner = "client!bc", name = "O", descriptor = "I")
	public int sunY;

	@OriginalMember(owner = "client!bc", name = "R", descriptor = "I")
	public int viewY1;

	@OriginalMember(owner = "client!bc", name = "U", descriptor = "I")
	private int surfaceHeight;

	@OriginalMember(owner = "client!bc", name = "V", descriptor = "I")
	public int sunZ;

	@OriginalMember(owner = "client!bc", name = "W", descriptor = "I")
	public int threadCount;

	@OriginalMember(owner = "client!bc", name = "Z", descriptor = "I")
	public int anInt422;

	@OriginalMember(owner = "client!bc", name = "ab", descriptor = "I")
	public int viewX1;

	@OriginalMember(owner = "client!bc", name = "bb", descriptor = "F")
	private float aFloat13;

	@OriginalMember(owner = "client!bc", name = "cb", descriptor = "I")
	public int projectionCenterX;

	@OriginalMember(owner = "client!bc", name = "db", descriptor = "Z")
	public boolean aBoolean44;

	@OriginalMember(owner = "client!bc", name = "ib", descriptor = "I")
	public int viewX2;

	@OriginalMember(owner = "client!bc", name = "jb", descriptor = "Lclient!l;")
	private NativeSprite aClass57_5;

	@OriginalMember(owner = "client!bc", name = "nb", descriptor = "I")
	public int anInt430;

	@OriginalMember(owner = "client!bc", name = "ob", descriptor = "Lclient!l;")
	private NativeSprite cachedBillboardSprite;

	@OriginalMember(owner = "client!bc", name = "s", descriptor = "Z")
	private boolean stopped;

	@OriginalMember(owner = "client!bc", name = "u", descriptor = "Z")
	private boolean colourTables;

	@OriginalMember(owner = "client!bc", name = "v", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_3;

	@OriginalMember(owner = "client!bc", name = "A", descriptor = "I")
	public int anInt403;

	@OriginalMember(owner = "client!bc", name = "G", descriptor = "I")
	public int anInt406;

	@OriginalMember(owner = "client!bc", name = "D", descriptor = "I")
	public int ambient;

	@OriginalMember(owner = "client!bc", name = "H", descriptor = "Z")
	public boolean aBoolean43;

	@OriginalMember(owner = "client!bc", name = "M", descriptor = "I")
	public int anInt410;

	@OriginalMember(owner = "client!bc", name = "T", descriptor = "I")
	public int anInt417;

	@OriginalMember(owner = "client!bc", name = "E", descriptor = "I")
	private int anInt405;

	@OriginalMember(owner = "client!bc", name = "z", descriptor = "I")
	public int clipX2;

	@OriginalMember(owner = "client!bc", name = "eb", descriptor = "I")
	public int clipY1;

	@OriginalMember(owner = "client!bc", name = "S", descriptor = "I")
	public int anInt416;

	@OriginalMember(owner = "client!bc", name = "K", descriptor = "I")
	public int anInt409;

	@OriginalMember(owner = "client!bc", name = "X", descriptor = "I")
	public int clipY2;

	@OriginalMember(owner = "client!bc", name = "I", descriptor = "I")
	private int anInt407;

	@OriginalMember(owner = "client!bc", name = "hb", descriptor = "I")
	public int sunIntensity;

	@OriginalMember(owner = "client!bc", name = "Q", descriptor = "I")
	private int anInt414;

	@OriginalMember(owner = "client!bc", name = "P", descriptor = "I")
	public int reverseSunIntensity;

	@OriginalMember(owner = "client!bc", name = "fb", descriptor = "I")
	public int anInt426;

	@OriginalMember(owner = "client!bc", name = "N", descriptor = "I")
	public int anInt411;

	@OriginalMember(owner = "client!bc", name = "mb", descriptor = "I")
	private int anInt429;

	@OriginalMember(owner = "client!bc", name = "lb", descriptor = "I")
	public int clipX1;

	@OriginalMember(owner = "client!bc", name = "gb", descriptor = "Z")
	private boolean shrinkTextures;

	@OriginalMember(owner = "client!bc", name = "F", descriptor = "Lclient!fa;")
	private final LRUCache spriteCache;

	@OriginalMember(owner = "client!bc", name = "pb", descriptor = "I")
	private int anInt431;

	@OriginalMember(owner = "client!bc", name = "kb", descriptor = "Lclient!fa;")
	private final LRUCache textureCache;

	@OriginalMember(owner = "client!bc", name = "Y", descriptor = "Lclient!wp;")
	public Class31_Sub2 aClass31_Sub2_1;

	@OriginalMember(owner = "client!bc", name = "<init>", descriptor = "(ILclient!ga;)V")
	private JavaToolkit(@OriginalArg(0) int arg0, @OriginalArg(1) unpack_Sub1 arg1) {
		super(arg0, arg1);
		this.stopped = false;
		this.colourTables = false;
		this.aClass102_3 = new CubeMapSpecularEffect(4);
		this.anInt403 = 3500;
		this.anInt406 = 50;
		this.ambient = 75518;
		this.aBoolean43 = false;
		this.anInt410 = 0;
		this.anInt417 = 512;
		this.anInt405 = 3500;
		this.clipX2 = 0;
		this.clipY1 = 0;
		this.anInt416 = 512;
		this.anInt409 = 0;
		this.clipY2 = 0;
		this.anInt407 = 0;
		this.sunIntensity = 45823;
		this.anInt414 = 0;
		this.reverseSunIntensity = 78642;
		this.anInt426 = 0;
		this.anInt411 = this.anInt405 - 255;
		this.anInt429 = 0;
		this.clipX1 = 0;
		this.shrinkTextures = false;
		this.spriteCache = new LRUCache(16);
		this.anInt431 = -1;
		this.textureCache = new LRUCache(20);
		this.aClass31_Sub2_1 = new Class31_Sub2();
		this.allocateThreads(1);
		this.linkThreads(0);
		Static347.method4678(true, false);
		this.colourTables = true;
	}

	@OriginalMember(owner = "client!bc", name = "<init>", descriptor = "(ILjava/awt/Canvas;Lclient!ga;)V")
	public JavaToolkit(@OriginalArg(0) int arg0, @OriginalArg(1) Canvas arg1, @OriginalArg(2) unpack_Sub1 arg2) {
		this(arg0, arg2);
		this.method4249(arg1);
		this.setCanvas(arg1);
	}

	@OriginalMember(owner = "client!bc", name = "YA", descriptor = "()I")
	@Override
	public int YA() {
		return this.anInt405;
	}

	@OriginalMember(owner = "client!bc", name = "C", descriptor = "()Z")
	@Override
	public boolean method4301() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "z", descriptor = "()Z")
	@Override
	public boolean method4291() {
		return true;
	}

	@OriginalMember(owner = "client!bc", name = "H", descriptor = "(IIII)V")
	@Override
	public void H(@OriginalArg(0) int projectionCenterX, @OriginalArg(1) int projectionCenterY, @OriginalArg(2) int projectionScaleX, @OriginalArg(3) int projectionScaleY) {
		this.projectionCenterX = projectionCenterX;
		this.anInt422 = projectionCenterY;
		this.anInt416 = projectionScaleX;
		this.anInt417 = projectionScaleY;
		this.updateViewport();
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIIILclient!ma;II)V")
	@Override
	public void drawSurfaceLine(@OriginalArg(0) int startX, @OriginalArg(1) int startY, @OriginalArg(2) int endX, @OriginalArg(3) int endY, @OriginalArg(4) int colour, @OriginalArg(6) ComponentSurface arg5, @OriginalArg(7) int clippingMask, @OriginalArg(8) int surfaceClipLeft) {
		@Pc(2) Class145_Sub2 local2 = (Class145_Sub2) arg5;
		@Pc(5) int[] local5 = local2.anIntArray554;
		@Pc(8) int[] local8 = local2.anIntArray553;
		@Pc(17) int local17 = this.clipY1 > surfaceClipLeft ? this.clipY1 : surfaceClipLeft;
		@Pc(32) int local32 = this.clipY2 < surfaceClipLeft + local5.length ? this.clipY2 : surfaceClipLeft + local5.length;
		endX -= startX;
		endY -= startY;
		if (endX + endY < 0) {
			startX += endX;
			endX = -endX;
			startY += endY;
			endY = -endY;
		}
		@Pc(83) int local83;
		@Pc(116) int local116;
		@Pc(129) int local129;
		@Pc(133) int local133;
		@Pc(145) int local145;
		@Pc(226) int local226;
		@Pc(243) int local243;
		@Pc(248) int local248;
		@Pc(201) int local201;
		if (endX <= endY) {
			startX <<= 0x10;
			startX += 32768;
			@Pc(390) int local390 = endX << 16;
			local83 = (int) Math.floor((double) local390 / (double) endY + 0.5D);
			endY += startY;
			if (startY < local17) {
				startX += local83 * (local17 - startY);
				startY = local17;
			}
			if (endY >= local32) {
				endY = local32 - 1;
			}
			local116 = colour >>> 24;
			if (local116 == 255) {
				while (startY <= endY) {
					local129 = startX >> 16;
					local133 = startY - surfaceClipLeft;
					local145 = clippingMask + local5[local133];
					if (local129 >= this.clipX1 && local129 < this.clipX2 && local129 >= local145 && local129 < local145 + local8[local133]) {
						this.surfaceRaster[local129 + startY * this.anInt430] = colour;
					}
					startX += local83;
					startY++;
				}
			} else {
				local201 = ((colour & 0xFF00FF) * local116 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local116 >> 8 & 0xFF00) + (local116 << 24);
				local129 = 256 - local116;
				while (startY <= endY) {
					local133 = startX >> 16;
					local145 = startY - surfaceClipLeft;
					local226 = clippingMask + local5[local145];
					if (local133 >= this.clipX1 && local133 < this.clipX2 && local133 >= local226 && local133 < local226 + local8[local145]) {
						local243 = local133 + startY * this.anInt430;
						local248 = this.surfaceRaster[local243];
						@Pc(584) int local584 = ((local248 & 0xFF00FF) * local129 >> 8 & 0xFF00FF) + ((local248 & 0xFF00) * local129 >> 8 & 0xFF00);
						this.surfaceRaster[local133 + startY * this.anInt430] = local201 + local584;
					}
					startX += local83;
					startY++;
				}
			}
			return;
		}
		startY <<= 0x10;
		startY += 32768;
		@Pc(73) int local73 = endY << 16;
		local83 = (int) Math.floor((double) local73 / (double) endX + 0.5D);
		endX += startX;
		if (startX < this.clipX1) {
			startY += local83 * (this.clipX1 - startX);
			startX = this.clipX1;
		}
		if (endX >= this.clipX2) {
			endX = this.clipX2 - 1;
		}
		local116 = colour >>> 24;
		if (local116 == 255) {
			while (startX <= endX) {
				local129 = startY >> 16;
				local133 = local129 - surfaceClipLeft;
				if (local129 >= local17 && local129 < local32) {
					local145 = clippingMask + local5[local133];
					if (startX >= local145 && startX < local145 + local8[local133]) {
						this.surfaceRaster[startX + local129 * this.anInt430] = colour;
					}
				}
				startY += local83;
				startX++;
			}
			return;
		}
		local201 = ((colour & 0xFF00FF) * local116 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local116 >> 8 & 0xFF00) + (local116 << 24);
		local129 = 256 - local116;
		while (startX <= endX) {
			local133 = startY >> 16;
			local145 = local133 - surfaceClipLeft;
			if (local133 >= local17 && local133 < local32) {
				local226 = clippingMask + local5[local145];
				if (startX >= local226 && startX < local226 + local8[local145]) {
					local243 = startX + local133 * this.anInt430;
					local248 = this.surfaceRaster[local243];
					local248 = ((local248 & 0xFF00FF) * local129 >> 8 & 0xFF00FF) + ((local248 & 0xFF00) * local129 >> 8 & 0xFF00);
					this.surfaceRaster[local243] = local201 + local248;
				}
			}
			startY += local83;
			startX++;
		}
	}

	@OriginalMember(owner = "client!bc", name = "E", descriptor = "()V")
	private void updateViewport() {
		this.viewX1 = this.clipX1 - this.projectionCenterX;
		this.viewX2 = this.clipX2 - this.projectionCenterX;
		this.viewY1 = this.clipY1 - this.anInt422;
		this.viewY2 = this.clipY2 - this.anInt422;
		for (@Pc(29) int local29 = 0; local29 < this.threadCount; local29++) {
			@Pc(36) Rasterizer local36 = this.resources[local29].rasterizer;
			local36.anInt5773 = this.projectionCenterX - this.clipX1;
			local36.anInt5777 = this.anInt422 - this.clipY1;
			local36.width = this.clipX2 - this.clipX1;
			local36.anInt5775 = this.clipY2 - this.clipY1;
		}
		@Pc(78) int local78 = this.clipY1 * this.anInt430 + this.clipX1;
		for (@Pc(81) int local81 = this.clipY1; local81 < this.clipY2; local81++) {
			for (@Pc(84) int local84 = 0; local84 < this.threadCount; local84++) {
				this.resources[local84].rasterizer.lineOffsets[local81 - this.clipY1] = local78;
			}
			local78 += this.anInt430;
		}
	}

	@OriginalMember(owner = "client!bc", name = "i", descriptor = "(I)I")
	public int textureHsl(@OriginalArg(0) int id) {
		return super.materialProvider.getMetrics(id).colorHsl & 0xFFFF;
	}

	@OriginalMember(owner = "client!bc", name = "ca", descriptor = "(ILclient!ma;II)V")
	@Override
	public void ca(@OriginalArg(0) int colour, @OriginalArg(1) ComponentSurface clippingMask, @OriginalArg(2) int x, @OriginalArg(3) int y) {
		@Pc(2) Class145_Sub2 local2 = (Class145_Sub2) clippingMask;
		@Pc(5) int[] local5 = local2.anIntArray554;
		@Pc(8) int[] local8 = local2.anIntArray553;
		@Pc(20) int local20;
		if (this.clipY2 < y + local5.length) {
			local20 = this.clipY2 - y;
		} else {
			local20 = local5.length;
		}
		@Pc(33) int local33;
		if (this.clipY1 > y) {
			local33 = this.clipY1 - y;
			y = this.clipY1;
		} else {
			local33 = 0;
		}
		if (local20 - local33 <= 0) {
			return;
		}
		@Pc(49) int local49 = y * this.anInt430;
		for (@Pc(51) int local51 = local33; local51 < local20; local51++) {
			@Pc(58) int local58 = x + local5[local51];
			@Pc(62) int local62 = local8[local51];
			if (this.clipX1 > local58) {
				local62 -= this.clipX1 - local58;
				local58 = this.clipX1;
			}
			if (this.clipX2 < local58 + local62) {
				local62 = this.clipX2 - local58;
			}
			local58 += local49;
			for (@Pc(94) int local94 = -local62; local94 < 0; local94++) {
				this.surfaceRaster[local58++] = colour;
			}
			local49 += this.anInt430;
		}
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(II)I")
	@Override
	public int compareFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB) {
		@Pc(3) int local3 = maskA | 0x20800;
		return local3 & maskB ^ maskB;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIII)V")
	@Override
	public void method4294(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height) {
	}

	@OriginalMember(owner = "client!bc", name = "NA", descriptor = "(IIIII)V")
	@Override
	protected void NA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int radius, @OriginalArg(3) int colour, @OriginalArg(4) int mode) {
		if (radius < 0) {
			radius = -radius;
		}
		@Pc(8) int local8 = y - radius;
		if (local8 < this.clipY1) {
			local8 = this.clipY1;
		}
		@Pc(21) int local21 = y + radius + 1;
		if (local21 > this.clipY2) {
			local21 = this.clipY2;
		}
		@Pc(30) int local30 = local8;
		@Pc(34) int local34 = radius * radius;
		@Pc(36) int local36 = 0;
		@Pc(40) int local40 = y - local8;
		@Pc(44) int local44 = local40 * local40;
		@Pc(48) int local48 = local44 - local40;
		if (y > local21) {
			y = local21;
		}
		@Pc(57) int local57 = colour >>> 24;
		@Pc(91) int local91;
		@Pc(102) int local102;
		@Pc(116) int local116;
		@Pc(118) int local118;
		if (mode == 0 || mode == 1 && local57 == 255) {
			while (local30 < y) {
				while (local48 <= local34 || local44 <= local34) {
					local44 += local36 + local36;
					local48 += local36++ + local36;
				}
				local91 = x + 1 - local36;
				if (local91 < this.clipX1) {
					local91 = this.clipX1;
				}
				local102 = x + local36;
				if (local102 > this.clipX2) {
					local102 = this.clipX2;
				}
				local116 = local91 + local30 * this.anInt430;
				for (local118 = local91; local118 < local102; local118++) {
					this.surfaceRaster[local116++] = colour;
				}
				local30++;
				local44 -= local40-- + local40;
				local48 -= local40 + local40;
			}
			local36 = radius;
			local40 = local30 - y;
			local48 = local40 * local40 + local34;
			local44 = local48 - radius;
			local48 -= local40;
			while (local30 < local21) {
				while (local48 > local34 && local44 > local34) {
					local48 -= local36-- + local36;
					local44 -= local36 + local36;
				}
				local91 = x - local36;
				if (local91 < this.clipX1) {
					local91 = this.clipX1;
				}
				local102 = x + local36;
				if (local102 > this.clipX2 - 1) {
					local102 = this.clipX2 - 1;
				}
				local116 = local91 + local30 * this.anInt430;
				for (local118 = local91; local118 <= local102; local118++) {
					this.surfaceRaster[local116++] = colour;
				}
				local30++;
				local48 += local40 + local40;
				local44 += local40++ + local40;
			}
			return;
		}
		@Pc(334) int local334;
		@Pc(340) int local340;
		if (mode == 1) {
			@Pc(277) int local277 = ((colour & 0xFF00FF) * local57 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local57 >> 8 & 0xFF00) + (local57 << 24);
			local91 = 256 - local57;
			while (local30 < y) {
				while (local48 <= local34 || local44 <= local34) {
					local44 += local36 + local36;
					local48 += local36++ + local36;
				}
				local102 = x + 1 - local36;
				if (local102 < this.clipX1) {
					local102 = this.clipX1;
				}
				local116 = x + local36;
				if (local116 > this.clipX2) {
					local116 = this.clipX2;
				}
				local118 = local102 + local30 * this.anInt430;
				for (local334 = local102; local334 < local116; local334++) {
					local340 = this.surfaceRaster[local118];
					local340 = ((local340 & 0xFF00FF) * local91 >> 8 & 0xFF00FF) + ((local340 & 0xFF00) * local91 >> 8 & 0xFF00);
					this.surfaceRaster[local118++] = local277 + local340;
				}
				local30++;
				local44 -= local40-- + local40;
				local48 -= local40 + local40;
			}
			local36 = radius;
			local40 = -local40;
			local48 = local40 * local40 + local34;
			local44 = local48 - radius;
			local48 -= local40;
			while (local30 < local21) {
				while (local48 > local34 && local44 > local34) {
					local48 -= local36-- + local36;
					local44 -= local36 + local36;
				}
				local102 = x - local36;
				if (local102 < this.clipX1) {
					local102 = this.clipX1;
				}
				local116 = x + local36;
				if (local116 > this.clipX2 - 1) {
					local116 = this.clipX2 - 1;
				}
				local118 = local102 + local30 * this.anInt430;
				for (local334 = local102; local334 <= local116; local334++) {
					local340 = this.surfaceRaster[local118];
					local340 = ((local340 & 0xFF00FF) * local91 >> 8 & 0xFF00FF) + ((local340 & 0xFF00) * local91 >> 8 & 0xFF00);
					this.surfaceRaster[local118++] = local277 + local340;
				}
				local30++;
				local48 += local40 + local40;
				local44 += local40++ + local40;
			}
		} else if (mode == 2) {
			@Pc(593) int local593;
			while (local30 < y) {
				while (local48 <= local34 || local44 <= local34) {
					local44 += local36 + local36;
					local48 += local36++ + local36;
				}
				local91 = x + 1 - local36;
				if (local91 < this.clipX1) {
					local91 = this.clipX1;
				}
				local102 = x + local36;
				if (local102 > this.clipX2) {
					local102 = this.clipX2;
				}
				local116 = local91 + local30 * this.anInt430;
				for (local118 = local91; local118 < local102; local118++) {
					local334 = this.surfaceRaster[local116];
					local340 = colour + local334;
					local593 = (colour & 0xFF00FF) + (local334 & 0xFF00FF);
					@Pc(603) int local603 = (local593 & 0x1000100) + (local340 - local593 & 0x10000);
					this.surfaceRaster[local116++] = local340 - local603 | local603 - (local603 >>> 8);
				}
				local30++;
				local44 -= local40-- + local40;
				local48 -= local40 + local40;
			}
			local36 = radius;
			local40 = -local40;
			local48 = local40 * local40 + local34;
			local44 = local48 - radius;
			local48 -= local40;
			while (local30 < local21) {
				while (local48 > local34 && local44 > local34) {
					local48 -= local36-- + local36;
					local44 -= local36 + local36;
				}
				local91 = x - local36;
				if (local91 < this.clipX1) {
					local91 = this.clipX1;
				}
				local102 = x + local36;
				if (local102 > this.clipX2 - 1) {
					local102 = this.clipX2 - 1;
				}
				local116 = local91 + local30 * this.anInt430;
				for (local118 = local91; local118 <= local102; local118++) {
					local334 = this.surfaceRaster[local116];
					local340 = colour + local334;
					local593 = (colour & 0xFF00FF) + (local334 & 0xFF00FF);
					local334 = (local593 & 0x1000100) + (local340 - local593 & 0x10000);
					this.surfaceRaster[local116++] = local340 - local334 | local334 - (local334 >>> 8);
				}
				local30++;
				local48 += local40 + local40;
				local44 += local40++ + local40;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!bc", name = "v", descriptor = "()V")
	@Override
	public void method4281() {
	}

	@OriginalMember(owner = "client!bc", name = "F", descriptor = "()V")
	private void method485() {
		if (this.aFloat13 == 0.0F) {
			this.anInt403 = this.anInt405;
			return;
		}
		@Pc(8) float local8 = (float) this.anInt405;
		@Pc(12) float local12 = (float) this.anInt406;
		@Pc(23) float local23 = -(local12 * 2.0F * local8) / (local8 - local12);
		@Pc(32) float local32 = this.aFloat12 / (this.aFloat13 + this.aFloat12);
		@Pc(36) float local36 = local32 * local32;
		@Pc(50) float local50 = -local23 * (1.0F - local32) * (1.0F - local32) / this.aFloat13;
		this.anInt403 = (int) (((float) this.anInt405 - local23 * local36) / ((local8 + local12) / (local8 - local12) - local50) + 0.5F);
	}

	@OriginalMember(owner = "client!bc", name = "o", descriptor = "()I")
	@Override
	public int getMaxLights() {
		return 0;
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(I)V")
	@Override
	public void linkThreads(@OriginalArg(0) int index) {
		this.resources[index].bindThread(Thread.currentThread());
	}

	@OriginalMember(owner = "client!bc", name = "O", descriptor = "(IIIIII)V")
	@Override
	public void O(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int arg3, @OriginalArg(4) int colour, @OriginalArg(5) int mode) {
		if (x < this.clipX1) {
			width -= this.clipX1 - x;
			x = this.clipX1;
		}
		if (y < this.clipY1) {
			arg3 -= this.clipY1 - y;
			y = this.clipY1;
		}
		if (x + width > this.clipX2) {
			width = this.clipX2 - x;
		}
		if (y + arg3 > this.clipY2) {
			arg3 = this.clipY2 - y;
		}
		if (width <= 0 || arg3 <= 0 || x > this.clipX2 || y > this.clipY2) {
			return;
		}
		@Pc(67) int local67 = this.anInt430 - width;
		@Pc(74) int local74 = x + y * this.anInt430;
		@Pc(78) int local78 = colour >>> 24;
		@Pc(90) int local90;
		@Pc(94) int local94;
		@Pc(101) int local101;
		if (mode != 0 && (mode != 1 || local78 != 255)) {
			@Pc(221) int local221;
			if (mode == 1) {
				@Pc(205) int local205 = ((colour & 0xFF00FF) * local78 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local78 >> 8 & 0xFF00) + (local78 << 24);
				local90 = 256 - local78;
				for (local94 = 0; local94 < arg3; local94++) {
					for (local101 = -width; local101 < 0; local101++) {
						local221 = this.surfaceRaster[local74];
						local221 = ((local221 & 0xFF00FF) * local90 >> 8 & 0xFF00FF) + ((local221 & 0xFF00) * local90 >> 8 & 0xFF00);
						this.surfaceRaster[local74++] = local205 + local221;
					}
					local74 += local67;
				}
			} else if (mode == 2) {
				for (local90 = 0; local90 < arg3; local90++) {
					for (local94 = -width; local94 < 0; local94++) {
						local101 = this.surfaceRaster[local74];
						local221 = colour + local101;
						@Pc(288) int local288 = (colour & 0xFF00FF) + (local101 & 0xFF00FF);
						@Pc(298) int local298 = (local288 & 0x1000100) + (local221 - local288 & 0x10000);
						this.surfaceRaster[local74++] = local221 - local298 | local298 - (local298 >>> 8);
					}
					local74 += local67;
				}
			} else {
				throw new IllegalArgumentException();
			}
			return;
		}
		local90 = width >> 3;
		local94 = width & 0x7;
		width = local74 - 1;
		for (local101 = -arg3; local101 < 0; local101++) {
			if (local90 > 0) {
				x = local90;
				do {
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					width++;
					this.surfaceRaster[width] = colour;
					x--;
				} while (x > 0);
			}
			if (local94 > 0) {
				x = local94;
				do {
					width++;
					this.surfaceRaster[width] = colour;
					x--;
				} while (x > 0);
			}
			width += local67;
		}
	}

	@OriginalMember(owner = "client!bc", name = "p", descriptor = "(I)V")
	@Override
	public void p(@OriginalArg(0) int colour) {
		this.O(0, 0, this.anInt430, this.surfaceHeight, colour, 0);
	}

	@OriginalMember(owner = "client!bc", name = "xa", descriptor = "()F")
	@Override
	public float xa() {
		return this.aFloat13;
	}

	@OriginalMember(owner = "client!bc", name = "MA", descriptor = "(I)V")
	@Override
	public void setOffsets(@OriginalArg(0) int arg0) {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!tb;)V")
	@Override
	public void setSunCubeTexture(@OriginalArg(0) EnvironmentSampler environmentSampler) {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIIF)Lclient!vp;")
	@Override
	public PointLight createPointLight(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int radius, @OriginalArg(4) int colour, @OriginalArg(5) float intensity) {
		return null;
	}

	@OriginalMember(owner = "client!bc", name = "j", descriptor = "(I)Z")
	public boolean textureAvailable(@OriginalArg(0) int id) {
		return super.materialProvider.textureAvailable(id);
	}

	@OriginalMember(owner = "client!bc", name = "r", descriptor = "()V")
	@Override
	public void method4266() {
	}

	@OriginalMember(owner = "client!bc", name = "DA", descriptor = "(IIIIII[BII)V")
	@Override
	public void DA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int backgroundColour, @OriginalArg(5) int foregroundColour, @OriginalArg(6) byte[] mask, @OriginalArg(7) int maskWidth, @OriginalArg(8) int mode) {
		if (width <= 0 || height <= 0) {
			return;
		}
		@Pc(6) int local6 = 0;
		@Pc(8) int local8 = 0;
		@Pc(14) int local14 = (maskWidth << 16) / width;
		@Pc(23) int local23 = (mask.length / maskWidth << 16) / height;
		@Pc(30) int local30 = x + y * this.anInt430;
		@Pc(35) int local35 = this.anInt430 - width;
		if (y + height > this.clipY2) {
			height -= y + height - this.clipY2;
		}
		@Pc(59) int local59;
		if (y < this.clipY1) {
			local59 = this.clipY1 - y;
			height -= local59;
			local30 += local59 * this.anInt430;
			local8 += local23 * local59;
		}
		if (x + width > this.clipX2) {
			local59 = x + width - this.clipX2;
			width -= local59;
			local35 += local59;
		}
		if (x < this.clipX1) {
			local59 = this.clipX1 - x;
			width -= local59;
			local30 += local59;
			local6 += local14 * local59;
			local35 += local59;
		}
		local59 = backgroundColour >>> 24;
		@Pc(132) int local132 = foregroundColour >>> 24;
		@Pc(145) int local145;
		@Pc(148) int local148;
		@Pc(155) int local155;
		@Pc(158) int local158;
		if (mode == 0 || mode == 1 && local59 == 255 && local132 == 255) {
			local145 = local6;
			for (local148 = -height; local148 < 0; local148++) {
				local155 = (local8 >> 16) * maskWidth;
				for (local158 = -width; local158 < 0; local158++) {
					if (mask[(local6 >> 16) + local155] == 0) {
						this.surfaceRaster[local30++] = backgroundColour;
					} else {
						this.surfaceRaster[local30++] = foregroundColour;
					}
					local6 += local14;
				}
				local8 += local23;
				local6 = local145;
				local30 += local35;
			}
			return;
		}
		@Pc(222) int local222;
		@Pc(236) int local236;
		@Pc(240) int local240;
		@Pc(245) int local245;
		if (mode == 1) {
			local145 = local6;
			for (local148 = -height; local148 < 0; local148++) {
				local155 = (local8 >> 16) * maskWidth;
				for (local158 = -width; local158 < 0; local158++) {
					local222 = backgroundColour;
					if (mask[(local6 >> 16) + local155] != 0) {
						local222 = foregroundColour;
					}
					local236 = local222 >>> 24;
					local240 = 255 - local236;
					local245 = this.surfaceRaster[local30];
					this.surfaceRaster[local30++] = ((local222 & 0xFF00FF) * local236 + (local245 & 0xFF00FF) * local240 & 0xFF00FF00) + ((local222 & 0xFF00) * local236 + (local245 & 0xFF00) * local240 & 0xFF0000) >> 8;
					local6 += local14;
				}
				local8 += local23;
				local6 = local145;
				local30 += local35;
			}
		} else if (mode == 2) {
			local145 = local6;
			for (local148 = -height; local148 < 0; local148++) {
				local155 = (local8 >> 16) * maskWidth;
				for (local158 = -width; local158 < 0; local158++) {
					local222 = backgroundColour;
					if (mask[(local6 >> 16) + local155] != 0) {
						local222 = foregroundColour;
					}
					if (local222 == 0) {
						local30++;
					} else {
						local236 = this.surfaceRaster[local30];
						local240 = local222 + local236;
						local245 = (local222 & 0xFF00FF) + (local236 & 0xFF00FF);
						@Pc(360) int local360 = (local245 & 0x1000100) + (local240 - local245 & 0x10000);
						this.surfaceRaster[local30++] = local240 - local360 | local360 - (local360 >>> 8);
					}
					local6 += local14;
				}
				local8 += local23;
				local6 = local145;
				local30 += local35;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!bc", name = "k", descriptor = "(I)[I")
	private int[] getArgbTexture(@OriginalArg(0) int id) {
		@Pc(2) LRUCache local2 = this.textureCache;
		@Pc(12) SafeModeTexture2D local12;
		synchronized (this.textureCache) {
			local12 = (SafeModeTexture2D) this.textureCache.method1787((long) id);
			if (local12 == null) {
				if (!super.materialProvider.textureAvailable(id)) {
					return null;
				}
				@Pc(32) MaterialType local32 = super.materialProvider.getMetrics(id);
				@Pc(42) int local42 = local32.isSmall || this.shrinkTextures ? 64 : 128;
				local12 = new SafeModeTexture2D(id, local42, super.materialProvider.getPixelsArgb(id, 0.7F, local42, local42), local32.aBoolean641);
				this.textureCache.method1779((long) id, local12);
			}
		}
		local12.isAnimated = true;
		return local12.getArgbColor();
	}

	@OriginalMember(owner = "client!bc", name = "fa", descriptor = "(IIII)V")
	@Override
	public void fa(@OriginalArg(0) int clipX1, @OriginalArg(1) int clipY1, @OriginalArg(2) int clipX2, @OriginalArg(3) int clipY2) {
		if (this.clipX1 < clipX1) {
			this.clipX1 = clipX1;
		}
		if (this.clipY1 < clipY1) {
			this.clipY1 = clipY1;
		}
		if (this.clipX2 > clipX2) {
			this.clipX2 = clipX2;
		}
		if (this.clipY2 > clipY2) {
			this.clipY2 = clipY2;
		}
		this.updateViewport();
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Ljava/lang/Runnable;)Lclient!cv;")
	public JavaThreadResource threadResource(@OriginalArg(0) Runnable thread) {
		for (@Pc(1) int local1 = 0; local1 < this.threadCount; local1++) {
			if (this.resources[local1].thread == thread) {
				return this.resources[local1];
			}
		}
		return null;
	}

	@OriginalMember(owner = "client!bc", name = "b", descriptor = "()V")
	@Override
	public void method4236() {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "([IIIII)Lclient!l;")
	@Override
	public NativeSprite method4265(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		@Pc(1) boolean local1 = false;
		@Pc(3) int local3 = 0;
		for (@Pc(5) int local5 = 0; local5 < arg3; local5++) {
			for (@Pc(8) int local8 = 0; local8 < arg2; local8++) {
				@Pc(16) int local16 = arg0[local3++] >>> 24;
				if (local16 != 0 && local16 != 255) {
					local1 = true;
					return local1 ? new TransparentSpriteSM(this, arg0, 0, arg1, arg2, arg3) : new Class57_Sub1_Sub1(this, arg0, 0, arg1, arg2, arg3);
				}
			}
		}
		return local1 ? new TransparentSpriteSM(this, arg0, 0, arg1, arg2, arg3) : new Class57_Sub1_Sub1(this, arg0, 0, arg1, arg2, arg3);
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(I)V")
	@Override
	public void tick(@OriginalArg(0) int time) {
		@Pc(4) int local4 = time - this.lastTickTime;
		for (@Pc(9) Object local9 = this.textureCache.method1789(); local9 != null; local9 = this.textureCache.method1782()) {
			@Pc(13) SafeModeTexture2D local13 = (SafeModeTexture2D) local9;
			if (local13.isAnimated) {
				local13.cycle += local4;
				@Pc(27) int local27 = local13.cycle / 20;
				if (local27 > 0) {
					@Pc(36) MaterialType local36 = super.materialProvider.getMetrics(local13.materialId);
					local13.animate(local36.aByte91 * local4 * 50 / 1000, local36.aByte93 * local4 * 50 / 1000);
					local13.cycle -= local27 * 20;
				}
				local13.isAnimated = false;
			}
		}
		this.lastTickTime = time;
		this.spriteCache.method1788(5);
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(II[I[I)Lclient!ma;")
	@Override
	public ComponentSurface createMask(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(2) int[] offsets, @OriginalArg(3) int[] widths) {
		return new Class145_Sub2(width, height, offsets, widths);
	}

	@OriginalMember(owner = "client!bc", name = "b", descriptor = "(Z)V")
	@Override
	public void method4255(@OriginalArg(0) boolean enabled) {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!u;)V")
	@Override
	public void setHeap(@OriginalArg(0) MemoryPool memoryPool) {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "([Lclient!t;Lclient!c;[Lclient!mi;I)V")
	@Override
	public void method4307(@OriginalArg(0) Model[] arg0, @OriginalArg(1) Ground arg1, @OriginalArg(2) BoundingBoxPolygon[] arg2, @OriginalArg(3) int arg3) {
		@Pc(3) JavaModel[] local3 = new JavaModel[arg0.length];
		for (@Pc(5) int local5 = 0; local5 < arg0.length; local5++) {
			if (arg0[local5] != null) {
				local3[local5] = (JavaModel) arg0[local5];
			}
		}
		@Pc(26) JavaModel local26 = JavaModel.method4912(this, local3);
		local26.method4900(arg1, arg2 == null ? null : arg2[0], null, arg3);
	}

	@OriginalMember(owner = "client!bc", name = "G", descriptor = "()Z")
	public boolean stopped() {
		return this.stopped;
	}

	@OriginalMember(owner = "client!bc", name = "ZA", descriptor = "()I")
	@Override
	public int ZA() {
		@Pc(2) int local2 = this.anInt414;
		this.anInt414 = 0;
		return local2;
	}

	@OriginalMember(owner = "client!bc", name = "B", descriptor = "(F)V")
	@Override
	public void B(@OriginalArg(0) float ambient) {
		this.ambient = (int) (ambient * 65535.0F);
	}

	@OriginalMember(owner = "client!bc", name = "q", descriptor = "()Z")
	@Override
	public boolean method4264() {
		return true;
	}

	@OriginalMember(owner = "client!bc", name = "j", descriptor = "()Z")
	@Override
	public boolean method4251() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!mc;)V")
	@Override
	public void render(@OriginalArg(0) ParticleList particleList) {
		@Pc(3) ParticleNode local3 = particleList.particles.aClass3_Sub1_7;
		for (@Pc(6) ParticleNode local6 = local3.next2; local6 != local3; local6 = local6.next2) {
			@Pc(10) PointEntity local10 = (PointEntity) local6;
			@Pc(15) int local15 = local10.x >> 12;
			@Pc(20) int local20 = local10.y >> 12;
			@Pc(25) int local25 = local10.z >> 12;
			@Pc(49) int local49 = this.aClass31_Sub2_1.anInt7333 + (this.aClass31_Sub2_1.anInt7323 * local15 + this.aClass31_Sub2_1.anInt7330 * local20 + this.aClass31_Sub2_1.anInt7325 * local25 >> 15);
			if (local49 >= this.anInt406 && local49 <= this.anInt411) {
				@Pc(90) int local90 = this.projectionCenterX + this.anInt416 * (this.aClass31_Sub2_1.anInt7329 + (this.aClass31_Sub2_1.anInt7334 * local15 + this.aClass31_Sub2_1.anInt7328 * local20 + this.aClass31_Sub2_1.anInt7332 * local25 >> 15)) / local49;
				@Pc(122) int local122 = this.anInt422 + this.anInt417 * (this.aClass31_Sub2_1.anInt7324 + (this.aClass31_Sub2_1.anInt7331 * local15 + this.aClass31_Sub2_1.anInt7326 * local20 + this.aClass31_Sub2_1.anInt7327 * local25 >> 15)) / local49;
				if (local90 >= this.clipX1 && local90 <= this.clipX2 && local122 >= this.clipY1 && local122 <= this.clipY2) {
					if (local49 == 0) {
						local49 = 1;
					}
					this.method491(local10, local90, local122, (local10.size >> 2) / local49);
				}
			}
		}
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(FFF)V")
	@Override
	public void setBloomParams(@OriginalArg(0) float whitePoint, @OriginalArg(1) float bloom, @OriginalArg(2) float brightpass) {
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(IIIIII)V")
	@Override
	public void line(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int mode) {
		x2 -= x1;
		y2 -= y1;
		if (y2 == 0) {
			if (x2 >= 0) {
				this.b(x1, y1, x2 + 1, colour, mode);
			} else {
				this.b(x1 + x2, y1, 1 - x2, colour, mode);
			}
		} else if (x2 != 0) {
			if (x2 + y2 < 0) {
				x1 += x2;
				x2 = -x2;
				y1 += y2;
				y2 = -y2;
			}
			@Pc(104) int local104;
			@Pc(137) int local137;
			@Pc(150) int local150;
			@Pc(213) int local213;
			@Pc(228) int local228;
			@Pc(233) int local233;
			@Pc(204) int local204;
			if (x2 > y2) {
				y1 <<= 0x10;
				y1 += 32768;
				@Pc(94) int local94 = y2 << 16;
				local104 = (int) Math.floor((double) local94 / (double) x2 + 0.5D);
				x2 += x1;
				if (x1 < this.clipX1) {
					y1 += local104 * (this.clipX1 - x1);
					x1 = this.clipX1;
				}
				if (x2 >= this.clipX2) {
					x2 = this.clipX2 - 1;
				}
				local137 = colour >>> 24;
				if (mode == 0 || mode == 1 && local137 == 255) {
					while (x1 <= x2) {
						local150 = y1 >> 16;
						if (local150 >= this.clipY1 && local150 < this.clipY2) {
							this.surfaceRaster[x1 + local150 * this.anInt430] = colour;
						}
						y1 += local104;
						x1++;
					}
				} else if (mode == 1) {
					local204 = ((colour & 0xFF00FF) * local137 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local137 >> 8 & 0xFF00) + (local137 << 24);
					local150 = 256 - local137;
					while (x1 <= x2) {
						local213 = y1 >> 16;
						if (local213 >= this.clipY1 && local213 < this.clipY2) {
							local228 = x1 + local213 * this.anInt430;
							local233 = this.surfaceRaster[local228];
							local233 = ((local233 & 0xFF00FF) * local150 >> 8 & 0xFF00FF) + ((local233 & 0xFF00) * local150 >> 8 & 0xFF00);
							this.surfaceRaster[local228] = local204 + local233;
						}
						y1 += local104;
						x1++;
					}
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				x1 <<= 0x10;
				x1 += 32768;
				@Pc(357) int local357 = x2 << 16;
				local104 = (int) Math.floor((double) local357 / (double) y2 + 0.5D);
				y2 += y1;
				if (y1 < this.clipY1) {
					x1 += local104 * (this.clipY1 - y1);
					y1 = this.clipY1;
				}
				if (y2 >= this.clipY2) {
					y2 = this.clipY2 - 1;
				}
				local137 = colour >>> 24;
				if (mode == 0 || mode == 1 && local137 == 255) {
					while (y1 <= y2) {
						local150 = x1 >> 16;
						if (local150 >= this.clipX1 && local150 < this.clipX2) {
							this.surfaceRaster[local150 + y1 * this.anInt430] = colour;
						}
						x1 += local104;
						y1++;
					}
				} else if (mode == 1) {
					local204 = ((colour & 0xFF00FF) * local137 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local137 >> 8 & 0xFF00) + (local137 << 24);
					local150 = 256 - local137;
					while (y1 <= y2) {
						local213 = x1 >> 16;
						if (local213 >= this.clipX1 && local213 < this.clipX2) {
							local228 = local213 + y1 * this.anInt430;
							local233 = this.surfaceRaster[local228];
							@Pc(516) int local516 = ((local233 & 0xFF00FF) * local150 >> 8 & 0xFF00FF) + ((local233 & 0xFF00) * local150 >> 8 & 0xFF00);
							this.surfaceRaster[local213 + y1 * this.anInt430] = local204 + local516;
						}
						x1 += local104;
						y1++;
					}
				} else {
					throw new IllegalArgumentException();
				}
			}
		} else if (y2 >= 0) {
			this.TA(x1, y1, y2 + 1, colour, mode);
		} else {
			this.TA(x1, y1 + y2, -y2 + 1, colour, mode);
		}
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIII)Lclient!tb;")
	@Override
	public EnvironmentSampler createCubeTexture(@OriginalArg(0) int materialPositiveX, @OriginalArg(1) int materialNegativeX, @OriginalArg(2) int materialPositiveY, @OriginalArg(3) int materialNegativeY, @OriginalArg(4) int materialPositiveZ, @OriginalArg(5) int materialNegativeZ) {
		return null;
	}

	@OriginalMember(owner = "client!bc", name = "e", descriptor = "()V")
	@Override
	public void e() {
		this.clipX1 = 0;
		this.clipY1 = 0;
		this.clipX2 = this.anInt430;
		this.clipY2 = this.surfaceHeight;
		this.updateViewport();
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void method4249(@OriginalArg(0) Canvas arg0) {
		@Pc(8) EnvironmentContext local8 = (EnvironmentContext) this.aClass102_3.method2700((long) arg0.hashCode());
		if (local8 == null) {
			local8 = Class16_Sub2.method3584(arg0);
			this.aClass102_3.method2703((long) arg0.hashCode(), local8);
		}
	}

	@OriginalMember(owner = "client!bc", name = "g", descriptor = "()Z")
	@Override
	public boolean method4245() {
		return true;
	}

	@OriginalMember(owner = "client!bc", name = "c", descriptor = "()V")
	@Override
	protected void stop() {
		if (this.colourTables) {
			TextureType.method3281(true, false);
			this.colourTables = false;
		}
		this.aClass4_Sub22_1 = null;
		this.canvas = null;
		this.aClass102_3 = null;
		this.stopped = true;
	}

	@OriginalMember(owner = "client!bc", name = "l", descriptor = "(I)Z")
	public boolean textureRepeats(@OriginalArg(0) int id) {
		return super.materialProvider.getMetrics(id).aBoolean640;
	}

	@OriginalMember(owner = "client!bc", name = "AA", descriptor = "(IIII)V")
	@Override
	public void AA(@OriginalArg(0) int dx, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt429 = this.anInt409;
		this.anInt410 = dx;
		this.anInt409 = arg1;
		this.anInt426 = arg2;
		this.aBoolean43 = true;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!wv;III)V")
	public void method491(@OriginalArg(0) PointEntity arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(2) int local2 = arg0.materialId;
		@Pc(6) int local6 = arg3 >> 1;
		if (local2 == -1) {
			this.NA(arg1, arg2, local6, arg0.colorArgb, 1);
			return;
		}
		if (this.anInt431 != local2) {
			@Pc(30) NativeSprite local30 = (NativeSprite) this.spriteCache.method1787((long) local2);
			if (local30 == null) {
				@Pc(36) int[] local36 = this.getArgbTexture(local2);
				if (local36 == null) {
					return;
				}
				@Pc(46) int local46 = this.smallTexture(local2) ? 64 : 128;
				local30 = this.method4265(local36, local46, local46, local46);
				this.spriteCache.method1779((long) local2, local30);
			}
			this.anInt431 = local2;
			this.cachedBillboardSprite = local30;
		}
		if (this.cachedBillboardSprite.scaleWidth() == 64) {
			arg3 <<= 0x1;
			local6 <<= 0x1;
		}
		this.cachedBillboardSprite.renderImpl(arg1 - local6, arg2 - local6, arg3, arg3, 0, arg0.colorArgb, 1);
	}

	@OriginalMember(owner = "client!bc", name = "m", descriptor = "()Z")
	@Override
	public boolean method4258() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "ea", descriptor = "(Lclient!c;)V")
	@Override
	public void ea(@OriginalArg(0) Ground arg0) {
		this.aClass31_Sub2_1 = (Class31_Sub2) arg0;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!tb;Lclient!tb;FLclient!tb;)Lclient!tb;")
	@Override
	public EnvironmentSampler drawQuad(@OriginalArg(0) EnvironmentSampler environmentSampler, @OriginalArg(1) EnvironmentSampler environmentSampler2, @OriginalArg(2) float alpha, @OriginalArg(3) EnvironmentSampler environmentSampler3) {
		return null;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!t;Lclient!mc;Lclient!c;Lclient!mi;I)V")
	@Override
	public void method4262(@OriginalArg(0) Model arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon arg3) {
		((JavaModel) arg0).method4900(arg2, arg3, arg1, 0);
	}

	@OriginalMember(owner = "client!bc", name = "f", descriptor = "()Z")
	@Override
	public boolean method4243() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "H", descriptor = "()V")
	private void reset() {
		for (@Pc(1) int local1 = 0; local1 < this.threadCount; local1++) {
			this.resources[local1].method998();
		}
		this.e();
	}

	@OriginalMember(owner = "client!bc", name = "ia", descriptor = "(II)V")
	@Override
	public void ia(@OriginalArg(0) int zNear, @OriginalArg(1) int zFar) {
		this.anInt406 = zNear;
		this.anInt405 = FogDistance.scaleDistance(zFar);
		this.anInt411 = this.anInt405 - 255;
		this.method485();
	}

	@OriginalMember(owner = "client!bc", name = "b", descriptor = "(II)I")
	@Override
	public int combineFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB) {
		return maskA | maskB;
	}

	@OriginalMember(owner = "client!bc", name = "y", descriptor = "()Lclient!c;")
	@Override
	public Ground method4289() {
		@Pc(3) JavaThreadResource local3 = this.threadResource(Thread.currentThread());
		return local3.aClass31_Sub2_2;
	}

	@OriginalMember(owner = "client!bc", name = "X", descriptor = "(III[I)V")
	@Override
	public void X(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3) {
		@Pc(23) int local23 = this.aClass31_Sub2_1.anInt7333 + (this.aClass31_Sub2_1.anInt7323 * arg0 + this.aClass31_Sub2_1.anInt7330 * arg1 + this.aClass31_Sub2_1.anInt7325 * arg2 >> 15);
		// Near plane only: anInt405 is the fog-scaled far plane, so rejecting
		// against it made geometry disappear at a fog-dependent range.
		if (local23 < this.anInt406) {
			arg3[0] = arg3[1] = arg3[2] = -1;
			return;
		}
		@Pc(73) int local73 = this.anInt416 * (this.aClass31_Sub2_1.anInt7329 + (this.aClass31_Sub2_1.anInt7334 * arg0 + this.aClass31_Sub2_1.anInt7328 * arg1 + this.aClass31_Sub2_1.anInt7332 * arg2 >> 15)) / local23;
		@Pc(102) int local102 = this.anInt417 * (this.aClass31_Sub2_1.anInt7324 + (this.aClass31_Sub2_1.anInt7331 * arg0 + this.aClass31_Sub2_1.anInt7326 * arg1 + this.aClass31_Sub2_1.anInt7327 * arg2 >> 15)) / local23;
		if (local73 >= this.viewX1 && local73 <= this.viewX2 && local102 >= this.viewY1 && local102 <= this.viewY2) {
			arg3[0] = local73 - this.viewX1;
			arg3[1] = local102 - this.viewY1;
			arg3[2] = local23;
		} else {
			arg3[0] = arg3[1] = arg3[2] = -1;
		}
	}

	@OriginalMember(owner = "client!bc", name = "g", descriptor = "(FF)V")
	@Override
	public void g(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		this.aFloat12 = arg0;
		this.aFloat13 = arg1;
		this.method485();
	}

	@OriginalMember(owner = "client!bc", name = "da", descriptor = "([I)V")
	@Override
	public void da(@OriginalArg(0) int[] clip) {
		clip[0] = this.clipX1;
		clip[1] = this.clipY1;
		clip[2] = this.clipX2;
		clip[3] = this.clipY2;
	}

	@OriginalMember(owner = "client!bc", name = "T", descriptor = "(IFFFFF)V")
	@Override
	public void scaleHeight(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5) {
		this.sunIntensity = (int) (arg1 * 65535.0F);
		this.reverseSunIntensity = (int) (arg2 * 65535.0F);
		@Pc(26) float local26 = (float) Math.sqrt((double) (arg3 * arg3 + arg4 * arg4 + arg5 * arg5));
		this.sunX = (int) (arg3 * 65535.0F / local26);
		this.sunY = (int) (arg4 * 65535.0F / local26);
		this.sunZ = (int) (arg5 * 65535.0F / local26);
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!bb;Z)Lclient!l;")
	@Override
	public NativeSprite generateSprite(@OriginalArg(0) SpriteType indexedImage) {
		@Pc(2) int[] local2 = indexedImage.pallete;
		@Pc(5) byte[] local5 = indexedImage.pixels;
		@Pc(8) int local8 = indexedImage.width;
		@Pc(11) int local11 = indexedImage.height;
		@Pc(76) SpriteSM local76;
		@Pc(20) int[] local20;
		@Pc(25) byte[] local25;
		@Pc(27) int local27;
		@Pc(32) int local32;
		@Pc(34) int local34;
		if (indexedImage.alpha == null) {
			local20 = new int[local2.length];
			local25 = new byte[local8 * local11];
			for (local27 = 0; local27 < local11; local27++) {
				local32 = local27 * local8;
				for (local34 = 0; local34 < local8; local34++) {
					local25[local32 + local34] = local5[local32 + local34];
				}
			}
			for (local32 = 0; local32 < local2.length; local32++) {
				local20[local32] = local2[local32];
			}
			local76 = new PaletteSpriteSM(this, local25, local20, local8, local11);
		} else {
			local20 = new int[local8 * local11];
			local25 = indexedImage.alpha;
			if (local25 == null) {
				for (local27 = 0; local27 < local11; local27++) {
					local32 = local27 * local8;
					for (local34 = 0; local34 < local8; local34++) {
						local20[local32 + local34] = local2[local5[local32 + local34] & 0xFF];
					}
				}
				new TransparentSpriteSM(this, local20, local8, local11);
				local76 = new Class57_Sub1_Sub1(this, local20, local8, local11);
			} else {
				for (local27 = 0; local27 < local11; local27++) {
					local32 = local27 * local8;
					for (local34 = 0; local34 < local8; local34++) {
						local20[local32 + local34] = local2[local5[local32 + local34] & 0xFF] | local25[local32 + local34] << 24;
					}
				}
				local76 = new TransparentSpriteSM(this, local20, local8, local11);
			}
		}
		local76.setOffsets(indexedImage.minX, indexedImage.minY, indexedImage.borderX, indexedImage.borderY);
		return local76;
	}

	@OriginalMember(owner = "client!bc", name = "GA", descriptor = "(IIII)V")
	@Override
	public void GA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt410 = arg0;
		this.anInt409 = arg1;
		this.anInt426 = arg2;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIZ)Lclient!l;")
	@Override
	public NativeSprite createNativeSprite(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) boolean hasAlpha) {
		@Pc(4) int[] local4 = new int[arg2 * arg3];
		@Pc(11) int local11 = y * this.anInt430 + x;
		@Pc(16) int local16 = this.anInt430 - arg2;
		for (@Pc(18) int local18 = 0; local18 < arg3; local18++) {
			@Pc(23) int local23 = local18 * arg2;
			for (@Pc(25) int local25 = 0; local25 < arg2; local25++) {
				local4[local23 + local25] = this.surfaceRaster[local11++];
			}
			local11 += local16;
		}
		if (hasAlpha) {
			return new TransparentSpriteSM(this, local4, arg2, arg3);
		} else {
			return new Class57_Sub1_Sub1(this, local4, arg2, arg3);
		}
	}

	@OriginalMember(owner = "client!bc", name = "IA", descriptor = "()I")
	@Override
	public int IA() {
		return 0;
	}

	@OriginalMember(owner = "client!bc", name = "m", descriptor = "(I)Z")
	public boolean smallTexture(@OriginalArg(0) int id) {
		return this.shrinkTextures || super.materialProvider.getMetrics(id).isSmall;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIIIIII)V")
	public void method494(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8) {
		if (arg2 == 0 || arg3 == 0) {
			return;
		}
		if (arg5 == 65535 || super.materialProvider.getMetrics(arg5).aBoolean639) {
			this.NA(arg0, arg1, arg2, arg6, arg8);
			return;
		}
		if (this.anInt431 != arg5) {
			@Pc(26) NativeSprite local26 = (NativeSprite) this.spriteCache.method1787((long) arg5);
			if (local26 == null) {
				@Pc(32) int[] local32 = this.getArgbTexture(arg5);
				if (local32 == null) {
					return;
				}
				@Pc(43) int local43 = this.smallTexture(arg5) ? 64 : 128;
				local26 = this.method4265(local32, local43, local43, local43);
				this.spriteCache.method1779((long) arg5, local26);
			}
			this.anInt431 = arg5;
			this.cachedBillboardSprite = local26;
		}
		this.cachedBillboardSprite.renderImpl(arg0 - arg2, arg1 - arg3, arg2 << 1, arg3 << 1, arg7, arg6, arg8);
	}

	@OriginalMember(owner = "client!bc", name = "l", descriptor = "()V")
	@Override
	public void l() {
		this.anInt409 = this.anInt429;
		this.aBoolean43 = false;
	}

	@OriginalMember(owner = "client!bc", name = "W", descriptor = "()F")
	@Override
	public float W() {
		return this.aFloat12;
	}

	@OriginalMember(owner = "client!bc", name = "i", descriptor = "()Z")
	@Override
	public boolean method4248() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void releaseSurface(@OriginalArg(0) Canvas canvas2) {
		@Pc(8) EnvironmentContext local8 = (EnvironmentContext) this.aClass102_3.method2700((long) canvas2.hashCode());
		if (local8 == null) {
			return;
		}
		local8.method5684();
		local8 = Class16_Sub2.method3584(canvas2);
		this.aClass102_3.method2703((long) canvas2.hashCode(), local8);
		if (this.canvas != canvas2 || this.aClass57_5 != null) {
			return;
		}
		this.aClass4_Sub22_1 = local8;
		this.surfaceRaster = local8.anIntArray409;
		this.anInt430 = local8.anInt5857;
		this.surfaceHeight = local8.anInt5856;
		this.reset();
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(I[Lclient!vp;)V")
	@Override
	public void copyLights(@OriginalArg(0) int count, @OriginalArg(1) PointLight[] pointLights) {
	}

	@OriginalMember(owner = "client!bc", name = "h", descriptor = "()V")
	@Override
	public void method4247() {
		if (this.canvas == null || this.aClass4_Sub22_1 == null) {
			throw new IllegalStateException("off");
		}
		try {
			@Pc(14) Graphics local14 = this.canvas.getGraphics();
			this.aClass4_Sub22_1.method4608(local14);
		} catch (@Pc(23) Exception local23) {
			this.canvas.repaint();
		}
	}

	@OriginalMember(owner = "client!bc", name = "F", descriptor = "(IIII)[I")
	@Override
	public int[] F(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(4) int[] local4 = new int[arg2 * arg3];
		@Pc(6) int local6 = 0;
		for (@Pc(8) int local8 = 0; local8 < arg3; local8++) {
			@Pc(18) int local18 = (y + local8) * this.anInt430 + x;
			for (@Pc(20) int local20 = 0; local20 < arg2; local20++) {
				local4[local6++] = this.surfaceRaster[local18 + local20];
			}
		}
		return local4;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!jq;IIII)Lclient!t;")
	@Override
	public Model createModel(@OriginalArg(0) Mesh mesh, @OriginalArg(1) int functionMask, @OriginalArg(2) int featureMask, @OriginalArg(3) int ambient, @OriginalArg(4) int contrast) {
		return new JavaModel(this, mesh, functionMask, ambient, contrast, featureMask);
	}

	@OriginalMember(owner = "client!bc", name = "k", descriptor = "()Z")
	@Override
	public boolean method4252() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIIIIIII)V")
	@Override
	public void method4276() {
		@Pc(3) JavaThreadResource local3 = this.threadResource(Thread.currentThread());
		@Pc(6) Rasterizer local6 = local3.rasterizer;
		local6.halfBlend = false;
		local6.halfBlend = false;
		local6.alpha = 0;
		local6.aBoolean519 = true;
		local6.method4548(10, 50, 90, 5, 75, 15, -65536, -65536, -65536);
		local6.halfBlend = true;
	}

	@OriginalMember(owner = "client!bc", name = "TA", descriptor = "(IIIII)V")
	@Override
	public void TA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int colour, @OriginalArg(4) int mode) {
		if (x < this.clipX1 || x >= this.clipX2) {
			return;
		}
		if (y < this.clipY1) {
			arg2 -= this.clipY1 - y;
			y = this.clipY1;
		}
		if (y + arg2 > this.clipY2) {
			arg2 = this.clipY2 - y;
		}
		@Pc(40) int local40 = x + y * this.anInt430;
		@Pc(44) int local44 = colour >>> 24;
		@Pc(54) int local54;
		if (mode == 0 || mode == 1 && local44 == 255) {
			for (local54 = 0; local54 < arg2; local54++) {
				this.surfaceRaster[local40 + local54 * this.anInt430] = colour;
			}
			return;
		}
		@Pc(103) int local103;
		@Pc(111) int local111;
		@Pc(116) int local116;
		if (mode == 1) {
			@Pc(97) int local97 = ((colour & 0xFF00FF) * local44 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local44 >> 8 & 0xFF00) + (local44 << 24);
			local54 = 256 - local44;
			for (local103 = 0; local103 < arg2; local103++) {
				local111 = local40 + local103 * this.anInt430;
				local116 = this.surfaceRaster[local111];
				local116 = ((local116 & 0xFF00FF) * local54 >> 8 & 0xFF00FF) + ((local116 & 0xFF00) * local54 >> 8 & 0xFF00);
				this.surfaceRaster[local111] = local97 + local116;
			}
		} else if (mode == 2) {
			for (local54 = 0; local54 < arg2; local54++) {
				local103 = local40 + local54 * this.anInt430;
				local111 = this.surfaceRaster[local103];
				local116 = colour + local111;
				@Pc(178) int local178 = (colour & 0xFF00FF) + (local111 & 0xFF00FF);
				@Pc(188) int local188 = (local178 & 0x1000100) + (local116 - local178 & 0x10000);
				this.surfaceRaster[local103] = local116 - local188 | local188 - (local188 >>> 8);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!bc", name = "e", descriptor = "(I)V")
	@Override
	public void allocateThreads(@OriginalArg(0) int threadCount) {
		if (this.threadCount == threadCount) {
			return;
		}
		this.threadCount = threadCount;
		this.resources = new JavaThreadResource[this.threadCount];
		for (@Pc(14) int local14 = 0; local14 < this.threadCount; local14++) {
			this.resources[local14] = new JavaThreadResource(this);
		}
	}

	@OriginalMember(owner = "client!bc", name = "c", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void method4263(@OriginalArg(0) Canvas arg0) {
		if (this.canvas == arg0) {
			this.setCanvas(null);
		}
		@Pc(15) EnvironmentContext local15 = (EnvironmentContext) this.aClass102_3.method2700((long) arg0.hashCode());
		if (local15 != null) {
			local15.method5684();
		}
	}

	@OriginalMember(owner = "client!bc", name = "D", descriptor = "()V")
	@Override
	public void cacheReset() {
		this.textureCache.method1777();
	}

	@OriginalMember(owner = "client!bc", name = "OA", descriptor = "(IIII)V")
	@Override
	public void OA(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (left < 0) {
			left = 0;
		}
		if (top < 0) {
			top = 0;
		}
		if (right > this.anInt430) {
			right = this.anInt430;
		}
		if (bottom > this.surfaceHeight) {
			bottom = this.surfaceHeight;
		}
		this.clipX1 = left;
		this.clipX2 = right;
		this.clipY1 = top;
		this.clipY2 = bottom;
		this.updateViewport();
	}

	@OriginalMember(owner = "client!bc", name = "g", descriptor = "(I)V")
	@Override
	public void method4303(@OriginalArg(0) int threadId) {
		this.resources[threadId].bindThread(null);
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "(Z)V")
	@Override
	public void method4309(@OriginalArg(0) boolean zWrite) {
	}

	@OriginalMember(owner = "client!bc", name = "b", descriptor = "(IIIII)V")
	@Override
	public void b(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int colour, @OriginalArg(4) int mode) {
		if (y < this.clipY1 || y >= this.clipY2) {
			return;
		}
		if (x < this.clipX1) {
			arg2 -= this.clipX1 - x;
			x = this.clipX1;
		}
		if (x + arg2 > this.clipX2) {
			arg2 = this.clipX2 - x;
		}
		@Pc(40) int local40 = x + y * this.anInt430;
		@Pc(44) int local44 = colour >>> 24;
		@Pc(54) int local54;
		if (mode == 0 || mode == 1 && local44 == 255) {
			for (local54 = 0; local54 < arg2; local54++) {
				this.surfaceRaster[local40 + local54] = colour;
			}
			return;
		}
		@Pc(100) int local100;
		@Pc(108) int local108;
		if (mode == 1) {
			@Pc(94) int local94 = ((colour & 0xFF00FF) * local44 >> 8 & 0xFF00FF) + ((colour & 0xFF00) * local44 >> 8 & 0xFF00) + (local44 << 24);
			local54 = 256 - local44;
			for (local100 = 0; local100 < arg2; local100++) {
				local108 = this.surfaceRaster[local40 + local100];
				local108 = ((local108 & 0xFF00FF) * local54 >> 8 & 0xFF00FF) + ((local108 & 0xFF00) * local54 >> 8 & 0xFF00);
				this.surfaceRaster[local40 + local100] = local94 + local108;
			}
		} else if (mode == 2) {
			for (local54 = 0; local54 < arg2; local54++) {
				local100 = this.surfaceRaster[local40 + local54];
				local108 = colour + local100;
				@Pc(167) int local167 = (colour & 0xFF00FF) + (local100 & 0xFF00FF);
				@Pc(177) int local177 = (local167 & 0x1000100) + (local108 - local167 & 0x10000);
				this.surfaceRaster[local40 + local54] = local108 - local177 | local177 - (local177 >>> 8);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!bc", name = "d", descriptor = "()Z")
	@Override
	public boolean hardShadow() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "u", descriptor = "()Z")
	@Override
	public boolean hasTextures() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "e", descriptor = "(IIIIII)V")
	@Override
	public void outlineRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int colour, @OriginalArg(5) int mode) {
		this.b(x, y, width, colour, mode);
		this.b(x, y + height - 1, width, colour, mode);
		this.TA(x, y + 1, height - 2, colour, mode);
		this.TA(x + width - 1, y + 1, height - 2, colour, mode);
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "()I")
	@Override
	public int a() {
		return this.anInt406;
	}

	@OriginalMember(owner = "client!bc", name = "B", descriptor = "()Z")
	@Override
	public boolean method4300() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(IIIIIII)V")
	@Override
	public void strongLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int strength) {
		@Pc(3) JavaThreadResource local3 = this.threadResource(Thread.currentThread());
		@Pc(6) Rasterizer local6 = local3.rasterizer;
		@Pc(10) int local10 = x2 - x1;
		@Pc(14) int local14 = y2 - y1;
		@Pc(21) int local21 = local10 >= 0 ? local10 : -local10;
		@Pc(28) int local28 = local14 >= 0 ? local14 : -local14;
		@Pc(30) int local30 = local21;
		if (local21 < local28) {
			local30 = local28;
		}
		if (local30 == 0) {
			return;
		}
		@Pc(44) int local44 = (local10 << 16) / local30;
		@Pc(50) int local50 = (local14 << 16) / local30;
		if (local50 <= local44) {
			local44 = -local44;
		} else {
			local50 = -local50;
		}
		@Pc(66) int local66 = strength * local50 >> 17;
		@Pc(74) int local74 = strength * local50 + 1 >> 17;
		@Pc(80) int local80 = strength * local44 >> 17;
		@Pc(88) int local88 = strength * local44 + 1 >> 17;
		@Pc(93) int local93 = x1 - local6.offsetX();
		@Pc(98) int local98 = y1 - local6.offsetY();
		@Pc(102) int local102 = local93 + local66;
		@Pc(106) int local106 = local93 - local74;
		@Pc(112) int local112 = local93 + local10 - local74;
		@Pc(118) int local118 = local93 + local10 + local66;
		@Pc(122) int local122 = local98 + local80;
		@Pc(126) int local126 = local98 - local88;
		@Pc(132) int local132 = local98 + local14 - local88;
		@Pc(138) int local138 = local98 + local14 + local80;
		local6.alpha = 0;
		local6.aBoolean519 = local102 < 0 || local102 > local6.width || local106 < 0 || local106 > local6.width || local112 < 0 || local112 > local6.width;
		local6.method4552(local122, local126, local132, local102, local106, local112, colour);
		local6.aBoolean519 = local102 < 0 || local102 > local6.width || local106 < 0 || local106 > local6.width || local118 < 0 || local118 > local6.width;
		local6.method4552(local122, local132, local138, local102, local112, local118, colour);
	}

	@OriginalMember(owner = "client!bc", name = "t", descriptor = "()V")
	@Override
	public void method4278() {
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Z)V")
	@Override
	public void setShrinkTextures(@OriginalArg(0) boolean shrinkTextures) {
		this.shrinkTextures = shrinkTextures;
		this.textureCache.method1777();
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(II[[I[[IIII)Lclient!ta;")
	@Override
	public Class65 method4297(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[][] arg2, @OriginalArg(3) int[][] arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		return new JavaGround(this, arg4, arg5, arg0, arg1, arg2, arg3, 128);
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "(Lclient!gt;[Lclient!bb;Z)Lclient!la;")
	@Override
	public AbstractFontRenderer createFontRenderer(@OriginalArg(0) com.jagex.game.runetek5.graphics.font.FontMetrics metrics, @OriginalArg(1) SpriteType[] images) {
		@Pc(3) int[] local3 = new int[images.length];
		@Pc(7) int[] local7 = new int[images.length];
		for (@Pc(11) int local11 = 0; local11 < images.length; local11++) {
			local3[local11] = images[local11].width;
			local7[local11] = images[local11].height;
			if (images[local11].alpha != null) {
			}
		}
		return new JavaMonoFont(this, metrics, images, local3, local7);
	}

	@OriginalMember(owner = "client!bc", name = "s", descriptor = "()Lclient!c;")
	@Override
	public Ground method4274() {
		return new Class31_Sub2();
	}

	@OriginalMember(owner = "client!bc", name = "h", descriptor = "(I)V")
	@Override
	public void setTextureSize(@OriginalArg(0) int size) {
	}

	@OriginalMember(owner = "client!bc", name = "n", descriptor = "()Z")
	@Override
	public boolean supportsAntiAliasing() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "n", descriptor = "(I)[I")
	public int[] getTexels(@OriginalArg(0) int id) {
		@Pc(2) LRUCache local2 = this.textureCache;
		@Pc(12) SafeModeTexture2D local12;
		synchronized (this.textureCache) {
			local12 = (SafeModeTexture2D) this.textureCache.method1787((long) id);
			if (local12 == null) {
				if (!super.materialProvider.textureAvailable(id)) {
					return null;
				}
				@Pc(32) MaterialType local32 = super.materialProvider.getMetrics(id);
				@Pc(42) int local42 = local32.isSmall || this.shrinkTextures ? 64 : 128;
				local12 = new SafeModeTexture2D(id, local42, super.materialProvider.getPixels(true, id, 0.7F, local42, local42), local32.aBoolean641);
				this.textureCache.method1779((long) id, local12);
			}
		}
		local12.isAnimated = true;
		return local12.getArgbColor();
	}

	@OriginalMember(owner = "client!bc", name = "p", descriptor = "()Z")
	@Override
	public boolean method4261() {
		return true;
	}

	@OriginalMember(owner = "client!bc", name = "x", descriptor = "()Z")
	@Override
	public boolean method4286() {
		return false;
	}

	@OriginalMember(owner = "client!bc", name = "w", descriptor = "()I")
	@Override
	public int w() {
		@Pc(2) int local2 = this.anInt407;
		this.anInt407 = 0;
		return local2;
	}

	@OriginalMember(owner = "client!bc", name = "UA", descriptor = "(III)V")
	@Override
	public void UA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		this.anInt409 = arg0 & 0xFFFFFF;
		@Pc(11) int local11 = this.anInt409 >>> 16 & 0xFF;
		if (local11 < 2) {
			local11 = 2;
		}
		@Pc(23) int local23 = this.anInt409 >> 8 & 0xFF;
		if (local23 < 2) {
			local23 = 2;
		}
		@Pc(33) int local33 = this.anInt409 & 0xFF;
		if (local33 < 2) {
			local33 = 2;
		}
		this.anInt409 = local11 << 16 | local23 << 8 | local33;
		if (arg1 < 0) {
			this.aBoolean44 = false;
		} else {
			this.aBoolean44 = true;
		}
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "([Ljava/awt/Rectangle;I)V")
	@Override
	public void method4290(@OriginalArg(0) Rectangle[] arg0, @OriginalArg(1) int arg1) {
		if (this.canvas == null || this.aClass4_Sub22_1 == null) {
			throw new IllegalStateException("off");
		}
		try {
			@Pc(14) Graphics local14 = this.canvas.getGraphics();
			for (@Pc(16) int local16 = 0; local16 < arg1; local16++) {
				@Pc(21) Rectangle local21 = arg0[local16];
				if (local21.x <= this.anInt430 && local21.y <= this.surfaceHeight && local21.x + local21.width > 0 && local21.y + local21.height > 0) {
					this.aClass4_Sub22_1.method4601(local21.y, local21.width, local21.x, local14, local21.height);
				}
			}
		} catch (@Pc(63) Exception local63) {
			this.canvas.repaint();
		}
	}

	@OriginalMember(owner = "client!bc", name = "JA", descriptor = "(IIIIII)Z")
	@Override
	public boolean drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		@Pc(23) int local23 = (this.aClass31_Sub2_1.anInt7323 * arg0 + this.aClass31_Sub2_1.anInt7330 * arg1 + this.aClass31_Sub2_1.anInt7325 * arg2 >> 15) + this.aClass31_Sub2_1.anInt7333;
		if (local23 < 1) {
			local23 = 1;
		}
		@Pc(52) int local52 = (this.aClass31_Sub2_1.anInt7323 * arg3 + this.aClass31_Sub2_1.anInt7330 * arg4 + this.aClass31_Sub2_1.anInt7325 * arg5 >> 15) + this.aClass31_Sub2_1.anInt7333;
		if (local52 < 1) {
			local52 = 1;
		}
		// Near plane only; see the matching note in X().
		if (local23 < this.anInt406 && local52 < this.anInt406) {
			return false;
		}
		@Pc(104) int local104 = this.anInt416 * ((this.aClass31_Sub2_1.anInt7334 * arg0 + this.aClass31_Sub2_1.anInt7328 * arg1 + this.aClass31_Sub2_1.anInt7332 * arg2 >> 15) + this.aClass31_Sub2_1.anInt7329) / local23;
		@Pc(133) int local133 = this.anInt416 * ((this.aClass31_Sub2_1.anInt7334 * arg3 + this.aClass31_Sub2_1.anInt7328 * arg4 + this.aClass31_Sub2_1.anInt7332 * arg5 >> 15) + this.aClass31_Sub2_1.anInt7329) / local52;
		if (local104 < this.viewX1 && local133 < this.viewX1 || local104 > this.viewX2 && local133 > this.viewX2) {
			return false;
		} else {
			@Pc(180) int local180 = this.anInt417 * ((this.aClass31_Sub2_1.anInt7331 * arg0 + this.aClass31_Sub2_1.anInt7326 * arg1 + this.aClass31_Sub2_1.anInt7327 * arg2 >> 15) + this.aClass31_Sub2_1.anInt7324) / local23;
			@Pc(209) int local209 = this.anInt417 * ((this.aClass31_Sub2_1.anInt7331 * arg3 + this.aClass31_Sub2_1.anInt7326 * arg4 + this.aClass31_Sub2_1.anInt7327 * arg5 >> 15) + this.aClass31_Sub2_1.anInt7324) / local52;
			return (local180 >= this.viewY1 || local209 >= this.viewY1) && (local180 <= this.viewY2 || local209 <= this.viewY2);
		}
	}

	@OriginalMember(owner = "client!bc", name = "b", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void setCanvas(@OriginalArg(0) Canvas canvas2) {
		if (canvas2 == null) {
			this.canvas = null;
			this.aClass4_Sub22_1 = null;
			if (this.aClass57_5 == null) {
				this.surfaceRaster = null;
				this.anInt430 = this.surfaceHeight = 1;
				this.reset();
			}
			return;
		}
		@Pc(10) EnvironmentContext local10 = (EnvironmentContext) this.aClass102_3.method2700((long) canvas2.hashCode());
		if (local10 == null) {
			return;
		}
		this.canvas = canvas2;
		this.aClass4_Sub22_1 = local10;
		if (this.aClass57_5 != null) {
			return;
		}
		this.surfaceRaster = local10.anIntArray409;
		this.anInt430 = local10.anInt5857;
		this.surfaceHeight = local10.anInt5856;
		this.reset();
		return;
	}

	@OriginalMember(owner = "client!bc", name = "f", descriptor = "(I)Lclient!u;")
	@Override
	public MemoryPool createHeap(@OriginalArg(0) int size) {
		return null;
	}

	@OriginalMember(owner = "client!bc", name = "a", descriptor = "([Lclient!t;Lclient!mc;Lclient!c;[Lclient!mi;I)V")
	@Override
	public void method4299(@OriginalArg(0) Model[] arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon[] arg3, @OriginalArg(4) int arg4) {
		@Pc(3) JavaModel[] local3 = new JavaModel[arg0.length];
		for (@Pc(5) int local5 = 0; local5 < arg0.length; local5++) {
			if (arg0[local5] != null) {
				local3[local5] = (JavaModel) arg0[local5];
			}
		}
		@Pc(26) JavaModel local26 = JavaModel.method4912(this, local3);
		local26.method4900(arg2, arg3 == null ? null : arg3[0], arg1, arg4);
	}
}
