package stl.m1.tme6;

import stl.m1.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnvelopeConvexe {



    // enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
    //   renvoie l'enveloppe convexe de la liste.
    public static ArrayList<Point> enveloppeConvexeNaif(ArrayList<Point> points){
        if (points.size()<3) {
            return null;
        }
        ArrayList<Point> enveloppe = new ArrayList<Point>();
        int length = points.size();
        for (int i = 0; i < length; i++) {
            Point A = points.get(i);
            for (int j = 0; j < length; j++) {
                if(j == i){
                    continue;
                }
                Point B = points.get(j);
                boolean pos = false;
                boolean first = true;
                List<Point> temps = new ArrayList<>();
                int k;
                for (k = 0; k < length; k++) {
                    if(k ==j || k == i){
                        continue;
                    }
                    Point C = points.get(k);
                    double produitVect = Utils.produitVectoriel(A, B, C);
                    if (produitVect == 0.) {
                        temps.add(C);
                        continue;
                    }
                    if (first) {
                        pos = produitVect > 0;
                        first = false;
                    }
                    if (pos != (produitVect > 0)) {
                        break;
                    }
                }
                if (k == length) {
                    temps.add(A);
                    temps.add(B);
                    Point toAdd1 = new Point();
                    Point toAdd2 = new Point();
                    double max = -1;
                    for (int l = 0; l < temps.size(); l++) {
                        for (int m = l+1; m < temps.size(); m++) {
                            Point p1 = temps.get(l);
                            Point p2 = temps.get(m);

                            double distance = Utils.getSquareDistanceOf2Points(p1, p2);
                            if (distance > max) {
                                max = distance;
                                toAdd1 = p1;
                                toAdd2 = p2;
                            }
                        }
                    }
                    if(!enveloppe.contains(toAdd1)){
                        enveloppe.add(toAdd1);
                    }
                    if(!enveloppe.contains(toAdd2)){
                        enveloppe.add(toAdd2);
                    }
                }
            }
        }
        enveloppe = sortEnveloppe(enveloppe);
        return enveloppe;
    }

    private static ArrayList<Point> sortEnveloppe(ArrayList<Point> enveloppe) {
        Point Origine = Utils.getBarycentreOfEnveloppe(enveloppe);
        ArrayList<Point> vectors = pointsToVectorEnveloppe(enveloppe, Origine);

        // TODO :: Sort these vectors by angle
        vectors.sort((a,b) -> {
            double angle = Math.atan2(b.y, b.x) - Math.atan2(a.y, a.x);
            if( angle > 0){
                return 1;
            }
            else if (angle == 0){
                return 0;
            }
            else {
                return -1;
            }
        });



        return vectorToPointsEnveloppe(vectors, Origine);

    }

    private static ArrayList<Point> vectorToPointsEnveloppe(ArrayList<Point> vectors, Point origine) {
        ArrayList<Point> res = new ArrayList<>(vectors.size());
        for (Point p : vectors) {
            res.add(new Point(origine.x + p.x, origine.y + p.y));
        }
        return res;
    }

    private static ArrayList<Point> pointsToVectorEnveloppe(ArrayList<Point> enveloppe, Point Origine) {
        ArrayList<Point> res = new ArrayList<>(enveloppe.size() );
        for (int i = 0; i < enveloppe.size(); i++) {
            Point p = enveloppe.get(i);
            res.add(new Point(p.x - Origine.x, p.y - Origine.y));
        }
        return res;
    }


    public static java.util.List<Point> filtrageParPixel(java.util.List<Point> points){
        List<Point> res = new ArrayList<>(points);
        for (int i = 0; i < points.size(); i++) {
            Point A = points.get(i);
            for (int j = 0; j < points.size(); j++) {
                if(i != j){
                    Point B = points.get(j);
                    int distXAB = (int)Math.abs(A.getX() - B.getX());
                    int distYAB = (int)Math.abs(A.getY() - B.getY());
                    for (int k = 0; k < points.size(); k++) {
                        if(k != j && k!= i){
                            Point C = points.get(k);
                            if( A.getX() == B.getX() && B.getX() == C.getX() ){
                                int distYAC = (int)Math.abs(C.getY() - A.getY());
                                int distYBC = (int)Math.abs(C.getY() - B.getY());
                                if( distYAB > distYAC && distYAB > distYBC){
                                    res.remove(C);
                                }else if (distYAC > distYAB && distYAC > distYBC){
                                    res.remove(B);
                                }else if( distYBC > distYAB && distYBC > distYAC){
                                    res.remove(A);
                                }
                            }else if ( A.getY() == B.getY() && B.getY() == C.getY() ){
                                int distXAC = (int)Math.abs(C.getX() - A.getX());
                                int distXBC = (int)Math.abs(C.getX() - B.getX());
                                if( distXAB > distXAC && distXAB > distXBC){
                                    res.remove(C);
                                }else if (distXAC > distXAB && distXAC > distXBC){
                                    res.remove(B);
                                }else if( distXBC > distXAB && distXBC > distXAC){
                                    res.remove(A);
                                }
                            }
                        }
                    }
                }
            }
        }
        return res;

    }

}
