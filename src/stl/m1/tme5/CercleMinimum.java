package stl.m1.tme5;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import stl.m1.Utils;
import supportGUI.Circle;


public class CercleMinimum {

    /**
     * Cette fonction calcul le cercle couvrant minimal à l'aide d'un algorithme naif
     * @param points : ArrayList<Points>, Un ensemble de points
     * @return Circle : Un cercle minimal couvrant points
     */
    public static Circle calculCercleMinNaif(ArrayList<Point> points) {
        for( Point p : points){
            for (Point q : points){
                if(p.equals(q)){
                    continue;
                }
                double mPQx = (p.x + q.x)/2.0;
                double mPQy = (p.y + q.y)/2.0;
                double distancePQ = Math.sqrt( ( (p.x - q.x)* (p.x - q.x) ) + ( (p.y - q.y)* (p.y - q.y) ) );
                double tempRadius = distancePQ/2.0;
                if(Utils.containsAllPoints(mPQx, mPQy, tempRadius, points)){
                    Point center = new Point( (int)mPQx, (int)mPQy );
                    return new Circle(center, (int) tempRadius);
                }
            }
        }
        Circle result = new Circle( new Point(0,0) ,Integer.MAX_VALUE );
        // créer un cercle infini
        for(Point p : points){
            for(Point q : points){
                if(p.equals(q)){
                    continue;
                }
                for(Point r : points){
                    if(p.equals(r) || q.equals(r)){
                        continue;
                    }

                    /*
                     * Millieu de P et Q
                     */
                    double mPQx = (p.x+q.x)/2.0;
                    double mPQy = (p.y+q.y)/2.0;

                    /*
                     * Millieu de Q et R
                     */
                    double mQRx = (q.x+r.x)/2.0;
                    double mQRy = (q.y+r.y)/2.0;

                    /*
                     * dPQ et dQR est le coefficient directeur de la droite (PQ) et (QR)
                     */
                    double dPQ;
                    double dQR;
                    /*
                     * Soit L1 et L2 les droites perpandiculaires à PQ et QR
                     */
                    double dL1;
                    double dL2;

                    if (q.x == p.x ){
                        dL1 = 0;
                    }else{
                        dPQ = 1.0 * ( q.y - p.y)/ ( q.x - p.x );
                        dL1 = -1 / dPQ;
                    }
                    if(r.x == q.x){
                        dL2 = 0;
                    }else{
                        dQR = 1.0 * ( r.y - q.y)/ ( r.x - q.x );
                        dL2 = -1. / dQR;
                    }
                    double x = (mQRy - dL2 * mQRx - mPQy + dL1 * mPQx) / (dL1 - dL2) ;
                    double y = dL1 * x - dL1 * mPQx + mPQy;
                    double radius = Utils.distance(x,y, p.x, p.y);
                    if( radius < result.getRadius() ){
                        if(Utils.containsAllPoints(x, y, radius, points)){
                            Point centre = new Point((int) x, (int) y );
                            result = new Circle( centre, (int) radius );
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * Cette fonction calcul le cercle couvrant minimal à l'aide d'un algorithme naif
     * @param p : ArrayList<Points>, Un ensemble de points
     * @return Circle : Un cercle minimal couvrant points
     */
    public static Circle calculCercleMinRitter(ArrayList<Point> p){
        ArrayList<Point> points = Utils.cloneList(p);
        int indexDummy = (int)(Math.random() * points.size());
        Point dummy = points.get(indexDummy);
        Point P = getFurtherPoint(points,dummy);
        Point Q = getFurtherPoint(points, P);

        double CX = (P.getX() + Q.getX())/2.0;
        double CY = (P.getY() + Q.getY())/2.0;
        double deltaX = (P.getX() - Q.getX());
        double deltaY = (P.getY() - Q.getY());
        double DiamSquared = (deltaX * deltaX) + (deltaY * deltaY); // radius without squareRoot
        double radius = Math.sqrt(DiamSquared)/2.;
        points = removeAllPointsContainedInCercle(points, CX, CY, radius);
        int i = 0;
        while(points.size() != 0){
            i++;
            if(i > 200){
                System.out.println("debug");
            }
            int indexS = (int)(Math.random() * points.size());
            Point S = points.get(indexS);
            double CS = Utils.distance(CX, CY, S.getX(), S.getY());
            double newRadius = (radius + CS) / 2.0;
            double CCB = CS - newRadius;
            CX = (CCB/CS) * S.getX() + (newRadius/CS) * CX;
            CY = (CCB/CS) * S.getY() + (newRadius/CS) * CY;
            radius = newRadius;
            points = removeAllPointsContainedInCercle(points, CX, CY, radius);
        }
        Point center = new Point((int)CX, (int)CY);
        return new Circle(center, (int)radius);
    }

    private static ArrayList<Point> removeAllPointsContainedInCercle(ArrayList<Point> points, double cx, double cy, double radiusSquared) {
        points.removeIf((p) -> Utils.isInCircleWhithoutSquareRoot(p, cx, cy, radiusSquared));
        return points;
    }

    public static Point getFurtherPoint(ArrayList<Point> points, Point P){
        Point res = P;
        double maxDistance = 0;
        for (Point tmp : points){
            double newDistance = Utils.distance(P,tmp);
            if(newDistance > maxDistance){
                maxDistance = newDistance;
                res = tmp;
            }
        }
        return res;
    }

}
