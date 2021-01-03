package at.ac.univie.sketchup.view.service;

import android.graphics.Canvas;

import at.ac.univie.sketchup.model.drawable.CombinedShape;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.shape.Circle;
import at.ac.univie.sketchup.model.drawable.shape.Line;
import at.ac.univie.sketchup.model.drawable.shape.Polygon;
import at.ac.univie.sketchup.model.drawable.shape.Quadrangle;
import at.ac.univie.sketchup.model.drawable.shape.Shape;
import at.ac.univie.sketchup.model.drawable.shape.Triangle;
import at.ac.univie.sketchup.model.drawable.textbox.TextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawCombinedShape;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawStrategy;
import at.ac.univie.sketchup.view.service.drawstrategy.DrawTextBox;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawCircle;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawLine;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawPolygon;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawQuadrangle;
import at.ac.univie.sketchup.view.service.drawstrategy.shape.DrawTriangle;

public class DrawService {


    public void handle(Canvas canvas, DrawableObject objectToDraw) {
        DrawStrategy drawStrategy = null;
        if (objectToDraw instanceof TextBox)
            drawStrategy = new DrawTextBox();
        else if (objectToDraw instanceof Shape)
            drawStrategy = handleShape(objectToDraw);
        else if (objectToDraw instanceof CombinedShape)
            drawStrategy = new DrawCombinedShape();

        if (drawStrategy != null) {
            drawStrategy.drawObject(objectToDraw, canvas);
        } else {
            //todo custom exception DrawableObjectNotDefined
        }
    }

    private DrawStrategy handleShape(DrawableObject objectToDraw) {
        if (objectToDraw instanceof Circle)
            return new DrawCircle();
        else if (objectToDraw instanceof Line)
            return new DrawLine();
        else if (objectToDraw instanceof Quadrangle)
            return new DrawQuadrangle();
        else if (objectToDraw instanceof Triangle)
            return new DrawTriangle();
        else if (objectToDraw instanceof Polygon)
            return new DrawPolygon();
        return null;
    }
}
