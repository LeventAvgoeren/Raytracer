package cgg.a10;

import cgtools.*;

public class Background implements Shape {
    private Material material;

    public Background(Material material) {
        this.material = material;
    }

    public Hit intersect(Ray r) {
        // Direction normal = Vector.normalize(r.getDirection());
        // Hit hitPoint = new Hit(Double.POSITIVE_INFINITY, r.pointAt(Double.POSITIVE_INFINITY), normal, color);
        Point xpoint = Vector.point(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        Direction normal = Vector.negate(r.getDirection());
        double inclination = Math.acos(r.getDirection().y());
        double azimuth = Math.PI + Math.atan2(r.getDirection().x(), r.getDirection().y());
        double x = azimuth / (2 * Math.PI);
        double y = inclination / Math.PI;

        Hit hitPoint = new Hit(Double.POSITIVE_INFINITY, xpoint, normal, material, x, y);
        return hitPoint;
    }
}