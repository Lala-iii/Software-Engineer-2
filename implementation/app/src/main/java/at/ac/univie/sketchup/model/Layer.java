package at.ac.univie.sketchup.model;

import java.io.Serializable;
import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class Layer implements Serializable {
    private boolean visibility;
    private ArrayList<DrawStrategy> drawableObjects = new ArrayList<>();


    public Layer(boolean visibility) {
        this.visibility = visibility;
    }

    public void addDrawableObject (DrawStrategy o) {
        drawableObjects.add(o);
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public ArrayList<DrawStrategy> getDrawableObjects() {
        return drawableObjects;
    }

    public void setDrawableObjects(ArrayList<DrawStrategy> drawableObjects) {
        this.drawableObjects = drawableObjects;
    }
}

