package com.jagex.game.runetek5.deob;

import jagex3.jagmisc.jagmisc;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.IOException;
import java.net.Socket;
import java.util.GregorianCalendar;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.objtype.ObjTypeList;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageTypeList;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.config.sequence.SeqTypeList;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.deob.Class170;
import com.jagex.game.runetek5.deob.Class186;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.js5.NamedFileReference;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTiledSprite;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub19;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.js5.request.Js5ResourceRequest;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.deob.FloType;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.gl.GlFramebuffer;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub8;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpCurve;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.Class48;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.deob.Class78_Sub1;
import com.jagex.game.runetek5.deob.Class225;
import com.jagex.game.runetek5.config.bas.BasType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.entity.pathingentity.player.PlayerAppearance;
import com.jagex.game.runetek5.scene.CollisionMap;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.config.staticelements.StaticElements;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub12;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTexture;
import com.jagex.game.runetek5.deob.Class36;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.entity.EntityNode;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.Class223;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub14;
import com.jagex.game.runetek5.deob.SpotAnimation;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.sound.player.PcmPlayer;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class16_Sub4;
import com.jagex.game.runetek5.deob.Class101;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationType;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class95;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.deob.Class173;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.Class208;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.graphics.Toolkit;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.config.particle.ParticleType;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.client.camera.CameraZoom;
import com.jagex.game.runetek5.client.camera.CameraZoomController;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.client.camera.CameraPreferences;

import com.jagex.game.runetek5.client.input.mouse.MiddleMouseCamera;
import com.jagex.game.runetek5.client.input.mouse.SimpleMouseLog;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.js5.request.Js5WorkerRequestMessage;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpConstant;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!client")
public final class client extends GameShell {

	@OriginalMember(owner = "client!client", name = "main", descriptor = "([Ljava/lang/String;)V")
	public static void main(@OriginalArg(0) String[] arg0) {
		try {
			if (arg0.length != 4) {
				Class186.method4159("argument count");
			}
			Static107.anInt2335 = Integer.parseInt(arg0[0]);
			GlToolkit.aClass219_2 = SoundBankCache.MODE_WHERE_WTQA;
			if (arg0[1].equals("live")) {
				Class95.aClass117_9 = Js5ResourceRequest.aClass117_4;
			} else if (arg0[1].equals("rc")) {
				Class95.aClass117_9 = Class204.aClass117_6;
			} else if (arg0[1].equals("wip")) {
				Class95.aClass117_9 = Texture3DShaderGL.aClass117_1;
			} else {
				Class186.method4159("modewhat");
			}
			NativeBillboardFace.anInt3423 = Static73.method1357(arg0[2]);
			if (NativeBillboardFace.anInt3423 == -1) {
				if (arg0[2].equals("english")) {
					NativeBillboardFace.anInt3423 = 0;
				} else if (arg0[2].equals("german")) {
					NativeBillboardFace.anInt3423 = 1;
				} else {
					Class186.method4159("language");
				}
			}
			Class70_Sub2.aBoolean591 = false;
			Class105.aBoolean607 = false;
			if (arg0[3].equals("game0")) {
				DynamicWall.aClass121_3 = TextureOpTexture.aClass121_1;
			} else if (arg0[3].equals("game1")) {
				DynamicWall.aClass121_3 = FixedFunctionSpecularPass.WRITE_PING;
			} else {
				Class186.method4159("game");
			}
			Model.aBoolean573 = true;
			Class4_Sub29.aBoolean423 = true;
			ReflectionCheck.aString13 = "";
			ModelNormalsGL.anInt4373 = 0;
			GlCubeTexture.anInt3259 = 0;
			StructType.anInt5940 = DynamicWall.aClass121_3.anInt3570;
			@Pc(130) client local130 = new client();
			ClientScript.aClient1 = local130;
			local130.method885(DynamicWall.aClass121_3.aString36, Class95.aClass117_9.method2865() + 32);
			ModelNormalsGL.aFrame1.setLocation(40, 40);
		} catch (@Pc(153) Exception local153) {
			Class64_Sub1.method1654(local153, null);
		}
	}

	@OriginalMember(owner = "client!client", name = "i", descriptor = "(I)V")
	private void method893() {
		if (MECType.aClass255_2.errors > Class190.anInt5505) {
			ParticleBinaryTree.anInt6543 = (MECType.aClass255_2.errors * 50 - 50) * 5;
			if (Skybox.anInt6723 == Class189.anInt5436) {
				Skybox.anInt6723 = Class4_Sub6_Sub8.anInt1949;
			} else {
				Skybox.anInt6723 = Class189.anInt5436;
			}
			if (ParticleBinaryTree.anInt6543 > 3000) {
				ParticleBinaryTree.anInt6543 = 3000;
			}
			if (MECType.aClass255_2.errors >= 2 && MECType.aClass255_2.response == 6) {
				this.raiseErrorScreen("js5connect_outofdate");
				VertexDeclarationComponent.anInt6667 = 1000;
				return;
			}
			if (MECType.aClass255_2.errors >= 4 && MECType.aClass255_2.response == -1) {
				this.raiseErrorScreen("js5crc");
				VertexDeclarationComponent.anInt6667 = 1000;
				return;
			}
			if (MECType.aClass255_2.errors >= 4 && (VertexDeclarationComponent.anInt6667 == 0 || VertexDeclarationComponent.anInt6667 == 5)) {
				if (MECType.aClass255_2.response == 7 || MECType.aClass255_2.response == 9) {
					this.raiseErrorScreen("js5connect_full");
				} else if (MECType.aClass255_2.response <= 0) {
					this.raiseErrorScreen("js5io");
				} else {
					this.raiseErrorScreen("js5connect");
				}
				VertexDeclarationComponent.anInt6667 = 1000;
				return;
			}
		}
		Class190.anInt5505 = MECType.aClass255_2.errors;
		if (ParticleBinaryTree.anInt6543 > 0) {
			ParticleBinaryTree.anInt6543--;
			return;
		}
		try {
			if (Static78.anInt1567 == 0) {
				LocEntity.aClass199_2 = Class126.aClass66_4.method1728(Class191.aString54, Skybox.anInt6723);
				Static78.anInt1567++;
			}
			if (Static78.anInt1567 == 1) {
				if (LocEntity.aClass199_2.anInt5760 == 2) {
					this.method903(1000);
					return;
				}
				if (LocEntity.aClass199_2.anInt5760 == 1) {
					Static78.anInt1567++;
				}
			}
			if (Static78.anInt1567 == 2) {
				EntityNode.aClass11_14 = new BufferedSocket((Socket) LocEntity.aClass199_2.anObject29, Class126.aClass66_4);
				@Pc(194) Packet local194 = new Packet(5);
				local194.method2551(Class4_Sub1_Sub11.aClass60_2.anInt1812);
				local194.method2531(592);
				EntityNode.aClass11_14.method142(5, local194.buffer);
				Static78.anInt1567++;
				SpotAnimationTypeList.aLong189 = Class168.method3962();
			}
			if (Static78.anInt1567 == 3) {
				if (VertexDeclarationComponent.anInt6667 == 0 || VertexDeclarationComponent.anInt6667 == 5 || EntityNode.aClass11_14.method133() > 0) {
					@Pc(259) int local259 = EntityNode.aClass11_14.method139();
					if (local259 != 0) {
						this.method903(local259);
						return;
					}
					Static78.anInt1567++;
				} else if (Class168.method3962() - SpotAnimationTypeList.aLong189 > 30000L) {
					this.method903(1001);
					return;
				}
			}
			if (Static78.anInt1567 == 4) {
				@Pc(293) boolean local293 = VertexDeclarationComponent.anInt6667 == 5 || VertexDeclarationComponent.anInt6667 == 10 || VertexDeclarationComponent.anInt6667 == 28;
				MECType.aClass255_2.method5461(EntityNode.aClass11_14, !local293);
				LocEntity.aClass199_2 = null;
				Static78.anInt1567 = 0;
				EntityNode.aClass11_14 = null;
			}
		} catch (@Pc(312) IOException local312) {
			this.method903(1002);
		}
	}

	@OriginalMember(owner = "client!client", name = "e", descriptor = "(I)V")
	@Override
	protected void method886() {
		if (TextureOpKaleidoscope.aBoolean256) {
			Class190.method4216();
		}
		if (MonoStereoData.aClass19_16 != null) {
			MonoStereoData.aClass19_16.method4267();
		}
		if (BillowNoiseGenerator.aFrame3 != null) {
			Class267.method5620(Class126.aClass66_4, BillowNoiseGenerator.aFrame3);
			BillowNoiseGenerator.aFrame3 = null;
		}
		if (QuickchatMessageTypeList.aClass11_5 != null) {
			QuickchatMessageTypeList.aClass11_5.method138();
			QuickchatMessageTypeList.aClass11_5 = null;
		}
		if (PlayerEntity.aClass14_1 != null) {
			PlayerEntity.aClass14_1.detachFrom(Class164.aCanvas5);
		}
		PlayerEntity.aClass14_1 = null;
		SpotAnimation.method1241();
		MECType.aClass255_2.method5474();
		Class70_Sub2.aClass254_3.method5433();
		if (Class4_Sub6_Sub35.aClass256_1 != null) {
			Class4_Sub6_Sub35.aClass256_1.close();
			Class4_Sub6_Sub35.aClass256_1 = null;
		}
	}

	@OriginalMember(owner = "client!client", name = "j", descriptor = "(I)V")
	private void method894() {
		if (VertexDeclarationComponent.anInt6667 == 1000) {
			return;
		}
		@Pc(19) long local19 = Class4_Sub1_Sub11.method2607() / 1000000L - Class4_Sub1_Sub11.aLong107;
		Class4_Sub1_Sub11.aLong107 = Class4_Sub1_Sub11.method2607() / 1000000L;
		@Pc(27) boolean local27 = Class120_Sub1.method4111();
		if (local27 && TextureOpSquareWaveform.aBoolean666 && Class4_Sub43.aClass49_2 != null) {
			Class4_Sub43.aClass49_2.method2064();
		}
		if (VertexDeclarationComponent.anInt6667 == 30 || VertexDeclarationComponent.anInt6667 == 10) {
			if (TileMessage.aLong223 != 0L && Class168.method3962() > TileMessage.aLong223) {
				Class120.method4107(Class269.method5664(), TextureOpBrightness.aClass50_Sub1_1.anInt3450, TextureOpBrightness.aClass50_Sub1_1.anInt3431, false);
			} else if (!MonoStereoData.aClass19_16.method4258() && Js5WorkerRequestMessage.aBoolean383) {
				ObjTypeList.method4711();
			}
		}
		@Pc(98) int local98;
		@Pc(102) int local102;
		if (BillowNoiseGenerator.aFrame3 == null) {
			@Pc(89) Container local89;
			if (ModelNormalsGL.aFrame1 == null) {
				local89 = Class126.aClass66_4.anApplet1;
			} else {
				local89 = ModelNormalsGL.aFrame1;
			}
			local98 = local89.getSize().width;
			local102 = local89.getSize().height;
			if (local89 == ModelNormalsGL.aFrame1) {
				@Pc(108) Insets local108 = ModelNormalsGL.aFrame1.getInsets();
				local102 -= local108.bottom + local108.top;
				local98 -= local108.left + local108.right;
			}
			if (local98 != Class4_Sub6_Sub37.anInt7000 || Static17.anInt222 != local102) {
				if (MonoStereoData.aClass19_16 == null || MonoStereoData.aClass19_16.method4245()) {
					TextureCombinerOp.method3938();
				} else {
					Static17.anInt222 = local102;
					Class4_Sub6_Sub37.anInt7000 = local98;
				}
				TileMessage.aLong223 = Class168.method3962() + 500L;
			}
		}
		if (BillowNoiseGenerator.aFrame3 != null && !RequestManager.aBoolean457 && (VertexDeclarationComponent.anInt6667 == 30 || VertexDeclarationComponent.anInt6667 == 10)) {
			Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
		}
		@Pc(176) boolean local176 = false;
		if (TextureOpIrregularBricks.aBoolean189) {
			TextureOpIrregularBricks.aBoolean189 = false;
			local176 = true;
		}
		if (local176) {
			Class4_Sub6_Sub31.method4697();
		}
		if (MonoStereoData.aClass19_16 != null && MonoStereoData.aClass19_16.method4258() || Class269.method5664() != 1) {
			ModeWhere.method4940();
		}
		if (VertexDeclarationComponent.anInt6667 == 0) {
			EnvironmentContext.method4604(GlModel.aColorArray5[StructType.anInt5940], Class16_Sub2.aColorArray6[StructType.anInt5940], SpotAnimation.aColorArray3[StructType.anInt5940], GlFramebuffer.anInt4590, local176, Shadow.aString53);
		} else if (VertexDeclarationComponent.anInt6667 == 5) {
			Class4_Sub1_Sub8_Sub1.method3773(Class16_Sub2.aColorArray6[StructType.anInt5940].getRGB(), local176 | MonoStereoData.aClass19_16.method4258(), SpotAnimation.aColorArray3[StructType.anInt5940].getRGB(), GlModel.aColorArray5[StructType.anInt5940].getRGB(), Static331.aClass46_10, MonoStereoData.aClass19_16);
		} else if (VertexDeclarationComponent.anInt6667 == 10) {
			Static298.method4085();
		} else if (VertexDeclarationComponent.anInt6667 == 25 || VertexDeclarationComponent.anInt6667 == 28) {
			if (PlayerAppearance.anInt883 == 1) {
				if (TextureOpIrregularBricks.lastMiscUpdateTick > KeyRecordEntry.anInt448) {
					KeyRecordEntry.anInt448 = TextureOpIrregularBricks.lastMiscUpdateTick;
				}
				local98 = (KeyRecordEntry.anInt448 - TextureOpIrregularBricks.lastMiscUpdateTick) * 50 / KeyRecordEntry.anInt448;
				TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423) + "<br>(" + local98 + "%)", Class6.aClass46_9);
			} else if (PlayerAppearance.anInt883 == 2) {
				if (Terrain.anInt7298 < PathingEntitySequence.anInt5144) {
					Terrain.anInt7298 = PathingEntitySequence.anInt5144;
				}
				local98 = (Terrain.anInt7298 - PathingEntitySequence.anInt5144) * 50 / Terrain.anInt7298 + 50;
				TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423) + "<br>(" + local98 + "%)", Class6.aClass46_9);
			} else {
				TextureOpSquareWaveform.method5519(true, Resampler.aClass83_148.method2267(NativeBillboardFace.anInt3423), Class6.aClass46_9);
			}
		} else if (VertexDeclarationComponent.anInt6667 == 30) {
			Static45.method764(local19);
		} else if (VertexDeclarationComponent.anInt6667 == 40) {
			TextureOpSquareWaveform.method5519(true, Terrain.aClass83_150.method2267(NativeBillboardFace.anInt3423) + "<br>" + GlCubeTexture.aClass83_69.method2267(NativeBillboardFace.anInt3423), Class6.aClass46_9);
		}
		if (GlGround.lastSentMouseX == 3) {
			for (local98 = 0; local98 < SeqType.p12FullIndex; local98++) {
				@Pc(394) Rectangle local394 = Class4_Sub6_Sub23.aRectangleArray1[local98];
				if (Texture3DShaderGL.aBooleanArray9[local98]) {
					MonoStereoData.aClass19_16.method4293(local394.x, local394.width, local394.y, -1996553985, local394.height);
				} else if (Class4_Sub6_Sub25.aBooleanArray15[local98]) {
					MonoStereoData.aClass19_16.method4293(local394.x, local394.width, local394.y, -1996554240, local394.height);
				}
			}
		}
		if (LocalRequester.method5440()) {
			Static433.method5498(MonoStereoData.aClass19_16);
		}
		if ((VertexDeclarationComponent.anInt6667 == 30 || VertexDeclarationComponent.anInt6667 == 10) && GlGround.lastSentMouseX == 0 && Class269.method5664() == 1 && !local176 && SignLink.aString24.equals("1.1")) {
			local98 = 0;
			for (local102 = 0; local102 < SeqType.p12FullIndex; local102++) {
				if (Class4_Sub6_Sub25.aBooleanArray15[local102]) {
					Class4_Sub6_Sub25.aBooleanArray15[local102] = false;
					Class217.aRectangleArray2[local98++] = Class4_Sub6_Sub23.aRectangleArray1[local102];
				}
			}
			MonoStereoData.aClass19_16.method4290(Class217.aRectangleArray2, local98);
		} else if (VertexDeclarationComponent.anInt6667 != 0) {
			MonoStereoData.aClass19_16.method4247();
			for (local98 = 0; local98 < SeqType.p12FullIndex; local98++) {
				Class4_Sub6_Sub25.aBooleanArray15[local98] = false;
			}
		}
		if (TextureOpBrightness.aClass50_Sub1_1.anInt3437 == 0) {
			Class259.method5503(15L);
		} else if (TextureOpBrightness.aClass50_Sub1_1.anInt3437 == 1) {
			Class259.method5503(10L);
		} else if (TextureOpBrightness.aClass50_Sub1_1.anInt3437 == 2) {
			Class259.method5503(5L);
		} else if (TextureOpBrightness.aClass50_Sub1_1.anInt3437 == 3) {
			Class259.method5503(2L);
		}
		if (ParticleType.aBoolean10) {
			ParticleProducerType.method3117();
		}
		if (TextureOpBrightness.aClass50_Sub1_1.aBoolean297 && VertexDeclarationComponent.anInt6667 == 10 && EnumType.anInt5766 != -1) {
			TextureOpBrightness.aClass50_Sub1_1.aBoolean297 = false;
			TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
		}
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(B)V")
	@Override
	protected void cleanup() {
		try {
			this.method899();
		} catch (@Pc(14) OutOfMemoryError local14) {
			if (local14.getMessage() == null || !local14.getMessage().startsWith("native")) {
				throw local14;
			}
			ParticleList.method3551(0);
			Class64_Sub1.method1654(local14, local14.getMessage() + " (Recovered) " + this.method887());
		}
	}

	@OriginalMember(owner = "client!client", name = "e", descriptor = "(B)Ljava/lang/String;")
	@Override
	protected String method887() {
		@Pc(5) String local5 = null;
		try {
			local5 = "[1)" + Class50_Sub1.anInt3453 + "," + OpenGLTextureCache.anInt1771 + "," + GLBillowTextureData.anInt5666 + "," + Static283.anInt5187 + "|";
			if (Static1.aClass16_Sub1_Sub5_Sub1_1 != null) {
				local5 = local5 + "2)" + Class4_Sub6_Sub25.anInt4963 + "," + (Class50_Sub1.anInt3453 + Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0]) + "," + (Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0] + OpenGLTextureCache.anInt1771) + "|";
			}
			local5 = local5 + "3)" + Packet.anInt2973 + "|4)" + TextureOpBrightness.aClass50_Sub1_1.anInt3440 + "|5)" + Class269.method5664() + "|6)" + GpuPointLight.anInt2881 + "," + EffectiveVertex.anInt5346 + "|";
			local5 = local5 + "7)" + TextureOpBrightness.aClass50_Sub1_1.method2854(Packet.anInt2973) + "|";
			local5 = local5 + "8)" + TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973) + "|";
			local5 = local5 + "9)" + TextureOpBrightness.aClass50_Sub1_1.aBoolean294 + "|";
			local5 = local5 + "10)" + TextureOpBrightness.aClass50_Sub1_1.aBoolean307 + "|";
			local5 = local5 + "11)" + TextureOpBrightness.aClass50_Sub1_1.aBoolean300 + "|";
			local5 = local5 + "12)" + TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973) + "|";
			local5 = local5 + "13)" + BreakingWaterEffect.anInt1503 + "|";
			try {
				local5 = local5 + "|15)" + jagmisc.getTotalPhysicalMemory();
			} catch (@Pc(212) Throwable local212) {
			}
			local5 = local5 + "]";
		} catch (@Pc(223) Throwable local223) {
		}
		return local5;
	}

	@OriginalMember(owner = "client!client", name = "l", descriptor = "(I)V")
	private void method897() {
		LightGL.npcCount++;
		Class79_Sub1.method4870(-1, null, -1);
		EmissiveTriangle.method697(null, -1, -1);
		Class16_Sub2.method3582();
		MusicPatch.anInt5640++;
		for (@Pc(31) int local31 = 0; local31 < 32768; local31++) {
			@Pc(37) NPCEntity local37 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local31];
			if (local37 != null) {
				@Pc(43) byte local43 = local37.npcType.aByte99;
				if ((local43 & 0x1) != 0) {
					@Pc(51) int local51 = local37.getSize();
					@Pc(78) int local78;
					if ((local43 & 0x2) != 0 && local37.pathPointer == 0 && Math.random() * 1000.0D < 10.0D) {
						local78 = (int) Math.round(Math.random() * 10.0D - 5.0D);
						@Pc(86) int local86 = (int) Math.round(Math.random() * 10.0D - 5.0D);
						if (local78 != 0 || local86 != 0) {
							@Pc(101) int local101 = local37.movementBaseX[0] + local78;
							if (local101 < 0) {
								local101 = 0;
							} else if (GLBillowTextureData.anInt5666 - local51 - 1 < local101) {
								local101 = GLBillowTextureData.anInt5666 - local51 - 1;
							}
							@Pc(136) int local136 = local37.movementBaseY[0] + local86;
							if (local136 < 0) {
								local136 = 0;
							} else if (local136 > Static283.anInt5187 - local51 - 1) {
								local136 = Static283.anInt5187 - local51 - 1;
							}
							@Pc(183) int local183 = ClientScript.method49(PlayerVarDomain.aClass213Array1[local37.aByte82], local37.movementBaseX[0], local101, local51, local136, local51, 0, Class4_Sub7.anIntArray36, 0, true, ParticleBinaryTree.anIntArray498, local37.movementBaseY[0], local51, -1);
							if (local183 > 0) {
								if (local183 > 9) {
									local183 = 9;
								}
								for (@Pc(195) int local195 = 0; local195 < local183; local195++) {
									local37.movementBaseX[local195] = ParticleBinaryTree.anIntArray498[local183 - local195 - 1];
									local37.movementBaseY[local195] = Class4_Sub7.anIntArray36[local183 - local195 - 1];
									local37.movementQueueSpeed[local195] = 1;
								}
								local37.pathPointer = local183;
							}
						}
					}
					Static395.method5163(true, local37);
					local78 = GpuPointLight.method2424(local37);
					Class145_Sub2.method5628(local37, RuntimeException_Sub1.anInt4620, Class56.anInt1655, local78);
					FluType.method4959(local37);
				}
			}
		}
		if (CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0) {
			if (Class3_Sub3.anInt2386 == 2) {
				Class196.method4430();
			} else {
				Class4_Sub5.method514();
			}
			if (Class4_Sub23.anInt3197 >> 7 < 14 || GLBillowTextureData.anInt5666 - 14 <= Class4_Sub23.anInt3197 >> 7 || Static145.anInt2906 >> 7 < 14 || Static145.anInt2906 >> 7 >= Static283.anInt5187 - 14) {
				Class4_Sub12_Sub1.method1134();
			}
		}
		while (true) {
			@Pc(311) HookRequest local311;
			@Pc(316) com.jagex.game.runetek5.config.rsinterface.Component local316;
			@Pc(327) com.jagex.game.runetek5.config.rsinterface.Component local327;
			do {
				local311 = (HookRequest) ComponentSurface.aClass183_52.method4136();
				if (local311 == null) {
					while (true) {
						do {
							local311 = (HookRequest) TextureOpCurve.aClass183_3.method4136();
							if (local311 == null) {
								while (true) {
									do {
										local311 = (HookRequest) TextureOpMandelbrot.aClass183_35.method4136();
										if (local311 == null) {
											if (Static281.aClass247_12 != null) {
												Static1.method3();
											}
											if (Shadow.anInt5323 % 1500 == 0) {
												Class30.method744();
											}
											Class223.method4999();
											if (TextureOpKaleidoscope.aBoolean256 && Class168.method3962() - 60000L > FloType.aLong119) {
												Class190.method4216();
												return;
											}
											return;
										}
										local316 = local311.source;
										if (local316.anInt6865 < 0) {
											break;
										}
										local327 = ParticleBinaryTree.method5121(local316.anInt6850);
									} while (local327 == null || local327.slotChildren == null || local316.anInt6865 >= local327.slotChildren.length || local316 != local327.slotChildren[local316.anInt6865]);
									Static271.executeHookInner(local311);
								}
							}
							local316 = local311.source;
							if (local316.anInt6865 < 0) {
								break;
							}
							local327 = ParticleBinaryTree.method5121(local316.anInt6850);
						} while (local327 == null || local327.slotChildren == null || local327.slotChildren.length <= local316.anInt6865 || local316 != local327.slotChildren[local316.anInt6865]);
						Static271.executeHookInner(local311);
					}
				}
				local316 = local311.source;
				if (local316.anInt6865 < 0) {
					break;
				}
				local327 = ParticleBinaryTree.method5121(local316.anInt6850);
			} while (local327 == null || local327.slotChildren == null || local327.slotChildren.length <= local316.anInt6865 || local327.slotChildren[local316.anInt6865] != local316);
			Static271.executeHookInner(local311);
		}
	}

	private final MiddleMouseCamera middleMouseCamera = new MiddleMouseCamera();

	private int lastFogApplied = FogDistance.FOG_DEFAULT;


	@OriginalMember(owner = "client!client", name = "m", descriptor = "(I)V")
	private void method899() {
		if (VertexDeclarationComponent.anInt6667 == 1000) {
			return;
		}
		Shadow.anInt5323++;
		if (Shadow.anInt5323 % 1000 == 1) {
			@Pc(24) GregorianCalendar local24 = new GregorianCalendar();
			ParticleList.anInt4519 = local24.get(11) * 600 + local24.get(12) * 10 + local24.get(13) / 6;
			MusicPatch.aRandom1.setSeed((long) ParticleList.anInt4519);
		}
		if (Shadow.anInt5323 % 50 == 0) {
			Static449.anInt7316 = GlRenderList.anInt1097;
			WorldMapElement.anInt1020 = Class4_Sub6_Sub12.anInt2826;
			GlRenderList.anInt1097 = 0;
			Class4_Sub6_Sub12.anInt2826 = 0;
		}
		this.method912();
		if (Class64_Sub1.aClass159_1 != null) {
			Class64_Sub1.aClass159_1.method3819();
		}
		Class136.method3438();
		Static384.aClass244_1.method5487();
		MaterialType.aClass80_1.method2236();
		this.middleMouseCamera.update(MaterialType.aClass80_1);
		if (PlayerEntity.aClass14_1 != null) {
			// Ctrl (without Shift) + wheel zooms; Alt (without Ctrl) + wheel
			// changes fog distance; Ctrl+Shift+wheel and plain wheel stay on
			// the legacy scroll path (see CameraZoomController).
			@Pc(85) int wheelNotches = PlayerEntity.aClass14_1.consumeWheelRotation();
			PointLight.pendingWheelDelta = CameraZoomController.routeWheel(wheelNotches);
		}
		CameraZoom.smoothStep();
		if (FogDistance.smoothStep() != this.lastFogApplied) {
			this.lastFogApplied = FogDistance.getCurrentFog();
			FileOnDisk.anInt4431 = FogDistance.scaledTileRadius();
			if (MonoStereoData.aClass19_16 != null) {
				MonoStereoData.aClass19_16.refreshFog();
			}
		}

		if (MonoStereoData.aClass19_16 != null) {
			MonoStereoData.aClass19_16.tick((int) Class168.method3962());
		}
		EnvironmentContext.method4605();
		Class4_Sub6_Sub15.anInt3602 = 0;
		for (@Pc(106) Class30 local106 = Static384.aClass244_1.method5483(); local106 != null && Class4_Sub6_Sub15.anInt3602 < 128; local106 = Static384.aClass244_1.method5483()) {
			if (local106.method745() != 1) {
				@Pc(119) char local119 = local106.method749();
				if (!TextureOpMonochromeEdgeDetector.method5406() || local119 != '`' && local119 != '§') {
					Class16_Sub2.aClass30Array4[Class4_Sub6_Sub15.anInt3602] = local106;
					Class4_Sub6_Sub15.anInt3602++;
				} else if (LocalRequester.method5440()) {
					Static433.method5500();
				} else {
					Class4_Sub6_Sub25.method3806();
				}
			}
		}
		for (@Pc(168) Class4_Sub5 local168 = MaterialType.aClass80_1.method2232(); local168 != null; local168 = MaterialType.aClass80_1.method2232()) {
			@Pc(174) int local174 = local168.method515();
			if (local174 == -1) {
				Class4_Sub6_Sub31.aClass183_42.method4137(local168);
			} else if (Static105.method1981(local174)) {
				Component.aClass183_47.method4137(local168);
			}
			if (Component.aClass183_47.method4148() > 10) {
				Component.aClass183_47.method4136();
			}
		}
		if (LocalRequester.method5440()) {
			Class4_Sub6_Sub19.tickClientInput();
		}
		if (VertexDeclarationComponent.anInt6667 == 0) {
			this.method910();
			GlVboArrayBuffer.method3660();
		} else if (VertexDeclarationComponent.anInt6667 == 5) {
			this.method910();
			GlVboArrayBuffer.method3660();
		} else if (VertexDeclarationComponent.anInt6667 == 25 || VertexDeclarationComponent.anInt6667 == 28) {
			TextureType.method3275();
		}
		if (VertexDeclarationComponent.anInt6667 == 10) {
			this.method897();
			Class229.method5090();
			FileWorker.method3510();
			Class4_Sub6_Sub37.method5430();
		} else if (VertexDeclarationComponent.anInt6667 == 30) {
			Class174.method4042();
		} else if (VertexDeclarationComponent.anInt6667 == 40) {
			Class4_Sub6_Sub37.method5430();
			if (StringNode.anInt5302 != -3 && StringNode.anInt5302 != 2 && StringNode.anInt5302 != 15) {
				BasType.method3639();
			}
		}
		SeqTypeList.method1400(MonoStereoData.aClass19_16);
		Component.aClass183_47.method4136();
	}

	@OriginalMember(owner = "client!client", name = "init", descriptor = "()V")
	@Override
	public void init() {
		CameraPreferences.load();
		Static107.anInt2335 = Integer.parseInt(this.getParameter("worldid"));
		GlToolkit.aClass219_2 = Class190.method4222(Integer.parseInt(this.getParameter("modewhere")));
		if (!Class16_Sub4_Sub3.method3034(GlToolkit.aClass219_2) && Class222.aClass219_6 != GlToolkit.aClass219_2) {
			GlToolkit.aClass219_2 = Class222.aClass219_6;
		}
		Class95.aClass117_9 = Class173.method3983(Integer.parseInt(this.getParameter("modewhat")));
		if (Class95.aClass117_9 != Texture3DShaderGL.aClass117_1 && Class95.aClass117_9 != Class204.aClass117_6 && Class95.aClass117_9 != Js5ResourceRequest.aClass117_4) {
			Class95.aClass117_9 = Js5ResourceRequest.aClass117_4;
		}
		try {
			NativeBillboardFace.anInt3423 = Integer.parseInt(this.getParameter("lang"));
		} catch (@Pc(56) Exception local56) {
			NativeBillboardFace.anInt3423 = 0;
		}
		@Pc(62) String local62 = this.getParameter("objecttag");
		if (local62 != null && local62.equals("1")) {
			Class105.aBoolean607 = true;
		} else {
			Class105.aBoolean607 = false;
		}
		@Pc(78) String local78 = this.getParameter("js");
		if (local78 != null && local78.equals("1")) {
			Class70_Sub2.aBoolean591 = true;
		} else {
			Class70_Sub2.aBoolean591 = false;
		}
		@Pc(94) String local94 = this.getParameter("advert");
		if (local94 != null && local94.equals("1")) {
			Class183.aBoolean484 = true;
		} else {
			Class183.aBoolean484 = false;
		}
		@Pc(110) String local110 = this.getParameter("game");
		if (local110 != null && local110.equals("1")) {
			DynamicWall.aClass121_3 = FixedFunctionSpecularPass.WRITE_PING;
		} else {
			DynamicWall.aClass121_3 = TextureOpTexture.aClass121_1;
		}
		try {
			GlCubeTexture.anInt3259 = Integer.parseInt(this.getParameter("affid"));
		} catch (@Pc(129) Exception local129) {
			GlCubeTexture.anInt3259 = 0;
		}
		Class16_Sub2.aString45 = this.getParameter("quiturl");
		ReflectionCheck.aString13 = this.getParameter("settings");
		if (ReflectionCheck.aString13 == null) {
			ReflectionCheck.aString13 = "";
		}
		@Pc(147) String local147 = this.getParameter("country");
		if (local147 != null) {
			try {
				ModelNormalsGL.anInt4373 = Integer.parseInt(local147);
			} catch (@Pc(154) Exception local154) {
				ModelNormalsGL.anInt4373 = 0;
			}
		}
		StructType.anInt5940 = Integer.parseInt(this.getParameter("colourid"));
		if (StructType.anInt5940 < 0 || SpotAnimation.aColorArray3.length <= StructType.anInt5940) {
			StructType.anInt5940 = 0;
		}
		if (Integer.parseInt(this.getParameter("sitesettings_member")) == 1) {
			Model.aBoolean573 = true;
			Class4_Sub29.aBoolean423 = true;
		}
		ClientScript.aClient1 = this;
		if (TextureOpTexture.aClass121_1 == DynamicWall.aClass121_3) {
			Class210.anInt6121 = 765;
			HashTable.anInt3148 = 503;
		} else if (FixedFunctionSpecularPass.WRITE_PING == DynamicWall.aClass121_3) {
			HashTable.anInt3148 = 480;
			Class210.anInt6121 = 640;
		}
		this.method881(Class95.aClass117_9.method2865() + 32, HashTable.anInt3148, Class210.anInt6121);
	}

	@OriginalMember(owner = "client!client", name = "g", descriptor = "(I)V")
	@Override
	protected void method891() {
		try {
			this.method894();
		} catch (@Pc(13) OutOfMemoryError local13) {
			if (local13.getMessage() == null || !local13.getMessage().startsWith("native")) {
				throw local13;
			}
			ParticleList.method3551(0);
			Class64_Sub1.method1654(local13, local13.getMessage() + " (Recovered) " + this.method887());
		}
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(Z)V")
	@Override
	protected void method880() {
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "(II)V")
	private void method903(@OriginalArg(1) int arg0) {
		EntityNode.aClass11_14 = null;
		LocEntity.aClass199_2 = null;
		MECType.aClass255_2.errors++;
		MECType.aClass255_2.response = arg0;
		Static78.anInt1567 = 0;
	}

	@OriginalMember(owner = "client!client", name = "d", descriptor = "(I)V")
	@Override
	protected void maininit() {
		@Pc(10) Frame local10 = new Frame("Jagex");
		local10.pack();
		local10.dispose();
		TextureCombinerOp.method3938();
		Class70_Sub2.aClass254_3 = new LocalRequester(Class126.aClass66_4);
		MECType.aClass255_2 = new Js5WorkerThread();
		if (Class222.aClass219_6 != GlToolkit.aClass219_2) {
			ParticleBinaryTree.aByteArrayArray28 = new byte[50][];
		}
		TextureOpBrightness.aClass50_Sub1_1 = new Class50_Sub1(Class126.aClass66_4);
		if (GlToolkit.aClass219_2 == Class222.aClass219_6) {
			BufferedSocket.aString4 = this.getCodeBase().getHost();
			Static133.anInt2755 = 43594;
			ReflectiveRipplingWaterEffect.anInt154 = 43594; // 443
		} else if (Class16_Sub4_Sub3.method3034(GlToolkit.aClass219_2)) {
			BufferedSocket.aString4 = this.getCodeBase().getHost();
			Static133.anInt2755 = Static107.anInt2335 + 40000;
			ReflectiveRipplingWaterEffect.anInt154 = Static107.anInt2335 + 50000;
		} else if (GlToolkit.aClass219_2 == SoundBankCache.MODE_WHERE_WTQA) {
			Static133.anInt2755 = Static107.anInt2335 + 40000;
			BufferedSocket.aString4 = "127.0.0.1";
			ReflectiveRipplingWaterEffect.anInt154 = Static107.anInt2335 + 50000;
		}
		Class189.anInt5435 = Static133.anInt2755;
		Class191.aString54 = BufferedSocket.aString4;
		Class4_Sub6_Sub8.anInt1949 = ReflectiveRipplingWaterEffect.anInt154;
		Class189.anInt5436 = Static133.anInt2755;
		if (SignLink.anInt1987 == 3) {
			MapSpriteType.anInt1028 = Static107.anInt2335;
		}
		GlVboArrayBuffer.aShortArray151 = OggOtherStream.aShortArray196 = Static78.aShortArray46 = Static166.aShortArray97 = new short[256];
		if (FixedFunctionSpecularPass.WRITE_PING == DynamicWall.aClass121_3) {
			Class4_Sub1_Sub2.aShortArrayArray1 = Static78.aShortArrayArray4;
			Class177.aShortArray179 = EntityNode.aShortArray251;
			Texture3DShaderGL.anInt1369 = 16777215;
			FixedFunctionSpecularPass.anInt5521 = 0;
			Country.aBoolean692 = true;
			Country.aShortArray252 = BreakingWaterEffect.aShortArray43;
			KeyRecordEntry.aShortArrayArray2 = Js5WorkerRequestMessage.aShortArrayArray8;
		} else {
			KeyRecordEntry.aShortArrayArray2 = BreakingWaterEffect.aShortArrayArray3;
			Class4_Sub1_Sub2.aShortArrayArray1 = RectangularTextureGL.aShortArrayArray6;
			Country.aShortArray252 = GlGround.aShortArray175;
			Class177.aShortArray179 = ItemType.aShortArray210;
		}
		Skybox.anInt6723 = Class189.anInt5435;
		Static384.aClass244_1 = Js5TextureSource.method2398(Class164.aCanvas5);
		MaterialType.aClass80_1 = Model.method4882(Class164.aCanvas5);
		PlayerEntity.aClass14_1 = StaticElements.method4424();
		if (PlayerEntity.aClass14_1 != null) {
			PlayerEntity.aClass14_1.attachTo(Class164.aCanvas5);
		}
		Class4_Sub6_Sub7.anInt1932 = SignLink.anInt1987;
		try {
			if (Class126.aClass66_4.aClass207_3 != null) {
				Class60.aClass139_1 = new FileOnDisk(Class126.aClass66_4.aClass207_3, 5200, 0);
				for (@Pc(169) int local169 = 0; local169 < 30; local169++) {
					OpenGLTextureCache.aClass139Array1[local169] = new FileOnDisk(Class126.aClass66_4.aClass207Array1[local169], 6000, 0);
				}
				Class4_Sub6_Sub37.aClass139_5 = new FileOnDisk(Class126.aClass66_4.cacheIndex255, 6000, 0);
				Class136.aClass222_2 = new Class222(255, Class60.aClass139_1, Class4_Sub6_Sub37.aClass139_5, 500000);
				JavaMatrix.aClass139_4 = new FileOnDisk(Class126.aClass66_4.aClass207_2, 24, 0);
				Class126.aClass66_4.cacheIndex255 = null;
				Class126.aClass66_4.aClass207_2 = null;
				Class126.aClass66_4.aClass207_3 = null;
				Class126.aClass66_4.aClass207Array1 = null;
			}
		} catch (@Pc(227) IOException local227) {
			JavaMatrix.aClass139_4 = null;
			Class60.aClass139_1 = null;
			Class136.aClass222_2 = null;
			Class4_Sub6_Sub37.aClass139_5 = null;
		}
		if (Class222.aClass219_6 != GlToolkit.aClass219_2) {
			MusicPatch.aBoolean507 = true;
		}
		TextureOpPerlinNoise.aString30 = (TextureOpTexture.aClass121_1 == DynamicWall.aClass121_3 ? SpotAnimationType.aClass83_104 : Class95_Sub3_Sub1.aClass83_129).method2267(NativeBillboardFace.anInt3423);
	}

	@OriginalMember(owner = "client!client", name = "a", descriptor = "([BB)V")
	private void method908(@OriginalArg(0) byte[] arg0) {
		@Pc(10) Packet local10 = new Packet(arg0);
		while (true) {
			@Pc(18) int local18;
			@Pc(42) int local42;
			@Pc(37) int local37;
			label45: do {
				while (true) {
					while (true) {
						local18 = local10.method2490();
						if (local18 == 0) {
							return;
						}
						if (local18 == 1) {
							@Pc(106) int[] local106 = Class4_Sub6_Sub33.anIntArray491 = new int[6];
							local106[0] = local10.method2536();
							local106[1] = local10.method2536();
							local106[2] = local10.method2536();
							local106[3] = local10.method2536();
							local106[4] = local10.method2536();
							local106[5] = local10.method2536();
						} else {
							if (local18 != 4) {
								continue label45;
							}
							local37 = local10.method2490();
							Class95_Sub2.anIntArray490 = new int[local37];
							for (local42 = 0; local42 < local37; local42++) {
								Class95_Sub2.anIntArray490[local42] = local10.method2536();
								if (Class95_Sub2.anIntArray490[local42] == 65535) {
									Class95_Sub2.anIntArray490[local42] = -1;
								}
							}
						}
					}
				}
			} while (local18 != 5);
			local37 = local10.method2490();
			GlModel.anIntArray217 = new int[local37];
			for (local42 = 0; local42 < local37; local42++) {
				GlModel.anIntArray217[local42] = local10.method2536();
				if (GlModel.anIntArray217[local42] == 65535) {
					GlModel.anIntArray217[local42] = -1;
				}
			}
		}
	}

	@OriginalMember(owner = "client!client", name = "p", descriptor = "(I)V")
	private void method910() {
		@Pc(10) int local10;
		if (!TextureOpBrightness.aClass50_Sub1_1.aBoolean297) {
			for (local10 = 0; local10 < Class4_Sub6_Sub15.anInt3602; local10++) {
				if (Class16_Sub2.aClass30Array4[local10].method749() == 's' || Class16_Sub2.aClass30Array4[local10].method749() == 'S') {
					TextureOpBrightness.aClass50_Sub1_1.aBoolean297 = true;
					break;
				}
			}
		}
		@Pc(57) int local57;
		if (Static1.anInt5 == 0) {
			@Pc(47) Runtime local47 = Runtime.getRuntime();
			local57 = (int) ((local47.totalMemory() - local47.freeMemory()) / 1024L);
			@Pc(60) long local60 = Class168.method3962();
			if (Class16_Sub1_Sub1.aLong17 == 0L) {
				Class16_Sub1_Sub1.aLong17 = local60;
			}
			if (local57 > 16384 && local60 - Class16_Sub1_Sub1.aLong17 < 5000L) {
				if (local60 - StaticElements.aLong190 > 1000L) {
					System.gc();
					StaticElements.aLong190 = local60;
				}
				Shadow.aString53 = Static107.aClass83_40.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 5;
			} else {
				Shadow.aString53 = Shadow.aClass83_111.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 10;
				GlFramebuffer.anInt4590 = 5;
			}
		} else if (Static1.anInt5 == 10) {
			for (local10 = 0; local10 < 4; local10++) {
				PlayerVarDomain.aClass213Array1[local10] = Class267.method5622(Static283.anInt5187, GLBillowTextureData.anInt5666);
			}
			Shadow.aString53 = Class4_Sub1_Sub11.aClass83_61.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 10;
			Static1.anInt5 = 20;
		} else if (Static1.anInt5 == 20) {
			if (Class64_Sub1.aClass159_1 == null) {
				Class64_Sub1.aClass159_1 = new RequestManager(MECType.aClass255_2, Class70_Sub2.aClass254_3);
			}
			if (Class64_Sub1.aClass159_1.method3814()) {
				Static395.aClass76_92 = RequestManager.method3820(false, 0, true);
				Class193.aClass76_69 = RequestManager.method3820(false, 1, true);
				Isaac.aClass76_20 = RequestManager.method3820(false, 2, true);
				TileLighting.aClass76_50 = RequestManager.method3820(false, 3, true);
				Class4_Sub6_Sub4.aClass76_19 = RequestManager.method3820(false, 4, true);
				Class4_Sub10.aClass76_27 = RequestManager.method3820(true, 5, true);
				TextureCombinerOp.aClass76_54 = RequestManager.method3820(true, 6, false);
				CollisionMap.aClass76_82 = RequestManager.method3820(false, 7, true);
				GlGround.aClass76_60 = RequestManager.method3820(false, 8, true);
				TextureOpTexture.aClass76_44 = RequestManager.method3820(false, 9, true);
				Class60.aClass76_23 = RequestManager.method3820(false, 10, true);
				Class170.aClass76_55 = RequestManager.method3820(false, 11, true);
				TextureOpBlend.aClass76_45 = RequestManager.method3820(false, 12, true);
				Static209.aClass76_48 = RequestManager.method3820(false, 13, true);
				Static312.aClass76_66 = RequestManager.method3820(false, 14, false);
				Class208.aClass76_79 = RequestManager.method3820(false, 15, true);
				Class4_Sub43.aClass76_99 = RequestManager.method3820(false, 16, true);
				Class36.aClass76_29 = RequestManager.method3820(false, 17, true);
				ParamTypeList.aClass76_87 = RequestManager.method3820(false, 18, true);
				Static391.aClass76_91 = RequestManager.method3820(false, 19, true);
				Class230.aClass76_90 = RequestManager.method3820(false, 20, true);
				WorldMapElement.aClass76_16 = RequestManager.method3820(false, 21, true);
				SpotAnimation.aClass76_17 = RequestManager.method3820(false, 22, true);
				Class4_Sub23.aClass76_39 = RequestManager.method3820(true, 23, true);
				Class48.aClass76_70 = RequestManager.method3820(false, 24, true);
				ByteArrayWrapper.aClass76_11 = RequestManager.method3820(false, 25, true);
				Shadow.aClass76_61 = RequestManager.method3820(true, 26, true);
				Class16_Sub1_Sub6.aClass76_98 = RequestManager.method3820(false, 27, true);
				Class6_Sub2.aClass76_2 = RequestManager.method3820(true, 28, true);
				Static366.aClass76_83 = RequestManager.method3820(false, 29, true);
				Static366.aClass76_100 = RequestManager.method3820(false, 30, true);
				Shadow.aString53 = Class191.aClass83_117.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 15;
				Static1.anInt5 = 30;
			} else {
				Shadow.aString53 = Class4_Sub7.aClass83_18.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 12;
			}
		} else if (Static1.anInt5 == 30) {
			local10 = 0;
			for (local57 = 0; local57 < 31; local57++) {
				local10 += Class16_Sub4_Sub2.aClass143_Sub1Array1[local57].method3525() * BasType.anIntArray307[local57] / 100;
			}
			if (local10 == 100) {
				Shadow.aString53 = Class16_Sub5.aClass83_135.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 20;
				Class4_Sub6_Sub12.method2373(GlGround.aClass76_60);
				Class4_Sub1_Sub6_Sub2.method3346(GlGround.aClass76_60);
				Static1.anInt5 = 40;
			} else {
				if (local10 != 0) {
					Shadow.aString53 = Class4_Sub6_Sub7.aClass83_37.method2267(NativeBillboardFace.anInt3423) + local10 + "%";
				}
				GlFramebuffer.anInt4590 = 20;
			}
		} else if (Static1.anInt5 == 40) {
			if (Class6_Sub2.aClass76_2.method2116()) {
				this.method908(Class6_Sub2.aClass76_2.method2122(1));
				Shadow.aString53 = Class244.aClass83_145.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 25;
				Static1.anInt5 = 50;
			} else {
				Shadow.aString53 = EasedAngle.aClass83_27.method2267(NativeBillboardFace.anInt3423) + Class6_Sub2.aClass76_2.method2112() + "%";
				GlFramebuffer.anInt4590 = 25;
			}
		} else if (Static1.anInt5 == 50) {
			Class9_Sub3.method1354();
			Shadow.aString53 = Class186.aClass83_114.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 30;
			Static1.anInt5 = 60;
		} else if (Static1.anInt5 == 60) {
			local10 = Terrain.method5632(Static209.aClass76_48, GlGround.aClass76_60);
			local57 = PointEntity.method3302();
			if (local10 < local57) {
				Shadow.aString53 = CubeMapSpecularEffect.aClass83_67.method2267(NativeBillboardFace.anInt3423) + local10 * 100 / local57 + "%";
				GlFramebuffer.anInt4590 = 35;
			} else {
				Shadow.aString53 = Ground.aClass83_151.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 70;
				GlFramebuffer.anInt4590 = 35;
			}
		} else if (Static1.anInt5 == 70) {
			local10 = Class4_Sub6_Sub8.method1701(GlGround.aClass76_60);
			local57 = Static395.method5165();
			if (local57 > local10) {
				Shadow.aString53 = Static363.aClass83_123.method2267(NativeBillboardFace.anInt3423) + local10 * 100 / local57 + "%";
				GlFramebuffer.anInt4590 = 40;
			} else {
				Shadow.aString53 = Static363.aClass83_122.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 80;
				GlFramebuffer.anInt4590 = 40;
			}
		} else if (Static1.anInt5 == 80) {
			if (Shadow.aClass76_61.method2116()) {
				MidiPcmStream.anInterface7_3 = new Js5TextureSource(Shadow.aClass76_61, TextureOpTexture.aClass76_44, GlGround.aClass76_60);
				Shadow.aString53 = SimpleMouseLog.aClass83_22.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 45;
				Static1.anInt5 = 90;
			} else {
				Shadow.aString53 = GlModel.aClass83_71.method2267(NativeBillboardFace.anInt3423) + Shadow.aClass76_61.method2112() + "%";
				GlFramebuffer.anInt4590 = 45;
			}
		} else if (Static1.anInt5 == 90) {
			Shadow.aString53 = Class149.aClass83_95.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 50;
			Static1.anInt5 = 95;
		} else if (Static1.anInt5 == 95) {
			if (TextureOpBrightness.aClass50_Sub1_1.aBoolean297) {
				TextureOpBrightness.aClass50_Sub1_1.anInt3442 = 0;
				TextureOpBrightness.aClass50_Sub1_1.anInt3447 = 1;
				TextureOpBrightness.aClass50_Sub1_1.anInt3440 = 0;
				TextureOpBrightness.aClass50_Sub1_1.anInt3445 = 0;
				TextureOpBrightness.aClass50_Sub1_1.anInt3434 = 0;
			}
			TextureOpBrightness.aClass50_Sub1_1.aBoolean297 = true;
			TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
			LocTypeList.method5561(false, TextureOpBrightness.aClass50_Sub1_1.anInt3445);
			Shadow.aString53 = TextureOpTile.aClass83_126.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 55;
			Static1.anInt5 = 100;
		} else if (Static1.anInt5 == 100) {
			EmissiveTriangle.method696(MonoStereoData.aClass19_16, GlGround.aClass76_60, Static209.aClass76_48);
			Shadow.aString53 = StringNode.aClass83_109.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 60;
			Huffman.method2932(5);
			Static1.anInt5 = 110;
		} else if (Static1.anInt5 == 110) {
			Isaac.aClass76_20.method2116();
			local10 = Isaac.aClass76_20.method2112();
			Class4_Sub43.aClass76_99.method2116();
			local10 += Class4_Sub43.aClass76_99.method2112();
			Class36.aClass76_29.method2116();
			local10 += Class36.aClass76_29.method2112();
			ParamTypeList.aClass76_87.method2116();
			local10 += ParamTypeList.aClass76_87.method2112();
			Static391.aClass76_91.method2116();
			local10 += Static391.aClass76_91.method2112();
			Class230.aClass76_90.method2116();
			local10 += Class230.aClass76_90.method2112();
			WorldMapElement.aClass76_16.method2116();
			local10 += WorldMapElement.aClass76_16.method2112();
			SpotAnimation.aClass76_17.method2116();
			local10 += SpotAnimation.aClass76_17.method2112();
			Class48.aClass76_70.method2116();
			local10 += Class48.aClass76_70.method2112();
			ByteArrayWrapper.aClass76_11.method2116();
			local10 += ByteArrayWrapper.aClass76_11.method2112();
			Class16_Sub1_Sub6.aClass76_98.method2116();
			local10 += Class16_Sub1_Sub6.aClass76_98.method2112();
			Static366.aClass76_83.method2116();
			local10 += Static366.aClass76_83.method2112();
			if (local10 < 1200) {
				Shadow.aString53 = Class16_Sub1_Sub1.aClass83_13.method2267(NativeBillboardFace.anInt3423) + local10 / 12 + "%";
				GlFramebuffer.anInt4590 = 65;
			} else {
				Class4_Sub1_Sub11.aClass180_1 = new Class180(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Class133.aClass249_1 = new Class249(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Js5ResourceProvider.aClass172_2 = new CursorTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20, GlGround.aClass76_60);
				PingRequest.aClass158_1 = new EnumTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Class36.aClass76_29);
				ByteArrayWrapper.aClass124_2 = new FloTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Class4_Sub6_Sub22.aClass192_2 = new FluTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				MaterialType.aClass109_2 = new IDKTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20, CollisionMap.aClass76_82);
				LRUCache.aClass75_1 = new Class75(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				NPCTypeList.aClass204_1 = new Class204(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Static267.aClass262_2 = new LocTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, true, Class4_Sub43.aClass76_99, CollisionMap.aClass76_82);
				Class4_Sub6_Sub31.aClass182_4 = new MECTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20, GlGround.aClass76_60);
				Class4_Sub14.aClass265_2 = new MapSpriteTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20, GlGround.aClass76_60);
				Class196.aClass240_1 = new NPCTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, true, ParamTypeList.aClass76_87, CollisionMap.aClass76_82);
				Terrain.aClass206_3 = new ObjTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, true, Class4_Sub1_Sub11.aClass180_1, Static391.aClass76_91, CollisionMap.aClass76_82);
				LocalRequester.aClass208_1 = new Class208(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				SceneLocEntity.aClass55_1 = new SeqTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Class230.aClass76_90, Static395.aClass76_92, Class193.aClass76_69);
				StringNode.aClass217_1 = new Class217(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				CacheableNodeByteArray.aClass226_1 = new ParamTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Js5MasterIndex.aClass194_2 = new SpotAnimationTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, WorldMapElement.aClass76_16, CollisionMap.aClass76_82);
				Class4_Sub6_Sub26.aClass72_1 = new StructTypeList(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Class4_Sub25.aClass197_1 = new Js5GroupLoader(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				Ground.aClass93_4 = new Class93(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				KeyRecordEntry.aClass26_1 = new Class26(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, SpotAnimation.aClass76_17);
				Js5ResourceRequest.aClass223_1 = new Class223(DynamicWall.aClass121_3, NativeBillboardFace.anInt3423, Isaac.aClass76_20);
				NPCType.method5586(TileLighting.aClass76_50, Static209.aClass76_48, CollisionMap.aClass76_82, GlGround.aClass76_60);
				Class4_Sub18.method2034(742);
				Class4_Sub18.method2034(882);
				ParticleEffector.method2730(Static366.aClass76_83);
				GlShadowMap.aClass62_1 = new Class62(NativeBillboardFace.anInt3423, Class48.aClass76_70, ByteArrayWrapper.aClass76_11);
				Class145_Sub1.aClass81_2 = new QuickchatMessageTypeList(NativeBillboardFace.anInt3423, Class48.aClass76_70, ByteArrayWrapper.aClass76_11, new Class130());
				Shadow.aString53 = GlShadow.aClass83_52.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 65;
				Class28.method673();
				Static267.aClass262_2.method5562(!TextureOpBrightness.aClass50_Sub1_1.method2861(Packet.anInt2973));
				Class4_Sub6_Sub24.aClass114_1 = new PlayerVarDomain();
				Class16_Sub4_Sub2.method2149();
				EnvironmentSampler.method3227(Class16_Sub1_Sub6.aClass76_98);
				Class164.method3916(CollisionMap.aClass76_82, MidiPcmStream.anInterface7_3);
				Static1.anInt5 = 120;
			}
		} else if (Static1.anInt5 == 120) {
			local10 = Class95_Sub2.method5014(GlGround.aClass76_60);
			local57 = Class78_Sub1.method3176();
			if (local10 < local57) {
				Shadow.aString53 = EntityNode.aClass83_153.method2267(NativeBillboardFace.anInt3423) + local10 * 100 / local57 + "%";
				GlFramebuffer.anInt4590 = 70;
			} else {
				Class4_Sub6_Sub8.method1700(GlGround.aClass76_60, MonoStereoData.aClass19_16);
				Class193.method4387(PingWorker.aClass57Array18);
				Shadow.aString53 = Static391.aClass83_132.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 130;
				GlFramebuffer.anInt4590 = 70;
			}
		} else if (Static1.anInt5 == 130) {
			if (Class60.aClass76_23.method2123("", "huffman")) {
				@Pc(1252) Huffman local1252 = new Huffman(Class60.aClass76_23.method2109("huffman", ""));
				Class4_Sub6_Sub16.method3074(local1252);
				Shadow.aString53 = ParticleNode.aClass83_88.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 75;
				Static1.anInt5 = 140;
			} else {
				Shadow.aString53 = GlRenderList.aClass83_29.method2267(NativeBillboardFace.anInt3423) + "0%";
				GlFramebuffer.anInt4590 = 75;
			}
		} else if (Static1.anInt5 == 140) {
			if (TileLighting.aClass76_50.method2116()) {
				Shadow.aString53 = Class225.aClass83_130.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 150;
				GlFramebuffer.anInt4590 = 80;
			} else {
				Shadow.aString53 = Class222.aClass83_127.method2267(NativeBillboardFace.anInt3423) + TileLighting.aClass76_50.method2112() + "%";
				GlFramebuffer.anInt4590 = 80;
			}
		} else if (Static1.anInt5 == 150) {
			if (TextureOpBlend.aClass76_45.method2116()) {
				Shadow.aString53 = NamedFileReference.aClass83_138.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 160;
				GlFramebuffer.anInt4590 = 82;
			} else {
				Shadow.aString53 = Class64_Sub1.aClass83_35.method2267(NativeBillboardFace.anInt3423) + TextureOpBlend.aClass76_45.method2112() + "%";
				GlFramebuffer.anInt4590 = 82;
			}
		} else if (Static1.anInt5 == 160) {
			if (Static209.aClass76_48.method2116()) {
				Shadow.aString53 = ParticleBinaryTree.aClass83_134.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 170;
				GlFramebuffer.anInt4590 = 85;
			} else {
				Shadow.aString53 = ParticleBinaryTree.aClass83_134.method2267(NativeBillboardFace.anInt3423) + Static209.aClass76_48.method2112() + "%";
				GlFramebuffer.anInt4590 = 85;
			}
		} else if (Static1.anInt5 == 170) {
			if (Class4_Sub23.aClass76_39.method2103("details")) {
				Static148.method4514(Class4_Sub23.aClass76_39, ByteArrayWrapper.aClass124_2, Class4_Sub6_Sub22.aClass192_2, Static267.aClass262_2, Class4_Sub6_Sub31.aClass182_4, Class4_Sub14.aClass265_2, Class4_Sub6_Sub24.aClass114_1);
				Shadow.aString53 = MECType.aClass83_103.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 180;
				GlFramebuffer.anInt4590 = 89;
			} else {
				Shadow.aString53 = QuickChatCategoryType.aClass83_106.method2267(NativeBillboardFace.anInt3423) + Class4_Sub23.aClass76_39.method2113("details") + "%";
				GlFramebuffer.anInt4590 = 87;
			}
		} else if (Static1.anInt5 == 180) {
			local10 = Class4_Sub18.method2035();
			if (local10 == -1) {
				Shadow.aString53 = Class4_Sub6_Sub26.aClass83_108.method2267(NativeBillboardFace.anInt3423);
				GlFramebuffer.anInt4590 = 90;
			} else if (local10 == 7 || local10 == 9) {
				this.raiseErrorScreen("worldlistfull");
				Huffman.method2932(1000);
			} else if (Class64_Sub1.aBoolean176) {
				Shadow.aString53 = Class16_Sub4_Sub2.aClass83_44.method2267(NativeBillboardFace.anInt3423);
				Static1.anInt5 = 190;
				GlFramebuffer.anInt4590 = 90;
			} else {
				this.raiseErrorScreen("worldlistio_" + local10);
				Huffman.method2932(1000);
			}
		} else if (Static1.anInt5 == 190) {
			RequestManager.aStringArray20 = new String[Class4_Sub25.aClass197_1.anInt5738];
			Class16_Sub1_Sub1.aBooleanArray3 = new boolean[Ground.aClass93_4.anInt2890];
			ParticleEffector.WALL_ROTATION_FLAGS = new int[Ground.aClass93_4.anInt2890];
			for (local10 = 0; local10 < Ground.aClass93_4.anInt2890; local10++) {
				if (Ground.aClass93_4.method2430(local10).anInt6668 == 0) {
					Class16_Sub1_Sub1.aBooleanArray3[local10] = true;
					SpotAnimationType.anInt4998++;
				}
				ParticleEffector.WALL_ROTATION_FLAGS[local10] = -1;
			}
			PcmPlayer.method2068();
			ColorImageCache.anInt4755 = TileLighting.aClass76_50.method2099("loginscreen");
			Class4_Sub10.aClass76_27.method2128(false);
			TextureCombinerOp.aClass76_54.method2128(true);
			GlGround.aClass76_60.method2128(true);
			Static209.aClass76_48.method2128(true);
			Class60.aClass76_23.method2128(true);
			TileLighting.aClass76_50.method2128(true);
			Isaac.aClass76_20.loadType = 2;
			ParticleType.aBoolean10 = true;
			Class36.aClass76_29.loadType = 2;
			Class4_Sub43.aClass76_99.loadType = 2;
			ParamTypeList.aClass76_87.loadType = 2;
			Static391.aClass76_91.loadType = 2;
			Class230.aClass76_90.loadType = 2;
			WorldMapElement.aClass76_16.loadType = 2;
			Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
			Shadow.aString53 = BzipDecompressor.aClass83_30.method2267(NativeBillboardFace.anInt3423);
			GlFramebuffer.anInt4590 = 95;
			Static1.anInt5 = 200;
		} else if (Static1.anInt5 == 200) {
			Class95_Sub3_Sub1.method4994(true);
		}
	}

	@OriginalMember(owner = "client!client", name = "f", descriptor = "(B)V")
	private void method912() {
		@Pc(7) boolean local7 = MECType.aClass255_2.loop();
		if (!local7) {
			this.method893();
		}
	}

@OriginalMember(owner = "client!client", name = "fb", descriptor = "Lclient!sp;")
	public static final ModeWhere aClass219_1 = new ModeWhere("WTRC", 1);
@OriginalMember(owner = "client!client", name = "mb", descriptor = "Lclient!pn;")
	public static final Class186 aClass186_4 = new Class186("", 11);
@OriginalMember(owner = "client!client", name = "a", descriptor = "(Lclient!uu;)Z")
	public static boolean isComponentHidden(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component component) {
		if (Class249.aBoolean626) {
			if (method900(component).anInt5105 != 0) {
				return false;
			}
			if (component.anInt6840 == 0) {
				return false;
			}
		}
		return component.hidden;
	}
@OriginalMember(owner = "client!client", name = "k", descriptor = "(I)V")
	public static void positionEntitiesOnPlane(@OriginalArg(0) int level) {
		@Pc(1) int local1 = Class4_Sub7.anInt606;
		@Pc(3) int[] local3 = Static99.anIntArray157;
		@Pc(11) int local11 = Class48.aBoolean508 ? local1 : local1 + Class10.anInt163;
		for (@Pc(13) int local13 = 0; local13 < local11; local13++) {
			@Pc(23) PathingEntity local23;
			if (local13 < local1) {
				local23 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local3[local13]];
			} else {
				local23 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local13 - local1]];
			}
			if (local23.aByte82 == level) {
				local23.overheadStackCount = 0;
				if (local23.worldPriority < 0) {
					local23.isHidden = false;
				} else {
					@Pc(50) int local50 = local23.getSize();
					if ((local50 & 0x1) == 0) {
						if ((local23.anInt6893 & 0x7F) != 0 || (local23.anInt6892 & 0x7F) != 0) {
							local23.isHidden = false;
							continue;
						}
					} else if ((local23.anInt6893 & 0x7F) != 64 || (local23.anInt6892 & 0x7F) != 64) {
						local23.isHidden = false;
						continue;
					}
					@Pc(92) int local92;
					@Pc(97) int local97;
					@Pc(122) int local122;
					if (local50 == 1) {
						local92 = local23.anInt6893 >> 7;
						local97 = local23.anInt6892 >> 7;
						if (local23.worldPriority != EasedAngle.anIntArrayArray8[local92][local97]) {
							local23.isHidden = true;
							continue;
						}
						if (Class4_Sub9.anIntArrayArray7[local92][local97] > 1) {
							local122 = Class4_Sub9.anIntArrayArray7[local92][local97]--;
							local23.isHidden = true;
							continue;
						}
					} else {
						local92 = (local50 - 1) * 64 + 60;
						local97 = local23.anInt6893 - local92 >> 7;
						@Pc(151) int local151 = local23.anInt6892 - local92 >> 7;
						@Pc(158) int local158 = local23.anInt6893 + local92 >> 7;
						@Pc(165) int local165 = local23.anInt6892 + local92 >> 7;
						if (!Static298.method4083(local165, local151, local158, local23.worldPriority, local97)) {
							for (@Pc(176) int local176 = local97; local176 <= local158; local176++) {
								for (@Pc(179) int local179 = local151; local179 <= local165; local179++) {
									if (local23.worldPriority == EasedAngle.anIntArrayArray8[local176][local179]) {
										local122 = Class4_Sub9.anIntArrayArray7[local176][local179]--;
									}
								}
							}
							local23.isHidden = true;
							continue;
						}
					}
					if (local23 instanceof PlayerEntity && local23.aClass4_Sub23_3 != null && Shadow.anInt5323 >= local23.aClass4_Sub23_3.anInt3191 && Shadow.anInt5323 < local23.aClass4_Sub23_3.anInt3202) {
						((PlayerEntity) local23).shouldSuppressMovingAnimation = false;
					}
					local23.isHidden = false;
					local23.anInt6889 = BufferedSocket.method135(local23.anInt6893, local23.aByte82, local23.anInt6892);
					Class193.method4385(local23, true);
				}
			}
		}
	}
@OriginalMember(owner = "client!client", name = "b", descriptor = "(IB)I")
	public static int method898(@OriginalArg(0) int arg0) {
		if (arg0 < 96) {
			return 0;
		} else if (arg0 < 128) {
			return 2;
		} else {
			return 3;
		}
	}
@OriginalMember(owner = "client!client", name = "b", descriptor = "(Lclient!uu;)Lclient!ns;")
	public static GlRectangleTexture method900(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		@Pc(13) GlRectangleTexture local13 = (GlRectangleTexture) Static211.aClass102_18.method2700(((long) arg0.anInt6809 << 32) + (long) arg0.anInt6865);
		return local13 == null ? arg0.aClass4_Sub33_2 : local13;
	}
@OriginalMember(owner = "client!client", name = "a", descriptor = "([Lclient!uu;IIIIIIIII)V")
	public static void method901(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component[] arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) int arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9) {
		for (@Pc(1) int local1 = 0; local1 < arg0.length; local1++) {
			@Pc(6) com.jagex.game.runetek5.config.rsinterface.Component local6 = arg0[local1];
			if (local6 != null && local6.anInt6850 == arg1) {
				@Pc(17) int local17 = local6.anInt6836 + arg6;
				@Pc(22) int local22 = local6.anInt6859 + arg7;
				@Pc(28) int local28;
				@Pc(30) int local30;
				@Pc(32) int local32;
				@Pc(34) int local34;
				if (local6.anInt6840 == 2) {
					local28 = arg2;
					local30 = arg3;
					local32 = arg4;
					local34 = arg5;
				} else {
					@Pc(40) int local40 = local17 + local6.anInt6833;
					@Pc(45) int local45 = local22 + local6.anInt6805;
					if (local6.anInt6840 == 9) {
						local40++;
						local45++;
					}
					local28 = local17 > arg2 ? local17 : arg2;
					local30 = local22 > arg3 ? local22 : arg3;
					local32 = local40 < arg4 ? local40 : arg4;
					local34 = local45 < arg5 ? local45 : arg5;
				}
				if (local6.anInt6840 == 0 || local6.aBoolean612 || method900(local6).anInt5105 != 0 || local6 == Static57.aClass247_1 || local6.anInt6842 == Class16_Sub4.anInt3645) {
					if (!isComponentHidden(local6)) {
						if (local6 == Static281.aClass247_12) {
							Class79_Sub1.aBoolean570 = true;
							GlFramebuffer.totalTransitionDuration = local17;
							Class95_Sub3_Sub1.anInt6394 = local22;
						}
						if (local6.aBoolean624 || local28 < local32 && local30 < local34) {
							if (local6.aBoolean622 && arg8 >= local28 && arg9 >= local30 && arg8 < local32 && arg9 < local34) {
								for (@Pc(148) HookRequest local148 = (HookRequest) TextureOpMandelbrot.aClass183_35.method4140(); local148 != null; local148 = (HookRequest) TextureOpMandelbrot.aClass183_35.method4144()) {
									if (local148.hasMousePosition) {
										local148.method5684();
										local148.source.aBoolean611 = false;
									}
								}
								if (Class4_Sub1_Sub17.anInt5487 == 0) {
									Static281.aClass247_12 = null;
									Static57.aClass247_1 = null;
								}
								SpotAnimation.anInt1367 = 0;
								TextureOpMonochromeEdgeDetector.aBoolean653 = false;
								AbstractFontRenderer.aBoolean588 = false;
								if (!Class4_Sub6_Sub22.aBoolean411) {
									OpenGl2DImageTexture.method4198();
								}
							}
							@Pc(204) boolean local204;
							if (MaterialType.aClass80_1.method2234() >= local28 && MaterialType.aClass80_1.getDefaultValue() >= local30 && MaterialType.aClass80_1.method2234() < local32 && MaterialType.aClass80_1.getDefaultValue() < local34) {
								local204 = true;
							} else {
								local204 = false;
							}
							if (!AbstractArbBufferGL.aBoolean599 && local204) {
								if (local6.anInt6789 >= 0) {
									HashTable.anInt3147 = local6.anInt6789;
								} else if (local6.aBoolean622) {
									HashTable.anInt3147 = -1;
								}
							}
							if (!Class4_Sub6_Sub22.aBoolean411 && arg8 >= local28 && arg9 >= local30 && arg8 < local32 && arg9 < local34) {
								Class95_Sub2.method5013(local6, arg8 - local17, arg9 - local22);
							}
							@Pc(248) boolean local248 = false;
							if (MaterialType.aClass80_1.method2235() && local204) {
								local248 = true;
							}
							@Pc(258) boolean local258 = false;
							@Pc(263) Class4_Sub5 local263 = (Class4_Sub5) Component.aClass183_47.method4140();
							if (local263 != null && local263.method515() == 0 && local263.method518() >= local28 && local263.method519() >= local30 && local263.method518() < local32 && local263.method519() < local34) {
								local258 = true;
							}
							@Pc(296) int local296;
							@Pc(430) int local430;
							if (local6.keyTriggeringAction != null) {
								for (local296 = 0; local296 < local6.keyTriggeringAction.length; local296++) {
									if (Static384.aClass244_1.method5489(local6.keyTriggeringAction[local296])) {
										if (local6.cycles == null || Shadow.anInt5323 >= local6.cycles[local296]) {
											@Pc(328) byte local328 = local6.keyTriggerParams[local296];
											if (local328 == 0 || ((local328 & 0x8) == 0 || !Static384.aClass244_1.method5489(86) && !Static384.aClass244_1.method5489(82) && !Static384.aClass244_1.method5489(81)) && ((local328 & 0x2) == 0 || Static384.aClass244_1.method5489(86)) && ((local328 & 0x1) == 0 || Static384.aClass244_1.method5489(82)) && ((local328 & 0x4) == 0 || Static384.aClass244_1.method5489(81))) {
												if (local296 < 10) {
													Component.method5309(local296 + 1, "", local6.anInt6809, -1);
												} else if (local296 == 10) {
													Class16_Sub5.method5139();
													@Pc(399) GlRectangleTexture local399 = method900(local6);
													SoundBankCache.method3975(local399.anInt5110, local399.method3911(), local6);
													Class230.aString62 = Class196.method4431(local6);
													if (Class230.aString62 == null) {
														Class230.aString62 = "Null";
													}
													Class4_Sub10.aString28 = local6.aString66 + "<col=ffffff>";
												}
												local430 = local6.anIntArray523[local296];
												if (local6.cycles == null) {
													local6.cycles = new int[local6.keyTriggeringAction.length];
												}
												if (local430 == 0) {
													local6.cycles[local296] = Integer.MAX_VALUE;
												} else {
													local6.cycles[local296] = Shadow.anInt5323 + local430;
												}
											}
										}
									} else if (local6.cycles != null) {
										local6.cycles[local296] = 0;
									}
								}
							}
							if (local258) {
								Class4_Sub6_Sub7.method1680(local263.method519() - local22, local6, local263.method518() - local17);
							}
							if (Static281.aClass247_12 != null && Static281.aClass247_12 != local6 && local204 && method900(local6).method3904()) {
								Static232.aClass247_9 = local6;
							}
							if (local6 == Static57.aClass247_1) {
								Class95_Sub3.aBoolean584 = true;
								Toolkit.anInt5535 = local17;
								Class222.anInt6383 = local22;
							}
							if (local6.aBoolean612 || local6.anInt6842 != 0) {
								@Pc(515) HookRequest local515;
								if (local204 && PointLight.pendingWheelDelta != 0 && local6.anObjectArray24 != null) {
									local515 = new HookRequest();
									local515.hasMousePosition = true;
									local515.source = local6;
									local515.anInt5252 = PointLight.pendingWheelDelta;
									local515.params = local6.anObjectArray24;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (Static281.aClass247_12 != null || Class4_Sub6_Sub22.aBoolean411 || local6.anInt6842 != Class196.anInt5689 && SpotAnimation.anInt1367 > 0) {
									local258 = false;
									local248 = false;
									local204 = false;
								}
								@Pc(670) int local670;
								if (local6.anInt6842 != 0) {
									if (local6.anInt6842 == QuickchatMessageType.anInt295 || local6.anInt6842 == Class28.anInt671) {
										ParticleProducerType.aClass247_7 = local6;
										if (GlMemoryPool.aClass242_1 != null) {
											GlMemoryPool.aClass242_1.method5248(local6.anInt6805, MonoStereoData.aClass19_16);
										}
										if (local6.anInt6842 == QuickchatMessageType.anInt295) {
											if (!Class4_Sub6_Sub22.aBoolean411 && arg8 >= local28 && arg9 >= local30 && arg8 < local32 && arg9 < local34) {
												TextureOpFlip.method2920(MonoStereoData.aClass19_16, arg8, arg9);
												for (@Pc(598) Class3_Sub4 local598 = (Class3_Sub4) LRUCache.aClass84_5.method2312(); local598 != null; local598 = (Class3_Sub4) LRUCache.aClass84_5.method2309()) {
													if (arg8 >= local598.anInt4019 && arg8 < local598.anInt4022 && arg9 >= local598.anInt4023 && arg9 < local598.anInt4021) {
														OpenGl2DImageTexture.method4198();
														Class4_Sub18.method2032(local598.aClass16_Sub1_Sub5_1);
													}
												}
											}
											Class79_Sub1.method4870(local22, local6, local17);
											continue;
										}
									}
									@Pc(709) int local709;
									if (local6.anInt6842 == Class16_Sub4.anInt3645) {
										if (local6.method5312(MonoStereoData.aClass19_16) == null || TextureOpFlip.anInt3532 != 0 && TextureOpFlip.anInt3532 != 3 || Class4_Sub6_Sub22.aBoolean411 || arg8 < local28 || arg9 < local30 || arg8 >= local32 || arg9 >= local34) {
											continue;
										}
										local296 = arg8 - local17;
										local670 = arg9 - local22;
										local430 = local6.anIntArray524[local670];
										if (local296 < local430 || local296 > local430 + local6.anIntArray519[local670]) {
											continue;
										}
										local296 -= local6.anInt6833 / 2;
										local670 -= local6.anInt6805 / 2;
										if (Class3_Sub3.anInt2386 == 4) {
											local709 = (int) Static368.aFloat192 & 0x3FFF;
										} else {
											local709 = (int) Static368.aFloat192 + PlayerVarDomain.anInt3399 & 0x3FFF;
										}
										@Pc(721) int local721 = Class50_Sub1.anIntArray225[local709];
										@Pc(725) int local725 = Class50_Sub1.anIntArray224[local709];
										if (Class3_Sub3.anInt2386 != 4) {
											local721 = local721 * (Class4_Sub17.anInt2374 + 256) >> 8;
											local725 = local725 * (Class4_Sub17.anInt2374 + 256) >> 8;
										}
										@Pc(754) int local754 = local670 * local721 + local296 * local725 >> 15;
										@Pc(764) int local764 = local670 * local725 - local296 * local721 >> 15;
										@Pc(775) int local775;
										@Pc(783) int local783;
										if (Class3_Sub3.anInt2386 == 4) {
											local775 = (Js5WorkerThread.anInt7059 >> 7) + (local754 >> 2);
											local783 = (Class4_Sub45.anInt7360 >> 7) - (local764 >> 2);
										} else {
											@Pc(792) int local792 = (Static1.aClass16_Sub1_Sub5_Sub1_1.getSize() - 1) * 64;
											local775 = (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 - local792 >> 7) + (local754 >> 2);
											local783 = (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 - local792 >> 7) - (local764 >> 2);
										}
										if (AbstractArbBufferGL.aBoolean599 && (Country.anInt7378 & 0x40) != 0) {
											@Pc(825) com.jagex.game.runetek5.config.rsinterface.Component local825 = EnvironmentSampler.method3230(Class243.anInt6726, StackFrame.anInt350);
											if (local825 == null) {
												Class16_Sub5.method5139();
											} else {
												Static380.method5017(19, false, 1L, local775, " ->", true, Class230.aString62, local783, local6.anInt6779, Class164.anInt5120);
											}
											continue;
										}
										if (DynamicWall.aClass121_3 == FixedFunctionSpecularPass.WRITE_PING) {
											Static380.method5017(59, false, 1L, local775, "", true, TextureType.aClass83_83.method2267(NativeBillboardFace.anInt3423), local783, -1, -1);
										}
										Static380.method5017(44, false, 1L, local775, "", true, Static167.aString35, local783, -1, TextureOpConstant.anInt4097);
										continue;
									}
									if (local6.anInt6842 == Class196.anInt5689) {
										GlGroundLayer.aClass247_2 = local6;
										if (local204) {
											TextureOpMonochromeEdgeDetector.aBoolean653 = true;
										}
										if (local258) {
											local296 = (int) ((double) (local263.method518() - local17 - local6.anInt6833 / 2) * 2.0D / (double) Static148.mapZoom);
											local670 = (int) -((double) (local263.method519() - local22 - local6.anInt6805 / 2) * 2.0D / (double) Static148.mapZoom);
											local430 = OpenGl2DImageTexture.anInt5471 + local296 + Static148.anInt5732;
											local709 = TextureCombinerOp.anInt5156 + local670 + Static148.anInt5724;
											@Pc(939) WorldMapElement local939 = Static107.method1990();
											if (local939 == null) {
												continue;
											}
											@Pc(944) int[] local944 = new int[3];
											local939.method930(local430, local944, local709);
											if (local944 != null) {
												if (Static384.aClass244_1.method5489(82) && GlToolkit.activeChildCount > 0) {
													Class4_Sub21_Sub2.method3168(local944[0], local944[2], local944[1]);
													continue;
												}
												AbstractFontRenderer.aBoolean588 = true;
												Class16_Sub4.anInt3644 = local944[0];
												Class4_Sub6_Sub25.anInt4958 = local944[1];
												Class145_Sub1.anInt7274 = local944[2];
											}
											SpotAnimation.anInt1367 = 1;
											Static105.aBoolean218 = false;
											Huffman.anInt3559 = MaterialType.aClass80_1.method2234();
											Class4_Sub45.anInt7359 = MaterialType.aClass80_1.getDefaultValue();
											continue;
										}
										if (local248 && SpotAnimation.anInt1367 > 0) {
											if (SpotAnimation.anInt1367 == 1 && (Huffman.anInt3559 != MaterialType.aClass80_1.method2234() || Class4_Sub45.anInt7359 != MaterialType.aClass80_1.getDefaultValue())) {
												Class269.anInt7339 = OpenGl2DImageTexture.anInt5471;
												Class4_Sub6_Sub7.anInt1933 = TextureCombinerOp.anInt5156;
												SpotAnimation.anInt1367 = 2;
											}
											if (SpotAnimation.anInt1367 == 2) {
												Static105.aBoolean218 = true;
												CacheableNode.method4663(Class269.anInt7339 + (int) ((double) (Huffman.anInt3559 - MaterialType.aClass80_1.method2234()) * 2.0D / (double) Static148.requestedMapZoom));
												TileMessage.method5679(Class4_Sub6_Sub7.anInt1933 - (int) ((double) (Class4_Sub45.anInt7359 - MaterialType.aClass80_1.getDefaultValue()) * 2.0D / (double) Static148.requestedMapZoom));
											}
											continue;
										}
										if (SpotAnimation.anInt1367 > 0 && !Static105.aBoolean218) {
											if ((SpotAnimation.anInt1361 == 1 || Class101.method2677()) && Skybox.anInt6710 > 2) {
												Static102.method1794(Huffman.anInt3559, Class4_Sub45.anInt7359);
											} else if (Static14.method143()) {
												Static102.method1794(Huffman.anInt3559, Class4_Sub45.anInt7359);
											}
										}
										SpotAnimation.anInt1367 = 0;
										continue;
									}
									if (local6.anInt6842 == Class4_Sub25.anInt3548) {
										if (local248) {
											SeqTypeList.method1395(local6.anInt6833, MaterialType.aClass80_1.method2234() - local17, local6.anInt6805, MaterialType.aClass80_1.getDefaultValue() - local22);
										}
										continue;
									}
									if (local6.anInt6842 == AnimBase.anInt4982) {
										EmissiveTriangle.method697(local6, local22, local17);
										continue;
									}
								}
								if (!local6.aBoolean617 && local258) {
									local6.aBoolean617 = true;
									if (local6.anObjectArray12 != null) {
										local515 = new HookRequest();
										local515.hasMousePosition = true;
										local515.source = local6;
										local515.anInt5254 = local263.method518() - local17;
										local515.anInt5252 = local263.method519() - local22;
										local515.params = local6.anObjectArray12;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									}
								}
								if (local6.aBoolean617 && local248 && local6.anObjectArray23 != null) {
									local515 = new HookRequest();
									local515.hasMousePosition = true;
									local515.source = local6;
									local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
									local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
									local515.params = local6.anObjectArray23;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (local6.aBoolean617 && !local248) {
									local6.aBoolean617 = false;
									if (local6.anObjectArray33 != null) {
										local515 = new HookRequest();
										local515.hasMousePosition = true;
										local515.source = local6;
										local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
										local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
										local515.params = local6.anObjectArray33;
										TextureOpCurve.aClass183_3.method4137(local515);
									}
								}
								if (local248 && local6.anObjectArray22 != null) {
									local515 = new HookRequest();
									local515.hasMousePosition = true;
									local515.source = local6;
									local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
									local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
									local515.params = local6.anObjectArray22;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (!local6.aBoolean611 && local204) {
									local6.aBoolean611 = true;
									if (local6.anObjectArray32 != null) {
										local515 = new HookRequest();
										local515.hasMousePosition = true;
										local515.source = local6;
										local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
										local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
										local515.params = local6.anObjectArray32;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									}
								}
								if (local6.aBoolean611 && local204 && local6.anObjectArray27 != null) {
									local515 = new HookRequest();
									local515.hasMousePosition = true;
									local515.source = local6;
									local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
									local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
									local515.params = local6.anObjectArray27;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (local6.aBoolean611 && !local204) {
									local6.aBoolean611 = false;
									if (local6.anObjectArray9 != null) {
										local515 = new HookRequest();
										local515.hasMousePosition = true;
										local515.source = local6;
										local515.anInt5254 = MaterialType.aClass80_1.method2234() - local17;
										local515.anInt5252 = MaterialType.aClass80_1.getDefaultValue() - local22;
										local515.params = local6.anObjectArray9;
										TextureOpCurve.aClass183_3.method4137(local515);
									}
								}
								if (local6.anObjectArray8 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray8;
									ComponentSurface.aClass183_52.method4137(local515);
								}
								@Pc(1483) HookRequest local1483;
								if (local6.anObjectArray30 != null && OpenGl2DImageTexture.anInt5473 > local6.anInt6827) {
									if (local6.anIntArray518 == null || OpenGl2DImageTexture.anInt5473 - local6.anInt6827 > 32) {
										local515 = new HookRequest();
										local515.source = local6;
										local515.params = local6.anObjectArray30;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									} else {
										label678: for (local296 = local6.anInt6827; local296 < OpenGl2DImageTexture.anInt5473; local296++) {
											local670 = Class93.anIntArray184[local296 & 0x1F];
											for (local430 = 0; local430 < local6.anIntArray518.length; local430++) {
												if (local6.anIntArray518[local430] == local670) {
													local1483 = new HookRequest();
													local1483.source = local6;
													local1483.params = local6.anObjectArray30;
													TextureOpMandelbrot.aClass183_35.method4137(local1483);
													break label678;
												}
											}
										}
									}
									local6.anInt6827 = OpenGl2DImageTexture.anInt5473;
								}
								if (local6.anObjectArray34 != null && TextureType.anInt4042 > local6.anInt6806) {
									if (local6.anIntArray517 == null || TextureType.anInt4042 - local6.anInt6806 > 32) {
										local515 = new HookRequest();
										local515.source = local6;
										local515.params = local6.anObjectArray34;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									} else {
										label654: for (local296 = local6.anInt6806; local296 < TextureType.anInt4042; local296++) {
											local670 = Static277.anIntArray337[local296 & 0x1F];
											for (local430 = 0; local430 < local6.anIntArray517.length; local430++) {
												if (local6.anIntArray517[local430] == local670) {
													local1483 = new HookRequest();
													local1483.source = local6;
													local1483.params = local6.anObjectArray34;
													TextureOpMandelbrot.aClass183_35.method4137(local1483);
													break label654;
												}
											}
										}
									}
									local6.anInt6806 = TextureType.anInt4042;
								}
								if (local6.anObjectArray18 != null && HookRequest.anInt5255 > local6.anInt6847) {
									if (local6.anIntArray515 == null || HookRequest.anInt5255 - local6.anInt6847 > 32) {
										local515 = new HookRequest();
										local515.source = local6;
										local515.params = local6.anObjectArray18;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									} else {
										label630: for (local296 = local6.anInt6847; local296 < HookRequest.anInt5255; local296++) {
											local670 = Class7.anIntArray7[local296 & 0x1F];
											for (local430 = 0; local430 < local6.anIntArray515.length; local430++) {
												if (local6.anIntArray515[local430] == local670) {
													local1483 = new HookRequest();
													local1483.source = local6;
													local1483.params = local6.anObjectArray18;
													TextureOpMandelbrot.aClass183_35.method4137(local1483);
													break label630;
												}
											}
										}
									}
									local6.anInt6847 = HookRequest.anInt5255;
								}
								if (local6.anObjectArray19 != null && Js5TextureSource.anInt2841 > local6.anInt6839) {
									if (local6.anIntArray520 == null || Js5TextureSource.anInt2841 - local6.anInt6839 > 32) {
										local515 = new HookRequest();
										local515.source = local6;
										local515.params = local6.anObjectArray19;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									} else {
										label606: for (local296 = local6.anInt6839; local296 < Js5TextureSource.anInt2841; local296++) {
											local670 = DynamicWall.anIntArray500[local296 & 0x1F];
											for (local430 = 0; local430 < local6.anIntArray520.length; local430++) {
												if (local6.anIntArray520[local430] == local670) {
													local1483 = new HookRequest();
													local1483.source = local6;
													local1483.params = local6.anObjectArray19;
													TextureOpMandelbrot.aClass183_35.method4137(local1483);
													break label606;
												}
											}
										}
									}
									local6.anInt6839 = Js5TextureSource.anInt2841;
								}
								if (local6.anObjectArray36 != null && Class204.anInt5948 > local6.anInt6820) {
									if (local6.anIntArray522 == null || Class204.anInt5948 - local6.anInt6820 > 32) {
										local515 = new HookRequest();
										local515.source = local6;
										local515.params = local6.anObjectArray36;
										TextureOpMandelbrot.aClass183_35.method4137(local515);
									} else {
										label582: for (local296 = local6.anInt6820; local296 < Class204.anInt5948; local296++) {
											local670 = TextureOpCurve.anIntArray40[local296 & 0x1F];
											for (local430 = 0; local430 < local6.anIntArray522.length; local430++) {
												if (local6.anIntArray522[local430] == local670) {
													local1483 = new HookRequest();
													local1483.source = local6;
													local1483.params = local6.anObjectArray36;
													TextureOpMandelbrot.aClass183_35.method4137(local1483);
													break label582;
												}
											}
										}
									}
									local6.anInt6820 = Class204.anInt5948;
								}
								if (StringNode.anInt5303 > local6.anInt6824 && local6.anObjectArray11 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray11;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (Class4_Sub1_Sub9.anInt3027 > local6.anInt6824 && local6.anObjectArray35 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray35;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (ParamTypeList.anInt6418 > local6.anInt6824 && local6.anObjectArray31 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray31;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (Class4_Sub29.anInt4589 > local6.anInt6824 && local6.anObjectArray13 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray13;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								if (Static308.anInt5413 > local6.anInt6824 && local6.anObjectArray14 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray14;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
								local6.anInt6824 = MusicPatch.anInt5640;
								if (local6.anObjectArray20 != null) {
									for (local296 = 0; local296 < Class4_Sub6_Sub15.anInt3602; local296++) {
										@Pc(1951) HookRequest local1951 = new HookRequest();
										local1951.source = local6;
										local1951.typedKeyCode = Class16_Sub2.aClass30Array4[local296].method746();
										local1951.anInt5251 = Class16_Sub2.aClass30Array4[local296].method749();
										local1951.params = local6.anObjectArray20;
										TextureOpMandelbrot.aClass183_35.method4137(local1951);
									}
								}
								if (Class16_Sub5_Sub2.aBoolean549 && local6.anObjectArray6 != null) {
									local515 = new HookRequest();
									local515.source = local6;
									local515.params = local6.anObjectArray6;
									TextureOpMandelbrot.aClass183_35.method4137(local515);
								}
							}
							if (local6.anInt6840 == 5 && local6.anInt6780 != -1) {
								local6.method5311(CacheableNodeByteArray.aClass226_1, StringNode.aClass217_1).method5248(local6.anInt6805, MonoStereoData.aClass19_16);
							}
							Class50.method2851(local6);
							if (local6.anInt6840 == 0) {
								method901(arg0, local6.anInt6809, local28, local30, local32, local34, local17 - local6.anInt6825, local22 - local6.anInt6849, arg8, arg9);
								if (local6.slotChildren != null) {
									method901(local6.slotChildren, local6.anInt6809, local28, local30, local32, local34, local17 - local6.anInt6825, local22 - local6.anInt6849, arg8, arg9);
								}
								@Pc(2073) Class4_Sub43 local2073 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local6.anInt6809);
								if (local2073 != null) {
									if (DynamicWall.aClass121_3 == TextureOpTexture.aClass121_1 && local2073.anInt6981 == 0 && !Class4_Sub6_Sub22.aBoolean411 && local204 && !Class249.aBoolean626) {
										OpenGl2DImageTexture.method4198();
									}
									TextureOpTiledSprite.method988(local28, local22, local32, local2073.anInt6979, local30, local17, local34, arg9, arg8);
								}
							}
						}
					}
				} else if (local28 < local32 && local30 < local34) {
					Class50.method2851(local6);
				}
			}
		}
	}
@OriginalMember(owner = "client!client", name = "n", descriptor = "(I)V")
	public static void updateDrawPriority(@OriginalArg(0) int level) {
		@Pc(1) int local1 = Class4_Sub7.anInt606;
		@Pc(3) int[] local3 = Static99.anIntArray157;
		for (@Pc(5) int local5 = 0; local5 < local1 + Class10.anInt163; local5++) {
			@Pc(15) PathingEntity local15;
			if (local5 < local1) {
				local15 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local3[local5]];
			} else {
				local15 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local5 - local1]];
			}
			if (local15.aByte82 == level && local15.worldPriority >= 0) {
				@Pc(35) int local35 = local15.getSize();
				if ((local35 & 0x1) == 0) {
					if ((local15.anInt6893 & 0x7F) != 0 || (local15.anInt6892 & 0x7F) != 0) {
						continue;
					}
				} else if ((local15.anInt6893 & 0x7F) != 64 || (local15.anInt6892 & 0x7F) != 64) {
					continue;
				}
				@Pc(71) int local71;
				@Pc(76) int local76;
				@Pc(112) int local112;
				if (local35 == 1) {
					local71 = local15.anInt6893 >> 7;
					local76 = local15.anInt6892 >> 7;
					if (local15.worldPriority > EasedAngle.anIntArrayArray8[local71][local76]) {
						EasedAngle.anIntArrayArray8[local71][local76] = local15.worldPriority;
						Class4_Sub9.anIntArrayArray7[local71][local76] = 1;
					} else if (local15.worldPriority == EasedAngle.anIntArrayArray8[local71][local76]) {
						local112 = Class4_Sub9.anIntArrayArray7[local71][local76]++;
					}
				} else {
					local71 = (local35 - 1) * 64 + 60;
					local76 = local15.anInt6893 - local71 >> 7;
					@Pc(138) int local138 = local15.anInt6892 - local71 >> 7;
					@Pc(145) int local145 = local15.anInt6893 + local71 >> 7;
					@Pc(152) int local152 = local15.anInt6892 + local71 >> 7;
					for (@Pc(154) int local154 = local76; local154 <= local145; local154++) {
						for (@Pc(157) int local157 = local138; local157 <= local152; local157++) {
							if (local15.worldPriority > EasedAngle.anIntArrayArray8[local154][local157]) {
								EasedAngle.anIntArrayArray8[local154][local157] = local15.worldPriority;
								Class4_Sub9.anIntArrayArray7[local154][local157] = 1;
							} else if (local15.worldPriority == EasedAngle.anIntArrayArray8[local154][local157]) {
								local112 = Class4_Sub9.anIntArrayArray7[local154][local157]++;
							}
						}
					}
				}
			}
		}
	}
@OriginalMember(owner = "client!client", name = "b", descriptor = "()V")
	public static void method904() {
		@Pc(1) int local1 = Class4_Sub7.anInt606;
		@Pc(3) int[] local3 = Static99.anIntArray157;
		@Pc(11) int local11 = Class48.aBoolean508 ? local1 : local1 + Class10.anInt163;
		for (@Pc(13) int local13 = 0; local13 < local11; local13++) {
			@Pc(23) PathingEntity local23;
			if (local13 < local1) {
				local23 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local3[local13]];
			} else {
				local23 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local13 - local1]];
			}
			if (local23.worldPriority >= 0) {
				@Pc(39) int local39 = local23.getSize();
				if ((local39 & 0x1) == 0) {
					if ((local23.anInt6893 & 0x7F) == 0 && (local23.anInt6892 & 0x7F) == 0) {
						continue;
					}
				} else if ((local23.anInt6893 & 0x7F) == 64 && (local23.anInt6892 & 0x7F) == 64) {
					continue;
				}
				if (local23 instanceof PlayerEntity && local23.aClass4_Sub23_3 != null && Shadow.anInt5323 >= local23.aClass4_Sub23_3.anInt3191 && Shadow.anInt5323 < local23.aClass4_Sub23_3.anInt3202) {
					((PlayerEntity) local23).shouldSuppressMovingAnimation = false;
				}
				local23.anInt6889 = BufferedSocket.method135(local23.anInt6893, local23.aByte82, local23.anInt6892);
				Class193.method4385(local23, true);
			}
		}
	}
@OriginalMember(owner = "client!client", name = "c", descriptor = "(Lclient!uu;)Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component method906(@OriginalArg(0) com.jagex.game.runetek5.config.rsinterface.Component arg0) {
		@Pc(4) int local4 = method900(arg0).method3910();
		if (local4 == 0) {
			return null;
		}
		for (@Pc(10) int local10 = 0; local10 < local4; local10++) {
			arg0 = ParticleBinaryTree.method5121(arg0.anInt6850);
			if (arg0 == null) {
				return null;
			}
		}
		return arg0;
	}
@OriginalMember(owner = "client!client", name = "c", descriptor = "()V")
	public static void method907() {
		Class180.anInt5369 = 0;
		for (@Pc(3) int local3 = 0; local3 < Class10.anInt163; local3++) {
			@Pc(10) NPCEntity local10 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local3]];
			if (local10.isHidden && local10.getOverheadSprite() != -1) {
				@Pc(28) int local28 = (local10.getSize() - 1) * 64 + 60;
				@Pc(35) int local35 = local10.anInt6893 - local28 >> 7;
				@Pc(42) int local42 = local10.anInt6892 - local28 >> 7;
				@Pc(49) PathingEntity local49 = FileWorker.method3513(local42, local35, local10.aByte82);
				if (local49 != null) {
					@Pc(54) int local54 = local49.anInt6037;
					if (local49 instanceof NPCEntity) {
						local54 += 2048;
					}
					if (local49.overheadStackCount == 0 && local49.getOverheadSprite() != -1) {
						Class136.anIntArray290[Class180.anInt5369] = local54;
						EffectManagerGL.anIntArray525[Class180.anInt5369] = local54;
						Class180.anInt5369++;
						local49.overheadStackCount++;
					}
					Class136.anIntArray290[Class180.anInt5369] = local54;
					EffectManagerGL.anIntArray525[Class180.anInt5369] = local10.anInt6037 + 2048;
					Class180.anInt5369++;
					local49.overheadStackCount++;
				}
			}
		}
		Class4_Sub6_Sub5.method1387(EffectManagerGL.anIntArray525, 0, Class136.anIntArray290, Class180.anInt5369 - 1);
	}
@OriginalMember(owner = "client!client", name = "d", descriptor = "()V")
	public static void resetDrawPriorities() {
		for (@Pc(1) int local1 = 0; local1 < GLBillowTextureData.anInt5666; local1++) {
			@Pc(6) int[] local6 = EasedAngle.anIntArrayArray8[local1];
			for (@Pc(8) int local8 = 0; local8 < Static283.anInt5187; local8++) {
				local6[local8] = 0;
			}
		}
	}
@OriginalMember(owner = "client!client", name = "e", descriptor = "()V")
	public static void calculateEntityPriorities() {
		@Pc(1) int local1 = Class4_Sub7.anInt606;
		@Pc(3) int[] local3 = Static99.anIntArray157;
		@Pc(20) boolean local20 = TextureOpBrightness.aClass50_Sub1_1.anInt3433 == 1 && local1 > 200 || TextureOpBrightness.aClass50_Sub1_1.anInt3433 == 0 && local1 > 50;
		@Pc(75) int local75;
		for (@Pc(22) int local22 = 0; local22 < local1; local22++) {
			@Pc(29) PlayerEntity local29 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local3[local22]];
			if (local29.hasAppearance()) {
				local29.method5346();
				if (local29.aShort94 >= 0 && local29.aShort96 >= 0 && local29.aShort97 < GLBillowTextureData.anInt5666 && local29.aShort95 < Static283.anInt5187) {
					local29.shouldSuppressMovingAnimation = local29.aBoolean562 ? local20 : false;
					if (local29 == Static1.aClass16_Sub1_Sub5_Sub1_1) {
						local29.worldPriority = Integer.MAX_VALUE;
					} else {
						local75 = 0;
						if (!local29.isHidden) {
							local75++;
						}
						if (local29.anInt6072 > Shadow.anInt5323) {
							local75 += 2;
						}
						local75 += 5 - local29.getSize() << 2;
						if (local29.pMapdot) {
							local75 += 512;
						} else {
							if (SpotAnimationType.anInt4991 == 0) {
								local75 += 32;
							} else {
								local75 += 128;
							}
							local75 += 256;
						}
						local29.worldPriority = local75 + 1;
					}
				} else {
					local29.worldPriority = -1;
				}
			} else {
				local29.worldPriority = -1;
			}
		}
		for (@Pc(116) int local116 = 0; local116 < Class10.anInt163; local116++) {
			@Pc(123) NPCEntity local123 = Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local116]];
			if (local123.hasType() && local123.npcType.resolvesToNpc(Class4_Sub6_Sub24.aClass114_1)) {
				local123.method5346();
				if (local123.aShort94 >= 0 && local123.aShort96 >= 0 && local123.aShort97 < GLBillowTextureData.anInt5666 && local123.aShort95 < Static283.anInt5187) {
					@Pc(160) int local160 = 0;
					if (!local123.isHidden) {
						local160++;
					}
					if (local123.anInt6072 > Shadow.anInt5323) {
						local160 += 2;
					}
					local160 += 5 - local123.getSize() << 2;
					if (SpotAnimationType.anInt4991 == 0) {
						if (local123.npcType.aBoolean685) {
							local160 += 64;
						} else {
							local160 += 128;
						}
					} else if (SpotAnimationType.anInt4991 == 1) {
						if (local123.npcType.aBoolean685) {
							local160 += 32;
						} else {
							local160 += 64;
						}
					}
					if (local123.npcType.highPriority) {
						local160 += 1024;
					} else if (!local123.npcType.aBoolean680) {
						local160 += 256;
					}
					local123.worldPriority = local160 + 1;
				} else {
					local123.worldPriority = -1;
				}
			} else {
				local123.worldPriority = -1;
			}
		}
		for (local75 = 0; local75 < Class183.aClass38Array1.length; local75++) {
			@Pc(226) Class38 local226 = Class183.aClass38Array1[local75];
			if (local226 != null) {
				if (local226.anInt891 == 1) {
					@Pc(237) NPCEntity local237 = Class93.aClass16_Sub1_Sub5_Sub2Array1[local226.anInt885];
					if (local237 != null && local237.worldPriority >= 0) {
						local237.worldPriority += 2048;
					}
				} else if (local226.anInt891 == 10) {
					@Pc(258) PlayerEntity local258 = Static267.aClass16_Sub1_Sub5_Sub1Array1[local226.anInt885];
					if (local258 != null && local258 != Static1.aClass16_Sub1_Sub5_Sub1_1 && local258.worldPriority >= 0) {
						local258.worldPriority += 2048;
					}
				}
			}
		}
	}
}
