package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

/**
 * Implémentation de l'interface {@link Visiteur} qui affiche les informations
 * relatives aux types de pièces dans un bâtiment, à savoir si une pièce est un bureau ou non.
 * <p>
 * Utilise le pattern visiteur pour traiter les objets {@link Batiment}, {@link Etage},
 * et {@link Piece} spécifiquement en fonction du type de chaque pièce.
 */
public class VisiteurTypePiece implements Visiteur {

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
     * Affiche les informations sur la pièce, y compris son numéro et son type
     * (bureau ou autre).
     *
     * @param piece La pièce à visiter.
     */
    @Override
    public void visit(Piece piece) {
        System.out.println("    Piece n°" + piece.getNumero() + " (Type : " + (piece.isEstBureau() ? "Bureau" : "Autre") + ")");
    }
}
