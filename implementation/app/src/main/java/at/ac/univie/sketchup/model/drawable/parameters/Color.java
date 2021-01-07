package at.ac.univie.sketchup.model.drawable.parameters;

public enum Color {
    BLUE,
    GREEN,
    RED,
    YELLOW,
    BLACK,
    WHITE;

    public int getAndroidColor() {
        if (this == BLUE) return android.graphics.Color.BLUE;
        if (this == GREEN) return android.graphics.Color.GREEN;
        if (this == RED) return android.graphics.Color.RED;
        if (this == YELLOW) return android.graphics.Color.YELLOW;
        if (this== WHITE) return android.graphics.Color.WHITE;

        // default color Black
        return android.graphics.Color.BLACK;
    }
}
