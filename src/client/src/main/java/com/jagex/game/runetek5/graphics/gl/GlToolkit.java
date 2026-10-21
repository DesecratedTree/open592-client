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
				@Pc(254) String local254 = System.getProperty("os.name").toLowerCase();
				if (!local254.startsWith("mac") && !local254.startsWith("darwin")) {
					System.loadLibrary("jawt");
				}
				// jaclib and jaggl are extracted from JS5 index 30 for the
				// running platform (Windows/Linux/macOS, 32- or 64-bit).
				NativeLibraries.load("jaclib");
				NativeLibraries.load("jaggl");
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
		@Pc(11) Class6 local11 = this.aClass6Array1[this.anInt2301];
		if (local11 != arg0) {
			if (arg0 == null) {
				OpenGL.glDisable(local11.anInt5441);
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
		if (this.anInt2314 != arg1) {
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
					this.effectManager.method5318(arg2, arg0, 3);
					this.setActiveTexture(local13);
					this.method1900(local19.anInt6934);
				} else {
					this.effectManager.method5318(arg2, arg0, local19.aByte89);
					this.effectManager.method5319(local19.anInt6933, local19.aByte88);
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
		@Pc(11) boolean local11 = this.sunColorRgb != arg0;
		if (local11 || arg1 != this.aFloat73 || arg2 != this.aFloat65) {
			this.aFloat65 = arg2;
			this.sunColorRgb = arg0;
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
		this.effectManager.method5318(false, false, 3);
		this.effectManager.waterEffect.method5380();
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
		OpenGL.glMultMatrixf(arg0.method5161(), 0);
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
		try {
			this.anOpenGL1.swapBuffers();
		} catch (@Pc(8) Exception local8) {
		}
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
		this.effectManager.waterEffect.method5380();
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
		if (this.aBoolean192) {
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
		if (near == this.zNear && far == this.anInt2312) {
			return;
		}
		this.zNear = near;
		this.anInt2312 = far;
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
			OpenGL.glFogf(2915, 0.0F);
			OpenGL.glFogf(2916, 1.0F);
			local23 = this.anInt2287;
		} else {
			this.aFloat76 = (float) (this.anInt2312 - this.anInt2282) - this.aFloat57;
			this.aFloat59 = this.aFloat76 - (float) this.anInt2315 * this.aFloat71;
			this.aFloat76 *= FogDistance.getScale();
			this.aFloat59 *= FogDistance.getScale();
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
	 * Re-pushes fog state after user fog-distance changes. Called from the
	 * client tick only while the smoothed fog level is moving.
	 */
	@Override
	public void refreshFog() {
		if (!this.aBoolean192) {
			this.updateFog();
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
		if (this.sunBrightness != brightness) {
			this.sunBrightness = brightness;
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
		if (this.anInt2302 == arg0 && arg1 == this.anInt2315 && arg2 == this.anInt2282) {
			return;
		}
		this.anInt2282 = arg2;
		this.anInt2302 = arg0;
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
		this.aClass6Array1 = new Class6[this.anInt2306];
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
		if (this.aBoolean197 && this.anInt2315 >= 0 | this.aBoolean192) {
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
		for (@Pc(14) int local14 = this.anInt2306 - 1; local14 >= 0; local14--) {
			this.method1936(local14);
			this.setActiveTexture(null);
			OpenGL.glTexEnvi(8960, 8704, 34160);
		}
		this.method1912(8448, 8448);
		this.method1945(2, 770, 34168);
		this.method1917();
		this.anInt2304 = 1;
		OpenGL.glEnable(3042);
		OpenGL.glBlendFunc(770, 771);
		this.anInt2316 = 1;
		OpenGL.glEnable(3008);
		OpenGL.glAlphaFunc(516, 0.0F);
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
		this.aString26 = OpenGL.glGetString(7936).toLowerCase();
		this.aString27 = OpenGL.glGetString(7937).toLowerCase();
		if (this.aString26.indexOf("microsoft") != -1) {
			local5 = 1;
		}
		if (this.aString26.indexOf("brian paul") != -1 || this.aString26.indexOf("mesa") != -1) {
			local5 |= 0x1;
		}
		@Pc(46) String local46 = OpenGL.glGetString(7938);
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
