package com.jagex.game.runetek5.client.hd.water;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.deob.Class6;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * 634HD's water textures: the ripple normal map, the shore foam mask and the
 * flow map it ships under {@code 634hd/materials/flow/}.
 *
 * <p>Upstream's water is not a flat colour with a tint: the surface's detail is
 * these maps, scrolled in three layers (see {@code hdWaterLayers} in
 * {@code hd_material.glsl}). This port shipped the same two files as content
 * but no water program sampled them, so the surface read as an untinted sheet -
 * the "water texture is missing" case. This class is the missing bind: it
 * decodes the PNGs once, resamples them the way the stock textures are
 * sampled, and hands the toolkit a texture it can bind.
 *
 * <p>The decode uses the same alpha-weighted box filter as the authored albedo
 * replacement ({@code HdTextureReplacements.decode}), not point sampling:
 * aliasing baked into level 0 is grain no filtering can remove afterwards.
 *
 * <p>A set that cannot be read is {@code null} rather than a flat stand-in, so
 * the water program keeps its wave-field surface and the toolkit reports that
 * the maps were unavailable instead of pretending to texture it.
 */
public final class HdWaterMaps {

	/** The maps' shipped side, 634HD's own level 0. */
	public static final int SIZE = 128;

	/** The content folder both files live under. */
	public static final String FOLDER = "flow";

	/** The ripple normal map's file name. */
	public static final String NORMAL_FILE = "water_normal_map_1.png";

	/** The shore foam mask's file name. */
	public static final String FOAM_FILE = "water_foam.png";

	/**
	 * The flow map's file name: the current's velocity, which drifts the ripple
	 * layers and the foam (634HD's third map, {@code hdWaterLayers}).
	 */
	public static final String FLOW_FILE = "water_flow_map.png";

	private final int[] normal;

	private final int[] foam;

	private final int[] flow;

	/** Uploaded lazily, on the first water draw, so no GL touches a headless load. */
	private OpenGl2DTexture normalTexture;

	private OpenGl2DTexture foamTexture;

	private OpenGl2DTexture flowTexture;

	/**
	 * What the two units held before this draw. A water program owns the frame
	 * while it is bound, but not the units' contents afterwards: the stock path
	 * leaves its own art there, and a later pass reads it back from the cache.
	 * Restoring them is what keeps this seam invisible to the frame's GL state.
	 */
	private Class6 savedNormal;

	private Class6 savedFoam;

	private Class6 savedFlow;

	/**
	 * True between the first bind and the unbind that closes the draw. A water
	 * region can bind more than once (one material per layer), and a second save
	 * would capture this seam's own textures and restore them as if they were
	 * the stock path's.
	 */
	private boolean attached;

	private HdWaterMaps(int[] normal, int[] foam, int[] flow) {
		this.normal = normal;
		this.foam = foam;
		this.flow = flow;
	}

	/** Loads the shipped maps through the current content set. */
	public static HdWaterMaps load() {
		return load(HdContent.get());
	}

	/** Loads the maps from a specific content set; null when they are unavailable. */
	public static HdWaterMaps load(HdContent content) {
		if (content == null) {
			return null;
		}
		int[] normal = decode(content.materialFile(FOLDER, NORMAL_FILE));
		int[] foam = decode(content.materialFile(FOLDER, FOAM_FILE));
		int[] flow = decode(content.materialFile(FOLDER, FLOW_FILE));
		return normal == null || foam == null || flow == null ? null
			: new HdWaterMaps(normal, foam, flow);
	}

	/** True when both maps decoded, so the shader may sample them. */
	public boolean available() {
		return this.normal != null && this.foam != null && this.flow != null;
	}

	/** The ripple map's texels; the tests' source of truth. */
	public int[] normal() {
		return this.normal;
	}

	/** The foam mask's texels. */
	public int[] foam() {
		return this.foam;
	}

	/** The flow map's texels. */
	public int[] flow() {
		return this.flow;
	}

	/**
	 * Binds both maps on their own units, building the textures on first use.
	 * The caller has already selected a water program; the active unit is
	 * returned to 0 so the draw's own material sampler is untouched.
	 */
	public boolean bind(GlToolkit toolkit) {
		if (toolkit == null || !available()) {
			return false;
		}
		if (!this.attached) {
			// Saved before the uploads: hdCreateTexture builds on a material unit and
			// leaves it naming the new texture, so a later save would capture this
			// seam's own art and restore it as if the stock path had put it there.
			this.savedNormal = toolkit.hdBoundTexture(HdWaterSource.WATER_NORMAL_UNIT);
			this.savedFoam = toolkit.hdBoundTexture(HdWaterSource.WATER_FOAM_UNIT);
			this.savedFlow = toolkit.hdBoundTexture(HdWaterSource.WATER_FLOW_UNIT);
			// A unit that already names this seam's own art is not stock state:
			// restoring it would adopt the maps as what to hand back, and the
			// seam would never release them.
			if (this.savedNormal == this.normalTexture) {
				this.savedNormal = null;
			}
			if (this.savedFoam == this.foamTexture) {
				this.savedFoam = null;
			}
			if (this.savedFlow == this.flowTexture) {
				this.savedFlow = null;
			}
			this.attached = true;
		}
		if (this.normalTexture == null) {
			// Built on this seam's own unit: the quality call binds what it
			// is handed, and unit 0 is the water surface being drawn.
			toolkit.hdActiveTextureUnit(HdWaterSource.WATER_NORMAL_UNIT);
			this.normalTexture = toolkit.hdCreateTexture(SIZE, SIZE, this.normal);
			this.foamTexture = toolkit.hdCreateTexture(SIZE, SIZE, this.foam);
			this.flowTexture = toolkit.hdCreateTexture(SIZE, SIZE, this.flow);
			// Three scrolled layers sample the normal map under minification;
			// without the mip chain that detail aliases into shimmer grain.
			this.normalTexture.hdApplyTextureQuality(SIZE, SIZE);
			this.foamTexture.hdApplyTextureQuality(SIZE, SIZE);
			this.flowTexture.hdApplyTextureQuality(SIZE, SIZE);
		}
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_NORMAL_UNIT);
		toolkit.hdSetActiveTexture(this.normalTexture);
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_FOAM_UNIT);
		toolkit.hdSetActiveTexture(this.foamTexture);
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_FLOW_UNIT);
		toolkit.hdSetActiveTexture(this.flowTexture);
		toolkit.hdActiveTextureUnit(0);
		return true;
	}

	/**
	 * Puts both units back the way this draw found them, so a later pass sees the
	 * stock path's own art rather than this seam's, and the frame's GL state at
	 * every boundary is the one the stock frame would have left.
	 */
	public void unbind(GlToolkit toolkit) {
		if (toolkit == null) {
			return;
		}
		if (!this.attached) {
			return;
		}
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_NORMAL_UNIT);
		toolkit.hdSetActiveTexture(this.savedNormal);
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_FOAM_UNIT);
		toolkit.hdSetActiveTexture(this.savedFoam);
		toolkit.hdActiveTextureUnit(HdWaterSource.WATER_FLOW_UNIT);
		toolkit.hdSetActiveTexture(this.savedFlow);
		toolkit.hdActiveTextureUnit(0);
		this.savedNormal = null;
		this.savedFoam = null;
		this.savedFlow = null;
		this.attached = false;
	}

	/** Decodes a PNG to {@link #SIZE} square ARGB texels, or null. */
	static int[] decode(byte[] png) {
		if (png == null || png.length == 0) {
			return null;
		}
		BufferedImage image;
		try {
			image = ImageIO.read(new ByteArrayInputStream(png));
		} catch (Exception ex) {
			return null;
		}
		if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
			return null;
		}
		return resample(image, SIZE);
	}

	/**
	 * A square, alpha-weighted box average of the image: the same filter the
	 * authored albedo takes, so a larger PNG lands at {@link #SIZE} without
	 * point sampling's grain. The two shipped maps are already that size, so
	 * for them this is the identity; a replacement is what the filter protects.
	 */
	static int[] resample(BufferedImage image, int size) {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] source = image.getRGB(0, 0, width, height, null, 0, width);
		boolean alpha = image.getColorModel().hasAlpha();
		int[] out = new int[size * size];
		for (int y = 0; y < size; y++) {
			int top = y * height / size;
			int bottom = Math.max(top + 1, (y + 1) * height / size);
			for (int x = 0; x < size; x++) {
				int left = x * width / size;
				int right = Math.max(left + 1, (x + 1) * width / size);
				long red = 0L;
				long green = 0L;
				long blue = 0L;
				long weight = 0L;
				int count = 0;
				for (int sourceY = top; sourceY < bottom; sourceY++) {
					for (int sourceX = left; sourceX < right; sourceX++) {
						int texel = source[sourceY * width + sourceX];
						int texelAlpha = alpha ? texel >>> 24 : 0xFF;
						red += (long) ((texel >> 16) & 0xFF) * texelAlpha;
						green += (long) ((texel >> 8) & 0xFF) * texelAlpha;
						blue += (long) (texel & 0xFF) * texelAlpha;
						weight += texelAlpha;
						count++;
					}
				}
				int outAlpha = alpha ? (int) ((weight + count / 2) / count) : 0xFF;
				if (weight == 0L) {
					out[y * size + x] = 0;
					continue;
				}
				out[y * size + x] = outAlpha << 24
					| (int) ((red + weight / 2) / weight) << 16
					| (int) ((green + weight / 2) / weight) << 8
					| (int) ((blue + weight / 2) / weight);
			}
		}
		return out;
	}
}
