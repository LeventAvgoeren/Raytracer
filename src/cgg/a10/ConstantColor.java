package cgg.a10;

import cgtools.*;

public class ConstantColor implements Sampler{
    
    Color color;

    public  ConstantColor (Color color) {
        this.color = color;
    }

    public Color getColor(double x, double y) {
        return color;
    }
}
