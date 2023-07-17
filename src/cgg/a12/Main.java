package cgg.a12;

import java.util.concurrent.ExecutionException;

import cgg.Image;
import cgg.a09.ConstantColor;
import cgtools.*;

public class Main {
    public static void main(String[] args) {
         final int width = 720;
        final int height = 480;

        Group scene = new Group(new Transformation(Matrix.identity));
        Plane ground = new Plane(Vector.point(0.0, 0.0, 0.0), Vector.direction(0.0, 1.0, 0.0),
                new GlassMaterial(new ConstantColor(Vector.black), 2), Double.POSITIVE_INFINITY);

        Background background = new Background(
                new BackgroundMaterial(new Texture("doc/Texturen/Himmel.png", Matrix.translation(0, 0, 0))));

        double angleStep = Math.PI / 3; // Der Winkel ändert sich bei jedem Schritt um Pi/3
        double radius = 0.5;
        for (int i = 0; i < 12; i++) {
            double x = Math.cos(i * angleStep);
            double y = i * 0.2; // Die Kugeln steigen nach oben
            double z = Math.sin(i * angleStep);
            Sphere sphere = new Sphere(Vector.point(x, y, z), radius,
                    new GlassMaterial(new ConstantColor(Vector.white), 1.5));
            scene.addShape(sphere);
        }

        // Fügen Sie einen Zylinder hinzu, der von der Basis bis zum höchsten Punkt der
        // Kugeln geht
        Cylinder cylinder = new Cylinder(0.2, Vector.point(0, 0, 0), 12 * 0.2,
                new DiffusingMaterial(new ConstantColor(new Color(0, 0, 0)))); // Weißer Zylinder mit DiffusingMaterial
        scene.addShape(cylinder);

        scene.addShape(background);
        scene.addShape(ground);

        int numberOfSnowmen = 25;

        for (int i = 0; i < numberOfSnowmen; i++) {
        Point position = generateRandomPosition(-9, 9, 0, 0, -15, 6, 6); // Mindestabstand zur Kamera ist jetzt 5
        double scale = 1.0;
        Group snowman = createSnowman(position, scale);
        scene.addShape(snowman);
        }


        Point treePosition = new Point(Math.cos(11 * angleStep), 12 * 0.2, Math.sin(11 * angleStep)); // Ändern Sie
                                                                                                      // diese Werte, um
                                                                                                      // die Position
                                                                                                      // des Baumes zu
                                                                                                      // bestimmen
        Point treePosition1 = new Point(Math.cos(9 * angleStep), 9 * 0.2, Math.sin(9 * angleStep));

        // Erzeugen Sie die Bäume und fügen Sie sie zur Szene hinzu
        Group tree1 = createTree(treePosition1, 1.0);
        scene.addShape(tree1);

        Group tree = createTree(treePosition, 1.0);
        scene.addShape(tree);
        
       Material towerMaterial = new DiffusingMaterial(new ConstantColor(new Color(0, 0, 1))); // Blaues Material für den Turm
       Point towerBaseCenter = new Point(10, 0, -15); // Basiskoordinaten des Turms
        createTower(scene, towerMaterial, towerBaseCenter);

         Material towerMaterial2 = new DiffusingMaterial(new ConstantColor(new Color(0, 0, 1))); // Blaues Material für den Turm
       Point towerBaseCenter2 = new Point(-10, 0, -15); // Basiskoordinaten des Turms
        createTower(scene, towerMaterial2, towerBaseCenter2);

        Material towerMaterial3 = new DiffusingMaterial(new ConstantColor(new Color(1, 0, 0))); // Blaues Material für den Turm
       Point towerBaseCenter3 = new Point(15, 0, -15); // Basiskoordinaten des Turms
        createTower(scene, towerMaterial3, towerBaseCenter3);

        Material towerMaterial4 = new DiffusingMaterial(new ConstantColor(new Color(1, 0, 0))); // Blaues Material für den Turm
       Point towerBaseCenter4 = new Point(-15, 0, -15); // Basiskoordinaten des Turms
        createTower(scene, towerMaterial4, towerBaseCenter4);

        World world = new World(scene);
        Color[] colors = new Color[] { new Color(1, 0, 0), new Color(0, 1, 0), new Color(0, 0, 1) }; // Rot, Grün und //
                                                                                                     // Blau
        for (int i = 0; i < 12; i++) {
            double x = Math.cos(i * angleStep);
            double y = i * 0.2 + 0.6; // Die Lichtquellen befinden sich über den Kugeln
            double z = Math.sin(i * angleStep);
            world.addLightSource(new PointLightSource(Vector.point(x, y, z), colors[i % 3], 1));
        }
        world.addLightSource(new DirectionLightSource(Vector.direction(-2, -1, -2), Vector.white, 0.25));
        world.addLightSource(new PointLightSource(Vector.point(12, 10, -5), Vector.blue, 1));
        world.addLightSource(new PointLightSource(Vector.point(-10, 6, -5), Vector.red, 1));
        world.addLightSource(new PointLightSource(Vector.point(-5, 4, 1), Vector.green, 1));

        CameraObscura camera = new CameraObscura(width, height, Math.PI / 2, Vector.point(0, 2, 5)); // Kamera weiter
                                                                                                     // weg
        Image shapes = new Image(width, height);
        try {
            shapes.sample(100, scene, camera, new Raytracing(camera, world, 5), 500, 8);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        final String filename = "doc/cgg-competition-ss-23-941761.png";
        shapes.write(filename);
        System.out.println("Wrote image: " + filename);

    }

    static Group createSnowman(Point position, double scale) {
        Group snowmanGroup = new Group(
                new Transformation(Matrix.translation(position.x(), position.y(), position.z())));
        Material snowMaterial = generateRandomMaterial();
        Material eyeMaterial = new DiffusingMaterial(new ConstantColor(new Color(0, 0, 0))); // Schwarzes Material

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
                new DiffusingMaterial(new ConstantColor(generateRandomColor())),
                new MetallMaterial(new ConstantColor(generateRandomColor()), random.nextDouble()), // Annahme, dass
                                                                                                   // Metall ein
                // Reflektionsgrad-Parameter hat
                new GlassMaterial(new ConstantColor(generateRandomColor()), random.nextDouble()) // Annahme, dass Glas
                                                                                                 // einen
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

    private static Point generateRandomPosition(double minX, double maxX, double minY, double maxY, double minZ,
            double maxZ) {
        Random random = new Random();

        double x = minX + (maxX - minX) * random.nextDouble();
        double y = minY + (maxY - minY) * random.nextDouble();
        double z = minZ + (maxZ - minZ) * random.nextDouble();

        return new Point(x, y, z);
    }

    static Group createTree(Point position, double scale) {
        Group treeGroup = new Group(new Transformation(Matrix.translation(position.x(), position.y(), position.z())));
        Material leavesMaterial = new DiffusingMaterial(new ConstantColor(new Color(0, 1, 0))); // Grünes Material für
                                                                                                // Blätter
        Material trunkMaterial = new DiffusingMaterial(new ConstantColor(new Color(0.5, 0.25, 0))); // Braunes Material
                                                                                                    // für den Stamm

        // Erzeugen Sie einen Zylinder für den Stamm
        Cylinder trunk = new Cylinder(0.1 * scale, Vector.point(0, 0, 0), 1 * scale, trunkMaterial);
        treeGroup.addShape(trunk);

        // Erzeugen Sie einige Kugeln, um die Krone des Baumes zu simulieren
        for (int i = 0; i < 5; i++) {
            double y = i * 0.2 * scale + 1 * scale; // Die Kugeln steigen nach oben
            Sphere sphere = new Sphere(Vector.point(0, y, 0), 0.5 * scale, leavesMaterial);
            treeGroup.addShape(sphere);
        }

        return treeGroup;
    }
    private static Point generateRandomPosition(double minX, double maxX, double minY, double maxY, double minZ, double maxZ, double minCameraDistance) {
    Random random = new Random();

    double x, y, z;
    do {
        x = minX + (maxX - minX) * random.nextDouble();
        y = minY + (maxY - minY) * random.nextDouble();
        z = minZ + (maxZ - minZ) * random.nextDouble();
    } while (Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) < minCameraDistance); // z-Wert muss größer als der Abstand zur Kamera sein

    return new Point(x, y, z);
}
private static void createTower(Group group, Material material, Point baseCenter) {
    double cylinderRadius = 0.3;
    double cylinderHeight = 1;
    double sphereRadius = 0.5;

    for (int i = 0; i < 5; i++) { // 5 cylinders and spheres stacked on top of each other
        Point cylinderCenter = new Point(baseCenter.x(), baseCenter.y() + i * (cylinderHeight + sphereRadius), baseCenter.z());
        Cylinder cylinder = new Cylinder(cylinderRadius, cylinderCenter, cylinderHeight, material);
        group.addShape(cylinder);

        Point sphereCenter = new Point(cylinderCenter.x(), cylinderCenter.y() + cylinderHeight + sphereRadius / 2, cylinderCenter.z());
        Sphere sphere = new Sphere(sphereCenter, sphereRadius, material);
        group.addShape(sphere);
    }
}

}
