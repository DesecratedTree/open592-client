package com.jagex.game.runetek5.graphics.gl;

import jaggl.OpenGL;
import jaclib.memory.NativeBuffer;
import jaclib.memory.heap.NativeHeap;
import jaclib.memory.Stream;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.GlRenderTarget;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.OpenGLArbTextureCubeMapGenerator;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.cache.node.GlMemoryPool;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.cache.node.MemoryPool;
import com.jagex.game.runetek5.cache.node.PointLight;
import com.jagex.game.runetek5.client.camera.FogDistance;
import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.atmosphere.HdAtmosphere;
import com.jagex.game.runetek5.client.hd.ground.GroundTileSpace;
import com.jagex.game.runetek5.client.hd.ground.HdGroundTextures;
import com.jagex.game.runetek5.client.hd.ground.HdMaterialArt;
import com.jagex.game.runetek5.client.hd.ground.HdMaterialMap;
import com.jagex.game.runetek5.client.hd.ground.HdTerrainBlend;
import com.jagex.game.runetek5.client.hd.ground.HdTileMaterialMap;
import com.jagex.game.runetek5.client.hd.ground.HdWorldPage;
import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;
import com.jagex.game.runetek5.client.hd.light.HdEnvironmentLight;
import com.jagex.game.runetek5.client.hd.light.HdLightFrame;
import com.jagex.game.runetek5.client.hd.reflect.HdReflection;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRequest;
import com.jagex.game.runetek5.client.hd.shader.HdDrawRows;
import com.jagex.game.runetek5.client.hd.shader.HdDrawSpace;
import com.jagex.game.runetek5.client.hd.shader.HdFrameState;
import com.jagex.game.runetek5.client.hd.shader.HdLightingGate;
import com.jagex.game.runetek5.client.hd.shader.HdLightingPipeline;
import com.jagex.game.runetek5.client.hd.shader.HdShaderSource;
import com.jagex.game.runetek5.client.hd.shader.JagglShaderApi;
import com.jagex.game.runetek5.client.hd.state.HdGlState;
	import com.jagex.game.runetek5.client.hd.post.HdPostParams;
	import com.jagex.game.runetek5.client.hd.post.HdPostProcess;
	import com.jagex.game.runetek5.client.hd.sky.HdDayNight;
import com.jagex.game.runetek5.client.hd.sky.HdSkyColours;
import com.jagex.game.runetek5.client.hd.sky.HdSkyPipeline;
import com.jagex.game.runetek5.client.hd.sky.HdSkyTime;
import com.jagex.game.runetek5.client.hd.sky.SkyGate;	import com.jagex.game.runetek5.client.hd.water.HdWaterBreaking;
	import com.jagex.game.runetek5.client.hd.water.HdWaterGate;
	import com.jagex.game.runetek5.client.hd.water.HdWaterPipeline;
import com.jagex.game.runetek5.client.hd.water.HdWaterRequest;
import com.jagex.game.runetek5.client.hd.water.HdWaterSource;
import com.jagex.game.runetek5.client.hd.water.HdWaterTime;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;
import com.jagex.game.runetek5.client.hd.shadow.ShadowCaster;
import com.jagex.game.runetek5.client.hd.shadow.ShadowCasterSet;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowFrame;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowGate;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowSubmission;
import com.jagex.game.runetek5.client.hd.shadow.SunShadowMap;
import com.jagex.game.runetek5.client.hd.sway.HdSway;
import com.jagex.game.runetek5.client.hd.sway.HdSwayGate;
import com.jagex.game.runetek5.client.hd.sway.HdVegetation;
import com.jagex.game.runetek5.client.hd.sway.HdWindTime;
import com.jagex.game.runetek5.client.render.RendererInfo;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class183;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class4_Sub37;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class64_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class99_Sub1;
import com.jagex.game.runetek5.deob.GlVboElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Static179;
import com.jagex.game.runetek5.deob.Static347;
import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.deob.ia;
import com.jagex.game.runetek5.deob.p;
import com.jagex.game.runetek5.deob.s;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.FramebufferCompositor;
import com.jagex.game.runetek5.graphics.NativeLibraries;
import com.jagex.game.runetek5.graphics.font.AbstractFontRenderer;
import com.jagex.game.runetek5.graphics.font.GlFont;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.shader.Texture3DShaderGL;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.graphics.texture.OpenGl2DImageTexture;
import com.jagex.game.runetek5.graphics.texture.RectangularTextureGL;
import com.jagex.game.runetek5.scene.ground.GlGround;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.toolkit.opengl.GlParticleRenderer;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static99;
import com.jagex.game.runetek5.graphics.gl.*;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchInfo;
import com.jagex.game.runetek5.config.cursor.CursorType;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.deob.SceneLocEntity;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class4_Sub41;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub2;
import com.jagex.game.runetek5.config.material.texture.TextureType;
@OriginalClass("client!fd")
public final class GlToolkit extends com.jagex.game.runetek5.graphics.Toolkit {

	@OriginalMember(owner = "client!fd", name = "K", descriptor = "I")
	private int anInt2106;

	@OriginalMember(owner = "client!fd", name = "tb", descriptor = "I")
	public int anInt2138;

	@OriginalMember(owner = "client!fd", name = "Uc", descriptor = "I")
	private int anInt2214;

	@OriginalMember(owner = "client!fd", name = "vd", descriptor = "I")
	public int anInt2240;

	@OriginalMember(owner = "client!fd", name = "pe", descriptor = "Lclient!jaggl/memory/NativeHeap;")
	public NativeHeap aNativeHeap2;

	@OriginalMember(owner = "client!fd", name = "qe", descriptor = "I")
	public int anInt2274;

	@OriginalMember(owner = "client!fd", name = "ve", descriptor = "Lclient!cp;")
	private GlRenderTarget anInterface3_1;

	@OriginalMember(owner = "client!fd", name = "ye", descriptor = "Lclient!cp;")
	private GlRenderTarget anInterface3_2;

	@OriginalMember(owner = "client!fd", name = "Ce", descriptor = "I")
	public int textureBuffersSize;

	@OriginalMember(owner = "client!fd", name = "Ee", descriptor = "I")
	public int vboBuffersSize;

	@OriginalMember(owner = "client!fd", name = "Fe", descriptor = "I")
	private int renderBuffersSize;

	@OriginalMember(owner = "client!fd", name = "Pe", descriptor = "Z")
	public boolean aBoolean192;

	@OriginalMember(owner = "client!fd", name = "Re", descriptor = "I")
	private int anInt2284;

	@OriginalMember(owner = "client!fd", name = "Se", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_1;

	@OriginalMember(owner = "client!fd", name = "Te", descriptor = "I")
	private int anInt2285;

	@OriginalMember(owner = "client!fd", name = "Ye", descriptor = "I")
	public int anInt2288;

	@OriginalMember(owner = "client!fd", name = "Ze", descriptor = "I")
	public int sunSlopeZ;

	@OriginalMember(owner = "client!fd", name = "af", descriptor = "Z")
	private boolean arbMultisample;

	@OriginalMember(owner = "client!fd", name = "df", descriptor = "Z")
	private boolean aBoolean195;

	@OriginalMember(owner = "client!fd", name = "ef", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_2;

	@OriginalMember(owner = "client!fd", name = "gf", descriptor = "F")
	public float aFloat59;

	@OriginalMember(owner = "client!fd", name = "hf", descriptor = "F")
	private float aFloat60;

	@OriginalMember(owner = "client!fd", name = "jf", descriptor = "I")
	private int lastSceneLightCount;

	/**
	 * The 592HD lighting program, owned for this context's lifetime: created
	 * here, prepared from the settings backend, deleted in {@link #stop()} before
	 * the GL context is released.
	 */
	private final HdLightingPipeline hdLighting = new HdLightingPipeline(new JagglShaderApi());

	/**
	 * True while the client is baking the top-down map texture.
	 *
	 * <p>That bake draws the scene's ground objects through the same pass as the
	 * world - {@code GlGround.method4025} - into a small texture, from a
	 * projection of its own. The per-pixel program's lighting, shadow map and fog
	 * are all expressed in the scene camera's space, so applying them here lights
	 * a view the camera never had: the whole map collapses to one fogged tone.
	 * The stock path draws that bake with the fixed-function lights it sets up
	 * itself, so the seam stands aside for it.
	 */
	private boolean hdMapBake;

	/** The model-to-scene matrix of the primitive being drawn; see method1948. */
	private final float[] hdModelMatrix = { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
		0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };

	/**
	 * Tile space to scene space for the terrain seam, rewritten by every ground
	 * draw; see {@link GroundTileSpace#tileToScene}.
	 */
	private final float[] hdTileToScene = { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F,
		0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };

	/**
	 * {@code rotX(180) * V}, rewritten on demand by the terrain seam and by a
	 * displaced vegetation draw; see {@link GroundTileSpace#sceneToEye}.
	 */
	private float[] hdSceneToEye;

	/** Vegetation primitives submitted for displacement since startup. */
	private long hdSwayBinds;

	/** HD water's program owner; created with the toolkit, compiled on first use. */
	private final HdWaterPipeline hdWater = new HdWaterPipeline(new JagglShaderApi());

	/** One line on the first live 592HD water draw; zero draws means the seam is dead. */
	private boolean hdWaterReported;

	/** True while an HD water draw has the environment cube on unit 1, so it is cleared. */
	private boolean hdWaterCubeBound;

	/** True while the reflection cube is bound on its own unit for the open draw. */
	private boolean hdReflectionCubeBound;

	/**
	 * The sun shadow map's raw name while it is bound on its sampler unit, else 0.
	 *
	 * <p>The cache in {@code aClass6Array1} can only name a {@code Class6}; this is
	 * the one texture the toolkit binds that is not one. Keeping its name beside
	 * the cache is what lets a bind on that unit clear it and what makes
	 * {@link #hdGlState()} describe the unit the way GL actually has it.
	 */
	private int hdShadowTextureName;

	/**
	 * The alpha-test function this toolkit last issued, or {@link HdGlState#UNKNOWN}
	 * before it has issued one.
	 *
	 * <p>Unlike the enable flags this has no short-circuiting setter: it is issued
	 * once, by {@link #prepareForRendering()}, and every pass that changes it (the
	 * sun's cut-out) saves and restores it. Recording the value the toolkit set is
	 * what lets {@link #hdGlState()} answer for it, so a pass that restores the
	 * flag but not the function is named instead of quietly leaving a half-opaque
	 * threshold behind for the interface's sprites.
	 */
	private int hdAlphaFunc = HdGlState.UNKNOWN;

	/** The depth function this toolkit last issued, or {@link HdGlState#UNKNOWN}. */
	private int hdDepthFunc = HdGlState.UNKNOWN;

	/**
	 * Units 3..5 still hold the ground atlas from the draw that bound them; the
	 * seam releases them when that draw ends.
	 */
	private boolean hdGroundUnitsBound;

	/**
	 * The map's art test: whether a page build would fill this material's cell.
	 *
	 * <p>Delegated, not restated: {@link HdMaterialArt} is the one owner of the
	 * upload's format and of the page's ability to hold a material, so the map and
	 * the page cannot disagree about either. A cell the page skipped holds zeroes,
	 * which the ground shader reads as a black texel with alpha zero - discarded
	 * by the pass' alpha test, so the terrain writes no colour and no depth and
	 * whatever is behind it draws through.
	 */
	private final HdMaterialMap.Art hdGroundArt = new HdMaterialMap.Art() {
		@Override
		public boolean available(int material) {
			return hdGroundArtAvailable(material);
		}
	};

	/** The material's stock texture-effect slot for the draw being submitted; -1 when none. */
	private int hdMaterialSlot = -1;

	/** True between a model material bind and the draw that consumes it. */
	private boolean hdModelMaterialBind;

	/** The ground material map: which page cell holds which material's texture. */
	private final HdMaterialMap hdMaterialMap = new HdMaterialMap();

	/** 592HD: ground draws that bound the world-texture atlas (one per layer draw). */
	private long hdGroundAtlasBinds;

	/** 592HD: atlas pages uploaded, and the pages they retired. */
	private long hdGroundPageBuilds;

	private long hdGroundPageDisposals;

	/** The world-texture page and its material map, built as materials appear. */
	private OpenGl2DTexture hdWorldPage;

	private OpenGl2DTexture hdMaterialIdMap;

	private int hdWorldPageVersion = -1;

	/** The screen-space pass; created with the toolkit, compiled on first use. */
	private final HdPostProcess hdPost = new HdPostProcess(new JagglShaderApi());

	/** The sky dome; created with the toolkit, compiled on the first frame that draws it. */
	private final HdSkyPipeline hdSky = new HdSkyPipeline(new JagglShaderApi());

	/** Created on the first sun shadow pass; null until then, and after teardown. */
	private SunShadowMap sunShadow;

	/** The per-frame shadow driver: gating, order and counters. Never null. */
	private final SunShadowFrame sunShadowFrame = new SunShadowFrame();

	/** The scene's per-frame casters-only submission; never null, empty until fed. */
	private final SunShadowSubmission hdShadowSubmission = new SunShadowSubmission();

	/** Scratch for one caster's matrix, so the pass allocates nothing per frame. */
	private final float[] hdShadowMatrix = new float[16];

	/** Casters the last {@link #hdSunShadowFrame} call actually submitted. */
	private long hdShadowCasters;

	/** Material textures the last frame's casters bound; zero for a solid set. */
	private long hdShadowMaterialBinds;

	/** True while the world draw is recording this frame's casters. */
	private volatile boolean hdShadowCollecting;

	@OriginalMember(owner = "client!fd", name = "kf", descriptor = "Lclient!bk;")
	private ArrayBufferGL anInterface1_1;

	@OriginalMember(owner = "client!fd", name = "lf", descriptor = "Z")
	private boolean aBoolean197;

	@OriginalMember(owner = "client!fd", name = "pf", descriptor = "Lclient!af;")
	public OpenGl2DTexture aClass6_Sub1_1;

	@OriginalMember(owner = "client!fd", name = "qf", descriptor = "F")
	public float aFloat62;

	@OriginalMember(owner = "client!fd", name = "rf", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_3;

	@OriginalMember(owner = "client!fd", name = "uf", descriptor = "Z")
	public boolean aBoolean198;

	@OriginalMember(owner = "client!fd", name = "xf", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_4;

	@OriginalMember(owner = "client!fd", name = "zf", descriptor = "Lclient!af;")
	public OpenGl2DTexture aClass6_Sub1_2;

	@OriginalMember(owner = "client!fd", name = "Bf", descriptor = "Z")
	public boolean arbTextureCubeMap;

	@OriginalMember(owner = "client!fd", name = "Cf", descriptor = "Z")
	private boolean aBoolean200;

	@OriginalMember(owner = "client!fd", name = "Df", descriptor = "Lclient!pc;")
	public Class177 quadTexCoordBuffer;

	@OriginalMember(owner = "client!fd", name = "Ff", descriptor = "F")
	public float aFloat64;

	@OriginalMember(owner = "client!fd", name = "Gf", descriptor = "I")
	private int anInt2298;

	@OriginalMember(owner = "client!fd", name = "Hf", descriptor = "Z")
	private boolean aBoolean201;

	@OriginalMember(owner = "client!fd", name = "If", descriptor = "Lclient!ps;")
	private OpenGl2DImageTexture worldMapTexture;

	@OriginalMember(owner = "client!fd", name = "Jf", descriptor = "Lclient!vd;")
	private ElementsBufferGL anInterface11_2;

	@OriginalMember(owner = "client!fd", name = "Kf", descriptor = "Lclient!pc;")
	public Class177 quadVertexBuffer;

	@OriginalMember(owner = "client!fd", name = "Mf", descriptor = "Z")
	private boolean aBoolean202;

	@OriginalMember(owner = "client!fd", name = "Pf", descriptor = "Z")
	private boolean aBoolean203;

	@OriginalMember(owner = "client!fd", name = "Qf", descriptor = "Ljava/lang/String;")
	private String aString26;

	@OriginalMember(owner = "client!fd", name = "Rf", descriptor = "I")
	private int anInt2300;

	@OriginalMember(owner = "client!fd", name = "Sf", descriptor = "I")
	private int anInt2301;

	@OriginalMember(owner = "client!fd", name = "Tf", descriptor = "Z")
	public boolean aBoolean204;

	@OriginalMember(owner = "client!fd", name = "Uf", descriptor = "F")
	public float aFloat67;

	@OriginalMember(owner = "client!fd", name = "Wf", descriptor = "F")
	public float aFloat68;

	@OriginalMember(owner = "client!fd", name = "Xf", descriptor = "Z")
	private boolean aBoolean206;

	@OriginalMember(owner = "client!fd", name = "Yf", descriptor = "Lclient!ml;")
	public GlFramebuffer aClass150_1;

	@OriginalMember(owner = "client!fd", name = "ag", descriptor = "Lclient!vd;")
	private ElementsBufferGL glBinding;

	@OriginalMember(owner = "client!fd", name = "bg", descriptor = "Lclient!tu;")
	public JavaMatrix aClass31_Sub1_3;

	@OriginalMember(owner = "client!fd", name = "cg", descriptor = "F")
	private float aFloat69;

	@OriginalMember(owner = "client!fd", name = "dg", descriptor = "I")
	private int anInt2303;

	@OriginalMember(owner = "client!fd", name = "eg", descriptor = "I")
	private int anInt2304;

	@OriginalMember(owner = "client!fd", name = "gg", descriptor = "F")
	public float sunBrightness;

	@OriginalMember(owner = "client!fd", name = "hg", descriptor = "Lclient!tu;")
	public JavaMatrix aClass31_Sub1_4;

	@OriginalMember(owner = "client!fd", name = "ig", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_5;

	@OriginalMember(owner = "client!fd", name = "jg", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_6;

	@OriginalMember(owner = "client!fd", name = "lg", descriptor = "Lclient!ip;")
	public GlModel copyTarget1;

	@OriginalMember(owner = "client!fd", name = "mg", descriptor = "I")
	public int anInt2306;

	@OriginalMember(owner = "client!fd", name = "qg", descriptor = "Ljava/lang/String;")
	private String aString27;

	@OriginalMember(owner = "client!fd", name = "ug", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_8;

	@OriginalMember(owner = "client!fd", name = "xg", descriptor = "[Lclient!kj;")
	private Class6[] aClass6Array1;

	@OriginalMember(owner = "client!fd", name = "Ag", descriptor = "I")
	private int anInt2310;

	@OriginalMember(owner = "client!fd", name = "Bg", descriptor = "I")
	public int sunSlopeX;

	@OriginalMember(owner = "client!fd", name = "Cg", descriptor = "Lclient!af;")
	public OpenGl2DTexture texture2d;

	@OriginalMember(owner = "client!fd", name = "Eg", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_9;

	@OriginalMember(owner = "client!fd", name = "Hg", descriptor = "Z")
	public boolean aBoolean209;

	@OriginalMember(owner = "client!fd", name = "Ig", descriptor = "I")
	private int anInt2314;

	@OriginalMember(owner = "client!fd", name = "Kg", descriptor = "Lclient!ml;")
	public GlFramebuffer aClass150_2;

	@OriginalMember(owner = "client!fd", name = "Mg", descriptor = "I")
	private int anInt2316;

	@OriginalMember(owner = "client!fd", name = "Og", descriptor = "Z")
	private boolean aBoolean212;

	@OriginalMember(owner = "client!fd", name = "Pg", descriptor = "F")
	public float aFloat75;

	@OriginalMember(owner = "client!fd", name = "Rg", descriptor = "Z")
	public boolean framebufferObjectSupported;

	@OriginalMember(owner = "client!fd", name = "Tg", descriptor = "Lclient!ip;")
	public GlModel aClass110_Sub1_10;

	@OriginalMember(owner = "client!fd", name = "Ug", descriptor = "F")
	private float aFloat76;

	@OriginalMember(owner = "client!fd", name = "Wg", descriptor = "J")
	private long lastGcTime;

	@OriginalMember(owner = "client!fd", name = "Xg", descriptor = "Z")
	public boolean aBoolean214;

	@OriginalMember(owner = "client!fd", name = "Yg", descriptor = "Z")
	public boolean bigEndianess;

	@OriginalMember(owner = "client!fd", name = "Zg", descriptor = "I")
	private int anInt2319;

	@OriginalMember(owner = "client!fd", name = "ah", descriptor = "Z")
	private boolean aBoolean216;

	@OriginalMember(owner = "client!fd", name = "bh", descriptor = "Z")
	public boolean aBoolean217;

	@OriginalMember(owner = "client!fd", name = "ch", descriptor = "I")
	private int anInt2320;

	@OriginalMember(owner = "client!fd", name = "dh", descriptor = "Lclient!tm;")
	private Class99_Sub1 aClass99_Sub1_1;

	@OriginalMember(owner = "client!fd", name = "wd", descriptor = "Ljava/util/Hashtable;")
	private final Hashtable aHashtable2 = new Hashtable();

	@OriginalMember(owner = "client!fd", name = "ce", descriptor = "I")
	public int size = 128;

	@OriginalMember(owner = "client!fd", name = "he", descriptor = "Lclient!cm;")
	private final GlParticleRenderer particleRenderer = new GlParticleRenderer();

	@OriginalMember(owner = "client!fd", name = "le", descriptor = "Lclient!tu;")
	private final JavaMatrix aClass31_Sub1_1 = new JavaMatrix();

	@OriginalMember(owner = "client!fd", name = "me", descriptor = "Lclient!tu;")
	public final JavaMatrix aClass31_Sub1_2 = new JavaMatrix();

	@OriginalMember(owner = "client!fd", name = "re", descriptor = "I")
	public int shadowScaleShift = 3;

	@OriginalMember(owner = "client!fd", name = "oe", descriptor = "I")
	public int shadowScale = 8;

	@OriginalMember(owner = "client!fd", name = "ne", descriptor = "Z")
	private boolean colortableReset = false;

	@OriginalMember(owner = "client!fd", name = "te", descriptor = "Lclient!pk;")
	private final Class183 heapManager = new Class183();

	@OriginalMember(owner = "client!fd", name = "ue", descriptor = "[Lclient!cp;")
	private final GlRenderTarget[] drawTargetStack = new GlRenderTarget[4];

	@OriginalMember(owner = "client!fd", name = "xe", descriptor = "I")
	private int anInt2277 = -1;

	@OriginalMember(owner = "client!fd", name = "we", descriptor = "I")
	private int anInt2276 = -1;

	@OriginalMember(owner = "client!fd", name = "Be", descriptor = "[Lclient!cp;")
	private final GlRenderTarget[] readTargetStack = new GlRenderTarget[4];

	@OriginalMember(owner = "client!fd", name = "Ae", descriptor = "[Lclient!cp;")
	private final GlRenderTarget[] targetStack = new GlRenderTarget[4];

	@OriginalMember(owner = "client!fd", name = "ze", descriptor = "I")
	private int anInt2278 = -1;

	@OriginalMember(owner = "client!fd", name = "De", descriptor = "Lclient!pk;")
	private final Class183 listsAwaitingDeallocation;

	@OriginalMember(owner = "client!fd", name = "Ge", descriptor = "Lclient!pk;")
	private final Class183 vboBuffersAwaitingDeallocation;

	@OriginalMember(owner = "client!fd", name = "He", descriptor = "Lclient!pk;")
	private final Class183 texturesAwaitingDeallocation;

	@OriginalMember(owner = "client!fd", name = "Ie", descriptor = "Lclient!pk;")
	private final Class183 frameBuffersAwaitingDeallocation;

	@OriginalMember(owner = "client!fd", name = "Je", descriptor = "Lclient!pk;")
	private final Class183 aClass183_11;

	@OriginalMember(owner = "client!fd", name = "Ke", descriptor = "Lclient!pk;")
	private final Class183 arbProgramsAwaitingDeallocation;

	@OriginalMember(owner = "client!fd", name = "Le", descriptor = "Lclient!pk;")
	private final Class183 aClass183_13;

	@OriginalMember(owner = "client!fd", name = "We", descriptor = "F")
	public float aFloat58;

	@OriginalMember(owner = "client!fd", name = "Ne", descriptor = "I")
	public int anInt2282;

	@OriginalMember(owner = "client!fd", name = "of", descriptor = "[F")
	private final float[] antiSunPreviousPosition;

	@OriginalMember(owner = "client!fd", name = "cf", descriptor = "I")
	public int zNear;

	@OriginalMember(owner = "client!fd", name = "yf", descriptor = "I")
	private int anInt2296;

	@OriginalMember(owner = "client!fd", name = "Ue", descriptor = "I")
	private int sunColorRgb;

	@OriginalMember(owner = "client!fd", name = "wf", descriptor = "[Lclient!vp;")
	private final PointLight[] aClass4_Sub21Array1;

	@OriginalMember(owner = "client!fd", name = "sf", descriptor = "I")
	public int anInt2293;

	@OriginalMember(owner = "client!fd", name = "Ve", descriptor = "I")
	private int anInt2287;

	@OriginalMember(owner = "client!fd", name = "nf", descriptor = "I")
	private int anInt2292;

	@OriginalMember(owner = "client!fd", name = "tf", descriptor = "I")
	public int anInt2294;

	@OriginalMember(owner = "client!fd", name = "Af", descriptor = "I")
	public int anInt2297;

	@OriginalMember(owner = "client!fd", name = "bf", descriptor = "[F")
	private final float[] projectionMatrix;

	@OriginalMember(owner = "client!fd", name = "Qe", descriptor = "F")
	private float aFloat57;

	@OriginalMember(owner = "client!fd", name = "kg", descriptor = "I")
	private int anInt2305;

	@OriginalMember(owner = "client!fd", name = "mf", descriptor = "F")
	public float projectedFarClipDistance;

	@OriginalMember(owner = "client!fd", name = "fg", descriptor = "[F")
	private final float[] lightingDirection;

	@OriginalMember(owner = "client!fd", name = "tg", descriptor = "F")
	public float aFloat72;

	@OriginalMember(owner = "client!fd", name = "vg", descriptor = "F")
	public float aFloat73;

	@OriginalMember(owner = "client!fd", name = "vf", descriptor = "I")
	public int anInt2295;

	@OriginalMember(owner = "client!fd", name = "Of", descriptor = "F")
	private float aFloat66;

	@OriginalMember(owner = "client!fd", name = "Zf", descriptor = "I")
	public int anInt2302;

	@OriginalMember(owner = "client!fd", name = "Ef", descriptor = "F")
	public float aFloat63;

	@OriginalMember(owner = "client!fd", name = "wg", descriptor = "I")
	private int anInt2308;

	@OriginalMember(owner = "client!fd", name = "Gg", descriptor = "[F")
	public final float[] sunDirection;

	@OriginalMember(owner = "client!fd", name = "Qg", descriptor = "I")
	public int anInt2317;

	@OriginalMember(owner = "client!fd", name = "Lg", descriptor = "Z")
	private boolean depthBufferingEnabled;

	@OriginalMember(owner = "client!fd", name = "Jg", descriptor = "I")
	public int anInt2315;

	@OriginalMember(owner = "client!fd", name = "ng", descriptor = "[F")
	private final float[] sunPreviousPosition;

	@OriginalMember(owner = "client!fd", name = "og", descriptor = "F")
	private float aFloat71;

	@OriginalMember(owner = "client!fd", name = "Dg", descriptor = "I")
	public int anInt2312;

	/**
	 * Scene far plane before user fog scaling. The effective far plane in
	 * anInt2312 is this value scaled by the smoothed fog level, so
	 * projection, culling and fog all follow one render-distance control.
	 */
	private int sceneFarPlane = 3584;

	@OriginalMember(owner = "client!fd", name = "zg", descriptor = "F")
	public float aFloat74;

	@OriginalMember(owner = "client!fd", name = "yg", descriptor = "I")
	public int anInt2309;

	@OriginalMember(owner = "client!fd", name = "Fg", descriptor = "I")
	private int clipLeft;

	@OriginalMember(owner = "client!fd", name = "Nf", descriptor = "F")
	public float aFloat65;

	@OriginalMember(owner = "client!fd", name = "Lf", descriptor = "I")
	private int anInt2299;

	@OriginalMember(owner = "client!fd", name = "Sg", descriptor = "I")
	private int anInt2318;

	@OriginalMember(owner = "client!fd", name = "Vg", descriptor = "F")
	public float farClipDistance;

	@OriginalMember(owner = "client!fd", name = "Oe", descriptor = "I")
	private int anInt2283;

	@OriginalMember(owner = "client!fd", name = "Sb", descriptor = "Ljava/awt/Canvas;")
	private final Canvas aCanvas3;

	@OriginalMember(owner = "client!fd", name = "hb", descriptor = "Ljava/awt/Canvas;")
	private Canvas aCanvas2;

	@OriginalMember(owner = "client!fd", name = "je", descriptor = "I")
	public final int multiSamplesPreference;

	@OriginalMember(owner = "client!fd", name = "P", descriptor = "Lclient!jaggl/OpenGL;")
	private OpenGL anOpenGL1;

	@OriginalMember(owner = "client!fd", name = "ed", descriptor = "J")
	private final long canvasSurface;

	@OriginalMember(owner = "client!fd", name = "mc", descriptor = "J")
	private long currentSurface;

	@OriginalMember(owner = "client!fd", name = "sg", descriptor = "I")
	public final int pixelType;

	@OriginalMember(owner = "client!fd", name = "Xe", descriptor = "Z")
	public boolean arbTextureFloat;

	@OriginalMember(owner = "client!fd", name = "Vf", descriptor = "Z")
	private boolean aBoolean205;

	@OriginalMember(owner = "client!fd", name = "Ng", descriptor = "Z")
	public boolean aBoolean211;

	@OriginalMember(owner = "client!fd", name = "pg", descriptor = "Z")
	public boolean arbRectangleTexturesSupported;

	@OriginalMember(owner = "client!fd", name = "rg", descriptor = "Z")
	private boolean shouldUseArbVbo;

	@OriginalMember(owner = "client!fd", name = "Me", descriptor = "Z")
	public boolean extFramebufferObject;

	@OriginalMember(owner = "client!fd", name = "ff", descriptor = "Z")
	private final boolean aBoolean196;

	@OriginalMember(owner = "client!fd", name = "ie", descriptor = "Lclient!ee;")
	public final OpenGLTextureCache textureCache;

	@OriginalMember(owner = "client!fd", name = "se", descriptor = "Lclient!de;")
	public final Texture3DShaderGL texture3dShader;

	@OriginalMember(owner = "client!fd", name = "ee", descriptor = "Lclient!wb;")
	private final FramebufferCompositor framebufferCompositor;

	@OriginalMember(owner = "client!fd", name = "ke", descriptor = "Lclient!fh;")
	private RectangularTextureGL aClass4_Sub10_Sub1_1;

	@OriginalMember(owner = "client!fd", name = "fe", descriptor = "Lclient!uv;")
	private final EffectManagerGL effectManager;

	@OriginalMember(owner = "client!fd", name = "<init>", descriptor = "(ILjava/awt/Canvas;Lclient!ga;ILclient!et;)V")
	public GlToolkit(@OriginalArg(0) int arg0, @OriginalArg(1) Canvas arg1, @OriginalArg(2) unpack_Sub1 arg2, @OriginalArg(3) int arg3, @OriginalArg(4) SignLink arg4) {
		super(arg0, arg2);
		new Class229();
		new CubeMapSpecularEffect(16);
		this.listsAwaitingDeallocation = new Class183();
		this.vboBuffersAwaitingDeallocation = new Class183();
		this.texturesAwaitingDeallocation = new Class183();
		this.frameBuffersAwaitingDeallocation = new Class183();
		this.aClass183_11 = new Class183();
		this.arbProgramsAwaitingDeallocation = new Class183();
		this.aClass183_13 = new Class183();
		this.aFloat58 = 1.0F;
		this.anInt2282 = 0;
		this.antiSunPreviousPosition = new float[4];
		this.zNear = 50;
		this.anInt2296 = 0;
		this.sunColorRgb = -1;
		this.aClass4_Sub21Array1 = new PointLight[SceneLocEntity.anInt3494];
		this.anInt2293 = 0;
		this.anInt2287 = -1;
		this.anInt2292 = 0;
		this.anInt2294 = 512;
		this.anInt2297 = 0;
		this.projectionMatrix = new float[16];
		this.aFloat57 = 0.0F;
		this.anInt2305 = 8448;
		this.projectedFarClipDistance = 3584.0F;
		this.lightingDirection = new float[4];
		this.aFloat72 = 1.0F;
		this.aFloat73 = -1.0F;
		this.anInt2295 = 0;
		this.aFloat66 = 1.0F;
		this.anInt2302 = -1;
		this.aFloat63 = 3000.0F;
		this.anInt2308 = 0;
		this.sunDirection = new float[4];
		this.anInt2317 = -1;
		this.depthBufferingEnabled = true;
		this.anInt2315 = -1;
		this.sunPreviousPosition = new float[4];
		this.aFloat71 = 1.0F;
		this.anInt2312 = 3584;
		this.aFloat74 = 1.0F;
		this.anInt2309 = 512;
		this.clipLeft = 0;
		this.aFloat65 = -1.0F;
		this.anInt2299 = 8448;
		this.anInt2318 = 0;
		this.farClipDistance = 3584.0F;
		this.anInt2283 = 0;
		this.aCanvas2 = this.aCanvas3 = arg1;
		this.multiSamplesPreference = arg3;
		try {
			if (Static179.aBoolean288 == null || !Static179.aBoolean288) {
				// jaclib is still extracted from JS5 index 30 for the running platform
				// (Windows/Linux/macOS, 32- or 64-bit); GL itself is LWJGL now, which
				// ships its own natives and needs no JAWT load of its own.
				NativeLibraries.load("jaclib");
				Static179.aBoolean288 = Boolean.TRUE;
			}
			if (Static179.aBoolean288 == null || !Static179.aBoolean288) {
				throw new RuntimeException("");
			}
			this.anOpenGL1 = new OpenGL();
			this.currentSurface = this.canvasSurface = this.anOpenGL1.init(arg1, 8, 8, 8, 24, 0, this.multiSamplesPreference);
			if (this.canvasSurface == 0L) {
				throw new RuntimeException("");
			}
			this.method1896();
			@Pc(353) int local353 = this.method1974();
			if (local353 != 0) {
				throw new RuntimeException("");
			}
			this.pixelType = this.bigEndianess ? 33639 : 5121;
			@Pc(376) int local376;
			if (this.aString27.indexOf("radeon") != -1) {
				local376 = 0;
				@Pc(378) boolean local378 = false;
				@Pc(380) boolean local380 = false;
				@Pc(389) String[] local389 = CursorType.method2058(this.aString27.replace('/', ' '), ' ');
				for (@Pc(391) int local391 = 0; local391 < local389.length; local391++) {
					@Pc(397) String local397 = local389[local391];
					try {
						if (local397.length() > 0) {
							if (local397.charAt(0) == 'x' && local397.length() >= 3 && Class60.method1590(local397.substring(1, 3))) {
								local397 = local397.substring(1);
								local380 = true;
							}
							if (local397.equals("hd")) {
								local378 = true;
							} else {
								if (local397.startsWith("hd")) {
									local378 = true;
									local397 = local397.substring(2);
								}
								if (local397.length() >= 4 && Class60.method1590(local397.substring(0, 4))) {
									local376 = ParticleProducerType.method3113(local397.substring(0, 4));
									break;
								}
							}
						}
					} catch (@Pc(464) Exception local464) {
					}
				}
				if (!local378 || local376 < 4000) {
					this.arbTextureFloat = false;
				}
				if (!local380 && !local378) {
					if (local376 >= 7000 && local376 <= 7999) {
						this.aBoolean205 = false;
					}
					if (local376 >= 7000 && local376 <= 9250) {
						this.aBoolean211 = false;
					}
				}
				this.arbRectangleTexturesSupported &= this.anOpenGL1.isExtensionAvailable("GL_ARB_half_float_pixel");
				this.shouldUseArbVbo = this.aBoolean205;
			}
			if (this.aString26.indexOf("intel") != -1) {
				this.extFramebufferObject = false;
			}
			this.aBoolean196 = !this.aString26.equals("s3 graphics");
			if (this.aBoolean205) {
				try {
					@Pc(543) int[] local543 = new int[1];
					OpenGL.glGenBuffersARB(1, local543, 0);
				} catch (@Pc(549) Throwable local549) {
					throw new RuntimeException("");
				}
			}
			Static347.method4678(false, true);
			this.colortableReset = true;
			this.textureCache = new OpenGLTextureCache(this, super.materialProvider);
			this.method1964();
			this.texture3dShader = new Texture3DShaderGL(this);
			this.framebufferCompositor = new FramebufferCompositor(this);
			if (this.framebufferCompositor.isDisplayAvailable()) {
				this.aClass4_Sub10_Sub1_1 = new RectangularTextureGL(this);
				if (!this.aClass4_Sub10_Sub1_1.method2008()) {
					this.aClass4_Sub10_Sub1_1.method2006();
					this.aClass4_Sub10_Sub1_1 = null;
				}
			}
			this.effectManager = new EffectManagerGL(this);
			this.prepareForRendering();
			this.method1909();
			OpenGL.glClear(16640);
			local376 = 0;
			while (true) {
				try {
					this.anOpenGL1.swapBuffers();
					break;
				} catch (@Pc(628) Exception local628) {
					if (local376++ > 5) {
						throw new RuntimeException("");
					}
					Class259.method5503(100L);
				}
			}
			OpenGL.glClear(16384);
		} catch (@Pc(648) Throwable local648) {
			if (local648 instanceof UnsatisfiedLinkError) {
				// A missing/incompatible native is an expected fallback to the
				// Java renderer; a full stack trace just looks like a crash.
				System.out.println("Unable to load the OpenGL natives: " + local648.getMessage());
			} else {
				local648.printStackTrace();
			}
			this.stop();
			throw new RuntimeException("");
		}
	}

	@OriginalMember(owner = "client!fd", name = "i", descriptor = "(I)V")
	private void method1896() {
		@Pc(7) int local7 = 0;
		while (!this.anOpenGL1.attach()) {
			if (local7++ > 5) {
				throw new RuntimeException("");
			}
			Class259.method5503(1000L);
		}
	}

	@OriginalMember(owner = "client!fd", name = "X", descriptor = "(III[I)V")
	@Override
	public void X(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int[] arg3) {
		@Pc(28) float local28 = (float) arg2 * this.aClass31_Sub1_4.aFloat199 + this.aClass31_Sub1_4.aFloat203 * (float) arg1 + this.aClass31_Sub1_4.aFloat206 * (float) arg0 + this.aClass31_Sub1_4.aFloat198;
		if ((float) this.zNear > local28 || local28 > (float) this.anInt2312) {
			arg3[0] = arg3[1] = arg3[2] = -1;
			return;
		}
		@Pc(85) int local85 = (int) ((float) this.anInt2309 * (this.aClass31_Sub1_4.aFloat209 + (float) arg2 * this.aClass31_Sub1_4.aFloat208 + this.aClass31_Sub1_4.aFloat202 * (float) arg0 + (float) arg1 * this.aClass31_Sub1_4.aFloat207) / local28);
		@Pc(117) int local117 = (int) ((this.aClass31_Sub1_4.aFloat205 + this.aClass31_Sub1_4.aFloat200 * (float) arg0 + (float) arg1 * this.aClass31_Sub1_4.aFloat204 + (float) arg2 * this.aClass31_Sub1_4.aFloat201) * (float) this.anInt2294 / local28);
		if ((float) local85 >= this.aFloat75 && this.aFloat64 >= (float) local85 && (float) local117 >= this.aFloat62 && (float) local117 <= this.aFloat68) {
			arg3[2] = (int) local28;
			arg3[1] = (int) ((float) local117 - this.aFloat62);
			arg3[0] = (int) ((float) local85 - this.aFloat75);
		} else {
			arg3[0] = arg3[1] = arg3[2] = -1;
		}
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(II)I")
	public int method1897(@OriginalArg(0) int arg0) {
		if (arg0 == 5121 || arg0 == 5120) {
			return 1;
		} else if (arg0 == 5123 || arg0 == 5122) {
			return 2;
		} else if (arg0 == 5125 || arg0 == 5124 || arg0 == 5126) {
			return 4;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	@OriginalMember(owner = "client!fd", name = "u", descriptor = "()Z")
	@Override
	public boolean hasTextures() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "()Z")
	@Override
	public boolean method4243() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void method4263(@OriginalArg(0) Canvas arg0) {
		if (arg0 == this.aCanvas3) {
			throw new RuntimeException();
		} else if (this.aHashtable2.containsKey(arg0)) {
			@Pc(22) Long local22 = (Long) this.aHashtable2.get(arg0);
			this.anOpenGL1.releaseSurface(arg0, local22);
			this.aHashtable2.remove(arg0);
		}
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "(Z)V")
	public void method1898() {
		if (this.anInt2319 != 0) {
			this.anInt2319 = 0;
			this.anInt2320 &= 0xFFFFFFE0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Z)V")
	@Override
	public void setShrinkTextures(@OriginalArg(0) boolean shrink) {
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(II[I[I)Lclient!ma;")
	@Override
	public ComponentSurface createMask(@OriginalArg(0) int width, @OriginalArg(1) int height, @OriginalArg(2) int[] rowOffsets, @OriginalArg(3) int[] rowWidths) {
		return Class243.method5256(height, rowOffsets, width, this, rowWidths);
	}

	@OriginalMember(owner = "client!fd", name = "j", descriptor = "(I)V")
	private void method1899() {
		if (this.anInt2319 != 3) {
			this.anInt2319 = 3;
			this.method1933();
			this.method1952();
			this.anInt2320 &= 0xFFFFFFF8;
		}
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(II)V")
	public void method1900(@OriginalArg(1) int arg0) {
		if (arg0 == 1) {
			this.method1912(7681, 7681);
		} else if (arg0 == 0) {
			this.method1912(8448, 8448);
		} else if (arg0 == 2) {
			this.method1912(34165, 7681);
		} else if (arg0 == 3) {
			this.method1912(260, 8448);
		} else if (arg0 == 4) {
			this.method1912(34023, 34023);
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(BI)V")
	public void method1901(@OriginalArg(1) int arg0) {
		Class4_Sub41.aFloatArray30[0] = (float) (arg0 & 0xFF0000) / 1.671168E7F;
		Class4_Sub41.aFloatArray30[2] = (float) (arg0 & 0xFF) / 255.0F;
		Class4_Sub41.aFloatArray30[3] = (float) (arg0 >>> 24) / 255.0F;
		Class4_Sub41.aFloatArray30[1] = (float) (arg0 & 0xFF00) / 65280.0F;
		OpenGL.glTexEnvfv(8960, 8705, Class4_Sub41.aFloatArray30, 0);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZILclient!jaggl/memory/NativeBuffer;II)Lclient!vd;")
	public ElementsBufferGL method1902(@OriginalArg(2) NativeBuffer arg0, @OriginalArg(3) int arg1, @OriginalArg(4) int arg2) {
		return (ElementsBufferGL) (this.aBoolean205 ? new GlVboElementsArrayBuffer(this, arg2, arg0, arg1, false) : new GlElementsArrayBuffer(this, arg2, arg0));
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(Z)V")
	@Override
	public void method4255(@OriginalArg(0) boolean enabled) {
	}

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "(Z)V")
	private void method1903() {
		@Pc(7) int local7;
		for (local7 = 0; local7 < this.lastSceneLightCount; local7++) {
			@Pc(19) PointLight local19 = this.aClass4_Sub21Array1[local7];
			@Pc(23) int local23 = local7 + 16386;
			Class126.aFloatArray23[0] = local19.method3160();
			Class126.aFloatArray23[1] = local19.method3161();
			Class126.aFloatArray23[2] = local19.method3164();
			Class126.aFloatArray23[3] = 1.0F;
			OpenGL.glLightfv(local23, 4611, Class126.aFloatArray23, 0);
			@Pc(57) int local57 = local19.method3162();
			@Pc(63) float local63 = local19.getIntensity() / 255.0F;
			Class126.aFloatArray23[0] = local63 * (float) (local57 >> 16 & 0xFF);
			Class126.aFloatArray23[1] = (float) (local57 >> 8 & 0xFF) * local63;
			Class126.aFloatArray23[2] = (float) (local57 & 0xFF) * local63;
			OpenGL.glLightfv(local23, 4609, Class126.aFloatArray23, 0);
			OpenGL.glLightf(local23, 4617, 1.0F / (float) (local19.method3165() * local19.method3165()));
			OpenGL.glEnable(local23);
		}
		while (local7 < this.anInt2284) {
			OpenGL.glDisable(local7 + 16386);
			local7++;
		}
		this.anInt2284 = this.lastSceneLightCount;
	}

	@OriginalMember(owner = "client!fd", name = "k", descriptor = "(I)V")
	public void method1904() {
		if (this.anInt2320 == 8) {
			return;
		}
		this.method1924();
		this.method1918(true);
		this.method1951(true);
		this.method1911(true);
		this.method1958(1);
		this.method1900(0);
		this.anInt2320 = 8;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(BLclient!kj;)V")
	public void setActiveTexture(@OriginalArg(1) Class6 arg0) {
		// The shadow map is not a Class6: hdBindShadowTexture binds it raw and
		// records its name here. Any bind on that unit has to clear it first,
		// or the cache would read "nothing bound" and the disable below would
		// be skipped, leaving a depth map sampling under whatever binds next.
		if (this.anInt2301 == HdShaderSource.SAMPLER_UNIT_SHADOW && this.hdShadowTextureName != 0) {
			OpenGL.glDisable(3553);
			OpenGL.glBindTexture(3553, 0);
			this.hdShadowTextureName = 0;
			this.aClass6Array1[this.anInt2301] = null;
		}
		@Pc(11) Class6 local11 = this.aClass6Array1[this.anInt2301];
		if (local11 != arg0) {
			if (arg0 == null) {
				// Clearing a unit disables the target *and* unbinds it. Disabling
				// alone leaves the name on the unit, so the cache's "nothing here"
				// would describe the enabled state while GL still holds a texture
				// that a later raw bind could re-enable by accident.
				OpenGL.glDisable(local11.anInt5441);
				OpenGL.glBindTexture(local11.anInt5441, 0);
			} else {
				if (local11 == null) {
					OpenGL.glEnable(arg0.anInt5441);
				} else if (arg0.anInt5441 != local11.anInt5441) {
					OpenGL.glDisable(local11.anInt5441);
					OpenGL.glEnable(arg0.anInt5441);
				}
				OpenGL.glBindTexture(arg0.anInt5441, arg0.method4186());
			}
			this.aClass6Array1[this.anInt2301] = arg0;
		}
		this.anInt2320 &= 0xFFFFFFFE;
	}

	@OriginalMember(owner = "client!fd", name = "fa", descriptor = "(IIII)V")
	@Override
	public void fa(@OriginalArg(0) int left, @OriginalArg(1) int arg1, @OriginalArg(2) int right, @OriginalArg(3) int arg3) {
		if (arg3 < this.anInt2296) {
			this.anInt2296 = arg3;
		}
		if (this.anInt2292 > right) {
			this.anInt2292 = right;
		}
		if (this.anInt2318 < arg1) {
			this.anInt2318 = arg1;
		}
		if (this.clipLeft < left) {
			this.clipLeft = left;
		}
		OpenGL.glEnable(3089);
		this.method1953();
		this.method1915();
	}

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "(I)Lclient!u;")
	@Override
	public MemoryPool createHeap(@OriginalArg(0) int size) {
		@Pc(8) GlMemoryPool local8 = new GlMemoryPool(size);
		this.heapManager.method4137(local8);
		return local8;
	}

	@OriginalMember(owner = "client!fd", name = "o", descriptor = "()I")
	@Override
	public int getMaxLights() {
		return 4;
	}

	/**
	 * 592HD seam: binds the per-pixel lighting program for one model primitive.
	 *
	 * <p>All this method does is gather this draw's state - the stock shader
	 * state already holds the sun, the point lights and the fog band, so the
	 * program only has to be bound. Whether the bind is allowed is
	 * {@link com.jagex.game.runetek5.client.hd.shader.HdLightingGate}'s decision,
	 * evaluated once inside the pipeline. The settings are asked first so a
	 * 592HD-off primitive allocates nothing on the model path.
	 *
	 * @param textured whether the primitive has a material texture
	 * @return true when the caller must call {@link #hdEndModelLighting()}
	 */
	public boolean hdBeginModelLighting(@OriginalArg(0) boolean textured) {
		return hdBeginModelLighting(textured, HdVegetation.NONE);
	}

	/**
	 * The model seam with the scene's own classification of the model.
	 *
	 * <p>The kind travels with the draw, exactly as the light rows do: it comes
	 * from the loc that submitted the model, not from anything this method could
	 * infer. {@code HdVegetation.NONE} - every model the scene did not mark - is
	 * the ordinary lit draw.
	 *
	 * <p>A vegetation draw additionally supplies the frame's scene-to-eye rows,
	 * which the model path otherwise never needs: a displaced vertex has no
	 * modelview entry that already contains its moved position, so the shader
	 * re-derives eye space from the same {@code rotX(180) * V} the modelview is
	 * built from. Nothing is supplied - and no GL call made - for a draw whose
	 * rows would not move it.
	 *
	 * @param textured   whether the primitive has a material texture
	 * @param vegetation this model's kind, from {@code HdVegetation}
	 * @return true when the caller must call {@link #hdEndModelLighting()}
	 */
	public boolean hdBeginModelLighting(@OriginalArg(0) boolean textured, @OriginalArg(1) int vegetation) {
		if (!hdLightingAllowed()) {
			return false;
		}
		@Pc(6) ShadowBinding shadow = hdBindShadowMap();
		@Pc(8) HdSway sway = hdSway();
		@Pc(10) boolean displaces = HdSwayGate.displaces(vegetation, sway);
		@Pc(12) float[] sceneToEye = displaces ? hdSceneToEye() : null;
		if (displaces) {
			this.hdSwayBinds++;
		}
		@Pc(14) HdDrawSpace space = HdDrawSpace.model(this.hdModelMatrix, sceneToEye);
		HdDrawRequest request = hdDrawRequest(textured, vegetation)
			.withMaterialEffectSlot(this.hdMaterialSlot);
		// The reflection cube goes on its own unit before the program binds, and
		// stays there until the draw ends. A draw that does not reflect leaves the
		// unit exactly as it found it.
		if (HdReflection.of(request.reflections(), request.materialEffectSlot()).active()) {
			GlCubeTexture environment = this.method1946();
			if (environment != null) {
				this.method1936(HdShaderSource.SAMPLER_UNIT_REFLECTION);
				this.setActiveTexture(environment);
				this.method1936(0);
				this.hdReflectionCubeBound = true;
			}
		}
		boolean bound = this.hdLighting.begin(request, space, hdFrameState(shadow).withSway(sway));
		if (!bound) {
			// The map was bound for a draw that did not happen; the caller will
			// not reach hdEndModelLighting().
			hdReleaseShadowTexture();
			if (this.hdReflectionCubeBound) {
				hdUnbindReflectionCube();
			}
		}
		return bound;
	}

	/**
	 * This draw's request: the primitive's own rows, named, over the scene's rows
	 * {@link HdDrawRequest#current} resolves from the settings and the gates. Both
	 * seams build it here, so what describes a draw - its normals, its material,
	 * its fog, its kind - is stated once and never by argument position.
	 *
	 * @param textured   whether the primitive has a material texture
	 * @param vegetation the scene's classification of the model
	 */
	private HdDrawRequest hdDrawRequest(boolean textured, int vegetation) {
		@Pc(4) HdDrawRows primitive = HdDrawRows.none()
			.with(HdDrawRows.Row.NORMALS, this.aBoolean212)
			.with(HdDrawRows.Row.TEXTURE, textured)
			.with(HdDrawRows.Row.MATERIAL_EFFECT, this.effectManager.isEffectActive())
			.with(HdDrawRows.Row.FOG, this.fogActive());
		return HdDrawRequest.current(primitive, this.anInt2301, this.anInt2299, vegetation);
	}

	/**
	 * The early-outs both lighting seams share, in one place: the row must be on
	 * and the map not baking, the sun's depth pass must not be running - inside
	 * it colour writes are off, so lighting and its shadow map would be work
	 * thrown away - and HD water must not own the draw, since binding the lit
	 * opaque program over it would replace the water with dry geometry.
	 */
	private boolean hdLightingAllowed() {
		if (!HdLightingGate.lightingRequested() || this.hdMapBake) {
			return false;
		}
		if (this.sunShadow != null && this.sunShadow.inPass()) {
			return false;
		}
		return !this.hdWater.bound();
	}

	/**
	 * True when this material is one the stock renderer already draws reflectively
	 * and the lit program can take the draw without changing its blend.
	 *
	 * <p>The slot is the material's own claim (see {@link HdReflection}): only the
	 * stock specular effect's slot is a reflection, so every other material keeps
	 * the stock pass. The rest are the lit program's preconditions, checked here so
	 * a draw is only taken from its effect when the program will actually bind:
	 * the row, the program, vertex normals, the base texture unit, and the
	 * {@code GL_MODULATE} environment the shader reproduces.
	 */
	private boolean hdReflectionOwns(MaterialType type) {
		if (type == null || !hdLightingAllowed()) {
			return false;
		}
		if (!HdReflection.of(HdSettings.value(HdFeature.REFLECTIONS) != 0, type.aByte89).active()) {
			return false;
		}
		if (type.anInt6934 != HdReflection.MODULATE_ENVIRONMENT || !this.aBoolean212) {
			return false;
		}
		if (this.anInt2301 != 0 || this.method1946() == null) {
			return false;
		}
		return this.hdLighting.prepare();
	}

	/**
	 * True when the effect selected for this material no longer matches what the
	 * reflections row decides.
	 *
	 * <p>Without this, toggling the row while the same material stays bound would
	 * leave the previous selection in place: the material-changed test would skip
	 * the very bind that has to change its mind. The two states are exact
	 * opposites - the surface is either drawn by the stock specular effect or by
	 * the lit program - so a disagreement means the bind has to run again.
	 */
	private boolean hdReflectionStale(int material, boolean modelBind) {
		if (!modelBind || material < 0 || super.materialProvider == null) {
			return false;
		}
		MaterialType type = super.materialProvider.getMetrics(material);
		if (type == null || type.aByte89 != HdReflection.SPECULAR_SLOT) {
			return false;
		}
		return hdReflectionOwns(type) == this.effectManager.isEffectActive();
	}

	/**
	 * Binds this frame's shadow map for a draw and returns it, or null when the
	 * row has no map. The map lives on its own unit so it cannot disturb a
	 * material bind.
	 */
	private ShadowBinding hdBindShadowMap() {
		@Pc(3) ShadowBinding shadow = hdShadowBinding();
		if (shadow != null) {
			this.hdBindShadowTexture(this.sunShadow.textureName());
		}
		return shadow;
	}

	/**
	 * Binds the sun shadow map on its sampler unit, through the per-unit cache.
	 *
	 * <p>The map is a raw texture name, not a {@code Class6}, so
	 * {@link #setActiveTexture(Class6)} cannot describe it. This records the name
	 * the cache would otherwise not know about, which is what makes
	 * {@link #hdReleaseShadowTexture()} able to clear the unit and what makes any
	 * later bind on this unit clear it too instead of silently inheriting it.
	 * The common case - the row off, no map - is two comparisons and no call.
	 */
	public void hdBindShadowTexture(@OriginalArg(0) int texture) {
		if (texture == 0) {
			return;
		}
		this.method1936(HdShaderSource.SAMPLER_UNIT_SHADOW);
		// Whatever the cache names here is not this draw's texture.
		this.setActiveTexture(null);
		OpenGL.glEnable(3553);
		OpenGL.glBindTexture(3553, texture);
		this.hdShadowTextureName = texture;
		this.method1936(0);
	}

	/**
	 * Leaves the shadow sampler unit empty and disabled, as the cache says it is.
	 *
	 * <p>Called at the end of every lighting draw, and on the paths where a draw
	 * was refused after the map had already been bound, so no frame can carry a
	 * depth map on that unit into the interface or the next pass.
	 */
	public void hdReleaseShadowTexture() {
		if (this.hdShadowTextureName == 0) {
			return;
		}
		this.method1936(HdShaderSource.SAMPLER_UNIT_SHADOW);
		OpenGL.glDisable(3553);
		OpenGL.glBindTexture(3553, 0);
		this.aClass6Array1[HdShaderSource.SAMPLER_UNIT_SHADOW] = null;
		this.hdShadowTextureName = 0;
		this.method1936(0);
	}

	/** The shadow map name bound on its sampler unit, or 0 when the unit is clear. */
	public int hdShadowTexture() {
		return this.hdShadowTextureName;
	}

	/**
	 * Empties every texture unit the toolkit owns, without asking the cache.
	 *
	 * <p>This is the one place that cannot go through
	 * {@link #setActiveTexture(Class6)}, because it is the code that establishes
	 * the state that cache describes from then on. The units arrive holding
	 * whatever the context setup left on them - the toolkit's own constructor
	 * textures among it - so a reset that reads the cache first is asking the
	 * thing it is supposed to define: with an empty cache every clear is a no-op
	 * and the residue survives into the first frame, where a plain
	 * {@code glEnable} is then enough to sample it.
	 *
	 * <p>Both targets on each unit are disabled and unbound, and the per-unit
	 * cache is nulled in the same pass, so afterwards every unit and its claim
	 * agree exactly. The texture-environment state is set in the same loop the
	 * stock code had it. Called once, from {@code prepareForRendering}.
	 */
	private void hdClearTextureUnits() {
		if (this.aClass6Array1 == null) {
			return;
		}
		// The unit count is the image-unit count: glActiveTexture addresses those,
		// and the array is sized to the highest sampler the 592HD programs name,
		// which can exceed the fixed-function combiner count.
		int units = this.aClass6Array1.length;
		if (this.anInt2285 > 0 && this.anInt2285 < units) {
			units = this.anInt2285;
		}
		for (int unit = 0; unit < units; unit++) {
			this.method1936(unit);
			OpenGL.glDisable(3553);
			OpenGL.glDisable(34067);
			OpenGL.glBindTexture(3553, 0);
			OpenGL.glBindTexture(34067, 0);
			this.aClass6Array1[unit] = null;
			OpenGL.glTexEnvi(8960, 8704, 34160);
		}
		// The shadow map is the one texture that is not a Class6; its unit is
		// cleared above like any other, so its name goes with it.
		this.hdShadowTextureName = 0;
		this.method1936(0);
	}

	/**
	 * This frame's half of the lighting state, as one value: the scene's live
	 * point lights, the shadow map the seam just bound, and the four rows the
	 * frame owns. Both seams read it, so a new frame-owned row is added here once
	 * instead of in every seam's argument list.
	 */
	private HdFrameState hdFrameState(ShadowBinding shadow) {
		return HdFrameState.of(this.lastSceneLightCount).withShadow(shadow)
			.withStrength(hdDayNight().shadow()).withAtmosphere(HdAtmosphere.current())
			.withLights(HdLightFrame.current()).withEnvironment(hdEnvironmentLight())
			.withWaterSeconds(HdWaterTime.seconds());
	}

	/**
	 * This frame's vegetation displacement block, or {@link HdSway#OFF}.
	 *
	 * <p>Both amplitudes come from the gate, the phase from the frame's wind
	 * clock, and the viewer from the frame's own camera: inverting the
	 * scene-to-eye map it already supplies is the only position this side can
	 * recover without reaching into game state. The allocation-free early-out
	 * keeps a 592HD-off or rows-off frame exactly as cheap as it was.
	 */
	private HdSway hdSway() {
		if (!HdSwayGate.requested() || this.aClass31_Sub1_4 == null) {
			return HdSway.OFF;
		}
		return HdSway.ofScene(HdSwayGate.foliageRequested(), HdSwayGate.grassRequested(),
			HdWindTime.seconds(), hdSceneToEye());
	}

	/**
	 * This frame's {@code rotX(180) * V}, rewritten on demand into one scratch
	 * array. Shared by the terrain seam and the vegetation displacement: both
	 * need the transform the modelview is built from, and {@code method5161}
	 * returns a scratch array of its own that the next matrix call overwrites.
	 */
	private float[] hdSceneToEye() {
		if (this.hdSceneToEye == null) {
			this.hdSceneToEye = new float[16];
		}
		GroundTileSpace.sceneToEye(this.aClass31_Sub1_4.method5161(), this.hdSceneToEye);
		return this.hdSceneToEye;
	}

	/**
	 * 592HD seam: per-pixel lighting for the terrain's tile window.
	 *
	 * <p>The ground pass loads an identity modelview and draws tile-space
	 * vertices under a tile-window ortho projection, so neither the scene nor the
	 * eye position can be read from {@code gl_Vertex} or the modelview. This
	 * supplies both:
	 *
	 * <ul>
	 * <li>tile-to-scene from {@link GroundTileSpace#tileToScene}, with the window
	 * origin the caller's own ortho subtracts;</li>
	 * <li>scene-to-eye from {@link GroundTileSpace#sceneToEye} over this frame's
	 * camera - {@code rotX(180) * V}, exactly the matrix {@link #method1952()}
	 * loads before a ground draw.</li>
	 * </ul>
	 *
	 * <p>Both are needed for the shadow map, which is defined in scene space: the
	 * ground has to report the same world position a model at that spot reports,
	 * or a caster's shadow stops at the model/ground seam. The stock path is
	 * untouched - nothing here runs, and no GL call is made, when the rows that
	 * use it are off.
	 *
	 * <p>There are two callers. The top-down map bake keeps an identity
	 * modelview and its own tile-window ortho, so it names the window it is
	 * drawing; that call is guarded and never binds.
	 * {@code GlGroundLayer.method1462} - the pass the scene submits every frame -
	 * draws the same buffer, already in scene units, under the camera's own
	 * matrices, so it passes the window origin 0 and this becomes the identity:
	 * the world position the shader reads is exactly the one GL rasterises.
	 *
	 * @param textured  whether the ground layer has a material texture
	 * @param tileUnits vertices per tile ({@code GlGround.anInt5261})
	 * @param originX   the window's first tile on the vertex buffer's x axis
	 * @param originZ   the window's first tile on the vertex buffer's z axis
	 * @param water     the water standing over the tiles this layer draws, which
	 *                  is the caller's own per-tile value: the stock 0..255 depth
	 *                  byte {@code GlGroundLayer} keeps (see {@link HdGroundWater})
	 */
	public boolean hdBeginGroundLighting(@OriginalArg(0) boolean textured,
			@OriginalArg(1) int tileUnits, @OriginalArg(2) int originX, @OriginalArg(3) int originZ,
			HdGroundWater water) {
		return hdBeginGroundLighting(textured, tileUnits, originX, originZ, water, -1, 1.0F, null);
	}

	/**
	 * 592HD seam: the same ground draw, with the layer's own material and its
	 * mesh's per-tile material map.
	 *
	 * <p>The material is what the stock draw selects a texture by; the atlas
	 * needs it for the same reason, and the map turns it into a page cell. The
	 * tile map is the mesh's, built when its layers were: it is what lets a
	 * fragment read the tile's second material without a second draw.
	 */
	public boolean hdBeginGroundLighting(@OriginalArg(0) boolean textured,
			@OriginalArg(1) int tileUnits, @OriginalArg(2) int originX, @OriginalArg(3) int originZ,
			HdGroundWater water, @OriginalArg(5) int material, @OriginalArg(6) float textureScale,
			@OriginalArg(7) OpenGl2DTexture tileMaterials) {
		if (!hdLightingAllowed() || tileUnits <= 0) {
			return false;
		}
		@Pc(6) ShadowBinding shadow = hdBindShadowMap();
		GroundTileSpace.tileToScene(this.hdTileToScene, tileUnits, originX, originZ);
		this.hdSceneToEye = hdSceneToEye();
		@Pc(8) HdDrawSpace space = HdDrawSpace.sceneSpace(this.hdTileToScene, this.hdSceneToEye);
		// No vegetation in the terrain pass: its tiles are not submitted as
		// plants, so the frame's displacement block stays OFF and this draw is
		// the same draw it was before the rows existed.
		@Pc(10) HdDrawRequest request = hdDrawRequest(textured, 0).withWater(water);
		@Pc(12) int pageMaterial = hdBindGroundTextures(request, material, textureScale, tileMaterials);
		@Pc(14) HdGroundTextures textures = HdGroundTextures.of(
			request.worldTextures() && request.materialMaps(), pageMaterial, textured, tileUnits,
			textureScale, HdTerrainBlend.of(request.terrainBlend()));
		@Pc(16) boolean bound = this.hdLighting.begin(request.withGroundTextures(textures), space,
			hdFrameState(shadow));
		if (!bound) {
			// The map was bound for a draw that did not happen.
			hdReleaseShadowTexture();
		}
		return bound;
	}

	/**
	 * Registers this layer's material on the page and binds the three ground
	 * textures, returning the material the shader may sample or -1 when the
	 * atlas cannot take this draw.
	 *
	 * <p>Nothing is built or bound unless both rows are on: a frame with them off
	 * makes no GL call here and the stock draw is untouched.
	 *
	 * <p>A material that is new to the page - or new to this frame, because the
	 * page filled and its cell went to a material drawn more recently - gets a
	 * stock draw for the frame it joins in. That is the same rule for both cases:
	 * the page that exists cannot hold it, and a material the page does not hold
	 * must never sample a cell that belongs to another material.
	 */
	private int hdBindGroundTextures(HdDrawRequest request, int material, float textureScale,
			OpenGl2DTexture tileMaterials) {
		if (!request.worldTextures() || !request.materialMaps() || material < 0 || super.materialProvider == null
			|| !super.materialProvider.textureAvailable(material)) {
			return -1;
		}
		int pageVersion = this.hdWorldPageVersion;
		int cell = this.hdMaterialMap.register(material, super.materialProvider.getMetrics(material),
			this.hdGroundArt);
		if (cell < 0 || tileMaterials == null) {
			return -1;
		}
		if (this.hdWorldPage == null || pageVersion != this.hdMaterialMap.version()) {
			// This material joined after the page was last built, so the page
			// that exists cannot hold it. Build the page now - before any draw
			// samples it - and draw the stock material texture for this frame:
			// a page is never sampled by the frame that built it, and a material
			// the page does not hold never reads an empty cell. The next draw
			// finds the page current and takes the atlas path.
			buildGroundPage();
			return -1;
		}
		this.method1936(HdShaderSource.SAMPLER_UNIT_WORLD_TEXTURES);
		this.setActiveTexture(this.hdWorldPage);
		this.method1936(HdShaderSource.SAMPLER_UNIT_MATERIAL_MAPS);
		this.setActiveTexture(this.hdMaterialIdMap);
		this.method1936(HdShaderSource.SAMPLER_UNIT_TILE_MATERIALS);
		this.setActiveTexture(tileMaterials);
		this.method1936(0);
		this.hdGroundUnitsBound = true;
		this.hdGroundAtlasBinds++;
		return material;
	}

	/**
	 * Uploads the world-texture page and the material map from the client's own
	 * material sampler.
	 *
	 * <p>The two textures are built while the material unit is current - the
	 * draw has already bound this layer's stock texture there - and an
	 * {@code OpenGl2DTexture} binds itself on construction. Their construction
	 * must therefore leave the material unit exactly as it found it: the stock
	 * path this frame samples that unit, and the toolkit's bind cache would
	 * otherwise claim a texture is bound that has been replaced.
	 */
	private void buildGroundPage() {
		@Pc(1) Class6 materialTexture = this.aClass6Array1[0];
		@Pc(2) OpenGl2DTexture retiredPage = this.hdWorldPage;
		@Pc(3) OpenGl2DTexture retiredMap = this.hdMaterialIdMap;
		this.hdWorldPage = new OpenGl2DTexture(this, 3553, 6408, HdWorldPage.size(), HdWorldPage.size(),
			false, HdWorldPage.pixels(this.hdMaterialMap, super.materialProvider), false);
		this.hdMaterialIdMap = new OpenGl2DTexture(this, 3553, 6408, HdMaterialMap.WIDTH, 1, false,
			this.hdMaterialMap.texels(), 6408, false);
		this.hdWorldPageVersion = this.hdMaterialMap.version();
		this.hdGroundPageBuilds++;
		this.method1936(0);
		this.setActiveTexture(materialTexture);
		// A rebuild replaces both textures. Everything the atlas owns is small
		// except the page, and a session that streams a material per frame would
		// leave one page per registration alive until finalization runs - the
		// driver keeps the memory, and the toolkit's accounting keeps counting
		// it. Release the retires now, and forget the bindings to them so a
		// later bind cannot be skipped against a deleted object.
		if (retiredPage != null) {
			this.aClass6Array1[HdShaderSource.SAMPLER_UNIT_WORLD_TEXTURES] = null;
			retiredPage.method4183();
			this.hdGroundPageDisposals++;
		}
		if (retiredMap != null) {
			this.aClass6Array1[HdShaderSource.SAMPLER_UNIT_MATERIAL_MAPS] = null;
			retiredMap.method4183();
		}
	}

	/**
	 * True when the page build would copy this material's art into a cell.
	 *
	 * <p>One call, no format knowledge: the toolkit asks the page's own owner what
	 * art this material has and whether there is a cell's worth of it, and it is
	 * that answer the map publishes. Restating the RGB-versus-alpha rule here is
	 * how the page and the map came to disagree about an opaque material's alpha.
	 */
	private boolean hdGroundArtAvailable(int material) {
		return HdMaterialArt.art(super.materialProvider, material) != null;
	}

	/** How many ground draws have bound the world-texture atlas. */
	public long hdGroundAtlasBinds() {
		return this.hdGroundAtlasBinds;
	}

	/** How many times a page (and its material map) has been uploaded. */
	public long hdGroundPageBuilds() {
		return this.hdGroundPageBuilds;
	}

	/** How many retired pages have been released to the driver. */
	public long hdGroundPageDisposals() {
		return this.hdGroundPageDisposals;
	}

	/**
	 * 592HD: one world frame of the atlas' staleness clock.
	 *
	 * <p>Called once per outermost world draw, before the scene submits any
	 * ground. Every material the frame draws is stamped with the new generation,
	 * so the cells that get recycled when the page fills are the ones belonging to
	 * materials this frame did not draw - the ones no longer in view. A nested
	 * world draw (the reflective water effect's reflection) does not call it: it
	 * redraws the same view and must not age the set.
	 */
	public void hdGroundFrameBegin() {
		this.hdMaterialMap.beginFrame();
	}

	/** How many page cells have been recycled for a newer material. */
	public int hdGroundAtlasEvictions() {
		return this.hdMaterialMap.evictions();
	}

	/**
	 * How many atlas textures exist right now: the page and the id map, one
	 * pair per build, one pair retired per build after the first. It is 2 for
	 * the life of a session however many materials arrive - a total that grows
	 * with the streaming world would mean a retired page is never released.
	 */
	public int hdGroundAtlasTextures() {
		return (int) (2L * (this.hdGroundPageBuilds - this.hdGroundPageDisposals));
	}

	/**
	 * Builds the mesh's per-tile material map texture; one per mesh, on first draw.
	 *
	 * <p>Like the page, this texture binds itself while being built, and the mesh
	 * is built from inside the ground draw after the layer's material texture is
	 * bound. The material unit is restored so the first frame that builds the
	 * tile map does not draw with the map in place of the material.
	 */
	public OpenGl2DTexture hdGroundTileMaterials(HdTileMaterialMap materials) {
		if (materials == null) {
			return null;
		}
		@Pc(1) Class6 materialTexture = this.aClass6Array1[0];
		@Pc(2) OpenGl2DTexture texture = new OpenGl2DTexture(this, 3553, 6408, HdTileMaterialMap.SIZE,
			HdTileMaterialMap.SIZE, false, materials.bytes(), 6408, false);
		this.method1936(0);
		this.setActiveTexture(materialTexture);
		return texture;
	}

	/**
	 * This frame's environment light, read from the state the stock renderer
	 * already holds: the sky/fog colour {@code UA} stored, the level ambient
	 * {@code B} stored, and the camera the scene placed this frame.
	 *
	 * <p>Nothing here is a new source. A region's environment reaches these
	 * fields through {@code Class10.method127} ({@code B} and {@code scaleHeight})
	 * and {@code FixedFunctionSpecularPass.method4231} ({@code UA}), so the
	 * snapshot moves with the map the player is standing in and with the hour,
	 * exactly as the stock lights do.
	 *
	 * <p>The eye-space up axis is {@code rotX(180) * view}'s image of the world's
	 * y axis: the scene loads that flip before the camera for models and terrain
	 * alike, so one direction serves both passes. {@code method5161} hands back a
	 * shared scratch array, so the three components are read immediately.
	 */
	private HdEnvironmentLight hdEnvironmentLight() {
		if (!HdEnvironmentLight.requested() || this.aClass31_Sub1_4 == null) {
			return HdEnvironmentLight.OFF;
		}
		@Pc(3) float[] view = this.aClass31_Sub1_4.method5161();
		return HdEnvironmentLight.of(this.anInt2302, this.sunBrightness, view[4], -view[5], -view[6]);
	}

	/**
	 * This frame's shadow binding, or null when the row is off, the map was never
	 * built, or the map has no pass yet. Null is the pre-shadow path: the
	 * lighting shader then samples nothing and draws exactly as it did before.
	 */
	private ShadowBinding hdShadowBinding() {
		@Pc(3) SunShadowMap map = this.sunShadow;
		if (map == null || !map.hasFrame() || !SunShadowGate.requested()) {
			return null;
		}
		return map.binding(SunShadowGate.pcfRadius(HdSettings.value(HdFeature.LIGHTING_DETAIL)));
	}

	/**
	 * 592HD seam: rebuilds the sun's shadow map for this frame and leaves the
	 * context inside the depth pass. The caller draws the casters with the
	 * toolkit's ordinary model calls - colour writes are off and the light's clip
	 * matrix is loaded - then calls {@link #hdSunShadowEnd()}. Returns false when
	 * the Sun shadows row is off or the target could not be created, in which
	 * case nothing was changed and the caller must not draw into it.
	 *
	 * <p>The volume is explicit: the caller names the centre and half-extent, so
	 * the scene decides how much of the world the map covers rather than this
	 * method guessing from the camera's far plane (a 3584-unit volume at 2048
	 * would be 1.75 units per texel).
	 */
	public boolean hdSunShadowBegin(@OriginalArg(0) float centreX, @OriginalArg(1) float centreY,
			@OriginalArg(2) float centreZ, @OriginalArg(3) float radius) {
		if (!SunShadowGate.requested()) {
			return false;
		}
		if (this.sunShadow == null) {
			this.sunShadow = new SunShadowMap();
		}
		if (!this.sunShadow.prepare(SunShadowGate.resolution(HdSettings.value(HdFeature.SHADOW_MAP)))) {
			return false;
		}
		return this.sunShadow.beginPass(this.sunDirection[0], this.sunDirection[1], this.sunDirection[2],
			centreX, centreY, centreZ, radius);
	}

	/** 592HD seam: ends the depth pass and restores every piece of GL state it touched. */
	public void hdSunShadowEnd() {
		if (this.sunShadow != null) {
			this.sunShadow.endPass();
		}
		// The pass restored the scene's texture state, but the casters' last
		// material is still in the toolkit's cache. Forget it, or the next
		// scene draw of that material skips the rebind and samples whatever
		// the pass left behind.
		this.hdShadowPassEnded();
	}

	/** 592HD seam: the shadow target, or null before the first pass. */
	public SunShadowMap hdSunShadow() {
		return this.sunShadow;
	}

	/**
	 * 592HD seam: one frame's sun shadow work, exactly as the scene renderer
	 * calls it - before the world is drawn for that frame.
	 *
	 * <p>This is the whole integration: the renderer supplies the sun (the
	 * toolkit already has it), the centre and half-extent of the volume the map
	 * must cover, and a callback that draws the casters with its ordinary model
	 * and ground calls. Everything else - the row, the resolution, the target,
	 * the state sandwich, the counters - is this side's.
	 *
	 * <p>Returns false, having touched no GL state, when 592HD, per-pixel
	 * lighting or the Sun shadows row is off, or when the target could not be
	 * created. The casters are never called in that case, so the stock draw sees
	 * exactly the frame it saw before this stage existed.
	 */
	public boolean hdSunShadowFrame(@OriginalArg(0) float centreX, @OriginalArg(1) float centreY,
			@OriginalArg(2) float centreZ, @OriginalArg(3) float radius,
			@OriginalArg(4) SunShadowFrame.Casters casters) {
		if (this.sunShadow == null && SunShadowGate.requested()) {
			this.sunShadow = new SunShadowMap();
		}
		boolean ran = this.sunShadowFrame.render(this.sunShadow, casters, HdSettings.isEnabled(),
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0, HdSettings.value(HdFeature.SHADOW_MAP),
			this.sunDirection[0], this.sunDirection[1], this.sunDirection[2],
			centreX, centreY, centreZ, radius);
		if (ran) {
			this.hdShadowPassEnded();
		}
		return ran;
	}

	/**
	 * 592HD seam: the same frame, with the scene's casters instead of a callback.
	 *
	 * <p>Each caster's model matrix is applied with {@code glPushMatrix} +
	 * {@code glMultMatrixf} around its own draw and popped straight after, so the
	 * pass's modelview is the identity plus one object at a time and never the
	 * world's camera. That is the whole of per-object placement: a model that
	 * draws in its own local space lands where the renderer said it sits, and a
	 * model whose draw composes its own matrix is registered with
	 * {@link ShadowCaster#of(ShadowCaster.Draw)} and left alone.
	 *
	 * <p>The set is walked only from inside the pass, so with 592HD, per-pixel
	 * lighting or the Sun shadows row off no caster is touched and no GL call is
	 * issued - not even the matrix push.
	 */
	public boolean hdSunShadowFrame(@OriginalArg(0) float centreX, @OriginalArg(1) float centreY,
			@OriginalArg(2) float centreZ, @OriginalArg(3) float radius,
			@OriginalArg(4) ShadowCasterSet casters) {
		if (this.sunShadow == null && SunShadowGate.requested()) {
			this.sunShadow = new SunShadowMap();
		}
		this.hdShadowCasters = 0L;
		this.hdShadowMaterialBinds = 0L;
		boolean ran;
		if (casters == null) {
			ran = this.sunShadowFrame.render(this.sunShadow, null, HdSettings.isEnabled(),
				HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0,
				HdSettings.value(HdFeature.SHADOW_MAP), this.sunDirection[0], this.sunDirection[1],
				this.sunDirection[2], centreX, centreY, centreZ, radius);
		} else {
			ran = this.sunShadowFrame.render(this.sunShadow, new SunShadowFrame.Casters() {

				@Override
				public void drawCasters() {
					GlToolkit.this.hdDrawShadowCasters(casters);
				}
			}, HdSettings.isEnabled(), HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0,
				HdSettings.value(HdFeature.SHADOW_MAP), this.sunDirection[0], this.sunDirection[1],
				this.sunDirection[2], centreX, centreY, centreZ, radius);
		}
		if (ran) {
			this.hdShadowPassEnded();
		}
		return ran;
	}

	/**
	 * Puts the toolkit's caches back in charge after the sun's depth pass.
	 *
	 * <p>The pass is the one draw that owns the write masks: it turns depth
	 * writes on and colour writes off, and restores them by hand on the way out
	 * because the {@code jaggl} wrapper has no read-back for either. Nothing in
	 * that restore tells this toolkit, so its cache could still claim the
	 * frame's "no depth writes" - and the next draw that asks for exactly that
	 * value ({@code method1911(false)}, which every 2D pass asks for) issues no
	 * GL call and inherits the pass's mask for the rest of the frame.
	 *
	 * <p>So the caches are made true again here rather than at each draw: the
	 * depth mask is re-asserted from {@code aBoolean200} through the same method
	 * that owns it, and the colour mask the pass forced on is re-asserted when
	 * the cache has claimed it. The material the pass bound last is forgotten
	 * for the same reason: the pass restored the scene's texture state, so the
	 * cache no longer describes that unit.
	 */
	private void hdShadowPassEnded() {
		this.anInt2314 = -1;
		this.method1976();
		if (this.aBoolean195) {
			OpenGL.glColorMask(true, true, true, true);
		}
	}

	/** Draws every caster into the open depth pass, one matrix push each. */
	private void hdDrawShadowCasters(@OriginalArg(0) ShadowCasterSet casters) {
		for (int index = 0; index < casters.size(); index++) {
			@Pc(5) ShadowCaster caster = casters.get(index);
			caster.modelToScene(this.hdShadowMatrix);
			OpenGL.glPushMatrix();
			OpenGL.glMultMatrixf(this.hdShadowMatrix, 0);
			caster.draw().draw();
			OpenGL.glPopMatrix();
			this.hdShadowCasters++;
		}
	}

	/** 592HD seam: casters submitted by the most recent sun shadow frame call. */
	public long hdShadowCastersSubmitted() {
		return this.hdShadowCasters;
	}

	/** 592HD seam: the scene renderer's per-frame caster submission. */
	public SunShadowSubmission hdShadowSubmission() {
		return this.hdShadowSubmission;
	}

	/**
	 * 592HD seam: the world draw is starting, so every model it submits is a
	 * sun-shadow caster for the next pass.
	 *
	 * <p>Off when 592HD, per-pixel lighting or the Sun shadows row is off, so
	 * the draw path records nothing and touches no GL. Paired with
	 * {@link #hdShadowSceneEnd()}; a frame that records and never ends simply
	 * keeps recording, which the cap in the submission bounds.
	 */
	public void hdShadowSceneBegin() {
		this.hdShadowCollecting = SunShadowGate.requested();
	}

	/** 592HD seam: the world draw has finished; stop recording casters. */
	public void hdShadowSceneEnd() {
		this.hdShadowCollecting = false;
	}

	/**
	 * 592HD seam: records one model the world draw is about to draw, with the
	 * placement matrix the draw uses.
	 *
	 * <p>Called from the scene's model path, which may run on a renderer
	 * thread; the submission takes the lock and no GL is touched here. A model
	 * drawn more than once in a frame is recorded more than once - the pass is a
	 * depth test, so a duplicate is only cost, never a wrong pixel.
	 */
	public void hdRecordShadowCaster(@OriginalArg(0) GlModel model, @OriginalArg(1) JavaMatrix modelToScene) {
		if (!this.hdShadowCollecting || model == null || modelToScene == null) {
			return;
		}
		if (this.sunShadow != null && this.sunShadow.inPass()) {
			return;
		}
		this.hdShadowSubmission.recordModel(model, modelToScene.method5161());
	}

	/**
	 * 592HD seam: the fixed-function state a caster needs inside the depth pass.
	 *
	 * <p>Only positions matter there, so this drops the lit program and the
	 * material texture. It deliberately does not touch the matrices or the
	 * viewport - the pass owns those, and {@code method1977} would reset them.
	 * The pass's own attribute push restores the enables on the way out.
	 */
	public void hdBeginShadowCasterDraw() {
		this.hdLighting.unbind();
		this.setActiveTexture(null);
		// The screen draw's material cache still names the last material it
		// bound, but the unit was just cleared. Forgetting it makes a cut-out
		// caster's first group bind again instead of drawing with no texture at
		// all, and keeps the next scene draw from skipping a rebind that the
		// cleared unit now needs.
		this.anInt2314 = -1;
	}

	/** True while the open sun pass is alpha-testing casters. */
	public boolean hdShadowCutoutActive() {
		return this.sunShadow != null && this.sunShadow.cutoutActive();
	}

	/**
	 * Binds one face group's material for a cut-out caster, or unbinds when
	 * {@code material} is negative.
	 *
	 * <p>This is the screen draw's own material path, so the sampled texture, its
	 * alpha channel and its combine mode are the ones the player sees. It is only
	 * reached from a cut-out pass: with the row off the depth draw never binds a
	 * texture, which is what keeps the solid path at its old cost.
	 */
	public void hdBindShadowCasterMaterial(@OriginalArg(0) int material) {
		if (material >= 0 && this.anInt2314 != material) {
			this.hdShadowMaterialBinds++;
		}
		this.method1938(material, false);
	}

	/** Material textures bound by the last frame's casters. */
	public long hdShadowMaterialBinds() {
		return this.hdShadowMaterialBinds;
	}

	/** 592HD seam: the frame driver, so its counters can be read in a test. */
	public SunShadowFrame hdSunShadowDriver() {
		return this.sunShadowFrame;
	}

	/** 592HD seam: how many primitives the lighting program has drawn. */
	public long hdLightingBinds() {
		return this.hdLighting.binds();
	}

	/**
	 * 592HD seam: how many caustics uploads the lighting program has issued. Zero
	 * across a frame with the row off (or with no water over the tile) is the
	 * proof that the off state makes no GL call at all.
	 */
	public long hdCausticsUploads() {
		return this.hdLighting.causticsUploads();
	}

	/** How many reflection uploads the lighting program has issued; zero means the row never drew. */
	public long hdReflectionUploads() {
		return this.hdLighting.reflectionUploads();
	}

	/**
	 * 592HD seam: how many vegetation primitives were submitted for displacement
	 * - a plant drawn while at least one sway row is on. Zero says the rig never
	 * reached the rows; non-zero with no visible motion says the displacement
	 * itself is the problem, which is the distinction a pixel proof needs.
	 */
	public long hdSwayBinds() {
		return this.hdSwayBinds;
	}

	/**
	 * 592HD seam: the counters that say whether the shadow and lighting paths
	 * are actually running this session. One line, for the console or the log.
	 */
	public String hdLightingStats() {
		// recorded is the world draw's own number: it says the scene fed the
		// submission, which drawn alone cannot distinguish from a stale map.
		return this.sunShadowFrame.stats() + "; sun casters drawn=" + this.hdShadowCasters
			+ "; recorded=" + this.hdShadowSubmission.recorded()
			+ "; model binds=" + this.hdLighting.binds();
	}

	/** 592HD seam: returns the context to fixed function after the draw. */
	public void hdEndModelLighting() {
		hdReleaseShadowTexture();
		if (this.hdReflectionCubeBound) {
			hdUnbindReflectionCube();
		}
		this.hdLighting.unbind();
		hdReleaseGroundTextures();
	}

	/**
	 * Leaves the three ground units empty and disabled, the way the stock frame
	 * has them.
	 *
	 * <p>A ground draw binds the page, the material map and the tile map on
	 * their own units. Before this it also left them there once the draw ended,
	 * so the frame carried four textures no stock pass ever binds - the interface
	 * that follows and every later pass ran beside a bound world page. Nothing
	 * sampled them, but they are state the frame does not have without 592HD, and
	 * a draw that enables a 2D target on one of those units without binding
	 * inherits whatever is on it. Released with the shadow map, at the end of the
	 * draw that bound them, and only when that draw bound them: a model draw pays
	 * one flag test and no GL call.
	 */
	private void hdReleaseGroundTextures() {
		if (!this.hdGroundUnitsBound) {
			return;
		}
		for (int unit = HdShaderSource.SAMPLER_UNIT_WORLD_TEXTURES;
				unit <= HdShaderSource.SAMPLER_UNIT_TILE_MATERIALS; unit++) {
			this.method1936(unit);
			this.setActiveTexture(null);
		}
		this.method1936(0);
		this.hdGroundUnitsBound = false;
	}

	/** Leaves the reflection unit the way the frame found it. */
	private void hdUnbindReflectionCube() {
		this.method1936(HdShaderSource.SAMPLER_UNIT_REFLECTION);
		this.setActiveTexture(null);
		this.method1936(0);
		this.hdReflectionCubeBound = false;
	}

	/** 592HD seam: true when the lighting program linked on this context. */
	public boolean hdLightingAvailable() {
		return this.hdLighting.available();
	}

	/**
	 * 592HD seam: starts an HD water draw. Called from {@link #AA} where the
	 * stock water effect would have been selected.
	 *
	 * <p>The region itself has no material, so it starts as calm water; the
	 * material bind that follows asks for the type its effect slot names through
	 * {@link #hdWaterMaterial(int)}. Returns false when HD water is off,
	 * unavailable, or the row is off, in which case the caller runs the stock
	 * selection untouched.
	 */
	public boolean hdBeginWater() {
		boolean bound = this.hdWater.begin(HdWaterRequest.still(HdWaterGate.requested(),
			HdWaterGate.custom(), HdWaterTime.seconds()));
		if (bound && !this.hdWaterReported) {
			this.hdWaterReported = true;
			System.out.println("[592HD] " + hdWaterStats());
		}
		return bound;
	}

	/**
	 * 592HD seam: a water material is bound for the face groups that follow.
	 *
	 * <p>This is where the water type is decided. A material whose stock effect
	 * slot is {@link HdWaterGate#REFLECTIVE_SLOT} is the reflective water type,
	 * the one the stock manager silently downgrades because that effect reports
	 * itself unsupported; when the toolkit also has the environment cube the
	 * stock cube effects sample, the draw switches to the program that reflects
	 * it. A material whose slot is {@link HdWaterGate#BREAKING_SLOT} is the
	 * breaking water type: the same cube, and the surface is driven by the
	 * material's own two effect-parameter fields, decoded by
	 * {@link HdWaterBreaking} exactly as the stock effect decodes them.
	 * Everything else stays on the calm program, which is byte-identical to the
	 * draw before this pass.
	 *
	 * <p>Nothing happens while no HD water draw is open, so a stray call from
	 * another material bind cannot reach GL.
	 */
	public void hdWaterMaterial(int material) {
		if (!this.hdWater.bound()) {
			return;
		}
		MaterialType type = material < 0 || super.materialProvider == null ? null
			: super.materialProvider.getMetrics(material);
		boolean reflective = type != null && HdWaterGate.isReflectiveMaterial(type.aByte89);
		boolean breaking = type != null && HdWaterGate.isBreakingMaterial(type.aByte89);
		GlCubeTexture environment = type != null && HdWaterGate.samplesEnvironment(type.aByte89)
			? this.method1946() : null;
		if (environment != null) {
			// Unit 1, where the stock cube effects put their environment map.
			this.method1936(HdWaterSource.ENVIRONMENT_UNIT);
			this.setActiveTexture(environment);
			this.method1936(0);
			this.hdWaterCubeBound = true;
		}
		HdWaterRequest request = HdWaterRequest.current(HdWaterGate.requested(), HdWaterGate.custom(), reflective,
			environment != null, HdWaterTime.seconds());
		if (breaking) {
			request = request.withBreaking(HdWaterBreaking.of(type.aByte88, type.anInt6933));
		}
		this.hdWater.select(request);
	}

	/** True while HD water owns the program, so nothing else may bind over it. */
	public boolean hdWaterActive() {
		return this.hdWater.bound();
	}

	/**
	 * 592HD seam: ends an HD water draw and returns to fixed function, and
	 * leaves the environment unit the way it found it - the stock effects bind
	 * their own cube there and must not inherit a stale reflection.
	 */
	public void hdEndWater() {
		if (this.hdWaterCubeBound) {
			this.method1936(HdWaterSource.ENVIRONMENT_UNIT);
			this.setActiveTexture(null);
			this.method1936(0);
			this.hdWaterCubeBound = false;
		}
		this.hdWater.unbind();
	}

	/** How many water draws have used the HD program. */
	public long hdWaterBinds() {
		return this.hdWater.binds();
	}

	/** How many of those draws ran the custom surface. */
	public long hdWaterCustomBinds() {
		return this.hdWater.customBinds();
	}

	/** How many of those draws sampled the environment cube. */
	public long hdWaterReflectiveBinds() {
		return this.hdWater.reflectiveBinds();
	}

	/** How many of those draws ran the breaking water type's surface. */
	public long hdWaterBreakingBinds() {
		return this.hdWater.breakingBinds();
	}

	/** Test hook: the wave amplitude, so a flat surface can be rendered. */
	public void hdWaterFlowForTest(float amplitude) {
		this.hdWater.setFlowForTest(amplitude);
	}

	/** One line: does the water stage compile and run on this context. */
	public String hdWaterStats() {
		return "hd water: available=" + this.hdWater.available()
			+ " reflective=" + this.hdWater.reflectiveAvailable() + " draws=" + this.hdWater.binds()
			+ " reflectiveDraws=" + this.hdWater.reflectiveBinds()
			+ (this.hdWater.logLine().isEmpty() ? "" : " [" + this.hdWater.logLine() + "]");
	}

	/**
	 * 592HD seam: the sky dome for one frame, drawn before any world geometry.
	 *
	 * <p>This client has no sky draw at all, so this is the pass itself rather
	 * than a hook into one: the scene renderer calls it once per frame with the
	 * 3D viewport current and the camera already placed (the same camera
	 * {@link #method1952()} builds the modelview from), then draws the world over
	 * it. The dome writes no depth, so it is a background: geometry covers it
	 * exactly as it would cover the clear colour.
	 *
	 * <p>Everything the pass samples is state this toolkit already holds - the
	 * sun direction and colour {@link #scaleHeight} last stored, the fog colour
	 * {@link #UA} last stored, this frame's projection, and the camera - so the
	 * sky cannot drift from the light the world is drawn with. The colours
	 * themselves are {@link HdSkyColours}'s, with the day/night clock from
	 * {@link HdSkyTime}.
	 *
	 * <p>Returns false, having made no GL call, when 592HD or the Sky row is off,
	 * during the sun's depth pass, while HD water owns the program, or before a
	 * camera exists: the stock frame is then exactly the frame it always was.
	 */
	public boolean hdSkyFrame() {
		if (!SkyGate.skyRequested()) {
			return false;
		}
		if (this.aClass31_Sub1_4 == null) {
			return false;
		}
		// Inside the sun's depth pass only depth matters, and the water pass
		// owns the program while it draws.
		if (this.sunShadow != null && this.sunShadow.inPass()) {
			return false;
		}
		if (this.hdWater.bound()) {
			return false;
		}
		return this.hdSky.draw(this.projectionMatrix, this.aClass31_Sub1_4.method5161(), hdSkyColours(),
			this.sunDirection);
	}

	/**
	 * This frame's day/night factors: the one place the toolkit asks the hour
	 * what it means for the light.
	 *
	 * <p>Off - 592HD disabled or the Day and night row off - is
	 * {@link HdDayNight#IDENTITY}, so every caller multiplies by 1.0F and the
	 * stock frame is bit-identical rather than merely close.
	 *
	 * <p>The sun's elevation comes from the direction the lighting is already
	 * using, negated because this world's y axis points down; the clock supplies
	 * the phase. Neither the direction nor any matrix is ever written from the
	 * clock here - only colours and strengths are.
	 */
	private HdDayNight.Factors hdDayNight() {
		return hdDayNight(this.sunDirection[0], this.sunDirection[1], this.sunDirection[2]);
	}

	/**
	 * The same factors for a sun the caller is about to store.
	 *
	 * <p>{@code scaleHeight} uses this rather than the stored direction: the
	 * colour it writes has to match the direction it writes in the same call, or
	 * one frame's stale elevation decides the other's light.
	 */
	private HdDayNight.Factors hdDayNight(float sunX, float sunY, float sunZ) {
		@Pc(2) float length = (float) Math.sqrt((double) (sunX * sunX + sunY * sunY + sunZ * sunZ));
		// A nil direction is "the scene has not set a sun yet", which is a day
		// frame: reading it as a sun on the horizon would dim the first frame of
		// every session.
		@Pc(4) float elevation = length <= 1.0e-6F ? 1.0F : -sunY / length;
		return HdDayNight.factors(HdSettings.isEnabled(), SkyGate.dayNightRequested(), elevation,
			HdSkyTime.phase());
	}

	/**
	 * The four colours this frame's sky is made of, or null when the rows leave no
	 * sky. Public so a test can predict a pixel from the same state the pass
	 * uploads, instead of from a recorded frame.
	 */
	public HdSkyColours hdSkyColours() {
		if (!SkyGate.skyRequested()) {
			return null;
		}
		// Both colours are the effective ones - scaleHeight and UA stored them
		// already dimmed for this frame - so the dome inherits the world's level
		// instead of computing a second one.
		@Pc(4) int fogColour = this.anInt2302 < 0 ? 0 : this.anInt2302;
		@Pc(6) int sunColour = this.sunColorRgb < 0 ? 0xFFFFFF : this.sunColorRgb;
		// This world's y axis points down, so the sun's height above the horizon
		// is the negated y of the direction the lighting is using.
		@Pc(8) float sunElevation = -this.sunDirection[1];
		return HdSkyColours.resolve(HdSettings.isEnabled(), HdSettings.value(HdFeature.SKY) != 0,
			SkyGate.dayNightRequested(), sunElevation, HdSkyTime.phase(), sunColour, fogColour);
	}

	/**
	 * This frame's projection, as the scene's passes see it. A pass that casts
	 * rays per pixel needs the same matrix the geometry was clipped by; a copy,
	 * so a caller cannot rewrite the scene's projection.
	 */
	public float[] hdProjection() {
		return this.projectionMatrix.clone();
	}

	/**
	 * 592HD seam: the client is starting a top-down map bake. Paired with
	 * {@link #hdMapBakeEnd()} around the bake's own draw calls; the per-pixel
	 * lighting seam declines while it is set, and nothing else changes.
	 */
	public void hdMapBakeBegin() {
		this.hdMapBake = true;
	}

	/** 592HD seam: the map bake is over; the scene's seam is live again. */
	public void hdMapBakeEnd() {
		this.hdMapBake = false;
	}

	/** How many skies the pass has drawn; zero means the seam is dead. */
	public long hdSkyDraws() {
		return this.hdSky.draws();
	}

	/** One line: does the sky stage compile and run on this context. */
	public String hdSkyStats() {
		return "hd sky: available=" + this.hdSky.available() + " draws=" + this.hdSky.draws()
			+ (this.hdSky.logLine().isEmpty() ? "" : " [" + this.hdSky.logLine() + "]");
	}

	/**
	 * Whether the stock path is drawing this frame with fog enabled, using the
	 * same rule that enables {@code GL_FOG} in {@link #method1968()}. The 592HD
	 * gate needs it because the lighting shader always applies the fog band.
	 */
	private boolean fogActive() {
		return this.aBoolean197 && this.anInt2315 >= 0 | this.aBoolean192;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZI)V")
	public void method1906(@OriginalArg(0) boolean arg0) {
		if (this.aBoolean212 != arg0) {
			this.aBoolean212 = arg0;
			this.method1919();
			this.anInt2320 &= 0xFFFFFFF8;
		}
	}

	@OriginalMember(owner = "client!fd", name = "l", descriptor = "(I)V")
	private void method1907() {
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadMatrixf(this.projectionMatrix, 0);
		OpenGL.glMatrixMode(5888);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IB)V")
	public synchronized void method1908(@OriginalArg(0) int arg0) {
		@Pc(13) Country local13 = new Country();
		local13.aLong224 = arg0;
		this.arbProgramsAwaitingDeallocation.method4137(local13);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(B)V")
	private void method1909() {
		if (this.aCanvas2 == null) {
			this.anInt2214 = this.anInt2106 = 0;
		} else {
			@Pc(14) Dimension local14 = this.aCanvas2.getSize();
			this.anInt2106 = local14.height;
			this.anInt2214 = local14.width;
		}
		if (this.anInterface3_1 == null) {
			this.anInt2138 = this.anInt2106;
			this.anInt2240 = this.anInt2214;
			this.method1950();
		}
		this.method1898();
		this.e();
	}

	@OriginalMember(owner = "client!fd", name = "O", descriptor = "(IIIIII)V")
	@Override
	public void O(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int colour, @OriginalArg(5) int blendMode) {
		@Pc(8) float local8 = (float) x + 0.35F;
		@Pc(13) float local13 = (float) y + 0.35F;
		@Pc(18) float local18 = (float) width + local8;
		this.method1977();
		@Pc(26) float local26 = (float) height + local13;
		this.method1958(blendMode);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		if (this.arbMultisample) {
			OpenGL.glDisable(32925);
		}
		OpenGL.glBegin(7);
		OpenGL.glVertex2f(local8, local13);
		OpenGL.glVertex2f(local8, local26);
		OpenGL.glVertex2f(local18, local26);
		OpenGL.glVertex2f(local18, local13);
		OpenGL.glEnd();
		if (this.arbMultisample) {
			OpenGL.glEnable(32925);
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(III)V")
	public synchronized void method1910(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		@Pc(8) Class4_Sub37 local8 = new Class4_Sub37(arg0);
		local8.aLong224 = arg1;
		this.aClass183_11.method4137(local8);
	}

	@OriginalMember(owner = "client!fd", name = "x", descriptor = "()Z")
	@Override
	public boolean method4286() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "(I)V")
	@Override
	public void allocateThreads(@OriginalArg(0) int threadCount) {
		if (threadCount != 1) {
			throw new IllegalArgumentException("");
		}
	}

	@OriginalMember(owner = "client!fd", name = "B", descriptor = "()Z")
	@Override
	public boolean method4300() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IZ)V")
	public void method1911(@OriginalArg(1) boolean arg0) {
		if (this.aBoolean200 != arg0) {
			this.aBoolean200 = arg0;
			this.method1976();
			this.anInt2320 &= 0xFFFFFFE0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIII)V")
	@Override
	public void method4294(@OriginalArg(0) int arg0, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int height) {
		this.framebufferCompositor.prepareForRendering(arg0, arg2, y, height);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIZ)V")
	public void method1912(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		if (this.anInt2301 != 0) {
			OpenGL.glTexEnvi(8960, 34161, arg0);
			OpenGL.glTexEnvi(8960, 34162, arg1);
			return;
		}
		@Pc(17) boolean local17 = false;
		if (this.anInt2299 != arg0) {
			OpenGL.glTexEnvi(8960, 34161, arg0);
			this.anInt2299 = arg0;
			local17 = true;
		}
		if (arg1 != this.anInt2305) {
			OpenGL.glTexEnvi(8960, 34162, arg1);
			local17 = true;
			this.anInt2305 = arg1;
		}
		if (local17) {
			this.anInt2320 &= 0xFFFFFFE2;
		}
	}

	@OriginalMember(owner = "client!fd", name = "NA", descriptor = "(IIIII)V")
	@Override
	protected void NA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int radius, @OriginalArg(3) int colour, @OriginalArg(4) int blendMode) {
		throw new IllegalStateException("Circle fill is not supported by the gl toolkit");
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZFFFF)V")
	public void method1913(@OriginalArg(1) float arg0, @OriginalArg(2) float arg1, @OriginalArg(3) float arg2, @OriginalArg(4) float arg3) {
		Class4_Sub41.aFloatArray30[2] = arg2;
		Class4_Sub41.aFloatArray30[1] = arg0;
		Class4_Sub41.aFloatArray30[3] = arg1;
		Class4_Sub41.aFloatArray30[0] = arg3;
		OpenGL.glTexEnvfv(8960, 8705, Class4_Sub41.aFloatArray30, 0);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!jq;IIII)Lclient!t;")
	@Override
	public Model createModel(@OriginalArg(0) Mesh mesh, @OriginalArg(1) int functionMask, @OriginalArg(2) int featureMask, @OriginalArg(3) int ambient, @OriginalArg(4) int contrast) {
		return new GlModel(this, mesh, functionMask, ambient, contrast, featureMask);
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(B)V")
	public void method1914() {
		OpenGL.glLightfv(16384, 4611, this.sunDirection, 0);
		OpenGL.glLightfv(16385, 4611, this.lightingDirection, 0);
	}

	@OriginalMember(owner = "client!fd", name = "p", descriptor = "()Z")
	@Override
	public boolean method4261() {
		return this.effectManager.method5317();
	}

	@OriginalMember(owner = "client!fd", name = "ea", descriptor = "(Lclient!c;)V")
	@Override
	public void ea(@OriginalArg(0) Ground arg0) {
		this.aClass31_Sub1_4 = (JavaMatrix) arg0;
		this.aClass31_Sub1_3.method5158(this.aClass31_Sub1_4);
		if (this.anInt2319 != 1) {
			this.method1952();
		}
	}

	@OriginalMember(owner = "client!fd", name = "D", descriptor = "()V")
	@Override
	public void cacheReset() {
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void releaseSurface(@OriginalArg(0) Canvas canvas) {
		@Pc(5) long local5 = 0L;
		if (canvas == null || this.aCanvas3 == canvas) {
			local5 = this.canvasSurface;
		} else if (this.aHashtable2.containsKey(canvas)) {
			@Pc(27) Long local27 = (Long) this.aHashtable2.get(canvas);
			local5 = local27;
		}
		if (local5 == 0L) {
			throw new RuntimeException();
		}
		this.anOpenGL1.surfaceResized(local5);
		if (this.aCanvas2 == canvas) {
			this.method1909();
		}
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(B)V")
	private void method1915() {
		if (this.clipLeft <= this.anInt2292 && this.anInt2318 <= this.anInt2296) {
			OpenGL.glScissor(this.clipLeft + this.anInt2308, -this.anInt2296 + (this.anInt2283 - -this.anInt2138), this.anInt2292 - this.clipLeft, this.anInt2296 + -this.anInt2318);
		} else {
			OpenGL.glScissor(0, 0, 0, 0);
		}
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void setCanvas(@OriginalArg(0) Canvas canvas) {
		this.aCanvas2 = null;
		this.currentSurface = 0L;
		if (canvas == null || canvas == this.aCanvas3) {
			this.currentSurface = this.canvasSurface;
			this.aCanvas2 = this.aCanvas3;
		} else if (this.aHashtable2.containsKey(canvas)) {
			@Pc(26) Long local26 = (Long) this.aHashtable2.get(canvas);
			this.currentSurface = local26;
			this.aCanvas2 = canvas;
		}
		if (this.aCanvas2 == null || this.currentSurface == 0L) {
			throw new RuntimeException();
		}
		this.anOpenGL1.setSurface(this.currentSurface);
		this.method1909();
	}

	@OriginalMember(owner = "client!fd", name = "t", descriptor = "()V")
	@Override
	public void method4278() {
		// The compositor's own pass for a component that captured the screen.
		// 592HD's screen-space rows are not applied here: they belong to the
		// finished frame, which is {@link #method4247()} - see
		// {@link #hdPostFrame()}.
		this.framebufferCompositor.compositeEffects();
	}

	@OriginalMember(owner = "client!fd", name = "m", descriptor = "(I)V")
	public void method1916() {
		OpenGL.glPopMatrix();
	}

	@OriginalMember(owner = "client!fd", name = "W", descriptor = "()F")
	@Override
	public float W() {
		return this.aFloat63;
	}

	@OriginalMember(owner = "client!fd", name = "n", descriptor = "(I)V")
	private void method1917() {
		if (this.aBoolean206) {
			OpenGL.glMatrixMode(5890);
			OpenGL.glLoadIdentity();
			OpenGL.glMatrixMode(5888);
			this.aBoolean206 = false;
		}
	}

	@OriginalMember(owner = "client!fd", name = "i", descriptor = "()Z")
	@Override
	public boolean method4248() {
		if (this.aClass4_Sub10_Sub1_1 == null) {
			return false;
		}
		if (!this.aClass4_Sub10_Sub1_1.method2003()) {
			if (!this.framebufferCompositor.method5525(this.aClass4_Sub10_Sub1_1)) {
				return false;
			}
			this.textureCache.method1550();
		}
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(ZI)V")
	public void method1918(@OriginalArg(0) boolean arg0) {
		if (this.aBoolean197 != arg0) {
			this.aBoolean197 = arg0;
			this.method1968();
			this.anInt2320 &= 0xFFFFFFE0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "o", descriptor = "(I)V")
	private void method1919() {
		if (this.aBoolean212 && !this.aBoolean202) {
			OpenGL.glEnable(2896);
		} else {
			OpenGL.glDisable(2896);
		}
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "(II)I")
	public int method1920(@OriginalArg(0) int arg0) {
		if (arg0 == 6406 || arg0 == 6409) {
			return 1;
		} else if (arg0 == 6410 || arg0 == 34846 || arg0 == 34844) {
			return 2;
		} else if (arg0 == 6407) {
			return 3;
		} else if (arg0 == 6408 || arg0 == 34847) {
			return 4;
		} else if (arg0 == 34843) {
			return 6;
		} else if (arg0 == 34842) {
			return 8;
		} else if (arg0 == 6402) {
			return 3;
		} else if (arg0 == 6401) {
			return 1;
		} else {
			throw new IllegalArgumentException("");
		}
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "(Z)V")
	private void method1921() {
		this.anOpenGL1.detach();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!cp;B)V")
	public void method1922(@OriginalArg(0) GlRenderTarget arg0) {
		if (this.aBoolean204) {
			this.method1940(arg0);
			this.method1956(arg0);
		} else if (this.anInt2276 >= 3) {
			throw new RuntimeException();
		} else {
			if (this.anInt2276 >= 0) {
				this.readTargetStack[this.anInt2276].method3603();
			}
			this.anInterface3_2 = this.anInterface3_1 = this.readTargetStack[++this.anInt2276] = arg0;
			this.anInterface3_2.method3602();
		}
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(B)V")
	private void method1923() {
		@Pc(6) float[] local6 = this.projectionMatrix;
		@Pc(18) float local18 = (float) (-this.anInt2295 * this.zNear) / (float) this.anInt2309;
		@Pc(32) float local32 = (float) ((this.anInt2240 - this.anInt2295) * this.zNear) / (float) this.anInt2309;
		@Pc(52) float local52 = (float) (this.anInt2297 * this.zNear) / (float) this.anInt2294;
		@Pc(67) float local67 = (float) (this.zNear * (this.anInt2297 - this.anInt2138)) / (float) this.anInt2294;
		if (local18 == local32 || local67 == local52) {
			local6[7] = 0.0F;
			local6[13] = 0.0F;
			local6[3] = 0.0F;
			local6[5] = 1.0F;
			local6[2] = 0.0F;
			local6[9] = 0.0F;
			local6[14] = 0.0F;
			local6[4] = 0.0F;
			local6[10] = 1.0F;
			local6[15] = 1.0F;
			local6[8] = 0.0F;
			local6[1] = 0.0F;
			local6[12] = 0.0F;
			local6[0] = 1.0F;
			local6[11] = 0.0F;
			local6[6] = 0.0F;
		} else {
			@Pc(81) float local81 = (float) this.zNear * 2.0F;
			local6[6] = 0.0F;
			local6[3] = 0.0F;
			local6[13] = 0.0F;
			local6[5] = local81 / (local52 - local67);
			local6[4] = 0.0F;
			local6[7] = 0.0F;
			local6[8] = (local32 + local18) / (local32 - local18);
			local6[12] = 0.0F;
			local6[9] = (local52 + local67) / (-local67 + local52);
			local6[14] = this.aFloat60 = -(local81 * (float) this.anInt2312) / (float) (this.anInt2312 - this.zNear);
			local6[2] = 0.0F;
			local6[15] = 0.0F;
			local6[10] = this.aFloat69 = (float) -(this.anInt2312 + this.zNear) / (float) (this.anInt2312 - this.zNear);
			local6[1] = 0.0F;
			local6[11] = -1.0F;
			local6[0] = local81 / (local32 - local18);
		}
		this.method1975();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIII)Lclient!tb;")
	@Override
	public EnvironmentSampler createCubeTexture(@OriginalArg(0) int materialAtPositiveX, @OriginalArg(1) int materialAtNegativeX, @OriginalArg(2) int materialAtPositiveY, @OriginalArg(3) int materialAtNegativeY, @OriginalArg(4) int materialAtPositiveZ, @OriginalArg(5) int materialAtNegativeZ) {
		return this.arbTextureCubeMap ? new OpenGLArbTextureCubeMapGenerator(this, materialAtPositiveX, materialAtNegativeX, materialAtPositiveY, materialAtNegativeY, materialAtPositiveZ, materialAtNegativeZ) : null;
	}

	@OriginalMember(owner = "client!fd", name = "h", descriptor = "(Z)V")
	private void method1924() {
		if (this.anInt2319 != 2) {
			this.anInt2319 = 2;
			this.method1907();
			this.method1952();
			this.anInt2320 &= 0xFFFFFFF8;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "([Ljava/awt/Rectangle;I)V")
	@Override
	public void method4290(@OriginalArg(0) Rectangle[] arg0, @OriginalArg(1) int arg1) {
		this.method4247();
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(IIII)V")
	public void method1925(@OriginalArg(3) int arg0) {
		OpenGL.glDrawArrays(7, 0, arg0);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!tb;Lclient!tb;FLclient!tb;)Lclient!tb;")
	@Override
	public EnvironmentSampler drawQuad(@OriginalArg(0) EnvironmentSampler environmentSampler, @OriginalArg(1) EnvironmentSampler environmentSampler2, @OriginalArg(2) float alpha, @OriginalArg(3) EnvironmentSampler environmentSampler3) {
		if (environmentSampler != null && environmentSampler2 != null && this.arbTextureCubeMap && this.extFramebufferObject && this.aBoolean204) {
			@Pc(18) OpenGlCubeTextureGenerator local18 = null;
			@Pc(21) Class99_Sub1 local21 = (Class99_Sub1) environmentSampler;
			@Pc(24) Class99_Sub1 local24 = (Class99_Sub1) environmentSampler2;
			@Pc(28) GlCubeTexture local28 = local21.method3232();
			@Pc(32) GlCubeTexture local32 = local24.method3232();
			if (local28 != null && local32 != null) {
				@Pc(51) int local51 = local32.size < local28.size ? local28.size : local32.size;
				if (environmentSampler3 != environmentSampler && environmentSampler3 != environmentSampler2 && environmentSampler3 instanceof OpenGlCubeTextureGenerator) {
					@Pc(63) OpenGlCubeTextureGenerator local63 = (OpenGlCubeTextureGenerator) environmentSampler3;
					if (local51 == local63.method2668()) {
						local18 = local63;
					}
				}
				if (local18 == null) {
					local18 = new OpenGlCubeTextureGenerator(this, local51);
				}
				if (local18.method2666(local32, alpha, local28)) {
					return local18;
				}
			}
		}
		return alpha < 0.5F ? environmentSampler : environmentSampler2;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!t;Lclient!mc;Lclient!c;Lclient!mi;I)V")
	@Override
	public void method4262(@OriginalArg(0) Model arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon arg3) {
		arg0.render(arg2, arg3, 0);
		this.render(arg1);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZBIZ)V")
	public void method1926(@OriginalArg(0) boolean arg0, @OriginalArg(2) int arg1, @OriginalArg(3) boolean arg2) {
		boolean hdModelBind = this.hdModelMaterialBind;
		this.hdModelMaterialBind = false;
		if (this.hdWaterActive()) {
			// 592HD: the water type is the material's, so it is resolved on every
			// bind rather than only when the material changes - a reflective lake
			// must re-read the environment cube every frame, and the cube object
			// is replaced when the sky is repainted.
			this.hdWaterMaterial(arg1);
		}
		if (this.anInt2314 != arg1 || hdReflectionStale(arg1, hdModelBind)) {
			if (arg1 < 0) {
				this.method1917();
				this.setActiveTexture(null);
				this.method1900(0);
				if (!this.aBoolean192) {
					this.effectManager.method5318(arg2, arg0, 0);
				}
			} else {
				@Pc(13) OpenGl2DTexture local13 = this.textureCache.method1549(arg1);
				@Pc(19) MaterialType local19 = super.materialProvider.getMetrics(arg1);
				if (local19.aByte91 == 0 && local19.aByte93 == 0) {
					this.method1917();
				} else {
					@Pc(35) int local35 = local19.isSmall ? 64 : 128;
					@Pc(39) int local39 = local35 * 50;
					this.method1963((float) (local19.aByte93 * (this.anInt2274 % local39)) / (float) local39, (float) (local19.aByte91 * (this.anInt2274 % local39)) / (float) local39, 0.0F);
				}
				if (this.aBoolean192) {
					// HD water owns the program already; enabling the stock ARB
					// water program here would leave two programs enabled at once.
					if (!this.hdWaterActive()) {
						this.effectManager.method5318(arg2, arg0, 3);
					}
					this.setActiveTexture(local13);
					this.method1900(local19.anInt6934);
					this.hdMaterialSlot = -1;
				} else {
					// 592HD: a material the client already draws reflectively can be taken
					// from the stock specular effect and drawn by the lighting program,
					// which samples the same environment cube instead. Off, or a
					// material that is not the reflective type, keeps the stock pass.
					boolean hdReflection = hdModelBind && hdReflectionOwns(local19);
					this.effectManager.method5318(arg2, arg0, hdReflection ? 0 : local19.aByte89);
					if (!hdReflection) {
						this.effectManager.method5319(local19.anInt6933, local19.aByte88);
					}
					this.hdMaterialSlot = local19.aByte89;
					if (!this.effectManager.method5316(local13, local19.anInt6934)) {
						this.setActiveTexture(local13);
						this.method1900(local19.anInt6934);
					}
				}
			}
			this.anInt2314 = arg1;
		}
		this.anInt2320 &= 0xFFFFFFF8;
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "(B)V")
	private void method1927() {
		Class4_Sub41.aFloatArray30[2] = this.aFloat74 * this.aFloat73;
		Class4_Sub41.aFloatArray30[0] = this.aFloat73 * this.aFloat72;
		Class4_Sub41.aFloatArray30[3] = 1.0F;
		Class4_Sub41.aFloatArray30[1] = this.aFloat58 * this.aFloat73;
		OpenGL.glLightfv(16384, 4609, Class4_Sub41.aFloatArray30, 0);
		Class4_Sub41.aFloatArray30[1] = this.aFloat58 * -this.aFloat65;
		Class4_Sub41.aFloatArray30[2] = -this.aFloat65 * this.aFloat74;
		Class4_Sub41.aFloatArray30[3] = 1.0F;
		Class4_Sub41.aFloatArray30[0] = -this.aFloat65 * this.aFloat72;
		OpenGL.glLightfv(16385, 4609, Class4_Sub41.aFloatArray30, 0);
	}

	@OriginalMember(owner = "client!fd", name = "T", descriptor = "(IFFFFF)V")
	@Override
	public void scaleHeight(@OriginalArg(0) int arg0, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) float arg4, @OriginalArg(5) float arg5) {
		// The hour dims the sun's colour and the fill's share of it; the
		// intensity in arg1 stays the caller's. Everything downstream - the
		// GL lights, the baked vertex light in GlGround/GlModel, the sky's
		// disc - reads the value stored here, so they cannot disagree.
		@Pc(1) HdDayNight.Factors dayNight = hdDayNight(arg3, arg4, arg5);
		@Pc(3) int colour = dayNight.sunColour(arg0);
		@Pc(5) float fill = arg2 * dayNight.fill();
		@Pc(11) boolean local11 = this.sunColorRgb != colour;
		if (local11 || arg1 != this.aFloat73 || fill != this.aFloat65) {
			this.aFloat65 = fill;
			this.sunColorRgb = colour;
			this.aFloat73 = arg1;
			if (local11) {
				this.aFloat58 = (float) (this.sunColorRgb & 0xFF00) / 65280.0F;
				this.aFloat72 = (float) (this.sunColorRgb & 0xFF0000) / 1.671168E7F;
				this.aFloat74 = (float) (this.sunColorRgb & 0xFF) / 255.0F;
				this.method1961();
			}
			this.method1927();
		}
		if (this.sunPreviousPosition[0] == arg3 && arg4 == this.sunPreviousPosition[1] && this.sunPreviousPosition[2] == arg5) {
			return;
		}
		this.sunPreviousPosition[2] = arg5;
		this.sunPreviousPosition[1] = arg4;
		this.sunPreviousPosition[0] = arg3;
		this.antiSunPreviousPosition[1] = -arg4;
		this.antiSunPreviousPosition[0] = -arg3;
		this.antiSunPreviousPosition[2] = -arg5;
		@Pc(138) float local138 = (float) (1.0D / Math.sqrt((double) (arg4 * arg4 + arg3 * arg3 + arg5 * arg5)));
		this.sunDirection[0] = arg3 * local138;
		this.sunDirection[2] = arg5 * local138;
		this.sunDirection[1] = local138 * arg4;
		this.lightingDirection[2] = -this.sunDirection[2];
		this.lightingDirection[1] = -this.sunDirection[1];
		this.lightingDirection[0] = -this.sunDirection[0];
		this.method1914();
		this.sunSlopeZ = (int) (arg3 * 256.0F / arg4);
		this.sunSlopeX = (int) (arg5 * 256.0F / arg4);
	}

	@OriginalMember(owner = "client!fd", name = "i", descriptor = "(Z)V")
	public void method1928() {
		if (this.anInt2320 == 16) {
			return;
		}
		this.method1899();
		this.method1918(true);
		this.method1951(true);
		this.method1911(true);
		this.method1958(1);
		this.method1900(0);
		this.anInt2320 = 16;
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(II)I")
	@Override
	public int combineFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB) {
		return maskB | maskA;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(I)V")
	@Override
	public synchronized void tick(@OriginalArg(0) int time) {
		@Pc(9) int local9 = time & Integer.MAX_VALUE;
		@Pc(11) int local11 = 0;
		@Pc(19) Class4_Sub37 local19;
		while (!this.vboBuffersAwaitingDeallocation.method4147()) {
			local19 = (Class4_Sub37) this.vboBuffersAwaitingDeallocation.method4136();
			PingWorker.anIntArray537[local11++] = (int) local19.aLong224;
			this.vboBuffersSize -= local19.anInt5426;
			if (local11 == 1000) {
				OpenGL.glDeleteBuffersARB(local11, PingWorker.anIntArray537, 0);
				local11 = 0;
			}
		}
		if (local11 > 0) {
			OpenGL.glDeleteBuffersARB(local11, PingWorker.anIntArray537, 0);
			local11 = 0;
		}
		while (!this.texturesAwaitingDeallocation.method4147()) {
			local19 = (Class4_Sub37) this.texturesAwaitingDeallocation.method4136();
			PingWorker.anIntArray537[local11++] = (int) local19.aLong224;
			this.textureBuffersSize -= local19.anInt5426;
			if (local11 == 1000) {
				OpenGL.glDeleteTextures(local11, PingWorker.anIntArray537, 0);
				local11 = 0;
			}
		}
		if (local11 > 0) {
			OpenGL.glDeleteTextures(local11, PingWorker.anIntArray537, 0);
			local11 = 0;
		}
		while (!this.frameBuffersAwaitingDeallocation.method4147()) {
			local19 = (Class4_Sub37) this.frameBuffersAwaitingDeallocation.method4136();
			PingWorker.anIntArray537[local11++] = local19.anInt5426;
			if (local11 == 1000) {
				OpenGL.glDeleteFramebuffersEXT(local11, PingWorker.anIntArray537, 0);
				local11 = 0;
			}
		}
		if (local11 > 0) {
			OpenGL.glDeleteFramebuffersEXT(local11, PingWorker.anIntArray537, 0);
			local11 = 0;
		}
		while (!this.aClass183_11.method4147()) {
			local19 = (Class4_Sub37) this.aClass183_11.method4136();
			PingWorker.anIntArray537[local11++] = (int) local19.aLong224;
			this.renderBuffersSize -= local19.anInt5426;
			if (local11 == 1000) {
				OpenGL.glDeleteRenderbuffersEXT(local11, PingWorker.anIntArray537, 0);
				local11 = 0;
			}
		}
		if (local11 > 0) {
			OpenGL.glDeleteRenderbuffersEXT(local11, PingWorker.anIntArray537, 0);
		}
		while (!this.listsAwaitingDeallocation.method4147()) {
			local19 = (Class4_Sub37) this.listsAwaitingDeallocation.method4136();
			OpenGL.glDeleteLists((int) local19.aLong224, local19.anInt5426);
		}
		@Pc(215) Country local215;
		while (!this.arbProgramsAwaitingDeallocation.method4147()) {
			local215 = this.arbProgramsAwaitingDeallocation.method4136();
			// Not every jaggl build exports glDeleteProgramARB (the 64-bit
			// Windows and macOS natives do not); glDeleteObjectARB frees the
			// same ARB shader/program object.
			OpenGL.glDeleteObjectARB(local215.aLong224);
		}
		while (!this.aClass183_13.method4147()) {
			local215 = this.aClass183_13.method4136();
			OpenGL.glDeleteObjectARB(local215.aLong224);
		}
		while (!this.listsAwaitingDeallocation.method4147()) {
			local19 = (Class4_Sub37) this.listsAwaitingDeallocation.method4136();
			OpenGL.glDeleteLists((int) local19.aLong224, local19.anInt5426);
		}
		this.textureCache.method1545();
		if (this.IA() > 100663296 && Class168.method3962() > this.lastGcTime + 60000L) {
			System.gc();
			this.lastGcTime = Class168.method3962();
		}
		this.anInt2274 = local9;
	}

	@OriginalMember(owner = "client!fd", name = "q", descriptor = "(I)V")
	public void method1929() {
		if (this.anInt2320 == 4) {
			return;
		}
		this.method1932();
		this.method1918(false);
		this.method1906(false);
		this.method1951(false);
		this.method1911(false);
		this.method1957(-2);
		this.method1958(1);
		this.method1900(0);
		this.anInt2320 = 4;
	}

	@OriginalMember(owner = "client!fd", name = "j", descriptor = "(Z)V")
	public void method1930() {
		if (this.anInt2320 == 2) {
			return;
		}
		this.method1932();
		this.method1918(false);
		this.method1906(false);
		this.method1951(false);
		this.method1911(false);
		this.method1957(-2);
		this.anInt2320 = 2;
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(III)V")
	public void method1931(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		this.anInt2308 = arg1;
		this.anInt2283 = arg0;
		this.method1950();
		this.method1915();
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "(I)V")
	@Override
	public void method4303(@OriginalArg(0) int threadId) {
		this.method1921();
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "()V")
	@Override
	public void e() {
		this.anInt2292 = this.anInt2240;
		this.clipLeft = 0;
		this.anInt2296 = this.anInt2138;
		this.anInt2318 = 0;
		OpenGL.glDisable(3089);
		this.method1953();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(II[[I[[IIII)Lclient!ta;")
	@Override
	public Class65 method4297(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int[][] arg2, @OriginalArg(3) int[][] arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5) {
		return new GlGround(this, arg4, arg5, arg0, arg1, arg2, arg3, 128);
	}

	@OriginalMember(owner = "client!fd", name = "r", descriptor = "(I)V")
	private void method1932() {
		if (this.anInt2319 == 1) {
			return;
		}
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadIdentity();
		if (this.anInt2240 > 0 && this.anInt2138 > 0) {
			OpenGL.glOrtho(0.0D, (double) this.anInt2240, (double) this.anInt2138, 0.0D, -1.0D, 1.0D);
		}
		OpenGL.glMatrixMode(5888);
		OpenGL.glLoadIdentity();
		this.anInt2319 = 1;
		this.anInt2320 &= 0xFFFFFFE7;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIIILclient!ma;II)V")
	@Override
	public void drawSurfaceLine(@OriginalArg(0) int startX, @OriginalArg(1) int startY, @OriginalArg(2) int endX, @OriginalArg(3) int endY, @OriginalArg(4) int colour, @OriginalArg(6) ComponentSurface arg5, @OriginalArg(7) int clippingMask, @OriginalArg(8) int maskX) {
		@Pc(6) Class145_Sub1 local6 = (Class145_Sub1) arg5;
		@Pc(9) OpenGl2DImageTexture local9 = local6.aClass6_Sub1_Sub1_5;
		this.method1930();
		this.setActiveTexture(local6.aClass6_Sub1_Sub1_5);
		this.method1958(1);
		this.method1912(7681, 8448);
		this.method1945(0, 768, 34167);
		@Pc(39) float local39 = local9.s / (float) local9.singleWidth;
		@Pc(46) float local46 = local9.captureScreenRegion / (float) local9.singleLength;
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glBegin(1);
		OpenGL.glTexCoord2f(local39 * (float) (startX - clippingMask), (float) (startY - maskX) * local46);
		OpenGL.glVertex2f((float) startX + 0.35F, (float) startY + 0.35F);
		OpenGL.glTexCoord2f(local39 * (float) (endX - clippingMask), local46 * (float) (endY - maskX));
		OpenGL.glVertex2f((float) endX + 0.35F, (float) endY + 0.35F);
		OpenGL.glEnd();
		this.method1945(0, 768, 5890);
	}

	@OriginalMember(owner = "client!fd", name = "AA", descriptor = "(IIII)V")
	@Override
	public void AA(@OriginalArg(0) int offsetX, @OriginalArg(1) int offsetY, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt2293 = arg3;
		this.anInt2288 = offsetX;
		this.anInt2317 = arg2;
		this.anInt2287 = offsetY;
		this.aBoolean192 = true;
		// 592HD: this is the one place the stock water effect is selected for a
		// water region. The HD program takes its place when the "592HD water"
		// row asks for it; otherwise the stock selection and its program run
		// untouched.
		if (!this.hdBeginWater()) {
			this.effectManager.method5318(false, false, 3);
			this.effectManager.waterEffect.method5380();
		}
		this.updateFog();
		this.method1968();
	}

	@OriginalMember(owner = "client!fd", name = "n", descriptor = "()Z")
	@Override
	public boolean supportsAntiAliasing() {
		return this.arbMultisample && (!this.method4301() || this.aBoolean201);
	}

	@OriginalMember(owner = "client!fd", name = "s", descriptor = "(I)V")
	private void method1933() {
		@Pc(15) float local15 = (float) -this.anInt2295 * this.aFloat66 / (float) this.anInt2309;
		@Pc(27) float local27 = this.aFloat66 * (float) -this.anInt2297 / (float) this.anInt2294;
		@Pc(48) float local48 = this.aFloat66 * (float) (this.anInt2240 - this.anInt2295) / (float) this.anInt2309;
		@Pc(63) float local63 = (float) (this.anInt2138 - this.anInt2297) * this.aFloat66 / (float) this.anInt2294;
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadIdentity();
		if (local15 != local48 && local27 != local63) {
			OpenGL.glOrtho((double) local15, (double) local48, (double) -local63, (double) -local27, (double) ((float) this.zNear - this.aFloat67), (double) ((float) this.anInt2312 - this.aFloat67));
		}
		OpenGL.glMatrixMode(5888);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!pc;ILclient!pc;Lclient!pc;Lclient!pc;)V")
	public void method1934(@OriginalArg(0) Class177 arg0, @OriginalArg(2) Class177 arg1, @OriginalArg(3) Class177 arg2, @OriginalArg(4) Class177 arg3) {
		if (arg3 == null) {
			OpenGL.glDisableClientState(32884);
		} else {
			this.method1944(arg3.anInterface11_7);
			OpenGL.glVertexPointer(arg3.aByte56, arg3.aShort68, this.glBinding.method5182(), this.glBinding.getAddress() + (long) arg3.aByte55);
			OpenGL.glEnableClientState(32884);
		}
		if (arg1 == null) {
			OpenGL.glDisableClientState(32885);
		} else {
			this.method1944(arg1.anInterface11_7);
			OpenGL.glNormalPointer(arg1.aShort68, this.glBinding.method5182(), this.glBinding.getAddress() + (long) arg1.aByte55);
			OpenGL.glEnableClientState(32885);
		}
		if (arg0 == null) {
			OpenGL.glDisableClientState(32886);
		} else {
			this.method1944(arg0.anInterface11_7);
			OpenGL.glColorPointer(arg0.aByte56, arg0.aShort68, this.glBinding.method5182(), this.glBinding.getAddress() + (long) arg0.aByte55);
			OpenGL.glEnableClientState(32886);
		}
		if (arg2 == null) {
			OpenGL.glDisableClientState(32888);
		} else {
			this.method1944(arg2.anInterface11_7);
			OpenGL.glTexCoordPointer(arg2.aByte56, arg2.aShort68, this.glBinding.method5182(), this.glBinding.getAddress() + (long) arg2.aByte55);
			OpenGL.glEnableClientState(32888);
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(I[Lclient!vp;)V")
	@Override
	public void copyLights(@OriginalArg(0) int arg0, @OriginalArg(1) PointLight[] pointLights) {
		for (@Pc(7) int local7 = 0; local7 < arg0; local7++) {
			this.aClass4_Sub21Array1[local7] = pointLights[local7];
		}
		this.lastSceneLightCount = arg0;
		if (this.anInt2319 != 1) {
			this.method1903();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZLclient!cp;)V")
	public void method1935(@OriginalArg(1) GlRenderTarget arg0) {
		if (this.anInt2278 < 0 || this.targetStack[this.anInt2278] != arg0) {
			throw new RuntimeException();
		}
		this.targetStack[this.anInt2278--] = null;
		arg0.method3606();
		if (this.anInt2278 >= 0) {
			this.anInterface3_2 = this.targetStack[this.anInt2278];
			this.anInterface3_2.method3607();
		} else {
			this.anInterface3_2 = null;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!u;)V")
	@Override
	public void setHeap(@OriginalArg(0) MemoryPool memoryPool) {
		this.aNativeHeap2 = ((GlMemoryPool) memoryPool).aNativeHeap1;
		if (this.anInterface11_2 != null) {
			return;
		}
		@Pc(16) GlBitBuffer local16 = new GlBitBuffer(80);
		if (this.bigEndianess) {
			local16.method2556(-1.0F);
			local16.method2556(-1.0F);
			local16.method2556(0.0F);
			local16.method2556(0.0F);
			local16.method2556(1.0F);
			local16.method2556(1.0F);
			local16.method2556(-1.0F);
			local16.method2556(0.0F);
			local16.method2556(1.0F);
			local16.method2556(1.0F);
			local16.method2556(1.0F);
			local16.method2556(1.0F);
			local16.method2556(0.0F);
			local16.method2556(1.0F);
			local16.method2556(0.0F);
			local16.method2556(-1.0F);
			local16.method2556(1.0F);
			local16.method2556(0.0F);
			local16.method2556(0.0F);
			local16.method2556(0.0F);
		} else {
			local16.method2552(-1.0F);
			local16.method2552(-1.0F);
			local16.method2552(0.0F);
			local16.method2552(0.0F);
			local16.method2552(1.0F);
			local16.method2552(1.0F);
			local16.method2552(-1.0F);
			local16.method2552(0.0F);
			local16.method2552(1.0F);
			local16.method2552(1.0F);
			local16.method2552(1.0F);
			local16.method2552(1.0F);
			local16.method2552(0.0F);
			local16.method2552(1.0F);
			local16.method2552(0.0F);
			local16.method2552(-1.0F);
			local16.method2552(1.0F);
			local16.method2552(0.0F);
			local16.method2552(0.0F);
			local16.method2552(0.0F);
		}
		this.anInterface11_2 = this.method1960(local16.currentPosition, local16.buffer, false, 20);
		this.quadVertexBuffer = new Class177(this.anInterface11_2, 5126, 3, 0);
		this.quadTexCoordBuffer = new Class177(this.anInterface11_2, 5126, 2, 12);
		this.particleRenderer.method917(this);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "()I")
	@Override
	public int a() {
		return this.zNear;
	}

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "(II)V")
	public void method1936(@OriginalArg(0) int arg0) {
		if (arg0 != this.anInt2301) {
			OpenGL.glActiveTexture(arg0 + 33984);
			this.anInt2301 = arg0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "l", descriptor = "()V")
	@Override
	public void l() {
		this.aBoolean192 = false;
		this.hdEndWater();
		this.effectManager.method5318(false, false, 0);
		this.updateFog();
		this.method1968();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(FB)V")
	public void method1937(@OriginalArg(0) float arg0) {
		if (arg0 != this.aFloat66) {
			this.aFloat66 = arg0;
			if (this.anInt2319 == 3) {
				this.method1933();
			}
		}
	}

	@OriginalMember(owner = "client!fd", name = "y", descriptor = "()Lclient!c;")
	@Override
	public Ground method4289() {
		return this.aClass31_Sub1_1;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZIZ)V")
	public void method1938(@OriginalArg(1) int arg0, @OriginalArg(2) boolean arg1) {
		// Only this entry is the model draw path; the terrain calls method1926
		// directly and keeps its own effect selection untouched.
		this.hdModelMaterialBind = true;
		this.method1926(arg1, arg0, true);
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(III)V")
	public synchronized void method1939(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1) {
		@Pc(12) Class4_Sub37 local12 = new Class4_Sub37(arg1);
		local12.aLong224 = arg0;
		this.vboBuffersAwaitingDeallocation.method4137(local12);
	}

	@OriginalMember(owner = "client!fd", name = "q", descriptor = "()Z")
	@Override
	public boolean method4264() {
		return false;
	}

	@OriginalMember(owner = "client!fd", name = "s", descriptor = "()Lclient!c;")
	@Override
	public Ground method4274() {
		return new JavaMatrix();
	}

	@OriginalMember(owner = "client!fd", name = "OA", descriptor = "(IIII)V")
	@Override
	public void OA(@OriginalArg(0) int left, @OriginalArg(1) int top, @OriginalArg(2) int right, @OriginalArg(3) int bottom) {
		if (top < 0) {
			top = 0;
		}
		if (left < 0) {
			left = 0;
		}
		if (bottom > this.anInt2138) {
			bottom = this.anInt2138;
		}
		if (right > this.anInt2240) {
			right = this.anInt2240;
		}
		this.clipLeft = left;
		this.anInt2318 = top;
		this.anInt2292 = right;
		this.anInt2296 = bottom;
		OpenGL.glEnable(3089);
		this.method1953();
		this.method1915();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(BLclient!cp;)V")
	public void method1940(@OriginalArg(1) GlRenderTarget arg0) {
		if (this.anInt2278 >= 3) {
			throw new RuntimeException();
		}
		if (this.anInt2278 >= 0) {
			this.targetStack[this.anInt2278].method3606();
		}
		this.anInterface3_2 = this.targetStack[++this.anInt2278] = arg0;
		this.anInterface3_2.method3607();
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(ZI)V")
	public synchronized void method1941(@OriginalArg(1) int arg0) {
		@Pc(12) Class4_Sub37 local12 = new Class4_Sub37(arg0);
		this.frameBuffersAwaitingDeallocation.method4137(local12);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!tu;I)V")
	public void method1942(@OriginalArg(0) JavaMatrix arg0) {
		OpenGL.glLoadMatrixf(arg0.method5161(), 0);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(FFI)V")
	public void method1943(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		this.aFloat57 = arg1;
		this.aFloat71 = arg0;
		if (!this.aBoolean192) {
			this.updateFog();
		}
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "()Z")
	@Override
	public boolean method4245() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "ZA", descriptor = "()I")
	@Override
	public int ZA() {
		@Pc(6) int local6 = this.anInt2310;
		this.anInt2310 = 0;
		return local6;
	}

	@OriginalMember(owner = "client!fd", name = "da", descriptor = "([I)V")
	@Override
	public void da(@OriginalArg(0) int[] bounds) {
		bounds[3] = this.anInt2296;
		bounds[2] = this.anInt2292;
		bounds[0] = this.clipLeft;
		bounds[1] = this.anInt2318;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!vd;I)V")
	public void method1944(@OriginalArg(0) ElementsBufferGL arg0) {
		if (arg0 != this.glBinding) {
			if (this.aBoolean205) {
				OpenGL.glBindBufferARB(34962, arg0.method5181());
			}
			this.glBinding = arg0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "xa", descriptor = "()F")
	@Override
	public float xa() {
		return this.aFloat67;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(BIII)V")
	public void method1945(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		OpenGL.glTexEnvi(8960, arg0 + 34176, arg2);
		OpenGL.glTexEnvi(8960, arg0 + 34192, arg1);
	}

	@OriginalMember(owner = "client!fd", name = "f", descriptor = "(B)Lclient!im;")
	public GlCubeTexture method1946() {
		return this.aClass99_Sub1_1 == null ? null : this.aClass99_Sub1_1.method3232();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!tb;)V")
	@Override
	public void setSunCubeTexture(@OriginalArg(0) EnvironmentSampler environmentSampler) {
		this.aClass99_Sub1_1 = (Class99_Sub1) environmentSampler;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIZ)Lclient!l;")
	@Override
	public NativeSprite createNativeSprite(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) boolean hasAlpha) {
		return new SpriteGL(this, x, y, width, height);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ILclient!tu;)V")
	public void method1948(@OriginalArg(1) JavaMatrix arg0) {
		OpenGL.glPushMatrix();
		float[] matrix = arg0.method5161();
		OpenGL.glMultMatrixf(matrix, 0);
		// 592HD: the model's place in the scene. The vertex buffer is
		// model-local, so the lighting shader composes this to get a scene
		// position for the shadow map and the occlusion kernel.
		System.arraycopy(matrix, 0, this.hdModelMatrix, 0, this.hdModelMatrix.length);
	}

	@OriginalMember(owner = "client!fd", name = "j", descriptor = "()Z")
	@Override
	public boolean method4251() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "w", descriptor = "()I")
	@Override
	public int w() {
		@Pc(6) int local6 = this.anInt2303;
		this.anInt2303 = 0;
		return local6;
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(Lclient!cp;B)V")
	public void method1949(@OriginalArg(0) GlRenderTarget arg0) {
		if (this.anInt2277 < 0 || arg0 != this.drawTargetStack[this.anInt2277]) {
			throw new RuntimeException();
		}
		this.drawTargetStack[this.anInt2277--] = null;
		arg0.method3604();
		if (this.anInt2277 < 0) {
			this.anInterface3_1 = null;
		} else {
			this.anInterface3_1 = this.drawTargetStack[this.anInt2277];
			this.anInterface3_1.method3605();
		}
	}

	@OriginalMember(owner = "client!fd", name = "u", descriptor = "(I)V")
	private void method1950() {
		OpenGL.glViewport(this.anInt2308, this.anInt2283, this.anInt2240, this.anInt2138);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZB)V")
	public void method1951(@OriginalArg(0) boolean arg0) {
		if (this.aBoolean203 == arg0) {
			return;
		}
		if (arg0) {
			OpenGL.glEnable(2929);
		} else {
			OpenGL.glDisable(2929);
		}
		this.anInt2320 &= 0xFFFFFFE0;
		this.aBoolean203 = arg0;
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "()V")
	@Override
	protected void stop() {
		// The 592HD program belongs to this context: delete it while the context
		// is still current, before the surface and context are released.
		this.hdLighting.shutdown();
		this.hdWater.shutdown();
		this.hdPost.shutdown();
		this.hdSky.shutdown();
		if (this.sunShadow != null) {
			this.sunShadow.release();
			this.sunShadow = null;
		}
		for (@Pc(6) Country local6 = this.heapManager.method4140(); local6 != null; local6 = this.heapManager.method4144()) {
			((GlMemoryPool) local6).deallocate();
		}
		if (this.framebufferCompositor != null) {
			this.framebufferCompositor.shutdown();
		}
		if (this.anOpenGL1 != null) {
			this.method1921();
			@Pc(40) Enumeration local40 = this.aHashtable2.keys();
			while (local40.hasMoreElements()) {
				@Pc(46) Canvas local46 = (Canvas) local40.nextElement();
				@Pc(52) Long local52 = (Long) this.aHashtable2.get(local46);
				this.anOpenGL1.releaseSurface(local46, local52);
			}
			this.anOpenGL1.release();
			this.anOpenGL1 = null;
		}
		if (this.colortableReset) {
			TextureType.method3281(false, true);
			this.colortableReset = false;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIIII)V")
	@Override
	public void strongLine(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int width) {
		OpenGL.glLineWidth((float) width);
		this.line(x1, y1, x2, y2, colour, 0);
		OpenGL.glLineWidth(1.0F);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "([Lclient!t;Lclient!mc;Lclient!c;[Lclient!mi;I)V")
	@Override
	public void method4299(@OriginalArg(0) Model[] arg0, @OriginalArg(1) ParticleList arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) BoundingBoxPolygon[] arg3, @OriginalArg(4) int arg4) {
		this.method4307(arg0, arg2, arg3, arg4);
		this.render(arg1);
	}

	@OriginalMember(owner = "client!fd", name = "h", descriptor = "()V")
	@Override
	public void method4247() {
		// 592HD: the last thing before the frame is presented, which is the
		// only point that sees the world, the interface and any composition a
		// component asked for. Off rows make this a no-op with no GL call.
		this.hdPostFrame();
		try {
			this.anOpenGL1.swapBuffers();
		} catch (@Pc(8) Exception local8) {
		}
	}

	/**
	 * 592HD seam: one frame's screen-space pass, resolved and applied to the
	 * finished frame just before it is presented.
	 *
	 * <p>This is the frame's last pixel work, so it is the only place that sees
	 * exactly what a screenshot would. Returns false, having issued nothing,
	 * when no row is on or the program could not compile; the presented frame
	 * is then the stock one.
	 *
	 * @return true when the pass was applied to this frame
	 */
	public boolean hdPostFrame() {
		this.framebufferCompositor.compositeEffects();
		if (this.aCanvas2 == null) {
			return false;
		}
		return this.hdPost.apply(0, 0, this.aCanvas2.getWidth(), this.aCanvas2.getHeight(),
			HdPostParams.fromSettings());
	}

	/** How many frames the screen-space pass has been applied to. */
	public long hdPostDraws() {
		return this.hdPost.frames();
	}

	/** One line: has the frame-end screen-space pass run at all. */
	public String hdPostStats() {
		return "hd post: frames=" + this.hdPost.frames();
	}

	/**
	 * What this toolkit's caches claim the fixed-function state is, in the shape
	 * {@link HdGlState} compares against a real read-back.
	 *
	 * <p>The caches exist so a setter that is asked for the value GL already has
	 * issues no call; that only works while they are true. This is the reading a
	 * frame boundary is checked with: {@code HdGlState.actual().differences(
	 * toolkit.hdGlState())} is empty exactly when no pass has moved GL behind the
	 * cache's back.
	 */
	public HdGlState hdGlState() {
		int blendSrc;
		int blendDst;
		// The same factors method1958 uploads for each mode, in the same order.
		if (this.anInt2316 == 1) {
			blendSrc = 770;
			blendDst = 771;
		} else if (this.anInt2316 == 2) {
			blendSrc = 1;
			blendDst = 1;
		} else if (this.anInt2316 == 3) {
			blendSrc = 774;
			blendDst = 1;
		} else {
			blendSrc = 0;
			blendDst = 0;
		}
		// Every unit, not just the active one: a pass binds on a numbered unit
		// and restores the active unit, so a stale binding there is only visible
		// from that unit's own index. Both targets are reported, because the
		// cache holds a Class6 and its target - a cube the cache still names on
		// a unit is a different claim from that unit being empty.
		int[] textures = new int[HdGlState.UNITS];
		int[] cubes = new int[HdGlState.UNITS];
		if (this.aClass6Array1 != null) {
			for (int unit = 0; unit < Math.min(HdGlState.UNITS, this.aClass6Array1.length); unit++) {
				Class6 bound = this.aClass6Array1[unit];
				if (bound == null) {
					continue;
				}
				if (bound.anInt5441 == 34067) {
					cubes[unit] = bound.method4186();
				} else {
					textures[unit] = bound.method4186();
				}
			}
		}
		if (this.hdShadowTextureName != 0) {
			textures[HdShaderSource.SAMPLER_UNIT_SHADOW] = this.hdShadowTextureName;
		}
		return HdGlState.cached(blendSrc, blendDst, this.aBoolean216, this.hdAlphaFunc,
			this.aBoolean203, this.hdDepthFunc,
			this.aBoolean200 && this.depthBufferingEnabled,
			// aBoolean195 records that the all-on mask was set, not that colour
			// writes are off - an unset cache claims nothing.
			this.aBoolean195 ? 1 : HdGlState.UNKNOWN, this.anInt2301, textures, cubes,
			hdBoundProgram());
	}

	/**
	 * The program an HD pass has bound right now, or 0 for fixed function.
	 *
	 * <p>Only two owners bind a program and keep it: the lighting pass and the
	 * water pass, and the lighting gate refuses while water owns the draw, so at
	 * most one of them claims. The sky and screen-space passes save the previous
	 * program and put it back themselves, so they never appear here. Fixed
	 * function is 0 rather than {@link HdGlState#UNKNOWN}: before any pass has
	 * bound, and after every pass has unbound, zero is exactly what GL has.
	 */
	private int hdBoundProgram() {
		int lighting = this.hdLighting.programName();
		return lighting != 0 ? lighting : this.hdWater.programName();
	}

	@OriginalMember(owner = "client!fd", name = "GA", descriptor = "(IIII)V")
	@Override
	public void GA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		if (!this.aBoolean192) {
			throw new RuntimeException("");
		}
		this.anInt2288 = arg0;
		this.anInt2317 = arg2;
		this.anInt2287 = arg1;
		this.anInt2293 = arg3;
		// A water layer's own params: the stock effect re-binds its ARB program
		// here, which would sit on top of the HD program. Only the stock path
		// gets to do that.
		if (!this.hdWaterActive()) {
			this.effectManager.waterEffect.method5380();
		}
		this.updateFog();
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(I)V")
	@Override
	public void linkThreads(@OriginalArg(0) int threadId) {
		this.method1896();
	}

	@OriginalMember(owner = "client!fd", name = "v", descriptor = "(I)V")
	private void method1952() {
		OpenGL.glLoadIdentity();
		OpenGL.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		OpenGL.glMultMatrixf(this.aClass31_Sub1_4.method5161(), 0);
		if (this.aBoolean192 && !this.hdWaterActive()) {
			this.effectManager.waterEffect.method5380();
		}
		this.method1914();
		this.method1903();
	}

	@OriginalMember(owner = "client!fd", name = "k", descriptor = "(Z)V")
	private void method1953() {
		this.aFloat68 = this.anInt2296 - this.anInt2297;
		this.aFloat62 = this.anInt2318 - this.anInt2297;
		this.aFloat64 = this.anInt2292 - this.anInt2295;
		this.aFloat75 = this.clipLeft - this.anInt2295;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZIII)V")
	public void method1954(@OriginalArg(2) int arg0, @OriginalArg(3) int arg1) {
		OpenGL.glTexEnvi(8960, arg1 + 34184, arg0);
		OpenGL.glTexEnvi(8960, arg1 + 34200, 770);
	}

	@OriginalMember(owner = "client!fd", name = "IA", descriptor = "()I")
	@Override
	public int IA() {
		return this.renderBuffersSize + this.textureBuffersSize + this.vboBuffersSize;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(II)I")
	@Override
	public int compareFunctionMasks(@OriginalArg(0) int maskA, @OriginalArg(1) int maskB) {
		return maskB ^ maskA & maskB;
	}

	@OriginalMember(owner = "client!fd", name = "ia", descriptor = "(II)V")
	@Override
	public void ia(@OriginalArg(0) int near, @OriginalArg(1) int far) {
		int effectiveFar = FogDistance.scaleDistance(far);
		if (near == this.zNear && far == this.sceneFarPlane && effectiveFar == this.anInt2312) {
			return;
		}
		this.zNear = near;
		this.sceneFarPlane = far;
		this.anInt2312 = effectiveFar;
		this.method1923();
		this.updateFog();
		if (this.anInt2319 == 3) {
			this.method1933();
		} else if (this.anInt2319 == 2) {
			this.method1907();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIZ[BI)Lclient!bk;")
	public ArrayBufferGL method1955(@OriginalArg(1) int arg0, @OriginalArg(2) boolean arg1, @OriginalArg(3) byte[] arg2) {
		return (ArrayBufferGL) (this.aBoolean205 && (!arg1 || this.shouldUseArbVbo) ? new GlVboArrayBuffer(this, 5123, arg2, arg0, arg1) : new Class64_Sub1(this, 5123, arg2, arg0));
	}

	@OriginalMember(owner = "client!fd", name = "v", descriptor = "()V")
	@Override
	public void method4281() {
		if (!this.aBoolean196 || this.anInt2240 <= 0 || this.anInt2138 <= 0) {
			return;
		}
		@Pc(19) int local19 = this.clipLeft;
		@Pc(22) int local22 = this.anInt2292;
		@Pc(25) int local25 = this.anInt2318;
		@Pc(28) int local28 = this.anInt2296;
		this.e();
		OpenGL.glReadBuffer(1028);
		OpenGL.glDrawBuffer(1029);
		this.method1898();
		this.method1918(false);
		this.method1906(false);
		this.method1951(false);
		this.method1911(false);
		this.setActiveTexture(null);
		this.method1957(-2);
		this.method1900(1);
		this.method1958(0);
		OpenGL.glMatrixMode(5889);
		OpenGL.glLoadIdentity();
		OpenGL.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, -1.0D, 1.0D);
		OpenGL.glMatrixMode(5888);
		OpenGL.glLoadIdentity();
		OpenGL.glRasterPos2i(0, 0);
		OpenGL.glCopyPixels(0, 0, this.anInt2240, this.anInt2138, 6144);
		OpenGL.glFlush();
		OpenGL.glReadBuffer(1029);
		OpenGL.glDrawBuffer(1029);
		this.OA(local19, local25, local22, local28);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(FFF)V")
	@Override
	public void setBloomParams(@OriginalArg(0) float whitePoint, @OriginalArg(1) float arg1, @OriginalArg(2) float arg2) {
		CubeMapSpecularEffect.aFloat108 = arg1;
		Class4_Sub1_Sub2.aFloat7 = whitePoint;
		MusicPatchInfo.aFloat215 = arg2;
	}

	@OriginalMember(owner = "client!fd", name = "h", descriptor = "(I)V")
	@Override
	public void setTextureSize(@OriginalArg(0) int size) {
		if (size < 128 || size > 1024) {
			throw new IllegalArgumentException();
		}
		this.size = size;
		this.textureCache.method1550();
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "()Z")
	@Override
	public boolean hardShadow() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "c", descriptor = "(Lclient!cp;B)V")
	public void method1956(@OriginalArg(0) GlRenderTarget arg0) {
		if (this.anInt2277 >= 3) {
			throw new RuntimeException();
		}
		if (this.anInt2277 >= 0) {
			this.drawTargetStack[this.anInt2277].method3604();
		}
		this.anInterface3_1 = this.drawTargetStack[++this.anInt2277] = arg0;
		this.anInterface3_1.method3605();
	}

	@OriginalMember(owner = "client!fd", name = "YA", descriptor = "()I")
	@Override
	public int YA() {
		return this.anInt2312;
	}

	@OriginalMember(owner = "client!fd", name = "e", descriptor = "(IIIIII)V")
	@Override
	public void outlineRect(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int height, @OriginalArg(4) int colour, @OriginalArg(5) int mode) {
		@Pc(8) float local8 = (float) x + 0.35F;
		@Pc(13) float local13 = (float) y + 0.35F;
		@Pc(20) float local20 = (float) width + local8 - 1.0F;
		@Pc(27) float local27 = (float) height + local13 - 1.0F;
		this.method1977();
		this.method1958(mode);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		if (this.arbMultisample) {
			OpenGL.glDisable(32925);
		}
		OpenGL.glBegin(2);
		OpenGL.glVertex2f(local8, local13);
		OpenGL.glVertex2f(local8, local27);
		OpenGL.glVertex2f(local20, local27);
		OpenGL.glVertex2f(local20, local13);
		OpenGL.glEnd();
		if (this.arbMultisample) {
			OpenGL.glEnable(32925);
		}
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(IIIIII)V")
	@Override
	public void line(@OriginalArg(0) int x1, @OriginalArg(1) int y1, @OriginalArg(2) int x2, @OriginalArg(3) int y2, @OriginalArg(4) int colour, @OriginalArg(5) int mode) {
		this.method1977();
		this.method1958(mode);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glBegin(1);
		OpenGL.glVertex2f((float) x1 + 0.35F, (float) y1 + 0.35F);
		OpenGL.glVertex2f((float) x2 + 0.35F, (float) y2 + 0.35F);
		OpenGL.glEnd();
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(ZI)V")
	public void method1957(@OriginalArg(1) int arg0) {
		this.method1938(arg0, true);
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "(II)V")
	public void method1958(@OriginalArg(1) int arg0) {
		if (this.anInt2304 == arg0) {
			return;
		}
		@Pc(22) boolean local22;
		@Pc(20) byte local20;
		if (arg0 == 1) {
			local22 = true;
			local20 = 1;
		} else if (arg0 == 2) {
			local22 = false;
			local20 = 2;
		} else if (arg0 == 128) {
			local20 = 3;
			local22 = true;
		} else {
			local20 = 0;
			local22 = false;
		}
		if (!this.aBoolean195) {
			OpenGL.glColorMask(true, true, true, true);
			this.aBoolean195 = true;
		}
		if (local22 != this.aBoolean216) {
			if (local22) {
				OpenGL.glEnable(3008);
			} else {
				OpenGL.glDisable(3008);
			}
			this.aBoolean216 = local22;
		}
		if (local20 != this.anInt2316) {
			if (local20 == 1) {
				OpenGL.glEnable(3042);
				OpenGL.glBlendFunc(770, 771);
			} else if (local20 == 2) {
				OpenGL.glEnable(3042);
				OpenGL.glBlendFunc(1, 1);
			} else if (local20 == 3) {
				OpenGL.glEnable(3042);
				OpenGL.glBlendFunc(774, 1);
			} else {
				OpenGL.glDisable(3042);
			}
			this.anInt2316 = local20;
		}
		this.anInt2320 &= 0xFFFFFFE3;
		this.anInt2304 = arg0;
	}

	@OriginalMember(owner = "client!fd", name = "ca", descriptor = "(ILclient!ma;II)V")
	@Override
	public void ca(@OriginalArg(0) int colour, @OriginalArg(1) ComponentSurface clippingMask, @OriginalArg(2) int maskX, @OriginalArg(3) int maskY) {
		@Pc(6) Class145_Sub1 local6 = (Class145_Sub1) clippingMask;
		@Pc(9) OpenGl2DImageTexture local9 = local6.aClass6_Sub1_Sub1_5;
		this.method1930();
		this.setActiveTexture(local6.aClass6_Sub1_Sub1_5);
		this.method1958(1);
		this.method1912(7681, 8448);
		this.method1945(0, 768, 34167);
		@Pc(39) float local39 = local9.s / (float) local9.singleWidth;
		@Pc(46) float local46 = local9.captureScreenRegion / (float) local9.singleLength;
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glBegin(7);
		OpenGL.glTexCoord2f((float) (this.clipLeft - maskX) * local39, (float) (this.anInt2318 - maskY) * local46);
		OpenGL.glVertex2i(this.clipLeft, this.anInt2318);
		OpenGL.glTexCoord2f(local39 * (float) (this.clipLeft - maskX), (float) (this.anInt2296 - maskY) * local46);
		OpenGL.glVertex2i(this.clipLeft, this.anInt2296);
		OpenGL.glTexCoord2f(local39 * (float) (this.anInt2292 - maskX), local46 * (float) (this.anInt2296 - maskY));
		OpenGL.glVertex2i(this.anInt2292, this.anInt2296);
		OpenGL.glTexCoord2f((float) (this.anInt2292 - maskX) * local39, (float) (this.anInt2318 - maskY) * local46);
		OpenGL.glVertex2i(this.anInt2292, this.anInt2318);
		OpenGL.glEnd();
		this.method1945(0, 768, 5890);
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "(B)V")
	private void updateFog() {
		@Pc(23) int local23;
		if (this.aBoolean192) {
			if (this.hdWaterActive()) {
				// The 592HD water fragment applies the atmospheric band itself.
				// The stock water mode's 0..1 fog is for its own ARB program and
				// would flatten every HD water pixel to one fog colour, so while
				// the HD program owns the draw the scene's band and colour are
				// the ones GL carries - exactly the ones the rest of the frame
				// fades into. Off, the stock values below run untouched.
				OpenGL.glFogf(2915, this.aFloat59);
				OpenGL.glFogf(2916, this.aFloat76);
				local23 = this.anInt2302;
			} else {
				OpenGL.glFogf(2915, 0.0F);
				OpenGL.glFogf(2916, 1.0F);
				// Underwater fog is not the scene's fog colour, so it carries its
				// own dimming; the band has to fade into night under water too.
				local23 = hdDayNight().fogColour(this.anInt2287);
			}
		} else {
			this.aFloat76 = (float) (this.anInt2312 - this.anInt2282) - this.aFloat57;
			this.aFloat59 = this.aFloat76 - (float) this.anInt2315 * this.aFloat71;
			if ((float) this.zNear > this.aFloat59) {
				this.aFloat59 = this.zNear;
			}
			OpenGL.glFogf(2915, this.aFloat59);
			OpenGL.glFogf(2916, this.aFloat76);
			local23 = this.anInt2302;
		}
		Class4_Sub41.aFloatArray30[1] = (float) (local23 & 0xFF00) / 65280.0F;
		Class4_Sub41.aFloatArray30[0] = (float) (local23 & 0xFF0000) / 1.671168E7F;
		Class4_Sub41.aFloatArray30[2] = (float) (local23 & 0xFF) / 255.0F;
		OpenGL.glFogfv(2918, Class4_Sub41.aFloatArray30, 0);
		if (this.aBoolean192) {
			this.effectManager.waterEffect.method5379();
		}
	}

	/**
	 * Re-applies the far plane and fog state after user fog-distance
	 * changes. Called from the client tick only while the smoothed fog level
	 * is moving.
	 */
	@Override
	public void refreshFog() {
		if (!this.aBoolean192) {
			this.ia(this.zNear, this.sceneFarPlane);
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(II[BZI)Lclient!vd;")
	public ElementsBufferGL method1960(@OriginalArg(1) int arg0, @OriginalArg(2) byte[] arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) int arg3) {
		return (ElementsBufferGL) (this.aBoolean205 && (!arg2 || this.shouldUseArbVbo) ? new GlVboElementsArrayBuffer(this, arg3, arg1, arg0, arg2) : new GlElementsArrayBuffer(this, arg3, arg1, arg0));
	}

	@OriginalMember(owner = "client!fd", name = "l", descriptor = "(Z)V")
	private void method1961() {
		Class4_Sub41.aFloatArray30[2] = this.sunBrightness * this.aFloat74;
		Class4_Sub41.aFloatArray30[1] = this.aFloat58 * this.sunBrightness;
		Class4_Sub41.aFloatArray30[3] = 1.0F;
		Class4_Sub41.aFloatArray30[0] = this.aFloat72 * this.sunBrightness;
		OpenGL.glLightModelfv(2899, Class4_Sub41.aFloatArray30, 0);
	}

	@OriginalMember(owner = "client!fd", name = "h", descriptor = "(II)I")
	public int method1962(@OriginalArg(1) int arg0) {
		if (arg0 == 1) {
			return 7681;
		} else if (arg0 == 0) {
			return 8448;
		} else if (arg0 == 2) {
			return 34165;
		} else if (arg0 == 3) {
			return 260;
		} else if (arg0 == 4) {
			return 34023;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "([Lclient!t;Lclient!c;[Lclient!mi;I)V")
	@Override
	public void method4307(@OriginalArg(0) Model[] arg0, @OriginalArg(1) Ground arg1, @OriginalArg(2) BoundingBoxPolygon[] arg2, @OriginalArg(3) int arg3) {
		for (@Pc(7) int local7 = 0; local7 < arg0.length; local7++) {
			if (arg0[local7] != null) {
				arg0[local7].render(arg1, arg2 == null ? null : arg2[local7], arg3);
			}
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!gt;[Lclient!bb;Z)Lclient!la;")
	@Override
	public AbstractFontRenderer createFontRenderer(@OriginalArg(0) com.jagex.game.runetek5.graphics.font.FontMetrics metrics, @OriginalArg(1) SpriteType[] image) {
		return new GlFont(this, metrics, image, true);
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "()V")
	@Override
	public void method4236() {
		if (this.aClass4_Sub10_Sub1_1 != null && this.aClass4_Sub10_Sub1_1.method2003()) {
			this.framebufferCompositor.method5529(this.aClass4_Sub10_Sub1_1);
			this.textureCache.method1550();
		}
	}

	@OriginalMember(owner = "client!fd", name = "B", descriptor = "(F)V")
	@Override
	public void B(@OriginalArg(0) float brightness) {
		// The ambient level the world is lit to; the hour scales it, and the
		// same field feeds GL's light model and the stock vertex lighting.
		@Pc(1) float ambient = brightness * hdDayNight().ambient();
		if (this.sunBrightness != ambient) {
			this.sunBrightness = ambient;
			this.method1961();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Ljava/awt/Canvas;)V")
	@Override
	public void method4249(@OriginalArg(0) Canvas arg0) {
		if (this.aCanvas3 == arg0) {
			throw new RuntimeException();
		} else if (!this.aHashtable2.containsKey(arg0)) {
			if (!arg0.isShowing()) {
				throw new RuntimeException();
			}
			try {
				@Pc(26) Class local26 = Class.forName("java.awt.Canvas");
				@Pc(36) Method local36 = local26.getMethod("setIgnoreRepaint", Boolean.TYPE);
				local36.invoke(arg0, Boolean.TRUE);
			} catch (@Pc(48) Exception local48) {
			}
			@Pc(53) long local53 = this.anOpenGL1.prepareSurface(arg0);
			if (local53 == -1L) {
				throw new RuntimeException();
			}
			this.aHashtable2.put(arg0, Long.valueOf(local53));
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(BFFF)V")
	private void method1963(@OriginalArg(1) float arg0, @OriginalArg(2) float arg1, @OriginalArg(3) float arg2) {
		OpenGL.glMatrixMode(5890);
		if (this.aBoolean206) {
			OpenGL.glLoadIdentity();
		}
		OpenGL.glTranslatef(arg1, arg0, arg2);
		OpenGL.glMatrixMode(5888);
		this.aBoolean206 = true;
	}

	@OriginalMember(owner = "client!fd", name = "UA", descriptor = "(III)V")
	@Override
	public void UA(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		// The hour dims the fog the world fades into. Storing the dimmed value
		// is what makes the early-out below correct as the clock moves: the
		// caller's colour is unchanged at midnight, the effective one is not,
		// so the band is re-uploaded. The sky reads the same stored value.
		@Pc(1) int colour = hdDayNight().fogColour(arg0);
		if (this.anInt2302 == colour && arg1 == this.anInt2315 && arg2 == this.anInt2282) {
			return;
		}
		this.anInt2282 = arg2;
		this.anInt2302 = colour;
		this.anInt2315 = arg1;
		if (!this.aBoolean192) {
			this.updateFog();
			this.method1968();
		}
	}

	@OriginalMember(owner = "client!fd", name = "DA", descriptor = "(IIIIII[BII)V")
	@Override
	public void DA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int colour, @OriginalArg(5) int envColour, @OriginalArg(6) byte[] alphaData, @OriginalArg(7) int maskWidth, @OriginalArg(8) int blendMode) {
		@Pc(41) float local41;
		@Pc(45) float local45;
		if (this.worldMapTexture == null || this.worldMapTexture.anInt5466 < arg2 || this.worldMapTexture.anInt5458 < arg3) {
			this.worldMapTexture = Class28.method671(alphaData, this, arg2, arg3);
			this.worldMapTexture.method4196(false, false);
			local41 = this.worldMapTexture.captureScreenRegion;
			local45 = this.worldMapTexture.s;
		} else {
			this.worldMapTexture.method4191(arg2, alphaData, arg3, 6406, false);
			local45 = (float) arg2 * this.worldMapTexture.s / (float) this.worldMapTexture.anInt5466;
			local41 = (float) arg3 * this.worldMapTexture.captureScreenRegion / (float) this.worldMapTexture.anInt5458;
		}
		this.method1930();
		this.setActiveTexture(this.worldMapTexture);
		this.method1958(blendMode);
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		this.method1901(envColour);
		this.method1912(34165, 34165);
		this.method1945(0, 768, 34166);
		this.method1945(2, 770, 5890);
		this.method1954(34166, 0);
		this.method1954(5890, 2);
		@Pc(147) float local147 = (float) x;
		@Pc(150) float local150 = (float) y;
		@Pc(155) float local155 = local147 + (float) arg2;
		OpenGL.glBegin(7);
		@Pc(162) float local162 = (float) arg3 + local150;
		OpenGL.glTexCoord2f(0.0F, 0.0F);
		OpenGL.glVertex2f(local147, local150);
		OpenGL.glTexCoord2f(0.0F, local45);
		OpenGL.glVertex2f(local147, local162);
		OpenGL.glTexCoord2f(local41, local45);
		OpenGL.glVertex2f(local155, local162);
		OpenGL.glTexCoord2f(local41, 0.0F);
		OpenGL.glVertex2f(local155, local150);
		OpenGL.glEnd();
		this.method1945(0, 768, 5890);
		this.method1945(2, 770, 34166);
		this.method1954(5890, 0);
		this.method1954(34166, 2);
	}

	@OriginalMember(owner = "client!fd", name = "MA", descriptor = "(I)V")
	@Override
	public void setOffsets(@OriginalArg(0) int arg0) {
		this.shadowScaleShift = 0;
		while (arg0 > 1) {
			arg0 >>= 0x1;
			this.shadowScaleShift++;
		}
		this.shadowScale = 0x1 << this.shadowScaleShift;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIIF)Lclient!vp;")
	@Override
	public PointLight createPointLight(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int z, @OriginalArg(3) int radius, @OriginalArg(4) int range, @OriginalArg(5) float intensity) {
		return new GpuPointLight(x, y, z, radius, range, intensity);
	}

	@OriginalMember(owner = "client!fd", name = "TA", descriptor = "(IIIII)V")
	@Override
	public void TA(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int height, @OriginalArg(3) int colour, @OriginalArg(4) int blendMode) {
		this.method1977();
		this.method1958(blendMode);
		@Pc(15) float local15 = (float) x + 0.35F;
		@Pc(20) float local20 = (float) y + 0.35F;
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		OpenGL.glBegin(1);
		OpenGL.glVertex2f(local15, local20);
		OpenGL.glVertex2f(local15, local20 + (float) height);
		OpenGL.glEnd();
	}

	@OriginalMember(owner = "client!fd", name = "h", descriptor = "(B)V")
	private void method1964() {
		this.aClass31_Sub1_4 = new JavaMatrix();
		this.aClass31_Sub1_3 = new JavaMatrix();
		// The lit program samples six units (base, shadow, reflection and the
		// ground atlas' three). This cache is per image unit, and the stock count
		// is the fixed-function combiner count - four on most drivers - which is
		// smaller than the image-unit space glActiveTexture addresses, so size it
		// to the highest sampler the shader names.
		this.aClass6Array1 = new Class6[Math.max(this.anInt2306,
			HdShaderSource.SAMPLER_UNIT_TILE_MATERIALS + 1)];
		this.texture2d = new OpenGl2DTexture(this, 3553, 6408, 1, 1);
		this.aClass6_Sub1_2 = new OpenGl2DTexture(this, 3553, 6408, 1, 1);
		this.aClass6_Sub1_1 = new OpenGl2DTexture(this, 3553, 6408, 1, 1);
		this.copyTarget1 = new GlModel(this);
		this.aClass110_Sub1_3 = new GlModel(this);
		this.aClass110_Sub1_9 = new GlModel(this);
		this.aClass110_Sub1_2 = new GlModel(this);
		this.aClass110_Sub1_8 = new GlModel(this);
		this.aClass110_Sub1_1 = new GlModel(this);
		this.aClass110_Sub1_5 = new GlModel(this);
		this.aClass110_Sub1_10 = new GlModel(this);
		this.aClass110_Sub1_6 = new GlModel(this);
		this.aClass110_Sub1_4 = new GlModel(this);
		if (this.extFramebufferObject) {
			this.aClass150_2 = new GlFramebuffer(this);
			this.aClass150_1 = new GlFramebuffer(this);
		}
	}

	@OriginalMember(owner = "client!fd", name = "b", descriptor = "(IIIII)V")
	@Override
	public void b(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int width, @OriginalArg(3) int colour, @OriginalArg(4) int blendMode) {
		this.method1977();
		this.method1958(blendMode);
		@Pc(15) float local15 = (float) x + 0.35F;
		OpenGL.glColor4ub((byte) (colour >> 16), (byte) (colour >> 8), (byte) colour, (byte) (colour >> 24));
		@Pc(35) float local35 = (float) y + 0.35F;
		OpenGL.glBegin(1);
		OpenGL.glVertex2f(local15, local35);
		OpenGL.glVertex2f((float) width + local15, local35);
		OpenGL.glEnd();
	}

	@OriginalMember(owner = "client!fd", name = "i", descriptor = "(B)V")
	public void method1965() {
		OpenGL.glPushMatrix();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ZZ)V")
	public void method1966(@OriginalArg(0) boolean arg0) {
		if (this.aBoolean202 != arg0) {
			this.aBoolean202 = arg0;
			this.method1919();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIILclient!bk;B)V")
	public void method1967(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) ArrayBufferGL arg2) {
		@Pc(13) int local13 = arg2.getStride();
		@Pc(20) int local20 = arg0 * this.method1897(local13);
		this.method1970(arg2);
		OpenGL.glDrawElements(4, arg1, local13, (long) local20 + arg2.getAddress());
	}

	@OriginalMember(owner = "client!fd", name = "d", descriptor = "(Z)V")
	@Override
	public void method4309(@OriginalArg(0) boolean enabled) {
		this.depthBufferingEnabled = enabled;
		this.method1976();
	}

	@OriginalMember(owner = "client!fd", name = "m", descriptor = "(Z)V")
	private void method1968() {
		if (fogActive()) {
			OpenGL.glEnable(2912);
		} else {
			OpenGL.glDisable(2912);
		}
	}

	@OriginalMember(owner = "client!fd", name = "JA", descriptor = "(IIIIII)Z")
	@Override
	public boolean drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5) {
		@Pc(28) float local28 = (float) arg2 * this.aClass31_Sub1_4.aFloat199 + (float) arg1 * this.aClass31_Sub1_4.aFloat203 + (float) arg0 * this.aClass31_Sub1_4.aFloat206 + this.aClass31_Sub1_4.aFloat198;
		if (local28 < 1.0F) {
			local28 = 1.0F;
		}
		@Pc(59) float local59 = this.aClass31_Sub1_4.aFloat198 + this.aClass31_Sub1_4.aFloat206 * (float) arg3 + this.aClass31_Sub1_4.aFloat203 * (float) arg4 + this.aClass31_Sub1_4.aFloat199 * (float) arg5;
		if (local59 < 1.0F) {
			local59 = 1.0F;
		}
		if ((float) this.zNear > local28 && (float) this.zNear > local59 || !(!(local28 > (float) this.anInt2312) || !(local59 > (float) this.anInt2312))) {
			return false;
		}
		@Pc(123) int local123 = (int) ((float) this.anInt2309 * (this.aClass31_Sub1_4.aFloat202 * (float) arg0 + (float) arg1 * this.aClass31_Sub1_4.aFloat207 + (float) arg2 * this.aClass31_Sub1_4.aFloat208 + this.aClass31_Sub1_4.aFloat209) / local28);
		@Pc(155) int local155 = (int) ((float) this.anInt2309 * (this.aClass31_Sub1_4.aFloat202 * (float) arg3 + (float) arg4 * this.aClass31_Sub1_4.aFloat207 + this.aClass31_Sub1_4.aFloat208 * (float) arg5 + this.aClass31_Sub1_4.aFloat209) / local59);
		if ((float) local123 < this.aFloat75 && (float) local155 < this.aFloat75 || this.aFloat64 < (float) local123 && this.aFloat64 < (float) local155) {
			return false;
		} else {
			@Pc(213) int local213 = (int) ((this.aClass31_Sub1_4.aFloat201 * (float) arg2 + this.aClass31_Sub1_4.aFloat200 * (float) arg0 + this.aClass31_Sub1_4.aFloat204 * (float) arg1 + this.aClass31_Sub1_4.aFloat205) * (float) this.anInt2294 / local28);
			@Pc(245) int local245 = (int) ((float) this.anInt2294 * (this.aClass31_Sub1_4.aFloat201 * (float) arg5 + (float) arg3 * this.aClass31_Sub1_4.aFloat200 + this.aClass31_Sub1_4.aFloat204 * (float) arg4 + this.aClass31_Sub1_4.aFloat205) / local59);
			return (!((float) local213 < this.aFloat62) || !(this.aFloat62 > (float) local245)) && (!((float) local213 > this.aFloat68) || !((float) local245 > this.aFloat68));
		}
	}

	@OriginalMember(owner = "client!fd", name = "w", descriptor = "(I)V")
	private void prepareForRendering() {
		this.method1957(-2);
		this.hdClearTextureUnits();
		this.method1912(8448, 8448);
		this.method1945(2, 770, 34168);
		this.method1917();
		this.anInt2304 = 1;
		OpenGL.glEnable(3042);
		OpenGL.glBlendFunc(770, 771);
		this.anInt2316 = 1;
		OpenGL.glEnable(3008);
		OpenGL.glAlphaFunc(516, 0.0F);
		// The function goes with the flag: the cut-out pass changes both and must
		// put both back, and the toolkit can only check that if it knows what it
		// set.
		this.hdAlphaFunc = 516;
		this.aBoolean216 = true;
		OpenGL.glColorMask(true, true, true, true);
		this.aBoolean195 = true;
		this.method1918(true);
		this.method1906(true);
		this.method1951(true);
		this.method1911(true);
		this.method1898();
		this.anOpenGL1.setSwapInterval(0);
		OpenGL.glShadeModel(7425);
		OpenGL.glClearDepth(1.0F);
		OpenGL.glDepthFunc(515);
		this.hdDepthFunc = 515;
		OpenGL.glPolygonMode(1028, 6914);
		OpenGL.glEnable(2884);
		OpenGL.glCullFace(1029);
		OpenGL.glMatrixMode(5888);
		OpenGL.glLoadIdentity();
		OpenGL.glColorMaterial(1028, 5634);
		OpenGL.glEnable(2903);
		@Pc(125) float[] local125 = new float[] { 0.0F, 0.0F, 0.0F, 1.0F };
		for (@Pc(127) int local127 = 0; local127 < 8; local127++) {
			@Pc(133) int local133 = local127 + 16384;
			OpenGL.glLightfv(local133, 4608, local125, 0);
			OpenGL.glLightf(local133, 4615, 0.0F);
			OpenGL.glLightf(local133, 4616, 0.0F);
		}
		OpenGL.glEnable(16384);
		OpenGL.glEnable(16385);
		OpenGL.glFogf(2914, 0.95F);
		OpenGL.glFogi(2917, 9729);
		OpenGL.glHint(3156, 4353);
		this.sunColorRgb = this.anInt2302 = -1;
		this.e();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(ILclient!bk;)V")
	public void method1970(@OriginalArg(1) ArrayBufferGL arg0) {
		if (this.anInterface1_1 != arg0) {
			if (this.aBoolean205) {
				OpenGL.glBindBufferARB(34963, arg0.getId());
			}
			this.anInterface1_1 = arg0;
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IIIIIIIIII)V")
	@Override
	public void method4276() {
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!mc;)V")
	@Override
	public void render(@OriginalArg(0) ParticleList particleList) {
		this.particleRenderer.method916(particleList, this);
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!cp;I)V")
	public void method1971(@OriginalArg(0) GlRenderTarget arg0) {
		if (this.aBoolean204) {
			this.method1935(arg0);
			this.method1949(arg0);
		} else if (this.anInt2276 >= 0 && arg0 == this.readTargetStack[this.anInt2276]) {
			this.readTargetStack[this.anInt2276--] = null;
			arg0.method3603();
			if (this.anInt2276 >= 0) {
				this.anInterface3_2 = this.anInterface3_1 = this.readTargetStack[this.anInt2276];
				this.anInterface3_2.method3602();
			} else {
				this.anInterface3_2 = this.anInterface3_1 = null;
			}
		} else {
			throw new RuntimeException();
		}
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "([IIIII)Lclient!l;")
	@Override
	public NativeSprite method4265(@OriginalArg(0) int[] arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3) {
		return new SpriteGL(this, arg2, arg3, arg0, 0, arg1);
	}

	@OriginalMember(owner = "client!fd", name = "F", descriptor = "(IIII)[I")
	@Override
	public int[] F(@OriginalArg(0) int x, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		@Pc(10) int[] local10 = new int[arg2 * arg3];
		for (@Pc(12) int local12 = 0; local12 < arg3; local12++) {
			OpenGL.glReadPixelsi(x, this.anInt2138 - local12 - arg1, arg2, 1, 32993, this.pixelType, local10, local12 * arg2);
		}
		return local10;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(Lclient!bb;Z)Lclient!l;")
	@Override
	public NativeSprite generateSprite(@OriginalArg(0) SpriteType indexedImage) {
		@Pc(12) int[] local12 = new int[indexedImage.height * indexedImage.width];
		@Pc(14) int local14 = 0;
		@Pc(16) int local16 = 0;
		@Pc(21) int local21;
		@Pc(25) int local25;
		if (indexedImage.alpha == null) {
			for (local21 = 0; local21 < indexedImage.height; local21++) {
				for (local25 = 0; local25 < indexedImage.width; local25++) {
					@Pc(38) int local38 = indexedImage.pallete[indexedImage.pixels[local14++] & 0xFF];
					local12[local16++] = local38 == 0 ? 0 : local38 | 0xFF000000;
				}
			}
		} else {
			for (local21 = 0; local21 < indexedImage.height; local21++) {
				for (local25 = 0; local25 < indexedImage.width; local25++) {
					local12[local16++] = indexedImage.alpha[local14] << 24 | indexedImage.pallete[indexedImage.pixels[local14] & 0xFF];
					local14++;
				}
			}
		}
		@Pc(122) NativeSprite local122 = this.method4265(local12, indexedImage.width, indexedImage.width, indexedImage.height);
		local122.setOffsets(indexedImage.minX, indexedImage.minY, indexedImage.borderX, indexedImage.borderY);
		return local122;
	}

	@OriginalMember(owner = "client!fd", name = "m", descriptor = "()Z")
	@Override
	public boolean method4258() {
		return true;
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IJ)V")
	public synchronized void method1973(@OriginalArg(1) long arg0) {
		@Pc(7) Country local7 = new Country();
		local7.aLong224 = arg0;
		this.aClass183_13.method4137(local7);
	}

	@OriginalMember(owner = "client!fd", name = "H", descriptor = "(IIII)V")
	@Override
	public void H(@OriginalArg(0) int centerX, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt2294 = arg3;
		this.anInt2297 = arg1;
		this.anInt2309 = arg2;
		this.anInt2295 = centerX;
		this.method1923();
		this.method1953();
		if (this.anInt2319 == 3) {
			this.method1933();
		} else if (this.anInt2319 == 2) {
			this.method1907();
		}
	}

	@OriginalMember(owner = "client!fd", name = "k", descriptor = "()Z")
	@Override
	public boolean method4252() {
		return this.aClass4_Sub10_Sub1_1 != null && (this.multiSamplesPreference <= 1 || this.aBoolean201);
	}

	@OriginalMember(owner = "client!fd", name = "x", descriptor = "(I)I")
	private int method1974() {
		@Pc(5) int local5 = 0;
		String glVendor = OpenGL.glGetString(7936);
		String glRenderer = OpenGL.glGetString(7937);
		this.aString26 = glVendor.toLowerCase();
		this.aString27 = glRenderer.toLowerCase();
		if (this.aString26.indexOf("microsoft") != -1) {
			local5 = 1;
		}
		if (this.aString26.indexOf("brian paul") != -1 || this.aString26.indexOf("mesa") != -1) {
			local5 |= 0x1;
		}
		@Pc(46) String local46 = OpenGL.glGetString(7938);
		// The context is current here, so this is where the driver strings the
		// displayfps overlay shows have to be captured.
		RendererInfo.record(local46, glVendor, glRenderer);
		@Pc(54) String[] local54 = CursorType.method2058(local46.replace('.', ' '), ' ');
		if (local54.length < 2) {
			local5 |= 0x4;
		} else {
			try {
				@Pc(70) int local70 = ParticleProducerType.method3113(local54[0]);
				@Pc(76) int local76 = ParticleProducerType.method3113(local54[1]);
				this.anInt2300 = local70 * 10 + local76;
			} catch (@Pc(85) NumberFormatException local85) {
				local5 |= 0x4;
			}
		}
		if (this.anInt2300 < 12) {
			local5 |= 0x2;
		}
		if (!this.anOpenGL1.isExtensionAvailable("GL_ARB_multitexture")) {
			local5 |= 0x8;
		}
		if (!this.anOpenGL1.isExtensionAvailable("GL_ARB_texture_env_combine")) {
			local5 |= 0x20;
		}
		@Pc(119) int[] local119 = new int[1];
		OpenGL.glGetIntegerv(34018, local119, 0);
		this.anInt2306 = local119[0];
		OpenGL.glGetIntegerv(34929, local119, 0);
		this.anInt2298 = local119[0];
		OpenGL.glGetIntegerv(34930, local119, 0);
		this.anInt2285 = local119[0];
		if (this.anInt2306 < 2 || this.anInt2298 < 2 || this.anInt2285 < 2) {
			local5 |= 0x10;
		}
		this.bigEndianess = Stream.c();
		this.anOpenGL1.arePbuffersAvailable();
		this.aBoolean205 = this.anOpenGL1.isExtensionAvailable("GL_ARB_vertex_buffer_object");
		this.arbMultisample = this.anOpenGL1.isExtensionAvailable("GL_ARB_multisample");
		this.aBoolean198 = this.anOpenGL1.isExtensionAvailable("GL_ARB_vertex_program");
		this.anOpenGL1.isExtensionAvailable("GL_ARB_fragment_program");
		this.aBoolean217 = this.anOpenGL1.isExtensionAvailable("GL_ARB_vertex_shader");
		this.aBoolean214 = this.anOpenGL1.isExtensionAvailable("GL_ARB_fragment_shader");
		this.aBoolean211 = this.anOpenGL1.isExtensionAvailable("GL_EXT_texture3D");
		this.arbRectangleTexturesSupported = this.anOpenGL1.isExtensionAvailable("GL_ARB_texture_rectangle");
		this.arbTextureCubeMap = this.anOpenGL1.isExtensionAvailable("GL_ARB_texture_cube_map");
		this.arbTextureFloat = this.anOpenGL1.isExtensionAvailable("GL_ARB_texture_float");
		this.framebufferObjectSupported = false;
		this.extFramebufferObject = this.anOpenGL1.isExtensionAvailable("GL_EXT_framebuffer_object");
		this.aBoolean204 = this.anOpenGL1.isExtensionAvailable("GL_EXT_framebuffer_blit");
		this.aBoolean209 = this.anOpenGL1.isExtensionAvailable("GL_EXT_framebuffer_multisample");
		this.aBoolean201 = this.aBoolean209 & this.aBoolean204;
		return local5 == 0 ? 0 : local5;
	}

	@OriginalMember(owner = "client!fd", name = "r", descriptor = "()V")
	@Override
	public void method4266() {
		this.method1911(true);
		OpenGL.glClear(256);
	}

	@OriginalMember(owner = "client!fd", name = "y", descriptor = "(I)V")
	private void method1975() {
		if (this.aFloat67 == (float) 0) {
			this.projectionMatrix[10] = this.aFloat69;
			this.projectionMatrix[14] = this.aFloat60;
		} else {
			@Pc(28) float local28 = this.aFloat63 / (this.aFloat63 + this.aFloat67);
			@Pc(32) float local32 = local28 * local28;
			@Pc(47) float local47 = -this.aFloat60 * (1.0F - local28) * (1.0F - local28) / this.aFloat67;
			this.projectionMatrix[14] = local32 * this.aFloat60;
			this.projectionMatrix[10] = this.aFloat69 + local47;
		}
		this.farClipDistance = (float) this.anInt2312 - this.aFloat67;
		this.projectedFarClipDistance = (this.projectionMatrix[14] - (float) this.anInt2312) / this.projectionMatrix[10];
	}

	@OriginalMember(owner = "client!fd", name = "z", descriptor = "()Z")
	@Override
	public boolean method4291() {
		return false;
	}

	@OriginalMember(owner = "client!fd", name = "C", descriptor = "()Z")
	@Override
	public boolean method4301() {
		return this.aClass4_Sub10_Sub1_1 != null && this.aClass4_Sub10_Sub1_1.method2003();
	}

	@OriginalMember(owner = "client!fd", name = "p", descriptor = "(I)V")
	@Override
	public void p(@OriginalArg(0) int colour) {
		this.method1958(0);
		OpenGL.glClearColor((float) (colour & 0xFF0000) / 1.671168E7F, (float) (colour & 0xFF00) / 65280.0F, (float) (colour & 0xFF) / 255.0F, (float) (colour >>> 24) / 255.0F);
		OpenGL.glClear(16384);
	}

	@OriginalMember(owner = "client!fd", name = "z", descriptor = "(I)V")
	private void method1976() {
		OpenGL.glDepthMask(this.aBoolean200 && this.depthBufferingEnabled);
	}

	@OriginalMember(owner = "client!fd", name = "j", descriptor = "(B)V")
	private void method1977() {
		if (this.anInt2320 == 1) {
			return;
		}
		this.method1932();
		this.method1918(false);
		this.method1906(false);
		this.method1951(false);
		this.method1911(false);
		this.setActiveTexture(null);
		this.method1957(-2);
		this.method1900(1);
		this.anInt2320 = 1;
	}

	@OriginalMember(owner = "client!fd", name = "g", descriptor = "(FF)V")
	@Override
	public void g(@OriginalArg(0) float arg0, @OriginalArg(1) float arg1) {
		if (this.aFloat63 == arg0 && this.aFloat67 == arg1) {
			return;
		}
		this.aFloat63 = arg0;
		this.aFloat67 = arg1;
		this.method1975();
		if (this.anInt2319 == 3) {
			this.method1933();
			return;
		}
		if (this.anInt2319 != 2) {
			return;
		}
		this.method1907();
	}

	@OriginalMember(owner = "client!fd", name = "a", descriptor = "(IBI)V")
	public synchronized void method1978(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(8) Class4_Sub37 local8 = new Class4_Sub37(arg1);
		local8.aLong224 = arg0;
		this.texturesAwaitingDeallocation.method4137(local8);
	}

@OriginalMember(owner = "client!fd", name = "Rd", descriptor = "Lclient!sp;")
	public static ModeWhere aClass219_2;
@OriginalMember(owner = "client!fd", name = "Ec", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_25 = new Class215(2, -1);
@OriginalMember(owner = "client!fd", name = "xd", descriptor = "[I")
	public static final int[] anIntArray159 = new int[] { 4, 2, 1, 1, 2, 2, 3, 1, 3, 3, 3, 2, 0 };
@OriginalMember(owner = "client!fd", name = "Jd", descriptor = "I")
	public static int activeChildCount = 0;
@OriginalMember(owner = "client!fd", name = "be", descriptor = "[I")
	public static final int[] anIntArray160 = new int[] { 2, 1, 1, 1, 2, 2, 2, 1, 3, 3, 3, 2, 0, 4, 0 };
@OriginalMember(owner = "client!fd", name = "c", descriptor = "(IIII)I")
	public static int method1972(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(7) int local7 = arg2 / arg0;
		@Pc(13) int local13 = arg0 - 1 & arg2;
		@Pc(17) int local17 = arg1 / arg0;
		@Pc(23) int local23 = arg1 & arg0 - 1;
		@Pc(28) int local28 = Static99.method1757(local7, local17);
		@Pc(35) int local35 = Static99.method1757(local7 + 1, local17);
		@Pc(42) int local42 = Static99.method1757(local7, local17 + 1);
		@Pc(51) int local51 = Static99.method1757(local7 + 1, local17 + 1);
		@Pc(58) int local58 = MusicPatchNode.method3450(local13, arg0, local28, local35);
		@Pc(65) int local65 = MusicPatchNode.method3450(local13, arg0, local42, local51);
		return MusicPatchNode.method3450(local23, arg0, local58, local65);
	}
}
