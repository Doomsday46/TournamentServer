package com.doomsday.tournamentserver.domain.winneridentifier;

import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Player;

import java.util.Collections;
import java.util.List;

public class OlympicWinnerIdentifier implements WinnerIdentifier{

    public OlympicWinnerIdentifier() {

    }

    @Override
    public List<Player> identifyWinners(List<Match> finishedMatches){
        if (finishedMatches == null) throw new NullPointerException();
        if(finishedMatches.isEmpty()) throw new IllegalArgumentException("List winners is empty!");
        Match match = finishedMatches.get(0);
        for (int i = 1; i < finishedMatches.size(); i++) {
            if (finishedMatches.get(i).getDate().isAfter(match.getDate())) {
                match = finishedMatches.get(i);
            }
        }
        return Collections.singletonList(match.getWinner());
    }
}
