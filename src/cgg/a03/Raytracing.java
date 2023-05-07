package cgg.a03;

import java.util.ArrayList;

import cgtools.*;

public class Raytracing implements Sampler {
    PinholeCamera kamera;
    ArrayList<Sphere> spheres;

    
    Random random = new Random();

    public Raytracing(int width, int height, PinholeCamera kamera, int spheresAnzahl) {
        this.kamera = kamera;
        

        spheres = new ArrayList<Sphere>();
        for (int i = 0; i < spheresAnzahl; i++) {
            double randomX = random.nextDouble(-200, 200);
            double randomY = random.nextDouble(-200, 200);
            double randomZ = random.nextDouble(-220, -100);

            double randomRadius = random.nextDouble(15, 25);

            Color color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble());
            Sphere randomSphere = new Sphere(new Point(randomX, randomY, randomZ), randomRadius, color);
            spheres.add(randomSphere);
        }

      
    }

    public Color getColor(double x, double y) {
        Ray r = kamera.generateRay(x, y);
        Hit closestHit = null;
        for(Sphere sphere: spheres) {
            Hit hit = sphere.intersect(r);
            if(hit != null) {
                if((closestHit == null) || (hit.getRayParameterT() < closestHit.getRayParameterT())) {
                    closestHit = hit;
                }
            }
        }
        if(closestHit != null) {
            return shade(closestHit.getNormVec(), closestHit.getHitPointColor());
        }
        return Vector.black;
    }

    public static Color shade(Direction normal, Color color) {
        Direction lightingDirection = Vector.normalize(Vector.direction(1, 1, 0.5));
        Color ambient = Vector.multiply(0.1, color);
        Color diffuse = Vector.multiply(0.9 * Math.max(0, Vector.dotProduct(lightingDirection, normal)), color);
        return Vector.add(ambient, diffuse);
    }
}