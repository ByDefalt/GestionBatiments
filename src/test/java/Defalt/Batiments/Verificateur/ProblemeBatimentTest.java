package Defalt.Batiments.Verificateur;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProblemeBatimentTest {

    @Test
    void testWithDetails() {
        // Création de listes pour les tests
        List<Integer> etagesTest = Arrays.asList(1, 2, 3);
        List<Integer> piecesTest = Arrays.asList(101, 102);

        // Utilisation de withDetails
        ProblemeBatiment probleme = ProblemeBatiment.ETAGES.withDetails(etagesTest, piecesTest);

        // Vérifications
        assertEquals(etagesTest, probleme.getEtagesProblemes(), "Les étages ne correspondent pas.");
        assertEquals(piecesTest, probleme.getPiecesProblemes(), "Les pièces ne correspondent pas.");
    }

    @Test
    void testGetEtagesProblemesWithoutSetting() {
        // Vérifie que par défaut les étages sont null
        assertNull(ProblemeBatiment.NOM.getEtagesProblemes(), "Les étages doivent être null.");
    }

    @Test
    void testGetPiecesProblemesWithoutSetting() {
        // Vérifie que par défaut les pièces sont null
        assertNull(ProblemeBatiment.NOM.getPiecesProblemes(), "Les pièces doivent être null.");
    }

    @Test
    void testMultipleInstancesIndependence() {
        // Création de deux instances avec des détails différents
        List<Integer> etagesA = Arrays.asList(1, 2);
        List<Integer> piecesA = Arrays.asList(101);
        ProblemeBatiment problemeA = ProblemeBatiment.ETAGES.withDetails(etagesA, piecesA);

        List<Integer> etagesB = Arrays.asList(3, 4);
        List<Integer> piecesB = Arrays.asList(201, 202);
        ProblemeBatiment problemeB = ProblemeBatiment.PIECES.withDetails(etagesB, piecesB);

        // Vérifie que chaque instance conserve ses propres détails
        assertEquals(etagesA, problemeA.getEtagesProblemes(), "Les étages de problème A ne correspondent pas.");
        assertEquals(piecesA, problemeA.getPiecesProblemes(), "Les pièces de problème A ne correspondent pas.");

        assertEquals(etagesB, problemeB.getEtagesProblemes(), "Les étages de problème B ne correspondent pas.");
        assertEquals(piecesB, problemeB.getPiecesProblemes(), "Les pièces de problème B ne correspondent pas.");
    }

    @Test
    void testEnumDefaultValues() {
        // Vérifie que les valeurs par défaut des champs sont null pour chaque élément de l'énumération
        for (ProblemeBatiment probleme : ProblemeBatiment.values()) {
            assertNull(probleme.getEtagesProblemes(), "Les étages doivent être null pour " + probleme.name());
            assertNull(probleme.getPiecesProblemes(), "Les pièces doivent être null pour " + probleme.name());
        }
    }
}
