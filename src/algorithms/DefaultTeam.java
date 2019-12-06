package algorithms;

import stl.m1.tme5.CercleMinimum;
import stl.m1.tme5.Diametre;
import stl.m1.tme6.EnvelopeConvexe;
import supportGUI.Circle;
import supportGUI.Line;

import java.awt.*;
import java.util.ArrayList;

public class DefaultTeam {

    public Circle calculCercleMin(ArrayList<Point> points) {

//        return CercleMinimum.calculCercleMinNaif(points);

        return CercleMinimum.calculCercleMinRitter(points);
    }


    public Line calculDiametre(ArrayList<Point> points){
        return Diametre.calculDiametreNaif(points);
//        return Diametre.calculDiametreAvecAkl_Toussaint(points);
//        return Diametre.calculDiametreAvecQuickHull(points);
    }

    public  ArrayList<Point> enveloppeConvexe(ArrayList<Point> points){
        return EnvelopeConvexe.enveloppeConvexeNaif(points);

        //* TODO :: Ajouter le rectangle minimum
    }

    public Line calculDiametreOptimise(ArrayList<Point> points){
        return Diametre.calculDiametreNaif(points);
    }
}
