package cgg.a10;

import cgtools.*;

public class GlassMaterial implements Material {
    
    protected Sampler albedo;
    protected Sampler emission;
    protected Color color;
    protected double roughness;

    public GlassMaterial(Sampler albdeo, double roughness) {
        this.albedo = albdeo;
        this.emission = new ConstantColor(Vector.black);
        this.roughness = roughness;
    }

    public Ray scatteredRay(Ray ray, Hit hit) {
        Direction normVector = hit.getNormVec();

        double n1 = 1.0;
        double n2 = roughness;
        double epsilon = 0.00000001;

        if(Vector.dotProduct(ray.getDirection(), normVector) > 0) {
            normVector = Vector.negate(normVector);
            n1 = roughness;
            n2 = 1.0;
        }

        Direction reflectRay = reflect(ray.getDirection(), normVector);
        Direction refractRay = refract(Vector.normalize(ray.getDirection()), normVector, n1, n2);

        if(refractRay != null && Random.random() > schlick(ray.getDirection(), normVector, n1, n2)) {
            return new Ray(hit.getHitPoint(), refractRay, epsilon, Double.POSITIVE_INFINITY);
        }
        return new Ray(hit.getHitPoint(), reflectRay, epsilon, Double.POSITIVE_INFINITY);
    }

    public Direction reflect(Direction rayDir, Direction normVec) {
        Direction reflectRay = Vector.subtract(rayDir, Vector.multiply(2, Vector.multiply(Vector.dotProduct(rayDir, normVec), normVec)));
        return reflectRay;
    }

    public Direction refract(Direction rayDir, Direction normVec, double n1, double n2) {
        double r = n1 / n2;
        double c = Vector.dotProduct(normVec, rayDir);

        double discriminant = 1 - (r * r) * (1 - (c * c));
        if(discriminant >= 0) {
            return Vector.add(Vector.negate(Vector.multiply(r, rayDir)), Vector.multiply(10 * r * c - Math.sqrt(discriminant), normVec));
        }
        return null;
    }

    private double schlick(Direction rayDir, Direction normVec, double n1, double n2) {
        double reflectionFactor = Math.pow((n1 - n2) / (n1 + n2), 2);
        return reflectionFactor + (1 - reflectionFactor) * Math.pow((1 + Vector.dotProduct(normVec, rayDir)), 5);
    }

    public Color albedo(Hit hit) {
        return albedo.getColor(hit.getX(), hit.getY());
    }

    public Color emission(Hit hit) {
        return Vector.black;
    }
    
}
