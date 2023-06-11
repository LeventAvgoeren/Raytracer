package cgg.a08;

public interface Shape {
    public Hit intersect(Ray r);

    public BoundingBox bounds();

    public BoundingBox calculateBounds();
}
