public class DataFrame {
    public double internalVoltage;
    public double accel;
    public double SoC;
    public double milesRemaining;
    public double mph;
    public double lat;
    public double lon;
    public long timeCreated;

    public DataFrame(){
        this.timeCreated = System.currentTimeMillis();
    }
}