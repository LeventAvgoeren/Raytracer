package cgg.a02;
import cgg.Image;




public class Main {

  public static void main(String[] args) {
    final int width = 480;
    final int height = 270;

    // This class instance defines the contents of the image.
    // bei 0,0,0 sieht man nichts 
    Disc disc = new Disc(width,height,4);


    // Creates an image and iterates over all pixel positions inside the image.
    Image image = new Image(width, height);
        // Sets the color for one particular pixel

        // Write the image to disk.
    final String filename = "doc/a02-discs-supersampling.png";
    image.write(filename);
    System.out.println("Wrote image: " + filename);

    }
  }
