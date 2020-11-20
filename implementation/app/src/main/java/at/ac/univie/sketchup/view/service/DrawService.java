package at.ac.univie.sketchup.view.service;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawTextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawCircle;

public class DrawService {

    private DrawStrategy drawStrategy;

    public void handle(Canvas canvas, DrawableObject objectToDraw) {
        if (objectToDraw instanceof TextBox) {
            drawStrategy = new DrawTextBox();
            drawStrategy.drawObject(objectToDraw, canvas);
        }
        if (objectToDraw instanceof Circle) {
            drawStrategy = new DrawCircle();
            drawStrategy.drawObject(objectToDraw, canvas);
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
        if (objectToDraw instanceof Triangle) {
            float width = ((Triangle) objectToDraw).getWidth();

            Path path = new Path();
            path.moveTo(objectToDraw.getPosition().getX(), objectToDraw.getPosition().getY() - width); // Top
            path.lineTo(objectToDraw.getPosition().getX() - width, objectToDraw.getPosition().getY() + width); // Bottom left
            path.lineTo(objectToDraw.getPosition().getX() + width, objectToDraw.getPosition().getY() + width); // Bottom right
            path.lineTo(objectToDraw.getPosition().getX(), objectToDraw.getPosition().getY() - width); // Back to top
            path.close();

            canvas.drawPath(path, setUpPaint(objectToDraw));
        }

        //drawStrategy.drawObject(objectToDraw, canvas);
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
