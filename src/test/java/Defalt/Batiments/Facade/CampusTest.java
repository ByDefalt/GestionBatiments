package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.DAO.BatimentDAO;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Visiteur.VisiteurTypePiece;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
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
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);

        assertTrue(campus.updatePieceEstBureau("B1", 1));
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
    @Test
    void testJsonToBatimentsFail() throws Exception {

        String result = campus.jsonToBatiments("test_batiments_mauvais.json");
        assertFalse(result.isEmpty());
        assertEquals(1, campus.getBatiments().size());
    }

    @Test
    void afficherDetailsBatiment() {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        assertEquals(new TreeItem<>("Bâtiment : B1").getValue(), campus.afficherDetailsBatiment("B1",new VisiteurTypePiece()).getValue());
    }
    @Test
    public void serializeBatiments() throws IOException {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        campus.createBatiment("B2", "Usage2", 50, true, 10, 2, 5);
        campus.createBatiment("B3", "Usage3", 50, true, 10, 2, 5);
        campus.serializeBatiments("test_serialize.save");
        File file = new File("test_serialize.save");
        assertTrue(file.exists());
    }
    @Test
    public void deserializeBatiments() throws IOException, ClassNotFoundException {
        campus.createBatiment("B1", "Usage1", 50, true, 10, 2, 5);
        campus.createBatiment("B2", "Usage2", 50, true, 10, 2, 5);
        campus.createBatiment("B3", "Usage3", 50, true, 10, 2, 5);
        campus.serializeBatiments("test_serialize.save");
        File file = new File("test_serialize.save");
        assertTrue(file.exists());
        String pb=campus.deserializeBatiments("test_serialize.save");
        assertEquals("Le batiment 'B1' a les problèmes suivants :\n" +
                "    Le nom batiment existe déja\n" +
                "Le batiment 'B2' a les problèmes suivants :\n" +
                "    Le nom batiment existe déja\n" +
                "Le batiment 'B3' a les problèmes suivants :\n" +
                "    Le nom batiment existe déja\n",pb);
        campus.destroyBatiment("B1");
        campus.destroyBatiment("B2");
        campus.destroyBatiment("B3");
        String pb2=campus.deserializeBatiments("test_serialize.save");
        assertEquals("",pb2);
        assertEquals(3, campus.getBatiments().size());
    }
}
