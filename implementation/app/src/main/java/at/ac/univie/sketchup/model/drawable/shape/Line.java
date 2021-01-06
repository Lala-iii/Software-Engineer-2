package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Line extends DoublePointShape implements Serializable {
    public Line() {
        super(Color.BLACK,6);
    }
}
