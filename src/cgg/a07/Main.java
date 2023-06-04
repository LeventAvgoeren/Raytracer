package cgg.a07;

import cgtools.*;
import cgg.Image;

public class Main {
    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;

        Point cameraPosition = new Point(0, 0, 0);
        Matrix translation = Matrix.translation(0, 10, 25);

        // Rotate the camera to look downward
        Matrix rotation = Matrix.rotation(1, 0, 0, -30);

        Matrix transformationMatrix = Matrix.multiply(translation, rotation);

        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2.0, cameraPosition, transformationMatrix);

        Background background = new Background(new BackgroundMaterial(new Color(1, 1, 1)));
        Plane ground = new Plane(Vector.point(0.0, -0.5, 0.0), Vector.direction(0, 1, 0),
                new DiffusingMaterial(new Color(0.6, 0.2, 0.8)), Double.POSITIVE_INFINITY);
        // Change the color of the ground to dark brown
        Material metalMaterial = new MetallMaterial(new Color(0.8, 0.8, 0.8), 0.5);
        Sphere metalSphere = new Sphere(new Point(0, 0, 0), 0.8, metalMaterial);

        // Create glass sphere next to the metal sphere
        Material glassMaterial = new GlassMaterial(new Color(1.0, 1.0, 1.0), 1.5); // Clear glass with refractive index
                                                                                   // // // 1.5
        Sphere glassSphere = new Sphere(new Point(1.8, 0, 0), 0.8, glassMaterial); // Position (1.8, 0, 0) is next to
                                                                                   // the metal sphere

        double cylinderRadius = 0.5;
        double cylinderHeight = 2;
        Point cylinderBaseCenter = new Point(-1.5, -0.5, 0);
        Point cylinderTopCenter = new Point(cylinderBaseCenter.x(), cylinderBaseCenter.y() + cylinderHeight,
                cylinderBaseCenter.z());

        Sphere cylinderBaseCap = new Sphere(cylinderBaseCenter, cylinderRadius, metalMaterial);
        Sphere cylinderTopCap = new Sphere(cylinderTopCenter, cylinderRadius, metalMaterial);

        Cylinder metallCylinder = new Cylinder(0.3, new Point(-1.5, -0.5, 0), 2, metalMaterial); // Position (-1.0,
                                                                                                 // -0.5, 0) is to the
                                                                                                 // left of the metal
                                                                                                 // sphere
        Group group = new Group(new Transformation(Matrix.identity));
        group.addShape(background);
        group.addShape(ground);
        group.addShape(metalSphere);
        group.addShape(glassSphere);
        group.addShape(metallCylinder);
        group.addShape(cylinderBaseCap);
        group.addShape(cylinderTopCap);

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
                new DiffusingMaterial(new Color(0.6, 0.2, 0.8)), // Purple diffuse material
                new MetallMaterial(new Color(0.8, 0.8, 0.8), 0.5) // Metal material
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
                randomMaterial = new DiffusingMaterial(randomColor);
            } else if (randomMaterial instanceof MetallMaterial) { // Change the color if the material is metal
                randomMaterial = new MetallMaterial(randomColor, 0.5);
            }

            Sphere randomSphere = new Sphere(randomSphereCenter, randomRadius, randomMaterial);
            group.addShape(randomSphere);
        }

        // Variables for tree components
        double treeTrunkRadius = 0.2;
        double treeTrunkHeight = 1.5;
        Material treeTrunkMaterial = new DiffusingMaterial(new Color(0.5, 0.25, 0.0)); // Braun
        Material treeCrownMaterial = new DiffusingMaterial(new Color(0.0, 0.5, 0.0)); // Grün

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
        // Radius und Mittelpunkt des Kreises, in dem die Bäume sind
        double treeCircleRadius2 = 10.0;
        Point treeCircleCenter = new Point(0.0, 0.0, 0.0);

        // Zufällig generierte Koordinaten
        Random rand = new Random();

        for (int i = 0; i < 50; i++) {
            double x, z;
            do {
                // Generieren Sie zufällige x und z Koordinaten zwischen -20 und 20
                x = rand.nextDouble() * 40 - 20;
                z = rand.nextDouble() * 40 - 20;
                // Überprüfen Sie, ob der Abstand vom Mittelpunkt des Kreises größer als der
                // Radius ist
            } while (Math.sqrt(Math.pow(x - treeCircleCenter.x(), 2)
                    + Math.pow(z - treeCircleCenter.z(), 2)) <= treeCircleRadius2);

            // Position des Schneemanns
            Point snowmanPosition = new Point(x, -0.3, z);

            // Erzeugen Sie den Schneemann und fügen Sie ihn zur Gruppe hinzu
            Group snowmanGroup = createSnowman(snowmanPosition, 1.0);
            group.addShape(snowmanGroup);
        }

        // Create smaller sphere next to the tree
        Raytracing raytracer = new Raytracing(camera, group, 100);

        Image shapes = new Image(width, height);
        shapes.sample(10, group, camera, raytracer, 100);

        final String filename = "doc/a09-camera.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }

    // Methode zum Erstellen eines Schneemanns
    private static Group createSnowman(Point position, double scale) {
        Group snowmanGroup = new Group(
                new Transformation(Matrix.translation(position.x(), position.y(), position.z())));
        Material snowMaterial = generateRandomMaterial();
        Material eyeMaterial = new DiffusingMaterial(new Color(0, 0, 0)); // Schwarzes Material

        // Untere Schneemannkugel
        double bottomSnowmanSphereRadius = 0.6 * scale;
        Point bottomSnowmanSphereCenter = new Point(0, -0.3 * scale, 0);
        Sphere bottomSnowmanSphere = new Sphere(bottomSnowmanSphereCenter, bottomSnowmanSphereRadius, snowMaterial);

        // Obere Schneemannkugel
        double topSnowmanSphereRadius = 0.4 * scale;
        Point topSnowmanSphereCenter = new Point(0, -0.3 * scale + bottomSnowmanSphereRadius + topSnowmanSphereRadius,
                0);
        Sphere topSnowmanSphere = new Sphere(topSnowmanSphereCenter, topSnowmanSphereRadius, snowMaterial);

        snowmanGroup.addShape(bottomSnowmanSphere);
        snowmanGroup.addShape(topSnowmanSphere);

        // Augen
        double eyeDistance = 0.3 * scale; // Abstand zwischen den Augen
        double eyeHeight = topSnowmanSphereRadius * 1.7; // Höhe der Augen relativ zur Mitte der oberen Kugel
        double eyeDepth = topSnowmanSphereRadius - 0.1 * scale; // Tiefe der Augen relativ zur Vorderseite der oberen
                                                                // Kugel

        Point leftEyeCenter = new Point(-eyeDistance / 2, eyeHeight, eyeDepth);
        Point rightEyeCenter = new Point(eyeDistance / 2, eyeHeight, eyeDepth);

        double eyeRadius = 0.1 * scale;
        Sphere leftEyeSphere = new Sphere(leftEyeCenter, eyeRadius, eyeMaterial);
        Sphere rightEyeSphere = new Sphere(rightEyeCenter, eyeRadius, eyeMaterial);

        snowmanGroup.addShape(leftEyeSphere);
        snowmanGroup.addShape(rightEyeSphere);

        return snowmanGroup;
    }

    private static Material generateRandomMaterial() {
        Random random = new Random();
        Material[] materials = new Material[] {
                new DiffusingMaterial(generateRandomColor()),
                new MetallMaterial(generateRandomColor(), random.nextDouble()), // Annahme, dass Metall ein
                                                                                // Reflektionsgrad-Parameter hat
                new GlassMaterial(generateRandomColor(), random.nextDouble()) // Annahme, dass Glas einen
                                                                              // Refraktionsindex-Parameter hat
        };

        // Wählen Sie ein zufälliges Material aus dem Array aus
        return materials[random.nextInt(materials.length)];
    }

    private static Color generateRandomColor() {
        Random random = new Random();

        // Erzeugen Sie drei zufällige Zahlen zwischen 0 und 1, die als RGB-Farbwerte
        // verwendet werden
        double r = random.nextDouble();
        double g = random.nextDouble();
        double b = random.nextDouble();

        return new Color(r, g, b);
    }
}
