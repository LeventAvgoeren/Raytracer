/** @author henrik.tramberend@beuth-hochschule.de */
package cgg;
import java.util.*;
import cgtools.*;
import cgtools.Random;

import static cgtools.Vector.*;
import cgg.a02.Disc;

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

  public void sample(Disc disc, int runden) {
    for (int x = 0; x < this.width; x++) {
        for (int y = 0; y < this.height; y++) {
            Color sampleColor = new Color(0, 0, 0);
            for (int i = 0; i < runden; i++) {
                Color pixelColor = disc.coloredDiscs(x + Random.random(), y + Random.random());
                sampleColor = new Color(sampleColor.r() + pixelColor.r(), sampleColor.g() + pixelColor.g(), sampleColor.b() + pixelColor.b());
            }
            sampleColor = new Color(sampleColor.r() / runden, sampleColor.g() / runden, sampleColor.b() / runden);
            setPixel(x, y, sampleColor);
        }
    }

  }
}
