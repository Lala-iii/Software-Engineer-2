package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

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

    private Paint setUpPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(objectToDraw.getInputSize());
        mPaint.setStrokeWidth(objectToDraw.getInputSize());
        return mPaint;
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
}
