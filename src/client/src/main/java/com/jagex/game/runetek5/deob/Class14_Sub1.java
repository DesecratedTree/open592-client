package com.jagex.game.runetek5.deob;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


@OriginalClass("client!as")
public final class Class14_Sub1 extends Class14 implements MouseWheelListener {

	@OriginalMember(owner = "client!as", name = "j", descriptor = "I")
	private int accumulatedWheelNotches = 0;

	@OriginalMember(owner = "client!as", name = "b", descriptor = "(B)I")
	@Override
	public synchronized int consumeWheelRotation() {
		@Pc(2) int pendingNotches = this.accumulatedWheelNotches;
		this.accumulatedWheelNotches = 0;
		return pendingNotches;
	}

	@OriginalMember(owner = "client!as", name = "mouseWheelMoved", descriptor = "(Ljava/awt/event/MouseWheelEvent;)V")
	@Override
	public synchronized void mouseWheelMoved(@OriginalArg(0) MouseWheelEvent wheelEvent) {
		this.accumulatedWheelNotches += wheelEvent.getWheelRotation();
	}

	@OriginalMember(owner = "client!as", name = "a", descriptor = "(BLjava/awt/Component;)V")
	@Override
	public void attachTo(@OriginalArg(1) Component canvas) {
		canvas.addMouseWheelListener(this);
	}

	@OriginalMember(owner = "client!as", name = "a", descriptor = "(Ljava/awt/Component;I)V")
	@Override
	public void detachFrom(@OriginalArg(0) Component canvas) {
		canvas.removeMouseWheelListener(this);
	}
}
