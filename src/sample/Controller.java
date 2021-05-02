package sample;

import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.coremedia.iso.IsoFile;



public class Controller {
    public AnchorPane mainAnchorPane;
    public static int videoCnt=0;
    public static double totalSec=0;

    public void selectDirectoryButtonAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        discord=0;
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage primaryStage=(Stage) mainAnchorPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        doVideoCalculation(selectedDirectory);
        System.out.println("total vids "+videoCnt);
        System.out.println("total time "+discord);
       // Main.codeforces=discord;
    }
    public static double discord=0;

    public void doVideoCalculation(File path) throws InterruptedException, IOException {
        File files[]= path.listFiles();

        for(int i=0;i<files.length;i++){
            File curFile=files[i];
            if(curFile.isDirectory()==true){
                doVideoCalculation(curFile);
            }
            if(isVideoFile(curFile.toString())){
                videoCnt++;
                String[] arr={curFile.toString()};
                discord+= FFMpeg.doIT(arr);
            }
        }
    }


    public boolean isVideoFile(String path) {
        //System.out.println(path);
        String[] extensions={"mp4","mkv","avi","3gp","m4p"};
        for (String x: extensions){
            if(path.endsWith(x))
                return true;
        }
        return false;
    }


}
