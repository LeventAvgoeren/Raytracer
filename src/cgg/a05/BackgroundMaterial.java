package cgg.a05;

import cgtools.*;

public class BackgroundMaterial implements Material {
    Color color;

    public BackgroundMaterial(Color color) {
        this.color = color;
    }

    public Ray scatteredRay(Ray ray, Hit hit) {
        return null;
    }

    public Color albedo() {
        return null;
    }

    public Color emission() {
        return color;
    }
}