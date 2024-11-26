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

    /**
     * Liste des Observers.
     */
    private List<Observer> observers;

    /**
     * Constructeur par defaut du campus.
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
     */
    public void createBatiment(String nom, String usage, int surfacePiece,boolean startOne, int nbBureau, int nbEtage, int nbPiece) {
        Batiment batiment = batimentFactory.createBatiment(nom, usage, surfacePiece, startOne, nbBureau, nbEtage, nbPiece);
        batiments.add(batiment);
    }

    /**
     * Supprime un bâtiment du campus.
     *
     * @param nom Nom du bâtiment à supprimer.
     * @return {@code true} si le bâtiment a été supprimé, {@code false} sinon.
     */
    public boolean destroyBatiment(String nom) {
        return batiments.stream()
                .filter(batiment -> batiment.getNom().equals(nom))
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
                .filter(batiment -> batiment.getNom().equals(oldNom))
                .findFirst()
                .map(batiment -> {
                    batiment.setNom(newNom);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Renverse l'état "bureau" d'une pièce spécifique.
     *
     * @param nom   Nom du bâtiment contenant la pièce.
     * @param numPiece Numéro de la pièce.
     * @return {@code true} si la mise à jour a réussi, {@code false} sinon.
     */
    public boolean updatePieceEstBureau(String nom,int numPiece) {
        Batiment batiment = batiments.stream()
                .filter(b -> b.getNom().equals(nom))
                .findFirst()
                .orElse(null);

        if (batiment == null) return false;

        return batiment.getPieces().stream()
                .filter(piece -> piece.getNumero() == numPiece)
                .findFirst()
                .map(piece -> {
                    piece.setEstBureau(!piece.isEstBureau());
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
    public void batimentsToJson(String nomFichierSortie) throws Exception {
        BatimentFactoryJson factoryJson = new BatimentFactoryJson();
        factoryJson.BatimentsToJson(batiments, nomFichierSortie);
    }

    /**
     * Importe des bâtiments depuis un fichier JSON et les ajoute au campus si valides.
     *
     * @param nomFichierEntree Nom du fichier JSON d'entrée.
     * @throws Exception En cas d'erreur de lecture.
     * @return Liste des problèmes rencontrés lors de l'importation.
     */
    public String jsonToBatiments(String nomFichierEntree) throws Exception {
        BatimentFactoryJson factoryJson = new BatimentFactoryJson();
        List<Batiment> batimentsImported = factoryJson.jsonToBatiments(nomFichierEntree).stream().filter(Objects::nonNull).toList();

        VerificateurBatiment verifier = new VerificateurBatiment();
        StringBuilder res = new StringBuilder();
        int numeroBatiment=1;
        for(Batiment batiment:batimentsImported){
            List<ProblemeBatiment> listProbleme=verifier.verifBatiment(batiment);
            if (batiments.contains(batiment)) {
                listProbleme.add(ProblemeBatiment.NOMINLISTE);
                listProbleme.remove(ProblemeBatiment.AUCUN);
            }
            if (listProbleme.contains(ProblemeBatiment.AUCUN)) {
                batiments.add(batiment);
                notifyObservers();
            }else{
                res.append(checkError(listProbleme, batiment, numeroBatiment));
            }
            numeroBatiment++;
        }
        return res.toString();
    }


    /**
     * Vérifie les problèmes associés à un bâtiment donné et retourne une description des erreurs.
     *
     * @param listProbleme une liste des problèmes détectés pour le bâtiment.
     * @param batiment le bâtiment à vérifier.
     * @param numeroBatiment l'identifiant ou l'index du bâtiment (utilisé si le bâtiment est null ou ne possède pas de nom valide).
     * @return une chaîne décrivant les erreurs détectées pour le bâtiment.
     */
    public String checkError(List<ProblemeBatiment> listProbleme, Batiment batiment, int numeroBatiment){
        StringBuilder res= new StringBuilder();
        if(listProbleme.contains(ProblemeBatiment.NULLBATIMENT)){
            return "\nLe batiment "+numeroBatiment+" est null\n";
        }
        if(!listProbleme.contains(ProblemeBatiment.NOM)){
            res.append("Le batiment '").append(batiment.getNom()).append("' a les problèmes suivants :\n");
        }else{
            res.append("Le batiment ").append(numeroBatiment).append(" a les problèmes suivants :\n");
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

    /**
     * Affiche les détails d'un bâtiment en utilisant un visiteur et les encapsule dans un objet TreeItem.
     *
     * @param nomBatiment le nom du bâtiment dont on souhaite afficher les détails.
     * @param visiteur le visiteur utilisé pour visiter le bâtiment et récupérer ses informations.
     * @return un objet TreeItem contenant les détails du bâtiment, ou null si le bâtiment n'existe pas.
     */
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
    /**
     * Ajoute un nouvel observateur à la liste des observateurs de la classe.
     *
     * @param observer l'observateur à ajouter.
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    /**
     * Supprime un observateur de la liste des observateurs de la classe.
     *
     * @param observer l'observateur à supprimer.
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
    /**
     * Notifie tous les observateurs enregistrés que quelque chose a changé.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
    /**
     * Récupère la liste actuelle des observateurs enregistrés.
     *
     * @return une liste d'observateurs.
     */
    public List<Observer> getObservers() {
        return observers;
    }
    /**
     * Remplace la liste actuelle des observateurs par une nouvelle liste.
     *
     * @param observers une nouvelle liste d'observateurs.
     */
    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }
}
