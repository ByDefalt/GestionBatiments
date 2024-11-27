package Defalt.Batiments.DAO;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import Defalt.Batiments.Facade.Campus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatimentDAOTest {
    Campus campus = new Campus();
    @BeforeEach
    void setUp() {

    }

    @Test
    void serializeBatiment() throws IOException {
        List<Batiment> batiments = new ArrayList<>();
        Batiment batiment = new Batiment();
        batiment.setNom("Batiment 1");
        batiment.setUsage("Batiment de cours");
        Etage etage = new Etage(0);
        Etage etage1 = new Etage(1);
        batiment.setEtages(new ArrayList<>(List.of(etage, etage1)));
        batiment.setPieces(new ArrayList<>(List.of(new Piece(10,false,0,etage), new Piece(10,false,1,etage1))));
        batiments.add(batiment);
        BatimentDAO batimentDAO = new BatimentDAO();
        batimentDAO.serializeBatiments(batiments, "batiments.test.save");
        File file = new File("batiments.test.save");
        assertTrue(file.exists());
    }

    @Test
    void deserializeBatiment() throws IOException, ClassNotFoundException {
        BatimentDAO batimentDAO = new BatimentDAO();
        List<Batiment> batiments1 = batimentDAO.deserializeBatiments("batiments.test.save");
        assertEquals("Batiment 1", batiments1.getFirst().getNom());
        assertEquals("Batiment de cours", batiments1.getFirst().getUsage());
        assertEquals(2, batiments1.getFirst().getEtages().size());
        assertEquals(2, batiments1.getFirst().getPieces().size());
    }
}