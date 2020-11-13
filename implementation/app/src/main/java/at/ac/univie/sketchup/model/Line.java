package at.ac.univie.sketchup.model;

import android.graphics.Color;
import android.graphics.Point;

public class Line extends Shape {
    private Point end;

    public Line(ShapeType type, String name, int stroke_width, Color color, Point anchor, Point end) {
        super(type, name, stroke_width, color, anchor);
        this.end = end;
    }

    public Point getEnd() {
        return this.end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
