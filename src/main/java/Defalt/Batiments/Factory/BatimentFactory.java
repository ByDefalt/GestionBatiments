package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.Objects;

/**
 * Usine pour créer des instances de {@link Batiment}.
 * Permet de générer des bâtiments avec un nombre donné d'étages et de pièces,
 * avec des options de personnalisation comme la surface des pièces ou le nombre de bureaux.
 */
public class BatimentFactory {
    /** Identifiant unique de l'usine. */
    private int num;

    /** Identifiant statique utilisé pour générer des identifiants uniques pour chaque usine. */
    private static int id = 0;

    /** Nombre d'étages à créer pour un bâtiment. */
    private int nbEtage;

    /** Nombre de pièces à créer pour un bâtiment. */
    private int nbPiece;

    /** Nombre total de bâtiments créés par cette usine. */
    private int nbBatimentsCree;

    /**
     * Constructeur par défaut. Initialise une nouvelle usine avec un identifiant unique.
     */
    public BatimentFactory() {
        this.num = id;
        this.nbEtage = 0;
        this.nbPiece = 0;
        this.nbBatimentsCree = 0;
        id++;
    }

    /**
     * Obtient l'identifiant de cette usine.
     *
     * @return L'identifiant unique de l'usine.
     */
    public int getNum() {
        return num;
    }

    /**
     * Définit l'identifiant de cette usine.
     *
     * @param num L'identifiant unique à attribuer.
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * Obtient le nombre d'étages à créer pour un bâtiment.
     *
     * @return Le nombre d'étages.
     */
    public int getNbEtage() {
        return nbEtage;
    }

    /**
     * Définit le nombre d'étages à créer pour un bâtiment.
     *
     * @param nbEtage Le nombre d'étages.
     */
    public void setNbEtage(int nbEtage) {
        this.nbEtage = nbEtage;
    }

    /**
     * Obtient le nombre de pièces à créer pour un bâtiment.
     *
     * @return Le nombre de pièces.
     */
    public int getNbPiece() {
        return nbPiece;
    }

    /**
     * Définit le nombre de pièces à créer pour un bâtiment.
     *
     * @param nbPiece Le nombre de pièces.
     */
    public void setNbPiece(int nbPiece) {
        this.nbPiece = nbPiece;
    }

    /**
     * Obtient le nombre total de bâtiments créés par cette usine.
     *
     * @return Le nombre de bâtiments créés.
     */
    public int getNbBatimentsCree() {
        return nbBatimentsCree;
    }

    /**
     * Définit le nombre total de bâtiments créés par cette usine.
     *
     * @param nbBatimentsCree Le nombre de bâtiments créés.
     */
    public void setNbBatimentsCree(int nbBatimentsCree) {
        this.nbBatimentsCree = nbBatimentsCree;
    }

    /**
     * Crée un bâtiment avec les paramètres donnés.
     *
     * @param nom Le nom du bâtiment.
     * @param usage L'usage du bâtiment (ex. résidentiel, commercial).
     * @param surfacePiece La surface de chaque pièce en mètres carrés.
     * @param startOne Si les numéros d'étage et de pièce commencent à 1 ({@code true}) ou à 0 ({@code false}).
     * @param nbBureau Le nombre de pièces qui seront des bureaux.
     * @return Une instance de {@link Batiment}, ou {@code null} si les paramètres sont invalides.
     */
    public Batiment createBatiment(String nom, String usage, int surfacePiece, boolean startOne, int nbBureau) {
        if (nbEtage < 1 || nbPiece < 1 || surfacePiece < 1) return null;

        int nbBureaux = nbBureau;
        Batiment batiment = new Batiment(nom, usage);

        for (int i = 0; i < nbEtage; i++) {
            Etage etage = new Etage(startOne ? i + 1 : i);
            batiment.getEtages().add(etage);
        }

        int pieceParEtage = (int) Math.ceil((double) nbPiece / nbEtage);

        for (int i = 0; i < nbPiece; i++) {
            int numeroPiece = i + (startOne ? 1 : 0);
            int etageIndex = (startOne ? i : i + 1) / pieceParEtage;

            if (etageIndex >= batiment.getEtages().size()) {
                etageIndex = batiment.getEtages().size() - 1;
            }

            Etage etage = batiment.getEtages().get(etageIndex);
            Piece piece = new Piece(surfacePiece, nbBureaux > 0, numeroPiece, etage);
            batiment.getPieces().add(piece);
            nbBureaux--;
        }

        this.nbBatimentsCree++;
        return batiment;
    }

    /**
     * Crée un bâtiment avec des paramètres supplémentaires pour le nombre d'étages et de pièces.
     *
     * @param nom Le nom du bâtiment.
     * @param usage L'usage du bâtiment (ex. résidentiel, commercial).
     * @param surfacePiece La surface de chaque pièce en mètres carrés.
     * @param startOne Si les numéros d'étage et de pièce commencent à 1 ({@code true}) ou à 0 ({@code false}).
     * @param nbBureau Le nombre de pièces qui seront des bureaux.
     * @param nbEtage Le nombre d'étages du bâtiment.
     * @param nbPiece Le nombre de pièces dans le bâtiment.
     * @return Une instance de {@link Batiment}, ou {@code null} si les paramètres sont invalides.
     */
    public Batiment createBatiment(String nom, String usage, int surfacePiece, boolean startOne, int nbBureau, int nbEtage, int nbPiece) {
        int nbBureaux = nbBureau;
        Batiment batiment = new Batiment(nom, usage);

        for (int i = 0; i < nbEtage; i++) {
            Etage etage = new Etage(startOne ? i + 1 : i);
            batiment.getEtages().add(etage);
        }

        int pieceParEtage = (int) Math.ceil((double) nbPiece / nbEtage);

        for (int i = 0; i < nbPiece; i++) {
            int numeroPiece = i + (startOne ? 1 : 0);
            int etageIndex = (startOne ? i : i + 1) / pieceParEtage;

            if (etageIndex >= batiment.getEtages().size()) {
                etageIndex = batiment.getEtages().size() - 1;
            }

            Etage etage = batiment.getEtages().get(etageIndex);
            Piece piece = new Piece(surfacePiece, nbBureaux > 0, numeroPiece, etage);
            batiment.getPieces().add(piece);
            nbBureaux--;
        }

        this.nbBatimentsCree++;
        return batiment;
    }

    /**
     * Vérifie l'égalité entre cette usine et un autre objet.
     *
     * @param o L'objet à comparer.
     * @return {@code true} si les usines sont égales, sinon {@code false}.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatimentFactory that = (BatimentFactory) o;
        return num == that.num && nbEtage == that.nbEtage && nbPiece == that.nbPiece;
    }

    /**
     * Génère le code de hachage pour cette usine.
     *
     * @return Le code de hachage.
     */
    @Override
    public int hashCode() {
        return Objects.hash(num, nbEtage, nbPiece);
    }

    /**
     * Renvoie une représentation sous forme de chaîne de caractères de cette usine.
     *
     * @return Une chaîne décrivant l'usine.
     */
    @Override
    public String toString() {
        return "BatimentFactory{" +
                "num=" + num +
                ", nbEtage=" + nbEtage +
                ", nbPiece=" + nbPiece +
                ", nbBatimentsCree=" + nbBatimentsCree +
                '}';
    }
}
