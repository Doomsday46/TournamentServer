package com.doomsday.tournamentserver.domain.winneridentifier;

import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Player;
import com.doomsday.tournamentserver.domain.pair.Pair;

import java.util.*;
import java.util.stream.Stream;

public class RoundWinnerIdentifier implements WinnerIdentifier {

    private Map<Player, Integer> playerScores;
    private List<Match> finishedMatches;

    public RoundWinnerIdentifier() {
    }

    @Override
    public List<Player> identifyWinners(List<Match> finishedMatches) {
        if (finishedMatches == null) throw new NullPointerException();
        if (finishedMatches.isEmpty()) throw new IllegalArgumentException("Matches list is empty");

        this.finishedMatches = finishedMatches;
        playerScores = getPlayerScore();
        List<Player> sortedPlayers = new ArrayList<>();
        Map<Player, Integer> bergerMap = calcBerger();
        Stream<Map.Entry<Player,Integer>> st = bergerMap.entrySet().stream();

        st.sorted(Comparator.comparing(e -> e.getValue()))
                .forEach(e ->sortedPlayers.add(e.getKey()));
        List<Player> result = sortedPlayers.subList(sortedPlayers.size()-3, sortedPlayers.size());
        Collections.reverse(result);
        return result;
    }
    private Map<Player, Integer> getPlayerScore(){
        playerScores = new HashMap<>();
        for (Match match : finishedMatches)
        {
            if (match == null) throw new NullPointerException();
            if (!(playerScores.containsKey(match.getFirstSide())))
                playerScores.put(match.getFirstSide(), 0);
            if (!(playerScores.containsKey(match.getSecondSide())))
                playerScores.put(match.getSecondSide(), 0);
        }
        for (Match match : finishedMatches) {
            Player winner = match.getWinner();
            Integer score = playerScores.get(winner);
            score = score + 1;
            playerScores.replace(winner, score);
        }
        return playerScores;
    }
    private Map<Player, Integer> calcBerger(){
        Map<Player, Integer> bergerMap = new HashMap<>();
        var bergerCoeff = 0;
        for (Player player : playerScores.keySet()) {
            bergerCoeff = caclBergerCoeffForAllMatches(player);
            bergerMap.put(player, bergerCoeff);
        }
        return bergerMap;
    }
    private Integer caclBergerCoeffForAllMatches(Player player){
        var bergerCoeff = 0;
        for (Match match : finishedMatches) {
            if (match.getFirstSide().equals(player) || match.getSecondSide().equals(player)) {
                if (match.getWinner().equals(player)) {
                    bergerCoeff = calcBergerCoeff(match,player);
                } else {
                    bergerCoeff =  calcBergerCoeff(match,player);
                }
            }
        }
        return bergerCoeff;
    }
    private Integer calcBergerCoeff(Match match, Player player){
        var bergerCoeff = 0;
        if (match.getFirstSide().equals(player)) {
            bergerCoeff = bergerCoeff - playerScores.get(match.getSecondSide());
        } else {
            bergerCoeff = bergerCoeff - playerScores.get(match.getFirstSide());
        }
        return bergerCoeff;
    }
}
