package at.ac.univie.sketchup.model.drawable.shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class TriangleTest {
 private Triangle triangle;
    Coordinate anchorCoordinate = new Coordinate(4,5);
    Coordinate endCoordinate = new Coordinate(5,7);
    @Before
    public void setUp() throws Exception {
        triangle = new Triangle(Color.BLUE,56);
    }

    @Before
    public void tearDown() throws Exception {
        triangle.setColor(Color.BLUE);
        triangle.setInputSize(78);
        triangle.setAnchorCoordinate(anchorCoordinate);
        triangle.setEndCoordinate(endCoordinate);
    }
    @Test
    public void testColor() {
        assertEquals(triangle.getColor(),Color.BLUE);
    }

    @Test
    public void testInputsize() {
        assertEquals(triangle.getInputSize(),78);
    }
    @Test
    public void testEndCoordinate() {
        assertEquals(triangle.getEndCoordinate(),endCoordinate);
    }

    @Test
    public void testAnchorCoordinate() {
        assertEquals(triangle.getAnchorCoordinate(),anchorCoordinate);
    }
}