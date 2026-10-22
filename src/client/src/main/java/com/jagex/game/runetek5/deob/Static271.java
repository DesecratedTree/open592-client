package com.jagex.game.runetek5.deob;

import jagex3.jagmisc.jagmisc;
import java.util.Calendar;
import java.util.Date;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.client.cursor.CursorSettings;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.config.item.ItemType;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.config.quickchat.QuickChatCategoryType;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.config.bas.BasType;

import com.jagex.game.runetek5.config.sequence.frameset.AnimBase;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpPerlinNoise;
import com.jagex.game.runetek5.deob.Class16;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.deob.RuntimeException_Sub1;
import com.jagex.game.runetek5.preference.Preference;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.config.identitykit.IdkType;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.deob.Class120;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.FluType;
import com.jagex.game.runetek5.Js5TextureSource;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.cache.node.PingRequest;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class3_Sub3;
import com.jagex.game.runetek5.deob.Class9_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub36;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.js5.Js5WorkerThread;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSquareWaveform;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.deob.Class189;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteType;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class70_Sub1;
import com.jagex.game.runetek5.deob.QuickChatValueType;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class79_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub7;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.Class48_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.CacheableNodeByteArray;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.deob.Class4_Sub3;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.OpenGLArbTextureCubeMapGenerator;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.entity.DynamicWall;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub12;
import com.jagex.game.runetek5.core.compress.BzipDecompressor;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.entity.EntityNode;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.graphics.gl.GlCubeTexture;
import com.jagex.game.runetek5.deob.Class85;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.graphics.FramebufferCompositor;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class190;
import com.jagex.game.runetek5.config.midi.SoundBankCache;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.graphics.gl.OpenGlCubeTextureGenerator;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.Class95_Sub3_Sub1;
import com.jagex.game.runetek5.deob.Resampler;
import com.jagex.game.runetek5.cache.node.lru.OpenGlRenderBuffer;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.Class149;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMandelbrot;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.deob.Class103;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class95;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.deob.Class27_Sub3;
import com.jagex.game.runetek5.deob.TileMessage;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub33;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.PositionedGfx;
import com.jagex.game.runetek5.config.material.texture.TextureType;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.entity.PathingEntity;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub35;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class4_Sub17;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.deob.TextureCombinerOp;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.deob.Class95_Sub2;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.entity.ParticleEmitter;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpDiagonalWaves;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpIrregularBricks;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class267;
import com.jagex.game.runetek5.deob.Class79;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub17;
import com.jagex.game.runetek5.entity.pathingentity.EasedAngle;
import com.jagex.game.runetek5.deob.Class9;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.toolkit.opengl.effect.StillWaterEffect;
import com.jagex.game.runetek5.deob.Class30;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.client.input.mouse.SimpleMouseLog;
import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.deob.Class4_Sub29;
import com.jagex.game.runetek5.deob.Class4_Sub7;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.config.sequence.PathingEntitySequence;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.Class27_Sub2;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub1;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.config.rsinterface.Component;
public final class Static271 {

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "[I")
	private static int[] intLocals;

	@OriginalMember(owner = "client!nr", name = "g", descriptor = "Lclient!vq;")
	private static QuickChatPhrase currentQuickChatPhrase;

	@OriginalMember(owner = "client!nr", name = "j", descriptor = "Lclient!uu;")
	private static com.jagex.game.runetek5.config.rsinterface.Component activeComponent;

	@OriginalMember(owner = "client!nr", name = "u", descriptor = "Lclient!uu;")
	private static com.jagex.game.runetek5.config.rsinterface.Component activeComponent2;

	@OriginalMember(owner = "client!nr", name = "y", descriptor = "[Ljava/lang/String;")
	private static String[] stringVars;

	@OriginalMember(owner = "client!nr", name = "i", descriptor = "[I")
	private static final int[] intStack = new int[1000];

	@OriginalMember(owner = "client!nr", name = "l", descriptor = "[Ljava/lang/String;")
	private static final String[] stringStack = new String[1000];

	@OriginalMember(owner = "client!nr", name = "m", descriptor = "I")
	private static int stringStackPtr = 0;

	@OriginalMember(owner = "client!nr", name = "n", descriptor = "[[I")
	private static final int[][] arrayVars = new int[5][5000];

	@OriginalMember(owner = "client!nr", name = "q", descriptor = "I")
	private static int intStackPtr = 0;

	@OriginalMember(owner = "client!nr", name = "s", descriptor = "Ljava/util/Calendar;")
	private static final Calendar LOCAL_CALENDAR = Calendar.getInstance();

	@OriginalMember(owner = "client!nr", name = "t", descriptor = "[Lclient!b;")
	private static final StackFrame[] frames = new StackFrame[50];

	@OriginalMember(owner = "client!nr", name = "w", descriptor = "[I")
	private static final int[] globalArrayLengths = new int[5];

	@OriginalMember(owner = "client!nr", name = "x", descriptor = "I")
	private static int framePtr = 0;

	@OriginalMember(owner = "client!nr", name = "z", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_29 = new LRUCache(4);

	@OriginalMember(owner = "client!nr", name = "A", descriptor = "[I")
	private static final int[] coordConversionBuffer = new int[3];

	@OriginalMember(owner = "client!nr", name = "B", descriptor = "[Ljava/lang/String;")
	private static final String[] monthNames = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

	@OriginalMember(owner = "client!nr", name = "C", descriptor = "I")
	private static int hookRequestCount = 0;

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(Lclient!op;I)V")
	private static void executeHookInternal(@OriginalArg(0) HookRequest hook, @OriginalArg(1) int stackLimit) {
		@Pc(2) Object[] local2 = hook.params;
		@Pc(8) int local8 = (Integer) local2[0];
		@Pc(12) ClientScript local12 = Static211.method3200(local8);
		if (local12 == null) {
			return;
		}
		intLocals = new int[local12.anInt55];
		@Pc(21) int local21 = 0;
		stringVars = new String[local12.anInt56];
		@Pc(27) int local27 = 0;
		for (@Pc(29) int local29 = 1; local29 < local2.length; local29++) {
			if (local2[local29] instanceof Integer) {
				@Pc(41) int local41 = (Integer) local2[local29];
				if (local41 == -2147483647) {
					local41 = hook.anInt5254;
				}
				if (local41 == -2147483646) {
					local41 = hook.anInt5252;
				}
				if (local41 == -2147483645) {
					local41 = hook.source == null ? -1 : hook.source.anInt6809;
				}
				if (local41 == -2147483644) {
					local41 = hook.anInt5250;
				}
				if (local41 == -2147483643) {
					local41 = hook.source == null ? -1 : hook.source.anInt6865;
				}
				if (local41 == -2147483642) {
					local41 = hook.targetComponent == null ? -1 : hook.targetComponent.anInt6809;
				}
				if (local41 == -2147483641) {
					local41 = hook.targetComponent == null ? -1 : hook.targetComponent.anInt6865;
				}
				if (local41 == -2147483640) {
					local41 = hook.typedKeyCode;
				}
				if (local41 == -2147483639) {
					local41 = hook.anInt5251;
				}
				intLocals[local21++] = local41;
			} else if (local2[local29] instanceof String) {
				@Pc(135) String local135 = (String) local2[local29];
				if (local135.equals("event_opbase")) {
					local135 = hook.aString50;
				}
				stringVars[local27++] = local135;
			}
		}
		hookRequestCount = hook.id;
		executeScript(local12, stackLimit);
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(I)Ljava/lang/String;")
	private static String runedayToDateString(@OriginalArg(0) int runeday) {
		@Pc(6) long local6 = ((long) runeday + 11745L) * 86400000L;
		LOCAL_CALENDAR.setTime(new Date(local6));
		@Pc(16) int local16 = LOCAL_CALENDAR.get(5);
		@Pc(20) int local20 = LOCAL_CALENDAR.get(2);
		@Pc(24) int local24 = LOCAL_CALENDAR.get(1);
		return local16 + "-" + monthNames[local20] + "-" + local24;
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(IZ)V")
	private static void executeOpBelow5000(@OriginalArg(0) int opcode, @OriginalArg(1) boolean hasIntOperand) {
		@Pc(137) com.jagex.game.runetek5.config.rsinterface.Component local137;
		@Pc(25) int local25;
		@Pc(56) int local56;
		@Pc(19) int local19;
		@Pc(35) com.jagex.game.runetek5.config.rsinterface.Component local35;
		@Pc(13) int local13;
		@Pc(210) com.jagex.game.runetek5.config.rsinterface.Component local210;
		@Pc(158) com.jagex.game.runetek5.config.rsinterface.Component local158;
		if (opcode < 300) {
			if (opcode == 100) {
				intStackPtr -= 3;
				local13 = intStack[intStackPtr];
				local19 = intStack[intStackPtr + 1];
				local25 = intStack[intStackPtr + 2];
				if (local19 == 0) {
					throw new RuntimeException();
				}
				local35 = ParticleBinaryTree.method5121(local13);
				if (local35.slotChildren == null) {
					local35.slotChildren = new com.jagex.game.runetek5.config.rsinterface.Component[local25 + 1];
				}
				if (local35.slotChildren.length <= local25) {
					@Pc(54) com.jagex.game.runetek5.config.rsinterface.Component[] local54 = new com.jagex.game.runetek5.config.rsinterface.Component[local25 + 1];
					for (local56 = 0; local56 < local35.slotChildren.length; local56++) {
						local54[local56] = local35.slotChildren[local56];
					}
					local35.slotChildren = local54;
				}
				if (local25 > 0 && local35.slotChildren[local25 - 1] == null) {
					throw new RuntimeException("Gap at:" + (local25 - 1));
				}
				@Pc(99) com.jagex.game.runetek5.config.rsinterface.Component local99 = new com.jagex.game.runetek5.config.rsinterface.Component();
				local99.anInt6840 = local19;
				local99.anInt6850 = local99.anInt6809 = local35.anInt6809;
				local99.anInt6865 = local25;
				local35.slotChildren[local25] = local99;
				if (hasIntOperand) {
					activeComponent2 = local99;
				} else {
					activeComponent = local99;
				}
				Class4_Sub12_Sub1.method1142(local35);
				return;
			}
			if (opcode == 101) {
				local137 = hasIntOperand ? activeComponent2 : activeComponent;
				if (local137.anInt6865 == -1) {
					if (hasIntOperand) {
						throw new RuntimeException("Tried to .cc_delete static .active-component!");
					}
					throw new RuntimeException("Tried to cc_delete static active-component!");
				}
				local158 = ParticleBinaryTree.method5121(local137.anInt6809);
				local158.slotChildren[local137.anInt6865] = null;
				Class4_Sub12_Sub1.method1142(local158);
				return;
			}
			if (opcode == 102) {
				local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
				local137.slotChildren = null;
				Class4_Sub12_Sub1.method1142(local137);
				return;
			}
			if (opcode == 200) {
				intStackPtr -= 2;
				local13 = intStack[intStackPtr];
				local19 = intStack[intStackPtr + 1];
				local210 = EnvironmentSampler.method3230(local13, local19);
				if (local210 != null && local19 != -1) {
					intStack[intStackPtr++] = 1;
					if (hasIntOperand) {
						activeComponent2 = local210;
						return;
					}
					activeComponent = local210;
					return;
				}
				intStack[intStackPtr++] = 0;
				return;
			}
			if (opcode == 201) {
				local13 = intStack[--intStackPtr];
				local158 = ParticleBinaryTree.method5121(local13);
				if (local158 != null) {
					intStack[intStackPtr++] = 1;
					if (hasIntOperand) {
						activeComponent2 = local158;
						return;
					}
					activeComponent = local158;
					return;
				}
				intStack[intStackPtr++] = 0;
				return;
			}
			if (opcode == 202) {
				local13 = intStack[--intStackPtr];
				method3889(local13);
				return;
			}
			if (opcode == 203) {
				local13 = intStack[--intStackPtr];
				method3890(local13);
				return;
			}
		} else {
			@Pc(353) int local353;
			@Pc(412) boolean local412;
			if (opcode < 500) {
				if (opcode == 403) {
					intStackPtr -= 2;
					local13 = intStack[intStackPtr];
					local19 = intStack[intStackPtr + 1];
					for (local25 = 0; local25 < LRUCache.anIntArray158.length; local25++) {
						if (LRUCache.anIntArray158[local25] == local13) {
							Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.method832(local19, MaterialType.aClass109_2, local25);
							return;
						}
					}
					for (local353 = 0; local353 < Class111.anIntArray218.length; local353++) {
						if (Class111.anIntArray218[local353] == local13) {
							Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.method832(local19, MaterialType.aClass109_2, local353);
							return;
						}
					}
					return;
				}
				if (opcode == 404) {
					intStackPtr -= 2;
					local13 = intStack[intStackPtr];
					local19 = intStack[intStackPtr + 1];
					Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.method838(local13, local19);
					return;
				}
				if (opcode == 410) {
					local412 = intStack[--intStackPtr] != 0;
					Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.method831(local412);
					return;
				}
			} else if ((opcode < 1000 || opcode >= 1100) && (opcode < 2000 || opcode >= 2100)) {
				@Pc(1488) String local1488;
				@Pc(1101) String local1101;
				if (opcode >= 1100 && opcode < 1200 || opcode >= 2100 && opcode < 2200) {
					if (opcode >= 2000) {
						opcode -= 1000;
						local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
					} else {
						local137 = hasIntOperand ? activeComponent2 : activeComponent;
					}
					if (opcode == 1100) {
						intStackPtr -= 2;
						local137.anInt6825 = intStack[intStackPtr];
						if (local137.anInt6825 > local137.anInt6821 - local137.anInt6833) {
							local137.anInt6825 = local137.anInt6821 - local137.anInt6833;
						}
						if (local137.anInt6825 < 0) {
							local137.anInt6825 = 0;
						}
						local137.anInt6849 = intStack[intStackPtr + 1];
						if (local137.anInt6849 > local137.anInt6834 - local137.anInt6805) {
							local137.anInt6849 = local137.anInt6834 - local137.anInt6805;
						}
						if (local137.anInt6849 < 0) {
							local137.anInt6849 = 0;
						}
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							Class189.method4176(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1101) {
						local137.anInt6772 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							Static410.method5271(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1102) {
						local137.aBoolean618 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1103) {
						local137.anInt6851 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1104) {
						local137.anInt6794 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1105) {
						local19 = intStack[--intStackPtr];
						if (local137.lb != local19) {
							local137.lb = local19;
							Class4_Sub12_Sub1.method1142(local137);
						}
						if (local137.anInt6865 == -1) {
							Class4_Sub6_Sub22.method3502(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1106) {
						local137.anInt6818 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1107) {
						local137.aBoolean621 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1108) {
						local137.anInt6796 = 1;
						local137.anInt6781 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1109) {
						intStackPtr -= 6;
						local137.anInt6814 = intStack[intStackPtr];
						local137.anInt6826 = intStack[intStackPtr + 1];
						local137.anInt6844 = intStack[intStackPtr + 2];
						local137.anInt6858 = intStack[intStackPtr + 3];
						local137.anInt6843 = intStack[intStackPtr + 4];
						local137.spriteScale = intStack[intStackPtr + 5];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							ModeWhere.method4942(local137.anInt6809);
							OpenGlCubeTextureGenerator.method2667(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1110) {
						local19 = intStack[--intStackPtr];
						if (local137.anInt6788 != local19) {
							local137.anInt6788 = local19;
							local137.anInt6815 = 0;
							local137.anInt6783 = 1;
							local137.anInt6829 = 0;
							Class4_Sub12_Sub1.method1142(local137);
						}
						if (local137.anInt6865 == -1) {
							Class4_Sub6_Sub33.method5045(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1111) {
						local137.aBoolean620 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1112) {
						local1101 = stringStack[--stringStackPtr];
						if (!local1101.equals(local137.aString67)) {
							local137.aString67 = local1101;
							Class4_Sub12_Sub1.method1142(local137);
						}
						if (local137.anInt6865 == -1) {
							Class62.method1608(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1113) {
						local137.anInt6791 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							GlElementsArrayBuffer.method3995(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1114) {
						intStackPtr -= 3;
						local137.anInt6838 = intStack[intStackPtr];
						local137.anInt6803 = intStack[intStackPtr + 1];
						local137.anInt6853 = intStack[intStackPtr + 2];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1115) {
						local137.aBoolean615 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1116) {
						local137.anInt6831 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1117) {
						local137.anInt6778 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1118) {
						local137.aBoolean609 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1119) {
						local137.aBoolean623 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1120) {
						intStackPtr -= 2;
						local137.anInt6821 = intStack[intStackPtr];
						local137.anInt6834 = intStack[intStackPtr + 1];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6840 == 0) {
							Class95_Sub3_Sub1.method4998(local137, false);
						}
						return;
					}
					if (opcode == 1121) {
						intStackPtr -= 2;
						local137.anInt6862 = (short) intStack[intStackPtr];
						local137.anInt6782 = (short) intStack[intStackPtr + 1];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1122) {
						local137.aBoolean614 = intStack[--intStackPtr] == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1123) {
						local137.spriteScale = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						if (local137.anInt6865 == -1) {
							ModeWhere.method4942(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1124) {
						local19 = intStack[--intStackPtr];
						local137.aBoolean610 = local19 == 1;
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1125) {
						intStackPtr -= 2;
						local137.anInt6846 = intStack[intStackPtr];
						local137.anInt6793 = intStack[intStackPtr + 1];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1126) {
						local137.anInt6817 = intStack[--intStackPtr];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					@Pc(1454) Class133 local1454;
					if (opcode == 1127) {
						intStackPtr -= 2;
						local19 = intStack[intStackPtr];
						local25 = intStack[intStackPtr + 1];
						local1454 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
						if (local25 != local1454.anInt4123) {
							local137.method5315(local19, local25);
							return;
						}
						local137.method5303(local19);
						return;
					}
					if (opcode == 1128) {
						local19 = intStack[--intStackPtr];
						local1488 = stringStack[--stringStackPtr];
						local1454 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
						if (!local1454.aString40.equals(local1488)) {
							local137.method5297(local1488, local19);
							return;
						}
						local137.method5303(local19);
						return;
					}
				} else if ((opcode < 1200 || opcode >= 1300) && (opcode < 2200 || opcode >= 2300)) {
					@Pc(2290) int local2290;
					if (opcode >= 1300 && opcode < 1400 || opcode >= 2300 && opcode < 2400) {
						if (opcode >= 2000) {
							opcode -= 1000;
							local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
						} else {
							local137 = hasIntOperand ? activeComponent2 : activeComponent;
						}
						if (opcode == 1300) {
							local19 = intStack[--intStackPtr] - 1;
							if (local19 >= 0 && local19 <= 9) {
								local137.method5305(stringStack[--stringStackPtr], local19);
								return;
							}
							stringStackPtr--;
							return;
						}
						if (opcode == 1301) {
							intStackPtr -= 2;
							local19 = intStack[intStackPtr];
							local25 = intStack[intStackPtr + 1];
							if (local19 == -1 && local25 == -1) {
								local137.depthChildOverride = null;
								return;
							}
							local137.depthChildOverride = EnvironmentSampler.method3230(local19, local25);
							return;
						}
						if (opcode == 1302) {
							local19 = intStack[--intStackPtr];
							if (local19 != GlRectangleTexture.anInt5106 && local19 != Class56.anInt1656 && local19 != ParamTypeList.anInt6419) {
								return;
							}
							local137.anInt6777 = local19;
							return;
						}
						if (opcode == 1303) {
							local137.anInt6795 = intStack[--intStackPtr];
							return;
						}
						if (opcode == 1304) {
							local137.anInt6786 = intStack[--intStackPtr];
							return;
						}
						if (opcode == 1305) {
							local137.aString66 = stringStack[--stringStackPtr];
							return;
						}
						if (opcode == 1306) {
							local137.aString68 = stringStack[--stringStackPtr];
							return;
						}
						if (opcode == 1307) {
							local137.options = null;
							return;
						}
						if (opcode == 1308) {
							local137.anInt6773 = intStack[--intStackPtr];
							local137.anInt6790 = intStack[--intStackPtr];
							return;
						}
						if (opcode == 1309) {
							local19 = intStack[--intStackPtr];
							local25 = intStack[--intStackPtr];
							if (local25 >= 1 && local25 <= 10) {
								local137.method5298(local19, local25 - 1);
							}
							return;
						}
						if (opcode == 1310) {
							local137.aString64 = stringStack[--stringStackPtr];
							return;
						}
						if (opcode == 1311) {
							local137.anInt6845 = intStack[--intStackPtr];
							return;
						}
						if (opcode == 1312 || opcode == 1313) {
							if (opcode == 1312) {
								intStackPtr -= 3;
								local19 = intStack[intStackPtr] - 1;
								local25 = intStack[intStackPtr + 1];
								local353 = intStack[intStackPtr + 2];
								if (local19 < 0 || local19 > 9) {
									throw new RuntimeException("IOR13121313");
								}
							} else {
								intStackPtr -= 2;
								local19 = 10;
								local25 = intStack[intStackPtr];
								local353 = intStack[intStackPtr + 1];
							}
							if (local137.keyTriggeringAction == null) {
								if (local25 == 0) {
									return;
								}
								local137.keyTriggeringAction = new byte[11];
								local137.keyTriggerParams = new byte[11];
								local137.anIntArray523 = new int[11];
							}
							local137.keyTriggeringAction[local19] = (byte) local25;
							if (local25 == 0) {
								local137.aBoolean624 = false;
								for (local2290 = 0; local2290 < local137.keyTriggeringAction.length; local2290++) {
									if (local137.keyTriggeringAction[local2290] != 0) {
										local137.aBoolean624 = true;
										break;
									}
								}
							} else {
								local137.aBoolean624 = true;
							}
							local137.keyTriggerParams[local19] = (byte) local353;
							return;
						}
						if (opcode == 1314) {
							local137.anInt6789 = intStack[--intStackPtr];
							return;
						}
					} else if (opcode >= 1400 && opcode < 1500 || opcode >= 2400 && opcode < 2500) {
						if (opcode >= 2000) {
							opcode -= 1000;
							local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
						} else {
							local137 = hasIntOperand ? activeComponent2 : activeComponent;
						}
						local1101 = stringStack[--stringStackPtr];
						@Pc(2369) int[] local2369 = null;
						if (local1101.length() > 0 && local1101.charAt(local1101.length() - 1) == 'Y') {
							local353 = intStack[--intStackPtr];
							if (local353 > 0) {
								local2369 = new int[local353];
								while (local353-- > 0) {
									local2369[local353] = intStack[--intStackPtr];
								}
							}
							local1101 = local1101.substring(0, local1101.length() - 1);
						}
						@Pc(2421) Object[] local2421 = new Object[local1101.length() + 1];
						for (local2290 = local2421.length - 1; local2290 >= 1; local2290--) {
							if (local1101.charAt(local2290 - 1) == 's') {
								local2421[local2290] = stringStack[--stringStackPtr];
							} else {
								local2421[local2290] = Integer.valueOf(intStack[--intStackPtr]);
							}
						}
						local56 = intStack[--intStackPtr];
						if (local56 == -1) {
							local2421 = null;
						} else {
							local2421[0] = Integer.valueOf(local56);
						}
						if (opcode == 1400) {
							local137.anObjectArray12 = local2421;
						} else if (opcode == 1401) {
							local137.anObjectArray22 = local2421;
						} else if (opcode == 1402) {
							local137.anObjectArray33 = local2421;
						} else if (opcode == 1403) {
							local137.anObjectArray32 = local2421;
						} else if (opcode == 1404) {
							local137.anObjectArray9 = local2421;
						} else if (opcode == 1405) {
							local137.anObjectArray26 = local2421;
						} else if (opcode == 1406) {
							local137.anObjectArray25 = local2421;
						} else if (opcode == 1407) {
							local137.anObjectArray18 = local2421;
							local137.anIntArray515 = local2369;
						} else if (opcode == 1408) {
							local137.anObjectArray8 = local2421;
						} else if (opcode == 1409) {
							local137.anObjectArray15 = local2421;
						} else if (opcode == 1410) {
							local137.anObjectArray16 = local2421;
						} else if (opcode == 1411) {
							local137.anObjectArray23 = local2421;
						} else if (opcode == 1412) {
							local137.anObjectArray27 = local2421;
						} else if (opcode == 1414) {
							local137.anObjectArray19 = local2421;
							local137.anIntArray520 = local2369;
						} else if (opcode == 1415) {
							local137.anObjectArray36 = local2421;
							local137.anIntArray522 = local2369;
						} else if (opcode == 1416) {
							local137.anObjectArray21 = local2421;
						} else if (opcode == 1417) {
							local137.anObjectArray24 = local2421;
						} else if (opcode == 1418) {
							local137.anObjectArray11 = local2421;
						} else if (opcode == 1419) {
							local137.anObjectArray20 = local2421;
						} else if (opcode == 1420) {
							local137.anObjectArray35 = local2421;
						} else if (opcode == 1421) {
							local137.anObjectArray31 = local2421;
						} else if (opcode == 1422) {
							local137.anObjectArray14 = local2421;
						} else if (opcode == 1423) {
							local137.anObjectArray29 = local2421;
						} else if (opcode == 1424) {
							local137.anObjectArray10 = local2421;
						} else if (opcode == 1425) {
							local137.anObjectArray13 = local2421;
						} else if (opcode == 1426) {
							local137.anObjectArray6 = local2421;
						} else if (opcode == 1427) {
							local137.anObjectArray7 = local2421;
						} else if (opcode == 1428) {
							local137.anObjectArray30 = local2421;
							local137.anIntArray518 = local2369;
						} else if (opcode == 1429) {
							local137.anObjectArray34 = local2421;
							local137.anIntArray517 = local2369;
						} else if (opcode == 1430) {
							local137.anObjectArray17 = local2421;
						}
						local137.aBoolean612 = true;
						return;
					} else if (opcode < 1600) {
						local137 = hasIntOperand ? activeComponent2 : activeComponent;
						if (opcode == 1500) {
							intStack[intStackPtr++] = local137.anInt6836;
							return;
						}
						if (opcode == 1501) {
							intStack[intStackPtr++] = local137.anInt6859;
							return;
						}
						if (opcode == 1502) {
							intStack[intStackPtr++] = local137.anInt6833;
							return;
						}
						if (opcode == 1503) {
							intStack[intStackPtr++] = local137.anInt6805;
							return;
						}
						if (opcode == 1504) {
							intStack[intStackPtr++] = local137.hidden ? 1 : 0;
							return;
						}
						if (opcode == 1505) {
							intStack[intStackPtr++] = local137.anInt6850;
							return;
						}
						if (opcode == 1506) {
							local158 = Class10.method125(local137);
							intStack[intStackPtr++] = local158 == null ? -1 : local158.anInt6809;
							return;
						}
					} else {
						@Pc(3017) Class133 local3017;
						if (opcode < 1700) {
							local137 = hasIntOperand ? activeComponent2 : activeComponent;
							if (opcode == 1600) {
								intStack[intStackPtr++] = local137.anInt6825;
								return;
							}
							if (opcode == 1601) {
								intStack[intStackPtr++] = local137.anInt6849;
								return;
							}
							if (opcode == 1602) {
								stringStack[stringStackPtr++] = local137.aString67;
								return;
							}
							if (opcode == 1603) {
								intStack[intStackPtr++] = local137.anInt6821;
								return;
							}
							if (opcode == 1604) {
								intStack[intStackPtr++] = local137.anInt6834;
								return;
							}
							if (opcode == 1605) {
								intStack[intStackPtr++] = local137.spriteScale;
								return;
							}
							if (opcode == 1606) {
								intStack[intStackPtr++] = local137.anInt6844;
								return;
							}
							if (opcode == 1607) {
								intStack[intStackPtr++] = local137.anInt6843;
								return;
							}
							if (opcode == 1608) {
								intStack[intStackPtr++] = local137.anInt6858;
								return;
							}
							if (opcode == 1609) {
								intStack[intStackPtr++] = local137.anInt6851;
								return;
							}
							if (opcode == 1610) {
								intStack[intStackPtr++] = local137.anInt6814;
								return;
							}
							if (opcode == 1611) {
								intStack[intStackPtr++] = local137.anInt6826;
								return;
							}
							if (opcode == 1612) {
								intStack[intStackPtr++] = local137.lb;
								return;
							}
							if (opcode == 1613) {
								local19 = intStack[--intStackPtr];
								local3017 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
								if (local3017.method3350()) {
									stringStack[stringStackPtr++] = local137.method5314(local3017.aString40, local19);
								} else {
									intStack[intStackPtr++] = local137.method5299(local3017.anInt4123, local19);
								}
							}
						} else if (opcode < 1800) {
							local137 = hasIntOperand ? activeComponent2 : activeComponent;
							if (opcode == 1700) {
								intStack[intStackPtr++] = local137.anInt6779;
								return;
							}
							if (opcode == 1701) {
								if (local137.anInt6779 != -1) {
									intStack[intStackPtr++] = local137.anInt6832;
									return;
								}
								intStack[intStackPtr++] = 0;
								return;
							}
							if (opcode == 1702) {
								intStack[intStackPtr++] = local137.anInt6865;
								return;
							}
						} else if (opcode < 1900) {
							local137 = hasIntOperand ? activeComponent2 : activeComponent;
							if (opcode == 1800) {
								intStack[intStackPtr++] = client.method900(local137).method3911();
								return;
							}
							if (opcode == 1801) {
								local19 = intStack[--intStackPtr];
								local19--;
								if (local137.options != null && local19 < local137.options.length && local137.options[local19] != null) {
									stringStack[stringStackPtr++] = local137.options[local19];
									return;
								}
								stringStack[stringStackPtr++] = "";
								return;
							}
							if (opcode == 1802) {
								if (local137.aString66 == null) {
									stringStack[stringStackPtr++] = "";
									return;
								}
								stringStack[stringStackPtr++] = local137.aString66;
								return;
							}
						} else if (opcode < 2000 || opcode >= 2900 && opcode < 3000) {
							if (opcode >= 2000) {
								local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
								opcode -= 1000;
							} else {
								local137 = hasIntOperand ? activeComponent2 : activeComponent;
							}
							if (hookRequestCount >= 10) {
								throw new RuntimeException("C29xx-1");
							}
							if (opcode == 1927) {
								if (local137.anObjectArray7 == null) {
									return;
								}
								@Pc(3254) HookRequest local3254 = new HookRequest();
								local3254.source = local137;
								local3254.params = local137.anObjectArray7;
								local3254.id = hookRequestCount + 1;
								TextureOpMandelbrot.aClass183_35.method4137(local3254);
								return;
							}
						} else if (opcode < 2600) {
							local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
							if (opcode == 2500) {
								intStack[intStackPtr++] = local137.anInt6836;
								return;
							}
							if (opcode == 2501) {
								intStack[intStackPtr++] = local137.anInt6859;
								return;
							}
							if (opcode == 2502) {
								intStack[intStackPtr++] = local137.anInt6833;
								return;
							}
							if (opcode == 2503) {
								intStack[intStackPtr++] = local137.anInt6805;
								return;
							}
							if (opcode == 2504) {
								intStack[intStackPtr++] = local137.hidden ? 1 : 0;
								return;
							}
							if (opcode == 2505) {
								intStack[intStackPtr++] = local137.anInt6850;
								return;
							}
							if (opcode == 1506) {
								local158 = Class10.method125(local137);
								intStack[intStackPtr++] = local158 == null ? -1 : local158.anInt6809;
								return;
							}
						} else if (opcode < 2700) {
							local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
							if (opcode == 2600) {
								intStack[intStackPtr++] = local137.anInt6825;
								return;
							}
							if (opcode == 2601) {
								intStack[intStackPtr++] = local137.anInt6849;
								return;
							}
							if (opcode == 2602) {
								stringStack[stringStackPtr++] = local137.aString67;
								return;
							}
							if (opcode == 2603) {
								intStack[intStackPtr++] = local137.anInt6821;
								return;
							}
							if (opcode == 2604) {
								intStack[intStackPtr++] = local137.anInt6834;
								return;
							}
							if (opcode == 2605) {
								intStack[intStackPtr++] = local137.spriteScale;
								return;
							}
							if (opcode == 2606) {
								intStack[intStackPtr++] = local137.anInt6844;
								return;
							}
							if (opcode == 2607) {
								intStack[intStackPtr++] = local137.anInt6843;
								return;
							}
							if (opcode == 2608) {
								intStack[intStackPtr++] = local137.anInt6858;
								return;
							}
							if (opcode == 2609) {
								intStack[intStackPtr++] = local137.anInt6851;
								return;
							}
							if (opcode == 2610) {
								intStack[intStackPtr++] = local137.anInt6814;
								return;
							}
							if (opcode == 2611) {
								intStack[intStackPtr++] = local137.anInt6826;
								return;
							}
							if (opcode == 2612) {
								intStack[intStackPtr++] = local137.lb;
								return;
							}
						} else {
							@Pc(3751) Class4_Sub43 local3751;
							@Pc(3649) Class4_Sub43 local3649;
							if (opcode < 2800) {
								if (opcode == 2700) {
									local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
									intStack[intStackPtr++] = local137.anInt6779;
									return;
								}
								if (opcode == 2701) {
									local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
									if (local137.anInt6779 != -1) {
										intStack[intStackPtr++] = local137.anInt6832;
										return;
									}
									intStack[intStackPtr++] = 0;
									return;
								}
								if (opcode == 2702) {
									local13 = intStack[--intStackPtr];
									local3649 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local13);
									if (local3649 != null) {
										intStack[intStackPtr++] = 1;
										return;
									}
									intStack[intStackPtr++] = 0;
									return;
								}
								if (opcode == 2703) {
									local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
									if (local137.slotChildren == null) {
										intStack[intStackPtr++] = 0;
										return;
									}
									local19 = local137.slotChildren.length;
									for (local25 = 0; local25 < local137.slotChildren.length; local25++) {
										if (local137.slotChildren[local25] == null) {
											local19 = local25;
											break;
										}
									}
									intStack[intStackPtr++] = local19;
									return;
								}
								if (opcode == 2704 || opcode == 2705) {
									intStackPtr -= 2;
									local13 = intStack[intStackPtr];
									local19 = intStack[intStackPtr + 1];
									local3751 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local13);
									if (local3751 != null && local3751.anInt6979 == local19) {
										intStack[intStackPtr++] = 1;
										return;
									}
									intStack[intStackPtr++] = 0;
									return;
								}
							} else if (opcode < 2900) {
								local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
								if (opcode == 2800) {
									intStack[intStackPtr++] = client.method900(local137).method3911();
									return;
								}
								if (opcode == 2801) {
									local19 = intStack[--intStackPtr];
									local19--;
									if (local137.options != null && local19 < local137.options.length && local137.options[local19] != null) {
										stringStack[stringStackPtr++] = local137.options[local19];
										return;
									}
									stringStack[stringStackPtr++] = "";
									return;
								}
								if (opcode == 2802) {
									if (local137.aString66 == null) {
										stringStack[stringStackPtr++] = "";
										return;
									}
									stringStack[stringStackPtr++] = local137.aString66;
									return;
								}
							} else {
								@Pc(3888) String local3888;
								if (opcode < 3200) {
									if (opcode == 3100) {
										local3888 = stringStack[--stringStackPtr];
										Class249.method5323(local3888);
										return;
									}
									if (opcode == 3101) {
										intStackPtr -= 2;
										Class4_Sub3.method185(intStack[intStackPtr + 1], Static1.aClass16_Sub1_Sub5_Sub1_1, intStack[intStackPtr]);
										return;
									}
									if (opcode == 3103) {
										Static449.method5653();
										return;
									}
									if (opcode == 3104) {
										local3888 = stringStack[--stringStackPtr];
										local19 = 0;
										if (Class60.method1590(local3888)) {
											local19 = ParticleProducerType.method3113(local3888);
										}
										PingWorker.method5476(EntityNodeList.aClass215_35);
										ParticleNode.aClass4_Sub12_Sub1_5.method2531(local19);
										return;
									}
									if (opcode == 3105) {
										local3888 = stringStack[--stringStackPtr];
										PingWorker.method5476(Static242.aClass215_49);
										ParticleNode.aClass4_Sub12_Sub1_5.method2551(local3888.length() + 1);
										ParticleNode.aClass4_Sub12_Sub1_5.method2518(local3888);
										return;
									}
									if (opcode == 3106) {
										local3888 = stringStack[--stringStackPtr];
										PingWorker.method5476(Class149.aClass215_50);
										ParticleNode.aClass4_Sub12_Sub1_5.method2551(local3888.length() + 1);
										ParticleNode.aClass4_Sub12_Sub1_5.method2518(local3888);
										return;
									}
									if (opcode == 3107) {
										local13 = intStack[--intStackPtr];
										local1101 = stringStack[--stringStackPtr];
										MapSpriteTypeList.method5593(local1101, local13);
										return;
									}
									if (opcode == 3108) {
										intStackPtr -= 3;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local25 = intStack[intStackPtr + 2];
										local35 = ParticleBinaryTree.method5121(local25);
										Class4_Sub6_Sub7.method1680(local19, local35, local13);
										return;
									}
									if (opcode == 3109) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local210 = hasIntOperand ? activeComponent2 : activeComponent;
										Class4_Sub6_Sub7.method1680(local19, local210, local13);
										return;
									}
									if (opcode == 3110) {
										local13 = intStack[--intStackPtr];
										PingWorker.method5476(ParticleBinaryTree.aClass215_86);
										ParticleNode.aClass4_Sub12_Sub1_5.method2524(local13);
										return;
									}
									if (opcode == 3111) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local3751 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local13);
										if (local3751 != null) {
											Class62.method1606(local3751.anInt6979 != local19, local3751, true);
										}
										IdkType.method4092(local19, 3, true, local13);
										return;
									}
									if (opcode == 3112) {
										intStackPtr--;
										local13 = intStack[intStackPtr];
										local3649 = (Class4_Sub43) MusicPatch.aClass102_29.method2700((long) local13);
										if (local3649 != null && local3649.anInt6981 == 3) {
											Class62.method1606(true, local3649, true);
										}
										return;
									}
									if (opcode == 3113) {
										MECTypeList.method4130(stringStack[--stringStackPtr]);
										return;
									}
									if (opcode == 3114) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local1488 = stringStack[--stringStackPtr];
										Class83.method2268(local19, local13, local1488, "", "");
										return;
									}
								} else if (opcode < 3300) {
									if (opcode == 3200) {
										intStackPtr -= 3;
										Huffman.method2934(intStack[intStackPtr], intStack[intStackPtr + 1], 255, intStack[intStackPtr + 2]);
										return;
									}
									if (opcode == 3201) {
										Class4_Sub6_Sub38.method5456(intStack[--intStackPtr], 50, 255);
										return;
									}
									if (opcode == 3202) {
										intStackPtr -= 2;
										Static411.method5274(255, intStack[intStackPtr + 1], intStack[intStackPtr]);
										return;
									}
									if (opcode == 3203) {
										intStackPtr -= 4;
										Huffman.method2934(intStack[intStackPtr], intStack[intStackPtr + 1], intStack[intStackPtr + 3], intStack[intStackPtr + 2]);
										return;
									}
									if (opcode == 3204) {
										intStackPtr -= 3;
										Class4_Sub6_Sub38.method5456(intStack[intStackPtr], intStack[intStackPtr + 2], intStack[intStackPtr + 1]);
										return;
									}
									if (opcode == 3205) {
										intStackPtr -= 3;
										Static411.method5274(intStack[intStackPtr + 2], intStack[intStackPtr + 1], intStack[intStackPtr]);
										return;
									}
									if (opcode == 3206) {
										intStackPtr -= 4;
										Class164.method3912(intStack[intStackPtr + 1], intStack[intStackPtr + 2], intStack[intStackPtr], intStack[intStackPtr + 3]);
										return;
									}
								} else if (opcode < 3400) {
									if (opcode == 3300) {
										intStack[intStackPtr++] = Shadow.anInt5323;
										return;
									}
									if (opcode == 3301) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class4_Sub6_Sub24.method3713(false, local19, local13);
										return;
									}
									if (opcode == 3302) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = TileMessage.method5678(local19, local13, false);
										return;
									}
									if (opcode == 3303) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class50.method2853(false, local13, local19);
										return;
									}
									if (opcode == 3304) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = LRUCache.aClass75_1.method2079(local13).anInt207;
										return;
									}
									if (opcode == 3305) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Static395.anIntArray502[local13];
										return;
									}
									if (opcode == 3306) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = ParticleProducerType.anIntArray258[local13];
										return;
									}
									if (opcode == 3307) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class103.anIntArray207[local13];
										return;
									}
									if (opcode == 3308) {
										@Pc(4605) byte local4605 = Static1.aClass16_Sub1_Sub5_Sub1_1.aByte82;
										local19 = (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6893 >> 7) + Class50_Sub1.anInt3453;
										local25 = (Static1.aClass16_Sub1_Sub5_Sub1_1.anInt6892 >> 7) + OpenGLTextureCache.anInt1771;
										intStack[intStackPtr++] = (local4605 << 28) + (local19 << 14) + local25;
										return;
									}
									if (opcode == 3309) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = local13 >> 14 & 0x3FFF;
										return;
									}
									if (opcode == 3310) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = local13 >> 28;
										return;
									}
									if (opcode == 3311) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = local13 & 0x3FFF;
										return;
									}
									if (opcode == 3312) {
										intStack[intStackPtr++] = MusicPatch.aBoolean506 ? 1 : 0;
										return;
									}
									if (opcode == 3313) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class4_Sub6_Sub24.method3713(true, local19, local13);
										return;
									}
									if (opcode == 3314) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = TileMessage.method5678(local19, local13, true);
										return;
									}
									if (opcode == 3315) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class50.method2853(true, local13, local19);
										return;
									}
									if (opcode == 3316) {
										if (GlToolkit.activeChildCount >= 2) {
											intStack[intStackPtr++] = GlToolkit.activeChildCount;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3317) {
										intStack[intStackPtr++] = Class120.anInt5353;
										return;
									}
									if (opcode == 3318) {
										intStack[intStackPtr++] = MapSpriteType.anInt1028;
										return;
									}
									if (opcode == 3321) {
										intStack[intStackPtr++] = DequeIterator.anInt1847;
										return;
									}
									if (opcode == 3322) {
										intStack[intStackPtr++] = Static17.anInt223;
										return;
									}
									if (opcode == 3323) {
										if (Class79.anInt6262 >= 5 && Class79.anInt6262 <= 9) {
											intStack[intStackPtr++] = 1;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3324) {
										if (Class79.anInt6262 >= 5 && Class79.anInt6262 <= 9) {
											intStack[intStackPtr++] = Class79.anInt6262;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3325) {
										intStack[intStackPtr++] = Class4_Sub29.aBoolean423 ? 1 : 0;
										return;
									}
									if (opcode == 3326) {
										intStack[intStackPtr++] = Static1.aClass16_Sub1_Sub5_Sub1_1.anInt4345;
										return;
									}
									if (opcode == 3327) {
										intStack[intStackPtr++] = Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.female ? 1 : 0;
										return;
									}
									if (opcode == 3328) {
										intStack[intStackPtr++] = Class16.aBoolean628 && !MemoryPool.aBoolean443 ? 1 : 0;
										return;
									}
									if (opcode == 3329) {
										intStack[intStackPtr++] = Static308.aBoolean486 ? 1 : 0;
										return;
									}
									if (opcode == 3330) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class4_Sub6_Sub35.method5212(local13);
										return;
									}
									if (opcode == 3331) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class27_Sub3.method3185(false, local13, local19);
										return;
									}
									if (opcode == 3332) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = Class27_Sub3.method3185(true, local13, local19);
										return;
									}
									if (opcode == 3333) {
										intStack[intStackPtr++] = ChatFilter.anInt3177;
										return;
									}
									if (opcode == 3335) {
										intStack[intStackPtr++] = NativeBillboardFace.anInt3423;
										return;
									}
									if (opcode == 3336) {
										intStackPtr -= 4;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local25 = intStack[intStackPtr + 2];
										local353 = intStack[intStackPtr + 3];
										local13 += local19 << 14;
										local13 += local25 << 28;
										local13 += local353;
										intStack[intStackPtr++] = local13;
										return;
									}
									if (opcode == 3337) {
										intStack[intStackPtr++] = GlCubeTexture.anInt3259;
										return;
									}
									if (opcode == 3338) {
										intStack[intStackPtr++] = FluType.method4958();
										return;
									}
								} else if (opcode < 3500) {
									@Pc(5230) EnumType local5230;
									if (opcode == 3400) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local5230 = PingRequest.aClass158_1.method3790(local13);
										stringStack[stringStackPtr++] = local5230.getString(local19);
										return;
									}
									if (opcode == 3408) {
										intStackPtr -= 4;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local25 = intStack[intStackPtr + 2];
										local353 = intStack[intStackPtr + 3];
										@Pc(5276) EnumType local5276 = PingRequest.aClass158_1.method3790(local25);
										if (local5276.keyType == local13 && local5276.valueType == local19) {
											if (local19 == 115) {
												stringStack[stringStackPtr++] = local5276.getString(local353);
												return;
											}
											intStack[intStackPtr++] = local5276.getInt(local353);
											return;
										}
										throw new RuntimeException("C3408-1");
									}
									if (opcode == 3409) {
										intStackPtr -= 3;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local25 = intStack[intStackPtr + 2];
										if (local19 == -1) {
											throw new RuntimeException("C3409-2");
										}
										@Pc(5352) EnumType local5352 = PingRequest.aClass158_1.method3790(local19);
										if (local5352.valueType != local13) {
											throw new RuntimeException("C3409-1");
										}
										intStack[intStackPtr++] = local5352.hasOutput(local25) ? 1 : 0;
										return;
									}
									if (opcode == 3410) {
										local13 = intStack[--intStackPtr];
										local1101 = stringStack[--stringStackPtr];
										if (local13 == -1) {
											throw new RuntimeException("C3410-2");
										}
										local5230 = PingRequest.aClass158_1.method3790(local13);
										if (local5230.valueType != 's') {
											throw new RuntimeException("C3410-1");
										}
										intStack[intStackPtr++] = local5230.hasOutputString(local1101) ? 1 : 0;
										return;
									}
									if (opcode == 3411) {
										local13 = intStack[--intStackPtr];
										@Pc(5450) EnumType local5450 = PingRequest.aClass158_1.method3790(local13);
										intStack[intStackPtr++] = local5450.aClass102_32.method2698();
										return;
									}
								} else if (opcode < 3700) {
									if (opcode == 3600) {
										if (Model.anInt6277 == 0) {
											intStack[intStackPtr++] = -2;
											return;
										}
										if (Model.anInt6277 == 1) {
											intStack[intStackPtr++] = -1;
											return;
										}
										intStack[intStackPtr++] = Class4_Sub10.anInt2346;
										return;
									}
									if (opcode == 3601) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 == 2 && local13 < Class4_Sub10.anInt2346) {
											stringStack[stringStackPtr++] = Mesh.aStringArray14[local13];
											if (Static57.aStringArray6[local13] != null) {
												stringStack[stringStackPtr++] = Static57.aStringArray6[local13];
												return;
											}
											stringStack[stringStackPtr++] = "";
											return;
										}
										stringStack[stringStackPtr++] = "";
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3602) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 == 2 && local13 < Class4_Sub10.anInt2346) {
											intStack[intStackPtr++] = Class9.anIntArray536[local13];
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3603) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 == 2 && local13 < Class4_Sub10.anInt2346) {
											intStack[intStackPtr++] = Class4_Sub6_Sub26.anIntArray339[local13];
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3604) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										CacheableNodeByteArray.method3544(local3888, local19);
										return;
									}
									if (opcode == 3605) {
										local3888 = stringStack[--stringStackPtr];
										CacheableNodeQueue.method3478(local3888);
										return;
									}
									if (opcode == 3606) {
										local3888 = stringStack[--stringStackPtr];
										PcmPlayerThread.method5070(local3888);
										return;
									}
									if (opcode == 3607) {
										local3888 = stringStack[--stringStackPtr];
										LocType.method4595(false, local3888);
										return;
									}
									if (opcode == 3608) {
										local3888 = stringStack[--stringStackPtr];
										Resampler.method5549(local3888);
										return;
									}
									if (opcode == 3609) {
										local3888 = stringStack[--stringStackPtr];
										if (local3888.startsWith("<img=0>") || local3888.startsWith("<img=1>")) {
											local3888 = local3888.substring(7);
										}
										intStack[intStackPtr++] = PositionedGfx.method2458(local3888) ? 1 : 0;
										return;
									}
									if (opcode == 3610) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 == 2 && local13 < Class4_Sub10.anInt2346) {
											stringStack[stringStackPtr++] = Static433.aStringArray38[local13];
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3611) {
										if (Class4_Sub1_Sub6_Sub2.aString39 != null) {
											stringStack[stringStackPtr++] = Static347.method4680(Class4_Sub1_Sub6_Sub2.aString39);
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3612) {
										if (Class4_Sub1_Sub6_Sub2.aString39 != null) {
											intStack[intStackPtr++] = Class95_Sub2.anInt6410;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3613) {
										local13 = intStack[--intStackPtr];
										if (Class4_Sub1_Sub6_Sub2.aString39 != null && local13 < Class95_Sub2.anInt6410) {
											stringStack[stringStackPtr++] = GameShell.aClass15Array2[local13].aString8;
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3614) {
										local13 = intStack[--intStackPtr];
										if (Class4_Sub1_Sub6_Sub2.aString39 != null && local13 < Class95_Sub2.anInt6410) {
											intStack[intStackPtr++] = GameShell.aClass15Array2[local13].anInt272;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3615) {
										local13 = intStack[--intStackPtr];
										if (Class4_Sub1_Sub6_Sub2.aString39 != null && local13 < Class95_Sub2.anInt6410) {
											intStack[intStackPtr++] = GameShell.aClass15Array2[local13].aByte1;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3616) {
										intStack[intStackPtr++] = Class267.aByte103;
										return;
									}
									if (opcode == 3617) {
										local3888 = stringStack[--stringStackPtr];
										JavaBillboardAttributes.method3853(local3888);
										return;
									}
									if (opcode == 3618) {
										intStack[intStackPtr++] = Class103.aByte18;
										return;
									}
									if (opcode == 3619) {
										local3888 = stringStack[--stringStackPtr];
										Class9_Sub4.method1601(local3888);
										return;
									}
									if (opcode == 3620) {
										Class79_Sub1.method4872();
										return;
									}
									if (opcode == 3621) {
										if (Model.anInt6277 == 0) {
											intStack[intStackPtr++] = -1;
											return;
										}
										intStack[intStackPtr++] = Component.anInt6823;
										return;
									}
									if (opcode == 3622) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 != 0 && local13 < Component.anInt6823) {
											stringStack[stringStackPtr++] = GLBillowTextureData.aStringArray27[local13];
											if (Class4_Sub1_Sub17.aStringArray26[local13] != null) {
												stringStack[stringStackPtr++] = Class4_Sub1_Sub17.aStringArray26[local13];
												return;
											}
											stringStack[stringStackPtr++] = "";
											return;
										}
										stringStack[stringStackPtr++] = "";
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3623) {
										local3888 = stringStack[--stringStackPtr];
										if (local3888.startsWith("<img=0>") || local3888.startsWith("<img=1>")) {
											local3888 = local3888.substring(7);
										}
										intStack[intStackPtr++] = ParticleList.method3549(local3888) ? 1 : 0;
										return;
									}
									if (opcode == 3624) {
										local13 = intStack[--intStackPtr];
										if (GameShell.aClass15Array2 != null && local13 < Class95_Sub2.anInt6410 && GameShell.aClass15Array2[local13].aString7.equalsIgnoreCase(Static1.aClass16_Sub1_Sub5_Sub1_1.aString44)) {
											intStack[intStackPtr++] = 1;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3625) {
										if (Static395.aString63 != null) {
											stringStack[stringStackPtr++] = Static395.aString63;
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3626) {
										local13 = intStack[--intStackPtr];
										if (Class4_Sub1_Sub6_Sub2.aString39 != null && local13 < Class95_Sub2.anInt6410) {
											stringStack[stringStackPtr++] = GameShell.aClass15Array2[local13].aString5;
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3627) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 == 2 && local13 >= 0 && local13 < Class4_Sub10.anInt2346) {
											intStack[intStackPtr++] = Class4_Sub43.aBooleanArray23[local13] ? 1 : 0;
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 3628) {
										local3888 = stringStack[--stringStackPtr];
										if (local3888.startsWith("<img=0>") || local3888.startsWith("<img=1>")) {
											local3888 = local3888.substring(7);
										}
										intStack[intStackPtr++] = Class16_Sub2_Sub1.method573(local3888);
										return;
									}
									if (opcode == 3629) {
										intStack[intStackPtr++] = ModelNormalsGL.anInt4373;
										return;
									}
									if (opcode == 3630) {
										local3888 = stringStack[--stringStackPtr];
										LocType.method4595(true, local3888);
										return;
									}
									if (opcode == 3631) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = EffectManagerGL.aBooleanArray20[local13] ? 1 : 0;
										return;
									}
									if (opcode == 3632) {
										local13 = intStack[--intStackPtr];
										if (Class4_Sub1_Sub6_Sub2.aString39 != null && local13 < Class95_Sub2.anInt6410) {
											stringStack[stringStackPtr++] = GameShell.aClass15Array2[local13].aString7;
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 3633) {
										local13 = intStack[--intStackPtr];
										if (Model.anInt6277 != 0 && local13 < Component.anInt6823) {
											stringStack[stringStackPtr++] = Static102.aStringArray7[local13];
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
								} else if (opcode < 4000) {
									if (opcode == 3903) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].method668();
										return;
									}
									if (opcode == 3904) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].anInt660;
										return;
									}
									if (opcode == 3905) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].anInt663;
										return;
									}
									if (opcode == 3906) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].anInt664;
										return;
									}
									if (opcode == 3907) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].anInt658;
										return;
									}
									if (opcode == 3908) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class243.aClass28Array1[local13].anInt669;
										return;
									}
									if (opcode == 3910) {
										local13 = intStack[--intStackPtr];
										local19 = Class243.aClass28Array1[local13].method667();
										intStack[intStackPtr++] = local19 == 0 ? 1 : 0;
										return;
									}
									if (opcode == 3911) {
										local13 = intStack[--intStackPtr];
										local19 = Class243.aClass28Array1[local13].method667();
										intStack[intStackPtr++] = local19 == 2 ? 1 : 0;
										return;
									}
									if (opcode == 3912) {
										local13 = intStack[--intStackPtr];
										local19 = Class243.aClass28Array1[local13].method667();
										intStack[intStackPtr++] = local19 == 5 ? 1 : 0;
										return;
									}
									if (opcode == 3913) {
										local13 = intStack[--intStackPtr];
										local19 = Class243.aClass28Array1[local13].method667();
										intStack[intStackPtr++] = local19 == 1 ? 1 : 0;
										return;
									}
								} else if (opcode < 4100) {
									if (opcode == 4000) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 + local19;
										return;
									}
									if (opcode == 4001) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 - local19;
										return;
									}
									if (opcode == 4002) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 * local19;
										return;
									}
									if (opcode == 4003) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 / local19;
										return;
									}
									if (opcode == 4004) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = (int) (Math.random() * (double) local13);
										return;
									}
									if (opcode == 4005) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = (int) (Math.random() * (double) (local13 + 1));
										return;
									}
									if (opcode == 4006) {
										intStackPtr -= 5;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local25 = intStack[intStackPtr + 2];
										local353 = intStack[intStackPtr + 3];
										local2290 = intStack[intStackPtr + 4];
										intStack[intStackPtr++] = local13 + (local19 - local13) * (local2290 - local25) / (local353 - local25);
										return;
									}
									@Pc(6960) long local6960;
									@Pc(6953) long local6953;
									if (opcode == 4007) {
										intStackPtr -= 2;
										local6953 = intStack[intStackPtr];
										local6960 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = (int) (local6953 + local6953 * local6960 / 100L);
										return;
									}
									if (opcode == 4008) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 | 0x1 << local19;
										return;
									}
									if (opcode == 4009) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 & -(0x1 << local19) - 1;
										return;
									}
									if (opcode == 4010) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = (local13 & 0x1 << local19) == 0 ? 0 : 1;
										return;
									}
									if (opcode == 4011) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 % local19;
										return;
									}
									if (opcode == 4012) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										if (local13 == 0) {
											intStack[intStackPtr++] = 0;
											return;
										}
										intStack[intStackPtr++] = (int) Math.pow((double) local13, (double) local19);
										return;
									}
									if (opcode == 4013) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										if (local13 == 0) {
											intStack[intStackPtr++] = 0;
											return;
										}
										if (local19 == 0) {
											intStack[intStackPtr++] = Integer.MAX_VALUE;
											return;
										}
										intStack[intStackPtr++] = (int) Math.pow((double) local13, 1.0D / (double) local19);
										return;
									}
									if (opcode == 4014) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 & local19;
										return;
									}
									if (opcode == 4015) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 | local19;
										return;
									}
									if (opcode == 4016) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 < local19 ? local13 : local19;
										return;
									}
									if (opcode == 4017) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local13 > local19 ? local13 : local19;
										return;
									}
									if (opcode == 4018) {
										intStackPtr -= 3;
										local6953 = intStack[intStackPtr];
										local6960 = intStack[intStackPtr + 1];
										@Pc(7341) long local7341 = (long) intStack[intStackPtr + 2];
										intStack[intStackPtr++] = (int) (local6953 * local7341 / local6960);
										return;
									}
								} else if (opcode < 4200) {
									if (opcode == 4100) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										stringStack[stringStackPtr++] = local3888 + local19;
										return;
									}
									if (opcode == 4101) {
										stringStackPtr -= 2;
										local3888 = stringStack[stringStackPtr];
										local1101 = stringStack[stringStackPtr + 1];
										stringStack[stringStackPtr++] = local3888 + local1101;
										return;
									}
									if (opcode == 4102) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										stringStack[stringStackPtr++] = local3888 + Class154.method3691(local19);
										return;
									}
									if (opcode == 4103) {
										local3888 = stringStack[--stringStackPtr];
										stringStack[stringStackPtr++] = local3888.toLowerCase();
										return;
									}
									if (opcode == 4104) {
										stringStack[stringStackPtr++] = runedayToDateString(intStack[--intStackPtr]);
										return;
									}
									if (opcode == 4105) {
										stringStackPtr -= 2;
										local3888 = stringStack[stringStackPtr];
										local1101 = stringStack[stringStackPtr + 1];
										if (Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel != null && Static1.aClass16_Sub1_Sub5_Sub1_1.playerModel.female) {
											stringStack[stringStackPtr++] = local1101;
											return;
										}
										stringStack[stringStackPtr++] = local3888;
										return;
									}
									if (opcode == 4106) {
										local13 = intStack[--intStackPtr];
										stringStack[stringStackPtr++] = Integer.toString(local13);
										return;
									}
									if (opcode == 4107) {
										stringStackPtr -= 2;
										intStack[intStackPtr++] = EmissiveTriangle.method698(stringStack[stringStackPtr + 1], stringStack[stringStackPtr], NativeBillboardFace.anInt3423);
										return;
									}
									@Pc(7624) com.jagex.game.runetek5.graphics.font.FontMetrics local7624;
									if (opcode == 4108) {
										local3888 = stringStack[--stringStackPtr];
										intStackPtr -= 2;
										local19 = intStack[intStackPtr];
										local25 = intStack[intStackPtr + 1];
										local7624 = ParticleList.method3546(local25, Static209.aClass76_48);
										intStack[intStackPtr++] = local7624.method2347(PingWorker.aClass57Array18, local19, local3888);
										return;
									}
									if (opcode == 4109) {
										local3888 = stringStack[--stringStackPtr];
										intStackPtr -= 2;
										local19 = intStack[intStackPtr];
										local25 = intStack[intStackPtr + 1];
										local7624 = ParticleList.method3546(local25, Static209.aClass76_48);
										intStack[intStackPtr++] = local7624.method2349(local3888, local19, PingWorker.aClass57Array18);
										return;
									}
									if (opcode == 4110) {
										stringStackPtr -= 2;
										local3888 = stringStack[stringStackPtr];
										local1101 = stringStack[stringStackPtr + 1];
										if (intStack[--intStackPtr] == 1) {
											stringStack[stringStackPtr++] = local3888;
											return;
										}
										stringStack[stringStackPtr++] = local1101;
										return;
									}
									if (opcode == 4111) {
										local3888 = stringStack[--stringStackPtr];
										stringStack[stringStackPtr++] = Class16_Sub1_Sub1.method297(local3888);
										return;
									}
									if (opcode == 4112) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										if (local19 == -1) {
											throw new RuntimeException("null char");
										}
										stringStack[stringStackPtr++] = local3888 + (char) local19;
										return;
									}
									if (opcode == 4113) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class16.method5334((char) local13) ? 1 : 0;
										return;
									}
									if (opcode == 4114) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = TextureType.method3280((char) local13) ? 1 : 0;
										return;
									}
									if (opcode == 4115) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Class149.method3559((char) local13) ? 1 : 0;
										return;
									}
									if (opcode == 4116) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = TextureOpSquareWaveform.method5521((char) local13) ? 1 : 0;
										return;
									}
									if (opcode == 4117) {
										local3888 = stringStack[--stringStackPtr];
										if (local3888 != null) {
											intStack[intStackPtr++] = local3888.length();
											return;
										}
										intStack[intStackPtr++] = 0;
										return;
									}
									if (opcode == 4118) {
										local3888 = stringStack[--stringStackPtr];
										intStackPtr -= 2;
										local19 = intStack[intStackPtr];
										local25 = intStack[intStackPtr + 1];
										stringStack[stringStackPtr++] = local3888.substring(local19, local25);
										return;
									}
									if (opcode == 4119) {
										local3888 = stringStack[--stringStackPtr];
										@Pc(7987) StringBuffer local7987 = new StringBuffer(local3888.length());
										@Pc(7989) boolean local7989 = false;
										for (local353 = 0; local353 < local3888.length(); local353++) {
											@Pc(7996) char local7996 = local3888.charAt(local353);
											if (local7996 == '<') {
												local7989 = true;
											} else if (local7996 == '>') {
												local7989 = false;
											} else if (!local7989) {
												local7987.append(local7996);
											}
										}
										stringStack[stringStackPtr++] = local7987.toString();
										return;
									}
									if (opcode == 4120) {
										local3888 = stringStack[--stringStackPtr];
										intStackPtr -= 2;
										local19 = intStack[intStackPtr];
										local25 = intStack[intStackPtr + 1];
										intStack[intStackPtr++] = local3888.indexOf(local19, local25);
										return;
									}
									if (opcode == 4121) {
										stringStackPtr -= 2;
										local3888 = stringStack[stringStackPtr];
										local1101 = stringStack[stringStackPtr + 1];
										local25 = intStack[--intStackPtr];
										intStack[intStackPtr++] = local3888.indexOf(local1101, local25);
										return;
									}
									if (opcode == 4122) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Character.toLowerCase((char) local13);
										return;
									}
									if (opcode == 4123) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Character.toUpperCase((char) local13);
										return;
									}
									if (opcode == 4124) {
										local412 = intStack[--intStackPtr] != 0;
										local19 = intStack[--intStackPtr];
										stringStack[stringStackPtr++] = Class30.method748(local412, (long) local19, 0, NativeBillboardFace.anInt3423);
										return;
									}
									if (opcode == 4125) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										@Pc(8210) com.jagex.game.runetek5.graphics.font.FontMetrics local8210 = ParticleList.method3546(local19, Static209.aClass76_48);
										intStack[intStackPtr++] = local8210.method2342(local3888, PingWorker.aClass57Array18);
										return;
									}
								} else if (opcode < 4300) {
									if (opcode == 4200) {
										local13 = intStack[--intStackPtr];
										stringStack[stringStackPtr++] = Terrain.aClass206_3.method4703(local13).name;
										return;
									}
									@Pc(8272) ItemType local8272;
									if (opcode == 4201) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local8272 = Terrain.aClass206_3.method4703(local13);
										if (local19 >= 1 && local19 <= 5 && local8272.aStringArray34[local19 - 1] != null) {
											stringStack[stringStackPtr++] = local8272.aStringArray34[local19 - 1];
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 4202) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local8272 = Terrain.aClass206_3.method4703(local13);
										if (local19 >= 1 && local19 <= 5 && local8272.aStringArray33[local19 - 1] != null) {
											stringStack[stringStackPtr++] = local8272.aStringArray33[local19 - 1];
											return;
										}
										stringStack[stringStackPtr++] = "";
										return;
									}
									if (opcode == 4203) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Terrain.aClass206_3.method4703(local13).anInt6165;
										return;
									}
									if (opcode == 4204) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Terrain.aClass206_3.method4703(local13).anInt6164 == 1 ? 1 : 0;
										return;
									}
									@Pc(8435) ItemType local8435;
									if (opcode == 4205) {
										local13 = intStack[--intStackPtr];
										local8435 = Terrain.aClass206_3.method4703(local13);
										if (local8435.anInt6175 == -1 && local8435.anInt6162 >= 0) {
											intStack[intStackPtr++] = local8435.anInt6162;
											return;
										}
										intStack[intStackPtr++] = local13;
										return;
									}
									if (opcode == 4206) {
										local13 = intStack[--intStackPtr];
										local8435 = Terrain.aClass206_3.method4703(local13);
										if (local8435.anInt6175 >= 0 && local8435.anInt6162 >= 0) {
											intStack[intStackPtr++] = local8435.anInt6162;
											return;
										}
										intStack[intStackPtr++] = local13;
										return;
									}
									if (opcode == 4207) {
										local13 = intStack[--intStackPtr];
										intStack[intStackPtr++] = Terrain.aClass206_3.method4703(local13).membersOnly ? 1 : 0;
										return;
									}
									if (opcode == 4208) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local3017 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
										if (local3017.method3350()) {
											stringStack[stringStackPtr++] = Terrain.aClass206_3.method4703(local13).method4789(local3017.aString40, local19);
											return;
										}
										intStack[intStackPtr++] = Terrain.aClass206_3.method4703(local13).method4781(local19, local3017.anInt4123);
										return;
									}
									if (opcode == 4209) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1] - 1;
										local8272 = Terrain.aClass206_3.method4703(local13);
										if (local8272.anInt6143 == local19) {
											intStack[intStackPtr++] = local8272.anInt6160;
											return;
										}
										if (local8272.anInt6184 == local19) {
											intStack[intStackPtr++] = local8272.anInt6126;
											return;
										}
										intStack[intStackPtr++] = -1;
										return;
									}
									if (opcode == 4210) {
										local3888 = stringStack[--stringStackPtr];
										local19 = intStack[--intStackPtr];
										Class149.method3561(local3888, local19 == 1);
										intStack[intStackPtr++] = Class4_Sub41.anInt6702;
										return;
									}
									if (opcode == 4211) {
										if (Preference.aShortArray66 != null && Static47.anInt809 < Class4_Sub41.anInt6702) {
											intStack[intStackPtr++] = Preference.aShortArray66[Static47.anInt809++] & 0xFFFF;
											return;
										}
										intStack[intStackPtr++] = -1;
										return;
									}
									if (opcode == 4212) {
										Static47.anInt809 = 0;
										return;
									}
								} else if (opcode < 4400) {
									if (opcode == 4300) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local3017 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
										if (local3017.method3350()) {
											stringStack[stringStackPtr++] = Class196.aClass240_1.method5229(local13).method5579(local3017.aString40, local19);
											return;
										}
										intStack[intStackPtr++] = Class196.aClass240_1.method5229(local13).method5582(local3017.anInt4123, local19);
										return;
									}
								} else if (opcode < 4500) {
									if (opcode == 4400) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local3017 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
										if (local3017.method3350()) {
											stringStack[stringStackPtr++] = Static267.aClass262_2.method5560(local13).method4597(local3017.aString40, local19);
											return;
										}
										intStack[intStackPtr++] = Static267.aClass262_2.method5560(local13).method4585(local19, local3017.anInt4123);
										return;
									}
								} else if (opcode < 4600) {
									if (opcode == 4500) {
										intStackPtr -= 2;
										local13 = intStack[intStackPtr];
										local19 = intStack[intStackPtr + 1];
										local3017 = Class4_Sub1_Sub11.aClass180_1.method4121(local19);
										if (local3017.method3350()) {
											stringStack[stringStackPtr++] = Class4_Sub6_Sub26.aClass72_1.method1988(local13).getStringParam(local3017.aString40, local19);
											return;
										}
										intStack[intStackPtr++] = Class4_Sub6_Sub26.aClass72_1.method1988(local13).getParam(local3017.anInt4123, local19);
										return;
									}
								} else if (opcode < 4700 && opcode == 4600) {
									local13 = intStack[--intStackPtr];
									@Pc(8936) BasType local8936 = Class133.aClass249_1.method5325(local13);
									if (local8936.anIntArray304 != null && local8936.anIntArray304.length > 0) {
										local25 = 0;
										local353 = local8936.anIntArray305[0];
										for (local2290 = 1; local2290 < local8936.anIntArray304.length; local2290++) {
											if (local8936.anIntArray305[local2290] > local353) {
												local25 = local2290;
												local353 = local8936.anIntArray305[local2290];
											}
										}
										intStack[intStackPtr++] = local8936.anIntArray304[local25];
										return;
									}
									intStack[intStackPtr++] = local8936.anInt4661;
									return;
								}
							}
						}
					}
				} else {
					if (opcode >= 2000) {
						opcode -= 1000;
						local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
					} else {
						local137 = hasIntOperand ? activeComponent2 : activeComponent;
					}
					Class4_Sub12_Sub1.method1142(local137);
					if (opcode == 1200 || opcode == 1205 || opcode == 1208 || opcode == 1209 || opcode == 1212 || opcode == 1213) {
						intStackPtr -= 2;
						local19 = intStack[intStackPtr];
						local25 = intStack[intStackPtr + 1];
						if (local137.anInt6865 == -1) {
							Class30.method750(local137.anInt6809);
							ModeWhere.method4942(local137.anInt6809);
							OpenGlCubeTextureGenerator.method2667(local137.anInt6809);
						}
						if (local19 == -1) {
							local137.anInt6796 = 1;
							local137.anInt6781 = -1;
							local137.anInt6779 = -1;
							return;
						}
						local137.anInt6779 = local19;
						local137.anInt6832 = local25;
						if (opcode == 1208 || opcode == 1209) {
							local137.aBoolean613 = true;
						} else {
							local137.aBoolean613 = false;
						}
						@Pc(1630) ItemType local1630 = Terrain.aClass206_3.method4703(local19);
						local137.anInt6844 = local1630.anInt6133;
						local137.anInt6858 = local1630.anInt6122;
						local137.anInt6843 = local1630.anInt6135;
						local137.anInt6814 = local1630.anInt6152;
						local137.anInt6826 = local1630.lb;
						local137.spriteScale = local1630.anInt6137;
						if (opcode == 1205 || opcode == 1209) {
							local137.anInt6828 = 0;
						} else if (opcode == 1212 || opcode == 1213) {
							local137.anInt6828 = 1;
						} else {
							local137.anInt6828 = 2;
						}
						if (local137.anInt6822 > 0) {
							local137.spriteScale = local137.spriteScale * 32 / local137.anInt6822;
							return;
						}
						if (local137.anInt6816 > 0) {
							local137.spriteScale = local137.spriteScale * 32 / local137.anInt6816;
						}
						return;
					}
					if (opcode == 1201) {
						local137.anInt6796 = 2;
						local137.anInt6781 = intStack[--intStackPtr];
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1202) {
						local137.anInt6796 = 3;
						local137.anInt6781 = -1;
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1203) {
						local137.anInt6796 = 6;
						local137.anInt6781 = intStack[--intStackPtr];
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1204) {
						local137.anInt6796 = 5;
						local137.anInt6781 = intStack[--intStackPtr];
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1206) {
						intStackPtr -= 4;
						local137.anInt6780 = intStack[intStackPtr];
						local137.lightPositionX = intStack[intStackPtr + 1];
						local137.lightPositionY = intStack[intStackPtr + 2];
						local137.lightPositionZ = intStack[intStackPtr + 3];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1207) {
						intStackPtr -= 2;
						local137.sunRotationX = intStack[intStackPtr];
						local137.sunRotationY = intStack[intStackPtr + 1];
						Class4_Sub12_Sub1.method1142(local137);
						return;
					}
					if (opcode == 1210) {
						intStackPtr -= 4;
						local137.anInt6781 = intStack[intStackPtr];
						local137.anInt6848 = intStack[intStackPtr + 1];
						if (intStack[intStackPtr + 2] == 1) {
							local137.anInt6796 = 9;
						} else {
							local137.anInt6796 = 8;
						}
						if (intStack[intStackPtr + 3] == 1) {
							local137.aBoolean613 = true;
						} else {
							local137.aBoolean613 = false;
						}
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
					if (opcode == 1211) {
						local137.anInt6796 = 5;
						local137.anInt6781 = Class6.anInt5452;
						local137.anInt6848 = 0;
						if (local137.anInt6865 == -1) {
							Class229.method5086(local137.anInt6809);
						}
						return;
					}
				}
			} else {
				if (opcode >= 2000) {
					opcode -= 1000;
					local137 = ParticleBinaryTree.method5121(intStack[--intStackPtr]);
				} else {
					local137 = hasIntOperand ? activeComponent2 : activeComponent;
				}
				if (opcode == 1000) {
					intStackPtr -= 4;
					local137.anInt6804 = intStack[intStackPtr];
					local137.anInt6808 = intStack[intStackPtr + 1];
					local19 = intStack[intStackPtr + 2];
					if (local19 < 0) {
						local19 = 0;
					} else if (local19 > 5) {
						local19 = 5;
					}
					local25 = intStack[intStackPtr + 3];
					if (local25 < 0) {
						local25 = 0;
					} else if (local25 > 5) {
						local25 = 5;
					}
					local137.aByte79 = (byte) local19;
					local137.aByte81 = (byte) local25;
					Class4_Sub12_Sub1.method1142(local137);
					Class4_Sub17.method2016(local137);
					if (local137.anInt6865 == -1) {
						GlElementsArrayBuffer.method3994(local137.anInt6809);
					}
					return;
				}
				if (opcode == 1001) {
					intStackPtr -= 4;
					local137.anInt6816 = intStack[intStackPtr];
					local137.anInt6802 = intStack[intStackPtr + 1];
					local137.anInt6822 = 0;
					local137.anInt6792 = 0;
					local19 = intStack[intStackPtr + 2];
					if (local19 < 0) {
						local19 = 0;
					} else if (local19 > 4) {
						local19 = 4;
					}
					local25 = intStack[intStackPtr + 3];
					if (local25 < 0) {
						local25 = 0;
					} else if (local25 > 4) {
						local25 = 4;
					}
					local137.aByte80 = (byte) local19;
					local137.aByte78 = (byte) local25;
					Class4_Sub12_Sub1.method1142(local137);
					Class4_Sub17.method2016(local137);
					if (local137.anInt6840 == 0) {
						Class95_Sub3_Sub1.method4998(local137, false);
					}
					return;
				}
				if (opcode == 1003) {
					@Pc(620) boolean local620 = intStack[--intStackPtr] == 1;
					if (local137.hidden != local620) {
						local137.hidden = local620;
						Class4_Sub12_Sub1.method1142(local137);
					}
					if (local137.anInt6865 == -1) {
						Class38.method842(local137.anInt6809);
					}
					return;
				}
				if (opcode == 1004) {
					intStackPtr -= 2;
					local137.anInt6774 = intStack[intStackPtr];
					local137.anInt6856 = intStack[intStackPtr + 1];
					Class4_Sub12_Sub1.method1142(local137);
					Class4_Sub17.method2016(local137);
					if (local137.anInt6840 == 0) {
						Class95_Sub3_Sub1.method4998(local137, false);
					}
					return;
				}
				if (opcode == 1005) {
					local137.aBoolean622 = intStack[--intStackPtr] == 1;
					return;
				}
			}
		}
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!nr", name = "b", descriptor = "(I)V")
	private static void method3889(@OriginalArg(0) int arg0) {
		@Pc(3) com.jagex.game.runetek5.config.rsinterface.Component local3 = ParticleBinaryTree.method5121(arg0);
		if (local3 == null) {
			return;
		}
		@Pc(9) int local9 = arg0 >>> 16;
		@Pc(13) com.jagex.game.runetek5.config.rsinterface.Component[] local13 = LocEntity.aClass247ArrayArray1[local9];
		if (local13 == null) {
			@Pc(19) com.jagex.game.runetek5.config.rsinterface.Component[] local19 = Class4_Sub36.aClass247ArrayArray2[local9];
			@Pc(22) int local22 = local19.length;
			local13 = LocEntity.aClass247ArrayArray1[local9] = new com.jagex.game.runetek5.config.rsinterface.Component[local22];
			Static459.copy(local19, 0, local13, 0, local19.length);
		}
		@Pc(38) int local38;
		for (local38 = 0; local38 < local13.length && local13[local38] != local3; local38++) {
		}
		if (local38 >= local13.length) {
			return;
		}
		Static459.copy(local13, local38 + 1, local13, local38, local13.length - local38 - 1);
		local13[local13.length - 1] = local3;
	}

	@OriginalMember(owner = "client!nr", name = "c", descriptor = "(I)V")
	private static void method3890(@OriginalArg(0) int arg0) {
		@Pc(3) com.jagex.game.runetek5.config.rsinterface.Component local3 = ParticleBinaryTree.method5121(arg0);
		if (local3 == null) {
			return;
		}
		@Pc(9) int local9 = arg0 >>> 16;
		@Pc(13) com.jagex.game.runetek5.config.rsinterface.Component[] local13 = LocEntity.aClass247ArrayArray1[local9];
		if (local13 == null) {
			@Pc(19) com.jagex.game.runetek5.config.rsinterface.Component[] local19 = Class4_Sub36.aClass247ArrayArray2[local9];
			@Pc(22) int local22 = local19.length;
			local13 = LocEntity.aClass247ArrayArray1[local9] = new com.jagex.game.runetek5.config.rsinterface.Component[local22];
			Static459.copy(local19, 0, local13, 0, local19.length);
		}
		@Pc(38) int local38;
		for (local38 = 0; local38 < local13.length && local13[local38] != local3; local38++) {
		}
		if (local38 >= local13.length) {
			return;
		}
		Static459.copy(local13, 0, local13, 1, local38);
		local13[0] = local3;
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(Ljava/lang/String;I)V")
	private static void sendPublicChat(@OriginalArg(0) String string, @OriginalArg(1) int opcode) {
		if (GlToolkit.activeChildCount == 0 && (Class16.aBoolean628 && !MemoryPool.aBoolean443 || Static308.aBoolean486)) {
			return;
		}
		@Pc(11) String local11 = string.toLowerCase();
		@Pc(13) byte local13 = 0;
		if (local11.startsWith(Class48_Sub1.aClass83_101.method2267(0))) {
			local13 = 0;
			string = string.substring(Class48_Sub1.aClass83_101.method2267(0).length());
		} else if (local11.startsWith(Class244.aClass83_146.method2267(0))) {
			local13 = 1;
			string = string.substring(Class244.aClass83_146.method2267(0).length());
		} else if (local11.startsWith(Class4_Sub1_Sub11.aClass83_60.method2267(0))) {
			local13 = 2;
			string = string.substring(Class4_Sub1_Sub11.aClass83_60.method2267(0).length());
		} else if (local11.startsWith(HashTable.aClass83_64.method2267(0))) {
			local13 = 3;
			string = string.substring(HashTable.aClass83_64.method2267(0).length());
		} else if (local11.startsWith(SimpleMouseLog.aClass83_23.method2267(0))) {
			local13 = 4;
			string = string.substring(SimpleMouseLog.aClass83_23.method2267(0).length());
		} else if (local11.startsWith(Class249.aClass83_143.method2267(0))) {
			local13 = 5;
			string = string.substring(Class249.aClass83_143.method2267(0).length());
		} else if (local11.startsWith(PointEntity.aClass83_89.method2267(0))) {
			local13 = 6;
			string = string.substring(PointEntity.aClass83_89.method2267(0).length());
		} else if (local11.startsWith(Class243.aClass83_139.method2267(0))) {
			local13 = 7;
			string = string.substring(Class243.aClass83_139.method2267(0).length());
		} else if (local11.startsWith(PositionedGfx.aClass83_56.method2267(0))) {
			local13 = 8;
			string = string.substring(PositionedGfx.aClass83_56.method2267(0).length());
		} else if (local11.startsWith(Preference.aClass83_48.method2267(0))) {
			local13 = 9;
			string = string.substring(Preference.aClass83_48.method2267(0).length());
		} else if (local11.startsWith(Class75.aClass83_42.method2267(0))) {
			local13 = 10;
			string = string.substring(Class75.aClass83_42.method2267(0).length());
		} else if (local11.startsWith(Class4_Sub6_Sub22.aClass83_93.method2267(0))) {
			local13 = 11;
			string = string.substring(Class4_Sub6_Sub22.aClass83_93.method2267(0).length());
		} else if (NativeBillboardFace.anInt3423 != 0) {
			if (local11.startsWith(Class48_Sub1.aClass83_101.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 0;
				string = string.substring(Class48_Sub1.aClass83_101.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class244.aClass83_146.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 1;
				string = string.substring(Class244.aClass83_146.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class4_Sub1_Sub11.aClass83_60.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 2;
				string = string.substring(Class4_Sub1_Sub11.aClass83_60.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(HashTable.aClass83_64.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 3;
				string = string.substring(HashTable.aClass83_64.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(SimpleMouseLog.aClass83_23.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 4;
				string = string.substring(SimpleMouseLog.aClass83_23.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class249.aClass83_143.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 5;
				string = string.substring(Class249.aClass83_143.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(PointEntity.aClass83_89.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 6;
				string = string.substring(PointEntity.aClass83_89.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class243.aClass83_139.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 7;
				string = string.substring(Class243.aClass83_139.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(PositionedGfx.aClass83_56.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 8;
				string = string.substring(PositionedGfx.aClass83_56.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Preference.aClass83_48.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 9;
				string = string.substring(Preference.aClass83_48.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class75.aClass83_42.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 10;
				string = string.substring(Class75.aClass83_42.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Class4_Sub6_Sub22.aClass83_93.method2267(NativeBillboardFace.anInt3423))) {
				local13 = 11;
				string = string.substring(Class4_Sub6_Sub22.aClass83_93.method2267(NativeBillboardFace.anInt3423).length());
			}
		}
		local11 = string.toLowerCase();
		@Pc(451) byte local451 = 0;
		if (local11.startsWith(Class149.aClass83_96.method2267(0))) {
			local451 = 1;
			string = string.substring(Class149.aClass83_96.method2267(0).length());
		} else if (local11.startsWith(Static99.aClass83_39.method2267(0))) {
			local451 = 2;
			string = string.substring(Static99.aClass83_39.method2267(0).length());
		} else if (local11.startsWith(Static384.aClass83_131.method2267(0))) {
			local451 = 3;
			string = string.substring(Static384.aClass83_131.method2267(0).length());
		} else if (local11.startsWith(HashTable.aClass83_65.method2267(0))) {
			local451 = 4;
			string = string.substring(HashTable.aClass83_65.method2267(0).length());
		} else if (local11.startsWith(Static298.aClass83_112.method2267(0))) {
			local451 = 5;
			string = string.substring(Static298.aClass83_112.method2267(0).length());
		} else if (NativeBillboardFace.anInt3423 != 0) {
			if (local11.startsWith(Class149.aClass83_96.method2267(NativeBillboardFace.anInt3423))) {
				local451 = 1;
				string = string.substring(Class149.aClass83_96.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Static99.aClass83_39.method2267(NativeBillboardFace.anInt3423))) {
				local451 = 2;
				string = string.substring(Static99.aClass83_39.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Static384.aClass83_131.method2267(NativeBillboardFace.anInt3423))) {
				local451 = 3;
				string = string.substring(Static384.aClass83_131.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(HashTable.aClass83_65.method2267(NativeBillboardFace.anInt3423))) {
				local451 = 4;
				string = string.substring(HashTable.aClass83_65.method2267(NativeBillboardFace.anInt3423).length());
			} else if (local11.startsWith(Static298.aClass83_112.method2267(NativeBillboardFace.anInt3423))) {
				local451 = 5;
				string = string.substring(Static298.aClass83_112.method2267(NativeBillboardFace.anInt3423).length());
			}
		}
		PingWorker.method5476(Class4_Sub9.aClass215_10);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
		@Pc(646) int local646 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
		if (opcode == 5021) {
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(1);
		} else if (opcode == 5022) {
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(2);
		} else {
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
		}
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(local13);
		ParticleNode.aClass4_Sub12_Sub1_5.method2551(local451);
		Class16_Sub1_Sub6.method5359(string, ParticleNode.aClass4_Sub12_Sub1_5);
		ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local646);
	}

	@OriginalMember(owner = "client!nr", name = "b", descriptor = "()V")
	public static void profileClear() {
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(Lclient!op;)V")
	public static void executeHookInner(@OriginalArg(0) HookRequest hookRequest) {
		executeHookInternal(hookRequest, 200000);
	}

	@OriginalMember(owner = "client!nr", name = "b", descriptor = "(IZ)V")
	public static void profileOutput() {
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(Lclient!ae;I)V")
	private static void executeScript(@OriginalArg(0) ClientScript clientScript, @OriginalArg(1) int stackLimit) {
		intStackPtr = 0;
		stringStackPtr = 0;
		@Pc(5) int local5 = -1;
		@Pc(8) int[] local8 = clientScript.anIntArray5;
		@Pc(11) int[] local11 = clientScript.anIntArray6;
		@Pc(13) byte local13 = -1;
		framePtr = 0;
		@Pc(603) int local603;
		try {
			@Pc(17) int local17 = 0;
			label260: while (true) {
				local17++;
				if (local17 > stackLimit) {
					throw new RuntimeException("slow");
				}
				local5++;
				@Pc(31) int local31 = local8[local5];
				if (local31 >= 100) {
					@Pc(802) boolean local802;
					if (local11[local5] == 1) {
						local802 = true;
					} else {
						local802 = false;
					}
					if (local31 >= 100 && local31 < 5000) {
						executeOpBelow5000(local31, local802);
					} else if (local31 >= 5000 && local31 < 10000) {
						method3898(local31, local802);
					} else {
						throw new IllegalStateException("Command: " + local31);
					}
				} else if (local31 == 0) {
					intStack[intStackPtr++] = local11[local5];
				} else {
					@Pc(54) int local54;
					if (local31 == 1) {
						local54 = local11[local5];
						intStack[intStackPtr++] = Class4_Sub6_Sub24.aClass114_1.activeVars[local54];
					} else if (local31 == 2) {
						local54 = local11[local5];
						Class4_Sub6_Sub24.aClass114_1.setVarValueInt(intStack[--intStackPtr], local54);
					} else if (local31 == 3) {
						stringStack[stringStackPtr++] = clientScript.aStringArray1[local5];
					} else if (local31 == 6) {
						local5 += local11[local5];
					} else if (local31 == 7) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] != intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 8) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] == intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 9) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] < intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 10) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] > intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 21) {
						if (framePtr == 0) {
							return;
						}
						@Pc(216) StackFrame local216 = frames[--framePtr];
						clientScript = local216.script;
						local8 = clientScript.anIntArray5;
						local11 = clientScript.anIntArray6;
						local5 = local216.pc;
						intLocals = local216.intVars;
						stringVars = local216.stringVars;
					} else if (local31 == 25) {
						local54 = local11[local5];
						intStack[intStackPtr++] = Class4_Sub6_Sub24.aClass114_1.getVarBitValue(local54);
					} else if (local31 == 27) {
						local54 = local11[local5];
						Class4_Sub6_Sub24.aClass114_1.method2835(local54, intStack[--intStackPtr]);
					} else if (local31 == 31) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] <= intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 32) {
						intStackPtr -= 2;
						if (intStack[intStackPtr] >= intStack[intStackPtr + 1]) {
							local5 += local11[local5];
						}
					} else if (local31 == 33) {
						intStack[intStackPtr++] = intLocals[local11[local5]];
					} else if (local31 == 34) {
						intLocals[local11[local5]] = intStack[--intStackPtr];
					} else if (local31 == 35) {
						stringStack[stringStackPtr++] = stringVars[local11[local5]];
					} else if (local31 == 36) {
						stringVars[local11[local5]] = stringStack[--stringStackPtr];
					} else if (local31 == 37) {
						local54 = local11[local5];
						stringStackPtr -= local54;
						@Pc(400) String local400 = QuickChatPhrase.method5482(stringStackPtr, stringStack, local54);
						stringStack[stringStackPtr++] = local400;
					} else if (local31 == 38) {
						intStackPtr--;
					} else if (local31 == 39) {
						stringStackPtr--;
					} else {
						@Pc(452) int local452;
						if (local31 == 40) {
							local54 = local11[local5];
							@Pc(436) ClientScript local436 = Static211.method3200(local54);
							if (local436 == null) {
								throw new RuntimeException();
							}
							@Pc(446) int[] local446 = new int[local436.anInt55];
							@Pc(450) String[] local450 = new String[local436.anInt56];
							for (local452 = 0; local452 < local436.anInt54; local452++) {
								local446[local452] = intStack[intStackPtr + local452 - local436.anInt54];
							}
							for (@Pc(471) int local471 = 0; local471 < local436.anInt58; local471++) {
								local450[local471] = stringStack[stringStackPtr + local471 - local436.anInt58];
							}
							intStackPtr -= local436.anInt54;
							stringStackPtr -= local436.anInt58;
							@Pc(502) StackFrame local502 = new StackFrame();
							local502.script = clientScript;
							local502.pc = local5;
							local502.intVars = intLocals;
							local502.stringVars = stringVars;
							if (framePtr >= frames.length) {
								throw new RuntimeException();
							}
							frames[framePtr++] = local502;
							clientScript = local436;
							local8 = local436.anIntArray5;
							local11 = local436.anIntArray6;
							local5 = -1;
							intLocals = local446;
							stringVars = local450;
						} else if (local31 == 42) {
							intStack[intStackPtr++] = ParticleEffector.WALL_ROTATION_FLAGS[local11[local5]];
						} else if (local31 == 43) {
							local54 = local11[local5];
							ParticleEffector.WALL_ROTATION_FLAGS[local54] = intStack[--intStackPtr];
							Class28.method670(local54);
							TextureOpKaleidoscope.aBoolean256 |= Class16_Sub1_Sub1.aBooleanArray3[local54];
						} else if (local31 == 44) {
							local54 = local11[local5] >> 16;
							local603 = local11[local5] & 0xFFFF;
							@Pc(611) int local611 = intStack[--intStackPtr];
							if (local611 >= 0 && local611 <= 5000) {
								globalArrayLengths[local54] = local611;
								@Pc(626) byte local626 = -1;
								if (local603 == 105) {
									local626 = 0;
								}
								local452 = 0;
								while (true) {
									if (local452 >= local611) {
										continue label260;
									}
									arrayVars[local54][local452] = local626;
									local452++;
								}
							}
							throw new RuntimeException();
						} else if (local31 == 45) {
							local54 = local11[local5];
							local603 = intStack[--intStackPtr];
							if (local603 < 0 || local603 >= globalArrayLengths[local54]) {
								throw new RuntimeException();
							}
							intStack[intStackPtr++] = arrayVars[local54][local603];
						} else if (local31 == 46) {
							local54 = local11[local5];
							intStackPtr -= 2;
							local603 = intStack[intStackPtr];
							if (local603 < 0 || local603 >= globalArrayLengths[local54]) {
								throw new RuntimeException();
							}
							arrayVars[local54][local603] = intStack[intStackPtr + 1];
						} else if (local31 == 47) {
							@Pc(730) String local730 = RequestManager.aStringArray20[local11[local5]];
							if (local730 == null) {
								local730 = "null";
							}
							stringStack[stringStackPtr++] = local730;
						} else if (local31 == 48) {
							local54 = local11[local5];
							RequestManager.aStringArray20[local54] = stringStack[--stringStackPtr];
							ParticleEmitter.method3629(local54);
						} else if (local31 == 51) {
							@Pc(774) CubeMapSpecularEffect local774 = clientScript.aClass102Array1[local11[local5]];
							@Pc(787) Class4_Sub37 local787 = (Class4_Sub37) local774.method2700((long) intStack[--intStackPtr]);
							if (local787 != null) {
								local5 += local787.anInt5426;
							}
						}
					}
				}
			}
		} catch (@Pc(837) Exception local837) {
			@Pc(855) StringBuffer local855;
			if (clientScript.aString2 == null) {
				local855 = new StringBuffer(30);
				local855.append("CS2: ").append(clientScript.aLong224).append(" ");
				for (local603 = framePtr - 1; local603 >= 0; local603--) {
					local855.append("v: ").append(frames[local603].script.aLong224).append(" ");
				}
				local855.append("op: ").append(local13);
				Class64_Sub1.method1654(local837, local855.toString());
			} else {
				Class249.method5323("Clientscript error in: " + clientScript.aString2);
				local855 = new StringBuffer(30);
				local855.append("Clientscript error in: ").append(clientScript.aString2).append("\n");
				for (local603 = framePtr - 1; local603 >= 0; local603--) {
					local855.append("via: ").append(frames[local603].script.aString2).append("\n");
				}
				local855.append("Op: ").append(local13).append("\n");
				@Pc(895) String local895 = local837.getMessage();
				if (local895 != null && local895.length() > 0) {
					local855.append("Message: ").append(local895).append("\n");
				}
				Class64_Sub1.method1654(local837, local855.toString());
				Static441.method5568(local855.toString());
			}
		}
	}

	@OriginalMember(owner = "client!nr", name = "d", descriptor = "(I)V")
	public static void runInterfaceOnLoadScripts(@OriginalArg(0) int interfaceId) {
		if (interfaceId == -1 || !Class4_Sub18.method2034(interfaceId)) {
			return;
		}
		@Pc(12) com.jagex.game.runetek5.config.rsinterface.Component[] local12 = Class4_Sub36.aClass247ArrayArray2[interfaceId];
		for (@Pc(14) int local14 = 0; local14 < local12.length; local14++) {
			@Pc(19) com.jagex.game.runetek5.config.rsinterface.Component local19 = local12[local14];
			if (local19.anObjectArray28 != null) {
				@Pc(26) HookRequest local26 = new HookRequest();
				local26.source = local19;
				local26.params = local19.anObjectArray28;
				executeHookInternal(local26, 2000000);
			}
		}
	}

	@OriginalMember(owner = "client!nr", name = "c", descriptor = "(IZ)V")
	private static void method3898(@OriginalArg(0) int arg0, @OriginalArg(1) boolean arg1) {
		@Pc(157) int local157;
		@Pc(1313) boolean local1313;
		@Pc(89) int local89;
		@Pc(75) String local75;
		@Pc(192) int local192;
		@Pc(95) int local95;
		@Pc(81) String local81;
		if (arg0 < 5100) {
			if (arg0 == 5000) {
				intStack[intStackPtr++] = Static14.anInt189;
				return;
			}
			if (arg0 == 5001) {
				intStackPtr -= 3;
				Static14.anInt189 = intStack[intStackPtr];
				Class4_Sub25.aClass104_2 = Static2.method6(intStack[intStackPtr + 1]);
				if (Class4_Sub25.aClass104_2 == null) {
					Class4_Sub25.aClass104_2 = Class53.aClass104_1;
				}
				SpriteGL.anInt6765 = intStack[intStackPtr + 2];
				PingWorker.method5476(GlVboElementsArrayBuffer.aClass215_88);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Static14.anInt189);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(Class4_Sub25.aClass104_2.id);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(SpriteGL.anInt6765);
				return;
			}
			if (arg0 == 5002) {
				stringStackPtr -= 2;
				local75 = stringStack[stringStackPtr];
				local81 = stringStack[stringStackPtr + 1];
				intStackPtr -= 2;
				local89 = intStack[intStackPtr];
				local95 = intStack[intStackPtr + 1];
				if (local81 == null) {
					local81 = "";
				}
				if (local81.length() > 80) {
					local81 = local81.substring(0, 80);
				}
				PingWorker.method5476(OpenGLArbTextureCubeMapGenerator.aClass215_42);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(local75) + JavaBillboardAttributes.method3856(local81) + 2);
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(local75);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(local89 - 1);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(local95);
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(local81);
				return;
			}
			if (arg0 == 5003) {
				local157 = intStack[--intStackPtr];
				local81 = null;
				if (local157 < 100) {
					local81 = Class4_Sub1_Sub2.aStringArray2[local157];
				}
				if (local81 == null) {
					local81 = "";
				}
				stringStack[stringStackPtr++] = local81;
				return;
			}
			if (arg0 == 5004) {
				local157 = intStack[--intStackPtr];
				local192 = -1;
				if (local157 < 100 && Class4_Sub1_Sub2.aStringArray2[local157] != null) {
					local192 = Static340.anIntArray411[local157];
				}
				intStack[intStackPtr++] = local192;
				return;
			}
			if (arg0 == 5005) {
				if (Class4_Sub25.aClass104_2 == null) {
					intStack[intStackPtr++] = -1;
					return;
				}
				intStack[intStackPtr++] = Class4_Sub25.aClass104_2.id;
				return;
			}
			if (arg0 == 5008 || arg0 == 5021 || arg0 == 5022) {
				local75 = stringStack[--stringStackPtr];
				sendPublicChat(local75, arg0);
				return;
			}
			if (arg0 == 5009) {
				stringStackPtr -= 2;
				local75 = stringStack[stringStackPtr];
				local81 = stringStack[stringStackPtr + 1];
				if (GlToolkit.activeChildCount != 0 || (!Class16.aBoolean628 || MemoryPool.aBoolean443) && !Static308.aBoolean486) {
					PingWorker.method5476(GzipDecompressor.aClass215_69);
					ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
					local89 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
					ParticleNode.aClass4_Sub12_Sub1_5.method2518(local75);
					Class16_Sub1_Sub6.method5359(local81, ParticleNode.aClass4_Sub12_Sub1_5);
					ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local89);
					return;
				}
				return;
			}
			if (arg0 == 5010) {
				local157 = intStack[--intStackPtr];
				local81 = null;
				if (local157 < 100) {
					local81 = Class210.aStringArray32[local157];
				}
				if (local81 == null) {
					local81 = "";
				}
				stringStack[stringStackPtr++] = local81;
				return;
			}
			if (arg0 == 5011) {
				local157 = intStack[--intStackPtr];
				local81 = null;
				if (local157 < 100) {
					local81 = Class4_Sub21_Sub2.aStringArray15[local157];
				}
				if (local81 == null) {
					local81 = "";
				}
				stringStack[stringStackPtr++] = local81;
				return;
			}
			if (arg0 == 5012) {
				local157 = intStack[--intStackPtr];
				local192 = -1;
				if (local157 < 100) {
					local192 = MusicPatchNode.anIntArray294[local157];
				}
				intStack[intStackPtr++] = local192;
				return;
			}
			if (arg0 == 5015) {
				if (Static1.aClass16_Sub1_Sub5_Sub1_1 == null || Static1.aClass16_Sub1_Sub5_Sub1_1.aString43 == null) {
					local75 = Static2.aString1;
				} else {
					local75 = Static1.aClass16_Sub1_Sub5_Sub1_1.method3428();
				}
				stringStack[stringStackPtr++] = local75;
				return;
			}
			if (arg0 == 5016) {
				intStack[intStackPtr++] = SpriteGL.anInt6765;
				return;
			}
			if (arg0 == 5017) {
				intStack[intStackPtr++] = GlShadowMap.anInt6639;
				return;
			}
			if (arg0 == 5018) {
				local157 = intStack[--intStackPtr];
				local192 = 0;
				if (local157 < 100 && Class4_Sub1_Sub2.aStringArray2[local157] != null) {
					local192 = Static340.anIntArray411[local157];
				}
				intStack[intStackPtr++] = local192;
				return;
			}
			if (arg0 == 5019) {
				local157 = intStack[--intStackPtr];
				local81 = null;
				if (local157 < 100) {
					local81 = MonoStereoData.aStringArray9[local157];
				}
				if (local81 == null) {
					local81 = "";
				}
				stringStack[stringStackPtr++] = local81;
				return;
			}
			if (arg0 == 5020) {
				if (Static1.aClass16_Sub1_Sub5_Sub1_1 == null || Static1.aClass16_Sub1_Sub5_Sub1_1.aString43 == null) {
					local75 = Static2.aString1;
				} else {
					local75 = Static1.aClass16_Sub1_Sub5_Sub1_1.method3432();
				}
				stringStack[stringStackPtr++] = local75;
				return;
			}
			if (arg0 == 5050) {
				local157 = intStack[--intStackPtr];
				stringStack[stringStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).aString49;
				return;
			}
			@Pc(589) QuickChatCategoryType local589;
			if (arg0 == 5051) {
				local157 = intStack[--intStackPtr];
				local589 = GlShadowMap.aClass62_1.method1607(local157);
				if (local589.anIntArray326 == null) {
					intStack[intStackPtr++] = 0;
					return;
				}
				intStack[intStackPtr++] = local589.anIntArray326.length;
				return;
			}
			if (arg0 == 5052) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				@Pc(634) QuickChatCategoryType local634 = GlShadowMap.aClass62_1.method1607(local157);
				local95 = local634.anIntArray326[local192];
				intStack[intStackPtr++] = local95;
				return;
			}
			if (arg0 == 5053) {
				local157 = intStack[--intStackPtr];
				local589 = GlShadowMap.aClass62_1.method1607(local157);
				if (local589.anIntArray325 == null) {
					intStack[intStackPtr++] = 0;
					return;
				}
				intStack[intStackPtr++] = local589.anIntArray325.length;
				return;
			}
			if (arg0 == 5054) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				intStack[intStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).anIntArray325[local192];
				return;
			}
			if (arg0 == 5055) {
				local157 = intStack[--intStackPtr];
				stringStack[stringStackPtr++] = Class145_Sub1.aClass81_2.method2258(local157).getText();
				return;
			}
			if (arg0 == 5056) {
				local157 = intStack[--intStackPtr];
				@Pc(760) QuickchatMessageType local760 = Class145_Sub1.aClass81_2.method2258(local157);
				if (local760.responses == null) {
					intStack[intStackPtr++] = 0;
					return;
				}
				intStack[intStackPtr++] = local760.responses.length;
				return;
			}
			if (arg0 == 5057) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				intStack[intStackPtr++] = Class145_Sub1.aClass81_2.method2258(local157).responses[local192];
				return;
			}
			if (arg0 == 5058) {
				currentQuickChatPhrase = new QuickChatPhrase();
				currentQuickChatPhrase.id = intStack[--intStackPtr];
				currentQuickChatPhrase.type = Class145_Sub1.aClass81_2.method2258(currentQuickChatPhrase.id);
				currentQuickChatPhrase.values = new int[currentQuickChatPhrase.type.getDynamicCommandCount()];
				return;
			}
			if (arg0 == 5059) {
				PingWorker.method5476(CacheableNodeByteArray.aClass215_48);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				local157 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(currentQuickChatPhrase.id);
				currentQuickChatPhrase.type.encodeValues(currentQuickChatPhrase.values, ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local157);
				return;
			}
			if (arg0 == 5060) {
				local75 = stringStack[--stringStackPtr];
				PingWorker.method5476(GlToolkit.aClass215_25);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				local192 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				ParticleNode.aClass4_Sub12_Sub1_5.method2518(local75);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(currentQuickChatPhrase.id);
				currentQuickChatPhrase.type.encodeValues(currentQuickChatPhrase.values, ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local192);
				return;
			}
			if (arg0 == 5061) {
				PingWorker.method5476(CacheableNodeByteArray.aClass215_48);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				local157 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(1);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(currentQuickChatPhrase.id);
				currentQuickChatPhrase.type.encodeValues(currentQuickChatPhrase.values, ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local157);
				return;
			}
			if (arg0 == 5062) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				intStack[intStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).aCharArray3[local192];
				return;
			}
			if (arg0 == 5063) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				intStack[intStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).aCharArray4[local192];
				return;
			}
			if (arg0 == 5064) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				if (local192 == -1) {
					intStack[intStackPtr++] = -1;
					return;
				}
				intStack[intStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).method3869((char) local192);
				return;
			}
			if (arg0 == 5065) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				if (local192 == -1) {
					intStack[intStackPtr++] = -1;
					return;
				}
				intStack[intStackPtr++] = GlShadowMap.aClass62_1.method1607(local157).method3867((char) local192);
				return;
			}
			if (arg0 == 5066) {
				local157 = intStack[--intStackPtr];
				intStack[intStackPtr++] = Class145_Sub1.aClass81_2.method2258(local157).getDynamicCommandCount();
				return;
			}
			if (arg0 == 5067) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				local89 = Class145_Sub1.aClass81_2.method2258(local157).method226(local192).anInt3060;
				intStack[intStackPtr++] = local89;
				return;
			}
			if (arg0 == 5068) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				currentQuickChatPhrase.values[local157] = local192;
				return;
			}
			if (arg0 == 5069) {
				intStackPtr -= 2;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				currentQuickChatPhrase.values[local157] = local192;
				return;
			}
			if (arg0 == 5070) {
				intStackPtr -= 3;
				local157 = intStack[intStackPtr];
				local192 = intStack[intStackPtr + 1];
				local89 = intStack[intStackPtr + 2];
				@Pc(1265) QuickchatMessageType local1265 = Class145_Sub1.aClass81_2.method2258(local157);
				if (local1265.method226(local192).anInt3060 != 0) {
					throw new RuntimeException("bad command");
				}
				intStack[intStackPtr++] = local1265.getDynamicCommandParam(local89, local192);
				return;
			}
			if (arg0 == 5071) {
				local75 = stringStack[--stringStackPtr];
				local1313 = intStack[--intStackPtr] == 1;
				Class120_Sub1.method4113(local1313, local75);
				intStack[intStackPtr++] = Class4_Sub41.anInt6702;
				return;
			}
			if (arg0 == 5072) {
				if (Preference.aShortArray66 != null && Static47.anInt809 < Class4_Sub41.anInt6702) {
					intStack[intStackPtr++] = Preference.aShortArray66[Static47.anInt809++] & 0xFFFF;
					return;
				}
				intStack[intStackPtr++] = -1;
				return;
			}
			if (arg0 == 5073) {
				Static47.anInt809 = 0;
				return;
			}
			if (arg0 == 5074) {
				PingWorker.method5476(CacheableNodeByteArray.aClass215_48);
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(0);
				local157 = ParticleNode.aClass4_Sub12_Sub1_5.currentPosition;
				ParticleNode.aClass4_Sub12_Sub1_5.method2551(2);
				ParticleNode.aClass4_Sub12_Sub1_5.method2524(currentQuickChatPhrase.id);
				currentQuickChatPhrase.type.encodeValues(currentQuickChatPhrase.values, ParticleNode.aClass4_Sub12_Sub1_5);
				ParticleNode.aClass4_Sub12_Sub1_5.method2545(ParticleNode.aClass4_Sub12_Sub1_5.currentPosition - local157);
				return;
			}
		} else if (arg0 < 5200) {
			if (arg0 == 5100) {
				if (Static384.aClass244_1.method5489(86)) {
					intStack[intStackPtr++] = 1;
					return;
				}
				intStack[intStackPtr++] = 0;
				return;
			}
			if (arg0 == 5101) {
				if (Static384.aClass244_1.method5489(82)) {
					intStack[intStackPtr++] = 1;
					return;
				}
				intStack[intStackPtr++] = 0;
				return;
			}
			if (arg0 == 5102) {
				if (Static384.aClass244_1.method5489(81)) {
					intStack[intStackPtr++] = 1;
					return;
				}
				intStack[intStackPtr++] = 0;
				return;
			}
		} else {
			@Pc(2691) int local2691;
			@Pc(1965) boolean local1965;
			if (arg0 < 5300) {
				if (arg0 == 5200) {
					GzipDecompressor.method4169(intStack[--intStackPtr]);
					return;
				}
				if (arg0 == 5201) {
					intStack[intStackPtr++] = Mesh.method3048();
					return;
				}
				if (arg0 == 5205) {
					Static231.method3474(-1, -1, intStack[--intStackPtr], false);
					return;
				}
				@Pc(1554) WorldMapElement local1554;
				if (arg0 == 5206) {
					local157 = intStack[--intStackPtr];
					local1554 = Static148.method4494(local157 >> 14 & 0x3FFF, local157 & 0x3FFF);
					if (local1554 == null) {
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = local1554.anInt1012;
					return;
				}
				@Pc(1587) WorldMapElement local1587;
				if (arg0 == 5207) {
					local1587 = Static148.method4511(intStack[--intStackPtr]);
					if (local1587 != null && local1587.aString15 != null) {
						stringStack[stringStackPtr++] = local1587.aString15;
						return;
					}
					stringStack[stringStackPtr++] = "";
					return;
				}
				if (arg0 == 5208) {
					intStack[intStackPtr++] = Static17.anInt220;
					intStack[intStackPtr++] = Static384.anInt6470;
					return;
				}
				if (arg0 == 5209) {
					intStack[intStackPtr++] = OpenGl2DImageTexture.anInt5471 + Static148.anInt5732;
					intStack[intStackPtr++] = TextureCombinerOp.anInt5156 + Static148.anInt5724;
					return;
				}
				if (arg0 == 5210) {
					local157 = intStack[--intStackPtr];
					local1554 = Static148.method4511(local157);
					if (local1554 == null) {
						intStack[intStackPtr++] = 0;
						intStack[intStackPtr++] = 0;
						return;
					}
					intStack[intStackPtr++] = local1554.anInt1023 >> 14 & 0x3FFF;
					intStack[intStackPtr++] = local1554.anInt1023 & 0x3FFF;
					return;
				}
				if (arg0 == 5211) {
					local157 = intStack[--intStackPtr];
					local1554 = Static148.method4511(local157);
					if (local1554 == null) {
						intStack[intStackPtr++] = 0;
						intStack[intStackPtr++] = 0;
						return;
					}
					intStack[intStackPtr++] = local1554.anInt1014 - local1554.anInt1010;
					intStack[intStackPtr++] = local1554.anInt1019 - local1554.anInt1018;
					return;
				}
				@Pc(1777) Class4_Sub3 local1777;
				if (arg0 == 5212) {
					local1777 = VertexDeclarationComponent.method5217();
					if (local1777 == null) {
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = local1777.anInt234;
					local192 = local1777.anInt236 << 28 | local1777.anInt233 + Static148.anInt5732 << 14 | local1777.anInt228 + Static148.anInt5724;
					intStack[intStackPtr++] = local192;
					return;
				}
				if (arg0 == 5213) {
					local1777 = Huffman.method2937();
					if (local1777 == null) {
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = local1777.anInt234;
					local192 = local1777.anInt236 << 28 | local1777.anInt233 + Static148.anInt5732 << 14 | local1777.anInt228 + Static148.anInt5724;
					intStack[intStackPtr++] = local192;
					return;
				}
				@Pc(1925) boolean local1925;
				if (arg0 == 5214) {
					local157 = intStack[--intStackPtr];
					local1554 = Static107.method1990();
					if (local1554 != null) {
						local1925 = local1554.method935(coordConversionBuffer, local157 >> 28 & 0x3, local157 & 0x3FFF, local157 >> 14 & 0x3FFF);
						if (local1925) {
							Class15.method215(coordConversionBuffer[2], coordConversionBuffer[1]);
						}
					}
					return;
				}
				if (arg0 == 5215) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local192 = intStack[intStackPtr + 1];
					@Pc(1963) Class229 local1963 = Static148.getRenderedElements(local157 >> 14 & 0x3FFF, local157 & 0x3FFF);
					local1965 = false;
					for (@Pc(1970) WorldMapElement local1970 = (WorldMapElement) local1963.method5089(); local1970 != null; local1970 = (WorldMapElement) local1963.method5092()) {
						if (local1970.anInt1012 == local192) {
							local1965 = true;
							break;
						}
					}
					if (local1965) {
						intStack[intStackPtr++] = 1;
						return;
					}
					intStack[intStackPtr++] = 0;
					return;
				}
				if (arg0 == 5218) {
					local157 = intStack[--intStackPtr];
					local1554 = Static148.method4511(local157);
					if (local1554 == null) {
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = local1554.anInt1016;
					return;
				}
				if (arg0 == 5220) {
					intStack[intStackPtr++] = Class50.anInt3429 == 100 ? 1 : 0;
					return;
				}
				if (arg0 == 5221) {
					local157 = intStack[--intStackPtr];
					Class15.method215(local157 & 0x3FFF, local157 >> 14 & 0x3FFF);
					return;
				}
				if (arg0 == 5222) {
					local1587 = Static107.method1990();
					if (local1587 != null) {
						local1313 = local1587.method930(OpenGl2DImageTexture.anInt5471 + Static148.anInt5732, coordConversionBuffer, TextureCombinerOp.anInt5156 + Static148.anInt5724);
						if (local1313) {
							intStack[intStackPtr++] = coordConversionBuffer[1];
							intStack[intStackPtr++] = coordConversionBuffer[2];
							return;
						}
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = -1;
					intStack[intStackPtr++] = -1;
					return;
				}
				if (arg0 == 5223) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local192 = intStack[intStackPtr + 1];
					Static231.method3474(local192 >> 14 & 0x3FFF, local192 & 0x3FFF, local157, false);
					return;
				}
				if (arg0 == 5224) {
					local157 = intStack[--intStackPtr];
					local1554 = Static107.method1990();
					if (local1554 != null) {
						local1925 = local1554.method935(coordConversionBuffer, local157 >> 28 & 0x3, local157 & 0x3FFF, local157 >> 14 & 0x3FFF);
						if (local1925) {
							intStack[intStackPtr++] = coordConversionBuffer[1];
							intStack[intStackPtr++] = coordConversionBuffer[2];
							return;
						}
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = -1;
					intStack[intStackPtr++] = -1;
					return;
				}
				if (arg0 == 5225) {
					local157 = intStack[--intStackPtr];
					local1554 = Static107.method1990();
					if (local1554 != null) {
						local1925 = local1554.method930(local157 >> 14 & 0x3FFF, coordConversionBuffer, local157 & 0x3FFF);
						if (local1925) {
							intStack[intStackPtr++] = coordConversionBuffer[1];
							intStack[intStackPtr++] = coordConversionBuffer[2];
							return;
						}
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = -1;
					intStack[intStackPtr++] = -1;
					return;
				}
				if (arg0 == 5226) {
					NPCTypeList.method5231(intStack[--intStackPtr]);
					return;
				}
				if (arg0 == 5227) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local192 = intStack[intStackPtr + 1];
					Static231.method3474(local192 >> 14 & 0x3FFF, local192 & 0x3FFF, local157, true);
					return;
				}
				if (arg0 == 5228) {
					Class120_Sub1.aBoolean482 = intStack[--intStackPtr] == 1;
					return;
				}
				if (arg0 == 5229) {
					intStack[intStackPtr++] = Class120_Sub1.aBoolean482 ? 1 : 0;
					return;
				}
				if (arg0 == 5230) {
					local157 = intStack[--intStackPtr];
					TextureOpKaleidoscope.method2336(local157);
					return;
				}
				@Pc(2483) Country local2483;
				if (arg0 == 5231) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local1313 = intStack[intStackPtr + 1] == 1;
					if (ReflectiveRipplingWaterEffect.aClass102_1 != null) {
						local2483 = ReflectiveRipplingWaterEffect.aClass102_1.method2700((long) local157);
						if (local2483 != null && !local1313) {
							local2483.method5684();
							return;
						}
						if (local2483 == null && local1313) {
							local2483 = new Country();
							ReflectiveRipplingWaterEffect.aClass102_1.method2703((long) local157, local2483);
						}
					}
					return;
				}
				@Pc(2525) Country local2525;
				if (arg0 == 5232) {
					local157 = intStack[--intStackPtr];
					if (ReflectiveRipplingWaterEffect.aClass102_1 != null) {
						local2525 = ReflectiveRipplingWaterEffect.aClass102_1.method2700((long) local157);
						intStack[intStackPtr++] = local2525 == null ? 0 : 1;
						return;
					}
					intStack[intStackPtr++] = 0;
					return;
				}
				if (arg0 == 5233) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local1313 = intStack[intStackPtr + 1] == 1;
					if (Class4_Sub6_Sub12.aClass102_12 != null) {
						local2483 = Class4_Sub6_Sub12.aClass102_12.method2700((long) local157);
						if (local2483 != null && !local1313) {
							local2483.method5684();
							return;
						}
						if (local2483 == null && local1313) {
							local2483 = new Country();
							Class4_Sub6_Sub12.aClass102_12.method2703((long) local157, local2483);
						}
					}
					return;
				}
				if (arg0 == 5234) {
					local157 = intStack[--intStackPtr];
					if (Class4_Sub6_Sub12.aClass102_12 != null) {
						local2525 = Class4_Sub6_Sub12.aClass102_12.method2700((long) local157);
						intStack[intStackPtr++] = local2525 == null ? 0 : 1;
						return;
					}
					intStack[intStackPtr++] = 0;
					return;
				}
				if (arg0 == 5235) {
					intStack[intStackPtr++] = Static148.area == null ? -1 : Static148.area.anInt1012;
					return;
				}
				if (arg0 == 5236) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local192 = intStack[intStackPtr + 1];
					local89 = local192 >> 14 & 0x3FFF;
					local95 = local192 & 0x3FFF;
					local2691 = Class230.method5096(local89, local95, local157);
					if (local2691 < 0) {
						intStack[intStackPtr++] = -1;
						return;
					}
					intStack[intStackPtr++] = local2691;
					return;
				}
			} else if (arg0 < 5400) {
				if (arg0 == 5300) {
					intStackPtr -= 2;
					local157 = intStack[intStackPtr];
					local192 = intStack[intStackPtr + 1];
					Class120.method4107(3, local192, local157, false);
					intStack[intStackPtr++] = BillowNoiseGenerator.aFrame3 == null ? 0 : 1;
					return;
				}
				if (arg0 == 5301) {
					if (BillowNoiseGenerator.aFrame3 != null) {
						Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
					}
					return;
				}
				if (arg0 == 5302) {
					@Pc(2769) Class103[] local2769 = EntityNode.method5675();
					intStack[intStackPtr++] = local2769.length;
					return;
				}
				if (arg0 == 5303) {
					local157 = intStack[--intStackPtr];
					@Pc(2793) Class103[] local2793 = EntityNode.method5675();
					intStack[intStackPtr++] = local2793[local157].anInt3173;
					intStack[intStackPtr++] = local2793[local157].anInt3174;
					return;
				}
				if (arg0 == 5305) {
					local157 = TextureOpBrightness.aClass50_Sub1_1.anInt3431;
					local192 = TextureOpBrightness.aClass50_Sub1_1.anInt3450;
					local89 = -1;
					@Pc(2830) Class103[] local2830 = EntityNode.method5675();
					for (local2691 = 0; local2691 < local2830.length; local2691++) {
						@Pc(2837) Class103 local2837 = local2830[local2691];
						if (local2837.anInt3173 == local157 && local2837.anInt3174 == local192) {
							local89 = local2691;
							break;
						}
					}
					intStack[intStackPtr++] = local89;
					return;
				}
				if (arg0 == 5306) {
					intStack[intStackPtr++] = Class269.method5664();
					return;
				}
				if (arg0 == 5307) {
					local157 = intStack[--intStackPtr];
					if (local157 >= 1 && local157 <= 2) {
						Class120.method4107(local157, -1, -1, false);
						return;
					}
					return;
				}
				if (arg0 == 5308) {
					intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3447;
					return;
				}
				if (arg0 == 5309) {
					local157 = intStack[--intStackPtr];
					if (local157 >= 1 && local157 <= 2) {
						TextureOpBrightness.aClass50_Sub1_1.anInt3447 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					return;
				}
			} else {
				@Pc(3345) String local3345;
				if (arg0 < 5500) {
					if (arg0 == 5400) {
						stringStackPtr -= 2;
						local75 = stringStack[stringStackPtr];
						local81 = stringStack[stringStackPtr + 1];
						local89 = intStack[--intStackPtr];
						PingWorker.method5476(AbstractArbBufferGL.aClass215_87);
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(JavaBillboardAttributes.method3856(local75) + JavaBillboardAttributes.method3856(local81) + 1);
						ParticleNode.aClass4_Sub12_Sub1_5.method2518(local75);
						ParticleNode.aClass4_Sub12_Sub1_5.method2518(local81);
						ParticleNode.aClass4_Sub12_Sub1_5.method2551(local89);
						return;
					}
					if (arg0 == 5401) {
						intStackPtr -= 2;
						GlVboArrayBuffer.aShortArray151[intStack[intStackPtr]] = (short) AnimBase.method3825(intStack[intStackPtr + 1]);
						Terrain.aClass206_3.method4710();
						Terrain.aClass206_3.method4701();
						Class196.aClass240_1.method5223();
						ModeWhere.method4940();
						return;
					}
					if (arg0 == 5405) {
						intStackPtr -= 2;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						if (local157 >= 0 && local157 < 2) {
							Class27_Sub2.anIntArrayArrayArray3[local157] = new int[local192 << 1][4];
						}
						return;
					}
					if (arg0 == 5406) {
						intStackPtr -= 7;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1] << 1;
						local89 = intStack[intStackPtr + 2];
						local95 = intStack[intStackPtr + 3];
						local2691 = intStack[intStackPtr + 4];
						@Pc(3105) int local3105 = intStack[intStackPtr + 5];
						@Pc(3111) int local3111 = intStack[intStackPtr + 6];
						if (local157 >= 0 && local157 < 2 && Class27_Sub2.anIntArrayArrayArray3[local157] != null && local192 >= 0 && local192 < Class27_Sub2.anIntArrayArrayArray3[local157].length) {
							Class27_Sub2.anIntArrayArrayArray3[local157][local192] = new int[] { (local89 >> 14 & 0x3FFF) << 7, local95, (local89 & 0x3FFF) << 7, local3111 };
							Class27_Sub2.anIntArrayArrayArray3[local157][local192 + 1] = new int[] { (local2691 >> 14 & 0x3FFF) << 7, local3105, (local2691 & 0x3FFF) << 7 };
						}
						return;
					}
					if (arg0 == 5407) {
						local157 = Class27_Sub2.anIntArrayArrayArray3[intStack[--intStackPtr]].length >> 1;
						intStack[intStackPtr++] = local157;
						return;
					}
					if (arg0 == 5411) {
						if (BillowNoiseGenerator.aFrame3 != null) {
							Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
						}
						if (ModelNormalsGL.aFrame1 != null) {
							Class190.method4216();
							System.exit(0);
							return;
						}
						local75 = Class16_Sub2.aString45 == null ? Class16_Sub1_Sub1.method298() : Class16_Sub2.aString45;
						PathingEntitySequence.method3929(Class126.aClass66_4, local75, Packet.anInt2973 == 1, false);
						return;
					}
					if (arg0 == 5419) {
						local75 = "";
						if (TextureOpDiagonalWaves.aClass199_7 != null) {
							if (TextureOpDiagonalWaves.aClass199_7.anObject29 == null) {
								local75 = Class164.method3913(TextureOpDiagonalWaves.aClass199_7.anInt5758);
							} else {
								local75 = (String) TextureOpDiagonalWaves.aClass199_7.anObject29;
							}
						}
						stringStack[stringStackPtr++] = local75;
						return;
					}
					if (arg0 == 5420) {
						intStack[intStackPtr++] = SignLink.anInt1987 == 3 ? 1 : 0;
						return;
					}
					if (arg0 == 5421) {
						if (BillowNoiseGenerator.aFrame3 != null) {
							Class120.method4107(TextureOpBrightness.aClass50_Sub1_1.anInt3447, -1, -1, false);
						}
						local75 = stringStack[--stringStackPtr];
						local1313 = intStack[--intStackPtr] == 1;
						local3345 = Class16_Sub1_Sub1.method298() + local75;
						PathingEntitySequence.method3929(Class126.aClass66_4, local3345, Packet.anInt2973 == 1, local1313);
						return;
					}
					if (arg0 == 5422) {
						stringStackPtr -= 2;
						local75 = stringStack[stringStackPtr];
						local81 = stringStack[stringStackPtr + 1];
						local89 = intStack[--intStackPtr];
						if (local75.length() > 0) {
							if (LocTypeList.aStringArray40 == null) {
								LocTypeList.aStringArray40 = new String[Class9_Sub3.anIntArray116[DynamicWall.aClass121_3.anInt3570]];
							}
							LocTypeList.aStringArray40[local89] = local75;
						}
						if (local81.length() > 0) {
							if (FramebufferCompositor.aStringArray39 == null) {
								FramebufferCompositor.aStringArray39 = new String[Class9_Sub3.anIntArray116[DynamicWall.aClass121_3.anInt3570]];
							}
							FramebufferCompositor.aStringArray39[local89] = local81;
						}
						return;
					}
					if (arg0 == 5423) {
						System.out.println(stringStack[--stringStackPtr]);
						return;
					}
					if (arg0 == 5424) {
						intStackPtr -= 11;
						LocalRequester.anInt7011 = intStack[intStackPtr];
						TextureOpTile.anInt6373 = intStack[intStackPtr + 1];
						QuickChatValueType.anInt3062 = intStack[intStackPtr + 2];
						EntityNodeList.anInt2738 = intStack[intStackPtr + 3];
						FloTypeList.anInt3850 = intStack[intStackPtr + 4];
						StillWaterEffect.anInt6931 = intStack[intStackPtr + 5];
						QuickChatCategoryType.anInt5073 = intStack[intStackPtr + 6];
						Class4_Sub6_Sub15.anInt3601 = intStack[intStackPtr + 7];
						Terrain.anInt7297 = intStack[intStackPtr + 8];
						Class3_Sub3.anInt2387 = intStack[intStackPtr + 9];
						SoundBankCache.anInt5199 = intStack[intStackPtr + 10];
						GlGround.aClass76_60.method2125(FloTypeList.anInt3850);
						GlGround.aClass76_60.method2125(StillWaterEffect.anInt6931);
						GlGround.aClass76_60.method2125(QuickChatCategoryType.anInt5073);
						GlGround.aClass76_60.method2125(Class4_Sub6_Sub15.anInt3601);
						GlGround.aClass76_60.method2125(Terrain.anInt7297);
						Class4_Sub6_Sub24.aClass57_19 = null;
						Class85.aClass57_12 = null;
						Class93.aClass57_15 = null;
						Class177.aClass57_20 = null;
						PointLight.aClass57_17 = null;
						QuickchatMessageType.aClass57_4 = null;
						MidiPcmStream.aClass57_9 = null;
						ModeWhere.aClass57_21 = null;
						Class177.aBoolean481 = true;
						return;
					}
					if (arg0 == 5425) {
						Class4_Sub29.method3601();
						Class177.aBoolean481 = false;
						return;
					}
					if (arg0 == 5426) {
						intStackPtr -= 2;
						Class30.anInt770 = intStack[intStackPtr];
						Class6.anInt5453 = intStack[intStackPtr + 1];
						return;
					}
					if (arg0 == 5427) {
						intStackPtr -= 2;
						Class70_Sub1.anInt2066 = intStack[intStackPtr + 1];
						return;
					}
					if (arg0 == 5428) {
						intStackPtr -= 2;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						intStack[intStackPtr++] = OpenGlRenderBuffer.method2771(local157, local192) ? 1 : 0;
						return;
					}
					if (arg0 == 5429) {
						GlVboElementsArrayBuffer.method5186(stringStack[--stringStackPtr], false);
						return;
					}
					if (arg0 == 5430) {
						try {
							Static458.method2593(Class126.aClass66_4.anApplet1, "accountcreated");
							return;
						} catch (@Pc(3639) Throwable local3639) {
							return;
						}
					}
				} else if (arg0 < 5600) {
					if (arg0 == 5500) {
						intStackPtr -= 4;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						local95 = intStack[intStackPtr + 3];
						Class16_Sub2_Sub3.method3596(local192, false, local95, (local157 & 0x3FFF) - OpenGLTextureCache.anInt1771, (local157 >> 14 & 0x3FFF) - Class50_Sub1.anInt3453, local89);
						return;
					}
					if (arg0 == 5501) {
						intStackPtr -= 4;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						local95 = intStack[intStackPtr + 3];
						Class189.method4175(local95, local192, (local157 & 0x3FFF) - OpenGLTextureCache.anInt1771, local89, (local157 >> 14 & 0x3FFF) - Class50_Sub1.anInt3453);
						return;
					}
					if (arg0 == 5502) {
						intStackPtr -= 6;
						local157 = intStack[intStackPtr];
						if (local157 >= 2) {
							throw new RuntimeException();
						}
						Js5TextureSource.anInt2836 = local157;
						local192 = intStack[intStackPtr + 1];
						if (local192 + 1 >= Class27_Sub2.anIntArrayArrayArray3[Js5TextureSource.anInt2836].length >> 1) {
							throw new RuntimeException();
						}
						Static145.anInt2908 = local192;
						Class4_Sub7.anInt605 = 0;
						TextureOpColorGradient.anInt1765 = intStack[intStackPtr + 2];
						GpuFaceNormal.anInt6951 = intStack[intStackPtr + 3];
						local89 = intStack[intStackPtr + 4];
						if (local89 >= 2) {
							throw new RuntimeException();
						}
						Class27_Sub2.anInt2797 = local89;
						local95 = intStack[intStackPtr + 5];
						if (local95 + 1 >= Class27_Sub2.anIntArrayArrayArray3[Class27_Sub2.anInt2797].length >> 1) {
							throw new RuntimeException();
						}
						Class111.anInt3368 = local95;
						Class3_Sub3.anInt2386 = 3;
						return;
					}
					if (arg0 == 5503) {
						GlParticleRenderer.method913();
						return;
					}
					if (arg0 == 5504) {
						intStackPtr -= 2;
						MusicPatchNode.method3451(intStack[intStackPtr], intStack[intStackPtr + 1]);
						return;
					}
					if (arg0 == 5505) {
						intStack[intStackPtr++] = (int) Class16_Sub2.aFloat142 >> 3;
						return;
					}
					if (arg0 == 5506) {
						intStack[intStackPtr++] = (int) Static368.aFloat192 >> 3;
						return;
					}
					if (arg0 == 5507) {
						ModelNormalsGL.method3442();
						return;
					}
					if (arg0 == 5508) {
						Class4_Sub6_Sub33.method5041();
						return;
					}
					if (arg0 == 5509) {
						Class4_Sub6_Sub4.method1276();
						return;
					}
					if (arg0 == 5510) {
						GzipDecompressor.method4170();
						return;
					}
					if (arg0 == 5511) {
						local157 = intStack[--intStackPtr];
						local192 = local157 >> 14 & 0x3FFF;
						local89 = local157 & 0x3FFF;
						local192 -= Class50_Sub1.anInt3453;
						if (local192 < 0) {
							local192 = 0;
						} else if (local192 >= GLBillowTextureData.anInt5666) {
							local192 = GLBillowTextureData.anInt5666;
						}
						local89 -= OpenGLTextureCache.anInt1771;
						if (local89 < 0) {
							local89 = 0;
						} else if (local89 >= Static283.anInt5187) {
							local89 = Static283.anInt5187;
						}
						Js5WorkerThread.anInt7059 = (local192 << 7) + 64;
						Class4_Sub45.anInt7360 = (local89 << 7) + 64;
						Class3_Sub3.anInt2386 = 4;
						return;
					}
					if (arg0 == 5512) {
						TextureOpIrregularBricks.method1773();
						return;
					}
				} else if (arg0 < 5700) {
					if (arg0 == 5600) {
						stringStackPtr -= 2;
						local75 = stringStack[stringStackPtr];
						local81 = stringStack[stringStackPtr + 1];
						local89 = intStack[--intStackPtr];
						if (VertexDeclarationComponent.anInt6667 != 10) {
							return;
						}
						if (NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0 && EasedAngle.anInt862 == 0) {
							Class145_Sub1.method5618(local75, local89, local81);
							return;
						}
						return;
					}
					if (arg0 == 5601) {
						Class183.method4146();
						return;
					}
					if (arg0 == 5602) {
						if (CacheableNodeByteArray.anInt4506 == 0) {
							StringNode.anInt5302 = -2;
						}
						return;
					}
					if (arg0 == 5603) {
						intStackPtr -= 4;
						if (VertexDeclarationComponent.anInt6667 != 10) {
							return;
						}
						if (NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0 && EasedAngle.anInt862 == 0) {
							Class99_Sub1.method3233(intStack[intStackPtr + 1], intStack[intStackPtr], intStack[intStackPtr + 2], intStack[intStackPtr + 3]);
							return;
						}
						return;
					}
					if (arg0 == 5604) {
						stringStackPtr--;
						if (VertexDeclarationComponent.anInt6667 != 10) {
							return;
						}
						if (NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0 && EasedAngle.anInt862 == 0) {
							Class27_Sub4.method3942(Class4_Sub6_Sub7.method1684(stringStack[stringStackPtr]));
							return;
						}
						return;
					}
					if (arg0 == 5605) {
						stringStackPtr -= 3;
						intStackPtr -= 7;
						if (VertexDeclarationComponent.anInt6667 != 10) {
							return;
						}
						if (NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0 && EasedAngle.anInt862 == 0) {
							Static179.method2848(intStack[intStackPtr + 1], intStack[intStackPtr + 5] == 1, stringStack[stringStackPtr + 1], intStack[intStackPtr + 6] == 1, intStack[intStackPtr + 4] == 1, intStack[intStackPtr + 2], intStack[intStackPtr + 3], Class4_Sub6_Sub7.method1684(stringStack[stringStackPtr]), stringStack[stringStackPtr + 2], intStack[intStackPtr]);
							return;
						}
						return;
					}
					if (arg0 == 5606) {
						if (ItemType.anInt6183 == 0) {
							RuntimeException_Sub1.anInt4623 = -2;
						}
						return;
					}
					if (arg0 == 5607) {
						intStack[intStackPtr++] = StringNode.anInt5302;
						return;
					}
					if (arg0 == 5608) {
						intStack[intStackPtr++] = PlayerVarDomain.anInt3398;
						return;
					}
					if (arg0 == 5609) {
						intStack[intStackPtr++] = RuntimeException_Sub1.anInt4623;
						return;
					}
					if (arg0 == 5610) {
						for (local157 = 0; local157 < 5; local157++) {
							stringStack[stringStackPtr++] = Class126.aStringArray16.length > local157 ? Static347.method4680(Class126.aStringArray16[local157]) : "";
						}
						Class126.aStringArray16 = null;
						return;
					}
					if (arg0 == 5611) {
						intStack[intStackPtr++] = Class177.anInt5328;
						return;
					}
				} else if (arg0 < 6100) {
					if (arg0 == 6001) {
						local157 = intStack[--intStackPtr];
						if (local157 < 1) {
							local157 = 1;
						}
						if (local157 > 4) {
							local157 = 4;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3436 = local157;
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6002) {
						TextureOpBrightness.aClass50_Sub1_1.method2859(intStack[--intStackPtr] == 1);
						PathingEntity.method4744();
						OpenGLArbTextureCubeMapGenerator.method3238();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6003) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean306 = intStack[--intStackPtr] == 1;
						OpenGLArbTextureCubeMapGenerator.method3238();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6005) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean305 = intStack[--intStackPtr] == 1;
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6006) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean296 = intStack[--intStackPtr] == 1;
						MonoStereoData.aClass19_16.setShrinkTextures(!TextureOpBrightness.aClass50_Sub1_1.aBoolean296);
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6007) {
						TextureOpBrightness.aClass50_Sub1_1.anInt3433 = intStack[--intStackPtr];
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6008) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean295 = intStack[--intStackPtr] == 1;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6009) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean293 = intStack[--intStackPtr] == 1;
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6010) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean302 = intStack[--intStackPtr] == 1;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6011) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 2) {
							local157 = 0;
						}
						TextureOpBrightness.aClass50_Sub1_1.method2852(Packet.anInt2973, local157);
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6012) {
						TextureOpBrightness.aClass50_Sub1_1.method2855(intStack[--intStackPtr] == 1, Packet.anInt2973);
						Class16_Sub4_Sub2.method2149();
						Class133.method3355();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6014) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean294 = intStack[--intStackPtr] == 1;
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6015) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean298 = intStack[--intStackPtr] == 1;
						PathingEntity.method4744();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6016) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 2) {
							local157 = 0;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3440 = local157;
						ParticleList.method3551(Packet.anInt2973);
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6017) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean291 = intStack[--intStackPtr] == 1;
						Static167.method2740();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6018) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0) {
							local157 = 0;
						}
						if (local157 > 127) {
							local157 = 127;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3448 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6019) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0) {
							local157 = 0;
						}
						if (local157 > 255) {
							local157 = 255;
						}
						if (local157 != TextureOpBrightness.aClass50_Sub1_1.anInt3439) {
							if (TextureOpBrightness.aClass50_Sub1_1.anInt3439 == 0 && JavaThreadResource.mapBuildLevel != -1) {
								Static32.method625(local157, JavaThreadResource.mapBuildLevel, TextureCombinerOp.aClass76_54);
								TextureOpSquareWaveform.aBoolean666 = false;
							} else if (local157 == 0) {
								StructTypeList.method1986();
								TextureOpSquareWaveform.aBoolean666 = false;
							} else {
								Class21.method522(local157);
							}
							TextureOpBrightness.aClass50_Sub1_1.anInt3439 = local157;
						}
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6020) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0) {
							local157 = 0;
						}
						if (local157 > 127) {
							local157 = 127;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3449 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6021) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean309 = intStack[--intStackPtr] == 1;
						OpenGLArbTextureCubeMapGenerator.method3238();
						return;
					}
					if (arg0 == 6023) {
						local157 = intStack[--intStackPtr];
						local1313 = false;
						if (local157 < 0) {
							local157 = 0;
						}
						if (local157 > 2) {
							local157 = 2;
						}
						if (BreakingWaterEffect.anInt1503 < 96) {
							local157 = 0;
							local1313 = true;
						}
						HashTable.method2673(local157);
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						intStack[intStackPtr++] = local1313 ? 0 : 1;
						return;
					}
					if (arg0 == 6024) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 2) {
							local157 = 0;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3442 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6025) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > client.method898(BreakingWaterEffect.anInt1503)) {
							local157 = 0;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3434 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6027) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 1) {
							local157 = 0;
						}
						Class210.method4772(local157 == 1);
						return;
					}
					if (arg0 == 6028) {
						boolean customCursors = intStack[--intStackPtr] != 0;
						TextureOpBrightness.aClass50_Sub1_1.aBoolean304 = customCursors;
						// The switch itself lives in CursorSettings, so the interface
						// toggle, the console and the saved file always agree.
						CursorSettings.setEnabled(customCursors);
						CursorSettings.save();
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6029) {
						TextureOpBrightness.aClass50_Sub1_1.anInt3433 = intStack[--intStackPtr];
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6030) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean289 = intStack[--intStackPtr] != 0;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						PathingEntity.method4744();
						return;
					}
					if (arg0 == 6031) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 3) {
							local157 = 2;
						}
						ParticleList.method3551(local157);
						return;
					}
					if (arg0 == 6032) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 3) {
							local157 = 2;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3445 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6033) {
						local157 = intStack[--intStackPtr];
						if (local157 < 0 || local157 > 4) {
							local157 = 2;
						}
						TextureOpBrightness.aClass50_Sub1_1.anInt3437 = local157;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6034) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean307 = intStack[--intStackPtr] == 1;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						Class16_Sub4_Sub2.method2149();
						RuntimeException_Sub1.aBoolean425 = false;
						return;
					}
					if (arg0 == 6035) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean308 = intStack[--intStackPtr] == 1;
						PathingEntity.method4744();
						OpenGLArbTextureCubeMapGenerator.method3238();
						return;
					}
				} else if (arg0 < 6200) {
					if (arg0 == 6101) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3436;
						return;
					}
					if (arg0 == 6102) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.method2857(Packet.anInt2973) ? 1 : 0;
						return;
					}
					if (arg0 == 6103) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean306 ? 1 : 0;
						return;
					}
					if (arg0 == 6105) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean305 ? 1 : 0;
						return;
					}
					if (arg0 == 6106) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean296 ? 1 : 0;
						return;
					}
					if (arg0 == 6107) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3433;
						return;
					}
					if (arg0 == 6108) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean295 ? 1 : 0;
						return;
					}
					if (arg0 == 6109) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean293 ? 1 : 0;
						return;
					}
					if (arg0 == 6110) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean302 ? 1 : 0;
						return;
					}
					if (arg0 == 6111) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.method2850(Packet.anInt2973);
						return;
					}
					if (arg0 == 6112) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.method2854(Packet.anInt2973) ? 1 : 0;
						return;
					}
					if (arg0 == 6114) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean294 ? 1 : 0;
						return;
					}
					if (arg0 == 6115) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean298 ? 1 : 0;
						return;
					}
					if (arg0 == 6116) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3440;
						return;
					}
					if (arg0 == 6117) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean291 ? 1 : 0;
						return;
					}
					if (arg0 == 6118) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3448;
						return;
					}
					if (arg0 == 6119) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3439;
						return;
					}
					if (arg0 == 6120) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3449;
						return;
					}
					if (arg0 == 6121) {
						intStack[intStackPtr++] = MonoStereoData.aClass19_16.supportsAntiAliasing() ? 1 : 0;
						return;
					}
					if (arg0 == 6123) {
						intStack[intStackPtr++] = TextureType.method3279();
						return;
					}
					if (arg0 == 6124) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3442;
						return;
					}
					if (arg0 == 6125) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3434;
						return;
					}
					if (arg0 == 6126) {
						intStack[intStackPtr++] = MonoStereoData.aClass19_16.method4252() ? 1 : 0;
						return;
					}
					if (arg0 == 6127) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean300 ? 1 : 0;
						return;
					}
					if (arg0 == 6128) {
						intStack[intStackPtr++] = CursorSettings.isEnabled() ? 1 : 0;
						return;
					}
					if (arg0 == 6129) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3433;
						return;
					}
					if (arg0 == 6130) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean289 ? 1 : 0;
						return;
					}
					if (arg0 == 6131) {
						intStack[intStackPtr++] = Packet.anInt2973;
						return;
					}
					if (arg0 == 6132) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3445;
						return;
					}
					if (arg0 == 6133) {
						intStack[intStackPtr++] = SignLink.anInt1987 == 1 || SignLink.anInt1987 == 4 ? 1 : 0;
						return;
					}
					if (arg0 == 6134) {
						intStack[intStackPtr++] = client.method898(BreakingWaterEffect.anInt1503);
						return;
					}
					if (arg0 == 6135) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3437;
						return;
					}
					if (arg0 == 6136) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean307 ? 1 : 0;
						return;
					}
					if (arg0 == 6137) {
						@Pc(5679) boolean local5679 = true;
						try {
							local5679 = jagmisc.getTotalPhysicalMemory() > 268435456L;
						} catch (@Pc(5689) Throwable local5689) {
						}
						intStack[intStackPtr++] = local5679 ? 1 : 0;
						return;
					}
				} else if (arg0 < 6300) {
					if (arg0 == 6200) {
						intStackPtr -= 2;
						Static298.aShort67 = (short) intStack[intStackPtr];
						if (Static298.aShort67 <= 0) {
							Static298.aShort67 = 256;
						}
						Class83.aShort24 = (short) intStack[intStackPtr + 1];
						if (Class83.aShort24 <= 0) {
							Class83.aShort24 = 205;
						}
						return;
					}
					if (arg0 == 6201) {
						intStackPtr -= 2;
						Static14.aShort1 = (short) intStack[intStackPtr];
						if (Static14.aShort1 <= 0) {
							Static14.aShort1 = 256;
						}
						BzipDecompressor.aShort13 = (short) intStack[intStackPtr + 1];
						if (BzipDecompressor.aShort13 <= 0) {
							BzipDecompressor.aShort13 = 320;
						}
						return;
					}
					if (arg0 == 6202) {
						intStackPtr -= 4;
						TextureOpMandelbrot.aShort66 = (short) intStack[intStackPtr];
						if (TextureOpMandelbrot.aShort66 <= 0) {
							TextureOpMandelbrot.aShort66 = 1;
						}
						Class4_Sub17.aShort18 = (short) intStack[intStackPtr + 1];
						if (Class4_Sub17.aShort18 <= 0) {
							Class4_Sub17.aShort18 = 32767;
						} else if (Class4_Sub17.aShort18 < TextureOpMandelbrot.aShort66) {
							Class4_Sub17.aShort18 = TextureOpMandelbrot.aShort66;
						}
						CursorType.aShort19 = (short) intStack[intStackPtr + 2];
						if (CursorType.aShort19 <= 0) {
							CursorType.aShort19 = 1;
						}
						Class244.aShort100 = (short) intStack[intStackPtr + 3];
						if (Class244.aShort100 <= 0) {
							Class244.aShort100 = 32767;
							return;
						}
						if (Class244.aShort100 < CursorType.aShort19) {
							Class244.aShort100 = CursorType.aShort19;
						}
						return;
					}
					if (arg0 == 6203) {
						GameShell.method878(0, ParticleProducerType.aClass247_7.anInt6833, 0, false, ParticleProducerType.aClass247_7.anInt6805);
						intStack[intStackPtr++] = Class196.anInt5685;
						intStack[intStackPtr++] = Class16_Sub4_Sub2.anInt2527;
						return;
					}
					if (arg0 == 6204) {
						intStack[intStackPtr++] = Static14.aShort1;
						intStack[intStackPtr++] = BzipDecompressor.aShort13;
						return;
					}
					if (arg0 == 6205) {
						intStack[intStackPtr++] = Static298.aShort67;
						intStack[intStackPtr++] = Class83.aShort24;
						return;
					}
				} else if (arg0 < 6400) {
					if (arg0 == 6300) {
						intStack[intStackPtr++] = (int) (Class168.method3962() / 60000L);
						return;
					}
					if (arg0 == 6301) {
						intStack[intStackPtr++] = (int) (Class168.method3962() / 86400000L) - 11745;
						return;
					}
					if (arg0 == 6302) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						LOCAL_CALENDAR.clear();
						LOCAL_CALENDAR.set(11, 12);
						LOCAL_CALENDAR.set(local89, local192, local157);
						intStack[intStackPtr++] = (int) (LOCAL_CALENDAR.getTime().getTime() / 86400000L) - 11745;
						return;
					}
					if (arg0 == 6303) {
						LOCAL_CALENDAR.clear();
						LOCAL_CALENDAR.setTime(new Date(Class168.method3962()));
						intStack[intStackPtr++] = LOCAL_CALENDAR.get(1);
						return;
					}
					if (arg0 == 6304) {
						local157 = intStack[--intStackPtr];
						local1313 = true;
						if (local157 < 0) {
							local1313 = (local157 + 1) % 4 == 0;
						} else if (local157 < 1582) {
							local1313 = local157 % 4 == 0;
						} else if (local157 % 4 != 0) {
							local1313 = false;
						} else if (local157 % 100 != 0) {
							local1313 = true;
						} else if (local157 % 400 != 0) {
							local1313 = false;
						}
						intStack[intStackPtr++] = local1313 ? 1 : 0;
						return;
					}
				} else if (arg0 < 6500) {
					if (arg0 == 6405) {
						intStack[intStackPtr++] = GlVboElementsArrayBuffer.method5187() ? 1 : 0;
						return;
					}
					if (arg0 == 6406) {
						intStack[intStackPtr++] = EffectiveVertex.method4105() ? 1 : 0;
						return;
					}
				} else if (arg0 < 6600) {
					if (arg0 == 6500) {
						if (VertexDeclarationComponent.anInt6667 == 10 && NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0) {
							intStack[intStackPtr++] = Class4_Sub18.method2035() == -1 ? 0 : 1;
							return;
						}
						intStack[intStackPtr++] = 1;
						return;
					}
					@Pc(6188) Class10 local6188;
					@Pc(6155) Class78_Sub1 local6155;
					if (arg0 == 6501) {
						local6155 = Skybox.method5250();
						if (local6155 != null) {
							intStack[intStackPtr++] = local6155.anInt3881;
							intStack[intStackPtr++] = local6155.anInt3874;
							stringStack[stringStackPtr++] = local6155.aString37;
							local6188 = local6155.method3175();
							intStack[intStackPtr++] = local6188.anInt169;
							stringStack[stringStackPtr++] = local6188.aString3;
							intStack[intStackPtr++] = local6155.anInt3878;
							intStack[intStackPtr++] = local6155.anInt3887;
							return;
						}
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = 0;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						intStack[intStackPtr++] = 0;
						return;
					}
					if (arg0 == 6502) {
						local6155 = Static281.method3960();
						if (local6155 != null) {
							intStack[intStackPtr++] = local6155.anInt3881;
							intStack[intStackPtr++] = local6155.anInt3874;
							stringStack[stringStackPtr++] = local6155.aString37;
							local6188 = local6155.method3175();
							intStack[intStackPtr++] = local6188.anInt169;
							stringStack[stringStackPtr++] = local6188.aString3;
							intStack[intStackPtr++] = local6155.anInt3878;
							intStack[intStackPtr++] = local6155.anInt3887;
							return;
						}
						intStack[intStackPtr++] = -1;
						intStack[intStackPtr++] = 0;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						intStack[intStackPtr++] = 0;
						return;
					}
					if (arg0 == 6503) {
						local157 = intStack[--intStackPtr];
						if (VertexDeclarationComponent.anInt6667 == 10 && NPCTypeList.anInt6682 == 0 && CacheableNodeByteArray.anInt4506 == 0 && ItemType.anInt6183 == 0) {
							intStack[intStackPtr++] = Class105.method5279(local157) ? 1 : 0;
							return;
						}
						intStack[intStackPtr++] = 0;
						return;
					}
					if (arg0 == 6504) {
						TextureOpBrightness.aClass50_Sub1_1.anInt3428 = intStack[--intStackPtr];
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6505) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.anInt3428;
						return;
					}
					if (arg0 == 6506) {
						local157 = intStack[--intStackPtr];
						@Pc(6504) Class78_Sub1 local6504 = Class126.method3192(local157);
						if (local6504 != null) {
							intStack[intStackPtr++] = local6504.anInt3874;
							stringStack[stringStackPtr++] = local6504.aString37;
							@Pc(6528) Class10 local6528 = local6504.method3175();
							intStack[intStackPtr++] = local6528.anInt169;
							stringStack[stringStackPtr++] = local6528.aString3;
							intStack[intStackPtr++] = local6504.anInt3878;
							intStack[intStackPtr++] = local6504.anInt3887;
							return;
						}
						intStack[intStackPtr++] = -1;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						stringStack[stringStackPtr++] = "";
						intStack[intStackPtr++] = 0;
						intStack[intStackPtr++] = 0;
						return;
					}
					if (arg0 == 6507) {
						intStackPtr -= 4;
						local157 = intStack[intStackPtr];
						local1313 = intStack[intStackPtr + 1] == 1;
						local89 = intStack[intStackPtr + 2];
						local1965 = intStack[intStackPtr + 3] == 1;
						Class28.method669(local157, local1313, local89, local1965);
						return;
					}
					if (arg0 == 6508) {
						TextureOpPerlinNoise.method2288();
						return;
					}
					if (arg0 == 6509) {
						if (VertexDeclarationComponent.anInt6667 != 10) {
							return;
						}
						Class10.aBoolean18 = intStack[--intStackPtr] == 1;
						return;
					}
				} else if (arg0 < 6700) {
					if (arg0 == 6600) {
						TextureOpBrightness.aClass50_Sub1_1.aBoolean303 = intStack[--intStackPtr] == 1;
						TextureOpBrightness.aClass50_Sub1_1.method2856(Class126.aClass66_4);
						return;
					}
					if (arg0 == 6601) {
						intStack[intStackPtr++] = TextureOpBrightness.aClass50_Sub1_1.aBoolean303 ? 1 : 0;
						return;
					}
				} else if (arg0 < 6800 && Class95.aClass117_9 == Texture3DShaderGL.aClass117_1) {
					if (arg0 == 6700) {
						local157 = MusicPatch.aClass102_29.method2698();
						if (EnumType.anInt5766 != -1) {
							local157++;
						}
						intStack[intStackPtr++] = local157;
						return;
					}
					if (arg0 == 6701) {
						local157 = intStack[--intStackPtr];
						if (EnumType.anInt5766 != -1) {
							if (local157 == 0) {
								intStack[intStackPtr++] = EnumType.anInt5766;
								return;
							}
							local157--;
						}
						@Pc(6786) Class4_Sub43 local6786 = (Class4_Sub43) MusicPatch.aClass102_29.method2705();
						while (local157-- > 0) {
							local6786 = (Class4_Sub43) MusicPatch.aClass102_29.method2704();
						}
						intStack[intStackPtr++] = local6786.anInt6979;
						return;
					}
					if (arg0 == 6702) {
						local157 = intStack[--intStackPtr];
						if (Class4_Sub36.aClass247ArrayArray2[local157] == null) {
							stringStack[stringStackPtr++] = "";
							return;
						}
						local81 = Class4_Sub36.aClass247ArrayArray2[local157][0].name;
						if (local81 == null) {
							stringStack[stringStackPtr++] = "";
							return;
						}
						stringStack[stringStackPtr++] = local81.substring(0, local81.indexOf(58));
						return;
					}
					if (arg0 == 6703) {
						local157 = intStack[--intStackPtr];
						if (Class4_Sub36.aClass247ArrayArray2[local157] == null) {
							intStack[intStackPtr++] = 0;
							return;
						}
						intStack[intStackPtr++] = Class4_Sub36.aClass247ArrayArray2[local157].length;
						return;
					}
					if (arg0 == 6704) {
						intStackPtr -= 2;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						if (Class4_Sub36.aClass247ArrayArray2[local157] == null) {
							stringStack[stringStackPtr++] = "";
							return;
						}
						local3345 = Class4_Sub36.aClass247ArrayArray2[local157][local192].name;
						if (local3345 == null) {
							stringStack[stringStackPtr++] = "";
							return;
						}
						stringStack[stringStackPtr++] = local3345;
						return;
					}
					if (arg0 == 6705) {
						intStackPtr -= 2;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						if (Class4_Sub36.aClass247ArrayArray2[local157] == null) {
							intStack[intStackPtr++] = 0;
							return;
						}
						intStack[intStackPtr++] = Class4_Sub36.aClass247ArrayArray2[local157][local192].serverTriggers;
						return;
					}
					if (arg0 == 6706) {
						return;
					}
					if (arg0 == 6707) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(1, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6708) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(2, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6709) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(3, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6710) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(4, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6711) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(5, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6712) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(6, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6713) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(7, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6714) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(8, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6715) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(9, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6716) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						Component.method5309(10, "", local157 << 16 | local192, local89);
						return;
					}
					if (arg0 == 6717) {
						intStackPtr -= 3;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						local89 = intStack[intStackPtr + 2];
						@Pc(7374) com.jagex.game.runetek5.config.rsinterface.Component local7374 = EnvironmentSampler.method3230(local157 << 16 | local192, local89);
						Class16_Sub5.method5139();
						@Pc(7379) GlRectangleTexture local7379 = client.method900(local7374);
						SoundBankCache.method3975(local7379.anInt5110, local7379.method3911(), local7374);
						return;
					}
				} else if (arg0 < 6900) {
					@Pc(7407) MECType local7407;
					if (arg0 == 6800) {
						local157 = intStack[--intStackPtr];
						local7407 = Class4_Sub6_Sub31.aClass182_4.method4124(local157);
						if (local7407.aString47 == null) {
							stringStack[stringStackPtr++] = "";
							return;
						}
						stringStack[stringStackPtr++] = local7407.aString47;
						return;
					}
					if (arg0 == 6801) {
						local157 = intStack[--intStackPtr];
						local7407 = Class4_Sub6_Sub31.aClass182_4.method4124(local157);
						intStack[intStackPtr++] = local7407.anInt4930;
						return;
					}
					if (arg0 == 6802) {
						local157 = intStack[--intStackPtr];
						local7407 = Class4_Sub6_Sub31.aClass182_4.method4124(local157);
						intStack[intStackPtr++] = local7407.anInt4904;
						return;
					}
					if (arg0 == 6803) {
						local157 = intStack[--intStackPtr];
						local7407 = Class4_Sub6_Sub31.aClass182_4.method4124(local157);
						intStack[intStackPtr++] = local7407.anInt4919;
						return;
					}
					if (arg0 == 6804) {
						intStackPtr -= 2;
						local157 = intStack[intStackPtr];
						local192 = intStack[intStackPtr + 1];
						@Pc(7529) Class133 local7529 = Class4_Sub1_Sub11.aClass180_1.method4121(local192);
						if (local7529.method3350()) {
							stringStack[stringStackPtr++] = Class4_Sub6_Sub31.aClass182_4.method4124(local157).method3777(local7529.aString40, local192);
							return;
						}
						intStack[intStackPtr++] = Class4_Sub6_Sub31.aClass182_4.method4124(local157).method3779(local7529.anInt4123, local192);
						return;
					}
				}
			}
		}
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!nr", name = "a", descriptor = "(Lclient!pn;II)V")
	public static void method3899(@OriginalArg(0) Class186 arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(5) ClientScript local5 = Class16_Sub4_Sub2.method2147(arg2, arg1, arg0);
		if (local5 == null) {
			return;
		}
		intLocals = new int[local5.anInt55];
		stringVars = new String[local5.anInt56];
		if (local5.aClass186_1 == Static312.aClass186_13 || local5.aClass186_1 == DequeIterator.aClass186_7 || local5.aClass186_1 == AbstractFontRenderer.aClass186_16) {
			@Pc(30) int local30 = 0;
			@Pc(32) int local32 = 0;
			if (GlGroundLayer.aClass247_2 != null) {
				local30 = GlGroundLayer.aClass247_2.anInt6836;
				local32 = GlGroundLayer.aClass247_2.anInt6859;
			}
			intLocals[0] = MaterialType.aClass80_1.method2234() - local30;
			intLocals[1] = MaterialType.aClass80_1.getDefaultValue() - local32;
		}
		executeScript(local5, 200000);
	}
}
