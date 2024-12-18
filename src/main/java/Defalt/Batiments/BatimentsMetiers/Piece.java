package Defalt.Batiments.BatimentsMetiers;

import Defalt.Batiments.Visiteur.Visitable;
import Defalt.Batiments.Visiteur.Visiteur;
import javafx.scene.control.TreeItem;

import java.io.Serializable;

/**
 * Représente une pièce dans un bâtiment. Cette classe est visitable via le
 * pattern Visiteur.
 */
public class Piece implements Visitable, Serializable {
	/** Surface de la pièce en mètres carrés. */
	private int surface;

	/** Indique si la pièce est utilisée comme bureau. */
	private boolean estBureau;

	/** Numéro unique de la pièce. */
	private int numero;

	/** Étage auquel appartient la pièce. */
	private Etage etage;

	/**
	 * Constructeur par défaut.
	 */
	public Piece() {
	}

	/**
	 * Constructeur pour créer une pièce avec des informations spécifiques.
	 *
	 * @param surface   La surface de la pièce.
	 * @param estBureau Indique si la pièce est un bureau.
	 * @param numero    Le numéro de la pièce.
	 * @param etage     L'étage auquel appartient la pièce.
	 */
	public Piece(int surface, boolean estBureau, int numero, Etage etage) {
		this.surface = surface;
		this.estBureau = estBureau;
		this.numero = numero;
		this.etage = etage;
	}

	/**
	 * Obtient la surface de la pièce.
	 *
	 * @return La surface en mètres carrés.
	 */
	public int getSurface() {
		return surface;
	}

	/**
	 * Définit la surface de la pièce.
	 *
	 * @param surface La surface en mètres carrés.
	 */
	public void setSurface(int surface) {
		this.surface = surface;
	}

	/**
	 * Vérifie si la pièce est utilisée comme bureau.
	 *
	 * @return {@code true} si la pièce est un bureau, sinon {@code false}.
	 */
	public boolean isEstBureau() {
		return estBureau;
	}

	/**
	 * Définit si la pièce est utilisée comme bureau.
	 *
	 * @param estBureau {@code true} si la pièce est un bureau, sinon {@code false}.
	 */
	public void setEstBureau(boolean estBureau) {
		this.estBureau = estBureau;
	}

	/**
	 * Obtient le numéro de la pièce.
	 *
	 * @return Le numéro de la pièce.
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Définit le numéro de la pièce.
	 *
	 * @param numero Le numéro de la pièce.
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Obtient l'étage auquel appartient la pièce.
	 *
	 * @return L'étage de la pièce.
	 */
	public Etage getEtage() {
		return etage;
	}

	/**
	 * Définit l'étage auquel appartient la pièce.
	 *
	 * @param etage L'étage de la pièce.
	 */
	public void setEtage(Etage etage) {
		this.etage = etage;
	}

	/**
	 * Méthode acceptant un visiteur, permettant l'application d'une logique sur
	 * cette pièce.
	 *
	 * @param visiteur Le visiteur à accepter.
	 */
	@Override
	public TreeItem<String> accept(Visiteur visiteur) {
		return visiteur.visit(this);
	}

	/**
	 * Vérifie l'égalité de cette pièce avec un autre objet.
	 *
	 * @param o L'objet à comparer.
	 * @return {@code true} si les deux objets sont égaux, sinon {@code false}.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Piece piece = (Piece) o;
		return numero == piece.numero;
	}

	/**
	 * Renvoie une représentation sous forme de chaîne de caractères de cette pièce.
	 *
	 * @return Une chaîne décrivant la pièce.
	 */
	@Override
	public String toString() {
		return "Piece{" + "surface=" + surface + ", estBureau=" + estBureau + ", numero=" + numero + ", etage=" + etage
				+ '}';
	}
}
