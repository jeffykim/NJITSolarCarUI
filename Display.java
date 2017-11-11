import java.applet.Applet;
import java.awt.*;
import java.awt.Graphics;
import java.util.Map;
import java.applet.*;
import java.awt.*;
import java.net.*;
import java.io.IOException.*;
import java.util.*;
import java.io.File;
import java.io.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Created by nayeemkamal on 11/11/17.
 */
public class Display extends Applet{
    HashMap<Coordinate,Coordinate> coordMap = new HashMap<Coordinate,Coordinate>();

    URL img;
    try{
        File f = new File("/Users/nayeemkamal/Desktop/telemetry/ScreenShot2017-11-09at1.55.55PM.png");
        BufferedImage image = new BufferedImage(412,480,BufferedImage.TYPE_INT_ARGB);

        image = ImageIO.read(f);
        System.out.println("read")

    }catch(IOException e){

    }

    {
        try {
            img = new URL("/Users/nayeemkamal/Desktop/telemetry/ScreenShot2017-11-09at1.55.55PM.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    UIMap map = new UIMap(img,coordMap);

    public void init(){
        setSize(412,480);




    }
    public void paint(Graphics g){
        g.drawImage(map.getImg(),0,0,this);

    }
}
