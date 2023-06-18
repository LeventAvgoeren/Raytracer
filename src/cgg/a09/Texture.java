package cgg.a09;

import cgtools.*;

public class Texture implements Sampler {

    ImageTexture texture;
    Matrix transformation;
    int width;
    int height;

    public Texture(String fileName) {
        texture = new ImageTexture(fileName);
        this.transformation = Matrix.identity();
        this.width = texture.width;
        this.height = texture.height;
    }

    public Texture(String fileName, Matrix transformation) {
        texture = new ImageTexture(fileName);
        this.transformation = transformation;
        this.width = texture.width;
        this.height = texture.height;
    }

    public Color getColor(double x, double y) {
        Point xy = Matrix.multiply(transformation, Vector.point(x, y, 0));
        double ri = Math.pow(texture.getColor(xy.x(), xy.y()).r(), 2.2);
        double gi = Math.pow(texture.getColor(xy.x(), xy.y()).g(), 2.2);
        double bi = Math.pow(texture.getColor(xy.x(), xy.y()).b(), 2.2);
        Color color = new Color(ri, gi, bi);
        return color;
    }
    
}