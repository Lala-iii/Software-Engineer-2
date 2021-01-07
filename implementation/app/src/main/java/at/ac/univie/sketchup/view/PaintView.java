package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawQuadrangle;
import at.ac.univie.sketchup.viewmodel.Mode;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;


    private DrawQuadrangle selector;

    private DrawService drawService;
//    Bitmap bitmap;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(SketchEditActivityViewModel vm) {
        sketchViewModel = vm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        if (sketchViewModel.getMode().getValue() != null && sketchViewModel.getMode().getValue().equals(SketchEditActivityViewModel.SELECTION) && this.selector != null)
            selector.drawObject(canvas);

        if (sketchViewModel.getDrawStrategy() != null)
            sketchViewModel.getDrawStrategy().drawObject(canvas);

        sketchViewModel.getObjectsToDraw().forEach(s -> s.drawObject(canvas));

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (sketchViewModel.getMode().getValue() == null) return true;
        if (sketchViewModel.getMode().getValue().equals(SketchEditActivityViewModel.SELECTION)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        selector = new DrawQuadrangle(new Quadrangle(Color.BLACK, 5));
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return true;
                    }
                    selector.getDrawableObject().setSelected(true);
                    selector.onTouchDown(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    selector.onTouchMove(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    for (DrawStrategy d : sketchViewModel.getObjectsToDraw()) {
                        if (d.inSelectedArea(selector.getDrawableObject().getAnchorCoordinate(), ((Quadrangle)selector.getDrawableObject()).getEndCoordinate())) {
                            sketchViewModel.setDrawStrategy(d);
                            sketchViewModel.getDrawStrategy().getDrawableObject().setSelected(true);
                            sketchViewModel.setMode(SketchEditActivityViewModel.EDIT);
                            break;
                        }
                    }
                    selector = null;
                    break;
            }
        } else if (sketchViewModel.getMode().getValue().equals(SketchEditActivityViewModel.EDIT)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sketchViewModel.getDrawStrategy().onEditDown(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    sketchViewModel.getDrawStrategy().onEditMove(event.getX(), event.getY());
                    break;
            }
        } else if (sketchViewModel.getMode().getValue() == SketchEditActivityViewModel.CREATE) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sketchViewModel.cloneToNew();
                    sketchViewModel.getDrawStrategy().onTouchDown(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    sketchViewModel.getDrawStrategy().onTouchMove(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    sketchViewModel.addSelectedToSketch();
                    break;
            }
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
