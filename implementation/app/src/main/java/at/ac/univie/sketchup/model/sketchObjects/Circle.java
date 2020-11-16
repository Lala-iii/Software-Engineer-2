package at.ac.univie.sketchup.model.sketchObjects;

import android.graphics.Point;

import at.ac.univie.sketchup.model.Color;
import at.ac.univie.sketchup.model.Shape;

public class Circle extends Shape {
    private float radius;

//    public Circle(ElementType type, String name, int stroke_width, Color color, Point anchor, double radius) {
//        super(type, name, stroke_width, color, anchor);
//        this.radius = radius;
//    }

    public Circle(){
        super(Color.BLACK,2);
        radius = 70;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
