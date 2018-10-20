
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@SuppressWarnings("unused")
public class UDPReceiver extends Thread
{
	private static final int BUF_SIZE = 1024;
	private static byte[] recvBuf = new byte[BUF_SIZE];

	private String addr; //"solarcar-telemetry-raspi.local"
	private static final int port = 7888;
	private static InetAddress serverIp;
	private static DatagramSocket sock;

    // gravity acceleration
    private double gaccX;
    private double gaccY;
    private double gaccZ;

    // linear acceleration
    private double laccX;
    private double laccY;
    private double laccZ;

    //Battery cell broadcast
    private double instantVoltage;
    private double internalResistance;
    private double openVoltage;
    // gyroscope
    private double gyroX;
    private double gyroY;
    private double gyroZ;

    //Misc. Arduino
    private double potVal;

    // BMS data
    private double packCurrent;
    private double packInstVolts;
    private double packSoc;
    private double relayState;
    private double packDCL;
    private double packTemp;

    private int msbRPM;
    private int lsbRPM;
    private double RPM;
    private int mph;

    //Cells
    public HashMap<Integer ,HashMap<String,Double>> cells;
    private HashMap<String, Double> cell_1;
    private HashMap<String, Double> cell_2;
    private HashMap<String, Double> cell_3;
    private HashMap<String, Double> cell_4;
    private HashMap<String, Double> cell_5;
    private HashMap<String, Double> cell_6;
    private HashMap<String, Double> cell_7;
    private HashMap<String, Double> cell_8;
    private HashMap<String, Double> cell_9;
    private HashMap<String, Double> cell_10;
    private HashMap<String, Double> cell_11;
    private HashMap<String, Double> cell_12;
    private HashMap<String, Double> cell_13;
    private HashMap<String, Double> cell_14;
    private HashMap<String, Double> cell_15;
    private HashMap<String, Double> cell_16;
    private HashMap<String, Double> cell_17;
    private HashMap<String, Double> cell_18;
    private HashMap<String, Double> cell_19;
    private HashMap<String, Double> cell_20;



    public static final Logger log = LogManager.getLogger(UDPReceiver.class.getName());

	public UDPReceiver()  {
        addr = "127.0.0.1";
        try {
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			sock = new DatagramSocket(port);
			serverIp = InetAddress.getByName(addr);
		} catch (Exception e) {
            log.error("UDPR: "+ e.getMessage());
		}

	}
    public UDPReceiver(String address)  {
	    addr = address;
        try {
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            sock = new DatagramSocket(port);
            serverIp = InetAddress.getByName(addr);
        } catch (Exception e) {
            log.error("UDPR: "+ e.getMessage());
        }

    }

	/**
	 * 
	 * 
	 * @param dat String to be sent over lora
	 * @throws IOException 
	 */
	private void sendData(String dat) throws IOException {
		DatagramPacket pack = new DatagramPacket(dat.getBytes(), dat.length(), serverIp, port);
		sock.send(pack);
	}


	public void run(){
				try{
					sendData("Starting"); // init the remote IP
					byte[] receiveData = new byte[256];
					byte[] sendData = new byte[256];

					while(true)
					{
						DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						sock.receive(receivePacket);
						String sentence = new String( receivePacket.getData());
						System.out.println("RECEIVED: " + sentence);
                        //*****************************
                        //Parse Data
                        //*****************************
						InetAddress IPAddress = receivePacket.getAddress();

					}
				}
				catch (Exception e){
                    log.error("UDPR: "+ e.getMessage());				
                }

	}
    private void parseFrame(String data) {

    }

    public double getGaccX() {
        return gaccX;
    }
    /**
     *
     * @return gravitational acceleration Y
     */

    public double getGaccY() {
        return gaccY;
    }

    /**
     *
     * @return Gravitational acceleration Z
     */
    public double getGaccZ() {
        return gaccZ;
    }

    /**
     *
     * @return linear acceleration X
     */
    public double getLaccX() {
        return laccX;
    }

    /**
     *
     * @return linear acceleration Y
     */
    public double getLaccY() {
        return laccY;
    }
    /**
     *
     * @return linear acceleration Z
     */

    public double getLaccZ() {
        return laccZ;
    }

    /**
     *
     * @return Gyroscope X
     */

    public double getGyroX() {
        return gyroX;
    }

    /**
     *
     * @return Gyroscope Y
     */
    public double getGyroY() {
        return gyroY;
    }
    /**
     *
     * @return Gyroscope Z
     */

    public double getGyroZ() {
        return gyroZ;
    }

    /**
     *
     * @return state of acceleration pedal
     */
    public double getPotVal() {
        return potVal;
    }

    /**
     *
     * @return Pack Current
     */

    public double getPackCurrent() {
        return packCurrent;
    }

    /**
     *
     * @return Pack Instant Voltage
     */
    public double getPackInstVolts() {
        return packInstVolts;
    }

    /**
     *
     * @return Pack SoC
     */
    public double getPackSoc() {
        return packSoc;
    }

    /**
     *
     * @return RelayState
     */
    public double getRelayState() {
        return relayState;
    }

    /**
     *
     * @return Pack DCL
     */
    public double getPackDCL() {
        return packDCL;
    }
    /**
     *
     * @return Temperature
     */

    public double getPackTemp() {
        return packTemp;
    }
    /**
     *
     * @return internal voltage
     */
    public double getInternalVoltage() {
        return this.instantVoltage;
    }
    /**
     *
     * @return internal resistance
     */
    public double getInternalResistance() {
        return this.internalResistance;
    }
    /**
     *
     * @return open voltage
     */
    public double getOpenVoltage() {
        return this.openVoltage;
    }

    public double getMPH(){ return mph;}

    public double getRPM(){ return RPM;}

    private void initialiseHashMaps(){
        cell_1 = new HashMap<>(4);
        cell_2 = new HashMap<>(4);
        cell_3 = new HashMap<>(4);
        cell_4 = new HashMap<>(4);
        cell_5 = new HashMap<>(4);
        cell_6 = new HashMap<>(4);
        cell_7 = new HashMap<>(4);
        cell_8 = new HashMap<>(4);
        cell_9 = new HashMap<>(4);
        cell_10 = new HashMap<>(4);
        cell_11 = new HashMap<>(4);
        cell_12 = new HashMap<>(4);
        cell_13 = new HashMap<>(4);
        cell_14 = new HashMap<>(4);
        cell_15 = new HashMap<>(4);
        cell_16 = new HashMap<>(4);
        cell_17 = new HashMap<>(4);
        cell_18 = new HashMap<>(4);
        cell_19 = new HashMap<>(4);
        cell_20 = new HashMap<>(4);
    }

}

