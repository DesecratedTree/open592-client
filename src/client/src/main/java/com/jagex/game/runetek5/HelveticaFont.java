package com.jagex.game.runetek5;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import org.openrs2.deob.annotation.OriginalArg;
import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;
import org.openrs2.deob.annotation.Pc;


import com.jagex.game.runetek5.deob.da;
import com.jagex.game.runetek5.graphics.sprite.NativeSprite;

import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.*;
import com.jagex.game.runetek5.HelveticaFont;
import com.jagex.game.runetek5.deob.Class164;
@OriginalClass("client!sb")
public final class HelveticaFont {

	@OriginalMember(owner = "client!sb", name = "h", descriptor = "[I")
	private static final int[] VALID_CHARACTERS = new int[256];

	@OriginalMember(owner = "client!sb", name = "c", descriptor = "I")
	private int startY;

	@OriginalMember(owner = "client!sb", name = "f", descriptor = "I")
	private int sizeY;

	@OriginalMember(owner = "client!sb", name = "b", descriptor = "[I")
	private final int[] clipping = new int[4];

	@OriginalMember(owner = "client!sb", name = "e", descriptor = "Z")
	private boolean loadPlain = false;

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "[Lclient!l;")
	private final NativeSprite[] nativeSprites;

	@OriginalMember(owner = "client!sb", name = "g", descriptor = "[I")
	private final int[] characterWidths;

	static {
		for (@Pc(7) int local7 = 0; local7 < 256; local7++) {
			@Pc(12) int local12 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ÄËÏÖÜäëïöüÿßÁÀÉÈÍÌÓÒÚÙáàéèíìóòúùÂÊÎÔÛâêîôûÆæãÃõÕçÇ".indexOf(local7);
			if (local12 == -1) {
				local12 = 74;
			}
			VALID_CHARACTERS[local7] = local12;
		}
	}

	@OriginalMember(owner = "client!sb", name = "<init>", descriptor = "(Lclient!ya;IZLjava/awt/Component;)V")
	public HelveticaFont(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) int size, @OriginalArg(2) boolean arg2, @OriginalArg(3) Component component) {
		this.loadPlain = false;
		this.nativeSprites = new NativeSprite[256];
		this.characterWidths = new int[256];
		@Pc(30) Font local30 = new Font("Helvetica", 1, size);
		@Pc(34) FontMetrics local34 = component.getFontMetrics(local30);
		for (@Pc(36) int local36 = 0; local36 < HelveticaFont.VALID_CHARACTER_COUNT; local36++) {
			this.load(toolkit, local30, local34, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ÄËÏÖÜäëïöüÿßÁÀÉÈÍÌÓÒÚÙáàéèíìóòúùÂÊÎÔÛâêîôûÆæãÃõÕçÇ".charAt(local36), local36, false);
		}
		if (this.loadPlain) {
			this.loadPlain = false;
			local30 = new Font("Helvetica", 0, size);
			local34 = component.getFontMetrics(local30);
			for (@Pc(72) int local72 = 0; local72 < HelveticaFont.VALID_CHARACTER_COUNT; local72++) {
				this.load(toolkit, local30, local34, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ÄËÏÖÜäëïöüÿßÁÀÉÈÍÌÓÒÚÙáàéèíìóòúùÂÊÎÔÛâêîôûÆæãÃõÕçÇ".charAt(local72), local72, false);
			}
			if (!this.loadPlain) {
				this.loadPlain = false;
				for (@Pc(95) int local95 = 0; local95 < HelveticaFont.VALID_CHARACTER_COUNT; local95++) {
					this.load(toolkit, local30, local34, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ÄËÏÖÜäëïöüÿßÁÀÉÈÍÌÓÒÚÙáàéèíìóòúùÂÊÎÔÛâêîôûÆæãÃõÕçÇ".charAt(local95), local95, true);
				}
			}
		}
	}

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "(Lclient!ya;Ljava/lang/String;[IIIIZ)V")
	private void render(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) String string, @OriginalArg(2) int[] clipping, @OriginalArg(3) int x, @OriginalArg(4) int y, @OriginalArg(5) int colour, @OriginalArg(6) boolean shadow) {
		if (colour == 0) {
			shadow = false;
		}
		@Pc(7) int local7 = colour | 0xFF000000;
		for (@Pc(9) int local9 = 0; local9 < string.length(); local9++) {
			@Pc(16) int local16 = VALID_CHARACTERS[string.charAt(local9)];
			if (shadow) {
				this.nativeSprites[local16].render(x + 1, y + 1, 0, -16777216, 1);
			}
			this.nativeSprites[local16].render(x, y, 0, local7, 1);
			x += this.characterWidths[local16];
		}
	}

	@OriginalMember(owner = "client!sb", name = "b", descriptor = "()I")
	public int getLineHeight() {
		return this.startY - 1;
	}

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "(Lclient!ya;Ljava/lang/String;IIIZ)V")
	public void renderCenter(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) String string, @OriginalArg(2) int x, @OriginalArg(3) int y, @OriginalArg(4) int colour) {
		@Pc(5) int local5 = this.getTextWidth(string) / 2;
		toolkit.da(this.clipping);
		if (x - local5 <= this.clipping[2] && (x + local5 >= this.clipping[0] && (y - this.sizeY <= this.clipping[3] && y + this.startY >= this.clipping[1]))) {
			this.render(toolkit, string, this.clipping, x - local5, y, colour, true);
		}
	}

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "(Ljava/lang/String;)I")
	public int getTextWidth(@OriginalArg(0) String text) {
		@Pc(1) int local1 = 0;
		for (@Pc(3) int local3 = 0; local3 < text.length(); local3++) {
			@Pc(10) int local10 = VALID_CHARACTERS[text.charAt(local3)];
			local1 += this.characterWidths[local10];
		}
		return local1;
	}

	@OriginalMember(owner = "client!sb", name = "c", descriptor = "()I")
	public int getHeight() {
		return this.sizeY;
	}

	@OriginalMember(owner = "client!sb", name = "a", descriptor = "(Lclient!ya;Ljava/awt/Font;Ljava/awt/FontMetrics;CIZ)V")
	private void load(@OriginalArg(0) com.jagex.game.runetek5.graphics.Toolkit toolkit, @OriginalArg(1) Font font, @OriginalArg(2) FontMetrics metrics, @OriginalArg(3) char c, @OriginalArg(4) int id, @OriginalArg(5) boolean bold) {
		@Pc(3) int local3 = metrics.charWidth(c);
		@Pc(5) int local5 = local3;
		if (bold) {
			try {
				if (c == '/') {
					bold = false;
				}
				if (c == 'f' || c == 't' || c == 'w' || c == 'v' || c == 'k' || c == 'x' || c == 'y' || c == 'A' || c == 'V' || c == 'W') {
					local3++;
				}
			} catch (@Pc(45) Exception local45) {
			}
		}
		@Pc(49) int local49 = metrics.getMaxAscent();
		@Pc(55) int local55 = metrics.getMaxAscent() + metrics.getMaxDescent();
		@Pc(58) int local58 = metrics.getHeight();
		@Pc(63) Image local63 = Class164.aCanvas5.createImage(local3, local55);
		@Pc(66) Graphics local66 = local63.getGraphics();
		local66.setColor(Color.black);
		local66.fillRect(0, 0, local3, local55);
		local66.setColor(Color.white);
		local66.setFont(font);
		local66.drawString(String.valueOf(c), 0, local49);
		if (bold) {
			local66.drawString(String.valueOf(c), 1, local49);
		}
		@Pc(100) int[] local100 = new int[local3 * local55];
		@Pc(112) PixelGrabber local112 = new PixelGrabber(local63, 0, 0, local3, local55, local100, 0, local3);
		try {
			local112.grabPixels();
		} catch (@Pc(117) Exception local117) {
		}
		local63.flush();
		@Pc(123) int local123 = 0;
		@Pc(128) int local128;
		label66: for (@Pc(125) int local125 = 0; local125 < local55; local125++) {
			for (local128 = 0; local128 < local3; local128++) {
				@Pc(137) int local137 = local100[local128 + local125 * local3];
				if ((local137 & 0xFFFFFF) != 0) {
					local123 = local125;
					break label66;
				}
			}
		}
		for (local128 = 0; local128 < local100.length; local128++) {
			if ((local100[local128] & 0xFFFFFF) == 0) {
				local100[local128] = 0;
			}
		}
		this.sizeY = local49 - local123;
		this.startY = local58;
		this.characterWidths[id] = local5;
		this.nativeSprites[id] = toolkit.method4265(local100, local3, local3, local55);
	}

@OriginalMember(owner = "client!sb", name = "d", descriptor = "I")
	public static final int VALID_CHARACTER_COUNT = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ÄËÏÖÜäëïöüÿßÁÀÉÈÍÌÓÒÚÙáàéèíìóòúùÂÊÎÔÛâêîôûÆæãÃõÕçÇ".length();
}
