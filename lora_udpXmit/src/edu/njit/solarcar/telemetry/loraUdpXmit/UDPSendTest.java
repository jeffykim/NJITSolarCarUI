package edu.njit.solarcar.telemetry.loraUdpXmit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSendTest 
{
	private static final int BUF_SIZE = 1024;
	private static byte[] recvBuf = new byte[BUF_SIZE];
	
	private static final String addr = "localhost";
	private static final int port = 6969;
	private static InetAddress serverIp;
	private static DatagramSocket sock;
	
	
	
	
	public static void main(String[] args) throws Exception {
		BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
		sock = new DatagramSocket();
		serverIp = InetAddress.getByName(addr);
		
		// recieve thread, listens for data coming in via udp and forwards it to the console
		Thread t = new Thread(() -> {
			System.out.println("STARTING READER");
			while(true) {
				try {
					DatagramPacket recvPack = new DatagramPacket(recvBuf, BUF_SIZE);
					sock.receive(recvPack);
					String recvStr = new String(recvPack.getData(), 0, recvPack.getLength());
					System.err.printf("SERVER DATA: %s\n", recvStr);
				} catch (Exception e) {
					e.printStackTrace();
					try { Thread.sleep(1000); } catch(Exception f) {}
				}
			}
		});
		t.setName("Read Thread");
		t.setDaemon(true);
		t.start();
		
		// listen on user input
		System.out.println("STARTING WRITER");
		while(true) {
			String line = userIn.readLine();
			sendData(line);
		}
	}
	
	
	/**
	 * Writes the data in {@code dat} to the given UDP socket
	 * @param dat
	 * @throws IOException
	 */
	private static void sendData(String dat) throws IOException {
		DatagramPacket pack = new DatagramPacket(dat.getBytes(), dat.length(), serverIp, port);
		sock.send(pack);
	}

}
