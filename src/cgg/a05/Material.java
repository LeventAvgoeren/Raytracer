package cgg.a05;

import cgtools.*;

public interface Material {
    public Ray scatteredRay(Ray ray, Hit hit);
    //Ray r hit h
    public Color albedo();
    //ray r hit h
    public Color emission();
}
