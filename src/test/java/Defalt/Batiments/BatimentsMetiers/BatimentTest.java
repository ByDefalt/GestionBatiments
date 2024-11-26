package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BatimentTest {

    private Batiment batiment;

    @BeforeEach
    void setUp() {
        batiment = new Batiment("Bâtiment A", "Résidentiel");
    }

    @Test
    void testConstructeurParDefaut() {
        Batiment batimentVide = new Batiment();
        assertNull(batimentVide.getNom(), "Le nom par défaut doit être null.");
        assertNull(batimentVide.getUsage(), "L'usage par défaut doit être null.");
        assertNull(batimentVide.getEtages(), "Les étages par défaut doivent être null.");
        assertNull(batimentVide.getPieces(), "Les pièces par défaut doivent être null.");
    }

    @Test
    void testConstructeurAvecParametres() {
        assertEquals("Bâtiment A", batiment.getNom(), "Le nom ne correspond pas.");
        assertEquals("Résidentiel", batiment.getUsage(), "L'usage ne correspond pas.");
        assertNotNull(batiment.getEtages(), "La liste des étages doit être initialisée.");
        assertNotNull(batiment.getPieces(), "La liste des pièces doit être initialisée.");
    }

    @Test
    void testSettersEtGetters() {
        batiment.setNom("Bâtiment B");
        batiment.setUsage("Commercial");

        List<Etage> etages = new ArrayList<>();
        batiment.setEtages(etages);

        List<Piece> pieces = new ArrayList<>();
        batiment.setPieces(pieces);

        assertEquals("Bâtiment B", batiment.getNom(), "Le nom ne correspond pas après modification.");
        assertEquals("Commercial", batiment.getUsage(), "L'usage ne correspond pas après modification.");
        assertEquals(etages, batiment.getEtages(), "Les étages ne correspondent pas après modification.");
        assertEquals(pieces, batiment.getPieces(), "Les pièces ne correspondent pas après modification.");
    }

    @Test
    void testEquals() {
        Batiment batimentIdentique = new Batiment("Bâtiment A", "Autre usage");
        Batiment batimentDifferent = new Batiment("Bâtiment C", "Résidentiel");

        assertTrue(batiment.equals(batimentIdentique), "Les bâtiments identiques devraient être égaux.");
        assertFalse(batiment.equals(batimentDifferent), "Les bâtiments différents ne devraient pas être égaux.");
    }

    @Test
    void testToString() {
        String attendu = "Batiment{, nom='Bâtiment A', usage='Résidentiel', etages=[], pieces=[]}";
        assertEquals(attendu, batiment.toString(), "La méthode toString ne retourne pas la valeur attendue.");
    }

    @Test
    void testAcceptVisiteur() {
        Visiteur visiteurMock = mock(Visiteur.class);
        TreeItem<String> treeItemMock = new TreeItem<>("Bâtiment A");
        when(visiteurMock.visit(batiment)).thenReturn(treeItemMock);

        TreeItem<String> resultat = batiment.accept(visiteurMock);

        assertEquals(treeItemMock, resultat, "La méthode accept n'a pas retourné la valeur attendue.");
    }

    @Test
    void testGestionEtagesEtPieces() {
        Etage etage1 = new Etage(1);
        Etage etage2 = new Etage(2);
        Piece piece1 = new Piece(10,true,1,etage1);
        Piece piece2 = new Piece(10,true,2,etage2);

        batiment.setEtages(Arrays.asList(etage1, etage2));
        batiment.setPieces(Arrays.asList(piece1, piece2));

        assertEquals(2, batiment.getEtages().size(), "La taille des étages est incorrecte.");
        assertEquals(2, batiment.getPieces().size(), "La taille des pièces est incorrecte.");
        assertTrue(batiment.getEtages().contains(etage1), "L'étage 1 devrait être présent.");
        assertTrue(batiment.getPieces().contains(piece1), "La pièce 1 devrait être présente.");
    }
}
