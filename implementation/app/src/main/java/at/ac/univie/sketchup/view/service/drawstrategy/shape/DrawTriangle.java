package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawTriangle implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        float width = ((Triangle) objectToDraw).getWidth();

        Path path = new Path();
        path.moveTo(objectToDraw.getPosition().getX(), objectToDraw.getPosition().getY() - width); // Top
        path.lineTo(objectToDraw.getPosition().getX() - width, objectToDraw.getPosition().getY() + width); // Bottom left
        path.lineTo(objectToDraw.getPosition().getX() + width, objectToDraw.getPosition().getY() + width); // Bottom right
        path.lineTo(objectToDraw.getPosition().getX(), objectToDraw.getPosition().getY() - width); // Back to top
        path.close();

        canvas.drawPath(path, setPaint(objectToDraw));

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
