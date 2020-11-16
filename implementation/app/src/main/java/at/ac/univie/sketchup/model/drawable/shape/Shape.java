package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Color;

public abstract class Shape extends DrawableObject {

    public Shape(Color c, int size) {
        super(c, size);
    }
}
