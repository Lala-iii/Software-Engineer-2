package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Line extends DoublePointShape implements Serializable {
    public Line(Color c, int size) {
        super(c, size);
    }

    public Line() {
        this(Color.BLACK,6);
    }
}
