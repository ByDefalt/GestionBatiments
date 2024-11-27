package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatimentFactoryJsonTest {

    private BatimentFactoryJson batimentFactoryJson;
    private List<Batiment> batiments;
    private final String nomFichierTest = "test_batiments.json";


    @BeforeEach
    void setUp() {
        batimentFactoryJson = new BatimentFactoryJson();

        // Création d'une liste de bâtiments pour les tests
        batiments = new ArrayList<>();
        Batiment batiment1 = new Batiment();
        batiment1.setNom("Bâtiment A");
        batiment1.setUsage("Usage A");

        Batiment batiment2 = new Batiment();
        batiment2.setNom("Bâtiment B");
        batiment2.setUsage("Usage B");

        batiments.add(batiment1);
        batiments.add(batiment2);
    }

    @AfterEach
    void tearDown() {
        // Suppression du fichier de test après chaque test
        File file = new File(nomFichierTest);
        if (file.exists()) {
            assertTrue(file.delete(), "Le fichier de test n'a pas pu être supprimé.");
        }
    }

    @Test
    void testBatimentsToJson() {
        try {
            // Test de sérialisation
            batimentFactoryJson.BatimentsToJson(batiments, nomFichierTest);

            // Vérification que le fichier a été créé
            File file = new File(nomFichierTest);
            assertTrue(file.exists(), "Le fichier JSON n'a pas été créé.");
            assertTrue(file.length() > 0, "Le fichier JSON est vide.");
        } catch (Exception e) {
            fail("La méthode BatimentsToJson a levé une exception : " + e.getMessage());
        }
    }

    @Test
    void testJsonToBatiments() {
        try {
            // Sérialisation des bâtiments dans un fichier
            batimentFactoryJson.BatimentsToJson(batiments, nomFichierTest);

            // Désérialisation à partir du fichier
            List<Batiment> batimentsDeserialises = batimentFactoryJson.jsonToBatiments(nomFichierTest);

            // Vérification des données désérialisées
            assertNotNull(batimentsDeserialises, "La liste désérialisée est null.");
            assertEquals(batiments.size(), batimentsDeserialises.size(), "Le nombre de bâtiments désérialisés est incorrect.");
            assertEquals(batiments.get(0).getNom(), batimentsDeserialises.get(0).getNom(), "Le nom du premier bâtiment est incorrect.");
            assertEquals(batiments.get(1).getUsage(), batimentsDeserialises.get(1).getUsage(), "L'usage du deuxième bâtiment est incorrect.");
        } catch (Exception e) {
            fail("La méthode jsonToBatiments a levé une exception : " + e.getMessage());
        }
    }
    @Test
    void testJsonToBatimentsFail() {
        try {

            // Désérialisation à partir du fichier
            List<Batiment> batimentsDeserialises = batimentFactoryJson.jsonToBatiments("test_batiments_mauvais.json");

            // Vérification des données désérialisées
            assertNotNull(batimentsDeserialises, "La liste désérialisée est null.");
            assertEquals(batiments.size(), batimentsDeserialises.size()-2, "Le nombre de bâtiments désérialisés est incorrect.");
            assertNotEquals(batiments.get(0).getNom(), batimentsDeserialises.get(0).getNom(), "Le nom du premier bâtiment est incorrect.");
            assertNotEquals(batiments.get(1).getUsage(), batimentsDeserialises.get(1).getUsage(), "L'usage du deuxième bâtiment est incorrect.");
        } catch (Exception e) {
            fail("La méthode jsonToBatiments a levé une exception : " + e.getMessage());
        }
    }

    @Test
    void testJsonToBatimentsFichierInexistant() {
        try {
            // Tentative de désérialisation à partir d'un fichier inexistant
            batimentFactoryJson.jsonToBatiments("fichier_inexistant.json");
            fail("Une exception était attendue pour un fichier inexistant.");
        } catch (Exception e) {
            // Vérification que l'exception est bien levée
            assertTrue(e instanceof java.io.FileNotFoundException, "Une FileNotFoundException était attendue.");
        }
    }
}
