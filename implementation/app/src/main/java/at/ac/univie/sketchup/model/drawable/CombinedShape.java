package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.DoublePointShape;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawTextBox;

public class CombinedShape extends DrawableObject {

    private ArrayList<DrawStrategy> drawableObjects = new ArrayList<>();
    private String title;

    public CombinedShape(ArrayList<DrawStrategy> shapes) {
        super(Color.BLACK, 70); // todo do we need it???
        shapes.forEach(selected -> {
            try {
                drawableObjects.add(cloneSelected(selected));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
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

    private DrawStrategy cloneSelected(DrawStrategy selectedShape) throws CloneNotSupportedException {
        if (selectedShape.getDrawableObject() instanceof CombinedShape) {
            DrawStrategy emptyObj = new DrawTextBox(new TextBox());
            emptyObj.getDrawableObject().setAnchorCoordinate(new Coordinate(0, 0));
            return emptyObj; // very bad dirty cheat: replace CombinedShape with empty TextBox to avoid recursion
        }

        try {
            DrawStrategy clonedObj = new DrawService().determineDrawableObject((DrawableObject) selectedShape.getDrawableObject().clone());

            if (clonedObj.getDrawableObject() instanceof Polygon) {
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                ((Polygon) clonedObj.getDrawableObject()).getCoordinates().forEach(coordinate -> coordinates.add(new Coordinate(coordinate.getX(), coordinate.getY())));
                ((Polygon) clonedObj.getDrawableObject()).initializeList();
                ((Polygon) clonedObj.getDrawableObject()).setCoordinates(coordinates);
            }

            return clonedObj;
        } catch (Exception e ) { //CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        CombinedShape cloned = new CombinedShape(drawableObjects);
        return cloned;
    }

    @Override
    public String toString() {
        return title;
    }
}
