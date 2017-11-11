import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

/**
 * Created by nayeemkamal on 11/9/17.
 */
public class UIMap {
    private Image img;
    private Map<> coordinateMap;
    public Map(URL imgURL, Map coordinates){
        try{
            img = ImageIO.read(imgURL);
        }catch(IOException e){

        }

        coordinateMap = coordinates;

    }

    public Image getImg() {
        return img;
    }



}
