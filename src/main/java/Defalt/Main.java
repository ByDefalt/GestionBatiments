package Defalt;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.DAO.BatimentDAO;
import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Visiteur.VisiteurTypePiece;
import javafx.scene.control.TreeItem;


public class Main {
    public static void main(String[] args) throws Exception {
        Campus campus = new Campus();
        campus.createBatiment("Batiment 12", "Usage 1", 100, true, 10, 2, 5);
        campus.createBatiment("Batiment 2", "Usage 2", 200, false, 20, 3, 10);
        campus.createBatiment("Batiment 3", "Usage 3", 300, true, 30, 4, 15);
        campus.createBatiment("Batiment 4", "Usage 4", 400, false, 40, 5, 20);
        campus.destroyBatiment("Batiment 4");
        campus.batimentsToJson("batiments99.json");
        Campus campus2 = new Campus();
        campus2.jsonToBatiments("batiments99.json");
        System.out.println(campus2.getBatiments());
        campus2.updateNomBatiment("Batiment 12", "Batiment 1");
        System.out.println(campus2.getBatiments());
        System.out.println(campus2.afficherDetailsBatiment("Batiment 1",new VisiteurTypePiece()));
        for(TreeItem<String> i : campus2.afficherDetailsBatiment("Batiment 1", new VisiteurTypePiece()).getChildren()){
            System.out.println(i.getValue());
            for(TreeItem<String> j : i.getChildren()){
                System.out.println(j.getValue());
            }
        }
        campus2.updatePieceEstBureau("Batiment 1", 1);
        System.out.println(campus2.afficherDetailsBatiment("Batiment 1",new VisiteurTypePiece()));
        for(TreeItem<String> i : campus2.afficherDetailsBatiment("Batiment 1", new VisiteurTypePiece()).getChildren()){
            System.out.println(i.getValue());
            for(TreeItem<String> j : i.getChildren()){
                System.out.println(j.getValue());
            }
        }
        Campus campus3 = new Campus();
        System.out.println(campus3.jsonToBatiments("batiments99False.json"));
    }
}