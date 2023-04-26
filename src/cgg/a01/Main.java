package cgg.a01;

import cgg.Image;
import cgg.a02.Disc;
import cgtools.Color;
import static cgtools.Vector.*;
import cgg.*;

public class Main {

  public static void main(String[] args) {
    final int width = 480;
    final int height = 270;

    // This class instance defines the contents of the image.
    // bei 0,0,0 sieht man nichts 

    // Creates an image and iterates over all pixel positions inside the image.
    Image image = new Image(width, height);
    Disc disc= new Disc(width,height,100);

    // Write the image to disk.
    final String filename = "doc/a02-pattern.png";
    image.write(filename);
    System.out.println("Wrote image: " + filename);
  }
}
