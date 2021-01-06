package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawTriangle implements DrawStrategy {
    private Triangle triangle;
    private Coordinate originalAnchorCoordinate;
    private Coordinate originalEndCoordinate;
    private Coordinate begin;
    private Coordinate end;

    public DrawTriangle(DrawableObject drawableObject) {
        this.triangle = new Triangle(drawableObject.getColor(), drawableObject.getInputSize());
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        float width = this.triangle.getRadius();

        Path path = new Path();
        path.moveTo(this.triangle.getAnchorCoordinate().getX(), this.triangle.getAnchorCoordinate().getY() - width); // Top
        path.lineTo(this.triangle.getAnchorCoordinate().getX() - width, this.triangle.getAnchorCoordinate().getY() + width); // Bottom left
        path.lineTo(this.triangle.getAnchorCoordinate().getX() + width, this.triangle.getAnchorCoordinate().getY() + width); // Bottom right
        path.lineTo(this.triangle.getAnchorCoordinate().getX(), this.triangle.getAnchorCoordinate().getY() - width); // Back to top
        path.close();

        canvas.drawPath(path, setPaint());

        return true;
    }

    @Override
    public Paint setPaint() {
        Paint paint = new Paint();
        paint.setColor(this.triangle.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.triangle.getInputSize());
        if (this.triangle.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4},50));
        return paint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        float beginTriangleX = this.triangle.getAnchorCoordinate().getX();
        float beginTriangleY  = this.triangle.getAnchorCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());
        float radius = this.triangle.getRadius();

        return (beginTriangleX - radius  > beginX && beginTriangleY - radius > beginY &&
                beginTriangleX + radius < endX && beginTriangleY + radius < endY);
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.triangle.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = this.triangle.getAnchorCoordinate();
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.triangle.setEndCoordinate(new Coordinate(x, y));
        this.originalEndCoordinate = this.triangle.getEndCoordinate();
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = this.triangle.getAnchorCoordinate();
        this.end = this.triangle.getEndCoordinate();
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.triangle.setAnchorCoordinate(new Coordinate(x, y));
        this.triangle.setEndCoordinate(new Coordinate(end.getX() + diffX, end.getY() + diffY));
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.triangle;
    }

    @Override
    public void restore() {
        this.triangle.setAnchorCoordinate(this.originalAnchorCoordinate);
        this.triangle.setEndCoordinate(this.originalEndCoordinate);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = this.triangle.getAnchorCoordinate();
        this.originalEndCoordinate = this.triangle.getEndCoordinate();
    }

}
