package at.ac.univie.sketchup.view.service;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawableObjectFactory extends DrawableObjectAbstractFactory{
    public DrawableObject getDrawableObject(Class c) {
        if (c == Line.class) return new Line();
        else if (c == Circle.class) return new Circle();
        else if (c == Quadrangle.class) return new Quadrangle();
        else if (c == Triangle.class) return new Triangle();
        else if (c == Polygon.class) return new Polygon();
        else if (c == TextBox.class) return new TextBox();
        return null;
    }
}
