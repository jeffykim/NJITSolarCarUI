import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class GPS extends Thread {
	
    private static ErrorLog logger;

	private static double lat;
	private static double lon;
	private static LinkedList<XYCoordinate> coords;
	private static Scanner sc;
	
	public GPS() {
		coords = new LinkedList<XYCoordinate>();
		try {
			sc = new Scanner(new File("src/coordinates.txt"));
		} catch (FileNotFoundException e) {
			ErrorLog logger = new ErrorLog();
			logger.log(e);
		}
	}
	
	public double getLat() {
		return GPS.lat;
	}
	public double getLon() {
		return GPS.lon;
	}
	
	
	public void run() {
		
	}
	
	

}
