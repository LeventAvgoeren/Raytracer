package cgg.a10;

import cgtools.*;

public class PolkaDots implements Sampler {

    private Color colorDots;
    private Color colorBackground;
    private double radius;
    private Matrix transformation;

    public PolkaDots(Color colorDots, Color colorBackground, double radius, Matrix transformation) {
        this.colorDots = colorDots;
        this.colorBackground = colorBackground;
        this.radius = radius;
        this.transformation = transformation;
    }

    public Color getColor(double x, double y) {
        Point transformedPoint = Matrix.multiply(transformation, Vector.point(x, y, 0));
        double xi = transformedPoint.x() - Math.floor(transformedPoint.x() + 0.5);
        double yi = transformedPoint.y() - Math.floor(transformedPoint.y() + 0.5);
        if((xi * xi) + (yi * yi) > (radius * radius)) {
            return colorBackground;
        }
        else {
            return colorDots;
        }
    }
}