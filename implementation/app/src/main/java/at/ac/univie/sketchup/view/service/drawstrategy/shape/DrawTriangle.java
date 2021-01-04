package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawTriangle implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        float width = ((Triangle) objectToDraw).getRadius();

        Path path = new Path();
        path.moveTo(objectToDraw.getAnchorCoordinate().getX(), objectToDraw.getAnchorCoordinate().getY() - width); // Top
        path.lineTo(objectToDraw.getAnchorCoordinate().getX() - width, objectToDraw.getAnchorCoordinate().getY() + width); // Bottom left
        path.lineTo(objectToDraw.getAnchorCoordinate().getX() + width, objectToDraw.getAnchorCoordinate().getY() + width); // Bottom right
        path.lineTo(objectToDraw.getAnchorCoordinate().getX(), objectToDraw.getAnchorCoordinate().getY() - width); // Back to top
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

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end, DrawableObject drawableObject) {
        float beginTriangleX = drawableObject.getAnchorCoordinate().getX();
        float beginTriangleY = drawableObject.getAnchorCoordinate().getY();
        float beginX = end.getX() < begin.getX() ? end.getX() : begin.getX();
        float beginY = end.getY() < begin.getY() ? end.getX() : begin.getX();
        float endX = end.getX() > begin.getX() ? end.getX() : begin.getX();
        float endY = end.getY() > begin.getY() ? end.getY() : begin.getY();
        float radius = ((Triangle)drawableObject).getRadius();

        return (beginTriangleX - radius  > beginX && beginTriangleY - radius > beginY &&
                beginTriangleX + radius < endX && beginTriangleY + radius < endY);
    }

    @Override
    public void onTouchMove(float x, float y, DrawableObject drawableObject) {

    }

}
