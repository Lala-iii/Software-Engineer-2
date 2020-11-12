package at.ac.univie.sketchup.model;

import android.graphics.Point;

public class Circle extends Shape {

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Circle(String name, int stroke_width, Color color, Point anchor) {
        super(name, stroke_width, color, anchor);
    }
}
