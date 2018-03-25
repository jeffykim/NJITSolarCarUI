package edu.njit.solarcar.telemetry.CanTest;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class UI extends JFrame
{
    static JFrame frame1;
    public static JLabel JLBatTemp;
    public static JLabel JLBatPercent;
    public static JLabel JLCar;
    public static JLabel JLMilesRem;
    public static JLabel JLSpeed;
    public  JLabel JLMap;
    public static FileRead coordsReader;
    public static spoof fake;
    public static CanReader can;
    public static double internalVoltage;
    public static double potValue;
    public static double mps;
    public static double fullCharge = 44.22; //watt/hrs
    public static double socOriginal = 100; //percentage
    public static double Cr = 0.008;
    public static double Cd = 0.1;
    public static double A = 0.0959; //m^2
    public static int mass = 317; //kg
    public static  double SoC;
    public static double milesRemaining;
    public static double mph;
    public static double summph;
    public static double avgmph;
    public static int counter;
    public static double avgmtph;
    public static double curcharge;
    public static double vol; 
    public static double mah = 3.35;
    public static double totalCharge = 44.22;
    public static double maxvol;
    public static double minvol;
    public UI() throws IOException 
    {
    		counter = 0;
    		summph = 0;
    		avgmph = 0;
    		avgmtph = 0;
        curcharge = 0;
        vol = 0;
        maxvol = 16.8;
        minvol = 10;
        milesRemaining = 100;
        createUserInterface();
        fake = new spoof();
        
        can = new CanReader(true);
        can.startPollingLoop(5);
    }

    private void createUserInterface() throws IOException
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
        JLMap.setIcon(new ImageIcon(ImageIO.read(UI.class.getResourceAsStream("mapfinal.jpg"))));
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
    public static void main(String[] args) throws IOException
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

        timer.schedule(gen,0,50);


        Thread daemon = new Thread(() -> {
            int delayMin = 100;
            int delayMax = 1000;
            int minSpeed = 3;
            int maxSpeed = 65;
            try {
                coordsReader = new FileRead(UI.class.getResourceAsStream("coordinates.txt"));
                java.util.List<XYCoordinate> coords = coordsReader.getCoordsList();
                int currIdx = 0;
                while(true) {
                    XYCoordinate c = coords.get(currIdx);
                    JLCar.setBounds((c.x)+400,c.y,10,10);
                    if(mph >= minSpeed)
                    	currIdx++;
                    if(currIdx >= coords.size())
                        currIdx = 0;
                    // PSEUDOCODE
                    //delay = readPotDelay();

                    try { Thread.sleep((long)map(mph, minSpeed, maxSpeed, delayMax, delayMin)); } catch (Exception e) {}
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        daemon.setDaemon(true);
        daemon.start();
    }
    
    
    private static double map(double x, double in_min, double in_max, double out_min, double out_max)
    {
    	return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    

    public static void PsudoValues() {
    	Random rand = new Random();
        fake.update();
        //double batTemp = Math.round((0.0 + (50.0 - 0.0) * rand.nextDouble()) * 100.0) / 100.0; //rangeMin + (rangeMax - rangeMin) * random double
        double MRemain = fake.milesRemaining();
        double Lat_In = 40.581613, Long_In = -98.347368; //random double number
        int SPD = fake.mph();
        int batPercent = fake.Charge();
        potValue = can.getPotVal();
        internalVoltage = can.getPackInstVolts();
        mps = potValue * 0.02867909638; 
        //((-mps/0.04422*100) + 44.22);
        vol = can.getPackInstVolts();
        curcharge = vol*mah;
        SoC = can.getPackSoc();
        //can.getPackSoc();//curcharge/totalCharge; 
        mph = mps*2.23694;
        summph = summph+mph*(1.38E-5); // integrate mph
        counter += 1;
        avgmph = summph/counter;
        avgmtph = (avgmph/2.23694)/3600;
        milesRemaining = milesRemaining - avgmtph*curcharge;
        	
        		
        	//mps*(44.22/((mass*9.8*Cr)+(.5*1.2*Cd*A*mps*mps)*mps)) ;
        //int x = (int)Math.ceil((400+(Long_In-(-98.353633))*49694.39277)-5);
        //int y = (int)Math.ceil((454-((Lat_In-40.575737)*73617.64229))-5);


        if(mph >= 40)
            JLSpeed.setForeground(Color.RED);
          else
            JLSpeed.setForeground(Color.BLACK);

//        if (batPercent <= 20)
//            JLBatPercent.setText(SoC*100 +" % "+"Battery Critical!!");
//        else
            JLBatPercent.setText(String.format("%.1f%%", SoC * 100));

        JLMilesRem.setText(String.format("Miles Remaining: %d", (int)(SoC * 150)));
        JLSpeed.setText(String.format("%.1f mph", mph));
    }
}