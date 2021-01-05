package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawTextBox implements DrawStrategy {
    private final TextBox textBox;

    public DrawTextBox(DrawableObject drawableObject) {
        this.textBox = new TextBox(drawableObject.getColor(), drawableObject.getInputSize(), ((TextBox)drawableObject).getText());
    }
    @Override
    public boolean drawObject(Canvas canvas) {
        canvas.drawText(
                this.textBox.getText(),
                this.textBox.getAnchorCoordinate().getX(),
                this.textBox.getAnchorCoordinate().getY(),
                setPaint()
        );
        return true;
    }

    @Override
    public Paint setPaint() {
        Paint mPaint = new Paint();
        mPaint.setColor(this.textBox.getColor().getAndroidColor());
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(this.textBox.getInputSize());
        return mPaint;
    }


    @Override
    public boolean inSelectedArea(Coordinate begin, Coordinate end) {
        float beginCircleX =  this.textBox.getAnchorCoordinate().getX();
        float beginCircleY =  this.textBox.getAnchorCoordinate().getY();
        float beginX = Math.min(end.getX(), begin.getX());
        float beginY = Math.min(end.getY(), begin.getY());
        float endX = Math.max(end.getX(), begin.getX());
        float endY = Math.max(end.getY(), begin.getY());

        return (beginCircleX > beginX && beginCircleY > beginY &&
                beginCircleX < endX && beginCircleY < endY);

    }

    @Override
    public void onTouchMove(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onTouchDown(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onEditDown(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
    }

    @Override
    public void onEditMove(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
    }
}
