package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawPolygon implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        Path path = new Path();
        Polygon polygon = (Polygon) objectToDraw;

        path.moveTo(polygon.getAnchorCoordinate().getX(), polygon.getAnchorCoordinate().getY());
        for (Coordinate c : polygon.getCoordinates())
            path.lineTo(c.getX(), c.getY());
        //path.close();

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
        return false;
    }

    @Override
    public void onTouchMove(float x, float y, DrawableObject drawableObject) {

    }
}
