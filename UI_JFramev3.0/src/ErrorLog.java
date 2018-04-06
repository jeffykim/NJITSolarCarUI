import java.io.*;
import java.util.*;
public class ErrorLog {
	private static GregorianCalendar date;
	//private static File log;
	private static PrintWriter logger;
	
	
	public ErrorLog() {
	
			date = new GregorianCalendar();
			try {
				logger = new PrintWriter(date.getTime()+".txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	
	public void close() {
		logger.close();
	}

	public void log(Exception e) {
		for(StackTraceElement i : e.getStackTrace()) {
			logger.write(i.toString());
		}		
	}
	
	
}
