package com.jagex.game.runetek5.client.hd.ground;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("world texture atlas")
class HdWorldAtlasTest {

	@Test
	@DisplayName("the page is a power-of-two square of whole cells")
	void page_shouldBeWholeCells() {
		assertAll(
			() -> assertEquals(64, HdWorldAtlas.CAPACITY, "8x8 cells"),
			() -> assertEquals(512, HdWorldAtlas.SIZE, "8 cells of 64 texels"),
			() -> assertEquals(0, HdWorldAtlas.SIZE & (HdWorldAtlas.SIZE - 1), "a power of two"),
			() -> assertEquals(0, HdWorldAtlas.CELL_SIZE & (HdWorldAtlas.CELL_SIZE - 1), "a power of two"),
			() -> assertEquals(64, HdWorldAtlas.COLUMNS * HdWorldAtlas.ROWS, "the page holds columns times rows")
		);
	}

	@Test
	@DisplayName("every cell's sampling rectangle sits inside its own cell, away from the seam")
	void cell_shouldBeInsetInsideItsSquare() {
		for (int cell = 0; cell < HdWorldAtlas.CAPACITY; cell++) {
			int finalCell = cell;
			float u0 = HdWorldAtlas.u0(finalCell);
			float v0 = HdWorldAtlas.v0(finalCell);
			float u1 = u0 + HdWorldAtlas.span();
			float v1 = v0 + HdWorldAtlas.span();
			float cellU1 = (HdWorldAtlas.x(finalCell) + HdWorldAtlas.CELL_SIZE) / (float) HdWorldAtlas.SIZE;
			float cellV1 = (HdWorldAtlas.y(finalCell) + HdWorldAtlas.CELL_SIZE) / (float) HdWorldAtlas.SIZE;
			assertAll(
				() -> assertTrue(u0 > HdWorldAtlas.x(finalCell) / (float) HdWorldAtlas.SIZE,
					"cell " + finalCell + " starts inside its square: " + u0),
				() -> assertTrue(v0 > HdWorldAtlas.y(finalCell) / (float) HdWorldAtlas.SIZE,
					"cell " + finalCell + " starts above its square: " + v0),
				() -> assertTrue(u1 < cellU1, "cell " + finalCell + " ends before the next column: " + u1),
				() -> assertTrue(v1 < cellV1, "cell " + finalCell + " ends before the next row: " + v1)
			);
		}
	}

	@Test
	@DisplayName("cells never share a texel, row by row")
	void cells_shouldNotOverlap() {
		for (int row = 0; row < HdWorldAtlas.ROWS; row++) {
			for (int column = 0; column < HdWorldAtlas.COLUMNS; column++) {
				int cell = row * HdWorldAtlas.COLUMNS + column;
				int nextColumn = cell + 1;
				if (column + 1 < HdWorldAtlas.COLUMNS) {
					assertTrue(HdWorldAtlas.u0(cell) + HdWorldAtlas.span() <= HdWorldAtlas.u0(nextColumn),
						"cell " + cell + " ends before cell " + nextColumn);
				}
				int nextRow = cell + HdWorldAtlas.COLUMNS;
				if (row + 1 < HdWorldAtlas.ROWS) {
					assertTrue(HdWorldAtlas.v0(cell) + HdWorldAtlas.span() <= HdWorldAtlas.v0(nextRow),
						"cell " + cell + " ends before cell " + nextRow);
				}
			}
		}
	}

	@Test
	@DisplayName("a cell number outside the page is not a cell")
	void cell_shouldRefuseOutOfRange() {
		assertAll(
			() -> assertEquals(0, HdWorldAtlas.cell(0)),
			() -> assertEquals(HdWorldAtlas.CAPACITY - 1, HdWorldAtlas.cell(HdWorldAtlas.CAPACITY - 1)),
			() -> assertEquals(-1, HdWorldAtlas.cell(-1)),
			() -> assertEquals(-1, HdWorldAtlas.cell(HdWorldAtlas.CAPACITY))
		);
	}

	@Test
	@DisplayName("a cell covers one cell of texels less its inset")
	void sampledSize_shouldBeTheInsetRectangle() {
		assertEquals(HdWorldAtlas.CELL_SIZE - 2 * HdWorldAtlas.INSET, HdWorldAtlas.sampledSize());
	}
}
