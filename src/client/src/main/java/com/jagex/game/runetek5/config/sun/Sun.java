package com.jagex.game.runetek5.config.sun;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import com.jagex.game.runetek5.config.sun.*;
import com.jagex.game.runetek5.config.sun.Sun;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpInterpolate;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
@OriginalClass("client!ad")
public final class Sun {

	@OriginalMember(owner = "client!ad", name = "b", descriptor = "I")
	private int sunRotationX;

	@OriginalMember(owner = "client!ad", name = "d", descriptor = "I")
	public int distance;

	@OriginalMember(owner = "client!ad", name = "h", descriptor = "I")
	private int positionOffset;

	@OriginalMember(owner = "client!ad", name = "k", descriptor = "I")
	private int sunUseDimension;

	@OriginalMember(owner = "client!ad", name = "l", descriptor = "I")
	private int sunRotationY;

	@OriginalMember(owner = "client!ad", name = "r", descriptor = "Lclient!l;")
	private NativeSprite sunSprite;

	@OriginalMember(owner = "client!ad", name = "p", descriptor = "I")
	private final int x;

	@OriginalMember(owner = "client!ad", name = "e", descriptor = "I")
	private final int y;

	@OriginalMember(owner = "client!ad", name = "m", descriptor = "I")
	private final int z;

	@OriginalMember(owner = "client!ad", name = "c", descriptor = "Z")
	private final boolean invertPosition;

	@OriginalMember(owner = "client!ad", name = "a", descriptor = "I")
	private final int sunMaterialId;

	@OriginalMember(owner = "client!ad", name = "o", descriptor = "I")
	private final int renderType;

	@OriginalMember(owner = "client!ad", name = "q", descriptor = "I")
	private final int positionOffsetStep;

	@OriginalMember(owner = "client!ad", name = "g", descriptor = "I")
	private final int colour;

	@OriginalMember(owner = "client!ad", name = "<init>", descriptor = "(IIIIIIIZ)V")
	public Sun(@OriginalArg(0) int colour, @OriginalArg(1) int sunMaterialId, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) int positionOffsetStep, @OriginalArg(6) int renderType, @OriginalArg(7) boolean invertPosition) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.invertPosition = invertPosition;
		this.sunMaterialId = sunMaterialId;
		this.renderType = renderType;
		this.positionOffsetStep = positionOffsetStep;
		this.colour = colour;
	}

	@OriginalMember(owner = "client!ad", name = "a", descriptor = "(Lclient!ya;Lclient!ad;)V")
	private void drawColoredSun(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Sun sun) {
		@Pc(6) Mesh local6 = SpriteDrawQueue.method4948(TextureOpInterpolate.aClass76_38, this.sunMaterialId);
		if (local6 == null) {
			return;
		}
		toolkit.da(Sun.SUN_CLIPPING);
		toolkit.OA(0, 0, this.sunUseDimension, this.sunUseDimension);
		toolkit.O(0, 0, this.sunUseDimension, this.sunUseDimension, 0, 0);
		@Pc(32) int local32 = 0;
		@Pc(34) int local34 = 0;
		@Pc(36) int local36 = 256;
		if (sun != null) {
			if (sun.invertPosition) {
				local32 = -sun.x;
				local34 = -sun.y;
				local36 = -sun.z;
			} else {
				local32 = this.x - sun.x;
				local34 = this.y - sun.y;
				local36 = this.z - sun.z;
			}
		}
		@Pc(81) int local81;
		@Pc(85) int local85;
		@Pc(89) int local89;
		@Pc(99) int local99;
		if (this.sunRotationX != 0) {
			local81 = -this.sunRotationX & 0x3FFF;
			local85 = Class50_Sub1.anIntArray225[local81];
			local89 = Class50_Sub1.anIntArray224[local81];
			local99 = local34 * local89 - local36 * local85 >> 15;
			local36 = local34 * local85 + local36 * local89 >> 15;
			local34 = local99;
		}
		if (this.sunRotationY != 0) {
			local81 = -this.sunRotationY & 0x3FFF;
			local85 = Class50_Sub1.anIntArray225[local81];
			local89 = Class50_Sub1.anIntArray224[local81];
			local99 = local36 * local85 + local32 * local89 >> 15;
			local36 = local36 * local89 - local32 * local85 >> 15;
			local32 = local99;
		}
		toolkit.B(1.0F);
		toolkit.scaleHeight(16777215, 1.0F, 1.0F, (float) local32, (float) local34, (float) local36);
		@Pc(172) Model local172 = toolkit.createModel(local6, 2048, 0, 64, 768);
		local85 = local172.NA() - local172.HA();
		local89 = local172.WA() - local172.setOffsets();
		local99 = local172.HA() + local85 / 2;
		@Pc(198) int local198 = local172.setOffsets() + local89 / 2;
		@Pc(205) int local205 = local85 > local89 ? local85 : local89;
		toolkit.H(local99, local198, local205, local205);
		toolkit.ea(toolkit.method4274());
		@Pc(218) Ground local218 = toolkit.method4289();
		local218.scaleWidth(0, 0, toolkit.a() - local172.PA());
		local172.renderOrtho(local218, null, toolkit.a());
		this.sunSprite = toolkit.createNativeSprite(0, 0, this.sunUseDimension, this.sunUseDimension, true);
		this.sunSprite.copyAlpha(0, 0, 3);
		toolkit.OA(Sun.SUN_CLIPPING[0], Sun.SUN_CLIPPING[1], Sun.SUN_CLIPPING[2], Sun.SUN_CLIPPING[3]);
	}

	@OriginalMember(owner = "client!ad", name = "a", descriptor = "()V")
	public void resetSprite() {
		this.sunSprite = null;
	}

	@OriginalMember(owner = "client!ad", name = "b", descriptor = "(Lclient!ya;Lclient!ad;)Z")
	private boolean performAction(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Sun sun) {
		if (this.sunSprite == null) {
			if (this.colour == 0) {
				if (Class4_Sub6_Sub35.anInterface7_8.textureAvailable(this.sunMaterialId)) {
					@Pc(23) int[] local23 = Class4_Sub6_Sub35.anInterface7_8.getPixelsArgb(this.sunMaterialId, 0.7F, this.sunUseDimension, this.sunUseDimension);
					this.sunSprite = toolkit.method4265(local23, this.sunUseDimension, this.sunUseDimension, this.sunUseDimension);
				}
			} else if (this.colour == 2) {
				this.drawColoredSun(toolkit, sun);
			} else if (this.colour == 1) {
				this.drawTexturedSun(toolkit, sun);
			}
		}
		return this.sunSprite != null;
	}

	@OriginalMember(owner = "client!ad", name = "a", descriptor = "(IIII)Z")
	public boolean update(@OriginalArg(0) int offsetX, @OriginalArg(1) int offsetY, @OriginalArg(2) int offsetZ, @OriginalArg(3) int viewportHeight) {
		@Pc(7) int local7;
		@Pc(12) int local12;
		@Pc(17) int local17;
		if (this.invertPosition) {
			this.distance = 1073741823;
			local7 = this.x;
			local12 = this.y;
			local17 = this.z;
		} else {
			local7 = this.x - offsetX;
			local12 = this.y - offsetY;
			local17 = this.z - offsetZ;
			this.distance = (int) Math.sqrt((double) (local7 * local7 + local12 * local12 + local17 * local17));
			if (this.distance == 0) {
				this.distance = 1;
			}
			local7 = (local7 << 8) / this.distance;
			local12 = (local12 << 8) / this.distance;
			local17 = (local17 << 8) / this.distance;
		}
		@Pc(90) int local90 = (int) (Math.sqrt((double) (local7 * local7 + local12 * local12 + local17 * local17)) * 256.0D);
		if (local90 > 128) {
			local7 = (local7 << 16) / local90;
			local12 = (local12 << 16) / local90;
			local17 = (local17 << 16) / local90;
			this.positionOffset = this.positionOffsetStep * viewportHeight / (this.invertPosition ? 1024 : this.distance);
		} else {
			this.positionOffset = 0;
		}
		if (this.positionOffset < 8) {
			this.sunSprite = null;
			return false;
		}
		@Pc(143) int local143 = SpriteDrawQueue.method4949(this.positionOffset);
		if (local143 > viewportHeight) {
			local143 = CubeMapSpecularEffect.method2696(viewportHeight);
		}
		if (local143 > 512) {
			local143 = 512;
		}
		if (local143 != this.sunUseDimension) {
			this.sunUseDimension = local143;
		}
		this.sunRotationX = (int) (Math.asin((double) ((float) local12 / 256.0F)) * 5215.189175235227D) & 0x3FFF;
		this.sunRotationY = (int) (Math.atan2((double) local7, (double) -local17) * 5215.189175235227D) & 0x3FFF;
		this.sunSprite = null;
		return true;
	}

	@OriginalMember(owner = "client!ad", name = "c", descriptor = "(Lclient!ya;Lclient!ad;)V")
	private void drawTexturedSun(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Sun sun) {
		Sun.generateModel(toolkit);
		Sun.generateSunSprites(toolkit);
		toolkit.da(Sun.SUN_CLIPPING);
		toolkit.OA(0, 0, this.sunUseDimension, this.sunUseDimension);
		toolkit.method4266();
		toolkit.O(0, 0, this.sunUseDimension, this.sunUseDimension, this.renderType | 0xFF000000, 0);
		@Pc(31) int local31 = 0;
		@Pc(33) int local33 = 0;
		@Pc(35) int local35 = 256;
		if (sun != null) {
			if (sun.invertPosition) {
				local31 = -sun.x;
				local33 = -sun.y;
				local35 = -sun.z;
			} else {
				local31 = sun.x - this.x;
				local33 = sun.y - this.y;
				local35 = sun.z - this.z;
			}
		}
		@Pc(79) int local79;
		@Pc(84) int local84;
		@Pc(94) int local94;
		if (this.sunRotationX != 0) {
			local79 = Class50_Sub1.anIntArray225[this.sunRotationX];
			local84 = Class50_Sub1.anIntArray224[this.sunRotationX];
			local94 = local33 * local84 - local35 * local79 >> 15;
			local35 = local33 * local79 + local35 * local84 >> 15;
			local33 = local94;
		}
		if (this.sunRotationY != 0) {
			local79 = Class50_Sub1.anIntArray225[this.sunRotationY];
			local84 = Class50_Sub1.anIntArray224[this.sunRotationY];
			local94 = local35 * local79 + local31 * local84 >> 15;
			local35 = local35 * local84 - local31 * local79 >> 15;
			local31 = local94;
		}
		@Pc(147) Model local147 = Sun.sunModel.copy((byte) 0, 51200, true);
		if (toolkit.method4251()) {
			local147.I((short) 0, (short) this.sunMaterialId);
		} else {
			local147.copyAlpha((short) 127, Class4_Sub6_Sub35.anInterface7_8.getMetrics(this.sunMaterialId).colorHsl);
			local147.I((short) 0, (short) -1);
		}
		toolkit.B(1.0F);
		toolkit.scaleHeight(16777215, 1.0F, 1.0F, (float) local31, (float) local33, (float) local35);
		local84 = this.sunUseDimension * 1024 / (local147.NA() - local147.HA());
		if (this.renderType != 0) {
			local84 = local84 * 13 / 16;
		}
		toolkit.H(this.sunUseDimension / 2, this.sunUseDimension / 2, local84, local84);
		toolkit.ea(toolkit.method4274());
		@Pc(223) Ground local223 = toolkit.method4274();
		local223.scaleWidth(0, 0, toolkit.a() - local147.PA());
		local147.renderOrtho(local223, null, 1024);
		@Pc(245) int local245 = this.sunUseDimension * 13 / 16;
		@Pc(252) int local252 = (this.sunUseDimension - local245) / 2;
		Sun.activeAntiSun.renderImpl(local252, local252, local245, local245, 0, this.renderType | 0xFF000000, 1);
		this.sunSprite = toolkit.createNativeSprite(0, 0, this.sunUseDimension, this.sunUseDimension, true);
		toolkit.method4266();
		toolkit.O(0, 0, this.sunUseDimension, this.sunUseDimension, 0, 0);
		Sun.activeSun.renderImpl(0, 0, this.sunUseDimension, this.sunUseDimension, 1, 0, 0);
		this.sunSprite.copyAlpha(0, 0, 0);
		toolkit.OA(Sun.SUN_CLIPPING[0], Sun.SUN_CLIPPING[1], Sun.SUN_CLIPPING[2], Sun.SUN_CLIPPING[3]);
	}

	@OriginalMember(owner = "client!ad", name = "a", descriptor = "(Lclient!ya;IIIIII)V")
	public void calculateProjection(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int width, @OriginalArg(2) int height, @OriginalArg(3) int sunDeltaX, @OriginalArg(4) int sunDeltaY, @OriginalArg(5) int sunDeltaZ, @OriginalArg(6) int alpha) {
		if (this.sunSprite == null) {
			return;
		}
		@Pc(10) int local10 = this.sunRotationX - sunDeltaZ & 0x3FFF;
		@Pc(17) int local17 = this.sunRotationY - alpha & 0x3FFF;
		if (local17 > 8192) {
			local17 -= 16384;
		}
		if (local10 > 8192) {
			local10 -= 16384;
		}
		@Pc(38) int local38 = local10 * sunDeltaY / 4096 + (sunDeltaY - this.positionOffset) / 2;
		@Pc(51) int local51 = local17 * sunDeltaY / -4096 + (sunDeltaX - this.positionOffset) / 2;
		if (local38 < sunDeltaY && local38 + this.positionOffset > 0 && local51 < sunDeltaX && local51 + this.positionOffset > 0) {
			this.sunSprite.drawTintedScaled(local51 + width, local38 + height, this.positionOffset, this.positionOffset);
		}
	}

	@OriginalMember(owner = "client!ad", name = "d", descriptor = "(Lclient!ya;Lclient!ad;)Z")
	public boolean isSunGenerated(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Sun sun) {
		return this.sunSprite != null || this.performAction(toolkit, sun);
	}

@OriginalMember(owner = "client!ad", name = "f", descriptor = "Lclient!l;")
	public static NativeSprite activeSun;
@OriginalMember(owner = "client!ad", name = "i", descriptor = "Lclient!t;")
	public static Model sunModel;
@OriginalMember(owner = "client!ad", name = "n", descriptor = "Lclient!l;")
	public static NativeSprite activeAntiSun;
@OriginalMember(owner = "client!ad", name = "j", descriptor = "[I")
	public static final int[] SUN_CLIPPING = new int[4];
@OriginalMember(owner = "client!ad", name = "a", descriptor = "(Lclient!ya;)V")
	public static void generateModel(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit) {
		if (sunModel != null) {
			return;
		}
		@Pc(8) Mesh local8 = new Mesh(580, 1104, 1);
		local8.method3042();
		local8.method3040(0, 128, 0);
		local8.method3040(0, -128, 0);
		for (@Pc(37) int local37 = 0; local37 <= 24; local37++) {
			@Pc(44) int local44 = local37 * 8192 / 24;
			@Pc(48) int local48 = Class50_Sub1.anIntArray225[local44];
			@Pc(52) int local52 = Class50_Sub1.anIntArray224[local44];
			@Pc(61) int local61;
			@Pc(67) int local67;
			@Pc(75) int local75;
			@Pc(83) int local83;
			for (@Pc(54) int local54 = 1; local54 < 24; local54++) {
				local61 = local54 * 8192 / 24;
				local67 = Class50_Sub1.anIntArray224[local61] >> 8;
				local75 = Class50_Sub1.anIntArray225[local61] * local48 >> 23;
				local83 = Class50_Sub1.anIntArray225[local61] * local52 >> 23;
				local8.method3040(-local75, local67, local83);
			}
			if (local37 > 0) {
				local61 = local37 * 23 + 2;
				local67 = local61 - 23;
				local8.method3047(local61, (short) 0, (byte) 0, 0, local67, (short) 127, (byte) 0, (byte) 0);
				for (local75 = 1; local75 < 23; local75++) {
					local83 = local67 + 1;
					@Pc(130) int local130 = local61 + 1;
					local8.method3047(local61, (short) 0, (byte) 0, local67, local83, (short) 127, (byte) 0, (byte) 0);
					local8.method3047(local61, (short) 0, (byte) 0, local83, local130, (short) 127, (byte) 0, (byte) 0);
					local67 = local83;
					local61 = local130;
				}
				local8.method3047(1, (short) 0, (byte) 0, local61, local67, (short) 127, (byte) 0, (byte) 0);
			}
		}
		local8.maxDepth = local8.anInt3668;
		local8.anIntArray245 = null;
		local8.anIntArray246 = null;
		local8.aByteArray42 = null;
		sunModel = toolkit.createModel(local8, 51200, 33, 64, 768);
	}
@OriginalMember(owner = "client!ad", name = "b", descriptor = "(Lclient!ya;)V")
	public static void generateSunSprites(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit) {
		if (activeSun != null) {
			return;
		}
		@Pc(4) int[] local4 = new int[16384];
		@Pc(7) int[] local7 = new int[16384];
		for (@Pc(9) int local9 = 0; local9 < 64; local9++) {
			@Pc(14) int local14 = 64 - local9;
			@Pc(18) int local18 = local14 * local14;
			@Pc(24) int local24 = 128 - local9 - 1;
			@Pc(28) int local28 = local9 * 128;
			@Pc(32) int local32 = local24 * 128;
			for (@Pc(34) int local34 = 0; local34 < 64; local34++) {
				@Pc(39) int local39 = 64 - local34;
				@Pc(43) int local43 = local39 * local39;
				@Pc(49) int local49 = 128 - local34 - 1;
				@Pc(59) int local59 = 256 - (local43 + local18 << 8) / 4096;
				local59 = local59 * 16 * 192 / 1536;
				if (local59 < 0) {
					local59 = 0;
				} else if (local59 > 255) {
					local59 = 255;
				}
				@Pc(81) int local81 = local59 / 2;
				local7[local28 + local34] = local7[local28 + local49] = local7[local32 + local34] = local7[local32 + local49] = (local59 | 0xFF00) << 16;
				local4[local28 + local34] = local4[local28 + local49] = local4[local32 + local34] = local4[local32 + local49] = 127 - local81 << 24 | 0xFFFFFF;
			}
		}
		activeSun = toolkit.method4265(local7, 128, 128, 128);
		activeAntiSun = toolkit.method4265(local4, 128, 128, 128);
	}
@OriginalMember(owner = "client!ad", name = "c", descriptor = "()V")
	public static void resetMedia() {
		sunModel = null;
		activeSun = null;
	}
}
