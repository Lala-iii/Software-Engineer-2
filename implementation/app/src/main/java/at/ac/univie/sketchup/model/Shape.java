package at.ac.univie.sketchup.model;

import android.graphics.Point;

import java.util.List;

public abstract class Shape {
    private String name;
    private int stroke_width;
    private Color color;
    private Point anchor;
    //private List<Coordinate> path;

    public Shape(String name, int stroke_width, Color color, Point anchor) {
        this.name = name;
        this.stroke_width = stroke_width;
        this.color = color;
        this.anchor = anchor;
    }
  public Point getAnchor() { return anchor; }
}
