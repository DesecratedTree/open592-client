package com.jagex.game.runetek5.client.hd.water;

/**
 * Everything the 592HD water gate needs for one water draw.
 *
 * <p>Same shape as {@code HdDrawRequest}: gathered at the seam, evaluated by
 * {@link HdWaterGate}, never re-read from the settings inside the pipeline. The
 * facts that decide the draw are:
 *
 * <ul>
 * <li>{@code requested} - the Water row is on, resolved once by
 * {@link HdWaterGate#requested()};</li>
 * <li>{@code custom} - that row's third value, resolved once by
 * {@link HdWaterGate#custom()}, asking for the custom surface;</li>
 * <li>{@code reflective} - the water material's stock effect slot is the
 * reflective rippling water type, so this surface is the one the stock client
 * would have handed {@code ReflectiveRipplingWaterEffect};</li>
 * <li>{@code breaking} - the slot is the breaking water type, and
 * {@code breakingParams} carries that material's own decoded numbers, so the
 * surface is driven by the data instead of by a look invented here;</li>
 * <li>{@code environment} - the toolkit has an environment cube bound
 * ({@code GlToolkit.method1946()}), which every surface samples: with no cube
 * the sheen is the fog band's colour;</li>
 * <li>{@code caustics} - the Caustics row as the custom surface's pattern
 * strength, 0 when the row is off;</li>
 * <li>{@code seconds} - the animation clock for this frame.</li>
 * </ul>
 *
 * <p>The decision travels with the draw: a water region starts as
 * {@link #still} because the region itself has no material, and the material
 * bind that follows asks for the type its effect slot names. Nothing downstream
 * decides what to draw from the settings.
 */
public final class HdWaterRequest {

	private final boolean requested;

	private final boolean custom;

	private final boolean reflective;

	private final boolean breaking;

	private final boolean environment;

	private final float caustics;

	private final float seconds;

	private final HdWaterBreaking breakingParams;

	/** This draw's water type; {@link HdWaterType#DEFAULT} until a material says otherwise. */
	private final HdWaterType type;

	private HdWaterRequest(boolean requested, boolean custom, boolean reflective, boolean breaking,
			boolean environment, float caustics, float seconds, HdWaterBreaking breakingParams,
			HdWaterType type) {
		this.requested = requested;
		this.custom = custom;
		this.reflective = reflective;
		this.breaking = breaking;
		this.environment = environment;
		this.caustics = caustics;
		this.seconds = seconds;
		this.breakingParams = breakingParams;
		this.type = type == null ? HdWaterType.DEFAULT : type;
	}

	/** The draw a water region starts with: the standard surface, calm until a material says otherwise. */
	public static HdWaterRequest still(boolean requested, float seconds) {
		return new HdWaterRequest(requested, false, false, false, false, 0.0F, seconds,
			HdWaterBreaking.NONE, HdWaterType.DEFAULT);
	}

	/** The same draw on the row's custom surface. */
	public static HdWaterRequest still(boolean requested, boolean custom, float seconds) {
		return new HdWaterRequest(requested, custom, false, false, false, 0.0F, seconds,
			HdWaterBreaking.NONE, HdWaterType.DEFAULT);
	}

	/** The draw a bound water material asks for, on the standard surface. */
	public static HdWaterRequest current(boolean requested, boolean reflective, boolean environment, float seconds) {
		return new HdWaterRequest(requested, false, reflective, false, environment, 0.0F, seconds,
			HdWaterBreaking.NONE, HdWaterType.DEFAULT);
	}

	/** The draw a bound water material asks for, on the row's chosen surface style. */
	public static HdWaterRequest current(boolean requested, boolean custom, boolean reflective,
			boolean environment, float seconds) {
		return new HdWaterRequest(requested, custom, reflective, false, environment, 0.0F, seconds,
			HdWaterBreaking.NONE, HdWaterType.DEFAULT);
	}

	/** The same draw with the reflection half set; for the gate's truth table. */
	public HdWaterRequest reflection(boolean reflective, boolean environment) {
		return new HdWaterRequest(this.requested, this.custom, reflective, this.breaking, environment,
			this.caustics, this.seconds, this.breakingParams, this.type);
	}

	/** The same draw with a cube bound or not; this is the flag the surfaces sample with. */
	public HdWaterRequest withEnvironment(boolean environment) {
		return new HdWaterRequest(this.requested, this.custom, this.reflective, this.breaking, environment,
			this.caustics, this.seconds, this.breakingParams, this.type);
	}

	/** The same draw with the Caustics row's strength on the custom surface. */
	public HdWaterRequest withCaustics(float caustics) {
		return new HdWaterRequest(this.requested, this.custom, this.reflective, this.breaking,
			this.environment, Math.max(0.0F, caustics), this.seconds, this.breakingParams, this.type);
	}

	/** The same draw on the given surface style; for the gate's truth table. */
	public HdWaterRequest style(boolean custom) {
		return new HdWaterRequest(this.requested, custom, this.reflective, this.breaking, this.environment,
			this.caustics, this.seconds, this.breakingParams, this.type);
	}

	/**
	 * The same draw, now with the breaking type's parameters. {@link
	 * HdWaterBreaking#NONE} clears it again, which is what the gate's truth table
	 * uses for "a calm material".
	 */
	public HdWaterRequest withBreaking(HdWaterBreaking params) {
		HdWaterBreaking decoded = params == null ? HdWaterBreaking.NONE : params;
		return new HdWaterRequest(this.requested, this.custom, this.reflective, !decoded.absent(),
			this.environment, this.caustics, this.seconds, decoded, this.type);
	}

	/**
	 * The same draw with a resolved water type: the material's {@code water} block
	 * (634HD's {@code hdWaterType(slot)}). A null type is the plain WATER type, so
	 * a caller that has no content entry cannot blank the surface.
	 */
	public HdWaterRequest withType(HdWaterType type) {
		return new HdWaterRequest(this.requested, this.custom, this.reflective, this.breaking,
			this.environment, this.caustics, this.seconds, this.breakingParams, type);
	}

	/** This draw's water type; never null. */
	public HdWaterType type() {
		return this.type;
	}

	public boolean requested() {
		return this.requested;
	}

	/** True when the row asks for the custom surface. */
	public boolean custom() {
		return this.custom;
	}

	/** True when this material's stock effect is the reflective water type. */
	public boolean reflective() {
		return this.reflective;
	}

	/** True when this material's stock effect is the breaking water type. */
	public boolean breaking() {
		return this.breaking;
	}

	/** That material's decoded numbers; {@link HdWaterBreaking#NONE} when calm. */
	public HdWaterBreaking breakingParams() {
		return this.breakingParams;
	}

	/** True when the toolkit has an environment cube to sample. */
	public boolean environment() {
		return this.environment;
	}

	/** The Caustics row as the custom surface's pattern strength; 0 when off. */
	public float caustics() {
		return this.caustics;
	}

	public float seconds() {
		return this.seconds;
	}
}
