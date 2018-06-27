import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings({ "unused" })
public class UI extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JFrame frame1;
    public static JLabel JLBatTemp;
    public static JLabel JLBatPercent;
    public static JLabel JLCar;
    public static JLabel JLMilesRem;
    public static JLabel JLSpeed;
    public static JLabel JLPowerConsumed;
    public static JLabel JLWarning;
    public static TextArea WarningList;
    //public static double mph;
    //public static double SoC;
    //public static double milesRemaining;
    public static Database database;
    public static final Logger log = LogManager.getLogger(UI.class.getName());

    public UI()  
    {
    	
        createUserInterface();
        //Kina na3mil el dataframe 7wn - ma ba3rif lish
    }

    private void createUserInterface()
    {
        Color backgroundColor = Color.GRAY;
        Container contentPane = frame1.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(backgroundColor);
        contentPane.setBounds(0,0,400,500);

        JLayeredPane JLPane = frame1.getLayeredPane();
        JLPane.setBounds(400,0,400,500);
        JLPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        frame1.setTitle("UI");
        frame1.setSize(800,500);
        frame1.setVisible(true);

        JLBatTemp = new JLabel("Battery Temp: " + 00 + "°F");
        JLBatTemp.setBounds(600,10,200,50);
        JLBatTemp.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatTemp.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLBatTemp);

        JLBatPercent = new JLabel(0 + "%");
        JLBatPercent.setBounds(0,10,250,50);
        JLBatPercent.setHorizontalAlignment(SwingConstants.CENTER);
        JLBatPercent.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLBatPercent);

        JLMilesRem = new JLabel("Miles Remain: " + 0);
        JLMilesRem.setBounds(10,390,200,55);
        JLMilesRem.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesRem.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLMilesRem);

        JLSpeed = new JLabel(0 + " mph");
        JLSpeed.setBounds(200,60,400,330);
        JLSpeed.setHorizontalAlignment(SwingConstants.CENTER);
        JLSpeed.setFont(new Font("Ariel",Font.BOLD,45));
        contentPane.add(JLSpeed);


        JLPowerConsumed = new JLabel("Power Consumed: " + 0);
        JLPowerConsumed.setBounds(600,390,300,55);
        JLPowerConsumed.setFont(new Font("Ariel",Font.PLAIN,20));
        JLPowerConsumed.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(JLPowerConsumed);

        JLWarning = new JLabel("Warnings:");
        JLWarning.setBounds(600,70,200,50);
        JLWarning.setHorizontalAlignment(SwingConstants.LEFT);
        JLWarning.setFont(new Font("Ariel",Font.BOLD,20));
        contentPane.add(JLWarning);

        WarningList = new TextArea("None");
        WarningList.setBounds(600,120,200,300);
        WarningList.setFont(new Font("Ariel",Font.PLAIN,18));
        WarningList.setBackground(backgroundColor);
        contentPane.add(WarningList);
        /*
        JLCar = new JLabel();
        JLCar.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLCar.setOpaque(true);
        JLCar.setBackground(Color.RED);
        JLPane.add(JLCar,0 );
        */
    }
    @SuppressWarnings({ "unused" })
	public static void main(String[] args) throws Exception
    {
        frame1 = new JFrame("UI");
        UI application = new UI();
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );

        CanReader can = new CanReader(false);
        can.startPollingLoop(0);

        database = new Database("/home/george/Desktop"); //Path Must Be Changed

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
            int mph = (int) dataFrame.mph;
            double SoC = dataFrame.SoC;
            double milesRemaining = dataFrame.milesRemaining;

            if (mph >= 40)
                JLSpeed.setForeground(Color.RED);
            else
                JLSpeed.setForeground(Color.BLACK);

            if (SoC <= 20)
                JLBatPercent.setText(SoC + " % " + "LOW!!");
            else
                JLBatPercent.setText(SoC + " % ");

            JLMilesRem.setText("Miles Remain: " + milesRemaining);
            JLSpeed.setText(mph + " mph");
            JLBatTemp.setText((int)dataFrame.packTemp+"°F");
            JLPowerConsumed.setText(dataFrame.drainRate+"Amps");
            updateWarnings(dataFrame);

        }
    }
    public static void updateWarnings(DataFrame dataframe){
        int warningCount = 0; //Screen only fits 12 warnings
        StringBuilder output = new StringBuilder("");
        for(int i = 1; i<=20; i++){
            if(dataframe.cells.get(i).get("instVoltage")<=2.7 && warningCount<12){
                output.append("Cell "+i+" low:"+dataframe.cells.get(i).get("instVoltage")+"\n");
            }
            else if (dataframe.cells.get(i).get("instVoltage")<=2.7 && warningCount==11){
                output.append("More Errors!!");
            }
        }
        if(warningCount == 0){
            output.append("NONE");
        }

        WarningList.setText(output.toString());
    }
}