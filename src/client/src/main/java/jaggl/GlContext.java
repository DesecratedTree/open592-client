package jaggl;

import java.awt.AWTError;
import java.awt.AWTException;
import java.awt.Canvas;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.awt.AWTGLCanvas;
import org.lwjgl.opengl.awt.GLData;
import org.lwjgl.opengl.awt.PlatformGLCanvas;
import org.lwjgl.opengl.awt.PlatformLinuxGLCanvas;
import org.lwjgl.opengl.awt.PlatformMacOSXGLCanvas;
import org.lwjgl.opengl.awt.PlatformWin32GLCanvas;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.Platform;

/**
 * Not part of the original client: the GL context behind an {@link OpenGL}, on LWJGL.
 *
 * <p>libjaggl made its context on the canvas's native window; lwjgl3-awt does the same through
 * JAWT, with GLX on Linux, WGL on Windows and an NSOpenGLView (a legacy 2.1 context) on macOS.</p>
 *
 * <p>One context draws to every surface: the canvas given to {@link #create} and each canvas given
 * to {@link #prepareSurface}. GLX and WGL can make a context current on any window with a compatible
 * pixel format, as libjaggl did, so a further surface only borrows its canvas's drawable. macOS ties
 * a context to its view, so there a further surface has its own context, sharing objects with the
 * first.</p>
 *
 * <p>The JAWT drawing surface is locked only around makeCurrent and swapBuffers. On X11 the lock is
 * AWT's global lock, so holding it for a frame would stall the event thread; lwjgl3-awt requires it
 * to be held while a context is made current (Linux) and while buffers swap (Windows).</p>
 */
final class GlContext {

	/** One canvas the context can draw to, with the handle {@link OpenGL} hands out for it. */
	static final class Surface {
		final Canvas canvas;
		final PlatformGLCanvas platform;
		final long context;
		final boolean ownsContext;

		Surface(Canvas canvas, PlatformGLCanvas platform, long context, boolean ownsContext) {
			this.canvas = canvas;
			this.platform = platform;
			this.context = context;
			this.ownsContext = ownsContext;
		}
	}

	private final GLData data;
	private final Map<Long, Surface> surfaces = new HashMap<Long, Surface>();
	private long nextHandle = 1L;
	private Surface current;
	private Thread owner;
	private GLCapabilities capabilities;

	/** The context of the surface from {@link #create}: what jaggl's {@code peer} holds. */
	private final long context;

	/** The handle of the surface from {@link #create}, which {@link OpenGL#init} returns. */
	private final long mainHandle;

	private GlContext(GLData data, Surface main) {
		this.data = data;
		this.current = main;
		this.context = main.context;
		this.mainHandle = add(main);
	}

	long context() {
		return this.context;
	}

	long mainHandle() {
		return this.mainHandle;
	}

	private long add(Surface surface) {
		long handle = this.nextHandle++;
		this.surfaces.put(Long.valueOf(handle), surface);
		return handle;
	}

	private boolean bind(Surface surface) throws AWTException {
		surface.platform.lock();
		try {
			if (!surface.platform.makeCurrent(surface.context)) {
				return false;
			}
		} finally {
			surface.platform.unlock();
		}
		GL.setCapabilities(this.capabilities);
		this.current = surface;
		return true;
	}

	/** libjaggl's attachPeer: makes the context current on this thread, on the current surface. */
	synchronized boolean attach() {
		Thread thread = Thread.currentThread();
		if (this.owner != null && this.owner != thread) {
			return false;
		}
		try {
			if (!bind(this.current)) {
				return false;
			}
		} catch (AWTException exception) {
			return false;
		} catch (AWTError error) {
			return false;
		}
		this.owner = thread;
		return true;
	}

	/** libjaggl's detachPeer. */
	synchronized void detach() {
		if (this.owner != Thread.currentThread()) {
			return;
		}
		PlatformGLCanvas platform = this.current.platform;
		try {
			platform.lock();
			try {
				platform.makeCurrent(0L);
			} finally {
				platform.unlock();
			}
		} catch (AWTException exception) {
			// the canvas's window is already gone
		}
		GL.setCapabilities(null);
		this.owner = null;
	}

	synchronized boolean setSurface(long handle) {
		Surface surface = this.surfaces.get(Long.valueOf(handle));
		if (surface == null || this.owner != Thread.currentThread()) {
			return false;
		}
		try {
			return bind(surface);
		} catch (AWTException exception) {
			return false;
		} catch (AWTError error) {
			return false;
		}
	}

	synchronized long prepareSurface(Canvas canvas) {
		try {
			PlatformGLCanvas platform = newPlatformCanvas();
			if (Platform.get() == Platform.MACOSX) {
				// Not the drawable of another view: a context of its own, sharing with the first.
				GLData shared = copy(this.data);
				shared.shareContext = new ShareHandle(this.context);
				long surfaceContext = platform.create(canvas, shared, new GLData());
				return add(new Surface(canvas, platform, surfaceContext, true));
			}
			// create() sets the window's pixel format and finds its drawable; its own context is
			// not needed.
			long throwaway = platform.create(canvas, this.data, new GLData());
			platform.deleteContext(throwaway);
			return add(new Surface(canvas, platform, this.context, false));
		} catch (AWTException exception) {
			return -1L;
		} catch (AWTError error) {
			return -1L;
		}
	}

	synchronized void releaseSurface(long handle) {
		Surface surface = this.surfaces.get(Long.valueOf(handle));
		if (surface == null || handle == this.mainHandle) {
			return;
		}
		if (surface == this.current) {
			// Stay current, as OpenGL.detach() still records this thread as holding the context,
			// but on the canvas from init.
			Surface main = this.surfaces.get(Long.valueOf(this.mainHandle));
			if (this.owner == Thread.currentThread()) {
				boolean rebound = main != null;
				if (rebound) {
					try {
						rebound = bind(main);
					} catch (AWTException exception) {
						rebound = false;
					} catch (AWTError error) {
						rebound = false;
					}
				}
				if (!rebound) {
					detach();
				}
			}
			this.current = main == null ? surface : main;
		}
		this.surfaces.remove(Long.valueOf(handle));
		dispose(surface);
	}

	synchronized void swapBuffers() {
		PlatformGLCanvas platform = this.current.platform;
		try {
			platform.lock();
		} catch (AWTException exception) {
			return;
		}
		try {
			platform.swapBuffers();
		} finally {
			try {
				platform.unlock();
			} catch (AWTException exception) {
				// the canvas's window is already gone
			}
		}
	}

	/**
	 * The swap interval is chosen when the context is made ({@link #create}), as GLData carries it,
	 * and jaggl only ever set it to 0; lwjgl3-awt has no per-surface call to change it afterwards.
	 */
	synchronized void setSwapInterval(int interval) {
	}

	/**
	 * libjaggl's release. Only the thread holding the context (or none) can delete it: GL cannot
	 * delete a context current on another thread, so then it is left, with a message, rather than
	 * deleted under that thread.
	 */
	synchronized void release() {
		Thread holder = this.owner;
		if (holder != null && holder != Thread.currentThread()) {
			System.err.println("jaggl: the OpenGL context is still current on " + holder.getName() + "; not deleted");
			return;
		}
		detach();
		for (Surface surface : this.surfaces.values()) {
			if (surface.ownsContext) {
				dispose(surface);
			}
		}
		for (Surface surface : this.surfaces.values()) {
			if (!surface.ownsContext) {
				dispose(surface);
			}
		}
		this.surfaces.clear();
	}

	private void dispose(Surface surface) {
		try {
			if (surface.ownsContext) {
				surface.platform.deleteContext(surface.context);
			}
			surface.platform.dispose();
		} catch (RuntimeException exception) {
			// the canvas or its window is already gone
		}
	}

	/**
	 * Carries a context handle into {@link GLData#shareContext}, which takes an AWTGLCanvas.
	 */
	private static final class ShareHandle extends AWTGLCanvas {
		ShareHandle(long handle) {
			this.context = handle;
		}

		@Override
		public void initGL() {
		}

		@Override
		public void paintGL() {
		}
	}

	static {
		// LWJGL unpacks its natives into the shared temporary directory by default; keep them in the
		// home directory, as the game cache is.
		if (System.getProperty("org.lwjgl.system.SharedLibraryExtractPath") == null) {
			Configuration.SHARED_LIBRARY_EXTRACT_PATH.set(
				new File(System.getProperty("user.home"), ".lwjgl/open592").getPath());
		}
		// In a Wayland session LWJGL loads EGL for GL; the contexts here are GLX (see
		// newPlatformCanvas), so it must load libGL's GLX instead.
		if (System.getProperty("org.lwjgl.opengl.contextAPI") == null) {
			Configuration.OPENGL_CONTEXT_API.set("native");
		}
	}

	/**
	 * libjaggl's init: a context on {@code canvas} with the given pixel format. No version and no
	 * profile are asked for, which gives the newest compatibility context on GLX and WGL (the
	 * client needs immediate mode, the matrix stack and ARB programs) and the legacy 2.1 context on
	 * macOS.
	 */
	static GlContext create(Canvas canvas, int red, int green, int blue, int depth, int stencil, int samples)
			throws AWTException {
		GLData data = new GLData();
		data.redSize = red;
		data.greenSize = green;
		data.blueSize = blue;
		data.alphaSize = 0;
		data.depthSize = depth;
		data.stencilSize = stencil;
		data.doubleBuffer = true;
		if (samples > 0) {
			data.sampleBuffers = 1;
			data.samples = samples;
		}
		data.swapInterval = Integer.valueOf(0);
		PlatformGLCanvas platform = newPlatformCanvas();
		long context = platform.create(canvas, data, new GLData());
		Surface main = new Surface(canvas, platform, context, true);
		GlContext gl = new GlContext(data, main);
		try {
			platform.lock();
			try {
				if (!platform.makeCurrent(context)) {
					throw new AWTException("cannot make the context current");
				}
				gl.capabilities = GL.createCapabilities();
				platform.makeCurrent(0L);
			} finally {
				platform.unlock();
			}
		} catch (RuntimeException exception) {
			gl.dispose(main);
			throw exception;
		} catch (LinkageError error) {
			gl.dispose(main);
			throw error;
		} catch (AWTError error) {
			gl.dispose(main);
			throw error;
		} finally {
			GL.setCapabilities(null);
		}
		return gl;
	}

	private static PlatformGLCanvas newPlatformCanvas() {
		Platform platform = Platform.get();
		if (platform == Platform.WINDOWS) {
			return new PlatformWin32GLCanvas();
		}
		if (platform == Platform.MACOSX) {
			return new PlatformMacOSXGLCanvas();
		}
		// GLX, as libjaggl used, also under Wayland (through XWayland, where AWT's windows are);
		// lwjgl3-awt would pick EGL there.
		return new PlatformLinuxGLCanvas();
	}

	private static GLData copy(GLData from) {
		GLData to = new GLData();
		to.redSize = from.redSize;
		to.greenSize = from.greenSize;
		to.blueSize = from.blueSize;
		to.alphaSize = from.alphaSize;
		to.depthSize = from.depthSize;
		to.stencilSize = from.stencilSize;
		to.doubleBuffer = from.doubleBuffer;
		to.sampleBuffers = from.sampleBuffers;
		to.samples = from.samples;
		to.swapInterval = from.swapInterval;
		return to;
	}
}
