package at.ac.univie.sketchup.model.drawable.shape;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.sketchup.model.drawable.parameters.Color;
import at.ac.univie.sketchup.model.drawable.parameters.Coordinate;

import static org.junit.Assert.*;

public class PolygonTest {
   private Polygon polygon;
   private List<Coordinate> liste= new ArrayList<>();
    Coordinate coordinate1 = new Coordinate(4,5);
    Coordinate coordinate2 = new Coordinate(5,7);

    @Before
    public void setUp() throws Exception {

        polygon = new Polygon(Color.BLUE,67);
        liste.add(coordinate1);
        liste.add(coordinate2);
    }

    @Before
    public void tearDown() throws Exception {
        polygon.setCoordinates(liste);
        polygon.setAnchorCoordinate(coordinate1);
        polygon.setColor(Color.BLUE);
        polygon.setInputSize(35);
    }

    @Test
    public void testCoordinates() {
        List<Coordinate> newListe= new ArrayList<>();
        Coordinate coordinate1 = new Coordinate(4,5);
        Coordinate coordinate2 = new Coordinate(5,7);
        newListe.add(coordinate1);
        newListe.add(coordinate2);
        assertEquals(polygon.getCoordinates().size(),newListe.size());
    }

    @Test
    public void testAnchorCoordinate() {
        assertEquals(polygon.getAnchorCoordinate(),coordinate1);

    }

    @Test
    public void testColor() {
        assertEquals(polygon.getColor(),Color.BLUE);
    }

    @Test
    public void testInputsize() {
        assertEquals(polygon.getInputSize(),35);
    }
}