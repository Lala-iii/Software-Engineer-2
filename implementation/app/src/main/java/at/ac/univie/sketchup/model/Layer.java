package at.ac.univie.sketchup.model;

import java.io.Serializable;
import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.DrawableObject;

public class Layer implements Serializable {
    private boolean visibility;
    private ArrayList<DrawableObject> drawableObjects = new ArrayList<>();

    public Layer(boolean visibility) {
        this.visibility = visibility;
    }

    public void addDrawableObject (DrawableObject o) {
        drawableObjects.add(o);
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public ArrayList<DrawableObject> getDrawableObjects() {
        return drawableObjects;
    }

    public void setDrawableObjects(ArrayList<DrawableObject> drawableObjects) {
        this.drawableObjects = drawableObjects;
    }
}

