package Defalt;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Factory.BatimentFactory;
import Defalt.Batiments.Factory.BatimentFactoryJson;
import Defalt.Batiments.Verificateur.ProblemeBatiment;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import Defalt.Batiments.Visiteur.VisiteurTypePiece;
import javafx.scene.control.TreeItem;

import java.io.IOException;
import java.util.List;


public class Main {


    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.TestFactory();
        main.TestFactoryJson();
        main.TestVerificateurBatiment();
        main.TestCampus();
        main.TestVisiteur();
        main.TestBatimentDao();
    }


    public void TestFactory(){
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestFactory");
        BatimentFactory batimentFactory = new BatimentFactory();
        batimentFactory.createBatiment("Batiment 1", "Usage 1", 100, true, 10, 2, 5);
    }
    public void TestFactoryJson() throws Exception {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestFactoryJson");
        BatimentFactoryJson batimentFactoryJson = new BatimentFactoryJson();
        List<Batiment> batiments=batimentFactoryJson.jsonToBatiments("batiments99.json");
        System.out.println(batiments);
    }
    public void TestVerificateurBatiment() throws Exception {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestVerificateurBatiment");
        BatimentFactoryJson batimentFactoryJson = new BatimentFactoryJson();
        List<Batiment> batiments=batimentFactoryJson.jsonToBatiments("batiments99.json");
        VerificateurBatiment verificateurBatiment = new VerificateurBatiment();
        for(Batiment batiment : batiments){
            List<ProblemeBatiment> problemes =verificateurBatiment.verifBatiment(batiment);
            System.out.println(problemes);
        }
    }
    public void TestCampus(){
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestCampus");
        Campus campus = new Campus();
        campus.createBatiment("Batiment 1", "Usage 1", 100, true, 10, 2, 5);
        campus.updateNomBatiment("Batiment 1", "Batiment 2");
        campus.updatePieceEstBureau("Batiment 2", 1);
        campus.destroyBatiment("Batiment 2");
        System.out.println(campus.getBatiments());
    }
    public void TestVisiteur(){
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestVisiteur");
        Campus campus = new Campus();
        campus.createBatiment("Batiment 1", "Usage 1", 100, true, 10, 2, 5);
        TreeItem<String> tree = campus.afficherDetailsBatiment("Batiment 1",new VisiteurTypePiece());
        System.out.println(tree.getValue());
        for(TreeItem<String> item : tree.getChildren()){
            System.out.println(item.getValue());
            for (TreeItem<String> item2 : item.getChildren()){
                System.out.println(item2.getValue());
            }
        }
    }
    public void TestBatimentDao() throws IOException, ClassNotFoundException {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("TestBatimentDao");
        Campus campus = new Campus();
        campus.createBatiment("Batiment 1", "Usage 1", 100, true, 10, 2, 5);
        campus.serializeBatiments("batimentsTestMain.json");
        Campus campus2 = new Campus();
        campus2.deserializeBatiments("batimentsTestMain.json");
        System.out.println(campus2.getBatiments());
    }

}
