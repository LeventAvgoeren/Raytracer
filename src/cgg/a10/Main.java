package cgg.a10;

import cgtools.*;

import java.util.concurrent.ExecutionException;

import cgg.Image;

public class Main {
    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;

        Point cameraPosition = new Point(0, 0, 0);
        Matrix translation = Matrix.translation(0, 0, 5);

        // Rotate the camera to look downward
        Matrix rotation = Matrix.rotation(1, 0, 0, 0);

        Matrix transformationMatrix = Matrix.multiply(translation, rotation);

        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2.0, cameraPosition, transformationMatrix);

         Background background = new Background(new BackgroundMaterial(new Texture("doc/Texturen/Himmel.png", Matrix.translation(0, 0, 0))));

        Plane ground = new Plane(Vector.point(0.0, -0.5, 0.0), Vector.direction(0, 1, 0),
                new DiffusingMaterial(new ConstantColor(new Color(0.6, 0.2, 0.8))), Double.POSITIVE_INFINITY);
        // Change the color of the ground to dark brown
        Material metalMaterial = new MetallMaterial(new ConstantColor(new Color(0.8, 0.8, 0.8)), 0);
        Material metalMaterial2 = new MetallMaterial(new ConstantColor(new Color(0.8, 0.8, 0.8)),1);
        Sphere metalSphere = new Sphere(new Point(0, 0, 0), 0.8, metalMaterial);

        // Create glass sphere next to the metal sphere
        Material glassMaterial = new GlassMaterial(new ConstantColor(new Color(1.0, 1.0, 1.0)), 1.5); // Clear glass with refractive index
                                                                                   // // // 1.5
        Sphere glassSphere = new Sphere(new Point(1.8, 0, 0), 0.8, glassMaterial); // Position (1.8, 0, 0) is next to
                                                                                   // the metal sphere
        
         Sphere metalSphere2 = new Sphere(new Point(-1.8, 0, 0), 0.8, metalMaterial2);

        Group group = new Group(new Transformation(Matrix.identity));
        group.addShape(background);
        group.addShape(ground);
        group.addShape(metalSphere);
        group.addShape(glassSphere);
        group.addShape(metalSphere2);

        // Grey diffuse material

        // Create random spheres around the tree circle
        double circleRadius = 6.0; // Use the same circle radius as the tree circle

        double minRadius = 0.1;
        double maxRadius = 0.4;
        double minX = -25.0; // Define the area where the spheres can be generated
        double maxX = 25.0;
        double minZ = -25.0;
        double maxZ = 25.0;
        double treeCircleRadius = 4.0; // Radius of the tree circle
        Material[] materials = {
                new DiffusingMaterial(new ConstantColor(new Color(0.6, 0.2, 0.8))), // Purple diffuse material
                new MetallMaterial(new ConstantColor(new Color(0.8, 0.8, 0.8)), 1) // Metal material
        };
        // Generate random spheres
        for (int i = 0; i < 150; i++) { // Create 50 random spheres
            double randomRadius = minRadius + Math.random() * (maxRadius - minRadius); // Random radius between
                                                                                       // minRadius and maxRadius
            double x;
            double z;

            // Generate a position that is not within the tree circle
            do {
                x = minX + Math.random() * (maxX - minX); // Random x-coordinate
                z = minZ + Math.random() * (maxZ - minZ); // Random z-coordinate
            } while (Math.sqrt(x * x + z * z) < treeCircleRadius + maxRadius); // Retry if the position is within the
                                                                               // tree circle

            Point randomSphereCenter = new Point(x, -0.5 + randomRadius, z); // Positioned on the ground based on its
                                                                             // size

            Material randomMaterial = materials[(int) (Math.random() * materials.length)]; // Random material
            Color randomColor = new Color(Math.random(), Math.random(), Math.random()); // Random color
            if (randomMaterial instanceof DiffusingMaterial) { // Change the color if the material is diffusing
                randomMaterial = new DiffusingMaterial(new ConstantColor(randomColor));
            } else if (randomMaterial instanceof MetallMaterial) { // Change the color if the material is metal
                randomMaterial = new MetallMaterial(new cgg.a09.ConstantColor(randomColor), 1);
            }

            Sphere randomSphere = new Sphere(randomSphereCenter, randomRadius, randomMaterial);
            group.addShape(randomSphere);
        }

        // Variables for tree components
        double treeTrunkRadius = 0.2;
        double treeTrunkHeight = 1.5;
        Material treeTrunkMaterial = new DiffusingMaterial(new ConstantColor(new Color(0.5, 0.25, 0.0))); // Braun
        Material treeCrownMaterial = new DiffusingMaterial(new ConstantColor(new Color(0.0, 0.5, 0.0))); // Grün

        /// Variables for tree crown components
        int numberOfCrownSpheres = 10; // The number of spheres to use for each tree crown
        double minCrownSphereRadius = 0.3; // The minimum radius of the crown spheres
        double maxCrownSphereRadius = 0.6; // The maximum radius of the crown spheres

        // Create multiple trees
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * i / 20;

            double x = circleRadius * Math.cos(angle);
            double z = circleRadius * Math.sin(angle);

            // Trunk
            Point treeTrunkCenter = new Point(x, -0.5, z);
            Cylinder treeTrunk = new Cylinder(treeTrunkRadius, treeTrunkCenter, treeTrunkHeight, treeTrunkMaterial);
            group.addShape(treeTrunk);

            // Crown
            Point treeCrownCenter = new Point(treeTrunkCenter.x(), treeTrunkCenter.y() + treeTrunkHeight,
                    treeTrunkCenter.z());
            Matrix identityMatrix = Matrix.identity(); // Create the identity matrix
            Group treeCrownGroup = new Group(new Transformation(identityMatrix)); // Create a new group for each tree
                                                                                  // crown

            // Generate multiple spheres for the tree crown
            for (int j = 0; j < numberOfCrownSpheres; j++) {
                double crownSphereRadius = minCrownSphereRadius
                        + Math.random() * (maxCrownSphereRadius - minCrownSphereRadius); // Random radius between
                                                                                         // minCrownSphereRadius and
                                                                                         // maxCrownSphereRadius

                double xOffset = crownSphereRadius * (Math.random() - 0.5); // Random x-coordinate offset
                double yOffset = crownSphereRadius * (Math.random() - 0.5); // Random y-coordinate offset
                double zOffset = crownSphereRadius * (Math.random() - 0.5); // Random z-coordinate offset

                Point crownSphereCenter = new Point(treeCrownCenter.x() + xOffset, treeCrownCenter.y() + yOffset,
                        treeCrownCenter.z() + zOffset);

                Sphere crownSphere = new Sphere(crownSphereCenter, crownSphereRadius, treeCrownMaterial);
                treeCrownGroup.addShape(crownSphere); // Add the sphere to the tree crown group
            }

            group.addShape(treeCrownGroup); // Add the tree crown group to the main group

        }

        // Create smaller sphere next to the tree
        Raytracing raytracer = new Raytracing(camera, group, 500);

        Image shapes = new Image(width, height);
        //try {
          //  shapes.sample(100, group, camera, raytracer, 500, 8);
        //} catch (InterruptedException | ExecutionException e) {
         //   e.printStackTrace();
       // }

        final String filename = "doc/a10.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }
}
