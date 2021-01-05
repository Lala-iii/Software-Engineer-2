package at.ac.univie.sketchup.model;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class Sketch {

    private int id;
    private String title;
    private ArrayList<DrawStrategy> drawableObjects = new ArrayList<>();

    // todo constructor

    public ArrayList<DrawStrategy> getDrawableObjects() {
        return drawableObjects;
    }

    public void setDrawableObjects(ArrayList<DrawStrategy> drawableObjects) {
        this.drawableObjects = drawableObjects;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public void addDrawableObject(DrawStrategy object) {
        this.drawableObjects.add(object);
    }
}
