package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificateurBatimentTest {

	private VerificateurBatiment verificateur;
	private Batiment batiment;

	@BeforeEach
	void setUp() {
		verificateur = new VerificateurBatiment();

		// Créer un bâtiment avec des attributs valides pour les tests
		batiment = new Batiment();
		batiment.setNom("Bâtiment A");
		batiment.setUsage("Bureau");

		LinkedList<Etage> etages = new LinkedList<>();
		etages.add(new Etage(0));
		etages.add(new Etage(1));
		batiment.setEtages(etages);

		LinkedList<Piece> pieces = new LinkedList<>();
		pieces.add(new Piece(20, true, 1, etages.get(0)));
		pieces.add(new Piece(30, false, 2, etages.get(1)));
		batiment.setPieces(pieces);
	}

	@Test
	void testBatimentNull() {
		List<ProblemeBatiment> problemes = verificateur.verifBatiment(null);
		assertEquals(1, problemes.size(), "Un bâtiment null devrait produire un seul problème.");
		assertTrue(problemes.contains(ProblemeBatiment.NULLBATIMENT), "Le problème attendu est NULLBATIMENT.");
	}

	@Test
	void testNomUsageInvalides() {
		batiment.setNom("");
		batiment.setUsage("");

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.contains(ProblemeBatiment.NOM), "Le problème attendu est NOM.");
		assertTrue(problemes.contains(ProblemeBatiment.USAGE), "Le problème attendu est USAGE.");
	}

	@Test
	void testEtageNull() {
		batiment.getEtages().add(null);

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.contains(ProblemeBatiment.NULLETAGE), "Le problème attendu est NULLETAGE.");
	}

	@Test
	void testPieceNull() {
		batiment.getPieces().add(null);

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.contains(ProblemeBatiment.NULLEPIECE), "Le problème attendu est NULLEPIECE.");
	}

	@Test
	void testNumeroEtageIncorrect() {
		batiment.getEtages().add(1, new Etage(3)); // Ajouter un étage avec un numéro incorrect

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.ETAGES), "Le problème attendu est ETAGES.");
	}

	@Test
	void testNumeroPieceIncorrect() {
		batiment.getPieces().add(1, new Piece(15, false, 5, batiment.getEtages().get(0))); // Ajouter une pièce avec un
																							// numéro incorrect

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.PIECES), "Le problème attendu est PIECES.");
	}

	@Test
	void testAllInvalid() {
		batiment.setNom("");
		batiment.setUsage("");
		batiment.getEtages().add(null);
		batiment.getPieces().add(null);
		batiment.getEtages().add(1, new Etage(3));
		batiment.getPieces().add(1, new Piece(15, false, 5, batiment.getEtages().get(0)));

		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.NOM), "Le problème attendu est NOM.");
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.USAGE), "Le problème attendu est USAGE.");
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.NULLETAGE),
				"Le problème attendu est NULLETAGE.");
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.NULLEPIECE),
				"Le problème attendu est NULLEPIECE.");
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.ETAGES), "Le problème attendu est ETAGES.");
		assertTrue(problemes.stream().anyMatch(p -> p == ProblemeBatiment.PIECES), "Le problème attendu est PIECES.");
	}

	@Test
	void testBatimentValide() {
		List<ProblemeBatiment> problemes = verificateur.verifBatiment(batiment);

		assertEquals(1, problemes.size(), "Un bâtiment valide devrait produire un seul problème (AUCUN).");
		assertTrue(problemes.contains(ProblemeBatiment.AUCUN), "Le problème attendu est AUCUN.");
	}

}
