package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.pair.Pair;

import java.util.List;

public interface Scheme {
    List<Pair<Integer, Integer>> getAllPairs();

    Pair<Integer, Integer> getNextNotPlayedPair() throws Exception;

    List<Pair<Integer, Integer>> getAllPairsInTour(Integer tourNumber) throws Exception;

    Integer getMaxPairCount();

    Integer getToursCount();

    void updateScheme(List<Integer> winnersList) throws Exception;
}
