package at.ac.univie.sketchup.model.drawable.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public class Line extends Shape{

    private Coordinate endPoint;

    public Line(){
        super(Color.BLACK,6);
    }

    public Coordinate getEndPoint() {
        endPoint = new Coordinate(
                super.getPosition().getX() + 300,
                super.getPosition().getY() + 300
        );
        return endPoint;
    }
    public void draw(Canvas canvas, Paint paint) {
        System.out.println("Drawing the Line");
        canvas.drawLine(
                this.getPosition().getX(),
                this.getPosition().getY(),
                this.getEndPoint().getX(),
                this.getEndPoint().getY(),
                paint
        );
    }
    public void setEndPoint(Coordinate endPoint) {
        this.endPoint = endPoint;
    }

}
