package cgg.a10;

import cgtools.*;

public class MetallMaterial implements Material {

    protected Sampler albedo;
    protected Sampler emission;
    protected double scattering;

    public MetallMaterial(Sampler albdeo, double frostedN) {
        this.albedo = albdeo;
        this.emission = new ConstantColor(Vector.black);
        this.scattering=frostedN;
    }

    public Ray scatteredRay(Ray ray, Hit hit) {
        Direction r = Vector.subtract(ray.getDirection(),
            Vector.multiply(2 * Vector.dotProduct(hit.getNormVec(), ray.getDirection()), hit.getNormVec()));
        Random rand = new Random();
        double epsilon = 0.00000001;
        
        while(true) {
            double x = (rand.nextDouble() * 2.0) - 1.0;
            double y = (rand.nextDouble() * 2.0) - 1.0;
            double z = (rand.nextDouble() * 2.0) - 1.0;
            
            if(scattering != 0) {
                Direction randomDir = new Direction(x, y, z);
                r = Vector.add(r, Vector.multiply(scattering, randomDir));
            }
            return new Ray(hit.getHitPoint(), r, epsilon, Double.POSITIVE_INFINITY);
        }
    }

    public Color albedo(Hit hit) {
        return albedo.getColor(hit.getX(), hit.getY());
    }

    public Color emission(Hit hit) {
        return Vector.black;
    }
    
}
