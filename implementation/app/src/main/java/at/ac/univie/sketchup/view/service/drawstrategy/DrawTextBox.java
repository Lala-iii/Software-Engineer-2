package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;

public class DrawTextBox implements DrawStrategy, Serializable {
    private final TextBox textBox;
    private Coordinate originalAnchorCoordinate;
    private Coordinate begin;

    public DrawTextBox(DrawableObject drawableObject) throws CloneNotSupportedException {
        this.textBox = (TextBox) drawableObject.clone();
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
        Paint paint = new Paint();
        paint.setColor(this.textBox.getColor().getAndroidColor());
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(this.textBox.getInputSize());
        if (this.textBox.isSelected())
            paint.setPathEffect(new DashPathEffect(new float[]{2, 4},50));
        return paint;
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
    public void onTouchDown(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = this.textBox.getAnchorCoordinate();
    }

    @Override
    public void onTouchMove(float x, float y) {
        this.textBox.setAnchorCoordinate(new Coordinate(x, y));
        this.originalAnchorCoordinate = this.textBox.getAnchorCoordinate();
    }

    @Override
    public void onEditDown(float x, float y) {
        this.begin = new Coordinate(x, y);
    }

    @Override
    public void onEditMove(float x, float y) {
        float diffX = (begin.getX() - x) * (-1);
        float diffY = (begin.getY() - y) * (-1);
        this.textBox.setAnchorCoordinate(new Coordinate(this.textBox.getAnchorCoordinate().getX() + diffX, this.textBox.getAnchorCoordinate().getY() + diffY));

        this.begin = new Coordinate(x, y);
    }

    @Override
    public DrawableObject getDrawableObject() {
        return this.textBox;
    }

    @Override
    public void restore() {
        this.textBox.setAnchorCoordinate(this.originalAnchorCoordinate);
    }

    @Override
    public void store() {
        this.originalAnchorCoordinate = this.textBox.getAnchorCoordinate();
    }
}
