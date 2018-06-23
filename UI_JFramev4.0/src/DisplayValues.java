/**
 * @author nayeemkamal
 */
import java.io.IOException;
import java.util.Random;

public class DisplayValues extends Thread {
	 	@SuppressWarnings("unused")
		private  double internalVoltage;
	    private  double accel;
	    private  int mps;
	    private  double Cr = 0.008;
	    private  double Cd = 0.1;
	    private  double A = 0.0959; //m^2
	    private  int mass = 317; //kg
	    private   double SoC;
	    private  double milesRemaining;
	    private  double mph;
	    private  CanReader can;
	    /**
	     * 
	     * @throws IOException
	     */
	    public DisplayValues() throws IOException {
	    	can = new CanReader(true);
	    can.startPollingLoop(500000);
	    
	    }
	    /**
	     * 
	     * @return State of Charge
	     */
	    public double getSOC() {
	    	return this.SoC;
	    }
	    /**
	     * 
	     * @return Speed as MPH
	     */
	    public double getMPH() {
	    	return this.mph;
	    }
	    /**
	     * 
	     * @return miles remaining
	     */
	    public double getMilesRemaining() {
	    	return this.milesRemaining;
	    	
	    }
	    /**
	     * 
	     * @return internal voltage
	     */
	    public double getInternalVoltage() {
	    	return can.getInternalVoltage();
	    }
	    
	@Override
	/**
	 * Calculates efficiency algorithm
	 */
	public void run() {
		
        internalVoltage = can.getInternalVoltage();
        mps = (int) (accel/3.938);
        SoC = ((-mps/0.04422*100) + 44.22);
        mph = mps*2.23694;
        milesRemaining = mps*(44.22/((mass*9.8*Cr)+(.5*1.2*Cd*A*mps*mps)*mps)) ;
	}

}
