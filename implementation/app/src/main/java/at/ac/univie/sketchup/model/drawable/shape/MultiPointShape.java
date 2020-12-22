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

    public void initializeList() {
        coordinates = new ArrayList<>();
    }

    @Override
    public void onTouchMove(float x, float y) {
        coordinates.add(new Coordinate(x, y));
    }
}