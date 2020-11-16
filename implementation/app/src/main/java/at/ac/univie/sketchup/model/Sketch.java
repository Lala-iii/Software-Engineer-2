package at.ac.univie.sketchup.model;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.DrawableObject;

public class Sketch {

    private int id;
    private String title;
    private ArrayList<DrawableObject> drawableObjects = new ArrayList<>();

    // todo constructor

    public ArrayList<DrawableObject> getDrawableObjects() {
        return drawableObjects;
    }

    public void setDrawableObjects(ArrayList<DrawableObject> drawableObjects) {
        this.drawableObjects = drawableObjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public void addDrawableObject(DrawableObject object) {
        drawableObjects.add(object);
    }
}
