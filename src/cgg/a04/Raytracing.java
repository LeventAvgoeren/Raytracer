package cgg.a04;

import cgtools.*;

public class Raytracing implements Sampler {
    PinholeCamera camera;
    Group group;

    public Raytracing(PinholeCamera camera, Group group) {
        this.camera = camera;
        this.group = group;
    }

    public Color getColor(double x, double y) {
        Ray r = camera.generateRay(x, y);
        Hit closestHit = group.intersect(r);

        if(closestHit != null) {
            return shade(closestHit.getNormVec(), closestHit.getHitPointColor());
        }
        return Vector.black;
    }

    public static Color shade(Direction normal, Color color) {
        Direction lightingDirection = Vector.normalize(Vector.direction(1.0, 1.0, 0.5));
        Color ambient = Vector.multiply(0.1, color);
        Color diffuse = Vector.multiply(0.9 * Math.max(0, Vector.dotProduct(lightingDirection, normal)), color);
        return Vector.add(ambient, diffuse);
    }
}