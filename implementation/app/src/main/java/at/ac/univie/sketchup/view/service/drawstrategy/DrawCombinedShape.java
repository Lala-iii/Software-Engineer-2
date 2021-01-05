package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.view.service.DrawService;

public class DrawCombinedShape implements DrawStrategy {

    DrawService drawService = new DrawService();

    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        if (!(objectToDraw instanceof CombinedShape)) return false;
        for (DrawableObject object : ((CombinedShape) objectToDraw).getDrawableObjects()) {
            drawService.handle(canvas, object);
        }
        return true;
    }

    @Override
    public Paint setPaint(DrawableObject objectToDraw) {
        return null;
    }
}
