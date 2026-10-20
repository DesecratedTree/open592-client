package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.rsinterface.HookRequest;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.ChatFilter;
import com.jagex.game.runetek5.deob.Class243;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.StackFrame;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.deob.Class4_Sub12_Sub1;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
@OriginalClass("client!rb")
public abstract class Class16_Sub5 extends Class16 {

	@OriginalMember(owner = "client!rb", name = "m", descriptor = "I")
	protected int anInt6552;

	@OriginalMember(owner = "client!rb", name = "k", descriptor = "I")
	protected int anInt6550;

	@OriginalMember(owner = "client!rb", name = "o", descriptor = "I")
	protected final int anInt6554;

	@OriginalMember(owner = "client!rb", name = "l", descriptor = "I")
	public final int anInt6551;

	@OriginalMember(owner = "client!rb", name = "<init>", descriptor = "(IIII)V")
	protected Class16_Sub5(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		this.anInt6552 = arg2;
		this.anInt6550 = arg0;
		this.anInt6554 = arg1;
		this.anInt6551 = arg3;
	}

@OriginalMember(owner = "client!rb", name = "n", descriptor = "I")
	public static int anInt6553;
@OriginalMember(owner = "client!rb", name = "i", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_135 = new Class83("Loaded update list", "Update-Liste geladen.", "Liste des mises à jour chargée", "Lista de atualizações carregada");
@OriginalMember(owner = "client!rb", name = "p", descriptor = "Z")
	public static boolean aBoolean596 = false;
@OriginalMember(owner = "client!rb", name = "e", descriptor = "(B)V")
	public static void method5139() {
		if (!AbstractArbBufferGL.aBoolean599) {
			return;
		}
		@Pc(11) com.jagex.game.runetek5.config.rsinterface.Component local11 = EnvironmentSampler.method3230(Class243.anInt6726, StackFrame.anInt350);
		if (local11 != null && local11.anObjectArray25 != null) {
			@Pc(20) HookRequest local20 = new HookRequest();
			local20.params = local11.anObjectArray25;
			local20.source = local11;
			Static271.executeHookInner(local20);
		}
		HashTable.anInt3147 = -1;
		ChatFilter.anInt3179 = -1;
		AbstractArbBufferGL.aBoolean599 = false;
		if (local11 != null) {
			Class4_Sub12_Sub1.method1142(local11);
		}
	}
}
