package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.Class83;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class215;
import com.jagex.game.runetek5.deob.Class22;

import com.jagex.game.runetek5.entity.*;
@OriginalClass("client!gn")
public final class EntityNodeList {

	@OriginalMember(owner = "client!gn", name = "k", descriptor = "Lclient!md;")
	private EntityNode pointer;

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "Lclient!md;")
	private final EntityNode head = new EntityNode();

	static {
		new Class83("You have been removed from this channel.", "Du wurdest aus dem Chatraum entfernt.", "Vous avez été supprimé de ce canal.", "Você foi retirado desse canal.");
	}

	@OriginalMember(owner = "client!gn", name = "<init>", descriptor = "()V")
	public EntityNodeList() {
		this.head.aClass3_23 = this.head;
		this.head.aClass3_24 = this.head;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(ILclient!md;)V")
	public void offer(@OriginalArg(1) EntityNode arg0) {
		if (arg0.aClass3_24 != null) {
			arg0.method5676();
		}
		arg0.aClass3_23 = this.head;
		arg0.aClass3_24 = this.head.aClass3_24;
		arg0.aClass3_24.aClass3_23 = arg0;
		arg0.aClass3_23.aClass3_24 = arg0;
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(B)Lclient!md;")
	public EntityNode method2306() {
		@Pc(12) EntityNode local12 = this.head.aClass3_23;
		if (local12 == this.head) {
			return null;
		} else {
			local12.method5676();
			return local12;
		}
	}

	@OriginalMember(owner = "client!gn", name = "b", descriptor = "(B)I")
	public int method2307() {
		@Pc(7) int local7 = 0;
		@Pc(19) EntityNode local19 = this.head.aClass3_23;
		while (local19 != this.head) {
			local19 = local19.aClass3_23;
			local7++;
		}
		return local7;
	}

	@OriginalMember(owner = "client!gn", name = "c", descriptor = "(B)Lclient!md;")
	public EntityNode method2308() {
		@Pc(7) EntityNode local7 = this.head.aClass3_24;
		if (this.head == local7) {
			this.pointer = null;
			return null;
		} else {
			this.pointer = local7.aClass3_24;
			return local7;
		}
	}

	@OriginalMember(owner = "client!gn", name = "d", descriptor = "(B)Lclient!md;")
	public EntityNode method2309() {
		@Pc(13) EntityNode local13 = this.pointer;
		if (local13 == this.head) {
			this.pointer = null;
			return null;
		} else {
			this.pointer = local13.aClass3_23;
			return local13;
		}
	}

	@OriginalMember(owner = "client!gn", name = "a", descriptor = "(I)V")
	public void method2310() {
		while (true) {
			@Pc(9) EntityNode local9 = this.head.aClass3_23;
			if (local9 == this.head) {
				this.pointer = null;
				return;
			}
			local9.method5676();
		}
	}

	@OriginalMember(owner = "client!gn", name = "b", descriptor = "(I)Lclient!md;")
	public EntityNode method2312() {
		@Pc(14) EntityNode local14 = this.head.aClass3_23;
		if (this.head == local14) {
			this.pointer = null;
			return null;
		} else {
			this.pointer = local14.aClass3_23;
			return local14;
		}
	}

	@OriginalMember(owner = "client!gn", name = "f", descriptor = "(B)Z")
	public boolean method2313() {
		return this.head == this.head.aClass3_23;
	}

@OriginalMember(owner = "client!gn", name = "m", descriptor = "I")
	public static int anInt2738;
@OriginalMember(owner = "client!gn", name = "f", descriptor = "Lclient!sl;")
	public static final Class215 aClass215_35 = new Class215(81, 4);
@OriginalMember(owner = "client!gn", name = "l", descriptor = "Lclient!bg;")
	public static final Class22 IF_BUTTON8 = new Class22(62, 8);
}
