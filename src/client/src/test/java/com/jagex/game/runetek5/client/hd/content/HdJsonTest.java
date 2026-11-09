package com.jagex.game.runetek5.client.hd.content;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class HdJsonTest {

	@Test
	void readsAWholeDocumentInFileOrder() {
		Object parsed = HdJson.parse("{\"b\":1,\"a\":[true,false,null,\"x\"]}");

		Map<String, Object> object = HdJson.object(parsed);
		assertNotNull(object);
		assertEquals("[b, a]", object.keySet().toString());
		assertEquals(Double.valueOf(1.0D), HdJson.number(object.get("b")));
		List<Object> array = HdJson.array(object.get("a"));
		assertNotNull(array);
		assertEquals(Boolean.TRUE, array.get(0));
		assertEquals(Boolean.FALSE, array.get(1));
		assertNull(array.get(2));
		assertEquals("x", array.get(3));
	}

	@Test
	void decodesEscapesAndUnicode() {
		assertEquals("a\"b\\c/d\ne\tf", HdJson.string(HdJson.parse("\"a\\\"b\\\\c\\/d\\ne\\tf\"")));
		assertEquals("\u00e9", HdJson.string(HdJson.parse("\"\\u00e9\"")));
	}

	@Test
	void readsNegativeFractionsAndExponents() {
		assertEquals(Double.valueOf(-12.5D), HdJson.number(HdJson.parse("-12.5")));
		assertEquals(Double.valueOf(1500.0D), HdJson.number(HdJson.parse("1.5e3")));
		assertEquals(Double.valueOf(-0.25D), HdJson.number(HdJson.parse("-2.5E-1")));
	}

	@Test
	void accessorsReturnTheirFallbacksForTheWrongShape() {
		assertNull(HdJson.object(HdJson.parse("[]")));
		assertNull(HdJson.array(HdJson.parse("{}")));
		assertNull(HdJson.string(HdJson.parse("1")));
		assertEquals(7, HdJson.integer(HdJson.parse("\"7\""), 7));
		assertEquals(1.5F, HdJson.decimal(HdJson.parse("1.5"), 0.0F));
		assertEquals(0x123456, HdJson.colour("#123456", -1));
		assertEquals(-1, HdJson.colour("#12345", -1));
		assertEquals(-1, HdJson.colour("#12345g", -1));
		assertEquals(-1, HdJson.colour(123, -1));
	}

	@Test
	void refusesTrailingContent() {
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse("{} {}"));
	}

	@Test
	void refusesUnterminatedStrings() {
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse("\"abc"));
	}

	@Test
	void refusesMalformedNumbers() {
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse("1."));
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse("+1"));
	}

	@Test
	void reportsTheLineOfTheProblem() {
		HdJson.ParseException failure = assertThrows(HdJson.ParseException.class,
			() -> HdJson.parse("{\n  \"a\": 1,\n  \"b\": ,\n}"));
		assertTrue(failure.getMessage().contains("line 3"), failure.getMessage());
		assertTrue(failure.offset() > 0);
	}

	@Test
	void refusesDeeplyNestedDocuments() {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i <= HdJson.MAX_DEPTH + 1; i++) {
			text.append('[');
		}
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse(text.toString()));
	}

	@Test
	void skipsAByteOrderMark() {
		assertEquals(Double.valueOf(2.0D), HdJson.number(HdJson.parse("\uFEFF2")));
	}

	@Test
	void aDocumentOfOnlyWhitespaceIsRefused() {
		assertThrows(HdJson.ParseException.class, () -> HdJson.parse("  \r\n\t "));
	}
}
