package cgg.a11;

import java.util.concurrent.ExecutionException;

import cgg.Image;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
        final int width = 1080;
        final int height = 720;

        Group scene = new Group(new Transformation(Matrix.identity));
        Plane ground = new Plane(Vector.point(0.0, 0.0, 0.0), Vector.direction(0.0, 1.0, 0.0), new GlassMaterial(new ConstantColor(Vector.black), 2), Double.POSITIVE_INFINITY);
        Background background = new Background(new BackgroundMaterial(new Texture("doc/Texturen/Himmel.png", Matrix.translation(0, 0, 0))));

        
        for (int i = 0; i < 6; i++) {
            double radius = 0.5 - (i * 0.075);  
            double x = 2 - i * 0.75;  // Verschieben Sie die Position für jede Kugel nach links
            Sphere sphere = new Sphere(Vector.point(x, radius, -0.5), radius, new GlassMaterial(new ConstantColor(Vector.white), 1.5));
            scene.addShape(sphere);
        }
        
        scene.addShape(background);
        scene.addShape(ground);

        World world = new World(scene);
        Color[] colors = new Color[]{new Color(1,0,0), new Color(0,1,0), new Color(0,0,1)}; // Rot, Grün und Blau
        for (int i = 0; i < 6; i++) {
            double x = 2 - i * 0.75;  // Die x-Position entspricht der Position der Kugeln
            world.addLightSource(new PointLightSource(Vector.point(x, 2, -0.5), colors[i%3], 1)); // Die y-Position ist über den Kugeln
        }
        world.addLightSource(new DirectionLightSource(Vector.direction(-2,-1,-2), Vector.white, 0.25));
        world.addLightSource(new PointLightSource(Vector.point(-1, 1, -1), Vector.blue, 1));
        world.addLightSource(new PointLightSource(Vector.point(-2, 1, -1), Vector.red, 1));
        world.addLightSource(new PointLightSource(Vector.point(1, 1, 0.5), Vector.green, 1));
        CameraObscura camera = new CameraObscura(width, height, Math.PI / 2, Vector.point(0, 1, 2));
        Image shapes = new Image(width, height);
        try {
            shapes.sample(100, scene, camera, new Raytracing(camera, world, 5), 500, 8);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
            
        final String filename = "doc/a11.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }
}
