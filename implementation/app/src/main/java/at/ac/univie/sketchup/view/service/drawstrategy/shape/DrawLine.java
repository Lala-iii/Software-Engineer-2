package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawLine implements DrawStrategy, Serializable {
    private Line line;
    private Coordinate begin;
    private Coordinate originalAnchorCoordinate;
    private Coordinate originalEndCoordinate;

    public DrawLine(DrawableObject drawableObject) throws CloneNotSupportedException {
        this.line = (Line) drawableObject.clone();
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
        Paint paint = new Paint();
        paint.setColor(this.line.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.line.getInputSize());
        if (this.line.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4},50));
        return paint;
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
        this.originalEndCoordinate = new Coordinate(x, y);
        this.line.setEndCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.originalAnchorCoordinate = new Coordinate(x, y);
        this.line.setAnchorCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = new Coordinate(x, y);
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.line.setAnchorCoordinate(new Coordinate(this.line.getAnchorCoordinate().getX() + diffX, this.line.getAnchorCoordinate().getY() + diffY));
        this.line.setEndCoordinate(new Coordinate(this.line.getEndCoordinate().getX() + diffX, this.line.getEndCoordinate().getY() + diffY));

        this.begin = new Coordinate(x, y);
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.line;
    }

    @Override
    public void restore() {
        this.line.setAnchorCoordinate(this.originalAnchorCoordinate);
        this.line.setEndCoordinate(this.originalEndCoordinate);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = this.line.getAnchorCoordinate();
        this.originalEndCoordinate = this.line.getEndCoordinate();
    }
}