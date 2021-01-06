package at.ac.univie.sketchup.model;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.DrawableObject;

public class Sketch {

    private int id;
    private String title;
    private ArrayList<Layer> layersList = new ArrayList<>();

    public ArrayList<Layer> getLayersList() {
        return layersList;
    }

    public Sketch() {
        Layer l1 = new Layer(true);
        Layer l2 = new Layer(true);
        Layer l3 = new Layer(true);

        layersList.add(l1);
        layersList.add(l2);
        layersList.add(l3);
    }


    public ArrayList<DrawableObject> getDrawableObjects() {
        ArrayList<DrawableObject> drawableObjects = new ArrayList<>();
        for (Layer l : layersList) {
            if (l.getVisibility()) {
                l.getDrawableObjects().forEach(drawableObject -> drawableObjects.add(drawableObject));
            }
        }
        return drawableObjects;
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
        Layer lastVisible = null;
        for (Layer l : layersList) {
            if (l.getVisibility()) lastVisible = l;
        }
        lastVisible.addDrawableObject(object);
    }
}
