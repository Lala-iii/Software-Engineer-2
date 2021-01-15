package at.ac.univie.sketchup.view.service.drawstrategy;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public interface DrawStrategy {

    /**
     * Draws the DrawableObject of the DrawStrategy to the given Canvas
     * @param canvas Canvas in which the DrawableObject is drawn
     * @return returns true if the drawing was successful
     */
    boolean drawObject(Canvas canvas);

    /**
     * Sets the Paint of the DrawStrategy
     * @return Returns the Paint of the DrawStrategy
     */
    Paint setPaint();

    /**
     * Checks if the DrawStrategy is in the selected area
     * @param begin Begin Coordinate of the selector
     * @param end End Coordinate of the selector
     * @return
     */
    boolean inSelectedArea(Coordinate begin, Coordinate end);

    /**
     * First TouchDown of the created DrawStrategy
     * @param x X-Coordinate of Touch Down
     * @param y Y-Coordinate of Touch Down
     */
    void onTouchDown(float x, float y);

    /**
     * Moving the created DrawStrategy
     * @param x X-Coordinate of Touch Move
     * @param y Y-Coordinate of Touch Move
     */
    void onTouchMove(float x, float y);

    /**
     * First TouchDown of the selected DrawStrategies
     * @param x X-Coordinate of Touch Down
     * @param y Y-Coordinate of Touch Down
     */
    void onEditDown(float x, float y);

    /**
     * Moving the selected DrawStrategies
     * @param x X-Coordinate of Touch Move
     * @param y Y-Coordinate of Touch Move
     */
    void onEditMove(float x, float y);

    /**
     * Returns it's DrawableObject stored
     * @return DrawableObject
     */
    DrawableObject getDrawableObject();

    /**
     * Restores to the original position when user cancels the editing
     */
    void restore();

    /**
     * Stores the current position of the DrawableObject,
     * if user cancels editing it will move it back to the original stored position
     */
    void store();
}
