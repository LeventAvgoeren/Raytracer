package cgg.a07;

import cgtools.*;

public class Hit {
    private double t;
    private Point hitPoint;
    private Direction normVec;
    private Material hitPointMaterial;

    public Hit(double t, Point hitPoint, Direction normVec, Material hitPointMaterial) {
        this.t = t;
        this.hitPoint = hitPoint;
        this.normVec = normVec;
        this.hitPointMaterial = hitPointMaterial;
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
}
