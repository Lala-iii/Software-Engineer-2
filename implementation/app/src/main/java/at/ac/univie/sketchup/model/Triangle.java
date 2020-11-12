package at.ac.univie.sketchup.model;

import android.graphics.Point;

public class Triangle extends Shape {
    private Point second;
    private Point third;
    private double width;



    public Triangle(String name, int stroke_width, Color color, Point anchor, Point second, Point third) {
        super(name, stroke_width, color, anchor);
        this.second=second;
        this.third=third;
        this.width=0;
    }
    public Triangle(String name, int stroke_width, Color color, Point anchor,double width) {
        super(name, stroke_width, color, anchor);
        this.second=null;
        this.third=null;
        this.width=width;
    }
    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
