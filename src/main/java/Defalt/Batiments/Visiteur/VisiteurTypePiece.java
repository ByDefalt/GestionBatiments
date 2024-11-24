package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import javafx.scene.control.TreeItem;

/**
 * Implémentation de l'interface {@link Visiteur} qui affiche les informations
 * relatives aux types de pièces dans un bâtiment, à savoir si une pièce est un bureau ou non.
 * <p>
 * Utilise le pattern visiteur pour traiter les objets {@link Batiment}, {@link Etage},
 * et {@link Piece} spécifiquement en fonction du type de chaque pièce.
 */
public class VisiteurTypePiece implements Visiteur {
    private TreeItem<String> rootItem;
    public VisiteurTypePiece() {
        this.rootItem = new TreeItem<>();
    }
    /**
     * Affiche le nom du bâtiment.
     *
     * @param batiment Le bâtiment à visiter.
     */
    @Override
    public TreeItem<String> visit(Batiment batiment) {
        rootItem.setValue("Batiment : "+batiment.getNom());
        rootItem.setExpanded(true);
        Etage etageVisitedObject = null;
        TreeItem<String> etageItem = null;
        for (Piece piece : batiment.getPieces()) {
            if (!piece.getEtage().equals(etageVisitedObject)) {
                etageItem=piece.getEtage().accept(this);
                etageItem.setExpanded(true);
                rootItem.getChildren().add(etageItem);
                etageVisitedObject = piece.getEtage();
            }
            TreeItem<String> pieceItem = piece.accept(this);
            if(pieceItem!=null){
                etageItem.getChildren().add(pieceItem);
            }
        }
        return rootItem;
    }

    /**
     * Affiche le numéro de l'étage.
     *
     * @param etage L'étage à visiter.
     */
    @Override
    public TreeItem<String> visit(Etage etage) {
        return new TreeItem<>("  Etage n°" + etage.getNumero());
    }

    /**
     * Affiche les informations sur la pièce, y compris son numéro et son type
     * (bureau ou autre).
     *
     * @param piece La pièce à visiter.
     */
    @Override
    public TreeItem<String> visit(Piece piece) {
        return new TreeItem<>("    Piece n°" + piece.getNumero() + " (Type : " + (piece.isEstBureau() ? "Bureau" : "Autre") + ")");
    }
}
