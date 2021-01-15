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

    /**
     * Exports DrawStrategies as a image according to its format
     * @param list the list of DrawStrategies
     * @param format The format of the image i.e. ExportFormat.PNG
     * @param dir The root directory where the image file should be saved
     * @param context The ApplicationContext
     */
    public void saveImage(ArrayList<DrawStrategy> list, ExportFormat format, File dir, Context context) {
        try{
            String filename = "Sketch" + System.currentTimeMillis();
            File file= new File(dir,filename+"."+format);
            FileOutputStream fos= new FileOutputStream(file);
            if(format == ExportFormat.JPG) {
                saveAsJpg(list, fos, context);
            } else {
                saveAsPng(list, fos, context);
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    /**
     * Exports the DrawStrategies as a JPEG image
     * @param list The list of DrawStrategies
     * @param fos The FileOutputStream for the exporting progress
     * @param context The ApplicationContext
     */
    private void saveAsJpg(ArrayList<DrawStrategy> list, FileOutputStream fos, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(1080,1920,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        canvas.drawColor(Color.WHITE);
        list.forEach(d->d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

    }

    /**
     * Exports the DrawStrategies as a PNG image
     * @param list The list of DrawStrategies
     * @param fos The FileOutputStream for the exporting progress
     * @param context The ApplicationContext
     */
    private void saveAsPng(ArrayList<DrawStrategy> list, FileOutputStream fos, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        View view = new View(context);
        list.forEach(d -> d.drawObject(canvas));
        view.draw(canvas);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
    }
}
