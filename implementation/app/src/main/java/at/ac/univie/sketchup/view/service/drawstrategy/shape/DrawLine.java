package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawLine implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        canvas.drawLine(
                objectToDraw.getAnchorCoordinate().getX(),
                objectToDraw.getAnchorCoordinate().getY(),
                ((Line) objectToDraw).getEndCoordinate().getX(),
                ((Line) objectToDraw).getEndCoordinate().getY(),
                setPaint(objectToDraw)
        );

        return true;
    }

    @Override
    public Paint setPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(objectToDraw.getInputSize());
        return mPaint;
    }
}
