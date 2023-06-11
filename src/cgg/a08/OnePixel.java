package cgg.a08;

import java.util.concurrent.Callable;

import cgtools.Color;
import cgtools.Sampler;
import cgtools.Vector;

public record OnePixel(Sampler content, int x, int y, int n) implements Callable<Color> {

    public Color call() {
        Color color = Vector.color(0);

        for (int xi = 0; xi < n; xi++) {
            for (int yi = 0; yi < n; yi++) {
                double rx = cgtools.Random.random();
                double ry = cgtools.Random.random();
                double xs = x + (xi + rx) / n;
                double ys = y + (yi + ry) / n;
                color = Vector.add(color, content.getColor(xs, ys));
            }
        }
        color = Vector.divide(color, n * n);
        return color;
    }
}
