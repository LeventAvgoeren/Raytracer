package cgg;

import cgtools.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import cgg.a08.*;


public class Image {
  double[] comp;
  int width;
  int height;

  public Image(int width, int height) {

    this.width = width;
    this.height = height;
    this.comp = new double[width * height * 3];

  }

  public void setPixel(int x, int y, Color color) {
    int i = 3 * (y * width + x);
    double gamma = 2.2;
    comp[i + 0] = Math.pow(color.r(), 1/gamma);
    comp[i + 1] = Math.pow(color.g(), 1/gamma);
    comp[i + 2] = Math.pow(color.b(), 1/gamma);

  }


 
  public void sample(int sampleRate, Group group, PinholeCamera camera, Raytracing raytracer, int recursionDepth, int threadCount) throws InterruptedException, ExecutionException {
    Color backgroundColor = new Color(0.5f, 0.7f, 0.9f);

    // Thread-Pool erstellen
    ExecutorService pool = Executors.newFixedThreadPool(threadCount);

    // Fortschritt in Thread-sichere Variable auslagern
    AtomicInteger progressCounter = new AtomicInteger();

    // Batching der Pixel-Reihen in größere Aufgaben
    int batchSize = Math.max(height / threadCount, 1);

    List<Future<Void>> futures = new ArrayList<>();

    for (int xPosition = 0; xPosition < width; xPosition++) {
        final int x = xPosition;

        // Gruppe von Pixel-Reihen als Aufgabe einreichen
        Future<Void> future = pool.submit(() -> {
            for (int batchStart = 0; batchStart < height; batchStart += batchSize) {
                int batchEnd = Math.min(batchStart + batchSize, height);
                for (int yPosition = batchStart; yPosition < batchEnd; yPosition++) {
                    final int y = yPosition;
                    Color accumulatedColor = new Color(0, 0, 0);
                    for (int sampleIndex = 0; sampleIndex < sampleRate; sampleIndex++) {
                        Ray ray = camera.generateRay(x, y);
                        Hit nearestHit = group.intersect(ray);
                        Color currentPixelColor;
                        if (nearestHit != null) {
                            currentPixelColor = raytracer.calculateRadiance(group, ray, recursionDepth);
                        } else {
                            currentPixelColor = backgroundColor;
                        }
                        accumulatedColor = Vector.add(accumulatedColor, currentPixelColor);
                    }
                    Color finalColor = Vector.divide(accumulatedColor, sampleRate);
                    setPixel(x, y, finalColor);
                }
            }

            // Fortschritt anzeigen
            int progress = progressCounter.incrementAndGet();
            System.out.printf("Rendering progress: %.2f%%\n", (double) progress / width * 100);

            return null;
        });

        futures.add(future);
    }

    // Warten, bis alle Aufgaben abgeschlossen sind
    for (Future<Void> future : futures) {
        try {
            future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    pool.shutdown();
}










  public void write(String filename) {
    // Use cggtools.ImageWriter.write() to implement this.

    ImageWriter.write(filename, comp, width, height);;
  }
}
