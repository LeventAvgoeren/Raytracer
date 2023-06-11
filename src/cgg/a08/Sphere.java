package cgg.a08;

import cgtools.*;

public class Sphere implements Shape {
    private Point center;
    private double radius;
    private Material material;
    private BoundingBox boundingBox;

    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
        boundingBox = new BoundingBox(Vector.add(Vector.direction(-radius, -radius, -radius), center), Vector.add(Vector.direction(radius, radius, radius), center));
    }

    public Hit intersect(Ray r) {
        Direction x0 = Vector.subtract(r.getOrigin(), center);

        double a = Vector.dotProduct(r.getDirection(), r.getDirection());
        double b = 2 * Vector.dotProduct(x0, r.getDirection());
        double c = Vector.dotProduct(x0, x0) - (radius * radius);

        double n = b * b - 4 * a * c;

        if(n >= 0) {
            double t = 0;
            double t1 = (-b + Math.sqrt((b * b) - (4 * a * c))) / (2 * a);
            double t2 = (-b - Math.sqrt((b * b) - (4 * a * c))) / (2 * a);

            if(t1 < t2) {
                t = t1;
            }
            else {
                t = t2;
            }

            if(r.isValid(t)) {
                Direction normal = Vector.normalize(Vector.divide(Vector.subtract(r.pointAt(t2), center), radius));
                Hit hitPoint = new Hit(t2, r.pointAt(t2), normal, material);
                return hitPoint;
            }
        }
        return null;
    }
    
    public BoundingBox bounds() {
        return boundingBox;
    }

    public BoundingBox calculateBounds() {
        return bounds();
    }

    // public Group sphereFlake(Group group, int n) {
    //     group.addShape(this);

    //     if(n == 0) return group;
    //     Material m = material;
        
    //     Sphere newSphereL = new Sphere(new Point(center.x() + radius + radius * 0.5,
    //                         center.y(), center.z()), radius * 0.5, m);
        
    //     Sphere newSphereR = new Sphere(new Point(center.x() - radius - radius * 0.5,
    //                         center.y(), center.z()), radius * 0.5, m);
        
    //     Sphere newSphereO = new Sphere(new Point(center.x(), center.y() + radius + radius * 0.5,
    //                         center.z()), radius * 0.5, m);
        
    //     Sphere newSphereU = new Sphere(new Point(center.x(), center.y() - radius - radius * 0.5,
    //                         center.z()), radius * 0.5, m);
        
    //     Sphere newSphereV = new Sphere(new Point(center.x(), center.y(),
    //                         center.z() + radius + radius * 0.5), 
    //                         radius * 0.5, m);
        
    //     Sphere newSphereH = new Sphere(new Point(center.x(), center.y(),
    //                         center.z() - radius - radius * 0.5), 
    //                         radius * 0.5, m);
        
    //     group = newSphereL.sphereFlake(group, n-1);
    //     group = newSphereR.sphereFlake(group, n-1);
    //     group = newSphereO.sphereFlake(group, n-1);
    //     group = newSphereU.sphereFlake(group, n-1);
    //     group = newSphereV.sphereFlake(group, n-1);
    //     return newSphereH.sphereFlake(group, n-1);
    // }
}
