package at.ac.univie.sketchup.model.drawable.textbox;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.DrawableObject;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

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

    @Override
    public void onTouchMove(float x, float y) {
        this.setAnchorCoordinate(new Coordinate(x, y));
    }
}
