package vlc;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitingPageController implements Initializable {

    public ProgressBar waitingProgressBar;
    public VBox waitingPageVBox;
    public  AnchorPane waitingPageAnchorPane;
    public static Label waitingPageLabel;

    public static void doUIMod(){
        waitingPageLabel.setText("ready");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
