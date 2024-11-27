package Defalt.Batiments.Visiteur;

import Defalt.Batiments.Facade.Campus;
import javafx.scene.control.TreeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VisiteurTypePieceTest {

	@Test
	void visit() {
		Campus campus = new Campus();
		campus.createBatiment("Bureau", "Bureau", 10, true, 10, 10, 100);
		VisiteurTypePiece visiteurTypePiece = new VisiteurTypePiece();
		TreeItem<String> t = campus.afficherDetailsBatiment("Bureau", visiteurTypePiece);
		assertEquals("  Étage n°0", t.getChildren().getFirst().getValue());
		assertEquals("    Pièce n°1 (Type : Bureau)", t.getChildren().getFirst().getChildren().getFirst().getValue());
	}
}