package at.ac.univie.sketchup.model.drawable.parameters;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CoordinateTest {
    private Coordinate coordinate;

    @Before
    public void setUp() throws Exception {
      coordinate = new Coordinate(77,89);
    }

    @Before
    public void tearDown() throws Exception {
        coordinate.setX(45);
        coordinate.setY(45);

    }

    @Test
    public void testX() {
        assertEquals(coordinate.getX(),45,45);

    }

    @Test
    public void testY() {
        assertEquals(coordinate.getY(),45,45);

    }
}