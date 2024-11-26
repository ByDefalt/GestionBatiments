package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visitable;
import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;

import java.io.Serializable;

/**
 * Représente un étage dans un bâtiment.
 * Cette classe est visitable via le pattern Visiteur.
 */
public class Etage implements Visitable, Serializable {
    /** Numéro unique de l'étage. */
    private int numero;

    /**
     * Constructeur par défaut.
     */
    public Etage() {
    }

    /**
     * Constructeur pour créer un étage avec un numéro spécifié.
     * @param numero Le numéro de l'étage.
     */
    public Etage(int numero) {
        this.numero = numero;
    }

    /**
     * Définit le numéro de l'étage.
     * @param numero Le numéro de l'étage.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtient le numéro de l'étage.
     * @return Le numéro de l'étage.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Méthode acceptant un visiteur, permettant l'application d'une logique sur cet étage.
     * @param visiteur Le visiteur à accepter.
     */
    @Override
    public TreeItem<String> accept(Visiteur visiteur) {
        return visiteur.visit(this);
    }

    /**
     * Vérifie l'égalité de cet étage avec un autre objet.
     * @param o L'objet à comparer.
     * @return {@code true} si les deux objets sont égaux, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Etage etage = (Etage) o;
        return numero == etage.numero;
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de cet étage.
     * @return Une chaîne décrivant l'étage.
     */
    @Override
    public String toString() {
        return "Etage{" +
                "numero=" + numero +
                '}';
    }
}
