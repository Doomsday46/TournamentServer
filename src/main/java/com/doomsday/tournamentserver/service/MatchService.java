package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.db.Match;

import java.util.Date;
import java.util.List;

public interface MatchService {
    boolean save(long idUser, long idTournament, Match match);
    boolean update(long idUser, long idTournament, Match match);
    boolean remove(long idUser, long idTournament, Match match);
    boolean remove(long idUser, long idTournament, long id);
    Match getMatch(long idUser, long idTournament, long id);
    Match getMatch(long idUser, long idTournament, boolean state);
    List<Match> getMatches(long idUser, long idTournament, Date beforeInterval);
    List<Match> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval);
    List<Match> getMatches(long idUser, long idTournament, boolean state);
    List<Match> getMatches(long idUser, long idTournament, long idPlayer);
}
