package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.DoublePointShape;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;

public class DrawCombinedShape implements DrawStrategy, Serializable {

    private CombinedShape combinedShape;

    public DrawCombinedShape(DrawableObject drawableObject) throws CloneNotSupportedException {
        this.combinedShape = (CombinedShape) drawableObject.clone();
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
        float beginCombinedShapeX =  this.combinedShape.getAnchorCoordinate().getX();
        float beginCombinedShapeY =  this.combinedShape.getAnchorCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());

        return (beginCombinedShapeX > beginX && beginCombinedShapeY > beginY &&
                beginCombinedShapeX < endX && beginCombinedShapeY < endY);
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.combinedShape.setAnchorCoordinate(new Coordinate(x, y));

    }

    @Override
    public void onTouchMove(float x, float y) {
        this.combinedShape.setAnchorCoordinate(new Coordinate(x, y));
        Coordinate diff = getDiffForNewCoordinate(x, y);

        this.combinedShape.getDrawableObjects().forEach(obj -> setNewCoordinate(obj, diff));
    }

    private void setNewCoordinate(DrawStrategy obj, Coordinate diff) {
        float newX;
        float newY;

        if (obj.getDrawableObject() instanceof DoublePointShape) {
            newX = ((DoublePointShape) obj.getDrawableObject()).getEndCoordinate().getX() + diff.getX();
            newY = ((DoublePointShape) obj.getDrawableObject()).getEndCoordinate().getY() + diff.getY();
            ((DoublePointShape) obj.getDrawableObject()).setEndCoordinate(new Coordinate(newX, newY));
        }

        if (obj.getDrawableObject() instanceof Polygon) {
            for (Coordinate c : ((Polygon) obj.getDrawableObject()).getCoordinates()) {
                c.setX(c.getX() + diff.getX());
                c.setY(c.getY() + diff.getY());
            }
        }

        if (obj.getDrawableObject() instanceof CombinedShape) {
            ((CombinedShape)obj.getDrawableObject()).getDrawableObjects().forEach(selected -> setNewCoordinate(selected, diff));
            return;
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
        this.combinedShape.getDrawableObjects().forEach(obj -> obj.onEditDown(x, y));
    }

    @Override
    public void onEditMove(float x, float y) {
        this.combinedShape.setAnchorCoordinate(new Coordinate(x, y));
        this.combinedShape.getDrawableObjects().forEach(obj -> obj.onEditMove(x, y));
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
