package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.LocEntity;
import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.scene.ground.Ground;

import com.jagex.game.runetek5.cache.node.lru.ClientScript;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub9_Sub2;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.js5.Js5MasterIndex;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub1;
import com.jagex.game.runetek5.preference.Preference;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class28;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpBrightness;
import com.jagex.game.runetek5.deob.Huffman;
import com.jagex.game.runetek5.deob.TextureOpBlend;
import com.jagex.game.runetek5.GlVolumeTexture;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
@OriginalClass("client!ft")
public final class Class16_Sub4_Sub2 extends Class16_Sub4 implements GameObject {

	@OriginalMember(owner = "client!ft", name = "F", descriptor = "Lclient!bi;")
	public final LocEntity aClass23_1;

	@OriginalMember(owner = "client!ft", name = "K", descriptor = "Z")
	private final boolean aBoolean234;

	static {
		new Class83("Already attempting to join a channel - please wait...", "Du versuchst bereits, einem Chatraum beizutreten - bitte warte.", "Tentative de connexion au canal déjà en cours - veuillez patienter...", "Já há uma tentativa de entrar em um canal. Aguarde...");
	}

	@OriginalMember(owner = "client!ft", name = "<init>", descriptor = "(Lclient!ya;Lclient!rd;IIIIIZII)V")
	public Class16_Sub4_Sub2(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) LocType arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(7) boolean arg7, @OriginalArg(8) int arg8, @OriginalArg(9) int arg9) {
		super(arg4, arg5, arg6, arg1.aBoolean527, arg1.aBoolean529);
		this.aClass23_1 = new LocEntity(arg0, arg1, 22, arg8, arg2, arg3, arg4, arg6, arg7, arg9);
		this.aBoolean234 = arg1.anInt5813 != 0 && !arg7;
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(B)I")
	@Override
	public int method5347() {
		return this.aClass23_1.anInt551;
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	@Override
	public Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1) {
		return this.aClass23_1.method578(0, false, arg0, 0, arg1, false);
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(IILclient!fi;BZLclient!ya;I)V")
	@Override
	public void method5337(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Class16 arg2, @OriginalArg(4) boolean arg3, @OriginalArg(5) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(6) int arg5) {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(ZLclient!ya;)V")
	@Override
	public void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_1.method588(arg0);
	}

	@OriginalMember(owner = "client!ft", name = "c", descriptor = "(I)I")
	@Override
	public int method5354() {
		return this.aClass23_1.anInt536;
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(Z)I")
	@Override
	public int method5350() {
		return this.aClass23_1.anInt548;
	}

	@OriginalMember(owner = "client!ft", name = "d", descriptor = "(I)V")
	@Override
	public void removeTileShadow() {
		throw new IllegalStateException();
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(I)Z")
	@Override
	public boolean hardShadow() {
		return this.aClass23_1.method584();
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(Lclient!ya;I)V")
	@Override
	public void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		this.aClass23_1.method582(arg0);
	}

	@OriginalMember(owner = "client!ft", name = "d", descriptor = "(B)Z")
	@Override
	public boolean hasTileShadow() {
		return false;
	}

	@OriginalMember(owner = "client!ft", name = "a", descriptor = "(IILclient!ya;I)Z")
	@Override
	public boolean method5341(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.graphics.Toolkit arg1, @OriginalArg(3) int arg2) {
		@Pc(16) Model local16 = this.aClass23_1.method578(super.anInt3647, false, arg1, super.anInt3646, 131072, false);
		if (local16 == null) {
			return false;
		} else {
			@Pc(27) Ground local27 = arg1.method4289();
			local27.scaleWidth(super.anInt3646, super.anInt3648, super.anInt3647);
			return local16.picked(arg2, arg0, local27, false);
		}
	}

	@OriginalMember(owner = "client!ft", name = "b", descriptor = "(Lclient!ya;I)V")
	@Override
	public void method5335(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(23) Model local23 = this.aClass23_1.method578(super.anInt3647, true, arg0, super.anInt3646, 262144, true);
		if (local23 != null) {
			@Pc(30) int local30 = super.anInt3646 >> 7;
			@Pc(35) int local35 = super.anInt3647 >> 7;
			this.aClass23_1.method586(false, local35, local23, local30, arg0, local35, local30);
		}
	}

	@OriginalMember(owner = "client!ft", name = "c", descriptor = "(Lclient!ya;I)Lclient!fk;")
	@Override
	public Class3_Sub3 method5339(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0) {
		@Pc(16) Model local16 = this.aClass23_1.method578(super.anInt3647, true, arg0, super.anInt3646, 2048, false);
		if (local16 == null) {
			return null;
		}
		@Pc(23) Ground local23 = arg0.method4289();
		local23.scaleWidth(super.anInt3646, super.anInt3648, super.anInt3647);
		@Pc(33) Class3_Sub3 local33 = null;
		if (this.aBoolean234) {
			local33 = Class28.method672(1);
		}
		if (this.aClass23_1.aClass3_Sub2_1 == null) {
			local16.render(local23, local33 == null ? null : local33.aClass3_Sub5Array1[0], 0);
		} else {
			@Pc(49) ParticleList local49 = this.aClass23_1.aClass3_Sub2_1.getList();
			arg0.method4262(local16, local49, local23, local33 == null ? null : local33.aClass3_Sub5Array1[0]);
		}
		@Pc(82) int local82 = super.anInt3646 >> 7;
		@Pc(94) int local94 = super.anInt3647 >> 7;
		this.aClass23_1.method586(true, local94, local16, local82, arg0, local94, local82);
		return local33;
	}

	@OriginalMember(owner = "client!ft", name = "b", descriptor = "(I)V")
	@Override
	public void upload() {
	}

@OriginalMember(owner = "client!ft", name = "E", descriptor = "[I")
	public static int[] anIntArray169;
@OriginalMember(owner = "client!ft", name = "M", descriptor = "[I")
	public static int[] anIntArray170;
@OriginalMember(owner = "client!ft", name = "G", descriptor = "I")
	public static int anInt2524 = 0;
@OriginalMember(owner = "client!ft", name = "I", descriptor = "[Lclient!lv;")
	public static final RemoteFileWorker[] aClass143_Sub1Array1 = new RemoteFileWorker[31];
@OriginalMember(owner = "client!ft", name = "J", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_44 = new Class83("Loaded world list data", "Liste der Welten geladen", "Liste des serveurs chargée", "Dados da lista de mundos carregados");
@OriginalMember(owner = "client!ft", name = "N", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_106 = new Class22(20, 12);
@OriginalMember(owner = "client!ft", name = "O", descriptor = "I")
	public static int anInt2527 = 0;
@OriginalMember(owner = "client!ft", name = "a", descriptor = "(IIILclient!pn;)Lclient!ae;")
	public static ClientScript method2147(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) Class186 arg2) {
		@Pc(10) int local10 = arg2.anInt5418 | arg1 << 8;
		@Pc(19) ClientScript local19 = (ClientScript) GlVolumeTexture.aClass142_1.method3483((long) local10 << 16);
		if (local19 != null) {
			return local19;
		}
		@Pc(33) byte[] local33 = TextureOpBlend.aClass76_45.method2122(TextureOpBlend.aClass76_45.method2117(local10));
		if (local33 == null) {
			local10 = arg2.anInt5418 | arg0 + 65536 << 8;
			local19 = (ClientScript) GlVolumeTexture.aClass142_1.method3483((long) local10 << 16);
			if (local19 != null) {
				return local19;
			}
			local33 = TextureOpBlend.aClass76_45.method2122(TextureOpBlend.aClass76_45.method2117(local10));
			if (local33 == null) {
				local10 = arg2.anInt5418 | 0xFFFF00;
				local19 = (ClientScript) GlVolumeTexture.aClass142_1.method3483((long) local10 << 16);
				if (local19 != null) {
					return local19;
				}
				local33 = TextureOpBlend.aClass76_45.method2122(TextureOpBlend.aClass76_45.method2117(local10));
				if (local33 == null) {
					return null;
				} else if (local33.length <= 1) {
					return null;
				} else {
					local19 = ModelNormalsGL.method3441(local33);
					local19.aClass186_1 = arg2;
					GlVolumeTexture.aClass142_1.method3480((long) local10 << 16, local19);
					return local19;
				}
			} else if (local33.length <= 1) {
				return null;
			} else {
				local19 = ModelNormalsGL.method3441(local33);
				local19.aClass186_1 = arg2;
				GlVolumeTexture.aClass142_1.method3480((long) local10 << 16, local19);
				return local19;
			}
		} else if (local33.length <= 1) {
			return null;
		} else {
			local19 = ModelNormalsGL.method3441(local33);
			local19.aClass186_1 = arg2;
			GlVolumeTexture.aClass142_1.method3480((long) local10 << 16, local19);
			return local19;
		}
	}
@OriginalMember(owner = "client!ft", name = "f", descriptor = "(I)V")
	public static void method2149() {
		@Pc(5) int local5 = 0;
		if (TextureOpBrightness.aClass50_Sub1_1.method2854(Packet.anInt2973)) {
			local5 = 55;
		}
		if (!TextureOpBrightness.aClass50_Sub1_1.aBoolean307) {
			local5 |= 0x40;
		}
		ClientScript.method51(local5);
		Static267.aClass262_2.method5558(local5);
		Terrain.aClass206_3.method4712(local5);
		Class196.aClass240_1.method5225(local5);
		Js5MasterIndex.aClass194_2.method4417(local5);
		GameShell.method889(local5);
		Class4_Sub1_Sub9_Sub2.method2574(local5);
		Preference.method2233(local5);
		Class16_Sub1_Sub1.method296(local5);
		if (VertexDeclarationComponent.anInt6667 == 10) {
			Huffman.method2932(28);
		} else if (VertexDeclarationComponent.anInt6667 == 30) {
			Huffman.method2932(25);
		}
	}
@OriginalMember(owner = "client!ft", name = "a", descriptor = "(IIII)V")
	public static void method2150(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		@Pc(16) Class4_Sub1_Sub11 local16 = NPCTypeList.method5222(arg0, 9);
		local16.method2604();
		local16.anInt3083 = arg2;
		local16.anInt3082 = arg1;
	}
}
