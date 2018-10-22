/**
 * Can read and parse class
 * 
 * @version 1.0
 * @author Brian Duemmer & nayeem kamal  
 *  
 *  
 *  */
import java.io.IOException;
import java.util.HashMap;

import javax.xml.bind.DatatypeConverter;

import de.entropia.can.CanSocket;
import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanInterface;
import de.entropia.can.CanSocket.Mode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CanReader
{
	private CanSocket sock;

	private boolean shutdown;

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

	private CanInterface canIF;

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



    public static final Logger log = LogManager.getLogger(CanReader.class.getName());


    /**
 *  Creates CanReader
 * @param startShellModule Starts shell
 * @throws IOException
 */

	public CanReader(boolean startShellModule) throws IOException {
        initialiseHashMaps();
		if(startShellModule) {
			Runtime.getRuntime().exec("sudo sh ~/initCan.sh");
		}
		sock = new CanSocket(Mode.RAW);
		canIF = new CanInterface(sock, "can0");
		sock.bind(canIF);
	}


	/**
	 * Starts a daemon to continuously grab any buffered CAN data
	 * @param pollRate
	 */
	public void startPollingLoop(long pollRate) {
		shutdown = false;
		Thread t = new Thread(() -> {
			while(!shutdown) {
				try {
					CanFrame frame = sock.recv();
					if(frame != null)
						parseFrame(frame);

					if(pollRate > 0) // only delay if needed
						Thread.sleep(pollRate);
				} catch (Exception e) {
					log.error("CAN "+e.getMessage());
				}
			}
		});
		t.setDaemon(true);
		t.setName("CAN Polling");
		t.start();
	}


	/**
	 * Prints a frame to stout
	 * @param frame
	 */
	public void dumpFrame(CanFrame frame) {
		int id = frame.getCanId().getCanId_SFF();
		byte[] data = frame.getData();
		String hexDat = DatatypeConverter.printHexBinary(data);
		System.out.printf("Frame => id:%X data: %s\n", id, hexDat);
	}

	/**
	 * Parses can frames to distinguish values
	 * @param frame input from CAN
	 */

	public void parseFrame(CanFrame frame) {
		int id = frame.getCanId().getCanId_SFF();
		byte[] data = frame.getData();


		switch(id) { // extract the proper frame info, update the respective variables
			case 0x6B0: { // BMS 1 
         /** ID: 0x6B0 Length: 8
         * Byte 0: Pack Current
         * Byte 1: IN USE
         * Byte 2: Pack Inst Voltage 
         * Byte 3: IN USE
         * Byte 4: Pack SOC
         * Byte 5: Relay State 
         * Byte 6: IN USE
         * Byte 7: CRC Checksum 
         */        
				packCurrent = (double)data[0];// Byte 0: Pack Current 
				packInstVolts = (double)data[2]; // Byte 2: Pack 
				packSoc = (double)data[4];
				relayState = (double)data[7];
				break;
			}
			case 0x6B1: { // BMS 2
         /** ID: 0x6B1 Length: 8
         * Byte 0: Pack DCL
         * Byte 1: IN USE
         * Byte 2: IN USE 
         * Byte 3: IN USE
         * Byte 4: High Temperature
         * Byte 5: Low Temperature 
         * Byte 6: Blank
         * Byte 7: CRC Checksum 
         */
				packDCL = (double)data[0];
				packTemp = (double)(((int)data[4] << 8) + data[5]);
				break;
			}
			case 0x6B2: { //BMS 3
				instantVoltage = (double)(((int)data[0] << 8)+data[1]);
				internalResistance = (double)(((int)data[2]<<8)+data[3]);
				openVoltage = (double)(((int)data[4] <<8)+data[5]);
			}
            case 0xCBF:{ // Individual Battery Cell Broadcast
                int cellID = (int) data[0];
                switch (cellID){
                    case 0:{
                        cell_1.put("id",(double) cellID);
                        cell_1.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
                        cell_1.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
                        cell_1.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
                        cells.put(1,cell_1);
                        break;
                    }
                    case 1:{
                        cell_2.put("id",(double) cellID);
                        cell_2.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
                        cell_2.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
                        cell_2.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(2,cell_2);
						break;
                    }
                    case 2:{
                        cell_3.put("id",(double) cellID);
                        cell_3.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
                        cell_3.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
                        cell_3.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(3,cell_3);
						break;
                    }
                    case 3:{
                        cell_4.put("id",(double) 1);
                        cell_4.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
                        cell_4.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
                        cell_4.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(4,cell_4);
						break;
                    }
					case 4:{
						cell_5.put("id",(double) 1);
						cell_5.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_5.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_5.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(5,cell_5);
						break;
					}
					case 5:{
						cell_6.put("id",(double) cellID);
						cell_6.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_6.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_6.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(6,cell_6);
						break;
					}
					case 6:{
						cell_7.put("id",(double) cellID);
						cell_7.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_7.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_7.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(7,cell_7);
						break;
					}
					case 7:{
						cell_8.put("id",(double) cellID);
						cell_8.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_8.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_8.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(8,cell_8);
						break;
					}
					case 8:{
						cell_9.put("id",(double) cellID);
						cell_9.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_9.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_9.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(9,cell_9);
						break;
					}
					case 9:{
						cell_10.put("id",(double) cellID);
						cell_10.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_10.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_10.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(10,cell_10);
						break;
					}
					case 10:{
						cell_11.put("id",(double) cellID);
						cell_11.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_11.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_11.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(11,cell_11);
						break;
					}
					case 11:{
						cell_12.put("id",(double) cellID);
						cell_12.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_12.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_12.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(12,cell_12);
						break;
					}
					case 12:{
						cell_13.put("id",(double) cellID);
						cell_13.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_13.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_13.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(13,cell_13);
						break;
					}
					case 13:{
						cell_14.put("id",(double) cellID);
						cell_14.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_14.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_14.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(14,cell_14);
						break;
					}
					case 14:{
						cell_15.put("id",(double) cellID);
						cell_15.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_15.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_15.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(15,cell_15);
						break;
					}
					case 15:{
						cell_16.put("id",(double) cellID);
						cell_16.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_16.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_16.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(16,cell_16);
						break;
					}
					case 16:{
						cell_17.put("id",(double) cellID);
						cell_17.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_17.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_17.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(17,cell_17);
						break;
					}
					case 17:{
						cell_18.put("id",(double) cellID);
						cell_18.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_18.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_18.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(18,cell_18);
						break;
					}
					case 18:{
						cell_19.put("id",(double) cellID);
						cell_19.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_19.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_19.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(19,cell_19);
						break;
					}
					case 19:{
						cell_20.put("id",(double) cellID);
						cell_20.put("instVoltage",((double)((data[1]<<8) +data[2]))*.0001);
						cell_20.put("internalResistance",((double) ((data[3]<<8)+data[4]))*.01);
						cell_20.put("openVoltage",((double)((data[5]<<8)+data[6]))*.0001);
						cells.put(20,cell_20);
						break;
					}
					default:{
                        System.out.println("Unknown Cell ID"+cellID);
                        log.error("Unknown Cell ID"+cellID);
                    }
                }

                break;
            }
			case 0x100: { // Arduino Gravity Acceleration
				gaccX = ((double)(((int)data[0] << 8) + data[1])) / 256;
				gaccY = ((double)(((int)data[2] << 8) + data[3])) / 256;
				gaccZ = ((double)(((int)data[4] << 8) + data[5])) / 256;
				break;
			}
			case 0x101: { // Arduino Linear Acceleration
				laccX = ((double)(((int)data[0] << 8) + data[1])) / 256;
				laccY = ((double)(((int)data[2] << 8) + data[3])) / 256;
				laccZ = ((double)(((int)data[4] << 8) + data[5])) / 256;
				break;
			}
			case 0x102: { // Arduino Gyro
				gyroX = ((double)(((int)data[0] << 8) + data[1])) / 256;
				gyroY = ((double)(((int)data[2] << 8) + data[3])) / 256;
				gyroZ = ((double)(((int)data[4] << 8) + data[5])) / 256;
				break;
			}
			case 0x103: { // Arduino Sensors
				potVal = ((double)(((int)data[0] << 8) + data[1])) / 32; // it's premultiplied before sending, so don't need to divide by full 256
				break;
			}
			case 0x73:{
				msbRPM = data[0];
				lsbRPM = data[1];
				RPM = (msbRPM << 8)|lsbRPM;
				mph = (int) ((RPM)*.0673092176);
				break;
			}
			default: {
				System.err.println("Unknown Frame ID: " +id);
				log.error("Unknown Frame ID: " +id);
				break;
			}
		}
	}
/**
 * @return string of all parsed data
 */

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CanReader [gaccX=");
		builder.append(gaccX);
		builder.append(", gaccY=");
		builder.append(gaccY);
		builder.append(", gaccZ=");
		builder.append(gaccZ);
		builder.append(", laccX=");
		builder.append(laccX);
		builder.append(", laccY=");
		builder.append(laccY);
		builder.append(", laccZ=");
		builder.append(laccZ);
		builder.append(", gyroX=");
		builder.append(gyroX);
		builder.append(", gyroY=");
		builder.append(gyroY);
		builder.append(", gyroZ=");
		builder.append(gyroZ);
		builder.append(", packCurrent=");
		builder.append(packCurrent);
		builder.append(", packInstVolts=");
		builder.append(packInstVolts);
		builder.append(", packSoc=");
		builder.append(packSoc);
		builder.append(", relayState=");
		builder.append(relayState);
		builder.append(", packDCL=");
		builder.append(packDCL);
		builder.append(", packTemp=");
		builder.append(packTemp);
		builder.append(", potVal=");
		builder.append(potVal);
		builder.append("]");
		return builder.toString();
	}

/**
 * 
 * @return Gravitational acceleration X
 */
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
	/**
	 * 
	 * @return Current CAN interface
	 */
	
	public CanInterface getCanIF() {
		return canIF;
	}

	public double getMPH(){ return mph;}

	public double getRPM(){ return RPM;}

    public boolean shutdown() {
        return shutdown;
    }
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
