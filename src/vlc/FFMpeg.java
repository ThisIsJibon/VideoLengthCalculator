package vlc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FFMpeg {


    public String FFMpegEXE;

    public FFMpeg(String FFMpegEXE) {

        super(); overallTimeCnt =0;
        this.FFMpegEXE =FFMpegEXE;
    }
    static double overallTimeCnt =0;

    public double getVideoLength(String videoInputPath) throws Exception{

        List<String> command=new ArrayList<>();

        command.add(FFMpegEXE);
        command.add("-i");
        command.add(videoInputPath);

        ProcessBuilder processBuilder=new ProcessBuilder(command);
        Process p=processBuilder.start();

        Thread t =new Thread() {
            public void run() {

                Scanner sc = new Scanner(p.getErrorStream());
                Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
                String dur = sc.findWithinHorizon(durPattern, 0);
                if (dur == null)
                    throw new RuntimeException("Could not parse duration.");
                String[] hms = dur.split(":");
                double a1=0,a2=0,a3=0;
                try{
                        a1=Double.parseDouble(hms[0]);
                        a2=Double.parseDouble(hms[1]);
                        a3=Double.parseDouble(hms[2]);
                } catch (NumberFormatException e){
                         System.out.println("NumberFormatException occur");
                }
                double totalSecs = a1 * 3600
                        + a2 *   60
                        + a3;

                overallTimeCnt +=totalSecs;
            }
        }; t.start();
        t.join();
        return 0;

    }

    public static double  doIT(String[] args){

        String ffmpegpath = new File("src/res/exe/ffmpeg.exe")
                .getAbsolutePath();

        FFMpeg ffmpeg=new FFMpeg(ffmpegpath);
        try {

            String curFilePathString=args[0];
            ffmpeg.getVideoLength(curFilePathString);

        } catch (Exception e){
            e.printStackTrace();
        }
        return ffmpeg.overallTimeCnt;
    }

}
