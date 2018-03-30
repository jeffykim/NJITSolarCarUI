import java.io.IOException;
import java.util.Random;

public class DisplayValues implements Runnable {
	 	private  double internalVoltage;
	    private  double potValue;
	    private  int mps;
	    private  double Cr = 0.008;
	    private  double Cd = 0.1;
	    private  double A = 0.0959; //m^2
	    private  int mass = 317; //kg
	    private   double SoC;
	    private  double milesRemaining;
	    private  double mph;
	    private  CanReader can;
	    
	    public DisplayValues() throws IOException {
	    	can = new CanReader(true);
	    can.startPollingLoop(500000);
	    
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
	    
	@Override
	public void run() {
		
        internalVoltage = can.getInternalVoltage();
        mps = (int) (potValue/3.938);
        SoC = ((-mps/0.04422*100) + 44.22);
        mph = mps*2.23694;
        milesRemaining = mps*(44.22/((mass*9.8*Cr)+(.5*1.2*Cd*A*mps*mps)*mps)) ;
	}

}
