package cgg.a02;
import java.util.*;
import cgtools.*;
import static cgtools.Vector.*;

public class Disc {

    double x;
    double y;
    double radius;
    Color color;
    List <Disc> list = new ArrayList<>();
    /**
     über die anzahl itterieren dann alles sachen random setzten
     und in die liste packen 
     um abstand zu berechnen x-x 
    */
    
        public Disc(int width, int height, int anzahl){

            
            java.util.Random random = new java.util.Random();
            for (int i = 0; i < anzahl; i++) {
                double x = random.nextDouble() * width;
                double y = random.nextDouble() * height;
                Color color = new Color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                list.add(new Disc(x, y, random.nextDouble()*10+1, color));
            }
        }

    public Disc(double x,double y,double radius ,Color color){
        this.x=x;
        this.y=y;
        this.radius=radius;
        this.color=color;
    }
    

    public boolean isPointInDisc( double x,double y){
         double kordx= x-this.x;
         double kordy= y-this.y;
         return kordx*kordx + kordy*kordy <= radius * radius;
}
    public Color coloredDiscs(double x, double y){
        for (int i = list.size() - 1; i >= 0; i--) {
              Disc disc=list.get(i);
              if(disc.isPointInDisc(x, y)){
                return disc.color;
              }
        }
        return black ;
    }
}
