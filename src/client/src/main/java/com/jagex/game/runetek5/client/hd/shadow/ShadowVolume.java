package com.jagex.game.runetek5.client.hd.shadow;

/**
 * The world volume the sun's map must cover this frame: where it is centred and
 * how far it reaches.
 *
 * <p>Explicit rather than derived from the camera's far plane, for the reason
 * the map's own documentation gives: a 3584-unit volume in a 1024-texel map is
 * 3.5 scene units per texel, and a one-texel shadow edge is wider than a fence
 * post. The radius is clamped so a caller cannot ask for a volume so small the
 * camera's own receivers fall outside it, or so large the map is useless.
 *
 * <p>Pure: the scene renderer decides the centre and the settings decide the
 * radius, and both are pinned here so the pass cannot drift from the volume the
 * shader is told about.
 */
public final class ShadowVolume {

	/** Scene units; 2048 at 1024 texels is 4 units per texel. */
	public static final float DEFAULT_RADIUS = 2048.0F;

	/** Below this the map is smaller than a character's shadow. */
	public static final float MIN_RADIUS = 256.0F;

	/** Above this the map's texels are wider than a tile. */
	public static final float MAX_RADIUS = 8192.0F;

	private final float centreX;

	private final float centreY;

	private final float centreZ;

	private final float radius;

	private ShadowVolume(float centreX, float centreY, float centreZ, float radius) {
		this.centreX = centreX;
		this.centreY = centreY;
		this.centreZ = centreZ;
		this.radius = radius;
	}

	/** A volume centred on a scene point, with the radius clamped into range. */
	public static ShadowVolume around(float centreX, float centreY, float centreZ, float radius) {
		float clamped = radius;
		if (!(clamped >= MIN_RADIUS)) {
			clamped = MIN_RADIUS;
		} else if (clamped > MAX_RADIUS) {
			clamped = MAX_RADIUS;
		}
		return new ShadowVolume(centreX, centreY, centreZ, clamped);
	}

	/** The default volume around the camera. */
	public static ShadowVolume aroundCamera(float cameraX, float cameraY, float cameraZ) {
		return around(cameraX, cameraY, cameraZ, DEFAULT_RADIUS);
	}

	public float centreX() {
		return this.centreX;
	}

	public float centreY() {
		return this.centreY;
	}

	public float centreZ() {
		return this.centreZ;
	}

	public float radius() {
		return this.radius;
	}

	/**
	 * How many whole tiles of the scene the volume covers horizontally, rounded
	 * up and with one tile of margin. The caster collector walks this box: it is
	 * the only way an object at the volume's edge still lands in the map.
	 */
	public int tileRadius() {
		return (int) Math.ceil(this.radius / 128.0F) + 1;
	}
}
