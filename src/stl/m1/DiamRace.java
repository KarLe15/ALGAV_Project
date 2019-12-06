package stl.m1;

import stl.m1.projet.TestsRunner;
import supportGUI.FramedDiamRace;
import supportGUI.Variables;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DiamRace extends supportGUI.DiamRace {
    private static int nbPoints = 100;
    private static int width = 0;
    private static int height = 0;
    private static String title = "Diameter Racer";
    private static String filename = "input.points";
    private static FramedDiamRace framedGUI;

    public static void main(String[] args) {
//        List<String> newA = Arrays.asList(args);
//        run((String[]) newA.toArray());
        TestsRunner.runTestOnVaroumasSamples();
//        run(args);
    }


    public static void run(String[] args) {
        int i = 0;

        label50:
        while(true) {
            if (i >= args.length) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        DiamRace.framedGUI = new FramedDiamRace(DiamRace.width, DiamRace.height, DiamRace.title, DiamRace.nbPoints);
                    }
                });
                synchronized(Variables.lock) {
                    try {
                        Variables.lock.wait();
                    } catch (InterruptedException var4) {
                        var4.printStackTrace();
                    }
                }

                readFile();
                return;
            }

            if (args[i].charAt(0) == '-') {
                if (args[i + 1].charAt(0) == '-') {
                    System.err.println("Option " + args[i] + " expects an argument but received none");
                    return;
                }

                String var2;
                switch((var2 = args[i]).hashCode()) {
                    case -2118335772:
                        if (var2.equals("-nbPoints")) {
                            try {
                                nbPoints = Integer.parseInt(args[i + 1]);
                            } catch (Exception var6) {
                                System.err.println("Invalid argument for option " + args[i] + ": Integer type expected");
                                return;
                            }

                            ++i;
                            break;
                        }
                    default:
                        break label50;
                }
            }

            ++i;
        }

        System.err.println("Unknown option " + args[i]);
    }

    public static void readFile() {
        ArrayList points = new ArrayList();

        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

            try {
                String line;
                while((line = input.readLine()) != null) {
                    String[] coordinates = line.split("\\s+");
                    points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                }

                framedGUI.drawPoints(points);
                synchronized(Variables.lock2) {
                    Variables.lock2.notify();
                }
            } catch (IOException var16) {
                System.err.println("Exception: interrupted I/O.");
            } finally {
                try {
                    input.close();
                } catch (IOException var14) {
                    System.err.println("I/O exception: unable to close " + filename);
                }

            }
        } catch (FileNotFoundException var18) {
            System.err.println("Input file not found.");
        }

    }


}