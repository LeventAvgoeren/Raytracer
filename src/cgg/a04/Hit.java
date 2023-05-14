package cgg.a04;

import cgtools.*;

public class Hit {
    private double t;
    private Point hitPoint;
    private Direction normVec;
    private Color hitPointColor;

    public Hit(double t, Point hitPoint, Direction normVec, Color hitPointColor) {
        this.t = t;
        this.hitPoint = hitPoint;
        this.normVec = normVec;
        this.hitPointColor = hitPointColor;
    }

    public double getRayParameterT() {
        return t;
    }

    public Direction getNormVec() {
        return normVec;
    }

    public Color getHitPointColor() {
        return hitPointColor;
    }

    public String toString() {
        String returnString = "" + hitPoint;
        return returnString;
    }
}
