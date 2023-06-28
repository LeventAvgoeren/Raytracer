package cgg.a11;

import cgtools.*;

public class Sphere implements Shape {
    private Point center;
    private double radius;
    private Material material;

    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
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
                double inclination = Math.acos(r.getDirection().y());
                double azimuth = Math.PI + Math.atan2(r.getDirection().x(), r.getDirection().y());
                double x = azimuth / (2 * Math.PI);
                double y = inclination / Math.PI;
                Hit hitPoint = new Hit(t2, r.pointAt(t2), normal, material, x, y);
                return hitPoint;
            }
        }
        return null;
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