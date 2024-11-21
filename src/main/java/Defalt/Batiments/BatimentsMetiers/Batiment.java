package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visitable;
import Defalt.Batiments.Visiteur.Visiteur;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Représente un bâtiment avec des informations générales telles que le nom, l'usage, les étages et les pièces.
 * Cette classe est visitable via le pattern Visiteur.
 */
public class Batiment implements Visitable {

    /** Nom du bâtiment. */
    private String nom;

    /** Usage du bâtiment (exemple : résidentiel, commercial, etc.). */
    private String usage;

    /** Liste des étages du bâtiment. */
    private List<Etage> etages;

    /** Liste des pièces du bâtiment. */
    private List<Piece> pieces;

    /**
     * Constructeur par défaut.
     */
    public Batiment() {
    }

    /**
     * Constructeur pour créer un bâtiment avec un nom et un usage spécifiés.
     * @param nom Le nom du bâtiment.
     * @param usage L'usage du bâtiment.
     */
    public Batiment(String nom, String usage) {
        this.nom = nom;
        this.usage = usage;
        this.etages = new ArrayList<>();
        this.pieces = new ArrayList<>();
    }

    /**
     * Obtient le nom du bâtiment.
     * @return Le nom du bâtiment.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du bâtiment.
     * @param nom Le nom du bâtiment.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient l'usage du bâtiment.
     * @return L'usage du bâtiment.
     */
    public String getUsage() {
        return usage;
    }

    /**
     * Définit l'usage du bâtiment.
     * @param usage L'usage du bâtiment.
     */
    public void setUsage(String usage) {
        this.usage = usage;
    }

    /**
     * Obtient la liste des étages du bâtiment.
     * @return La liste des étages.
     */
    public List<Etage> getEtages() {
        return etages;
    }

    /**
     * Définit la liste des étages du bâtiment.
     * @param etages La liste des étages.
     */
    public void setEtages(List<Etage> etages) {
        this.etages = etages;
    }

    /**
     * Obtient la liste des pièces du bâtiment.
     * @return La liste des pièces.
     */
    public List<Piece> getPieces() {
        return pieces;
    }

    /**
     * Définit la liste des pièces du bâtiment.
     * @param pieces La liste des pièces.
     */
    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Méthode acceptant un visiteur, permettant l'application d'une logique sur ce bâtiment
     * et ses composants (étages et pièces).
     * @param visiteur Le visiteur à accepter.
     */
    @Override
    public void accept(Visiteur visiteur) {
        visiteur.visit(this);
        boolean etageVisited = false;
        Etage etageVisitedObject = null;
        for (Piece piece : pieces) {
            if (!etageVisited || etageVisitedObject != piece.getEtage()) {
                piece.getEtage().accept(visiteur);
                etageVisited = true;
                etageVisitedObject = piece.getEtage();
            }
            piece.accept(visiteur);
        }
    }

    /**
     * Vérifie l'égalité de ce bâtiment avec un autre objet.
     * @param o L'objet à comparer.
     * @return {@code true} si les deux objets sont égaux, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Batiment batiment = (Batiment) o;
        return nom.equals(batiment.nom);
    }

    /**
     * Génère le code de hachage pour ce bâtiment.
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom, usage, etages, pieces);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de ce bâtiment.
     * @return Une chaîne décrivant le bâtiment.
     */
    @Override
    public String toString() {
        return "Batiment{" +
                ", nom='" + nom + '\'' +
                ", usage='" + usage + '\'' +
                ", etages=" + etages +
                ", pieces=" + pieces +
                '}';
    }
}
