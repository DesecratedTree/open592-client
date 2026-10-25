package com.jagex.game.runetek5.client.config;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.jagex.game.runetek5.config.material.texture.operation.TextureOpColorGradient;
import com.jagex.game.runetek5.deob.Class186;

@DisplayName("launcher properties seam")
class LaunchConfigTest {

	private static final String[] PROPERTIES = {
		"open592.config",
		"open592.server.host",
		"open592.server.port",
		"open592.rsa.modulus",
		"open592.rsa.exponent",
	};

	@TempDir
	Path home;

	private final Map<String, String> previous = new HashMap<String, String>();

	@BeforeEach
	void clearProperties() {
		for (String key : PROPERTIES) {
			this.previous.put(key, System.getProperty(key));
			System.clearProperty(key);
		}
		LaunchConfig.resetForTest();
	}

	@AfterEach
	void restoreProperties() {
		for (String key : PROPERTIES) {
			String value = this.previous.get(key);
			if (value == null) {
				System.clearProperty(key);
			} else {
				System.setProperty(key, value);
			}
		}
		LaunchConfig.resetForTest();
	}

	private void writeConfig(String text) throws Exception {
		Path file = this.home.resolve("launcher.properties");
		Files.write(file, text.getBytes(StandardCharsets.UTF_8));
		System.setProperty("open592.config", file.toString());
		LaunchConfig.resetForTest();
	}

	@Test
	@DisplayName("without a launcher the client keeps its shipped values")
	void defaults_shouldMatchTheClient() {
		LaunchConfig.resetForTest();

		assertAll(
			() -> assertEquals("15.204.91.129", LaunchConfig.serverHost()),
			() -> assertEquals(43594, LaunchConfig.gamePort()),
			() -> assertEquals(Class186.aBigInteger2, LaunchConfig.rsaModulus()),
			() -> assertEquals(TextureOpColorGradient.aBigInteger1, LaunchConfig.rsaExponent()),
			() -> assertEquals("65537", LaunchConfig.rsaExponent().toString())
		);
	}

	@Test
	@DisplayName("the properties file supplies the address and key")
	void file_shouldSupplyEveryValue() throws Exception {
		writeConfig(
			"server.host=play.example.com\n"
				+ "server.port=5555\n"
				+ "rsa.modulus=0x0123456789abcdef0123456789abcdef\n"
				+ "rsa.exponent=3\n"
				+ "hd.preset=high\n");

		assertAll(
			() -> assertEquals("play.example.com", LaunchConfig.serverHost()),
			() -> assertEquals(5555, LaunchConfig.gamePort()),
			() -> assertEquals(new BigInteger("0123456789abcdef0123456789abcdef", 16),
				LaunchConfig.rsaModulus()),
			() -> assertEquals(BigInteger.valueOf(3L), LaunchConfig.rsaExponent())
		);
	}

	@Test
	@DisplayName("a system property overrides the file")
	void systemProperty_shouldWinOverTheFile() throws Exception {
		writeConfig("server.host=file.example.com\nserver.port=1111\n");
		System.setProperty("open592.server.host", "prop.example.com");
		System.setProperty("open592.server.port", "2222");
		LaunchConfig.resetForTest();

		assertAll(
			() -> assertEquals("prop.example.com", LaunchConfig.serverHost()),
			() -> assertEquals(2222, LaunchConfig.gamePort())
		);
	}

	@Test
	@DisplayName("a host:port pair splits, and a separate port still wins")
	void address_shouldSplitIntoHostAndPort() throws Exception {
		writeConfig("server.host=localhost:5000\n");

		assertAll(
			() -> assertEquals("localhost", LaunchConfig.serverHost()),
			() -> assertEquals(5000, LaunchConfig.gamePort())
		);

		writeConfig("server.host=localhost:5000\nserver.port=7000\n");
		assertEquals(7000, LaunchConfig.gamePort());

		writeConfig("server.host=[::1]:6000\n");
		assertAll(
			() -> assertEquals("::1", LaunchConfig.serverHost()),
			() -> assertEquals(6000, LaunchConfig.gamePort())
		);
	}

	@Test
	@DisplayName("a bare IPv6 address is not mistaken for a port")
	void address_shouldKeepABareIpv6Host() throws Exception {
		writeConfig("server.host=::1\n");

		assertAll(
			() -> assertEquals("::1", LaunchConfig.serverHost()),
			() -> assertEquals(43594, LaunchConfig.gamePort())
		);
	}

	@Test
	@DisplayName("a malformed or out-of-range value falls back to the shipped one")
	void invalidValues_shouldFallBack() throws Exception {
		writeConfig(
			"server.port=99999\n"
				+ "rsa.modulus=not-a-number\n"
				+ "rsa.exponent=-5\n"
				+ "hd.preset=impossible\n");

		assertAll(
			() -> assertEquals(43594, LaunchConfig.gamePort()),
			() -> assertEquals(Class186.aBigInteger2, LaunchConfig.rsaModulus()),
			() -> assertEquals(TextureOpColorGradient.aBigInteger1, LaunchConfig.rsaExponent())
		);
	}

	@Test
	@DisplayName("a missing file leaves every shipped value alone")
	void missingFile_shouldKeepDefaults() {
		System.setProperty("open592.config", this.home.resolve("nothing-here.properties").toString());
		LaunchConfig.resetForTest();

		assertAll(
			() -> assertEquals("15.204.91.129", LaunchConfig.serverHost()),
			() -> assertEquals(43594, LaunchConfig.gamePort())
		);
	}

	@Test
	@DisplayName("a launcher-written file is read literally, including its path fields")
	void launcherFile_shouldBeReadLiterally() throws Exception {
		writeConfig(
			"# open592 launcher - hand-editable, rewritten by the launcher GUI\n"
				+ "# 592HD preset: ingame (use the in-game choice), off, low, medium, high, ultra\n"
				+ "launcher.version=1\n"
				+ "client.jar=C:\\utils\\\u00fcn\u00efcode\\client-current.jar\n"
				+ "client.updateUrl=https://example.com/client.jar?q=a=b#frag\n"
				+ "client.md5=\n"
				+ "java.path=C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe\n"
				+ "memory.mb=1024\n"
				+ "hd.preset=high\n"
				+ "server.host=127.0.0.1\n"
				+ "server.port=5555\n"
				+ "rsa.modulus=0x0123456789abcdef0123456789abcdef\n"
				+ "rsa.exponent=3\n");

		assertAll(
			() -> assertEquals("127.0.0.1", LaunchConfig.serverHost()),
			() -> assertEquals(5555, LaunchConfig.gamePort()),
			() -> assertEquals(new BigInteger("0123456789abcdef0123456789abcdef", 16),
				LaunchConfig.rsaModulus()),
			() -> assertEquals(BigInteger.valueOf(3L), LaunchConfig.rsaExponent())
		);
	}

	@Test
	@DisplayName("empty values fall back without erasing the rest of the file")
	void emptyValues_shouldFallBack() throws Exception {
		writeConfig(
			"server.host=\n"
				+ "server.port=\n"
				+ "rsa.modulus=\n"
				+ "rsa.exponent=\n"
				+ "hd.preset=\n");

		assertAll(
			() -> assertEquals("15.204.91.129", LaunchConfig.serverHost()),
			() -> assertEquals(43594, LaunchConfig.gamePort()),
			() -> assertEquals(Class186.aBigInteger2, LaunchConfig.rsaModulus()),
			() -> assertEquals(TextureOpColorGradient.aBigInteger1, LaunchConfig.rsaExponent())
		);
	}

	@Test
	@DisplayName("a file that is not UTF-8 falls back instead of decoding replacement characters")
	void invalidUtf8_shouldFallBack() throws Exception {
		Path file = this.home.resolve("launcher.properties");
		Files.write(file, new byte[] { 's', 'e', 'r', 'v', 'e', 'r', '.', 'h', 'o', 's', 't', '=',
			(byte) 0xC3, (byte) 0x28 });
		System.setProperty("open592.config", file.toString());
		LaunchConfig.resetForTest();

		assertEquals("15.204.91.129", LaunchConfig.serverHost());
	}

}
