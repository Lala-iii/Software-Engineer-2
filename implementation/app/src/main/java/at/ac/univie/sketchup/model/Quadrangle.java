package at.ac.univie.sketchup.model;

import android.graphics.Point;

public class Quadrangle extends Shape {
    private Point end;

    public Quadrangle(String name, int stroke_width, Color color, Point anchor, Point end) {
        super(name, stroke_width, color, anchor);
        this.end=end;
    }
    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }
}
