package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Circle extends Shape {

    private float radius;

    public Circle(){
        super(Color.BLACK,2);
        radius = 70;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
