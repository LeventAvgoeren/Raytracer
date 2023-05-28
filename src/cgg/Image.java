// @author henrik.tramberend@beuth-hochschule.de /
package cgg;

import cgtools.*;
import cgg.a06.*;


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


  public void sample(int sampleRate, Group group, PinholeCamera camera, Raytracing raytracer, int recursionDepth) {
    Color backgroundColor = new Color(0.5f, 0.7f, 0.9f);

    for (int xPosition = 0; xPosition < width; xPosition++) {
        for (int yPosition = 0; yPosition < height; yPosition++) {
            Color accumulatedColor = new Color(0, 0, 0);
            for (int sampleIndex = 0; sampleIndex < sampleRate; sampleIndex++) {
                Ray ray = camera.generateRay(xPosition, yPosition);
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
            setPixel(xPosition, yPosition, finalColor);
        }
    }
}







  public void write(String filename) {
    // Use cggtools.ImageWriter.write() to implement this.

    ImageWriter.write(filename, comp, width, height);;
  }
}