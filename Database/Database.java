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
                + " internalVoltage real,\n"
                + " accel real,\n"
                + " SoC real,\n"
                + " drainRate real,\n"
                + " milesRemaining real,\n"
                + " mph real,\n"
                + " lat real,\n"
                + " lon real,\n"
                + " linearAccelX real,\n"
				+ " linearAccelY real,\n"
				+ " linearAccelZ real,\n"
				+ " gravitationalAccelX real,\n"
				+ " gravitationalAccelY real,\n"
				+ " gravitationalAccelZ real\n"
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
	public void addDataByTime(long time, double internalVoltage, double accel, double SoC, double drainRate,
							  double milesRemaining, double mph, double lat, double lon, double linearAccelX,
							  double linearAccelY, double linearAccelZ, double gravitationalAccelX,
							  double gravitationalAccelY, double gravitationalAccelZ) {
		String insertStatement = "INSERT INTO dataByTime(Time,internalVoltage,accel,SoC,drainRate,milesRemaining,mph,lat,lon,linearAccelX,linearAccelY,linearAccelZ,gravitationalAccelX,gravitationalAccelY,gravitationalAccelZ)"
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			long unixTime = System.currentTimeMillis();
			PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
			insertDataStatement.setLong(1,time);
			insertDataStatement.setDouble(2,internalVoltage);
			insertDataStatement.setDouble(3,accel);
			insertDataStatement.setDouble(4,SoC);
			insertDataStatement.setDouble(5,drainRate);
			insertDataStatement.setDouble(6,milesRemaining);
			insertDataStatement.setDouble(7,mph);
			insertDataStatement.setDouble(8,lat);
			insertDataStatement.setDouble(9,lon);
			insertDataStatement.setDouble(10,linearAccelX);
			insertDataStatement.setDouble(11,linearAccelY);
			insertDataStatement.setDouble(12,linearAccelZ);
			insertDataStatement.setDouble(13,gravitationalAccelX);
			insertDataStatement.setDouble(14,gravitationalAccelY);
			insertDataStatement.setDouble(15,gravitationalAccelZ);

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
