package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.*;

/**
 * Classe utilitaire pour vérifier l'intégrité d'un objet {@link Batiment}.
 * Permet d'identifier les étages et pièces ayant des numéros incorrects
 * par rapport à un ordre séquentiel attendu.
 */
public class VerificateurBatiment {
    /**
     * Vérifie si les numérotations des étages et des pièces d'un bâtiment sont correctes.
     * <p>
     * Les numéros doivent suivre une séquence croissante à partir de 0 ou 1, selon le paramètre `startOne`.
     *
     * @param bat      Le bâtiment à vérifier.
     * @return {@code true} si toutes les numérotations sont correctes, {@code false} sinon.
     */
    public List<ProblemeBatiment> verifBatiment(Batiment bat) {
        List<ProblemeBatiment> problemes = new ArrayList<>();
        if (bat == null) {
            problemes.add(ProblemeBatiment.NULLBATIMENT);
            return problemes;
        }
        if(bat.getNom()!=null && bat.getNom().isEmpty()){
            problemes.add(ProblemeBatiment.NOM);
        }
        if(bat.getUsage()!=null && bat.getUsage().isEmpty()){
            problemes.add(ProblemeBatiment.USAGE);
        }
        boolean startOne=bat.getEtages().getFirst()!=null && bat.getEtages().getFirst().getNumero()==1;

        int piecenb = startOne ? 1 : 0;
        int etagenb = startOne ? 1 : 0;

        List<Integer> etagesProblemes = new ArrayList<>();
        for(Etage etage:bat.getEtages()){
            if (etage==null) {
                etagenb++;
                problemes.add(ProblemeBatiment.NULLETAGE);
            }
            if (etage.getNumero() != etagenb++) {
                etagesProblemes.add(etage.getNumero());
            }
        }
        List<Integer> piecesProblemes = new ArrayList<>();
        for(Piece piece: bat.getPieces()){
            if (piece==null) {
                piecenb++;
                problemes.add(ProblemeBatiment.NULLEPIECE);
            }
            if (piece.getNumero() != piecenb++) {
                piecesProblemes.add(piece.getNumero());
            }
        }

        if(!etagesProblemes.isEmpty()){
            problemes.add(ProblemeBatiment.ETAGES.withDetails(etagesProblemes, new ArrayList<>()));
        }
        if(!piecesProblemes.isEmpty()){
            problemes.add(ProblemeBatiment.PIECES.withDetails(new ArrayList<>(), piecesProblemes));
        }
        if(problemes.isEmpty()){
            problemes.add(ProblemeBatiment.AUCUN.withDetails(new ArrayList<>(),new ArrayList<>()));
        }
        return problemes;
    }
}
