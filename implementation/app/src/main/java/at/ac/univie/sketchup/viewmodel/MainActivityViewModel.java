package at.ac.univie.sketchup.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.repository.SketchRepository;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Sketch>> sketches;
    private SketchRepository sketchRepository;

    public void init(Context context ) {
        if (sketches != null) {
            return;
        }
        sketchRepository = SketchRepository.getInstance();
        sketchRepository.setContext(context);

        sketches = new MutableLiveData<>();
        sketches.setValue(sketchRepository.findAll());
    }

    public LiveData<List<Sketch>> getSketches() {
        // todo create custom error if repo is not init
        return sketches;
    }

    public int createNewSketch() {
        Sketch newSketch = createSketch();
        sketchRepository.add(newSketch);
        sketches.postValue(sketchRepository.findAll());

        return newSketch.getId();
    }

    public void deleteSketchById(int id) {
        sketchRepository.deleteById(id);
        sketches.postValue(sketchRepository.findAll());
    }

    private Sketch createSketch() {
        Sketch s = new Sketch();
        if ( sketches.getValue().size() == 0) {
            s.setId(1);
        } else {
            s.setId(sketches.getValue().get(sketches.getValue().size()-1).getId() + 1);
        }
        s.setTitle("Sketch " + s.getId());

        return s;
    }
}
