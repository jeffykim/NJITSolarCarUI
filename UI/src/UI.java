import javax.swing.*;
import java.awt.*;

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
        frame1.setSize(800,480);
        frame1.setVisible(true);
        frame1.setBackground(Color.DARK_GRAY);

        JLTelemetry = new JLabel("Telemetry");
        JLTelemetry.setBounds(0,0,400,30);
        JLTelemetry.setHorizontalAlignment(SwingConstants.CENTER);
        JLTelemetry.setFont(new Font("Ariel", Font.BOLD,50));
        JLTelemetry.setVisible(true);
        contentPane.add(JLTelemetry);

        JLBatTemp = new JLabel("Battery Temp: ");
        JLBatTemp.setBounds(0,30,100,30);
        JLBatTemp.setHorizontalAlignment(SwingConstants.LEFT);
        JLTelemetry.setFont(new Font("Ariel",Font.PLAIN,10));
        JLBatTemp.setVisible(true);
        contentPane.add(JLBatTemp);

        JLBatV = new JLabel("Battery Volt: ");
        JLBatV.setBounds(0,60,100,30);
        JLBatV.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatV.setFont(new Font("Ariel",Font.PLAIN,10));
        JLBatV.setVisible(true);
        contentPane.add(JLBatV);

        JLBatC = new JLabel("Battery Current: ");
        JLBatC.setBounds(0,90,100,30);
        JLBatC.setHorizontalAlignment(SwingConstants.LEFT);
        JLBatC.setFont(new Font("Ariel",Font.PLAIN,10));
        JLBatC.setVisible(true);
        contentPane.add(JLBatC);

        JLSolarV = new JLabel("Solar Volt: ");
        JLSolarV.setBounds(0,120,100,30);
        JLSolarV.setHorizontalAlignment(SwingConstants.LEFT);
        JLSolarV.setFont(new Font("Ariel",Font.PLAIN,10));
        JLSolarV.setVisible(true);
        contentPane.add(JLSolarV);

        JLSolarC = new JLabel("Solar Current: ");
        JLSolarC.setBounds(200,30,100,30);
        JLSolarC.setHorizontalAlignment(SwingConstants.LEFT);
        JLSolarC.setFont(new Font("Ariel",Font.PLAIN,10));
        JLSolarC.setVisible(true);
        contentPane.add(JLSolarC);

        JLMilesRem = new JLabel("Miles Remain: ");
        JLMilesRem.setBounds(200,60,100,30);
        JLMilesRem.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesRem.setFont(new Font("Ariel",Font.PLAIN,10));
        JLMilesRem.setVisible(true);
        contentPane.add(JLMilesRem);

        JLMilesDriven = new JLabel("Miles Driven: ");
        JLMilesDriven.setBounds(200,120,100,30);
        JLMilesDriven.setHorizontalAlignment(SwingConstants.LEFT);
        JLMilesDriven.setFont(new Font("Ariel",Font.PLAIN,10));
        JLMilesDriven.setVisible(true);
        contentPane.add(JLMilesDriven);

        JLAvgSpd = new JLabel("AVG Speed: ");
        JLAvgSpd.setBounds(200,150,100,30);
        JLAvgSpd.setHorizontalAlignment(SwingConstants.LEFT);
        JLAvgSpd.setFont(new Font("Ariel",Font.PLAIN,10));
        JLAvgSpd.setVisible(true);
        contentPane.add(JLAvgSpd);

        JLMap = new JLabel();
        JLMap.setBounds(400,0,400,480);
        JLMap.setIcon(new ImageIcon("D:\\Other\\UI\\mapfinal.jpg"));
        JLMap.setVisible(true);
        contentPane.add(JLMap);

    }
    public static void main(String[] args)
    {
        frame1 = new JFrame("UI");
        UI application = new UI();
    }
}