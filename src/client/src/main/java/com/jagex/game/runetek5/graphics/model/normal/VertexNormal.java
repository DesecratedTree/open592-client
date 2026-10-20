package com.jagex.game.runetek5.graphics.model.normal;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


@OriginalClass("client!gp")
public final class VertexNormal {

	@OriginalMember(owner = "client!gp", name = "a", descriptor = "I")
	public int x;

	@OriginalMember(owner = "client!gp", name = "b", descriptor = "I")
	public int y;

	@OriginalMember(owner = "client!gp", name = "c", descriptor = "I")
	public int z;

	@OriginalMember(owner = "client!gp", name = "d", descriptor = "I")
	public int magnitude;

	@OriginalMember(owner = "client!gp", name = "<init>", descriptor = "()V")
	public VertexNormal() {
	}

	@OriginalMember(owner = "client!gp", name = "<init>", descriptor = "(Lclient!gp;)V")
	public VertexNormal(@OriginalArg(0) VertexNormal vertexNormal) {
		this.x = vertexNormal.x;
		this.y = vertexNormal.y;
		this.z = vertexNormal.z;
		this.magnitude = vertexNormal.magnitude;
	}
}
