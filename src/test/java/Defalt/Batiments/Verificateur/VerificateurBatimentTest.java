package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificateurBatimentTest {

    private VerificateurBatiment verificateur;
    private Batiment batimentValide;
    private Batiment batimentProbleme;

    @BeforeEach
    void setUp() {
        verificateur = new VerificateurBatiment();

        // Créer un bâtiment valide
        batimentValide = new Batiment("Batiment1", "usage");
        // Ajouter des étages et des pièces avec numérotation correcte
        batimentValide.getEtages().add(new Etage(1));
        batimentValide.getEtages().add(new Etage(2));
        batimentValide.getPieces().add(new Piece(10, true, 1, batimentValide.getEtages().get(0)));
        batimentValide.getPieces().add(new Piece(10, true, 2, batimentValide.getEtages().get(1)));

        // Créer un bâtiment avec des numéros d'étages ou de pièces incorrects
        batimentProbleme = new Batiment("Batiment2", "usage");
        // Ajouter des étages et des pièces avec des numérotations incorrectes
        batimentProbleme.getEtages().add(new Etage(1));
        batimentProbleme.getEtages().add(new Etage(3)); // Problème ici, manque le 2
        batimentProbleme.getPieces().add(new Piece(10, true, 1, batimentValide.getEtages().get(0)));
        batimentProbleme.getPieces().add(new Piece(10, true, 3, batimentValide.getEtages().get(0))); // Problème ici, manque le 2
    }

    @Test
    void verifBatiment_valide() {
        // Vérifier que la numérotation des étages et des pièces est correcte
        boolean isValid = verificateur.verifBatiment(batimentValide, true);
        assertTrue(isValid, "Le bâtiment devrait être valide avec des numérotations correctes.");
    }

    @Test
    void verifBatiment_problemeEtage() {
        // Vérifier que le bâtiment avec problème d'étage renvoie false
        boolean isValid = verificateur.verifBatiment(batimentProbleme, true);
        assertFalse(isValid, "Le bâtiment avec une numérotation d'étage incorrecte devrait être invalide.");
    }

    @Test
    void verifBatiment_problemePiece() {
        // Vérifier que le bâtiment avec problème de pièce renvoie false
        boolean isValid = verificateur.verifBatiment(batimentProbleme, true);
        assertFalse(isValid, "Le bâtiment avec une numérotation de pièce incorrecte devrait être invalide.");
    }

    @Test
    void getEtagesProblemes() {
        // Vérifier les étages problématiques
        verificateur.verifBatiment(batimentProbleme, true);
        List<Etage> etages = verificateur.getEtagesProblemes();
        assertEquals(1, etages.size(), "Il devrait y avoir 1 étage problématique.");
        assertEquals(3, etages.get(0).getNumero(), "L'étage problématique devrait avoir le numéro 3.");
    }

    @Test
    void getPiecesProblemes() {
        // Vérifier les pièces problématiques
        verificateur.verifBatiment(batimentProbleme, true);
        List<Piece> pieces = verificateur.getPiecesProblemes();
        assertEquals(1, pieces.size(), "Il devrait y avoir 1 pièce problématique.");
        assertEquals(3, pieces.get(0).getNumero(), "La pièce problématique devrait avoir le numéro 3.");
    }
}
