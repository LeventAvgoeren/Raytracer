package cgg.a03;

import java.util.ArrayList;

import cgg.Image;
import cgg.a02.Disc;
import cgtools.*;

import java.util.ArrayList;

public class Main{
    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;
        Image image = new Image(width, height);
    
        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2, new Point(0, 0, 0));
        ArrayList<Sphere> spheres = new ArrayList<>();
    
        int numTowers = 7;
        double towerHeight = 20;
        double towerRadius = 4;
    
        for (int i = 0; i < numTowers; i++) {
            double yPosition = (i * towerHeight) / (numTowers - 1) - towerHeight / 2;
            double colorFactor = (double) i / (numTowers - 1);
    
            Color towerColor = interpolateRainbow(colorFactor);
            spheres.add(new Sphere(new Point(0, yPosition, -25 - i * towerRadius), towerRadius, towerColor));
        }
    
        image.sample(100, spheres, camera);
    
        // Write the image to disk.
        final String filename = "doc/a03-spheres.png";
        image.write(filename);
        System.out.println("Wrote image: " + filename);
    }
    
    public static Color interpolateRainbow(double t) {
        float r = (float) (Math.sin(t * Math.PI * 2) * 0.75f + 0.25f);
        float g = (float) (Math.sin((t + 1.0 / 3.0) * Math.PI * 2) * 0.75f + 0.25f);
        float b = (float) (Math.sin((t + 2.0 / 3.0) * Math.PI * 2) * 0.75f + 0.25f);
    
        return new Color(r, g, b);
    }
    

    }
