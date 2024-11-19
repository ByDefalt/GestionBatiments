package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Classe utilitaire pour gérer la sérialisation et la désérialisation des objets {@link Batiment}
 * en JSON. Permet de convertir une liste de bâtiments en fichier JSON et inversement.
 */
public class BatimentFactoryJson {

    /**
     * Constructeur par défaut.
     */
    public BatimentFactoryJson() {
    }

    /**
     * Sérialise une liste d'objets {@link Batiment} dans un fichier JSON.
     *
     * @param batiments La liste des bâtiments à sérialiser.
     * @param nomFichierSortie Le nom du fichier JSON où les bâtiments seront sauvegardés.
     * @throws IOException Si une erreur d'entrée/sortie survient lors de l'écriture du fichier.
     */
    public void BatimentsToJson(List<Batiment> batiments, String nomFichierSortie) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(nomFichierSortie), batiments);
    }

    /**
     * Désérialise une liste d'objets {@link Batiment} à partir d'un fichier JSON.
     *
     * @param nomFichierEntree Le nom du fichier JSON contenant les données des bâtiments.
     * @return Une liste d'objets {@link Batiment} reconstruits depuis le fichier JSON.
     * @throws Exception Si une erreur d'entrée/sortie survient lors de la lecture du fichier.
     */
    public List<Batiment> jsonToBatiments(String nomFichierEntree) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(nomFichierEntree), new TypeReference<List<Batiment>>() {});
    }
}
