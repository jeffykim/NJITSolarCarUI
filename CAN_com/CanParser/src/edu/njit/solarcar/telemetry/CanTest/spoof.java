package edu.njit.solarcar.telemetry.CanTest;

import java.util.HashMap;
import java.util.Map;

public class spoof {
    int potentiometer;
    boolean counter = true;
    int count;
    float potDecimal = 0;
    int mph = 0;
    //int totalMPH = mph;
    double fullCharge = 5200;
    double socOriginal = 5.2;
    double charge = 100;
    int seconds = 0;
    double SoC;
    double milesRemaining = 260;
    Map<Integer, Float> val = new HashMap();
    int[] mphs = new int[60];

    public spoof() {

        for (int i = 0;i<60;i++) {

            mphs[i] = i;
        }
        for(int i = 0; i <=50; i++) {
            ++mph;
            potDecimal = (float) (potDecimal + 0.01);
            val.put(mph, potDecimal);
        }
        SoC = ((-mph/5.2)*100) + 5200;
        charge = (SoC/socOriginal)/10;
        milesRemaining = milesRemaining - (1/(socOriginal/potDecimal))*10;
    }
    public int mph() {
        return mph;
    }
    public int Charge() {
        return (int)charge;

    }
    public int SoC() {
        return (int)SoC;

    }
    public int milesRemaining() {
        return (int)milesRemaining;
    }
    public void update() {

        if(charge>0) {
            if(mph>59) {
                mph = 0;
                fullCharge = SoC;
            }
            ++mph;
            SoC = ((-mph/5.2)*100) + fullCharge;
            charge = (SoC/socOriginal)/10;

            milesRemaining = milesRemaining - (1/(socOriginal/potDecimal))*10;
            if(charge == 0)
            {
                charge = 0;
                SoC = 0;

            }

        }

        else {

        }
    }
}