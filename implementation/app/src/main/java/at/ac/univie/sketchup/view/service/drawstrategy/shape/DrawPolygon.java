package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawPolygon implements DrawStrategy, Serializable {
    private Polygon polygon;
    private Coordinate originalAnchorCoordinate;
    private List<Coordinate> originalCoordinates;

    private Coordinate begin;

    public DrawPolygon(DrawableObject drawableObject) throws CloneNotSupportedException {
        this.polygon = (Polygon) drawableObject.clone();
        this.originalCoordinates = new ArrayList<>();
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        Path path = new Path();
        path.moveTo(this.polygon.getAnchorCoordinate().getX(), this.polygon.getAnchorCoordinate().getY());
        for (Coordinate c : this.polygon.getCoordinates())
            path.lineTo(c.getX(), c.getY());

        canvas.drawPath(path, setPaint());
        return true;
    }

    @Override
    public Paint setPaint() {
        Paint paint = new Paint();
        paint.setColor(this.polygon.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(this.polygon.getInputSize());
        if (this.polygon.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4}, 50));
        return paint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        float beginPolygonX = this.polygon.getAnchorCoordinate().getX();
        float beginPolygonY = this.polygon.getAnchorCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());

        if (beginPolygonX > beginX && beginPolygonY > beginY &&
                beginPolygonX < endX && beginPolygonY < endY) {
            for (Coordinate c : this.polygon.getCoordinates()) {
                if (!(c.getX() > beginX && c.getY() > beginY &&
                        c.getX() < endX && c.getY() < endY))
                    return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.polygon.initializeList();
        this.polygon.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = new Coordinate(x, y);
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.polygon.addCoordinate(x, y);
        this.originalCoordinates.add(new Coordinate(x, y));
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = new Coordinate(x, y);
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) *(-1);
        float diffY = (begin.getY() - y) *(-1);
        this.polygon.setAnchorCoordinate(new Coordinate(this.polygon.getAnchorCoordinate().getX() + diffX, this.polygon.getAnchorCoordinate().getY() + diffY));
        for (Coordinate c : this.polygon.getCoordinates()) {
            c.setX(c.getX() + diffX);
            c.setY(c.getY() + diffY);
        };

        this.begin = new Coordinate(x, y);
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.polygon;
    }

    @Override
    public void restore() {
        this.polygon.setAnchorCoordinate(new Coordinate(this.originalAnchorCoordinate.getX(), this.originalAnchorCoordinate.getY()));
        this.polygon.setCoordinates(this.originalCoordinates);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = new Coordinate(this.polygon.getAnchorCoordinate().getX(), this.polygon.getAnchorCoordinate().getY());
        this.originalCoordinates = new ArrayList<>();
        this.polygon.getCoordinates().forEach(c -> this.originalCoordinates.add(new Coordinate(c.getX(), c.getY())));
    }
}
