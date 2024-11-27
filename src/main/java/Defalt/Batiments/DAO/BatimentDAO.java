package Defalt.Batiments.DAO;

import Defalt.Batiments.BatimentsMetiers.Batiment;

import java.io.*;
import java.util.List;

public class BatimentDAO{

    public void serializeBatiments(List<Batiment> listBatiments, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(listBatiments);
            System.out.println("Sérialisation du bâtiment réussie !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Batiment> deserializeBatiments(String fileName) {
        List<Batiment> listBatiments = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            listBatiments = (List<Batiment>) ois.readObject();
            System.out.println("Désérialisation du bâtiment réussie !");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return listBatiments;
    }
}
