import java.io.IOException;
import java.util.HashMap;
public class DataFrame {
    public long timeCreated;

    public double milesRemaining;
    public double mph;

    public double SoC;
    public double drainRate;
    public double packTemp;
    public double internalVoltage;
    public double packCurrent;

    public double pedalVolt;
    public double packInstVolt;

    //public double lat;
    //public double lon;

    public double RPM;
    public HashMap<String,Double> linearAcceleration;
    public HashMap<String, Double> gravitationalAcceleration;
    public HashMap<String, Double> gyroData;

    public HashMap< Integer, HashMap<String, Double>> cells;

    CanReader can;

    public DataFrame() throws IOException {
        this.can = UI.can;
        linearAcceleration = new HashMap<>(3);
        gravitationalAcceleration = new HashMap<>(3);
        gyroData = new HashMap<>(3);
        this.timeCreated = System.currentTimeMillis();
        updateData();
    }
    private void updateData(){
        linearAcceleration.put("X",can.getLaccX());
        linearAcceleration.put("Y",can.getLaccY());
        linearAcceleration.put("Z",can.getLaccZ());

        gravitationalAcceleration.put("X",can.getGaccX());
        gravitationalAcceleration.put("Y",can.getGaccY());
        gravitationalAcceleration.put("Z",can.getGaccZ());

        gyroData.put("X",can.getGyroX());
        gyroData.put("Y",can.getGyroY());
        gyroData.put("Z",can.getGyroZ());

        SoC = can.getPackSoc();
        packTemp = can.getPackTemp();
        packCurrent = can.getPackCurrent();
        packInstVolt = can.getPackInstVolts();
        internalVoltage = can.getInternalVoltage();

        pedalVolt = can.getPotVal();


        /*****************************

         *milesRemaining = still need
         * drainRate = still need
         */

        mph = can.getMPH();
        milesRemaining = 100;
        drainRate = 5;
        RPM = can.getRPM();

        this.cells = can.cells;

    }
}