package at.ac.univie.sketchup.viewmodel;

import android.graphics.Canvas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import at.ac.univie.sketchup.exception.IncorrectAttributesException;
import at.ac.univie.sketchup.model.Layer;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.repository.SketchRepository;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class SketchEditActivityViewModel extends ViewModel {
    public static final Integer CREATE = 1;
    public static final Integer EDIT = 2;
    public static final Integer SELECTION = 3;

    private MutableLiveData<Sketch> sketch;
    private DrawableObject template;
    //private DrawStrategy drawStrategy;
    private MutableLiveData<Integer> mode;
    private DrawService drawService;


    private SketchRepository sketchRepository;
    private ArrayList<DrawStrategy> selectedDrawStrategies;

    public void init(int id) {
        this.sketchRepository = SketchRepository.getInstance();
        this.sketch = new MutableLiveData<>();
        this.sketch.setValue(sketchRepository.findOneById(id));
        //todo add exception in case item do not exist

        this.selectedDrawStrategies = new ArrayList<>();
        this.mode = new MutableLiveData<>();
        this.mode.setValue(0);
        this.drawService = new DrawService();
    }

    public LiveData<Sketch> getSketch() {
        return this.sketch;
    }

    public ArrayList<DrawStrategy> getDrawStrategies() {
        return Objects.requireNonNull(this.sketch.getValue()).getDrawableObjects();
    }

    public void addSelectedToSketch() {
        if (template == null || this.selectedDrawStrategies.size() == 0) return;

        // Add obj to sketch and thought event for observer
        Sketch currentSketch = this.sketch.getValue();
        Objects.requireNonNull(currentSketch).addDrawableObject(this.selectedDrawStrategies.get(0));
        this.sketch.postValue(currentSketch);
        this.selectedDrawStrategies.clear();
    }

    public void setTemplate(DrawableObject t) {
        restoreDrawableObjectCoordinates();
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
            if (this.selectedDrawStrategies.size() > 0)
                this.selectedDrawStrategies.forEach(d -> d.getDrawableObject().setInputSize(s));
        } else {
            // Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which size changes should be applied!");
        }
    }

    public void setColorForSelected(Color c) throws IncorrectAttributesException {
        if (this.template != null) {
            this.template.setColor(c);
            if (this.selectedDrawStrategies.size() > 0)
                this.selectedDrawStrategies.forEach(d -> d.getDrawableObject().setColor(c));
        } else {
            //Custom ExceptionClass Usage
            throw new IncorrectAttributesException("Select the element first to which color changes should be applied!");
        }
    }

    public void cloneFromTemplate() {
        if (this.template == null) return;
        try {
            this.selectedDrawStrategies.clear();
            this.selectedDrawStrategies.add(this.drawService.determineDrawableObject((this.template)));

            // May be an issue with cloning Color. Monitor and make deep clone in case
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void setMode(Integer mode) {
        this.mode.setValue(mode);
    }

    public MutableLiveData<Integer> getMode() {
        return this.mode;
    }

    public void restoreDrawableObjectCoordinates() {

        this.selectedDrawStrategies.forEach(d -> {
            d.restore();
            d.getDrawableObject().setSelected(false);
        });
        this.mode.setValue(SketchEditActivityViewModel.SELECTION);
    }

    public void storeDrawableObjectCoordinates() {
        this.getSelectedDrawStrategies().forEach(d -> {
            d.store();
            d.getDrawableObject().setSelected(false);
        });
        this.mode.setValue(SketchEditActivityViewModel.SELECTION);
    }

    public void removeDrawableObject() {
        if (this.sketch.getValue().getDrawableObjects() == null) return;

        this.mode.setValue(SketchEditActivityViewModel.SELECTION);
        Sketch currentSketch = this.sketch.getValue();
        this.selectedDrawStrategies.forEach(d -> Objects.requireNonNull(currentSketch).removeObject(d));
        this.sketch.postValue(currentSketch);
        this.selectedDrawStrategies.clear();
    }

    public void storeNewCombinedShape(String title) throws IncorrectAttributesException {
        if (this.mode.getValue().equals(SketchEditActivityViewModel.EDIT) && this.selectedDrawStrategies.size() > 0) {
            this.selectedDrawStrategies.forEach(d -> d.getDrawableObject().setSelected(false));
            CombinedShape cs = new CombinedShape(this.selectedDrawStrategies);
            cs.setTitle(title);
            sketch.getValue().addCombinedShape(cs);
            this.selectedDrawStrategies.clear();
            this.mode.setValue(SketchEditActivityViewModel.CREATE);
        } else
            throw new IncorrectAttributesException("Select at least two elements you want to combine as a new shape!");
}

    public CombinedShape getCombinedShapeById(int id) {
        return this.sketch.getValue().getCreatedCombinedShapes().get(id);
    }

    public List<CombinedShape> getCombinedShapeTitles() {
        return this.sketch.getValue().getCreatedCombinedShapes();
    }

    public Layer getByLayerId(int id) {
        return this.sketch.getValue().getLayersList().get(id);
    }

    public void storeSketchChanges() {
        this.sketchRepository.update(this.sketch.getValue());
    }

    public void deleteAllDrawObj() {
        Sketch s = this.sketch.getValue();
        s.clearLayers();
        this.sketch.postValue(s);
    }

    public void deleteSelectedDrawStrategies() {
        this.selectedDrawStrategies.clear();
    }

    public void addSelectedDrawStrategy(DrawStrategy d) {
        d.getDrawableObject().setSelected(true);
        this.selectedDrawStrategies.add(d);
    }

    public ArrayList<DrawStrategy> getSelectedDrawStrategies() {
        return this.selectedDrawStrategies;
    }
}
