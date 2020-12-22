package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawQuadrangle implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        @SuppressLint("DrawAllocation")
        Rect rect = new Rect(
                (int) objectToDraw.getAnchorCoordinate().getX(),
                (int) objectToDraw.getAnchorCoordinate().getY(),
                (int) ((Quadrangle) objectToDraw).getEndCoordinate().getX(),
                (int) ((Quadrangle) objectToDraw).getEndCoordinate().getY()
        );
        canvas.drawRect(rect, setPaint(objectToDraw));

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
