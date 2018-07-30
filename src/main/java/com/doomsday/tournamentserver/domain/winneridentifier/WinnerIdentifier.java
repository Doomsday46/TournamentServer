package com.doomsday.tournamentserver.domain.winneridentifier;

import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Player;

import java.util.List;

public interface WinnerIdentifier {
     List<Player> identifyWinners (List<Match> finishedMatches);
}
