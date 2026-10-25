package com.jagex.game.runetek5.toolkit.opengl;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.unpack_Sub1;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.material.MaterialType;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.deob.Class83;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

import com.jagex.game.runetek5.LocType;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.deob.Static2;
import com.jagex.game.runetek5.deob.Static214;
import com.jagex.game.runetek5.deob.Static267;
import com.jagex.game.runetek5.entity.gameobject.GameObject;
import com.jagex.game.runetek5.toolkit.opengl.*;
import com.jagex.game.runetek5.deob.RemoteFileWorker;
import com.jagex.game.runetek5.deob.Class133;
import com.jagex.game.runetek5.deob.Class70;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.deob.Class4_Sub19;
import com.jagex.game.runetek5.sound.jagtheora.MusicPatchNode;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub4;
import com.jagex.game.runetek5.deob.Class26;
import com.jagex.game.runetek5.js5.request.FileWorker;
import com.jagex.game.runetek5.config.identitykit.IdkType;
@OriginalClass("client!ee")
public final class OpenGLTextureCache {

	@OriginalMember(owner = "client!ee", name = "f", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_7 = new LRUCache(256);

	@OriginalMember(owner = "client!ee", name = "a", descriptor = "Lclient!ga;")
	private final unpack_Sub1 materialProvider;

	@OriginalMember(owner = "client!ee", name = "d", descriptor = "Lclient!fd;")
	private final GlToolkit aClass19_Sub2_12;

	static {
		new Class83("Leave request already in progress - please wait...", "Du versuchst bereits, einen Chatraum zu verlassen - bitte warte.", "Demande de sortie déjà effectuée - veuillez patienter...", "Solicitação de saída já em andamento. Aguarde...");
	}

	@OriginalMember(owner = "client!ee", name = "<init>", descriptor = "(Lclient!fd;Lclient!ga;)V")
	public OpenGLTextureCache(@OriginalArg(0) GlToolkit glToolkit, @OriginalArg(1) unpack_Sub1 textureSource) {
		this.materialProvider = textureSource;
		this.aClass19_Sub2_12 = glToolkit;
	}

	@OriginalMember(owner = "client!ee", name = "a", descriptor = "(B)V")
	public void method1545() {
		this.aClass68_7.method1788(5);
	}

	@OriginalMember(owner = "client!ee", name = "a", descriptor = "(II)Lclient!af;")
	public OpenGl2DTexture method1549(@OriginalArg(0) int arg0) {
		@Pc(17) Object local17 = this.aClass68_7.method1787((long) arg0);
		if (local17 != null) {
			// 592HD: a texture built before a quality row changed picks the new
			// state up here; a no-op when nothing changed.
			((OpenGl2DTexture) local17).hdApplyTextureQuality(
				((OpenGl2DTexture) local17).anInt5466, ((OpenGl2DTexture) local17).anInt5458);
			return (OpenGl2DTexture) local17;
		} else if (this.materialProvider.textureAvailable(arg0)) {
			@Pc(36) MaterialType local36 = this.materialProvider.getMetrics(arg0);
			@Pc(45) int local45 = local36.isSmall ? 64 : this.aClass19_Sub2_12.size;
			@Pc(80) OpenGl2DTexture local80;
			if (local36.aBoolean638 && this.aClass19_Sub2_12.method4301()) {
				@Pc(62) float[] local62 = this.materialProvider.method2394(0.7F, arg0, local45, local45);
				local80 = new OpenGl2DTexture(this.aClass19_Sub2_12, 3553, 34842, local45, local45, local36.aByte92 != 0, local62, 6408);
			} else {
				@Pc(102) short local102;
				@Pc(100) int[] local100;
				if (local36.aBoolean640 && Class133.method3349(local36.aByte89)) {
					local100 = this.materialProvider.getPixels(false, arg0, 0.7F, local45, local45);
					local102 = 6407;
				} else {
					local100 = this.materialProvider.getPixelsArgb(arg0, 0.7F, local45, local45);
					local102 = 6408;
				}
				local80 = new OpenGl2DTexture(this.aClass19_Sub2_12, 3553, local102, local45, local45, local36.aByte92 != 0, local100, false);
			}
			local80.method4196(local36.aBoolean643, local36.aBoolean644);
			// 592HD: the Mipmaps and Anisotropic rows are properties of the
			// texture, so they are applied here, once, where a world texture is
			// made - never per draw.
			local80.hdApplyTextureQuality(local45, local45);
			this.aClass68_7.method1779((long) arg0, local80);
			return local80;
		} else {
			return null;
		}
	}

	@OriginalMember(owner = "client!ee", name = "a", descriptor = "(I)V")
	public void method1550() {
		this.aClass68_7.method1777();
	}

@OriginalMember(owner = "client!ee", name = "j", descriptor = "I")
	public static int anInt1771;
@OriginalMember(owner = "client!ee", name = "h", descriptor = "[Lclient!wq;")
	public static Class269[] aClass269Array1 = new Class269[50];
@OriginalMember(owner = "client!ee", name = "k", descriptor = "[Lclient!lq;")
	public static final FileOnDisk[] aClass139Array1 = new FileOnDisk[31];
@OriginalMember(owner = "client!ee", name = "a", descriptor = "(III)I")
	public static int method1546(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1) {
		@Pc(12) int local12 = 0;
		while (arg1 > 0) {
			local12 = arg0 & 0x1 | local12 << 1;
			arg1--;
			arg0 >>>= 0x1;
		}
		return local12;
	}
@OriginalMember(owner = "client!ee", name = "b", descriptor = "(Z)V")
	public static void method1547(@OriginalArg(0) boolean arg0) {
		if (arg0) {
			Class70.aClass164ArrayArrayArray5 = Static2.aClass164ArrayArrayArray1;
			Class16_Sub1_Sub6.aClass65Array4 = FileWorker.aClass65Array3;
		} else {
			Class70.aClass164ArrayArrayArray5 = Static214.aClass164ArrayArrayArray4;
			Class16_Sub1_Sub6.aClass65Array4 = Class4_Sub6_Sub4.aClass65Array1;
		}
		RemoteFileWorker.anInt4487 = Class70.aClass164ArrayArrayArray5.length;
	}
@OriginalMember(owner = "client!ee", name = "a", descriptor = "(IIIILclient!ya;IIII)V")
	public static void method1548(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) com.jagex.game.runetek5.graphics.Toolkit arg4, @OriginalArg(5) int arg5, @OriginalArg(6) int arg6, @OriginalArg(8) int arg7) {
		@Pc(11) GameObject local11 = (GameObject) MusicPatchNode.method3449(arg0, arg5, arg2);
		@Pc(20) LocType local20;
		@Pc(26) int local26;
		@Pc(30) int local30;
		@Pc(36) int local36;
		if (local11 != null) {
			local20 = Static267.aClass262_2.method5560(local11.method5350());
			local26 = local11.method5347() & 0x3;
			local30 = local11.method5354();
			if (local20.anInt5803 == -1) {
				local36 = arg1;
				if (local20.anInt5813 > 0) {
					local36 = arg7;
				}
				if (local30 == 0 || local30 == 2) {
					if (local26 == 0) {
						arg4.method4240(arg3, 4, arg6, local36);
					} else if (local26 == 1) {
						arg4.method4256(4, arg3, arg6, local36);
					} else if (local26 == 2) {
						arg4.method4240(arg3, 4, arg6 + 3, local36);
					} else if (local26 == 3) {
						arg4.method4256(4, arg3 + 3, arg6, local36);
					}
				}
				if (local30 == 3) {
					if (local26 == 0) {
						arg4.method4293(arg6, 1, arg3, local36, 1);
					} else if (local26 == 1) {
						arg4.method4293(arg6 + 3, 1, arg3, local36, 1);
					} else if (local26 == 2) {
						arg4.method4293(arg6 + 3, 1, arg3 + 3, local36, 1);
					} else if (local26 == 3) {
						arg4.method4293(arg6, 1, arg3 + 3, local36, 1);
					}
				}
				if (local30 == 2) {
					if (local26 == 0) {
						arg4.method4256(4, arg3, arg6, local36);
					} else if (local26 == 1) {
						arg4.method4240(arg3, 4, arg6 + 3, local36);
					} else if (local26 == 2) {
						arg4.method4256(4, arg3 + 3, arg6, local36);
					} else if (local26 == 3) {
						arg4.method4240(arg3, 4, arg6, local36);
					}
				}
			} else {
				IdkType.method4094(arg4, local26, local20, arg3, arg6);
			}
		}
		local11 = (GameObject) Class26.method648(arg0, arg5, arg2, GameObject.class);
		if (local11 != null) {
			local20 = Static267.aClass262_2.method5560(local11.method5350());
			local26 = local11.method5347() & 0x3;
			local30 = local11.method5354();
			if (local20.anInt5803 != -1) {
				IdkType.method4094(arg4, local26, local20, arg3, arg6);
			} else if (local30 == 9) {
				local36 = -1118482;
				if (local20.anInt5813 > 0) {
					local36 = -1179648;
				}
				if (local26 == 0 || local26 == 2) {
					arg4.method4296(arg3 + 3, local36, arg6, arg3, arg6 + 3);
				} else {
					arg4.method4296(arg3, local36, arg6, arg3 + 3, arg6 + 3);
				}
			}
		}
		local11 = (GameObject) Class4_Sub19.method2096(arg0, arg5, arg2);
		if (local11 == null) {
			return;
		}
		local20 = Static267.aClass262_2.method5560(local11.method5350());
		local26 = local11.method5347() & 0x3;
		if (local20.anInt5803 != -1) {
			IdkType.method4094(arg4, local26, local20, arg3, arg6);
		}
	}
}
