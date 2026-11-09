package com.jagex.game.runetek5.client.hd.material;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;
import com.jagex.game.runetek5.client.hd.shader.HdShaderSource;
import com.jagex.game.runetek5.deob.OpenGl2DTexture;
import com.jagex.game.runetek5.graphics.gl.GlToolkit;

/**
 * A material's normal, ORM, emissive and height maps, resolved from the shipped
 * content and uploaded once per material.
 *
 * <p>The maps are bound per draw because a model batch is already one material
 * ({@code GlModel} submits one texture id at a time and the terrain layer
 * carries its own material), so no atlas or material-id trampoline is needed:
 * the seam knows the material id, this class resolves it, and the shader
 * samples plain 2D textures. That is also why the samplers are per material
 * units rather than an array: GLSL 1.20 has no texture arrays.
 *
 * <p><b>Where the pixels come from.</b> 634HD ships each map twice: a PNG and
 * the precompressed blocks it actually uploads (BC5 for the normal's two
 * channels, BC7 mode 6 for the packed and emissive maps, with the height in the
 * packed map's alpha). Upstream samples the blocks where the driver can and
 * decodes them on the CPU where it cannot; jaggl can only sample RGBA, so this
 * client is always in the second case and {@link HdMapBlocks} is that decoder.
 * The PNG is the fallback for a set whose blocks are missing or in a mode this
 * decoder does not read, so a material never silently loses its maps while it
 * still has decodable art.
 *
 * <p><b>Height.</b> 634HD's height lives in the packed map's alpha (BC7) and,
 * for a PNG set, in the normal PNG's alpha; upstream feeds the parallax march
 * from there. The height texture this class binds is that alpha, so the
 * parallax depth a material states has something to read.
 *
 * <p>A map that cannot be resolved is recorded with its exact reason
 * ({@link #unresolved()}, {@link #coverage(HdContent)}) instead of being
 * silently replaced by a flat surface.
 */
public final class HdMaterialTextures {

	/** Map dimensions: 634HD's own size, which is the shipped blocks' level 0. */
	public static final int SIZE = 256;

	/** A parallax a test asks for instead of the material's own; see {@link #overrideParallaxForTest}. */
	private static final Map<Integer, Float> PARALLAX_FOR_TEST = new HashMap<Integer, Float>();

	private final GlToolkit toolkit;

	private final Map<Integer, Slot> slots = new HashMap<Integer, Slot>();

	private final Map<Integer, String> unresolved = new LinkedHashMap<Integer, String>();

	public HdMaterialTextures(GlToolkit toolkit) {
		this.toolkit = toolkit;
	}

	/**
	 * Binds {@code material}'s maps and returns the surface the shader should
	 * use. Units are left empty for maps that are absent.
	 */
	public HdMaterialSurface bind(int material) {
		if (this.toolkit == null || material < 0) {
			return HdMaterialSurface.OFF;
		}
		HdContent content = HdContent.get();
		HdMaterialDefinition definition = content.material(material);
		if (definition == null) {
			return HdMaterialSurface.OFF;
		}
		Slot slot = this.slots.get(Integer.valueOf(material));
		if (slot == null) {
			slot = load(material, definition);
			this.slots.put(Integer.valueOf(material), slot);
		}
		bind(HdShaderSource.SAMPLER_UNIT_MATERIAL_NORMAL, slot.normal);
		bind(HdShaderSource.SAMPLER_UNIT_MATERIAL_ORM, slot.orm);
		bind(HdShaderSource.SAMPLER_UNIT_MATERIAL_EMISSIVE, slot.emissive);
		bind(HdShaderSource.SAMPLER_UNIT_MATERIAL_HEIGHT, slot.height);
		this.toolkit.hdActiveTextureUnit(0);
		return HdMaterialSurface.of(definition, slot.normal != null, slot.orm != null, slot.emissive != null,
			slot.height != null, slot.height == null ? 0.0F : parallaxOf(definition));
	}

	/** The materials this instance could not resolve, and why. */
	public Map<Integer, String> unresolved() {
		return this.unresolved;
	}

	/** Loads one material's maps; a missing or unreadable file is recorded, not thrown. */
	private Slot load(int material, HdMaterialDefinition definition) {
		Resolution resolution = resolve(HdContent.get(), definition);
		if (resolution.reason != null) {
			this.unresolved.put(Integer.valueOf(material), resolution.reason);
		}
		return new Slot(texture(resolution.normal), texture(resolution.orm), texture(resolution.emissive),
			texture(heightPixels(resolution)));
	}

	/** The height texture's pixels: the resolved alpha written to every channel. */
	private static int[] heightPixels(Resolution resolution) {
		return resolution.height;
	}

	private OpenGl2DTexture texture(int[] pixels) {
		if (pixels == null) {
			return null;
		}
		// Built through the toolkit's upload seam, not directly: the constructor
		// binds on the selected unit, which is unit 0 while this draw's own
		// surface texture is bound there. A map created that way would replace
		// the art being drawn (no colour, no environment, the packed map's
		// height as alpha) - the seam builds on a material unit and puts unit 0
		// back exactly as it found it.
		// Built on the map's own unit: the quality call below binds the
		// texture it is handed, and unit 0 is the surface being drawn.
		this.toolkit.hdActiveTextureUnit(HdShaderSource.SAMPLER_UNIT_MATERIAL_NORMAL);
		OpenGl2DTexture texture = this.toolkit.hdCreateTexture(SIZE, SIZE, pixels);
		// The maps are sampled under minification on every surface, so they
		// take the Mipmaps and Anisotropic rows here, exactly like the world
		// texture cache's own textures. Without the chain a 256-square
		// normal map aliases into the grain the map is blamed for.
		texture.hdApplyTextureQuality(SIZE, SIZE);
		return texture;
	}

	private void bind(int unit, OpenGl2DTexture texture) {
		this.toolkit.hdActiveTextureUnit(unit);
		this.toolkit.hdSetActiveTexture(texture);
	}

	// -- resolution --------------------------------------------------------

	/**
	 * What a material's maps resolve to on this machine, without GL: the pixels,
	 * where each came from, and the reason for anything that could not be read.
	 */
	public static final class Resolution {

		static final Resolution NONE = new Resolution(null, null, null, null, null, null, null, null,
			"no material");

		public final int[] normal;

		public final int[] orm;

		public final int[] emissive;

		/** Alpha written to every channel; null when no height could be read. */
		public final int[] height;

		public final String normalSource;

		public final String ormSource;

		public final String emissiveSource;

		public final String heightSource;

		/** Null when every named map resolved; else the exact failure. */
		public final String reason;

		Resolution(int[] normal, int[] orm, int[] emissive, int[] height, String normalSource, String ormSource,
				String emissiveSource, String heightSource, String reason) {
			this.normal = normal;
			this.orm = orm;
			this.emissive = emissive;
			this.height = height;
			this.normalSource = normalSource;
			this.ormSource = ormSource;
			this.emissiveSource = emissiveSource;
			this.heightSource = heightSource;
			this.reason = reason;
		}

		/** True when this material has art the shader can sample. */
		public boolean hasAny() {
			return this.normal != null || this.orm != null || this.emissive != null || this.height != null;
		}
	}

	/** Resolves one definition's maps from the shipped content. */
	public static Resolution resolve(HdContent content, HdMaterialDefinition definition) {
		if (content == null || definition == null) {
			return Resolution.NONE;
		}
		List<String> failures = new ArrayList<String>();
		Decoded normal = map(content, definition.texture(), definition.normal(), ".bc5", failures);
		Decoded orm = map(content, definition.texture(), definition.orm(), ".bc7", failures);
		Decoded emissive = map(content, definition.texture(), definition.emissive(), ".bc7", failures);
		int[] height = null;
		String heightSource = null;
		if (definition.height() != null) {
			// The packed map's alpha is the height where the set ships BC7; a PNG set
			// keeps it in the map the definition names (the normal PNG's alpha).
			if (orm != null && orm.pixels != null && hasAlpha(orm.pixels)) {
				height = alphaOnly(orm.pixels);
				heightSource = orm.source;
			} else {
				byte[] png = content.materialFile(definition.texture(), definition.height());
				int[] decoded = decode(png, SIZE);
				if (decoded != null && hasAlpha(decoded)) {
					height = alphaOnly(decoded);
					heightSource = definition.height();
				}
			}
		}
		String reason = failures.isEmpty() ? null : failures.get(0);
		return new Resolution(normal == null ? null : normal.pixels, orm == null ? null : orm.pixels,
			emissive == null ? null : emissive.pixels, height, normal == null ? null : normal.source,
			orm == null ? null : orm.source, emissive == null ? null : emissive.source, heightSource, reason);
	}

	/** One map's pixels and origin, or null when the definition names none. */
	private static final class Decoded {

		final int[] pixels;

		final String source;

		Decoded(int[] pixels, String source) {
			this.pixels = pixels;
			this.source = source;
		}
	}

	/**
	 * One map: the precompressed blocks first (upstream's only form), then the
	 * PNG of the same name. A named map that neither path decodes goes into
	 * {@code failures} with both attempts.
	 */
	private static Decoded map(HdContent content, int texture, String name, String blockExtension,
			List<String> failures) {
		if (name == null) {
			return null;
		}
		String blocksName = blocksName(name, blockExtension);
		byte[] blocks = content.materialFile(texture, blocksName);
		String blockFailure = null;
		if (blocks != null) {
			int side = HdMapBlocks.sideOf(blocks);
			int[] pixels = side <= 0 ? null
				: ".bc5".equals(blockExtension) ? HdMapBlocks.decodeBc5(blocks, side)
					: HdMapBlocks.decodeBc7(blocks, side);
			if (pixels != null) {
				return new Decoded(resample(pixels, side, SIZE), blocksName);
			}
			blockFailure = "not a " + (".bc5".equals(blockExtension) ? "BC5" : "BC7 mode 6") + " image";
		}
		byte[] png = content.materialFile(texture, name);
		if (png != null) {
			int[] pixels = decode(png, SIZE);
			if (pixels != null) {
				return new Decoded(pixels, name);
			}
		}
		if (blocks == null && png == null) {
			// Nothing is shipped for this material. That is content, not a failure:
			// upstream leaves the map null in exactly this case too, so there is
			// no set here to bind and nothing to report.
			return null;
		}
		StringBuilder reason = new StringBuilder("texture ").append(texture).append(": ").append(name);
		if (blocks == null) {
			reason.append(" does not decode and ").append(blocksName).append(" is missing");
		} else {
			reason.append(": ").append(blocksName).append(' ').append(blockFailure).append(
				", and ").append(name).append(png == null ? " is missing" : " does not decode");
		}
		failures.add(reason.toString());
		return null;
	}

	/** A square box average, for blocks whose level 0 is not the map size. */
	static int[] resample(int[] pixels, int from, int to) {
		if (from == to || from <= 0 || to <= 0) {
			return pixels;
		}
		int[] out = new int[to * to];
		for (int y = 0; y < to; y++) {
			int y0 = y * from / to;
			int y1 = Math.max(y0 + 1, (y + 1) * from / to);
			for (int x = 0; x < to; x++) {
				int x0 = x * from / to;
				int x1 = Math.max(x0 + 1, (x + 1) * from / to);
				int alpha = 0;
				int red = 0;
				int green = 0;
				int blue = 0;
				int count = 0;
				for (int sy = y0; sy < y1; sy++) {
					for (int sx = x0; sx < x1; sx++) {
						int texel = pixels[sy * from + sx];
						alpha += texel >>> 24;
						red += texel >> 16 & 0xFF;
						green += texel >> 8 & 0xFF;
						blue += texel & 0xFF;
						count++;
					}
				}
				out[y * to + x] = alpha / count << 24 | red / count << 16 | green / count << 8 | blue / count;
			}
		}
		return out;
	}

	/** {@code normal.png} with {@code .bc5} becomes {@code normal.bc5}. */
	private static String blocksName(String name, String extension) {
		int dot = name.lastIndexOf('.');
		return (dot <= 0 ? name : name.substring(0, dot)) + extension;
	}

	/** True when any texel's alpha is below opaque, i.e. the map carries a height. */
	private static boolean hasAlpha(int[] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			if ((pixels[i] >>> 24) != 0xFF) {
				return true;
			}
		}
		return false;
	}

	/** The alpha channel written to every channel, so the height map reads plainly. */
	private static int[] alphaOnly(int[] pixels) {
		int[] out = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			int alpha = pixels[i] >>> 24;
			out[i] = alpha << 24 | alpha << 16 | alpha << 8 | alpha;
		}
		return out;
	}

	// -- coverage ----------------------------------------------------------

	/** How many of the file's materials bind decoded maps, and where each came from. */
	public static final class Coverage {

		public int materials;

		/** Map sets that bind at least one decoded map. */
		public int resolved;

		/** Map sets the file names but the build ships no files for. */
		public int notShipped;

		/** Map sets whose files exist and could not be decoded: the only real failures. */
		public int unresolved;

		int blockNormal;

		int pngNormal;

		int blockOrm;

		int pngOrm;

		int blockEmissive;

		int pngEmissive;

		int heightFromPacked;

		int heightFromPng;

		public final List<String> reasons = new ArrayList<String>();

		/** One line for the run's log: the coverage and every source's count. */
		public String summary() {
			return "[hd-maps] " + this.materials + " declared map sets: " + this.resolved
				+ " bind decoded maps, " + this.notShipped + " name maps this build does not ship, "
				+ this.unresolved + " unresolved; normal blocks " + this.blockNormal + " / png " + this.pngNormal
				+ ", orm blocks " + this.blockOrm + " / png " + this.pngOrm + ", emissive blocks "
				+ this.blockEmissive + " / png " + this.pngEmissive + ", height packed " + this.heightFromPacked
				+ " / png " + this.heightFromPng + "; " + SIZE + " square";
		}
	}

	/** Resolves every material in {@code content}; the coverage proof's source of truth. */
	public static Coverage coverage(HdContent content) {
		Coverage coverage = new Coverage();
		for (int i = 0; i < content.materials().size(); i++) {
			HdMaterialDefinition definition = content.materials().get(i);
			if (definition.normal() == null && definition.orm() == null && definition.emissive() == null
				&& definition.height() == null) {
				continue;
			}
			coverage.materials++;
			Resolution resolution = resolve(content, definition);
			if (resolution.reason != null) {
				coverage.unresolved++;
				coverage.reasons.add(resolution.reason);
			}
			if (resolution.hasAny()) {
				coverage.resolved++;
			} else {
				coverage.notShipped++;
			}
			if (resolution.normalSource != null) {
				if (resolution.normalSource.endsWith(".bc5")) {
					coverage.blockNormal++;
				} else {
					coverage.pngNormal++;
				}
			}
			if (resolution.ormSource != null) {
				if (resolution.ormSource.endsWith(".bc7")) {
					coverage.blockOrm++;
				} else {
					coverage.pngOrm++;
				}
			}
			if (resolution.emissiveSource != null) {
				if (resolution.emissiveSource.endsWith(".bc7")) {
					coverage.blockEmissive++;
				} else {
					coverage.pngEmissive++;
				}
			}
			if (resolution.height != null) {
				if (resolution.heightSource != null && resolution.heightSource.endsWith(".bc7")) {
					coverage.heightFromPacked++;
				} else {
					coverage.heightFromPng++;
				}
			}
		}
		return coverage;
	}

	// -- parallax test seam ------------------------------------------------

	/** Test seam: the parallax the shader gets for {@code material}, overriding the content. */
	public static void overrideParallaxForTest(int material, float parallax) {
		if (Float.isNaN(parallax)) {
			PARALLAX_FOR_TEST.remove(Integer.valueOf(material));
		} else {
			PARALLAX_FOR_TEST.put(Integer.valueOf(material), Float.valueOf(parallax));
		}
	}

	/** Test seam: forget every parallax override. */
	public static void resetParallaxForTest() {
		PARALLAX_FOR_TEST.clear();
	}

	/** The material's own parallax depth, gated on it naming a height map. */
	private static float parallaxOf(HdMaterialDefinition definition) {
		if (definition.height() == null) {
			return 0.0F;
		}
		Float override = PARALLAX_FOR_TEST.get(Integer.valueOf(definition.texture()));
		return override != null ? override.floatValue() : definition.parallax();
	}

	/** A material's four map textures; null means the map is absent. */
	private static final class Slot {

		final OpenGl2DTexture normal;

		final OpenGl2DTexture orm;

		final OpenGl2DTexture emissive;

		final OpenGl2DTexture height;

		Slot(OpenGl2DTexture normal, OpenGl2DTexture orm, OpenGl2DTexture emissive, OpenGl2DTexture height) {
			this.normal = normal;
			this.orm = orm;
			this.emissive = emissive;
			this.height = height;
		}
	}

	/**
	 * Decodes a PNG to SIZE square ARGB texels, averaging the source texels each
	 * target texel covers. Returns null for absent or unreadable art, which the
	 * caller treats as "no map".
	 *
	 * <p>The filter matters: point sampling a 512-square normal or packed map
	 * down to 256 keeps one texel of each 2x2 block and throws the rest away, so
	 * the slope it encodes is aliased detail the shader then reads as grain on
	 * every surface. Averaging is what the authored albedo replacement
	 * ({@code HdTextureReplacements.decode}) has always done for the same reason.
	 */
	public static int[] decode(byte[] png, int size) {
		if (png == null || png.length == 0 || size <= 0) {
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
		int width = image.getWidth();
		int height = image.getHeight();
		int[] source = image.getRGB(0, 0, width, height, null, 0, width);
		boolean alpha = image.getColorModel().hasAlpha();
		int[] out = new int[size * size];
		if (size >= width || size >= height) {
			// Nothing to average: each target texel is the nearest source one.
			for (int y = 0; y < size; y++) {
				int sourceY = y * height / size;
				for (int x = 0; x < size; x++) {
					int texel = source[sourceY * width + x * width / size];
					out[y * size + x] = alpha ? texel : texel | 0xFF000000;
				}
			}
			return out;
		}
		// Downscale: average the source texels each target texel covers, weighted
		// by alpha, so fine authored detail becomes the map's own resolution
		// instead of the aliased noise point sampling leaves behind.
		for (int y = 0; y < size; y++) {
			int top = y * height / size;
			int bottom = Math.max(top + 1, (y + 1) * height / size);
			for (int x = 0; x < size; x++) {
				int left = x * width / size;
				int right = Math.max(left + 1, (x + 1) * width / size);
				long red = 0L;
				long green = 0L;
				long blue = 0L;
				long plainRed = 0L;
				long plainGreen = 0L;
				long plainBlue = 0L;
				long weight = 0L;
				int count = 0;
				for (int sourceY = top; sourceY < bottom; sourceY++) {
					for (int sourceX = left; sourceX < right; sourceX++) {
						int texel = source[sourceY * width + sourceX];
						int texelAlpha = alpha ? texel >>> 24 : 0xFF;
						red += (long) ((texel >> 16) & 0xFF) * texelAlpha;
						green += (long) ((texel >> 8) & 0xFF) * texelAlpha;
						blue += (long) (texel & 0xFF) * texelAlpha;
						plainRed += (texel >> 16) & 0xFF;
						plainGreen += (texel >> 8) & 0xFF;
						plainBlue += texel & 0xFF;
						weight += texelAlpha;
						count++;
					}
				}
				int outAlpha = alpha ? (int) ((weight + count / 2) / count) : 0xFF;
				if (weight == 0L) {
					// Every source texel in this block is transparent. Weighting
					// by alpha would throw its colour away and emit opaque black,
					// which a normal or packed map reads as a hole in the surface.
					// Its colour is still the block's average; only alpha is zero.
					out[y * size + x] = (int) (((plainRed + count / 2) / count) << 16
						| ((plainGreen + count / 2) / count) << 8
						| (plainBlue + count / 2) / count);
					continue;
				}
				int averageRed = (int) ((red + weight / 2) / weight);
				int averageGreen = (int) ((green + weight / 2) / weight);
				int averageBlue = (int) ((blue + weight / 2) / weight);
				out[y * size + x] = outAlpha << 24 | averageRed << 16 | averageGreen << 8 | averageBlue;
			}
		}
		return out;
	}
}
