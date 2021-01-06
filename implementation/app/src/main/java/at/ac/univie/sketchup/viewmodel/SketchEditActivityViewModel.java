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
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.repository.SketchRepository;
import at.ac.univie.sketchup.view.SketchEditActivity;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class SketchEditActivityViewModel extends ViewModel {
    public static final Integer CREATE = 1;
    public static final Integer EDIT = 2;
    public static final Integer SELECTION = 3;

    private MutableLiveData<Sketch> sketch;
    private DrawableObject template;
    private DrawStrategy drawStrategy;
    private MutableLiveData<Integer> mode;

    public void init(int id) {

        SketchRepository sketchRepository = SketchRepository.getInstance();
        sketch = sketchRepository.findOneById(id);
        mode = new MutableLiveData<>();

        //todo add exception in case item do not exist
    }

    public LiveData<Sketch> getSketch() {
        return sketch;
    }

    public ArrayList<DrawStrategy> getObjectsToDraw() {
        return Objects.requireNonNull(sketch.getValue()).getDrawableObjects();
    }

    public void addSelectedToSketch() {
        if (template == null || this.drawStrategy == null) return;

        // Add obj to sketch and thought event for observer
        Sketch currentSketch = sketch.getValue();
        Objects.requireNonNull(currentSketch).addDrawableObject(this.drawStrategy);
        sketch.postValue(currentSketch);
        this.drawStrategy = null;

        // todo write in storage(?)
    }

    public void setTemplate(DrawableObject t) {
        this.template = t;
        this.mode.setValue(SketchEditActivityViewModel.CREATE);
    }

    public void setTextForSelected(String text) {
        if (this.template instanceof TextBox) {
            ((TextBox) this.template).setText(text);
        }
    }

    public void setSizeForSelected(int s) throws IncorrectAttributesException {
        if (this.template != null) {
            this.template.setInputSize(s);
            if (this.drawStrategy != null)
                this.drawStrategy.getDrawableObject().setInputSize(s);
        } else {
            // Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which size changes should be applied!");
        }
    }

    public void setColorForSelected(Color c) throws IncorrectAttributesException {
        if (this.template != null) {
            this.template.setColor(c);
            if (this.drawStrategy != null)
                this.drawStrategy.getDrawableObject().setColor(c);
        } else {
            //Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which color changes should be applied!");
        }
    }

    public void cloneToNew() {
        if (this.template == null) return;

        // Create copy(!) of selected object and set coordinate from touch
        try {
            this.drawStrategy = new DrawService().determineDrawableObject(((DrawableObject)this.template.clone()));
            /*if (this.drawableObject instanceof Polygon) {
                this.drawableObject = (Polygon) this.template.clone();
                ((Polygon) this.drawableObject).initializeList();
            }*/
            // May be an issue with cloning Color. Monitor and make deep clone in case
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public DrawStrategy getDrawStrategy() {
        return this.drawStrategy;
    }

    public void setDrawStrategy(DrawStrategy drawStrategy) {
        this.drawStrategy = drawStrategy;
    }

    public void setMode(Integer mode) {
        this.mode.setValue(mode);
    }

    public MutableLiveData<Integer> getMode() {
        return this.mode;
    }

    public void restoreDrawableObjectCoordinates() {
        this.drawStrategy.restore();
        this.drawStrategy.getDrawableObject().setSelected(false);
        this.mode.setValue(SketchEditActivityViewModel.CREATE);
    }

    public void storeDrawableObjectCoordinates() {
        this.drawStrategy.store();
        this.drawStrategy.getDrawableObject().setSelected(false);
        this.mode.setValue(SketchEditActivityViewModel.CREATE);
    }


}
