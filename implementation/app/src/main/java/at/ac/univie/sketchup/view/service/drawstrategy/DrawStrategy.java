package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;

public interface DrawStrategy {

    boolean drawObject(DrawableObject objectToDraw, Canvas canvas);
    Paint setPaint(DrawableObject objectToDraw);
}
