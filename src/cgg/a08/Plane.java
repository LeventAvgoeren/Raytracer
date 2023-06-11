package cgg.a08;

import cgtools.*;

public class Plane implements Shape {
    public final Point point;
    public final Direction normal;
    public final Material material;
    public final double rPara;
    private BoundingBox boundingBox;


    public Plane(Point point, Direction normal, Material material, double rPara) {
        this.point = point;
        this.normal = normal;
        this.material = material;
        this.rPara = rPara;
        boundingBox = new BoundingBox(Vector.add(Vector.direction(-rPara, -0.001, -rPara), point), Vector.add(Vector.direction(rPara, 0.001, rPara), point));

    }

    public Hit intersect(Ray r) {
        double dn = Vector.dotProduct(r.getDirection(), normal);

        double t = Vector.dotProduct(Vector.subtract(point, r.getOrigin()), normal) / dn;
        Point cuttingPoint = r.pointAt(t);

        if(dn == 0 || !r.isValid(t) || Vector.length(Vector.subtract(cuttingPoint, point)) > rPara) {
            return null;
        }

        Hit hitPoint = new Hit(t, cuttingPoint, normal, material);
        return hitPoint;
    }

    public BoundingBox bounds() {
        return boundingBox;
    }

    public BoundingBox calculateBounds() {
        return bounds();
    }
}

