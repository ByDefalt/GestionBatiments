package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la classe {@link Batiment}.
 */
public class BatimentTest {

    private Batiment batiment;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un nouveau bâtiment avant chaque test
        batiment = new Batiment("Bâtiment A", "Commercial");
    }

    /**
     * Teste la méthode {@link Batiment#getNom()} et {@link Batiment#setNom(String)}.
     */
    @Test
    public void testGetSetNom() {
        // Vérification du nom initial
        assertEquals("Bâtiment A", batiment.getNom());

        // Modification du nom
        batiment.setNom("Bâtiment B");
        assertEquals("Bâtiment B", batiment.getNom());
    }

    /**
     * Teste la méthode {@link Batiment#getUsage()} et {@link Batiment#setUsage(String)}.
     */
    @Test
    public void testGetSetUsage() {
        // Vérification de l'usage initial
        assertEquals("Commercial", batiment.getUsage());

        // Modification de l'usage
        batiment.setUsage("Résidentiel");
        assertEquals("Résidentiel", batiment.getUsage());
    }

    /**
     * Teste la méthode {@link Batiment#getNumero()} pour vérifier que le numéro est bien auto-incrémenté.
     */
    @Test
    public void testGetNumero() {
        Batiment batiment2 = new Batiment("Bâtiment B", "Commercial");
        assertEquals(3, batiment.getNumero());
        assertEquals(4, batiment2.getNumero());
    }

    /**
     * Teste la méthode {@link Batiment#getEtages()} et {@link Batiment#setEtages(List)}.
     */
    @Test
    public void testGetSetEtages() {
        List<Etage> etages = new ArrayList<>();
        Etage etage1 = new Etage(1);
        Etage etage2 = new Etage(2);
        etages.add(etage1);
        etages.add(etage2);

        batiment.setEtages(etages);

        assertEquals(2, batiment.getEtages().size());
        assertTrue(batiment.getEtages().contains(etage1));
        assertTrue(batiment.getEtages().contains(etage2));
    }

    /**
     * Teste la méthode {@link Batiment#getPieces()} et {@link Batiment#setPieces(List)}.
     */
    @Test
    public void testGetSetPieces() {
        List<Piece> pieces = new ArrayList<>();
        Piece piece1 = new Piece(1, true, 10,null);
        Piece piece2 = new Piece(2,true, 15,null);
        pieces.add(piece1);
        pieces.add(piece2);

        batiment.setPieces(pieces);

        assertEquals(2, batiment.getPieces().size());
        assertTrue(batiment.getPieces().contains(piece1));
        assertTrue(batiment.getPieces().contains(piece2));
    }

    /**
     * Teste la méthode {@link Batiment#accept(Visiteur)}.
     */
    @Test
    public void testAcceptVisiteur() {
        Visiteur visiteur = new Visiteur() {
            @Override
            public void visit(Batiment batiment) {
                assertEquals("Bâtiment A", batiment.getNom());
            }

            @Override
            public void visit(Etage etage) {
                // Test pour les étages
                assertNotNull(etage);
            }

            @Override
            public void visit(Piece piece) {
                // Test pour les pièces
                assertNotNull(piece);
            }
        };

        // Test de l'acceptation du visiteur
        batiment.accept(visiteur);
    }
}

