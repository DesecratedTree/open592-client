package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import java.io.IOException;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.js5.NamedFileReference;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpCurve;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.Class225;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub12;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9_Sub2;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class103;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class25;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.MapRegion;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.client.input.mouse.SimpleMouseLog;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.deob.Class3_Sub4;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!is")
public final class FloType {

	@OriginalMember(owner = "client!is", name = "c", descriptor = "Lclient!kb;")
	public FloTypeList aClass124_4;

	@OriginalMember(owner = "client!is", name = "s", descriptor = "I")
	public int anInt3385;

	@OriginalMember(owner = "client!is", name = "e", descriptor = "I")
	public int anInt3373 = 8;

	@OriginalMember(owner = "client!is", name = "l", descriptor = "Z")
	public boolean aBoolean283 = false;

	@OriginalMember(owner = "client!is", name = "n", descriptor = "Z")
	public boolean aBoolean284 = true;

	@OriginalMember(owner = "client!is", name = "g", descriptor = "I")
	public int anInt3375 = -1;

	@OriginalMember(owner = "client!is", name = "j", descriptor = "I")
	public int anInt3378 = -1;

	@OriginalMember(owner = "client!is", name = "o", descriptor = "I")
	public int anInt3381 = 0;

	@OriginalMember(owner = "client!is", name = "f", descriptor = "I")
	public int anInt3374 = 128;

	@OriginalMember(owner = "client!is", name = "h", descriptor = "I")
	public int anInt3376 = 1190717;

	@OriginalMember(owner = "client!is", name = "u", descriptor = "Z")
	public boolean aBoolean285 = true;

	@OriginalMember(owner = "client!is", name = "r", descriptor = "I")
	public int anInt3384 = 127;

	@OriginalMember(owner = "client!is", name = "b", descriptor = "I")
	public int anInt3372 = -1;

	@OriginalMember(owner = "client!is", name = "q", descriptor = "I")
	public int anInt3383 = 16;

	@OriginalMember(owner = "client!is", name = "a", descriptor = "(Z)V")
	public void method2818() {
		this.anInt3373 = this.anInt3373 << 8 | this.anInt3385;
		if (this.anInt3375 == -1) {
			this.anInt3375 = this.anInt3378;
		}
	}

	@OriginalMember(owner = "client!is", name = "a", descriptor = "(ILclient!iv;I)V")
	private void method2819(@OriginalArg(1) Packet arg0, @OriginalArg(2) int arg1) {
		if (arg1 == 1) {
			this.anInt3381 = AbstractFontRenderer.method5061(arg0.method2493());
		} else if (arg1 == 2) {
			this.anInt3378 = arg0.method2490();
			return;
		} else if (arg1 == 3) {
			this.anInt3378 = arg0.method2536();
			if (this.anInt3378 == 65535) {
				this.anInt3378 = -1;
				return;
			}
		} else if (arg1 == 5) {
			this.aBoolean285 = false;
			return;
		} else if (arg1 == 7) {
			this.anInt3372 = AbstractFontRenderer.method5061(arg0.method2493());
			return;
		} else if (arg1 == 8) {
			this.aClass124_4.lastLoadedOverlayId = this.anInt3385;
			return;
		} else if (arg1 == 9) {
			this.anInt3374 = arg0.method2536() << 0;
			return;
		} else if (arg1 == 10) {
			this.aBoolean284 = false;
			return;
		} else if (arg1 == 11) {
			this.anInt3373 = arg0.method2490();
			return;
		} else if (arg1 == 12) {
			this.aBoolean283 = true;
			return;
		} else if (arg1 == 13) {
			this.anInt3376 = arg0.method2493();
			return;
		} else {
			if (arg1 == 14) {
				this.anInt3383 = arg0.method2490();
			} else if (arg1 == 15) {
				this.anInt3375 = arg0.method2536();
				if (this.anInt3375 == 65535) {
					this.anInt3375 = -1;
					return;
				}
			} else if (arg1 == 16) {
				this.anInt3384 = arg0.method2490();
				return;
			} else {
				return;
			}
			return;
		}
	}

	@OriginalMember(owner = "client!is", name = "a", descriptor = "(ILclient!iv;)V")
	public void method2822(@OriginalArg(1) Packet arg0) {
		while (true) {
			@Pc(5) int local5 = arg0.method2490();
			if (local5 == 0) {
				return;
			}
			this.method2819(arg0, local5);
		}
	}

@OriginalMember(owner = "client!is", name = "d", descriptor = "J")
	public static long aLong119 = -1L;
@OriginalMember(owner = "client!is", name = "a", descriptor = "(I)Z")
	public static boolean method2816() throws IOException {
		if (QuickchatMessageTypeList.aClass11_5 == null) {
			return false;
		}
		@Pc(13) int local13 = QuickchatMessageTypeList.aClass11_5.method133();
		if (local13 == 0) {
			return false;
		}
		@Pc(70) int local70;
		if (IdkType.aClass22_246 == null) {
			if (OggOtherStream.aBoolean513) {
				QuickchatMessageTypeList.aClass11_5.method131(0, 1, Class14.aClass4_Sub12_Sub1_3.buffer);
				local13--;
				OggOtherStream.aBoolean513 = false;
				Class4_Sub6_Sub12.anInt2826++;
			}
			Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
			if (Class14.aClass4_Sub12_Sub1_3.method1141()) {
				if (local13 == 0) {
					return false;
				}
				local13--;
				QuickchatMessageTypeList.aClass11_5.method131(1, 1, Class14.aClass4_Sub12_Sub1_3.buffer);
				Class4_Sub6_Sub12.anInt2826++;
			}
			OggOtherStream.aBoolean513 = true;
			@Pc(66) Class22[] local66 = GlBitBuffer.method2553();
			local70 = Class14.aClass4_Sub12_Sub1_3.method1140();
			if (local70 < 0 || local70 >= local66.length) {
				throw new IOException("invo:" + local70 + " ip:" + Class14.aClass4_Sub12_Sub1_3.currentPosition);
			}
			IdkType.aClass22_246 = local66[local70];
			PointEntity.anInt4075 = IdkType.aClass22_246.anInt468;
		}
		if (PointEntity.anInt4075 == -1) {
			if (local13 <= 0) {
				return false;
			}
			QuickchatMessageTypeList.aClass11_5.method131(0, 1, Class14.aClass4_Sub12_Sub1_3.buffer);
			local13--;
			Class4_Sub6_Sub12.anInt2826++;
			PointEntity.anInt4075 = Class14.aClass4_Sub12_Sub1_3.buffer[0] & 0xFF;
		}
		if (PointEntity.anInt4075 == -2) {
			if (local13 <= 1) {
				return false;
			}
			QuickchatMessageTypeList.aClass11_5.method131(0, 2, Class14.aClass4_Sub12_Sub1_3.buffer);
			Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
			local13 -= 2;
			PointEntity.anInt4075 = Class14.aClass4_Sub12_Sub1_3.method2536();
			Class4_Sub6_Sub12.anInt2826 += 2;
		}
		if (local13 < PointEntity.anInt4075) {
			return false;
		}
		Class14.aClass4_Sub12_Sub1_3.currentPosition = 0;
		QuickchatMessageTypeList.aClass11_5.method131(0, PointEntity.anInt4075, Class14.aClass4_Sub12_Sub1_3.buffer);
		Class190.aClass22_257 = Static380.aClass22_294;
		Class4_Sub6_Sub12.anInt2826 += PointEntity.anInt4075;
		Static410.anInt6735 = 0;
		Static380.aClass22_294 = Class25.aClass22_42;
		Class25.aClass22_42 = IdkType.aClass22_246;
		if (GlVolumeTexture.aClass22_53 == IdkType.aClass22_246) {
			Class4_Sub1_Sub9.method2572(Class259.aClass21_17);
			IdkType.aClass22_246 = null;
			return true;
		}
		@Pc(212) String local212;
		@Pc(206) boolean local206;
		@Pc(210) String local210;
		@Pc(224) int local224;
		@Pc(228) int local228;
		@Pc(251) String local251;
		@Pc(230) boolean local230;
		if (IdkType.aClass22_246 == Class93.aClass22_125) {
			local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
			local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
			local212 = local210;
			if (local206) {
				local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
			}
			local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
			local228 = Class14.aClass4_Sub12_Sub1_3.method2536();
			local230 = false;
			if (local224 <= 1 && ParticleList.method3549(local212)) {
				local230 = true;
			}
			if (!local230 && Texture3DShaderGL.anInt1373 == 0) {
				local251 = Class145_Sub1.aClass81_2.method2258(local228).method234(Class14.aClass4_Sub12_Sub1_3);
				if (local224 == 2) {
					LocalRequester.method5438(local228, "<img=1>" + local212, local251, null, 0, "<img=1>" + local210, 25);
				} else if (local224 == 1) {
					LocalRequester.method5438(local228, "<img=0>" + local212, local251, null, 0, "<img=0>" + local210, 25);
				} else {
					LocalRequester.method5438(local228, local212, local251, null, 0, local210, 25);
				}
			}
			IdkType.aClass22_246 = null;
			return true;
		}
		@Pc(327) int local327;
		@Pc(335) int local335;
		if (IdkType.aClass22_246 == OpenGl2DTexture.aClass22_254) {
			local327 = Class14.aClass4_Sub12_Sub1_3.method2491();
			local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
			local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
			if (GLBillowTextureData.method4415(local70)) {
				Class149.method3562(local327, local335);
			}
			IdkType.aClass22_246 = null;
			return true;
		} else if (IdkType.aClass22_246 == PlayerEntity.aClass22_191) {
			local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
			local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
			@Pc(366) Object[] local366 = new Object[local210.length() + 1];
			for (local224 = local210.length() - 1; local224 >= 0; local224--) {
				if (local210.charAt(local224) == 's') {
					local366[local224 + 1] = Class14.aClass4_Sub12_Sub1_3.method2537();
				} else {
					local366[local224 + 1] = Integer.valueOf(Class14.aClass4_Sub12_Sub1_3.method2529());
				}
			}
			local366[0] = Integer.valueOf(Class14.aClass4_Sub12_Sub1_3.method2529());
			if (GLBillowTextureData.method4415(local327)) {
				@Pc(421) HookRequest local421 = new HookRequest();
				local421.params = local366;
				Static271.executeHookInner(local421);
			}
			IdkType.aClass22_246 = null;
			return true;
		} else if (StringNode.aClass22_241 == IdkType.aClass22_246) {
			Class4_Sub1_Sub9.method2572(Class186.aClass21_13);
			IdkType.aClass22_246 = null;
			return true;
		} else if (Class4_Sub6_Sub16.aClass22_167 == IdkType.aClass22_246) {
			FileOnDisk.method3470();
			IdkType.aClass22_246 = null;
			return false;
		} else if (Js5MasterIndex.aClass22_282 == IdkType.aClass22_246) {
			local327 = Class14.aClass4_Sub12_Sub1_3.method2520();
			local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
			local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
			if (local335 == 65535) {
				local335 = -1;
			}
			Class4_Sub6_Sub38.method5456(local335, local70, local327);
			IdkType.aClass22_246 = null;
			return true;
		} else if (ParticleList.aClass22_203 == IdkType.aClass22_246) {
			local327 = Class14.aClass4_Sub12_Sub1_3.method2493();
			local70 = Class14.aClass4_Sub12_Sub1_3.method2515();
			if (local70 == 65535) {
				local70 = -1;
			}
			local335 = Class14.aClass4_Sub12_Sub1_3.method2490();
			Static411.method5274(local335, local327, local70);
			IdkType.aClass22_246 = null;
			return true;
		} else {
			@Pc(535) String local535;
			if (IdkType.aClass22_246 == Js5MasterIndex.aClass22_281) {
				local327 = Class14.aClass4_Sub12_Sub1_3.method2489();
				local70 = Class14.aClass4_Sub12_Sub1_3.method2523();
				local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
				if (local335 == 65535) {
					local335 = -1;
				}
				local535 = Class14.aClass4_Sub12_Sub1_3.method2537();
				if (local327 >= 1 && local327 <= 8) {
					if (local535.equalsIgnoreCase("null")) {
						local535 = null;
					}
					ObjTypeList.aStringArray29[local327 - 1] = local535;
					GlRectangleTexture.anIntArray333[local327 - 1] = local335;
					Class3_Sub4.aBooleanArray13[local327 - 1] = local70 == 0;
				}
				IdkType.aClass22_246 = null;
				return true;
			} else if (Class4_Sub43.aClass22_319 == IdkType.aClass22_246) {
				local327 = Class14.aClass4_Sub12_Sub1_3.method2490();
				local70 = local327 >> 5;
				local335 = local327 & 0x1F;
				if (local335 == 0) {
					Class183.aClass38Array1[local70] = null;
					IdkType.aClass22_246 = null;
					return true;
				}
				@Pc(610) Class38 local610 = new Class38();
				local610.anInt891 = local335;
				local610.anInt886 = Class14.aClass4_Sub12_Sub1_3.method2490();
				if (local610.anInt886 >= 0 && local610.anInt886 < TextureOpTiledSprite.aClass57Array3.length) {
					if (local610.anInt891 == 1 || local610.anInt891 == 10) {
						local610.anInt885 = Class14.aClass4_Sub12_Sub1_3.method2536();
						Class14.aClass4_Sub12_Sub1_3.currentPosition += 6;
					} else if (local610.anInt891 >= 2 && local610.anInt891 <= 6) {
						if (local610.anInt891 == 2) {
							local610.anInt894 = 64;
							local610.anInt892 = 64;
						}
						if (local610.anInt891 == 3) {
							local610.anInt894 = 64;
							local610.anInt892 = 0;
						}
						if (local610.anInt891 == 4) {
							local610.anInt894 = 64;
							local610.anInt892 = 128;
						}
						if (local610.anInt891 == 5) {
							local610.anInt894 = 0;
							local610.anInt892 = 64;
						}
						if (local610.anInt891 == 6) {
							local610.anInt894 = 128;
							local610.anInt892 = 64;
						}
						local610.anInt891 = 2;
						local610.anInt887 = Class14.aClass4_Sub12_Sub1_3.method2490();
						local610.anInt892 += Class14.aClass4_Sub12_Sub1_3.method2536() - Class50_Sub1.anInt3453 << 7;
						local610.anInt894 += Class14.aClass4_Sub12_Sub1_3.method2536() - OpenGLTextureCache.anInt1771 << 7;
						local610.anInt895 = Class14.aClass4_Sub12_Sub1_3.method2490();
						local610.anInt889 = Class14.aClass4_Sub12_Sub1_3.method2536();
					}
					local610.anInt888 = Class14.aClass4_Sub12_Sub1_3.method2536();
					if (local610.anInt888 == 65535) {
						local610.anInt888 = -1;
					}
					Class183.aClass38Array1[local70] = local610;
				}
				IdkType.aClass22_246 = null;
				return true;
			} else if (IdkType.aClass22_246 == Class53.aClass22_73) {
				local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
				local70 = Class14.aClass4_Sub12_Sub1_3.method2538();
				local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
				if (GLBillowTextureData.method4415(local327)) {
					MusicPatchNode.method3451(local335, local70);
				}
				IdkType.aClass22_246 = null;
				return true;
			} else {
				@Pc(813) String local813;
				if (IdkType.aClass22_246 == ObjStackEntity.aClass22_45) {
					local813 = Class14.aClass4_Sub12_Sub1_3.method2537();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
					local212 = Class145_Sub1.aClass81_2.method2258(local70).method234(Class14.aClass4_Sub12_Sub1_3);
					LocalRequester.method5438(local70, local813, local212, null, 0, local813, 19);
					IdkType.aClass22_246 = null;
					return true;
				} else if (Class27_Sub4.aClass22_232 == IdkType.aClass22_246) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2548();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2538();
					local335 = Class14.aClass4_Sub12_Sub1_3.method2511();
					if (GLBillowTextureData.method4415(local327)) {
						Class190.method4221(local70, local335);
					}
					IdkType.aClass22_246 = null;
					return true;
				} else if (IdkType.aClass22_246 == Country.aClass22_336) {
					Static14.anInt189 = Class14.aClass4_Sub12_Sub1_3.method2520();
					SpriteGL.anInt6765 = Class14.aClass4_Sub12_Sub1_3.method2523();
					IdkType.aClass22_246 = null;
					return true;
				} else if (IdkType.aClass22_246 == Class193.aClass22_261) {
					if (EnumType.anInt5766 != -1) {
						Class4_Sub37.method4165(EnumType.anInt5766, 0);
					}
					IdkType.aClass22_246 = null;
					return true;
				} else if (Static331.aClass22_268 == IdkType.aClass22_246) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2515();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2511();
					Class4_Sub6_Sub24.aClass114_1.method2828(local70, local327);
					IdkType.aClass22_246 = null;
					return true;
				} else if (Class4_Sub10.aClass22_98 == IdkType.aClass22_246) {
					BasType.method3639();
					IdkType.aClass22_246 = null;
					return false;
				} else if (MECType.aClass22_218 == IdkType.aClass22_246) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2490();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2523();
					if (local327 == 255) {
						local70 = -1;
						local327 = -1;
					}
					NPCType.method5573(local327, local70);
					IdkType.aClass22_246 = null;
					return true;
				} else if (IdkType.aClass22_246 == Model.aClass22_286) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2511();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2520();
					local335 = Class14.aClass4_Sub12_Sub1_3.method2489();
					Class103.anIntArray207[local70] = local327;
					Static395.anIntArray502[local70] = local335;
					ParticleProducerType.anIntArray258[local70] = 1;
					local224 = Class4_Sub19.anIntArray167[local70] - 1;
					for (local228 = 0; local228 < local224; local228++) {
						if (local327 >= Class126.anIntArray260[local228]) {
							ParticleProducerType.anIntArray258[local70] = local228 + 2;
						}
					}
					TextureOpCurve.anIntArray40[Class204.anInt5948++ & 0x1F] = local70;
					IdkType.aClass22_246 = null;
					return true;
				} else if (IdkType.aClass22_246 == TextureOpKaleidoscope.aClass22_120) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2510();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
					if (GLBillowTextureData.method4415(local70)) {
						GlParticleRenderer.method914(3, local327, -1, -1);
					}
					IdkType.aClass22_246 = null;
					return true;
				} else if (IdkType.aClass22_246 == ColorImageCache.aClass22_209) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2490();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2538();
					local335 = Class14.aClass4_Sub12_Sub1_3.method2538();
					if (GLBillowTextureData.method4415(local70)) {
						if (local327 == 2) {
							TextureOpSquareWaveform.method5520();
						}
						EnumType.anInt5766 = local335;
						LocalRequester.method5439(local335);
						SpotAnimationTypeList.method4422(false);
						Static271.runInterfaceOnLoadScripts(EnumType.anInt5766);
						for (local224 = 0; local224 < 100; local224++) {
							PingRequest.aBooleanArray21[local224] = true;
						}
					}
					IdkType.aClass22_246 = null;
					return true;
				} else if (Class16_Sub4_Sub2.aClass22_106 == IdkType.aClass22_246) {
					local327 = Class14.aClass4_Sub12_Sub1_3.method2491();
					local70 = Class14.aClass4_Sub12_Sub1_3.method2529();
					local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
					local224 = Class14.aClass4_Sub12_Sub1_3.method2548();
					if (GLBillowTextureData.method4415(local335)) {
						GlParticleRenderer.method914(5, local327, local224, local70);
					}
					IdkType.aClass22_246 = null;
					return true;
				} else {
					@Pc(1190) int local1190;
					if (GlShadowMap.ALIGN_CENTER == IdkType.aClass22_246) {
						local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
						local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
						local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
						local224 = Class14.aClass4_Sub12_Sub1_3.method2536();
						if (GLBillowTextureData.method4415(local327) && Class4_Sub36.aClass247ArrayArray2[local70] != null) {
							for (local228 = local335; local228 < local224; local228++) {
								local1190 = Class14.aClass4_Sub12_Sub1_3.method2493();
								if (local228 < Class4_Sub36.aClass247ArrayArray2[local70].length && Class4_Sub36.aClass247ArrayArray2[local70][local228] != null) {
									Class4_Sub36.aClass247ArrayArray2[local70][local228].serverTriggers = local1190;
								}
							}
						}
						IdkType.aClass22_246 = null;
						return true;
					}
					@Pc(1252) long local1252;
					@Pc(1257) long local1257;
					@Pc(1267) int local1267;
					@Pc(1263) int local1263;
					@Pc(1277) int local1277;
					if (IdkType.aClass22_246 == KeyRecordEntry.aClass22_32) {
						local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
						local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
						local212 = local210;
						if (local206) {
							local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
						}
						local1252 = Class14.aClass4_Sub12_Sub1_3.method2536();
						local1257 = Class14.aClass4_Sub12_Sub1_3.method2493();
						local1263 = Class14.aClass4_Sub12_Sub1_3.method2490();
						local1267 = Class14.aClass4_Sub12_Sub1_3.method2536();
						@Pc(1273) long local1273 = local1257 + (local1252 << 32);
						@Pc(1275) boolean local1275 = false;
						local1277 = 0;
						while (true) {
							if (local1277 >= 100) {
								if (local1263 <= 1 && ParticleList.method3549(local212)) {
									local1275 = true;
								}
								break;
							}
							if (QuickChatCategoryType.aLongArray7[local1277] == local1273) {
								local1275 = true;
								break;
							}
							local1277++;
						}
						if (!local1275 && Texture3DShaderGL.anInt1373 == 0) {
							QuickChatCategoryType.aLongArray7[Skybox.anInt6719] = local1273;
							Skybox.anInt6719 = (Skybox.anInt6719 + 1) % 100;
							@Pc(1324) String local1324 = Class145_Sub1.aClass81_2.method2258(local1267).method234(Class14.aClass4_Sub12_Sub1_3);
							if (local1263 == 2) {
								LocalRequester.method5438(local1267, "<img=1>" + local212, local1324, null, 0, "<img=1>" + local210, 18);
							} else if (local1263 == 1) {
								LocalRequester.method5438(local1267, "<img=0>" + local212, local1324, null, 0, "<img=0>" + local210, 18);
							} else {
								LocalRequester.method5438(local1267, local212, local1324, null, 0, local210, 18);
							}
						}
						IdkType.aClass22_246 = null;
						return true;
					}
					@Pc(1411) boolean local1411;
					if (IdkType.aClass22_246 == Class75.aClass22_102) {
						local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
						local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
						local1411 = (local70 & 0x1) == 1;
						while (PointEntity.anInt4075 > Class14.aClass4_Sub12_Sub1_3.currentPosition) {
							local224 = Class14.aClass4_Sub12_Sub1_3.method2506();
							local228 = Class14.aClass4_Sub12_Sub1_3.method2536();
							local1190 = 0;
							if (local228 != 0) {
								local1190 = Class14.aClass4_Sub12_Sub1_3.method2490();
								if (local1190 == 255) {
									local1190 = Class14.aClass4_Sub12_Sub1_3.method2529();
								}
							}
							Class4_Sub1_Sub11.method2603(local224, local228 - 1, local327, local1190, local1411);
						}
						DynamicWall.anIntArray500[Js5TextureSource.anInt2841++ & 0x1F] = local327;
						IdkType.aClass22_246 = null;
						return true;
					} else if (Huffman.aClass22_159 == IdkType.aClass22_246) {
						if (BillowNoiseGenerator.aFrame3 != null) {
							Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
						}
						@Pc(1483) byte[] local1483 = new byte[PointEntity.anInt4075];
						Class14.aClass4_Sub12_Sub1_3.method1132(PointEntity.anInt4075, local1483);
						local210 = SpriteGL.method5293(local1483, PointEntity.anInt4075, 0);
						PathingEntitySequence.method3929(Class126.aClass66_4, local210, Packet.anInt2973 == 1, true);
						IdkType.aClass22_246 = null;
						return true;
					} else if (IdkType.aClass22_246 == Class196.aClass22_264) {
						Class4_Sub1_Sub9.method2572(Static133.aClass21_8);
						IdkType.aClass22_246 = null;
						return true;
					} else if (IdkType.aClass22_246 == Game.aClass22_160) {
						local327 = Class14.aClass4_Sub12_Sub1_3.method2491();
						local70 = Class14.aClass4_Sub12_Sub1_3.method2538();
						Class4_Sub6_Sub24.aClass114_1.updateVarp(local70, local327);
						IdkType.aClass22_246 = null;
						return true;
					} else {
						@Pc(1576) Class4_Sub43 local1576;
						if (EffectiveVertex.aClass22_247 == IdkType.aClass22_246) {
							local327 = Class14.aClass4_Sub12_Sub1_3.method2489();
							local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
							local335 = Class14.aClass4_Sub12_Sub1_3.method2529();
							local224 = Class14.aClass4_Sub12_Sub1_3.method2538();
							if (GLBillowTextureData.method4415(local70)) {
								local1576 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local335);
								if (local1576 != null) {
									Class62.method1606(local224 != local1576.anInt6979, local1576, false);
								}
								IdkType.method4092(local224, local327, false, local335);
							}
							IdkType.aClass22_246 = null;
							return true;
						} else if (GzipDecompressor.aClass22_252 == IdkType.aClass22_246) {
							Class4_Sub1_Sub9.method2572(Class14.aClass21_2);
							IdkType.aClass22_246 = null;
							return true;
						} else if (RequestManager.MAP_BUILD_COMPLETE == IdkType.aClass22_246) {
							local327 = Class14.aClass4_Sub12_Sub1_3.method2548();
							local70 = Class14.aClass4_Sub12_Sub1_3.method2509();
							local335 = Class14.aClass4_Sub12_Sub1_3.method2510();
							if (GLBillowTextureData.method4415(local327)) {
								Class4_Sub18.method2033(local70, local335);
							}
							IdkType.aClass22_246 = null;
							return true;
						} else if (QuickChatPhrase.OPNPC3 == IdkType.aClass22_246) {
							local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
							local70 = Class14.aClass4_Sub12_Sub1_3.method2489();
							local1411 = (local70 & 0x1) == 1;
							Class4_Sub1_Sub9_Sub2.method2573(local327, local1411);
							DynamicWall.anIntArray500[Js5TextureSource.anInt2841++ & 0x1F] = local327;
							IdkType.aClass22_246 = null;
							return true;
						} else {
							@Pc(1747) String local1747;
							if (GpuFaceNormal.aClass22_312 == IdkType.aClass22_246) {
								local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
								local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
								local212 = local210;
								if (local206) {
									local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
								}
								local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
								@Pc(1713) boolean local1713 = false;
								if (local224 <= 1) {
									if (Class16.aBoolean628 && !MemoryPool.aBoolean443 || Static308.aBoolean486) {
										local1713 = true;
									} else if (local224 <= 1 && ParticleList.method3549(local212)) {
										local1713 = true;
									}
								}
								if (!local1713 && Texture3DShaderGL.anInt1373 == 0) {
									local1747 = Class16_Sub1_Sub1.method297(MECType.method3776(Class14.aClass4_Sub12_Sub1_3));
									if (local224 == 2) {
										LocalRequester.method5438(-1, "<img=1>" + local212, local1747, null, 0, "<img=1>" + local210, 24);
									} else if (local224 == 1) {
										LocalRequester.method5438(-1, "<img=0>" + local212, local1747, null, 0, "<img=0>" + local210, 24);
									} else {
										LocalRequester.method5438(-1, local212, local1747, null, 0, local210, 24);
									}
								}
								IdkType.aClass22_246 = null;
								return true;
							} else if (IdkType.aClass22_246 == Class16_Sub2_Sub3.aClass22_205) {
								Class4_Sub1_Sub9.method2572(Js5ResourceRequest.aClass21_12);
								IdkType.aClass22_246 = null;
								return true;
							} else {
								@Pc(2080) String local2080;
								@Pc(1858) boolean local1858;
								@Pc(2152) boolean local2152;
								if (CacheableNodeQueue.aClass22_195 == IdkType.aClass22_246) {
									while (Class14.aClass4_Sub12_Sub1_3.currentPosition < PointEntity.anInt4075) {
										local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
										local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
										local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
										local224 = Class14.aClass4_Sub12_Sub1_3.method2536();
										local228 = Class14.aClass4_Sub12_Sub1_3.method2490();
										local1747 = "";
										local1858 = false;
										if (local224 > 0) {
											local1747 = Class14.aClass4_Sub12_Sub1_3.method2537();
											local1858 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
										}
										for (local1263 = 0; local1263 < Class4_Sub10.anInt2346; local1263++) {
											if (local206) {
												if (local212.equals(Mesh.aStringArray14[local1263])) {
													Mesh.aStringArray14[local1263] = local210;
													local210 = null;
													Static57.aStringArray6[local1263] = local212;
													break;
												}
											} else if (local210.equals(Mesh.aStringArray14[local1263])) {
												if (Class9.anIntArray536[local1263] != local224) {
													Class9.anIntArray536[local1263] = local224;
													if (local224 > 0) {
														Class83.method2268(0, 5, local210 + Class4_Sub6_Sub12.aClass83_54.method2267(NativeBillboardFace.anInt3423), "", "");
													}
													if (local224 == 0) {
														Class83.method2268(0, 5, local210 + FluType.aClass83_125.method2267(NativeBillboardFace.anInt3423), "", "");
													}
												}
												Static57.aStringArray6[local1263] = local212;
												Static433.aStringArray38[local1263] = local1747;
												Class4_Sub6_Sub26.anIntArray339[local1263] = local228;
												local210 = null;
												Class4_Sub43.aBooleanArray23[local1263] = local1858;
												break;
											}
										}
										if (local210 != null && Class4_Sub10.anInt2346 < 200) {
											Mesh.aStringArray14[Class4_Sub10.anInt2346] = local210;
											Static57.aStringArray6[Class4_Sub10.anInt2346] = local212;
											Class9.anIntArray536[Class4_Sub10.anInt2346] = local224;
											Static433.aStringArray38[Class4_Sub10.anInt2346] = local1747;
											Class4_Sub6_Sub26.anIntArray339[Class4_Sub10.anInt2346] = local228;
											Class4_Sub43.aBooleanArray23[Class4_Sub10.anInt2346] = local1858;
											Class4_Sub10.anInt2346++;
										}
									}
									Model.anInt6277 = 2;
									Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
									local70 = Class4_Sub10.anInt2346;
									while (local70 > 0) {
										local70--;
										local206 = true;
										for (local335 = 0; local335 < local70; local335++) {
											if (Class9.anIntArray536[local335] != MapSpriteType.anInt1028 && MapSpriteType.anInt1028 == Class9.anIntArray536[local335 + 1] || Class9.anIntArray536[local335] == 0 && Class9.anIntArray536[local335 + 1] != 0) {
												local224 = Class9.anIntArray536[local335];
												Class9.anIntArray536[local335] = Class9.anIntArray536[local335 + 1];
												Class9.anIntArray536[local335 + 1] = local224;
												local2080 = Static433.aStringArray38[local335];
												Static433.aStringArray38[local335] = Static433.aStringArray38[local335 + 1];
												Static433.aStringArray38[local335 + 1] = local2080;
												local1747 = Mesh.aStringArray14[local335];
												Mesh.aStringArray14[local335] = Mesh.aStringArray14[local335 + 1];
												Mesh.aStringArray14[local335 + 1] = local1747;
												local251 = Static57.aStringArray6[local335];
												Static57.aStringArray6[local335] = Static57.aStringArray6[local335 + 1];
												Static57.aStringArray6[local335 + 1] = local251;
												local1263 = Class4_Sub6_Sub26.anIntArray339[local335];
												Class4_Sub6_Sub26.anIntArray339[local335] = Class4_Sub6_Sub26.anIntArray339[local335 + 1];
												Class4_Sub6_Sub26.anIntArray339[local335 + 1] = local1263;
												local2152 = Class4_Sub43.aBooleanArray23[local335];
												Class4_Sub43.aBooleanArray23[local335] = Class4_Sub43.aBooleanArray23[local335 + 1];
												Class4_Sub43.aBooleanArray23[local335 + 1] = local2152;
												local206 = false;
											}
										}
										if (local206) {
											break;
										}
									}
									IdkType.aClass22_246 = null;
									return true;
								} else if (IdkType.aClass22_246 == TextureOpMonochromeEdgeDetector.aClass22_317) {
									local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
									if (local327 == 65535) {
										local327 = -1;
									}
									local70 = Class14.aClass4_Sub12_Sub1_3.method2529();
									local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
									if (GLBillowTextureData.method4415(local335)) {
										SeqTypeList.method1399(local327, local70);
									}
									IdkType.aClass22_246 = null;
									return true;
								} else if (NativeWaterTexture.aClass22_200 == IdkType.aClass22_246) {
									Game.method2942(Class14.aClass4_Sub12_Sub1_3, PointEntity.anInt4075);
									IdkType.aClass22_246 = null;
									return true;
								} else if (IdkType.aClass22_246 == Class10.aClass22_17) {
									local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
									local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
									Class4_Sub6_Sub24.aClass114_1.method2828(local70, local327);
									IdkType.aClass22_246 = null;
									return true;
								} else {
									@Pc(2311) long local2311;
									@Pc(2329) GlRectangleTexture local2329;
									@Pc(2317) GlRectangleTexture local2317;
									if (MusicPatchInfo.aClass22_311 == IdkType.aClass22_246) {
										local327 = Class14.aClass4_Sub12_Sub1_3.method2515();
										if (local327 == 65535) {
											local327 = -1;
										}
										local70 = Class14.aClass4_Sub12_Sub1_3.method2510();
										local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
										local224 = Class14.aClass4_Sub12_Sub1_3.method2529();
										local228 = Class14.aClass4_Sub12_Sub1_3.method2538();
										if (local228 == 65535) {
											local228 = -1;
										}
										if (GLBillowTextureData.method4415(local335)) {
											for (local1190 = local327; local1190 <= local228; local1190++) {
												local2311 = ((long) local224 << 32) + (long) local1190;
												local2317 = (GlRectangleTexture) Static211.aClass102_18.method2700(local2311);
												if (local2317 != null) {
													local2329 = new GlRectangleTexture(local70, local2317.anInt5110);
													local2317.method5684();
												} else if (local1190 == -1) {
													local2329 = new GlRectangleTexture(local70, ParticleBinaryTree.method5121(local224).aClass4_Sub33_2.anInt5110);
												} else {
													local2329 = new GlRectangleTexture(local70, -1);
												}
												Static211.aClass102_18.method2703(local2311, local2329);
											}
										}
										IdkType.aClass22_246 = null;
										return true;
									} else if (Static107.aClass22_96 == IdkType.aClass22_246) {
										ByteArrayWrapper.cameraStepV = Class14.aClass4_Sub12_Sub1_3.method2534() << 3;
										Class70_Sub2.anInt6487 = Class14.aClass4_Sub12_Sub1_3.method2542() << 3;
										CursorType.anInt2426 = Class14.aClass4_Sub12_Sub1_3.method2523();
										IdkType.aClass22_246 = null;
										return true;
									} else if (Class4_Sub41.aClass22_306 == IdkType.aClass22_246) {
										Static17.anInt223 = Class14.aClass4_Sub12_Sub1_3.method2512();
										Static308.anInt5413 = MusicPatch.anInt5640;
										IdkType.aClass22_246 = null;
										return true;
									} else if (IdkType.aClass22_246 == NativeBillboardFace.aClass22_152) {
										local327 = Class14.aClass4_Sub12_Sub1_3.method2489();
										local70 = Class14.aClass4_Sub12_Sub1_3.method2515();
										if (GLBillowTextureData.method4415(local70)) {
											SpotAnimationType.anInt4991 = local327;
										}
										IdkType.aClass22_246 = null;
										return true;
									} else {
										@Pc(2473) int local2473;
										@Pc(2494) int local2494;
										@Pc(2648) int local2648;
										@Pc(3138) int local3138;
										if (TextureOpTile.aClass22_290 == IdkType.aClass22_246) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
											local335 = Class14.aClass4_Sub12_Sub1_3.method2510();
											local224 = Class14.aClass4_Sub12_Sub1_3.method2548();
											local228 = Class14.aClass4_Sub12_Sub1_3.method2520();
											local230 = (local228 & 0x80) != 0;
											if (local335 >> 30 == 0) {
												@Pc(2515) SpotAnimationType local2515;
												@Pc(2573) SeqType local2573;
												@Pc(2520) SpotAnimationType local2520;
												@Pc(2535) SeqType local2535;
												@Pc(2541) SeqType local2541;
												if (local335 >> 29 != 0) {
													local2473 = local335 & 0xFFFF;
													@Pc(2477) NPCEntity local2477 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local2473];
													if (local2477 != null) {
														if (local70 == 65535) {
															local70 = -1;
														}
														local2152 = true;
														local2494 = local230 ? local2477.anInt6045 : local2477.anInt6029;
														if (local70 != -1 && local2494 != -1) {
															if (local70 == local2494) {
																local2515 = Js5MasterIndex.aClass194_2.method4421(local70);
																if (local2515.replay && local2515.sequenceId != -1) {
																	local2573 = SceneLocEntity.aClass55_1.method1397(local2515.sequenceId);
																	local1277 = local2573.loopType;
																	if (local1277 == 0 || local1277 == 2) {
																		local2152 = false;
																	} else if (local1277 == 1) {
																		local2152 = true;
																	}
																}
															} else {
																local2515 = Js5MasterIndex.aClass194_2.method4421(local70);
																local2520 = Js5MasterIndex.aClass194_2.method4421(local2494);
																if (local2515.sequenceId != -1 && local2520.sequenceId != -1) {
																	local2535 = SceneLocEntity.aClass55_1.method1397(local2515.sequenceId);
																	local2541 = SceneLocEntity.aClass55_1.method1397(local2520.sequenceId);
																	if (local2541.priority > local2535.priority) {
																		local2152 = false;
																	}
																}
															}
														}
														if (local2152) {
															if (local230) {
																local2477.anInt6022 = 0;
																local2477.anInt6066 = local327 + Shadow.anInt5323;
																local2477.anInt6053 = local224;
																local2477.anInt6069 = 1;
																local2477.anInt6062 = 0;
																local2477.anInt6056 = local228 & 0x7;
																local2477.anInt6045 = local70;
																if (local2477.anInt6066 > Shadow.anInt5323) {
																	local2477.anInt6022 = -1;
																}
																if (local2477.anInt6045 != -1 && Shadow.anInt5323 == local2477.anInt6066) {
																	local2648 = Js5MasterIndex.aClass194_2.method4421(local2477.anInt6045).sequenceId;
																	if (local2648 != -1) {
																		local2573 = SceneLocEntity.aClass55_1.method1397(local2648);
																		if (local2573 != null && local2573.durations != null) {
																			Class4_Sub1_Sub2.method156(local2477.anInt6893, 0, local2477.aByte82, local2477.anInt6892, local2573, false);
																		}
																	}
																}
															} else {
																local2477.anInt6014 = local224;
																local2477.anInt6027 = 0;
																local2477.anInt6083 = 1;
																local2477.anInt6029 = local70;
																local2477.anInt6017 = local228 & 0x7;
																local2477.anInt6057 = local327 + Shadow.anInt5323;
																local2477.anInt6033 = 0;
																if (local2477.anInt6057 > Shadow.anInt5323) {
																	local2477.anInt6027 = -1;
																}
																if (local2477.anInt6029 != -1 && Shadow.anInt5323 == local2477.anInt6057) {
																	local2648 = Js5MasterIndex.aClass194_2.method4421(local2477.anInt6029).sequenceId;
																	if (local2648 != -1) {
																		local2573 = SceneLocEntity.aClass55_1.method1397(local2648);
																		if (local2573 != null && local2573.durations != null) {
																			Class4_Sub1_Sub2.method156(local2477.anInt6893, 0, local2477.aByte82, local2477.anInt6892, local2573, false);
																		}
																	}
																}
															}
														}
													}
												} else if (local335 >> 28 != 0) {
													local2473 = local335 & 0xFFFF;
													@Pc(2773) PlayerEntity local2773;
													if (Class6.anInt5452 == local2473) {
														local2773 = Static1.aClass16_Sub1_Sub5_Sub1_1;
													} else {
														local2773 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local2473];
													}
													if (local2773 != null) {
														if (local70 == 65535) {
															local70 = -1;
														}
														local2152 = true;
														local2494 = local230 ? local2773.anInt6045 : local2773.anInt6029;
														if (local70 != -1 && local2494 != -1) {
															if (local70 == local2494) {
																local2515 = Js5MasterIndex.aClass194_2.method4421(local70);
																if (local2515.replay && local2515.sequenceId != -1) {
																	local2573 = SceneLocEntity.aClass55_1.method1397(local2515.sequenceId);
																	local1277 = local2573.loopType;
																	if (local1277 == 0 || local1277 == 2) {
																		local2152 = false;
																	} else if (local1277 == 1) {
																		local2152 = true;
																	}
																}
															} else {
																local2515 = Js5MasterIndex.aClass194_2.method4421(local70);
																local2520 = Js5MasterIndex.aClass194_2.method4421(local2494);
																if (local2515.sequenceId != -1 && local2520.sequenceId != -1) {
																	local2535 = SceneLocEntity.aClass55_1.method1397(local2515.sequenceId);
																	local2541 = SceneLocEntity.aClass55_1.method1397(local2520.sequenceId);
																	if (local2541.priority > local2535.priority) {
																		local2152 = false;
																	}
																}
															}
														}
														if (local2152) {
															if (local230) {
																local2773.anInt6056 = local228 & 0x7;
																local2773.anInt6045 = local70;
																local2773.anInt6053 = local224;
																local2773.anInt6069 = 1;
																local2773.anInt6066 = local327 + Shadow.anInt5323;
																local2773.anInt6022 = 0;
																local2773.anInt6062 = 0;
																if (local2773.anInt6045 == 65535) {
																	local2773.anInt6045 = -1;
																}
																if (local2773.anInt6066 > Shadow.anInt5323) {
																	local2773.anInt6022 = -1;
																}
																if (local2773.anInt6045 != -1 && local2773.anInt6066 == Shadow.anInt5323) {
																	local2648 = Js5MasterIndex.aClass194_2.method4421(local2773.anInt6045).sequenceId;
																	if (local2648 != -1) {
																		local2573 = SceneLocEntity.aClass55_1.method1397(local2648);
																		if (local2573 != null && local2573.durations != null) {
																			Class4_Sub1_Sub2.method156(local2773.anInt6893, 0, local2773.aByte82, local2773.anInt6892, local2573, local2773 == Static1.aClass16_Sub1_Sub5_Sub1_1);
																		}
																	}
																}
															} else {
																local2773.anInt6017 = local228 & 0x7;
																local2773.anInt6033 = 0;
																local2773.anInt6014 = local224;
																local2773.anInt6083 = 1;
																local2773.anInt6029 = local70;
																local2773.anInt6057 = Shadow.anInt5323 + local327;
																local2773.anInt6027 = 0;
																if (local2773.anInt6029 == 65535) {
																	local2773.anInt6029 = -1;
																}
																if (local2773.anInt6057 > Shadow.anInt5323) {
																	local2773.anInt6027 = -1;
																}
																if (local2773.anInt6029 != -1 && local2773.anInt6057 == Shadow.anInt5323) {
																	local2648 = Js5MasterIndex.aClass194_2.method4421(local2773.anInt6029).sequenceId;
																	if (local2648 != -1) {
																		local2573 = SceneLocEntity.aClass55_1.method1397(local2648);
																		if (local2573 != null && local2573.durations != null) {
																			Class4_Sub1_Sub2.method156(local2773.anInt6893, 0, local2773.aByte82, local2773.anInt6892, local2573, Static1.aClass16_Sub1_Sub5_Sub1_1 == local2773);
																		}
																	}
																}
															}
														}
													}
												}
											} else {
												local2473 = local335 >> 28 & 0x3;
												local1263 = (local335 >> 14 & 0x3FFF) - Class50_Sub1.anInt3453;
												local1267 = (local335 & 0x3FFF) - OpenGLTextureCache.anInt1771;
												if (local1263 >= 0 && local1267 >= 0 && GLBillowTextureData.anInt5666 > local1263 && Static283.anInt5187 > local1267) {
													local2494 = local1263 * 128 + 64;
													local2648 = local1267 * 128 + 64;
													local3138 = local2473;
													if (local2473 < 3 && EnvironmentSampler.method3229(local1263, local1267)) {
														local3138 = local2473 + 1;
													}
													@Pc(3173) SpotAnimation local3173 = new SpotAnimation(local70, 0, Shadow.anInt5323, local2473, local3138, local2494, BufferedSocket.method135(local2494, local2473, local2648) - local224, local2648, local1263, local1263, local1267, local1267, local228);
													HookRequest.aClass183_36.method4137(new PositionedGfx(local3173));
												}
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == Class95_Sub3.aClass22_292) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
											local1411 = (local70 & 0x1) == 1;
											GlModel.method2796(local327, local1411);
											local224 = Class14.aClass4_Sub12_Sub1_3.method2536();
											for (local228 = 0; local228 < local224; local228++) {
												local1190 = Class14.aClass4_Sub12_Sub1_3.method2489();
												if (local1190 == 255) {
													local1190 = Class14.aClass4_Sub12_Sub1_3.method2511();
												}
												local2473 = Class14.aClass4_Sub12_Sub1_3.method2536();
												Class4_Sub1_Sub11.method2603(local228, local2473 - 1, local327, local1190, local1411);
											}
											DynamicWall.anIntArray500[Js5TextureSource.anInt2841++ & 0x1F] = local327;
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == Class244.aClass22_325) {
											local813 = Class14.aClass4_Sub12_Sub1_3.method2537();
											local210 = Class16_Sub1_Sub1.method297(MECType.method3776(Class14.aClass4_Sub12_Sub1_3));
											Class83.method2268(0, 6, local210, local813, local813);
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == EffectiveVertex.aClass22_248) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
											if (local70 == 65535) {
												local70 = -1;
											}
											local335 = Class14.aClass4_Sub12_Sub1_3.method2529();
											local224 = Class14.aClass4_Sub12_Sub1_3.method2491();
											if (GLBillowTextureData.method4415(local327)) {
												Class16_Sub4_Sub2.method2150(local224, local335, local70);
												@Pc(3326) ItemType local3326 = Terrain.aClass206_3.method4703(local70);
												Class64_Sub1.method1652(local224, local3326.anInt6122, local3326.anInt6133, local3326.anInt6137);
												Class4_Sub6_Sub4.method1274(local3326.anInt6152, local3326.lb, local224, local3326.anInt6135);
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == TextureOpFlip.aClass22_157) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2515();
											local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
											local224 = Class14.aClass4_Sub12_Sub1_3.method2548();
											local228 = Class14.aClass4_Sub12_Sub1_3.method2491();
											if (GLBillowTextureData.method4415(local224)) {
												GlParticleRenderer.method914(7, local228, local70 | local335 << 16, local327);
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == Class7.aClass22_11) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2548();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
											local335 = Class14.aClass4_Sub12_Sub1_3.method2529();
											local224 = Class14.aClass4_Sub12_Sub1_3.method2538();
											local228 = Class14.aClass4_Sub12_Sub1_3.method2536();
											if (GLBillowTextureData.method4415(local327)) {
												Class64_Sub1.method1652(local335, local228, local224, local70);
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == Static105.aClass22_94) {
											MouseManager.method2241(true);
											IdkType.aClass22_246 = null;
											return true;
										} else if (Shadow.categoryIterator == IdkType.aClass22_246) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
											@Pc(3451) byte local3451 = Class14.aClass4_Sub12_Sub1_3.method2507();
											Class4_Sub6_Sub24.aClass114_1.updateVarp(local327, local3451);
											IdkType.aClass22_246 = null;
											return true;
										} else if (Class14.aClass22_23 == IdkType.aClass22_246) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2490();
											if (Class14.aClass4_Sub12_Sub1_3.method2490() == 0) {
												Class243.aClass28Array1[local327] = new Class28();
											} else {
												Class14.aClass4_Sub12_Sub1_3.currentPosition--;
												Class243.aClass28Array1[local327] = new Class28(Class14.aClass4_Sub12_Sub1_3);
											}
											Class4_Sub29.anInt4589 = MusicPatch.anInt5640;
											IdkType.aClass22_246 = null;
											return true;
										} else if (IdkType.aClass22_246 == Class111.aClass22_150) {
											local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
											local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
											local212 = local210;
											if (local206) {
												local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
											}
											local1252 = Class14.aClass4_Sub12_Sub1_3.method2536();
											local1257 = Class14.aClass4_Sub12_Sub1_3.method2493();
											local1263 = Class14.aClass4_Sub12_Sub1_3.method2490();
											@Pc(3544) long local3544 = (local1252 << 32) + local1257;
											@Pc(3546) boolean local3546 = false;
											local3138 = 0;
											while (true) {
												if (local3138 >= 100) {
													if (local1263 <= 1) {
														if (Class16.aBoolean628 && !MemoryPool.aBoolean443 || Static308.aBoolean486) {
															local3546 = true;
														} else if (ParticleList.method3549(local212)) {
															local3546 = true;
														}
													}
													break;
												}
												if (local3544 == QuickChatCategoryType.aLongArray7[local3138]) {
													local3546 = true;
													break;
												}
												local3138++;
											}
											if (!local3546 && Texture3DShaderGL.anInt1373 == 0) {
												QuickChatCategoryType.aLongArray7[Skybox.anInt6719] = local3544;
												Skybox.anInt6719 = (Skybox.anInt6719 + 1) % 100;
												@Pc(3605) String local3605 = Class16_Sub1_Sub1.method297(MECType.method3776(Class14.aClass4_Sub12_Sub1_3));
												if (local1263 == 2) {
													LocalRequester.method5438(-1, "<img=1>" + local212, local3605, null, 0, "<img=1>" + local210, 7);
												} else if (local1263 == 1) {
													LocalRequester.method5438(-1, "<img=0>" + local212, local3605, null, 0, "<img=0>" + local210, 7);
												} else {
													LocalRequester.method5438(-1, local212, local3605, null, 0, local210, 3);
												}
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (Class4_Sub6_Sub24.aClass22_212 == IdkType.aClass22_246) {
											local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
											local70 = Class14.aClass4_Sub12_Sub1_3.method2520();
											local335 = Class14.aClass4_Sub12_Sub1_3.method2538();
											@Pc(3691) NPCEntity local3691 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local327];
											if (local3691 != null) {
												Class4_Sub7.method632(local335, local70, local3691);
											}
											IdkType.aClass22_246 = null;
											return true;
										} else if (Isaac.aClass22_72 == IdkType.aClass22_246) {
											Component.anInt6823 = Class14.aClass4_Sub12_Sub1_3.method2490();
											for (local327 = 0; local327 < Component.anInt6823; local327++) {
												GLBillowTextureData.aStringArray27[local327] = Class14.aClass4_Sub12_Sub1_3.method2537();
												Static102.aStringArray7[local327] = Class14.aClass4_Sub12_Sub1_3.method2537();
												if (Static102.aStringArray7[local327].equals("")) {
													Static102.aStringArray7[local327] = GLBillowTextureData.aStringArray27[local327];
												}
												Class4_Sub1_Sub17.aStringArray26[local327] = Class14.aClass4_Sub12_Sub1_3.method2537();
												GpuPointLight.aStringArray11[local327] = Class14.aClass4_Sub12_Sub1_3.method2537();
												if (GpuPointLight.aStringArray11[local327].equals("")) {
													GpuPointLight.aStringArray11[local327] = Class4_Sub1_Sub17.aStringArray26[local327];
												}
												EffectManagerGL.aBooleanArray20[local327] = false;
											}
											IdkType.aClass22_246 = null;
											Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
											return true;
										} else {
											@Pc(3817) long local3817;
											if (Class259.aClass22_328 == IdkType.aClass22_246) {
												local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
												local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
												local212 = local210;
												if (local206) {
													local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
												}
												local1252 = Class14.aClass4_Sub12_Sub1_3.method2501();
												local1257 = Class14.aClass4_Sub12_Sub1_3.method2536();
												local3817 = Class14.aClass4_Sub12_Sub1_3.method2493();
												local2494 = Class14.aClass4_Sub12_Sub1_3.method2490();
												local2648 = Class14.aClass4_Sub12_Sub1_3.method2536();
												@Pc(3832) long local3832 = (local1257 << 32) + local3817;
												@Pc(3834) boolean local3834 = false;
												@Pc(3836) int local3836 = 0;
												while (true) {
													if (local3836 >= 100) {
														if (local2494 <= 1 && ParticleList.method3549(local212)) {
															local3834 = true;
														}
														break;
													}
													if (QuickChatCategoryType.aLongArray7[local3836] == local3832) {
														local3834 = true;
														break;
													}
													local3836++;
												}
												if (!local3834 && Texture3DShaderGL.anInt1373 == 0) {
													QuickChatCategoryType.aLongArray7[Skybox.anInt6719] = local3832;
													Skybox.anInt6719 = (Skybox.anInt6719 + 1) % 100;
													@Pc(3889) String local3889 = Class145_Sub1.aClass81_2.method2258(local2648).method234(Class14.aClass4_Sub12_Sub1_3);
													if (local2494 == 2) {
														LocalRequester.method5438(local2648, "<img=1>" + local212, local3889, Static45.method766(local1252), 0, "<img=1>" + local210, 20);
													} else if (local2494 == 1) {
														LocalRequester.method5438(local2648, "<img=0>" + local212, local3889, Static45.method766(local1252), 0, "<img=0>" + local210, 20);
													} else {
														LocalRequester.method5438(local2648, local212, local3889, Static45.method766(local1252), 0, local210, 20);
													}
												}
												IdkType.aClass22_246 = null;
												return true;
											} else if (NamedFileReference.aClass22_300 == IdkType.aClass22_246) {
												for (local327 = 0; local327 < Static267.aClass16_Sub1_Sub5_Sub1Array1.length; local327++) {
													if (Static267.aClass16_Sub1_Sub5_Sub1Array1[local327] != null) {
														Static267.aClass16_Sub1_Sub5_Sub1Array1[local327].anInt6021 = -1;
													}
												}
												for (local70 = 0; local70 < Class93.aClass16_Sub1_Sub5_Sub2Array1.length; local70++) {
													if (Class93.aClass16_Sub1_Sub5_Sub2Array1[local70] != null) {
														Class93.aClass16_Sub1_Sub5_Sub2Array1[local70].anInt6021 = -1;
													}
												}
												IdkType.aClass22_246 = null;
												return true;
											} else if (Class4_Sub37.aClass22_251 == IdkType.aClass22_246) {
												local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
												if (GLBillowTextureData.method4415(local327)) {
													GlParticleRenderer.method913();
												}
												IdkType.aClass22_246 = null;
												return true;
											} else {
												@Pc(4235) Class15 local4235;
												if (CacheableNode.aClass22_277 == IdkType.aClass22_246) {
													ParamTypeList.anInt6418 = MusicPatch.anInt5640;
													if (PointEntity.anInt4075 == 0) {
														Class4_Sub1_Sub6_Sub2.aString39 = null;
														IdkType.aClass22_246 = null;
														GameShell.aClass15Array2 = null;
														Class95_Sub2.anInt6410 = 0;
														Static395.aString63 = null;
														return true;
													}
													Static395.aString63 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
													if (local206) {
														Class14.aClass4_Sub12_Sub1_3.method2537();
													}
													@Pc(4078) long local4078 = Class14.aClass4_Sub12_Sub1_3.method2501();
													Class4_Sub1_Sub6_Sub2.aString39 = Class4_Sub9.method763(local4078);
													Class267.aByte103 = Class14.aClass4_Sub12_Sub1_3.method2542();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
													if (local224 == 255) {
														IdkType.aClass22_246 = null;
														return true;
													}
													Class95_Sub2.anInt6410 = local224;
													@Pc(4104) Class15[] local4104 = new Class15[100];
													for (local1190 = 0; local1190 < Class95_Sub2.anInt6410; local1190++) {
														local4104[local1190] = new Class15();
														local4104[local1190].aString8 = Class14.aClass4_Sub12_Sub1_3.method2537();
														local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
														if (local206) {
															local4104[local1190].aString7 = Class14.aClass4_Sub12_Sub1_3.method2537();
														} else {
															local4104[local1190].aString7 = local4104[local1190].aString8;
														}
														local4104[local1190].aString6 = Class79.method4868(local4104[local1190].aString7);
														local4104[local1190].anInt272 = Class14.aClass4_Sub12_Sub1_3.method2536();
														local4104[local1190].aByte1 = Class14.aClass4_Sub12_Sub1_3.method2542();
														local4104[local1190].aString5 = Class14.aClass4_Sub12_Sub1_3.method2537();
														if (local4104[local1190].aString7.equals(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44)) {
															Class103.aByte18 = local4104[local1190].aByte1;
														}
													}
													local1267 = Class95_Sub2.anInt6410;
													while (local1267 > 0) {
														local1858 = true;
														local1267--;
														for (local2494 = 0; local2494 < local1267; local2494++) {
															if (local4104[local2494].aString6.compareTo(local4104[local2494 + 1].aString6) > 0) {
																local4235 = local4104[local2494];
																local4104[local2494] = local4104[local2494 + 1];
																local1858 = false;
																local4104[local2494 + 1] = local4235;
															}
														}
														if (local1858) {
															break;
														}
													}
													GameShell.aClass15Array2 = local4104;
													IdkType.aClass22_246 = null;
													return true;
												} else if (Class4_Sub41.aClass22_307 == IdkType.aClass22_246) {
													Class4_Sub6_Sub24.aClass114_1.reset();
													HookRequest.anInt5255 += 32;
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == CacheableNodeQueue.aClass22_196) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2538();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2515();
													if (local70 == 65535) {
														local70 = -1;
													}
													local335 = Class14.aClass4_Sub12_Sub1_3.method2510();
													if (GLBillowTextureData.method4415(local327)) {
														GlParticleRenderer.method914(1, local335, local70, -1);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == Static433.aClass22_327) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2529();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
													if (GLBillowTextureData.method4415(local70)) {
														GlParticleRenderer.method914(5, local327, Class6.anInt5452, 0);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == FloTypeList.aClass22_173) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2536() << 0;
													local228 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local1190 = Class14.aClass4_Sub12_Sub1_3.method2490();
													if (GLBillowTextureData.method4415(local327)) {
														Class189.method4175(local1190, local224, local335, local228, local70);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == TextureOpMonochromeEdgeDetector.aClass22_316) {
													IdkType.aClass22_246 = null;
													Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
													Model.anInt6277 = 1;
													return true;
												} else if (MusicPatchInfo.aClass22_310 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2490();
													@Pc(4427) boolean local4427 = (local327 & 0x1) == 1;
													local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local535 = Class14.aClass4_Sub12_Sub1_3.method2537();
													if (local535.equals("")) {
														local535 = local212;
													}
													local2080 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local1747 = Class14.aClass4_Sub12_Sub1_3.method2537();
													if (local1747.equals("")) {
														local1747 = local2080;
													}
													if (local4427) {
														for (local2473 = 0; local2473 < Component.anInt6823; local2473++) {
															if (Static102.aStringArray7[local2473].equals(local1747)) {
																GLBillowTextureData.aStringArray27[local2473] = local212;
																Static102.aStringArray7[local2473] = local535;
																Class4_Sub1_Sub17.aStringArray26[local2473] = local2080;
																GpuPointLight.aStringArray11[local2473] = local1747;
																break;
															}
														}
													} else {
														GLBillowTextureData.aStringArray27[Component.anInt6823] = local212;
														Static102.aStringArray7[Component.anInt6823] = local535;
														Class4_Sub1_Sub17.aStringArray26[Component.anInt6823] = local2080;
														GpuPointLight.aStringArray11[Component.anInt6823] = local1747;
														EffectManagerGL.aBooleanArray20[Component.anInt6823] = (local327 & 0x2) == 2;
														Component.anInt6823++;
													}
													IdkType.aClass22_246 = null;
													Class4_Sub1_Sub9.anInt3027 = MusicPatch.anInt5640;
													return true;
												} else if (IdkType.aClass22_246 == Class21.aClass22_33) {
													DequeIterator.anInt1847 = Class14.aClass4_Sub12_Sub1_3.method2490();
													IdkType.aClass22_246 = null;
													Static308.anInt5413 = MusicPatch.anInt5640;
													return true;
												} else if (IdkType.aClass22_246 == Static167.aClass22_144) {
													Class120.anInt5353 = Class14.aClass4_Sub12_Sub1_3.method2536() * 30;
													IdkType.aClass22_246 = null;
													Static308.anInt5413 = MusicPatch.anInt5640;
													return true;
												} else if (Class56.aClass22_80 == IdkType.aClass22_246) {
													local813 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2510();
													if (GLBillowTextureData.method4415(local70)) {
														SeqTypeList.method1398(local813, local335);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == Class16_Sub2.aClass22_204) {
													Class4_Sub1_Sub9.method2572(Class4_Sub5.aClass21_4);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IDKTypeList.aClass22_146 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2548();
													local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (GLBillowTextureData.method4415(local335)) {
														Class4_Sub1_Sub9.method2569(local210, local327);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (EntityNodeList.IF_BUTTON8 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2529();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2538();
													if (GLBillowTextureData.method4415(local335)) {
														Static14.method145(local327, local70);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (Ground.aClass22_332 == IdkType.aClass22_246) {
													Static211.method3203();
													IdkType.aClass22_246 = null;
													return false;
												} else if (IdkType.aClass22_246 == ParticleNode.aClass22_185) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2510();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
													if (GLBillowTextureData.method4415(local327)) {
														Class16_Sub2.method3585(local335, local70);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == FileOnDisk.aClass22_194) {
													ByteArrayWrapper.cameraStepV = Class14.aClass4_Sub12_Sub1_3.method2534() << 3;
													Class70_Sub2.anInt6487 = Class14.aClass4_Sub12_Sub1_3.method2542() << 3;
													CursorType.anInt2426 = Class14.aClass4_Sub12_Sub1_3.method2523();
													while (PointEntity.anInt4075 > Class14.aClass4_Sub12_Sub1_3.currentPosition) {
														@Pc(4723) Class21 local4723 = MapRegion.method5650()[Class14.aClass4_Sub12_Sub1_3.method2490()];
														Class4_Sub1_Sub9.method2572(local4723);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == EnumTypeList.aClass22_219) {
													TextureOpFlip.anInt3532 = Class14.aClass4_Sub12_Sub1_3.method2490();
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == GpuFaceNormal.aClass22_313) {
													Class4_Sub1_Sub9.method2572(Class28.aClass21_6);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == ByteArrayWrapper.aClass22_51) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (GLBillowTextureData.method4415(local327)) {
														TextureOpIrregularBricks.method1773();
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (Mesh.aClass22_164 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2535();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2529();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2535();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2515();
													if (GLBillowTextureData.method4415(local224)) {
														GlVboElementsArrayBuffer.method5189(local70, local335, local327);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == Class4_Sub6_Sub22.aClass22_199) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (local327 == 65535) {
														local327 = -1;
													}
													local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
													Huffman.method2934(local327, local70, local224, local335);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == GlRenderList.aClass22_63) {
													Class4_Sub1_Sub9.method2572(Static410.aClass21_15);
													IdkType.aClass22_246 = null;
													return true;
												} else if (Js5GroupLoader.aClass22_269 == IdkType.aClass22_246) {
													Class4_Sub1_Sub9.method2572(Class25.aClass21_5);
													IdkType.aClass22_246 = null;
													return true;
												} else if (Static45.aClass22_49 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2536() << 0;
													local228 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local1190 = Class14.aClass4_Sub12_Sub1_3.method2490();
													if (GLBillowTextureData.method4415(local327)) {
														Class16_Sub2_Sub3.method3596(local224, true, local1190, local335, local70, local228);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == GlBitBuffer.aClass22_129) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (local327 == 65535) {
														local327 = -1;
													}
													local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
													Class164.method3912(local70, local335, local327, local224);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == CursorTypeList.aClass22_236) {
													Class4_Sub1_Sub9.method2572(Js5ResourceRequest.aClass21_11);
													IdkType.aClass22_246 = null;
													return true;
												} else if (Class9_Sub3.aClass22_71 == IdkType.aClass22_246) {
													MouseManager.method2241(false);
													IdkType.aClass22_246 = null;
													return true;
												} else if (BillowNoiseGenerator.aClass22_263 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2489();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2510();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
													if (GLBillowTextureData.method4415(local335)) {
														Class154.method3686(local327, local70);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (MapSpriteTypeList.aClass22_331 == IdkType.aClass22_246) {
													local813 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local1411 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
													if (local1411) {
														local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
													} else {
														local210 = local813;
													}
													local224 = Class14.aClass4_Sub12_Sub1_3.method2536();
													@Pc(5040) byte local5040 = Class14.aClass4_Sub12_Sub1_3.method2542();
													local230 = false;
													if (local5040 == -128) {
														local230 = true;
													}
													if (local230) {
														if (Class95_Sub2.anInt6410 == 0) {
															IdkType.aClass22_246 = null;
															return true;
														}
														for (local2473 = 0; Class95_Sub2.anInt6410 > local2473 && (!GameShell.aClass15Array2[local2473].aString7.equals(local210) || GameShell.aClass15Array2[local2473].anInt272 != local224); local2473++) {
														}
														if (local2473 < Class95_Sub2.anInt6410) {
															while (local2473 < Class95_Sub2.anInt6410 - 1) {
																GameShell.aClass15Array2[local2473] = GameShell.aClass15Array2[local2473 + 1];
																local2473++;
															}
															Class95_Sub2.anInt6410--;
															GameShell.aClass15Array2[Class95_Sub2.anInt6410] = null;
														}
													} else {
														local251 = Class14.aClass4_Sub12_Sub1_3.method2537();
														local4235 = new Class15();
														local4235.aString8 = local813;
														local4235.aString7 = local210;
														local4235.aString6 = Class79.method4868(local4235.aString7);
														local4235.aByte1 = local5040;
														local4235.anInt272 = local224;
														local4235.aString5 = local251;
														for (local1267 = Class95_Sub2.anInt6410 - 1; local1267 >= 0; local1267--) {
															local2494 = GameShell.aClass15Array2[local1267].aString6.compareTo(local4235.aString6);
															if (local2494 == 0) {
																GameShell.aClass15Array2[local1267].anInt272 = local224;
																GameShell.aClass15Array2[local1267].aByte1 = local5040;
																GameShell.aClass15Array2[local1267].aString5 = local251;
																if (local210.equals(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44)) {
																	Class103.aByte18 = local5040;
																}
																ParamTypeList.anInt6418 = MusicPatch.anInt5640;
																IdkType.aClass22_246 = null;
																return true;
															}
															if (local2494 < 0) {
																break;
															}
														}
														if (Class95_Sub2.anInt6410 >= GameShell.aClass15Array2.length) {
															IdkType.aClass22_246 = null;
															return true;
														}
														for (local2494 = Class95_Sub2.anInt6410 - 1; local2494 > local1267; local2494--) {
															GameShell.aClass15Array2[local2494 + 1] = GameShell.aClass15Array2[local2494];
														}
														if (Class95_Sub2.anInt6410 == 0) {
															GameShell.aClass15Array2 = new Class15[100];
														}
														GameShell.aClass15Array2[local1267 + 1] = local4235;
														Class95_Sub2.anInt6410++;
														if (local210.equals(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44)) {
															Class103.aByte18 = local5040;
														}
													}
													IdkType.aClass22_246 = null;
													ParamTypeList.anInt6418 = MusicPatch.anInt5640;
													return true;
												} else if (IdkType.aClass22_246 == Static1.aClass22_1) {
													Class4_Sub1_Sub9.method2572(NPCTypeList.aClass21_14);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == EnvironmentContext.aClass22_273) {
													Class4_Sub1_Sub9.method2572(Static179.aClass21_10);
													IdkType.aClass22_246 = null;
													return true;
												} else if (Static214.aClass22_178 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (local327 == 65535) {
														local327 = -1;
													}
													local70 = Class14.aClass4_Sub12_Sub1_3.method2548();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2511();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2538();
													local228 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (local228 == 65535) {
														local228 = -1;
													}
													if (GLBillowTextureData.method4415(local70)) {
														for (local1190 = local228; local1190 <= local327; local1190++) {
															local2311 = ((long) local335 << 32) + ((long) local1190);
															local2317 = (GlRectangleTexture) Static211.aClass102_18.method2700(local2311);
															if (local2317 != null) {
																local2329 = new GlRectangleTexture(local2317.anInt5105, local224);
																local2317.method5684();
															} else if (local1190 == -1) {
																local2329 = new GlRectangleTexture(ParticleBinaryTree.method5121(local335).aClass4_Sub33_2.anInt5105, local224);
															} else {
																local2329 = new GlRectangleTexture(0, local224);
															}
															Static211.aClass102_18.method2703(local2311, local2329);
														}
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == Class225.aClass22_293) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2548();
													local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
													if (GLBillowTextureData.method4415(local327)) {
														Class4_Sub1_Sub9.method2569(local210, local335);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (Static277.aClass22_231 == IdkType.aClass22_246) {
													Resampler.method5553(Class14.aClass4_Sub12_Sub1_3.method2537());
													IdkType.aClass22_246 = null;
													return true;
												} else if (Class6.aClass22_253 == IdkType.aClass22_246) {
													CursorType.anInt2426 = Class14.aClass4_Sub12_Sub1_3.method2523();
													ByteArrayWrapper.cameraStepV = Class14.aClass4_Sub12_Sub1_3.method2534() << 3;
													Class70_Sub2.anInt6487 = Class14.aClass4_Sub12_Sub1_3.method2507() << 3;
													for (@Pc(5469) Class4_Sub18 local5469 = (Class4_Sub18) LocTypeList.aClass102_40.method2705(); local5469 != null; local5469 = (Class4_Sub18) LocTypeList.aClass102_40.method2704()) {
														local70 = (int) (local5469.aLong224 & 0x3FFFL);
														local335 = (int) (local5469.aLong224 >> 14 & 0x3FFFL);
														local224 = (int) (local5469.aLong224 >> 28 & 0x3L);
														if (local224 == CursorType.anInt2426 && local70 >= ByteArrayWrapper.cameraStepV && ByteArrayWrapper.cameraStepV + 8 > local70 && Class70_Sub2.anInt6487 <= local335 && Class70_Sub2.anInt6487 + 8 > local335) {
															local5469.method5684();
															MapSpriteTypeList.method5595(CursorType.anInt2426, local70, local335);
														}
													}
													for (@Pc(5544) Class4_Sub9 local5544 = (Class4_Sub9) Class75.aClass183_15.method4140(); local5544 != null; local5544 = (Class4_Sub9) Class75.aClass183_15.method4144()) {
														if (ByteArrayWrapper.cameraStepV <= local5544.anInt793 && ByteArrayWrapper.cameraStepV + 8 > local5544.anInt793 && local5544.anInt790 >= Class70_Sub2.anInt6487 && Class70_Sub2.anInt6487 + 8 > local5544.anInt790 && CursorType.anInt2426 == local5544.anInt800) {
															local5544.anInt796 = 0;
														}
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == StructTypeList.aClass22_95) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2511();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2548();
													if (local335 == 65535) {
														local335 = -1;
													}
													if (GLBillowTextureData.method4415(local70)) {
														GlParticleRenderer.method914(2, local327, local335, -1);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (SimpleMouseLog.aClass22_46 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2506();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2529();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local535 = "";
													local2080 = local535;
													if ((local335 & 0x1) != 0) {
														local535 = Class14.aClass4_Sub12_Sub1_3.method2537();
														if ((local335 & 0x2) == 0) {
															local2080 = local535;
														} else {
															local2080 = Class14.aClass4_Sub12_Sub1_3.method2537();
														}
													}
													local1747 = Class14.aClass4_Sub12_Sub1_3.method2537();
													if (local327 == 99) {
														Static441.method5568(local1747);
													} else if (local2080.equals("") || !ParticleList.method3549(local2080)) {
														Class83.method2268(local70, local327, local1747, local535, local2080);
													} else {
														IdkType.aClass22_246 = null;
														return true;
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == ModeWhere.aClass22_287) {
													Component.method5301();
													IdkType.aClass22_246 = null;
													return true;
												} else if (OggOtherStream.aClass22_266 == IdkType.aClass22_246) {
													Class4_Sub1_Sub9.method2572(StructTypeList.aClass21_7);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == Class136.aClass22_192) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2529();
													if (GLBillowTextureData.method4415(local327)) {
														@Pc(5751) Class4_Sub43 local5751 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local70);
														if (local5751 != null) {
															Class62.method1606(true, local5751, false);
														}
														if (Class180.aClass247_16 != null) {
															Class4_Sub12_Sub1.method1142(Class180.aClass247_16);
															Class180.aClass247_16 = null;
														}
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == StructType.aClass22_278) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2523();
													local70 = local327 >> 2;
													local335 = local327 & 0x3;
													local224 = EnvironmentContext.anIntArray410[local70];
													local228 = Class14.aClass4_Sub12_Sub1_3.method2511();
													local1190 = local228 >> 28 & 0x3;
													local2473 = local228 >> 14 & 0x3FFF;
													local1263 = local228 & 0x3FFF;
													local1267 = Class14.aClass4_Sub12_Sub1_3.method2538();
													@Pc(5820) int local5820 = local1263 - OpenGLTextureCache.anInt1771;
													if (local1267 == 65535) {
														local1267 = -1;
													}
													local2473 -= Class50_Sub1.anInt3453;
													Class4_Sub36.method4071(local1190, local335, local1267, local224, local70, local2473, local5820);
													IdkType.aClass22_246 = null;
													return true;
												} else if (GlRectangleTexture.STELLAR_DAWN == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local228 = Class14.aClass4_Sub12_Sub1_3.method2490();
													local1190 = Class14.aClass4_Sub12_Sub1_3.method2536();
													if (GLBillowTextureData.method4415(local327)) {
														TextureOpBoxBlur.aBooleanArray17[local70] = true;
														Static105.anIntArray161[local70] = local335;
														ObjStackEntity.anIntArray46[local70] = local224;
														BzipDecompressor.anIntArray65[local70] = local228;
														Class95_Sub3.anIntArray486[local70] = local1190;
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == JavaBillboardFace.aClass22_175) {
													Class4_Sub1_Sub9.method2572(MaterialType.aClass21_16);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == NamedFileReference.aClass22_301) {
													Static167.aString35 = PointEntity.anInt4075 > 2 ? Class14.aClass4_Sub12_Sub1_3.method2537() : Class4_Sub6_Sub15.aClass83_76.method2267(NativeBillboardFace.anInt3423);
													TextureOpConstant.anInt4097 = PointEntity.anInt4075 > 0 ? Class14.aClass4_Sub12_Sub1_3.method2536() : -1;
													IdkType.aClass22_246 = null;
													if (TextureOpConstant.anInt4097 == 65535) {
														TextureOpConstant.anInt4097 = -1;
													}
													return true;
												} else if (CursorTypeList.aClass22_235 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2529();
													TextureOpDiagonalWaves.aClass199_7 = Class126.aClass66_4.method1739(local327);
													IdkType.aClass22_246 = null;
													return true;
												} else if (Class4_Sub45.aClass22_335 == IdkType.aClass22_246) {
													Class4_Sub25.aClass104_2 = Static2.method6(Class14.aClass4_Sub12_Sub1_3.method2490());
													IdkType.aClass22_246 = null;
													return true;
												} else if (MECTypeList.aClass22_249 == IdkType.aClass22_246) {
													ProjectileAnimationNode.method1665(Class14.aClass4_Sub12_Sub1_3, PointEntity.anInt4075, Class126.aClass66_4);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == TextureOperation.CONTAINER_MAP) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2515();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2536();
													@Pc(6007) byte local6007 = Class14.aClass4_Sub12_Sub1_3.method2534();
													if (GLBillowTextureData.method4415(local327)) {
														Class190.method4221(local70, local6007);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (ReflectiveRipplingWaterEffect.aClass22_16 == IdkType.aClass22_246) {
													Class14.aClass4_Sub12_Sub1_3.currentPosition += 28;
													if (Class14.aClass4_Sub12_Sub1_3.method2500()) {
														HashTable.method2669(Class14.aClass4_Sub12_Sub1_3, Class14.aClass4_Sub12_Sub1_3.currentPosition - 28);
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (MemoryPool.aClass22_214 == IdkType.aClass22_246) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2515();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2515();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2529();
													local224 = Class14.aClass4_Sub12_Sub1_3.method2515();
													if (GLBillowTextureData.method4415(local327)) {
														Class120_Sub1.method4114(local335, local70 + (local224 << 16));
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == CursorType.aClass22_101) {
													Class4_Sub1_Sub9.method2572(QuickChatValueType.aClass21_9);
													IdkType.aClass22_246 = null;
													return true;
												} else if (IdkType.aClass22_246 == GameCanvas.aClass22_284) {
													local327 = Class14.aClass4_Sub12_Sub1_3.method2511();
													local70 = Class14.aClass4_Sub12_Sub1_3.method2510();
													local335 = Class14.aClass4_Sub12_Sub1_3.method2538();
													if (GLBillowTextureData.method4415(local335)) {
														@Pc(6126) Class4_Sub43 local6126 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local70);
														local1576 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local327);
														if (local1576 != null) {
															Class62.method1606(local6126 == null || local1576.anInt6979 != local6126.anInt6979, local1576, false);
														}
														if (local6126 != null) {
															local6126.method5684();
															MusicPatch.aClass102_29.method2703((long) local327, local6126);
														}
														@Pc(6164) com.jagex.game.runetek5.config.rsinterface.Component local6164 = ParticleBinaryTree.method5121(local70);
														if (local6164 != null) {
															Class4_Sub12_Sub1.method1142(local6164);
														}
														local6164 = ParticleBinaryTree.method5121(local327);
														if (local6164 != null) {
															Class4_Sub12_Sub1.method1142(local6164);
															Class95_Sub3_Sub1.method4998(local6164, true);
														}
														if (EnumType.anInt5766 != -1) {
															Class4_Sub37.method4165(EnumType.anInt5766, 1);
														}
													}
													IdkType.aClass22_246 = null;
													return true;
												} else if (Static433.aClass22_326 == IdkType.aClass22_246) {
													local206 = Class14.aClass4_Sub12_Sub1_3.method2490() == 1;
													local210 = Class14.aClass4_Sub12_Sub1_3.method2537();
													local212 = local210;
													if (local206) {
														local212 = Class14.aClass4_Sub12_Sub1_3.method2537();
													}
													local1252 = Class14.aClass4_Sub12_Sub1_3.method2501();
													local1257 = Class14.aClass4_Sub12_Sub1_3.method2536();
													local3817 = Class14.aClass4_Sub12_Sub1_3.method2493();
													local2494 = Class14.aClass4_Sub12_Sub1_3.method2490();
													@Pc(6247) long local6247 = (local1257 << 32) + local3817;
													@Pc(6249) boolean local6249 = false;
													@Pc(6251) int local6251 = 0;
													while (true) {
														if (local6251 >= 100) {
															if (local2494 <= 1) {
																if (Class16.aBoolean628 && !MemoryPool.aBoolean443 || Static308.aBoolean486) {
																	local6249 = true;
																} else if (ParticleList.method3549(local212)) {
																	local6249 = true;
																}
															}
															break;
														}
														if (local6247 == QuickChatCategoryType.aLongArray7[local6251]) {
															local6249 = true;
															break;
														}
														local6251++;
													}
													if (!local6249 && Texture3DShaderGL.anInt1373 == 0) {
														QuickChatCategoryType.aLongArray7[Skybox.anInt6719] = local6247;
														Skybox.anInt6719 = (Skybox.anInt6719 + 1) % 100;
														@Pc(6311) String local6311 = Class16_Sub1_Sub1.method297(MECType.method3776(Class14.aClass4_Sub12_Sub1_3));
														if (local2494 == 2 || local2494 == 3) {
															LocalRequester.method5438(-1, "<img=1>" + local212, local6311, Static45.method766(local1252), 0, "<img=1>" + local210, 9);
														} else if (local2494 == 1) {
															LocalRequester.method5438(-1, "<img=0>" + local212, local6311, Static45.method766(local1252), 0, "<img=0>" + local210, 9);
														} else {
															LocalRequester.method5438(-1, local212, local6311, Static45.method766(local1252), 0, local210, 9);
														}
													}
													IdkType.aClass22_246 = null;
													return true;
												} else {
													Class64_Sub1.method1654(null, "T1 - " + (IdkType.aClass22_246 == null ? -1 : IdkType.aClass22_246.method527()) + "," + (Static380.aClass22_294 == null ? -1 : Static380.aClass22_294.method527()) + "," + (Class190.aClass22_257 == null ? -1 : Class190.aClass22_257.method527()) + " - " + PointEntity.anInt4075);
													BasType.method3639();
													return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!is", name = "b", descriptor = "(I)V")
	public static void method2817() {
		LegacyMouseManager.aClass68_43.method1777();
	}
@OriginalMember(owner = "client!is", name = "a", descriptor = "(BI)I")
	public static int method2820(@OriginalArg(1) int arg0) {
		return arg0 & 0x7F;
	}
}
