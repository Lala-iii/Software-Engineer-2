package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Polygon extends MultiPointShape implements Serializable {
    public Polygon() {
        super(Color.BLACK,5);
    }
}
