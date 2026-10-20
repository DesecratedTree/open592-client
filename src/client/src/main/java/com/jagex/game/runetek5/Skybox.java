package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.config.sun.Sun;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static145;
import com.jagex.game.runetek5.deob.Static281;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.deob.Static449;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.entity.EntityNode;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class4_Sub31;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.graphics.font.FontMetrics;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.entity.ProjectileAnimation;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
@OriginalClass("client!un")
public final class Skybox {

	@OriginalMember(owner = "client!un", name = "g", descriptor = "I")
	private int anInt6712;

	@OriginalMember(owner = "client!un", name = "j", descriptor = "I")
	private int anInt6714;

	@OriginalMember(owner = "client!un", name = "n", descriptor = "Lclient!l;")
	private NativeSprite backgroundSprite;

	@OriginalMember(owner = "client!un", name = "a", descriptor = "Z")
	private boolean prepareSun = true;

	@OriginalMember(owner = "client!un", name = "m", descriptor = "I")
	private int anInt6716 = -1;

	@OriginalMember(owner = "client!un", name = "o", descriptor = "[Lclient!ad;")
	private final Sun[] skyBoxSpheres;

	@OriginalMember(owner = "client!un", name = "t", descriptor = "I")
	private final int anInt6721;

	@OriginalMember(owner = "client!un", name = "c", descriptor = "I")
	private final int positionY;

	@OriginalMember(owner = "client!un", name = "v", descriptor = "I")
	private final int anInt6722;

	@OriginalMember(owner = "client!un", name = "p", descriptor = "I")
	private final int anInt6717;

	@OriginalMember(owner = "client!un", name = "u", descriptor = "[Lclient!ad;")
	private final Sun[] aClass5Array2;

	@OriginalMember(owner = "client!un", name = "b", descriptor = "Lclient!ad;")
	private final Sun aClass5_1;

	@OriginalMember(owner = "client!un", name = "<init>", descriptor = "(I[Lclient!ad;IIII)V")
	public Skybox(@OriginalArg(0) int materialId, @OriginalArg(1) Sun[] arg1, @OriginalArg(2) int defaultSunIndex, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int positionZ) {
		this.skyBoxSpheres = arg1;
		this.anInt6721 = materialId;
		this.positionY = arg3;
		this.anInt6722 = arg4;
		this.anInt6717 = positionZ;
		if (arg1 == null) {
			this.aClass5_1 = null;
			this.aClass5Array2 = null;
		} else {
			this.aClass5Array2 = new Sun[arg1.length];
			this.aClass5_1 = defaultSunIndex >= 0 ? arg1[defaultSunIndex] : null;
		}
	}

	@OriginalMember(owner = "client!un", name = "a", descriptor = "(BILclient!ya;)Z")
	public boolean method5248(@OriginalArg(1) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1) {
		@Pc(57) int local57;
		if (this.anInt6716 != arg0) {
			this.anInt6716 = arg0;
			@Pc(20) int local20 = SpriteDrawQueue.method4949(arg0);
			if (local20 > arg0) {
				local20 = CubeMapSpecularEffect.method2696(arg0);
			}
			if (local20 > 512) {
				local20 = 512;
			}
			if (local20 != this.anInt6714) {
				this.backgroundSprite = null;
				this.anInt6714 = local20;
			}
			if (this.skyBoxSpheres != null) {
				this.anInt6712 = 0;
				@Pc(55) int[] local55 = new int[this.skyBoxSpheres.length];
				for (local57 = 0; local57 < this.skyBoxSpheres.length; local57++) {
					@Pc(64) Sun local64 = this.skyBoxSpheres[local57];
					if (local64.update(this.positionY, this.anInt6722, this.anInt6717, this.anInt6716)) {
						local55[this.anInt6712] = local64.distance;
						this.aClass5Array2[this.anInt6712++] = local64;
					}
				}
				Class4_Sub31.method3813(local55, this.anInt6712 - 1, 0, this.aClass5Array2);
			}
			this.prepareSun = true;
		}
		@Pc(121) boolean local121 = false;
		if (this.prepareSun) {
			this.prepareSun = false;
			for (local57 = this.anInt6712 - 1; local57 >= 0; local57--) {
				@Pc(143) boolean local143 = this.aClass5Array2[local57].isSunGenerated(arg1, this.aClass5_1);
				this.prepareSun |= !local143;
				local121 |= local143;
			}
		}
		return local121;
	}

	@OriginalMember(owner = "client!un", name = "c", descriptor = "(I)V")
	public void method5253() {
		if (this.skyBoxSpheres != null) {
			for (@Pc(6) int local6 = 0; local6 < this.skyBoxSpheres.length; local6++) {
				this.skyBoxSpheres[local6].resetSprite();
			}
		}
		this.backgroundSprite = null;
	}

	@OriginalMember(owner = "client!un", name = "a", descriptor = "(IIIIILclient!ya;IIII)V")
	public void method5255(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7, @OriginalArg(9) int arg8) {
		@Pc(12) int local12 = arg3 + arg0 & 0x3FFF;
		if (this.anInt6721 == -1) {
			arg5.O(arg1, arg2, arg6, arg8, arg7, 0);
		} else {
			@Pc(32) MaterialType local32 = Class4_Sub6_Sub35.anInterface7_8.getMetrics(this.anInt6721);
			if (this.backgroundSprite == null && Class4_Sub6_Sub35.anInterface7_8.textureAvailable(this.anInt6721)) {
				@Pc(70) int[] local70 = local32.aBoolean640 ? Class4_Sub6_Sub35.anInterface7_8.getPixels(false, this.anInt6721, 0.7F, this.anInt6714, this.anInt6714) : Class4_Sub6_Sub35.anInterface7_8.getPixelsArgb(this.anInt6721, 0.7F, this.anInt6714, this.anInt6714);
				this.backgroundSprite = arg5.method4265(local70, this.anInt6714, this.anInt6714, this.anInt6714);
			}
			if (!local32.aBoolean640) {
				arg5.O(arg1, arg2, arg6, arg8, arg7, 0);
			}
			if (this.backgroundSprite != null) {
				@Pc(103) int local103 = local32.aBoolean640 ? 0 : 1;
				@Pc(109) int local109 = arg4 * arg8 / -4096;
				@Pc(121) int local121;
				for (local121 = (arg6 - arg8) / 2 + arg8 * local12 / 4096; local121 > arg8; local121 -= arg8) {
				}
				while (local121 < 0) {
					local121 += arg8;
				}
				while (arg8 < local109) {
					local109 -= arg8;
				}
				while (local109 < 0) {
					local109 += arg8;
				}
				for (@Pc(167) int local167 = local121 - arg8; local167 < arg6; local167 += arg8) {
					for (@Pc(174) int local174 = local109 - arg8; local174 < arg8; local174 += arg8) {
						this.backgroundSprite.renderImpl(arg1 + local167, local174 + arg2, arg8, arg8, 1, 0, local103);
					}
				}
			}
		}
		for (@Pc(209) int local209 = this.anInt6712 - 1; local209 >= 0; local209--) {
			this.aClass5Array2[local209].calculateProjection(arg5, arg1, arg2, arg6, arg8, arg4, local12);
		}
	}

@OriginalMember(owner = "client!un", name = "l", descriptor = "[Lclient!kf;")
	public static Class78_Sub1[] aClass78_Sub1Array2;
@OriginalMember(owner = "client!un", name = "w", descriptor = "I")
	public static int anInt6723;
@OriginalMember(owner = "client!un", name = "e", descriptor = "I")
	public static int anInt6710 = 0;
@OriginalMember(owner = "client!un", name = "i", descriptor = "[I")
	public static final int[] anIntArray513 = new int[4096];
@OriginalMember(owner = "client!un", name = "r", descriptor = "I")
	public static int anInt6719 = 0;
@OriginalMember(owner = "client!un", name = "a", descriptor = "(IBIZII)V")
	public static void method5247(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4) {
		if (Class70.aClass164ArrayArrayArray5 == null) {
			MonoStereoData.aClass19_16.method4293(arg0, arg1, arg3, -16777216, arg4);
		} else if (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >= 0 && GLBillowTextureData.anInt5666 * 128 > Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 && Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >= 0 && Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 < Static283.anInt5187 * 128) {
			Class79_Sub1.anInt6264++;
			if (Static1.aClass16_Sub1_Sub5_Sub1_1 != null && Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 - (Static1.aClass16_Sub1_Sub5_Sub1_1.getSize() - 1) * 64 >> 7 == Class30.anInt771 && Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 - (Static1.aClass16_Sub1_Sub5_Sub1_1.getSize() - 1) * 64 >> 7 == GlRenderList.anInt1094) {
				Class30.anInt771 = -1;
				GlRenderList.anInt1094 = -1;
				Class53.method1365();
			}
			Class27_Sub2.method2357();
			if (!arg2) {
				Class4_Sub6_Sub7.method1683();
			}
			Static449.method5654();
			GameShell.method878(arg3, arg1, arg0, true, arg4);
			@Pc(113) int local113 = Class196.anInt5685;
			@Pc(115) int local115 = Class16_Sub4_Sub2.anInt2527;
			@Pc(117) int local117 = ProjectileAnimation.anInt2574;
			@Pc(119) int local119 = ProjectileAnimation.anInt2600;
			@Pc(127) int local127;
			@Pc(164) int local164;
			if (Class3_Sub3.anInt2386 == 1) {
				local127 = (int) Class16_Sub2.aFloat142;
				if (GlBitBuffer.anInt3012 >> 8 > local127) {
					local127 = GlBitBuffer.anInt3012 >> 8;
				}
				if (TextureOpBoxBlur.aBooleanArray17[4] && ObjStackEntity.anIntArray46[4] + 128 > local127) {
					local127 = ObjStackEntity.anIntArray46[4] + 128;
				}
				local164 = (int) Static368.aFloat192 + Class164.anInt5119 & 0x3FFF;
				Class4_Sub6_Sub16.method3072(JavaMatrix.anInt6578, (local127 >> 3) * 3 + 600 << 0, MusicPatch.anInt5636, local164, BufferedSocket.method135(Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893, Class4_Sub6_Sub25.anInt4963, Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892) - 50, local115, local127);
			} else if (Class3_Sub3.anInt2386 == 4) {
				local127 = (int) Class16_Sub2.aFloat142;
				if (GlBitBuffer.anInt3012 >> 8 > local127) {
					local127 = GlBitBuffer.anInt3012 >> 8;
				}
				if (TextureOpBoxBlur.aBooleanArray17[4] && local127 < ObjStackEntity.anIntArray46[4] + 128) {
					local127 = ObjStackEntity.anIntArray46[4] + 128;
				}
				local164 = (int) Static368.aFloat192 & 0x3FFF;
				Class4_Sub6_Sub16.method3072(JavaMatrix.anInt6578, (local127 >> 3) * 3 + 600 << 0, MusicPatch.anInt5636, local164, BufferedSocket.method135(Js5WorkerThread.anInt7059, Class4_Sub6_Sub25.anInt4963, Class4_Sub45.anInt7360) - 50, local115, local127);
			} else if (Class3_Sub3.anInt2386 == 5) {
				Class4_Sub1_Sub11.method2610(local115);
			}
			local127 = Class4_Sub23.anInt3197;
			local164 = TextureOpIrregularBricks.anInt2024;
			@Pc(276) int local276 = Static145.anInt2906;
			@Pc(278) int local278 = QuickChatPhrase.anInt7074;
			@Pc(280) int local280 = FontMetrics.anInt2790;
			@Pc(323) int local323;
			for (@Pc(282) int local282 = 0; local282 < 5; local282++) {
				if (TextureOpBoxBlur.aBooleanArray17[local282]) {
					local323 = (int) (Math.random() * (double) (Static105.anIntArray161[local282] * 2 + 1) - (double) Static105.anIntArray161[local282] + Math.sin((double) BzipDecompressor.anIntArray65[local282] / 100.0D * (double) Class95_Sub3.anIntArray486[local282]) * (double) ObjStackEntity.anIntArray46[local282]);
					if (local282 == 3) {
						FontMetrics.anInt2790 = FontMetrics.anInt2790 + local323 & 0x3FFF;
					}
					if (local282 == 1) {
						TextureOpIrregularBricks.anInt2024 += local323;
					}
					if (local282 == 0) {
						Class4_Sub23.anInt3197 += local323;
					}
					if (local282 == 4) {
						QuickChatPhrase.anInt7074 += local323;
						if (QuickChatPhrase.anInt7074 < 1024) {
							QuickChatPhrase.anInt7074 = 1024;
						} else if (QuickChatPhrase.anInt7074 > 3072) {
							QuickChatPhrase.anInt7074 = 3072;
						}
					}
					if (local282 == 2) {
						Static145.anInt2906 += local323;
					}
				}
			}
			if (Class4_Sub23.anInt3197 < 0) {
				Class4_Sub23.anInt3197 = 0;
			}
			if (Class4_Sub23.anInt3197 > (GpuFaceNormal.menuOpenWidth << 7) - 1) {
				Class4_Sub23.anInt3197 = (GpuFaceNormal.menuOpenWidth << 7) - 1;
			}
			if (Static145.anInt2906 < 0) {
				Static145.anInt2906 = 0;
			}
			if ((GlModel.anInt3361 << 7) - 1 < Static145.anInt2906) {
				Static145.anInt2906 = (GlModel.anInt3361 << 7) - 1;
			}
			EntityNode.method5677();
			DummyInputStream.method3623();
			MonoStereoData.aClass19_16.OA(local119, local117, local119 + local113, local117 + local115);
			MonoStereoData.aClass19_16.method4266();
			local323 = Js5MasterIndex.anInt6110;
			if (GlMemoryPool.aClass242_1 == null) {
				MonoStereoData.aClass19_16.p(local323);
			} else {
				GlMemoryPool.aClass242_1.method5255(TextureOpIrregularBricks.anInt2023 << 3, local119, local117, FontMetrics.anInt2790, QuickChatPhrase.anInt7074, MonoStereoData.aClass19_16, local113, local323, local115);
			}
			CubeMapSpecularEffect.method2701();
			Class3_Sub3.aClass31_1.s(Class4_Sub23.anInt3197, TextureOpIrregularBricks.anInt2024, Static145.anInt2906, -QuickChatPhrase.anInt7074 & 0x3FFF, -FontMetrics.anInt2790 & 0x3FFF, -Class70_Sub1.anInt2070 & 0x3FFF);
			MonoStereoData.aClass19_16.ea(Class3_Sub3.aClass31_1);
			MonoStereoData.aClass19_16.H(local113 / 2 + local119, local117 + local115 / 2, AbstractFontRenderer.anInt6458 << 1, AbstractFontRenderer.anInt6458 << 1);
			TextureOpIrregularBricks.method1772(AbstractFontRenderer.anInt6458 << 1, local113 / 2 + local119, local115 / 2 + local117, AbstractFontRenderer.anInt6458 << 1);
			Class267.method5621(-FontMetrics.anInt2790 & 0x3FFF, -Class70_Sub1.anInt2070 & 0x3FFF, Class4_Sub23.anInt3197, Static145.anInt2906, -QuickChatPhrase.anInt7074 & 0x3FFF, TextureOpIrregularBricks.anInt2024);
			@Pc(548) byte local548 = TextureOpBrightness.aClass50_Sub1_1.method2858(Packet.anInt2973) == 2 ? (byte) Class79_Sub1.anInt6264 : 1;
			Class62.method1610(MonoStereoData.aClass19_16, Shadow.anInt5323, TextureType.anInt4043, Class3_Sub3.aClass31_1, Class4_Sub23.anInt3197, TextureOpIrregularBricks.anInt2024, Static145.anInt2906, Class10.aByteArrayArrayArray1, Class183.anIntArray360, QuickchatMessageType.DYNAMIC_CORNER_WALL_SIDE_MASKS, MapSpriteTypeList.anIntArray549, Class190.anIntArray376, Class38.anIntArray53, Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82 + 1, local548, Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7, Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7, !TextureOpBrightness.aClass50_Sub1_1.aBoolean295);
			CubeMapSpecularEffect.method2701();
			if (VertexDeclarationComponent.anInt6667 == 30) {
				Class92.method2429(local115, local117, local113, local119);
				Class65.method4027(local113, local117, local119, local115);
				MusicPatch.method4395(local117, local113, local115, local119);
				Class186.method4157(local115, local113, local119, local117);
			}
			GameShell.method873();
			Class4_Sub23.anInt3197 = local127;
			TextureOpIrregularBricks.anInt2024 = local164;
			Static145.anInt2906 = local276;
			FontMetrics.anInt2790 = local280;
			QuickChatPhrase.anInt7074 = local278;
			if (Resampler.aBoolean676 && MECType.aClass255_2.getUrgentRequestCount() == 0) {
				Resampler.aBoolean676 = false;
			}
			if (Resampler.aBoolean676) {
				MonoStereoData.aClass19_16.method4293(local119, local113, local117, -16777216, local115);
				TextureOpSquareWaveform.method5519(false, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423), Class6.aClass46_9);
			}
		} else {
			MonoStereoData.aClass19_16.method4293(arg0, arg1, arg3, -16777216, arg4);
		}
	}
@OriginalMember(owner = "client!un", name = "a", descriptor = "(IIBZIZI)V")
	public static void method5249(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) int arg3, @OriginalArg(5) boolean arg4, @OriginalArg(6) int arg5) {
		if (arg0 >= arg3) {
			return;
		}
		@Pc(18) int local18 = (arg0 + arg3) / 2;
		@Pc(20) int local20 = arg0;
		@Pc(24) Class78_Sub1 local24 = ParticleBinaryTree.aClass78_Sub1Array1[local18];
		ParticleBinaryTree.aClass78_Sub1Array1[local18] = ParticleBinaryTree.aClass78_Sub1Array1[arg3];
		ParticleBinaryTree.aClass78_Sub1Array1[arg3] = local24;
		for (@Pc(36) int local36 = arg0; local36 < arg3; local36++) {
			if (Static308.method4155(arg5, ParticleBinaryTree.aClass78_Sub1Array1[local36], arg2, local24, arg1, arg4) <= 0) {
				@Pc(56) Class78_Sub1 local56 = ParticleBinaryTree.aClass78_Sub1Array1[local36];
				ParticleBinaryTree.aClass78_Sub1Array1[local36] = ParticleBinaryTree.aClass78_Sub1Array1[local20];
				ParticleBinaryTree.aClass78_Sub1Array1[local20++] = local56;
			}
		}
		ParticleBinaryTree.aClass78_Sub1Array1[arg3] = ParticleBinaryTree.aClass78_Sub1Array1[local20];
		ParticleBinaryTree.aClass78_Sub1Array1[local20] = local24;
		method5249(arg0, arg1, arg2, local20 - 1, arg4, arg5);
		method5249(local20 + 1, arg1, arg2, arg3, arg4, arg5);
	}
@OriginalMember(owner = "client!un", name = "a", descriptor = "(I)Lclient!kf;")
	public static Class78_Sub1 method5250() {
		AbstractArbBufferGL.anInt6617 = 0;
		return Static281.method3960();
	}
@OriginalMember(owner = "client!un", name = "a", descriptor = "(IIII)I")
	public static int method5252(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(7) int local7 = arg1 & 0x3;
		if (local7 == 0) {
			return arg2;
		} else if (local7 == 1) {
			return arg0;
		} else if (local7 == 2) {
			return 7 - arg2;
		} else {
			return 7 - arg0;
		}
	}
@OriginalMember(owner = "client!un", name = "a", descriptor = "(DB)V")
	public static void method5254(@OriginalArg(0) double arg0) {
		if (Class9_Sub3.aDouble1 == arg0) {
			return;
		}
		for (@Pc(7) int local7 = 0; local7 < 256; local7++) {
			@Pc(19) int local19 = (int) (Math.pow((double) local7 / 255.0D, arg0) * 255.0D);
			Class16_Sub3.anIntArray45[local7] = local19 <= 255 ? local19 : 255;
		}
		Class9_Sub3.aDouble1 = arg0;
	}
}
