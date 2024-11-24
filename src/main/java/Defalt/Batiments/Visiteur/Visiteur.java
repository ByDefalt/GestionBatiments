package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import javafx.scene.control.TreeItem;

/**
 * Interface représentant un visiteur capable de traiter différents types d'éléments d'un bâtiment.
 * Implémente le pattern visiteur pour exécuter des opérations spécifiques sur des objets
 * sans modifier leurs classes.
 */
public interface Visiteur {

    /**
     * Applique une opération sur un {@link Batiment}.
     *
     * @param batiment Le bâtiment à visiter.
     */
    TreeItem<String> visit(Batiment batiment);

    /**
     * Applique une opération sur un {@link Etage}.
     *
     * @param etage L'étage à visiter.
     */
    TreeItem<String> visit(Etage etage);

    /**
     * Applique une opération sur une {@link Piece}.
     *
     * @param piece La pièce à visiter.
     */
    TreeItem<String> visit(Piece piece);
}
