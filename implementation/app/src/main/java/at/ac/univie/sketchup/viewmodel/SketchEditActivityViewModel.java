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
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class SketchEditActivityViewModel extends ViewModel {
    public static final int CREATE = 1;
    public static final int EDIT = 2;
    public static final int SELECTION = 3;

    private MutableLiveData<Sketch> sketch;
    private DrawableObject template;
    private DrawStrategy drawStrategy;
    private LiveData<Mode> mode;

    public void init(int id) {

        SketchRepository sketchRepository = SketchRepository.getInstance();
        sketch = sketchRepository.findOneById(id);

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
        setMode(Mode.CREATE);
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

    /*public void onTouchDown(float x, float y) {
        // TODO maybe it is better to move the ontouch events to drawstrategy; check it
        if (this.template == null) return;
        if (this.mode == Mode.CREATE) {
            cloneToNew();
            this.drawStrategy.onTouchDown(x, y);
        } else if (this.mode == Mode.EDIT) {
            Coordinate origin = this.drawStrategy.getAnchorCoordinate();
            this.drawStrategy.onTouchDown(x + origin.getX(), y + origin.getY());
        }
    }

    public void onTouchMove(float x, float y) {
        if (this.template == null) return;
        //if (this.mode == Mode.CREATE) {
            this.drawStrategy.onTouchMove(x, y);
        //} else if (this.mode == Mode.EDIT) {
        //    Coordinate origin = this.drawableObject.getAnchorCoordinate();
        //    this.drawableObject.onTouchMove(x + origin.getX(), y + origin.getY());
        //}
    }*/

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

    public void setMode(LiveData<Mode> mode) {
        this.mode = mode;
    }

    public LiveData<Mode> getMode() {
        return this.mode;
    }
}
