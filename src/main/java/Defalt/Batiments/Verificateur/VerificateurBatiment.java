package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Classe utilitaire pour vérifier l'intégrité d'un objet {@link Batiment}.
 * Permet d'identifier les étages et pièces ayant des numéros incorrects
 * par rapport à un ordre séquentiel attendu.
 */
public class VerificateurBatiment {

    /**
     * Liste des étages présentant des problèmes de numérotation.
     */
    private List<Etage> etagesProblemes;

    /**
     * Liste des pièces présentant des problèmes de numérotation.
     */
    private List<Piece> piecesProblemes;

    /**
     * Constructeur par défaut initialisant les listes de problèmes.
     */
    public VerificateurBatiment() {
        this.etagesProblemes = new ArrayList<>();
        this.piecesProblemes = new ArrayList<>();
    }

    /**
     * Récupère la liste des étages ayant des problèmes de numérotation.
     *
     * @return Une liste d'objets {@link Etage} avec des problèmes.
     */
    public List<Etage> getEtagesProblemes() {
        return etagesProblemes;
    }

    /**
     * Récupère la liste des pièces ayant des problèmes de numérotation.
     *
     * @return Une liste d'objets {@link Piece} avec des problèmes.
     */
    public List<Piece> getPiecesProblemes() {
        return piecesProblemes;
    }

    /**
     * Vérifie si les numérotations des étages et des pièces d'un bâtiment sont correctes.
     *
     * Les numéros doivent suivre une séquence croissante à partir de 0 ou 1, selon le paramètre `startOne`.
     *
     * @param bat      Le bâtiment à vérifier.
     * @param startOne Indique si la numérotation commence à 1 (true) ou à 0 (false).
     * @return {@code true} si toutes les numérotations sont correctes, {@code false} sinon.
     */
    public boolean verifBatiment(Batiment bat, boolean startOne) {
        if (bat == null) {
            return false;
        }

        etagesProblemes = new ArrayList<>();
        piecesProblemes = new ArrayList<>();

        AtomicInteger piecenb = new AtomicInteger(startOne ? 1 : 0);
        AtomicInteger etagenb = new AtomicInteger(startOne ? 1 : 0);

        etagesProblemes = bat.getEtages()
                .stream()
                .filter(etage -> etage.getNumero() != etagenb.getAndIncrement())
                .toList();

        piecesProblemes = bat.getPieces()
                .stream()
                .filter(piece -> piece.getNumero() != piecenb.getAndIncrement())
                .toList();

        return etagesProblemes.isEmpty() && piecesProblemes.isEmpty();
    }
}
