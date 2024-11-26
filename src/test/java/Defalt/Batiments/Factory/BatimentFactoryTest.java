package Defalt.Batiments.Factory;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatimentFactoryTest {

    private BatimentFactory factory;

    @BeforeEach
    void setUp() {
        factory = new BatimentFactory();
    }

    @Test
    void testCreateBatiment() {
        String nom = "Bâtiment Test";
        String usage = "Résidentiel";
        int surfacePiece = 20;
        boolean startOne = true;
        int nbBureau = 2;
        int nbEtage = 2;
        int nbPiece = 4;

        // Création d'un bâtiment avec les paramètres donnés
        Batiment batiment = factory.createBatiment(nom, usage, surfacePiece, startOne, nbBureau, nbEtage, nbPiece);

        // Vérification des attributs principaux du bâtiment
        assertEquals(nom, batiment.getNom(), "Le nom du bâtiment est incorrect.");
        assertEquals(usage, batiment.getUsage(), "L'usage du bâtiment est incorrect.");
        assertEquals(nbEtage, batiment.getEtages().size(), "Le nombre d'étages est incorrect.");
        assertEquals(nbPiece, batiment.getPieces().size(), "Le nombre de pièces est incorrect.");

        // Vérification des pièces et de leurs attributs
        for (int i = 0; i < nbPiece; i++) {
            Piece piece = batiment.getPieces().get(i);
            assertEquals(surfacePiece, piece.getSurface(), "La surface de la pièce est incorrecte.");
            if (i < nbBureau) {
                assertTrue(piece.isEstBureau(), "La pièce aurait dû être un bureau.");
            } else {
                assertFalse(piece.isEstBureau(), "La pièce n'aurait pas dû être un bureau.");
            }
        }

        // Vérification des étages et de l'association des pièces
        for (int i = 0; i < nbEtage; i++) {
            Etage etage = batiment.getEtages().get(i);
            int piecesDansEtage = (int) batiment.getPieces().stream()
                    .filter(piece -> piece.getEtage().equals(etage))
                    .count();
            assertTrue(piecesDansEtage > 0, "Chaque étage doit contenir au moins une pièce.");
        }

        // Vérification du compteur d'usine
        assertEquals(1, factory.getNbBatimentsCree(), "Le compteur de bâtiments créés est incorrect.");
    }

    @Test
    void testEqualsAndHashCode() {
        BatimentFactory factory1 = new BatimentFactory();
        factory1.setNbEtage(2);
        factory1.setNbPiece(4);
        factory1.setNbBatimentsCree(1);

        BatimentFactory factory2 = new BatimentFactory();
        factory2.setNbEtage(2);
        factory2.setNbPiece(4);
        factory2.setNbBatimentsCree(1);

        assertEquals(factory1, factory2, "Les deux usines devraient être égales.");
    }

    @Test
    void testToString() {
        factory.setNbEtage(2);
        factory.setNbPiece(4);
        factory.setNbBatimentsCree(1);

        String expected = "BatimentFactory{, nbEtage=2, nbPiece=4, nbBatimentsCree=1}";
        assertEquals(expected, factory.toString(), "La méthode toString retourne une chaîne incorrecte.");
    }
}
