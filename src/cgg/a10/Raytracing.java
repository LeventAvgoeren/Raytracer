package cgg.a10;

import cgtools.*;

public class Raytracing implements Sampler {
    PinholeCamera camera;
    Group group;
    int recursionDepth;

    public Raytracing(PinholeCamera camera, Group group, int recursionDepth) {
        this.camera = camera;
        this.group = group;
        this.recursionDepth = recursionDepth;
    }

    public Color getColor(double x, double y) {
        Ray r = camera.generateRay(x, y);
        return calculateRadiance(group, r, recursionDepth);
    }

    public Color calculateRadiance(Group scene, Ray ray, int depth) {
        if(depth == 0) {
            return Vector.black;
        }

        Hit hit = scene.intersect(ray);
        Material material = hit.getHitPointMaterial();
        Ray scatteredRay = material.scatteredRay(ray, hit);
        Color emission = material.emission(hit);

        if(scatteredRay != null) {
            Color color = Vector.multiply(material.albedo(hit), calculateRadiance(scene, scatteredRay, depth - 1));
            color = Vector.add(material.emission(hit), color);

            return color;
        }
        else {
            return emission;
        }
    }
}