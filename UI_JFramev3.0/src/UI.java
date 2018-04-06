import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    public  JLabel JLMap;
    public static Dataframe display;
    public static double mph;
    public static double SoC;
    public static double milesRemaining;
    private static ErrorLog logger;
    
    public UI()  
    {
    	
        createUserInterface();
		try {
			display = new Dataframe();
		} catch (Exception e) {
	        logger = new ErrorLog();

			((ErrorLog) logger).log(e);
		}

        display.run();
    }

    private void createUserInterface()
    {
        Container contentPane = frame1.getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(Color.GRAY);
        contentPane.setBounds(0,0,400,500);

        JLayeredPane JLPane = frame1.getLayeredPane();
        JLPane.setBounds(400,0,400,500);
        JLPane.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        frame1.setTitle("UI");
        frame1.setSize(800,500);
        frame1.setVisible(true);

        JLBatTemp = new JLabel("Battery Temp: " + 70 + "F");
        JLBatTemp.setBounds(200,10,200,50);
        JLBatTemp.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatTemp.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLBatTemp);

        JLBatPercent = new JLabel(0 + "%");
        JLBatPercent.setBounds(0,10,100,50);
        JLBatPercent.setHorizontalAlignment(SwingConstants.CENTER);
        JLBatPercent.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLBatPercent);

        JLMilesRem = new JLabel("Miles Remain: " + 0);
        JLMilesRem.setBounds(0,390,200,55);
        JLMilesRem.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesRem.setFont(new Font("Ariel",Font.PLAIN,20));
        contentPane.add(JLMilesRem);

        JLMap = new JLabel();
        JLMap.setBounds(400,0,376,458);
        JLMap.setIcon(new ImageIcon("src/mapfinal.jpg"));
        //JLMap.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        JLPane.add(JLMap,1);

        JLSpeed = new JLabel(0 + " mph");
        JLSpeed.setBounds(0,60,400,330);
        JLSpeed.setHorizontalAlignment(SwingConstants.CENTER);
        JLSpeed.setFont(new Font("Ariel",Font.BOLD,45));
        contentPane.add(JLSpeed);

        JLCar = new JLabel();
        JLCar.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLCar.setOpaque(true);
        JLCar.setBackground(Color.RED);
        JLPane.add(JLCar,0 );

    }
    @SuppressWarnings({ "unused" })
	public static void main(String[] args) throws Exception
    {
        frame1 = new JFrame("UI");
       UI application = new UI();

        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );

        Timer timer = new Timer();
        TimerTask gen = new TimerTask()
        {
            @Override
            public void run() {
                PsudoValues();
            }
        };

        timer.schedule(gen,0,2000);



    }

    public static void PsudoValues() {
    		mph = display.getMPH();
    		SoC = display.getSOC();
    		milesRemaining = display.getMilesRemaining();

        if(mph >= 40)
            JLSpeed.setForeground(Color.RED);
          else
            JLSpeed.setForeground(Color.BLACK);

        if (SoC <= 20)
            JLBatPercent.setText(SoC +" % "+"Battery Critical!!");
        else
            JLBatPercent.setText(SoC + " % ");

        JLMilesRem.setText("Miles Remain: " + milesRemaining);
        JLSpeed.setText(mph + " mph");
    }
}