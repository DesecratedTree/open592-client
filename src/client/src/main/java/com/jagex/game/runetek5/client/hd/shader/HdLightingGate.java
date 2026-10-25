package com.jagex.game.runetek5.client.hd.shader;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;
import com.jagex.game.runetek5.client.hd.shadow.ShadowBinding;

/**
 * The single owner of "may the per-pixel lighting shader draw this primitive?".
 *
 * <p>A pure function over {@link HdDrawRequest} and the program's availability,
 * so the precedence is stated once and tested as a truth table instead of being
 * spread across the renderer. {@link #lightingRequested} is the same question
 * without the draw state, and is the draw seam's allocation-free early-out.
 * In order of precedence:
 *
 * <ol>
 * <li>592HD must be enabled and the row on - off means the stock path is
 * untouched, with no GL calls at all;</li>
 * <li>the program must have linked - a driver that refuses the shader falls back
 * to fixed function on every draw;</li>
 * <li>the texture combine must be {@code GL_MODULATE} on the base unit - other
 * combines and multi-texturing stay on fixed function until each has its own
 * shader path;</li>
 * <li>the primitive needs vertex normals - without them there is nothing to
 * light per pixel;</li>
 * <li>no material effect may own the draw - water and the other effect programs
 * bind themselves inside {@code method1938}.</li>
 * </ol>
 *
 * <p>Fog is deliberately not on that list. The shader applies the band unless
 * the draw asks it not to ({@code uFogOff}), so a draw the stock path renders
 * fogless - the terrain pass, whose tiles are lit per pixel and carry their own
 * sun shadows - can bind and simply leaves the band out. Gating on fog instead
 * would have made the ground the one surface in the scene that gets neither.
 */
public final class HdLightingGate {

	/** {@code GL_MODULATE}: the only texture combine the shader reproduces. */
	private static final int GL_MODULATE = 7681;

	private HdLightingGate() {
	}

	/**
	 * True when the stored settings ask for per-pixel lighting. Read before a
	 * draw request exists, so a 592HD-off primitive costs two field reads and no
	 * allocation on the model path.
	 */
	public static boolean lightingRequested() {
		return lightingRequested(HdSettings.isEnabled(),
			HdSettings.value(HdFeature.PER_PIXEL_LIGHTING) != 0);
	}

	/** The one settings predicate; both entry points below delegate here. */
	public static boolean lightingRequested(boolean hdEnabled, boolean perPixelLighting) {
		return hdEnabled && perPixelLighting;
	}

	/** True when this draw's settings ask for per-pixel lighting at all. */
	public static boolean wantsLighting(HdDrawRequest request) {
		return request != null && lightingRequested(request.hdEnabled(), request.perPixelLighting());
	}

	public static boolean shouldBind(HdDrawRequest request, boolean programAvailable) {
		if (!wantsLighting(request) || !programAvailable) {
			return false;
		}
		return supportsTextureCombine(request.textured(), request.textureUnit(), request.textureCombine())
			&& request.hasNormals()
			&& !request.materialEffectActive();
	}

	/**
	 * The uniforms this draw needs. Point lights are zeroed when the row is off,
	 * so toggling the row is a uniform change rather than a different program.
	 *
	 * <p>Sun shadows do not gate lighting: the Sun shadows row gates the depth
	 * pass, and a draw with no map simply reports {@code shadowEnabled = false},
	 * which is what makes the row's Off state pixel-identical to the frame
	 * before the stage existed.
	 */
	public static HdDrawState drawState(HdDrawRequest request, int scenePointLights) {
		return drawState(request, scenePointLights, null);
	}

	/** {@link #drawState(HdDrawRequest, int)} with this frame's shadow binding, if any. */
	public static HdDrawState drawState(HdDrawRequest request, int scenePointLights, ShadowBinding shadow) {
		int pointLights = request.pointLights() ? HdDrawState.clampPointLights(scenePointLights) : 0;
		return new HdDrawState(request.textured(), pointLights, shadow != null,
			shadow == null ? 0 : shadow.radius());
	}

	/**
	 * The fragment stage multiplies the texel into the lit colour, which is
	 * {@code GL_MODULATE}. Some materials set {@code GL_REPLACE},
	 * {@code GL_ADD}, {@code GL_SUBTRACT} or {@code GL_INTERPOLATE} instead;
	 * drawing those with this shader would silently change their look. An
	 * untextured primitive has no combine to disagree with.
	 */
	public static boolean supportsTextureCombine(boolean textured, int textureUnit, int combineMode) {
		if (!textured) {
			return true;
		}
		return textureUnit == 0 && combineMode == GL_MODULATE;
	}
}
