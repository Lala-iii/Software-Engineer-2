package at.ac.univie.sketchup.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.repository.SketchRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Sketch>> sketches;
    private SketchRepository sketchRepository;

    public void init(){
        if (sketches != null) {
            return;
        }
        sketchRepository = SketchRepository.getInstance();
        sketches = sketchRepository.findAll();
    }

    public LiveData<List<Sketch>> getSketches(){
        // todo create custom error if repo is not init
        return sketches;
    }

    public int createNewSketch() {
         Sketch newSketch = createSketch();
         List<Sketch> currentSketches = sketches.getValue();
         Objects.requireNonNull(currentSketches).add(newSketch);
         sketches.postValue(currentSketches);

         // todo write in storage(?)
         return newSketch.getId();
    }

    public void deleteSketchById(int id) {
        sketchRepository.deleteById(id);
    }

    private Sketch createSketch(){
        Sketch s = new Sketch();
        s.setId(Objects.requireNonNull(sketches.getValue()).size() + 1);
        s.setTitle("Sketch " + s.getId());

        return s;
    }
}
