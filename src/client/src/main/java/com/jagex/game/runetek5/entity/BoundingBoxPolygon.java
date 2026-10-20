package com.jagex.game.runetek5.entity;

import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


@OriginalClass("client!mi")
public final class BoundingBoxPolygon extends EntityNode {

	@OriginalMember(owner = "client!mi", name = "j", descriptor = "I")
	public int anInt4540;

	@OriginalMember(owner = "client!mi", name = "k", descriptor = "I")
	public int anInt4541;

	@OriginalMember(owner = "client!mi", name = "l", descriptor = "I")
	public int anInt4542;

	@OriginalMember(owner = "client!mi", name = "n", descriptor = "I")
	public int anInt4543;

	@OriginalMember(owner = "client!mi", name = "o", descriptor = "I")
	public int anInt4544;

	@OriginalMember(owner = "client!mi", name = "m", descriptor = "Z")
	public boolean positionCalculated = false;

	@OriginalMember(owner = "client!mi", name = "a", descriptor = "(II)Z")
	public boolean hovered(@OriginalArg(0) int mouseX, @OriginalArg(1) int mouseY) {
		if (!this.positionCalculated) {
			return false;
		}
		@Pc(10) int local10 = this.anInt4543 - this.anInt4542;
		@Pc(16) int local16 = this.anInt4541 - this.anInt4540;
		@Pc(24) int local24 = local10 * local10 + local16 * local16;
		@Pc(42) int local42 = mouseX * local10 + mouseY * local16 - this.anInt4542 * local10 - this.anInt4540 * local16;
		@Pc(49) int local49;
		@Pc(54) int local54;
		if (local42 <= 0) {
			local49 = this.anInt4542 - mouseX;
			local54 = this.anInt4540 - mouseY;
			return local49 * local49 + local54 * local54 < this.anInt4544 * this.anInt4544;
		} else if (local42 > local24) {
			local49 = this.anInt4543 - mouseX;
			local54 = this.anInt4541 - mouseY;
			return local49 * local49 + local54 * local54 < this.anInt4544 * this.anInt4544;
		} else {
			local42 = (local42 << 10) / local24;
			local49 = this.anInt4542 + (local10 * local42 >> 10) - mouseX;
			local54 = this.anInt4540 + (local16 * local42 >> 10) - mouseY;
			return local49 * local49 + local54 * local54 < this.anInt4544 * this.anInt4544;
		}
	}
}
