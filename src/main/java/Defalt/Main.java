package Defalt;

import Defalt.Batiments.Facade.Campus;
import Defalt.Batiments.Visiteur.VisiteurTypePiece;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Campus campus = new Campus();
        campus.createBatiment("Batiment 12", "Usage 1", 100, true, 10, 2, 5);
        campus.createBatiment("Batiment 2", "Usage 2", 200, false, 20, 3, 10);
        campus.createBatiment("Batiment 3", "Usage 3", 300, true, 30, 4, 15);
        campus.createBatiment("Batiment 4", "Usage 4", 400, false, 40, 5, 20);

        campus.batimentsToJson("batiments99.json");
    }
}