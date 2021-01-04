package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public interface DrawStrategy {

    boolean drawObject(DrawableObject objectToDraw, Canvas canvas);
    Paint setPaint(DrawableObject objectToDraw);
    boolean inSelectedArea(Coordinate begin, Coordinate end, DrawableObject drawableObject);
    void onTouchMove(float x, float y, DrawableObject drawableObject);
}
