package com.jagex.game.runetek5.graphics.model;

import jaggl.OpenGL;
import jaclib.memory.Stream;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.buffer.ArrayBufferGL;
import com.jagex.game.runetek5.buffer.ElementsBufferGL;
import com.jagex.game.runetek5.cache.node.lru.GlShadow;
import com.jagex.game.runetek5.client.hd.shadow.ShadowCutout;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.Class177;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class50_Sub1;
import com.jagex.game.runetek5.deob.Class65;
import com.jagex.game.runetek5.deob.Class85;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.deob.Static102;
import com.jagex.game.runetek5.deob.Static173;
import com.jagex.game.runetek5.deob.Static231;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static312;
import com.jagex.game.runetek5.deob.Static391;
import com.jagex.game.runetek5.deob.Static410;
import com.jagex.game.runetek5.deob.Static441;
import com.jagex.game.runetek5.deob.Static449;
import com.jagex.game.runetek5.entity.BoundingBoxPolygon;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.graphics.model.billboard.BillboardType;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
import com.jagex.game.runetek5.graphics.model.particle.EmissiveTriangle;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.toolkit.opengl.effect.UnlitEffect;
import java.awt.Color;
import java.util.Date;
import com.jagex.game.runetek5.deob.Class4_Sub25;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.model.*;
import com.jagex.game.runetek5.deob.Class16_Sub2_Sub3;
import com.jagex.game.runetek5.deob.Class4_Sub9;
import com.jagex.game.runetek5.deob.Class4_Sub45;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.deob.Class16_Sub5_Sub2;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.js5.NamedFileReference;
import com.jagex.game.runetek5.deob.Class249;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub15;
import com.jagex.game.runetek5.client.loading.loader.Js5GroupLoader;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub19;
import com.jagex.game.runetek5.deob.Class50;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.deob.Class27_Sub1;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class6_Sub2;
import com.jagex.game.runetek5.config.cursor.CursorTypeList;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub23;
import com.jagex.game.runetek5.deob.GameCanvas;
import com.jagex.game.runetek5.deob.Class7;
import com.jagex.game.runetek5.deob.DequeIterator;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.deob.Class16_Sub4_Sub3;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub6_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9;
import com.jagex.game.runetek5.config.rsinterface.ComponentSurface;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class16_Sub1;
import com.jagex.game.runetek5.deob.Class38;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.cache.node.lru.ProjectileAnimationNode;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub26;
import com.jagex.game.runetek5.js5.request.local.LocalRequester;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class145_Sub1;
import com.jagex.game.runetek5.deob.Class15;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.cache.node.StringNode;
import com.jagex.game.runetek5.deob.Class58;
import com.jagex.game.runetek5.deob.Class27_Sub3;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorFill;
import com.jagex.game.runetek5.deob.Class62;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpCurve;
import com.jagex.game.runetek5.graphics.sprite.SpriteDrawQueue;
import com.jagex.game.runetek5.cache.node.impl.SpriteRenderNode;
import com.jagex.game.runetek5.deob.Class16_Sub3;
import com.jagex.game.runetek5.config.quickchat.QuickchatMessageType;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub22;
import com.jagex.game.runetek5.deob.Class78_Sub1;
@OriginalClass("client!ip")
public final class GlModel extends Model {

	/**
	 * 592HD: what the scene says this model is, for the two vegetation rows.
	 *
	 * <p>{@code HdVegetation.NONE} unless a loc marked it, which is every model
	 * the scene does not submit as ground-layer scenery - and every model in
	 * stock. It lives on the model beside {@code renderState} because that is
	 * where the draw seam already reads per-model state; the toolkit never
	 * guesses the kind from geometry.
	 *
	 * <p>Deliberately not copied by the model's own copies: a copy is a new draw
	 * site and the submitting caller re-marks it, so a stale kind cannot travel
	 * to a model that is no longer that plant.
	 */
	public int hdVegetation;

	@OriginalMember(owner = "client!ip", name = "q", descriptor = "[F")
	private float[] aFloatArray20;

	@OriginalMember(owner = "client!ip", name = "r", descriptor = "[S")
	private short[] aShortArray99;

	@OriginalMember(owner = "client!ip", name = "u", descriptor = "[[I")
	private int[][] anIntArrayArray32;

	@OriginalMember(owner = "client!ip", name = "w", descriptor = "[S")
	private short[] aShortArray100;

	@OriginalMember(owner = "client!ip", name = "x", descriptor = "[S")
	private short[] aShortArray101;

	@OriginalMember(owner = "client!ip", name = "A", descriptor = "[[I")
	private int[][] faceGroups;

	@OriginalMember(owner = "client!ip", name = "B", descriptor = "[I")
	private int[] anIntArray212;

	@OriginalMember(owner = "client!ip", name = "D", descriptor = "[S")
	private short[] aShortArray102;

	@OriginalMember(owner = "client!ip", name = "E", descriptor = "[S")
	private short[] aShortArray103;

	@OriginalMember(owner = "client!ip", name = "F", descriptor = "S")
	private short aShort25;

	@OriginalMember(owner = "client!ip", name = "K", descriptor = "[S")
	private short[] aShortArray104;

	@OriginalMember(owner = "client!ip", name = "R", descriptor = "[S")
	private short[] aShortArray105;

	@OriginalMember(owner = "client!ip", name = "U", descriptor = "S")
	private short aShort26;

	@OriginalMember(owner = "client!ip", name = "Y", descriptor = "[I")
	private int[] anIntArray213;

	@OriginalMember(owner = "client!ip", name = "eb", descriptor = "S")
	private short aShort27;

	@OriginalMember(owner = "client!ip", name = "hb", descriptor = "[S")
	private short[] aShortArray106;

	@OriginalMember(owner = "client!ip", name = "jb", descriptor = "[Lclient!go;")
	private Class85[] aClass85Array1;

	@OriginalMember(owner = "client!ip", name = "lb", descriptor = "S")
	private short lb;

	@OriginalMember(owner = "client!ip", name = "qb", descriptor = "B")
	private byte renderState;

	@OriginalMember(owner = "client!ip", name = "tb", descriptor = "[S")
	private short[] aShortArray107;

	@OriginalMember(owner = "client!ip", name = "ub", descriptor = "Lclient!ll;")
	private ModelNormalsGL mergedNormals;

	@OriginalMember(owner = "client!ip", name = "zb", descriptor = "[S")
	private short[] aShortArray108;

	@OriginalMember(owner = "client!ip", name = "Ab", descriptor = "S")
	private short aShort28;

	@OriginalMember(owner = "client!ip", name = "Cb", descriptor = "I")
	private int preferenceFlags;

	@OriginalMember(owner = "client!ip", name = "Fb", descriptor = "[F")
	private float[] aFloatArray21;

	@OriginalMember(owner = "client!ip", name = "Gb", descriptor = "[B")
	private byte[] aByteArray38;

	@OriginalMember(owner = "client!ip", name = "Hb", descriptor = "I")
	private int functionMask;

	@OriginalMember(owner = "client!ip", name = "Ib", descriptor = "S")
	private short aShort29;

	@OriginalMember(owner = "client!ip", name = "Nb", descriptor = "[Lclient!pf;")
	private EffectiveVertex[] effectiveVertices;

	@OriginalMember(owner = "client!ip", name = "Ob", descriptor = "S")
	private short contrast;

	@OriginalMember(owner = "client!ip", name = "Pb", descriptor = "[Lclient!bu;")
	private EmissiveTriangle[] emissiveTriangles;

	@OriginalMember(owner = "client!ip", name = "Qb", descriptor = "[I")
	private int[] anIntArray214;

	@OriginalMember(owner = "client!ip", name = "Rb", descriptor = "[B")
	private byte[] aByteArray39;

	@OriginalMember(owner = "client!ip", name = "Yb", descriptor = "S")
	private short aShort31;

	@OriginalMember(owner = "client!ip", name = "ac", descriptor = "[[I")
	private int[][] anIntArrayArray34;

	@OriginalMember(owner = "client!ip", name = "bc", descriptor = "[Lclient!j;")
	private NativeBillboardFace[] aClass115Array1;

	@OriginalMember(owner = "client!ip", name = "fc", descriptor = "[S")
	private short[] aShortArray109;

	@OriginalMember(owner = "client!ip", name = "gc", descriptor = "[I")
	private int[] anIntArray215;

	@OriginalMember(owner = "client!ip", name = "ic", descriptor = "I")
	private int billboardCount;

	@OriginalMember(owner = "client!ip", name = "jc", descriptor = "Lclient!bk;")
	private ArrayBufferGL anInterface1_2;

	@OriginalMember(owner = "client!ip", name = "pc", descriptor = "[I")
	private int[] anIntArray216;

	@OriginalMember(owner = "client!ip", name = "tc", descriptor = "S")
	private short ambient;

	@OriginalMember(owner = "client!ip", name = "wc", descriptor = "Lclient!vd;")
	private ElementsBufferGL anInterface11_4;

	@OriginalMember(owner = "client!ip", name = "V", descriptor = "Z")
	private boolean boundsCalculated = false;

	@OriginalMember(owner = "client!ip", name = "p", descriptor = "I")
	private int renderedFaceCount = 0;

	@OriginalMember(owner = "client!ip", name = "Db", descriptor = "I")
	private int numVertices = 0;

	@OriginalMember(owner = "client!ip", name = "Zb", descriptor = "I")
	private int maxDepth = 0;

	@OriginalMember(owner = "client!ip", name = "sb", descriptor = "Z")
	private boolean aBoolean281 = true;

	@OriginalMember(owner = "client!ip", name = "yb", descriptor = "I")
	private int numNormals = 0;

	@OriginalMember(owner = "client!ip", name = "kc", descriptor = "I")
	private int numFaces = 0;

	@OriginalMember(owner = "client!ip", name = "Wb", descriptor = "Lclient!fd;")
	private final GlToolkit glToolkit;

	@OriginalMember(owner = "client!ip", name = "C", descriptor = "Lclient!pc;")
	private Class177 vertexBuffer;

	@OriginalMember(owner = "client!ip", name = "Mb", descriptor = "Lclient!pc;")
	private Class177 aClass177_9;

	@OriginalMember(owner = "client!ip", name = "cc", descriptor = "Lclient!pc;")
	private Class177 normalsBuffer;

	@OriginalMember(owner = "client!ip", name = "L", descriptor = "Lclient!pc;")
	private Class177 colorsBuffer;

	@OriginalMember(owner = "client!ip", name = "J", descriptor = "Lclient!to;")
	private Class230 aClass230_1;

	@OriginalMember(owner = "client!ip", name = "<init>", descriptor = "(Lclient!fd;)V")
	public GlModel(@OriginalArg(0) GlToolkit glToolkit) {
		this.glToolkit = glToolkit;
		this.vertexBuffer = new Class177(null, 5126, 3, 0);
		this.aClass177_9 = new Class177(null, 5126, 2, 0);
		this.normalsBuffer = new Class177(null, 5126, 3, 0);
		this.colorsBuffer = new Class177(null, 5121, 4, 0);
		this.aClass230_1 = new Class230();
	}

	@OriginalMember(owner = "client!ip", name = "<init>", descriptor = "(Lclient!fd;Lclient!jq;IIII)V")
	public GlModel(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) Mesh mesh, @OriginalArg(2) int arg2, @OriginalArg(3) int ambient, @OriginalArg(4) int contrast, @OriginalArg(5) int arg5) {
		this.preferenceFlags = arg5;
		this.glToolkit = glToolkit;
		this.functionMask = arg2;
		if (LocalRequester.method5437(arg5, arg2)) {
			this.vertexBuffer = new Class177(null, 5126, 3, 0);
		}
		if (Class7.method87(arg2, arg5)) {
			this.aClass177_9 = new Class177(null, 5126, 2, 0);
		}
		if (Class4_Sub10.method2002(arg2, arg5)) {
			this.normalsBuffer = new Class177(null, 5126, 3, 0);
		}
		if (Class4_Sub9.method760(arg5, arg2)) {
			this.colorsBuffer = new Class177(null, 5121, 4, 0);
		}
		if (NamedFileReference.method5172(arg5, arg2)) {
			this.aClass230_1 = new Class230();
		}
		@Pc(102) unpack_Sub1 local102 = glToolkit.materialProvider;
		@Pc(106) int[] local106 = new int[mesh.anInt3686];
		this.anIntArray214 = new int[mesh.maxDepth + 1];
		for (@Pc(115) int local115 = 0; local115 < mesh.anInt3686; local115++) {
			if ((mesh.aByteArray43 == null || mesh.aByteArray43[local115] != 2) && (mesh.aShortArray125 == null || mesh.aShortArray125[local115] == -1 || !local102.getMetrics(mesh.aShortArray125[local115] & 0xFFFF).aBoolean642)) {
				local106[this.numFaces++] = local115;
				this.anIntArray214[mesh.aShortArray120[local115]]++;
				this.anIntArray214[mesh.aShortArray119[local115]]++;
				this.anIntArray214[mesh.aShortArray117[local115]]++;
			}
		}
		this.renderedFaceCount = this.numFaces;
		@Pc(203) long[] local203 = new long[this.numFaces];
		@Pc(212) boolean local212 = (this.functionMask & 0x100) != 0;
		@Pc(224) int local224;
		@Pc(235) int local235;
		@Pc(367) int local367;
		label488: for (@Pc(214) int local214 = 0; local214 < this.numFaces; local214++) {
			@Pc(220) int local220 = local106[local214];
			@Pc(222) MaterialType local222 = null;
			local224 = 0;
			@Pc(226) byte local226 = 0;
			@Pc(228) byte local228 = 0;
			@Pc(230) byte local230 = 0;
			if (mesh.billboards != null) {
				for (local235 = 0; local235 < mesh.billboards.length; local235++) {
					@Pc(242) MeshBillboard local242 = mesh.billboards[local235];
					if (local220 == local242.anInt4521) {
						@Pc(255) BillboardType local255 = Class259.method5501(local242.anInt4526);
						if (local255.hasUid) {
							local203[local214] = Long.MAX_VALUE;
							this.renderedFaceCount--;
							continue label488;
						}
					}
				}
			}
			@Pc(282) short local282 = -1;
			if (mesh.aShortArray125 != null) {
				local282 = mesh.aShortArray125[local220];
				if (local282 != -1) {
					local222 = local102.getMetrics(local282 & 0xFFFF);
					local228 = local222.aByte89;
					local230 = local222.aByte88;
				}
			}
			@Pc(323) boolean local323 = mesh.aByteArray44 != null && mesh.aByteArray44[local220] != 0 || local222 != null && !local222.aBoolean640;
			if ((local212 || local323) && mesh.aByteArray42 != null) {
				local224 += mesh.aByteArray42[local220] << 17;
			}
			if (local323) {
				local224 += 65536;
			}
			local224 += (local228 & 0xFF) << 8;
			local224 += local230 & 0xFF;
			local367 = local226 + ((local282 & 0xFFFF) << 16);
			@Pc(373) int local373 = local367 + (local214 & 0xFFFF);
			local203[local214] = ((long) local224 << 32) + ((long) local373);
		}
		Static410.method5270(local106, local203);
		this.maxDepth = mesh.maxDepth;
		this.anIntArray212 = mesh.anIntArray244;
		this.anIntArray216 = mesh.anIntArray242;
		this.numVertices = mesh.anInt3668;
		this.anIntArray213 = mesh.anIntArray251;
		this.aShortArray99 = mesh.aShortArray122;
		this.emissiveTriangles = mesh.particleEmitters;
		this.effectiveVertices = mesh.aClass179Array3;
		@Pc(429) Class196[] local429 = new Class196[this.maxDepth];
		@Pc(449) int local449;
		@Pc(463) int local463;
		if (mesh.billboards != null) {
			this.billboardCount = mesh.billboards.length;
			this.aClass115Array1 = new NativeBillboardFace[this.billboardCount];
			this.aClass85Array1 = new Class85[this.billboardCount];
			for (local449 = 0; local449 < this.billboardCount; local449++) {
				@Pc(456) MeshBillboard local456 = mesh.billboards[local449];
				@Pc(461) BillboardType local461 = Class259.method5501(local456.anInt4526);
				local463 = -1;
				for (@Pc(465) int local465 = 0; local465 < this.numFaces; local465++) {
					if (local106[local465] == local456.anInt4521) {
						local463 = local465;
						break;
					}
				}
				if (local463 == -1) {
					throw new RuntimeException();
				}
				local235 = Class9_Sub3.anIntArray115[mesh.aShortArray123[local456.anInt4521] & 0xFFFF] & 0xFFFFFF;
				local235 |= 255 - (mesh.aByteArray44 == null ? 0 : mesh.aByteArray44[local456.anInt4521]) << 24;
				this.aClass115Array1[local449] = new NativeBillboardFace(local463, mesh.aShortArray120[local456.anInt4521], mesh.aShortArray119[local456.anInt4521], mesh.aShortArray117[local456.anInt4521], local461.anInt3538, local461.anInt3543, local461.anInt3536, local461.anInt3535, local461.anInt3542, local461.hasUid, local461.stationary, local456.anInt4528);
				this.aClass85Array1[local449] = new Class85(local235);
			}
		}
		local449 = this.numFaces * 3;
		this.aFloatArray21 = new float[local449];
		this.aByteArray38 = new byte[this.numFaces];
		this.aFloatArray20 = new float[local449];
		this.aShortArray107 = new short[this.numFaces];
		this.ambient = (short) ambient;
		this.aShortArray109 = new short[local449];
		this.aShortArray101 = new short[local449];
		PingWorker.aLongArray9 = new long[local449];
		this.aShortArray102 = new short[local449];
		if (mesh.aShortArray121 != null) {
			this.aShortArray104 = new short[this.numFaces];
		}
		this.aShortArray103 = new short[local449];
		this.aShortArray108 = new short[this.numFaces];
		this.contrast = (short) contrast;
		this.aShortArray105 = new short[this.numFaces];
		this.aShortArray106 = new short[this.numFaces];
		this.aShortArray100 = new short[this.numFaces];
		this.aByteArray39 = new byte[local449];
		local224 = 0;
		for (local367 = 0; local367 < mesh.maxDepth; local367++) {
			local463 = this.anIntArray214[local367];
			this.anIntArray214[local367] = local224;
			local429[local367] = new Class196();
			local224 += local463;
		}
		this.anIntArray214[mesh.maxDepth] = local224;
		@Pc(701) int[] local701 = null;
		@Pc(703) int[] local703 = null;
		@Pc(705) int[] local705 = null;
		@Pc(707) float[][] local707 = null;
		@Pc(733) int local733;
		@Pc(768) int local768;
		@Pc(774) int local774;
		@Pc(788) int local788;
		@Pc(790) int local790;
		@Pc(824) int local824;
		@Pc(829) int local829;
		@Pc(991) float local991;
		@Pc(987) float local987;
		@Pc(993) float local993;
		if (mesh.aByteArray46 != null) {
			@Pc(713) int local713 = mesh.anInt3689;
			@Pc(716) int[] local716 = new int[local713];
			@Pc(719) int[] local719 = new int[local713];
			@Pc(722) int[] local722 = new int[local713];
			@Pc(725) int[] local725 = new int[local713];
			@Pc(728) int[] local728 = new int[local713];
			@Pc(731) int[] local731 = new int[local713];
			for (local733 = 0; local733 < local713; local733++) {
				local716[local733] = Integer.MAX_VALUE;
				local719[local733] = -2147483647;
				local722[local733] = Integer.MAX_VALUE;
				local725[local733] = -2147483647;
				local728[local733] = Integer.MAX_VALUE;
				local731[local733] = -2147483647;
			}
			local705 = new int[local713];
			for (local768 = 0; local768 < this.numFaces; local768++) {
				local774 = local106[local768];
				if (mesh.aByteArray46[local774] != -1) {
					local788 = mesh.aByteArray46[local774] & 0xFF;
					for (local790 = 0; local790 < 3; local790++) {
						@Pc(802) short local802;
						if (local790 == 0) {
							local802 = mesh.aShortArray120[local774];
						} else if (local790 == 1) {
							local802 = mesh.aShortArray119[local774];
						} else {
							local802 = mesh.aShortArray117[local774];
						}
						local824 = mesh.anIntArray244[local802];
						local829 = mesh.anIntArray242[local802];
						@Pc(834) int local834 = mesh.anIntArray251[local802];
						if (local716[local788] > local824) {
							local716[local788] = local824;
						}
						if (local824 > local719[local788]) {
							local719[local788] = local824;
						}
						if (local829 < local722[local788]) {
							local722[local788] = local829;
						}
						if (local829 > local725[local788]) {
							local725[local788] = local829;
						}
						if (local728[local788] > local834) {
							local728[local788] = local834;
						}
						if (local731[local788] < local834) {
							local731[local788] = local834;
						}
					}
				}
			}
			local701 = new int[local713];
			local703 = new int[local713];
			local707 = new float[local713][];
			for (local774 = 0; local774 < local713; local774++) {
				@Pc(931) byte local931 = mesh.aByteArray47[local774];
				if (local931 > 0) {
					local701[local774] = (local716[local774] + local719[local774]) / 2;
					local703[local774] = (local722[local774] + local725[local774]) / 2;
					local705[local774] = (local731[local774] + local728[local774]) / 2;
					if (local931 == 1) {
						local829 = mesh.anIntArray249[local774];
						local987 = 64.0F / (float) mesh.anIntArray248[local774];
						if (local829 == 0) {
							local991 = 1.0F;
							local993 = 1.0F;
						} else if (local829 > 0) {
							local991 = 1.0F;
							local993 = (float) local829 / 1024.0F;
						} else {
							local993 = 1.0F;
							local991 = (float) -local829 / 1024.0F;
						}
					} else if (local931 == 2) {
						local993 = 64.0F / (float) mesh.anIntArray250[local774];
						local991 = 64.0F / (float) mesh.anIntArray249[local774];
						local987 = 64.0F / (float) mesh.anIntArray248[local774];
					} else {
						local993 = (float) mesh.anIntArray250[local774] / 1024.0F;
						local987 = (float) mesh.anIntArray248[local774] / 1024.0F;
						local991 = (float) mesh.anIntArray249[local774] / 1024.0F;
					}
					local707[local774] = Static449.method5652(local991, mesh.aByteArray41[local774] & 0xFF, mesh.aShortArray116[local774], mesh.aShortArray124[local774], mesh.aShortArray118[local774], local987, local993);
				}
			}
		}
		@Pc(1107) Class92[] local1107 = new Class92[mesh.anInt3686];
		@Pc(1126) short local1126;
		@Pc(1136) int local1136;
		@Pc(1147) int local1147;
		@Pc(1209) int local1209;
		for (@Pc(1109) int local1109 = 0; local1109 < mesh.anInt3686; local1109++) {
			@Pc(1116) short local1116 = mesh.aShortArray120[local1109];
			@Pc(1121) short local1121 = mesh.aShortArray119[local1109];
			local1126 = mesh.aShortArray117[local1109];
			local1136 = this.anIntArray212[local1121] - this.anIntArray212[local1116];
			local1147 = this.anIntArray216[local1121] - this.anIntArray216[local1116];
			local733 = this.anIntArray213[local1121] - this.anIntArray213[local1116];
			local768 = this.anIntArray212[local1126] - this.anIntArray212[local1116];
			local774 = this.anIntArray216[local1126] - this.anIntArray216[local1116];
			local788 = this.anIntArray213[local1126] - this.anIntArray213[local1116];
			local790 = local788 * local1147 - local774 * local733;
			local1209 = local768 * local733 - local788 * local1136;
			for (local824 = local1136 * local774 - local768 * local1147; local790 > 8192 || local1209 > 8192 || local824 > 8192 || local790 < -8192 || local1209 < -8192 || local824 < -8192; local824 >>= 0x1) {
				local1209 >>= 0x1;
				local790 >>= 0x1;
			}
			local829 = (int) Math.sqrt((double) (local824 * local824 + local1209 * local1209 + local790 * local790));
			if (local829 <= 0) {
				local829 = 1;
			}
			local790 = local790 * 256 / local829;
			local1209 = local1209 * 256 / local829;
			local824 = local824 * 256 / local829;
			@Pc(1301) byte local1301 = mesh.aByteArray43 == null ? 0 : mesh.aByteArray43[local1109];
			if (local1301 == 0) {
				@Pc(1334) Class196 local1334 = local429[local1116];
				local1334.anInt5690++;
				local1334.anInt5687 += local1209;
				local1334.anInt5683 += local790;
				local1334.anInt5680 += local824;
				@Pc(1362) Class196 local1362 = local429[local1121];
				local1362.anInt5683 += local790;
				local1362.anInt5687 += local1209;
				local1362.anInt5690++;
				local1362.anInt5680 += local824;
				@Pc(1390) Class196 local1390 = local429[local1126];
				local1390.anInt5683 += local790;
				local1390.anInt5680 += local824;
				local1390.anInt5687 += local1209;
				local1390.anInt5690++;
			} else if (local1301 == 1) {
				@Pc(1319) Class92 local1319 = local1107[local1109] = new Class92();
				local1319.anInt2882 = local1209;
				local1319.anInt2883 = local824;
				local1319.anInt2885 = local790;
			}
		}
		@Pc(1427) int local1427;
		for (@Pc(1421) int local1421 = 0; local1421 < this.numFaces; local1421++) {
			local1427 = local106[local1421];
			@Pc(1434) int local1434 = mesh.aShortArray123[local1427] & 0xFFFF;
			@Pc(1439) short local1439;
			if (mesh.aShortArray125 == null) {
				local1439 = -1;
			} else {
				local1439 = mesh.aShortArray125[local1427];
			}
			if (mesh.aByteArray46 == null) {
				local1147 = -1;
			} else {
				local1147 = mesh.aByteArray46[local1427];
			}
			if (mesh.aByteArray44 == null) {
				local733 = 0;
			} else {
				local733 = mesh.aByteArray44[local1427] & 0xFF;
			}
			@Pc(1474) float local1474 = 0.0F;
			@Pc(1476) float local1476 = 0.0F;
			@Pc(1478) float local1478 = 0.0F;
			local991 = 0.0F;
			local987 = 0.0F;
			local993 = 0.0F;
			@Pc(1486) byte local1486 = 0;
			@Pc(1488) byte local1488 = 0;
			@Pc(1490) int local1490 = 0;
			@Pc(1523) byte local1523;
			@Pc(1540) short local1540;
			@Pc(2280) short local2280;
			@Pc(2285) short local2285;
			if (local1439 != -1) {
				if (local1147 == -1) {
					local1486 = 1;
					local991 = 1.0F;
					local993 = 0.0F;
					local1476 = 1.0F;
					local1474 = 0.0F;
					local1488 = 2;
					local987 = 0.0F;
					local1478 = 1.0F;
				} else {
					local1147 &= 0xFF;
					local1523 = mesh.aByteArray47[local1147];
					@Pc(1530) short local1530;
					@Pc(1535) short local1535;
					@Pc(1569) float local1569;
					@Pc(1580) float local1580;
					@Pc(1659) float local1659;
					@Pc(2086) float local2086;
					@Pc(2094) float local2094;
					@Pc(2102) float local2102;
					@Pc(2125) float local2125;
					@Pc(2148) float local2148;
					@Pc(2171) float local2171;
					if (local1523 == 0) {
						local1530 = mesh.aShortArray120[local1427];
						local1535 = mesh.aShortArray119[local1427];
						local1540 = mesh.aShortArray117[local1427];
						local2280 = mesh.aShortArray118[local1147];
						local2285 = mesh.aShortArray116[local1147];
						@Pc(2290) short local2290 = mesh.aShortArray124[local1147];
						@Pc(2296) float local2296 = (float) mesh.anIntArray244[local2280];
						@Pc(2302) float local2302 = (float) mesh.anIntArray242[local2280];
						local1569 = mesh.anIntArray251[local2280];
						local1580 = (float) mesh.anIntArray244[local2285] - local2296;
						local1659 = (float) mesh.anIntArray242[local2285] - local2302;
						@Pc(2333) float local2333 = (float) mesh.anIntArray251[local2285] - local1569;
						@Pc(2341) float local2341 = (float) mesh.anIntArray244[local2290] - local2296;
						@Pc(2349) float local2349 = (float) mesh.anIntArray242[local2290] - local2302;
						@Pc(2357) float local2357 = (float) mesh.anIntArray251[local2290] - local1569;
						@Pc(2365) float local2365 = (float) mesh.anIntArray244[local1530] - local2296;
						@Pc(2373) float local2373 = (float) mesh.anIntArray242[local1530] - local2302;
						@Pc(2381) float local2381 = (float) mesh.anIntArray251[local1530] - local1569;
						@Pc(2389) float local2389 = (float) mesh.anIntArray244[local1535] - local2296;
						@Pc(2398) float local2398 = (float) mesh.anIntArray242[local1535] - local2302;
						local2086 = (float) mesh.anIntArray251[local1535] - local1569;
						local2094 = (float) mesh.anIntArray244[local1540] - local2296;
						local2102 = (float) mesh.anIntArray242[local1540] - local2302;
						local2125 = (float) mesh.anIntArray251[local1540] - local1569;
						local2148 = local1659 * local2357 - local2349 * local2333;
						local2171 = local2341 * local2333 - local2357 * local1580;
						@Pc(2460) float local2460 = local1580 * local2349 - local1659 * local2341;
						@Pc(2469) float local2469 = local2460 * local2349 - local2171 * local2357;
						@Pc(2477) float local2477 = local2148 * local2357 - local2460 * local2341;
						@Pc(2486) float local2486 = local2171 * local2341 - local2349 * local2148;
						@Pc(2500) float local2500 = 1.0F / (local2469 * local1580 + local2477 * local1659 + local2486 * local2333);
						local1478 = local2500 * (local2477 * local2398 + local2469 * local2389 + local2086 * local2486);
						local1474 = (local2477 * local2373 + local2365 * local2469 + local2381 * local2486) * local2500;
						local987 = local2500 * (local2125 * local2486 + local2477 * local2102 + local2094 * local2469);
						@Pc(2551) float local2551 = local2171 * local1580 - local2148 * local1659;
						@Pc(2559) float local2559 = local2460 * local1659 - local2171 * local2333;
						@Pc(2567) float local2567 = local2148 * local2333 - local2460 * local1580;
						@Pc(2581) float local2581 = 1.0F / (local2551 * local2357 + local2567 * local2349 + local2341 * local2559);
						local991 = (local2567 * local2398 + local2389 * local2559 + local2551 * local2086) * local2581;
						local1476 = (local2551 * local2381 + local2567 * local2373 + local2365 * local2559) * local2581;
						local993 = local2581 * (local2551 * local2125 + local2102 * local2567 + local2559 * local2094);
					} else {
						local1530 = mesh.aShortArray120[local1427];
						local1535 = mesh.aShortArray119[local1427];
						local1540 = mesh.aShortArray117[local1427];
						@Pc(1544) int local1544 = local701[local1147];
						@Pc(1548) int local1548 = local703[local1147];
						@Pc(1552) int local1552 = local705[local1147];
						@Pc(1556) float[] local1556 = local707[local1147];
						@Pc(1561) byte local1561 = mesh.aByteArray45[local1147];
						local1569 = (float) mesh.anIntArray252[local1147] / 256.0F;
						if (local1523 == 1) {
							local1580 = (float) mesh.anIntArray250[local1147] / 1024.0F;
							GameShell.method875(mesh.anIntArray251[local1530], local1569, local1548, mesh.anIntArray242[local1530], local1556, mesh.anIntArray244[local1530], local1544, local1580, local1561, local1552);
							local1476 = Class4_Sub1_Sub9.aFloat102;
							local1474 = VertexDeclarationComponent.aFloat211;
							GameShell.method875(mesh.anIntArray251[local1535], local1569, local1548, mesh.anIntArray242[local1535], local1556, mesh.anIntArray244[local1535], local1544, local1580, local1561, local1552);
							local1478 = VertexDeclarationComponent.aFloat211;
							local991 = Class4_Sub1_Sub9.aFloat102;
							GameShell.method875(mesh.anIntArray251[local1540], local1569, local1548, mesh.anIntArray242[local1540], local1556, mesh.anIntArray244[local1540], local1544, local1580, local1561, local1552);
							local987 = VertexDeclarationComponent.aFloat211;
							local993 = Class4_Sub1_Sub9.aFloat102;
							local1659 = local1580 / 2.0F;
							if ((local1561 & 0x1) == 0) {
								if (local1478 - local1474 > local1659) {
									local1478 -= local1580;
									local1486 = 1;
								} else if (local1659 < local1474 - local1478) {
									local1486 = 2;
									local1478 += local1580;
								}
								if (local1659 < local987 - local1474) {
									local1488 = 1;
									local987 -= local1580;
								} else if (local1659 < local1474 - local987) {
									local987 += local1580;
									local1488 = 2;
								}
							} else {
								if (local1659 < local991 - local1476) {
									local991 -= local1580;
									local1486 = 1;
								} else if (local1476 - local991 > local1659) {
									local991 += local1580;
									local1486 = 2;
								}
								if (local993 - local1476 > local1659) {
									local1488 = 1;
									local993 -= local1580;
								} else if (local1476 - local993 > local1659) {
									local1488 = 2;
									local993 += local1580;
								}
							}
						} else if (local1523 == 2) {
							local1580 = (float) mesh.anIntArray243[local1147] / 256.0F;
							local1659 = (float) mesh.anIntArray247[local1147] / 256.0F;
							@Pc(1997) int local1997 = mesh.anIntArray244[local1535] - mesh.anIntArray244[local1530];
							@Pc(2008) int local2008 = mesh.anIntArray242[local1535] - mesh.anIntArray242[local1530];
							@Pc(2019) int local2019 = mesh.anIntArray251[local1535] - mesh.anIntArray251[local1530];
							@Pc(2030) int local2030 = mesh.anIntArray244[local1540] - mesh.anIntArray244[local1530];
							@Pc(2041) int local2041 = mesh.anIntArray242[local1540] - mesh.anIntArray242[local1530];
							@Pc(2052) int local2052 = mesh.anIntArray251[local1540] - mesh.anIntArray251[local1530];
							@Pc(2060) int local2060 = local2052 * local2008 - local2041 * local2019;
							@Pc(2069) int local2069 = local2030 * local2019 - local2052 * local1997;
							@Pc(2078) int local2078 = local1997 * local2041 - local2030 * local2008;
							local2086 = 64.0F / (float) mesh.anIntArray249[local1147];
							local2094 = 64.0F / (float) mesh.anIntArray248[local1147];
							local2102 = 64.0F / (float) mesh.anIntArray250[local1147];
							local2125 = (local1556[1] * (float) local2069 + (float) local2060 * local1556[0] + (float) local2078 * local1556[2]) / local2086;
							local2148 = (local1556[5] * (float) local2078 + local1556[4] * (float) local2069 + local1556[3] * (float) local2060) / local2094;
							local2171 = (local1556[7] * (float) local2069 + (float) local2060 * local1556[6] + (float) local2078 * local1556[8]) / local2102;
							local1490 = Static441.method5567(local2125, local2171, local2148);
							GLBillowTextureData.method4413(local1556, local1580, local1569, mesh.anIntArray244[local1530], local1659, mesh.anIntArray242[local1530], local1552, local1490, local1561, local1544, local1548, mesh.anIntArray251[local1530]);
							local1474 = Class27_Sub3.aFloat127;
							local1476 = Js5GroupLoader.aFloat180;
							GLBillowTextureData.method4413(local1556, local1580, local1569, mesh.anIntArray244[local1535], local1659, mesh.anIntArray242[local1535], local1552, local1490, local1561, local1544, local1548, mesh.anIntArray251[local1535]);
							local1478 = Class27_Sub3.aFloat127;
							local991 = Js5GroupLoader.aFloat180;
							GLBillowTextureData.method4413(local1556, local1580, local1569, mesh.anIntArray244[local1540], local1659, mesh.anIntArray242[local1540], local1552, local1490, local1561, local1544, local1548, mesh.anIntArray251[local1540]);
							local987 = Class27_Sub3.aFloat127;
							local993 = Js5GroupLoader.aFloat180;
						} else if (local1523 == 3) {
							TextureOpColorFill.method619(mesh.anIntArray251[local1530], local1569, mesh.anIntArray244[local1530], local1544, mesh.anIntArray242[local1530], local1556, local1561, local1548, local1552);
							local1476 = Class16_Sub2.aFloat143;
							local1474 = Class62.aFloat49;
							TextureOpColorFill.method619(mesh.anIntArray251[local1535], local1569, mesh.anIntArray244[local1535], local1544, mesh.anIntArray242[local1535], local1556, local1561, local1548, local1552);
							local1478 = Class62.aFloat49;
							local991 = Class16_Sub2.aFloat143;
							TextureOpColorFill.method619(mesh.anIntArray251[local1540], local1569, mesh.anIntArray244[local1540], local1544, mesh.anIntArray242[local1540], local1556, local1561, local1548, local1552);
							local987 = Class62.aFloat49;
							local993 = Class16_Sub2.aFloat143;
							if ((local1561 & 0x1) == 0) {
								if (local987 - local1474 > 0.5F) {
									local987--;
									local1488 = 1;
								} else if (local1474 - local987 > 0.5F) {
									local1488 = 2;
									local987++;
								}
								if (local1478 - local1474 > 0.5F) {
									local1478--;
									local1486 = 1;
								} else if (local1474 - local1478 > 0.5F) {
									local1486 = 2;
									local1478++;
								}
							} else {
								if (local991 - local1476 > 0.5F) {
									local991--;
									local1486 = 1;
								} else if (local1476 - local991 > 0.5F) {
									local991++;
									local1486 = 2;
								}
								if (local993 - local1476 > 0.5F) {
									local993--;
									local1488 = 1;
								} else if (local1476 - local993 > 0.5F) {
									local993++;
									local1488 = 2;
								}
							}
						}
					}
				}
			}
			if (mesh.aByteArray43 == null) {
				local1523 = 0;
			} else {
				local1523 = mesh.aByteArray43[local1427];
			}
			if (local1523 == 0) {
				@Pc(2785) long local2785 = (long) (local1147 << 2) + ((long) (local1434 << 8) + (long) (local1490 << 24) + (long) local733 << 32);
				local1540 = mesh.aShortArray120[local1427];
				local2280 = mesh.aShortArray119[local1427];
				local2285 = mesh.aShortArray117[local1427];
				@Pc(2804) Class196 local2804 = local429[local1540];
				this.aShortArray108[local1421] = this.method2803(local2804.anInt5680, local2785, local1476, local1474, local2804.anInt5690, local2804.anInt5687, local1540, mesh, local2804.anInt5683);
				@Pc(2828) Class196 local2828 = local429[local2280];
				this.aShortArray105[local1421] = this.method2803(local2828.anInt5680, (long) local1486 + local2785, local991, local1478, local2828.anInt5690, local2828.anInt5687, local2280, mesh, local2828.anInt5683);
				@Pc(2855) Class196 local2855 = local429[local2285];
				this.aShortArray106[local1421] = this.method2803(local2855.anInt5680, (long) local1488 + local2785, local993, local987, local2855.anInt5690, local2855.anInt5687, local2285, mesh, local2855.anInt5683);
			} else if (local1523 == 1) {
				@Pc(2647) Class92 local2647 = local1107[local1427];
				@Pc(2691) long local2691 = ((long) (local1490 << 24) + (long) (local1434 << 8) + (long) local733 << 32) + (long) ((local2647.anInt2882 + 256 << 12) + (local2647.anInt2885 > 0 ? 1024 : 2048) + (local1147 << 2) + (local2647.anInt2883 + 256 << 22));
				this.aShortArray108[local1421] = this.method2803(local2647.anInt2883, local2691, local1476, local1474, 0, local2647.anInt2882, mesh.aShortArray120[local1427], mesh, local2647.anInt2885);
				this.aShortArray105[local1421] = this.method2803(local2647.anInt2883, local2691 + (long) local1486, local991, local1478, 0, local2647.anInt2882, mesh.aShortArray119[local1427], mesh, local2647.anInt2885);
				this.aShortArray106[local1421] = this.method2803(local2647.anInt2883, local2691 + (long) local1488, local993, local987, 0, local2647.anInt2882, mesh.aShortArray117[local1427], mesh, local2647.anInt2885);
			}
			if (mesh.aShortArray125 == null) {
				this.aShortArray107[local1421] = -1;
			} else {
				this.aShortArray107[local1421] = mesh.aShortArray125[local1427];
			}
			if (mesh.aByteArray44 != null) {
				this.aByteArray38[local1421] = mesh.aByteArray44[local1427];
			}
			if (mesh.aShortArray121 != null) {
				this.aShortArray104[local1421] = mesh.aShortArray121[local1427];
			}
			this.aShortArray100[local1421] = mesh.aShortArray123[local1427];
		}
		local1427 = 0;
		local1126 = -10000;
		for (local1136 = 0; local1136 < this.renderedFaceCount; local1136++) {
			@Pc(2948) short local2948 = this.aShortArray107[local1136];
			if (local1126 != local2948) {
				local1427++;
				local1126 = local2948;
			}
		}
		this.anIntArray215 = new int[local1427 + 1];
		local1126 = -10000;
		local1427 = 0;
		for (local1147 = 0; local1147 < this.renderedFaceCount; local1147++) {
			@Pc(2986) short local2986 = this.aShortArray107[local1147];
			if (local2986 != local1126) {
				local1126 = local2986;
				this.anIntArray215[local1427++] = local1147;
			}
		}
		this.anIntArray215[local1427] = this.renderedFaceCount;
		PingWorker.aLongArray9 = null;
		this.aShortArray103 = Class4_Sub1_Sub6_Sub2.method3347(this.aShortArray103, this.numNormals);
		this.aShortArray101 = Class4_Sub1_Sub6_Sub2.method3347(this.aShortArray101, this.numNormals);
		this.aShortArray102 = Class4_Sub1_Sub6_Sub2.method3347(this.aShortArray102, this.numNormals);
		this.aByteArray39 = ComponentSurface.method5626(this.aByteArray39, this.numNormals);
		this.aFloatArray21 = BillowNoiseGenerator.method4410(this.numNormals, this.aFloatArray21);
		this.aFloatArray20 = BillowNoiseGenerator.method4410(this.numNormals, this.aFloatArray20);
		if (mesh.anIntArray246 != null && TextureOpCurve.method688(this.preferenceFlags, arg2)) {
			this.anIntArrayArray34 = mesh.method3050();
		}
		if (mesh.billboards != null && Class16_Sub1.method5343(arg2, this.preferenceFlags)) {
			this.anIntArrayArray32 = mesh.method3037();
		}
		if (mesh.anIntArray245 != null && Class16_Sub3.method726(this.preferenceFlags, arg2)) {
			local733 = 0;
			@Pc(3100) int[] local3100 = new int[256];
			for (local774 = 0; local774 < this.numFaces; local774++) {
				local788 = mesh.anIntArray245[local106[local774]];
				if (local788 >= 0) {
					@Pc(3117) int local3117 = local3100[local788]++;
					if (local788 > local733) {
						local733 = local788;
					}
				}
			}
			this.faceGroups = new int[local733 + 1][];
			for (local788 = 0; local788 <= local733; local788++) {
				this.faceGroups[local788] = new int[local3100[local788]];
				local3100[local788] = 0;
			}
			for (local790 = 0; local790 < this.numFaces; local790++) {
				local1209 = mesh.anIntArray245[local106[local790]];
				if (local1209 >= 0) {
					this.faceGroups[local1209][local3100[local1209]++] = local790;
				}
			}
		}
	}

	@OriginalMember(owner = "client!ip", name = "XA", descriptor = "(I[IIIIIZ)V")
	@Override
	protected void applyTransformUnmasked(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int z, @OriginalArg(5) int rotation, @OriginalArg(6) boolean lights) {
		@Pc(8) int local8 = labels.length;
		@Pc(27) int local27;
		@Pc(35) int local35;
		@Pc(59) int local59;
		@Pc(25) int local25;
		@Pc(17) int local17;
		@Pc(21) int local21;
		@Pc(41) int local41;
		@Pc(53) int local53;
		@Pc(51) int[] local51;
		if (type == 0) {
			local17 = y << 4;
			local21 = z << 4;
			local25 = x << 4;
			local27 = 0;
			Class4_Sub6_Sub19.anInt3954 = 0;
			Class6_Sub2.anInt255 = 0;
			BillboardType.anInt3541 = 0;
			for (local35 = 0; local35 < local8; local35++) {
				local41 = labels[local35];
				if (this.anIntArrayArray34.length > local41) {
					local51 = this.anIntArrayArray34[local41];
					for (local53 = 0; local53 < local51.length; local53++) {
						local59 = local51[local53];
						Class6_Sub2.anInt255 += this.anIntArray212[local59];
						BillboardType.anInt3541 += this.anIntArray216[local59];
						Class4_Sub6_Sub19.anInt3954 += this.anIntArray213[local59];
						local27++;
					}
				}
			}
			if (local27 > 0) {
				Class4_Sub6_Sub19.anInt3954 = Class4_Sub6_Sub19.anInt3954 / local27 + local21;
				Class6_Sub2.anInt255 = local25 + Class6_Sub2.anInt255 / local27;
				BillboardType.anInt3541 = BillboardType.anInt3541 / local27 + local17;
			} else {
				Class4_Sub6_Sub19.anInt3954 = local21;
				BillboardType.anInt3541 = local17;
				Class6_Sub2.anInt255 = local25;
			}
			return;
		}
		@Pc(159) int[] local159;
		@Pc(161) int local161;
		if (type == 1) {
			local25 = x << 4;
			local17 = y << 4;
			local21 = z << 4;
			for (local27 = 0; local27 < local8; local27++) {
				local35 = labels[local27];
				if (local35 < this.anIntArrayArray34.length) {
					local159 = this.anIntArrayArray34[local35];
					for (local161 = 0; local161 < local159.length; local161++) {
						local53 = local159[local161];
						this.anIntArray212[local53] += local25;
						this.anIntArray216[local53] += local17;
						this.anIntArray213[local53] += local21;
					}
				}
			}
			return;
		}
		@Pc(282) int local282;
		@Pc(301) int local301;
		@Pc(757) int local757;
		if (type == 2) {
			for (local27 = 0; local27 < local8; local27++) {
				local35 = labels[local27];
				if (this.anIntArrayArray34.length > local35) {
					local159 = this.anIntArrayArray34[local35];
					if ((rotation & 0x1) == 0) {
						for (local161 = 0; local161 < local159.length; local161++) {
							local53 = local159[local161];
							this.anIntArray212[local53] -= Class6_Sub2.anInt255;
							this.anIntArray216[local53] -= BillboardType.anInt3541;
							this.anIntArray213[local53] -= Class4_Sub6_Sub19.anInt3954;
							if (z != 0) {
								local59 = Class50_Sub1.anIntArray225[z];
								local282 = Class50_Sub1.anIntArray224[z];
								local301 = local282 * this.anIntArray212[local53] + this.anIntArray216[local53] * local59 + 32767 >> 15;
								this.anIntArray216[local53] = this.anIntArray216[local53] * local282 + 32767 - local59 * this.anIntArray212[local53] >> 15;
								this.anIntArray212[local53] = local301;
							}
							if (x != 0) {
								local59 = Class50_Sub1.anIntArray225[x];
								local282 = Class50_Sub1.anIntArray224[x];
								local301 = this.anIntArray216[local53] * local282 + 32767 - local59 * this.anIntArray213[local53] >> 15;
								this.anIntArray213[local53] = this.anIntArray213[local53] * local282 + this.anIntArray216[local53] * local59 + 32767 >> 15;
								this.anIntArray216[local53] = local301;
							}
							if (y != 0) {
								local59 = Class50_Sub1.anIntArray225[y];
								local282 = Class50_Sub1.anIntArray224[y];
								local301 = this.anIntArray212[local53] * local282 + local59 * this.anIntArray213[local53] + 32767 >> 15;
								this.anIntArray213[local53] = local282 * this.anIntArray213[local53] + 32767 - this.anIntArray212[local53] * local59 >> 15;
								this.anIntArray212[local53] = local301;
							}
							this.anIntArray212[local53] += Class6_Sub2.anInt255;
							this.anIntArray216[local53] += BillboardType.anInt3541;
							this.anIntArray213[local53] += Class4_Sub6_Sub19.anInt3954;
						}
					} else {
						for (local161 = 0; local161 < local159.length; local161++) {
							local53 = local159[local161];
							this.anIntArray212[local53] -= Class6_Sub2.anInt255;
							this.anIntArray216[local53] -= BillboardType.anInt3541;
							this.anIntArray213[local53] -= Class4_Sub6_Sub19.anInt3954;
							if (x != 0) {
								local59 = Class50_Sub1.anIntArray225[x];
								local282 = Class50_Sub1.anIntArray224[x];
								local301 = local282 * this.anIntArray216[local53] + 32767 - local59 * this.anIntArray213[local53] >> 15;
								this.anIntArray213[local53] = local282 * this.anIntArray213[local53] + local59 * this.anIntArray216[local53] + 32767 >> 15;
								this.anIntArray216[local53] = local301;
							}
							if (z != 0) {
								local59 = Class50_Sub1.anIntArray225[z];
								local282 = Class50_Sub1.anIntArray224[z];
								local301 = local59 * this.anIntArray216[local53] + this.anIntArray212[local53] * local282 + 32767 >> 15;
								this.anIntArray216[local53] = local282 * this.anIntArray216[local53] + 32767 - local59 * this.anIntArray212[local53] >> 15;
								this.anIntArray212[local53] = local301;
							}
							if (y != 0) {
								local59 = Class50_Sub1.anIntArray225[y];
								local282 = Class50_Sub1.anIntArray224[y];
								local301 = this.anIntArray212[local53] * local282 + this.anIntArray213[local53] * local59 + 32767 >> 15;
								this.anIntArray213[local53] = local282 * this.anIntArray213[local53] + 32767 - this.anIntArray212[local53] * local59 >> 15;
								this.anIntArray212[local53] = local301;
							}
							this.anIntArray212[local53] += Class6_Sub2.anInt255;
							this.anIntArray216[local53] += BillboardType.anInt3541;
							this.anIntArray213[local53] += Class4_Sub6_Sub19.anInt3954;
						}
					}
				}
			}
			if (lights) {
				for (local35 = 0; local35 < local8; local35++) {
					local41 = labels[local35];
					if (local41 < this.anIntArrayArray34.length) {
						local51 = this.anIntArrayArray34[local41];
						for (local53 = 0; local53 < local51.length; local53++) {
							local59 = local51[local53];
							local282 = this.anIntArray214[local59];
							local301 = this.anIntArray214[local59 + 1];
							for (local757 = local282; local757 < local301; local757++) {
								@Pc(766) int local766 = this.aShortArray109[local757] - 1;
								if (local766 == -1) {
									break;
								}
								@Pc(778) int local778;
								@Pc(782) int local782;
								@Pc(801) int local801;
								if (z != 0) {
									local778 = Class50_Sub1.anIntArray225[z];
									local782 = Class50_Sub1.anIntArray224[z];
									local801 = local778 * this.aShortArray101[local766] + local782 * this.aShortArray103[local766] + 32767 >> 15;
									this.aShortArray101[local766] = (short) (this.aShortArray101[local766] * local782 + 32767 - local778 * this.aShortArray103[local766] >> 15);
									this.aShortArray103[local766] = (short) local801;
								}
								if (x != 0) {
									local778 = Class50_Sub1.anIntArray225[x];
									local782 = Class50_Sub1.anIntArray224[x];
									local801 = this.aShortArray101[local766] * local782 + 32767 - local778 * this.aShortArray102[local766] >> 15;
									this.aShortArray102[local766] = (short) (this.aShortArray101[local766] * local778 + this.aShortArray102[local766] * local782 + 32767 >> 15);
									this.aShortArray101[local766] = (short) local801;
								}
								if (y != 0) {
									local778 = Class50_Sub1.anIntArray225[y];
									local782 = Class50_Sub1.anIntArray224[y];
									local801 = local778 * this.aShortArray102[local766] + this.aShortArray103[local766] * local782 + 32767 >> 15;
									this.aShortArray102[local766] = (short) (this.aShortArray102[local766] * local782 + 32767 - this.aShortArray103[local766] * local778 >> 15);
									this.aShortArray103[local766] = (short) local801;
								}
							}
						}
					}
				}
				if (this.normalsBuffer == null && this.colorsBuffer != null) {
					this.colorsBuffer.anInterface11_7 = null;
				}
				if (this.normalsBuffer != null) {
					this.normalsBuffer.anInterface11_7 = null;
				}
			}
		} else if (type == 3) {
			for (local27 = 0; local27 < local8; local27++) {
				local35 = labels[local27];
				if (local35 < this.anIntArrayArray34.length) {
					local159 = this.anIntArrayArray34[local35];
					for (local161 = 0; local161 < local159.length; local161++) {
						local53 = local159[local161];
						this.anIntArray212[local53] -= Class6_Sub2.anInt255;
						this.anIntArray216[local53] -= BillboardType.anInt3541;
						this.anIntArray213[local53] -= Class4_Sub6_Sub19.anInt3954;
						this.anIntArray212[local53] = x * this.anIntArray212[local53] >> 7;
						this.anIntArray216[local53] = this.anIntArray216[local53] * y >> 7;
						this.anIntArray213[local53] = this.anIntArray213[local53] * z >> 7;
						this.anIntArray212[local53] += Class6_Sub2.anInt255;
						this.anIntArray216[local53] += BillboardType.anInt3541;
						this.anIntArray213[local53] += Class4_Sub6_Sub19.anInt3954;
					}
				}
			}
		} else {
			@Pc(1206) NativeBillboardFace local1206;
			@Pc(1211) Class85 local1211;
			if (type == 5) {
				if (this.faceGroups != null) {
					for (local27 = 0; local27 < local8; local27++) {
						local35 = labels[local27];
						if (local35 < this.faceGroups.length) {
							local159 = this.faceGroups[local35];
							for (local161 = 0; local161 < local159.length; local161++) {
								local53 = local159[local161];
								local59 = x * 8 + (this.aByteArray38[local53] & 0xFF);
								if (local59 < 0) {
									local59 = 0;
								} else if (local59 > 255) {
									local59 = 255;
								}
								this.aByteArray38[local53] = (byte) local59;
							}
							if (local159.length > 0 && this.colorsBuffer != null) {
								this.colorsBuffer.anInterface11_7 = null;
							}
						}
					}
					if (this.aClass115Array1 != null) {
						for (local35 = 0; local35 < this.billboardCount; local35++) {
							local1206 = this.aClass115Array1[local35];
							local1211 = this.aClass85Array1[local35];
							local1211.anInt2743 = 255 - (this.aByteArray38[local1206.anInt3416] & 0xFF) << 24 | local1211.anInt2743 & 0xFFFFFF;
						}
					}
				}
			} else if (type != 7) {
				@Pc(1466) Class85 local1466;
				if (type == 8) {
					if (this.anIntArrayArray32 != null) {
						for (local27 = 0; local27 < local8; local27++) {
							local35 = labels[local27];
							if (local35 < this.anIntArrayArray32.length) {
								local159 = this.anIntArrayArray32[local35];
								for (local161 = 0; local161 < local159.length; local161++) {
									local1466 = this.aClass85Array1[local159[local161]];
									local1466.anInt2749 += x;
									local1466.anInt2746 += y;
								}
							}
						}
					}
				} else if (type == 10) {
					if (this.anIntArrayArray32 != null) {
						for (local27 = 0; local27 < local8; local27++) {
							local35 = labels[local27];
							if (local35 < this.anIntArrayArray32.length) {
								local159 = this.anIntArrayArray32[local35];
								for (local161 = 0; local161 < local159.length; local161++) {
									local1466 = this.aClass85Array1[local159[local161]];
									local1466.anInt2741 = x * local1466.anInt2741 >> 7;
									local1466.anInt2748 = local1466.anInt2748 * y >> 7;
								}
							}
						}
					}
				} else if (type == 9 && this.anIntArrayArray32 != null) {
					for (local27 = 0; local27 < local8; local27++) {
						local35 = labels[local27];
						if (this.anIntArrayArray32.length > local35) {
							local159 = this.anIntArrayArray32[local35];
							for (local161 = 0; local161 < local159.length; local161++) {
								local1466 = this.aClass85Array1[local159[local161]];
								local1466.anInt2745 = x + local1466.anInt2745 & 0x3FFF;
							}
						}
					}
				}
			} else if (this.faceGroups != null) {
				for (local27 = 0; local27 < local8; local27++) {
					local35 = labels[local27];
					if (local35 < this.faceGroups.length) {
						local159 = this.faceGroups[local35];
						for (local161 = 0; local161 < local159.length; local161++) {
							local53 = local159[local161];
							local59 = this.aShortArray100[local53] & 0xFFFF;
							local282 = local59 >> 10 & 0x3F;
							local301 = local59 >> 7 & 0x7;
							local757 = local59 & 0x7F;
							local301 += y / 4;
							@Pc(1309) int local1309 = x + local282 & 0x3F;
							local757 += z;
							if (local301 < 0) {
								local301 = 0;
							} else if (local301 > 7) {
								local301 = 7;
							}
							if (local757 < 0) {
								local757 = 0;
							} else if (local757 > 127) {
								local757 = 127;
							}
							this.aShortArray100[local53] = (short) (local1309 << 10 | local301 << 7 | local757);
						}
						if (local159.length > 0 && this.colorsBuffer != null) {
							this.colorsBuffer.anInterface11_7 = null;
						}
					}
				}
				if (this.aClass115Array1 != null) {
					for (local35 = 0; local35 < this.billboardCount; local35++) {
						local1206 = this.aClass115Array1[local35];
						local1211 = this.aClass85Array1[local35];
						local1211.anInt2743 = local1211.anInt2743 & 0xFF000000 | Class9_Sub3.anIntArray115[this.aShortArray100[local1206.anInt3416] & 0xFFFF] & 0xFFFFFF;
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ip", name = "N", descriptor = "(IIII)V")
	@Override
	protected void N(@OriginalArg(0) int type, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z) {
		@Pc(9) int local9;
		@Pc(17) int local17;
		if (type == 0) {
			local9 = 0;
			Class6_Sub2.anInt255 = 0;
			BillboardType.anInt3541 = 0;
			Class4_Sub6_Sub19.anInt3954 = 0;
			for (local17 = 0; local17 < this.maxDepth; local17++) {
				Class6_Sub2.anInt255 += this.anIntArray212[local17];
				BillboardType.anInt3541 += this.anIntArray216[local17];
				local9++;
				Class4_Sub6_Sub19.anInt3954 += this.anIntArray213[local17];
			}
			if (local9 > 0) {
				Class6_Sub2.anInt255 = x + Class6_Sub2.anInt255 / local9;
				BillboardType.anInt3541 = BillboardType.anInt3541 / local9 + y;
				Class4_Sub6_Sub19.anInt3954 = Class4_Sub6_Sub19.anInt3954 / local9 + z;
			} else {
				Class6_Sub2.anInt255 = x;
				BillboardType.anInt3541 = y;
				Class4_Sub6_Sub19.anInt3954 = z;
			}
		} else if (type == 1) {
			for (local9 = 0; local9 < this.maxDepth; local9++) {
				this.anIntArray212[local9] += x;
				this.anIntArray216[local9] += y;
				this.anIntArray213[local9] += z;
			}
		} else {
			@Pc(172) int local172;
			@Pc(190) int local190;
			if (type == 2) {
				for (local9 = 0; local9 < this.maxDepth; local9++) {
					this.anIntArray212[local9] -= Class6_Sub2.anInt255;
					this.anIntArray216[local9] -= BillboardType.anInt3541;
					this.anIntArray213[local9] -= Class4_Sub6_Sub19.anInt3954;
					if (z != 0) {
						local17 = Class50_Sub1.anIntArray225[z];
						local172 = Class50_Sub1.anIntArray224[z];
						local190 = local17 * this.anIntArray216[local9] + local172 * this.anIntArray212[local9] + 32767 >> 15;
						this.anIntArray216[local9] = this.anIntArray216[local9] * local172 + 32767 - local17 * this.anIntArray212[local9] >> 15;
						this.anIntArray212[local9] = local190;
					}
					if (x != 0) {
						local17 = Class50_Sub1.anIntArray225[x];
						local172 = Class50_Sub1.anIntArray224[x];
						local190 = local172 * this.anIntArray216[local9] + 32767 - local17 * this.anIntArray213[local9] >> 15;
						this.anIntArray213[local9] = local172 * this.anIntArray213[local9] + this.anIntArray216[local9] * local17 + 32767 >> 15;
						this.anIntArray216[local9] = local190;
					}
					if (y != 0) {
						local17 = Class50_Sub1.anIntArray225[y];
						local172 = Class50_Sub1.anIntArray224[y];
						local190 = this.anIntArray213[local9] * local17 + this.anIntArray212[local9] * local172 + 32767 >> 15;
						this.anIntArray213[local9] = this.anIntArray213[local9] * local172 + 32767 - local17 * this.anIntArray212[local9] >> 15;
						this.anIntArray212[local9] = local190;
					}
					this.anIntArray212[local9] += Class6_Sub2.anInt255;
					this.anIntArray216[local9] += BillboardType.anInt3541;
					this.anIntArray213[local9] += Class4_Sub6_Sub19.anInt3954;
				}
			} else if (type == 3) {
				for (local9 = 0; local9 < this.maxDepth; local9++) {
					this.anIntArray212[local9] -= Class6_Sub2.anInt255;
					this.anIntArray216[local9] -= BillboardType.anInt3541;
					this.anIntArray213[local9] -= Class4_Sub6_Sub19.anInt3954;
					this.anIntArray212[local9] = this.anIntArray212[local9] * x / 128;
					this.anIntArray216[local9] = y * this.anIntArray216[local9] / 128;
					this.anIntArray213[local9] = this.anIntArray213[local9] * z / 128;
					this.anIntArray212[local9] += Class6_Sub2.anInt255;
					this.anIntArray216[local9] += BillboardType.anInt3541;
					this.anIntArray213[local9] += Class4_Sub6_Sub19.anInt3954;
				}
			} else {
				@Pc(519) NativeBillboardFace local519;
				@Pc(524) Class85 local524;
				if (type == 5) {
					for (local9 = 0; local9 < this.numFaces; local9++) {
						local17 = (this.aByteArray38[local9] & 0xFF) + x * 8;
						if (local17 < 0) {
							local17 = 0;
						} else if (local17 > 255) {
							local17 = 255;
						}
						this.aByteArray38[local9] = (byte) local17;
					}
					if (this.colorsBuffer != null) {
						this.colorsBuffer.anInterface11_7 = null;
					}
					if (this.aClass115Array1 != null) {
						for (local17 = 0; local17 < this.billboardCount; local17++) {
							local519 = this.aClass115Array1[local17];
							local524 = this.aClass85Array1[local17];
							local524.anInt2743 = local524.anInt2743 & 0xFFFFFF | 255 - (this.aByteArray38[local519.anInt3416] & 0xFF) << 24;
						}
					}
				} else if (type == 7) {
					for (local9 = 0; local9 < this.numFaces; local9++) {
						local17 = this.aShortArray100[local9] & 0xFFFF;
						local172 = local17 >> 10 & 0x3F;
						local190 = local17 >> 7 & 0x7;
						local190 += y / 4;
						@Pc(587) int local587 = local17 & 0x7F;
						@Pc(594) int local594 = local172 + x & 0x3F;
						local587 += z;
						if (local190 < 0) {
							local190 = 0;
						} else if (local190 > 7) {
							local190 = 7;
						}
						if (local587 < 0) {
							local587 = 0;
						} else if (local587 > 127) {
							local587 = 127;
						}
						this.aShortArray100[local9] = (short) (local594 << 10 | local190 << 7 | local587);
					}
					if (this.colorsBuffer != null) {
						this.colorsBuffer.anInterface11_7 = null;
					}
					if (this.aClass115Array1 != null) {
						for (local17 = 0; local17 < this.billboardCount; local17++) {
							local519 = this.aClass115Array1[local17];
							local524 = this.aClass85Array1[local17];
							local524.anInt2743 = Class9_Sub3.anIntArray115[this.aShortArray100[local519.anInt3416] & 0xFFFF] & 0xFFFFFF | local524.anInt2743 & 0xFF000000;
						}
					}
				} else {
					@Pc(706) Class85 local706;
					if (type == 8) {
						for (local9 = 0; local9 < this.billboardCount; local9++) {
							local706 = this.aClass85Array1[local9];
							local706.anInt2746 += y;
							local706.anInt2749 += x;
						}
					} else if (type == 10) {
						for (local9 = 0; local9 < this.billboardCount; local9++) {
							local706 = this.aClass85Array1[local9];
							local706.anInt2748 = local706.anInt2748 * y >> 7;
							local706.anInt2741 = local706.anInt2741 * x >> 7;
						}
					} else if (type == 9) {
						for (local9 = 0; local9 < this.billboardCount; local9++) {
							local706 = this.aClass85Array1[local9];
							local706.anInt2745 = local706.anInt2745 + x & 0x3FFF;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ip", name = "j", descriptor = "()V")
	@Override
	protected void scaleWidth() {
		for (@Pc(3) int local3 = 0; local3 < this.numVertices; local3++) {
			this.anIntArray212[local3] = this.anIntArray212[local3] + 7 >> 4;
			this.anIntArray216[local3] = this.anIntArray216[local3] + 7 >> 4;
			this.anIntArray213[local3] = this.anIntArray213[local3] + 7 >> 4;
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "PA", descriptor = "()I")
	@Override
	public int PA() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort27;
	}

	@OriginalMember(owner = "client!ip", name = "UA", descriptor = "(I)V")
	@Override
	public void UA(@OriginalArg(0) int functionMask) {
		this.functionMask = functionMask;
		this.aBoolean281 = true;
		if (this.mergedNormals != null && (this.functionMask & 0x10000) == 0) {
			this.aShortArray101 = this.mergedNormals.aShortArray142;
			this.aShortArray102 = this.mergedNormals.aShortArray144;
			this.aByteArray39 = this.mergedNormals.magnitude;
			this.aShortArray103 = this.mergedNormals.aShortArray143;
			this.mergedNormals = null;
		}
		this.method2807();
	}

	@OriginalMember(owner = "client!ip", name = "fa", descriptor = "(Lclient!ba;)Lclient!ba;")
	@Override
	public Shadow fa(@OriginalArg(0) Shadow shadow) {
		if (this.numNormals == 0) {
			return null;
		}
		if (!this.boundsCalculated) {
			this.method2804();
		}
		@Pc(40) int local40;
		@Pc(57) int local57;
		if (this.glToolkit.sunSlopeZ > 0) {
			local40 = this.aShort29 - (this.aShort28 * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
			local57 = this.aShort31 - (this.lb * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
		} else {
			local40 = this.aShort29 - (this.lb * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
			local57 = this.aShort31 - (this.aShort28 * this.glToolkit.sunSlopeZ >> 8) >> this.glToolkit.shadowScaleShift;
		}
		@Pc(114) int local114;
		@Pc(131) int local131;
		if (this.glToolkit.sunSlopeX <= 0) {
			local114 = this.aShort27 - (this.glToolkit.sunSlopeX * this.lb >> 8) >> this.glToolkit.shadowScaleShift;
			local131 = this.aShort25 - (this.glToolkit.sunSlopeX * this.aShort28 >> 8) >> this.glToolkit.shadowScaleShift;
		} else {
			local114 = this.aShort27 - (this.glToolkit.sunSlopeX * this.aShort28 >> 8) >> this.glToolkit.shadowScaleShift;
			local131 = this.aShort25 - (this.lb * this.glToolkit.sunSlopeX >> 8) >> this.glToolkit.shadowScaleShift;
		}
		@Pc(172) int local172 = local57 + 1 - local40;
		@Pc(179) int local179 = local131 + 1 - local114;
		@Pc(182) GlShadow local182 = (GlShadow) shadow;
		@Pc(198) GlShadow local198;
		if (local182 != null && local182.fitsDimension(local172, local179)) {
			local198 = local182;
			local182.reset();
		} else {
			local198 = new GlShadow(this.glToolkit, local172, local179);
		}
		local198.setDimension(local114, local40, local57, local131);
		this.method2800(local198);
		return local198;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(Lclient!t;IIIZ)V")
	@Override
	public void shareLight(@OriginalArg(0) Model model, @OriginalArg(1) int x, @OriginalArg(2) int y, @OriginalArg(3) int z, @OriginalArg(4) boolean renderTypeAdjustment) {
		@Pc(8) GlModel local8 = (GlModel) model;
		if (this.numFaces == 0 || local8.numFaces == 0) {
			return;
		}
		@Pc(18) int local18 = local8.maxDepth;
		@Pc(21) int[] local21 = local8.anIntArray212;
		@Pc(24) int[] local24 = local8.anIntArray216;
		@Pc(27) int[] local27 = local8.anIntArray213;
		@Pc(30) short[] local30 = local8.aShortArray103;
		@Pc(33) short[] local33 = local8.aShortArray101;
		@Pc(36) short[] local36 = local8.aShortArray102;
		@Pc(39) byte[] local39 = local8.aByteArray39;
		@Pc(44) short[] local44;
		@Pc(48) short[] local48;
		@Pc(50) short[] local50;
		@Pc(46) byte[] local46;
		if (this.mergedNormals == null) {
			local44 = null;
			local46 = null;
			local48 = null;
			local50 = null;
		} else {
			local48 = this.mergedNormals.aShortArray142;
			local46 = this.mergedNormals.magnitude;
			local50 = this.mergedNormals.aShortArray144;
			local44 = this.mergedNormals.aShortArray143;
		}
		@Pc(79) short[] local79;
		@Pc(83) short[] local83;
		@Pc(87) short[] local87;
		@Pc(75) byte[] local75;
		if (local8.mergedNormals == null) {
			local75 = null;
			local79 = null;
			local83 = null;
			local87 = null;
		} else {
			local75 = local8.mergedNormals.magnitude;
			local79 = local8.mergedNormals.aShortArray143;
			local83 = local8.mergedNormals.aShortArray142;
			local87 = local8.mergedNormals.aShortArray144;
		}
		@Pc(100) int[] local100 = local8.anIntArray214;
		@Pc(103) short[] local103 = local8.aShortArray109;
		if (!local8.boundsCalculated) {
			local8.method2804();
		}
		@Pc(112) short local112 = local8.lb;
		@Pc(115) short local115 = local8.aShort28;
		@Pc(118) short local118 = local8.aShort29;
		@Pc(121) short local121 = local8.aShort31;
		@Pc(124) short local124 = local8.aShort27;
		@Pc(127) short local127 = local8.aShort25;
		for (@Pc(129) int local129 = 0; local129 < this.maxDepth; local129++) {
			@Pc(139) int local139 = this.anIntArray216[local129] - y;
			if (local112 <= local139 && local115 >= local139) {
				@Pc(163) int local163 = this.anIntArray212[local129] - x;
				if (local163 >= local118 && local163 <= local121) {
					@Pc(178) int local178 = this.anIntArray213[local129] - z;
					if (local178 >= local124 && local127 >= local178) {
						@Pc(196) int local196 = -1;
						@Pc(201) int local201 = this.anIntArray214[local129];
						@Pc(208) int local208 = this.anIntArray214[local129 + 1];
						for (@Pc(210) int local210 = local201; local210 < local208; local210++) {
							local196 = this.aShortArray109[local210] - 1;
							if (local196 == -1 || this.aByteArray39[local196] != 0) {
								break;
							}
						}
						if (local196 != -1) {
							for (@Pc(243) int local243 = 0; local243 < local18; local243++) {
								if (local21[local243] == local163 && local27[local243] == local178 && local139 == local24[local243]) {
									@Pc(266) int local266 = -1;
									local201 = local100[local243];
									local208 = local100[local243 + 1];
									for (@Pc(278) int local278 = local201; local278 < local208; local278++) {
										local266 = local103[local278] - 1;
										if (local266 == -1 || local39[local266] != 0) {
											break;
										}
									}
									if (local266 != -1) {
										if (local44 == null) {
											this.mergedNormals = new ModelNormalsGL();
											local44 = this.mergedNormals.aShortArray143 = Class4_Sub18.method2031(this.aShortArray103);
											local48 = this.mergedNormals.aShortArray142 = Class4_Sub18.method2031(this.aShortArray101);
											local50 = this.mergedNormals.aShortArray144 = Class4_Sub18.method2031(this.aShortArray102);
											local46 = this.mergedNormals.magnitude = Class38.method841(this.aByteArray39);
										}
										if (local79 == null) {
											@Pc(359) ModelNormalsGL local359 = local8.mergedNormals = new ModelNormalsGL();
											local79 = local359.aShortArray143 = Class4_Sub18.method2031(local30);
											local83 = local359.aShortArray142 = Class4_Sub18.method2031(local33);
											local87 = local359.aShortArray144 = Class4_Sub18.method2031(local36);
											local75 = local359.magnitude = Class38.method841(local39);
										}
										@Pc(392) short local392 = this.aShortArray103[local196];
										@Pc(397) short local397 = this.aShortArray101[local196];
										@Pc(402) short local402 = this.aShortArray102[local196];
										@Pc(407) byte local407 = this.aByteArray39[local196];
										local208 = local100[local243 + 1];
										local201 = local100[local243];
										@Pc(427) int local427;
										for (@Pc(419) int local419 = local201; local419 < local208; local419++) {
											local427 = local103[local419] - 1;
											if (local427 == -1) {
												break;
											}
											if (local75[local427] != 0) {
												local79[local427] += local392;
												local83[local427] += local397;
												local87[local427] += local402;
												local75[local427] += local407;
											}
										}
										local407 = local39[local266];
										local201 = this.anIntArray214[local129];
										local208 = this.anIntArray214[local129 + 1];
										local402 = local36[local266];
										local392 = local30[local266];
										local397 = local33[local266];
										for (local427 = local201; local427 < local208; local427++) {
											@Pc(510) int local510 = this.aShortArray109[local427] - 1;
											if (local510 == -1) {
												break;
											}
											if (local46[local510] != 0) {
												local44[local510] += local392;
												local48[local510] += local397;
												local50[local510] += local402;
												local46[local510] += local407;
											}
										}
										if (this.normalsBuffer == null && this.colorsBuffer != null) {
											this.colorsBuffer.anInterface11_7 = null;
										}
										if (this.normalsBuffer != null) {
											this.normalsBuffer.anInterface11_7 = null;
										}
										if (local8.normalsBuffer == null && local8.colorsBuffer != null) {
											local8.colorsBuffer.anInterface11_7 = null;
										}
										if (local8.normalsBuffer != null) {
											local8.normalsBuffer.anInterface11_7 = null;
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

	@OriginalMember(owner = "client!ip", name = "U", descriptor = "()I")
	@Override
	public int U() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort26;
	}

	@OriginalMember(owner = "client!ip", name = "b", descriptor = "()[Lclient!pf;")
	@Override
	public EffectiveVertex[] getEffectiveVertices() {
		return this.effectiveVertices;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(ILclient!tu;)V")
	private void method2794(@OriginalArg(1) JavaMatrix arg0) {
		@Pc(6) int local6;
		if (this.emissiveTriangles != null) {
			for (local6 = 0; local6 < this.emissiveTriangles.length; local6++) {
				@Pc(13) EmissiveTriangle local13 = this.emissiveTriangles[local6];
				@Pc(15) EmissiveTriangle local15 = local13;
				if (local13.aClass29_1 != null) {
					local15 = local13.aClass29_1;
				}
				local15.anInt699 = (int) ((float) this.anIntArray212[local13.anInt706] * arg0.aFloat202 + arg0.aFloat207 * (float) this.anIntArray216[local13.anInt706] + (float) this.anIntArray213[local13.anInt706] * arg0.aFloat208 + arg0.aFloat209);
				local15.anInt700 = (int) (arg0.aFloat205 + arg0.aFloat201 * (float) this.anIntArray213[local13.anInt706] + (float) this.anIntArray212[local13.anInt706] * arg0.aFloat200 + (float) this.anIntArray216[local13.anInt706] * arg0.aFloat204);
				local15.anInt711 = (int) (arg0.aFloat198 + arg0.aFloat199 * (float) this.anIntArray213[local13.anInt706] + (float) this.anIntArray216[local13.anInt706] * arg0.aFloat203 + arg0.aFloat206 * (float) this.anIntArray212[local13.anInt706]);
				local15.anInt701 = (int) (arg0.aFloat209 + arg0.aFloat208 * (float) this.anIntArray213[local13.anInt713] + arg0.aFloat207 * (float) this.anIntArray216[local13.anInt713] + (float) this.anIntArray212[local13.anInt713] * arg0.aFloat202);
				local15.anInt702 = (int) (arg0.aFloat200 * (float) this.anIntArray212[local13.anInt713] + arg0.aFloat204 * (float) this.anIntArray216[local13.anInt713] + arg0.aFloat201 * (float) this.anIntArray213[local13.anInt713] + arg0.aFloat205);
				local15.anInt707 = (int) ((float) this.anIntArray213[local13.anInt713] * arg0.aFloat199 + (float) this.anIntArray216[local13.anInt713] * arg0.aFloat203 + arg0.aFloat206 * (float) this.anIntArray212[local13.anInt713] + arg0.aFloat198);
				local15.anInt708 = (int) (arg0.aFloat209 + (float) this.anIntArray216[local13.anInt698] * arg0.aFloat207 + arg0.aFloat202 * (float) this.anIntArray212[local13.anInt698] + (float) this.anIntArray213[local13.anInt698] * arg0.aFloat208);
				local15.anInt703 = (int) (arg0.aFloat205 + (float) this.anIntArray213[local13.anInt698] * arg0.aFloat201 + arg0.aFloat204 * (float) this.anIntArray216[local13.anInt698] + (float) this.anIntArray212[local13.anInt698] * arg0.aFloat200);
				local15.anInt709 = (int) (arg0.aFloat198 + arg0.aFloat199 * (float) this.anIntArray213[local13.anInt698] + arg0.aFloat206 * (float) this.anIntArray212[local13.anInt698] + (float) this.anIntArray216[local13.anInt698] * arg0.aFloat203);
			}
		}
		if (this.effectiveVertices == null) {
			return;
		}
		for (local6 = 0; local6 < this.effectiveVertices.length; local6++) {
			@Pc(363) EffectiveVertex local363 = this.effectiveVertices[local6];
			@Pc(365) EffectiveVertex local365 = local363;
			if (local363.aClass179_2 != null) {
				local365 = local363.aClass179_2;
			}
			if (local363.aClass31_4 == null) {
				local363.aClass31_4 = arg0.method5657();
			} else {
				local363.aClass31_4.UnlitEffect(arg0);
			}
			local365.anInt5342 = (int) (arg0.aFloat209 + (float) this.anIntArray213[local363.appliesOnVertex] * arg0.aFloat208 + arg0.aFloat207 * (float) this.anIntArray216[local363.appliesOnVertex] + (float) this.anIntArray212[local363.appliesOnVertex] * arg0.aFloat202);
			local365.anInt5340 = (int) ((float) this.anIntArray212[local363.appliesOnVertex] * arg0.aFloat200 + arg0.aFloat204 * (float) this.anIntArray216[local363.appliesOnVertex] + (float) this.anIntArray213[local363.appliesOnVertex] * arg0.aFloat201 + arg0.aFloat205);
			local365.anInt5347 = (int) ((float) this.anIntArray213[local363.appliesOnVertex] * arg0.aFloat199 + arg0.aFloat203 * (float) this.anIntArray216[local363.appliesOnVertex] + arg0.aFloat206 * (float) this.anIntArray212[local363.appliesOnVertex] + arg0.aFloat198);
		}
	}

	@OriginalMember(owner = "client!ip", name = "HA", descriptor = "()I")
	@Override
	public int HA() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort29;
	}

	@OriginalMember(owner = "client!ip", name = "c", descriptor = "(I)V")
	@Override
	public void c(@OriginalArg(0) int angle) {
		@Pc(9) int local9 = Class50_Sub1.anIntArray225[angle];
		@Pc(13) int local13 = Class50_Sub1.anIntArray224[angle];
		@Pc(33) int local33;
		for (@Pc(15) int local15 = 0; local15 < this.maxDepth; local15++) {
			local33 = this.anIntArray212[local15] * local13 + this.anIntArray213[local15] * local9 >> 15;
			this.anIntArray213[local15] = local13 * this.anIntArray213[local15] - local9 * this.anIntArray212[local15] >> 15;
			this.anIntArray212[local15] = local33;
		}
		for (local33 = 0; local33 < this.numNormals; local33++) {
			@Pc(83) int local83 = local13 * this.aShortArray103[local33] + this.aShortArray102[local33] * local9 >> 15;
			this.aShortArray102[local33] = (short) (local13 * this.aShortArray102[local33] - local9 * this.aShortArray103[local33] >> 15);
			this.aShortArray103[local33] = (short) local83;
		}
		if (this.normalsBuffer == null && this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
		if (this.normalsBuffer != null) {
			this.normalsBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "D", descriptor = "()I")
	@Override
	public int D() {
		return this.ambient;
	}

	@OriginalMember(owner = "client!ip", name = "BA", descriptor = "(SS)V")
	@Override
	public void copyAlpha(@OriginalArg(0) short arg0, @OriginalArg(1) short arg1) {
		for (@Pc(7) int local7 = 0; local7 < this.numFaces; local7++) {
			if (this.aShortArray100[local7] == arg0) {
				this.aShortArray100[local7] = arg1;
			}
		}
		if (this.aClass115Array1 != null) {
			for (@Pc(34) int local34 = 0; local34 < this.billboardCount; local34++) {
				@Pc(41) NativeBillboardFace local41 = this.aClass115Array1[local34];
				@Pc(46) Class85 local46 = this.aClass85Array1[local34];
				local46.anInt2743 = Class9_Sub3.anIntArray115[this.aShortArray100[local41.anInt3416] & 0xFFFF] & 0xFFFFFF | local46.anInt2743 & 0xFF000000;
			}
		}
		if (this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "Z", descriptor = "(I)V")
	@Override
	public void Z(@OriginalArg(0) int arg0) {
		@Pc(9) int local9 = Class50_Sub1.anIntArray225[arg0];
		@Pc(13) int local13 = Class50_Sub1.anIntArray224[arg0];
		for (@Pc(15) int local15 = 0; local15 < this.maxDepth; local15++) {
			@Pc(34) int local34 = this.anIntArray216[local15] * local13 - this.anIntArray213[local15] * local9 >> 15;
			this.anIntArray213[local15] = local13 * this.anIntArray213[local15] + this.anIntArray216[local15] * local9 >> 15;
			this.anIntArray216[local15] = local34;
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "h", descriptor = "(IILclient!ta;Lclient!ta;III)V")
	@Override
	public void h(@OriginalArg(0) int contourType, @OriginalArg(1) int contourModifier, @OriginalArg(2) Class65 ground, @OriginalArg(3) Class65 ground2, @OriginalArg(4) int x, @OriginalArg(5) int y, @OriginalArg(6) int z) {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		@Pc(16) int local16 = x + this.aShort29;
		@Pc(22) int local22 = x + this.aShort31;
		@Pc(28) int local28 = z + this.aShort27;
		@Pc(33) int local33 = this.aShort25 + z;
		if ((contourType == 1 || contourType == 2 || contourType == 3 || contourType == 5) && (local16 < 0 || local22 + ground.anInt5261 >> ground.anInt5256 >= ground.anInt5262 || local28 < 0 || ground.anInt5258 <= local33 + ground.anInt5261 >> ground.anInt5256)) {
			return;
		}
		if (contourType == 4 || contourType == 5) {
			if (ground2 == null) {
				return;
			}
			if (local16 < 0 || local22 + ground2.anInt5261 >> ground2.anInt5256 >= ground2.anInt5262 || local28 < 0 || local33 + ground2.anInt5261 >> ground2.anInt5256 >= ground2.anInt5258) {
				return;
			}
		} else {
			local16 >>= ground.anInt5256;
			local22 = ground.anInt5261 + local22 - 1 >> ground.anInt5256;
			local28 >>= ground.anInt5256;
			local33 = ground.anInt5261 + local33 - 1 >> ground.anInt5256;
			if (y == ground.l(local16, local28) && ground.l(local22, local28) == y && y == ground.l(local16, local33) && y == ground.l(local22, local33)) {
				return;
			}
		}
		@Pc(209) int local209;
		if (contourType == 1) {
			for (local209 = 0; local209 < this.maxDepth; local209++) {
				this.anIntArray216[local209] = this.anIntArray216[local209] + ground.a(x + this.anIntArray212[local209], this.anIntArray213[local209] - -z) - y;
			}
		} else {
			@Pc(211) int local211;
			@Pc(220) int local220;
			if (contourType == 2) {
				@Pc(422) short local422 = this.lb;
				if (local422 == 0) {
					return;
				}
				for (local211 = 0; local211 < this.maxDepth; local211++) {
					local220 = (this.anIntArray216[local211] << 16) / local422;
					if (local220 < contourModifier) {
						this.anIntArray216[local211] += (contourModifier - local220) * (-y + ground.a(x + this.anIntArray212[local211], this.anIntArray213[local211] - -z)) / contourModifier;
					}
				}
			} else {
				@Pc(227) int local227;
				if (contourType == 3) {
					local209 = (contourModifier & 0xFF) * 4;
					local211 = (contourModifier >> 8 & 0xFF) * 4;
					local220 = (contourModifier >> 16 & 0xFF) << 6;
					local227 = (contourModifier >> 24 & 0xFF) << 6;
					if (x - (local209 >> 1) < 0 || (local209 >> 1) + x + ground.anInt5261 >= ground.anInt5262 << ground.anInt5256 || z - (local211 >> 1) < 0 || (local211 >> 1) + z + ground.anInt5261 >= ground.anInt5258 << ground.anInt5256) {
						return;
					}
					this.method4884(x, z, local211, local220, ground, local209, local227, y);
				} else if (contourType == 4) {
					local209 = this.aShort28 - this.lb;
					for (local211 = 0; local211 < this.maxDepth; local211++) {
						this.anIntArray216[local211] = local209 + this.anIntArray216[local211] + ground2.a(x + this.anIntArray212[local211], z + this.anIntArray213[local211]) - y;
					}
				} else if (contourType == 5) {
					local209 = this.aShort28 - this.lb;
					for (local211 = 0; local211 < this.maxDepth; local211++) {
						local220 = x + this.anIntArray212[local211];
						local227 = this.anIntArray213[local211] + z;
						@Pc(232) int local232 = ground.a(local220, local227);
						@Pc(237) int local237 = ground2.a(local220, local227);
						@Pc(241) int local241 = local232 - local237;
						this.anIntArray216[local211] = local232 + (local241 * ((this.anIntArray216[local211] << 8) / local209) >> 8) - y;
					}
				}
			}
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "d", descriptor = "()Z")
	@Override
	public boolean loadedTextures() {
		if (this.aShortArray107 == null) {
			return true;
		}
		for (@Pc(12) int local12 = 0; local12 < this.aShortArray107.length; local12++) {
			if (this.aShortArray107[local12] != -1 && !this.glToolkit.materialProvider.textureAvailable(this.aShortArray107[local12])) {
				return false;
			}
		}
		return true;
	}

	@OriginalMember(owner = "client!ip", name = "NA", descriptor = "()I")
	@Override
	public int NA() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort31;
	}

	@OriginalMember(owner = "client!ip", name = "l", descriptor = "()I")
	@Override
	public int l() {
		return this.contrast;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IILclient!c;Z)Z")
	@Override
	public boolean picked(@OriginalArg(0) int x, @OriginalArg(1) int arg1, @OriginalArg(2) Ground arg2, @OriginalArg(3) boolean arg3) {
		@Pc(8) JavaMatrix local8 = (JavaMatrix) arg2;
		@Pc(12) JavaMatrix local12 = this.glToolkit.aClass31_Sub1_4;
		@Pc(33) float local33 = local12.aFloat209 + local8.aFloat205 * local12.aFloat207 + local8.aFloat209 * local12.aFloat202 + local8.aFloat198 * local12.aFloat208;
		@Pc(54) float local54 = local12.aFloat200 * local8.aFloat209 + local12.aFloat204 * local8.aFloat205 + local12.aFloat201 * local8.aFloat198 + local12.aFloat205;
		Class249.aFloat213 = local8.aFloat199 * local12.aFloat201 + local8.aFloat201 * local12.aFloat204 + local12.aFloat200 * local8.aFloat208;
		GameShell.aFloat30 = local8.aFloat204 * local12.aFloat207 + local8.aFloat207 * local12.aFloat202 + local8.aFloat203 * local12.aFloat208;
		Class78_Sub1.aFloat126 = local12.aFloat206 * local8.aFloat208 + local8.aFloat201 * local12.aFloat203 + local8.aFloat199 * local12.aFloat199;
		Class50.aFloat113 = local8.aFloat201 * local12.aFloat207 + local12.aFloat202 * local8.aFloat208 + local12.aFloat208 * local8.aFloat199;
		GameCanvas.aFloat189 = local8.aFloat204 * local12.aFloat203 + local8.aFloat207 * local12.aFloat206 + local8.aFloat203 * local12.aFloat199;
		Class4_Sub6_Sub22.aFloat140 = local12.aFloat208 * local8.aFloat206 + local8.aFloat202 * local12.aFloat202 + local8.aFloat200 * local12.aFloat207;
		@Pc(183) float local183 = local8.aFloat209 * local12.aFloat206 + local12.aFloat203 * local8.aFloat205 + local8.aFloat198 * local12.aFloat199 + local12.aFloat198;
		Class4_Sub45.aFloat227 = local8.aFloat202 * local12.aFloat206 + local12.aFloat203 * local8.aFloat200 + local12.aFloat199 * local8.aFloat206;
		CursorTypeList.aFloat163 = local8.aFloat204 * local12.aFloat204 + local12.aFloat200 * local8.aFloat207 + local12.aFloat201 * local8.aFloat203;
		Class16_Sub5_Sub2.aFloat184 = local12.aFloat201 * local8.aFloat206 + local8.aFloat200 * local12.aFloat204 + local8.aFloat202 * local12.aFloat200;
		@Pc(239) boolean local239 = false;
		@Pc(241) float local241 = Float.MAX_VALUE;
		@Pc(243) float local243 = -3.4028235E38F;
		@Pc(245) float local245 = Float.MAX_VALUE;
		@Pc(247) float local247 = -3.4028235E38F;
		@Pc(251) int local251 = this.glToolkit.anInt2309;
		@Pc(255) int local255 = this.glToolkit.anInt2294;
		if (!this.boundsCalculated) {
			this.method2804();
		}
		SpriteRenderNode.anIntArray328[0] = this.aShort29;
		Class164.anIntArray334[0] = this.lb;
		SpriteRenderNode.anIntArray328[1] = this.aShort31;
		Class15.anIntArray13[0] = this.aShort27;
		Class164.anIntArray334[1] = this.lb;
		Class15.anIntArray13[1] = this.aShort27;
		SpriteRenderNode.anIntArray328[2] = this.aShort29;
		Class164.anIntArray334[2] = this.aShort28;
		Class15.anIntArray13[2] = this.aShort27;
		SpriteRenderNode.anIntArray328[3] = this.aShort31;
		Class164.anIntArray334[3] = this.aShort28;
		SpriteRenderNode.anIntArray328[4] = this.aShort29;
		Class15.anIntArray13[3] = this.aShort27;
		Class164.anIntArray334[4] = this.lb;
		SpriteRenderNode.anIntArray328[5] = this.aShort31;
		Class15.anIntArray13[4] = this.aShort25;
		Class164.anIntArray334[5] = this.lb;
		SpriteRenderNode.anIntArray328[6] = this.aShort29;
		Class15.anIntArray13[5] = this.aShort25;
		Class164.anIntArray334[6] = this.aShort28;
		Class15.anIntArray13[6] = this.aShort25;
		SpriteRenderNode.anIntArray328[7] = this.aShort31;
		Class164.anIntArray334[7] = this.aShort28;
		Class15.anIntArray13[7] = this.aShort25;
		@Pc(442) float local442;
		@Pc(428) float local428;
		@Pc(414) float local414;
		@Pc(395) float local395;
		@Pc(390) float local390;
		@Pc(400) float local400;
		for (@Pc(383) int local383 = 0; local383 < 8; local383++) {
			local390 = Class164.anIntArray334[local383];
			local395 = SpriteRenderNode.anIntArray328[local383];
			local400 = Class15.anIntArray13[local383];
			local414 = Class78_Sub1.aFloat126 * local400 + local395 * Class4_Sub45.aFloat227 + GameCanvas.aFloat189 * local390 + local183;
			local428 = local390 * CursorTypeList.aFloat163 + Class16_Sub5_Sub2.aFloat184 * local395 + local400 * Class249.aFloat213 + local54;
			local442 = local33 + local390 * GameShell.aFloat30 + Class4_Sub6_Sub22.aFloat140 * local395 + Class50.aFloat113 * local400;
			if (local414 >= (float) this.glToolkit.zNear) {
				@Pc(461) float local461 = (float) this.glToolkit.anInt2295 + local442 * (float) local251 / local414;
				if (local461 < local241) {
					local241 = local461;
				}
				if (local461 > local243) {
					local243 = local461;
				}
				@Pc(485) float local485 = (float) this.glToolkit.anInt2297 + (float) local255 * local428 / local414;
				local239 = true;
				if (local245 > local485) {
					local245 = local485;
				}
				if (local247 < local485) {
					local247 = local485;
				}
			}
		}
		if (local239 && (float) x > local241 && (float) x < local243 && (float) arg1 > local245 && (float) arg1 < local247) {
			if (arg3) {
				return true;
			}
			if (Static231.anIntArray298.length < this.numNormals) {
				Static173.anIntArray219 = new int[this.numNormals];
				Static231.anIntArray298 = new int[this.numNormals];
			}
			@Pc(628) int local628;
			for (@Pc(546) int local546 = 0; local546 < this.maxDepth; local546++) {
				local395 = this.anIntArray212[local546];
				local390 = this.anIntArray216[local546];
				local400 = this.anIntArray213[local546];
				local442 = local33 + local390 * GameShell.aFloat30 + Class4_Sub6_Sub22.aFloat140 * local395 + Class50.aFloat113 * local400;
				local414 = local183 + Class78_Sub1.aFloat126 * local400 + local390 * GameCanvas.aFloat189 + local395 * Class4_Sub45.aFloat227;
				local428 = local54 + Class249.aFloat213 * local400 + Class16_Sub5_Sub2.aFloat184 * local395 + local390 * CursorTypeList.aFloat163;
				@Pc(641) int local641;
				@Pc(646) int local646;
				@Pc(653) int local653;
				if ((float) this.glToolkit.zNear <= local414) {
					local628 = (int) (local442 * (float) local251 / local414 + (float) this.glToolkit.anInt2295);
					local641 = (int) (local428 * (float) local255 / local414 + (float) this.glToolkit.anInt2297);
					local646 = this.anIntArray214[local546];
					local653 = this.anIntArray214[local546 + 1];
					for (@Pc(655) int local655 = local646; local655 < local653; local655++) {
						@Pc(664) int local664 = this.aShortArray109[local655] - 1;
						if (local664 == -1) {
							break;
						}
						Static231.anIntArray298[local664] = local628;
						Static173.anIntArray219[local664] = local641;
					}
				} else {
					local628 = this.anIntArray214[local546];
					local641 = this.anIntArray214[local546 + 1];
					for (local646 = local628; local646 < local641; local646++) {
						local653 = this.aShortArray109[local646] - 1;
						if (local653 == -1) {
							break;
						}
						Static231.anIntArray298[this.aShortArray109[local646] - 1] = -999999;
					}
				}
			}
			for (local628 = 0; local628 < this.numFaces; local628++) {
				if (Static231.anIntArray298[this.aShortArray108[local628]] != -999999 && Static231.anIntArray298[this.aShortArray105[local628]] != -999999 && Static231.anIntArray298[this.aShortArray106[local628]] != -999999 && this.method2806(Static231.anIntArray298[this.aShortArray108[local628]], Static173.anIntArray219[this.aShortArray108[local628]], Static231.anIntArray298[this.aShortArray106[local628]], arg1, Static231.anIntArray298[this.aShortArray105[local628]], Static173.anIntArray219[this.aShortArray105[local628]], Static173.anIntArray219[this.aShortArray106[local628]], x)) {
					return true;
				}
			}
		}
		return false;
	}

	@OriginalMember(owner = "client!ip", name = "m", descriptor = "()I")
	@Override
	public int addLight() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort25;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(Z)V")
	private void method2797() {
		if (this.renderedFaceCount == 0) {
			return;
		}
		if (this.renderState != 0) {
			this.method2799(true);
		}
		this.method2799(false);
		if (this.aClass230_1 != null) {
			if (this.aClass230_1.anInterface1_5 == null) {
				this.method2808((this.renderState & 0x10) != 0);
			}
			if (this.aClass230_1.anInterface1_5 != null) {
				this.glToolkit.method1906(this.normalsBuffer != null);
				this.glToolkit.method1934(this.colorsBuffer, this.normalsBuffer, this.aClass177_9, this.vertexBuffer);
				@Pc(73) int local73 = this.anIntArray215.length - 1;
				for (@Pc(75) int local75 = 0; local75 < local73; local75++) {
					@Pc(82) int local82 = this.anIntArray215[local75];
					@Pc(89) int local89 = this.anIntArray215[local75 + 1];
					@Pc(96) int local96 = this.aShortArray107[local82] & 0xFFFF;
					if (local96 == 65535) {
						local96 = -1;
					}
					this.glToolkit.method1938(local96, this.normalsBuffer != null);
					// 592HD per-pixel lighting, bound per primitive because the
					// material effect above may have bound its own program.
					@Pc(97) boolean hdLighting = this.glToolkit.hdBeginModelLighting(local96 >= 0, this.hdVegetation);
					this.glToolkit.method1967(local82 * 3, (local89 - local82) * 3, this.aClass230_1.anInterface1_5);
					if (hdLighting) {
						this.glToolkit.hdEndModelLighting();
					}
				}
			}
		}
		this.method2807();
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IIBBS)I")
	private int method2798(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) byte arg2, @OriginalArg(4) short arg3) {
		@Pc(20) int local20 = Class9_Sub3.anIntArray115[QuickchatMessageType.method236(arg1, arg0)];
		if (arg3 != -1) {
			@Pc(32) MaterialType local32 = this.glToolkit.materialProvider.getMetrics(arg3 & 0xFFFF);
			@Pc(37) int local37 = local32.aByte90 & 0xFF;
			@Pc(51) int local51;
			@Pc(68) int local68;
			if (local37 != 0) {
				if (arg1 < 0) {
					local51 = 0;
				} else if (arg1 <= 127) {
					local51 = arg1 * 131586;
				} else {
					local51 = 16777215;
				}
				if (local37 == 256) {
					local20 = local51;
				} else {
					local68 = 256 - local37;
					local20 = (local37 * (local51 & 0xFF00FF) + local68 * (local20 & 0xFF00FF) & 0xFF00FF00) + (local68 * (local20 & 0xFF00) + local37 * (local51 & 0xFF00) & 0xFF0000) >> 8;
				}
			}
			local51 = local32.aByte87 & 0xFF;
			if (local51 != 0) {
				local51 += 256;
				@Pc(119) int local119 = (local20 >> 16 & 0xFF) * local51;
				if (local119 > 65535) {
					local119 = 65535;
				}
				local68 = local51 * (local20 >> 8 & 0xFF);
				if (local68 > 65535) {
					local68 = 65535;
				}
				@Pc(145) int local145 = local51 * (local20 & 0xFF);
				if (local145 > 65535) {
					local145 = 65535;
				}
				local20 = (local68 & 0xFF00) + ((local119 << 8 & 0xFF00F4) + (local145 >> 8));
			}
		}
		return (local20 << 8) + 255 - (arg2 & 0xFF);
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IZ)V")
	private void method2799(@OriginalArg(1) boolean arg0) {
		@Pc(16) boolean local16 = this.colorsBuffer != null && this.colorsBuffer.anInterface11_7 == null;
		@Pc(27) boolean local27 = this.normalsBuffer != null && this.normalsBuffer.anInterface11_7 == null;
		@Pc(38) boolean local38 = this.vertexBuffer != null && this.vertexBuffer.anInterface11_7 == null;
		@Pc(49) boolean local49 = this.aClass177_9 != null && this.aClass177_9.anInterface11_7 == null;
		if (arg0) {
			local38 &= (this.renderState & 0x1) != 0;
			local27 &= (this.renderState & 0x4) != 0;
			local16 &= (this.renderState & 0x2) != 0;
			local49 &= (this.renderState & 0x8) != 0;
		}
		@Pc(106) byte local106 = 0;
		@Pc(110) byte local110 = 0;
		@Pc(116) byte local116 = 0;
		if (local38) {
			local106 = 12;
		}
		@Pc(127) byte local127 = 0;
		if (local16) {
			local110 = local106;
			local106 = (byte) (local106 + 4);
		}
		if (local27) {
			local116 = local106;
			local106 = (byte) (local106 + 12);
		}
		if (local49) {
			local127 = local106;
			local106 = (byte) (local106 + 8);
		}
		if (local106 == 0) {
			return;
		}
		if (SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer.length >= this.numNormals * local106) {
			SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = 0;
		} else {
			SpriteDrawQueue.aClass4_Sub12_Sub2_3 = new GlBitBuffer(local106 * (this.numNormals + 100));
		}
		@Pc(191) int local191;
		@Pc(200) int local200;
		@Pc(228) int local228;
		@Pc(237) int local237;
		if (local38) {
			@Pc(207) int local207;
			@Pc(214) int local214;
			@Pc(219) int local219;
			@Pc(226) int local226;
			if (this.glToolkit.bigEndianess) {
				for (local191 = 0; local191 < this.maxDepth; local191++) {
					local200 = Stream.floatToRawIntBits((float) this.anIntArray212[local191]);
					local207 = Stream.floatToRawIntBits((float) this.anIntArray216[local191]);
					local214 = Stream.floatToRawIntBits((float) this.anIntArray213[local191]);
					local219 = this.anIntArray214[local191];
					local226 = this.anIntArray214[local191 + 1];
					for (local228 = local219; local228 < local226; local228++) {
						local237 = this.aShortArray109[local228] - 1;
						if (local237 == -1) {
							break;
						}
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local106 * local237;
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local200);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local207);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local214);
					}
				}
			} else {
				for (local191 = 0; local191 < this.maxDepth; local191++) {
					local200 = Stream.floatToRawIntBits((float) this.anIntArray212[local191]);
					local207 = Stream.floatToRawIntBits((float) this.anIntArray216[local191]);
					local214 = Stream.floatToRawIntBits((float) this.anIntArray213[local191]);
					local219 = this.anIntArray214[local191];
					local226 = this.anIntArray214[local191 + 1];
					for (local228 = local219; local228 < local226; local228++) {
						local237 = this.aShortArray109[local228] - 1;
						if (local237 == -1) {
							break;
						}
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local237 * local106;
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2550(local200);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2550(local207);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2550(local214);
					}
				}
			}
		}
		@Pc(565) float local565;
		@Pc(451) short[] local451;
		@Pc(447) short[] local447;
		@Pc(439) short[] local439;
		@Pc(443) byte[] local443;
		@Pc(598) float local598;
		if (local16) {
			if (this.normalsBuffer == null) {
				if (this.mergedNormals == null) {
					local451 = this.aShortArray103;
					local447 = this.aShortArray101;
					local443 = this.aByteArray39;
					local439 = this.aShortArray102;
				} else {
					local439 = this.mergedNormals.aShortArray144;
					local443 = this.mergedNormals.magnitude;
					local447 = this.mergedNormals.aShortArray142;
					local451 = this.mergedNormals.aShortArray143;
				}
				@Pc(471) float local471 = this.glToolkit.sunDirection[0];
				@Pc(477) float local477 = this.glToolkit.sunDirection[1];
				@Pc(483) float local483 = this.glToolkit.sunDirection[2];
				@Pc(487) float local487 = this.glToolkit.sunBrightness;
				@Pc(497) float local497 = this.glToolkit.aFloat73 * 768.0F / (float) this.contrast;
				@Pc(507) float local507 = this.glToolkit.aFloat65 * 768.0F / (float) this.contrast;
				for (@Pc(509) int local509 = 0; local509 < this.numFaces; local509++) {
					@Pc(529) int local529 = this.method2798(this.aShortArray100[local509], this.ambient, this.aByteArray38[local509], this.aShortArray107[local509]);
					@Pc(534) short local534 = this.aShortArray108[local509];
					@Pc(545) float local545 = (float) (local529 >> 8 & 0xFF) * this.glToolkit.aFloat74;
					@Pc(554) float local554 = this.glToolkit.aFloat72 * (float) (local529 >>> 24);
					local565 = (float) (local529 >> 16 & 0xFF) * this.glToolkit.aFloat58;
					@Pc(570) short local570 = (short) local443[local534];
					if (local570 == 0) {
						local598 = (local483 * (float) local439[local534] + (float) local447[local534] * local477 + local471 * (float) local451[local534]) * 0.0026041667F;
					} else {
						local598 = ((float) local439[local534] * local483 + local471 * (float) local451[local534] + (float) local447[local534] * local477) / (float) (local570 * 256);
					}
					@Pc(638) float local638 = local487 + (local598 < 0.0F ? local507 : local497) * local598;
					@Pc(643) int local643 = (int) (local554 * local638);
					@Pc(648) int local648 = (int) (local638 * local565);
					if (local643 < 0) {
						local643 = 0;
					} else if (local643 > 255) {
						local643 = 255;
					}
					if (local648 < 0) {
						local648 = 0;
					} else if (local648 > 255) {
						local648 = 255;
					}
					@Pc(682) int local682 = (int) (local545 * local638);
					if (local682 < 0) {
						local682 = 0;
					} else if (local682 > 255) {
						local682 = 255;
					}
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local534 * local106 + local110;
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local643);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local648);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local682);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(255 - (this.aByteArray38[local509] & 0xFF));
					local534 = this.aShortArray105[local509];
					local570 = local443[local534];
					if (local570 == 0) {
						local598 = (local471 * (float) local451[local534] + (float) local447[local534] * local477 + local483 * (float) local439[local534]) * 0.0026041667F;
					} else {
						local598 = ((float) local439[local534] * local483 + (float) local451[local534] * local471 + local477 * (float) local447[local534]) / (float) (local570 * 256);
					}
					local638 = local598 * (local598 < 0.0F ? local507 : local497) + local487;
					local643 = (int) (local554 * local638);
					local648 = (int) (local565 * local638);
					if (local643 < 0) {
						local643 = 0;
					} else if (local643 > 255) {
						local643 = 255;
					}
					if (local648 < 0) {
						local648 = 0;
					} else if (local648 > 255) {
						local648 = 255;
					}
					local682 = (int) (local545 * local638);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local110 + local106 * local534;
					if (local682 < 0) {
						local682 = 0;
					} else if (local682 > 255) {
						local682 = 255;
					}
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local643);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local648);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local682);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(255 - (this.aByteArray38[local509] & 0xFF));
					local534 = this.aShortArray106[local509];
					local570 = local443[local534];
					if (local570 == 0) {
						local598 = ((float) local439[local534] * local483 + (float) local451[local534] * local471 + local477 * (float) local447[local534]) * 0.0026041667F;
					} else {
						local598 = (local483 * (float) local439[local534] + local471 * (float) local451[local534] + (float) local447[local534] * local477) / (float) (local570 * 256);
					}
					local638 = local487 + (local598 < 0.0F ? local507 : local497) * local598;
					local643 = (int) (local638 * local554);
					local648 = (int) (local565 * local638);
					if (local643 < 0) {
						local643 = 0;
					} else if (local643 > 255) {
						local643 = 255;
					}
					if (local648 < 0) {
						local648 = 0;
					} else if (local648 > 255) {
						local648 = 255;
					}
					local682 = (int) (local545 * local638);
					if (local682 < 0) {
						local682 = 0;
					} else if (local682 > 255) {
						local682 = 255;
					}
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local110 + local534 * local106;
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local643);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local648);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(local682);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2551(255 - (this.aByteArray38[local509] & 0xFF));
				}
			} else {
				for (local191 = 0; local191 < this.numFaces; local191++) {
					local200 = this.method2798(this.aShortArray100[local191], this.ambient, this.aByteArray38[local191], this.aShortArray107[local191]);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local110 + this.aShortArray108[local191] * local106;
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local200);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local110 + local106 * this.aShortArray105[local191];
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local200);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local106 * this.aShortArray106[local191] + local110;
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2531(local200);
				}
			}
		}
		if (local27) {
			if (this.mergedNormals == null) {
				local447 = this.aShortArray101;
				local443 = this.aByteArray39;
				local439 = this.aShortArray102;
				local451 = this.aShortArray103;
			} else {
				local439 = this.mergedNormals.aShortArray144;
				local447 = this.mergedNormals.aShortArray142;
				local443 = this.mergedNormals.magnitude;
				local451 = this.mergedNormals.aShortArray143;
			}
			@Pc(1102) float local1102 = 3.0F / (float) this.contrast;
			SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local116;
			local598 = 3.0F / (float) (this.contrast + this.contrast / 2);
			if (this.glToolkit.bigEndianess) {
				for (local228 = 0; local228 < this.numNormals; local228++) {
					local237 = local443[local228] & 0xFF;
					if (local237 == 0) {
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556(local598 * (float) local451[local228]);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556((float) local447[local228] * local598);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556((float) local439[local228] * local598);
					} else {
						local565 = local1102 / (float) local237;
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556((float) local451[local228] * local565);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556((float) local447[local228] * local565);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556((float) local439[local228] * local565);
					}
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition += local106 - 12;
				}
			} else {
				for (local228 = 0; local228 < this.numNormals; local228++) {
					local237 = local443[local228] & 0xFF;
					if (local237 == 0) {
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552((float) local451[local228] * local598);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552((float) local447[local228] * local598);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552((float) local439[local228] * local598);
					} else {
						local565 = local1102 / (float) local237;
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552(local565 * (float) local451[local228]);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552(local565 * (float) local447[local228]);
						SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552((float) local439[local228] * local565);
					}
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition += local106 - 12;
				}
			}
		}
		if (local49) {
			SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = local127;
			if (this.glToolkit.bigEndianess) {
				for (local191 = 0; local191 < this.numNormals; local191++) {
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556(this.aFloatArray21[local191]);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2556(this.aFloatArray20[local191]);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition += local106 - 8;
				}
			} else {
				for (local191 = 0; local191 < this.numNormals; local191++) {
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552(this.aFloatArray21[local191]);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2552(this.aFloatArray20[local191]);
					SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition += local106 - 8;
				}
			}
		}
		SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = this.numNormals * local106;
		@Pc(1410) ElementsBufferGL local1410;
		if (arg0) {
			if (this.anInterface11_4 == null) {
				this.anInterface11_4 = this.glToolkit.method1960(SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition, SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer, true, local106);
			} else {
				this.anInterface11_4.method5184(SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition, local106, SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer);
			}
			local1410 = this.anInterface11_4;
			this.renderState = 0;
		} else {
			local1410 = this.glToolkit.method1960(SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition, SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer, false, local106);
			this.aBoolean281 = true;
		}
		if (local38) {
			this.vertexBuffer.aByte55 = 0;
			this.vertexBuffer.anInterface11_7 = local1410;
		}
		if (local49) {
			this.aClass177_9.anInterface11_7 = local1410;
			this.aClass177_9.aByte55 = local127;
		}
		if (local16) {
			this.colorsBuffer.anInterface11_7 = local1410;
			this.colorsBuffer.aByte55 = local110;
		}
		if (local27) {
			this.normalsBuffer.aByte55 = local116;
			this.normalsBuffer.anInterface11_7 = local1410;
		}
	}

	@OriginalMember(owner = "client!ip", name = "aa", descriptor = "(I)V")
	@Override
	public void aa(@OriginalArg(0) int contrast) {
		if (this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
		this.contrast = (short) contrast;
		if (this.normalsBuffer != null) {
			this.normalsBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "w", descriptor = "()Z")
	@Override
	protected boolean w() {
		if (this.anIntArrayArray34 == null) {
			return false;
		}
		for (@Pc(12) int local12 = 0; local12 < this.numVertices; local12++) {
			this.anIntArray212[local12] <<= 0x4;
			this.anIntArray216[local12] <<= 0x4;
			this.anIntArray213[local12] <<= 0x4;
		}
		BillboardType.anInt3541 = 0;
		Class6_Sub2.anInt255 = 0;
		Class4_Sub6_Sub19.anInt3954 = 0;
		return true;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(Lclient!gm;I)V")
	private void method2800(@OriginalArg(0) GlShadow arg0) {
		if (Static231.anIntArray298.length < this.numNormals) {
			Static231.anIntArray298 = new int[this.numNormals];
			Static173.anIntArray219 = new int[this.numNormals];
		}
		@Pc(55) int local55;
		@Pc(94) int local94;
		@Pc(103) int local103;
		for (@Pc(28) int local28 = 0; local28 < this.maxDepth; local28++) {
			local55 = (this.anIntArray212[local28] - (this.glToolkit.sunSlopeZ * this.anIntArray216[local28] >> 8) >> this.glToolkit.shadowScaleShift) - arg0.anInt2727;
			@Pc(80) int local80 = (this.anIntArray213[local28] - (this.glToolkit.sunSlopeX * this.anIntArray216[local28] >> 8) >> this.glToolkit.shadowScaleShift) - arg0.anInt2725;
			@Pc(85) int local85 = this.anIntArray214[local28];
			@Pc(92) int local92 = this.anIntArray214[local28 + 1];
			for (local94 = local85; local94 < local92; local94++) {
				local103 = this.aShortArray109[local94] - 1;
				if (local103 == -1) {
					break;
				}
				Static231.anIntArray298[local103] = local55;
				Static173.anIntArray219[local103] = local80;
			}
		}
		for (local55 = 0; local55 < this.renderedFaceCount; local55++) {
			if (this.aByteArray38 == null || this.aByteArray38[local55] <= 128) {
				@Pc(150) short local150 = this.aShortArray108[local55];
				@Pc(155) short local155 = this.aShortArray105[local55];
				@Pc(160) short local160 = this.aShortArray106[local55];
				local94 = Static231.anIntArray298[local150];
				local103 = Static231.anIntArray298[local155];
				@Pc(172) int local172 = Static231.anIntArray298[local160];
				@Pc(176) int local176 = Static173.anIntArray219[local150];
				@Pc(180) int local180 = Static173.anIntArray219[local155];
				@Pc(184) int local184 = Static173.anIntArray219[local160];
				if ((local94 - local103) * (local180 - local184) - (local172 - local103) * (local180 - local176) > 0) {
					arg0.fillTriangle(local94, local180, local176, local103, local184, local172);
				}
			}
		}
	}

	@OriginalMember(owner = "client!ip", name = "b", descriptor = "(Z)V")
	private void method2801() {
		if (this.aClass115Array1 == null) {
			return;
		}
		@Pc(16) JavaMatrix local16 = this.glToolkit.aClass31_Sub1_2;
		@Pc(20) float local20 = this.glToolkit.xa();
		@Pc(24) float local24 = this.glToolkit.W();
		this.glToolkit.method1965();
		this.glToolkit.method4309(false);
		this.glToolkit.method1906(false);
		this.glToolkit.method1934(null, null, this.glToolkit.quadTexCoordBuffer, this.glToolkit.quadVertexBuffer);
		for (@Pc(51) int local51 = 0; local51 < this.billboardCount; local51++) {
			@Pc(58) NativeBillboardFace local58 = this.aClass115Array1[local51];
			@Pc(63) Class85 local63 = this.aClass85Array1[local51];
			if (!local58.aBoolean287 || !this.glToolkit.method4301()) {
				@Pc(91) float local91 = (float) (this.anIntArray212[local58.anInt3417] + this.anIntArray212[local58.anInt3422] + this.anIntArray212[local58.anInt3420]) * 0.3333333F;
				@Pc(112) float local112 = (float) (this.anIntArray216[local58.anInt3417] + this.anIntArray216[local58.anInt3422] + this.anIntArray216[local58.anInt3420]) * 0.3333333F;
				@Pc(134) float local134 = (float) (this.anIntArray213[local58.anInt3422] + this.anIntArray213[local58.anInt3420] + this.anIntArray213[local58.anInt3417]) * 0.3333333F;
				@Pc(148) float local148 = Class145_Sub1.aFloat222 + local112 * GameShell.aFloat30 + Class4_Sub6_Sub22.aFloat140 * local91 + Class50.aFloat113 * local134;
				@Pc(162) float local162 = local134 * Class249.aFloat213 + Class16_Sub5_Sub2.aFloat184 * local91 + local112 * CursorTypeList.aFloat163 + Static102.aFloat56;
				@Pc(176) float local176 = Terrain.aFloat223 + local134 * Class78_Sub1.aFloat126 + local91 * Class4_Sub45.aFloat227 + GameCanvas.aFloat189 * local112;
				local16.method5159(local63.anInt2745, local63.anInt2741 * local58.aShort34 >> 7, (float) local63.anInt2746 - local162, local58.aShort33 * local63.anInt2748 >> 7, (float) local63.anInt2749 + local148, -local176);
				this.glToolkit.method1942(local16);
				this.glToolkit.g(local24, local20 - (float) local58.anInt3418 * 1.5F);
				@Pc(226) int local226 = local63.anInt2743;
				OpenGL.glColor4ub((byte) (local226 >> 16), (byte) (local226 >> 8), (byte) local226, (byte) (local226 >> 24));
				this.glToolkit.method1957(local58.aShort35);
				this.glToolkit.method1958(local58.aByte20);
				this.glToolkit.method1900(local58.aByte21);
				this.glToolkit.method1925(4);
			}
		}
		this.glToolkit.g(local24, local20);
		this.glToolkit.method4309(true);
		this.glToolkit.method1916();
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(Lclient!c;Lclient!mi;II)V")
	@Override
	public void renderOrtho(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon cylinder, @OriginalArg(2) int zoom) {
		if (this.numNormals == 0) {
			return;
		}
		@Pc(13) JavaMatrix local13 = this.glToolkit.aClass31_Sub1_4;
		if (!this.boundsCalculated) {
			this.method2804();
		}
		@Pc(22) JavaMatrix local22 = (JavaMatrix) arg0;
		Terrain.aFloat223 = local13.aFloat199 * local22.aFloat198 + local13.aFloat203 * local22.aFloat205 + local13.aFloat206 * local22.aFloat209 + local13.aFloat198;
		GameCanvas.aFloat189 = local13.aFloat199 * local22.aFloat203 + local22.aFloat207 * local13.aFloat206 + local22.aFloat204 * local13.aFloat203;
		@Pc(69) float local69 = Terrain.aFloat223 + (float) this.lb * GameCanvas.aFloat189;
		@Pc(77) float local77 = (float) this.aShort28 * GameCanvas.aFloat189 + Terrain.aFloat223;
		@Pc(93) float local93;
		@Pc(87) float local87;
		if (local77 < local69) {
			local87 = local69 + (float) this.aShort26;
			local93 = local77 - (float) this.aShort26;
		} else {
			local87 = (float) this.aShort26 + local77;
			local93 = (float) -this.aShort26 + local69;
		}
		if (local93 >= this.glToolkit.farClipDistance || local87 <= (float) this.glToolkit.zNear) {
			return;
		}
		GameShell.aFloat30 = local13.aFloat202 * local22.aFloat207 + local22.aFloat204 * local13.aFloat207 + local13.aFloat208 * local22.aFloat203;
		Class145_Sub1.aFloat222 = local13.aFloat209 + local22.aFloat198 * local13.aFloat208 + local13.aFloat207 * local22.aFloat205 + local22.aFloat209 * local13.aFloat202;
		@Pc(170) float local170 = Class145_Sub1.aFloat222 + (float) this.lb * GameShell.aFloat30;
		@Pc(178) float local178 = GameShell.aFloat30 * (float) this.aShort28 + Class145_Sub1.aFloat222;
		@Pc(204) float local204;
		@Pc(193) float local193;
		if (local178 < local170) {
			local193 = (local170 + (float) this.aShort26) * (float) this.glToolkit.anInt2309;
			local204 = (float) this.glToolkit.anInt2309 * (local178 - (float) this.aShort26);
		} else {
			local193 = (float) this.glToolkit.anInt2309 * (local178 + (float) this.aShort26);
			local204 = (float) this.glToolkit.anInt2309 * ((float) -this.aShort26 + local170);
		}
		if (local204 / (float) zoom >= this.glToolkit.aFloat64 || local193 / (float) zoom <= this.glToolkit.aFloat75) {
			return;
		}
		CursorTypeList.aFloat163 = local13.aFloat200 * local22.aFloat207 + local13.aFloat204 * local22.aFloat204 + local13.aFloat201 * local22.aFloat203;
		Static102.aFloat56 = local13.aFloat205 + local22.aFloat205 * local13.aFloat204 + local22.aFloat209 * local13.aFloat200 + local13.aFloat201 * local22.aFloat198;
		@Pc(296) float local296 = Static102.aFloat56 + CursorTypeList.aFloat163 * (float) this.lb;
		@Pc(304) float local304 = Static102.aFloat56 + CursorTypeList.aFloat163 * (float) this.aShort28;
		@Pc(320) float local320;
		@Pc(331) float local331;
		if (local296 > local304) {
			local320 = ((float) -this.aShort26 + local304) * (float) this.glToolkit.anInt2294;
			local331 = ((float) this.aShort26 + local296) * (float) this.glToolkit.anInt2294;
		} else {
			local331 = (local304 + (float) this.aShort26) * (float) this.glToolkit.anInt2294;
			local320 = (local296 - (float) this.aShort26) * (float) this.glToolkit.anInt2294;
		}
		if (this.glToolkit.aFloat68 <= local320 / (float) zoom || this.glToolkit.aFloat62 >= local331 / (float) zoom) {
			return;
		}
		if (cylinder != null || this.aClass115Array1 != null) {
			Class78_Sub1.aFloat126 = local22.aFloat199 * local13.aFloat199 + local22.aFloat208 * local13.aFloat206 + local13.aFloat203 * local22.aFloat201;
			Class4_Sub6_Sub22.aFloat140 = local13.aFloat202 * local22.aFloat202 + local13.aFloat207 * local22.aFloat200 + local22.aFloat206 * local13.aFloat208;
			Class4_Sub45.aFloat227 = local22.aFloat200 * local13.aFloat203 + local13.aFloat206 * local22.aFloat202 + local13.aFloat199 * local22.aFloat206;
			Class16_Sub5_Sub2.aFloat184 = local22.aFloat206 * local13.aFloat201 + local22.aFloat202 * local13.aFloat200 + local22.aFloat200 * local13.aFloat204;
			Class249.aFloat213 = local22.aFloat201 * local13.aFloat204 + local13.aFloat200 * local22.aFloat208 + local13.aFloat201 * local22.aFloat199;
			Class50.aFloat113 = local13.aFloat208 * local22.aFloat199 + local22.aFloat208 * local13.aFloat202 + local13.aFloat207 * local22.aFloat201;
		}
		if (cylinder != null) {
			@Pc(498) int local498 = this.aShort31 + this.aShort29 >> 1;
			@Pc(507) int local507 = this.aShort27 + this.aShort25 >> 1;
			@Pc(526) int local526 = (int) (Class50.aFloat113 * (float) local507 + (float) this.lb * GameShell.aFloat30 + (float) local498 * Class4_Sub6_Sub22.aFloat140 + Class145_Sub1.aFloat222);
			@Pc(545) int local545 = (int) ((float) local498 * Class16_Sub5_Sub2.aFloat184 + Static102.aFloat56 + CursorTypeList.aFloat163 * (float) this.lb + Class249.aFloat213 * (float) local507);
			@Pc(564) int local564 = (int) ((float) local507 * Class78_Sub1.aFloat126 + (float) local498 * Class4_Sub45.aFloat227 + Terrain.aFloat223 + GameCanvas.aFloat189 * (float) this.lb);
			@Pc(583) int local583 = (int) ((float) local507 * Class50.aFloat113 + (float) local498 * Class4_Sub6_Sub22.aFloat140 + Class145_Sub1.aFloat222 + (float) this.aShort28 * GameShell.aFloat30);
			@Pc(602) int local602 = (int) (Static102.aFloat56 + Class16_Sub5_Sub2.aFloat184 * (float) local498 + CursorTypeList.aFloat163 * (float) this.aShort28 + (float) local507 * Class249.aFloat213);
			cylinder.anInt4542 = this.glToolkit.anInt2309 * local526 / zoom + this.glToolkit.anInt2295;
			cylinder.anInt4540 = local545 * this.glToolkit.anInt2294 / zoom + this.glToolkit.anInt2297;
			cylinder.anInt4543 = this.glToolkit.anInt2295 + local583 * this.glToolkit.anInt2309 / zoom;
			cylinder.anInt4541 = this.glToolkit.anInt2297 + local602 * this.glToolkit.anInt2294 / zoom;
			@Pc(674) int local674 = (int) ((float) this.aShort28 * GameCanvas.aFloat189 + Class4_Sub45.aFloat227 * (float) local498 + Terrain.aFloat223 + (float) local507 * Class78_Sub1.aFloat126);
			if (local564 < this.glToolkit.zNear && this.glToolkit.zNear > local674) {
				cylinder.anInt4544 = this.glToolkit.anInt2295 + this.glToolkit.anInt2309 * (this.aShort26 + local526) / zoom - cylinder.anInt4542;
				cylinder.positionCalculated = true;
			}
		}
		this.glToolkit.method1937((float) zoom);
		this.glToolkit.method1928();
		this.glToolkit.method1948(local22);
		this.method2797();
		this.glToolkit.method1916();
		this.method2801();
	}

	@OriginalMember(owner = "client!ip", name = "JA", descriptor = "(III)V")
	@Override
	public void drawScaledColored(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		for (@Pc(3) int local3 = 0; local3 < this.maxDepth; local3++) {
			if (arg0 != 0) {
				this.anIntArray212[local3] += arg0;
			}
			if (arg1 != 0) {
				this.anIntArray216[local3] += arg1;
			}
			if (arg2 != 0) {
				this.anIntArray213[local3] += arg2;
			}
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IILclient!ip;ZLclient!ip;Z)Lclient!t;")
	private Model method2802(@OriginalArg(1) int arg0, @OriginalArg(2) GlModel arg1, @OriginalArg(3) boolean arg2, @OriginalArg(4) GlModel arg3, @OriginalArg(5) boolean arg4) {
		arg3.ambient = this.ambient;
		arg3.numVertices = this.numVertices;
		arg3.maxDepth = this.maxDepth;
		arg3.billboardCount = this.billboardCount;
		arg3.preferenceFlags = this.preferenceFlags;
		arg3.functionMask = arg0;
		arg3.numNormals = this.numNormals;
		arg3.contrast = this.contrast;
		arg3.renderedFaceCount = this.renderedFaceCount;
		arg3.renderState = 0;
		arg3.numFaces = this.numFaces;
		@Pc(53) boolean local53 = Class16_Sub2_Sub3.method3594(this.preferenceFlags, arg0);
		@Pc(61) boolean local61 = Class4_Sub6_Sub15.method2975(arg0, this.preferenceFlags);
		@Pc(67) boolean local67 = Class58.method1510(arg0, this.preferenceFlags);
		@Pc(73) boolean local73 = local53 | local61 | local67;
		@Pc(184) int local184;
		if (local73) {
			if (!local53) {
				arg3.anIntArray212 = this.anIntArray212;
			} else if (arg1.anIntArray212 == null || arg1.anIntArray212.length < this.numVertices) {
				arg3.anIntArray212 = arg1.anIntArray212 = new int[this.numVertices];
			} else {
				arg3.anIntArray212 = arg1.anIntArray212;
			}
			if (!local61) {
				arg3.anIntArray216 = this.anIntArray216;
			} else if (arg1.anIntArray216 == null || arg1.anIntArray216.length < this.numVertices) {
				arg3.anIntArray216 = arg1.anIntArray216 = new int[this.numVertices];
			} else {
				arg3.anIntArray216 = arg1.anIntArray216;
			}
			if (!local67) {
				arg3.anIntArray213 = this.anIntArray213;
			} else if (arg1.anIntArray213 == null || arg1.anIntArray213.length < this.numVertices) {
				arg3.anIntArray213 = arg1.anIntArray213 = new int[this.numVertices];
			} else {
				arg3.anIntArray213 = arg1.anIntArray213;
			}
			for (local184 = 0; local184 < this.numVertices; local184++) {
				if (local53) {
					arg3.anIntArray212[local184] = this.anIntArray212[local184];
				}
				if (local61) {
					arg3.anIntArray216[local184] = this.anIntArray216[local184];
				}
				if (local67) {
					arg3.anIntArray213[local184] = this.anIntArray213[local184];
				}
			}
		} else {
			arg3.anIntArray213 = this.anIntArray213;
			arg3.anIntArray216 = this.anIntArray216;
			arg3.anIntArray212 = this.anIntArray212;
		}
		if (Class164.method3914(this.preferenceFlags, arg0)) {
			arg3.vertexBuffer = arg1.vertexBuffer;
			if (arg4) {
				arg3.renderState = (byte) (arg3.renderState | 0x1);
			}
			arg3.vertexBuffer.aByte55 = this.vertexBuffer.aByte55;
			arg3.vertexBuffer.anInterface11_7 = this.vertexBuffer.anInterface11_7;
		} else if (LocalRequester.method5437(this.preferenceFlags, arg0)) {
			arg3.vertexBuffer = this.vertexBuffer;
		} else {
			arg3.vertexBuffer = null;
		}
		if (OpenGlArbElementArrayBuffer.method1643(this.preferenceFlags, arg0)) {
			if (arg1.aShortArray100 == null || arg1.aShortArray100.length < this.numFaces) {
				arg3.aShortArray100 = arg1.aShortArray100 = new short[this.numFaces];
			} else {
				arg3.aShortArray100 = arg1.aShortArray100;
			}
			for (local184 = 0; local184 < this.numFaces; local184++) {
				arg3.aShortArray100[local184] = this.aShortArray100[local184];
			}
		} else {
			arg3.aShortArray100 = this.aShortArray100;
		}
		if (WorldMapElement.method934(arg0, this.preferenceFlags)) {
			if (arg1.aByteArray38 == null || this.numFaces > arg1.aByteArray38.length) {
				arg3.aByteArray38 = arg1.aByteArray38 = new byte[this.numFaces];
			} else {
				arg3.aByteArray38 = arg1.aByteArray38;
			}
			for (local184 = 0; local184 < this.numFaces; local184++) {
				arg3.aByteArray38[local184] = this.aByteArray38[local184];
			}
		} else {
			arg3.aByteArray38 = this.aByteArray38;
		}
		if (BufferedSocket.method137(this.preferenceFlags, arg0)) {
			if (arg4) {
				arg3.renderState = (byte) (arg3.renderState | 0x2);
			}
			arg3.colorsBuffer = arg1.colorsBuffer;
			arg3.colorsBuffer.aByte55 = this.colorsBuffer.aByte55;
			arg3.colorsBuffer.anInterface11_7 = this.colorsBuffer.anInterface11_7;
		} else if (Class4_Sub9.method760(this.preferenceFlags, arg0)) {
			arg3.colorsBuffer = this.colorsBuffer;
		} else {
			arg3.colorsBuffer = null;
		}
		@Pc(560) int local560;
		if (Class9_Sub3.method1353(this.preferenceFlags, arg0)) {
			if (arg1.aShortArray103 == null || arg1.aShortArray103.length < this.numNormals) {
				local184 = this.numNormals;
				arg3.aShortArray103 = arg1.aShortArray103 = new short[local184];
				arg3.aShortArray102 = arg1.aShortArray102 = new short[local184];
				arg3.aShortArray101 = arg1.aShortArray101 = new short[local184];
			} else {
				arg3.aShortArray103 = arg1.aShortArray103;
				arg3.aShortArray101 = arg1.aShortArray101;
				arg3.aShortArray102 = arg1.aShortArray102;
			}
			if (this.mergedNormals == null) {
				for (local184 = 0; local184 < this.numNormals; local184++) {
					arg3.aShortArray103[local184] = this.aShortArray103[local184];
					arg3.aShortArray101[local184] = this.aShortArray101[local184];
					arg3.aShortArray102[local184] = this.aShortArray102[local184];
				}
			} else {
				if (arg1.mergedNormals == null) {
					arg1.mergedNormals = new ModelNormalsGL();
				}
				@Pc(544) ModelNormalsGL local544 = arg3.mergedNormals = arg1.mergedNormals;
				if (local544.aShortArray143 == null || local544.aShortArray143.length < this.numNormals) {
					local560 = this.numNormals;
					local544.aShortArray143 = new short[local560];
					local544.magnitude = new byte[local560];
					local544.aShortArray144 = new short[local560];
					local544.aShortArray142 = new short[local560];
				}
				for (local560 = 0; local560 < this.numNormals; local560++) {
					arg3.aShortArray103[local560] = this.aShortArray103[local560];
					arg3.aShortArray101[local560] = this.aShortArray101[local560];
					arg3.aShortArray102[local560] = this.aShortArray102[local560];
					local544.aShortArray143[local560] = this.mergedNormals.aShortArray143[local560];
					local544.aShortArray142[local560] = this.mergedNormals.aShortArray142[local560];
					local544.aShortArray144[local560] = this.mergedNormals.aShortArray144[local560];
					local544.magnitude[local560] = this.mergedNormals.magnitude[local560];
				}
			}
			arg3.aByteArray39 = this.aByteArray39;
		} else {
			arg3.aByteArray39 = this.aByteArray39;
			arg3.aShortArray101 = this.aShortArray101;
			arg3.aShortArray102 = this.aShortArray102;
			arg3.mergedNormals = this.mergedNormals;
			arg3.aShortArray103 = this.aShortArray103;
		}
		if (FileOnDisk.method3468(this.preferenceFlags, arg0)) {
			if (arg4) {
				arg3.renderState = (byte) (arg3.renderState | 0x4);
			}
			arg3.normalsBuffer = arg1.normalsBuffer;
			arg3.normalsBuffer.anInterface11_7 = this.normalsBuffer.anInterface11_7;
			arg3.normalsBuffer.aByte55 = this.normalsBuffer.aByte55;
		} else if (Class4_Sub10.method2002(arg0, this.preferenceFlags)) {
			arg3.normalsBuffer = this.normalsBuffer;
		} else {
			arg3.normalsBuffer = null;
		}
		if (Static283.method3965(arg0, this.preferenceFlags)) {
			if (arg1.aFloatArray21 == null || arg1.aFloatArray21.length < this.numFaces) {
				local184 = this.numNormals;
				arg3.aFloatArray21 = arg1.aFloatArray21 = new float[local184];
				arg3.aFloatArray20 = arg1.aFloatArray20 = new float[local184];
			} else {
				arg3.aFloatArray20 = arg1.aFloatArray20;
				arg3.aFloatArray21 = arg1.aFloatArray21;
			}
			for (local184 = 0; local184 < this.numNormals; local184++) {
				arg3.aFloatArray21[local184] = this.aFloatArray21[local184];
				arg3.aFloatArray20[local184] = this.aFloatArray20[local184];
			}
		} else {
			arg3.aFloatArray21 = this.aFloatArray21;
			arg3.aFloatArray20 = this.aFloatArray20;
		}
		if (StackFrame.method303(arg0, this.preferenceFlags)) {
			if (arg4) {
				arg3.renderState = (byte) (arg3.renderState | 0x8);
			}
			arg3.aClass177_9 = arg1.aClass177_9;
			arg3.aClass177_9.aByte55 = this.aClass177_9.aByte55;
			arg3.aClass177_9.anInterface11_7 = this.aClass177_9.anInterface11_7;
		} else if (Class7.method87(arg0, this.preferenceFlags)) {
			arg3.aClass177_9 = this.aClass177_9;
		} else {
			arg3.aClass177_9 = null;
		}
		if (DequeIterator.method1613(arg0, this.preferenceFlags)) {
			if (arg1.aShortArray108 == null || arg1.aShortArray108.length < this.numFaces) {
				local184 = this.numFaces;
				arg3.aShortArray106 = arg1.aShortArray106 = new short[local184];
				arg3.aShortArray105 = arg1.aShortArray105 = new short[local184];
				arg3.aShortArray108 = arg1.aShortArray108 = new short[local184];
			} else {
				arg3.aShortArray106 = arg1.aShortArray106;
				arg3.aShortArray105 = arg1.aShortArray105;
				arg3.aShortArray108 = arg1.aShortArray108;
			}
			for (local184 = 0; local184 < this.numFaces; local184++) {
				arg3.aShortArray108[local184] = this.aShortArray108[local184];
				arg3.aShortArray105[local184] = this.aShortArray105[local184];
				arg3.aShortArray106[local184] = this.aShortArray106[local184];
			}
		} else {
			arg3.aShortArray105 = this.aShortArray105;
			arg3.aShortArray106 = this.aShortArray106;
			arg3.aShortArray108 = this.aShortArray108;
		}
		if (Class177.method4090(arg0, this.preferenceFlags)) {
			if (arg4) {
				arg3.renderState = (byte) (arg3.renderState | 0x10);
			}
			arg3.aClass230_1 = arg1.aClass230_1;
			arg3.aClass230_1.anInterface1_5 = this.aClass230_1.anInterface1_5;
		} else if (NamedFileReference.method5172(this.preferenceFlags, arg0)) {
			arg3.aClass230_1 = this.aClass230_1;
		} else {
			arg3.aClass230_1 = null;
		}
		if (Class120_Sub1.method4110(arg0, this.preferenceFlags)) {
			if (arg1.aShortArray107 == null || arg1.aShortArray107.length < this.numFaces) {
				local184 = this.numFaces;
				arg3.aShortArray107 = arg1.aShortArray107 = new short[local184];
			} else {
				arg3.aShortArray107 = arg1.aShortArray107;
			}
			for (local184 = 0; local184 < this.numFaces; local184++) {
				arg3.aShortArray107[local184] = this.aShortArray107[local184];
			}
		} else {
			arg3.aShortArray107 = this.aShortArray107;
		}
		if (!Class16_Sub4_Sub3.method3032(this.preferenceFlags, arg0)) {
			arg3.aClass85Array1 = this.aClass85Array1;
		} else if (arg1.aClass85Array1 == null || arg1.aClass85Array1.length < this.billboardCount) {
			local184 = this.billboardCount;
			arg3.aClass85Array1 = arg1.aClass85Array1 = new Class85[local184];
			for (local560 = 0; local560 < this.billboardCount; local560++) {
				arg3.aClass85Array1[local560] = this.aClass85Array1[local560].method2317();
			}
		} else {
			arg3.aClass85Array1 = arg1.aClass85Array1;
			for (local184 = 0; local184 < this.billboardCount; local184++) {
				arg3.aClass85Array1[local184].method2314(this.aClass85Array1[local184]);
			}
		}
		arg3.effectiveVertices = this.effectiveVertices;
		arg3.aClass115Array1 = this.aClass115Array1;
		arg3.aShortArray104 = this.aShortArray104;
		arg3.aShortArray109 = this.aShortArray109;
		arg3.faceGroups = this.faceGroups;
		arg3.anIntArray215 = this.anIntArray215;
		arg3.emissiveTriangles = this.emissiveTriangles;
		if (this.boundsCalculated) {
			arg3.lb = this.lb;
			arg3.aShort29 = this.aShort29;
			arg3.aShort31 = this.aShort31;
			arg3.aShort27 = this.aShort27;
			arg3.aShort26 = this.aShort26;
			arg3.boundsCalculated = true;
			arg3.aShort28 = this.aShort28;
			arg3.aShort25 = this.aShort25;
		} else {
			arg3.boundsCalculated = false;
		}
		arg3.anIntArrayArray32 = this.anIntArrayArray32;
		arg3.anIntArray214 = this.anIntArray214;
		arg3.anIntArrayArray34 = this.anIntArrayArray34;
		arg3.aShortArray99 = this.aShortArray99;
		return arg3;
	}

	@OriginalMember(owner = "client!ip", name = "H", descriptor = "(III)V")
	@Override
	public void H(@OriginalArg(0) int scaleX, @OriginalArg(1) int scaleY, @OriginalArg(2) int scaleZ) {
		for (@Pc(3) int local3 = 0; local3 < this.maxDepth; local3++) {
			if (scaleX != 128) {
				this.anIntArray212[local3] = this.anIntArray212[local3] * scaleX >> 7;
			}
			if (scaleY != 128) {
				this.anIntArray216[local3] = scaleY * this.anIntArray216[local3] >> 7;
			}
			if (scaleZ != 128) {
				this.anIntArray213[local3] = scaleZ * this.anIntArray213[local3] >> 7;
			}
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "M", descriptor = "(I[IIIIZI[I)V")
	@Override
	protected void M(@OriginalArg(0) int type, @OriginalArg(1) int[] labels, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) boolean lights, @OriginalArg(6) int submesh, @OriginalArg(7) int[] matrix) {
		@Pc(8) int local8 = labels.length;
		@Pc(31) int local31;
		@Pc(35) int local35;
		@Pc(63) int local63;
		@Pc(41) int local41;
		@Pc(57) int local57;
		@Pc(55) int[] local55;
		if (type == 0) {
			arg2 <<= 0x4;
			arg4 <<= 0x4;
			arg3 <<= 0x4;
			BillboardType.anInt3541 = 0;
			Class6_Sub2.anInt255 = 0;
			local31 = 0;
			Class4_Sub6_Sub19.anInt3954 = 0;
			for (local35 = 0; local35 < local8; local35++) {
				local41 = labels[local35];
				if (this.anIntArrayArray34.length > local41) {
					local55 = this.anIntArrayArray34[local41];
					for (local57 = 0; local57 < local55.length; local57++) {
						local63 = local55[local57];
						if (this.aShortArray99 == null || (this.aShortArray99[local63] & submesh) != 0) {
							Class6_Sub2.anInt255 += this.anIntArray212[local63];
							BillboardType.anInt3541 += this.anIntArray216[local63];
							Class4_Sub6_Sub19.anInt3954 += this.anIntArray213[local63];
							local31++;
						}
					}
				}
			}
			if (local31 > 0) {
				ProjectileAnimationNode.aBoolean178 = true;
				Class6_Sub2.anInt255 = arg2 + Class6_Sub2.anInt255 / local31;
				BillboardType.anInt3541 = BillboardType.anInt3541 / local31 + arg3;
				Class4_Sub6_Sub19.anInt3954 = Class4_Sub6_Sub19.anInt3954 / local31 + arg4;
			} else {
				BillboardType.anInt3541 = arg3;
				Class6_Sub2.anInt255 = arg2;
				Class4_Sub6_Sub19.anInt3954 = arg4;
			}
			return;
		}
		@Pc(248) int[] local248;
		@Pc(250) int local250;
		if (type == 1) {
			if (matrix != null) {
				local31 = matrix[0] * arg2 + arg3 * matrix[1] + matrix[2] * arg4 + 16384 >> 15;
				local35 = matrix[5] * arg4 + matrix[3] * arg2 + arg3 * matrix[4] + 16384 >> 15;
				local41 = matrix[6] * arg2 + arg3 * matrix[7] + matrix[8] * arg4 + 16384 >> 15;
				arg2 = local31;
				arg4 = local41;
				arg3 = local35;
			}
			arg4 <<= 0x4;
			arg3 <<= 0x4;
			arg2 <<= 0x4;
			for (local31 = 0; local31 < local8; local31++) {
				local35 = labels[local31];
				if (local35 < this.anIntArrayArray34.length) {
					local248 = this.anIntArrayArray34[local35];
					for (local250 = 0; local250 < local248.length; local250++) {
						local57 = local248[local250];
						if (this.aShortArray99 == null || (submesh & this.aShortArray99[local57]) != 0) {
							this.anIntArray212[local57] += arg2;
							this.anIntArray216[local57] += arg3;
							this.anIntArray213[local57] += arg4;
						}
					}
				}
			}
			return;
		}
		@Pc(370) int local370;
		@Pc(392) int local392;
		@Pc(422) int local422;
		@Pc(449) int local449;
		@Pc(453) int local453;
		@Pc(457) int local457;
		@Pc(461) int local461;
		@Pc(469) int local469;
		@Pc(477) int local477;
		@Pc(631) int local631;
		@Pc(657) int local657;
		@Pc(661) int local661;
		@Pc(670) int local670;
		@Pc(675) int local675;
		@Pc(679) int local679;
		@Pc(683) int local683;
		@Pc(685) int local685;
		@Pc(813) int[] local813;
		@Pc(815) int local815;
		@Pc(819) int local819;
		@Pc(823) int local823;
		@Pc(825) int local825;
		@Pc(956) int local956;
		if (type == 2) {
			if (matrix == null) {
				for (local31 = 0; local31 < local8; local31++) {
					local35 = labels[local31];
					if (this.anIntArrayArray34.length > local35) {
						local248 = this.anIntArrayArray34[local35];
						for (local250 = 0; local250 < local248.length; local250++) {
							local57 = local248[local250];
							if (this.aShortArray99 == null || (submesh & this.aShortArray99[local57]) != 0) {
								this.anIntArray212[local57] -= Class6_Sub2.anInt255;
								this.anIntArray216[local57] -= BillboardType.anInt3541;
								this.anIntArray213[local57] -= Class4_Sub6_Sub19.anInt3954;
								if (arg4 != 0) {
									local63 = Class50_Sub1.anIntArray225[arg4];
									local370 = Class50_Sub1.anIntArray224[arg4];
									local392 = this.anIntArray216[local57] * local63 + this.anIntArray212[local57] * local370 + 32767 >> 15;
									this.anIntArray216[local57] = local370 * this.anIntArray216[local57] + 32767 - this.anIntArray212[local57] * local63 >> 15;
									this.anIntArray212[local57] = local392;
								}
								if (arg2 != 0) {
									local63 = Class50_Sub1.anIntArray225[arg2];
									local370 = Class50_Sub1.anIntArray224[arg2];
									local392 = local370 * this.anIntArray216[local57] + 32767 - this.anIntArray213[local57] * local63 >> 15;
									this.anIntArray213[local57] = this.anIntArray213[local57] * local370 + local63 * this.anIntArray216[local57] + 32767 >> 15;
									this.anIntArray216[local57] = local392;
								}
								if (arg3 != 0) {
									local63 = Class50_Sub1.anIntArray225[arg3];
									local370 = Class50_Sub1.anIntArray224[arg3];
									local392 = local63 * this.anIntArray213[local57] + this.anIntArray212[local57] * local370 + 32767 >> 15;
									this.anIntArray213[local57] = local370 * this.anIntArray213[local57] + 32767 - local63 * this.anIntArray212[local57] >> 15;
									this.anIntArray212[local57] = local392;
								}
								this.anIntArray212[local57] += Class6_Sub2.anInt255;
								this.anIntArray216[local57] += BillboardType.anInt3541;
								this.anIntArray213[local57] += Class4_Sub6_Sub19.anInt3954;
							}
						}
					}
				}
				if (lights) {
					for (local35 = 0; local35 < local8; local35++) {
						local41 = labels[local35];
						if (local41 < this.anIntArrayArray34.length) {
							local55 = this.anIntArrayArray34[local41];
							for (local57 = 0; local57 < local55.length; local57++) {
								local63 = local55[local57];
								if (this.aShortArray99 == null || (submesh & this.aShortArray99[local63]) != 0) {
									local370 = this.anIntArray214[local63];
									local392 = this.anIntArray214[local63 + 1];
									for (local422 = local370; local422 < local392; local422++) {
										local449 = this.aShortArray109[local422] - 1;
										if (local449 == -1) {
											break;
										}
										if (arg4 != 0) {
											local453 = Class50_Sub1.anIntArray225[arg4];
											local457 = Class50_Sub1.anIntArray224[arg4];
											local461 = this.aShortArray103[local449] * local457 + local453 * this.aShortArray101[local449] + 32767 >> 15;
											this.aShortArray101[local449] = (short) (this.aShortArray101[local449] * local457 + 32767 - local453 * this.aShortArray103[local449] >> 15);
											this.aShortArray103[local449] = (short) local461;
										}
										if (arg2 != 0) {
											local453 = Class50_Sub1.anIntArray225[arg2];
											local457 = Class50_Sub1.anIntArray224[arg2];
											local461 = local457 * this.aShortArray101[local449] + 32767 - local453 * this.aShortArray102[local449] >> 15;
											this.aShortArray102[local449] = (short) (this.aShortArray101[local449] * local453 + local457 * this.aShortArray102[local449] + 32767 >> 15);
											this.aShortArray101[local449] = (short) local461;
										}
										if (arg3 != 0) {
											local453 = Class50_Sub1.anIntArray225[arg3];
											local457 = Class50_Sub1.anIntArray224[arg3];
											local461 = local457 * this.aShortArray103[local449] + this.aShortArray102[local449] * local453 + 32767 >> 15;
											this.aShortArray102[local449] = (short) (this.aShortArray102[local449] * local457 + 32767 - this.aShortArray103[local449] * local453 >> 15);
											this.aShortArray103[local449] = (short) local461;
										}
									}
								}
							}
						}
					}
					if (this.normalsBuffer == null && this.colorsBuffer != null) {
						this.colorsBuffer.anInterface11_7 = null;
					}
					if (this.normalsBuffer != null) {
						this.normalsBuffer.anInterface11_7 = null;
					}
				}
			} else {
				local31 = matrix[9] << 4;
				local35 = matrix[10] << 4;
				local41 = matrix[11] << 4;
				local250 = matrix[12] << 4;
				local57 = matrix[13] << 4;
				local63 = matrix[14] << 4;
				if (ProjectileAnimationNode.aBoolean178) {
					local370 = Class6_Sub2.anInt255 * matrix[0] + matrix[3] * BillboardType.anInt3541 + Class4_Sub6_Sub19.anInt3954 * matrix[6] + 16384 >> 15;
					local392 = Class4_Sub6_Sub19.anInt3954 * matrix[7] + Class6_Sub2.anInt255 * matrix[1] + BillboardType.anInt3541 * matrix[4] + 16384 >> 15;
					local392 += local57;
					local370 += local250;
					local422 = BillboardType.anInt3541 * matrix[5] + matrix[2] * Class6_Sub2.anInt255 + Class4_Sub6_Sub19.anInt3954 * matrix[8] + 16384 >> 15;
					local422 += local63;
					Class6_Sub2.anInt255 = local370;
					BillboardType.anInt3541 = local392;
					ProjectileAnimationNode.aBoolean178 = false;
					Class4_Sub6_Sub19.anInt3954 = local422;
				}
				@Pc(437) int[] local437 = new int[9];
				local392 = Class50_Sub1.anIntArray224[arg2];
				local422 = Class50_Sub1.anIntArray225[arg2];
				local449 = Class50_Sub1.anIntArray224[arg3];
				local453 = Class50_Sub1.anIntArray225[arg3];
				local457 = Class50_Sub1.anIntArray224[arg4];
				local461 = Class50_Sub1.anIntArray225[arg4];
				local469 = local457 * local422 + 16384 >> 15;
				local477 = local461 * local422 + 16384 >> 15;
				local437[3] = local461 * local392 + 16384 >> 15;
				local437[6] = local449 * local477 + local457 * -local453 + 16384 >> 15;
				local437[5] = -local422;
				local437[2] = local392 * local453 + 16384 >> 15;
				local437[8] = local449 * local392 + 16384 >> 15;
				local437[1] = local453 * local469 + -local449 * local461 + 16384 >> 15;
				local437[7] = local461 * local453 + local469 * local449 + 16384 >> 15;
				local437[0] = local477 * local453 + local449 * local457 + 16384 >> 15;
				local437[4] = local392 * local457 + 16384 >> 15;
				@Pc(606) int local606 = local437[1] * -BillboardType.anInt3541 + -Class6_Sub2.anInt255 * local437[0] + -Class4_Sub6_Sub19.anInt3954 * local437[2] + 16384 >> 15;
				local631 = -Class4_Sub6_Sub19.anInt3954 * local437[5] + local437[4] * -BillboardType.anInt3541 + local437[3] * -Class6_Sub2.anInt255 + 16384 >> 15;
				local657 = local437[6] * -Class6_Sub2.anInt255 + local437[7] * -BillboardType.anInt3541 + -Class4_Sub6_Sub19.anInt3954 * local437[8] + 16384 >> 15;
				local661 = Class6_Sub2.anInt255 + local606;
				@Pc(665) int local665 = local631 + BillboardType.anInt3541;
				local670 = local657 + Class4_Sub6_Sub19.anInt3954;
				@Pc(673) int[] local673 = new int[9];
				for (local675 = 0; local675 < 3; local675++) {
					for (local679 = 0; local679 < 3; local679++) {
						local683 = 0;
						for (local685 = 0; local685 < 3; local685++) {
							local683 += local437[local685 + local675 * 3] * matrix[local685 + local679 * 3];
						}
						local673[local675 * 3 + local679] = local683 + 16384 >> 15;
					}
				}
				local679 = local63 * local437[2] + local437[1] * local57 + local437[0] * local250 + 16384 >> 15;
				local683 = local63 * local437[5] + local437[3] * local250 + local437[4] * local57 + 16384 >> 15;
				local679 += local661;
				local683 += local665;
				local685 = local57 * local437[7] + local250 * local437[6] + local437[8] * local63 + 16384 >> 15;
				local685 += local670;
				local813 = new int[9];
				for (local815 = 0; local815 < 3; local815++) {
					for (local819 = 0; local819 < 3; local819++) {
						local823 = 0;
						for (local825 = 0; local825 < 3; local825++) {
							local823 += local673[local819 + local825 * 3] * matrix[local825 + local815 * 3];
						}
						local813[local819 + local815 * 3] = local823 + 16384 >> 15;
					}
				}
				local819 = matrix[1] * local683 + matrix[0] * local679 + local685 * matrix[2] + 16384 >> 15;
				local823 = matrix[3] * local679 + local683 * matrix[4] + matrix[5] * local685 + 16384 >> 15;
				local825 = local685 * matrix[8] + matrix[6] * local679 + matrix[7] * local683 + 16384 >> 15;
				local823 += local35;
				local819 += local31;
				local825 += local41;
				for (local956 = 0; local956 < local8; local956++) {
					@Pc(962) int local962 = labels[local956];
					if (local962 < this.anIntArrayArray34.length) {
						@Pc(976) int[] local976 = this.anIntArrayArray34[local962];
						for (@Pc(978) int local978 = 0; local978 < local976.length; local978++) {
							@Pc(984) int local984 = local976[local978];
							if (this.aShortArray99 == null || (this.aShortArray99[local984] & submesh) != 0) {
								@Pc(1028) int local1028 = this.anIntArray213[local984] * local813[2] + this.anIntArray216[local984] * local813[1] + this.anIntArray212[local984] * local813[0] + 16384 >> 15;
								@Pc(1059) int local1059 = local813[5] * this.anIntArray213[local984] + local813[4] * this.anIntArray216[local984] + this.anIntArray212[local984] * local813[3] + 16384 >> 15;
								@Pc(1063) int local1063 = local1028 + local819;
								@Pc(1067) int local1067 = local1059 + local823;
								@Pc(1099) int local1099 = this.anIntArray213[local984] * local813[8] + this.anIntArray212[local984] * local813[6] + local813[7] * this.anIntArray216[local984] + 16384 >> 15;
								@Pc(1103) int local1103 = local1099 + local825;
								this.anIntArray212[local984] = local1063;
								this.anIntArray216[local984] = local1067;
								this.anIntArray213[local984] = local1103;
							}
						}
					}
				}
			}
		} else if (type != 3) {
			@Pc(2609) NativeBillboardFace local2609;
			@Pc(2614) Class85 local2614;
			if (type == 5) {
				if (this.faceGroups != null) {
					for (local31 = 0; local31 < local8; local31++) {
						local35 = labels[local31];
						if (local35 < this.faceGroups.length) {
							local248 = this.faceGroups[local35];
							for (local250 = 0; local250 < local248.length; local250++) {
								local57 = local248[local250];
								if (this.aShortArray104 == null || (this.aShortArray104[local57] & submesh) != 0) {
									local63 = (this.aByteArray38[local57] & 0xFF) + (arg2 * 8);
									if (local63 < 0) {
										local63 = 0;
									} else if (local63 > 255) {
										local63 = 255;
									}
									this.aByteArray38[local57] = (byte) local63;
									if (this.colorsBuffer != null) {
										this.colorsBuffer.anInterface11_7 = null;
									}
								}
							}
						}
					}
					if (this.aClass115Array1 != null) {
						for (local35 = 0; local35 < this.billboardCount; local35++) {
							local2609 = this.aClass115Array1[local35];
							local2614 = this.aClass85Array1[local35];
							local2614.anInt2743 = 255 - (this.aByteArray38[local2609.anInt3416] & 0xFF) << 24 | local2614.anInt2743 & 0xFFFFFF;
						}
					}
				}
			} else if (type != 7) {
				@Pc(2864) Class85 local2864;
				if (type == 8) {
					if (this.anIntArrayArray32 != null) {
						for (local31 = 0; local31 < local8; local31++) {
							local35 = labels[local31];
							if (local35 < this.anIntArrayArray32.length) {
								local248 = this.anIntArrayArray32[local35];
								for (local250 = 0; local250 < local248.length; local250++) {
									local2864 = this.aClass85Array1[local248[local250]];
									local2864.anInt2746 += arg3;
									local2864.anInt2749 += arg2;
								}
							}
						}
					}
				} else if (type == 10) {
					if (this.anIntArrayArray32 != null) {
						for (local31 = 0; local31 < local8; local31++) {
							local35 = labels[local31];
							if (this.anIntArrayArray32.length > local35) {
								local248 = this.anIntArrayArray32[local35];
								for (local250 = 0; local250 < local248.length; local250++) {
									local2864 = this.aClass85Array1[local248[local250]];
									local2864.anInt2748 = local2864.anInt2748 * arg3 >> 7;
									local2864.anInt2741 = local2864.anInt2741 * arg2 >> 7;
								}
							}
						}
					}
				} else if (type == 9 && this.anIntArrayArray32 != null) {
					for (local31 = 0; local31 < local8; local31++) {
						local35 = labels[local31];
						if (this.anIntArrayArray32.length > local35) {
							local248 = this.anIntArrayArray32[local35];
							for (local250 = 0; local250 < local248.length; local250++) {
								local2864 = this.aClass85Array1[local248[local250]];
								local2864.anInt2745 = arg2 + local2864.anInt2745 & 0x3FFF;
							}
						}
					}
				}
			} else if (this.faceGroups != null) {
				for (local31 = 0; local31 < local8; local31++) {
					local35 = labels[local31];
					if (this.faceGroups.length > local35) {
						local248 = this.faceGroups[local35];
						for (local250 = 0; local250 < local248.length; local250++) {
							local57 = local248[local250];
							if (this.aShortArray104 == null || (submesh & this.aShortArray104[local57]) != 0) {
								local63 = this.aShortArray100[local57] & 0xFFFF;
								local370 = local63 >> 10 & 0x3F;
								local392 = local63 >> 7 & 0x7;
								@Pc(2710) int local2710 = arg2 + local370 & 0x3F;
								local392 += arg3 / 4;
								local422 = local63 & 0x7F;
								if (local392 < 0) {
									local392 = 0;
								} else if (local392 > 7) {
									local392 = 7;
								}
								local422 += arg4;
								if (local422 < 0) {
									local422 = 0;
								} else if (local422 > 127) {
									local422 = 127;
								}
								this.aShortArray100[local57] = (short) (local422 | local392 << 7 | local2710 << 10);
								if (this.colorsBuffer != null) {
									this.colorsBuffer.anInterface11_7 = null;
								}
							}
						}
					}
				}
				if (this.aClass115Array1 != null) {
					for (local35 = 0; local35 < this.billboardCount; local35++) {
						local2609 = this.aClass115Array1[local35];
						local2614 = this.aClass85Array1[local35];
						local2614.anInt2743 = local2614.anInt2743 & 0xFF000000 | Class9_Sub3.anIntArray115[this.aShortArray100[local2609.anInt3416] & 0xFFFF] & 0xFFFFFF;
					}
				}
			}
		} else if (matrix == null) {
			for (local31 = 0; local31 < local8; local31++) {
				local35 = labels[local31];
				if (this.anIntArrayArray34.length > local35) {
					local248 = this.anIntArrayArray34[local35];
					for (local250 = 0; local250 < local248.length; local250++) {
						local57 = local248[local250];
						if (this.aShortArray99 == null || (submesh & this.aShortArray99[local57]) != 0) {
							this.anIntArray212[local57] -= Class6_Sub2.anInt255;
							this.anIntArray216[local57] -= BillboardType.anInt3541;
							this.anIntArray213[local57] -= Class4_Sub6_Sub19.anInt3954;
							this.anIntArray212[local57] = this.anIntArray212[local57] * arg2 >> 7;
							this.anIntArray216[local57] = this.anIntArray216[local57] * arg3 >> 7;
							this.anIntArray213[local57] = this.anIntArray213[local57] * arg4 >> 7;
							this.anIntArray212[local57] += Class6_Sub2.anInt255;
							this.anIntArray216[local57] += BillboardType.anInt3541;
							this.anIntArray213[local57] += Class4_Sub6_Sub19.anInt3954;
						}
					}
				}
			}
		} else {
			local31 = matrix[9] << 4;
			local35 = matrix[10] << 4;
			local41 = matrix[11] << 4;
			local250 = matrix[12] << 4;
			local57 = matrix[13] << 4;
			local63 = matrix[14] << 4;
			if (ProjectileAnimationNode.aBoolean178) {
				local370 = matrix[6] * Class4_Sub6_Sub19.anInt3954 + matrix[3] * BillboardType.anInt3541 + Class6_Sub2.anInt255 * matrix[0] + 16384 >> 15;
				local392 = matrix[4] * BillboardType.anInt3541 + matrix[1] * Class6_Sub2.anInt255 + matrix[7] * Class4_Sub6_Sub19.anInt3954 + 16384 >> 15;
				local370 += local250;
				local392 += local57;
				local422 = Class6_Sub2.anInt255 * matrix[2] + BillboardType.anInt3541 * matrix[5] + Class4_Sub6_Sub19.anInt3954 * matrix[8] + 16384 >> 15;
				local422 += local63;
				BillboardType.anInt3541 = local392;
				Class6_Sub2.anInt255 = local370;
				ProjectileAnimationNode.aBoolean178 = false;
				Class4_Sub6_Sub19.anInt3954 = local422;
			}
			local370 = arg2 << 15 >> 7;
			local392 = arg3 << 15 >> 7;
			local422 = arg4 << 15 >> 7;
			local449 = local370 * -Class6_Sub2.anInt255 + 16384 >> 15;
			local453 = -BillboardType.anInt3541 * local392 + 16384 >> 15;
			local457 = -Class4_Sub6_Sub19.anInt3954 * local422 + 16384 >> 15;
			local461 = Class6_Sub2.anInt255 + local449;
			local469 = local453 + BillboardType.anInt3541;
			local477 = local457 + Class4_Sub6_Sub19.anInt3954;
			@Pc(1888) int[] local1888 = new int[] { local370 * matrix[0] + 16384 >> 15, matrix[3] * local370 + 16384 >> 15, local370 * matrix[6] + 16384 >> 15, matrix[1] * local392 + 16384 >> 15, matrix[4] * local392 + 16384 >> 15, matrix[7] * local392 + 16384 >> 15, local422 * matrix[2] + 16384 >> 15, local422 * matrix[5] + 16384 >> 15, local422 * matrix[8] + 16384 >> 15 };
			local631 = local250 * local370 + 16384 >> 15;
			local657 = local392 * local57 + 16384 >> 15;
			local661 = local63 * local422 + 16384 >> 15;
			@Pc(2024) int local2024 = local657 + local469;
			@Pc(2028) int local2028 = local631 + local461;
			@Pc(2032) int local2032 = local661 + local477;
			@Pc(2035) int[] local2035 = new int[9];
			@Pc(2041) int local2041;
			for (local670 = 0; local670 < 3; local670++) {
				for (local2041 = 0; local2041 < 3; local2041++) {
					local675 = 0;
					for (local679 = 0; local679 < 3; local679++) {
						local675 += matrix[local670 * 3 + local679] * local1888[local2041 + local679 * 3];
					}
					local2035[local670 * 3 + local2041] = local675 + 16384 >> 15;
				}
			}
			local2041 = local2028 * matrix[0] + local2024 * matrix[1] + local2032 * matrix[2] + 16384 >> 15;
			local675 = matrix[5] * local2032 + matrix[4] * local2024 + matrix[3] * local2028 + 16384 >> 15;
			local675 += local35;
			local2041 += local31;
			local679 = local2028 * matrix[6] + matrix[7] * local2024 + matrix[8] * local2032 + 16384 >> 15;
			local679 += local41;
			for (local683 = 0; local683 < local8; local683++) {
				local685 = labels[local683];
				if (this.anIntArrayArray34.length > local685) {
					local813 = this.anIntArrayArray34[local685];
					for (local815 = 0; local815 < local813.length; local815++) {
						local819 = local813[local815];
						if (this.aShortArray99 == null || (submesh & this.aShortArray99[local819]) != 0) {
							local823 = this.anIntArray212[local819] * local2035[0] + this.anIntArray216[local819] * local2035[1] + this.anIntArray213[local819] * local2035[2] + 16384 >> 15;
							local825 = local2035[3] * this.anIntArray212[local819] + local2035[4] * this.anIntArray216[local819] + local2035[5] * this.anIntArray213[local819] + 16384 >> 15;
							local956 = this.anIntArray213[local819] * local2035[8] + this.anIntArray216[local819] * local2035[7] + local2035[6] * this.anIntArray212[local819] + 16384 >> 15;
							@Pc(2318) int local2318 = local825 + local675;
							@Pc(2322) int local2322 = local823 + local2041;
							@Pc(2326) int local2326 = local956 + local679;
							this.anIntArray212[local819] = local2322;
							this.anIntArray216[local819] = local2318;
							this.anIntArray213[local819] = local2326;
						}
					}
				}
			}
		}
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "()[Lclient!bu;")
	@Override
	public EmissiveTriangle[] getEmissiveTriangles() {
		return this.emissiveTriangles;
	}

	@OriginalMember(owner = "client!ip", name = "E", descriptor = "(I)V")
	@Override
	public void E(@OriginalArg(0) int ambient) {
		if (this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
		this.ambient = (short) ambient;
	}

	@OriginalMember(owner = "client!ip", name = "Q", descriptor = "(I)V")
	@Override
	public void Q(@OriginalArg(0) int angle) {
		@Pc(9) int local9 = Class50_Sub1.anIntArray225[angle];
		@Pc(13) int local13 = Class50_Sub1.anIntArray224[angle];
		for (@Pc(15) int local15 = 0; local15 < this.maxDepth; local15++) {
			@Pc(34) int local34 = local9 * this.anIntArray213[local15] + this.anIntArray212[local15] * local13 >> 15;
			this.anIntArray213[local15] = this.anIntArray213[local15] * local13 - this.anIntArray212[local15] * local9 >> 15;
			this.anIntArray212[local15] = local34;
		}
		this.boundsCalculated = false;
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(BIZ)Lclient!t;")
	@Override
	public Model copy(@OriginalArg(0) byte copyType, @OriginalArg(1) int functionMask, @OriginalArg(2) boolean ensureLit) {
		@Pc(14) GlModel local14;
		@Pc(18) GlModel local18;
		if (copyType == 1) {
			local14 = this.glToolkit.copyTarget1;
			local18 = this.glToolkit.aClass110_Sub1_1;
		} else if (copyType == 2) {
			local14 = this.glToolkit.aClass110_Sub1_3;
			local18 = this.glToolkit.aClass110_Sub1_5;
		} else if (copyType == 3) {
			local18 = this.glToolkit.aClass110_Sub1_10;
			local14 = this.glToolkit.aClass110_Sub1_9;
		} else if (copyType == 4) {
			local18 = this.glToolkit.aClass110_Sub1_6;
			local14 = this.glToolkit.aClass110_Sub1_2;
		} else if (copyType == 5) {
			local14 = this.glToolkit.aClass110_Sub1_8;
			local18 = this.glToolkit.aClass110_Sub1_4;
		} else {
			local14 = local18 = new GlModel(this.glToolkit);
		}
		return this.method2802(functionMask, local18, ensureLit, local14, copyType != 0);
	}

	@OriginalMember(owner = "client!ip", name = "R", descriptor = "(I)V")
	@Override
	public void R(@OriginalArg(0) int arg0) {
		@Pc(9) int local9 = Class50_Sub1.anIntArray225[arg0];
		@Pc(13) int local13 = Class50_Sub1.anIntArray224[arg0];
		for (@Pc(15) int local15 = 0; local15 < this.maxDepth; local15++) {
			@Pc(33) int local33 = local13 * this.anIntArray212[local15] + this.anIntArray216[local15] * local9 >> 15;
			this.anIntArray216[local15] = local13 * this.anIntArray216[local15] - this.anIntArray212[local15] * local9 >> 15;
			this.anIntArray212[local15] = local33;
		}
		this.boundsCalculated = false;
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IJFFIBIILclient!jq;I)S")
	private short method2803(@OriginalArg(0) int arg0, @OriginalArg(1) long arg1, @OriginalArg(2) float arg2, @OriginalArg(3) float arg3, @OriginalArg(4) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) Mesh arg7, @OriginalArg(9) int arg8) {
		@Pc(10) int local10 = this.anIntArray214[arg6];
		@Pc(17) int local17 = this.anIntArray214[arg6 + 1];
		@Pc(24) int local24 = 0;
		for (@Pc(26) int local26 = local10; local26 < local17; local26++) {
			@Pc(33) short local33 = this.aShortArray109[local26];
			if (local33 == 0) {
				local24 = local26;
				break;
			}
			if (PingWorker.aLongArray9[local26] == arg1) {
				return (short) (local33 - 1);
			}
		}
		this.aShortArray109[local24] = (short) (this.numNormals + 1);
		PingWorker.aLongArray9[local24] = arg1;
		this.aShortArray103[this.numNormals] = (short) arg8;
		this.aShortArray101[this.numNormals] = (short) arg5;
		this.aShortArray102[this.numNormals] = (short) arg0;
		this.aByteArray39[this.numNormals] = (byte) arg4;
		this.aFloatArray21[this.numNormals] = arg3;
		this.aFloatArray20[this.numNormals] = arg2;
		return (short) this.numNormals++;
	}

	@OriginalMember(owner = "client!ip", name = "IA", descriptor = "()V")
	@Override
	public void IA() {
		for (@Pc(7) int local7 = 0; local7 < this.maxDepth; local7++) {
			this.anIntArray213[local7] = -this.anIntArray213[local7];
		}
		for (@Pc(25) int local25 = 0; local25 < this.numNormals; local25++) {
			this.aShortArray102[local25] = (short) -this.aShortArray102[local25];
		}
		for (@Pc(48) int local48 = 0; local48 < this.numFaces; local48++) {
			@Pc(55) short local55 = this.aShortArray108[local48];
			this.aShortArray108[local48] = this.aShortArray106[local48];
			this.aShortArray106[local48] = local55;
		}
		if (this.normalsBuffer == null && this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
		if (this.normalsBuffer != null) {
			this.normalsBuffer.anInterface11_7 = null;
		}
		if (this.vertexBuffer != null) {
			this.vertexBuffer.anInterface11_7 = null;
		}
		if (this.aClass230_1 != null) {
			this.aClass230_1.anInterface1_5 = null;
		}
		this.boundsCalculated = false;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(Lclient!c;Lclient!mi;I)V")
	@Override
	public void render(@OriginalArg(0) Ground arg0, @OriginalArg(1) BoundingBoxPolygon cylinder, @OriginalArg(2) int flags) {
		if (this.numNormals == 0) {
			return;
		}
		@Pc(13) JavaMatrix local13 = this.glToolkit.aClass31_Sub1_4;
		@Pc(16) JavaMatrix local16 = (JavaMatrix) arg0;
		if (!this.boundsCalculated) {
			this.method2804();
		}
		this.method2794(local16);
		Terrain.aFloat223 = local16.aFloat209 * local13.aFloat206 + local13.aFloat203 * local16.aFloat205 + local16.aFloat198 * local13.aFloat199 + local13.aFloat198;
		GameCanvas.aFloat189 = local13.aFloat199 * local16.aFloat203 + local16.aFloat207 * local13.aFloat206 + local13.aFloat203 * local16.aFloat204;
		@Pc(73) float local73 = GameCanvas.aFloat189 * (float) this.lb + Terrain.aFloat223;
		@Pc(81) float local81 = (float) this.aShort28 * GameCanvas.aFloat189 + Terrain.aFloat223;
		@Pc(98) float local98;
		@Pc(91) float local91;
		if (local73 > local81) {
			local91 = (float) this.aShort26 + local73;
			local98 = local81 - (float) this.aShort26;
		} else {
			local91 = (float) this.aShort26 + local81;
			local98 = (float) -this.aShort26 + local73;
		}
		if (this.glToolkit.projectedFarClipDistance <= local98 || local91 <= (float) this.glToolkit.zNear) {
			return;
		}
		Class145_Sub1.aFloat222 = local13.aFloat202 * local16.aFloat209 + local13.aFloat207 * local16.aFloat205 + local16.aFloat198 * local13.aFloat208 + local13.aFloat209;
		GameShell.aFloat30 = local16.aFloat203 * local13.aFloat208 + local16.aFloat204 * local13.aFloat207 + local13.aFloat202 * local16.aFloat207;
		@Pc(174) float local174 = GameShell.aFloat30 * (float) this.lb + Class145_Sub1.aFloat222;
		@Pc(182) float local182 = GameShell.aFloat30 * (float) this.aShort28 + Class145_Sub1.aFloat222;
		@Pc(198) float local198;
		@Pc(209) float local209;
		if (local174 > local182) {
			local198 = (float) this.glToolkit.anInt2309 * ((float) -this.aShort26 + local182);
			local209 = (local174 + (float) this.aShort26) * (float) this.glToolkit.anInt2309;
		} else {
			local198 = ((float) -this.aShort26 + local174) * (float) this.glToolkit.anInt2309;
			local209 = (local182 + (float) this.aShort26) * (float) this.glToolkit.anInt2309;
		}
		if (this.glToolkit.aFloat64 <= local198 / local91 || local209 / local91 <= this.glToolkit.aFloat75) {
			return;
		}
		CursorTypeList.aFloat163 = local13.aFloat204 * local16.aFloat204 + local13.aFloat200 * local16.aFloat207 + local13.aFloat201 * local16.aFloat203;
		Static102.aFloat56 = local13.aFloat205 + local13.aFloat200 * local16.aFloat209 + local16.aFloat205 * local13.aFloat204 + local13.aFloat201 * local16.aFloat198;
		@Pc(299) float local299 = CursorTypeList.aFloat163 * (float) this.lb + Static102.aFloat56;
		@Pc(307) float local307 = Static102.aFloat56 + (float) this.aShort28 * CursorTypeList.aFloat163;
		@Pc(334) float local334;
		@Pc(322) float local322;
		if (local299 > local307) {
			local322 = (float) this.glToolkit.anInt2294 * (local299 + (float) this.aShort26);
			local334 = (float) this.glToolkit.anInt2294 * ((float) -this.aShort26 + local307);
		} else {
			local334 = (float) this.glToolkit.anInt2294 * (local299 - (float) this.aShort26);
			local322 = (local307 + (float) this.aShort26) * (float) this.glToolkit.anInt2294;
		}
		if (this.glToolkit.aFloat68 <= local334 / local91 || local322 / local91 <= this.glToolkit.aFloat62) {
			return;
		}
		if (cylinder != null || this.aClass115Array1 != null) {
			Class50.aFloat113 = local16.aFloat201 * local13.aFloat207 + local13.aFloat202 * local16.aFloat208 + local13.aFloat208 * local16.aFloat199;
			Class78_Sub1.aFloat126 = local16.aFloat208 * local13.aFloat206 + local13.aFloat203 * local16.aFloat201 + local13.aFloat199 * local16.aFloat199;
			Class4_Sub45.aFloat227 = local13.aFloat203 * local16.aFloat200 + local13.aFloat206 * local16.aFloat202 + local16.aFloat206 * local13.aFloat199;
			Class4_Sub6_Sub22.aFloat140 = local13.aFloat207 * local16.aFloat200 + local13.aFloat202 * local16.aFloat202 + local16.aFloat206 * local13.aFloat208;
			Class16_Sub5_Sub2.aFloat184 = local16.aFloat202 * local13.aFloat200 + local16.aFloat200 * local13.aFloat204 + local16.aFloat206 * local13.aFloat201;
			Class249.aFloat213 = local16.aFloat208 * local13.aFloat200 + local13.aFloat204 * local16.aFloat201 + local16.aFloat199 * local13.aFloat201;
		}
		if (cylinder != null) {
			@Pc(493) boolean local493 = false;
			@Pc(495) boolean local495 = true;
			@Pc(503) int local503 = this.aShort29 + this.aShort31 >> 1;
			@Pc(511) int local511 = this.aShort25 + this.aShort27 >> 1;
			@Pc(530) int local530 = (int) (Class50.aFloat113 * (float) local511 + Class4_Sub6_Sub22.aFloat140 * (float) local503 + Class145_Sub1.aFloat222 + GameShell.aFloat30 * (float) this.lb);
			@Pc(549) int local549 = (int) (Class249.aFloat213 * (float) local511 + CursorTypeList.aFloat163 * (float) this.lb + Static102.aFloat56 + (float) local503 * Class16_Sub5_Sub2.aFloat184);
			@Pc(568) int local568 = (int) ((float) this.lb * GameCanvas.aFloat189 + (float) local503 * Class4_Sub45.aFloat227 + Terrain.aFloat223 + Class78_Sub1.aFloat126 * (float) local511);
			if (local568 < this.glToolkit.zNear) {
				local493 = true;
			} else {
				cylinder.anInt4542 = this.glToolkit.anInt2295 + this.glToolkit.anInt2309 * local530 / local568;
				cylinder.anInt4540 = local549 * this.glToolkit.anInt2294 / local568 + this.glToolkit.anInt2297;
			}
			@Pc(627) int local627 = (int) ((float) local511 * Class50.aFloat113 + Class145_Sub1.aFloat222 + Class4_Sub6_Sub22.aFloat140 * (float) local503 + GameShell.aFloat30 * (float) this.aShort28);
			@Pc(646) int local646 = (int) ((float) local511 * Class249.aFloat213 + CursorTypeList.aFloat163 * (float) this.aShort28 + Static102.aFloat56 + (float) local503 * Class16_Sub5_Sub2.aFloat184);
			@Pc(665) int local665 = (int) (Class4_Sub45.aFloat227 * (float) local503 + Terrain.aFloat223 + (float) this.aShort28 * GameCanvas.aFloat189 + (float) local511 * Class78_Sub1.aFloat126);
			if (this.glToolkit.zNear <= local665) {
				cylinder.anInt4541 = local646 * this.glToolkit.anInt2294 / local665 + this.glToolkit.anInt2297;
				cylinder.anInt4543 = this.glToolkit.anInt2295 + this.glToolkit.anInt2309 * local627 / local665;
			} else {
				local493 = true;
			}
			if (local493) {
				if (local568 < this.glToolkit.zNear && local665 < this.glToolkit.zNear) {
					local495 = false;
				} else {
					@Pc(747) int local747;
					@Pc(759) int local759;
					@Pc(785) int local785;
					if (local568 < this.glToolkit.zNear) {
						local747 = (local665 - this.glToolkit.zNear << 16) / (local665 - local568);
						local759 = ((local627 - local530) * local747 >> 16) + local627;
						local785 = local646 + ((local646 - local549) * local747 >> 16);
						cylinder.anInt4542 = this.glToolkit.anInt2309 * local759 / this.glToolkit.zNear + this.glToolkit.anInt2295;
						cylinder.anInt4540 = this.glToolkit.anInt2297 + local785 * this.glToolkit.anInt2294 / this.glToolkit.zNear;
					} else if (local665 < this.glToolkit.zNear) {
						local747 = (local568 - this.glToolkit.zNear << 16) / (local568 - local665);
						local759 = local530 + ((local530 - local627) * local747 >> 16);
						cylinder.anInt4542 = this.glToolkit.anInt2309 * local759 / this.glToolkit.zNear + this.glToolkit.anInt2295;
						local785 = local549 + ((local549 - local646) * local747 >> 16);
						cylinder.anInt4540 = this.glToolkit.anInt2294 * local785 / this.glToolkit.zNear + this.glToolkit.anInt2297;
					}
				}
			}
			if (local495) {
				if (local665 >= local568) {
					cylinder.anInt4544 = this.glToolkit.anInt2295 + this.glToolkit.anInt2309 * (local627 - -this.aShort26) / local665 - cylinder.anInt4543;
				} else {
					cylinder.anInt4544 = (this.aShort26 + local530) * this.glToolkit.anInt2309 / local568 + this.glToolkit.anInt2295 - cylinder.anInt4542;
				}
				cylinder.positionCalculated = true;
			}
		}
		// 592HD: this model is about to be drawn, so it is a sun-shadow caster
		// for the next frame's map: placement from the ground matrix the draw
		// pushes, geometry from the same buffers. The seam is inert with the row
		// or 592HD off, and records nothing outside the world draw.
		this.glToolkit.hdRecordShadowCaster(this, local16);
		this.glToolkit.method1904();
		this.glToolkit.method1948(local16);
		this.method2797();
		this.glToolkit.method1916();
		this.method2801();
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IIII)V")
	@Override
	public void adjustColours(@OriginalArg(0) int hue, @OriginalArg(1) int saturation, @OriginalArg(2) int lightness, @OriginalArg(3) int arg3) {
		@Pc(16) int local16;
		for (@Pc(7) int local7 = 0; local7 < this.numFaces; local7++) {
			local16 = this.aShortArray100[local7] & 0xFFFF;
			@Pc(22) int local22 = local16 >> 10 & 0x3F;
			@Pc(28) int local28 = local16 >> 7 & 0x7;
			if (hue != -1) {
				local22 += arg3 * (hue - local22) >> 7;
			}
			@Pc(47) int local47 = local16 & 0x7F;
			if (saturation != -1) {
				local28 += (saturation - local28) * arg3 >> 7;
			}
			if (lightness != -1) {
				local47 += (lightness - local47) * arg3 >> 7;
			}
			this.aShortArray100[local7] = (short) (local47 | local28 << 7 | local22 << 10);
		}
		if (this.aClass115Array1 != null) {
			for (local16 = 0; local16 < this.billboardCount; local16++) {
				@Pc(108) NativeBillboardFace local108 = this.aClass115Array1[local16];
				@Pc(113) Class85 local113 = this.aClass85Array1[local16];
				local113.anInt2743 = local113.anInt2743 & 0xFF000000 | Class9_Sub3.anIntArray115[this.aShortArray100[local108.anInt3416] & 0xFFFF] & 0xFFFFFF;
			}
		}
		if (this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "b", descriptor = "(I)V")
	private void method2804() {
		@Pc(7) int local7 = 32767;
		@Pc(9) int local9 = 32767;
		@Pc(11) int local11 = 32767;
		@Pc(13) int local13 = -32768;
		@Pc(15) int local15 = -32768;
		@Pc(17) int local17 = -32768;
		@Pc(19) int local19 = 0;
		@Pc(21) int local21 = 0;
		for (@Pc(27) int local27 = 0; local27 < this.maxDepth; local27++) {
			@Pc(34) int local34 = this.anIntArray212[local27];
			@Pc(39) int local39 = this.anIntArray216[local27];
			if (local13 < local34) {
				local13 = local34;
			}
			if (local39 < local9) {
				local9 = local39;
			}
			if (local15 < local39) {
				local15 = local39;
			}
			@Pc(67) int local67 = this.anIntArray213[local27];
			if (local7 > local34) {
				local7 = local34;
			}
			if (local11 > local67) {
				local11 = local67;
			}
			if (local17 < local67) {
				local17 = local67;
			}
			@Pc(94) int local94 = local67 * local67 + local34 * local34;
			if (local94 > local19) {
				local19 = local94;
			}
			local94 = local67 * local67 + local34 * local34 + local39 * local39;
			if (local21 < local94) {
				local21 = local94;
			}
		}
		this.aShort31 = (short) local13;
		this.aShort27 = (short) local11;
		this.aShort25 = (short) local17;
		this.aShort28 = (short) local15;
		this.aShort29 = (short) local7;
		this.lb = (short) local9;
		this.aShort26 = (short) (Math.sqrt((double) local19) + 0.99D);
		Math.sqrt((double) local21);
		this.boundsCalculated = true;
	}

	@OriginalMember(owner = "client!ip", name = "WA", descriptor = "()I")
	@Override
	public int WA() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.aShort28;
	}

	/**
	 * 592HD: draws this model's geometry into an open sun depth pass.
	 *
	 * <p>No lit program, and no material at all with the cut-out row off: the
	 * pass's modelview is the identity plus the caster's own placement, which is
	 * the space {@link #anIntArray212} and the vertex buffer already hold, so the
	 * triangles land where the screen draw puts them. Called from the seam's
	 * caster list, never from the screen draw.
	 *
	 * <p>With the row on, each face group whose material carries an alpha channel
	 * binds that material's texture and draws with the caster's own texcoords,
	 * so the pass's alpha test sees the shape the player sees instead of the
	 * quad. Groups with a solid or missing material keep the position-only path,
	 * which is why a solid caster binds no texture and why a model without
	 * texcoords still casts. Silent when the model has no faces or no uploaded
	 * buffers: a model the world draw culled before its first upload cannot
	 * cast, and the pass must not draw whatever the previous user of this
	 * toolkit left bound.
	 */
	public void drawShadowDepth() {
		if (this.numNormals <= 0 || this.renderedFaceCount <= 0 || this.aClass230_1 == null) {
			return;
		}
		this.method2799(false);
		if (this.aClass230_1.anInterface1_5 == null) {
			this.method2808((this.renderState & 0x10) != 0);
		}
		if (this.aClass230_1.anInterface1_5 == null || this.vertexBuffer == null
				|| this.vertexBuffer.anInterface11_7 == null) {
			return;
		}
		this.glToolkit.hdBeginShadowCasterDraw();
		// The group table can legitimately be gone: method2807 releases it for a
		// mesh whose faces all lack an alpha group, while the geometry and the
		// uploaded buffer stay. Such a caster still casts, as one position-only
		// draw - the same path as the row being off.
		if (!ShadowCutout.canDrawGroups(this.glToolkit.hdShadowCutoutActive(),
				this.aClass177_9 != null && this.aClass177_9.anInterface11_7 != null,
				this.anIntArray215, this.aShortArray107)) {
			this.drawShadowDepthPositionsOnly();
			return;
		}
		// A caster whose materials all lack an alpha channel has nothing to test:
		// one position-only draw, exactly like the row-off path, instead of a
		// draw call per group with an unbound unit.
		int groups = this.anIntArray215.length - 1;
		boolean alpha = false;
		for (int group = 0; group < groups && !alpha; group++) {
			alpha = this.groupMaterial(group) >= 0;
		}
		if (!alpha) {
			this.drawShadowDepthPositionsOnly();
			return;
		}
		// The caster's own texcoords, so each group samples the texture the
		// screen draw gives it. A group without an alpha material is drawn with
		// the unit cleared: solid geometry still casts, it just binds nothing.
		this.glToolkit.method1934(null, null, this.aClass177_9, this.vertexBuffer);
		for (int group = 0; group < groups; group++) {
			int start = this.anIntArray215[group];
			int end = this.anIntArray215[group + 1];
			if (start >= this.renderedFaceCount) {
				break;
			}
			if (end > this.renderedFaceCount) {
				end = this.renderedFaceCount;
			}
			this.glToolkit.hdBindShadowCasterMaterial(this.groupMaterial(group));
			this.glToolkit.method1967(start * 3, (end - start) * 3, this.aClass230_1.anInterface1_5);
		}
	}

	/** The whole rendered mesh, with no material and no texcoords. */
	private void drawShadowDepthPositionsOnly() {
		this.glToolkit.method1934(null, null, null, this.vertexBuffer);
		this.glToolkit.method1967(0, this.renderedFaceCount * 3, this.aClass230_1.anInterface1_5);
	}

	/**
	 * The material one face group samples, or -1 when the group is to draw with
	 * the unit cleared: only a material whose texture has an alpha channel is
	 * worth binding in a depth pass.
	 */
	private int groupMaterial(int group) {
		if (this.anIntArray215 == null || this.aShortArray107 == null) {
			return -1;
		}
		int start = this.anIntArray215[group];
		if (start >= this.renderedFaceCount) {
			return -1;
		}
		int material = this.aShortArray107[start] & 0xFFFF;
		if (material == 65535 || !this.hasAlphaMaterial(material)) {
			return -1;
		}
		return material;
	}

	/** True when this material's texture has an alpha channel worth testing. */
	private boolean hasAlphaMaterial(int material) {
		if (material < 0 || this.glToolkit.materialProvider == null
				|| !this.glToolkit.materialProvider.textureAvailable(material)) {
			return false;
		}
		MaterialType type = this.glToolkit.materialProvider.getMetrics(material);
		return type != null && type.aByte92 != 0;
	}

	@OriginalMember(owner = "client!ip", name = "c", descriptor = "()V")
	@Override
	public void uploadToGpu() {
		if (this.numNormals <= 0 || this.renderedFaceCount <= 0) {
			return;
		}
		this.method2799(false);
		if ((this.renderState & 0x10) == 0 && this.aClass230_1.anInterface1_5 == null) {
			this.method2808(false);
		}
		this.method2807();
	}

	@OriginalMember(owner = "client!ip", name = "I", descriptor = "(SS)V")
	@Override
	public void I(@OriginalArg(0) short src, @OriginalArg(1) short dst) {
		@Pc(9) unpack_Sub1 local9 = this.glToolkit.materialProvider;
		for (@Pc(11) int local11 = 0; local11 < this.numFaces; local11++) {
			if (this.aShortArray107[local11] == src) {
				this.aShortArray107[local11] = dst;
			}
		}
		@Pc(35) byte local35 = 0;
		@Pc(37) byte local37 = 0;
		if (src != -1) {
			@Pc(47) MaterialType local47 = local9.getMetrics(src & 0xFFFF);
			local37 = local47.aByte87;
			local35 = local47.aByte90;
		}
		@Pc(55) byte local55 = 0;
		@Pc(57) byte local57 = 0;
		if (dst != -1) {
			@Pc(67) MaterialType local67 = local9.getMetrics(dst & 0xFFFF);
			local55 = local67.aByte90;
			local57 = local67.aByte87;
		}
		if (!(local35 != local55 | local37 != local57)) {
			return;
		}
		if (this.aClass115Array1 != null) {
			for (@Pc(96) int local96 = 0; local96 < this.billboardCount; local96++) {
				@Pc(103) NativeBillboardFace local103 = this.aClass115Array1[local96];
				@Pc(108) Class85 local108 = this.aClass85Array1[local96];
				local108.anInt2743 = local108.anInt2743 & 0xFF000000 | Class9_Sub3.anIntArray115[this.aShortArray100[local103.anInt3416] & 0xFFFF] & 0xFFFFFF;
			}
		}
		if (this.colorsBuffer != null) {
			this.colorsBuffer.anInterface11_7 = null;
			return;
		}
	}

	@OriginalMember(owner = "client!ip", name = "P", descriptor = "()I")
	@Override
	public int P() {
		return this.functionMask;
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(ZIIIIIIII)Z")
	private boolean method2806(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) int arg4, @OriginalArg(6) int arg5, @OriginalArg(7) int arg6, @OriginalArg(8) int arg7) {
		if (arg1 > arg3 && arg3 < arg5 && arg6 > arg3) {
			return false;
		} else if (arg1 < arg3 && arg3 > arg5 && arg3 > arg6) {
			return false;
		} else if (arg7 < arg0 && arg4 > arg7 && arg2 > arg7) {
			return false;
		} else {
			return arg7 <= arg0 || arg7 <= arg4 || arg2 >= arg7;
		}
	}

	@OriginalMember(owner = "client!ip", name = "d", descriptor = "(I)V")
	private void method2807() {
		if (!this.aBoolean281) {
			return;
		}
		this.aBoolean281 = false;
		if (this.emissiveTriangles == null && this.effectiveVertices == null && this.aClass115Array1 == null) {
			if (this.anIntArray212 != null && !Class16_Sub3.method723(this.preferenceFlags, this.functionMask)) {
				if (this.vertexBuffer != null && this.vertexBuffer.anInterface11_7 == null) {
					this.aBoolean281 = true;
				} else {
					if (!this.boundsCalculated) {
						this.method2804();
					}
					this.anIntArray212 = null;
				}
			}
			if (this.anIntArray216 != null && !Class4_Sub10.method1997(this.functionMask, this.preferenceFlags)) {
				if (this.vertexBuffer != null && this.vertexBuffer.anInterface11_7 == null) {
					this.aBoolean281 = true;
				} else {
					if (!this.boundsCalculated) {
						this.method2804();
					}
					this.anIntArray216 = null;
				}
			}
			if (this.anIntArray213 != null && !Class16_Sub5_Sub2.method4656(this.preferenceFlags, this.functionMask)) {
				if (this.vertexBuffer != null && this.vertexBuffer.anInterface11_7 == null) {
					this.aBoolean281 = true;
				} else {
					if (!this.boundsCalculated) {
						this.method2804();
					}
					this.anIntArray213 = null;
				}
			}
		}
		if (this.aShortArray109 != null && this.anIntArray212 == null && this.anIntArray216 == null && this.anIntArray213 == null) {
			this.anIntArray214 = null;
			this.aShortArray109 = null;
		}
		if (this.aByteArray39 != null && !Class4_Sub18.method2029(this.preferenceFlags, this.functionMask)) {
			if (this.normalsBuffer == null) {
				if (this.colorsBuffer != null && this.colorsBuffer.anInterface11_7 == null) {
					this.aBoolean281 = true;
				} else {
					this.aByteArray39 = null;
					this.aShortArray103 = this.aShortArray101 = this.aShortArray102 = null;
				}
			} else if (this.normalsBuffer.anInterface11_7 == null) {
				this.aBoolean281 = true;
			} else {
				this.aShortArray103 = this.aShortArray101 = this.aShortArray102 = null;
				this.aByteArray39 = null;
			}
		}
		if (this.aShortArray100 != null && !StringNode.method4064(this.functionMask, this.preferenceFlags)) {
			if (this.colorsBuffer != null && this.colorsBuffer.anInterface11_7 == null) {
				this.aBoolean281 = true;
			} else {
				this.aShortArray100 = null;
			}
		}
		if (this.aByteArray38 != null && !MouseManager.method2246(this.preferenceFlags, this.functionMask)) {
			if (this.colorsBuffer != null && this.colorsBuffer.anInterface11_7 == null) {
				this.aBoolean281 = true;
			} else {
				this.aByteArray38 = null;
			}
		}
		if (this.aFloatArray21 != null && !Static391.method5119(this.preferenceFlags, this.functionMask)) {
			if (this.aClass177_9 != null && this.aClass177_9.anInterface11_7 == null) {
				this.aBoolean281 = true;
			} else {
				this.aFloatArray21 = this.aFloatArray20 = null;
			}
		}
		if (this.aShortArray107 != null && !JavaFallbackSurface.method4609(this.preferenceFlags, this.functionMask)) {
			if (this.colorsBuffer != null && this.colorsBuffer.anInterface11_7 == null) {
				this.aBoolean281 = true;
			} else {
				this.aShortArray107 = null;
			}
		}
		if (this.aShortArray108 != null && !Static312.method4173(this.preferenceFlags, this.functionMask)) {
			if ((this.aClass230_1 == null || this.aClass230_1.anInterface1_5 != null) && (this.colorsBuffer == null || this.colorsBuffer.anInterface11_7 != null)) {
				this.aShortArray108 = this.aShortArray105 = this.aShortArray106 = null;
			} else {
				this.aBoolean281 = true;
			}
		}
		if (this.faceGroups != null && !Class16_Sub3.method726(this.preferenceFlags, this.functionMask)) {
			this.aShortArray104 = null;
			this.faceGroups = null;
		}
		if (this.anIntArrayArray34 != null && !TextureOpCurve.method688(this.preferenceFlags, this.functionMask)) {
			this.anIntArrayArray34 = null;
			this.aShortArray99 = null;
		}
		if (this.anIntArrayArray32 != null && !Class16_Sub1.method5343(this.functionMask, this.preferenceFlags)) {
			this.anIntArrayArray32 = null;
		}
		if (this.anIntArray215 != null && (this.functionMask & 0x800) == 0 && (this.functionMask & 0x40000) == 0) {
			this.anIntArray215 = null;
		}
	}

	@OriginalMember(owner = "client!ip", name = "a", descriptor = "(ZZ)V")
	private void method2808(@OriginalArg(0) boolean arg0) {
		if (this.renderedFaceCount * 6 > SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer.length) {
			SpriteDrawQueue.aClass4_Sub12_Sub2_3 = new GlBitBuffer(this.renderedFaceCount * 6 + 600);
		} else {
			SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition = 0;
		}
		@Pc(44) int local44;
		if (this.glToolkit.bigEndianess) {
			for (local44 = 0; local44 < this.renderedFaceCount; local44++) {
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2524(this.aShortArray108[local44]);
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2524(this.aShortArray105[local44]);
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2524(this.aShortArray106[local44]);
			}
		} else {
			for (local44 = 0; local44 < this.renderedFaceCount; local44++) {
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2541(this.aShortArray108[local44]);
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2541(this.aShortArray105[local44]);
				SpriteDrawQueue.aClass4_Sub12_Sub2_3.method2541(this.aShortArray106[local44]);
			}
		}
		if (SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition == 0) {
			return;
		}
		if (arg0) {
			if (this.anInterface1_2 == null) {
				this.anInterface1_2 = this.glToolkit.method1955(SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition, true, SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer);
			} else {
				this.anInterface1_2.method3657(SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer, SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition);
			}
			this.aClass230_1.anInterface1_5 = this.anInterface1_2;
		} else {
			this.aClass230_1.anInterface1_5 = this.glToolkit.method1955(SpriteDrawQueue.aClass4_Sub12_Sub2_3.currentPosition, false, SpriteDrawQueue.aClass4_Sub12_Sub2_3.buffer);
		}
		if (!arg0) {
			this.aBoolean281 = true;
		}
	}

	@OriginalMember(owner = "client!ip", name = "MA", descriptor = "()I")
	@Override
	public int setOffsets() {
		if (!this.boundsCalculated) {
			this.method2804();
		}
		return this.lb;
	}

@OriginalMember(owner = "client!ip", name = "sc", descriptor = "I")
	public static int anInt3361;
@OriginalMember(owner = "client!ip", name = "vc", descriptor = "[I")
	public static int[] anIntArray217;
@OriginalMember(owner = "client!ip", name = "lc", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_71 = new Class83("Loading textures - ", "Lade Texturen - ", "Chargement des textures - ", "Carregando texturas - ");
@OriginalMember(owner = "client!ip", name = "xc", descriptor = "[Ljava/awt/Color;")
	public static final Color[] aColorArray5 = new Color[] { new Color(16777215), new Color(16777215) };
@OriginalMember(owner = "client!ip", name = "a", descriptor = "(IIZ)V")
	public static void method2796(@OriginalArg(0) int arg0, @OriginalArg(2) boolean arg1) {
		@Pc(10) Class4_Sub25 local10 = PlayerVarDomain.method2834(arg1, arg0);
		if (local10 != null) {
			for (@Pc(15) int local15 = 0; local15 < local10.anIntArray230.length; local15++) {
				local10.anIntArray230[local15] = -1;
				local10.anIntArray229[local15] = 0;
			}
		}
	}
@OriginalMember(owner = "client!ip", name = "a", descriptor = "(JI)Ljava/lang/String;")
	public static String method2805(@OriginalArg(0) long arg0) {
		Class4_Sub6_Sub23.aCalendar1.setTime(new Date(arg0));
		@Pc(18) int local18 = Class4_Sub6_Sub23.aCalendar1.get(7);
		@Pc(22) int local22 = Class4_Sub6_Sub23.aCalendar1.get(5);
		@Pc(26) int local26 = Class4_Sub6_Sub23.aCalendar1.get(2);
		@Pc(30) int local30 = Class4_Sub6_Sub23.aCalendar1.get(1);
		@Pc(34) int local34 = Class4_Sub6_Sub23.aCalendar1.get(11);
		@Pc(38) int local38 = Class4_Sub6_Sub23.aCalendar1.get(12);
		@Pc(42) int local42 = Class4_Sub6_Sub23.aCalendar1.get(13);
		return Class27_Sub1.aStringArray5[local18 - 1] + ", " + local22 / 10 + local22 % 10 + "-" + Class4_Sub6_Sub26.aStringArray24[local26] + "-" + local30 + " " + local34 / 10 + local34 % 10 + ":" + local38 / 10 + local38 % 10 + ":" + local42 / 10 + local42 % 10 + " GMT";
	}
}
