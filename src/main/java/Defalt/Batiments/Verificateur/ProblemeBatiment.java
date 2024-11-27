package Defalt.Batiments.Verificateur;

import java.util.List;

/**
 * Énumération représentant les différents types de problèmes pouvant être
 * associés à un bâtiment. Chaque problème peut avoir des détails optionnels
 * sous forme de listes d'identifiants d'étages ou de pièces concernés.
 */
public enum ProblemeBatiment {

	/**
	 * Problème lié au nom du bâtiment.
	 */
	NOM,

	/**
	 * Problème indiquant que le nom du bâtiment existe déjà dans une liste.
	 */
	NOMINLISTE,

	/**
	 * Problème lié à l'usage défini pour le bâtiment.
	 */
	USAGE,

	/**
	 * Problème concernant l'ordre ou les informations des étages.
	 */
	ETAGES,

	/**
	 * Problème concernant l'ordre ou les informations des pièces.
	 */
	PIECES,

	/**
	 * Problème indiquant que le bâtiment est null.
	 */
	NULLBATIMENT,

	/**
	 * Problème indiquant qu'un étage est null.
	 */
	NULLETAGE,

	/**
	 * Problème indiquant qu'une pièce est null.
	 */
	NULLEPIECE,

	/**
	 * Indique l'absence de problèmes.
	 */
	AUCUN;

	// Champs pour les détails des problèmes
	private List<Integer> etagesProblemes;
	private List<Integer> piecesProblemes;

	/**
	 * Méthode pour initialiser dynamiquement les listes d'étages et de pièces
	 * concernés par le problème.
	 *
	 * @param etages une liste d'identifiants d'étages posant un problème.
	 * @param pieces une liste d'identifiants de pièces posant un problème.
	 * @return l'instance de l'énumération avec les détails définis.
	 */
	public ProblemeBatiment withDetails(List<Integer> etages, List<Integer> pieces) {
		this.etagesProblemes = etages;
		this.piecesProblemes = pieces;
		return this;
	}

	/**
	 * Récupère la liste des identifiants des étages posant un problème.
	 *
	 * @return une liste d'identifiants d'étages, ou null si aucun problème n'est
	 *         défini pour les étages.
	 */
	public List<Integer> getEtagesProblemes() {
		return etagesProblemes;
	}

	/**
	 * Récupère la liste des identifiants des pièces posant un problème.
	 *
	 * @return une liste d'identifiants de pièces, ou null si aucun problème n'est
	 *         défini pour les pièces.
	 */
	public List<Integer> getPiecesProblemes() {
		return piecesProblemes;
	}
}
