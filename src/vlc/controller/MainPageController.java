package vlc.controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import vlc.FFMpeg;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainPageController extends Application implements Initializable {
    public AnchorPane mainAnchorPane;
    public static int videoCnt=0;
    public static double totalSecCnt=0;
    public CheckBox isSubDirIncluded;
    public VBox mainPageVBox;
    public HBox videoCntHbox;
    public HBox durationHBox;
    public Label helloLabel;
    public Button selectDirectoryButton;
    public Label videoDurationLabel;
    public Label videoCntLabel;
    public Button goBackButton;
    public ImageView backImageView;
    public ImageView directoryImageView;


    public void selectDirectoryButtonAction(ActionEvent actionEvent) throws IOException, InterruptedException {

        totalSecCnt=0;
        modifyUI();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage primaryStage=(Stage) mainAnchorPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
        primaryStage.setTitle("please wait a while!");

        try {
            doVideoCalculation(selectedDirectory);
        } catch (Exception e){
           // e.printStackTrace();
        } finally{

            primaryStage.setTitle("VideoLengthCalculator - VLC");

            String s=Integer.toString(videoCnt);
            videoCntLabel.setText(s);
            s=Double.toString(totalSecCnt);

            String formattedString="";
            String hourVal,minVal,secVal;

            hourVal=Integer.toString((int)totalSecCnt/(60*60));

            minVal=Integer.toString((int)(totalSecCnt%3600)/60);

            secVal=Integer.toString((int)totalSecCnt%60);

            if(!hourVal.equals("0")){
                formattedString+=hourVal;
                formattedString+=" hours : ";
            }
            if(!minVal.equals("0")){
                formattedString+=minVal;
                formattedString+=" minutes : ";
            }
            if(!secVal.equals("0")){
                formattedString+=secVal;
                formattedString+=" seconds : ";
            }
            int len=formattedString.length();
            if(formattedString.endsWith(" ")) formattedString=formattedString.substring(0,len-2);
            videoDurationLabel.setText(formattedString);

            if(videoCnt==0){
                videoDurationLabel.setText("No video files found");
            }
        }

    }

    public void modifyUI() throws IOException{

        mainPageVBox.getChildren().addAll(videoCntHbox,durationHBox,goBackButton);

        videoCntHbox.setVisible(true);
        durationHBox.setVisible(true);
        goBackButton.setVisible(true);

        mainPageVBox.getChildren().remove(0,3);
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

        String[] extensions={".mp4",".mkv",".avi",".3gp",".m4p",".webm",".gif",".gifv",".amv",".m4v","mov","wmv","flv",
                             ".MP4",".MKV",".AVI",".3GP",".M4P",".WEBM",".GIF",".GIFV",".AMV",".M4V","MOV","WMV","FLV",
                            };
        for (String x: extensions){
            if(path.endsWith(x))
                return true;
        }
        return false;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        videoCntHbox.setVisible(false);
        durationHBox.setVisible(false);
        goBackButton.setVisible(false);
        File iconFile=new File("src/res/icon/folderIcon.png");
        directoryImageView.setImage(new Image(iconFile.toURI().toString()));
        iconFile=new File("src/res/icon/backIcon.png");
        backImageView.setImage(new Image(iconFile.toURI().toString()));
        mainPageVBox.getChildren().remove(3,6);

    }

    public void goBackButtonAction(ActionEvent actionEvent) throws IOException {
        Stage curStage=(Stage) mainAnchorPane.getScene().getWindow();
        videoCntLabel.setText("0");
        videoCnt=0;
        totalSecCnt=0;
        curStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage primaryStage=new Stage();
        primaryStage.setTitle("VideoLengthCalculator - VLC");
        primaryStage.setScene(new Scene(root, 600, 450));

        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
