import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
/** 
 * Class to Create and Manage a SQLite Database
 */
public class Database {
	protected Connection connection;
	protected Statement statement;
	private String url = "jdbc:sqlite:"; //The Database URL, Can Be a File Path
	public Database(String databaseLocation) {
		url += databaseLocation;
		startConnection(databaseLocation);
	}
	
	private void startConnection(String databaseLocation) {
		try {
			connection = DriverManager.getConnection(url); //Open Connection
			statement = connection.createStatement();
			System.out.println("Connection Successful, Path: "+url.substring(12, url.length()));
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	public void startConnection() {
		startConnection(url);
	}
	
	public void closeConnection() {
		try {
			connection.close();
			System.out.println("Connection Closed");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * This only needs to be called one to set up the database, if a database is already made then there is no need to run it
	 */
	public void makeTables() {
		String dataByLap = "CREATE TABLE IF NOT EXISTS dataByLap (\n"
                + "	Lap_Number integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " Lap_Time real,\n"
                + " Lap_Distance real,\n"
                + "	State_of_Charge real,\n"
                + "	Miles_Remaining real\n"
                + ") WITHOUT ROWID;";
		String dataByTime = "CREATE TABLE IF NOT EXISTS dataByTime (\n"
                + " Time TEXT PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " Speed real,\n"
                + " State_of_Charge real,\n"
                + " Power_Draw real,\n"
                + " Acclrm_X real,\n"
                + " Acclrm_Y real,\n"
                + " Acclrm_Z real,\n"
                + " Miles_Travled real\n"
                + " Miles_Remaining real\n"
                + " Cabin_Temperature real\n"
                + "	Driver_Heart_Rate real\n"
                + ") WITHOUT ROWID;";
		try{
			statement.execute(dataByLap);
			System.out.println("dataByLap table made successfully");
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		try{
			statement.execute(dataByTime);
			System.out.println("dataByTime table made successfully");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void addDataByLap(int Lap_Number, double Lap_Time, double Lap_Distance, double State_of_Charge, double Miles_Remaining) {
		String insertStatement = "INSERT INTO dataByLap(Lap_Number,Lap_Time,Lap_Distance,State_of_Charge,Miles_Remaining)"
				+ "VALUES(?,?,?,?,?)";
		
		try {
			PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
			insertDataStatement.setInt(1,Lap_Number);
			insertDataStatement.setDouble(2,Lap_Time);
			insertDataStatement.setDouble(3,Lap_Distance);
			insertDataStatement.setDouble(4,State_of_Charge);
			insertDataStatement.setDouble(5,Miles_Remaining);
			insertDataStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	public Connection getConnection() {
		return connection;
	}

}
