package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;
import java.util.Objects;

import at.ac.univie.sketchup.exception.IncorrectAttributesException;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.repository.SketchRepository;

public class SketchEditActivityViewModel extends ViewModel {

    private MutableLiveData<Sketch> sketch;
    private DrawableObject template;
    private DrawableObject drawableObject;
    private Mode mode;

    public void init(int id) {

        SketchRepository sketchRepository = SketchRepository.getInstance();
        sketch = sketchRepository.findOneById(id);

        //todo add exception in case item do not exist
    }

    public LiveData<Sketch> getSketch() {
        return sketch;
    }

    public ArrayList<DrawableObject> getObjectsToDraw() {
        return Objects.requireNonNull(sketch.getValue()).getDrawableObjects();
    }

    public void addSelectedToSketch() {
        if (template == null || this.drawableObject == null) return;

        // Add obj to sketch and thought event for observer
        Sketch currentSketch = sketch.getValue();
        Objects.requireNonNull(currentSketch).addDrawableObject(this.drawableObject);
        sketch.postValue(currentSketch);
        this.drawableObject = null;

        // todo write in storage(?)
    }

    public void setTemplate(DrawableObject t) {
        this.template = t;
        this.mode = Mode.CREATE;
    }

    public void setTextForSelected(String text) {
        if (this.template instanceof TextBox) {
            ((TextBox) this.template).setText(text);
        }
    }

    public void setSizeForSelected(int s) throws IncorrectAttributesException {
        if (this.template != null) {
            this.template.setInputSize(s);
        } else {
            // Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which size changes should be applied!");
        }
    }

    public void setColorForSelected(Color c) throws IncorrectAttributesException {
        if (this.template != null) {
            this.template.setColor(c);
        } else {
            //Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which color changes should be applied!");
        }
    }

    public void onTouchDown(float x, float y) {
        // TODO maybe it is better to move the ontouch events to drawstrategy; check it
        if (this.template == null) return;
        if (this.mode == Mode.CREATE) {
            cloneToNew();
            this.drawableObject.onTouchDown(x, y);
        } else if (this.mode == Mode.EDIT) {
            Coordinate origin = this.drawableObject.getAnchorCoordinate();
            this.drawableObject.onTouchDown(x + origin.getX(), y + origin.getY());
        }
    }

    public void onTouchMove(float x, float y) {
        if (this.template == null) return;
        //if (this.mode == Mode.CREATE) {
            this.drawableObject.onTouchMove(x, y);
        //} else if (this.mode == Mode.EDIT) {
        //    Coordinate origin = this.drawableObject.getAnchorCoordinate();
        //    this.drawableObject.onTouchMove(x + origin.getX(), y + origin.getY());
        //}
    }

    private void cloneToNew() {
        // Create copy(!) of selected object and set coordinate from touch
        try {
            this.drawableObject = (DrawableObject) this.template.clone();

            if (this.drawableObject instanceof Polygon) {
                this.drawableObject = (Polygon) this.template.clone();
                ((Polygon) this.drawableObject).initializeList();
            }
            // May be an issue with cloning Color. Monitor and make deep clone in case
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public DrawableObject getDrawableObject() {
        return this.drawableObject;
    }

    public void setDrawableObject(DrawableObject drawableObject) {
        this.drawableObject = drawableObject;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return this.mode;
    }
}
