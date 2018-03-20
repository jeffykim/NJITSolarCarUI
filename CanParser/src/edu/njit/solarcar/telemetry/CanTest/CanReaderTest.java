package edu.njit.solarcar.telemetry.CanTest;

public class CanReaderTest 
{
	public static void main(String[] args) throws Exception 
	{
		CanReader r = new CanReader(true);
		r.startPollingLoop(5);
//		Thread t = new Thread(() -> {
		while(true)
		{
			System.out.println(r);
			try { Thread.sleep(500); } catch (Exception e) {}
		}
//		});
//		t.setDaemon(on);
	}
}
