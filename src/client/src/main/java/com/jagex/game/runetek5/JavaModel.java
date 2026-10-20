package com.jagex.game.runetek5;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.Class31_Sub2;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.JavaBillboardAttributes;
import com.jagex.game.runetek5.graphics.JavaBillboardFace;
import com.jagex.game.runetek5.graphics.model.Mesh;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.model.normal.FaceNormal;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.graphics.model.normal.VertexNormal;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.toolkit.opengl.effect.UnlitEffect;

import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.JavaModel;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class56;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.entity.PathingEntity;
@OriginalClass("client!sk")
public final class JavaModel extends Model {

	@OriginalMember(owner = "client!sk", name = "rc", descriptor = "[I")
	private static final int[] anIntArray483 = new int[4096];

	@OriginalMember(owner = "client!sk", name = "p", descriptor = "[I")
	private int[] anIntArray447;

	@OriginalMember(owner = "client!sk", name = "q", descriptor = "Lclient!sk;")
	private JavaModel copySrcModel4;

	@OriginalMember(owner = "client!sk", name = "r", descriptor = "S")
	private short maxZ;

	@OriginalMember(owner = "client!sk", name = "s", descriptor = "[I")
	private int[] anIntArray448;

	@OriginalMember(owner = "client!sk", name = "t", descriptor = "I")
	private int pivotY;

	@OriginalMember(owner = "client!sk", name = "u", descriptor = "I")
	private int functionMask;

	@OriginalMember(owner = "client!sk", name = "w", descriptor = "[I")
	private int[] anIntArray449;

	@OriginalMember(owner = "client!sk", name = "x", descriptor = "[Lclient!bu;")
	private EmissiveTriangle[] emitters;

	@OriginalMember(owner = "client!sk", name = "y", descriptor = "[Lclient!pf;")
	private EffectiveVertex[] effectors;

	@OriginalMember(owner = "client!sk", name = "z", descriptor = "Lclient!qv;")
	private Rasterizer rasterizer;

	@OriginalMember(owner = "client!sk", name = "A", descriptor = "S")
	private short radius;

	@OriginalMember(owner = "client!sk", name = "B", descriptor = "[S")
	private short[] faceA;

	@OriginalMember(owner = "client!sk", name = "C", descriptor = "I")
	private int pivotX;

	@OriginalMember(owner = "client!sk", name = "D", descriptor = "I")
	private int billboardCount;

	@OriginalMember(owner = "client!sk", name = "E", descriptor = "[Lclient!km;")
	private JavaBillboardFace[] billboardFaces;

	@OriginalMember(owner = "client!sk", name = "F", descriptor = "I")
	private int pivotZ;

	@OriginalMember(owner = "client!sk", name = "G", descriptor = "[I")
	private int[] anIntArray450;

	@OriginalMember(owner = "client!sk", name = "I", descriptor = "S")
	private short maxX;

	@OriginalMember(owner = "client!sk", name = "J", descriptor = "[I")
	private int[] anIntArray451;

	@OriginalMember(owner = "client!sk", name = "K", descriptor = "[S")
	private short[] aShortArray218;

	@OriginalMember(owner = "client!sk", name = "L", descriptor = "[[I")
	private int[][] anIntArrayArray51;

	@OriginalMember(owner = "client!sk", name = "M", descriptor = "S")
	private short minX;

	@OriginalMember(owner = "client!sk", name = "N", descriptor = "Lclient!cv;")
	private JavaThreadResource threadResource;

	@OriginalMember(owner = "client!sk", name = "O", descriptor = "[Lclient!wv;")
	private PointEntity[] aClass3_Sub1_Sub1Array2;

	@OriginalMember(owner = "client!sk", name = "P", descriptor = "Lclient!sk;")
	private JavaModel copyDstModel2;

	@OriginalMember(owner = "client!sk", name = "Q", descriptor = "[I")
	private int[] anIntArray452;

	@OriginalMember(owner = "client!sk", name = "R", descriptor = "[B")
	private byte[] shadingType;

	@OriginalMember(owner = "client!sk", name = "S", descriptor = "[S")
	private short[] faceC;

	@OriginalMember(owner = "client!sk", name = "U", descriptor = "[I")
	private int[] screenY;

	@OriginalMember(owner = "client!sk", name = "V", descriptor = "[I")
	private int[] faceColourB;

	@OriginalMember(owner = "client!sk", name = "W", descriptor = "Lclient!sk;")
	private JavaModel copySrcModel5;

	@OriginalMember(owner = "client!sk", name = "X", descriptor = "Lclient!sk;")
	private JavaModel copyDstModel4;

	@OriginalMember(owner = "client!sk", name = "Y", descriptor = "Lclient!sk;")
	private JavaModel copySrcModel1;

	@OriginalMember(owner = "client!sk", name = "Z", descriptor = "[I")
	private int[] anIntArray455;

	@OriginalMember(owner = "client!sk", name = "ab", descriptor = "Z")
	private boolean recoloured;

	@OriginalMember(owner = "client!sk", name = "bb", descriptor = "[I")
	private int[] faceColourA;

	@OriginalMember(owner = "client!sk", name = "cb", descriptor = "[S")
	private short[] faceB;

	@OriginalMember(owner = "client!sk", name = "db", descriptor = "Lclient!sk;")
	private JavaModel copyDstModel5;

	@OriginalMember(owner = "client!sk", name = "fb", descriptor = "[I")
	private int[] vertexX;

	@OriginalMember(owner = "client!sk", name = "gb", descriptor = "[I")
	private int[] vertexY;

	@OriginalMember(owner = "client!sk", name = "hb", descriptor = "I")
	private int ambient;

	@OriginalMember(owner = "client!sk", name = "ib", descriptor = "[[I")
	private int[][] anIntArrayArray52;

	@OriginalMember(owner = "client!sk", name = "lb", descriptor = "[Lclient!no;")
	private JavaBillboardAttributes[] billboardAttributes;

	@OriginalMember(owner = "client!sk", name = "nb", descriptor = "[B")
	private byte[] aByteArray88;

	@OriginalMember(owner = "client!sk", name = "ob", descriptor = "S")
	private short minZ;

	@OriginalMember(owner = "client!sk", name = "pb", descriptor = "[Lclient!gp;")
	private VertexNormal[] aClass86Array1;

	@OriginalMember(owner = "client!sk", name = "qb", descriptor = "[I")
	private int[] anIntArray459;

	@OriginalMember(owner = "client!sk", name = "sb", descriptor = "[B")
	private byte[] facePriority;

	@OriginalMember(owner = "client!sk", name = "tb", descriptor = "[[I")
	private int[][] anIntArrayArray53;

	@OriginalMember(owner = "client!sk", name = "vb", descriptor = "S")
	private short maxY;

	@OriginalMember(owner = "client!sk", name = "wb", descriptor = "[Z")
	private boolean[] aBooleanArray18;

	@OriginalMember(owner = "client!sk", name = "xb", descriptor = "Lclient!sk;")
	private JavaModel copyDstModel3;

	@OriginalMember(owner = "client!sk", name = "yb", descriptor = "[S")
	private short[] faceColour;

	@OriginalMember(owner = "client!sk", name = "zb", descriptor = "[I")
	private int[] vertexZ;

	@OriginalMember(owner = "client!sk", name = "Ab", descriptor = "[I")
	private int[] anIntArray461;

	@OriginalMember(owner = "client!sk", name = "Bb", descriptor = "[I")
	private int[] anIntArray462;

	@OriginalMember(owner = "client!sk", name = "Eb", descriptor = "[I")
	private int[] anIntArray463;

	@OriginalMember(owner = "client!sk", name = "Fb", descriptor = "[I")
	private int[] anIntArray464;

	@OriginalMember(owner = "client!sk", name = "Gb", descriptor = "[I")
	private int[] anIntArray465;

	@OriginalMember(owner = "client!sk", name = "Hb", descriptor = "[I")
	private int[] anIntArray466;

	@OriginalMember(owner = "client!sk", name = "Ib", descriptor = "[[I")
	private int[][] anIntArrayArray54;

	@OriginalMember(owner = "client!sk", name = "Jb", descriptor = "Lclient!wp;")
	private Class31_Sub2 aClass31_Sub2_3;

	@OriginalMember(owner = "client!sk", name = "Kb", descriptor = "[I")
	private int[] anIntArray467;

	@OriginalMember(owner = "client!sk", name = "Lb", descriptor = "Lclient!cv;")
	private JavaThreadResource copyThreadResource;

	@OriginalMember(owner = "client!sk", name = "Mb", descriptor = "S")
	private short minY;

	@OriginalMember(owner = "client!sk", name = "Nb", descriptor = "[I")
	private int[] anIntArray468;

	@OriginalMember(owner = "client!sk", name = "Ob", descriptor = "Lclient!sk;")
	private JavaModel copyDstModel1;

	@OriginalMember(owner = "client!sk", name = "Pb", descriptor = "[I")
	private int[] cameraZ;

	@OriginalMember(owner = "client!sk", name = "Qb", descriptor = "[I")
	private int[] anIntArray470;

	@OriginalMember(owner = "client!sk", name = "Rb", descriptor = "[I")
	private int[] anIntArray471;

	@OriginalMember(owner = "client!sk", name = "Sb", descriptor = "I")
	private int contrast;

	@OriginalMember(owner = "client!sk", name = "Tb", descriptor = "[B")
	private byte[] faceAlpha;

	@OriginalMember(owner = "client!sk", name = "Ub", descriptor = "[I")
	private int[] anIntArray472;

	@OriginalMember(owner = "client!sk", name = "Vb", descriptor = "[Lclient!ih;")
	private FaceNormal[] faceNormals;

	@OriginalMember(owner = "client!sk", name = "Xb", descriptor = "[I")
	private int[] anIntArray473;

	@OriginalMember(owner = "client!sk", name = "Yb", descriptor = "[[I")
	private int[][] anIntArrayArray55;

	@OriginalMember(owner = "client!sk", name = "ac", descriptor = "[I")
	private int[] anIntArray474;

	@OriginalMember(owner = "client!sk", name = "bc", descriptor = "[S")
	private short[] originModels;

	@OriginalMember(owner = "client!sk", name = "cc", descriptor = "[Lclient!gp;")
	private VertexNormal[] vertexNormalsOffset;

	@OriginalMember(owner = "client!sk", name = "dc", descriptor = "[S")
	private short[] faceTextures;

	@OriginalMember(owner = "client!sk", name = "ec", descriptor = "[I")
	private int[] faceBillboard;

	@OriginalMember(owner = "client!sk", name = "fc", descriptor = "[I")
	private int[] anIntArray476;

	@OriginalMember(owner = "client!sk", name = "hc", descriptor = "[I")
	private int[] faceColourC;

	@OriginalMember(owner = "client!sk", name = "ic", descriptor = "[I")
	private int[] screenX;

	@OriginalMember(owner = "client!sk", name = "jc", descriptor = "[I")
	private int[] anIntArray479;

	@OriginalMember(owner = "client!sk", name = "kc", descriptor = "Lclient!sk;")
	private JavaModel copySrcModel3;

	@OriginalMember(owner = "client!sk", name = "lc", descriptor = "[[I")
	private int[][] anIntArrayArray56;

	@OriginalMember(owner = "client!sk", name = "mc", descriptor = "[I")
	private int[] anIntArray480;

	@OriginalMember(owner = "client!sk", name = "nc", descriptor = "Lclient!sk;")
	private JavaModel copySrcModel2;

	@OriginalMember(owner = "client!sk", name = "oc", descriptor = "[I")
	private int[] anIntArray481;

	@OriginalMember(owner = "client!sk", name = "pc", descriptor = "[I")
	private int[] anIntArray482;

	@OriginalMember(owner = "client!sk", name = "qc", descriptor = "[Z")
	private boolean[] aBooleanArray19;

	@OriginalMember(owner = "client!sk", name = "v", descriptor = "I")
	private int vertexCount = 0;

	@OriginalMember(owner = "client!sk", name = "kb", descriptor = "I")
	private int anInt6297 = 0;

	@OriginalMember(owner = "client!sk", name = "jb", descriptor = "Z")
	private boolean pivotDirty = false;

	@OriginalMember(owner = "client!sk", name = "T", descriptor = "I")
	private int lightingState = 0;

	@OriginalMember(owner = "client!sk", name = "eb", descriptor = "Z")
	private boolean highPrecisionVertices = false;

	@OriginalMember(owner = "client!sk", name = "Zb", descriptor = "I")
	private int faceCount = 0;

	@OriginalMember(owner = "client!sk", name = "mb", descriptor = "B")
	private byte aByte72 = -1;

	@OriginalMember(owner = "client!sk", name = "gc", descriptor = "Z")
	private boolean boundsValid = false;

	@OriginalMember(owner = "client!sk", name = "Db", descriptor = "I")
	private int maxVertex = 0;

	@OriginalMember(owner = "client!sk", name = "Cb", descriptor = "Z")
	private boolean rendering = false;

	@OriginalMember(owner = "client!sk", name = "ub", descriptor = "Lclient!bc;")
	private final JavaToolkit toolkit;

	static {
		for (@Pc(14) int local14 = 1; local14 < 4096; local14++) {
			anIntArray483[local14] = 65536 / local14;
		}
	}

	@OriginalMember(owner = "client!sk", name = "<init>", descriptor = "(Lclient!bc;)V")
	public JavaModel(@OriginalArg(0) JavaToolkit javaToolkit) {
		this.toolkit = javaToolkit;
	}

	@OriginalMember(owner = "client!sk", name = "<init>", descriptor = "(Lclient!bc;Lclient!jq;IIII)V")
	public JavaModel(@OriginalArg(0) JavaToolkit javaToolkit, @OriginalArg(1) Mesh arg1, @OriginalArg(2) int functionMask, @OriginalArg(3) int ambient, @OriginalArg(4) int contrast, @OriginalArg(5) int arg5) {
		this.toolkit = javaToolkit;
		this.functionMask = functionMask;
		this.ambient = ambient;
		this.contrast = contrast;
		this.vertexCount = arg1.anInt3668;
		this.maxVertex = arg1.maxDepth;
		this.vertexX = arg1.anIntArray244;
		this.vertexY = arg1.anIntArray242;
		this.vertexZ = arg1.anIntArray251;
		this.faceCount = arg1.anInt3686;
		this.faceA = arg1.aShortArray120;
		this.faceB = arg1.aShortArray119;
		this.faceC = arg1.aShortArray117;
		this.aByte72 = arg1.priority;
		this.facePriority = arg1.aByteArray42;
		this.faceColour = arg1.aShortArray123;
		this.faceAlpha = arg1.aByteArray44;
		this.aShortArray218 = arg1.aShortArray121;
		this.shadingType = arg1.aByteArray43;
		this.emitters = arg1.particleEmitters;
		this.effectors = arg1.aClass179Array3;
		this.originModels = arg1.aShortArray122;
		@Pc(158) int local158;
		if (arg1.billboards != null) {
			this.billboardCount = arg1.billboards.length;
			this.billboardFaces = new JavaBillboardFace[this.billboardCount];
			this.billboardAttributes = new JavaBillboardAttributes[this.billboardCount];
			for (@Pc(135) int local135 = 0; local135 < arg1.billboards.length; local135++) {
				@Pc(141) MeshBillboard local141 = arg1.billboards[local135];
				@Pc(146) BillboardType local146 = Class259.method5501(local141.anInt4526);
				local158 = PathingEntity.anIntArray423[arg1.aShortArray123[local141.anInt4521] & 0xFFFF] & 0xFFFFFF;
				@Pc(177) int local177 = local158 | 255 - (arg1.aByteArray44 == null ? 0 : arg1.aByteArray44[local141.anInt4521] & 0xFF) << 24;
				this.billboardFaces[local135] = new JavaBillboardFace(local141.anInt4521, arg1.aShortArray120[local141.anInt4521], arg1.aShortArray119[local141.anInt4521], arg1.aShortArray117[local141.anInt4521], local146.anInt3538, local146.anInt3543, local146.anInt3536, local146.anInt3535, local146.anInt3542, local146.hasUid);
				this.billboardAttributes[local135] = new JavaBillboardAttributes(local177);
			}
		}
		if (arg1.anIntArray246 != null && (this.functionMask & 0x20) != 0) {
			this.anIntArrayArray55 = arg1.method3050();
		}
		if (arg1.anIntArray245 != null && (this.functionMask & 0x180) != 0) {
			this.anIntArrayArray52 = arg1.method3045();
		}
		if (arg1.billboards != null && (this.functionMask & 0x400) != 0) {
			this.anIntArrayArray54 = arg1.method3037();
		}
		@Pc(278) int local278;
		if (arg1.aShortArray125 == null) {
			this.faceTextures = null;
		} else {
			this.faceTextures = new short[this.faceCount];
			@Pc(276) boolean local276 = false;
			for (local278 = 0; local278 < this.faceCount; local278++) {
				@Pc(284) short local284 = arg1.aShortArray125[local278];
				if (local284 == -1 || this.toolkit.materialProvider.getMetrics(local284).aBoolean639) {
					this.faceTextures[local278] = -1;
				} else {
					this.faceTextures[local278] = local284;
					local276 = true;
				}
			}
			if (!local276) {
				this.faceTextures = null;
			}
		}
		if (this.faceTextures != null && arg1.anInt3689 > 0 && arg1.aByteArray46 != null) {
			@Pc(335) int[] local335 = new int[arg1.anInt3689];
			for (local278 = 0; local278 < this.faceCount; local278++) {
				if (arg1.aByteArray46[local278] != -1) {
					local335[arg1.aByteArray46[local278] & 0xFF]++;
				}
			}
			this.anInt6297 = 0;
			for (@Pc(366) int local366 = 0; local366 < arg1.anInt3689; local366++) {
				if (local335[local366] > 0 && arg1.aByteArray47[local366] == 0) {
					this.anInt6297++;
				}
			}
			this.anIntArray474 = new int[this.anInt6297];
			this.anIntArray452 = new int[this.anInt6297];
			this.anIntArray447 = new int[this.anInt6297];
			local158 = 0;
			for (@Pc(406) int local406 = 0; local406 < arg1.anInt3689; local406++) {
				if (local335[local406] > 0 && arg1.aByteArray47[local406] == 0) {
					this.anIntArray474[local158] = arg1.aShortArray118[local406] & 0xFFFF;
					this.anIntArray452[local158] = arg1.aShortArray116[local406] & 0xFFFF;
					this.anIntArray447[local158] = arg1.aShortArray124[local406] & 0xFFFF;
					local335[local406] = local158++;
				} else {
					local335[local406] = -1;
				}
			}
			this.aByteArray88 = new byte[this.faceCount];
			for (@Pc(468) int local468 = 0; local468 < this.faceCount; local468++) {
				if (arg1.aByteArray46[local468] == -1) {
					this.aByteArray88[local468] = -1;
				} else {
					this.aByteArray88[local468] = (byte) local335[arg1.aByteArray46[local468] & 0xFF];
					if (this.aByteArray88[local468] == -1 && this.faceTextures != null) {
						this.faceTextures[local468] = -1;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "<init>", descriptor = "(Lclient!bc;[Lclient!sk;)V")
	public JavaModel(@OriginalArg(0) JavaToolkit arg0, @OriginalArg(1) JavaModel[] arg1) {
		this.toolkit = arg0;
		@Pc(36) boolean local36 = false;
		@Pc(38) boolean local38 = false;
		@Pc(40) boolean local40 = false;
		@Pc(42) boolean local42 = false;
		this.vertexCount = 0;
		this.faceCount = 0;
		this.anInt6297 = 0;
		this.billboardCount = 0;
		@Pc(56) int local56 = 0;
		@Pc(58) int local58 = 0;
		this.aByte72 = -1;
		for (@Pc(63) int local63 = 0; local63 < arg1.length; local63++) {
			@Pc(68) JavaModel local68 = arg1[local63];
			if (local68 != null) {
				this.vertexCount += local68.vertexCount;
				this.faceCount += local68.faceCount;
				this.anInt6297 += local68.anInt6297;
				if (local68.facePriority == null) {
					if (this.aByte72 == -1) {
						this.aByte72 = local68.aByte72;
					}
					if (this.aByte72 != local68.aByte72) {
						local36 = true;
					}
				} else {
					local36 = true;
				}
				local38 |= local68.faceAlpha != null;
				local40 |= local68.faceTextures != null;
				local42 |= local68.aByteArray88 != null;
				if (local68.emitters != null) {
					local56 += local68.emitters.length;
				}
				if (local68.effectors != null) {
					local58 += local68.effectors.length;
				}
				if (local68.billboardFaces != null) {
					this.billboardCount += local68.billboardCount;
				}
			}
		}
		this.vertexX = new int[this.vertexCount];
		this.vertexY = new int[this.vertexCount];
		this.vertexZ = new int[this.vertexCount];
		this.faceA = new short[this.faceCount];
		this.faceB = new short[this.faceCount];
		this.faceC = new short[this.faceCount];
		this.faceColourA = new int[this.faceCount];
		this.faceColourB = new int[this.faceCount];
		this.faceColourC = new int[this.faceCount];
		if (local36) {
			this.facePriority = new byte[this.faceCount];
		}
		if (local38) {
			this.faceAlpha = new byte[this.faceCount];
		}
		if (local40) {
			this.faceTextures = new short[this.faceCount];
		}
		if (local42) {
			this.aByteArray88 = new byte[this.faceCount];
		}
		if (this.anInt6297 > 0) {
			this.anIntArray474 = new int[this.anInt6297];
			this.anIntArray452 = new int[this.anInt6297];
			this.anIntArray447 = new int[this.anInt6297];
		}
		if (local56 > 0) {
			this.emitters = new EmissiveTriangle[local56];
		}
		if (local58 > 0) {
			this.effectors = new EffectiveVertex[local58];
		}
		if (this.billboardCount > 0) {
			this.billboardFaces = new JavaBillboardFace[this.billboardCount];
			this.billboardAttributes = new JavaBillboardAttributes[this.billboardCount];
		}
		this.vertexCount = 0;
		@Pc(294) int local294 = this.faceCount;
		this.faceCount = 0;
		this.anInt6297 = 0;
		local58 = 0;
		local56 = 0;
		this.billboardCount = 0;
		@Pc(334) int local334;
		@Pc(714) int local714;
		for (@Pc(309) int local309 = 0; local309 < arg1.length; local309++) {
			@Pc(314) JavaModel local314 = arg1[local309];
			if (local314 != null) {
				local314.calculateLighting(false);
				if (local314.faceColour != null && this.faceColour == null) {
					this.faceColour = new short[local294];
				}
				if (local314.billboardFaces != null) {
					for (local334 = 0; local334 < local314.billboardCount; local334++) {
						@Pc(340) JavaBillboardFace local340 = local314.billboardFaces[local334];
						this.billboardFaces[this.billboardCount] = local340.method3196(local314.faceC[local340.anInt3925] + this.vertexCount, this.faceCount + local340.anInt3925, local314.faceB[local340.anInt3925] + this.vertexCount, local314.faceA[local340.anInt3925] + this.vertexCount);
						this.billboardAttributes[this.billboardCount] = local314.billboardAttributes[local334].method3852();
						this.billboardCount++;
					}
				}
				for (local334 = 0; local334 < local314.faceCount; local334++) {
					this.faceA[this.faceCount] = (short) (local314.faceA[local334] + this.vertexCount);
					this.faceB[this.faceCount] = (short) (local314.faceB[local334] + this.vertexCount);
					this.faceC[this.faceCount] = (short) (local314.faceC[local334] + this.vertexCount);
					this.faceColourA[this.faceCount] = local314.faceColourA[local334];
					this.faceColourB[this.faceCount] = local314.faceColourB[local334];
					this.faceColourC[this.faceCount] = local314.faceColourC[local334];
					if (local314.faceColour != null) {
						this.faceColour[this.faceCount] = local314.faceColour[local334];
					}
					if (local36) {
						if (local314.facePriority != null) {
							this.facePriority[this.faceCount] = local314.facePriority[local334];
						} else if (local314.aByte72 == -1) {
							this.facePriority[this.faceCount] = 10;
						} else {
							this.facePriority[this.faceCount] = local314.aByte72;
						}
					}
					if (local38 && local314.faceAlpha != null) {
						this.faceAlpha[this.faceCount] = local314.faceAlpha[local334];
					}
					if (local40) {
						if (local314.faceTextures == null) {
							this.faceTextures[this.faceCount] = -1;
						} else {
							this.faceTextures[this.faceCount] = local314.faceTextures[local334];
						}
					}
					if (local42) {
						this.aByteArray88[this.faceCount] = (byte) (local314.aByteArray88 == null || local314.aByteArray88[local334] == -1 ? -1 : local314.aByteArray88[local334] + this.anInt6297);
					}
					this.faceCount++;
				}
				@Pc(590) int local590;
				if (local314.emitters != null) {
					for (local590 = 0; local590 < local314.emitters.length; local590++) {
						@Pc(596) EmissiveTriangle local596 = local314.emitters[local590];
						this.emitters[local56] = new EmissiveTriangle(local596, this.vertexCount);
						local56++;
					}
				}
				if (local314.effectors != null) {
					for (local590 = 0; local590 < local314.effectors.length; local590++) {
						@Pc(624) EffectiveVertex local624 = local314.effectors[local590];
						this.effectors[local58] = new EffectiveVertex(local624, this.vertexCount);
						local58++;
					}
				}
				for (local590 = 0; local590 < local314.anInt6297; local590++) {
					if (local314.anIntArray474[local590] < local314.maxVertex) {
						this.anIntArray474[this.anInt6297] = local314.anIntArray474[local590] + this.vertexCount;
					}
					if (local314.anIntArray452[local590] < local314.maxVertex) {
						this.anIntArray452[this.anInt6297] = local314.anIntArray452[local590] + this.vertexCount;
					}
					if (local314.anIntArray447[local590] < local314.maxVertex) {
						this.anIntArray447[this.anInt6297] = local314.anIntArray447[local590] + this.vertexCount;
					}
					this.anInt6297++;
				}
				for (local714 = 0; local714 < local314.maxVertex; local714++) {
					this.vertexX[this.vertexCount] = local314.vertexX[local714];
					this.vertexY[this.vertexCount] = local314.vertexY[local714];
					this.vertexZ[this.vertexCount] = local314.vertexZ[local714];
					this.vertexCount++;
				}
			}
		}
		this.lightingState = 2;
		@Pc(763) int local763 = 0;
		this.maxVertex = this.vertexCount;
		for (local334 = 0; local334 < arg1.length; local334++) {
			@Pc(774) JavaModel local774 = arg1[local334];
			if (local774 != null) {
				for (local714 = 0; local714 < local774.anInt6297; local714++) {
					if (local774.anIntArray474[local714] >= local774.maxVertex) {
						this.anIntArray474[local763] = local774.anIntArray474[local714] + this.vertexCount - local774.maxVertex;
					}
					if (local774.anIntArray452[local714] >= local774.maxVertex) {
						this.anIntArray452[local763] = local774.anIntArray452[local714] + this.vertexCount - local774.maxVertex;
					}
					if (local774.anIntArray447[local714] >= local774.maxVertex) {
						this.anIntArray447[local763] = local774.anIntArray447[local714] + this.vertexCount - local774.maxVertex;
					}
					local763++;
				}
				for (@Pc(851) int local851 = local774.maxVertex; local851 < local774.vertexCount; local851++) {
					this.vertexX[this.vertexCount] = local774.vertexX[local851];
					this.vertexY[this.vertexCount] = local774.vertexY[local851];
					this.vertexZ[this.vertexCount] = local774.vertexZ[local851];
					this.vertexCount++;
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "NA", descriptor = "()I")
	@Override
	public int NA() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.maxX;
	}

	@OriginalMember(owner = "client!sk", name = "MA", descriptor = "()I")
	@Override
	public int setOffsets() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minY;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!c;Lclient!mi;Lclient!mc;I)V")
	public void method4900(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon arg1, @OriginalArg(2) ParticleList arg2, @OriginalArg(3) int arg3) {
		this.method4915(arg0, arg1, arg2, -1, arg3);
	}

	@OriginalMember(owner = "client!sk", name = "HA", descriptor = "()I")
	@Override
	public int HA() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minX;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(IILclient!c;Z)Z")
	@Override
	public boolean picked(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) boolean arg3) {
		this.aClass31_Sub2_3 = (Class31_Sub2) arg2;
		@Pc(7) Class31_Sub2 local7 = this.toolkit.aClass31_Sub2_1;
		@Pc(33) int local33 = local7.anInt7329 + (local7.anInt7334 * this.aClass31_Sub2_3.anInt7329 + local7.anInt7328 * this.aClass31_Sub2_3.anInt7324 + local7.anInt7332 * this.aClass31_Sub2_3.anInt7333 >> 15);
		@Pc(59) int local59 = local7.anInt7324 + (local7.anInt7331 * this.aClass31_Sub2_3.anInt7329 + local7.anInt7326 * this.aClass31_Sub2_3.anInt7324 + local7.anInt7327 * this.aClass31_Sub2_3.anInt7333 >> 15);
		@Pc(85) int local85 = local7.anInt7333 + (local7.anInt7323 * this.aClass31_Sub2_3.anInt7329 + local7.anInt7330 * this.aClass31_Sub2_3.anInt7324 + local7.anInt7325 * this.aClass31_Sub2_3.anInt7333 >> 15);
		@Pc(108) int local108 = local7.anInt7334 * this.aClass31_Sub2_3.anInt7334 + local7.anInt7328 * this.aClass31_Sub2_3.anInt7331 + local7.anInt7332 * this.aClass31_Sub2_3.anInt7323 >> 15;
		@Pc(131) int local131 = local7.anInt7334 * this.aClass31_Sub2_3.anInt7328 + local7.anInt7328 * this.aClass31_Sub2_3.anInt7326 + local7.anInt7332 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(154) int local154 = local7.anInt7334 * this.aClass31_Sub2_3.anInt7332 + local7.anInt7328 * this.aClass31_Sub2_3.anInt7327 + local7.anInt7332 * this.aClass31_Sub2_3.anInt7325 >> 15;
		@Pc(177) int local177 = local7.anInt7331 * this.aClass31_Sub2_3.anInt7334 + local7.anInt7326 * this.aClass31_Sub2_3.anInt7331 + local7.anInt7327 * this.aClass31_Sub2_3.anInt7323 >> 15;
		@Pc(200) int local200 = local7.anInt7331 * this.aClass31_Sub2_3.anInt7328 + local7.anInt7326 * this.aClass31_Sub2_3.anInt7326 + local7.anInt7327 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(223) int local223 = local7.anInt7331 * this.aClass31_Sub2_3.anInt7332 + local7.anInt7326 * this.aClass31_Sub2_3.anInt7327 + local7.anInt7327 * this.aClass31_Sub2_3.anInt7325 >> 15;
		@Pc(246) int local246 = local7.anInt7323 * this.aClass31_Sub2_3.anInt7334 + local7.anInt7330 * this.aClass31_Sub2_3.anInt7331 + local7.anInt7325 * this.aClass31_Sub2_3.anInt7323 >> 15;
		@Pc(269) int local269 = local7.anInt7323 * this.aClass31_Sub2_3.anInt7328 + local7.anInt7330 * this.aClass31_Sub2_3.anInt7326 + local7.anInt7325 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(292) int local292 = local7.anInt7323 * this.aClass31_Sub2_3.anInt7332 + local7.anInt7330 * this.aClass31_Sub2_3.anInt7327 + local7.anInt7325 * this.aClass31_Sub2_3.anInt7325 >> 15;
		@Pc(294) boolean local294 = false;
		@Pc(298) int local298 = this.toolkit.projectionCenterX;
		@Pc(302) int local302 = this.toolkit.anInt422;
		@Pc(306) int local306 = this.toolkit.anInt416;
		@Pc(310) int local310 = this.toolkit.anInt417;
		@Pc(312) int local312 = Integer.MAX_VALUE;
		@Pc(314) int local314 = Integer.MIN_VALUE;
		@Pc(316) int local316 = Integer.MAX_VALUE;
		@Pc(318) int local318 = Integer.MIN_VALUE;
		this.useThreadBuffers(Thread.currentThread());
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		this.anIntArray476[0] = this.minX;
		this.anIntArray463[0] = this.minY;
		this.anIntArray466[0] = this.minZ;
		this.anIntArray476[1] = this.maxX;
		this.anIntArray463[1] = this.minY;
		this.anIntArray466[1] = this.minZ;
		this.anIntArray476[2] = this.minX;
		this.anIntArray463[2] = this.maxY;
		this.anIntArray466[2] = this.minZ;
		this.anIntArray476[3] = this.maxX;
		this.anIntArray463[3] = this.maxY;
		this.anIntArray466[3] = this.minZ;
		this.anIntArray476[4] = this.minX;
		this.anIntArray463[4] = this.minY;
		this.anIntArray466[4] = this.maxZ;
		this.anIntArray476[5] = this.maxX;
		this.anIntArray463[5] = this.minY;
		this.anIntArray466[5] = this.maxZ;
		this.anIntArray476[6] = this.minX;
		this.anIntArray463[6] = this.maxY;
		this.anIntArray466[6] = this.maxZ;
		this.anIntArray476[7] = this.maxX;
		this.anIntArray463[7] = this.maxY;
		this.anIntArray466[7] = this.maxZ;
		@Pc(504) int local504;
		@Pc(520) int local520;
		@Pc(536) int local536;
		@Pc(478) int local478;
		@Pc(483) int local483;
		@Pc(488) int local488;
		@Pc(549) int local549;
		@Pc(557) int local557;
		for (@Pc(472) int local472 = 0; local472 < 8; local472++) {
			local478 = this.anIntArray476[local472];
			local483 = this.anIntArray463[local472];
			local488 = this.anIntArray466[local472];
			local504 = local33 + (local108 * local478 + local131 * local483 + local154 * local488 >> 15);
			local520 = local59 + (local177 * local478 + local200 * local483 + local223 * local488 >> 15);
			local536 = local85 + (local246 * local478 + local269 * local483 + local292 * local488 >> 15);
			if (local536 >= this.toolkit.anInt406) {
				local549 = local298 + local504 * local306 / local536;
				local557 = local302 + local520 * local310 / local536;
				if (local549 < local312) {
					local312 = local549;
				}
				if (local549 > local314) {
					local314 = local549;
				}
				if (local557 < local316) {
					local316 = local557;
				}
				if (local557 > local318) {
					local318 = local557;
				}
				local294 = true;
			}
		}
		if (local294 && arg0 > local312 && arg0 < local314 && arg1 > local316 && arg1 < local318) {
			if (arg3) {
				return true;
			}
			for (local549 = 0; local549 < this.vertexCount; local549++) {
				local478 = this.vertexX[local549];
				local483 = this.vertexY[local549];
				local488 = this.vertexZ[local549];
				local504 = local33 + (local108 * local478 + local131 * local483 + local154 * local488 >> 15);
				local520 = local59 + (local177 * local478 + local200 * local483 + local223 * local488 >> 15);
				local536 = local85 + (local246 * local478 + local269 * local483 + local292 * local488 >> 15);
				if (local536 >= this.toolkit.anInt406) {
					this.screenX[local549] = local298 + local504 * local306 / local536;
					this.screenY[local549] = local302 + local520 * local310 / local536;
				} else {
					this.screenX[local549] = -999999;
				}
			}
			for (local557 = 0; local557 < this.faceCount; local557++) {
				if (this.screenX[this.faceA[local557]] != -999999 && this.screenX[this.faceB[local557]] != -999999 && this.screenX[this.faceC[local557]] != -999999 && this.pointInFaceBounds(arg0, arg1, this.screenY[this.faceA[local557]], this.screenY[this.faceB[local557]], this.screenY[this.faceC[local557]], this.screenX[this.faceA[local557]], this.screenX[this.faceB[local557]], this.screenX[this.faceC[local557]])) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!sk", name = "M", descriptor = "(I[IIIIZI[I)V")
	@Override
	protected void M(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) boolean lights, @OriginalArg(6) int submesh, @OriginalArg(7) int[] transform) {
		@Pc(2) int local2 = labels.length;
		@Pc(21) int local21;
		@Pc(67) int local67;
		@Pc(89) int local89;
		@Pc(72) int local72;
		@Pc(84) int local84;
		if (type == 0) {
			x <<= 0x4;
			y <<= 0x4;
			z <<= 0x4;
			if (!this.highPrecisionVertices) {
				for (local21 = 0; local21 < this.vertexCount; local21++) {
					this.vertexX[local21] <<= 0x4;
					this.vertexY[local21] <<= 0x4;
					this.vertexZ[local21] <<= 0x4;
				}
				this.highPrecisionVertices = true;
			}
			local21 = 0;
			this.pivotX = 0;
			this.pivotY = 0;
			this.pivotZ = 0;
			for (local67 = 0; local67 < local2; local67++) {
				local72 = labels[local67];
				if (local72 < this.anIntArrayArray55.length) {
					@Pc(82) int[] local82 = this.anIntArrayArray55[local72];
					for (local84 = 0; local84 < local82.length; local84++) {
						local89 = local82[local84];
						if (this.originModels == null || (submesh & this.originModels[local89]) != 0) {
							this.pivotX += this.vertexX[local89];
							this.pivotY += this.vertexY[local89];
							this.pivotZ += this.vertexZ[local89];
							local21++;
						}
					}
				}
			}
			if (local21 > 0) {
				this.pivotX = this.pivotX / local21 + x;
				this.pivotY = this.pivotY / local21 + y;
				this.pivotZ = this.pivotZ / local21 + z;
				this.pivotDirty = true;
			} else {
				this.pivotX = x;
				this.pivotY = y;
				this.pivotZ = z;
			}
			return;
		}
		@Pc(320) int[] local320;
		@Pc(322) int local322;
		if (type == 1) {
			if (transform != null) {
				local21 = transform[0] * x + transform[1] * y + transform[2] * z + 16384 >> 15;
				local67 = transform[3] * x + transform[4] * y + transform[5] * z + 16384 >> 15;
				local72 = transform[6] * x + transform[7] * y + transform[8] * z + 16384 >> 15;
				x = local21;
				y = local67;
				z = local72;
			}
			x <<= 0x4;
			y <<= 0x4;
			z <<= 0x4;
			if (!this.highPrecisionVertices) {
				for (local21 = 0; local21 < this.vertexCount; local21++) {
					this.vertexX[local21] <<= 0x4;
					this.vertexY[local21] <<= 0x4;
					this.vertexZ[local21] <<= 0x4;
				}
				this.highPrecisionVertices = true;
			}
			for (local21 = 0; local21 < local2; local21++) {
				local67 = labels[local21];
				if (local67 < this.anIntArrayArray55.length) {
					local320 = this.anIntArrayArray55[local67];
					for (local322 = 0; local322 < local320.length; local322++) {
						local84 = local320[local322];
						if (this.originModels == null || (submesh & this.originModels[local84]) != 0) {
							this.vertexX[local84] += x;
							this.vertexY[local84] += y;
							this.vertexZ[local84] += z;
						}
					}
				}
			}
			return;
		}
		@Pc(478) int local478;
		@Pc(503) int local503;
		@Pc(528) int local528;
		@Pc(567) int local567;
		@Pc(571) int local571;
		@Pc(575) int local575;
		@Pc(579) int local579;
		@Pc(587) int local587;
		@Pc(595) int local595;
		@Pc(754) int local754;
		@Pc(782) int local782;
		@Pc(787) int local787;
		@Pc(797) int local797;
		@Pc(802) int local802;
		@Pc(805) int local805;
		@Pc(808) int local808;
		@Pc(810) int local810;
		@Pc(934) int[] local934;
		@Pc(936) int local936;
		@Pc(939) int local939;
		@Pc(942) int local942;
		@Pc(944) int local944;
		@Pc(1067) int local1067;
		if (type == 2) {
			if (transform == null) {
				for (local21 = 0; local21 < local2; local21++) {
					local67 = labels[local21];
					if (local67 < this.anIntArrayArray55.length) {
						local320 = this.anIntArrayArray55[local67];
						for (local322 = 0; local322 < local320.length; local322++) {
							local84 = local320[local322];
							if (this.originModels == null || (submesh & this.originModels[local84]) != 0) {
								this.vertexX[local84] -= this.pivotX;
								this.vertexY[local84] -= this.pivotY;
								this.vertexZ[local84] -= this.pivotZ;
								if (z != 0) {
									local89 = Class50_Sub1.anIntArray225[z];
									local478 = Class50_Sub1.anIntArray224[z];
									local503 = this.vertexY[local84] * local89 + this.vertexX[local84] * local478 + 32767 >> 15;
									this.vertexY[local84] = this.vertexY[local84] * local478 + 32767 - this.vertexX[local84] * local89 >> 15;
									this.vertexX[local84] = local503;
								}
								if (x != 0) {
									local89 = Class50_Sub1.anIntArray225[x];
									local478 = Class50_Sub1.anIntArray224[x];
									local503 = this.vertexY[local84] * local478 + 32767 - this.vertexZ[local84] * local89 >> 15;
									this.vertexZ[local84] = this.vertexY[local84] * local89 + this.vertexZ[local84] * local478 + 32767 >> 15;
									this.vertexY[local84] = local503;
								}
								if (y != 0) {
									local89 = Class50_Sub1.anIntArray225[y];
									local478 = Class50_Sub1.anIntArray224[y];
									local503 = this.vertexZ[local84] * local89 + this.vertexX[local84] * local478 + 32767 >> 15;
									this.vertexZ[local84] = this.vertexZ[local84] * local478 + 32767 - this.vertexX[local84] * local89 >> 15;
									this.vertexX[local84] = local503;
								}
								this.vertexX[local84] += this.pivotX;
								this.vertexY[local84] += this.pivotY;
								this.vertexZ[local84] += this.pivotZ;
							}
						}
					}
				}
			} else {
				if (!this.highPrecisionVertices) {
					for (local21 = 0; local21 < this.vertexCount; local21++) {
						this.vertexX[local21] <<= 0x4;
						this.vertexY[local21] <<= 0x4;
						this.vertexZ[local21] <<= 0x4;
					}
					this.highPrecisionVertices = true;
				}
				local21 = transform[9] << 4;
				local67 = transform[10] << 4;
				local72 = transform[11] << 4;
				local322 = transform[12] << 4;
				local84 = transform[13] << 4;
				local89 = transform[14] << 4;
				if (this.pivotDirty) {
					local478 = transform[0] * this.pivotX + transform[3] * this.pivotY + transform[6] * this.pivotZ + 16384 >> 15;
					local503 = transform[1] * this.pivotX + transform[4] * this.pivotY + transform[7] * this.pivotZ + 16384 >> 15;
					local528 = transform[2] * this.pivotX + transform[5] * this.pivotY + transform[8] * this.pivotZ + 16384 >> 15;
					local478 += local322;
					local503 += local84;
					local528 += local89;
					this.pivotX = local478;
					this.pivotY = local503;
					this.pivotZ = local528;
					this.pivotDirty = false;
				}
				@Pc(555) int[] local555 = new int[9];
				local503 = Class50_Sub1.anIntArray224[x];
				local528 = Class50_Sub1.anIntArray225[x];
				local567 = Class50_Sub1.anIntArray224[y];
				local571 = Class50_Sub1.anIntArray225[y];
				local575 = Class50_Sub1.anIntArray224[z];
				local579 = Class50_Sub1.anIntArray225[z];
				local587 = local528 * local575 + 16384 >> 15;
				local595 = local528 * local579 + 16384 >> 15;
				local555[0] = local567 * local575 + local571 * local595 + 16384 >> 15;
				local555[1] = -local567 * local579 + local571 * local587 + 16384 >> 15;
				local555[2] = local571 * local503 + 16384 >> 15;
				local555[3] = local503 * local579 + 16384 >> 15;
				local555[4] = local503 * local575 + 16384 >> 15;
				local555[5] = -local528;
				local555[6] = -local571 * local575 + local567 * local595 + 16384 >> 15;
				local555[7] = local571 * local579 + local567 * local587 + 16384 >> 15;
				local555[8] = local567 * local503 + 16384 >> 15;
				@Pc(726) int local726 = local555[0] * -this.pivotX + local555[1] * -this.pivotY + local555[2] * -this.pivotZ + 16384 >> 15;
				local754 = local555[3] * -this.pivotX + local555[4] * -this.pivotY + local555[5] * -this.pivotZ + 16384 >> 15;
				local782 = local555[6] * -this.pivotX + local555[7] * -this.pivotY + local555[8] * -this.pivotZ + 16384 >> 15;
				local787 = local726 + this.pivotX;
				@Pc(792) int local792 = local754 + this.pivotY;
				local797 = local782 + this.pivotZ;
				@Pc(800) int[] local800 = new int[9];
				for (local802 = 0; local802 < 3; local802++) {
					for (local805 = 0; local805 < 3; local805++) {
						local808 = 0;
						for (local810 = 0; local810 < 3; local810++) {
							local808 += local555[local802 * 3 + local810] * transform[local805 * 3 + local810];
						}
						local800[local802 * 3 + local805] = local808 + 16384 >> 15;
					}
				}
				local805 = local555[0] * local322 + local555[1] * local84 + local555[2] * local89 + 16384 >> 15;
				local808 = local555[3] * local322 + local555[4] * local84 + local555[5] * local89 + 16384 >> 15;
				local810 = local555[6] * local322 + local555[7] * local84 + local555[8] * local89 + 16384 >> 15;
				local805 += local787;
				local808 += local792;
				local810 += local797;
				local934 = new int[9];
				for (local936 = 0; local936 < 3; local936++) {
					for (local939 = 0; local939 < 3; local939++) {
						local942 = 0;
						for (local944 = 0; local944 < 3; local944++) {
							local942 += transform[local936 * 3 + local944] * local800[local939 + local944 * 3];
						}
						local934[local936 * 3 + local939] = local942 + 16384 >> 15;
					}
				}
				local939 = transform[0] * local805 + transform[1] * local808 + transform[2] * local810 + 16384 >> 15;
				local942 = transform[3] * local805 + transform[4] * local808 + transform[5] * local810 + 16384 >> 15;
				local944 = transform[6] * local805 + transform[7] * local808 + transform[8] * local810 + 16384 >> 15;
				local939 += local21;
				local942 += local67;
				local944 += local72;
				for (local1067 = 0; local1067 < local2; local1067++) {
					@Pc(1072) int local1072 = labels[local1067];
					if (local1072 < this.anIntArrayArray55.length) {
						@Pc(1082) int[] local1082 = this.anIntArrayArray55[local1072];
						for (@Pc(1084) int local1084 = 0; local1084 < local1082.length; local1084++) {
							@Pc(1089) int local1089 = local1082[local1084];
							if (this.originModels == null || (submesh & this.originModels[local1089]) != 0) {
								@Pc(1130) int local1130 = local934[0] * this.vertexX[local1089] + local934[1] * this.vertexY[local1089] + local934[2] * this.vertexZ[local1089] + 16384 >> 15;
								@Pc(1161) int local1161 = local934[3] * this.vertexX[local1089] + local934[4] * this.vertexY[local1089] + local934[5] * this.vertexZ[local1089] + 16384 >> 15;
								@Pc(1192) int local1192 = local934[6] * this.vertexX[local1089] + local934[7] * this.vertexY[local1089] + local934[8] * this.vertexZ[local1089] + 16384 >> 15;
								@Pc(1196) int local1196 = local1130 + local939;
								@Pc(1200) int local1200 = local1161 + local942;
								@Pc(1204) int local1204 = local1192 + local944;
								this.vertexX[local1089] = local1196;
								this.vertexY[local1089] = local1200;
								this.vertexZ[local1089] = local1204;
							}
						}
					}
				}
			}
		} else if (type != 3) {
			@Pc(2402) JavaBillboardFace local2402;
			@Pc(2407) JavaBillboardAttributes local2407;
			if (type == 5) {
				if (this.anIntArrayArray52 != null && this.faceAlpha != null) {
					for (local21 = 0; local21 < local2; local21++) {
						local67 = labels[local21];
						if (local67 < this.anIntArrayArray52.length) {
							local320 = this.anIntArrayArray52[local67];
							for (local322 = 0; local322 < local320.length; local322++) {
								local84 = local320[local322];
								if (this.aShortArray218 == null || (submesh & this.aShortArray218[local84]) != 0) {
									local89 = (this.faceAlpha[local84] & 0xFF) + x * 8;
									if (local89 < 0) {
										local89 = 0;
									} else if (local89 > 255) {
										local89 = 255;
									}
									this.faceAlpha[local84] = (byte) local89;
								}
							}
						}
					}
					if (this.billboardFaces != null) {
						for (local67 = 0; local67 < this.billboardCount; local67++) {
							local2402 = this.billboardFaces[local67];
							local2407 = this.billboardAttributes[local67];
							local2407.diffuseColor = local2407.diffuseColor & 0xFFFFFF | 255 - (this.faceAlpha[local2402.anInt3925] & 0xFF) << 24;
						}
					}
				}
			} else if (type != 7) {
				@Pc(2629) JavaBillboardAttributes local2629;
				if (type == 8) {
					if (this.anIntArrayArray54 != null) {
						for (local21 = 0; local21 < local2; local21++) {
							local67 = labels[local21];
							if (local67 < this.anIntArrayArray54.length) {
								local320 = this.anIntArrayArray54[local67];
								for (local322 = 0; local322 < local320.length; local322++) {
									local2629 = this.billboardAttributes[local320[local322]];
									local2629.offsetX += x;
									local2629.offsetY += y;
								}
							}
						}
					}
				} else if (type == 10) {
					if (this.anIntArrayArray54 != null) {
						for (local21 = 0; local21 < local2; local21++) {
							local67 = labels[local21];
							if (local67 < this.anIntArrayArray54.length) {
								local320 = this.anIntArrayArray54[local67];
								for (local322 = 0; local322 < local320.length; local322++) {
									local2629 = this.billboardAttributes[local320[local322]];
									local2629.scaleX = local2629.scaleX * x >> 7;
									local2629.scaleY = local2629.scaleY * y >> 7;
								}
							}
						}
					}
				} else if (type == 9 && this.anIntArrayArray54 != null) {
					for (local21 = 0; local21 < local2; local21++) {
						local67 = labels[local21];
						if (local67 < this.anIntArrayArray54.length) {
							local320 = this.anIntArrayArray54[local67];
							for (local322 = 0; local322 < local320.length; local322++) {
								local2629 = this.billboardAttributes[local320[local322]];
								local2629.rotation = local2629.rotation + x & 0x3FFF;
							}
						}
					}
				}
			} else if (this.anIntArrayArray52 != null) {
				for (local21 = 0; local21 < local2; local21++) {
					local67 = labels[local21];
					if (local67 < this.anIntArrayArray52.length) {
						local320 = this.anIntArrayArray52[local67];
						for (local322 = 0; local322 < local320.length; local322++) {
							local84 = local320[local322];
							if (this.aShortArray218 == null || (submesh & this.aShortArray218[local84]) != 0) {
								local89 = this.faceColour[local84] & 0xFFFF;
								local478 = local89 >> 10 & 0x3F;
								local503 = local89 >> 7 & 0x7;
								local528 = local89 & 0x7F;
								@Pc(2500) int local2500 = local478 + x & 0x3F;
								local503 += y;
								if (local503 < 0) {
									local503 = 0;
								} else if (local503 > 7) {
									local503 = 7;
								}
								local528 += z;
								if (local528 < 0) {
									local528 = 0;
								} else if (local528 > 127) {
									local528 = 127;
								}
								this.faceColour[local84] = (short) (local2500 << 10 | local503 << 7 | local528);
							}
						}
						this.recoloured = true;
					}
				}
				if (this.billboardFaces != null) {
					for (local67 = 0; local67 < this.billboardCount; local67++) {
						local2402 = this.billboardFaces[local67];
						local2407 = this.billboardAttributes[local67];
						local2407.diffuseColor = local2407.diffuseColor & 0xFF000000 | PathingEntity.anIntArray423[Class56.method1470(this.faceColour[local2402.anInt3925] & 0xFFFF)] & 0xFFFFFF;
					}
				}
			}
		} else if (transform == null) {
			for (local21 = 0; local21 < local2; local21++) {
				local67 = labels[local21];
				if (local67 < this.anIntArrayArray55.length) {
					local320 = this.anIntArrayArray55[local67];
					for (local322 = 0; local322 < local320.length; local322++) {
						local84 = local320[local322];
						if (this.originModels == null || (submesh & this.originModels[local84]) != 0) {
							this.vertexX[local84] -= this.pivotX;
							this.vertexY[local84] -= this.pivotY;
							this.vertexZ[local84] -= this.pivotZ;
							this.vertexX[local84] = this.vertexX[local84] * x / 128;
							this.vertexY[local84] = this.vertexY[local84] * y / 128;
							this.vertexZ[local84] = this.vertexZ[local84] * z / 128;
							this.vertexX[local84] += this.pivotX;
							this.vertexY[local84] += this.pivotY;
							this.vertexZ[local84] += this.pivotZ;
						}
					}
				}
			}
		} else {
			if (!this.highPrecisionVertices) {
				for (local21 = 0; local21 < this.vertexCount; local21++) {
					this.vertexX[local21] <<= 0x4;
					this.vertexY[local21] <<= 0x4;
					this.vertexZ[local21] <<= 0x4;
				}
				this.highPrecisionVertices = true;
			}
			local21 = transform[9] << 4;
			local67 = transform[10] << 4;
			local72 = transform[11] << 4;
			local322 = transform[12] << 4;
			local84 = transform[13] << 4;
			local89 = transform[14] << 4;
			if (this.pivotDirty) {
				local478 = transform[0] * this.pivotX + transform[3] * this.pivotY + transform[6] * this.pivotZ + 16384 >> 15;
				local503 = transform[1] * this.pivotX + transform[4] * this.pivotY + transform[7] * this.pivotZ + 16384 >> 15;
				local528 = transform[2] * this.pivotX + transform[5] * this.pivotY + transform[8] * this.pivotZ + 16384 >> 15;
				local478 += local322;
				local503 += local84;
				local528 += local89;
				this.pivotX = local478;
				this.pivotY = local503;
				this.pivotZ = local528;
				this.pivotDirty = false;
			}
			local478 = x << 15 >> 7;
			local503 = y << 15 >> 7;
			local528 = z << 15 >> 7;
			local567 = local478 * -this.pivotX + 16384 >> 15;
			local571 = local503 * -this.pivotY + 16384 >> 15;
			local575 = local528 * -this.pivotZ + 16384 >> 15;
			local579 = local567 + this.pivotX;
			local587 = local571 + this.pivotY;
			local595 = local575 + this.pivotZ;
			@Pc(1736) int[] local1736 = new int[] { local478 * transform[0] + 16384 >> 15, local478 * transform[3] + 16384 >> 15, local478 * transform[6] + 16384 >> 15, local503 * transform[1] + 16384 >> 15, local503 * transform[4] + 16384 >> 15, local503 * transform[7] + 16384 >> 15, local528 * transform[2] + 16384 >> 15, local528 * transform[5] + 16384 >> 15, local528 * transform[8] + 16384 >> 15 };
			local754 = local478 * local322 + 16384 >> 15;
			local782 = local503 * local84 + 16384 >> 15;
			local787 = local528 * local89 + 16384 >> 15;
			@Pc(1872) int local1872 = local754 + local579;
			@Pc(1876) int local1876 = local782 + local587;
			@Pc(1880) int local1880 = local787 + local595;
			@Pc(1883) int[] local1883 = new int[9];
			@Pc(1888) int local1888;
			for (local797 = 0; local797 < 3; local797++) {
				for (local1888 = 0; local1888 < 3; local1888++) {
					local802 = 0;
					for (local805 = 0; local805 < 3; local805++) {
						local802 += transform[local797 * 3 + local805] * local1736[local1888 + local805 * 3];
					}
					local1883[local797 * 3 + local1888] = local802 + 16384 >> 15;
				}
			}
			local1888 = transform[0] * local1872 + transform[1] * local1876 + transform[2] * local1880 + 16384 >> 15;
			local802 = transform[3] * local1872 + transform[4] * local1876 + transform[5] * local1880 + 16384 >> 15;
			local805 = transform[6] * local1872 + transform[7] * local1876 + transform[8] * local1880 + 16384 >> 15;
			local1888 += local21;
			local802 += local67;
			local805 += local72;
			for (local808 = 0; local808 < local2; local808++) {
				local810 = labels[local808];
				if (local810 < this.anIntArrayArray55.length) {
					local934 = this.anIntArrayArray55[local810];
					for (local936 = 0; local936 < local934.length; local936++) {
						local939 = local934[local936];
						if (this.originModels == null || (submesh & this.originModels[local939]) != 0) {
							local942 = local1883[0] * this.vertexX[local939] + local1883[1] * this.vertexY[local939] + local1883[2] * this.vertexZ[local939] + 16384 >> 15;
							local944 = local1883[3] * this.vertexX[local939] + local1883[4] * this.vertexY[local939] + local1883[5] * this.vertexZ[local939] + 16384 >> 15;
							local1067 = local1883[6] * this.vertexX[local939] + local1883[7] * this.vertexY[local939] + local1883[8] * this.vertexZ[local939] + 16384 >> 15;
							@Pc(2145) int local2145 = local942 + local1888;
							@Pc(2149) int local2149 = local944 + local802;
							@Pc(2153) int local2153 = local1067 + local805;
							this.vertexX[local939] = local2145;
							this.vertexY[local939] = local2149;
							this.vertexZ[local939] = local2153;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "e", descriptor = "()V")
	private void relightColours() {
		if (this.lightingState == 0) {
			this.calculateLighting(false);
			return;
		}
		for (@Pc(8) int local8 = 0; local8 < this.faceCount; local8++) {
			@Pc(19) short local19 = this.faceTextures == null ? -1 : this.faceTextures[local8];
			if (local19 == -1) {
				@Pc(29) int local29 = this.faceColour[local8] & 0xFFFF;
				@Pc(38) int local38 = (local29 & 0x7F) * this.ambient >> 7;
				@Pc(46) short local46 = Class56.method1470(local29 & 0xFFFFFF80 | local38);
				@Pc(59) int local59;
				if (this.faceColourC[local8] == -1) {
					local59 = this.faceColourA[local8] & 0xFFFE0000;
					this.faceColourA[local8] = local59 | GlVolumeTexture.method791(local46, local59 >> 17);
				} else if (this.faceColourC[local8] != -2) {
					local59 = this.faceColourA[local8] & 0xFFFE0000;
					this.faceColourA[local8] = local59 | GlVolumeTexture.method791(local46, local59 >> 17);
					local59 = this.faceColourB[local8] & 0xFFFE0000;
					this.faceColourB[local8] = local59 | GlVolumeTexture.method791(local46, local59 >> 17);
					local59 = this.faceColourC[local8] & 0xFFFE0000;
					this.faceColourC[local8] = local59 | GlVolumeTexture.method791(local46, local59 >> 17);
				}
			}
		}
		this.lightingState = 2;
	}

	@OriginalMember(owner = "client!sk", name = "f", descriptor = "()V")
	private void rotate90WithNormals() {
		@Pc(7) int local7;
		for (@Pc(1) int local1 = 0; local1 < this.maxVertex; local1++) {
			local7 = this.vertexX[local1];
			this.vertexX[local1] = this.vertexZ[local1];
			this.vertexZ[local1] = -local7;
			if (this.aClass86Array1[local1] != null) {
				local7 = this.aClass86Array1[local1].x;
				this.aClass86Array1[local1].x = this.aClass86Array1[local1].z;
				this.aClass86Array1[local1].z = -local7;
			}
		}
		@Pc(71) int local71;
		if (this.faceNormals != null) {
			for (local7 = 0; local7 < this.faceCount; local7++) {
				if (this.faceNormals[local7] != null) {
					local71 = this.faceNormals[local7].x;
					this.faceNormals[local7].x = this.faceNormals[local7].z;
					this.faceNormals[local7].z = -local71;
				}
			}
		}
		for (local7 = this.maxVertex; local7 < this.vertexCount; local7++) {
			local71 = this.vertexX[local7];
			this.vertexX[local7] = this.vertexZ[local7];
			this.vertexZ[local7] = -local71;
		}
		this.lightingState = 0;
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "g", descriptor = "()V")
	private void invalidateNormals() {
		this.aClass86Array1 = null;
		this.vertexNormalsOffset = null;
		this.faceNormals = null;
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "c", descriptor = "()V")
	@Override
	public void uploadToGpu() {
	}

	@OriginalMember(owner = "client!sk", name = "h", descriptor = "()V")
	private void rotate270() {
		for (@Pc(1) int local1 = 0; local1 < this.vertexCount; local1++) {
			@Pc(7) int local7 = this.vertexZ[local1];
			this.vertexZ[local1] = this.vertexX[local1];
			this.vertexX[local1] = -local7;
		}
		this.invalidateNormals();
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(I)V")
	private void drawClippedTriangleRgb(@OriginalArg(0) int face) {
		@Pc(1) int local1 = 0;
		@Pc(5) int local5 = this.toolkit.anInt406;
		@Pc(10) short local10 = this.faceA[face];
		@Pc(15) short local15 = this.faceB[face];
		@Pc(20) short local20 = this.faceC[face];
		@Pc(25) int local25 = this.cameraZ[local10];
		@Pc(30) int local30 = this.cameraZ[local15];
		@Pc(35) int local35 = this.cameraZ[local20];
		if (this.faceAlpha == null) {
			this.rasterizer.alpha = 0;
		} else {
			this.rasterizer.alpha = this.faceAlpha[face] & 0xFF;
		}
		@Pc(88) int local88;
		@Pc(93) int local93;
		@Pc(100) int local100;
		@Pc(113) int local113;
		if (local25 >= local5) {
			this.anIntArray462[0] = this.screenX[local10];
			this.anIntArray480[0] = this.screenY[local10];
			local1++;
			this.anIntArray449[0] = this.faceColourA[face] & 0xFFFF;
		} else {
			local88 = this.anIntArray461[local10];
			local93 = this.anIntArray470[local10];
			local100 = this.faceColourA[face] & 0xFFFF;
			if (local35 >= local5) {
				local113 = (local5 - local25) * anIntArray483[local35 - local25];
				this.anIntArray462[0] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local20] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[0] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local20] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				local1++;
				this.anIntArray449[0] = local100 + (((this.faceColourC[face] & 0xFFFF) - local100) * local113 >> 16);
			}
			if (local30 >= local5) {
				local113 = (local5 - local25) * anIntArray483[local30 - local25];
				this.anIntArray462[local1] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local15] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[local1] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local15] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				this.anIntArray449[local1++] = local100 + (((this.faceColourB[face] & 0xFFFF) - local100) * local113 >> 16);
			}
		}
		if (local30 >= local5) {
			this.anIntArray462[local1] = this.screenX[local15];
			this.anIntArray480[local1] = this.screenY[local15];
			this.anIntArray449[local1++] = this.faceColourB[face] & 0xFFFF;
		} else {
			local88 = this.anIntArray461[local15];
			local93 = this.anIntArray470[local15];
			local100 = this.faceColourB[face] & 0xFFFF;
			if (local25 >= local5) {
				local113 = (local5 - local30) * anIntArray483[local25 - local30];
				this.anIntArray462[local1] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local10] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[local1] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local10] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				this.anIntArray449[local1++] = local100 + (((this.faceColourA[face] & 0xFFFF) - local100) * local113 >> 16);
			}
			if (local35 >= local5) {
				local113 = (local5 - local30) * anIntArray483[local35 - local30];
				this.anIntArray462[local1] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local20] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[local1] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local20] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				this.anIntArray449[local1++] = local100 + (((this.faceColourC[face] & 0xFFFF) - local100) * local113 >> 16);
			}
		}
		if (local35 >= local5) {
			this.anIntArray462[local1] = this.screenX[local20];
			this.anIntArray480[local1] = this.screenY[local20];
			this.anIntArray449[local1++] = this.faceColourC[face] & 0xFFFF;
		} else {
			local88 = this.anIntArray461[local20];
			local93 = this.anIntArray470[local20];
			local100 = this.faceColourC[face] & 0xFFFF;
			if (local30 >= local5) {
				local113 = (local5 - local35) * anIntArray483[local30 - local35];
				this.anIntArray462[local1] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local15] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[local1] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local15] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				this.anIntArray449[local1++] = local100 + (((this.faceColourB[face] & 0xFFFF) - local100) * local113 >> 16);
			}
			if (local25 >= local5) {
				local113 = (local5 - local35) * anIntArray483[local25 - local35];
				this.anIntArray462[local1] = this.threadResource.anInt1118 + (local88 + ((this.anIntArray461[local10] - local88) * local113 >> 16)) * this.toolkit.anInt416 / local5;
				this.anIntArray480[local1] = this.threadResource.anInt1119 + (local93 + ((this.anIntArray470[local10] - local93) * local113 >> 16)) * this.toolkit.anInt417 / local5;
				this.anIntArray449[local1++] = local100 + (((this.faceColourA[face] & 0xFFFF) - local100) * local113 >> 16);
			}
		}
		local88 = this.anIntArray462[0];
		local93 = this.anIntArray462[1];
		local100 = this.anIntArray462[2];
		local113 = this.anIntArray480[0];
		@Pc(725) int local725 = this.anIntArray480[1];
		@Pc(730) int local730 = this.anIntArray480[2];
		this.rasterizer.aBoolean519 = false;
		@Pc(838) int local838;
		@Pc(843) int local843;
		@Pc(848) int local848;
		@Pc(833) int local833;
		if (local1 == 3) {
			if (local88 < 0 || local93 < 0 || local100 < 0 || local88 > this.threadResource.anInt1120 || local93 > this.threadResource.anInt1120 || local100 > this.threadResource.anInt1120) {
				this.rasterizer.aBoolean519 = true;
			}
			if (this.faceTextures != null && this.faceTextures[face] != -1) {
				if (this.aByteArray88 == null || this.aByteArray88[face] == -1) {
					local838 = local10;
					local843 = local15;
					local848 = local20;
				} else {
					local833 = this.aByteArray88[face] & 0xFF;
					local838 = this.anIntArray474[local833];
					local843 = this.anIntArray452[local833];
					local848 = this.anIntArray447[local833];
				}
				if (this.faceColourC[face] == -1) {
					this.rasterizer.method4546(local113, local725, local730, local88, local93, local100, this.faceColourA[face], this.faceColourA[face], this.faceColourA[face], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], this.faceTextures[face]);
				} else {
					this.rasterizer.method4546(local113, local725, local730, local88, local93, local100, this.anIntArray449[0], this.anIntArray449[1], this.anIntArray449[2], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], this.faceTextures[face]);
				}
			} else if (this.faceColourC[face] == -1) {
				this.rasterizer.method4552(local113, local725, local730, local88, local93, local100, PathingEntity.anIntArray423[this.faceColourA[face] & 0xFFFF]);
			} else {
				this.rasterizer.method4547(local113, local725, local730, local88, local93, local100, this.anIntArray449[0], this.anIntArray449[1], this.anIntArray449[2]);
			}
		}
		if (local1 != 4) {
			return;
		}
		if (local88 < 0 || local93 < 0 || local100 < 0 || local88 > this.threadResource.anInt1120 || local93 > this.threadResource.anInt1120 || local100 > this.threadResource.anInt1120 || this.anIntArray462[3] < 0 || this.anIntArray462[3] > this.threadResource.anInt1120) {
			this.rasterizer.aBoolean519 = true;
		}
		if (this.faceTextures == null || this.faceTextures[face] == -1) {
			if (this.faceColourC[face] == -1) {
				local838 = PathingEntity.anIntArray423[this.faceColourA[face] & 0xFFFF];
				this.rasterizer.method4552(local113, local725, local730, local88, local93, local100, local838);
				this.rasterizer.method4552(local113, local730, this.anIntArray480[3], local88, local100, this.anIntArray462[3], local838);
				return;
			}
			this.rasterizer.method4547(local113, local725, local730, local88, local93, local100, this.anIntArray449[0], this.anIntArray449[1], this.anIntArray449[2]);
			this.rasterizer.method4547(local113, local730, this.anIntArray480[3], local88, local100, this.anIntArray462[3], this.anIntArray449[0], this.anIntArray449[2], this.anIntArray449[3]);
			return;
		}
		if (this.aByteArray88 == null || this.aByteArray88[face] == -1) {
			local838 = local10;
			local843 = local15;
			local848 = local20;
		} else {
			local833 = this.aByteArray88[face] & 0xFF;
			local838 = this.anIntArray474[local833];
			local843 = this.anIntArray452[local833];
			local848 = this.anIntArray447[local833];
		}
		@Pc(1168) short local1168 = this.faceTextures[face];
		if (this.faceColourC[face] == -1) {
			this.rasterizer.method4546(local113, local725, local730, local88, local93, local100, this.faceColourA[face], this.faceColourA[face], this.faceColourA[face], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], local1168);
			this.rasterizer.method4546(local113, local730, this.anIntArray480[3], local88, local100, this.anIntArray462[3], this.faceColourA[face], this.faceColourA[face], this.faceColourA[face], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], local1168);
			return;
		}
		this.rasterizer.method4546(local113, local725, local730, local88, local93, local100, this.anIntArray449[0], this.anIntArray449[1], this.anIntArray449[2], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], local1168);
		this.rasterizer.method4546(local113, local730, this.anIntArray480[3], local88, local100, this.anIntArray462[3], this.anIntArray449[0], this.anIntArray449[2], this.anIntArray449[3], this.anIntArray461[local838], this.anIntArray461[local843], this.anIntArray461[local848], this.anIntArray470[local838], this.anIntArray470[local843], this.anIntArray470[local848], this.cameraZ[local838], this.cameraZ[local843], this.cameraZ[local848], local1168);
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Ljava/lang/Thread;)V")
	private void useThreadCopyCache(@OriginalArg(0) Thread thread) {
		@Pc(4) JavaThreadResource local4 = this.toolkit.threadResource(thread);
		if (local4 == this.copyThreadResource) {
			return;
		}
		this.copyThreadResource = local4;
		this.copyDstModel1 = this.copyThreadResource.copyDstModel1;
		this.copyDstModel2 = this.copyThreadResource.copyDstModel2;
		this.copyDstModel3 = this.copyThreadResource.copyDstModel3;
		this.copyDstModel4 = this.copyThreadResource.copyDstModel4;
		this.copyDstModel5 = this.copyThreadResource.copyDstModel5;
		this.copySrcModel1 = this.copyThreadResource.copySrcModel1;
		this.copySrcModel2 = this.copyThreadResource.copySrcModel2;
		this.copySrcModel3 = this.copyThreadResource.copySrcModel3;
		this.copySrcModel4 = this.copyThreadResource.copySrcModel4;
		this.copySrcModel5 = this.copyThreadResource.copySrcModel5;
	}

	@OriginalMember(owner = "client!sk", name = "w", descriptor = "()Z")
	@Override
	protected boolean w() {
		if (this.anIntArrayArray55 == null) {
			return false;
		} else {
			this.pivotX = 0;
			this.pivotY = 0;
			this.pivotZ = 0;
			return true;
		}
	}

	@OriginalMember(owner = "client!sk", name = "U", descriptor = "()I")
	@Override
	public int U() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.radius;
	}

	@OriginalMember(owner = "client!sk", name = "UA", descriptor = "(I)V")
	@Override
	public void UA(@OriginalArg(0) int functionMask) {
		if ((this.functionMask & 0x10000) == 65536 && (functionMask & 0x10000) == 0) {
			this.calculateLighting(true);
		}
		this.functionMask = functionMask;
	}

	@OriginalMember(owner = "client!sk", name = "E", descriptor = "(I)V")
	@Override
	public void E(@OriginalArg(0) int ambient) {
		if ((this.functionMask & 0x1000) != 4096) {
			throw new IllegalStateException();
		}
		this.ambient = ambient;
		this.lightingState = 0;
	}

	@OriginalMember(owner = "client!sk", name = "N", descriptor = "(IIII)V")
	@Override
	protected void N(@OriginalArg(0) int type, @OriginalArg(1) int pivotX, @OriginalArg(2) int pivotY, @OriginalArg(3) int pivotZ) {
		@Pc(3) int local3;
		@Pc(14) int local14;
		if (type == 0) {
			local3 = 0;
			this.pivotX = 0;
			this.pivotY = 0;
			this.pivotZ = 0;
			for (local14 = 0; local14 < this.vertexCount; local14++) {
				this.pivotX += this.vertexX[local14];
				this.pivotY += this.vertexY[local14];
				this.pivotZ += this.vertexZ[local14];
				local3++;
			}
			if (local3 > 0) {
				this.pivotX = this.pivotX / local3 + pivotX;
				this.pivotY = this.pivotY / local3 + pivotY;
				this.pivotZ = this.pivotZ / local3 + pivotZ;
			} else {
				this.pivotX = pivotX;
				this.pivotY = pivotY;
				this.pivotZ = pivotZ;
			}
		} else if (type == 1) {
			for (local3 = 0; local3 < this.vertexCount; local3++) {
				this.vertexX[local3] += pivotX;
				this.vertexY[local3] += pivotY;
				this.vertexZ[local3] += pivotZ;
			}
		} else {
			@Pc(164) int local164;
			@Pc(182) int local182;
			if (type == 2) {
				for (local3 = 0; local3 < this.vertexCount; local3++) {
					this.vertexX[local3] -= this.pivotX;
					this.vertexY[local3] -= this.pivotY;
					this.vertexZ[local3] -= this.pivotZ;
					if (pivotZ != 0) {
						local14 = Class50_Sub1.anIntArray225[pivotZ];
						local164 = Class50_Sub1.anIntArray224[pivotZ];
						local182 = this.vertexY[local3] * local14 + this.vertexX[local3] * local164 + 32767 >> 15;
						this.vertexY[local3] = this.vertexY[local3] * local164 + 32767 - this.vertexX[local3] * local14 >> 15;
						this.vertexX[local3] = local182;
					}
					if (pivotX != 0) {
						local14 = Class50_Sub1.anIntArray225[pivotX];
						local164 = Class50_Sub1.anIntArray224[pivotX];
						local182 = this.vertexY[local3] * local164 + 32767 - this.vertexZ[local3] * local14 >> 15;
						this.vertexZ[local3] = this.vertexY[local3] * local14 + this.vertexZ[local3] * local164 + 32767 >> 15;
						this.vertexY[local3] = local182;
					}
					if (pivotY != 0) {
						local14 = Class50_Sub1.anIntArray225[pivotY];
						local164 = Class50_Sub1.anIntArray224[pivotY];
						local182 = this.vertexZ[local3] * local14 + this.vertexX[local3] * local164 + 32767 >> 15;
						this.vertexZ[local3] = this.vertexZ[local3] * local164 + 32767 - this.vertexX[local3] * local14 >> 15;
						this.vertexX[local3] = local182;
					}
					this.vertexX[local3] += this.pivotX;
					this.vertexY[local3] += this.pivotY;
					this.vertexZ[local3] += this.pivotZ;
				}
			} else if (type == 3) {
				for (local3 = 0; local3 < this.vertexCount; local3++) {
					this.vertexX[local3] -= this.pivotX;
					this.vertexY[local3] -= this.pivotY;
					this.vertexZ[local3] -= this.pivotZ;
					this.vertexX[local3] = this.vertexX[local3] * pivotX / 128;
					this.vertexY[local3] = this.vertexY[local3] * pivotY / 128;
					this.vertexZ[local3] = this.vertexZ[local3] * pivotZ / 128;
					this.vertexX[local3] += this.pivotX;
					this.vertexY[local3] += this.pivotY;
					this.vertexZ[local3] += this.pivotZ;
				}
			} else {
				@Pc(500) JavaBillboardFace local500;
				@Pc(505) JavaBillboardAttributes local505;
				if (type == 5) {
					for (local3 = 0; local3 < this.faceCount; local3++) {
						local14 = (this.faceAlpha[local3] & 0xFF) + pivotX * 8;
						if (local14 < 0) {
							local14 = 0;
						} else if (local14 > 255) {
							local14 = 255;
						}
						this.faceAlpha[local3] = (byte) local14;
					}
					if (this.billboardFaces != null) {
						for (local14 = 0; local14 < this.billboardCount; local14++) {
							local500 = this.billboardFaces[local14];
							local505 = this.billboardAttributes[local14];
							local505.diffuseColor = local505.diffuseColor & 0xFFFFFF | 255 - (this.faceAlpha[local500.anInt3925] & 0xFF) << 24;
						}
					}
				} else if (type == 7) {
					for (local3 = 0; local3 < this.faceCount; local3++) {
						local14 = this.faceColour[local3] & 0xFFFF;
						local164 = local14 >> 10 & 0x3F;
						local182 = local14 >> 7 & 0x7;
						@Pc(558) int local558 = local14 & 0x7F;
						@Pc(564) int local564 = local164 + pivotX & 0x3F;
						local182 += pivotY;
						if (local182 < 0) {
							local182 = 0;
						} else if (local182 > 7) {
							local182 = 7;
						}
						local558 += pivotZ;
						if (local558 < 0) {
							local558 = 0;
						} else if (local558 > 127) {
							local558 = 127;
						}
						this.faceColour[local3] = (short) (local564 << 10 | local182 << 7 | local558);
					}
					this.recoloured = true;
					if (this.billboardFaces != null) {
						for (local14 = 0; local14 < this.billboardCount; local14++) {
							local500 = this.billboardFaces[local14];
							local505 = this.billboardAttributes[local14];
							local505.diffuseColor = local505.diffuseColor & 0xFF000000 | PathingEntity.anIntArray423[Class56.method1470(this.faceColour[local500.anInt3925] & 0xFFFF)] & 0xFFFFFF;
						}
					}
				} else {
					@Pc(667) JavaBillboardAttributes local667;
					if (type == 8) {
						for (local3 = 0; local3 < this.billboardCount; local3++) {
							local667 = this.billboardAttributes[local3];
							local667.offsetX += pivotX;
							local667.offsetY += pivotY;
						}
					} else if (type == 10) {
						for (local3 = 0; local3 < this.billboardCount; local3++) {
							local667 = this.billboardAttributes[local3];
							local667.scaleX = local667.scaleX * pivotX >> 7;
							local667.scaleY = local667.scaleY * pivotY >> 7;
						}
					} else if (type == 9) {
						for (local3 = 0; local3 < this.billboardCount; local3++) {
							local667 = this.billboardAttributes[local3];
							local667.rotation = local667.rotation + pivotX & 0x3FFF;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!wp;)V")
	private void method4907(@OriginalArg(0) Class31_Sub2 arg0) {
		@Pc(4) int local4;
		if (this.emitters != null) {
			for (local4 = 0; local4 < this.emitters.length; local4++) {
				@Pc(10) EmissiveTriangle local10 = this.emitters[local4];
				@Pc(12) EmissiveTriangle local12 = local10;
				if (local10.aClass29_1 != null) {
					local12 = local10.aClass29_1;
				}
				local12.anInt699 = arg0.anInt7329 + (arg0.anInt7334 * this.vertexX[local10.anInt706] + arg0.anInt7328 * this.vertexY[local10.anInt706] + arg0.anInt7332 * this.vertexZ[local10.anInt706] >> 15);
				local12.anInt700 = arg0.anInt7324 + (arg0.anInt7331 * this.vertexX[local10.anInt706] + arg0.anInt7326 * this.vertexY[local10.anInt706] + arg0.anInt7327 * this.vertexZ[local10.anInt706] >> 15);
				local12.anInt711 = arg0.anInt7333 + (arg0.anInt7323 * this.vertexX[local10.anInt706] + arg0.anInt7330 * this.vertexY[local10.anInt706] + arg0.anInt7325 * this.vertexZ[local10.anInt706] >> 15);
				local12.anInt701 = arg0.anInt7329 + (arg0.anInt7334 * this.vertexX[local10.anInt713] + arg0.anInt7328 * this.vertexY[local10.anInt713] + arg0.anInt7332 * this.vertexZ[local10.anInt713] >> 15);
				local12.anInt702 = arg0.anInt7324 + (arg0.anInt7331 * this.vertexX[local10.anInt713] + arg0.anInt7326 * this.vertexY[local10.anInt713] + arg0.anInt7327 * this.vertexZ[local10.anInt713] >> 15);
				local12.anInt707 = arg0.anInt7333 + (arg0.anInt7323 * this.vertexX[local10.anInt713] + arg0.anInt7330 * this.vertexY[local10.anInt713] + arg0.anInt7325 * this.vertexZ[local10.anInt713] >> 15);
				local12.anInt708 = arg0.anInt7329 + (arg0.anInt7334 * this.vertexX[local10.anInt698] + arg0.anInt7328 * this.vertexY[local10.anInt698] + arg0.anInt7332 * this.vertexZ[local10.anInt698] >> 15);
				local12.anInt703 = arg0.anInt7324 + (arg0.anInt7331 * this.vertexX[local10.anInt698] + arg0.anInt7326 * this.vertexY[local10.anInt698] + arg0.anInt7327 * this.vertexZ[local10.anInt698] >> 15);
				local12.anInt709 = arg0.anInt7333 + (arg0.anInt7323 * this.vertexX[local10.anInt698] + arg0.anInt7330 * this.vertexY[local10.anInt698] + arg0.anInt7325 * this.vertexZ[local10.anInt698] >> 15);
			}
		}
		if (this.effectors == null) {
			return;
		}
		for (local4 = 0; local4 < this.effectors.length; local4++) {
			@Pc(332) EffectiveVertex local332 = this.effectors[local4];
			@Pc(334) EffectiveVertex local334 = local332;
			if (local332.aClass179_2 != null) {
				local334 = local332.aClass179_2;
			}
			if (local332.aClass31_4 == null) {
				local332.aClass31_4 = arg0.method5657();
			} else {
				local332.aClass31_4.UnlitEffect(arg0);
			}
			local334.anInt5342 = arg0.anInt7329 + (arg0.anInt7334 * this.vertexX[local332.appliesOnVertex] + arg0.anInt7328 * this.vertexY[local332.appliesOnVertex] + arg0.anInt7332 * this.vertexZ[local332.appliesOnVertex] >> 15);
			local334.anInt5340 = arg0.anInt7324 + (arg0.anInt7331 * this.vertexX[local332.appliesOnVertex] + arg0.anInt7326 * this.vertexY[local332.appliesOnVertex] + arg0.anInt7327 * this.vertexZ[local332.appliesOnVertex] >> 15);
			local334.anInt5347 = arg0.anInt7333 + (arg0.anInt7323 * this.vertexX[local332.appliesOnVertex] + arg0.anInt7330 * this.vertexY[local332.appliesOnVertex] + arg0.anInt7325 * this.vertexZ[local332.appliesOnVertex] >> 15);
		}
	}

	@OriginalMember(owner = "client!sk", name = "I", descriptor = "(SS)V")
	@Override
	public void I(@OriginalArg(0) short src, @OriginalArg(1) short dst) {
		if (this.faceTextures == null) {
			return;
		}
		for (@Pc(5) int local5 = 0; local5 < this.faceCount; local5++) {
			if (this.faceTextures[local5] == src) {
				this.faceTextures[local5] = dst;
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "Q", descriptor = "(I)V")
	@Override
	public void Q(@OriginalArg(0) int angle) {
		if ((this.functionMask & 0x5) != 5) {
			throw new IllegalStateException();
		} else if (angle == 4096) {
			this.rotate90();
		} else if (angle == 8192) {
			this.rotate180();
		} else if (angle == 12288) {
			this.rotate270();
		} else {
			@Pc(31) int local31 = Class50_Sub1.anIntArray225[angle];
			@Pc(35) int local35 = Class50_Sub1.anIntArray224[angle];
			for (@Pc(37) int local37 = 0; local37 < this.vertexCount; local37++) {
				@Pc(54) int local54 = this.vertexZ[local37] * local31 + this.vertexX[local37] * local35 >> 15;
				this.vertexZ[local37] = this.vertexZ[local37] * local35 - this.vertexX[local37] * local31 >> 15;
				this.vertexX[local37] = local54;
			}
			this.invalidateNormals();
		}
	}

	@OriginalMember(owner = "client!sk", name = "b", descriptor = "(I)V")
	private void drawTriangleRgb(@OriginalArg(0) int face) {
		if (this.aBooleanArray19[face]) {
			this.drawClippedTriangleRgb(face);
			return;
		}
		@Pc(13) short local13 = this.faceA[face];
		@Pc(18) short local18 = this.faceB[face];
		@Pc(23) short local23 = this.faceC[face];
		this.rasterizer.aBoolean519 = this.aBooleanArray18[face];
		if (this.faceAlpha == null) {
			this.rasterizer.alpha = 0;
		} else {
			this.rasterizer.alpha = this.faceAlpha[face] & 0xFF;
		}
		if (this.faceTextures != null && this.faceTextures[face] != -1) {
			@Pc(165) int local165;
			@Pc(170) int local170;
			@Pc(175) int local175;
			if (this.aByteArray88 == null || this.aByteArray88[face] == -1) {
				local165 = local13;
				local170 = local18;
				local175 = local23;
			} else {
				@Pc(160) int local160 = this.aByteArray88[face] & 0xFF;
				local165 = this.anIntArray474[local160];
				local170 = this.anIntArray452[local160];
				local175 = this.anIntArray447[local160];
			}
			if (this.faceColourC[face] == -1) {
				this.rasterizer.method4546(this.screenY[local13], this.screenY[local18], this.screenY[local23], this.screenX[local13], this.screenX[local18], this.screenX[local23], this.faceColourA[face], this.faceColourA[face], this.faceColourA[face], this.anIntArray461[local165], this.anIntArray461[local170], this.anIntArray461[local175], this.anIntArray470[local165], this.anIntArray470[local170], this.anIntArray470[local175], this.cameraZ[local165], this.cameraZ[local170], this.cameraZ[local175], this.faceTextures[face]);
			} else {
				this.rasterizer.method4546(this.screenY[local13], this.screenY[local18], this.screenY[local23], this.screenX[local13], this.screenX[local18], this.screenX[local23], this.faceColourA[face], this.faceColourB[face], this.faceColourC[face], this.anIntArray461[local165], this.anIntArray461[local170], this.anIntArray461[local175], this.anIntArray470[local165], this.anIntArray470[local170], this.anIntArray470[local175], this.cameraZ[local165], this.cameraZ[local170], this.cameraZ[local175], this.faceTextures[face]);
			}
		} else if (this.faceColourC[face] == -1) {
			this.rasterizer.method4552(this.screenY[local13], this.screenY[local18], this.screenY[local23], this.screenX[local13], this.screenX[local18], this.screenX[local23], PathingEntity.anIntArray423[this.faceColourA[face] & 0xFFFF]);
		} else {
			this.rasterizer.method4547(this.screenY[local13], this.screenY[local18], this.screenY[local23], this.screenX[local13], this.screenX[local18], this.screenX[local23], this.faceColourA[face] & 0xFFFF, this.faceColourB[face] & 0xFFFF, this.faceColourC[face] & 0xFFFF);
		}
	}

	@OriginalMember(owner = "client!sk", name = "R", descriptor = "(I)V")
	@Override
	public void R(@OriginalArg(0) int arg0) {
		if ((this.functionMask & 0x3) != 3) {
			throw new IllegalStateException();
		}
		@Pc(13) int local13 = Class50_Sub1.anIntArray225[arg0];
		@Pc(17) int local17 = Class50_Sub1.anIntArray224[arg0];
		for (@Pc(19) int local19 = 0; local19 < this.vertexCount; local19++) {
			@Pc(36) int local36 = this.vertexY[local19] * local13 + this.vertexX[local19] * local17 >> 15;
			this.vertexY[local19] = this.vertexY[local19] * local17 - this.vertexX[local19] * local13 >> 15;
			this.vertexX[local19] = local36;
		}
		this.invalidateNormals();
	}

	@OriginalMember(owner = "client!sk", name = "WA", descriptor = "()I")
	@Override
	public int WA() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.maxY;
	}

	@OriginalMember(owner = "client!sk", name = "d", descriptor = "()Z")
	@Override
	public boolean loadedTextures() {
		if (this.faceTextures == null) {
			return true;
		}
		for (@Pc(6) int local6 = 0; local6 < this.faceTextures.length; local6++) {
			if (this.faceTextures[local6] != -1 && !this.toolkit.textureAvailable(this.faceTextures[local6])) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!c;Lclient!mi;I)V")
	@Override
	public void render(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon cylinder, @OriginalArg(2) int flags) {
		this.method4915(arg0, cylinder, null, -1, flags);
	}

	@OriginalMember(owner = "client!sk", name = "m", descriptor = "()I")
	@Override
	public int addLight() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.maxZ;
	}

	@OriginalMember(owner = "client!sk", name = "IA", descriptor = "()V")
	@Override
	public void IA() {
		if ((this.functionMask & 0x10) != 16) {
			throw new IllegalStateException();
		}
		for (@Pc(11) int local11 = 0; local11 < this.vertexCount; local11++) {
			this.vertexZ[local11] = -this.vertexZ[local11];
		}
		@Pc(31) int local31;
		if (this.aClass86Array1 != null) {
			for (local31 = 0; local31 < this.maxVertex; local31++) {
				if (this.aClass86Array1[local31] != null) {
					this.aClass86Array1[local31].z = -this.aClass86Array1[local31].z;
				}
			}
		}
		if (this.vertexNormalsOffset != null) {
			for (local31 = 0; local31 < this.maxVertex; local31++) {
				if (this.vertexNormalsOffset[local31] != null) {
					this.vertexNormalsOffset[local31].z = -this.vertexNormalsOffset[local31].z;
				}
			}
		}
		if (this.faceNormals != null) {
			for (local31 = 0; local31 < this.faceCount; local31++) {
				if (this.faceNormals[local31] != null) {
					this.faceNormals[local31].z = -this.faceNormals[local31].z;
				}
			}
		}
		@Pc(110) short[] local110 = this.faceA;
		this.faceA = this.faceC;
		this.faceC = local110;
		this.boundsValid = false;
		this.lightingState = 0;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!t;IIIZ)V")
	@Override
	public void shareLight(@OriginalArg(0) Model other, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) boolean renderTypeAdjustment) {
		@Pc(2) JavaModel local2 = (JavaModel) other;
		if ((this.functionMask & 0x10000) != 65536) {
			throw new IllegalStateException("");
		} else if ((local2.functionMask & 0x10000) == 65536) {
			this.useThreadBuffers(Thread.currentThread());
			this.calculateBounds();
			this.calculateNormals();
			local2.calculateBounds();
			local2.calculateNormals();
			JavaModel.renderCycle++;
			@Pc(41) int local41 = 0;
			@Pc(44) int[] local44 = local2.vertexX;
			@Pc(47) int local47 = local2.maxVertex;
			@Pc(65) int local65;
			for (@Pc(49) int local49 = 0; local49 < this.maxVertex; local49++) {
				@Pc(55) VertexNormal local55 = this.aClass86Array1[local49];
				if (local55.magnitude != 0) {
					local65 = this.vertexY[local49] - y;
					if (local65 >= local2.minY && local65 <= local2.maxY) {
						@Pc(81) int local81 = this.vertexX[local49] - x;
						if (local81 >= local2.minX && local81 <= local2.maxX) {
							@Pc(97) int local97 = this.vertexZ[local49] - z;
							if (local97 >= local2.minZ && local97 <= local2.maxZ) {
								for (@Pc(108) int local108 = 0; local108 < local47; local108++) {
									@Pc(114) VertexNormal local114 = local2.aClass86Array1[local108];
									if (local81 == local44[local108] && local97 == local2.vertexZ[local108] && local65 == local2.vertexY[local108] && local114.magnitude != 0) {
										if (this.vertexNormalsOffset == null) {
											this.vertexNormalsOffset = new VertexNormal[this.maxVertex];
										}
										if (local2.vertexNormalsOffset == null) {
											local2.vertexNormalsOffset = new VertexNormal[local47];
										}
										@Pc(154) VertexNormal local154 = this.vertexNormalsOffset[local49];
										if (local154 == null) {
											local154 = this.vertexNormalsOffset[local49] = new VertexNormal(local55);
										}
										@Pc(171) VertexNormal local171 = local2.vertexNormalsOffset[local108];
										if (local171 == null) {
											local171 = local2.vertexNormalsOffset[local108] = new VertexNormal(local114);
										}
										local154.x += local114.x;
										local154.y += local114.y;
										local154.z += local114.z;
										local154.magnitude += local114.magnitude;
										local171.x += local55.x;
										local171.y += local55.y;
										local171.z += local55.z;
										local171.magnitude += local55.magnitude;
										local41++;
										this.anIntArray468[local49] = JavaModel.renderCycle;
										this.anIntArray455[local108] = JavaModel.renderCycle;
									}
								}
							}
						}
					}
				}
			}
			if (local41 >= 3 && renderTypeAdjustment) {
				for (@Pc(267) int local267 = 0; local267 < this.faceCount; local267++) {
					if (this.anIntArray468[this.faceA[local267]] == JavaModel.renderCycle && this.anIntArray468[this.faceB[local267]] == JavaModel.renderCycle && this.anIntArray468[this.faceC[local267]] == JavaModel.renderCycle) {
						if (this.shadingType == null) {
							this.shadingType = new byte[this.faceCount];
						}
						this.shadingType[local267] = 2;
					}
				}
				for (local65 = 0; local65 < local2.faceCount; local65++) {
					if (this.anIntArray455[local2.faceA[local65]] == JavaModel.renderCycle && this.anIntArray455[local2.faceB[local65]] == JavaModel.renderCycle && this.anIntArray455[local2.faceC[local65]] == JavaModel.renderCycle) {
						if (local2.shadingType == null) {
							local2.shadingType = new byte[local2.faceCount];
						}
						local2.shadingType[local65] = 2;
					}
				}
			}
		} else {
			throw new IllegalStateException("");
		}
	}

	@OriginalMember(owner = "client!sk", name = "aa", descriptor = "(I)V")
	@Override
	public void aa(@OriginalArg(0) int contrast) {
		if ((this.functionMask & 0x2000) != 8192) {
			throw new IllegalStateException();
		}
		this.contrast = contrast;
		this.lightingState = 0;
	}

	@OriginalMember(owner = "client!sk", name = "c", descriptor = "(I)V")
	@Override
	public void c(@OriginalArg(0) int angle) {
		if ((this.functionMask & 0xD) != 13) {
			throw new IllegalStateException();
		} else if (this.aClass86Array1 == null) {
			this.Q(angle);
		} else if (angle == 4096) {
			this.rotate90WithNormals();
		} else if (angle == 8192) {
			this.rotate180WithNormals();
		} else if (angle == 12288) {
			this.rotate270WithNormals();
		} else {
			@Pc(34) int local34 = Class50_Sub1.anIntArray225[angle];
			@Pc(38) int local38 = Class50_Sub1.anIntArray224[angle];
			@Pc(57) int local57;
			for (@Pc(40) int local40 = 0; local40 < this.maxVertex; local40++) {
				local57 = this.vertexZ[local40] * local34 + this.vertexX[local40] * local38 >> 15;
				this.vertexZ[local40] = this.vertexZ[local40] * local38 - this.vertexX[local40] * local34 >> 15;
				this.vertexX[local40] = local57;
				if (this.aClass86Array1[local40] != null) {
					local57 = this.aClass86Array1[local40].z * local34 + this.aClass86Array1[local40].x * local38 >> 15;
					this.aClass86Array1[local40].z = this.aClass86Array1[local40].z * local38 - this.aClass86Array1[local40].x * local34 >> 15;
					this.aClass86Array1[local40].x = local57;
				}
			}
			@Pc(166) int local166;
			if (this.faceNormals != null) {
				for (local57 = 0; local57 < this.faceCount; local57++) {
					if (this.faceNormals[local57] != null) {
						local166 = this.faceNormals[local57].z * local34 + this.faceNormals[local57].x * local38 >> 15;
						this.faceNormals[local57].z = this.faceNormals[local57].z * local38 - this.faceNormals[local57].x * local34 >> 15;
						this.faceNormals[local57].x = local166;
					}
				}
			}
			for (local57 = this.maxVertex; local57 < this.vertexCount; local57++) {
				local166 = this.vertexZ[local57] * local34 + this.vertexX[local57] * local38 >> 15;
				this.vertexZ[local57] = this.vertexZ[local57] * local38 - this.vertexX[local57] * local34 >> 15;
				this.vertexX[local57] = local166;
			}
			this.lightingState = 0;
			this.boundsValid = false;
		}
	}

	@OriginalMember(owner = "client!sk", name = "H", descriptor = "(III)V")
	@Override
	public void H(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ) {
		if (scaleX != 128 && (this.functionMask & 0x1) != 1) {
			throw new IllegalStateException();
		} else if (scaleY != 128 && (this.functionMask & 0x2) != 2) {
			throw new IllegalStateException();
		} else if (scaleZ == 128 || (this.functionMask & 0x4) == 4) {
			for (@Pc(40) int local40 = 0; local40 < this.vertexCount; local40++) {
				this.vertexX[local40] = this.vertexX[local40] * scaleX >> 7;
				this.vertexY[local40] = this.vertexY[local40] * scaleY >> 7;
				this.vertexZ[local40] = this.vertexZ[local40] * scaleZ >> 7;
			}
			this.boundsValid = false;
		} else {
			throw new IllegalStateException();
		}
	}

	@OriginalMember(owner = "client!sk", name = "j", descriptor = "()V")
	@Override
	protected void scaleWidth() {
		if (this.highPrecisionVertices) {
			for (@Pc(4) int local4 = 0; local4 < this.vertexCount; local4++) {
				this.vertexX[local4] = this.vertexX[local4] + 7 >> 4;
				this.vertexY[local4] = this.vertexY[local4] + 7 >> 4;
				this.vertexZ[local4] = this.vertexZ[local4] + 7 >> 4;
			}
			this.highPrecisionVertices = false;
		}
		if (this.recoloured) {
			this.relightColours();
			this.recoloured = false;
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "P", descriptor = "()I")
	@Override
	public int P() {
		return this.functionMask;
	}

	@OriginalMember(owner = "client!sk", name = "XA", descriptor = "(I[IIIIIZ)V")
	@Override
	protected void applyTransformUnmasked(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) int rotation, @OriginalArg(6) boolean lights) {
		@Pc(2) int local2 = labels.length;
		@Pc(21) int local21;
		@Pc(67) int local67;
		@Pc(89) int local89;
		@Pc(8) int local8;
		@Pc(12) int local12;
		@Pc(16) int local16;
		@Pc(84) int local84;
		if (type == 0) {
			local8 = x << 4;
			local12 = y << 4;
			local16 = z << 4;
			if (!this.highPrecisionVertices) {
				for (local21 = 0; local21 < this.vertexCount; local21++) {
					this.vertexX[local21] <<= 0x4;
					this.vertexY[local21] <<= 0x4;
					this.vertexZ[local21] <<= 0x4;
				}
				this.highPrecisionVertices = true;
			}
			local21 = 0;
			this.pivotX = 0;
			this.pivotY = 0;
			this.pivotZ = 0;
			for (local67 = 0; local67 < local2; local67++) {
				@Pc(72) int local72 = labels[local67];
				if (local72 < this.anIntArrayArray55.length) {
					@Pc(82) int[] local82 = this.anIntArrayArray55[local72];
					for (local84 = 0; local84 < local82.length; local84++) {
						local89 = local82[local84];
						this.pivotX += this.vertexX[local89];
						this.pivotY += this.vertexY[local89];
						this.pivotZ += this.vertexZ[local89];
						local21++;
					}
				}
			}
			if (local21 > 0) {
				this.pivotX = this.pivotX / local21 + local8;
				this.pivotY = this.pivotY / local21 + local12;
				this.pivotZ = this.pivotZ / local21 + local16;
			} else {
				this.pivotX = local8;
				this.pivotY = local12;
				this.pivotZ = local16;
			}
			return;
		}
		@Pc(233) int[] local233;
		@Pc(235) int local235;
		if (type == 1) {
			local8 = x << 4;
			local12 = y << 4;
			local16 = z << 4;
			if (!this.highPrecisionVertices) {
				for (local21 = 0; local21 < this.vertexCount; local21++) {
					this.vertexX[local21] <<= 0x4;
					this.vertexY[local21] <<= 0x4;
					this.vertexZ[local21] <<= 0x4;
				}
				this.highPrecisionVertices = true;
			}
			for (local21 = 0; local21 < local2; local21++) {
				local67 = labels[local21];
				if (local67 < this.anIntArrayArray55.length) {
					local233 = this.anIntArrayArray55[local67];
					for (local235 = 0; local235 < local233.length; local235++) {
						local84 = local233[local235];
						this.vertexX[local84] += local8;
						this.vertexY[local84] += local12;
						this.vertexZ[local84] += local16;
					}
				}
			}
			return;
		}
		@Pc(342) int local342;
		@Pc(360) int local360;
		if (type == 2) {
			for (local21 = 0; local21 < local2; local21++) {
				local67 = labels[local21];
				if (local67 < this.anIntArrayArray55.length) {
					local233 = this.anIntArrayArray55[local67];
					if ((rotation & 0x1) == 0) {
						for (local235 = 0; local235 < local233.length; local235++) {
							local84 = local233[local235];
							this.vertexX[local84] -= this.pivotX;
							this.vertexY[local84] -= this.pivotY;
							this.vertexZ[local84] -= this.pivotZ;
							if (z != 0) {
								local89 = Class50_Sub1.anIntArray225[z];
								local342 = Class50_Sub1.anIntArray224[z];
								local360 = this.vertexY[local84] * local89 + this.vertexX[local84] * local342 + 32767 >> 15;
								this.vertexY[local84] = this.vertexY[local84] * local342 + 32767 - this.vertexX[local84] * local89 >> 15;
								this.vertexX[local84] = local360;
							}
							if (x != 0) {
								local89 = Class50_Sub1.anIntArray225[x];
								local342 = Class50_Sub1.anIntArray224[x];
								local360 = this.vertexY[local84] * local342 + 32767 - this.vertexZ[local84] * local89 >> 15;
								this.vertexZ[local84] = this.vertexY[local84] * local89 + this.vertexZ[local84] * local342 + 32767 >> 15;
								this.vertexY[local84] = local360;
							}
							if (y != 0) {
								local89 = Class50_Sub1.anIntArray225[y];
								local342 = Class50_Sub1.anIntArray224[y];
								local360 = this.vertexZ[local84] * local89 + this.vertexX[local84] * local342 + 32767 >> 15;
								this.vertexZ[local84] = this.vertexZ[local84] * local342 + 32767 - this.vertexX[local84] * local89 >> 15;
								this.vertexX[local84] = local360;
							}
							this.vertexX[local84] += this.pivotX;
							this.vertexY[local84] += this.pivotY;
							this.vertexZ[local84] += this.pivotZ;
						}
					} else {
						for (local235 = 0; local235 < local233.length; local235++) {
							local84 = local233[local235];
							this.vertexX[local84] -= this.pivotX;
							this.vertexY[local84] -= this.pivotY;
							this.vertexZ[local84] -= this.pivotZ;
							if (x != 0) {
								local89 = Class50_Sub1.anIntArray225[x];
								local342 = Class50_Sub1.anIntArray224[x];
								local360 = this.vertexY[local84] * local342 + 32767 - this.vertexZ[local84] * local89 >> 15;
								this.vertexZ[local84] = this.vertexY[local84] * local89 + this.vertexZ[local84] * local342 + 32767 >> 15;
								this.vertexY[local84] = local360;
							}
							if (z != 0) {
								local89 = Class50_Sub1.anIntArray225[z];
								local342 = Class50_Sub1.anIntArray224[z];
								local360 = this.vertexY[local84] * local89 + this.vertexX[local84] * local342 + 32767 >> 15;
								this.vertexY[local84] = this.vertexY[local84] * local342 + 32767 - this.vertexX[local84] * local89 >> 15;
								this.vertexX[local84] = local360;
							}
							if (y != 0) {
								local89 = Class50_Sub1.anIntArray225[y];
								local342 = Class50_Sub1.anIntArray224[y];
								local360 = this.vertexZ[local84] * local89 + this.vertexX[local84] * local342 + 32767 >> 15;
								this.vertexZ[local84] = this.vertexZ[local84] * local342 + 32767 - this.vertexX[local84] * local89 >> 15;
								this.vertexX[local84] = local360;
							}
							this.vertexX[local84] += this.pivotX;
							this.vertexY[local84] += this.pivotY;
							this.vertexZ[local84] += this.pivotZ;
						}
					}
				}
			}
		} else if (type == 3) {
			for (local21 = 0; local21 < local2; local21++) {
				local67 = labels[local21];
				if (local67 < this.anIntArrayArray55.length) {
					local233 = this.anIntArrayArray55[local67];
					for (local235 = 0; local235 < local233.length; local235++) {
						local84 = local233[local235];
						this.vertexX[local84] -= this.pivotX;
						this.vertexY[local84] -= this.pivotY;
						this.vertexZ[local84] -= this.pivotZ;
						this.vertexX[local84] = this.vertexX[local84] * x / 128;
						this.vertexY[local84] = this.vertexY[local84] * y / 128;
						this.vertexZ[local84] = this.vertexZ[local84] * z / 128;
						this.vertexX[local84] += this.pivotX;
						this.vertexY[local84] += this.pivotY;
						this.vertexZ[local84] += this.pivotZ;
					}
				}
			}
		} else {
			@Pc(967) JavaBillboardFace local967;
			@Pc(972) JavaBillboardAttributes local972;
			if (type == 5) {
				if (this.anIntArrayArray52 != null && this.faceAlpha != null) {
					for (local21 = 0; local21 < local2; local21++) {
						local67 = labels[local21];
						if (local67 < this.anIntArrayArray52.length) {
							local233 = this.anIntArrayArray52[local67];
							for (local235 = 0; local235 < local233.length; local235++) {
								local84 = local233[local235];
								local89 = (this.faceAlpha[local84] & 0xFF) + x * 8;
								if (local89 < 0) {
									local89 = 0;
								} else if (local89 > 255) {
									local89 = 255;
								}
								this.faceAlpha[local84] = (byte) local89;
							}
						}
					}
					if (this.billboardFaces != null) {
						for (local67 = 0; local67 < this.billboardCount; local67++) {
							local967 = this.billboardFaces[local67];
							local972 = this.billboardAttributes[local67];
							local972.diffuseColor = local972.diffuseColor & 0xFFFFFF | 255 - (this.faceAlpha[local967.anInt3925] & 0xFF) << 24;
						}
					}
				}
			} else if (type != 7) {
				@Pc(1186) JavaBillboardAttributes local1186;
				if (type == 8) {
					if (this.anIntArrayArray54 != null) {
						for (local21 = 0; local21 < local2; local21++) {
							local67 = labels[local21];
							if (local67 < this.anIntArrayArray54.length) {
								local233 = this.anIntArrayArray54[local67];
								for (local235 = 0; local235 < local233.length; local235++) {
									local1186 = this.billboardAttributes[local233[local235]];
									local1186.offsetX += x;
									local1186.offsetY += y;
								}
							}
						}
					}
				} else if (type == 10) {
					if (this.anIntArrayArray54 != null) {
						for (local21 = 0; local21 < local2; local21++) {
							local67 = labels[local21];
							if (local67 < this.anIntArrayArray54.length) {
								local233 = this.anIntArrayArray54[local67];
								for (local235 = 0; local235 < local233.length; local235++) {
									local1186 = this.billboardAttributes[local233[local235]];
									local1186.scaleX = local1186.scaleX * x >> 7;
									local1186.scaleY = local1186.scaleY * y >> 7;
								}
							}
						}
					}
				} else if (type == 9 && this.anIntArrayArray54 != null) {
					for (local21 = 0; local21 < local2; local21++) {
						local67 = labels[local21];
						if (local67 < this.anIntArrayArray54.length) {
							local233 = this.anIntArrayArray54[local67];
							for (local235 = 0; local235 < local233.length; local235++) {
								local1186 = this.billboardAttributes[local233[local235]];
								local1186.rotation = local1186.rotation + x & 0x3FFF;
							}
						}
					}
				}
			} else if (this.anIntArrayArray52 != null) {
				for (local21 = 0; local21 < local2; local21++) {
					local67 = labels[local21];
					if (local67 < this.anIntArrayArray52.length) {
						local233 = this.anIntArrayArray52[local67];
						for (local235 = 0; local235 < local233.length; local235++) {
							local84 = local233[local235];
							local89 = this.faceColour[local84] & 0xFFFF;
							local342 = local89 >> 10 & 0x3F;
							local360 = local89 >> 7 & 0x7;
							@Pc(1049) int local1049 = local89 & 0x7F;
							@Pc(1055) int local1055 = local342 + x & 0x3F;
							local360 += y;
							if (local360 < 0) {
								local360 = 0;
							} else if (local360 > 7) {
								local360 = 7;
							}
							local1049 += z;
							if (local1049 < 0) {
								local1049 = 0;
							} else if (local1049 > 127) {
								local1049 = 127;
							}
							this.faceColour[local84] = (short) (local1055 << 10 | local360 << 7 | local1049);
						}
						this.recoloured = true;
					}
				}
				if (this.billboardFaces != null) {
					for (local67 = 0; local67 < this.billboardCount; local67++) {
						local967 = this.billboardFaces[local67];
						local972 = this.billboardAttributes[local67];
						local972.diffuseColor = local972.diffuseColor & 0xFF000000 | PathingEntity.anIntArray423[Class56.method1470(this.faceColour[local967.anInt3925] & 0xFFFF) & 0xFFFF] & 0xFFFFFF;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "()[Lclient!bu;")
	@Override
	public EmissiveTriangle[] getEmissiveTriangles() {
		return this.emitters;
	}

	@OriginalMember(owner = "client!sk", name = "Z", descriptor = "(I)V")
	@Override
	public void Z(@OriginalArg(0) int arg0) {
		if ((this.functionMask & 0x6) != 6) {
			throw new IllegalStateException();
		}
		@Pc(13) int local13 = Class50_Sub1.anIntArray225[arg0];
		@Pc(17) int local17 = Class50_Sub1.anIntArray224[arg0];
		for (@Pc(19) int local19 = 0; local19 < this.vertexCount; local19++) {
			@Pc(36) int local36 = this.vertexY[local19] * local17 - this.vertexZ[local19] * local13 >> 15;
			this.vertexZ[local19] = this.vertexY[local19] * local13 + this.vertexZ[local19] * local17 >> 15;
			this.vertexY[local19] = local36;
		}
		this.invalidateNormals();
	}

	@OriginalMember(owner = "client!sk", name = "h", descriptor = "(IILclient!ta;Lclient!ta;III)V")
	@Override
	public void h(@OriginalArg(0) int contourType, @OriginalArg(1) int contourModifier, @OriginalArg(2) Class65 arg2, @OriginalArg(3) Class65 arg3, @OriginalArg(4) int x, @OriginalArg(5) int y, @OriginalArg(6) int z) {
		if (contourType == 3) {
			if ((this.functionMask & 0x7) != 7) {
				throw new IllegalStateException();
			}
		} else if ((this.functionMask & 0x2) != 2) {
			throw new IllegalStateException();
		}
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		@Pc(32) int local32 = x + this.minX;
		@Pc(37) int local37 = x + this.maxX;
		@Pc(42) int local42 = z + this.minZ;
		@Pc(47) int local47 = z + this.maxZ;
		if (contourType != 4 && (local32 < 0 || local37 + arg2.anInt5261 >> arg2.anInt5256 >= arg2.anInt5262 || local42 < 0 || local47 + arg2.anInt5261 >> arg2.anInt5256 >= arg2.anInt5258)) {
			return;
		}
		@Pc(79) int[][] local79 = ((JavaGround) arg2).anIntArrayArray22;
		@Pc(81) int[][] local81 = null;
		if (arg3 != null) {
			local81 = ((JavaGround) arg3).anIntArrayArray22;
		}
		if (contourType == 4 || contourType == 5) {
			if (arg3 == null) {
				return;
			}
			if (local32 < 0 || local37 + arg3.anInt5261 >> arg3.anInt5256 >= arg3.anInt5262 || local42 < 0 || local47 + arg3.anInt5261 >> arg3.anInt5256 >= arg3.anInt5258) {
				return;
			}
		} else {
			local32 >>= arg2.anInt5256;
			local37 = local37 + arg2.anInt5261 - 1 >> arg2.anInt5256;
			local42 >>= arg2.anInt5256;
			local47 = local47 + arg2.anInt5261 - 1 >> arg2.anInt5256;
			if (local79[local32][local42] == y && local79[local37][local42] == y && local79[local32][local47] == y && local79[local37][local47] == y) {
				return;
			}
		}
		@Pc(188) int local188;
		@Pc(190) int local190;
		@Pc(198) int local198;
		@Pc(205) int local205;
		@Pc(209) int local209;
		@Pc(213) int local213;
		@Pc(218) int local218;
		@Pc(223) int local223;
		@Pc(247) int local247;
		@Pc(275) int local275;
		@Pc(289) int local289;
		@Pc(427) int local427;
		if (contourType == 1) {
			local188 = arg2.anInt5261 - 1;
			for (local190 = 0; local190 < this.maxVertex; local190++) {
				local198 = this.vertexX[local190] + x;
				local205 = this.vertexZ[local190] + z;
				local209 = local198 & local188;
				local213 = local205 & local188;
				local218 = local198 >> arg2.anInt5256;
				local223 = local205 >> arg2.anInt5256;
				local247 = local79[local218][local223] * (arg2.anInt5261 - local209) + local79[local218 + 1][local223] * local209 >> arg2.anInt5256;
				local275 = local79[local218][local223 + 1] * (arg2.anInt5261 - local209) + local79[local218 + 1][local223 + 1] * local209 >> arg2.anInt5256;
				local289 = local247 * (arg2.anInt5261 - local213) + local275 * local213 >> arg2.anInt5256;
				this.vertexY[local190] = this.vertexY[local190] + local289 - y;
			}
			for (local198 = this.maxVertex; local198 < this.vertexCount; local198++) {
				local205 = this.vertexX[local198] + x;
				local209 = this.vertexZ[local198] + z;
				local213 = local205 & local188;
				local218 = local209 & local188;
				local223 = local205 >> arg2.anInt5256;
				local247 = local209 >> arg2.anInt5256;
				if (local223 >= 0 && local223 < local79.length - 1 && local247 >= 0 && local247 < local79[0].length - 1) {
					local275 = local79[local223][local247] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247] * local213 >> arg2.anInt5256;
					local289 = local79[local223][local247 + 1] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247 + 1] * local213 >> arg2.anInt5256;
					local427 = local275 * (arg2.anInt5261 - local218) + local289 * local218 >> arg2.anInt5256;
					this.vertexY[local198] = this.vertexY[local198] + local427 - y;
				}
			}
		} else {
			@Pc(731) int local731;
			if (contourType == 2) {
				local188 = arg2.anInt5261 - 1;
				for (local190 = 0; local190 < this.maxVertex; local190++) {
					local198 = (this.vertexY[local190] << 16) / this.minY;
					if (local198 < contourModifier) {
						local205 = this.vertexX[local190] + x;
						local209 = this.vertexZ[local190] + z;
						local213 = local205 & local188;
						local218 = local209 & local188;
						local223 = local205 >> arg2.anInt5256;
						local247 = local209 >> arg2.anInt5256;
						local275 = local79[local223][local247] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247] * local213 >> arg2.anInt5256;
						local289 = local79[local223][local247 + 1] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247 + 1] * local213 >> arg2.anInt5256;
						local427 = local275 * (arg2.anInt5261 - local218) + local289 * local218 >> arg2.anInt5256;
						this.vertexY[local190] += (local427 - y) * (contourModifier - local198) / contourModifier;
					} else {
						this.vertexY[local190] = this.vertexY[local190];
					}
				}
				for (local198 = this.maxVertex; local198 < this.vertexCount; local198++) {
					local205 = (this.vertexY[local198] << 16) / this.minY;
					if (local205 < contourModifier) {
						local209 = this.vertexX[local198] + x;
						local213 = this.vertexZ[local198] + z;
						local218 = local209 & local188;
						local223 = local213 & local188;
						local247 = local209 >> arg2.anInt5256;
						local275 = local213 >> arg2.anInt5256;
						if (local247 >= 0 && local247 < arg2.anInt5262 - 1 && local275 >= 0 && local275 < arg2.anInt5258 - 1) {
							local289 = local79[local247][local275] * (arg2.anInt5261 - local218) + local79[local247 + 1][local275] * local218 >> arg2.anInt5256;
							local427 = local79[local247][local275 + 1] * (arg2.anInt5261 - local218) + local79[local247 + 1][local275 + 1] * local218 >> arg2.anInt5256;
							local731 = local289 * (arg2.anInt5261 - local223) + local427 * local223 >> arg2.anInt5256;
							this.vertexY[local198] += (local731 - y) * (contourModifier - local205) / contourModifier;
						}
					} else {
						this.vertexY[local198] = this.vertexY[local198];
					}
				}
			} else if (contourType == 3) {
				local188 = (contourModifier & 0xFF) * 4;
				local190 = (contourModifier >> 8 & 0xFF) * 4;
				local198 = (contourModifier >> 16 & 0xFF) << 6;
				local205 = (contourModifier >> 24 & 0xFF) << 6;
				if (x - (local188 >> 1) < 0 || x + (local188 >> 1) + arg2.anInt5261 >= arg2.anInt5262 << arg2.anInt5256 || z - (local190 >> 1) < 0 || z + (local190 >> 1) + arg2.anInt5261 >= arg2.anInt5258 << arg2.anInt5256) {
					return;
				}
				this.method4884(x, z, local190, local198, arg2, local188, local205, y);
			} else if (contourType == 4) {
				local188 = arg3.anInt5261 - 1;
				local190 = this.maxY - this.minY;
				for (local198 = 0; local198 < this.maxVertex; local198++) {
					local205 = this.vertexX[local198] + x;
					local209 = this.vertexZ[local198] + z;
					local213 = local205 & local188;
					local218 = local209 & local188;
					local223 = local205 >> arg3.anInt5256;
					local247 = local209 >> arg3.anInt5256;
					local275 = local81[local223][local247] * (arg3.anInt5261 - local213) + local81[local223 + 1][local247] * local213 >> arg3.anInt5256;
					local289 = local81[local223][local247 + 1] * (arg3.anInt5261 - local213) + local81[local223 + 1][local247 + 1] * local213 >> arg3.anInt5256;
					local427 = local275 * (arg3.anInt5261 - local218) + local289 * local218 >> arg3.anInt5256;
					this.vertexY[local198] = this.vertexY[local198] + local427 + local190 - y;
				}
				for (local205 = this.maxVertex; local205 < this.vertexCount; local205++) {
					local209 = this.vertexX[local205] + x;
					local213 = this.vertexZ[local205] + z;
					local218 = local209 & local188;
					local223 = local213 & local188;
					local247 = local209 >> arg3.anInt5256;
					local275 = local213 >> arg3.anInt5256;
					if (local247 >= 0 && local247 < arg3.anInt5262 - 1 && local275 >= 0 && local275 < arg3.anInt5258 - 1) {
						local289 = local81[local247][local275] * (arg3.anInt5261 - local218) + local81[local247 + 1][local275] * local218 >> arg3.anInt5256;
						local427 = local81[local247][local275 + 1] * (arg3.anInt5261 - local218) + local81[local247 + 1][local275 + 1] * local218 >> arg3.anInt5256;
						local731 = local289 * (arg3.anInt5261 - local223) + local427 * local223 >> arg3.anInt5256;
						this.vertexY[local205] = this.vertexY[local205] + local731 + local190 - y;
					}
				}
			} else if (contourType == 5) {
				local188 = arg3.anInt5261 - 1;
				local190 = this.maxY - this.minY;
				@Pc(1308) int local1308;
				for (local198 = 0; local198 < this.maxVertex; local198++) {
					local205 = this.vertexX[local198] + x;
					local209 = this.vertexZ[local198] + z;
					local213 = local205 & local188;
					local218 = local209 & local188;
					local223 = local205 >> arg2.anInt5256;
					local247 = local209 >> arg2.anInt5256;
					local275 = local79[local223][local247] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247] * local213 >> arg2.anInt5256;
					local289 = local79[local223][local247 + 1] * (arg2.anInt5261 - local213) + local79[local223 + 1][local247 + 1] * local213 >> arg2.anInt5256;
					local427 = local275 * (arg2.anInt5261 - local218) + local289 * local218 >> arg2.anInt5256;
					local275 = local81[local223][local247] * (arg3.anInt5261 - local213) + local81[local223 + 1][local247] * local213 >> arg3.anInt5256;
					local289 = local81[local223][local247 + 1] * (arg3.anInt5261 - local213) + local81[local223 + 1][local247 + 1] * local213 >> arg3.anInt5256;
					local731 = local275 * (arg3.anInt5261 - local218) + local289 * local218 >> arg3.anInt5256;
					local1308 = local427 - local731;
					this.vertexY[local198] = ((this.vertexY[local198] << 8) / local190 * local1308 >> 8) - (y - local427);
				}
				for (local205 = this.maxVertex; local205 < this.vertexCount; local205++) {
					local209 = this.vertexX[local205] + x;
					local213 = this.vertexZ[local205] + z;
					local218 = local209 & local188;
					local223 = local213 & local188;
					local247 = local209 >> arg2.anInt5256;
					local275 = local213 >> arg2.anInt5256;
					if (local247 >= 0 && local247 < arg2.anInt5262 - 1 && local247 < arg3.anInt5262 - 1 && local275 >= 0 && local275 < arg2.anInt5258 - 1 && local275 < arg3.anInt5258 - 1) {
						local289 = local79[local247][local275] * (arg2.anInt5261 - local218) + local79[local247 + 1][local275] * local218 >> arg2.anInt5256;
						local427 = local79[local247][local275 + 1] * (arg2.anInt5261 - local218) + local79[local247 + 1][local275 + 1] * local218 >> arg2.anInt5256;
						local731 = local289 * (arg2.anInt5261 - local223) + local427 * local223 >> arg2.anInt5256;
						local289 = local81[local247][local275] * (arg3.anInt5261 - local218) + local81[local247 + 1][local275] * local218 >> arg3.anInt5256;
						local427 = local81[local247][local275 + 1] * (arg3.anInt5261 - local218) + local81[local247 + 1][local275 + 1] * local218 >> arg3.anInt5256;
						local1308 = local289 * (arg3.anInt5261 - local223) + local427 * local223 >> arg3.anInt5256;
						@Pc(1534) int local1534 = local731 - local1308;
						this.vertexY[local205] = ((this.vertexY[local205] << 8) / local190 * local1534 >> 8) - (y - local731);
					}
				}
			}
		}
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(ZZIZI)V")
	private void drawFacesRgb(@OriginalArg(0) boolean arg0, @OriginalArg(1) boolean arg1, @OriginalArg(2) int minDepth, @OriginalArg(3) boolean arg3, @OriginalArg(4) int arg4) {
		@Pc(12) int local12;
		if (minDepth + 2 > 2200) {
			local12 = Class196.method4426(minDepth) + 1 - JavaModel.anInt6298;
			minDepth = (minDepth >> local12) + 2;
		} else {
			local12 = 0;
			minDepth += 2;
		}
		for (@Pc(24) int local24 = 0; local24 < 2200; local24++) {
			this.anIntArray479[local24] = 0;
		}
		@Pc(36) int local36 = 0;
		@Pc(41) int local41;
		if (this.billboardFaces != null) {
			local41 = 0;
			while (local41 < this.billboardCount) {
				@Pc(47) JavaBillboardFace local47 = this.billboardFaces[local41];
				this.faceBillboard[local47.anInt3925] = local41++;
			}
		}
		@Pc(87) int local87;
		@Pc(92) int local92;
		@Pc(97) int local97;
		@Pc(113) int local113;
		@Pc(123) int local123;
		@Pc(128) int local128;
		@Pc(133) int local133;
		@Pc(138) int local138;
		@Pc(143) int local143;
		@Pc(148) int local148;
		@Pc(153) int local153;
		@Pc(193) int local193;
		for (local41 = 0; local41 < this.faceCount; local41++) {
			if (this.faceColourC[local41] != -2) {
				@Pc(72) short local72 = this.faceA[local41];
				@Pc(77) short local77 = this.faceB[local41];
				@Pc(82) short local82 = this.faceC[local41];
				local87 = this.screenX[local72];
				local92 = this.screenX[local77];
				local97 = this.screenX[local82];
				@Pc(118) int local118;
				if (arg0 && (local87 == -5000 || local92 == -5000 || local97 == -5000)) {
					local113 = this.anIntArray461[local72];
					local118 = this.anIntArray461[local77];
					local123 = this.anIntArray461[local82];
					local128 = this.anIntArray470[local72];
					local133 = this.anIntArray470[local77];
					local138 = this.anIntArray470[local82];
					local143 = this.cameraZ[local72];
					local148 = this.cameraZ[local77];
					local153 = this.cameraZ[local82];
					@Pc(157) int local157 = local113 - local118;
					@Pc(161) int local161 = local123 - local118;
					@Pc(165) int local165 = local128 - local133;
					@Pc(169) int local169 = local138 - local133;
					@Pc(173) int local173 = local143 - local148;
					@Pc(177) int local177 = local153 - local148;
					@Pc(185) int local185 = local165 * local177 - local173 * local169;
					local193 = local173 * local161 - local157 * local177;
					@Pc(201) int local201 = local157 * local169 - local165 * local161;
					if (local118 * local185 + local133 * local193 + local148 * local201 > 0) {
						this.aBooleanArray19[local41] = true;
						@Pc(237) int local237 = (this.anIntArray459[local72] + this.anIntArray459[local77] + this.anIntArray459[local82]) / 3 >> local12;
						if (this.anIntArray479[local237] < 64) {
							this.anIntArrayArray56[local237][this.anIntArray479[local237]++] = local41;
						} else {
							@Pc(264) int local264 = this.anIntArray479[local237];
							if (local264 == 64) {
								if (local36 == 512) {
									continue;
								}
								this.anIntArray472[local36] = 0;
								this.anIntArray479[local237] = local264 = local36++ + 65;
							}
							local264 -= 65;
							this.anIntArrayArray53[local264][this.anIntArray472[local264]++] = local41;
						}
					}
				} else if (this.faceBillboard[local41] != -1 || (local87 - local92) * (this.screenY[local82] - this.screenY[local77]) - (this.screenY[local72] - this.screenY[local77]) * (local97 - local92) > 0) {
					this.aBooleanArray19[local41] = false;
					if (local87 >= 0 && local92 >= 0 && local97 >= 0 && local87 <= this.threadResource.anInt1120 && local92 <= this.threadResource.anInt1120 && local97 <= this.threadResource.anInt1120) {
						this.aBooleanArray18[local41] = false;
					} else {
						this.aBooleanArray18[local41] = true;
					}
					local113 = (this.anIntArray459[local72] + this.anIntArray459[local77] + this.anIntArray459[local82]) / 3 >> local12;
					if (this.anIntArray479[local113] < 64) {
						this.anIntArrayArray56[local113][this.anIntArray479[local113]++] = local41;
					} else {
						local118 = this.anIntArray479[local113];
						if (local118 == 64) {
							if (local36 == 512) {
								continue;
							}
							this.anIntArray472[local36] = 0;
							this.anIntArray479[local113] = local118 = local36++ + 65;
						}
						local118 -= 65;
						this.anIntArrayArray53[local118][this.anIntArray472[local118]++] = local41;
					}
				}
			}
		}
		@Pc(467) int local467;
		@Pc(475) int local475;
		@Pc(511) int local511;
		if (arg3) {
			for (local467 = 0; local467 < arg4; local467++) {
				local475 = this.anIntArray448[local467] >> local12;
				if (local475 >= 0 && local475 < 2200) {
					if (this.anIntArray479[local475] < 64) {
						this.anIntArrayArray56[local475][this.anIntArray479[local475]++] = local467 + 1 << 16;
					} else {
						local511 = this.anIntArray479[local475];
						if (local511 == 64) {
							if (local36 == 512) {
								continue;
							}
							this.anIntArray472[local36] = 0;
							this.anIntArray479[local475] = local511 = local36++ + 65;
						}
						local511 -= 65;
						this.anIntArrayArray53[local511][this.anIntArray472[local511]++] = local467 + 1 << 16;
					}
				}
			}
		}
		if (this.facePriority == null) {
			@Pc(591) int[] local591;
			if (!arg3 && this.billboardFaces == null) {
				if (arg1) {
					for (local467 = minDepth - 1; local467 >= 0; local467--) {
						local475 = this.anIntArray479[local467];
						if (local475 > 0) {
							local511 = local475 > 64 ? 64 : local475;
							local591 = this.anIntArrayArray56[local467];
							for (local92 = 0; local92 < local511; local92++) {
								this.method4916(local591[local92]);
							}
						}
						if (local475 > 64) {
							local511 = this.anIntArray479[local467] - 64 - 1;
							local591 = this.anIntArrayArray53[local511];
							for (local92 = 0; local92 < this.anIntArray472[local511]; local92++) {
								this.method4916(local591[local92]);
							}
						}
					}
				} else {
					for (local467 = minDepth - 1; local467 >= 0; local467--) {
						local475 = this.anIntArray479[local467];
						if (local475 > 0) {
							local511 = local475 > 64 ? 64 : local475;
							local591 = this.anIntArrayArray56[local467];
							for (local92 = 0; local92 < local511; local92++) {
								this.drawTriangleRgb(local591[local92]);
							}
						}
						if (local475 > 64) {
							local511 = this.anIntArray479[local467] - 64 - 1;
							local591 = this.anIntArrayArray53[local511];
							for (local92 = 0; local92 < this.anIntArray472[local511]; local92++) {
								this.drawTriangleRgb(local591[local92]);
							}
						}
					}
				}
			} else if (arg1) {
				for (local467 = minDepth - 1; local467 >= 0; local467--) {
					local475 = this.anIntArray479[local467];
					if (local475 > 0) {
						local511 = local475 > 64 ? 64 : local475;
						local591 = this.anIntArrayArray56[local467];
						for (local92 = 0; local92 < local511; local92++) {
							local97 = local591[local92];
							if (local97 < 65536) {
								local113 = this.faceBillboard[local97];
								if (local113 == -1 || !this.billboardFaces[local113].aBoolean365) {
									this.method4916(local97);
								}
							}
						}
					}
					if (local475 > 64) {
						local511 = this.anIntArray479[local467] - 64 - 1;
						local591 = this.anIntArrayArray53[local511];
						for (local92 = 0; local92 < this.anIntArray472[local511]; local92++) {
							local97 = local591[local92];
							if (local97 < 65536) {
								local113 = this.faceBillboard[local97];
								if (local113 == -1 || !this.billboardFaces[local113].aBoolean365) {
									this.method4916(local97);
								}
							}
						}
					}
				}
			} else {
				for (local467 = minDepth - 1; local467 >= 0; local467--) {
					local475 = this.anIntArray479[local467];
					@Pc(724) JavaBillboardFace local724;
					@Pc(776) PointEntity local776;
					@Pc(729) JavaBillboardAttributes local729;
					if (local475 > 0) {
						local511 = local475 > 64 ? 64 : local475;
						local591 = this.anIntArrayArray56[local467];
						for (local92 = 0; local92 < local511; local92++) {
							local97 = local591[local92];
							if (local97 < 65536) {
								local113 = this.faceBillboard[local97];
								if (local113 == -1) {
									this.drawTriangleRgb(local591[local92]);
								} else {
									local724 = this.billboardFaces[local113];
									local729 = this.billboardAttributes[local113];
									if (!local724.aBoolean365) {
										this.drawTriangleRgb(local97);
									}
									this.toolkit.method494(local729.anInt5041, local729.anInt5052, local729.anInt5044, local729.anInt5046, local729.rotation, local724.aShort47 & 0xFFFF, local729.diffuseColor, local724.aByte29, local724.aByte28);
								}
							} else {
								local113 = (local97 >> 16) - 1;
								local776 = this.aClass3_Sub1_Sub1Array2[local113];
								local123 = this.anIntArray465[local113];
								if (local123 == 0) {
									local123 = 1;
								}
								this.toolkit.method491(local776, this.anIntArray481[local113], this.anIntArray482[local113], (local776.size >> 2) / local123);
							}
						}
					}
					if (local475 > 64) {
						local511 = this.anIntArray479[local467] - 64 - 1;
						local591 = this.anIntArrayArray53[local511];
						for (local92 = 0; local92 < this.anIntArray472[local511]; local92++) {
							local97 = local591[local92];
							if (local97 < 65536) {
								local113 = this.faceBillboard[local97];
								if (local113 == -1) {
									this.drawTriangleRgb(local591[local92]);
								} else {
									local724 = this.billboardFaces[local113];
									local729 = this.billboardAttributes[local113];
									if (!local724.aBoolean365) {
										this.drawTriangleRgb(local97);
									}
									this.toolkit.method494(local729.anInt5041, local729.anInt5052, local729.anInt5044, local729.anInt5046, local729.rotation, local724.aShort47 & 0xFFFF, local729.diffuseColor, local724.aByte29, local724.aByte28);
								}
							} else {
								local113 = (local97 >> 16) - 1;
								local776 = this.aClass3_Sub1_Sub1Array2[local113];
								local123 = this.anIntArray465[local113];
								if (local123 == 0) {
									local123 = 1;
								}
								this.toolkit.method491(local776, this.anIntArray481[local113], this.anIntArray482[local113], (local776.size >> 2) / local123);
							}
						}
					}
				}
			}
			return;
		}
		for (local467 = 0; local467 < 12; local467++) {
			this.anIntArray451[local467] = 0;
			this.anIntArray471[local467] = 0;
		}
		@Pc(1127) int[] local1127;
		@Pc(1142) byte local1142;
		if (arg3) {
			for (local475 = minDepth - 1; local475 >= 0; local475--) {
				local511 = this.anIntArray479[local475];
				if (local511 > 0) {
					if (local511 > 64) {
						local87 = 64;
					} else {
						local87 = local511;
					}
					local1127 = this.anIntArrayArray56[local475];
					for (local97 = 0; local97 < local87; local97++) {
						local113 = local1127[local97];
						if (local113 < 65536) {
							local1142 = this.facePriority[local113];
							local123 = this.anIntArray451[local1142]++;
							this.anIntArrayArray51[local1142][local123] = local113;
							if (local1142 < 10) {
								this.anIntArray471[local1142] += local475;
							} else if (local1142 == 10) {
								this.anIntArray464[local123] = local475;
							} else {
								this.anIntArray473[local123] = local475;
							}
						} else {
							local1142 = this.aClass3_Sub1_Sub1Array2[(local113 >> 16) - 1].priority;
							local123 = this.anIntArray451[local1142]++;
							this.anIntArrayArray51[local1142][local123] = local113;
							if (local1142 < 10) {
								this.anIntArray471[local1142] += local475;
							} else if (local1142 == 10) {
								this.anIntArray464[local123] = local475;
							} else {
								this.anIntArray473[local123] = local475;
							}
						}
					}
				}
				if (local511 > 64) {
					local87 = this.anIntArray479[local475] - 64 - 1;
					local1127 = this.anIntArrayArray53[local87];
					for (local97 = 0; local97 < this.anIntArray472[local87]; local97++) {
						local113 = local1127[local97];
						if (local113 < 65536) {
							local1142 = this.facePriority[local113];
							local123 = this.anIntArray451[local1142]++;
							this.anIntArrayArray51[local1142][local123] = local113;
							if (local1142 < 10) {
								this.anIntArray471[local1142] += local475;
							} else if (local1142 == 10) {
								this.anIntArray464[local123] = local475;
							} else {
								this.anIntArray473[local123] = local475;
							}
						} else {
							local1142 = this.aClass3_Sub1_Sub1Array2[(local113 >> 16) - 1].priority;
							local123 = this.anIntArray451[local1142]++;
							this.anIntArrayArray51[local1142][local123] = local113;
							if (local1142 < 10) {
								this.anIntArray471[local1142] += local475;
							} else if (local1142 == 10) {
								this.anIntArray464[local123] = local475;
							} else {
								this.anIntArray473[local123] = local475;
							}
						}
					}
				}
			}
		} else {
			for (local475 = minDepth - 1; local475 >= 0; local475--) {
				local511 = this.anIntArray479[local475];
				if (local511 > 0) {
					if (local511 > 64) {
						local87 = 64;
					} else {
						local87 = local511;
					}
					local1127 = this.anIntArrayArray56[local475];
					for (local97 = 0; local97 < local87; local97++) {
						local113 = local1127[local97];
						local1142 = this.facePriority[local113];
						local123 = this.anIntArray451[local1142]++;
						this.anIntArrayArray51[local1142][local123] = local113;
						if (local1142 < 10) {
							this.anIntArray471[local1142] += local475;
						} else if (local1142 == 10) {
							this.anIntArray464[local123] = local475;
						} else {
							this.anIntArray473[local123] = local475;
						}
					}
				}
				if (local511 > 64) {
					local87 = this.anIntArray479[local475] - 64 - 1;
					local1127 = this.anIntArrayArray53[local87];
					for (local97 = 0; local97 < this.anIntArray472[local87]; local97++) {
						local113 = local1127[local97];
						local1142 = this.facePriority[local113];
						local123 = this.anIntArray451[local1142]++;
						this.anIntArrayArray51[local1142][local123] = local113;
						if (local1142 < 10) {
							this.anIntArray471[local1142] += local475;
						} else if (local1142 == 10) {
							this.anIntArray464[local123] = local475;
						} else {
							this.anIntArray473[local123] = local475;
						}
					}
				}
			}
		}
		local475 = 0;
		if (this.anIntArray451[1] > 0 || this.anIntArray451[2] > 0) {
			local475 = (this.anIntArray471[1] + this.anIntArray471[2]) / (this.anIntArray451[1] + this.anIntArray451[2]);
		}
		local511 = 0;
		if (this.anIntArray451[3] > 0 || this.anIntArray451[4] > 0) {
			local511 = (this.anIntArray471[3] + this.anIntArray471[4]) / (this.anIntArray451[3] + this.anIntArray451[4]);
		}
		local87 = 0;
		if (this.anIntArray451[6] > 0 || this.anIntArray451[8] > 0) {
			local87 = (this.anIntArray471[6] + this.anIntArray471[8]) / (this.anIntArray451[6] + this.anIntArray451[8]);
		}
		local97 = 0;
		local113 = this.anIntArray451[10];
		@Pc(1657) int[] local1657 = this.anIntArrayArray51[10];
		@Pc(1660) int[] local1660 = this.anIntArray464;
		if (local113 == 0) {
			local97 = 0;
			local113 = this.anIntArray451[11];
			local1657 = this.anIntArrayArray51[11];
			local1660 = this.anIntArray473;
		}
		if (local113 > 0) {
			local92 = local1660[0];
		} else {
			local92 = -1000;
		}
		@Pc(1912) JavaBillboardFace local1912;
		@Pc(1962) PointEntity local1962;
		@Pc(1917) JavaBillboardAttributes local1917;
		for (local128 = 0; local128 < 10; local128++) {
			if (arg1) {
				while (local128 == 0 && local92 > local475) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1 || !this.billboardFaces[local138].aBoolean365) {
							this.method4916(local133);
						}
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
				while (local128 == 3 && local92 > local511) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1 || !this.billboardFaces[local138].aBoolean365) {
							this.method4916(local133);
						}
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
				while (local128 == 5 && local92 > local87) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1 || !this.billboardFaces[local138].aBoolean365) {
							this.method4916(local133);
						}
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
			} else {
				while (local128 == 0 && local92 > local475) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1) {
							this.drawTriangleRgb(local133);
						} else {
							local1912 = this.billboardFaces[local138];
							local1917 = this.billboardAttributes[local138];
							if (!local1912.aBoolean365) {
								this.drawTriangleRgb(local133);
							}
							this.toolkit.method494(local1917.anInt5041, local1917.anInt5052, local1917.anInt5044, local1917.anInt5046, local1917.rotation, local1912.aShort47 & 0xFFFF, local1917.diffuseColor, local1912.aByte29, local1912.aByte28);
						}
					} else {
						local138 = (local133 >> 16) - 1;
						local1962 = this.aClass3_Sub1_Sub1Array2[local138];
						local148 = this.anIntArray465[local138];
						if (local148 == 0) {
							local148 = 1;
						}
						this.toolkit.method491(local1962, this.anIntArray481[local138], this.anIntArray482[local138], (local1962.size >> 2) / local148);
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
				while (local128 == 3 && local92 > local511) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1) {
							this.drawTriangleRgb(local133);
						} else {
							local1912 = this.billboardFaces[local138];
							local1917 = this.billboardAttributes[local138];
							if (!local1912.aBoolean365) {
								this.drawTriangleRgb(local133);
							}
							this.toolkit.method494(local1917.anInt5041, local1917.anInt5052, local1917.anInt5044, local1917.anInt5046, local1917.rotation, local1912.aShort47 & 0xFFFF, local1917.diffuseColor, local1912.aByte29, local1912.aByte28);
						}
					} else {
						local138 = (local133 >> 16) - 1;
						local1962 = this.aClass3_Sub1_Sub1Array2[local138];
						local148 = this.anIntArray465[local138];
						if (local148 == 0) {
							local148 = 1;
						}
						this.toolkit.method491(local1962, this.anIntArray481[local138], this.anIntArray482[local138], (local1962.size >> 2) / local148);
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
				while (local128 == 5 && local92 > local87) {
					local133 = local1657[local97++];
					if (local133 < 65536) {
						local138 = this.faceBillboard[local133];
						if (local138 == -1) {
							this.drawTriangleRgb(local133);
						} else {
							local1912 = this.billboardFaces[local138];
							local1917 = this.billboardAttributes[local138];
							if (!local1912.aBoolean365) {
								this.drawTriangleRgb(local133);
							}
							this.toolkit.method494(local1917.anInt5041, local1917.anInt5052, local1917.anInt5044, local1917.anInt5046, local1917.rotation, local1912.aShort47 & 0xFFFF, local1917.diffuseColor, local1912.aByte29, local1912.aByte28);
						}
					} else {
						local138 = (local133 >> 16) - 1;
						local1962 = this.aClass3_Sub1_Sub1Array2[local138];
						local148 = this.anIntArray465[local138];
						if (local148 == 0) {
							local148 = 1;
						}
						this.toolkit.method491(local1962, this.anIntArray481[local138], this.anIntArray482[local138], (local1962.size >> 2) / local148);
					}
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local113 = this.anIntArray451[11];
						local1657 = this.anIntArrayArray51[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
			}
			local133 = this.anIntArray451[local128];
			@Pc(2316) int[] local2316 = this.anIntArrayArray51[local128];
			if (!arg3 && this.billboardFaces == null) {
				if (arg1) {
					for (local143 = 0; local143 < local133; local143++) {
						this.method4916(local2316[local143]);
					}
				} else {
					for (local143 = 0; local143 < local133; local143++) {
						this.drawTriangleRgb(local2316[local143]);
					}
				}
			} else if (arg1) {
				for (local143 = 0; local143 < local133; local143++) {
					local148 = local2316[local143];
					if (local148 < 65536) {
						local153 = this.faceBillboard[local148];
						if (local153 == -1 || !this.billboardFaces[local153].aBoolean365) {
							this.method4916(local148);
						}
					}
				}
			} else {
				for (local143 = 0; local143 < local133; local143++) {
					local148 = local2316[local143];
					if (local148 < 65536) {
						local153 = this.faceBillboard[local148];
						if (local153 == -1) {
							this.drawTriangleRgb(local148);
						} else {
							@Pc(2378) JavaBillboardFace local2378 = this.billboardFaces[local153];
							@Pc(2383) JavaBillboardAttributes local2383 = this.billboardAttributes[local153];
							if (!local2378.aBoolean365) {
								this.drawTriangleRgb(local148);
							}
							this.toolkit.method494(local2383.anInt5041, local2383.anInt5052, local2383.anInt5044, local2383.anInt5046, local2383.rotation, local2378.aShort47 & 0xFFFF, local2383.diffuseColor, local2378.aByte29, local2378.aByte28);
						}
					} else {
						local153 = (local148 >> 16) - 1;
						@Pc(2428) PointEntity local2428 = this.aClass3_Sub1_Sub1Array2[local153];
						local193 = this.anIntArray465[local153];
						if (local193 == 0) {
							local193 = 1;
						}
						this.toolkit.method491(local2428, this.anIntArray481[local153], this.anIntArray482[local153], (local2428.size >> 2) / local193);
					}
				}
			}
		}
		if (!arg3 && this.billboardFaces == null) {
			if (arg1) {
				while (local92 != -1000) {
					this.method4916(local1657[local97++]);
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local1657 = this.anIntArrayArray51[11];
						local113 = this.anIntArray451[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
			} else {
				while (local92 != -1000) {
					this.drawTriangleRgb(local1657[local97++]);
					if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
						local97 = 0;
						local1657 = this.anIntArrayArray51[11];
						local113 = this.anIntArray451[11];
						local1660 = this.anIntArray473;
					}
					if (local97 < local113) {
						local92 = local1660[local97];
					} else {
						local92 = -1000;
					}
				}
			}
		} else if (arg1) {
			while (local92 != -1000) {
				local133 = local1657[local97++];
				if (local133 < 65536) {
					local138 = this.faceBillboard[local133];
					if (local138 == -1 || !this.billboardFaces[local138].aBoolean365) {
						this.method4916(local133);
					}
				}
				if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
					local97 = 0;
					local1657 = this.anIntArrayArray51[11];
					local113 = this.anIntArray451[11];
					local1660 = this.anIntArray473;
				}
				if (local97 < local113) {
					local92 = local1660[local97];
				} else {
					local92 = -1000;
				}
			}
		} else {
			while (local92 != -1000) {
				local133 = local1657[local97++];
				if (local133 < 65536) {
					local138 = this.faceBillboard[local133];
					if (local138 == -1) {
						this.drawTriangleRgb(local133);
					} else {
						local1912 = this.billboardFaces[local138];
						local1917 = this.billboardAttributes[local138];
						if (!local1912.aBoolean365) {
							this.drawTriangleRgb(local133);
						}
						this.toolkit.method494(local1917.anInt5041, local1917.anInt5052, local1917.anInt5044, local1917.anInt5046, local1917.rotation, local1912.aShort47 & 0xFFFF, local1917.diffuseColor, local1912.aByte29, local1912.aByte28);
					}
				} else {
					local138 = (local133 >> 16) - 1;
					local1962 = this.aClass3_Sub1_Sub1Array2[local138];
					local148 = this.anIntArray465[local138];
					if (local148 == 0) {
						local148 = 1;
					}
					this.toolkit.method491(local1962, this.anIntArray481[local138], this.anIntArray482[local138], (local1962.size >> 2) / local148);
				}
				if (local97 == local113 && local1657 != this.anIntArrayArray51[11]) {
					local97 = 0;
					local1657 = this.anIntArrayArray51[11];
					local113 = this.anIntArray451[11];
					local1660 = this.anIntArray473;
				}
				if (local97 < local113) {
					local92 = local1660[local97];
				} else {
					local92 = -1000;
				}
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "d", descriptor = "(I)I")
	private int clampLightness(@OriginalArg(0) int arg0) {
		if (arg0 < 2) {
			arg0 = 2;
		} else if (arg0 > 126) {
			arg0 = 126;
		}
		return arg0;
	}

	@OriginalMember(owner = "client!sk", name = "l", descriptor = "()I")
	@Override
	public int l() {
		return this.contrast;
	}

	@OriginalMember(owner = "client!sk", name = "b", descriptor = "(Ljava/lang/Thread;)V")
	private void useThreadBuffers(@OriginalArg(0) Thread thread) {
		@Pc(4) JavaThreadResource local4 = this.toolkit.threadResource(thread);
		this.rasterizer = local4.rasterizer;
		if (local4 == this.threadResource) {
			return;
		}
		this.threadResource = local4;
		this.aBooleanArray18 = this.threadResource.aBooleanArray7;
		this.aBooleanArray19 = this.threadResource.aBooleanArray8;
		this.anIntArray450 = this.threadResource.anIntArray91;
		this.anIntArray461 = this.threadResource.worldY;
		this.anIntArray470 = this.threadResource.cameraZ;
		this.cameraZ = this.threadResource.anIntArray97;
		this.screenX = this.threadResource.anIntArray76;
		this.screenY = this.threadResource.cameraX;
		this.anIntArray467 = this.threadResource.anIntArray73;
		this.anIntArray476 = this.threadResource.anIntArray95;
		this.anIntArray463 = this.threadResource.anIntArray89;
		this.anIntArray466 = this.threadResource.anIntArray88;
		this.anIntArray481 = this.threadResource.anIntArray68;
		this.anIntArray482 = this.threadResource.anIntArray77;
		this.anIntArray465 = this.threadResource.anIntArray72;
		this.aClass3_Sub1_Sub1Array2 = this.threadResource.aClass3_Sub1_Sub1Array1;
		this.anIntArray448 = this.threadResource.anIntArray69;
		this.anIntArray459 = this.threadResource.otherMergeStamps;
		this.anIntArray479 = this.threadResource.anIntArray70;
		this.anIntArrayArray56 = this.threadResource.anIntArrayArray13;
		this.anIntArray472 = this.threadResource.anIntArray78;
		this.anIntArrayArray53 = this.threadResource.anIntArrayArray14;
		this.anIntArray451 = this.threadResource.anIntArray90;
		this.anIntArrayArray51 = this.threadResource.anIntArrayArray15;
		this.anIntArray464 = this.threadResource.anIntArray94;
		this.anIntArray473 = this.threadResource.anIntArray84;
		this.anIntArray471 = this.threadResource.anIntArray74;
		this.anIntArray462 = this.threadResource.anIntArray96;
		this.anIntArray480 = this.threadResource.anIntArray79;
		this.anIntArray449 = this.threadResource.anIntArray87;
		this.anIntArray468 = this.threadResource.anIntArray86;
		this.anIntArray455 = this.threadResource.anIntArray80;
		this.faceBillboard = this.threadResource.faceBillboard;
	}

	@OriginalMember(owner = "client!sk", name = "k", descriptor = "()V")
	private void rotate180() {
		for (@Pc(1) int local1 = 0; local1 < this.vertexCount; local1++) {
			this.vertexX[local1] = -this.vertexX[local1];
			this.vertexZ[local1] = -this.vertexZ[local1];
		}
		this.invalidateNormals();
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!c;Lclient!mi;II)V")
	@Override
	public void renderOrtho(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon cylinder, @OriginalArg(2) int orthoDepth) {
		this.method4915(arg0, cylinder, null, orthoDepth, 1);
	}

	@OriginalMember(owner = "client!sk", name = "JA", descriptor = "(III)V")
	@Override
	public void drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		if (arg0 != 0 && (this.functionMask & 0x1) != 1) {
			throw new IllegalStateException();
		} else if (arg1 != 0 && (this.functionMask & 0x2) != 2) {
			throw new IllegalStateException();
		} else if (arg2 == 0 || (this.functionMask & 0x4) == 4) {
			for (@Pc(37) int local37 = 0; local37 < this.vertexCount; local37++) {
				this.vertexX[local37] += arg0;
				this.vertexY[local37] += arg1;
				this.vertexZ[local37] += arg2;
			}
		} else {
			throw new IllegalStateException();
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!c;Lclient!mi;Lclient!mc;II)V")
	private void method4915(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon arg1, @OriginalArg(2) ParticleList arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4) {
		if (this.maxVertex < 1) {
			return;
		}
		this.aClass31_Sub2_3 = (Class31_Sub2) arg0;
		@Pc(12) Class31_Sub2 local12 = this.toolkit.aClass31_Sub2_1;
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		this.method4907(this.aClass31_Sub2_3);
		@Pc(23) boolean local23 = false;
		if (this.aClass31_Sub2_3.anInt7334 == 32768 && this.aClass31_Sub2_3.anInt7328 == 0 && this.aClass31_Sub2_3.anInt7332 == 0 && this.aClass31_Sub2_3.anInt7331 == 0 && this.aClass31_Sub2_3.anInt7326 == 32768 && this.aClass31_Sub2_3.anInt7327 == 0 && this.aClass31_Sub2_3.anInt7323 == 0 && this.aClass31_Sub2_3.anInt7330 == 0 && this.aClass31_Sub2_3.anInt7325 == 32768) {
			local23 = true;
		}
		@Pc(90) int local90 = (local12.anInt7333 << 15) + local12.anInt7323 * this.aClass31_Sub2_3.anInt7329 + local12.anInt7330 * this.aClass31_Sub2_3.anInt7324 + local12.anInt7325 * this.aClass31_Sub2_3.anInt7333;
		@Pc(118) int local118 = local23 ? local12.anInt7330 : local12.anInt7323 * this.aClass31_Sub2_3.anInt7328 + local12.anInt7330 * this.aClass31_Sub2_3.anInt7326 + local12.anInt7325 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(127) int local127 = local90 + this.minY * local118 >> 15;
		@Pc(136) int local136 = local90 + this.maxY * local118 >> 15;
		@Pc(144) int local144;
		@Pc(149) int local149;
		if (local127 > local136) {
			local144 = local136 - this.radius;
			local149 = local127 + this.radius;
		} else {
			local144 = local127 - this.radius;
			local149 = local136 + this.radius;
		}
		if (local144 >= this.toolkit.anInt403 || local149 <= this.toolkit.anInt406) {
			return;
		}
		@Pc(198) int local198 = (local12.anInt7329 << 15) + local12.anInt7334 * this.aClass31_Sub2_3.anInt7329 + local12.anInt7328 * this.aClass31_Sub2_3.anInt7324 + local12.anInt7332 * this.aClass31_Sub2_3.anInt7333;
		@Pc(226) int local226 = local23 ? local12.anInt7328 : local12.anInt7334 * this.aClass31_Sub2_3.anInt7328 + local12.anInt7328 * this.aClass31_Sub2_3.anInt7326 + local12.anInt7332 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(235) int local235 = local198 + this.minY * local226 >> 15;
		@Pc(244) int local244 = local198 + this.maxY * local226 >> 15;
		@Pc(256) int local256;
		@Pc(265) int local265;
		if (local235 > local244) {
			local256 = (local244 - this.radius) * this.toolkit.anInt416;
			local265 = (local235 + this.radius) * this.toolkit.anInt416;
		} else {
			local256 = (local235 - this.radius) * this.toolkit.anInt416;
			local265 = (local244 + this.radius) * this.toolkit.anInt416;
		}
		if (arg3 == -1) {
			if (local256 / local149 >= this.toolkit.viewX2) {
				return;
			}
			if (local265 / local149 <= this.toolkit.viewX1) {
				return;
			}
		} else if (local256 / arg3 >= this.toolkit.viewX2) {
			return;
		} else if (local265 / arg3 <= this.toolkit.viewX1) {
			return;
		}
		@Pc(345) int local345 = (local12.anInt7324 << 15) + local12.anInt7331 * this.aClass31_Sub2_3.anInt7329 + local12.anInt7326 * this.aClass31_Sub2_3.anInt7324 + local12.anInt7327 * this.aClass31_Sub2_3.anInt7333;
		@Pc(373) int local373 = local23 ? local12.anInt7326 : local12.anInt7331 * this.aClass31_Sub2_3.anInt7328 + local12.anInt7326 * this.aClass31_Sub2_3.anInt7326 + local12.anInt7327 * this.aClass31_Sub2_3.anInt7330 >> 15;
		@Pc(382) int local382 = local345 + this.minY * local373 >> 15;
		@Pc(391) int local391 = local345 + this.maxY * local373 >> 15;
		@Pc(403) int local403;
		@Pc(412) int local412;
		if (local382 > local391) {
			local403 = (local391 - this.radius) * this.toolkit.anInt417;
			local412 = (local382 + this.radius) * this.toolkit.anInt417;
		} else {
			local403 = (local382 - this.radius) * this.toolkit.anInt417;
			local412 = (local391 + this.radius) * this.toolkit.anInt417;
		}
		if (arg3 == -1) {
			if (local403 / local149 >= this.toolkit.viewY2) {
				return;
			}
			if (local412 / local149 <= this.toolkit.viewY1) {
				return;
			}
		} else if (local403 / arg3 >= this.toolkit.viewY2) {
			return;
		} else if (local412 / arg3 <= this.toolkit.viewY1) {
			return;
		}
		@Pc(471) int local471;
		@Pc(474) int local474;
		@Pc(477) int local477;
		@Pc(480) int local480;
		@Pc(483) int local483;
		@Pc(486) int local486;
		if (local23) {
			local471 = local12.anInt7334;
			local474 = local12.anInt7331;
			local477 = local12.anInt7323;
			local480 = local12.anInt7332;
			local483 = local12.anInt7327;
			local486 = local12.anInt7325;
		} else {
			local471 = local12.anInt7334 * this.aClass31_Sub2_3.anInt7334 + local12.anInt7328 * this.aClass31_Sub2_3.anInt7331 + local12.anInt7332 * this.aClass31_Sub2_3.anInt7323 >> 15;
			local474 = local12.anInt7331 * this.aClass31_Sub2_3.anInt7334 + local12.anInt7326 * this.aClass31_Sub2_3.anInt7331 + local12.anInt7327 * this.aClass31_Sub2_3.anInt7323 >> 15;
			local477 = local12.anInt7323 * this.aClass31_Sub2_3.anInt7334 + local12.anInt7330 * this.aClass31_Sub2_3.anInt7331 + local12.anInt7325 * this.aClass31_Sub2_3.anInt7323 >> 15;
			local480 = local12.anInt7334 * this.aClass31_Sub2_3.anInt7332 + local12.anInt7328 * this.aClass31_Sub2_3.anInt7327 + local12.anInt7332 * this.aClass31_Sub2_3.anInt7325 >> 15;
			local483 = local12.anInt7331 * this.aClass31_Sub2_3.anInt7332 + local12.anInt7326 * this.aClass31_Sub2_3.anInt7327 + local12.anInt7327 * this.aClass31_Sub2_3.anInt7325 >> 15;
			local486 = local12.anInt7323 * this.aClass31_Sub2_3.anInt7332 + local12.anInt7330 * this.aClass31_Sub2_3.anInt7327 + local12.anInt7325 * this.aClass31_Sub2_3.anInt7325 >> 15;
		}
		if (this.toolkit.threadCount > 1) {
			synchronized (this) {
				while (this.rendering) {
					try {
						this.wait();
					} catch (@Pc(639) InterruptedException local639) {
					}
				}
				this.rendering = true;
			}
		}
		this.useThreadBuffers(Thread.currentThread());
		@Pc(657) boolean local657 = false;
		@Pc(666) boolean local666 = local144 <= this.toolkit.anInt406;
		@Pc(681) boolean local681 = local666 || this.anInt6297 > 0 || this.emitters != null || this.effectors != null;
		this.threadResource.anInt1120 = this.rasterizer.width;
		this.threadResource.anInt1118 = this.rasterizer.anInt5773;
		this.threadResource.anInt1119 = this.rasterizer.anInt5777;
		@Pc(703) int local703 = this.toolkit.anInt416;
		@Pc(707) int local707 = this.toolkit.anInt417;
		@Pc(711) int local711 = this.toolkit.anInt406;
		@Pc(748) int local748;
		@Pc(764) int local764;
		@Pc(780) int local780;
		@Pc(722) int local722;
		@Pc(727) int local727;
		@Pc(732) int local732;
		@Pc(716) int local716;
		@Pc(887) int local887;
		@Pc(893) JavaBillboardFace local893;
		@Pc(898) JavaBillboardAttributes local898;
		@Pc(904) short local904;
		@Pc(910) short local910;
		@Pc(916) short local916;
		if (arg3 == -1) {
			for (local716 = 0; local716 < this.vertexCount; local716++) {
				local722 = this.vertexX[local716];
				local727 = this.vertexY[local716];
				local732 = this.vertexZ[local716];
				local748 = local198 + local471 * local722 + local226 * local727 + local480 * local732 >> 15;
				local764 = local345 + local474 * local722 + local373 * local727 + local483 * local732 >> 15;
				local780 = local90 + local477 * local722 + local118 * local727 + local486 * local732 >> 15;
				this.anIntArray459[local716] = local780 - local144;
				this.anIntArray467[local716] = local780;
				if (local780 >= local711) {
					this.screenX[local716] = this.threadResource.anInt1118 + local748 * local703 / local780;
					this.screenY[local716] = this.threadResource.anInt1119 + local764 * local707 / local780;
				} else {
					this.screenX[local716] = -5000;
					local657 = true;
				}
				if (local681) {
					this.anIntArray461[local716] = local748;
					this.anIntArray470[local716] = local764;
					this.cameraZ[local716] = local780;
				}
				if (this.toolkit.aBoolean43) {
					this.anIntArray450[local716] = this.aClass31_Sub2_3.anInt7324 + (this.aClass31_Sub2_3.anInt7331 * local722 + this.aClass31_Sub2_3.anInt7326 * local727 + this.aClass31_Sub2_3.anInt7327 * local732 >> 15);
				}
			}
			if (this.billboardFaces != null) {
				for (local887 = 0; local887 < this.billboardCount; local887++) {
					local893 = this.billboardFaces[local887];
					local898 = this.billboardAttributes[local887];
					local904 = this.faceA[local893.anInt3925];
					local910 = this.faceB[local893.anInt3925];
					local916 = this.faceC[local893.anInt3925];
					local722 = (this.vertexX[local904] + this.vertexX[local910] + this.vertexX[local916]) / 3;
					local727 = (this.vertexY[local904] + this.vertexY[local910] + this.vertexY[local916]) / 3;
					local732 = (this.vertexZ[local904] + this.vertexZ[local910] + this.vertexZ[local916]) / 3;
					local748 = local898.offsetX + (local198 + local471 * local722 + local226 * local727 + local480 * local732 >> 15);
					local764 = local898.offsetY + (local345 + local474 * local722 + local373 * local727 + local483 * local732 >> 15);
					local780 = local90 + local477 * local722 + local118 * local727 + local486 * local732 >> 15;
					if (local780 > this.toolkit.anInt406) {
						local898.anInt5041 = this.threadResource.anInt1118 + local748 * local703 / local780;
						local898.anInt5052 = this.threadResource.anInt1119 + local764 * local707 / local780;
						local898.anInt5044 = local898.scaleX * local893.aShort46 * local703 / (local780 << 7);
						local898.anInt5046 = local898.scaleY * local893.aShort45 * local707 / (local780 << 7);
					} else {
						local898.anInt5044 = local898.anInt5046 = 0;
					}
				}
			}
		} else {
			for (local716 = 0; local716 < this.vertexCount; local716++) {
				local722 = this.vertexX[local716];
				local727 = this.vertexY[local716];
				local732 = this.vertexZ[local716];
				local748 = local198 + local471 * local722 + local226 * local727 + local480 * local732 >> 15;
				local764 = local345 + local474 * local722 + local373 * local727 + local483 * local732 >> 15;
				local780 = local90 + local477 * local722 + local118 * local727 + local486 * local732 >> 15;
				this.anIntArray459[local716] = local780 - local144;
				this.anIntArray467[local716] = local780;
				if (local780 >= local711) {
					this.screenX[local716] = this.threadResource.anInt1118 + local748 * local703 / arg3;
					this.screenY[local716] = this.threadResource.anInt1119 + local764 * local707 / arg3;
				} else {
					this.screenX[local716] = -5000;
					local657 = true;
				}
				if (local681) {
					this.anIntArray461[local716] = local748;
					this.anIntArray470[local716] = local764;
					this.cameraZ[local716] = local780;
				}
				if (this.toolkit.aBoolean43) {
					this.anIntArray450[local716] = this.aClass31_Sub2_3.anInt7324 + (this.aClass31_Sub2_3.anInt7331 * local722 + this.aClass31_Sub2_3.anInt7326 * local727 + this.aClass31_Sub2_3.anInt7327 * local732 >> 15);
				}
			}
			if (this.billboardFaces != null) {
				for (local887 = 0; local887 < this.billboardCount; local887++) {
					local893 = this.billboardFaces[local887];
					local898 = this.billboardAttributes[local887];
					local904 = this.faceA[local893.anInt3925];
					local910 = this.faceB[local893.anInt3925];
					local916 = this.faceC[local893.anInt3925];
					local722 = (this.vertexX[local904] + this.vertexX[local910] + this.vertexX[local916]) / 3;
					local727 = (this.vertexY[local904] + this.vertexY[local910] + this.vertexY[local916]) / 3;
					local732 = (this.vertexZ[local904] + this.vertexZ[local910] + this.vertexZ[local916]) / 3;
					local748 = local198 + local471 * local722 + local226 * local727 + local480 * local732 >> 15;
					local764 = local345 + local474 * local722 + local373 * local727 + local483 * local732 >> 15;
					local780 = local90 + local477 * local722 + local118 * local727 + local486 * local732 >> 15;
					if (local780 > this.toolkit.anInt406) {
						local898.anInt5041 = this.threadResource.anInt1118 + local748 * local703 / arg3;
						local898.anInt5052 = this.threadResource.anInt1119 + local764 * local707 / arg3;
						local898.anInt5044 = local898.scaleX * local893.aShort46 * local703 / (arg3 << 7);
						local898.anInt5046 = local898.scaleY * local893.aShort45 * local707 / (arg3 << 7);
					} else {
						local898.anInt5044 = local898.anInt5046 = 0;
					}
				}
			}
		}
		local716 = 0;
		@Pc(1474) int local1474;
		@Pc(1479) int local1479;
		if (arg2 != null) {
			for (@Pc(1463) PointEntity local1463 = (PointEntity) arg2.particles.method5127(); local1463 != null; local1463 = (PointEntity) arg2.particles.method5124()) {
				@Pc(1469) int local1469 = local1463.x >> 12;
				local1474 = local1463.y >> 12;
				local1479 = local1463.z >> 12;
				local748 = local12.anInt7329 + (local12.anInt7334 * local1469 + local12.anInt7328 * local1474 + local12.anInt7332 * local1479 >> 15);
				local764 = local12.anInt7324 + (local12.anInt7331 * local1469 + local12.anInt7326 * local1474 + local12.anInt7327 * local1479 >> 15);
				local780 = local12.anInt7333 + (local12.anInt7323 * local1469 + local12.anInt7330 * local1474 + local12.anInt7325 * local1479 >> 15);
				if (local780 >= local711) {
					this.anIntArray481[local716] = this.toolkit.projectionCenterX + local748 * local703 / local780;
					this.anIntArray482[local716] = this.toolkit.anInt422 + local764 * local707 / local780;
					this.anIntArray465[local716] = local780;
					this.anIntArray448[local716] = local780 - local144;
					this.aClass3_Sub1_Sub1Array2[local716] = local1463;
				} else {
					this.anIntArray448[local716] = Integer.MIN_VALUE;
				}
				local716++;
			}
		}
		if (arg1 != null) {
			@Pc(1604) boolean local1604 = false;
			@Pc(1606) boolean local1606 = true;
			local1474 = this.minX + this.maxX >> 1;
			local1479 = this.minZ + this.maxZ >> 1;
			@Pc(1627) short local1627 = this.minY;
			local748 = local198 + local471 * local1474 + local226 * local1627 + local480 * local1479 >> 15;
			local764 = local345 + local474 * local1474 + local373 * local1627 + local483 * local1479 >> 15;
			local780 = local90 + local477 * local1474 + local118 * local1627 + local486 * local1479 >> 15;
			@Pc(1682) int local1682;
			if (local780 >= local711) {
				local1682 = local780;
				if (arg3 != -1) {
					local1682 = arg3;
				}
				arg1.anInt4542 = this.toolkit.projectionCenterX + local748 * local703 / local1682;
				arg1.anInt4540 = this.toolkit.anInt422 + local764 * local707 / local1682;
			} else {
				local1604 = true;
			}
			local1627 = this.maxY;
			local1682 = local198 + local471 * local1474 + local226 * local1627 + local480 * local1479 >> 15;
			@Pc(1751) int local1751 = local345 + local474 * local1474 + local373 * local1627 + local483 * local1479 >> 15;
			@Pc(1767) int local1767 = local90 + local477 * local1474 + local118 * local1627 + local486 * local1479 >> 15;
			@Pc(1772) int local1772;
			if (local1767 >= local711) {
				local1772 = local1767;
				if (arg3 != -1) {
					local1772 = arg3;
				}
				arg1.anInt4543 = this.toolkit.projectionCenterX + local1682 * local703 / local1772;
				arg1.anInt4541 = this.toolkit.anInt422 + local1751 * local707 / local1772;
			} else {
				local1604 = true;
			}
			if (local1604) {
				if (local780 < local711 && local1767 < local711) {
					local1606 = false;
				} else {
					@Pc(1838) int local1838;
					@Pc(1848) int local1848;
					@Pc(1850) int local1850;
					if (local780 < local711) {
						local1772 = (local1767 - this.toolkit.anInt406 << 16) / (local1767 - local780);
						local1838 = local1682 + ((local1682 - local748) * local1772 >> 16);
						local1848 = local1751 + ((local1751 - local764) * local1772 >> 16);
						local1850 = local711;
						if (arg3 != -1) {
							local1850 = arg3;
						}
						arg1.anInt4542 = this.toolkit.projectionCenterX + local1838 * local703 / local1850;
						arg1.anInt4540 = this.toolkit.anInt422 + local1848 * local707 / local1850;
					} else if (local1767 < local711) {
						local1772 = (local780 - local711 << 16) / (local780 - local1767);
						local1838 = local748 + ((local748 - local1682) * local1772 >> 16);
						local1848 = local764 + ((local764 - local1751) * local1772 >> 16);
						local1850 = local711;
						if (arg3 != -1) {
							local1850 = arg3;
						}
						arg1.anInt4542 = this.toolkit.projectionCenterX + local1838 * local703 / local1850;
						arg1.anInt4540 = this.toolkit.anInt422 + local1848 * local707 / local1850;
					}
				}
			}
			if (local1606) {
				if (local780 > local1767) {
					local1772 = local780;
					if (arg3 != -1) {
						local1772 = arg3;
					}
					arg1.anInt4544 = this.toolkit.projectionCenterX + (local748 + this.radius) * local703 / local1772 - arg1.anInt4542;
				} else {
					local1772 = local1767;
					if (arg3 != -1) {
						local1772 = arg3;
					}
					arg1.anInt4544 = this.toolkit.projectionCenterX + (local1682 + this.radius) * local703 / local1772 - arg1.anInt4543;
				}
				arg1.positionCalculated = true;
			}
		}
		this.calculateLighting(true);
		this.rasterizer.halfBlend = (arg4 & 0x1) == 0;
		try {
			this.drawFacesRgb(local657, this.toolkit.aBoolean44 && local149 > this.toolkit.anInt411 || this.toolkit.aBoolean43, local149 - local144, arg2 != null, local716);
		} catch (@Pc(2040) Exception local2040) {
		}
		if (this.billboardFaces != null) {
			for (local887 = 0; local887 < this.faceCount; local887++) {
				this.faceBillboard[local887] = -1;
			}
		}
		this.rasterizer = null;
		if (this.toolkit.threadCount > 1) {
			synchronized (this) {
				this.rendering = false;
				this.notifyAll();
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "PA", descriptor = "()I")
	@Override
	public int PA() {
		if (!this.boundsValid) {
			this.calculateBounds();
		}
		return this.minZ;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(IIII)V")
	@Override
	public void adjustColours(@OriginalArg(0) int hue, @OriginalArg(1) int saturation, @OriginalArg(2) int lightness, @OriginalArg(3) int scale) {
		if ((this.functionMask & 0x80000) != 524288) {
			throw new IllegalStateException("FMT");
		}
		@Pc(20) int local20;
		for (@Pc(12) int local12 = 0; local12 < this.faceCount; local12++) {
			local20 = this.faceColour[local12] & 0xFFFF;
			@Pc(26) int local26 = local20 >> 10 & 0x3F;
			@Pc(32) int local32 = local20 >> 7 & 0x7;
			@Pc(36) int local36 = local20 & 0x7F;
			if (hue != -1) {
				local26 += (hue - local26) * scale >> 7;
			}
			if (saturation != -1) {
				local32 += (saturation - local32) * scale >> 7;
			}
			if (lightness != -1) {
				local36 += (lightness - local36) * scale >> 7;
			}
			this.faceColour[local12] = (short) (local26 << 10 | local32 << 7 | local36);
		}
		if (this.billboardFaces != null) {
			for (local20 = 0; local20 < this.billboardCount; local20++) {
				@Pc(105) JavaBillboardFace local105 = this.billboardFaces[local20];
				@Pc(110) JavaBillboardAttributes local110 = this.billboardAttributes[local20];
				local110.diffuseColor = local110.diffuseColor & 0xFF000000 | PathingEntity.anIntArray423[Class56.method1470(this.faceColour[local105.anInt3925] & 0xFFFF)] & 0xFFFFFF;
			}
		}
		if (this.lightingState == 2) {
			this.lightingState = 1;
		}
	}

	@OriginalMember(owner = "client!sk", name = "D", descriptor = "()I")
	@Override
	public int D() {
		return this.ambient;
	}

	@OriginalMember(owner = "client!sk", name = "e", descriptor = "(I)V")
	private void method4916(@OriginalArg(0) int arg0) {
		@Pc(8) short local8;
		@Pc(13) short local13;
		@Pc(18) short local18;
		@Pc(27) int local27;
		@Pc(46) int local46;
		@Pc(65) int local65;
		@Pc(81) int local81;
		@Pc(309) int local309;
		@Pc(314) int local314;
		@Pc(319) int local319;
		if (!this.toolkit.aBoolean43) {
			local8 = this.faceA[arg0];
			local13 = this.faceB[arg0];
			local18 = this.faceC[arg0];
			local27 = this.anIntArray467[local8] - this.toolkit.anInt411;
			if (local27 > 255) {
				local27 = 255;
			} else if (local27 < 0) {
				local27 = 0;
			}
			local46 = this.anIntArray467[local13] - this.toolkit.anInt411;
			if (local46 > 255) {
				local46 = 255;
			} else if (local46 < 0) {
				local46 = 0;
			}
			local65 = this.anIntArray467[local18] - this.toolkit.anInt411;
			if (local65 > 255) {
				local65 = 255;
			} else if (local65 < 0) {
				local65 = 0;
			}
			local81 = local27 + local46 + local65;
			if (local81 != 765) {
				if (local81 == 0) {
					this.drawTriangleRgb(arg0);
				} else {
					if (this.faceAlpha == null) {
						this.rasterizer.alpha = 0;
					} else {
						this.rasterizer.alpha = this.faceAlpha[arg0] & 0xFF;
					}
					this.rasterizer.aBoolean519 = this.aBooleanArray18[arg0];
					if (this.faceTextures != null && this.faceTextures[arg0] != -1) {
						if (this.aByteArray88 == null || this.aByteArray88[arg0] == -1) {
							local309 = local8;
							local314 = local13;
							local319 = local18;
						} else {
							@Pc(304) int local304 = this.aByteArray88[arg0] & 0xFF;
							local309 = this.anIntArray474[local304];
							local314 = this.anIntArray452[local304];
							local319 = this.anIntArray447[local304];
						}
						if (this.faceColourC[arg0] == -1) {
							this.rasterizer.method4542(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], this.toolkit.anInt409, local27, local46, local65, this.faceColourA[arg0], this.faceColourA[arg0], this.faceColourA[arg0], this.anIntArray461[local309], this.anIntArray461[local314], this.anIntArray461[local319], this.anIntArray470[local309], this.anIntArray470[local314], this.anIntArray470[local319], this.cameraZ[local309], this.cameraZ[local314], this.cameraZ[local319], this.faceTextures[arg0]);
						} else {
							this.rasterizer.method4542(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], this.toolkit.anInt409, local27, local46, local65, this.faceColourA[arg0], this.faceColourB[arg0], this.faceColourC[arg0], this.anIntArray461[local309], this.anIntArray461[local314], this.anIntArray461[local319], this.anIntArray470[local309], this.anIntArray470[local314], this.anIntArray470[local319], this.cameraZ[local309], this.cameraZ[local314], this.cameraZ[local319], this.faceTextures[arg0]);
						}
					} else if (this.faceColourC[arg0] == -1) {
						this.rasterizer.method4548(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local27 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local46 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local65 << 24 | this.toolkit.anInt409));
					} else {
						this.rasterizer.method4548(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local27 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourB[arg0] & 0xFFFF], local46 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourC[arg0] & 0xFFFF], local65 << 24 | this.toolkit.anInt409));
					}
				}
			}
		} else if (!this.aBooleanArray19[arg0]) {
			local8 = this.faceA[arg0];
			local13 = this.faceB[arg0];
			local18 = this.faceC[arg0];
			local27 = 0;
			local46 = 0;
			local65 = 0;
			if (this.anIntArray450[local8] > this.toolkit.anInt426) {
				local27 = 255;
			} else if (this.anIntArray450[local8] > this.toolkit.anInt410) {
				local27 = (this.toolkit.anInt410 - this.anIntArray450[local8]) * 255 / (this.toolkit.anInt410 - this.toolkit.anInt426);
			}
			if (this.anIntArray450[local13] > this.toolkit.anInt426) {
				local46 = 255;
			} else if (this.anIntArray450[local13] > this.toolkit.anInt410) {
				local46 = (this.toolkit.anInt410 - this.anIntArray450[local13]) * 255 / (this.toolkit.anInt410 - this.toolkit.anInt426);
			}
			if (this.anIntArray450[local18] > this.toolkit.anInt426) {
				local65 = 255;
			} else if (this.anIntArray450[local18] > this.toolkit.anInt410) {
				local65 = (this.toolkit.anInt410 - this.anIntArray450[local18]) * 255 / (this.toolkit.anInt410 - this.toolkit.anInt426);
			}
			if (this.faceAlpha == null) {
				this.rasterizer.alpha = 0;
			} else {
				this.rasterizer.alpha = this.faceAlpha[arg0] & 0xFF;
			}
			this.rasterizer.aBoolean519 = this.aBooleanArray18[arg0];
			if (this.faceTextures != null && this.faceTextures[arg0] != -1) {
				if (this.aByteArray88 == null || this.aByteArray88[arg0] == -1) {
					local81 = local8;
					local309 = local13;
					local314 = local18;
				} else {
					local319 = this.aByteArray88[arg0] & 0xFF;
					local81 = this.anIntArray474[local319];
					local309 = this.anIntArray452[local319];
					local314 = this.anIntArray447[local319];
				}
				if (this.faceColourC[arg0] == -1) {
					this.rasterizer.method4542(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], this.toolkit.anInt409, local27, local46, local65, this.faceColourA[arg0], this.faceColourA[arg0], this.faceColourA[arg0], this.anIntArray461[local81], this.anIntArray461[local309], this.anIntArray461[local314], this.anIntArray470[local81], this.anIntArray470[local309], this.anIntArray470[local314], this.cameraZ[local81], this.cameraZ[local309], this.cameraZ[local314], this.faceTextures[arg0]);
				} else {
					this.rasterizer.method4542(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], this.toolkit.anInt409, local27, local46, local65, this.faceColourA[arg0], this.faceColourB[arg0], this.faceColourC[arg0], this.anIntArray461[local81], this.anIntArray461[local309], this.anIntArray461[local314], this.anIntArray470[local81], this.anIntArray470[local309], this.anIntArray470[local314], this.cameraZ[local81], this.cameraZ[local309], this.cameraZ[local314], this.faceTextures[arg0]);
				}
			} else if (this.faceColourC[arg0] == -1) {
				this.rasterizer.method4548(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local27 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local46 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local65 << 24 | this.toolkit.anInt409));
			} else {
				this.rasterizer.method4548(this.screenY[local8], this.screenY[local13], this.screenY[local18], this.screenX[local8], this.screenX[local13], this.screenX[local18], Packet.method2539(PathingEntity.anIntArray423[this.faceColourA[arg0] & 0xFFFF], local27 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourB[arg0] & 0xFFFF], local46 << 24 | this.toolkit.anInt409), Packet.method2539(PathingEntity.anIntArray423[this.faceColourC[arg0] & 0xFFFF], local65 << 24 | this.toolkit.anInt409));
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "n", descriptor = "()V")
	private void rotate90() {
		for (@Pc(1) int local1 = 0; local1 < this.vertexCount; local1++) {
			@Pc(7) int local7 = this.vertexX[local1];
			this.vertexX[local1] = this.vertexZ[local1];
			this.vertexZ[local1] = -local7;
		}
		this.invalidateNormals();
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Z)V")
	private void calculateLighting(@OriginalArg(0) boolean discardShading) {
		synchronized (this) {
			if (this.lightingState == 1) {
				this.relightColours();
			} else if (this.lightingState == 2) {
				if ((this.functionMask & 0x97298) == 0) {
					this.faceColour = null;
				}
				if (discardShading) {
					this.shadingType = null;
				}
			} else {
				this.calculateNormals();
				@Pc(38) int local38 = this.toolkit.sunX;
				@Pc(42) int local42 = this.toolkit.sunY;
				@Pc(46) int local46 = this.toolkit.sunZ;
				@Pc(52) int local52 = this.toolkit.ambient >> 8;
				@Pc(61) int local61 = this.toolkit.sunIntensity * 768 / this.contrast;
				@Pc(70) int local70 = this.toolkit.reverseSunIntensity * 768 / this.contrast;
				if (this.faceColourA == null) {
					this.faceColourA = new int[this.faceCount];
					this.faceColourB = new int[this.faceCount];
					this.faceColourC = new int[this.faceCount];
				}
				for (@Pc(90) int local90 = 0; local90 < this.faceCount; local90++) {
					@Pc(96) byte local96;
					if (this.shadingType == null) {
						local96 = 0;
					} else {
						local96 = this.shadingType[local90];
					}
					@Pc(107) byte local107;
					if (this.faceAlpha == null) {
						local107 = 0;
					} else {
						local107 = this.faceAlpha[local90];
					}
					@Pc(118) short local118;
					if (this.faceTextures == null) {
						local118 = -1;
					} else {
						local118 = this.faceTextures[local90];
					}
					if (local107 == -2) {
						local96 = 3;
					}
					if (local107 == -1) {
						local96 = 2;
					}
					@Pc(155) int local155;
					if (local118 == -1) {
						@Pc(228) int local228;
						@Pc(211) int local211;
						@Pc(218) int local218;
						@Pc(146) int local146;
						@Pc(163) short local163;
						if (local96 == 0) {
							local146 = this.faceColour[local90] & 0xFFFF;
							local155 = (local146 & 0x7F) * this.ambient >> 7;
							local163 = Class56.method1470(local146 & 0xFFFFFF80 | local155);
							@Pc(182) VertexNormal local182;
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceA[local90]] == null) {
								local182 = this.aClass86Array1[this.faceA[local90]];
							} else {
								local182 = this.vertexNormalsOffset[this.faceA[local90]];
							}
							local211 = (local38 * local182.x + local42 * local182.y + local46 * local182.z) / local182.magnitude >> 16;
							local218 = local211 > 256 ? local61 : local70;
							local228 = (local52 >> 1) + (local218 * local211 >> 17);
							this.faceColourA[local90] = local228 << 17 | GlVolumeTexture.method791(local163, local228);
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceB[local90]] == null) {
								local182 = this.aClass86Array1[this.faceB[local90]];
							} else {
								local182 = this.vertexNormalsOffset[this.faceB[local90]];
							}
							local211 = (local38 * local182.x + local42 * local182.y + local46 * local182.z) / local182.magnitude >> 16;
							local218 = local211 > 256 ? local61 : local70;
							local228 = (local52 >> 1) + (local218 * local211 >> 17);
							this.faceColourB[local90] = local228 << 17 | GlVolumeTexture.method791(local163, local228);
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceC[local90]] == null) {
								local182 = this.aClass86Array1[this.faceC[local90]];
							} else {
								local182 = this.vertexNormalsOffset[this.faceC[local90]];
							}
							local211 = (local38 * local182.x + local42 * local182.y + local46 * local182.z) / local182.magnitude >> 16;
							local218 = local211 > 256 ? local61 : local70;
							local228 = (local52 >> 1) + (local218 * local211 >> 17);
							this.faceColourC[local90] = local228 << 17 | GlVolumeTexture.method791(local163, local228);
						} else if (local96 == 1) {
							local146 = this.faceColour[local90] & 0xFFFF;
							local155 = (local146 & 0x7F) * this.ambient >> 7;
							local163 = Class56.method1470(local146 & 0xFFFFFF80 | local155);
							@Pc(427) FaceNormal local427 = this.faceNormals[local90];
							local228 = local38 * local427.x + local42 * local427.y + local46 * local427.z >> 16;
							local211 = local228 > 256 ? local61 : local70;
							local218 = (local52 >> 1) + (local211 * local228 >> 17);
							this.faceColourA[local90] = local218 << 17 | GlVolumeTexture.method791(local163, local218);
							this.faceColourC[local90] = -1;
						} else if (local96 == 3) {
							this.faceColourA[local90] = 128;
							this.faceColourC[local90] = -1;
						} else {
							this.faceColourC[local90] = -2;
						}
					} else {
						@Pc(549) int local549;
						@Pc(556) int local556;
						if (local96 == 0) {
							@Pc(520) VertexNormal local520;
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceA[local90]] == null) {
								local520 = this.aClass86Array1[this.faceA[local90]];
							} else {
								local520 = this.vertexNormalsOffset[this.faceA[local90]];
							}
							local549 = (local38 * local520.x + local42 * local520.y + local46 * local520.z) / local520.magnitude >> 16;
							local556 = local549 > 256 ? local61 : local70;
							local155 = (local52 >> 2) + (local556 * local549 >> 18);
							this.faceColourA[local90] = this.clampLightness(local155);
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceB[local90]] == null) {
								local520 = this.aClass86Array1[this.faceB[local90]];
							} else {
								local520 = this.vertexNormalsOffset[this.faceB[local90]];
							}
							local549 = (local38 * local520.x + local42 * local520.y + local46 * local520.z) / local520.magnitude >> 16;
							local556 = local549 > 256 ? local61 : local70;
							local155 = (local52 >> 2) + (local556 * local549 >> 18);
							this.faceColourB[local90] = this.clampLightness(local155);
							if (this.vertexNormalsOffset == null || this.vertexNormalsOffset[this.faceC[local90]] == null) {
								local520 = this.aClass86Array1[this.faceC[local90]];
							} else {
								local520 = this.vertexNormalsOffset[this.faceC[local90]];
							}
							local549 = (local38 * local520.x + local42 * local520.y + local46 * local520.z) / local520.magnitude >> 16;
							local556 = local549 > 256 ? local61 : local70;
							local155 = (local52 >> 2) + (local556 * local549 >> 18);
							this.faceColourC[local90] = this.clampLightness(local155);
						} else if (local96 == 1) {
							@Pc(726) FaceNormal local726 = this.faceNormals[local90];
							local155 = local38 * local726.x + local42 * local726.y + local46 * local726.z >> 16;
							local549 = local155 > 256 ? local61 : local70;
							local556 = (local52 >> 2) + (local549 * local155 >> 18);
							this.faceColourA[local90] = this.clampLightness(local556);
							this.faceColourC[local90] = -1;
						} else {
							this.faceColourC[local90] = -2;
						}
					}
				}
				this.aClass86Array1 = null;
				this.vertexNormalsOffset = null;
				this.faceNormals = null;
				if ((this.functionMask & 0x97298) == 0) {
					this.faceColour = null;
				}
				if (discardShading) {
					this.shadingType = null;
				}
				this.lightingState = 2;
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "BA", descriptor = "(SS)V")
	@Override
	public void copyAlpha(@OriginalArg(0) short arg0, @OriginalArg(1) short arg1) {
		for (@Pc(1) int local1 = 0; local1 < this.faceCount; local1++) {
			if (this.faceColour[local1] == arg0) {
				this.faceColour[local1] = arg1;
			}
		}
		if (this.billboardFaces != null) {
			for (@Pc(23) int local23 = 0; local23 < this.billboardCount; local23++) {
				@Pc(29) JavaBillboardFace local29 = this.billboardFaces[local23];
				@Pc(34) JavaBillboardAttributes local34 = this.billboardAttributes[local23];
				local34.diffuseColor = local34.diffuseColor & 0xFF000000 | PathingEntity.anIntArray423[Class56.method1470(this.faceColour[local29.anInt3925]) & 0xFFFF] & 0xFFFFFF;
			}
		}
		if (this.lightingState == 2) {
			this.lightingState = 1;
		}
	}

	@OriginalMember(owner = "client!sk", name = "o", descriptor = "()V")
	private void calculateBounds() {
		if (this.boundsValid) {
			return;
		}
		@Pc(5) int local5 = 0;
		@Pc(7) int local7 = 0;
		@Pc(9) int local9 = 32767;
		@Pc(11) int local11 = 32767;
		@Pc(13) int local13 = 32767;
		@Pc(15) int local15 = -32768;
		@Pc(17) int local17 = -32768;
		@Pc(19) int local19 = -32768;
		for (@Pc(21) int local21 = 0; local21 < this.maxVertex; local21++) {
			@Pc(27) int local27 = this.vertexX[local21];
			@Pc(32) int local32 = this.vertexY[local21];
			@Pc(37) int local37 = this.vertexZ[local21];
			if (local27 < local9) {
				local9 = local27;
			}
			if (local27 > local15) {
				local15 = local27;
			}
			if (local32 < local11) {
				local11 = local32;
			}
			if (local32 > local17) {
				local17 = local32;
			}
			if (local37 < local13) {
				local13 = local37;
			}
			if (local37 > local19) {
				local19 = local37;
			}
			@Pc(75) int local75 = local27 * local27 + local37 * local37;
			if (local75 > local5) {
				local5 = local75;
			}
			local75 += local32 * local32;
			if (local75 > local7) {
				local7 = local75;
			}
		}
		this.minX = (short) local9;
		this.maxX = (short) local15;
		this.minY = (short) local11;
		this.maxY = (short) local17;
		this.minZ = (short) local13;
		this.maxZ = (short) local19;
		this.radius = (short) (Math.sqrt((double) local5) + 0.99D);
		Math.sqrt((double) local7);
		this.boundsValid = true;
	}

	@OriginalMember(owner = "client!sk", name = "p", descriptor = "()V")
	private void rotate270WithNormals() {
		@Pc(7) int local7;
		for (@Pc(1) int local1 = 0; local1 < this.maxVertex; local1++) {
			local7 = this.vertexZ[local1];
			this.vertexZ[local1] = this.vertexX[local1];
			this.vertexX[local1] = -local7;
			if (this.aClass86Array1[local1] != null) {
				local7 = this.aClass86Array1[local1].z;
				this.aClass86Array1[local1].z = this.aClass86Array1[local1].x;
				this.aClass86Array1[local1].x = -local7;
			}
		}
		@Pc(71) int local71;
		if (this.faceNormals != null) {
			for (local7 = 0; local7 < this.faceCount; local7++) {
				if (this.faceNormals[local7] != null) {
					local71 = this.faceNormals[local7].z;
					this.faceNormals[local7].z = this.faceNormals[local7].x;
					this.faceNormals[local7].x = -local71;
				}
			}
		}
		for (local7 = this.maxVertex; local7 < this.vertexCount; local7++) {
			local71 = this.vertexZ[local7];
			this.vertexZ[local7] = this.vertexX[local7];
			this.vertexX[local7] = -local71;
		}
		this.lightingState = 0;
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(IIIIIIII)Z")
	private boolean pointInFaceBounds(@OriginalArg(0) int x, @OriginalArg(1) int y, @OriginalArg(2) int y0, @OriginalArg(3) int y1, @OriginalArg(4) int y2, @OriginalArg(5) int x0, @OriginalArg(6) int x1, @OriginalArg(7) int x2) {
		if (y < y0 && y < y1 && y < y2) {
			return false;
		} else if (y > y0 && y > y1 && y > y2) {
			return false;
		} else if (x < x0 && x < x1 && x < x2) {
			return false;
		} else {
			return x <= x0 || x <= x1 || x <= x2;
		}
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(BIZ)Lclient!t;")
	@Override
	public Model copy(@OriginalArg(0) byte copyType, @OriginalArg(1) int functionMask, @OriginalArg(2) boolean ensureLit) {
		this.useThreadCopyCache(Thread.currentThread());
		@Pc(11) JavaModel local11;
		@Pc(8) JavaModel local8;
		if (copyType == 1) {
			local8 = this.copySrcModel1;
			local11 = this.copyDstModel1;
		} else if (copyType == 2) {
			local8 = this.copySrcModel2;
			local11 = this.copyDstModel2;
		} else if (copyType == 3) {
			local8 = this.copySrcModel3;
			local11 = this.copyDstModel3;
		} else if (copyType == 4) {
			local8 = this.copySrcModel4;
			local11 = this.copyDstModel4;
		} else if (copyType == 5) {
			local8 = this.copySrcModel5;
			local11 = this.copyDstModel5;
		} else {
			local11 = local8 = new JavaModel(this.toolkit);
		}
		return this.copyTo(local11, local8, functionMask, copyType != 0, ensureLit);
	}

	@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!sk;Lclient!sk;IZZ)Lclient!t;")
	private Model copyTo(@OriginalArg(0) JavaModel arg0, @OriginalArg(1) JavaModel buffers, @OriginalArg(2) int functionMask, @OriginalArg(3) boolean reserve, @OriginalArg(4) boolean ensureLit) {
		arg0.boundsValid = this.boundsValid;
		if (this.boundsValid) {
			arg0.maxX = this.maxX;
			arg0.maxY = this.maxY;
			arg0.maxZ = this.maxZ;
			arg0.minX = this.minX;
			arg0.minY = this.minY;
			arg0.minZ = this.minZ;
			arg0.radius = this.radius;
		}
		arg0.ambient = this.ambient;
		arg0.contrast = this.contrast;
		arg0.vertexCount = this.vertexCount;
		arg0.maxVertex = this.maxVertex;
		arg0.faceCount = this.faceCount;
		arg0.billboardCount = this.billboardCount;
		@Pc(75) boolean local75 = (functionMask & 0x7) == 7 | (functionMask & 0x20) != 0;
		@Pc(85) boolean local85 = local75 || (functionMask & 0x1) != 0;
		@Pc(95) boolean local95 = local75 || (functionMask & 0x2) != 0;
		@Pc(109) boolean local109 = local75 || (functionMask & 0x4) != 0 || (functionMask & 0x10) != 0;
		@Pc(141) int local141;
		if (local85 || local95 || local109) {
			if (local85) {
				if (buffers.vertexX == null || buffers.vertexX.length < this.vertexCount) {
					arg0.vertexX = buffers.vertexX = new int[this.vertexCount];
				} else {
					arg0.vertexX = buffers.vertexX;
				}
				for (local141 = 0; local141 < this.vertexCount; local141++) {
					arg0.vertexX[local141] = this.vertexX[local141];
				}
			} else {
				arg0.vertexX = this.vertexX;
			}
			if (local95) {
				if (buffers.vertexY == null || buffers.vertexY.length < this.vertexCount) {
					arg0.vertexY = buffers.vertexY = new int[this.vertexCount];
				} else {
					arg0.vertexY = buffers.vertexY;
				}
				for (local141 = 0; local141 < this.vertexCount; local141++) {
					arg0.vertexY[local141] = this.vertexY[local141];
				}
			} else {
				arg0.vertexY = this.vertexY;
			}
			if (local109) {
				if (buffers.vertexZ == null || buffers.vertexZ.length < this.vertexCount) {
					arg0.vertexZ = buffers.vertexZ = new int[this.vertexCount];
				} else {
					arg0.vertexZ = buffers.vertexZ;
				}
				for (local141 = 0; local141 < this.vertexCount; local141++) {
					arg0.vertexZ[local141] = this.vertexZ[local141];
				}
			} else {
				arg0.vertexZ = this.vertexZ;
			}
		} else {
			arg0.vertexX = this.vertexX;
			arg0.vertexY = this.vertexY;
			arg0.vertexZ = this.vertexZ;
		}
		if ((functionMask & 0x84080) == 0) {
			arg0.faceColour = this.faceColour;
		} else {
			if (buffers.faceColour == null || buffers.faceColour.length < this.faceCount) {
				local141 = this.faceCount;
				arg0.faceColour = buffers.faceColour = new short[local141];
			} else {
				arg0.faceColour = buffers.faceColour;
			}
			for (local141 = 0; local141 < this.faceCount; local141++) {
				arg0.faceColour[local141] = this.faceColour[local141];
			}
		}
		if ((functionMask & 0x97218) != 0) {
			arg0.lightingState = 0;
			arg0.faceColourA = arg0.faceColourB = arg0.faceColourC = null;
		} else if ((functionMask & 0x80) == 0) {
			if (ensureLit) {
				this.calculateLighting(false);
			}
			arg0.faceColourA = this.faceColourA;
			arg0.faceColourB = this.faceColourB;
			arg0.faceColourC = this.faceColourC;
			arg0.lightingState = this.lightingState;
		} else {
			if (ensureLit) {
				this.calculateLighting(false);
			}
			if (this.faceColourA != null) {
				if (buffers.faceColourA == null || buffers.faceColourA.length < this.faceCount) {
					local141 = this.faceCount;
					arg0.faceColourA = buffers.faceColourA = new int[local141];
					arg0.faceColourB = buffers.faceColourB = new int[local141];
					arg0.faceColourC = buffers.faceColourC = new int[local141];
				} else {
					arg0.faceColourA = buffers.faceColourA;
					arg0.faceColourB = buffers.faceColourB;
					arg0.faceColourC = buffers.faceColourC;
				}
				for (local141 = 0; local141 < this.faceCount; local141++) {
					arg0.faceColourA[local141] = this.faceColourA[local141];
					arg0.faceColourB[local141] = this.faceColourB[local141];
					arg0.faceColourC[local141] = this.faceColourC[local141];
				}
			}
			arg0.lightingState = this.lightingState;
		}
		if ((functionMask & 0x100) == 0) {
			arg0.faceAlpha = this.faceAlpha;
		} else {
			if (buffers.faceAlpha == null || buffers.faceAlpha.length < this.faceCount) {
				local141 = this.faceCount;
				arg0.faceAlpha = buffers.faceAlpha = new byte[local141];
			} else {
				arg0.faceAlpha = buffers.faceAlpha;
			}
			if (this.faceAlpha == null) {
				for (local141 = 0; local141 < this.faceCount; local141++) {
					arg0.faceAlpha[local141] = 0;
				}
			} else {
				for (local141 = 0; local141 < this.faceCount; local141++) {
					arg0.faceAlpha[local141] = this.faceAlpha[local141];
				}
			}
		}
		if ((functionMask & 0x8) == 0 && (functionMask & 0x10) == 0) {
			if (ensureLit) {
				this.calculateNormals();
			}
			arg0.aClass86Array1 = this.aClass86Array1;
			arg0.faceNormals = this.faceNormals;
		} else {
			if (buffers.aClass86Array1 == null || buffers.aClass86Array1.length < this.maxVertex) {
				local141 = this.maxVertex;
				arg0.aClass86Array1 = buffers.aClass86Array1 = new VertexNormal[local141];
			} else {
				arg0.aClass86Array1 = buffers.aClass86Array1;
			}
			if (this.aClass86Array1 == null) {
				arg0.aClass86Array1 = null;
			} else {
				for (local141 = 0; local141 < this.maxVertex; local141++) {
					arg0.aClass86Array1[local141] = new VertexNormal(this.aClass86Array1[local141]);
				}
			}
			if (this.faceNormals == null) {
				arg0.faceNormals = null;
			} else {
				if (buffers.faceNormals == null || buffers.faceNormals.length < this.faceCount) {
					local141 = this.faceCount;
					arg0.faceNormals = buffers.faceNormals = new FaceNormal[local141];
				} else {
					arg0.faceNormals = buffers.faceNormals;
				}
				for (local141 = 0; local141 < this.faceCount; local141++) {
					arg0.faceNormals[local141] = this.faceNormals[local141] == null ? null : new FaceNormal(this.faceNormals[local141]);
				}
			}
		}
		if ((functionMask & 0x8000) == 0) {
			arg0.faceTextures = this.faceTextures;
		} else if (this.faceTextures == null) {
			arg0.faceTextures = null;
		} else {
			if (buffers.faceTextures == null || buffers.faceTextures.length < this.faceCount) {
				local141 = this.faceCount;
				arg0.faceTextures = buffers.faceTextures = new short[local141];
			} else {
				arg0.faceTextures = buffers.faceTextures;
			}
			for (local141 = 0; local141 < this.faceCount; local141++) {
				arg0.faceTextures[local141] = this.faceTextures[local141];
			}
		}
		if ((functionMask & 0x10000) == 0) {
			arg0.shadingType = this.shadingType;
		} else if (this.shadingType == null) {
			arg0.shadingType = null;
		} else {
			if (buffers.shadingType == null || buffers.shadingType.length < this.faceCount) {
				local141 = reserve ? this.faceCount + 100 : this.faceCount;
				arg0.shadingType = buffers.shadingType = new byte[local141];
			} else {
				arg0.shadingType = buffers.shadingType;
			}
			for (local141 = 0; local141 < this.faceCount; local141++) {
				arg0.shadingType[local141] = this.shadingType[local141];
			}
		}
		if ((functionMask & 0x580) == 0) {
			arg0.billboardAttributes = this.billboardAttributes;
		} else if (buffers.billboardAttributes == null || buffers.billboardAttributes.length < this.billboardCount) {
			local141 = this.billboardCount;
			arg0.billboardAttributes = buffers.billboardAttributes = new JavaBillboardAttributes[local141];
			for (@Pc(783) int local783 = 0; local783 < this.billboardCount; local783++) {
				arg0.billboardAttributes[local783] = this.billboardAttributes[local783].method3852();
			}
		} else {
			arg0.billboardAttributes = buffers.billboardAttributes;
			for (local141 = 0; local141 < this.billboardCount; local141++) {
				arg0.billboardAttributes[local141].method3857(this.billboardAttributes[local141]);
			}
		}
		arg0.anIntArrayArray55 = this.anIntArrayArray55;
		arg0.anIntArrayArray52 = this.anIntArrayArray52;
		arg0.anIntArrayArray54 = this.anIntArrayArray54;
		arg0.originModels = this.originModels;
		arg0.aShortArray218 = this.aShortArray218;
		arg0.aByteArray88 = this.aByteArray88;
		arg0.facePriority = this.facePriority;
		arg0.aByte72 = this.aByte72;
		arg0.anInt6297 = this.anInt6297;
		arg0.anIntArray474 = this.anIntArray474;
		arg0.anIntArray452 = this.anIntArray452;
		arg0.anIntArray447 = this.anIntArray447;
		arg0.faceA = this.faceA;
		arg0.faceB = this.faceB;
		arg0.faceC = this.faceC;
		arg0.emitters = this.emitters;
		arg0.effectors = this.effectors;
		arg0.billboardFaces = this.billboardFaces;
		arg0.functionMask = functionMask;
		return arg0;
	}

	@OriginalMember(owner = "client!sk", name = "q", descriptor = "()V")
	private void rotate180WithNormals() {
		for (@Pc(1) int local1 = 0; local1 < this.maxVertex; local1++) {
			this.vertexX[local1] = -this.vertexX[local1];
			this.vertexZ[local1] = -this.vertexZ[local1];
			if (this.aClass86Array1[local1] != null) {
				this.aClass86Array1[local1].x = -this.aClass86Array1[local1].x;
				this.aClass86Array1[local1].z = -this.aClass86Array1[local1].z;
			}
		}
		@Pc(57) int local57;
		if (this.faceNormals != null) {
			for (local57 = 0; local57 < this.faceCount; local57++) {
				if (this.faceNormals[local57] != null) {
					this.faceNormals[local57].x = -this.faceNormals[local57].x;
					this.faceNormals[local57].z = -this.faceNormals[local57].z;
				}
			}
		}
		for (local57 = this.maxVertex; local57 < this.vertexCount; local57++) {
			this.vertexX[local57] = -this.vertexX[local57];
			this.vertexZ[local57] = -this.vertexZ[local57];
		}
		this.lightingState = 0;
		this.boundsValid = false;
	}

	@OriginalMember(owner = "client!sk", name = "r", descriptor = "()V")
	private void calculateNormals() {
		if (this.lightingState != 0 || this.aClass86Array1 != null) {
			return;
		}
		this.aClass86Array1 = new VertexNormal[this.maxVertex];
		for (@Pc(13) int local13 = 0; local13 < this.maxVertex; local13++) {
			this.aClass86Array1[local13] = new VertexNormal();
		}
		for (@Pc(28) int local28 = 0; local28 < this.faceCount; local28++) {
			@Pc(34) short local34 = this.faceA[local28];
			@Pc(39) short local39 = this.faceB[local28];
			@Pc(44) short local44 = this.faceC[local28];
			@Pc(54) int local54 = this.vertexX[local39] - this.vertexX[local34];
			@Pc(64) int local64 = this.vertexY[local39] - this.vertexY[local34];
			@Pc(74) int local74 = this.vertexZ[local39] - this.vertexZ[local34];
			@Pc(84) int local84 = this.vertexX[local44] - this.vertexX[local34];
			@Pc(94) int local94 = this.vertexY[local44] - this.vertexY[local34];
			@Pc(104) int local104 = this.vertexZ[local44] - this.vertexZ[local34];
			@Pc(112) int local112 = local64 * local104 - local94 * local74;
			@Pc(120) int local120 = local74 * local84 - local104 * local54;
			@Pc(128) int local128;
			for (local128 = local54 * local94 - local84 * local64; local112 > 8192 || local120 > 8192 || local128 > 8192 || local112 < -8192 || local120 < -8192 || local128 < -8192; local128 >>= 0x1) {
				local112 >>= 0x1;
				local120 >>= 0x1;
			}
			@Pc(174) int local174 = (int) Math.sqrt((double) (local112 * local112 + local120 * local120 + local128 * local128));
			if (local174 <= 0) {
				local174 = 1;
			}
			local112 = local112 * 256 / local174;
			local120 = local120 * 256 / local174;
			local128 = local128 * 256 / local174;
			@Pc(201) byte local201;
			if (this.shadingType == null) {
				local201 = 0;
			} else {
				local201 = this.shadingType[local28];
			}
			if (local201 == 0) {
				@Pc(214) VertexNormal local214 = this.aClass86Array1[local34];
				local214.x += local112;
				local214.y += local120;
				local214.z += local128;
				local214.magnitude++;
				@Pc(243) VertexNormal local243 = this.aClass86Array1[local39];
				local243.x += local112;
				local243.y += local120;
				local243.z += local128;
				local243.magnitude++;
				@Pc(272) VertexNormal local272 = this.aClass86Array1[local44];
				local272.x += local112;
				local272.y += local120;
				local272.z += local128;
				local272.magnitude++;
			} else if (local201 == 1) {
				if (this.faceNormals == null) {
					this.faceNormals = new FaceNormal[this.faceCount];
				}
				@Pc(317) FaceNormal local317 = this.faceNormals[local28] = new FaceNormal();
				local317.x = local112;
				local317.y = local120;
				local317.z = local128;
			}
		}
	}

	@OriginalMember(owner = "client!sk", name = "fa", descriptor = "(Lclient!ba;)Lclient!ba;")
	@Override
	public Shadow fa(@OriginalArg(0) Shadow shadow) {
		return null;
	}

	@OriginalMember(owner = "client!sk", name = "b", descriptor = "()[Lclient!pf;")
	@Override
	public EffectiveVertex[] getEffectiveVertices() {
		return this.effectors;
	}

@OriginalMember(owner = "client!sk", name = "H", descriptor = "I")
	public static final int anInt6294 = 4096;
@OriginalMember(owner = "client!sk", name = "rb", descriptor = "I")
	public static final int anInt6298 = Class196.method4426(2200);
@OriginalMember(owner = "client!sk", name = "Wb", descriptor = "I")
	public static final int anInt6301 = 4096;
@OriginalMember(owner = "client!sk", name = "sc", descriptor = "I")
	public static int renderCycle = 0;
@OriginalMember(owner = "client!sk", name = "a", descriptor = "(Lclient!bc;[Lclient!sk;)Lclient!sk;")
	public static JavaModel method4912(@OriginalArg(0) JavaToolkit arg0, @OriginalArg(1) JavaModel[] arg1) {
		@Pc(1) int local1 = 0;
		@Pc(3) JavaModel local3 = null;
		for (@Pc(5) int local5 = 0; local5 < arg1.length; local5++) {
			if (arg1[local5] != null) {
				local1++;
				local3 = arg1[local5];
			}
		}
		if (local1 == 1) {
			return local3;
		} else {
			return new JavaModel(arg0, arg1);
		}
	}
}
