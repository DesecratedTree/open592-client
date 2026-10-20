package com.jagex.game.runetek5.config.material.texture;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static242;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static308;
import com.jagex.game.runetek5.deob.Static380;
import com.jagex.game.runetek5.config.material.texture.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.core.io.DummyInputStream;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.OpenGLArbTextureCubeMapGenerator;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class48_Sub1_Sub1;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
@OriginalClass("client!kv")
public final class TextureType extends CacheableNode {

	@OriginalMember(owner = "client!kv", name = "y", descriptor = "[I")
	private final int[] textureFileIds;

	@OriginalMember(owner = "client!kv", name = "N", descriptor = "[I")
	private final int[] materialFileIds;

	@OriginalMember(owner = "client!kv", name = "z", descriptor = "Lclient!oc;")
	private final TextureOperation hdrOperation;

	@OriginalMember(owner = "client!kv", name = "L", descriptor = "Lclient!oc;")
	private final TextureOperation opaqueOperation;

	@OriginalMember(owner = "client!kv", name = "Q", descriptor = "Lclient!oc;")
	private final TextureOperation opacityOperation;

	@OriginalMember(owner = "client!kv", name = "G", descriptor = "[Lclient!oc;")
	private final TextureOperation[] textureOperations;

	@OriginalMember(owner = "client!kv", name = "<init>", descriptor = "()V")
	private TextureType() {
		this.textureFileIds = new int[0];
		this.materialFileIds = new int[0];
		this.hdrOperation = new TextureOpConstant(0);
		this.hdrOperation.imageCacheCapacity = 1;
		this.opaqueOperation = new TextureOpConstant();
		this.opaqueOperation.imageCacheCapacity = 1;
		this.opacityOperation = new TextureOpConstant();
		this.opacityOperation.imageCacheCapacity = 1;
		this.textureOperations = new TextureOperation[] { this.opaqueOperation, this.opacityOperation, this.hdrOperation };
	}

	@OriginalMember(owner = "client!kv", name = "<init>", descriptor = "(Lclient!iv;)V")
	public TextureType(@OriginalArg(0) Packet packet) {
		@Pc(7) int local7 = packet.method2490();
		@Pc(9) int local9 = 0;
		@Pc(11) int local11 = 0;
		@Pc(14) int[][] local14 = new int[local7][];
		this.textureOperations = new TextureOperation[local7];
		@Pc(50) int local50;
		for (@Pc(20) int local20 = 0; local20 < local7; local20++) {
			@Pc(26) TextureOperation local26 = Class101.method2680(packet);
			if (local26.getSpriteId() >= 0) {
				local9++;
			}
			if (local26.getTextureId() >= 0) {
				local11++;
			}
			@Pc(43) int local43 = local26.operations.length;
			local14[local20] = new int[local43];
			for (local50 = 0; local50 < local43; local50++) {
				local14[local20][local50] = packet.method2490();
			}
			this.textureOperations[local20] = local26;
		}
		this.textureFileIds = new int[local9];
		local9 = 0;
		this.materialFileIds = new int[local11];
		local11 = 0;
		for (@Pc(91) int local91 = 0; local91 < local7; local91++) {
			@Pc(98) TextureOperation local98 = this.textureOperations[local91];
			local50 = local98.operations.length;
			for (@Pc(104) int local104 = 0; local104 < local50; local104++) {
				local98.operations[local104] = this.textureOperations[local14[local91][local104]];
			}
			@Pc(126) int local126 = local98.getSpriteId();
			@Pc(130) int local130 = local98.getTextureId();
			if (local126 > 0) {
				this.textureFileIds[local9++] = local126;
			}
			if (local130 > 0) {
				this.materialFileIds[local11++] = local130;
			}
			local14[local91] = null;
		}
		this.opaqueOperation = this.textureOperations[packet.method2490()];
		this.opacityOperation = this.textureOperations[packet.method2490()];
		this.hdrOperation = this.textureOperations[packet.method2490()];
	}

	@OriginalMember(owner = "client!kv", name = "a", descriptor = "(BLclient!fs;Lclient!ga;)Z")
	public boolean method3276(@OriginalArg(1) Js5ResourceProvider arg0, @OriginalArg(2) unpack_Sub1 arg1) {
		@Pc(9) int local9;
		if (SceneLocEntity.anInt3496 < 0) {
			for (local9 = 0; local9 < this.textureFileIds.length; local9++) {
				if (!arg0.method2125(this.textureFileIds[local9])) {
					return false;
				}
			}
		} else {
			for (local9 = 0; local9 < this.textureFileIds.length; local9++) {
				if (!arg0.method2098(this.textureFileIds[local9], SceneLocEntity.anInt3496)) {
					return false;
				}
			}
		}
		for (local9 = 0; local9 < this.materialFileIds.length; local9++) {
			if (!arg1.textureAvailable(this.materialFileIds[local9])) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!kv", name = "a", descriptor = "(Lclient!ga;IZZLclient!fs;DII)[I")
	public int[] method3277(@OriginalArg(0) unpack_Sub1 arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) Js5ResourceProvider arg3, @OriginalArg(5) double arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6) {
		EmissiveTriangle.aClass76_9 = arg3;
		Static331.anInterface7_7 = arg0;
		for (@Pc(11) int local11 = 0; local11 < this.textureOperations.length; local11++) {
			this.textureOperations[local11].initCache(arg5, arg6);
		}
		Skybox.method5254(arg4);
		SoundBankCache.method3974(arg5, arg6);
		@Pc(39) int[] local39 = new int[arg6 * arg5];
		@Pc(47) int local47;
		@Pc(43) int local43;
		@Pc(49) byte local49;
		if (arg1) {
			local43 = -1;
			local47 = arg6 - 1;
			local49 = -1;
		} else {
			local49 = 1;
			local43 = arg6;
			local47 = 0;
		}
		@Pc(59) int local59 = 0;
		for (@Pc(61) int local61 = 0; local61 < arg5; local61++) {
			@Pc(77) int[] local77;
			@Pc(85) int[] local85;
			@Pc(81) int[] local81;
			if (this.opaqueOperation.monochrome) {
				@Pc(93) int[] local93 = this.opaqueOperation.monochromeOutput(local61);
				local77 = local93;
				local85 = local93;
				local81 = local93;
			} else {
				@Pc(73) int[][] local73 = this.opaqueOperation.getColorOutput(local61);
				local77 = local73[0];
				local81 = local73[2];
				local85 = local73[1];
			}
			if (arg2) {
				local59 = local61;
			}
			for (@Pc(105) int local105 = local47; local105 != local43; local105 += local49) {
				@Pc(113) int local113 = local77[local105] >> 4;
				if (local113 > 255) {
					local113 = 255;
				}
				if (local113 < 0) {
					local113 = 0;
				}
				@Pc(133) int local133 = local85[local105] >> 4;
				if (local133 > 255) {
					local133 = 255;
				}
				if (local133 < 0) {
					local133 = 0;
				}
				@Pc(150) int local150 = local81[local105] >> 4;
				if (local150 > 255) {
					local150 = 255;
				}
				local113 = Class16_Sub3.anIntArray45[local113];
				if (local150 < 0) {
					local150 = 0;
				}
				local133 = Class16_Sub3.anIntArray45[local133];
				local150 = Class16_Sub3.anIntArray45[local150];
				@Pc(181) int local181 = local150 + (local113 << 16) + (local133 << 8);
				if (local181 != 0) {
					local181 |= 0xFF000000;
				}
				local39[local59++] = local181;
				if (arg2) {
					local59 += arg6 - 1;
				}
			}
		}
		for (@Pc(213) int local213 = 0; local213 < this.textureOperations.length; local213++) {
			this.textureOperations[local213].cacheReset();
		}
		return local39;
	}

	@OriginalMember(owner = "client!kv", name = "a", descriptor = "(IBLclient!fs;Lclient!ga;IDZ)[I")
	public int[] method3282(@OriginalArg(0) int arg0, @OriginalArg(2) Js5ResourceProvider arg1, @OriginalArg(3) unpack_Sub1 arg2, @OriginalArg(4) int arg3, @OriginalArg(5) double arg4, @OriginalArg(6) boolean arg5) {
		Static331.anInterface7_7 = arg2;
		EmissiveTriangle.aClass76_9 = arg1;
		for (@Pc(23) int local23 = 0; local23 < this.textureOperations.length; local23++) {
			this.textureOperations[local23].initCache(arg3, arg0);
		}
		Skybox.method5254(arg4);
		SoundBankCache.method3974(arg3, arg0);
		@Pc(57) int[] local57 = new int[arg3 * arg0 * 4];
		@Pc(59) int local59 = 0;
		for (@Pc(61) int local61 = 0; local61 < arg3; local61++) {
			@Pc(81) int[] local81;
			@Pc(79) int[] local79;
			@Pc(77) int[] local77;
			@Pc(75) int[] local75;
			if (this.opaqueOperation.monochrome) {
				local75 = this.opaqueOperation.monochromeOutput(local61);
				local77 = local75;
				local79 = local75;
				local81 = local75;
			} else {
				@Pc(89) int[][] local89 = this.opaqueOperation.getColorOutput(local61);
				local81 = local89[0];
				local79 = local89[1];
				local77 = local89[2];
			}
			if (arg5) {
				local59 = local61;
			}
			if (this.opacityOperation.monochrome) {
				local75 = this.opacityOperation.monochromeOutput(local61);
			} else {
				local75 = this.opacityOperation.getColorOutput(local61)[0];
			}
			for (@Pc(129) int local129 = arg0 - 1; local129 >= 0; local129--) {
				@Pc(137) int local137 = local81[local129] >> 4;
				if (local137 > 255) {
					local137 = 255;
				}
				if (local137 < 0) {
					local137 = 0;
				}
				@Pc(152) int local152 = local79[local129] >> 4;
				if (local152 > 255) {
					local152 = 255;
				}
				if (local152 < 0) {
					local152 = 0;
				}
				@Pc(170) int local170 = local77[local129] >> 4;
				if (local170 > 255) {
					local170 = 255;
				}
				if (local170 < 0) {
					local170 = 0;
				}
				local137 = Class16_Sub3.anIntArray45[local137];
				local152 = Class16_Sub3.anIntArray45[local152];
				local170 = Class16_Sub3.anIntArray45[local170];
				@Pc(210) int local210;
				if (local137 == 0 && local152 == 0 && local170 == 0) {
					local210 = 0;
				} else {
					local210 = local75[local129] >> 4;
					if (local210 > 255) {
						local210 = 255;
					}
					if (local210 < 0) {
						local210 = 0;
					}
				}
				local57[local59++] = local170 + (local152 << 8) + (local210 << 24) + (local137 << 16);
				if (arg5) {
					local59 += arg0 - 1;
				}
			}
		}
		for (@Pc(262) int local262 = 0; local262 < this.textureOperations.length; local262++) {
			this.textureOperations[local262].cacheReset();
		}
		return local57;
	}

	@OriginalMember(owner = "client!kv", name = "a", descriptor = "(ZLclient!ga;Lclient!fs;IZI)[F")
	public float[] method3283(@OriginalArg(1) unpack_Sub1 arg0, @OriginalArg(2) Js5ResourceProvider arg1, @OriginalArg(3) int arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) int arg4) {
		EmissiveTriangle.aClass76_9 = arg1;
		Static331.anInterface7_7 = arg0;
		for (@Pc(18) int local18 = 0; local18 < this.textureOperations.length; local18++) {
			this.textureOperations[local18].initCache(arg4, arg2);
		}
		SoundBankCache.method3974(arg4, arg2);
		@Pc(49) float[] local49 = new float[arg2 * 4 * arg4];
		@Pc(51) int local51 = 0;
		for (@Pc(53) int local53 = 0; local53 < arg4; local53++) {
			@Pc(73) int[] local73;
			@Pc(77) int[] local77;
			@Pc(69) int[] local69;
			if (this.opaqueOperation.monochrome) {
				@Pc(85) int[] local85 = this.opaqueOperation.monochromeOutput(local53);
				local69 = local85;
				local77 = local85;
				local73 = local85;
			} else {
				@Pc(65) int[][] local65 = this.opaqueOperation.getColorOutput(local53);
				local69 = local65[2];
				local73 = local65[0];
				local77 = local65[1];
			}
			@Pc(101) int[] local101;
			if (this.opacityOperation.monochrome) {
				local101 = this.opacityOperation.monochromeOutput(local53);
			} else {
				local101 = this.opacityOperation.getColorOutput(local53)[0];
			}
			if (arg3) {
				local51 = local53 << 2;
			}
			@Pc(127) int[] local127;
			if (this.hdrOperation.monochrome) {
				local127 = this.hdrOperation.monochromeOutput(local53);
			} else {
				local127 = this.hdrOperation.getColorOutput(local53)[0];
			}
			for (@Pc(141) int local141 = arg2 - 1; local141 >= 0; local141--) {
				@Pc(150) float local150 = (float) local101[local141] / 4096.0F;
				@Pc(163) float local163 = ((float) local127[local141] * 31.0F / 4096.0F + 1.0F) / 4096.0F;
				if (local150 < 0.0F) {
					local150 = 0.0F;
				} else if (local150 > 1.0F) {
					local150 = 1.0F;
				}
				local49[local51++] = (float) local73[local141] * local163;
				local49[local51++] = local163 * (float) local77[local141];
				local49[local51++] = local163 * (float) local69[local141];
				local49[local51++] = local150;
				if (arg3) {
					local51 += (arg2 << 2) - 4;
				}
			}
		}
		for (@Pc(231) int local231 = 0; local231 < this.textureOperations.length; local231++) {
			this.textureOperations[local231].cacheReset();
		}
		return local49;
	}

@OriginalMember(owner = "client!kv", name = "J", descriptor = "I")
	public static int anInt4042 = 0;
@OriginalMember(owner = "client!kv", name = "K", descriptor = "I")
	public static int anInt4043 = 1;
@OriginalMember(owner = "client!kv", name = "P", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_83 = new Class83("Face here", "Hierhin drehen", "Regarder dans cette direction", "Virar para cá");
@OriginalMember(owner = "client!kv", name = "e", descriptor = "(B)V")
	public static void method3275() {
		Class177.method4087(false);
		TextureOpIrregularBricks.lastMiscUpdateTick = 0;
		@Pc(12) boolean local12 = true;
		for (@Pc(14) int local14 = 0; local14 < Class99_Sub1.aByteArrayArray15.length; local14++) {
			if (Class136.anIntArray291[local14] != -1 && Class99_Sub1.aByteArrayArray15[local14] == null) {
				Class99_Sub1.aByteArrayArray15[local14] = Class4_Sub10.aClass76_27.method2104(0, Class136.anIntArray291[local14]);
				if (Class99_Sub1.aByteArrayArray15[local14] == null) {
					TextureOpIrregularBricks.lastMiscUpdateTick++;
					local12 = false;
				}
			}
			if (Class204.anIntArray418[local14] != -1 && GlFramebuffer.aByteArrayArray16[local14] == null) {
				GlFramebuffer.aByteArrayArray16[local14] = Class4_Sub10.aClass76_27.method2121(Class204.anIntArray418[local14], 0, Model.anIntArrayArray50[local14]);
				if (GlFramebuffer.aByteArrayArray16[local14] == null) {
					TextureOpIrregularBricks.lastMiscUpdateTick++;
					local12 = false;
				}
			}
			if (ProjectileAnimationNode.anIntArray155[local14] != -1 && Class259.aByteArrayArray30[local14] == null) {
				Class259.aByteArrayArray30[local14] = Class4_Sub10.aClass76_27.method2104(0, ProjectileAnimationNode.anIntArray155[local14]);
				if (Class259.aByteArrayArray30[local14] == null) {
					TextureOpIrregularBricks.lastMiscUpdateTick++;
					local12 = false;
				}
			}
			if (Game.anIntArray236[local14] != -1 && Class16_Sub4_Sub3.aByteArrayArray13[local14] == null) {
				Class16_Sub4_Sub3.aByteArrayArray13[local14] = Class4_Sub10.aClass76_27.method2104(0, Game.anIntArray236[local14]);
				if (Class16_Sub4_Sub3.aByteArrayArray13[local14] == null) {
					TextureOpIrregularBricks.lastMiscUpdateTick++;
					local12 = false;
				}
			}
			if (PlayerVarDomain.anIntArray222 != null && ReflectionCheck.aByteArrayArray1[local14] == null && PlayerVarDomain.anIntArray222[local14] != -1) {
				ReflectionCheck.aByteArrayArray1[local14] = Class4_Sub10.aClass76_27.method2121(PlayerVarDomain.anIntArray222[local14], 0, Model.anIntArrayArray50[local14]);
				if (ReflectionCheck.aByteArrayArray1[local14] == null) {
					TextureOpIrregularBricks.lastMiscUpdateTick++;
					local12 = false;
				}
			}
		}
		if (HookRequest.aClass195_2 == null) {
			if (TextureOpBlend.aClass4_Sub1_Sub5_2 == null || !Class4_Sub23.aClass76_39.method2107(TextureOpBlend.aClass4_Sub1_Sub5_2.filenamePrefix + "_staticelements")) {
				HookRequest.aClass195_2 = new StaticElements(0);
			} else if (Class4_Sub23.aClass76_39.method2103(TextureOpBlend.aClass4_Sub1_Sub5_2.filenamePrefix + "_staticelements")) {
				HookRequest.aClass195_2 = Static380.method5018(Class4_Sub23.aClass76_39, TextureOpBlend.aClass4_Sub1_Sub5_2.filenamePrefix + "_staticelements", MusicPatch.aBoolean506);
			} else {
				local12 = false;
				TextureOpIrregularBricks.lastMiscUpdateTick++;
			}
		}
		if (!local12) {
			PlayerAppearance.anInt883 = 1;
			return;
		}
		local12 = true;
		PathingEntitySequence.anInt5144 = 0;
		@Pc(266) int local266;
		@Pc(276) int local276;
		for (@Pc(248) int local248 = 0; local248 < Class99_Sub1.aByteArrayArray15.length; local248++) {
			@Pc(254) byte[] local254 = GlFramebuffer.aByteArrayArray16[local248];
			if (local254 != null) {
				local266 = (Static308.anIntArray372[local248] >> 8) * 64 - Class50_Sub1.anInt3453;
				local276 = (Static308.anIntArray372[local248] & 0xFF) * 64 - OpenGLTextureCache.anInt1771;
				if (MapRegion.anInt7307 != 0) {
					local266 = 10;
					local276 = 10;
				}
				local12 &= Class79_Sub1.method4871(GLBillowTextureData.anInt5666, local266, Static283.anInt5187, local254, local276);
			}
			local254 = Class16_Sub4_Sub3.aByteArrayArray13[local248];
			if (local254 != null) {
				local266 = (Static308.anIntArray372[local248] >> 8) * 64 - Class50_Sub1.anInt3453;
				local276 = (Static308.anIntArray372[local248] & 0xFF) * 64 - OpenGLTextureCache.anInt1771;
				if (MapRegion.anInt7307 != 0) {
					local266 = 10;
					local276 = 10;
				}
				local12 &= Class79_Sub1.method4871(GLBillowTextureData.anInt5666, local266, Static283.anInt5187, local254, local276);
			}
		}
		if (!local12) {
			PlayerAppearance.anInt883 = 2;
			return;
		}
		if (PlayerAppearance.anInt883 != 0) {
			TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423) + "<br>(100%)", Class6.aClass46_9);
		}
		CubeMapSpecularEffect.method2701();
		Class244.method5485();
		@Pc(386) boolean local386 = false;
		if (MonoStereoData.aClass19_16.method4261() && TextureOpBrightness.aClass50_Sub1_1.aBoolean294) {
			for (local266 = 0; local266 < Class99_Sub1.aByteArrayArray15.length; local266++) {
				if (Class16_Sub4_Sub3.aByteArrayArray13[local266] != null || Class259.aByteArrayArray30[local266] != null) {
					local386 = true;
					break;
				}
			}
		}
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean298) {
			local266 = GlBitBuffer.anIntArray189[Js5TextureSource.anInt2845];
		} else {
			local266 = Class16_Sub2.anIntArray302[Js5TextureSource.anInt2845];
		}
		if (MonoStereoData.aClass19_16.method4286()) {
			local266++;
		}
		Class4_Sub6_Sub23.method3677(GLBillowTextureData.anInt5666, Static283.anInt5187, local266, local386, MonoStereoData.aClass19_16.getMaxLights() > 0);
		for (local276 = 0; local276 < 4; local276++) {
			PlayerVarDomain.aClass213Array1[local276].method4851();
		}
		MeshBillboard.method3555();
		Class230.method5094(false);
		RequestManager.method3816();
		GlMemoryPool.aClass242_1 = null;
		CubeMapSpecularEffect.method2701();
		System.gc();
		Class177.method4087(true);
		MECType.method3788();
		Class4_Sub12_Sub1.anInt1262 = TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973);
		ObjStackEntity.aBoolean75 = BreakingWaterEffect.anInt1503 >= 96;
		Class191.aBoolean498 = TextureOpBrightness.aClass50_Sub1_1.aBoolean294;
		Static242.aBoolean418 = TextureOpBrightness.aClass50_Sub1_1.method2854(Packet.anInt2973);
		PositionedGfx.aBoolean263 = !TextureOpBrightness.aClass50_Sub1_1.aBoolean293;
		Class48_Sub1_Sub1.anInt4877 = TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973) ? -1 : FixedFunctionSpecularPass.anInt5523;
		TextureOpSquareWaveform.aBoolean667 = TextureOpBrightness.aClass50_Sub1_1.aBoolean307;
		GlParticleRenderer.aBoolean93 = Packet.anInt2973 == 1 || TextureOpBrightness.aClass50_Sub1_1.aBoolean289;
		Class4_Sub6_Sub4.aClass266_Sub1_1 = new MapRegion(4, GLBillowTextureData.anInt5666, Static283.anInt5187, false);
		if (MapRegion.anInt7307 == 0) {
			RemoteFileWorker.method3523(Class4_Sub6_Sub4.aClass266_Sub1_1, Class99_Sub1.aByteArrayArray15);
		} else {
			Class48_Sub1_Sub1.method3757(Class4_Sub6_Sub4.aClass266_Sub1_1, Class99_Sub1.aByteArrayArray15);
		}
		OpenGl2DTexture.method4194(GLBillowTextureData.anInt5666 >> 4, Static283.anInt5187 >> 4);
		DummyInputStream.method3623();
		if (local386) {
			OpenGLTextureCache.method1547(true);
			Class93.aClass266_Sub1_2 = new MapRegion(1, GLBillowTextureData.anInt5666, Static283.anInt5187, true);
			if (MapRegion.anInt7307 == 0) {
				RemoteFileWorker.method3523(Class93.aClass266_Sub1_2, Class259.aByteArrayArray30);
				Class177.method4087(true);
			} else {
				Class48_Sub1_Sub1.method3757(Class93.aClass266_Sub1_2, Class259.aByteArrayArray30);
				Class177.method4087(true);
			}
			Class93.aClass266_Sub1_2.method5633(Class4_Sub6_Sub4.aClass266_Sub1_1.tileHeights[0]);
			Class93.aClass266_Sub1_2.method5635(null, null, MonoStereoData.aClass19_16);
			OpenGLTextureCache.method1547(false);
		}
		Class4_Sub6_Sub4.aClass266_Sub1_1.method5635(local386 ? Class93.aClass266_Sub1_2.tileHeights : null, PlayerVarDomain.aClass213Array1, MonoStereoData.aClass19_16);
		if (MapRegion.anInt7307 == 0) {
			Class177.method4087(true);
			Class16_Sub5_Sub2.method4655(Class4_Sub6_Sub4.aClass266_Sub1_1, GlFramebuffer.aByteArrayArray16);
			if (ReflectionCheck.aByteArrayArray1 != null) {
				Mesh.method3044();
			}
		} else {
			Class177.method4087(true);
			Class95_Sub3.method4993(Class4_Sub6_Sub4.aClass266_Sub1_1, GlFramebuffer.aByteArrayArray16);
		}
		Class244.method5485();
		Class177.method4087(true);
		Class4_Sub6_Sub4.aClass266_Sub1_1.method5636(null, local386 ? FileWorker.aClass65Array3[0] : null, MonoStereoData.aClass19_16);
		Class4_Sub6_Sub4.aClass266_Sub1_1.method5651(MonoStereoData.aClass19_16);
		Class177.method4087(true);
		if (local386) {
			OpenGLTextureCache.method1547(true);
			Class177.method4087(true);
			if (MapRegion.anInt7307 == 0) {
				Class16_Sub5_Sub2.method4655(Class93.aClass266_Sub1_2, Class16_Sub4_Sub3.aByteArrayArray13);
			} else {
				Class95_Sub3.method4993(Class93.aClass266_Sub1_2, Class16_Sub4_Sub3.aByteArrayArray13);
			}
			Class244.method5485();
			Class177.method4087(true);
			Class93.aClass266_Sub1_2.method5636(Class4_Sub6_Sub4.aClass65Array1[0], null, MonoStereoData.aClass19_16);
			Class93.aClass266_Sub1_2.method5651(MonoStereoData.aClass19_16);
			Class177.method4087(true);
			OpenGLTextureCache.method1547(false);
		}
		QuickChatCategoryType.method3866();
		@Pc(729) int local729 = Class4_Sub6_Sub4.aClass266_Sub1_1.minLevel;
		if (local729 > Class4_Sub6_Sub25.anInt4963) {
			local729 = Class4_Sub6_Sub25.anInt4963;
		}
		if (Class4_Sub6_Sub25.anInt4963 - 1 > local729) {
			local729 = Class4_Sub6_Sub25.anInt4963 - 1;
		}
		if (TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973)) {
			GameShell.method882(0);
		} else {
			GameShell.method882(local729);
		}
		@Pc(766) int local766;
		@Pc(770) int local770;
		for (@Pc(762) int local762 = 0; local762 < 4; local762++) {
			for (local766 = 0; local766 < GLBillowTextureData.anInt5666; local766++) {
				for (local770 = 0; local770 < Static283.anInt5187; local770++) {
					MapSpriteTypeList.method5595(local762, local766, local770);
				}
			}
		}
		OpenGLArbTextureCubeMapGenerator.method3238();
		CubeMapSpecularEffect.method2701();
		Static1.method1();
		Class244.method5485();
		FloTypeList.aBoolean362 = false;
		FixedFunctionSpecularPass.method4231();
		if (ModelNormalsGL.aFrame1 != null && QuickchatMessageTypeList.aClass11_5 != null && VertexDeclarationComponent.anInt6667 == 25) {
			PingWorker.method5476(Class4_Sub6_Sub38.aClass215_94);
			ParticleNode.aClass4_Sub12_Sub1_5.method2531(1057001181);
		}
		if (MapRegion.anInt7307 == 0) {
			local766 = (IDKTypeList.anInt3265 - (GLBillowTextureData.anInt5666 >> 4)) / 8;
			local770 = (IDKTypeList.anInt3265 + (GLBillowTextureData.anInt5666 >> 4)) / 8;
			@Pc(856) int local856 = (TileMessage.anInt7373 - (Static283.anInt5187 >> 4)) / 8;
			@Pc(864) int local864 = (TileMessage.anInt7373 + (Static283.anInt5187 >> 4)) / 8;
			for (@Pc(868) int local868 = local766 - 1; local868 <= local770 + 1; local868++) {
				for (@Pc(874) int local874 = local856 - 1; local874 <= local864 + 1; local874++) {
					if (local766 > local868 || local770 < local868 || local874 < local856 || local864 < local874) {
						Class4_Sub10.aClass76_27.method2126("m" + local868 + "_" + local874);
						Class4_Sub10.aClass76_27.method2126("l" + local868 + "_" + local874);
					}
				}
			}
		}
		if (VertexDeclarationComponent.anInt6667 == 28) {
			Huffman.method2932(10);
		} else {
			Huffman.method2932(30);
			if (QuickchatMessageTypeList.aClass11_5 != null) {
				PingWorker.method5476(SpriteGL.aClass215_90);
			}
		}
		MonochromeImageCache.method2262();
		CubeMapSpecularEffect.method2701();
		GlVboArrayBuffer.method3660();
	}
@OriginalMember(owner = "client!kv", name = "a", descriptor = "(II[Lclient!uu;IIZ)V")
	public static void method3278(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component[] arg2, @OriginalArg(4) int arg3, @OriginalArg(5) boolean arg4) {
		for (@Pc(8) int local8 = 0; local8 < arg2.length; local8++) {
			@Pc(14) com.jagex.game.runetek5.config.rsinterface.Component local14 = arg2[local8];
			if (local14 != null && local14.anInt6850 == arg3) {
				Ground.method5658(arg4, arg0, local14, arg1);
				Class186.method4160(local14, arg0, arg1);
				if (local14.anInt6825 > local14.anInt6821 - local14.anInt6833) {
					local14.anInt6825 = local14.anInt6821 - local14.anInt6833;
				}
				if (local14.anInt6825 < 0) {
					local14.anInt6825 = 0;
				}
				if (local14.anInt6849 > local14.anInt6834 - local14.anInt6805) {
					local14.anInt6849 = local14.anInt6834 - local14.anInt6805;
				}
				if (local14.anInt6849 < 0) {
					local14.anInt6849 = 0;
				}
				if (local14.anInt6840 == 0) {
					Class95_Sub3_Sub1.method4998(local14, arg4);
				}
			}
		}
	}
@OriginalMember(owner = "client!kv", name = "a", descriptor = "(I)I")
	public static int method3279() {
		return TextureOpDiagonalWaves.anInt4008;
	}
@OriginalMember(owner = "client!kv", name = "a", descriptor = "(IC)Z")
	public static boolean method3280(@OriginalArg(1) char arg0) {
		return arg0 >= '0' && arg0 <= '9' || arg0 >= 'A' && arg0 <= 'Z' || arg0 >= 'a' && arg0 <= 'z';
	}
@OriginalMember(owner = "client!kv", name = "a", descriptor = "(ZIZ)V")
	public static void method3281(@OriginalArg(0) boolean arg0, @OriginalArg(2) boolean arg1) {
		if (arg1) {
			EasedAngle.anInt855--;
			if (EasedAngle.anInt855 == 0) {
				Class9_Sub3.anIntArray115 = null;
			}
		}
		if (arg0) {
			Class4_Sub25.anInt3550--;
			if (Class4_Sub25.anInt3550 == 0) {
				PathingEntity.anIntArray423 = null;
			}
		}
	}
}
