package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.Color;
import at.ac.univie.sketchup.model.DrawableObject;
import at.ac.univie.sketchup.model.sketchObjects.Circle;
import at.ac.univie.sketchup.model.sketchObjects.TextBox;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;
    private DrawableObject selected;

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

        for (DrawableObject objectToDraw : sketchViewModel.getObjectsToDraw()) {
            Log.d("colorDraw", objectToDraw.getColor().toString());
            Log.d("sizeDraw", String.valueOf(objectToDraw.getInputSize()));
            if (objectToDraw instanceof TextBox) {
                canvas.drawText(
                        ((TextBox) objectToDraw).getText(),
                        objectToDraw.getPosition().getX(),
                        objectToDraw.getPosition().getY(),
                        setUpPaint(objectToDraw)
                );
            }
            if (objectToDraw instanceof Circle) {
                canvas.drawCircle(
                        objectToDraw.getPosition().getX(),
                        objectToDraw.getPosition().getY(),
                        ((Circle) objectToDraw).getRadius(),
                        setUpPaint(objectToDraw)
                );
            }
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null != selected) {
                    sketchViewModel.addDrawableObject(selected, event.getX(), event.getY());
                    Log.d("Color", selected.getColor().toString());

                }
        }

        return true;
    }

    private Paint setUpPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(objectToDraw.getInputSize());
        mPaint.setStrokeWidth(objectToDraw.getInputSize());
        return mPaint;
    }

    public void setSelected(DrawableObject s) {
        selected = s;
    }

    public void setTextForSelected(String text) {
        if (selected instanceof TextBox) {
            ((TextBox) selected).setText(text);
        } else {
            // todo through a error
        }
    }

    public void setSize(int s) {
        selected.setInputSize(s);
    }

    public void setColor(Color c) {
        selected.setColor(c);
    }
}
