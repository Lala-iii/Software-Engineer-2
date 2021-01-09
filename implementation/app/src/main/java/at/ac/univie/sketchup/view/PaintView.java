package at.ac.univie.sketchup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawQuadrangle;
import at.ac.univie.sketchup.viewmodel.SketchEditActivityViewModel;

public class PaintView extends View {
    private SketchEditActivityViewModel sketchViewModel;
    private DrawQuadrangle selector;

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

        //if (sketchViewModel.getDrawStrategy() != null)
        //    sketchViewModel.getDrawStrategy().drawObject(canvas);
        sketchViewModel.getSelectedDrawStrategies().forEach(d -> d.drawObject(canvas));

        sketchViewModel.getDrawStrategies().forEach(s -> s.drawObject(canvas));

        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (sketchViewModel.getMode().getValue() == null) return true;

        Integer mode = sketchViewModel.getMode().getValue();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mode.equals(SketchEditActivityViewModel.CREATE)) {
                    sketchViewModel.cloneFromTemplate();
                    sketchViewModel.getSelectedDrawStrategies().forEach(d -> d.onTouchDown(event.getX(), event.getY()));
                } else if (mode.equals(SketchEditActivityViewModel.EDIT))
                    sketchViewModel.getSelectedDrawStrategies().forEach(d -> d.onEditDown(event.getX(), event.getY()));
                else if (mode.equals(SketchEditActivityViewModel.SELECTION)) {
                    try {
                        selector = new DrawQuadrangle(new Quadrangle(Color.BLACK, 5));
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return true;
                    }
                    selector.getDrawableObject().setSelected(true);
                    selector.onTouchDown(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode.equals(SketchEditActivityViewModel.CREATE))
                    sketchViewModel.getSelectedDrawStrategies().forEach(d -> d.onTouchMove(event.getX(), event.getY()));
                else if (mode.equals(SketchEditActivityViewModel.EDIT))
                    sketchViewModel.getSelectedDrawStrategies().forEach(d -> d.onEditMove(event.getX(), event.getY()));
                else if (mode.equals(SketchEditActivityViewModel.SELECTION))
                    selector.onTouchMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (mode.equals(SketchEditActivityViewModel.CREATE))
                    sketchViewModel.addSelectedToSketch();
                else if (mode.equals(SketchEditActivityViewModel.SELECTION)) {
                    sketchViewModel.deleteSelectedDrawStrategies();
                    for (DrawStrategy d : sketchViewModel.getDrawStrategies())
                        if (d.inSelectedArea(selector.getDrawableObject().getAnchorCoordinate(), ((Quadrangle) selector.getDrawableObject()).getEndCoordinate()))
                            sketchViewModel.addSelectedDrawStrategy(d);
                    if (sketchViewModel.getSelectedDrawStrategies().size() > 0)
                        sketchViewModel.setMode(SketchEditActivityViewModel.EDIT);
                    selector = null;
                }
                break;
        }
        postInvalidate();
        return true;
    }
}
