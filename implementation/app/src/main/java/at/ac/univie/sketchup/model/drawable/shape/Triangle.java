package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Triangle extends RadiusBasedShape implements Serializable{
    public Triangle(Color c, int size) {
        super(c, size);
    }

    public Triangle(){
        this(Color.BLACK,5);
    }
}
