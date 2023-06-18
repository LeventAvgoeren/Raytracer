  package cgg.a09;

import java.util.concurrent.ExecutionException;

import cgg.Image;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
        final int width = 720;
        final int height = 480;

        Point cameraPosition = new Point(0, 0, 0);
        Matrix translation = Matrix.translation(0, 0, 5);

        // Rotate the camera to look downward
        Matrix rotation = Matrix.rotation(1, 0, 0, 20);

        Matrix transformationMatrix = Matrix.multiply(translation, rotation);

        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2.0, cameraPosition, transformationMatrix);

        Group scene = new Group(new Transformation(Matrix.identity));
        Plane ground = new Plane(Vector.point(0.0, 0.0, 0.0), Vector.direction(0.0, 1.0, 0.0), new GlassMaterial(new ConstantColor(Vector.white), 2.0), Double.POSITIVE_INFINITY);
        Background background = new Background(new BackgroundMaterial(new Texture("doc/Texturen/Himmel.png", Matrix.translation(0, 0, 0))));

        Sphere polkadot = new Sphere(Vector.point(0, 1, -1), 1, new DiffusingMaterial(new PolkaDots(Vector.black, Vector.white, 0.3, Matrix.scaling(10, 10, 10))));
        Sphere merkur = new Sphere(Vector.point(-2.5, 1, -0.2), 1, new BackgroundMaterial(new Texture("doc/Texturen/Merkur.jpeg")));
        Sphere mars = new Sphere(Vector.point(2.5, 1, -0.7), 1, new BackgroundMaterial(new Texture("doc/Texturen/mars.png")));
        Sphere sun = new Sphere(Vector.point(0, 3.0, 0), 1, new BackgroundMaterial(new Texture("doc/Texturen/sonne.png")));
        Sphere pluto = new Sphere(Vector.point(2.5, 3.0, 0), 1, new BackgroundMaterial(new Texture("doc/Texturen/Pluti.jpeg")));
        Sphere jupiter = new Sphere(Vector.point(-2.5, 3.0, 0), 1, new BackgroundMaterial(new Texture("doc/Texturen/Jupiter_by_Cassini-Huygens.jpeg")));
      
        scene.addShape(background);
        scene.addShape(ground);
        scene.addShape(polkadot);
        scene.addShape(merkur);
        scene.addShape(mars);
        scene.addShape(sun);
        scene.addShape(pluto);
        scene.addShape(jupiter);
        
        // Create new camera and sample pictured shapes
        Image shapes = new Image(width, height);
         Raytracing raytracer = new Raytracing(camera, scene, 500);
        try {
            shapes.sample(100, scene, camera, raytracer, 500, 8);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        
        
            
        // Write the images to disk
        final String filename = "doc/a09.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }
}