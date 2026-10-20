package com.jagex.game.runetek5.graphics.model.normal;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


@OriginalClass("client!ih")
public final class FaceNormal {

	@OriginalMember(owner = "client!ih", name = "a", descriptor = "I")
	public int z;

	@OriginalMember(owner = "client!ih", name = "b", descriptor = "I")
	public int y;

	@OriginalMember(owner = "client!ih", name = "c", descriptor = "I")
	public int x;

	@OriginalMember(owner = "client!ih", name = "<init>", descriptor = "()V")
	public FaceNormal() {
	}

	@OriginalMember(owner = "client!ih", name = "<init>", descriptor = "(Lclient!ih;)V")
	public FaceNormal(@OriginalArg(0) FaceNormal faceNormal) {
		this.x = faceNormal.x;
		this.y = faceNormal.y;
		this.z = faceNormal.z;
	}
}
