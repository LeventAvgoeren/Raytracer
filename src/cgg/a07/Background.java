package cgg.a07;

import cgtools.*;

public class Background implements Shape {
    private Material material;

    public Background(Material material) {
        this.material = material;
    }

    public Hit intersect(Ray r) {
        // Direction normal = Vector.normalize(r.getDirection());
        // Hit hitPoint = new Hit(Double.POSITIVE_INFINITY, r.pointAt(Double.POSITIVE_INFINITY), normal, color);
        Hit hitPoint = new Hit(r.getTmax(), r.pointAt(r.getTmax()), new Direction(1, 0, 0), material);
        return hitPoint;
    }
}