package cgg.a05;

import cgg.Image;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;

        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2.0);
        Background background = new Background(new BackgroundMaterial(new Color(1.0, 1.0, 1.0)));
        Plane ground = new Plane(Vector.point(0.0, -0.5, 0.0), Vector.direction(0, 1, 0), new DiffusingMaterial(new Color(1.0, 1.0, 1.0)), Double.POSITIVE_INFINITY);

        Group group = new Group();
        group.addShape(background);
        group.addShape(ground);

        double radiusIncrement = 0.2;
        double initialRadius = 0.4;
        double shift = 1.0; // Verschiebung nach rechts

        for (int i = 0; i < 4; i++) {
            double x = -1.5 + shift + i * 1.0;
            double y = initialRadius;
            double z = -4.0;
            double radius = initialRadius + i * radiusIncrement;

            if (i == 1) {
                x -= 0.05; // Verschiebung der zweiten Kugel nach links
            }

            double t = i / 3.0; // Parameter für den Farbverlauf
            Color color = hsvToRgb(t * 120, 0.5, 0.8); // HSV-Farbverlauf mit mittlerer Sättigung und Helligkeit
            Shape sphere = new Sphere(Vector.point(x, y, z), radius, new DiffusingMaterial(color));
            group.addShape(sphere);
        }

        Raytracing raytracer = new Raytracing(camera, group, 100);

        // Create new camera and sample pictured shapes
        Image shapes = new Image(width, height);
        shapes.sample(100, group, camera, raytracer, 500); // Erhöhte Probenanzahl

        // Write the images to disk
        final String filename = "doc/a05-diffuse-spheres.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }

    // Funktion zur Umwandlung von HSV in RGB
    private static Color hsvToRgb(double h, double s, double v) {
        double c = v * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = v - c;
        double r, g, b;

        if (h >= 0 && h < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (h >= 60 && h < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (h >= 120 && h < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (h >= 180 && h < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (h >= 240 && h < 300) {
            r = x;
            g = 0;
            b = c;
        } else {
            r = c;
            g = 0;
            b = x;
        }

        return new Color(r + m, g + m, b + m);
    }
}