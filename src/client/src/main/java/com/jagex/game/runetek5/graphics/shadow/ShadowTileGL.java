package com.jagex.game.runetek5.graphics.shadow;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.scene.ground.GlGround;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.shadow.*;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
@OriginalClass("client!kh")
public final class ShadowTileGL {

	@OriginalMember(owner = "client!kh", name = "i", descriptor = "Z")
	public boolean needsTextureRegeneration = true;

	@OriginalMember(owner = "client!kh", name = "p", descriptor = "I")
	private int lastModifiedPixelCount = -1;

	@OriginalMember(owner = "client!kh", name = "c", descriptor = "I")
	private final int tileWidth;

	@OriginalMember(owner = "client!kh", name = "h", descriptor = "Lclient!uf;")
	private final GlShadowMap shadowMapper;

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "Lclient!fd;")
	private final GlToolkit toolkit;

	@OriginalMember(owner = "client!kh", name = "e", descriptor = "I")
	private final int tileLength;

	@OriginalMember(owner = "client!kh", name = "b", descriptor = "I")
	private final int materialCount;

	@OriginalMember(owner = "client!kh", name = "n", descriptor = "Lclient!bk;")
	private ArrayBufferGL anInterface1_4;

	@OriginalMember(owner = "client!kh", name = "m", descriptor = "Lclient!eo;")
	private Class64_Sub1 aClass64_Sub1_2;

	@OriginalMember(owner = "client!kh", name = "f", descriptor = "Lclient!af;")
	private OpenGl2DTexture cachedTexture;

	@OriginalMember(owner = "client!kh", name = "<init>", descriptor = "(Lclient!fd;Lclient!uf;Lclient!oq;IIIII)V")
	public ShadowTileGL(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) GlShadowMap glShadowMap, @OriginalArg(2) GlGround glGround, @OriginalArg(3) int chunkX, @OriginalArg(4) int chunkY, @OriginalArg(5) int arg5, @OriginalArg(6) int tileLength, @OriginalArg(7) int tileWidth) {
		this.tileWidth = tileWidth;
		this.shadowMapper = glShadowMap;
		this.toolkit = glToolkit;
		this.tileLength = tileLength;
		@Pc(25) int local25 = 0x1 << arg5;
		@Pc(27) int local27 = 0;
		@Pc(31) int local31 = chunkX << arg5;
		@Pc(35) int local35 = chunkY << arg5;
		@Pc(50) int local50;
		for (@Pc(37) int local37 = 0; local37 < local25; local37++) {
			@Pc(48) int local48 = glGround.anInt5262 * (local37 + local35) + local31;
			for (local50 = 0; local50 < local25; local50++) {
				@Pc(58) short[] local58 = glGround.tileVertexIndices[local48++];
				if (local58 != null) {
					local27 += local58.length;
				}
			}
		}
		this.materialCount = local27;
		if (local27 <= 0) {
			this.cachedTexture = null;
		} else {
			@Pc(96) Packet local96 = new Packet(local27 * 2);
			@Pc(115) int local115;
			@Pc(123) short[] local123;
			@Pc(127) int local127;
			@Pc(113) int local113;
			if (this.toolkit.bigEndianess) {
				for (local50 = 0; local50 < local25; local50++) {
					local113 = (local50 + local35) * glGround.anInt5262 + local31;
					for (local115 = 0; local115 < local25; local115++) {
						local123 = glGround.tileVertexIndices[local113++];
						if (local123 != null) {
							for (local127 = 0; local127 < local123.length; local127++) {
								local96.method2524(local123[local127] & 0xFFFF);
							}
						}
					}
				}
			} else {
				for (local50 = 0; local50 < local25; local50++) {
					local113 = local31 + glGround.anInt5262 * (local50 + local35);
					for (local115 = 0; local115 < local25; local115++) {
						local123 = glGround.tileVertexIndices[local113++];
						if (local123 != null) {
							for (local127 = 0; local127 < local123.length; local127++) {
								local96.method2541(local123[local127] & 0xFFFF);
							}
						}
					}
				}
			}
			this.anInterface1_4 = this.toolkit.method1955(local96.currentPosition, false, local96.buffer);
			this.aClass64_Sub1_2 = new Class64_Sub1(this.toolkit, 5123, null, 1);
		}
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(I)V")
	private void updateTexture() {
		if (!this.needsTextureRegeneration) {
			return;
		}
		this.needsTextureRegeneration = false;
		@Pc(16) byte[] local16 = this.shadowMapper.shadowMapperData;
		@Pc(18) byte[] local18 = LocalRequester.aByteArray102;
		@Pc(20) int local20 = 0;
		@Pc(24) int local24 = this.shadowMapper.width;
		@Pc(34) int local34 = this.shadowMapper.width * this.tileWidth + this.tileLength;
		@Pc(47) int local47;
		for (@Pc(36) int local36 = -128; local36 < 0; local36++) {
			local20 = (local20 << 8) - local20;
			for (local47 = -128; local47 < 0; local47++) {
				if (local16[local34++] != 0) {
					local20++;
				}
			}
			local34 += local24 - 128;
		}
		if (this.cachedTexture != null && local20 == this.lastModifiedPixelCount) {
			this.needsTextureRegeneration = false;
			return;
		}
		this.lastModifiedPixelCount = local20;
		local47 = 0;
		local34 = this.tileLength + this.tileWidth * local24;
		for (@Pc(96) int local96 = -128; local96 < 0; local96++) {
			for (@Pc(100) int local100 = -128; local100 < 0; local100++) {
				if (local16[local34] == 0) {
					@Pc(108) int local108 = 0;
					if (local16[local34 - 1] != 0) {
						local108++;
					}
					if (local16[local34 + 1] != 0) {
						local108++;
					}
					if (local16[local34 - local24] != 0) {
						local108++;
					}
					if (local16[local24 + local34] != 0) {
						local108++;
					}
					local18[local47++] = (byte) (local108 * 17);
				} else {
					local18[local47++] = 68;
				}
				local34++;
			}
			local34 += this.shadowMapper.width - 128;
		}
		if (this.cachedTexture == null) {
			this.cachedTexture = new OpenGl2DTexture(this.toolkit, 3553, 6406, 128, 128, false, LocalRequester.aByteArray102, 6406, false);
			this.cachedTexture.method4196(false, false);
			this.cachedTexture.method4181(true);
		} else {
			this.cachedTexture.method4191(128, LocalRequester.aByteArray102, 128, 6406, false);
		}
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(Lclient!bk;IZ)V")
	private void method3188(@OriginalArg(0) ArrayBufferGL arg0, @OriginalArg(1) int arg1) {
		if (arg1 != 0) {
			this.updateTexture();
			this.toolkit.setActiveTexture(this.cachedTexture);
			this.toolkit.method1967(0, arg1, arg0);
		}
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(B)V")
	public void drawShadow() {
		this.method3188(this.anInterface1_4, this.materialCount);
	}

	@OriginalMember(owner = "client!kh", name = "a", descriptor = "(B[BII)V")
	public void method3190(@OriginalArg(1) byte[] arg0, @OriginalArg(3) int arg1) {
		this.aClass64_Sub1_2.method3657(arg0, arg1 * this.toolkit.method1897(5123));
		this.method3188(this.aClass64_Sub1_2, arg1);
	}

@OriginalMember(owner = "client!kh", name = "k", descriptor = "I")
	public static int anInt3910;
@OriginalMember(owner = "client!kh", name = "l", descriptor = "I")
	public static int anInt3911 = 0;
}
