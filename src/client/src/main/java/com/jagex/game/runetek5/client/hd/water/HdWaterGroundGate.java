package com.jagex.game.runetek5.client.hd.water;

import com.jagex.game.runetek5.client.hd.ground.HdGroundWater;

/**
 * Whether 592HD's water surface may own one ground layer's draw.
 *
 * <p>The client submits its whole ground mesh twice per frame: the water pass
 * ({@code Class62} → {@code GlToolkit.AA} … {@code l()}) draws it with the water
 * effect, then the terrain pass draws it again with the terrain program. Both
 * passes cover the same tiles at the same depth, so a tile drawn by two
 * different programs is decided by depth and alpha rather than by geometry -
 * and the second pass wins. A water layer therefore has to run the water surface
 * in <em>both</em> passes, and a dry layer must never run it in either.
 *
 * <p>Whether a layer is water is two facts, because the client answers it two
 * different ways depending on the region:
 *
 * <ul>
 * <li>the layer's material - its stock effect slot is one of the water types
 * ({@link HdWaterGate#isWaterMaterial(int)}). This is the only answer for a
 * lake, a river or the ocean in an ordinary region;</li>
 * <li>the stock per-tile depth byte ({@link HdGroundWater#submerged()}). This is
 * the answer for an underwater region, where {@code Terrain} fills the byte and
 * the material may be any ground type.</li>
 * </ul>
 *
 * <p>Either one standing is enough, and nothing else is: the region's own
 * selection covers the whole visible mesh, dry tiles included, so owning every
 * layer would paint ground with a surface that samples the water maps instead of
 * the ground atlas. That is the shape of both reported defects - water whose
 * surface the terrain pass replaces, and ground squares that lose their texture
 * under an opaque water draw.
 *
 * <p>Pure, like {@link HdWaterGate}, so the rule is a truth table rather than an
 * if-chain buried in the toolkit.
 */
public final class HdWaterGroundGate {

	private HdWaterGroundGate() {
	}

	/**
	 * True when this ground layer draws with the terrain-format water surface.
	 *
	 * @param requested     the row's own answer: 592HD enabled and Water not Off
	 * @param waterMaterial the layer's material is one of the stock water types
	 * @param water         how much water the client says stands over this tile
	 */
	public static boolean owns(boolean requested, boolean waterMaterial, HdGroundWater water) {
		if (!requested) {
			return false;
		}
		if (waterMaterial) {
			return true;
		}
		return water != null && water.submerged();
	}
}
