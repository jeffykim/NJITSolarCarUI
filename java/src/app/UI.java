import java.util.Map


public class UI implements DataInputController
{
    public void onPacketReady(Map<String, String> data)
    {
        Velocity(data.get("vel"));
        StateOfCharge(data.get("soc"));
        string lat = data.get("gpsLat");
        string lng = data.get("gpslong");
        GPS(lat,lng);
        TempOfBatMod(data.get("bat_temp"));
        Cell_Pwr(data.get("solar_power_out"));
        Motor_CurrentDraw(data.get("motorIDraw"));
        Line_CurrentDraw(data.get("LineIDraw"));
        Brake_Lights(data.get("lightState"));
        Acc_Sensor(data.get("accelState"));

    }

    public void reset()
    {

    }
}

public static void Velocity(string value)
{
    double dbValue = Double.parseInt(value);
}

public static void StateOfCharge(string value){double dbSOC = Double.parseDouble(value);}

public static void GPS(string lat, string lng)
{
    double dblng = Double.parseDouble(lng);
    double dblat = Double.parseDouble(lat);
}

public static void TempOfBatMod(string value){double dbTempOfBat = Double.parseDouble(value);}

public static void Cell_Pwr(string value){double dbCellPwr = Double.parseDouble(value);}

public static void Motor_CurrentDraw(String value){double dbMotorIDraw = Double.parseDouble(value);}

public static void Line_CurrentDraw(String values){double dbLineIDraw = Double.pareseDouble(value);}

public static void Brake_Lights(String value) { boolean blLights = Boolean.parseBoolean(value);}

public static void Acc_Sensor(String values){double dbAcc = Double.parseDouble(value);}