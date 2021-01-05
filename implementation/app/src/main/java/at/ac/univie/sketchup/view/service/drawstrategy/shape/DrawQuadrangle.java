package at.ac.univie.sketchup.view.service.drawstrategy.shape;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;

public class DrawQuadrangle implements DrawStrategy {
    public Quadrangle getQuadrangle() {
        return quadrangle;
    }

    public void setQuadrangle(Quadrangle quadrangle) {
        this.quadrangle = quadrangle;
    }

    Quadrangle quadrangle;

    public DrawQuadrangle(DrawableObject drawableObject) {
        quadrangle = new Quadrangle(drawableObject.getColor(), drawableObject.getInputSize(), false);
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
        Paint mPaint = new Paint();
        mPaint.setColor(this.quadrangle.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(this.quadrangle.getInputSize());
        if (this.quadrangle.isSelector())
            mPaint.setPathEffect(new DashPathEffect(new float[]{4,4},50));
        return mPaint;
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
    public void onTouchMove(float x, float y) {
        // TODO handle moving of selected element
        this.quadrangle.onTouchMove(x, y);
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
