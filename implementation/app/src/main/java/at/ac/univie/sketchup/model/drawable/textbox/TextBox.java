package at.ac.univie.sketchup.model.drawable.textbox;

import android.graphics.Canvas;
import android.graphics.Paint;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.DrawableObject;

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
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawText(
                this.getText(),
                this.getPosition().getX(),
                this.getPosition().getY(),
                paint
        );
    }
}
