package cgg.a07;

import cgtools.*;

public class PinholeCamera {
    private double width;
    private double height;
    private double cameraOpeningAngle;
    private Point cameraPosition;
    private Matrix matrix;

public PinholeCamera(double width, double height, double cameraOpeningAngle, Point cameraPosition) {
    this.width = width;
    this.height = height;
    this.cameraOpeningAngle = cameraOpeningAngle;
    this.cameraPosition = cameraPosition;
    this.matrix = Matrix.identity();
}

public PinholeCamera(double width, double height, double cameraOpeningAngle, Point cameraPosition, Matrix matrix) {
    this.width = width;
    this.height = height;
    this.cameraOpeningAngle = cameraOpeningAngle;
    this.cameraPosition = cameraPosition;
    this.matrix = matrix;
}

    public Ray generateRay(double x, double y) {
        double X = x - (width / 2.0);
        double Y = -(y - (height / 2.0));
        double Z = -((width / 2.0) / Math.tan(cameraOpeningAngle / 2.0));

        Point cameraPosition = Matrix.multiply(this.matrix, this.cameraPosition);
        Direction dir = Matrix.multiply(matrix, Vector.normalize(Vector.direction(X, Y, Z)));

        Ray ray = new Ray(cameraPosition, dir, 0.0, Double.POSITIVE_INFINITY);

        return ray;
    }
}