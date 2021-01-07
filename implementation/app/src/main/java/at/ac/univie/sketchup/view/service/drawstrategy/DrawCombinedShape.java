package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.view.service.DrawService;

public class DrawCombinedShape implements DrawStrategy, Serializable {

    private CombinedShape combinedShape;

    public DrawCombinedShape(DrawableObject drawableObject) {
        this.combinedShape = new CombinedShape(((CombinedShape)drawableObject).getDrawableObjects());
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        if (!(this.combinedShape instanceof CombinedShape)) return false;
        for (DrawStrategy object : ((CombinedShape) this.combinedShape).getDrawableObjects()) {
            object.drawObject(canvas);
        }
        return true;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        return false;
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.combinedShape.onTouchMove(x, y);
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.combinedShape.onTouchMove(x, y);
    }

    @Override
    public void onEditDown(float x, float y) {

    }

    @Override
    public void onEditMove(float x, float y) {

    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.combinedShape;
    }

    @Override
    public void restore() {

    }

    @Override
    public void store() {

    }

    @Override
    public Paint setPaint() {
        return null;
    }
}
