package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawTextBox implements DrawStrategy {

    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        canvas.drawText(
                ((TextBox) objectToDraw).getText(),
                objectToDraw.getAnchorCoordinate().getX(),
                objectToDraw.getAnchorCoordinate().getY(),
                setPaint(objectToDraw)
        );
        return true;
    }

    @Override
    public Paint setPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(objectToDraw.getInputSize());
        return mPaint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end, DrawableObject drawableObject) {
        return false;
    }

    @Override
    public void onTouchMove(float x, float y, DrawableObject drawableObject) {

    }
}
