package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class CombinedShape extends DrawableObject {

    private ArrayList<DrawStrategy> drawableObjects = new ArrayList<>();
    private String title;

    public CombinedShape(ArrayList<DrawStrategy> shapes) {
        super(Color.BLACK, 70); // todo do we need it???
        shapes.forEach(selected -> drawableObjects.add(cloneSelected(selected)));
    }

    public ArrayList<DrawStrategy> getDrawableObjects() {
        return drawableObjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private DrawStrategy cloneSelected(DrawStrategy selectedShape) {
        try {
            DrawStrategy clonedObj = new DrawService().determineDrawableObject(selectedShape.getDrawableObject());

            if (clonedObj.getDrawableObject() instanceof Polygon) {
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                ((Polygon) clonedObj.getDrawableObject()).getCoordinates().forEach(coordinate -> coordinates.add(new Coordinate(coordinate.getX(), coordinate.getY())));
                ((Polygon) clonedObj.getDrawableObject()).initializeList();
                ((Polygon) clonedObj.getDrawableObject()).setCoordinates(coordinates);
            }

            return clonedObj;
        } catch (Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    @Override
    public Object clone() {
        CombinedShape cloned = new CombinedShape(drawableObjects);
        return cloned;
    }

    @Override
    public String toString() {
        return title;
    }
}
