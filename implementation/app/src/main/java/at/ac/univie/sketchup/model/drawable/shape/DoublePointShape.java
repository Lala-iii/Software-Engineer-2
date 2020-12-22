package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class DoublePointShape extends Shape {
    private Coordinate endCoordinate;

    public DoublePointShape(Color c, int size) {
        super(c, size);
    }

    public Coordinate getEndCoordinate() { return this.endCoordinate == null ? this.getAnchorCoordinate(): this.endCoordinate; }

    public void setEndCoordinate(Coordinate endCoordinate) {
        this.endCoordinate = endCoordinate;
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.endCoordinate = new Coordinate(x, y);
    }
}
