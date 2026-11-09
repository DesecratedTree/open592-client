package com.jagex.game.runetek5.client.hd.content;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A small, strict JSON reader for 634HD's content files.
 *
 * <p>The client has no JSON dependency and the content files are plain data:
 * objects, arrays, strings, numbers, booleans and null. This reader produces
 * {@link LinkedHashMap}, {@link ArrayList}, {@link String}, {@link Double} and
 * {@link Boolean}, so file order is preserved and the callers' accessors stay
 * simple.
 *
 * <p>It is deliberately strict: trailing content, an unterminated string, a
 * malformed number or a document nested deeper than {@link #MAX_DEPTH} is an
 * error. A content file with one bad entry is handled one level up - the entry
 * is skipped and named - rather than by silently accepting a half-parsed
 * document, which is what would turn a typo into a renderer reading nonsense.
 */
public final class HdJson {

	/** Deepest object/array nesting accepted; content files nest five or six levels. */
	public static final int MAX_DEPTH = 64;

	private final String text;

	private int at;

	private HdJson(String text) {
		this.text = text;
	}

	/** Parses one complete JSON document. */
	public static Object parse(String text) {
		if (text == null) {
			throw new ParseException("no document", 0);
		}
		HdJson json = new HdJson(text);
		// A UTF-8 BOM is common in editors on this platform and is not content.
		if (json.at < text.length() && text.charAt(json.at) == '\uFEFF') {
			json.at++;
		}
		json.skipWhitespace();
		Object value = json.value(0);
		json.skipWhitespace();
		if (json.at != text.length()) {
			throw json.error("trailing content");
		}
		return value;
	}

	/** The object value, or null when {@code value} is not an object. */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> object(Object value) {
		return value instanceof Map ? (Map<String, Object>) value : null;
	}

	/** The array value, or null when {@code value} is not an array. */
	@SuppressWarnings("unchecked")
	public static List<Object> array(Object value) {
		return value instanceof List ? (List<Object>) value : null;
	}

	/** The string value, or null. */
	public static String string(Object value) {
		return value instanceof String ? (String) value : null;
	}

	/** The numeric value, or null. */
	public static Double number(Object value) {
		return value instanceof Number ? Double.valueOf(((Number) value).doubleValue()) : null;
	}

	/** The boolean value, or null. */
	public static Boolean bool(Object value) {
		return value instanceof Boolean ? (Boolean) value : null;
	}

	/** An integral value, or {@code fallback} when absent or not a whole number. */
	public static int integer(Object value, int fallback) {
		Double number = number(value);
		if (number == null) {
			return fallback;
		}
		double raw = number.doubleValue();
		if (raw != Math.floor(raw) || raw < Integer.MIN_VALUE || raw > Integer.MAX_VALUE) {
			return fallback;
		}
		return (int) raw;
	}

	/** A decimal value, or {@code fallback} when absent. */
	public static float decimal(Object value, float fallback) {
		Double number = number(value);
		return number == null ? fallback : number.floatValue();
	}

	/**
	 * A {@code #rrggbb} colour as 0xRRGGBB, or {@code fallback} when absent or
	 * malformed. Alpha is not part of the format: these files describe light,
	 * not blending.
	 */
	public static int colour(Object value, int fallback) {
		String text = string(value);
		if (text == null || text.length() != 7 || text.charAt(0) != '#') {
			return fallback;
		}
		int rgb = 0;
		for (int i = 1; i < 7; i++) {
			int digit = Character.digit(text.charAt(i), 16);
			if (digit < 0) {
				return fallback;
			}
			rgb = rgb << 4 | digit;
		}
		return rgb;
	}

	private Object value(int depth) {
		if (depth > MAX_DEPTH) {
			throw error("nested too deeply");
		}
		skipWhitespace();
		if (this.at >= this.text.length()) {
			throw error("unexpected end of document");
		}
		char c = this.text.charAt(this.at);
		switch (c) {
		case '{':
			return objectValue(depth);
		case '[':
			return arrayValue(depth);
		case '"':
			return stringValue();
		case 't':
			expectLiteral("true");
			return Boolean.TRUE;
		case 'f':
			expectLiteral("false");
			return Boolean.FALSE;
		case 'n':
			expectLiteral("null");
			return null;
		default:
			return numberValue();
		}
	}

	private Map<String, Object> objectValue(int depth) {
		this.at++;
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		skipWhitespace();
		if (peek() == '}') {
			this.at++;
			return object;
		}
		while (true) {
			skipWhitespace();
			if (peek() != '"') {
				throw error("object key must be a string");
			}
			String key = stringValue();
			skipWhitespace();
			if (peek() != ':') {
				throw error("expected ':' after object key");
			}
			this.at++;
			Object value = value(depth + 1);
			object.put(key, value);
			skipWhitespace();
			char next = peek();
			if (next == ',') {
				this.at++;
				continue;
			}
			if (next == '}') {
				this.at++;
				return object;
			}
			throw error("expected ',' or '}' in object");
		}
	}

	private List<Object> arrayValue(int depth) {
		this.at++;
		List<Object> array = new ArrayList<Object>();
		skipWhitespace();
		if (peek() == ']') {
			this.at++;
			return array;
		}
		while (true) {
			array.add(value(depth + 1));
			skipWhitespace();
			char next = peek();
			if (next == ',') {
				this.at++;
				continue;
			}
			if (next == ']') {
				this.at++;
				return array;
			}
			throw error("expected ',' or ']' in array");
		}
	}

	private String stringValue() {
		this.at++;
		StringBuilder value = new StringBuilder();
		while (true) {
			if (this.at >= this.text.length()) {
				throw error("unterminated string");
			}
			char c = this.text.charAt(this.at++);
			if (c == '"') {
				return value.toString();
			}
			if (c != '\\') {
				if (c < 0x20) {
					throw error("control character in string");
				}
				value.append(c);
				continue;
			}
			if (this.at >= this.text.length()) {
				throw error("unterminated escape");
			}
			char escape = this.text.charAt(this.at++);
			switch (escape) {
			case '"':
				value.append('"');
				break;
			case '\\':
				value.append('\\');
				break;
			case '/':
				value.append('/');
				break;
			case 'b':
				value.append('\b');
				break;
			case 'f':
				value.append('\f');
				break;
			case 'n':
				value.append('\n');
				break;
			case 'r':
				value.append('\r');
				break;
			case 't':
				value.append('\t');
				break;
			case 'u':
				value.append(unicode());
				break;
			default:
				throw error("unknown escape \\" + escape);
			}
		}
	}

	private char unicode() {
		if (this.at + 4 > this.text.length()) {
			throw error("truncated unicode escape");
		}
		int code = 0;
		for (int i = 0; i < 4; i++) {
			int digit = Character.digit(this.text.charAt(this.at++), 16);
			if (digit < 0) {
				throw error("malformed unicode escape");
			}
			code = code << 4 | digit;
		}
		return (char) code;
	}

	private Double numberValue() {
		int start = this.at;
		if (peek() == '-') {
			this.at++;
		}
		if (peek() == '0') {
			this.at++;
		} else if (isDigit(peek())) {
			while (isDigit(peek())) {
				this.at++;
			}
		} else {
			throw error("unexpected character '" + peek() + "'");
		}
		if (peek() == '.') {
			this.at++;
			if (!isDigit(peek())) {
				throw error("expected digit after '.'");
			}
			while (isDigit(peek())) {
				this.at++;
			}
		}
		if (peek() == 'e' || peek() == 'E') {
			this.at++;
			if (peek() == '+' || peek() == '-') {
				this.at++;
			}
			if (!isDigit(peek())) {
				throw error("expected digit in exponent");
			}
			while (isDigit(peek())) {
				this.at++;
			}
		}
		String literal = this.text.substring(start, this.at);
		try {
			return Double.valueOf(literal);
		} catch (NumberFormatException ex) {
			throw error("malformed number '" + literal + "'");
		}
	}

	private void expectLiteral(String literal) {
		if (!this.text.startsWith(literal, this.at)) {
			throw error("expected '" + literal + "'");
		}
		this.at += literal.length();
	}

	private char peek() {
		return this.at < this.text.length() ? this.text.charAt(this.at) : '\0';
	}

	private static boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}

	private void skipWhitespace() {
		while (this.at < this.text.length()) {
			char c = this.text.charAt(this.at);
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				this.at++;
			} else {
				return;
			}
		}
	}

	private ParseException error(String message) {
		int line = 1;
		int column = 1;
		int limit = Math.min(this.at, this.text.length());
		for (int i = 0; i < limit; i++) {
			if (this.text.charAt(i) == '\n') {
				line++;
				column = 1;
			} else {
				column++;
			}
		}
		return new ParseException(message + " at line " + line + ", column " + column, this.at);
	}

	/** A malformed document; {@link #offset()} is where the reader stopped. */
	public static final class ParseException extends IllegalArgumentException {

		private static final long serialVersionUID = 1L;

		private final int offset;

		ParseException(String message, int offset) {
			super(message);
			this.offset = offset;
		}

		public int offset() {
			return this.offset;
		}
	}
}
