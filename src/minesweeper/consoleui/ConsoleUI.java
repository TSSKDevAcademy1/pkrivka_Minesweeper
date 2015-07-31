package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import minesweeper.UserInterface;
import minesweeper.core.Field;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
	/** Playing field. */
	private Field field;

	/** Input reader. */
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * Reads line of text from the reader.
	 * 
	 * @return line as a string
	 */
	private String readLine() {
		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#newGameStarted(minesweeper.core.
	 * Field)
	 */
	@Override
	public void newGameStarted(Field field) {
		this.field = field;
		// do {
		update();
		// processInput();
		//// throw new UnsupportedOperationException("Resolve the game state -
		// winning or loosing condition.");
		// } while(true);
		//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see minesweeper.consoleui.UserInterface#update()
	 */
	@Override
	public void update() {
		int b = 0;
		int c = 0;
		int rowCount = field.getRowCount();
		int colCount = field.getColumnCount();
		// System.out.print(" ");
		int x = field.getColumnCount();
		System.out.printf("%2s", " ");
		for (int i = 0; i < rowCount; i++) {
			int a = 1 + i;
			System.out.printf("%3s", a);
		}
		System.out.print("\n");
		for (int i = 0; i < rowCount; i++) {
			int znak1 = 65 + b;
			char myChar = (char) znak1;
			if (i < 26) {
				System.out.printf("%3s", myChar);
				b++;
				if(b==26){
					b=0;
				}
			} else {
				if (c == 26) {
					c = 0;
					b++;
				}
				znak1 = 65 + b;
				int znak2 = 65 + c;
				char myChar1 = (char) znak1;
				char myChar2 = (char) znak2;
				System.out.printf("%3s", myChar1 + "" + myChar2);
				c++;
			}
			for (int j = 0; j < colCount; j++) {
				System.out.printf("%3s", field.getTile(i, j));
			}
			System.out.printf("%n");
		}

	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		// throw new UnsupportedOperationException("Method processInput not yet
		// implemented");
	}
}
