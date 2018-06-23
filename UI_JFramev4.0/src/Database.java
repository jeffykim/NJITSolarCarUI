import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



/** 
 * Class to Create and Manage a SQLite Database
 */
public class Database {
    protected Connection connection;
    protected Statement statement;
    private static final Object lock = new Object();
    public static final Logger log = LogManager.getLogger(Database.class.getName());
    private String url = "jdbc:sqlite:"; //The Database URL, Can Be a File Path

    /**
     * Constructs a Database with filename relative to UNIX time, builds tables, and starts a connection
     */
    public Database(String saveLocation) {
        long unixTime = System.currentTimeMillis()/1000;
        url += saveLocation + "/" + unixTime + ".db";
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
            log.warn("Connection Successful, Path: " + url.substring(12, url.length()));
        } catch (SQLException e) {
            log.error("database "+e.getMessage());
        }
    }

    /**
     * Close Connection to Database
     */
    public void closeConnection() {
        try {
            connection.close();
            log.warn("Connection Closed");
        } catch (SQLException e) {
            log.error("database "+e.getMessage());
        }
    }

    /**
     * Sets up the tables in the database
     */
    private void makeTables() {
        makeDataByTimeTable();
        //makeDataByLapTable();
    }
    private void makeDataByLapTable(){
        String dataByLap = "CREATE TABLE IF NOT EXISTS dataByLap (\n"
                + "	Lap_Number integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " Lap_Time real,\n"
                + " Lap_Distance real,\n"
                + "	State_of_Charge real,\n"
                + "	Miles_Remaining real\n"
                + ") WITHOUT ROWID;";
        try {
            statement.execute(dataByLap);
            log.warn("dataByLap table made successfully");
        } catch (SQLException e) {
            log.error("dataByLap "+e.getMessage());
        }
    }
    private void makeDataByTimeTable(){
        String dataByTime = "CREATE TABLE IF NOT EXISTS dataByTime (\n"
                + " Time integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " mph real,\n"
                + " milesRemaining real,\n"
                + " internalVoltage real,\n"
                + " SoC real,\n"
                + " internalVoltage real,\n"
                + " packInstVolt real,\n"
                + " drainRate real,\n"
                + " packTemp real,\n"
                + " packCurrent real,\n"
                + " pedalVoltage real,\n"
                + " accel real,\n"
                + " linearAccelX real,\n"
                + " linearAccelY real,\n"
                + " linearAccelZ real,\n"
                + " gravitationalAccelX real,\n"
                + " gravitationalAccelY real,\n"
                + " gravitationalAccelZ real,\n"
                + " gyroX real,\n"
                + " gyroY real,\n"
                + " gyroZ real\n"
                + ") WITHOUT ROWID;";

        try {
            statement.execute(dataByTime);
            log.warn("dataByTime table made successfully");
        } catch (SQLException e) {
            log.error("dataByTime "+e.getMessage());
        }
    }
    private void makeCellDataTable(){
        String cellData = "CREATE TABLE IF NOT EXISTS cellData (\n"
                + " Time integer PRIMARY KEY,\n" //first value is col name second is data type third is col constraint
                + " cell_ID real,\n"
                + " instVoltage real,\n"
                + " internalResistance real,\n"
                + " openVoltage real\n"
                + ") WITHOUT ROWID;";

        try {
            statement.execute(cellData);
            log.warn("cellData table made successfully");
        } catch (SQLException e) {
            log.error("cellData "+e.getMessage());
        }
    }

    public void addDataByTime(DataFrame dataframe) {
        synchronized (lock) {
            String insertStatement = "INSERT INTO dataByTime(Time,mph,milesRemaining,internalVoltage,SoC,internalVoltage,packInstVolt,drainRate,packTemp,packCurrent,pedalVoltage,RPM,linearAccelX,linearAccelY,linearAccelZ,gravitationalAccelX,gravitationalAccelY,gravitationalAccelZ,gyroX,gyroY,gyroZ)"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                long unixTime = System.currentTimeMillis();
                PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
                insertDataStatement.setLong(1, dataframe.timeCreated);
                insertDataStatement.setDouble(2, dataframe.mph);
                insertDataStatement.setDouble(3, dataframe.milesRemaining);
                insertDataStatement.setDouble(4, dataframe.internalVoltage);
                insertDataStatement.setDouble(5, dataframe.SoC);
                insertDataStatement.setDouble(6, dataframe.internalVoltage);
                insertDataStatement.setDouble(7, dataframe.packInstVolt);
                insertDataStatement.setDouble(8, dataframe.drainRate);
                insertDataStatement.setDouble(9, dataframe.packTemp);
                insertDataStatement.setDouble(10, dataframe.packCurrent);
                insertDataStatement.setDouble(11, dataframe.pedalVolt);
                insertDataStatement.setDouble(12, dataframe.RPM);
                insertDataStatement.setDouble(13, dataframe.linearAcceleration.get("X"));
                insertDataStatement.setDouble(14, dataframe.linearAcceleration.get("Y"));
                insertDataStatement.setDouble(15, dataframe.linearAcceleration.get("Z"));
                insertDataStatement.setDouble(16, dataframe.gravitationalAcceleration.get("X"));
                insertDataStatement.setDouble(17, dataframe.gravitationalAcceleration.get("Y"));
                insertDataStatement.setDouble(18, dataframe.gravitationalAcceleration.get("Z"));
                insertDataStatement.setDouble(19, dataframe.gyroData.get("X"));
                insertDataStatement.setDouble(20, dataframe.gyroData.get("Y"));
                insertDataStatement.setDouble(21, dataframe.gyroData.get("Z"));
                insertDataStatement.executeUpdate();
            } catch (SQLException e) {
                log.error("dataByTime "+e.getMessage());
            }
        }

    }
    public void addCellData(DataFrame dataframe) {
        synchronized (lock) {
            String insertStatement = "INSERT INTO dataByLap(Time, cell_ID, instVoltage, internalResistance, openVoltage)"
                    + "VALUES(?,?,?,?,?)";
            for (int cellNUM = 0; cellNUM < 20; cellNUM++) {
                try {
                    PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
                    insertDataStatement.setDouble(1, dataframe.timeCreated);
                    insertDataStatement.setDouble(2, dataframe.cells.get(cellNUM).get("id"));
                    insertDataStatement.setDouble(3, dataframe.cells.get(cellNUM).get("instVoltage"));
                    insertDataStatement.setDouble(4, dataframe.cells.get(cellNUM).get("internalResistance"));
                    insertDataStatement.setDouble(5, dataframe.cells.get(cellNUM).get("internalResistance"));
                    insertDataStatement.executeUpdate();
                } catch (SQLException e) {
                    log.error("dataByLap " + e.getMessage());
                }
            }
        }
    }

    public void addDataByLap(int Lap_Number, double Lap_Time, double Lap_Distance, double State_of_Charge, double Miles_Remaining) {
        synchronized (lock) {
            String insertStatement = "INSERT INTO dataByLap(Lap_Number,Lap_Time,Lap_Distance,State_of_Charge,Miles_Remaining)"
                    + "VALUES(?,?,?,?,?)";

            try {
                PreparedStatement insertDataStatement = connection.prepareStatement(insertStatement);
                insertDataStatement.setInt(1, Lap_Number);
                insertDataStatement.setDouble(2, Lap_Time);
                insertDataStatement.setDouble(3, Lap_Distance);
                insertDataStatement.setDouble(4, State_of_Charge);
                insertDataStatement.setDouble(5, Miles_Remaining);
                insertDataStatement.executeUpdate();
            } catch (SQLException e) {
                log.error("dataByLap "+e.getMessage());
            }
        }
    }

    private Connection getConnection() {
        return connection;
    }

}