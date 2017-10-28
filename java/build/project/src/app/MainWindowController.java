package app;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class MainWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private WebView webview;
    
    @FXML
    void addText(ActionEvent event) {
    	webview.getEngine().executeScript("document.write(\"<p>foo</p>\");");
    }

    @FXML
    void initialize() {
        assert webview != null : "fx:id=\"webview\" was not injected!";
        webview.getEngine().setUserStyleSheetLocation(getClass().getResource("application.css").toString());
        webview.getEngine().load(getClass().getResource("test.html").toString());
    }
}
