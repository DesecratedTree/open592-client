package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalClass;


import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.config.sequence.SeqType;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class9_Sub3;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class4_Sub10;
import com.jagex.game.runetek5.deob.Class27_Sub1;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.Country;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.graphics.NativeBillboardFace;
import com.jagex.game.runetek5.deob.Class196;
import com.jagex.game.runetek5.deob.Class230;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.graphics.sprite.SpriteGL;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.graphics.model.Model;
@OriginalClass("client!on")
public final class Class95_Sub2 extends Class95 {

	static {
		new Class83("That user is not in this channel.", "Dieser Benutzer befindet sich nicht in diesem Chatraum.", "Cet utilisateur n'est pas dans ce canal.", "Esse usuário não está no canal.");
	}

@OriginalMember(owner = "client!on", name = "e", descriptor = "[I")
	public static int[] anIntArray490;
@OriginalMember(owner = "client!on", name = "g", descriptor = "I")
	public static int anInt6410;
@OriginalMember(owner = "client!on", name = "d", descriptor = "[Lclient!lp;")
	public static final SeqType[] aClass138Array2 = new SeqType[14];
@OriginalMember(owner = "client!on", name = "f", descriptor = "I")
	public static int anInt6409 = -1;
@OriginalMember(owner = "client!on", name = "a", descriptor = "(BLclient!uu;II)V")
	public static void method5013(@OriginalArg(1) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		if (AbstractArbBufferGL.aBoolean599) {
			@Pc(181) Class133 local181 = OpenGl2DTexture.anInt5460 == -1 ? null : Class4_Sub1_Sub11.aClass180_1.method4121(OpenGl2DTexture.anInt5460);
			if (client.method900(arg0).method3903() && (Country.anInt7378 & 0x20) != 0 && (local181 == null || arg0.method5299(local181.anInt4123, OpenGl2DTexture.anInt5460) != local181.anInt4123)) {
				Static380.method5017(47, false, 0L, arg0.anInt6865, Class4_Sub10.aString28 + " -> " + arg0.aString66, true, Class230.aString62, arg0.anInt6809, arg0.anInt6779, Class164.anInt5120);
			}
			return;
		}
		@Pc(16) String local16;
		for (@Pc(9) int local9 = 9; local9 >= 5; local9--) {
			local16 = MaterialType.method5386(local9, arg0);
			if (local16 != null) {
				Static380.method5017(1002, false, (long) (local9 + 1), arg0.anInt6865, arg0.aString66, true, local16, arg0.anInt6809, arg0.anInt6779, Class9_Sub3.method1350(local9, arg0));
			}
		}
		local16 = Class196.method4431(arg0);
		if (local16 != null) {
			Static380.method5017(9, false, 0L, arg0.anInt6865, arg0.aString66, true, local16, arg0.anInt6809, arg0.anInt6779, arg0.anInt6845);
		}
		for (@Pc(79) int local79 = 4; local79 >= 0; local79--) {
			@Pc(86) String local86 = MaterialType.method5386(local79, arg0);
			if (local86 != null) {
				Static380.method5017(48, false, (long) (local79 + 1), arg0.anInt6865, arg0.aString66, true, local86, arg0.anInt6809, arg0.anInt6779, Class9_Sub3.method1350(local79, arg0));
			}
		}
		if (!client.method900(arg0).method3906()) {
			return;
		}
		if (arg0.aString64 == null) {
			Static380.method5017(60, false, 0L, arg0.anInt6865, "", true, SpriteGL.aClass83_142.method2267(NativeBillboardFace.anInt3423), arg0.anInt6809, arg0.anInt6779, -1);
		} else {
			Static380.method5017(60, false, 0L, arg0.anInt6865, "", true, arg0.aString64, arg0.anInt6809, arg0.anInt6779, -1);
		}
	}
@OriginalMember(owner = "client!on", name = "a", descriptor = "(ILclient!fs;)I")
	public static int method5014(@OriginalArg(1) Js5ResourceProvider arg0) {
		@Pc(5) int local5 = 0;
		if (arg0.method2125(Class16_Sub5.anInt6553)) {
			local5++;
		}
		if (arg0.method2125(JavaMatrix.anInt6593)) {
			local5++;
		}
		if (arg0.method2125(Class53.anInt1532)) {
			local5++;
		}
		if (arg0.method2125(PointEntity.anInt4074)) {
			local5++;
		}
		if (arg0.method2125(Static145.anInt2904)) {
			local5++;
		}
		if (arg0.method2125(Class4_Sub1_Sub8_Sub1.anInt4897)) {
			local5++;
		}
		if (arg0.method2125(GlElementsArrayBuffer.anInt5213)) {
			local5++;
		}
		if (arg0.method2125(Class215.anInt6310)) {
			local5++;
		}
		if (arg0.method2125(PlayerEntity.anInt4342)) {
			local5++;
		}
		if (arg0.method2125(Class27_Sub1.anInt649)) {
			local5++;
		}
		if (arg0.method2125(Model.anInt6286)) {
			local5++;
		}
		if (arg0.method2125(ShadowTileGL.anInt3910)) {
			local5++;
		}
		if (arg0.method2125(Class4_Sub6_Sub5.anInt1566)) {
			local5++;
		}
		if (arg0.method2125(JavaFallbackSurface.anInt5870)) {
			local5++;
		}
		if (arg0.method2125(Static410.anInt6733)) {
			local5++;
		}
		if (arg0.method2125(PlayerVarDomain.anInt3393)) {
			local5++;
		}
		return local5;
	}
}
