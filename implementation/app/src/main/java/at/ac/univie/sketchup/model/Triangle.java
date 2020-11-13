package at.ac.univie.sketchup.model;

import android.graphics.Color;
import android.graphics.Point;

public class Triangle extends Shape {
    private double width;

    public Triangle(ShapeType type, String name, int stroke_width, Color color, Point anchor, double width) {
        super(type, name, stroke_width, color, anchor);
        this.width=width;
    }
    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
