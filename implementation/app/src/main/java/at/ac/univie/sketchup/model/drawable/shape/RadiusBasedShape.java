package at.ac.univie.sketchup.model.drawable.shape;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class RadiusBasedShape extends DoublePointShape {
    public RadiusBasedShape(Color c, int size) {
        super(c, size);
    }

    public float getRadius() {
        Coordinate begin = getAnchorCoordinate();
        Coordinate end = getEndCoordinate();
        return (float) Math.sqrt(Math.pow(Math.abs(end.getX() - begin.getX()), 2) + Math.pow(Math.abs(end.getY() - begin.getY()), 2));
    }
}
