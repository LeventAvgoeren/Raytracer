package cgg.a05;

import cgtools.*;

public class DiffusingMaterial implements Material{

    Color color;
    
    public DiffusingMaterial(Color color){
	    this.color = color;
    }
    
    public Ray scatteredRay(Ray ray, Hit hit) {
        Random rand = new Random();
        double epsilon = 0.00000001;
        
        while(true) {
            double x = (rand.nextDouble() * 2.0) - 1.0;
            double y = (rand.nextDouble() * 2.0) - 1.0;
            double z = (rand.nextDouble() * 2.0) - 1.0;
            
            double length = x * x + y * y + z * z;
            
            if(length <= 1) {
                Direction randDirection = new Direction(x, y, z);
                Direction dir = Vector.normalize(Vector.add(hit.getNormVec(), randDirection));

                return new Ray(hit.getHitPoint(), dir, epsilon, ray.getTmax());
            }
        }
    }

    public Color albedo() {
        return color;
    }

    public Color emission() {
        return Vector.black;
    }
}