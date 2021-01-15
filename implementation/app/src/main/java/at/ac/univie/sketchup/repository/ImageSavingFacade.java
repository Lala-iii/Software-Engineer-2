package at.ac.univie.sketchup.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class ImageSavingFacade {

    public void saveImage(ArrayList<DrawStrategy> list, ExportFormat format, File dir, Context context) {

        try{
            String filename = "Sketch" + System.currentTimeMillis();
            File file= new File(dir,filename+"."+format);
            FileOutputStream fos= new FileOutputStream(file);
            if(format == ExportFormat.JPG) {
                saveAsJpg(list, fos, context);
            }else
            {
                saveAsPng(list, fos, context);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    private void saveAsJpg(ArrayList<DrawStrategy> list, FileOutputStream fos, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(1080,1920,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        canvas.drawColor(Color.WHITE);
        list.forEach(d->d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

    }

    private void saveAsPng(ArrayList<DrawStrategy> list, FileOutputStream fos, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        list.forEach(d -> d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
    }
}
