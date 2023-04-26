package cgg.a03;
import cgg.Image;
import cgg.a02.Disc;
import cgtools.Color;
import cgtools.Vector;

import static cgtools.Vector.*;
import cgg.*;

public class Ray {

        Vector origin;
        Vector direction;
        double tMin;
        double tMax;
    
        public Ray(Vector origin, Vector direction, double tMin, double tMax) {
            this.origin = origin;
            this.direction = direction;
            this.tMin = tMin;
            this.tMax = tMax;
        }
    
        public Vector pointAt(double t) {
            return add(origin, multiply(t, direction));
        }
    
    }

