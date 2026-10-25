package com.jagex.game.runetek5.client.hd.post;

/**
 * The GLSL 120 full-screen pass that carries the Effects rows.
 *
 * <p>One program, one screen-sized copy of the frame, four independent stages.
 * Doing them in one pass means a player can enable all four for the cost of a
 * single texture read per pixel and one extra draw; a stage whose row is off is
 * a multiply by 0 or a flag the driver can fold, not a second pass.
 *
 * <p>Two constraints come from the client it draws on:
 *
 * <ul>
 * <li>the source is the composited frame copied out of the back buffer, so it
 * is display-referred: the tonemap is a highlight shoulder
 * ({@code HdPostParams.KNEE}) rather than a scene-referred curve, and grading
 * stays near unity;</li>
 * <li>the stage order is fixed and matters: FXAA first (it must see the
 * rendered edge, not a graded one), then exposure and tonemap, then grading,
 * then dither, which must be the last thing that touches a level.</li>
 * </ul>
 */
public final class HdPostSource {

	/** {@code #version} the pass declares; the 592 context accepts 120. */
	public static final int GLSL_VERSION = 120;

	/** {@code sampler2D}: the copied frame. */
	public static final String UNIFORM_SCREEN = "uScreen";

	/** {@code vec2}: one texel in normalised units, for the FXAA taps. */
	public static final String UNIFORM_TEXEL = "uTexel";

	/** {@code float}: exposure before the shoulder. */
	public static final String UNIFORM_EXPOSURE = "uExposure";

	/** {@code float}: 1 when the tonemap row is on. */
	public static final String UNIFORM_TONEMAP = "uTonemap";

	/** {@code float}: 1 when the grading row is on. */
	public static final String UNIFORM_GRADE = "uGrade";

	/** {@code vec4}: per-channel gain, {@code .rgb} used. */
	public static final String UNIFORM_GAIN = "uGain";

	/** {@code vec4}: per-channel lift, {@code .rgb} used. */
	public static final String UNIFORM_LIFT = "uLift";

	/** {@code float}: saturation multiplier. */
	public static final String UNIFORM_SATURATION = "uSaturation";

	/** {@code float}: dither amplitude in colour units; 0 disables it. */
	public static final String UNIFORM_DITHER = "uDither";

	/** {@code float}: 1 when the FXAA row is on. */
	public static final String UNIFORM_FXAA = "uFxaa";

	/** {@code float}: the level the tonemap shoulder starts at. */
	public static final String UNIFORM_KNEE = "uKnee";

	/** {@code float}: how far the shoulder compresses above the knee. */
	public static final String UNIFORM_SHOULDER = "uShoulder";

	private static final String[] UNIFORMS = {
		UNIFORM_SCREEN, UNIFORM_TEXEL, UNIFORM_EXPOSURE, UNIFORM_TONEMAP, UNIFORM_GRADE,
		UNIFORM_GAIN, UNIFORM_LIFT, UNIFORM_SATURATION, UNIFORM_DITHER, UNIFORM_FXAA,
		UNIFORM_KNEE, UNIFORM_SHOULDER
	};

	public static final String VERTEX = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "varying vec2 vScreenCoord;\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tgl_Position = ftransform();\n"
		+ "\t// Raw attribute: a program does not apply the fixed-function texture matrix.\n"
		+ "\tvScreenCoord = gl_MultiTexCoord0.xy;\n"
		+ "}\n";

	public static final String FRAGMENT = "#version " + GLSL_VERSION + "\n"
		+ "\n"
		+ "uniform sampler2D uScreen;\n"
		+ "uniform vec2 uTexel;\n"
		+ "uniform float uExposure;\n"
		+ "uniform float uTonemap;\n"
		+ "uniform float uGrade;\n"
		+ "uniform vec4 uGain;\n"
		+ "uniform vec4 uLift;\n"
		+ "uniform float uSaturation;\n"
		+ "uniform float uDither;\n"
		+ "uniform float uFxaa;\n"
		+ "uniform float uKnee;\n"
		+ "uniform float uShoulder;\n"
		+ "\n"
		+ "varying vec2 vScreenCoord;\n"
		+ "\n"
		+ "float luma(vec3 colour) {\n"
		+ "\treturn dot(colour, vec3(0.299, 0.587, 0.114));\n"
		+ "}\n"
		+ "\n"
		+ "// Recursive Bayer matrix: ordered, so it does not move between frames.\n"
		+ "float bayer2(vec2 cell) {\n"
		+ "\tcell = floor(cell);\n"
		+ "\treturn fract(cell.x * 0.5 + cell.y * cell.y * 0.75);\n"
		+ "}\n"
		+ "\n"
		+ "float bayer4(vec2 cell) {\n"
		+ "\treturn bayer2(cell * 0.5) * 0.25 + bayer2(cell);\n"
		+ "}\n"
		+ "\n"
		+ "void main() {\n"
		+ "\tvec2 uv = vScreenCoord;\n"
		+ "\tvec3 colour = texture2D(uScreen, uv).rgb;\n"
		+ "\n"
		+ "\tif (uFxaa > 0.5) {\n"
		+ "\t\tvec3 north = texture2D(uScreen, uv + vec2(0.0, uTexel.y)).rgb;\n"
		+ "\t\tvec3 south = texture2D(uScreen, uv - vec2(0.0, uTexel.y)).rgb;\n"
		+ "\t\tvec3 east = texture2D(uScreen, uv + vec2(uTexel.x, 0.0)).rgb;\n"
		+ "\t\tvec3 west = texture2D(uScreen, uv - vec2(uTexel.x, 0.0)).rgb;\n"
		+ "\t\tfloat centre = luma(colour);\n"
		+ "\t\tfloat lowest = min(centre, min(min(luma(north), luma(south)), min(luma(east), luma(west))));\n"
		+ "\t\tfloat highest = max(centre, max(max(luma(north), luma(south)), max(luma(east), luma(west))));\n"
		+ "\t\tfloat range = highest - lowest;\n"
		+ "\t\tif (range > 0.08) {\n"
		+ "\t\t\tvec3 average = (colour + north + south + east + west) * 0.2;\n"
		+ "\t\t\tcolour = mix(colour, average, clamp(range * 2.0, 0.0, 0.75));\n"
		+ "\t\t}\n"
		+ "\t}\n"
		+ "\n"
		+ "\tcolour *= uExposure;\n"
		+ "\tif (uTonemap > 0.5) {\n"
		+ "\t\t// Shoulder above the knee: below it the curve is the identity, so the\n"
		+ "\t\t// interface and mid tones keep their levels, and highlights roll to 1.\n"
		+ "\t\tvec3 knee = max(colour - vec3(uKnee), 0.0);\n"
		+ "\t\tcolour = min(colour, vec3(uKnee))\n"
		+ "\t\t\t+ uShoulder * knee / (knee + vec3(uShoulder));\n"
		+ "\t}\n"
		+ "\n"
		+ "\tif (uGrade > 0.5) {\n"
		+ "\t\tcolour = colour * uGain.rgb + uLift.rgb;\n"
		+ "\t\tcolour = mix(vec3(luma(colour)), colour, uSaturation);\n"
		+ "\t}\n"
		+ "\n"
		+ "\tcolour = clamp(colour, 0.0, 1.0);\n"
		+ "\tif (uDither > 0.0) {\n"
		+ "\t\tcolour += (bayer4(gl_FragCoord.xy) - 0.5) * uDither;\n"
		+ "\t\tcolour = clamp(colour, 0.0, 1.0);\n"
		+ "\t}\n"
		+ "\n"
		+ "\tgl_FragColor = vec4(colour, 1.0);\n"
		+ "}\n";

	private HdPostSource() {
	}

	/** Every uniform the pass sets, in declaration order. */
	public static String[] uniforms() {
		return UNIFORMS.clone();
	}
}
