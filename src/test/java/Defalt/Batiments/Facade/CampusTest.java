package Defalt.Batiments.Facade;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.Factory.BatimentFactoryJson;
import Defalt.Batiments.Verificateur.VerificateurBatiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CampusTest {

    private Campus campus;
    private final String testFileName = "test_batiments.json";

    @BeforeEach
    void setUp() {
        campus = new Campus("Campus Test", true);
    }

    @Test
    void testCreateBatiment() {
        // Test de la création d'un bâtiment
        assertTrue(campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5),
                "Le bâtiment devrait être créé avec succès.");
        assertFalse(campus.createBatiment("Batiment 1", "commercial", 200, 15, 4, 6),
                "Le bâtiment ne devrait pas être créé car le nom est déjà pris.");
    }

    @Test
    void testDestroyBatiment() {
        // Création d'un bâtiment pour le tester
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);

        // Test de suppression d'un bâtiment existant
        assertTrue(campus.destroyBatiment("Batiment 1"), "Le bâtiment devrait être supprimé avec succès.");
        // Test de suppression d'un bâtiment inexistant
        assertFalse(campus.destroyBatiment("Batiment 2"), "Le bâtiment n'existe pas, donc il ne doit pas être supprimé.");
    }

    @Test
    void testUpdateNomBatiment() {
        // Création d'un bâtiment pour le tester
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);

        // Test de la mise à jour du nom d'un bâtiment
        assertTrue(campus.updateNomBatiment("Batiment 1", "Batiment 2"),
                "Le nom du bâtiment devrait être mis à jour avec succès.");
        assertFalse(campus.updateNomBatiment("Batiment 3", "Batiment 4"),
                "Le bâtiment à mettre à jour n'existe pas, donc l'opération doit échouer.");
    }

    @Test
    void testUpdatePieceEstBureau() {
        // Création d'un bâtiment avec des pièces
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);
        // Mettre à jour une pièce pour la marquer comme bureau
        assertTrue(campus.updatePieceEstBureau("Batiment 1", 1, 1),
                "La pièce devrait être mise à jour comme bureau.");
        // Tenter de mettre à jour une pièce qui n'existe pas
        assertFalse(campus.updatePieceEstBureau("Batiment 1", 10, 10),
                "La pièce spécifiée n'existe pas, donc l'opération doit échouer.");
    }

    @Test
    void testVerifBatiments() {
        // Création d'un bâtiment valide
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);

        // Vérification que tous les bâtiments respectent les règles de numérotation
        assertTrue(campus.verifBatiments(), "Tous les bâtiments devraient respecter les règles.");
    }

    @Test
    void testBatimentsToJson() throws IOException {
        // Création d'un bâtiment
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);

        // Exportation des bâtiments dans un fichier JSON
        campus.batimentsToJson(testFileName);

        // Vérification que le fichier JSON a bien été créé
        File file = new File(testFileName);
        assertTrue(file.exists(), "Le fichier JSON devrait exister après l'exportation.");

        // Nettoyer après le test
        file.delete();
    }

    @Test
    void testJsonToBatiments() throws Exception {
        // Création d'un bâtiment et exportation dans un fichier JSON
        campus.createBatiment("Batiment 1", "résidentiel", 100, 10, 3, 5);
        campus.batimentsToJson(testFileName);

        // Importation des bâtiments depuis le fichier JSON
        List<Batiment> importedBatiments = campus.jsonToBatiments(testFileName);

        // Vérification que les bâtiments ont bien été importés
        assertNotNull(importedBatiments, "Les bâtiments importés ne doivent pas être nuls.");
        assertEquals(1, importedBatiments.size(), "Il devrait y avoir un bâtiment importé.");

        // Nettoyer après le test
        new File(testFileName).delete();
    }

    @Test
    void testEqualsAndHashCode() {
        Campus campus1 = new Campus("Campus Test", true);
        Campus campus2 = new Campus("Campus Test", true);
        Campus campus3 = new Campus("Campus Différent", true);

        // Vérification de l'égalité entre campus avec le même nom
        assertTrue(campus1.equals(campus2), "Les campus avec le même nom doivent être égaux.");
        assertFalse(campus1.equals(campus3), "Les campus avec des noms différents ne doivent pas être égaux.");

        // Vérification des codes de hachage
        assertEquals(campus1.hashCode(), campus2.hashCode(), "Les codes de hachage doivent être identiques pour les campus égaux.");
        assertNotEquals(campus1.hashCode(), campus3.hashCode(), "Les codes de hachage doivent être différents pour les campus non égaux.");
    }
}
