package minesweeper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import java.util.Formatter;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name ot the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		PlayerTime player = new PlayerTime(name, time);
		playerTimes.add(player);
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		String hall="";
		Iterator<PlayerTime> iterator=playerTimes.iterator();
		while (iterator.hasNext()){
			PlayerTime playerIterator=iterator.next();
			hall+=String.format("%10s %10s", playerIterator.name,playerIterator.time+"\n");
		}
		return hall;
//		StringBuilder sb = new StringBuilder();
//		Formatter f = new Formatter(sb);
//		int hall = playerTimes.size();
//		f.format("%10s", "-Meno-");
//		f.format("%10s", "-Cas-"+"\n");
//		for (int i = 0; i < hall; i++) {
//			f.format("%10s", playerTimes.get(i).name+" ");
//			f.format("%10s", playerTimes.get(i).time+" \n");
//		}
//		return f.toString();		
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {

		public int compareTo(PlayerTime o) {
			if (this.time == o.getTime()) {
				return 0;
			} else if (this.time < o.getTime()) {
				return -1;
			} else {
				return 1;
			}
		}

		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		public String getName() {
			return name;
		}

		public int getTime() {
			return time;
		}

	}

}
