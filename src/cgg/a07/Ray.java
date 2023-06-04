package cgg.a07;

import cgtools.*;

public class Ray {
    
    private Point origin;
    private Direction dir;
    private double tMin;
    private double tMax;

    public Ray(Point origin, Direction dir, double tMin, double tMax) {
        this.origin = origin;
        this.dir = Vector.normalize(dir);
        this.tMin = tMin;
        this.tMax = tMax;
    }

    public Point pointAt(double t) {
        Direction dirT = Vector.multiply(t, dir);
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

    public double getTmax(){
        return tMax;
    }

    public Direction getDirection() {
        return dir;
    }

    public Point getOrigin() {
        return origin;
    }

    public String toString() {
        String returnString = "" + Ray.class;
        return returnString;
    }
}
