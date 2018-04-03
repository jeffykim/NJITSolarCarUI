public class gpsTest {
    public static void main(String [] args){
        GPS gps = new GPS("/media/pi/TETA/GPS/src/GPS Reader/gpsd.py");
        while(true)
        System.out.println(gps.readGPS());
    }
}
