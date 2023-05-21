package cgg.a05;

import cgtools.*;

public class PinholeCamera {
    private double width;
    private double height;
    private double cameraOpeningAngle;

    public PinholeCamera(double width, double height, double cameraOpeningAngle) {
        this.width = width;
        this.height = height;
        this.cameraOpeningAngle = cameraOpeningAngle;
    }

    public Ray generateRay(double x, double y) {
        double X = x - (width / 2.0);
        double Y = -(y - (height / 2.0));
        double Z = -((width / 2.0) / Math.tan(cameraOpeningAngle / 2.0));

        Direction dir = Vector.normalize(Vector.direction(X, Y, Z));
        Ray ray = new Ray(Vector.zero, dir, 0.0, Double.POSITIVE_INFINITY);

        return ray;
    }
}
