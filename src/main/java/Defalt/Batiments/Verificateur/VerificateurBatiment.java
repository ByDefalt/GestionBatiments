package Defalt.Batiments.Verificateur;

import Defalt.Batiments.BatimentsMetiers.Batiment;
import Defalt.Batiments.BatimentsMetiers.Etage;
import Defalt.Batiments.BatimentsMetiers.Piece;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
     * @param startOne Indique si la numérotation commence à 1 (true) ou à 0 (false).
     * @return {@code true} si toutes les numérotations sont correctes, {@code false} sinon.
     */
    public Map<String,List<ProblemeBatiment>> verifBatiment(Batiment bat, boolean startOne) {
        Map<String,List<ProblemeBatiment>> mapProblemes = new HashMap<>();
        List<ProblemeBatiment> problemes = new ArrayList<>();
        if (bat == null) {
            return null;
        }
        if(bat.getNom()!=null && bat.getNom().isEmpty()){
            problemes.add(ProblemeBatiment.NOM);
        }
        if(bat.getUsage()!=null && bat.getUsage().isEmpty()){
            problemes.add(ProblemeBatiment.USAGE.withDetails(new ArrayList<>(),new ArrayList<>()));
        }
        AtomicInteger piecenb = new AtomicInteger(startOne ? 1 : 0);
        AtomicInteger etagenb = new AtomicInteger(startOne ? 1 : 0);

        List<Integer> etagesProblemes = bat.getEtages()
                .stream()
                .filter(Objects::nonNull)
                .map(Etage::getNumero)
                .filter(numero -> numero != etagenb.getAndIncrement())
                .toList();

        List<Integer> piecesProblemes = bat.getPieces()
                .stream()
                .filter(Objects::nonNull)
                .map(Piece::getNumero)
                .filter(numero -> numero != piecenb.getAndIncrement())
                .toList();

        if(!etagesProblemes.isEmpty() && !piecesProblemes.isEmpty()){
            problemes.add(ProblemeBatiment.ETAGE_ET_PIECE.withDetails(etagesProblemes, piecesProblemes));
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
        mapProblemes.put(bat.getNom(),problemes);
        return mapProblemes;
    }
}
