package at.ac.univie.sketchup.model;

import android.graphics.Color;
import android.graphics.Point;

public class Circle extends Shape {
    private double radius;

    public Circle(ElementType type, String name, int stroke_width, Color color, Point anchor, double radius) {
        super(type, name, stroke_width, color, anchor);
        this.radius = radius;
    }

    public double getRadius() {
        return this.radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
