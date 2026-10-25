package jaggl;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.AWTError;
import java.awt.AWTException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

/**
 * A real GL context for tests, created exactly the way the client creates one.
 *
 * <p>{@link GlContext} is package-private because it is the context behind
 * {@link OpenGL}; this wrapper exists in the test tree so a rendering test can
 * borrow it instead of re-implementing JAWT, pixel-format selection and
 * capability loading. The frame is moved far off screen: the context needs a
 * realised native window, but nobody should see one.
 *
 * <p>All GL calls must happen on the thread that called {@link #makeCurrent()},
 * which is the same rule the client lives by. {@link #close()} releases the
 * context from that thread.
 *
 * <p>There is no property or environment switch: either {@link #open()} gives a
 * context and every proof runs, or a caller reports why it could not. A driver
 * that faults inside native GL code takes the worker down, which is a real
 * failure of that driver rather than something hidden behind a skip.
 */
public final class GlTestContext implements AutoCloseable {

	/**
	 * The one context this JVM's tests share.
	 *
	 * <p>Creating and destroying a JAWT context per test class crashed the
	 * NVIDIA driver at a later {@code glTexImage2D}: LWJGL's function table is
	 * per current context, so a class that opened, closed and then let the GL
	 * toolkit open its own context left calls bound to a released context. One
	 * context that lives until the JVM exits removes the churn entirely, and
	 * every test already creates the objects it needs on it.
	 */
	private static GlTestContext shared;

	/** No display, no GL: the reason a test should be reported as not run. */
	public static final class Unavailable extends Exception {

		private static final long serialVersionUID = 1L;

		Unavailable(String message) {
			super(message);
		}
	}

	private final Frame frame;

	private final GlContext context;

	private final String version;

	private final String vendor;

	private final String renderer;

	private final String glslVersion;

	private GlTestContext(Frame frame, GlContext context) {
		this.frame = frame;
		this.context = context;
		this.version = jaggl.OpenGL.glGetString(GL11.GL_VERSION);
		this.vendor = jaggl.OpenGL.glGetString(GL11.GL_VENDOR);
		this.renderer = jaggl.OpenGL.glGetString(GL11.GL_RENDERER);
		this.glslVersion = jaggl.OpenGL.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
	}

	/**
	 * Creates a context on an off-screen canvas and makes it current on the
	 * calling thread.
	 *
	 * @throws Unavailable when this machine/desktop cannot give one, with the
	 *         native or display reason in the message
	 */
	public static synchronized GlTestContext open() throws Unavailable {
		if (shared != null) {
			// A toolkit test may have made its own context current since; make
			// the shared one current again for this class.
			shared.context.attach();
			return shared;
		}
		if (GraphicsEnvironment.isHeadless()) {
			throw new Unavailable("java.awt.headless=true: JAWT needs a desktop session");
		}
		Frame frame = new Frame("592HD GL test");
		try {
			Canvas canvas = new Canvas();
			canvas.setPreferredSize(new Dimension(64, 64));
			frame.add(canvas);
			frame.pack();
			frame.setLocation(-4000, -4000);
			frame.setVisible(true);
			GlContext context = GlContext.create(canvas, 8, 8, 8, 24, 0, 0);
			if (!context.attach()) {
				context.release();
				throw new Unavailable("a context was created but could not be made current on this thread");
			}
			shared = new GlTestContext(frame, context);
			return shared;
		} catch (AWTException exception) {
			frame.dispose();
			throw new Unavailable("JAWT/WGL refused a context: " + exception.getMessage());
		} catch (AWTError error) {
			frame.dispose();
			throw new Unavailable("AWT is not available: " + error.getMessage());
		} catch (RuntimeException exception) {
			frame.dispose();
			throw new Unavailable("the driver refused a context: " + exception);
		} catch (LinkageError error) {
			frame.dispose();
			throw new Unavailable("the LWJGL/OpenGL natives did not load: " + error);
		}
	}

	public String version() {
		return this.version;
	}

	public String vendor() {
		return this.vendor;
	}

	public String renderer() {
		return this.renderer;
	}

	public String glslVersion() {
		return this.glslVersion;
	}

	/** True when the context exposes legacy GLSL, which 592HD's source needs. */
	public boolean supportsGlsl120() {
		return this.glslVersion != null && this.glslVersion.matches("^\\d+\\.\\d+.*")
			&& Double.parseDouble(this.glslVersion.split(" ")[0]) >= 1.2;
	}

	/**
	 * Keeps the shared context alive. See {@link #shared}: releasing it between
	 * test classes is what the driver crashed on, and a test context has no
	 * reason to outlive its usefulness any earlier than the JVM does. The
	 * window and context are reclaimed by process exit.
	 */
	@Override
	public void close() {
	}
}
