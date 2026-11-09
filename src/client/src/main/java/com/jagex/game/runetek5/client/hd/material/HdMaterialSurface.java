package com.jagex.game.runetek5.client.hd.material;

import com.jagex.game.runetek5.client.hd.content.HdMaterialDefinition;

/**
 * The material maps actually available for one draw, as the shader sees them.
 *
 * <p>Separate from {@link HdMaterialDefinition} because the file says what a
 * material <em>should</em> have while this says what was decoded and uploaded:
 * a PNG that fails to read, or a material whose texture never streams in, must
 * fall back to the face normal and no occlusion rather than sampling an empty
 * unit. The flags are exactly the shader's {@code uMaterialFlags}.
 *
 * <p>{@link #OFF} is the identity: no maps, so the shader keeps the face normal,
 * no occlusion, no emission and no highlight - the stock per-pixel path.
 */
public final class HdMaterialSurface {

	public static final HdMaterialSurface OFF = new HdMaterialSurface(false, false, false, false, false, 0.0F,
		0.0F, 0.0F);

	private final boolean normal;

	private final boolean orm;

	private final boolean emissive;

	private final boolean height;

	private final boolean unlit;

	private final float specular;

	private final float emissiveStrength;

	private final float parallax;

	private HdMaterialSurface(boolean normal, boolean orm, boolean emissive, boolean height, boolean unlit,
			float specular, float emissiveStrength, float parallax) {
		this.normal = normal;
		this.orm = orm;
		this.emissive = emissive;
		this.height = height;
		this.unlit = unlit;
		this.specular = specular;
		this.emissiveStrength = emissiveStrength;
		this.parallax = parallax;
	}

	/** The surface for a definition, given which maps were decoded. */
	public static HdMaterialSurface of(HdMaterialDefinition definition, boolean normal, boolean orm,
			boolean emissive, boolean height) {
		return of(definition, normal, orm, emissive, height,
			definition == null ? 0.0F : definition.parallax());
	}

	/**
	 * The same, with the parallax depth the resolver decided for this material:
	 * zero unless a height map was found, so a depth nothing can read is never
	 * sent to the shader.
	 */
	public static HdMaterialSurface of(HdMaterialDefinition definition, boolean normal, boolean orm,
			boolean emissive, boolean height, float parallax) {
		if (definition == null || (!normal && !orm && !emissive && !height)) {
			return OFF;
		}
		boolean unlit = definition.unlit();
		return new HdMaterialSurface(normal, orm, emissive, height, unlit, definition.specular(),
			definition.emissiveStrength(), unlit ? 0.0F : parallax);
	}

	public boolean normal() {
		return this.normal;
	}

	public boolean orm() {
		return this.orm;
	}

	public boolean emissive() {
		return this.emissive;
	}

	public boolean height() {
		return this.height;
	}

	public boolean unlit() {
		return this.unlit;
	}

	public float specular() {
		return this.specular;
	}

	public float emissiveStrength() {
		return this.emissiveStrength;
	}

	public float parallax() {
		return this.parallax;
	}

	/** True when no map is available and the shader must keep the stock path. */
	public boolean isIdentity() {
		return !this.normal && !this.orm && !this.emissive && !this.height;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdMaterialSurface)) {
			return false;
		}
		HdMaterialSurface surface = (HdMaterialSurface) other;
		return surface.normal == this.normal && surface.orm == this.orm && surface.emissive == this.emissive
			&& surface.height == this.height && surface.unlit == this.unlit
			&& Float.compare(surface.specular, this.specular) == 0
			&& Float.compare(surface.emissiveStrength, this.emissiveStrength) == 0
			&& Float.compare(surface.parallax, this.parallax) == 0;
	}

	@Override
	public int hashCode() {
		int hash = this.normal ? 1 : 0;
		hash = hash * 31 + (this.orm ? 1 : 0);
		hash = hash * 31 + (this.emissive ? 1 : 0);
		hash = hash * 31 + (this.height ? 1 : 0);
		hash = hash * 31 + (this.unlit ? 1 : 0);
		hash = hash * 31 + Float.floatToIntBits(this.specular);
		hash = hash * 31 + Float.floatToIntBits(this.emissiveStrength);
		return hash * 31 + Float.floatToIntBits(this.parallax);
	}

	@Override
	public String toString() {
		return "normal=" + this.normal + " orm=" + this.orm + " emissive=" + this.emissive + " height="
			+ this.height + " unlit=" + this.unlit + " specular=" + this.specular;
	}
}
