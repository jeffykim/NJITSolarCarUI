import javax.swing.*;
import java.awt.*;

public class UI extends JFrame
{
    static JFrame frame1;

    public UI(){createUserInterface();}

    private void createUserInterface()
    {
        Container contentPane = frame1.getContentPane();
        contentPane.setLayout(null);

        frame1.setTitle("UI");
        frame1.setSize(800,480);
        frame1.setVisible(true);

    }
    public static void main(String[] args)
    {
        frame1 = new JFrame("UI");
        UI application = new UI();
    }
}
