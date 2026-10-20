package com.jagex.game.runetek5.core.io;

import java.io.InputStream;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.deob.Static145;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.sign.SignLink;
import com.jagex.game.runetek5.core.io.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub23;
import com.jagex.game.runetek5.toolkit.vertexdeclaration.VertexDeclarationComponent;
import com.jagex.game.runetek5.deob.Class259;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.FixedFunctionSpecularPass;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
@OriginalClass("client!mm")
public final class DummyInputStream extends InputStream {

	@OriginalMember(owner = "client!mm", name = "read", descriptor = "()I")
	@Override
	public int read() {
		Class259.method5503(30000L);
		return -1;
	}

@OriginalMember(owner = "client!mm", name = "c", descriptor = "Lclient!et;")
	public static SignLink aClass66_6;
@OriginalMember(owner = "client!mm", name = "f", descriptor = "[I")
	public static int[] anIntArray303;
@OriginalMember(owner = "client!mm", name = "a", descriptor = "[F")
	public static final float[] aFloatArray24 = new float[] { 0.0F, -1.0F, 0.0F, 0.0F };
@OriginalMember(owner = "client!mm", name = "d", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_53 = new Class215(16, 15);
@OriginalMember(owner = "client!mm", name = "b", descriptor = "(I)V")
	public static void method3623() {
		if (VertexDeclarationComponent.anInt6667 == 10 || VertexDeclarationComponent.anInt6667 == 28) {
			GlBitBuffer.method2558(Class4_Sub23.anInt3197 >> 10, Static145.anInt2906 >> 10, 5000);
		} else {
			@Pc(16) int local16 = Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseX[0] >> 3;
			@Pc(23) int local23 = Static1.aClass16_Sub1_Sub5_Sub1_1.movementBaseY[0] >> 3;
			if (local16 >= 0 && GLBillowTextureData.anInt5666 >> 3 > local16 && local23 >= 0 && local23 < Static283.anInt5187 >> 3) {
				GlBitBuffer.method2558(local16, local23, 5000);
			} else {
				GlBitBuffer.method2558(GLBillowTextureData.anInt5666 >> 4, Static283.anInt5187 >> 4, 0);
			}
		}
		RemoteFileWorker.method3519();
		Class10.method127();
		PlayerVarDomain.method2833();
		FixedFunctionSpecularPass.method4231();
	}
}
