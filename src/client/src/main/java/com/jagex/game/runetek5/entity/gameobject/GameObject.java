package com.jagex.game.runetek5.entity.gameobject;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.graphics.model.Model;

@OriginalClass("client!f")
public interface GameObject {

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(B)I")
	int method5347();

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(Lclient!ya;I)V")
	void removeShadow(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit);

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(ZLclient!ya;)V")
	void method5349(@OriginalArg(1) com.jagex.game.runetek5.graphics.Toolkit arg0);

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(Z)I")
	int method5350();

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(I)Z")
	boolean hardShadow();

	@OriginalMember(owner = "client!f", name = "a", descriptor = "(Lclient!ya;II)Lclient!t;")
	Model method5352(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) int arg1);

	@OriginalMember(owner = "client!f", name = "b", descriptor = "(I)V")
	void upload();

	@OriginalMember(owner = "client!f", name = "c", descriptor = "(I)I")
	int method5354();
}
