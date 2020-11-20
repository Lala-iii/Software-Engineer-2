package at.ac.univie.sketchup.model.drawable.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Quadrangle extends Shape {
    private Coordinate endPoint;

    public Quadrangle(){
        super(Color.BLACK,5);
    }

    public Coordinate getEndPoint() {
        endPoint = new Coordinate(
                super.getPosition().getX() + 300,
                super.getPosition().getY() + 300
        );
        return endPoint;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Rect rect = new Rect(
                (int) this.getPosition().getX(),
                (int) this.getPosition().getY(),
                (int) this.getEndPoint().getX(),
                (int) this.getEndPoint().getY()
        );
        canvas.drawRect(
                rect,
                paint
        );
    }
    public void setEndPoint(Coordinate endPoint) {
        this.endPoint = endPoint;
    }
}
