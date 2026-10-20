package netscape.javascript;

import java.applet.Applet;

/**
 * Minimal source stub for the browser JavaScript bridge ({@code netscape.javascript.JSObject}).
 *
 * <p>The client calls into the embedding page through this API. It shipped inside
 * the JDK (rt.jar/plugin.jar) up to Java 8 and was removed from later releases,
 * so the Gradle build (release 8 API surface via {@code ct.sym}, or any newer
 * JDK) cannot see it. This stub exposes exactly the surface the deobfuscated
 * sources use; it is never executed by the build.
 */
public final class JSObject {
	private JSObject() {
	}

	public static JSObject getWindow(Applet applet) {
		throw new UnsupportedOperationException();
	}

	public Object call(String methodName, Object[] args) {
		throw new UnsupportedOperationException();
	}

	public Object eval(String script) {
		throw new UnsupportedOperationException();
	}
}
