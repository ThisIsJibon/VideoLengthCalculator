package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FFprobe {
    public String FFprobeEXE;
    public FFprobe(String FFprobeEXE) {
        super();
        this.FFprobeEXE =FFprobeEXE;
    }
    public void getDuration(String videoInputPath) throws Exception{
        List<String>command=new ArrayList<>();
        //ffprobe -v error -show_entries format=duration -of default=noprint_wrappers=1 fifa.mp4
        command.add(FFprobeEXE);
        command.add(" -v error -show_entries format=duration -of default=noprint_wrappers=1 ");
        command.add(videoInputPath);


        ProcessBuilder processBuilder=new ProcessBuilder(command);
        Process p=processBuilder.start();

        new Thread() {
            public void run() {

                Scanner sc = new Scanner(p.getErrorStream());
                System.out.println("AISI");
                // Find duration;
                Pattern durPattern = Pattern.compile("(?<=Duration: )[^,]*");
                String dur = sc.findWithinHorizon(durPattern, 0);
                System.out.println("dur");
                if (dur == null)
                    throw new RuntimeException("Could not parse duration.");
                String[] hms = dur.split(":");
                double totalSecs = Integer.parseInt(hms[0]) * 3600
                        + Integer.parseInt(hms[1]) *   60
                        + Double.parseDouble(hms[2]);
                System.out.println("Total duration: " + totalSecs + " seconds.");

                // Find time as long as possible.
                Pattern timePattern = Pattern.compile("(?<=time=)[\\d.]*");
                String match;
                while (null != (match = sc.findWithinHorizon(timePattern, 0))) {
                    double progress = Double.parseDouble(match) / totalSecs;
                    System.out.printf("Progress: %.2f%%%n", progress * 100);
                }
            }
        }.start();

    }


    public static void main(String[] args) {
        FFprobe fFprobe=new FFprobe("E:\\Source_Code\\JavaWorld\\VideoLengthCalculator\\src\\sample\\ffprobe.exe");
        try {
            fFprobe.getDuration("E:\\streams\\FIFA 19\\fifa.mp4");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
