package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Circle extends RadiusBasedShape implements Serializable {
    public Circle(Color c, int size) {
        super(c, size);
    }

    public Circle() {
        this(Color.BLACK, 5);
    }
}
