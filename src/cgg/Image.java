/** @author henrik.tramberend@beuth-hochschule.de */
package cgg;

import cgtools.*;

import java.util.ArrayList;

import cgg.a02.Disc;
import cgg.a04.Group;
import cgg.a04.Hit;
import cgg.a04.Sphere;
import cgg.a04.PinholeCamera;
import cgg.a04.Ray;
import cgg.a04.Raytracing;


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
  

  public void sample(int sampleRate, Group group, PinholeCamera camera) {
    Color backgroundColor = new Color(0.5f, 0.7f, 0.9f);

    for (int xPosition = 0; xPosition < width; xPosition++) {
        for (int yPosition = 0; yPosition < height; yPosition++) {
            Color accumulatedColor = new Color(0, 0, 0);
            for (int sampleIndex = 0; sampleIndex < sampleRate; sampleIndex++) {
                Ray ray = camera.generateRay(xPosition, yPosition);
                
                Hit nearestHit = group.intersect(ray);
                Color currentPixelColor;
                if (nearestHit != null) {
                    currentPixelColor = Raytracing.shade(nearestHit.getNormVec(), nearestHit.getHitPointColor());
                } else {
                    currentPixelColor = backgroundColor;
                }
                accumulatedColor = new Color(accumulatedColor.r() + currentPixelColor.r(), accumulatedColor.g() + currentPixelColor.g(), accumulatedColor.b() + currentPixelColor.b());
            }
            Color finalColor = new Color(accumulatedColor.r() / sampleRate, accumulatedColor.g() / sampleRate, accumulatedColor.b() / sampleRate);
            setPixel(xPosition, yPosition, finalColor);
        }
    }
}
  public void write(String filename) {
    // Use cggtools.ImageWriter.write() to implement this.
    
    ImageWriter.write(filename, comp, width, height);;
   
  }

public void sample(Raytracing raytracer, boolean b) {
}

}