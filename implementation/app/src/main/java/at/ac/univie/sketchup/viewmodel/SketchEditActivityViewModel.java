package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import java.util.ArrayList;

import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.repository.SketchRepository;

public class SketchEditActivityViewModel extends ViewModel {

    private MutableLiveData<Sketch> sketch;

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

    public void addDrawableObject(DrawableObject object, float x, float y) {

        // Create copy(!) of selected object and set coordinate from touch
        DrawableObject objectToSet = null;
        try {
            objectToSet = (DrawableObject) object.clone(); // May be an issue with with cloning Color. Monitor and make deep clone in case
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
//
//    private Sketch createSketch(){
//        Sketch s = new Sketch();
//        s.setId(sketches.getValue().size() + 1);
//        s.setTitle("Sketch " + s.getId());
//
//        return s;
//    }
}
