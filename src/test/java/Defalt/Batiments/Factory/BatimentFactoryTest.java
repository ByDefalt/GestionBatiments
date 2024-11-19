package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Piece;
import Defalt.Batiments.BatimentsMetiers.Etage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class BatimentFactoryTest {

    private BatimentFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new BatimentFactory();
    }

    @Test
    public void testCreateBatimentWithValidParams() {
        // Créer un bâtiment avec des paramètres valides
        Batiment batiment = factory.createBatiment("Batiment A", "Commercial", 20, true, 5, 3, 6);

        // Vérifier que le bâtiment est créé avec les bonnes informations
        assertNotNull(batiment);
        assertEquals("Batiment A", batiment.getNom());
        assertEquals("Commercial", batiment.getUsage());
        assertEquals(3, batiment.getEtages().size()); // 3 étages attendus
        assertEquals(6, batiment.getPieces().size()); // 6 pièces attendues

        // Vérifier les pièces
        List<Piece> pieces = batiment.getPieces();
        for (int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            assertTrue(piece.getSurface() > 0);
            assertTrue(piece.getNumero() > 0);
            assertNotNull(piece.getEtage());
        }
    }

    @Test
    public void testCreateBatimentWithInvalidParams() {
        // Créer un bâtiment avec des paramètres invalides
        Batiment batiment = factory.createBatiment("Batiment B", "Commercial", 0, true, 3, -1, -2);

        // Vérifier que la création échoue
        assertNull(batiment);
    }

    @Test
    public void testCreateBatimentWithInvalidSurface() {
        // Créer un bâtiment avec une surface de pièce invalide
        Batiment batiment = factory.createBatiment("Batiment C", "Commercial", -5, true, 3, 3, 6);

        // Vérifier que la création échoue
        assertNull(batiment);
    }

    @Test
    public void testFactoryIdIncrement() {
        // Vérifier que l'id de l'usine s'incrémente correctement
        BatimentFactory anotherFactory = new BatimentFactory();

        // Vérifier que l'usine précédente a un id différent
        assertNotEquals(factory.getNum(), anotherFactory.getNum());
    }

    @Test
    public void testEqualsAndHashCode() {
        BatimentFactory factory1 = new BatimentFactory();
        BatimentFactory factory2 = new BatimentFactory();

        // Vérifier l'égalité
        assertNotEquals(factory1, factory2);
        assertEquals(factory1.hashCode(), factory1.hashCode());
        assertNotEquals(factory1.hashCode(), factory2.hashCode());
    }

    @Test
    public void testCreateBatimentWithStartOne() {
        // Créer un bâtiment avec startOne = true
        Batiment batiment = factory.createBatiment("Batiment D", "Commercial", 15, true, 2, 3, 6);

        // Vérifier que les numéros de pièce et d'étage commencent bien à 1
        assertEquals(1, batiment.getPieces().get(0).getNumero());
        assertEquals(1, batiment.getEtages().get(0).getNumero());
    }

    @Test
    public void testCreateBatimentWithStartZero() {
        // Créer un bâtiment avec startOne = false
        Batiment batiment = factory.createBatiment("Batiment E", "Commercial", 10, false, 1, 2, 4);

        // Vérifier que les numéros de pièce et d'étage commencent bien à 0
        assertEquals(0, batiment.getPieces().get(0).getNumero());
        assertEquals(0, batiment.getEtages().get(0).getNumero());
    }
}
