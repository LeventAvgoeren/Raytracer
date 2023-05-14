package cgg.a04;
import cgg.Image;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
        final int sWidth = 480;
        final int sHeight = 270;

        PinholeCamera sCamera = new PinholeCamera(sWidth, sHeight, Math.PI / 3);
        Shape sBackground = new Background(new Color(0, 1, 0));
        Shape sGround = new Plane(new Point(0.0, -0.5, 0.0), new Direction(0, 1, 0), new Color(0, 0, 1), Double.POSITIVE_INFINITY);
        
        Shape sGlobe1 = new Sphere(new Point(-1.0, 0.25, -2.5), 0.7, new Color(1, 0, 0));
        Shape sGlobe2 = new Sphere(new Point(0.0, -0.25, -2.5), 0.5, new Color(0, 1, 0));
        
        Shape sGlobe3 = new Sphere(new Point(1.0, -0.35, -3.0), 0.7, new Color(0, 2, 2));

        Group sGroup = new Group();
        sGroup.addShape(sBackground);
        sGroup.addShape(sGround);
        sGroup.addShape(sGlobe1);
        sGroup.addShape(sGlobe2);
        sGroup.addShape(sGlobe3);

        Image sImage = new Image(sWidth, sHeight);
        sImage.sample(8, sGroup, sCamera);

        // Write the image to disk
        final String filename = "doc/a04-scene.png";
        sImage.write(filename);
        System.out.println("Wrote image: " + filename);
    }
}
