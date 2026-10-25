package com.jagex.game.runetek5.client.hd.shadow;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

/**
 * Cut-out casters for the sun's depth pass, ported from 634HD's
 * {@code cutoutShadows}.
 *
 * <p>A fixed-function depth draw tests nothing but coverage, so a leaf, a fence
 * or a grille casts a solid block. Alpha testing the caster's own texture while
 * the pass runs makes texels under the threshold cast nothing, so the shadow
 * carries the shape the player sees. Casters with no alpha (most stone) test
 * against an opaque texel and are unchanged.
 * * <p>The row is opt-in and off in every preset below Ultra, as in 634HD. Off
 * means a solid depth pass: the pass switches the test off while it draws, so
 * every caster casts its full silhouette instead of inheriting a reference the
 * scene's last sprite draw left behind. Either way the pass saves the scene's
 * enable, function and reference and puts all three back when it ends.
 *
 * <p>The policy is a pure function over the settings so the truth table is
 * tested here rather than inferred from the renderer.
 */
public final class ShadowCutout {

	/** {@code GL_ALPHA_TEST}: fixed-function per-fragment alpha rejection. */
	public static final int GL_ALPHA_TEST = 3008;

	/** {@code GL_GREATER}: keep a fragment whose alpha is above the reference. */
	public static final int GL_GREATER = 516;

	/**
	 * Half-opaque, 634HD's texel rule: texels at or under half alpha cast
	 * nothing, so a mip-blended leaf edge still contributes its solid middle.
	 */
	public static final float THRESHOLD = 0.5F;

	private ShadowCutout() {
	}

	/** True when the stored settings ask for cut-out casters this frame. */
	public static boolean enabled() {
		return requested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0,
			HdSettings.value(HdFeature.SHADOW_MAP),
			HdSettings.value(HdFeature.CUTOUT_SHADOWS));
	}

	/**
	 * The one settings predicate: cut-out casters only exist inside a sun
	 * shadow pass, so the row is inert unless HD, per-pixel lighting and a
	 * non-zero map size are all on.
	 */
	public static boolean requested(boolean hdEnabled, boolean perPixelLighting, int shadowRow,
			int cutoutRow) {
		return hdEnabled && perPixelLighting && SunShadowGate.resolution(shadowRow) > 0 && cutoutRow != 0;
	}

	/**
	 * True when this caster has the per-group data a cut-out draw needs.
	 *
	 * <p>The model path releases the face-group table for meshes whose faces all
	 * lack an alpha group ({@code GlModel.method2807}); such a caster still has
	 * geometry, a face count and an uploaded buffer, so the depth pass must draw
	 * it - just as one position-only draw, never as a per-group loop over a null
	 * table. That release is what made a live client throw
	 * {@code NullPointerException} in {@code drawShadowDepth} the frame after one
	 * of its casters was trimmed.
	 *
	 * <p>Texcoords are required for the same reason the row exists: without them
	 * the alpha test would sample one texel for the whole mesh.
	 */
	public static boolean canDrawGroups(boolean cutoutActive, boolean hasTexCoords, int[] faceGroups,
			short[] faceMaterials) {
		return cutoutActive && hasTexCoords && faceGroups != null && faceGroups.length >= 2
			&& faceMaterials != null;
	}
}
