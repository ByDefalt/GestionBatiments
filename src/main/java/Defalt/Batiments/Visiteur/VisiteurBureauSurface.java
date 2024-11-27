package Defalt.Batiments.Visiteur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;
import javafx.scene.control.TreeItem;

/**
 * Implémentation de l'interface {@link Visiteur} qui affiche les informations
 * des bureaux et leur surface dans un bâtiment.
 * <p>
 * Cette classe utilise le pattern visiteur pour traverser la structure d'un
 * bâtiment, ses étages et ses pièces. Elle construit un arbre hiérarchique de
 * type {@link TreeItem} représentant les bureaux d'un bâtiment et leurs détails
 * (numéro et surface).
 * </p>
 */
public class VisiteurBureauSurface implements Visiteur {
	/**
	 * Racine de l'arbre contenant les informations du bâtiment.
	 */
	private TreeItem<String> rootItem;

	/**
	 * Constructeur par défaut. Initialise la racine de l'arbre {@link TreeItem}.
	 */
	public VisiteurBureauSurface() {
		this.rootItem = new TreeItem<>();
	}

	/**
	 * Visite un objet {@link Batiment} pour en extraire les informations sur les
	 * bureaux et leurs surfaces, en parcourant ses étages et pièces.
	 *
	 * @param batiment le bâtiment à visiter.
	 * @return un objet {@link TreeItem} représentant la hiérarchie des bureaux du
	 *         bâtiment.
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
	 * Visite un objet {@link Piece} pour récupérer ses informations si c'est un
	 * bureau.
	 *
	 * @param piece la pièce à visiter.
	 * @return un objet {@link TreeItem} contenant les informations de la pièce si
	 *         c'est un bureau, ou {@code null} sinon.
	 */
	@Override
	public TreeItem<String> visit(Piece piece) {
		if (piece.isEstBureau()) {
			return new TreeItem<>("Bureau n°" + piece.getNumero() + " (Surface : " + piece.getSurface() + " m²)");
		} else {
			return null;
		}
	}
}
