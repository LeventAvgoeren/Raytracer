package cgg.a05;

import java.util.ArrayList;

public class Group implements Shape {
    private ArrayList<Shape> shapes;

    public Group() {
        shapes = new ArrayList<Shape>();
    }

    public Hit intersect(Ray r) {
        Hit closesHit = null;

        for(Shape shape : shapes) {
            Hit shapeHit = shape.intersect(r);

            if(shapeHit != null) {
                if(closesHit == null || shapeHit.getRayParameterT() <= closesHit.getRayParameterT()) {
                    closesHit = shapeHit;
                }
            }
        } 
        return closesHit;
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }
}
