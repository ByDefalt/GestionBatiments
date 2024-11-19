package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class VisiteurTypePieceTest {

    private VisiteurTypePiece visiteur;
    private Batiment batiment;
    private Etage etage1;
    private Etage etage2;
    private Piece pieceBureau;
    private Piece pieceAutre;

    @BeforeEach
    void setUp() {
        visiteur = new VisiteurTypePiece();

        // Création du bâtiment mocké
        batiment = mock(Batiment.class);
        when(batiment.getNom()).thenReturn("Batiment1");

        // Création des étages mockés
        etage1 = mock(Etage.class);
        when(etage1.getNumero()).thenReturn(1);

        etage2 = mock(Etage.class);
        when(etage2.getNumero()).thenReturn(2);

        // Création des pièces mockées
        pieceBureau = mock(Piece.class);
        when(pieceBureau.getNumero()).thenReturn(1);
        when(pieceBureau.isEstBureau()).thenReturn(true);

        pieceAutre = mock(Piece.class);
        when(pieceAutre.getNumero()).thenReturn(2);
        when(pieceAutre.isEstBureau()).thenReturn(false);
    }

    @Test
    void testVisitBatiment() {
        visiteur.visit(batiment);
        // Vérification que le nom du bâtiment est bien récupéré
        verify(batiment).getNom();
    }

    @Test
    void testVisitEtage() {
        visiteur.visit(etage1);
        verify(etage1).getNumero();
        visiteur.visit(etage2);
        verify(etage2).getNumero();
    }

    @Test
    void testVisitPieceBureau() {
        visiteur.visit(pieceBureau);
        // Vérification que la pièce est bien un bureau
        verify(pieceBureau).getNumero();
        verify(pieceBureau).isEstBureau();
    }

    @Test
    void testVisitPieceAutre() {
        visiteur.visit(pieceAutre);
        // Vérification que la pièce n'est pas un bureau
        verify(pieceAutre).getNumero();
        verify(pieceAutre).isEstBureau();
    }
}
