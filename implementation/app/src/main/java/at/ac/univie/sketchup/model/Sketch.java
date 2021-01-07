package at.ac.univie.sketchup.model;


import java.io.Serializable;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;


import java.util.ArrayList;

import at.ac.univie.sketchup.R;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;

public class Sketch implements Serializable {

    private int id;
    private String title;

    private ArrayList<Layer> layersList = new ArrayList<>();
    private ArrayList<CombinedShape> createdCombinedShapes = new ArrayList<>();

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
    public void clear() {
        this.getDrawableObjects().clear();
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


    public ArrayList<CombinedShape> getCreatedCombinedShapes() {
        return createdCombinedShapes;
    }

    public void setCreatedCombinedShapes(ArrayList<CombinedShape> createdCombinedShapes) {
        this.createdCombinedShapes = createdCombinedShapes;
    }


    @Override
    public String toString() {
        return title;
    }

    public void addDrawableObject(DrawableObject object) {
        Layer lastVisible = null;
        Layer layerZero = new Layer(true);

        for (Layer l : layersList) {
            if (l.getVisibility()) lastVisible = l;
        }

        if(lastVisible != null)
            lastVisible.addDrawableObject(object);
        else
            layerZero.addDrawableObject(object);

    }

    public void addCombinedShape(CombinedShape combinedShapes) {
        createdCombinedShapes.add(combinedShapes);
    }
}
