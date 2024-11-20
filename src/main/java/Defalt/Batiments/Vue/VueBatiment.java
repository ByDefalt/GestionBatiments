package Defalt.Batiments.Vue;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Observer.Observer;


public class VueBatiment implements Observer {
    private Campus campus;
    private Batiment batiment;

    public VueBatiment(Campus campus) {
        this.campus = campus;
        this.campus.addObserver(this);
    }

    @Override
    public void update() {
        if (batiment != null) {
            afficherDetailsBatiment();
        }
    }

    public void afficherDetailsBatiment() {
        System.out.println("Détails du bâtiment :");
        System.out.println("Nom : " + batiment.getNom());
        System.out.println("Numero : " + batiment.getNumero());
        System.out.println("Usage : " + batiment.getUsage());
        System.out.println("Nombre d'étages : " + batiment.getEtages().size());
        System.out.println("Nombre de pièces : " + batiment.getPieces().size());
        boolean etageVisited = false;
        Etage etageVisitedObject = null;
        for (Piece piece : batiment.getPieces()) {
            if (!etageVisited || etageVisitedObject != piece.getEtage()) {
                System.out.println("Etage : " + piece.getEtage().getNumero());
                etageVisited = true;
                etageVisitedObject = piece.getEtage();
            }
            System.out.println("Piece : " + piece.getNumero() + " - Type : " + (piece.isEstBureau()?"Bureau":"Autre"));
        }
    }

    public void ajouterEtage(Etage etage) {
        batiment.getEtages().add(etage);
        campus.notifyObservers();
    }

    public void supprimerEtage(Etage etage) {
        batiment.getEtages().remove(etage);
        campus.notifyObservers();
    }
}

