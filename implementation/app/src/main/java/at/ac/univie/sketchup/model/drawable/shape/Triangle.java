package at.ac.univie.sketchup.model.drawable.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Triangle extends Shape {
    private float width;

    public Triangle(){
        super(Color.BLACK,5);
        width = 70;
    }

    public float getWidth() {
        return this.width;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float width =  this.getWidth();

        Path path = new Path();
        path.moveTo(this.getPosition().getX(), this.getPosition().getY() - width); // Top
        path.lineTo(this.getPosition().getX() - width, this.getPosition().getY() + width); // Bottom left
        path.lineTo(this.getPosition().getX() + width, this.getPosition().getY() + width); // Bottom right
        path.lineTo(this.getPosition().getX(), this.getPosition().getY() - width); // Back to top
        path.close();

        canvas.drawPath(path, paint);
    }

    public void setWidth(float width) {
        this.width = width;
    }

}
