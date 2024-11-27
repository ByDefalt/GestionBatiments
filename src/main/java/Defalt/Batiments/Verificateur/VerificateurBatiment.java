package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.*;

/**
 * Classe permettant de vérifier la validité d'un bâtiment en analysant ses
 * attributs, ses étages et ses pièces, et en détectant les éventuels problèmes.
 */
public class VerificateurBatiment {

	/**
	 * Vérifie les attributs, les étages et les pièces d'un bâtiment pour détecter
	 * les problèmes éventuels.
	 *
	 * @param bat le bâtiment à vérifier.
	 * @return une liste de problèmes détectés sous forme d'énumérations
	 *         {@link ProblemeBatiment}. Si aucun problème n'est détecté, la liste
	 *         contiendra l'élément {@link ProblemeBatiment#AUCUN}.
	 */
	public List<ProblemeBatiment> verifBatiment(Batiment bat) {
		List<ProblemeBatiment> problemes = new ArrayList<>();

		if (bat == null) {
			problemes.add(ProblemeBatiment.NULLBATIMENT);
			return problemes;
		}

		if (bat.getNom() != null && bat.getNom().isEmpty()) {
			problemes.add(ProblemeBatiment.NOM);
		}
		if (bat.getUsage() != null && bat.getUsage().isEmpty()) {
			problemes.add(ProblemeBatiment.USAGE);
		}

		boolean startOne = bat.getPieces().getFirst() != null && bat.getPieces().getFirst().getNumero() == 1;
		int piecenb = startOne ? 1 : 0;
		int etagenb = 0;

		List<Integer> etagesProblemes = new ArrayList<>();
		for (Etage etage : bat.getEtages()) {
			if (etage == null) {
				etagenb++;
				problemes.add(ProblemeBatiment.NULLETAGE);
			} else if (etage.getNumero() != etagenb++) {
				etagesProblemes.add(etage.getNumero());
			}
		}

		List<Integer> piecesProblemes = new ArrayList<>();
		for (Piece piece : bat.getPieces()) {
			if (piece == null) {
				piecenb++;
				problemes.add(ProblemeBatiment.NULLEPIECE);
			} else if (piece.getNumero() != piecenb++) {
				piecesProblemes.add(piece.getNumero());
			}
		}

		if (!etagesProblemes.isEmpty()) {
			problemes.add(ProblemeBatiment.ETAGES.withDetails(etagesProblemes, piecesProblemes));
		}
		if (!piecesProblemes.isEmpty()) {
			problemes.add(ProblemeBatiment.PIECES.withDetails(etagesProblemes, piecesProblemes));
		}

		if (problemes.isEmpty()) {
			problemes.add(ProblemeBatiment.AUCUN.withDetails(new ArrayList<>(), new ArrayList<>()));
		}

		return problemes;
	}
}
