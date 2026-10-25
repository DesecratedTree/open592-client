package com.jagex.game.runetek5.client.config;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Reads {@code launcher.properties} in the launcher's literal dialect.
 *
 * <p>This is deliberately not {@link java.util.Properties#load}: the launcher
 * writes values verbatim, so a Windows path such as
 * <code>C:&#92;utils&#92;client-current.jar</code> keeps its backslashes and a
 * literal backslash-u never becomes an escape (or an exception). The contract
 * is:
 *
 * <ul>
 * <li>the file is UTF-8; bytes that are not valid UTF-8 make the whole file
 * unreadable, just as the launcher's {@code read_to_string} does;</li>
 * <li>lines end at {@code \n}; one trailing {@code \r} is ignored;</li>
 * <li>surrounding Unicode whitespace is trimmed from every line, key and
 * value; blank lines are skipped;</li>
 * <li>a line whose first non-space character is {@code #} or {@code !} is a
 * comment;</li>
 * <li>the key ends at the first {@code =}, or at the first {@code :} when the
 * line has no {@code =}; there are no line continuations;</li>
 * <li>everything after the separator is literal: backslashes, a literal
 * backslash-u, other colons, equals signs and hashes are ordinary characters,
 * so values can contain them. A line break cannot be part of a value;</li>
 * <li>a later duplicate key replaces an earlier one, and an empty value is
 * stored as empty (the caller decides whether that means "unset").</li>
 * </ul>
 *
 * <p>The launcher side of this contract lives in
 * {@code launcher/src-tauri/src/config.rs}; the two parsers are tested with
 * the same corpus and must stay in lockstep.
 */
final class LauncherProperties {

	private LauncherProperties() {
	}

	/** Reads {@code file}, returning an empty map when it is absent or unreadable. */
	static Map<String, String> read(Path file) {
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(file);
		} catch (IOException ignored) {
			return Collections.emptyMap();
		}
		String text;
		try {
			CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
				.onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT);
			text = decoder.decode(ByteBuffer.wrap(bytes)).toString();
		} catch (CharacterCodingException ignored) {
			// The launcher also treats a non-UTF-8 file as absent rather than
			// decoding a replacement character into a path or a host.
			return Collections.emptyMap();
		}
		return parse(text);
	}

	/** Parses already-decoded text; visible for the contract tests. */
	static Map<String, String> parse(String text) {
		Map<String, String> values = new HashMap<String, String>();
		int start = 0;
		while (start < text.length()) {
			int newline = text.indexOf('\n', start);
			int end = newline < 0 ? text.length() : newline;
			String line = text.substring(start, end);
			if (line.endsWith("\r")) {
				line = line.substring(0, line.length() - 1);
			}
			start = newline < 0 ? text.length() : newline + 1;

			String trimmed = trim(line);
			if (trimmed.isEmpty()) {
				continue;
			}
			char first = trimmed.charAt(0);
			if (first == '#' || first == '!') {
				continue;
			}
			int equals = trimmed.indexOf('=');
			int colon = trimmed.indexOf(':');
			int separator = equals >= 0 ? equals : colon;
			if (separator < 0) {
				continue;
			}
			values.put(
				trim(trimmed.substring(0, separator)),
				trim(trimmed.substring(separator + 1)));
		}
		return values;
	}

	/**
	 * The launcher uses Rust's {@code trim}, whose set is Unicode White_Space.
	 * Java's {@code String.trim} differs at the edges (it keeps a no-break
	 * space and strips some control characters), so spell the set out.
	 */
	private static String trim(String value) {
		int begin = 0;
		int end = value.length();
		while (begin < end && isWhitespace(value.charAt(begin))) {
			begin++;
		}
		while (end > begin && isWhitespace(value.charAt(end - 1))) {
			end--;
		}
		return value.substring(begin, end);
	}

	private static boolean isWhitespace(char value) {
		if (value >= '\u2000' && value <= '\u200A') {
			return true;
		}
		switch (value) {
		case '\t':
		case '\n':
		case '\u000B':
		case '\f':
		case '\r':
		case ' ':
		case '\u0085':
		case '\u00A0':
		case '\u1680':
		case '\u2028':
		case '\u2029':
		case '\u202F':
		case '\u205F':
		case '\u3000':
			return true;
		default:
			return false;
		}
	}
}
