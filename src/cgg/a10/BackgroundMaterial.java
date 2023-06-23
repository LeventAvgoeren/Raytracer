package cgg.a10;

import cgtools.*;

public class BackgroundMaterial implements Material {

    private Sampler emission;

    public BackgroundMaterial(Sampler emission) {
        this.emission = emission;
    }

    public Ray scatteredRay(Ray ray, Hit hit) {
        return null;
    }

    public Color albedo(Hit hit) {
        return null;
    }

    public Color emission(Hit hit) {
        return emission.getColor(hit.getX(), hit.getY());
    }
}
