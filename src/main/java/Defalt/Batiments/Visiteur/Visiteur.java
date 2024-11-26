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
     * Effectue une opération spécifique sur un objet de type {@link Batiment}.
     *
     * @param batiment l'objet {@link Batiment} à traiter.
     * @return un objet {@link TreeItem} contenant les détails générés à partir du bâtiment.
     */
    TreeItem<String> visit(Batiment batiment);

    /**
     * Effectue une opération spécifique sur un objet de type {@link Etage}.
     *
     * @param etage l'objet {@link Etage} à traiter.
     * @return un objet {@link TreeItem} contenant les détails générés à partir de l'étage.
     */
    TreeItem<String> visit(Etage etage);

    /**
     * Effectue une opération spécifique sur un objet de type {@link Piece}.
     *
     * @param piece l'objet {@link Piece} à traiter.
     * @return un objet {@link TreeItem} contenant les détails générés à partir de la pièce.
     */
    TreeItem<String> visit(Piece piece);
}
