package com.jagex.game.runetek5.client.hd.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Records every call the shader pipeline makes, and can simulate driver
 * failures.
 *
 * <p>This is how the pipeline is verified without a GL context: the call order
 * (bind, uniform group, unbind), the handles handed out and deleted, and the
 * "nothing happens when 592HD is off" claims are all observable here.
 */
public final class FakeShaderApi implements HdShaderApi {

	/** Every call, as {@code name:arg[,arg]}. */
	public final List<String> calls = new ArrayList<String>();

	/** Uniform uploads, as {@code name=value}; anonymous locations show as {@code -1}. */
	public final List<String> uploads = new ArrayList<String>();

	private final Map<Long, Integer> shaderTypes = new LinkedHashMap<Long, Integer>();

	private final Map<Long, String> shaderSources = new HashMap<Long, String>();

	private final Set<Long> compilesOk = new HashSet<Long>();

	private final Map<Long, String> shaderLogs = new HashMap<Long, String>();

	private final Set<Long> linksOk = new HashSet<Long>();

	private final Map<Long, String> programLogs = new HashMap<Long, String>();

	private final Map<Integer, String> uniformNames = new HashMap<Integer, String>();

	private final Set<Long> live = new HashSet<Long>();

	private final List<String> deleted = new ArrayList<String>();

	private long nextHandle = 100L;

	private int nextUniformLocation = 10;

	/** Per-type compile outcome; default true. */
	public boolean failVertexCompile;

	public boolean failFragmentCompile;

	public boolean failLink;

	public boolean failCreation;

	public String vertexLog = "vertex boom";

	public String fragmentLog = "fragment boom";

	public String linkLog = "link boom";

	/** Registers a uniform name with a fresh location so uploads can be named. */
	public int declareUniform(String name) {
		int location = this.nextUniformLocation++;
		this.uniformNames.put(Integer.valueOf(location), name);
		return location;
	}

	public int calls(String prefix) {
		int count = 0;
		for (String call : this.calls) {
			if (call.startsWith(prefix)) {
				count++;
			}
		}
		return count;
	}

	public List<String> callsStartingWith(String prefix) {
		List<String> matches = new ArrayList<String>();
		for (String call : this.calls) {
			if (call.startsWith(prefix)) {
				matches.add(call);
			}
		}
		return matches;
	}

	public String sourceOf(long shader) {
		return this.shaderSources.get(Long.valueOf(shader));
	}

	/** True when any shader object was handed this exact source. */
	public boolean uploadedSource(String source) {
		return this.shaderSources.containsValue(source);
	}

	/** Handles created and not yet deleted; a leak leaves entries here. */
	public Set<Long> liveHandles() {
		return new HashSet<Long>(this.live);
	}

	public List<String> deleted() {
		return new ArrayList<String>(this.deleted);
	}

	@Override
	public long createShader(int type) {
		this.calls.add("createShader:" + type);
		if (this.failCreation) {
			return 0L;
		}
		long handle = this.nextHandle++;
		this.shaderTypes.put(Long.valueOf(handle), Integer.valueOf(type));
		this.live.add(Long.valueOf(handle));
		return handle;
	}

	@Override
	public void shaderSource(long shader, String source) {
		this.calls.add("shaderSource:" + shader);
		this.shaderSources.put(Long.valueOf(shader), source);
	}

	@Override
	public void compileShader(long shader) {
		this.calls.add("compileShader:" + shader);
		Integer type = this.shaderTypes.get(Long.valueOf(shader));
		boolean vertex = type != null && type.intValue() == VERTEX_SHADER;
		boolean failed = vertex ? this.failVertexCompile : this.failFragmentCompile;
		if (!failed) {
			this.compilesOk.add(Long.valueOf(shader));
		} else {
			this.shaderLogs.put(Long.valueOf(shader), vertex ? this.vertexLog : this.fragmentLog);
		}
	}

	@Override
	public boolean compileSucceeded(long shader) {
		this.calls.add("compileSucceeded:" + shader);
		return this.compilesOk.contains(Long.valueOf(shader));
	}

	@Override
	public String shaderInfoLog(long shader) {
		this.calls.add("shaderInfoLog:" + shader);
		String log = this.shaderLogs.get(Long.valueOf(shader));
		return log == null ? "" : log;
	}

	@Override
	public long createProgram() {
		this.calls.add("createProgram");
		if (this.failCreation) {
			return 0L;
		}
		long handle = this.nextHandle++;
		this.live.add(Long.valueOf(handle));
		return handle;
	}

	@Override
	public void attach(long program, long shader) {
		this.calls.add("attach:" + program + "," + shader);
	}

	@Override
	public void link(long program) {
		this.calls.add("link:" + program);
		if (this.failLink) {
			this.programLogs.put(Long.valueOf(program), this.linkLog);
		} else {
			this.linksOk.add(Long.valueOf(program));
		}
	}

	@Override
	public boolean linkSucceeded(long program) {
		this.calls.add("linkSucceeded:" + program);
		return this.linksOk.contains(Long.valueOf(program));
	}

	@Override
	public String programInfoLog(long program) {
		this.calls.add("programInfoLog:" + program);
		String log = this.programLogs.get(Long.valueOf(program));
		return log == null ? "" : log;
	}

	@Override
	public void detach(long program, long shader) {
		this.calls.add("detach:" + program + "," + shader);
	}

	@Override
	public void delete(long handle) {
		this.calls.add("delete:" + handle);
		if (handle == 0L) {
			return;
		}
		this.deleted.add(Long.toString(handle));
		this.live.remove(Long.valueOf(handle));
	}

	@Override
	public void use(long program) {
		this.calls.add("use:" + program);
	}

	@Override
	public int uniformLocation(long program, String name) {
		this.calls.add("uniformLocation:" + name);
		for (Map.Entry<Integer, String> entry : this.uniformNames.entrySet()) {
			if (entry.getValue().equals(name)) {
				return entry.getKey().intValue();
			}
		}
		return -1;
	}

	@Override
	public void uniform1i(int location, int value) {
		String name = this.uniformNames.get(Integer.valueOf(location));
		this.calls.add("uniform1i:" + location + "," + value);
		this.uploads.add((name == null ? Integer.toString(location) : name) + "=" + value);
	}

	@Override
	public void uniform1f(int location, float value) {
		String name = this.uniformNames.get(Integer.valueOf(location));
		this.calls.add("uniform1f:" + location + "," + value);
		this.uploads.add((name == null ? Integer.toString(location) : name) + "=" + value);
	}

	@Override
	public void uniform2f(int location, float x, float y) {
		String name = this.uniformNames.get(Integer.valueOf(location));
		this.calls.add("uniform2f:" + location + "," + x + "," + y);
		this.uploads.add((name == null ? Integer.toString(location) : name) + "=" + x + "," + y);
	}

	@Override
	public void uniform3f(int location, float x, float y, float z) {
		String name = this.uniformNames.get(Integer.valueOf(location));
		this.calls.add("uniform3f:" + location + "," + x + "," + y + "," + z);
		this.uploads.add((name == null ? Integer.toString(location) : name) + "=" + x + "," + y + "," + z);
	}

	@Override
	public void uniform4f(int location, float x, float y, float z, float w) {
		String name = this.uniformNames.get(Integer.valueOf(location));
		this.calls.add("uniform4f:" + location + "," + x + "," + y + "," + z + "," + w);
		this.uploads.add((name == null ? Integer.toString(location) : name) + "=" + x + "," + y + "," + z + "," + w);
	}
}
