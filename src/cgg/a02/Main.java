package cgg.a01;

import cgg.Image;
import cgtools.Color;
import static cgtools.Vector.*;
import cgg.*;

public class Main {

  public static void main(String[] args) {
    final int width = 480;
    final int height = 270;

    // This class instance defines the contents of the image.
    // bei 0,0,0 sieht man nichts 
    KomplexMuster muster=new KomplexMuster(10);

    // Creates an image and iterates over all pixel positions inside the image.
    Image image = new Image(width, height);
    for (int x = 0; x != width; x++) {
      for (int y = 0; y != height; y++) {
        // Sets the color for one particular pixel.
        image.setPixel(x, y, muster.getColor(x,y,red,black));
      }
    }

    // Write the image to disk.
    final String filename = "doc/a01-pattern.png";
    image.write(filename);
    System.out.println("Wrote image: " + filename);
  }
}
