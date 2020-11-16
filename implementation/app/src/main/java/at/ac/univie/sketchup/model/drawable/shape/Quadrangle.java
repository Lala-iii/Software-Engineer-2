package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Quadrangle extends Shape {
    private Coordinate endPoint;

    public Quadrangle(){
        super(Color.BLACK,5);
    }

    public Coordinate getEndPoint() {
        endPoint = new Coordinate(
                super.getPosition().getX() + 300,
                super.getPosition().getY() + 300
        );
        return endPoint;
    }

    public void setEndPoint(Coordinate endPoint) {
        this.endPoint = endPoint;
    }
}
