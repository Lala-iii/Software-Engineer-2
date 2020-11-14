package at.ac.univie.sketchup.model;

public enum Color {
    BLUE,
    GREEN,
    RED,
    YELLOW,
    BLACK;

    public int getAndroidColor() {
        if (this == BLUE) return android.graphics.Color.BLUE;
        if (this == GREEN) return android.graphics.Color.GREEN;
        if (this == RED) return android.graphics.Color.RED;
        if (this == YELLOW) return android.graphics.Color.YELLOW;

        // default color Black
        return android.graphics.Color.BLACK;
    }
}
