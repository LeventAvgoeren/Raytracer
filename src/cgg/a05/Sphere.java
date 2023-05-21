package cgg.a05;

import cgtools.*;

public class Sphere implements Shape {
    private Point center;
    private double radius;
    private Material material;

    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Hit intersect(Ray r) {
        Direction x0 = Vector.subtract(r.getOrigin(), center);

        double a = Vector.dotProduct(r.getDirection(), r.getDirection());
        double b = 2 * Vector.dotProduct(x0, r.getDirection());
        double c = Vector.dotProduct(x0, x0) - (radius * radius);

        double n = b * b - 4 * a * c;

        if(n >= 0) {
            double t = 0;
            double t1 = (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
            double t2 = (-b - Math.sqrt((b * b) - (4 * a * c))) / (2 * a);

            if(t1 < t2) {
                t = t1;
            }
            else {
                t = t2;
            }

            if(r.isValid(t)) {
                Direction normal = Vector.normalize(Vector.divide(Vector.subtract(r.pointAt(t2), center), radius));
                Hit hitPoint = new Hit(t2, r.pointAt(t2), normal, material);
                return hitPoint;
            }
        }
        return null;
    }
}