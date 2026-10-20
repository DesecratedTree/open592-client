package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import com.jagex.game.runetek5.config.struct.StructTypeList;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.config.npctype.NPCTypeList;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!od")
public final class Class4_Sub6_Sub26 extends TextureOperation {

	@OriginalMember(owner = "client!od", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub26() {
		super(0, true);
	}

	@OriginalMember(owner = "client!od", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		return AbstractArbBufferGL.anIntArray504;
	}

@OriginalMember(owner = "client!od", name = "E", descriptor = "Lclient!ff;")
	public static StructTypeList aClass72_1;
@OriginalMember(owner = "client!od", name = "I", descriptor = "Z")
	public static boolean aBoolean470;
@OriginalMember(owner = "client!od", name = "C", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_12 = new QuickChatValueType(13, 0, 1, 0);
@OriginalMember(owner = "client!od", name = "F", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_108 = new Class83("Loading world list data", "Lade Liste der Welten", "Chargement de la liste des serveurs", "Carregando dados da lista de mundos");
@OriginalMember(owner = "client!od", name = "G", descriptor = "[I")
	public static final int[] anIntArray339 = new int[200];
@OriginalMember(owner = "client!od", name = "J", descriptor = "[I")
	public static final int[] anIntArray340 = new int[] { 0, 2, 2, 2, 1, 1, 3, 3, 1, 3, 3, 4, 4 };
@OriginalMember(owner = "client!od", name = "K", descriptor = "[Ljava/lang/String;")
	public static final String[] aStringArray24 = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
@OriginalMember(owner = "client!od", name = "a", descriptor = "()V")
	public static void method3958() {
		NPCTypeList.method5228(RemoteFileWorker.anInt4487);
	}
}
