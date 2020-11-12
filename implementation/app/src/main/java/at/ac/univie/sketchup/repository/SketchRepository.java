package at.ac.univie.sketchup.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.sketchup.model.Sketch;

public class SketchRepository {

    private static SketchRepository instance = new SketchRepository();
    private ArrayList<Sketch> dataSet = new ArrayList<>();

    private SketchRepository(){};

    public static SketchRepository getInstance(){
        return instance;
    }

    // TODO: get data from storage
    public MutableLiveData<List<Sketch>> findAll() {
        createSeedData();

        MutableLiveData<List<Sketch>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    public MutableLiveData<Sketch> findOneById(int id){
        MutableLiveData<Sketch> data = new MutableLiveData<>();
        data.setValue(dataSet.get(id-1));

        return data;
    }

    public void createSketch(Sketch sketch) {
        dataSet.add(sketch);
    }

    /**
     * Create seed data for test. TODO Remove and create separate service that can be reused for test
     */
    private void createSeedData(){
        Sketch s1 = new Sketch();
        s1.setId(1);
        s1.setTitle("Sketch 1");
        dataSet.add(s1);

        Sketch s2 = new Sketch();
        s2.setId(2);
        s2.setTitle("Sketch 2");
        dataSet.add(s2);
    }
}
