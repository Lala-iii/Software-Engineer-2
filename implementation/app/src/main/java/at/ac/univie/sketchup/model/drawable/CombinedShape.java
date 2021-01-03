package at.ac.univie.sketchup.model.drawable;

import android.util.Log;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class CombinedShape extends DrawableObject{

    private ArrayList<DrawableObject> drawableObjects = new ArrayList<>();

    public CombinedShape(ArrayList<DrawableObject> shapes) {
        super(Color.BLACK,70); // todo do we need it???
        shapes.forEach(selected -> drawableObjects.add(cloneSelected(selected)));
    }
    @Override
    public void onTouchMove(float x, float y) {
        this.setAnchorCoordinate(new Coordinate(x, y));
    }// todo do we need it???

    public ArrayList<DrawableObject> getDrawableObjects() {
        return drawableObjects;
    }

    private DrawableObject cloneSelected(DrawableObject selectedShape) {
        try {
            DrawableObject clonedObj = (DrawableObject) selectedShape.clone();
            return clonedObj;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
