package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Factory.BatimentFactoryJson;
import Defalt.Batiments.Observer.Observable;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.util.*;

/**
 * Classe représentant un campus contenant plusieurs bâtiments.
 * Fournit des fonctionnalités pour créer, gérer, et vérifier les bâtiments.
 */
public class Campus implements Observable {

    /**
     * Usine pour la création de bâtiments.
     */
    private BatimentFactory batimentFactory;

    /**
     * Liste des bâtiments du campus.
     */
    private List<Batiment> batiments;

    private List<Observer> observers;

    /**
     * Constructeur du campus.
     *
     */
    public Campus() {
        this.batimentFactory = new BatimentFactory();
        this.batiments = new ArrayList<>();
        this.observers = new ArrayList<>();
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
    public boolean createBatiment(String nom, String usage, int surfacePiece,boolean startOne, int nbBureau, int nbEtage, int nbPiece) {
        Batiment batiment = batimentFactory.createBatiment(nom, usage, surfacePiece, startOne, nbBureau, nbEtage, nbPiece);
        if (batiment == null) return false;

        batiments.add(batiment);
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
                    return true;
                })
                .orElse(false);
    }

    /**
     * Met à jour l'état "bureau" d'une pièce spécifique.
     *
     * @param nom   Nom du bâtiment contenant la pièce.
     * @param numPiece Numéro de la pièce.
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon.
     */
    public boolean updatePieceEstBureau(String nom,int numPiece) {
        Batiment batiment = batiments.stream()
                .filter(i -> i.getNom().equals(nom))
                .findFirst()
                .orElse(null);

        if (batiment == null) return false;

        return batiment.getPieces().stream()
                .filter(i -> i.getNumero() == numPiece)
                .findFirst()
                .map(piece1 -> {
                    piece1.setEstBureau(!piece1.isEstBureau());
                    return true;
                })
                .orElse(false);
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

        VerificateurBatiment verifier = new VerificateurBatiment();
        StringBuilder res = new StringBuilder();
        int n=1;
        for(Batiment b:batimentsImported){
            List<ProblemeBatiment> listProbleme=verifier.verifBatiment(b);
            if (batiments.contains(b)) {
                listProbleme.add(ProblemeBatiment.NOMINLISTE);
                listProbleme.remove(ProblemeBatiment.AUCUN);
            }
            if (listProbleme.contains(ProblemeBatiment.AUCUN)) {
                batiments.add(b);
            }else{
                res.append(checkError(listProbleme, b, n));
            }
            n++;
        }
        return res.toString();
    }

    public String checkError(List<ProblemeBatiment> listProbleme, Batiment batiment, int n){
        StringBuilder res= new StringBuilder();
        if(listProbleme.contains(ProblemeBatiment.NULLBATIMENT)){
            return "\nLe batiment "+n+" est null\n";
        }
        if(!listProbleme.contains(ProblemeBatiment.NOM)){
            res.append("Le batiment '").append(batiment.getNom()).append("' a les problèmes suivants :\n");
        }else{
            res.append("Le batiment ").append(n).append(" a les problèmes suivants :\n");
        }
        for(ProblemeBatiment problemeBatiment:listProbleme){
            switch (problemeBatiment){
                case NOM:
                    res.append("    Le nom du batiment est incorrect\n");
                    break;
                case USAGE:
                    res.append("    L'usage du batiment est incorrect\n");
                    break;
                case ETAGES:
                    res.append("    L'ordre des étages est incorrecte ").append(problemeBatiment.getEtagesProblemes()).append("\n");
                    break;
                case PIECES:
                    res.append("    L'ordre des pieces est incorrecte ").append(problemeBatiment.getPiecesProblemes()).append("\n");
                    break;
                case NULLETAGE:
                    res.append("    Un étage est null\n");
                    break;
                case NULLEPIECE:
                    res.append("    Une pièce est null\n");
                    break;
                case NOMINLISTE:
                    res.append("    Le nom batiment existe déja\n");
                    break;
                default:
                    break;
            }
        }
        return res.toString();
    }

    public TreeItem<String> afficherDetailsBatiment(String nomBatiment, Visiteur visiteur) {
            Batiment batiment=batiments.stream()
                    .filter(b->b.getNom().equals(nomBatiment))
                    .findFirst()
                    .orElse(null);
            if(batiment==null){
                return null;
            }
            return batiment.accept(visiteur);
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

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
