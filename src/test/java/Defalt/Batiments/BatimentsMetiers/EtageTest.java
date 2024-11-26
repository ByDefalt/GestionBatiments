package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtageTest {

    private Etage etage;

    @BeforeEach
    void setUp() {
        etage = new Etage(1);
    }

    @Test
    void testConstructeurParDefaut() {
        Etage etageVide = new Etage();
        assertEquals(0, etageVide.getNumero(), "Le numéro par défaut d'un étage doit être 0.");
    }

    @Test
    void testConstructeurAvecParametre() {
        assertEquals(1, etage.getNumero(), "Le numéro de l'étage ne correspond pas au paramètre du constructeur.");
    }

    @Test
    void testSettersEtGetters() {
        etage.setNumero(5);
        assertEquals(5, etage.getNumero(), "Le numéro de l'étage ne correspond pas après modification.");
    }

    @Test
    void testEquals() {
        Etage etageIdentique = new Etage(1);
        Etage etageDifferent = new Etage(2);

        assertEquals(etage, etageIdentique, "Les étages identiques devraient être égaux.");
        assertNotEquals(etage, etageDifferent, "Les étages différents ne devraient pas être égaux.");
        assertNotEquals(null, etage, "Un étage ne devrait pas être égal à null.");
        assertNotEquals("String", etage, "Un étage ne devrait pas être égal à un objet d'une autre classe.");
    }

    @Test
    void testToString() {
        String attendu = "Etage{numero=1}";
        assertEquals(attendu, etage.toString(), "La méthode toString ne retourne pas la valeur attendue.");
    }

    @Test
    void testAcceptVisiteur() {
        // Création d'un mock de visiteur
        Visiteur visiteurMock = mock(Visiteur.class);
        TreeItem<String> treeItemMock = new TreeItem<>("Etage 1");
        when(visiteurMock.visit(etage)).thenReturn(treeItemMock);

        // Appel de la méthode accept
        TreeItem<String> resultat = etage.accept(visiteurMock);

        // Vérification
        assertEquals(treeItemMock, resultat, "La méthode accept n'a pas retourné la valeur attendue.");
        verify(visiteurMock, times(1)).visit(etage);
    }
}
