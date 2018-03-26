package edu.njit.solarcar.telemetry.testing.lorasqlitelogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Random;

public class Main 
{
	private static Connection db;
	
	public static void main(String... args) throws Exception {
		db = DriverManager.getConnection("jdbc:sqlite:testDB.sqlite");
		
		// verify table existence
		String sqlCreateTbl = "CREATE TABLE IF NOT EXISTS test_log (timestamp INTEGER NOT NULL, latitude DOUBLE NOT NULL, longitude DOUBLE NOT NULL, message VARCHAR(256), id INTEGER PRIMARY KEY)";
		
		Statement stCreateTbl = db.createStatement();
		
		stCreateTbl.executeUpdate(sqlCreateTbl);
		
		stCreateTbl.close();
		
		String sqlIns = "INSERT INTO test_log (timestamp, latitude, longitude, message) VALUES(?, ?, ?, ?)";
		PreparedStatement psIns = db.prepareStatement(sqlIns);
		Random r = new Random();
		for(;;) {
			long time = System.currentTimeMillis();
			double lat = r.nextDouble()-0.5;
			double lon = r.nextDouble()-0.5;
			
			System.out.printf("time: %d lat: %.3f long: %.3f\n", time, lat, lon);
			
			psIns.setLong(1, time);
			psIns.setDouble(2, lat);
			psIns.setDouble(3, lon);
			psIns.setString(4, "I can't think of something good to put here");
			
			psIns.executeUpdate();
			psIns.clearParameters();
			
			Thread.sleep(100);
		}
	}

}
