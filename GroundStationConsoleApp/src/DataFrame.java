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

    UDPReceiver udpreceiver;

    public DataFrame() throws IOException {
        this.udpreceiver = UI.udpReceiver;
        linearAcceleration = new HashMap<>(3);
        gravitationalAcceleration = new HashMap<>(3);
        gyroData = new HashMap<>(3);
        this.timeCreated = System.currentTimeMillis();
        updateData();
    }
    private void updateData(){
        linearAcceleration.put("X",udpreceiver.getLaccX());
        linearAcceleration.put("Y",udpreceiver.getLaccY());
        linearAcceleration.put("Z",udpreceiver.getLaccZ());

        gravitationalAcceleration.put("X",udpreceiver.getGaccX());
        gravitationalAcceleration.put("Y",udpreceiver.getGaccY());
        gravitationalAcceleration.put("Z",udpreceiver.getGaccZ());

        gyroData.put("X",udpreceiver.getGyroX());
        gyroData.put("Y",udpreceiver.getGyroY());
        gyroData.put("Z",udpreceiver.getGyroZ());

        SoC = udpreceiver.getPackSoc();
        packTemp = udpreceiver.getPackTemp();
        packCurrent = udpreceiver.getPackCurrent();
        packInstVolt = udpreceiver.getPackInstVolts();
        internalVoltage = udpreceiver.getInternalVoltage();

        pedalVolt = udpreceiver.getPotVal();


        /*****************************

         *milesRemaining = still need
         * drainRate = still need
         */

        mph = udpreceiver.getMPH();
        milesRemaining = 100;
        drainRate = 5;
        RPM = udpreceiver.getRPM();

        this.cells = udpreceiver.cells;

    }
}