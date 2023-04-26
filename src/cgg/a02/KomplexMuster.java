
package cgg.a02;

import cgtools.Color;

public class KomplexMuster {

    private double groesse;
    Color color;
    
    public KomplexMuster(double groesse) {
        this.groesse = groesse;
    }


    public Color getColor(double x, double y,Color color1,Color color2) {
        int cellX = (int) Math.floor(x / groesse);      //row 
        int cellY = (int) Math.floor(y / groesse);      //column
        if ((cellX + cellY) %4==0 || cellX %4==0) {
            //hier weiss ändern 
            return color1;
        } else {
            //hier schwarz ändern
            return color2;
        }
    }
}
