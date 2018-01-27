package UI;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
public class Display extends Application{
	public static void main(String[]args) {
		Application.launch(args);
		
	}
	public void start(Stage stage) throws Exception{
		WebView myWebView = new WebView();
		WebEngine engine = myWebView.getEngine();
		URL url = this.getClass().getResource("UILAYOUTFINAL.html");
		engine.load(url.toString());
		//engine.load("https://google.com");
		VBox root = new VBox();
		root.getChildren().addAll(myWebView);
		
		Scene scene = new Scene(root, 783, 457);
		stage.setScene(scene);
		
		stage.show();
	}
}
