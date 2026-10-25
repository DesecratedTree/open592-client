package com.jagex.game.runetek5.client.hd.texture;

import com.jagex.game.runetek5.client.hd.HdFeature;
import com.jagex.game.runetek5.client.hd.HdSettings;

import jaggl.OpenGL;

/**
 * The Mipmaps and Anisotropic filtering rows, applied where a world texture is
 * created.
 *
 * <p>Both rows are properties of the texture object, not of a draw, so they are
 * set once per texture in the material cache ({@code OpenGLTextureCache}) and
 * never per frame. That also keeps them off sprite, interface and render-target
 * textures: those are created elsewhere, and mipmapping an interface atlas or a
 * framebuffer attachment would be wrong or wasteful. The rows are read live, so
 * a texture created after the player toggles them gets the new state and an
 * existing one keeps what it was built with, which is the same rule the stock
 * per-material flags follow.
 *
 * <p>Anisotropy is only meaningful on a mipmapped texture and only when the
 * driver advertises {@code GL_EXT_texture_filter_anisotropic}; without either,
 * the row is inert rather than a GL error, and the frame is the stock one.
 * Values above the driver's maximum are clamped by GL itself.
 *
 * <p><b>Off restores stock, including after being on.</b> Both rows are
 * per-texture GL state, so turning one off has to put the parameter back
 * rather than merely stop writing it: {@link #restoreAnisotropy(int)} returns
 * the sampler to GL's default 1x, and {@code Class6.hdApplyTextureQuality}
 * drops the chain it generated and reinstates the texture's own stock filter.
 * The work happens the next time the material cache hands the texture out,
 * which is the only moment its parameters are touched at all.
 */
public final class HdTextureQuality {

	/** {@code GL_TEXTURE_2D}: the only target the material cache creates. */
	public static final int TEXTURE_2D = 3553;

	/** {@code GL_TEXTURE_MAX_ANISOTROPY_EXT}. */
	public static final int TEXTURE_MAX_ANISOTROPY = 0x84FE;

	/** {@code GL_LINEAR_MIPMAP_LINEAR}, the min filter a mipmapped texture wants. */
	public static final int LINEAR_MIPMAP_LINEAR = 9987;

	/** {@code GL_TEXTURE_MIN_FILTER}. */
	private static final int TEXTURE_MIN_FILTER = 10241;

	/** The extension that owns {@link #TEXTURE_MAX_ANISOTROPY}. */
	private static final String ANISOTROPY_EXTENSION = "GL_EXT_texture_filter_anisotropic";

	/** Largest sample count a row value can ask for: 2x, 4x, 8x, 16x. */
	public static final int MAX_SAMPLES = 16;

	/**
	 * A cheap summary of what the rows ask a texture for, so a texture can tell
	 * whether it was already configured: 0 when both are off, otherwise the
	 * mipmap bit and the sample count.
	 */
	public static int signature() {
		boolean mipmaps = mipmapsRequested();
		boolean anisotropy = anisotropyRequested();
		if (!mipmaps && !anisotropy) {
			return 0;
		}
		int samples = anisotropy ? supportedSamples() : 1;
		return (mipmaps ? 1 : 0) | samples << 1;
	}

	private static int supportedSamples = -1;

	private HdTextureQuality() {
	}

	/** True when the Mipmaps row is on and 592HD is enabled. */
	public static boolean mipmapsRequested() {
		return mipmapsRequested(HdSettings.isEnabled(), HdSettings.value(HdFeature.MIPMAPS));
	}

	/** The one mipmap predicate; a row value of 0 is Off. */
	public static boolean mipmapsRequested(boolean hdEnabled, int rowValue) {
		return hdEnabled && rowValue != 0;
	}

	/** True when the texture is something mipmaps can be generated for. */
	public static boolean canMipmap(boolean hdEnabled, int rowValue, int target, int width, int height) {
		return mipmapsRequested(hdEnabled, rowValue) && target == TEXTURE_2D && width > 1 && height > 1;
	}

	/** True when the Anisotropic row is on and 592HD is enabled. */
	public static boolean anisotropyRequested() {
		return anisotropyRequested(HdSettings.isEnabled(), HdSettings.value(HdFeature.ANISOTROPIC));
	}

	/** The one anisotropy predicate. */
	public static boolean anisotropyRequested(boolean hdEnabled, int rowValue) {
		return hdEnabled && rowValue != 0;
	}

	/**
	 * The sample count a row value asks for: index 1..4 is 2x, 4x, 8x, 16x and
	 * index 0 is Off (1x).
	 */
	public static int samplesFor(int rowValue) {
		if (rowValue <= 0) {
			return 1;
		}
		if (rowValue >= 5) {
			// Also stops a corrupt saved value from shifting a Java int by >= 32.
			return MAX_SAMPLES;
		}
		return 1 << rowValue;
	}

	/** The sample count this context will accept, or 1 when the extension is absent. */
	public static int supportedSamples() {
		if (supportedSamples < 0) {
			supportedSamples = detectAnisotropy() ? MAX_SAMPLES : 1;
		}
		return supportedSamples;
	}

	/** Test hook: forgets the cached extension probe. */
	static void resetForTest() {
		supportedSamples = -1;
	}

	private static boolean detectAnisotropy() {
		try {
			String extensions = OpenGL.glGetString(0x1F03);
			return extensions != null && extensions.indexOf(ANISOTROPY_EXTENSION) >= 0;
		} catch (RuntimeException ex) {
			return false;
		}
	}

	/**
	 * Generates the mip chain for the texture currently bound to the active
	 * unit and switches its min filter to read it. Called from {@code Class6},
	 * which owns the memory accounting and the filter state.
	 *
	 * @return true when a chain was generated
	 */
	public static boolean generateMipmaps(int target, int width, int height) {
		if (!mipmapsRequested() || target != TEXTURE_2D || width <= 1 || height <= 1) {
			return false;
		}
		OpenGL.glGenerateMipmapEXT(target);
		OpenGL.glTexParameteri(target, TEXTURE_MIN_FILTER, LINEAR_MIPMAP_LINEAR);
		return true;
	}    /**
     * Applies the Anisotropic row to the texture on the active unit.
     *
     * @return the sample count actually requested, or 1 when the row is off or
     *         the extension is missing
     */
    public static int applyAnisotropy(int target) {
		if (!anisotropyRequested()) {
			return 1;
		}
		int supported = supportedSamples();
		if (supported <= 1) {
			return 1;
		}
		int samples = Math.min(samplesFor(HdSettings.value(HdFeature.ANISOTROPIC)), supported);        // jaggl has no glTexParameterf wrapper; LWJGL's binding is the same call.
        org.lwjgl.opengl.GL11.glTexParameterf(target, TEXTURE_MAX_ANISOTROPY, samples);
        return samples;
    }

    /**
     * Puts a texture's anisotropy back to GL's default 1x, the value it had
     * before the row touched it.
     *
     * <p>Only issued when the driver advertises the extension: without it the
     * row never wrote the parameter, and this would be a GL error rather than a
     * restore. The sample count is a per-texture parameter, not a context
     * setting, so turning the row off has to visit each texture the row
     * configured - which the material cache does on its next hand-out, the same
     * moment {@link #applyAnisotropy(int)} runs.
     *
     * @return true when the parameter was written
     */
    public static boolean restoreAnisotropy(int target) {
        if (supportedSamples() <= 1) {
            return false;
        }
        org.lwjgl.opengl.GL11.glTexParameterf(target, TEXTURE_MAX_ANISOTROPY, 1.0F);
        return true;
    }
}
