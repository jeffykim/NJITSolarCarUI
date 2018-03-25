package edu.njit.solarcar.telemetry.CanTest;
import java.io.IOException;



import javax.xml.bind.DatatypeConverter;

import de.entropia.can.CanSocket;
import de.entropia.can.CanSocket.CanFrame;
import de.entropia.can.CanSocket.CanInterface;
import de.entropia.can.CanSocket.Mode;

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
	private CanInterface canIF;



	public CanReader(boolean startShellModule) throws IOException {
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
//					qshutdown = true;
					e.printStackTrace();
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


	public void parseFrame(CanFrame frame) {
		int id = frame.getCanId().getCanId_SFF();
		byte[] data = frame.getData();

		switch(id) { // extract the proper frame info, update the respective variables
			case 0x6B0: { // BMS 1
				packCurrent = ((double)(((int)data[0] << 8) + data[1])) / 10;
				packInstVolts = ((double)(((int)data[2] << 8) + data[3])) / 10;
				packSoc = /*((double)(((int)data[4] << 8) + data[5])) / 200;*/ ((double)data[4]) / 200;
				relayState = (double)data[7];
				break;
			}
			case 0x6B1: { // BMS 2
				packDCL = (double)data[0];
				packTemp = (double)(((int)data[4] << 8) + data[5]);
				break;
			}
			case 0x6B2: { //BMS 3
				instantVoltage = (double)(((int)data[0] << 8)+data[1]);
				internalResistance = (double)(((int)data[2]<<8)+data[3]);
				openVoltage = (double)(((int)data[4] <<8)+data[5]);
				
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
			default: {
				System.err.println("Unknown Frame ID: " +id);
				break;
			}
		}
	}


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


	public double getGaccX() {
		return gaccX;
	}


	public double getGaccY() {
		return gaccY;
	}


	public double getGaccZ() {
		return gaccZ;
	}


	public double getLaccX() {
		return laccX;
	}


	public double getLaccY() {
		return laccY;
	}


	public double getLaccZ() {
		return laccZ;
	}


	public double getGyroX() {
		return gyroX;
	}


	public double getGyroY() {
		return gyroY;
	}


	public double getGyroZ() {
		return gyroZ;
	}


	public double getPotVal() {
		return potVal;
	}


	public double getPackCurrent() {
		return packCurrent;
	}


	public double getPackInstVolts() {
		return packInstVolts;
	}


	public double getPackSoc() {
		return packSoc;
	}


	public double getRelayState() {
		return relayState;
	}


	public double getPackDCL() {
		return packDCL;
	}


	public double getPackTemp() {
		return packTemp;
	}
	public double getInternalVoltage() {
		return this.instantVoltage;
	}
	public double getInternalResistance() {
		return this.internalResistance;
	}
	public double getOpenVoltage() {
		return this.openVoltage;
	}
	
	public CanInterface getCanIF() {
		return canIF;
	}


}
