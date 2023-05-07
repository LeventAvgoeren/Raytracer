/** @author henrik.tramberend@beuth-hochschule.de */
package cgg;
import java.util.*;

import cgtools.*;
import cgtools.Random;

import static cgtools.Vector.*;
import cgg.a02.Disc;
import cgg.a03.Hit;
import cgg.a03.PinholeCamera;
import cgg.a03.Ray;
import cgg.a03.Raytracing;
import cgg.a03.Sphere;

public class Image {

  double [] array;
  int width;
  int height;

  public Image(int width, int height) {
    this.width=width;
    this.height=height;
    this.array= new double[width*height*3];
  }

  public void setPixel(int x, int y, Color color) {
    //zu weisung der pixel 
    int i = 3 * (y * width + x);
    double gamma = 2.2;
    array[i + 0] = Math.pow(color.r(), 1/gamma);
    array[i + 1] = Math.pow(color.g(), 1/gamma);
    array[i + 2] = Math.pow(color.b(), 1/gamma);
  }

  public void write(String filename) {
    ImageWriter.write(filename,array,width,height);
  }

  public void sample(int sampleRate, ArrayList<Sphere> spheres, PinholeCamera camera) {
    Color backgroundColor = new Color(0.5f, 0.7f, 0.9f); // Ändern Sie die Werte für die gewünschte Hintergrundfarbe

    for (int xPosition = 0; xPosition < width; xPosition++) {
        for (int yPosition = 0; yPosition < height; yPosition++) {
            Color accumulatedColor = new Color(0, 0, 0);
            for (int sampleIndex = 0; sampleIndex < sampleRate; sampleIndex++) {
                Ray ray = camera.generateRay(xPosition, yPosition);
                
                Hit nearestHit = null;
                for (Sphere sphere : spheres) {
                    Hit hit = sphere.intersect(ray);
                    if (hit != null) {
                        if ((nearestHit == null) || (hit.getRayParameterT() < nearestHit.getRayParameterT())) {
                            nearestHit = hit;
                        }
                    }
                }
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
}

