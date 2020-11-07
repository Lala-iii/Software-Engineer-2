package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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

    public void updateSketch(String newText) {
        Sketch currentSketch = sketch.getValue();
        currentSketch.setText(newText);
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
