package com.jagex.game.runetek5.client.hd.state;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

/**
 * The fixed-function state a frame boundary is defined by: the bits one pass can
 * leave behind for the next one.
 *
 * <p>Two snapshots of the same shape exist - what GL actually has
 * ({@link #actual()}), and what {@code GlToolkit}'s caches claim
 * ({@code GlToolkit.hdGlState()}) - and {@link #differences(HdGlState)} names
 * every field where the two disagree. The toolkit's setters short-circuit on
 * their caches ({@code method1958} returns when the requested blend mode is
 * already the cached one), so a cache that lies is not a cosmetic problem: the
 * next draw that asks for the "already applied" value issues no GL call and
 * inherits whatever the last pass really left on.
 *
 * <p>Texture state is recorded per unit, not for the active unit alone. A pass
 * binds its own texture on a numbered unit and leaves the active unit where it
 * found it, so the stale binding is invisible from the active unit while a later
 * draw on that numbered unit - the water reflection, a material, the atlas -
 * samples it. Each unit records both targets and whether each target is
 * <em>enabled</em>, because that is what decides whether a draw can sample the
 * name: a residue left bound on a disabled target is not state a draw sees, and
 * a unit the cache believes is empty but GL has enabled is exactly the bug this
 * comparison exists to find. Bindings are therefore compared only where either
 * side has the target enabled; the enables themselves are always compared.
 *
 * <p>Read-backs go through LWJGL's {@code GL11}/{@code GL20} rather than the
 * {@code jaggl} wrapper: the wrapper's {@code glGetIntegerv} copy-back answers
 * zeroes for several of these enums on the drivers this client runs on, which
 * would make the probe agree with a broken cache.
 *	 * <p>{@link #UNKNOWN} means "this side keeps no value for it", so
	 * {@link #differences(HdGlState)} skips the field rather than inventing a
	 * mismatch: before the toolkit has prepared a frame it has no function to
	 * claim, and a snapshot taken then says so.
	 *
	 * <p>The alpha-test function, the depth function and the bound program are
	 * compared as well as the toggles. They are the fields a pass can move while
	 * leaving every enable flag looking right: an alpha-test function left at the
	 * shadow cut-out's half-opaque threshold makes the interface's panels see
	 * through, and a program left bound replaces the fixed-function blend with a
	 * lit or water fragment shader. {@code GlToolkit} records the ones it issues
	 * and the one an HD pass has bound; a raw snapshot reads all three back.
	 */
public final class HdGlState {

	/** The cached side has no value for this field. */
	public static final int UNKNOWN = Integer.MIN_VALUE;

	/** Texture units recorded per snapshot. */
	public static final int UNITS = 8;

	/** {@code GL_TEXTURE_BINDING_CUBE_MAP}, which this LWJGL build does not name. */
	private static final int TEXTURE_BINDING_CUBE_MAP = 34068;

	private static final int TEXTURE_CUBE_MAP = 34067;

	private final boolean blend;

	private final int blendSrc;

	private final int blendDst;

	private final boolean alphaTest;

	private final int alphaFunc;

	private final boolean depthTest;

	private final int depthFunc;

	private final boolean depthMask;

	private final int colourMask;

	private final int activeUnit;

	private final int[] texture2d;

	private final int[] textureCube;

	private final boolean[] enabled2d;

	private final boolean[] enabledCube;

	private final int program;

	private HdGlState(boolean blend, int blendSrc, int blendDst, boolean alphaTest, int alphaFunc,
			boolean depthTest, int depthFunc, boolean depthMask, int colourMask, int activeUnit,
			int[] texture2d, int[] textureCube, boolean[] enabled2d, boolean[] enabledCube, int program) {
		this.blend = blend;
		this.blendSrc = blendSrc;
		this.blendDst = blendDst;
		this.alphaTest = alphaTest;
		this.alphaFunc = alphaFunc;
		this.depthTest = depthTest;
		this.depthFunc = depthFunc;
		this.depthMask = depthMask;
		this.colourMask = colourMask;
		this.activeUnit = activeUnit;
		this.texture2d = texture2d;
		this.textureCube = textureCube;
		this.enabled2d = enabled2d;
		this.enabledCube = enabledCube;
		this.program = program;
	}

	/**
	 * The state GL actually has now.
	 *
	 * <p>Colour and depth writes are queried as booleans because
	 * {@code glGetIntegerv} on those enums is {@code GL_INVALID_ENUM}; the
	 * alpha-test reference is not read because the cache never holds one and the
	 * pass that owns it (the shadow cut-out) saves and restores it itself.
	 *
	 * <p>Per-unit reads select each unit and put the active one back afterwards,
	 * so a snapshot is invisible to the toolkit that owns that state.
	 */
	public static HdGlState actual() {
		ByteBuffer bools = BufferUtils.createByteBuffer(4);
		int blendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
		int blendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);
		int colourMask = 1;
		GL11.glGetBooleanv(GL11.GL_COLOR_WRITEMASK, bools);
		for (int channel = 0; channel < 4; channel++) {
			colourMask &= bools.get(channel) != 0 ? 1 : 0;
		}
		bools.clear();
		GL11.glGetBooleanv(GL11.GL_DEPTH_WRITEMASK, bools);
		boolean depthMask = bools.get(0) != 0;

		int active = GL11.glGetInteger(GL13.GL_ACTIVE_TEXTURE) - GL13.GL_TEXTURE0;
		int[] texture2d = new int[UNITS];
		int[] textureCube = new int[UNITS];
		boolean[] enabled2d = new boolean[UNITS];
		boolean[] enabledCube = new boolean[UNITS];
		for (int unit = 0; unit < UNITS; unit++) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
			texture2d[unit] = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			textureCube[unit] = GL11.glGetInteger(TEXTURE_BINDING_CUBE_MAP);
			enabled2d[unit] = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
			enabledCube[unit] = GL11.glIsEnabled(TEXTURE_CUBE_MAP);
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + Math.max(0, active));

		return new HdGlState(
			GL11.glGetInteger(GL11.GL_BLEND) != 0, blendSrc, blendDst,
			GL11.glGetInteger(GL11.GL_ALPHA_TEST) != 0, GL11.glGetInteger(GL11.GL_ALPHA_TEST_FUNC),
			GL11.glGetInteger(GL11.GL_DEPTH_TEST) != 0, GL11.glGetInteger(GL11.GL_DEPTH_FUNC), depthMask,
			colourMask, active, texture2d, textureCube, enabled2d, enabledCube,
			GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM));
	}

	/**
	 * What the toolkit's caches claim, in the same fields.
	 *
	 * <p>The cache holds one {@code Class6} per unit, and a bind always enables
	 * that texture's target and disables the one it replaced, so the cache's
	 * enable claim is "the target of the bound texture, and nothing else".
	 *
	 * @param blendSrc   blend source factor the cache implies, or 0 when it
	 *                   claims blending is off
	 * @param blendDst   blend destination factor, or 0 when blending is off
	 * @param alphaTest  {@code aBoolean216}
	 * @param alphaFunc  the alpha-test function the toolkit last issued, or
	 *                   {@link #UNKNOWN} before it has issued one
	 * @param depthTest  {@code aBoolean203}
	 * @param depthFunc  the depth function the toolkit last issued, or
	 *                   {@link #UNKNOWN} before it has issued one
	 * @param depthMask  {@code aBoolean200 && depthBufferingEnabled}
	 * @param colourMask 1 when the cache claims all four channels on,
	 *                   {@link #UNKNOWN} when it has never asserted one
	 *                   ({@code aBoolean195} is a "has been set" flag, not a
	 *                   state of its own)
	 * @param activeUnit {@code anInt2301}
	 * @param texture2d  the 2D texture name the cache holds per unit, 0 for none
	 * @param textureCube the cube texture name the cache holds per unit, 0 for none
	 * @param program    the program an HD pass has bound, 0 for fixed function
	 *                   (which is what GL has before any pass binds)
	 */
	public static HdGlState cached(int blendSrc, int blendDst, boolean alphaTest, int alphaFunc,
			boolean depthTest, int depthFunc, boolean depthMask, int colourMask, int activeUnit,
			int[] texture2d, int[] textureCube, int program) {
		int[] twoD = texture2d == null ? new int[UNITS] : texture2d.clone();
		int[] cubes = textureCube == null ? new int[UNITS] : textureCube.clone();
		boolean[] enabled2d = new boolean[UNITS];
		boolean[] enabledCube = new boolean[UNITS];
		for (int unit = 0; unit < UNITS; unit++) {
			enabled2d[unit] = twoD[unit] != 0;
			enabledCube[unit] = cubes[unit] != 0;
		}
		return new HdGlState(blendSrc != 0 || blendDst != 0, blendSrc, blendDst, alphaTest, alphaFunc,
			depthTest, depthFunc, depthMask, colourMask, activeUnit, twoD, cubes, enabled2d, enabledCube,
			program);
	}

	/**
	 * Every field where this snapshot and {@code other} disagree, as
	 * {@code "field: mine != theirs"} lines. Fields either side has no value for
	 * are skipped.
	 */
	public List<String> differences(HdGlState other) {
		List<String> lines = new ArrayList<String>();
		if (other == null) {
			lines.add("state: no snapshot");
			return lines;
		}
		compare(lines, "blend", this.blend, other.blend);
		if (this.blend && other.blend) {
			compare(lines, "blendSrc", this.blendSrc, other.blendSrc);
			compare(lines, "blendDst", this.blendDst, other.blendDst);
		}
		compare(lines, "alphaTest", this.alphaTest, other.alphaTest);
		compare(lines, "alphaFunc", this.alphaFunc, other.alphaFunc);
		compare(lines, "depthTest", this.depthTest, other.depthTest);
		compare(lines, "depthFunc", this.depthFunc, other.depthFunc);
		compare(lines, "depthMask", this.depthMask, other.depthMask);
		compare(lines, "colourMask", this.colourMask, other.colourMask);
		compare(lines, "activeUnit", this.activeUnit, other.activeUnit);
		for (int unit = 0; unit < UNITS; unit++) {
			compare(lines, "texture2dEnabled[" + unit + "]", this.enabled2d[unit], other.enabled2d[unit]);
			compare(lines, "textureCubeEnabled[" + unit + "]", this.enabledCube[unit], other.enabledCube[unit]);
			// Names are compared whether or not the target is enabled. A residue
			// left bound on a disabled target is still a cache that does not
			// describe its unit, and one enable call away from being sampled; the
			// point of the contract is that the toolkit knows what is on its units.
			compare(lines, "texture2d[" + unit + "]", this.texture2d[unit], other.texture2d[unit]);
			compare(lines, "textureCube[" + unit + "]", this.textureCube[unit], other.textureCube[unit]);
		}
		compare(lines, "program", this.program, other.program);
		return lines;
	}

	private static void compare(List<String> lines, String field, int mine, int theirs) {
		if (mine == UNKNOWN || theirs == UNKNOWN) {
			return;
		}
		if (mine != theirs) {
			lines.add(field + ": " + mine + " != " + theirs);
		}
	}

	private static void compare(List<String> lines, String field, boolean mine, boolean theirs) {
		if (mine != theirs) {
			lines.add(field + ": " + mine + " != " + theirs);
		}
	}

	/** True when every field both snapshots know agrees. */
	public boolean agreesWith(HdGlState other) {
		return differences(other).isEmpty();
	}

	/** One line for a frame log: {@code blend=on(770/771) ...}. */
	@Override
	public String toString() {
		StringBuilder textures = new StringBuilder();
		for (int unit = 0; unit < UNITS; unit++) {
			if (unit > 0) {
				textures.append(',');
			}
			textures.append(this.texture2d[unit]);
			if (this.enabled2d[unit]) {
				textures.append('*');
			}
			if (this.textureCube[unit] != UNKNOWN && this.textureCube[unit] != 0) {
				textures.append('c').append(this.textureCube[unit]);
				if (this.enabledCube[unit]) {
					textures.append('*');
				}
			}
		}
		return "blend=" + (this.blend ? "on(" + this.blendSrc + "/" + this.blendDst + ")" : "off")
			+ " alphaTest=" + (this.alphaTest ? "on(" + name(this.alphaFunc) + ")" : "off")
			+ " depth=" + (this.depthTest ? "on(" + name(this.depthFunc) + ")" : "off")
			+ " depthMask=" + (this.depthMask ? "on" : "off")
			+ " colourMask=" + (this.colourMask == UNKNOWN ? "?" : this.colourMask != 0 ? "all" : "partial")
			+ " unit=" + (this.activeUnit == UNKNOWN ? "?" : Integer.toString(this.activeUnit))
			+ " tex=[" + textures + "]"
			+ " program=" + this.program;
	}

	private static String name(int func) {
		if (func == UNKNOWN) {
			return "?";
		}
		if (func == GL11.GL_NEVER) {
			return "never";
		}
		if (func == GL11.GL_LESS) {
			return "less";
		}
		if (func == GL11.GL_EQUAL) {
			return "equal";
		}
		if (func == GL11.GL_LEQUAL) {
			return "lequal";
		}
		if (func == GL11.GL_GREATER) {
			return "greater";
		}
		if (func == GL11.GL_NOTEQUAL) {
			return "notequal";
		}
		if (func == GL11.GL_GEQUAL) {
			return "gequal";
		}
		if (func == GL11.GL_ALWAYS) {
			return "always";
		}
		return Integer.toString(func);
	}
}
