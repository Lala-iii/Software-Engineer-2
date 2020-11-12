package at.ac.univie.sketchup.model;

import java.util.ArrayList;

import at.ac.univie.sketchup.model.sketchObjects.TextBox;

public class Sketch {

    private int id;
    private String title;
    private ArrayList<TextBox> textBoxes = new ArrayList<>();
    private String currentText = "";

    public ArrayList<TextBox> getTextBoxes() {
        return textBoxes;
    }

    public void setTextBoxes(ArrayList<TextBox> textBoxes) {
        this.textBoxes = textBoxes;
    }

    public String getCurrentText() {
        return currentText;
    }

    public void setCurrentText(String currentText) {
        this.currentText = currentText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public void addTextBox(TextBox textBox) {
        textBoxes.add(textBox);
    }
}
