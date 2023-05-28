package cgg.a06;

import cgtools.*;
import cgg.Image;

public class Main {
    public static void main(String[] args) {
        final int width = 1280;
        final int height = 720;

        Point cameraPosition = new Point(0, 0, 0);
        Matrix identity = Matrix.identity();
        Matrix translation = Matrix.translation(0, 10, 15);
        Matrix rotation = Matrix.rotation(1, 0, 0, -30);
        Matrix transformationMatrix = Matrix.multiply(translation, rotation);

        PinholeCamera camera = new PinholeCamera(width, height, Math.PI / 2.0, cameraPosition, transformationMatrix);
        Background background = new Background(new BackgroundMaterial(new Color(1, 1, 1)));
        Plane ground = new Plane(Vector.point(0.0, -0.5, 0.0), Vector.direction(0, 1, 0), new DiffusingMaterial(new Color(0.6, 0.2, 0.8)), Double.POSITIVE_INFINITY);
        // Change the color of the ground to dark brown
        Material metalMaterial = new MetallMaterial(new Color(0.8, 0.8, 0.8), 0.5);
        Sphere metalSphere = new Sphere(new Point(0, 0, 0), 0.8, metalMaterial);

        // Create glass sphere next to the metal sphere
        Material glassMaterial = new GlassMaterial(new Color(1.0, 1.0, 1.0), 1.5); // Clear glass with refractive index 1.5
        Sphere glassSphere = new Sphere(new Point(1.8, 0, 0), 0.8, glassMaterial); // Position (1.8, 0, 0) is next to the metal sphere

        double cylinderRadius = 0.5;
        double cylinderHeight = 2;
        Point cylinderBaseCenter = new Point(-1.5, -0.5, 0);
        Point cylinderTopCenter = new Point(cylinderBaseCenter.x(), cylinderBaseCenter.y() + cylinderHeight, cylinderBaseCenter.z());

        Sphere cylinderBaseCap = new Sphere(cylinderBaseCenter, cylinderRadius, metalMaterial);
        Sphere cylinderTopCap = new Sphere(cylinderTopCenter, cylinderRadius, metalMaterial);

        Cylinder metallCylinder = new Cylinder(0.3, new Point(-1.5, -0.5, 0), 2, metalMaterial); // Position (-1.0, -0.5, 0) is to the left of the metal sphere
        Group group = new Group();
        group.addShape(background);
        group.addShape(ground);
        group.addShape(metalSphere);
        group.addShape(glassSphere);
        group.addShape(metallCylinder);
        group.addShape(cylinderBaseCap);
        group.addShape(cylinderTopCap);

        // Grey diffuse material
        Material towerMaterial = new MetallMaterial(new Color(0.8, 0.8, 0.8), 0.5); // Metallmaterial

        Point leftTowerBase = new Point(4, 0.5, 4); // Moved closer to the front
        Point rightTowerBase = new Point(-4, 0.5, 4); // Moved closer to the front
        Material randomSphereMaterial = new DiffusingMaterial(new Color(0.6, 0.2, 0.8)); // Purple diffuse material
        createTower(group, towerMaterial, leftTowerBase); // Tower to the left
        createTower(group, towerMaterial, rightTowerBase); // Tower to the right
        createConnection(group, towerMaterial, leftTowerBase, rightTowerBase); // Connection between towers

        // Create random spheres around the tree circle
        double circleRadius = 4.0; // Use the same circle radius as the tree circle

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
            double randomRadius = minRadius + Math.random() * (maxRadius - minRadius); // Random radius between minRadius and maxRadius
            double x;
            double z;

            // Generate a position that is not within the tree circle
            do {
                x = minX + Math.random() * (maxX - minX); // Random x-coordinate
                z = minZ + Math.random() * (maxZ - minZ); // Random z-coordinate
            } while (Math.sqrt(x * x + z * z) < treeCircleRadius + maxRadius); // Retry if the position is within the tree circle

            Point randomSphereCenter = new Point(x, -0.5 + randomRadius, z); // Positioned on the ground based on its size

            Material randomMaterial = materials[(int)(Math.random() * materials.length)]; // Random material
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

        double treeCrownRadius = 0.6;
        Material treeCrownMaterial = new DiffusingMaterial(new Color(0.0, 0.5, 0.0)); // Grün

        // Create multiple trees
        for (int i = 0; i < 20; i++) {
            double angle = 2 * Math.PI * i / 20;

            double x = circleRadius * Math.cos(angle);
            double z = circleRadius * Math.sin(angle);

            // Stamm
            Point treeTrunkCenter = new Point(x, -0.5, z);
            Cylinder treeTrunk = new Cylinder(treeTrunkRadius, treeTrunkCenter, treeTrunkHeight, treeTrunkMaterial);
            group.addShape(treeTrunk);

            // Krone
            Point treeCrownCenter = new Point(treeTrunkCenter.x(), treeTrunkCenter.y() + treeTrunkHeight, treeTrunkCenter.z());
            Sphere treeCrown = new Sphere(treeCrownCenter, treeCrownRadius, treeCrownMaterial);
            group.addShape(treeCrown);
        }

        Raytracing raytracer = new Raytracing(camera, group, 500);

        Image shapes = new Image(width, height);
        shapes.sample(100, group, camera, raytracer, 500);

        final String filename = "doc/a6-camera.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);
    }

    private static void createTower(Group group, Material material, Point baseCenter) {
        double cylinderRadius = 0.3;
        double cylinderHeight = 1;
        double sphereRadius = 0.5;

        for (int i = 0; i < 5; i++) { // 5 cylinders and spheres stacked on top of each other
            Point cylinderCenter = new Point(baseCenter.x(), baseCenter.y() + (i * (cylinderHeight + sphereRadius * 2)), baseCenter.z());
            Cylinder cylinder = new Cylinder(cylinderRadius, cylinderCenter, cylinderHeight, material);
            group.addShape(cylinder);

            Point sphereCenter = new Point(cylinderCenter.x(), cylinderCenter.y() + cylinderHeight + sphereRadius, cylinderCenter.z());
            Sphere sphere = new Sphere(sphereCenter, sphereRadius, material);
            group.addShape(sphere);
        }
    }

    private static void createConnection(Group group, Material material, Point leftTowerTop, Point rightTowerTop) {
        double sphereRadius = 0.3;
        double totalHeight = 9; // Adjust as needed

        double xStart = leftTowerTop.x();
        double xEnd = rightTowerTop.x();
        double y = leftTowerTop.y() + totalHeight;
        double z = (leftTowerTop.z() + rightTowerTop.z()) / 2;

        int numSpheres = 20; // The number of spheres to use in the connection
        for (int i = 0; i < numSpheres; i++) {
            double x = xStart + (xEnd - xStart) * i / (numSpheres - 1);
            Point sphereCenter = new Point(x, y, z);
            Sphere sphere = new Sphere(sphereCenter, sphereRadius, material);
            group.addShape(sphere);
        }
    }
}
