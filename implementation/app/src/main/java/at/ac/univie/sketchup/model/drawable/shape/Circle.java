package at.ac.univie.sketchup.model.drawable.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.parameters.Color;

public class Circle extends Shape {

    private float radius;

    public Circle(){
        super(Color.BLACK,5);
        radius = 70;
    }

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawCircle(
                this.getPosition().getX(),
                this.getPosition().getY(),
                this.getRadius(),
                paint
        );
    }
}
