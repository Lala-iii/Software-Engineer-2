package at.ac.univie.sketchup.view.service;

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

    public DrawStrategy determineDrawableObject(DrawableObject drawableObject) throws CloneNotSupportedException {
        DrawStrategy result = null;
        if (drawableObject instanceof TextBox)
            result = new DrawTextBox(drawableObject);
        else if (drawableObject instanceof Shape)
            result = determineShape(drawableObject);
        else if (drawableObject instanceof CombinedShape)
            result = new DrawCombinedShape(drawableObject);

        // TODO throw exception
        return result;
    }

    private DrawStrategy determineShape(DrawableObject drawableObject) throws CloneNotSupportedException {
        if (drawableObject instanceof Circle)
            return new DrawCircle(drawableObject);
        else if (drawableObject instanceof Line)
            return new DrawLine(drawableObject);
        else if (drawableObject instanceof Quadrangle)
            return new DrawQuadrangle(drawableObject);
        else if (drawableObject instanceof Triangle)
            return new DrawTriangle(drawableObject);
        else if (drawableObject instanceof Polygon)
            return new DrawPolygon(drawableObject);
        return null;
    }
}
