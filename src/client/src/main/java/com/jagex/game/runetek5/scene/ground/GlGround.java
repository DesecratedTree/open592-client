package com.jagex.game.runetek5.scene.ground;

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
import com.jagex.game.runetek5.cache.node.GlGroundLayer;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class4_Sub31;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.i;
import com.jagex.game.runetek5.deob.k;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.graphics.gl.GlShadowMap;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.light.LightGL;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.config.npc.NPCType;
import com.jagex.game.runetek5.deob.Class174;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.deob.Static391;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.entity.NPCEntity;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.sound.jagtheora.OggOtherStream;
import com.jagex.game.runetek5.scene.ground.*;
import com.jagex.game.runetek5.client.hd.ground.HdTileMaterialMap;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class70_Sub2;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub24;
import com.jagex.game.runetek5.deob.Class111;
import com.jagex.game.runetek5.deob.Class4_Sub21_Sub2;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class27_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub1;
import com.jagex.game.runetek5.deob.Class191;
import com.jagex.game.runetek5.PcmPlayerThread;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.LegacyMouseManager;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpVoronoiNoise;
@OriginalClass("client!oq")
public final class GlGround extends Class65 {

	@OriginalMember(owner = "client!oq", name = "r", descriptor = "I")
	private int anInt5270;

	@OriginalMember(owner = "client!oq", name = "z", descriptor = "[[[I")
	private int[][][] anIntArrayArrayArray9;

	@OriginalMember(owner = "client!oq", name = "K", descriptor = "I")
	private int anInt5280;

	@OriginalMember(owner = "client!oq", name = "W", descriptor = "Lclient!pc;")
	private Class177 layerTable;

	@OriginalMember(owner = "client!oq", name = "X", descriptor = "Lclient!pc;")
	public Class177 aClass177_14;

	@OriginalMember(owner = "client!oq", name = "Y", descriptor = "Lclient!vd;")
	private ElementsBufferGL anInterface11_6;

	@OriginalMember(owner = "client!oq", name = "Z", descriptor = "I")
	private int anInt5287;

	@OriginalMember(owner = "client!oq", name = "ab", descriptor = "Lclient!pc;")
	public Class177 aClass177_15;

	@OriginalMember(owner = "client!oq", name = "fb", descriptor = "I")
	private int anInt5288;

	@OriginalMember(owner = "client!oq", name = "gb", descriptor = "Lclient!pc;")
	public Class177 aClass177_16;

	@OriginalMember(owner = "client!oq", name = "ib", descriptor = "[Lclient!dw;")
	private GlGroundLayer[] layers;

	/**
	 * 592HD: the mesh's per-tile material map, recorded as each tile's layers
	 * are created. The layer array is released when the mesh is finished, so the
	 * two materials a tile is built from have to be copied out here or the draw
	 * can no longer know them.
	 */
	private HdTileMaterialMap hdTileMaterials;

	/** The map's texture, built on the first draw that wants it. */
	private com.jagex.game.runetek5.deob.OpenGl2DTexture hdTileMaterialsTexture;

	@OriginalMember(owner = "client!oq", name = "F", descriptor = "Lclient!pk;")
	private final Class183 lights = new Class183();

	@OriginalMember(owner = "client!oq", name = "u", descriptor = "[[I")
	private final int[][] anIntArrayArray44;

	@OriginalMember(owner = "client!oq", name = "D", descriptor = "Lclient!fd;")
	public final GlToolkit glToolkit;

	@OriginalMember(owner = "client!oq", name = "v", descriptor = "I")
	private final int quarterTileScale;

	@OriginalMember(owner = "client!oq", name = "U", descriptor = "[[S")
	public final short[][] tileVertexIndices;

	@OriginalMember(owner = "client!oq", name = "H", descriptor = "[[B")
	private final byte[][] tileFlags;

	@OriginalMember(owner = "client!oq", name = "T", descriptor = "I")
	public final int featureFlags;

	@OriginalMember(owner = "client!oq", name = "y", descriptor = "I")
	private final int quarterTileUnits;

	@OriginalMember(owner = "client!oq", name = "P", descriptor = "[[[Lclient!dw;")
	private GlGroundLayer[][][] tileLayers;

	@OriginalMember(owner = "client!oq", name = "t", descriptor = "[[[I")
	private int[][][] anIntArrayArrayArray8;

	@OriginalMember(owner = "client!oq", name = "M", descriptor = "[[[I")
	private int[][][] anIntArrayArrayArray11;

	@OriginalMember(owner = "client!oq", name = "q", descriptor = "[[[I")
	public int[][][] anIntArrayArrayArray7;

	@OriginalMember(owner = "client!oq", name = "I", descriptor = "[[[I")
	private int[][][] anIntArrayArrayArray10;

	@OriginalMember(owner = "client!oq", name = "db", descriptor = "[[B")
	private byte[][] shadowLevels;

	@OriginalMember(owner = "client!oq", name = "bb", descriptor = "[[F")
	private float[][] aFloatArrayArray7;

	@OriginalMember(owner = "client!oq", name = "cb", descriptor = "[[F")
	private float[][] normalZ;

	@OriginalMember(owner = "client!oq", name = "hb", descriptor = "[[F")
	private float[][] aFloatArrayArray9;

	@OriginalMember(owner = "client!oq", name = "p", descriptor = "[[[I")
	public int[][][] anIntArrayArrayArray6;

	@OriginalMember(owner = "client!oq", name = "eb", descriptor = "Lclient!ib;")
	private CubeMapSpecularEffect aClass102_27;

	@OriginalMember(owner = "client!oq", name = "E", descriptor = "Lclient!uf;")
	private GlShadowMap shadowMap;

	@OriginalMember(owner = "client!oq", name = "<init>", descriptor = "(Lclient!fd;IIII[[I[[II)V")
	public GlGround(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int groundFlags, @OriginalArg(2) int featureFlags, @OriginalArg(3) int width, @OriginalArg(4) int length, @OriginalArg(5) int[][] tileHeights, @OriginalArg(6) int[][] normalHeights, @OriginalArg(7) int scaling) {
		super(width, length, scaling);
		this.anIntArrayArray44 = tileHeights;
		this.glToolkit = arg0;
		this.quarterTileScale = super.anInt5256 - 2;
		this.tileVertexIndices = new short[width * length][];
		this.tileFlags = new byte[width][length];
		this.featureFlags = featureFlags;
		this.quarterTileUnits = 0x1 << this.quarterTileScale;
		this.tileLayers = new GlGroundLayer[width][length][];
		this.anIntArrayArrayArray8 = new int[width][length][];
		this.anIntArrayArrayArray11 = new int[width][length][];
		this.anIntArrayArrayArray7 = new int[width][length][];
		this.anIntArrayArrayArray10 = new int[width][length][];
		this.shadowLevels = new byte[width + 1][length + 1];
		this.aFloatArrayArray7 = new float[super.anInt5262 + 1][super.anInt5258 + 1];
		this.normalZ = new float[super.anInt5262 + 1][super.anInt5258 + 1];
		this.aFloatArrayArray9 = new float[super.anInt5262 + 1][super.anInt5258 + 1];
		this.anIntArrayArrayArray6 = new int[width][length][];
		for (@Pc(117) int local117 = 1; local117 < super.anInt5258; local117++) {
			for (@Pc(121) int local121 = 1; super.anInt5262 > local121; local121++) {
				@Pc(139) int local139 = normalHeights[local121 + 1][local117] - normalHeights[local121 - 1][local117];
				@Pc(156) int local156 = normalHeights[local121][local117 + 1] - normalHeights[local121][local117 - 1];
				@Pc(177) float local177 = (float) (1.0D / Math.sqrt((double) (local139 * local139 + scaling * scaling * 4 + local156 * local156)));
				this.aFloatArrayArray9[local121][local117] = local177 * (float) local139;
				this.aFloatArrayArray7[local121][local117] = local177 * (float) (-scaling * 2);
				this.normalZ[local121][local117] = (float) local156 * local177;
			}
		}
		this.aClass102_27 = new CubeMapSpecularEffect(128);
		if ((this.featureFlags & 0x10) != 0) {
			this.shadowMap = new GlShadowMap(this.glToolkit, this);
		}
	}

	@OriginalMember(owner = "client!oq", name = "b", descriptor = "(II)V")
	@Override
	public void method4028(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
	}

	@OriginalMember(owner = "client!oq", name = "i", descriptor = "(III)V")
	@Override
	public void i(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		if (arg2 > (this.shadowLevels[arg0][arg1] & 0xFF)) {
			this.shadowLevels[arg0][arg1] = (byte) arg2;
		}
	}

	@OriginalMember(owner = "client!oq", name = "p", descriptor = "(Lclient!ba;IIIIZ)V")
	@Override
	public void p(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5) {
		if (this.shadowMap != null && arg0 != null) {
			@Pc(24) int local24 = arg1 - (arg2 * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
			@Pc(39) int local39 = arg3 - (this.glToolkit.sunSlopeX * arg2 >> 8) >> this.glToolkit.shadowScaleShift;
			this.shadowMap.method5194(local39, local24, arg0);
		}
	}

	@OriginalMember(owner = "client!oq", name = "k", descriptor = "(IILclient!ba;)Lclient!ba;")
	@Override
	public Shadow k(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Shadow arg2) {
		if ((this.tileFlags[arg0][arg1] & 0x1) == 0) {
			return null;
		}
		@Pc(24) int local24 = super.anInt5261 >> this.glToolkit.shadowScaleShift;
		@Pc(27) GlShadow local27 = (GlShadow) arg2;
		@Pc(43) GlShadow local43;
		if (local27 != null && local27.fitsDimension(local24, local24)) {
			local43 = local27;
			local27.reset();
		} else {
			local43 = new GlShadow(this.glToolkit, local24, local24);
		}
		local43.setDimension(0, 0, local24, local24);
		this.method4032(local43, arg0, arg1);
		return local43;
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(IIIIIII[[Z)V")
	@Override
	public void method4025(@OriginalArg(3) int arg0, @OriginalArg(4) int arg1, @OriginalArg(5) int arg2, @OriginalArg(6) int arg3, @OriginalArg(7) boolean[][] arg4) {
		if (this.anInt5288 <= 0) {
			return;
		}
		this.glToolkit.method1898();
		this.glToolkit.method1918(false);
		this.glToolkit.method1906(false);
		this.glToolkit.method1951(false);
		this.glToolkit.method1911(false);
		this.glToolkit.method1958(0);
		this.glToolkit.method1957(-2);
		this.glToolkit.setActiveTexture(null);
		SpriteDrawQueue.aFloatArray28[7] = 0.0F;
		SpriteDrawQueue.aFloatArray28[5] = (float) 1024 / ((float) this.glToolkit.anInt2138 * 128.0F * (float) super.anInt5261);
		SpriteDrawQueue.aFloatArray28[6] = 0.0F;
		SpriteDrawQueue.aFloatArray28[9] = 0.0F;
		SpriteDrawQueue.aFloatArray28[1] = 0.0F;
		SpriteDrawQueue.aFloatArray28[3] = 0.0F;
		SpriteDrawQueue.aFloatArray28[10] = 0.0F;
		SpriteDrawQueue.aFloatArray28[15] = 1.0F;
		SpriteDrawQueue.aFloatArray28[14] = 0.0F;
		SpriteDrawQueue.aFloatArray28[2] = 0.0F;
		SpriteDrawQueue.aFloatArray28[0] = (float) 1024 / ((float) this.glToolkit.anInt2240 * 128.0F * (float) super.anInt5261);
		SpriteDrawQueue.aFloatArray28[12] = -1.0F - ((float) 0 + (float) (arg0 * 1024) / 128.0F) / (float) this.glToolkit.anInt2240;
		SpriteDrawQueue.aFloatArray28[4] = 0.0F;
		SpriteDrawQueue.aFloatArray28[8] = 0.0F;
		SpriteDrawQueue.aFloatArray28[11] = 0.0F;
		SpriteDrawQueue.aFloatArray28[13] = 1.0F - ((float) 0 + (float) (arg3 * 1024) / 128.0F) / (float) this.glToolkit.anInt2138;
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadMatrixf(SpriteDrawQueue.aFloatArray28, 0);
		SpriteDrawQueue.aFloatArray28[8] = 0.0F;
		SpriteDrawQueue.aFloatArray28[3] = 0.0F;
		SpriteDrawQueue.aFloatArray28[12] = 0.0F;
		SpriteDrawQueue.aFloatArray28[5] = 0.0F;
		SpriteDrawQueue.aFloatArray28[9] = 1.0F;
		SpriteDrawQueue.aFloatArray28[0] = 1.0F;
		SpriteDrawQueue.aFloatArray28[10] = 0.0F;
		SpriteDrawQueue.aFloatArray28[2] = 0.0F;
		SpriteDrawQueue.aFloatArray28[1] = 0.0F;
		SpriteDrawQueue.aFloatArray28[6] = 1.0F;
		SpriteDrawQueue.aFloatArray28[4] = 0.0F;
		SpriteDrawQueue.aFloatArray28[13] = 0.0F;
		SpriteDrawQueue.aFloatArray28[15] = 1.0F;
		SpriteDrawQueue.aFloatArray28[11] = 0.0F;
		SpriteDrawQueue.aFloatArray28[14] = 0.0F;
		SpriteDrawQueue.aFloatArray28[7] = 0.0F;
		OpenGL.glMatrixMode(5888);
		OpenGL.glLoadMatrixf(SpriteDrawQueue.aFloatArray28, 0);
		@Pc(268) boolean litPass = (this.featureFlags & 0x7) != 0;
		if (litPass) {
			this.glToolkit.method1906(true);
			this.glToolkit.method1914();
		} else {
			this.glToolkit.method1906(false);
		}
		this.glToolkit.method1934(this.layerTable, this.aClass177_16, this.aClass177_14, this.aClass177_15);
		if (PcmPlayerThread.aClass4_Sub12_7.buffer.length < this.anInt5270 * 2) {
			PcmPlayerThread.aClass4_Sub12_7 = new Packet(this.anInt5270 * 2);
		} else {
			PcmPlayerThread.aClass4_Sub12_7.currentPosition = 0;
		}
		@Pc(305) int local305 = 0;
		@Pc(311) int local311;
		@Pc(321) int local321;
		@Pc(323) int local323;
		@Pc(342) short[] local342;
		@Pc(346) int local346;
		if (this.glToolkit.bigEndianess) {
			for (local311 = arg1; local311 < arg3; local311++) {
				local321 = arg0 + local311 * super.anInt5262;
				for (local323 = arg0; local323 < arg2; local323++) {
					if (arg4[local323 - arg0][local311 - arg1]) {
						local342 = this.tileVertexIndices[local321];
						if (local342 != null) {
							for (local346 = 0; local346 < local342.length; local346++) {
								PcmPlayerThread.aClass4_Sub12_7.method2524(local342[local346] & 0xFFFF);
								local305++;
							}
						}
					}
					local321++;
				}
			}
		} else {
			for (local311 = arg1; local311 < arg3; local311++) {
				local321 = super.anInt5262 * local311 + arg0;
				for (local323 = arg0; local323 < arg2; local323++) {
					if (arg4[local323 - arg0][local311 - arg1]) {
						local342 = this.tileVertexIndices[local321];
						if (local342 != null) {
							for (local346 = 0; local346 < local342.length; local346++) {
								local305++;
								PcmPlayerThread.aClass4_Sub12_7.method2541(local342[local346] & 0xFFFF);
							}
						}
					}
					local321++;
				}
			}
		}
		if (local305 > 0) {
			@Pc(470) Class64_Sub1 local470 = new Class64_Sub1(this.glToolkit, 5123, PcmPlayerThread.aClass4_Sub12_7.buffer, PcmPlayerThread.aClass4_Sub12_7.currentPosition);
			// 592HD: this is the world map's top-down bake - the caller holds
			// Toolkit.hdMapBakeBegin/End around it - so it must draw exactly what
			// the stock path drew: the per-pixel program is expressed in the scene
			// camera's space, which this pass does not have. The live terrain draw
			// is GlGroundLayer.method1462, and that is where the seam binds.
			this.glToolkit.method1967(0, local305, local470);
		}
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(Lclient!vp;[I)V")
	@Override
	public void method4024(@OriginalArg(0) PointLight arg0, @OriginalArg(1) int[] arg1) {
		this.lights.method4137(new LightGL(this.glToolkit, this, arg0, arg1));
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(Lclient!ba;IIIIZ)Z")
	@Override
	public boolean method4022(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		if (this.shadowMap == null || arg0 == null) {
			return false;
		} else {
			@Pc(24) int local24 = arg1 - (this.glToolkit.sunSlopeZ * arg2 >> 8) >> this.glToolkit.shadowScaleShift;
			@Pc(39) int local39 = arg3 - (arg2 * this.glToolkit.sunSlopeX >> 8) >> this.glToolkit.shadowScaleShift;
			return this.shadowMap.method5190(arg0, local39, local24);
		}
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(II[I[I[I[I[I[I[I[I[I[I[IIIIZ)V")
	@Override
	public void method4023(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int[] arg9, @OriginalArg(10) int[] arg10, @OriginalArg(11) int[] arg11, @OriginalArg(12) int[] arg12, @OriginalArg(13) int arg13, @OriginalArg(14) int arg14, @OriginalArg(15) int arg15) {
		@Pc(8) int local8 = arg9.length;
		@Pc(13) int[] local13 = new int[local8 * 3];
		@Pc(18) int[] local18 = new int[local8 * 3];
		@Pc(23) int[] local23 = new int[local8 * 3];
		@Pc(28) int[] local28 = new int[local8 * 3];
		@Pc(33) int[] local33 = new int[local8 * 3];
		@Pc(38) int[] local38 = new int[local8 * 3];
		@Pc(47) int[] local47 = arg3 == null ? null : new int[local8 * 3];
		@Pc(56) int[] local56 = arg5 == null ? null : new int[local8 * 3];
		@Pc(58) int local58 = 0;
		for (@Pc(60) int local60 = 0; local60 < local8; local60++) {
			@Pc(66) int local66 = arg6[local60];
			@Pc(70) int local70 = arg7[local60];
			@Pc(74) int local74 = arg8[local60];
			local13[local58] = arg2[local66];
			local18[local58] = arg4[local66];
			local23[local58] = arg9[local60];
			local33[local58] = arg11[local60];
			local38[local58] = arg12[local60];
			if (arg10 != null) {
				local28[local58] = arg10[local60];
			}
			if (arg3 != null) {
				local47[local58] = arg3[local66];
			}
			if (arg5 != null) {
				local56[local58] = arg5[local66];
			}
			local58++;
			local13[local58] = arg2[local70];
			local18[local58] = arg4[local70];
			local23[local58] = arg9[local60];
			local33[local58] = arg11[local60];
			local38[local58] = arg12[local60];
			if (arg10 != null) {
				local28[local58] = arg10[local60];
			}
			if (arg3 != null) {
				local47[local58] = arg3[local70];
			}
			if (arg5 != null) {
				local56[local58] = arg5[local70];
			}
			local58++;
			local13[local58] = arg2[local74];
			local18[local58] = arg4[local74];
			local23[local58] = arg9[local60];
			local33[local58] = arg11[local60];
			local38[local58] = arg12[local60];
			if (arg10 != null) {
				local28[local58] = arg10[local60];
			}
			if (arg3 != null) {
				local47[local58] = arg3[local74];
			}
			if (arg5 != null) {
				local56[local58] = arg5[local74];
			}
			local58++;
		}
		this.B(arg0, arg1, local13, local47, local18, local56, local23, local28, local33, local38, arg13, arg14, arg15, false);
	}

	@OriginalMember(owner = "client!oq", name = "b", descriptor = "(Lclient!ba;IIIIZ)V")
	@Override
	public void b(@OriginalArg(0) Shadow arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean arg5) {
		if (this.shadowMap != null && arg0 != null) {
			@Pc(24) int local24 = arg1 - (arg2 * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
			@Pc(39) int local39 = arg3 - (this.glToolkit.sunSlopeX * arg2 >> 8) >> this.glToolkit.shadowScaleShift;
			this.shadowMap.method5195(local39, local24, arg0);
		}
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(III[[ZZ)V")
	@Override
	public void method4029(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean[][] arg3, @OriginalArg(4) boolean arg4) {
		this.method4034(arg0, arg4, arg3, arg1, arg2);
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(Lclient!gm;IBI)V")
	private void method4032(@OriginalArg(0) GlShadow arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(12) int[] local12 = this.anIntArrayArrayArray6[arg1][arg2];
		@Pc(19) int[] local19 = this.anIntArrayArrayArray7[arg1][arg2];
		@Pc(22) int local22 = local12.length;
		if (local22 > MusicPatchNode.anIntArray293.length) {
			MusicPatchNode.anIntArray293 = new int[local22];
			Class191.anIntArray387 = new int[local22];
		}
		for (@Pc(34) int local34 = 0; local34 < local22; local34++) {
			MusicPatchNode.anIntArray293[local34] = (local12[local34] & 0xFF) >> this.glToolkit.shadowScaleShift;
			Class191.anIntArray387[local34] = (local19[local34] & 0xFF) >> this.glToolkit.shadowScaleShift;
		}
		@Pc(74) int local74 = 0;
		while (local22 > local74) {
			@Pc(80) int local80 = MusicPatchNode.anIntArray293[local74];
			@Pc(85) int local85 = Class191.anIntArray387[local74++];
			@Pc(89) int local89 = MusicPatchNode.anIntArray293[local74];
			@Pc(94) int local94 = Class191.anIntArray387[local74++];
			@Pc(98) int local98 = MusicPatchNode.anIntArray293[local74];
			@Pc(103) int local103 = Class191.anIntArray387[local74++];
			if (-((local98 - local89) * (-local85 + local94)) + (local94 - local103) * (local80 - local89) > 0) {
				arg0.fillTriangle(local80, local94, local85, local89, local103, local98);
			}
		}
	}

	/** 592HD seam: this mesh's per-tile material texture, or null when no mesh has been built. */
	public com.jagex.game.runetek5.deob.OpenGl2DTexture hdTileMaterialsTexture() {
		if (this.hdTileMaterialsTexture == null && this.hdTileMaterials != null) {
			this.hdTileMaterialsTexture = this.glToolkit.hdGroundTileMaterials(this.hdTileMaterials);
		}
		return this.hdTileMaterialsTexture;
	}

	/**
	 * 592HD test hook: attaches a per-tile material map to a mesh whose tiles were
	 * built outside {@code constructTileLayers}, so a real-GL proof can drive the
	 * atlas branch. Production fills the map as each tile's layers are made;
	 * nothing in the game calls this. Public because the frame-contract harness
	 * lives in another package and builds its terrain the same synthetic way.
	 */
	public void hdAttachTileMaterialsForTest(HdTileMaterialMap materials) {
		this.hdTileMaterials = materials;
		this.hdTileMaterialsTexture = null;
	}

	@OriginalMember(owner = "client!oq", name = "l", descriptor = "(II)I")
	@Override
	public int l(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		return this.anIntArrayArray44[arg0][arg1];
	}

	@OriginalMember(owner = "client!oq", name = "B", descriptor = "(II[I[I[I[I[I[I[I[IIIIZ)V")
	@Override
	public void B(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[] arg2, @OriginalArg(3) int[] arg3, @OriginalArg(4) int[] arg4, @OriginalArg(5) int[] arg5, @OriginalArg(6) int[] arg6, @OriginalArg(7) int[] arg7, @OriginalArg(8) int[] arg8, @OriginalArg(9) int[] arg9, @OriginalArg(10) int arg10, @OriginalArg(11) int arg11, @OriginalArg(12) int arg12, @OriginalArg(13) boolean arg13) {
		if (arg5 != null && this.anIntArrayArrayArray9 == null) {
			this.anIntArrayArrayArray9 = new int[super.anInt5262][super.anInt5258][];
		}
		if (arg3 != null && this.anIntArrayArrayArray8 == null) {
			this.anIntArrayArrayArray8 = new int[super.anInt5262][super.anInt5258][];
		}
		this.anIntArrayArrayArray6[arg0][arg1] = arg2;
		this.anIntArrayArrayArray7[arg0][arg1] = arg4;
		this.anIntArrayArrayArray11[arg0][arg1] = arg6;
		this.anIntArrayArrayArray10[arg0][arg1] = arg7;
		if (this.anIntArrayArrayArray9 != null) {
			this.anIntArrayArrayArray9[arg0][arg1] = arg5;
		}
		if (this.anIntArrayArrayArray8 != null) {
			this.anIntArrayArrayArray8[arg0][arg1] = arg3;
		}
		@Pc(88) GlGroundLayer[] local88 = this.tileLayers[arg0][arg1] = new GlGroundLayer[arg6.length];
		// 592HD: the tile's two materials, copied out before the layer array is
		// released. The first layer is the tile's dominant material; the mate is
		// the first later layer that uses a different one.
		if (this.hdTileMaterials == null) {
			this.hdTileMaterials = new HdTileMaterialMap();
		}
		int hdPrimary = arg6.length > 0 ? arg8[0] : -1;
		int hdMate = -1;
		for (int hdLayer = 1; hdLayer < arg6.length; hdLayer++) {
			if (arg8[hdLayer] != hdPrimary) {
				hdMate = arg8[hdLayer];
				break;
			}
		}
		this.hdTileMaterials.set(arg0, arg1, hdPrimary, hdMate);
		for (@Pc(90) int local90 = 0; local90 < arg6.length; local90++) {
			@Pc(119) long local119 = (long) arg10 << 28 | (long) arg12 << 48 | (long) arg11 << 42 | (long) (arg9[local90] << 14) | (long) arg8[local90];
			@Pc(125) Country local125;
			for (local125 = this.aClass102_27.method2700(local119); local125 != null; local125 = this.aClass102_27.method2697()) {
				@Pc(130) GlGroundLayer local130 = (GlGroundLayer) local125;
				if (arg8[local90] == local130.anInt1647 && local130.textureScale == (float) arg9[local90] && local130.anInt1641 == arg10 && local130.anInt1640 == arg11 && local130.waterDepth == arg12) {
					break;
				}
			}
			if (local125 == null) {
				local88[local90] = new GlGroundLayer(this, arg8[local90], arg9[local90], arg10, arg11, arg12);
				this.aClass102_27.method2703(local119, local88[local90]);
			} else {
				local88[local90] = (GlGroundLayer) local125;
			}
		}
		if (arg13) {
			this.tileFlags[arg0][arg1] = (byte) (this.tileFlags[arg0][arg1] | 0x1);
		}
		if (this.anInt5287 < arg6.length) {
			this.anInt5287 = arg6.length;
		}
		this.anInt5288 += arg6.length;
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(II)I")
	@Override
	public int a(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(8) int local8 = arg0 >> super.anInt5256;
		@Pc(13) int local13 = arg1 >> super.anInt5256;
		if (local8 < 0 || local13 < 0 || super.anInt5262 - 1 < local8 || super.anInt5258 - 1 < local13) {
			return 0;
		}
		@Pc(44) int local44 = super.anInt5261 - 1 & arg0;
		@Pc(51) int local51 = super.anInt5261 - 1 & arg1;
		@Pc(78) int local78 = this.anIntArrayArray44[local8 + 1][local13] * local44 + this.anIntArrayArray44[local8][local13] * (super.anInt5261 - local44) >> super.anInt5256;
		@Pc(110) int local110 = (super.anInt5261 - local44) * this.anIntArrayArray44[local8][local13 + 1] + local44 * this.anIntArrayArray44[local8 + 1][local13 + 1] >> super.anInt5256;
		return local78 * (super.anInt5261 - local51) + local110 * local51 >> super.anInt5256;
	}

	@OriginalMember(owner = "client!oq", name = "ca", descriptor = "()V")
	@Override
	public void ca() {
		if (this.anInt5288 <= 0) {
			this.shadowMap = null;
		} else {
			@Pc(23) byte[][] local23 = new byte[super.anInt5262 + 1][super.anInt5258 + 1];
			@Pc(29) int local29;
			for (@Pc(25) int local25 = 1; local25 < super.anInt5262; local25++) {
				for (local29 = 1; super.anInt5258 > local29; local29++) {
					local23[local25][local29] = (byte) ((this.shadowLevels[local25 + 1][local29] >> 3) + (this.shadowLevels[local25 - 1][local29] >> 2) + (this.shadowLevels[local25][local29 - 1] >> 2) + (this.shadowLevels[local25][local29 + 1] >> 3) + (this.shadowLevels[local25][local29] >> 1));
				}
			}
			this.layers = new GlGroundLayer[this.aClass102_27.method2698()];
			this.aClass102_27.method2708(this.layers);
			for (local29 = 0; local29 < this.layers.length; local29++) {
				this.layers[local29].initColours(this.anInt5288);
			}
			@Pc(141) int local141 = 24;
			if (this.anIntArrayArrayArray9 != null) {
				local141 += 4;
			}
			if ((this.featureFlags & 0x7) != 0) {
				local141 += 12;
			}
			@Pc(163) NativeBuffer local163 = this.glToolkit.aNativeHeap2.a(local141 * this.anInt5288);
			@Pc(168) Stream local168 = new Stream(local163);
			@Pc(172) GlGroundLayer[] local172 = new GlGroundLayer[this.anInt5288];
			@Pc(179) int local179 = CubeMapSpecularEffect.method2696(this.anInt5288 / 4);
			if (local179 < 1) {
				local179 = 1;
			}
			@Pc(189) CubeMapSpecularEffect local189 = new CubeMapSpecularEffect(local179);
			@Pc(193) GlGroundLayer[] local193 = new GlGroundLayer[this.anInt5287];
			@Pc(199) int local199;
			for (@Pc(195) int local195 = 0; super.anInt5262 > local195; local195++) {
				for (local199 = 0; local199 < super.anInt5258; local199++) {
					if (this.anIntArrayArrayArray11[local195][local199] != null) {
						@Pc(215) GlGroundLayer[] local215 = this.tileLayers[local195][local199];
						@Pc(222) int[] local222 = this.anIntArrayArrayArray6[local195][local199];
						@Pc(229) int[] local229 = this.anIntArrayArrayArray7[local195][local199];
						@Pc(236) int[] local236 = this.anIntArrayArrayArray10[local195][local199];
						@Pc(243) int[] local243 = this.anIntArrayArrayArray11[local195][local199];
						@Pc(255) int[] local255 = this.anIntArrayArrayArray8 == null ? null : this.anIntArrayArrayArray8[local195][local199];
						@Pc(267) int[] local267 = this.anIntArrayArrayArray9 == null ? null : this.anIntArrayArrayArray9[local195][local199];
						if (local236 == null) {
							local236 = local243;
						}
						@Pc(278) float local278 = this.aFloatArrayArray9[local195][local199];
						@Pc(285) float local285 = this.aFloatArrayArray7[local195][local199];
						@Pc(292) float local292 = this.normalZ[local195][local199];
						@Pc(301) float local301 = this.aFloatArrayArray9[local195][local199 + 1];
						@Pc(310) float local310 = this.aFloatArrayArray7[local195][local199 + 1];
						@Pc(319) float local319 = this.normalZ[local195][local199 + 1];
						@Pc(330) float local330 = this.aFloatArrayArray9[local195 + 1][local199 + 1];
						@Pc(341) float local341 = this.aFloatArrayArray7[local195 + 1][local199 + 1];
						@Pc(352) float local352 = this.normalZ[local195 + 1][local199 + 1];
						@Pc(361) float local361 = this.aFloatArrayArray9[local195 + 1][local199];
						@Pc(370) float local370 = this.aFloatArrayArray7[local195 + 1][local199];
						@Pc(379) float local379 = this.normalZ[local195 + 1][local199];
						@Pc(387) int local387 = local23[local195][local199] & 0xFF;
						@Pc(397) int local397 = local23[local195][local199 + 1] & 0xFF;
						@Pc(409) int local409 = local23[local195 + 1][local199 + 1] & 0xFF;
						@Pc(419) int local419 = local23[local195 + 1][local199] & 0xFF;
						@Pc(421) int local421 = 0;
						@Pc(431) int local431;
						label335: for (@Pc(423) int local423 = 0; local423 < local243.length; local423++) {
							@Pc(429) GlGroundLayer local429 = local215[local423];
							for (local431 = 0; local431 < local421; local431++) {
								if (local193[local431] == local429) {
									continue label335;
								}
							}
							local193[local421++] = local429;
						}
						@Pc(473) short[] local473 = this.tileVertexIndices[local199 * super.anInt5262 + local195] = new short[local243.length];
						for (local431 = 0; local431 < local243.length; local431++) {
							@Pc(487) int local487 = (local195 << super.anInt5256) + local222[local431];
							@Pc(496) int local496 = (local199 << super.anInt5256) + local229[local431];
							@Pc(501) int local501 = local487 >> this.quarterTileScale;
							@Pc(506) int local506 = local496 >> this.quarterTileScale;
							@Pc(510) int local510 = local243[local431];
							@Pc(514) int local514 = local236[local431];
							@Pc(522) int local522 = local255 == null ? 0 : local255[local431];
							@Pc(540) long local540 = (long) local506 | (long) local510 << 32 | (long) local514 << 48 | (long) (local501 << 16);
							@Pc(544) int local544 = local222[local431];
							@Pc(548) int local548 = local229[local431];
							@Pc(550) byte local550 = 74;
							@Pc(552) int local552 = 0;
							@Pc(586) float local586;
							@Pc(588) float local588;
							@Pc(590) float local590;
							@Pc(636) float local636;
							@Pc(594) int local594;
							if (local544 == 0 && local548 == 0) {
								local588 = local285;
								local590 = local292;
								local594 = local550 - local387;
								local586 = local278;
							} else if (local544 == 0 && super.anInt5261 == local548) {
								local594 = local550 - local397;
								local586 = local301;
								local588 = local310;
								local590 = local319;
							} else if (local544 == super.anInt5261 && local548 == super.anInt5261) {
								local586 = local330;
								local588 = local341;
								local590 = local352;
								local594 = local550 - local409;
							} else if (super.anInt5261 == local544 && local548 == 0) {
								local590 = local379;
								local588 = local370;
								local586 = local361;
								local594 = local550 - local419;
							} else {
								@Pc(612) float local612 = (float) local544 / (float) super.anInt5261;
								@Pc(619) float local619 = (float) local548 / (float) super.anInt5261;
								@Pc(627) float local627 = local278 + local612 * (local361 - local278);
								local636 = local285 + local612 * (local370 - local285);
								@Pc(645) float local645 = local292 + (local379 - local292) * local612;
								@Pc(653) float local653 = (local330 - local301) * local612 + local301;
								@Pc(661) float local661 = local310 + local612 * (local341 - local310);
								local588 = local636 + local619 * (local661 - local636);
								@Pc(679) float local679 = (local352 - local319) * local612 + local319;
								local586 = (local653 - local627) * local619 + local627;
								local590 = local645 + local619 * (local679 - local645);
								@Pc(709) int local709 = local387 + (local544 * (local419 - local387) >> super.anInt5256);
								@Pc(721) int local721 = (local544 * (local409 - local397) >> super.anInt5256) + local397;
								local594 = local550 - local709 - ((local721 - local709) * local548 >> super.anInt5256);
							}
							@Pc(774) float local774 = 1.0F;
							if (local510 != -1) {
								@Pc(785) int local785 = (local510 & 0x7F) * local594 >> 7;
								if (local785 < 2) {
									local785 = 2;
								} else if (local785 > 126) {
									local785 = 126;
								}
								local552 = Class9_Sub3.anIntArray115[local785 | local510 & 0xFF80];
								if ((this.featureFlags & 0x7) == 0) {
									local774 = this.glToolkit.sunDirection[1] * local588 + local586 * this.glToolkit.sunDirection[0] + local590 * this.glToolkit.sunDirection[2];
									local774 = local774 * (local774 > 0.0F ? this.glToolkit.aFloat73 : this.glToolkit.aFloat65) + this.glToolkit.sunBrightness;
								}
							}
							@Pc(861) Country local861 = null;
							if ((this.quarterTileUnits - 1 & local487) == 0 && (local496 & this.quarterTileUnits - 1) == 0) {
								local861 = local189.method2700(local540);
							}
							@Pc(1229) int local1229;
							@Pc(894) int local894;
							if (local861 == null) {
								if (local510 == local514) {
									local894 = local552;
								} else {
									@Pc(904) int local904 = local594 * (local514 & 0x7F) >> 7;
									if (local904 < 2) {
										local904 = 2;
									} else if (local904 > 126) {
										local904 = 126;
									}
									local894 = Class9_Sub3.anIntArray115[local514 & 0xFF80 | local904];
									if ((this.featureFlags & 0x7) == 0) {
										local636 = this.glToolkit.sunDirection[2] * local590 + this.glToolkit.sunDirection[0] * local586 + this.glToolkit.sunDirection[1] * local588;
										local636 = this.glToolkit.sunBrightness + (local774 > 0.0F ? this.glToolkit.aFloat73 : this.glToolkit.aFloat65) * local774;
										@Pc(982) int local982 = local894 >> 16 & 0xFF;
										@Pc(988) int local988 = local894 >> 8 & 0xFF;
										@Pc(992) int local992 = local894 & 0xFF;
										local982 = (int) ((float) local982 * local636);
										if (local982 < 0) {
											local982 = 0;
										} else if (local982 > 255) {
											local982 = 255;
										}
										local988 = (int) ((float) local988 * local636);
										local992 = (int) ((float) local992 * local636);
										if (local988 < 0) {
											local988 = 0;
										} else if (local988 > 255) {
											local988 = 255;
										}
										if (local992 < 0) {
											local992 = 0;
										} else if (local992 > 255) {
											local992 = 255;
										}
										local894 = local992 | local988 << 8 | local982 << 16;
									}
								}
								if (this.glToolkit.bigEndianess) {
									local168.a((float) local487);
									local168.a((float) (this.a(local487, local496) + local522));
									local168.a((float) local496);
									local168.b((byte) (local894 >> 16));
									local168.b((byte) (local894 >> 8));
									local168.b((byte) local894);
									local168.b(-1);
									local168.a((float) local487);
									local168.a((float) local496);
									if (this.anIntArrayArrayArray9 != null) {
										local168.a((float) (local267 == null ? 0 : local267[local431] - 1));
									}
									if ((this.featureFlags & 0x7) != 0) {
										local168.a(local586);
										local168.a(local588);
										local168.a(local590);
									}
								} else {
									local168.b((float) local487);
									local168.b((float) (local522 + this.a(local487, local496)));
									local168.b((float) local496);
									local168.b((byte) (local894 >> 16));
									local168.b((byte) (local894 >> 8));
									local168.b((byte) local894);
									local168.b(-1);
									local168.b((float) local487);
									local168.b((float) local496);
									if (this.anIntArrayArrayArray9 != null) {
										local168.b((float) (local267 == null ? 0 : local267[local431] - 1));
									}
									if ((this.featureFlags & 0x7) != 0) {
										local168.b(local586);
										local168.b(local588);
										local168.b(local590);
									}
								}
								local1229 = this.anInt5280++;
								local473[local431] = (short) local1229;
								if (local510 != -1) {
									local172[local1229] = local215[local431];
								}
								local189.method2703(local540, new Class4_Sub31(local473[local431]));
							} else {
								local473[local431] = ((Class4_Sub31) local861).aShort62;
								local1229 = local473[local431] & 0xFFFF;
								if (local510 != -1 && local172[local1229].aLong224 > local215[local431].aLong224) {
									local172[local1229] = local215[local431];
								}
							}
							for (local894 = 0; local894 < local421; local894++) {
								local193[local894].setVertexColour(local594, local1229, local552, local774);
							}
							this.anInt5270++;
						}
					}
				}
			}
			for (local199 = 0; local199 < this.anInt5280; local199++) {
				@Pc(1337) GlGroundLayer local1337 = local172[local199];
				if (local1337 != null) {
					local1337.setVertexOpaque(local199);
				}
			}
			@Pc(1370) int local1370;
			for (@Pc(1350) int local1350 = 0; super.anInt5262 > local1350; local1350++) {
				for (@Pc(1354) int local1354 = 0; local1354 < super.anInt5258; local1354++) {
					@Pc(1366) short[] local1366 = this.tileVertexIndices[local1354 * super.anInt5262 + local1350];
					if (local1366 != null) {
						local1370 = 0;
						@Pc(1372) int local1372 = 0;
						while (local1372 < local1366.length) {
							@Pc(1381) int local1381 = local1366[local1372++] & 0xFFFF;
							@Pc(1388) int local1388 = local1366[local1372++] & 0xFFFF;
							@Pc(1395) int local1395 = local1366[local1372++] & 0xFFFF;
							@Pc(1399) GlGroundLayer local1399 = local172[local1381];
							@Pc(1403) GlGroundLayer local1403 = local172[local1388];
							@Pc(1407) GlGroundLayer local1407 = local172[local1395];
							@Pc(1409) GlGroundLayer local1409 = null;
							if (local1399 != null) {
								local1399.addTriangle(local1354, local1370, local1350);
								local1409 = local1399;
							}
							if (local1403 != null) {
								local1403.addTriangle(local1354, local1370, local1350);
								if (local1409 == null || local1409.aLong224 > local1403.aLong224) {
									local1409 = local1403;
								}
							}
							if (local1407 != null) {
								local1407.addTriangle(local1354, local1370, local1350);
								if (local1409 == null || local1409.aLong224 > local1407.aLong224) {
									local1409 = local1407;
								}
							}
							if (local1409 != null) {
								if (local1399 != null) {
									local1409.setVertexOpaque(local1381);
								}
								if (local1403 != null) {
									local1409.setVertexOpaque(local1388);
								}
								if (local1407 != null) {
									local1409.setVertexOpaque(local1395);
								}
								local1409.addTriangle(local1354, local1370, local1350);
							}
							local1370++;
						}
					}
				}
			}
			local168.b();
			this.anInterface11_6 = this.glToolkit.method1902(local163, local168.a(), local141);
			this.aClass177_15 = new Class177(this.anInterface11_6, 5126, 3, 0);
			this.layerTable = new Class177(this.anInterface11_6, 5121, 4, 12);
			@Pc(1542) byte local1542;
			if (this.anIntArrayArrayArray9 == null) {
				local1542 = 24;
				this.aClass177_14 = new Class177(this.anInterface11_6, 5126, 2, 16);
			} else {
				local1542 = 28;
				this.aClass177_14 = new Class177(this.anInterface11_6, 5126, 3, 16);
			}
			if ((this.featureFlags & 0x7) != 0) {
				this.aClass177_16 = new Class177(this.anInterface11_6, 5126, 3, local1542);
			}
			@Pc(1586) long[] local1586 = new long[this.layers.length];
			for (local1370 = 0; local1370 < this.layers.length; local1370++) {
				@Pc(1595) GlGroundLayer local1595 = this.layers[local1370];
				local1586[local1370] = local1595.aLong224;
				local1595.finishColours(this.anInt5280);
			}
			Class191.method4335(this.layers, local1586);
			if (this.shadowMap != null) {
				this.shadowMap.initializeShadowTiles();
			}
		}
		this.anIntArrayArrayArray8 = null;
		this.anIntArrayArrayArray9 = null;
		this.anIntArrayArrayArray10 = null;
		this.aClass102_27 = null;
		this.tileLayers = null;
		// The map stays: it describes the mesh that was just built, and its
		// texture is uploaded on the first draw. It is rebuilt with the mesh.
		this.hdTileMaterialsTexture = null;
		this.aFloatArrayArray9 = this.aFloatArrayArray7 = this.normalZ = null;
		this.anIntArrayArrayArray6 = this.anIntArrayArrayArray7 = null;
		this.shadowLevels = null;
		this.anIntArrayArrayArray11 = null;
	}

	@OriginalMember(owner = "client!oq", name = "a", descriptor = "(IBZ[[ZIII)V")
	private void method4034(@OriginalArg(0) int arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) boolean[][] arg2, @OriginalArg(4) int arg3, @OriginalArg(6) int arg4) {
		if (this.layers == null) {
			return;
		}
		@Pc(8) float local8 = this.glToolkit.aFloat67;
		@Pc(12) float local12 = this.glToolkit.aFloat63;
		@Pc(19) int local19 = arg4 + arg4 + 1;
		@Pc(23) int local23 = local19 * local19;
		if (local23 > Class16_Sub5_Sub1.anIntArray239.length) {
			Class16_Sub5_Sub1.anIntArray239 = new int[local23];
		}
		if (PcmPlayerThread.aClass4_Sub12_7.buffer.length < this.anInt5270 * 2) {
			PcmPlayerThread.aClass4_Sub12_7 = new Packet(this.anInt5270 * 2);
		}
		@Pc(59) int local59 = arg0 - arg4;
		@Pc(61) int local61 = local59;
		if (local59 < 0) {
			local59 = 0;
		}
		@Pc(70) int local70 = arg3 - arg4;
		@Pc(72) int local72 = local70;
		if (local70 < 0) {
			local70 = 0;
		}
		@Pc(81) int local81 = arg0 + arg4;
		if (local81 > super.anInt5262 - 1) {
			local81 = super.anInt5262 - 1;
		}
		@Pc(100) int local100 = arg4 + arg3;
		if (super.anInt5258 - 1 < local100) {
			local100 = super.anInt5258 - 1;
		}
		LegacyMouseManager.anInt6220 = 0;
		@Pc(130) int local130;
		for (@Pc(119) int local119 = local59; local119 <= local81; local119++) {
			@Pc(128) boolean[] local128 = arg2[local119 - local61];
			for (local130 = local70; local130 <= local100; local130++) {
				if (local128[local130 - local72]) {
					Class16_Sub5_Sub1.anIntArray239[LegacyMouseManager.anInt6220++] = local130 * super.anInt5262 + local119;
				}
			}
		}
		this.glToolkit.method1904();
		this.glToolkit.method1906((this.featureFlags & 0x7) != 0);
		for (@Pc(194) int local194 = 0; local194 < this.layers.length; local194++) {
			this.layers[local194].method1462(Class16_Sub5_Sub1.anIntArray239, LegacyMouseManager.anInt6220);
		}
		if (!this.lights.method4147()) {
			local130 = this.glToolkit.anInt2302;
			@Pc(227) int local227 = this.glToolkit.anInt2315;
			this.glToolkit.UA(0, local227, this.glToolkit.anInt2282);
			this.glToolkit.g(local12, local8 - 4.0F);
			this.glToolkit.method1906(false);
			this.glToolkit.method1911(false);
			this.glToolkit.method1958(128);
			this.glToolkit.method1957(-2);
			this.glToolkit.setActiveTexture(this.glToolkit.texture2d);
			this.glToolkit.method1912(8448, 7681);
			this.glToolkit.method1945(0, 770, 34166);
			this.glToolkit.method1954(34167, 0);
			for (@Pc(294) Country local294 = this.lights.method4140(); local294 != null; local294 = this.lights.method4144()) {
				@Pc(299) LightGL local299 = (LightGL) local294;
				local299.method2842(arg4, arg0, arg3, arg2);
			}
			this.glToolkit.method1945(0, 768, 5890);
			this.glToolkit.method1954(5890, 0);
			this.glToolkit.setActiveTexture(null);
			this.glToolkit.UA(local130, local227, this.glToolkit.anInt2282);
		}
		if (this.shadowMap != null) {
			this.glToolkit.g(local12, local8 - 8.0F);
			this.glToolkit.method1904();
			this.glToolkit.method1934(null, null, this.aClass177_14, this.aClass177_15);
			this.shadowMap.renderShadowOverlay(arg1, arg3, arg0, arg2, arg4);
		}
		this.glToolkit.g(local12, local8);
	}

@OriginalMember(owner = "client!oq", name = "G", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_60;
@OriginalMember(owner = "client!oq", name = "O", descriptor = "Lclient!tb;")
	public static EnvironmentSampler aClass99_6;
@OriginalMember(owner = "client!oq", name = "j", descriptor = "[S")
	public static final short[] aShortArray175 = new short[] { -10304, 9104, -1, -1, -1 };
@OriginalMember(owner = "client!oq", name = "R", descriptor = "Lclient!wa;")
	public static final Class174 aClass174_1 = SceneLocEntity.method2891();
@OriginalMember(owner = "client!oq", name = "V", descriptor = "I")
	public static int lastSentMouseX = 0;
@OriginalMember(owner = "client!oq", name = "a", descriptor = "(BLclient!ya;)V")
	public static void method4033(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(7) int local7 = Class4_Sub37.anInt5424;
		@Pc(9) int local9 = Class111.anInt3367;
		@Pc(11) int local11 = Static173.anInt3370;
		@Pc(13) int local13 = Class4_Sub18.anInt2392;
		arg0.method4293(local7, local11, local9, -10660793, local13);
		arg0.method4293(local7 + 1, local11 - 2, local9 + 1, -16777216, 16);
		arg0.method4295(local7 + 1, -16777216, -19 + local13, local11 - 2, local9 + 18);
		Static331.aClass46_10.method5058(-1, -10660793, Static391.aClass83_133.method2267(NativeBillboardFace.anInt3423), local9 + 14, local7 + 3);
		@Pc(77) int local77 = MaterialType.aClass80_1.method2234();
		@Pc(81) int local81 = MaterialType.aClass80_1.getDefaultValue();
		@Pc(83) int local83 = 0;
		for (@Pc(90) OggOtherStream local90 = (OggOtherStream) Class126.aClass183_28.method4140(); local90 != null; local90 = (OggOtherStream) Class126.aClass183_28.method4144()) {
			@Pc(105) int local105 = (Skybox.anInt6710 - local83 - 1) * 16 + local9 + 31;
			@Pc(107) short local107 = -1;
			if (local7 < local77 && local7 + local11 > local77 && local81 > local105 - 13 && local105 + 3 > local81 && local90.aBoolean512) {
				local107 = -256;
			}
			@Pc(149) int[] local149 = null;
			if (Static441.method5569(local90.anInt5699)) {
				local149 = Terrain.aClass206_3.method4703((int) local90.aLong192).quests;
			} else if (local90.anInt5695 != -1) {
				local149 = Terrain.aClass206_3.method4703(local90.anInt5695).quests;
			} else if (TextureOpBrightness.method4382(local90.anInt5699)) {
				@Pc(214) NPCEntity local214 = Class93.aClass16_Sub1_Sub5_Sub2Array1[(int) local90.aLong192];
				if (local214 != null) {
					@Pc(219) NPCType local219 = local214.npcType;
					if (local219.anIntArray544 != null) {
						local219 = local219.method5574(Class4_Sub6_Sub24.aClass114_1);
					}
					if (local219 != null) {
						local149 = local219.anIntArray547;
					}
				}
			} else if (StructTypeList.method1982(local90.anInt5699)) {
				@Pc(186) LocType local186;
				if (local90.anInt5699 == 1010) {
					local186 = Static267.aClass262_2.method5560((int) local90.aLong192);
				} else {
					local186 = Static267.aClass262_2.method5560((int) (local90.aLong192 >>> 32 & 0x7FFFFFFFL));
				}
				if (local186.alternativeIds != null) {
					local186 = local186.method4584(Class4_Sub6_Sub24.aClass114_1);
				}
				if (local186 != null) {
					local149 = local186.quests;
				}
			}
			@Pc(255) String local255 = Class4_Sub21_Sub2.method3166(local90);
			if (local149 != null) {
				local255 = local255 + Class259.method5504(local149);
			}
			Static331.aClass46_10.method5057(Class27_Sub4.anIntArray338, Class70_Sub2.aClass57Array16, local107, local255, local7 + 3, local105);
			local83++;
			if (local90.aBoolean511) {
				TextureOpVoronoiNoise.aClass57_22.render(Js5ResourceProvider.aClass89_5.getWidthNoSprites(local255) + local7 + 5, local105 + -12);
			}
		}
		VertexDeclarationComponent.method5216(Static173.anInt3370, Class4_Sub18.anInt2392, Class111.anInt3367, Class4_Sub37.anInt5424);
	}
}
