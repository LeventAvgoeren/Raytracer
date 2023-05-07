package cgg.a03;
import cgtools.*;

public class Hit {
    private double t;
    private Point hitPoint;
    private Direction normalenVec;
    private Color hitPointColor;

    public Hit(double t, Point hitPoint, Direction normalenVec, Color hitPointColor) {
        this.t = t;
        this.hitPoint = hitPoint;
        this.normalenVec = normalenVec;
        this.hitPointColor = hitPointColor;
    }

    public double getRayParameterT() {
        return t;
    }

    public Direction getNormVec() {
        return normalenVec;
    }

    public Color getHitPointColor() {
        return hitPointColor;
    }

    public String toString() {
        String returnString = "" + hitPoint;
        return returnString;
    }
}