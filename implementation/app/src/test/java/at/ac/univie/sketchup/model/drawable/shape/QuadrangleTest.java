package at.ac.univie.sketchup.model.drawable.shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class QuadrangleTest {
    Quadrangle quadrangle;
    Coordinate anchorCoordinate = new Coordinate(4,5);
    Coordinate endCoordinate = new Coordinate(5,7);
    @Before
    public void setUp() throws Exception {
    quadrangle = new Quadrangle(Color.BLUE,56);
    }

    @Before
    public void tearDown() throws Exception {
        quadrangle.setColor(Color.BLUE);
        quadrangle.setInputSize(99);
        quadrangle.setAnchorCoordinate(anchorCoordinate);
        quadrangle.setEndCoordinate(endCoordinate);
    }
    @Test
    public void testColor() {
        assertEquals(quadrangle.getColor(),Color.BLUE);
    }

    @Test
    public void testInputsize() {
        assertEquals(quadrangle.getInputSize(),99);
    }
    @Test
    public void testEndCoordinate() {
        assertEquals(quadrangle.getEndCoordinate(),endCoordinate);
    }

    @Test
    public void testAnchorCoordinate() {
        assertEquals(quadrangle.getAnchorCoordinate(),anchorCoordinate);
    }
}