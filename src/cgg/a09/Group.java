package cgg.a09;

import java.util.ArrayList;

public class Group implements Shape {
    private ArrayList<Shape> shapes;
    private Transformation transformation;

    public Group() {
        shapes = new ArrayList<Shape>();
    }

    public Group(Transformation transformation) {
        shapes = new ArrayList<Shape>();
        this.transformation = transformation;
    }

    public Hit intersect(Ray r) {
        Ray transformedRay = transformation.rayTransformation(r);
        Hit closesHit = null;

        for(Shape shape : shapes) {
            Hit shapeHit = shape.intersect(transformedRay);

            if(shapeHit != null) {
                if(closesHit == null || shapeHit.getRayParameterT() <= closesHit.getRayParameterT()) {
                    closesHit = shapeHit;
                }
            }
        }
        if(closesHit == null){
            return null;
        } 
        return transformation.hitTransformation(closesHit);
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }
}
