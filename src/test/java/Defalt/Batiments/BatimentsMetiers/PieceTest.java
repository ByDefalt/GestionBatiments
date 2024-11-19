package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la classe {@link Piece}.
 */
public class PieceTest {

    private Piece piece;
    private Etage etage;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un étage pour tester la pièce
        etage = new Etage(1);
        // Initialisation d'une nouvelle pièce avant chaque test
        piece = new Piece(20, true, 101, etage);
    }

    /**
     * Teste la méthode {@link Piece#getSurface()} et {@link Piece#setSurface(int)}.
     */
    @Test
    public void testGetSetSurface() {
        // Vérification de la surface initiale
        assertEquals(20, piece.getSurface());

        // Modification de la surface
        piece.setSurface(30);
        assertEquals(30, piece.getSurface());
    }

    /**
     * Teste la méthode {@link Piece#isEstBureau()} et {@link Piece#setEstBureau(boolean)}.
     */
    @Test
    public void testIsSetEstBureau() {
        // Vérification de l'état initial (estBureau)
        assertTrue(piece.isEstBureau());

        // Modification de l'état (estBureau)
        piece.setEstBureau(false);
        assertFalse(piece.isEstBureau());
    }

    /**
     * Teste la méthode {@link Piece#getNumero()} et {@link Piece#setNumero(int)}.
     */
    @Test
    public void testGetSetNumero() {
        // Vérification du numéro initial
        assertEquals(101, piece.getNumero());

        // Modification du numéro
        piece.setNumero(102);
        assertEquals(102, piece.getNumero());
    }

    /**
     * Teste la méthode {@link Piece#getEtage()} et {@link Piece#setEtage(Etage)}.
     */
    @Test
    public void testGetSetEtage() {
        // Vérification de l'étage initial
        assertEquals(etage, piece.getEtage());

        // Création d'un nouvel étage pour tester
        Etage etage2 = new Etage(2);
        piece.setEtage(etage2);
        assertEquals(etage2, piece.getEtage());
    }

    /**
     * Teste la méthode {@link Piece#accept(Visiteur)}.
     */
    @Test
    public void testAcceptVisiteur() {
        Visiteur visiteur = new Visiteur() {
            @Override
            public void visit(Batiment batiment) {
                // Ne fait rien
            }

            @Override
            public void visit(Etage etage) {
                // Ne fait rien
            }

            @Override
            public void visit(Piece piece) {
                assertEquals(20, piece.getSurface());
                assertTrue(piece.isEstBureau());
                assertEquals(101, piece.getNumero());
                assertEquals(etage, piece.getEtage());
            }
        };

        // Test de l'acceptation du visiteur sur la pièce
        piece.accept(visiteur);
    }

    /**
     * Teste la méthode {@link Piece#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Etage etage2 = new Etage(1);
        Piece piece2 = new Piece(20, true, 101, etage2);
        assertEquals(piece, piece2);

        Piece piece3 = new Piece(30, false, 102, etage2);
        assertNotEquals(piece, piece3);
    }

    /**
     * Teste la méthode {@link Piece#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Etage etage2 = new Etage(1);
        Piece piece2 = new Piece(20, true, 101, etage2);
        assertEquals(piece.hashCode(), piece2.hashCode());
    }

    /**
     * Teste la méthode {@link Piece#toString()}.
     */
    @Test
    public void testToString() {
        String expected = "Piece{surface=20, estBureau=true, numero=101, etage=Etage{numero=1}}";
        assertEquals(expected, piece.toString());
    }
}
