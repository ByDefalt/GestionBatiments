package Defalt.Batiments.Visiteur;

import Defalt.Batiments.Facade.Campus;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisiteurBureauSurfaceTest {

    @Test
    void visit() {
        Campus campus = new Campus();
        campus.createBatiment("Bureau", "Bureau", 10, true, 10, 10, 100);
        VisiteurBureauSurface visiteurBureauSurface = new VisiteurBureauSurface();
        TreeItem<String> t=campus.afficherDetailsBatiment("Bureau", visiteurBureauSurface);
        assertEquals("  Étage n°0", t.getChildren().getFirst().getValue());
        assertEquals("Bureau n°1 (Surface : 10 m²)", t.getChildren().getFirst().getChildren().getFirst().getValue());
    }
}