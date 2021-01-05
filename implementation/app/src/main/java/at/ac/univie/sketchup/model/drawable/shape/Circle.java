package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Circle extends RadiusBasedShape {
    public Circle(Color c, int size) {
        super(c, size);
    }

    public Circle() {
        this(Color.BLACK, 5);
    }
}
