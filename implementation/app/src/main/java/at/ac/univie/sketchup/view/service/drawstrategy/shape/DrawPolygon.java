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
    private Polygon polygon;

    public DrawPolygon(DrawableObject drawableObject) {
        this.polygon = new Polygon(drawableObject.getColor(), drawableObject.getInputSize());
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        Path path = new Path();
        path.moveTo(this.polygon.getAnchorCoordinate().getX(), this.polygon.getAnchorCoordinate().getY());
        for (Coordinate c : this.polygon.getCoordinates())
            path.lineTo(c.getX(), c.getY());
        //path.close();

        canvas.drawPath(path, setPaint());
        return true;
    }

    @Override
    public Paint setPaint() {
        Paint mPaint = new Paint();
        mPaint.setColor(this.polygon.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(this.polygon.getInputSize());
        return mPaint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        return false;
    }

    @Override
    public void onTouchMove(float x, float y) {

    }

    @Override
    public void onTouchDown(float x, float y) {

    }

    @Override
    public void onEditDown(float x, float y) {

    }

    @Override
    public void onEditMove(float x, float y) {

    }
}
