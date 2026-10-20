package com.jagex.game.runetek5.graphics.gl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class83;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.*;
import com.jagex.game.runetek5.deob.Class180;
import com.jagex.game.runetek5.deob.Class27_Sub1;
@OriginalClass("client!im")
public final class GlCubeTexture extends Class6 {

	@OriginalMember(owner = "client!im", name = "y", descriptor = "I")
	private int anInt3252 = -1;

	@OriginalMember(owner = "client!im", name = "A", descriptor = "I")
	private int anInt3254 = -1;

	@OriginalMember(owner = "client!im", name = "E", descriptor = "I")
	public final int size;

	static {
		new Class83("Unable to add friend - unknown player.", "Spieler konnte nicht hinzugefügt werden - Spieler unbekannt.", "Impossible d'ajouter l'ami - joueur inconnu.", "Não foi possível adicionar um amigo - jogador desconhecido.");
	}

	@OriginalMember(owner = "client!im", name = "<init>", descriptor = "(Lclient!fd;IIZ[[BI)V")
	public GlCubeTexture(@OriginalArg(0) GlToolkit arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) boolean arg3, @OriginalArg(4) byte[][] arg4, @OriginalArg(5) int arg5) {
		super(arg0, 34067, arg1, arg2 * arg2 * 6, arg3);
		this.size = arg2;
		super.aClass19_Sub2_31.setActiveTexture(this);
		for (@Pc(28) int local28 = 0; local28 < 6; local28++) {
			OpenGL.glTexImage2Dub(local28 + 34069, 0, super.anInt5443, arg2, arg2, 0, arg5, 5121, arg4[local28], 0);
		}
		this.method4181(true);
	}

	@OriginalMember(owner = "client!im", name = "<init>", descriptor = "(Lclient!fd;IIZ[[I)V")
	public GlCubeTexture(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int internalFormat, @OriginalArg(2) int size, @OriginalArg(3) boolean mipmapped, @OriginalArg(4) int[][] faces) {
		super(glToolkit, 34067, internalFormat, size * size * 6, mipmapped);
		this.size = size;
		super.aClass19_Sub2_31.setActiveTexture(this);
		@Pc(30) int local30;
		if (mipmapped) {
			for (local30 = 0; local30 < 6; local30++) {
				Class27_Sub1.method665(faces[local30], super.anInt5443, size, size, super.aClass19_Sub2_31.pixelType, local30 + 34069);
			}
		} else {
			for (local30 = 0; local30 < 6; local30++) {
				OpenGL.glTexImage2Di(local30 + 34069, 0, super.anInt5443, size, size, 0, 32993, super.aClass19_Sub2_31.pixelType, faces[local30], 0);
			}
		}
		this.method4181(true);
	}

	@OriginalMember(owner = "client!im", name = "<init>", descriptor = "(Lclient!fd;II)V")
	public GlCubeTexture(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int internalFormat, @OriginalArg(2) int size) {
		super(glToolkit, 34067, internalFormat, size * 6 * size, false);
		this.size = size;
		super.aClass19_Sub2_31.setActiveTexture(this);
		for (@Pc(28) int local28 = 0; local28 < 6; local28++) {
			OpenGL.glTexImage2Dub(local28 + 34069, 0, super.anInt5443, size, size, 0, Class180.method4122(super.anInt5443), 5121, null, 0);
		}
		this.method4181(true);
	}

	@OriginalMember(owner = "client!im", name = "a", descriptor = "(IIIBI)V")
	public void method2754(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1, @OriginalArg(4) int arg2) {
		OpenGL.glFramebufferTexture2DEXT(arg0, arg2, arg1, super.anInt5449, 0);
		this.anInt3252 = arg2;
		this.anInt3254 = arg0;
	}

	@OriginalMember(owner = "client!im", name = "a", descriptor = "(I)V")
	@Override
	public void detach() {
		OpenGL.glFramebufferTexture2DEXT(this.anInt3254, this.anInt3252, 3553, 0, 0);
		this.anInt3252 = -1;
		this.anInt3254 = -1;
	}

@OriginalMember(owner = "client!im", name = "z", descriptor = "I")
	public static int anInt3253 = -1;
@OriginalMember(owner = "client!im", name = "B", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_69 = new Class83("Please wait - attempting to reestablish.", "Bitte warte - es wird versucht, die Verbindung wiederherzustellen.", "Veuillez patienter - tentative de rétablissement.", "Tentando reestabelecer conexão. Aguarde.");
@OriginalMember(owner = "client!im", name = "D", descriptor = "I")
	public static int anInt3256 = 100;
@OriginalMember(owner = "client!im", name = "F", descriptor = "I")
	public static int moveToVariableSpeed = 0;
@OriginalMember(owner = "client!im", name = "G", descriptor = "I")
	public static int anInt3259 = 0;
}
