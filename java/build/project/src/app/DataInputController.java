package app;

import java.util.Map;

import javafx.scene.web.WebView;



/**
 * Represents a control interface from the back end for the UI
 * @author Duemmer
 *
 */
public interface DataInputController {
	/**
	 * This will run whenver a new packet of data is ready to be drawn to the UI
	 * @param data the key-value pairs of individual data points
	 */
	public void onPacketReady(Map<String, String> data);
	
	
	/**
	 * Resets the UI to an initialized default state
	 */
	public void reset(WebView wv);
	
}
