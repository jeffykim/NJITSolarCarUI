import java.util.HashMap;
public class Dataframe {
    public double internalVoltage;
    public double accel;
    public double SoC;
    public double drainRate;
    public double milesRemaining;
    public double mph;
    public double lat;
    public double lon;
    public HashMap<String,Double> linearAcceleration;
    private HashMap<String, Double> gravitationalAcceleration;
    public long timeCreated;

    public Dataframe(){
        this.timeCreated = System.currentTimeMillis();
        linearAcceleration = new HashMap<>(3);
        gravitationalAcceleration = new HashMap<>(3);
    }
}