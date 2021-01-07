package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;
    private DrawService drawService;
//    Bitmap bitmap;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(SketchEditActivityViewModel vm) {
        sketchViewModel = vm;
        drawService = new DrawService();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        drawService.handle(canvas, sketchViewModel.getDrawableObject()); // draw current obj first

        for (DrawableObject objectToDraw : sketchViewModel.getObjectsToDraw()) {
            drawService.handle(canvas, objectToDraw);
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                sketchViewModel.onTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                sketchViewModel.onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                sketchViewModel.addSelectedToSketch();
                break;
        }
        postInvalidate();
        return true;
    }

   /*public  void saveImage(){
        String filename= "Sketch" + System.currentTimeMillis();
        ContentValues  contentValues= new ContentValues();

        contentValues.put(MediaStore.Images.Media.TITLE, filename);
        contentValues.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");

        Uri uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        try {
            OutputStream outputStream = getContext().getContentResolver().openOutputStream(uri);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
      try {
          outputStream.flush();
          outputStream.close();
          Toast message = Toast.makeText(getContext(),"Image Saved", Toast.LENGTH_LONG);
          message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getYOffset()/2);
          message.show();
      }catch (IOException e){
          Toast message = Toast.makeText(getContext(),"Image Not Saved", Toast.LENGTH_LONG);
          message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getYOffset()/2);
          message.show();
      }
        }
        catch(FileNotFoundException f) {
            Toast message = Toast.makeText(getContext(),"Image Not Saved", Toast.LENGTH_LONG);
            message.setGravity(Gravity.CENTER, message.getXOffset()/2, message.getYOffset()/2);
            message.show();
       //f.printStackTrace();
        }


    }*/
}
