package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.Skybox;
import com.jagex.game.runetek5.buffer.Packet;
import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.config.param.ParamTypeList;
import com.jagex.game.runetek5.config.sun.Sun;
import com.jagex.game.runetek5.js5.Js5ResourceProvider;

import java.awt.Rectangle;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.*;
@OriginalClass("client!sn")
public final class Class217 {

	@OriginalMember(owner = "client!sn", name = "h", descriptor = "Lclient!fa;")
	private final LRUCache aClass68_44 = new LRUCache(16);

	@OriginalMember(owner = "client!sn", name = "c", descriptor = "Lclient!fs;")
	private final Js5ResourceProvider aClass76_84;

	static {
		new Class83("I can't reach that!", "Da kommst du nicht hin.", "Je ne peux pas l'atteindre !", "Não consigo alcançar!");
	}

	@OriginalMember(owner = "client!sn", name = "<init>", descriptor = "(Lclient!jk;ILclient!fs;)V")
	public Class217(@OriginalArg(0) Game arg0, @OriginalArg(1) int arg1, @OriginalArg(2) Js5ResourceProvider arg2) {
		this.aClass76_84 = arg2;
		this.aClass76_84.method2108(29);
	}

	@OriginalMember(owner = "client!sn", name = "a", descriptor = "(B)V")
	public void method4931() {
		@Pc(12) LRUCache local12 = this.aClass68_44;
		synchronized (this.aClass68_44) {
			this.aClass68_44.method1791();
		}
	}

	@OriginalMember(owner = "client!sn", name = "a", descriptor = "(I)V")
	public void method4932() {
		@Pc(2) LRUCache local2 = this.aClass68_44;
		synchronized (this.aClass68_44) {
			this.aClass68_44.method1777();
		}
	}

	@OriginalMember(owner = "client!sn", name = "a", descriptor = "(II)V")
	public void method4933() {
		@Pc(5) LRUCache local5 = this.aClass68_44;
		synchronized (this.aClass68_44) {
			this.aClass68_44.method1788(5);
		}
	}

	@OriginalMember(owner = "client!sn", name = "a", descriptor = "(IIIIILclient!tf;)Lclient!un;")
	public Skybox method4934(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2, @OriginalArg(4) int arg3, @OriginalArg(5) ParamTypeList arg4) {
		@Pc(12) Sun[] local12 = null;
		@Pc(17) Class170 local17 = this.method4935(arg1);
		if (local17.anIntArray341 != null) {
			local12 = new Sun[local17.anIntArray341.length];
			for (@Pc(27) int local27 = 0; local27 < local12.length; local27++) {
				@Pc(37) Class58 local37 = arg4.method5025(local17.anIntArray341[local27]);
				local12[local27] = new Sun(local37.anInt1719, local37.anInt1723, local37.anInt1729, local37.anInt1726, local37.anInt1728, local37.anInt1730, local37.anInt1731, local37.aBoolean160);
			}
		}
		return new Skybox(local17.anInt5190, local12, local17.anInt5189, arg0, arg2, arg3);
	}

	@OriginalMember(owner = "client!sn", name = "a", descriptor = "(IB)Lclient!oh;")
	private Class170 method4935(@OriginalArg(0) int arg0) {
		@Pc(6) LRUCache local6 = this.aClass68_44;
		@Pc(16) Class170 local16;
		synchronized (this.aClass68_44) {
			local16 = (Class170) this.aClass68_44.method1787((long) arg0);
		}
		if (local16 != null) {
			return local16;
		}
		@Pc(33) byte[] local33 = this.aClass76_84.method2104(arg0, 29);
		local16 = new Class170();
		if (local33 != null) {
			local16.method3966(new Packet(local33));
		}
		@Pc(57) LRUCache local57 = this.aClass68_44;
		synchronized (this.aClass68_44) {
			this.aClass68_44.method1779((long) arg0, local16);
			return local16;
		}
	}

@OriginalMember(owner = "client!sn", name = "g", descriptor = "I")
	public static int anInt6319;
@OriginalMember(owner = "client!sn", name = "i", descriptor = "I")
	public static int anInt6320 = -1;
@OriginalMember(owner = "client!sn", name = "j", descriptor = "[Ljava/awt/Rectangle;")
	public static final Rectangle[] aRectangleArray2 = new Rectangle[100];
}
