package com.jagex.game.runetek5.config.material;

import org.openrs2.deob.annotation.OriginalClass;
import org.openrs2.deob.annotation.OriginalMember;


import com.jagex.game.runetek5.deob.Class83;

import org.openrs2.deob.annotation.OriginalArg;
import com.jagex.game.runetek5.ModeWhere;
import com.jagex.game.runetek5.config.idktype.IDKTypeList;
import com.jagex.game.runetek5.deob.Class21;
import com.jagex.game.runetek5.preference.Preference;

import com.jagex.game.runetek5.config.material.*;
import com.jagex.game.runetek5.config.mec.MECType;
import com.jagex.game.runetek5.config.flu.FluTypeList;
import com.jagex.game.runetek5.GpuFaceNormal;
import com.jagex.game.runetek5.deob.client;
import com.jagex.game.runetek5.deob.Class249;
/**
 * One material as the 592 cache defines it: the parameters its texture is
 * generated from, not a lighting response.
 *
 * <p>Every field is a property the material index sets per material, and every
 * one of them is consumed by texture generation, animation or a texture effect:
 * the size ({@code isSmall}), the colours and the two scroll rates the texture
 * matrix is built from ({@code aByte87}, {@code aByte90}, {@code aByte91},
 * {@code aByte93}), the blend and alpha flags ({@code aBoolean639},
 * {@code aBoolean640}, {@code aBoolean642}, {@code aBoolean643},
 * {@code aBoolean644}, and {@code aByte92} for an alpha channel), the
 * texture-effect slot and its two parameters ({@code aByte89},
 * {@code aByte88}, {@code anInt6933}), the texture-environment parameter
 * ({@code anInt6934}), the colour ({@code colorHsl}) and whether the texture is
 * built as float data ({@code aBoolean638}).
 *
 * <p><b>There is no specular strength here and no exponent</b>, and no
 * roughness. 592's own "specular" is the stock pre-filtered environment cube
 * map ({@code FixedFunctionSpecularPass}), whose exponent levels are baked into
 * its ramp and selected by a texture effect - it is a reflection, not a
 * per-material light response. A renderer that wants a per-material highlight
 * (for instance {@code HdFeature.LIGHTING_MODEL}) therefore cannot read one out
 * of this record: it needs material maps of its own, which this client does not
 * ship.
 */
@OriginalClass("client!vf")
public final class MaterialType {

	@OriginalMember(owner = "client!vf", name = "a", descriptor = "Z")
	public boolean isSmall;

	@OriginalMember(owner = "client!vf", name = "b", descriptor = "I")
	public int anInt6933;

	@OriginalMember(owner = "client!vf", name = "c", descriptor = "Z")
	public boolean aBoolean638;

	@OriginalMember(owner = "client!vf", name = "d", descriptor = "B")
	public byte aByte87;

	@OriginalMember(owner = "client!vf", name = "e", descriptor = "Z")
	public boolean aBoolean639;

	@OriginalMember(owner = "client!vf", name = "f", descriptor = "Z")
	public boolean aBoolean640;

	@OriginalMember(owner = "client!vf", name = "g", descriptor = "I")
	public int anInt6934;

	@OriginalMember(owner = "client!vf", name = "h", descriptor = "B")
	public byte aByte88;

	@OriginalMember(owner = "client!vf", name = "i", descriptor = "B")
	public byte aByte89;

	@OriginalMember(owner = "client!vf", name = "j", descriptor = "B")
	public byte aByte90;

	@OriginalMember(owner = "client!vf", name = "k", descriptor = "B")
	public byte aByte91;

	@OriginalMember(owner = "client!vf", name = "m", descriptor = "Z")
	public boolean aBoolean641;

	@OriginalMember(owner = "client!vf", name = "n", descriptor = "Z")
	public boolean aBoolean642;

	@OriginalMember(owner = "client!vf", name = "o", descriptor = "B")
	public byte aByte92;

	@OriginalMember(owner = "client!vf", name = "p", descriptor = "B")
	public byte aByte93;

	@OriginalMember(owner = "client!vf", name = "r", descriptor = "Z")
	public boolean aBoolean643;

	@OriginalMember(owner = "client!vf", name = "s", descriptor = "Z")
	public boolean aBoolean644;

	@OriginalMember(owner = "client!vf", name = "t", descriptor = "S")
	public short colorHsl;

	@OriginalMember(owner = "client!vf", name = "v", descriptor = "Z")
	public boolean aBoolean645;

	static {
		new Class83("Nothing interesting happens.", "Nichts Interessantes passiert.", "Il ne se passe rien d'intéressant.", "Nada de interessante acontece.");
		new Class83("You do not have permission to kick this user.", "Du darfst diesen Benutzer nicht rauswerfen.", "Vous n'êtes pas autorisé à expulser cet utilisateur.", "Você não tem permissão para expulsar este usuário.");
		new Class83("Use", "Benutzen", "Utiliser", "Usar");
	}

@OriginalMember(owner = "client!vf", name = "x", descriptor = "Lclient!in;")
	public static IDKTypeList aClass109_2;
@OriginalMember(owner = "client!vf", name = "z", descriptor = "Lclient!jn;")
	public static Preference aClass80_1;
@OriginalMember(owner = "client!vf", name = "l", descriptor = "Lclient!bf;")
	public static final Class21 aClass21_16 = new Class21(8, 3);
@OriginalMember(owner = "client!vf", name = "q", descriptor = "Lclient!sp;")
	public static final ModeWhere aClass219_7 = new ModeWhere("WTWIP", 3);
@OriginalMember(owner = "client!vf", name = "y", descriptor = "Z")
	public static final boolean aBoolean646 = false;
@OriginalMember(owner = "client!vf", name = "A", descriptor = "Z")
	public static boolean aBoolean647 = false;
@OriginalMember(owner = "client!vf", name = "a", descriptor = "(IZZI)V")
	public static void method5385(@OriginalArg(1) boolean arg0) {
		FluTypeList.anInt5579 = 2;
		MECType.anInt4928 = 22050;
		GpuFaceNormal.aBoolean649 = arg0;
	}
@OriginalMember(owner = "client!vf", name = "a", descriptor = "(IZLclient!uu;)Ljava/lang/String;")
	public static String method5386(@OriginalArg(0) int arg0, @OriginalArg(2) com.jagex.game.runetek5.config.rsinterface.Component arg1) {
		if (!client.method900(arg1).method3905(arg0) && arg1.anObjectArray15 == null) {
			return null;
		} else if (arg1.options == null || arg1.options.length <= arg0 || arg1.options[arg0] == null || arg1.options[arg0].trim().length() == 0) {
			return Class249.aBoolean626 ? "Hidden-" + arg0 : null;
		} else {
			return arg1.options[arg0];
		}
	}
}
