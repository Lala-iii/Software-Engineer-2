package at.ac.univie.sketchup.model;

import android.graphics.Color;
import android.graphics.Point;


public abstract class Shape {
    private String name;
    private int stroke_width;
    private Color color;
    private Point anchor;
    private ShapeType type;

    public Shape(ShapeType type, String name, int stroke_width, Color color, Point anchor) {
        this.type = type;
        this.name = name;
        this.stroke_width = stroke_width;
        this.color = color;
        this.anchor = anchor;
    }

    public Point getAnchor() {
        return this.anchor;
    }

    public ShapeType getType() {
        return this.type;
    }
}
