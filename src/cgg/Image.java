/** @author henrik.tramberend@beuth-hochschule.de */
package cgg;

import cgtools.*;

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
  int i=(y*width+x)*3;
  array[i+0]=color.r();
  array[i+1]=color.g();
  array[i+2]=color.b();
  }

  public void write(String filename) {
    ImageWriter.write(filename,array,width,height);
  }
  private void notYetImplemented() {
    System.err.println("Please complete the implementation of class cgg.Image as part of assignment 1.");
    System.exit(1);
  }
}
