package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;
import at.ac.univie.sketchup.viewmodel.Mode;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;
    private DrawService drawService;

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

        drawService.handle(canvas, sketchViewModel.getDrawableObject());

        for (DrawableObject objectToDraw : sketchViewModel.getObjectsToDraw()) {
            drawService.handle(canvas, objectToDraw);
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (sketchViewModel.getMode() == Mode.EDIT) {

        }
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
}
