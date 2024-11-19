package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

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
    void visit(Batiment batiment);

    /**
     * Applique une opération sur un {@link Etage}.
     *
     * @param etage L'étage à visiter.
     */
    void visit(Etage etage);

    /**
     * Applique une opération sur une {@link Piece}.
     *
     * @param piece La pièce à visiter.
     */
    void visit(Piece piece);
}
