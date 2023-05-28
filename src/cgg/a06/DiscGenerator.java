package cgg.a06;
import java.util.*;
import cgtools.*;
import static cgtools.Vector.*;

public class DiscGenerator {

    double x;
    double y;
    double radius;
    Color color;
    List<DiscGenerator> list = new ArrayList<>();

    public DiscGenerator(int width, int height, int anzahl) {
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < anzahl; i++) {
            double x = random.nextDouble() * width;
            double y = random.nextDouble() * height;
            Color color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble());
            list.add(new DiscGenerator(x, y, random.nextDouble() * 10 + 1, color));
        }
    }

    public DiscGenerator(double x, double y, double radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public boolean isPointInDisc(double x, double y) {
        double kordx = x - this.x;
        double kordy = y - this.y;
        return kordx * kordx + kordy * kordy <= radius * radius;
    }

    public Color coloredDiscs(double x, double y) {
        for (int i = list.size() - 1; i >= 0; i--) {
            DiscGenerator disc = list.get(i);
            if (disc.isPointInDisc(x, y)) {
                return disc.color;
            }
        }
        return black;
    }
}
