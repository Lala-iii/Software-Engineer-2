package at.ac.univie.sketchup.model.drawable.textbox;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class TextBoxTest {
   private TextBox textBox;
   private Coordinate coordinate = new Coordinate(4,10);
    @Before
    public void setUp() throws Exception {
        textBox = new TextBox(Color.BLUE,23,"First text");
    }

    @Before
    public void tearDown() throws Exception {
        textBox.setText("Changed text");
        textBox.setColor(Color.BLACK);
        textBox.setInputSize(56);
        textBox.setAnchorCoordinate(coordinate);
    }
    @Test
    public void tesText() { assertEquals(textBox.getText(),"Changed text");}
    @Test
    public void testColor() {
        assertEquals(textBox.getColor(),Color.BLACK);
    }

    @Test
    public void testInputsize() {
        assertEquals(textBox.getInputSize(),56);
    }


    @Test
    public void testAnchorCoordinate() {
        assertEquals(textBox.getAnchorCoordinate(),coordinate);
    }

}