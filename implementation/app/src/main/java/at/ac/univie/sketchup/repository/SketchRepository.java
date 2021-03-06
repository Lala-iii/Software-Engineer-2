package at.ac.univie.sketchup.repository;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class SketchRepository {

    private static final SketchRepository instance = new SketchRepository();
    private Context context;

    private SketchRepository() { }

    public static SketchRepository getInstance() {
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Sketch> findAll() {
        return loadSketches();
    }

    public Sketch findOneById(int id) {
        for(Sketch sketch : loadSketches()) {
            if (sketch.getId() == id) return sketch;
        }
        return null; // todo thought not found
    }

    public void deleteById(int id) {
        ArrayList<Sketch> listSketches = loadSketches();
        listSketches.removeIf(sketch -> sketch.getId() == id);
        storeSketches(listSketches);
    }

    public void add(Sketch sketch) {
        ArrayList<Sketch> listSketches = loadSketches();
        listSketches.add(sketch);
        storeSketches(listSketches);
    }

    public void update(Sketch sketch) {
        ArrayList<Sketch> listSketches = loadSketches();
        for(int i = 0; i < listSketches.size(); i++) {
            if (listSketches.get(i).getId() == sketch.getId()) listSketches.set(i, sketch);
        }
        storeSketches(listSketches);

    }

    public void saveCanvas(ArrayList<DrawStrategy> list, ExportFormat format){
        File dir = new File(context.getFilesDir(),"sketchapp");
        ImageSavingFacade savingFacade = new ImageSavingFacade();
        savingFacade.saveImage(list, format, dir, context);
    }

    private void storeSketches(ArrayList<Sketch> sketchesToStore) {
        File dir = new File(context.getFilesDir(), "sketchup");
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            File file = new File(dir, "SketchUp.se2");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(sketchesToStore);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Sketch> loadSketches() {
        ArrayList<Sketch> dataSet = new ArrayList<>();
        try {
        File dir = new File(context.getFilesDir(), "sketchup");
        if (dir.exists()) {
            File file = new File(dir, "SketchUp.se2");
            if (!file.exists()) return new ArrayList<>();
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            dataSet = ((ArrayList<Sketch>) is.readObject());
            is.close();
            fis.close();
        }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dataSet;
    }
}
