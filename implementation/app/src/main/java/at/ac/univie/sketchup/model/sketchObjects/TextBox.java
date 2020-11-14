package at.ac.univie.sketchup.model.sketchObjects;

import at.ac.univie.sketchup.model.Color;
import at.ac.univie.sketchup.model.DrawableObject;

public class TextBox extends DrawableObject {

    private String text;

    public TextBox() {
        super(Color.BLACK,70);
        text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
