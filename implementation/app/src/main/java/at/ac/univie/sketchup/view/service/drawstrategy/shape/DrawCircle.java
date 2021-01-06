package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawCircle implements DrawStrategy {
    private final Circle circle;
    private Coordinate origin;
    private float width;
    private float height;
    private Coordinate originalEndCoordinate;
    private Coordinate originalAnchorCoordinate;
    private Coordinate end;
    private Coordinate begin;

    public DrawCircle(DrawableObject drawableObject) {
        this.circle = new Circle(drawableObject.getColor(), drawableObject.getInputSize());
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        canvas.drawCircle(
                this.circle.getAnchorCoordinate().getX(),
                this.circle.getAnchorCoordinate().getY(),
                this.circle.getRadius(),
                setPaint()
        );
        return true;
    }

    @Override
    public Paint setPaint() {
        Paint paint = new Paint();
        paint.setColor(this.circle.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.circle.getInputSize());
        if (this.circle.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4},50));
        return paint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        float beginCircleX = this.circle.getAnchorCoordinate().getX();
        float beginCircleY = this.circle.getAnchorCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());
        float radius = this.circle.getRadius();

        return (beginCircleX - radius > beginX && beginCircleY - radius > beginY &&
                beginCircleX + radius < endX && beginCircleY + radius < endY);
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.circle.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = this.circle.getAnchorCoordinate();
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.circle.setEndCoordinate(new Coordinate(x, y));
        this.originalEndCoordinate = this.circle.getEndCoordinate();
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = this.circle.getAnchorCoordinate();
        this.end = this.circle.getEndCoordinate();
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.circle.setAnchorCoordinate(new Coordinate(x, y));
        this.circle.setEndCoordinate(new Coordinate(end.getX() + diffX, end.getY() + diffY));
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.circle;
    }

    @Override
    public void restore() {
        this.circle.setAnchorCoordinate(this.originalAnchorCoordinate);
        this.circle.setEndCoordinate(this.originalEndCoordinate);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = this.circle.getAnchorCoordinate();
        this.originalEndCoordinate = this.circle.getEndCoordinate();
    }
}
