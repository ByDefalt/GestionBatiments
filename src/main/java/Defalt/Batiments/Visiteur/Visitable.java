package Defalt.Batiments.Visiteur;

import javafx.scene.control.TreeItem;

/**
 * Interface représentant un élément pouvant être visité par un {@link Visiteur}.
 * Permet d'appliquer des opérations spécifiques à l'objet sans modifier sa structure.
 */
public interface Visitable {

    /**
     * Accepte un visiteur pour exécuter une opération sur l'objet.
     *
     * @param visitor Le visiteur effectuant une opération sur cet objet.
     * @return Un {@link TreeItem} représentant l'objet après l'opération.
     */
    TreeItem<String> accept(Visiteur visitor);
}
