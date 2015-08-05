package minesweeper.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;

public class FieldTest {
	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void generate() {
		Field field = new Field(ROWS, COLUMNS, MINES);
		// testovanie poctu riadkov pola
		assertEquals(ROWS, field.getRowCount());
		// testovanie poctu stlpcov pola
		assertEquals(COLUMNS, field.getColumnCount());
		// testovanie poctu vygenerovanych min
		assertEquals(MINES, field.getMineCount());
		// testovanie nulovej hodnoty v dlazdici
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				int row = i;
				int column = j;
				assertNotNull(field.getTile(row, column));
			}
		}
		// testovanie vygenerovania poctu min
		int mineCount = field.getMineCount();
		assertEquals(MINES, mineCount);
		// testovanie vygenerovania dlazdic typu Clue
		int clueCount = 0;
		for (int i = 0; i < field.getRowCount(); i++) {
			for (int j = 0; j < field.getColumnCount(); j++) {
				int row = i;
				int column = j;
				if (field.getTile(row, column) instanceof Clue) {
					clueCount++;
				}
			}
		}
		assertEquals(ROWS * COLUMNS - MINES, clueCount);
	}
}
