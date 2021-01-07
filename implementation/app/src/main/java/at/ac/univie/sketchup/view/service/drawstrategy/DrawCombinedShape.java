package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.DoublePointShape;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.view.service.DrawService;

public class DrawCombinedShape implements DrawStrategy, Serializable {

    private CombinedShape combinedShape;

    public DrawCombinedShape(DrawableObject drawableObject) {
        this.combinedShape = new CombinedShape(((CombinedShape)drawableObject).getDrawableObjects());
    }

    @Override
    public boolean drawObject(Canvas canvas) {
        if (!(this.combinedShape instanceof CombinedShape)) return false;
        for (DrawStrategy object : this.combinedShape.getDrawableObjects()) {
            object.drawObject(canvas);
        }
        return true;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        return false;
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.onTouchMove(x, y);
    }

    @Override
    public void onTouchMove(float x, float y) {
        Coordinate diff = getDiffForNewCoordinate(x, y);

        this.combinedShape.getDrawableObjects().forEach(obj -> setNewCoordinate(obj, diff));
    }

    private void setNewCoordinate(DrawStrategy obj, Coordinate diff) {
        float newX;
        float newY;

        if (obj instanceof DoublePointShape) {
            newX = ((DoublePointShape) obj).getEndCoordinate().getX() + diff.getX();
            newY = ((DoublePointShape) obj).getEndCoordinate().getY() + diff.getY();
            ((DoublePointShape) obj).setEndCoordinate(new Coordinate(newX, newY));
        }

        if (obj instanceof Polygon) {
            for (Coordinate c : ((Polygon) obj).getCoordinates()) {
                c.setX(c.getX() + diff.getX());
                c.setY(c.getY() + diff.getY());
            }
        }

        newX = obj.getDrawableObject().getAnchorCoordinate().getX() + diff.getX();
        newY = obj.getDrawableObject().getAnchorCoordinate().getY() + diff.getY();
        obj.getDrawableObject().setAnchorCoordinate(new Coordinate(newX, newY));
    }

    private Coordinate getDiffForNewCoordinate(float x, float y) {
        if (this.combinedShape.getDrawableObjects() == null || this.combinedShape.getDrawableObjects().size() == 0) return null;
        Coordinate c = new Coordinate(
                x - this.combinedShape.getDrawableObjects().get(0).getDrawableObject().getAnchorCoordinate().getX(),
                y - this.combinedShape.getDrawableObjects().get(0).getDrawableObject().getAnchorCoordinate().getY()
        );

        return c;
    }

    @Override
    public void onEditDown(float x, float y) {

    }

    @Override
    public void onEditMove(float x, float y) {

    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.combinedShape;
    }

    @Override
    public void restore() {

    }

    @Override
    public void store() {

    }

    @Override
    public Paint setPaint() {
        return null;
    }
}
