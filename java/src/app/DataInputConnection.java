package app;

import java.util.Map;

/**
 * Represents an abstract connecton that we recieve
 * status packets from
 * @author Duemmer
 *
 */
public abstract class DataInputConnection 
{
	/** Represents the interface to the GUI */
	private DataInputController guiController;
	
	/**
	 * Recieves the current (valid) packet
	 * @return 
	 */
	public abstract Map<String, String> currFrame();
	
	/**
	 * This callback function will run when a new (valid)
	 * packet is ready. 
	 * @param callback
	 */
	public abstract void setOnPacketGet(DataInputController callback);
	
	
	/**
	 * Attempts to open a connection to recieve data
	 * @return true if successful, false otherwise
	 */
	public abstract boolean connect();
}
