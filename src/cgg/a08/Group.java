package cgg.a08;

import java.util.ArrayList;
import cgtools.*;

public class Group implements Shape {
    private ArrayList<Shape> shapes;
    private Transformation transformation;
    private BoundingBox boundingBox;

    public Group() {
        shapes = new ArrayList<Shape>();
        boundingBox = BoundingBox.empty;
    }

    public Group(Transformation transformation) {
        shapes = new ArrayList<Shape>();
        this.transformation = transformation;
    }

    public Hit intersect(Ray r) {
        Ray transformedRay = transformation.rayTransformation(r);
        Hit closesHit = null;
        
        if(!bounds().intersect(transformedRay)) {
            return null;
        }

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

    public BoundingBox bounds() {
        return boundingBox;
    }

    public BoundingBox calculateBounds() {
        this.boundingBox = BoundingBox.empty;
        for(Shape shape : shapes) {
            if(shape instanceof Group && ((Group) shape).getTransformation() != Matrix.identity()) {
                boundingBox = boundingBox.extend(shape.calculateBounds().transform(((Group) shape).getTransformation()));
            } else {
                boundingBox = boundingBox.extend(shape.calculateBounds());
            }
        }
        return boundingBox;
    }

    public Matrix getTransformation() {
        return transformation.getMatrix();
    }

    public BoundingBox getBoundingBox() {
        return null;
    }
}