package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampusTest{

    private Campus campus;
    private BatimentFactory mockFactory;


    @BeforeEach
    void setUp() {
        campus = new Campus();
        mockFactory = mock(BatimentFactory.class);
        BatimentFactory bf = mockFactory;
    }

    @Test
    void testCreateBatiment() {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        assertEquals(1, campus.getBatiments().size());
    }

    @Test
    void testDestroyBatiment() {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);

        assertTrue(campus.destroyBatiment("B1"));
        assertTrue(campus.getBatiments().isEmpty());

        assertFalse(campus.destroyBatiment("NonExistent"));
    }

    @Test
    void testUpdateNomBatiment() {

        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);

        assertTrue(campus.updateNomBatiment("B1", "B2"));

        assertFalse(campus.updateNomBatiment("NonExistent", "NewName"));
        assertEquals("B2", campus.getBatiments().getFirst());
    }

    @Test
    void testUpdatePieceEstBureau() {
        Batiment mockBatiment = mock(Batiment.class);
        when(mockBatiment.getNom()).thenReturn("B1");
        when(mockBatiment.getPieces()).thenReturn(new ArrayList<>());

        //campus.batiments.add(mockBatiment);

        assertFalse(campus.updatePieceEstBureau("B1", 1));
    }

    @Test
    void testAddObserverAndNotify() {
        Observer mockObserver = mock(Observer.class);

        campus.addObserver(mockObserver);
        assertEquals(1, campus.getObservers().size());

        campus.notifyObservers();
        verify(mockObserver, times(1)).update();
    }

    @Test
    void testRemoveObserver() {
        Observer mockObserver = mock(Observer.class);
        campus.addObserver(mockObserver);

        campus.removeObserver(mockObserver);
        assertTrue(campus.getObservers().isEmpty());
    }

    @Test
    void testBatimentsToJson() throws Exception {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        campus.batimentsToJson("output.json");
        File file = new File("output.json");
        assertTrue(file.exists());
    }

    @Test
    void testJsonToBatiments() throws Exception {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        campus.batimentsToJson("input.json");

        String result = campus.jsonToBatiments("input.json");

        assertTrue(result.contains("B1"));
        assertEquals(1, campus.getBatiments().size());
    }
}
