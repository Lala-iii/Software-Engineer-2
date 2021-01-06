package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import java.io.Serializable;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class DrawableObject implements Cloneable, Serializable {

    private Color color;
    private Coordinate anchorCoordinate;
    private int inputSize;   // Brush, Text or Stroke size

    public DrawableObject(Color c, int size) {
        color = c;
        inputSize = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Coordinate getAnchorCoordinate() {
        return anchorCoordinate;
    }

    public void setAnchorCoordinate(Coordinate anchorCoordinate) {
        this.anchorCoordinate = anchorCoordinate;
    }

    public int getInputSize() {
        return inputSize;
    }

    public void setInputSize(int inputSize) {
        this.inputSize = inputSize;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract void onTouchMove(float x, float y);

    public void onTouchDown(float x, float y) {
        this.anchorCoordinate = new Coordinate(x, y);
    }
}
