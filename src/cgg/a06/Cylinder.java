package cgg.a06;

import cgtools.*;

public class Cylinder implements Shape{
    private Point center;
    private Material material;
    private double radius;
    private double height;

    // source1: http://www.illusioncatalyst.com/notes_files/mathematics/line_cylinder_intersection.php
    // source2: https://www.pbr-book.org/3ed-2018/Shapes/Cylinders
    public Cylinder(double radius, Point center, double height, Material material) {
        this.center = center;
        this.radius = radius;
        this.height = height;
        this.material = material;
    }

    public Hit intersect(Ray r) {

        Point originShifted = Vector.subtract(r.getOrigin(), Vector.direction(center.x(), center.y(), center.z()));
        double a = (r.getDirection().x() * r.getDirection().x()) + (r.getDirection().z() * r.getDirection().z());
        double b = 2 * (r.getDirection().x() * originShifted.x() + r.getDirection().z() * originShifted.z());
        double c = (originShifted.x() * originShifted.x()) + (originShifted.z() * originShifted.z()) - (radius * radius);

        double n = b * b - 4 * a * c;

        if(n >= 0) {
            double t = 0;
            double t1 = (-b + Math.sqrt((b*b) - (4*a*c))) / (2*a);
            double t2 = (-b - Math.sqrt((b*b) - (4*a*c))) / (2*a);

            if(t1 < t2) {
                t = t1;
            }
            else {
                t = t2;
            }

            // if t >= tMin and <= tMax then return hitpoint
            if(r.isValid(t) && r.pointAt(t).y() >= center.y() && r.pointAt(t).y() < center.y() + height) {
                Point hitPoint = Vector.add(r.getOrigin(), Vector.multiply(t, r.getDirection()));
                // normal = (x-center)/radius
                Direction normal = Vector.divide(Vector.subtract(r.pointAt(t), center), radius);
                return new Hit(t, hitPoint, normal, material);
            }
        }
        
        return null;
    }

    public Group getFullCylinder() {
        Group group = new Group();
        Cylinder cylinder = new Cylinder(radius, center, height, material);
        Plane cylinderTop = new Plane(Vector.point(center.x(), center.y() + height, center.z()), Vector.direction(0, 1, 0), material, radius);
        Plane cylinderBottom = new Plane(center, Vector.direction(0, 1, 0), material, radius * 3);
        group.addShape(cylinder);
        group.addShape(cylinderTop);
        group.addShape(cylinderBottom);
        return group;
    }
    
}