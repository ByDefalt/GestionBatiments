package Defalt.Batiments.DAO;

import Defalt.Batiments.BatimentsMetiers.Batiment;

import java.io.*;
import java.util.List;

public class BatimentDAO{

    public void serializeBatiments(List<Batiment> listBatiments, String fileName) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(listBatiments);
    }

    public List<Batiment> deserializeBatiments(String fileName) throws IOException, ClassNotFoundException {
        List<Batiment> listBatiments = null;
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        listBatiments = (List<Batiment>) ois.readObject();
        return listBatiments;
    }
}
