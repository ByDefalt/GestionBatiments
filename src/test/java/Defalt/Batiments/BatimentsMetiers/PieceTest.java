package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PieceTest {

    private Piece piece;
    private Etage etage;

    @BeforeEach
    void setUp() {
        etage = new Etage(1); // Création d'un étage pour les tests
        piece = new Piece(20, true, 101, etage);
    }

    @Test
    void testConstructeurParDefaut() {
        Piece pieceVide = new Piece();
        assertEquals(0, pieceVide.getSurface(), "La surface par défaut devrait être 0.");
        assertFalse(pieceVide.isEstBureau(), "Par défaut, la pièce ne devrait pas être un bureau.");
        assertEquals(0, pieceVide.getNumero(), "Le numéro par défaut devrait être 0.");
        assertNull(pieceVide.getEtage(), "Par défaut, l'étage devrait être null.");
    }

    @Test
    void testConstructeurAvecParametres() {
        assertEquals(20, piece.getSurface(), "La surface ne correspond pas au paramètre du constructeur.");
        assertTrue(piece.isEstBureau(), "L'état bureau ne correspond pas au paramètre du constructeur.");
        assertEquals(101, piece.getNumero(), "Le numéro ne correspond pas au paramètre du constructeur.");
        assertEquals(etage, piece.getEtage(), "L'étage ne correspond pas au paramètre du constructeur.");
    }

    @Test
    void testSettersEtGetters() {
        piece.setSurface(30);
        assertEquals(30, piece.getSurface(), "La surface de la pièce ne correspond pas après modification.");

        piece.setEstBureau(false);
        assertFalse(piece.isEstBureau(), "L'état bureau de la pièce ne correspond pas après modification.");

        piece.setNumero(102);
        assertEquals(102, piece.getNumero(), "Le numéro de la pièce ne correspond pas après modification.");

        Etage nouvelEtage = new Etage(2);
        piece.setEtage(nouvelEtage);
        assertEquals(nouvelEtage, piece.getEtage(), "L'étage de la pièce ne correspond pas après modification.");
    }

    @Test
    void testEquals() {
        Piece pieceIdentique = new Piece(25, false, 101, etage);
        Piece pieceDifferente = new Piece(25, true, 102, etage);

        assertTrue(piece.equals(pieceIdentique), "Les pièces avec le même numéro devraient être égales.");
        assertFalse(piece.equals(pieceDifferente), "Les pièces avec des numéros différents ne devraient pas être égales.");
        assertFalse(piece.equals(null), "Une pièce ne devrait pas être égale à null.");
        assertFalse(piece.equals("String"), "Une pièce ne devrait pas être égale à un objet d'une autre classe.");
    }

    @Test
    void testToString() {
        String attendu = "Piece{surface=20, estBureau=true, numero=101, etage=Etage{numero=1}}";
        assertEquals(attendu, piece.toString(), "La méthode toString ne retourne pas la valeur attendue.");
    }

    @Test
    void testAcceptVisiteur() {
        // Création d'un mock de visiteur
        Visiteur visiteurMock = mock(Visiteur.class);
        TreeItem<String> treeItemMock = new TreeItem<>("Piece 101");
        when(visiteurMock.visit(piece)).thenReturn(treeItemMock);

        // Appel de la méthode accept
        TreeItem<String> resultat = piece.accept(visiteurMock);

        // Vérification
        assertEquals(treeItemMock, resultat, "La méthode accept n'a pas retourné la valeur attendue.");
        verify(visiteurMock, times(1)).visit(piece);
    }
}
