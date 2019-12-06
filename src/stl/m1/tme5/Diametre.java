package stl.m1.tme5;

import supportGUI.Line;

import java.awt.*;
import java.util.ArrayList;

public class Diametre {

    public static Line calculDiametreNaif(ArrayList<Point> points){
        return new Line(new Point(100, 100), new Point(900, 900));
    }

    public static Line calculDiametreAvecAkl_Toussaint(ArrayList<Point> points){
        return calculDiametreNaif(points);
    }

    public static Line calculDiametreAvecQuickHull(ArrayList<Point> points){
        return calculDiametreNaif(points);
    }
}
