
import java.io.BufferedReader;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SuppressWarnings("unused")
public class UDPReciever extends Thread
{
	private static final int BUF_SIZE = 1024;
	private static byte[] recvBuf = new byte[BUF_SIZE];

	private static final String addr = "solarcar-telemetry-raspi.local";
	private static final int port = 7888;
	private static InetAddress serverIp;
	private static DatagramSocket sock;

	public static void main(String[] args) {
		UDPReciever r = new UDPReciever();
		r.run();
	}

	public UDPReciever()  {


		try {

			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
			sock = new DatagramSocket(port);
			serverIp = InetAddress.getByName(addr);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * 
	 * @param dat String to be sent over lora
	 * @throws IOException 
	 */
	private static void sendData(String dat) throws IOException {
		DatagramPacket pack = new DatagramPacket(dat.getBytes(), dat.length(), serverIp, port);
		sock.send(pack);
	}


	public void run(){


		try{
			sendData("fuck you"); // init the remote IP
			byte[] receiveData = new byte[256];
			byte[] sendData = new byte[256];

			while(true)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				sock.receive(receivePacket);
				String sentence = new String( receivePacket.getData());
				System.out.println("RECEIVED: " + sentence);
				InetAddress IPAddress = receivePacket.getAddress();

			}
		}catch (Exception e){
		}
		
		
		
		
	}
}
