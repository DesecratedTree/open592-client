package com.jagex.game.runetek5.toolkit.opengl.buffer;

import jaclib.memory.Stream;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;

import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static105;
import com.jagex.game.runetek5.deob.Static107;
import com.jagex.game.runetek5.deob.Static167;
import com.jagex.game.runetek5.deob.Static214;
import com.jagex.game.runetek5.deob.Static277;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.deob.Static433;
import com.jagex.game.runetek5.deob.Static45;
import com.jagex.game.runetek5.deob.Static47;
import com.jagex.game.runetek5.deob.TileLighting;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.toolkit.opengl.buffer.*;
import com.jagex.game.runetek5.deob.Class4_Sub43;
import com.jagex.game.runetek5.config.material.texture.ColorImageCache;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.js5.NamedFileReference;
import com.jagex.game.runetek5.entity.EntityNodeList;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.graphics.texture.sampler.GlRectangleTexture;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpTile;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.config.material.texture.TextureOpKaleidoscope;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpMonochromeEdgeDetector;
import com.jagex.game.runetek5.deob.Class225;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.cache.compression.GzipDecompressor;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub2;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub16;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.toolkit.opengl.GlRenderList;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.config.flo.FloTypeList;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.config.mec.MECTypeList;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.QuickChatPhrase;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.config.mapsprite.MapSpriteTypeList;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.cache.node.lru.StructType;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class244;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.deob.Class136;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.ByteArrayWrapper;
import com.jagex.game.runetek5.EnvironmentContext;
import com.jagex.game.runetek5.config.enumtype.EnumTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.toolkit.opengl.effect.ReflectiveRipplingWaterEffect;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.config.material.texture.TextureOpFlip;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.tools.Isaac;
import com.jagex.game.runetek5.deob.Class193;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.deob.Class95_Sub3;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class75;
import com.jagex.game.runetek5.config.material.texture.MonochromeImageCache;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.cache.node.CacheableNodeQueue;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.client.input.mouse.SimpleMouseLog;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Game;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.graphics.texture.water.NativeWaterTexture;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.js5.request.RequestManager;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Class56;
@OriginalClass("client!hk")
public final class GlBitBuffer extends Packet {

	@OriginalMember(owner = "client!hk", name = "<init>", descriptor = "(I)V")
	public GlBitBuffer(@OriginalArg(0) int size) {
		super(size);
	}

	@OriginalMember(owner = "client!hk", name = "a", descriptor = "(FI)V")
	public void method2552(@OriginalArg(0) float arg0) {
		@Pc(12) int local12 = Stream.floatToRawIntBits(arg0);
		super.buffer[super.currentPosition++] = (byte) local12;
		super.buffer[super.currentPosition++] = (byte) (local12 >> 8);
		super.buffer[super.currentPosition++] = (byte) (local12 >> 16);
		super.buffer[super.currentPosition++] = (byte) (local12 >> 24);
	}

	@OriginalMember(owner = "client!hk", name = "a", descriptor = "(FB)V")
	public void method2556(@OriginalArg(0) float arg0) {
		@Pc(6) int local6 = Stream.floatToRawIntBits(arg0);
		super.buffer[super.currentPosition++] = (byte) (local6 >> 24);
		super.buffer[super.currentPosition++] = (byte) (local6 >> 16);
		super.buffer[super.currentPosition++] = (byte) (local6 >> 8);
		super.buffer[super.currentPosition++] = (byte) local6;
	}

@OriginalMember(owner = "client!hk", name = "Db", descriptor = "[I")
	public static final int[] anIntArray189 = new int[] { 32, 39, 44, 47 };
@OriginalMember(owner = "client!hk", name = "Hb", descriptor = "I")
	public static int anInt3012 = 0;
@OriginalMember(owner = "client!hk", name = "Ib", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_129 = new Class22(100, 6);
@OriginalMember(owner = "client!hk", name = "t", descriptor = "(I)[Lclient!bg;")
	public static Class22[] method2553() {
		return new Class22[] { ReflectiveRipplingWaterEffect.aClass22_16, Class56.aClass22_80, CacheableNodeQueue.aClass22_195, Class244.aClass22_325, Class259.aClass22_328, ParticleNode.aClass22_185, TextureOpTile.aClass22_290, Class193.aClass22_261, FileOnDisk.aClass22_194, Model.aClass22_286, EffectiveVertex.aClass22_248, Class95_Sub3.aClass22_292, TextureOperation.CONTAINER_MAP, Class4_Sub6_Sub16.aClass22_167, Class10.aClass22_17, TextureOpMonochromeEdgeDetector.aClass22_317, Huffman.aClass22_159, TextureOpMonochromeEdgeDetector.aClass22_316, Class21.aClass22_33, Class4_Sub6_Sub22.aClass22_199, Class16_Sub4_Sub2.aClass22_106, ParticleList.aClass22_203, EffectiveVertex.aClass22_247, CacheableNodeQueue.aClass22_196, TextureOpKaleidoscope.aClass22_120, MusicPatchInfo.aClass22_310, SimpleMouseLog.aClass22_46, PlayerEntity.aClass22_191, Static107.aClass22_96, Class4_Sub45.aClass22_335, GlVolumeTexture.aClass22_53, GpuFaceNormal.aClass22_313, Class75.aClass22_102, Class4_Sub10.aClass22_98, StringNode.aClass22_241, Isaac.aClass22_72, Static214.aClass22_178, FloTypeList.aClass22_173, EnumTypeList.aClass22_219, StructTypeList.aClass22_95, Shadow.categoryIterator, BillowNoiseGenerator.aClass22_263, EnvironmentContext.aClass22_273, JavaBillboardFace.aClass22_175, Class136.aClass22_192, Static1.aClass22_1, Class111.aClass22_150, Class14.aClass22_23, NativeBillboardFace.aClass22_152, Js5GroupLoader.aClass22_269, QuickChatPhrase.OPNPC3, CursorTypeList.aClass22_236, Class27_Sub4.aClass22_232, Class225.aClass22_293, IDKTypeList.aClass22_146, Class4_Sub37.aClass22_251, CursorTypeList.aClass22_235, Class16_Sub2.aClass22_204, GpuFaceNormal.aClass22_312, Ground.aClass22_332, NamedFileReference.aClass22_301, Class4_Sub6_Sub24.aClass22_212, EntityNodeList.IF_BUTTON8, Js5MasterIndex.aClass22_282, MemoryPool.aClass22_214, Country.aClass22_336, ModeWhere.aClass22_287, Mesh.aClass22_164, Class196.aClass22_264, ObjStackEntity.aClass22_45, GzipDecompressor.aClass22_252, Class53.aClass22_73, Class16_Sub2_Sub3.aClass22_205, Class4_Sub43.aClass22_319, Js5MasterIndex.aClass22_281, MusicPatchInfo.aClass22_311, StructType.aClass22_278, Class9_Sub3.aClass22_71, ByteArrayWrapper.aClass22_51, Game.aClass22_160, OggOtherStream.aClass22_266, CacheableNode.aClass22_277, Class7.aClass22_11, Class6.aClass22_253, MECType.aClass22_218, Static331.aClass22_268, RequestManager.MAP_BUILD_COMPLETE, KeyRecordEntry.aClass22_32, GlShadowMap.ALIGN_CENTER, OpenGl2DTexture.aClass22_254, TextureOpFlip.aClass22_157, Class93.aClass22_125, Static167.aClass22_144, GlRectangleTexture.STELLAR_DAWN, Class4_Sub41.aClass22_307, Static105.aClass22_94, Class4_Sub41.aClass22_306, NativeWaterTexture.aClass22_200, Static277.aClass22_231, GlRenderList.aClass22_63, aClass22_129, MapSpriteTypeList.aClass22_331, ColorImageCache.aClass22_209, Static433.aClass22_327, CursorType.aClass22_101, MECTypeList.aClass22_249, GameCanvas.aClass22_284, Static433.aClass22_326, Static45.aClass22_49, NamedFileReference.aClass22_300 };
	}
@OriginalMember(owner = "client!hk", name = "a", descriptor = "(Lclient!ya;B)V")
	public static void method2554(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		if (Class177.aBoolean481) {
			MECTypeList.method4129(arg0);
		} else {
			GlGround.method4033(arg0);
		}
	}
@OriginalMember(owner = "client!hk", name = "a", descriptor = "(Lclient!lh;I)V")
	public static void method2555(@OriginalArg(0) PlayerEntity arg0) {
		@Pc(17) ReflectionCheck local17 = (ReflectionCheck) AbstractArbBufferGL.aClass102_38.method2700((long) arg0.anInt6037);
		if (local17 == null) {
			MonochromeImageCache.method2264(arg0.movementBaseX[0], null, arg0, arg0.movementBaseY[0], 0, arg0.aByte82, null);
		} else {
			local17.method638();
		}
	}
@OriginalMember(owner = "client!hk", name = "a", descriptor = "(IIII)V")
	public static void method2558(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		@Pc(12) TileLighting local12 = Static1.aClass155ArrayArray1[arg0][arg1];
		PlayerEntity.method3431(local12 == null ? Static47.aClass155_1 : local12, arg2);
	}
}
