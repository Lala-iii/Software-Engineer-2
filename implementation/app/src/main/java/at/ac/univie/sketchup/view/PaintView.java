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
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawQuadrangle;
import at.ac.univie.sketchup.viewmodel.Mode;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class PaintView extends View {

    private SketchEditActivityViewModel sketchViewModel;
    private DrawService drawService;

    private DrawQuadrangle selector;
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

        if (sketchViewModel.getMode().getValue() == SketchEditActivityViewModel.SELECTION && this.selector != null) {
            //drawService.handle(canvas, selector);
            selector.drawObject(canvas);
        }

        if (sketchViewModel.getDrawStrategy() != null)
            sketchViewModel.getDrawStrategy().drawObject(canvas);

        sketchViewModel.getObjectsToDraw().forEach(s -> s.drawObject(canvas));

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (sketchViewModel.getMode().getValue() == SketchEditActivityViewModel.SELECTION) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    selectorBegin = new Coordinate(event.getX(), event.getY());
                    selector = new DrawQuadrangle(new Quadrangle(Color.BLACK, 5, true));
                    selector.getQuadrangle().setAnchorCoordinate(new Coordinate(event.getX(), event.getY()));
                    break;
                case MotionEvent.ACTION_MOVE:
                    selectorEnd = new Coordinate(event.getX(), event.getY());
                    selector.onTouchMove(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    sketchViewModel.getObjectsToDraw().forEach(d -> {
                        if (d.inSelectedArea(selectorBegin, selectorEnd)) {
                            sketchViewModel.setDrawStrategy(d);
                            sketchViewModel.setMode(SketchEditActivityViewModel.EDIT);
                        }
                    });
                    break;
            }
        } else if (sketchViewModel.getMode().getValue() == SketchEditActivityViewModel.EDIT) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    sketchViewModel.getDrawStrategy().onEditDown(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    sketchViewModel.getDrawStrategy().onEditMove(event.getX(), event.getY());
                    // Todo send current position of the selected element and
                    //  store original data so you can adjust it
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    //sketchViewModel.addSelectedToSketch();
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
}
