package cgg.a12;

import cgtools.*;

public class Transformation {

    private Matrix toWorld;
    private Matrix fromWorld;
    private Matrix toWorldM;

    public Transformation(Matrix toWorld) {
        this.toWorld = toWorld;
        fromWorld = Matrix.invert(toWorld);
        toWorldM = Matrix.transpose(fromWorld);
    }
    
    public Ray rayTransformation(Ray ray) {
        Point origin = Matrix.multiply(fromWorld, ray.getOrigin());
        Direction direction = Matrix.multiply(fromWorld, ray.getDirection());
        return new Ray(origin, direction, 0.00000001, Double.POSITIVE_INFINITY);
    }
    
    public Hit hitTransformation(Hit hit) {
        Point cuttingPoint = Matrix.multiply(toWorld, hit.getHitPoint());
        Direction normVector = Matrix.multiply(toWorldM, hit.getNormVec());
        double inclination = Math.acos(normVector.y());
        double azimuth = Math.PI + Math.atan2(normVector.x(), normVector.z());
        double x = azimuth / (2 * Math.PI);
        double y = inclination / Math.PI;
        return new Hit(hit.getRayParameterT(), cuttingPoint, normVector, hit.getHitPointMaterial(), x, y);
    }

    public Matrix getMatrix() {
        return toWorld;
    }
    
}