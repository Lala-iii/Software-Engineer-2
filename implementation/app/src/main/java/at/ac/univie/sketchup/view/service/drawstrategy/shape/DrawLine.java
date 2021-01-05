package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawLine implements DrawStrategy {
    private Line line;

    public DrawLine(DrawableObject drawableObject) {
        this.line = new Line(drawableObject.getColor(), drawableObject.getInputSize());
    }
    @Override
    public boolean drawObject(Canvas canvas) {
        canvas.drawLine(
                this.line.getAnchorCoordinate().getX(),
                this.line.getAnchorCoordinate().getY(),
                this.line.getEndCoordinate().getX(),
                this.line.getEndCoordinate().getY(),
                setPaint()
        );
        return true;
    }

    @Override
    public Paint setPaint() {
        Paint mPaint = new Paint();
        mPaint.setColor(this.line.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(this.line.getInputSize());
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