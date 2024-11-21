package Defalt.Batiments.Vue;

import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Observer.Observer;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;

import java.util.List;

public class VueListeBatiments implements Observer {
    private Campus campus;

    public VueListeBatiments(Campus campus) {
        this.campus = campus;
        this.campus.addObserver(this);
    }

    @Override
    public void update() {
        afficherListeBatiments();
    }

    public void afficherListeBatiments() {
        List<String> batiments = campus.getBatiments();
        System.out.println("Liste des bâtiments :");
        for (String batiment : batiments) {
            System.out.println(batiment);
        }
    }

    public void ajouterBatiment(String nom, String usage, int surfacePiece, int nbBureau, int nbEtage, int nbPiece) {
        campus.createBatiment(nom,usage,surfacePiece,nbBureau,nbEtage,nbPiece);
        campus.notifyObservers();
    }

    public void supprimerBatiment(String nom) {
        campus.destroyBatiment(nom);
        campus.notifyObservers();
    }

    public List<List<ProblemeBatiment>> verifierBatiment() {
        return campus.verifBatiments();
    }
}
