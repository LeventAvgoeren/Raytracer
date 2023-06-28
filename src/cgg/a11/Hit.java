package cgg.a11;

import cgtools.*;

public class Hit {
    private double t;
    private Point hitPoint;
    private Direction normVec;
    private Material hitPointMaterial;
    private double x;
    private double y;

    public Hit(double t, Point hitPoint, Direction normVec, Material hitPointMaterial, double x, double y) {
        this.t = t;
        this.hitPoint = hitPoint;
        this.normVec = normVec;
        this.hitPointMaterial = hitPointMaterial;
        this.x = x;
        this.y = y;
    }

    public double getRayParameterT() {
        return t;
    }

    public Point getHitPoint() {
        return hitPoint;
    }

    public Direction getNormVec() {
        return normVec;
    }

    public Material getHitPointMaterial() {
        return hitPointMaterial;
    }

    public String toString() {
        String returnString = "" + hitPoint;
        return returnString;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
