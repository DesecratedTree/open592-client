package com.jagex.game.runetek5.toolkit.opengl;

import jaggl.OpenGL;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.deob.Class22;
import com.jagex.game.runetek5.deob.Class83;

import com.jagex.game.runetek5.toolkit.opengl.*;
@OriginalClass("client!ct")
public final class GlRenderList {

	@OriginalMember(owner = "client!ct", name = "f", descriptor = "I")
	private final int reservedRange;

	@OriginalMember(owner = "client!ct", name = "<init>", descriptor = "(Lclient!fd;I)V")
	public GlRenderList(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) int range) {
		this.reservedRange = OpenGL.glGenLists(range);
	}

	@OriginalMember(owner = "client!ct", name = "a", descriptor = "(CI)V")
	public void method989(@OriginalArg(0) char arg0) {
		OpenGL.glCallList(arg0 + this.reservedRange);
	}

	@OriginalMember(owner = "client!ct", name = "a", descriptor = "(IZ)V")
	public void method990(@OriginalArg(0) int arg0) {
		OpenGL.glNewList(this.reservedRange + arg0, 4864);
	}

	@OriginalMember(owner = "client!ct", name = "a", descriptor = "(B)V")
	public void method991() {
		OpenGL.glEndList();
	}

@OriginalMember(owner = "client!ct", name = "h", descriptor = "I")
	public static int anInt1097;
@OriginalMember(owner = "client!ct", name = "b", descriptor = "Lclient!bg;")
	public static final Class22 aClass22_63 = new Class22(99, 7);
@OriginalMember(owner = "client!ct", name = "d", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_29 = new Class83("Loading wordpack - ", "Lade Wordpack - ", "Chargement du module texte - ", "Carregando pacote de palavras - ");
@OriginalMember(owner = "client!ct", name = "e", descriptor = "I")
	public static int anInt1094 = -1;
}
