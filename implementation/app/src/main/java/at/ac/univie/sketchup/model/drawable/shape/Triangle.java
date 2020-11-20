package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Triangle extends Shape {
    private float width;

    public Triangle(){
        super(Color.BLACK,5);
        width = 70;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

}
