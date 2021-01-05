package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawTextBox implements DrawStrategy {
    private final TextBox textBox;
    private float x, y;

    public DrawTextBox(DrawableObject drawableObject) {
        this.textBox = new TextBox(drawableObject.getColor(), drawableObject.getInputSize());
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
        return false;
    }

    @Override
    public void onTouchMove(float x, float y) {

    }

    @Override
    public void onTouchDown(float x, float y) {

    }

    @Override
    public void onEditDown(float x, float y) {
        this.x = textBox.getAnchorCoordinate().getX();
    }

    @Override
    public void onEditMove(float x, float y) {

    }
}
