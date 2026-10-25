package com.jagex.game.runetek5.client.hd.shader;

/**
 * Where one draw's vertices are and the rows that map them into the spaces the
 * lighting program reasons about.
 *
 * <ul>
 * <li>{@link #modelToScene} - model-local to scene, or null when the draw's
 * vertices are already in scene space (the terrain pass) or when its modelview
 * already is the transform (an ordinary model draw).</li>
 * <li>{@link #sceneToEye} - {@code rotX(180) * V} for this frame, or null for a
 * draw that reads eye space from the modelview instead. Supplied by the terrain
 * (whose modelview is a tile-window ortho, not the camera) and by a displaced
 * vegetation draw (whose moved vertex has no modelview entry).</li>
 * <li>{@link #sceneSpace} - true when {@code gl_Vertex} is a scene position
 * rather than a model position.</li>
 * </ul>
 *
 * <p>Only the draws that cannot read a transform from GL state carry rows here;
 * {@link #MODEL} is the ordinary model path and uploads nothing new.
 */
public final class HdDrawSpace {

	/** An ordinary model draw: model space, modelview transform, no extra rows. */
	public static final HdDrawSpace MODEL = new HdDrawSpace(null, null, false);

	private final float[] modelToScene;

	private final float[] sceneToEye;

	private final boolean sceneSpace;

	private HdDrawSpace(float[] modelToScene, float[] sceneToEye, boolean sceneSpace) {
		this.modelToScene = modelToScene;
		this.sceneToEye = sceneToEye;
		this.sceneSpace = sceneSpace;
	}

	/** A model draw with its own model-to-scene matrix and, if displaced, eye rows. */
	public static HdDrawSpace model(float[] modelToScene, float[] sceneToEye) {
		return new HdDrawSpace(modelToScene, sceneToEye, false);
	}

	/** A draw whose vertices are scene positions: the terrain's tile window. */
	public static HdDrawSpace sceneSpace(float[] modelToScene, float[] sceneToEye) {
		return new HdDrawSpace(modelToScene, sceneToEye, true);
	}

	/** The general form, for a test that needs the three flags apart. */
	public static HdDrawSpace of(float[] modelToScene, float[] sceneToEye, boolean sceneSpace) {
		return new HdDrawSpace(modelToScene, sceneToEye, sceneSpace);
	}

	/** Column-major model-to-scene matrix, or null for scene space / modelview. */
	public float[] modelToScene() {
		return this.modelToScene;
	}

	/** Column-major scene-to-eye rows, or null when the draw does not supply them. */
	public float[] sceneToEye() {
		return this.sceneToEye;
	}

	public boolean sceneSpace() {
		return this.sceneSpace;
	}
}
