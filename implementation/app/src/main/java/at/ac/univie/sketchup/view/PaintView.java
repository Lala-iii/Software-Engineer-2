package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.DrawService;
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
            DrawService.handle(canvas, objectToDraw);
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (null != selected) {
                    sketchViewModel.addDrawableObject(selected, event.getX(), event.getY());
                }
        }
        return true;
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
