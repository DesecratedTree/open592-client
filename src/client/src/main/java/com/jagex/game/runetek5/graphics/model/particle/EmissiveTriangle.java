package com.jagex.game.runetek5.graphics.model.particle;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.config.particle.ParticleProducerType;
import com.jagex.game.runetek5.deob.Class83;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Static145;
import com.jagex.game.runetek5.deob.Static283;
import com.jagex.game.runetek5.deob.Static331;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;
import com.jagex.game.runetek5.graphics.model.particle.*;
import com.jagex.game.runetek5.deob.Class229;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.Class48_Sub1;
import com.jagex.game.runetek5.config.sequence.spot.SpotAnimationTypeList;
import com.jagex.game.runetek5.ParticleList;
import com.jagex.game.runetek5.deob.WaterfallEffect;
import com.jagex.game.runetek5.JavaThreadResource;
import com.jagex.game.runetek5.graphics.sprite.SpriteType;
import com.jagex.game.runetek5.scene.ground.Ground;
import com.jagex.game.runetek5.deob.Class222;
import com.jagex.game.runetek5.deob.Class92;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.botdetection.ReflectionCheck;
import com.jagex.game.runetek5.config.rsinterface.Component;
@OriginalClass("client!bu")
public final class EmissiveTriangle {

	@OriginalMember(owner = "client!bu", name = "c", descriptor = "I")
	public int anInt699;

	@OriginalMember(owner = "client!bu", name = "d", descriptor = "I")
	public int anInt700;

	@OriginalMember(owner = "client!bu", name = "e", descriptor = "I")
	public int anInt701;

	@OriginalMember(owner = "client!bu", name = "f", descriptor = "I")
	public int anInt702;

	@OriginalMember(owner = "client!bu", name = "g", descriptor = "Lclient!bu;")
	public EmissiveTriangle aClass29_1;

	@OriginalMember(owner = "client!bu", name = "h", descriptor = "I")
	public int anInt703;

	@OriginalMember(owner = "client!bu", name = "l", descriptor = "I")
	public int anInt707;

	@OriginalMember(owner = "client!bu", name = "m", descriptor = "I")
	public int anInt708;

	@OriginalMember(owner = "client!bu", name = "n", descriptor = "I")
	public int anInt709;

	@OriginalMember(owner = "client!bu", name = "p", descriptor = "I")
	public int anInt711;

	@OriginalMember(owner = "client!bu", name = "u", descriptor = "B")
	public final byte priority;

	@OriginalMember(owner = "client!bu", name = "s", descriptor = "I")
	public final int anInt713;

	@OriginalMember(owner = "client!bu", name = "k", descriptor = "I")
	public final int anInt706;

	@OriginalMember(owner = "client!bu", name = "t", descriptor = "I")
	private final int id;

	@OriginalMember(owner = "client!bu", name = "b", descriptor = "I")
	public final int anInt698;

	static {
		new Class83("You can spot a Jagex moderator by the gold crown next to their name.", "Jagex-Mitarbeiter haben eine goldene Krone neben ihrem Namen.", "Vous pouvez reconnaître les modérateurs Jagex à la couronne dorée en regard de leur nom.", "Os moderadores da Jagex são identificados por uma coroa dourada próxima ao \u007fnome.");
		new Class83("You are not allowed to join this user's clan channel.", "Du darfst den Chatraum dieses Benutzers nicht betreten.", "Vous n'êtes pas autorisé à rejoindre le canal de clan de cet utilisateur.", "Você não tem permissão para entrar no canal de clã desse usuário.");
	}

	@OriginalMember(owner = "client!bu", name = "<init>", descriptor = "(IIIIB)V")
	public EmissiveTriangle(@OriginalArg(0) int producerId, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3, @OriginalArg(4) byte priority) {
		this.priority = priority;
		this.anInt713 = arg2;
		this.anInt706 = arg1;
		this.id = producerId;
		this.anInt698 = arg3;
	}

	@OriginalMember(owner = "client!bu", name = "<init>", descriptor = "(Lclient!bu;I)V")
	public EmissiveTriangle(@OriginalArg(0) EmissiveTriangle arg0, @OriginalArg(1) int arg1) {
		this.aClass29_1 = arg0;
		this.id = this.aClass29_1.id;
		this.priority = this.aClass29_1.priority;
		this.anInt698 = this.aClass29_1.anInt698 + arg1;
		this.anInt713 = this.aClass29_1.anInt713 + arg1;
		this.anInt706 = arg1 + this.aClass29_1.anInt706;
	}

	@OriginalMember(owner = "client!bu", name = "a", descriptor = "(IIII)Lclient!bu;")
	public EmissiveTriangle method694(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2) {
		return new EmissiveTriangle(this.id, arg0, arg1, arg2, this.priority);
	}

	@OriginalMember(owner = "client!bu", name = "b", descriptor = "(B)Lclient!jv;")
	public ParticleProducerType method695() {
		return WaterfallEffect.method3927(this.id);
	}

@OriginalMember(owner = "client!bu", name = "r", descriptor = "Lclient!fs;")
	public static Js5ResourceProvider aClass76_9;
@OriginalMember(owner = "client!bu", name = "w", descriptor = "I")
	public static int anInt715;
@OriginalMember(owner = "client!bu", name = "v", descriptor = "[I")
	public static final int[] anIntArray41 = new int[1];
@OriginalMember(owner = "client!bu", name = "a", descriptor = "(Lclient!ya;Lclient!fs;ILclient!fs;)V")
	public static void method696(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit arg0, @OriginalArg(1) Js5ResourceProvider arg1, @OriginalArg(3) Js5ResourceProvider arg2) {
		Class229.aClass89_10 = ParticleList.method3546(Packet.anInt2985, arg2);
		Ground.aClass46_12 = arg0.createFontRenderer(Class229.aClass89_10, SpriteType.decodeGroup(arg1, Packet.anInt2985));
		ReflectionCheck.aClass89_1 = ParticleList.method3546(JavaThreadResource.anInt1122, arg2);
		Class6.aClass46_9 = arg0.createFontRenderer(ReflectionCheck.aClass89_1, SpriteType.decodeGroup(arg1, JavaThreadResource.anInt1122));
		Js5ResourceProvider.aClass89_5 = ParticleList.method3546(Static145.anInt2907, arg2);
		Static331.aClass46_10 = arg0.createFontRenderer(Js5ResourceProvider.aClass89_5, SpriteType.decodeGroup(arg1, Static145.anInt2907));
	}
@OriginalMember(owner = "client!bu", name = "a", descriptor = "(ILclient!uu;II)V")
	public static void method697(@OriginalArg(1) com.jagex.game.runetek5.config.rsinterface.Component arg0, @OriginalArg(2) int arg1, @OriginalArg(3) int arg2) {
		SpotAnimationTypeList.anInt5675 = arg2;
		Static283.aClass247_13 = arg0;
		Component.anInt6861 = arg1;
	}
@OriginalMember(owner = "client!bu", name = "a", descriptor = "(ILjava/lang/String;Ljava/lang/String;I)I")
	public static int method698(@OriginalArg(1) String arg0, @OriginalArg(2) String arg1, @OriginalArg(3) int arg2) {
		@Pc(8) int local8 = arg1.length();
		@Pc(11) int local11 = arg0.length();
		@Pc(13) int local13 = 0;
		@Pc(15) int local15 = 0;
		@Pc(17) char local17 = 0;
		@Pc(19) char local19 = 0;
		while (local13 - local17 < local8 || local11 > local15 - local19) {
			if (local8 <= local13 - local17) {
				return -1;
			}
			if (local15 - local19 >= local11) {
				return 1;
			}
			@Pc(58) char local58;
			if (local17 == '\u0000') {
				local58 = arg1.charAt(local13++);
			} else {
				local58 = local17;
			}
			@Pc(74) char local74;
			if (local19 == '\u0000') {
				local74 = arg0.charAt(local15++);
			} else {
				local74 = local19;
			}
			local17 = Class222.method4980(local58);
			local19 = Class222.method4980(local74);
			local58 = Class48_Sub1.method3753(local58, arg2);
			local74 = Class48_Sub1.method3753(local74, arg2);
			if (local58 != local74 && Character.toUpperCase(local58) != Character.toUpperCase(local74)) {
				local58 = Character.toLowerCase(local58);
				local74 = Character.toLowerCase(local74);
				if (local58 != local74) {
					return Class92.method2428(arg2, local58) - Class92.method2428(arg2, local74);
				}
			}
		}
		@Pc(137) int local137 = Math.min(local8, local11);
		for (@Pc(139) int local139 = 0; local139 < local137; local139++) {
			if (arg2 == 2) {
				local15 = local11 - local139 - 1;
				local13 = local8 - local139 - 1;
			} else {
				local15 = local139;
				local13 = local139;
			}
			@Pc(170) char local170 = arg1.charAt(local13);
			@Pc(174) char local174 = arg0.charAt(local15);
			if (local174 != local170 && Character.toUpperCase(local170) != Character.toUpperCase(local174)) {
				local170 = Character.toLowerCase(local170);
				local174 = Character.toLowerCase(local174);
				if (local170 != local174) {
					return Class92.method2428(arg2, local170) - Class92.method2428(arg2, local174);
				}
			}
		}
		@Pc(215) int local215 = local8 - local11;
		if (local215 != 0) {
			return local215;
		}
		for (@Pc(229) int local229 = 0; local229 < local137; local229++) {
			@Pc(235) char local235 = arg1.charAt(local229);
			@Pc(239) char local239 = arg0.charAt(local229);
			if (local239 != local235) {
				return Class92.method2428(arg2, local235) - Class92.method2428(arg2, local239);
			}
		}
		return 0;
	}
}
