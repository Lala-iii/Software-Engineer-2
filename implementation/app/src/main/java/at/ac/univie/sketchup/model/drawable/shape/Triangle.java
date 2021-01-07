package at.ac.univie.sketchup.model.drawable.shape;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Triangle extends RadiusBasedShape implements Serializable {
    public Triangle(){
        super(Color.BLACK,5);
    }
}
