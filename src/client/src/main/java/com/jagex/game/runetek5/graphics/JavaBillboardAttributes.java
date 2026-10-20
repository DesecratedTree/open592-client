package com.jagex.game.runetek5.graphics;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static368;
import com.jagex.game.runetek5.graphics.*;
import com.jagex.game.runetek5.client.GameShell;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlBitBuffer;
import com.jagex.game.runetek5.graphics.FramebufferCompositor;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class16_Sub2;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatch;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub25;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.client.PingWorker;
import com.jagex.game.runetek5.EnvironmentSampler;
import com.jagex.game.runetek5.graphics.model.particle.ParticleNode;
import com.jagex.game.runetek5.graphics.texture.water.GLBillowTextureData;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
@OriginalClass("client!no")
public final class JavaBillboardAttributes {

	@OriginalMember(owner = "client!no", name = "d", descriptor = "I")
	public int anInt5041;

	@OriginalMember(owner = "client!no", name = "e", descriptor = "I")
	public int offsetY;

	@OriginalMember(owner = "client!no", name = "g", descriptor = "I")
	public int anInt5044;

	@OriginalMember(owner = "client!no", name = "i", descriptor = "I")
	public int anInt5046;

	@OriginalMember(owner = "client!no", name = "m", descriptor = "I")
	public int offsetX;

	@OriginalMember(owner = "client!no", name = "o", descriptor = "I")
	public int anInt5052;

	@OriginalMember(owner = "client!no", name = "q", descriptor = "I")
	public int rotation;

	@OriginalMember(owner = "client!no", name = "f", descriptor = "I")
	public int scaleY = 128;

	@OriginalMember(owner = "client!no", name = "b", descriptor = "I")
	public int scaleX = 128;

	@OriginalMember(owner = "client!no", name = "j", descriptor = "I")
	public int diffuseColor;

	@OriginalMember(owner = "client!no", name = "<init>", descriptor = "(I)V")
	public JavaBillboardAttributes(@OriginalArg(0) int diffuseColor) {
		this.diffuseColor = diffuseColor;
	}

	@OriginalMember(owner = "client!no", name = "<init>", descriptor = "(IIIIII)V")
	private JavaBillboardAttributes(@OriginalArg(0) int diffuseColor, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) int offsetY, @OriginalArg(5) int arg5) {
		this.offsetY = offsetY;
		this.diffuseColor = diffuseColor;
		this.scaleX = arg1;
		this.offsetX = arg3;
		this.scaleY = arg2;
		this.rotation = arg5;
	}

	@OriginalMember(owner = "client!no", name = "a", descriptor = "(I)Lclient!no;")
	public JavaBillboardAttributes method3852() {
		return new JavaBillboardAttributes(this.diffuseColor, this.scaleX, this.scaleY, this.offsetX, this.offsetY, this.rotation);
	}

	@OriginalMember(owner = "client!no", name = "a", descriptor = "(ILclient!no;)V")
	public void method3857(@OriginalArg(1) JavaBillboardAttributes arg0) {
		this.diffuseColor = arg0.diffuseColor;
		this.scaleX = arg0.scaleX;
		this.scaleY = arg0.scaleY;
		this.rotation = arg0.rotation;
		this.offsetY = arg0.offsetY;
		this.offsetX = arg0.offsetX;
	}

@OriginalMember(owner = "client!no", name = "a", descriptor = "(IB)I")
	public static int method3851(@OriginalArg(0) int arg0) {
		return arg0 & 0x3FF;
	}
@OriginalMember(owner = "client!no", name = "a", descriptor = "(Ljava/lang/String;B)V")
	public static void method3853(@OriginalArg(0) String arg0) {
		if (GameShell.aClass15Array2 != null) {
			PingWorker.method5476(FramebufferCompositor.aClass215_96);
			ParticleNode.aClass4_Sub12_Sub1_5.method2551(method3856(arg0));
			ParticleNode.aClass4_Sub12_Sub1_5.method2518(arg0);
		}
	}
@OriginalMember(owner = "client!no", name = "a", descriptor = "(ZLjava/lang/String;)I")
	public static int method3854(@OriginalArg(1) String arg0) {
		@Pc(8) int local8 = arg0.length();
		@Pc(10) int local10 = 0;
		for (@Pc(12) int local12 = 0; local12 < local8; local12++) {
			local10 = Class4_Sub1_Sub11.method2605(arg0.charAt(local12)) + (local10 << 5) - local10;
		}
		return local10;
	}
@OriginalMember(owner = "client!no", name = "b", descriptor = "(I)V")
	public static void method3855() {
		if (Class16_Sub2.aFloat142 < 1024.0F) {
			Class16_Sub2.aFloat142 = 1024.0F;
		}
		while (Static368.aFloat192 >= 16384.0F) {
			Static368.aFloat192 -= 16384.0F;
		}
		if (Class16_Sub2.aFloat142 > 3072.0F) {
			Class16_Sub2.aFloat142 = 3072.0F;
		}
		while (Static368.aFloat192 < 0.0F) {
			Static368.aFloat192 += 16384.0F;
		}
		@Pc(49) int local49 = JavaMatrix.anInt6578 >> 7;
		@Pc(53) int local53 = MusicPatch.anInt5636 >> 7;
		@Pc(59) int local59 = BufferedSocket.method135(JavaMatrix.anInt6578, Class4_Sub6_Sub25.anInt4963, MusicPatch.anInt5636);
		@Pc(61) int local61 = 0;
		@Pc(87) int local87;
		if (local49 > 3 && local53 > 3 && local49 < GLBillowTextureData.anInt5666 - 4 && local53 < Static283.anInt5187 - 4) {
			for (local87 = local49 - 4; local87 <= local49 + 4; local87++) {
				for (@Pc(93) int local93 = local53 - 4; local93 <= local53 + 4; local93++) {
					@Pc(97) int local97 = Class4_Sub6_Sub25.anInt4963;
					if (local97 < 3 && EnvironmentSampler.method3229(local87, local93)) {
						local97++;
					}
					@Pc(110) int local110 = 0;
					if (Class4_Sub6_Sub4.aClass266_Sub1_1.cameraHeights != null && Class4_Sub6_Sub4.aClass266_Sub1_1.cameraHeights[local97] != null) {
						local110 = (Class4_Sub6_Sub4.aClass266_Sub1_1.cameraHeights[local97][local87][local93] & 0xFF) * 8;
					}
					@Pc(143) int local143 = local110 + local59 - Class16_Sub1_Sub6.aClass65Array4[local97].l(local87, local93);
					if (local61 < local143) {
						local61 = local143;
					}
				}
			}
		}
		local87 = (local61 >> 0) * 1536;
		if (local87 > 786432) {
			local87 = 786432;
		}
		if (local87 < 262144) {
			local87 = 262144;
		}
		if (local87 > GlBitBuffer.anInt3012) {
			GlBitBuffer.anInt3012 += (local87 - GlBitBuffer.anInt3012) / 24;
		} else if (GlBitBuffer.anInt3012 > local87) {
			GlBitBuffer.anInt3012 += (local87 - GlBitBuffer.anInt3012) / 80;
		}
	}
@OriginalMember(owner = "client!no", name = "b", descriptor = "(Ljava/lang/String;B)I")
	public static int method3856(@OriginalArg(0) String arg0) {
		return arg0.length() + 1;
	}
}
