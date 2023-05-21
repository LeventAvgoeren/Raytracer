package cgg.a05;

import cgtools.*;

public class Background implements Shape {
    private Material material;

    public Background(Material material) {
        this.material = material;
    }

    public Hit intersect(Ray r) {
        Hit hitPoint = new Hit(r.getTmax(), r.pointAt(r.getTmax()), new Direction(1, 0, 0), material);
        return hitPoint;
    }
}