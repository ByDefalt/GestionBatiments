package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BatimentFactoryJsonTest {

    private BatimentFactoryJson batimentFactoryJson;
    private List<Batiment> batiments;
    private final String testFileName = "test_batiments.json";

    @BeforeEach
    void setUp() {
        batimentFactoryJson = new BatimentFactoryJson();
        batiments = new ArrayList<>();
        // Création de quelques bâtiments pour les tests
        Batiment batiment1 = new Batiment("Batiment 1", "residential");
        Batiment batiment2 = new Batiment("Batiment 2", "commercial");
        batiments.add(batiment1);
        batiments.add(batiment2);
    }

    @Test
    void testBatimentsToJson() throws IOException {
        // Sérialiser la liste de bâtiments
        batimentFactoryJson.BatimentsToJson(batiments, testFileName);

        // Vérifier que le fichier a bien été créé
        File file = new File(testFileName);
        assertTrue(file.exists(), "Le fichier JSON devrait exister après la sérialisation.");

        // Nettoyer après le test
        file.delete();
    }

    @Test
    void testJsonToBatiments() throws Exception {
        // Sérialiser les bâtiments dans un fichier JSON
        batimentFactoryJson.BatimentsToJson(batiments, testFileName);

        // Désérialiser la liste de bâtiments depuis le fichier
        List<Batiment> deserializedBatiments = batimentFactoryJson.jsonToBatiments(testFileName);

        // Vérifier que la désérialisation a fonctionné
        assertNotNull(deserializedBatiments, "La liste des bâtiments ne doit pas être nulle après la désérialisation.");
        assertEquals(batiments.size(), deserializedBatiments.size(), "La taille des listes de bâtiments doit être la même après la désérialisation.");

        // Vérifier que les bâtiments sérialisés et désérialisés sont équivalents
        assertEquals(batiments.get(0).getNom(), deserializedBatiments.get(0).getNom(), "Les noms des bâtiments doivent être égaux.");
        assertEquals(batiments.get(1).getNom(), deserializedBatiments.get(1).getNom(), "Les noms des bâtiments doivent être égaux.");

        // Nettoyer après le test
        new File(testFileName).delete();
    }

    @Test
    void testJsonToBatimentsWithEmptyFile() throws IOException {
        // Essayer de désérialiser depuis un fichier vide
        File emptyFile = new File("empty.json");
        emptyFile.createNewFile();

        assertThrows(IOException.class, () -> batimentFactoryJson.jsonToBatiments("empty.json"));
        // Nettoyer après le test
        emptyFile.delete();
    }
}
