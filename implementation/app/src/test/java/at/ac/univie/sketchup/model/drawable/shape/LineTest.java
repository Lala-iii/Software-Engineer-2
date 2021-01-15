package at.ac.univie.sketchup.model.drawable.shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class LineTest {
  private  Line line;
  Coordinate anchorCoordinate = new Coordinate(4,5);
  Coordinate endCoordinate = new Coordinate(5,7);
    @Before
    public void setUp() throws Exception {
        line = new Line(Color.BLUE,46);
    }

    @Before
    public void tearDown() throws Exception {
        line.setAnchorCoordinate(anchorCoordinate);
        line.setEndCoordinate(endCoordinate);

    }

    @Test
    public void testEndCoordinate() {
        assertEquals(line.getEndCoordinate(),endCoordinate);
    }

    @Test
    public void testAnchorCoordinate() {
        assertEquals(line.getAnchorCoordinate(),anchorCoordinate);
    }
}