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
    private Triangle triangle;

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
        Paint mPaint = new Paint();
        mPaint.setColor(this.triangle.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(this.triangle.getInputSize());
        return mPaint;
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
