package Defalt.Batiments.DAO;

import Defalt.Batiments.BatimentsMetiers.Batiment;

import java.io.*;

public class BatimentDAO{

    public void serializeBatiment(Batiment batiment, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(batiment);
            System.out.println("Sérialisation du bâtiment réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Batiment deserializeBatiment(String fileName) {
        Batiment batiment = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            batiment = (Batiment) ois.readObject();
            System.out.println("Désérialisation du bâtiment réussie !");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return batiment;
    }
}
