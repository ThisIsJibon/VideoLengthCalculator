package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FFMpeg {

    public static double timeCnt=0;

    public String FFMpegEXE;

    public FFMpeg(String FFMpegEXE) {

        super(); codechef=0;
        this.FFMpegEXE =FFMpegEXE;
    }
    static double codechef=0;
    public double getLen(String videoInputPath) throws Exception{

        List<String> command=new ArrayList<>();
        //ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1 fifa.mp4
        command.add(FFMpegEXE);
        command.add("-i");
        command.add(videoInputPath);



        ProcessBuilder processBuilder=new ProcessBuilder(command);
        Process p=processBuilder.start();

        Thread t =new Thread() {
            public void run() {

                Scanner sc = new Scanner(p.getErrorStream());
                // Find duration
                Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
                String dur = sc.findWithinHorizon(durPattern, 0);
                if (dur == null)
                    throw new RuntimeException("Could not parse duration.");
                String[] hms = dur.split(":");
                double totalSecs = Integer.parseInt(hms[0]) * 3600
                        + Integer.parseInt(hms[1]) *   60
                        + Double.parseDouble(hms[2]);

                //System.out.println("Total duration: " + totalSecs + " seconds.");

                codechef+=totalSecs;
            }
        }; t.start();
        t.join();
       // System.out.println("debug cc"+codechef);
        return timeCnt;

    }
    public static double tempcnt;


    public static double  doIT(String[] args){

        FFMpeg ffmpeg=new FFMpeg("E:\\Source_Code\\JavaWorld\\VideoLengthCalculator\\src\\sample\\ffmpeg.exe");
        try {
            String curString=args[0];
            ffmpeg.getLen(curString);
          //  System.out.println("final deb "+tempcnt);
        } catch (Exception e){
            e.printStackTrace();
        }
      //  System.out.println("int doit "+ ffmpeg.codechef);
        return ffmpeg.codechef;
    }

}
