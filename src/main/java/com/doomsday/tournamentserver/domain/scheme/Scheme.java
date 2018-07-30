package com.doomsday.tournamentserver.domain.scheme;

import com.doomsday.tournamentserver.domain.pair.Pair;
import com.doomsday.tournamentserver.exception.EmptyParameter;

import java.util.List;

public interface Scheme {
    List<Pair<Integer, Integer>> getAllPairs();

    Pair<Integer, Integer> getNextNotPlayedPair();

    List<Pair<Integer, Integer>> getAllPairsInTour(Integer tourNumber) throws IllegalArgumentException;

    Integer getMaxPairCount();

    Integer getToursCount();

    void updateScheme(List<Integer> winnersList) throws EmptyParameter;
}
