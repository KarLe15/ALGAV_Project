package stl.m1.projet;

import stl.m1.Utils;
import stl.m1.tme5.CercleMinimum;
import stl.m1.tme6.EnvelopeConvexe;
import supportGUI.Circle;
import supportGUI.Variables;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class TestsRunner {

    public static void runTestOnVaroumasSamples(){
        String dirname = "data/samples_Varoumas/";
        String outFileCercleMinumumName  = "results/CercleMinimal/Varoumas.data";
        String outFileRitterName  = "results/Ritter/Varoumas.data";
        String outFileEnveloppeName  = "results/EnveloppeConvexe/Varoumas.data";
        PrintWriter output;
        try {
//            output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFileCercleMinumumName)));
//            output  = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFileRitterName)));
            output = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outFileEnveloppeName)));
        } catch (FileNotFoundException e) {
            System.err.println("Error When opening output files ");
            return ;
        }

        for (int i = 0; i < 1664; i++) {
            String filename = dirname + "test-" + (i+1)  + ".points";
            ArrayList<Point> points = new ArrayList<Point>();
            System.out.println(i);
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
                try {
                    String line;
                    while((line = input.readLine()) != null) {
                        String[] coordinates = line.split("\\s+");
                        points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
                    }
                    long startTime = System.currentTimeMillis();
//                    Circle CM = CercleMinimum.calculCercleMinNaif(points);
                    ArrayList<Point> enveloppe = EnvelopeConvexe.enveloppeConvexeNaif(points);

                    long endTime = System.currentTimeMillis();

//                    double surface = Math.PI * (CM.getRadius() * CM.getRadius());
//                    double surface = Math.PI * (RC.getRadius() * RC.getRadius());
                    double surface = Utils.calculSurfaceEnveloppeConvexe(enveloppe);

                    output.println((i+1) + " " + (endTime - startTime) + " " + surface);

                } catch (IOException var16) {
                    System.err.println("Exception: interrupted I/O.");
                } finally {
                    try {
                        input.close();
                    } catch (IOException e) {
                        System.err.println("I/O exception: unable to close " + filename);
                    }

                }
            } catch (FileNotFoundException e) {
                System.err.println("Input file not found.");
            }
        }
        output.close();
    }
}
