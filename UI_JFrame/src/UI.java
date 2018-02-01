import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BrokenBarrierException;

public class UI extends JFrame
{
    static JFrame frame1;
    private JLabel JLTelemetry;
    private JLabel JLBatTemp;
    private JLabel JLBatV;
    private JLabel JLBatC;
    private JLabel JLSolarV;
    private JLabel JLSolarC;
    private JLabel JLMilesRem;
    private JLabel JLMilesDriven;
    private JLabel JLAvgSpd;
    private JLabel JLSpeedometer;
    private JLabel JLSpeed;
    private JLabel JLMap;

    public UI(){createUserInterface();}

    private void createUserInterface()
    {
        Container contentPane = frame1.getContentPane();
        contentPane.setLayout(null);

        frame1.setTitle("UI");
        frame1.setSize(800,500);
        frame1.setVisible(true);
        frame1.setBackground(Color.DARK_GRAY);
        //frame1.setResizable(false);

        JLTelemetry = new JLabel("Telemetry Data");
        JLTelemetry.setBounds(0,0,400,30);
        JLTelemetry.setHorizontalAlignment(SwingConstants.CENTER);
        JLTelemetry.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLTelemetry.setFont(new Font("Ariel",Font.BOLD,25));
        contentPane.add(JLTelemetry);

        JLBatTemp = new JLabel("Battery Temp: " + 0 + "F");
        JLBatTemp.setBounds(0,30,100,55);
        JLBatTemp.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatTemp.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLTelemetry.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLBatTemp);

        JLBatV = new JLabel("Battery Volt: " + 0 + "V");
        JLBatV.setBounds(0,85,100,55);
        JLBatV.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatV.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLBatV.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLBatV);

        JLBatC = new JLabel("Battery Current: "+ 0 + "A");
        JLBatC.setBounds(0,140,100,55);
        JLBatC.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatC.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLBatC.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLBatC);

        JLSolarV = new JLabel("Solar Volt: "+ 0 + "V");
        JLSolarV.setBounds(0,195,100,55);
        JLSolarV.setHorizontalAlignment(SwingConstants.LEFT);
        JLSolarV.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLSolarV.setFont(new Font("Ariel",Font.PLAIN,10));
        JLSolarV.setVisible(true);
        contentPane.add(JLSolarV);

        JLSolarC = new JLabel("Solar Current: " + 0 + "A");
        JLSolarC.setBounds(200,30,100,55);
        JLSolarC.setHorizontalAlignment(SwingConstants.LEFT);
        JLSolarC.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLSolarC.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLSolarC);

        JLMilesRem = new JLabel("Miles Remain: " + 0 + " miles");
        JLMilesRem.setBounds(200,85,100,55);
        JLMilesRem.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesRem.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLMilesRem.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLMilesRem);

        JLMilesDriven = new JLabel("Miles Driven: "+ 0 + " miles");
        JLMilesDriven.setBounds(200,140,100,55);
        JLMilesDriven.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesDriven.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLMilesDriven.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLMilesDriven);

        JLAvgSpd = new JLabel("AVG Speed: "+ 0 + " mph");
        JLAvgSpd.setBounds(200,195,100,55);
        JLAvgSpd.setHorizontalAlignment(SwingConstants.LEFT);
        JLAvgSpd.setBorder(BorderFactory.createLineBorder(Color.black,1));
        JLAvgSpd.setFont(new Font("Ariel",Font.PLAIN,10));
        contentPane.add(JLAvgSpd);

        JLMap = new JLabel();
        JLMap.setBounds((frame1.getWidth()/2),0,776,458);
        JLMap.setIcon(new ImageIcon("D:\\Other\\UI\\mapfinal.jpg"));
        JLMap.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        contentPane.add(JLMap);

        JLSpeedometer = new JLabel("Speedometer");
        JLSpeedometer.setBounds(0,250,400,30);
        JLSpeedometer.setHorizontalAlignment(SwingConstants.CENTER);
        JLSpeedometer.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        JLSpeedometer.setFont(new Font("Ariel",Font.BOLD,25));
        contentPane.add(JLSpeedometer);

        JLSpeed = new JLabel(0 + " mph");
        JLSpeed.setBounds(100,250,200,220);
        JLSpeed.setHorizontalAlignment(SwingConstants.CENTER);
        JLSpeed.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        JLSpeed.setFont(new Font("Ariel",Font.BOLD,35));
        contentPane.add(JLSpeed);


    }
    public static void main(String[] args)
    {
        frame1 = new JFrame("UI");
        UI application = new UI();
    }
}