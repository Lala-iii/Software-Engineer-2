package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawLine implements DrawStrategy {
    private Line line;
    private Coordinate begin;
    private Coordinate end;

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
        float beginLineX = this.line.getAnchorCoordinate().getX();
        float beginLineY = this.line.getAnchorCoordinate().getY();
        float endLineX = this.line.getEndCoordinate().getX();
        float endLineY = this.line.getEndCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());

        return (beginLineX > beginX && beginLineY > beginY &&
                endLineX < endX && endLineY < endY);
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.line.setEndCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.line.setAnchorCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = this.line.getAnchorCoordinate();
        this.end = this.line.getEndCoordinate();
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.line.setAnchorCoordinate(new Coordinate(x, y));
        this.line.setEndCoordinate(new Coordinate(end.getX() + diffX, end.getY() + diffY));
    }
}