package com.jagex.game.runetek5.client.hd.shadow;

/**
 * Whether a draw the world pass is making may cast into the sun's map.
 *
 * <p>Ported from 634HD's {@code 50261e493e}: a draw that is solid to a depth
 * pass but not to the eye casts a shadow of its whole outline, which is why an
 * impling's fake-shadow quad and a fishing spot's ripple drew squares on the
 * ground. Two of that commit's tests are things this client can decide from
 * state it already caches:
 *
 * <ul>
 * <li>a draw HD water owns - the ripple is an opaque texture a water-effect
 * material draws as part of the surface ({@code GlToolkit.hdWaterActive()});</li>
 * <li>a draw blended additively ({@code GL_ONE, GL_ONE}) or modulating
 * ({@code GL_DST_COLOR, GL_ONE}) - those only add light, so nothing of them is
 * an occluder. The blend mode is the toolkit's own cache ({@code anInt2316}),
 * where mode 1 is {@code SRC_ALPHA, ONE_MINUS_SRC_ALPHA}: an ordinary
 * translucent draw keeps its silhouette, as upstream's rule does.</li>
 * </ul>
 *
 * <p>What this cannot decide, and does not pretend to: a face that is
 * half-opaque by its own colour or texture. 634HD discards those per fragment
 * in {@code shadow_dynamic.frag}; this pass is fixed-function and carries no
 * colour or texcoord into the depth draw, so it has no such test.
 *
 * <p>Pure so the truth table is tested here rather than inferred from a frame.
 */
public final class ShadowCasterGate {

	/** The toolkit's blend cache: blending off. */
	public static final int BLEND_OFF = 0;

	/** The toolkit's blend cache: {@code SRC_ALPHA, ONE_MINUS_SRC_ALPHA}. */
	public static final int BLEND_ALPHA = 1;

	/** The toolkit's blend cache: {@code ONE, ONE}, additive. */
	public static final int BLEND_ADDITIVE = 2;

	/** The toolkit's blend cache: {@code DST_COLOR, ONE}, modulating. */
	public static final int BLEND_MODULATING = 3;

	/** Why a draw was refused; {@link #NONE} means it casts. */
	public enum Refusal {

		/** The draw casts: nothing about it says it is not an occluder. */
		NONE,

		/** HD water owns the draw: a composed surface, not an occluder. */
		WATER,

		/** Blended additively or modulating: it only adds or scales light. */
		BLEND
	}

	private ShadowCasterGate() {
	}

	/**
	 * True when this draw casts: every draw except a water draw and an additive
	 * or modulating effect.
	 *
	 * @param waterDraw whether HD water owns the draw
	 * @param blendMode the toolkit's blend cache ({@code anInt2316})
	 */
	public static boolean casts(boolean waterDraw, int blendMode) {
		return refusal(waterDraw, blendMode) == Refusal.NONE;
	}

	/**
	 * The rule as a reason rather than a boolean, so a frame can say which of
	 * upstream's two tests refused a caster: water and an effect are different
	 * bugs when a shadow is missing, and a single counter cannot tell them
	 * apart.
	 *
	 * @param waterDraw whether HD water owns the draw
	 * @param blendMode the toolkit's blend cache ({@code anInt2316})
	 */
	public static Refusal refusal(boolean waterDraw, int blendMode) {
		if (waterDraw) {
			return Refusal.WATER;
		}
		if (blendMode == BLEND_ADDITIVE || blendMode == BLEND_MODULATING) {
			return Refusal.BLEND;
		}
		return Refusal.NONE;
	}
}
