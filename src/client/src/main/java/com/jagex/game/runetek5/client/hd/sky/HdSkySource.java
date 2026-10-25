package com.jagex.game.runetek5.client.hd.sky;

/**
 * GLSL 1.20 source for 592HD's sky dome.
 *
 * <p>This client has no sky geometry and no sky draw at all, so unlike the
 * water and post passes there is nothing to sit in front of: the pass owns its
 * own submission. It is a full-screen quad drawn under identity projection and
 * identity modelview, which makes the vertex position the normalised device
 * coordinate the fragment stage needs, and each fragment turns that coordinate
 * back into a scene-space direction through the camera the toolkit already
 * holds ({@link SkyRaySpace}). Nothing is rasterised into the depth buffer, so
 * the dome is a background rather than an object: world geometry drawn after it
 * covers it exactly as it would cover a clear colour.
 *
 * <p>Why the colours are uniforms and not a palette in here: the sky has to be
 * lit by the same sun the scene is lit by, and the toolkit's sun colour and fog
 * colour are per-level cache data. {@link HdSkyColours} turns those two into
 * the four terms below, in Java, so the exact same arithmetic is testable
 * without a driver - the shader only interpolates between the terms and adds
 * the disc.
 *
 * <p>Two details are load-bearing. The vertex stage writes
 * {@code gl_FrontColor}/{@code gl_BackColor} because a shader that leaves both
 * unset leaves the fragment stage's colour undefined on NVIDIA, which is the
 * same trap the lighting program documents. And the disc's edge is a
 * {@code smoothstep} whose edges are ordered {@code outer < inner}, because GLSL
 * leaves {@code smoothstep} undefined when {@code edge0 >= edge1}.
 */
public final class HdSkySource {

	/** {@code #version} the sources declare; the same legacy profile the other passes use. */
	public static final int GLSL_VERSION = 120;

	/** {@code vec2}: the projection's {@code m00, m11}. */
	public static final String UNIFORM_PROJ_SCALE = "uProjScale";

	/** {@code vec2}: the projection's {@code m08, m09}, for an off-centre frustum. */
	public static final String UNIFORM_PROJ_OFFSET = "uProjOffset";

	/**
	 * {@code vec4}s: the screen's right, up and centre rays in scene
	 * coordinates, with the toolkit's {@code rotX(180)} already folded in; see
	 * {@link SkyRaySpace#axes}.
	 */
	public static final String UNIFORM_EYE_X = "uEyeX";

	public static final String UNIFORM_EYE_Y = "uEyeY";

	public static final String UNIFORM_EYE_Z = "uEyeZ";

	/** {@code vec4}: the lighting sun's direction, normalised. */
	public static final String UNIFORM_SUN_DIR = "uSunDir";

	/** {@code vec4}: the disc and glow colour. */
	public static final String UNIFORM_SUN_COLOUR = "uSunColour";

	/** {@code vec4}: the horizon colour, which is the fog band's colour. */
	public static final String UNIFORM_HORIZON = "uHorizon";

	/** {@code vec4}: the zenith colour. */
	public static final String UNIFORM_ZENITH = "uZenith";

	/** {@code vec4}: the below-horizon fill colour. */
	public static final String UNIFORM_AMBIENT = "uAmbient";

	/** {@code vec4}: {@code (inner cos, outer cos, glow strength, 0)}. */
	public static final String UNIFORM_SUN_SHAPE = "uSunShape";

	private static final String[] UNIFORMS = {
		UNIFORM_PROJ_SCALE, UNIFORM_PROJ_OFFSET, UNIFORM_EYE_X, UNIFORM_EYE_Y, UNIFORM_EYE_Z,
		UNIFORM_SUN_DIR, UNIFORM_SUN_COLOUR, UNIFORM_HORIZON, UNIFORM_ZENITH, UNIFORM_AMBIENT,
		UNIFORM_SUN_SHAPE
	};

	/**
	 * The quad is drawn under identity matrices, so the vertex position is
	 * already the clip coordinate; {@code ftransform()} still applies them, which
	 * keeps the submission identical to the stock fixed-function batches.
	 */
	public static final String VERTEX = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "varying vec2 vNdc;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvNdc = gl_Vertex.xy;\n"
		+ "\tgl_Position = ftransform();\n"
		+ "\tgl_FrontColor = gl_Color;\n"
		+ "\tgl_BackColor = gl_Color;\n"
		+ "}\n";

	public static final String FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform vec2 uProjScale;\n"
		+ "uniform vec2 uProjOffset;\n"
		+ "uniform vec4 uEyeX;\n"
		+ "uniform vec4 uEyeY;\n"
		+ "uniform vec4 uEyeZ;\n"
		+ "uniform vec4 uSunDir;\n"
		+ "uniform vec4 uSunColour;\n"
		+ "uniform vec4 uHorizon;\n"
		+ "uniform vec4 uZenith;\n"
		+ "uniform vec4 uAmbient;\n"
		+ "uniform vec4 uSunShape;\n"
		+ "\n"
		+ "varying vec2 vNdc;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\t// The pixel's ray: the camera's right and up axes scaled by the\n"
		+ "\t// normalised device coordinate, plus its centre ray.\n"
		+ "\tvec3 eye = vec3((vNdc.x + uProjOffset.x) / uProjScale.x,\n"
		+ "\t\t(vNdc.y + uProjOffset.y) / uProjScale.y, 1.0);\n"
		+ "\tvec3 direction = normalize(uEyeX.xyz * eye.x + uEyeY.xyz * eye.y + uEyeZ.xyz);\n"
		+ "\t// This world's y axis points down, so height above the horizon is\n"
		+ "\t// -direction.y. Above it the dome climbs to the zenith; below it,\n"
		+ "\t// toward the fill the world's fog fades into.\n"
		+ "\tfloat height = -direction.y;\n"
		+ "\tvec3 colour;\n"
		+ "\tif (height >= 0.0) {\n"
		+ "\t\tcolour = mix(uHorizon.rgb, uZenith.rgb, smoothstep(0.0, 0.55, height));\n"
		+ "\t} else {\n"
		+ "\t\tcolour = mix(uHorizon.rgb, uAmbient.rgb, smoothstep(0.0, 0.30, -height));\n"
		+ "\t}\n"
		+ "\tfloat cosSun = dot(direction, normalize(uSunDir.xyz));\n"
		+ "\t// Ordered outer < inner: smoothstep is undefined the other way round.\n"
		+ "\tcolour += uSunColour.rgb * smoothstep(uSunShape.y, uSunShape.x, cosSun);\n"
		+ "\tcolour += uSunColour.rgb * (pow(max(cosSun, 0.0), 24.0) * uSunShape.z);\n"
		+ "\tgl_FragColor = vec4(clamp(colour, 0.0, 1.0), 1.0);\n"
		+ "}\n";

	private HdSkySource() {
	}

	/** Every uniform the pipeline must be able to locate, in declaration order. */
	public static String[] uniforms() {
		return UNIFORMS.clone();
	}

	/**
	 * The colour the shader produces for one direction, as the shader computes
	 * it. This is the prediction the pixel proof compares against; the render
	 * path never calls it.
	 */
	public static float[] shade(HdSkyColours colours, float[] sunDirection, float[] direction, float[] out) {
		float[] result = out == null ? new float[3] : out;
		float[] horizon = colours.horizon();
		float[] zenith = colours.zenith();
		float[] ambient = colours.ambient();
		float[] sun = colours.sun();
		// The world's y axis points down, so height above the horizon is -y.
		float height = -direction[1];
		float factor;
		float[] from;
		float[] to;
		if (height >= 0.0F) {
			factor = HdSkyColours.smoothstep(0.0F, 0.55F, height);
			from = horizon;
			to = zenith;
		} else {
			factor = HdSkyColours.smoothstep(0.0F, 0.30F, -height);
			from = horizon;
			to = ambient;
		}
		float cosSun = dot(direction, sunDirection);
		float disc = HdSkyColours.smoothstep(HdSkyColours.SUN_COS_OUTER, HdSkyColours.SUN_COS_INNER, cosSun);
		float glow = (float) Math.pow(Math.max(cosSun, 0.0F), 24.0F) * HdSkyColours.SUN_GLOW;
		for (int channel = 0; channel < 3; channel++) {
			float colour = from[channel] + (to[channel] - from[channel]) * factor;
			result[channel] = HdSkyColours.clamp01(colour + sun[channel] * (disc + glow));
		}
		return result;
	}

	private static float dot(float[] first, float[] second) {
		return first[0] * second[0] + first[1] * second[1] + first[2] * second[2];
	}
}
