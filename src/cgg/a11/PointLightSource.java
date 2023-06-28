package cgg.a11;

import cgtools.*;

public class PointLightSource implements Light {

    private Point lightSourcePoint;
    private Color lightSourceColor;
    private double lightSourceRadius;

    public PointLightSource(Point point, Color lightColor, double radius) {
        this.lightSourcePoint = point;
        this.lightSourceColor = lightColor;
        this.lightSourceRadius = radius;
    }

    public Color incomingIntensity(Point hit, Direction normal, Shape scene) {

        Direction directionToLightSource = Vector.subtract(lightSourcePoint, hit);
        double t = Vector.length(directionToLightSource);
        Ray lightSourceRay = new Ray(hit, Vector.normalize(directionToLightSource), 0.0001, t);

        if(scene.intersect(lightSourceRay) != null && scene.intersect(lightSourceRay).getRayParameterT() < Double.POSITIVE_INFINITY) {
            return Vector.black;
        }

        double distance = (t / lightSourceRadius) * (t / lightSourceRadius);
        directionToLightSource = Vector.normalize(directionToLightSource);
        double reflectionParameter = Math.max(Vector.dotProduct(normal, directionToLightSource), 0);
        double spread = 1 / distance;

        if(t > lightSourceRadius) {
            spread = Math.pow(spread, 1 / spread);
        }

        Color color = Vector.multiply(spread * reflectionParameter, lightSourceColor);

        return color;
    }
    
}
