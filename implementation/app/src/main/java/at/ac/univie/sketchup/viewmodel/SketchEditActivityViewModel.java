package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;

import at.ac.univie.sketchup.IncorrectAttributesException;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.repository.SketchRepository;

public class SketchEditActivityViewModel extends ViewModel {

    private MutableLiveData<Sketch> sketch;
    private DrawableObject selected;
    private SketchRepository sketchRepository;

    public void init(int id){

        sketchRepository = SketchRepository.getInstance();
        sketch = sketchRepository.findOneById(id);

        //todo add exception in case item do not exist
    }

    public LiveData<Sketch> getSketch(){
        return sketch;
    }

    public ArrayList<DrawableObject> getObjectsToDraw() {
        return sketch.getValue().getDrawableObjects();
    }

    public void addSelectedToSketch(float x, float y) {
        if (selected == null) return;

        // Create copy(!) of selected object and set coordinate from touch
        DrawableObject objectToSet = null;
        try {
            objectToSet = (DrawableObject) selected.clone(); // May be an issue with with cloning Color. Monitor and make deep clone in case
            objectToSet.setPosition(new Coordinate(x, y));
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return;
        }

        // Add obj to sketch and thought event for observer
        Sketch currentSketch = sketch.getValue();
        currentSketch.addDrawableObject(objectToSet);
        sketch.postValue(currentSketch);

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
}
