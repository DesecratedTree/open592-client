package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.model.particle.ParticleBinaryTree;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
public final class Static281 {

	@OriginalMember(owner = "client!oe", name = "a", descriptor = "Lclient!pk;")
	public static final Class183 aClass183_34 = new Class183();

	@OriginalMember(owner = "client!oe", name = "b", descriptor = "Lclient!uu;")
	public static com.jagex.game.runetek5.config.rsinterface.Component aClass247_12 = null;

	@OriginalMember(owner = "client!oe", name = "d", descriptor = "I")
	public static final int anInt5183 = 1405;

	@OriginalMember(owner = "client!oe", name = "a", descriptor = "(I)Lclient!kf;")
	public static Class78_Sub1 method3960() {
		return ParticleBinaryTree.aClass78_Sub1Array1.length > AbstractArbBufferGL.anInt6617 ? ParticleBinaryTree.aClass78_Sub1Array1[AbstractArbBufferGL.anInt6617++] : null;
	}
}
