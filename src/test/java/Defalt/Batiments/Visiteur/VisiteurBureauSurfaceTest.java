package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

class VisiteurBureauSurfaceTest {

    private VisiteurBureauSurface visiteur;
    private Batiment batiment;
    private Etage etage1;
    private Etage etage2;
    private Piece pieceBureau;
    private Piece pieceNonBureau;

    @BeforeEach
    void setUp() {
        visiteur = new VisiteurBureauSurface();

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
        when(pieceBureau.getSurface()).thenReturn(25);

        pieceNonBureau = mock(Piece.class);
        when(pieceNonBureau.getNumero()).thenReturn(2);
        when(pieceNonBureau.isEstBureau()).thenReturn(false);
    }

    @Test
    void testVisitBatiment() {
        visiteur.visit(batiment);
        // Vérification que le nom du bâtiment est bien affiché
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
        // Vérification que la pièce est bien un bureau et que sa surface est correcte
        verify(pieceBureau).getNumero();
        verify(pieceBureau).getSurface();
    }

    @Test
    void testVisitPieceNonBureau() {
        visiteur.visit(pieceNonBureau);
        // Vérification que la pièce n'est pas un bureau, donc pas d'affichage pour cette pièce
        verify(pieceNonBureau, never()).getSurface();
    }
}
