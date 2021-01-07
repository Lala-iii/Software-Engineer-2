package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public interface DrawStrategy {
    boolean drawObject(Canvas canvas);
    Paint setPaint();
    boolean inSelectedArea(Coordinate begin, Coordinate end);
    void onTouchDown(float x, float y);
    void onTouchMove(float x, float y);
    void onEditDown(float x, float y);
    void onEditMove(float x, float y);

    DrawableObject getDrawableObject();

    void restore();
    void store();
}
