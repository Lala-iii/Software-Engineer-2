package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Quadrangle extends DoublePointShape {
    private boolean selector;

    public Quadrangle(Color c, int size, boolean selector) {
        super(c, size);
        this.selector = selector;
    }

    public Quadrangle() {
        this(Color.BLACK, 5, false);
    }

    public void setSelector() { this.selector = true;}

    public boolean isSelector() { return this.selector; }

}
