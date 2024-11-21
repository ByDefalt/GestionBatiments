package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Factory.BatimentFactoryJson;
import Defalt.Batiments.Observer.Observable;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import Defalt.Batiments.Visiteur.Visiteur;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
     * @param numEtage Numéro de l'étage.
     * @param numPiece Numéro de la pièce.
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon.
     */
    public boolean updatePieceEstBureau(String nom, int numEtage, int numPiece) {
        Batiment batiment = batiments.stream()
                .filter(i -> i.getNom().equals(nom))
                .findFirst()
                .orElse(null);

        if (batiment == null) return false;

        return batiment.getPieces().stream()
                .filter(i -> i.getEtage().getNumero() == numEtage && i.getNumero() == numPiece)
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
    public List<List<ProblemeBatiment>> verifBatiments() {
        VerificateurBatiment verifier = new VerificateurBatiment();
        return null;
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
     * @throws Exception En cas d'erreur de lecture.
     */
    public String jsonToBatiments(String nomFichierEntree) throws Exception {
        BatimentFactoryJson factoryJson = new BatimentFactoryJson();
        List<Batiment> batimentsImported = factoryJson.jsonToBatiments(nomFichierEntree).stream().filter(Objects::nonNull).toList();

        Map<String,List<ProblemeBatiment>> mapProblemes = new HashMap<>();
        VerificateurBatiment verifier = new VerificateurBatiment();

        batimentsImported.forEach(b->{
                    Map<String,List<ProblemeBatiment>> map=verifier.verifBatiment(b, startOne);
                    mapProblemes.putAll(map);
                    if(batiments.contains(b)){
                        map.get(b.getNom()).add(ProblemeBatiment.NOMINLISTE);
                        map.get(b.getNom()).remove(ProblemeBatiment.AUCUN);
                    }
                    if(map.get(b.getNom()).contains(ProblemeBatiment.AUCUN)){
                        batiments.add(b);
                    }
        });

        notifyObservers();
        return checkError(mapProblemes);
    }

    public String checkError(Map<String,List<ProblemeBatiment>> mapProblemes){
        if(mapProblemes.isEmpty()){
            return null;
        }
        AtomicReference<String> res = new AtomicReference<String>("");
        mapProblemes.forEach((k,v)->{
            res.set(res.get()+"Problemes pour le batiment "+k);
            v.forEach(p->{
                switch (p){
                    case NOM -> res.set(res.get()+"\nNom du batiment null ou vide");
                    case NOMINLISTE -> res.set(res.get()+"\nNom du batiment déjà existant");
                    case USAGE -> res.set(res.get()+"\nUsage du batiment null ou vide");
                    case ETAGES -> res.set(res.get()+"\nEtages incorrects : "+p.getEtagesProblemes());
                    case PIECES -> res.set(res.get()+"\nPieces incorrectes : "+p.getPiecesProblemes());
                    case ETAGE_ET_PIECE -> {
                        res.set(res.get()+"\nEtages incorrects : "+p.getEtagesProblemes());
                        res.set(res.get()+"\nPieces incorrectes : "+p.getPiecesProblemes());
                    }
                }
            });
        });
        return res.get();
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
