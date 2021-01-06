package at.ac.univie.sketchup.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.sketchup.model.Sketch;

public class SketchRepository {

    private static final SketchRepository instance = new SketchRepository();
    private ArrayList<Sketch> dataSet = new ArrayList<>();

    private SketchRepository(){}

    public static SketchRepository getInstance(){
        return instance;
    }

    // TODO: get data from storage
    public MutableLiveData<List<Sketch>> findAll() {
        //createSeedData();

        MutableLiveData<List<Sketch>> data = new MutableLiveData<>();
        data.setValue(dataSet);

        return data;
    }

    public MutableLiveData<Sketch> findOneById(int id){
        MutableLiveData<Sketch> data = new MutableLiveData<>();
        data.setValue(dataSet.get(id-1));

        return data;
    }

    public void deleteById(int id) {
        for(int i = 0; i < dataSet.size(); i++ ){
            if (dataSet.get(i).getId() == id) dataSet.remove(i);
        }
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
    public void storeSketches(Context context){
        File dir = new File(context.getFilesDir(), "mydir");
        if(!dir.exists()){
            dir.mkdir();
        }

        try {
            File file = new File(dir, "SketchUp.se2");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(dataSet);
            os.close();
            fos.close();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public ArrayList<Sketch> loadSketches(Context context) throws IOException, ClassNotFoundException {
        File dir = new File(context.getFilesDir(), "mydir");
        if(dir.exists()) {
            File file = new File(dir, "SketchUp.se2");
            if (!file.exists()) return new ArrayList<>();
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            dataSet = ((ArrayList<Sketch>) is.readObject());
            is.close();
            fis.close();
        }
        return dataSet;
    }
}
