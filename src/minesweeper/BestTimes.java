package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.misc.Cleaner;

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
		try {
			insertToDB(player);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Chyba pri ukladani casu: "+e.getMessage());
		}
		Collections.sort(playerTimes);
	}
	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		BestTimes.PlayerTime besttimes=new BestTimes.PlayerTime("",0);
		selectFromDB();
		String hall = "";
		Iterator<PlayerTime> iterator = playerTimes.iterator();
		for (int i=0;i<24;i++){
			hall+="_";
		}
		hall+=String.format("%1s", "\n");
		hall+=String.format("%1s %20s %1s", "|","-Hall of Fame-","|"+"\n");
		for (int i=0;i<24;i++){
			hall+="-";
		}
		hall+=String.format("%1s", "\n");
		hall += String.format("%1s %10s %1s %10s","|", "Name", "|","Time|\n");
//		System.out.println("-Hall of Fame-");
		while (iterator.hasNext()) {
			PlayerTime playerIterator = iterator.next();
			hall += String.format("%1s %10s %1s %10s","|", playerIterator.name, "|",playerIterator.time + "s|\n");
		}
		for (int i=0;i<24;i++){
			hall+="-";
		}
		return hall;
		// StringBuilder sb = new StringBuilder();
		// Formatter f = new Formatter(sb);
		// int hall = playerTimes.size();
		// f.format("%10s", "-Meno-");
		// f.format("%10s", "-Cas-"+"\n");
		// for (int i = 0; i < hall; i++) {
		// f.format("%10s", playerTimes.get(i).name+" ");
		// f.format("%10s", playerTimes.get(i).time+" \n");
		// }
		// return f.toString();
	}

	private void insertToDB(PlayerTime playerTime) throws SQLException {
		// Class.forName(DatabaseSetting.DRIVER_CLASS);
		Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
		Statement stm = connection.createStatement();
		try {
			stm.executeUpdate(DatabaseSetting.QUERY_CREATE_BEST_TIMES);
		} catch (Exception e) {
			// do not propagate exception, table may already exist
		}
		stm.close();
		PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.QUERY_ADD_BEST_TIME);
		try {
			pstm.setString(1, playerTime.getName());
			pstm.setInt(2, playerTime.getTime());
			pstm.execute();
		} catch (Exception e) {
			System.out.println("Exception occured during saving high score to database: " + e.getMessage());
		}
		
		
		pstm.close();
	}

	private void selectFromDB() {
		// Class.forName(DatabaseSetting.DRIVER_CLASS);
		try (Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery(DatabaseSetting.QUERY_SELECT_BEST_TIMES);) {
			while (rs.next()) {
				PlayerTime pt = new PlayerTime(rs.getString(1), rs.getInt(2));
				playerTimes.add(pt);
			}
		} catch (Exception e) {
			System.out.println("Exception occured during loading high score to database: " + e.getMessage());
		}
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
