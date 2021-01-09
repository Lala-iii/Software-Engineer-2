package at.ac.univie.sketchup.model;


import java.io.Serializable;

import java.util.ArrayList;
import at.ac.univie.sketchup.model.Iterator;
import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class Sketch implements Serializable,Container {

    private int id;
    private String title;
    private int index;
    private ArrayList<Layer> layersList = new ArrayList<>();
    private ArrayList<CombinedShape> createdCombinedShapes = new ArrayList<>();

    public ArrayList<Layer> getLayersList() {
        return layersList;
    }

    public Sketch() {

        create3Layers();
    }


    //public void setDrawableObjects(ArrayList<DrawStrategy> drawableObjects) {
    //    this.drawableObjects = drawableObjects;
    //}

    public ArrayList<DrawStrategy> getDrawableObjects() {
        ArrayList<DrawStrategy> drawableObjects = new ArrayList<>();
        for (Layer l : layersList) {
            if (l.getVisibility()) {
                l.getDrawableObjects().forEach(drawableObject -> drawableObjects.add(drawableObject));
            }
        }
        return drawableObjects;
    }

    public void removeObject(DrawStrategy drawStrategy) {
        for (Layer l : layersList)
            l.getDrawableObjects().removeIf(ds -> ds == drawStrategy);
    }

    public void clearLayers() {
        layersList = new ArrayList<>();
        create3Layers();
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


    public ArrayList<CombinedShape> getCreatedCombinedShapes() {
        return createdCombinedShapes;
    }

    public void setCreatedCombinedShapes(ArrayList<CombinedShape> createdCombinedShapes) {
        this.createdCombinedShapes = createdCombinedShapes;
    }


    @Override
    public String toString() {
        return this.title;
    }

    public void addDrawableObject(DrawStrategy object) {
        Layer lastVisible = null;
        Layer layerZero = new Layer(true);
         LayerIterator layerIterator = (LayerIterator) getIterator();
        while(layerIterator.hasNext()){
            Layer l= (Layer) layerIterator.next();
            if( l.getVisibility()) {

                lastVisible=l;
            }
        }
       /* for (Layer l : layersList) {
            if (l.getVisibility()) lastVisible = l;
        }
*/
        if(lastVisible != null)
            lastVisible.addDrawableObject(object);
        else
            layerZero.addDrawableObject(object);

    }

    public void addCombinedShape(CombinedShape combinedShapes) {
        createdCombinedShapes.add(combinedShapes);
    }

    private void create3Layers() {
        Layer l1 = new Layer(true);
        Layer l2 = new Layer(true);
        Layer l3 = new Layer(true);

        layersList.add(l1);
        layersList.add(l2);
        layersList.add(l3);
    }





    @Override
    public Iterator getIterator() {
        return new LayerIterator(layersList);
    }
}
