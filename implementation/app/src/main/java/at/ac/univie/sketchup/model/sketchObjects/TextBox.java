package at.ac.univie.sketchup.model.sketchObjects;

import android.graphics.Point;

public class TextBox extends Text {

    private int color; //todo to enum
    private double x;
    private double y;
    private Point anchor;

    public TextBox(Point anchor) {
        this.anchor = anchor;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public Point getAnchor() {
        return anchor;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
