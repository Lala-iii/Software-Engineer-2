package at.ac.univie.sketchup.view.service;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawService {

    public static void handle(Canvas canvas, DrawableObject objectToDraw) {
        if (objectToDraw instanceof TextBox) {
            canvas.drawText(
                    ((TextBox) objectToDraw).getText(),
                    objectToDraw.getPosition().getX(),
                    objectToDraw.getPosition().getY(),
                    setUpPaint(objectToDraw)
            );
        }
        if (objectToDraw instanceof Circle) {
            canvas.drawCircle(
                    objectToDraw.getPosition().getX(),
                    objectToDraw.getPosition().getY(),
                    ((Circle) objectToDraw).getRadius(),
                    setUpPaint(objectToDraw)
            );
        }
        if (objectToDraw instanceof Line) {
            canvas.drawLine(
                    objectToDraw.getPosition().getX(),
                    objectToDraw.getPosition().getY(),
                    ((Line) objectToDraw).getEndPoint().getX(),
                    ((Line) objectToDraw).getEndPoint().getY(),
                    setUpPaint(objectToDraw)
            );
        }
        if (objectToDraw instanceof Quadrangle) {
            @SuppressLint("DrawAllocation")
            Rect rect = new Rect(
                    (int) objectToDraw.getPosition().getX(),
                    (int) objectToDraw.getPosition().getY(),
                    (int) ((Quadrangle) objectToDraw).getEndPoint().getX(),
                    (int) ((Quadrangle) objectToDraw).getEndPoint().getY()
            );
            canvas.drawRect(
                    rect,
                    setUpPaint(objectToDraw)
            );
        }
    }

    private static Paint setUpPaint(DrawableObject objectToDraw) {
        Paint mPaint = new Paint();
        mPaint.setColor(objectToDraw.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(objectToDraw.getInputSize());
        mPaint.setStrokeWidth(objectToDraw.getInputSize());
        return mPaint;
    }
}
