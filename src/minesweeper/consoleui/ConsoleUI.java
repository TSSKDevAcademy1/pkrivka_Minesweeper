package minesweeper.consoleui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import minesweeper.BestTimes;
import minesweeper.BestTimes.PlayerTime;
import minesweeper.Minesweeper;
import minesweeper.UserInterface;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Tile;

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
		System.out.println("Vitaj " + System.getProperty("user.name"));
		System.out.println(
				"X – ukoncenie hry,\nMA1 – oznacenie dlazdice v riadku A a stlpci 1,\nOB4 – odkrytie dlazdice v riadku B a stlpci 4\n");
		
		do {
			update();
			processInput();
			if (field.getState() == GameState.SOLVED) {				
				for (int i = 0; i < field.getRowCount(); i++) {
					for (int j = 0; j < field.getColumnCount(); j++) {
						int row = i;
						int column = j;
						field.openTile(row, column);
					}
				}
				update();
				System.out.println("Si super, vyhral si!");
				System.exit(0);
			} else if (field.getState() == GameState.FAILED) {
				for (int i = 0; i < field.getRowCount(); i++) {
					for (int j = 0; j < field.getColumnCount(); j++) {
						int row = i;
						int column = j;
						field.openTile(row, column);
					}
				}
				update();
				System.out.println("Prehral si!");
				System.exit(0);
			}
			// throw new UnsupportedOperationException("Resolve the game state -
			// winning or loosing condition.");
		} while (true);

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
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		// System.out.print(" ");
		// int x = field.getColumnCount();
		System.out.printf("%2s", " ");
		for (int i = 0; i < rowCount; i++) {
			int a = i;
			formatter.format("%3s", a);
		}
		formatter.format("%n");
		for (int i = 0; i < rowCount; i++) {
			int znak1 = 65 + b;
			char myChar = (char) znak1;
			if (i < 26) {
				formatter.format("%3s", myChar);
				b++;
				if (b == 26) {
					b = 0;
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
				formatter.format("%3s", myChar1 + "" + myChar2);
				c++;
			}
			for (int j = 0; j < colCount; j++) {
				formatter.format("%3s", field.getTile(i, j));
			}
			formatter.format("%n");
		}
		System.out.print(sb);
		if (field.getState() == GameState.PLAYING) {
			System.out.println("Pocet neoznacenych min je: "+field.getRemainingMineCount());
			System.out.println("Tvoj hraci cas je zatial: "+Minesweeper.getInstance().getPlayingSeconds()+"s");
		}
		else {
			System.out.println("Tvoj hraci cas bol: "+Minesweeper.getInstance().getPlayingSeconds()+"s");
		}

		formatter.close();
	}

	/**
	 * Processes user input. Reads line from console and does the action on a
	 * playing field according to input string.
	 */
	private void processInput() {
		String input = readLine();
		try {
			handleInput(input);
		} catch (WrongFormatException ex) {
			ex.getMessage();
			System.out.println(ex);
		}
	}

	private void handleInput(String input) throws WrongFormatException {
		input = input.toUpperCase();
		Pattern p = Pattern.compile("X|((O|M)([A-Z])([0-9]))");
		Matcher matcher = p.matcher(input);
		if (matcher.matches()) {
			if ("X".equals(matcher.group(0))) {
				System.out.println("Dakujeme za hru");
				System.exit(0);
			} else {
				int row = matcher.group(3).charAt(0) - 'A';
				int column = Integer.parseInt(matcher.group(4));
				if (row >= field.getRowCount()) {
					throw new WrongFormatException("Zadal si velke cislo riadka!!!");
				}
				if (column >= field.getColumnCount()) {
					throw new WrongFormatException("Zadal si velke cislo stlpca!!!");
				}
				if ("O".equals(matcher.group(2))) {
					field.openTile(row, column);

				} else if ("M".equals(matcher.group(2))) {
					field.markTile(row, column);
				}
			}
		} else {
			throw new WrongFormatException("Zadal si zly parameter!!!");
		}

	}
}
