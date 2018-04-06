/**
 * 
 * @author nayeemkamal
 *
 */
public class Dataframe extends Thread {

	private  double internalVoltage;
    private  double accel;
    private   double SoC;
    private  double milesRemaining;
    private  double mph;
    private DisplayValues d;
    private static double lat;
    private static double lon;
    private GPS gps;

    public Dataframe() throws Exception {
    	d = new DisplayValues();
    	gps = new GPS();
    }
    public double getLat() {
		return gps.getLat();
	}
	public double getLon() {
		return gps.getLon();
	}
    public double getSOC() {
    	return this.SoC;
    }
    public double getMPH() {
    	return this.mph;
    }
    public double getMilesRemaining() {
    	return this.milesRemaining;
    	
    }
    
    public double getInternalVoltage() {
    	return this.internalVoltage;
    }

	public double getAccel() {
		return accel;
	}

    
    public String toString() {
    	return this.SoC+","+this.milesRemaining+","+this.SoC;
    }

	
	public void run() {
		internalVoltage = d.getInternalVoltage();
		milesRemaining = d.getMilesRemaining();
		mph = d.getMPH();
		SoC = d.getSOC();
		
	}



	
}
