package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import javafx.scene.control.TreeItem;

/**
 * Implémentation de l'interface {@link Visiteur} qui affiche les informations
 * relatives aux types de pièces dans un bâtiment, en précisant si une pièce est
 * un bureau ou non.
 * <p>
 * Cette classe utilise le pattern visiteur pour parcourir la structure d'un
 * bâtiment, ses étages et ses pièces. Elle construit une représentation
 * arborescente de type {@link TreeItem}, indiquant le type de chaque pièce.
 * </p>
 */
public class VisiteurTypePiece implements Visiteur {

	/**
	 * Racine de l'arbre contenant les informations sur le bâtiment.
	 */
	private TreeItem<String> rootItem;

	/**
	 * Constructeur par défaut. Initialise la racine de l'arbre {@link TreeItem}.
	 */
	public VisiteurTypePiece() {
		this.rootItem = new TreeItem<>();
	}

	/**
	 * Visite un objet {@link Batiment} pour collecter les informations sur les
	 * types de pièces, en parcourant ses étages et pièces.
	 *
	 * @param batiment le bâtiment à visiter.
	 * @return un objet {@link TreeItem} représentant la hiérarchie des pièces par
	 *         étage.
	 */
	@Override
	public TreeItem<String> visit(Batiment batiment) {
		rootItem.setValue("Bâtiment : " + batiment.getNom());
		rootItem.setExpanded(true);

		Etage etageVisited = null;
		TreeItem<String> etageItem = null;

		for (Piece piece : batiment.getPieces()) {
			if (!piece.getEtage().equals(etageVisited)) {
				etageItem = piece.getEtage().accept(this);
				etageItem.setExpanded(true);
				rootItem.getChildren().add(etageItem);
				etageVisited = piece.getEtage();
			}

			TreeItem<String> pieceItem = piece.accept(this);
			if (pieceItem != null) {
				etageItem.getChildren().add(pieceItem);
			}
		}
		return rootItem;
	}

	/**
	 * Visite un objet {@link Etage} pour récupérer ses informations.
	 *
	 * @param etage l'étage à visiter.
	 * @return un objet {@link TreeItem} contenant les informations de l'étage.
	 */
	@Override
	public TreeItem<String> visit(Etage etage) {
		return new TreeItem<>("  Étage n°" + etage.getNumero());
	}

	/**
	 * Visite un objet {@link Piece} pour récupérer ses informations et déterminer
	 * son type.
	 *
	 * @param piece la pièce à visiter.
	 * @return un objet {@link TreeItem} contenant les informations sur le type de
	 *         la pièce (bureau ou autre).
	 */
	@Override
	public TreeItem<String> visit(Piece piece) {
		return new TreeItem<>(
				"    Pièce n°" + piece.getNumero() + " (Type : " + (piece.isEstBureau() ? "Bureau" : "Autre") + ")");
	}
}
