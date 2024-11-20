package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Factory.BatimentFactoryJson;
import Defalt.Batiments.Observer.Observable;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import Defalt.Batiments.Visiteur.Visiteur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe représentant un campus contenant plusieurs bâtiments.
 * Fournit des fonctionnalités pour créer, gérer, et vérifier les bâtiments.
 */
public class Campus implements Observable {

    /**
     * Nom du campus.
     */
    private String nom;

    /**
     * Indique si la numérotation des étages et pièces commence à 1 (true) ou à 0 (false).
     */
    private Boolean startOne;

    /**
     * Usine pour la création de bâtiments.
     */
    private BatimentFactory batimentFactory;

    /**
     * Liste des bâtiments du campus.
     */
    private List<Batiment> batiments;

    private List<Observer> observers = new ArrayList<>();

    /**
     * Constructeur du campus.
     *
     * @param nom      Nom du campus.
     * @param startOne Indique si la numérotation commence à 1 (true) ou à 0 (false).
     */
    public Campus(String nom, Boolean startOne) {
        this.nom = nom;
        this.startOne = startOne;
        this.batimentFactory = new BatimentFactory();
        this.batiments = new ArrayList<>();
    }

    /**
     * Retourne le paramètre `startOne`.
     *
     * @return {@code true} si la numérotation commence à 1, {@code false} sinon.
     */
    public Boolean getStartOne() {
        return startOne;
    }

    /**
     * Définit le paramètre `startOne`.
     *
     * @param startOne {@code true} pour commencer la numérotation à 1, {@code false} pour commencer à 0.
     */
    public void setStartOne(Boolean startOne) {
        this.startOne = startOne;
    }

    /**
     * Retourne le nom du campus.
     *
     * @return Nom du campus.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom du campus.
     *
     * @param nom Nouveau nom du campus.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Crée un nouveau bâtiment et l'ajoute au campus.
     *
     * @param nom          Nom du bâtiment.
     * @param usage        Usage du bâtiment.
     * @param surfacePiece Surface des pièces du bâtiment.
     * @param nbBureau     Nombre de bureaux dans le bâtiment.
     * @param nbEtage      Nombre d'étages dans le bâtiment.
     * @param nbPiece      Nombre de pièces par étage.
     * @return {@code true} si le bâtiment a été créé avec succès, {@code false} si le nom existe déjà.
     */
    public boolean createBatiment(String nom, String usage, int surfacePiece, int nbBureau, int nbEtage, int nbPiece) {
        boolean exists = batiments.stream().anyMatch(i -> i.getNom().equals(nom));
        if (exists) return false;

        Batiment batiment = batimentFactory.createBatiment(nom, usage, surfacePiece, startOne, nbBureau, nbEtage, nbPiece);
        if (batiment == null) return false;

        batiments.add(batiment);
        notifyObservers();
        return true;
    }

    /**
     * Supprime un bâtiment du campus.
     *
     * @param nom Nom du bâtiment à supprimer.
     * @return {@code true} si le bâtiment a été supprimé, {@code false} sinon.
     */
    public boolean destroyBatiment(String nom) {
        return batiments.stream()
                .filter(i -> i.getNom().equals(nom))
                .findFirst()
                .map(batiment -> {
                    batiments.remove(batiment);
                    notifyObservers();
                    return true;
                })
                .orElse(false);
    }

    /**
     * Met à jour le nom d'un bâtiment.
     *
     * @param oldNom Ancien nom du bâtiment.
     * @param newNom Nouveau nom du bâtiment.
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon.
     */
    public boolean updateNomBatiment(String oldNom, String newNom) {
        return batiments.stream()
                .filter(i -> i.getNom().equals(oldNom))
                .findFirst()
                .map(batiment -> {
                    batiment.setNom(newNom);
                    notifyObservers();
                    return true;
                })
                .orElse(false);
    }

    /**
     * Met à jour l'état "bureau" d'une pièce spécifique.
     *
     * @param nom   Nom du bâtiment contenant la pièce.
     * @param etage Numéro de l'étage.
     * @param piece Numéro de la pièce.
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon.
     */
    public boolean updatePieceEstBureau(String nom, int etage, int piece) {
        Batiment batiment = batiments.stream()
                .filter(i -> i.getNom().equals(nom))
                .findFirst()
                .orElse(null);

        if (batiment == null) return false;

        return batiment.getPieces().stream()
                .filter(i -> i.getEtage().getNumero() == etage && i.getNumero() == piece)
                .findFirst()
                .map(piece1 -> {
                    piece1.setEstBureau(!piece1.isEstBureau());
                    notifyObservers();
                    return true;
                })
                .orElse(false);
    }

    /**
     * Vérifie si tous les bâtiments du campus respectent les contraintes de numérotation.
     *
     * @return {@code true} si tous les bâtiments sont valides, {@code false} sinon.
     */
    public boolean verifBatiments() {
        VerificateurBatiment verifier = new VerificateurBatiment();
        return batiments.stream().allMatch(b -> verifier.verifBatiment(b, startOne));
    }

    /**
     * Retourne les noms des bâtiments du campus.
     *
     * @return Liste des noms des bâtiments.
     */
    public List<String> getBatiments() {
        return batiments.stream().map(Batiment::getNom).toList();
    }

    /**
     * Exporte les bâtiments du campus dans un fichier JSON.
     *
     * @param nomFichierSortie Nom du fichier JSON de sortie.
     * @throws IOException En cas d'erreur d'écriture.
     */
    public void batimentsToJson(String nomFichierSortie) throws IOException {
        BatimentFactoryJson factoryJson = new BatimentFactoryJson();
        factoryJson.BatimentsToJson(batiments, nomFichierSortie);
    }

    /**
     * Importe des bâtiments depuis un fichier JSON et les ajoute au campus si valides.
     *
     * @param nomFichierEntree Nom du fichier JSON d'entrée.
     * @return Liste des bâtiments importés.
     * @throws Exception En cas d'erreur de lecture.
     */
    public List<Batiment> jsonToBatiments(String nomFichierEntree) throws Exception {
        BatimentFactoryJson factoryJson = new BatimentFactoryJson();
        List<Batiment> imported = factoryJson.jsonToBatiments(nomFichierEntree);

        VerificateurBatiment verifier = new VerificateurBatiment();
        if (imported != null && imported.stream().allMatch(b -> verifier.verifBatiment(b, startOne))) {
            this.batiments = imported;
            notifyObservers();
        }

        return imported;
    }

    /**
     * Affiche les caractéristiques des bâtiments en utilisant un visiteur.
     *
     * @param visiteur Visiteur appliqué aux bâtiments.
     */
    public void afficherCaracteristiques(Visiteur visiteur) {
        for (Batiment batiment : batiments) {
            batiment.accept(visiteur);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    /**
     * Vérifie si deux campus sont égaux (basé sur le nom).
     *
     * @param o Objet à comparer.
     * @return {@code true} si les campus sont égaux, {@code false} sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Campus campus = (Campus) o;
        return Objects.equals(nom, campus.nom);
    }

    /**
     * Retourne le code de hachage du campus (basé sur le nom).
     *
     * @return Code de hachage.
     */
    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }

    /**
     * Retourne une représentation textuelle du campus.
     *
     * @return Représentation textuelle.
     */
    @Override
    public String toString() {
        return "Campus{" +
                "nom='" + nom + '\'' +
                '}';
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
