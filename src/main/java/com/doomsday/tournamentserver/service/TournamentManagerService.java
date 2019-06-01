package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.domain.model.Match;
import com.doomsday.tournamentserver.domain.model.Score;
import com.doomsday.tournamentserver.service.model.view.MatchView;

import java.util.List;

public interface TournamentManagerService {
    void finishMatch(MatchView matchView);
    void finishMatches(List<MatchView> matches);
    boolean createTournament(long idUser, long idTournament);
}
