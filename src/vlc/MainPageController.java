package vlc;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class MainPageController {
    public AnchorPane mainAnchorPane;
    public static int videoCnt=0;
    public static double totalSecCnt=0;
    public CheckBox isSubDirIncluded;


    public void selectDirectoryButtonAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        totalSecCnt=0;

        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage primaryStage=(Stage) mainAnchorPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        Parent waitingRoot= FXMLLoader.load(getClass().getResource("WaitingPage.fxml"));
       // WaitingPageController.progressBarInitialize();
        primaryStage.setScene(new Scene(waitingRoot));
        try {
            doVideoCalculation(selectedDirectory);
        } catch (Exception e){
           // e.printStackTrace();

        } finally{
            System.out.println("total videos "+videoCnt);
            System.out.println("total time in secs "+totalSecCnt);
        }



    }


    public void doVideoCalculation(File path) throws InterruptedException, IOException {
        File files[]= path.listFiles();

        for(int i=0;i<files.length;i++){
            File curFile=files[i];
            if(curFile.isDirectory()==true && isSubDirIncluded.isSelected()){
                doVideoCalculation(curFile);
            }
            if(isVideoFile(curFile.toString())){
                videoCnt++;
                String[] arr={curFile.toString()};
                totalSecCnt+= FFMpeg.doIT(arr);
            }
        }
    }


    public boolean isVideoFile(String path) {
        //System.out.println(path);
        String[] extensions={"mp4","mkv","avi","3gp","m4p","webm","gif","gifv",".amv","m4v"};
        for (String x: extensions){
            if(path.endsWith(x))
                return true;
        }
        return false;
    }


}
