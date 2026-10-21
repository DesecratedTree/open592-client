package com.jagex.game.runetek5.deob;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.client.input.keyboard.KeyRecordEntry;
import com.jagex.game.runetek5.preference.Preference;

import com.jagex.game.runetek5.deob.*;
import com.jagex.game.runetek5.deob.Class60;
import com.jagex.game.runetek5.graphics.map.worldmap.WorldMapElement;
import com.jagex.game.runetek5.graphics.model.normal.MeshBillboard;
import com.jagex.game.runetek5.deob.Class154;
import com.jagex.game.runetek5.deob.Class93;
import com.jagex.game.runetek5.graphics.model.particle.PointEntity;
import com.jagex.game.runetek5.deob.Class4_Sub18;
import com.jagex.game.runetek5.cache.node.lru.Shadow;
import com.jagex.game.runetek5.deob.TextureOpBoxBlur;
import com.jagex.game.runetek5.deob.Class168;
import com.jagex.game.runetek5.deob.Class4_Sub6_Sub37;
import com.jagex.game.runetek5.deob.ObjStackEntity;
import com.jagex.game.runetek5.deob.Class10;
import com.jagex.game.runetek5.core.io.BufferedSocket;
import com.jagex.game.runetek5.deob.Class4_Sub1_Sub11;
import com.jagex.game.runetek5.cache.node.lru.CacheableNode;
import com.jagex.game.runetek5.deob.Class16_Sub1_Sub6;
import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorFill;
import com.jagex.game.runetek5.deob.Class14;
import com.jagex.game.runetek5.deob.ReflectingTextureEffect;
import com.jagex.game.runetek5.deob.Class177;
@OriginalClass("client!gf")
public final class MouseManager extends Preference implements MouseListener, MouseMotionListener {

	@OriginalMember(owner = "client!gf", name = "q", descriptor = "I")
	private int anInt2631;

	@OriginalMember(owner = "client!gf", name = "A", descriptor = "I")
	private int anInt2640;

	@OriginalMember(owner = "client!gf", name = "J", descriptor = "I")
	private int anInt2649;

	@OriginalMember(owner = "client!gf", name = "M", descriptor = "I")
	private int anInt2651;

	@OriginalMember(owner = "client!gf", name = "N", descriptor = "I")
	private int anInt2652;

	@OriginalMember(owner = "client!gf", name = "O", descriptor = "I")
	private int anInt2653;

	@OriginalMember(owner = "client!gf", name = "R", descriptor = "Ljava/awt/Component;")
	private Component aComponent1;

	@OriginalMember(owner = "client!gf", name = "v", descriptor = "Lclient!pk;")
	private Class183 aClass183_16 = new Class183();

	@OriginalMember(owner = "client!gf", name = "L", descriptor = "Lclient!pk;")
	private Class183 aClass183_17 = new Class183();

	@OriginalMember(owner = "client!gf", name = "Q", descriptor = "Z")
	private final boolean aBoolean242;

	@OriginalMember(owner = "client!gf", name = "<init>", descriptor = "(Ljava/awt/Component;Z)V")
	public MouseManager(@OriginalArg(0) Component arg0, @OriginalArg(1) boolean arg1) {
		this.method2245(arg0);
		this.aBoolean242 = arg1;
	}

	@OriginalMember(owner = "client!gf", name = "b", descriptor = "(B)I")
	@Override
	public int method2234() {
		return this.anInt2631;
	}

	@OriginalMember(owner = "client!gf", name = "e", descriptor = "(I)Z")
	@Override
	public boolean method2235() {
		return (this.anInt2649 & 0x1) != 0;
	}

	@OriginalMember(owner = "client!gf", name = "mouseExited", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseExited(@OriginalArg(0) MouseEvent arg0) {
		this.anInt2653 = -1;
		this.anInt2652 = -1;
	}

	@OriginalMember(owner = "client!gf", name = "b", descriptor = "(I)Z")
	@Override
	protected boolean method2229() {
		return (this.anInt2649 & 0x4) != 0;
	}

	@OriginalMember(owner = "client!gf", name = "b", descriptor = "(Z)V")
	private void method2239() {
		if (this.aComponent1 == null) {
			return;
		}
		this.aComponent1.removeMouseListener(this);
		this.aComponent1.removeMouseMotionListener(this);
		this.aComponent1 = null;
		this.aClass183_16 = null;
		this.anInt2652 = this.anInt2653 = this.anInt2651 = 0;
		this.anInt2631 = this.anInt2640 = this.anInt2649 = 0;
		this.aClass183_17 = null;
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Ljava/awt/event/MouseEvent;I)I")
	private int method2240(@OriginalArg(0) MouseEvent arg0) {
		@Pc(6) int local6 = arg0.getModifiers();
		@Pc(17) boolean local17 = (local6 & 0x10) != 0;
		@Pc(25) boolean local25 = (local6 & 0x8) != 0;
		@Pc(33) boolean local33 = (local6 & 0x4) != 0;
		if (local25 && (local17 || local33)) {
			local25 = false;
		}
		if (local17 && local33) {
			return 4;
		} else if (local17) {
			return 1;
		} else if (local25) {
			return 2;
		} else if (local33) {
			return 4;
		} else {
			return 0;
		}
	}

	@OriginalMember(owner = "client!gf", name = "mouseReleased", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseReleased(@OriginalArg(0) MouseEvent arg0) {
		@Pc(8) int local8 = this.method2240(arg0);
		if ((local8 & this.anInt2651) == 0) {
			local8 = this.anInt2651;
		}
		if ((local8 & 0x1) != 0) {
			this.method2243(arg0.getX(), arg0.getY(), 3);
		}
		if ((local8 & 0x4) != 0) {
			this.method2243(arg0.getX(), arg0.getY(), 5);
		}
		if ((local8 & 0x2) != 0) {
			this.method2243(arg0.getX(), arg0.getY(), 4);
		}
		this.anInt2651 &= ~local8;
		if (arg0.isPopupTrigger()) {
			arg0.consume();
		}
	}

	@OriginalMember(owner = "client!gf", name = "f", descriptor = "(I)V")
	@Override
	public synchronized void method2236() {
		this.anInt2631 = this.anInt2652;
		this.anInt2640 = this.anInt2653;
		this.anInt2649 = this.anInt2651;
		@Pc(25) Class183 local25 = this.aClass183_16;
		this.aClass183_16 = this.aClass183_17;
		this.aClass183_17 = local25;
		this.aClass183_17.method4138();
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(IIBI)V")
	private void method2243(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(3) int arg2) {
		@Pc(7) KeyRecordEntry local7 = new KeyRecordEntry();
		local7.anInt449 = arg0;
		local7.anInt455 = arg1;
		local7.anInt451 = arg2;
		local7.aLong23 = Class168.method3962();
		this.aClass183_17.method4137(local7);
	}

	@OriginalMember(owner = "client!gf", name = "mouseDragged", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseDragged(@OriginalArg(0) MouseEvent arg0) {
		this.method2244(arg0);
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Ljava/awt/event/MouseEvent;Z)V")
	private void method2244(@OriginalArg(0) MouseEvent arg0) {
		this.anInt2652 = arg0.getX();
		this.anInt2653 = arg0.getY();
		if (this.aBoolean242) {
			this.method2243(arg0.getX(), arg0.getY(), -1);
		}
	}

	@OriginalMember(owner = "client!gf", name = "mouseClicked", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseClicked(@OriginalArg(0) MouseEvent arg0) {
		if (arg0.isPopupTrigger()) {
			arg0.consume();
		}
	}

	@OriginalMember(owner = "client!gf", name = "mousePressed", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mousePressed(@OriginalArg(0) MouseEvent arg0) {
		@Pc(10) int local10 = this.method2240(arg0);
		if (local10 == 1) {
			this.method2243(arg0.getX(), arg0.getY(), 0);
		} else if (local10 == 4) {
			this.method2243(arg0.getX(), arg0.getY(), 2);
		} else if (local10 == 2) {
			this.method2243(arg0.getX(), arg0.getY(), 1);
		}
		this.anInt2651 |= local10;
		if (arg0.isPopupTrigger()) {
			arg0.consume();
		}
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Ljava/awt/Component;I)V")
	private void method2245(@OriginalArg(0) Component arg0) {
		this.method2239();
		this.aComponent1 = arg0;
		this.aComponent1.addMouseListener(this);
		this.aComponent1.addMouseMotionListener(this);
	}

	@OriginalMember(owner = "client!gf", name = "c", descriptor = "(I)I")
	@Override
	public int getDefaultValue() {
		return this.anInt2640;
	}

	/**
	 * Freshest pointer x written by the AWT callbacks, without waiting for the
	 * per-tick latch in {@link #method2236()}. Read-only: the click queue and
	 * the latched state keep their tick cadence.
	 */
	@Override
	public synchronized int getLatestX() {
		return this.anInt2652;
	}

	/** Freshest pointer y; see {@link #getLatestX()}. */
	@Override
	public synchronized int getLatestY() {
		return this.anInt2653;
	}

	@OriginalMember(owner = "client!gf", name = "mouseEntered", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseEntered(@OriginalArg(0) MouseEvent arg0) {
		this.method2244(arg0);
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(Z)V")
	@Override
	public void validate() {
		this.method2239();
	}

	@OriginalMember(owner = "client!gf", name = "c", descriptor = "(B)Z")
	@Override
	public boolean method2237() {
		return (this.anInt2649 & 0x2) != 0;
	}

	@OriginalMember(owner = "client!gf", name = "mouseMoved", descriptor = "(Ljava/awt/event/MouseEvent;)V")
	@Override
	public synchronized void mouseMoved(@OriginalArg(0) MouseEvent arg0) {
		this.method2244(arg0);
	}

	@OriginalMember(owner = "client!gf", name = "a", descriptor = "(B)Lclient!os;")
	@Override
	public Class4_Sub5 method2232() {
		return (Class4_Sub5) this.aClass183_16.method4136();
	}

@OriginalMember(owner = "client!gf", name = "k", descriptor = "[Z")
	public static boolean[] aBooleanArray12;
@OriginalMember(owner = "client!gf", name = "l", descriptor = "I")
	public static int anInt2626;
@OriginalMember(owner = "client!gf", name = "a", descriptor = "(BZ)V")
	public static void method2241(@OriginalArg(1) boolean arg0) {
		Class60.anInt1816 = 0;
		ObjStackEntity.anInt759 = 0;
		ReflectingTextureEffect.method4529();
		Class154.method3689(arg0);
		MeshBillboard.method3552();
		@Pc(33) int local33;
		for (@Pc(27) int local27 = 0; local27 < Class60.anInt1816; local27++) {
			local33 = TextureOpColorFill.anIntArray35[local27];
			if (Shadow.anInt5323 != Class93.aClass16_Sub1_Sub5_Sub2Array1[local33].anInt6015) {
				if (Class93.aClass16_Sub1_Sub5_Sub2Array1[local33].npcType.method5578()) {
					Class4_Sub6_Sub37.method5429(Class93.aClass16_Sub1_Sub5_Sub2Array1[local33]);
				}
				Class93.aClass16_Sub1_Sub5_Sub2Array1[local33].method4766(null);
				Class93.aClass16_Sub1_Sub5_Sub2Array1[local33] = null;
			}
		}
		if (Class14.aClass4_Sub12_Sub1_3.currentPosition != PointEntity.anInt4075) {
			throw new RuntimeException("gnp1 pos:" + Class14.aClass4_Sub12_Sub1_3.currentPosition + " psize:" + PointEntity.anInt4075);
		}
		for (local33 = 0; local33 < Class10.anInt163; local33++) {
			if (Class93.aClass16_Sub1_Sub5_Sub2Array1[TextureOpBoxBlur.anIntArray412[local33]] == null) {
				throw new RuntimeException("gnp2 pos:" + local33 + " size:" + Class10.anInt163);
			}
		}
	}
@OriginalMember(owner = "client!gf", name = "a", descriptor = "(IIII)Z")
	public static boolean method2242(@OriginalArg(0) int arg0, @OriginalArg(1) int arg1, @OriginalArg(2) int arg2, @OriginalArg(3) int arg3) {
		if (!Class4_Sub18.method2030(arg0, arg1, arg2)) {
			return false;
		}
		@Pc(10) int local10 = arg1 << Static231.anInt4434;
		@Pc(14) int local14 = arg2 << Static231.anInt4434;
		@Pc(23) int local23 = Class16_Sub1_Sub6.aClass65Array4[arg0].l(arg1, arg2) - 1;
		@Pc(31) int local31 = local23 - (0x78 << Static231.anInt4434 - 7);
		@Pc(39) int local39 = local23 - (0xE6 << Static231.anInt4434 - 7);
		@Pc(47) int local47 = local23 - (0xEE << Static231.anInt4434 - 7);
		if (arg3 == 1) {
			if (local10 > Class4_Sub1_Sub11.anInt3073) {
				if (!Class177.method4088(local10, local23, local14)) {
					return false;
				}
				if (!Class177.method4088(local10, local23, local14 + ReflectingTextureEffect.anInt5747)) {
					return false;
				}
				if (!Class177.method4088(local10, local23, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (arg0 > 0) {
				if (!Class177.method4088(local10, local31, local14)) {
					return false;
				}
				if (!Class177.method4088(local10, local31, local14 + ReflectingTextureEffect.anInt5747)) {
					return false;
				}
				if (!Class177.method4088(local10, local31, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (!Class177.method4088(local10, local39, local14)) {
				return false;
			} else if (Class177.method4088(local10, local39, local14 + ReflectingTextureEffect.anInt5747)) {
				return Class177.method4088(local10, local39, local14 + CacheableNode.anInt5935);
			} else {
				return false;
			}
		} else if (arg3 == 2) {
			if (local14 < Static209.anInt3921) {
				if (!Class177.method4088(local10, local23, local14 + CacheableNode.anInt5935)) {
					return false;
				}
				if (!Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local23, local14 + CacheableNode.anInt5935)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local23, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (arg0 > 0) {
				if (!Class177.method4088(local10, local31, local14 + CacheableNode.anInt5935)) {
					return false;
				}
				if (!Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local31, local14 + CacheableNode.anInt5935)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local31, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (!Class177.method4088(local10, local39, local14 + CacheableNode.anInt5935)) {
				return false;
			} else if (Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local39, local14 + CacheableNode.anInt5935)) {
				return Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14 + CacheableNode.anInt5935);
			} else {
				return false;
			}
		} else if (arg3 == 4) {
			if (local10 < Class4_Sub1_Sub11.anInt3073) {
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local23, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local23, local14 + ReflectingTextureEffect.anInt5747)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local23, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (arg0 > 0) {
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local31, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local31, local14 + ReflectingTextureEffect.anInt5747)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local31, local14 + CacheableNode.anInt5935)) {
					return false;
				}
			}
			if (!Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14)) {
				return false;
			} else if (Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14 + ReflectingTextureEffect.anInt5747)) {
				return Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14 + CacheableNode.anInt5935);
			} else {
				return false;
			}
		} else if (arg3 == 8) {
			if (local14 > Static209.anInt3921) {
				if (!Class177.method4088(local10, local23, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local23, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local23, local14)) {
					return false;
				}
			}
			if (arg0 > 0) {
				if (!Class177.method4088(local10, local31, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local31, local14)) {
					return false;
				}
				if (!Class177.method4088(local10 + CacheableNode.anInt5935, local31, local14)) {
					return false;
				}
			}
			if (!Class177.method4088(local10, local39, local14)) {
				return false;
			} else if (Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local39, local14)) {
				return Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14);
			} else {
				return false;
			}
		} else if (!Class177.method4088(local10 + ReflectingTextureEffect.anInt5747, local47, local14 + ReflectingTextureEffect.anInt5747)) {
			return false;
		} else if (arg3 == 16) {
			return Class177.method4088(local10, local39, local14 + CacheableNode.anInt5935);
		} else if (arg3 == 32) {
			return Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14 + CacheableNode.anInt5935);
		} else if (arg3 == 64) {
			return Class177.method4088(local10 + CacheableNode.anInt5935, local39, local14);
		} else if (arg3 == 128) {
			return Class177.method4088(local10, local39, local14);
		} else {
			return true;
		}
	}
@OriginalMember(owner = "client!gf", name = "a", descriptor = "(III)Z")
	public static boolean method2246(@OriginalArg(1) int arg0, @OriginalArg(2) int arg1) {
		return (arg1 & 0x40000) != 0 | WorldMapElement.method934(arg1, arg0) || BufferedSocket.method137(arg0, arg1);
	}
@OriginalMember(owner = "client!gf", name = "a", descriptor = "(I[Ljava/lang/String;)[Ljava/lang/String;")
	public static String[] method2247(@OriginalArg(1) String[] arg0) {
		@Pc(8) String[] local8 = new String[5];
		for (@Pc(10) int local10 = 0; local10 < 5; local10++) {
			local8[local10] = local10 + ": ";
			if (arg0 != null && arg0[local10] != null) {
				local8[local10] = local8[local10] + arg0[local10];
			}
		}
		return local8;
	}
}
