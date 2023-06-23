package cgg.a10;

import cgtools.*;

public interface Material {
    public Ray scatteredRay(Ray ray, Hit hit);

    public Color albedo(Hit hit);

    public Color emission(Hit hit);
}
