package at.ac.univie.sketchup.model.drawable.shape;

import junit.framework.TestCase;

import org.junit.Before;

import org.junit.Test;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class CircleTest  {
    private Circle circle;
    Coordinate anchorCoordinate = new Coordinate(4,5);
    Coordinate endCoordinate = new Coordinate(5,7);
    @Before
    public void testsetUp() throws Exception {
        circle = new Circle(Color.BLUE,25);
        //circle.setColor(Color.WHITE);
    }
    @Test
    public void testtearDown() throws Exception {

           circle.setColor(Color.GREEN);
           circle.setAnchorCoordinate(anchorCoordinate);
           circle.setEndCoordinate(endCoordinate);
           circle.setInputSize(36);
           assertEquals(circle.getColor(),Color.GREEN);
           assertEquals(circle.getInputSize(),36);
           assertEquals(circle.getRadius(),2.2,7);
           System.out.println("Test Completed");
    }

}