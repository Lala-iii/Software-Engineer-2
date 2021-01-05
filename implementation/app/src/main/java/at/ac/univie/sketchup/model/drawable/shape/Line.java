package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Line extends DoublePointShape {
    public Line(Color c, int size) {
        super(c, size);
    }

    public Line() {
        this(Color.BLACK,6);
    }
}
