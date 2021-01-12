package at.ac.univie.sketchup.model.drawable.shape;

import java.util.ArrayList;
import java.util.List;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class MultiPointShape extends Shape {
    private List<Coordinate> coordinates;
    public MultiPointShape(Color c, int size) {
        super(c, size);
        coordinates = new ArrayList<>();
    }

    public List<Coordinate> getCoordinates() { return this.coordinates; }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = new ArrayList<>();
        coordinates.forEach(c -> this.coordinates.add(new Coordinate(c.getX(), c.getY())));
    }

    public void initializeList() {
        coordinates = new ArrayList<>();
    }

    public void addCoordinate(float x, float y) {
        this.coordinates.add(new Coordinate(x, y));
    }
}
