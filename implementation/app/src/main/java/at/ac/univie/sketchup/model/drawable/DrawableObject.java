package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class DrawableObject implements Cloneable {

    private Color color;
    private Coordinate anchorCoordinate;
    private int inputSize;   // Brush, Text or Stroke size



    private boolean selected;

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
