package com.jagex.game.runetek5.client.config;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("launcher properties grammar")
class LauncherPropertiesTest {

	@TempDir
	Path home;

	@Test
	@DisplayName("launcher-written values are literal, including Windows paths")
	void launcherWrittenValues_shouldStayLiteral() throws Exception {
		String text =
			"# open592 launcher - hand-editable, rewritten by the launcher GUI\n"
				+ "# 592HD preset: ingame (use the in-game choice), off, low, medium, high, ultra\n"
				+ "launcher.version=1\n"
				+ "client.jar=C:\\utils\\\u00fcn\u00efcode\\client-current.jar\n"
				+ "client.updateUrl=https://example.com/client.jar?q=a=b#frag\n"
				+ "client.md5=\n"
				+ "java.path=C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe\n"
				+ "memory.mb=1024\n"
				+ "hd.preset=high\n"
				+ "server.host=play.example.com\n"
				+ "server.port=5555\n"
				+ "rsa.modulus=0xDEADBEEFDEADBEEF\n"
				+ "rsa.exponent=3\n";

		Map<String, String> values = LauncherProperties.parse(text);

		assertAll(
			() -> assertEquals("C:\\utils\\\u00fcn\u00efcode\\client-current.jar",
				values.get("client.jar")),
			() -> assertEquals("https://example.com/client.jar?q=a=b#frag",
				values.get("client.updateUrl")),
			() -> assertEquals("", values.get("client.md5")),
			() -> assertEquals("C:\\Program Files\\Java\\jre1.8.0_491\\bin\\javaw.exe",
				values.get("java.path")),
			() -> assertEquals("high", values.get("hd.preset")),
			() -> assertEquals("play.example.com", values.get("server.host")),
			() -> assertEquals("5555", values.get("server.port"))
		);
	}

	@Test
	@DisplayName("a literal \\u in a path is not an escape and cannot poison the file")
	void literalUnicodeEscape_shouldNotThrow() {
		String text =
			"client.jar=C:\\users\\client-current.jar\n"
				+ "java.path=C:\\utils\\javaw.exe\n"
				+ "server.host=play.example.com\n"
				+ "server.port=5555\n"
				+ "hd.preset=high\n";

		Map<String, String> values = LauncherProperties.parse(text);

		assertAll(
			() -> assertEquals("C:\\users\\client-current.jar", values.get("client.jar")),
			() -> assertEquals("C:\\utils\\javaw.exe", values.get("java.path")),
			() -> assertEquals("play.example.com", values.get("server.host")),
			() -> assertEquals("high", values.get("hd.preset"))
		);
	}

	@Test
	@DisplayName("whitespace, comments, CRLF and both separators are understood")
	void grammar_shouldMatchTheLauncher() {
		String text =
			"  # a comment with = and \\u1234  \r\n"
				+ "\t! another comment\r\n"
				+ "  server.host :  play.example.com  \r\n"
				+ "server.port\t=\t5555\r\n"
				+ "client.md5=a=b:c#d\r\n"
				+ "client.jar=C:\\rs\\client.jar\r\n"
				+ "\r\n"
				+ "hd.preset=  hi gh  \r\n";

		Map<String, String> values = LauncherProperties.parse(text);

		assertAll(
			() -> assertEquals("play.example.com", values.get("server.host")),
			() -> assertEquals("5555", values.get("server.port")),
			() -> assertEquals("a=b:c#d", values.get("client.md5")),
			() -> assertEquals("C:\\rs\\client.jar", values.get("client.jar")),
			() -> assertEquals("hi gh", values.get("hd.preset")),
			() -> assertTrue(!values.containsKey("a comment with ="))
		);
	}

	@Test
	@DisplayName("the first equals wins, even when a colon comes earlier")
	void equals_shouldWinOverAnEarlierColon() {
		String text = "server.host:ignored=play.example.com\n";

		Map<String, String> values = LauncherProperties.parse(text);

		assertAll(
			() -> assertEquals("play.example.com", values.get("server.host:ignored")),
			() -> assertTrue(!values.containsKey("server.host"))
		);
	}

	@Test
	@DisplayName("leading and trailing Unicode whitespace is trimmed, the interior is not")
	void unicodeWhitespace_shouldTrimLikeTheLauncher() {
		String text = "server.host=\u00a0 play.example.com \u3000\nclient.md5= a b \n";

		Map<String, String> values = LauncherProperties.parse(text);

		assertAll(
			() -> assertEquals("play.example.com", values.get("server.host")),
			() -> assertEquals("a b", values.get("client.md5"))
		);
	}

	@Test
	@DisplayName("a later duplicate replaces an earlier value")
	void duplicates_shouldUseTheLastLine() {
		String text = "server.host=first.example.com\nserver.host=second.example.com\n";

		assertEquals("second.example.com", LauncherProperties.parse(text).get("server.host"));
	}

	@Test
	@DisplayName("valid UTF-8 is decoded literally")
	void unicodeValues_shouldRoundTrip() {
		Map<String, String> values = LauncherProperties.parse("server.host=na\u00efve.\u4f8b\u5b50\n");

		assertEquals("na\u00efve.\u4f8b\u5b50", values.get("server.host"));
	}

	@Test
	@DisplayName("a non-UTF-8 file is treated as absent, not replacement-decoded")
	void invalidUtf8_shouldReadAsEmpty() throws Exception {
		Path file = this.home.resolve("bad.properties");
		Files.write(file, new byte[] { 's', 'e', 'r', 'v', 'e', 'r', '.', 'h', 'o', 's', 't', '=',
			(byte) 0xC3, (byte) 0x28 });

		assertTrue(LauncherProperties.read(file).isEmpty());
	}

	@Test
	@DisplayName("a missing file is an empty map")
	void missingFile_shouldReadAsEmpty() {
		assertTrue(LauncherProperties.read(this.home.resolve("nothing.properties")).isEmpty());
	}

	@Test
	@DisplayName("the reader decodes UTF-8 files written by the launcher")
	void read_shouldDecodeAFile() throws Exception {
		Path file = this.home.resolve("launcher.properties");
		Files.write(file,
			"server.host=\u4f8b\u5b50.example\nserver.port=4444\n"
				.getBytes(StandardCharsets.UTF_8));

		Map<String, String> values = LauncherProperties.read(file);

		assertAll(
			() -> assertEquals("\u4f8b\u5b50.example", values.get("server.host")),
			() -> assertEquals("4444", values.get("server.port"))
		);
	}
}
