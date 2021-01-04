package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.DrawService;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;
import at.ac.univie.sketchup.viewmodel.Mode;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;
    private DrawService drawService;

    private DrawableObject selector;
    private Coordinate selectorBegin;
    private Coordinate selectorEnd;

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

        if (sketchViewModel.getMode() == Mode.SELECTION && this.selector != null) {
            drawService.handle(canvas, selector);
        }

        drawService.handle(canvas, sketchViewModel.getDrawableObject());

        for (DrawableObject objectToDraw : sketchViewModel.getObjectsToDraw()) {
            drawService.handle(canvas, objectToDraw);
        }

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (sketchViewModel.getMode() == Mode.SELECTION) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    selectorBegin = new Coordinate(event.getX(), event.getY());
                    selector = new Quadrangle();
                    selector.setSelector();
                    selector.setAnchorCoordinate(new Coordinate(event.getX(), event.getY()));
                    break;
                case MotionEvent.ACTION_MOVE:
                    selectorEnd = new Coordinate(event.getX(), event.getY());
                    selector.onTouchMove(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    sketchViewModel.getObjectsToDraw().forEach(d -> {
                        if (drawService.isSelectDrawableObject(selectorBegin, selectorEnd, d)) {
                            sketchViewModel.setDrawableObject(d);
                            sketchViewModel.setMode(Mode.EDIT);
                        }
                    });

                    break;
            }
        } else if (sketchViewModel.getMode() == Mode.EDIT) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sketchViewModel.onTouchDown(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    sketchViewModel.onTouchMove(event.getX(), event.getY());
                    // Todo send current position of the selected element and store original data so you can adjust it
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    //sketchViewModel.addSelectedToSketch();
                    break;
            }
        } else if (sketchViewModel.getMode() == Mode.CREATE) {
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
        }
        postInvalidate();
        return true;
    }
}
