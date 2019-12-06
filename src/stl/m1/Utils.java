package stl.m1;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static  double distance(Point p, Point q){
        return distance(p.getX(), p.getY(),q.getX(),q.getY());
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        return Math.sqrt( deltaX * deltaX + deltaY* deltaY );
    }

    public static double getSquareDistanceOf2Points(Point p1, Point p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return (deltaX * deltaX) + (deltaY * deltaY) ;
    }


    public static double getSquareDistanceOf2Points(double x1, double y1, double x2, double y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;
        return (deltaX * deltaX) + (deltaY * deltaY) ;
    }

    public static boolean containsAllPoints(double x, double y, double radius, ArrayList<Point> points) {
        for (Point p : points){
            if(! containsPoint(x, y, radius, p)){
                return false;
            }
        }
        return true;

    }

    public static boolean containsPoint(double x, double y, double radius, Point p) {
        double deltaX = x - p.x;
        double deltaY = y - p.y;
        return ( (deltaX * deltaX) + (deltaY * deltaY) ) <= radius * radius;

    }

    public static double produitVectoriel(Point pa, Point pb, Point pOther) {
        double x = pb.getX() - pa.getX();
        double y = pb.getY() - pa.getY();
        double xp =  (pb.getX() - pOther.getX());
        double yp =  (pb.getY() - pOther.getY());
        return x*yp - y*xp ;
    }

    public static boolean isInCircleWhithoutSquareRoot(Point p, double cx, double cy, double radius) {
        double distance = distance(p.getX(), p.getY(), cx, cy);
//        return distance <= radius;
        return distance - radius <= 0.1;
    }

    public static ArrayList<Point> cloneList(List<Point> list) {
        ArrayList<Point> clone = new ArrayList<Point>(list.size());
        for (Point item : list) clone.add((Point) item.clone());
        return clone;
    }

    public static double calculSurfaceEnveloppeConvexe(ArrayList<Point> enveloppe){
        double res = 0;
        int length = enveloppe.size();
        Point C = getBarycentreOfEnveloppe(enveloppe);
        for (int i = 0; i < length ; i++) {
            Point A = enveloppe.get(i%length);
            Point B = enveloppe.get( (i+1) % length );
            double surfaceTriangle = calculSurfaceTriangle(A, B, C);
            res += surfaceTriangle;
        }
        return res;
    }



    private static double calculSurfaceTriangle(Point A, Point B, Point C) {
        double area = (A.x * (B.y - C.y) + B.x * (C.y - A.y) + C.x * (A.y - B.y)) / 2.0;
        return Math.abs(area);

    }

    public static Point getBarycentreOfEnveloppe(ArrayList<Point> enveloppe){
        double barycentreX = 0;
        double barycentreY = 0;
        int length = enveloppe.size();
        for (Point p : enveloppe) {
            barycentreX += p.getX();
            barycentreY += p.getY();
        }
        barycentreX = barycentreX / length;
        barycentreY = barycentreY / length;
        return new Point((int)barycentreX, (int)barycentreY);
    }

}
