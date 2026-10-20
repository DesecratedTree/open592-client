package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import java.awt.Container;
import java.awt.Insets;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class126;
import com.jagex.game.runetek5.deob.Class120_Sub1;
import com.jagex.game.runetek5.config.enumtype.EnumType;
import com.jagex.game.runetek5.graphics.texture.water.BillowNoiseGenerator;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;
import com.jagex.game.runetek5.cache.node.lru.MonoStereoData;
import com.jagex.game.runetek5.deob.Class269;
import com.jagex.game.runetek5.toolkit.opengl.effect.BreakingWaterEffect;
import com.jagex.game.runetek5.cache.node.HashTable;
import com.jagex.game.runetek5.deob.Class210;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub31;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.cache.FileOnDisk;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.cache.node.GpuPointLight;
import com.jagex.game.runetek5.deob.Class164;
import com.jagex.game.runetek5.graphics.model.normal.ModelNormalsGL;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.graphics.model.particle.EffectiveVertex;
@OriginalClass("client!cc")
public abstract class TextureCombinerOp {

	@OriginalMember(owner = "client!cc", name = "a", descriptor = "I")
	public final int anInt5152;

	@OriginalMember(owner = "client!cc", name = "c", descriptor = "I")
	protected final int anInt5154;

	@OriginalMember(owner = "client!cc", name = "e", descriptor = "I")
	public final int anInt5155;

	static {
		new Class83("Unable to add friend - system busy.", "Der Freund konnte nicht hinzugefügt werden, das System ist derzeit ausgelastet.", "Impossible d'ajouter un ami - système occupé.", "Não foi possível adicionar o amigo. O sistema está ocupado.");
	}

	@OriginalMember(owner = "client!cc", name = "<init>", descriptor = "(III)V")
	protected TextureCombinerOp(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		this.anInt5152 = arg1;
		this.anInt5154 = arg2;
		this.anInt5155 = arg0;
	}

	@OriginalMember(owner = "client!cc", name = "a", descriptor = "(III)V")
	public abstract void method3935(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1);

	@OriginalMember(owner = "client!cc", name = "b", descriptor = "(III)V")
	public abstract void method3936(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1);

	@OriginalMember(owner = "client!cc", name = "c", descriptor = "(III)V")
	public abstract void method3939(@OriginalArg(0) int arg0, @OriginalArg(2) int arg1);

@OriginalMember(owner = "client!cc", name = "f", descriptor = "I")
	public static int anInt5156;
@OriginalMember(owner = "client!cc", name = "g", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_54;
@OriginalMember(owner = "client!cc", name = "d", descriptor = "Lclient!ho;")
	public static final QuickChatValueType aClass98_11 = new QuickChatValueType(6, 0, 4, 2);
@OriginalMember(owner = "client!cc", name = "a", descriptor = "(Z)V")
	public static void method3938() {
		if (BillowNoiseGenerator.aFrame3 != null) {
			return;
		}
		@Pc(13) Container local13;
		if (ModelNormalsGL.aFrame1 == null) {
			local13 = Class126.aClass66_4.anApplet1;
		} else {
			local13 = ModelNormalsGL.aFrame1;
		}
		Class4_Sub6_Sub37.anInt7000 = local13.getSize().width;
		Static17.anInt222 = local13.getSize().height;
		@Pc(31) Insets local31;
		if (ModelNormalsGL.aFrame1 == local13) {
			local31 = ModelNormalsGL.aFrame1.getInsets();
			Class4_Sub6_Sub37.anInt7000 -= local31.left + local31.right;
			Static17.anInt222 -= local31.bottom + local31.top;
		}
		if (Class269.method5664() == 1) {
			EffectiveVertex.anInt5346 = HashTable.anInt3148;
			GpuPointLight.anInt2881 = Class210.anInt6121;
			FileOnDisk.anInt4424 = (Class4_Sub6_Sub37.anInt7000 - Class210.anInt6121) / 2;
			Class120_Sub1.anInt5363 = 0;
		} else if (BreakingWaterEffect.anInt1503 < 96 && Packet.anInt2973 == 0) {
			@Pc(100) int local100 = Class4_Sub6_Sub37.anInt7000 > 1024 ? 1024 : Class4_Sub6_Sub37.anInt7000;
			GpuPointLight.anInt2881 = local100;
			@Pc(109) int local109 = Static17.anInt222 <= 768 ? Static17.anInt222 : 768;
			FileOnDisk.anInt4424 = (Class4_Sub6_Sub37.anInt7000 - local100) / 2;
			EffectiveVertex.anInt5346 = local109;
			Class120_Sub1.anInt5363 = 0;
		} else {
			EffectiveVertex.anInt5346 = Static17.anInt222;
			GpuPointLight.anInt2881 = Class4_Sub6_Sub37.anInt7000;
			Class120_Sub1.anInt5363 = 0;
			FileOnDisk.anInt4424 = 0;
		}
		if (GlToolkit.aClass219_2 != Class222.aClass219_6) {
			@Pc(132) boolean local132;
			if (GpuPointLight.anInt2881 < 1024 && EffectiveVertex.anInt5346 < 768) {
				local132 = true;
			} else {
				local132 = false;
			}
		}
		Class164.aCanvas5.setSize(GpuPointLight.anInt2881, EffectiveVertex.anInt5346);
		if (MonoStereoData.aClass19_16 != null) {
			MonoStereoData.aClass19_16.releaseSurface(Class164.aCanvas5);
		}
		if (local13 == ModelNormalsGL.aFrame1) {
			local31 = ModelNormalsGL.aFrame1.getInsets();
			Class164.aCanvas5.setLocation(FileOnDisk.anInt4424 + local31.left, local31.top - -Class120_Sub1.anInt5363);
		} else {
			Class164.aCanvas5.setLocation(FileOnDisk.anInt4424, Class120_Sub1.anInt5363);
		}
		if (EnumType.anInt5766 != -1) {
			SpotAnimationTypeList.method4422(true);
		}
		Class4_Sub6_Sub31.method4697();
	}
}
