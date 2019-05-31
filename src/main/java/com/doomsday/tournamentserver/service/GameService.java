package com.doomsday.tournamentserver.service;

import com.doomsday.tournamentserver.db.Game;

import java.util.Date;
import java.util.List;

public interface GameService {
    boolean save(long idUser, long idTournament, Game game);
    boolean update(long idUser, long idTournament, Game game);
    boolean remove(long idUser, long idTournament, Game game);
    boolean remove(long idUser, long idTournament, long id);
    Game getMatch(long idUser, long idTournament, long id);
    Game getMatch(long idUser, long idTournament, boolean state);
    List<Game> getMatches(long idUser, long idTournament, Date beforeInterval);
    List<Game> getMatches(long idUser, long idTournament, Date beginInterval, Date endInterval);
    List<Game> getMatches(long idUser, long idTournament, boolean state);
    List<Game> getMatches(long idUser, long idTournament, long idPlayer);
}
