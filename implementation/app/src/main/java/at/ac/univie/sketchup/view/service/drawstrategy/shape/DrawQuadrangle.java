package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawQuadrangle implements DrawStrategy {
    @Override
    public boolean drawObject(DrawableObject objectToDraw, Canvas canvas) {
        @SuppressLint("DrawAllocation")
        Rect rect = new Rect(
                (int) objectToDraw.getAnchorCoordinate().getX(),
                (int) objectToDraw.getAnchorCoordinate().getY(),
                (int) ((Quadrangle) objectToDraw).getEndCoordinate().getX(),
                (int) ((Quadrangle) objectToDraw).getEndCoordinate().getY()
        );
        canvas.drawRect(rect, setPaint(objectToDraw));

        return true;
    }

    @Override
    public Paint setPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(objectToDraw.getInputSize());
        if (objectToDraw.isSelector())
            mPaint.setPathEffect(new DashPathEffect(new float[]{4,4},50));
        return mPaint;
    }

    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end, DrawableObject drawableObject) {
        float beginQuadrangleX = drawableObject.getAnchorCoordinate().getX();
        float beginQuadrangleY = drawableObject.getAnchorCoordinate().getY();
        float endQuadrangleX = ((Quadrangle)drawableObject).getEndCoordinate().getX();
        float endQuadrangleY = ((Quadrangle)drawableObject).getEndCoordinate().getY();
        float beginX = end.getX() < begin.getX() ? end.getX() : begin.getX();
        float beginY = end.getY() < begin.getY() ? end.getX() : begin.getX();
        float endX = end.getX() > begin.getX() ? end.getX() : begin.getX();
        float endY = end.getY() > begin.getY() ? end.getY() : begin.getY();

        return (beginQuadrangleX > beginX && beginQuadrangleY > beginY &&
                endQuadrangleX < endX && endQuadrangleY < endY);
    }

    @Override
    public void onTouchMove(float x, float y, DrawableObject drawableObject) {
        // TODO handle moving of selected element
        drawableObject.onTouchMove(x, y);
    }
}
