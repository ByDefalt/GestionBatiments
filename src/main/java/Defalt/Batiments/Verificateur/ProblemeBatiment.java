package Defalt.Batiments.Verificateur;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.List;

public enum ProblemeBatiment {
    NOM,
    NOMINLISTE,
    USAGE,
    ETAGES,
    PIECES,
    ETAGE_ET_PIECE,
    AUCUN;

    private List<Integer> etagesProblemes;
    private List<Integer> piecesProblemes;

    // Méthode pour initialiser dynamiquement les listes
    public ProblemeBatiment withDetails(List<Integer> etages, List<Integer> pieces) {
        this.etagesProblemes = etages;
        this.piecesProblemes = pieces;
        return this;
    }

    public List<Integer> getEtagesProblemes() {
        return etagesProblemes;
    }

    public List<Integer> getPiecesProblemes() {
        return piecesProblemes;
    }
}