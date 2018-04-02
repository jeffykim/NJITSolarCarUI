import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GPS {
    private Process p;
    private BufferedReader in;
    public GPS() {
        try {
            p = Runtime.getRuntime().exec("python /media/george/TETA/GPS/src/zib.py");
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        }
        catch (IOException e){e.printStackTrace();}
    }

    public String readGPS(){
        String ret = "";
        try {
            ret = in.readLine();
        }
        catch (IOException e){e.printStackTrace();}
        return ret;
    }
}
