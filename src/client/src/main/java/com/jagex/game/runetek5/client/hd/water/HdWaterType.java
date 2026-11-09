package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;

/**
 * One water type: the fields 634HD stores per water texture in its
 * {@code materials.json} {@code water} block, resolved for a draw.
 *
 * <p>Upstream keys a water type per material slot ({@code hdMapD[slot].w} ->
 * {@code hdWaterType}) and indexes its tables with it: the surface colour and
 * its opacity, the depth colour the seabed is tinted towards, the foam colour,
 * and the specular/gloss/normal-strength/Fresnel/animation-period group. This
 * port has the same data - {@link HdMaterialDefinition.Water} is the parse of
 * that block - and this class is the resolved type the pipeline hands the
 * shader, so a swamp's flat green water and a lake's teal water are the same
 * program with different numbers rather than different shaders.
 *
 * <p>{@link #DEFAULT} is the WATER type: 634HD's defaults in
 * {@code materials.schema.json}, and the type every water texture without a
 * {@code water} block takes. Its Fresnel share (0.85) is the value upstream's
 * "plain water is darkened by a quarter" rule tests for, which is why that rule
 * lives on {@link #plain()} rather than in a shader literal.
 *
 * <p>Colours are sRGB 0xRRGGBB, as the content stores them; the water shader
 * shades in the texture's own sRGB space, so the bytes divide straight through
 * rather than being linearised first.
 */
public final class HdWaterType {

	/** 634HD's WATER type: the schema's defaults, and what a bare texture takes. */
	public static final HdWaterType DEFAULT = new HdWaterType(0x69809C, 0xB0A492, 0x00758E,
		0.5F, 0.5F, HdWaterPipeline.DEFAULT_SHININESS, HdWaterPipeline.DEFAULT_NORMAL_STRENGTH,
		0.85F, 1.0F, true, false);

	private final int surface;

	private final int foam;

	private final int depth;

	private final float opacity;

	private final float specular;

	private final float gloss;

	private final float normalStrength;

	private final float fresnel;

	private final float duration;

	private final boolean hasFoam;

	private final boolean flat;

	private HdWaterType(int surface, int foam, int depth, float opacity, float specular, float gloss,
			float normalStrength, float fresnel, float duration, boolean hasFoam, boolean flat) {
		this.surface = surface & 0xFFFFFF;
		this.foam = foam & 0xFFFFFF;
		this.depth = depth & 0xFFFFFF;
		this.opacity = clamp(opacity, 0.0F, 1.0F);
		this.specular = clamp(specular, 0.0F, 4.0F);
		this.gloss = clamp(gloss, 1.0F, 2000.0F);
		this.normalStrength = clamp(normalStrength, 0.0F, 1.0F);
		this.fresnel = clamp(fresnel, 0.0F, 1.0F);
		this.duration = clamp(duration, 0.001F, 8.0F);
		this.hasFoam = hasFoam;
		this.flat = flat;
	}

	/**
	 * A type from the values themselves, clamped the way the content parser
	 * clamps them: the parser is one caller, the tests and any content source
	 * that does not come from a {@code materials.json} are the others.
	 */
	public static HdWaterType of(int surface, int foam, int depth, float opacity, float specular,
			float gloss, float normalStrength, float fresnel, float duration, boolean hasFoam,
			boolean flat) {
		return new HdWaterType(surface, foam, depth, opacity, specular, gloss, normalStrength,
			fresnel, duration, hasFoam, flat);
	}

	/** One material's water block; {@link #DEFAULT} when the entry has none. */
	public static HdWaterType of(HdMaterialDefinition.Water water) {
		if (water == null) {
			return DEFAULT;
		}
		return new HdWaterType(water.surface(), water.foam(), water.depth(), water.opacity(),
			water.specular(), water.gloss(), water.normalStrength(), water.fresnel(),
			water.duration(), water.hasFoam(), water.flat());
	}

	/** One material's type, or {@link #DEFAULT} when the id has no entry. */
	public static HdWaterType of(HdMaterialDefinition material) {
		return material == null ? DEFAULT : of(material.water());
	}

	/** The water's own colour, lit; the type's {@code surface}. */
	public float[] surfaceColour() {
		return components(this.surface);
	}

	/** The shore foam's colour; the type's {@code foam}. */
	public float[] foamColour() {
		return components(this.foam);
	}

	/** The seabed's tint, deeper towards black; the type's {@code depth}. */
	public float[] depthColour() {
		return components(this.depth);
	}

	public int surface() {
		return this.surface;
	}

	public int foam() {
		return this.foam;
	}

	public int depth() {
		return this.depth;
	}

	/** The least opacity, looking straight down. */
	public float opacity() {
		return this.opacity;
	}

	/** The sun's glint strength. */
	public float specular() {
		return this.specular;
	}

	/** The glint's exponent. */
	public float gloss() {
		return this.gloss;
	}

	/** The ripples' height. */
	public float normalStrength() {
		return this.normalStrength;
	}

	/** How much of the reflection shows. */
	public float fresnel() {
		return this.fresnel;
	}

	/** The animation's period multiplier on the shipped ripple periods. */
	public float duration() {
		return this.duration;
	}

	/** Foam at the shore. */
	public boolean hasFoam() {
		return this.hasFoam;
	}

	/** Opaque: the depth colour fills in for the seabed. */
	public boolean flat() {
		return this.flat;
	}

	/**
	 * True for the types carrying WATER's Fresnel amount, which 634HD darkens by
	 * a quarter: {@code spec.w > 0.84 && spec.w < 0.86} in
	 * {@code hdWaterSurface}.
	 */
	public boolean plain() {
		return this.fresnel > 0.84F && this.fresnel < 0.86F;
	}

	/** True when every field matches, so the pipeline can skip a re-upload. */
	public boolean sameAs(HdWaterType other) {
		return other != null
			&& this.surface == other.surface
			&& this.foam == other.foam
			&& this.depth == other.depth
			&& this.opacity == other.opacity
			&& this.specular == other.specular
			&& this.gloss == other.gloss
			&& this.normalStrength == other.normalStrength
			&& this.fresnel == other.fresnel
			&& this.duration == other.duration
			&& this.hasFoam == other.hasFoam
			&& this.flat == other.flat;
	}

	@Override
	public String toString() {
		return "water[surface=#" + hex(this.surface) + ", foam=#" + hex(this.foam)
			+ ", depth=#" + hex(this.depth) + ", opacity=" + this.opacity
			+ ", specular=" + this.specular + ", gloss=" + this.gloss
			+ ", normalStrength=" + this.normalStrength + ", fresnel=" + this.fresnel
			+ ", duration=" + this.duration + ", hasFoam=" + this.hasFoam
			+ ", flat=" + this.flat + "]";
	}

	private static float[] components(int colour) {
		return new float[] {
			(colour >> 16 & 0xFF) / 255.0F, (colour >> 8 & 0xFF) / 255.0F, (colour & 0xFF) / 255.0F
		};
	}

	private static String hex(int colour) {
		String digits = Integer.toHexString(colour & 0xFFFFFF);
		while (digits.length() < 6) {
			digits = "0" + digits;
		}
		return digits;
	}

	private static float clamp(float value, float min, float max) {
		return value < min ? min : (value > max ? max : value);
	}
}
