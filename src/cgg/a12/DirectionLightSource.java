package cgg.a12;

import cgtools.*;

public class DirectionLightSource implements Light {

    private Direction lightSourceDirection;
    private Color lightSourceColor;
    private double lighSourceIntensity;

    public DirectionLightSource(Direction direction, Color color, double intensity) {
        this.lightSourceDirection = Vector.normalize(direction);
        this.lightSourceColor = color;
        this.lighSourceIntensity = intensity;
    }

    public Color incomingIntensity(Point hit, Direction normal, Shape scene) {

        Ray lightSourceRay = new Ray(hit, Vector.negate(lightSourceDirection), 0.0001, Double.POSITIVE_INFINITY);

        if(scene.intersect(lightSourceRay) != null && scene.intersect(lightSourceRay).getRayParameterT() < Double.POSITIVE_INFINITY) {
            return Vector.black;
        }

        Color ambient = Vector.multiply(0.1 * lighSourceIntensity, lightSourceColor);
        Color diffuse = Vector.multiply(0.9 * lighSourceIntensity * Math.max(0, Vector.dotProduct(Vector.negate(lightSourceDirection), normal)), lightSourceColor);
        Color color = Vector.add(ambient, diffuse);

        return color;
    }
    
}
