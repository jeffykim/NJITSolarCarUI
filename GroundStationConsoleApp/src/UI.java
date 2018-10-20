import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({ "unused" })
public class UI
{
  
	private static final long serialVersionUID = 1L;

    public static Database database;
    public static UDPReceiver udpReceiver;
    public static final Logger log = LogManager.getLogger(UI.class.getName());

    @SuppressWarnings({ "unused" })
	public static void main(String[] args) throws Exception
    {

        udpReceiver = new UDPReceiver();
        udpReceiver.run();

        database = new Database();

        try{
            Thread.sleep(20000);  //To allow Cell Data to populate
        }
        catch (InterruptedException e){
            log.error("UI: "+ e.getMessage());
        }

        Timer timer = new Timer();
        TimerTask gen = new TimerTask()
        {
            @Override
            public void run() {
                updateValues();

            }
        };
        timer.schedule(gen,0,2000);



    }

    public static void updateValues() {
        DataFrame dataFrame = null;
        try {
            dataFrame = new DataFrame();
        } catch (IOException e) {
            log.error("dataFrame Creation "+e.toString());
        }
        if (dataFrame != null) {
        	//********************************
           	//Print Data to Console 
           	//Add data to Database
           	//********************************
        }
    }
}
