package minesweeper.core;

import minesweeper.core.Tile.State;

/**
 * Clue tile.
 */
public class Clue extends Tile {
	/** Value of the clue. */
	private final int value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            value of the clue
	 */
	public Clue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String toString() {
		if (this.getState()==State.OPEN){
			return value + " ";
		}
		else {
			return super.toString();
		}
		
	}

}
