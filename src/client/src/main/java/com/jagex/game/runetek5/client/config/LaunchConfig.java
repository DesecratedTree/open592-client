package com.jagex.game.runetek5.client.config;

import java.io.File;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;

import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class186;

/**
 * The settings the launcher hands to the client at startup.
 *
 * <p>One properties file, named by {@code -Dopen592.config}, supplies the
 * server address and the login key. A matching {@code open592.*} system
 * property overrides the file, which keeps a debug run and the tests independent
 * of the launcher; anything absent falls back to the stock constant.
 *
 * <p>Defaults are the client's own constants, so a launch with no launcher
 * config is bit-identical to the behaviour before this seam existed:
 * {@link #DEFAULT_HOST}, {@link #DEFAULT_PORT}, {@link Class186#aBigInteger2}
 * and {@link TextureOpColorGradient#aBigInteger1}. The file's {@code hd.preset}
 * key is the launcher's, not this seam's: the 592HD work on its own branch reads
 * it, so a build without that branch simply ignores it.
 *
 * <p>The file is the launcher's literal dialect, not {@code java.util.Properties}:
 * values are read verbatim and never unescaped, so a Windows path keeps its
 * backslashes and a literal backslash-u is just text. {@link LauncherProperties}
 * defines the grammar and mirrors the launcher's own parser; the client only
 * reads the four keys below. A file that cannot be decoded as UTF-8 is treated
 * as absent, which leaves every stock default in place.
 */
public final class LaunchConfig {

	/** The stock server host, the value {@code ClientLoader} shipped with. */
	public static final String DEFAULT_HOST = "15.204.91.129";

	/** The stock game port used by {@code client.maininit}'s GL branch. */
	public static final int DEFAULT_PORT = 43594;

	private static final String PROPERTY_CONFIG = "open592.config";
	private static final String PROPERTY_HOST = "open592.server.host";
	private static final String PROPERTY_PORT = "open592.server.port";
	private static final String PROPERTY_MODULUS = "open592.rsa.modulus";
	private static final String PROPERTY_EXPONENT = "open592.rsa.exponent";

	private static final String KEY_HOST = "server.host";
	private static final String KEY_PORT = "server.port";
	private static final String KEY_MODULUS = "rsa.modulus";
	private static final String KEY_EXPONENT = "rsa.exponent";

	private static volatile Snapshot snapshot;

	private LaunchConfig() {
	}

	/** The server host, without any port. */
	public static String serverHost() {
		return snapshot().host;
	}

	/** The game port, 1-65535. */
	public static int gamePort() {
		return snapshot().port;
	}

	/** The login modulus; the stock key unless the file overrides it. */
	public static BigInteger rsaModulus() {
		return snapshot().modulus;
	}

	/** The login exponent; 65537 unless the file overrides it. */
	public static BigInteger rsaExponent() {
		return snapshot().exponent;
	}

	/** Test hook: drops the cached file so the next read sees new properties. */
	public static void resetForTest() {
		snapshot = null;
	}

	private static Snapshot snapshot() {
		Snapshot value = snapshot;
		if (value == null) {
			synchronized (LaunchConfig.class) {
				value = snapshot;
				if (value == null) {
					value = read();
					snapshot = value;
				}
			}
		}
		return value;
	}

	private static Snapshot read() {
		Map<String, String> properties = loadProperties();

		String address = setting(KEY_HOST, PROPERTY_HOST, properties);
		String host = DEFAULT_HOST;
		Integer addressPort = null;
		if (address != null && !address.isEmpty()) {
			String[] split = splitAddress(address);
			host = split[0].isEmpty() ? DEFAULT_HOST : split[0];
			addressPort = parsePort(split[1]);
		}

		Integer storedPort = parsePort(setting(KEY_PORT, PROPERTY_PORT, properties));
		int port = storedPort != null ? storedPort.intValue()
			: addressPort != null ? addressPort.intValue() : DEFAULT_PORT;

		BigInteger modulus = parseKey(setting(KEY_MODULUS, PROPERTY_MODULUS, properties),
			Class186.aBigInteger2);
		BigInteger exponent = parseKey(setting(KEY_EXPONENT, PROPERTY_EXPONENT, properties),
			TextureOpColorGradient.aBigInteger1);

		return new Snapshot(host, port, modulus, exponent);
	}

	private static Map<String, String> loadProperties() {
		String path = System.getProperty(PROPERTY_CONFIG);
		if (path == null || path.trim().isEmpty()) {
			return Collections.emptyMap();
		}
		File file = new File(path.trim());
		if (!file.isFile()) {
			return Collections.emptyMap();
		}
		// A missing, unreadable, non-UTF-8 or sandboxed file all mean the
		// stock defaults; LauncherProperties performs the strict decode.
		return LauncherProperties.read(file.toPath());
	}

	/**
	 * A system property wins over the file, and an empty value counts as
	 * absent so a blank line cannot override a real setting.
	 */
	private static String setting(String key, String systemProperty, Map<String, String> properties) {
		String override = System.getProperty(systemProperty);
		if (override != null && !override.trim().isEmpty()) {
			return override.trim();
		}
		String stored = properties.get(key);
		if (stored == null) {
			return null;
		}
		String trimmed = stored.trim();
		return trimmed.isEmpty() ? null : trimmed;
	}

	/**
	 * Splits {@code host}, {@code host:port} or {@code [v6]:port}. A bare
	 * IPv6 address is left whole because its colons are not a port.
	 */
	static String[] splitAddress(String address) {
		String trimmed = address.trim();
		if (trimmed.startsWith("[")) {
			int close = trimmed.indexOf(']');
			if (close > 1) {
				String host = trimmed.substring(1, close);
				String tail = trimmed.substring(close + 1);
				String port = tail.startsWith(":") ? tail.substring(1) : "";
				return new String[] { host, port };
			}
		}
		int colon = trimmed.lastIndexOf(':');
		if (colon > 0 && trimmed.indexOf(':') == colon) {
			return new String[] { trimmed.substring(0, colon), trimmed.substring(colon + 1) };
		}
		return new String[] { trimmed, "" };
	}

	private static Integer parsePort(String value) {
		if (value == null) {
			return null;
		}
		try {
			int port = Integer.parseInt(value.trim());
			return port >= 1 && port <= 65535 ? Integer.valueOf(port) : null;
		} catch (NumberFormatException ex) {
			return null;
		}
	}

	/** Decimal, or hex when prefixed {@code 0x}; anything else keeps the default. */
	private static BigInteger parseKey(String value, BigInteger fallback) {
		if (value == null) {
			return fallback;
		}
		String trimmed = value.trim();
		try {
			BigInteger parsed;
			if (trimmed.startsWith("0x") || trimmed.startsWith("0X")) {
				parsed = new BigInteger(trimmed.substring(2), 16);
			} else {
				parsed = new BigInteger(trimmed, 10);
			}
			return parsed.signum() > 0 ? parsed : fallback;
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	private static final class Snapshot {

		final String host;

		final int port;

		final BigInteger modulus;

		final BigInteger exponent;

		Snapshot(String host, int port, BigInteger modulus, BigInteger exponent) {
			this.host = host;
			this.port = port;
			this.modulus = modulus;
			this.exponent = exponent;
		}
	}
}
