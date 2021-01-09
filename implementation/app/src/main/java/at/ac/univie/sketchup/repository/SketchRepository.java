package at.ac.univie.sketchup.repository;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.widget.LinearLayout;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import at.ac.univie.sketchup.R;
import at.ac.univie.sketchup.model.Sketch;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class SketchRepository {

    private static final SketchRepository instance = new SketchRepository();
    private Context context;

    private SketchRepository() {}

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
    private void saveAsJpg(ArrayList<DrawStrategy> list,FileOutputStream fos){
        Bitmap bitmap = Bitmap.createBitmap(1080,1920,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        list.forEach(d->d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

    }


    private void saveAsPng(ArrayList<DrawStrategy> list, FileOutputStream fos) {
        Bitmap bitmap = Bitmap.createBitmap(1080,1920,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        list.forEach(d->d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
    }
    public void saveCanvas(ArrayList<DrawStrategy> list,String format){
        File dir = new File(context.getFilesDir(),"sketchapp");
        if(!dir.exists()){
            dir.mkdir();
        }
        try{
            File file= new File(dir,"SketchUp."+format);
            FileOutputStream fos= new FileOutputStream(file);
            if(format.equals("JPEG")) {
                saveAsJpg(list, fos);
            }else
            {
                saveAsPng(list,fos);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }
    /*public void saveAsJpg(ArrayList<Sketch> sketchesToSave) {
        View view = this.view.findViewById(R.layout.activity_sketch_editor);
        try{

            ContextWrapper cw = new ContextWrapper(this.context);
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
            File mypath=new File(directory,"profile2.jpg");
            view.setDrawingCacheEnabled(true);
            Bitmap b = view.getDrawingCache();
            b.compress(Bitmap.CompressFormat.JPEG, 95, new FileOutputStream("/some/location/image.jpg"));
            //view.buildDrawingCache();
            //Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            FileOutputStream fos = new FileOutputStream(mypath);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(sketchesToSave);
            os.close();
            fos.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }*/
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
