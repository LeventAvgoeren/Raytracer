package cgg.a03;

import cgtools.*;

public class PinholeCamera {
    private double width;
    private double height;
    private double cameraAngle;
    private Point cameraPosition;

    public PinholeCamera(double width, double height, double cameraAngle, Point cameraPosition) {
        this.width = width;
        this.height = height;
        this.cameraAngle = cameraAngle;
        this.cameraPosition = cameraPosition;
    }

    public Ray generateRay(double x, double y) {
        double X = x - (width / 2.0);
        double Y = -(y - (height / 2.0));
        double Z = -((width / 2.0) / Math.tan(cameraAngle / 2.0));

        Direction dir = Vector.normalize(Vector.direction(X, Y, Z));
        Ray ray = new Ray(cameraPosition, dir, 0.0, Double.POSITIVE_INFINITY);

        return ray;
    }
}