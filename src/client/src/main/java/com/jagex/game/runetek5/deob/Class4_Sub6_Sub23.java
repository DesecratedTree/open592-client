package com.jagex.game.runetek5.deob;

import java.awt.Rectangle;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import java.awt.Image;
import java.util.Calendar;
import java.util.TimeZone;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class204;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpSprite;
import com.jagex.game.runetek5.cache.node.lru.ParticleEffector;
import com.jagex.game.runetek5.toolkit.opengl.OpenGLTextureCache;
import com.jagex.game.runetek5.toolkit.opengl.buffer.AbstractArbBufferGL;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.deob.Class105;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub38;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.Terrain;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.config.struct.StructTypeList;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.MouseManager;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.deob.Class145_Sub2;
import com.jagex.game.runetek5.toolkit.opengl.EffectManagerGL;
import com.jagex.game.runetek5.deob.Class217;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlVboArrayBuffer;
import com.jagex.game.runetek5.graphics.model.GlModel;
import com.jagex.game.runetek5.config.loctype.LocTypeList;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.OpenGlArbElementArrayBuffer;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
@OriginalClass("client!mr")
public final class Class4_Sub6_Sub23 extends TextureOperation {

	@OriginalMember(owner = "client!mr", name = "H", descriptor = "[Ljava/awt/Rectangle;")
	public static final Rectangle[] aRectangleArray1 = new Rectangle[100];

	@OriginalMember(owner = "client!mr", name = "E", descriptor = "I")
	private int anInt4742 = 1;

	@OriginalMember(owner = "client!mr", name = "I", descriptor = "I")
	private int anInt4745 = 204;

	@OriginalMember(owner = "client!mr", name = "D", descriptor = "I")
	private int anInt4741 = 1;

	static {
		for (@Pc(4) int local4 = 0; local4 < 100; local4++) {
			aRectangleArray1[local4] = new Rectangle();
		}
		new Class83("To use this item please login to a members' server.", "Du musst auf einer Mitglieder-Welt sein, um diesen Gegenstand zu benutzen.", "Veuillez vous connecter à un serveur d'abonnés pour utiliser cet objet.", "Acesse um servidor para membros para usar este objeto.");
	}

	@OriginalMember(owner = "client!mr", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub23() {
		super(0, true);
	}

	@OriginalMember(owner = "client!mr", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(11) int[] local11 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			for (@Pc(25) int local25 = 0; local25 < MidiPcmStream.anInt1616; local25++) {
				@Pc(31) int local31 = AbstractArbBufferGL.anIntArray504[local25];
				@Pc(35) int local35 = ParticleEffector.anIntArray209[arg0];
				@Pc(42) int local42 = local31 * this.anInt4742 >> 12;
				@Pc(49) int local49 = this.anInt4741 * local35 >> 12;
				@Pc(59) int local59 = this.anInt4742 * (local31 % (4096 / this.anInt4742));
				@Pc(69) int local69 = local35 % (4096 / this.anInt4741) * this.anInt4741;
				if (this.anInt4745 > local69) {
					for (local42 -= local49; local42 < 0; local42 += 4) {
					}
					while (local42 > 3) {
						local42 -= 4;
					}
					if (local42 != 1) {
						local11[local25] = 0;
						continue;
					}
					if (this.anInt4745 > local59) {
						local11[local25] = 0;
						continue;
					}
				}
				if (local59 < this.anInt4745) {
					for (local42 -= local49; local42 < 0; local42 += 4) {
					}
					while (local42 > 3) {
						local42 -= 4;
					}
					if (local42 > 0) {
						local11[local25] = 0;
						continue;
					}
				}
				local11[local25] = 4096;
			}
		}
		return local11;
	}

	@OriginalMember(owner = "client!mr", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt4742 = arg1.method2490();
		} else if (arg0 == 1) {
			this.anInt4741 = arg1.method2490();
		} else if (arg0 == 2) {
			this.anInt4745 = arg1.method2536();
		}
	}

@OriginalMember(owner = "client!mr", name = "L", descriptor = "Ljava/awt/Image;")
	public static Image anImage2;
@OriginalMember(owner = "client!mr", name = "K", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_57 = new Class215(51, 4);
@OriginalMember(owner = "client!mr", name = "M", descriptor = "Ljava/util/Calendar;")
	public static final Calendar aCalendar1 = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
@OriginalMember(owner = "client!mr", name = "b", descriptor = "(Z)V")
	public static void method3676() {
		anImage2 = null;
		ParamTypeList.aFont1 = null;
	}
@OriginalMember(owner = "client!mr", name = "a", descriptor = "(IIIIIZZ)V")
	public static void method3677(@OriginalArg(2) int arg0, @OriginalArg(3) int arg1, @OriginalArg(4) int arg2, @OriginalArg(5) boolean arg3, @OriginalArg(6) boolean arg4) {
		Static231.anInt4434 = 7;
		CacheableNode.anInt5935 = 0x1 << Static231.anInt4434;
		ReflectingTextureEffect.anInt5747 = CacheableNode.anInt5935 >> 1;
		OpenGlArbElementArrayBuffer.anInt1873 = (int) Math.sqrt((double) (ReflectingTextureEffect.anInt5747 * ReflectingTextureEffect.anInt5747 + ReflectingTextureEffect.anInt5747 * ReflectingTextureEffect.anInt5747));
		HashTable.anInt3142 = CacheableNode.anInt5935 >> 2;
		Class217.anInt6319 = CacheableNode.anInt5935;
		GpuFaceNormal.menuOpenWidth = arg0;
		GlModel.anInt3361 = arg1;
		FileOnDisk.anInt4431 = arg2;
		Static214.aClass164ArrayArrayArray4 = new Class164[4][GpuFaceNormal.menuOpenWidth][GlModel.anInt3361];
		Class4_Sub6_Sub4.aClass65Array1 = new Class65[4];
		if (arg3) {
			Class145_Sub2.anIntArrayArray64 = new int[GpuFaceNormal.menuOpenWidth][GlModel.anInt3361];
			AbstractArbBufferGL.aByteArrayArray29 = new byte[GpuFaceNormal.menuOpenWidth][GlModel.anInt3361];
			Class204.aByteArrayArray26 = new byte[GpuFaceNormal.menuOpenWidth][GlModel.anInt3361];
			Static2.aClass164ArrayArrayArray1 = new Class164[1][GpuFaceNormal.menuOpenWidth][GlModel.anInt3361];
			FileWorker.aClass65Array3 = new Class65[1];
		} else {
			Class145_Sub2.anIntArrayArray64 = null;
			AbstractArbBufferGL.aByteArrayArray29 = null;
			Class204.aByteArrayArray26 = null;
			Static2.aClass164ArrayArrayArray1 = null;
			FileWorker.aClass65Array3 = null;
		}
		if (arg4) {
			Terrain.aLongArrayArrayArray2 = new long[4][arg0][arg1];
			StructTypeList.aClass191Array1 = new Class191[65535];
			MouseManager.aBooleanArray12 = new boolean[65535];
			Ground.anInt7322 = 0;
		} else {
			Terrain.aLongArrayArrayArray2 = null;
			StructTypeList.aClass191Array1 = null;
			MouseManager.aBooleanArray12 = null;
			Ground.anInt7322 = 0;
		}
		OpenGLTextureCache.method1547(false);
		TextureOpSprite.aClass136Array2 = new Class136[1000];
		OpenGlArbElementArrayBuffer.anInt1878 = 0;
		EffectManagerGL.aClass136Array3 = new Class136[1000];
		OpenGl2DTexture.anInt5461 = 0;
		Class105.anIntArrayArrayArray13 = new int[4][GpuFaceNormal.menuOpenWidth + 1][GlModel.anInt3361 + 1];
		Class4_Sub6_Sub38.aClass16_Sub1Array3 = new Class16_Sub1[5000];
		Static1.anInt6 = 0;
		GlVboArrayBuffer.aBooleanArrayArray4 = new boolean[FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 1][FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 1];
		LocTypeList.aBooleanArrayArray6 = new boolean[FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 2][FileOnDisk.anInt4431 + FileOnDisk.anInt4431 + 2];
		Static433.aClass70_1 = null;
	}
}
