package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test JUnit pour la classe {@link Etage}.
 */
public class EtageTest {

    private Etage etage;

    @BeforeEach
    public void setUp() {
        // Initialisation d'un nouvel étage avant chaque test
        etage = new Etage(1);
    }

    /**
     * Teste la méthode {@link Etage#getNumero()} et {@link Etage#setNumero(int)}.
     */
    @Test
    public void testGetSetNumero() {
        // Vérification du numéro initial
        assertEquals(1, etage.getNumero());

        // Modification du numéro
        etage.setNumero(2);
        assertEquals(2, etage.getNumero());
    }

    /**
     * Teste la méthode {@link Etage#accept(Visiteur)}.
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
                assertEquals(1, etage.getNumero());
            }

            @Override
            public void visit(Piece piece) {
                // Ne fait rien
            }
        };

        // Test de l'acceptation du visiteur sur l'étage
        etage.accept(visiteur);
    }

    /**
     * Teste la méthode {@link Etage#equals(Object)}.
     */
    @Test
    public void testEquals() {
        Etage etage2 = new Etage(1);
        assertEquals(etage, etage2);

        Etage etage3 = new Etage(2);
        assertNotEquals(etage, etage3);
    }

    /**
     * Teste la méthode {@link Etage#hashCode()}.
     */
    @Test
    public void testHashCode() {
        Etage etage2 = new Etage(1);
        assertEquals(etage.hashCode(), etage2.hashCode());
    }

    /**
     * Teste la méthode {@link Etage#toString()}.
     */
    @Test
    public void testToString() {
        String expected = "Etage{numero=1}";
        assertEquals(expected, etage.toString());
    }
}

