
package cgg.a04;
import cgtools.*;

public class Plane implements Shape {
    public final Point point;
    public final Direction normal;
    public final Color color;
    public final double rPara;

    public Plane(Point point, Direction normal, Color color, double rPara) {
        this.point = point;
        this.normal = normal;
        this.color = color;
        this.rPara = rPara;
    }

    public Hit intersect(Ray r) {
        double dn = Vector.dotProduct(r.getDirection(), normal);

        double t = Vector.dotProduct(Vector.subtract(point, r.getOrigin()), normal) / dn;
        Point cuttingPoint = r.pointAt(t);

        if(dn == 0 || !r.isValid(t) || Vector.length(Vector.subtract(cuttingPoint, point)) > rPara) {
            return null;
        }

        Hit hitPoint = new Hit(t, cuttingPoint, normal, color);
        return hitPoint;
    }
}