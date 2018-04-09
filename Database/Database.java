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
	/**
	 * Constructs a Database with filename relative to UNIX time, builds tables, and starts a connection
	 */
	public Database(String saveLocation) {
		long unixTime = System.currentTimeMillis() / 1000L;
		url += saveLocation+"\\"+unixTime+".db";
		startConnection();
		makeTables();
		
	}
	/**
	 * To Start the Connection to the database if it was closed. 
	 */
	public void startConnection() {
		try {
			connection = DriverManager.getConnection(url); //Open Connection
			statement = connection.createStatement();
			System.out.println("Connection Successful, Path: "+url.substring(12, url.length()));
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Close Connection to Database
	 */
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
	 * Constructor Calls This
	 * This only needs to be called one to set up the database, if a database is already made then there is no need to run it
	 */
	private void makeTables() {
		String dataByLap = "CREATE TABLE IF NOT EXISTS dataByLap (\n"
                + "	Lap_Number integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " Lap_Time real,\n"
                + " Lap_Distance real,\n"
                + "	State_of_Charge real,\n"
                + "	Miles_Remaining real\n"
                + ") WITHOUT ROWID;";
		String dataByTime = "CREATE TABLE IF NOT EXISTS dataByTime (\n"
                + " Time integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " Speed real,\n"
                + " State_of_Charge real,\n"
                + " Power_Draw real,\n"
                + " Acclrm_X real,\n"
                + " Acclrm_Y real,\n"
                + " Acclrm_Z real,\n"
                + " Miles_Travled real,\n"
                + " Miles_Remaining real,\n"
                + " Cabin_Temperature real,\n"
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
	public void addDataByTime(double Speed, double State_of_Charge, double Power_Draw, double Acclrm_X, double Acclrm_Y, double Acclrm_Z, double Miles_Travled, double Miles_Remaining, double Cabin_Temperature, double Driver_Heart_Rate) {
		String insertStatement = "INSERT INTO dataByTime(Time,Speed,State_of_Charge,Power_Draw,Acclrm_X,Acclrm_Y,Acclrm_Z,Miles_Travled,Miles_Remaining,Cabin_Temperature,Driver_Heart_Rate)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			long unixTime = System.currentTimeMillis();
			PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
			insertDataStatement.setLong(1,unixTime);
			insertDataStatement.setDouble(2,Speed);
			insertDataStatement.setDouble(3,State_of_Charge);
			insertDataStatement.setDouble(4,Power_Draw);
			insertDataStatement.setDouble(5,Acclrm_X);
			insertDataStatement.setDouble(6,Acclrm_Y);
			insertDataStatement.setDouble(7,Acclrm_Z);
			insertDataStatement.setDouble(8,Miles_Travled);
			insertDataStatement.setDouble(9,Miles_Remaining);
			insertDataStatement.setDouble(10,Cabin_Temperature);
			insertDataStatement.setDouble(11,Driver_Heart_Rate);

			insertDataStatement.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
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
