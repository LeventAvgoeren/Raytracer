package cgg.a12;

import cgtools.*;

public class Raytracing implements Sampler {
    CameraObscura camera;
    Group group;
    World world;
    int recursionDepth;

    public Raytracing(CameraObscura camera, World world, int recursionDepth) {
        this.camera = camera;
        this.world = world;
        this.recursionDepth = recursionDepth;
        group = world.getScene();
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

        // if(scatteredRay != null) {
        //     Color color = Vector.multiply(material.albedo(hit), calculateRadiance(scene, scatteredRay, depth - 1));
        //     color = Vector.add(material.emission(hit), color);

        //     return color;
        // }
        // else {
        //     return emission;
        // }

        if(scatteredRay != null) {
            Color color = Vector.multiply(material.albedo(hit), calculateRadiance(group, scatteredRay, depth));
            color = Vector.add(material.emission(hit), color);

            if(world.getLightSources() != null) {
                for(Light light : world.getLightSources()) {
                    color = Vector.add(color, light.incomingIntensity(hit.getHitPoint(), hit.getNormVec(), group));
                }
            }

            return color;
        }

        return material.emission(hit);
    }
}