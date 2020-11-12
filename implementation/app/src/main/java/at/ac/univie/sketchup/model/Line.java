package at.ac.univie.sketchup.model;

import android.graphics.Point;

public class Line extends Shape {
   private Point end;

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public Line(String name, int stroke_width, Color color, Point anchor) {
        super(name, stroke_width, color, anchor);
        this.end=end;
    }
}
