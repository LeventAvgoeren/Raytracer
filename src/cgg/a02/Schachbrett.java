package cgg.a02;

import cgtools.Color;

public class Schachbrett {

    private double groesse;
    Color color;
    
    public Schachbrett(double groesse) {
        this.groesse = groesse;
    }


    public Color getColor(double x, double y,Color color1,Color color2) {
        //Math.floor rundet double zahlen in int 
        int cellX = (int) Math.floor(x / groesse);
        int cellY = (int) Math.floor(y / groesse);
        if ((cellX + cellY) % 2 == 0) {

            return color1;
        } else {
            return color2;
        }
    }
}
