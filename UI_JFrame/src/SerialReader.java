import jssc.SerialPort;
import jssc.SerialPortException;
public class SerialReader {
	private static SerialPort serialPort; 
	public SerialReader(String port) {
		SerialReader.serialPort = new SerialPort(port);
		try{
			serialPort.openPort();
			serialPort.setParams(9600, 8, 1, 0);
		}catch(SerialPortException ex) {
			 System.out.println(ex);
		}
		//= new SerialPort("/dev/tty.usbmodem14511");

	}

	public static void open() {
		
		
	}
	public static void close() {
		try {
			serialPort.closePort();
		}catch(SerialPortException ex) {
			System.out.println(ex);
		}
	}
	public String readHex() {
	try{
		String hex =serialPort.readHexString();
		if(hex!=null) {
		//int ret = Integer.parseInt(hex,16);
		
		return ""+hex;}
		return "";
	}catch(SerialPortException ex) {
		return ""+ex;
	}
	
	}
//	public int[] readIntArr() {
//		try{
//			return serialPort.readIntArray();
//		}catch(SerialPortException ex) {
//			System.out.println(ex);
//		}
//		
//		}
//	public static String readStr() {
//		try {
//			return this.serialPort
//		}
//	}

			
		

	

}
