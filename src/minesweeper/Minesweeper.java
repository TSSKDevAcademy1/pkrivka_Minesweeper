package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;
	private long startMillis = System.currentTimeMillis();
	private static Minesweeper instance;

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance=this;
		userInterface = new ConsoleUI();
		Field field = new Field(8, 8, 10);
		// field.openTile(2, 3);
		userInterface.newGameStarted(field);
		
	}
	
	public static Minesweeper getInstance(){
		return instance;
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		new Minesweeper();
	}

	public int getPlayingSeconds() {
		long seconds = (System.currentTimeMillis() - startMillis) / 1000;
		return (int) seconds;
	}
}
