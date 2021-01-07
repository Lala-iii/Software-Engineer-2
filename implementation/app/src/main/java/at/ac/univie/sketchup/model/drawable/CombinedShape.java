package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.DoublePointShape;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class CombinedShape extends DrawableObject{

    private ArrayList<DrawableObject> drawableObjects = new ArrayList<>();
    private String title;

    public CombinedShape(ArrayList<DrawableObject> shapes) {
        super(Color.BLACK,70); // todo do we need it???
        shapes.forEach(selected -> drawableObjects.add(cloneSelected(selected)));
    }

    @Override
    public void onTouchMove(float x, float y) {
        Coordinate diff = getDiffForNewCoordinate(x, y);

        drawableObjects.forEach(obj -> setNewCoordinate(obj, diff));
    }

    public ArrayList<DrawableObject> getDrawableObjects() {
        return drawableObjects;
    }

    public void setDrawableObjects(ArrayList<DrawableObject> drawableObjects) {
        this.drawableObjects = drawableObjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private DrawableObject cloneSelected(DrawableObject selectedShape) {
        if (selectedShape instanceof CombinedShape) {
            DrawableObject emptyObj = new TextBox();
            emptyObj.setAnchorCoordinate(new Coordinate(0,0));
            return emptyObj; // very bad dirty cheat: replace CombinedShape with empty TextBox to avoid recursion
        }

        try {
            DrawableObject clonedObj = (DrawableObject) selectedShape.clone();

            if (clonedObj instanceof Polygon) {
                ArrayList<Coordinate> coordinates = new ArrayList<>();
                ((Polygon) clonedObj).getCoordinates().forEach( coordinate -> coordinates.add(new Coordinate(coordinate.getX(), coordinate.getY())));
                ((Polygon) clonedObj).initializeList();
                ((Polygon) clonedObj).setCoordinates(coordinates);
            }

            return clonedObj;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Coordinate getDiffForNewCoordinate(float x, float y) {
        if (drawableObjects == null || drawableObjects.size() == 0) return null;
        Coordinate c = new Coordinate(
                    x - drawableObjects.get(0).getAnchorCoordinate().getX(),
                    y - drawableObjects.get(0).getAnchorCoordinate().getY()
        );

        return c;
    }

    private void setNewCoordinate(DrawableObject obj, Coordinate diff) {
        float newX;
        float newY;

        if (obj instanceof DoublePointShape) {
            newX = ((DoublePointShape) obj).getEndCoordinate().getX() + diff.getX();
            newY = ((DoublePointShape) obj).getEndCoordinate().getY() + diff.getY();
            ((DoublePointShape) obj).setEndCoordinate(new Coordinate(newX, newY));
        }

        if (obj instanceof Polygon) {
            for(Coordinate c : ((Polygon) obj).getCoordinates()) {
                c.setX(c.getX() + diff.getX());
                c.setY(c.getY() + diff.getY());
            }
        }

        newX = obj.getAnchorCoordinate().getX() + diff.getX();
        newY = obj.getAnchorCoordinate().getY() + diff.getY();
        obj.setAnchorCoordinate(new Coordinate(newX, newY));
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
