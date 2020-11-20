package at.ac.univie.sketchup.model.drawable;

import androidx.annotation.NonNull;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

public abstract class DrawableObject implements Cloneable {

    private Color color;
    private Coordinate position;
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

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
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
}
