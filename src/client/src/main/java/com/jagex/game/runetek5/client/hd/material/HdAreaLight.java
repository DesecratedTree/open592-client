package com.jagex.game.runetek5.client.hd.material;

import com.jagex.game.runetek5.client.hd.content.HdContent;
import com.jagex.game.runetek5.client.hd.content.HdEnvironmentDefinition;
import com.jagex.game.runetek5.deob.Static1;
import com.jagex.game.runetek5.entity.PlayerEntity;

/**
 * The area's own sun and ambient tint, resolved from {@code environments.json}.
 *
 * <p>The stock client already uploads a level ambient and a sun colour, so
 * duplicating them would only create a second source. What this adds is the
 * datum the stock path has no field for: the area's sun and ambient
 * <em>tint</em>, which the term-sum shader multiplies over the stock light.
 * A desert's warm sun and a dungeon's cold fill then differ even when both
 * frames use the same stock level light.
 *
 * <p>The area is selected for the local player's tile and plane through
 * {@link HdContent#environmentFor}, which keeps Stage 1's rule: an entry with an
 * area wins over the default regardless of file order.
 *
 * <p>{@link #OFF} is the identity, and also what a frame without a player (the
 * login screen, a test harness) uses, so no tint can leak from an environment
 * that is not being stood in.
 */
public final class HdAreaLight {

	public static final HdAreaLight OFF = new HdAreaLight(false, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F,
		0.0F);

	private static volatile HdAreaLight current = OFF;

	/** The environment the published light was resolved from; null outside any area. */
	private static volatile HdEnvironmentDefinition currentEnvironment;

	/**
	 * The tile the published light was resolved for, so the per-draw frame read
	 * costs no lookup and allocates nothing while the player stands still. -1 in
	 * every component means "no published tile yet".
	 */
	private static int publishedTileX = -1;

	private static int publishedTileZ = -1;

	private static int publishedPlane = -1;

	/** Set by {@link #publishForTest}: the published light wins over the player. */
	private static boolean overridden;

	private final boolean active;

	private final float sunRed;

	private final float sunGreen;

	private final float sunBlue;

	private final float sunIntensity;

	private final float ambientRed;

	private final float ambientGreen;

	private final float ambientBlue;

	private final float ambientIntensity;

	private HdAreaLight(boolean active, float sunRed, float sunGreen, float sunBlue, float sunIntensity,
			float ambientRed, float ambientGreen, float ambientBlue, float ambientIntensity) {
		this.active = active;
		this.sunRed = sunRed;
		this.sunGreen = sunGreen;
		this.sunBlue = sunBlue;
		this.sunIntensity = sunIntensity;
		this.ambientRed = ambientRed;
		this.ambientGreen = ambientGreen;
		this.ambientBlue = ambientBlue;
		this.ambientIntensity = ambientIntensity;
	}

	/** Resolves the local player's area and publishes it for this frame. */
	public static HdAreaLight capture() {
		if (overridden) {
			return current;
		}
		PlayerEntity player = Static1.aClass16_Sub1_Sub5_Sub1_1;
		if (player == null) {
			// A frame without a player - the login screen, a harness - stands in
			// no area, so no tint can leak from one that is not being stood in.
			current = OFF;
			currentEnvironment = null;
			publishedTileX = -1;
			publishedTileZ = -1;
			publishedPlane = -1;
			return current;
		}
		int tileX = player.anInt6893 >> 7;
		int tileZ = player.anInt6892 >> 7;
		int plane = player.aByte82;
		if (tileX == publishedTileX && tileZ == publishedTileZ && plane == publishedPlane) {
			return current;
		}
		HdEnvironmentDefinition environment = HdContent.get().environmentFor(tileX, tileZ, plane);
		HdAreaLight light = of(environment);
		currentEnvironment = environment;
		current = light;
		publishedTileX = tileX;
		publishedTileZ = tileZ;
		publishedPlane = plane;
		return light;
	}

	/** The tint an environment definition asks for, or OFF when it states none. */
	public static HdAreaLight of(HdEnvironmentDefinition environment) {
		if (environment == null) {
			return OFF;
		}
		Integer sun = environment.sunColour();
		Integer ambient = environment.ambientColour();
		if (sun == null && ambient == null) {
			return OFF;
		}
		float sunIntensity = environment.sunIntensity();
		float ambientIntensity = environment.ambientIntensity();
		return new HdAreaLight(true, channel(sun, 16), channel(sun, 8), channel(sun, 0), sunIntensity,
			channel(ambient, 16), channel(ambient, 8), channel(ambient, 0), ambientIntensity);
	}

	private static float channel(Integer rgb, int shift) {
		return rgb == null ? 1.0F : (float) (rgb.intValue() >> shift & 0xFF) / 255.0F;
	}

	/** The frame's area light; OFF until {@link #capture()} finds one. */
	public static HdAreaLight current() {
		return current;
	}

	/**
	 * The environment the published area was resolved from, or null outside
	 * any area. The water holder reads it so a frame looks its area up once.
	 */
	public static HdEnvironmentDefinition environment() {
		return currentEnvironment;
	}

	/** Test seam: publish a resolved area light without a live player. */
	public static void publishForTest(HdAreaLight light) {
		publishForTest(light, null);
	}

	/** Test seam: publish a light with the environment it was resolved from. */
	public static void publishForTest(HdAreaLight light, HdEnvironmentDefinition environment) {
		current = light == null ? OFF : light;
		currentEnvironment = environment;
		overridden = true;
	}

	/** Test seam: forget the published area light and return to the player. */
	public static void resetForTest() {
		current = OFF;
		currentEnvironment = null;
		overridden = false;
		publishedTileX = -1;
		publishedTileZ = -1;
		publishedPlane = -1;
	}

	public boolean active() {
		return this.active;
	}

	public float sunRed() {
		return this.sunRed;
	}

	public float sunGreen() {
		return this.sunGreen;
	}

	public float sunBlue() {
		return this.sunBlue;
	}

	public float sunIntensity() {
		return this.sunIntensity;
	}

	public float ambientRed() {
		return this.ambientRed;
	}

	public float ambientGreen() {
		return this.ambientGreen;
	}

	public float ambientBlue() {
		return this.ambientBlue;
	}

	public float ambientIntensity() {
		return this.ambientIntensity;
	}

	/** True when this area has no tint to contribute. */
	public boolean isIdentity() {
		return !this.active;
	}

	/** The sun term the shader multiplies over the stock light's colour. */
	public float tintedSunRed() {
		return this.active ? this.sunRed * this.sunIntensity : 1.0F;
	}

	public float tintedSunGreen() {
		return this.active ? this.sunGreen * this.sunIntensity : 1.0F;
	}

	public float tintedSunBlue() {
		return this.active ? this.sunBlue * this.sunIntensity : 1.0F;
	}

	/** The ambient term the shader multiplies over the stock ambient. */
	public float tintedAmbientRed() {
		return this.active ? this.ambientRed * this.ambientIntensity : 1.0F;
	}

	public float tintedAmbientGreen() {
		return this.active ? this.ambientGreen * this.ambientIntensity : 1.0F;
	}

	public float tintedAmbientBlue() {
		return this.active ? this.ambientBlue * this.ambientIntensity : 1.0F;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof HdAreaLight)) {
			return false;
		}
		HdAreaLight light = (HdAreaLight) other;
		return light.active == this.active && Float.compare(light.sunRed, this.sunRed) == 0
			&& Float.compare(light.sunGreen, this.sunGreen) == 0 && Float.compare(light.sunBlue, this.sunBlue) == 0
			&& Float.compare(light.sunIntensity, this.sunIntensity) == 0
			&& Float.compare(light.ambientRed, this.ambientRed) == 0
			&& Float.compare(light.ambientGreen, this.ambientGreen) == 0
			&& Float.compare(light.ambientBlue, this.ambientBlue) == 0
			&& Float.compare(light.ambientIntensity, this.ambientIntensity) == 0;
	}

	@Override
	public int hashCode() {
		int hash = this.active ? 1 : 0;
		hash = hash * 31 + Float.floatToIntBits(this.sunRed);
		hash = hash * 31 + Float.floatToIntBits(this.sunGreen);
		hash = hash * 31 + Float.floatToIntBits(this.sunBlue);
		hash = hash * 31 + Float.floatToIntBits(this.sunIntensity);
		hash = hash * 31 + Float.floatToIntBits(this.ambientRed);
		hash = hash * 31 + Float.floatToIntBits(this.ambientGreen);
		hash = hash * 31 + Float.floatToIntBits(this.ambientBlue);
		return hash * 31 + Float.floatToIntBits(this.ambientIntensity);
	}

	@Override
	public String toString() {
		return "active=" + this.active + " sun=" + this.sunRed + "/" + this.sunGreen + "/" + this.sunBlue
			+ "x" + this.sunIntensity + " ambient=" + this.ambientRed + "/" + this.ambientGreen + "/"
			+ this.ambientBlue + "x" + this.ambientIntensity;
	}
}
