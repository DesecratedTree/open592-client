package com.jagex.game.runetek5.deob;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.cache.node.lru.LRUCache;
import com.jagex.game.runetek5.entity.EntityNodeList;

public final class Static384 {

	@OriginalMember(owner = "client!tk", name = "c", descriptor = "Lclient!gn;")
	public static EntityNodeList aClass84_8;

	@OriginalMember(owner = "client!tk", name = "d", descriptor = "Lclient!up;")
	public static Class244 aClass244_1;

	@OriginalMember(owner = "client!tk", name = "e", descriptor = "I")
	public static int anInt6470;

	@OriginalMember(owner = "client!tk", name = "a", descriptor = "Lclient!gk;")
	public static final Class83 aClass83_131 = new Class83("shake:", "schütteln:", "tremblement:", "tremor:");

	@OriginalMember(owner = "client!tk", name = "b", descriptor = "Lclient!fa;")
	public static final LRUCache aClass68_47 = new LRUCache(20);
}
