package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

/**
 * Implémentation de l'interface {@link Visiteur} qui affiche les informations
 * des bureaux et leur surface dans un bâtiment.
 * <p>
 * Utilise le pattern visiteur pour traiter les {@link Batiment}, {@link Etage},
 * et {@link Piece} spécifiquement.
 */
public class VisiteurBureauSurface implements Visiteur {

    /**
     * Affiche le nom du bâtiment.
     *
     * @param batiment Le bâtiment à visiter.
     */
    @Override
    public void visit(Batiment batiment) {
        System.out.println("Batiment : " + batiment.getNom());
    }

    /**
     * Affiche le numéro de l'étage.
     *
     * @param etage L'étage à visiter.
     */
    @Override
    public void visit(Etage etage) {
        System.out.println("  Etage n°" + etage.getNumero());
    }

    /**
     * Affiche les informations sur la pièce si elle est un bureau,
     * y compris son numéro et sa surface.
     *
     * @param piece La pièce à visiter.
     */
    @Override
    public void visit(Piece piece) {
        if (piece.isEstBureau()) {
            System.out.println("    Bureau n°" + piece.getNumero() + " (Surface : " + piece.getSurface() + " m²)");
        }
    }
}
