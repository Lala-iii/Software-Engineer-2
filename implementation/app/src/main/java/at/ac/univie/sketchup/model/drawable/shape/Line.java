package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Line extends Shape{

    private Coordinate endPoint;

    public Line(){
        super(Color.BLACK,2);
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
