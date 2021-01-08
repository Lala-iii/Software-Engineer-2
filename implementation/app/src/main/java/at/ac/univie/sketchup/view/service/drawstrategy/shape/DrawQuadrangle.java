package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawQuadrangle implements DrawStrategy, Serializable {
    private Quadrangle quadrangle;
    private Coordinate originalAnchorCoordinate;
    private Coordinate originalEndCoordinate;
    private Coordinate begin;
    private Coordinate end;

    public DrawQuadrangle(DrawableObject drawableObject) throws CloneNotSupportedException {
        quadrangle = (Quadrangle) drawableObject.clone();
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        @SuppressLint("DrawAllocation")
        Rect rect = new Rect(
                (int) this.quadrangle.getAnchorCoordinate().getX(),
                (int) this.quadrangle.getAnchorCoordinate().getY(),
                (int) this.quadrangle.getEndCoordinate().getX(),
                (int) this.quadrangle.getEndCoordinate().getY()
        );
        canvas.drawRect(rect, setPaint());

        return true;
    }

    @Override
    public Paint setPaint() {
        Paint paint = new Paint();
        paint.setColor(this.quadrangle.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.quadrangle.getInputSize());
        if (this.quadrangle.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4},50));
        return paint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        float beginQuadrangleX = this.quadrangle.getAnchorCoordinate().getX();
        float beginQuadrangleY = this.quadrangle.getAnchorCoordinate().getY();
        float endQuadrangleX = this.quadrangle.getEndCoordinate().getX();
        float endQuadrangleY = this.quadrangle.getEndCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());

        return (beginQuadrangleX > beginX && beginQuadrangleY > beginY &&
                endQuadrangleX < endX && endQuadrangleY < endY);
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.quadrangle.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = this.quadrangle.getAnchorCoordinate();
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.quadrangle.setEndCoordinate(new Coordinate(x, y));
        this.originalEndCoordinate = this.quadrangle.getEndCoordinate();
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = this.quadrangle.getAnchorCoordinate();
        this.end = this.quadrangle.getEndCoordinate();
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.quadrangle.setAnchorCoordinate(new Coordinate(x, y));
        this.quadrangle.setEndCoordinate(new Coordinate(end.getX() + diffX, end.getY() + diffY));
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.quadrangle;
    }

    @Override
    public void restore() {
        this.quadrangle.setAnchorCoordinate(this.originalAnchorCoordinate);
        this.quadrangle.setEndCoordinate(this.originalEndCoordinate);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = this.quadrangle.getAnchorCoordinate();
        this.originalEndCoordinate = this.quadrangle.getEndCoordinate();
    }
}
