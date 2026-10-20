package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.config.material.texture.TextureOperation;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.toolkit.opengl.effect.CubeMapSpecularEffect;
import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.sound.jagtheora.MidiPcmStream;
import com.jagex.game.runetek5.toolkit.opengl.buffer.GlElementsArrayBuffer;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub5;
import com.jagex.game.runetek5.vardomain.PlayerVarDomain;
import com.jagex.game.runetek5.deob.Class27_Sub1;
import com.jagex.game.runetek5.graphics.shadow.ShadowTileGL;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub8_Sub1;
import com.jagex.game.runetek5.deob.Class53;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.JavaMatrix;
import com.jagex.game.runetek5.entity.PlayerEntity;
import com.jagex.game.runetek5.graphics.model.Model;
import com.jagex.game.runetek5.deob.Class16_Sub5;
import com.jagex.game.runetek5.deob.JavaFallbackSurface;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
@OriginalClass("client!gv")
public final class Class4_Sub6_Sub12 extends TextureOperation {

	@OriginalMember(owner = "client!gv", name = "E", descriptor = "I")
	private int anInt2822 = 4096;

	@OriginalMember(owner = "client!gv", name = "H", descriptor = "I")
	private int anInt2824 = 0;

	static {
		new Class83("You do not have permission to kick users in this channel.", "Du darfst keine Benutzer aus diesem Chatraum rauswerfen.", "Vous n'êtes pas autorisé à expulser des utilisateurs de ce canal.", "Você não tem permissão para expulsar usuários neste canal.");
	}

	@OriginalMember(owner = "client!gv", name = "<init>", descriptor = "()V")
	public Class4_Sub6_Sub12() {
		super(1, true);
	}

	@OriginalMember(owner = "client!gv", name = "a", descriptor = "(IILclient!iv;)V")
	@Override
	public void method5518(@OriginalArg(1) int arg0, @OriginalArg(2) Packet arg1) {
		if (arg0 == 0) {
			this.anInt2824 = arg1.method2536();
		} else if (arg0 == 1) {
			this.anInt2822 = arg1.method2536();
		}
	}

	@OriginalMember(owner = "client!gv", name = "a", descriptor = "(II)[I")
	@Override
	public int[] monochromeOutput(@OriginalArg(1) int arg0) {
		@Pc(19) int[] local19 = super.monochromeImageCache.method2265(arg0);
		if (super.monochromeImageCache.dirty) {
			@Pc(29) int[] local29 = this.getChildMonochromeOutput(arg0, 0);
			for (@Pc(31) int local31 = 0; local31 < MidiPcmStream.anInt1616; local31++) {
				@Pc(37) int local37 = local29[local31];
				local19[local31] = this.anInt2824 <= local37 && this.anInt2822 >= local37 ? 4096 : 0;
			}
		}
		return local19;
	}

@OriginalMember(owner = "client!gv", name = "J", descriptor = "I")
	public static int anInt2826;
@OriginalMember(owner = "client!gv", name = "C", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_54 = new Class83(" has logged in.", " loggt sich ein.", " s'est connecté.", " entrou no jogo.");
@OriginalMember(owner = "client!gv", name = "G", descriptor = "Lclient!ib;")
	public static final CubeMapSpecularEffect aClass102_12 = new CubeMapSpecularEffect(8);
@OriginalMember(owner = "client!gv", name = "a", descriptor = "(BLclient!fs;)V")
	public static void method2373(@OriginalArg(1) Js5ResourceProvider arg0) {
		Packet.anInt2985 = arg0.method2099("p11_full");
		JavaThreadResource.anInt1122 = arg0.method2099("p12_full");
		Static145.anInt2907 = arg0.method2099("b12_full");
		Class16_Sub5.anInt6553 = arg0.method2099("hitmarks");
		JavaMatrix.anInt6593 = arg0.method2099("hitbar_default");
		Class53.anInt1532 = arg0.method2099("timerbar_default");
		PointEntity.anInt4074 = arg0.method2099("headicons_pk");
		Static145.anInt2904 = arg0.method2099("headicons_prayer");
		Class4_Sub1_Sub8_Sub1.anInt4897 = arg0.method2099("hint_headicons");
		GlElementsArrayBuffer.anInt5213 = arg0.method2099("hint_mapmarkers");
		Class215.anInt6310 = arg0.method2099("mapflag");
		PlayerEntity.anInt4342 = arg0.method2099("cross");
		Class27_Sub1.anInt649 = arg0.method2099("mapdots");
		Model.anInt6286 = arg0.method2099("scrollbar");
		ShadowTileGL.anInt3910 = arg0.method2099("name_icons");
		Class4_Sub6_Sub5.anInt1566 = arg0.method2099("floorshadows");
		JavaFallbackSurface.anInt5870 = arg0.method2099("compass");
		Static410.anInt6733 = arg0.method2099("otherlevel");
		PlayerVarDomain.anInt3393 = arg0.method2099("hint_mapedge");
	}
}
