package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.Objects;

import at.ac.univie.sketchup.exception.IncorrectAttributesException;
import at.ac.univie.sketchup.model.Layer;
import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.repository.SketchRepository;

public class SketchEditActivityViewModel extends ViewModel {

    private MutableLiveData<Sketch> sketch;
    private DrawableObject selected;
    private DrawableObject drawableObject;

    private SketchRepository sketchRepository;

    public void init(int id){
        sketchRepository = SketchRepository.getInstance();
        sketch = new MutableLiveData<>();
        sketch.setValue(sketchRepository.findOneById(id));

        //todo add exception in case item do not exist
    }

    public LiveData<Sketch> getSketch(){
        return sketch;
    }

    public ArrayList<DrawableObject> getObjectsToDraw() {
        return Objects.requireNonNull(sketch.getValue()).getDrawableObjects();
    }

    public void addSelectedToSketch() {
        if (selected == null || this.drawableObject == null) return;

        // Add obj to sketch and thought event for observer
        Sketch currentSketch = sketch.getValue();
        Objects.requireNonNull(currentSketch).addDrawableObject(this.drawableObject);
        sketch.postValue(currentSketch);
        this.drawableObject = null;

        // todo write in storage(?)
    }

    public void setSelected(DrawableObject s) {
        selected = s;
    }

    public void setTextForSelected(String text) {
        if (selected instanceof TextBox) {
            ((TextBox) selected).setText(text);
        }
    }

    public void setSizeForSelected(int s) throws IncorrectAttributesException {
        if (null != selected) {
            selected.setInputSize(s);
        } else {
            // Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which size changes should be applied!");
        }
    }

    public void setColorForSelected(Color c) throws IncorrectAttributesException {
        if (null != selected) {
            selected.setColor(c);
        } else {
            //Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which color changes should be applied!");
        }
    }

    public void onTouchDown(float x, float y) {
        if (selected == null) return;
        cloneToNew();
        this.drawableObject.onTouchDown(x, y);
    }

    public void onTouchMove(float x, float y) {
        if (selected == null) return;
        this.drawableObject.onTouchMove(x, y);
    }

    private void cloneToNew() {
        // Create copy(!) of selected object and set coordinate from touch
        try {
            this.drawableObject = (DrawableObject) selected.clone();

            if (this.drawableObject instanceof Polygon) {
                this.drawableObject = (Polygon) selected.clone();
                ((Polygon)this.drawableObject).initializeList();
            }

            // May be an issue with cloning Color. Monitor and make deep clone in case
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }
    }

    public DrawableObject getDrawableObject() {
        return this.drawableObject;
    }

    public void storeNewCombinedShape(String title) {
        CombinedShape cs = new CombinedShape(sketch.getValue().getDrawableObjects());
        cs.setTitle(title);
        sketch.getValue().addCombinedShape(cs);
    }

    public CombinedShape getCombinedShapeById(int id) {
        return sketch.getValue().getCreatedCombinedShapes().get(id);
    }

    public ArrayList getCombinedShapeTitles() {
        return sketch.getValue().getCreatedCombinedShapes();
    }

    public Layer getByLayerId(int id) {
        return sketch.getValue().getLayersList().get(id);
    }

    public void storeSketchChanges() {
        sketchRepository.update(sketch.getValue());
    }

    public void deleteAllDrawObj() {
        Sketch s = sketch.getValue();
        s.clearLayers();
        sketch.postValue(s);
    }
}
