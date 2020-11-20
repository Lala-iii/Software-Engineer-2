package at.ac.univie.sketchup.view.service;

import android.graphics.Canvas;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Shape;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawTextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawCircle;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawLine;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawQuadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawTriangle;

public class DrawService {

    private DrawStrategy drawStrategy;

    public void handle(Canvas canvas, DrawableObject objectToDraw) {

        if (objectToDraw instanceof TextBox) {
            drawStrategy = new DrawTextBox();
        }
        if (objectToDraw instanceof Shape) {
            handleShape(objectToDraw);
        }

        if (null != drawStrategy) {
            drawStrategy.drawObject(objectToDraw, canvas);
        } else {
            //todo custom exception DrawableObjectNotDefined
        }
    }

    private void handleShape(DrawableObject objectToDraw) {
        if (objectToDraw instanceof Circle) {
            drawStrategy = new DrawCircle();
        }
        if (objectToDraw instanceof Line) {
            drawStrategy = new DrawLine();
        }
        if (objectToDraw instanceof Quadrangle) {
            drawStrategy = new DrawQuadrangle();
        }
        if (objectToDraw instanceof Triangle) {
            drawStrategy = new DrawTriangle();
        }
    };
}
