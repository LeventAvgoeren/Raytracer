package cgg.a03;
import cgtools.*;

public class Ray {
    
    private Point origin;
    private Direction direc;
    private double tMin;
    private double tMax;

    public Ray(Point origin, Direction direc, double tMin, double tMax) {
        this.origin = origin;
        this.direc = Vector.normalize(direc);
        this.tMin = tMin;
        this.tMax = tMax;
    }

    public Point pointAt(double t) {
        Direction dirT = Vector.multiply(t, direc);
        Point point = Vector.add(dirT, origin);
        return point;
    }

    public boolean isValid(double t) {
        if(t>=tMin && t<=tMax) {
            return true;
        }
        else {
            return false;
        }
    }

    public Direction getDirection() {
        return direc;
    }

    public Point getOrigin() {
        return origin;
    }

    public String toString() {
        String returnString = "" + Ray.class;
        return returnString;
    }
}