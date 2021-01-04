package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawCircle implements DrawStrategy {

    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        canvas.drawCircle(
                objectToDraw.getAnchorCoordinate().getX(),
                objectToDraw.getAnchorCoordinate().getY(),
                ((Circle) objectToDraw).getRadius(),
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

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end, DrawableObject drawableObject) {
        float beginTriangleX = drawableObject.getAnchorCoordinate().getX();
        float beginTriangleY = drawableObject.getAnchorCoordinate().getY();
        float beginX = end.getX() < begin.getX() ? end.getX() : begin.getX();
        float beginY = end.getY() < begin.getY() ? end.getX() : begin.getX();
        float endX = end.getX() > begin.getX() ? end.getX() : begin.getX();
        float endY = end.getY() > begin.getY() ? end.getY() : begin.getY();
        float radius = ((Circle)drawableObject).getRadius();

        return (beginTriangleX - radius  > beginX && beginTriangleY - radius > beginY &&
                beginTriangleX + radius < endX && beginTriangleY + radius < endY);
    }

    @Override
    public void onTouchMove(float x, float y, DrawableObject drawableObject) {

    }

}
